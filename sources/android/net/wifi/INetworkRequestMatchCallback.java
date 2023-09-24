package android.net.wifi;

import android.net.wifi.INetworkRequestUserSelectionCallback;
import android.p007os.Binder;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.RemoteException;
import java.util.List;

/* loaded from: classes3.dex */
public interface INetworkRequestMatchCallback extends IInterface {
    void onAbort() throws RemoteException;

    void onMatch(List<ScanResult> list) throws RemoteException;

    void onUserSelectionCallbackRegistration(INetworkRequestUserSelectionCallback iNetworkRequestUserSelectionCallback) throws RemoteException;

    void onUserSelectionConnectFailure(WifiConfiguration wifiConfiguration) throws RemoteException;

    void onUserSelectionConnectSuccess(WifiConfiguration wifiConfiguration) throws RemoteException;

    /* loaded from: classes3.dex */
    public static class Default implements INetworkRequestMatchCallback {
        @Override // android.net.wifi.INetworkRequestMatchCallback
        public void onUserSelectionCallbackRegistration(INetworkRequestUserSelectionCallback userSelectionCallback) throws RemoteException {
        }

        @Override // android.net.wifi.INetworkRequestMatchCallback
        public void onAbort() throws RemoteException {
        }

        @Override // android.net.wifi.INetworkRequestMatchCallback
        public void onMatch(List<ScanResult> scanResults) throws RemoteException {
        }

        @Override // android.net.wifi.INetworkRequestMatchCallback
        public void onUserSelectionConnectSuccess(WifiConfiguration wificonfiguration) throws RemoteException {
        }

        @Override // android.net.wifi.INetworkRequestMatchCallback
        public void onUserSelectionConnectFailure(WifiConfiguration wificonfiguration) throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* loaded from: classes3.dex */
    public static abstract class Stub extends Binder implements INetworkRequestMatchCallback {
        private static final String DESCRIPTOR = "android.net.wifi.INetworkRequestMatchCallback";
        static final int TRANSACTION_onAbort = 2;
        static final int TRANSACTION_onMatch = 3;
        static final int TRANSACTION_onUserSelectionCallbackRegistration = 1;
        static final int TRANSACTION_onUserSelectionConnectFailure = 5;
        static final int TRANSACTION_onUserSelectionConnectSuccess = 4;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static INetworkRequestMatchCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof INetworkRequestMatchCallback)) {
                return (INetworkRequestMatchCallback) iin;
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
                    return "onUserSelectionCallbackRegistration";
                case 2:
                    return "onAbort";
                case 3:
                    return "onMatch";
                case 4:
                    return "onUserSelectionConnectSuccess";
                case 5:
                    return "onUserSelectionConnectFailure";
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
            WifiConfiguration _arg0;
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    onUserSelectionCallbackRegistration(INetworkRequestUserSelectionCallback.Stub.asInterface(data.readStrongBinder()));
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    onAbort();
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    onMatch(data.createTypedArrayList(ScanResult.CREATOR));
                    return true;
                case 4:
                    data.enforceInterface(DESCRIPTOR);
                    _arg0 = data.readInt() != 0 ? WifiConfiguration.CREATOR.createFromParcel(data) : null;
                    onUserSelectionConnectSuccess(_arg0);
                    return true;
                case 5:
                    data.enforceInterface(DESCRIPTOR);
                    _arg0 = data.readInt() != 0 ? WifiConfiguration.CREATOR.createFromParcel(data) : null;
                    onUserSelectionConnectFailure(_arg0);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* loaded from: classes3.dex */
        private static class Proxy implements INetworkRequestMatchCallback {
            public static INetworkRequestMatchCallback sDefaultImpl;
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

            @Override // android.net.wifi.INetworkRequestMatchCallback
            public void onUserSelectionCallbackRegistration(INetworkRequestUserSelectionCallback userSelectionCallback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(userSelectionCallback != null ? userSelectionCallback.asBinder() : null);
                    boolean _status = this.mRemote.transact(1, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onUserSelectionCallbackRegistration(userSelectionCallback);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.INetworkRequestMatchCallback
            public void onAbort() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(2, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onAbort();
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.INetworkRequestMatchCallback
            public void onMatch(List<ScanResult> scanResults) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedList(scanResults);
                    boolean _status = this.mRemote.transact(3, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onMatch(scanResults);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.INetworkRequestMatchCallback
            public void onUserSelectionConnectSuccess(WifiConfiguration wificonfiguration) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (wificonfiguration != null) {
                        _data.writeInt(1);
                        wificonfiguration.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(4, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onUserSelectionConnectSuccess(wificonfiguration);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.INetworkRequestMatchCallback
            public void onUserSelectionConnectFailure(WifiConfiguration wificonfiguration) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (wificonfiguration != null) {
                        _data.writeInt(1);
                        wificonfiguration.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(5, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onUserSelectionConnectFailure(wificonfiguration);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(INetworkRequestMatchCallback impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static INetworkRequestMatchCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
