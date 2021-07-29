package android.hardware.input;

import android.annotation.UnsupportedAppUsage;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.InputDevice;
import android.view.InputEvent;
import android.view.InputMonitor;
import android.view.PointerIcon;

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

    public static class Default implements IInputManager {
        public InputDevice getInputDevice(int deviceId) throws RemoteException {
            return null;
        }

        public int[] getInputDeviceIds() throws RemoteException {
            return null;
        }

        public boolean isInputDeviceEnabled(int deviceId) throws RemoteException {
            return false;
        }

        public void enableInputDevice(int deviceId) throws RemoteException {
        }

        public void disableInputDevice(int deviceId) throws RemoteException {
        }

        public boolean hasKeys(int deviceId, int sourceMask, int[] keyCodes, boolean[] keyExists) throws RemoteException {
            return false;
        }

        public void tryPointerSpeed(int speed) throws RemoteException {
        }

        public boolean injectInputEvent(InputEvent ev, int mode) throws RemoteException {
            return false;
        }

        public TouchCalibration getTouchCalibrationForInputDevice(String inputDeviceDescriptor, int rotation) throws RemoteException {
            return null;
        }

        public void setTouchCalibrationForInputDevice(String inputDeviceDescriptor, int rotation, TouchCalibration calibration) throws RemoteException {
        }

        public KeyboardLayout[] getKeyboardLayouts() throws RemoteException {
            return null;
        }

        public KeyboardLayout[] getKeyboardLayoutsForInputDevice(InputDeviceIdentifier identifier) throws RemoteException {
            return null;
        }

        public KeyboardLayout getKeyboardLayout(String keyboardLayoutDescriptor) throws RemoteException {
            return null;
        }

        public String getCurrentKeyboardLayoutForInputDevice(InputDeviceIdentifier identifier) throws RemoteException {
            return null;
        }

        public void setCurrentKeyboardLayoutForInputDevice(InputDeviceIdentifier identifier, String keyboardLayoutDescriptor) throws RemoteException {
        }

        public String[] getEnabledKeyboardLayoutsForInputDevice(InputDeviceIdentifier identifier) throws RemoteException {
            return null;
        }

        public void addKeyboardLayoutForInputDevice(InputDeviceIdentifier identifier, String keyboardLayoutDescriptor) throws RemoteException {
        }

        public void removeKeyboardLayoutForInputDevice(InputDeviceIdentifier identifier, String keyboardLayoutDescriptor) throws RemoteException {
        }

        public void registerInputDevicesChangedListener(IInputDevicesChangedListener listener) throws RemoteException {
        }

        public int isInTabletMode() throws RemoteException {
            return 0;
        }

        public void registerTabletModeChangedListener(ITabletModeChangedListener listener) throws RemoteException {
        }

        public void vibrate(int deviceId, long[] pattern, int repeat, IBinder token) throws RemoteException {
        }

        public void cancelVibrate(int deviceId, IBinder token) throws RemoteException {
        }

        public void setPointerIconType(int typeId) throws RemoteException {
        }

        public void setCustomPointerIcon(PointerIcon icon) throws RemoteException {
        }

        public void requestPointerCapture(IBinder windowToken, boolean enabled) throws RemoteException {
        }

        public InputMonitor monitorGestureInput(String name, int displayId) throws RemoteException {
            return null;
        }

        public IBinder asBinder() {
            return null;
        }
    }

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
            if (iin == null || !(iin instanceof IInputManager)) {
                return new Proxy(obj);
            }
            return (IInputManager) iin;
        }

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

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v0, resolved type: android.hardware.input.TouchCalibration} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v4, resolved type: android.hardware.input.TouchCalibration} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v9, resolved type: android.hardware.input.TouchCalibration} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v13, resolved type: android.hardware.input.TouchCalibration} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v15, resolved type: android.hardware.input.InputDeviceIdentifier} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v18, resolved type: android.hardware.input.TouchCalibration} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v19, resolved type: android.hardware.input.InputDeviceIdentifier} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v22, resolved type: android.hardware.input.TouchCalibration} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v23, resolved type: android.hardware.input.InputDeviceIdentifier} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v26, resolved type: android.hardware.input.TouchCalibration} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v27, resolved type: android.hardware.input.InputDeviceIdentifier} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v30, resolved type: android.hardware.input.TouchCalibration} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v31, resolved type: android.hardware.input.InputDeviceIdentifier} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v34, resolved type: android.hardware.input.TouchCalibration} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v35, resolved type: android.hardware.input.InputDeviceIdentifier} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v40, resolved type: android.hardware.input.TouchCalibration} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v47, resolved type: android.view.PointerIcon} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v45, resolved type: android.hardware.input.TouchCalibration} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v46, resolved type: android.hardware.input.TouchCalibration} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v47, resolved type: android.hardware.input.TouchCalibration} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v48, resolved type: android.hardware.input.TouchCalibration} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v49, resolved type: android.hardware.input.TouchCalibration} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v50, resolved type: android.hardware.input.TouchCalibration} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v51, resolved type: android.hardware.input.TouchCalibration} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v52, resolved type: android.hardware.input.TouchCalibration} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v53, resolved type: android.hardware.input.TouchCalibration} */
        /* JADX WARNING: type inference failed for: r1v9, types: [android.view.InputEvent] */
        /* JADX WARNING: type inference failed for: r3v7, types: [android.view.InputEvent] */
        /* JADX WARNING: type inference failed for: r3v16, types: [android.hardware.input.InputDeviceIdentifier] */
        /* JADX WARNING: type inference failed for: r3v21, types: [android.hardware.input.InputDeviceIdentifier] */
        /* JADX WARNING: type inference failed for: r3v25, types: [android.hardware.input.InputDeviceIdentifier] */
        /* JADX WARNING: type inference failed for: r3v29, types: [android.hardware.input.InputDeviceIdentifier] */
        /* JADX WARNING: type inference failed for: r3v33, types: [android.hardware.input.InputDeviceIdentifier] */
        /* JADX WARNING: type inference failed for: r3v37, types: [android.hardware.input.InputDeviceIdentifier] */
        /* JADX WARNING: type inference failed for: r3v42, types: [android.view.PointerIcon] */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r9, android.os.Parcel r10, android.os.Parcel r11, int r12) throws android.os.RemoteException {
            /*
                r8 = this;
                java.lang.String r0 = "android.hardware.input.IInputManager"
                r1 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r2 = 1
                if (r9 == r1) goto L_0x02a9
                r1 = 0
                r3 = 0
                switch(r9) {
                    case 1: goto L_0x028e;
                    case 2: goto L_0x0280;
                    case 3: goto L_0x026e;
                    case 4: goto L_0x0260;
                    case 5: goto L_0x0252;
                    case 6: goto L_0x022b;
                    case 7: goto L_0x021d;
                    case 8: goto L_0x01f9;
                    case 9: goto L_0x01da;
                    case 10: goto L_0x01b8;
                    case 11: goto L_0x01aa;
                    case 12: goto L_0x018a;
                    case 13: goto L_0x016f;
                    case 14: goto L_0x014f;
                    case 15: goto L_0x012f;
                    case 16: goto L_0x010f;
                    case 17: goto L_0x00ef;
                    case 18: goto L_0x00cf;
                    case 19: goto L_0x00bd;
                    case 20: goto L_0x00af;
                    case 21: goto L_0x009d;
                    case 22: goto L_0x0083;
                    case 23: goto L_0x0071;
                    case 24: goto L_0x0063;
                    case 25: goto L_0x0047;
                    case 26: goto L_0x0031;
                    case 27: goto L_0x0012;
                    default: goto L_0x000d;
                }
            L_0x000d:
                boolean r1 = super.onTransact(r9, r10, r11, r12)
                return r1
            L_0x0012:
                r10.enforceInterface(r0)
                java.lang.String r3 = r10.readString()
                int r4 = r10.readInt()
                android.view.InputMonitor r5 = r8.monitorGestureInput(r3, r4)
                r11.writeNoException()
                if (r5 == 0) goto L_0x002d
                r11.writeInt(r2)
                r5.writeToParcel(r11, r2)
                goto L_0x0030
            L_0x002d:
                r11.writeInt(r1)
            L_0x0030:
                return r2
            L_0x0031:
                r10.enforceInterface(r0)
                android.os.IBinder r3 = r10.readStrongBinder()
                int r4 = r10.readInt()
                if (r4 == 0) goto L_0x0040
                r1 = r2
            L_0x0040:
                r8.requestPointerCapture(r3, r1)
                r11.writeNoException()
                return r2
            L_0x0047:
                r10.enforceInterface(r0)
                int r1 = r10.readInt()
                if (r1 == 0) goto L_0x005a
                android.os.Parcelable$Creator<android.view.PointerIcon> r1 = android.view.PointerIcon.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r10)
                r3 = r1
                android.view.PointerIcon r3 = (android.view.PointerIcon) r3
                goto L_0x005b
            L_0x005a:
            L_0x005b:
                r1 = r3
                r8.setCustomPointerIcon(r1)
                r11.writeNoException()
                return r2
            L_0x0063:
                r10.enforceInterface(r0)
                int r1 = r10.readInt()
                r8.setPointerIconType(r1)
                r11.writeNoException()
                return r2
            L_0x0071:
                r10.enforceInterface(r0)
                int r1 = r10.readInt()
                android.os.IBinder r3 = r10.readStrongBinder()
                r8.cancelVibrate(r1, r3)
                r11.writeNoException()
                return r2
            L_0x0083:
                r10.enforceInterface(r0)
                int r1 = r10.readInt()
                long[] r3 = r10.createLongArray()
                int r4 = r10.readInt()
                android.os.IBinder r5 = r10.readStrongBinder()
                r8.vibrate(r1, r3, r4, r5)
                r11.writeNoException()
                return r2
            L_0x009d:
                r10.enforceInterface(r0)
                android.os.IBinder r1 = r10.readStrongBinder()
                android.hardware.input.ITabletModeChangedListener r1 = android.hardware.input.ITabletModeChangedListener.Stub.asInterface(r1)
                r8.registerTabletModeChangedListener(r1)
                r11.writeNoException()
                return r2
            L_0x00af:
                r10.enforceInterface(r0)
                int r1 = r8.isInTabletMode()
                r11.writeNoException()
                r11.writeInt(r1)
                return r2
            L_0x00bd:
                r10.enforceInterface(r0)
                android.os.IBinder r1 = r10.readStrongBinder()
                android.hardware.input.IInputDevicesChangedListener r1 = android.hardware.input.IInputDevicesChangedListener.Stub.asInterface(r1)
                r8.registerInputDevicesChangedListener(r1)
                r11.writeNoException()
                return r2
            L_0x00cf:
                r10.enforceInterface(r0)
                int r1 = r10.readInt()
                if (r1 == 0) goto L_0x00e2
                android.os.Parcelable$Creator<android.hardware.input.InputDeviceIdentifier> r1 = android.hardware.input.InputDeviceIdentifier.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r10)
                r3 = r1
                android.hardware.input.InputDeviceIdentifier r3 = (android.hardware.input.InputDeviceIdentifier) r3
                goto L_0x00e3
            L_0x00e2:
            L_0x00e3:
                r1 = r3
                java.lang.String r3 = r10.readString()
                r8.removeKeyboardLayoutForInputDevice(r1, r3)
                r11.writeNoException()
                return r2
            L_0x00ef:
                r10.enforceInterface(r0)
                int r1 = r10.readInt()
                if (r1 == 0) goto L_0x0102
                android.os.Parcelable$Creator<android.hardware.input.InputDeviceIdentifier> r1 = android.hardware.input.InputDeviceIdentifier.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r10)
                r3 = r1
                android.hardware.input.InputDeviceIdentifier r3 = (android.hardware.input.InputDeviceIdentifier) r3
                goto L_0x0103
            L_0x0102:
            L_0x0103:
                r1 = r3
                java.lang.String r3 = r10.readString()
                r8.addKeyboardLayoutForInputDevice(r1, r3)
                r11.writeNoException()
                return r2
            L_0x010f:
                r10.enforceInterface(r0)
                int r1 = r10.readInt()
                if (r1 == 0) goto L_0x0122
                android.os.Parcelable$Creator<android.hardware.input.InputDeviceIdentifier> r1 = android.hardware.input.InputDeviceIdentifier.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r10)
                r3 = r1
                android.hardware.input.InputDeviceIdentifier r3 = (android.hardware.input.InputDeviceIdentifier) r3
                goto L_0x0123
            L_0x0122:
            L_0x0123:
                r1 = r3
                java.lang.String[] r3 = r8.getEnabledKeyboardLayoutsForInputDevice(r1)
                r11.writeNoException()
                r11.writeStringArray(r3)
                return r2
            L_0x012f:
                r10.enforceInterface(r0)
                int r1 = r10.readInt()
                if (r1 == 0) goto L_0x0142
                android.os.Parcelable$Creator<android.hardware.input.InputDeviceIdentifier> r1 = android.hardware.input.InputDeviceIdentifier.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r10)
                r3 = r1
                android.hardware.input.InputDeviceIdentifier r3 = (android.hardware.input.InputDeviceIdentifier) r3
                goto L_0x0143
            L_0x0142:
            L_0x0143:
                r1 = r3
                java.lang.String r3 = r10.readString()
                r8.setCurrentKeyboardLayoutForInputDevice(r1, r3)
                r11.writeNoException()
                return r2
            L_0x014f:
                r10.enforceInterface(r0)
                int r1 = r10.readInt()
                if (r1 == 0) goto L_0x0162
                android.os.Parcelable$Creator<android.hardware.input.InputDeviceIdentifier> r1 = android.hardware.input.InputDeviceIdentifier.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r10)
                r3 = r1
                android.hardware.input.InputDeviceIdentifier r3 = (android.hardware.input.InputDeviceIdentifier) r3
                goto L_0x0163
            L_0x0162:
            L_0x0163:
                r1 = r3
                java.lang.String r3 = r8.getCurrentKeyboardLayoutForInputDevice(r1)
                r11.writeNoException()
                r11.writeString(r3)
                return r2
            L_0x016f:
                r10.enforceInterface(r0)
                java.lang.String r3 = r10.readString()
                android.hardware.input.KeyboardLayout r4 = r8.getKeyboardLayout(r3)
                r11.writeNoException()
                if (r4 == 0) goto L_0x0186
                r11.writeInt(r2)
                r4.writeToParcel(r11, r2)
                goto L_0x0189
            L_0x0186:
                r11.writeInt(r1)
            L_0x0189:
                return r2
            L_0x018a:
                r10.enforceInterface(r0)
                int r1 = r10.readInt()
                if (r1 == 0) goto L_0x019d
                android.os.Parcelable$Creator<android.hardware.input.InputDeviceIdentifier> r1 = android.hardware.input.InputDeviceIdentifier.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r10)
                r3 = r1
                android.hardware.input.InputDeviceIdentifier r3 = (android.hardware.input.InputDeviceIdentifier) r3
                goto L_0x019e
            L_0x019d:
            L_0x019e:
                r1 = r3
                android.hardware.input.KeyboardLayout[] r3 = r8.getKeyboardLayoutsForInputDevice(r1)
                r11.writeNoException()
                r11.writeTypedArray(r3, r2)
                return r2
            L_0x01aa:
                r10.enforceInterface(r0)
                android.hardware.input.KeyboardLayout[] r1 = r8.getKeyboardLayouts()
                r11.writeNoException()
                r11.writeTypedArray(r1, r2)
                return r2
            L_0x01b8:
                r10.enforceInterface(r0)
                java.lang.String r1 = r10.readString()
                int r4 = r10.readInt()
                int r5 = r10.readInt()
                if (r5 == 0) goto L_0x01d2
                android.os.Parcelable$Creator<android.hardware.input.TouchCalibration> r3 = android.hardware.input.TouchCalibration.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r10)
                android.hardware.input.TouchCalibration r3 = (android.hardware.input.TouchCalibration) r3
                goto L_0x01d3
            L_0x01d2:
            L_0x01d3:
                r8.setTouchCalibrationForInputDevice(r1, r4, r3)
                r11.writeNoException()
                return r2
            L_0x01da:
                r10.enforceInterface(r0)
                java.lang.String r3 = r10.readString()
                int r4 = r10.readInt()
                android.hardware.input.TouchCalibration r5 = r8.getTouchCalibrationForInputDevice(r3, r4)
                r11.writeNoException()
                if (r5 == 0) goto L_0x01f5
                r11.writeInt(r2)
                r5.writeToParcel(r11, r2)
                goto L_0x01f8
            L_0x01f5:
                r11.writeInt(r1)
            L_0x01f8:
                return r2
            L_0x01f9:
                r10.enforceInterface(r0)
                int r1 = r10.readInt()
                if (r1 == 0) goto L_0x020c
                android.os.Parcelable$Creator<android.view.InputEvent> r1 = android.view.InputEvent.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r10)
                r3 = r1
                android.view.InputEvent r3 = (android.view.InputEvent) r3
                goto L_0x020d
            L_0x020c:
            L_0x020d:
                r1 = r3
                int r3 = r10.readInt()
                boolean r4 = r8.injectInputEvent(r1, r3)
                r11.writeNoException()
                r11.writeInt(r4)
                return r2
            L_0x021d:
                r10.enforceInterface(r0)
                int r1 = r10.readInt()
                r8.tryPointerSpeed(r1)
                r11.writeNoException()
                return r2
            L_0x022b:
                r10.enforceInterface(r0)
                int r1 = r10.readInt()
                int r3 = r10.readInt()
                int[] r4 = r10.createIntArray()
                int r5 = r10.readInt()
                if (r5 >= 0) goto L_0x0242
                r6 = 0
                goto L_0x0244
            L_0x0242:
                boolean[] r6 = new boolean[r5]
            L_0x0244:
                boolean r7 = r8.hasKeys(r1, r3, r4, r6)
                r11.writeNoException()
                r11.writeInt(r7)
                r11.writeBooleanArray(r6)
                return r2
            L_0x0252:
                r10.enforceInterface(r0)
                int r1 = r10.readInt()
                r8.disableInputDevice(r1)
                r11.writeNoException()
                return r2
            L_0x0260:
                r10.enforceInterface(r0)
                int r1 = r10.readInt()
                r8.enableInputDevice(r1)
                r11.writeNoException()
                return r2
            L_0x026e:
                r10.enforceInterface(r0)
                int r1 = r10.readInt()
                boolean r3 = r8.isInputDeviceEnabled(r1)
                r11.writeNoException()
                r11.writeInt(r3)
                return r2
            L_0x0280:
                r10.enforceInterface(r0)
                int[] r1 = r8.getInputDeviceIds()
                r11.writeNoException()
                r11.writeIntArray(r1)
                return r2
            L_0x028e:
                r10.enforceInterface(r0)
                int r3 = r10.readInt()
                android.view.InputDevice r4 = r8.getInputDevice(r3)
                r11.writeNoException()
                if (r4 == 0) goto L_0x02a5
                r11.writeInt(r2)
                r4.writeToParcel(r11, r2)
                goto L_0x02a8
            L_0x02a5:
                r11.writeInt(r1)
            L_0x02a8:
                return r2
            L_0x02a9:
                r11.writeString(r0)
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: android.hardware.input.IInputManager.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IInputManager {
            public static IInputManager sDefaultImpl;
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

            public InputDevice getInputDevice(int deviceId) throws RemoteException {
                InputDevice _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(deviceId);
                    if (!this.mRemote.transact(1, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getInputDevice(deviceId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = InputDevice.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    InputDevice _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int[] getInputDeviceIds() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(2, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getInputDeviceIds();
                    }
                    _reply.readException();
                    int[] _result = _reply.createIntArray();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isInputDeviceEnabled(int deviceId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(deviceId);
                    boolean z = false;
                    if (!this.mRemote.transact(3, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isInputDeviceEnabled(deviceId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _result = z;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void enableInputDevice(int deviceId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(deviceId);
                    if (this.mRemote.transact(4, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().enableInputDevice(deviceId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void disableInputDevice(int deviceId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(deviceId);
                    if (this.mRemote.transact(5, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().disableInputDevice(deviceId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

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
                    boolean z = false;
                    if (!this.mRemote.transact(6, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().hasKeys(deviceId, sourceMask, keyCodes, keyExists);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _result = z;
                    _reply.readBooleanArray(keyExists);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void tryPointerSpeed(int speed) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(speed);
                    if (this.mRemote.transact(7, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().tryPointerSpeed(speed);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean injectInputEvent(InputEvent ev, int mode) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (ev != null) {
                        _data.writeInt(1);
                        ev.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(mode);
                    if (!this.mRemote.transact(8, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().injectInputEvent(ev, mode);
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

            public TouchCalibration getTouchCalibrationForInputDevice(String inputDeviceDescriptor, int rotation) throws RemoteException {
                TouchCalibration _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(inputDeviceDescriptor);
                    _data.writeInt(rotation);
                    if (!this.mRemote.transact(9, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getTouchCalibrationForInputDevice(inputDeviceDescriptor, rotation);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = TouchCalibration.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    TouchCalibration _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

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
                    if (this.mRemote.transact(10, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setTouchCalibrationForInputDevice(inputDeviceDescriptor, rotation, calibration);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public KeyboardLayout[] getKeyboardLayouts() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(11, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getKeyboardLayouts();
                    }
                    _reply.readException();
                    KeyboardLayout[] _result = (KeyboardLayout[]) _reply.createTypedArray(KeyboardLayout.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

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
                    if (!this.mRemote.transact(12, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getKeyboardLayoutsForInputDevice(identifier);
                    }
                    _reply.readException();
                    KeyboardLayout[] _result = (KeyboardLayout[]) _reply.createTypedArray(KeyboardLayout.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public KeyboardLayout getKeyboardLayout(String keyboardLayoutDescriptor) throws RemoteException {
                KeyboardLayout _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(keyboardLayoutDescriptor);
                    if (!this.mRemote.transact(13, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getKeyboardLayout(keyboardLayoutDescriptor);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = KeyboardLayout.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    KeyboardLayout _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

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
                    if (!this.mRemote.transact(14, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getCurrentKeyboardLayoutForInputDevice(identifier);
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
                    if (this.mRemote.transact(15, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setCurrentKeyboardLayoutForInputDevice(identifier, keyboardLayoutDescriptor);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

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
                    if (!this.mRemote.transact(16, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getEnabledKeyboardLayoutsForInputDevice(identifier);
                    }
                    _reply.readException();
                    String[] _result = _reply.createStringArray();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

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
                    if (this.mRemote.transact(17, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().addKeyboardLayoutForInputDevice(identifier, keyboardLayoutDescriptor);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

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
                    if (this.mRemote.transact(18, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().removeKeyboardLayoutForInputDevice(identifier, keyboardLayoutDescriptor);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void registerInputDevicesChangedListener(IInputDevicesChangedListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    if (this.mRemote.transact(19, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().registerInputDevicesChangedListener(listener);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int isInTabletMode() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(20, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isInTabletMode();
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

            public void registerTabletModeChangedListener(ITabletModeChangedListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    if (this.mRemote.transact(21, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().registerTabletModeChangedListener(listener);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void vibrate(int deviceId, long[] pattern, int repeat, IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(deviceId);
                    _data.writeLongArray(pattern);
                    _data.writeInt(repeat);
                    _data.writeStrongBinder(token);
                    if (this.mRemote.transact(22, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().vibrate(deviceId, pattern, repeat, token);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void cancelVibrate(int deviceId, IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(deviceId);
                    _data.writeStrongBinder(token);
                    if (this.mRemote.transact(23, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().cancelVibrate(deviceId, token);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setPointerIconType(int typeId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(typeId);
                    if (this.mRemote.transact(24, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setPointerIconType(typeId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

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
                    if (this.mRemote.transact(25, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setCustomPointerIcon(icon);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void requestPointerCapture(IBinder windowToken, boolean enabled) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(windowToken);
                    _data.writeInt(enabled);
                    if (this.mRemote.transact(26, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().requestPointerCapture(windowToken, enabled);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public InputMonitor monitorGestureInput(String name, int displayId) throws RemoteException {
                InputMonitor _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    _data.writeInt(displayId);
                    if (!this.mRemote.transact(27, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().monitorGestureInput(name, displayId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = InputMonitor.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    InputMonitor _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IInputManager impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IInputManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
