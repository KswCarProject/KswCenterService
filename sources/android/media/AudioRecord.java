package android.media;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.app.ActivityThread;
import android.media.AudioAttributes;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRouting;
import android.media.IAudioService;
import android.media.audiopolicy.AudioMix;
import android.media.audiopolicy.AudioPolicy;
import android.media.projection.MediaProjection;
import android.p007os.Binder;
import android.p007os.Handler;
import android.p007os.IBinder;
import android.p007os.Looper;
import android.p007os.Message;
import android.p007os.PersistableBundle;
import android.p007os.RemoteException;
import android.p007os.ServiceManager;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Pair;
import android.util.SeempLog;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.util.Preconditions;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
public class AudioRecord implements AudioRouting, MicrophoneDirection, AudioRecordingMonitor, AudioRecordingMonitorClient {
    private static final int AUDIORECORD_ERROR_SETUP_INVALIDCHANNELMASK = -17;
    private static final int AUDIORECORD_ERROR_SETUP_INVALIDFORMAT = -18;
    private static final int AUDIORECORD_ERROR_SETUP_INVALIDSOURCE = -19;
    private static final int AUDIORECORD_ERROR_SETUP_NATIVEINITFAILED = -20;
    private static final int AUDIORECORD_ERROR_SETUP_ZEROFRAMECOUNT = -16;
    public static final int ERROR = -1;
    public static final int ERROR_BAD_VALUE = -2;
    public static final int ERROR_DEAD_OBJECT = -6;
    public static final int ERROR_INVALID_OPERATION = -3;
    private static final int NATIVE_EVENT_MARKER = 2;
    private static final int NATIVE_EVENT_NEW_POS = 3;
    public static final int READ_BLOCKING = 0;
    public static final int READ_NON_BLOCKING = 1;
    public static final int RECORDSTATE_RECORDING = 3;
    public static final int RECORDSTATE_STOPPED = 1;
    public static final int STATE_INITIALIZED = 1;
    public static final int STATE_UNINITIALIZED = 0;
    public static final String SUBMIX_FIXED_VOLUME = "fixedVolume";
    public static final int SUCCESS = 0;
    private static final String TAG = "android.media.AudioRecord";
    @UnsupportedAppUsage
    private AudioAttributes mAudioAttributes;
    private AudioPolicy mAudioCapturePolicy;
    private int mAudioFormat;
    private int mChannelCount;
    private int mChannelIndexMask;
    private int mChannelMask;
    private NativeEventHandler mEventHandler;
    private final IBinder mICallBack;
    @UnsupportedAppUsage
    private Looper mInitializationLooper;
    private boolean mIsSubmixFullVolume;
    private int mNativeBufferSizeInBytes;
    @UnsupportedAppUsage
    private long mNativeCallbackCookie;
    @UnsupportedAppUsage
    private long mNativeDeviceCallback;
    @UnsupportedAppUsage
    private long mNativeRecorderInJavaObj;
    private OnRecordPositionUpdateListener mPositionListener;
    private final Object mPositionListenerLock;
    private AudioDeviceInfo mPreferredDevice;
    private int mRecordSource;
    AudioRecordingMonitorImpl mRecordingInfoImpl;
    private int mRecordingState;
    private final Object mRecordingStateLock;
    @GuardedBy({"mRoutingChangeListeners"})
    private ArrayMap<AudioRouting.OnRoutingChangedListener, NativeRoutingEventHandlerDelegate> mRoutingChangeListeners;
    private int mSampleRate;
    private int mSessionId;
    private int mState;

    /* loaded from: classes3.dex */
    public interface OnRecordPositionUpdateListener {
        void onMarkerReached(AudioRecord audioRecord);

        void onPeriodicNotification(AudioRecord audioRecord);
    }

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes3.dex */
    public @interface ReadMode {
    }

    private final native void native_disableDeviceCallback();

    private final native void native_enableDeviceCallback();

    private final native void native_finalize();

    private native PersistableBundle native_getMetrics();

    private native int native_getPortId();

    private final native int native_getRoutedDeviceId();

    private final native int native_get_active_microphones(ArrayList<MicrophoneInfo> arrayList);

    private final native int native_get_buffer_size_in_frames();

