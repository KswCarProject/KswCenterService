package android.net.wifi;

import android.net.wifi.INetworkRequestUserSelectionCallback;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.List;

public interface INetworkRequestMatchCallback extends IInterface {
    void onAbort() throws RemoteException;

    void onMatch(List<ScanResult> list) throws RemoteException;

    void onUserSelectionCallbackRegistration(INetworkRequestUserSelectionCallback iNetworkRequestUserSelectionCallback) throws RemoteException;

    void onUserSelectionConnectFailure(WifiConfiguration wifiConfiguration) throws RemoteException;

    void onUserSelectionConnectSuccess(WifiConfiguration wifiConfiguration) throws RemoteException;

    public static class Default implements INetworkRequestMatchCallback {
        public void onUserSelectionCallbackRegistration(INetworkRequestUserSelectionCallback userSelectionCallback) throws RemoteException {
        }

        public void onAbort() throws RemoteException {
        }

        public void onMatch(List<ScanResult> list) throws RemoteException {
        }

        public void onUserSelectionConnectSuccess(WifiConfiguration wificonfiguration) throws RemoteException {
        }

        public void onUserSelectionConnectFailure(WifiConfiguration wificonfiguration) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

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
            if (iin == null || !(iin instanceof INetworkRequestMatchCallback)) {
                return new Proxy(obj);
            }
            return (INetworkRequestMatchCallback) iin;
        }

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

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code != 1598968902) {
                WifiConfiguration _arg0 = null;
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
                        if (data.readInt() != 0) {
                            _arg0 = WifiConfiguration.CREATOR.createFromParcel(data);
                        }
                        onUserSelectionConnectSuccess(_arg0);
                        return true;
                    case 5:
                        data.enforceInterface(DESCRIPTOR);
                        if (data.readInt() != 0) {
                            _arg0 = WifiConfiguration.CREATOR.createFromParcel(data);
                        }
                        onUserSelectionConnectFailure(_arg0);
                        return true;
                    default:
                        return super.onTransact(code, data, reply, flags);
                }
            } else {
                reply.writeString(DESCRIPTOR);
                return true;
            }
        }

        private static class Proxy implements INetworkRequestMatchCallback {
            public static INetworkRequestMatchCallback sDefaultImpl;
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

            public void onUserSelectionCallbackRegistration(INetworkRequestUserSelectionCallback userSelectionCallback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(userSelectionCallback != null ? userSelectionCallback.asBinder() : null);
                    if (this.mRemote.transact(1, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onUserSelectionCallbackRegistration(userSelectionCallback);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onAbort() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(2, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onAbort();
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onMatch(List<ScanResult> scanResults) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedList(scanResults);
                    if (this.mRemote.transact(3, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onMatch(scanResults);
                    }
                } finally {
                    _data.recycle();
                }
            }

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
                    if (this.mRemote.transact(4, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onUserSelectionConnectSuccess(wificonfiguration);
                    }
                } finally {
                    _data.recycle();
                }
            }

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
                    if (this.mRemote.transact(5, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onUserSelectionConnectFailure(wificonfiguration);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(INetworkRequestMatchCallback impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static INetworkRequestMatchCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
