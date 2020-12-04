package android.bluetooth.le;

import android.bluetooth.BluetoothUuid;
import android.os.ParcelUuid;
import android.util.SparseArray;
import java.util.List;
import java.util.Map;

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

    /* JADX WARNING: Removed duplicated region for block: B:26:0x005f A[Catch:{ Exception -> 0x00c8 }] */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0061 A[Catch:{ Exception -> 0x00c8 }] */
    @android.annotation.UnsupportedAppUsage
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static android.bluetooth.le.ScanRecord parseFromBytes(byte[] r23) {
        /*
            r15 = r23
            r0 = 0
            if (r15 != 0) goto L_0x0006
            return r0
        L_0x0006:
            r1 = 0
            r2 = -1
            java.util.ArrayList r3 = new java.util.ArrayList
            r3.<init>()
            java.util.ArrayList r4 = new java.util.ArrayList
            r4.<init>()
            r14 = r4
            r4 = 0
            r5 = -2147483648(0xffffffff80000000, float:-0.0)
            android.util.SparseArray r6 = new android.util.SparseArray
            r6.<init>()
            r13 = r6
            android.util.ArrayMap r6 = new android.util.ArrayMap
            r6.<init>()
            r12 = r6
            r9 = r0
            r20 = r2
            r22 = r4
            r21 = r5
        L_0x0029:
            int r0 = r15.length     // Catch:{ Exception -> 0x00f6 }
            if (r1 >= r0) goto L_0x00cd
            int r2 = r1 + 1
            byte r0 = r15[r1]     // Catch:{ Exception -> 0x00ca }
            r1 = 255(0xff, float:3.57E-43)
            r0 = r0 & r1
            if (r0 != 0) goto L_0x0039
            r11 = r2
            goto L_0x00ce
        L_0x0039:
            int r4 = r0 + -1
            int r5 = r2 + 1
            byte r2 = r15[r2]     // Catch:{ Exception -> 0x00c8 }
            r2 = r2 & r1
            r6 = 38
            if (r2 == r6) goto L_0x00be
            if (r2 == r1) goto L_0x00a7
            r6 = 16
            r7 = 4
            r8 = 2
            switch(r2) {
                case 1: goto L_0x00a0;
                case 2: goto L_0x009c;
                case 3: goto L_0x009c;
                case 4: goto L_0x0098;
                case 5: goto L_0x0098;
                case 6: goto L_0x0094;
                case 7: goto L_0x0094;
                case 8: goto L_0x0088;
                case 9: goto L_0x0088;
                case 10: goto L_0x0083;
                default: goto L_0x004d;
            }     // Catch:{ Exception -> 0x00c8 }
        L_0x004d:
            switch(r2) {
                case 20: goto L_0x007f;
                case 21: goto L_0x007b;
                case 22: goto L_0x005a;
                default: goto L_0x0050;
            }     // Catch:{ Exception -> 0x00c8 }
        L_0x0050:
            switch(r2) {
                case 31: goto L_0x0055;
                case 32: goto L_0x005a;
                case 33: goto L_0x005a;
                default: goto L_0x0053;
            }     // Catch:{ Exception -> 0x00c8 }
        L_0x0053:
            goto L_0x00c4
        L_0x0055:
            parseServiceSolicitationUuid(r15, r5, r4, r7, r14)     // Catch:{ Exception -> 0x00c8 }
            goto L_0x00c4
        L_0x005a:
            r1 = 2
            r6 = 32
            if (r2 != r6) goto L_0x0061
            r1 = 4
            goto L_0x0067
        L_0x0061:
            r6 = 33
            if (r2 != r6) goto L_0x0067
            r1 = 16
        L_0x0067:
            byte[] r6 = extractBytes(r15, r5, r1)     // Catch:{ Exception -> 0x00c8 }
            android.os.ParcelUuid r7 = android.bluetooth.BluetoothUuid.parseUuidFrom(r6)     // Catch:{ Exception -> 0x00c8 }
            int r8 = r5 + r1
            int r10 = r4 - r1
            byte[] r8 = extractBytes(r15, r8, r10)     // Catch:{ Exception -> 0x00c8 }
            r12.put(r7, r8)     // Catch:{ Exception -> 0x00c8 }
            goto L_0x00c4
        L_0x007b:
            parseServiceSolicitationUuid(r15, r5, r4, r6, r14)     // Catch:{ Exception -> 0x00c8 }
            goto L_0x00c4
        L_0x007f:
            parseServiceSolicitationUuid(r15, r5, r4, r8, r14)     // Catch:{ Exception -> 0x00c8 }
            goto L_0x00c4
        L_0x0083:
            byte r1 = r15[r5]     // Catch:{ Exception -> 0x00c8 }
            r21 = r1
            goto L_0x00c4
        L_0x0088:
            java.lang.String r1 = new java.lang.String     // Catch:{ Exception -> 0x00c8 }
            byte[] r6 = extractBytes(r15, r5, r4)     // Catch:{ Exception -> 0x00c8 }
            r1.<init>(r6)     // Catch:{ Exception -> 0x00c8 }
            r22 = r1
            goto L_0x00c4
        L_0x0094:
            parseServiceUuid(r15, r5, r4, r6, r3)     // Catch:{ Exception -> 0x00c8 }
            goto L_0x00c4
        L_0x0098:
            parseServiceUuid(r15, r5, r4, r7, r3)     // Catch:{ Exception -> 0x00c8 }
            goto L_0x00c4
        L_0x009c:
            parseServiceUuid(r15, r5, r4, r8, r3)     // Catch:{ Exception -> 0x00c8 }
            goto L_0x00c4
        L_0x00a0:
            byte r6 = r15[r5]     // Catch:{ Exception -> 0x00c8 }
            r1 = r1 & r6
            r20 = r1
            goto L_0x00c4
        L_0x00a7:
            int r6 = r5 + 1
            byte r6 = r15[r6]     // Catch:{ Exception -> 0x00c8 }
            r6 = r6 & r1
            int r6 = r6 << 8
            byte r7 = r15[r5]     // Catch:{ Exception -> 0x00c8 }
            r1 = r1 & r7
            int r6 = r6 + r1
            int r1 = r5 + 2
            int r7 = r4 + -2
            byte[] r1 = extractBytes(r15, r1, r7)     // Catch:{ Exception -> 0x00c8 }
            r13.put(r6, r1)     // Catch:{ Exception -> 0x00c8 }
            goto L_0x00c4
        L_0x00be:
            byte[] r1 = extractBytes(r15, r5, r4)     // Catch:{ Exception -> 0x00c8 }
            r9 = r1
        L_0x00c4:
            int r1 = r5 + r4
            goto L_0x0029
        L_0x00c8:
            r0 = move-exception
            goto L_0x00f8
        L_0x00ca:
            r0 = move-exception
            r5 = r2
            goto L_0x00f8
        L_0x00cd:
            r11 = r1
        L_0x00ce:
            boolean r0 = r3.isEmpty()     // Catch:{ Exception -> 0x00f3 }
            if (r0 == 0) goto L_0x00d8
            r0 = 0
            r16 = r0
            goto L_0x00da
        L_0x00d8:
            r16 = r3
        L_0x00da:
            android.bluetooth.le.ScanRecord r0 = new android.bluetooth.le.ScanRecord     // Catch:{ Exception -> 0x00ee }
            r1 = r0
            r2 = r16
            r3 = r14
            r4 = r13
            r5 = r12
            r6 = r20
            r7 = r21
            r8 = r22
            r10 = r23
            r1.<init>(r2, r3, r4, r5, r6, r7, r8, r9, r10)     // Catch:{ Exception -> 0x00ee }
            return r0
        L_0x00ee:
            r0 = move-exception
            r5 = r11
            r3 = r16
            goto L_0x00f8
        L_0x00f3:
            r0 = move-exception
            r5 = r11
            goto L_0x00f8
        L_0x00f6:
            r0 = move-exception
            r5 = r1
        L_0x00f8:
            java.lang.String r1 = "ScanRecord"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r4 = "unable to parse scan record: "
            r2.append(r4)
            java.lang.String r4 = java.util.Arrays.toString(r23)
            r2.append(r4)
            java.lang.String r2 = r2.toString()
            android.util.Log.e(r1, r2)
            android.bluetooth.le.ScanRecord r1 = new android.bluetooth.le.ScanRecord
            r11 = 0
            r2 = 0
            r4 = 0
            r6 = 0
            r7 = -1
            r16 = -2147483648(0xffffffff80000000, float:-0.0)
            r17 = 0
            r18 = 0
            r10 = r1
            r8 = r12
            r12 = r2
            r2 = r13
            r13 = r4
            r4 = r14
            r14 = r6
            r15 = r7
            r19 = r23
            r10.<init>(r11, r12, r13, r14, r15, r16, r17, r18, r19)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.bluetooth.le.ScanRecord.parseFromBytes(byte[]):android.bluetooth.le.ScanRecord");
    }

    public String toString() {
        return "ScanRecord [mAdvertiseFlags=" + this.mAdvertiseFlags + ", mServiceUuids=" + this.mServiceUuids + ", mServiceSolicitationUuids=" + this.mServiceSolicitationUuids + ", mManufacturerSpecificData=" + BluetoothLeUtils.toString(this.mManufacturerSpecificData) + ", mServiceData=" + BluetoothLeUtils.toString(this.mServiceData) + ", mTxPowerLevel=" + this.mTxPowerLevel + ", mDeviceName=" + this.mDeviceName + ", mTDSData=" + BluetoothLeUtils.toString(this.mTDSData) + "]";
    }

    private static int parseServiceUuid(byte[] scanRecord, int currentPos, int dataLength, int uuidLength, List<ParcelUuid> serviceUuids) {
        while (dataLength > 0) {
            serviceUuids.add(BluetoothUuid.parseUuidFrom(extractBytes(scanRecord, currentPos, uuidLength)));
            dataLength -= uuidLength;
            currentPos += uuidLength;
        }
        return currentPos;
    }

    private static int parseServiceSolicitationUuid(byte[] scanRecord, int currentPos, int dataLength, int uuidLength, List<ParcelUuid> serviceSolicitationUuids) {
        while (dataLength > 0) {
            serviceSolicitationUuids.add(BluetoothUuid.parseUuidFrom(extractBytes(scanRecord, currentPos, uuidLength)));
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
