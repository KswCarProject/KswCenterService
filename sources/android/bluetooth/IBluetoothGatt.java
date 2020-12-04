package android.bluetooth;

import android.app.PendingIntent;
import android.bluetooth.le.AdvertiseData;
import android.bluetooth.le.AdvertisingSetParameters;
import android.bluetooth.le.IAdvertisingSetCallback;
import android.bluetooth.le.IPeriodicAdvertisingCallback;
import android.bluetooth.le.IScannerCallback;
import android.bluetooth.le.PeriodicAdvertisingParameters;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelUuid;
import android.os.RemoteException;
import android.os.WorkSource;
import java.util.List;

public interface IBluetoothGatt extends IInterface {
    void addService(int i, BluetoothGattService bluetoothGattService) throws RemoteException;

    void beginReliableWrite(int i, String str) throws RemoteException;

    void clearServices(int i) throws RemoteException;

    void clientConnect(int i, String str, boolean z, int i2, boolean z2, int i3) throws RemoteException;

    void clientDisconnect(int i, String str) throws RemoteException;

    void clientReadPhy(int i, String str) throws RemoteException;

    void clientSetPreferredPhy(int i, String str, int i2, int i3, int i4) throws RemoteException;

    void configureMTU(int i, String str, int i2) throws RemoteException;

    void connectionParameterUpdate(int i, String str, int i2) throws RemoteException;

    void disconnectAll() throws RemoteException;

    void discoverServiceByUuid(int i, String str, ParcelUuid parcelUuid) throws RemoteException;

    void discoverServices(int i, String str) throws RemoteException;

    void enableAdvertisingSet(int i, boolean z, int i2, int i3) throws RemoteException;

    void endReliableWrite(int i, String str, boolean z) throws RemoteException;

    void flushPendingBatchResults(int i) throws RemoteException;

    List<BluetoothDevice> getDevicesMatchingConnectionStates(int[] iArr) throws RemoteException;

    void getOwnAddress(int i) throws RemoteException;

    void leConnectionUpdate(int i, String str, int i2, int i3, int i4, int i5, int i6, int i7) throws RemoteException;

    int numHwTrackFiltersAvailable() throws RemoteException;

    void readCharacteristic(int i, String str, int i2, int i3) throws RemoteException;

    void readDescriptor(int i, String str, int i2, int i3) throws RemoteException;

    void readRemoteRssi(int i, String str) throws RemoteException;

    void readUsingCharacteristicUuid(int i, String str, ParcelUuid parcelUuid, int i2, int i3, int i4) throws RemoteException;

    void refreshDevice(int i, String str) throws RemoteException;

    void registerClient(ParcelUuid parcelUuid, IBluetoothGattCallback iBluetoothGattCallback) throws RemoteException;

    void registerForNotification(int i, String str, int i2, boolean z) throws RemoteException;

    void registerScanner(IScannerCallback iScannerCallback, WorkSource workSource) throws RemoteException;

    void registerServer(ParcelUuid parcelUuid, IBluetoothGattServerCallback iBluetoothGattServerCallback) throws RemoteException;

    void registerSync(ScanResult scanResult, int i, int i2, IPeriodicAdvertisingCallback iPeriodicAdvertisingCallback) throws RemoteException;

    void removeService(int i, int i2) throws RemoteException;

    void sendNotification(int i, String str, int i2, boolean z, byte[] bArr) throws RemoteException;

    void sendResponse(int i, String str, int i2, int i3, int i4, byte[] bArr) throws RemoteException;

    void serverConnect(int i, String str, boolean z, int i2) throws RemoteException;

    void serverDisconnect(int i, String str) throws RemoteException;

    void serverReadPhy(int i, String str) throws RemoteException;

    void serverSetPreferredPhy(int i, String str, int i2, int i3, int i4) throws RemoteException;

    void setAdvertisingData(int i, AdvertiseData advertiseData) throws RemoteException;

    void setAdvertisingParameters(int i, AdvertisingSetParameters advertisingSetParameters) throws RemoteException;

    void setPeriodicAdvertisingData(int i, AdvertiseData advertiseData) throws RemoteException;

    void setPeriodicAdvertisingEnable(int i, boolean z) throws RemoteException;

    void setPeriodicAdvertisingParameters(int i, PeriodicAdvertisingParameters periodicAdvertisingParameters) throws RemoteException;

    void setScanResponseData(int i, AdvertiseData advertiseData) throws RemoteException;

    void startAdvertisingSet(AdvertisingSetParameters advertisingSetParameters, AdvertiseData advertiseData, AdvertiseData advertiseData2, PeriodicAdvertisingParameters periodicAdvertisingParameters, AdvertiseData advertiseData3, int i, int i2, IAdvertisingSetCallback iAdvertisingSetCallback) throws RemoteException;

    void startScan(int i, ScanSettings scanSettings, List<ScanFilter> list, List list2, String str) throws RemoteException;

    void startScanForIntent(PendingIntent pendingIntent, ScanSettings scanSettings, List<ScanFilter> list, String str) throws RemoteException;

    void stopAdvertisingSet(IAdvertisingSetCallback iAdvertisingSetCallback) throws RemoteException;

    void stopScan(int i) throws RemoteException;

    void stopScanForIntent(PendingIntent pendingIntent, String str) throws RemoteException;

    void unregAll() throws RemoteException;

    void unregisterClient(int i) throws RemoteException;

    void unregisterScanner(int i) throws RemoteException;

    void unregisterServer(int i) throws RemoteException;

    void unregisterSync(IPeriodicAdvertisingCallback iPeriodicAdvertisingCallback) throws RemoteException;

    void writeCharacteristic(int i, String str, int i2, int i3, int i4, byte[] bArr) throws RemoteException;

    void writeDescriptor(int i, String str, int i2, int i3, byte[] bArr) throws RemoteException;

    public static class Default implements IBluetoothGatt {
        public List<BluetoothDevice> getDevicesMatchingConnectionStates(int[] states) throws RemoteException {
            return null;
        }

        public void registerScanner(IScannerCallback callback, WorkSource workSource) throws RemoteException {
        }

        public void unregisterScanner(int scannerId) throws RemoteException {
        }

        public void startScan(int scannerId, ScanSettings settings, List<ScanFilter> list, List scanStorages, String callingPackage) throws RemoteException {
        }

        public void startScanForIntent(PendingIntent intent, ScanSettings settings, List<ScanFilter> list, String callingPackage) throws RemoteException {
        }

        public void stopScanForIntent(PendingIntent intent, String callingPackage) throws RemoteException {
        }

        public void stopScan(int scannerId) throws RemoteException {
        }

        public void flushPendingBatchResults(int scannerId) throws RemoteException {
        }

        public void startAdvertisingSet(AdvertisingSetParameters parameters, AdvertiseData advertiseData, AdvertiseData scanResponse, PeriodicAdvertisingParameters periodicParameters, AdvertiseData periodicData, int duration, int maxExtAdvEvents, IAdvertisingSetCallback callback) throws RemoteException {
        }

        public void stopAdvertisingSet(IAdvertisingSetCallback callback) throws RemoteException {
        }

        public void getOwnAddress(int advertiserId) throws RemoteException {
        }

        public void enableAdvertisingSet(int advertiserId, boolean enable, int duration, int maxExtAdvEvents) throws RemoteException {
        }

        public void setAdvertisingData(int advertiserId, AdvertiseData data) throws RemoteException {
        }

        public void setScanResponseData(int advertiserId, AdvertiseData data) throws RemoteException {
        }

        public void setAdvertisingParameters(int advertiserId, AdvertisingSetParameters parameters) throws RemoteException {
        }

        public void setPeriodicAdvertisingParameters(int advertiserId, PeriodicAdvertisingParameters parameters) throws RemoteException {
        }

        public void setPeriodicAdvertisingData(int advertiserId, AdvertiseData data) throws RemoteException {
        }

        public void setPeriodicAdvertisingEnable(int advertiserId, boolean enable) throws RemoteException {
        }

        public void registerSync(ScanResult scanResult, int skip, int timeout, IPeriodicAdvertisingCallback callback) throws RemoteException {
        }

        public void unregisterSync(IPeriodicAdvertisingCallback callback) throws RemoteException {
        }

        public void registerClient(ParcelUuid appId, IBluetoothGattCallback callback) throws RemoteException {
        }

        public void unregisterClient(int clientIf) throws RemoteException {
        }

        public void clientConnect(int clientIf, String address, boolean isDirect, int transport, boolean opportunistic, int phy) throws RemoteException {
        }

        public void clientDisconnect(int clientIf, String address) throws RemoteException {
        }

        public void clientSetPreferredPhy(int clientIf, String address, int txPhy, int rxPhy, int phyOptions) throws RemoteException {
        }

        public void clientReadPhy(int clientIf, String address) throws RemoteException {
        }

        public void refreshDevice(int clientIf, String address) throws RemoteException {
        }

        public void discoverServices(int clientIf, String address) throws RemoteException {
        }

        public void discoverServiceByUuid(int clientIf, String address, ParcelUuid uuid) throws RemoteException {
        }

