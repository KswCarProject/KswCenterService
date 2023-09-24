package android.preference;

import android.annotation.UnsupportedAppUsage;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.media.audiopolicy.AudioProductStrategy;
import android.net.Uri;
import android.p007os.Handler;
import android.p007os.HandlerThread;
import android.p007os.Message;
import android.preference.VolumePreference;
import android.provider.Settings;
import android.service.notification.ZenModeConfig;
import android.util.Log;
import android.widget.SeekBar;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.p016os.SomeArgs;
import java.util.concurrent.Executor;
import java.util.function.Function;
import java.util.function.Predicate;

@Deprecated
/* loaded from: classes3.dex */
public class SeekBarVolumizer implements SeekBar.OnSeekBarChangeListener, Handler.Callback {
    private static final int CHECK_RINGTONE_PLAYBACK_DELAY_MS = 1000;
    private static final int MSG_GROUP_VOLUME_CHANGED = 1;
    private static final int MSG_INIT_SAMPLE = 3;
    private static final int MSG_SET_STREAM_VOLUME = 0;
    private static final int MSG_START_SAMPLE = 1;
    private static final int MSG_STOP_SAMPLE = 2;
    private static final String TAG = "SeekBarVolumizer";
    private boolean mAffectedByRingerMode;
    private boolean mAllowAlarms;
    private boolean mAllowMedia;
    private boolean mAllowRinger;
    private AudioAttributes mAttributes;
    @UnsupportedAppUsage
    private final AudioManager mAudioManager;
    private final Callback mCallback;
    @UnsupportedAppUsage
    private final Context mContext;
    private final Uri mDefaultUri;
    private Handler mHandler;
    private int mLastAudibleStreamVolume;
    @UnsupportedAppUsage
    private int mLastProgress;
    private final int mMaxStreamVolume;
    private boolean mMuted;
    private final NotificationManager mNotificationManager;
    private boolean mNotificationOrRing;
    private NotificationManager.Policy mNotificationPolicy;
    @UnsupportedAppUsage
    private int mOriginalStreamVolume;
    private boolean mPlaySample;
    private final Receiver mReceiver;
    private int mRingerMode;
    @UnsupportedAppUsage
    @GuardedBy({"this"})
    private Ringtone mRingtone;
    @UnsupportedAppUsage
    private SeekBar mSeekBar;
    @UnsupportedAppUsage
    private final int mStreamType;
    private final HandlerC1625H mUiHandler;
    private int mVolumeBeforeMute;
    private final AudioManager.VolumeGroupCallback mVolumeGroupCallback;
    private int mVolumeGroupId;
    private final Handler mVolumeHandler;
    private Observer mVolumeObserver;
    private int mZenMode;

    /* loaded from: classes3.dex */
    public interface Callback {
        void onMuted(boolean z, boolean z2);

        void onProgressChanged(SeekBar seekBar, int i, boolean z);

        void onSampleStarting(SeekBarVolumizer seekBarVolumizer);
    }

    @UnsupportedAppUsage
    public SeekBarVolumizer(Context context, int streamType, Uri defaultUri, Callback callback) {
        this(context, streamType, defaultUri, callback, true);
    }

