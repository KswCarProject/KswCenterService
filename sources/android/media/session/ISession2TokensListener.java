package android.media.session;

import android.media.Session2Token;
import android.p007os.Binder;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.RemoteException;
import java.util.List;

/* loaded from: classes3.dex */
public interface ISession2TokensListener extends IInterface {
    void onSession2TokensChanged(List<Session2Token> list) throws RemoteException;

    /* loaded from: classes3.dex */
    public static class Default implements ISession2TokensListener {
        @Override // android.media.session.ISession2TokensListener
        public void onSession2TokensChanged(List<Session2Token> tokens) throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* loaded from: classes3.dex */
    public static abstract class Stub extends Binder implements ISession2TokensListener {
        private static final String DESCRIPTOR = "android.media.session.ISession2TokensListener";
        static final int TRANSACTION_onSession2TokensChanged = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ISession2TokensListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof ISession2TokensListener)) {
                return (ISession2TokensListener) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            if (transactionCode == 1) {
                return "onSession2TokensChanged";
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
            List<Session2Token> _arg0 = data.createTypedArrayList(Session2Token.CREATOR);
            onSession2TokensChanged(_arg0);
            return true;
        }

        /* loaded from: classes3.dex */
        private static class Proxy implements ISession2TokensListener {
            public static ISession2TokensListener sDefaultImpl;
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

            @Override // android.media.session.ISession2TokensListener
            public void onSession2TokensChanged(List<Session2Token> tokens) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedList(tokens);
                    boolean _status = this.mRemote.transact(1, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onSession2TokensChanged(tokens);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(ISession2TokensListener impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static ISession2TokensListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
