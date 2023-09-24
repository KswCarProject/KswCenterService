package android.media;

import android.annotation.UnsupportedAppUsage;
import android.media.AudioAttributes;
import android.media.AudioFormat;
import android.media.AudioRouting;
import android.media.VolumeShaper;
import android.net.TrafficStats;
import android.opengl.GLES30;
import android.p007os.Binder;
import android.p007os.Handler;
import android.p007os.HandlerThread;
import android.p007os.Looper;
import android.p007os.Message;
import android.p007os.PersistableBundle;
import android.util.ArrayMap;
import android.util.Log;
import com.android.internal.annotations.GuardedBy;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.NioUtils;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
public class AudioTrack extends PlayerBase implements AudioRouting, VolumeAutomation {
    private static final int AUDIO_OUTPUT_FLAG_DEEP_BUFFER = 8;
    private static final int AUDIO_OUTPUT_FLAG_FAST = 4;
    public static final int ERROR = -1;
    public static final int ERROR_BAD_VALUE = -2;
    public static final int ERROR_DEAD_OBJECT = -6;
    public static final int ERROR_INVALID_OPERATION = -3;
    private static final int ERROR_NATIVESETUP_AUDIOSYSTEM = -16;
    private static final int ERROR_NATIVESETUP_INVALIDCHANNELMASK = -17;
    private static final int ERROR_NATIVESETUP_INVALIDFORMAT = -18;
    private static final int ERROR_NATIVESETUP_INVALIDSTREAMTYPE = -19;
    private static final int ERROR_NATIVESETUP_NATIVEINITFAILED = -20;
    public static final int ERROR_WOULD_BLOCK = -7;
    private static final float GAIN_MAX = 1.0f;
    private static final float GAIN_MIN = 0.0f;
    private static final float HEADER_V2_SIZE_BYTES = 20.0f;
    public static final int MODE_STATIC = 0;
    public static final int MODE_STREAM = 1;
    private static final int NATIVE_EVENT_CAN_WRITE_MORE_DATA = 9;
    private static final int NATIVE_EVENT_MARKER = 3;
    private static final int NATIVE_EVENT_NEW_IAUDIOTRACK = 6;
    private static final int NATIVE_EVENT_NEW_POS = 4;
    private static final int NATIVE_EVENT_STREAM_END = 7;
    public static final int PERFORMANCE_MODE_LOW_LATENCY = 1;
    public static final int PERFORMANCE_MODE_NONE = 0;
    public static final int PERFORMANCE_MODE_POWER_SAVING = 2;
    public static final int PLAYSTATE_PAUSED = 2;
    private static final int PLAYSTATE_PAUSED_STOPPING = 5;
    public static final int PLAYSTATE_PLAYING = 3;
    public static final int PLAYSTATE_STOPPED = 1;
    private static final int PLAYSTATE_STOPPING = 4;
    public static final int STATE_INITIALIZED = 1;
    public static final int STATE_NO_STATIC_DATA = 2;
    public static final int STATE_UNINITIALIZED = 0;
    public static final int SUCCESS = 0;
    private static final int SUPPORTED_OUT_CHANNELS = 7420;
    private static final String TAG = "android.media.AudioTrack";
    public static final int WRITE_BLOCKING = 0;
    public static final int WRITE_NON_BLOCKING = 1;
    private boolean isPapaGo;
    private int mAudioFormat;
    private int mAvSyncBytesRemaining;
    private ByteBuffer mAvSyncHeader;
    private int mChannelConfiguration;
    private int mChannelCount;
    private int mChannelIndexMask;
    private int mChannelMask;
    private AudioAttributes mConfiguredAudioAttributes;
    private int mDataLoadMode;
    private NativePositionEventHandlerDelegate mEventHandlerDelegate;
    private final Looper mInitializationLooper;
    @UnsupportedAppUsage
    private long mJniData;
    private int mNativeBufferSizeInBytes;
    private int mNativeBufferSizeInFrames;
    @UnsupportedAppUsage
    protected long mNativeTrackInJavaObj;
    private NaviHelper mNaviHelper;
    private int mOffloadDelayFrames;
    private boolean mOffloadEosPending;
    private int mOffloadPaddingFrames;
    private boolean mOffloaded;
    private int mOffset;
    private int mPlayState;
    private final Object mPlayStateLock;
    private AudioDeviceInfo mPreferredDevice;
    @GuardedBy({"mRoutingChangeListeners"})
    private ArrayMap<AudioRouting.OnRoutingChangedListener, NativeRoutingEventHandlerDelegate> mRoutingChangeListeners;
    private int mSampleRate;
    private int mSessionId;
    private int mState;
    @GuardedBy({"mStreamEventCbLock"})
    private LinkedList<StreamEventCbInfo> mStreamEventCbInfoList;
    private final Object mStreamEventCbLock;
    private volatile StreamEventHandler mStreamEventHandler;
    private HandlerThread mStreamEventHandlerThread;
    @UnsupportedAppUsage
    private int mStreamType;

    /* loaded from: classes3.dex */
    public interface OnPlaybackPositionUpdateListener {
        void onMarkerReached(AudioTrack audioTrack);

        void onPeriodicNotification(AudioTrack audioTrack);
    }

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes3.dex */
    public @interface PerformanceMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes3.dex */
    public @interface TransferMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes3.dex */
    public @interface WriteMode {
    }

    private native int native_applyVolumeShaper(VolumeShaper.Configuration configuration, VolumeShaper.Operation operation);

    private final native int native_attachAuxEffect(int i);

    private final native void native_disableDeviceCallback();

    private final native void native_enableDeviceCallback();

    private final native void native_finalize();

    private final native void native_flush();

    private native PersistableBundle native_getMetrics();

    private native int native_getPortId();

    private final native int native_getRoutedDeviceId();

    private native VolumeShaper.State native_getVolumeShaperState(int i);

    private final native int native_get_buffer_capacity_frames();

    private final native int native_get_buffer_size_frames();

    private final native int native_get_flags();

    private final native int native_get_latency();

    private final native int native_get_marker_pos();

    private static final native int native_get_min_buff_size(int i, int i2, int i3);

    private static final native int native_get_output_sample_rate(int i);

    private final native PlaybackParams native_get_playback_params();

    private final native int native_get_playback_rate();

    private final native int native_get_pos_update_period();

    private final native int native_get_position();

    private final native int native_get_timestamp(long[] jArr);

    private final native int native_get_underrun_count();

    private static native boolean native_is_direct_output_supported(int i, int i2, int i3, int i4, int i5, int i6, int i7);

    private final native void native_pause();

    private final native int native_reload_static();

    private final native int native_setAuxEffectSendLevel(float f);

    private final native boolean native_setOutputDevice(int i);

    private final native int native_setPresentation(int i, int i2);

    private final native void native_setVolume(float f, float f2);

    private final native int native_set_buffer_size_frames(int i);

    private native void native_set_delay_padding(int i, int i2);

    private final native int native_set_loop(int i, int i2, int i3);

    private final native int native_set_marker_pos(int i);

    private final native void native_set_playback_params(PlaybackParams playbackParams);

    private final native int native_set_playback_rate(int i);

    private final native int native_set_pos_update_period(int i);

    private final native int native_set_position(int i);

    private final native int native_setup(Object obj, Object obj2, int[] iArr, int i, int i2, int i3, int i4, int i5, int[] iArr2, long j, boolean z);

    /* JADX INFO: Access modifiers changed from: private */
    public final native void native_start();

    private final native void native_stop();

    private final native int native_write_byte(byte[] bArr, int i, int i2, int i3, boolean z);

    private final native int native_write_float(float[] fArr, int i, int i2, int i3, boolean z);

    private final native int native_write_native_bytes(ByteBuffer byteBuffer, int i, int i2, int i3, boolean z);

    private final native int native_write_short(short[] sArr, int i, int i2, int i3, boolean z);

