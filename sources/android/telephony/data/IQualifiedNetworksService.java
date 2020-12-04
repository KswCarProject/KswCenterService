package android.telephony.data;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.telephony.data.IQualifiedNetworksServiceCallback;

public interface IQualifiedNetworksService extends IInterface {
    void createNetworkAvailabilityProvider(int i, IQualifiedNetworksServiceCallback iQualifiedNetworksServiceCallback) throws RemoteException;

    void removeNetworkAvailabilityProvider(int i) throws RemoteException;

    public static class Default implements IQualifiedNetworksService {
        public void createNetworkAvailabilityProvider(int slotId, IQualifiedNetworksServiceCallback callback) throws RemoteException {
        }

        public void removeNetworkAvailabilityProvider(int slotId) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

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
            if (iin == null || !(iin instanceof IQualifiedNetworksService)) {
                return new Proxy(obj);
            }
            return (IQualifiedNetworksService) iin;
        }

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

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code != 1598968902) {
                switch (code) {
                    case 1:
                        data.enforceInterface(DESCRIPTOR);
                        createNetworkAvailabilityProvider(data.readInt(), IQualifiedNetworksServiceCallback.Stub.asInterface(data.readStrongBinder()));
                        return true;
                    case 2:
                        data.enforceInterface(DESCRIPTOR);
                        removeNetworkAvailabilityProvider(data.readInt());
                        return true;
                    default:
                        return super.onTransact(code, data, reply, flags);
                }
            } else {
                reply.writeString(DESCRIPTOR);
                return true;
            }
        }

        private static class Proxy implements IQualifiedNetworksService {
            public static IQualifiedNetworksService sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            public void createNetworkAvailabilityProvider(int slotId, IQualifiedNetworksServiceCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(slotId);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(1, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().createNetworkAvailabilityProvider(slotId, callback);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void removeNetworkAvailabilityProvider(int slotId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(slotId);
                    if (this.mRemote.transact(2, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().removeNetworkAvailabilityProvider(slotId);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IQualifiedNetworksService impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IQualifiedNetworksService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
