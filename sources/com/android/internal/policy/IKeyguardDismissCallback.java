package com.android.internal.policy;

import android.p007os.Binder;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.RemoteException;

/* loaded from: classes4.dex */
public interface IKeyguardDismissCallback extends IInterface {
    void onDismissCancelled() throws RemoteException;

    void onDismissError() throws RemoteException;

    void onDismissSucceeded() throws RemoteException;

    /* loaded from: classes4.dex */
    public static class Default implements IKeyguardDismissCallback {
        @Override // com.android.internal.policy.IKeyguardDismissCallback
        public void onDismissError() throws RemoteException {
        }

        @Override // com.android.internal.policy.IKeyguardDismissCallback
        public void onDismissSucceeded() throws RemoteException {
        }

        @Override // com.android.internal.policy.IKeyguardDismissCallback
        public void onDismissCancelled() throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* loaded from: classes4.dex */
    public static abstract class Stub extends Binder implements IKeyguardDismissCallback {
        private static final String DESCRIPTOR = "com.android.internal.policy.IKeyguardDismissCallback";
        static final int TRANSACTION_onDismissCancelled = 3;
        static final int TRANSACTION_onDismissError = 1;
        static final int TRANSACTION_onDismissSucceeded = 2;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IKeyguardDismissCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IKeyguardDismissCallback)) {
                return (IKeyguardDismissCallback) iin;
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
                    return "onDismissError";
                case 2:
                    return "onDismissSucceeded";
                case 3:
                    return "onDismissCancelled";
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
                    onDismissError();
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    onDismissSucceeded();
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    onDismissCancelled();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* loaded from: classes4.dex */
        private static class Proxy implements IKeyguardDismissCallback {
            public static IKeyguardDismissCallback sDefaultImpl;
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

            @Override // com.android.internal.policy.IKeyguardDismissCallback
            public void onDismissError() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(1, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onDismissError();
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.policy.IKeyguardDismissCallback
            public void onDismissSucceeded() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(2, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onDismissSucceeded();
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.policy.IKeyguardDismissCallback
            public void onDismissCancelled() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(3, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onDismissCancelled();
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IKeyguardDismissCallback impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static IKeyguardDismissCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
