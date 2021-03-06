package android.media.soundtrigger;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface ISoundTriggerDetectionServiceClient extends IInterface {
    void onOpFinished(int i) throws RemoteException;

    public static class Default implements ISoundTriggerDetectionServiceClient {
        public void onOpFinished(int opId) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ISoundTriggerDetectionServiceClient {
        private static final String DESCRIPTOR = "android.media.soundtrigger.ISoundTriggerDetectionServiceClient";
        static final int TRANSACTION_onOpFinished = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ISoundTriggerDetectionServiceClient asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof ISoundTriggerDetectionServiceClient)) {
                return new Proxy(obj);
            }
            return (ISoundTriggerDetectionServiceClient) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            if (transactionCode != 1) {
                return null;
            }
            return "onOpFinished";
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code == 1) {
                data.enforceInterface(DESCRIPTOR);
                onOpFinished(data.readInt());
                return true;
            } else if (code != 1598968902) {
                return super.onTransact(code, data, reply, flags);
            } else {
                reply.writeString(DESCRIPTOR);
                return true;
            }
        }

        private static class Proxy implements ISoundTriggerDetectionServiceClient {
            public static ISoundTriggerDetectionServiceClient sDefaultImpl;
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

            public void onOpFinished(int opId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(opId);
                    if (this.mRemote.transact(1, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onOpFinished(opId);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(ISoundTriggerDetectionServiceClient impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static ISoundTriggerDetectionServiceClient getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
