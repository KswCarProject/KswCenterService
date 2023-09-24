package android.media;

import android.annotation.SuppressLint;
import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.app.PendingIntent;
import android.bluetooth.BluetoothCodecConfig;
import android.bluetooth.BluetoothDevice;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.media.IAudioFocusDispatcher;
import android.media.IAudioServerStateDispatcher;
import android.media.IAudioService;
import android.media.IPlaybackConfigDispatcher;
import android.media.IRecordingConfigDispatcher;
import android.media.audiopolicy.AudioPolicy;
import android.media.audiopolicy.AudioProductStrategy;
import android.media.audiopolicy.AudioVolumeGroup;
import android.media.audiopolicy.AudioVolumeGroupChangeHandler;
import android.media.projection.MediaProjection;
import android.media.session.MediaSessionLegacyHelper;
import android.net.Uri;
import android.p007os.Binder;
import android.p007os.Build;
import android.p007os.Handler;
import android.p007os.IBinder;
import android.p007os.Looper;
import android.p007os.Message;
import android.p007os.Process;
import android.p007os.RemoteException;
import android.p007os.ServiceManager;
import android.p007os.SystemClock;
import android.p007os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.view.KeyEvent;
import com.android.internal.C3132R;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.util.Preconditions;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
public class AudioManager {
    public static final String ACTION_AUDIO_BECOMING_NOISY = "android.media.AUDIO_BECOMING_NOISY";
    public static final String ACTION_HDMI_AUDIO_PLUG = "android.media.action.HDMI_AUDIO_PLUG";
    public static final String ACTION_HEADSET_PLUG = "android.intent.action.HEADSET_PLUG";
    public static final String ACTION_MICROPHONE_MUTE_CHANGED = "android.media.action.MICROPHONE_MUTE_CHANGED";
    @Deprecated
    public static final String ACTION_SCO_AUDIO_STATE_CHANGED = "android.media.SCO_AUDIO_STATE_CHANGED";
    public static final String ACTION_SCO_AUDIO_STATE_UPDATED = "android.media.ACTION_SCO_AUDIO_STATE_UPDATED";
    public static final String ACTION_SPEAKERPHONE_STATE_CHANGED = "android.media.action.SPEAKERPHONE_STATE_CHANGED";
    public static final int ADJUST_LOWER = -1;
    public static final int ADJUST_MUTE = -100;
    public static final int ADJUST_RAISE = 1;
    public static final int ADJUST_SAME = 0;
    public static final int ADJUST_TOGGLE_MUTE = 101;
    public static final int ADJUST_UNMUTE = 100;
    public static final int AUDIOFOCUS_FLAGS_APPS = 3;
    public static final int AUDIOFOCUS_FLAGS_SYSTEM = 7;
    @SystemApi
    public static final int AUDIOFOCUS_FLAG_DELAY_OK = 1;
    @SystemApi
    public static final int AUDIOFOCUS_FLAG_LOCK = 4;
    @SystemApi
    public static final int AUDIOFOCUS_FLAG_PAUSES_ON_DUCKABLE_LOSS = 2;
    public static final int AUDIOFOCUS_GAIN = 1;
    public static final int AUDIOFOCUS_GAIN_TRANSIENT = 2;
    public static final int AUDIOFOCUS_GAIN_TRANSIENT_EXCLUSIVE = 4;
    public static final int AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK = 3;
    public static final int AUDIOFOCUS_LOSS = -1;
    public static final int AUDIOFOCUS_LOSS_TRANSIENT = -2;
    public static final int AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK = -3;
    public static final int AUDIOFOCUS_NONE = 0;
    public static final int AUDIOFOCUS_REQUEST_DELAYED = 2;
    public static final int AUDIOFOCUS_REQUEST_FAILED = 0;
    public static final int AUDIOFOCUS_REQUEST_GRANTED = 1;
    public static final int AUDIOFOCUS_REQUEST_WAITING_FOR_EXT_POLICY = 100;
    static final int AUDIOPORT_GENERATION_INIT = 0;
    public static final int AUDIO_SESSION_ID_GENERATE = 0;
    private static final boolean DEBUG = false;
    public static final int DEVICE_IN_ANLG_DOCK_HEADSET = -2147483136;
    public static final int DEVICE_IN_BACK_MIC = -2147483520;
    public static final int DEVICE_IN_BLUETOOTH_SCO_HEADSET = -2147483640;
    public static final int DEVICE_IN_BUILTIN_MIC = -2147483644;
    public static final int DEVICE_IN_DGTL_DOCK_HEADSET = -2147482624;
    public static final int DEVICE_IN_FM_TUNER = -2147475456;
    public static final int DEVICE_IN_HDMI = -2147483616;
    public static final int DEVICE_IN_HDMI_ARC = -2013265920;
    public static final int DEVICE_IN_LINE = -2147450880;
    public static final int DEVICE_IN_LOOPBACK = -2147221504;
    public static final int DEVICE_IN_SPDIF = -2147418112;
    public static final int DEVICE_IN_TELEPHONY_RX = -2147483584;
    public static final int DEVICE_IN_TV_TUNER = -2147467264;
    public static final int DEVICE_IN_USB_ACCESSORY = -2147481600;
    public static final int DEVICE_IN_USB_DEVICE = -2147479552;
    public static final int DEVICE_IN_WIRED_HEADSET = -2147483632;
    public static final int DEVICE_NONE = 0;
    @UnsupportedAppUsage
    public static final int DEVICE_OUT_ANLG_DOCK_HEADSET = 2048;
    public static final int DEVICE_OUT_AUX_DIGITAL = 1024;
    @UnsupportedAppUsage
    public static final int DEVICE_OUT_BLUETOOTH_A2DP = 128;
    @UnsupportedAppUsage
    public static final int DEVICE_OUT_BLUETOOTH_A2DP_HEADPHONES = 256;
    @UnsupportedAppUsage
    public static final int DEVICE_OUT_BLUETOOTH_A2DP_SPEAKER = 512;
    public static final int DEVICE_OUT_BLUETOOTH_SCO = 16;
    public static final int DEVICE_OUT_BLUETOOTH_SCO_CARKIT = 64;
    @UnsupportedAppUsage
    public static final int DEVICE_OUT_BLUETOOTH_SCO_HEADSET = 32;
    public static final int DEVICE_OUT_DEFAULT = 1073741824;
    @UnsupportedAppUsage
    public static final int DEVICE_OUT_DGTL_DOCK_HEADSET = 4096;
    @UnsupportedAppUsage
    public static final int DEVICE_OUT_EARPIECE = 1;
    public static final int DEVICE_OUT_FM = 1048576;
    @UnsupportedAppUsage
    public static final int DEVICE_OUT_HDMI = 1024;
    public static final int DEVICE_OUT_HDMI_ARC = 262144;
    public static final int DEVICE_OUT_LINE = 131072;
    public static final int DEVICE_OUT_REMOTE_SUBMIX = 32768;
    public static final int DEVICE_OUT_SPDIF = 524288;
    @UnsupportedAppUsage
    public static final int DEVICE_OUT_SPEAKER = 2;
    public static final int DEVICE_OUT_TELEPHONY_TX = 65536;
    public static final int DEVICE_OUT_USB_ACCESSORY = 8192;
    public static final int DEVICE_OUT_USB_DEVICE = 16384;
    public static final int DEVICE_OUT_USB_HEADSET = 67108864;
    @UnsupportedAppUsage
    public static final int DEVICE_OUT_WIRED_HEADPHONE = 8;
    @UnsupportedAppUsage
    public static final int DEVICE_OUT_WIRED_HEADSET = 4;
    public static final int ERROR = -1;
    public static final int ERROR_BAD_VALUE = -2;
    public static final int ERROR_DEAD_OBJECT = -6;
    public static final int ERROR_INVALID_OPERATION = -3;
    public static final int ERROR_NO_INIT = -5;
    public static final int ERROR_PERMISSION_DENIED = -4;
    public static final String EXTRA_AUDIO_PLUG_STATE = "android.media.extra.AUDIO_PLUG_STATE";
    public static final String EXTRA_ENCODINGS = "android.media.extra.ENCODINGS";
    public static final String EXTRA_MASTER_VOLUME_MUTED = "android.media.EXTRA_MASTER_VOLUME_MUTED";
    public static final String EXTRA_MAX_CHANNEL_COUNT = "android.media.extra.MAX_CHANNEL_COUNT";
    public static final String EXTRA_PREV_VOLUME_STREAM_DEVICES = "android.media.EXTRA_PREV_VOLUME_STREAM_DEVICES";
    public static final String EXTRA_PREV_VOLUME_STREAM_VALUE = "android.media.EXTRA_PREV_VOLUME_STREAM_VALUE";
    public static final String EXTRA_RINGER_MODE = "android.media.EXTRA_RINGER_MODE";
    public static final String EXTRA_SCO_AUDIO_PREVIOUS_STATE = "android.media.extra.SCO_AUDIO_PREVIOUS_STATE";
    public static final String EXTRA_SCO_AUDIO_STATE = "android.media.extra.SCO_AUDIO_STATE";
    public static final String EXTRA_STREAM_VOLUME_MUTED = "android.media.EXTRA_STREAM_VOLUME_MUTED";
    public static final String EXTRA_VIBRATE_SETTING = "android.media.EXTRA_VIBRATE_SETTING";
    public static final String EXTRA_VIBRATE_TYPE = "android.media.EXTRA_VIBRATE_TYPE";
    public static final String EXTRA_VOLUME_STREAM_DEVICES = "android.media.EXTRA_VOLUME_STREAM_DEVICES";
    @UnsupportedAppUsage
    public static final String EXTRA_VOLUME_STREAM_TYPE = "android.media.EXTRA_VOLUME_STREAM_TYPE";
    public static final String EXTRA_VOLUME_STREAM_TYPE_ALIAS = "android.media.EXTRA_VOLUME_STREAM_TYPE_ALIAS";
    @UnsupportedAppUsage
    public static final String EXTRA_VOLUME_STREAM_VALUE = "android.media.EXTRA_VOLUME_STREAM_VALUE";
    private static final int EXT_FOCUS_POLICY_TIMEOUT_MS = 200;
    public static final int FLAG_ACTIVE_MEDIA_ONLY = 512;
    public static final int FLAG_ALLOW_RINGER_MODES = 2;
    public static final int FLAG_BLUETOOTH_ABS_VOLUME = 64;
    public static final int FLAG_FIXED_VOLUME = 32;
    public static final int FLAG_FROM_KEY = 4096;
    public static final int FLAG_HDMI_SYSTEM_AUDIO_VOLUME = 256;
    public static final int FLAG_PLAY_SOUND = 4;
    public static final int FLAG_REMOVE_SOUND_AND_VIBRATE = 8;
    public static final int FLAG_SHOW_SILENT_HINT = 128;
    public static final int FLAG_SHOW_UI = 1;
    public static final int FLAG_SHOW_UI_WARNINGS = 1024;
    public static final int FLAG_SHOW_VIBRATE_HINT = 2048;
    public static final int FLAG_VIBRATE = 16;
    private static final String FOCUS_CLIENT_ID_STRING = "android_audio_focus_client_id";
    public static final int FX_FOCUS_NAVIGATION_DOWN = 2;
    public static final int FX_FOCUS_NAVIGATION_LEFT = 3;
    public static final int FX_FOCUS_NAVIGATION_RIGHT = 4;
    public static final int FX_FOCUS_NAVIGATION_UP = 1;
    public static final int FX_KEYPRESS_DELETE = 7;
    public static final int FX_KEYPRESS_INVALID = 9;
    public static final int FX_KEYPRESS_RETURN = 8;
    public static final int FX_KEYPRESS_SPACEBAR = 6;
    public static final int FX_KEYPRESS_STANDARD = 5;
    public static final int FX_KEY_CLICK = 0;
    public static final int GET_DEVICES_ALL = 3;
    public static final int GET_DEVICES_INPUTS = 1;
    public static final int GET_DEVICES_OUTPUTS = 2;
    public static final String INTERNAL_RINGER_MODE_CHANGED_ACTION = "android.media.INTERNAL_RINGER_MODE_CHANGED_ACTION";
    public static final String MASTER_MUTE_CHANGED_ACTION = "android.media.MASTER_MUTE_CHANGED_ACTION";
    public static final int MODE_CURRENT = -1;
    public static final int MODE_INVALID = -2;
    public static final int MODE_IN_CALL = 2;
    public static final int MODE_IN_COMMUNICATION = 3;
    public static final int MODE_NORMAL = 0;
    public static final int MODE_RINGTONE = 1;
    private static final int MSG_DEVICES_CALLBACK_REGISTERED = 0;
    private static final int MSG_DEVICES_DEVICES_ADDED = 1;
    private static final int MSG_DEVICES_DEVICES_REMOVED = 2;
    private static final int MSSG_FOCUS_CHANGE = 0;
    private static final int MSSG_PLAYBACK_CONFIG_CHANGE = 2;
    private static final int MSSG_RECORDING_CONFIG_CHANGE = 1;
    @UnsupportedAppUsage
    public static final int NUM_SOUND_EFFECTS = 10;
    @Deprecated
    public static final int NUM_STREAMS = 5;
    public static final String PROPERTY_OUTPUT_FRAMES_PER_BUFFER = "android.media.property.OUTPUT_FRAMES_PER_BUFFER";
    public static final String PROPERTY_OUTPUT_SAMPLE_RATE = "android.media.property.OUTPUT_SAMPLE_RATE";
    public static final String PROPERTY_SUPPORT_AUDIO_SOURCE_UNPROCESSED = "android.media.property.SUPPORT_AUDIO_SOURCE_UNPROCESSED";
    public static final String PROPERTY_SUPPORT_MIC_NEAR_ULTRASOUND = "android.media.property.SUPPORT_MIC_NEAR_ULTRASOUND";
    public static final String PROPERTY_SUPPORT_SPEAKER_NEAR_ULTRASOUND = "android.media.property.SUPPORT_SPEAKER_NEAR_ULTRASOUND";
    public static final int RECORDER_STATE_STARTED = 0;
    public static final int RECORDER_STATE_STOPPED = 1;
    public static final int RECORD_CONFIG_EVENT_NONE = -1;
    public static final int RECORD_CONFIG_EVENT_RELEASE = 3;
    public static final int RECORD_CONFIG_EVENT_START = 0;
    public static final int RECORD_CONFIG_EVENT_STOP = 1;
    public static final int RECORD_CONFIG_EVENT_UPDATE = 2;
    public static final int RECORD_RIID_INVALID = -1;
    public static final String RINGER_MODE_CHANGED_ACTION = "android.media.RINGER_MODE_CHANGED";
    public static final int RINGER_MODE_MAX = 2;
    public static final int RINGER_MODE_NORMAL = 2;
    public static final int RINGER_MODE_SILENT = 0;
    public static final int RINGER_MODE_VIBRATE = 1;
    @Deprecated
    public static final int ROUTE_ALL = -1;
    @Deprecated
    public static final int ROUTE_BLUETOOTH = 4;
    @Deprecated
    public static final int ROUTE_BLUETOOTH_A2DP = 16;
    @Deprecated
    public static final int ROUTE_BLUETOOTH_SCO = 4;
    @Deprecated
    public static final int ROUTE_EARPIECE = 1;
    @Deprecated
    public static final int ROUTE_HEADSET = 8;
    @Deprecated
    public static final int ROUTE_SPEAKER = 2;
    public static final int SCO_AUDIO_STATE_CONNECTED = 1;
    public static final int SCO_AUDIO_STATE_CONNECTING = 2;
    public static final int SCO_AUDIO_STATE_DISCONNECTED = 0;
    public static final int SCO_AUDIO_STATE_ERROR = -1;
    public static final int STREAM_ACCESSIBILITY = 10;
    public static final int STREAM_ALARM = 4;
    @UnsupportedAppUsage
    public static final int STREAM_BLUETOOTH_SCO = 6;
    public static final String STREAM_DEVICES_CHANGED_ACTION = "android.media.STREAM_DEVICES_CHANGED_ACTION";
    public static final int STREAM_DTMF = 8;
    public static final int STREAM_MUSIC = 3;
    public static final String STREAM_MUTE_CHANGED_ACTION = "android.media.STREAM_MUTE_CHANGED_ACTION";
    public static final int STREAM_NOTIFICATION = 5;
    public static final int STREAM_RING = 2;
    public static final int STREAM_SYSTEM = 1;
    @UnsupportedAppUsage
    public static final int STREAM_SYSTEM_ENFORCED = 7;
    @UnsupportedAppUsage
    public static final int STREAM_TTS = 9;
    public static final int STREAM_VOICE_CALL = 0;
    @SystemApi
    public static final int SUCCESS = 0;
    private static final String TAG = "AudioManager";
    public static final int USE_DEFAULT_STREAM_TYPE = Integer.MIN_VALUE;
    public static final String VIBRATE_SETTING_CHANGED_ACTION = "android.media.VIBRATE_SETTING_CHANGED";
    public static final int VIBRATE_SETTING_OFF = 0;
    public static final int VIBRATE_SETTING_ON = 1;
    public static final int VIBRATE_SETTING_ONLY_SILENT = 2;
    public static final int VIBRATE_TYPE_NOTIFICATION = 1;
    public static final int VIBRATE_TYPE_RINGER = 0;
    @UnsupportedAppUsage
    public static final String VOLUME_CHANGED_ACTION = "android.media.VOLUME_CHANGED_ACTION";
    private static final float VOLUME_MIN_DB = -758.0f;
    static ArrayList<AudioPatch> sAudioPatchesCached;
    static Integer sAudioPortGeneration;
    static ArrayList<AudioPort> sAudioPortsCached;
    static ArrayList<AudioPort> sPreviousAudioPortsCached;
    private static IAudioService sService;
    private Context mApplicationContext;
    private final IAudioFocusDispatcher mAudioFocusDispatcher;
    @UnsupportedAppUsage
    private final ConcurrentHashMap<String, FocusRequestInfo> mAudioFocusIdListenerMap;
    private AudioServerStateCallback mAudioServerStateCb;
    private final Object mAudioServerStateCbLock;
    private final IAudioServerStateDispatcher mAudioServerStateDispatcher;
    private Executor mAudioServerStateExec;
    private int mCapturePolicy;
    private final ArrayMap<AudioDeviceCallback, NativeEventHandlerDelegate> mDeviceCallbacks;
    @GuardedBy({"mFocusRequestsLock"})
    private HashMap<String, BlockingFocusResultReceiver> mFocusRequestsAwaitingResult;
    private final Object mFocusRequestsLock;
    private final IBinder mICallBack;
    private Context mOriginalContext;
    private final IPlaybackConfigDispatcher mPlayCb;
    private List<AudioPlaybackCallbackInfo> mPlaybackCallbackList;
    private final Object mPlaybackCallbackLock;
    private OnAmPortUpdateListener mPortListener;
    private ArrayList<AudioDevicePort> mPreviousPorts;
    private final IRecordingConfigDispatcher mRecCb;
    private List<AudioRecordingCallbackInfo> mRecordCallbackList;
    private final Object mRecordCallbackLock;
    private final ServiceEventHandlerDelegate mServiceEventHandlerDelegate;
    private final boolean mUseFixedVolume;
    private final boolean mUseVolumeKeySounds;
    private long mVolumeKeyUpTime;
    private static final AudioPortEventHandler sAudioPortEventHandler = new AudioPortEventHandler();
    private static final AudioVolumeGroupChangeHandler sAudioAudioVolumeGroupChangedHandler = new AudioVolumeGroupChangeHandler();
    private static final TreeMap<Integer, String> FLAG_NAMES = new TreeMap<>();

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes3.dex */
    public @interface FocusRequestResult {
    }

