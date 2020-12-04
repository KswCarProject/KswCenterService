package android.service.autofill;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IAutoFillService extends IInterface {
    void onConnectedStateChanged(boolean z) throws RemoteException;

    void onFillRequest(FillRequest fillRequest, IFillCallback iFillCallback) throws RemoteException;

    void onSaveRequest(SaveRequest saveRequest, ISaveCallback iSaveCallback) throws RemoteException;

    public static class Default implements IAutoFillService {
        public void onConnectedStateChanged(boolean connected) throws RemoteException {
        }

        public void onFillRequest(FillRequest request, IFillCallback callback) throws RemoteException {
        }

        public void onSaveRequest(SaveRequest request, ISaveCallback callback) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IAutoFillService {
        private static final String DESCRIPTOR = "android.service.autofill.IAutoFillService";
        static final int TRANSACTION_onConnectedStateChanged = 1;
        static final int TRANSACTION_onFillRequest = 2;
        static final int TRANSACTION_onSaveRequest = 3;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IAutoFillService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IAutoFillService)) {
                return new Proxy(obj);
            }
            return (IAutoFillService) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "onConnectedStateChanged";
                case 2:
                    return "onFillRequest";
                case 3:
                    return "onSaveRequest";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v6, resolved type: android.service.autofill.FillRequest} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v10, resolved type: android.service.autofill.SaveRequest} */
        /* JADX WARNING: type inference failed for: r1v1 */
        /* JADX WARNING: type inference failed for: r1v15 */
        /* JADX WARNING: type inference failed for: r1v16 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r5, android.os.Parcel r6, android.os.Parcel r7, int r8) throws android.os.RemoteException {
            /*
                r4 = this;
                java.lang.String r0 = "android.service.autofill.IAutoFillService"
                r1 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r2 = 1
                if (r5 == r1) goto L_0x005f
                r1 = 0
                switch(r5) {
                    case 1: goto L_0x004f;
                    case 2: goto L_0x0030;
                    case 3: goto L_0x0011;
                    default: goto L_0x000c;
                }
            L_0x000c:
                boolean r1 = super.onTransact(r5, r6, r7, r8)
                return r1
            L_0x0011:
                r6.enforceInterface(r0)
                int r3 = r6.readInt()
                if (r3 == 0) goto L_0x0023
                android.os.Parcelable$Creator<android.service.autofill.SaveRequest> r1 = android.service.autofill.SaveRequest.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r6)
                android.service.autofill.SaveRequest r1 = (android.service.autofill.SaveRequest) r1
                goto L_0x0024
            L_0x0023:
            L_0x0024:
                android.os.IBinder r3 = r6.readStrongBinder()
                android.service.autofill.ISaveCallback r3 = android.service.autofill.ISaveCallback.Stub.asInterface(r3)
                r4.onSaveRequest(r1, r3)
                return r2
            L_0x0030:
                r6.enforceInterface(r0)
                int r3 = r6.readInt()
                if (r3 == 0) goto L_0x0042
                android.os.Parcelable$Creator<android.service.autofill.FillRequest> r1 = android.service.autofill.FillRequest.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r6)
                android.service.autofill.FillRequest r1 = (android.service.autofill.FillRequest) r1
                goto L_0x0043
            L_0x0042:
            L_0x0043:
                android.os.IBinder r3 = r6.readStrongBinder()
                android.service.autofill.IFillCallback r3 = android.service.autofill.IFillCallback.Stub.asInterface(r3)
                r4.onFillRequest(r1, r3)
                return r2
            L_0x004f:
                r6.enforceInterface(r0)
                int r1 = r6.readInt()
                if (r1 == 0) goto L_0x005a
                r1 = r2
                goto L_0x005b
            L_0x005a:
                r1 = 0
            L_0x005b:
                r4.onConnectedStateChanged(r1)
                return r2
            L_0x005f:
                r7.writeString(r0)
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: android.service.autofill.IAutoFillService.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IAutoFillService {
            public static IAutoFillService sDefaultImpl;
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

            public void onConnectedStateChanged(boolean connected) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(connected);
                    if (this.mRemote.transact(1, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onConnectedStateChanged(connected);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onFillRequest(FillRequest request, IFillCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (request != null) {
                        _data.writeInt(1);
                        request.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(2, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onFillRequest(request, callback);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onSaveRequest(SaveRequest request, ISaveCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (request != null) {
                        _data.writeInt(1);
                        request.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(3, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onSaveRequest(request, callback);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IAutoFillService impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IAutoFillService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