    public SeekBarVolumizer(Context context, int streamType, Uri defaultUri, Callback callback, boolean playSample) {
        this.mVolumeHandler = new VolumeHandler();
        this.mVolumeGroupCallback = new AudioManager.VolumeGroupCallback() { // from class: android.preference.SeekBarVolumizer.1
            @Override // android.media.AudioManager.VolumeGroupCallback
            public void onAudioVolumeGroupChanged(int group, int flags) {
                if (SeekBarVolumizer.this.mHandler == null) {
                    return;
                }
                SomeArgs args = SomeArgs.obtain();
                args.arg1 = Integer.valueOf(group);
                args.arg2 = Integer.valueOf(flags);
                SeekBarVolumizer.this.mVolumeHandler.sendMessage(SeekBarVolumizer.this.mHandler.obtainMessage(1, args));
            }
        };
        this.mUiHandler = new HandlerC1625H();
        this.mReceiver = new Receiver();
        this.mLastProgress = -1;
        this.mVolumeBeforeMute = -1;
        this.mContext = context;
        this.mAudioManager = (AudioManager) context.getSystemService(AudioManager.class);
        this.mNotificationManager = (NotificationManager) context.getSystemService(NotificationManager.class);
        this.mNotificationPolicy = this.mNotificationManager.getConsolidatedNotificationPolicy();
        this.mAllowAlarms = (this.mNotificationPolicy.priorityCategories & 32) != 0;
        this.mAllowMedia = (this.mNotificationPolicy.priorityCategories & 64) != 0;
        this.mAllowRinger = !ZenModeConfig.areAllPriorityOnlyNotificationZenSoundsMuted(this.mNotificationPolicy);
        this.mStreamType = streamType;
        this.mAffectedByRingerMode = this.mAudioManager.isStreamAffectedByRingerMode(this.mStreamType);
        this.mNotificationOrRing = isNotificationOrRing(this.mStreamType);
        if (this.mNotificationOrRing) {
            this.mRingerMode = this.mAudioManager.getRingerModeInternal();
        }
        this.mZenMode = this.mNotificationManager.getZenMode();
        if (hasAudioProductStrategies()) {
            this.mVolumeGroupId = getVolumeGroupIdForLegacyStreamType(this.mStreamType);
            this.mAttributes = getAudioAttributesForLegacyStreamType(this.mStreamType);
        }
        this.mMaxStreamVolume = this.mAudioManager.getStreamMaxVolume(this.mStreamType);
        this.mCallback = callback;
        this.mOriginalStreamVolume = this.mAudioManager.getStreamVolume(this.mStreamType);
        this.mLastAudibleStreamVolume = this.mAudioManager.getLastAudibleStreamVolume(this.mStreamType);
        this.mMuted = this.mAudioManager.isStreamMute(this.mStreamType);
        this.mPlaySample = playSample;
        if (this.mCallback != null) {
            this.mCallback.onMuted(this.mMuted, isZenMuted());
        }
        if (defaultUri == null) {
            if (this.mStreamType == 2) {
                defaultUri = Settings.System.DEFAULT_RINGTONE_URI;
            } else if (this.mStreamType == 5) {
                defaultUri = Settings.System.DEFAULT_NOTIFICATION_URI;
            } else {
                defaultUri = Settings.System.DEFAULT_ALARM_ALERT_URI;
            }
        }
        this.mDefaultUri = defaultUri;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean hasAudioProductStrategies() {
        return AudioManager.getAudioProductStrategies().size() > 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getVolumeGroupIdForLegacyStreamType(int streamType) {
        for (AudioProductStrategy productStrategy : AudioManager.getAudioProductStrategies()) {
            int volumeGroupId = productStrategy.getVolumeGroupIdForLegacyStreamType(streamType);
            if (volumeGroupId != -1) {
                return volumeGroupId;
            }
        }
        return ((Integer) AudioManager.getAudioProductStrategies().stream().map(new Function() { // from class: android.preference.-$$Lambda$SeekBarVolumizer$ezNr2aLs33rVlzIsAVW8OXqqpIs
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Integer valueOf;
                valueOf = Integer.valueOf(((AudioProductStrategy) obj).getVolumeGroupIdForAudioAttributes(AudioProductStrategy.sDefaultAttributes));
                return valueOf;
            }
        }).filter(new Predicate() { // from class: android.preference.-$$Lambda$SeekBarVolumizer$pv2-5S-FjgAtIix6Vp68yZJoqvQ
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return SeekBarVolumizer.lambda$getVolumeGroupIdForLegacyStreamType$1((Integer) obj);
            }
        }).findFirst().orElse(-1)).intValue();
    }

    static /* synthetic */ boolean lambda$getVolumeGroupIdForLegacyStreamType$1(Integer volumeGroupId) {
        return volumeGroupId.intValue() != -1;
    }

