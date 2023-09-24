package android.net.wifi.rtt;

import android.p007os.Binder;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.RemoteException;
import java.util.List;

/* loaded from: classes3.dex */
public interface IRttCallback extends IInterface {
    void onRangingFailure(int i) throws RemoteException;

    void onRangingResults(List<RangingResult> list) throws RemoteException;

    /* loaded from: classes3.dex */
    public static class Default implements IRttCallback {
        @Override // android.net.wifi.rtt.IRttCallback
        public void onRangingFailure(int status) throws RemoteException {
        }

        @Override // android.net.wifi.rtt.IRttCallback
        public void onRangingResults(List<RangingResult> results) throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* loaded from: classes3.dex */
    public static abstract class Stub extends Binder implements IRttCallback {
        private static final String DESCRIPTOR = "android.net.wifi.rtt.IRttCallback";
        static final int TRANSACTION_onRangingFailure = 1;
        static final int TRANSACTION_onRangingResults = 2;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IRttCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IRttCallback)) {
                return (IRttCallback) iin;
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
                    return "onRangingFailure";
                case 2:
                    return "onRangingResults";
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
                    onRangingFailure(_arg0);
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    List<RangingResult> _arg02 = data.createTypedArrayList(RangingResult.CREATOR);
                    onRangingResults(_arg02);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* loaded from: classes3.dex */
        private static class Proxy implements IRttCallback {
            public static IRttCallback sDefaultImpl;
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

            @Override // android.net.wifi.rtt.IRttCallback
            public void onRangingFailure(int status) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(status);
                    boolean _status = this.mRemote.transact(1, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onRangingFailure(status);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.rtt.IRttCallback
            public void onRangingResults(List<RangingResult> results) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedList(results);
                    boolean _status = this.mRemote.transact(2, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onRangingResults(results);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IRttCallback impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static IRttCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
