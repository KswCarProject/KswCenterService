package android.view;

import android.graphics.Region;
import android.p007os.Binder;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.RemoteException;

/* loaded from: classes4.dex */
public interface ISystemGestureExclusionListener extends IInterface {
    void onSystemGestureExclusionChanged(int i, Region region) throws RemoteException;

    /* loaded from: classes4.dex */
    public static class Default implements ISystemGestureExclusionListener {
        @Override // android.view.ISystemGestureExclusionListener
        public void onSystemGestureExclusionChanged(int displayId, Region systemGestureExclusion) throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* loaded from: classes4.dex */
    public static abstract class Stub extends Binder implements ISystemGestureExclusionListener {
        private static final String DESCRIPTOR = "android.view.ISystemGestureExclusionListener";
        static final int TRANSACTION_onSystemGestureExclusionChanged = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ISystemGestureExclusionListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof ISystemGestureExclusionListener)) {
                return (ISystemGestureExclusionListener) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            if (transactionCode == 1) {
                return "onSystemGestureExclusionChanged";
            }
            return null;
        }

        @Override // android.p007os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.p007os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            Region _arg1;
            if (code != 1) {
                if (code == 1598968902) {
                    reply.writeString(DESCRIPTOR);
                    return true;
                }
                return super.onTransact(code, data, reply, flags);
            }
            data.enforceInterface(DESCRIPTOR);
            int _arg0 = data.readInt();
            if (data.readInt() != 0) {
                _arg1 = Region.CREATOR.createFromParcel(data);
            } else {
                _arg1 = null;
            }
            onSystemGestureExclusionChanged(_arg0, _arg1);
            return true;
        }

        /* loaded from: classes4.dex */
        private static class Proxy implements ISystemGestureExclusionListener {
            public static ISystemGestureExclusionListener sDefaultImpl;
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

            @Override // android.view.ISystemGestureExclusionListener
            public void onSystemGestureExclusionChanged(int displayId, Region systemGestureExclusion) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    if (systemGestureExclusion != null) {
                        _data.writeInt(1);
                        systemGestureExclusion.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(1, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onSystemGestureExclusionChanged(displayId, systemGestureExclusion);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(ISystemGestureExclusionListener impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static ISystemGestureExclusionListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
