package android.bluetooth.p000le;

import android.p007os.Parcel;
import android.p007os.ParcelUuid;
import android.p007os.Parcelable;
import android.util.ArrayMap;
import android.util.SparseArray;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/* renamed from: android.bluetooth.le.AdvertiseData */
/* loaded from: classes.dex */
public final class AdvertiseData implements Parcelable {
    public static final Parcelable.Creator<AdvertiseData> CREATOR = new Parcelable.Creator<AdvertiseData>() { // from class: android.bluetooth.le.AdvertiseData.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public AdvertiseData[] newArray(int size) {
            return new AdvertiseData[size];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public AdvertiseData createFromParcel(Parcel in) {
            Builder builder = new Builder();
            ArrayList<ParcelUuid> uuids = in.createTypedArrayList(ParcelUuid.CREATOR);
            Iterator<ParcelUuid> it = uuids.iterator();
            while (it.hasNext()) {
                ParcelUuid uuid = it.next();
                builder.addServiceUuid(uuid);
            }
            int manufacturerSize = in.readInt();
            for (int i = 0; i < manufacturerSize; i++) {
                int manufacturerId = in.readInt();
                byte[] manufacturerData = in.createByteArray();
                builder.addManufacturerData(manufacturerId, manufacturerData);
            }
            int serviceDataSize = in.readInt();
            for (int i2 = 0; i2 < serviceDataSize; i2++) {
                ParcelUuid serviceDataUuid = (ParcelUuid) in.readTypedObject(ParcelUuid.CREATOR);
                byte[] serviceData = in.createByteArray();
                builder.addServiceData(serviceDataUuid, serviceData);
            }
            int i3 = in.readByte();
            builder.setIncludeTxPowerLevel(i3 == 1);
            builder.setIncludeDeviceName(in.readByte() == 1);
            int transportDiscoveryDataSize = in.readInt();
            if (transportDiscoveryDataSize > 0) {
                byte[] transportDiscoveryData = in.createByteArray();
                builder.addTransportDiscoveryData(transportDiscoveryData);
            }
            return builder.build();
        }
    };
    private final boolean mIncludeDeviceName;
    private final boolean mIncludeTxPowerLevel;
    private final SparseArray<byte[]> mManufacturerSpecificData;
    private final Map<ParcelUuid, byte[]> mServiceData;
    private final List<ParcelUuid> mServiceUuids;
    private final byte[] mTransportDiscoveryData;

    private AdvertiseData(List<ParcelUuid> serviceUuids, SparseArray<byte[]> manufacturerData, Map<ParcelUuid, byte[]> serviceData, boolean includeTxPowerLevel, boolean includeDeviceName, byte[] transportDiscoveryData) {
        this.mServiceUuids = serviceUuids;
        this.mManufacturerSpecificData = manufacturerData;
        this.mServiceData = serviceData;
        this.mIncludeTxPowerLevel = includeTxPowerLevel;
        this.mIncludeDeviceName = includeDeviceName;
        this.mTransportDiscoveryData = transportDiscoveryData;
    }

    public List<ParcelUuid> getServiceUuids() {
        return this.mServiceUuids;
    }

    public SparseArray<byte[]> getManufacturerSpecificData() {
        return this.mManufacturerSpecificData;
    }

    public Map<ParcelUuid, byte[]> getServiceData() {
        return this.mServiceData;
    }

    public boolean getIncludeTxPowerLevel() {
        return this.mIncludeTxPowerLevel;
    }

    public boolean getIncludeDeviceName() {
        return this.mIncludeDeviceName;
    }

    public byte[] getTransportDiscoveryData() {
        return this.mTransportDiscoveryData;
    }

    public int hashCode() {
        return Objects.hash(this.mServiceUuids, this.mManufacturerSpecificData, this.mServiceData, Boolean.valueOf(this.mIncludeDeviceName), Boolean.valueOf(this.mIncludeTxPowerLevel), this.mTransportDiscoveryData);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        AdvertiseData other = (AdvertiseData) obj;
        if (Objects.equals(this.mServiceUuids, other.mServiceUuids) && BluetoothLeUtils.equals(this.mManufacturerSpecificData, other.mManufacturerSpecificData) && BluetoothLeUtils.equals(this.mServiceData, other.mServiceData) && this.mIncludeDeviceName == other.mIncludeDeviceName && this.mIncludeTxPowerLevel == other.mIncludeTxPowerLevel && BluetoothLeUtils.equals(this.mTransportDiscoveryData, other.mTransportDiscoveryData)) {
            return true;
        }
        return false;
    }

