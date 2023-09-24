package android.bluetooth;

import android.p007os.Binder;
import android.p007os.Bundle;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.RemoteException;
import java.util.List;

/* loaded from: classes.dex */
public interface IBluetoothHeadsetClient extends IInterface {
    boolean acceptCall(BluetoothDevice bluetoothDevice, int i) throws RemoteException;

    boolean connect(BluetoothDevice bluetoothDevice) throws RemoteException;

    boolean connectAudio(BluetoothDevice bluetoothDevice) throws RemoteException;

    BluetoothHeadsetClientCall dial(BluetoothDevice bluetoothDevice, String str) throws RemoteException;

    boolean disconnect(BluetoothDevice bluetoothDevice) throws RemoteException;

    boolean disconnectAudio(BluetoothDevice bluetoothDevice) throws RemoteException;

    boolean enterPrivateMode(BluetoothDevice bluetoothDevice, int i) throws RemoteException;

    boolean explicitCallTransfer(BluetoothDevice bluetoothDevice) throws RemoteException;

    boolean getAudioRouteAllowed(BluetoothDevice bluetoothDevice) throws RemoteException;

    int getAudioState(BluetoothDevice bluetoothDevice) throws RemoteException;

    List<BluetoothDevice> getConnectedDevices() throws RemoteException;

    int getConnectionState(BluetoothDevice bluetoothDevice) throws RemoteException;

    Bundle getCurrentAgEvents(BluetoothDevice bluetoothDevice) throws RemoteException;

    Bundle getCurrentAgFeatures(BluetoothDevice bluetoothDevice) throws RemoteException;

    List<BluetoothHeadsetClientCall> getCurrentCalls(BluetoothDevice bluetoothDevice) throws RemoteException;

    List<BluetoothDevice> getDevicesMatchingConnectionStates(int[] iArr) throws RemoteException;

    boolean getLastVoiceTagNumber(BluetoothDevice bluetoothDevice) throws RemoteException;

    int getPriority(BluetoothDevice bluetoothDevice) throws RemoteException;

    boolean holdCall(BluetoothDevice bluetoothDevice) throws RemoteException;

    boolean rejectCall(BluetoothDevice bluetoothDevice) throws RemoteException;

    boolean sendDTMF(BluetoothDevice bluetoothDevice, byte b) throws RemoteException;

    void setAudioRouteAllowed(BluetoothDevice bluetoothDevice, boolean z) throws RemoteException;

    boolean setPriority(BluetoothDevice bluetoothDevice, int i) throws RemoteException;

    boolean startVoiceRecognition(BluetoothDevice bluetoothDevice) throws RemoteException;

    boolean stopVoiceRecognition(BluetoothDevice bluetoothDevice) throws RemoteException;

    boolean terminateCall(BluetoothDevice bluetoothDevice, BluetoothHeadsetClientCall bluetoothHeadsetClientCall) throws RemoteException;

    /* loaded from: classes.dex */
    public static class Default implements IBluetoothHeadsetClient {
        @Override // android.bluetooth.IBluetoothHeadsetClient
        public boolean connect(BluetoothDevice device) throws RemoteException {
            return false;
        }

        @Override // android.bluetooth.IBluetoothHeadsetClient
        public boolean disconnect(BluetoothDevice device) throws RemoteException {
            return false;
        }

        @Override // android.bluetooth.IBluetoothHeadsetClient
        public List<BluetoothDevice> getConnectedDevices() throws RemoteException {
            return null;
        }

        @Override // android.bluetooth.IBluetoothHeadsetClient
        public List<BluetoothDevice> getDevicesMatchingConnectionStates(int[] states) throws RemoteException {
            return null;
        }

        @Override // android.bluetooth.IBluetoothHeadsetClient
        public int getConnectionState(BluetoothDevice device) throws RemoteException {
            return 0;
        }

        @Override // android.bluetooth.IBluetoothHeadsetClient
        public boolean setPriority(BluetoothDevice device, int priority) throws RemoteException {
            return false;
        }

        @Override // android.bluetooth.IBluetoothHeadsetClient
        public int getPriority(BluetoothDevice device) throws RemoteException {
            return 0;
        }

