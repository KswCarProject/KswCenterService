package android.bluetooth;

import android.annotation.UnsupportedAppUsage;
import android.os.Parcel;
import android.os.Parcelable;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

public final class BluetoothClass implements Parcelable {
    public static final Parcelable.Creator<BluetoothClass> CREATOR = new Parcelable.Creator<BluetoothClass>() {
        public BluetoothClass createFromParcel(Parcel in) {
            return new BluetoothClass(in.readInt());
        }

        public BluetoothClass[] newArray(int size) {
            return new BluetoothClass[size];
        }
    };
    public static final int ERROR = -16777216;
    @UnsupportedAppUsage
    public static final int PROFILE_A2DP = 1;
    public static final int PROFILE_A2DP_SINK = 6;
    @UnsupportedAppUsage
    public static final int PROFILE_HEADSET = 0;
    public static final int PROFILE_HID = 3;
    public static final int PROFILE_NAP = 5;
    public static final int PROFILE_OPP = 2;
    public static final int PROFILE_PANU = 4;
    private final int mClass;

    public static class Device {
        public static final int AUDIO_VIDEO_CAMCORDER = 1076;
        public static final int AUDIO_VIDEO_CAR_AUDIO = 1056;
        public static final int AUDIO_VIDEO_HANDSFREE = 1032;
        public static final int AUDIO_VIDEO_HEADPHONES = 1048;
        public static final int AUDIO_VIDEO_HIFI_AUDIO = 1064;
        public static final int AUDIO_VIDEO_LOUDSPEAKER = 1044;
        public static final int AUDIO_VIDEO_MICROPHONE = 1040;
        public static final int AUDIO_VIDEO_PORTABLE_AUDIO = 1052;
        public static final int AUDIO_VIDEO_SET_TOP_BOX = 1060;
        public static final int AUDIO_VIDEO_UNCATEGORIZED = 1024;
        public static final int AUDIO_VIDEO_VCR = 1068;
        public static final int AUDIO_VIDEO_VIDEO_CAMERA = 1072;
        public static final int AUDIO_VIDEO_VIDEO_CONFERENCING = 1088;
        public static final int AUDIO_VIDEO_VIDEO_DISPLAY_AND_LOUDSPEAKER = 1084;
        public static final int AUDIO_VIDEO_VIDEO_GAMING_TOY = 1096;
        public static final int AUDIO_VIDEO_VIDEO_MONITOR = 1080;
        public static final int AUDIO_VIDEO_WEARABLE_HEADSET = 1028;
        private static final int BITMASK = 8188;
        public static final int COMPUTER_DESKTOP = 260;
        public static final int COMPUTER_HANDHELD_PC_PDA = 272;
        public static final int COMPUTER_LAPTOP = 268;
        public static final int COMPUTER_PALM_SIZE_PC_PDA = 276;
        public static final int COMPUTER_SERVER = 264;
        public static final int COMPUTER_UNCATEGORIZED = 256;
        public static final int COMPUTER_WEARABLE = 280;
        public static final int HEALTH_BLOOD_PRESSURE = 2308;
        public static final int HEALTH_DATA_DISPLAY = 2332;
        public static final int HEALTH_GLUCOSE = 2320;
        public static final int HEALTH_PULSE_OXIMETER = 2324;
        public static final int HEALTH_PULSE_RATE = 2328;
        public static final int HEALTH_THERMOMETER = 2312;
        public static final int HEALTH_UNCATEGORIZED = 2304;
        public static final int HEALTH_WEIGHING = 2316;
        public static final int PERIPHERAL_KEYBOARD = 1344;
        public static final int PERIPHERAL_KEYBOARD_POINTING = 1472;
        public static final int PERIPHERAL_NON_KEYBOARD_NON_POINTING = 1280;
        public static final int PERIPHERAL_POINTING = 1408;
        public static final int PHONE_CELLULAR = 516;
        public static final int PHONE_CORDLESS = 520;
        public static final int PHONE_ISDN = 532;
        public static final int PHONE_MODEM_OR_GATEWAY = 528;
        public static final int PHONE_SMART = 524;
        public static final int PHONE_UNCATEGORIZED = 512;
        public static final int TOY_CONTROLLER = 2064;
        public static final int TOY_DOLL_ACTION_FIGURE = 2060;
        public static final int TOY_GAME = 2068;
        public static final int TOY_ROBOT = 2052;
        public static final int TOY_UNCATEGORIZED = 2048;
        public static final int TOY_VEHICLE = 2056;
        public static final int WEARABLE_GLASSES = 1812;
        public static final int WEARABLE_HELMET = 1808;
        public static final int WEARABLE_JACKET = 1804;
        public static final int WEARABLE_PAGER = 1800;
        public static final int WEARABLE_UNCATEGORIZED = 1792;
        public static final int WEARABLE_WRIST_WATCH = 1796;

        public static class Major {
            public static final int AUDIO_VIDEO = 1024;
            private static final int BITMASK = 7936;
            public static final int COMPUTER = 256;
            public static final int HEALTH = 2304;
            public static final int IMAGING = 1536;
            public static final int MISC = 0;
            public static final int NETWORKING = 768;
            public static final int PERIPHERAL = 1280;
            public static final int PHONE = 512;
            public static final int TOY = 2048;
            public static final int UNCATEGORIZED = 7936;
            public static final int WEARABLE = 1792;
        }
    }

