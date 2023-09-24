package android.bluetooth.p000le;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.p007os.Parcel;
import android.p007os.ParcelUuid;
import android.p007os.Parcelable;
import com.android.internal.util.BitUtils;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/* renamed from: android.bluetooth.le.ScanFilter */
/* loaded from: classes.dex */
public final class ScanFilter implements Parcelable {
    public static final int WIFI_ALLIANCE_ORG_ID = 2;
    private final String mDeviceAddress;
    private final String mDeviceName;
    private final byte[] mManufacturerData;
    private final byte[] mManufacturerDataMask;
    private final int mManufacturerId;
    private final int mOrgId;
    private final byte[] mServiceData;
    private final byte[] mServiceDataMask;
    private final ParcelUuid mServiceDataUuid;
    private final ParcelUuid mServiceSolicitationUuid;
    private final ParcelUuid mServiceSolicitationUuidMask;
    private final ParcelUuid mServiceUuid;
    private final ParcelUuid mServiceUuidMask;
    private final int mTDSFlags;
    private final int mTDSFlagsMask;
    private final byte[] mWifiNANHash;
    public static final ScanFilter EMPTY = new Builder().build();
    public static final Parcelable.Creator<ScanFilter> CREATOR = new Parcelable.Creator<ScanFilter>() { // from class: android.bluetooth.le.ScanFilter.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public ScanFilter[] newArray(int size) {
            return new ScanFilter[size];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public ScanFilter createFromParcel(Parcel in) {
            Builder builder = new Builder();
            if (in.readInt() == 1) {
                builder.setDeviceName(in.readString());
            }
            if (in.readInt() == 1) {
                builder.setDeviceAddress(in.readString());
            }
            if (in.readInt() == 1) {
                ParcelUuid uuid = (ParcelUuid) in.readParcelable(ParcelUuid.class.getClassLoader());
                builder.setServiceUuid(uuid);
                if (in.readInt() == 1) {
                    ParcelUuid uuidMask = (ParcelUuid) in.readParcelable(ParcelUuid.class.getClassLoader());
                    builder.setServiceUuid(uuid, uuidMask);
                }
            }
            if (in.readInt() == 1) {
                ParcelUuid solicitationUuid = (ParcelUuid) in.readParcelable(ParcelUuid.class.getClassLoader());
                builder.setServiceSolicitationUuid(solicitationUuid);
                if (in.readInt() == 1) {
                    ParcelUuid solicitationUuidMask = (ParcelUuid) in.readParcelable(ParcelUuid.class.getClassLoader());
                    builder.setServiceSolicitationUuid(solicitationUuid, solicitationUuidMask);
                }
            }
            if (in.readInt() == 1) {
                ParcelUuid servcieDataUuid = (ParcelUuid) in.readParcelable(ParcelUuid.class.getClassLoader());
                if (in.readInt() == 1) {
                    int serviceDataLength = in.readInt();
                    byte[] serviceData = new byte[serviceDataLength];
                    in.readByteArray(serviceData);
                    if (in.readInt() == 0) {
                        builder.setServiceData(servcieDataUuid, serviceData);
                    } else {
                        int serviceDataMaskLength = in.readInt();
                        byte[] serviceDataMask = new byte[serviceDataMaskLength];
                        in.readByteArray(serviceDataMask);
                        builder.setServiceData(servcieDataUuid, serviceData, serviceDataMask);
                    }
                }
            }
            int manufacturerId = in.readInt();
            if (in.readInt() == 1) {
                int manufacturerDataLength = in.readInt();
                byte[] manufacturerData = new byte[manufacturerDataLength];
                in.readByteArray(manufacturerData);
                if (in.readInt() == 0) {
                    builder.setManufacturerData(manufacturerId, manufacturerData);
                } else {
                    int manufacturerDataMaskLength = in.readInt();
                    byte[] manufacturerDataMask = new byte[manufacturerDataMaskLength];
                    in.readByteArray(manufacturerDataMask);
                    builder.setManufacturerData(manufacturerId, manufacturerData, manufacturerDataMask);
                }
            }
            int orgId = in.readInt();
            if (in.readInt() == 1) {
                int tdsFlags = in.readInt();
                int tdsFlagsMask = in.readInt();
                if (in.readInt() == 1) {
                    int wifiNANHashLength = in.readInt();
                    byte[] wifiNanHash = new byte[wifiNANHashLength];
                    in.readByteArray(wifiNanHash);
                    builder.setTransportDiscoveryData(orgId, tdsFlags, tdsFlagsMask, wifiNanHash);
                } else {
                    builder.setTransportDiscoveryData(orgId, tdsFlags, tdsFlagsMask, null);
                }
            }
            return builder.build();
        }
    };

