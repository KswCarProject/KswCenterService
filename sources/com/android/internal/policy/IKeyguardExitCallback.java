package com.android.internal.policy;

import android.p007os.Binder;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.RemoteException;

/* loaded from: classes4.dex */
public interface IKeyguardExitCallback extends IInterface {
    void onKeyguardExitResult(boolean z) throws RemoteException;

    /* loaded from: classes4.dex */
    public static class Default implements IKeyguardExitCallback {
        @Override // com.android.internal.policy.IKeyguardExitCallback
        public void onKeyguardExitResult(boolean success) throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* loaded from: classes4.dex */
    public static abstract class Stub extends Binder implements IKeyguardExitCallback {
        private static final String DESCRIPTOR = "com.android.internal.policy.IKeyguardExitCallback";
        static final int TRANSACTION_onKeyguardExitResult = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IKeyguardExitCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IKeyguardExitCallback)) {
                return (IKeyguardExitCallback) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            if (transactionCode == 1) {
                return "onKeyguardExitResult";
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
            boolean _arg0 = data.readInt() != 0;
            onKeyguardExitResult(_arg0);
            return true;
        }

        /* loaded from: classes4.dex */
        private static class Proxy implements IKeyguardExitCallback {
            public static IKeyguardExitCallback sDefaultImpl;
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

            @Override // com.android.internal.policy.IKeyguardExitCallback
            public void onKeyguardExitResult(boolean success) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(success ? 1 : 0);
                    boolean _status = this.mRemote.transact(1, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onKeyguardExitResult(success);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IKeyguardExitCallback impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static IKeyguardExitCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
