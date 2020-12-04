package android.media.midi;

import android.bluetooth.BluetoothDevice;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IMidiManager extends IInterface {
    void closeDevice(IBinder iBinder, IBinder iBinder2) throws RemoteException;

    MidiDeviceStatus getDeviceStatus(MidiDeviceInfo midiDeviceInfo) throws RemoteException;

    MidiDeviceInfo[] getDevices() throws RemoteException;

    MidiDeviceInfo getServiceDeviceInfo(String str, String str2) throws RemoteException;

    void openBluetoothDevice(IBinder iBinder, BluetoothDevice bluetoothDevice, IMidiDeviceOpenCallback iMidiDeviceOpenCallback) throws RemoteException;

    void openDevice(IBinder iBinder, MidiDeviceInfo midiDeviceInfo, IMidiDeviceOpenCallback iMidiDeviceOpenCallback) throws RemoteException;

    MidiDeviceInfo registerDeviceServer(IMidiDeviceServer iMidiDeviceServer, int i, int i2, String[] strArr, String[] strArr2, Bundle bundle, int i3) throws RemoteException;

    void registerListener(IBinder iBinder, IMidiDeviceListener iMidiDeviceListener) throws RemoteException;

    void setDeviceStatus(IMidiDeviceServer iMidiDeviceServer, MidiDeviceStatus midiDeviceStatus) throws RemoteException;

    void unregisterDeviceServer(IMidiDeviceServer iMidiDeviceServer) throws RemoteException;

    void unregisterListener(IBinder iBinder, IMidiDeviceListener iMidiDeviceListener) throws RemoteException;

    public static class Default implements IMidiManager {
        public MidiDeviceInfo[] getDevices() throws RemoteException {
            return null;
        }

        public void registerListener(IBinder clientToken, IMidiDeviceListener listener) throws RemoteException {
        }

        public void unregisterListener(IBinder clientToken, IMidiDeviceListener listener) throws RemoteException {
        }

        public void openDevice(IBinder clientToken, MidiDeviceInfo device, IMidiDeviceOpenCallback callback) throws RemoteException {
        }

        public void openBluetoothDevice(IBinder clientToken, BluetoothDevice bluetoothDevice, IMidiDeviceOpenCallback callback) throws RemoteException {
        }

        public void closeDevice(IBinder clientToken, IBinder deviceToken) throws RemoteException {
        }

        public MidiDeviceInfo registerDeviceServer(IMidiDeviceServer server, int numInputPorts, int numOutputPorts, String[] inputPortNames, String[] outputPortNames, Bundle properties, int type) throws RemoteException {
            return null;
        }

        public void unregisterDeviceServer(IMidiDeviceServer server) throws RemoteException {
        }

        public MidiDeviceInfo getServiceDeviceInfo(String packageName, String className) throws RemoteException {
            return null;
        }

        public MidiDeviceStatus getDeviceStatus(MidiDeviceInfo deviceInfo) throws RemoteException {
            return null;
        }

        public void setDeviceStatus(IMidiDeviceServer server, MidiDeviceStatus status) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IMidiManager {
        private static final String DESCRIPTOR = "android.media.midi.IMidiManager";
        static final int TRANSACTION_closeDevice = 6;
        static final int TRANSACTION_getDeviceStatus = 10;
        static final int TRANSACTION_getDevices = 1;
        static final int TRANSACTION_getServiceDeviceInfo = 9;
        static final int TRANSACTION_openBluetoothDevice = 5;
        static final int TRANSACTION_openDevice = 4;
        static final int TRANSACTION_registerDeviceServer = 7;
        static final int TRANSACTION_registerListener = 2;
        static final int TRANSACTION_setDeviceStatus = 11;
        static final int TRANSACTION_unregisterDeviceServer = 8;
        static final int TRANSACTION_unregisterListener = 3;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IMidiManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IMidiManager)) {
                return new Proxy(obj);
            }
            return (IMidiManager) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "getDevices";
                case 2:
                    return "registerListener";
                case 3:
                    return "unregisterListener";
                case 4:
                    return "openDevice";
                case 5:
                    return "openBluetoothDevice";
                case 6:
                    return "closeDevice";
                case 7:
                    return "registerDeviceServer";
                case 8:
                    return "unregisterDeviceServer";
                case 9:
                    return "getServiceDeviceInfo";
                case 10:
                    return "getDeviceStatus";
                case 11:
                    return "setDeviceStatus";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v5, resolved type: android.media.midi.MidiDeviceInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v9, resolved type: android.bluetooth.BluetoothDevice} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v23, resolved type: android.media.midi.MidiDeviceInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v27, resolved type: android.media.midi.MidiDeviceStatus} */
        /* JADX WARNING: type inference failed for: r0v1 */
        /* JADX WARNING: type inference failed for: r0v14 */
        /* JADX WARNING: type inference failed for: r0v32 */
        /* JADX WARNING: type inference failed for: r0v33 */
        /* JADX WARNING: type inference failed for: r0v34 */
        /* JADX WARNING: type inference failed for: r0v35 */
        /* JADX WARNING: type inference failed for: r0v36 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r22, android.os.Parcel r23, android.os.Parcel r24, int r25) throws android.os.RemoteException {
            /*
                r21 = this;
                r8 = r21
                r9 = r22
                r10 = r23
                r11 = r24
                java.lang.String r12 = "android.media.midi.IMidiManager"
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r13 = 1
                if (r9 == r0) goto L_0x017d
                r14 = 0
                r0 = 0
                switch(r9) {
                    case 1: goto L_0x016f;
                    case 2: goto L_0x0159;
                    case 3: goto L_0x0143;
                    case 4: goto L_0x011d;
                    case 5: goto L_0x00f7;
                    case 6: goto L_0x00e5;
                    case 7: goto L_0x0094;
                    case 8: goto L_0x0082;
                    case 9: goto L_0x0063;
                    case 10: goto L_0x003c;
                    case 11: goto L_0x001a;
                    default: goto L_0x0015;
                }
            L_0x0015:
                boolean r0 = super.onTransact(r22, r23, r24, r25)
                return r0
            L_0x001a:
                r10.enforceInterface(r12)
                android.os.IBinder r1 = r23.readStrongBinder()
                android.media.midi.IMidiDeviceServer r1 = android.media.midi.IMidiDeviceServer.Stub.asInterface(r1)
                int r2 = r23.readInt()
                if (r2 == 0) goto L_0x0034
                android.os.Parcelable$Creator<android.media.midi.MidiDeviceStatus> r0 = android.media.midi.MidiDeviceStatus.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.media.midi.MidiDeviceStatus r0 = (android.media.midi.MidiDeviceStatus) r0
                goto L_0x0035
            L_0x0034:
            L_0x0035:
                r8.setDeviceStatus(r1, r0)
                r24.writeNoException()
                return r13
            L_0x003c:
                r10.enforceInterface(r12)
                int r1 = r23.readInt()
                if (r1 == 0) goto L_0x004e
                android.os.Parcelable$Creator<android.media.midi.MidiDeviceInfo> r0 = android.media.midi.MidiDeviceInfo.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.media.midi.MidiDeviceInfo r0 = (android.media.midi.MidiDeviceInfo) r0
                goto L_0x004f
            L_0x004e:
            L_0x004f:
                android.media.midi.MidiDeviceStatus r1 = r8.getDeviceStatus(r0)
                r24.writeNoException()
                if (r1 == 0) goto L_0x005f
                r11.writeInt(r13)
                r1.writeToParcel(r11, r13)
                goto L_0x0062
            L_0x005f:
                r11.writeInt(r14)
            L_0x0062:
                return r13
            L_0x0063:
                r10.enforceInterface(r12)
                java.lang.String r0 = r23.readString()
                java.lang.String r1 = r23.readString()
                android.media.midi.MidiDeviceInfo r2 = r8.getServiceDeviceInfo(r0, r1)
                r24.writeNoException()
                if (r2 == 0) goto L_0x007e
                r11.writeInt(r13)
                r2.writeToParcel(r11, r13)
                goto L_0x0081
            L_0x007e:
                r11.writeInt(r14)
            L_0x0081:
                return r13
            L_0x0082:
                r10.enforceInterface(r12)
                android.os.IBinder r0 = r23.readStrongBinder()
                android.media.midi.IMidiDeviceServer r0 = android.media.midi.IMidiDeviceServer.Stub.asInterface(r0)
                r8.unregisterDeviceServer(r0)
                r24.writeNoException()
                return r13
            L_0x0094:
                r10.enforceInterface(r12)
                android.os.IBinder r1 = r23.readStrongBinder()
                android.media.midi.IMidiDeviceServer r15 = android.media.midi.IMidiDeviceServer.Stub.asInterface(r1)
                int r16 = r23.readInt()
                int r17 = r23.readInt()
                java.lang.String[] r18 = r23.createStringArray()
                java.lang.String[] r19 = r23.createStringArray()
                int r1 = r23.readInt()
                if (r1 == 0) goto L_0x00bf
                android.os.Parcelable$Creator<android.os.Bundle> r0 = android.os.Bundle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.os.Bundle r0 = (android.os.Bundle) r0
            L_0x00bd:
                r6 = r0
                goto L_0x00c0
            L_0x00bf:
                goto L_0x00bd
            L_0x00c0:
                int r20 = r23.readInt()
                r0 = r21
                r1 = r15
                r2 = r16
                r3 = r17
                r4 = r18
                r5 = r19
                r7 = r20
                android.media.midi.MidiDeviceInfo r0 = r0.registerDeviceServer(r1, r2, r3, r4, r5, r6, r7)
                r24.writeNoException()
                if (r0 == 0) goto L_0x00e1
                r11.writeInt(r13)
                r0.writeToParcel(r11, r13)
                goto L_0x00e4
            L_0x00e1:
                r11.writeInt(r14)
            L_0x00e4:
                return r13
            L_0x00e5:
                r10.enforceInterface(r12)
                android.os.IBinder r0 = r23.readStrongBinder()
                android.os.IBinder r1 = r23.readStrongBinder()
                r8.closeDevice(r0, r1)
                r24.writeNoException()
                return r13
            L_0x00f7:
                r10.enforceInterface(r12)
                android.os.IBinder r1 = r23.readStrongBinder()
                int r2 = r23.readInt()
                if (r2 == 0) goto L_0x010d
                android.os.Parcelable$Creator<android.bluetooth.BluetoothDevice> r0 = android.bluetooth.BluetoothDevice.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.bluetooth.BluetoothDevice r0 = (android.bluetooth.BluetoothDevice) r0
                goto L_0x010e
            L_0x010d:
            L_0x010e:
                android.os.IBinder r2 = r23.readStrongBinder()
                android.media.midi.IMidiDeviceOpenCallback r2 = android.media.midi.IMidiDeviceOpenCallback.Stub.asInterface(r2)
                r8.openBluetoothDevice(r1, r0, r2)
                r24.writeNoException()
                return r13
            L_0x011d:
                r10.enforceInterface(r12)
                android.os.IBinder r1 = r23.readStrongBinder()
                int r2 = r23.readInt()
                if (r2 == 0) goto L_0x0133
                android.os.Parcelable$Creator<android.media.midi.MidiDeviceInfo> r0 = android.media.midi.MidiDeviceInfo.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.media.midi.MidiDeviceInfo r0 = (android.media.midi.MidiDeviceInfo) r0
                goto L_0x0134
            L_0x0133:
            L_0x0134:
                android.os.IBinder r2 = r23.readStrongBinder()
                android.media.midi.IMidiDeviceOpenCallback r2 = android.media.midi.IMidiDeviceOpenCallback.Stub.asInterface(r2)
                r8.openDevice(r1, r0, r2)
                r24.writeNoException()
                return r13
            L_0x0143:
                r10.enforceInterface(r12)
                android.os.IBinder r0 = r23.readStrongBinder()
                android.os.IBinder r1 = r23.readStrongBinder()
                android.media.midi.IMidiDeviceListener r1 = android.media.midi.IMidiDeviceListener.Stub.asInterface(r1)
                r8.unregisterListener(r0, r1)
                r24.writeNoException()
                return r13
            L_0x0159:
                r10.enforceInterface(r12)
                android.os.IBinder r0 = r23.readStrongBinder()
                android.os.IBinder r1 = r23.readStrongBinder()
                android.media.midi.IMidiDeviceListener r1 = android.media.midi.IMidiDeviceListener.Stub.asInterface(r1)
                r8.registerListener(r0, r1)
                r24.writeNoException()
                return r13
            L_0x016f:
                r10.enforceInterface(r12)
                android.media.midi.MidiDeviceInfo[] r0 = r21.getDevices()
                r24.writeNoException()
                r11.writeTypedArray(r0, r13)
                return r13
            L_0x017d:
                r11.writeString(r12)
                return r13
            */
            throw new UnsupportedOperationException("Method not decompiled: android.media.midi.IMidiManager.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IMidiManager {
            public static IMidiManager sDefaultImpl;
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

            public MidiDeviceInfo[] getDevices() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getDevices();
                    }
                    _reply.readException();
                    MidiDeviceInfo[] _result = (MidiDeviceInfo[]) _reply.createTypedArray(MidiDeviceInfo.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void registerListener(IBinder clientToken, IMidiDeviceListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(clientToken);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    if (this.mRemote.transact(2, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().registerListener(clientToken, listener);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void unregisterListener(IBinder clientToken, IMidiDeviceListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(clientToken);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    if (this.mRemote.transact(3, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().unregisterListener(clientToken, listener);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void openDevice(IBinder clientToken, MidiDeviceInfo device, IMidiDeviceOpenCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(clientToken);
                    if (device != null) {
                        _data.writeInt(1);
                        device.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(4, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().openDevice(clientToken, device, callback);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void openBluetoothDevice(IBinder clientToken, BluetoothDevice bluetoothDevice, IMidiDeviceOpenCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(clientToken);
                    if (bluetoothDevice != null) {
                        _data.writeInt(1);
                        bluetoothDevice.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(5, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().openBluetoothDevice(clientToken, bluetoothDevice, callback);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void closeDevice(IBinder clientToken, IBinder deviceToken) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(clientToken);
                    _data.writeStrongBinder(deviceToken);
                    if (this.mRemote.transact(6, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().closeDevice(clientToken, deviceToken);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public MidiDeviceInfo registerDeviceServer(IMidiDeviceServer server, int numInputPorts, int numOutputPorts, String[] inputPortNames, String[] outputPortNames, Bundle properties, int type) throws RemoteException {
                Bundle bundle = properties;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    MidiDeviceInfo _result = null;
                    _data.writeStrongBinder(server != null ? server.asBinder() : null);
                    try {
                        _data.writeInt(numInputPorts);
                    } catch (Throwable th) {
                        th = th;
                        int i = numOutputPorts;
                        String[] strArr = inputPortNames;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(numOutputPorts);
                    } catch (Throwable th2) {
                        th = th2;
                        String[] strArr2 = inputPortNames;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeStringArray(inputPortNames);
                        _data.writeStringArray(outputPortNames);
                        if (bundle != null) {
                            _data.writeInt(1);
                            bundle.writeToParcel(_data, 0);
                        } else {
                            _data.writeInt(0);
                        }
                        _data.writeInt(type);
                        if (this.mRemote.transact(7, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                            _reply.readException();
                            if (_reply.readInt() != 0) {
                                _result = MidiDeviceInfo.CREATOR.createFromParcel(_reply);
                            }
                            _reply.recycle();
                            _data.recycle();
                            return _result;
                        }
                        MidiDeviceInfo registerDeviceServer = Stub.getDefaultImpl().registerDeviceServer(server, numInputPorts, numOutputPorts, inputPortNames, outputPortNames, properties, type);
                        _reply.recycle();
                        _data.recycle();
                        return registerDeviceServer;
                    } catch (Throwable th3) {
                        th = th3;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th4) {
                    th = th4;
                    int i2 = numInputPorts;
                    int i3 = numOutputPorts;
                    String[] strArr22 = inputPortNames;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public void unregisterDeviceServer(IMidiDeviceServer server) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(server != null ? server.asBinder() : null);
                    if (this.mRemote.transact(8, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().unregisterDeviceServer(server);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public MidiDeviceInfo getServiceDeviceInfo(String packageName, String className) throws RemoteException {
                MidiDeviceInfo _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeString(className);
                    if (!this.mRemote.transact(9, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getServiceDeviceInfo(packageName, className);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = MidiDeviceInfo.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    MidiDeviceInfo _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public MidiDeviceStatus getDeviceStatus(MidiDeviceInfo deviceInfo) throws RemoteException {
                MidiDeviceStatus _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (deviceInfo != null) {
                        _data.writeInt(1);
                        deviceInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(10, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getDeviceStatus(deviceInfo);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = MidiDeviceStatus.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    MidiDeviceStatus _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setDeviceStatus(IMidiDeviceServer server, MidiDeviceStatus status) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(server != null ? server.asBinder() : null);
                    if (status != null) {
                        _data.writeInt(1);
                        status.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(11, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setDeviceStatus(server, status);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IMidiManager impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IMidiManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
