package android.content.p002pm;

import android.p007os.Binder;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.RemoteException;

/* renamed from: android.content.pm.IDexModuleRegisterCallback */
/* loaded from: classes.dex */
public interface IDexModuleRegisterCallback extends IInterface {
    void onDexModuleRegistered(String str, boolean z, String str2) throws RemoteException;

    /* renamed from: android.content.pm.IDexModuleRegisterCallback$Default */
    /* loaded from: classes.dex */
    public static class Default implements IDexModuleRegisterCallback {
        @Override // android.content.p002pm.IDexModuleRegisterCallback
        public void onDexModuleRegistered(String dexModulePath, boolean success, String message) throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* renamed from: android.content.pm.IDexModuleRegisterCallback$Stub */
    /* loaded from: classes.dex */
    public static abstract class Stub extends Binder implements IDexModuleRegisterCallback {
        private static final String DESCRIPTOR = "android.content.pm.IDexModuleRegisterCallback";
        static final int TRANSACTION_onDexModuleRegistered = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IDexModuleRegisterCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IDexModuleRegisterCallback)) {
                return (IDexModuleRegisterCallback) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            if (transactionCode == 1) {
                return "onDexModuleRegistered";
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
            String _arg0 = data.readString();
            boolean _arg1 = data.readInt() != 0;
            String _arg2 = data.readString();
            onDexModuleRegistered(_arg0, _arg1, _arg2);
            return true;
        }

        /* renamed from: android.content.pm.IDexModuleRegisterCallback$Stub$Proxy */
        /* loaded from: classes.dex */
        private static class Proxy implements IDexModuleRegisterCallback {
            public static IDexModuleRegisterCallback sDefaultImpl;
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

            @Override // android.content.p002pm.IDexModuleRegisterCallback
            public void onDexModuleRegistered(String dexModulePath, boolean success, String message) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(dexModulePath);
                    _data.writeInt(success ? 1 : 0);
                    _data.writeString(message);
                    boolean _status = this.mRemote.transact(1, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onDexModuleRegistered(dexModulePath, success, message);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IDexModuleRegisterCallback impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static IDexModuleRegisterCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
