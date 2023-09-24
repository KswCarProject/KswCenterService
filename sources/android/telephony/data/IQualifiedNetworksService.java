package android.telephony.data;

import android.p007os.Binder;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.RemoteException;
import android.telephony.data.IQualifiedNetworksServiceCallback;

/* loaded from: classes3.dex */
public interface IQualifiedNetworksService extends IInterface {
    void createNetworkAvailabilityProvider(int i, IQualifiedNetworksServiceCallback iQualifiedNetworksServiceCallback) throws RemoteException;

    void removeNetworkAvailabilityProvider(int i) throws RemoteException;

    /* loaded from: classes3.dex */
    public static class Default implements IQualifiedNetworksService {
        @Override // android.telephony.data.IQualifiedNetworksService
        public void createNetworkAvailabilityProvider(int slotId, IQualifiedNetworksServiceCallback callback) throws RemoteException {
        }

        @Override // android.telephony.data.IQualifiedNetworksService
        public void removeNetworkAvailabilityProvider(int slotId) throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* loaded from: classes3.dex */
    public static abstract class Stub extends Binder implements IQualifiedNetworksService {
        private static final String DESCRIPTOR = "android.telephony.data.IQualifiedNetworksService";
        static final int TRANSACTION_createNetworkAvailabilityProvider = 1;
        static final int TRANSACTION_removeNetworkAvailabilityProvider = 2;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IQualifiedNetworksService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IQualifiedNetworksService)) {
                return (IQualifiedNetworksService) iin;
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
                    return "createNetworkAvailabilityProvider";
                case 2:
                    return "removeNetworkAvailabilityProvider";
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
                    IQualifiedNetworksServiceCallback _arg1 = IQualifiedNetworksServiceCallback.Stub.asInterface(data.readStrongBinder());
                    createNetworkAvailabilityProvider(_arg0, _arg1);
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg02 = data.readInt();
                    removeNetworkAvailabilityProvider(_arg02);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* loaded from: classes3.dex */
        private static class Proxy implements IQualifiedNetworksService {
            public static IQualifiedNetworksService sDefaultImpl;
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

            @Override // android.telephony.data.IQualifiedNetworksService
            public void createNetworkAvailabilityProvider(int slotId, IQualifiedNetworksServiceCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(slotId);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    boolean _status = this.mRemote.transact(1, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().createNetworkAvailabilityProvider(slotId, callback);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.telephony.data.IQualifiedNetworksService
            public void removeNetworkAvailabilityProvider(int slotId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(slotId);
                    boolean _status = this.mRemote.transact(2, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeNetworkAvailabilityProvider(slotId);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IQualifiedNetworksService impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static IQualifiedNetworksService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
