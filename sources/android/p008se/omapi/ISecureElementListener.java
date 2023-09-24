package android.p008se.omapi;

import android.p007os.Binder;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.RemoteException;

/* renamed from: android.se.omapi.ISecureElementListener */
/* loaded from: classes3.dex */
public interface ISecureElementListener extends IInterface {

    /* renamed from: android.se.omapi.ISecureElementListener$Default */
    /* loaded from: classes3.dex */
    public static class Default implements ISecureElementListener {
        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* renamed from: android.se.omapi.ISecureElementListener$Stub */
    /* loaded from: classes3.dex */
    public static abstract class Stub extends Binder implements ISecureElementListener {
        private static final String DESCRIPTOR = "android.se.omapi.ISecureElementListener";

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ISecureElementListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof ISecureElementListener)) {
                return (ISecureElementListener) iin;
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

        /* renamed from: android.se.omapi.ISecureElementListener$Stub$Proxy */
        /* loaded from: classes3.dex */
        private static class Proxy implements ISecureElementListener {
            public static ISecureElementListener sDefaultImpl;
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

        public static boolean setDefaultImpl(ISecureElementListener impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static ISecureElementListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
