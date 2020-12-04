package android.hardware.usb;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import java.util.List;

public interface IUsbManager extends IInterface {
    void clearDefaults(String str, int i) throws RemoteException;

    void enableContaminantDetection(String str, boolean z) throws RemoteException;

    ParcelFileDescriptor getControlFd(long j) throws RemoteException;

    UsbAccessory getCurrentAccessory() throws RemoteException;

    long getCurrentFunctions() throws RemoteException;

    void getDeviceList(Bundle bundle) throws RemoteException;

    UsbPortStatus getPortStatus(String str) throws RemoteException;

    List<ParcelableUsbPort> getPorts() throws RemoteException;

    long getScreenUnlockedFunctions() throws RemoteException;

    void grantAccessoryPermission(UsbAccessory usbAccessory, int i) throws RemoteException;

    void grantDevicePermission(UsbDevice usbDevice, int i) throws RemoteException;

    boolean hasAccessoryPermission(UsbAccessory usbAccessory) throws RemoteException;

    boolean hasDefaults(String str, int i) throws RemoteException;

    boolean hasDevicePermission(UsbDevice usbDevice, String str) throws RemoteException;

    boolean isFunctionEnabled(String str) throws RemoteException;

    ParcelFileDescriptor openAccessory(UsbAccessory usbAccessory) throws RemoteException;

    ParcelFileDescriptor openDevice(String str, String str2) throws RemoteException;

    void requestAccessoryPermission(UsbAccessory usbAccessory, String str, PendingIntent pendingIntent) throws RemoteException;

    void requestDevicePermission(UsbDevice usbDevice, String str, PendingIntent pendingIntent) throws RemoteException;

    void setAccessoryPackage(UsbAccessory usbAccessory, String str, int i) throws RemoteException;

    void setCurrentFunction(String str, boolean z) throws RemoteException;

    void setCurrentFunctions(long j) throws RemoteException;

    void setDevicePackage(UsbDevice usbDevice, String str, int i) throws RemoteException;

    void setPortRoles(String str, int i, int i2) throws RemoteException;

    void setScreenUnlockedFunctions(long j) throws RemoteException;

    void setUsbDeviceConnectionHandler(ComponentName componentName) throws RemoteException;

    public static class Default implements IUsbManager {
        public void getDeviceList(Bundle devices) throws RemoteException {
        }

        public ParcelFileDescriptor openDevice(String deviceName, String packageName) throws RemoteException {
            return null;
        }

        public UsbAccessory getCurrentAccessory() throws RemoteException {
            return null;
        }

        public ParcelFileDescriptor openAccessory(UsbAccessory accessory) throws RemoteException {
            return null;
        }

        public void setDevicePackage(UsbDevice device, String packageName, int userId) throws RemoteException {
        }

        public void setAccessoryPackage(UsbAccessory accessory, String packageName, int userId) throws RemoteException {
        }

        public boolean hasDevicePermission(UsbDevice device, String packageName) throws RemoteException {
            return false;
        }

        public boolean hasAccessoryPermission(UsbAccessory accessory) throws RemoteException {
            return false;
        }

        public void requestDevicePermission(UsbDevice device, String packageName, PendingIntent pi) throws RemoteException {
        }

        public void requestAccessoryPermission(UsbAccessory accessory, String packageName, PendingIntent pi) throws RemoteException {
        }

        public void grantDevicePermission(UsbDevice device, int uid) throws RemoteException {
        }

        public void grantAccessoryPermission(UsbAccessory accessory, int uid) throws RemoteException {
        }

        public boolean hasDefaults(String packageName, int userId) throws RemoteException {
            return false;
        }

        public void clearDefaults(String packageName, int userId) throws RemoteException {
        }

        public boolean isFunctionEnabled(String function) throws RemoteException {
            return false;
        }

        public void setCurrentFunctions(long functions) throws RemoteException {
        }

        public void setCurrentFunction(String function, boolean usbDataUnlocked) throws RemoteException {
        }

