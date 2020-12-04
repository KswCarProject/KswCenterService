package android.service.autofill;

import android.content.IntentSender;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.text.TextUtils;

public interface ISaveCallback extends IInterface {
    void onFailure(CharSequence charSequence) throws RemoteException;

    void onSuccess(IntentSender intentSender) throws RemoteException;

    public static class Default implements ISaveCallback {
        public void onSuccess(IntentSender intentSender) throws RemoteException {
        }

        public void onFailure(CharSequence message) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ISaveCallback {
        private static final String DESCRIPTOR = "android.service.autofill.ISaveCallback";
        static final int TRANSACTION_onFailure = 2;
        static final int TRANSACTION_onSuccess = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ISaveCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof ISaveCallback)) {
                return new Proxy(obj);
            }
            return (ISaveCallback) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "onSuccess";
                case 2:
                    return "onFailure";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v2, resolved type: android.content.IntentSender} */
        /* JADX WARNING: type inference failed for: r1v1 */
        /* JADX WARNING: type inference failed for: r1v6, types: [java.lang.CharSequence] */
        /* JADX WARNING: type inference failed for: r1v11 */
        /* JADX WARNING: type inference failed for: r1v12 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r5, android.os.Parcel r6, android.os.Parcel r7, int r8) throws android.os.RemoteException {
            /*
                r4 = this;
                java.lang.String r0 = "android.service.autofill.ISaveCallback"
                r1 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r2 = 1
                if (r5 == r1) goto L_0x0045
                r1 = 0
                switch(r5) {
                    case 1: goto L_0x002b;
                    case 2: goto L_0x0011;
                    default: goto L_0x000c;
                }
            L_0x000c:
                boolean r1 = super.onTransact(r5, r6, r7, r8)
                return r1
            L_0x0011:
                r6.enforceInterface(r0)
                int r3 = r6.readInt()
                if (r3 == 0) goto L_0x0023
                android.os.Parcelable$Creator<java.lang.CharSequence> r1 = android.text.TextUtils.CHAR_SEQUENCE_CREATOR
                java.lang.Object r1 = r1.createFromParcel(r6)
                java.lang.CharSequence r1 = (java.lang.CharSequence) r1
                goto L_0x0024
            L_0x0023:
            L_0x0024:
                r4.onFailure(r1)
                r7.writeNoException()
                return r2
            L_0x002b:
                r6.enforceInterface(r0)
                int r3 = r6.readInt()
                if (r3 == 0) goto L_0x003d
                android.os.Parcelable$Creator<android.content.IntentSender> r1 = android.content.IntentSender.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r6)
                android.content.IntentSender r1 = (android.content.IntentSender) r1
                goto L_0x003e
            L_0x003d:
            L_0x003e:
                r4.onSuccess(r1)
                r7.writeNoException()
                return r2
            L_0x0045:
                r7.writeString(r0)
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: android.service.autofill.ISaveCallback.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements ISaveCallback {
            public static ISaveCallback sDefaultImpl;
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

            public void onSuccess(IntentSender intentSender) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (intentSender != null) {
                        _data.writeInt(1);
                        intentSender.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(1, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onSuccess(intentSender);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void onFailure(CharSequence message) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (message != null) {
                        _data.writeInt(1);
                        TextUtils.writeToParcel(message, _data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(2, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onFailure(message);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(ISaveCallback impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static ISaveCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
