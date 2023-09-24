package android.bluetooth;

import android.p007os.Binder;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.RemoteException;
import java.util.List;

/* loaded from: classes.dex */
public interface IBluetoothA2dp extends IInterface {
    boolean connect(BluetoothDevice bluetoothDevice) throws RemoteException;

    void disableOptionalCodecs(BluetoothDevice bluetoothDevice) throws RemoteException;

    boolean disconnect(BluetoothDevice bluetoothDevice) throws RemoteException;

    void enableOptionalCodecs(BluetoothDevice bluetoothDevice) throws RemoteException;

    BluetoothDevice getActiveDevice() throws RemoteException;

    BluetoothCodecStatus getCodecStatus(BluetoothDevice bluetoothDevice) throws RemoteException;

    List<BluetoothDevice> getConnectedDevices() throws RemoteException;

    int getConnectionState(BluetoothDevice bluetoothDevice) throws RemoteException;

    List<BluetoothDevice> getDevicesMatchingConnectionStates(int[] iArr) throws RemoteException;

    int getOptionalCodecsEnabled(BluetoothDevice bluetoothDevice) throws RemoteException;

    int getPriority(BluetoothDevice bluetoothDevice) throws RemoteException;

    boolean isA2dpPlaying(BluetoothDevice bluetoothDevice) throws RemoteException;

    boolean isAvrcpAbsoluteVolumeSupported() throws RemoteException;

    boolean setActiveDevice(BluetoothDevice bluetoothDevice) throws RemoteException;

    void setAvrcpAbsoluteVolume(int i) throws RemoteException;

    void setCodecConfigPreference(BluetoothDevice bluetoothDevice, BluetoothCodecConfig bluetoothCodecConfig) throws RemoteException;

    void setOptionalCodecsEnabled(BluetoothDevice bluetoothDevice, int i) throws RemoteException;

    boolean setPriority(BluetoothDevice bluetoothDevice, int i) throws RemoteException;

    int supportsOptionalCodecs(BluetoothDevice bluetoothDevice) throws RemoteException;

    /* loaded from: classes.dex */
    public static class Default implements IBluetoothA2dp {
        @Override // android.bluetooth.IBluetoothA2dp
        public boolean connect(BluetoothDevice device) throws RemoteException {
            return false;
        }

        @Override // android.bluetooth.IBluetoothA2dp
        public boolean disconnect(BluetoothDevice device) throws RemoteException {
            return false;
        }

        @Override // android.bluetooth.IBluetoothA2dp
        public List<BluetoothDevice> getConnectedDevices() throws RemoteException {
            return null;
        }

        @Override // android.bluetooth.IBluetoothA2dp
        public List<BluetoothDevice> getDevicesMatchingConnectionStates(int[] states) throws RemoteException {
            return null;
        }

        @Override // android.bluetooth.IBluetoothA2dp
        public int getConnectionState(BluetoothDevice device) throws RemoteException {
            return 0;
        }

        @Override // android.bluetooth.IBluetoothA2dp
        public boolean setActiveDevice(BluetoothDevice device) throws RemoteException {
            return false;
        }

        @Override // android.bluetooth.IBluetoothA2dp
        public BluetoothDevice getActiveDevice() throws RemoteException {
            return null;
        }

        @Override // android.bluetooth.IBluetoothA2dp
        public boolean setPriority(BluetoothDevice device, int priority) throws RemoteException {
            return false;
        }

        @Override // android.bluetooth.IBluetoothA2dp
        public int getPriority(BluetoothDevice device) throws RemoteException {
            return 0;
        }

        @Override // android.bluetooth.IBluetoothA2dp
        public boolean isAvrcpAbsoluteVolumeSupported() throws RemoteException {
            return false;
        }

        @Override // android.bluetooth.IBluetoothA2dp
        public void setAvrcpAbsoluteVolume(int volume) throws RemoteException {
        }

        @Override // android.bluetooth.IBluetoothA2dp
        public boolean isA2dpPlaying(BluetoothDevice device) throws RemoteException {
            return false;
        }

        @Override // android.bluetooth.IBluetoothA2dp
        public BluetoothCodecStatus getCodecStatus(BluetoothDevice device) throws RemoteException {
            return null;
        }

