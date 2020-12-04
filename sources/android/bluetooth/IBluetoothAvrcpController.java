package android.bluetooth;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.List;

public interface IBluetoothAvrcpController extends IInterface {
    List<BluetoothDevice> getConnectedDevices() throws RemoteException;

    int getConnectionState(BluetoothDevice bluetoothDevice) throws RemoteException;

    List<BluetoothDevice> getDevicesMatchingConnectionStates(int[] iArr) throws RemoteException;

    BluetoothAvrcpPlayerSettings getPlayerSettings(BluetoothDevice bluetoothDevice) throws RemoteException;

    void sendGroupNavigationCmd(BluetoothDevice bluetoothDevice, int i, int i2) throws RemoteException;

    boolean setPlayerApplicationSetting(BluetoothAvrcpPlayerSettings bluetoothAvrcpPlayerSettings) throws RemoteException;

    public static class Default implements IBluetoothAvrcpController {
        public List<BluetoothDevice> getConnectedDevices() throws RemoteException {
            return null;
        }

        public List<BluetoothDevice> getDevicesMatchingConnectionStates(int[] states) throws RemoteException {
            return null;
        }

        public int getConnectionState(BluetoothDevice device) throws RemoteException {
            return 0;
        }

        public BluetoothAvrcpPlayerSettings getPlayerSettings(BluetoothDevice device) throws RemoteException {
            return null;
        }

        public boolean setPlayerApplicationSetting(BluetoothAvrcpPlayerSettings plAppSetting) throws RemoteException {
            return false;
        }

        public void sendGroupNavigationCmd(BluetoothDevice device, int keyCode, int keyState) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

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
            if (iin == null || !(iin instanceof IBluetoothAvrcpController)) {
                return new Proxy(obj);
            }
            return (IBluetoothAvrcpController) iin;
        }

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

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v4, resolved type: android.bluetooth.BluetoothDevice} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v8, resolved type: android.bluetooth.BluetoothDevice} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v12, resolved type: android.bluetooth.BluetoothAvrcpPlayerSettings} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v16, resolved type: android.bluetooth.BluetoothDevice} */
        /* JADX WARNING: type inference failed for: r1v1 */
        /* JADX WARNING: type inference failed for: r1v21 */
        /* JADX WARNING: type inference failed for: r1v22 */
        /* JADX WARNING: type inference failed for: r1v23 */
        /* JADX WARNING: type inference failed for: r1v24 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r6, android.os.Parcel r7, android.os.Parcel r8, int r9) throws android.os.RemoteException {
            /*
                r5 = this;
                java.lang.String r0 = "android.bluetooth.IBluetoothAvrcpController"
                r1 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r2 = 1
                if (r6 == r1) goto L_0x00b7
                r1 = 0
                switch(r6) {
                    case 1: goto L_0x00a9;
                    case 2: goto L_0x0097;
                    case 3: goto L_0x0079;
                    case 4: goto L_0x0051;
                    case 5: goto L_0x0033;
                    case 6: goto L_0x0011;
                    default: goto L_0x000c;
                }
            L_0x000c:
                boolean r1 = super.onTransact(r6, r7, r8, r9)
                return r1
            L_0x0011:
                r7.enforceInterface(r0)
                int r3 = r7.readInt()
                if (r3 == 0) goto L_0x0023
                android.os.Parcelable$Creator<android.bluetooth.BluetoothDevice> r1 = android.bluetooth.BluetoothDevice.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r7)
                android.bluetooth.BluetoothDevice r1 = (android.bluetooth.BluetoothDevice) r1
                goto L_0x0024
            L_0x0023:
            L_0x0024:
                int r3 = r7.readInt()
                int r4 = r7.readInt()
                r5.sendGroupNavigationCmd(r1, r3, r4)
                r8.writeNoException()
                return r2
            L_0x0033:
                r7.enforceInterface(r0)
                int r3 = r7.readInt()
                if (r3 == 0) goto L_0x0045
                android.os.Parcelable$Creator<android.bluetooth.BluetoothAvrcpPlayerSettings> r1 = android.bluetooth.BluetoothAvrcpPlayerSettings.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r7)
                android.bluetooth.BluetoothAvrcpPlayerSettings r1 = (android.bluetooth.BluetoothAvrcpPlayerSettings) r1
                goto L_0x0046
            L_0x0045:
            L_0x0046:
                boolean r3 = r5.setPlayerApplicationSetting(r1)
                r8.writeNoException()
                r8.writeInt(r3)
                return r2
            L_0x0051:
                r7.enforceInterface(r0)
                int r3 = r7.readInt()
                if (r3 == 0) goto L_0x0063
                android.os.Parcelable$Creator<android.bluetooth.BluetoothDevice> r1 = android.bluetooth.BluetoothDevice.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r7)
                android.bluetooth.BluetoothDevice r1 = (android.bluetooth.BluetoothDevice) r1
                goto L_0x0064
            L_0x0063:
            L_0x0064:
                android.bluetooth.BluetoothAvrcpPlayerSettings r3 = r5.getPlayerSettings(r1)
                r8.writeNoException()
                if (r3 == 0) goto L_0x0074
                r8.writeInt(r2)
                r3.writeToParcel(r8, r2)
                goto L_0x0078
            L_0x0074:
                r4 = 0
                r8.writeInt(r4)
            L_0x0078:
                return r2
            L_0x0079:
                r7.enforceInterface(r0)
                int r3 = r7.readInt()
                if (r3 == 0) goto L_0x008b
                android.os.Parcelable$Creator<android.bluetooth.BluetoothDevice> r1 = android.bluetooth.BluetoothDevice.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r7)
                android.bluetooth.BluetoothDevice r1 = (android.bluetooth.BluetoothDevice) r1
                goto L_0x008c
            L_0x008b:
            L_0x008c:
                int r3 = r5.getConnectionState(r1)
                r8.writeNoException()
                r8.writeInt(r3)
                return r2
            L_0x0097:
                r7.enforceInterface(r0)
                int[] r1 = r7.createIntArray()
                java.util.List r3 = r5.getDevicesMatchingConnectionStates(r1)
                r8.writeNoException()
                r8.writeTypedList(r3)
                return r2
            L_0x00a9:
                r7.enforceInterface(r0)
                java.util.List r1 = r5.getConnectedDevices()
                r8.writeNoException()
                r8.writeTypedList(r1)
                return r2
            L_0x00b7:
                r8.writeString(r0)
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: android.bluetooth.IBluetoothAvrcpController.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IBluetoothAvrcpController {
            public static IBluetoothAvrcpController sDefaultImpl;
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

            public List<BluetoothDevice> getConnectedDevices() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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
                    if (!this.mRemote.transact(2, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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
                    if (!this.mRemote.transact(3, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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
                    if (!this.mRemote.transact(4, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPlayerSettings(device);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = BluetoothAvrcpPlayerSettings.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    BluetoothAvrcpPlayerSettings _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean setPlayerApplicationSetting(BluetoothAvrcpPlayerSettings plAppSetting) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (plAppSetting != null) {
                        _data.writeInt(1);
                        plAppSetting.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(5, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setPlayerApplicationSetting(plAppSetting);
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
                    if (this.mRemote.transact(6, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().sendGroupNavigationCmd(device, keyCode, keyState);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IBluetoothAvrcpController impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IBluetoothAvrcpController getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
