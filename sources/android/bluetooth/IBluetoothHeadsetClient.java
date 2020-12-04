package android.bluetooth;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.List;

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

    public static class Default implements IBluetoothHeadsetClient {
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

        public boolean setPriority(BluetoothDevice device, int priority) throws RemoteException {
            return false;
        }

        public int getPriority(BluetoothDevice device) throws RemoteException {
            return 0;
        }

        public boolean startVoiceRecognition(BluetoothDevice device) throws RemoteException {
            return false;
        }

        public boolean stopVoiceRecognition(BluetoothDevice device) throws RemoteException {
            return false;
        }

        public List<BluetoothHeadsetClientCall> getCurrentCalls(BluetoothDevice device) throws RemoteException {
            return null;
        }

        public Bundle getCurrentAgEvents(BluetoothDevice device) throws RemoteException {
            return null;
        }

        public boolean acceptCall(BluetoothDevice device, int flag) throws RemoteException {
            return false;
        }

        public boolean holdCall(BluetoothDevice device) throws RemoteException {
            return false;
        }

        public boolean rejectCall(BluetoothDevice device) throws RemoteException {
            return false;
        }

        public boolean terminateCall(BluetoothDevice device, BluetoothHeadsetClientCall call) throws RemoteException {
            return false;
        }

        public boolean enterPrivateMode(BluetoothDevice device, int index) throws RemoteException {
            return false;
        }

        public boolean explicitCallTransfer(BluetoothDevice device) throws RemoteException {
            return false;
        }

        public BluetoothHeadsetClientCall dial(BluetoothDevice device, String number) throws RemoteException {
            return null;
        }

        public boolean sendDTMF(BluetoothDevice device, byte code) throws RemoteException {
            return false;
        }

        public boolean getLastVoiceTagNumber(BluetoothDevice device) throws RemoteException {
            return false;
        }

        public int getAudioState(BluetoothDevice device) throws RemoteException {
            return 0;
        }

        public boolean connectAudio(BluetoothDevice device) throws RemoteException {
            return false;
        }

        public boolean disconnectAudio(BluetoothDevice device) throws RemoteException {
            return false;
        }

        public void setAudioRouteAllowed(BluetoothDevice device, boolean allowed) throws RemoteException {
        }

        public boolean getAudioRouteAllowed(BluetoothDevice device) throws RemoteException {
            return false;
        }

        public Bundle getCurrentAgFeatures(BluetoothDevice device) throws RemoteException {
            return null;
        }

        public IBinder asBinder() {
            return null;
        }
    }

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
            if (iin == null || !(iin instanceof IBluetoothHeadsetClient)) {
                return new Proxy(obj);
            }
            return (IBluetoothHeadsetClient) iin;
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

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v34, resolved type: android.bluetooth.BluetoothDevice} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v50, resolved type: android.bluetooth.BluetoothHeadsetClientCall} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v62, resolved type: android.bluetooth.BluetoothDevice} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v86, resolved type: android.bluetooth.BluetoothDevice} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v94, resolved type: android.bluetooth.BluetoothDevice} */
        /* JADX WARNING: type inference failed for: r3v0 */
        /* JADX WARNING: type inference failed for: r3v1 */
        /* JADX WARNING: type inference failed for: r3v5 */
        /* JADX WARNING: type inference failed for: r3v10 */
        /* JADX WARNING: type inference failed for: r3v14 */
        /* JADX WARNING: type inference failed for: r3v18 */
        /* JADX WARNING: type inference failed for: r3v22 */
        /* JADX WARNING: type inference failed for: r3v26 */
        /* JADX WARNING: type inference failed for: r3v30 */
        /* JADX WARNING: type inference failed for: r3v38 */
        /* JADX WARNING: type inference failed for: r3v42 */
        /* JADX WARNING: type inference failed for: r3v46 */
        /* JADX WARNING: type inference failed for: r3v54 */
        /* JADX WARNING: type inference failed for: r3v58 */
        /* JADX WARNING: type inference failed for: r3v66 */
        /* JADX WARNING: type inference failed for: r3v70 */
        /* JADX WARNING: type inference failed for: r3v74 */
        /* JADX WARNING: type inference failed for: r3v78 */
        /* JADX WARNING: type inference failed for: r3v82 */
        /* JADX WARNING: type inference failed for: r3v90 */
        /* JADX WARNING: type inference failed for: r3v98 */
        /* JADX WARNING: type inference failed for: r3v99 */
        /* JADX WARNING: type inference failed for: r3v100 */
        /* JADX WARNING: type inference failed for: r3v101 */
        /* JADX WARNING: type inference failed for: r3v102 */
        /* JADX WARNING: type inference failed for: r3v103 */
        /* JADX WARNING: type inference failed for: r3v104 */
        /* JADX WARNING: type inference failed for: r3v105 */
        /* JADX WARNING: type inference failed for: r3v106 */
        /* JADX WARNING: type inference failed for: r3v107 */
        /* JADX WARNING: type inference failed for: r3v108 */
        /* JADX WARNING: type inference failed for: r3v109 */
        /* JADX WARNING: type inference failed for: r3v110 */
        /* JADX WARNING: type inference failed for: r3v111 */
        /* JADX WARNING: type inference failed for: r3v112 */
        /* JADX WARNING: type inference failed for: r3v113 */
        /* JADX WARNING: type inference failed for: r3v114 */
        /* JADX WARNING: type inference failed for: r3v115 */
        /* JADX WARNING: type inference failed for: r3v116 */
        /* JADX WARNING: type inference failed for: r3v117 */
        /* JADX WARNING: type inference failed for: r3v118 */
        /* JADX WARNING: type inference failed for: r3v119 */
        /* JADX WARNING: type inference failed for: r3v120 */
        /* JADX WARNING: type inference failed for: r3v121 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r7, android.os.Parcel r8, android.os.Parcel r9, int r10) throws android.os.RemoteException {
            /*
                r6 = this;
                java.lang.String r0 = "android.bluetooth.IBluetoothHeadsetClient"
                r1 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r2 = 1
                if (r7 == r1) goto L_0x036b
                r1 = 0
                r3 = 0
                switch(r7) {
                    case 1: goto L_0x034b;
                    case 2: goto L_0x032b;
                    case 3: goto L_0x031d;
                    case 4: goto L_0x030b;
                    case 5: goto L_0x02eb;
                    case 6: goto L_0x02c7;
                    case 7: goto L_0x02a7;
                    case 8: goto L_0x0287;
                    case 9: goto L_0x0267;
                    case 10: goto L_0x0247;
                    case 11: goto L_0x0220;
                    case 12: goto L_0x01fc;
                    case 13: goto L_0x01dc;
                    case 14: goto L_0x01bc;
                    case 15: goto L_0x018e;
                    case 16: goto L_0x016a;
                    case 17: goto L_0x014a;
                    case 18: goto L_0x011f;
                    case 19: goto L_0x00fb;
                    case 20: goto L_0x00db;
                    case 21: goto L_0x00bb;
                    case 22: goto L_0x009b;
                    case 23: goto L_0x007b;
                    case 24: goto L_0x0059;
                    case 25: goto L_0x0039;
                    case 26: goto L_0x0012;
                    default: goto L_0x000d;
                }
            L_0x000d:
                boolean r1 = super.onTransact(r7, r8, r9, r10)
                return r1
            L_0x0012:
                r8.enforceInterface(r0)
                int r4 = r8.readInt()
                if (r4 == 0) goto L_0x0024
                android.os.Parcelable$Creator<android.bluetooth.BluetoothDevice> r3 = android.bluetooth.BluetoothDevice.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r8)
                android.bluetooth.BluetoothDevice r3 = (android.bluetooth.BluetoothDevice) r3
                goto L_0x0025
            L_0x0024:
            L_0x0025:
                android.os.Bundle r4 = r6.getCurrentAgFeatures(r3)
                r9.writeNoException()
                if (r4 == 0) goto L_0x0035
                r9.writeInt(r2)
                r4.writeToParcel(r9, r2)
                goto L_0x0038
            L_0x0035:
                r9.writeInt(r1)
            L_0x0038:
                return r2
            L_0x0039:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                if (r1 == 0) goto L_0x004c
                android.os.Parcelable$Creator<android.bluetooth.BluetoothDevice> r1 = android.bluetooth.BluetoothDevice.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                r3 = r1
                android.bluetooth.BluetoothDevice r3 = (android.bluetooth.BluetoothDevice) r3
                goto L_0x004d
            L_0x004c:
            L_0x004d:
                r1 = r3
                boolean r3 = r6.getAudioRouteAllowed(r1)
                r9.writeNoException()
                r9.writeInt(r3)
                return r2
            L_0x0059:
                r8.enforceInterface(r0)
                int r4 = r8.readInt()
                if (r4 == 0) goto L_0x006b
                android.os.Parcelable$Creator<android.bluetooth.BluetoothDevice> r3 = android.bluetooth.BluetoothDevice.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r8)
                android.bluetooth.BluetoothDevice r3 = (android.bluetooth.BluetoothDevice) r3
                goto L_0x006c
            L_0x006b:
            L_0x006c:
                int r4 = r8.readInt()
                if (r4 == 0) goto L_0x0074
                r1 = r2
            L_0x0074:
                r6.setAudioRouteAllowed(r3, r1)
                r9.writeNoException()
                return r2
            L_0x007b:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                if (r1 == 0) goto L_0x008e
                android.os.Parcelable$Creator<android.bluetooth.BluetoothDevice> r1 = android.bluetooth.BluetoothDevice.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                r3 = r1
                android.bluetooth.BluetoothDevice r3 = (android.bluetooth.BluetoothDevice) r3
                goto L_0x008f
            L_0x008e:
            L_0x008f:
                r1 = r3
                boolean r3 = r6.disconnectAudio(r1)
                r9.writeNoException()
                r9.writeInt(r3)
                return r2
            L_0x009b:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                if (r1 == 0) goto L_0x00ae
                android.os.Parcelable$Creator<android.bluetooth.BluetoothDevice> r1 = android.bluetooth.BluetoothDevice.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                r3 = r1
                android.bluetooth.BluetoothDevice r3 = (android.bluetooth.BluetoothDevice) r3
                goto L_0x00af
            L_0x00ae:
            L_0x00af:
                r1 = r3
                boolean r3 = r6.connectAudio(r1)
                r9.writeNoException()
                r9.writeInt(r3)
                return r2
            L_0x00bb:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                if (r1 == 0) goto L_0x00ce
                android.os.Parcelable$Creator<android.bluetooth.BluetoothDevice> r1 = android.bluetooth.BluetoothDevice.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                r3 = r1
                android.bluetooth.BluetoothDevice r3 = (android.bluetooth.BluetoothDevice) r3
                goto L_0x00cf
            L_0x00ce:
            L_0x00cf:
                r1 = r3
                int r3 = r6.getAudioState(r1)
                r9.writeNoException()
                r9.writeInt(r3)
                return r2
            L_0x00db:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                if (r1 == 0) goto L_0x00ee
                android.os.Parcelable$Creator<android.bluetooth.BluetoothDevice> r1 = android.bluetooth.BluetoothDevice.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                r3 = r1
                android.bluetooth.BluetoothDevice r3 = (android.bluetooth.BluetoothDevice) r3
                goto L_0x00ef
            L_0x00ee:
            L_0x00ef:
                r1 = r3
                boolean r3 = r6.getLastVoiceTagNumber(r1)
                r9.writeNoException()
                r9.writeInt(r3)
                return r2
            L_0x00fb:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                if (r1 == 0) goto L_0x010e
                android.os.Parcelable$Creator<android.bluetooth.BluetoothDevice> r1 = android.bluetooth.BluetoothDevice.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                r3 = r1
                android.bluetooth.BluetoothDevice r3 = (android.bluetooth.BluetoothDevice) r3
                goto L_0x010f
            L_0x010e:
            L_0x010f:
                r1 = r3
                byte r3 = r8.readByte()
                boolean r4 = r6.sendDTMF(r1, r3)
                r9.writeNoException()
                r9.writeInt(r4)
                return r2
            L_0x011f:
                r8.enforceInterface(r0)
                int r4 = r8.readInt()
                if (r4 == 0) goto L_0x0131
                android.os.Parcelable$Creator<android.bluetooth.BluetoothDevice> r3 = android.bluetooth.BluetoothDevice.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r8)
                android.bluetooth.BluetoothDevice r3 = (android.bluetooth.BluetoothDevice) r3
                goto L_0x0132
            L_0x0131:
            L_0x0132:
                java.lang.String r4 = r8.readString()
                android.bluetooth.BluetoothHeadsetClientCall r5 = r6.dial(r3, r4)
                r9.writeNoException()
                if (r5 == 0) goto L_0x0146
                r9.writeInt(r2)
                r5.writeToParcel(r9, r2)
                goto L_0x0149
            L_0x0146:
                r9.writeInt(r1)
            L_0x0149:
                return r2
            L_0x014a:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                if (r1 == 0) goto L_0x015d
                android.os.Parcelable$Creator<android.bluetooth.BluetoothDevice> r1 = android.bluetooth.BluetoothDevice.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                r3 = r1
                android.bluetooth.BluetoothDevice r3 = (android.bluetooth.BluetoothDevice) r3
                goto L_0x015e
            L_0x015d:
            L_0x015e:
                r1 = r3
                boolean r3 = r6.explicitCallTransfer(r1)
                r9.writeNoException()
                r9.writeInt(r3)
                return r2
            L_0x016a:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                if (r1 == 0) goto L_0x017d
                android.os.Parcelable$Creator<android.bluetooth.BluetoothDevice> r1 = android.bluetooth.BluetoothDevice.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                r3 = r1
                android.bluetooth.BluetoothDevice r3 = (android.bluetooth.BluetoothDevice) r3
                goto L_0x017e
            L_0x017d:
            L_0x017e:
                r1 = r3
                int r3 = r8.readInt()
                boolean r4 = r6.enterPrivateMode(r1, r3)
                r9.writeNoException()
                r9.writeInt(r4)
                return r2
            L_0x018e:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                if (r1 == 0) goto L_0x01a0
                android.os.Parcelable$Creator<android.bluetooth.BluetoothDevice> r1 = android.bluetooth.BluetoothDevice.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.bluetooth.BluetoothDevice r1 = (android.bluetooth.BluetoothDevice) r1
                goto L_0x01a1
            L_0x01a0:
                r1 = r3
            L_0x01a1:
                int r4 = r8.readInt()
                if (r4 == 0) goto L_0x01b0
                android.os.Parcelable$Creator<android.bluetooth.BluetoothHeadsetClientCall> r3 = android.bluetooth.BluetoothHeadsetClientCall.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r8)
                android.bluetooth.BluetoothHeadsetClientCall r3 = (android.bluetooth.BluetoothHeadsetClientCall) r3
                goto L_0x01b1
            L_0x01b0:
            L_0x01b1:
                boolean r4 = r6.terminateCall(r1, r3)
                r9.writeNoException()
                r9.writeInt(r4)
                return r2
            L_0x01bc:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                if (r1 == 0) goto L_0x01cf
                android.os.Parcelable$Creator<android.bluetooth.BluetoothDevice> r1 = android.bluetooth.BluetoothDevice.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                r3 = r1
                android.bluetooth.BluetoothDevice r3 = (android.bluetooth.BluetoothDevice) r3
                goto L_0x01d0
            L_0x01cf:
            L_0x01d0:
                r1 = r3
                boolean r3 = r6.rejectCall(r1)
                r9.writeNoException()
                r9.writeInt(r3)
                return r2
            L_0x01dc:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                if (r1 == 0) goto L_0x01ef
                android.os.Parcelable$Creator<android.bluetooth.BluetoothDevice> r1 = android.bluetooth.BluetoothDevice.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                r3 = r1
                android.bluetooth.BluetoothDevice r3 = (android.bluetooth.BluetoothDevice) r3
                goto L_0x01f0
            L_0x01ef:
            L_0x01f0:
                r1 = r3
                boolean r3 = r6.holdCall(r1)
                r9.writeNoException()
                r9.writeInt(r3)
                return r2
            L_0x01fc:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                if (r1 == 0) goto L_0x020f
                android.os.Parcelable$Creator<android.bluetooth.BluetoothDevice> r1 = android.bluetooth.BluetoothDevice.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                r3 = r1
                android.bluetooth.BluetoothDevice r3 = (android.bluetooth.BluetoothDevice) r3
                goto L_0x0210
            L_0x020f:
            L_0x0210:
                r1 = r3
                int r3 = r8.readInt()
                boolean r4 = r6.acceptCall(r1, r3)
                r9.writeNoException()
                r9.writeInt(r4)
                return r2
            L_0x0220:
                r8.enforceInterface(r0)
                int r4 = r8.readInt()
                if (r4 == 0) goto L_0x0232
                android.os.Parcelable$Creator<android.bluetooth.BluetoothDevice> r3 = android.bluetooth.BluetoothDevice.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r8)
                android.bluetooth.BluetoothDevice r3 = (android.bluetooth.BluetoothDevice) r3
                goto L_0x0233
            L_0x0232:
            L_0x0233:
                android.os.Bundle r4 = r6.getCurrentAgEvents(r3)
                r9.writeNoException()
                if (r4 == 0) goto L_0x0243
                r9.writeInt(r2)
                r4.writeToParcel(r9, r2)
                goto L_0x0246
            L_0x0243:
                r9.writeInt(r1)
            L_0x0246:
                return r2
            L_0x0247:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                if (r1 == 0) goto L_0x025a
                android.os.Parcelable$Creator<android.bluetooth.BluetoothDevice> r1 = android.bluetooth.BluetoothDevice.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                r3 = r1
                android.bluetooth.BluetoothDevice r3 = (android.bluetooth.BluetoothDevice) r3
                goto L_0x025b
            L_0x025a:
            L_0x025b:
                r1 = r3
                java.util.List r3 = r6.getCurrentCalls(r1)
                r9.writeNoException()
                r9.writeTypedList(r3)
                return r2
            L_0x0267:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                if (r1 == 0) goto L_0x027a
                android.os.Parcelable$Creator<android.bluetooth.BluetoothDevice> r1 = android.bluetooth.BluetoothDevice.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                r3 = r1
                android.bluetooth.BluetoothDevice r3 = (android.bluetooth.BluetoothDevice) r3
                goto L_0x027b
            L_0x027a:
            L_0x027b:
                r1 = r3
                boolean r3 = r6.stopVoiceRecognition(r1)
                r9.writeNoException()
                r9.writeInt(r3)
                return r2
            L_0x0287:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                if (r1 == 0) goto L_0x029a
                android.os.Parcelable$Creator<android.bluetooth.BluetoothDevice> r1 = android.bluetooth.BluetoothDevice.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                r3 = r1
                android.bluetooth.BluetoothDevice r3 = (android.bluetooth.BluetoothDevice) r3
                goto L_0x029b
            L_0x029a:
            L_0x029b:
                r1 = r3
                boolean r3 = r6.startVoiceRecognition(r1)
                r9.writeNoException()
                r9.writeInt(r3)
                return r2
            L_0x02a7:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                if (r1 == 0) goto L_0x02ba
                android.os.Parcelable$Creator<android.bluetooth.BluetoothDevice> r1 = android.bluetooth.BluetoothDevice.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                r3 = r1
                android.bluetooth.BluetoothDevice r3 = (android.bluetooth.BluetoothDevice) r3
                goto L_0x02bb
            L_0x02ba:
            L_0x02bb:
                r1 = r3
                int r3 = r6.getPriority(r1)
                r9.writeNoException()
                r9.writeInt(r3)
                return r2
            L_0x02c7:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                if (r1 == 0) goto L_0x02da
                android.os.Parcelable$Creator<android.bluetooth.BluetoothDevice> r1 = android.bluetooth.BluetoothDevice.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                r3 = r1
                android.bluetooth.BluetoothDevice r3 = (android.bluetooth.BluetoothDevice) r3
                goto L_0x02db
            L_0x02da:
            L_0x02db:
                r1 = r3
                int r3 = r8.readInt()
                boolean r4 = r6.setPriority(r1, r3)
                r9.writeNoException()
                r9.writeInt(r4)
                return r2
            L_0x02eb:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                if (r1 == 0) goto L_0x02fe
                android.os.Parcelable$Creator<android.bluetooth.BluetoothDevice> r1 = android.bluetooth.BluetoothDevice.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                r3 = r1
                android.bluetooth.BluetoothDevice r3 = (android.bluetooth.BluetoothDevice) r3
                goto L_0x02ff
            L_0x02fe:
            L_0x02ff:
                r1 = r3
                int r3 = r6.getConnectionState(r1)
                r9.writeNoException()
                r9.writeInt(r3)
                return r2
            L_0x030b:
                r8.enforceInterface(r0)
                int[] r1 = r8.createIntArray()
                java.util.List r3 = r6.getDevicesMatchingConnectionStates(r1)
                r9.writeNoException()
                r9.writeTypedList(r3)
                return r2
            L_0x031d:
                r8.enforceInterface(r0)
                java.util.List r1 = r6.getConnectedDevices()
                r9.writeNoException()
                r9.writeTypedList(r1)
                return r2
            L_0x032b:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                if (r1 == 0) goto L_0x033e
                android.os.Parcelable$Creator<android.bluetooth.BluetoothDevice> r1 = android.bluetooth.BluetoothDevice.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                r3 = r1
                android.bluetooth.BluetoothDevice r3 = (android.bluetooth.BluetoothDevice) r3
                goto L_0x033f
            L_0x033e:
            L_0x033f:
                r1 = r3
                boolean r3 = r6.disconnect(r1)
                r9.writeNoException()
                r9.writeInt(r3)
                return r2
            L_0x034b:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                if (r1 == 0) goto L_0x035e
                android.os.Parcelable$Creator<android.bluetooth.BluetoothDevice> r1 = android.bluetooth.BluetoothDevice.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                r3 = r1
                android.bluetooth.BluetoothDevice r3 = (android.bluetooth.BluetoothDevice) r3
                goto L_0x035f
            L_0x035e:
            L_0x035f:
                r1 = r3
                boolean r3 = r6.connect(r1)
                r9.writeNoException()
                r9.writeInt(r3)
                return r2
            L_0x036b:
                r9.writeString(r0)
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: android.bluetooth.IBluetoothHeadsetClient.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IBluetoothHeadsetClient {
            public static IBluetoothHeadsetClient sDefaultImpl;
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
                    if (!this.mRemote.transact(6, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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
                    if (!this.mRemote.transact(7, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            public boolean startVoiceRecognition(BluetoothDevice device) throws RemoteException {
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
                        return Stub.getDefaultImpl().startVoiceRecognition(device);
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

            public boolean stopVoiceRecognition(BluetoothDevice device) throws RemoteException {
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
                    if (!this.mRemote.transact(9, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().stopVoiceRecognition(device);
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
                    if (!this.mRemote.transact(10, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getCurrentCalls(device);
                    }
                    _reply.readException();
                    List<BluetoothHeadsetClientCall> _result = _reply.createTypedArrayList(BluetoothHeadsetClientCall.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

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
                    if (!this.mRemote.transact(11, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getCurrentAgEvents(device);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = Bundle.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    Bundle _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean acceptCall(BluetoothDevice device, int flag) throws RemoteException {
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
                    _data.writeInt(flag);
                    if (!this.mRemote.transact(12, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().acceptCall(device, flag);
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

            public boolean holdCall(BluetoothDevice device) throws RemoteException {
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
                    if (!this.mRemote.transact(13, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().holdCall(device);
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

            public boolean rejectCall(BluetoothDevice device) throws RemoteException {
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
                    if (!this.mRemote.transact(14, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().rejectCall(device);
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

            public boolean terminateCall(BluetoothDevice device, BluetoothHeadsetClientCall call) throws RemoteException {
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
                    if (call != null) {
                        _data.writeInt(1);
                        call.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(15, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().terminateCall(device, call);
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

            public boolean enterPrivateMode(BluetoothDevice device, int index) throws RemoteException {
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
                    _data.writeInt(index);
                    if (!this.mRemote.transact(16, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().enterPrivateMode(device, index);
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

            public boolean explicitCallTransfer(BluetoothDevice device) throws RemoteException {
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
                    if (!this.mRemote.transact(17, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().explicitCallTransfer(device);
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
                    if (!this.mRemote.transact(18, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().dial(device, number);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = BluetoothHeadsetClientCall.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    BluetoothHeadsetClientCall _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean sendDTMF(BluetoothDevice device, byte code) throws RemoteException {
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
                    _data.writeByte(code);
                    if (!this.mRemote.transact(19, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().sendDTMF(device, code);
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

            public boolean getLastVoiceTagNumber(BluetoothDevice device) throws RemoteException {
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
                    if (!this.mRemote.transact(20, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getLastVoiceTagNumber(device);
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
                    if (!this.mRemote.transact(21, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAudioState(device);
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

            public boolean connectAudio(BluetoothDevice device) throws RemoteException {
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
                    if (!this.mRemote.transact(22, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().connectAudio(device);
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

            public boolean disconnectAudio(BluetoothDevice device) throws RemoteException {
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
                    if (!this.mRemote.transact(23, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().disconnectAudio(device);
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
                    _data.writeInt(allowed);
                    if (this.mRemote.transact(24, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setAudioRouteAllowed(device, allowed);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean getAudioRouteAllowed(BluetoothDevice device) throws RemoteException {
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
                    if (!this.mRemote.transact(25, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAudioRouteAllowed(device);
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
                    if (!this.mRemote.transact(26, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getCurrentAgFeatures(device);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = Bundle.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    Bundle _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IBluetoothHeadsetClient impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IBluetoothHeadsetClient getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
