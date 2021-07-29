package android.bluetooth;

import android.bluetooth.IBluetoothSocketManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelUuid;
import android.os.RemoteException;
import android.os.ResultReceiver;

public interface IBluetooth extends IInterface {
    boolean cancelBondProcess(BluetoothDevice bluetoothDevice) throws RemoteException;

    boolean cancelDiscovery() throws RemoteException;

    boolean createBond(BluetoothDevice bluetoothDevice, int i) throws RemoteException;

    boolean createBondOutOfBand(BluetoothDevice bluetoothDevice, int i, OobData oobData) throws RemoteException;

    boolean disable() throws RemoteException;

    boolean enable() throws RemoteException;

    boolean enableNoAutoConnect() throws RemoteException;

    boolean factoryReset() throws RemoteException;

    boolean fetchRemoteUuids(BluetoothDevice bluetoothDevice) throws RemoteException;

    int getAdapterConnectionState() throws RemoteException;

    String getAddress() throws RemoteException;

    int getBatteryLevel(BluetoothDevice bluetoothDevice) throws RemoteException;

    BluetoothClass getBluetoothClass() throws RemoteException;

    int getBondState(BluetoothDevice bluetoothDevice) throws RemoteException;

    BluetoothDevice[] getBondedDevices() throws RemoteException;

    int getConnectionState(BluetoothDevice bluetoothDevice) throws RemoteException;

    int getDiscoverableTimeout() throws RemoteException;

    long getDiscoveryEndMillis() throws RemoteException;

    int getIoCapability() throws RemoteException;

    int getLeIoCapability() throws RemoteException;

    int getLeMaximumAdvertisingDataLength() throws RemoteException;

    int getMaxConnectedAudioDevices() throws RemoteException;

    int getMessageAccessPermission(BluetoothDevice bluetoothDevice) throws RemoteException;

    byte[] getMetadata(BluetoothDevice bluetoothDevice, int i) throws RemoteException;

    String getName() throws RemoteException;

    int getPhonebookAccessPermission(BluetoothDevice bluetoothDevice) throws RemoteException;

    int getProfileConnectionState(int i) throws RemoteException;

    String getRemoteAlias(BluetoothDevice bluetoothDevice) throws RemoteException;

    int getRemoteClass(BluetoothDevice bluetoothDevice) throws RemoteException;

    String getRemoteName(BluetoothDevice bluetoothDevice) throws RemoteException;

    int getRemoteType(BluetoothDevice bluetoothDevice) throws RemoteException;

    ParcelUuid[] getRemoteUuids(BluetoothDevice bluetoothDevice) throws RemoteException;

    int getScanMode() throws RemoteException;

    boolean getSilenceMode(BluetoothDevice bluetoothDevice) throws RemoteException;

    int getSimAccessPermission(BluetoothDevice bluetoothDevice) throws RemoteException;

    IBluetoothSocketManager getSocketManager() throws RemoteException;

    int getSocketOpt(int i, int i2, int i3, byte[] bArr) throws RemoteException;

    int getState() throws RemoteException;

    long getSupportedProfiles() throws RemoteException;

    String getTwsPlusPeerAddress(BluetoothDevice bluetoothDevice) throws RemoteException;

    ParcelUuid[] getUuids() throws RemoteException;

    boolean isActivityAndEnergyReportingSupported() throws RemoteException;

    boolean isBondingInitiatedLocally(BluetoothDevice bluetoothDevice) throws RemoteException;

    boolean isDiscovering() throws RemoteException;

    boolean isEnabled() throws RemoteException;

    boolean isLe2MPhySupported() throws RemoteException;

    boolean isLeCodedPhySupported() throws RemoteException;

    boolean isLeExtendedAdvertisingSupported() throws RemoteException;

    boolean isLePeriodicAdvertisingSupported() throws RemoteException;

    boolean isMultiAdvertisementSupported() throws RemoteException;

    boolean isOffloadedFilteringSupported() throws RemoteException;

    boolean isOffloadedScanBatchingSupported() throws RemoteException;

    boolean isTwsPlusDevice(BluetoothDevice bluetoothDevice) throws RemoteException;

    void onBrEdrDown() throws RemoteException;

    void onLeServiceUp() throws RemoteException;

    void registerCallback(IBluetoothCallback iBluetoothCallback) throws RemoteException;

    boolean registerMetadataListener(IBluetoothMetadataListener iBluetoothMetadataListener, BluetoothDevice bluetoothDevice) throws RemoteException;

    boolean removeBond(BluetoothDevice bluetoothDevice) throws RemoteException;

    BluetoothActivityEnergyInfo reportActivityInfo() throws RemoteException;

    void requestActivityInfo(ResultReceiver resultReceiver) throws RemoteException;

    boolean sdpSearch(BluetoothDevice bluetoothDevice, ParcelUuid parcelUuid) throws RemoteException;

    void sendConnectionStateChange(BluetoothDevice bluetoothDevice, int i, int i2, int i3) throws RemoteException;

    boolean setBluetoothClass(BluetoothClass bluetoothClass) throws RemoteException;

    void setBondingInitiatedLocally(BluetoothDevice bluetoothDevice, boolean z) throws RemoteException;

    boolean setDiscoverableTimeout(int i) throws RemoteException;

    boolean setIoCapability(int i) throws RemoteException;

    boolean setLeIoCapability(int i) throws RemoteException;

    boolean setMessageAccessPermission(BluetoothDevice bluetoothDevice, int i) throws RemoteException;

    boolean setMetadata(BluetoothDevice bluetoothDevice, int i, byte[] bArr) throws RemoteException;

    boolean setName(String str) throws RemoteException;

    boolean setPairingConfirmation(BluetoothDevice bluetoothDevice, boolean z) throws RemoteException;

    boolean setPasskey(BluetoothDevice bluetoothDevice, boolean z, int i, byte[] bArr) throws RemoteException;

    boolean setPhonebookAccessPermission(BluetoothDevice bluetoothDevice, int i) throws RemoteException;

    boolean setPin(BluetoothDevice bluetoothDevice, boolean z, int i, byte[] bArr) throws RemoteException;

    boolean setRemoteAlias(BluetoothDevice bluetoothDevice, String str) throws RemoteException;

    boolean setScanMode(int i, int i2) throws RemoteException;

    boolean setSilenceMode(BluetoothDevice bluetoothDevice, boolean z) throws RemoteException;

    boolean setSimAccessPermission(BluetoothDevice bluetoothDevice, int i) throws RemoteException;

    int setSocketOpt(int i, int i2, int i3, byte[] bArr, int i4) throws RemoteException;

    boolean startDiscovery(String str) throws RemoteException;

    void unregisterCallback(IBluetoothCallback iBluetoothCallback) throws RemoteException;

    boolean unregisterMetadataListener(BluetoothDevice bluetoothDevice) throws RemoteException;

    void updateQuietModeStatus(boolean z) throws RemoteException;

    public static class Default implements IBluetooth {
        public boolean isEnabled() throws RemoteException {
            return false;
        }

        public int getState() throws RemoteException {
            return 0;
        }

        public boolean enable() throws RemoteException {
            return false;
        }

        public boolean enableNoAutoConnect() throws RemoteException {
            return false;
        }

        public boolean disable() throws RemoteException {
            return false;
        }

        public String getAddress() throws RemoteException {
            return null;
        }

        public ParcelUuid[] getUuids() throws RemoteException {
            return null;
        }

        public boolean setName(String name) throws RemoteException {
            return false;
        }

        public String getName() throws RemoteException {
            return null;
        }

        public BluetoothClass getBluetoothClass() throws RemoteException {
            return null;
        }

        public boolean setBluetoothClass(BluetoothClass bluetoothClass) throws RemoteException {
            return false;
        }

        public int getIoCapability() throws RemoteException {
            return 0;
        }

        public boolean setIoCapability(int capability) throws RemoteException {
            return false;
        }

        public int getLeIoCapability() throws RemoteException {
            return 0;
        }

        public boolean setLeIoCapability(int capability) throws RemoteException {
            return false;
        }

        public int getScanMode() throws RemoteException {
            return 0;
        }

        public boolean setScanMode(int mode, int duration) throws RemoteException {
            return false;
        }

        public int getDiscoverableTimeout() throws RemoteException {
            return 0;
        }

        public boolean setDiscoverableTimeout(int timeout) throws RemoteException {
            return false;
        }

        public boolean startDiscovery(String callingPackage) throws RemoteException {
            return false;
        }

        public boolean cancelDiscovery() throws RemoteException {
            return false;
        }

        public boolean isDiscovering() throws RemoteException {
            return false;
        }

        public long getDiscoveryEndMillis() throws RemoteException {
            return 0;
        }

        public int getAdapterConnectionState() throws RemoteException {
            return 0;
        }

        public int getProfileConnectionState(int profile) throws RemoteException {
            return 0;
        }

        public BluetoothDevice[] getBondedDevices() throws RemoteException {
            return null;
        }

        public boolean createBond(BluetoothDevice device, int transport) throws RemoteException {
            return false;
        }

        public boolean createBondOutOfBand(BluetoothDevice device, int transport, OobData oobData) throws RemoteException {
            return false;
        }

        public boolean cancelBondProcess(BluetoothDevice device) throws RemoteException {
            return false;
        }

        public boolean removeBond(BluetoothDevice device) throws RemoteException {
            return false;
        }

        public int getBondState(BluetoothDevice device) throws RemoteException {
            return 0;
        }

        public boolean isBondingInitiatedLocally(BluetoothDevice device) throws RemoteException {
            return false;
        }

        public void setBondingInitiatedLocally(BluetoothDevice devicei, boolean localInitiated) throws RemoteException {
        }

        public long getSupportedProfiles() throws RemoteException {
            return 0;
        }

        public int getConnectionState(BluetoothDevice device) throws RemoteException {
            return 0;
        }