    private AudioAttributes getAudioAttributesForLegacyStreamType(int streamType) {
        for (AudioProductStrategy productStrategy : AudioManager.getAudioProductStrategies()) {
            AudioAttributes aa = productStrategy.getAudioAttributesForLegacyStreamType(streamType);
            if (aa != null) {
                return aa;
            }
        }
        return new AudioAttributes.Builder().setContentType(0).setUsage(0).build();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isNotificationOrRing(int stream) {
        return stream == 2 || stream == 5;
    }

    private static boolean isAlarmsStream(int stream) {
        return stream == 4;
    }

    private static boolean isMediaStream(int stream) {
        return stream == 3;
    }

    public void setSeekBar(SeekBar seekBar) {
        if (this.mSeekBar != null) {
            this.mSeekBar.setOnSeekBarChangeListener(null);
        }
        this.mSeekBar = seekBar;
        this.mSeekBar.setOnSeekBarChangeListener(null);
        this.mSeekBar.setMax(this.mMaxStreamVolume);
        updateSeekBar();
        this.mSeekBar.setOnSeekBarChangeListener(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isZenMuted() {
        if ((this.mNotificationOrRing && this.mZenMode == 3) || this.mZenMode == 2) {
            return true;
        }
        if (this.mZenMode == 1) {
            if (!this.mAllowAlarms && isAlarmsStream(this.mStreamType)) {
                return true;
            }
            if (!this.mAllowMedia && isMediaStream(this.mStreamType)) {
                return true;
            }
            if (!this.mAllowRinger && isNotificationOrRing(this.mStreamType)) {
                return true;
            }
        }
        return false;
    }

    protected void updateSeekBar() {
        boolean zenMuted = isZenMuted();
        this.mSeekBar.setEnabled(!zenMuted);
        if (zenMuted) {
            this.mSeekBar.setProgress(this.mLastAudibleStreamVolume, true);
        } else if (this.mNotificationOrRing && this.mRingerMode == 1) {
            this.mSeekBar.setProgress(0, true);
        } else if (this.mMuted) {
            this.mSeekBar.setProgress(0, true);
        } else {
            this.mSeekBar.setProgress(this.mLastProgress > -1 ? this.mLastProgress : this.mOriginalStreamVolume, true);
        }
    }

    @Override // android.p007os.Handler.Callback
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case 0:
                if (this.mMuted && this.mLastProgress > 0) {
                    this.mAudioManager.adjustStreamVolume(this.mStreamType, 100, 0);
                } else if (!this.mMuted && this.mLastProgress == 0) {
                    this.mAudioManager.adjustStreamVolume(this.mStreamType, -100, 0);
                }
                this.mAudioManager.setStreamVolume(this.mStreamType, this.mLastProgress, 1024);
                return true;
            case 1:
                if (this.mPlaySample) {
                    onStartSample();
                    return true;
                }
                return true;
            case 2:
                if (this.mPlaySample) {
                    onStopSample();
                    return true;
                }
                return true;
            case 3:
                if (this.mPlaySample) {
                    onInitSample();
                    return true;
                }
                return true;
            default:
                Log.m70e(TAG, "invalid SeekBarVolumizer message: " + msg.what);
                return true;
        }
    }

    private void onInitSample() {
        synchronized (this) {
            this.mRingtone = RingtoneManager.getRingtone(this.mContext, this.mDefaultUri);
            if (this.mRingtone != null) {
                this.mRingtone.setStreamType(this.mStreamType);
            }
        }
    }

    private void postStartSample() {
        if (this.mHandler == null) {
            return;
        }
        this.mHandler.removeMessages(1);
        this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(1), isSamplePlaying() ? 1000L : 0L);
    }

    private void onStartSample() {
        if (!isSamplePlaying()) {
            if (this.mCallback != null) {
                this.mCallback.onSampleStarting(this);
            }
            synchronized (this) {
                if (this.mRingtone != null) {
                    this.mRingtone.setAudioAttributes(new AudioAttributes.Builder(this.mRingtone.getAudioAttributes()).setFlags(128).build());
                    this.mRingtone.play();
                }
            }
        }
    }

