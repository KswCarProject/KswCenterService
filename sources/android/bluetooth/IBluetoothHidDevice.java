package android.bluetooth;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.List;

public interface IBluetoothHidDevice extends IInterface {
    boolean connect(BluetoothDevice bluetoothDevice) throws RemoteException;

    boolean disconnect(BluetoothDevice bluetoothDevice) throws RemoteException;

    List<BluetoothDevice> getConnectedDevices() throws RemoteException;

    int getConnectionState(BluetoothDevice bluetoothDevice) throws RemoteException;

    List<BluetoothDevice> getDevicesMatchingConnectionStates(int[] iArr) throws RemoteException;

    String getUserAppName() throws RemoteException;

    boolean registerApp(BluetoothHidDeviceAppSdpSettings bluetoothHidDeviceAppSdpSettings, BluetoothHidDeviceAppQosSettings bluetoothHidDeviceAppQosSettings, BluetoothHidDeviceAppQosSettings bluetoothHidDeviceAppQosSettings2, IBluetoothHidDeviceCallback iBluetoothHidDeviceCallback) throws RemoteException;

    boolean replyReport(BluetoothDevice bluetoothDevice, byte b, byte b2, byte[] bArr) throws RemoteException;

    boolean reportError(BluetoothDevice bluetoothDevice, byte b) throws RemoteException;

    boolean sendReport(BluetoothDevice bluetoothDevice, int i, byte[] bArr) throws RemoteException;

    boolean unplug(BluetoothDevice bluetoothDevice) throws RemoteException;

    boolean unregisterApp() throws RemoteException;

    public static class Default implements IBluetoothHidDevice {
        public boolean registerApp(BluetoothHidDeviceAppSdpSettings sdp, BluetoothHidDeviceAppQosSettings inQos, BluetoothHidDeviceAppQosSettings outQos, IBluetoothHidDeviceCallback callback) throws RemoteException {
            return false;
        }

        public boolean unregisterApp() throws RemoteException {
            return false;
        }

        public boolean sendReport(BluetoothDevice device, int id, byte[] data) throws RemoteException {
            return false;
        }

        public boolean replyReport(BluetoothDevice device, byte type, byte id, byte[] data) throws RemoteException {
            return false;
        }

        public boolean reportError(BluetoothDevice device, byte error) throws RemoteException {
            return false;
        }

        public boolean unplug(BluetoothDevice device) throws RemoteException {
            return false;
        }

        public boolean connect(BluetoothDevice device) throws RemoteException {
            return false;
        }

        public boolean disconnect(BluetoothDevice device) throws RemoteException {
            return false;
        }

        public List<BluetoothDevice> getConnectedDevices() throws RemoteException {
            return null;
        }

        public List<BluetoothDevice> getDevicesMatchingConnectionStates(int[] states) throws RemoteException {
            return null;
        }

        public int getConnectionState(BluetoothDevice device) throws RemoteException {
            return 0;
        }

