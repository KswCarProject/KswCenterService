package android.bluetooth;

import android.annotation.UnsupportedAppUsage;
import android.os.Parcel;
import android.os.ParcelUuid;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class BluetoothGattCharacteristic implements Parcelable {
    public static final Parcelable.Creator<BluetoothGattCharacteristic> CREATOR = new Parcelable.Creator<BluetoothGattCharacteristic>() {
        public BluetoothGattCharacteristic createFromParcel(Parcel in) {
            return new BluetoothGattCharacteristic(in);
        }

        public BluetoothGattCharacteristic[] newArray(int size) {
            return new BluetoothGattCharacteristic[size];
        }
    };
    public static final int FORMAT_FLOAT = 52;
    public static final int FORMAT_SFLOAT = 50;
    public static final int FORMAT_SINT16 = 34;
    public static final int FORMAT_SINT32 = 36;
    public static final int FORMAT_SINT8 = 33;
    public static final int FORMAT_UINT16 = 18;
    public static final int FORMAT_UINT32 = 20;
    public static final int FORMAT_UINT8 = 17;
    public static final int PERMISSION_READ = 1;
    public static final int PERMISSION_READ_ENCRYPTED = 2;
    public static final int PERMISSION_READ_ENCRYPTED_MITM = 4;
    public static final int PERMISSION_WRITE = 16;
    public static final int PERMISSION_WRITE_ENCRYPTED = 32;
    public static final int PERMISSION_WRITE_ENCRYPTED_MITM = 64;
    public static final int PERMISSION_WRITE_SIGNED = 128;
    public static final int PERMISSION_WRITE_SIGNED_MITM = 256;
    public static final int PROPERTY_BROADCAST = 1;
    public static final int PROPERTY_EXTENDED_PROPS = 128;
    public static final int PROPERTY_INDICATE = 32;
    public static final int PROPERTY_NOTIFY = 16;
    public static final int PROPERTY_READ = 2;
    public static final int PROPERTY_SIGNED_WRITE = 64;
    public static final int PROPERTY_WRITE = 8;
    public static final int PROPERTY_WRITE_NO_RESPONSE = 4;
    public static final int WRITE_TYPE_DEFAULT = 2;
    public static final int WRITE_TYPE_NO_RESPONSE = 1;
    public static final int WRITE_TYPE_SIGNED = 4;
    protected List<BluetoothGattDescriptor> mDescriptors;
    @UnsupportedAppUsage
    protected int mInstance;
    protected int mKeySize;
    protected int mPermissions;
    protected int mProperties;
    @UnsupportedAppUsage
    protected BluetoothGattService mService;
    protected UUID mUuid;
    protected byte[] mValue;
    protected int mWriteType;

    public BluetoothGattCharacteristic(UUID uuid, int properties, int permissions) {
        this.mKeySize = 16;
        initCharacteristic((BluetoothGattService) null, uuid, 0, properties, permissions);
    }

    BluetoothGattCharacteristic(BluetoothGattService service, UUID uuid, int instanceId, int properties, int permissions) {
        this.mKeySize = 16;
        initCharacteristic(service, uuid, instanceId, properties, permissions);
    }

    public BluetoothGattCharacteristic(UUID uuid, int instanceId, int properties, int permissions) {
        this.mKeySize = 16;
        initCharacteristic((BluetoothGattService) null, uuid, instanceId, properties, permissions);
    }

    private void initCharacteristic(BluetoothGattService service, UUID uuid, int instanceId, int properties, int permissions) {
        this.mUuid = uuid;
        this.mInstance = instanceId;
        this.mProperties = properties;
        this.mPermissions = permissions;
        this.mService = service;
        this.mValue = null;
        this.mDescriptors = new ArrayList();
        if ((this.mProperties & 4) != 0) {
            this.mWriteType = 1;
        } else {
            this.mWriteType = 2;
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeParcelable(new ParcelUuid(this.mUuid), 0);
        out.writeInt(this.mInstance);
        out.writeInt(this.mProperties);
        out.writeInt(this.mPermissions);
        out.writeInt(this.mKeySize);
        out.writeInt(this.mWriteType);
        out.writeTypedList(this.mDescriptors);
    }

    private BluetoothGattCharacteristic(Parcel in) {
        this.mKeySize = 16;
        this.mUuid = ((ParcelUuid) in.readParcelable((ClassLoader) null)).getUuid();
        this.mInstance = in.readInt();
        this.mProperties = in.readInt();
        this.mPermissions = in.readInt();
        this.mKeySize = in.readInt();
        this.mWriteType = in.readInt();
        this.mDescriptors = new ArrayList();
        ArrayList<BluetoothGattDescriptor> descs = in.createTypedArrayList(BluetoothGattDescriptor.CREATOR);
        if (descs != null) {
            Iterator<BluetoothGattDescriptor> it = descs.iterator();
            while (it.hasNext()) {
                BluetoothGattDescriptor desc = it.next();
                desc.setCharacteristic(this);
                this.mDescriptors.add(desc);
            }
        }
    }

    public int getKeySize() {
        return this.mKeySize;
    }

    public boolean addDescriptor(BluetoothGattDescriptor descriptor) {
        this.mDescriptors.add(descriptor);
        descriptor.setCharacteristic(this);
        return true;
    }

    /* access modifiers changed from: package-private */
    public BluetoothGattDescriptor getDescriptor(UUID uuid, int instanceId) {
        for (BluetoothGattDescriptor descriptor : this.mDescriptors) {
            if (descriptor.getUuid().equals(uuid) && descriptor.getInstanceId() == instanceId) {
                return descriptor;
            }
        }
        return null;
    }

    public BluetoothGattService getService() {
        return this.mService;
    }

    /* access modifiers changed from: package-private */
    @UnsupportedAppUsage
    public void setService(BluetoothGattService service) {
        this.mService = service;
    }

    public UUID getUuid() {
        return this.mUuid;
    }

    public int getInstanceId() {
        return this.mInstance;
    }

    public void setInstanceId(int instanceId) {
        this.mInstance = instanceId;
    }

    public int getProperties() {
        return this.mProperties;
    }

    public int getPermissions() {
        return this.mPermissions;
    }

    public int getWriteType() {
        return this.mWriteType;
    }

    public void setWriteType(int writeType) {
        this.mWriteType = writeType;
    }

    @UnsupportedAppUsage
    public void setKeySize(int keySize) {
        this.mKeySize = keySize;
    }

    public List<BluetoothGattDescriptor> getDescriptors() {
        return this.mDescriptors;
    }

    public BluetoothGattDescriptor getDescriptor(UUID uuid) {
        for (BluetoothGattDescriptor descriptor : this.mDescriptors) {
            if (descriptor.getUuid().equals(uuid)) {
                return descriptor;
            }
        }
        return null;
    }

    public byte[] getValue() {
        return this.mValue;
    }

    public Integer getIntValue(int formatType, int offset) {
        if (getTypeLen(formatType) + offset > this.mValue.length) {
            return null;
        }
        switch (formatType) {
            case 17:
                return Integer.valueOf(unsignedByteToInt(this.mValue[offset]));
            case 18:
                return Integer.valueOf(unsignedBytesToInt(this.mValue[offset], this.mValue[offset + 1]));
            case 20:
                return Integer.valueOf(unsignedBytesToInt(this.mValue[offset], this.mValue[offset + 1], this.mValue[offset + 2], this.mValue[offset + 3]));
            case 33:
                return Integer.valueOf(unsignedToSigned(unsignedByteToInt(this.mValue[offset]), 8));
            case 34:
                return Integer.valueOf(unsignedToSigned(unsignedBytesToInt(this.mValue[offset], this.mValue[offset + 1]), 16));
            case 36:
                return Integer.valueOf(unsignedToSigned(unsignedBytesToInt(this.mValue[offset], this.mValue[offset + 1], this.mValue[offset + 2], this.mValue[offset + 3]), 32));
            default:
                return null;
        }
    }

    public Float getFloatValue(int formatType, int offset) {
        if (getTypeLen(formatType) + offset > this.mValue.length) {
            return null;
        }
        if (formatType == 50) {
            return Float.valueOf(bytesToFloat(this.mValue[offset], this.mValue[offset + 1]));
        }
        if (formatType != 52) {
            return null;
        }
        return Float.valueOf(bytesToFloat(this.mValue[offset], this.mValue[offset + 1], this.mValue[offset + 2], this.mValue[offset + 3]));
    }

    public String getStringValue(int offset) {
        if (this.mValue == null || offset > this.mValue.length) {
            return null;
        }
        byte[] strBytes = new byte[(this.mValue.length - offset)];
        for (int i = 0; i != this.mValue.length - offset; i++) {
            strBytes[i] = this.mValue[offset + i];
        }
        return new String(strBytes);
    }

    public boolean setValue(byte[] value) {
        this.mValue = value;
        return true;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x002d, code lost:
        r2 = r7 + 1;
        r4.mValue[r7] = (byte) (r5 & 255);
        r1 = r2 + 1;
        r4.mValue[r2] = (byte) ((r5 >> 8) & 255);
        r4.mValue[r1] = (byte) ((r5 >> 16) & 255);
        r4.mValue[r1 + 1] = (byte) ((r5 >> 24) & 255);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0056, code lost:
        r4.mValue[r7] = (byte) (r5 & 255);
        r4.mValue[r7 + 1] = (byte) ((r5 >> 8) & 255);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0069, code lost:
        r4.mValue[r7] = (byte) (r5 & 255);
        r2 = r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:?, code lost:
        return true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:?, code lost:
        return true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:?, code lost:
        return true;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean setValue(int r5, int r6, int r7) {
        /*
            r4 = this;
            int r0 = r4.getTypeLen(r6)
            int r0 = r0 + r7
            byte[] r1 = r4.mValue
            if (r1 != 0) goto L_0x000d
            byte[] r1 = new byte[r0]
            r4.mValue = r1
        L_0x000d:
            byte[] r1 = r4.mValue
            int r1 = r1.length
            r2 = 0
            if (r0 <= r1) goto L_0x0014
            return r2
        L_0x0014:
            switch(r6) {
                case 17: goto L_0x0069;
                case 18: goto L_0x0056;
                case 20: goto L_0x002d;
                case 33: goto L_0x0026;
                case 34: goto L_0x001f;
                case 36: goto L_0x0018;
                default: goto L_0x0017;
            }
        L_0x0017:
            return r2
        L_0x0018:
            r1 = 32
            int r5 = r4.intToSignedBits(r5, r1)
            goto L_0x002d
        L_0x001f:
            r1 = 16
            int r5 = r4.intToSignedBits(r5, r1)
            goto L_0x0056
        L_0x0026:
            r1 = 8
            int r5 = r4.intToSignedBits(r5, r1)
            goto L_0x0069
        L_0x002d:
            byte[] r1 = r4.mValue
            int r2 = r7 + 1
            r3 = r5 & 255(0xff, float:3.57E-43)
            byte r3 = (byte) r3
            r1[r7] = r3
            byte[] r7 = r4.mValue
            int r1 = r2 + 1
            int r3 = r5 >> 8
            r3 = r3 & 255(0xff, float:3.57E-43)
            byte r3 = (byte) r3
            r7[r2] = r3
            byte[] r7 = r4.mValue
            int r2 = r1 + 1
            int r3 = r5 >> 16
            r3 = r3 & 255(0xff, float:3.57E-43)
            byte r3 = (byte) r3
            r7[r1] = r3
            byte[] r7 = r4.mValue
            int r1 = r5 >> 24
            r1 = r1 & 255(0xff, float:3.57E-43)
            byte r1 = (byte) r1
            r7[r2] = r1
            goto L_0x0072
        L_0x0056:
            byte[] r1 = r4.mValue
            int r2 = r7 + 1
            r3 = r5 & 255(0xff, float:3.57E-43)
            byte r3 = (byte) r3
            r1[r7] = r3
            byte[] r7 = r4.mValue
            int r1 = r5 >> 8
            r1 = r1 & 255(0xff, float:3.57E-43)
            byte r1 = (byte) r1
            r7[r2] = r1
            goto L_0x0072
        L_0x0069:
            byte[] r1 = r4.mValue
            r2 = r5 & 255(0xff, float:3.57E-43)
            byte r2 = (byte) r2
            r1[r7] = r2
            r2 = r7
        L_0x0072:
            r7 = 1
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: android.bluetooth.BluetoothGattCharacteristic.setValue(int, int, int):boolean");
    }

    public boolean setValue(int mantissa, int exponent, int formatType, int offset) {
        int len = getTypeLen(formatType) + offset;
        if (this.mValue == null) {
            this.mValue = new byte[len];
        }
        if (len > this.mValue.length) {
            return false;
        }
        if (formatType == 50) {
            int mantissa2 = intToSignedBits(mantissa, 12);
            int exponent2 = intToSignedBits(exponent, 4);
            int offset2 = offset + 1;
            this.mValue[offset] = (byte) (mantissa2 & 255);
            this.mValue[offset2] = (byte) ((mantissa2 >> 8) & 15);
            byte[] bArr = this.mValue;
            bArr[offset2] = (byte) (bArr[offset2] + ((byte) ((exponent2 & 15) << 4)));
            int i = offset2;
            return true;
        } else if (formatType != 52) {
            return false;
        } else {
            int mantissa3 = intToSignedBits(mantissa, 24);
            int exponent3 = intToSignedBits(exponent, 8);
            int offset3 = offset + 1;
            this.mValue[offset] = (byte) (mantissa3 & 255);
            int offset4 = offset3 + 1;
            this.mValue[offset3] = (byte) ((mantissa3 >> 8) & 255);
            int offset5 = offset4 + 1;
            this.mValue[offset4] = (byte) ((mantissa3 >> 16) & 255);
            byte[] bArr2 = this.mValue;
            bArr2[offset5] = (byte) (bArr2[offset5] + ((byte) (exponent3 & 255)));
            return true;
        }
    }

    public boolean setValue(String value) {
        this.mValue = value.getBytes();
        return true;
    }

    private int getTypeLen(int formatType) {
        return formatType & 15;
    }

    private int unsignedByteToInt(byte b) {
        return b & 255;
    }

    private int unsignedBytesToInt(byte b0, byte b1) {
        return unsignedByteToInt(b0) + (unsignedByteToInt(b1) << 8);
    }

    private int unsignedBytesToInt(byte b0, byte b1, byte b2, byte b3) {
        return unsignedByteToInt(b0) + (unsignedByteToInt(b1) << 8) + (unsignedByteToInt(b2) << 16) + (unsignedByteToInt(b3) << 24);
    }

    private float bytesToFloat(byte b0, byte b1) {
        return (float) (((double) unsignedToSigned(unsignedByteToInt(b0) + ((unsignedByteToInt(b1) & 15) << 8), 12)) * Math.pow(10.0d, (double) unsignedToSigned(unsignedByteToInt(b1) >> 4, 4)));
    }

    private float bytesToFloat(byte b0, byte b1, byte b2, byte b3) {
        return (float) (((double) unsignedToSigned(unsignedByteToInt(b0) + (unsignedByteToInt(b1) << 8) + (unsignedByteToInt(b2) << 16), 24)) * Math.pow(10.0d, (double) b3));
    }

    private int unsignedToSigned(int unsigned, int size) {
        if (((1 << (size - 1)) & unsigned) != 0) {
            return ((1 << (size - 1)) - (unsigned & ((1 << (size - 1)) - 1))) * -1;
        }
        return unsigned;
    }

    private int intToSignedBits(int i, int size) {
        if (i < 0) {
            return (1 << (size - 1)) + (i & ((1 << (size - 1)) - 1));
        }
        return i;
    }
}
