package android.media;

import android.annotation.UnsupportedAppUsage;
import android.app.ActivityThread;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioAttributes;
import android.media.AudioRouting;
import android.media.MediaDrm;
import android.media.MediaTimeProvider;
import android.media.SubtitleController;
import android.media.SubtitleTrack;
import android.media.VolumeShaper;
import android.net.Uri;
import android.net.wifi.WifiEnterpriseConfig;
import android.p007os.Environment;
import android.p007os.Handler;
import android.p007os.HandlerThread;
import android.p007os.IBinder;
import android.p007os.Looper;
import android.p007os.Message;
import android.p007os.Parcel;
import android.p007os.Parcelable;
import android.p007os.PersistableBundle;
import android.p007os.PowerManager;
import android.p007os.SystemProperties;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.telephony.SmsManager;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Pair;
import android.util.TimeUtils;
import android.view.Surface;
import android.view.SurfaceHolder;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.telephony.IccCardConstants;
import com.android.internal.util.Preconditions;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URL;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.UUID;
import java.util.Vector;
import libcore.io.IoBridge;
import libcore.io.Streams;

/* loaded from: classes3.dex */
public class MediaPlayer extends PlayerBase implements SubtitleController.Listener, VolumeAutomation, AudioRouting {
    public static final boolean APPLY_METADATA_FILTER = true;
    @UnsupportedAppUsage
    public static final boolean BYPASS_METADATA_FILTER = false;
    private static final String IMEDIA_PLAYER = "android.media.IMediaPlayer";
    private static final int INVOKE_ID_ADD_EXTERNAL_SOURCE = 2;
    private static final int INVOKE_ID_ADD_EXTERNAL_SOURCE_FD = 3;
    private static final int INVOKE_ID_DESELECT_TRACK = 5;
    private static final int INVOKE_ID_GET_SELECTED_TRACK = 7;
    private static final int INVOKE_ID_GET_TRACK_INFO = 1;
    private static final int INVOKE_ID_SELECT_TRACK = 4;
    private static final int INVOKE_ID_SET_VIDEO_SCALE_MODE = 6;
    private static final int KEY_PARAMETER_AUDIO_ATTRIBUTES = 1400;
    private static final int MEDIA_AUDIO_ROUTING_CHANGED = 10000;
    private static final int MEDIA_BUFFERING_UPDATE = 3;
    private static final int MEDIA_DRM_INFO = 210;
    private static final int MEDIA_ERROR = 100;
    public static final int MEDIA_ERROR_IO = -1004;
    public static final int MEDIA_ERROR_MALFORMED = -1007;
    public static final int MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK = 200;
    public static final int MEDIA_ERROR_SERVER_DIED = 100;
    public static final int MEDIA_ERROR_SYSTEM = Integer.MIN_VALUE;
    public static final int MEDIA_ERROR_TIMED_OUT = -110;
    public static final int MEDIA_ERROR_UNKNOWN = 1;
    public static final int MEDIA_ERROR_UNSUPPORTED = -1010;
    private static final int MEDIA_INFO = 200;
    public static final int MEDIA_INFO_AUDIO_NOT_PLAYING = 804;
    public static final int MEDIA_INFO_BAD_INTERLEAVING = 800;
    public static final int MEDIA_INFO_BUFFERING_END = 702;
    public static final int MEDIA_INFO_BUFFERING_START = 701;
    @UnsupportedAppUsage
    public static final int MEDIA_INFO_EXTERNAL_METADATA_UPDATE = 803;
    public static final int MEDIA_INFO_METADATA_UPDATE = 802;
    public static final int MEDIA_INFO_NETWORK_BANDWIDTH = 703;
    public static final int MEDIA_INFO_NOT_SEEKABLE = 801;
    public static final int MEDIA_INFO_STARTED_AS_NEXT = 2;
    public static final int MEDIA_INFO_SUBTITLE_TIMED_OUT = 902;
    @UnsupportedAppUsage
    public static final int MEDIA_INFO_TIMED_TEXT_ERROR = 900;
    public static final int MEDIA_INFO_UNKNOWN = 1;
    public static final int MEDIA_INFO_UNSUPPORTED_SUBTITLE = 901;
    public static final int MEDIA_INFO_VIDEO_NOT_PLAYING = 805;
    public static final int MEDIA_INFO_VIDEO_RENDERING_START = 3;
    public static final int MEDIA_INFO_VIDEO_TRACK_LAGGING = 700;
    private static final int MEDIA_META_DATA = 202;
    public static final String MEDIA_MIMETYPE_TEXT_CEA_608 = "text/cea-608";
    public static final String MEDIA_MIMETYPE_TEXT_CEA_708 = "text/cea-708";
    public static final String MEDIA_MIMETYPE_TEXT_SUBRIP = "application/x-subrip";
    public static final String MEDIA_MIMETYPE_TEXT_VTT = "text/vtt";
    private static final int MEDIA_NOP = 0;
    private static final int MEDIA_NOTIFY_TIME = 98;
    private static final int MEDIA_PAUSED = 7;
    private static final int MEDIA_PLAYBACK_COMPLETE = 2;
    private static final int MEDIA_PREPARED = 1;
    private static final int MEDIA_SEEK_COMPLETE = 4;
    private static final int MEDIA_SET_VIDEO_SIZE = 5;
    private static final int MEDIA_SKIPPED = 9;
    private static final int MEDIA_STARTED = 6;
    private static final int MEDIA_STOPPED = 8;
    private static final int MEDIA_SUBTITLE_DATA = 201;
    private static final int MEDIA_TIMED_TEXT = 99;
    private static final int MEDIA_TIME_DISCONTINUITY = 211;
    @UnsupportedAppUsage
    public static final boolean METADATA_ALL = false;
    public static final boolean METADATA_UPDATE_ONLY = true;
    public static final int PLAYBACK_RATE_AUDIO_MODE_DEFAULT = 0;
    public static final int PLAYBACK_RATE_AUDIO_MODE_RESAMPLE = 2;
    public static final int PLAYBACK_RATE_AUDIO_MODE_STRETCH = 1;
    public static final int PREPARE_DRM_STATUS_PREPARATION_ERROR = 3;
    public static final int PREPARE_DRM_STATUS_PROVISIONING_NETWORK_ERROR = 1;
    public static final int PREPARE_DRM_STATUS_PROVISIONING_SERVER_ERROR = 2;
    public static final int PREPARE_DRM_STATUS_SUCCESS = 0;
    public static final int SEEK_CLOSEST = 3;
    public static final int SEEK_CLOSEST_SYNC = 2;
    public static final int SEEK_NEXT_SYNC = 1;
    public static final int SEEK_PREVIOUS_SYNC = 0;
    private static final String TAG = "MediaPlayer";
    public static final int VIDEO_SCALING_MODE_SCALE_TO_FIT = 1;
    public static final int VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING = 2;
    private boolean mActiveDrmScheme;
    private boolean mDrmConfigAllowed;
    private DrmInfo mDrmInfo;
    private boolean mDrmInfoResolved;
    private final Object mDrmLock;
    private MediaDrm mDrmObj;
    private boolean mDrmProvisioningInProgress;
    private ProvisioningThread mDrmProvisioningThread;
    private byte[] mDrmSessionId;
    private UUID mDrmUUID;
    @UnsupportedAppUsage
    private EventHandler mEventHandler;
    private Handler mExtSubtitleDataHandler;
    private OnSubtitleDataListener mExtSubtitleDataListener;
    private BitSet mInbandTrackIndices;
    private Vector<Pair<Integer, SubtitleTrack>> mIndexTrackPairs;
    private final OnSubtitleDataListener mIntSubtitleDataListener;
    private int mListenerContext;
    private long mNativeContext;
    private long mNativeSurfaceTexture;
    private OnBufferingUpdateListener mOnBufferingUpdateListener;
    private final OnCompletionListener mOnCompletionInternalListener;
    @UnsupportedAppUsage
    private OnCompletionListener mOnCompletionListener;
    private OnDrmConfigHelper mOnDrmConfigHelper;
    private OnDrmInfoHandlerDelegate mOnDrmInfoHandlerDelegate;
    private OnDrmPreparedHandlerDelegate mOnDrmPreparedHandlerDelegate;
    @UnsupportedAppUsage
    private OnErrorListener mOnErrorListener;
    @UnsupportedAppUsage
    private OnInfoListener mOnInfoListener;
    private Handler mOnMediaTimeDiscontinuityHandler;
    private OnMediaTimeDiscontinuityListener mOnMediaTimeDiscontinuityListener;
    @UnsupportedAppUsage
    private OnPreparedListener mOnPreparedListener;
    @UnsupportedAppUsage
    private OnSeekCompleteListener mOnSeekCompleteListener;
    private OnTimedMetaDataAvailableListener mOnTimedMetaDataAvailableListener;
    @UnsupportedAppUsage
    private OnTimedTextListener mOnTimedTextListener;
    private OnVideoSizeChangedListener mOnVideoSizeChangedListener;
    private Vector<InputStream> mOpenSubtitleSources;
    private AudioDeviceInfo mPreferredDevice;
    private boolean mPrepareDrmInProgress;
    @GuardedBy({"mRoutingChangeListeners"})
    private ArrayMap<AudioRouting.OnRoutingChangedListener, NativeRoutingEventHandlerDelegate> mRoutingChangeListeners;
    private boolean mScreenOnWhilePlaying;
    private int mSelectedSubtitleTrackIndex;
    private boolean mStayAwake;
    private int mStreamType;
    private SubtitleController mSubtitleController;
    private boolean mSubtitleDataListenerDisabled;
    private SurfaceHolder mSurfaceHolder;
    private TimeProvider mTimeProvider;
    private final Object mTimeProviderLock;
    private int mUsage;
    private PowerManager.WakeLock mWakeLock;

    /* loaded from: classes3.dex */
    public interface OnBufferingUpdateListener {
        void onBufferingUpdate(MediaPlayer mediaPlayer, int i);
    }

    /* loaded from: classes3.dex */
    public interface OnCompletionListener {
        void onCompletion(MediaPlayer mediaPlayer);
    }

    /* loaded from: classes3.dex */
    public interface OnDrmConfigHelper {
        void onDrmConfig(MediaPlayer mediaPlayer);
    }

    /* loaded from: classes3.dex */
    public interface OnDrmInfoListener {
        void onDrmInfo(MediaPlayer mediaPlayer, DrmInfo drmInfo);
    }

    /* loaded from: classes3.dex */
    public interface OnDrmPreparedListener {
        void onDrmPrepared(MediaPlayer mediaPlayer, int i);
    }

    /* loaded from: classes3.dex */
    public interface OnErrorListener {
        boolean onError(MediaPlayer mediaPlayer, int i, int i2);
    }

    /* loaded from: classes3.dex */
    public interface OnInfoListener {
        boolean onInfo(MediaPlayer mediaPlayer, int i, int i2);
    }

    /* loaded from: classes3.dex */
    public interface OnMediaTimeDiscontinuityListener {
        void onMediaTimeDiscontinuity(MediaPlayer mediaPlayer, MediaTimestamp mediaTimestamp);
    }

    /* loaded from: classes3.dex */
    public interface OnPreparedListener {
        void onPrepared(MediaPlayer mediaPlayer);
    }

    /* loaded from: classes3.dex */
    public interface OnSeekCompleteListener {
        void onSeekComplete(MediaPlayer mediaPlayer);
    }

    /* loaded from: classes3.dex */
    public interface OnSubtitleDataListener {
        void onSubtitleData(MediaPlayer mediaPlayer, SubtitleData subtitleData);
    }

    /* loaded from: classes3.dex */
    public interface OnTimedMetaDataAvailableListener {
        void onTimedMetaDataAvailable(MediaPlayer mediaPlayer, TimedMetaData timedMetaData);
    }

    /* loaded from: classes3.dex */
    public interface OnTimedTextListener {
        void onTimedText(MediaPlayer mediaPlayer, TimedText timedText);
    }

    /* loaded from: classes3.dex */
    public interface OnVideoSizeChangedListener {
        void onVideoSizeChanged(MediaPlayer mediaPlayer, int i, int i2);
    }

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes3.dex */
    public @interface PlaybackRateAudioMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes3.dex */
    public @interface PrepareDrmStatusCode {
    }

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes3.dex */
    public @interface SeekMode {
    }

    private native int _getAudioStreamType() throws IllegalStateException;

    private native void _notifyAt(long j);

    private native void _pause() throws IllegalStateException;

    private native void _prepare() throws IOException, IllegalStateException;

    private native void _prepareDrm(byte[] bArr, byte[] bArr2);

    private native void _release();

    private native void _releaseDrm();

    private native void _reset();

    private final native void _seekTo(long j, int i);

    private native void _setAudioStreamType(int i);

    private native void _setAuxEffectSendLevel(float f);

    private native void _setDataSource(MediaDataSource mediaDataSource) throws IllegalArgumentException, IllegalStateException;

    private native void _setDataSource(FileDescriptor fileDescriptor, long j, long j2) throws IOException, IllegalArgumentException, IllegalStateException;

    private native void _setVideoSurface(Surface surface);

    private native void _setVolume(float f, float f2);

    private native void _start() throws IllegalStateException;

    private native void _stop() throws IllegalStateException;

    private native void nativeSetDataSource(IBinder iBinder, String str, String[] strArr, String[] strArr2) throws IOException, IllegalArgumentException, SecurityException, IllegalStateException;

    private native int native_applyVolumeShaper(VolumeShaper.Configuration configuration, VolumeShaper.Operation operation);

    private final native void native_enableDeviceCallback(boolean z);

    private final native void native_finalize();

    private final native boolean native_getMetadata(boolean z, boolean z2, Parcel parcel);

    private native PersistableBundle native_getMetrics();

    private final native int native_getRoutedDeviceId();

    private native VolumeShaper.State native_getVolumeShaperState(int i);

    private static final native void native_init();

    private final native int native_invoke(Parcel parcel, Parcel parcel2);

    public static native int native_pullBatteryData(Parcel parcel);

    private final native int native_setMetadataFilter(Parcel parcel);

    private final native boolean native_setOutputDevice(int i);

    private final native int native_setRetransmitEndpoint(String str, int i);

    private final native void native_setup(Object obj);

    @UnsupportedAppUsage
    private native boolean setParameter(int i, Parcel parcel);

    public native void attachAuxEffect(int i);

    public native int getAudioSessionId();

    public native int getCurrentPosition();

    public native int getDuration();

    public native PlaybackParams getPlaybackParams();

    public native SyncParams getSyncParams();

    public native int getVideoHeight();

    public native int getVideoWidth();

    public native boolean isLooping();

    public native boolean isPlaying();

