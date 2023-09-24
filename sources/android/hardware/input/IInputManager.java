package android.hardware.input;

import android.annotation.UnsupportedAppUsage;
import android.hardware.input.IInputDevicesChangedListener;
import android.hardware.input.ITabletModeChangedListener;
import android.p007os.Binder;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.RemoteException;
import android.view.InputDevice;
import android.view.InputEvent;
import android.view.InputMonitor;
import android.view.PointerIcon;

/* loaded from: classes.dex */
public interface IInputManager extends IInterface {
    void addKeyboardLayoutForInputDevice(InputDeviceIdentifier inputDeviceIdentifier, String str) throws RemoteException;

    void cancelVibrate(int i, IBinder iBinder) throws RemoteException;

    void disableInputDevice(int i) throws RemoteException;

    void enableInputDevice(int i) throws RemoteException;

    String getCurrentKeyboardLayoutForInputDevice(InputDeviceIdentifier inputDeviceIdentifier) throws RemoteException;

    String[] getEnabledKeyboardLayoutsForInputDevice(InputDeviceIdentifier inputDeviceIdentifier) throws RemoteException;

    InputDevice getInputDevice(int i) throws RemoteException;

    int[] getInputDeviceIds() throws RemoteException;

    KeyboardLayout getKeyboardLayout(String str) throws RemoteException;

    KeyboardLayout[] getKeyboardLayouts() throws RemoteException;

    KeyboardLayout[] getKeyboardLayoutsForInputDevice(InputDeviceIdentifier inputDeviceIdentifier) throws RemoteException;

    TouchCalibration getTouchCalibrationForInputDevice(String str, int i) throws RemoteException;

    boolean hasKeys(int i, int i2, int[] iArr, boolean[] zArr) throws RemoteException;

    @UnsupportedAppUsage
    boolean injectInputEvent(InputEvent inputEvent, int i) throws RemoteException;

    int isInTabletMode() throws RemoteException;

    boolean isInputDeviceEnabled(int i) throws RemoteException;

    InputMonitor monitorGestureInput(String str, int i) throws RemoteException;

    void registerInputDevicesChangedListener(IInputDevicesChangedListener iInputDevicesChangedListener) throws RemoteException;

    void registerTabletModeChangedListener(ITabletModeChangedListener iTabletModeChangedListener) throws RemoteException;

    void removeKeyboardLayoutForInputDevice(InputDeviceIdentifier inputDeviceIdentifier, String str) throws RemoteException;

    void requestPointerCapture(IBinder iBinder, boolean z) throws RemoteException;

    void setCurrentKeyboardLayoutForInputDevice(InputDeviceIdentifier inputDeviceIdentifier, String str) throws RemoteException;

    void setCustomPointerIcon(PointerIcon pointerIcon) throws RemoteException;

    void setPointerIconType(int i) throws RemoteException;

    void setTouchCalibrationForInputDevice(String str, int i, TouchCalibration touchCalibration) throws RemoteException;

    void tryPointerSpeed(int i) throws RemoteException;

    void vibrate(int i, long[] jArr, int i2, IBinder iBinder) throws RemoteException;

    /* loaded from: classes.dex */
    public static class Default implements IInputManager {
        @Override // android.hardware.input.IInputManager
        public InputDevice getInputDevice(int deviceId) throws RemoteException {
            return null;
        }

        @Override // android.hardware.input.IInputManager
        public int[] getInputDeviceIds() throws RemoteException {
            return null;
        }

        @Override // android.hardware.input.IInputManager
        public boolean isInputDeviceEnabled(int deviceId) throws RemoteException {
            return false;
        }

        @Override // android.hardware.input.IInputManager
        public void enableInputDevice(int deviceId) throws RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public void disableInputDevice(int deviceId) throws RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public boolean hasKeys(int deviceId, int sourceMask, int[] keyCodes, boolean[] keyExists) throws RemoteException {
            return false;
        }

        @Override // android.hardware.input.IInputManager
        public void tryPointerSpeed(int speed) throws RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public boolean injectInputEvent(InputEvent ev, int mode) throws RemoteException {
            return false;
        }

        @Override // android.hardware.input.IInputManager
        public TouchCalibration getTouchCalibrationForInputDevice(String inputDeviceDescriptor, int rotation) throws RemoteException {
            return null;
        }

