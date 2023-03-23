package android.bluetooth;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.List;

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

    public static class Default implements IBluetoothA2dp {
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

        public boolean setActiveDevice(BluetoothDevice device) throws RemoteException {
            return false;
        }

        public BluetoothDevice getActiveDevice() throws RemoteException {
            return null;
        }

        public boolean setPriority(BluetoothDevice device, int priority) throws RemoteException {
            return false;
        }

        public int getPriority(BluetoothDevice device) throws RemoteException {
            return 0;
        }

        public boolean isAvrcpAbsoluteVolumeSupported() throws RemoteException {
            return false;
        }

        public void setAvrcpAbsoluteVolume(int volume) throws RemoteException {
        }

        public boolean isA2dpPlaying(BluetoothDevice device) throws RemoteException {
            return false;
        }

        public BluetoothCodecStatus getCodecStatus(BluetoothDevice device) throws RemoteException {
            return null;
        }

        public void setCodecConfigPreference(BluetoothDevice device, BluetoothCodecConfig codecConfig) throws RemoteException {
        }

        public void enableOptionalCodecs(BluetoothDevice device) throws RemoteException {
        }

        public void disableOptionalCodecs(BluetoothDevice device) throws RemoteException {
        }

        public int supportsOptionalCodecs(BluetoothDevice device) throws RemoteException {
            return 0;
        }

        public int getOptionalCodecsEnabled(BluetoothDevice device) throws RemoteException {
            return 0;
        }

        public void setOptionalCodecsEnabled(BluetoothDevice device, int value) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

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
            if (iin == null || !(iin instanceof IBluetoothA2dp)) {
                return new Proxy(obj);
            }
            return (IBluetoothA2dp) iin;
        }

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

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v31, resolved type: android.bluetooth.BluetoothDevice} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v35, resolved type: android.bluetooth.BluetoothCodecConfig} */
        /* JADX WARNING: type inference failed for: r3v0 */
        /* JADX WARNING: type inference failed for: r3v1 */
        /* JADX WARNING: type inference failed for: r3v5 */
        /* JADX WARNING: type inference failed for: r3v10 */
        /* JADX WARNING: type inference failed for: r3v14 */
        /* JADX WARNING: type inference failed for: r3v19 */
        /* JADX WARNING: type inference failed for: r3v23 */
        /* JADX WARNING: type inference failed for: r3v27 */
        /* JADX WARNING: type inference failed for: r3v39 */
        /* JADX WARNING: type inference failed for: r3v42 */
        /* JADX WARNING: type inference failed for: r3v45 */
        /* JADX WARNING: type inference failed for: r3v49 */
        /* JADX WARNING: type inference failed for: r3v53 */
        /* JADX WARNING: type inference failed for: r3v57 */
        /* JADX WARNING: type inference failed for: r3v58 */
        /* JADX WARNING: type inference failed for: r3v59 */
        /* JADX WARNING: type inference failed for: r3v60 */
        /* JADX WARNING: type inference failed for: r3v61 */
        /* JADX WARNING: type inference failed for: r3v62 */
        /* JADX WARNING: type inference failed for: r3v63 */
        /* JADX WARNING: type inference failed for: r3v64 */
        /* JADX WARNING: type inference failed for: r3v65 */
        /* JADX WARNING: type inference failed for: r3v66 */
        /* JADX WARNING: type inference failed for: r3v67 */
        /* JADX WARNING: type inference failed for: r3v68 */
        /* JADX WARNING: type inference failed for: r3v69 */
        /* JADX WARNING: type inference failed for: r3v70 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r6, android.os.Parcel r7, android.os.Parcel r8, int r9) throws android.os.RemoteException {
            /*
                r5 = this;
                java.lang.String r0 = "android.bluetooth.IBluetoothA2dp"
                r1 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r2 = 1
                if (r6 == r1) goto L_0x0223
                r1 = 0
                r3 = 0
                switch(r6) {
                    case 1: goto L_0x0203;
                    case 2: goto L_0x01e3;
                    case 3: goto L_0x01d5;
                    case 4: goto L_0x01c3;
                    case 5: goto L_0x01a3;
                    case 6: goto L_0x0183;
                    case 7: goto L_0x016c;
                    case 8: goto L_0x0148;
                    case 9: goto L_0x0128;
                    case 10: goto L_0x011a;
                    case 11: goto L_0x010f;
                    case 12: goto L_0x00ef;
                    case 13: goto L_0x00c8;
                    case 14: goto L_0x00a1;
                    case 15: goto L_0x0088;
                    case 16: goto L_0x006f;
                    case 17: goto L_0x004f;
                    case 18: goto L_0x002f;
                    case 19: goto L_0x0012;
                    default: goto L_0x000d;
                }
            L_0x000d:
                boolean r1 = super.onTransact(r6, r7, r8, r9)
                return r1
            L_0x0012:
                r7.enforceInterface(r0)
                int r1 = r7.readInt()
                if (r1 == 0) goto L_0x0025
                android.os.Parcelable$Creator<android.bluetooth.BluetoothDevice> r1 = android.bluetooth.BluetoothDevice.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r7)
                r3 = r1
                android.bluetooth.BluetoothDevice r3 = (android.bluetooth.BluetoothDevice) r3
                goto L_0x0026
            L_0x0025:
            L_0x0026:
                r1 = r3
                int r3 = r7.readInt()
                r5.setOptionalCodecsEnabled(r1, r3)
                return r2
            L_0x002f:
                r7.enforceInterface(r0)
                int r1 = r7.readInt()
                if (r1 == 0) goto L_0x0042
                android.os.Parcelable$Creator<android.bluetooth.BluetoothDevice> r1 = android.bluetooth.BluetoothDevice.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r7)
                r3 = r1
                android.bluetooth.BluetoothDevice r3 = (android.bluetooth.BluetoothDevice) r3
                goto L_0x0043
            L_0x0042:
            L_0x0043:
                r1 = r3
                int r3 = r5.getOptionalCodecsEnabled(r1)
                r8.writeNoException()
                r8.writeInt(r3)
                return r2
            L_0x004f:
                r7.enforceInterface(r0)
                int r1 = r7.readInt()
                if (r1 == 0) goto L_0x0062
                android.os.Parcelable$Creator<android.bluetooth.BluetoothDevice> r1 = android.bluetooth.BluetoothDevice.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r7)
                r3 = r1
                android.bluetooth.BluetoothDevice r3 = (android.bluetooth.BluetoothDevice) r3
                goto L_0x0063
            L_0x0062:
            L_0x0063:
                r1 = r3
                int r3 = r5.supportsOptionalCodecs(r1)
                r8.writeNoException()
                r8.writeInt(r3)
                return r2
            L_0x006f:
                r7.enforceInterface(r0)
                int r1 = r7.readInt()
                if (r1 == 0) goto L_0x0082
                android.os.Parcelable$Creator<android.bluetooth.BluetoothDevice> r1 = android.bluetooth.BluetoothDevice.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r7)
                r3 = r1
                android.bluetooth.BluetoothDevice r3 = (android.bluetooth.BluetoothDevice) r3
                goto L_0x0083
            L_0x0082:
            L_0x0083:
                r1 = r3
                r5.disableOptionalCodecs(r1)
                return r2
            L_0x0088:
                r7.enforceInterface(r0)
                int r1 = r7.readInt()
                if (r1 == 0) goto L_0x009b
                android.os.Parcelable$Creator<android.bluetooth.BluetoothDevice> r1 = android.bluetooth.BluetoothDevice.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r7)
                r3 = r1
                android.bluetooth.BluetoothDevice r3 = (android.bluetooth.BluetoothDevice) r3
                goto L_0x009c
            L_0x009b:
            L_0x009c:
                r1 = r3
                r5.enableOptionalCodecs(r1)
                return r2
            L_0x00a1:
                r7.enforceInterface(r0)
                int r1 = r7.readInt()
                if (r1 == 0) goto L_0x00b3
                android.os.Parcelable$Creator<android.bluetooth.BluetoothDevice> r1 = android.bluetooth.BluetoothDevice.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r7)
                android.bluetooth.BluetoothDevice r1 = (android.bluetooth.BluetoothDevice) r1
                goto L_0x00b4
            L_0x00b3:
                r1 = r3
            L_0x00b4:
                int r4 = r7.readInt()
                if (r4 == 0) goto L_0x00c3
                android.os.Parcelable$Creator<android.bluetooth.BluetoothCodecConfig> r3 = android.bluetooth.BluetoothCodecConfig.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r7)
                android.bluetooth.BluetoothCodecConfig r3 = (android.bluetooth.BluetoothCodecConfig) r3
                goto L_0x00c4
            L_0x00c3:
            L_0x00c4:
                r5.setCodecConfigPreference(r1, r3)
                return r2
            L_0x00c8:
                r7.enforceInterface(r0)
                int r4 = r7.readInt()
                if (r4 == 0) goto L_0x00da
                android.os.Parcelable$Creator<android.bluetooth.BluetoothDevice> r3 = android.bluetooth.BluetoothDevice.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r7)
                android.bluetooth.BluetoothDevice r3 = (android.bluetooth.BluetoothDevice) r3
                goto L_0x00db
            L_0x00da:
            L_0x00db:
                android.bluetooth.BluetoothCodecStatus r4 = r5.getCodecStatus(r3)
                r8.writeNoException()
                if (r4 == 0) goto L_0x00eb
                r8.writeInt(r2)
                r4.writeToParcel(r8, r2)
                goto L_0x00ee
            L_0x00eb:
                r8.writeInt(r1)
            L_0x00ee:
                return r2
            L_0x00ef:
                r7.enforceInterface(r0)
                int r1 = r7.readInt()
                if (r1 == 0) goto L_0x0102
                android.os.Parcelable$Creator<android.bluetooth.BluetoothDevice> r1 = android.bluetooth.BluetoothDevice.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r7)
                r3 = r1
                android.bluetooth.BluetoothDevice r3 = (android.bluetooth.BluetoothDevice) r3
                goto L_0x0103
            L_0x0102:
            L_0x0103:
                r1 = r3
                boolean r3 = r5.isA2dpPlaying(r1)
                r8.writeNoException()
                r8.writeInt(r3)
                return r2
            L_0x010f:
                r7.enforceInterface(r0)
                int r1 = r7.readInt()
                r5.setAvrcpAbsoluteVolume(r1)
                return r2
            L_0x011a:
                r7.enforceInterface(r0)
                boolean r1 = r5.isAvrcpAbsoluteVolumeSupported()
                r8.writeNoException()
                r8.writeInt(r1)
                return r2
            L_0x0128:
                r7.enforceInterface(r0)
                int r1 = r7.readInt()
                if (r1 == 0) goto L_0x013b
                android.os.Parcelable$Creator<android.bluetooth.BluetoothDevice> r1 = android.bluetooth.BluetoothDevice.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r7)
                r3 = r1
                android.bluetooth.BluetoothDevice r3 = (android.bluetooth.BluetoothDevice) r3
                goto L_0x013c
            L_0x013b:
            L_0x013c:
                r1 = r3
                int r3 = r5.getPriority(r1)
                r8.writeNoException()
                r8.writeInt(r3)
                return r2
            L_0x0148:
                r7.enforceInterface(r0)
                int r1 = r7.readInt()
                if (r1 == 0) goto L_0x015b
                android.os.Parcelable$Creator<android.bluetooth.BluetoothDevice> r1 = android.bluetooth.BluetoothDevice.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r7)
                r3 = r1
                android.bluetooth.BluetoothDevice r3 = (android.bluetooth.BluetoothDevice) r3
                goto L_0x015c
            L_0x015b:
            L_0x015c:
                r1 = r3
                int r3 = r7.readInt()
                boolean r4 = r5.setPriority(r1, r3)
                r8.writeNoException()
                r8.writeInt(r4)
                return r2
            L_0x016c:
                r7.enforceInterface(r0)
                android.bluetooth.BluetoothDevice r3 = r5.getActiveDevice()
                r8.writeNoException()
                if (r3 == 0) goto L_0x017f
                r8.writeInt(r2)
                r3.writeToParcel(r8, r2)
                goto L_0x0182
            L_0x017f:
                r8.writeInt(r1)
            L_0x0182:
                return r2
            L_0x0183:
                r7.enforceInterface(r0)
                int r1 = r7.readInt()
                if (r1 == 0) goto L_0x0196
                android.os.Parcelable$Creator<android.bluetooth.BluetoothDevice> r1 = android.bluetooth.BluetoothDevice.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r7)
                r3 = r1
                android.bluetooth.BluetoothDevice r3 = (android.bluetooth.BluetoothDevice) r3
                goto L_0x0197
            L_0x0196:
            L_0x0197:
                r1 = r3
                boolean r3 = r5.setActiveDevice(r1)
                r8.writeNoException()
                r8.writeInt(r3)
                return r2
            L_0x01a3:
                r7.enforceInterface(r0)
                int r1 = r7.readInt()
                if (r1 == 0) goto L_0x01b6
                android.os.Parcelable$Creator<android.bluetooth.BluetoothDevice> r1 = android.bluetooth.BluetoothDevice.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r7)
                r3 = r1
                android.bluetooth.BluetoothDevice r3 = (android.bluetooth.BluetoothDevice) r3
                goto L_0x01b7
            L_0x01b6:
            L_0x01b7:
                r1 = r3
                int r3 = r5.getConnectionState(r1)
                r8.writeNoException()
                r8.writeInt(r3)
                return r2
            L_0x01c3:
                r7.enforceInterface(r0)
                int[] r1 = r7.createIntArray()
                java.util.List r3 = r5.getDevicesMatchingConnectionStates(r1)
                r8.writeNoException()
                r8.writeTypedList(r3)
                return r2
            L_0x01d5:
                r7.enforceInterface(r0)
                java.util.List r1 = r5.getConnectedDevices()
                r8.writeNoException()
                r8.writeTypedList(r1)
                return r2
            L_0x01e3:
                r7.enforceInterface(r0)
                int r1 = r7.readInt()
                if (r1 == 0) goto L_0x01f6
                android.os.Parcelable$Creator<android.bluetooth.BluetoothDevice> r1 = android.bluetooth.BluetoothDevice.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r7)
                r3 = r1
                android.bluetooth.BluetoothDevice r3 = (android.bluetooth.BluetoothDevice) r3
                goto L_0x01f7
            L_0x01f6:
            L_0x01f7:
                r1 = r3
                boolean r3 = r5.disconnect(r1)
                r8.writeNoException()
                r8.writeInt(r3)
                return r2
            L_0x0203:
                r7.enforceInterface(r0)
                int r1 = r7.readInt()
                if (r1 == 0) goto L_0x0216
                android.os.Parcelable$Creator<android.bluetooth.BluetoothDevice> r1 = android.bluetooth.BluetoothDevice.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r7)
                r3 = r1
                android.bluetooth.BluetoothDevice r3 = (android.bluetooth.BluetoothDevice) r3
                goto L_0x0217
            L_0x0216:
            L_0x0217:
                r1 = r3
                boolean r3 = r5.connect(r1)
                r8.writeNoException()
                r8.writeInt(r3)
                return r2
            L_0x0223:
                r8.writeString(r0)
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: android.bluetooth.IBluetoothA2dp.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IBluetoothA2dp {
            public static IBluetoothA2dp sDefaultImpl;
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
                    if (!this.mRemote.transact(1, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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
                    if (!this.mRemote.transact(2, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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
                    if (!this.mRemote.transact(3, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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
                    if (!this.mRemote.transact(4, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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
                    if (!this.mRemote.transact(5, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            public boolean setActiveDevice(BluetoothDevice device) throws RemoteException {
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
                        return Stub.getDefaultImpl().setActiveDevice(device);
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

            public BluetoothDevice getActiveDevice() throws RemoteException {
                BluetoothDevice _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(7, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getActiveDevice();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = BluetoothDevice.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    BluetoothDevice _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean setPriority(BluetoothDevice device, int priority) throws RemoteException {
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
                    _data.writeInt(priority);
                    if (!this.mRemote.transact(8, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setPriority(device, priority);
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
                    if (!this.mRemote.transact(9, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPriority(device);
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

            public boolean isAvrcpAbsoluteVolumeSupported() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(10, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isAvrcpAbsoluteVolumeSupported();
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

            public void setAvrcpAbsoluteVolume(int volume) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(volume);
                    if (this.mRemote.transact(11, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setAvrcpAbsoluteVolume(volume);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public boolean isA2dpPlaying(BluetoothDevice device) throws RemoteException {
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
                    if (!this.mRemote.transact(12, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isA2dpPlaying(device);
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
                    if (!this.mRemote.transact(13, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getCodecStatus(device);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = BluetoothCodecStatus.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    BluetoothCodecStatus _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

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
                    if (this.mRemote.transact(14, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setCodecConfigPreference(device, codecConfig);
                    }
                } finally {
                    _data.recycle();
                }
            }

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
                    if (this.mRemote.transact(15, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().enableOptionalCodecs(device);
                    }
                } finally {
                    _data.recycle();
                }
            }

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
                    if (this.mRemote.transact(16, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().disableOptionalCodecs(device);
                    }
                } finally {
                    _data.recycle();
                }
            }

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
                    if (!this.mRemote.transact(17, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().supportsOptionalCodecs(device);
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
                    if (!this.mRemote.transact(18, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getOptionalCodecsEnabled(device);
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
                    if (this.mRemote.transact(19, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setOptionalCodecsEnabled(device, value);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IBluetoothA2dp impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IBluetoothA2dp getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
