package android.bluetooth;

import android.p007os.Binder;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.RemoteException;
import java.util.List;

/* loaded from: classes.dex */
public interface IBluetoothPbapClient extends IInterface {
    boolean connect(BluetoothDevice bluetoothDevice) throws RemoteException;

    boolean disconnect(BluetoothDevice bluetoothDevice) throws RemoteException;

    List<BluetoothDevice> getConnectedDevices() throws RemoteException;

    int getConnectionState(BluetoothDevice bluetoothDevice) throws RemoteException;

    List<BluetoothDevice> getDevicesMatchingConnectionStates(int[] iArr) throws RemoteException;

    int getPriority(BluetoothDevice bluetoothDevice) throws RemoteException;

    boolean setPriority(BluetoothDevice bluetoothDevice, int i) throws RemoteException;

    /* loaded from: classes.dex */
    public static class Default implements IBluetoothPbapClient {
        @Override // android.bluetooth.IBluetoothPbapClient
        public boolean connect(BluetoothDevice device) throws RemoteException {
            return false;
        }

        @Override // android.bluetooth.IBluetoothPbapClient
        public boolean disconnect(BluetoothDevice device) throws RemoteException {
            return false;
        }

        @Override // android.bluetooth.IBluetoothPbapClient
        public List<BluetoothDevice> getConnectedDevices() throws RemoteException {
            return null;
        }

        @Override // android.bluetooth.IBluetoothPbapClient
        public List<BluetoothDevice> getDevicesMatchingConnectionStates(int[] states) throws RemoteException {
            return null;
        }

        @Override // android.bluetooth.IBluetoothPbapClient
        public int getConnectionState(BluetoothDevice device) throws RemoteException {
            return 0;
        }

        @Override // android.bluetooth.IBluetoothPbapClient
        public boolean setPriority(BluetoothDevice device, int priority) throws RemoteException {
            return false;
        }

        @Override // android.bluetooth.IBluetoothPbapClient
        public int getPriority(BluetoothDevice device) throws RemoteException {
            return 0;
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* loaded from: classes.dex */
    public static abstract class Stub extends Binder implements IBluetoothPbapClient {
        private static final String DESCRIPTOR = "android.bluetooth.IBluetoothPbapClient";
        static final int TRANSACTION_connect = 1;
        static final int TRANSACTION_disconnect = 2;
        static final int TRANSACTION_getConnectedDevices = 3;
        static final int TRANSACTION_getConnectionState = 5;
        static final int TRANSACTION_getDevicesMatchingConnectionStates = 4;
        static final int TRANSACTION_getPriority = 7;
        static final int TRANSACTION_setPriority = 6;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IBluetoothPbapClient asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IBluetoothPbapClient)) {
                return (IBluetoothPbapClient) iin;
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
                    return "connect";
                case 2:
                    return "disconnect";
                case 3:
                    return "getConnectedDevices";
                case 4:
                    return "getDevicesMatchingConnectionStates";
                case 5:
                    return "getConnectionState";
                case 6:
                    return "setPriority";
                case 7:
                    return "getPriority";
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
            BluetoothDevice _arg0;
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    _arg0 = data.readInt() != 0 ? BluetoothDevice.CREATOR.createFromParcel(data) : null;
                    boolean connect = connect(_arg0);
                    reply.writeNoException();
                    reply.writeInt(connect ? 1 : 0);
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    _arg0 = data.readInt() != 0 ? BluetoothDevice.CREATOR.createFromParcel(data) : null;
                    boolean disconnect = disconnect(_arg0);
                    reply.writeNoException();
                    reply.writeInt(disconnect ? 1 : 0);
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    List<BluetoothDevice> _result = getConnectedDevices();
                    reply.writeNoException();
                    reply.writeTypedList(_result);
                    return true;
                case 4:
                    data.enforceInterface(DESCRIPTOR);
                    List<BluetoothDevice> _result2 = getDevicesMatchingConnectionStates(data.createIntArray());
                    reply.writeNoException();
                    reply.writeTypedList(_result2);
                    return true;
                case 5:
                    data.enforceInterface(DESCRIPTOR);
                    _arg0 = data.readInt() != 0 ? BluetoothDevice.CREATOR.createFromParcel(data) : null;
                    int _result3 = getConnectionState(_arg0);
                    reply.writeNoException();
                    reply.writeInt(_result3);
                    return true;
                case 6:
                    data.enforceInterface(DESCRIPTOR);
                    _arg0 = data.readInt() != 0 ? BluetoothDevice.CREATOR.createFromParcel(data) : null;
                    int _arg1 = data.readInt();
                    boolean priority = setPriority(_arg0, _arg1);
                    reply.writeNoException();
                    reply.writeInt(priority ? 1 : 0);
                    return true;
                case 7:
                    data.enforceInterface(DESCRIPTOR);
                    _arg0 = data.readInt() != 0 ? BluetoothDevice.CREATOR.createFromParcel(data) : null;
                    int _result4 = getPriority(_arg0);
                    reply.writeNoException();
                    reply.writeInt(_result4);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* loaded from: classes.dex */
        private static class Proxy implements IBluetoothPbapClient {
            public static IBluetoothPbapClient sDefaultImpl;
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

            @Override // android.bluetooth.IBluetoothPbapClient
            public boolean connect(BluetoothDevice device) throws RemoteException {
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
                    boolean _status = this.mRemote.transact(1, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().connect(device);
                    }
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.bluetooth.IBluetoothPbapClient
            public boolean disconnect(BluetoothDevice device) throws RemoteException {
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
                    boolean _status = this.mRemote.transact(2, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().disconnect(device);
                    }
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.bluetooth.IBluetoothPbapClient
            public List<BluetoothDevice> getConnectedDevices() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(3, _data, _reply, 0);
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

            @Override // android.bluetooth.IBluetoothPbapClient
            public List<BluetoothDevice> getDevicesMatchingConnectionStates(int[] states) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeIntArray(states);
                    boolean _status = this.mRemote.transact(4, _data, _reply, 0);
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

            @Override // android.bluetooth.IBluetoothPbapClient
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
                    boolean _status = this.mRemote.transact(5, _data, _reply, 0);
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

            @Override // android.bluetooth.IBluetoothPbapClient
            public boolean setPriority(BluetoothDevice device, int priority) throws RemoteException {
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
                    _data.writeInt(priority);
                    boolean _status = this.mRemote.transact(6, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setPriority(device, priority);
                    }
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.bluetooth.IBluetoothPbapClient
            public int getPriority(BluetoothDevice device) throws RemoteException {
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
                    boolean _status = this.mRemote.transact(7, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPriority(device);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IBluetoothPbapClient impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static IBluetoothPbapClient getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