        @Override // android.hardware.input.IInputManager
        public void setTouchCalibrationForInputDevice(String inputDeviceDescriptor, int rotation, TouchCalibration calibration) throws RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public KeyboardLayout[] getKeyboardLayouts() throws RemoteException {
            return null;
        }

        @Override // android.hardware.input.IInputManager
        public KeyboardLayout[] getKeyboardLayoutsForInputDevice(InputDeviceIdentifier identifier) throws RemoteException {
            return null;
        }

        @Override // android.hardware.input.IInputManager
        public KeyboardLayout getKeyboardLayout(String keyboardLayoutDescriptor) throws RemoteException {
            return null;
        }

        @Override // android.hardware.input.IInputManager
        public String getCurrentKeyboardLayoutForInputDevice(InputDeviceIdentifier identifier) throws RemoteException {
            return null;
        }

        @Override // android.hardware.input.IInputManager
        public void setCurrentKeyboardLayoutForInputDevice(InputDeviceIdentifier identifier, String keyboardLayoutDescriptor) throws RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public String[] getEnabledKeyboardLayoutsForInputDevice(InputDeviceIdentifier identifier) throws RemoteException {
            return null;
        }

        @Override // android.hardware.input.IInputManager
        public void addKeyboardLayoutForInputDevice(InputDeviceIdentifier identifier, String keyboardLayoutDescriptor) throws RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public void removeKeyboardLayoutForInputDevice(InputDeviceIdentifier identifier, String keyboardLayoutDescriptor) throws RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public void registerInputDevicesChangedListener(IInputDevicesChangedListener listener) throws RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public int isInTabletMode() throws RemoteException {
            return 0;
        }