    @UnsupportedAppUsage
    public final native void native_release();

    public AudioTrack(int streamType, int sampleRateInHz, int channelConfig, int audioFormat, int bufferSizeInBytes, int mode) throws IllegalArgumentException {
        this(streamType, sampleRateInHz, channelConfig, audioFormat, bufferSizeInBytes, mode, 0);
    }

    public AudioTrack(int streamType, int sampleRateInHz, int channelConfig, int audioFormat, int bufferSizeInBytes, int mode, int sessionId) throws IllegalArgumentException {
        this(new AudioAttributes.Builder().setLegacyStreamType(streamType).build(), new AudioFormat.Builder().setChannelMask(channelConfig).setEncoding(audioFormat).setSampleRate(sampleRateInHz).build(), bufferSizeInBytes, mode, sessionId);
        deprecateStreamTypeForPlayback(streamType, "AudioTrack", "AudioTrack()");
    }

    public AudioTrack(AudioAttributes attributes, AudioFormat format, int bufferSizeInBytes, int mode, int sessionId) throws IllegalArgumentException {
        this(attributes, format, bufferSizeInBytes, mode, sessionId, false);
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x00c6  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00e9  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0186  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private AudioTrack(AudioAttributes attributes, AudioFormat format, int bufferSizeInBytes, int mode, int sessionId, boolean offload) throws IllegalArgumentException {
        super(attributes, 1);
        int channelMask;
        int i;
        int frameSizeInBytes;
        int frameSizeInBytes2;
        this.mState = 0;
        this.mPlayState = 1;
        this.mOffloadEosPending = false;
        this.mPlayStateLock = new Object();
        this.mNativeBufferSizeInBytes = 0;
        this.mNativeBufferSizeInFrames = 0;
        this.mChannelCount = 1;
        this.mChannelMask = 4;
        this.mStreamType = 3;
        this.mDataLoadMode = 1;
        this.mChannelConfiguration = 4;
        this.mChannelIndexMask = 0;
        this.mSessionId = 0;
        this.mAvSyncHeader = null;
        this.mAvSyncBytesRemaining = 0;
        this.mOffset = 0;
        this.mOffloaded = false;
        this.mOffloadDelayFrames = 0;
        this.mOffloadPaddingFrames = 0;
        this.mPreferredDevice = null;
        this.mRoutingChangeListeners = new ArrayMap<>();
        this.mStreamEventCbLock = new Object();
        this.mStreamEventCbInfoList = new LinkedList<>();
        this.mConfiguredAudioAttributes = attributes;
        if (format == null) {
            throw new IllegalArgumentException("Illegal null AudioFormat");
        }
        this.mNaviHelper = new NaviHelper();
        if (shouldEnablePowerSaving(this.mAttributes, format, bufferSizeInBytes, mode)) {
            this.mAttributes = new AudioAttributes.Builder(this.mAttributes).replaceFlags((this.mAttributes.getAllFlags() | 512) & TrafficStats.TAG_NETWORK_STACK_RANGE_END).build();
        }
        Looper myLooper = Looper.myLooper();
        Looper looper = myLooper;
        Looper looper2 = myLooper == null ? Looper.getMainLooper() : looper;
        int rate = format.getSampleRate();
        int rate2 = rate == 0 ? 0 : rate;
        int channelIndexMask = (format.getPropertySetMask() & 8) != 0 ? format.getChannelIndexMask() : 0;
        if ((4 & format.getPropertySetMask()) != 0) {
            i = format.getChannelMask();
        } else if (channelIndexMask == 0) {
            i = 12;
        } else {
            channelMask = 0;
            int encoding = (format.getPropertySetMask() & 1) != 0 ? format.getEncoding() : 1;
            audioParamCheck(rate2, channelMask, channelIndexMask, encoding, mode);
            this.mOffloaded = offload;
            this.mStreamType = -1;
            audioBuffSizeCheck(bufferSizeInBytes);
            this.mInitializationLooper = looper2;
            if (sessionId >= 0) {
                throw new IllegalArgumentException("Invalid audio session ID: " + sessionId);
            }
            int[] sampleRate = {this.mSampleRate};
            int[] session = {sessionId};
            int initResult = native_setup(new WeakReference(this), this.mAttributes, sampleRate, this.mChannelMask, this.mChannelIndexMask, this.mAudioFormat, this.mNativeBufferSizeInBytes, this.mDataLoadMode, session, 0L, offload);
            if (initResult == 0) {
                this.mSampleRate = sampleRate[0];
                this.mSessionId = session[0];
                if ((this.mAttributes.getFlags() & 16) != 0) {
                    if (AudioFormat.isEncodingLinearFrames(this.mAudioFormat)) {
                        frameSizeInBytes = this.mChannelCount * AudioFormat.getBytesPerSample(this.mAudioFormat);
                    } else {
                        frameSizeInBytes = 1;
                    }
                    this.mOffset = ((int) Math.ceil(HEADER_V2_SIZE_BYTES / frameSizeInBytes2)) * frameSizeInBytes;
                }
                int frameSizeInBytes3 = this.mDataLoadMode;
                if (frameSizeInBytes3 == 0) {
                    this.mState = 2;
                } else {
                    this.mState = 1;
                }
                baseRegisterPlayer();
                return;
            }
            loge("Error code " + initResult + " when initializing AudioTrack.");
            return;
        }
        channelMask = i;
        if ((format.getPropertySetMask() & 1) != 0) {
        }
        audioParamCheck(rate2, channelMask, channelIndexMask, encoding, mode);
        this.mOffloaded = offload;
        this.mStreamType = -1;
        audioBuffSizeCheck(bufferSizeInBytes);
        this.mInitializationLooper = looper2;
        if (sessionId >= 0) {
        }
    }

    AudioTrack(long nativeTrackInJavaObj) {
        super(new AudioAttributes.Builder().build(), 1);
        this.mState = 0;
        this.mPlayState = 1;
        this.mOffloadEosPending = false;
        this.mPlayStateLock = new Object();
        this.mNativeBufferSizeInBytes = 0;
        this.mNativeBufferSizeInFrames = 0;
        this.mChannelCount = 1;
        this.mChannelMask = 4;
        this.mStreamType = 3;
        this.mDataLoadMode = 1;
        this.mChannelConfiguration = 4;
        this.mChannelIndexMask = 0;
        this.mSessionId = 0;
        this.mAvSyncHeader = null;
        this.mAvSyncBytesRemaining = 0;
        this.mOffset = 0;
        this.mOffloaded = false;
        this.mOffloadDelayFrames = 0;
        this.mOffloadPaddingFrames = 0;
        this.mPreferredDevice = null;
        this.mRoutingChangeListeners = new ArrayMap<>();
        this.mStreamEventCbLock = new Object();
        this.mStreamEventCbInfoList = new LinkedList<>();
        this.mNativeTrackInJavaObj = 0L;
        this.mJniData = 0L;
        Looper myLooper = Looper.myLooper();
        Looper looper = myLooper;
        this.mInitializationLooper = myLooper == null ? Looper.getMainLooper() : looper;
        if (nativeTrackInJavaObj != 0) {
            baseRegisterPlayer();
            deferred_connect(nativeTrackInJavaObj);
            return;
        }
        this.mState = 0;
    }

    @UnsupportedAppUsage
    void deferred_connect(long nativeTrackInJavaObj) {
        if (this.mState != 1) {
            int[] session = {0};
            int[] rates = {0};
            int initResult = native_setup(new WeakReference(this), null, rates, 0, 0, 0, 0, 0, session, nativeTrackInJavaObj, false);
            if (initResult == 0) {
                this.mSessionId = session[0];
                this.mState = 1;
                return;
            }
            loge("Error code " + initResult + " when initializing AudioTrack.");
        }
    }

    /* loaded from: classes3.dex */
    public static class Builder {
        private AudioAttributes mAttributes;
        private int mBufferSizeInBytes;
        private AudioFormat mFormat;
        private int mSessionId = 0;
        private int mMode = 1;
        private int mPerformanceMode = 0;
        private boolean mOffload = false;

        public Builder setAudioAttributes(AudioAttributes attributes) throws IllegalArgumentException {
            if (attributes == null) {
                throw new IllegalArgumentException("Illegal null AudioAttributes argument");
            }
            this.mAttributes = attributes;
            return this;
        }

        public Builder setAudioFormat(AudioFormat format) throws IllegalArgumentException {
            if (format == null) {
                throw new IllegalArgumentException("Illegal null AudioFormat argument");
            }
            this.mFormat = format;
            return this;
        }

        public Builder setBufferSizeInBytes(int bufferSizeInBytes) throws IllegalArgumentException {
            if (bufferSizeInBytes <= 0) {
                throw new IllegalArgumentException("Invalid buffer size " + bufferSizeInBytes);
            }
            this.mBufferSizeInBytes = bufferSizeInBytes;
            return this;
        }

        public Builder setTransferMode(int mode) throws IllegalArgumentException {
            switch (mode) {
                case 0:
                case 1:
                    this.mMode = mode;
                    return this;
                default:
                    throw new IllegalArgumentException("Invalid transfer mode " + mode);
            }
        }

        public Builder setSessionId(int sessionId) throws IllegalArgumentException {
            if (sessionId != 0 && sessionId < 1) {
                throw new IllegalArgumentException("Invalid audio session ID " + sessionId);
            }
            this.mSessionId = sessionId;
            return this;
        }

        public Builder setPerformanceMode(int performanceMode) {
            switch (performanceMode) {
                case 0:
                case 1:
                case 2:
                    this.mPerformanceMode = performanceMode;
                    return this;
                default:
                    throw new IllegalArgumentException("Invalid performance mode " + performanceMode);
            }
        }

        public Builder setOffloadedPlayback(boolean offload) {
            this.mOffload = offload;
            return this;
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARN: Code restructure failed: missing block: B:10:0x0042, code lost:
            if (android.media.AudioTrack.shouldEnablePowerSaving(r9.mAttributes, r9.mFormat, r9.mBufferSizeInBytes, r9.mMode) == false) goto L10;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public AudioTrack build() throws UnsupportedOperationException {
            if (this.mAttributes == null) {
                this.mAttributes = new AudioAttributes.Builder().setUsage(1).build();
            }
            switch (this.mPerformanceMode) {
                case 1:
                    this.mAttributes = new AudioAttributes.Builder(this.mAttributes).replaceFlags((this.mAttributes.getAllFlags() | 256) & (-513)).build();
                    break;
                case 2:
                    this.mAttributes = new AudioAttributes.Builder(this.mAttributes).replaceFlags((this.mAttributes.getAllFlags() | 512) & TrafficStats.TAG_NETWORK_STACK_RANGE_END).build();
                    break;
            }
            if (this.mFormat == null) {
                this.mFormat = new AudioFormat.Builder().setChannelMask(12).setEncoding(1).build();
            }
            if (this.mOffload) {
                if (this.mPerformanceMode == 1) {
                    throw new UnsupportedOperationException("Offload and low latency modes are incompatible");
                }
                if (!AudioSystem.isOffloadSupported(this.mFormat, this.mAttributes)) {
                    throw new UnsupportedOperationException("Cannot create AudioTrack, offload format / attributes not supported");
                }
            }
            try {
                if (this.mMode == 1 && this.mBufferSizeInBytes == 0) {
                    int channelCount = this.mFormat.getChannelCount();
                    AudioFormat audioFormat = this.mFormat;
                    this.mBufferSizeInBytes = channelCount * AudioFormat.getBytesPerSample(this.mFormat.getEncoding());
                }
                AudioTrack track = new AudioTrack(this.mAttributes, this.mFormat, this.mBufferSizeInBytes, this.mMode, this.mSessionId, this.mOffload);
                if (track.getState() == 0) {
                    throw new UnsupportedOperationException("Cannot create AudioTrack");
                }
                return track;
            } catch (IllegalArgumentException e) {
                throw new UnsupportedOperationException(e.getMessage());
            }
        }
    }

    public void setOffloadDelayPadding(int delayInFrames, int paddingInFrames) {
        if (paddingInFrames < 0) {
            throw new IllegalArgumentException("Illegal negative padding");
        }
        if (delayInFrames < 0) {
            throw new IllegalArgumentException("Illegal negative delay");
        }
        if (!this.mOffloaded) {
            throw new IllegalStateException("Illegal use of delay/padding on non-offloaded track");
        }
        if (this.mState == 0) {
            throw new IllegalStateException("Uninitialized track");
        }
        this.mOffloadDelayFrames = delayInFrames;
        this.mOffloadPaddingFrames = paddingInFrames;
        native_set_delay_padding(delayInFrames, paddingInFrames);
    }

    public int getOffloadDelay() {
        if (!this.mOffloaded) {
            throw new IllegalStateException("Illegal query of delay on non-offloaded track");
        }
        if (this.mState == 0) {
            throw new IllegalStateException("Illegal query of delay on uninitialized track");
        }
        return this.mOffloadDelayFrames;
    }

    public int getOffloadPadding() {
        if (!this.mOffloaded) {
            throw new IllegalStateException("Illegal query of padding on non-offloaded track");
        }
        if (this.mState == 0) {
            throw new IllegalStateException("Illegal query of padding on uninitialized track");
        }
        return this.mOffloadPaddingFrames;
    }

    public void setOffloadEndOfStream() {
        if (!this.mOffloaded) {
            throw new IllegalStateException("EOS not supported on non-offloaded track");
        }
        if (this.mState == 0) {
            throw new IllegalStateException("Uninitialized track");
        }
        if (this.mPlayState != 3) {
            throw new IllegalStateException("EOS not supported if not playing");
        }
        synchronized (this.mStreamEventCbLock) {
            if (this.mStreamEventCbInfoList.size() == 0) {
                throw new IllegalStateException("EOS not supported without StreamEventCallback");
            }
        }
        synchronized (this.mPlayStateLock) {
            native_stop();
            this.mOffloadEosPending = true;
            this.mPlayState = 4;
        }
    }

    public boolean isOffloadedPlayback() {
        return this.mOffloaded;
    }

    public static boolean isDirectPlaybackSupported(AudioFormat format, AudioAttributes attributes) {
        if (format == null) {
            throw new IllegalArgumentException("Illegal null AudioFormat argument");
        }
        if (attributes == null) {
            throw new IllegalArgumentException("Illegal null AudioAttributes argument");
        }
        return native_is_direct_output_supported(format.getEncoding(), format.getSampleRate(), format.getChannelMask(), format.getChannelIndexMask(), attributes.getContentType(), attributes.getUsage(), attributes.getFlags());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean shouldEnablePowerSaving(AudioAttributes attributes, AudioFormat format, int bufferSizeInBytes, int mode) {
        if ((attributes != null && (attributes.getAllFlags() != 0 || attributes.getUsage() != 1 || (attributes.getContentType() != 0 && attributes.getContentType() != 2 && attributes.getContentType() != 3))) || format == null || format.getSampleRate() == 0 || !AudioFormat.isEncodingLinearPcm(format.getEncoding()) || !AudioFormat.isValidEncoding(format.getEncoding()) || format.getChannelCount() < 1 || mode != 1) {
            return false;
        }
        if (bufferSizeInBytes != 0) {
            long bufferTargetSize = (((format.getChannelCount() * 100) * AudioFormat.getBytesPerSample(format.getEncoding())) * format.getSampleRate()) / 1000;
            if (bufferSizeInBytes < bufferTargetSize) {
                return false;
            }
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x0057  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0091  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0098  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x00b4  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void audioParamCheck(int sampleRateInHz, int channelConfig, int channelIndexMask, int audioFormat, int mode) {
        if ((sampleRateInHz < 4000 || sampleRateInHz > 192000) && sampleRateInHz != 0) {
            throw new IllegalArgumentException(sampleRateInHz + "Hz is not a supported sample rate.");
        }
        this.mSampleRate = sampleRateInHz;
        if (audioFormat == 13 && channelConfig != 12) {
            throw new IllegalArgumentException("ENCODING_IEC61937 must be configured as CHANNEL_OUT_STEREO");
        }
        this.mChannelConfiguration = channelConfig;
        if (channelConfig != 12) {
            switch (channelConfig) {
                case 1:
                case 2:
                case 4:
                    this.mChannelCount = 1;
                    this.mChannelMask = 4;
                    break;
                case 3:
                    break;
                default:
                    if (channelConfig == 0 && channelIndexMask != 0) {
                        this.mChannelCount = 0;
                        break;
                    } else if (!isMultichannelConfigSupported(channelConfig)) {
                        throw new IllegalArgumentException("Unsupported channel configuration.");
                    } else {
                        this.mChannelMask = channelConfig;
                        this.mChannelCount = AudioFormat.channelCountFromOutChannelMask(channelConfig);
                        break;
                    }
            }
            this.mChannelIndexMask = channelIndexMask;
            if (this.mChannelIndexMask != 0) {
                int indexMask = (1 << AudioSystem.OUT_CHANNEL_COUNT_MAX) - 1;
                if (((~indexMask) & channelIndexMask) != 0) {
                    throw new IllegalArgumentException("Unsupported channel index configuration " + channelIndexMask);
                }
                int channelIndexCount = Integer.bitCount(channelIndexMask);
                if (this.mChannelCount == 0) {
                    this.mChannelCount = channelIndexCount;
                } else if (this.mChannelCount != channelIndexCount) {
                    throw new IllegalArgumentException("Channel count must match");
                }
            }
            if (audioFormat == 1) {
                audioFormat = 2;
            }
            if (AudioFormat.isPublicEncoding(audioFormat)) {
                throw new IllegalArgumentException("Unsupported audio encoding.");
            }
            this.mAudioFormat = audioFormat;
            if ((mode != 1 && mode != 0) || (mode != 1 && !AudioFormat.isEncodingLinearPcm(this.mAudioFormat))) {
                throw new IllegalArgumentException("Invalid mode.");
            }
            this.mDataLoadMode = mode;
            return;
        }
        this.mChannelCount = 2;
        this.mChannelMask = 12;
        this.mChannelIndexMask = channelIndexMask;
        if (this.mChannelIndexMask != 0) {
        }
        if (audioFormat == 1) {
        }
        if (AudioFormat.isPublicEncoding(audioFormat)) {
        }
    }

    private static boolean isMultichannelConfigSupported(int channelConfig) {
        if ((channelConfig & SUPPORTED_OUT_CHANNELS) != channelConfig) {
            loge("Channel configuration features unsupported channels");
            return false;
        }
        int channelCount = AudioFormat.channelCountFromOutChannelMask(channelConfig);
        if (channelCount > AudioSystem.OUT_CHANNEL_COUNT_MAX) {
            loge("Channel configuration contains too many channels " + channelCount + ">" + AudioSystem.OUT_CHANNEL_COUNT_MAX);
            return false;
        } else if ((channelConfig & 12) != 12) {
            loge("Front channels must be present in multichannel configurations");
            return false;
        } else if ((channelConfig & 192) != 0 && (channelConfig & 192) != 192) {
            loge("Rear channels can't be used independently");
            return false;
        } else if ((channelConfig & GLES30.GL_COLOR) != 0 && (channelConfig & GLES30.GL_COLOR) != 6144) {
            loge("Side channels can't be used independently");
            return false;
        } else {
            return true;
        }
    }

    private void audioBuffSizeCheck(int audioBufferSize) {
        int frameSizeInBytes;
        if (AudioFormat.isEncodingLinearFrames(this.mAudioFormat)) {
            frameSizeInBytes = this.mChannelCount * AudioFormat.getBytesPerSample(this.mAudioFormat);
        } else {
            frameSizeInBytes = 1;
        }
        if (audioBufferSize % frameSizeInBytes != 0 || audioBufferSize < 1) {
            throw new IllegalArgumentException("Invalid audio buffer size.");
        }
        this.mNativeBufferSizeInBytes = audioBufferSize;
        this.mNativeBufferSizeInFrames = audioBufferSize / frameSizeInBytes;
    }

    public void release() {
        synchronized (this.mStreamEventCbLock) {
            endStreamEventHandling();
        }
        try {
            stop();
            this.mNaviHelper.stop();
        } catch (IllegalStateException e) {
        }
        baseRelease();
        native_release();
        synchronized (this.mPlayStateLock) {
            this.mState = 0;
            this.mPlayState = 1;
            this.mPlayStateLock.notify();
        }
    }

    protected void finalize() {
        baseRelease();
        native_finalize();
    }

    public static float getMinVolume() {
        return 0.0f;
    }

    public static float getMaxVolume() {
        return 1.0f;
    }

    public int getSampleRate() {
        return this.mSampleRate;
    }

    public int getPlaybackRate() {
        return native_get_playback_rate();
    }

    public PlaybackParams getPlaybackParams() {
        return native_get_playback_params();
    }

    public AudioAttributes getAudioAttributes() {
        if (this.mState == 0 || this.mConfiguredAudioAttributes == null) {
            throw new IllegalStateException("track not initialized");
        }
        return this.mConfiguredAudioAttributes;
    }

    public int getAudioFormat() {
        return this.mAudioFormat;
    }

    public int getStreamType() {
        return this.mStreamType;
    }

    public int getChannelConfiguration() {
        return this.mChannelConfiguration;
    }

    public AudioFormat getFormat() {
        AudioFormat.Builder builder = new AudioFormat.Builder().setSampleRate(this.mSampleRate).setEncoding(this.mAudioFormat);
        if (this.mChannelConfiguration != 0) {
            builder.setChannelMask(this.mChannelConfiguration);
        }
        if (this.mChannelIndexMask != 0) {
            builder.setChannelIndexMask(this.mChannelIndexMask);
        }
        return builder.build();
    }

    public int getChannelCount() {
        return this.mChannelCount;
    }

    public int getState() {
        return this.mState;
    }

    public int getPlayState() {
        synchronized (this.mPlayStateLock) {
            switch (this.mPlayState) {
                case 4:
                    return 3;
                case 5:
                    return 2;
                default:
                    return this.mPlayState;
            }
        }
    }

    public int getBufferSizeInFrames() {
        return native_get_buffer_size_frames();
    }

    public int setBufferSizeInFrames(int bufferSizeInFrames) {
        if (this.mDataLoadMode == 0 || this.mState == 0) {
            return -3;
        }
        if (bufferSizeInFrames < 0) {
            return -2;
        }
        return native_set_buffer_size_frames(bufferSizeInFrames);
    }

    public int getBufferCapacityInFrames() {
        return native_get_buffer_capacity_frames();
    }

    @Deprecated
    protected int getNativeFrameCount() {
        return native_get_buffer_capacity_frames();
    }

    public int getNotificationMarkerPosition() {
        return native_get_marker_pos();
    }

    public int getPositionNotificationPeriod() {
        return native_get_pos_update_period();
    }

    public int getPlaybackHeadPosition() {
        return native_get_position();
    }

    @UnsupportedAppUsage(trackingBug = 130237544)
    public int getLatency() {
        return native_get_latency();
    }

    public int getUnderrunCount() {
        return native_get_underrun_count();
    }

    public int getPerformanceMode() {
        int flags = native_get_flags();
        if ((flags & 4) != 0) {
            return 1;
        }
        if ((flags & 8) != 0) {
            return 2;
        }
        return 0;
    }

    public static int getNativeOutputSampleRate(int streamType) {
        return native_get_output_sample_rate(streamType);
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0024  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x002a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static int getMinBufferSize(int sampleRateInHz, int channelConfig, int audioFormat) {
        int channelCount;
        if (channelConfig != 12) {
            switch (channelConfig) {
                case 2:
                case 4:
                    channelCount = 1;
                    break;
                case 3:
                    break;
                default:
                    if (!isMultichannelConfigSupported(channelConfig)) {
                        loge("getMinBufferSize(): Invalid channel configuration.");
                        return -2;
                    }
                    channelCount = AudioFormat.channelCountFromOutChannelMask(channelConfig);
                    break;
            }
            if (AudioFormat.isPublicEncoding(audioFormat)) {
                loge("getMinBufferSize(): Invalid audio format.");
                return -2;
            } else if (sampleRateInHz < 4000 || sampleRateInHz > 192000) {
                loge("getMinBufferSize(): " + sampleRateInHz + " Hz is not a supported sample rate.");
                return -2;
            } else {
                int size = native_get_min_buff_size(sampleRateInHz, channelCount, audioFormat);
                if (size <= 0) {
                    loge("getMinBufferSize(): error querying hardware");
                    return -1;
                }
                return size;
            }
        }
        channelCount = 2;
        if (AudioFormat.isPublicEncoding(audioFormat)) {
        }
    }

    public int getAudioSessionId() {
        return this.mSessionId;
    }

    public boolean getTimestamp(AudioTimestamp timestamp) {
        if (timestamp == null) {
            throw new IllegalArgumentException();
        }
        long[] longArray = new long[2];
        int ret = native_get_timestamp(longArray);
        if (ret != 0) {
            return false;
        }
        timestamp.framePosition = longArray[0];
        timestamp.nanoTime = longArray[1];
        return true;
    }

    public int getTimestampWithStatus(AudioTimestamp timestamp) {
        if (timestamp == null) {
            throw new IllegalArgumentException();
        }
        long[] longArray = new long[2];
        int ret = native_get_timestamp(longArray);
        timestamp.framePosition = longArray[0];
        timestamp.nanoTime = longArray[1];
        return ret;
    }

    public PersistableBundle getMetrics() {
        PersistableBundle bundle = native_getMetrics();
        return bundle;
    }

    public void setPlaybackPositionUpdateListener(OnPlaybackPositionUpdateListener listener) {
        setPlaybackPositionUpdateListener(listener, null);
    }

    public void setPlaybackPositionUpdateListener(OnPlaybackPositionUpdateListener listener, Handler handler) {
        if (listener != null) {
            this.mEventHandlerDelegate = new NativePositionEventHandlerDelegate(this, listener, handler);
        } else {
            this.mEventHandlerDelegate = null;
        }
    }

    private static float clampGainOrLevel(float gainOrLevel) {
        if (Float.isNaN(gainOrLevel)) {
            throw new IllegalArgumentException();
        }
        if (gainOrLevel < 0.0f) {
            return 0.0f;
        }
        if (gainOrLevel > 1.0f) {
            return 1.0f;
        }
        return gainOrLevel;
    }

    @Deprecated
    public int setStereoVolume(float leftGain, float rightGain) {
        if (this.mState == 0) {
            return -3;
        }
        baseSetVolume(leftGain, rightGain);
        return 0;
    }

    @Override // android.media.PlayerBase
    void playerSetVolume(boolean muting, float leftVolume, float rightVolume) {
        native_setVolume(clampGainOrLevel(muting ? 0.0f : leftVolume), clampGainOrLevel(muting ? 0.0f : rightVolume));
    }

    public int setVolume(float gain) {
        return setStereoVolume(gain, gain);
    }

    @Override // android.media.PlayerBase
    int playerApplyVolumeShaper(VolumeShaper.Configuration configuration, VolumeShaper.Operation operation) {
        return native_applyVolumeShaper(configuration, operation);
    }

    @Override // android.media.PlayerBase
    VolumeShaper.State playerGetVolumeShaperState(int id) {
        return native_getVolumeShaperState(id);
    }

    @Override // android.media.VolumeAutomation
    public VolumeShaper createVolumeShaper(VolumeShaper.Configuration configuration) {
        return new VolumeShaper(configuration, this);
    }

    public int setPlaybackRate(int sampleRateInHz) {
        if (this.mState != 1) {
            return -3;
        }
        if (sampleRateInHz <= 0) {
            return -2;
        }
        return native_set_playback_rate(sampleRateInHz);
    }

    public void setPlaybackParams(PlaybackParams params) {
        if (params == null) {
            throw new IllegalArgumentException("params is null");
        }
        native_set_playback_params(params);
    }

    public int setNotificationMarkerPosition(int markerInFrames) {
        if (this.mState == 0) {
            return -3;
        }
        return native_set_marker_pos(markerInFrames);
    }

    public int setPositionNotificationPeriod(int periodInFrames) {
        if (this.mState == 0) {
            return -3;
        }
        return native_set_pos_update_period(periodInFrames);
    }

    public int setPlaybackHeadPosition(int positionInFrames) {
        if (this.mDataLoadMode == 1 || this.mState == 0 || getPlayState() == 3) {
            return -3;
        }
        if (positionInFrames < 0 || positionInFrames > this.mNativeBufferSizeInFrames) {
            return -2;
        }
        return native_set_position(positionInFrames);
    }

    public int setLoopPoints(int startInFrames, int endInFrames, int loopCount) {
        if (this.mDataLoadMode == 1 || this.mState == 0 || getPlayState() == 3) {
            return -3;
        }
        if (loopCount != 0 && (startInFrames < 0 || startInFrames >= this.mNativeBufferSizeInFrames || startInFrames >= endInFrames || endInFrames > this.mNativeBufferSizeInFrames)) {
            return -2;
        }
        return native_set_loop(startInFrames, endInFrames, loopCount);
    }

    public int setPresentation(AudioPresentation presentation) {
        if (presentation == null) {
            throw new IllegalArgumentException("audio presentation is null");
        }
        return native_setPresentation(presentation.getPresentationId(), presentation.getProgramId());
    }

    @Deprecated
    protected void setState(int state) {
        this.mState = state;
    }

    /* JADX WARN: Type inference failed for: r1v2, types: [android.media.AudioTrack$1] */
    public void play() throws IllegalStateException {
        if (this.mState != 1) {
            throw new IllegalStateException("play() called on uninitialized AudioTrack.");
        }
        final int delay = getStartDelayMs();
        if (delay == 0) {
            startImpl();
        } else {
            new Thread() { // from class: android.media.AudioTrack.1
                @Override // java.lang.Thread, java.lang.Runnable
                public void run() {
                    try {
                        Thread.sleep(delay);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    AudioTrack.this.baseSetStartDelayMs(0);
                    try {
                        AudioTrack.this.startImpl();
                    } catch (IllegalStateException e2) {
                    }
                }
            }.start();
        }
        this.mNaviHelper.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startImpl() {
        synchronized (this.mPlayStateLock) {
            baseStart();
            native_start();
            if (this.mPlayState == 5) {
                this.mPlayState = 4;
            } else {
                this.mPlayState = 3;
                this.mOffloadEosPending = false;
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0021, code lost:
        com.wits.pms.PowerManagerApp.sendCommand("{\"command\":1,\"jsonArg\":\"false\",\"subCommand\":608}");
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void stop() throws IllegalStateException {
        if (this.mState != 1) {
            throw new IllegalStateException("stop() called on uninitialized AudioTrack.");
        }
        try {
            StackTraceElement[] element = Thread.currentThread().getStackTrace();
            int i = 0;
            while (true) {
                if (i >= element.length) {
                    break;
                } else if (element[i].toString().contains("navngo.igo")) {
                    break;
                } else {
                    i++;
                }
            }
        } catch (IllegalStateException e) {
        }
        synchronized (this.mPlayStateLock) {
            native_stop();
            baseStop();
            if (this.mOffloaded && this.mPlayState != 5) {
                this.mPlayState = 4;
            } else {
                this.mPlayState = 1;
                this.mOffloadEosPending = false;
                this.mAvSyncHeader = null;
                this.mAvSyncBytesRemaining = 0;
                this.mPlayStateLock.notify();
            }
        }
    }

    public void pause() throws IllegalStateException {
        if (this.mState != 1) {
            throw new IllegalStateException("pause() called on uninitialized AudioTrack.");
        }
        synchronized (this.mPlayStateLock) {
            native_pause();
            basePause();
            if (this.mPlayState == 4) {
                this.mPlayState = 5;
            } else {
                this.mPlayState = 2;
            }
        }
    }

    public void flush() {
        if (this.mState == 1) {
            native_flush();
            this.mAvSyncHeader = null;
            this.mAvSyncBytesRemaining = 0;
        }
    }

    public int write(byte[] audioData, int offsetInBytes, int sizeInBytes) {
        return write(audioData, offsetInBytes, sizeInBytes, 0);
    }

    public int write(byte[] audioData, int offsetInBytes, int sizeInBytes, int writeMode) {
        if (this.mState == 0 || this.mAudioFormat == 4) {
            return -3;
        }
        if (writeMode != 0 && writeMode != 1) {
            Log.m70e(TAG, "AudioTrack.write() called with invalid blocking mode");
            return -2;
        } else if (audioData == null || offsetInBytes < 0 || sizeInBytes < 0 || offsetInBytes + sizeInBytes < 0 || offsetInBytes + sizeInBytes > audioData.length) {
            return -2;
        } else {
            if (blockUntilOffloadDrain(writeMode)) {
                int ret = native_write_byte(audioData, offsetInBytes, sizeInBytes, this.mAudioFormat, writeMode == 0);
                if (this.mDataLoadMode == 0 && this.mState == 2 && ret > 0) {
                    this.mState = 1;
                }
                return ret;
            }
            return 0;
        }
    }

    public int write(short[] audioData, int offsetInShorts, int sizeInShorts) {
        return write(audioData, offsetInShorts, sizeInShorts, 0);
    }

    public int write(short[] audioData, int offsetInShorts, int sizeInShorts, int writeMode) {
        if (this.mState == 0 || this.mAudioFormat == 4) {
            return -3;
        }
        if (writeMode != 0 && writeMode != 1) {
            Log.m70e(TAG, "AudioTrack.write() called with invalid blocking mode");
            return -2;
        } else if (audioData == null || offsetInShorts < 0 || sizeInShorts < 0 || offsetInShorts + sizeInShorts < 0 || offsetInShorts + sizeInShorts > audioData.length) {
            return -2;
        } else {
            if (blockUntilOffloadDrain(writeMode)) {
                int ret = native_write_short(audioData, offsetInShorts, sizeInShorts, this.mAudioFormat, writeMode == 0);
                if (this.mDataLoadMode == 0 && this.mState == 2 && ret > 0) {
                    this.mState = 1;
                }
                return ret;
            }
            return 0;
        }
    }

    public int write(float[] audioData, int offsetInFloats, int sizeInFloats, int writeMode) {
        if (this.mState == 0) {
            Log.m70e(TAG, "AudioTrack.write() called in invalid state STATE_UNINITIALIZED");
            return -3;
        } else if (this.mAudioFormat != 4) {
            Log.m70e(TAG, "AudioTrack.write(float[] ...) requires format ENCODING_PCM_FLOAT");
            return -3;
        } else if (writeMode != 0 && writeMode != 1) {
            Log.m70e(TAG, "AudioTrack.write() called with invalid blocking mode");
            return -2;
        } else if (audioData == null || offsetInFloats < 0 || sizeInFloats < 0 || offsetInFloats + sizeInFloats < 0 || offsetInFloats + sizeInFloats > audioData.length) {
            Log.m70e(TAG, "AudioTrack.write() called with invalid array, offset, or size");
            return -2;
        } else if (blockUntilOffloadDrain(writeMode)) {
            int ret = native_write_float(audioData, offsetInFloats, sizeInFloats, this.mAudioFormat, writeMode == 0);
            if (this.mDataLoadMode == 0 && this.mState == 2 && ret > 0) {
                this.mState = 1;
            }
            return ret;
        } else {
            return 0;
        }
    }

    public int write(ByteBuffer audioData, int sizeInBytes, int writeMode) {
        int ret;
        if (this.mState == 0) {
            Log.m70e(TAG, "AudioTrack.write() called in invalid state STATE_UNINITIALIZED");
            return -3;
        } else if (writeMode != 0 && writeMode != 1) {
            Log.m70e(TAG, "AudioTrack.write() called with invalid blocking mode");
            return -2;
        } else if (audioData == null || sizeInBytes < 0 || sizeInBytes > audioData.remaining()) {
            Log.m70e(TAG, "AudioTrack.write() called with invalid size (" + sizeInBytes + ") value");
            return -2;
        } else if (blockUntilOffloadDrain(writeMode)) {
            if (audioData.isDirect()) {
                ret = native_write_native_bytes(audioData, audioData.position(), sizeInBytes, this.mAudioFormat, writeMode == 0);
            } else {
                ret = native_write_byte(NioUtils.unsafeArray(audioData), NioUtils.unsafeArrayOffset(audioData) + audioData.position(), sizeInBytes, this.mAudioFormat, writeMode == 0);
            }
            if (this.mDataLoadMode == 0 && this.mState == 2 && ret > 0) {
                this.mState = 1;
            }
            if (ret > 0) {
                audioData.position(audioData.position() + ret);
            }
            return ret;
        } else {
            return 0;
        }
    }

    public int write(ByteBuffer audioData, int sizeInBytes, int writeMode, long timestamp) {
        if (this.mState == 0) {
            Log.m70e(TAG, "AudioTrack.write() called in invalid state STATE_UNINITIALIZED");
            return -3;
        } else if (writeMode != 0 && writeMode != 1) {
            Log.m70e(TAG, "AudioTrack.write() called with invalid blocking mode");
            return -2;
        } else if (this.mDataLoadMode != 1) {
            Log.m70e(TAG, "AudioTrack.write() with timestamp called for non-streaming mode track");
            return -3;
        } else if ((this.mAttributes.getFlags() & 16) == 0) {
            Log.m72d(TAG, "AudioTrack.write() called on a regular AudioTrack. Ignoring pts...");
            return write(audioData, sizeInBytes, writeMode);
        } else if (audioData == null || sizeInBytes < 0 || sizeInBytes > audioData.remaining()) {
            Log.m70e(TAG, "AudioTrack.write() called with invalid size (" + sizeInBytes + ") value");
            return -2;
        } else if (blockUntilOffloadDrain(writeMode)) {
            if (this.mAvSyncHeader == null) {
                this.mAvSyncHeader = ByteBuffer.allocate(this.mOffset);
                this.mAvSyncHeader.order(ByteOrder.BIG_ENDIAN);
                this.mAvSyncHeader.putInt(1431633922);
            }
            if (this.mAvSyncBytesRemaining == 0) {
                this.mAvSyncHeader.putInt(4, sizeInBytes);
                this.mAvSyncHeader.putLong(8, timestamp);
                this.mAvSyncHeader.putInt(16, this.mOffset);
                this.mAvSyncHeader.position(0);
                this.mAvSyncBytesRemaining = sizeInBytes;
            }
            if (this.mAvSyncHeader.remaining() != 0) {
                int ret = write(this.mAvSyncHeader, this.mAvSyncHeader.remaining(), writeMode);
                if (ret < 0) {
                    Log.m70e(TAG, "AudioTrack.write() could not write timestamp header!");
                    this.mAvSyncHeader = null;
                    this.mAvSyncBytesRemaining = 0;
                    return ret;
                } else if (this.mAvSyncHeader.remaining() > 0) {
                    Log.m66v(TAG, "AudioTrack.write() partial timestamp header written.");
                    return 0;
                }
            }
            int sizeToWrite = Math.min(this.mAvSyncBytesRemaining, sizeInBytes);
            int ret2 = write(audioData, sizeToWrite, writeMode);
            if (ret2 < 0) {
                Log.m70e(TAG, "AudioTrack.write() could not write audio data!");
                this.mAvSyncHeader = null;
                this.mAvSyncBytesRemaining = 0;
                return ret2;
            }
            this.mAvSyncBytesRemaining -= ret2;
            return ret2;
        } else {
            return 0;
        }
    }

    public int reloadStaticData() {
        if (this.mDataLoadMode == 1 || this.mState != 1) {
            return -3;
        }
        return native_reload_static();
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x0015, code lost:
        return false;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private boolean blockUntilOffloadDrain(int writeMode) {
        synchronized (this.mPlayStateLock) {
            while (true) {
                if (this.mPlayState != 4 && this.mPlayState != 5) {
                    return true;
                }
                try {
                    this.mPlayStateLock.wait();
                } catch (InterruptedException e) {
                }
            }
        }
    }

    public int attachAuxEffect(int effectId) {
        if (this.mState == 0) {
            return -3;
        }
        return native_attachAuxEffect(effectId);
    }

    public int setAuxEffectSendLevel(float level) {
        if (this.mState == 0) {
            return -3;
        }
        return baseSetAuxEffectSendLevel(level);
    }

    @Override // android.media.PlayerBase
    int playerSetAuxEffectSendLevel(boolean muting, float level) {
        int err = native_setAuxEffectSendLevel(clampGainOrLevel(muting ? 0.0f : level));
        return err == 0 ? 0 : -1;
    }

    @Override // android.media.AudioRouting
    public boolean setPreferredDevice(AudioDeviceInfo deviceInfo) {
        if (deviceInfo != null && !deviceInfo.isSink()) {
            return false;
        }
        int preferredDeviceId = deviceInfo != null ? deviceInfo.getId() : 0;
        boolean status = native_setOutputDevice(preferredDeviceId);
        if (status) {
            synchronized (this) {
                this.mPreferredDevice = deviceInfo;
            }
        }
        return status;
    }

    @Override // android.media.AudioRouting
    public AudioDeviceInfo getPreferredDevice() {
        AudioDeviceInfo audioDeviceInfo;
        synchronized (this) {
            audioDeviceInfo = this.mPreferredDevice;
        }
        return audioDeviceInfo;
    }

    @Override // android.media.AudioRouting
    public AudioDeviceInfo getRoutedDevice() {
        int deviceId = native_getRoutedDeviceId();
        if (deviceId == 0) {
            return null;
        }
        AudioDeviceInfo[] devices = AudioManager.getDevicesStatic(2);
        for (int i = 0; i < devices.length; i++) {
            if (devices[i].getId() == deviceId) {
                return devices[i];
            }
        }
        return null;
    }

    @GuardedBy({"mRoutingChangeListeners"})
    private void testEnableNativeRoutingCallbacksLocked() {
        if (this.mRoutingChangeListeners.size() == 0) {
            native_enableDeviceCallback();
        }
    }

    @GuardedBy({"mRoutingChangeListeners"})
    private void testDisableNativeRoutingCallbacksLocked() {
        if (this.mRoutingChangeListeners.size() == 0) {
            native_disableDeviceCallback();
        }
    }

    @Override // android.media.AudioRouting
    public void addOnRoutingChangedListener(AudioRouting.OnRoutingChangedListener listener, Handler handler) {
        synchronized (this.mRoutingChangeListeners) {
            if (listener != null) {
                try {
                    if (!this.mRoutingChangeListeners.containsKey(listener)) {
                        testEnableNativeRoutingCallbacksLocked();
                        this.mRoutingChangeListeners.put(listener, new NativeRoutingEventHandlerDelegate(this, listener, handler != null ? handler : new Handler(this.mInitializationLooper)));
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    @Override // android.media.AudioRouting
    public void removeOnRoutingChangedListener(AudioRouting.OnRoutingChangedListener listener) {
        synchronized (this.mRoutingChangeListeners) {
            if (this.mRoutingChangeListeners.containsKey(listener)) {
                this.mRoutingChangeListeners.remove(listener);
            }
            testDisableNativeRoutingCallbacksLocked();
        }
    }

    @Deprecated
    /* loaded from: classes3.dex */
    public interface OnRoutingChangedListener extends AudioRouting.OnRoutingChangedListener {
        void onRoutingChanged(AudioTrack audioTrack);

        @Override // android.media.AudioRouting.OnRoutingChangedListener
        default void onRoutingChanged(AudioRouting router) {
            if (router instanceof AudioTrack) {
                onRoutingChanged((AudioTrack) router);
            }
        }
    }

    @Deprecated
    public void addOnRoutingChangedListener(OnRoutingChangedListener listener, Handler handler) {
        addOnRoutingChangedListener((AudioRouting.OnRoutingChangedListener) listener, handler);
    }

    @Deprecated
    public void removeOnRoutingChangedListener(OnRoutingChangedListener listener) {
        removeOnRoutingChangedListener((AudioRouting.OnRoutingChangedListener) listener);
    }

    private void broadcastRoutingChange() {
        AudioManager.resetAudioPortGeneration();
        synchronized (this.mRoutingChangeListeners) {
            for (NativeRoutingEventHandlerDelegate delegate : this.mRoutingChangeListeners.values()) {
                delegate.notifyClient();
            }
        }
    }

    /* loaded from: classes3.dex */
    public static abstract class StreamEventCallback {
        public void onTearDown(AudioTrack track) {
        }

        public void onPresentationEnded(AudioTrack track) {
        }

        public void onDataRequest(AudioTrack track, int sizeInFrames) {
        }
    }

    public void registerStreamEventCallback(Executor executor, StreamEventCallback eventCallback) {
        if (eventCallback == null) {
            throw new IllegalArgumentException("Illegal null StreamEventCallback");
        }
        if (!this.mOffloaded) {
            throw new IllegalStateException("Cannot register StreamEventCallback on non-offloaded AudioTrack");
        }
        if (executor == null) {
            throw new IllegalArgumentException("Illegal null Executor for the StreamEventCallback");
        }
        synchronized (this.mStreamEventCbLock) {
            Iterator<StreamEventCbInfo> it = this.mStreamEventCbInfoList.iterator();
            while (it.hasNext()) {
                StreamEventCbInfo seci = it.next();
                if (seci.mStreamEventCb == eventCallback) {
                    throw new IllegalArgumentException("StreamEventCallback already registered");
                }
            }
            beginStreamEventHandling();
            this.mStreamEventCbInfoList.add(new StreamEventCbInfo(executor, eventCallback));
        }
    }

    public void unregisterStreamEventCallback(StreamEventCallback eventCallback) {
        if (eventCallback == null) {
            throw new IllegalArgumentException("Illegal null StreamEventCallback");
        }
        if (!this.mOffloaded) {
            throw new IllegalStateException("No StreamEventCallback on non-offloaded AudioTrack");
        }
        synchronized (this.mStreamEventCbLock) {
            Iterator<StreamEventCbInfo> it = this.mStreamEventCbInfoList.iterator();
            while (it.hasNext()) {
                StreamEventCbInfo seci = it.next();
                if (seci.mStreamEventCb == eventCallback) {
                    this.mStreamEventCbInfoList.remove(seci);
                    if (this.mStreamEventCbInfoList.size() == 0) {
                        endStreamEventHandling();
                    }
                }
            }
            throw new IllegalArgumentException("StreamEventCallback was not registered");
        }
    }

    /* loaded from: classes3.dex */
    private static class StreamEventCbInfo {
        final StreamEventCallback mStreamEventCb;
        final Executor mStreamEventExec;

        StreamEventCbInfo(Executor e, StreamEventCallback cb) {
            this.mStreamEventExec = e;
            this.mStreamEventCb = cb;
        }
    }

    void handleStreamEventFromNative(int what, int arg) {
        if (this.mStreamEventHandler == null) {
            return;
        }
        switch (what) {
            case 6:
                this.mStreamEventHandler.sendMessage(this.mStreamEventHandler.obtainMessage(6));
                return;
            case 7:
                this.mStreamEventHandler.sendMessage(this.mStreamEventHandler.obtainMessage(7));
                return;
            case 8:
            default:
                return;
            case 9:
                this.mStreamEventHandler.removeMessages(9);
                this.mStreamEventHandler.sendMessage(this.mStreamEventHandler.obtainMessage(9, arg, 0));
                return;
        }
    }

    /* loaded from: classes3.dex */
    private class StreamEventHandler extends Handler {
        StreamEventHandler(Looper looper) {
            super(looper);
        }

        @Override // android.p007os.Handler
        public void handleMessage(final Message msg) {
            synchronized (AudioTrack.this.mStreamEventCbLock) {
                if (msg.what == 7) {
                    synchronized (AudioTrack.this.mPlayStateLock) {
                        if (AudioTrack.this.mPlayState == 4) {
                            if (AudioTrack.this.mOffloadEosPending) {
                                AudioTrack.this.native_start();
                                AudioTrack.this.mPlayState = 3;
                            } else {
                                AudioTrack.this.mAvSyncHeader = null;
                                AudioTrack.this.mAvSyncBytesRemaining = 0;
                                AudioTrack.this.mPlayState = 1;
                            }
                            AudioTrack.this.mOffloadEosPending = false;
                            AudioTrack.this.mPlayStateLock.notify();
                        }
                    }
                }
                if (AudioTrack.this.mStreamEventCbInfoList.size() == 0) {
                    return;
                }
                LinkedList<StreamEventCbInfo> cbInfoList = new LinkedList<>(AudioTrack.this.mStreamEventCbInfoList);
                long identity = Binder.clearCallingIdentity();
                try {
                    Iterator<StreamEventCbInfo> it = cbInfoList.iterator();
                    while (it.hasNext()) {
                        final StreamEventCbInfo cbi = it.next();
                        switch (msg.what) {
                            case 6:
                                cbi.mStreamEventExec.execute(new Runnable() { // from class: android.media.-$$Lambda$AudioTrack$StreamEventHandler$uWnWUbk1g3MhAY3NoSFc6o37wsk
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        cbi.mStreamEventCb.onTearDown(AudioTrack.this);
                                    }
                                });
                                break;
                            case 7:
                                cbi.mStreamEventExec.execute(new Runnable() { // from class: android.media.-$$Lambda$AudioTrack$StreamEventHandler$-3NLz6Sbq0z_YUytzGW6tVjPCao
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        cbi.mStreamEventCb.onPresentationEnded(AudioTrack.this);
                                    }
                                });
                                break;
                            case 9:
                                cbi.mStreamEventExec.execute(new Runnable() { // from class: android.media.-$$Lambda$AudioTrack$StreamEventHandler$IUDediua4qA5AgKwU3zNCXA7jQo
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        cbi.mStreamEventCb.onDataRequest(AudioTrack.this, msg.arg1);
                                    }
                                });
                                break;
                        }
                    }
                } finally {
                    Binder.restoreCallingIdentity(identity);
                }
            }
        }
    }

    @GuardedBy({"mStreamEventCbLock"})
    private void beginStreamEventHandling() {
        if (this.mStreamEventHandlerThread == null) {
            this.mStreamEventHandlerThread = new HandlerThread("android.media.AudioTrack.StreamEvent");
            this.mStreamEventHandlerThread.start();
            Looper looper = this.mStreamEventHandlerThread.getLooper();
            if (looper != null) {
                this.mStreamEventHandler = new StreamEventHandler(looper);
            }
        }
    }

    @GuardedBy({"mStreamEventCbLock"})
    private void endStreamEventHandling() {
        if (this.mStreamEventHandlerThread != null) {
            this.mStreamEventHandlerThread.quit();
            this.mStreamEventHandlerThread = null;
        }
    }

    /* loaded from: classes3.dex */
    private class NativePositionEventHandlerDelegate {
        private final Handler mHandler;

        NativePositionEventHandlerDelegate(final AudioTrack track, final OnPlaybackPositionUpdateListener listener, Handler handler) {
            Looper looper;
            if (handler == null) {
                looper = AudioTrack.this.mInitializationLooper;
            } else {
                looper = handler.getLooper();
            }
            if (looper != null) {
                this.mHandler = new Handler(looper) { // from class: android.media.AudioTrack.NativePositionEventHandlerDelegate.1
                    @Override // android.p007os.Handler
                    public void handleMessage(Message msg) {
                        if (track == null) {
                            return;
                        }
                        switch (msg.what) {
                            case 3:
                                if (listener != null) {
                                    listener.onMarkerReached(track);
                                    return;
                                }
                                return;
                            case 4:
                                if (listener != null) {
                                    listener.onPeriodicNotification(track);
                                    return;
                                }
                                return;
                            default:
                                AudioTrack.loge("Unknown native event type: " + msg.what);
                                return;
                        }
                    }
                };
            } else {
                this.mHandler = null;
            }
        }

        Handler getHandler() {
            return this.mHandler;
        }
    }

    @Override // android.media.PlayerBase
    void playerStart() {
        play();
    }

    @Override // android.media.PlayerBase
    void playerPause() {
        pause();
    }

    @Override // android.media.PlayerBase
    void playerStop() {
        stop();
    }

    @UnsupportedAppUsage
    private static void postEventFromNative(Object audiotrack_ref, int what, int arg1, int arg2, Object obj) {
        Handler handler;
        AudioTrack track = (AudioTrack) ((WeakReference) audiotrack_ref).get();
        if (track == null) {
            return;
        }
        if (what == 1000) {
            track.broadcastRoutingChange();
        } else if (what == 9 || what == 6 || what == 7) {
            track.handleStreamEventFromNative(what, arg1);
        } else {
            NativePositionEventHandlerDelegate delegate = track.mEventHandlerDelegate;
            if (delegate != null && (handler = delegate.getHandler()) != null) {
                Message m = handler.obtainMessage(what, arg1, arg2, obj);
                handler.sendMessage(m);
            }
        }
    }

    private static void logd(String msg) {
        Log.m72d(TAG, msg);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void loge(String msg) {
        Log.m70e(TAG, msg);
    }

    /* loaded from: classes3.dex */
    public static final class MetricsConstants {
        public static final String ATTRIBUTES = "android.media.audiotrack.attributes";
        @Deprecated
        public static final String CHANNELMASK = "android.media.audiorecord.channelmask";
        public static final String CHANNEL_MASK = "android.media.audiotrack.channelMask";
        public static final String CONTENTTYPE = "android.media.audiotrack.type";
        public static final String ENCODING = "android.media.audiotrack.encoding";
        public static final String FRAME_COUNT = "android.media.audiotrack.frameCount";
        private static final String MM_PREFIX = "android.media.audiotrack.";
        public static final String PORT_ID = "android.media.audiotrack.portId";
        @Deprecated
        public static final String SAMPLERATE = "android.media.audiorecord.samplerate";
        public static final String SAMPLE_RATE = "android.media.audiotrack.sampleRate";
        public static final String STREAMTYPE = "android.media.audiotrack.streamtype";
        public static final String USAGE = "android.media.audiotrack.usage";

        private MetricsConstants() {
        }
    }
}