    private ScanFilter(String name, String deviceAddress, ParcelUuid uuid, ParcelUuid uuidMask, ParcelUuid solicitationUuid, ParcelUuid solicitationUuidMask, ParcelUuid serviceDataUuid, byte[] serviceData, byte[] serviceDataMask, int manufacturerId, byte[] manufacturerData, byte[] manufacturerDataMask, int orgId, int TDSFlags, int TDSFlagsMask, byte[] wifiNANHash) {
        this.mDeviceName = name;
        this.mServiceUuid = uuid;
        this.mServiceUuidMask = uuidMask;
        this.mServiceSolicitationUuid = solicitationUuid;
        this.mServiceSolicitationUuidMask = solicitationUuidMask;
        this.mDeviceAddress = deviceAddress;
        this.mServiceDataUuid = serviceDataUuid;
        this.mServiceData = serviceData;
        this.mServiceDataMask = serviceDataMask;
        this.mManufacturerId = manufacturerId;
        this.mManufacturerData = manufacturerData;
        this.mManufacturerDataMask = manufacturerDataMask;
        this.mOrgId = orgId;
        this.mTDSFlags = TDSFlags;
        this.mTDSFlagsMask = TDSFlagsMask;
        this.mWifiNANHash = wifiNANHash;
    }

    @Override // android.p007os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.p007os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mDeviceName == null ? 0 : 1);
        if (this.mDeviceName != null) {
            dest.writeString(this.mDeviceName);
        }
        dest.writeInt(this.mDeviceAddress == null ? 0 : 1);
        if (this.mDeviceAddress != null) {
            dest.writeString(this.mDeviceAddress);
        }
        dest.writeInt(this.mServiceUuid == null ? 0 : 1);
        if (this.mServiceUuid != null) {
            dest.writeParcelable(this.mServiceUuid, flags);
            dest.writeInt(this.mServiceUuidMask == null ? 0 : 1);
            if (this.mServiceUuidMask != null) {
                dest.writeParcelable(this.mServiceUuidMask, flags);
            }
        }
        dest.writeInt(this.mServiceSolicitationUuid == null ? 0 : 1);
        if (this.mServiceSolicitationUuid != null) {
            dest.writeParcelable(this.mServiceSolicitationUuid, flags);
            dest.writeInt(this.mServiceSolicitationUuidMask == null ? 0 : 1);
            if (this.mServiceSolicitationUuidMask != null) {
                dest.writeParcelable(this.mServiceSolicitationUuidMask, flags);
            }
        }
        dest.writeInt(this.mServiceDataUuid == null ? 0 : 1);
        if (this.mServiceDataUuid != null) {
            dest.writeParcelable(this.mServiceDataUuid, flags);
            dest.writeInt(this.mServiceData == null ? 0 : 1);
            if (this.mServiceData != null) {
                dest.writeInt(this.mServiceData.length);
                dest.writeByteArray(this.mServiceData);
                dest.writeInt(this.mServiceDataMask == null ? 0 : 1);
                if (this.mServiceDataMask != null) {
                    dest.writeInt(this.mServiceDataMask.length);
                    dest.writeByteArray(this.mServiceDataMask);
                }
            }
        }
        dest.writeInt(this.mManufacturerId);
        dest.writeInt(this.mManufacturerData == null ? 0 : 1);
        if (this.mManufacturerData != null) {
            dest.writeInt(this.mManufacturerData.length);
            dest.writeByteArray(this.mManufacturerData);
            dest.writeInt(this.mManufacturerDataMask == null ? 0 : 1);
            if (this.mManufacturerDataMask != null) {
                dest.writeInt(this.mManufacturerDataMask.length);
                dest.writeByteArray(this.mManufacturerDataMask);
            }
        }
        dest.writeInt(this.mOrgId);
        dest.writeInt(this.mOrgId < 0 ? 0 : 1);
        if (this.mOrgId >= 0) {
            dest.writeInt(this.mTDSFlags);
            dest.writeInt(this.mTDSFlagsMask);
            dest.writeInt(this.mWifiNANHash == null ? 0 : 1);
            if (this.mWifiNANHash != null) {
                dest.writeInt(this.mWifiNANHash.length);
                dest.writeByteArray(this.mWifiNANHash);
            }
        }
    }

    public String getDeviceName() {
        return this.mDeviceName;
    }

    public ParcelUuid getServiceUuid() {
        return this.mServiceUuid;
    }

    public ParcelUuid getServiceUuidMask() {
        return this.mServiceUuidMask;
    }

    public ParcelUuid getServiceSolicitationUuid() {
        return this.mServiceSolicitationUuid;
    }

    public ParcelUuid getServiceSolicitationUuidMask() {
        return this.mServiceSolicitationUuidMask;
    }

    public String getDeviceAddress() {
        return this.mDeviceAddress;
    }

    public byte[] getServiceData() {
        return this.mServiceData;
    }

    public byte[] getServiceDataMask() {
        return this.mServiceDataMask;
    }

    public ParcelUuid getServiceDataUuid() {
        return this.mServiceDataUuid;
    }

    public int getManufacturerId() {
        return this.mManufacturerId;
    }

    public byte[] getManufacturerData() {
        return this.mManufacturerData;
    }

    public byte[] getManufacturerDataMask() {
        return this.mManufacturerDataMask;
    }

    public int getOrgId() {
        return this.mOrgId;
    }

    public int getTDSFlags() {
        return this.mTDSFlags;
    }

    public int getTDSFlagsMask() {
        return this.mTDSFlagsMask;
    }

    public byte[] getWifiNANHash() {
        return this.mWifiNANHash;
    }

    public boolean matches(ScanResult scanResult) {
        byte[] tdsData;
        if (scanResult == null) {
            return false;
        }
        BluetoothDevice device = scanResult.getDevice();
        if (this.mDeviceAddress != null && (device == null || !this.mDeviceAddress.equals(device.getAddress()))) {
            return false;
        }
        ScanRecord scanRecord = scanResult.getScanRecord();
        if (scanRecord == null && (this.mDeviceName != null || this.mServiceUuid != null || this.mManufacturerData != null || this.mServiceData != null || this.mServiceSolicitationUuid != null)) {
            return false;
        }
        if (this.mDeviceName != null && !this.mDeviceName.equals(scanRecord.getDeviceName())) {
            return false;
        }
        if (this.mServiceUuid != null && !matchesServiceUuids(this.mServiceUuid, this.mServiceUuidMask, scanRecord.getServiceUuids())) {
            return false;
        }
        if (this.mServiceSolicitationUuid != null && !matchesServiceSolicitationUuids(this.mServiceSolicitationUuid, this.mServiceSolicitationUuidMask, scanRecord.getServiceSolicitationUuids())) {
            return false;
        }
        if (this.mServiceDataUuid != null && !matchesPartialData(this.mServiceData, this.mServiceDataMask, scanRecord.getServiceData(this.mServiceDataUuid))) {
            return false;
        }
        if (this.mManufacturerId < 0 || matchesPartialData(this.mManufacturerData, this.mManufacturerDataMask, scanRecord.getManufacturerSpecificData(this.mManufacturerId))) {
            return this.mOrgId < 0 || (tdsData = scanRecord.getTDSData()) == null || tdsData.length <= 0 || (this.mOrgId == tdsData[0] && (this.mTDSFlags & this.mTDSFlagsMask) == (tdsData[1] & this.mTDSFlagsMask));
        }
        return false;
    }

    public static boolean matchesServiceUuids(ParcelUuid uuid, ParcelUuid parcelUuidMask, List<ParcelUuid> uuids) {
        if (uuid == null) {
            return true;
        }
        if (uuids == null) {
            return false;
        }
        for (ParcelUuid parcelUuid : uuids) {
            UUID uuidMask = parcelUuidMask == null ? null : parcelUuidMask.getUuid();
            if (matchesServiceUuid(uuid.getUuid(), uuidMask, parcelUuid.getUuid())) {
                return true;
            }
        }
        return false;
    }

    private static boolean matchesServiceUuid(UUID uuid, UUID mask, UUID data) {
        return BitUtils.maskedEquals(data, uuid, mask);
    }

    private static boolean matchesServiceSolicitationUuids(ParcelUuid solicitationUuid, ParcelUuid parcelSolicitationUuidMask, List<ParcelUuid> solicitationUuids) {
        if (solicitationUuid == null) {
            return true;
        }
        if (solicitationUuids == null) {
            return false;
        }
        for (ParcelUuid parcelSolicitationUuid : solicitationUuids) {
            UUID solicitationUuidMask = parcelSolicitationUuidMask == null ? null : parcelSolicitationUuidMask.getUuid();
            if (matchesServiceUuid(solicitationUuid.getUuid(), solicitationUuidMask, parcelSolicitationUuid.getUuid())) {
                return true;
            }
        }
        return false;
    }

    private static boolean matchesServiceSolicitationUuid(UUID solicitationUuid, UUID solicitationUuidMask, UUID data) {
        return BitUtils.maskedEquals(data, solicitationUuid, solicitationUuidMask);
    }

    private boolean matchesPartialData(byte[] data, byte[] dataMask, byte[] parsedData) {
        if (parsedData == null || parsedData.length < data.length) {
            return false;
        }
        if (dataMask == null) {
            for (int i = 0; i < data.length; i++) {
                if (parsedData[i] != data[i]) {
                    return false;
                }
            }
            return true;
        }
        for (int i2 = 0; i2 < data.length; i2++) {
            if ((dataMask[i2] & parsedData[i2]) != (dataMask[i2] & data[i2])) {
                return false;
            }
        }
        return true;
    }

    public String toString() {
        return "BluetoothLeScanFilter [mDeviceName=" + this.mDeviceName + ", mDeviceAddress=" + this.mDeviceAddress + ", mUuid=" + this.mServiceUuid + ", mUuidMask=" + this.mServiceUuidMask + ", mServiceSolicitationUuid=" + this.mServiceSolicitationUuid + ", mServiceSolicitationUuidMask=" + this.mServiceSolicitationUuidMask + ", mServiceDataUuid=" + Objects.toString(this.mServiceDataUuid) + ", mServiceData=" + Arrays.toString(this.mServiceData) + ", mServiceDataMask=" + Arrays.toString(this.mServiceDataMask) + ", mManufacturerId=" + this.mManufacturerId + ", mManufacturerData=" + Arrays.toString(this.mManufacturerData) + ", mManufacturerDataMask=" + Arrays.toString(this.mManufacturerDataMask) + ", mOrganizationId=" + this.mOrgId + ", mTDSFlags=" + this.mTDSFlags + ", mTDSFlagsMask=" + this.mTDSFlagsMask + ", mWifiNANHash=" + Arrays.toString(this.mWifiNANHash) + "]";
    }

    public int hashCode() {
        return Objects.hash(this.mDeviceName, this.mDeviceAddress, Integer.valueOf(this.mManufacturerId), Integer.valueOf(Arrays.hashCode(this.mManufacturerData)), Integer.valueOf(Arrays.hashCode(this.mManufacturerDataMask)), this.mServiceDataUuid, Integer.valueOf(Arrays.hashCode(this.mServiceData)), Integer.valueOf(Arrays.hashCode(this.mServiceDataMask)), this.mServiceUuid, this.mServiceUuidMask, this.mServiceSolicitationUuid, this.mServiceSolicitationUuidMask, Integer.valueOf(this.mOrgId), Integer.valueOf(this.mTDSFlags), Integer.valueOf(this.mTDSFlagsMask), Integer.valueOf(Arrays.hashCode(this.mWifiNANHash)));
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ScanFilter other = (ScanFilter) obj;
        if (Objects.equals(this.mDeviceName, other.mDeviceName) && Objects.equals(this.mDeviceAddress, other.mDeviceAddress) && this.mManufacturerId == other.mManufacturerId && Objects.deepEquals(this.mManufacturerData, other.mManufacturerData) && Objects.deepEquals(this.mManufacturerDataMask, other.mManufacturerDataMask) && Objects.equals(this.mServiceDataUuid, other.mServiceDataUuid) && Objects.deepEquals(this.mServiceData, other.mServiceData) && Objects.deepEquals(this.mServiceDataMask, other.mServiceDataMask) && Objects.equals(this.mServiceUuid, other.mServiceUuid) && Objects.equals(this.mServiceUuidMask, other.mServiceUuidMask) && Objects.equals(this.mServiceSolicitationUuid, other.mServiceSolicitationUuid) && Objects.equals(this.mServiceSolicitationUuidMask, other.mServiceSolicitationUuidMask) && this.mOrgId == other.mOrgId && this.mTDSFlags == other.mTDSFlags && this.mTDSFlagsMask == other.mTDSFlagsMask && Objects.deepEquals(this.mWifiNANHash, other.mWifiNANHash)) {
            return true;
        }
        return false;
    }

    public boolean isAllFieldsEmpty() {
        return EMPTY.equals(this);
    }

    /* renamed from: android.bluetooth.le.ScanFilter$Builder */
    /* loaded from: classes.dex */
    public static final class Builder {
        private String mDeviceAddress;
        private String mDeviceName;
        private byte[] mManufacturerData;
        private byte[] mManufacturerDataMask;
        private byte[] mServiceData;
        private byte[] mServiceDataMask;
        private ParcelUuid mServiceDataUuid;
        private ParcelUuid mServiceSolicitationUuid;
        private ParcelUuid mServiceSolicitationUuidMask;
        private ParcelUuid mServiceUuid;
        private ParcelUuid mUuidMask;
        private byte[] mWifiNANHash;
        private int mManufacturerId = -1;
        private int mOrgId = -1;
        private int mTDSFlags = -1;
        private int mTDSFlagsMask = -1;

        public Builder setDeviceName(String deviceName) {
            this.mDeviceName = deviceName;
            return this;
        }

        public Builder setDeviceAddress(String deviceAddress) {
            if (deviceAddress != null && !BluetoothAdapter.checkBluetoothAddress(deviceAddress)) {
                throw new IllegalArgumentException("invalid device address " + deviceAddress);
            }
            this.mDeviceAddress = deviceAddress;
            return this;
        }

        public Builder setServiceUuid(ParcelUuid serviceUuid) {
            this.mServiceUuid = serviceUuid;
            this.mUuidMask = null;
            return this;
        }

        public Builder setServiceUuid(ParcelUuid serviceUuid, ParcelUuid uuidMask) {
            if (this.mUuidMask != null && this.mServiceUuid == null) {
                throw new IllegalArgumentException("uuid is null while uuidMask is not null!");
            }
            this.mServiceUuid = serviceUuid;
            this.mUuidMask = uuidMask;
            return this;
        }

        public Builder setServiceSolicitationUuid(ParcelUuid serviceSolicitationUuid) {
            this.mServiceSolicitationUuid = serviceSolicitationUuid;
            if (serviceSolicitationUuid == null) {
                this.mServiceSolicitationUuidMask = null;
            }
            return this;
        }

        public Builder setServiceSolicitationUuid(ParcelUuid serviceSolicitationUuid, ParcelUuid solicitationUuidMask) {
            if (solicitationUuidMask != null && serviceSolicitationUuid == null) {
                throw new IllegalArgumentException("SolicitationUuid is null while SolicitationUuidMask is not null!");
            }
            this.mServiceSolicitationUuid = serviceSolicitationUuid;
            this.mServiceSolicitationUuidMask = solicitationUuidMask;
            return this;
        }

        public Builder setServiceData(ParcelUuid serviceDataUuid, byte[] serviceData) {
            if (serviceDataUuid == null) {
                throw new IllegalArgumentException("serviceDataUuid is null");
            }
            this.mServiceDataUuid = serviceDataUuid;
            this.mServiceData = serviceData;
            this.mServiceDataMask = null;
            return this;
        }

        public Builder setServiceData(ParcelUuid serviceDataUuid, byte[] serviceData, byte[] serviceDataMask) {
            if (serviceDataUuid == null) {
                throw new IllegalArgumentException("serviceDataUuid is null");
            }
            if (this.mServiceDataMask != null) {
                if (this.mServiceData == null) {
                    throw new IllegalArgumentException("serviceData is null while serviceDataMask is not null");
                }
                if (this.mServiceData.length != this.mServiceDataMask.length) {
                    throw new IllegalArgumentException("size mismatch for service data and service data mask");
                }
            }
            this.mServiceDataUuid = serviceDataUuid;
            this.mServiceData = serviceData;
            this.mServiceDataMask = serviceDataMask;
            return this;
        }

        public Builder setManufacturerData(int manufacturerId, byte[] manufacturerData) {
            if (manufacturerData != null && manufacturerId < 0) {
                throw new IllegalArgumentException("invalid manufacture id");
            }
            this.mManufacturerId = manufacturerId;
            this.mManufacturerData = manufacturerData;
            this.mManufacturerDataMask = null;
            return this;
        }

        public Builder setManufacturerData(int manufacturerId, byte[] manufacturerData, byte[] manufacturerDataMask) {
            if (manufacturerData != null && manufacturerId < 0) {
                throw new IllegalArgumentException("invalid manufacture id");
            }
            if (this.mManufacturerDataMask != null) {
                if (this.mManufacturerData == null) {
                    throw new IllegalArgumentException("manufacturerData is null while manufacturerDataMask is not null");
                }
                if (this.mManufacturerData.length != this.mManufacturerDataMask.length) {
                    throw new IllegalArgumentException("size mismatch for manufacturerData and manufacturerDataMask");
                }
            }
            this.mManufacturerId = manufacturerId;
            this.mManufacturerData = manufacturerData;
            this.mManufacturerDataMask = manufacturerDataMask;
            return this;
        }

        public Builder setTransportDiscoveryData(int orgId, int TDSFlags, int TDSFlagsMask, byte[] wifiNANHash) {
            if (orgId < 0) {
                throw new IllegalArgumentException("invalid organization id");
            }
            if (orgId != 2 && wifiNANHash != null) {
                throw new IllegalArgumentException("Wifi NAN Hash is not null for non-Wifi Org Id");
            }
            this.mOrgId = orgId;
            this.mTDSFlags = TDSFlags;
            this.mTDSFlagsMask = TDSFlagsMask;
            this.mWifiNANHash = wifiNANHash;
            return this;
        }

        public ScanFilter build() {
            return new ScanFilter(this.mDeviceName, this.mDeviceAddress, this.mServiceUuid, this.mUuidMask, this.mServiceSolicitationUuid, this.mServiceSolicitationUuidMask, this.mServiceDataUuid, this.mServiceData, this.mServiceDataMask, this.mManufacturerId, this.mManufacturerData, this.mManufacturerDataMask, this.mOrgId, this.mTDSFlags, this.mTDSFlagsMask, this.mWifiNANHash);
        }
    }
}
