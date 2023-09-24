package android.print;

import android.p007os.Binder;
import android.p007os.IBinder;
import android.p007os.ICancellationSignal;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.RemoteException;
import android.text.TextUtils;

/* loaded from: classes3.dex */
public interface ILayoutResultCallback extends IInterface {
    void onLayoutCanceled(int i) throws RemoteException;

    void onLayoutFailed(CharSequence charSequence, int i) throws RemoteException;

    void onLayoutFinished(PrintDocumentInfo printDocumentInfo, boolean z, int i) throws RemoteException;

    void onLayoutStarted(ICancellationSignal iCancellationSignal, int i) throws RemoteException;

    /* loaded from: classes3.dex */
    public static class Default implements ILayoutResultCallback {
        @Override // android.print.ILayoutResultCallback
        public void onLayoutStarted(ICancellationSignal cancellation, int sequence) throws RemoteException {
        }

        @Override // android.print.ILayoutResultCallback
        public void onLayoutFinished(PrintDocumentInfo info, boolean changed, int sequence) throws RemoteException {
        }

        @Override // android.print.ILayoutResultCallback
        public void onLayoutFailed(CharSequence error, int sequence) throws RemoteException {
        }

        @Override // android.print.ILayoutResultCallback
        public void onLayoutCanceled(int sequence) throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* loaded from: classes3.dex */
    public static abstract class Stub extends Binder implements ILayoutResultCallback {
        private static final String DESCRIPTOR = "android.print.ILayoutResultCallback";
        static final int TRANSACTION_onLayoutCanceled = 4;
        static final int TRANSACTION_onLayoutFailed = 3;
        static final int TRANSACTION_onLayoutFinished = 2;
        static final int TRANSACTION_onLayoutStarted = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ILayoutResultCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof ILayoutResultCallback)) {
                return (ILayoutResultCallback) iin;
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
                    return "onLayoutStarted";
                case 2:
                    return "onLayoutFinished";
                case 3:
                    return "onLayoutFailed";
                case 4:
                    return "onLayoutCanceled";
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
                    ICancellationSignal _arg0 = ICancellationSignal.Stub.asInterface(data.readStrongBinder());
                    int _arg1 = data.readInt();
                    onLayoutStarted(_arg0, _arg1);
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    PrintDocumentInfo _arg02 = data.readInt() != 0 ? PrintDocumentInfo.CREATOR.createFromParcel(data) : null;
                    boolean _arg12 = data.readInt() != 0;
                    int _arg2 = data.readInt();
                    onLayoutFinished(_arg02, _arg12, _arg2);
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    CharSequence _arg03 = data.readInt() != 0 ? TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(data) : null;
                    int _arg13 = data.readInt();
                    onLayoutFailed(_arg03, _arg13);
                    return true;
                case 4:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg04 = data.readInt();
                    onLayoutCanceled(_arg04);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* loaded from: classes3.dex */
        private static class Proxy implements ILayoutResultCallback {
            public static ILayoutResultCallback sDefaultImpl;
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

            @Override // android.print.ILayoutResultCallback
            public void onLayoutStarted(ICancellationSignal cancellation, int sequence) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(cancellation != null ? cancellation.asBinder() : null);
                    _data.writeInt(sequence);
                    boolean _status = this.mRemote.transact(1, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onLayoutStarted(cancellation, sequence);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.print.ILayoutResultCallback
            public void onLayoutFinished(PrintDocumentInfo info, boolean changed, int sequence) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (info != null) {
                        _data.writeInt(1);
                        info.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(changed ? 1 : 0);
                    _data.writeInt(sequence);
                    boolean _status = this.mRemote.transact(2, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onLayoutFinished(info, changed, sequence);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.print.ILayoutResultCallback
            public void onLayoutFailed(CharSequence error, int sequence) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (error != null) {
                        _data.writeInt(1);
                        TextUtils.writeToParcel(error, _data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(sequence);
                    boolean _status = this.mRemote.transact(3, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onLayoutFailed(error, sequence);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.print.ILayoutResultCallback
            public void onLayoutCanceled(int sequence) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sequence);
                    boolean _status = this.mRemote.transact(4, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onLayoutCanceled(sequence);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(ILayoutResultCallback impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static ILayoutResultCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