        public long getCurrentFunctions() throws RemoteException {
            return 0;
        }

        public void setScreenUnlockedFunctions(long functions) throws RemoteException {
        }

        public long getScreenUnlockedFunctions() throws RemoteException {
            return 0;
        }

        public ParcelFileDescriptor getControlFd(long function) throws RemoteException {
            return null;
        }

        public List<ParcelableUsbPort> getPorts() throws RemoteException {
            return null;
        }

        public UsbPortStatus getPortStatus(String portId) throws RemoteException {
            return null;
        }

        public void setPortRoles(String portId, int powerRole, int dataRole) throws RemoteException {
        }

        public void enableContaminantDetection(String portId, boolean enable) throws RemoteException {
        }

        public void setUsbDeviceConnectionHandler(ComponentName usbDeviceConnectionHandler) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IUsbManager {
        private static final String DESCRIPTOR = "android.hardware.usb.IUsbManager";
        static final int TRANSACTION_clearDefaults = 14;
        static final int TRANSACTION_enableContaminantDetection = 25;
        static final int TRANSACTION_getControlFd = 21;
        static final int TRANSACTION_getCurrentAccessory = 3;
        static final int TRANSACTION_getCurrentFunctions = 18;
        static final int TRANSACTION_getDeviceList = 1;
        static final int TRANSACTION_getPortStatus = 23;
        static final int TRANSACTION_getPorts = 22;
        static final int TRANSACTION_getScreenUnlockedFunctions = 20;
        static final int TRANSACTION_grantAccessoryPermission = 12;
        static final int TRANSACTION_grantDevicePermission = 11;
        static final int TRANSACTION_hasAccessoryPermission = 8;
        static final int TRANSACTION_hasDefaults = 13;
        static final int TRANSACTION_hasDevicePermission = 7;
        static final int TRANSACTION_isFunctionEnabled = 15;
        static final int TRANSACTION_openAccessory = 4;
        static final int TRANSACTION_openDevice = 2;
        static final int TRANSACTION_requestAccessoryPermission = 10;
        static final int TRANSACTION_requestDevicePermission = 9;
        static final int TRANSACTION_setAccessoryPackage = 6;
        static final int TRANSACTION_setCurrentFunction = 17;
        static final int TRANSACTION_setCurrentFunctions = 16;
        static final int TRANSACTION_setDevicePackage = 5;
        static final int TRANSACTION_setPortRoles = 24;
        static final int TRANSACTION_setScreenUnlockedFunctions = 19;
        static final int TRANSACTION_setUsbDeviceConnectionHandler = 26;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IUsbManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IUsbManager)) {
                return new Proxy(obj);
            }
            return (IUsbManager) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "getDeviceList";
                case 2:
                    return "openDevice";
                case 3:
                    return "getCurrentAccessory";
                case 4:
                    return "openAccessory";
                case 5:
                    return "setDevicePackage";
                case 6:
                    return "setAccessoryPackage";
                case 7:
                    return "hasDevicePermission";
                case 8:
                    return "hasAccessoryPermission";
                case 9:
                    return "requestDevicePermission";
                case 10:
                    return "requestAccessoryPermission";
                case 11:
                    return "grantDevicePermission";
                case 12:
                    return "grantAccessoryPermission";
                case 13:
                    return "hasDefaults";
                case 14:
                    return "clearDefaults";
                case 15:
                    return "isFunctionEnabled";
                case 16:
                    return "setCurrentFunctions";
                case 17:
                    return "setCurrentFunction";
                case 18:
                    return "getCurrentFunctions";
                case 19:
                    return "setScreenUnlockedFunctions";
                case 20:
                    return "getScreenUnlockedFunctions";
                case 21:
                    return "getControlFd";
                case 22:
                    return "getPorts";
                case 23:
                    return "getPortStatus";
                case 24:
                    return "setPortRoles";
                case 25:
                    return "enableContaminantDetection";
                case 26:
                    return "setUsbDeviceConnectionHandler";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v3, resolved type: android.hardware.usb.UsbAccessory} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v23, resolved type: android.app.PendingIntent} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v27, resolved type: android.app.PendingIntent} */
        /* JADX WARNING: type inference failed for: r3v0 */
        /* JADX WARNING: type inference failed for: r3v7 */
        /* JADX WARNING: type inference failed for: r3v11 */
        /* JADX WARNING: type inference failed for: r3v15 */
        /* JADX WARNING: type inference failed for: r3v19 */
        /* JADX WARNING: type inference failed for: r3v31 */
        /* JADX WARNING: type inference failed for: r3v35 */
        /* JADX WARNING: type inference failed for: r3v51 */
        /* JADX WARNING: type inference failed for: r3v54 */
        /* JADX WARNING: type inference failed for: r3v55 */
        /* JADX WARNING: type inference failed for: r3v56 */
        /* JADX WARNING: type inference failed for: r3v57 */
        /* JADX WARNING: type inference failed for: r3v58 */
        /* JADX WARNING: type inference failed for: r3v59 */
        /* JADX WARNING: type inference failed for: r3v60 */
        /* JADX WARNING: type inference failed for: r3v61 */
        /* JADX WARNING: type inference failed for: r3v62 */
        /* JADX WARNING: type inference failed for: r3v63 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r7, android.os.Parcel r8, android.os.Parcel r9, int r10) throws android.os.RemoteException {
            /*
                r6 = this;
                java.lang.String r0 = "android.hardware.usb.IUsbManager"
                r1 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r2 = 1
                if (r7 == r1) goto L_0x02c1
                r1 = 0
                r3 = 0
                switch(r7) {
                    case 1: goto L_0x02ab;
                    case 2: goto L_0x028c;
                    case 3: goto L_0x0275;
                    case 4: goto L_0x024e;
                    case 5: goto L_0x022a;
                    case 6: goto L_0x0206;
                    case 7: goto L_0x01e2;
                    case 8: goto L_0x01c2;
                    case 9: goto L_0x0194;
                    case 10: goto L_0x0166;
                    case 11: goto L_0x0146;
                    case 12: goto L_0x0126;
                    case 13: goto L_0x0110;
                    case 14: goto L_0x00fe;
                    case 15: goto L_0x00ec;
                    case 16: goto L_0x00de;
                    case 17: goto L_0x00c8;
                    case 18: goto L_0x00ba;
                    case 19: goto L_0x00ac;
                    case 20: goto L_0x009e;
                    case 21: goto L_0x0083;
                    case 22: goto L_0x0075;
                    case 23: goto L_0x005a;
                    case 24: goto L_0x0044;
                    case 25: goto L_0x002e;
                    case 26: goto L_0x0012;
                    default: goto L_0x000d;
                }
            L_0x000d:
                boolean r1 = super.onTransact(r7, r8, r9, r10)
                return r1
            L_0x0012:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                if (r1 == 0) goto L_0x0025
                android.os.Parcelable$Creator<android.content.ComponentName> r1 = android.content.ComponentName.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                r3 = r1
                android.content.ComponentName r3 = (android.content.ComponentName) r3
                goto L_0x0026
            L_0x0025:
            L_0x0026:
                r1 = r3
                r6.setUsbDeviceConnectionHandler(r1)
                r9.writeNoException()
                return r2
            L_0x002e:
                r8.enforceInterface(r0)
                java.lang.String r3 = r8.readString()
                int r4 = r8.readInt()
                if (r4 == 0) goto L_0x003d
                r1 = r2
            L_0x003d:
                r6.enableContaminantDetection(r3, r1)
                r9.writeNoException()
                return r2
            L_0x0044:
                r8.enforceInterface(r0)
                java.lang.String r1 = r8.readString()
                int r3 = r8.readInt()
                int r4 = r8.readInt()
                r6.setPortRoles(r1, r3, r4)
                r9.writeNoException()
                return r2
            L_0x005a:
                r8.enforceInterface(r0)
                java.lang.String r3 = r8.readString()
                android.hardware.usb.UsbPortStatus r4 = r6.getPortStatus(r3)
                r9.writeNoException()
                if (r4 == 0) goto L_0x0071
                r9.writeInt(r2)
                r4.writeToParcel(r9, r2)
                goto L_0x0074
            L_0x0071:
                r9.writeInt(r1)
            L_0x0074:
                return r2
            L_0x0075:
                r8.enforceInterface(r0)
                java.util.List r1 = r6.getPorts()
                r9.writeNoException()
                r9.writeTypedList(r1)
                return r2
            L_0x0083:
                r8.enforceInterface(r0)
                long r3 = r8.readLong()
                android.os.ParcelFileDescriptor r5 = r6.getControlFd(r3)
                r9.writeNoException()
                if (r5 == 0) goto L_0x009a
                r9.writeInt(r2)
                r5.writeToParcel(r9, r2)
                goto L_0x009d
            L_0x009a:
                r9.writeInt(r1)
            L_0x009d:
                return r2
            L_0x009e:
                r8.enforceInterface(r0)
                long r3 = r6.getScreenUnlockedFunctions()
                r9.writeNoException()
                r9.writeLong(r3)
                return r2
            L_0x00ac:
                r8.enforceInterface(r0)
                long r3 = r8.readLong()
                r6.setScreenUnlockedFunctions(r3)
                r9.writeNoException()
                return r2
            L_0x00ba:
                r8.enforceInterface(r0)
                long r3 = r6.getCurrentFunctions()
                r9.writeNoException()
                r9.writeLong(r3)
                return r2
            L_0x00c8:
                r8.enforceInterface(r0)
                java.lang.String r3 = r8.readString()
                int r4 = r8.readInt()
                if (r4 == 0) goto L_0x00d7
                r1 = r2
            L_0x00d7:
                r6.setCurrentFunction(r3, r1)
                r9.writeNoException()
                return r2
            L_0x00de:
                r8.enforceInterface(r0)
                long r3 = r8.readLong()
                r6.setCurrentFunctions(r3)
                r9.writeNoException()
                return r2
            L_0x00ec:
                r8.enforceInterface(r0)
                java.lang.String r1 = r8.readString()
                boolean r3 = r6.isFunctionEnabled(r1)
                r9.writeNoException()
                r9.writeInt(r3)
                return r2
            L_0x00fe:
                r8.enforceInterface(r0)
                java.lang.String r1 = r8.readString()
                int r3 = r8.readInt()
                r6.clearDefaults(r1, r3)
                r9.writeNoException()
                return r2
            L_0x0110:
                r8.enforceInterface(r0)
                java.lang.String r1 = r8.readString()
                int r3 = r8.readInt()
                boolean r4 = r6.hasDefaults(r1, r3)
                r9.writeNoException()
                r9.writeInt(r4)
                return r2
            L_0x0126:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                if (r1 == 0) goto L_0x0139
                android.os.Parcelable$Creator<android.hardware.usb.UsbAccessory> r1 = android.hardware.usb.UsbAccessory.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                r3 = r1
                android.hardware.usb.UsbAccessory r3 = (android.hardware.usb.UsbAccessory) r3
                goto L_0x013a
            L_0x0139:
            L_0x013a:
                r1 = r3
                int r3 = r8.readInt()
                r6.grantAccessoryPermission(r1, r3)
                r9.writeNoException()
                return r2
            L_0x0146:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                if (r1 == 0) goto L_0x0159
                android.os.Parcelable$Creator<android.hardware.usb.UsbDevice> r1 = android.hardware.usb.UsbDevice.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                r3 = r1
                android.hardware.usb.UsbDevice r3 = (android.hardware.usb.UsbDevice) r3
                goto L_0x015a
            L_0x0159:
            L_0x015a:
                r1 = r3
                int r3 = r8.readInt()
                r6.grantDevicePermission(r1, r3)
                r9.writeNoException()
                return r2
            L_0x0166:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                if (r1 == 0) goto L_0x0178
                android.os.Parcelable$Creator<android.hardware.usb.UsbAccessory> r1 = android.hardware.usb.UsbAccessory.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.hardware.usb.UsbAccessory r1 = (android.hardware.usb.UsbAccessory) r1
                goto L_0x0179
            L_0x0178:
                r1 = r3
            L_0x0179:
                java.lang.String r4 = r8.readString()
                int r5 = r8.readInt()
                if (r5 == 0) goto L_0x018c
                android.os.Parcelable$Creator<android.app.PendingIntent> r3 = android.app.PendingIntent.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r8)
                android.app.PendingIntent r3 = (android.app.PendingIntent) r3
                goto L_0x018d
            L_0x018c:
            L_0x018d:
                r6.requestAccessoryPermission(r1, r4, r3)
                r9.writeNoException()
                return r2
            L_0x0194:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                if (r1 == 0) goto L_0x01a6
                android.os.Parcelable$Creator<android.hardware.usb.UsbDevice> r1 = android.hardware.usb.UsbDevice.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.hardware.usb.UsbDevice r1 = (android.hardware.usb.UsbDevice) r1
                goto L_0x01a7
            L_0x01a6:
                r1 = r3
            L_0x01a7:
                java.lang.String r4 = r8.readString()
                int r5 = r8.readInt()
                if (r5 == 0) goto L_0x01ba
                android.os.Parcelable$Creator<android.app.PendingIntent> r3 = android.app.PendingIntent.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r8)
                android.app.PendingIntent r3 = (android.app.PendingIntent) r3
                goto L_0x01bb
            L_0x01ba:
            L_0x01bb:
                r6.requestDevicePermission(r1, r4, r3)
                r9.writeNoException()
                return r2
            L_0x01c2:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                if (r1 == 0) goto L_0x01d5
                android.os.Parcelable$Creator<android.hardware.usb.UsbAccessory> r1 = android.hardware.usb.UsbAccessory.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                r3 = r1
                android.hardware.usb.UsbAccessory r3 = (android.hardware.usb.UsbAccessory) r3
                goto L_0x01d6
            L_0x01d5:
            L_0x01d6:
                r1 = r3
                boolean r3 = r6.hasAccessoryPermission(r1)
                r9.writeNoException()
                r9.writeInt(r3)
                return r2
            L_0x01e2:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                if (r1 == 0) goto L_0x01f5
                android.os.Parcelable$Creator<android.hardware.usb.UsbDevice> r1 = android.hardware.usb.UsbDevice.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                r3 = r1
                android.hardware.usb.UsbDevice r3 = (android.hardware.usb.UsbDevice) r3
                goto L_0x01f6
            L_0x01f5:
            L_0x01f6:
                r1 = r3
                java.lang.String r3 = r8.readString()
                boolean r4 = r6.hasDevicePermission(r1, r3)
                r9.writeNoException()
                r9.writeInt(r4)
                return r2
            L_0x0206:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                if (r1 == 0) goto L_0x0219
                android.os.Parcelable$Creator<android.hardware.usb.UsbAccessory> r1 = android.hardware.usb.UsbAccessory.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                r3 = r1
                android.hardware.usb.UsbAccessory r3 = (android.hardware.usb.UsbAccessory) r3
                goto L_0x021a
            L_0x0219:
            L_0x021a:
                r1 = r3
                java.lang.String r3 = r8.readString()
                int r4 = r8.readInt()
                r6.setAccessoryPackage(r1, r3, r4)
                r9.writeNoException()
                return r2
            L_0x022a:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                if (r1 == 0) goto L_0x023d
                android.os.Parcelable$Creator<android.hardware.usb.UsbDevice> r1 = android.hardware.usb.UsbDevice.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                r3 = r1
                android.hardware.usb.UsbDevice r3 = (android.hardware.usb.UsbDevice) r3
                goto L_0x023e
            L_0x023d:
            L_0x023e:
                r1 = r3
                java.lang.String r3 = r8.readString()
                int r4 = r8.readInt()
                r6.setDevicePackage(r1, r3, r4)
                r9.writeNoException()
                return r2
            L_0x024e:
                r8.enforceInterface(r0)
                int r4 = r8.readInt()
                if (r4 == 0) goto L_0x0260
                android.os.Parcelable$Creator<android.hardware.usb.UsbAccessory> r3 = android.hardware.usb.UsbAccessory.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r8)
                android.hardware.usb.UsbAccessory r3 = (android.hardware.usb.UsbAccessory) r3
                goto L_0x0261
            L_0x0260:
            L_0x0261:
                android.os.ParcelFileDescriptor r4 = r6.openAccessory(r3)
                r9.writeNoException()
                if (r4 == 0) goto L_0x0271
                r9.writeInt(r2)
                r4.writeToParcel(r9, r2)
                goto L_0x0274
            L_0x0271:
                r9.writeInt(r1)
            L_0x0274:
                return r2
            L_0x0275:
                r8.enforceInterface(r0)
                android.hardware.usb.UsbAccessory r3 = r6.getCurrentAccessory()
                r9.writeNoException()
                if (r3 == 0) goto L_0x0288
                r9.writeInt(r2)
                r3.writeToParcel(r9, r2)
                goto L_0x028b
            L_0x0288:
                r9.writeInt(r1)
            L_0x028b:
                return r2
            L_0x028c:
                r8.enforceInterface(r0)
                java.lang.String r3 = r8.readString()
                java.lang.String r4 = r8.readString()
                android.os.ParcelFileDescriptor r5 = r6.openDevice(r3, r4)
                r9.writeNoException()
                if (r5 == 0) goto L_0x02a7
                r9.writeInt(r2)
                r5.writeToParcel(r9, r2)
                goto L_0x02aa
            L_0x02a7:
                r9.writeInt(r1)
            L_0x02aa:
                return r2
            L_0x02ab:
                r8.enforceInterface(r0)
                android.os.Bundle r1 = new android.os.Bundle
                r1.<init>()
                r6.getDeviceList(r1)
                r9.writeNoException()
                r9.writeInt(r2)
                r1.writeToParcel(r9, r2)
                return r2
            L_0x02c1:
                r9.writeString(r0)
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: android.hardware.usb.IUsbManager.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IUsbManager {
            public static IUsbManager sDefaultImpl;
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

            public void getDeviceList(Bundle devices) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(1, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        if (_reply.readInt() != 0) {
                            devices.readFromParcel(_reply);
                        }
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().getDeviceList(devices);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public ParcelFileDescriptor openDevice(String deviceName, String packageName) throws RemoteException {
                ParcelFileDescriptor _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(deviceName);
                    _data.writeString(packageName);
                    if (!this.mRemote.transact(2, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().openDevice(deviceName, packageName);
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

            public UsbAccessory getCurrentAccessory() throws RemoteException {
                UsbAccessory _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(3, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getCurrentAccessory();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = UsbAccessory.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    UsbAccessory _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public ParcelFileDescriptor openAccessory(UsbAccessory accessory) throws RemoteException {
                ParcelFileDescriptor _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (accessory != null) {
                        _data.writeInt(1);
                        accessory.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(4, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().openAccessory(accessory);
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

            public void setDevicePackage(UsbDevice device, String packageName, int userId) throws RemoteException {
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
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(5, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setDevicePackage(device, packageName, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setAccessoryPackage(UsbAccessory accessory, String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (accessory != null) {
                        _data.writeInt(1);
                        accessory.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(6, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setAccessoryPackage(accessory, packageName, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean hasDevicePermission(UsbDevice device, String packageName) throws RemoteException {
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
                    _data.writeString(packageName);
                    if (!this.mRemote.transact(7, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().hasDevicePermission(device, packageName);
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

            public boolean hasAccessoryPermission(UsbAccessory accessory) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (accessory != null) {
                        _data.writeInt(1);
                        accessory.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(8, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().hasAccessoryPermission(accessory);
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

            public void requestDevicePermission(UsbDevice device, String packageName, PendingIntent pi) throws RemoteException {
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
                    _data.writeString(packageName);
                    if (pi != null) {
                        _data.writeInt(1);
                        pi.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(9, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().requestDevicePermission(device, packageName, pi);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void requestAccessoryPermission(UsbAccessory accessory, String packageName, PendingIntent pi) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (accessory != null) {
                        _data.writeInt(1);
                        accessory.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(packageName);
                    if (pi != null) {
                        _data.writeInt(1);
                        pi.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(10, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().requestAccessoryPermission(accessory, packageName, pi);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void grantDevicePermission(UsbDevice device, int uid) throws RemoteException {
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
                    _data.writeInt(uid);
                    if (this.mRemote.transact(11, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().grantDevicePermission(device, uid);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void grantAccessoryPermission(UsbAccessory accessory, int uid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (accessory != null) {
                        _data.writeInt(1);
                        accessory.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(uid);
                    if (this.mRemote.transact(12, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().grantAccessoryPermission(accessory, uid);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean hasDefaults(String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    boolean z = false;
                    if (!this.mRemote.transact(13, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().hasDefaults(packageName, userId);
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

            public void clearDefaults(String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(14, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().clearDefaults(packageName, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isFunctionEnabled(String function) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(function);
                    boolean z = false;
                    if (!this.mRemote.transact(15, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isFunctionEnabled(function);
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

            public void setCurrentFunctions(long functions) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(functions);
                    if (this.mRemote.transact(16, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setCurrentFunctions(functions);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setCurrentFunction(String function, boolean usbDataUnlocked) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(function);
                    _data.writeInt(usbDataUnlocked);
                    if (this.mRemote.transact(17, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setCurrentFunction(function, usbDataUnlocked);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public long getCurrentFunctions() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(18, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getCurrentFunctions();
                    }
                    _reply.readException();
                    long _result = _reply.readLong();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setScreenUnlockedFunctions(long functions) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(functions);
                    if (this.mRemote.transact(19, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setScreenUnlockedFunctions(functions);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public long getScreenUnlockedFunctions() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(20, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getScreenUnlockedFunctions();
                    }
                    _reply.readException();
                    long _result = _reply.readLong();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public ParcelFileDescriptor getControlFd(long function) throws RemoteException {
                ParcelFileDescriptor _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(function);
                    if (!this.mRemote.transact(21, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getControlFd(function);
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

            public List<ParcelableUsbPort> getPorts() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(22, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPorts();
                    }
                    _reply.readException();
                    List<ParcelableUsbPort> _result = _reply.createTypedArrayList(ParcelableUsbPort.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public UsbPortStatus getPortStatus(String portId) throws RemoteException {
                UsbPortStatus _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(portId);
                    if (!this.mRemote.transact(23, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPortStatus(portId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = UsbPortStatus.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    UsbPortStatus _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setPortRoles(String portId, int powerRole, int dataRole) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(portId);
                    _data.writeInt(powerRole);
                    _data.writeInt(dataRole);
                    if (this.mRemote.transact(24, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setPortRoles(portId, powerRole, dataRole);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void enableContaminantDetection(String portId, boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(portId);
                    _data.writeInt(enable);
                    if (this.mRemote.transact(25, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().enableContaminantDetection(portId, enable);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setUsbDeviceConnectionHandler(ComponentName usbDeviceConnectionHandler) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (usbDeviceConnectionHandler != null) {
                        _data.writeInt(1);
                        usbDeviceConnectionHandler.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(26, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setUsbDeviceConnectionHandler(usbDeviceConnectionHandler);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IUsbManager impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IUsbManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
