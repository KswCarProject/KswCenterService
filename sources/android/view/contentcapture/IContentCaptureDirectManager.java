package android.view.contentcapture;

import android.content.ContentCaptureOptions;
import android.content.p002pm.ParceledListSlice;
import android.p007os.Binder;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.RemoteException;

/* loaded from: classes4.dex */
public interface IContentCaptureDirectManager extends IInterface {
    void sendEvents(ParceledListSlice parceledListSlice, int i, ContentCaptureOptions contentCaptureOptions) throws RemoteException;

    /* loaded from: classes4.dex */
    public static class Default implements IContentCaptureDirectManager {
        @Override // android.view.contentcapture.IContentCaptureDirectManager
        public void sendEvents(ParceledListSlice events, int reason, ContentCaptureOptions options) throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* loaded from: classes4.dex */
    public static abstract class Stub extends Binder implements IContentCaptureDirectManager {
        private static final String DESCRIPTOR = "android.view.contentcapture.IContentCaptureDirectManager";
        static final int TRANSACTION_sendEvents = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IContentCaptureDirectManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IContentCaptureDirectManager)) {
                return (IContentCaptureDirectManager) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            if (transactionCode == 1) {
                return "sendEvents";
            }
            return null;
        }

        @Override // android.p007os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.p007os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            ParceledListSlice _arg0;
            if (code != 1) {
                if (code == 1598968902) {
                    reply.writeString(DESCRIPTOR);
                    return true;
                }
                return super.onTransact(code, data, reply, flags);
            }
            data.enforceInterface(DESCRIPTOR);
            if (data.readInt() != 0) {
                _arg0 = ParceledListSlice.CREATOR.createFromParcel(data);
            } else {
                _arg0 = null;
            }
            int _arg1 = data.readInt();
            ContentCaptureOptions _arg2 = data.readInt() != 0 ? ContentCaptureOptions.CREATOR.createFromParcel(data) : null;
            sendEvents(_arg0, _arg1, _arg2);
            return true;
        }

        /* loaded from: classes4.dex */
        private static class Proxy implements IContentCaptureDirectManager {
            public static IContentCaptureDirectManager sDefaultImpl;
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

            @Override // android.view.contentcapture.IContentCaptureDirectManager
            public void sendEvents(ParceledListSlice events, int reason, ContentCaptureOptions options) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (events != null) {
                        _data.writeInt(1);
                        events.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(reason);
                    if (options != null) {
                        _data.writeInt(1);
                        options.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(1, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().sendEvents(events, reason, options);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IContentCaptureDirectManager impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static IContentCaptureDirectManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