        public String getRemoteName(BluetoothDevice device) throws RemoteException {
            return null;
        }

        public int getRemoteType(BluetoothDevice device) throws RemoteException {
            return 0;
        }

        public String getRemoteAlias(BluetoothDevice device) throws RemoteException {
            return null;
        }

        public boolean setRemoteAlias(BluetoothDevice device, String name) throws RemoteException {
            return false;
        }

        public int getRemoteClass(BluetoothDevice device) throws RemoteException {
            return 0;
        }

        public ParcelUuid[] getRemoteUuids(BluetoothDevice device) throws RemoteException {
            return null;
        }

        public boolean fetchRemoteUuids(BluetoothDevice device) throws RemoteException {
            return false;
        }

        public boolean sdpSearch(BluetoothDevice device, ParcelUuid uuid) throws RemoteException {
            return false;
        }

        public int getBatteryLevel(BluetoothDevice device) throws RemoteException {
            return 0;
        }

        public int getMaxConnectedAudioDevices() throws RemoteException {
            return 0;
        }

        public boolean isTwsPlusDevice(BluetoothDevice device) throws RemoteException {
            return false;
        }

        public String getTwsPlusPeerAddress(BluetoothDevice device) throws RemoteException {
            return null;
        }

        public boolean setPin(BluetoothDevice device, boolean accept, int len, byte[] pinCode) throws RemoteException {
            return false;
        }

        public boolean setPasskey(BluetoothDevice device, boolean accept, int len, byte[] passkey) throws RemoteException {
            return false;
        }

        public boolean setPairingConfirmation(BluetoothDevice device, boolean accept) throws RemoteException {
            return false;
        }

        public int getPhonebookAccessPermission(BluetoothDevice device) throws RemoteException {
            return 0;
        }

        public boolean setSilenceMode(BluetoothDevice device, boolean silence) throws RemoteException {
            return false;
        }

        public boolean getSilenceMode(BluetoothDevice device) throws RemoteException {
            return false;
        }

        public boolean setPhonebookAccessPermission(BluetoothDevice device, int value) throws RemoteException {
            return false;
        }

        public int getMessageAccessPermission(BluetoothDevice device) throws RemoteException {
            return 0;
        }

        public boolean setMessageAccessPermission(BluetoothDevice device, int value) throws RemoteException {
            return false;
        }

        public int getSimAccessPermission(BluetoothDevice device) throws RemoteException {
            return 0;
        }

        public boolean setSimAccessPermission(BluetoothDevice device, int value) throws RemoteException {
            return false;
        }

        public void sendConnectionStateChange(BluetoothDevice device, int profile, int state, int prevState) throws RemoteException {
        }

        public void registerCallback(IBluetoothCallback callback) throws RemoteException {
        }

        public void unregisterCallback(IBluetoothCallback callback) throws RemoteException {
        }

        public IBluetoothSocketManager getSocketManager() throws RemoteException {
            return null;
        }

        public boolean factoryReset() throws RemoteException {
            return false;
        }

        public boolean isMultiAdvertisementSupported() throws RemoteException {
            return false;
        }

        public boolean isOffloadedFilteringSupported() throws RemoteException {
            return false;
        }

        public boolean isOffloadedScanBatchingSupported() throws RemoteException {
            return false;
        }

        public boolean isActivityAndEnergyReportingSupported() throws RemoteException {
            return false;
        }

        public boolean isLe2MPhySupported() throws RemoteException {
            return false;
        }

        public boolean isLeCodedPhySupported() throws RemoteException {
            return false;
        }

        public boolean isLeExtendedAdvertisingSupported() throws RemoteException {
            return false;
        }

        public boolean isLePeriodicAdvertisingSupported() throws RemoteException {
            return false;
        }

        public int getLeMaximumAdvertisingDataLength() throws RemoteException {
            return 0;
        }

        public BluetoothActivityEnergyInfo reportActivityInfo() throws RemoteException {
            return null;
        }

        public boolean registerMetadataListener(IBluetoothMetadataListener listener, BluetoothDevice device) throws RemoteException {
            return false;
        }

        public boolean unregisterMetadataListener(BluetoothDevice device) throws RemoteException {
            return false;
        }

        public boolean setMetadata(BluetoothDevice device, int key, byte[] value) throws RemoteException {
            return false;
        }

        public byte[] getMetadata(BluetoothDevice device, int key) throws RemoteException {
            return null;
        }

        public void requestActivityInfo(ResultReceiver result) throws RemoteException {
        }

        public void onLeServiceUp() throws RemoteException {
        }

        public void updateQuietModeStatus(boolean quietMode) throws RemoteException {
        }

        public void onBrEdrDown() throws RemoteException {
        }

        public int setSocketOpt(int type, int port, int optionName, byte[] optionVal, int optionLen) throws RemoteException {
            return 0;
        }

