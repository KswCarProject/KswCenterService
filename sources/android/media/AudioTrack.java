package android.media;

import android.annotation.UnsupportedAppUsage;
import android.media.AudioAttributes;
import android.media.AudioFormat;
import android.media.AudioRouting;
import android.media.VolumeShaper;
import android.opengl.GLES30;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.PersistableBundle;
import android.util.ArrayMap;
import android.util.Log;
import com.android.internal.annotations.GuardedBy;
import com.wits.pms.PowerManagerApp;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.NioUtils;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.Executor;

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
    /* access modifiers changed from: private */
    public int mAvSyncBytesRemaining;
    /* access modifiers changed from: private */
    public ByteBuffer mAvSyncHeader;
    private int mChannelConfiguration;
    private int mChannelCount;
    private int mChannelIndexMask;
    private int mChannelMask;
    private AudioAttributes mConfiguredAudioAttributes;
    private int mDataLoadMode;
    private NativePositionEventHandlerDelegate mEventHandlerDelegate;
    /* access modifiers changed from: private */
    public final Looper mInitializationLooper;
    @UnsupportedAppUsage
    private long mJniData;
    private int mNativeBufferSizeInBytes;
    private int mNativeBufferSizeInFrames;
    @UnsupportedAppUsage
    protected long mNativeTrackInJavaObj;
    private NaviHelper mNaviHelper;
    private int mOffloadDelayFrames;
    /* access modifiers changed from: private */
    public boolean mOffloadEosPending;
    private int mOffloadPaddingFrames;
    private boolean mOffloaded;
    private int mOffset;
    /* access modifiers changed from: private */
    public int mPlayState;
    /* access modifiers changed from: private */
    public final Object mPlayStateLock;
    private AudioDeviceInfo mPreferredDevice;
    @GuardedBy({"mRoutingChangeListeners"})
    private ArrayMap<AudioRouting.OnRoutingChangedListener, NativeRoutingEventHandlerDelegate> mRoutingChangeListeners;
    private int mSampleRate;
    private int mSessionId;
    private int mState;
    /* access modifiers changed from: private */
    @GuardedBy({"mStreamEventCbLock"})
    public LinkedList<StreamEventCbInfo> mStreamEventCbInfoList;
    /* access modifiers changed from: private */
    public final Object mStreamEventCbLock;
    private volatile StreamEventHandler mStreamEventHandler;
    private HandlerThread mStreamEventHandlerThread;
    @UnsupportedAppUsage
    private int mStreamType;

    public interface OnPlaybackPositionUpdateListener {
        void onMarkerReached(AudioTrack audioTrack);

        void onPeriodicNotification(AudioTrack audioTrack);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface PerformanceMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface TransferMode {
    }

    @Retention(RetentionPolicy.SOURCE)
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

    /* access modifiers changed from: private */
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

    /* JADX WARNING: Illegal instructions before constructor call */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x00c6  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x00e9  */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x0186  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private AudioTrack(android.media.AudioAttributes r28, android.media.AudioFormat r29, int r30, int r31, int r32, boolean r33) throws java.lang.IllegalArgumentException {
        /*
            r27 = this;
            r13 = r27
            r14 = r28
            r15 = r29
            r12 = r30
            r10 = r32
            r11 = 1
            r13.<init>(r14, r11)
            r9 = 0
            r13.mState = r9
            r13.mPlayState = r11
            r13.mOffloadEosPending = r9
            java.lang.Object r0 = new java.lang.Object
            r0.<init>()
            r13.mPlayStateLock = r0
            r13.mNativeBufferSizeInBytes = r9
            r13.mNativeBufferSizeInFrames = r9
            r13.mChannelCount = r11
            r0 = 4
            r13.mChannelMask = r0
            r1 = 3
            r13.mStreamType = r1
            r13.mDataLoadMode = r11
            r13.mChannelConfiguration = r0
            r13.mChannelIndexMask = r9
            r13.mSessionId = r9
            r1 = 0
            r13.mAvSyncHeader = r1
            r13.mAvSyncBytesRemaining = r9
            r13.mOffset = r9
            r13.mOffloaded = r9
            r13.mOffloadDelayFrames = r9
            r13.mOffloadPaddingFrames = r9
            r13.mPreferredDevice = r1
            android.util.ArrayMap r1 = new android.util.ArrayMap
            r1.<init>()
            r13.mRoutingChangeListeners = r1
            java.lang.Object r1 = new java.lang.Object
            r1.<init>()
            r13.mStreamEventCbLock = r1
            java.util.LinkedList r1 = new java.util.LinkedList
            r1.<init>()
            r13.mStreamEventCbInfoList = r1
            r13.mConfiguredAudioAttributes = r14
            if (r15 == 0) goto L_0x01a0
            android.media.NaviHelper r1 = new android.media.NaviHelper
            r1.<init>()
            r13.mNaviHelper = r1
            android.media.AudioAttributes r1 = r13.mAttributes
            r8 = r31
            boolean r1 = shouldEnablePowerSaving(r1, r15, r12, r8)
            if (r1 == 0) goto L_0x0084
            android.media.AudioAttributes$Builder r1 = new android.media.AudioAttributes$Builder
            android.media.AudioAttributes r2 = r13.mAttributes
            r1.<init>(r2)
            android.media.AudioAttributes r2 = r13.mAttributes
            int r2 = r2.getAllFlags()
            r2 = r2 | 512(0x200, float:7.175E-43)
            r2 = r2 & -257(0xfffffffffffffeff, float:NaN)
            android.media.AudioAttributes$Builder r1 = r1.replaceFlags(r2)
            android.media.AudioAttributes r1 = r1.build()
            r13.mAttributes = r1
        L_0x0084:
            android.os.Looper r1 = android.os.Looper.myLooper()
            r2 = r1
            if (r1 != 0) goto L_0x008f
            android.os.Looper r2 = android.os.Looper.getMainLooper()
        L_0x008f:
            r7 = r2
            int r1 = r29.getSampleRate()
            if (r1 != 0) goto L_0x0097
            r1 = 0
        L_0x0097:
            r16 = r1
            r1 = 0
            int r2 = r29.getPropertySetMask()
            r2 = r2 & 8
            if (r2 == 0) goto L_0x00a6
            int r1 = r29.getChannelIndexMask()
        L_0x00a6:
            r17 = r1
            r1 = 0
            int r2 = r29.getPropertySetMask()
            r0 = r0 & r2
            if (r0 == 0) goto L_0x00b7
            int r0 = r29.getChannelMask()
        L_0x00b4:
            r18 = r0
            goto L_0x00be
        L_0x00b7:
            if (r17 != 0) goto L_0x00bc
            r0 = 12
            goto L_0x00b4
        L_0x00bc:
            r18 = r1
        L_0x00be:
            r0 = 1
            int r1 = r29.getPropertySetMask()
            r1 = r1 & r11
            if (r1 == 0) goto L_0x00ca
            int r0 = r29.getEncoding()
        L_0x00ca:
            r19 = r0
            r0 = r27
            r1 = r16
            r2 = r18
            r3 = r17
            r4 = r19
            r5 = r31
            r0.audioParamCheck(r1, r2, r3, r4, r5)
            r6 = r33
            r13.mOffloaded = r6
            r0 = -1
            r13.mStreamType = r0
            r13.audioBuffSizeCheck(r12)
            r13.mInitializationLooper = r7
            if (r10 < 0) goto L_0x0186
            int[] r0 = new int[r11]
            int r1 = r13.mSampleRate
            r0[r9] = r1
            r20 = r0
            int[] r5 = new int[r11]
            r5[r9] = r10
            java.lang.ref.WeakReference r1 = new java.lang.ref.WeakReference
            r1.<init>(r13)
            android.media.AudioAttributes r2 = r13.mAttributes
            int r4 = r13.mChannelMask
            int r3 = r13.mChannelIndexMask
            int r0 = r13.mAudioFormat
            int r9 = r13.mNativeBufferSizeInBytes
            int r11 = r13.mDataLoadMode
            r22 = 0
            r24 = r0
            r0 = r27
            r25 = r3
            r3 = r20
            r26 = r5
            r5 = r25
            r6 = r24
            r24 = r7
            r7 = r9
            r8 = r11
            r21 = 0
            r9 = r26
            r14 = r10
            r15 = 1
            r10 = r22
            r12 = r33
            int r0 = r0.native_setup(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r12)
            if (r0 == 0) goto L_0x0144
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "Error code "
            r1.append(r2)
            r1.append(r0)
            java.lang.String r2 = " when initializing AudioTrack."
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            loge(r1)
            return
        L_0x0144:
            r1 = r20[r21]
            r13.mSampleRate = r1
            r1 = r26[r21]
            r13.mSessionId = r1
            android.media.AudioAttributes r1 = r13.mAttributes
            int r1 = r1.getFlags()
            r1 = r1 & 16
            if (r1 == 0) goto L_0x0178
            int r1 = r13.mAudioFormat
            boolean r1 = android.media.AudioFormat.isEncodingLinearFrames(r1)
            if (r1 == 0) goto L_0x0169
            int r1 = r13.mChannelCount
            int r2 = r13.mAudioFormat
            int r2 = android.media.AudioFormat.getBytesPerSample(r2)
            int r11 = r1 * r2
            goto L_0x016a
        L_0x0169:
            r11 = r15
        L_0x016a:
            r1 = r11
            r2 = 1101004800(0x41a00000, float:20.0)
            float r3 = (float) r1
            float r2 = r2 / r3
            double r2 = (double) r2
            double r2 = java.lang.Math.ceil(r2)
            int r2 = (int) r2
            int r2 = r2 * r1
            r13.mOffset = r2
        L_0x0178:
            int r1 = r13.mDataLoadMode
            if (r1 != 0) goto L_0x0180
            r1 = 2
            r13.mState = r1
            goto L_0x0182
        L_0x0180:
            r13.mState = r15
        L_0x0182:
            r27.baseRegisterPlayer()
            return
        L_0x0186:
            r24 = r7
            r14 = r10
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "Invalid audio session ID: "
            r1.append(r2)
            r1.append(r14)
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            throw r0
        L_0x01a0:
            r14 = r10
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.String r1 = "Illegal null AudioFormat"
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.media.AudioTrack.<init>(android.media.AudioAttributes, android.media.AudioFormat, int, int, int, boolean):void");
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
        this.mNativeTrackInJavaObj = 0;
        this.mJniData = 0;
        Looper looper = Looper.myLooper();
        this.mInitializationLooper = looper == null ? Looper.getMainLooper() : looper;
        if (nativeTrackInJavaObj != 0) {
            baseRegisterPlayer();
            deferred_connect(nativeTrackInJavaObj);
            return;
        }
        this.mState = 0;
    }

    /* access modifiers changed from: package-private */
    @UnsupportedAppUsage
    public void deferred_connect(long nativeTrackInJavaObj) {
        if (this.mState != 1) {
            int[] session = {0};
            int initResult = native_setup(new WeakReference(this), (Object) null, new int[]{0}, 0, 0, 0, 0, 0, session, nativeTrackInJavaObj, false);
            if (initResult != 0) {
                loge("Error code " + initResult + " when initializing AudioTrack.");
                return;
            }
            this.mSessionId = session[0];
            this.mState = 1;
        }
    }

    public static class Builder {
        private AudioAttributes mAttributes;
        private int mBufferSizeInBytes;
        private AudioFormat mFormat;
        private int mMode = 1;
        private boolean mOffload = false;
        private int mPerformanceMode = 0;
        private int mSessionId = 0;

        public Builder setAudioAttributes(AudioAttributes attributes) throws IllegalArgumentException {
            if (attributes != null) {
                this.mAttributes = attributes;
                return this;
            }
            throw new IllegalArgumentException("Illegal null AudioAttributes argument");
        }

        public Builder setAudioFormat(AudioFormat format) throws IllegalArgumentException {
            if (format != null) {
                this.mFormat = format;
                return this;
            }
            throw new IllegalArgumentException("Illegal null AudioFormat argument");
        }

        public Builder setBufferSizeInBytes(int bufferSizeInBytes) throws IllegalArgumentException {
            if (bufferSizeInBytes > 0) {
                this.mBufferSizeInBytes = bufferSizeInBytes;
                return this;
            }
            throw new IllegalArgumentException("Invalid buffer size " + bufferSizeInBytes);
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
            if (sessionId == 0 || sessionId >= 1) {
                this.mSessionId = sessionId;
                return this;
            }
            throw new IllegalArgumentException("Invalid audio session ID " + sessionId);
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

        /* JADX WARNING: Can't fix incorrect switch cases order */
        /* JADX WARNING: Code restructure failed: missing block: B:7:0x0042, code lost:
            if (android.media.AudioTrack.access$000(r9.mAttributes, r9.mFormat, r9.mBufferSizeInBytes, r9.mMode) == false) goto L_0x0060;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public android.media.AudioTrack build() throws java.lang.UnsupportedOperationException {
            /*
                r9 = this;
                android.media.AudioAttributes r0 = r9.mAttributes
                r1 = 1
                if (r0 != 0) goto L_0x0014
                android.media.AudioAttributes$Builder r0 = new android.media.AudioAttributes$Builder
                r0.<init>()
                android.media.AudioAttributes$Builder r0 = r0.setUsage(r1)
                android.media.AudioAttributes r0 = r0.build()
                r9.mAttributes = r0
            L_0x0014:
                int r0 = r9.mPerformanceMode
                switch(r0) {
                    case 0: goto L_0x0036;
                    case 1: goto L_0x001a;
                    case 2: goto L_0x0045;
                    default: goto L_0x0019;
                }
            L_0x0019:
                goto L_0x0060
            L_0x001a:
                android.media.AudioAttributes$Builder r0 = new android.media.AudioAttributes$Builder
                android.media.AudioAttributes r2 = r9.mAttributes
                r0.<init>(r2)
                android.media.AudioAttributes r2 = r9.mAttributes
                int r2 = r2.getAllFlags()
                r2 = r2 | 256(0x100, float:3.59E-43)
                r2 = r2 & -513(0xfffffffffffffdff, float:NaN)
                android.media.AudioAttributes$Builder r0 = r0.replaceFlags(r2)
                android.media.AudioAttributes r0 = r0.build()
                r9.mAttributes = r0
                goto L_0x0060
            L_0x0036:
                android.media.AudioAttributes r0 = r9.mAttributes
                android.media.AudioFormat r2 = r9.mFormat
                int r3 = r9.mBufferSizeInBytes
                int r4 = r9.mMode
                boolean r0 = android.media.AudioTrack.shouldEnablePowerSaving(r0, r2, r3, r4)
                if (r0 != 0) goto L_0x0045
                goto L_0x0060
            L_0x0045:
                android.media.AudioAttributes$Builder r0 = new android.media.AudioAttributes$Builder
                android.media.AudioAttributes r2 = r9.mAttributes
                r0.<init>(r2)
                android.media.AudioAttributes r2 = r9.mAttributes
                int r2 = r2.getAllFlags()
                r2 = r2 | 512(0x200, float:7.175E-43)
                r2 = r2 & -257(0xfffffffffffffeff, float:NaN)
                android.media.AudioAttributes$Builder r0 = r0.replaceFlags(r2)
                android.media.AudioAttributes r0 = r0.build()
                r9.mAttributes = r0
            L_0x0060:
                android.media.AudioFormat r0 = r9.mFormat
                if (r0 != 0) goto L_0x0079
                android.media.AudioFormat$Builder r0 = new android.media.AudioFormat$Builder
                r0.<init>()
                r2 = 12
                android.media.AudioFormat$Builder r0 = r0.setChannelMask(r2)
                android.media.AudioFormat$Builder r0 = r0.setEncoding(r1)
                android.media.AudioFormat r0 = r0.build()
                r9.mFormat = r0
            L_0x0079:
                boolean r0 = r9.mOffload
                if (r0 == 0) goto L_0x009c
                int r0 = r9.mPerformanceMode
                if (r0 == r1) goto L_0x0094
                android.media.AudioFormat r0 = r9.mFormat
                android.media.AudioAttributes r2 = r9.mAttributes
                boolean r0 = android.media.AudioSystem.isOffloadSupported(r0, r2)
                if (r0 == 0) goto L_0x008c
                goto L_0x009c
            L_0x008c:
                java.lang.UnsupportedOperationException r0 = new java.lang.UnsupportedOperationException
                java.lang.String r1 = "Cannot create AudioTrack, offload format / attributes not supported"
                r0.<init>(r1)
                throw r0
            L_0x0094:
                java.lang.UnsupportedOperationException r0 = new java.lang.UnsupportedOperationException
                java.lang.String r1 = "Offload and low latency modes are incompatible"
                r0.<init>(r1)
                throw r0
            L_0x009c:
                int r0 = r9.mMode     // Catch:{ IllegalArgumentException -> 0x00db }
                if (r0 != r1) goto L_0x00b9
                int r0 = r9.mBufferSizeInBytes     // Catch:{ IllegalArgumentException -> 0x00db }
                if (r0 != 0) goto L_0x00b9
                android.media.AudioFormat r0 = r9.mFormat     // Catch:{ IllegalArgumentException -> 0x00db }
                int r0 = r0.getChannelCount()     // Catch:{ IllegalArgumentException -> 0x00db }
                android.media.AudioFormat r1 = r9.mFormat     // Catch:{ IllegalArgumentException -> 0x00db }
                android.media.AudioFormat r1 = r9.mFormat     // Catch:{ IllegalArgumentException -> 0x00db }
                int r1 = r1.getEncoding()     // Catch:{ IllegalArgumentException -> 0x00db }
                int r1 = android.media.AudioFormat.getBytesPerSample(r1)     // Catch:{ IllegalArgumentException -> 0x00db }
                int r0 = r0 * r1
                r9.mBufferSizeInBytes = r0     // Catch:{ IllegalArgumentException -> 0x00db }
            L_0x00b9:
                android.media.AudioTrack r0 = new android.media.AudioTrack     // Catch:{ IllegalArgumentException -> 0x00db }
                android.media.AudioAttributes r2 = r9.mAttributes     // Catch:{ IllegalArgumentException -> 0x00db }
                android.media.AudioFormat r3 = r9.mFormat     // Catch:{ IllegalArgumentException -> 0x00db }
                int r4 = r9.mBufferSizeInBytes     // Catch:{ IllegalArgumentException -> 0x00db }
                int r5 = r9.mMode     // Catch:{ IllegalArgumentException -> 0x00db }
                int r6 = r9.mSessionId     // Catch:{ IllegalArgumentException -> 0x00db }
                boolean r7 = r9.mOffload     // Catch:{ IllegalArgumentException -> 0x00db }
                r8 = 0
                r1 = r0
                r1.<init>((android.media.AudioAttributes) r2, (android.media.AudioFormat) r3, (int) r4, (int) r5, (int) r6, (boolean) r7)     // Catch:{ IllegalArgumentException -> 0x00db }
                int r1 = r0.getState()     // Catch:{ IllegalArgumentException -> 0x00db }
                if (r1 == 0) goto L_0x00d3
                return r0
            L_0x00d3:
                java.lang.UnsupportedOperationException r1 = new java.lang.UnsupportedOperationException     // Catch:{ IllegalArgumentException -> 0x00db }
                java.lang.String r2 = "Cannot create AudioTrack"
                r1.<init>(r2)     // Catch:{ IllegalArgumentException -> 0x00db }
                throw r1     // Catch:{ IllegalArgumentException -> 0x00db }
            L_0x00db:
                r0 = move-exception
                java.lang.UnsupportedOperationException r1 = new java.lang.UnsupportedOperationException
                java.lang.String r2 = r0.getMessage()
                r1.<init>(r2)
                throw r1
            */
            throw new UnsupportedOperationException("Method not decompiled: android.media.AudioTrack.Builder.build():android.media.AudioTrack");
        }
    }

    public void setOffloadDelayPadding(int delayInFrames, int paddingInFrames) {
        if (paddingInFrames < 0) {
            throw new IllegalArgumentException("Illegal negative padding");
        } else if (delayInFrames < 0) {
            throw new IllegalArgumentException("Illegal negative delay");
        } else if (!this.mOffloaded) {
            throw new IllegalStateException("Illegal use of delay/padding on non-offloaded track");
        } else if (this.mState != 0) {
            this.mOffloadDelayFrames = delayInFrames;
            this.mOffloadPaddingFrames = paddingInFrames;
            native_set_delay_padding(delayInFrames, paddingInFrames);
        } else {
            throw new IllegalStateException("Uninitialized track");
        }
    }

    public int getOffloadDelay() {
        if (!this.mOffloaded) {
            throw new IllegalStateException("Illegal query of delay on non-offloaded track");
        } else if (this.mState != 0) {
            return this.mOffloadDelayFrames;
        } else {
            throw new IllegalStateException("Illegal query of delay on uninitialized track");
        }
    }

    public int getOffloadPadding() {
        if (!this.mOffloaded) {
            throw new IllegalStateException("Illegal query of padding on non-offloaded track");
        } else if (this.mState != 0) {
            return this.mOffloadPaddingFrames;
        } else {
            throw new IllegalStateException("Illegal query of padding on uninitialized track");
        }
    }

    public void setOffloadEndOfStream() {
        if (!this.mOffloaded) {
            throw new IllegalStateException("EOS not supported on non-offloaded track");
        } else if (this.mState == 0) {
            throw new IllegalStateException("Uninitialized track");
        } else if (this.mPlayState == 3) {
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
        } else {
            throw new IllegalStateException("EOS not supported if not playing");
        }
    }

    public boolean isOffloadedPlayback() {
        return this.mOffloaded;
    }

    public static boolean isDirectPlaybackSupported(AudioFormat format, AudioAttributes attributes) {
        if (format == null) {
            throw new IllegalArgumentException("Illegal null AudioFormat argument");
        } else if (attributes != null) {
            return native_is_direct_output_supported(format.getEncoding(), format.getSampleRate(), format.getChannelMask(), format.getChannelIndexMask(), attributes.getContentType(), attributes.getUsage(), attributes.getFlags());
        } else {
            throw new IllegalArgumentException("Illegal null AudioAttributes argument");
        }
    }

    /* access modifiers changed from: private */
    public static boolean shouldEnablePowerSaving(AudioAttributes attributes, AudioFormat format, int bufferSizeInBytes, int mode) {
        if ((attributes != null && (attributes.getAllFlags() != 0 || attributes.getUsage() != 1 || (attributes.getContentType() != 0 && attributes.getContentType() != 2 && attributes.getContentType() != 3))) || format == null || format.getSampleRate() == 0 || !AudioFormat.isEncodingLinearPcm(format.getEncoding()) || !AudioFormat.isValidEncoding(format.getEncoding()) || format.getChannelCount() < 1 || mode != 1) {
            return false;
        }
        if (bufferSizeInBytes != 0) {
            if (((long) bufferSizeInBytes) < (((((long) format.getChannelCount()) * 100) * ((long) AudioFormat.getBytesPerSample(format.getEncoding()))) * ((long) format.getSampleRate())) / 1000) {
                return false;
            }
        }
        return true;
    }

    private void audioParamCheck(int sampleRateInHz, int channelConfig, int channelIndexMask, int audioFormat, int mode) {
        if ((sampleRateInHz < 4000 || sampleRateInHz > 192000) && sampleRateInHz != 0) {
            throw new IllegalArgumentException(sampleRateInHz + "Hz is not a supported sample rate.");
        }
        this.mSampleRate = sampleRateInHz;
        if (audioFormat != 13 || channelConfig == 12) {
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
                        } else if (isMultichannelConfigSupported(channelConfig)) {
                            this.mChannelMask = channelConfig;
                            this.mChannelCount = AudioFormat.channelCountFromOutChannelMask(channelConfig);
                            break;
                        } else {
                            throw new IllegalArgumentException("Unsupported channel configuration.");
                        }
                }
            }
            this.mChannelCount = 2;
            this.mChannelMask = 12;
            this.mChannelIndexMask = channelIndexMask;
            if (this.mChannelIndexMask != 0) {
                if (((~((1 << AudioSystem.OUT_CHANNEL_COUNT_MAX) - 1)) & channelIndexMask) == 0) {
                    int channelIndexCount = Integer.bitCount(channelIndexMask);
                    if (this.mChannelCount == 0) {
                        this.mChannelCount = channelIndexCount;
                    } else if (this.mChannelCount != channelIndexCount) {
                        throw new IllegalArgumentException("Channel count must match");
                    }
                } else {
                    throw new IllegalArgumentException("Unsupported channel index configuration " + channelIndexMask);
                }
            }
            if (audioFormat == 1) {
                audioFormat = 2;
            }
            if (AudioFormat.isPublicEncoding(audioFormat)) {
                this.mAudioFormat = audioFormat;
                if ((mode == 1 || mode == 0) && (mode == 1 || AudioFormat.isEncodingLinearPcm(this.mAudioFormat))) {
                    this.mDataLoadMode = mode;
                    return;
                }
                throw new IllegalArgumentException("Invalid mode.");
            }
            throw new IllegalArgumentException("Unsupported audio encoding.");
        }
        throw new IllegalArgumentException("ENCODING_IEC61937 must be configured as CHANNEL_OUT_STEREO");
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
        } else if ((channelConfig & GLES30.GL_COLOR) == 0 || (channelConfig & GLES30.GL_COLOR) == 6144) {
            return true;
        } else {
            loge("Side channels can't be used independently");
            return false;
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

    /* access modifiers changed from: protected */
    public void finalize() {
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
        if (this.mState != 0 && this.mConfiguredAudioAttributes != null) {
            return this.mConfiguredAudioAttributes;
        }
        throw new IllegalStateException("track not initialized");
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
                    int i = this.mPlayState;
                    return i;
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

    /* access modifiers changed from: protected */
    @Deprecated
    public int getNativeFrameCount() {
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
                    if (isMultichannelConfigSupported(channelConfig)) {
                        channelCount = AudioFormat.channelCountFromOutChannelMask(channelConfig);
                        break;
                    } else {
                        loge("getMinBufferSize(): Invalid channel configuration.");
                        return -2;
                    }
            }
        }
        channelCount = 2;
        if (!AudioFormat.isPublicEncoding(audioFormat)) {
            loge("getMinBufferSize(): Invalid audio format.");
            return -2;
        } else if (sampleRateInHz < 4000 || sampleRateInHz > 192000) {
            loge("getMinBufferSize(): " + sampleRateInHz + " Hz is not a supported sample rate.");
            return -2;
        } else {
            int size = native_get_min_buff_size(sampleRateInHz, channelCount, audioFormat);
            if (size > 0) {
                return size;
            }
            loge("getMinBufferSize(): error querying hardware");
            return -1;
        }
    }

    public int getAudioSessionId() {
        return this.mSessionId;
    }

    public boolean getTimestamp(AudioTimestamp timestamp) {
        if (timestamp != null) {
            long[] longArray = new long[2];
            if (native_get_timestamp(longArray) != 0) {
                return false;
            }
            timestamp.framePosition = longArray[0];
            timestamp.nanoTime = longArray[1];
            return true;
        }
        throw new IllegalArgumentException();
    }

    public int getTimestampWithStatus(AudioTimestamp timestamp) {
        if (timestamp != null) {
            long[] longArray = new long[2];
            int ret = native_get_timestamp(longArray);
            timestamp.framePosition = longArray[0];
            timestamp.nanoTime = longArray[1];
            return ret;
        }
        throw new IllegalArgumentException();
    }

    public PersistableBundle getMetrics() {
        return native_getMetrics();
    }

    public void setPlaybackPositionUpdateListener(OnPlaybackPositionUpdateListener listener) {
        setPlaybackPositionUpdateListener(listener, (Handler) null);
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
        } else if (gainOrLevel < 0.0f) {
            return 0.0f;
        } else {
            if (gainOrLevel > 1.0f) {
                return 1.0f;
            }
            return gainOrLevel;
        }
    }

    @Deprecated
    public int setStereoVolume(float leftGain, float rightGain) {
        if (this.mState == 0) {
            return -3;
        }
        baseSetVolume(leftGain, rightGain);
        return 0;
    }

    /* access modifiers changed from: package-private */
    public void playerSetVolume(boolean muting, float leftVolume, float rightVolume) {
        float f = 0.0f;
        float leftVolume2 = clampGainOrLevel(muting ? 0.0f : leftVolume);
        if (!muting) {
            f = rightVolume;
        }
        native_setVolume(leftVolume2, clampGainOrLevel(f));
    }

    public int setVolume(float gain) {
        return setStereoVolume(gain, gain);
    }

    /* access modifiers changed from: package-private */
    public int playerApplyVolumeShaper(VolumeShaper.Configuration configuration, VolumeShaper.Operation operation) {
        return native_applyVolumeShaper(configuration, operation);
    }

    /* access modifiers changed from: package-private */
    public VolumeShaper.State playerGetVolumeShaperState(int id) {
        return native_getVolumeShaperState(id);
    }

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
        if (params != null) {
            native_set_playback_params(params);
            return;
        }
        throw new IllegalArgumentException("params is null");
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
        if (presentation != null) {
            return native_setPresentation(presentation.getPresentationId(), presentation.getProgramId());
        }
        throw new IllegalArgumentException("audio presentation is null");
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public void setState(int state) {
        this.mState = state;
    }

    public void play() throws IllegalStateException {
        if (this.mState == 1) {
            final int delay = getStartDelayMs();
            if (delay == 0) {
                startImpl();
            } else {
                new Thread() {
                    public void run() {
                        try {
                            Thread.sleep((long) delay);
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
            return;
        }
        throw new IllegalStateException("play() called on uninitialized AudioTrack.");
    }

    /* access modifiers changed from: private */
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

    public void stop() throws IllegalStateException {
        if (this.mState == 1) {
            try {
                StackTraceElement[] element = Thread.currentThread().getStackTrace();
                int i = 0;
                while (true) {
                    if (i >= element.length) {
                        break;
                    } else if (element[i].toString().contains("navngo.igo")) {
                        PowerManagerApp.sendCommand("{\"command\":1,\"jsonArg\":\"false\",\"subCommand\":608}");
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
                if (!this.mOffloaded || this.mPlayState == 5) {
                    this.mPlayState = 1;
                    this.mOffloadEosPending = false;
                    this.mAvSyncHeader = null;
                    this.mAvSyncBytesRemaining = 0;
                    this.mPlayStateLock.notify();
                } else {
                    this.mPlayState = 4;
                }
            }
            return;
        }
        throw new IllegalStateException("stop() called on uninitialized AudioTrack.");
    }

    public void pause() throws IllegalStateException {
        if (this.mState == 1) {
            synchronized (this.mPlayStateLock) {
                native_pause();
                basePause();
                if (this.mPlayState == 4) {
                    this.mPlayState = 5;
                } else {
                    this.mPlayState = 2;
                }
            }
            return;
        }
        throw new IllegalStateException("pause() called on uninitialized AudioTrack.");
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
            Log.e(TAG, "AudioTrack.write() called with invalid blocking mode");
            return -2;
        } else if (audioData == null || offsetInBytes < 0 || sizeInBytes < 0 || offsetInBytes + sizeInBytes < 0 || offsetInBytes + sizeInBytes > audioData.length) {
            return -2;
        } else {
            if (!blockUntilOffloadDrain(writeMode)) {
                return 0;
            }
            int ret = native_write_byte(audioData, offsetInBytes, sizeInBytes, this.mAudioFormat, writeMode == 0);
            if (this.mDataLoadMode == 0 && this.mState == 2 && ret > 0) {
                this.mState = 1;
            }
            return ret;
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
            Log.e(TAG, "AudioTrack.write() called with invalid blocking mode");
            return -2;
        } else if (audioData == null || offsetInShorts < 0 || sizeInShorts < 0 || offsetInShorts + sizeInShorts < 0 || offsetInShorts + sizeInShorts > audioData.length) {
            return -2;
        } else {
            if (!blockUntilOffloadDrain(writeMode)) {
                return 0;
            }
            int ret = native_write_short(audioData, offsetInShorts, sizeInShorts, this.mAudioFormat, writeMode == 0);
            if (this.mDataLoadMode == 0 && this.mState == 2 && ret > 0) {
                this.mState = 1;
            }
            return ret;
        }
    }

    public int write(float[] audioData, int offsetInFloats, int sizeInFloats, int writeMode) {
        if (this.mState == 0) {
            Log.e(TAG, "AudioTrack.write() called in invalid state STATE_UNINITIALIZED");
            return -3;
        } else if (this.mAudioFormat != 4) {
            Log.e(TAG, "AudioTrack.write(float[] ...) requires format ENCODING_PCM_FLOAT");
            return -3;
        } else if (writeMode != 0 && writeMode != 1) {
            Log.e(TAG, "AudioTrack.write() called with invalid blocking mode");
            return -2;
        } else if (audioData == null || offsetInFloats < 0 || sizeInFloats < 0 || offsetInFloats + sizeInFloats < 0 || offsetInFloats + sizeInFloats > audioData.length) {
            Log.e(TAG, "AudioTrack.write() called with invalid array, offset, or size");
            return -2;
        } else if (!blockUntilOffloadDrain(writeMode)) {
            return 0;
        } else {
            int ret = native_write_float(audioData, offsetInFloats, sizeInFloats, this.mAudioFormat, writeMode == 0);
            if (this.mDataLoadMode == 0 && this.mState == 2 && ret > 0) {
                this.mState = 1;
            }
            return ret;
        }
    }

    public int write(ByteBuffer audioData, int sizeInBytes, int writeMode) {
        int ret;
        if (this.mState == 0) {
            Log.e(TAG, "AudioTrack.write() called in invalid state STATE_UNINITIALIZED");
            return -3;
        } else if (writeMode != 0 && writeMode != 1) {
            Log.e(TAG, "AudioTrack.write() called with invalid blocking mode");
            return -2;
        } else if (audioData == null || sizeInBytes < 0 || sizeInBytes > audioData.remaining()) {
            Log.e(TAG, "AudioTrack.write() called with invalid size (" + sizeInBytes + ") value");
            return -2;
        } else if (!blockUntilOffloadDrain(writeMode)) {
            return 0;
        } else {
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
        }
    }

    public int write(ByteBuffer audioData, int sizeInBytes, int writeMode, long timestamp) {
        if (this.mState == 0) {
            Log.e(TAG, "AudioTrack.write() called in invalid state STATE_UNINITIALIZED");
            return -3;
        } else if (writeMode != 0 && writeMode != 1) {
            Log.e(TAG, "AudioTrack.write() called with invalid blocking mode");
            return -2;
        } else if (this.mDataLoadMode != 1) {
            Log.e(TAG, "AudioTrack.write() with timestamp called for non-streaming mode track");
            return -3;
        } else if ((this.mAttributes.getFlags() & 16) == 0) {
            Log.d(TAG, "AudioTrack.write() called on a regular AudioTrack. Ignoring pts...");
            return write(audioData, sizeInBytes, writeMode);
        } else if (audioData == null || sizeInBytes < 0 || sizeInBytes > audioData.remaining()) {
            Log.e(TAG, "AudioTrack.write() called with invalid size (" + sizeInBytes + ") value");
            return -2;
        } else if (!blockUntilOffloadDrain(writeMode)) {
            return 0;
        } else {
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
                    Log.e(TAG, "AudioTrack.write() could not write timestamp header!");
                    this.mAvSyncHeader = null;
                    this.mAvSyncBytesRemaining = 0;
                    return ret;
                } else if (this.mAvSyncHeader.remaining() > 0) {
                    Log.v(TAG, "AudioTrack.write() partial timestamp header written.");
                    return 0;
                }
            }
            int ret2 = write(audioData, Math.min(this.mAvSyncBytesRemaining, sizeInBytes), writeMode);
            if (ret2 < 0) {
                Log.e(TAG, "AudioTrack.write() could not write audio data!");
                this.mAvSyncHeader = null;
                this.mAvSyncBytesRemaining = 0;
                return ret2;
            }
            this.mAvSyncBytesRemaining -= ret2;
            return ret2;
        }
    }

    public int reloadStaticData() {
        if (this.mDataLoadMode == 1 || this.mState != 1) {
            return -3;
        }
        return native_reload_static();
    }

    private boolean blockUntilOffloadDrain(int writeMode) {
        synchronized (this.mPlayStateLock) {
            while (true) {
                if (this.mPlayState != 4) {
                    if (this.mPlayState != 5) {
                        return true;
                    }
                }
                if (writeMode == 1) {
                    return false;
                }
                try {
                    this.mPlayStateLock.wait();
                } catch (InterruptedException e) {
                }
            }
            while (true) {
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

    /* access modifiers changed from: package-private */
    public int playerSetAuxEffectSendLevel(boolean muting, float level) {
        return native_setAuxEffectSendLevel(clampGainOrLevel(muting ? 0.0f : level)) == 0 ? 0 : -1;
    }

    public boolean setPreferredDevice(AudioDeviceInfo deviceInfo) {
        int preferredDeviceId = 0;
        if (deviceInfo != null && !deviceInfo.isSink()) {
            return false;
        }
        if (deviceInfo != null) {
            preferredDeviceId = deviceInfo.getId();
        }
        boolean status = native_setOutputDevice(preferredDeviceId);
        if (status) {
            synchronized (this) {
                this.mPreferredDevice = deviceInfo;
            }
        }
        return status;
    }

    public AudioDeviceInfo getPreferredDevice() {
        AudioDeviceInfo audioDeviceInfo;
        synchronized (this) {
            audioDeviceInfo = this.mPreferredDevice;
        }
        return audioDeviceInfo;
    }

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

    public void removeOnRoutingChangedListener(AudioRouting.OnRoutingChangedListener listener) {
        synchronized (this.mRoutingChangeListeners) {
            if (this.mRoutingChangeListeners.containsKey(listener)) {
                this.mRoutingChangeListeners.remove(listener);
            }
            testDisableNativeRoutingCallbacksLocked();
        }
    }

    @Deprecated
    public interface OnRoutingChangedListener extends AudioRouting.OnRoutingChangedListener {
        void onRoutingChanged(AudioTrack audioTrack);

        void onRoutingChanged(AudioRouting router) {
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
        } else if (!this.mOffloaded) {
            throw new IllegalStateException("Cannot register StreamEventCallback on non-offloaded AudioTrack");
        } else if (executor != null) {
            synchronized (this.mStreamEventCbLock) {
                Iterator it = this.mStreamEventCbInfoList.iterator();
                while (it.hasNext()) {
                    if (((StreamEventCbInfo) it.next()).mStreamEventCb == eventCallback) {
                        throw new IllegalArgumentException("StreamEventCallback already registered");
                    }
                }
                beginStreamEventHandling();
                this.mStreamEventCbInfoList.add(new StreamEventCbInfo(executor, eventCallback));
            }
        } else {
            throw new IllegalArgumentException("Illegal null Executor for the StreamEventCallback");
        }
    }

    public void unregisterStreamEventCallback(StreamEventCallback eventCallback) {
        if (eventCallback == null) {
            throw new IllegalArgumentException("Illegal null StreamEventCallback");
        } else if (this.mOffloaded) {
            synchronized (this.mStreamEventCbLock) {
                Iterator it = this.mStreamEventCbInfoList.iterator();
                while (it.hasNext()) {
                    StreamEventCbInfo seci = (StreamEventCbInfo) it.next();
                    if (seci.mStreamEventCb == eventCallback) {
                        this.mStreamEventCbInfoList.remove(seci);
                        if (this.mStreamEventCbInfoList.size() == 0) {
                            endStreamEventHandling();
                        }
                    }
                }
                throw new IllegalArgumentException("StreamEventCallback was not registered");
            }
        } else {
            throw new IllegalStateException("No StreamEventCallback on non-offloaded AudioTrack");
        }
    }

    private static class StreamEventCbInfo {
        final StreamEventCallback mStreamEventCb;
        final Executor mStreamEventExec;

        StreamEventCbInfo(Executor e, StreamEventCallback cb) {
            this.mStreamEventExec = e;
            this.mStreamEventCb = cb;
        }
    }

    /* access modifiers changed from: package-private */
    public void handleStreamEventFromNative(int what, int arg) {
        if (this.mStreamEventHandler != null) {
            switch (what) {
                case 6:
                    this.mStreamEventHandler.sendMessage(this.mStreamEventHandler.obtainMessage(6));
                    return;
                case 7:
                    this.mStreamEventHandler.sendMessage(this.mStreamEventHandler.obtainMessage(7));
                    return;
                case 9:
                    this.mStreamEventHandler.removeMessages(9);
                    this.mStreamEventHandler.sendMessage(this.mStreamEventHandler.obtainMessage(9, arg, 0));
                    return;
                default:
                    return;
            }
        }
    }

    private class StreamEventHandler extends Handler {
        StreamEventHandler(Looper looper) {
            super(looper);
        }

        /* JADX WARNING: Code restructure failed: missing block: B:27:0x006f, code lost:
            r2 = android.os.Binder.clearCallingIdentity();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:29:?, code lost:
            r0 = r1.iterator();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:31:0x007b, code lost:
            if (r0.hasNext() == false) goto L_0x00ab;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:32:0x007d, code lost:
            r4 = (android.media.AudioTrack.StreamEventCbInfo) r0.next();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:33:0x0085, code lost:
            switch(r8.what) {
                case 6: goto L_0x009f;
                case 7: goto L_0x0094;
                case 8: goto L_0x0088;
                case 9: goto L_0x0089;
                default: goto L_0x0088;
            };
         */
        /* JADX WARNING: Code restructure failed: missing block: B:35:0x0089, code lost:
            r4.mStreamEventExec.execute(new android.media.$$Lambda$AudioTrack$StreamEventHandler$IUDediua4qA5AgKwU3zNCXA7jQo(r7, r4, r8));
         */
        /* JADX WARNING: Code restructure failed: missing block: B:36:0x0094, code lost:
            r4.mStreamEventExec.execute(new android.media.$$Lambda$AudioTrack$StreamEventHandler$3NLz6Sbq0z_YUytzGW6tVjPCao(r7, r4));
         */
        /* JADX WARNING: Code restructure failed: missing block: B:37:0x009f, code lost:
            r4.mStreamEventExec.execute(new android.media.$$Lambda$AudioTrack$StreamEventHandler$uWnWUbk1g3MhAY3NoSFc6o37wsk(r7, r4));
         */
        /* JADX WARNING: Code restructure failed: missing block: B:39:0x00af, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:40:0x00b0, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:41:0x00b1, code lost:
            android.os.Binder.restoreCallingIdentity(r2);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:42:0x00b4, code lost:
            throw r0;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void handleMessage(android.os.Message r8) {
            /*
                r7 = this;
                android.media.AudioTrack r0 = android.media.AudioTrack.this
                java.lang.Object r0 = r0.mStreamEventCbLock
                monitor-enter(r0)
                int r1 = r8.what     // Catch:{ all -> 0x00b5 }
                r2 = 7
                if (r1 != r2) goto L_0x0055
                android.media.AudioTrack r1 = android.media.AudioTrack.this     // Catch:{ all -> 0x00b5 }
                java.lang.Object r1 = r1.mPlayStateLock     // Catch:{ all -> 0x00b5 }
                monitor-enter(r1)     // Catch:{ all -> 0x00b5 }
                android.media.AudioTrack r2 = android.media.AudioTrack.this     // Catch:{ all -> 0x0052 }
                int r2 = r2.mPlayState     // Catch:{ all -> 0x0052 }
                r3 = 4
                if (r2 != r3) goto L_0x0050
                android.media.AudioTrack r2 = android.media.AudioTrack.this     // Catch:{ all -> 0x0052 }
                boolean r2 = r2.mOffloadEosPending     // Catch:{ all -> 0x0052 }
                r3 = 0
                if (r2 == 0) goto L_0x0031
                android.media.AudioTrack r2 = android.media.AudioTrack.this     // Catch:{ all -> 0x0052 }
                r2.native_start()     // Catch:{ all -> 0x0052 }
                android.media.AudioTrack r2 = android.media.AudioTrack.this     // Catch:{ all -> 0x0052 }
                r4 = 3
                int unused = r2.mPlayState = r4     // Catch:{ all -> 0x0052 }
                goto L_0x0042
            L_0x0031:
                android.media.AudioTrack r2 = android.media.AudioTrack.this     // Catch:{ all -> 0x0052 }
                r4 = 0
                java.nio.ByteBuffer unused = r2.mAvSyncHeader = r4     // Catch:{ all -> 0x0052 }
                android.media.AudioTrack r2 = android.media.AudioTrack.this     // Catch:{ all -> 0x0052 }
                int unused = r2.mAvSyncBytesRemaining = r3     // Catch:{ all -> 0x0052 }
                android.media.AudioTrack r2 = android.media.AudioTrack.this     // Catch:{ all -> 0x0052 }
                r4 = 1
                int unused = r2.mPlayState = r4     // Catch:{ all -> 0x0052 }
            L_0x0042:
                android.media.AudioTrack r2 = android.media.AudioTrack.this     // Catch:{ all -> 0x0052 }
                boolean unused = r2.mOffloadEosPending = r3     // Catch:{ all -> 0x0052 }
                android.media.AudioTrack r2 = android.media.AudioTrack.this     // Catch:{ all -> 0x0052 }
                java.lang.Object r2 = r2.mPlayStateLock     // Catch:{ all -> 0x0052 }
                r2.notify()     // Catch:{ all -> 0x0052 }
            L_0x0050:
                monitor-exit(r1)     // Catch:{ all -> 0x0052 }
                goto L_0x0055
            L_0x0052:
                r2 = move-exception
                monitor-exit(r1)     // Catch:{ all -> 0x0052 }
                throw r2     // Catch:{ all -> 0x00b5 }
            L_0x0055:
                android.media.AudioTrack r1 = android.media.AudioTrack.this     // Catch:{ all -> 0x00b5 }
                java.util.LinkedList r1 = r1.mStreamEventCbInfoList     // Catch:{ all -> 0x00b5 }
                int r1 = r1.size()     // Catch:{ all -> 0x00b5 }
                if (r1 != 0) goto L_0x0063
                monitor-exit(r0)     // Catch:{ all -> 0x00b5 }
                return
            L_0x0063:
                java.util.LinkedList r1 = new java.util.LinkedList     // Catch:{ all -> 0x00b5 }
                android.media.AudioTrack r2 = android.media.AudioTrack.this     // Catch:{ all -> 0x00b5 }
                java.util.LinkedList r2 = r2.mStreamEventCbInfoList     // Catch:{ all -> 0x00b5 }
                r1.<init>(r2)     // Catch:{ all -> 0x00b5 }
                monitor-exit(r0)     // Catch:{ all -> 0x00b5 }
                long r2 = android.os.Binder.clearCallingIdentity()
                java.util.Iterator r0 = r1.iterator()     // Catch:{ all -> 0x00b0 }
            L_0x0077:
                boolean r4 = r0.hasNext()     // Catch:{ all -> 0x00b0 }
                if (r4 == 0) goto L_0x00ab
                java.lang.Object r4 = r0.next()     // Catch:{ all -> 0x00b0 }
                android.media.AudioTrack$StreamEventCbInfo r4 = (android.media.AudioTrack.StreamEventCbInfo) r4     // Catch:{ all -> 0x00b0 }
                int r5 = r8.what     // Catch:{ all -> 0x00b0 }
                switch(r5) {
                    case 6: goto L_0x009f;
                    case 7: goto L_0x0094;
                    case 8: goto L_0x0088;
                    case 9: goto L_0x0089;
                    default: goto L_0x0088;
                }     // Catch:{ all -> 0x00b0 }
            L_0x0088:
                goto L_0x00aa
            L_0x0089:
                java.util.concurrent.Executor r5 = r4.mStreamEventExec     // Catch:{ all -> 0x00b0 }
                android.media.-$$Lambda$AudioTrack$StreamEventHandler$IUDediua4qA5AgKwU3zNCXA7jQo r6 = new android.media.-$$Lambda$AudioTrack$StreamEventHandler$IUDediua4qA5AgKwU3zNCXA7jQo     // Catch:{ all -> 0x00b0 }
                r6.<init>(r4, r8)     // Catch:{ all -> 0x00b0 }
                r5.execute(r6)     // Catch:{ all -> 0x00b0 }
                goto L_0x00aa
            L_0x0094:
                java.util.concurrent.Executor r5 = r4.mStreamEventExec     // Catch:{ all -> 0x00b0 }
                android.media.-$$Lambda$AudioTrack$StreamEventHandler$-3NLz6Sbq0z_YUytzGW6tVjPCao r6 = new android.media.-$$Lambda$AudioTrack$StreamEventHandler$-3NLz6Sbq0z_YUytzGW6tVjPCao     // Catch:{ all -> 0x00b0 }
                r6.<init>(r4)     // Catch:{ all -> 0x00b0 }
                r5.execute(r6)     // Catch:{ all -> 0x00b0 }
                goto L_0x00aa
            L_0x009f:
                java.util.concurrent.Executor r5 = r4.mStreamEventExec     // Catch:{ all -> 0x00b0 }
                android.media.-$$Lambda$AudioTrack$StreamEventHandler$uWnWUbk1g3MhAY3NoSFc6o37wsk r6 = new android.media.-$$Lambda$AudioTrack$StreamEventHandler$uWnWUbk1g3MhAY3NoSFc6o37wsk     // Catch:{ all -> 0x00b0 }
                r6.<init>(r4)     // Catch:{ all -> 0x00b0 }
                r5.execute(r6)     // Catch:{ all -> 0x00b0 }
            L_0x00aa:
                goto L_0x0077
            L_0x00ab:
                android.os.Binder.restoreCallingIdentity(r2)
                return
            L_0x00b0:
                r0 = move-exception
                android.os.Binder.restoreCallingIdentity(r2)
                throw r0
            L_0x00b5:
                r1 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x00b5 }
                throw r1
            */
            throw new UnsupportedOperationException("Method not decompiled: android.media.AudioTrack.StreamEventHandler.handleMessage(android.os.Message):void");
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

    private class NativePositionEventHandlerDelegate {
        private final Handler mHandler;

        NativePositionEventHandlerDelegate(AudioTrack track, OnPlaybackPositionUpdateListener listener, Handler handler) {
            Looper looper;
            if (handler != null) {
                looper = handler.getLooper();
            } else {
                looper = AudioTrack.this.mInitializationLooper;
            }
            if (looper != null) {
                final AudioTrack audioTrack = AudioTrack.this;
                final AudioTrack audioTrack2 = track;
                final OnPlaybackPositionUpdateListener onPlaybackPositionUpdateListener = listener;
                this.mHandler = new Handler(looper) {
                    public void handleMessage(Message msg) {
                        if (audioTrack2 != null) {
                            switch (msg.what) {
                                case 3:
                                    if (onPlaybackPositionUpdateListener != null) {
                                        onPlaybackPositionUpdateListener.onMarkerReached(audioTrack2);
                                        return;
                                    }
                                    return;
                                case 4:
                                    if (onPlaybackPositionUpdateListener != null) {
                                        onPlaybackPositionUpdateListener.onPeriodicNotification(audioTrack2);
                                        return;
                                    }
                                    return;
                                default:
                                    AudioTrack.loge("Unknown native event type: " + msg.what);
                                    return;
                            }
                        }
                    }
                };
                return;
            }
            this.mHandler = null;
        }

        /* access modifiers changed from: package-private */
        public Handler getHandler() {
            return this.mHandler;
        }
    }

    /* access modifiers changed from: package-private */
    public void playerStart() {
        play();
    }

    /* access modifiers changed from: package-private */
    public void playerPause() {
        pause();
    }

    /* access modifiers changed from: package-private */
    public void playerStop() {
        stop();
    }

    @UnsupportedAppUsage
    private static void postEventFromNative(Object audiotrack_ref, int what, int arg1, int arg2, Object obj) {
        Handler handler;
        AudioTrack track = (AudioTrack) ((WeakReference) audiotrack_ref).get();
        if (track != null) {
            if (what == 1000) {
                track.broadcastRoutingChange();
            } else if (what == 9 || what == 6 || what == 7) {
                track.handleStreamEventFromNative(what, arg1);
            } else {
                NativePositionEventHandlerDelegate delegate = track.mEventHandlerDelegate;
                if (delegate != null && (handler = delegate.getHandler()) != null) {
                    handler.sendMessage(handler.obtainMessage(what, arg1, arg2, obj));
                }
            }
        }
    }

    private static void logd(String msg) {
        Log.d(TAG, msg);
    }

    /* access modifiers changed from: private */
    public static void loge(String msg) {
        Log.e(TAG, msg);
    }

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
