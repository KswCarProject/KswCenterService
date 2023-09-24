package android.media;

import android.p007os.Binder;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.RemoteException;
import java.util.List;

/* loaded from: classes3.dex */
public interface IPlaybackConfigDispatcher extends IInterface {
    void dispatchPlaybackConfigChange(List<AudioPlaybackConfiguration> list, boolean z) throws RemoteException;

    /* loaded from: classes3.dex */
    public static class Default implements IPlaybackConfigDispatcher {
        @Override // android.media.IPlaybackConfigDispatcher
        public void dispatchPlaybackConfigChange(List<AudioPlaybackConfiguration> configs, boolean flush) throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* loaded from: classes3.dex */
    public static abstract class Stub extends Binder implements IPlaybackConfigDispatcher {
        private static final String DESCRIPTOR = "android.media.IPlaybackConfigDispatcher";
        static final int TRANSACTION_dispatchPlaybackConfigChange = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IPlaybackConfigDispatcher asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IPlaybackConfigDispatcher)) {
                return (IPlaybackConfigDispatcher) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            if (transactionCode == 1) {
                return "dispatchPlaybackConfigChange";
            }
            return null;
        }

        @Override // android.p007os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.p007os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code != 1) {
                if (code == 1598968902) {
                    reply.writeString(DESCRIPTOR);
                    return true;
                }
                return super.onTransact(code, data, reply, flags);
            }
            data.enforceInterface(DESCRIPTOR);
            List<AudioPlaybackConfiguration> _arg0 = data.createTypedArrayList(AudioPlaybackConfiguration.CREATOR);
            boolean _arg1 = data.readInt() != 0;
            dispatchPlaybackConfigChange(_arg0, _arg1);
            return true;
        }

        /* loaded from: classes3.dex */
        private static class Proxy implements IPlaybackConfigDispatcher {
            public static IPlaybackConfigDispatcher sDefaultImpl;
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

            @Override // android.media.IPlaybackConfigDispatcher
            public void dispatchPlaybackConfigChange(List<AudioPlaybackConfiguration> configs, boolean flush) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedList(configs);
                    _data.writeInt(flush ? 1 : 0);
                    boolean _status = this.mRemote.transact(1, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().dispatchPlaybackConfigChange(configs, flush);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IPlaybackConfigDispatcher impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static IPlaybackConfigDispatcher getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
