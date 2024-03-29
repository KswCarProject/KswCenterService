package android.media;

import android.annotation.UnsupportedAppUsage;
import android.security.keystore.KeyProperties;

/* loaded from: classes3.dex */
public class AudioPort {
    public static final int ROLE_NONE = 0;
    public static final int ROLE_SINK = 2;
    public static final int ROLE_SOURCE = 1;
    private static final String TAG = "AudioPort";
    public static final int TYPE_DEVICE = 1;
    public static final int TYPE_NONE = 0;
    public static final int TYPE_SESSION = 3;
    public static final int TYPE_SUBMIX = 2;
    @UnsupportedAppUsage
    private AudioPortConfig mActiveConfig;
    private final int[] mChannelIndexMasks;
    private final int[] mChannelMasks;
    private final int[] mFormats;
    @UnsupportedAppUsage
    private final AudioGain[] mGains;
    @UnsupportedAppUsage
    AudioHandle mHandle;
    private final String mName;
    @UnsupportedAppUsage
    protected final int mRole;
    private final int[] mSamplingRates;

    @UnsupportedAppUsage
    AudioPort(AudioHandle handle, int role, String name, int[] samplingRates, int[] channelMasks, int[] channelIndexMasks, int[] formats, AudioGain[] gains) {
        this.mHandle = handle;
        this.mRole = role;
        this.mName = name;
        this.mSamplingRates = samplingRates;
        this.mChannelMasks = channelMasks;
        this.mChannelIndexMasks = channelIndexMasks;
        this.mFormats = formats;
        this.mGains = gains;
    }

    AudioHandle handle() {
        return this.mHandle;
    }

    @UnsupportedAppUsage
    /* renamed from: id */
    public int m114id() {
        return this.mHandle.m116id();
    }

    @UnsupportedAppUsage
    public int role() {
        return this.mRole;
    }

    public String name() {
        return this.mName;
    }

    public int[] samplingRates() {
        return this.mSamplingRates;
    }

    public int[] channelMasks() {
        return this.mChannelMasks;
    }

    public int[] channelIndexMasks() {
        return this.mChannelIndexMasks;
    }

    public int[] formats() {
        return this.mFormats;
    }

    public AudioGain[] gains() {
        return this.mGains;
    }

    AudioGain gain(int index) {
        if (index < 0 || index >= this.mGains.length) {
            return null;
        }
        return this.mGains[index];
    }

    public AudioPortConfig buildConfig(int samplingRate, int channelMask, int format, AudioGainConfig gain) {
        return new AudioPortConfig(this, samplingRate, channelMask, format, gain);
    }

    public AudioPortConfig activeConfig() {
        return this.mActiveConfig;
    }

    public boolean equals(Object o) {
        if (o == null || !(o instanceof AudioPort)) {
            return false;
        }
        AudioPort ap = (AudioPort) o;
        return this.mHandle.equals(ap.handle());
    }

    public int hashCode() {
        return this.mHandle.hashCode();
    }

    public String toString() {
        String role = Integer.toString(this.mRole);
        switch (this.mRole) {
            case 0:
                role = KeyProperties.DIGEST_NONE;
                break;
            case 1:
                role = "SOURCE";
                break;
            case 2:
                role = "SINK";
                break;
        }
        return "{mHandle: " + this.mHandle + ", mRole: " + role + "}";
    }
}
