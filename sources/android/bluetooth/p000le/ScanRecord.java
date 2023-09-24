package android.bluetooth.p000le;

import android.annotation.UnsupportedAppUsage;
import android.bluetooth.BluetoothUuid;
import android.p007os.ParcelUuid;
import android.util.ArrayMap;
import android.util.Log;
import android.util.SparseArray;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/* renamed from: android.bluetooth.le.ScanRecord */
/* loaded from: classes.dex */
public final class ScanRecord {
    private static final int DATA_TYPE_FLAGS = 1;
    private static final int DATA_TYPE_LOCAL_NAME_COMPLETE = 9;
    private static final int DATA_TYPE_LOCAL_NAME_SHORT = 8;
    private static final int DATA_TYPE_MANUFACTURER_SPECIFIC_DATA = 255;
    private static final int DATA_TYPE_SERVICE_DATA_128_BIT = 33;
    private static final int DATA_TYPE_SERVICE_DATA_16_BIT = 22;
    private static final int DATA_TYPE_SERVICE_DATA_32_BIT = 32;
    private static final int DATA_TYPE_SERVICE_SOLICITATION_UUIDS_128_BIT = 21;
    private static final int DATA_TYPE_SERVICE_SOLICITATION_UUIDS_16_BIT = 20;
    private static final int DATA_TYPE_SERVICE_SOLICITATION_UUIDS_32_BIT = 31;
    private static final int DATA_TYPE_SERVICE_UUIDS_128_BIT_COMPLETE = 7;
    private static final int DATA_TYPE_SERVICE_UUIDS_128_BIT_PARTIAL = 6;
    private static final int DATA_TYPE_SERVICE_UUIDS_16_BIT_COMPLETE = 3;
    private static final int DATA_TYPE_SERVICE_UUIDS_16_BIT_PARTIAL = 2;
    private static final int DATA_TYPE_SERVICE_UUIDS_32_BIT_COMPLETE = 5;
    private static final int DATA_TYPE_SERVICE_UUIDS_32_BIT_PARTIAL = 4;
    private static final int DATA_TYPE_TRANSPORT_DISCOVERY_DATA = 38;
    private static final int DATA_TYPE_TX_POWER_LEVEL = 10;
    private static final String TAG = "ScanRecord";
    private final int mAdvertiseFlags;
    private final byte[] mBytes;
    private final String mDeviceName;
    private final SparseArray<byte[]> mManufacturerSpecificData;
    private final Map<ParcelUuid, byte[]> mServiceData;
    private final List<ParcelUuid> mServiceSolicitationUuids;
    private final List<ParcelUuid> mServiceUuids;
    private final byte[] mTDSData;
    private final int mTxPowerLevel;

    public int getAdvertiseFlags() {
        return this.mAdvertiseFlags;
    }

    public List<ParcelUuid> getServiceUuids() {
        return this.mServiceUuids;
    }

    public List<ParcelUuid> getServiceSolicitationUuids() {
        return this.mServiceSolicitationUuids;
    }

    public SparseArray<byte[]> getManufacturerSpecificData() {
        return this.mManufacturerSpecificData;
    }

    public byte[] getManufacturerSpecificData(int manufacturerId) {
        if (this.mManufacturerSpecificData == null) {
            return null;
        }
        return this.mManufacturerSpecificData.get(manufacturerId);
    }

    public Map<ParcelUuid, byte[]> getServiceData() {
        return this.mServiceData;
    }

    public byte[] getServiceData(ParcelUuid serviceDataUuid) {
        if (serviceDataUuid == null || this.mServiceData == null) {
            return null;
        }
        return this.mServiceData.get(serviceDataUuid);
    }

    public int getTxPowerLevel() {
        return this.mTxPowerLevel;
    }

    public String getDeviceName() {
        return this.mDeviceName;
    }

    public byte[] getTDSData() {
        return this.mTDSData;
    }

    public byte[] getBytes() {
        return this.mBytes;
    }

    private ScanRecord(List<ParcelUuid> serviceUuids, List<ParcelUuid> serviceSolicitationUuids, SparseArray<byte[]> manufacturerData, Map<ParcelUuid, byte[]> serviceData, int advertiseFlags, int txPowerLevel, String localName, byte[] tdsData, byte[] bytes) {
        this.mServiceSolicitationUuids = serviceSolicitationUuids;
        this.mServiceUuids = serviceUuids;
        this.mManufacturerSpecificData = manufacturerData;
        this.mServiceData = serviceData;
        this.mDeviceName = localName;
        this.mAdvertiseFlags = advertiseFlags;
        this.mTxPowerLevel = txPowerLevel;
        this.mTDSData = tdsData;
        this.mBytes = bytes;
    }

