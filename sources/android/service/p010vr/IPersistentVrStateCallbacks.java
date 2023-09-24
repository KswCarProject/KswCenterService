package android.service.p010vr;

import android.p007os.Binder;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.RemoteException;

/* renamed from: android.service.vr.IPersistentVrStateCallbacks */
/* loaded from: classes3.dex */
public interface IPersistentVrStateCallbacks extends IInterface {
    void onPersistentVrStateChanged(boolean z) throws RemoteException;

    /* renamed from: android.service.vr.IPersistentVrStateCallbacks$Default */
    /* loaded from: classes3.dex */
    public static class Default implements IPersistentVrStateCallbacks {
        @Override // android.service.p010vr.IPersistentVrStateCallbacks
        public void onPersistentVrStateChanged(boolean enabled) throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* renamed from: android.service.vr.IPersistentVrStateCallbacks$Stub */
    /* loaded from: classes3.dex */
    public static abstract class Stub extends Binder implements IPersistentVrStateCallbacks {
        private static final String DESCRIPTOR = "android.service.vr.IPersistentVrStateCallbacks";
        static final int TRANSACTION_onPersistentVrStateChanged = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IPersistentVrStateCallbacks asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IPersistentVrStateCallbacks)) {
                return (IPersistentVrStateCallbacks) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            if (transactionCode == 1) {
                return "onPersistentVrStateChanged";
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
            onPersistentVrStateChanged(_arg0);
            return true;
        }

        /* renamed from: android.service.vr.IPersistentVrStateCallbacks$Stub$Proxy */
        /* loaded from: classes3.dex */
        private static class Proxy implements IPersistentVrStateCallbacks {
            public static IPersistentVrStateCallbacks sDefaultImpl;
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

            @Override // android.service.p010vr.IPersistentVrStateCallbacks
            public void onPersistentVrStateChanged(boolean enabled) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(enabled ? 1 : 0);
                    boolean _status = this.mRemote.transact(1, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onPersistentVrStateChanged(enabled);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IPersistentVrStateCallbacks impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static IPersistentVrStateCallbacks getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}