        @Override // android.bluetooth.IBluetoothHeadsetClient
        public boolean startVoiceRecognition(BluetoothDevice device) throws RemoteException {
            return false;
        }

        @Override // android.bluetooth.IBluetoothHeadsetClient
        public boolean stopVoiceRecognition(BluetoothDevice device) throws RemoteException {
            return false;
        }

        @Override // android.bluetooth.IBluetoothHeadsetClient
        public List<BluetoothHeadsetClientCall> getCurrentCalls(BluetoothDevice device) throws RemoteException {
            return null;
        }

        @Override // android.bluetooth.IBluetoothHeadsetClient
        public Bundle getCurrentAgEvents(BluetoothDevice device) throws RemoteException {
            return null;
        }

        @Override // android.bluetooth.IBluetoothHeadsetClient
        public boolean acceptCall(BluetoothDevice device, int flag) throws RemoteException {
            return false;
        }

        @Override // android.bluetooth.IBluetoothHeadsetClient
        public boolean holdCall(BluetoothDevice device) throws RemoteException {
            return false;
        }

        @Override // android.bluetooth.IBluetoothHeadsetClient
        public boolean rejectCall(BluetoothDevice device) throws RemoteException {
            return false;
        }

        @Override // android.bluetooth.IBluetoothHeadsetClient
        public boolean terminateCall(BluetoothDevice device, BluetoothHeadsetClientCall call) throws RemoteException {
            return false;
        }

        @Override // android.bluetooth.IBluetoothHeadsetClient
        public boolean enterPrivateMode(BluetoothDevice device, int index) throws RemoteException {
            return false;
        }

        @Override // android.bluetooth.IBluetoothHeadsetClient
        public boolean explicitCallTransfer(BluetoothDevice device) throws RemoteException {
            return false;
        }

        @Override // android.bluetooth.IBluetoothHeadsetClient
        public BluetoothHeadsetClientCall dial(BluetoothDevice device, String number) throws RemoteException {
            return null;
        }

        @Override // android.bluetooth.IBluetoothHeadsetClient
        public boolean sendDTMF(BluetoothDevice device, byte code) throws RemoteException {
            return false;
        }

        @Override // android.bluetooth.IBluetoothHeadsetClient
        public boolean getLastVoiceTagNumber(BluetoothDevice device) throws RemoteException {
            return false;
        }

        @Override // android.bluetooth.IBluetoothHeadsetClient
        public int getAudioState(BluetoothDevice device) throws RemoteException {
            return 0;
        }

        @Override // android.bluetooth.IBluetoothHeadsetClient
        public boolean connectAudio(BluetoothDevice device) throws RemoteException {
            return false;
        }

        @Override // android.bluetooth.IBluetoothHeadsetClient
        public boolean disconnectAudio(BluetoothDevice device) throws RemoteException {
            return false;
        }

        @Override // android.bluetooth.IBluetoothHeadsetClient
        public void setAudioRouteAllowed(BluetoothDevice device, boolean allowed) throws RemoteException {
        }

        @Override // android.bluetooth.IBluetoothHeadsetClient
        public boolean getAudioRouteAllowed(BluetoothDevice device) throws RemoteException {
            return false;
        }

