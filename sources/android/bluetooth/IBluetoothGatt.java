package android.bluetooth;

import android.app.PendingIntent;
import android.bluetooth.IBluetoothGattCallback;
import android.bluetooth.IBluetoothGattServerCallback;
import android.bluetooth.p000le.AdvertiseData;
import android.bluetooth.p000le.AdvertisingSetParameters;
import android.bluetooth.p000le.IAdvertisingSetCallback;
import android.bluetooth.p000le.IPeriodicAdvertisingCallback;
import android.bluetooth.p000le.IScannerCallback;
import android.bluetooth.p000le.PeriodicAdvertisingParameters;
import android.bluetooth.p000le.ScanFilter;
import android.bluetooth.p000le.ScanResult;
import android.bluetooth.p000le.ScanSettings;
import android.p007os.Binder;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.ParcelUuid;
import android.p007os.RemoteException;
import android.p007os.WorkSource;
import java.util.List;

/* loaded from: classes.dex */
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

    /* loaded from: classes.dex */
    public static class Default implements IBluetoothGatt {
        @Override // android.bluetooth.IBluetoothGatt
        public List<BluetoothDevice> getDevicesMatchingConnectionStates(int[] states) throws RemoteException {
            return null;
        }

        @Override // android.bluetooth.IBluetoothGatt
        public void registerScanner(IScannerCallback callback, WorkSource workSource) throws RemoteException {
        }

        @Override // android.bluetooth.IBluetoothGatt
        public void unregisterScanner(int scannerId) throws RemoteException {
        }

        @Override // android.bluetooth.IBluetoothGatt
        public void startScan(int scannerId, ScanSettings settings, List<ScanFilter> filters, List scanStorages, String callingPackage) throws RemoteException {
        }

        @Override // android.bluetooth.IBluetoothGatt
        public void startScanForIntent(PendingIntent intent, ScanSettings settings, List<ScanFilter> filters, String callingPackage) throws RemoteException {
        }

        @Override // android.bluetooth.IBluetoothGatt
        public void stopScanForIntent(PendingIntent intent, String callingPackage) throws RemoteException {
        }

        @Override // android.bluetooth.IBluetoothGatt
        public void stopScan(int scannerId) throws RemoteException {
        }

        @Override // android.bluetooth.IBluetoothGatt
        public void flushPendingBatchResults(int scannerId) throws RemoteException {
        }

        @Override // android.bluetooth.IBluetoothGatt
        public void startAdvertisingSet(AdvertisingSetParameters parameters, AdvertiseData advertiseData, AdvertiseData scanResponse, PeriodicAdvertisingParameters periodicParameters, AdvertiseData periodicData, int duration, int maxExtAdvEvents, IAdvertisingSetCallback callback) throws RemoteException {
        }

        @Override // android.bluetooth.IBluetoothGatt
        public void stopAdvertisingSet(IAdvertisingSetCallback callback) throws RemoteException {
        }

        @Override // android.bluetooth.IBluetoothGatt
        public void getOwnAddress(int advertiserId) throws RemoteException {
        }

        @Override // android.bluetooth.IBluetoothGatt
        public void enableAdvertisingSet(int advertiserId, boolean enable, int duration, int maxExtAdvEvents) throws RemoteException {
        }

        @Override // android.bluetooth.IBluetoothGatt
        public void setAdvertisingData(int advertiserId, AdvertiseData data) throws RemoteException {
        }

        @Override // android.bluetooth.IBluetoothGatt
        public void setScanResponseData(int advertiserId, AdvertiseData data) throws RemoteException {
        }

        @Override // android.bluetooth.IBluetoothGatt
        public void setAdvertisingParameters(int advertiserId, AdvertisingSetParameters parameters) throws RemoteException {
        }

        @Override // android.bluetooth.IBluetoothGatt
        public void setPeriodicAdvertisingParameters(int advertiserId, PeriodicAdvertisingParameters parameters) throws RemoteException {
        }

        @Override // android.bluetooth.IBluetoothGatt
        public void setPeriodicAdvertisingData(int advertiserId, AdvertiseData data) throws RemoteException {
        }

        @Override // android.bluetooth.IBluetoothGatt
        public void setPeriodicAdvertisingEnable(int advertiserId, boolean enable) throws RemoteException {
        }

        @Override // android.bluetooth.IBluetoothGatt
        public void registerSync(ScanResult scanResult, int skip, int timeout, IPeriodicAdvertisingCallback callback) throws RemoteException {
        }

        @Override // android.bluetooth.IBluetoothGatt
        public void unregisterSync(IPeriodicAdvertisingCallback callback) throws RemoteException {
        }

        @Override // android.bluetooth.IBluetoothGatt
        public void registerClient(ParcelUuid appId, IBluetoothGattCallback callback) throws RemoteException {
        }

        @Override // android.bluetooth.IBluetoothGatt
        public void unregisterClient(int clientIf) throws RemoteException {
        }

        @Override // android.bluetooth.IBluetoothGatt
        public void clientConnect(int clientIf, String address, boolean isDirect, int transport, boolean opportunistic, int phy) throws RemoteException {
        }

        @Override // android.bluetooth.IBluetoothGatt
        public void clientDisconnect(int clientIf, String address) throws RemoteException {
        }

        @Override // android.bluetooth.IBluetoothGatt
        public void clientSetPreferredPhy(int clientIf, String address, int txPhy, int rxPhy, int phyOptions) throws RemoteException {
        }

        @Override // android.bluetooth.IBluetoothGatt
        public void clientReadPhy(int clientIf, String address) throws RemoteException {
        }

        @Override // android.bluetooth.IBluetoothGatt
        public void refreshDevice(int clientIf, String address) throws RemoteException {
        }

        @Override // android.bluetooth.IBluetoothGatt
        public void discoverServices(int clientIf, String address) throws RemoteException {
        }

        @Override // android.bluetooth.IBluetoothGatt
        public void discoverServiceByUuid(int clientIf, String address, ParcelUuid uuid) throws RemoteException {
        }

        @Override // android.bluetooth.IBluetoothGatt
        public void readCharacteristic(int clientIf, String address, int handle, int authReq) throws RemoteException {
        }

        @Override // android.bluetooth.IBluetoothGatt
        public void readUsingCharacteristicUuid(int clientIf, String address, ParcelUuid uuid, int startHandle, int endHandle, int authReq) throws RemoteException {
        }

        @Override // android.bluetooth.IBluetoothGatt
        public void writeCharacteristic(int clientIf, String address, int handle, int writeType, int authReq, byte[] value) throws RemoteException {
        }

        @Override // android.bluetooth.IBluetoothGatt
        public void readDescriptor(int clientIf, String address, int handle, int authReq) throws RemoteException {
        }

        @Override // android.bluetooth.IBluetoothGatt
        public void writeDescriptor(int clientIf, String address, int handle, int authReq, byte[] value) throws RemoteException {
        }

        @Override // android.bluetooth.IBluetoothGatt
        public void registerForNotification(int clientIf, String address, int handle, boolean enable) throws RemoteException {
        }

        @Override // android.bluetooth.IBluetoothGatt
        public void beginReliableWrite(int clientIf, String address) throws RemoteException {
        }

        @Override // android.bluetooth.IBluetoothGatt
        public void endReliableWrite(int clientIf, String address, boolean execute) throws RemoteException {
        }

        @Override // android.bluetooth.IBluetoothGatt
        public void readRemoteRssi(int clientIf, String address) throws RemoteException {
        }

        @Override // android.bluetooth.IBluetoothGatt
        public void configureMTU(int clientIf, String address, int mtu) throws RemoteException {
        }

        @Override // android.bluetooth.IBluetoothGatt
        public void connectionParameterUpdate(int clientIf, String address, int connectionPriority) throws RemoteException {
        }

        @Override // android.bluetooth.IBluetoothGatt
        public void leConnectionUpdate(int clientIf, String address, int minInterval, int maxInterval, int slaveLatency, int supervisionTimeout, int minConnectionEventLen, int maxConnectionEventLen) throws RemoteException {
        }

        @Override // android.bluetooth.IBluetoothGatt
        public void registerServer(ParcelUuid appId, IBluetoothGattServerCallback callback) throws RemoteException {
        }

        @Override // android.bluetooth.IBluetoothGatt
        public void unregisterServer(int serverIf) throws RemoteException {
        }

        @Override // android.bluetooth.IBluetoothGatt
        public void serverConnect(int serverIf, String address, boolean isDirect, int transport) throws RemoteException {
        }

        @Override // android.bluetooth.IBluetoothGatt
        public void serverDisconnect(int serverIf, String address) throws RemoteException {
        }

        @Override // android.bluetooth.IBluetoothGatt
        public void serverSetPreferredPhy(int clientIf, String address, int txPhy, int rxPhy, int phyOptions) throws RemoteException {
        }

        @Override // android.bluetooth.IBluetoothGatt
        public void serverReadPhy(int clientIf, String address) throws RemoteException {
        }

        @Override // android.bluetooth.IBluetoothGatt
        public void addService(int serverIf, BluetoothGattService service) throws RemoteException {
        }

        @Override // android.bluetooth.IBluetoothGatt
        public void removeService(int serverIf, int handle) throws RemoteException {
        }

        @Override // android.bluetooth.IBluetoothGatt
        public void clearServices(int serverIf) throws RemoteException {
        }

        @Override // android.bluetooth.IBluetoothGatt
        public void sendResponse(int serverIf, String address, int requestId, int status, int offset, byte[] value) throws RemoteException {
        }

        @Override // android.bluetooth.IBluetoothGatt
        public void sendNotification(int serverIf, String address, int handle, boolean confirm, byte[] value) throws RemoteException {
        }

        @Override // android.bluetooth.IBluetoothGatt
        public void disconnectAll() throws RemoteException {
        }

        @Override // android.bluetooth.IBluetoothGatt
        public void unregAll() throws RemoteException {
        }

        @Override // android.bluetooth.IBluetoothGatt
        public int numHwTrackFiltersAvailable() throws RemoteException {
            return 0;
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* loaded from: classes.dex */
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
            if (iin != null && (iin instanceof IBluetoothGatt)) {
                return (IBluetoothGatt) iin;
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

        @Override // android.p007os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.p007os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            PendingIntent _arg0;
            boolean _arg2;
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    int[] _arg02 = data.createIntArray();
                    List<BluetoothDevice> _result = getDevicesMatchingConnectionStates(_arg02);
                    reply.writeNoException();
                    reply.writeTypedList(_result);
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    IScannerCallback _arg03 = IScannerCallback.Stub.asInterface(data.readStrongBinder());
                    WorkSource _arg1 = data.readInt() != 0 ? WorkSource.CREATOR.createFromParcel(data) : null;
                    registerScanner(_arg03, _arg1);
                    reply.writeNoException();
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg04 = data.readInt();
                    unregisterScanner(_arg04);
                    reply.writeNoException();
                    return true;
                case 4:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg05 = data.readInt();
                    ScanSettings _arg12 = data.readInt() != 0 ? ScanSettings.CREATOR.createFromParcel(data) : null;
                    List<ScanFilter> _arg22 = data.createTypedArrayList(ScanFilter.CREATOR);
                    ClassLoader cl = getClass().getClassLoader();
                    List _arg3 = data.readArrayList(cl);
                    String _arg4 = data.readString();
                    startScan(_arg05, _arg12, _arg22, _arg3, _arg4);
                    reply.writeNoException();
                    return true;
                case 5:
                    data.enforceInterface(DESCRIPTOR);
                    if (data.readInt() != 0) {
                        _arg0 = PendingIntent.CREATOR.createFromParcel(data);
                    } else {
                        _arg0 = null;
                    }
                    ScanSettings _arg13 = data.readInt() != 0 ? ScanSettings.CREATOR.createFromParcel(data) : null;
                    List<ScanFilter> _arg23 = data.createTypedArrayList(ScanFilter.CREATOR);
                    String _arg32 = data.readString();
                    startScanForIntent(_arg0, _arg13, _arg23, _arg32);
                    reply.writeNoException();
                    return true;
                case 6:
                    data.enforceInterface(DESCRIPTOR);
                    PendingIntent _arg06 = data.readInt() != 0 ? PendingIntent.CREATOR.createFromParcel(data) : null;
                    String _arg14 = data.readString();
                    stopScanForIntent(_arg06, _arg14);
                    reply.writeNoException();
                    return true;
                case 7:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg07 = data.readInt();
                    stopScan(_arg07);
                    reply.writeNoException();
                    return true;
                case 8:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg08 = data.readInt();
                    flushPendingBatchResults(_arg08);
                    reply.writeNoException();
                    return true;
                case 9:
                    data.enforceInterface(DESCRIPTOR);
                    AdvertisingSetParameters _arg09 = data.readInt() != 0 ? AdvertisingSetParameters.CREATOR.createFromParcel(data) : null;
                    AdvertiseData _arg15 = data.readInt() != 0 ? AdvertiseData.CREATOR.createFromParcel(data) : null;
                    AdvertiseData _arg24 = data.readInt() != 0 ? AdvertiseData.CREATOR.createFromParcel(data) : null;
                    PeriodicAdvertisingParameters _arg33 = data.readInt() != 0 ? PeriodicAdvertisingParameters.CREATOR.createFromParcel(data) : null;
                    AdvertiseData _arg42 = data.readInt() != 0 ? AdvertiseData.CREATOR.createFromParcel(data) : null;
                    int _arg5 = data.readInt();
                    int _arg6 = data.readInt();
                    IAdvertisingSetCallback _arg7 = IAdvertisingSetCallback.Stub.asInterface(data.readStrongBinder());
                    startAdvertisingSet(_arg09, _arg15, _arg24, _arg33, _arg42, _arg5, _arg6, _arg7);
                    reply.writeNoException();
                    return true;
                case 10:
                    data.enforceInterface(DESCRIPTOR);
                    IAdvertisingSetCallback _arg010 = IAdvertisingSetCallback.Stub.asInterface(data.readStrongBinder());
                    stopAdvertisingSet(_arg010);
                    reply.writeNoException();
                    return true;
                case 11:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg011 = data.readInt();
                    getOwnAddress(_arg011);
                    reply.writeNoException();
                    return true;
                case 12:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg012 = data.readInt();
                    _arg2 = data.readInt() != 0;
                    int _arg25 = data.readInt();
                    int _arg34 = data.readInt();
                    enableAdvertisingSet(_arg012, _arg2, _arg25, _arg34);
                    reply.writeNoException();
                    return true;
                case 13:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg013 = data.readInt();
                    AdvertiseData _arg16 = data.readInt() != 0 ? AdvertiseData.CREATOR.createFromParcel(data) : null;
                    setAdvertisingData(_arg013, _arg16);
                    reply.writeNoException();
                    return true;
                case 14:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg014 = data.readInt();
                    AdvertiseData _arg17 = data.readInt() != 0 ? AdvertiseData.CREATOR.createFromParcel(data) : null;
                    setScanResponseData(_arg014, _arg17);
                    reply.writeNoException();
                    return true;
                case 15:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg015 = data.readInt();
                    AdvertisingSetParameters _arg18 = data.readInt() != 0 ? AdvertisingSetParameters.CREATOR.createFromParcel(data) : null;
                    setAdvertisingParameters(_arg015, _arg18);
                    reply.writeNoException();
                    return true;
                case 16:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg016 = data.readInt();
                    PeriodicAdvertisingParameters _arg19 = data.readInt() != 0 ? PeriodicAdvertisingParameters.CREATOR.createFromParcel(data) : null;
                    setPeriodicAdvertisingParameters(_arg016, _arg19);
                    reply.writeNoException();
                    return true;
                case 17:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg017 = data.readInt();
                    AdvertiseData _arg110 = data.readInt() != 0 ? AdvertiseData.CREATOR.createFromParcel(data) : null;
                    setPeriodicAdvertisingData(_arg017, _arg110);
                    reply.writeNoException();
                    return true;
                case 18:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg018 = data.readInt();
                    _arg2 = data.readInt() != 0;
                    setPeriodicAdvertisingEnable(_arg018, _arg2);
                    reply.writeNoException();
                    return true;
                case 19:
                    data.enforceInterface(DESCRIPTOR);
                    ScanResult _arg019 = data.readInt() != 0 ? ScanResult.CREATOR.createFromParcel(data) : null;
                    int _arg111 = data.readInt();
                    int _arg26 = data.readInt();
                    IPeriodicAdvertisingCallback _arg35 = IPeriodicAdvertisingCallback.Stub.asInterface(data.readStrongBinder());
                    registerSync(_arg019, _arg111, _arg26, _arg35);
                    reply.writeNoException();
                    return true;
                case 20:
                    data.enforceInterface(DESCRIPTOR);
                    IPeriodicAdvertisingCallback _arg020 = IPeriodicAdvertisingCallback.Stub.asInterface(data.readStrongBinder());
                    unregisterSync(_arg020);
                    reply.writeNoException();
                    return true;
                case 21:
                    data.enforceInterface(DESCRIPTOR);
                    ParcelUuid _arg021 = data.readInt() != 0 ? ParcelUuid.CREATOR.createFromParcel(data) : null;
                    IBluetoothGattCallback _arg112 = IBluetoothGattCallback.Stub.asInterface(data.readStrongBinder());
                    registerClient(_arg021, _arg112);
                    reply.writeNoException();
                    return true;
                case 22:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg022 = data.readInt();
                    unregisterClient(_arg022);
                    reply.writeNoException();
                    return true;
                case 23:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg023 = data.readInt();
                    String _arg113 = data.readString();
                    boolean _arg27 = data.readInt() != 0;
                    int _arg36 = data.readInt();
                    boolean _arg43 = data.readInt() != 0;
                    int _arg52 = data.readInt();
                    clientConnect(_arg023, _arg113, _arg27, _arg36, _arg43, _arg52);
                    reply.writeNoException();
                    return true;
                case 24:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg024 = data.readInt();
                    String _arg114 = data.readString();
                    clientDisconnect(_arg024, _arg114);
                    reply.writeNoException();
                    return true;
                case 25:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg025 = data.readInt();
                    String _arg115 = data.readString();
                    int _arg28 = data.readInt();
                    int _arg37 = data.readInt();
                    int _arg44 = data.readInt();
                    clientSetPreferredPhy(_arg025, _arg115, _arg28, _arg37, _arg44);
                    reply.writeNoException();
                    return true;
                case 26:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg026 = data.readInt();
                    String _arg116 = data.readString();
                    clientReadPhy(_arg026, _arg116);
                    reply.writeNoException();
                    return true;
                case 27:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg027 = data.readInt();
                    String _arg117 = data.readString();
                    refreshDevice(_arg027, _arg117);
                    reply.writeNoException();
                    return true;
                case 28:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg028 = data.readInt();
                    String _arg118 = data.readString();
                    discoverServices(_arg028, _arg118);
                    reply.writeNoException();
                    return true;
                case 29:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg029 = data.readInt();
                    String _arg119 = data.readString();
                    discoverServiceByUuid(_arg029, _arg119, data.readInt() != 0 ? ParcelUuid.CREATOR.createFromParcel(data) : null);
                    reply.writeNoException();
                    return true;
                case 30:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg030 = data.readInt();
                    String _arg120 = data.readString();
                    int _arg29 = data.readInt();
                    int _arg38 = data.readInt();
                    readCharacteristic(_arg030, _arg120, _arg29, _arg38);
                    reply.writeNoException();
                    return true;
                case 31:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg031 = data.readInt();
                    String _arg121 = data.readString();
                    ParcelUuid _arg210 = data.readInt() != 0 ? ParcelUuid.CREATOR.createFromParcel(data) : null;
                    int _arg39 = data.readInt();
                    int _arg45 = data.readInt();
                    int _arg53 = data.readInt();
                    readUsingCharacteristicUuid(_arg031, _arg121, _arg210, _arg39, _arg45, _arg53);
                    reply.writeNoException();
                    return true;
                case 32:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg032 = data.readInt();
                    String _arg122 = data.readString();
                    int _arg211 = data.readInt();
                    int _arg310 = data.readInt();
                    int _arg46 = data.readInt();
                    byte[] _arg54 = data.createByteArray();
                    writeCharacteristic(_arg032, _arg122, _arg211, _arg310, _arg46, _arg54);
                    reply.writeNoException();
                    return true;
                case 33:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg033 = data.readInt();
                    String _arg123 = data.readString();
                    int _arg212 = data.readInt();
                    int _arg311 = data.readInt();
                    readDescriptor(_arg033, _arg123, _arg212, _arg311);
                    reply.writeNoException();
                    return true;
                case 34:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg034 = data.readInt();
                    String _arg124 = data.readString();
                    int _arg213 = data.readInt();
                    int _arg312 = data.readInt();
                    byte[] _arg47 = data.createByteArray();
                    writeDescriptor(_arg034, _arg124, _arg213, _arg312, _arg47);
                    reply.writeNoException();
                    return true;
                case 35:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg035 = data.readInt();
                    String _arg125 = data.readString();
                    int _arg214 = data.readInt();
                    _arg2 = data.readInt() != 0;
                    registerForNotification(_arg035, _arg125, _arg214, _arg2);
                    reply.writeNoException();
                    return true;
                case 36:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg036 = data.readInt();
                    String _arg126 = data.readString();
                    beginReliableWrite(_arg036, _arg126);
                    reply.writeNoException();
                    return true;
                case 37:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg037 = data.readInt();
                    String _arg127 = data.readString();
                    _arg2 = data.readInt() != 0;
                    endReliableWrite(_arg037, _arg127, _arg2);
                    reply.writeNoException();
                    return true;
                case 38:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg038 = data.readInt();
                    String _arg128 = data.readString();
                    readRemoteRssi(_arg038, _arg128);
                    reply.writeNoException();
                    return true;
                case 39:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg039 = data.readInt();
                    String _arg129 = data.readString();
                    configureMTU(_arg039, _arg129, data.readInt());
                    reply.writeNoException();
                    return true;
                case 40:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg040 = data.readInt();
                    String _arg130 = data.readString();
                    connectionParameterUpdate(_arg040, _arg130, data.readInt());
                    reply.writeNoException();
                    return true;
                case 41:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg041 = data.readInt();
                    String _arg131 = data.readString();
                    int _arg215 = data.readInt();
                    int _arg313 = data.readInt();
                    int _arg48 = data.readInt();
                    int _arg55 = data.readInt();
                    int _arg62 = data.readInt();
                    int _arg72 = data.readInt();
                    leConnectionUpdate(_arg041, _arg131, _arg215, _arg313, _arg48, _arg55, _arg62, _arg72);
                    reply.writeNoException();
                    return true;
                case 42:
                    data.enforceInterface(DESCRIPTOR);
                    ParcelUuid _arg042 = data.readInt() != 0 ? ParcelUuid.CREATOR.createFromParcel(data) : null;
                    IBluetoothGattServerCallback _arg132 = IBluetoothGattServerCallback.Stub.asInterface(data.readStrongBinder());
                    registerServer(_arg042, _arg132);
                    reply.writeNoException();
                    return true;
                case 43:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg043 = data.readInt();
                    unregisterServer(_arg043);
                    reply.writeNoException();
                    return true;
                case 44:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg044 = data.readInt();
                    String _arg133 = data.readString();
                    _arg2 = data.readInt() != 0;
                    int _arg314 = data.readInt();
                    serverConnect(_arg044, _arg133, _arg2, _arg314);
                    reply.writeNoException();
                    return true;
                case 45:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg045 = data.readInt();
                    String _arg134 = data.readString();
                    serverDisconnect(_arg045, _arg134);
                    reply.writeNoException();
                    return true;
                case 46:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg046 = data.readInt();
                    String _arg135 = data.readString();
                    int _arg216 = data.readInt();
                    int _arg315 = data.readInt();
                    int _arg49 = data.readInt();
                    serverSetPreferredPhy(_arg046, _arg135, _arg216, _arg315, _arg49);
                    reply.writeNoException();
                    return true;
                case 47:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg047 = data.readInt();
                    String _arg136 = data.readString();
                    serverReadPhy(_arg047, _arg136);
                    reply.writeNoException();
                    return true;
                case 48:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg048 = data.readInt();
                    BluetoothGattService _arg137 = data.readInt() != 0 ? BluetoothGattService.CREATOR.createFromParcel(data) : null;
                    addService(_arg048, _arg137);
                    reply.writeNoException();
                    return true;
                case 49:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg049 = data.readInt();
                    int _arg138 = data.readInt();
                    removeService(_arg049, _arg138);
                    reply.writeNoException();
                    return true;
                case 50:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg050 = data.readInt();
                    clearServices(_arg050);
                    reply.writeNoException();
                    return true;
                case 51:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg051 = data.readInt();
                    String _arg139 = data.readString();
                    int _arg217 = data.readInt();
                    int _arg316 = data.readInt();
                    int _arg410 = data.readInt();
                    byte[] _arg56 = data.createByteArray();
                    sendResponse(_arg051, _arg139, _arg217, _arg316, _arg410, _arg56);
                    reply.writeNoException();
                    return true;
                case 52:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg052 = data.readInt();
                    String _arg140 = data.readString();
                    int _arg218 = data.readInt();
                    boolean _arg317 = data.readInt() != 0;
                    byte[] _arg411 = data.createByteArray();
                    sendNotification(_arg052, _arg140, _arg218, _arg317, _arg411);
                    reply.writeNoException();
                    return true;
                case 53:
                    data.enforceInterface(DESCRIPTOR);
                    disconnectAll();
                    reply.writeNoException();
                    return true;
                case 54:
                    data.enforceInterface(DESCRIPTOR);
                    unregAll();
                    reply.writeNoException();
                    return true;
                case 55:
                    data.enforceInterface(DESCRIPTOR);
                    int _result2 = numHwTrackFiltersAvailable();
                    reply.writeNoException();
                    reply.writeInt(_result2);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* loaded from: classes.dex */
        private static class Proxy implements IBluetoothGatt {
            public static IBluetoothGatt sDefaultImpl;
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

            @Override // android.bluetooth.IBluetoothGatt
            public List<BluetoothDevice> getDevicesMatchingConnectionStates(int[] states) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeIntArray(states);
                    boolean _status = this.mRemote.transact(1, _data, _reply, 0);
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

            @Override // android.bluetooth.IBluetoothGatt
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
                    boolean _status = this.mRemote.transact(2, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registerScanner(callback, workSource);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.bluetooth.IBluetoothGatt
            public void unregisterScanner(int scannerId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(scannerId);
                    boolean _status = this.mRemote.transact(3, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unregisterScanner(scannerId);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.bluetooth.IBluetoothGatt
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
                    boolean _status = this.mRemote.transact(4, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().startScan(scannerId, settings, filters, scanStorages, callingPackage);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.bluetooth.IBluetoothGatt
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
                    boolean _status = this.mRemote.transact(5, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().startScanForIntent(intent, settings, filters, callingPackage);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.bluetooth.IBluetoothGatt
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
                    boolean _status = this.mRemote.transact(6, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().stopScanForIntent(intent, callingPackage);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.bluetooth.IBluetoothGatt
            public void stopScan(int scannerId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(scannerId);
                    boolean _status = this.mRemote.transact(7, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().stopScan(scannerId);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.bluetooth.IBluetoothGatt
            public void flushPendingBatchResults(int scannerId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(scannerId);
                    boolean _status = this.mRemote.transact(8, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().flushPendingBatchResults(scannerId);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.bluetooth.IBluetoothGatt
            public void startAdvertisingSet(AdvertisingSetParameters parameters, AdvertiseData advertiseData, AdvertiseData scanResponse, PeriodicAdvertisingParameters periodicParameters, AdvertiseData periodicData, int duration, int maxExtAdvEvents, IAdvertisingSetCallback callback) throws RemoteException {
                Parcel _reply;
                Parcel _data = Parcel.obtain();
                Parcel _reply2 = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parameters != null) {
                        try {
                            _data.writeInt(1);
                            parameters.writeToParcel(_data, 0);
                        } catch (Throwable th) {
                            th = th;
                            _reply = _reply2;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } else {
                        _data.writeInt(0);
                    }
                    if (advertiseData != null) {
                        _data.writeInt(1);
                        advertiseData.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (scanResponse != null) {
                        _data.writeInt(1);
                        scanResponse.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (periodicParameters != null) {
                        _data.writeInt(1);
                        periodicParameters.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (periodicData != null) {
                        _data.writeInt(1);
                        periodicData.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(duration);
                    _data.writeInt(maxExtAdvEvents);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    boolean _status = this.mRemote.transact(9, _data, _reply2, 0);
                    try {
                        if (_status || Stub.getDefaultImpl() == null) {
                            _reply2.readException();
                            _reply2.recycle();
                            _data.recycle();
                            return;
                        }
                        Stub.getDefaultImpl().startAdvertisingSet(parameters, advertiseData, scanResponse, periodicParameters, periodicData, duration, maxExtAdvEvents, callback);
                        _reply2.recycle();
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
                }
            }

            @Override // android.bluetooth.IBluetoothGatt
            public void stopAdvertisingSet(IAdvertisingSetCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    boolean _status = this.mRemote.transact(10, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().stopAdvertisingSet(callback);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.bluetooth.IBluetoothGatt
            public void getOwnAddress(int advertiserId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(advertiserId);
                    boolean _status = this.mRemote.transact(11, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().getOwnAddress(advertiserId);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.bluetooth.IBluetoothGatt
            public void enableAdvertisingSet(int advertiserId, boolean enable, int duration, int maxExtAdvEvents) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(advertiserId);
                    _data.writeInt(enable ? 1 : 0);
                    _data.writeInt(duration);
                    _data.writeInt(maxExtAdvEvents);
                    boolean _status = this.mRemote.transact(12, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().enableAdvertisingSet(advertiserId, enable, duration, maxExtAdvEvents);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.bluetooth.IBluetoothGatt
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
                    boolean _status = this.mRemote.transact(13, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setAdvertisingData(advertiserId, data);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.bluetooth.IBluetoothGatt
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
                    boolean _status = this.mRemote.transact(14, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setScanResponseData(advertiserId, data);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.bluetooth.IBluetoothGatt
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
                    boolean _status = this.mRemote.transact(15, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setAdvertisingParameters(advertiserId, parameters);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.bluetooth.IBluetoothGatt
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
                    boolean _status = this.mRemote.transact(16, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setPeriodicAdvertisingParameters(advertiserId, parameters);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.bluetooth.IBluetoothGatt
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
                    boolean _status = this.mRemote.transact(17, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setPeriodicAdvertisingData(advertiserId, data);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.bluetooth.IBluetoothGatt
            public void setPeriodicAdvertisingEnable(int advertiserId, boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(advertiserId);
                    _data.writeInt(enable ? 1 : 0);
                    boolean _status = this.mRemote.transact(18, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setPeriodicAdvertisingEnable(advertiserId, enable);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.bluetooth.IBluetoothGatt
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
                    boolean _status = this.mRemote.transact(19, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registerSync(scanResult, skip, timeout, callback);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.bluetooth.IBluetoothGatt
            public void unregisterSync(IPeriodicAdvertisingCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    boolean _status = this.mRemote.transact(20, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unregisterSync(callback);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.bluetooth.IBluetoothGatt
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
                    boolean _status = this.mRemote.transact(21, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registerClient(appId, callback);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.bluetooth.IBluetoothGatt
            public void unregisterClient(int clientIf) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(clientIf);
                    boolean _status = this.mRemote.transact(22, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unregisterClient(clientIf);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.bluetooth.IBluetoothGatt
            public void clientConnect(int clientIf, String address, boolean isDirect, int transport, boolean opportunistic, int phy) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeInt(clientIf);
                    } catch (Throwable th) {
                        th = th;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeString(address);
                    } catch (Throwable th2) {
                        th = th2;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(isDirect ? 1 : 0);
                    } catch (Throwable th3) {
                        th = th3;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(transport);
                    } catch (Throwable th4) {
                        th = th4;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(opportunistic ? 1 : 0);
                    } catch (Throwable th5) {
                        th = th5;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(phy);
                        boolean _status = this.mRemote.transact(23, _data, _reply, 0);
                        if (!_status && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().clientConnect(clientIf, address, isDirect, transport, opportunistic, phy);
                            _reply.recycle();
                            _data.recycle();
                            return;
                        }
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                    } catch (Throwable th6) {
                        th = th6;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th7) {
                    th = th7;
                }
            }

            @Override // android.bluetooth.IBluetoothGatt
            public void clientDisconnect(int clientIf, String address) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(clientIf);
                    _data.writeString(address);
                    boolean _status = this.mRemote.transact(24, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().clientDisconnect(clientIf, address);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.bluetooth.IBluetoothGatt
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
                    boolean _status = this.mRemote.transact(25, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().clientSetPreferredPhy(clientIf, address, txPhy, rxPhy, phyOptions);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.bluetooth.IBluetoothGatt
            public void clientReadPhy(int clientIf, String address) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(clientIf);
                    _data.writeString(address);
                    boolean _status = this.mRemote.transact(26, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().clientReadPhy(clientIf, address);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.bluetooth.IBluetoothGatt
            public void refreshDevice(int clientIf, String address) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(clientIf);
                    _data.writeString(address);
                    boolean _status = this.mRemote.transact(27, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().refreshDevice(clientIf, address);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.bluetooth.IBluetoothGatt
            public void discoverServices(int clientIf, String address) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(clientIf);
                    _data.writeString(address);
                    boolean _status = this.mRemote.transact(28, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().discoverServices(clientIf, address);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.bluetooth.IBluetoothGatt
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
                    boolean _status = this.mRemote.transact(29, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().discoverServiceByUuid(clientIf, address, uuid);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.bluetooth.IBluetoothGatt
            public void readCharacteristic(int clientIf, String address, int handle, int authReq) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(clientIf);
                    _data.writeString(address);
                    _data.writeInt(handle);
                    _data.writeInt(authReq);
                    boolean _status = this.mRemote.transact(30, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().readCharacteristic(clientIf, address, handle, authReq);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.bluetooth.IBluetoothGatt
            public void readUsingCharacteristicUuid(int clientIf, String address, ParcelUuid uuid, int startHandle, int endHandle, int authReq) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                } catch (Throwable th) {
                    th = th;
                }
                try {
                    _data.writeInt(clientIf);
                } catch (Throwable th2) {
                    th = th2;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeString(address);
                    if (uuid != null) {
                        _data.writeInt(1);
                        uuid.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                } catch (Throwable th3) {
                    th = th3;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeInt(startHandle);
                } catch (Throwable th4) {
                    th = th4;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeInt(endHandle);
                    try {
                        _data.writeInt(authReq);
                        boolean _status = this.mRemote.transact(31, _data, _reply, 0);
                        if (!_status && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().readUsingCharacteristicUuid(clientIf, address, uuid, startHandle, endHandle, authReq);
                            _reply.recycle();
                            _data.recycle();
                            return;
                        }
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                    } catch (Throwable th5) {
                        th = th5;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th6) {
                    th = th6;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            @Override // android.bluetooth.IBluetoothGatt
            public void writeCharacteristic(int clientIf, String address, int handle, int writeType, int authReq, byte[] value) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeInt(clientIf);
                    } catch (Throwable th) {
                        th = th;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeString(address);
                    } catch (Throwable th2) {
                        th = th2;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(handle);
                    } catch (Throwable th3) {
                        th = th3;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(writeType);
                    } catch (Throwable th4) {
                        th = th4;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(authReq);
                    } catch (Throwable th5) {
                        th = th5;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeByteArray(value);
                        boolean _status = this.mRemote.transact(32, _data, _reply, 0);
                        if (!_status && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().writeCharacteristic(clientIf, address, handle, writeType, authReq, value);
                            _reply.recycle();
                            _data.recycle();
                            return;
                        }
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                    } catch (Throwable th6) {
                        th = th6;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th7) {
                    th = th7;
                }
            }

            @Override // android.bluetooth.IBluetoothGatt
            public void readDescriptor(int clientIf, String address, int handle, int authReq) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(clientIf);
                    _data.writeString(address);
                    _data.writeInt(handle);
                    _data.writeInt(authReq);
                    boolean _status = this.mRemote.transact(33, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().readDescriptor(clientIf, address, handle, authReq);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.bluetooth.IBluetoothGatt
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
                    boolean _status = this.mRemote.transact(34, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().writeDescriptor(clientIf, address, handle, authReq, value);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.bluetooth.IBluetoothGatt
            public void registerForNotification(int clientIf, String address, int handle, boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(clientIf);
                    _data.writeString(address);
                    _data.writeInt(handle);
                    _data.writeInt(enable ? 1 : 0);
                    boolean _status = this.mRemote.transact(35, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registerForNotification(clientIf, address, handle, enable);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.bluetooth.IBluetoothGatt
            public void beginReliableWrite(int clientIf, String address) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(clientIf);
                    _data.writeString(address);
                    boolean _status = this.mRemote.transact(36, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().beginReliableWrite(clientIf, address);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.bluetooth.IBluetoothGatt
            public void endReliableWrite(int clientIf, String address, boolean execute) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(clientIf);
                    _data.writeString(address);
                    _data.writeInt(execute ? 1 : 0);
                    boolean _status = this.mRemote.transact(37, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().endReliableWrite(clientIf, address, execute);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.bluetooth.IBluetoothGatt
            public void readRemoteRssi(int clientIf, String address) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(clientIf);
                    _data.writeString(address);
                    boolean _status = this.mRemote.transact(38, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().readRemoteRssi(clientIf, address);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.bluetooth.IBluetoothGatt
            public void configureMTU(int clientIf, String address, int mtu) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(clientIf);
                    _data.writeString(address);
                    _data.writeInt(mtu);
                    boolean _status = this.mRemote.transact(39, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().configureMTU(clientIf, address, mtu);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.bluetooth.IBluetoothGatt
            public void connectionParameterUpdate(int clientIf, String address, int connectionPriority) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(clientIf);
                    _data.writeString(address);
                    _data.writeInt(connectionPriority);
                    boolean _status = this.mRemote.transact(40, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().connectionParameterUpdate(clientIf, address, connectionPriority);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.bluetooth.IBluetoothGatt
            public void leConnectionUpdate(int clientIf, String address, int minInterval, int maxInterval, int slaveLatency, int supervisionTimeout, int minConnectionEventLen, int maxConnectionEventLen) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                } catch (Throwable th) {
                    th = th;
                }
                try {
                    _data.writeInt(clientIf);
                } catch (Throwable th2) {
                    th = th2;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeString(address);
                } catch (Throwable th3) {
                    th = th3;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeInt(minInterval);
                } catch (Throwable th4) {
                    th = th4;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeInt(maxInterval);
                    _data.writeInt(slaveLatency);
                    _data.writeInt(supervisionTimeout);
                    _data.writeInt(minConnectionEventLen);
                    _data.writeInt(maxConnectionEventLen);
                    boolean _status = this.mRemote.transact(41, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().leConnectionUpdate(clientIf, address, minInterval, maxInterval, slaveLatency, supervisionTimeout, minConnectionEventLen, maxConnectionEventLen);
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    _reply.readException();
                    _reply.recycle();
                    _data.recycle();
                } catch (Throwable th5) {
                    th = th5;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            @Override // android.bluetooth.IBluetoothGatt
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
                    boolean _status = this.mRemote.transact(42, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registerServer(appId, callback);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.bluetooth.IBluetoothGatt
            public void unregisterServer(int serverIf) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(serverIf);
                    boolean _status = this.mRemote.transact(43, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unregisterServer(serverIf);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.bluetooth.IBluetoothGatt
            public void serverConnect(int serverIf, String address, boolean isDirect, int transport) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(serverIf);
                    _data.writeString(address);
                    _data.writeInt(isDirect ? 1 : 0);
                    _data.writeInt(transport);
                    boolean _status = this.mRemote.transact(44, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().serverConnect(serverIf, address, isDirect, transport);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.bluetooth.IBluetoothGatt
            public void serverDisconnect(int serverIf, String address) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(serverIf);
                    _data.writeString(address);
                    boolean _status = this.mRemote.transact(45, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().serverDisconnect(serverIf, address);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.bluetooth.IBluetoothGatt
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
                    boolean _status = this.mRemote.transact(46, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().serverSetPreferredPhy(clientIf, address, txPhy, rxPhy, phyOptions);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.bluetooth.IBluetoothGatt
            public void serverReadPhy(int clientIf, String address) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(clientIf);
                    _data.writeString(address);
                    boolean _status = this.mRemote.transact(47, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().serverReadPhy(clientIf, address);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.bluetooth.IBluetoothGatt
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
                    boolean _status = this.mRemote.transact(48, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().addService(serverIf, service);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.bluetooth.IBluetoothGatt
            public void removeService(int serverIf, int handle) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(serverIf);
                    _data.writeInt(handle);
                    boolean _status = this.mRemote.transact(49, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeService(serverIf, handle);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.bluetooth.IBluetoothGatt
            public void clearServices(int serverIf) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(serverIf);
                    boolean _status = this.mRemote.transact(50, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().clearServices(serverIf);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.bluetooth.IBluetoothGatt
            public void sendResponse(int serverIf, String address, int requestId, int status, int offset, byte[] value) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeInt(serverIf);
                    } catch (Throwable th) {
                        th = th;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeString(address);
                    } catch (Throwable th2) {
                        th = th2;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(requestId);
                    } catch (Throwable th3) {
                        th = th3;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(status);
                    } catch (Throwable th4) {
                        th = th4;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(offset);
                    } catch (Throwable th5) {
                        th = th5;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeByteArray(value);
                        boolean _status = this.mRemote.transact(51, _data, _reply, 0);
                        if (!_status && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().sendResponse(serverIf, address, requestId, status, offset, value);
                            _reply.recycle();
                            _data.recycle();
                            return;
                        }
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                    } catch (Throwable th6) {
                        th = th6;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th7) {
                    th = th7;
                }
            }

            @Override // android.bluetooth.IBluetoothGatt
            public void sendNotification(int serverIf, String address, int handle, boolean confirm, byte[] value) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(serverIf);
                    _data.writeString(address);
                    _data.writeInt(handle);
                    _data.writeInt(confirm ? 1 : 0);
                    _data.writeByteArray(value);
                    boolean _status = this.mRemote.transact(52, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().sendNotification(serverIf, address, handle, confirm, value);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.bluetooth.IBluetoothGatt
            public void disconnectAll() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(53, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().disconnectAll();
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.bluetooth.IBluetoothGatt
            public void unregAll() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(54, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unregAll();
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.bluetooth.IBluetoothGatt
            public int numHwTrackFiltersAvailable() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(55, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().numHwTrackFiltersAvailable();
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IBluetoothGatt impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static IBluetoothGatt getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
