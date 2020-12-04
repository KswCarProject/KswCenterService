package android.hardware.usb;

import android.annotation.UnsupportedAppUsage;
import android.app.ActivityThread;
import android.hardware.usb.IUsbSerialReader;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.android.internal.util.Preconditions;

public class UsbDevice implements Parcelable {
    public static final Parcelable.Creator<UsbDevice> CREATOR = new Parcelable.Creator<UsbDevice>() {
        public UsbDevice createFromParcel(Parcel in) {
            return new UsbDevice(in.readString(), in.readInt(), in.readInt(), in.readInt(), in.readInt(), in.readInt(), in.readString(), in.readString(), in.readString(), (UsbConfiguration[]) in.readParcelableArray(UsbConfiguration.class.getClassLoader(), UsbConfiguration.class), IUsbSerialReader.Stub.asInterface(in.readStrongBinder()));
        }

        public UsbDevice[] newArray(int size) {
            return new UsbDevice[size];
        }
    };
    private static final boolean DEBUG = false;
    private static final String TAG = "UsbDevice";
    private final int mClass;
    private final UsbConfiguration[] mConfigurations;
    @UnsupportedAppUsage
    private UsbInterface[] mInterfaces;
    private final String mManufacturerName;
    private final String mName;
    private final int mProductId;
    private final String mProductName;
    private final int mProtocol;
    private final IUsbSerialReader mSerialNumberReader;
    private final int mSubclass;
    private final int mVendorId;
    private final String mVersion;

    private static native int native_get_device_id(String str);

    private static native String native_get_device_name(int i);

    private UsbDevice(String name, int vendorId, int productId, int Class, int subClass, int protocol, String manufacturerName, String productName, String version, UsbConfiguration[] configurations, IUsbSerialReader serialNumberReader) {
        this.mName = (String) Preconditions.checkNotNull(name);
        this.mVendorId = vendorId;
        this.mProductId = productId;
        this.mClass = Class;
        this.mSubclass = subClass;
        this.mProtocol = protocol;
        this.mManufacturerName = manufacturerName;
        this.mProductName = productName;
        this.mVersion = (String) Preconditions.checkStringNotEmpty(version);
        this.mConfigurations = (UsbConfiguration[]) Preconditions.checkArrayElementsNotNull(configurations, "configurations");
        this.mSerialNumberReader = (IUsbSerialReader) Preconditions.checkNotNull(serialNumberReader);
        if (ActivityThread.isSystem()) {
            Preconditions.checkArgument(this.mSerialNumberReader instanceof IUsbSerialReader.Stub);
        }
    }

    public String getDeviceName() {
        return this.mName;
    }

    public String getManufacturerName() {
        return this.mManufacturerName;
    }

    public String getProductName() {
        return this.mProductName;
    }

    public String getVersion() {
        return this.mVersion;
    }

    public String getSerialNumber() {
        try {
            return this.mSerialNumberReader.getSerial(ActivityThread.currentPackageName());
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
            return null;
        }
    }

    public int getDeviceId() {
        return getDeviceId(this.mName);
    }

    public int getVendorId() {
        return this.mVendorId;
    }

    public int getProductId() {
        return this.mProductId;
    }

    public int getDeviceClass() {
        return this.mClass;
    }

    public int getDeviceSubclass() {
        return this.mSubclass;
    }

    public int getDeviceProtocol() {
        return this.mProtocol;
    }

    public int getConfigurationCount() {
        return this.mConfigurations.length;
    }

    public UsbConfiguration getConfiguration(int index) {
        return this.mConfigurations[index];
    }

    private UsbInterface[] getInterfaceList() {
        if (this.mInterfaces == null) {
            int interfaceCount = 0;
            for (UsbConfiguration configuration : this.mConfigurations) {
                interfaceCount += configuration.getInterfaceCount();
            }
            this.mInterfaces = new UsbInterface[interfaceCount];
            int offset = 0;
            int i = 0;
            while (i < configurationCount) {
                UsbConfiguration configuration2 = this.mConfigurations[i];
                int interfaceCount2 = configuration2.getInterfaceCount();
                int offset2 = offset;
                int j = 0;
                while (j < interfaceCount2) {
                    this.mInterfaces[offset2] = configuration2.getInterface(j);
                    j++;
                    offset2++;
                }
                i++;
                offset = offset2;
            }
        }
        return this.mInterfaces;
    }

    public int getInterfaceCount() {
        return getInterfaceList().length;
    }

    public UsbInterface getInterface(int index) {
        return getInterfaceList()[index];
    }

    public boolean equals(Object o) {
        if (o instanceof UsbDevice) {
            return ((UsbDevice) o).mName.equals(this.mName);
        }
        if (o instanceof String) {
            return ((String) o).equals(this.mName);
        }
        return false;
    }

    public int hashCode() {
        return this.mName.hashCode();
    }

    public String toString() {
        StringBuilder builder = new StringBuilder("UsbDevice[mName=" + this.mName + ",mVendorId=" + this.mVendorId + ",mProductId=" + this.mProductId + ",mClass=" + this.mClass + ",mSubclass=" + this.mSubclass + ",mProtocol=" + this.mProtocol + ",mManufacturerName=" + this.mManufacturerName + ",mProductName=" + this.mProductName + ",mVersion=" + this.mVersion + ",mSerialNumberReader=" + this.mSerialNumberReader + ",mConfigurations=[");
        for (UsbConfiguration usbConfiguration : this.mConfigurations) {
            builder.append("\n");
            builder.append(usbConfiguration.toString());
        }
        builder.append("]");
        return builder.toString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(this.mName);
        parcel.writeInt(this.mVendorId);
        parcel.writeInt(this.mProductId);
        parcel.writeInt(this.mClass);
        parcel.writeInt(this.mSubclass);
        parcel.writeInt(this.mProtocol);
        parcel.writeString(this.mManufacturerName);
        parcel.writeString(this.mProductName);
        parcel.writeString(this.mVersion);
        parcel.writeStrongBinder(this.mSerialNumberReader.asBinder());
        parcel.writeParcelableArray(this.mConfigurations, 0);
    }

    public static int getDeviceId(String name) {
        return native_get_device_id(name);
    }

    public static String getDeviceName(int id) {
        return native_get_device_name(id);
    }

    public static class Builder {
        private final int mClass;
        private final UsbConfiguration[] mConfigurations;
        private final String mManufacturerName;
        private final String mName;
        private final int mProductId;
        private final String mProductName;
        private final int mProtocol;
        private final int mSubclass;
        private final int mVendorId;
        private final String mVersion;
        public final String serialNumber;

        public Builder(String name, int vendorId, int productId, int Class, int subClass, int protocol, String manufacturerName, String productName, String version, UsbConfiguration[] configurations, String serialNumber2) {
            this.mName = (String) Preconditions.checkNotNull(name);
            this.mVendorId = vendorId;
            this.mProductId = productId;
            this.mClass = Class;
            this.mSubclass = subClass;
            this.mProtocol = protocol;
            this.mManufacturerName = manufacturerName;
            this.mProductName = productName;
            this.mVersion = (String) Preconditions.checkStringNotEmpty(version);
            this.mConfigurations = configurations;
            this.serialNumber = serialNumber2;
        }

        public UsbDevice build(IUsbSerialReader serialReader) {
            return new UsbDevice(this.mName, this.mVendorId, this.mProductId, this.mClass, this.mSubclass, this.mProtocol, this.mManufacturerName, this.mProductName, this.mVersion, this.mConfigurations, serialReader);
        }
    }
}
