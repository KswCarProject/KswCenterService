package android.service.p010vr;

import android.content.ComponentName;
import android.p007os.Binder;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.RemoteException;

/* renamed from: android.service.vr.IVrListener */
/* loaded from: classes3.dex */
public interface IVrListener extends IInterface {
    void focusedActivityChanged(ComponentName componentName, boolean z, int i) throws RemoteException;

    /* renamed from: android.service.vr.IVrListener$Default */
    /* loaded from: classes3.dex */
    public static class Default implements IVrListener {
        @Override // android.service.p010vr.IVrListener
        public void focusedActivityChanged(ComponentName component, boolean running2dInVr, int pid) throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* renamed from: android.service.vr.IVrListener$Stub */
    /* loaded from: classes3.dex */
    public static abstract class Stub extends Binder implements IVrListener {
        private static final String DESCRIPTOR = "android.service.vr.IVrListener";
        static final int TRANSACTION_focusedActivityChanged = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IVrListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IVrListener)) {
                return (IVrListener) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            if (transactionCode == 1) {
                return "focusedActivityChanged";
            }
            return null;
        }

        @Override // android.p007os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.p007os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            ComponentName _arg0;
            if (code != 1) {
                if (code == 1598968902) {
                    reply.writeString(DESCRIPTOR);
                    return true;
                }
                return super.onTransact(code, data, reply, flags);
            }
            data.enforceInterface(DESCRIPTOR);
            if (data.readInt() != 0) {
                _arg0 = ComponentName.CREATOR.createFromParcel(data);
            } else {
                _arg0 = null;
            }
            boolean _arg1 = data.readInt() != 0;
            int _arg2 = data.readInt();
            focusedActivityChanged(_arg0, _arg1, _arg2);
            return true;
        }

        /* renamed from: android.service.vr.IVrListener$Stub$Proxy */
        /* loaded from: classes3.dex */
        private static class Proxy implements IVrListener {
            public static IVrListener sDefaultImpl;
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

            @Override // android.service.p010vr.IVrListener
            public void focusedActivityChanged(ComponentName component, boolean running2dInVr, int pid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (component != null) {
                        _data.writeInt(1);
                        component.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(running2dInVr ? 1 : 0);
                    _data.writeInt(pid);
                    boolean _status = this.mRemote.transact(1, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().focusedActivityChanged(component, running2dInVr, pid);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IVrListener impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static IVrListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
