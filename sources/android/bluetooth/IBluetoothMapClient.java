package android.bluetooth;

import android.app.PendingIntent;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.List;

public interface IBluetoothMapClient extends IInterface {
    boolean connect(BluetoothDevice bluetoothDevice) throws RemoteException;

    boolean disconnect(BluetoothDevice bluetoothDevice) throws RemoteException;

    List<BluetoothDevice> getConnectedDevices() throws RemoteException;

    int getConnectionState(BluetoothDevice bluetoothDevice) throws RemoteException;

    List<BluetoothDevice> getDevicesMatchingConnectionStates(int[] iArr) throws RemoteException;

    int getPriority(BluetoothDevice bluetoothDevice) throws RemoteException;

    int getSupportedFeatures(BluetoothDevice bluetoothDevice) throws RemoteException;

    boolean getUnreadMessages(BluetoothDevice bluetoothDevice) throws RemoteException;

    boolean isConnected(BluetoothDevice bluetoothDevice) throws RemoteException;

    boolean sendMessage(BluetoothDevice bluetoothDevice, Uri[] uriArr, String str, PendingIntent pendingIntent, PendingIntent pendingIntent2) throws RemoteException;

    boolean setPriority(BluetoothDevice bluetoothDevice, int i) throws RemoteException;

    public static class Default implements IBluetoothMapClient {
        public boolean connect(BluetoothDevice device) throws RemoteException {
            return false;
        }

        public boolean disconnect(BluetoothDevice device) throws RemoteException {
            return false;
        }