    public native void prepareAsync() throws IllegalStateException;

    public native void setAudioSessionId(int i) throws IllegalArgumentException, IllegalStateException;

    public native void setLooping(boolean z);

    public native void setNextMediaPlayer(MediaPlayer mediaPlayer);

    public native void setPlaybackParams(PlaybackParams playbackParams);

    public native void setSyncParams(SyncParams syncParams);

    static {
        System.loadLibrary("media_jni");
        native_init();
    }

    public MediaPlayer() {
        super(new AudioAttributes.Builder().build(), 2);
        this.mWakeLock = null;
        this.mStreamType = Integer.MIN_VALUE;
        this.mUsage = -1;
        this.mDrmLock = new Object();
        this.mPreferredDevice = null;
        this.mRoutingChangeListeners = new ArrayMap<>();
        this.mIndexTrackPairs = new Vector<>();
        this.mInbandTrackIndices = new BitSet();
        this.mSelectedSubtitleTrackIndex = -1;
        this.mIntSubtitleDataListener = new OnSubtitleDataListener() { // from class: android.media.MediaPlayer.3
            @Override // android.media.MediaPlayer.OnSubtitleDataListener
            public void onSubtitleData(MediaPlayer mp, SubtitleData data) {
                int index = data.getTrackIndex();
                synchronized (MediaPlayer.this.mIndexTrackPairs) {
                    Iterator it = MediaPlayer.this.mIndexTrackPairs.iterator();
                    while (it.hasNext()) {
                        Pair<Integer, SubtitleTrack> p = (Pair) it.next();
                        if (p.first != 0 && ((Integer) p.first).intValue() == index && p.second != 0) {
                            SubtitleTrack track = (SubtitleTrack) p.second;
                            track.onData(data);
                        }
                    }
                }
            }
        };
        this.mTimeProviderLock = new Object();
        this.mOnCompletionInternalListener = new OnCompletionListener() { // from class: android.media.MediaPlayer.7
            @Override // android.media.MediaPlayer.OnCompletionListener
            public void onCompletion(MediaPlayer mp) {
                MediaPlayer.this.baseStop();
            }
        };
        Looper looper = Looper.myLooper();
        if (looper != null) {
            this.mEventHandler = new EventHandler(this, looper);
        } else {
            Looper looper2 = Looper.getMainLooper();
            if (looper2 != null) {
                this.mEventHandler = new EventHandler(this, looper2);
            } else {
                this.mEventHandler = null;
            }
        }
        this.mTimeProvider = new TimeProvider(this);
        this.mOpenSubtitleSources = new Vector<>();
        native_setup(new WeakReference(this));
        baseRegisterPlayer();
    }

    @UnsupportedAppUsage
    public Parcel newRequest() {
        Parcel parcel = Parcel.obtain();
        parcel.writeInterfaceToken(IMEDIA_PLAYER);
        return parcel;
    }

    @UnsupportedAppUsage
    public void invoke(Parcel request, Parcel reply) {
        int retcode = native_invoke(request, reply);
        reply.setDataPosition(0);
        if (retcode != 0) {
            throw new RuntimeException("failure code: " + retcode);
        }
    }

    public void setDisplay(SurfaceHolder sh) {
        Surface surface;
        this.mSurfaceHolder = sh;
        if (sh != null) {
            surface = sh.getSurface();
        } else {
            surface = null;
        }
        _setVideoSurface(surface);
        updateSurfaceScreenOn();
    }

    public void setSurface(Surface surface) {
        if (this.mScreenOnWhilePlaying && surface != null) {
            Log.m64w(TAG, "setScreenOnWhilePlaying(true) is ineffective for Surface");
        }
        this.mSurfaceHolder = null;
        _setVideoSurface(surface);
        updateSurfaceScreenOn();
    }

    public void setVideoScalingMode(int mode) {
        if (!isVideoScalingModeSupported(mode)) {
            String msg = "Scaling mode " + mode + " is not supported";
            throw new IllegalArgumentException(msg);
        }
        Parcel request = Parcel.obtain();
        Parcel reply = Parcel.obtain();
        try {
            request.writeInterfaceToken(IMEDIA_PLAYER);
            request.writeInt(6);
            request.writeInt(mode);
            invoke(request, reply);
        } finally {
            request.recycle();
            reply.recycle();
        }
    }

    public static MediaPlayer create(Context context, Uri uri) {
        return create(context, uri, null);
    }

    public static MediaPlayer create(Context context, Uri uri, SurfaceHolder holder) {
        int s = AudioSystem.newAudioSessionId();
        return create(context, uri, holder, null, s > 0 ? s : 0);
    }

    public static MediaPlayer create(Context context, Uri uri, SurfaceHolder holder, AudioAttributes audioAttributes, int audioSessionId) {
        try {
            MediaPlayer mp = new MediaPlayer();
            AudioAttributes aa = audioAttributes != null ? audioAttributes : new AudioAttributes.Builder().build();
            mp.setAudioAttributes(aa);
            mp.setAudioSessionId(audioSessionId);
            mp.setDataSource(context, uri);
            if (holder != null) {
                mp.setDisplay(holder);
            }
            mp.prepare();
            return mp;
        } catch (IOException ex) {
            Log.m71d(TAG, "create failed:", ex);
            return null;
        } catch (IllegalArgumentException ex2) {
            Log.m71d(TAG, "create failed:", ex2);
            return null;
        } catch (SecurityException ex3) {
            Log.m71d(TAG, "create failed:", ex3);
            return null;
        }
    }

    public static MediaPlayer create(Context context, int resid) {
        int s = AudioSystem.newAudioSessionId();
        return create(context, resid, null, s > 0 ? s : 0);
    }

    public static MediaPlayer create(Context context, int resid, AudioAttributes audioAttributes, int audioSessionId) {
        try {
            AssetFileDescriptor afd = context.getResources().openRawResourceFd(resid);
            if (afd == null) {
                return null;
            }
            MediaPlayer mp = new MediaPlayer();
            AudioAttributes aa = audioAttributes != null ? audioAttributes : new AudioAttributes.Builder().build();
            mp.setAudioAttributes(aa);
            mp.setAudioSessionId(audioSessionId);
            mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            afd.close();
            mp.prepare();
            return mp;
        } catch (IOException ex) {
            Log.m71d(TAG, "create failed:", ex);
            return null;
        } catch (IllegalArgumentException ex2) {
            Log.m71d(TAG, "create failed:", ex2);
            return null;
        } catch (SecurityException ex3) {
            Log.m71d(TAG, "create failed:", ex3);
            return null;
        }
    }

    public void setDataSource(Context context, Uri uri) throws IOException, IllegalArgumentException, SecurityException, IllegalStateException {
        setDataSource(context, uri, (Map<String, String>) null, (List<HttpCookie>) null);
    }

