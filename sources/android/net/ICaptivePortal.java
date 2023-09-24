package android.net;

import android.p007os.Binder;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.RemoteException;

/* loaded from: classes3.dex */
public interface ICaptivePortal extends IInterface {
    void appResponse(int i) throws RemoteException;

    void logEvent(int i, String str) throws RemoteException;

    /* loaded from: classes3.dex */
    public static class Default implements ICaptivePortal {
        @Override // android.net.ICaptivePortal
        public void appResponse(int response) throws RemoteException {
        }

        @Override // android.net.ICaptivePortal
        public void logEvent(int eventId, String packageName) throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* loaded from: classes3.dex */
    public static abstract class Stub extends Binder implements ICaptivePortal {
        private static final String DESCRIPTOR = "android.net.ICaptivePortal";
        static final int TRANSACTION_appResponse = 1;
        static final int TRANSACTION_logEvent = 2;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ICaptivePortal asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof ICaptivePortal)) {
                return (ICaptivePortal) iin;
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
                    return "appResponse";
                case 2:
                    return "logEvent";
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
                    int _arg0 = data.readInt();
                    appResponse(_arg0);
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg02 = data.readInt();
                    String _arg1 = data.readString();
                    logEvent(_arg02, _arg1);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* loaded from: classes3.dex */
        private static class Proxy implements ICaptivePortal {
            public static ICaptivePortal sDefaultImpl;
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

            @Override // android.net.ICaptivePortal
            public void appResponse(int response) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(response);
                    boolean _status = this.mRemote.transact(1, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().appResponse(response);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.net.ICaptivePortal
            public void logEvent(int eventId, String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(eventId);
                    _data.writeString(packageName);
                    boolean _status = this.mRemote.transact(2, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().logEvent(eventId, packageName);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(ICaptivePortal impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static ICaptivePortal getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