        public void readCharacteristic(int clientIf, String address, int handle, int authReq) throws RemoteException {
        }

        public void readUsingCharacteristicUuid(int clientIf, String address, ParcelUuid uuid, int startHandle, int endHandle, int authReq) throws RemoteException {
        }

        public void writeCharacteristic(int clientIf, String address, int handle, int writeType, int authReq, byte[] value) throws RemoteException {
        }

        public void readDescriptor(int clientIf, String address, int handle, int authReq) throws RemoteException {
        }

        public void writeDescriptor(int clientIf, String address, int handle, int authReq, byte[] value) throws RemoteException {
        }

        public void registerForNotification(int clientIf, String address, int handle, boolean enable) throws RemoteException {
        }

        public void beginReliableWrite(int clientIf, String address) throws RemoteException {
        }

        public void endReliableWrite(int clientIf, String address, boolean execute) throws RemoteException {
        }

        public void readRemoteRssi(int clientIf, String address) throws RemoteException {
        }

        public void configureMTU(int clientIf, String address, int mtu) throws RemoteException {
        }

        public void connectionParameterUpdate(int clientIf, String address, int connectionPriority) throws RemoteException {
        }

        public void leConnectionUpdate(int clientIf, String address, int minInterval, int maxInterval, int slaveLatency, int supervisionTimeout, int minConnectionEventLen, int maxConnectionEventLen) throws RemoteException {
        }

        public void registerServer(ParcelUuid appId, IBluetoothGattServerCallback callback) throws RemoteException {
        }

        public void unregisterServer(int serverIf) throws RemoteException {
        }

        public void serverConnect(int serverIf, String address, boolean isDirect, int transport) throws RemoteException {
        }

        public void serverDisconnect(int serverIf, String address) throws RemoteException {
        }

        public void serverSetPreferredPhy(int clientIf, String address, int txPhy, int rxPhy, int phyOptions) throws RemoteException {
        }

        public void serverReadPhy(int clientIf, String address) throws RemoteException {
        }

        public void addService(int serverIf, BluetoothGattService service) throws RemoteException {
        }

        public void removeService(int serverIf, int handle) throws RemoteException {
        }

        public void clearServices(int serverIf) throws RemoteException {
        }

        public void sendResponse(int serverIf, String address, int requestId, int status, int offset, byte[] value) throws RemoteException {
        }

        public void sendNotification(int serverIf, String address, int handle, boolean confirm, byte[] value) throws RemoteException {
        }

        public void disconnectAll() throws RemoteException {
        }

        public void unregAll() throws RemoteException {
        }