    public void setDataSource(Context context, Uri uri, Map<String, String> headers, List<HttpCookie> cookies) throws IOException {
        CookieHandler cookieHandler;
        if (context == null) {
            throw new NullPointerException("context param can not be null.");
        }
        if (uri == null) {
            throw new NullPointerException("uri param can not be null.");
        }
        if (cookies != null && (cookieHandler = CookieHandler.getDefault()) != null && !(cookieHandler instanceof CookieManager)) {
            throw new IllegalArgumentException("The cookie handler has to be of CookieManager type when cookies are provided.");
        }
        ContentResolver resolver = context.getContentResolver();
        String scheme = uri.getScheme();
        String authority = ContentProvider.getAuthorityWithoutUserId(uri.getAuthority());
        if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            setDataSource(uri.getPath());
        } else if ("content".equals(scheme) && "settings".equals(authority)) {
            int type = RingtoneManager.getDefaultType(uri);
            Uri cacheUri = RingtoneManager.getCacheForType(type, context.getUserId());
            Uri actualUri = RingtoneManager.getActualDefaultRingtoneUri(context, type);
            if (attemptDataSource(resolver, cacheUri) || attemptDataSource(resolver, actualUri)) {
                return;
            }
            setDataSource(uri.toString(), headers, cookies);
        } else if (attemptDataSource(resolver, uri)) {
        } else {
            setDataSource(uri.toString(), headers, cookies);
        }
    }

    public void setDataSource(Context context, Uri uri, Map<String, String> headers) throws IOException, IllegalArgumentException, SecurityException, IllegalStateException {
        setDataSource(context, uri, headers, (List<HttpCookie>) null);
    }

    private boolean attemptDataSource(ContentResolver resolver, Uri uri) {
        try {
            AssetFileDescriptor afd = resolver.openAssetFileDescriptor(uri, "r");
            setDataSource(afd);
            if (afd != null) {
                $closeResource(null, afd);
            }
            return true;
        } catch (IOException | NullPointerException | SecurityException ex) {
            StringBuilder sb = new StringBuilder();
            sb.append("Couldn't open ");
            sb.append(uri == null ? "null uri" : uri.toSafeString());
            Log.m63w(TAG, sb.toString(), ex);
            return false;
        }
    }

    private static /* synthetic */ void $closeResource(Throwable x0, AutoCloseable x1) {
        if (x0 == null) {
            x1.close();
            return;
        }
        try {
            x1.close();
        } catch (Throwable th) {
            x0.addSuppressed(th);
        }
    }

    public void setDataSource(String path) throws IOException, IllegalArgumentException, SecurityException, IllegalStateException {
        setDataSource(path, (Map<String, String>) null, (List<HttpCookie>) null);
    }

    @UnsupportedAppUsage
    public void setDataSource(String path, Map<String, String> headers) throws IOException, IllegalArgumentException, SecurityException, IllegalStateException {
        setDataSource(path, headers, (List<HttpCookie>) null);
    }

    @UnsupportedAppUsage
    private void setDataSource(String path, Map<String, String> headers, List<HttpCookie> cookies) throws IOException, IllegalArgumentException, SecurityException, IllegalStateException {
        String[] keys = null;
        String[] values = null;
        if (headers != null) {
            keys = new String[headers.size()];
            values = new String[headers.size()];
            int i = 0;
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                keys[i] = entry.getKey();
                values[i] = entry.getValue();
                i++;
            }
        }
        setDataSource(path, keys, values, cookies);
    }

    @UnsupportedAppUsage
    private void setDataSource(String path, String[] keys, String[] values, List<HttpCookie> cookies) throws IOException, IllegalArgumentException, SecurityException, IllegalStateException {
        Uri uri = Uri.parse(path);
        String scheme = uri.getScheme();
        if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            path = uri.getPath();
        } else if (scheme != null) {
            nativeSetDataSource(MediaHTTPService.createHttpServiceBinderIfNecessary(path, cookies), path, keys, values);
            return;
        }
        File file = new File(path);
        FileInputStream is = new FileInputStream(file);
        try {
            setDataSource(is.getFD());
            $closeResource(null, is);
        } finally {
        }
    }

    public void setDataSource(AssetFileDescriptor afd) throws IOException, IllegalArgumentException, IllegalStateException {
        Preconditions.checkNotNull(afd);
        if (afd.getDeclaredLength() < 0) {
            setDataSource(afd.getFileDescriptor());
        } else {
            setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getDeclaredLength());
        }
    }

    public void setDataSource(FileDescriptor fd) throws IOException, IllegalArgumentException, IllegalStateException {
        setDataSource(fd, 0L, 576460752303423487L);
    }

    public void setDataSource(FileDescriptor fd, long offset, long length) throws IOException, IllegalArgumentException, IllegalStateException {
        _setDataSource(fd, offset, length);
    }

    public void setDataSource(MediaDataSource dataSource) throws IllegalArgumentException, IllegalStateException {
        _setDataSource(dataSource);
    }

    public void prepare() throws IOException, IllegalStateException {
        _prepare();
        scanInternalSubtitleTracks();
        synchronized (this.mDrmLock) {
            this.mDrmInfoResolved = true;
        }
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [android.media.MediaPlayer$1] */
    public void start() throws IllegalStateException {
        final int delay = getStartDelayMs();
        if (delay == 0) {
            startImpl();
        } else {
            new Thread() { // from class: android.media.MediaPlayer.1
                @Override // java.lang.Thread, java.lang.Runnable
                public void run() {
                    try {
                        Thread.sleep(delay);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    MediaPlayer.this.baseSetStartDelayMs(0);
                    try {
                        MediaPlayer.this.startImpl();
                    } catch (IllegalStateException e2) {
                    }
                }
            }.start();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startImpl() {
        baseStart();
        stayAwake(true);
        _start();
    }

    private int getAudioStreamType() {
        if (this.mStreamType == Integer.MIN_VALUE) {
            this.mStreamType = _getAudioStreamType();
        }
        return this.mStreamType;
    }

    public void stop() throws IllegalStateException {
        stayAwake(false);
        _stop();
        baseStop();
    }

    public void pause() throws IllegalStateException {
        stayAwake(false);
        _pause();
        basePause();
    }

    @Override // android.media.PlayerBase
    void playerStart() {
        start();
    }

    @Override // android.media.PlayerBase
    void playerPause() {
        pause();
    }

    @Override // android.media.PlayerBase
    void playerStop() {
        stop();
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
    private void enableNativeRoutingCallbacksLocked(boolean enabled) {
        if (this.mRoutingChangeListeners.size() == 0) {
            native_enableDeviceCallback(enabled);
        }
    }

    @Override // android.media.AudioRouting
    public void addOnRoutingChangedListener(AudioRouting.OnRoutingChangedListener listener, Handler handler) {
        synchronized (this.mRoutingChangeListeners) {
            if (listener != null) {
                try {
                    if (!this.mRoutingChangeListeners.containsKey(listener)) {
                        enableNativeRoutingCallbacksLocked(true);
                        this.mRoutingChangeListeners.put(listener, new NativeRoutingEventHandlerDelegate(this, listener, handler != null ? handler : this.mEventHandler));
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
                enableNativeRoutingCallbacksLocked(false);
            }
        }
    }

    public void setWakeMode(Context context, int mode) {
        boolean washeld = false;
        if (SystemProperties.getBoolean("audio.offload.ignore_setawake", false)) {
            Log.m64w(TAG, "IGNORING setWakeMode " + mode);
            return;
        }
        if (this.mWakeLock != null) {
            if (this.mWakeLock.isHeld()) {
                washeld = true;
                this.mWakeLock.release();
            }
            this.mWakeLock = null;
        }
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        this.mWakeLock = pm.newWakeLock(536870912 | mode, MediaPlayer.class.getName());
        this.mWakeLock.setReferenceCounted(false);
        if (washeld) {
            this.mWakeLock.acquire();
        }
    }

    public void setScreenOnWhilePlaying(boolean screenOn) {
        if (this.mScreenOnWhilePlaying != screenOn) {
            if (screenOn && this.mSurfaceHolder == null) {
                Log.m64w(TAG, "setScreenOnWhilePlaying(true) is ineffective without a SurfaceHolder");
            }
            this.mScreenOnWhilePlaying = screenOn;
            updateSurfaceScreenOn();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stayAwake(boolean awake) {
        if (this.mWakeLock != null) {
            if (awake && !this.mWakeLock.isHeld()) {
                this.mWakeLock.acquire();
            } else if (!awake && this.mWakeLock.isHeld()) {
                this.mWakeLock.release();
            }
        }
        this.mStayAwake = awake;
        updateSurfaceScreenOn();
    }

    private void updateSurfaceScreenOn() {
        if (this.mSurfaceHolder != null) {
            this.mSurfaceHolder.setKeepScreenOn(this.mScreenOnWhilePlaying && this.mStayAwake);
        }
    }

    public PersistableBundle getMetrics() {
        PersistableBundle bundle = native_getMetrics();
        return bundle;
    }

    public PlaybackParams easyPlaybackParams(float rate, int audioMode) {
        PlaybackParams params = new PlaybackParams();
        params.allowDefaults();
        switch (audioMode) {
            case 0:
                params.setSpeed(rate).setPitch(1.0f);
                break;
            case 1:
                params.setSpeed(rate).setPitch(1.0f).setAudioFallbackMode(2);
                break;
            case 2:
                params.setSpeed(rate).setPitch(rate);
                break;
            default:
                String msg = "Audio playback mode " + audioMode + " is not supported";
                throw new IllegalArgumentException(msg);
        }
        return params;
    }

    public void seekTo(long msec, int mode) {
        if (mode < 0 || mode > 3) {
            String msg = "Illegal seek mode: " + mode;
            throw new IllegalArgumentException(msg);
        }
        if (msec > 2147483647L) {
            Log.m64w(TAG, "seekTo offset " + msec + " is too large, cap to 2147483647");
            msec = 2147483647L;
        } else if (msec < -2147483648L) {
            Log.m64w(TAG, "seekTo offset " + msec + " is too small, cap to -2147483648");
            msec = -2147483648L;
        }
        _seekTo(msec, mode);
    }

    public void seekTo(int msec) throws IllegalStateException {
        seekTo(msec, 0);
    }

    public MediaTimestamp getTimestamp() {
        try {
            return new MediaTimestamp(getCurrentPosition() * 1000, System.nanoTime(), isPlaying() ? getPlaybackParams().getSpeed() : 0.0f);
        } catch (IllegalStateException e) {
            return null;
        }
    }

    @UnsupportedAppUsage
    public Metadata getMetadata(boolean update_only, boolean apply_filter) {
        Parcel reply = Parcel.obtain();
        Metadata data = new Metadata();
        if (!native_getMetadata(update_only, apply_filter, reply)) {
            reply.recycle();
            return null;
        } else if (!data.parse(reply)) {
            reply.recycle();
            return null;
        } else {
            return data;
        }
    }

    public int setMetadataFilter(Set<Integer> allow, Set<Integer> block) {
        Parcel request = newRequest();
        int capacity = request.dataSize() + ((allow.size() + 1 + 1 + block.size()) * 4);
        if (request.dataCapacity() < capacity) {
            request.setDataCapacity(capacity);
        }
        request.writeInt(allow.size());
        for (Integer t : allow) {
            request.writeInt(t.intValue());
        }
        request.writeInt(block.size());
        for (Integer t2 : block) {
            request.writeInt(t2.intValue());
        }
        return native_setMetadataFilter(request);
    }

    public void release() {
        baseRelease();
        stayAwake(false);
        updateSurfaceScreenOn();
        this.mOnPreparedListener = null;
        this.mOnBufferingUpdateListener = null;
        this.mOnCompletionListener = null;
        this.mOnSeekCompleteListener = null;
        this.mOnErrorListener = null;
        this.mOnInfoListener = null;
        this.mOnVideoSizeChangedListener = null;
        this.mOnTimedTextListener = null;
        synchronized (this.mTimeProviderLock) {
            if (this.mTimeProvider != null) {
                this.mTimeProvider.close();
                this.mTimeProvider = null;
            }
        }
        synchronized (this) {
            this.mSubtitleDataListenerDisabled = false;
            this.mExtSubtitleDataListener = null;
            this.mExtSubtitleDataHandler = null;
            this.mOnMediaTimeDiscontinuityListener = null;
            this.mOnMediaTimeDiscontinuityHandler = null;
        }
        this.mOnDrmConfigHelper = null;
        this.mOnDrmInfoHandlerDelegate = null;
        this.mOnDrmPreparedHandlerDelegate = null;
        resetDrmState();
        _release();
    }

    public void reset() {
        this.mSelectedSubtitleTrackIndex = -1;
        synchronized (this.mOpenSubtitleSources) {
            Iterator<InputStream> it = this.mOpenSubtitleSources.iterator();
            while (it.hasNext()) {
                InputStream is = it.next();
                try {
                    is.close();
                } catch (IOException e) {
                }
            }
            this.mOpenSubtitleSources.clear();
        }
        if (this.mSubtitleController != null) {
            this.mSubtitleController.reset();
        }
        synchronized (this.mTimeProviderLock) {
            if (this.mTimeProvider != null) {
                this.mTimeProvider.close();
                this.mTimeProvider = null;
            }
        }
        stayAwake(false);
        _reset();
        if (this.mEventHandler != null) {
            this.mEventHandler.removeCallbacksAndMessages(null);
        }
        synchronized (this.mIndexTrackPairs) {
            this.mIndexTrackPairs.clear();
            this.mInbandTrackIndices.clear();
        }
        resetDrmState();
    }

    public void notifyAt(long mediaTimeUs) {
        _notifyAt(mediaTimeUs);
    }

    public void setAudioStreamType(int streamtype) {
        deprecateStreamTypeForPlayback(streamtype, TAG, "setAudioStreamType()");
        baseUpdateAudioAttributes(new AudioAttributes.Builder().setInternalLegacyStreamType(streamtype).build());
        _setAudioStreamType(streamtype);
        this.mStreamType = streamtype;
    }

    public void setAudioAttributes(AudioAttributes attributes) throws IllegalArgumentException {
        if (attributes == null) {
            throw new IllegalArgumentException("Cannot set AudioAttributes to null");
        }
        baseUpdateAudioAttributes(attributes);
        this.mUsage = attributes.getUsage();
        Parcel pattributes = Parcel.obtain();
        attributes.writeToParcel(pattributes, 1);
        setParameter(1400, pattributes);
        pattributes.recycle();
    }

    public void setVolume(float leftVolume, float rightVolume) {
        baseSetVolume(leftVolume, rightVolume);
    }

    @Override // android.media.PlayerBase
    void playerSetVolume(boolean muting, float leftVolume, float rightVolume) {
        _setVolume(muting ? 0.0f : leftVolume, muting ? 0.0f : rightVolume);
    }

    public void setVolume(float volume) {
        setVolume(volume, volume);
    }

    public void setAuxEffectSendLevel(float level) {
        baseSetAuxEffectSendLevel(level);
    }

    @Override // android.media.PlayerBase
    int playerSetAuxEffectSendLevel(boolean muting, float level) {
        _setAuxEffectSendLevel(muting ? 0.0f : level);
        return 0;
    }

    /* loaded from: classes3.dex */
    public static class TrackInfo implements Parcelable {
        @UnsupportedAppUsage
        static final Parcelable.Creator<TrackInfo> CREATOR = new Parcelable.Creator<TrackInfo>() { // from class: android.media.MediaPlayer.TrackInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.p007os.Parcelable.Creator
            public TrackInfo createFromParcel(Parcel in) {
                return new TrackInfo(in);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.p007os.Parcelable.Creator
            public TrackInfo[] newArray(int size) {
                return new TrackInfo[size];
            }
        };
        public static final int MEDIA_TRACK_TYPE_AUDIO = 2;
        public static final int MEDIA_TRACK_TYPE_METADATA = 5;
        public static final int MEDIA_TRACK_TYPE_SUBTITLE = 4;
        public static final int MEDIA_TRACK_TYPE_TIMEDTEXT = 3;
        public static final int MEDIA_TRACK_TYPE_UNKNOWN = 0;
        public static final int MEDIA_TRACK_TYPE_VIDEO = 1;
        final MediaFormat mFormat;
        final int mTrackType;

        @Retention(RetentionPolicy.SOURCE)
        /* loaded from: classes3.dex */
        public @interface TrackType {
        }

        public int getTrackType() {
            return this.mTrackType;
        }

        public String getLanguage() {
            String language = this.mFormat.getString("language");
            return language == null ? "und" : language;
        }

        public MediaFormat getFormat() {
            if (this.mTrackType == 3 || this.mTrackType == 4) {
                return this.mFormat;
            }
            return null;
        }

        TrackInfo(Parcel in) {
            this.mTrackType = in.readInt();
            String mime = in.readString();
            String language = in.readString();
            this.mFormat = MediaFormat.createSubtitleFormat(mime, language);
            if (this.mTrackType == 4) {
                this.mFormat.setInteger(MediaFormat.KEY_IS_AUTOSELECT, in.readInt());
                this.mFormat.setInteger(MediaFormat.KEY_IS_DEFAULT, in.readInt());
                this.mFormat.setInteger(MediaFormat.KEY_IS_FORCED_SUBTITLE, in.readInt());
            }
        }

        TrackInfo(int type, MediaFormat format) {
            this.mTrackType = type;
            this.mFormat = format;
        }

        @Override // android.p007os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.p007os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.mTrackType);
            dest.writeString(this.mFormat.getString(MediaFormat.KEY_MIME));
            dest.writeString(getLanguage());
            if (this.mTrackType == 4) {
                dest.writeInt(this.mFormat.getInteger(MediaFormat.KEY_IS_AUTOSELECT));
                dest.writeInt(this.mFormat.getInteger(MediaFormat.KEY_IS_DEFAULT));
                dest.writeInt(this.mFormat.getInteger(MediaFormat.KEY_IS_FORCED_SUBTITLE));
            }
        }

        public String toString() {
            StringBuilder out = new StringBuilder(128);
            out.append(getClass().getName());
            out.append('{');
            switch (this.mTrackType) {
                case 1:
                    out.append("VIDEO");
                    break;
                case 2:
                    out.append("AUDIO");
                    break;
                case 3:
                    out.append("TIMEDTEXT");
                    break;
                case 4:
                    out.append("SUBTITLE");
                    break;
                default:
                    out.append(IccCardConstants.INTENT_VALUE_ICC_UNKNOWN);
                    break;
            }
            out.append(", " + this.mFormat.toString());
            out.append("}");
            return out.toString();
        }
    }

    public TrackInfo[] getTrackInfo() throws IllegalStateException {
        TrackInfo[] allTrackInfo;
        TrackInfo[] trackInfo = getInbandTrackInfo();
        synchronized (this.mIndexTrackPairs) {
            allTrackInfo = new TrackInfo[this.mIndexTrackPairs.size()];
            for (int i = 0; i < allTrackInfo.length; i++) {
                Pair<Integer, SubtitleTrack> p = this.mIndexTrackPairs.get(i);
                if (p.first != null) {
                    allTrackInfo[i] = trackInfo[p.first.intValue()];
                } else {
                    SubtitleTrack track = p.second;
                    allTrackInfo[i] = new TrackInfo(track.getTrackType(), track.getFormat());
                }
            }
        }
        return allTrackInfo;
    }

    private TrackInfo[] getInbandTrackInfo() throws IllegalStateException {
        Parcel request = Parcel.obtain();
        Parcel reply = Parcel.obtain();
        try {
            request.writeInterfaceToken(IMEDIA_PLAYER);
            request.writeInt(1);
            invoke(request, reply);
            TrackInfo[] trackInfo = (TrackInfo[]) reply.createTypedArray(TrackInfo.CREATOR);
            return trackInfo;
        } finally {
            request.recycle();
            reply.recycle();
        }
    }

    private static boolean availableMimeTypeForExternalSource(String mimeType) {
        if ("application/x-subrip".equals(mimeType)) {
            return true;
        }
        return false;
    }

    @UnsupportedAppUsage
    public void setSubtitleAnchor(SubtitleController controller, SubtitleController.Anchor anchor) {
        this.mSubtitleController = controller;
        this.mSubtitleController.setAnchor(anchor);
    }

    private synchronized void setSubtitleAnchor() {
        if (this.mSubtitleController == null && ActivityThread.currentApplication() != null) {
            final TimeProvider timeProvider = (TimeProvider) getMediaTimeProvider();
            final HandlerThread thread = new HandlerThread("SetSubtitleAnchorThread");
            thread.start();
            Handler handler = new Handler(thread.getLooper());
            handler.post(new Runnable() { // from class: android.media.MediaPlayer.2
                @Override // java.lang.Runnable
                public void run() {
                    Context context = ActivityThread.currentApplication();
                    MediaPlayer.this.mSubtitleController = new SubtitleController(context, timeProvider, MediaPlayer.this);
                    MediaPlayer.this.mSubtitleController.setAnchor(new SubtitleController.Anchor() { // from class: android.media.MediaPlayer.2.1
                        @Override // android.media.SubtitleController.Anchor
                        public void setSubtitleWidget(SubtitleTrack.RenderingWidget subtitleWidget) {
                        }

                        @Override // android.media.SubtitleController.Anchor
                        public Looper getSubtitleLooper() {
                            return timeProvider.mEventHandler.getLooper();
                        }
                    });
                    thread.getLooper().quitSafely();
                }
            });
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                Log.m64w(TAG, "failed to join SetSubtitleAnchorThread");
            }
        }
    }

    @Override // android.media.SubtitleController.Listener
    public void onSubtitleTrackSelected(SubtitleTrack track) {
        if (this.mSelectedSubtitleTrackIndex >= 0) {
            try {
                selectOrDeselectInbandTrack(this.mSelectedSubtitleTrackIndex, false);
            } catch (IllegalStateException e) {
            }
            this.mSelectedSubtitleTrackIndex = -1;
        }
        synchronized (this) {
            this.mSubtitleDataListenerDisabled = true;
        }
        if (track == null) {
            return;
        }
        synchronized (this.mIndexTrackPairs) {
            Iterator<Pair<Integer, SubtitleTrack>> it = this.mIndexTrackPairs.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                Pair<Integer, SubtitleTrack> p = it.next();
                if (p.first != null && p.second == track) {
                    this.mSelectedSubtitleTrackIndex = p.first.intValue();
                    break;
                }
            }
        }
        if (this.mSelectedSubtitleTrackIndex >= 0) {
            try {
                selectOrDeselectInbandTrack(this.mSelectedSubtitleTrackIndex, true);
            } catch (IllegalStateException e2) {
            }
            synchronized (this) {
                this.mSubtitleDataListenerDisabled = false;
            }
        }
    }

    @UnsupportedAppUsage
    public void addSubtitleSource(final InputStream is, final MediaFormat format) throws IllegalStateException {
        if (is != null) {
            synchronized (this.mOpenSubtitleSources) {
                this.mOpenSubtitleSources.add(is);
            }
        } else {
            Log.m64w(TAG, "addSubtitleSource called with null InputStream");
        }
        getMediaTimeProvider();
        final HandlerThread thread = new HandlerThread("SubtitleReadThread", 9);
        thread.start();
        Handler handler = new Handler(thread.getLooper());
        handler.post(new Runnable() { // from class: android.media.MediaPlayer.4
            private int addTrack() {
                SubtitleTrack track;
                if (is == null || MediaPlayer.this.mSubtitleController == null || (track = MediaPlayer.this.mSubtitleController.addTrack(format)) == null) {
                    return 901;
                }
                Scanner scanner = new Scanner(is, "UTF-8");
                String contents = scanner.useDelimiter("\\A").next();
                synchronized (MediaPlayer.this.mOpenSubtitleSources) {
                    MediaPlayer.this.mOpenSubtitleSources.remove(is);
                }
                scanner.close();
                synchronized (MediaPlayer.this.mIndexTrackPairs) {
                    MediaPlayer.this.mIndexTrackPairs.add(Pair.create(null, track));
                }
                synchronized (MediaPlayer.this.mTimeProviderLock) {
                    if (MediaPlayer.this.mTimeProvider != null) {
                        Handler h = MediaPlayer.this.mTimeProvider.mEventHandler;
                        Pair<SubtitleTrack, byte[]> trackData = Pair.create(track, contents.getBytes());
                        Message m = h.obtainMessage(1, 4, 0, trackData);
                        h.sendMessage(m);
                    }
                }
                return 803;
            }

            @Override // java.lang.Runnable
            public void run() {
                int res = addTrack();
                if (MediaPlayer.this.mEventHandler != null) {
                    Message m = MediaPlayer.this.mEventHandler.obtainMessage(200, res, 0, null);
                    MediaPlayer.this.mEventHandler.sendMessage(m);
                }
                thread.getLooper().quitSafely();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void scanInternalSubtitleTracks() {
        setSubtitleAnchor();
        populateInbandTracks();
        if (this.mSubtitleController != null) {
            this.mSubtitleController.selectDefaultTrack();
        }
    }

    private void populateInbandTracks() {
        TrackInfo[] tracks = getInbandTrackInfo();
        synchronized (this.mIndexTrackPairs) {
            for (int i = 0; i < tracks.length; i++) {
                if (!this.mInbandTrackIndices.get(i)) {
                    this.mInbandTrackIndices.set(i);
                    if (tracks[i] == null) {
                        Log.m64w(TAG, "unexpected NULL track at index " + i);
                    }
                    if (tracks[i] != null && tracks[i].getTrackType() == 4) {
                        SubtitleTrack track = this.mSubtitleController.addTrack(tracks[i].getFormat());
                        this.mIndexTrackPairs.add(Pair.create(Integer.valueOf(i), track));
                    } else {
                        this.mIndexTrackPairs.add(Pair.create(Integer.valueOf(i), null));
                    }
                }
            }
        }
    }

    public void addTimedTextSource(String path, String mimeType) throws IOException, IllegalArgumentException, IllegalStateException {
        if (!availableMimeTypeForExternalSource(mimeType)) {
            String msg = "Illegal mimeType for timed text source: " + mimeType;
            throw new IllegalArgumentException(msg);
        }
        File file = new File(path);
        FileInputStream is = new FileInputStream(file);
        try {
            addTimedTextSource(is.getFD(), mimeType);
            $closeResource(null, is);
        } finally {
        }
    }

    public void addTimedTextSource(Context context, Uri uri, String mimeType) throws IOException, IllegalArgumentException, IllegalStateException {
        String scheme = uri.getScheme();
        if (scheme == null || scheme.equals(ContentResolver.SCHEME_FILE)) {
            addTimedTextSource(uri.getPath(), mimeType);
            return;
        }
        AssetFileDescriptor fd = null;
        try {
            ContentResolver resolver = context.getContentResolver();
            fd = resolver.openAssetFileDescriptor(uri, "r");
            if (fd == null) {
                if (fd != null) {
                    fd.close();
                    return;
                }
                return;
            }
            addTimedTextSource(fd.getFileDescriptor(), mimeType);
            if (fd != null) {
                fd.close();
            }
        } catch (IOException e) {
            if (fd == null) {
                return;
            }
            fd.close();
        } catch (SecurityException e2) {
            if (fd == null) {
                return;
            }
            fd.close();
        } catch (Throwable th) {
            if (fd != null) {
                fd.close();
            }
            throw th;
        }
    }

    public void addTimedTextSource(FileDescriptor fd, String mimeType) throws IllegalArgumentException, IllegalStateException {
        addTimedTextSource(fd, 0L, 576460752303423487L, mimeType);
    }

    public void addTimedTextSource(FileDescriptor fd, final long offset, final long length, String mime) throws IllegalArgumentException, IllegalStateException {
        if (!availableMimeTypeForExternalSource(mime)) {
            throw new IllegalArgumentException("Illegal mimeType for timed text source: " + mime);
        }
        try {
            final FileDescriptor dupedFd = Os.dup(fd);
            MediaFormat fFormat = new MediaFormat();
            fFormat.setString(MediaFormat.KEY_MIME, mime);
            fFormat.setInteger(MediaFormat.KEY_IS_TIMED_TEXT, 1);
            if (this.mSubtitleController == null) {
                setSubtitleAnchor();
            }
            if (!this.mSubtitleController.hasRendererFor(fFormat)) {
                Context context = ActivityThread.currentApplication();
                this.mSubtitleController.registerRenderer(new SRTRenderer(context, this.mEventHandler));
            }
            final SubtitleTrack track = this.mSubtitleController.addTrack(fFormat);
            synchronized (this.mIndexTrackPairs) {
                this.mIndexTrackPairs.add(Pair.create(null, track));
            }
            getMediaTimeProvider();
            final HandlerThread thread = new HandlerThread("TimedTextReadThread", 9);
            thread.start();
            Handler handler = new Handler(thread.getLooper());
            handler.post(new Runnable() { // from class: android.media.MediaPlayer.5
                private int addTrack() {
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    try {
                        try {
                            Os.lseek(dupedFd, offset, OsConstants.SEEK_SET);
                            byte[] buffer = new byte[4096];
                            long total = 0;
                            while (total < length) {
                                int bytesToRead = (int) Math.min(buffer.length, length - total);
                                int bytes = IoBridge.read(dupedFd, buffer, 0, bytesToRead);
                                if (bytes < 0) {
                                    break;
                                }
                                bos.write(buffer, 0, bytes);
                                total += bytes;
                            }
                            synchronized (MediaPlayer.this.mTimeProviderLock) {
                                if (MediaPlayer.this.mTimeProvider != null) {
                                    Handler h = MediaPlayer.this.mTimeProvider.mEventHandler;
                                    Pair<SubtitleTrack, byte[]> trackData = Pair.create(track, bos.toByteArray());
                                    Message m = h.obtainMessage(1, 4, 0, trackData);
                                    h.sendMessage(m);
                                }
                            }
                            try {
                                Os.close(dupedFd);
                            } catch (ErrnoException e) {
                                Log.m69e(MediaPlayer.TAG, e.getMessage(), e);
                            }
                            return 803;
                        } catch (Exception e2) {
                            Log.m69e(MediaPlayer.TAG, e2.getMessage(), e2);
                            try {
                                Os.close(dupedFd);
                            } catch (ErrnoException e3) {
                                Log.m69e(MediaPlayer.TAG, e3.getMessage(), e3);
                            }
                            return 900;
                        }
                    } catch (Throwable th) {
                        try {
                            Os.close(dupedFd);
                        } catch (ErrnoException e4) {
                            Log.m69e(MediaPlayer.TAG, e4.getMessage(), e4);
                        }
                        throw th;
                    }
                }

                @Override // java.lang.Runnable
                public void run() {
                    int res = addTrack();
                    if (MediaPlayer.this.mEventHandler != null) {
                        Message m = MediaPlayer.this.mEventHandler.obtainMessage(200, res, 0, null);
                        MediaPlayer.this.mEventHandler.sendMessage(m);
                    }
                    thread.getLooper().quitSafely();
                }
            });
        } catch (ErrnoException ex) {
            Log.m69e(TAG, ex.getMessage(), ex);
            throw new RuntimeException(ex);
        }
    }

    public int getSelectedTrack(int trackType) throws IllegalStateException {
        SubtitleTrack subtitleTrack;
        if (this.mSubtitleController != null && ((trackType == 4 || trackType == 3) && (subtitleTrack = this.mSubtitleController.getSelectedTrack()) != null)) {
            synchronized (this.mIndexTrackPairs) {
                for (int i = 0; i < this.mIndexTrackPairs.size(); i++) {
                    if (this.mIndexTrackPairs.get(i).second == subtitleTrack && subtitleTrack.getTrackType() == trackType) {
                        return i;
                    }
                }
            }
        }
        Parcel request = Parcel.obtain();
        Parcel reply = Parcel.obtain();
        try {
            request.writeInterfaceToken(IMEDIA_PLAYER);
            request.writeInt(7);
            request.writeInt(trackType);
            invoke(request, reply);
            int inbandTrackIndex = reply.readInt();
            synchronized (this.mIndexTrackPairs) {
                for (int i2 = 0; i2 < this.mIndexTrackPairs.size(); i2++) {
                    Pair<Integer, SubtitleTrack> p = this.mIndexTrackPairs.get(i2);
                    if (p.first != null && p.first.intValue() == inbandTrackIndex) {
                        return i2;
                    }
                }
                return -1;
            }
        } finally {
            request.recycle();
            reply.recycle();
        }
    }

    public void selectTrack(int index) throws IllegalStateException {
        selectOrDeselectTrack(index, true);
    }

    public void deselectTrack(int index) throws IllegalStateException {
        selectOrDeselectTrack(index, false);
    }

    private void selectOrDeselectTrack(int index, boolean select) throws IllegalStateException {
        populateInbandTracks();
        try {
            Pair<Integer, SubtitleTrack> p = this.mIndexTrackPairs.get(index);
            SubtitleTrack track = p.second;
            if (track == null) {
                selectOrDeselectInbandTrack(p.first.intValue(), select);
            } else if (this.mSubtitleController == null) {
            } else {
                if (!select) {
                    if (this.mSubtitleController.getSelectedTrack() == track) {
                        this.mSubtitleController.selectTrack(null);
                        return;
                    } else {
                        Log.m64w(TAG, "trying to deselect track that was not selected");
                        return;
                    }
                }
                if (track.getTrackType() == 3) {
                    int ttIndex = getSelectedTrack(3);
                    synchronized (this.mIndexTrackPairs) {
                        if (ttIndex >= 0) {
                            try {
                                if (ttIndex < this.mIndexTrackPairs.size()) {
                                    Pair<Integer, SubtitleTrack> p2 = this.mIndexTrackPairs.get(ttIndex);
                                    if (p2.first != null && p2.second == null) {
                                        selectOrDeselectInbandTrack(p2.first.intValue(), false);
                                    }
                                }
                            } finally {
                            }
                        }
                    }
                }
                this.mSubtitleController.selectTrack(track);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
        }
    }

    private void selectOrDeselectInbandTrack(int index, boolean select) throws IllegalStateException {
        Parcel request = Parcel.obtain();
        Parcel reply = Parcel.obtain();
        try {
            request.writeInterfaceToken(IMEDIA_PLAYER);
            request.writeInt(select ? 4 : 5);
            request.writeInt(index);
            invoke(request, reply);
        } finally {
            request.recycle();
            reply.recycle();
        }
    }

    @UnsupportedAppUsage
    public void setRetransmitEndpoint(InetSocketAddress endpoint) throws IllegalStateException, IllegalArgumentException {
        String addrString = null;
        int port = 0;
        if (endpoint != null) {
            addrString = endpoint.getAddress().getHostAddress();
            port = endpoint.getPort();
        }
        int ret = native_setRetransmitEndpoint(addrString, port);
        if (ret != 0) {
            throw new IllegalArgumentException("Illegal re-transmit endpoint; native ret " + ret);
        }
    }

    protected void finalize() {
        baseRelease();
        native_finalize();
    }

    @UnsupportedAppUsage
    public MediaTimeProvider getMediaTimeProvider() {
        TimeProvider timeProvider;
        synchronized (this.mTimeProviderLock) {
            if (this.mTimeProvider == null) {
                this.mTimeProvider = new TimeProvider(this);
            }
            timeProvider = this.mTimeProvider;
        }
        return timeProvider;
    }

    /* loaded from: classes3.dex */
    private class EventHandler extends Handler {
        private MediaPlayer mMediaPlayer;

        public EventHandler(MediaPlayer mp, Looper looper) {
            super(looper);
            this.mMediaPlayer = mp;
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        @Override // android.p007os.Handler
        public void handleMessage(Message msg) {
            OnDrmInfoHandlerDelegate onDrmInfoHandlerDelegate;
            final OnMediaTimeDiscontinuityListener mediaTimeListener;
            Handler mediaTimeHandler;
            final MediaTimestamp timestamp;
            if (this.mMediaPlayer.mNativeContext == 0) {
                Log.m64w(MediaPlayer.TAG, "mediaplayer went away with unhandled events");
                return;
            }
            int i = msg.what;
            if (i == 10000) {
                AudioManager.resetAudioPortGeneration();
                synchronized (MediaPlayer.this.mRoutingChangeListeners) {
                    for (NativeRoutingEventHandlerDelegate delegate : MediaPlayer.this.mRoutingChangeListeners.values()) {
                        delegate.notifyClient();
                    }
                }
                return;
            }
            switch (i) {
                case 0:
                    return;
                case 1:
                    try {
                        MediaPlayer.this.scanInternalSubtitleTracks();
                    } catch (RuntimeException e) {
                        Message msg2 = obtainMessage(100, 1, MediaPlayer.MEDIA_ERROR_UNSUPPORTED, null);
                        sendMessage(msg2);
                    }
                    OnPreparedListener onPreparedListener = MediaPlayer.this.mOnPreparedListener;
                    if (onPreparedListener != null) {
                        onPreparedListener.onPrepared(this.mMediaPlayer);
                        return;
                    }
                    return;
                case 2:
                    MediaPlayer.this.mOnCompletionInternalListener.onCompletion(this.mMediaPlayer);
                    OnCompletionListener onCompletionListener = MediaPlayer.this.mOnCompletionListener;
                    if (onCompletionListener != null) {
                        onCompletionListener.onCompletion(this.mMediaPlayer);
                    }
                    MediaPlayer.this.stayAwake(false);
                    return;
                case 3:
                    OnBufferingUpdateListener onBufferingUpdateListener = MediaPlayer.this.mOnBufferingUpdateListener;
                    if (onBufferingUpdateListener != null) {
                        onBufferingUpdateListener.onBufferingUpdate(this.mMediaPlayer, msg.arg1);
                        return;
                    }
                    return;
                case 4:
                    OnSeekCompleteListener onSeekCompleteListener = MediaPlayer.this.mOnSeekCompleteListener;
                    if (onSeekCompleteListener != null) {
                        onSeekCompleteListener.onSeekComplete(this.mMediaPlayer);
                        break;
                    }
                    break;
                case 5:
                    OnVideoSizeChangedListener onVideoSizeChangedListener = MediaPlayer.this.mOnVideoSizeChangedListener;
                    if (onVideoSizeChangedListener != null) {
                        onVideoSizeChangedListener.onVideoSizeChanged(this.mMediaPlayer, msg.arg1, msg.arg2);
                        return;
                    }
                    return;
                case 6:
                case 7:
                    TimeProvider timeProvider = MediaPlayer.this.mTimeProvider;
                    if (timeProvider != null) {
                        timeProvider.onPaused(msg.what == 7);
                        return;
                    }
                    return;
                case 8:
                    TimeProvider timeProvider2 = MediaPlayer.this.mTimeProvider;
                    if (timeProvider2 != null) {
                        timeProvider2.onStopped();
                        return;
                    }
                    return;
                case 9:
                    break;
                default:
                    switch (i) {
                        case 98:
                            TimeProvider timeProvider3 = MediaPlayer.this.mTimeProvider;
                            if (timeProvider3 != null) {
                                timeProvider3.onNotifyTime();
                                return;
                            }
                            return;
                        case 99:
                            OnTimedTextListener onTimedTextListener = MediaPlayer.this.mOnTimedTextListener;
                            if (onTimedTextListener == null) {
                                return;
                            }
                            if (msg.obj == null) {
                                onTimedTextListener.onTimedText(this.mMediaPlayer, null);
                                return;
                            } else if (msg.obj instanceof Parcel) {
                                Parcel parcel = (Parcel) msg.obj;
                                TimedText text = new TimedText(parcel);
                                parcel.recycle();
                                onTimedTextListener.onTimedText(this.mMediaPlayer, text);
                                return;
                            } else {
                                return;
                            }
                        case 100:
                            Log.m70e(MediaPlayer.TAG, "Error (" + msg.arg1 + SmsManager.REGEX_PREFIX_DELIMITER + msg.arg2 + ")");
                            boolean error_was_handled = false;
                            OnErrorListener onErrorListener = MediaPlayer.this.mOnErrorListener;
                            if (onErrorListener != null) {
                                error_was_handled = onErrorListener.onError(this.mMediaPlayer, msg.arg1, msg.arg2);
                            }
                            MediaPlayer.this.mOnCompletionInternalListener.onCompletion(this.mMediaPlayer);
                            OnCompletionListener onCompletionListener2 = MediaPlayer.this.mOnCompletionListener;
                            if (onCompletionListener2 != null && !error_was_handled) {
                                onCompletionListener2.onCompletion(this.mMediaPlayer);
                            }
                            MediaPlayer.this.stayAwake(false);
                            return;
                        default:
                            switch (i) {
                                case 200:
                                    int i2 = msg.arg1;
                                    switch (i2) {
                                        case 700:
                                            Log.m68i(MediaPlayer.TAG, "Info (" + msg.arg1 + SmsManager.REGEX_PREFIX_DELIMITER + msg.arg2 + ")");
                                            break;
                                        case 701:
                                        case 702:
                                            TimeProvider timeProvider4 = MediaPlayer.this.mTimeProvider;
                                            if (timeProvider4 != null) {
                                                timeProvider4.onBuffering(msg.arg1 == 701);
                                                break;
                                            }
                                            break;
                                        default:
                                            switch (i2) {
                                                case 802:
                                                    try {
                                                        MediaPlayer.this.scanInternalSubtitleTracks();
                                                    } catch (RuntimeException e2) {
                                                        Message msg22 = obtainMessage(100, 1, MediaPlayer.MEDIA_ERROR_UNSUPPORTED, null);
                                                        sendMessage(msg22);
                                                    }
                                                case 803:
                                                    msg.arg1 = 802;
                                                    if (MediaPlayer.this.mSubtitleController != null) {
                                                        MediaPlayer.this.mSubtitleController.selectDefaultTrack();
                                                        break;
                                                    }
                                                    break;
                                            }
                                            break;
                                    }
                                    OnInfoListener onInfoListener = MediaPlayer.this.mOnInfoListener;
                                    if (onInfoListener != null) {
                                        onInfoListener.onInfo(this.mMediaPlayer, msg.arg1, msg.arg2);
                                        return;
                                    }
                                    return;
                                case 201:
                                    synchronized (this) {
                                        if (MediaPlayer.this.mSubtitleDataListenerDisabled) {
                                            return;
                                        }
                                        final OnSubtitleDataListener extSubtitleListener = MediaPlayer.this.mExtSubtitleDataListener;
                                        Handler extSubtitleHandler = MediaPlayer.this.mExtSubtitleDataHandler;
                                        if (msg.obj instanceof Parcel) {
                                            Parcel parcel2 = (Parcel) msg.obj;
                                            final SubtitleData data = new SubtitleData(parcel2);
                                            parcel2.recycle();
                                            MediaPlayer.this.mIntSubtitleDataListener.onSubtitleData(this.mMediaPlayer, data);
                                            if (extSubtitleListener != null) {
                                                if (extSubtitleHandler != null) {
                                                    extSubtitleHandler.post(new Runnable() { // from class: android.media.MediaPlayer.EventHandler.1
                                                        @Override // java.lang.Runnable
                                                        public void run() {
                                                            extSubtitleListener.onSubtitleData(EventHandler.this.mMediaPlayer, data);
                                                        }
                                                    });
                                                    return;
                                                } else {
                                                    extSubtitleListener.onSubtitleData(this.mMediaPlayer, data);
                                                    return;
                                                }
                                            }
                                            return;
                                        }
                                        return;
                                    }
                                case 202:
                                    OnTimedMetaDataAvailableListener onTimedMetaDataAvailableListener = MediaPlayer.this.mOnTimedMetaDataAvailableListener;
                                    if (onTimedMetaDataAvailableListener != null && (msg.obj instanceof Parcel)) {
                                        Parcel parcel3 = (Parcel) msg.obj;
                                        TimedMetaData data2 = TimedMetaData.createTimedMetaDataFromParcel(parcel3);
                                        parcel3.recycle();
                                        onTimedMetaDataAvailableListener.onTimedMetaDataAvailable(this.mMediaPlayer, data2);
                                        return;
                                    }
                                    return;
                                default:
                                    switch (i) {
                                        case 210:
                                            Log.m66v(MediaPlayer.TAG, "MEDIA_DRM_INFO " + MediaPlayer.this.mOnDrmInfoHandlerDelegate);
                                            if (msg.obj == null) {
                                                Log.m64w(MediaPlayer.TAG, "MEDIA_DRM_INFO msg.obj=NULL");
                                                return;
                                            } else if (msg.obj instanceof Parcel) {
                                                DrmInfo drmInfo = null;
                                                synchronized (MediaPlayer.this.mDrmLock) {
                                                    if (MediaPlayer.this.mOnDrmInfoHandlerDelegate != null && MediaPlayer.this.mDrmInfo != null) {
                                                        drmInfo = MediaPlayer.this.mDrmInfo.makeCopy();
                                                    }
                                                    onDrmInfoHandlerDelegate = MediaPlayer.this.mOnDrmInfoHandlerDelegate;
                                                }
                                                if (onDrmInfoHandlerDelegate != null) {
                                                    onDrmInfoHandlerDelegate.notifyClient(drmInfo);
                                                    return;
                                                }
                                                return;
                                            } else {
                                                Log.m64w(MediaPlayer.TAG, "MEDIA_DRM_INFO msg.obj of unexpected type " + msg.obj);
                                                return;
                                            }
                                        case 211:
                                            synchronized (this) {
                                                mediaTimeListener = MediaPlayer.this.mOnMediaTimeDiscontinuityListener;
                                                mediaTimeHandler = MediaPlayer.this.mOnMediaTimeDiscontinuityHandler;
                                            }
                                            if (mediaTimeListener != null && (msg.obj instanceof Parcel)) {
                                                Parcel parcel4 = (Parcel) msg.obj;
                                                parcel4.setDataPosition(0);
                                                long anchorMediaUs = parcel4.readLong();
                                                long anchorRealUs = parcel4.readLong();
                                                float playbackRate = parcel4.readFloat();
                                                parcel4.recycle();
                                                if (anchorMediaUs != -1 && anchorRealUs != -1) {
                                                    timestamp = new MediaTimestamp(anchorMediaUs, anchorRealUs * 1000, playbackRate);
                                                } else {
                                                    timestamp = MediaTimestamp.TIMESTAMP_UNKNOWN;
                                                }
                                                if (mediaTimeHandler != null) {
                                                    mediaTimeHandler.post(new Runnable() { // from class: android.media.MediaPlayer.EventHandler.2
                                                        @Override // java.lang.Runnable
                                                        public void run() {
                                                            mediaTimeListener.onMediaTimeDiscontinuity(EventHandler.this.mMediaPlayer, timestamp);
                                                        }
                                                    });
                                                    return;
                                                } else {
                                                    mediaTimeListener.onMediaTimeDiscontinuity(this.mMediaPlayer, timestamp);
                                                    return;
                                                }
                                            }
                                            return;
                                        default:
                                            Log.m70e(MediaPlayer.TAG, "Unknown message type " + msg.what);
                                            return;
                                    }
                            }
                    }
            }
            TimeProvider timeProvider5 = MediaPlayer.this.mTimeProvider;
            if (timeProvider5 != null) {
                timeProvider5.onSeekComplete(this.mMediaPlayer);
            }
        }
    }

    private static void postEventFromNative(Object mediaplayer_ref, int what, int arg1, int arg2, Object obj) {
        MediaPlayer mp = (MediaPlayer) ((WeakReference) mediaplayer_ref).get();
        if (mp == null) {
            return;
        }
        if (what == 1) {
            synchronized (mp.mDrmLock) {
                mp.mDrmInfoResolved = true;
            }
        } else if (what != 200) {
            if (what == 210) {
                Log.m66v(TAG, "postEventFromNative MEDIA_DRM_INFO");
                if (obj instanceof Parcel) {
                    Parcel parcel = (Parcel) obj;
                    DrmInfo drmInfo = new DrmInfo(parcel);
                    synchronized (mp.mDrmLock) {
                        mp.mDrmInfo = drmInfo;
                    }
                } else {
                    Log.m64w(TAG, "MEDIA_DRM_INFO msg.obj of unexpected type " + obj);
                }
            }
        } else if (arg1 == 2) {
            new Thread(new Runnable() { // from class: android.media.MediaPlayer.6
                @Override // java.lang.Runnable
                public void run() {
                    MediaPlayer.this.start();
                }
            }).start();
            Thread.yield();
        }
        if (mp.mEventHandler != null) {
            Message m = mp.mEventHandler.obtainMessage(what, arg1, arg2, obj);
            mp.mEventHandler.sendMessage(m);
        }
    }

    public void setOnPreparedListener(OnPreparedListener listener) {
        this.mOnPreparedListener = listener;
    }

    public void setOnCompletionListener(OnCompletionListener listener) {
        this.mOnCompletionListener = listener;
    }

    public void setOnBufferingUpdateListener(OnBufferingUpdateListener listener) {
        this.mOnBufferingUpdateListener = listener;
    }

    public void setOnSeekCompleteListener(OnSeekCompleteListener listener) {
        this.mOnSeekCompleteListener = listener;
    }

    public void setOnVideoSizeChangedListener(OnVideoSizeChangedListener listener) {
        this.mOnVideoSizeChangedListener = listener;
    }

    public void setOnTimedTextListener(OnTimedTextListener listener) {
        this.mOnTimedTextListener = listener;
    }

    public void setOnSubtitleDataListener(OnSubtitleDataListener listener, Handler handler) {
        if (listener == null) {
            throw new IllegalArgumentException("Illegal null listener");
        }
        if (handler == null) {
            throw new IllegalArgumentException("Illegal null handler");
        }
        setOnSubtitleDataListenerInt(listener, handler);
    }

    public void setOnSubtitleDataListener(OnSubtitleDataListener listener) {
        if (listener == null) {
            throw new IllegalArgumentException("Illegal null listener");
        }
        setOnSubtitleDataListenerInt(listener, null);
    }

    public void clearOnSubtitleDataListener() {
        setOnSubtitleDataListenerInt(null, null);
    }

    private void setOnSubtitleDataListenerInt(OnSubtitleDataListener listener, Handler handler) {
        synchronized (this) {
            this.mExtSubtitleDataListener = listener;
            this.mExtSubtitleDataHandler = handler;
        }
    }

    public void setOnMediaTimeDiscontinuityListener(OnMediaTimeDiscontinuityListener listener, Handler handler) {
        if (listener == null) {
            throw new IllegalArgumentException("Illegal null listener");
        }
        if (handler == null) {
            throw new IllegalArgumentException("Illegal null handler");
        }
        setOnMediaTimeDiscontinuityListenerInt(listener, handler);
    }

    public void setOnMediaTimeDiscontinuityListener(OnMediaTimeDiscontinuityListener listener) {
        if (listener == null) {
            throw new IllegalArgumentException("Illegal null listener");
        }
        setOnMediaTimeDiscontinuityListenerInt(listener, null);
    }

    public void clearOnMediaTimeDiscontinuityListener() {
        setOnMediaTimeDiscontinuityListenerInt(null, null);
    }

    private void setOnMediaTimeDiscontinuityListenerInt(OnMediaTimeDiscontinuityListener listener, Handler handler) {
        synchronized (this) {
            this.mOnMediaTimeDiscontinuityListener = listener;
            this.mOnMediaTimeDiscontinuityHandler = handler;
        }
    }

    public void setOnTimedMetaDataAvailableListener(OnTimedMetaDataAvailableListener listener) {
        this.mOnTimedMetaDataAvailableListener = listener;
    }

    public void setOnErrorListener(OnErrorListener listener) {
        this.mOnErrorListener = listener;
    }

    public void setOnInfoListener(OnInfoListener listener) {
        this.mOnInfoListener = listener;
    }

    public void setOnDrmConfigHelper(OnDrmConfigHelper listener) {
        synchronized (this.mDrmLock) {
            this.mOnDrmConfigHelper = listener;
        }
    }

    public void setOnDrmInfoListener(OnDrmInfoListener listener) {
        setOnDrmInfoListener(listener, null);
    }

    public void setOnDrmInfoListener(OnDrmInfoListener listener, Handler handler) {
        synchronized (this.mDrmLock) {
            try {
                if (listener != null) {
                    this.mOnDrmInfoHandlerDelegate = new OnDrmInfoHandlerDelegate(this, listener, handler);
                } else {
                    this.mOnDrmInfoHandlerDelegate = null;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void setOnDrmPreparedListener(OnDrmPreparedListener listener) {
        setOnDrmPreparedListener(listener, null);
    }

    public void setOnDrmPreparedListener(OnDrmPreparedListener listener, Handler handler) {
        synchronized (this.mDrmLock) {
            try {
                if (listener != null) {
                    this.mOnDrmPreparedHandlerDelegate = new OnDrmPreparedHandlerDelegate(this, listener, handler);
                } else {
                    this.mOnDrmPreparedHandlerDelegate = null;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* loaded from: classes3.dex */
    private class OnDrmInfoHandlerDelegate {
        private Handler mHandler;
        private MediaPlayer mMediaPlayer;
        private OnDrmInfoListener mOnDrmInfoListener;

        OnDrmInfoHandlerDelegate(MediaPlayer mp, OnDrmInfoListener listener, Handler handler) {
            this.mMediaPlayer = mp;
            this.mOnDrmInfoListener = listener;
            if (handler != null) {
                this.mHandler = handler;
            }
        }

        void notifyClient(final DrmInfo drmInfo) {
            if (this.mHandler != null) {
                this.mHandler.post(new Runnable() { // from class: android.media.MediaPlayer.OnDrmInfoHandlerDelegate.1
                    @Override // java.lang.Runnable
                    public void run() {
                        OnDrmInfoHandlerDelegate.this.mOnDrmInfoListener.onDrmInfo(OnDrmInfoHandlerDelegate.this.mMediaPlayer, drmInfo);
                    }
                });
            } else {
                this.mOnDrmInfoListener.onDrmInfo(this.mMediaPlayer, drmInfo);
            }
        }
    }

    /* loaded from: classes3.dex */
    private class OnDrmPreparedHandlerDelegate {
        private Handler mHandler;
        private MediaPlayer mMediaPlayer;
        private OnDrmPreparedListener mOnDrmPreparedListener;

        OnDrmPreparedHandlerDelegate(MediaPlayer mp, OnDrmPreparedListener listener, Handler handler) {
            this.mMediaPlayer = mp;
            this.mOnDrmPreparedListener = listener;
            if (handler == null) {
                if (MediaPlayer.this.mEventHandler != null) {
                    this.mHandler = MediaPlayer.this.mEventHandler;
                    return;
                } else {
                    Log.m70e(MediaPlayer.TAG, "OnDrmPreparedHandlerDelegate: Unexpected null mEventHandler");
                    return;
                }
            }
            this.mHandler = handler;
        }

        void notifyClient(final int status) {
            if (this.mHandler != null) {
                this.mHandler.post(new Runnable() { // from class: android.media.MediaPlayer.OnDrmPreparedHandlerDelegate.1
                    @Override // java.lang.Runnable
                    public void run() {
                        OnDrmPreparedHandlerDelegate.this.mOnDrmPreparedListener.onDrmPrepared(OnDrmPreparedHandlerDelegate.this.mMediaPlayer, status);
                    }
                });
            } else {
                Log.m70e(MediaPlayer.TAG, "OnDrmPreparedHandlerDelegate:notifyClient: Unexpected null mHandler");
            }
        }
    }

    public DrmInfo getDrmInfo() {
        DrmInfo drmInfo = null;
        synchronized (this.mDrmLock) {
            if (!this.mDrmInfoResolved && this.mDrmInfo == null) {
                Log.m66v(TAG, "The Player has not been prepared yet");
                throw new IllegalStateException("The Player has not been prepared yet");
            }
            if (this.mDrmInfo != null) {
                drmInfo = this.mDrmInfo.makeCopy();
            }
        }
        return drmInfo;
    }

    /* JADX WARN: Code restructure failed: missing block: B:29:0x0064, code lost:
        if (0 != 0) goto L33;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void prepareDrm(UUID uuid) throws UnsupportedSchemeException, ResourceBusyException, ProvisioningNetworkErrorException, ProvisioningServerErrorException {
        OnDrmPreparedHandlerDelegate onDrmPreparedHandlerDelegate;
        Log.m66v(TAG, "prepareDrm: uuid: " + uuid + " mOnDrmConfigHelper: " + this.mOnDrmConfigHelper);
        boolean allDoneWithoutProvisioning = false;
        synchronized (this.mDrmLock) {
            if (this.mDrmInfo == null) {
                Log.m70e(TAG, "prepareDrm(): Wrong usage: The player must be prepared and DRM info be retrieved before this call.");
                throw new IllegalStateException("prepareDrm(): Wrong usage: The player must be prepared and DRM info be retrieved before this call.");
            } else if (this.mActiveDrmScheme) {
                String msg = "prepareDrm(): Wrong usage: There is already an active DRM scheme with " + this.mDrmUUID;
                Log.m70e(TAG, msg);
                throw new IllegalStateException(msg);
            } else if (this.mPrepareDrmInProgress) {
                Log.m70e(TAG, "prepareDrm(): Wrong usage: There is already a pending prepareDrm call.");
                throw new IllegalStateException("prepareDrm(): Wrong usage: There is already a pending prepareDrm call.");
            } else if (this.mDrmProvisioningInProgress) {
                Log.m70e(TAG, "prepareDrm(): Unexpectd: Provisioning is already in progress.");
                throw new IllegalStateException("prepareDrm(): Unexpectd: Provisioning is already in progress.");
            } else {
                cleanDrmObj();
                this.mPrepareDrmInProgress = true;
                onDrmPreparedHandlerDelegate = this.mOnDrmPreparedHandlerDelegate;
                try {
                    prepareDrm_createDrmStep(uuid);
                    this.mDrmConfigAllowed = true;
                } catch (Exception e) {
                    Log.m63w(TAG, "prepareDrm(): Exception ", e);
                    this.mPrepareDrmInProgress = false;
                    throw e;
                }
            }
        }
        if (this.mOnDrmConfigHelper != null) {
            this.mOnDrmConfigHelper.onDrmConfig(this);
        }
        synchronized (this.mDrmLock) {
            this.mDrmConfigAllowed = false;
            try {
                try {
                    prepareDrm_openSessionStep(uuid);
                    this.mDrmUUID = uuid;
                    this.mActiveDrmScheme = true;
                    allDoneWithoutProvisioning = true;
                    if (!this.mDrmProvisioningInProgress) {
                        this.mPrepareDrmInProgress = false;
                    }
                } catch (IllegalStateException e2) {
                    Log.m70e(TAG, "prepareDrm(): Wrong usage: The player must be in the prepared state to call prepareDrm().");
                    throw new IllegalStateException("prepareDrm(): Wrong usage: The player must be in the prepared state to call prepareDrm().");
                } catch (Exception e3) {
                    Log.m70e(TAG, "prepareDrm: Exception " + e3);
                    throw e3;
                }
            } catch (NotProvisionedException e4) {
                Log.m64w(TAG, "prepareDrm: NotProvisionedException");
                int result = HandleProvisioninig(uuid);
                if (result != 0) {
                    switch (result) {
                        case 1:
                            Log.m70e(TAG, "prepareDrm: Provisioning was required but failed due to a network error.");
                            throw new ProvisioningNetworkErrorException("prepareDrm: Provisioning was required but failed due to a network error.");
                        case 2:
                            Log.m70e(TAG, "prepareDrm: Provisioning was required but the request was denied by the server.");
                            throw new ProvisioningServerErrorException("prepareDrm: Provisioning was required but the request was denied by the server.");
                        default:
                            Log.m70e(TAG, "prepareDrm: Post-provisioning preparation failed.");
                            throw new IllegalStateException("prepareDrm: Post-provisioning preparation failed.");
                    }
                }
                if (!this.mDrmProvisioningInProgress) {
                    this.mPrepareDrmInProgress = false;
                }
                if (0 != 0) {
                    cleanDrmObj();
                }
                if (allDoneWithoutProvisioning) {
                    return;
                }
                return;
            }
        }
        if (allDoneWithoutProvisioning || onDrmPreparedHandlerDelegate == null) {
            return;
        }
        onDrmPreparedHandlerDelegate.notifyClient(0);
    }

    public void releaseDrm() throws NoDrmSchemeException {
        Log.m66v(TAG, "releaseDrm:");
        synchronized (this.mDrmLock) {
            if (!this.mActiveDrmScheme) {
                Log.m70e(TAG, "releaseDrm(): No active DRM scheme to release.");
                throw new NoDrmSchemeException("releaseDrm: No active DRM scheme to release.");
            }
            try {
                _releaseDrm();
                cleanDrmObj();
                this.mActiveDrmScheme = false;
            } catch (IllegalStateException e) {
                Log.m63w(TAG, "releaseDrm: Exception ", e);
                throw new IllegalStateException("releaseDrm: The player is not in a valid state.");
            } catch (Exception e2) {
                Log.m69e(TAG, "releaseDrm: Exception ", e2);
            }
        }
    }

    public MediaDrm.KeyRequest getKeyRequest(byte[] keySetId, byte[] initData, String mimeType, int keyType, Map<String, String> optionalParameters) throws NoDrmSchemeException {
        byte[] scope;
        HashMap<String, String> hashMap;
        MediaDrm.KeyRequest request;
        Log.m66v(TAG, "getKeyRequest:  keySetId: " + keySetId + " initData:" + initData + " mimeType: " + mimeType + " keyType: " + keyType + " optionalParameters: " + optionalParameters);
        synchronized (this.mDrmLock) {
            if (!this.mActiveDrmScheme) {
                Log.m70e(TAG, "getKeyRequest NoDrmSchemeException");
                throw new NoDrmSchemeException("getKeyRequest: Has to set a DRM scheme first.");
            }
            if (keyType != 3) {
                try {
                    scope = this.mDrmSessionId;
                } catch (NotProvisionedException e) {
                    Log.m64w(TAG, "getKeyRequest NotProvisionedException: Unexpected. Shouldn't have reached here.");
                    throw new IllegalStateException("getKeyRequest: Unexpected provisioning error.");
                } catch (Exception e2) {
                    Log.m64w(TAG, "getKeyRequest Exception " + e2);
                    throw e2;
                }
            } else {
                scope = keySetId;
            }
            if (optionalParameters != null) {
                hashMap = new HashMap<>(optionalParameters);
            } else {
                hashMap = null;
            }
            HashMap<String, String> hmapOptionalParameters = hashMap;
            request = this.mDrmObj.getKeyRequest(scope, initData, mimeType, keyType, hmapOptionalParameters);
            Log.m66v(TAG, "getKeyRequest:   --> request: " + request);
        }
        return request;
    }

    public byte[] provideKeyResponse(byte[] keySetId, byte[] response) throws NoDrmSchemeException, DeniedByServerException {
        byte[] scope;
        byte[] keySetResult;
        Log.m66v(TAG, "provideKeyResponse: keySetId: " + keySetId + " response: " + response);
        synchronized (this.mDrmLock) {
            if (!this.mActiveDrmScheme) {
                Log.m70e(TAG, "getKeyRequest NoDrmSchemeException");
                throw new NoDrmSchemeException("getKeyRequest: Has to set a DRM scheme first.");
            }
            if (keySetId == null) {
                try {
                    scope = this.mDrmSessionId;
                } catch (NotProvisionedException e) {
                    Log.m64w(TAG, "provideKeyResponse NotProvisionedException: Unexpected. Shouldn't have reached here.");
                    throw new IllegalStateException("provideKeyResponse: Unexpected provisioning error.");
                } catch (Exception e2) {
                    Log.m64w(TAG, "provideKeyResponse Exception " + e2);
                    throw e2;
                }
            } else {
                scope = keySetId;
            }
            keySetResult = this.mDrmObj.provideKeyResponse(scope, response);
            Log.m66v(TAG, "provideKeyResponse: keySetId: " + keySetId + " response: " + response + " --> " + keySetResult);
        }
        return keySetResult;
    }

    public void restoreKeys(byte[] keySetId) throws NoDrmSchemeException {
        Log.m66v(TAG, "restoreKeys: keySetId: " + keySetId);
        synchronized (this.mDrmLock) {
            if (!this.mActiveDrmScheme) {
                Log.m64w(TAG, "restoreKeys NoDrmSchemeException");
                throw new NoDrmSchemeException("restoreKeys: Has to set a DRM scheme first.");
            }
            try {
                this.mDrmObj.restoreKeys(this.mDrmSessionId, keySetId);
            } catch (Exception e) {
                Log.m64w(TAG, "restoreKeys Exception " + e);
                throw e;
            }
        }
    }

    public String getDrmPropertyString(String propertyName) throws NoDrmSchemeException {
        String value;
        Log.m66v(TAG, "getDrmPropertyString: propertyName: " + propertyName);
        synchronized (this.mDrmLock) {
            if (!this.mActiveDrmScheme && !this.mDrmConfigAllowed) {
                Log.m64w(TAG, "getDrmPropertyString NoDrmSchemeException");
                throw new NoDrmSchemeException("getDrmPropertyString: Has to prepareDrm() first.");
            }
            try {
                value = this.mDrmObj.getPropertyString(propertyName);
            } catch (Exception e) {
                Log.m64w(TAG, "getDrmPropertyString Exception " + e);
                throw e;
            }
        }
        Log.m66v(TAG, "getDrmPropertyString: propertyName: " + propertyName + " --> value: " + value);
        return value;
    }

    public void setDrmPropertyString(String propertyName, String value) throws NoDrmSchemeException {
        Log.m66v(TAG, "setDrmPropertyString: propertyName: " + propertyName + " value: " + value);
        synchronized (this.mDrmLock) {
            if (!this.mActiveDrmScheme && !this.mDrmConfigAllowed) {
                Log.m64w(TAG, "setDrmPropertyString NoDrmSchemeException");
                throw new NoDrmSchemeException("setDrmPropertyString: Has to prepareDrm() first.");
            }
            try {
                this.mDrmObj.setPropertyString(propertyName, value);
            } catch (Exception e) {
                Log.m64w(TAG, "setDrmPropertyString Exception " + e);
                throw e;
            }
        }
    }

    /* loaded from: classes3.dex */
    public static final class DrmInfo {
        private Map<UUID, byte[]> mapPssh;
        private UUID[] supportedSchemes;

        public Map<UUID, byte[]> getPssh() {
            return this.mapPssh;
        }

        public UUID[] getSupportedSchemes() {
            return this.supportedSchemes;
        }

        private DrmInfo(Map<UUID, byte[]> Pssh, UUID[] SupportedSchemes) {
            this.mapPssh = Pssh;
            this.supportedSchemes = SupportedSchemes;
        }

        private DrmInfo(Parcel parcel) {
            Log.m66v(MediaPlayer.TAG, "DrmInfo(" + parcel + ") size " + parcel.dataSize());
            int psshsize = parcel.readInt();
            byte[] pssh = new byte[psshsize];
            parcel.readByteArray(pssh);
            Log.m66v(MediaPlayer.TAG, "DrmInfo() PSSH: " + arrToHex(pssh));
            this.mapPssh = parsePSSH(pssh, psshsize);
            Log.m66v(MediaPlayer.TAG, "DrmInfo() PSSH: " + this.mapPssh);
            int supportedDRMsCount = parcel.readInt();
            this.supportedSchemes = new UUID[supportedDRMsCount];
            for (int i = 0; i < supportedDRMsCount; i++) {
                byte[] uuid = new byte[16];
                parcel.readByteArray(uuid);
                this.supportedSchemes[i] = bytesToUUID(uuid);
                Log.m66v(MediaPlayer.TAG, "DrmInfo() supportedScheme[" + i + "]: " + this.supportedSchemes[i]);
            }
            Log.m66v(MediaPlayer.TAG, "DrmInfo() Parcel psshsize: " + psshsize + " supportedDRMsCount: " + supportedDRMsCount);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public DrmInfo makeCopy() {
            return new DrmInfo(this.mapPssh, this.supportedSchemes);
        }

        private String arrToHex(byte[] bytes) {
            String out = "0x";
            for (int i = 0; i < bytes.length; i++) {
                out = out + String.format("%02x", Byte.valueOf(bytes[i]));
            }
            return out;
        }

        private UUID bytesToUUID(byte[] uuid) {
            long msb = 0;
            long lsb = 0;
            for (int i = 0; i < 8; i++) {
                msb |= (uuid[i] & 255) << ((7 - i) * 8);
                lsb |= (uuid[i + 8] & 255) << ((7 - i) * 8);
            }
            return new UUID(msb, lsb);
        }

        private Map<UUID, byte[]> parsePSSH(byte[] pssh, int psshsize) {
            int datalen;
            Map<UUID, byte[]> result = new HashMap<>();
            char c = 0;
            int numentries = 0;
            int numentries2 = psshsize;
            int len = 0;
            while (numentries2 > 0) {
                if (numentries2 < 16) {
                    Object[] objArr = new Object[2];
                    objArr[c] = Integer.valueOf(numentries2);
                    objArr[1] = Integer.valueOf(psshsize);
                    Log.m64w(MediaPlayer.TAG, String.format("parsePSSH: len is too short to parse UUID: (%d < 16) pssh: %d", objArr));
                    return null;
                }
                UUID uuid = bytesToUUID(Arrays.copyOfRange(pssh, len, len + 16));
                int i = len + 16;
                int len2 = numentries2 - 16;
                if (len2 < 4) {
                    Object[] objArr2 = new Object[2];
                    objArr2[c] = Integer.valueOf(len2);
                    objArr2[1] = Integer.valueOf(psshsize);
                    Log.m64w(MediaPlayer.TAG, String.format("parsePSSH: len is too short to parse datalen: (%d < 4) pssh: %d", objArr2));
                    return null;
                }
                byte[] subset = Arrays.copyOfRange(pssh, i, i + 4);
                if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
                    datalen = ((subset[3] & 255) << 24) | ((subset[2] & 255) << 16) | ((subset[1] & 255) << 8) | (subset[0] & 255);
                } else {
                    datalen = ((subset[0] & 255) << 24) | ((subset[1] & 255) << 16) | ((subset[2] & 255) << 8) | (subset[3] & 255);
                }
                int i2 = i + 4;
                int len3 = len2 - 4;
                if (len3 < datalen) {
                    Log.m64w(MediaPlayer.TAG, String.format("parsePSSH: len is too short to parse data: (%d < %d) pssh: %d", Integer.valueOf(len3), Integer.valueOf(datalen), Integer.valueOf(psshsize)));
                    return null;
                }
                byte[] data = Arrays.copyOfRange(pssh, i2, i2 + datalen);
                len = i2 + datalen;
                numentries2 = len3 - datalen;
                Log.m66v(MediaPlayer.TAG, String.format("parsePSSH[%d]: <%s, %s> pssh: %d", Integer.valueOf(numentries), uuid, arrToHex(data), Integer.valueOf(psshsize)));
                numentries++;
                result.put(uuid, data);
                c = 0;
            }
            return result;
        }
    }

    /* loaded from: classes3.dex */
    public static final class NoDrmSchemeException extends MediaDrmException {
        public NoDrmSchemeException(String detailMessage) {
            super(detailMessage);
        }
    }

    /* loaded from: classes3.dex */
    public static final class ProvisioningNetworkErrorException extends MediaDrmException {
        public ProvisioningNetworkErrorException(String detailMessage) {
            super(detailMessage);
        }
    }

    /* loaded from: classes3.dex */
    public static final class ProvisioningServerErrorException extends MediaDrmException {
        public ProvisioningServerErrorException(String detailMessage) {
            super(detailMessage);
        }
    }

    private void prepareDrm_createDrmStep(UUID uuid) throws UnsupportedSchemeException {
        Log.m66v(TAG, "prepareDrm_createDrmStep: UUID: " + uuid);
        try {
            this.mDrmObj = new MediaDrm(uuid);
            Log.m66v(TAG, "prepareDrm_createDrmStep: Created mDrmObj=" + this.mDrmObj);
        } catch (Exception e) {
            Log.m70e(TAG, "prepareDrm_createDrmStep: MediaDrm failed with " + e);
            throw e;
        }
    }

    private void prepareDrm_openSessionStep(UUID uuid) throws NotProvisionedException, ResourceBusyException {
        Log.m66v(TAG, "prepareDrm_openSessionStep: uuid: " + uuid);
        try {
            this.mDrmSessionId = this.mDrmObj.openSession();
            Log.m66v(TAG, "prepareDrm_openSessionStep: mDrmSessionId=" + this.mDrmSessionId);
            _prepareDrm(getByteArrayFromUUID(uuid), this.mDrmSessionId);
            Log.m66v(TAG, "prepareDrm_openSessionStep: _prepareDrm/Crypto succeeded");
        } catch (Exception e) {
            Log.m70e(TAG, "prepareDrm_openSessionStep: open/crypto failed with " + e);
            throw e;
        }
    }

    /* loaded from: classes3.dex */
    private class ProvisioningThread extends Thread {
        public static final int TIMEOUT_MS = 60000;
        private Object drmLock;
        private boolean finished;
        private MediaPlayer mediaPlayer;
        private OnDrmPreparedHandlerDelegate onDrmPreparedHandlerDelegate;
        private int status;
        private String urlStr;
        private UUID uuid;

        private ProvisioningThread() {
        }

        public int status() {
            return this.status;
        }

        public ProvisioningThread initialize(MediaDrm.ProvisionRequest request, UUID uuid, MediaPlayer mediaPlayer) {
            this.drmLock = mediaPlayer.mDrmLock;
            this.onDrmPreparedHandlerDelegate = mediaPlayer.mOnDrmPreparedHandlerDelegate;
            this.mediaPlayer = mediaPlayer;
            this.urlStr = request.getDefaultUrl() + "&signedRequest=" + new String(request.getData());
            this.uuid = uuid;
            this.status = 3;
            Log.m66v(MediaPlayer.TAG, "HandleProvisioninig: Thread is initialised url: " + this.urlStr);
            return this;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            byte[] response = null;
            boolean provisioningSucceeded = false;
            try {
                URL url = new URL(this.urlStr);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                try {
                    connection.setRequestMethod("POST");
                    connection.setDoOutput(false);
                    connection.setDoInput(true);
                    connection.setConnectTimeout(TIMEOUT_MS);
                    connection.setReadTimeout(TIMEOUT_MS);
                    connection.connect();
                    response = Streams.readFully(connection.getInputStream());
                    Log.m66v(MediaPlayer.TAG, "HandleProvisioninig: Thread run: response " + response.length + WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER + response);
                    connection.disconnect();
                } catch (Exception e) {
                    this.status = 1;
                    Log.m64w(MediaPlayer.TAG, "HandleProvisioninig: Thread run: connect " + e + " url: " + url);
                    connection.disconnect();
                }
            } catch (Exception e2) {
                this.status = 1;
                Log.m64w(MediaPlayer.TAG, "HandleProvisioninig: Thread run: openConnection " + e2);
            }
            if (response != null) {
                try {
                    MediaPlayer.this.mDrmObj.provideProvisionResponse(response);
                    Log.m66v(MediaPlayer.TAG, "HandleProvisioninig: Thread run: provideProvisionResponse SUCCEEDED!");
                    provisioningSucceeded = true;
                } catch (Exception e3) {
                    this.status = 2;
                    Log.m64w(MediaPlayer.TAG, "HandleProvisioninig: Thread run: provideProvisionResponse " + e3);
                }
            }
            boolean succeeded = false;
            if (this.onDrmPreparedHandlerDelegate != null) {
                synchronized (this.drmLock) {
                    if (provisioningSucceeded) {
                        try {
                            succeeded = this.mediaPlayer.resumePrepareDrm(this.uuid);
                            this.status = succeeded ? 0 : 3;
                        } finally {
                        }
                    }
                    this.mediaPlayer.mDrmProvisioningInProgress = false;
                    this.mediaPlayer.mPrepareDrmInProgress = false;
                    if (!succeeded) {
                        MediaPlayer.this.cleanDrmObj();
                    }
                }
                this.onDrmPreparedHandlerDelegate.notifyClient(this.status);
            } else {
                if (provisioningSucceeded) {
                    succeeded = this.mediaPlayer.resumePrepareDrm(this.uuid);
                    this.status = succeeded ? 0 : 3;
                }
                this.mediaPlayer.mDrmProvisioningInProgress = false;
                this.mediaPlayer.mPrepareDrmInProgress = false;
                if (!succeeded) {
                    MediaPlayer.this.cleanDrmObj();
                }
            }
            this.finished = true;
        }
    }

    private int HandleProvisioninig(UUID uuid) {
        if (this.mDrmProvisioningInProgress) {
            Log.m70e(TAG, "HandleProvisioninig: Unexpected mDrmProvisioningInProgress");
            return 3;
        }
        MediaDrm.ProvisionRequest provReq = this.mDrmObj.getProvisionRequest();
        if (provReq == null) {
            Log.m70e(TAG, "HandleProvisioninig: getProvisionRequest returned null.");
            return 3;
        }
        Log.m66v(TAG, "HandleProvisioninig provReq  data: " + provReq.getData() + " url: " + provReq.getDefaultUrl());
        this.mDrmProvisioningInProgress = true;
        this.mDrmProvisioningThread = new ProvisioningThread().initialize(provReq, uuid, this);
        this.mDrmProvisioningThread.start();
        if (this.mOnDrmPreparedHandlerDelegate != null) {
            return 0;
        }
        try {
            this.mDrmProvisioningThread.join();
        } catch (Exception e) {
            Log.m64w(TAG, "HandleProvisioninig: Thread.join Exception " + e);
        }
        int result = this.mDrmProvisioningThread.status();
        this.mDrmProvisioningThread = null;
        return result;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean resumePrepareDrm(UUID uuid) {
        Log.m66v(TAG, "resumePrepareDrm: uuid: " + uuid);
        try {
            prepareDrm_openSessionStep(uuid);
            this.mDrmUUID = uuid;
            this.mActiveDrmScheme = true;
            return true;
        } catch (Exception e) {
            Log.m64w(TAG, "HandleProvisioninig: Thread run _prepareDrm resume failed with " + e);
            return false;
        }
    }

    private void resetDrmState() {
        synchronized (this.mDrmLock) {
            Log.m66v(TAG, "resetDrmState:  mDrmInfo=" + this.mDrmInfo + " mDrmProvisioningThread=" + this.mDrmProvisioningThread + " mPrepareDrmInProgress=" + this.mPrepareDrmInProgress + " mActiveDrmScheme=" + this.mActiveDrmScheme);
            this.mDrmInfoResolved = false;
            this.mDrmInfo = null;
            if (this.mDrmProvisioningThread != null) {
                try {
                    this.mDrmProvisioningThread.join();
                } catch (InterruptedException e) {
                    Log.m64w(TAG, "resetDrmState: ProvThread.join Exception " + e);
                }
                this.mDrmProvisioningThread = null;
            }
            this.mPrepareDrmInProgress = false;
            this.mActiveDrmScheme = false;
            cleanDrmObj();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cleanDrmObj() {
        Log.m66v(TAG, "cleanDrmObj: mDrmObj=" + this.mDrmObj + " mDrmSessionId=" + this.mDrmSessionId);
        if (this.mDrmSessionId != null) {
            this.mDrmObj.closeSession(this.mDrmSessionId);
            this.mDrmSessionId = null;
        }
        if (this.mDrmObj != null) {
            this.mDrmObj.release();
            this.mDrmObj = null;
        }
    }

    private static final byte[] getByteArrayFromUUID(UUID uuid) {
        long msb = uuid.getMostSignificantBits();
        long lsb = uuid.getLeastSignificantBits();
        byte[] uuidBytes = new byte[16];
        for (int i = 0; i < 8; i++) {
            uuidBytes[i] = (byte) (msb >>> ((7 - i) * 8));
            uuidBytes[i + 8] = (byte) (lsb >>> ((7 - i) * 8));
        }
        return uuidBytes;
    }

    private boolean isVideoScalingModeSupported(int mode) {
        return mode == 1 || mode == 2;
    }

    /* loaded from: classes3.dex */
    static class TimeProvider implements OnSeekCompleteListener, MediaTimeProvider {
        private static final long MAX_EARLY_CALLBACK_US = 1000;
        private static final long MAX_NS_WITHOUT_POSITION_CHECK = 5000000000L;
        private static final int NOTIFY = 1;
        private static final int NOTIFY_SEEK = 3;
        private static final int NOTIFY_STOP = 2;
        private static final int NOTIFY_TIME = 0;
        private static final int NOTIFY_TRACK_DATA = 4;
        private static final String TAG = "MTP";
        private static final long TIME_ADJUSTMENT_RATE = 2;
        private boolean mBuffering;
        private Handler mEventHandler;
        private HandlerThread mHandlerThread;
        private long mLastReportedTime;
        private long mLastTimeUs;
        private MediaTimeProvider.OnMediaTimeListener[] mListeners;
        private MediaPlayer mPlayer;
        private boolean mRefresh;
        private long[] mTimes;
        private boolean mPaused = true;
        private boolean mStopped = true;
        private boolean mPausing = false;
        private boolean mSeeking = false;
        public boolean DEBUG = false;

        public TimeProvider(MediaPlayer mp) {
            this.mLastTimeUs = 0L;
            this.mRefresh = false;
            this.mPlayer = mp;
            try {
                getCurrentTimeUs(true, false);
            } catch (IllegalStateException e) {
                this.mRefresh = true;
            }
            Looper myLooper = Looper.myLooper();
            Looper looper = myLooper;
            if (myLooper == null) {
                Looper mainLooper = Looper.getMainLooper();
                looper = mainLooper;
                if (mainLooper == null) {
                    this.mHandlerThread = new HandlerThread("MediaPlayerMTPEventThread", -2);
                    this.mHandlerThread.start();
                    looper = this.mHandlerThread.getLooper();
                }
            }
            this.mEventHandler = new EventHandler(looper);
            this.mListeners = new MediaTimeProvider.OnMediaTimeListener[0];
            this.mTimes = new long[0];
            this.mLastTimeUs = 0L;
        }

        private void scheduleNotification(int type, long delayUs) {
            if (this.mSeeking && type == 0) {
                return;
            }
            if (this.DEBUG) {
                Log.m66v(TAG, "scheduleNotification " + type + " in " + delayUs);
            }
            this.mEventHandler.removeMessages(1);
            Message msg = this.mEventHandler.obtainMessage(1, type, 0);
            this.mEventHandler.sendMessageDelayed(msg, (int) (delayUs / 1000));
        }

        public void close() {
            this.mEventHandler.removeMessages(1);
            if (this.mHandlerThread != null) {
                this.mHandlerThread.quitSafely();
                this.mHandlerThread = null;
            }
        }

        protected void finalize() {
            if (this.mHandlerThread != null) {
                this.mHandlerThread.quitSafely();
            }
        }

        public void onNotifyTime() {
            synchronized (this) {
                if (this.DEBUG) {
                    Log.m72d(TAG, "onNotifyTime: ");
                }
                scheduleNotification(0, 0L);
            }
        }

        public void onPaused(boolean paused) {
            synchronized (this) {
                if (this.DEBUG) {
                    Log.m72d(TAG, "onPaused: " + paused);
                }
                if (this.mStopped) {
                    this.mStopped = false;
                    this.mSeeking = true;
                    scheduleNotification(3, 0L);
                } else {
                    this.mPausing = paused;
                    this.mSeeking = false;
                    scheduleNotification(0, 0L);
                }
            }
        }

        public void onBuffering(boolean buffering) {
            synchronized (this) {
                if (this.DEBUG) {
                    Log.m72d(TAG, "onBuffering: " + buffering);
                }
                this.mBuffering = buffering;
                scheduleNotification(0, 0L);
            }
        }

        public void onStopped() {
            synchronized (this) {
                if (this.DEBUG) {
                    Log.m72d(TAG, "onStopped");
                }
                this.mPaused = true;
                this.mStopped = true;
                this.mSeeking = false;
                this.mBuffering = false;
                scheduleNotification(2, 0L);
            }
        }

        @Override // android.media.MediaPlayer.OnSeekCompleteListener
        public void onSeekComplete(MediaPlayer mp) {
            synchronized (this) {
                this.mStopped = false;
                this.mSeeking = true;
                scheduleNotification(3, 0L);
            }
        }

        public void onNewPlayer() {
            if (this.mRefresh) {
                synchronized (this) {
                    this.mStopped = false;
                    this.mSeeking = true;
                    this.mBuffering = false;
                    scheduleNotification(3, 0L);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public synchronized void notifySeek() {
            MediaTimeProvider.OnMediaTimeListener[] onMediaTimeListenerArr;
            this.mSeeking = false;
            try {
                long timeUs = getCurrentTimeUs(true, false);
                if (this.DEBUG) {
                    Log.m72d(TAG, "onSeekComplete at " + timeUs);
                }
                for (MediaTimeProvider.OnMediaTimeListener listener : this.mListeners) {
                    if (listener == null) {
                        break;
                    }
                    listener.onSeek(timeUs);
                }
            } catch (IllegalStateException e) {
                if (this.DEBUG) {
                    Log.m72d(TAG, "onSeekComplete but no player");
                }
                this.mPausing = true;
                notifyTimedEvent(false);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public synchronized void notifyTrackData(Pair<SubtitleTrack, byte[]> trackData) {
            SubtitleTrack track = trackData.first;
            byte[] data = trackData.second;
            track.onData(data, true, -1L);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public synchronized void notifyStop() {
            MediaTimeProvider.OnMediaTimeListener[] onMediaTimeListenerArr;
            for (MediaTimeProvider.OnMediaTimeListener listener : this.mListeners) {
                if (listener == null) {
                    break;
                }
                listener.onStop();
            }
        }

        private int registerListener(MediaTimeProvider.OnMediaTimeListener listener) {
            int i = 0;
            while (i < this.mListeners.length && this.mListeners[i] != listener && this.mListeners[i] != null) {
                i++;
            }
            if (i >= this.mListeners.length) {
                MediaTimeProvider.OnMediaTimeListener[] newListeners = new MediaTimeProvider.OnMediaTimeListener[i + 1];
                long[] newTimes = new long[i + 1];
                System.arraycopy(this.mListeners, 0, newListeners, 0, this.mListeners.length);
                System.arraycopy(this.mTimes, 0, newTimes, 0, this.mTimes.length);
                this.mListeners = newListeners;
                this.mTimes = newTimes;
            }
            if (this.mListeners[i] == null) {
                this.mListeners[i] = listener;
                this.mTimes[i] = -1;
            }
            return i;
        }

        @Override // android.media.MediaTimeProvider
        public void notifyAt(long timeUs, MediaTimeProvider.OnMediaTimeListener listener) {
            synchronized (this) {
                if (this.DEBUG) {
                    Log.m72d(TAG, "notifyAt " + timeUs);
                }
                this.mTimes[registerListener(listener)] = timeUs;
                scheduleNotification(0, 0L);
            }
        }

        @Override // android.media.MediaTimeProvider
        public void scheduleUpdate(MediaTimeProvider.OnMediaTimeListener listener) {
            synchronized (this) {
                if (this.DEBUG) {
                    Log.m72d(TAG, "scheduleUpdate");
                }
                int i = registerListener(listener);
                if (!this.mStopped) {
                    this.mTimes[i] = 0;
                    scheduleNotification(0, 0L);
                }
            }
        }

        @Override // android.media.MediaTimeProvider
        public void cancelNotifications(MediaTimeProvider.OnMediaTimeListener listener) {
            synchronized (this) {
                int i = 0;
                while (true) {
                    if (i >= this.mListeners.length) {
                        break;
                    } else if (this.mListeners[i] == listener) {
                        System.arraycopy(this.mListeners, i + 1, this.mListeners, i, (this.mListeners.length - i) - 1);
                        System.arraycopy(this.mTimes, i + 1, this.mTimes, i, (this.mTimes.length - i) - 1);
                        this.mListeners[this.mListeners.length - 1] = null;
                        this.mTimes[this.mTimes.length - 1] = -1;
                        break;
                    } else if (this.mListeners[i] == null) {
                        break;
                    } else {
                        i++;
                    }
                }
                scheduleNotification(0, 0L);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public synchronized void notifyTimedEvent(boolean refreshTime) {
            long nowUs;
            long nowUs2;
            long nowUs3;
            try {
                nowUs = getCurrentTimeUs(refreshTime, true);
            } catch (IllegalStateException e) {
                this.mRefresh = true;
                this.mPausing = true;
                nowUs = getCurrentTimeUs(refreshTime, true);
            }
            long nextTimeUs = nowUs;
            if (this.mSeeking) {
                return;
            }
            int ix = 0;
            if (this.DEBUG) {
                StringBuilder sb = new StringBuilder();
                sb.append("notifyTimedEvent(");
                sb.append(this.mLastTimeUs);
                sb.append(" -> ");
                sb.append(nowUs);
                sb.append(") from {");
                long[] jArr = this.mTimes;
                int length = jArr.length;
                boolean first = true;
                int i = 0;
                while (i < length) {
                    long time = jArr[i];
                    long nowUs4 = nowUs;
                    if (time != -1) {
                        if (!first) {
                            sb.append(", ");
                        }
                        sb.append(time);
                        first = false;
                    }
                    i++;
                    nowUs = nowUs4;
                }
                nowUs2 = nowUs;
                sb.append("}");
                Log.m72d(TAG, sb.toString());
            } else {
                nowUs2 = nowUs;
            }
            Vector<MediaTimeProvider.OnMediaTimeListener> activatedListeners = new Vector<>();
            while (true) {
                int ix2 = ix;
                if (ix2 >= this.mTimes.length || this.mListeners[ix2] == null) {
                    break;
                }
                if (this.mTimes[ix2] > -1) {
                    if (this.mTimes[ix2] <= nowUs2 + 1000) {
                        activatedListeners.add(this.mListeners[ix2]);
                        if (this.DEBUG) {
                            Log.m72d(TAG, Environment.MEDIA_REMOVED);
                        }
                        this.mTimes[ix2] = -1;
                    } else if (nextTimeUs == nowUs2 || this.mTimes[ix2] < nextTimeUs) {
                        nextTimeUs = this.mTimes[ix2];
                    }
                }
                ix = ix2 + 1;
            }
            int ix3 = (nextTimeUs > nowUs2 ? 1 : (nextTimeUs == nowUs2 ? 0 : -1));
            if (ix3 > 0 && !this.mPaused) {
                if (this.DEBUG) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("scheduling for ");
                    sb2.append(nextTimeUs);
                    sb2.append(" and ");
                    nowUs3 = nowUs2;
                    sb2.append(nowUs3);
                    Log.m72d(TAG, sb2.toString());
                } else {
                    nowUs3 = nowUs2;
                }
                this.mPlayer.notifyAt(nextTimeUs);
            } else {
                nowUs3 = nowUs2;
                this.mEventHandler.removeMessages(1);
            }
            Iterator<MediaTimeProvider.OnMediaTimeListener> it = activatedListeners.iterator();
            while (it.hasNext()) {
                MediaTimeProvider.OnMediaTimeListener listener = it.next();
                listener.onTimedEvent(nowUs3);
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:19:0x002f A[Catch: IllegalStateException -> 0x007f, all -> 0x00b7, TryCatch #0 {IllegalStateException -> 0x007f, blocks: (B:10:0x000d, B:12:0x0021, B:17:0x0029, B:19:0x002f, B:23:0x0041), top: B:53:0x000d, outer: #1 }] */
        @Override // android.media.MediaTimeProvider
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public long getCurrentTimeUs(boolean refreshTime, boolean monotonic) throws IllegalStateException {
            boolean z;
            synchronized (this) {
                if (this.mPaused && !refreshTime) {
                    return this.mLastReportedTime;
                }
                try {
                    this.mLastTimeUs = this.mPlayer.getCurrentPosition() * 1000;
                    if (this.mPlayer.isPlaying() && !this.mBuffering) {
                        z = false;
                        this.mPaused = z;
                        if (this.DEBUG) {
                            StringBuilder sb = new StringBuilder();
                            sb.append(this.mPaused ? "paused" : "playing");
                            sb.append(" at ");
                            sb.append(this.mLastTimeUs);
                            Log.m66v(TAG, sb.toString());
                        }
                        if (!monotonic && this.mLastTimeUs < this.mLastReportedTime) {
                            if (this.mLastReportedTime - this.mLastTimeUs > TimeUtils.NANOS_PER_MS) {
                                this.mStopped = false;
                                this.mSeeking = true;
                                scheduleNotification(3, 0L);
                            }
                        } else {
                            this.mLastReportedTime = this.mLastTimeUs;
                        }
                        return this.mLastReportedTime;
                    }
                    z = true;
                    this.mPaused = z;
                    if (this.DEBUG) {
                    }
                    if (!monotonic) {
                    }
                    this.mLastReportedTime = this.mLastTimeUs;
                    return this.mLastReportedTime;
                } catch (IllegalStateException e) {
                    if (this.mPausing) {
                        this.mPausing = false;
                        if (!monotonic || this.mLastReportedTime < this.mLastTimeUs) {
                            this.mLastReportedTime = this.mLastTimeUs;
                        }
                        this.mPaused = true;
                        if (this.DEBUG) {
                            Log.m72d(TAG, "illegal state, but pausing: estimating at " + this.mLastReportedTime);
                        }
                        return this.mLastReportedTime;
                    }
                    throw e;
                }
            }
        }

        /* loaded from: classes3.dex */
        private class EventHandler extends Handler {
            public EventHandler(Looper looper) {
                super(looper);
            }

            @Override // android.p007os.Handler
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    int i = msg.arg1;
                    if (i != 0) {
                        switch (i) {
                            case 2:
                                TimeProvider.this.notifyStop();
                                return;
                            case 3:
                                TimeProvider.this.notifySeek();
                                return;
                            case 4:
                                TimeProvider.this.notifyTrackData((Pair) msg.obj);
                                return;
                            default:
                                return;
                        }
                    }
                    TimeProvider.this.notifyTimedEvent(true);
                }
            }
        }
    }

    /* loaded from: classes3.dex */
    public static final class MetricsConstants {
        public static final String CODEC_AUDIO = "android.media.mediaplayer.audio.codec";
        public static final String CODEC_VIDEO = "android.media.mediaplayer.video.codec";
        public static final String DURATION = "android.media.mediaplayer.durationMs";
        public static final String ERRORS = "android.media.mediaplayer.err";
        public static final String ERROR_CODE = "android.media.mediaplayer.errcode";
        public static final String FRAMES = "android.media.mediaplayer.frames";
        public static final String FRAMES_DROPPED = "android.media.mediaplayer.dropped";
        public static final String HEIGHT = "android.media.mediaplayer.height";
        public static final String MIME_TYPE_AUDIO = "android.media.mediaplayer.audio.mime";
        public static final String MIME_TYPE_VIDEO = "android.media.mediaplayer.video.mime";
        public static final String PLAYING = "android.media.mediaplayer.playingMs";
        public static final String WIDTH = "android.media.mediaplayer.width";

        private MetricsConstants() {
        }
    }
}
