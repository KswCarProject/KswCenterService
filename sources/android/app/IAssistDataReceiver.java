package android.app;

import android.annotation.UnsupportedAppUsage;
import android.graphics.Bitmap;
import android.p007os.Binder;
import android.p007os.Bundle;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.RemoteException;

/* loaded from: classes.dex */
public interface IAssistDataReceiver extends IInterface {
    @UnsupportedAppUsage
    void onHandleAssistData(Bundle bundle) throws RemoteException;

    @UnsupportedAppUsage
    void onHandleAssistScreenshot(Bitmap bitmap) throws RemoteException;

    /* loaded from: classes.dex */
    public static class Default implements IAssistDataReceiver {
        @Override // android.app.IAssistDataReceiver
        public void onHandleAssistData(Bundle resultData) throws RemoteException {
        }

        @Override // android.app.IAssistDataReceiver
        public void onHandleAssistScreenshot(Bitmap screenshot) throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* loaded from: classes.dex */
    public static abstract class Stub extends Binder implements IAssistDataReceiver {
        private static final String DESCRIPTOR = "android.app.IAssistDataReceiver";
        static final int TRANSACTION_onHandleAssistData = 1;
        static final int TRANSACTION_onHandleAssistScreenshot = 2;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IAssistDataReceiver asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IAssistDataReceiver)) {
                return (IAssistDataReceiver) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "onHandleAssistData";
                case 2:
                    return "onHandleAssistScreenshot";
                default:
                    return null;
            }
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
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    Bundle _arg0 = data.readInt() != 0 ? Bundle.CREATOR.createFromParcel(data) : null;
                    onHandleAssistData(_arg0);
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    Bitmap _arg02 = data.readInt() != 0 ? Bitmap.CREATOR.createFromParcel(data) : null;
                    onHandleAssistScreenshot(_arg02);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* loaded from: classes.dex */
        private static class Proxy implements IAssistDataReceiver {
            public static IAssistDataReceiver sDefaultImpl;
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

            @Override // android.app.IAssistDataReceiver
            public void onHandleAssistData(Bundle resultData) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (resultData != null) {
                        _data.writeInt(1);
                        resultData.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(1, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onHandleAssistData(resultData);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IAssistDataReceiver
            public void onHandleAssistScreenshot(Bitmap screenshot) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (screenshot != null) {
                        _data.writeInt(1);
                        screenshot.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(2, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onHandleAssistScreenshot(screenshot);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IAssistDataReceiver impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static IAssistDataReceiver getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
