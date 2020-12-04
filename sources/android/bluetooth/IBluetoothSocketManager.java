package android.bluetooth;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.ParcelUuid;
import android.os.RemoteException;

public interface IBluetoothSocketManager extends IInterface {
    ParcelFileDescriptor connectSocket(BluetoothDevice bluetoothDevice, int i, ParcelUuid parcelUuid, int i2, int i3) throws RemoteException;

    ParcelFileDescriptor createSocketChannel(int i, String str, ParcelUuid parcelUuid, int i2, int i3) throws RemoteException;

    void requestMaximumTxDataLength(BluetoothDevice bluetoothDevice) throws RemoteException;

    public static class Default implements IBluetoothSocketManager {
        public ParcelFileDescriptor connectSocket(BluetoothDevice device, int type, ParcelUuid uuid, int port, int flag) throws RemoteException {
            return null;
        }

        public ParcelFileDescriptor createSocketChannel(int type, String serviceName, ParcelUuid uuid, int port, int flag) throws RemoteException {
            return null;
        }

        public void requestMaximumTxDataLength(BluetoothDevice device) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IBluetoothSocketManager {
        private static final String DESCRIPTOR = "android.bluetooth.IBluetoothSocketManager";
        static final int TRANSACTION_connectSocket = 1;
        static final int TRANSACTION_createSocketChannel = 2;
        static final int TRANSACTION_requestMaximumTxDataLength = 3;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IBluetoothSocketManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IBluetoothSocketManager)) {
                return new Proxy(obj);
            }
            return (IBluetoothSocketManager) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "connectSocket";
                case 2:
                    return "createSocketChannel";
                case 3:
                    return "requestMaximumTxDataLength";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v0, resolved type: android.os.ParcelUuid} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v1, resolved type: android.os.ParcelUuid} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v6, resolved type: android.os.ParcelUuid} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v12, resolved type: android.os.ParcelUuid} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v3, resolved type: android.bluetooth.BluetoothDevice} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v15, resolved type: android.os.ParcelUuid} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v16, resolved type: android.os.ParcelUuid} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v17, resolved type: android.os.ParcelUuid} */
        /* JADX WARNING: type inference failed for: r6v14, types: [android.bluetooth.BluetoothDevice] */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r18, android.os.Parcel r19, android.os.Parcel r20, int r21) throws android.os.RemoteException {
            /*
                r17 = this;
                r0 = r18
                r1 = r19
                r2 = r20
                java.lang.String r3 = "android.bluetooth.IBluetoothSocketManager"
                r4 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r5 = 1
                if (r0 == r4) goto L_0x00c3
                r4 = 0
                r6 = 0
                switch(r0) {
                    case 1: goto L_0x0077;
                    case 2: goto L_0x0036;
                    case 3: goto L_0x0018;
                    default: goto L_0x0013;
                }
            L_0x0013:
                boolean r4 = super.onTransact(r18, r19, r20, r21)
                return r4
            L_0x0018:
                r1.enforceInterface(r3)
                int r4 = r19.readInt()
                if (r4 == 0) goto L_0x002b
                android.os.Parcelable$Creator<android.bluetooth.BluetoothDevice> r4 = android.bluetooth.BluetoothDevice.CREATOR
                java.lang.Object r4 = r4.createFromParcel(r1)
                r6 = r4
                android.bluetooth.BluetoothDevice r6 = (android.bluetooth.BluetoothDevice) r6
                goto L_0x002c
            L_0x002b:
            L_0x002c:
                r4 = r6
                r13 = r17
                r13.requestMaximumTxDataLength(r4)
                r20.writeNoException()
                return r5
            L_0x0036:
                r13 = r17
                r1.enforceInterface(r3)
                int r12 = r19.readInt()
                java.lang.String r14 = r19.readString()
                int r7 = r19.readInt()
                if (r7 == 0) goto L_0x0053
                android.os.Parcelable$Creator<android.os.ParcelUuid> r6 = android.os.ParcelUuid.CREATOR
                java.lang.Object r6 = r6.createFromParcel(r1)
                android.os.ParcelUuid r6 = (android.os.ParcelUuid) r6
            L_0x0051:
                r9 = r6
                goto L_0x0054
            L_0x0053:
                goto L_0x0051
            L_0x0054:
                int r15 = r19.readInt()
                int r16 = r19.readInt()
                r6 = r17
                r7 = r12
                r8 = r14
                r10 = r15
                r11 = r16
                android.os.ParcelFileDescriptor r6 = r6.createSocketChannel(r7, r8, r9, r10, r11)
                r20.writeNoException()
                if (r6 == 0) goto L_0x0073
                r2.writeInt(r5)
                r6.writeToParcel(r2, r5)
                goto L_0x0076
            L_0x0073:
                r2.writeInt(r4)
            L_0x0076:
                return r5
            L_0x0077:
                r13 = r17
                r1.enforceInterface(r3)
                int r7 = r19.readInt()
                if (r7 == 0) goto L_0x008c
                android.os.Parcelable$Creator<android.bluetooth.BluetoothDevice> r7 = android.bluetooth.BluetoothDevice.CREATOR
                java.lang.Object r7 = r7.createFromParcel(r1)
                android.bluetooth.BluetoothDevice r7 = (android.bluetooth.BluetoothDevice) r7
                r8 = r7
                goto L_0x008d
            L_0x008c:
                r8 = r6
            L_0x008d:
                int r14 = r19.readInt()
                int r7 = r19.readInt()
                if (r7 == 0) goto L_0x00a1
                android.os.Parcelable$Creator<android.os.ParcelUuid> r6 = android.os.ParcelUuid.CREATOR
                java.lang.Object r6 = r6.createFromParcel(r1)
                android.os.ParcelUuid r6 = (android.os.ParcelUuid) r6
            L_0x009f:
                r10 = r6
                goto L_0x00a2
            L_0x00a1:
                goto L_0x009f
            L_0x00a2:
                int r6 = r19.readInt()
                int r15 = r19.readInt()
                r7 = r17
                r9 = r14
                r11 = r6
                r12 = r15
                android.os.ParcelFileDescriptor r7 = r7.connectSocket(r8, r9, r10, r11, r12)
                r20.writeNoException()
                if (r7 == 0) goto L_0x00bf
                r2.writeInt(r5)
                r7.writeToParcel(r2, r5)
                goto L_0x00c2
            L_0x00bf:
                r2.writeInt(r4)
            L_0x00c2:
                return r5
            L_0x00c3:
                r13 = r17
                r2.writeString(r3)
                return r5
            */
            throw new UnsupportedOperationException("Method not decompiled: android.bluetooth.IBluetoothSocketManager.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IBluetoothSocketManager {
            public static IBluetoothSocketManager sDefaultImpl;
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

            public ParcelFileDescriptor connectSocket(BluetoothDevice device, int type, ParcelUuid uuid, int port, int flag) throws RemoteException {
                ParcelFileDescriptor _result;
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
                    _data.writeInt(type);
                    if (uuid != null) {
                        _data.writeInt(1);
                        uuid.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(port);
                    _data.writeInt(flag);
                    if (!this.mRemote.transact(1, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().connectSocket(device, type, uuid, port, flag);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = ParcelFileDescriptor.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    ParcelFileDescriptor _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public ParcelFileDescriptor createSocketChannel(int type, String serviceName, ParcelUuid uuid, int port, int flag) throws RemoteException {
                ParcelFileDescriptor _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeString(serviceName);
                    if (uuid != null) {
                        _data.writeInt(1);
                        uuid.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(port);
                    _data.writeInt(flag);
                    if (!this.mRemote.transact(2, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().createSocketChannel(type, serviceName, uuid, port, flag);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = ParcelFileDescriptor.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    ParcelFileDescriptor _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void requestMaximumTxDataLength(BluetoothDevice device) throws RemoteException {
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
                    if (this.mRemote.transact(3, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().requestMaximumTxDataLength(device);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IBluetoothSocketManager impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IBluetoothSocketManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