    private void postStopSample() {
        if (this.mHandler == null) {
            return;
        }
        this.mHandler.removeMessages(1);
        this.mHandler.removeMessages(2);
        this.mHandler.sendMessage(this.mHandler.obtainMessage(2));
    }

    private void onStopSample() {
        synchronized (this) {
            if (this.mRingtone != null) {
                this.mRingtone.stop();
            }
        }
    }

    @UnsupportedAppUsage
    public void stop() {
        if (this.mHandler == null) {
            return;
        }
        postStopSample();
        this.mContext.getContentResolver().unregisterContentObserver(this.mVolumeObserver);
        this.mReceiver.setListening(false);
        if (hasAudioProductStrategies()) {
            unregisterVolumeGroupCb();
        }
        this.mSeekBar.setOnSeekBarChangeListener(null);
        this.mHandler.getLooper().quitSafely();
        this.mHandler = null;
        this.mVolumeObserver = null;
    }

    public void start() {
        if (this.mHandler != null) {
            return;
        }
        HandlerThread thread = new HandlerThread("SeekBarVolumizer.CallbackHandler");
        thread.start();
        this.mHandler = new Handler(thread.getLooper(), this);
        this.mHandler.sendEmptyMessage(3);
        this.mVolumeObserver = new Observer(this.mHandler);
        this.mContext.getContentResolver().registerContentObserver(Settings.System.getUriFor(Settings.System.VOLUME_SETTINGS_INT[this.mStreamType]), false, this.mVolumeObserver);
        this.mReceiver.setListening(true);
        if (hasAudioProductStrategies()) {
            registerVolumeGroupCb();
        }
    }

    public void revertVolume() {
        this.mAudioManager.setStreamVolume(this.mStreamType, this.mOriginalStreamVolume, 0);
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch) {
        if (fromTouch) {
            postSetVolume(progress);
        }
        if (this.mCallback != null) {
            this.mCallback.onProgressChanged(seekBar, progress, fromTouch);
        }
    }