    public static final class Service {
        public static final int AUDIO = 2097152;
        private static final int BITMASK = 16769024;
        public static final int CAPTURE = 524288;
        public static final int INFORMATION = 8388608;
        public static final int LIMITED_DISCOVERABILITY = 8192;
        public static final int NETWORKING = 131072;
        public static final int OBJECT_TRANSFER = 1048576;
        public static final int POSITIONING = 65536;
        public static final int RENDER = 262144;
        public static final int TELEPHONY = 4194304;
    }

    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
    public BluetoothClass(int classInt) {
        this.mClass = classInt;
    }

    public boolean equals(Object o) {
        if (!(o instanceof BluetoothClass) || this.mClass != ((BluetoothClass) o).mClass) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return this.mClass;
    }

    public String toString() {
        return Integer.toHexString(this.mClass);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(this.mClass);
    }

    public boolean hasService(int service) {
        return ((this.mClass & 16769024) & service) != 0;
    }

    public int getMajorDeviceClass() {
        return this.mClass & 7936;
    }

    public int getDeviceClass() {
        return this.mClass & 8188;
    }

    public int getClassOfDevice() {
        return this.mClass;
    }

    public byte[] getClassOfDeviceBytes() {
        byte[] bytes = ByteBuffer.allocate(4).order(ByteOrder.BIG_ENDIAN).putInt(this.mClass).array();
        return Arrays.copyOfRange(bytes, 1, bytes.length);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:5:0x0011, code lost:
        r2 = getDeviceClass();
     */
    @android.annotation.UnsupportedAppUsage
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean doesClassMatch(int r7) {
        /*
            r6 = this;
            r0 = 1064(0x428, float:1.491E-42)
            r1 = 1056(0x420, float:1.48E-42)
            r2 = 262144(0x40000, float:3.67342E-40)
            r3 = 0
            r4 = 1
            if (r7 != r4) goto L_0x0023
            boolean r2 = r6.hasService(r2)
            if (r2 == 0) goto L_0x0011
            return r4
        L_0x0011:
            int r2 = r6.getDeviceClass()
            r5 = 1044(0x414, float:1.463E-42)
            if (r2 == r5) goto L_0x0022
            r5 = 1048(0x418, float:1.469E-42)
            if (r2 == r5) goto L_0x0022
            if (r2 == r1) goto L_0x0022
            if (r2 == r0) goto L_0x0022
            return r3
        L_0x0022:
            return r4
        L_0x0023:
            r5 = 6
            if (r7 != r5) goto L_0x003f
            r1 = 524288(0x80000, float:7.34684E-40)
            boolean r1 = r6.hasService(r1)
            if (r1 == 0) goto L_0x002f
            return r4
        L_0x002f:
            int r1 = r6.getDeviceClass()
            r2 = 1060(0x424, float:1.485E-42)
            if (r1 == r2) goto L_0x003e
            if (r1 == r0) goto L_0x003e
            r0 = 1068(0x42c, float:1.497E-42)
            if (r1 == r0) goto L_0x003e
            return r3
        L_0x003e:
            return r4
        L_0x003f:
            if (r7 != 0) goto L_0x0058
            boolean r0 = r6.hasService(r2)
            if (r0 == 0) goto L_0x0048
            return r4
        L_0x0048:
            int r0 = r6.getDeviceClass()
            r2 = 1028(0x404, float:1.44E-42)
            if (r0 == r2) goto L_0x0057
            r2 = 1032(0x408, float:1.446E-42)
            if (r0 == r2) goto L_0x0057
            if (r0 == r1) goto L_0x0057
            return r3
        L_0x0057:
            return r4
        L_0x0058:
            r0 = 2
            if (r7 != r0) goto L_0x006d
            r0 = 1048576(0x100000, float:1.469368E-39)
            boolean r0 = r6.hasService(r0)
            if (r0 == 0) goto L_0x0064
            return r4
        L_0x0064:
            int r0 = r6.getDeviceClass()
            switch(r0) {
                case 256: goto L_0x006c;
                case 260: goto L_0x006c;
                case 264: goto L_0x006c;
                case 268: goto L_0x006c;
                case 272: goto L_0x006c;
                case 276: goto L_0x006c;
                case 280: goto L_0x006c;
                case 512: goto L_0x006c;
                case 516: goto L_0x006c;
                case 520: goto L_0x006c;
                case 524: goto L_0x006c;
                case 528: goto L_0x006c;
                case 532: goto L_0x006c;
                default: goto L_0x006b;
            }
        L_0x006b:
            return r3
        L_0x006c:
            return r4
        L_0x006d:
            r0 = 3
            if (r7 != r0) goto L_0x007c
            int r0 = r6.getDeviceClass()
            r1 = 1280(0x500, float:1.794E-42)
            r0 = r0 & r1
            if (r0 != r1) goto L_0x007b
            r3 = r4
        L_0x007b:
            return r3
        L_0x007c:
            r0 = 4
            if (r7 == r0) goto L_0x0084
            r0 = 5
            if (r7 != r0) goto L_0x0083
            goto L_0x0084
        L_0x0083:
            return r3
        L_0x0084:
            r0 = 131072(0x20000, float:1.83671E-40)
            boolean r0 = r6.hasService(r0)
            if (r0 == 0) goto L_0x008d
            return r4
        L_0x008d:
            int r0 = r6.getDeviceClass()
            r1 = 768(0x300, float:1.076E-42)
            r0 = r0 & r1
            if (r0 != r1) goto L_0x0098
            r3 = r4
        L_0x0098:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: android.bluetooth.BluetoothClass.doesClassMatch(int):boolean");
    }
}
