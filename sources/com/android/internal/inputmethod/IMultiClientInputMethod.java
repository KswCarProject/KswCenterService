package com.android.internal.inputmethod;

import android.content.ContentResolver;
import android.p007os.Binder;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.RemoteException;
import com.android.internal.inputmethod.IMultiClientInputMethodPrivilegedOperations;

/* loaded from: classes4.dex */
public interface IMultiClientInputMethod extends IInterface {
    void addClient(int i, int i2, int i3, int i4) throws RemoteException;

    void initialize(IMultiClientInputMethodPrivilegedOperations iMultiClientInputMethodPrivilegedOperations) throws RemoteException;

    void removeClient(int i) throws RemoteException;

    /* loaded from: classes4.dex */
    public static class Default implements IMultiClientInputMethod {
        @Override // com.android.internal.inputmethod.IMultiClientInputMethod
        public void initialize(IMultiClientInputMethodPrivilegedOperations privOps) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IMultiClientInputMethod
        public void addClient(int clientId, int uid, int pid, int selfReportedDisplayId) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IMultiClientInputMethod
        public void removeClient(int clientId) throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* loaded from: classes4.dex */
    public static abstract class Stub extends Binder implements IMultiClientInputMethod {
        private static final String DESCRIPTOR = "com.android.internal.inputmethod.IMultiClientInputMethod";
        static final int TRANSACTION_addClient = 2;
        static final int TRANSACTION_initialize = 1;
        static final int TRANSACTION_removeClient = 3;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IMultiClientInputMethod asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IMultiClientInputMethod)) {
                return (IMultiClientInputMethod) iin;
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
                    return ContentResolver.SYNC_EXTRAS_INITIALIZE;
                case 2:
                    return "addClient";
                case 3:
                    return "removeClient";
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
                    IMultiClientInputMethodPrivilegedOperations _arg0 = IMultiClientInputMethodPrivilegedOperations.Stub.asInterface(data.readStrongBinder());
                    initialize(_arg0);
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg02 = data.readInt();
                    int _arg1 = data.readInt();
                    int _arg2 = data.readInt();
                    int _arg3 = data.readInt();
                    addClient(_arg02, _arg1, _arg2, _arg3);
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg03 = data.readInt();
                    removeClient(_arg03);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* loaded from: classes4.dex */
        private static class Proxy implements IMultiClientInputMethod {
            public static IMultiClientInputMethod sDefaultImpl;
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

            @Override // com.android.internal.inputmethod.IMultiClientInputMethod
            public void initialize(IMultiClientInputMethodPrivilegedOperations privOps) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(privOps != null ? privOps.asBinder() : null);
                    boolean _status = this.mRemote.transact(1, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().initialize(privOps);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IMultiClientInputMethod
            public void addClient(int clientId, int uid, int pid, int selfReportedDisplayId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(clientId);
                    _data.writeInt(uid);
                    _data.writeInt(pid);
                    _data.writeInt(selfReportedDisplayId);
                    boolean _status = this.mRemote.transact(2, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().addClient(clientId, uid, pid, selfReportedDisplayId);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IMultiClientInputMethod
            public void removeClient(int clientId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(clientId);
                    boolean _status = this.mRemote.transact(3, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeClient(clientId);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IMultiClientInputMethod impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static IMultiClientInputMethod getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