    /* loaded from: classes3.dex */
    public interface OnAudioFocusChangeListener {
        void onAudioFocusChange(int i);
    }

    /* loaded from: classes3.dex */
    public interface OnAudioPortUpdateListener {
        void onAudioPatchListUpdate(AudioPatch[] audioPatchArr);

        void onAudioPortListUpdate(AudioPort[] audioPortArr);

        void onServiceDied();
    }

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes3.dex */
    public @interface PublicStreamTypes {
    }

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes3.dex */
    public @interface VolumeAdjustment {
    }

    static {
        FLAG_NAMES.put(1, "FLAG_SHOW_UI");
        FLAG_NAMES.put(2, "FLAG_ALLOW_RINGER_MODES");
        FLAG_NAMES.put(4, "FLAG_PLAY_SOUND");
        FLAG_NAMES.put(8, "FLAG_REMOVE_SOUND_AND_VIBRATE");
        FLAG_NAMES.put(16, "FLAG_VIBRATE");
        FLAG_NAMES.put(32, "FLAG_FIXED_VOLUME");
        FLAG_NAMES.put(64, "FLAG_BLUETOOTH_ABS_VOLUME");
        FLAG_NAMES.put(128, "FLAG_SHOW_SILENT_HINT");
        FLAG_NAMES.put(256, "FLAG_HDMI_SYSTEM_AUDIO_VOLUME");
        FLAG_NAMES.put(512, "FLAG_ACTIVE_MEDIA_ONLY");
        FLAG_NAMES.put(1024, "FLAG_SHOW_UI_WARNINGS");
        FLAG_NAMES.put(2048, "FLAG_SHOW_VIBRATE_HINT");
        FLAG_NAMES.put(4096, "FLAG_FROM_KEY");
        sAudioPortGeneration = new Integer(0);
        sAudioPortsCached = new ArrayList<>();
        sPreviousAudioPortsCached = new ArrayList<>();
        sAudioPatchesCached = new ArrayList<>();
    }

    public static final String adjustToString(int adj) {
        if (adj != -100) {
            switch (adj) {
                case -1:
                    return "ADJUST_LOWER";
                case 0:
                    return "ADJUST_SAME";
                case 1:
                    return "ADJUST_RAISE";
                default:
                    switch (adj) {
                        case 100:
                            return "ADJUST_UNMUTE";
                        case 101:
                            return "ADJUST_TOGGLE_MUTE";
                        default:
                            return "unknown adjust mode " + adj;
                    }
            }
        }
        return "ADJUST_MUTE";
    }