        @Override // android.hardware.input.IInputManager
        public void registerTabletModeChangedListener(ITabletModeChangedListener listener) throws RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public void vibrate(int deviceId, long[] pattern, int repeat, IBinder token) throws RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public void cancelVibrate(int deviceId, IBinder token) throws RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public void setPointerIconType(int typeId) throws RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public void setCustomPointerIcon(PointerIcon icon) throws RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public void requestPointerCapture(IBinder windowToken, boolean enabled) throws RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public InputMonitor monitorGestureInput(String name, int displayId) throws RemoteException {
            return null;
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* loaded from: classes.dex */
    public static abstract class Stub extends Binder implements IInputManager {
        private static final String DESCRIPTOR = "android.hardware.input.IInputManager";
        static final int TRANSACTION_addKeyboardLayoutForInputDevice = 17;
        static final int TRANSACTION_cancelVibrate = 23;
        static final int TRANSACTION_disableInputDevice = 5;
        static final int TRANSACTION_enableInputDevice = 4;
        static final int TRANSACTION_getCurrentKeyboardLayoutForInputDevice = 14;
        static final int TRANSACTION_getEnabledKeyboardLayoutsForInputDevice = 16;
        static final int TRANSACTION_getInputDevice = 1;
        static final int TRANSACTION_getInputDeviceIds = 2;
        static final int TRANSACTION_getKeyboardLayout = 13;
        static final int TRANSACTION_getKeyboardLayouts = 11;
        static final int TRANSACTION_getKeyboardLayoutsForInputDevice = 12;
        static final int TRANSACTION_getTouchCalibrationForInputDevice = 9;
        static final int TRANSACTION_hasKeys = 6;
        static final int TRANSACTION_injectInputEvent = 8;
        static final int TRANSACTION_isInTabletMode = 20;
        static final int TRANSACTION_isInputDeviceEnabled = 3;
        static final int TRANSACTION_monitorGestureInput = 27;
        static final int TRANSACTION_registerInputDevicesChangedListener = 19;
        static final int TRANSACTION_registerTabletModeChangedListener = 21;
        static final int TRANSACTION_removeKeyboardLayoutForInputDevice = 18;
        static final int TRANSACTION_requestPointerCapture = 26;
        static final int TRANSACTION_setCurrentKeyboardLayoutForInputDevice = 15;
        static final int TRANSACTION_setCustomPointerIcon = 25;
        static final int TRANSACTION_setPointerIconType = 24;
        static final int TRANSACTION_setTouchCalibrationForInputDevice = 10;
        static final int TRANSACTION_tryPointerSpeed = 7;
        static final int TRANSACTION_vibrate = 22;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IInputManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IInputManager)) {
                return (IInputManager) iin;
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
                    return "getInputDevice";
                case 2:
                    return "getInputDeviceIds";
                case 3:
                    return "isInputDeviceEnabled";
                case 4:
                    return "enableInputDevice";
                case 5:
                    return "disableInputDevice";
                case 6:
                    return "hasKeys";
                case 7:
                    return "tryPointerSpeed";
                case 8:
                    return "injectInputEvent";
                case 9:
                    return "getTouchCalibrationForInputDevice";
                case 10:
                    return "setTouchCalibrationForInputDevice";
                case 11:
                    return "getKeyboardLayouts";
                case 12:
                    return "getKeyboardLayoutsForInputDevice";
                case 13:
                    return "getKeyboardLayout";
                case 14:
                    return "getCurrentKeyboardLayoutForInputDevice";
                case 15:
                    return "setCurrentKeyboardLayoutForInputDevice";
                case 16:
                    return "getEnabledKeyboardLayoutsForInputDevice";
                case 17:
                    return "addKeyboardLayoutForInputDevice";
                case 18:
                    return "removeKeyboardLayoutForInputDevice";
                case 19:
                    return "registerInputDevicesChangedListener";
                case 20:
                    return "isInTabletMode";
                case 21:
                    return "registerTabletModeChangedListener";
                case 22:
                    return "vibrate";
                case 23:
                    return "cancelVibrate";
                case 24:
                    return "setPointerIconType";
                case 25:
                    return "setCustomPointerIcon";
                case 26:
                    return "requestPointerCapture";
                case 27:
                    return "monitorGestureInput";
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
            boolean[] _arg3;
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg0 = data.readInt();
                    InputDevice _result = getInputDevice(_arg0);
                    reply.writeNoException();
                    if (_result != null) {
                        reply.writeInt(1);
                        _result.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    int[] _result2 = getInputDeviceIds();
                    reply.writeNoException();
                    reply.writeIntArray(_result2);
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg02 = data.readInt();
                    boolean isInputDeviceEnabled = isInputDeviceEnabled(_arg02);
                    reply.writeNoException();
                    reply.writeInt(isInputDeviceEnabled ? 1 : 0);
                    return true;
                case 4:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg03 = data.readInt();
                    enableInputDevice(_arg03);
                    reply.writeNoException();
                    return true;
                case 5:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg04 = data.readInt();
                    disableInputDevice(_arg04);
                    reply.writeNoException();
                    return true;
                case 6:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg05 = data.readInt();
                    int _arg1 = data.readInt();
                    int[] _arg2 = data.createIntArray();
                    int _arg3_length = data.readInt();
                    if (_arg3_length < 0) {
                        _arg3 = null;
                    } else {
                        _arg3 = new boolean[_arg3_length];
                    }
                    boolean hasKeys = hasKeys(_arg05, _arg1, _arg2, _arg3);
                    reply.writeNoException();
                    reply.writeInt(hasKeys ? 1 : 0);
                    reply.writeBooleanArray(_arg3);
                    return true;
                case 7:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg06 = data.readInt();
                    tryPointerSpeed(_arg06);
                    reply.writeNoException();
                    return true;
                case 8:
                    data.enforceInterface(DESCRIPTOR);
                    InputEvent _arg07 = data.readInt() != 0 ? InputEvent.CREATOR.createFromParcel(data) : null;
                    InputEvent _arg08 = _arg07;
                    int _arg12 = data.readInt();
                    boolean injectInputEvent = injectInputEvent(_arg08, _arg12);
                    reply.writeNoException();
                    reply.writeInt(injectInputEvent ? 1 : 0);
                    return true;
                case 9:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg09 = data.readString();
                    int _arg13 = data.readInt();
                    TouchCalibration _result3 = getTouchCalibrationForInputDevice(_arg09, _arg13);
                    reply.writeNoException();
                    if (_result3 != null) {
                        reply.writeInt(1);
                        _result3.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 10:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg010 = data.readString();
                    int _arg14 = data.readInt();
                    TouchCalibration _arg22 = data.readInt() != 0 ? TouchCalibration.CREATOR.createFromParcel(data) : null;
                    setTouchCalibrationForInputDevice(_arg010, _arg14, _arg22);
                    reply.writeNoException();
                    return true;
                case 11:
                    data.enforceInterface(DESCRIPTOR);
                    KeyboardLayout[] _result4 = getKeyboardLayouts();
                    reply.writeNoException();
                    reply.writeTypedArray(_result4, 1);
                    return true;
                case 12:
                    data.enforceInterface(DESCRIPTOR);
                    InputDeviceIdentifier _arg011 = data.readInt() != 0 ? InputDeviceIdentifier.CREATOR.createFromParcel(data) : null;
                    KeyboardLayout[] _result5 = getKeyboardLayoutsForInputDevice(_arg011);
                    reply.writeNoException();
                    reply.writeTypedArray(_result5, 1);
                    return true;
                case 13:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg012 = data.readString();
                    KeyboardLayout _result6 = getKeyboardLayout(_arg012);
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
                    InputDeviceIdentifier _arg013 = data.readInt() != 0 ? InputDeviceIdentifier.CREATOR.createFromParcel(data) : null;
                    String _result7 = getCurrentKeyboardLayoutForInputDevice(_arg013);
                    reply.writeNoException();
                    reply.writeString(_result7);
                    return true;
                case 15:
                    data.enforceInterface(DESCRIPTOR);
                    InputDeviceIdentifier _arg014 = data.readInt() != 0 ? InputDeviceIdentifier.CREATOR.createFromParcel(data) : null;
                    InputDeviceIdentifier _arg015 = _arg014;
                    String _arg15 = data.readString();
                    setCurrentKeyboardLayoutForInputDevice(_arg015, _arg15);
                    reply.writeNoException();
                    return true;
                case 16:
                    data.enforceInterface(DESCRIPTOR);
                    InputDeviceIdentifier _arg016 = data.readInt() != 0 ? InputDeviceIdentifier.CREATOR.createFromParcel(data) : null;
                    String[] _result8 = getEnabledKeyboardLayoutsForInputDevice(_arg016);
                    reply.writeNoException();
                    reply.writeStringArray(_result8);
                    return true;
                case 17:
                    data.enforceInterface(DESCRIPTOR);
                    InputDeviceIdentifier _arg017 = data.readInt() != 0 ? InputDeviceIdentifier.CREATOR.createFromParcel(data) : null;
                    InputDeviceIdentifier _arg018 = _arg017;
                    String _arg16 = data.readString();
                    addKeyboardLayoutForInputDevice(_arg018, _arg16);
                    reply.writeNoException();
                    return true;
                case 18:
                    data.enforceInterface(DESCRIPTOR);
                    InputDeviceIdentifier _arg019 = data.readInt() != 0 ? InputDeviceIdentifier.CREATOR.createFromParcel(data) : null;
                    InputDeviceIdentifier _arg020 = _arg019;
                    String _arg17 = data.readString();
                    removeKeyboardLayoutForInputDevice(_arg020, _arg17);
                    reply.writeNoException();
                    return true;
                case 19:
                    data.enforceInterface(DESCRIPTOR);
                    IInputDevicesChangedListener _arg021 = IInputDevicesChangedListener.Stub.asInterface(data.readStrongBinder());
                    registerInputDevicesChangedListener(_arg021);
                    reply.writeNoException();
                    return true;
                case 20:
                    data.enforceInterface(DESCRIPTOR);
                    int _result9 = isInTabletMode();
                    reply.writeNoException();
                    reply.writeInt(_result9);
                    return true;
                case 21:
                    data.enforceInterface(DESCRIPTOR);
                    ITabletModeChangedListener _arg022 = ITabletModeChangedListener.Stub.asInterface(data.readStrongBinder());
                    registerTabletModeChangedListener(_arg022);
                    reply.writeNoException();
                    return true;
                case 22:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg023 = data.readInt();
                    long[] _arg18 = data.createLongArray();
                    int _arg23 = data.readInt();
                    IBinder _arg32 = data.readStrongBinder();
                    vibrate(_arg023, _arg18, _arg23, _arg32);
                    reply.writeNoException();
                    return true;
                case 23:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg024 = data.readInt();
                    IBinder _arg19 = data.readStrongBinder();
                    cancelVibrate(_arg024, _arg19);
                    reply.writeNoException();
                    return true;
                case 24:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg025 = data.readInt();
                    setPointerIconType(_arg025);
                    reply.writeNoException();
                    return true;
                case 25:
                    data.enforceInterface(DESCRIPTOR);
                    PointerIcon _arg026 = data.readInt() != 0 ? PointerIcon.CREATOR.createFromParcel(data) : null;
                    setCustomPointerIcon(_arg026);
                    reply.writeNoException();
                    return true;
                case 26:
                    data.enforceInterface(DESCRIPTOR);
                    IBinder _arg027 = data.readStrongBinder();
                    boolean _arg110 = data.readInt() != 0;
                    requestPointerCapture(_arg027, _arg110);
                    reply.writeNoException();
                    return true;
                case 27:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg028 = data.readString();
                    int _arg111 = data.readInt();
                    InputMonitor _result10 = monitorGestureInput(_arg028, _arg111);
                    reply.writeNoException();
                    if (_result10 != null) {
                        reply.writeInt(1);
                        _result10.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* loaded from: classes.dex */
        private static class Proxy implements IInputManager {
            public static IInputManager sDefaultImpl;
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

            @Override // android.hardware.input.IInputManager
            public InputDevice getInputDevice(int deviceId) throws RemoteException {
                InputDevice _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(deviceId);
                    boolean _status = this.mRemote.transact(1, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getInputDevice(deviceId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = InputDevice.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public int[] getInputDeviceIds() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(2, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getInputDeviceIds();
                    }
                    _reply.readException();
                    int[] _result = _reply.createIntArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public boolean isInputDeviceEnabled(int deviceId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(deviceId);
                    boolean _status = this.mRemote.transact(3, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isInputDeviceEnabled(deviceId);
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void enableInputDevice(int deviceId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(deviceId);
                    boolean _status = this.mRemote.transact(4, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().enableInputDevice(deviceId);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void disableInputDevice(int deviceId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(deviceId);
                    boolean _status = this.mRemote.transact(5, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().disableInputDevice(deviceId);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public boolean hasKeys(int deviceId, int sourceMask, int[] keyCodes, boolean[] keyExists) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(deviceId);
                    _data.writeInt(sourceMask);
                    _data.writeIntArray(keyCodes);
                    if (keyExists == null) {
                        _data.writeInt(-1);
                    } else {
                        _data.writeInt(keyExists.length);
                    }
                    boolean _status = this.mRemote.transact(6, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().hasKeys(deviceId, sourceMask, keyCodes, keyExists);
                    }
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    _reply.readBooleanArray(keyExists);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void tryPointerSpeed(int speed) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(speed);
                    boolean _status = this.mRemote.transact(7, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().tryPointerSpeed(speed);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public boolean injectInputEvent(InputEvent ev, int mode) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (ev != null) {
                        _data.writeInt(1);
                        ev.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(mode);
                    boolean _status = this.mRemote.transact(8, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().injectInputEvent(ev, mode);
                    }
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public TouchCalibration getTouchCalibrationForInputDevice(String inputDeviceDescriptor, int rotation) throws RemoteException {
                TouchCalibration _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(inputDeviceDescriptor);
                    _data.writeInt(rotation);
                    boolean _status = this.mRemote.transact(9, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getTouchCalibrationForInputDevice(inputDeviceDescriptor, rotation);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = TouchCalibration.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void setTouchCalibrationForInputDevice(String inputDeviceDescriptor, int rotation, TouchCalibration calibration) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(inputDeviceDescriptor);
                    _data.writeInt(rotation);
                    if (calibration != null) {
                        _data.writeInt(1);
                        calibration.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(10, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setTouchCalibrationForInputDevice(inputDeviceDescriptor, rotation, calibration);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public KeyboardLayout[] getKeyboardLayouts() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(11, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getKeyboardLayouts();
                    }
                    _reply.readException();
                    KeyboardLayout[] _result = (KeyboardLayout[]) _reply.createTypedArray(KeyboardLayout.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public KeyboardLayout[] getKeyboardLayoutsForInputDevice(InputDeviceIdentifier identifier) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (identifier != null) {
                        _data.writeInt(1);
                        identifier.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(12, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getKeyboardLayoutsForInputDevice(identifier);
                    }
                    _reply.readException();
                    KeyboardLayout[] _result = (KeyboardLayout[]) _reply.createTypedArray(KeyboardLayout.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public KeyboardLayout getKeyboardLayout(String keyboardLayoutDescriptor) throws RemoteException {
                KeyboardLayout _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(keyboardLayoutDescriptor);
                    boolean _status = this.mRemote.transact(13, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getKeyboardLayout(keyboardLayoutDescriptor);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = KeyboardLayout.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public String getCurrentKeyboardLayoutForInputDevice(InputDeviceIdentifier identifier) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (identifier != null) {
                        _data.writeInt(1);
                        identifier.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(14, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getCurrentKeyboardLayoutForInputDevice(identifier);
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void setCurrentKeyboardLayoutForInputDevice(InputDeviceIdentifier identifier, String keyboardLayoutDescriptor) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (identifier != null) {
                        _data.writeInt(1);
                        identifier.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(keyboardLayoutDescriptor);
                    boolean _status = this.mRemote.transact(15, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setCurrentKeyboardLayoutForInputDevice(identifier, keyboardLayoutDescriptor);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public String[] getEnabledKeyboardLayoutsForInputDevice(InputDeviceIdentifier identifier) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (identifier != null) {
                        _data.writeInt(1);
                        identifier.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(16, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getEnabledKeyboardLayoutsForInputDevice(identifier);
                    }
                    _reply.readException();
                    String[] _result = _reply.createStringArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void addKeyboardLayoutForInputDevice(InputDeviceIdentifier identifier, String keyboardLayoutDescriptor) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (identifier != null) {
                        _data.writeInt(1);
                        identifier.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(keyboardLayoutDescriptor);
                    boolean _status = this.mRemote.transact(17, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().addKeyboardLayoutForInputDevice(identifier, keyboardLayoutDescriptor);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void removeKeyboardLayoutForInputDevice(InputDeviceIdentifier identifier, String keyboardLayoutDescriptor) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (identifier != null) {
                        _data.writeInt(1);
                        identifier.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(keyboardLayoutDescriptor);
                    boolean _status = this.mRemote.transact(18, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeKeyboardLayoutForInputDevice(identifier, keyboardLayoutDescriptor);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void registerInputDevicesChangedListener(IInputDevicesChangedListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    boolean _status = this.mRemote.transact(19, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registerInputDevicesChangedListener(listener);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public int isInTabletMode() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(20, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isInTabletMode();
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void registerTabletModeChangedListener(ITabletModeChangedListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    boolean _status = this.mRemote.transact(21, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registerTabletModeChangedListener(listener);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void vibrate(int deviceId, long[] pattern, int repeat, IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(deviceId);
                    _data.writeLongArray(pattern);
                    _data.writeInt(repeat);
                    _data.writeStrongBinder(token);
                    boolean _status = this.mRemote.transact(22, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().vibrate(deviceId, pattern, repeat, token);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void cancelVibrate(int deviceId, IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(deviceId);
                    _data.writeStrongBinder(token);
                    boolean _status = this.mRemote.transact(23, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().cancelVibrate(deviceId, token);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void setPointerIconType(int typeId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(typeId);
                    boolean _status = this.mRemote.transact(24, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setPointerIconType(typeId);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void setCustomPointerIcon(PointerIcon icon) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (icon != null) {
                        _data.writeInt(1);
                        icon.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(25, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setCustomPointerIcon(icon);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void requestPointerCapture(IBinder windowToken, boolean enabled) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(windowToken);
                    _data.writeInt(enabled ? 1 : 0);
                    boolean _status = this.mRemote.transact(26, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().requestPointerCapture(windowToken, enabled);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public InputMonitor monitorGestureInput(String name, int displayId) throws RemoteException {
                InputMonitor _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    _data.writeInt(displayId);
                    boolean _status = this.mRemote.transact(27, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().monitorGestureInput(name, displayId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = InputMonitor.CREATOR.createFromParcel(_reply);
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

        public static boolean setDefaultImpl(IInputManager impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static IInputManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