        @Override // android.bluetooth.IBluetoothHeadsetClient
        public Bundle getCurrentAgFeatures(BluetoothDevice device) throws RemoteException {
            return null;
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* loaded from: classes.dex */
    public static abstract class Stub extends Binder implements IBluetoothHeadsetClient {
        private static final String DESCRIPTOR = "android.bluetooth.IBluetoothHeadsetClient";
        static final int TRANSACTION_acceptCall = 12;
        static final int TRANSACTION_connect = 1;
        static final int TRANSACTION_connectAudio = 22;
        static final int TRANSACTION_dial = 18;
        static final int TRANSACTION_disconnect = 2;
        static final int TRANSACTION_disconnectAudio = 23;
        static final int TRANSACTION_enterPrivateMode = 16;
        static final int TRANSACTION_explicitCallTransfer = 17;
        static final int TRANSACTION_getAudioRouteAllowed = 25;
        static final int TRANSACTION_getAudioState = 21;
        static final int TRANSACTION_getConnectedDevices = 3;
        static final int TRANSACTION_getConnectionState = 5;
        static final int TRANSACTION_getCurrentAgEvents = 11;
        static final int TRANSACTION_getCurrentAgFeatures = 26;
        static final int TRANSACTION_getCurrentCalls = 10;
        static final int TRANSACTION_getDevicesMatchingConnectionStates = 4;
        static final int TRANSACTION_getLastVoiceTagNumber = 20;
        static final int TRANSACTION_getPriority = 7;
        static final int TRANSACTION_holdCall = 13;
        static final int TRANSACTION_rejectCall = 14;
        static final int TRANSACTION_sendDTMF = 19;
        static final int TRANSACTION_setAudioRouteAllowed = 24;
        static final int TRANSACTION_setPriority = 6;
        static final int TRANSACTION_startVoiceRecognition = 8;
        static final int TRANSACTION_stopVoiceRecognition = 9;
        static final int TRANSACTION_terminateCall = 15;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IBluetoothHeadsetClient asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IBluetoothHeadsetClient)) {
                return (IBluetoothHeadsetClient) iin;
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
                case 8:
                    return "startVoiceRecognition";
                case 9:
                    return "stopVoiceRecognition";
                case 10:
                    return "getCurrentCalls";
                case 11:
                    return "getCurrentAgEvents";
                case 12:
                    return "acceptCall";
                case 13:
                    return "holdCall";
                case 14:
                    return "rejectCall";
                case 15:
                    return "terminateCall";
                case 16:
                    return "enterPrivateMode";
                case 17:
                    return "explicitCallTransfer";
                case 18:
                    return "dial";
                case 19:
                    return "sendDTMF";
                case 20:
                    return "getLastVoiceTagNumber";
                case 21:
                    return "getAudioState";
                case 22:
                    return "connectAudio";
                case 23:
                    return "disconnectAudio";
                case 24:
                    return "setAudioRouteAllowed";
                case 25:
                    return "getAudioRouteAllowed";
                case 26:
                    return "getCurrentAgFeatures";
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
                    BluetoothDevice _arg07 = _arg06;
                    int _arg1 = data.readInt();
                    boolean priority = setPriority(_arg07, _arg1);
                    reply.writeNoException();
                    reply.writeInt(priority ? 1 : 0);
                    return true;
                case 7:
                    data.enforceInterface(DESCRIPTOR);
                    BluetoothDevice _arg08 = data.readInt() != 0 ? BluetoothDevice.CREATOR.createFromParcel(data) : null;
                    int _result4 = getPriority(_arg08);
                    reply.writeNoException();
                    reply.writeInt(_result4);
                    return true;
                case 8:
                    data.enforceInterface(DESCRIPTOR);
                    BluetoothDevice _arg09 = data.readInt() != 0 ? BluetoothDevice.CREATOR.createFromParcel(data) : null;
                    boolean startVoiceRecognition = startVoiceRecognition(_arg09);
                    reply.writeNoException();
                    reply.writeInt(startVoiceRecognition ? 1 : 0);
                    return true;
                case 9:
                    data.enforceInterface(DESCRIPTOR);
                    BluetoothDevice _arg010 = data.readInt() != 0 ? BluetoothDevice.CREATOR.createFromParcel(data) : null;
                    boolean stopVoiceRecognition = stopVoiceRecognition(_arg010);
                    reply.writeNoException();
                    reply.writeInt(stopVoiceRecognition ? 1 : 0);
                    return true;
                case 10:
                    data.enforceInterface(DESCRIPTOR);
                    BluetoothDevice _arg011 = data.readInt() != 0 ? BluetoothDevice.CREATOR.createFromParcel(data) : null;
                    List<BluetoothHeadsetClientCall> _result5 = getCurrentCalls(_arg011);
                    reply.writeNoException();
                    reply.writeTypedList(_result5);
                    return true;
                case 11:
                    data.enforceInterface(DESCRIPTOR);
                    BluetoothDevice _arg012 = data.readInt() != 0 ? BluetoothDevice.CREATOR.createFromParcel(data) : null;
                    Bundle _result6 = getCurrentAgEvents(_arg012);
                    reply.writeNoException();
                    if (_result6 != null) {
                        reply.writeInt(1);
                        _result6.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 12:
                    data.enforceInterface(DESCRIPTOR);
                    BluetoothDevice _arg013 = data.readInt() != 0 ? BluetoothDevice.CREATOR.createFromParcel(data) : null;
                    BluetoothDevice _arg014 = _arg013;
                    int _arg12 = data.readInt();
                    boolean acceptCall = acceptCall(_arg014, _arg12);
                    reply.writeNoException();
                    reply.writeInt(acceptCall ? 1 : 0);
                    return true;
                case 13:
                    data.enforceInterface(DESCRIPTOR);
                    BluetoothDevice _arg015 = data.readInt() != 0 ? BluetoothDevice.CREATOR.createFromParcel(data) : null;
                    boolean holdCall = holdCall(_arg015);
                    reply.writeNoException();
                    reply.writeInt(holdCall ? 1 : 0);
                    return true;
                case 14:
                    data.enforceInterface(DESCRIPTOR);
                    BluetoothDevice _arg016 = data.readInt() != 0 ? BluetoothDevice.CREATOR.createFromParcel(data) : null;
                    boolean rejectCall = rejectCall(_arg016);
                    reply.writeNoException();
                    reply.writeInt(rejectCall ? 1 : 0);
                    return true;
                case 15:
                    data.enforceInterface(DESCRIPTOR);
                    if (data.readInt() != 0) {
                        _arg0 = BluetoothDevice.CREATOR.createFromParcel(data);
                    } else {
                        _arg0 = null;
                    }
                    BluetoothHeadsetClientCall _arg13 = data.readInt() != 0 ? BluetoothHeadsetClientCall.CREATOR.createFromParcel(data) : null;
                    boolean terminateCall = terminateCall(_arg0, _arg13);
                    reply.writeNoException();
                    reply.writeInt(terminateCall ? 1 : 0);
                    return true;
                case 16:
                    data.enforceInterface(DESCRIPTOR);
                    BluetoothDevice _arg017 = data.readInt() != 0 ? BluetoothDevice.CREATOR.createFromParcel(data) : null;
                    BluetoothDevice _arg018 = _arg017;
                    int _arg14 = data.readInt();
                    boolean enterPrivateMode = enterPrivateMode(_arg018, _arg14);
                    reply.writeNoException();
                    reply.writeInt(enterPrivateMode ? 1 : 0);
                    return true;
                case 17:
                    data.enforceInterface(DESCRIPTOR);
                    BluetoothDevice _arg019 = data.readInt() != 0 ? BluetoothDevice.CREATOR.createFromParcel(data) : null;
                    boolean explicitCallTransfer = explicitCallTransfer(_arg019);
                    reply.writeNoException();
                    reply.writeInt(explicitCallTransfer ? 1 : 0);
                    return true;
                case 18:
                    data.enforceInterface(DESCRIPTOR);
                    BluetoothDevice _arg020 = data.readInt() != 0 ? BluetoothDevice.CREATOR.createFromParcel(data) : null;
                    String _arg15 = data.readString();
                    BluetoothHeadsetClientCall _result7 = dial(_arg020, _arg15);
                    reply.writeNoException();
                    if (_result7 != null) {
                        reply.writeInt(1);
                        _result7.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 19:
                    data.enforceInterface(DESCRIPTOR);
                    BluetoothDevice _arg021 = data.readInt() != 0 ? BluetoothDevice.CREATOR.createFromParcel(data) : null;
                    BluetoothDevice _arg022 = _arg021;
                    byte _arg16 = data.readByte();
                    boolean sendDTMF = sendDTMF(_arg022, _arg16);
                    reply.writeNoException();
                    reply.writeInt(sendDTMF ? 1 : 0);
                    return true;
                case 20:
                    data.enforceInterface(DESCRIPTOR);
                    BluetoothDevice _arg023 = data.readInt() != 0 ? BluetoothDevice.CREATOR.createFromParcel(data) : null;
                    boolean lastVoiceTagNumber = getLastVoiceTagNumber(_arg023);
                    reply.writeNoException();
                    reply.writeInt(lastVoiceTagNumber ? 1 : 0);
                    return true;
                case 21:
                    data.enforceInterface(DESCRIPTOR);
                    BluetoothDevice _arg024 = data.readInt() != 0 ? BluetoothDevice.CREATOR.createFromParcel(data) : null;
                    int _result8 = getAudioState(_arg024);
                    reply.writeNoException();
                    reply.writeInt(_result8);
                    return true;
                case 22:
                    data.enforceInterface(DESCRIPTOR);
                    BluetoothDevice _arg025 = data.readInt() != 0 ? BluetoothDevice.CREATOR.createFromParcel(data) : null;
                    boolean connectAudio = connectAudio(_arg025);
                    reply.writeNoException();
                    reply.writeInt(connectAudio ? 1 : 0);
                    return true;
                case 23:
                    data.enforceInterface(DESCRIPTOR);
                    BluetoothDevice _arg026 = data.readInt() != 0 ? BluetoothDevice.CREATOR.createFromParcel(data) : null;
                    boolean disconnectAudio = disconnectAudio(_arg026);
                    reply.writeNoException();
                    reply.writeInt(disconnectAudio ? 1 : 0);
                    return true;
                case 24:
                    data.enforceInterface(DESCRIPTOR);
                    BluetoothDevice _arg027 = data.readInt() != 0 ? BluetoothDevice.CREATOR.createFromParcel(data) : null;
                    boolean _arg17 = data.readInt() != 0;
                    setAudioRouteAllowed(_arg027, _arg17);
                    reply.writeNoException();
                    return true;
                case 25:
                    data.enforceInterface(DESCRIPTOR);
                    BluetoothDevice _arg028 = data.readInt() != 0 ? BluetoothDevice.CREATOR.createFromParcel(data) : null;
                    boolean audioRouteAllowed = getAudioRouteAllowed(_arg028);
                    reply.writeNoException();
                    reply.writeInt(audioRouteAllowed ? 1 : 0);
                    return true;
                case 26:
                    data.enforceInterface(DESCRIPTOR);
                    BluetoothDevice _arg029 = data.readInt() != 0 ? BluetoothDevice.CREATOR.createFromParcel(data) : null;
                    Bundle _result9 = getCurrentAgFeatures(_arg029);
                    reply.writeNoException();
                    if (_result9 != null) {
                        reply.writeInt(1);
                        _result9.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* loaded from: classes.dex */
        private static class Proxy implements IBluetoothHeadsetClient {
            public static IBluetoothHeadsetClient sDefaultImpl;
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

            @Override // android.bluetooth.IBluetoothHeadsetClient
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

            @Override // android.bluetooth.IBluetoothHeadsetClient
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

            @Override // android.bluetooth.IBluetoothHeadsetClient
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

            @Override // android.bluetooth.IBluetoothHeadsetClient
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

            @Override // android.bluetooth.IBluetoothHeadsetClient
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

            @Override // android.bluetooth.IBluetoothHeadsetClient
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

            @Override // android.bluetooth.IBluetoothHeadsetClient
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

            @Override // android.bluetooth.IBluetoothHeadsetClient
            public boolean startVoiceRecognition(BluetoothDevice device) throws RemoteException {
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
                    boolean _status = this.mRemote.transact(8, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().startVoiceRecognition(device);
                    }
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.bluetooth.IBluetoothHeadsetClient
            public boolean stopVoiceRecognition(BluetoothDevice device) throws RemoteException {
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
                        return Stub.getDefaultImpl().stopVoiceRecognition(device);
                    }
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.bluetooth.IBluetoothHeadsetClient
            public List<BluetoothHeadsetClientCall> getCurrentCalls(BluetoothDevice device) throws RemoteException {
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
                    boolean _status = this.mRemote.transact(10, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getCurrentCalls(device);
                    }
                    _reply.readException();
                    List<BluetoothHeadsetClientCall> _result = _reply.createTypedArrayList(BluetoothHeadsetClientCall.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.bluetooth.IBluetoothHeadsetClient
            public Bundle getCurrentAgEvents(BluetoothDevice device) throws RemoteException {
                Bundle _result;
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
                    boolean _status = this.mRemote.transact(11, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getCurrentAgEvents(device);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = Bundle.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.bluetooth.IBluetoothHeadsetClient
            public boolean acceptCall(BluetoothDevice device, int flag) throws RemoteException {
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
                    _data.writeInt(flag);
                    boolean _status = this.mRemote.transact(12, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().acceptCall(device, flag);
                    }
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.bluetooth.IBluetoothHeadsetClient
            public boolean holdCall(BluetoothDevice device) throws RemoteException {
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
                        return Stub.getDefaultImpl().holdCall(device);
                    }
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.bluetooth.IBluetoothHeadsetClient
            public boolean rejectCall(BluetoothDevice device) throws RemoteException {
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
                    boolean _status = this.mRemote.transact(14, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().rejectCall(device);
                    }
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.bluetooth.IBluetoothHeadsetClient
            public boolean terminateCall(BluetoothDevice device, BluetoothHeadsetClientCall call) throws RemoteException {
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
                    if (call != null) {
                        _data.writeInt(1);
                        call.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(15, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().terminateCall(device, call);
                    }
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.bluetooth.IBluetoothHeadsetClient
            public boolean enterPrivateMode(BluetoothDevice device, int index) throws RemoteException {
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
                    _data.writeInt(index);
                    boolean _status = this.mRemote.transact(16, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().enterPrivateMode(device, index);
                    }
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.bluetooth.IBluetoothHeadsetClient
            public boolean explicitCallTransfer(BluetoothDevice device) throws RemoteException {
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
                        return Stub.getDefaultImpl().explicitCallTransfer(device);
                    }
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.bluetooth.IBluetoothHeadsetClient
            public BluetoothHeadsetClientCall dial(BluetoothDevice device, String number) throws RemoteException {
                BluetoothHeadsetClientCall _result;
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
                    _data.writeString(number);
                    boolean _status = this.mRemote.transact(18, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().dial(device, number);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = BluetoothHeadsetClientCall.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.bluetooth.IBluetoothHeadsetClient
            public boolean sendDTMF(BluetoothDevice device, byte code) throws RemoteException {
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
                    _data.writeByte(code);
                    boolean _status = this.mRemote.transact(19, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().sendDTMF(device, code);
                    }
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.bluetooth.IBluetoothHeadsetClient
            public boolean getLastVoiceTagNumber(BluetoothDevice device) throws RemoteException {
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
                    boolean _status = this.mRemote.transact(20, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getLastVoiceTagNumber(device);
                    }
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.bluetooth.IBluetoothHeadsetClient
            public int getAudioState(BluetoothDevice device) throws RemoteException {
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
                    boolean _status = this.mRemote.transact(21, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAudioState(device);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.bluetooth.IBluetoothHeadsetClient
            public boolean connectAudio(BluetoothDevice device) throws RemoteException {
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
                    boolean _status = this.mRemote.transact(22, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().connectAudio(device);
                    }
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.bluetooth.IBluetoothHeadsetClient
            public boolean disconnectAudio(BluetoothDevice device) throws RemoteException {
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
                    boolean _status = this.mRemote.transact(23, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().disconnectAudio(device);
                    }
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.bluetooth.IBluetoothHeadsetClient
            public void setAudioRouteAllowed(BluetoothDevice device, boolean allowed) throws RemoteException {
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
                    _data.writeInt(allowed ? 1 : 0);
                    boolean _status = this.mRemote.transact(24, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setAudioRouteAllowed(device, allowed);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.bluetooth.IBluetoothHeadsetClient
            public boolean getAudioRouteAllowed(BluetoothDevice device) throws RemoteException {
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
                    boolean _status = this.mRemote.transact(25, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAudioRouteAllowed(device);
                    }
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.bluetooth.IBluetoothHeadsetClient
            public Bundle getCurrentAgFeatures(BluetoothDevice device) throws RemoteException {
                Bundle _result;
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
                    boolean _status = this.mRemote.transact(26, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getCurrentAgFeatures(device);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = Bundle.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IBluetoothHeadsetClient impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static IBluetoothHeadsetClient getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