    public static String flagsToString(int flags) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Integer, String> entry : FLAG_NAMES.entrySet()) {
            int flag = entry.getKey().intValue();
            if ((flags & flag) != 0) {
                if (sb.length() > 0) {
                    sb.append(',');
                }
                sb.append(entry.getValue());
                flags &= ~flag;
            }
        }
        if (flags != 0) {
            if (sb.length() > 0) {
                sb.append(',');
            }
            sb.append(flags);
        }
        return sb.toString();
    }

    @UnsupportedAppUsage
    public AudioManager() {
        this.mCapturePolicy = 1;
        this.mAudioFocusIdListenerMap = new ConcurrentHashMap<>();
        this.mServiceEventHandlerDelegate = new ServiceEventHandlerDelegate(null);
        this.mAudioFocusDispatcher = new IAudioFocusDispatcher.Stub() { // from class: android.media.AudioManager.1
            @Override // android.media.IAudioFocusDispatcher
            public void dispatchAudioFocusChange(int focusChange, String id) {
                FocusRequestInfo fri = AudioManager.this.findFocusRequestInfo(id);
                if (fri != null) {
                    OnAudioFocusChangeListener listener = fri.mRequest.getOnAudioFocusChangeListener();
                    if (listener != null) {
                        Handler h = fri.mHandler == null ? AudioManager.this.mServiceEventHandlerDelegate.getHandler() : fri.mHandler;
                        Message m = h.obtainMessage(0, focusChange, 0, id);
                        h.sendMessage(m);
                    }
                }
            }

            @Override // android.media.IAudioFocusDispatcher
            public void dispatchFocusResultFromExtPolicy(int requestResult, String clientId) {
                synchronized (AudioManager.this.mFocusRequestsLock) {
                    BlockingFocusResultReceiver focusReceiver = (BlockingFocusResultReceiver) AudioManager.this.mFocusRequestsAwaitingResult.remove(clientId);
                    if (focusReceiver != null) {
                        focusReceiver.notifyResult(requestResult);
                    } else {
                        Log.m70e(AudioManager.TAG, "dispatchFocusResultFromExtPolicy found no result receiver");
                    }
                }
            }
        };
        this.mFocusRequestsLock = new Object();
        this.mPlaybackCallbackLock = new Object();
        this.mPlayCb = new IPlaybackConfigDispatcher.Stub() { // from class: android.media.AudioManager.2
            @Override // android.media.IPlaybackConfigDispatcher
            public void dispatchPlaybackConfigChange(List<AudioPlaybackConfiguration> configs, boolean flush) {
                if (flush) {
                    Binder.flushPendingCommands();
                }
                synchronized (AudioManager.this.mPlaybackCallbackLock) {
                    if (AudioManager.this.mPlaybackCallbackList != null) {
                        for (int i = 0; i < AudioManager.this.mPlaybackCallbackList.size(); i++) {
                            AudioPlaybackCallbackInfo arci = (AudioPlaybackCallbackInfo) AudioManager.this.mPlaybackCallbackList.get(i);
                            if (arci.mHandler != null) {
                                Message m = arci.mHandler.obtainMessage(2, new PlaybackConfigChangeCallbackData(arci.mCb, configs));
                                arci.mHandler.sendMessage(m);
                            }
                        }
                    }
                }
            }
        };
        this.mRecordCallbackLock = new Object();
        this.mRecCb = new IRecordingConfigDispatcher.Stub() { // from class: android.media.AudioManager.3
            @Override // android.media.IRecordingConfigDispatcher
            public void dispatchRecordingConfigChange(List<AudioRecordingConfiguration> configs) {
                synchronized (AudioManager.this.mRecordCallbackLock) {
                    if (AudioManager.this.mRecordCallbackList != null) {
                        for (int i = 0; i < AudioManager.this.mRecordCallbackList.size(); i++) {
                            AudioRecordingCallbackInfo arci = (AudioRecordingCallbackInfo) AudioManager.this.mRecordCallbackList.get(i);
                            if (arci.mHandler != null) {
                                Message m = arci.mHandler.obtainMessage(1, new RecordConfigChangeCallbackData(arci.mCb, configs));
                                arci.mHandler.sendMessage(m);
                            }
                        }
                    }
                }
            }
        };
        this.mICallBack = new Binder();
        this.mPortListener = null;
        this.mDeviceCallbacks = new ArrayMap<>();
        this.mPreviousPorts = new ArrayList<>();
        this.mAudioServerStateCbLock = new Object();
        this.mAudioServerStateDispatcher = new BinderC10404();
        this.mUseVolumeKeySounds = true;
        this.mUseFixedVolume = false;
    }

    @UnsupportedAppUsage
    public AudioManager(Context context) {
        this.mCapturePolicy = 1;
        this.mAudioFocusIdListenerMap = new ConcurrentHashMap<>();
        this.mServiceEventHandlerDelegate = new ServiceEventHandlerDelegate(null);
        this.mAudioFocusDispatcher = new IAudioFocusDispatcher.Stub() { // from class: android.media.AudioManager.1
            @Override // android.media.IAudioFocusDispatcher
            public void dispatchAudioFocusChange(int focusChange, String id) {
                FocusRequestInfo fri = AudioManager.this.findFocusRequestInfo(id);
                if (fri != null) {
                    OnAudioFocusChangeListener listener = fri.mRequest.getOnAudioFocusChangeListener();
                    if (listener != null) {
                        Handler h = fri.mHandler == null ? AudioManager.this.mServiceEventHandlerDelegate.getHandler() : fri.mHandler;
                        Message m = h.obtainMessage(0, focusChange, 0, id);
                        h.sendMessage(m);
                    }
                }
            }

            @Override // android.media.IAudioFocusDispatcher
            public void dispatchFocusResultFromExtPolicy(int requestResult, String clientId) {
                synchronized (AudioManager.this.mFocusRequestsLock) {
                    BlockingFocusResultReceiver focusReceiver = (BlockingFocusResultReceiver) AudioManager.this.mFocusRequestsAwaitingResult.remove(clientId);
                    if (focusReceiver != null) {
                        focusReceiver.notifyResult(requestResult);
                    } else {
                        Log.m70e(AudioManager.TAG, "dispatchFocusResultFromExtPolicy found no result receiver");
                    }
                }
            }
        };
        this.mFocusRequestsLock = new Object();
        this.mPlaybackCallbackLock = new Object();
        this.mPlayCb = new IPlaybackConfigDispatcher.Stub() { // from class: android.media.AudioManager.2
            @Override // android.media.IPlaybackConfigDispatcher
            public void dispatchPlaybackConfigChange(List<AudioPlaybackConfiguration> configs, boolean flush) {
                if (flush) {
                    Binder.flushPendingCommands();
                }
                synchronized (AudioManager.this.mPlaybackCallbackLock) {
                    if (AudioManager.this.mPlaybackCallbackList != null) {
                        for (int i = 0; i < AudioManager.this.mPlaybackCallbackList.size(); i++) {
                            AudioPlaybackCallbackInfo arci = (AudioPlaybackCallbackInfo) AudioManager.this.mPlaybackCallbackList.get(i);
                            if (arci.mHandler != null) {
                                Message m = arci.mHandler.obtainMessage(2, new PlaybackConfigChangeCallbackData(arci.mCb, configs));
                                arci.mHandler.sendMessage(m);
                            }
                        }
                    }
                }
            }
        };
        this.mRecordCallbackLock = new Object();
        this.mRecCb = new IRecordingConfigDispatcher.Stub() { // from class: android.media.AudioManager.3
            @Override // android.media.IRecordingConfigDispatcher
            public void dispatchRecordingConfigChange(List<AudioRecordingConfiguration> configs) {
                synchronized (AudioManager.this.mRecordCallbackLock) {
                    if (AudioManager.this.mRecordCallbackList != null) {
                        for (int i = 0; i < AudioManager.this.mRecordCallbackList.size(); i++) {
                            AudioRecordingCallbackInfo arci = (AudioRecordingCallbackInfo) AudioManager.this.mRecordCallbackList.get(i);
                            if (arci.mHandler != null) {
                                Message m = arci.mHandler.obtainMessage(1, new RecordConfigChangeCallbackData(arci.mCb, configs));
                                arci.mHandler.sendMessage(m);
                            }
                        }
                    }
                }
            }
        };
        this.mICallBack = new Binder();
        this.mPortListener = null;
        this.mDeviceCallbacks = new ArrayMap<>();
        this.mPreviousPorts = new ArrayList<>();
        this.mAudioServerStateCbLock = new Object();
        this.mAudioServerStateDispatcher = new BinderC10404();
        setContext(context);
        this.mUseVolumeKeySounds = getContext().getResources().getBoolean(C3132R.bool.config_useVolumeKeySounds);
        this.mUseFixedVolume = getContext().getResources().getBoolean(C3132R.bool.config_useFixedVolume);
    }

    private Context getContext() {
        if (this.mApplicationContext == null) {
            setContext(this.mOriginalContext);
        }
        if (this.mApplicationContext != null) {
            return this.mApplicationContext;
        }
        return this.mOriginalContext;
    }

    private void setContext(Context context) {
        this.mApplicationContext = context.getApplicationContext();
        if (this.mApplicationContext != null) {
            this.mOriginalContext = null;
        } else {
            this.mOriginalContext = context;
        }
    }

    @UnsupportedAppUsage
    private static IAudioService getService() {
        if (sService != null) {
            return sService;
        }
        IBinder b = ServiceManager.getService("audio");
        sService = IAudioService.Stub.asInterface(b);
        return sService;
    }

    public void dispatchMediaKeyEvent(KeyEvent keyEvent) {
        MediaSessionLegacyHelper helper = MediaSessionLegacyHelper.getHelper(getContext());
        helper.sendMediaButtonEvent(keyEvent, false);
    }

    public void preDispatchKeyEvent(KeyEvent event, int stream) {
        int keyCode = event.getKeyCode();
        if (keyCode != 25 && keyCode != 24 && keyCode != 164 && this.mVolumeKeyUpTime + 300 > SystemClock.uptimeMillis()) {
            adjustSuggestedStreamVolume(0, stream, 8);
        }
    }

    public boolean isVolumeFixed() {
        return this.mUseFixedVolume;
    }

    public void adjustStreamVolume(int streamType, int direction, int flags) {
        IAudioService service = getService();
        try {
            service.adjustStreamVolume(streamType, direction, flags, getContext().getOpPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void adjustVolume(int direction, int flags) {
        MediaSessionLegacyHelper helper = MediaSessionLegacyHelper.getHelper(getContext());
        helper.sendAdjustVolumeBy(Integer.MIN_VALUE, direction, flags);
    }

    public void adjustSuggestedStreamVolume(int direction, int suggestedStreamType, int flags) {
        MediaSessionLegacyHelper helper = MediaSessionLegacyHelper.getHelper(getContext());
        helper.sendAdjustVolumeBy(suggestedStreamType, direction, flags);
    }

    @UnsupportedAppUsage
    public void setMasterMute(boolean mute, int flags) {
        IAudioService service = getService();
        try {
            service.setMasterMute(mute, flags, getContext().getOpPackageName(), UserHandle.getCallingUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getRingerMode() {
        IAudioService service = getService();
        try {
            return service.getRingerModeExternal();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    public static boolean isValidRingerMode(int ringerMode) {
        if (ringerMode < 0 || ringerMode > 2) {
            return false;
        }
        IAudioService service = getService();
        try {
            return service.isValidRingerMode(ringerMode);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getStreamMaxVolume(int streamType) {
        IAudioService service = getService();
        try {
            return service.getStreamMaxVolume(streamType);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getStreamMinVolume(int streamType) {
        if (!isPublicStreamType(streamType)) {
            throw new IllegalArgumentException("Invalid stream type " + streamType);
        }
        return getStreamMinVolumeInt(streamType);
    }

    public int getStreamMinVolumeInt(int streamType) {
        IAudioService service = getService();
        try {
            return service.getStreamMinVolume(streamType);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getStreamVolume(int streamType) {
        IAudioService service = getService();
        try {
            return service.getStreamVolume(streamType);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public float getStreamVolumeDb(int streamType, int index, int deviceType) {
        if (!isPublicStreamType(streamType)) {
            throw new IllegalArgumentException("Invalid stream type " + streamType);
        } else if (index > getStreamMaxVolume(streamType) || index < getStreamMinVolume(streamType)) {
            throw new IllegalArgumentException("Invalid stream volume index " + index);
        } else if (!AudioDeviceInfo.isValidAudioDeviceTypeOut(deviceType)) {
            throw new IllegalArgumentException("Invalid audio output device type " + deviceType);
        } else {
            float gain = AudioSystem.getStreamVolumeDB(streamType, index, AudioDeviceInfo.convertDeviceTypeToInternalDevice(deviceType));
            if (gain <= VOLUME_MIN_DB) {
                return Float.NEGATIVE_INFINITY;
            }
            return gain;
        }
    }

    private static boolean isPublicStreamType(int streamType) {
        if (streamType == 8 || streamType == 10) {
            return true;
        }
        switch (streamType) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                return true;
            default:
                return false;
        }
    }

    @UnsupportedAppUsage
    public int getLastAudibleStreamVolume(int streamType) {
        IAudioService service = getService();
        try {
            return service.getLastAudibleStreamVolume(streamType);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getUiSoundsStreamType() {
        IAudioService service = getService();
        try {
            return service.getUiSoundsStreamType();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setRingerMode(int ringerMode) {
        if (!isValidRingerMode(ringerMode)) {
            return;
        }
        IAudioService service = getService();
        try {
            service.setRingerModeExternal(ringerMode, getContext().getOpPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setStreamVolume(int streamType, int index, int flags) {
        IAudioService service = getService();
        try {
            service.setStreamVolume(streamType, index, flags, getContext().getOpPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void setVolumeIndexForAttributes(AudioAttributes attr, int index, int flags) {
        Preconditions.checkNotNull(attr, "attr must not be null");
        IAudioService service = getService();
        try {
            service.setVolumeIndexForAttributes(attr, index, flags, getContext().getOpPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public int getVolumeIndexForAttributes(AudioAttributes attr) {
        Preconditions.checkNotNull(attr, "attr must not be null");
        IAudioService service = getService();
        try {
            return service.getVolumeIndexForAttributes(attr);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public int getMaxVolumeIndexForAttributes(AudioAttributes attr) {
        Preconditions.checkNotNull(attr, "attr must not be null");
        IAudioService service = getService();
        try {
            return service.getMaxVolumeIndexForAttributes(attr);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public int getMinVolumeIndexForAttributes(AudioAttributes attr) {
        Preconditions.checkNotNull(attr, "attr must not be null");
        IAudioService service = getService();
        try {
            return service.getMinVolumeIndexForAttributes(attr);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public void setStreamSolo(int streamType, boolean state) {
        Log.m64w(TAG, "setStreamSolo has been deprecated. Do not use.");
    }

    @Deprecated
    public void setStreamMute(int streamType, boolean state) {
        Log.m64w(TAG, "setStreamMute is deprecated. adjustStreamVolume should be used instead.");
        int direction = state ? -100 : 100;
        if (streamType == Integer.MIN_VALUE) {
            adjustSuggestedStreamVolume(direction, streamType, 0);
        } else {
            adjustStreamVolume(streamType, direction, 0);
        }
    }

    public boolean isStreamMute(int streamType) {
        IAudioService service = getService();
        try {
            return service.isStreamMute(streamType);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    public boolean isMasterMute() {
        IAudioService service = getService();
        try {
            return service.isMasterMute();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    public void forceVolumeControlStream(int streamType) {
        IAudioService service = getService();
        try {
            service.forceVolumeControlStream(streamType, this.mICallBack);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean shouldVibrate(int vibrateType) {
        IAudioService service = getService();
        try {
            return service.shouldVibrate(vibrateType);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getVibrateSetting(int vibrateType) {
        IAudioService service = getService();
        try {
            return service.getVibrateSetting(vibrateType);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setVibrateSetting(int vibrateType, int vibrateSetting) {
        IAudioService service = getService();
        try {
            service.setVibrateSetting(vibrateType, vibrateSetting);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setSpeakerphoneOn(boolean on) {
        IAudioService service = getService();
        Log.m68i(TAG, "In setSpeakerphoneOn(), on: " + on + ", calling application: " + this.mApplicationContext.getOpPackageName());
        try {
            service.setSpeakerphoneOn(on);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isSpeakerphoneOn() {
        Log.m68i(TAG, "In isSpeakerphoneOn(), calling application: " + this.mApplicationContext.getOpPackageName());
        IAudioService service = getService();
        try {
            return service.isSpeakerphoneOn();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setAllowedCapturePolicy(int capturePolicy) {
        int flags = AudioAttributes.capturePolicyToFlags(capturePolicy, 0);
        int result = AudioSystem.setAllowedCapturePolicy(Process.myUid(), flags);
        if (result != 0) {
            Log.m70e(TAG, "Could not setAllowedCapturePolicy: " + result);
            return;
        }
        this.mCapturePolicy = capturePolicy;
    }

    public int getAllowedCapturePolicy() {
        return this.mCapturePolicy;
    }

    public static boolean isOffloadedPlaybackSupported(AudioFormat format, AudioAttributes attributes) {
        if (format == null) {
            throw new NullPointerException("Illegal null AudioFormat");
        }
        if (attributes == null) {
            throw new NullPointerException("Illegal null AudioAttributes");
        }
        return AudioSystem.isOffloadSupported(format, attributes);
    }

    public boolean isBluetoothScoAvailableOffCall() {
        boolean retval = getContext().getResources().getBoolean(C3132R.bool.config_bluetooth_sco_off_call);
        Log.m68i(TAG, "In isBluetoothScoAvailableOffCall(), calling appilication: " + this.mApplicationContext.getOpPackageName() + ", return value: " + retval);
        return retval;
    }

    public void startBluetoothSco() {
        IAudioService service = getService();
        Log.m68i(TAG, "In startbluetoothSco(), calling application: " + this.mApplicationContext.getOpPackageName());
        try {
            service.startBluetoothSco(this.mICallBack, getContext().getApplicationInfo().targetSdkVersion);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    public void startBluetoothScoVirtualCall() {
        Log.m68i(TAG, "In startBluetoothScoVirtualCall(), calling application: " + this.mApplicationContext.getOpPackageName());
        IAudioService service = getService();
        try {
            service.startBluetoothScoVirtualCall(this.mICallBack);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void stopBluetoothSco() {
        IAudioService service = getService();
        Log.m68i(TAG, "In stopBluetoothSco(), calling application: " + this.mApplicationContext.getOpPackageName());
        try {
            service.stopBluetoothSco(this.mICallBack);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setBluetoothScoOn(boolean on) {
        IAudioService service = getService();
        Log.m68i(TAG, "In setBluetoothScoOn(), on: " + on + ", calling application: " + this.mApplicationContext.getOpPackageName());
        try {
            service.setBluetoothScoOn(on);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isBluetoothScoOn() {
        IAudioService service = getService();
        Log.m68i(TAG, "In isBluetoothScoOn(), calling application: " + this.mApplicationContext.getOpPackageName());
        try {
            return service.isBluetoothScoOn();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public void setBluetoothA2dpOn(boolean on) {
    }

    public boolean isBluetoothA2dpOn() {
        return AudioSystem.getDeviceConnectionState(128, "") == 1 || AudioSystem.getDeviceConnectionState(256, "") == 1 || AudioSystem.getDeviceConnectionState(512, "") == 1;
    }

    @Deprecated
    public void setWiredHeadsetOn(boolean on) {
    }

    public boolean isWiredHeadsetOn() {
        if (AudioSystem.getDeviceConnectionState(4, "") == 0 && AudioSystem.getDeviceConnectionState(8, "") == 0 && AudioSystem.getDeviceConnectionState(67108864, "") == 0) {
            return false;
        }
        return true;
    }

    public void setMicrophoneMute(boolean on) {
        IAudioService service = getService();
        try {
            service.setMicrophoneMute(on, getContext().getOpPackageName(), UserHandle.getCallingUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isMicrophoneMute() {
        return AudioSystem.isMicrophoneMuted();
    }

    public void setMode(int mode) {
        IAudioService service = getService();
        try {
            service.setMode(mode, this.mICallBack, this.mApplicationContext.getOpPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getMode() {
        IAudioService service = getService();
        try {
            return service.getMode();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public void setRouting(int mode, int routes, int mask) {
    }

    @Deprecated
    public int getRouting(int mode) {
        return -1;
    }

    public boolean isMusicActive() {
        return AudioSystem.isStreamActive(3, 0);
    }

    @UnsupportedAppUsage
    public boolean isMusicActiveRemotely() {
        return AudioSystem.isStreamActiveRemotely(3, 0);
    }

    public boolean isAudioFocusExclusive() {
        IAudioService service = getService();
        try {
            return service.getCurrentAudioFocus() == 4;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int generateAudioSessionId() {
        int session = AudioSystem.newAudioSessionId();
        if (session > 0) {
            return session;
        }
        Log.m70e(TAG, "Failure to generate a new audio session ID");
        return -1;
    }

    @Deprecated
    public void setParameter(String key, String value) {
        setParameters(key + "=" + value);
    }

    public void setParameters(String keyValuePairs) {
        AudioSystem.setParameters(keyValuePairs);
    }

    public String getParameters(String keys) {
        return AudioSystem.getParameters(keys);
    }

    public void playSoundEffect(int effectType) {
        if (effectType < 0 || effectType >= 10 || !querySoundEffectsEnabled(Process.myUserHandle().getIdentifier())) {
            return;
        }
        IAudioService service = getService();
        try {
            service.playSoundEffect(effectType);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void playSoundEffect(int effectType, int userId) {
        if (effectType < 0 || effectType >= 10 || !querySoundEffectsEnabled(userId)) {
            return;
        }
        IAudioService service = getService();
        try {
            service.playSoundEffect(effectType);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void playSoundEffect(int effectType, float volume) {
        if (effectType < 0 || effectType >= 10) {
            return;
        }
        IAudioService service = getService();
        try {
            service.playSoundEffectVolume(effectType, volume);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private boolean querySoundEffectsEnabled(int user) {
        return Settings.System.getIntForUser(getContext().getContentResolver(), Settings.System.SOUND_EFFECTS_ENABLED, 0, user) != 0;
    }

    public void loadSoundEffects() {
        IAudioService service = getService();
        try {
            service.loadSoundEffects();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void unloadSoundEffects() {
        IAudioService service = getService();
        try {
            service.unloadSoundEffects();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* loaded from: classes3.dex */
    private static class FocusRequestInfo {
        final Handler mHandler;
        final AudioFocusRequest mRequest;

        FocusRequestInfo(AudioFocusRequest afr, Handler handler) {
            this.mRequest = afr;
            this.mHandler = handler;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public FocusRequestInfo findFocusRequestInfo(String id) {
        return this.mAudioFocusIdListenerMap.get(id);
    }

    /* loaded from: classes3.dex */
    private class ServiceEventHandlerDelegate {
        private final Handler mHandler;

        ServiceEventHandlerDelegate(Handler handler) {
            Looper looper;
            if (handler == null) {
                Looper myLooper = Looper.myLooper();
                looper = myLooper;
                if (myLooper == null) {
                    looper = Looper.getMainLooper();
                }
            } else {
                looper = handler.getLooper();
            }
            Looper looper2 = looper;
            if (looper2 != null) {
                this.mHandler = new Handler(looper2) { // from class: android.media.AudioManager.ServiceEventHandlerDelegate.1
                    @Override // android.p007os.Handler
                    public void handleMessage(Message msg) {
                        OnAudioFocusChangeListener listener;
                        switch (msg.what) {
                            case 0:
                                FocusRequestInfo fri = AudioManager.this.findFocusRequestInfo((String) msg.obj);
                                if (fri != null && (listener = fri.mRequest.getOnAudioFocusChangeListener()) != null) {
                                    Log.m72d(AudioManager.TAG, "dispatching onAudioFocusChange(" + msg.arg1 + ") to " + msg.obj);
                                    listener.onAudioFocusChange(msg.arg1);
                                    return;
                                }
                                return;
                            case 1:
                                RecordConfigChangeCallbackData cbData = (RecordConfigChangeCallbackData) msg.obj;
                                if (cbData.mCb != null) {
                                    cbData.mCb.onRecordingConfigChanged(cbData.mConfigs);
                                    return;
                                }
                                return;
                            case 2:
                                PlaybackConfigChangeCallbackData cbData2 = (PlaybackConfigChangeCallbackData) msg.obj;
                                if (cbData2.mCb != null) {
                                    cbData2.mCb.onPlaybackConfigChanged(cbData2.mConfigs);
                                    return;
                                }
                                return;
                            default:
                                Log.m70e(AudioManager.TAG, "Unknown event " + msg.what);
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

    private String getIdForAudioFocusListener(OnAudioFocusChangeListener l) {
        if (l == null) {
            return new String(toString());
        }
        return new String(toString() + l.toString());
    }

    public void registerAudioFocusRequest(AudioFocusRequest afr) {
        Handler h = afr.getOnAudioFocusChangeListenerHandler();
        FocusRequestInfo fri = new FocusRequestInfo(afr, h == null ? null : new ServiceEventHandlerDelegate(h).getHandler());
        String key = getIdForAudioFocusListener(afr.getOnAudioFocusChangeListener());
        this.mAudioFocusIdListenerMap.put(key, fri);
    }

    public void unregisterAudioFocusRequest(OnAudioFocusChangeListener l) {
        this.mAudioFocusIdListenerMap.remove(getIdForAudioFocusListener(l));
    }

    public int requestAudioFocus(OnAudioFocusChangeListener l, int streamType, int durationHint) {
        PlayerBase.deprecateStreamTypeForPlayback(streamType, TAG, "requestAudioFocus()");
        try {
            int status = requestAudioFocus(l, new AudioAttributes.Builder().setInternalLegacyStreamType(streamType).build(), durationHint, 0);
            return status;
        } catch (IllegalArgumentException e) {
            Log.m69e(TAG, "Audio focus request denied due to ", e);
            return 0;
        }
    }

    public int requestAudioFocus(AudioFocusRequest focusRequest) {
        return requestAudioFocus(focusRequest, null);
    }

    public int abandonAudioFocusRequest(AudioFocusRequest focusRequest) {
        if (focusRequest == null) {
            throw new IllegalArgumentException("Illegal null AudioFocusRequest");
        }
        return abandonAudioFocus(focusRequest.getOnAudioFocusChangeListener(), focusRequest.getAudioAttributes());
    }

    @SystemApi
    public int requestAudioFocus(OnAudioFocusChangeListener l, AudioAttributes requestAttributes, int durationHint, int flags) throws IllegalArgumentException {
        if (flags != (flags & 3)) {
            throw new IllegalArgumentException("Invalid flags 0x" + Integer.toHexString(flags).toUpperCase());
        }
        return requestAudioFocus(l, requestAttributes, durationHint, flags & 3, null);
    }

    @SystemApi
    public int requestAudioFocus(OnAudioFocusChangeListener l, AudioAttributes requestAttributes, int durationHint, int flags, AudioPolicy ap) throws IllegalArgumentException {
        if (requestAttributes == null) {
            throw new IllegalArgumentException("Illegal null AudioAttributes argument");
        }
        if (!AudioFocusRequest.isValidFocusGain(durationHint)) {
            throw new IllegalArgumentException("Invalid duration hint");
        }
        if (flags != (flags & 7)) {
            throw new IllegalArgumentException("Illegal flags 0x" + Integer.toHexString(flags).toUpperCase());
        }
        if ((flags & 1) == 1 && l == null) {
            throw new IllegalArgumentException("Illegal null focus listener when flagged as accepting delayed focus grant");
        }
        if ((flags & 2) == 2 && l == null) {
            throw new IllegalArgumentException("Illegal null focus listener when flagged as pausing instead of ducking");
        }
        if ((flags & 4) == 4 && ap == null) {
            throw new IllegalArgumentException("Illegal null audio policy when locking audio focus");
        }
        AudioFocusRequest afr = new AudioFocusRequest.Builder(durationHint).setOnAudioFocusChangeListenerInt(l, null).setAudioAttributes(requestAttributes).setAcceptsDelayedFocusGain((flags & 1) == 1).setWillPauseWhenDucked((flags & 2) == 2).setLocksFocus((flags & 4) == 4).build();
        return requestAudioFocus(afr, ap);
    }

    @SystemApi
    public int requestAudioFocus(AudioFocusRequest afr, AudioPolicy ap) {
        int i;
        if (afr == null) {
            throw new NullPointerException("Illegal null AudioFocusRequest");
        }
        if (afr.locksFocus() && ap == null) {
            throw new IllegalArgumentException("Illegal null audio policy when locking audio focus");
        }
        registerAudioFocusRequest(afr);
        IAudioService service = getService();
        try {
            i = getContext().getApplicationInfo().targetSdkVersion;
        } catch (NullPointerException e) {
            i = Build.VERSION.SDK_INT;
        }
        int sdk = i;
        String clientId = getIdForAudioFocusListener(afr.getOnAudioFocusChangeListener());
        synchronized (this.mFocusRequestsLock) {
            try {
                int status = service.requestAudioFocus(afr.getAudioAttributes(), afr.getFocusGain(), this.mICallBack, this.mAudioFocusDispatcher, clientId, getContext().getOpPackageName(), afr.getFlags(), ap != null ? ap.m111cb() : null, sdk);
                if (status != 100) {
                    return status;
                }
                if (this.mFocusRequestsAwaitingResult == null) {
                    this.mFocusRequestsAwaitingResult = new HashMap<>(1);
                }
                BlockingFocusResultReceiver focusReceiver = new BlockingFocusResultReceiver(clientId);
                this.mFocusRequestsAwaitingResult.put(clientId, focusReceiver);
                focusReceiver.waitForResult(200L);
                synchronized (this.mFocusRequestsLock) {
                    this.mFocusRequestsAwaitingResult.remove(clientId);
                }
                return focusReceiver.requestResult();
            } catch (RemoteException e2) {
                throw e2.rethrowFromSystemServer();
            }
        }
    }

    /* loaded from: classes3.dex */
    private static final class SafeWaitObject {
        private boolean mQuit;

        private SafeWaitObject() {
            this.mQuit = false;
        }

        public void safeNotify() {
            synchronized (this) {
                this.mQuit = true;
                notify();
            }
        }

        public void safeWait(long millis) throws InterruptedException {
            long timeOutTime = System.currentTimeMillis() + millis;
            synchronized (this) {
                while (!this.mQuit) {
                    long timeToWait = timeOutTime - System.currentTimeMillis();
                    if (timeToWait < 0) {
                        break;
                    }
                    wait(timeToWait);
                }
            }
        }
    }

    /* loaded from: classes3.dex */
    private static final class BlockingFocusResultReceiver {
        private final String mFocusClientId;
        private final SafeWaitObject mLock = new SafeWaitObject();
        @GuardedBy({"mLock"})
        private boolean mResultReceived = false;
        private int mFocusRequestResult = 0;

        BlockingFocusResultReceiver(String clientId) {
            this.mFocusClientId = clientId;
        }

        boolean receivedResult() {
            return this.mResultReceived;
        }

        int requestResult() {
            return this.mFocusRequestResult;
        }

        void notifyResult(int requestResult) {
            synchronized (this.mLock) {
                this.mResultReceived = true;
                this.mFocusRequestResult = requestResult;
                this.mLock.safeNotify();
            }
        }

        public void waitForResult(long timeOutMs) {
            synchronized (this.mLock) {
                if (this.mResultReceived) {
                    return;
                }
                try {
                    this.mLock.safeWait(timeOutMs);
                } catch (InterruptedException e) {
                }
            }
        }
    }

    @UnsupportedAppUsage
    public void requestAudioFocusForCall(int streamType, int durationHint) {
        IAudioService service = getService();
        try {
            service.requestAudioFocus(new AudioAttributes.Builder().setInternalLegacyStreamType(streamType).build(), durationHint, this.mICallBack, null, AudioSystem.IN_VOICE_COMM_FOCUS_ID, getContext().getOpPackageName(), 4, null, 0);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getFocusRampTimeMs(int focusGain, AudioAttributes attr) {
        IAudioService service = getService();
        try {
            return service.getFocusRampTimeMs(focusGain, attr);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void setFocusRequestResult(AudioFocusInfo afi, int requestResult, AudioPolicy ap) {
        if (afi == null) {
            throw new IllegalArgumentException("Illegal null AudioFocusInfo");
        }
        if (ap == null) {
            throw new IllegalArgumentException("Illegal null AudioPolicy");
        }
        IAudioService service = getService();
        try {
            service.setFocusRequestResultFromExtPolicy(afi, requestResult, ap.m111cb());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public int dispatchAudioFocusChange(AudioFocusInfo afi, int focusChange, AudioPolicy ap) {
        if (afi == null) {
            throw new NullPointerException("Illegal null AudioFocusInfo");
        }
        if (ap == null) {
            throw new NullPointerException("Illegal null AudioPolicy");
        }
        IAudioService service = getService();
        try {
            return service.dispatchFocusChange(afi, focusChange, ap.m111cb());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    public void abandonAudioFocusForCall() {
        IAudioService service = getService();
        try {
            service.abandonAudioFocus(null, AudioSystem.IN_VOICE_COMM_FOCUS_ID, null, getContext().getOpPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int abandonAudioFocus(OnAudioFocusChangeListener l) {
        return abandonAudioFocus(l, null);
    }

    @SystemApi
    @SuppressLint({"Doclava125"})
    public int abandonAudioFocus(OnAudioFocusChangeListener l, AudioAttributes aa) {
        unregisterAudioFocusRequest(l);
        IAudioService service = getService();
        try {
            int status = service.abandonAudioFocus(this.mAudioFocusDispatcher, getIdForAudioFocusListener(l), aa, getContext().getOpPackageName());
            return status;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public void registerMediaButtonEventReceiver(ComponentName eventReceiver) {
        if (eventReceiver == null) {
            return;
        }
        if (!eventReceiver.getPackageName().equals(getContext().getPackageName())) {
            Log.m70e(TAG, "registerMediaButtonEventReceiver() error: receiver and context package names don't match");
            return;
        }
        Intent mediaButtonIntent = new Intent(Intent.ACTION_MEDIA_BUTTON);
        mediaButtonIntent.setComponent(eventReceiver);
        PendingIntent pi = PendingIntent.getBroadcast(getContext(), 0, mediaButtonIntent, 0);
        registerMediaButtonIntent(pi, eventReceiver);
    }

    @Deprecated
    public void registerMediaButtonEventReceiver(PendingIntent eventReceiver) {
        if (eventReceiver == null) {
            return;
        }
        registerMediaButtonIntent(eventReceiver, null);
    }

    public void registerMediaButtonIntent(PendingIntent pi, ComponentName eventReceiver) {
        if (pi == null) {
            Log.m70e(TAG, "Cannot call registerMediaButtonIntent() with a null parameter");
            return;
        }
        MediaSessionLegacyHelper helper = MediaSessionLegacyHelper.getHelper(getContext());
        helper.addMediaButtonListener(pi, eventReceiver, getContext());
    }

    @Deprecated
    public void unregisterMediaButtonEventReceiver(ComponentName eventReceiver) {
        if (eventReceiver == null) {
            return;
        }
        Intent mediaButtonIntent = new Intent(Intent.ACTION_MEDIA_BUTTON);
        mediaButtonIntent.setComponent(eventReceiver);
        PendingIntent pi = PendingIntent.getBroadcast(getContext(), 0, mediaButtonIntent, 0);
        unregisterMediaButtonIntent(pi);
    }

    @Deprecated
    public void unregisterMediaButtonEventReceiver(PendingIntent eventReceiver) {
        if (eventReceiver == null) {
            return;
        }
        unregisterMediaButtonIntent(eventReceiver);
    }

    public void unregisterMediaButtonIntent(PendingIntent pi) {
        MediaSessionLegacyHelper helper = MediaSessionLegacyHelper.getHelper(getContext());
        helper.removeMediaButtonListener(pi);
    }

    @Deprecated
    public void registerRemoteControlClient(RemoteControlClient rcClient) {
        if (rcClient == null || rcClient.getRcMediaIntent() == null) {
            return;
        }
        rcClient.registerWithSession(MediaSessionLegacyHelper.getHelper(getContext()));
    }

    @Deprecated
    public void unregisterRemoteControlClient(RemoteControlClient rcClient) {
        if (rcClient == null || rcClient.getRcMediaIntent() == null) {
            return;
        }
        rcClient.unregisterWithSession(MediaSessionLegacyHelper.getHelper(getContext()));
    }

    @Deprecated
    public boolean registerRemoteController(RemoteController rctlr) {
        if (rctlr == null) {
            return false;
        }
        rctlr.startListeningToSessions();
        return true;
    }

    @Deprecated
    public void unregisterRemoteController(RemoteController rctlr) {
        if (rctlr == null) {
            return;
        }
        rctlr.stopListeningToSessions();
    }

    @SystemApi
    public int registerAudioPolicy(AudioPolicy policy) {
        return registerAudioPolicyStatic(policy);
    }

    static int registerAudioPolicyStatic(AudioPolicy policy) {
        if (policy == null) {
            throw new IllegalArgumentException("Illegal null AudioPolicy argument");
        }
        IAudioService service = getService();
        try {
            MediaProjection projection = policy.getMediaProjection();
            String regId = service.registerAudioPolicy(policy.getConfig(), policy.m111cb(), policy.hasFocusListener(), policy.isFocusPolicy(), policy.isTestFocusPolicy(), policy.isVolumeController(), projection == null ? null : projection.getProjection());
            if (regId == null) {
                return -1;
            }
            policy.setRegistration(regId);
            return 0;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void unregisterAudioPolicyAsync(AudioPolicy policy) {
        unregisterAudioPolicyAsyncStatic(policy);
    }

    static void unregisterAudioPolicyAsyncStatic(AudioPolicy policy) {
        if (policy == null) {
            throw new IllegalArgumentException("Illegal null AudioPolicy argument");
        }
        IAudioService service = getService();
        try {
            service.unregisterAudioPolicyAsync(policy.m111cb());
            policy.setRegistration(null);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void unregisterAudioPolicy(AudioPolicy policy) {
        Preconditions.checkNotNull(policy, "Illegal null AudioPolicy argument");
        IAudioService service = getService();
        try {
            policy.invalidateCaptorsAndInjectors();
            service.unregisterAudioPolicy(policy.m111cb());
            policy.setRegistration(null);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean hasRegisteredDynamicPolicy() {
        IAudioService service = getService();
        try {
            return service.hasRegisteredDynamicPolicy();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* loaded from: classes3.dex */
    public static abstract class AudioPlaybackCallback {
        public void onPlaybackConfigChanged(List<AudioPlaybackConfiguration> configs) {
        }
    }

    /* loaded from: classes3.dex */
    private static class AudioPlaybackCallbackInfo {
        final AudioPlaybackCallback mCb;
        final Handler mHandler;

        AudioPlaybackCallbackInfo(AudioPlaybackCallback cb, Handler handler) {
            this.mCb = cb;
            this.mHandler = handler;
        }
    }

    /* loaded from: classes3.dex */
    private static final class PlaybackConfigChangeCallbackData {
        final AudioPlaybackCallback mCb;
        final List<AudioPlaybackConfiguration> mConfigs;

        PlaybackConfigChangeCallbackData(AudioPlaybackCallback cb, List<AudioPlaybackConfiguration> configs) {
            this.mCb = cb;
            this.mConfigs = configs;
        }
    }

    public void registerAudioPlaybackCallback(AudioPlaybackCallback cb, Handler handler) {
        if (cb == null) {
            throw new IllegalArgumentException("Illegal null AudioPlaybackCallback argument");
        }
        synchronized (this.mPlaybackCallbackLock) {
            if (this.mPlaybackCallbackList == null) {
                this.mPlaybackCallbackList = new ArrayList();
            }
            int oldCbCount = this.mPlaybackCallbackList.size();
            if (!hasPlaybackCallback_sync(cb)) {
                this.mPlaybackCallbackList.add(new AudioPlaybackCallbackInfo(cb, new ServiceEventHandlerDelegate(handler).getHandler()));
                int newCbCount = this.mPlaybackCallbackList.size();
                if (oldCbCount == 0 && newCbCount > 0) {
                    try {
                        getService().registerPlaybackCallback(this.mPlayCb);
                    } catch (RemoteException e) {
                        throw e.rethrowFromSystemServer();
                    }
                }
            } else {
                Log.m64w(TAG, "attempt to call registerAudioPlaybackCallback() on a previouslyregistered callback");
            }
        }
    }

    public void unregisterAudioPlaybackCallback(AudioPlaybackCallback cb) {
        if (cb == null) {
            throw new IllegalArgumentException("Illegal null AudioPlaybackCallback argument");
        }
        synchronized (this.mPlaybackCallbackLock) {
            if (this.mPlaybackCallbackList == null) {
                Log.m64w(TAG, "attempt to call unregisterAudioPlaybackCallback() on a callback that was never registered");
                return;
            }
            int oldCbCount = this.mPlaybackCallbackList.size();
            if (removePlaybackCallback_sync(cb)) {
                int newCbCount = this.mPlaybackCallbackList.size();
                if (oldCbCount > 0 && newCbCount == 0) {
                    try {
                        getService().unregisterPlaybackCallback(this.mPlayCb);
                    } catch (RemoteException e) {
                        throw e.rethrowFromSystemServer();
                    }
                }
            } else {
                Log.m64w(TAG, "attempt to call unregisterAudioPlaybackCallback() on a callback already unregistered or never registered");
            }
        }
    }

    public List<AudioPlaybackConfiguration> getActivePlaybackConfigurations() {
        IAudioService service = getService();
        try {
            return service.getActivePlaybackConfigurations();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private boolean hasPlaybackCallback_sync(AudioPlaybackCallback cb) {
        if (this.mPlaybackCallbackList != null) {
            for (int i = 0; i < this.mPlaybackCallbackList.size(); i++) {
                if (cb.equals(this.mPlaybackCallbackList.get(i).mCb)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean removePlaybackCallback_sync(AudioPlaybackCallback cb) {
        if (this.mPlaybackCallbackList != null) {
            for (int i = 0; i < this.mPlaybackCallbackList.size(); i++) {
                if (cb.equals(this.mPlaybackCallbackList.get(i).mCb)) {
                    this.mPlaybackCallbackList.remove(i);
                    return true;
                }
            }
        }
        return false;
    }

    /* loaded from: classes3.dex */
    public static abstract class AudioRecordingCallback {
        public void onRecordingConfigChanged(List<AudioRecordingConfiguration> configs) {
        }
    }

    /* loaded from: classes3.dex */
    private static class AudioRecordingCallbackInfo {
        final AudioRecordingCallback mCb;
        final Handler mHandler;

        AudioRecordingCallbackInfo(AudioRecordingCallback cb, Handler handler) {
            this.mCb = cb;
            this.mHandler = handler;
        }
    }

    /* loaded from: classes3.dex */
    private static final class RecordConfigChangeCallbackData {
        final AudioRecordingCallback mCb;
        final List<AudioRecordingConfiguration> mConfigs;

        RecordConfigChangeCallbackData(AudioRecordingCallback cb, List<AudioRecordingConfiguration> configs) {
            this.mCb = cb;
            this.mConfigs = configs;
        }
    }

    public void registerAudioRecordingCallback(AudioRecordingCallback cb, Handler handler) {
        if (cb == null) {
            throw new IllegalArgumentException("Illegal null AudioRecordingCallback argument");
        }
        synchronized (this.mRecordCallbackLock) {
            if (this.mRecordCallbackList == null) {
                this.mRecordCallbackList = new ArrayList();
            }
            int oldCbCount = this.mRecordCallbackList.size();
            if (!hasRecordCallback_sync(cb)) {
                this.mRecordCallbackList.add(new AudioRecordingCallbackInfo(cb, new ServiceEventHandlerDelegate(handler).getHandler()));
                int newCbCount = this.mRecordCallbackList.size();
                if (oldCbCount == 0 && newCbCount > 0) {
                    IAudioService service = getService();
                    try {
                        service.registerRecordingCallback(this.mRecCb);
                    } catch (RemoteException e) {
                        throw e.rethrowFromSystemServer();
                    }
                }
            } else {
                Log.m64w(TAG, "attempt to call registerAudioRecordingCallback() on a previouslyregistered callback");
            }
        }
    }

    public void unregisterAudioRecordingCallback(AudioRecordingCallback cb) {
        if (cb == null) {
            throw new IllegalArgumentException("Illegal null AudioRecordingCallback argument");
        }
        synchronized (this.mRecordCallbackLock) {
            if (this.mRecordCallbackList == null) {
                return;
            }
            int oldCbCount = this.mRecordCallbackList.size();
            if (removeRecordCallback_sync(cb)) {
                int newCbCount = this.mRecordCallbackList.size();
                if (oldCbCount > 0 && newCbCount == 0) {
                    IAudioService service = getService();
                    try {
                        service.unregisterRecordingCallback(this.mRecCb);
                    } catch (RemoteException e) {
                        throw e.rethrowFromSystemServer();
                    }
                }
            } else {
                Log.m64w(TAG, "attempt to call unregisterAudioRecordingCallback() on a callback already unregistered or never registered");
            }
        }
    }

    public List<AudioRecordingConfiguration> getActiveRecordingConfigurations() {
        IAudioService service = getService();
        try {
            return service.getActiveRecordingConfigurations();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private boolean hasRecordCallback_sync(AudioRecordingCallback cb) {
        if (this.mRecordCallbackList != null) {
            for (int i = 0; i < this.mRecordCallbackList.size(); i++) {
                if (cb.equals(this.mRecordCallbackList.get(i).mCb)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean removeRecordCallback_sync(AudioRecordingCallback cb) {
        if (this.mRecordCallbackList != null) {
            for (int i = 0; i < this.mRecordCallbackList.size(); i++) {
                if (cb.equals(this.mRecordCallbackList.get(i).mCb)) {
                    this.mRecordCallbackList.remove(i);
                    return true;
                }
            }
        }
        return false;
    }

    @UnsupportedAppUsage
    public void reloadAudioSettings() {
        IAudioService service = getService();
        try {
            service.reloadAudioSettings();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void avrcpSupportsAbsoluteVolume(String address, boolean support) {
        IAudioService service = getService();
        try {
            service.avrcpSupportsAbsoluteVolume(address, support);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    public boolean isSilentMode() {
        int ringerMode = getRingerMode();
        return ringerMode == 0 || ringerMode == 1;
    }

    public static boolean isOutputDevice(int device) {
        return (Integer.MIN_VALUE & device) == 0;
    }

    public static boolean isInputDevice(int device) {
        return (device & Integer.MIN_VALUE) == Integer.MIN_VALUE;
    }

    @UnsupportedAppUsage
    public int getDevicesForStream(int streamType) {
        if (streamType != 8 && streamType != 10) {
            switch (streamType) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                    break;
                default:
                    return 0;
            }
        }
        return AudioSystem.getDevicesForStream(streamType);
    }

    @UnsupportedAppUsage
    public void setWiredDeviceConnectionState(int type, int state, String address, String name) {
        IAudioService service = getService();
        try {
            service.setWiredDeviceConnectionState(type, state, address, name, this.mApplicationContext.getOpPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setBluetoothHearingAidDeviceConnectionState(BluetoothDevice device, int state, boolean suppressNoisyIntent, int musicDevice) {
        IAudioService service = getService();
        try {
            service.setBluetoothHearingAidDeviceConnectionState(device, state, suppressNoisyIntent, musicDevice);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setBluetoothA2dpDeviceConnectionStateSuppressNoisyIntent(BluetoothDevice device, int state, int profile, boolean suppressNoisyIntent, int a2dpVolume) {
        IAudioService service = getService();
        try {
            service.setBluetoothA2dpDeviceConnectionStateSuppressNoisyIntent(device, state, profile, suppressNoisyIntent, a2dpVolume);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void handleBluetoothA2dpDeviceConfigChange(BluetoothDevice device) {
        IAudioService service = getService();
        try {
            service.handleBluetoothA2dpDeviceConfigChange(device);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void handleBluetoothA2dpActiveDeviceChange(BluetoothDevice device, int state, int profile, boolean suppressNoisyIntent, int a2dpVolume) {
        IAudioService service = getService();
        try {
            service.handleBluetoothA2dpActiveDeviceChange(device, state, profile, suppressNoisyIntent, a2dpVolume);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public IRingtonePlayer getRingtonePlayer() {
        try {
            return getService().getRingtonePlayer();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String getProperty(String key) {
        if (PROPERTY_OUTPUT_SAMPLE_RATE.equals(key)) {
            int outputSampleRate = AudioSystem.getPrimaryOutputSamplingRate();
            if (outputSampleRate > 0) {
                return Integer.toString(outputSampleRate);
            }
            return null;
        } else if (PROPERTY_OUTPUT_FRAMES_PER_BUFFER.equals(key)) {
            int outputFramesPerBuffer = AudioSystem.getPrimaryOutputFrameCount();
            if (outputFramesPerBuffer > 0) {
                return Integer.toString(outputFramesPerBuffer);
            }
            return null;
        } else if (PROPERTY_SUPPORT_MIC_NEAR_ULTRASOUND.equals(key)) {
            return String.valueOf(getContext().getResources().getBoolean(C3132R.bool.config_supportMicNearUltrasound));
        } else {
            if (PROPERTY_SUPPORT_SPEAKER_NEAR_ULTRASOUND.equals(key)) {
                return String.valueOf(getContext().getResources().getBoolean(C3132R.bool.config_supportSpeakerNearUltrasound));
            }
            if (PROPERTY_SUPPORT_AUDIO_SOURCE_UNPROCESSED.equals(key)) {
                return String.valueOf(getContext().getResources().getBoolean(C3132R.bool.config_supportAudioSourceUnprocessed));
            }
            return null;
        }
    }

    @UnsupportedAppUsage
    public int getOutputLatency(int streamType) {
        return AudioSystem.getOutputLatency(streamType);
    }

    public void setVolumeController(IVolumeController controller) {
        try {
            getService().setVolumeController(controller);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyVolumeControllerVisible(IVolumeController controller, boolean visible) {
        try {
            getService().notifyVolumeControllerVisible(controller, visible);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isStreamAffectedByRingerMode(int streamType) {
        try {
            return getService().isStreamAffectedByRingerMode(streamType);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isStreamAffectedByMute(int streamType) {
        try {
            return getService().isStreamAffectedByMute(streamType);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void disableSafeMediaVolume() {
        try {
            getService().disableSafeMediaVolume(this.mApplicationContext.getOpPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    public void setRingerModeInternal(int ringerMode) {
        try {
            getService().setRingerModeInternal(ringerMode, getContext().getOpPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    public int getRingerModeInternal() {
        try {
            return getService().getRingerModeInternal();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setVolumePolicy(VolumePolicy policy) {
        try {
            getService().setVolumePolicy(policy);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int setHdmiSystemAudioSupported(boolean on) {
        try {
            return getService().setHdmiSystemAudioSupported(on);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    @SuppressLint({"Doclava125"})
    public boolean isHdmiSystemAudioSupported() {
        try {
            return getService().isHdmiSystemAudioSupported();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    public static int listAudioPorts(ArrayList<AudioPort> ports) {
        return updateAudioPortCache(ports, null, null);
    }

    public static int listPreviousAudioPorts(ArrayList<AudioPort> ports) {
        return updateAudioPortCache(null, null, ports);
    }

    public static int listAudioDevicePorts(ArrayList<AudioDevicePort> devices) {
        if (devices == null) {
            return -2;
        }
        ArrayList<AudioPort> ports = new ArrayList<>();
        int status = updateAudioPortCache(ports, null, null);
        if (status == 0) {
            filterDevicePorts(ports, devices);
        }
        return status;
    }

    public static int listPreviousAudioDevicePorts(ArrayList<AudioDevicePort> devices) {
        if (devices == null) {
            return -2;
        }
        ArrayList<AudioPort> ports = new ArrayList<>();
        int status = updateAudioPortCache(null, null, ports);
        if (status == 0) {
            filterDevicePorts(ports, devices);
        }
        return status;
    }

    private static void filterDevicePorts(ArrayList<AudioPort> ports, ArrayList<AudioDevicePort> devices) {
        devices.clear();
        for (int i = 0; i < ports.size(); i++) {
            if (ports.get(i) instanceof AudioDevicePort) {
                devices.add((AudioDevicePort) ports.get(i));
            }
        }
    }

    @UnsupportedAppUsage
    public static int createAudioPatch(AudioPatch[] patch, AudioPortConfig[] sources, AudioPortConfig[] sinks) {
        return AudioSystem.createAudioPatch(patch, sources, sinks);
    }

    @UnsupportedAppUsage
    public static int releaseAudioPatch(AudioPatch patch) {
        return AudioSystem.releaseAudioPatch(patch);
    }

    @UnsupportedAppUsage
    public static int listAudioPatches(ArrayList<AudioPatch> patches) {
        return updateAudioPortCache(null, patches, null);
    }

    public static int setAudioPortGain(AudioPort port, AudioGainConfig gain) {
        if (port == null || gain == null) {
            return -2;
        }
        AudioPortConfig activeConfig = port.activeConfig();
        AudioPortConfig config = new AudioPortConfig(port, activeConfig.samplingRate(), activeConfig.channelMask(), activeConfig.format(), gain);
        config.mConfigMask = 8;
        return AudioSystem.setAudioPortConfig(config);
    }

    @UnsupportedAppUsage
    public void registerAudioPortUpdateListener(OnAudioPortUpdateListener l) {
        sAudioPortEventHandler.init();
        sAudioPortEventHandler.registerListener(l);
    }

    @UnsupportedAppUsage
    public void unregisterAudioPortUpdateListener(OnAudioPortUpdateListener l) {
        sAudioPortEventHandler.unregisterListener(l);
    }

    static int resetAudioPortGeneration() {
        int generation;
        synchronized (sAudioPortGeneration) {
            generation = sAudioPortGeneration.intValue();
            sAudioPortGeneration = 0;
        }
        return generation;
    }

    /* JADX WARN: Code restructure failed: missing block: B:22:0x005b, code lost:
        if (r6[0] == r0[0]) goto L23;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x005f, code lost:
        return -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0060, code lost:
        r10 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0065, code lost:
        if (r10 >= r8.size()) goto L37;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0067, code lost:
        r11 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x0073, code lost:
        if (r11 >= r8.get(r10).sources().length) goto L30;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x0075, code lost:
        r12 = updatePortConfig(r8.get(r10).sources()[r11], r7);
        r8.get(r10).sources()[r11] = r12;
        r11 = r11 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0094, code lost:
        r11 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x00a0, code lost:
        if (r11 >= r8.get(r10).sinks().length) goto L35;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x00a2, code lost:
        r12 = updatePortConfig(r8.get(r10).sinks()[r11], r7);
        r8.get(r10).sinks()[r11] = r12;
        r11 = r11 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x00c1, code lost:
        r10 = r10 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x00c4, code lost:
        r10 = r8.iterator();
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x00cc, code lost:
        if (r10.hasNext() == false) goto L62;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x00ce, code lost:
        r11 = r10.next();
        r12 = false;
        r13 = r11.sources();
        r14 = r13.length;
        r15 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x00db, code lost:
        if (r15 >= r14) goto L61;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x00dd, code lost:
        r16 = r13[r15];
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x00df, code lost:
        if (r16 != null) goto L45;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x00e1, code lost:
        r12 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x00e3, code lost:
        r15 = r15 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x00e6, code lost:
        r13 = r11.sinks();
        r14 = r13.length;
        r15 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x00ec, code lost:
        if (r15 >= r14) goto L60;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x00ee, code lost:
        r16 = r13[r15];
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x00f0, code lost:
        if (r16 != null) goto L52;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x00f2, code lost:
        r12 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x00f4, code lost:
        r15 = r15 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x00f7, code lost:
        if (r12 == false) goto L59;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x00f9, code lost:
        r10.remove();
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x00fd, code lost:
        android.media.AudioManager.sPreviousAudioPortsCached = android.media.AudioManager.sAudioPortsCached;
        android.media.AudioManager.sAudioPortsCached = r7;
        android.media.AudioManager.sAudioPatchesCached = r8;
        android.media.AudioManager.sAudioPortGeneration = java.lang.Integer.valueOf(r0[0]);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    static int updateAudioPortCache(ArrayList<AudioPort> ports, ArrayList<AudioPatch> patches, ArrayList<AudioPort> previousPorts) {
        sAudioPortEventHandler.init();
        synchronized (sAudioPortGeneration) {
            if (sAudioPortGeneration.intValue() == 0) {
                int[] patchGeneration = new int[1];
                int[] portGeneration = new int[1];
                ArrayList<AudioPort> newPorts = new ArrayList<>();
                ArrayList<AudioPatch> newPatches = new ArrayList<>();
                while (true) {
                    newPorts.clear();
                    int status = AudioSystem.listAudioPorts(newPorts, portGeneration);
                    if (status != 0) {
                        Log.m64w(TAG, "updateAudioPortCache: listAudioPorts failed");
                        return status;
                    }
                    newPatches.clear();
                    int status2 = AudioSystem.listAudioPatches(newPatches, patchGeneration);
                    if (status2 != 0) {
                        Log.m64w(TAG, "updateAudioPortCache: listAudioPatches failed");
                        return status2;
                    } else if (patchGeneration[0] == portGeneration[0] || (ports != null && patches != null)) {
                        break;
                    }
                }
            }
            if (ports != null) {
                ports.clear();
                ports.addAll(sAudioPortsCached);
            }
            if (patches != null) {
                patches.clear();
                patches.addAll(sAudioPatchesCached);
            }
            if (previousPorts != null) {
                previousPorts.clear();
                previousPorts.addAll(sPreviousAudioPortsCached);
            }
            return 0;
        }
    }

    static AudioPortConfig updatePortConfig(AudioPortConfig portCfg, ArrayList<AudioPort> ports) {
        AudioPort port = portCfg.port();
        int k = 0;
        while (true) {
            if (k >= ports.size()) {
                break;
            } else if (!ports.get(k).handle().equals(port.handle())) {
                k++;
            } else {
                port = ports.get(k);
                break;
            }
        }
        if (k == ports.size()) {
            Log.m70e(TAG, "updatePortConfig port not found for handle: " + port.handle().m116id());
            return null;
        }
        AudioGainConfig gainCfg = portCfg.gain();
        if (gainCfg != null) {
            AudioGain gain = port.gain(gainCfg.index());
            gainCfg = gain.buildConfig(gainCfg.mode(), gainCfg.channelMask(), gainCfg.values(), gainCfg.rampDurationMs());
        }
        return port.buildConfig(portCfg.samplingRate(), portCfg.channelMask(), portCfg.format(), gainCfg);
    }

    private static boolean checkFlags(AudioDevicePort port, int flags) {
        if (port.role() != 2 || (flags & 2) == 0) {
            return port.role() == 1 && (flags & 1) != 0;
        }
        return true;
    }

    private static boolean checkTypes(AudioDevicePort port) {
        return AudioDeviceInfo.convertInternalDeviceToDeviceType(port.type()) != 0;
    }

    public AudioDeviceInfo[] getDevices(int flags) {
        return getDevicesStatic(flags);
    }

    private static AudioDeviceInfo[] infoListFromPortList(ArrayList<AudioDevicePort> ports, int flags) {
        int numRecs = 0;
        Iterator<AudioDevicePort> it = ports.iterator();
        while (it.hasNext()) {
            AudioDevicePort port = it.next();
            if (checkTypes(port) && checkFlags(port, flags)) {
                numRecs++;
            }
        }
        AudioDeviceInfo[] deviceList = new AudioDeviceInfo[numRecs];
        int slot = 0;
        Iterator<AudioDevicePort> it2 = ports.iterator();
        while (it2.hasNext()) {
            AudioDevicePort port2 = it2.next();
            if (checkTypes(port2) && checkFlags(port2, flags)) {
                deviceList[slot] = new AudioDeviceInfo(port2);
                slot++;
            }
        }
        return deviceList;
    }

    private static AudioDeviceInfo[] calcListDeltas(ArrayList<AudioDevicePort> ports_A, ArrayList<AudioDevicePort> ports_B, int flags) {
        ArrayList<AudioDevicePort> delta_ports = new ArrayList<>();
        for (int cur_index = 0; cur_index < ports_B.size(); cur_index++) {
            AudioDevicePort cur_port = ports_B.get(cur_index);
            boolean cur_port_found = false;
            for (int prev_index = 0; prev_index < ports_A.size() && !cur_port_found; prev_index++) {
                cur_port_found = cur_port.m114id() == ports_A.get(prev_index).m114id();
            }
            if (!cur_port_found) {
                delta_ports.add(cur_port);
            }
        }
        return infoListFromPortList(delta_ports, flags);
    }

    public static AudioDeviceInfo[] getDevicesStatic(int flags) {
        ArrayList<AudioDevicePort> ports = new ArrayList<>();
        int status = listAudioDevicePorts(ports);
        if (status != 0) {
            return new AudioDeviceInfo[0];
        }
        return infoListFromPortList(ports, flags);
    }

    public void registerAudioDeviceCallback(AudioDeviceCallback callback, Handler handler) {
        synchronized (this.mDeviceCallbacks) {
            if (callback != null) {
                try {
                    if (!this.mDeviceCallbacks.containsKey(callback)) {
                        if (this.mDeviceCallbacks.size() == 0) {
                            if (this.mPortListener == null) {
                                this.mPortListener = new OnAmPortUpdateListener();
                            }
                            registerAudioPortUpdateListener(this.mPortListener);
                        }
                        NativeEventHandlerDelegate delegate = new NativeEventHandlerDelegate(callback, handler);
                        this.mDeviceCallbacks.put(callback, delegate);
                        broadcastDeviceListChange_sync(delegate.getHandler());
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public void unregisterAudioDeviceCallback(AudioDeviceCallback callback) {
        synchronized (this.mDeviceCallbacks) {
            if (this.mDeviceCallbacks.containsKey(callback)) {
                this.mDeviceCallbacks.remove(callback);
                if (this.mDeviceCallbacks.size() == 0) {
                    unregisterAudioPortUpdateListener(this.mPortListener);
                }
            }
        }
    }

    public static void setPortIdForMicrophones(ArrayList<MicrophoneInfo> microphones) {
        AudioDeviceInfo[] devices = getDevicesStatic(1);
        int i = microphones.size() - 1;
        while (true) {
            int i2 = i;
            if (i2 >= 0) {
                boolean foundPortId = false;
                int length = devices.length;
                int i3 = 0;
                while (true) {
                    if (i3 >= length) {
                        break;
                    }
                    AudioDeviceInfo device = devices[i3];
                    if (device.getPort().type() != microphones.get(i2).getInternalDeviceType() || !TextUtils.equals(device.getAddress(), microphones.get(i2).getAddress())) {
                        i3++;
                    } else {
                        microphones.get(i2).setId(device.getId());
                        foundPortId = true;
                        break;
                    }
                }
                if (!foundPortId) {
                    Log.m68i(TAG, "Failed to find port id for device with type:" + microphones.get(i2).getType() + " address:" + microphones.get(i2).getAddress());
                    microphones.remove(i2);
                }
                i = i2 - 1;
            } else {
                return;
            }
        }
    }

    public static MicrophoneInfo microphoneInfoFromAudioDeviceInfo(AudioDeviceInfo deviceInfo) {
        int i;
        int deviceType = deviceInfo.getType();
        if (deviceType == 15 || deviceType == 18) {
            i = 1;
        } else {
            i = deviceType == 0 ? 0 : 3;
        }
        int micLocation = i;
        MicrophoneInfo microphone = new MicrophoneInfo(deviceInfo.getPort().name() + deviceInfo.getId(), deviceInfo.getPort().type(), deviceInfo.getAddress(), micLocation, -1, -1, MicrophoneInfo.POSITION_UNKNOWN, MicrophoneInfo.ORIENTATION_UNKNOWN, new ArrayList(), new ArrayList(), -3.4028235E38f, -3.4028235E38f, -3.4028235E38f, 0);
        microphone.setId(deviceInfo.getId());
        return microphone;
    }

    private void addMicrophonesFromAudioDeviceInfo(ArrayList<MicrophoneInfo> microphones, HashSet<Integer> filterTypes) {
        AudioDeviceInfo[] devices = getDevicesStatic(1);
        for (AudioDeviceInfo device : devices) {
            if (!filterTypes.contains(Integer.valueOf(device.getType()))) {
                MicrophoneInfo microphone = microphoneInfoFromAudioDeviceInfo(device);
                microphones.add(microphone);
            }
        }
    }

    public List<MicrophoneInfo> getMicrophones() throws IOException {
        ArrayList<MicrophoneInfo> microphones = new ArrayList<>();
        int status = AudioSystem.getMicrophones(microphones);
        HashSet<Integer> filterTypes = new HashSet<>();
        filterTypes.add(18);
        if (status != 0) {
            if (status != -3) {
                Log.m70e(TAG, "getMicrophones failed:" + status);
            }
            Log.m68i(TAG, "fallback on device info");
            addMicrophonesFromAudioDeviceInfo(microphones, filterTypes);
            return microphones;
        }
        setPortIdForMicrophones(microphones);
        filterTypes.add(15);
        addMicrophonesFromAudioDeviceInfo(microphones, filterTypes);
        return microphones;
    }

    public List<BluetoothCodecConfig> getHwOffloadEncodingFormatsSupportedForA2DP() {
        ArrayList<Integer> formatsList = new ArrayList<>();
        ArrayList<BluetoothCodecConfig> codecConfigList = new ArrayList<>();
        int status = AudioSystem.getHwOffloadEncodingFormatsSupportedForA2DP(formatsList);
        if (status != 0) {
            Log.m70e(TAG, "getHwOffloadEncodingFormatsSupportedForA2DP failed:" + status);
            return codecConfigList;
        }
        Iterator<Integer> it = formatsList.iterator();
        while (it.hasNext()) {
            Integer format = it.next();
            int btSourceCodec = AudioSystem.audioFormatToBluetoothSourceCodec(format.intValue());
            if (btSourceCodec != 1000000) {
                codecConfigList.add(new BluetoothCodecConfig(btSourceCodec));
            }
        }
        return codecConfigList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void broadcastDeviceListChange_sync(Handler handler) {
        ArrayList<AudioDevicePort> current_ports = new ArrayList<>();
        int status = listAudioDevicePorts(current_ports);
        if (status != 0) {
            return;
        }
        if (handler != null) {
            AudioDeviceInfo[] deviceList = infoListFromPortList(current_ports, 3);
            handler.sendMessage(Message.obtain(handler, 0, deviceList));
        } else {
            AudioDeviceInfo[] added_devices = calcListDeltas(this.mPreviousPorts, current_ports, 3);
            AudioDeviceInfo[] removed_devices = calcListDeltas(current_ports, this.mPreviousPorts, 3);
            if (added_devices.length != 0 || removed_devices.length != 0) {
                for (int i = 0; i < this.mDeviceCallbacks.size(); i++) {
                    Handler handler2 = this.mDeviceCallbacks.valueAt(i).getHandler();
                    if (handler2 != null) {
                        if (removed_devices.length != 0) {
                            handler2.sendMessage(Message.obtain(handler2, 2, removed_devices));
                        }
                        if (added_devices.length != 0) {
                            handler2.sendMessage(Message.obtain(handler2, 1, added_devices));
                        }
                    }
                }
            }
        }
        this.mPreviousPorts = current_ports;
    }

    /* loaded from: classes3.dex */
    private class OnAmPortUpdateListener implements OnAudioPortUpdateListener {
        static final String TAG = "OnAmPortUpdateListener";

        private OnAmPortUpdateListener() {
        }

        @Override // android.media.AudioManager.OnAudioPortUpdateListener
        public void onAudioPortListUpdate(AudioPort[] portList) {
            synchronized (AudioManager.this.mDeviceCallbacks) {
                AudioManager.this.broadcastDeviceListChange_sync(null);
            }
        }

        @Override // android.media.AudioManager.OnAudioPortUpdateListener
        public void onAudioPatchListUpdate(AudioPatch[] patchList) {
        }

        @Override // android.media.AudioManager.OnAudioPortUpdateListener
        public void onServiceDied() {
            synchronized (AudioManager.this.mDeviceCallbacks) {
                AudioManager.this.broadcastDeviceListChange_sync(null);
            }
        }
    }

    @SystemApi
    /* loaded from: classes3.dex */
    public static abstract class AudioServerStateCallback {
        public void onAudioServerDown() {
        }

        public void onAudioServerUp() {
        }
    }

    /* renamed from: android.media.AudioManager$4 */
    /* loaded from: classes3.dex */
    class BinderC10404 extends IAudioServerStateDispatcher.Stub {
        BinderC10404() {
        }

        @Override // android.media.IAudioServerStateDispatcher
        public void dispatchAudioServerStateChange(boolean state) {
            Executor exec;
            final AudioServerStateCallback cb;
            synchronized (AudioManager.this.mAudioServerStateCbLock) {
                exec = AudioManager.this.mAudioServerStateExec;
                cb = AudioManager.this.mAudioServerStateCb;
            }
            if (exec == null || cb == null) {
                return;
            }
            if (state) {
                exec.execute(new Runnable() { // from class: android.media.-$$Lambda$AudioManager$4$Q85LmhgKDCoq1YI14giFabZrM7A
                    @Override // java.lang.Runnable
                    public final void run() {
                        AudioManager.AudioServerStateCallback.this.onAudioServerUp();
                    }
                });
            } else {
                exec.execute(new Runnable() { // from class: android.media.-$$Lambda$AudioManager$4$7k7uSoMGULBCueASQSmf9jAil7I
                    @Override // java.lang.Runnable
                    public final void run() {
                        AudioManager.AudioServerStateCallback.this.onAudioServerDown();
                    }
                });
            }
        }
    }

    @SystemApi
    public void setAudioServerStateCallback(Executor executor, AudioServerStateCallback stateCallback) {
        if (stateCallback == null) {
            throw new IllegalArgumentException("Illegal null AudioServerStateCallback");
        }
        if (executor == null) {
            throw new IllegalArgumentException("Illegal null Executor for the AudioServerStateCallback");
        }
        synchronized (this.mAudioServerStateCbLock) {
            if (this.mAudioServerStateCb != null) {
                throw new IllegalStateException("setAudioServerStateCallback called with already registered callabck");
            }
            IAudioService service = getService();
            try {
                service.registerAudioServerStateDispatcher(this.mAudioServerStateDispatcher);
                this.mAudioServerStateExec = executor;
                this.mAudioServerStateCb = stateCallback;
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    @SystemApi
    public void clearAudioServerStateCallback() {
        synchronized (this.mAudioServerStateCbLock) {
            if (this.mAudioServerStateCb != null) {
                IAudioService service = getService();
                try {
                    service.unregisterAudioServerStateDispatcher(this.mAudioServerStateDispatcher);
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
            this.mAudioServerStateExec = null;
            this.mAudioServerStateCb = null;
        }
    }

    @SystemApi
    public boolean isAudioServerRunning() {
        IAudioService service = getService();
        try {
            return service.isAudioServerRunning();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public Map<Integer, Boolean> getSurroundFormats() {
        Map<Integer, Boolean> surroundFormats = new HashMap<>();
        int status = AudioSystem.getSurroundFormats(surroundFormats, false);
        if (status != 0) {
            Log.m70e(TAG, "getSurroundFormats failed:" + status);
            return new HashMap();
        }
        return surroundFormats;
    }

    public boolean setSurroundFormatEnabled(int audioFormat, boolean enabled) {
        int status = AudioSystem.setSurroundFormatEnabled(audioFormat, enabled);
        return status == 0;
    }

    public Map<Integer, Boolean> getReportedSurroundFormats() {
        Map<Integer, Boolean> reportedSurroundFormats = new HashMap<>();
        int status = AudioSystem.getSurroundFormats(reportedSurroundFormats, true);
        if (status != 0) {
            Log.m70e(TAG, "getReportedSurroundFormats failed:" + status);
            return new HashMap();
        }
        return reportedSurroundFormats;
    }

    public static boolean isHapticPlaybackSupported() {
        return AudioSystem.isHapticPlaybackSupported();
    }

    @SystemApi
    public static List<AudioProductStrategy> getAudioProductStrategies() {
        IAudioService service = getService();
        try {
            return service.getAudioProductStrategies();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public static List<AudioVolumeGroup> getAudioVolumeGroups() {
        IAudioService service = getService();
        try {
            return service.getAudioVolumeGroups();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    /* loaded from: classes3.dex */
    public static abstract class VolumeGroupCallback {
        public void onAudioVolumeGroupChanged(int group, int flags) {
        }
    }

    @SystemApi
    public void registerVolumeGroupCallback(Executor executor, VolumeGroupCallback callback) {
        Preconditions.checkNotNull(executor, "executor must not be null");
        Preconditions.checkNotNull(callback, "volume group change cb must not be null");
        sAudioAudioVolumeGroupChangedHandler.init();
        sAudioAudioVolumeGroupChangedHandler.registerListener(callback);
    }

    @SystemApi
    public void unregisterVolumeGroupCallback(VolumeGroupCallback callback) {
        Preconditions.checkNotNull(callback, "volume group change cb must not be null");
        sAudioAudioVolumeGroupChangedHandler.unregisterListener(callback);
    }

    public static boolean hasHapticChannels(Uri uri) {
        try {
            return getService().hasHapticChannels(uri);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* loaded from: classes3.dex */
    private class NativeEventHandlerDelegate {
        private final Handler mHandler;

        NativeEventHandlerDelegate(final AudioDeviceCallback callback, Handler handler) {
            Looper looper;
            if (handler != null) {
                looper = handler.getLooper();
            } else {
                looper = Looper.getMainLooper();
            }
            if (looper != null) {
                this.mHandler = new Handler(looper) { // from class: android.media.AudioManager.NativeEventHandlerDelegate.1
                    @Override // android.p007os.Handler
                    public void handleMessage(Message msg) {
                        switch (msg.what) {
                            case 0:
                            case 1:
                                if (callback != null) {
                                    callback.onAudioDevicesAdded((AudioDeviceInfo[]) msg.obj);
                                    return;
                                }
                                return;
                            case 2:
                                if (callback != null) {
                                    callback.onAudioDevicesRemoved((AudioDeviceInfo[]) msg.obj);
                                    return;
                                }
                                return;
                            default:
                                Log.m70e(AudioManager.TAG, "Unknown native event type: " + msg.what);
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
}
