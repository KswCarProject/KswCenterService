package com.android.net;

import android.p007os.Binder;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.RemoteException;

/* loaded from: classes4.dex */
public interface IProxyService extends IInterface {
    String resolvePacFile(String str, String str2) throws RemoteException;

    void setPacFile(String str) throws RemoteException;

    void startPacSystem() throws RemoteException;

    void stopPacSystem() throws RemoteException;

    /* loaded from: classes4.dex */
    public static class Default implements IProxyService {
        @Override // com.android.net.IProxyService
        public String resolvePacFile(String host, String url) throws RemoteException {
            return null;
        }

        @Override // com.android.net.IProxyService
        public void setPacFile(String scriptContents) throws RemoteException {
        }

        @Override // com.android.net.IProxyService
        public void startPacSystem() throws RemoteException {
        }

        @Override // com.android.net.IProxyService
        public void stopPacSystem() throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* loaded from: classes4.dex */
    public static abstract class Stub extends Binder implements IProxyService {
        private static final String DESCRIPTOR = "com.android.net.IProxyService";
        static final int TRANSACTION_resolvePacFile = 1;
        static final int TRANSACTION_setPacFile = 2;
        static final int TRANSACTION_startPacSystem = 3;
        static final int TRANSACTION_stopPacSystem = 4;

        public Stub() {
            attachInterface(this, "com.android.net.IProxyService");
        }

        public static IProxyService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface("com.android.net.IProxyService");
            if (iin != null && (iin instanceof IProxyService)) {
                return (IProxyService) iin;
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
                    return "resolvePacFile";
                case 2:
                    return "setPacFile";
                case 3:
                    return "startPacSystem";
                case 4:
                    return "stopPacSystem";
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
                reply.writeString("com.android.net.IProxyService");
                return true;
            }
            switch (code) {
                case 1:
                    data.enforceInterface("com.android.net.IProxyService");
                    String _arg0 = data.readString();
                    String _arg1 = data.readString();
                    String _result = resolvePacFile(_arg0, _arg1);
                    reply.writeNoException();
                    reply.writeString(_result);
                    return true;
                case 2:
                    data.enforceInterface("com.android.net.IProxyService");
                    String _arg02 = data.readString();
                    setPacFile(_arg02);
                    return true;
                case 3:
                    data.enforceInterface("com.android.net.IProxyService");
                    startPacSystem();
                    return true;
                case 4:
                    data.enforceInterface("com.android.net.IProxyService");
                    stopPacSystem();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* loaded from: classes4.dex */
        private static class Proxy implements IProxyService {
            public static IProxyService sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p007os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return "com.android.net.IProxyService";
            }

            @Override // com.android.net.IProxyService
            public String resolvePacFile(String host, String url) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.android.net.IProxyService");
                    _data.writeString(host);
                    _data.writeString(url);
                    boolean _status = this.mRemote.transact(1, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().resolvePacFile(host, url);
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.net.IProxyService
            public void setPacFile(String scriptContents) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.android.net.IProxyService");
                    _data.writeString(scriptContents);
                    boolean _status = this.mRemote.transact(2, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setPacFile(scriptContents);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.net.IProxyService
            public void startPacSystem() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.android.net.IProxyService");
                    boolean _status = this.mRemote.transact(3, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().startPacSystem();
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.net.IProxyService
            public void stopPacSystem() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.android.net.IProxyService");
                    boolean _status = this.mRemote.transact(4, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().stopPacSystem();
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IProxyService impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static IProxyService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
