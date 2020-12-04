package android.print;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.provider.Telephony;

public interface IPrintDocumentAdapter extends IInterface {
    void finish() throws RemoteException;

    void kill(String str) throws RemoteException;

    void layout(PrintAttributes printAttributes, PrintAttributes printAttributes2, ILayoutResultCallback iLayoutResultCallback, Bundle bundle, int i) throws RemoteException;

    void setObserver(IPrintDocumentAdapterObserver iPrintDocumentAdapterObserver) throws RemoteException;

    void start() throws RemoteException;

    void write(PageRange[] pageRangeArr, ParcelFileDescriptor parcelFileDescriptor, IWriteResultCallback iWriteResultCallback, int i) throws RemoteException;

    public static class Default implements IPrintDocumentAdapter {
        public void setObserver(IPrintDocumentAdapterObserver observer) throws RemoteException {
        }

        public void start() throws RemoteException {
        }

        public void layout(PrintAttributes oldAttributes, PrintAttributes newAttributes, ILayoutResultCallback callback, Bundle metadata, int sequence) throws RemoteException {
        }

        public void write(PageRange[] pages, ParcelFileDescriptor fd, IWriteResultCallback callback, int sequence) throws RemoteException {
        }

        public void finish() throws RemoteException {
        }

        public void kill(String reason) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IPrintDocumentAdapter {
        private static final String DESCRIPTOR = "android.print.IPrintDocumentAdapter";
        static final int TRANSACTION_finish = 5;
        static final int TRANSACTION_kill = 6;
        static final int TRANSACTION_layout = 3;
        static final int TRANSACTION_setObserver = 1;
        static final int TRANSACTION_start = 2;
        static final int TRANSACTION_write = 4;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IPrintDocumentAdapter asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IPrintDocumentAdapter)) {
                return new Proxy(obj);
            }
            return (IPrintDocumentAdapter) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "setObserver";
                case 2:
                    return Telephony.BaseMmsColumns.START;
                case 3:
                    return TtmlUtils.TAG_LAYOUT;
                case 4:
                    return "write";
                case 5:
                    return "finish";
                case 6:
                    return "kill";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v1, resolved type: android.os.ParcelFileDescriptor} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v4, resolved type: android.os.ParcelFileDescriptor} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v0, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v9, resolved type: android.os.ParcelFileDescriptor} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v15, resolved type: android.os.ParcelFileDescriptor} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v16, resolved type: android.os.ParcelFileDescriptor} */
        /* JADX WARNING: type inference failed for: r1v8, types: [android.os.Bundle] */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r11, android.os.Parcel r12, android.os.Parcel r13, int r14) throws android.os.RemoteException {
            /*
                r10 = this;
                java.lang.String r0 = "android.print.IPrintDocumentAdapter"
                r1 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r2 = 1
                if (r11 == r1) goto L_0x00ad
                r1 = 0
                switch(r11) {
                    case 1: goto L_0x009e;
                    case 2: goto L_0x0097;
                    case 3: goto L_0x004e;
                    case 4: goto L_0x0023;
                    case 5: goto L_0x001c;
                    case 6: goto L_0x0011;
                    default: goto L_0x000c;
                }
            L_0x000c:
                boolean r1 = super.onTransact(r11, r12, r13, r14)
                return r1
            L_0x0011:
                r12.enforceInterface(r0)
                java.lang.String r1 = r12.readString()
                r10.kill(r1)
                return r2
            L_0x001c:
                r12.enforceInterface(r0)
                r10.finish()
                return r2
            L_0x0023:
                r12.enforceInterface(r0)
                android.os.Parcelable$Creator<android.print.PageRange> r3 = android.print.PageRange.CREATOR
                java.lang.Object[] r3 = r12.createTypedArray(r3)
                android.print.PageRange[] r3 = (android.print.PageRange[]) r3
                int r4 = r12.readInt()
                if (r4 == 0) goto L_0x003d
                android.os.Parcelable$Creator<android.os.ParcelFileDescriptor> r1 = android.os.ParcelFileDescriptor.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r12)
                android.os.ParcelFileDescriptor r1 = (android.os.ParcelFileDescriptor) r1
                goto L_0x003e
            L_0x003d:
            L_0x003e:
                android.os.IBinder r4 = r12.readStrongBinder()
                android.print.IWriteResultCallback r4 = android.print.IWriteResultCallback.Stub.asInterface(r4)
                int r5 = r12.readInt()
                r10.write(r3, r1, r4, r5)
                return r2
            L_0x004e:
                r12.enforceInterface(r0)
                int r3 = r12.readInt()
                if (r3 == 0) goto L_0x0061
                android.os.Parcelable$Creator<android.print.PrintAttributes> r3 = android.print.PrintAttributes.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r12)
                android.print.PrintAttributes r3 = (android.print.PrintAttributes) r3
                r5 = r3
                goto L_0x0062
            L_0x0061:
                r5 = r1
            L_0x0062:
                int r3 = r12.readInt()
                if (r3 == 0) goto L_0x0072
                android.os.Parcelable$Creator<android.print.PrintAttributes> r3 = android.print.PrintAttributes.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r12)
                android.print.PrintAttributes r3 = (android.print.PrintAttributes) r3
                r6 = r3
                goto L_0x0073
            L_0x0072:
                r6 = r1
            L_0x0073:
                android.os.IBinder r3 = r12.readStrongBinder()
                android.print.ILayoutResultCallback r3 = android.print.ILayoutResultCallback.Stub.asInterface(r3)
                int r4 = r12.readInt()
                if (r4 == 0) goto L_0x008b
                android.os.Parcelable$Creator<android.os.Bundle> r1 = android.os.Bundle.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r12)
                android.os.Bundle r1 = (android.os.Bundle) r1
            L_0x0089:
                r8 = r1
                goto L_0x008c
            L_0x008b:
                goto L_0x0089
            L_0x008c:
                int r1 = r12.readInt()
                r4 = r10
                r7 = r3
                r9 = r1
                r4.layout(r5, r6, r7, r8, r9)
                return r2
            L_0x0097:
                r12.enforceInterface(r0)
                r10.start()
                return r2
            L_0x009e:
                r12.enforceInterface(r0)
                android.os.IBinder r1 = r12.readStrongBinder()
                android.print.IPrintDocumentAdapterObserver r1 = android.print.IPrintDocumentAdapterObserver.Stub.asInterface(r1)
                r10.setObserver(r1)
                return r2
            L_0x00ad:
                r13.writeString(r0)
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: android.print.IPrintDocumentAdapter.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IPrintDocumentAdapter {
            public static IPrintDocumentAdapter sDefaultImpl;
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

            public void setObserver(IPrintDocumentAdapterObserver observer) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(observer != null ? observer.asBinder() : null);
                    if (this.mRemote.transact(1, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setObserver(observer);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void start() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(2, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().start();
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void layout(PrintAttributes oldAttributes, PrintAttributes newAttributes, ILayoutResultCallback callback, Bundle metadata, int sequence) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (oldAttributes != null) {
                        _data.writeInt(1);
                        oldAttributes.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (newAttributes != null) {
                        _data.writeInt(1);
                        newAttributes.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (metadata != null) {
                        _data.writeInt(1);
                        metadata.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(sequence);
                    if (this.mRemote.transact(3, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().layout(oldAttributes, newAttributes, callback, metadata, sequence);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void write(PageRange[] pages, ParcelFileDescriptor fd, IWriteResultCallback callback, int sequence) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedArray(pages, 0);
                    if (fd != null) {
                        _data.writeInt(1);
                        fd.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    _data.writeInt(sequence);
                    if (this.mRemote.transact(4, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().write(pages, fd, callback, sequence);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void finish() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(5, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().finish();
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void kill(String reason) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(reason);
                    if (this.mRemote.transact(6, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().kill(reason);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IPrintDocumentAdapter impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IPrintDocumentAdapter getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
