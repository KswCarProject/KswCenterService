package com.android.internal.p016os;

import android.p007os.Binder;
import android.p007os.Bundle;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.RemoteException;

/* renamed from: com.android.internal.os.IResultReceiver */
/* loaded from: classes4.dex */
public interface IResultReceiver extends IInterface {
    void send(int i, Bundle bundle) throws RemoteException;

    /* renamed from: com.android.internal.os.IResultReceiver$Default */
    /* loaded from: classes4.dex */
    public static class Default implements IResultReceiver {
        @Override // com.android.internal.p016os.IResultReceiver
        public void send(int resultCode, Bundle resultData) throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* renamed from: com.android.internal.os.IResultReceiver$Stub */
    /* loaded from: classes4.dex */
    public static abstract class Stub extends Binder implements IResultReceiver {
        private static final String DESCRIPTOR = "com.android.internal.os.IResultReceiver";
        static final int TRANSACTION_send = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IResultReceiver asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IResultReceiver)) {
                return (IResultReceiver) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            if (transactionCode == 1) {
                return "send";
            }
            return null;
        }

        @Override // android.p007os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.p007os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            Bundle _arg1;
            if (code != 1) {
                if (code == 1598968902) {
                    reply.writeString(DESCRIPTOR);
                    return true;
                }
                return super.onTransact(code, data, reply, flags);
            }
            data.enforceInterface(DESCRIPTOR);
            int _arg0 = data.readInt();
            if (data.readInt() != 0) {
                _arg1 = Bundle.CREATOR.createFromParcel(data);
            } else {
                _arg1 = null;
            }
            send(_arg0, _arg1);
            return true;
        }

        /* renamed from: com.android.internal.os.IResultReceiver$Stub$Proxy */
        /* loaded from: classes4.dex */
        private static class Proxy implements IResultReceiver {
            public static IResultReceiver sDefaultImpl;
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

            @Override // com.android.internal.p016os.IResultReceiver
            public void send(int resultCode, Bundle resultData) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(resultCode);
                    if (resultData != null) {
                        _data.writeInt(1);
                        resultData.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(1, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().send(resultCode, resultData);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IResultReceiver impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static IResultReceiver getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
