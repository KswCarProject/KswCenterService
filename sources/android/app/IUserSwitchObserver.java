package android.app;

import android.p007os.Binder;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.IRemoteCallback;
import android.p007os.Parcel;
import android.p007os.RemoteException;

/* loaded from: classes.dex */
public interface IUserSwitchObserver extends IInterface {
    void onForegroundProfileSwitch(int i) throws RemoteException;

    void onLockedBootComplete(int i) throws RemoteException;

    void onUserSwitchComplete(int i) throws RemoteException;

    void onUserSwitching(int i, IRemoteCallback iRemoteCallback) throws RemoteException;

    /* loaded from: classes.dex */
    public static class Default implements IUserSwitchObserver {
        @Override // android.app.IUserSwitchObserver
        public void onUserSwitching(int newUserId, IRemoteCallback reply) throws RemoteException {
        }

        @Override // android.app.IUserSwitchObserver
        public void onUserSwitchComplete(int newUserId) throws RemoteException {
        }

        @Override // android.app.IUserSwitchObserver
        public void onForegroundProfileSwitch(int newProfileId) throws RemoteException {
        }

        @Override // android.app.IUserSwitchObserver
        public void onLockedBootComplete(int newUserId) throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* loaded from: classes.dex */
    public static abstract class Stub extends Binder implements IUserSwitchObserver {
        private static final String DESCRIPTOR = "android.app.IUserSwitchObserver";
        static final int TRANSACTION_onForegroundProfileSwitch = 3;
        static final int TRANSACTION_onLockedBootComplete = 4;
        static final int TRANSACTION_onUserSwitchComplete = 2;
        static final int TRANSACTION_onUserSwitching = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IUserSwitchObserver asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IUserSwitchObserver)) {
                return (IUserSwitchObserver) iin;
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
                    return "onUserSwitching";
                case 2:
                    return "onUserSwitchComplete";
                case 3:
                    return "onForegroundProfileSwitch";
                case 4:
                    return "onLockedBootComplete";
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
                    IRemoteCallback _arg1 = IRemoteCallback.Stub.asInterface(data.readStrongBinder());
                    onUserSwitching(_arg0, _arg1);
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg02 = data.readInt();
                    onUserSwitchComplete(_arg02);
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg03 = data.readInt();
                    onForegroundProfileSwitch(_arg03);
                    return true;
                case 4:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg04 = data.readInt();
                    onLockedBootComplete(_arg04);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* loaded from: classes.dex */
        private static class Proxy implements IUserSwitchObserver {
            public static IUserSwitchObserver sDefaultImpl;
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

            @Override // android.app.IUserSwitchObserver
            public void onUserSwitching(int newUserId, IRemoteCallback reply) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(newUserId);
                    _data.writeStrongBinder(reply != null ? reply.asBinder() : null);
                    boolean _status = this.mRemote.transact(1, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onUserSwitching(newUserId, reply);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IUserSwitchObserver
            public void onUserSwitchComplete(int newUserId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(newUserId);
                    boolean _status = this.mRemote.transact(2, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onUserSwitchComplete(newUserId);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IUserSwitchObserver
            public void onForegroundProfileSwitch(int newProfileId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(newProfileId);
                    boolean _status = this.mRemote.transact(3, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onForegroundProfileSwitch(newProfileId);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IUserSwitchObserver
            public void onLockedBootComplete(int newUserId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(newUserId);
                    boolean _status = this.mRemote.transact(4, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onLockedBootComplete(newUserId);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IUserSwitchObserver impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static IUserSwitchObserver getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
