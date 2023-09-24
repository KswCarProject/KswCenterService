package android.hardware;

import android.p007os.Binder;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.RemoteException;

/* loaded from: classes.dex */
public interface ISensorPrivacyListener extends IInterface {
    void onSensorPrivacyChanged(boolean z) throws RemoteException;

    /* loaded from: classes.dex */
    public static class Default implements ISensorPrivacyListener {
        @Override // android.hardware.ISensorPrivacyListener
        public void onSensorPrivacyChanged(boolean enabled) throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* loaded from: classes.dex */
    public static abstract class Stub extends Binder implements ISensorPrivacyListener {
        private static final String DESCRIPTOR = "android.hardware.ISensorPrivacyListener";
        static final int TRANSACTION_onSensorPrivacyChanged = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ISensorPrivacyListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof ISensorPrivacyListener)) {
                return (ISensorPrivacyListener) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            if (transactionCode == 1) {
                return "onSensorPrivacyChanged";
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
            onSensorPrivacyChanged(_arg0);
            return true;
        }

        /* loaded from: classes.dex */
        private static class Proxy implements ISensorPrivacyListener {
            public static ISensorPrivacyListener sDefaultImpl;
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

            @Override // android.hardware.ISensorPrivacyListener
            public void onSensorPrivacyChanged(boolean enabled) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(enabled ? 1 : 0);
                    boolean _status = this.mRemote.transact(1, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onSensorPrivacyChanged(enabled);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(ISensorPrivacyListener impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static ISensorPrivacyListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