        public int getSocketOpt(int type, int port, int optionName, byte[] optionVal) throws RemoteException {
            return 0;
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IBluetooth {
        private static final String DESCRIPTOR = "android.bluetooth.IBluetooth";
        static final int TRANSACTION_cancelBondProcess = 29;
        static final int TRANSACTION_cancelDiscovery = 21;
        static final int TRANSACTION_createBond = 27;
        static final int TRANSACTION_createBondOutOfBand = 28;
        static final int TRANSACTION_disable = 5;
        static final int TRANSACTION_enable = 3;
        static final int TRANSACTION_enableNoAutoConnect = 4;
        static final int TRANSACTION_factoryReset = 63;
        static final int TRANSACTION_fetchRemoteUuids = 42;
        static final int TRANSACTION_getAdapterConnectionState = 24;
        static final int TRANSACTION_getAddress = 6;
        static final int TRANSACTION_getBatteryLevel = 44;
        static final int TRANSACTION_getBluetoothClass = 10;
        static final int TRANSACTION_getBondState = 31;
        static final int TRANSACTION_getBondedDevices = 26;
        static final int TRANSACTION_getConnectionState = 35;
        static final int TRANSACTION_getDiscoverableTimeout = 18;
        static final int TRANSACTION_getDiscoveryEndMillis = 23;
        static final int TRANSACTION_getIoCapability = 12;
        static final int TRANSACTION_getLeIoCapability = 14;
        static final int TRANSACTION_getLeMaximumAdvertisingDataLength = 72;
        static final int TRANSACTION_getMaxConnectedAudioDevices = 45;
        static final int TRANSACTION_getMessageAccessPermission = 55;
        static final int TRANSACTION_getMetadata = 77;
        static final int TRANSACTION_getName = 9;
        static final int TRANSACTION_getPhonebookAccessPermission = 51;
        static final int TRANSACTION_getProfileConnectionState = 25;
        static final int TRANSACTION_getRemoteAlias = 38;
        static final int TRANSACTION_getRemoteClass = 40;
        static final int TRANSACTION_getRemoteName = 36;
        static final int TRANSACTION_getRemoteType = 37;
        static final int TRANSACTION_getRemoteUuids = 41;
        static final int TRANSACTION_getScanMode = 16;
        static final int TRANSACTION_getSilenceMode = 53;
        static final int TRANSACTION_getSimAccessPermission = 57;
        static final int TRANSACTION_getSocketManager = 62;
        static final int TRANSACTION_getSocketOpt = 83;
        static final int TRANSACTION_getState = 2;
        static final int TRANSACTION_getSupportedProfiles = 34;
        static final int TRANSACTION_getTwsPlusPeerAddress = 47;
        static final int TRANSACTION_getUuids = 7;
        static final int TRANSACTION_isActivityAndEnergyReportingSupported = 67;
        static final int TRANSACTION_isBondingInitiatedLocally = 32;
        static final int TRANSACTION_isDiscovering = 22;
        static final int TRANSACTION_isEnabled = 1;
        static final int TRANSACTION_isLe2MPhySupported = 68;
        static final int TRANSACTION_isLeCodedPhySupported = 69;
        static final int TRANSACTION_isLeExtendedAdvertisingSupported = 70;
        static final int TRANSACTION_isLePeriodicAdvertisingSupported = 71;
        static final int TRANSACTION_isMultiAdvertisementSupported = 64;
        static final int TRANSACTION_isOffloadedFilteringSupported = 65;
        static final int TRANSACTION_isOffloadedScanBatchingSupported = 66;
        static final int TRANSACTION_isTwsPlusDevice = 46;
        static final int TRANSACTION_onBrEdrDown = 81;
        static final int TRANSACTION_onLeServiceUp = 79;
        static final int TRANSACTION_registerCallback = 60;
        static final int TRANSACTION_registerMetadataListener = 74;
        static final int TRANSACTION_removeBond = 30;
        static final int TRANSACTION_reportActivityInfo = 73;
        static final int TRANSACTION_requestActivityInfo = 78;
        static final int TRANSACTION_sdpSearch = 43;
        static final int TRANSACTION_sendConnectionStateChange = 59;
        static final int TRANSACTION_setBluetoothClass = 11;
        static final int TRANSACTION_setBondingInitiatedLocally = 33;
        static final int TRANSACTION_setDiscoverableTimeout = 19;
        static final int TRANSACTION_setIoCapability = 13;
        static final int TRANSACTION_setLeIoCapability = 15;
        static final int TRANSACTION_setMessageAccessPermission = 56;
        static final int TRANSACTION_setMetadata = 76;
        static final int TRANSACTION_setName = 8;
        static final int TRANSACTION_setPairingConfirmation = 50;
        static final int TRANSACTION_setPasskey = 49;
        static final int TRANSACTION_setPhonebookAccessPermission = 54;
        static final int TRANSACTION_setPin = 48;
        static final int TRANSACTION_setRemoteAlias = 39;
        static final int TRANSACTION_setScanMode = 17;
        static final int TRANSACTION_setSilenceMode = 52;
        static final int TRANSACTION_setSimAccessPermission = 58;
        static final int TRANSACTION_setSocketOpt = 82;
        static final int TRANSACTION_startDiscovery = 20;
        static final int TRANSACTION_unregisterCallback = 61;
        static final int TRANSACTION_unregisterMetadataListener = 75;
        static final int TRANSACTION_updateQuietModeStatus = 80;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IBluetooth asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IBluetooth)) {
                return new Proxy(obj);
            }
            return (IBluetooth) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "isEnabled";
                case 2:
                    return "getState";
                case 3:
                    return "enable";
                case 4:
                    return "enableNoAutoConnect";
                case 5:
                    return "disable";
                case 6:
                    return "getAddress";
                case 7:
                    return "getUuids";
                case 8:
                    return "setName";
                case 9:
                    return "getName";
                case 10:
                    return "getBluetoothClass";
                case 11:
                    return "setBluetoothClass";
                case 12:
                    return "getIoCapability";
                case 13:
                    return "setIoCapability";
                case 14:
                    return "getLeIoCapability";
                case 15:
                    return "setLeIoCapability";
                case 16:
                    return "getScanMode";
                case 17:
                    return "setScanMode";
                case 18:
                    return "getDiscoverableTimeout";
                case 19:
                    return "setDiscoverableTimeout";
                case 20:
                    return "startDiscovery";
                case 21:
                    return "cancelDiscovery";
                case 22:
                    return "isDiscovering";
                case 23:
                    return "getDiscoveryEndMillis";
                case 24:
                    return "getAdapterConnectionState";
                case 25:
                    return "getProfileConnectionState";
                case 26:
                    return "getBondedDevices";
                case 27:
                    return "createBond";
                case 28:
                    return "createBondOutOfBand";
                case 29:
                    return "cancelBondProcess";
                case 30:
                    return "removeBond";
                case 31:
                    return "getBondState";
                case 32:
                    return "isBondingInitiatedLocally";
                case 33:
                    return "setBondingInitiatedLocally";
                case 34:
                    return "getSupportedProfiles";
                case 35:
                    return "getConnectionState";
                case 36:
                    return "getRemoteName";
                case 37:
                    return "getRemoteType";
                case 38:
                    return "getRemoteAlias";
                case 39:
                    return "setRemoteAlias";
                case 40:
                    return "getRemoteClass";
                case 41:
                    return "getRemoteUuids";
                case 42:
                    return "fetchRemoteUuids";
                case 43:
                    return "sdpSearch";
                case 44:
                    return "getBatteryLevel";
                case 45:
                    return "getMaxConnectedAudioDevices";
                case 46:
                    return "isTwsPlusDevice";
                case 47:
                    return "getTwsPlusPeerAddress";
                case 48:
                    return "setPin";
                case 49:
                    return "setPasskey";
                case 50:
                    return "setPairingConfirmation";
                case 51:
                    return "getPhonebookAccessPermission";
                case 52:
                    return "setSilenceMode";
                case 53:
                    return "getSilenceMode";
                case 54:
                    return "setPhonebookAccessPermission";
                case 55:
                    return "getMessageAccessPermission";
                case 56:
                    return "setMessageAccessPermission";
                case 57:
                    return "getSimAccessPermission";
                case 58:
                    return "setSimAccessPermission";
                case 59:
                    return "sendConnectionStateChange";
                case 60:
                    return "registerCallback";
                case 61:
                    return "unregisterCallback";
                case 62:
                    return "getSocketManager";
                case 63:
                    return "factoryReset";
                case 64:
                    return "isMultiAdvertisementSupported";
                case 65:
                    return "isOffloadedFilteringSupported";
                case 66:
                    return "isOffloadedScanBatchingSupported";
                case 67:
                    return "isActivityAndEnergyReportingSupported";
                case 68:
                    return "isLe2MPhySupported";
                case 69:
                    return "isLeCodedPhySupported";
                case 70:
                    return "isLeExtendedAdvertisingSupported";
                case 71:
                    return "isLePeriodicAdvertisingSupported";
                case 72:
                    return "getLeMaximumAdvertisingDataLength";
                case 73:
                    return "reportActivityInfo";
                case 74:
                    return "registerMetadataListener";
                case 75:
                    return "unregisterMetadataListener";
                case 76:
                    return "setMetadata";
                case 77:
                    return "getMetadata";
                case 78:
                    return "requestActivityInfo";
                case 79:
                    return "onLeServiceUp";
                case 80:
                    return "updateQuietModeStatus";
                case 81:
                    return "onBrEdrDown";
                case 82:
                    return "setSocketOpt";
                case 83:
                    return "getSocketOpt";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v17, resolved type: android.bluetooth.OobData} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v37, resolved type: android.bluetooth.BluetoothDevice} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v73, resolved type: android.os.ParcelUuid} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v89, resolved type: android.bluetooth.BluetoothDevice} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v93, resolved type: android.bluetooth.BluetoothDevice} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v97, resolved type: android.bluetooth.BluetoothDevice} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v105, resolved type: android.bluetooth.BluetoothDevice} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v140, resolved type: android.bluetooth.BluetoothDevice} */
        /* JADX WARNING: type inference failed for: r1v0 */
        /* JADX WARNING: type inference failed for: r1v3 */
        /* JADX WARNING: type inference failed for: r1v13 */
        /* JADX WARNING: type inference failed for: r1v21 */
        /* JADX WARNING: type inference failed for: r1v25 */
        /* JADX WARNING: type inference failed for: r1v29 */
        /* JADX WARNING: type inference failed for: r1v33 */
        /* JADX WARNING: type inference failed for: r1v41 */
        /* JADX WARNING: type inference failed for: r1v45 */
        /* JADX WARNING: type inference failed for: r1v49 */
        /* JADX WARNING: type inference failed for: r1v53 */
        /* JADX WARNING: type inference failed for: r1v57 */
        /* JADX WARNING: type inference failed for: r1v61 */
        /* JADX WARNING: type inference failed for: r1v65 */
        /* JADX WARNING: type inference failed for: r1v69 */
        /* JADX WARNING: type inference failed for: r1v77 */
        /* JADX WARNING: type inference failed for: r1v81 */
        /* JADX WARNING: type inference failed for: r1v85 */
        /* JADX WARNING: type inference failed for: r1v101 */
        /* JADX WARNING: type inference failed for: r1v109 */
        /* JADX WARNING: type inference failed for: r1v113 */
        /* JADX WARNING: type inference failed for: r1v117 */
        /* JADX WARNING: type inference failed for: r1v121 */
        /* JADX WARNING: type inference failed for: r1v125 */
        /* JADX WARNING: type inference failed for: r1v129 */
        /* JADX WARNING: type inference failed for: r1v133 */
        /* JADX WARNING: type inference failed for: r1v137, types: [android.os.IBinder] */
        /* JADX WARNING: type inference failed for: r1v144 */
        /* JADX WARNING: type inference failed for: r1v148 */
        /* JADX WARNING: type inference failed for: r1v152 */
        /* JADX WARNING: type inference failed for: r1v156 */
        /* JADX WARNING: type inference failed for: r1v162 */
        /* JADX WARNING: type inference failed for: r1v163 */
        /* JADX WARNING: type inference failed for: r1v164 */
        /* JADX WARNING: type inference failed for: r1v165 */
        /* JADX WARNING: type inference failed for: r1v166 */
        /* JADX WARNING: type inference failed for: r1v167 */
        /* JADX WARNING: type inference failed for: r1v168 */
        /* JADX WARNING: type inference failed for: r1v169 */
        /* JADX WARNING: type inference failed for: r1v170 */
        /* JADX WARNING: type inference failed for: r1v171 */
        /* JADX WARNING: type inference failed for: r1v172 */
        /* JADX WARNING: type inference failed for: r1v173 */
        /* JADX WARNING: type inference failed for: r1v174 */
        /* JADX WARNING: type inference failed for: r1v175 */
        /* JADX WARNING: type inference failed for: r1v176 */
        /* JADX WARNING: type inference failed for: r1v177 */
        /* JADX WARNING: type inference failed for: r1v178 */
        /* JADX WARNING: type inference failed for: r1v179 */
        /* JADX WARNING: type inference failed for: r1v180 */
        /* JADX WARNING: type inference failed for: r1v181 */
        /* JADX WARNING: type inference failed for: r1v182 */
        /* JADX WARNING: type inference failed for: r1v183 */
        /* JADX WARNING: type inference failed for: r1v184 */
        /* JADX WARNING: type inference failed for: r1v185 */
        /* JADX WARNING: type inference failed for: r1v186 */
        /* JADX WARNING: type inference failed for: r1v187 */
        /* JADX WARNING: type inference failed for: r1v188 */
        /* JADX WARNING: type inference failed for: r1v189 */
        /* JADX WARNING: type inference failed for: r1v190 */
        /* JADX WARNING: type inference failed for: r1v191 */
        /* JADX WARNING: type inference failed for: r1v192 */
        /* JADX WARNING: type inference failed for: r1v193 */
        /* JADX WARNING: type inference failed for: r1v194 */
        /* JADX WARNING: type inference failed for: r1v195 */
        /* JADX WARNING: type inference failed for: r1v196 */
        /* JADX WARNING: type inference failed for: r1v197 */
        /* JADX WARNING: type inference failed for: r1v198 */
        /* JADX WARNING: type inference failed for: r1v199 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r18, android.os.Parcel r19, android.os.Parcel r20, int r21) throws android.os.RemoteException {
            /*
                r17 = this;
                r6 = r17
                r7 = r18
                r8 = r19
                r9 = r20
                java.lang.String r10 = "android.bluetooth.IBluetooth"
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r11 = 1
                if (r7 == r0) goto L_0x0821
                r0 = 0
                r1 = 0
                switch(r7) {
                    case 1: goto L_0x0813;
                    case 2: goto L_0x0805;
                    case 3: goto L_0x07f7;
                    case 4: goto L_0x07e9;
                    case 5: goto L_0x07db;
                    case 6: goto L_0x07cd;
                    case 7: goto L_0x07bf;
                    case 8: goto L_0x07ad;
                    case 9: goto L_0x079f;
                    case 10: goto L_0x0788;
                    case 11: goto L_0x0768;
                    case 12: goto L_0x075a;
                    case 13: goto L_0x0748;
                    case 14: goto L_0x073a;
                    case 15: goto L_0x0728;
                    case 16: goto L_0x071a;
                    case 17: goto L_0x0704;
                    case 18: goto L_0x06f6;
                    case 19: goto L_0x06e4;
                    case 20: goto L_0x06d2;
                    case 21: goto L_0x06c4;
                    case 22: goto L_0x06b6;
                    case 23: goto L_0x06a8;
                    case 24: goto L_0x069a;
                    case 25: goto L_0x0688;
                    case 26: goto L_0x067a;
                    case 27: goto L_0x0656;
                    case 28: goto L_0x0624;
                    case 29: goto L_0x0604;
                    case 30: goto L_0x05e4;
                    case 31: goto L_0x05c4;
                    case 32: goto L_0x05a4;
                    case 33: goto L_0x0582;
                    case 34: goto L_0x0574;
                    case 35: goto L_0x0554;
                    case 36: goto L_0x0534;
                    case 37: goto L_0x0514;
                    case 38: goto L_0x04f4;
                    case 39: goto L_0x04d0;
                    case 40: goto L_0x04b0;
                    case 41: goto L_0x0490;
                    case 42: goto L_0x0470;
                    case 43: goto L_0x0442;
                    case 44: goto L_0x0422;
                    case 45: goto L_0x0414;
                    case 46: goto L_0x03f4;
                    case 47: goto L_0x03d4;
                    case 48: goto L_0x03a6;
                    case 49: goto L_0x0378;
                    case 50: goto L_0x0352;
                    case 51: goto L_0x0332;
                    case 52: goto L_0x030c;
                    case 53: goto L_0x02ec;
                    case 54: goto L_0x02c8;
                    case 55: goto L_0x02a8;
                    case 56: goto L_0x0284;
                    case 57: goto L_0x0264;
                    case 58: goto L_0x0240;
                    case 59: goto L_0x0218;
                    case 60: goto L_0x0206;
                    case 61: goto L_0x01f4;
                    case 62: goto L_0x01df;
                    case 63: goto L_0x01d1;
                    case 64: goto L_0x01c3;
                    case 65: goto L_0x01b5;
                    case 66: goto L_0x01a7;
                    case 67: goto L_0x0199;
                    case 68: goto L_0x018b;
                    case 69: goto L_0x017d;
                    case 70: goto L_0x016f;
                    case 71: goto L_0x0161;
                    case 72: goto L_0x0153;
                    case 73: goto L_0x013c;
                    case 74: goto L_0x0116;
                    case 75: goto L_0x00f6;
                    case 76: goto L_0x00ce;
                    case 77: goto L_0x00aa;
                    case 78: goto L_0x0091;
                    case 79: goto L_0x0087;
                    case 80: goto L_0x0075;
                    case 81: goto L_0x006b;
                    case 82: goto L_0x0041;
                    case 83: goto L_0x001a;
                    default: goto L_0x0015;
                }
            L_0x0015:
                boolean r0 = super.onTransact(r18, r19, r20, r21)
                return r0
            L_0x001a:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                int r1 = r19.readInt()
                int r2 = r19.readInt()
                int r3 = r19.readInt()
                if (r3 >= 0) goto L_0x0031
                r4 = 0
                goto L_0x0033
            L_0x0031:
                byte[] r4 = new byte[r3]
            L_0x0033:
                int r5 = r6.getSocketOpt(r0, r1, r2, r4)
                r20.writeNoException()
                r9.writeInt(r5)
                r9.writeByteArray(r4)
                return r11
            L_0x0041:
                r8.enforceInterface(r10)
                int r12 = r19.readInt()
                int r13 = r19.readInt()
                int r14 = r19.readInt()
                byte[] r15 = r19.createByteArray()
                int r16 = r19.readInt()
                r0 = r17
                r1 = r12
                r2 = r13
                r3 = r14
                r4 = r15
                r5 = r16
                int r0 = r0.setSocketOpt(r1, r2, r3, r4, r5)
                r20.writeNoException()
                r9.writeInt(r0)
                return r11
            L_0x006b:
                r8.enforceInterface(r10)
                r17.onBrEdrDown()
                r20.writeNoException()
                return r11
            L_0x0075:
                r8.enforceInterface(r10)
                int r1 = r19.readInt()
                if (r1 == 0) goto L_0x0080
                r0 = r11
            L_0x0080:
                r6.updateQuietModeStatus(r0)
                r20.writeNoException()
                return r11
            L_0x0087:
                r8.enforceInterface(r10)
                r17.onLeServiceUp()
                r20.writeNoException()
                return r11
            L_0x0091:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                if (r0 == 0) goto L_0x00a4
                android.os.Parcelable$Creator<android.os.ResultReceiver> r0 = android.os.ResultReceiver.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                r1 = r0
                android.os.ResultReceiver r1 = (android.os.ResultReceiver) r1
                goto L_0x00a5
            L_0x00a4:
            L_0x00a5:
                r0 = r1
                r6.requestActivityInfo(r0)
                return r11
            L_0x00aa:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                if (r0 == 0) goto L_0x00bd
                android.os.Parcelable$Creator<android.bluetooth.BluetoothDevice> r0 = android.bluetooth.BluetoothDevice.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                r1 = r0
                android.bluetooth.BluetoothDevice r1 = (android.bluetooth.BluetoothDevice) r1
                goto L_0x00be
            L_0x00bd:
            L_0x00be:
                r0 = r1
                int r1 = r19.readInt()
                byte[] r2 = r6.getMetadata(r0, r1)
                r20.writeNoException()
                r9.writeByteArray(r2)
                return r11
            L_0x00ce:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                if (r0 == 0) goto L_0x00e1
                android.os.Parcelable$Creator<android.bluetooth.BluetoothDevice> r0 = android.bluetooth.BluetoothDevice.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                r1 = r0
                android.bluetooth.BluetoothDevice r1 = (android.bluetooth.BluetoothDevice) r1
                goto L_0x00e2
            L_0x00e1:
            L_0x00e2:
                r0 = r1
                int r1 = r19.readInt()
                byte[] r2 = r19.createByteArray()
                boolean r3 = r6.setMetadata(r0, r1, r2)
                r20.writeNoException()
                r9.writeInt(r3)
                return r11
            L_0x00f6:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                if (r0 == 0) goto L_0x0109
                android.os.Parcelable$Creator<android.bluetooth.BluetoothDevice> r0 = android.bluetooth.BluetoothDevice.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                r1 = r0
                android.bluetooth.BluetoothDevice r1 = (android.bluetooth.BluetoothDevice) r1
                goto L_0x010a
            L_0x0109:
            L_0x010a:
                r0 = r1
                boolean r1 = r6.unregisterMetadataListener(r0)
                r20.writeNoException()
                r9.writeInt(r1)
                return r11
            L_0x0116:
                r8.enforceInterface(r10)
                android.os.IBinder r0 = r19.readStrongBinder()
                android.bluetooth.IBluetoothMetadataListener r0 = android.bluetooth.IBluetoothMetadataListener.Stub.asInterface(r0)
                int r2 = r19.readInt()
                if (r2 == 0) goto L_0x0130
                android.os.Parcelable$Creator<android.bluetooth.BluetoothDevice> r1 = android.bluetooth.BluetoothDevice.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.bluetooth.BluetoothDevice r1 = (android.bluetooth.BluetoothDevice) r1
                goto L_0x0131
            L_0x0130:
            L_0x0131:
                boolean r2 = r6.registerMetadataListener(r0, r1)
                r20.writeNoException()
                r9.writeInt(r2)
                return r11
            L_0x013c:
                r8.enforceInterface(r10)
                android.bluetooth.BluetoothActivityEnergyInfo r1 = r17.reportActivityInfo()
                r20.writeNoException()
                if (r1 == 0) goto L_0x014f
                r9.writeInt(r11)
                r1.writeToParcel(r9, r11)
                goto L_0x0152
            L_0x014f:
                r9.writeInt(r0)
            L_0x0152:
                return r11
            L_0x0153:
                r8.enforceInterface(r10)
                int r0 = r17.getLeMaximumAdvertisingDataLength()
                r20.writeNoException()
                r9.writeInt(r0)
                return r11
            L_0x0161:
                r8.enforceInterface(r10)
                boolean r0 = r17.isLePeriodicAdvertisingSupported()
                r20.writeNoException()
                r9.writeInt(r0)
                return r11
            L_0x016f:
                r8.enforceInterface(r10)
                boolean r0 = r17.isLeExtendedAdvertisingSupported()
                r20.writeNoException()
                r9.writeInt(r0)
                return r11
            L_0x017d:
                r8.enforceInterface(r10)
                boolean r0 = r17.isLeCodedPhySupported()
                r20.writeNoException()
                r9.writeInt(r0)
                return r11
            L_0x018b:
                r8.enforceInterface(r10)
                boolean r0 = r17.isLe2MPhySupported()
                r20.writeNoException()
                r9.writeInt(r0)
                return r11
            L_0x0199:
                r8.enforceInterface(r10)
                boolean r0 = r17.isActivityAndEnergyReportingSupported()
                r20.writeNoException()
                r9.writeInt(r0)
                return r11
            L_0x01a7:
                r8.enforceInterface(r10)
                boolean r0 = r17.isOffloadedScanBatchingSupported()
                r20.writeNoException()
                r9.writeInt(r0)
                return r11
            L_0x01b5:
                r8.enforceInterface(r10)
                boolean r0 = r17.isOffloadedFilteringSupported()
                r20.writeNoException()
                r9.writeInt(r0)
                return r11
            L_0x01c3:
                r8.enforceInterface(r10)
                boolean r0 = r17.isMultiAdvertisementSupported()
                r20.writeNoException()
                r9.writeInt(r0)
                return r11
            L_0x01d1:
                r8.enforceInterface(r10)
                boolean r0 = r17.factoryReset()
                r20.writeNoException()
                r9.writeInt(r0)
                return r11
            L_0x01df:
                r8.enforceInterface(r10)
                android.bluetooth.IBluetoothSocketManager r0 = r17.getSocketManager()
                r20.writeNoException()
                if (r0 == 0) goto L_0x01f0
                android.os.IBinder r1 = r0.asBinder()
            L_0x01f0:
                r9.writeStrongBinder(r1)
                return r11
            L_0x01f4:
                r8.enforceInterface(r10)
                android.os.IBinder r0 = r19.readStrongBinder()
                android.bluetooth.IBluetoothCallback r0 = android.bluetooth.IBluetoothCallback.Stub.asInterface(r0)
                r6.unregisterCallback(r0)
                r20.writeNoException()
                return r11
            L_0x0206:
                r8.enforceInterface(r10)
                android.os.IBinder r0 = r19.readStrongBinder()
                android.bluetooth.IBluetoothCallback r0 = android.bluetooth.IBluetoothCallback.Stub.asInterface(r0)
                r6.registerCallback(r0)
                r20.writeNoException()
                return r11
            L_0x0218:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                if (r0 == 0) goto L_0x022b
                android.os.Parcelable$Creator<android.bluetooth.BluetoothDevice> r0 = android.bluetooth.BluetoothDevice.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                r1 = r0
                android.bluetooth.BluetoothDevice r1 = (android.bluetooth.BluetoothDevice) r1
                goto L_0x022c
            L_0x022b:
            L_0x022c:
                r0 = r1
                int r1 = r19.readInt()
                int r2 = r19.readInt()
                int r3 = r19.readInt()
                r6.sendConnectionStateChange(r0, r1, r2, r3)
                r20.writeNoException()
                return r11
            L_0x0240:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                if (r0 == 0) goto L_0x0253
                android.os.Parcelable$Creator<android.bluetooth.BluetoothDevice> r0 = android.bluetooth.BluetoothDevice.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                r1 = r0
                android.bluetooth.BluetoothDevice r1 = (android.bluetooth.BluetoothDevice) r1
                goto L_0x0254
            L_0x0253:
            L_0x0254:
                r0 = r1
                int r1 = r19.readInt()
                boolean r2 = r6.setSimAccessPermission(r0, r1)
                r20.writeNoException()
                r9.writeInt(r2)
                return r11
            L_0x0264:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                if (r0 == 0) goto L_0x0277
                android.os.Parcelable$Creator<android.bluetooth.BluetoothDevice> r0 = android.bluetooth.BluetoothDevice.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                r1 = r0
                android.bluetooth.BluetoothDevice r1 = (android.bluetooth.BluetoothDevice) r1
                goto L_0x0278
            L_0x0277:
            L_0x0278:
                r0 = r1
                int r1 = r6.getSimAccessPermission(r0)
                r20.writeNoException()
                r9.writeInt(r1)
                return r11
            L_0x0284:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                if (r0 == 0) goto L_0x0297
                android.os.Parcelable$Creator<android.bluetooth.BluetoothDevice> r0 = android.bluetooth.BluetoothDevice.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                r1 = r0
                android.bluetooth.BluetoothDevice r1 = (android.bluetooth.BluetoothDevice) r1
                goto L_0x0298
            L_0x0297:
            L_0x0298:
                r0 = r1
                int r1 = r19.readInt()
                boolean r2 = r6.setMessageAccessPermission(r0, r1)
                r20.writeNoException()
                r9.writeInt(r2)
                return r11
            L_0x02a8:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                if (r0 == 0) goto L_0x02bb
                android.os.Parcelable$Creator<android.bluetooth.BluetoothDevice> r0 = android.bluetooth.BluetoothDevice.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                r1 = r0
                android.bluetooth.BluetoothDevice r1 = (android.bluetooth.BluetoothDevice) r1
                goto L_0x02bc
            L_0x02bb:
            L_0x02bc:
                r0 = r1
                int r1 = r6.getMessageAccessPermission(r0)
                r20.writeNoException()
                r9.writeInt(r1)
                return r11
            L_0x02c8:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                if (r0 == 0) goto L_0x02db
                android.os.Parcelable$Creator<android.bluetooth.BluetoothDevice> r0 = android.bluetooth.BluetoothDevice.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                r1 = r0
                android.bluetooth.BluetoothDevice r1 = (android.bluetooth.BluetoothDevice) r1
                goto L_0x02dc
            L_0x02db:
            L_0x02dc:
                r0 = r1
                int r1 = r19.readInt()
                boolean r2 = r6.setPhonebookAccessPermission(r0, r1)
                r20.writeNoException()
                r9.writeInt(r2)
                return r11
            L_0x02ec:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                if (r0 == 0) goto L_0x02ff
                android.os.Parcelable$Creator<android.bluetooth.BluetoothDevice> r0 = android.bluetooth.BluetoothDevice.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                r1 = r0
                android.bluetooth.BluetoothDevice r1 = (android.bluetooth.BluetoothDevice) r1
                goto L_0x0300
            L_0x02ff:
            L_0x0300:
                r0 = r1
                boolean r1 = r6.getSilenceMode(r0)
                r20.writeNoException()
                r9.writeInt(r1)
                return r11
            L_0x030c:
                r8.enforceInterface(r10)
                int r2 = r19.readInt()
                if (r2 == 0) goto L_0x031e
                android.os.Parcelable$Creator<android.bluetooth.BluetoothDevice> r1 = android.bluetooth.BluetoothDevice.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.bluetooth.BluetoothDevice r1 = (android.bluetooth.BluetoothDevice) r1
                goto L_0x031f
            L_0x031e:
            L_0x031f:
                int r2 = r19.readInt()
                if (r2 == 0) goto L_0x0327
                r0 = r11
            L_0x0327:
                boolean r2 = r6.setSilenceMode(r1, r0)
                r20.writeNoException()
                r9.writeInt(r2)
                return r11
            L_0x0332:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                if (r0 == 0) goto L_0x0345
                android.os.Parcelable$Creator<android.bluetooth.BluetoothDevice> r0 = android.bluetooth.BluetoothDevice.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                r1 = r0
                android.bluetooth.BluetoothDevice r1 = (android.bluetooth.BluetoothDevice) r1
                goto L_0x0346
            L_0x0345:
            L_0x0346:
                r0 = r1
                int r1 = r6.getPhonebookAccessPermission(r0)
                r20.writeNoException()
                r9.writeInt(r1)
                return r11
            L_0x0352:
                r8.enforceInterface(r10)
                int r2 = r19.readInt()
                if (r2 == 0) goto L_0x0364
                android.os.Parcelable$Creator<android.bluetooth.BluetoothDevice> r1 = android.bluetooth.BluetoothDevice.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.bluetooth.BluetoothDevice r1 = (android.bluetooth.BluetoothDevice) r1
                goto L_0x0365
            L_0x0364:
            L_0x0365:
                int r2 = r19.readInt()
                if (r2 == 0) goto L_0x036d
                r0 = r11
            L_0x036d:
                boolean r2 = r6.setPairingConfirmation(r1, r0)
                r20.writeNoException()
                r9.writeInt(r2)
                return r11
            L_0x0378:
                r8.enforceInterface(r10)
                int r2 = r19.readInt()
                if (r2 == 0) goto L_0x038a
                android.os.Parcelable$Creator<android.bluetooth.BluetoothDevice> r1 = android.bluetooth.BluetoothDevice.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.bluetooth.BluetoothDevice r1 = (android.bluetooth.BluetoothDevice) r1
                goto L_0x038b
            L_0x038a:
            L_0x038b:
                int r2 = r19.readInt()
                if (r2 == 0) goto L_0x0393
                r0 = r11
            L_0x0393:
                int r2 = r19.readInt()
                byte[] r3 = r19.createByteArray()
                boolean r4 = r6.setPasskey(r1, r0, r2, r3)
                r20.writeNoException()
                r9.writeInt(r4)
                return r11
            L_0x03a6:
                r8.enforceInterface(r10)
                int r2 = r19.readInt()
                if (r2 == 0) goto L_0x03b8
                android.os.Parcelable$Creator<android.bluetooth.BluetoothDevice> r1 = android.bluetooth.BluetoothDevice.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.bluetooth.BluetoothDevice r1 = (android.bluetooth.BluetoothDevice) r1
                goto L_0x03b9
            L_0x03b8:
            L_0x03b9:
                int r2 = r19.readInt()
                if (r2 == 0) goto L_0x03c1
                r0 = r11
            L_0x03c1:
                int r2 = r19.readInt()
                byte[] r3 = r19.createByteArray()
                boolean r4 = r6.setPin(r1, r0, r2, r3)
                r20.writeNoException()
                r9.writeInt(r4)
                return r11
            L_0x03d4:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                if (r0 == 0) goto L_0x03e7
                android.os.Parcelable$Creator<android.bluetooth.BluetoothDevice> r0 = android.bluetooth.BluetoothDevice.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                r1 = r0
                android.bluetooth.BluetoothDevice r1 = (android.bluetooth.BluetoothDevice) r1
                goto L_0x03e8
            L_0x03e7:
            L_0x03e8:
                r0 = r1
                java.lang.String r1 = r6.getTwsPlusPeerAddress(r0)
                r20.writeNoException()
                r9.writeString(r1)
                return r11
            L_0x03f4:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                if (r0 == 0) goto L_0x0407
                android.os.Parcelable$Creator<android.bluetooth.BluetoothDevice> r0 = android.bluetooth.BluetoothDevice.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                r1 = r0
                android.bluetooth.BluetoothDevice r1 = (android.bluetooth.BluetoothDevice) r1
                goto L_0x0408
            L_0x0407:
            L_0x0408:
                r0 = r1
                boolean r1 = r6.isTwsPlusDevice(r0)
                r20.writeNoException()
                r9.writeInt(r1)
                return r11
            L_0x0414:
                r8.enforceInterface(r10)
                int r0 = r17.getMaxConnectedAudioDevices()
                r20.writeNoException()
                r9.writeInt(r0)
                return r11
            L_0x0422:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                if (r0 == 0) goto L_0x0435
                android.os.Parcelable$Creator<android.bluetooth.BluetoothDevice> r0 = android.bluetooth.BluetoothDevice.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                r1 = r0
                android.bluetooth.BluetoothDevice r1 = (android.bluetooth.BluetoothDevice) r1
                goto L_0x0436
            L_0x0435:
            L_0x0436:
                r0 = r1
                int r1 = r6.getBatteryLevel(r0)
                r20.writeNoException()
                r9.writeInt(r1)
                return r11
            L_0x0442:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                if (r0 == 0) goto L_0x0454
                android.os.Parcelable$Creator<android.bluetooth.BluetoothDevice> r0 = android.bluetooth.BluetoothDevice.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                android.bluetooth.BluetoothDevice r0 = (android.bluetooth.BluetoothDevice) r0
                goto L_0x0455
            L_0x0454:
                r0 = r1
            L_0x0455:
                int r2 = r19.readInt()
                if (r2 == 0) goto L_0x0464
                android.os.Parcelable$Creator<android.os.ParcelUuid> r1 = android.os.ParcelUuid.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.os.ParcelUuid r1 = (android.os.ParcelUuid) r1
                goto L_0x0465
            L_0x0464:
            L_0x0465:
                boolean r2 = r6.sdpSearch(r0, r1)
                r20.writeNoException()
                r9.writeInt(r2)
                return r11
            L_0x0470:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                if (r0 == 0) goto L_0x0483
                android.os.Parcelable$Creator<android.bluetooth.BluetoothDevice> r0 = android.bluetooth.BluetoothDevice.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                r1 = r0
                android.bluetooth.BluetoothDevice r1 = (android.bluetooth.BluetoothDevice) r1
                goto L_0x0484
            L_0x0483:
            L_0x0484:
                r0 = r1
                boolean r1 = r6.fetchRemoteUuids(r0)
                r20.writeNoException()
                r9.writeInt(r1)
                return r11
            L_0x0490:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                if (r0 == 0) goto L_0x04a3
                android.os.Parcelable$Creator<android.bluetooth.BluetoothDevice> r0 = android.bluetooth.BluetoothDevice.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                r1 = r0
                android.bluetooth.BluetoothDevice r1 = (android.bluetooth.BluetoothDevice) r1
                goto L_0x04a4
            L_0x04a3:
            L_0x04a4:
                r0 = r1
                android.os.ParcelUuid[] r1 = r6.getRemoteUuids(r0)
                r20.writeNoException()
                r9.writeTypedArray(r1, r11)
                return r11
            L_0x04b0:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                if (r0 == 0) goto L_0x04c3
                android.os.Parcelable$Creator<android.bluetooth.BluetoothDevice> r0 = android.bluetooth.BluetoothDevice.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                r1 = r0
                android.bluetooth.BluetoothDevice r1 = (android.bluetooth.BluetoothDevice) r1
                goto L_0x04c4
            L_0x04c3:
            L_0x04c4:
                r0 = r1
                int r1 = r6.getRemoteClass(r0)
                r20.writeNoException()
                r9.writeInt(r1)
                return r11
            L_0x04d0:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                if (r0 == 0) goto L_0x04e3
                android.os.Parcelable$Creator<android.bluetooth.BluetoothDevice> r0 = android.bluetooth.BluetoothDevice.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                r1 = r0
                android.bluetooth.BluetoothDevice r1 = (android.bluetooth.BluetoothDevice) r1
                goto L_0x04e4
            L_0x04e3:
            L_0x04e4:
                r0 = r1
                java.lang.String r1 = r19.readString()
                boolean r2 = r6.setRemoteAlias(r0, r1)
                r20.writeNoException()
                r9.writeInt(r2)
                return r11
            L_0x04f4:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                if (r0 == 0) goto L_0x0507
                android.os.Parcelable$Creator<android.bluetooth.BluetoothDevice> r0 = android.bluetooth.BluetoothDevice.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                r1 = r0
                android.bluetooth.BluetoothDevice r1 = (android.bluetooth.BluetoothDevice) r1
                goto L_0x0508
            L_0x0507:
            L_0x0508:
                r0 = r1
                java.lang.String r1 = r6.getRemoteAlias(r0)
                r20.writeNoException()
                r9.writeString(r1)
                return r11
            L_0x0514:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                if (r0 == 0) goto L_0x0527
                android.os.Parcelable$Creator<android.bluetooth.BluetoothDevice> r0 = android.bluetooth.BluetoothDevice.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                r1 = r0
                android.bluetooth.BluetoothDevice r1 = (android.bluetooth.BluetoothDevice) r1
                goto L_0x0528
            L_0x0527:
            L_0x0528:
                r0 = r1
                int r1 = r6.getRemoteType(r0)
                r20.writeNoException()
                r9.writeInt(r1)
                return r11
            L_0x0534:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                if (r0 == 0) goto L_0x0547
                android.os.Parcelable$Creator<android.bluetooth.BluetoothDevice> r0 = android.bluetooth.BluetoothDevice.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                r1 = r0
                android.bluetooth.BluetoothDevice r1 = (android.bluetooth.BluetoothDevice) r1
                goto L_0x0548
            L_0x0547:
            L_0x0548:
                r0 = r1
                java.lang.String r1 = r6.getRemoteName(r0)
                r20.writeNoException()
                r9.writeString(r1)
                return r11
            L_0x0554:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                if (r0 == 0) goto L_0x0567
                android.os.Parcelable$Creator<android.bluetooth.BluetoothDevice> r0 = android.bluetooth.BluetoothDevice.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                r1 = r0
                android.bluetooth.BluetoothDevice r1 = (android.bluetooth.BluetoothDevice) r1
                goto L_0x0568
            L_0x0567:
            L_0x0568:
                r0 = r1
                int r1 = r6.getConnectionState(r0)
                r20.writeNoException()
                r9.writeInt(r1)
                return r11
            L_0x0574:
                r8.enforceInterface(r10)
                long r0 = r17.getSupportedProfiles()
                r20.writeNoException()
                r9.writeLong(r0)
                return r11
            L_0x0582:
                r8.enforceInterface(r10)
                int r2 = r19.readInt()
                if (r2 == 0) goto L_0x0594
                android.os.Parcelable$Creator<android.bluetooth.BluetoothDevice> r1 = android.bluetooth.BluetoothDevice.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.bluetooth.BluetoothDevice r1 = (android.bluetooth.BluetoothDevice) r1
                goto L_0x0595
            L_0x0594:
            L_0x0595:
                int r2 = r19.readInt()
                if (r2 == 0) goto L_0x059d
                r0 = r11
            L_0x059d:
                r6.setBondingInitiatedLocally(r1, r0)
                r20.writeNoException()
                return r11
            L_0x05a4:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                if (r0 == 0) goto L_0x05b7
                android.os.Parcelable$Creator<android.bluetooth.BluetoothDevice> r0 = android.bluetooth.BluetoothDevice.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                r1 = r0
                android.bluetooth.BluetoothDevice r1 = (android.bluetooth.BluetoothDevice) r1
                goto L_0x05b8
            L_0x05b7:
            L_0x05b8:
                r0 = r1
                boolean r1 = r6.isBondingInitiatedLocally(r0)
                r20.writeNoException()
                r9.writeInt(r1)
                return r11
            L_0x05c4:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                if (r0 == 0) goto L_0x05d7
                android.os.Parcelable$Creator<android.bluetooth.BluetoothDevice> r0 = android.bluetooth.BluetoothDevice.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                r1 = r0
                android.bluetooth.BluetoothDevice r1 = (android.bluetooth.BluetoothDevice) r1
                goto L_0x05d8
            L_0x05d7:
            L_0x05d8:
                r0 = r1
                int r1 = r6.getBondState(r0)
                r20.writeNoException()
                r9.writeInt(r1)
                return r11
            L_0x05e4:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                if (r0 == 0) goto L_0x05f7
                android.os.Parcelable$Creator<android.bluetooth.BluetoothDevice> r0 = android.bluetooth.BluetoothDevice.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                r1 = r0
                android.bluetooth.BluetoothDevice r1 = (android.bluetooth.BluetoothDevice) r1
                goto L_0x05f8
            L_0x05f7:
            L_0x05f8:
                r0 = r1
                boolean r1 = r6.removeBond(r0)
                r20.writeNoException()
                r9.writeInt(r1)
                return r11
            L_0x0604:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                if (r0 == 0) goto L_0x0617
                android.os.Parcelable$Creator<android.bluetooth.BluetoothDevice> r0 = android.bluetooth.BluetoothDevice.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                r1 = r0
                android.bluetooth.BluetoothDevice r1 = (android.bluetooth.BluetoothDevice) r1
                goto L_0x0618
            L_0x0617:
            L_0x0618:
                r0 = r1
                boolean r1 = r6.cancelBondProcess(r0)
                r20.writeNoException()
                r9.writeInt(r1)
                return r11
            L_0x0624:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                if (r0 == 0) goto L_0x0636
                android.os.Parcelable$Creator<android.bluetooth.BluetoothDevice> r0 = android.bluetooth.BluetoothDevice.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                android.bluetooth.BluetoothDevice r0 = (android.bluetooth.BluetoothDevice) r0
                goto L_0x0637
            L_0x0636:
                r0 = r1
            L_0x0637:
                int r2 = r19.readInt()
                int r3 = r19.readInt()
                if (r3 == 0) goto L_0x064a
                android.os.Parcelable$Creator<android.bluetooth.OobData> r1 = android.bluetooth.OobData.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.bluetooth.OobData r1 = (android.bluetooth.OobData) r1
                goto L_0x064b
            L_0x064a:
            L_0x064b:
                boolean r3 = r6.createBondOutOfBand(r0, r2, r1)
                r20.writeNoException()
                r9.writeInt(r3)
                return r11
            L_0x0656:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                if (r0 == 0) goto L_0x0669
                android.os.Parcelable$Creator<android.bluetooth.BluetoothDevice> r0 = android.bluetooth.BluetoothDevice.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                r1 = r0
                android.bluetooth.BluetoothDevice r1 = (android.bluetooth.BluetoothDevice) r1
                goto L_0x066a
            L_0x0669:
            L_0x066a:
                r0 = r1
                int r1 = r19.readInt()
                boolean r2 = r6.createBond(r0, r1)
                r20.writeNoException()
                r9.writeInt(r2)
                return r11
            L_0x067a:
                r8.enforceInterface(r10)
                android.bluetooth.BluetoothDevice[] r0 = r17.getBondedDevices()
                r20.writeNoException()
                r9.writeTypedArray(r0, r11)
                return r11
            L_0x0688:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                int r1 = r6.getProfileConnectionState(r0)
                r20.writeNoException()
                r9.writeInt(r1)
                return r11
            L_0x069a:
                r8.enforceInterface(r10)
                int r0 = r17.getAdapterConnectionState()
                r20.writeNoException()
                r9.writeInt(r0)
                return r11
            L_0x06a8:
                r8.enforceInterface(r10)
                long r0 = r17.getDiscoveryEndMillis()
                r20.writeNoException()
                r9.writeLong(r0)
                return r11
            L_0x06b6:
                r8.enforceInterface(r10)
                boolean r0 = r17.isDiscovering()
                r20.writeNoException()
                r9.writeInt(r0)
                return r11
            L_0x06c4:
                r8.enforceInterface(r10)
                boolean r0 = r17.cancelDiscovery()
                r20.writeNoException()
                r9.writeInt(r0)
                return r11
            L_0x06d2:
                r8.enforceInterface(r10)
                java.lang.String r0 = r19.readString()
                boolean r1 = r6.startDiscovery(r0)
                r20.writeNoException()
                r9.writeInt(r1)
                return r11
            L_0x06e4:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                boolean r1 = r6.setDiscoverableTimeout(r0)
                r20.writeNoException()
                r9.writeInt(r1)
                return r11
            L_0x06f6:
                r8.enforceInterface(r10)
                int r0 = r17.getDiscoverableTimeout()
                r20.writeNoException()
                r9.writeInt(r0)
                return r11
            L_0x0704:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                int r1 = r19.readInt()
                boolean r2 = r6.setScanMode(r0, r1)
                r20.writeNoException()
                r9.writeInt(r2)
                return r11
            L_0x071a:
                r8.enforceInterface(r10)
                int r0 = r17.getScanMode()
                r20.writeNoException()
                r9.writeInt(r0)
                return r11
            L_0x0728:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                boolean r1 = r6.setLeIoCapability(r0)
                r20.writeNoException()
                r9.writeInt(r1)
                return r11
            L_0x073a:
                r8.enforceInterface(r10)
                int r0 = r17.getLeIoCapability()
                r20.writeNoException()
                r9.writeInt(r0)
                return r11
            L_0x0748:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                boolean r1 = r6.setIoCapability(r0)
                r20.writeNoException()
                r9.writeInt(r1)
                return r11
            L_0x075a:
                r8.enforceInterface(r10)
                int r0 = r17.getIoCapability()
                r20.writeNoException()
                r9.writeInt(r0)
                return r11
            L_0x0768:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                if (r0 == 0) goto L_0x077b
                android.os.Parcelable$Creator<android.bluetooth.BluetoothClass> r0 = android.bluetooth.BluetoothClass.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                r1 = r0
                android.bluetooth.BluetoothClass r1 = (android.bluetooth.BluetoothClass) r1
                goto L_0x077c
            L_0x077b:
            L_0x077c:
                r0 = r1
                boolean r1 = r6.setBluetoothClass(r0)
                r20.writeNoException()
                r9.writeInt(r1)
                return r11
            L_0x0788:
                r8.enforceInterface(r10)
                android.bluetooth.BluetoothClass r1 = r17.getBluetoothClass()
                r20.writeNoException()
                if (r1 == 0) goto L_0x079b
                r9.writeInt(r11)
                r1.writeToParcel(r9, r11)
                goto L_0x079e
            L_0x079b:
                r9.writeInt(r0)
            L_0x079e:
                return r11
            L_0x079f:
                r8.enforceInterface(r10)
                java.lang.String r0 = r17.getName()
                r20.writeNoException()
                r9.writeString(r0)
                return r11
            L_0x07ad:
                r8.enforceInterface(r10)
                java.lang.String r0 = r19.readString()
                boolean r1 = r6.setName(r0)
                r20.writeNoException()
                r9.writeInt(r1)
                return r11
            L_0x07bf:
                r8.enforceInterface(r10)
                android.os.ParcelUuid[] r0 = r17.getUuids()
                r20.writeNoException()
                r9.writeTypedArray(r0, r11)
                return r11
            L_0x07cd:
                r8.enforceInterface(r10)
                java.lang.String r0 = r17.getAddress()
                r20.writeNoException()
                r9.writeString(r0)
                return r11
            L_0x07db:
                r8.enforceInterface(r10)
                boolean r0 = r17.disable()
                r20.writeNoException()
                r9.writeInt(r0)
                return r11
            L_0x07e9:
                r8.enforceInterface(r10)
                boolean r0 = r17.enableNoAutoConnect()
                r20.writeNoException()
                r9.writeInt(r0)
                return r11
            L_0x07f7:
                r8.enforceInterface(r10)
                boolean r0 = r17.enable()
                r20.writeNoException()
                r9.writeInt(r0)
                return r11
            L_0x0805:
                r8.enforceInterface(r10)
                int r0 = r17.getState()
                r20.writeNoException()
                r9.writeInt(r0)
                return r11
            L_0x0813:
                r8.enforceInterface(r10)
                boolean r0 = r17.isEnabled()
                r20.writeNoException()
                r9.writeInt(r0)
                return r11
            L_0x0821:
                r9.writeString(r10)
                return r11
            */
            throw new UnsupportedOperationException("Method not decompiled: android.bluetooth.IBluetooth.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IBluetooth {
            public static IBluetooth sDefaultImpl;
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

            public boolean isEnabled() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(1, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isEnabled();
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

            public int getState() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(2, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getState();
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

            public boolean enable() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(3, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().enable();
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

            public boolean enableNoAutoConnect() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(4, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().enableNoAutoConnect();
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

            public boolean disable() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(5, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().disable();
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

            public String getAddress() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(6, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAddress();
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

            public ParcelUuid[] getUuids() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(7, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getUuids();
                    }
                    _reply.readException();
                    ParcelUuid[] _result = (ParcelUuid[]) _reply.createTypedArray(ParcelUuid.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean setName(String name) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    boolean z = false;
                    if (!this.mRemote.transact(8, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setName(name);
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

            public String getName() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(9, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getName();
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

            public BluetoothClass getBluetoothClass() throws RemoteException {
                BluetoothClass _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(10, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getBluetoothClass();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = BluetoothClass.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    BluetoothClass _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean setBluetoothClass(BluetoothClass bluetoothClass) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (bluetoothClass != null) {
                        _data.writeInt(1);
                        bluetoothClass.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(11, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setBluetoothClass(bluetoothClass);
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

            public int getIoCapability() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(12, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getIoCapability();
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

            public boolean setIoCapability(int capability) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(capability);
                    boolean z = false;
                    if (!this.mRemote.transact(13, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setIoCapability(capability);
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

            public int getLeIoCapability() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(14, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getLeIoCapability();
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

            public boolean setLeIoCapability(int capability) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(capability);
                    boolean z = false;
                    if (!this.mRemote.transact(15, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setLeIoCapability(capability);
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

            public int getScanMode() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(16, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getScanMode();
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

            public boolean setScanMode(int mode, int duration) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(mode);
                    _data.writeInt(duration);
                    boolean z = false;
                    if (!this.mRemote.transact(17, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setScanMode(mode, duration);
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

            public int getDiscoverableTimeout() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(18, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getDiscoverableTimeout();
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

            public boolean setDiscoverableTimeout(int timeout) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(timeout);
                    boolean z = false;
                    if (!this.mRemote.transact(19, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setDiscoverableTimeout(timeout);
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

            public boolean startDiscovery(String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    boolean z = false;
                    if (!this.mRemote.transact(20, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().startDiscovery(callingPackage);
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

            public boolean cancelDiscovery() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(21, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().cancelDiscovery();
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

            public boolean isDiscovering() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(22, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isDiscovering();
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

            public long getDiscoveryEndMillis() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(23, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getDiscoveryEndMillis();
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

            public int getAdapterConnectionState() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(24, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAdapterConnectionState();
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

            public int getProfileConnectionState(int profile) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(profile);
                    if (!this.mRemote.transact(25, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getProfileConnectionState(profile);
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

            public BluetoothDevice[] getBondedDevices() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(26, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getBondedDevices();
                    }
                    _reply.readException();
                    BluetoothDevice[] _result = (BluetoothDevice[]) _reply.createTypedArray(BluetoothDevice.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean createBond(BluetoothDevice device, int transport) throws RemoteException {
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
                    _data.writeInt(transport);
                    if (!this.mRemote.transact(27, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().createBond(device, transport);
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

            public boolean createBondOutOfBand(BluetoothDevice device, int transport, OobData oobData) throws RemoteException {
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
                    _data.writeInt(transport);
                    if (oobData != null) {
                        _data.writeInt(1);
                        oobData.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(28, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().createBondOutOfBand(device, transport, oobData);
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

            public boolean cancelBondProcess(BluetoothDevice device) throws RemoteException {
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
                    if (!this.mRemote.transact(29, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().cancelBondProcess(device);
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

            public boolean removeBond(BluetoothDevice device) throws RemoteException {
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
                    if (!this.mRemote.transact(30, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().removeBond(device);
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

            public int getBondState(BluetoothDevice device) throws RemoteException {
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
                    if (!this.mRemote.transact(31, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getBondState(device);
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

            public boolean isBondingInitiatedLocally(BluetoothDevice device) throws RemoteException {
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
                    if (!this.mRemote.transact(32, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isBondingInitiatedLocally(device);
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

            public void setBondingInitiatedLocally(BluetoothDevice devicei, boolean localInitiated) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (devicei != null) {
                        _data.writeInt(1);
                        devicei.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(localInitiated);
                    if (this.mRemote.transact(33, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setBondingInitiatedLocally(devicei, localInitiated);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public long getSupportedProfiles() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(34, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getSupportedProfiles();
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
                    if (!this.mRemote.transact(35, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            public String getRemoteName(BluetoothDevice device) throws RemoteException {
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
                    if (!this.mRemote.transact(36, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getRemoteName(device);
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

            public int getRemoteType(BluetoothDevice device) throws RemoteException {
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
                    if (!this.mRemote.transact(37, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getRemoteType(device);
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

            public String getRemoteAlias(BluetoothDevice device) throws RemoteException {
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
                    if (!this.mRemote.transact(38, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getRemoteAlias(device);
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

            public boolean setRemoteAlias(BluetoothDevice device, String name) throws RemoteException {
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
                    _data.writeString(name);
                    if (!this.mRemote.transact(39, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setRemoteAlias(device, name);
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

            public int getRemoteClass(BluetoothDevice device) throws RemoteException {
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
                    if (!this.mRemote.transact(40, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getRemoteClass(device);
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

            public ParcelUuid[] getRemoteUuids(BluetoothDevice device) throws RemoteException {
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
                    if (!this.mRemote.transact(41, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getRemoteUuids(device);
                    }
                    _reply.readException();
                    ParcelUuid[] _result = (ParcelUuid[]) _reply.createTypedArray(ParcelUuid.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean fetchRemoteUuids(BluetoothDevice device) throws RemoteException {
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
                    if (!this.mRemote.transact(42, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().fetchRemoteUuids(device);
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

            public boolean sdpSearch(BluetoothDevice device, ParcelUuid uuid) throws RemoteException {
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
                    if (uuid != null) {
                        _data.writeInt(1);
                        uuid.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(43, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().sdpSearch(device, uuid);
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

            public int getBatteryLevel(BluetoothDevice device) throws RemoteException {
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
                    if (!this.mRemote.transact(44, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getBatteryLevel(device);
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

            public int getMaxConnectedAudioDevices() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(45, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getMaxConnectedAudioDevices();
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

            public boolean isTwsPlusDevice(BluetoothDevice device) throws RemoteException {
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
                    if (!this.mRemote.transact(46, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isTwsPlusDevice(device);
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

            public String getTwsPlusPeerAddress(BluetoothDevice device) throws RemoteException {
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
                    if (!this.mRemote.transact(47, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getTwsPlusPeerAddress(device);
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

            public boolean setPin(BluetoothDevice device, boolean accept, int len, byte[] pinCode) throws RemoteException {
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
                    _data.writeInt(accept);
                    _data.writeInt(len);
                    _data.writeByteArray(pinCode);
                    if (!this.mRemote.transact(48, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setPin(device, accept, len, pinCode);
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

            public boolean setPasskey(BluetoothDevice device, boolean accept, int len, byte[] passkey) throws RemoteException {
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
                    _data.writeInt(accept);
                    _data.writeInt(len);
                    _data.writeByteArray(passkey);
                    if (!this.mRemote.transact(49, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setPasskey(device, accept, len, passkey);
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

            public boolean setPairingConfirmation(BluetoothDevice device, boolean accept) throws RemoteException {
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
                    _data.writeInt(accept);
                    if (!this.mRemote.transact(50, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setPairingConfirmation(device, accept);
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

            public int getPhonebookAccessPermission(BluetoothDevice device) throws RemoteException {
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
                    if (!this.mRemote.transact(51, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPhonebookAccessPermission(device);
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

            public boolean setSilenceMode(BluetoothDevice device, boolean silence) throws RemoteException {
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
                    _data.writeInt(silence);
                    if (!this.mRemote.transact(52, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setSilenceMode(device, silence);
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

            public boolean getSilenceMode(BluetoothDevice device) throws RemoteException {
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
                    if (!this.mRemote.transact(53, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getSilenceMode(device);
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

            public boolean setPhonebookAccessPermission(BluetoothDevice device, int value) throws RemoteException {
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
                    _data.writeInt(value);
                    if (!this.mRemote.transact(54, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setPhonebookAccessPermission(device, value);
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

            public int getMessageAccessPermission(BluetoothDevice device) throws RemoteException {
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
                    if (!this.mRemote.transact(55, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getMessageAccessPermission(device);
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

            public boolean setMessageAccessPermission(BluetoothDevice device, int value) throws RemoteException {
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
                    _data.writeInt(value);
                    if (!this.mRemote.transact(56, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setMessageAccessPermission(device, value);
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

            public int getSimAccessPermission(BluetoothDevice device) throws RemoteException {
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
                    if (!this.mRemote.transact(57, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getSimAccessPermission(device);
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

            public boolean setSimAccessPermission(BluetoothDevice device, int value) throws RemoteException {
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
                    _data.writeInt(value);
                    if (!this.mRemote.transact(58, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setSimAccessPermission(device, value);
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

            public void sendConnectionStateChange(BluetoothDevice device, int profile, int state, int prevState) throws RemoteException {
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
                    _data.writeInt(profile);
                    _data.writeInt(state);
                    _data.writeInt(prevState);
                    if (this.mRemote.transact(59, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().sendConnectionStateChange(device, profile, state, prevState);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void registerCallback(IBluetoothCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(60, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().registerCallback(callback);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void unregisterCallback(IBluetoothCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(61, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().unregisterCallback(callback);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public IBluetoothSocketManager getSocketManager() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(62, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getSocketManager();
                    }
                    _reply.readException();
                    IBluetoothSocketManager _result = IBluetoothSocketManager.Stub.asInterface(_reply.readStrongBinder());
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean factoryReset() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(63, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().factoryReset();
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

            public boolean isMultiAdvertisementSupported() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(64, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isMultiAdvertisementSupported();
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

            public boolean isOffloadedFilteringSupported() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(65, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isOffloadedFilteringSupported();
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

            public boolean isOffloadedScanBatchingSupported() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(66, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isOffloadedScanBatchingSupported();
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

            public boolean isActivityAndEnergyReportingSupported() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(67, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isActivityAndEnergyReportingSupported();
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

            public boolean isLe2MPhySupported() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(68, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isLe2MPhySupported();
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

            public boolean isLeCodedPhySupported() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(69, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isLeCodedPhySupported();
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

            public boolean isLeExtendedAdvertisingSupported() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(70, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isLeExtendedAdvertisingSupported();
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

            public boolean isLePeriodicAdvertisingSupported() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(71, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isLePeriodicAdvertisingSupported();
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

            public int getLeMaximumAdvertisingDataLength() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(72, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getLeMaximumAdvertisingDataLength();
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

            public BluetoothActivityEnergyInfo reportActivityInfo() throws RemoteException {
                BluetoothActivityEnergyInfo _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(73, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reportActivityInfo();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = BluetoothActivityEnergyInfo.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    BluetoothActivityEnergyInfo _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean registerMetadataListener(IBluetoothMetadataListener listener, BluetoothDevice device) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    boolean _result = true;
                    if (device != null) {
                        _data.writeInt(1);
                        device.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(74, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().registerMetadataListener(listener, device);
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

            public boolean unregisterMetadataListener(BluetoothDevice device) throws RemoteException {
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
                    if (!this.mRemote.transact(75, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().unregisterMetadataListener(device);
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

            public boolean setMetadata(BluetoothDevice device, int key, byte[] value) throws RemoteException {
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
                    _data.writeInt(key);
                    _data.writeByteArray(value);
                    if (!this.mRemote.transact(76, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setMetadata(device, key, value);
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

            public byte[] getMetadata(BluetoothDevice device, int key) throws RemoteException {
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
                    _data.writeInt(key);
                    if (!this.mRemote.transact(77, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getMetadata(device, key);
                    }
                    _reply.readException();
                    byte[] _result = _reply.createByteArray();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void requestActivityInfo(ResultReceiver result) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (result != null) {
                        _data.writeInt(1);
                        result.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(78, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().requestActivityInfo(result);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onLeServiceUp() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(79, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onLeServiceUp();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void updateQuietModeStatus(boolean quietMode) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(quietMode);
                    if (this.mRemote.transact(80, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().updateQuietModeStatus(quietMode);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void onBrEdrDown() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(81, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onBrEdrDown();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int setSocketOpt(int type, int port, int optionName, byte[] optionVal, int optionLen) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeInt(port);
                    _data.writeInt(optionName);
                    _data.writeByteArray(optionVal);
                    _data.writeInt(optionLen);
                    if (!this.mRemote.transact(82, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setSocketOpt(type, port, optionName, optionVal, optionLen);
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

            public int getSocketOpt(int type, int port, int optionName, byte[] optionVal) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeInt(port);
                    _data.writeInt(optionName);
                    if (optionVal == null) {
                        _data.writeInt(-1);
                    } else {
                        _data.writeInt(optionVal.length);
                    }
                    if (!this.mRemote.transact(83, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getSocketOpt(type, port, optionName, optionVal);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.readByteArray(optionVal);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IBluetooth impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IBluetooth getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