    @UnsupportedAppUsage
    public static ScanRecord parseFromBytes(byte[] scanRecord) {
        int currentPos;
        if (scanRecord == null) {
            return null;
        }
        int currentPos2 = 0;
        ArrayList arrayList = new ArrayList();
        List<ParcelUuid> serviceSolicitationUuids = new ArrayList<>();
        SparseArray<byte[]> manufacturerData = new SparseArray<>();
        Map<ParcelUuid, byte[]> serviceData = new ArrayMap<>();
        byte[] tdsData = null;
        int advertiseFlag = -1;
        String localName = null;
        int txPowerLevel = -2147483648;
        while (true) {
            try {
                if (currentPos2 < scanRecord.length) {
                    int currentPos3 = currentPos2 + 1;
                    try {
                        int length = scanRecord[currentPos2] & 255;
                        if (length != 0) {
                            int dataLength = length - 1;
                            int currentPos4 = currentPos3 + 1;
                            try {
                                int fieldType = scanRecord[currentPos3] & 255;
                                if (fieldType == 38) {
                                    byte[] tdsData2 = extractBytes(scanRecord, currentPos4, dataLength);
                                    tdsData = tdsData2;
                                } else if (fieldType == 255) {
                                    int manufacturerId = ((scanRecord[currentPos4 + 1] & 255) << 8) + (255 & scanRecord[currentPos4]);
                                    byte[] manufacturerDataBytes = extractBytes(scanRecord, currentPos4 + 2, dataLength - 2);
                                    manufacturerData.put(manufacturerId, manufacturerDataBytes);
                                } else {
                                    switch (fieldType) {
                                        case 1:
                                            int advertiseFlag2 = 255 & scanRecord[currentPos4];
                                            advertiseFlag = advertiseFlag2;
                                            continue;
                                        case 2:
                                        case 3:
                                            parseServiceUuid(scanRecord, currentPos4, dataLength, 2, arrayList);
                                            continue;
                                        case 4:
                                        case 5:
                                            parseServiceUuid(scanRecord, currentPos4, dataLength, 4, arrayList);
                                            continue;
                                        case 6:
                                        case 7:
                                            parseServiceUuid(scanRecord, currentPos4, dataLength, 16, arrayList);
                                            continue;
                                        case 8:
                                        case 9:
                                            localName = new String(extractBytes(scanRecord, currentPos4, dataLength));
                                            continue;
                                        case 10:
                                            txPowerLevel = scanRecord[currentPos4];
                                            continue;
                                        default:
                                            switch (fieldType) {
                                                case 20:
                                                    parseServiceSolicitationUuid(scanRecord, currentPos4, dataLength, 2, serviceSolicitationUuids);
                                                    break;
                                                case 21:
                                                    parseServiceSolicitationUuid(scanRecord, currentPos4, dataLength, 16, serviceSolicitationUuids);
                                                    break;
                                                default:
                                                    switch (fieldType) {
                                                        case 31:
                                                            parseServiceSolicitationUuid(scanRecord, currentPos4, dataLength, 4, serviceSolicitationUuids);
                                                            break;
                                                        case 32:
                                                        case 33:
                                                            break;
                                                        default:
                                                            continue;
                                                    }
                                                case 22:
                                                    int serviceUuidLength = 2;
                                                    if (fieldType == 32) {
                                                        serviceUuidLength = 4;
                                                    } else if (fieldType == 33) {
                                                        serviceUuidLength = 16;
                                                    }
                                                    byte[] serviceDataUuidBytes = extractBytes(scanRecord, currentPos4, serviceUuidLength);
                                                    ParcelUuid serviceDataUuid = BluetoothUuid.parseUuidFrom(serviceDataUuidBytes);
                                                    byte[] serviceDataArray = extractBytes(scanRecord, currentPos4 + serviceUuidLength, dataLength - serviceUuidLength);
                                                    serviceData.put(serviceDataUuid, serviceDataArray);
                                                    break;
                                            }
                                    }
                                }
                                currentPos2 = currentPos4 + dataLength;
                            } catch (Exception e) {
                                Log.m70e(TAG, "unable to parse scan record: " + Arrays.toString(scanRecord));
                                return new ScanRecord(null, null, null, null, -1, Integer.MIN_VALUE, null, null, scanRecord);
                            }
                        } else {
                            currentPos = currentPos3;
                        }
                    } catch (Exception e2) {
                    }
                } else {
                    currentPos = currentPos2;
                }
            } catch (Exception e3) {
            }
        }
        try {
            return new ScanRecord(arrayList.isEmpty() ? null : arrayList, serviceSolicitationUuids, manufacturerData, serviceData, advertiseFlag, txPowerLevel, localName, tdsData, scanRecord);
        } catch (Exception e4) {
            Log.m70e(TAG, "unable to parse scan record: " + Arrays.toString(scanRecord));
            return new ScanRecord(null, null, null, null, -1, Integer.MIN_VALUE, null, null, scanRecord);
        }
    }

    public String toString() {
        return "ScanRecord [mAdvertiseFlags=" + this.mAdvertiseFlags + ", mServiceUuids=" + this.mServiceUuids + ", mServiceSolicitationUuids=" + this.mServiceSolicitationUuids + ", mManufacturerSpecificData=" + BluetoothLeUtils.toString(this.mManufacturerSpecificData) + ", mServiceData=" + BluetoothLeUtils.toString(this.mServiceData) + ", mTxPowerLevel=" + this.mTxPowerLevel + ", mDeviceName=" + this.mDeviceName + ", mTDSData=" + BluetoothLeUtils.toString(this.mTDSData) + "]";
    }

    private static int parseServiceUuid(byte[] scanRecord, int currentPos, int dataLength, int uuidLength, List<ParcelUuid> serviceUuids) {
        while (dataLength > 0) {
            byte[] uuidBytes = extractBytes(scanRecord, currentPos, uuidLength);
            serviceUuids.add(BluetoothUuid.parseUuidFrom(uuidBytes));
            dataLength -= uuidLength;
            currentPos += uuidLength;
        }
        return currentPos;
    }

    private static int parseServiceSolicitationUuid(byte[] scanRecord, int currentPos, int dataLength, int uuidLength, List<ParcelUuid> serviceSolicitationUuids) {
        while (dataLength > 0) {
            byte[] uuidBytes = extractBytes(scanRecord, currentPos, uuidLength);
            serviceSolicitationUuids.add(BluetoothUuid.parseUuidFrom(uuidBytes));
            dataLength -= uuidLength;
            currentPos += uuidLength;
        }
        return currentPos;
    }

    private static byte[] extractBytes(byte[] scanRecord, int start, int length) {
        byte[] bytes = new byte[length];
        System.arraycopy(scanRecord, start, bytes, 0, length);
        return bytes;
    }
}