        @Override // android.bluetooth.IBluetoothA2dp
        public void setCodecConfigPreference(BluetoothDevice device, BluetoothCodecConfig codecConfig) throws RemoteException {
        }

        @Override // android.bluetooth.IBluetoothA2dp
        public void enableOptionalCodecs(BluetoothDevice device) throws RemoteException {
        }

        @Override // android.bluetooth.IBluetoothA2dp
        public void disableOptionalCodecs(BluetoothDevice device) throws RemoteException {
        }

        @Override // android.bluetooth.IBluetoothA2dp
        public int supportsOptionalCodecs(BluetoothDevice device) throws RemoteException {
            return 0;
        }

        @Override // android.bluetooth.IBluetoothA2dp
        public int getOptionalCodecsEnabled(BluetoothDevice device) throws RemoteException {
            return 0;
        }

        @Override // android.bluetooth.IBluetoothA2dp
        public void setOptionalCodecsEnabled(BluetoothDevice device, int value) throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* loaded from: classes.dex */
    public static abstract class Stub extends Binder implements IBluetoothA2dp {
        private static final String DESCRIPTOR = "android.bluetooth.IBluetoothA2dp";
        static final int TRANSACTION_connect = 1;
        static final int TRANSACTION_disableOptionalCodecs = 16;
        static final int TRANSACTION_disconnect = 2;
        static final int TRANSACTION_enableOptionalCodecs = 15;
        static final int TRANSACTION_getActiveDevice = 7;
        static final int TRANSACTION_getCodecStatus = 13;
        static final int TRANSACTION_getConnectedDevices = 3;
        static final int TRANSACTION_getConnectionState = 5;
        static final int TRANSACTION_getDevicesMatchingConnectionStates = 4;
        static final int TRANSACTION_getOptionalCodecsEnabled = 18;
        static final int TRANSACTION_getPriority = 9;
        static final int TRANSACTION_isA2dpPlaying = 12;
        static final int TRANSACTION_isAvrcpAbsoluteVolumeSupported = 10;
        static final int TRANSACTION_setActiveDevice = 6;
        static final int TRANSACTION_setAvrcpAbsoluteVolume = 11;
        static final int TRANSACTION_setCodecConfigPreference = 14;
        static final int TRANSACTION_setOptionalCodecsEnabled = 19;
        static final int TRANSACTION_setPriority = 8;
        static final int TRANSACTION_supportsOptionalCodecs = 17;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IBluetoothA2dp asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IBluetoothA2dp)) {
                return (IBluetoothA2dp) iin;
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
                    return "setActiveDevice";
                case 7:
                    return "getActiveDevice";
                case 8:
                    return "setPriority";
                case 9:
                    return "getPriority";
                case 10:
                    return "isAvrcpAbsoluteVolumeSupported";
                case 11:
                    return "setAvrcpAbsoluteVolume";
                case 12:
                    return "isA2dpPlaying";
                case 13:
                    return "getCodecStatus";
                case 14:
                    return "setCodecConfigPreference";
                case 15:
                    return "enableOptionalCodecs";
                case 16:
                    return "disableOptionalCodecs";
                case 17:
                    return "supportsOptionalCodecs";
                case 18:
                    return "getOptionalCodecsEnabled";
                case 19:
                    return "setOptionalCodecsEnabled";
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
                    BluetoothDevice _arg02 = data.readInt() != 0 ? BluetoothDevice.CREATOR.createFromParcel(data) : null;
                    boolean connect = connect(_arg02);
                    reply.writeNoException();
                    reply.writeInt(connect ? 1 : 0);
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    BluetoothDevice _arg03 = data.readInt() != 0 ? BluetoothDevice.CREATOR.createFromParcel(data) : null;
                    boolean disconnect = disconnect(_arg03);
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
                    int[] _arg04 = data.createIntArray();
                    List<BluetoothDevice> _result2 = getDevicesMatchingConnectionStates(_arg04);
                    reply.writeNoException();
                    reply.writeTypedList(_result2);
                    return true;
                case 5:
                    data.enforceInterface(DESCRIPTOR);
                    BluetoothDevice _arg05 = data.readInt() != 0 ? BluetoothDevice.CREATOR.createFromParcel(data) : null;
                    int _result3 = getConnectionState(_arg05);
                    reply.writeNoException();
                    reply.writeInt(_result3);
                    return true;
                case 6:
                    data.enforceInterface(DESCRIPTOR);
                    BluetoothDevice _arg06 = data.readInt() != 0 ? BluetoothDevice.CREATOR.createFromParcel(data) : null;
                    boolean activeDevice = setActiveDevice(_arg06);
                    reply.writeNoException();
                    reply.writeInt(activeDevice ? 1 : 0);
                    return true;
                case 7:
                    data.enforceInterface(DESCRIPTOR);
                    BluetoothDevice _result4 = getActiveDevice();
                    reply.writeNoException();
                    if (_result4 != null) {
                        reply.writeInt(1);
                        _result4.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 8:
                    data.enforceInterface(DESCRIPTOR);
                    BluetoothDevice _arg07 = data.readInt() != 0 ? BluetoothDevice.CREATOR.createFromParcel(data) : null;
                    BluetoothDevice _arg08 = _arg07;
                    int _arg1 = data.readInt();
                    boolean priority = setPriority(_arg08, _arg1);
                    reply.writeNoException();
                    reply.writeInt(priority ? 1 : 0);
                    return true;
                case 9:
                    data.enforceInterface(DESCRIPTOR);
                    BluetoothDevice _arg09 = data.readInt() != 0 ? BluetoothDevice.CREATOR.createFromParcel(data) : null;
                    int _result5 = getPriority(_arg09);
                    reply.writeNoException();
                    reply.writeInt(_result5);
                    return true;
                case 10:
                    data.enforceInterface(DESCRIPTOR);
                    boolean isAvrcpAbsoluteVolumeSupported = isAvrcpAbsoluteVolumeSupported();
                    reply.writeNoException();
                    reply.writeInt(isAvrcpAbsoluteVolumeSupported ? 1 : 0);
                    return true;
                case 11:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg010 = data.readInt();
                    setAvrcpAbsoluteVolume(_arg010);
                    return true;
                case 12:
                    data.enforceInterface(DESCRIPTOR);
                    BluetoothDevice _arg011 = data.readInt() != 0 ? BluetoothDevice.CREATOR.createFromParcel(data) : null;
                    boolean isA2dpPlaying = isA2dpPlaying(_arg011);
                    reply.writeNoException();
                    reply.writeInt(isA2dpPlaying ? 1 : 0);
                    return true;
                case 13:
                    data.enforceInterface(DESCRIPTOR);
                    BluetoothDevice _arg012 = data.readInt() != 0 ? BluetoothDevice.CREATOR.createFromParcel(data) : null;
                    BluetoothCodecStatus _result6 = getCodecStatus(_arg012);
                    reply.writeNoException();
                    if (_result6 != null) {
                        reply.writeInt(1);
                        _result6.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 14:
                    data.enforceInterface(DESCRIPTOR);
                    if (data.readInt() != 0) {
                        _arg0 = BluetoothDevice.CREATOR.createFromParcel(data);
                    } else {
                        _arg0 = null;
                    }
                    BluetoothCodecConfig _arg12 = data.readInt() != 0 ? BluetoothCodecConfig.CREATOR.createFromParcel(data) : null;
                    setCodecConfigPreference(_arg0, _arg12);
                    return true;
                case 15:
                    data.enforceInterface(DESCRIPTOR);
                    BluetoothDevice _arg013 = data.readInt() != 0 ? BluetoothDevice.CREATOR.createFromParcel(data) : null;
                    enableOptionalCodecs(_arg013);
                    return true;
                case 16:
                    data.enforceInterface(DESCRIPTOR);
                    BluetoothDevice _arg014 = data.readInt() != 0 ? BluetoothDevice.CREATOR.createFromParcel(data) : null;
                    disableOptionalCodecs(_arg014);
                    return true;
                case 17:
                    data.enforceInterface(DESCRIPTOR);
                    BluetoothDevice _arg015 = data.readInt() != 0 ? BluetoothDevice.CREATOR.createFromParcel(data) : null;
                    int _result7 = supportsOptionalCodecs(_arg015);
                    reply.writeNoException();
                    reply.writeInt(_result7);
                    return true;
                case 18:
                    data.enforceInterface(DESCRIPTOR);
                    BluetoothDevice _arg016 = data.readInt() != 0 ? BluetoothDevice.CREATOR.createFromParcel(data) : null;
                    int _result8 = getOptionalCodecsEnabled(_arg016);
                    reply.writeNoException();
                    reply.writeInt(_result8);
                    return true;
                case 19:
                    data.enforceInterface(DESCRIPTOR);
                    BluetoothDevice _arg017 = data.readInt() != 0 ? BluetoothDevice.CREATOR.createFromParcel(data) : null;
                    BluetoothDevice _arg018 = _arg017;
                    int _arg13 = data.readInt();
                    setOptionalCodecsEnabled(_arg018, _arg13);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* loaded from: classes.dex */
        private static class Proxy implements IBluetoothA2dp {
            public static IBluetoothA2dp sDefaultImpl;
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

            @Override // android.bluetooth.IBluetoothA2dp
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

            @Override // android.bluetooth.IBluetoothA2dp
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

            @Override // android.bluetooth.IBluetoothA2dp
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

            @Override // android.bluetooth.IBluetoothA2dp
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

            @Override // android.bluetooth.IBluetoothA2dp
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

            @Override // android.bluetooth.IBluetoothA2dp
            public boolean setActiveDevice(BluetoothDevice device) throws RemoteException {
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
                    boolean _status = this.mRemote.transact(6, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setActiveDevice(device);
                    }
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.bluetooth.IBluetoothA2dp
            public BluetoothDevice getActiveDevice() throws RemoteException {
                BluetoothDevice _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(7, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getActiveDevice();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = BluetoothDevice.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.bluetooth.IBluetoothA2dp
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
                    boolean _status = this.mRemote.transact(8, _data, _reply, 0);
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

            @Override // android.bluetooth.IBluetoothA2dp
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
                    boolean _status = this.mRemote.transact(9, _data, _reply, 0);
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

            @Override // android.bluetooth.IBluetoothA2dp
            public boolean isAvrcpAbsoluteVolumeSupported() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(10, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isAvrcpAbsoluteVolumeSupported();
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.bluetooth.IBluetoothA2dp
            public void setAvrcpAbsoluteVolume(int volume) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(volume);
                    boolean _status = this.mRemote.transact(11, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setAvrcpAbsoluteVolume(volume);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.bluetooth.IBluetoothA2dp
            public boolean isA2dpPlaying(BluetoothDevice device) throws RemoteException {
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
                    boolean _status = this.mRemote.transact(12, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isA2dpPlaying(device);
                    }
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.bluetooth.IBluetoothA2dp
            public BluetoothCodecStatus getCodecStatus(BluetoothDevice device) throws RemoteException {
                BluetoothCodecStatus _result;
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
                    boolean _status = this.mRemote.transact(13, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getCodecStatus(device);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = BluetoothCodecStatus.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.bluetooth.IBluetoothA2dp
            public void setCodecConfigPreference(BluetoothDevice device, BluetoothCodecConfig codecConfig) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (device != null) {
                        _data.writeInt(1);
                        device.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (codecConfig != null) {
                        _data.writeInt(1);
                        codecConfig.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(14, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setCodecConfigPreference(device, codecConfig);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.bluetooth.IBluetoothA2dp
            public void enableOptionalCodecs(BluetoothDevice device) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (device != null) {
                        _data.writeInt(1);
                        device.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(15, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().enableOptionalCodecs(device);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.bluetooth.IBluetoothA2dp
            public void disableOptionalCodecs(BluetoothDevice device) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (device != null) {
                        _data.writeInt(1);
                        device.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(16, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().disableOptionalCodecs(device);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.bluetooth.IBluetoothA2dp
            public int supportsOptionalCodecs(BluetoothDevice device) throws RemoteException {
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
                    boolean _status = this.mRemote.transact(17, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().supportsOptionalCodecs(device);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.bluetooth.IBluetoothA2dp
            public int getOptionalCodecsEnabled(BluetoothDevice device) throws RemoteException {
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
                    boolean _status = this.mRemote.transact(18, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getOptionalCodecsEnabled(device);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.bluetooth.IBluetoothA2dp
            public void setOptionalCodecsEnabled(BluetoothDevice device, int value) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (device != null) {
                        _data.writeInt(1);
                        device.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(value);
                    boolean _status = this.mRemote.transact(19, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setOptionalCodecsEnabled(device, value);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IBluetoothA2dp impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static IBluetoothA2dp getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
