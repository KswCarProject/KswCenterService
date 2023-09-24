package android.p007os;

/* renamed from: android.os.IDumpstateToken */
/* loaded from: classes3.dex */
public interface IDumpstateToken extends IInterface {

    /* renamed from: android.os.IDumpstateToken$Default */
    /* loaded from: classes3.dex */
    public static class Default implements IDumpstateToken {
        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* renamed from: android.os.IDumpstateToken$Stub */
    /* loaded from: classes3.dex */
    public static abstract class Stub extends Binder implements IDumpstateToken {
        private static final String DESCRIPTOR = "android.os.IDumpstateToken";

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IDumpstateToken asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IDumpstateToken)) {
                return (IDumpstateToken) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            return null;
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
            return super.onTransact(code, data, reply, flags);
        }

        /* renamed from: android.os.IDumpstateToken$Stub$Proxy */
        /* loaded from: classes3.dex */
        private static class Proxy implements IDumpstateToken {
            public static IDumpstateToken sDefaultImpl;
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
        }

        public static boolean setDefaultImpl(IDumpstateToken impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static IDumpstateToken getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