        public boolean isConnected(BluetoothDevice device) throws RemoteException {
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

        public boolean setPriority(BluetoothDevice device, int priority) throws RemoteException {
            return false;
        }

        public int getPriority(BluetoothDevice device) throws RemoteException {
            return 0;
        }

        public boolean sendMessage(BluetoothDevice device, Uri[] contacts, String message, PendingIntent sentIntent, PendingIntent deliveryIntent) throws RemoteException {
            return false;
        }

        public boolean getUnreadMessages(BluetoothDevice device) throws RemoteException {
            return false;
        }

        public int getSupportedFeatures(BluetoothDevice device) throws RemoteException {
            return 0;
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IBluetoothMapClient {
        private static final String DESCRIPTOR = "android.bluetooth.IBluetoothMapClient";
        static final int TRANSACTION_connect = 1;
        static final int TRANSACTION_disconnect = 2;
        static final int TRANSACTION_getConnectedDevices = 4;
        static final int TRANSACTION_getConnectionState = 6;
        static final int TRANSACTION_getDevicesMatchingConnectionStates = 5;
        static final int TRANSACTION_getPriority = 8;
        static final int TRANSACTION_getSupportedFeatures = 11;
        static final int TRANSACTION_getUnreadMessages = 10;
        static final int TRANSACTION_isConnected = 3;
        static final int TRANSACTION_sendMessage = 9;
        static final int TRANSACTION_setPriority = 7;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IBluetoothMapClient asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IBluetoothMapClient)) {
                return new Proxy(obj);
            }
            return (IBluetoothMapClient) iin;
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
                    return "isConnected";
                case 4:
                    return "getConnectedDevices";
                case 5:
                    return "getDevicesMatchingConnectionStates";
                case 6:
                    return "getConnectionState";
                case 7:
                    return "setPriority";
                case 8:
                    return "getPriority";
                case 9:
                    return "sendMessage";
                case 10:
                    return "getUnreadMessages";
                case 11:
                    return "getSupportedFeatures";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v1, resolved type: android.bluetooth.BluetoothDevice} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v2, resolved type: android.bluetooth.BluetoothDevice} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v6, resolved type: android.bluetooth.BluetoothDevice} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v10, resolved type: android.bluetooth.BluetoothDevice} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v16, resolved type: android.bluetooth.BluetoothDevice} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v20, resolved type: android.bluetooth.BluetoothDevice} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v24, resolved type: android.bluetooth.BluetoothDevice} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v28, resolved type: android.bluetooth.BluetoothDevice} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v0, resolved type: android.app.PendingIntent} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v33, resolved type: android.bluetooth.BluetoothDevice} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v37, resolved type: android.bluetooth.BluetoothDevice} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v42, resolved type: android.bluetooth.BluetoothDevice} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v43, resolved type: android.bluetooth.BluetoothDevice} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v44, resolved type: android.bluetooth.BluetoothDevice} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v45, resolved type: android.bluetooth.BluetoothDevice} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v46, resolved type: android.bluetooth.BluetoothDevice} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v47, resolved type: android.bluetooth.BluetoothDevice} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v48, resolved type: android.bluetooth.BluetoothDevice} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v49, resolved type: android.bluetooth.BluetoothDevice} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v50, resolved type: android.bluetooth.BluetoothDevice} */
        /* JADX WARNING: type inference failed for: r1v32, types: [android.app.PendingIntent] */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r12, android.os.Parcel r13, android.os.Parcel r14, int r15) throws android.os.RemoteException {
            /*
                r11 = this;
                java.lang.String r0 = "android.bluetooth.IBluetoothMapClient"
                r1 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r2 = 1
                if (r12 == r1) goto L_0x0175
                r1 = 0
                switch(r12) {
                    case 1: goto L_0x0157;
                    case 2: goto L_0x0139;
                    case 3: goto L_0x011b;
                    case 4: goto L_0x010d;
                    case 5: goto L_0x00fb;
                    case 6: goto L_0x00dd;
                    case 7: goto L_0x00bb;
                    case 8: goto L_0x009d;
                    case 9: goto L_0x004d;
                    case 10: goto L_0x002f;
                    case 11: goto L_0x0011;
                    default: goto L_0x000c;
                }
            L_0x000c:
                boolean r1 = super.onTransact(r12, r13, r14, r15)
                return r1
            L_0x0011:
                r13.enforceInterface(r0)
                int r3 = r13.readInt()
                if (r3 == 0) goto L_0x0023
                android.os.Parcelable$Creator<android.bluetooth.BluetoothDevice> r1 = android.bluetooth.BluetoothDevice.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r13)
                android.bluetooth.BluetoothDevice r1 = (android.bluetooth.BluetoothDevice) r1
                goto L_0x0024
            L_0x0023:
            L_0x0024:
                int r3 = r11.getSupportedFeatures(r1)
                r14.writeNoException()
                r14.writeInt(r3)
                return r2
            L_0x002f:
                r13.enforceInterface(r0)
                int r3 = r13.readInt()
                if (r3 == 0) goto L_0x0041
                android.os.Parcelable$Creator<android.bluetooth.BluetoothDevice> r1 = android.bluetooth.BluetoothDevice.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r13)
                android.bluetooth.BluetoothDevice r1 = (android.bluetooth.BluetoothDevice) r1
                goto L_0x0042
            L_0x0041:
            L_0x0042:
                boolean r3 = r11.getUnreadMessages(r1)
                r14.writeNoException()
                r14.writeInt(r3)
                return r2
            L_0x004d:
                r13.enforceInterface(r0)
                int r3 = r13.readInt()
                if (r3 == 0) goto L_0x0060
                android.os.Parcelable$Creator<android.bluetooth.BluetoothDevice> r3 = android.bluetooth.BluetoothDevice.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r13)
                android.bluetooth.BluetoothDevice r3 = (android.bluetooth.BluetoothDevice) r3
                r5 = r3
                goto L_0x0061
            L_0x0060:
                r5 = r1
            L_0x0061:
                android.os.Parcelable$Creator<android.net.Uri> r3 = android.net.Uri.CREATOR
                java.lang.Object[] r3 = r13.createTypedArray(r3)
                android.net.Uri[] r3 = (android.net.Uri[]) r3
                java.lang.String r10 = r13.readString()
                int r4 = r13.readInt()
                if (r4 == 0) goto L_0x007d
                android.os.Parcelable$Creator<android.app.PendingIntent> r4 = android.app.PendingIntent.CREATOR
                java.lang.Object r4 = r4.createFromParcel(r13)
                android.app.PendingIntent r4 = (android.app.PendingIntent) r4
                r8 = r4
                goto L_0x007e
            L_0x007d:
                r8 = r1
            L_0x007e:
                int r4 = r13.readInt()
                if (r4 == 0) goto L_0x008e
                android.os.Parcelable$Creator<android.app.PendingIntent> r1 = android.app.PendingIntent.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r13)
                android.app.PendingIntent r1 = (android.app.PendingIntent) r1
            L_0x008c:
                r9 = r1
                goto L_0x008f
            L_0x008e:
                goto L_0x008c
            L_0x008f:
                r4 = r11
                r6 = r3
                r7 = r10
                boolean r1 = r4.sendMessage(r5, r6, r7, r8, r9)
                r14.writeNoException()
                r14.writeInt(r1)
                return r2
            L_0x009d:
                r13.enforceInterface(r0)
                int r3 = r13.readInt()
                if (r3 == 0) goto L_0x00af
                android.os.Parcelable$Creator<android.bluetooth.BluetoothDevice> r1 = android.bluetooth.BluetoothDevice.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r13)
                android.bluetooth.BluetoothDevice r1 = (android.bluetooth.BluetoothDevice) r1
                goto L_0x00b0
            L_0x00af:
            L_0x00b0:
                int r3 = r11.getPriority(r1)
                r14.writeNoException()
                r14.writeInt(r3)
                return r2
            L_0x00bb:
                r13.enforceInterface(r0)
                int r3 = r13.readInt()
                if (r3 == 0) goto L_0x00cd
                android.os.Parcelable$Creator<android.bluetooth.BluetoothDevice> r1 = android.bluetooth.BluetoothDevice.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r13)
                android.bluetooth.BluetoothDevice r1 = (android.bluetooth.BluetoothDevice) r1
                goto L_0x00ce
            L_0x00cd:
            L_0x00ce:
                int r3 = r13.readInt()
                boolean r4 = r11.setPriority(r1, r3)
                r14.writeNoException()
                r14.writeInt(r4)
                return r2
            L_0x00dd:
                r13.enforceInterface(r0)
                int r3 = r13.readInt()
                if (r3 == 0) goto L_0x00ef
                android.os.Parcelable$Creator<android.bluetooth.BluetoothDevice> r1 = android.bluetooth.BluetoothDevice.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r13)
                android.bluetooth.BluetoothDevice r1 = (android.bluetooth.BluetoothDevice) r1
                goto L_0x00f0
            L_0x00ef:
            L_0x00f0:
                int r3 = r11.getConnectionState(r1)
                r14.writeNoException()
                r14.writeInt(r3)
                return r2
            L_0x00fb:
                r13.enforceInterface(r0)
                int[] r1 = r13.createIntArray()
                java.util.List r3 = r11.getDevicesMatchingConnectionStates(r1)
                r14.writeNoException()
                r14.writeTypedList(r3)
                return r2
            L_0x010d:
                r13.enforceInterface(r0)
                java.util.List r1 = r11.getConnectedDevices()
                r14.writeNoException()
                r14.writeTypedList(r1)
                return r2
            L_0x011b:
                r13.enforceInterface(r0)
                int r3 = r13.readInt()
                if (r3 == 0) goto L_0x012d
                android.os.Parcelable$Creator<android.bluetooth.BluetoothDevice> r1 = android.bluetooth.BluetoothDevice.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r13)
                android.bluetooth.BluetoothDevice r1 = (android.bluetooth.BluetoothDevice) r1
                goto L_0x012e
            L_0x012d:
            L_0x012e:
                boolean r3 = r11.isConnected(r1)
                r14.writeNoException()
                r14.writeInt(r3)
                return r2
            L_0x0139:
                r13.enforceInterface(r0)
                int r3 = r13.readInt()
                if (r3 == 0) goto L_0x014b
                android.os.Parcelable$Creator<android.bluetooth.BluetoothDevice> r1 = android.bluetooth.BluetoothDevice.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r13)
                android.bluetooth.BluetoothDevice r1 = (android.bluetooth.BluetoothDevice) r1
                goto L_0x014c
            L_0x014b:
            L_0x014c:
                boolean r3 = r11.disconnect(r1)
                r14.writeNoException()
                r14.writeInt(r3)
                return r2
            L_0x0157:
                r13.enforceInterface(r0)
                int r3 = r13.readInt()
                if (r3 == 0) goto L_0x0169
                android.os.Parcelable$Creator<android.bluetooth.BluetoothDevice> r1 = android.bluetooth.BluetoothDevice.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r13)
                android.bluetooth.BluetoothDevice r1 = (android.bluetooth.BluetoothDevice) r1
                goto L_0x016a
            L_0x0169:
            L_0x016a:
                boolean r3 = r11.connect(r1)
                r14.writeNoException()
                r14.writeInt(r3)
                return r2
            L_0x0175:
                r14.writeString(r0)
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: android.bluetooth.IBluetoothMapClient.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IBluetoothMapClient {
            public static IBluetoothMapClient sDefaultImpl;
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

            public boolean isConnected(BluetoothDevice device) throws RemoteException {
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
                    if (!this.mRemote.transact(3, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isConnected(device);
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
                    if (!this.mRemote.transact(4, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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
                    if (!this.mRemote.transact(5, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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
                    if (!this.mRemote.transact(6, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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
                    if (!this.mRemote.transact(7, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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
                    if (!this.mRemote.transact(8, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            public boolean sendMessage(BluetoothDevice device, Uri[] contacts, String message, PendingIntent sentIntent, PendingIntent deliveryIntent) throws RemoteException {
                BluetoothDevice bluetoothDevice = device;
                PendingIntent pendingIntent = sentIntent;
                PendingIntent pendingIntent2 = deliveryIntent;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (bluetoothDevice != null) {
                        _data.writeInt(1);
                        bluetoothDevice.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    try {
                        _data.writeTypedArray(contacts, 0);
                    } catch (Throwable th) {
                        th = th;
                        String str = message;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeString(message);
                        if (pendingIntent != null) {
                            _data.writeInt(1);
                            pendingIntent.writeToParcel(_data, 0);
                        } else {
                            _data.writeInt(0);
                        }
                        if (pendingIntent2 != null) {
                            _data.writeInt(1);
                            pendingIntent2.writeToParcel(_data, 0);
                        } else {
                            _data.writeInt(0);
                        }
                        try {
                            if (this.mRemote.transact(9, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                _reply.readException();
                                if (_reply.readInt() == 0) {
                                    _result = false;
                                }
                                _reply.recycle();
                                _data.recycle();
                                return _result;
                            }
                            boolean sendMessage = Stub.getDefaultImpl().sendMessage(device, contacts, message, sentIntent, deliveryIntent);
                            _reply.recycle();
                            _data.recycle();
                            return sendMessage;
                        } catch (Throwable th2) {
                            th = th2;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th4) {
                    th = th4;
                    Uri[] uriArr = contacts;
                    String str2 = message;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public boolean getUnreadMessages(BluetoothDevice device) throws RemoteException {
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
                    if (!this.mRemote.transact(10, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getUnreadMessages(device);
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

            public int getSupportedFeatures(BluetoothDevice device) throws RemoteException {
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
                        return Stub.getDefaultImpl().getSupportedFeatures(device);
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
        }

        public static boolean setDefaultImpl(IBluetoothMapClient impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IBluetoothMapClient getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
