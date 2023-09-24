package android.service.resolver;

import android.p007os.Binder;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.RemoteException;
import android.service.resolver.IResolverRankerResult;
import java.util.List;

/* loaded from: classes3.dex */
public interface IResolverRankerService extends IInterface {
    void predict(List<ResolverTarget> list, IResolverRankerResult iResolverRankerResult) throws RemoteException;

    void train(List<ResolverTarget> list, int i) throws RemoteException;

    /* loaded from: classes3.dex */
    public static class Default implements IResolverRankerService {
        @Override // android.service.resolver.IResolverRankerService
        public void predict(List<ResolverTarget> targets, IResolverRankerResult result) throws RemoteException {
        }

        @Override // android.service.resolver.IResolverRankerService
        public void train(List<ResolverTarget> targets, int selectedPosition) throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* loaded from: classes3.dex */
    public static abstract class Stub extends Binder implements IResolverRankerService {
        private static final String DESCRIPTOR = "android.service.resolver.IResolverRankerService";
        static final int TRANSACTION_predict = 1;
        static final int TRANSACTION_train = 2;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IResolverRankerService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IResolverRankerService)) {
                return (IResolverRankerService) iin;
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
                    return "predict";
                case 2:
                    return "train";
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
                    List<ResolverTarget> _arg0 = data.createTypedArrayList(ResolverTarget.CREATOR);
                    IResolverRankerResult _arg1 = IResolverRankerResult.Stub.asInterface(data.readStrongBinder());
                    predict(_arg0, _arg1);
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    List<ResolverTarget> _arg02 = data.createTypedArrayList(ResolverTarget.CREATOR);
                    int _arg12 = data.readInt();
                    train(_arg02, _arg12);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* loaded from: classes3.dex */
        private static class Proxy implements IResolverRankerService {
            public static IResolverRankerService sDefaultImpl;
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

            @Override // android.service.resolver.IResolverRankerService
            public void predict(List<ResolverTarget> targets, IResolverRankerResult result) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedList(targets);
                    _data.writeStrongBinder(result != null ? result.asBinder() : null);
                    boolean _status = this.mRemote.transact(1, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().predict(targets, result);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.resolver.IResolverRankerService
            public void train(List<ResolverTarget> targets, int selectedPosition) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedList(targets);
                    _data.writeInt(selectedPosition);
                    boolean _status = this.mRemote.transact(2, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().train(targets, selectedPosition);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IResolverRankerService impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static IResolverRankerService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
