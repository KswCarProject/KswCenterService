package android.bluetooth;

import android.p007os.Binder;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.RemoteException;
import java.util.List;

/* loaded from: classes.dex */
public interface IBluetoothAvrcpController extends IInterface {
    List<BluetoothDevice> getConnectedDevices() throws RemoteException;

    int getConnectionState(BluetoothDevice bluetoothDevice) throws RemoteException;

    List<BluetoothDevice> getDevicesMatchingConnectionStates(int[] iArr) throws RemoteException;

    BluetoothAvrcpPlayerSettings getPlayerSettings(BluetoothDevice bluetoothDevice) throws RemoteException;

    void sendGroupNavigationCmd(BluetoothDevice bluetoothDevice, int i, int i2) throws RemoteException;

    boolean setPlayerApplicationSetting(BluetoothAvrcpPlayerSettings bluetoothAvrcpPlayerSettings) throws RemoteException;

    /* loaded from: classes.dex */
    public static class Default implements IBluetoothAvrcpController {
        @Override // android.bluetooth.IBluetoothAvrcpController
        public List<BluetoothDevice> getConnectedDevices() throws RemoteException {
            return null;
        }

        @Override // android.bluetooth.IBluetoothAvrcpController
        public List<BluetoothDevice> getDevicesMatchingConnectionStates(int[] states) throws RemoteException {
            return null;
        }

        @Override // android.bluetooth.IBluetoothAvrcpController
        public int getConnectionState(BluetoothDevice device) throws RemoteException {
            return 0;
        }

        @Override // android.bluetooth.IBluetoothAvrcpController
        public BluetoothAvrcpPlayerSettings getPlayerSettings(BluetoothDevice device) throws RemoteException {
            return null;
        }

        @Override // android.bluetooth.IBluetoothAvrcpController
        public boolean setPlayerApplicationSetting(BluetoothAvrcpPlayerSettings plAppSetting) throws RemoteException {
            return false;
        }

        @Override // android.bluetooth.IBluetoothAvrcpController
        public void sendGroupNavigationCmd(BluetoothDevice device, int keyCode, int keyState) throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* loaded from: classes.dex */
    public static abstract class Stub extends Binder implements IBluetoothAvrcpController {
        private static final String DESCRIPTOR = "android.bluetooth.IBluetoothAvrcpController";
        static final int TRANSACTION_getConnectedDevices = 1;
        static final int TRANSACTION_getConnectionState = 3;
        static final int TRANSACTION_getDevicesMatchingConnectionStates = 2;
        static final int TRANSACTION_getPlayerSettings = 4;
        static final int TRANSACTION_sendGroupNavigationCmd = 6;
        static final int TRANSACTION_setPlayerApplicationSetting = 5;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IBluetoothAvrcpController asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IBluetoothAvrcpController)) {
                return (IBluetoothAvrcpController) iin;
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
                    return "getConnectedDevices";
                case 2:
                    return "getDevicesMatchingConnectionStates";
                case 3:
                    return "getConnectionState";
                case 4:
                    return "getPlayerSettings";
                case 5:
                    return "setPlayerApplicationSetting";
                case 6:
                    return "sendGroupNavigationCmd";
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
                    List<BluetoothDevice> _result = getConnectedDevices();
                    reply.writeNoException();
                    reply.writeTypedList(_result);
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    int[] _arg0 = data.createIntArray();
                    List<BluetoothDevice> _result2 = getDevicesMatchingConnectionStates(_arg0);
                    reply.writeNoException();
                    reply.writeTypedList(_result2);
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    BluetoothDevice _arg02 = data.readInt() != 0 ? BluetoothDevice.CREATOR.createFromParcel(data) : null;
                    int _result3 = getConnectionState(_arg02);
                    reply.writeNoException();
                    reply.writeInt(_result3);
                    return true;
                case 4:
                    data.enforceInterface(DESCRIPTOR);
                    BluetoothDevice _arg03 = data.readInt() != 0 ? BluetoothDevice.CREATOR.createFromParcel(data) : null;
                    BluetoothAvrcpPlayerSettings _result4 = getPlayerSettings(_arg03);
                    reply.writeNoException();
                    if (_result4 != null) {
                        reply.writeInt(1);
                        _result4.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 5:
                    data.enforceInterface(DESCRIPTOR);
                    BluetoothAvrcpPlayerSettings _arg04 = data.readInt() != 0 ? BluetoothAvrcpPlayerSettings.CREATOR.createFromParcel(data) : null;
                    boolean playerApplicationSetting = setPlayerApplicationSetting(_arg04);
                    reply.writeNoException();
                    reply.writeInt(playerApplicationSetting ? 1 : 0);
                    return true;
                case 6:
                    data.enforceInterface(DESCRIPTOR);
                    BluetoothDevice _arg05 = data.readInt() != 0 ? BluetoothDevice.CREATOR.createFromParcel(data) : null;
                    int _arg1 = data.readInt();
                    int _arg2 = data.readInt();
                    sendGroupNavigationCmd(_arg05, _arg1, _arg2);
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* loaded from: classes.dex */
        private static class Proxy implements IBluetoothAvrcpController {
            public static IBluetoothAvrcpController sDefaultImpl;
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

            @Override // android.bluetooth.IBluetoothAvrcpController
            public List<BluetoothDevice> getConnectedDevices() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(1, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getConnectedDevices();
                    }
                    _reply.readException();
                    List<BluetoothDevice> _result = _reply.createTypedArrayList(BluetoothDevice.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.bluetooth.IBluetoothAvrcpController
            public List<BluetoothDevice> getDevicesMatchingConnectionStates(int[] states) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeIntArray(states);
                    boolean _status = this.mRemote.transact(2, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getDevicesMatchingConnectionStates(states);
                    }
                    _reply.readException();
                    List<BluetoothDevice> _result = _reply.createTypedArrayList(BluetoothDevice.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.bluetooth.IBluetoothAvrcpController
            public int getConnectionState(BluetoothDevice device) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (device != null) {
                        _data.writeInt(1);
                        device.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(3, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getConnectionState(device);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.bluetooth.IBluetoothAvrcpController
            public BluetoothAvrcpPlayerSettings getPlayerSettings(BluetoothDevice device) throws RemoteException {
                BluetoothAvrcpPlayerSettings _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (device != null) {
                        _data.writeInt(1);
                        device.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(4, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPlayerSettings(device);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = BluetoothAvrcpPlayerSettings.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.bluetooth.IBluetoothAvrcpController
            public boolean setPlayerApplicationSetting(BluetoothAvrcpPlayerSettings plAppSetting) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (plAppSetting != null) {
                        _data.writeInt(1);
                        plAppSetting.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(5, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setPlayerApplicationSetting(plAppSetting);
                    }
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.bluetooth.IBluetoothAvrcpController
            public void sendGroupNavigationCmd(BluetoothDevice device, int keyCode, int keyState) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (device != null) {
                        _data.writeInt(1);
                        device.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(keyCode);
                    _data.writeInt(keyState);
                    boolean _status = this.mRemote.transact(6, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().sendGroupNavigationCmd(device, keyCode, keyState);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IBluetoothAvrcpController impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static IBluetoothAvrcpController getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