    private final native int native_get_marker_pos();

    private static final native int native_get_min_buff_size(int i, int i2, int i3);

    private final native int native_get_pos_update_period();

    private final native int native_get_timestamp(AudioTimestamp audioTimestamp, int i);

    private final native int native_read_in_byte_array(byte[] bArr, int i, int i2, boolean z);

    private final native int native_read_in_direct_buffer(Object obj, int i, boolean z);

    private final native int native_read_in_float_array(float[] fArr, int i, int i2, boolean z);

    private final native int native_read_in_short_array(short[] sArr, int i, int i2, boolean z);

    private final native boolean native_setInputDevice(int i);

    private final native int native_set_marker_pos(int i);

    private final native int native_set_pos_update_period(int i);

    private native int native_set_preferred_microphone_direction(int i);

    private native int native_set_preferred_microphone_field_dimension(float f);

    @UnsupportedAppUsage
    private final native int native_setup(Object obj, Object obj2, int[] iArr, int i, int i2, int i3, int i4, int[] iArr2, String str, long j);

    private final native int native_start(int i, int i2);

    private final native void native_stop();

    @UnsupportedAppUsage
    public final native void native_release();

    public AudioRecord(int audioSource, int sampleRateInHz, int channelConfig, int audioFormat, int bufferSizeInBytes) throws IllegalArgumentException {
        this(new AudioAttributes.Builder().setInternalCapturePreset(audioSource).build(), new AudioFormat.Builder().setChannelMask(getChannelMaskFromLegacyConfig(channelConfig, true)).setEncoding(audioFormat).setSampleRate(sampleRateInHz).build(), bufferSizeInBytes, 0);
    }

    @SystemApi
    public AudioRecord(AudioAttributes attributes, AudioFormat format, int bufferSizeInBytes, int sessionId) throws IllegalArgumentException {
        this.mState = 0;
        this.mRecordingState = 1;
        this.mRecordingStateLock = new Object();
        this.mPositionListener = null;
        this.mPositionListenerLock = new Object();
        this.mEventHandler = null;
        this.mInitializationLooper = null;
        this.mNativeBufferSizeInBytes = 0;
        this.mSessionId = 0;
        this.mIsSubmixFullVolume = false;
        this.mICallBack = new Binder();
        this.mRoutingChangeListeners = new ArrayMap<>();
        this.mPreferredDevice = null;
        this.mRecordingInfoImpl = new AudioRecordingMonitorImpl(this);
        this.mRecordingState = 1;
        if (attributes == null) {
            throw new IllegalArgumentException("Illegal null AudioAttributes");
        }
        if (format == null) {
            throw new IllegalArgumentException("Illegal null AudioFormat");
        }
        Looper myLooper = Looper.myLooper();
        this.mInitializationLooper = myLooper;
        if (myLooper == null) {
            this.mInitializationLooper = Looper.getMainLooper();
        }
        if (attributes.getCapturePreset() == 8) {
            AudioAttributes.Builder filteredAttr = new AudioAttributes.Builder();
            for (String tag : attributes.getTags()) {
                if (tag.equalsIgnoreCase(SUBMIX_FIXED_VOLUME)) {
                    this.mIsSubmixFullVolume = true;
                    Log.m66v(TAG, "Will record from REMOTE_SUBMIX at full fixed volume");
                } else {
                    filteredAttr.addTag(tag);
                }
            }
            filteredAttr.setInternalCapturePreset(attributes.getCapturePreset());
            this.mAudioAttributes = filteredAttr.build();
        } else {
            this.mAudioAttributes = attributes;
        }
        int rate = format.getSampleRate();
        int rate2 = rate == 0 ? 0 : rate;
        int encoding = (format.getPropertySetMask() & 1) != 0 ? format.getEncoding() : 1;
        audioParamCheck(attributes.getCapturePreset(), rate2, encoding);
        if ((format.getPropertySetMask() & 8) != 0) {
            this.mChannelIndexMask = format.getChannelIndexMask();
            this.mChannelCount = format.getChannelCount();
        }
        if ((format.getPropertySetMask() & 4) != 0) {
            this.mChannelMask = getChannelMaskFromLegacyConfig(format.getChannelMask(), false);
            this.mChannelCount = format.getChannelCount();
        } else if (this.mChannelIndexMask == 0) {
            this.mChannelMask = getChannelMaskFromLegacyConfig(1, false);
            this.mChannelCount = AudioFormat.channelCountFromInChannelMask(this.mChannelMask);
        }
        audioBuffSizeCheck(bufferSizeInBytes);
        int[] sampleRate = {this.mSampleRate};
        int[] session = {sessionId};
        int initResult = native_setup(new WeakReference(this), this.mAudioAttributes, sampleRate, this.mChannelMask, this.mChannelIndexMask, this.mAudioFormat, this.mNativeBufferSizeInBytes, session, getCurrentOpPackageName(), 0L);
        if (initResult == 0) {
            this.mSampleRate = sampleRate[0];
            this.mSessionId = session[0];
            this.mState = 1;
            return;
        }
        loge("Error code " + initResult + " when initializing native AudioRecord object.");
    }

