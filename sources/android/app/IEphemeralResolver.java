package android.app;

import android.p007os.Binder;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.IRemoteCallback;
import android.p007os.Parcel;
import android.p007os.RemoteException;

/* loaded from: classes.dex */
public interface IEphemeralResolver extends IInterface {
    void getEphemeralIntentFilterList(IRemoteCallback iRemoteCallback, String str, int i) throws RemoteException;

    void getEphemeralResolveInfoList(IRemoteCallback iRemoteCallback, int[] iArr, int i) throws RemoteException;

    /* loaded from: classes.dex */
    public static class Default implements IEphemeralResolver {
        @Override // android.app.IEphemeralResolver
        public void getEphemeralResolveInfoList(IRemoteCallback callback, int[] digestPrefix, int sequence) throws RemoteException {
        }

        @Override // android.app.IEphemeralResolver
        public void getEphemeralIntentFilterList(IRemoteCallback callback, String hostName, int sequence) throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* loaded from: classes.dex */
    public static abstract class Stub extends Binder implements IEphemeralResolver {
        private static final String DESCRIPTOR = "android.app.IEphemeralResolver";
        static final int TRANSACTION_getEphemeralIntentFilterList = 2;
        static final int TRANSACTION_getEphemeralResolveInfoList = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IEphemeralResolver asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IEphemeralResolver)) {
                return (IEphemeralResolver) iin;
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
                    return "getEphemeralResolveInfoList";
                case 2:
                    return "getEphemeralIntentFilterList";
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
                    IRemoteCallback _arg0 = IRemoteCallback.Stub.asInterface(data.readStrongBinder());
                    int[] _arg1 = data.createIntArray();
                    int _arg2 = data.readInt();
                    getEphemeralResolveInfoList(_arg0, _arg1, _arg2);
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    IRemoteCallback _arg02 = IRemoteCallback.Stub.asInterface(data.readStrongBinder());
                    String _arg12 = data.readString();
                    int _arg22 = data.readInt();
                    getEphemeralIntentFilterList(_arg02, _arg12, _arg22);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* loaded from: classes.dex */
        private static class Proxy implements IEphemeralResolver {
            public static IEphemeralResolver sDefaultImpl;
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

            @Override // android.app.IEphemeralResolver
            public void getEphemeralResolveInfoList(IRemoteCallback callback, int[] digestPrefix, int sequence) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    _data.writeIntArray(digestPrefix);
                    _data.writeInt(sequence);
                    boolean _status = this.mRemote.transact(1, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().getEphemeralResolveInfoList(callback, digestPrefix, sequence);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IEphemeralResolver
            public void getEphemeralIntentFilterList(IRemoteCallback callback, String hostName, int sequence) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    _data.writeString(hostName);
                    _data.writeInt(sequence);
                    boolean _status = this.mRemote.transact(2, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().getEphemeralIntentFilterList(callback, hostName, sequence);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IEphemeralResolver impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static IEphemeralResolver getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
