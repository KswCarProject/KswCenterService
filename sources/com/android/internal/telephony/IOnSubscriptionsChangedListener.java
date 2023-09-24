package com.android.internal.telephony;

import android.p007os.Binder;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.RemoteException;

/* loaded from: classes4.dex */
public interface IOnSubscriptionsChangedListener extends IInterface {
    void onSubscriptionsChanged() throws RemoteException;

    /* loaded from: classes4.dex */
    public static class Default implements IOnSubscriptionsChangedListener {
        @Override // com.android.internal.telephony.IOnSubscriptionsChangedListener
        public void onSubscriptionsChanged() throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* loaded from: classes4.dex */
    public static abstract class Stub extends Binder implements IOnSubscriptionsChangedListener {
        private static final String DESCRIPTOR = "com.android.internal.telephony.IOnSubscriptionsChangedListener";
        static final int TRANSACTION_onSubscriptionsChanged = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IOnSubscriptionsChangedListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IOnSubscriptionsChangedListener)) {
                return (IOnSubscriptionsChangedListener) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            if (transactionCode == 1) {
                return "onSubscriptionsChanged";
            }
            return null;
        }

        @Override // android.p007os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.p007os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code == 1) {
                data.enforceInterface(DESCRIPTOR);
                onSubscriptionsChanged();
                return true;
            } else if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            } else {
                return super.onTransact(code, data, reply, flags);
            }
        }

        /* loaded from: classes4.dex */
        private static class Proxy implements IOnSubscriptionsChangedListener {
            public static IOnSubscriptionsChangedListener sDefaultImpl;
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

            @Override // com.android.internal.telephony.IOnSubscriptionsChangedListener
            public void onSubscriptionsChanged() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(1, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onSubscriptionsChanged();
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IOnSubscriptionsChangedListener impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static IOnSubscriptionsChangedListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
