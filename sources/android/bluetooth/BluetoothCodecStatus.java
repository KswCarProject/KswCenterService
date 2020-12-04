package android.bluetooth;

import android.annotation.UnsupportedAppUsage;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.Objects;

public final class BluetoothCodecStatus implements Parcelable {
    public static final Parcelable.Creator<BluetoothCodecStatus> CREATOR = new Parcelable.Creator<BluetoothCodecStatus>() {
        public BluetoothCodecStatus createFromParcel(Parcel in) {
            return new BluetoothCodecStatus((BluetoothCodecConfig) in.readTypedObject(BluetoothCodecConfig.CREATOR), (BluetoothCodecConfig[]) in.createTypedArray(BluetoothCodecConfig.CREATOR), (BluetoothCodecConfig[]) in.createTypedArray(BluetoothCodecConfig.CREATOR));
        }

        public BluetoothCodecStatus[] newArray(int size) {
            return new BluetoothCodecStatus[size];
        }
    };
    @UnsupportedAppUsage
    public static final String EXTRA_CODEC_STATUS = "android.bluetooth.codec.extra.CODEC_STATUS";
    private final BluetoothCodecConfig mCodecConfig;
    private final BluetoothCodecConfig[] mCodecsLocalCapabilities;
    private final BluetoothCodecConfig[] mCodecsSelectableCapabilities;

    public BluetoothCodecStatus(BluetoothCodecConfig codecConfig, BluetoothCodecConfig[] codecsLocalCapabilities, BluetoothCodecConfig[] codecsSelectableCapabilities) {
        this.mCodecConfig = codecConfig;
        this.mCodecsLocalCapabilities = codecsLocalCapabilities;
        this.mCodecsSelectableCapabilities = codecsSelectableCapabilities;
    }

    public boolean equals(Object o) {
        if (!(o instanceof BluetoothCodecStatus)) {
            return false;
        }
        BluetoothCodecStatus other = (BluetoothCodecStatus) o;
        if (!Objects.equals(other.mCodecConfig, this.mCodecConfig) || !sameCapabilities(other.mCodecsLocalCapabilities, this.mCodecsLocalCapabilities) || !sameCapabilities(other.mCodecsSelectableCapabilities, this.mCodecsSelectableCapabilities)) {
            return false;
        }
        return true;
    }

    public static boolean sameCapabilities(BluetoothCodecConfig[] c1, BluetoothCodecConfig[] c2) {
        if (c1 == null) {
            if (c2 == null) {
                return true;
            }
            return false;
        } else if (c2 != null && c1.length == c2.length) {
            return Arrays.asList(c1).containsAll(Arrays.asList(c2));
        } else {
            return false;
        }
    }

    public boolean isCodecConfigSelectable(BluetoothCodecConfig codecConfig) {
        if (codecConfig == null || !codecConfig.hasSingleSampleRate() || !codecConfig.hasSingleBitsPerSample() || !codecConfig.hasSingleChannelMode()) {
            return false;
        }
        for (BluetoothCodecConfig selectableConfig : this.mCodecsSelectableCapabilities) {
            if (codecConfig.getCodecType() == selectableConfig.getCodecType()) {
                int sampleRate = codecConfig.getSampleRate();
                if ((selectableConfig.getSampleRate() & sampleRate) != 0 || sampleRate == 0) {
                    int bitsPerSample = codecConfig.getBitsPerSample();
                    if ((selectableConfig.getBitsPerSample() & bitsPerSample) != 0 || bitsPerSample == 0) {
                        int channelMode = codecConfig.getChannelMode();
                        if ((selectableConfig.getChannelMode() & channelMode) != 0 || channelMode == 0) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.mCodecConfig, this.mCodecsLocalCapabilities, this.mCodecsLocalCapabilities});
    }

    public String toString() {
        return "{mCodecConfig:" + this.mCodecConfig + ",mCodecsLocalCapabilities:" + Arrays.toString(this.mCodecsLocalCapabilities) + ",mCodecsSelectableCapabilities:" + Arrays.toString(this.mCodecsSelectableCapabilities) + "}";
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeTypedObject(this.mCodecConfig, 0);
        out.writeTypedArray(this.mCodecsLocalCapabilities, 0);
        out.writeTypedArray(this.mCodecsSelectableCapabilities, 0);
    }

    @UnsupportedAppUsage
    public BluetoothCodecConfig getCodecConfig() {
        return this.mCodecConfig;
    }

    @UnsupportedAppUsage
    public BluetoothCodecConfig[] getCodecsLocalCapabilities() {
        return this.mCodecsLocalCapabilities;
    }

    @UnsupportedAppUsage
    public BluetoothCodecConfig[] getCodecsSelectableCapabilities() {
        return this.mCodecsSelectableCapabilities;
    }
}