        public int numHwTrackFiltersAvailable() throws RemoteException {
            return 0;
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IBluetoothGatt {
        private static final String DESCRIPTOR = "android.bluetooth.IBluetoothGatt";
        static final int TRANSACTION_addService = 48;
        static final int TRANSACTION_beginReliableWrite = 36;
        static final int TRANSACTION_clearServices = 50;
        static final int TRANSACTION_clientConnect = 23;
        static final int TRANSACTION_clientDisconnect = 24;
        static final int TRANSACTION_clientReadPhy = 26;
        static final int TRANSACTION_clientSetPreferredPhy = 25;
        static final int TRANSACTION_configureMTU = 39;
        static final int TRANSACTION_connectionParameterUpdate = 40;
        static final int TRANSACTION_disconnectAll = 53;
        static final int TRANSACTION_discoverServiceByUuid = 29;
        static final int TRANSACTION_discoverServices = 28;
        static final int TRANSACTION_enableAdvertisingSet = 12;
        static final int TRANSACTION_endReliableWrite = 37;
        static final int TRANSACTION_flushPendingBatchResults = 8;
        static final int TRANSACTION_getDevicesMatchingConnectionStates = 1;
        static final int TRANSACTION_getOwnAddress = 11;
        static final int TRANSACTION_leConnectionUpdate = 41;
        static final int TRANSACTION_numHwTrackFiltersAvailable = 55;
        static final int TRANSACTION_readCharacteristic = 30;
        static final int TRANSACTION_readDescriptor = 33;
        static final int TRANSACTION_readRemoteRssi = 38;
        static final int TRANSACTION_readUsingCharacteristicUuid = 31;
        static final int TRANSACTION_refreshDevice = 27;
        static final int TRANSACTION_registerClient = 21;
        static final int TRANSACTION_registerForNotification = 35;
        static final int TRANSACTION_registerScanner = 2;
        static final int TRANSACTION_registerServer = 42;
        static final int TRANSACTION_registerSync = 19;
        static final int TRANSACTION_removeService = 49;
        static final int TRANSACTION_sendNotification = 52;
        static final int TRANSACTION_sendResponse = 51;
        static final int TRANSACTION_serverConnect = 44;
        static final int TRANSACTION_serverDisconnect = 45;
        static final int TRANSACTION_serverReadPhy = 47;
        static final int TRANSACTION_serverSetPreferredPhy = 46;
        static final int TRANSACTION_setAdvertisingData = 13;
        static final int TRANSACTION_setAdvertisingParameters = 15;
        static final int TRANSACTION_setPeriodicAdvertisingData = 17;
        static final int TRANSACTION_setPeriodicAdvertisingEnable = 18;
        static final int TRANSACTION_setPeriodicAdvertisingParameters = 16;
        static final int TRANSACTION_setScanResponseData = 14;
        static final int TRANSACTION_startAdvertisingSet = 9;
        static final int TRANSACTION_startScan = 4;
        static final int TRANSACTION_startScanForIntent = 5;
        static final int TRANSACTION_stopAdvertisingSet = 10;
        static final int TRANSACTION_stopScan = 7;
        static final int TRANSACTION_stopScanForIntent = 6;
        static final int TRANSACTION_unregAll = 54;
        static final int TRANSACTION_unregisterClient = 22;
        static final int TRANSACTION_unregisterScanner = 3;
        static final int TRANSACTION_unregisterServer = 43;
        static final int TRANSACTION_unregisterSync = 20;
        static final int TRANSACTION_writeCharacteristic = 32;
        static final int TRANSACTION_writeDescriptor = 34;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IBluetoothGatt asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IBluetoothGatt)) {
                return new Proxy(obj);
            }
            return (IBluetoothGatt) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "getDevicesMatchingConnectionStates";
                case 2:
                    return "registerScanner";
                case 3:
                    return "unregisterScanner";
                case 4:
                    return "startScan";
                case 5:
                    return "startScanForIntent";
                case 6:
                    return "stopScanForIntent";
                case 7:
                    return "stopScan";
                case 8:
                    return "flushPendingBatchResults";
                case 9:
                    return "startAdvertisingSet";
                case 10:
                    return "stopAdvertisingSet";
                case 11:
                    return "getOwnAddress";
                case 12:
                    return "enableAdvertisingSet";
                case 13:
                    return "setAdvertisingData";
                case 14:
                    return "setScanResponseData";
                case 15:
                    return "setAdvertisingParameters";
                case 16:
                    return "setPeriodicAdvertisingParameters";
                case 17:
                    return "setPeriodicAdvertisingData";
                case 18:
                    return "setPeriodicAdvertisingEnable";
                case 19:
                    return "registerSync";
                case 20:
                    return "unregisterSync";
                case 21:
                    return "registerClient";
                case 22:
                    return "unregisterClient";
                case 23:
                    return "clientConnect";
                case 24:
                    return "clientDisconnect";
                case 25:
                    return "clientSetPreferredPhy";
                case 26:
                    return "clientReadPhy";
                case 27:
                    return "refreshDevice";
                case 28:
                    return "discoverServices";
                case 29:
                    return "discoverServiceByUuid";
                case 30:
                    return "readCharacteristic";
                case 31:
                    return "readUsingCharacteristicUuid";
                case 32:
                    return "writeCharacteristic";
                case 33:
                    return "readDescriptor";
                case 34:
                    return "writeDescriptor";
                case 35:
                    return "registerForNotification";
                case 36:
                    return "beginReliableWrite";
                case 37:
                    return "endReliableWrite";
                case 38:
                    return "readRemoteRssi";
                case 39:
                    return "configureMTU";
                case 40:
                    return "connectionParameterUpdate";
                case 41:
                    return "leConnectionUpdate";
                case 42:
                    return "registerServer";
                case 43:
                    return "unregisterServer";
                case 44:
                    return "serverConnect";
                case 45:
                    return "serverDisconnect";
                case 46:
                    return "serverSetPreferredPhy";
                case 47:
                    return "serverReadPhy";
                case 48:
                    return "addService";
                case 49:
                    return "removeService";
                case 50:
                    return "clearServices";
                case 51:
                    return "sendResponse";
                case 52:
                    return "sendNotification";
                case 53:
                    return "disconnectAll";
                case 54:
                    return "unregAll";
                case 55:
                    return "numHwTrackFiltersAvailable";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v0, resolved type: android.bluetooth.le.AdvertiseData} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v1, resolved type: android.bluetooth.le.AdvertiseData} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v2, resolved type: android.os.WorkSource} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v5, resolved type: android.bluetooth.le.AdvertiseData} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v7, resolved type: android.bluetooth.le.ScanSettings} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v8, resolved type: android.bluetooth.le.AdvertiseData} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v20, resolved type: android.app.PendingIntent} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v11, resolved type: android.bluetooth.le.AdvertiseData} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v12, resolved type: android.bluetooth.le.AdvertiseData} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v13, resolved type: android.bluetooth.le.AdvertiseData} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v16, resolved type: android.bluetooth.le.AdvertiseData} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v19, resolved type: android.bluetooth.le.AdvertiseData} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v24, resolved type: android.bluetooth.le.AdvertisingSetParameters} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v22, resolved type: android.bluetooth.le.AdvertiseData} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v28, resolved type: android.bluetooth.le.PeriodicAdvertisingParameters} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v25, resolved type: android.bluetooth.le.AdvertiseData} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v28, resolved type: android.bluetooth.le.AdvertiseData} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v60, resolved type: android.bluetooth.le.ScanResult} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v31, resolved type: android.bluetooth.le.AdvertiseData} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v66, resolved type: android.os.ParcelUuid} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v38, resolved type: android.bluetooth.le.AdvertiseData} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v15, resolved type: android.os.ParcelUuid} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v45, resolved type: android.bluetooth.le.AdvertiseData} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v96, resolved type: android.os.ParcelUuid} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v49, resolved type: android.bluetooth.le.AdvertiseData} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v67, resolved type: android.bluetooth.BluetoothGattService} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v54, resolved type: android.bluetooth.le.AdvertiseData} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v55, resolved type: android.bluetooth.le.AdvertiseData} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v56, resolved type: android.bluetooth.le.AdvertiseData} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v57, resolved type: android.bluetooth.le.AdvertiseData} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v58, resolved type: android.bluetooth.le.AdvertiseData} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v59, resolved type: android.bluetooth.le.AdvertiseData} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v60, resolved type: android.bluetooth.le.AdvertiseData} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v61, resolved type: android.bluetooth.le.AdvertiseData} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v62, resolved type: android.bluetooth.le.AdvertiseData} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v63, resolved type: android.bluetooth.le.AdvertiseData} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v64, resolved type: android.bluetooth.le.AdvertiseData} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v65, resolved type: android.bluetooth.le.AdvertiseData} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v66, resolved type: android.bluetooth.le.AdvertiseData} */
        /* JADX WARNING: type inference failed for: r5v3, types: [android.os.WorkSource] */
        /* JADX WARNING: type inference failed for: r5v7, types: [android.bluetooth.le.ScanSettings] */
        /* JADX WARNING: type inference failed for: r5v10, types: [android.app.PendingIntent] */
        /* JADX WARNING: type inference failed for: r5v21, types: [android.bluetooth.le.AdvertisingSetParameters] */
        /* JADX WARNING: type inference failed for: r5v24, types: [android.bluetooth.le.PeriodicAdvertisingParameters] */
        /* JADX WARNING: type inference failed for: r5v30, types: [android.bluetooth.le.ScanResult] */
        /* JADX WARNING: type inference failed for: r5v33, types: [android.os.ParcelUuid] */
        /* JADX WARNING: type inference failed for: r5v40, types: [android.os.ParcelUuid] */
        /* JADX WARNING: type inference failed for: r5v47, types: [android.os.ParcelUuid] */
        /* JADX WARNING: type inference failed for: r5v51, types: [android.bluetooth.BluetoothGattService] */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r24, android.os.Parcel r25, android.os.Parcel r26, int r27) throws android.os.RemoteException {
            /*
                r23 = this;
                r9 = r23
                r10 = r24
                r11 = r25
                r12 = r26
                java.lang.String r13 = "android.bluetooth.IBluetoothGatt"
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r14 = 1
                if (r10 == r0) goto L_0x067e
                r0 = 0
                r5 = 0
                switch(r10) {
                    case 1: goto L_0x066c;
                    case 2: goto L_0x0648;
                    case 3: goto L_0x063a;
                    case 4: goto L_0x05fe;
                    case 5: goto L_0x05c8;
                    case 6: goto L_0x05a8;
                    case 7: goto L_0x059a;
                    case 8: goto L_0x058c;
                    case 9: goto L_0x0517;
                    case 10: goto L_0x0505;
                    case 11: goto L_0x04f7;
                    case 12: goto L_0x04d9;
                    case 13: goto L_0x04b9;
                    case 14: goto L_0x0499;
                    case 15: goto L_0x0479;
                    case 16: goto L_0x0459;
                    case 17: goto L_0x0439;
                    case 18: goto L_0x0423;
                    case 19: goto L_0x03f7;
                    case 20: goto L_0x03e5;
                    case 21: goto L_0x03c1;
                    case 22: goto L_0x03b3;
                    case 23: goto L_0x0380;
                    case 24: goto L_0x036e;
                    case 25: goto L_0x0348;
                    case 26: goto L_0x0336;
                    case 27: goto L_0x0324;
                    case 28: goto L_0x0312;
                    case 29: goto L_0x02ee;
                    case 30: goto L_0x02d4;
                    case 31: goto L_0x029c;
                    case 32: goto L_0x026f;
                    case 33: goto L_0x0255;
                    case 34: goto L_0x022f;
                    case 35: goto L_0x0211;
                    case 36: goto L_0x01ff;
                    case 37: goto L_0x01e5;
                    case 38: goto L_0x01d3;
                    case 39: goto L_0x01bd;
                    case 40: goto L_0x01a7;
                    case 41: goto L_0x016c;
                    case 42: goto L_0x0148;
                    case 43: goto L_0x013a;
                    case 44: goto L_0x011c;
                    case 45: goto L_0x010a;
                    case 46: goto L_0x00e4;
                    case 47: goto L_0x00d2;
                    case 48: goto L_0x00b2;
                    case 49: goto L_0x00a0;
                    case 50: goto L_0x0092;
                    case 51: goto L_0x0065;
                    case 52: goto L_0x003c;
                    case 53: goto L_0x0032;
                    case 54: goto L_0x0028;
                    case 55: goto L_0x001a;
                    default: goto L_0x0015;
                }
            L_0x0015:
                boolean r0 = super.onTransact(r24, r25, r26, r27)
                return r0
            L_0x001a:
                r11.enforceInterface(r13)
                int r0 = r23.numHwTrackFiltersAvailable()
                r26.writeNoException()
                r12.writeInt(r0)
                return r14
            L_0x0028:
                r11.enforceInterface(r13)
                r23.unregAll()
                r26.writeNoException()
                return r14
            L_0x0032:
                r11.enforceInterface(r13)
                r23.disconnectAll()
                r26.writeNoException()
                return r14
            L_0x003c:
                r11.enforceInterface(r13)
                int r6 = r25.readInt()
                java.lang.String r7 = r25.readString()
                int r8 = r25.readInt()
                int r1 = r25.readInt()
                if (r1 == 0) goto L_0x0053
                r4 = r14
                goto L_0x0054
            L_0x0053:
                r4 = r0
            L_0x0054:
                byte[] r15 = r25.createByteArray()
                r0 = r23
                r1 = r6
                r2 = r7
                r3 = r8
                r5 = r15
                r0.sendNotification(r1, r2, r3, r4, r5)
                r26.writeNoException()
                return r14
            L_0x0065:
                r11.enforceInterface(r13)
                int r7 = r25.readInt()
                java.lang.String r8 = r25.readString()
                int r15 = r25.readInt()
                int r16 = r25.readInt()
                int r17 = r25.readInt()
                byte[] r18 = r25.createByteArray()
                r0 = r23
                r1 = r7
                r2 = r8
                r3 = r15
                r4 = r16
                r5 = r17
                r6 = r18
                r0.sendResponse(r1, r2, r3, r4, r5, r6)
                r26.writeNoException()
                return r14
            L_0x0092:
                r11.enforceInterface(r13)
                int r0 = r25.readInt()
                r9.clearServices(r0)
                r26.writeNoException()
                return r14
            L_0x00a0:
                r11.enforceInterface(r13)
                int r0 = r25.readInt()
                int r1 = r25.readInt()
                r9.removeService(r0, r1)
                r26.writeNoException()
                return r14
            L_0x00b2:
                r11.enforceInterface(r13)
                int r0 = r25.readInt()
                int r1 = r25.readInt()
                if (r1 == 0) goto L_0x00c9
                android.os.Parcelable$Creator<android.bluetooth.BluetoothGattService> r1 = android.bluetooth.BluetoothGattService.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r11)
                r5 = r1
                android.bluetooth.BluetoothGattService r5 = (android.bluetooth.BluetoothGattService) r5
                goto L_0x00ca
            L_0x00c9:
            L_0x00ca:
                r1 = r5
                r9.addService(r0, r1)
                r26.writeNoException()
                return r14
            L_0x00d2:
                r11.enforceInterface(r13)
                int r0 = r25.readInt()
                java.lang.String r1 = r25.readString()
                r9.serverReadPhy(r0, r1)
                r26.writeNoException()
                return r14
            L_0x00e4:
                r11.enforceInterface(r13)
                int r6 = r25.readInt()
                java.lang.String r7 = r25.readString()
                int r8 = r25.readInt()
                int r15 = r25.readInt()
                int r16 = r25.readInt()
                r0 = r23
                r1 = r6
                r2 = r7
                r3 = r8
                r4 = r15
                r5 = r16
                r0.serverSetPreferredPhy(r1, r2, r3, r4, r5)
                r26.writeNoException()
                return r14
            L_0x010a:
                r11.enforceInterface(r13)
                int r0 = r25.readInt()
                java.lang.String r1 = r25.readString()
                r9.serverDisconnect(r0, r1)
                r26.writeNoException()
                return r14
            L_0x011c:
                r11.enforceInterface(r13)
                int r1 = r25.readInt()
                java.lang.String r2 = r25.readString()
                int r3 = r25.readInt()
                if (r3 == 0) goto L_0x012f
                r0 = r14
            L_0x012f:
                int r3 = r25.readInt()
                r9.serverConnect(r1, r2, r0, r3)
                r26.writeNoException()
                return r14
            L_0x013a:
                r11.enforceInterface(r13)
                int r0 = r25.readInt()
                r9.unregisterServer(r0)
                r26.writeNoException()
                return r14
            L_0x0148:
                r11.enforceInterface(r13)
                int r0 = r25.readInt()
                if (r0 == 0) goto L_0x015b
                android.os.Parcelable$Creator<android.os.ParcelUuid> r0 = android.os.ParcelUuid.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r11)
                r5 = r0
                android.os.ParcelUuid r5 = (android.os.ParcelUuid) r5
                goto L_0x015c
            L_0x015b:
            L_0x015c:
                r0 = r5
                android.os.IBinder r1 = r25.readStrongBinder()
                android.bluetooth.IBluetoothGattServerCallback r1 = android.bluetooth.IBluetoothGattServerCallback.Stub.asInterface(r1)
                r9.registerServer(r0, r1)
                r26.writeNoException()
                return r14
            L_0x016c:
                r11.enforceInterface(r13)
                int r15 = r25.readInt()
                java.lang.String r16 = r25.readString()
                int r17 = r25.readInt()
                int r18 = r25.readInt()
                int r19 = r25.readInt()
                int r20 = r25.readInt()
                int r21 = r25.readInt()
                int r22 = r25.readInt()
                r0 = r23
                r1 = r15
                r2 = r16
                r3 = r17
                r4 = r18
                r5 = r19
                r6 = r20
                r7 = r21
                r8 = r22
                r0.leConnectionUpdate(r1, r2, r3, r4, r5, r6, r7, r8)
                r26.writeNoException()
                return r14
            L_0x01a7:
                r11.enforceInterface(r13)
                int r0 = r25.readInt()
                java.lang.String r1 = r25.readString()
                int r2 = r25.readInt()
                r9.connectionParameterUpdate(r0, r1, r2)
                r26.writeNoException()
                return r14
            L_0x01bd:
                r11.enforceInterface(r13)
                int r0 = r25.readInt()
                java.lang.String r1 = r25.readString()
                int r2 = r25.readInt()
                r9.configureMTU(r0, r1, r2)
                r26.writeNoException()
                return r14
            L_0x01d3:
                r11.enforceInterface(r13)
                int r0 = r25.readInt()
                java.lang.String r1 = r25.readString()
                r9.readRemoteRssi(r0, r1)
                r26.writeNoException()
                return r14
            L_0x01e5:
                r11.enforceInterface(r13)
                int r1 = r25.readInt()
                java.lang.String r2 = r25.readString()
                int r3 = r25.readInt()
                if (r3 == 0) goto L_0x01f8
                r0 = r14
            L_0x01f8:
                r9.endReliableWrite(r1, r2, r0)
                r26.writeNoException()
                return r14
            L_0x01ff:
                r11.enforceInterface(r13)
                int r0 = r25.readInt()
                java.lang.String r1 = r25.readString()
                r9.beginReliableWrite(r0, r1)
                r26.writeNoException()
                return r14
            L_0x0211:
                r11.enforceInterface(r13)
                int r1 = r25.readInt()
                java.lang.String r2 = r25.readString()
                int r3 = r25.readInt()
                int r4 = r25.readInt()
                if (r4 == 0) goto L_0x0228
                r0 = r14
            L_0x0228:
                r9.registerForNotification(r1, r2, r3, r0)
                r26.writeNoException()
                return r14
            L_0x022f:
                r11.enforceInterface(r13)
                int r6 = r25.readInt()
                java.lang.String r7 = r25.readString()
                int r8 = r25.readInt()
                int r15 = r25.readInt()
                byte[] r16 = r25.createByteArray()
                r0 = r23
                r1 = r6
                r2 = r7
                r3 = r8
                r4 = r15
                r5 = r16
                r0.writeDescriptor(r1, r2, r3, r4, r5)
                r26.writeNoException()
                return r14
            L_0x0255:
                r11.enforceInterface(r13)
                int r0 = r25.readInt()
                java.lang.String r1 = r25.readString()
                int r2 = r25.readInt()
                int r3 = r25.readInt()
                r9.readDescriptor(r0, r1, r2, r3)
                r26.writeNoException()
                return r14
            L_0x026f:
                r11.enforceInterface(r13)
                int r7 = r25.readInt()
                java.lang.String r8 = r25.readString()
                int r15 = r25.readInt()
                int r16 = r25.readInt()
                int r17 = r25.readInt()
                byte[] r18 = r25.createByteArray()
                r0 = r23
                r1 = r7
                r2 = r8
                r3 = r15
                r4 = r16
                r5 = r17
                r6 = r18
                r0.writeCharacteristic(r1, r2, r3, r4, r5, r6)
                r26.writeNoException()
                return r14
            L_0x029c:
                r11.enforceInterface(r13)
                int r7 = r25.readInt()
                java.lang.String r8 = r25.readString()
                int r0 = r25.readInt()
                if (r0 == 0) goto L_0x02b7
                android.os.Parcelable$Creator<android.os.ParcelUuid> r0 = android.os.ParcelUuid.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r11)
                android.os.ParcelUuid r0 = (android.os.ParcelUuid) r0
                r3 = r0
                goto L_0x02b8
            L_0x02b7:
                r3 = r5
            L_0x02b8:
                int r15 = r25.readInt()
                int r16 = r25.readInt()
                int r17 = r25.readInt()
                r0 = r23
                r1 = r7
                r2 = r8
                r4 = r15
                r5 = r16
                r6 = r17
                r0.readUsingCharacteristicUuid(r1, r2, r3, r4, r5, r6)
                r26.writeNoException()
                return r14
            L_0x02d4:
                r11.enforceInterface(r13)
                int r0 = r25.readInt()
                java.lang.String r1 = r25.readString()
                int r2 = r25.readInt()
                int r3 = r25.readInt()
                r9.readCharacteristic(r0, r1, r2, r3)
                r26.writeNoException()
                return r14
            L_0x02ee:
                r11.enforceInterface(r13)
                int r0 = r25.readInt()
                java.lang.String r1 = r25.readString()
                int r2 = r25.readInt()
                if (r2 == 0) goto L_0x0309
                android.os.Parcelable$Creator<android.os.ParcelUuid> r2 = android.os.ParcelUuid.CREATOR
                java.lang.Object r2 = r2.createFromParcel(r11)
                r5 = r2
                android.os.ParcelUuid r5 = (android.os.ParcelUuid) r5
                goto L_0x030a
            L_0x0309:
            L_0x030a:
                r2 = r5
                r9.discoverServiceByUuid(r0, r1, r2)
                r26.writeNoException()
                return r14
            L_0x0312:
                r11.enforceInterface(r13)
                int r0 = r25.readInt()
                java.lang.String r1 = r25.readString()
                r9.discoverServices(r0, r1)
                r26.writeNoException()
                return r14
            L_0x0324:
                r11.enforceInterface(r13)
                int r0 = r25.readInt()
                java.lang.String r1 = r25.readString()
                r9.refreshDevice(r0, r1)
                r26.writeNoException()
                return r14
            L_0x0336:
                r11.enforceInterface(r13)
                int r0 = r25.readInt()
                java.lang.String r1 = r25.readString()
                r9.clientReadPhy(r0, r1)
                r26.writeNoException()
                return r14
            L_0x0348:
                r11.enforceInterface(r13)
                int r6 = r25.readInt()
                java.lang.String r7 = r25.readString()
                int r8 = r25.readInt()
                int r15 = r25.readInt()
                int r16 = r25.readInt()
                r0 = r23
                r1 = r6
                r2 = r7
                r3 = r8
                r4 = r15
                r5 = r16
                r0.clientSetPreferredPhy(r1, r2, r3, r4, r5)
                r26.writeNoException()
                return r14
            L_0x036e:
                r11.enforceInterface(r13)
                int r0 = r25.readInt()
                java.lang.String r1 = r25.readString()
                r9.clientDisconnect(r0, r1)
                r26.writeNoException()
                return r14
            L_0x0380:
                r11.enforceInterface(r13)
                int r7 = r25.readInt()
                java.lang.String r8 = r25.readString()
                int r1 = r25.readInt()
                if (r1 == 0) goto L_0x0393
                r3 = r14
                goto L_0x0394
            L_0x0393:
                r3 = r0
            L_0x0394:
                int r15 = r25.readInt()
                int r1 = r25.readInt()
                if (r1 == 0) goto L_0x03a0
                r5 = r14
                goto L_0x03a1
            L_0x03a0:
                r5 = r0
            L_0x03a1:
                int r16 = r25.readInt()
                r0 = r23
                r1 = r7
                r2 = r8
                r4 = r15
                r6 = r16
                r0.clientConnect(r1, r2, r3, r4, r5, r6)
                r26.writeNoException()
                return r14
            L_0x03b3:
                r11.enforceInterface(r13)
                int r0 = r25.readInt()
                r9.unregisterClient(r0)
                r26.writeNoException()
                return r14
            L_0x03c1:
                r11.enforceInterface(r13)
                int r0 = r25.readInt()
                if (r0 == 0) goto L_0x03d4
                android.os.Parcelable$Creator<android.os.ParcelUuid> r0 = android.os.ParcelUuid.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r11)
                r5 = r0
                android.os.ParcelUuid r5 = (android.os.ParcelUuid) r5
                goto L_0x03d5
            L_0x03d4:
            L_0x03d5:
                r0 = r5
                android.os.IBinder r1 = r25.readStrongBinder()
                android.bluetooth.IBluetoothGattCallback r1 = android.bluetooth.IBluetoothGattCallback.Stub.asInterface(r1)
                r9.registerClient(r0, r1)
                r26.writeNoException()
                return r14
            L_0x03e5:
                r11.enforceInterface(r13)
                android.os.IBinder r0 = r25.readStrongBinder()
                android.bluetooth.le.IPeriodicAdvertisingCallback r0 = android.bluetooth.le.IPeriodicAdvertisingCallback.Stub.asInterface(r0)
                r9.unregisterSync(r0)
                r26.writeNoException()
                return r14
            L_0x03f7:
                r11.enforceInterface(r13)
                int r0 = r25.readInt()
                if (r0 == 0) goto L_0x040a
                android.os.Parcelable$Creator<android.bluetooth.le.ScanResult> r0 = android.bluetooth.le.ScanResult.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r11)
                r5 = r0
                android.bluetooth.le.ScanResult r5 = (android.bluetooth.le.ScanResult) r5
                goto L_0x040b
            L_0x040a:
            L_0x040b:
                r0 = r5
                int r1 = r25.readInt()
                int r2 = r25.readInt()
                android.os.IBinder r3 = r25.readStrongBinder()
                android.bluetooth.le.IPeriodicAdvertisingCallback r3 = android.bluetooth.le.IPeriodicAdvertisingCallback.Stub.asInterface(r3)
                r9.registerSync(r0, r1, r2, r3)
                r26.writeNoException()
                return r14
            L_0x0423:
                r11.enforceInterface(r13)
                int r1 = r25.readInt()
                int r2 = r25.readInt()
                if (r2 == 0) goto L_0x0432
                r0 = r14
            L_0x0432:
                r9.setPeriodicAdvertisingEnable(r1, r0)
                r26.writeNoException()
                return r14
            L_0x0439:
                r11.enforceInterface(r13)
                int r0 = r25.readInt()
                int r1 = r25.readInt()
                if (r1 == 0) goto L_0x0450
                android.os.Parcelable$Creator<android.bluetooth.le.AdvertiseData> r1 = android.bluetooth.le.AdvertiseData.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r11)
                r5 = r1
                android.bluetooth.le.AdvertiseData r5 = (android.bluetooth.le.AdvertiseData) r5
                goto L_0x0451
            L_0x0450:
            L_0x0451:
                r1 = r5
                r9.setPeriodicAdvertisingData(r0, r1)
                r26.writeNoException()
                return r14
            L_0x0459:
                r11.enforceInterface(r13)
                int r0 = r25.readInt()
                int r1 = r25.readInt()
                if (r1 == 0) goto L_0x0470
                android.os.Parcelable$Creator<android.bluetooth.le.PeriodicAdvertisingParameters> r1 = android.bluetooth.le.PeriodicAdvertisingParameters.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r11)
                r5 = r1
                android.bluetooth.le.PeriodicAdvertisingParameters r5 = (android.bluetooth.le.PeriodicAdvertisingParameters) r5
                goto L_0x0471
            L_0x0470:
            L_0x0471:
                r1 = r5
                r9.setPeriodicAdvertisingParameters(r0, r1)
                r26.writeNoException()
                return r14
            L_0x0479:
                r11.enforceInterface(r13)
                int r0 = r25.readInt()
                int r1 = r25.readInt()
                if (r1 == 0) goto L_0x0490
                android.os.Parcelable$Creator<android.bluetooth.le.AdvertisingSetParameters> r1 = android.bluetooth.le.AdvertisingSetParameters.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r11)
                r5 = r1
                android.bluetooth.le.AdvertisingSetParameters r5 = (android.bluetooth.le.AdvertisingSetParameters) r5
                goto L_0x0491
            L_0x0490:
            L_0x0491:
                r1 = r5
                r9.setAdvertisingParameters(r0, r1)
                r26.writeNoException()
                return r14
            L_0x0499:
                r11.enforceInterface(r13)
                int r0 = r25.readInt()
                int r1 = r25.readInt()
                if (r1 == 0) goto L_0x04b0
                android.os.Parcelable$Creator<android.bluetooth.le.AdvertiseData> r1 = android.bluetooth.le.AdvertiseData.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r11)
                r5 = r1
                android.bluetooth.le.AdvertiseData r5 = (android.bluetooth.le.AdvertiseData) r5
                goto L_0x04b1
            L_0x04b0:
            L_0x04b1:
                r1 = r5
                r9.setScanResponseData(r0, r1)
                r26.writeNoException()
                return r14
            L_0x04b9:
                r11.enforceInterface(r13)
                int r0 = r25.readInt()
                int r1 = r25.readInt()
                if (r1 == 0) goto L_0x04d0
                android.os.Parcelable$Creator<android.bluetooth.le.AdvertiseData> r1 = android.bluetooth.le.AdvertiseData.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r11)
                r5 = r1
                android.bluetooth.le.AdvertiseData r5 = (android.bluetooth.le.AdvertiseData) r5
                goto L_0x04d1
            L_0x04d0:
            L_0x04d1:
                r1 = r5
                r9.setAdvertisingData(r0, r1)
                r26.writeNoException()
                return r14
            L_0x04d9:
                r11.enforceInterface(r13)
                int r1 = r25.readInt()
                int r2 = r25.readInt()
                if (r2 == 0) goto L_0x04e8
                r0 = r14
            L_0x04e8:
                int r2 = r25.readInt()
                int r3 = r25.readInt()
                r9.enableAdvertisingSet(r1, r0, r2, r3)
                r26.writeNoException()
                return r14
            L_0x04f7:
                r11.enforceInterface(r13)
                int r0 = r25.readInt()
                r9.getOwnAddress(r0)
                r26.writeNoException()
                return r14
            L_0x0505:
                r11.enforceInterface(r13)
                android.os.IBinder r0 = r25.readStrongBinder()
                android.bluetooth.le.IAdvertisingSetCallback r0 = android.bluetooth.le.IAdvertisingSetCallback.Stub.asInterface(r0)
                r9.stopAdvertisingSet(r0)
                r26.writeNoException()
                return r14
            L_0x0517:
                r11.enforceInterface(r13)
                int r0 = r25.readInt()
                if (r0 == 0) goto L_0x052a
                android.os.Parcelable$Creator<android.bluetooth.le.AdvertisingSetParameters> r0 = android.bluetooth.le.AdvertisingSetParameters.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r11)
                android.bluetooth.le.AdvertisingSetParameters r0 = (android.bluetooth.le.AdvertisingSetParameters) r0
                r1 = r0
                goto L_0x052b
            L_0x052a:
                r1 = r5
            L_0x052b:
                int r0 = r25.readInt()
                if (r0 == 0) goto L_0x053b
                android.os.Parcelable$Creator<android.bluetooth.le.AdvertiseData> r0 = android.bluetooth.le.AdvertiseData.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r11)
                android.bluetooth.le.AdvertiseData r0 = (android.bluetooth.le.AdvertiseData) r0
                r2 = r0
                goto L_0x053c
            L_0x053b:
                r2 = r5
            L_0x053c:
                int r0 = r25.readInt()
                if (r0 == 0) goto L_0x054c
                android.os.Parcelable$Creator<android.bluetooth.le.AdvertiseData> r0 = android.bluetooth.le.AdvertiseData.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r11)
                android.bluetooth.le.AdvertiseData r0 = (android.bluetooth.le.AdvertiseData) r0
                r3 = r0
                goto L_0x054d
            L_0x054c:
                r3 = r5
            L_0x054d:
                int r0 = r25.readInt()
                if (r0 == 0) goto L_0x055d
                android.os.Parcelable$Creator<android.bluetooth.le.PeriodicAdvertisingParameters> r0 = android.bluetooth.le.PeriodicAdvertisingParameters.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r11)
                android.bluetooth.le.PeriodicAdvertisingParameters r0 = (android.bluetooth.le.PeriodicAdvertisingParameters) r0
                r4 = r0
                goto L_0x055e
            L_0x055d:
                r4 = r5
            L_0x055e:
                int r0 = r25.readInt()
                if (r0 == 0) goto L_0x056e
                android.os.Parcelable$Creator<android.bluetooth.le.AdvertiseData> r0 = android.bluetooth.le.AdvertiseData.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r11)
                android.bluetooth.le.AdvertiseData r0 = (android.bluetooth.le.AdvertiseData) r0
                r5 = r0
            L_0x056e:
                int r15 = r25.readInt()
                int r16 = r25.readInt()
                android.os.IBinder r0 = r25.readStrongBinder()
                android.bluetooth.le.IAdvertisingSetCallback r17 = android.bluetooth.le.IAdvertisingSetCallback.Stub.asInterface(r0)
                r0 = r23
                r6 = r15
                r7 = r16
                r8 = r17
                r0.startAdvertisingSet(r1, r2, r3, r4, r5, r6, r7, r8)
                r26.writeNoException()
                return r14
            L_0x058c:
                r11.enforceInterface(r13)
                int r0 = r25.readInt()
                r9.flushPendingBatchResults(r0)
                r26.writeNoException()
                return r14
            L_0x059a:
                r11.enforceInterface(r13)
                int r0 = r25.readInt()
                r9.stopScan(r0)
                r26.writeNoException()
                return r14
            L_0x05a8:
                r11.enforceInterface(r13)
                int r0 = r25.readInt()
                if (r0 == 0) goto L_0x05bb
                android.os.Parcelable$Creator<android.app.PendingIntent> r0 = android.app.PendingIntent.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r11)
                r5 = r0
                android.app.PendingIntent r5 = (android.app.PendingIntent) r5
                goto L_0x05bc
            L_0x05bb:
            L_0x05bc:
                r0 = r5
                java.lang.String r1 = r25.readString()
                r9.stopScanForIntent(r0, r1)
                r26.writeNoException()
                return r14
            L_0x05c8:
                r11.enforceInterface(r13)
                int r0 = r25.readInt()
                if (r0 == 0) goto L_0x05da
                android.os.Parcelable$Creator<android.app.PendingIntent> r0 = android.app.PendingIntent.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r11)
                android.app.PendingIntent r0 = (android.app.PendingIntent) r0
                goto L_0x05db
            L_0x05da:
                r0 = r5
            L_0x05db:
                int r1 = r25.readInt()
                if (r1 == 0) goto L_0x05eb
                android.os.Parcelable$Creator<android.bluetooth.le.ScanSettings> r1 = android.bluetooth.le.ScanSettings.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r11)
                r5 = r1
                android.bluetooth.le.ScanSettings r5 = (android.bluetooth.le.ScanSettings) r5
                goto L_0x05ec
            L_0x05eb:
            L_0x05ec:
                r1 = r5
                android.os.Parcelable$Creator<android.bluetooth.le.ScanFilter> r2 = android.bluetooth.le.ScanFilter.CREATOR
                java.util.ArrayList r2 = r11.createTypedArrayList(r2)
                java.lang.String r3 = r25.readString()
                r9.startScanForIntent(r0, r1, r2, r3)
                r26.writeNoException()
                return r14
            L_0x05fe:
                r11.enforceInterface(r13)
                int r6 = r25.readInt()
                int r0 = r25.readInt()
                if (r0 == 0) goto L_0x0615
                android.os.Parcelable$Creator<android.bluetooth.le.ScanSettings> r0 = android.bluetooth.le.ScanSettings.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r11)
                android.bluetooth.le.ScanSettings r0 = (android.bluetooth.le.ScanSettings) r0
                r2 = r0
                goto L_0x0616
            L_0x0615:
                r2 = r5
            L_0x0616:
                android.os.Parcelable$Creator<android.bluetooth.le.ScanFilter> r0 = android.bluetooth.le.ScanFilter.CREATOR
                java.util.ArrayList r7 = r11.createTypedArrayList(r0)
                java.lang.Class r0 = r23.getClass()
                java.lang.ClassLoader r8 = r0.getClassLoader()
                java.util.ArrayList r15 = r11.readArrayList(r8)
                java.lang.String r16 = r25.readString()
                r0 = r23
                r1 = r6
                r3 = r7
                r4 = r15
                r5 = r16
                r0.startScan(r1, r2, r3, r4, r5)
                r26.writeNoException()
                return r14
            L_0x063a:
                r11.enforceInterface(r13)
                int r0 = r25.readInt()
                r9.unregisterScanner(r0)
                r26.writeNoException()
                return r14
            L_0x0648:
                r11.enforceInterface(r13)
                android.os.IBinder r0 = r25.readStrongBinder()
                android.bluetooth.le.IScannerCallback r0 = android.bluetooth.le.IScannerCallback.Stub.asInterface(r0)
                int r1 = r25.readInt()
                if (r1 == 0) goto L_0x0663
                android.os.Parcelable$Creator<android.os.WorkSource> r1 = android.os.WorkSource.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r11)
                r5 = r1
                android.os.WorkSource r5 = (android.os.WorkSource) r5
                goto L_0x0664
            L_0x0663:
            L_0x0664:
                r1 = r5
                r9.registerScanner(r0, r1)
                r26.writeNoException()
                return r14
            L_0x066c:
                r11.enforceInterface(r13)
                int[] r0 = r25.createIntArray()
                java.util.List r1 = r9.getDevicesMatchingConnectionStates(r0)
                r26.writeNoException()
                r12.writeTypedList(r1)
                return r14
            L_0x067e:
                r12.writeString(r13)
                return r14
            */
            throw new UnsupportedOperationException("Method not decompiled: android.bluetooth.IBluetoothGatt.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IBluetoothGatt {
            public static IBluetoothGatt sDefaultImpl;
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

            public List<BluetoothDevice> getDevicesMatchingConnectionStates(int[] states) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeIntArray(states);
                    if (!this.mRemote.transact(1, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            public void registerScanner(IScannerCallback callback, WorkSource workSource) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (workSource != null) {
                        _data.writeInt(1);
                        workSource.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(2, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().registerScanner(callback, workSource);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void unregisterScanner(int scannerId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(scannerId);
                    if (this.mRemote.transact(3, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().unregisterScanner(scannerId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void startScan(int scannerId, ScanSettings settings, List<ScanFilter> filters, List scanStorages, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(scannerId);
                    if (settings != null) {
                        _data.writeInt(1);
                        settings.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeTypedList(filters);
                    _data.writeList(scanStorages);
                    _data.writeString(callingPackage);
                    if (this.mRemote.transact(4, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().startScan(scannerId, settings, filters, scanStorages, callingPackage);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void startScanForIntent(PendingIntent intent, ScanSettings settings, List<ScanFilter> filters, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (intent != null) {
                        _data.writeInt(1);
                        intent.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (settings != null) {
                        _data.writeInt(1);
                        settings.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeTypedList(filters);
                    _data.writeString(callingPackage);
                    if (this.mRemote.transact(5, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().startScanForIntent(intent, settings, filters, callingPackage);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void stopScanForIntent(PendingIntent intent, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (intent != null) {
                        _data.writeInt(1);
                        intent.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(callingPackage);
                    if (this.mRemote.transact(6, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().stopScanForIntent(intent, callingPackage);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void stopScan(int scannerId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(scannerId);
                    if (this.mRemote.transact(7, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().stopScan(scannerId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void flushPendingBatchResults(int scannerId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(scannerId);
                    if (this.mRemote.transact(8, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().flushPendingBatchResults(scannerId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void startAdvertisingSet(AdvertisingSetParameters parameters, AdvertiseData advertiseData, AdvertiseData scanResponse, PeriodicAdvertisingParameters periodicParameters, AdvertiseData periodicData, int duration, int maxExtAdvEvents, IAdvertisingSetCallback callback) throws RemoteException {
                Parcel _reply;
                AdvertisingSetParameters advertisingSetParameters = parameters;
                AdvertiseData advertiseData2 = advertiseData;
                AdvertiseData advertiseData3 = scanResponse;
                PeriodicAdvertisingParameters periodicAdvertisingParameters = periodicParameters;
                AdvertiseData advertiseData4 = periodicData;
                Parcel _data = Parcel.obtain();
                Parcel _reply2 = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (advertisingSetParameters != null) {
                        try {
                            _data.writeInt(1);
                            advertisingSetParameters.writeToParcel(_data, 0);
                        } catch (Throwable th) {
                            th = th;
                            _reply = _reply2;
                        }
                    } else {
                        _data.writeInt(0);
                    }
                    if (advertiseData2 != null) {
                        _data.writeInt(1);
                        advertiseData2.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (advertiseData3 != null) {
                        _data.writeInt(1);
                        advertiseData3.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (periodicAdvertisingParameters != null) {
                        _data.writeInt(1);
                        periodicAdvertisingParameters.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (advertiseData4 != null) {
                        _data.writeInt(1);
                        advertiseData4.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(duration);
                    _data.writeInt(maxExtAdvEvents);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(9, _data, _reply2, 0) || Stub.getDefaultImpl() == null) {
                        _reply = _reply2;
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    _reply = _reply2;
                    try {
                        Stub.getDefaultImpl().startAdvertisingSet(parameters, advertiseData, scanResponse, periodicParameters, periodicData, duration, maxExtAdvEvents, callback);
                        _reply.recycle();
                        _data.recycle();
                    } catch (Throwable th2) {
                        th = th2;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    _reply = _reply2;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public void stopAdvertisingSet(IAdvertisingSetCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(10, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().stopAdvertisingSet(callback);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void getOwnAddress(int advertiserId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(advertiserId);
                    if (this.mRemote.transact(11, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().getOwnAddress(advertiserId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void enableAdvertisingSet(int advertiserId, boolean enable, int duration, int maxExtAdvEvents) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(advertiserId);
                    _data.writeInt(enable);
                    _data.writeInt(duration);
                    _data.writeInt(maxExtAdvEvents);
                    if (this.mRemote.transact(12, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().enableAdvertisingSet(advertiserId, enable, duration, maxExtAdvEvents);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setAdvertisingData(int advertiserId, AdvertiseData data) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(advertiserId);
                    if (data != null) {
                        _data.writeInt(1);
                        data.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(13, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setAdvertisingData(advertiserId, data);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setScanResponseData(int advertiserId, AdvertiseData data) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(advertiserId);
                    if (data != null) {
                        _data.writeInt(1);
                        data.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(14, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setScanResponseData(advertiserId, data);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setAdvertisingParameters(int advertiserId, AdvertisingSetParameters parameters) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(advertiserId);
                    if (parameters != null) {
                        _data.writeInt(1);
                        parameters.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(15, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setAdvertisingParameters(advertiserId, parameters);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setPeriodicAdvertisingParameters(int advertiserId, PeriodicAdvertisingParameters parameters) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(advertiserId);
                    if (parameters != null) {
                        _data.writeInt(1);
                        parameters.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(16, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setPeriodicAdvertisingParameters(advertiserId, parameters);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setPeriodicAdvertisingData(int advertiserId, AdvertiseData data) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(advertiserId);
                    if (data != null) {
                        _data.writeInt(1);
                        data.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(17, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setPeriodicAdvertisingData(advertiserId, data);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setPeriodicAdvertisingEnable(int advertiserId, boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(advertiserId);
                    _data.writeInt(enable);
                    if (this.mRemote.transact(18, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setPeriodicAdvertisingEnable(advertiserId, enable);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void registerSync(ScanResult scanResult, int skip, int timeout, IPeriodicAdvertisingCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (scanResult != null) {
                        _data.writeInt(1);
                        scanResult.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(skip);
                    _data.writeInt(timeout);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(19, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().registerSync(scanResult, skip, timeout, callback);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void unregisterSync(IPeriodicAdvertisingCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(20, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().unregisterSync(callback);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void registerClient(ParcelUuid appId, IBluetoothGattCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (appId != null) {
                        _data.writeInt(1);
                        appId.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(21, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().registerClient(appId, callback);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void unregisterClient(int clientIf) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(clientIf);
                    if (this.mRemote.transact(22, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().unregisterClient(clientIf);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void clientConnect(int clientIf, String address, boolean isDirect, int transport, boolean opportunistic, int phy) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeInt(clientIf);
                        try {
                            _data.writeString(address);
                            try {
                                _data.writeInt(isDirect ? 1 : 0);
                                try {
                                    _data.writeInt(transport);
                                } catch (Throwable th) {
                                    th = th;
                                    boolean z = opportunistic;
                                    int i = phy;
                                    _reply.recycle();
                                    _data.recycle();
                                    throw th;
                                }
                            } catch (Throwable th2) {
                                th = th2;
                                int i2 = transport;
                                boolean z2 = opportunistic;
                                int i3 = phy;
                                _reply.recycle();
                                _data.recycle();
                                throw th;
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            boolean z3 = isDirect;
                            int i22 = transport;
                            boolean z22 = opportunistic;
                            int i32 = phy;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                        try {
                            _data.writeInt(opportunistic ? 1 : 0);
                            try {
                                _data.writeInt(phy);
                                if (this.mRemote.transact(23, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                    _reply.readException();
                                    _reply.recycle();
                                    _data.recycle();
                                    return;
                                }
                                Stub.getDefaultImpl().clientConnect(clientIf, address, isDirect, transport, opportunistic, phy);
                                _reply.recycle();
                                _data.recycle();
                            } catch (Throwable th4) {
                                th = th4;
                                _reply.recycle();
                                _data.recycle();
                                throw th;
                            }
                        } catch (Throwable th5) {
                            th = th5;
                            int i322 = phy;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th6) {
                        th = th6;
                        String str = address;
                        boolean z32 = isDirect;
                        int i222 = transport;
                        boolean z222 = opportunistic;
                        int i3222 = phy;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th7) {
                    th = th7;
                    int i4 = clientIf;
                    String str2 = address;
                    boolean z322 = isDirect;
                    int i2222 = transport;
                    boolean z2222 = opportunistic;
                    int i32222 = phy;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public void clientDisconnect(int clientIf, String address) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(clientIf);
                    _data.writeString(address);
                    if (this.mRemote.transact(24, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().clientDisconnect(clientIf, address);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void clientSetPreferredPhy(int clientIf, String address, int txPhy, int rxPhy, int phyOptions) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(clientIf);
                    _data.writeString(address);
                    _data.writeInt(txPhy);
                    _data.writeInt(rxPhy);
                    _data.writeInt(phyOptions);
                    if (this.mRemote.transact(25, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().clientSetPreferredPhy(clientIf, address, txPhy, rxPhy, phyOptions);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void clientReadPhy(int clientIf, String address) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(clientIf);
                    _data.writeString(address);
                    if (this.mRemote.transact(26, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().clientReadPhy(clientIf, address);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void refreshDevice(int clientIf, String address) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(clientIf);
                    _data.writeString(address);
                    if (this.mRemote.transact(27, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().refreshDevice(clientIf, address);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void discoverServices(int clientIf, String address) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(clientIf);
                    _data.writeString(address);
                    if (this.mRemote.transact(28, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().discoverServices(clientIf, address);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void discoverServiceByUuid(int clientIf, String address, ParcelUuid uuid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(clientIf);
                    _data.writeString(address);
                    if (uuid != null) {
                        _data.writeInt(1);
                        uuid.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(29, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().discoverServiceByUuid(clientIf, address, uuid);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void readCharacteristic(int clientIf, String address, int handle, int authReq) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(clientIf);
                    _data.writeString(address);
                    _data.writeInt(handle);
                    _data.writeInt(authReq);
                    if (this.mRemote.transact(30, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().readCharacteristic(clientIf, address, handle, authReq);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void readUsingCharacteristicUuid(int clientIf, String address, ParcelUuid uuid, int startHandle, int endHandle, int authReq) throws RemoteException {
                ParcelUuid parcelUuid = uuid;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeInt(clientIf);
                        try {
                            _data.writeString(address);
                            if (parcelUuid != null) {
                                _data.writeInt(1);
                                parcelUuid.writeToParcel(_data, 0);
                            } else {
                                _data.writeInt(0);
                            }
                        } catch (Throwable th) {
                            th = th;
                            int i = startHandle;
                            int i2 = endHandle;
                            int i3 = authReq;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        String str = address;
                        int i4 = startHandle;
                        int i22 = endHandle;
                        int i32 = authReq;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(startHandle);
                        try {
                            _data.writeInt(endHandle);
                        } catch (Throwable th3) {
                            th = th3;
                            int i322 = authReq;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                        try {
                            _data.writeInt(authReq);
                            if (this.mRemote.transact(31, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                _reply.readException();
                                _reply.recycle();
                                _data.recycle();
                                return;
                            }
                            Stub.getDefaultImpl().readUsingCharacteristicUuid(clientIf, address, uuid, startHandle, endHandle, authReq);
                            _reply.recycle();
                            _data.recycle();
                        } catch (Throwable th4) {
                            th = th4;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th5) {
                        th = th5;
                        int i222 = endHandle;
                        int i3222 = authReq;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th6) {
                    th = th6;
                    int i5 = clientIf;
                    String str2 = address;
                    int i42 = startHandle;
                    int i2222 = endHandle;
                    int i32222 = authReq;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public void writeCharacteristic(int clientIf, String address, int handle, int writeType, int authReq, byte[] value) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeInt(clientIf);
                        try {
                            _data.writeString(address);
                            try {
                                _data.writeInt(handle);
                                try {
                                    _data.writeInt(writeType);
                                } catch (Throwable th) {
                                    th = th;
                                    int i = authReq;
                                    byte[] bArr = value;
                                    _reply.recycle();
                                    _data.recycle();
                                    throw th;
                                }
                            } catch (Throwable th2) {
                                th = th2;
                                int i2 = writeType;
                                int i3 = authReq;
                                byte[] bArr2 = value;
                                _reply.recycle();
                                _data.recycle();
                                throw th;
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            int i4 = handle;
                            int i22 = writeType;
                            int i32 = authReq;
                            byte[] bArr22 = value;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                        try {
                            _data.writeInt(authReq);
                            try {
                                _data.writeByteArray(value);
                                if (this.mRemote.transact(32, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                    _reply.readException();
                                    _reply.recycle();
                                    _data.recycle();
                                    return;
                                }
                                Stub.getDefaultImpl().writeCharacteristic(clientIf, address, handle, writeType, authReq, value);
                                _reply.recycle();
                                _data.recycle();
                            } catch (Throwable th4) {
                                th = th4;
                                _reply.recycle();
                                _data.recycle();
                                throw th;
                            }
                        } catch (Throwable th5) {
                            th = th5;
                            byte[] bArr222 = value;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th6) {
                        th = th6;
                        String str = address;
                        int i42 = handle;
                        int i222 = writeType;
                        int i322 = authReq;
                        byte[] bArr2222 = value;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th7) {
                    th = th7;
                    int i5 = clientIf;
                    String str2 = address;
                    int i422 = handle;
                    int i2222 = writeType;
                    int i3222 = authReq;
                    byte[] bArr22222 = value;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public void readDescriptor(int clientIf, String address, int handle, int authReq) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(clientIf);
                    _data.writeString(address);
                    _data.writeInt(handle);
                    _data.writeInt(authReq);
                    if (this.mRemote.transact(33, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().readDescriptor(clientIf, address, handle, authReq);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void writeDescriptor(int clientIf, String address, int handle, int authReq, byte[] value) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(clientIf);
                    _data.writeString(address);
                    _data.writeInt(handle);
                    _data.writeInt(authReq);
                    _data.writeByteArray(value);
                    if (this.mRemote.transact(34, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().writeDescriptor(clientIf, address, handle, authReq, value);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void registerForNotification(int clientIf, String address, int handle, boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(clientIf);
                    _data.writeString(address);
                    _data.writeInt(handle);
                    _data.writeInt(enable);
                    if (this.mRemote.transact(35, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().registerForNotification(clientIf, address, handle, enable);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void beginReliableWrite(int clientIf, String address) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(clientIf);
                    _data.writeString(address);
                    if (this.mRemote.transact(36, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().beginReliableWrite(clientIf, address);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void endReliableWrite(int clientIf, String address, boolean execute) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(clientIf);
                    _data.writeString(address);
                    _data.writeInt(execute);
                    if (this.mRemote.transact(37, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().endReliableWrite(clientIf, address, execute);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void readRemoteRssi(int clientIf, String address) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(clientIf);
                    _data.writeString(address);
                    if (this.mRemote.transact(38, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().readRemoteRssi(clientIf, address);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void configureMTU(int clientIf, String address, int mtu) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(clientIf);
                    _data.writeString(address);
                    _data.writeInt(mtu);
                    if (this.mRemote.transact(39, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().configureMTU(clientIf, address, mtu);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void connectionParameterUpdate(int clientIf, String address, int connectionPriority) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(clientIf);
                    _data.writeString(address);
                    _data.writeInt(connectionPriority);
                    if (this.mRemote.transact(40, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().connectionParameterUpdate(clientIf, address, connectionPriority);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void leConnectionUpdate(int clientIf, String address, int minInterval, int maxInterval, int slaveLatency, int supervisionTimeout, int minConnectionEventLen, int maxConnectionEventLen) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeInt(clientIf);
                        try {
                            _data.writeString(address);
                        } catch (Throwable th) {
                            th = th;
                            int i = minInterval;
                            int i2 = maxInterval;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                        try {
                            _data.writeInt(minInterval);
                            try {
                                _data.writeInt(maxInterval);
                                _data.writeInt(slaveLatency);
                                _data.writeInt(supervisionTimeout);
                                _data.writeInt(minConnectionEventLen);
                                _data.writeInt(maxConnectionEventLen);
                                if (this.mRemote.transact(41, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                    _reply.readException();
                                    _reply.recycle();
                                    _data.recycle();
                                    return;
                                }
                                Stub.getDefaultImpl().leConnectionUpdate(clientIf, address, minInterval, maxInterval, slaveLatency, supervisionTimeout, minConnectionEventLen, maxConnectionEventLen);
                                _reply.recycle();
                                _data.recycle();
                            } catch (Throwable th2) {
                                th = th2;
                                _reply.recycle();
                                _data.recycle();
                                throw th;
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            int i22 = maxInterval;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th4) {
                        th = th4;
                        String str = address;
                        int i3 = minInterval;
                        int i222 = maxInterval;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th5) {
                    th = th5;
                    int i4 = clientIf;
                    String str2 = address;
                    int i32 = minInterval;
                    int i2222 = maxInterval;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public void registerServer(ParcelUuid appId, IBluetoothGattServerCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (appId != null) {
                        _data.writeInt(1);
                        appId.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(42, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().registerServer(appId, callback);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void unregisterServer(int serverIf) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(serverIf);
                    if (this.mRemote.transact(43, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().unregisterServer(serverIf);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void serverConnect(int serverIf, String address, boolean isDirect, int transport) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(serverIf);
                    _data.writeString(address);
                    _data.writeInt(isDirect);
                    _data.writeInt(transport);
                    if (this.mRemote.transact(44, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().serverConnect(serverIf, address, isDirect, transport);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void serverDisconnect(int serverIf, String address) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(serverIf);
                    _data.writeString(address);
                    if (this.mRemote.transact(45, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().serverDisconnect(serverIf, address);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void serverSetPreferredPhy(int clientIf, String address, int txPhy, int rxPhy, int phyOptions) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(clientIf);
                    _data.writeString(address);
                    _data.writeInt(txPhy);
                    _data.writeInt(rxPhy);
                    _data.writeInt(phyOptions);
                    if (this.mRemote.transact(46, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().serverSetPreferredPhy(clientIf, address, txPhy, rxPhy, phyOptions);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void serverReadPhy(int clientIf, String address) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(clientIf);
                    _data.writeString(address);
                    if (this.mRemote.transact(47, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().serverReadPhy(clientIf, address);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void addService(int serverIf, BluetoothGattService service) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(serverIf);
                    if (service != null) {
                        _data.writeInt(1);
                        service.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(48, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().addService(serverIf, service);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void removeService(int serverIf, int handle) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(serverIf);
                    _data.writeInt(handle);
                    if (this.mRemote.transact(49, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().removeService(serverIf, handle);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void clearServices(int serverIf) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(serverIf);
                    if (this.mRemote.transact(50, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().clearServices(serverIf);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void sendResponse(int serverIf, String address, int requestId, int status, int offset, byte[] value) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeInt(serverIf);
                        try {
                            _data.writeString(address);
                            try {
                                _data.writeInt(requestId);
                                try {
                                    _data.writeInt(status);
                                } catch (Throwable th) {
                                    th = th;
                                    int i = offset;
                                    byte[] bArr = value;
                                    _reply.recycle();
                                    _data.recycle();
                                    throw th;
                                }
                            } catch (Throwable th2) {
                                th = th2;
                                int i2 = status;
                                int i3 = offset;
                                byte[] bArr2 = value;
                                _reply.recycle();
                                _data.recycle();
                                throw th;
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            int i4 = requestId;
                            int i22 = status;
                            int i32 = offset;
                            byte[] bArr22 = value;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                        try {
                            _data.writeInt(offset);
                            try {
                                _data.writeByteArray(value);
                                if (this.mRemote.transact(51, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                    _reply.readException();
                                    _reply.recycle();
                                    _data.recycle();
                                    return;
                                }
                                Stub.getDefaultImpl().sendResponse(serverIf, address, requestId, status, offset, value);
                                _reply.recycle();
                                _data.recycle();
                            } catch (Throwable th4) {
                                th = th4;
                                _reply.recycle();
                                _data.recycle();
                                throw th;
                            }
                        } catch (Throwable th5) {
                            th = th5;
                            byte[] bArr222 = value;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th6) {
                        th = th6;
                        String str = address;
                        int i42 = requestId;
                        int i222 = status;
                        int i322 = offset;
                        byte[] bArr2222 = value;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th7) {
                    th = th7;
                    int i5 = serverIf;
                    String str2 = address;
                    int i422 = requestId;
                    int i2222 = status;
                    int i3222 = offset;
                    byte[] bArr22222 = value;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public void sendNotification(int serverIf, String address, int handle, boolean confirm, byte[] value) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(serverIf);
                    _data.writeString(address);
                    _data.writeInt(handle);
                    _data.writeInt(confirm);
                    _data.writeByteArray(value);
                    if (this.mRemote.transact(52, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().sendNotification(serverIf, address, handle, confirm, value);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void disconnectAll() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(53, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().disconnectAll();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void unregAll() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(54, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().unregAll();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int numHwTrackFiltersAvailable() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(55, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().numHwTrackFiltersAvailable();
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

        public static boolean setDefaultImpl(IBluetoothGatt impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IBluetoothGatt getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
