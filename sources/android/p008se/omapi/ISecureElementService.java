package android.p008se.omapi;

import android.p007os.Binder;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.RemoteException;
import android.p008se.omapi.ISecureElementReader;

/* renamed from: android.se.omapi.ISecureElementService */
/* loaded from: classes3.dex */
public interface ISecureElementService extends IInterface {
    ISecureElementReader getReader(String str) throws RemoteException;

    String[] getReaders() throws RemoteException;

    boolean[] isNFCEventAllowed(String str, byte[] bArr, String[] strArr) throws RemoteException;

    /* renamed from: android.se.omapi.ISecureElementService$Default */
    /* loaded from: classes3.dex */
    public static class Default implements ISecureElementService {
        @Override // android.p008se.omapi.ISecureElementService
        public String[] getReaders() throws RemoteException {
            return null;
        }

        @Override // android.p008se.omapi.ISecureElementService
        public ISecureElementReader getReader(String reader) throws RemoteException {
            return null;
        }

        @Override // android.p008se.omapi.ISecureElementService
        public boolean[] isNFCEventAllowed(String reader, byte[] aid, String[] packageNames) throws RemoteException {
            return null;
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* renamed from: android.se.omapi.ISecureElementService$Stub */
    /* loaded from: classes3.dex */
    public static abstract class Stub extends Binder implements ISecureElementService {
        private static final String DESCRIPTOR = "android.se.omapi.ISecureElementService";
        static final int TRANSACTION_getReader = 2;
        static final int TRANSACTION_getReaders = 1;
        static final int TRANSACTION_isNFCEventAllowed = 3;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ISecureElementService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof ISecureElementService)) {
                return (ISecureElementService) iin;
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
                    return "getReaders";
                case 2:
                    return "getReader";
                case 3:
                    return "isNFCEventAllowed";
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
                    String[] _result = getReaders();
                    reply.writeNoException();
                    reply.writeStringArray(_result);
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg0 = data.readString();
                    ISecureElementReader _result2 = getReader(_arg0);
                    reply.writeNoException();
                    reply.writeStrongBinder(_result2 != null ? _result2.asBinder() : null);
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg02 = data.readString();
                    byte[] _arg1 = data.createByteArray();
                    String[] _arg2 = data.createStringArray();
                    boolean[] _result3 = isNFCEventAllowed(_arg02, _arg1, _arg2);
                    reply.writeNoException();
                    reply.writeBooleanArray(_result3);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* renamed from: android.se.omapi.ISecureElementService$Stub$Proxy */
        /* loaded from: classes3.dex */
        private static class Proxy implements ISecureElementService {
            public static ISecureElementService sDefaultImpl;
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

            @Override // android.p008se.omapi.ISecureElementService
            public String[] getReaders() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(1, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getReaders();
                    }
                    _reply.readException();
                    String[] _result = _reply.createStringArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p008se.omapi.ISecureElementService
            public ISecureElementReader getReader(String reader) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(reader);
                    boolean _status = this.mRemote.transact(2, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getReader(reader);
                    }
                    _reply.readException();
                    ISecureElementReader _result = ISecureElementReader.Stub.asInterface(_reply.readStrongBinder());
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p008se.omapi.ISecureElementService
            public boolean[] isNFCEventAllowed(String reader, byte[] aid, String[] packageNames) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(reader);
                    _data.writeByteArray(aid);
                    _data.writeStringArray(packageNames);
                    boolean _status = this.mRemote.transact(3, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isNFCEventAllowed(reader, aid, packageNames);
                    }
                    _reply.readException();
                    boolean[] _result = _reply.createBooleanArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(ISecureElementService impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static ISecureElementService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
