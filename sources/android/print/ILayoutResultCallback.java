package android.print;

import android.os.Binder;
import android.os.IBinder;
import android.os.ICancellationSignal;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.text.TextUtils;

public interface ILayoutResultCallback extends IInterface {
    void onLayoutCanceled(int i) throws RemoteException;

    void onLayoutFailed(CharSequence charSequence, int i) throws RemoteException;

    void onLayoutFinished(PrintDocumentInfo printDocumentInfo, boolean z, int i) throws RemoteException;

    void onLayoutStarted(ICancellationSignal iCancellationSignal, int i) throws RemoteException;

    public static class Default implements ILayoutResultCallback {
        public void onLayoutStarted(ICancellationSignal cancellation, int sequence) throws RemoteException {
        }

        public void onLayoutFinished(PrintDocumentInfo info, boolean changed, int sequence) throws RemoteException {
        }

        public void onLayoutFailed(CharSequence error, int sequence) throws RemoteException {
        }

        public void onLayoutCanceled(int sequence) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

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
            if (iin == null || !(iin instanceof ILayoutResultCallback)) {
                return new Proxy(obj);
            }
            return (ILayoutResultCallback) iin;
        }

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

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v4, resolved type: android.print.PrintDocumentInfo} */
        /* JADX WARNING: type inference failed for: r1v1 */
        /* JADX WARNING: type inference failed for: r1v8, types: [java.lang.CharSequence] */
        /* JADX WARNING: type inference failed for: r1v14 */
        /* JADX WARNING: type inference failed for: r1v15 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r6, android.os.Parcel r7, android.os.Parcel r8, int r9) throws android.os.RemoteException {
            /*
                r5 = this;
                java.lang.String r0 = "android.print.ILayoutResultCallback"
                r1 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r2 = 1
                if (r6 == r1) goto L_0x006e
                r1 = 0
                switch(r6) {
                    case 1: goto L_0x005b;
                    case 2: goto L_0x0037;
                    case 3: goto L_0x001c;
                    case 4: goto L_0x0011;
                    default: goto L_0x000c;
                }
            L_0x000c:
                boolean r1 = super.onTransact(r6, r7, r8, r9)
                return r1
            L_0x0011:
                r7.enforceInterface(r0)
                int r1 = r7.readInt()
                r5.onLayoutCanceled(r1)
                return r2
            L_0x001c:
                r7.enforceInterface(r0)
                int r3 = r7.readInt()
                if (r3 == 0) goto L_0x002e
                android.os.Parcelable$Creator<java.lang.CharSequence> r1 = android.text.TextUtils.CHAR_SEQUENCE_CREATOR
                java.lang.Object r1 = r1.createFromParcel(r7)
                java.lang.CharSequence r1 = (java.lang.CharSequence) r1
                goto L_0x002f
            L_0x002e:
            L_0x002f:
                int r3 = r7.readInt()
                r5.onLayoutFailed(r1, r3)
                return r2
            L_0x0037:
                r7.enforceInterface(r0)
                int r3 = r7.readInt()
                if (r3 == 0) goto L_0x0049
                android.os.Parcelable$Creator<android.print.PrintDocumentInfo> r1 = android.print.PrintDocumentInfo.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r7)
                android.print.PrintDocumentInfo r1 = (android.print.PrintDocumentInfo) r1
                goto L_0x004a
            L_0x0049:
            L_0x004a:
                int r3 = r7.readInt()
                if (r3 == 0) goto L_0x0052
                r3 = r2
                goto L_0x0053
            L_0x0052:
                r3 = 0
            L_0x0053:
                int r4 = r7.readInt()
                r5.onLayoutFinished(r1, r3, r4)
                return r2
            L_0x005b:
                r7.enforceInterface(r0)
                android.os.IBinder r1 = r7.readStrongBinder()
                android.os.ICancellationSignal r1 = android.os.ICancellationSignal.Stub.asInterface(r1)
                int r3 = r7.readInt()
                r5.onLayoutStarted(r1, r3)
                return r2
            L_0x006e:
                r8.writeString(r0)
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: android.print.ILayoutResultCallback.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements ILayoutResultCallback {
            public static ILayoutResultCallback sDefaultImpl;
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

            public void onLayoutStarted(ICancellationSignal cancellation, int sequence) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(cancellation != null ? cancellation.asBinder() : null);
                    _data.writeInt(sequence);
                    if (this.mRemote.transact(1, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onLayoutStarted(cancellation, sequence);
                    }
                } finally {
                    _data.recycle();
                }
            }

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
                    _data.writeInt(changed);
                    _data.writeInt(sequence);
                    if (this.mRemote.transact(2, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onLayoutFinished(info, changed, sequence);
                    }
                } finally {
                    _data.recycle();
                }
            }

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
                    if (this.mRemote.transact(3, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onLayoutFailed(error, sequence);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onLayoutCanceled(int sequence) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sequence);
                    if (this.mRemote.transact(4, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onLayoutCanceled(sequence);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(ILayoutResultCallback impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static ILayoutResultCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