    private void postSetVolume(int progress) {
        if (this.mHandler == null) {
            return;
        }
        this.mLastProgress = progress;
        this.mHandler.removeMessages(0);
        this.mHandler.sendMessage(this.mHandler.obtainMessage(0));
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onStopTrackingTouch(SeekBar seekBar) {
        postStartSample();
    }

    public boolean isSamplePlaying() {
        boolean z;
        synchronized (this) {
            z = this.mRingtone != null && this.mRingtone.isPlaying();
        }
        return z;
    }

    public void startSample() {
        postStartSample();
    }

    public void stopSample() {
        postStopSample();
    }

    public SeekBar getSeekBar() {
        return this.mSeekBar;
    }

    public void changeVolumeBy(int amount) {
        this.mSeekBar.incrementProgressBy(amount);
        postSetVolume(this.mSeekBar.getProgress());
        postStartSample();
        this.mVolumeBeforeMute = -1;
    }

    public void muteVolume() {
        if (this.mVolumeBeforeMute != -1) {
            this.mSeekBar.setProgress(this.mVolumeBeforeMute, true);
            postSetVolume(this.mVolumeBeforeMute);
            postStartSample();
            this.mVolumeBeforeMute = -1;
            return;
        }
        this.mVolumeBeforeMute = this.mSeekBar.getProgress();
        this.mSeekBar.setProgress(0, true);
        postStopSample();
        postSetVolume(0);
    }

    public void onSaveInstanceState(VolumePreference.VolumeStore volumeStore) {
        if (this.mLastProgress >= 0) {
            volumeStore.volume = this.mLastProgress;
            volumeStore.originalVolume = this.mOriginalStreamVolume;
        }
    }

    public void onRestoreInstanceState(VolumePreference.VolumeStore volumeStore) {
        if (volumeStore.volume != -1) {
            this.mOriginalStreamVolume = volumeStore.originalVolume;
            this.mLastProgress = volumeStore.volume;
            postSetVolume(this.mLastProgress);
        }
    }

    /* renamed from: android.preference.SeekBarVolumizer$H */
    /* loaded from: classes3.dex */
    private final class HandlerC1625H extends Handler {
        private static final int UPDATE_SLIDER = 1;

        private HandlerC1625H() {
        }

        @Override // android.p007os.Handler
        public void handleMessage(Message msg) {
            if (msg.what == 1 && SeekBarVolumizer.this.mSeekBar != null) {
                SeekBarVolumizer.this.mLastProgress = msg.arg1;
                SeekBarVolumizer.this.mLastAudibleStreamVolume = msg.arg2;
                boolean muted = ((Boolean) msg.obj).booleanValue();
                if (muted != SeekBarVolumizer.this.mMuted) {
                    SeekBarVolumizer.this.mMuted = muted;
                    if (SeekBarVolumizer.this.mCallback != null) {
                        SeekBarVolumizer.this.mCallback.onMuted(SeekBarVolumizer.this.mMuted, SeekBarVolumizer.this.isZenMuted());
                    }
                }
                SeekBarVolumizer.this.updateSeekBar();
            }
        }

        public void postUpdateSlider(int volume, int lastAudibleVolume, boolean mute) {
            obtainMessage(1, volume, lastAudibleVolume, new Boolean(mute)).sendToTarget();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateSlider() {
        if (this.mSeekBar != null && this.mAudioManager != null) {
            int volume = this.mAudioManager.getStreamVolume(this.mStreamType);
            int lastAudibleVolume = this.mAudioManager.getLastAudibleStreamVolume(this.mStreamType);
            boolean mute = this.mAudioManager.isStreamMute(this.mStreamType);
            this.mUiHandler.postUpdateSlider(volume, lastAudibleVolume, mute);
        }
    }

    /* loaded from: classes3.dex */
    private final class Observer extends ContentObserver {
        public Observer(Handler handler) {
            super(handler);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            SeekBarVolumizer.this.updateSlider();
        }
    }

    /* loaded from: classes3.dex */
    private final class Receiver extends BroadcastReceiver {
        private boolean mListening;

        private Receiver() {
        }

        public void setListening(boolean listening) {
            if (this.mListening == listening) {
                return;
            }
            this.mListening = listening;
            if (!listening) {
                SeekBarVolumizer.this.mContext.unregisterReceiver(this);
                return;
            }
            IntentFilter filter = new IntentFilter(AudioManager.VOLUME_CHANGED_ACTION);
            filter.addAction(AudioManager.INTERNAL_RINGER_MODE_CHANGED_ACTION);
            filter.addAction(NotificationManager.ACTION_INTERRUPTION_FILTER_CHANGED);
            filter.addAction(NotificationManager.ACTION_NOTIFICATION_POLICY_CHANGED);
            filter.addAction(AudioManager.STREAM_DEVICES_CHANGED_ACTION);
            SeekBarVolumizer.this.mContext.registerReceiver(this, filter);
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (AudioManager.VOLUME_CHANGED_ACTION.equals(action)) {
                int streamType = intent.getIntExtra(AudioManager.EXTRA_VOLUME_STREAM_TYPE, -1);
                int streamValue = intent.getIntExtra(AudioManager.EXTRA_VOLUME_STREAM_VALUE, -1);
                if (SeekBarVolumizer.this.hasAudioProductStrategies()) {
                    updateVolumeSlider(streamType, streamValue);
                }
            } else if (AudioManager.INTERNAL_RINGER_MODE_CHANGED_ACTION.equals(action)) {
                if (SeekBarVolumizer.this.mNotificationOrRing) {
                    SeekBarVolumizer.this.mRingerMode = SeekBarVolumizer.this.mAudioManager.getRingerModeInternal();
                }
                if (SeekBarVolumizer.this.mAffectedByRingerMode) {
                    SeekBarVolumizer.this.updateSlider();
                }
            } else if (AudioManager.STREAM_DEVICES_CHANGED_ACTION.equals(action)) {
                int streamType2 = intent.getIntExtra(AudioManager.EXTRA_VOLUME_STREAM_TYPE, -1);
                if (SeekBarVolumizer.this.hasAudioProductStrategies()) {
                    int streamVolume = SeekBarVolumizer.this.mAudioManager.getStreamVolume(streamType2);
                    updateVolumeSlider(streamType2, streamVolume);
                    return;
                }
                int volumeGroup = SeekBarVolumizer.this.getVolumeGroupIdForLegacyStreamType(streamType2);
                if (volumeGroup != -1 && volumeGroup == SeekBarVolumizer.this.mVolumeGroupId) {
                    int streamVolume2 = SeekBarVolumizer.this.mAudioManager.getStreamVolume(streamType2);
                    updateVolumeSlider(streamType2, streamVolume2);
                }
            } else if (NotificationManager.ACTION_INTERRUPTION_FILTER_CHANGED.equals(action)) {
                SeekBarVolumizer.this.mZenMode = SeekBarVolumizer.this.mNotificationManager.getZenMode();
                SeekBarVolumizer.this.updateSlider();
            } else if (NotificationManager.ACTION_NOTIFICATION_POLICY_CHANGED.equals(action)) {
                SeekBarVolumizer.this.mNotificationPolicy = SeekBarVolumizer.this.mNotificationManager.getConsolidatedNotificationPolicy();
                SeekBarVolumizer.this.mAllowAlarms = (SeekBarVolumizer.this.mNotificationPolicy.priorityCategories & 32) != 0;
                SeekBarVolumizer.this.mAllowMedia = (SeekBarVolumizer.this.mNotificationPolicy.priorityCategories & 64) != 0;
                SeekBarVolumizer.this.mAllowRinger = !ZenModeConfig.areAllPriorityOnlyNotificationZenSoundsMuted(SeekBarVolumizer.this.mNotificationPolicy);
                SeekBarVolumizer.this.updateSlider();
            }
        }

        private void updateVolumeSlider(int streamType, int streamValue) {
            boolean muted = false;
            boolean streamMatch = SeekBarVolumizer.this.mNotificationOrRing ? SeekBarVolumizer.isNotificationOrRing(streamType) : streamType == SeekBarVolumizer.this.mStreamType;
            if (SeekBarVolumizer.this.mSeekBar != null && streamMatch && streamValue != -1) {
                if (SeekBarVolumizer.this.mAudioManager.isStreamMute(SeekBarVolumizer.this.mStreamType) || streamValue == 0) {
                    muted = true;
                }
                SeekBarVolumizer.this.mUiHandler.postUpdateSlider(streamValue, SeekBarVolumizer.this.mLastAudibleStreamVolume, muted);
            }
        }
    }

    private void registerVolumeGroupCb() {
        if (this.mVolumeGroupId != -1) {
            this.mAudioManager.registerVolumeGroupCallback(new Executor() { // from class: android.preference.-$$Lambda$_14QHG018Z6p13d3hzJuGTWnNeo
                @Override // java.util.concurrent.Executor
                public final void execute(Runnable runnable) {
                    runnable.run();
                }
            }, this.mVolumeGroupCallback);
            updateSlider();
        }
    }

    private void unregisterVolumeGroupCb() {
        if (this.mVolumeGroupId != -1) {
            this.mAudioManager.unregisterVolumeGroupCallback(this.mVolumeGroupCallback);
        }
    }

    /* loaded from: classes3.dex */
    private class VolumeHandler extends Handler {
        private VolumeHandler() {
        }

        @Override // android.p007os.Handler
        public void handleMessage(Message msg) {
            SomeArgs args = (SomeArgs) msg.obj;
            if (msg.what == 1) {
                int group = ((Integer) args.arg1).intValue();
                if (SeekBarVolumizer.this.mVolumeGroupId == group && SeekBarVolumizer.this.mVolumeGroupId != -1) {
                    SeekBarVolumizer.this.updateSlider();
                }
            }
        }
    }
}