    public String toString() {
        return "AdvertiseData [mServiceUuids=" + this.mServiceUuids + ", mManufacturerSpecificData=" + BluetoothLeUtils.toString(this.mManufacturerSpecificData) + ", mServiceData=" + BluetoothLeUtils.toString(this.mServiceData) + ", mIncludeTxPowerLevel=" + this.mIncludeTxPowerLevel + ", mIncludeDeviceName=" + this.mIncludeDeviceName + ", mTransportDiscoveryData=" + BluetoothLeUtils.toString(this.mTransportDiscoveryData) + "]";
    }

    @Override // android.p007os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.p007os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedArray((ParcelUuid[]) this.mServiceUuids.toArray(new ParcelUuid[this.mServiceUuids.size()]), flags);
        dest.writeInt(this.mManufacturerSpecificData.size());
        for (int i = 0; i < this.mManufacturerSpecificData.size(); i++) {
            dest.writeInt(this.mManufacturerSpecificData.keyAt(i));
            dest.writeByteArray(this.mManufacturerSpecificData.valueAt(i));
        }
        dest.writeInt(this.mServiceData.size());
        for (ParcelUuid uuid : this.mServiceData.keySet()) {
            dest.writeTypedObject(uuid, flags);
            dest.writeByteArray(this.mServiceData.get(uuid));
        }
        dest.writeByte(getIncludeTxPowerLevel() ? (byte) 1 : (byte) 0);
        dest.writeByte(getIncludeDeviceName() ? (byte) 1 : (byte) 0);
        dest.writeInt(this.mTransportDiscoveryData != null ? this.mTransportDiscoveryData.length : 0);
        if (this.mTransportDiscoveryData != null) {
            dest.writeByteArray(this.mTransportDiscoveryData);
        }
    }

    /* renamed from: android.bluetooth.le.AdvertiseData$Builder */
    /* loaded from: classes.dex */
    public static final class Builder {
        private boolean mIncludeDeviceName;
        private boolean mIncludeTxPowerLevel;
        private byte[] mTransportDiscoveryData;
        private List<ParcelUuid> mServiceUuids = new ArrayList();
        private SparseArray<byte[]> mManufacturerSpecificData = new SparseArray<>();
        private Map<ParcelUuid, byte[]> mServiceData = new ArrayMap();

        public Builder addServiceUuid(ParcelUuid serviceUuid) {
            if (serviceUuid == null) {
                throw new IllegalArgumentException("serivceUuids are null");
            }
            this.mServiceUuids.add(serviceUuid);
            return this;
        }

        public Builder addServiceData(ParcelUuid serviceDataUuid, byte[] serviceData) {
            if (serviceDataUuid == null || serviceData == null) {
                throw new IllegalArgumentException("serviceDataUuid or serviceDataUuid is null");
            }
            this.mServiceData.put(serviceDataUuid, serviceData);
            return this;
        }

        public Builder addManufacturerData(int manufacturerId, byte[] manufacturerSpecificData) {
            if (manufacturerId < 0) {
                throw new IllegalArgumentException("invalid manufacturerId - " + manufacturerId);
            } else if (manufacturerSpecificData == null) {
                throw new IllegalArgumentException("manufacturerSpecificData is null");
            } else {
                this.mManufacturerSpecificData.put(manufacturerId, manufacturerSpecificData);
                return this;
            }
        }

        public Builder setIncludeTxPowerLevel(boolean includeTxPowerLevel) {
            this.mIncludeTxPowerLevel = includeTxPowerLevel;
            return this;
        }

        public Builder setIncludeDeviceName(boolean includeDeviceName) {
            this.mIncludeDeviceName = includeDeviceName;
            return this;
        }

        public Builder addTransportDiscoveryData(byte[] transportDiscoveryData) {
            if (transportDiscoveryData == null || transportDiscoveryData.length == 0) {
                throw new IllegalArgumentException("transportDiscoveryData is null");
            }
            this.mTransportDiscoveryData = transportDiscoveryData;
            return this;
        }

        public AdvertiseData build() {
            return new AdvertiseData(this.mServiceUuids, this.mManufacturerSpecificData, this.mServiceData, this.mIncludeTxPowerLevel, this.mIncludeDeviceName, this.mTransportDiscoveryData);
        }
    }
}
