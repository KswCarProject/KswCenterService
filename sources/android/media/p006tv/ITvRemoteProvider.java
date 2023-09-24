package android.media.p006tv;

import android.media.p006tv.ITvRemoteServiceInput;
import android.p007os.Binder;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.RemoteException;

/* renamed from: android.media.tv.ITvRemoteProvider */
/* loaded from: classes3.dex */
public interface ITvRemoteProvider extends IInterface {
    void onInputBridgeConnected(IBinder iBinder) throws RemoteException;

    void setRemoteServiceInputSink(ITvRemoteServiceInput iTvRemoteServiceInput) throws RemoteException;

    /* renamed from: android.media.tv.ITvRemoteProvider$Default */
    /* loaded from: classes3.dex */
    public static class Default implements ITvRemoteProvider {
        @Override // android.media.p006tv.ITvRemoteProvider
        public void setRemoteServiceInputSink(ITvRemoteServiceInput tvServiceInput) throws RemoteException {
        }

        @Override // android.media.p006tv.ITvRemoteProvider
        public void onInputBridgeConnected(IBinder token) throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* renamed from: android.media.tv.ITvRemoteProvider$Stub */
    /* loaded from: classes3.dex */
    public static abstract class Stub extends Binder implements ITvRemoteProvider {
        private static final String DESCRIPTOR = "android.media.tv.ITvRemoteProvider";
        static final int TRANSACTION_onInputBridgeConnected = 2;
        static final int TRANSACTION_setRemoteServiceInputSink = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ITvRemoteProvider asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof ITvRemoteProvider)) {
                return (ITvRemoteProvider) iin;
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
                    return "setRemoteServiceInputSink";
                case 2:
                    return "onInputBridgeConnected";
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
                    ITvRemoteServiceInput _arg0 = ITvRemoteServiceInput.Stub.asInterface(data.readStrongBinder());
                    setRemoteServiceInputSink(_arg0);
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    IBinder _arg02 = data.readStrongBinder();
                    onInputBridgeConnected(_arg02);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* renamed from: android.media.tv.ITvRemoteProvider$Stub$Proxy */
        /* loaded from: classes3.dex */
        private static class Proxy implements ITvRemoteProvider {
            public static ITvRemoteProvider sDefaultImpl;
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

            @Override // android.media.p006tv.ITvRemoteProvider
            public void setRemoteServiceInputSink(ITvRemoteServiceInput tvServiceInput) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(tvServiceInput != null ? tvServiceInput.asBinder() : null);
                    boolean _status = this.mRemote.transact(1, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setRemoteServiceInputSink(tvServiceInput);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.p006tv.ITvRemoteProvider
            public void onInputBridgeConnected(IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    boolean _status = this.mRemote.transact(2, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onInputBridgeConnected(token);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(ITvRemoteProvider impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static ITvRemoteProvider getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