    private String getCurrentOpPackageName() {
        String opPackageName = ActivityThread.currentOpPackageName();
        if (opPackageName != null) {
            return opPackageName;
        }
        return "uid:" + Binder.getCallingUid();
    }

    AudioRecord(long nativeRecordInJavaObj) {
        this.mState = 0;
        this.mRecordingState = 1;
        this.mRecordingStateLock = new Object();
        this.mPositionListener = null;
        this.mPositionListenerLock = new Object();
        this.mEventHandler = null;
        this.mInitializationLooper = null;
        this.mNativeBufferSizeInBytes = 0;
        this.mSessionId = 0;
        this.mIsSubmixFullVolume = false;
        this.mICallBack = new Binder();
        this.mRoutingChangeListeners = new ArrayMap<>();
        this.mPreferredDevice = null;
        this.mRecordingInfoImpl = new AudioRecordingMonitorImpl(this);
        this.mNativeRecorderInJavaObj = 0L;
        this.mNativeCallbackCookie = 0L;
        this.mNativeDeviceCallback = 0L;
        if (nativeRecordInJavaObj != 0) {
            deferred_connect(nativeRecordInJavaObj);
        } else {
            this.mState = 0;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void unregisterAudioPolicyOnRelease(AudioPolicy audioPolicy) {
        this.mAudioCapturePolicy = audioPolicy;
    }

    void deferred_connect(long nativeRecordInJavaObj) {
        if (this.mState != 1) {
            int[] session = {0};
            int[] rates = {0};
            int initResult = native_setup(new WeakReference(this), null, rates, 0, 0, 0, 0, session, ActivityThread.currentOpPackageName(), nativeRecordInJavaObj);
            if (initResult == 0) {
                this.mSessionId = session[0];
                this.mState = 1;
                return;
            }
            loge("Error code " + initResult + " when initializing native AudioRecord object.");
        }
    }

    /* loaded from: classes3.dex */
    public static class Builder {
        private static final String ERROR_MESSAGE_SOURCE_MISMATCH = "Cannot both set audio source and set playback capture config";
        private AudioAttributes mAttributes;
        private AudioPlaybackCaptureConfiguration mAudioPlaybackCaptureConfiguration;
        private int mBufferSizeInBytes;
        private AudioFormat mFormat;
        private int mSessionId = 0;

        public Builder setAudioSource(int source) throws IllegalArgumentException {
            Preconditions.checkState(this.mAudioPlaybackCaptureConfiguration == null, ERROR_MESSAGE_SOURCE_MISMATCH);
            if (source < 0 || source > MediaRecorder.getAudioSourceMax()) {
                throw new IllegalArgumentException("Invalid audio source " + source);
            }
            this.mAttributes = new AudioAttributes.Builder().setInternalCapturePreset(source).build();
            return this;
        }

        @SystemApi
        public Builder setAudioAttributes(AudioAttributes attributes) throws IllegalArgumentException {
            if (attributes == null) {
                throw new IllegalArgumentException("Illegal null AudioAttributes argument");
            }
            if (attributes.getCapturePreset() == -1) {
                throw new IllegalArgumentException("No valid capture preset in AudioAttributes argument");
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

        public Builder setAudioPlaybackCaptureConfig(AudioPlaybackCaptureConfiguration config) {
            Preconditions.checkNotNull(config, "Illegal null AudioPlaybackCaptureConfiguration argument");
            Preconditions.checkState(this.mAttributes == null, ERROR_MESSAGE_SOURCE_MISMATCH);
            this.mAudioPlaybackCaptureConfiguration = config;
            return this;
        }

        @SystemApi
        public Builder setSessionId(int sessionId) throws IllegalArgumentException {
            if (sessionId < 0) {
                throw new IllegalArgumentException("Invalid session ID " + sessionId);
            }
            this.mSessionId = sessionId;
            return this;
        }

        private AudioRecord buildAudioPlaybackCaptureRecord() {
            AudioMix audioMix = this.mAudioPlaybackCaptureConfiguration.createAudioMix(this.mFormat);
            MediaProjection projection = this.mAudioPlaybackCaptureConfiguration.getMediaProjection();
            AudioPolicy audioPolicy = new AudioPolicy.Builder(null).setMediaProjection(projection).addMix(audioMix).build();
            int error = AudioManager.registerAudioPolicyStatic(audioPolicy);
            if (error != 0) {
                throw new UnsupportedOperationException("Error: could not register audio policy");
            }
            AudioRecord record = audioPolicy.createAudioRecordSink(audioMix);
            if (record != null) {
                record.unregisterAudioPolicyOnRelease(audioPolicy);
                return record;
            }
            throw new UnsupportedOperationException("Cannot create AudioRecord");
        }

        public AudioRecord build() throws UnsupportedOperationException {
            if (this.mAudioPlaybackCaptureConfiguration != null) {
                return buildAudioPlaybackCaptureRecord();
            }
            if (this.mFormat == null) {
                this.mFormat = new AudioFormat.Builder().setEncoding(2).setChannelMask(16).build();
            } else {
                if (this.mFormat.getEncoding() == 0) {
                    this.mFormat = new AudioFormat.Builder(this.mFormat).setEncoding(2).build();
                }
                if (this.mFormat.getChannelMask() == 0 && this.mFormat.getChannelIndexMask() == 0) {
                    this.mFormat = new AudioFormat.Builder(this.mFormat).setChannelMask(16).build();
                }
            }
            if (this.mAttributes == null) {
                this.mAttributes = new AudioAttributes.Builder().setInternalCapturePreset(0).build();
            }
            try {
                if (this.mBufferSizeInBytes == 0) {
                    int channelCount = this.mFormat.getChannelCount();
                    AudioFormat audioFormat = this.mFormat;
                    this.mBufferSizeInBytes = channelCount * AudioFormat.getBytesPerSample(this.mFormat.getEncoding());
                }
                AudioRecord record = new AudioRecord(this.mAttributes, this.mFormat, this.mBufferSizeInBytes, this.mSessionId);
                if (record.getState() == 0) {
                    throw new UnsupportedOperationException("Cannot create AudioRecord");
                }
                return record;
            } catch (IllegalArgumentException e) {
                throw new UnsupportedOperationException(e.getMessage());
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0022  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static int getChannelMaskFromLegacyConfig(int inChannelConfig, boolean allowLegacyConfig) {
        int mask;
        if (inChannelConfig != 12) {
            if (inChannelConfig != 16) {
                if (inChannelConfig == 48) {
                    mask = inChannelConfig;
                    if (allowLegacyConfig) {
                    }
                    return mask;
                }
                switch (inChannelConfig) {
                    case 1:
                    case 2:
                        break;
                    case 3:
                        break;
                    default:
                        throw new IllegalArgumentException("Unsupported channel configuration.");
                }
                if (allowLegacyConfig && (inChannelConfig == 2 || inChannelConfig == 3)) {
                    throw new IllegalArgumentException("Unsupported deprecated configuration.");
                }
                return mask;
            }
            mask = 16;
            if (allowLegacyConfig) {
            }
            return mask;
        }
        mask = 12;
        if (allowLegacyConfig) {
        }
        return mask;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private void audioParamCheck(int audioSource, int sampleRateInHz, int audioFormat) throws IllegalArgumentException {
        if (audioSource < 0 || (audioSource > MediaRecorder.getAudioSourceMax() && audioSource != 1998 && audioSource != 1997 && audioSource != 1999)) {
            throw new IllegalArgumentException("Invalid audio source " + audioSource);
        }
        this.mRecordSource = audioSource;
        if ((sampleRateInHz < 4000 || sampleRateInHz > 192000) && sampleRateInHz != 0) {
            throw new IllegalArgumentException(sampleRateInHz + "Hz is not a supported sample rate.");
        }
        this.mSampleRate = sampleRateInHz;
        switch (audioFormat) {
            case 1:
                this.mAudioFormat = 2;
                return;
            case 2:
            case 3:
            case 4:
                break;
            default:
                switch (audioFormat) {
                    case 100:
                    case 101:
                    case 102:
                    case 103:
                    case 104:
                    case 105:
                        break;
                    default:
                        throw new IllegalArgumentException("Unsupported sample encoding " + audioFormat + ". Should be ENCODING_PCM_8BIT, ENCODING_PCM_16BIT, or ENCODING_PCM_FLOAT.");
                }
        }
        this.mAudioFormat = audioFormat;
    }

    private void audioBuffSizeCheck(int audioBufferSize) throws IllegalArgumentException {
        int frameSizeInBytes = this.mChannelCount * AudioFormat.getBytesPerSample(this.mAudioFormat);
        if (audioBufferSize % frameSizeInBytes != 0 || audioBufferSize < 1) {
            throw new IllegalArgumentException("Invalid audio buffer size " + audioBufferSize + " (frame size " + frameSizeInBytes + ")");
        }
        this.mNativeBufferSizeInBytes = audioBufferSize;
    }

    public void release() {
        try {
            stop();
        } catch (IllegalStateException e) {
        }
        if (this.mAudioCapturePolicy != null) {
            AudioManager.unregisterAudioPolicyAsyncStatic(this.mAudioCapturePolicy);
            this.mAudioCapturePolicy = null;
        }
        native_release();
        this.mState = 0;
    }

    protected void finalize() {
        release();
    }

    public int getSampleRate() {
        return this.mSampleRate;
    }

    public int getAudioSource() {
        return this.mRecordSource;
    }

    public int getAudioFormat() {
        return this.mAudioFormat;
    }

    public int getChannelConfiguration() {
        return this.mChannelMask;
    }

    public AudioFormat getFormat() {
        AudioFormat.Builder builder = new AudioFormat.Builder().setSampleRate(this.mSampleRate).setEncoding(this.mAudioFormat);
        if (this.mChannelMask != 0) {
            builder.setChannelMask(this.mChannelMask);
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

    public int getRecordingState() {
        int i;
        synchronized (this.mRecordingStateLock) {
            i = this.mRecordingState;
        }
        return i;
    }

    public int getBufferSizeInFrames() {
        return native_get_buffer_size_in_frames();
    }

    public int getNotificationMarkerPosition() {
        return native_get_marker_pos();
    }

    public int getPositionNotificationPeriod() {
        return native_get_pos_update_period();
    }

    public int getTimestamp(AudioTimestamp outTimestamp, int timebase) {
        if (outTimestamp == null || (timebase != 1 && timebase != 0)) {
            throw new IllegalArgumentException();
        }
        return native_get_timestamp(outTimestamp, timebase);
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0027 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0028  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static int getMinBufferSize(int sampleRateInHz, int channelConfig, int audioFormat) {
        int channelCount;
        int size;
        if (channelConfig != 12) {
            if (channelConfig != 16) {
                if (channelConfig != 48) {
                    if (channelConfig == 252) {
                        channelCount = 6;
                        size = native_get_min_buff_size(sampleRateInHz, channelCount, audioFormat);
                        if (size != 0) {
                        }
                    } else {
                        switch (channelConfig) {
                            case 1:
                            case 2:
                                break;
                            case 3:
                                break;
                            default:
                                loge("getMinBufferSize(): Invalid channel configuration.");
                                return -2;
                        }
                        size = native_get_min_buff_size(sampleRateInHz, channelCount, audioFormat);
                        if (size != 0) {
                            return -2;
                        }
                        if (size == -1) {
                            return -1;
                        }
                        return size;
                    }
                }
            }
            channelCount = 1;
            size = native_get_min_buff_size(sampleRateInHz, channelCount, audioFormat);
            if (size != 0) {
            }
        }
        channelCount = 2;
        size = native_get_min_buff_size(sampleRateInHz, channelCount, audioFormat);
        if (size != 0) {
        }
    }

    public int getAudioSessionId() {
        return this.mSessionId;
    }

    public void startRecording() throws IllegalStateException {
        SeempLog.record(70);
        if (this.mState != 1) {
            throw new IllegalStateException("startRecording() called on an uninitialized AudioRecord.");
        }
        synchronized (this.mRecordingStateLock) {
            if (native_start(0, 0) == 0) {
                handleFullVolumeRec(true);
                this.mRecordingState = 3;
            }
        }
    }

    public void startRecording(MediaSyncEvent syncEvent) throws IllegalStateException {
        SeempLog.record(70);
        if (this.mState != 1) {
            throw new IllegalStateException("startRecording() called on an uninitialized AudioRecord.");
        }
        synchronized (this.mRecordingStateLock) {
            if (native_start(syncEvent.getType(), syncEvent.getAudioSessionId()) == 0) {
                handleFullVolumeRec(true);
                this.mRecordingState = 3;
            }
        }
    }

    public void stop() throws IllegalStateException {
        if (this.mState != 1) {
            throw new IllegalStateException("stop() called on an uninitialized AudioRecord.");
        }
        synchronized (this.mRecordingStateLock) {
            handleFullVolumeRec(false);
            native_stop();
            this.mRecordingState = 1;
        }
    }

    private void handleFullVolumeRec(boolean starting) {
        if (!this.mIsSubmixFullVolume) {
            return;
        }
        IBinder b = ServiceManager.getService("audio");
        IAudioService ias = IAudioService.Stub.asInterface(b);
        try {
            ias.forceRemoteSubmixFullVolume(starting, this.mICallBack);
        } catch (RemoteException e) {
            Log.m69e(TAG, "Error talking to AudioService when handling full submix volume", e);
        }
    }

    public int read(byte[] audioData, int offsetInBytes, int sizeInBytes) {
        return read(audioData, offsetInBytes, sizeInBytes, 0);
    }

    public int read(byte[] audioData, int offsetInBytes, int sizeInBytes, int readMode) {
        if (this.mState != 1 || this.mAudioFormat == 4) {
            return -3;
        }
        if (readMode != 0 && readMode != 1) {
            Log.m70e(TAG, "AudioRecord.read() called with invalid blocking mode");
            return -2;
        } else if (audioData == null || offsetInBytes < 0 || sizeInBytes < 0 || offsetInBytes + sizeInBytes < 0 || offsetInBytes + sizeInBytes > audioData.length) {
            return -2;
        } else {
            return native_read_in_byte_array(audioData, offsetInBytes, sizeInBytes, readMode == 0);
        }
    }

    public int read(short[] audioData, int offsetInShorts, int sizeInShorts) {
        return read(audioData, offsetInShorts, sizeInShorts, 0);
    }

    public int read(short[] audioData, int offsetInShorts, int sizeInShorts, int readMode) {
        if (this.mState != 1 || this.mAudioFormat == 4) {
            return -3;
        }
        if (readMode != 0 && readMode != 1) {
            Log.m70e(TAG, "AudioRecord.read() called with invalid blocking mode");
            return -2;
        } else if (audioData == null || offsetInShorts < 0 || sizeInShorts < 0 || offsetInShorts + sizeInShorts < 0 || offsetInShorts + sizeInShorts > audioData.length) {
            return -2;
        } else {
            return native_read_in_short_array(audioData, offsetInShorts, sizeInShorts, readMode == 0);
        }
    }

    public int read(float[] audioData, int offsetInFloats, int sizeInFloats, int readMode) {
        if (this.mState == 0) {
            Log.m70e(TAG, "AudioRecord.read() called in invalid state STATE_UNINITIALIZED");
            return -3;
        } else if (this.mAudioFormat != 4) {
            Log.m70e(TAG, "AudioRecord.read(float[] ...) requires format ENCODING_PCM_FLOAT");
            return -3;
        } else {
            if (readMode != 0 && readMode != 1) {
                Log.m70e(TAG, "AudioRecord.read() called with invalid blocking mode");
                return -2;
            } else if (audioData == null || offsetInFloats < 0 || sizeInFloats < 0 || offsetInFloats + sizeInFloats < 0 || offsetInFloats + sizeInFloats > audioData.length) {
                return -2;
            } else {
                return native_read_in_float_array(audioData, offsetInFloats, sizeInFloats, readMode == 0);
            }
        }
    }

    public int read(ByteBuffer audioBuffer, int sizeInBytes) {
        return read(audioBuffer, sizeInBytes, 0);
    }

    public int read(ByteBuffer audioBuffer, int sizeInBytes, int readMode) {
        if (this.mState != 1) {
            return -3;
        }
        if (readMode != 0 && readMode != 1) {
            Log.m70e(TAG, "AudioRecord.read() called with invalid blocking mode");
            return -2;
        } else if (audioBuffer == null || sizeInBytes < 0) {
            return -2;
        } else {
            return native_read_in_direct_buffer(audioBuffer, sizeInBytes, readMode == 0);
        }
    }

    public PersistableBundle getMetrics() {
        PersistableBundle bundle = native_getMetrics();
        return bundle;
    }

    public void setRecordPositionUpdateListener(OnRecordPositionUpdateListener listener) {
        setRecordPositionUpdateListener(listener, null);
    }

    public void setRecordPositionUpdateListener(OnRecordPositionUpdateListener listener, Handler handler) {
        synchronized (this.mPositionListenerLock) {
            this.mPositionListener = listener;
            if (listener != null) {
                if (handler != null) {
                    this.mEventHandler = new NativeEventHandler(this, handler.getLooper());
                } else {
                    this.mEventHandler = new NativeEventHandler(this, this.mInitializationLooper);
                }
            } else {
                this.mEventHandler = null;
            }
        }
    }

    public int setNotificationMarkerPosition(int markerInFrames) {
        if (this.mState == 0) {
            return -3;
        }
        return native_set_marker_pos(markerInFrames);
    }

    @Override // android.media.AudioRouting
    public AudioDeviceInfo getRoutedDevice() {
        int deviceId = native_getRoutedDeviceId();
        if (deviceId == 0) {
            return null;
        }
        AudioDeviceInfo[] devices = AudioManager.getDevicesStatic(1);
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
                testDisableNativeRoutingCallbacksLocked();
            }
        }
    }

    @Deprecated
    /* loaded from: classes3.dex */
    public interface OnRoutingChangedListener extends AudioRouting.OnRoutingChangedListener {
        void onRoutingChanged(AudioRecord audioRecord);

        @Override // android.media.AudioRouting.OnRoutingChangedListener
        default void onRoutingChanged(AudioRouting router) {
            if (router instanceof AudioRecord) {
                onRoutingChanged((AudioRecord) router);
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

    public int setPositionNotificationPeriod(int periodInFrames) {
        if (this.mState == 0) {
            return -3;
        }
        return native_set_pos_update_period(periodInFrames);
    }

    @Override // android.media.AudioRouting
    public boolean setPreferredDevice(AudioDeviceInfo deviceInfo) {
        if (deviceInfo != null && !deviceInfo.isSource()) {
            return false;
        }
        int preferredDeviceId = deviceInfo != null ? deviceInfo.getId() : 0;
        boolean status = native_setInputDevice(preferredDeviceId);
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

    public List<MicrophoneInfo> getActiveMicrophones() throws IOException {
        AudioDeviceInfo device;
        ArrayList<MicrophoneInfo> activeMicrophones = new ArrayList<>();
        int status = native_get_active_microphones(activeMicrophones);
        if (status != 0) {
            if (status != -3) {
                Log.m70e(TAG, "getActiveMicrophones failed:" + status);
            }
            Log.m68i(TAG, "getActiveMicrophones failed, fallback on routed device info");
        }
        AudioManager.setPortIdForMicrophones(activeMicrophones);
        if (activeMicrophones.size() == 0 && (device = getRoutedDevice()) != null) {
            MicrophoneInfo microphone = AudioManager.microphoneInfoFromAudioDeviceInfo(device);
            ArrayList<Pair<Integer, Integer>> channelMapping = new ArrayList<>();
            for (int i = 0; i < this.mChannelCount; i++) {
                channelMapping.add(new Pair<>(Integer.valueOf(i), 1));
            }
            microphone.setChannelMapping(channelMapping);
            activeMicrophones.add(microphone);
        }
        return activeMicrophones;
    }

    @Override // android.media.AudioRecordingMonitor
    public void registerAudioRecordingCallback(Executor executor, AudioManager.AudioRecordingCallback cb) {
        this.mRecordingInfoImpl.registerAudioRecordingCallback(executor, cb);
    }

    @Override // android.media.AudioRecordingMonitor
    public void unregisterAudioRecordingCallback(AudioManager.AudioRecordingCallback cb) {
        this.mRecordingInfoImpl.unregisterAudioRecordingCallback(cb);
    }

    @Override // android.media.AudioRecordingMonitor
    public AudioRecordingConfiguration getActiveRecordingConfiguration() {
        return this.mRecordingInfoImpl.getActiveRecordingConfiguration();
    }

    @Override // android.media.AudioRecordingMonitorClient
    public int getPortId() {
        return native_getPortId();
    }

    @Override // android.media.MicrophoneDirection
    public boolean setPreferredMicrophoneDirection(int direction) {
        return native_set_preferred_microphone_direction(direction) == 0;
    }

    @Override // android.media.MicrophoneDirection
    public boolean setPreferredMicrophoneFieldDimension(float zoom) {
        Preconditions.checkArgument(zoom >= -1.0f && zoom <= 1.0f, "Argument must fall between -1 & 1 (inclusive)");
        return native_set_preferred_microphone_field_dimension(zoom) == 0;
    }

    /* loaded from: classes3.dex */
    private class NativeEventHandler extends Handler {
        private final AudioRecord mAudioRecord;

        NativeEventHandler(AudioRecord recorder, Looper looper) {
            super(looper);
            this.mAudioRecord = recorder;
        }

        @Override // android.p007os.Handler
        public void handleMessage(Message msg) {
            OnRecordPositionUpdateListener listener;
            synchronized (AudioRecord.this.mPositionListenerLock) {
                listener = this.mAudioRecord.mPositionListener;
            }
            switch (msg.what) {
                case 2:
                    if (listener != null) {
                        listener.onMarkerReached(this.mAudioRecord);
                        return;
                    }
                    return;
                case 3:
                    if (listener != null) {
                        listener.onPeriodicNotification(this.mAudioRecord);
                        return;
                    }
                    return;
                default:
                    AudioRecord.loge("Unknown native event type: " + msg.what);
                    return;
            }
        }
    }

    @UnsupportedAppUsage
    private static void postEventFromNative(Object audiorecord_ref, int what, int arg1, int arg2, Object obj) {
        AudioRecord recorder = (AudioRecord) ((WeakReference) audiorecord_ref).get();
        if (recorder == null) {
            return;
        }
        if (what == 1000) {
            recorder.broadcastRoutingChange();
        } else if (recorder.mEventHandler != null) {
            Message m = recorder.mEventHandler.obtainMessage(what, arg1, arg2, obj);
            recorder.mEventHandler.sendMessage(m);
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
        public static final String ATTRIBUTES = "android.media.audiorecord.attributes";
        public static final String CHANNELS = "android.media.audiorecord.channels";
        public static final String CHANNEL_MASK = "android.media.audiorecord.channelMask";
        public static final String DURATION_MS = "android.media.audiorecord.durationMs";
        public static final String ENCODING = "android.media.audiorecord.encoding";
        public static final String FRAME_COUNT = "android.media.audiorecord.frameCount";
        @Deprecated
        public static final String LATENCY = "android.media.audiorecord.latency";
        private static final String MM_PREFIX = "android.media.audiorecord.";
        public static final String PORT_ID = "android.media.audiorecord.portId";
        public static final String SAMPLERATE = "android.media.audiorecord.samplerate";
        public static final String SOURCE = "android.media.audiorecord.source";
        public static final String START_COUNT = "android.media.audiorecord.startCount";

        private MetricsConstants() {
        }
    }
}