        public String getUserAppName() throws RemoteException {
            return null;
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IBluetoothHidDevice {
        private static final String DESCRIPTOR = "android.bluetooth.IBluetoothHidDevice";
        static final int TRANSACTION_connect = 7;
        static final int TRANSACTION_disconnect = 8;
        static final int TRANSACTION_getConnectedDevices = 9;
        static final int TRANSACTION_getConnectionState = 11;
        static final int TRANSACTION_getDevicesMatchingConnectionStates = 10;
        static final int TRANSACTION_getUserAppName = 12;
        static final int TRANSACTION_registerApp = 1;
        static final int TRANSACTION_replyReport = 4;
        static final int TRANSACTION_reportError = 5;
        static final int TRANSACTION_sendReport = 3;
        static final int TRANSACTION_unplug = 6;
        static final int TRANSACTION_unregisterApp = 2;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IBluetoothHidDevice asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IBluetoothHidDevice)) {
                return new Proxy(obj);
            }
            return (IBluetoothHidDevice) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "registerApp";
                case 2:
                    return "unregisterApp";
                case 3:
                    return "sendReport";
                case 4:
                    return "replyReport";
                case 5:
                    return "reportError";
                case 6:
                    return "unplug";
                case 7:
                    return "connect";
                case 8:
                    return "disconnect";
                case 9:
                    return "getConnectedDevices";
                case 10:
                    return "getDevicesMatchingConnectionStates";
                case 11:
                    return "getConnectionState";
                case 12:
                    return "getUserAppName";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v2, resolved type: android.bluetooth.BluetoothHidDeviceAppQosSettings} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v7, resolved type: android.bluetooth.BluetoothDevice} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v11, resolved type: android.bluetooth.BluetoothDevice} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v15, resolved type: android.bluetooth.BluetoothDevice} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v19, resolved type: android.bluetooth.BluetoothDevice} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v23, resolved type: android.bluetooth.BluetoothDevice} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v27, resolved type: android.bluetooth.BluetoothDevice} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v33, resolved type: android.bluetooth.BluetoothDevice} */
        /* JADX WARNING: type inference failed for: r1v1 */
        /* JADX WARNING: type inference failed for: r1v39 */
        /* JADX WARNING: type inference failed for: r1v40 */
        /* JADX WARNING: type inference failed for: r1v41 */
        /* JADX WARNING: type inference failed for: r1v42 */
        /* JADX WARNING: type inference failed for: r1v43 */
        /* JADX WARNING: type inference failed for: r1v44 */
        /* JADX WARNING: type inference failed for: r1v45 */
        /* JADX WARNING: type inference failed for: r1v46 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r8, android.os.Parcel r9, android.os.Parcel r10, int r11) throws android.os.RemoteException {
            /*
                r7 = this;
                java.lang.String r0 = "android.bluetooth.IBluetoothHidDevice"
                r1 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r2 = 1
                if (r8 == r1) goto L_0x017d
                r1 = 0
                switch(r8) {
                    case 1: goto L_0x0137;
                    case 2: goto L_0x0129;
                    case 3: goto L_0x0103;
                    case 4: goto L_0x00d9;
                    case 5: goto L_0x00b7;
                    case 6: goto L_0x0099;
                    case 7: goto L_0x007b;
                    case 8: goto L_0x005d;
                    case 9: goto L_0x004f;
                    case 10: goto L_0x003d;
                    case 11: goto L_0x001f;
                    case 12: goto L_0x0011;
                    default: goto L_0x000c;
                }
            L_0x000c:
                boolean r1 = super.onTransact(r8, r9, r10, r11)
                return r1
            L_0x0011:
                r9.enforceInterface(r0)
                java.lang.String r1 = r7.getUserAppName()
                r10.writeNoException()
                r10.writeString(r1)
                return r2
            L_0x001f:
                r9.enforceInterface(r0)
                int r3 = r9.readInt()
                if (r3 == 0) goto L_0x0031
                android.os.Parcelable$Creator<android.bluetooth.BluetoothDevice> r1 = android.bluetooth.BluetoothDevice.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                android.bluetooth.BluetoothDevice r1 = (android.bluetooth.BluetoothDevice) r1
                goto L_0x0032
            L_0x0031:
            L_0x0032:
                int r3 = r7.getConnectionState(r1)
                r10.writeNoException()
                r10.writeInt(r3)
                return r2
            L_0x003d:
                r9.enforceInterface(r0)
                int[] r1 = r9.createIntArray()
                java.util.List r3 = r7.getDevicesMatchingConnectionStates(r1)
                r10.writeNoException()
                r10.writeTypedList(r3)
                return r2
            L_0x004f:
                r9.enforceInterface(r0)
                java.util.List r1 = r7.getConnectedDevices()
                r10.writeNoException()
                r10.writeTypedList(r1)
                return r2
            L_0x005d:
                r9.enforceInterface(r0)
                int r3 = r9.readInt()
                if (r3 == 0) goto L_0x006f
                android.os.Parcelable$Creator<android.bluetooth.BluetoothDevice> r1 = android.bluetooth.BluetoothDevice.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                android.bluetooth.BluetoothDevice r1 = (android.bluetooth.BluetoothDevice) r1
                goto L_0x0070
            L_0x006f:
            L_0x0070:
                boolean r3 = r7.disconnect(r1)
                r10.writeNoException()
                r10.writeInt(r3)
                return r2
            L_0x007b:
                r9.enforceInterface(r0)
                int r3 = r9.readInt()
                if (r3 == 0) goto L_0x008d
                android.os.Parcelable$Creator<android.bluetooth.BluetoothDevice> r1 = android.bluetooth.BluetoothDevice.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                android.bluetooth.BluetoothDevice r1 = (android.bluetooth.BluetoothDevice) r1
                goto L_0x008e
            L_0x008d:
            L_0x008e:
                boolean r3 = r7.connect(r1)
                r10.writeNoException()
                r10.writeInt(r3)
                return r2
            L_0x0099:
                r9.enforceInterface(r0)
                int r3 = r9.readInt()
                if (r3 == 0) goto L_0x00ab
                android.os.Parcelable$Creator<android.bluetooth.BluetoothDevice> r1 = android.bluetooth.BluetoothDevice.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                android.bluetooth.BluetoothDevice r1 = (android.bluetooth.BluetoothDevice) r1
                goto L_0x00ac
            L_0x00ab:
            L_0x00ac:
                boolean r3 = r7.unplug(r1)
                r10.writeNoException()
                r10.writeInt(r3)
                return r2
            L_0x00b7:
                r9.enforceInterface(r0)
                int r3 = r9.readInt()
                if (r3 == 0) goto L_0x00c9
                android.os.Parcelable$Creator<android.bluetooth.BluetoothDevice> r1 = android.bluetooth.BluetoothDevice.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                android.bluetooth.BluetoothDevice r1 = (android.bluetooth.BluetoothDevice) r1
                goto L_0x00ca
            L_0x00c9:
            L_0x00ca:
                byte r3 = r9.readByte()
                boolean r4 = r7.reportError(r1, r3)
                r10.writeNoException()
                r10.writeInt(r4)
                return r2
            L_0x00d9:
                r9.enforceInterface(r0)
                int r3 = r9.readInt()
                if (r3 == 0) goto L_0x00eb
                android.os.Parcelable$Creator<android.bluetooth.BluetoothDevice> r1 = android.bluetooth.BluetoothDevice.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                android.bluetooth.BluetoothDevice r1 = (android.bluetooth.BluetoothDevice) r1
                goto L_0x00ec
            L_0x00eb:
            L_0x00ec:
                byte r3 = r9.readByte()
                byte r4 = r9.readByte()
                byte[] r5 = r9.createByteArray()
                boolean r6 = r7.replyReport(r1, r3, r4, r5)
                r10.writeNoException()
                r10.writeInt(r6)
                return r2
            L_0x0103:
                r9.enforceInterface(r0)
                int r3 = r9.readInt()
                if (r3 == 0) goto L_0x0115
                android.os.Parcelable$Creator<android.bluetooth.BluetoothDevice> r1 = android.bluetooth.BluetoothDevice.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                android.bluetooth.BluetoothDevice r1 = (android.bluetooth.BluetoothDevice) r1
                goto L_0x0116
            L_0x0115:
            L_0x0116:
                int r3 = r9.readInt()
                byte[] r4 = r9.createByteArray()
                boolean r5 = r7.sendReport(r1, r3, r4)
                r10.writeNoException()
                r10.writeInt(r5)
                return r2
            L_0x0129:
                r9.enforceInterface(r0)
                boolean r1 = r7.unregisterApp()
                r10.writeNoException()
                r10.writeInt(r1)
                return r2
            L_0x0137:
                r9.enforceInterface(r0)
                int r3 = r9.readInt()
                if (r3 == 0) goto L_0x0149
                android.os.Parcelable$Creator<android.bluetooth.BluetoothHidDeviceAppSdpSettings> r3 = android.bluetooth.BluetoothHidDeviceAppSdpSettings.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r9)
                android.bluetooth.BluetoothHidDeviceAppSdpSettings r3 = (android.bluetooth.BluetoothHidDeviceAppSdpSettings) r3
                goto L_0x014a
            L_0x0149:
                r3 = r1
            L_0x014a:
                int r4 = r9.readInt()
                if (r4 == 0) goto L_0x0159
                android.os.Parcelable$Creator<android.bluetooth.BluetoothHidDeviceAppQosSettings> r4 = android.bluetooth.BluetoothHidDeviceAppQosSettings.CREATOR
                java.lang.Object r4 = r4.createFromParcel(r9)
                android.bluetooth.BluetoothHidDeviceAppQosSettings r4 = (android.bluetooth.BluetoothHidDeviceAppQosSettings) r4
                goto L_0x015a
            L_0x0159:
                r4 = r1
            L_0x015a:
                int r5 = r9.readInt()
                if (r5 == 0) goto L_0x0169
                android.os.Parcelable$Creator<android.bluetooth.BluetoothHidDeviceAppQosSettings> r1 = android.bluetooth.BluetoothHidDeviceAppQosSettings.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                android.bluetooth.BluetoothHidDeviceAppQosSettings r1 = (android.bluetooth.BluetoothHidDeviceAppQosSettings) r1
                goto L_0x016a
            L_0x0169:
            L_0x016a:
                android.os.IBinder r5 = r9.readStrongBinder()
                android.bluetooth.IBluetoothHidDeviceCallback r5 = android.bluetooth.IBluetoothHidDeviceCallback.Stub.asInterface(r5)
                boolean r6 = r7.registerApp(r3, r4, r1, r5)
                r10.writeNoException()
                r10.writeInt(r6)
                return r2
            L_0x017d:
                r10.writeString(r0)
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: android.bluetooth.IBluetoothHidDevice.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IBluetoothHidDevice {
            public static IBluetoothHidDevice sDefaultImpl;
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

            public boolean registerApp(BluetoothHidDeviceAppSdpSettings sdp, BluetoothHidDeviceAppQosSettings inQos, BluetoothHidDeviceAppQosSettings outQos, IBluetoothHidDeviceCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (sdp != null) {
                        _data.writeInt(1);
                        sdp.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (inQos != null) {
                        _data.writeInt(1);
                        inQos.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (outQos != null) {
                        _data.writeInt(1);
                        outQos.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (!this.mRemote.transact(1, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().registerApp(sdp, inQos, outQos, callback);
                    }
                    _reply.readException();
                    if (_reply.readInt() == 0) {
                        _result = false;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean unregisterApp() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(2, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().unregisterApp();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _status = z;
                    _reply.recycle();
                    _data.recycle();
                    return _status;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean sendReport(BluetoothDevice device, int id, byte[] data) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (device != null) {
                        _data.writeInt(1);
                        device.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(id);
                    _data.writeByteArray(data);
                    if (!this.mRemote.transact(3, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().sendReport(device, id, data);
                    }
                    _reply.readException();
                    if (_reply.readInt() == 0) {
                        _result = false;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean replyReport(BluetoothDevice device, byte type, byte id, byte[] data) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (device != null) {
                        _data.writeInt(1);
                        device.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeByte(type);
                    _data.writeByte(id);
                    _data.writeByteArray(data);
                    if (!this.mRemote.transact(4, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().replyReport(device, type, id, data);
                    }
                    _reply.readException();
                    if (_reply.readInt() == 0) {
                        _result = false;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean reportError(BluetoothDevice device, byte error) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (device != null) {
                        _data.writeInt(1);
                        device.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeByte(error);
                    if (!this.mRemote.transact(5, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reportError(device, error);
                    }
                    _reply.readException();
                    if (_reply.readInt() == 0) {
                        _result = false;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean unplug(BluetoothDevice device) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (device != null) {
                        _data.writeInt(1);
                        device.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(6, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().unplug(device);
                    }
                    _reply.readException();
                    if (_reply.readInt() == 0) {
                        _result = false;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean connect(BluetoothDevice device) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (device != null) {
                        _data.writeInt(1);
                        device.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(7, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().connect(device);
                    }
                    _reply.readException();
                    if (_reply.readInt() == 0) {
                        _result = false;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean disconnect(BluetoothDevice device) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (device != null) {
                        _data.writeInt(1);
                        device.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(8, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().disconnect(device);
                    }
                    _reply.readException();
                    if (_reply.readInt() == 0) {
                        _result = false;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public List<BluetoothDevice> getConnectedDevices() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(9, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getConnectedDevices();
                    }
                    _reply.readException();
                    List<BluetoothDevice> _result = _reply.createTypedArrayList(BluetoothDevice.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public List<BluetoothDevice> getDevicesMatchingConnectionStates(int[] states) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeIntArray(states);
                    if (!this.mRemote.transact(10, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getDevicesMatchingConnectionStates(states);
                    }
                    _reply.readException();
                    List<BluetoothDevice> _result = _reply.createTypedArrayList(BluetoothDevice.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

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
                    if (!this.mRemote.transact(11, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getConnectionState(device);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String getUserAppName() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(12, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getUserAppName();
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IBluetoothHidDevice impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IBluetoothHidDevice getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
