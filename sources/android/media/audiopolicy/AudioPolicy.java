package android.media.audiopolicy;

import android.annotation.SystemApi;
import android.app.ActivityManager;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioDeviceInfo;
import android.media.AudioFocusInfo;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.IAudioService;
import android.media.audiopolicy.IAudioPolicyCallback;
import android.media.projection.MediaProjection;
import android.os.Binder;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import android.util.Slog;
import com.android.internal.annotations.GuardedBy;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@SystemApi
public class AudioPolicy {
    private static final boolean DEBUG = false;
    public static final int FOCUS_POLICY_DUCKING_DEFAULT = 0;
    public static final int FOCUS_POLICY_DUCKING_IN_APP = 0;
    public static final int FOCUS_POLICY_DUCKING_IN_POLICY = 1;
    private static final int MSG_FOCUS_ABANDON = 5;
    private static final int MSG_FOCUS_GRANT = 1;
    private static final int MSG_FOCUS_LOSS = 2;
    private static final int MSG_FOCUS_REQUEST = 4;
    private static final int MSG_MIX_STATE_UPDATE = 3;
    private static final int MSG_POLICY_STATUS_CHANGE = 0;
    private static final int MSG_VOL_ADJUST = 6;
    public static final int POLICY_STATUS_REGISTERED = 2;
    public static final int POLICY_STATUS_UNREGISTERED = 1;
    private static final String TAG = "AudioPolicy";
    private static IAudioService sService;
    @GuardedBy({"mLock"})
    private ArrayList<WeakReference<AudioRecord>> mCaptors;
    /* access modifiers changed from: private */
    public AudioPolicyConfig mConfig;
    private Context mContext;
    private final EventHandler mEventHandler;
    /* access modifiers changed from: private */
    public AudioPolicyFocusListener mFocusListener;
    @GuardedBy({"mLock"})
    private ArrayList<WeakReference<AudioTrack>> mInjectors;
    private boolean mIsFocusPolicy;
    private boolean mIsTestFocusPolicy;
    private final Object mLock;
    private final IAudioPolicyCallback mPolicyCb;
    private final MediaProjection mProjection;
    private String mRegistrationId;
    private int mStatus;
    /* access modifiers changed from: private */
    public AudioPolicyStatusListener mStatusListener;
    /* access modifiers changed from: private */
    public final AudioPolicyVolumeCallback mVolCb;

    @Retention(RetentionPolicy.SOURCE)
    public @interface PolicyStatus {
    }

    public AudioPolicyConfig getConfig() {
        return this.mConfig;
    }

    public boolean hasFocusListener() {
        return this.mFocusListener != null;
    }

    public boolean isFocusPolicy() {
        return this.mIsFocusPolicy;
    }

    public boolean isTestFocusPolicy() {
        return this.mIsTestFocusPolicy;
    }

    public boolean isVolumeController() {
        return this.mVolCb != null;
    }

    public MediaProjection getMediaProjection() {
        return this.mProjection;
    }

    private AudioPolicy(AudioPolicyConfig config, Context context, Looper looper, AudioPolicyFocusListener fl, AudioPolicyStatusListener sl, boolean isFocusPolicy, boolean isTestFocusPolicy, AudioPolicyVolumeCallback vc, MediaProjection projection) {
        this.mLock = new Object();
        this.mPolicyCb = new IAudioPolicyCallback.Stub() {
            public void notifyAudioFocusGrant(AudioFocusInfo afi, int requestResult) {
                AudioPolicy.this.sendMsg(1, afi, requestResult);
            }

            public void notifyAudioFocusLoss(AudioFocusInfo afi, boolean wasNotified) {
                AudioPolicy.this.sendMsg(2, afi, wasNotified);
            }

            public void notifyAudioFocusRequest(AudioFocusInfo afi, int requestResult) {
                AudioPolicy.this.sendMsg(4, afi, requestResult);
            }

            public void notifyAudioFocusAbandon(AudioFocusInfo afi) {
                AudioPolicy.this.sendMsg(5, afi, 0);
            }

            public void notifyMixStateUpdate(String regId, int state) {
                Iterator<AudioMix> it = AudioPolicy.this.mConfig.getMixes().iterator();
                while (it.hasNext()) {
                    AudioMix mix = it.next();
                    if (mix.getRegistration().equals(regId)) {
                        mix.mMixState = state;
                        AudioPolicy.this.sendMsg(3, mix, 0);
                    }
                }
            }

            public void notifyVolumeAdjust(int adjustment) {
                AudioPolicy.this.sendMsg(6, (Object) null, adjustment);
            }
        };
        this.mConfig = config;
        this.mStatus = 1;
        this.mContext = context;
        looper = looper == null ? Looper.getMainLooper() : looper;
        if (looper != null) {
            this.mEventHandler = new EventHandler(this, looper);
        } else {
            this.mEventHandler = null;
            Log.e(TAG, "No event handler due to looper without a thread");
        }
        this.mFocusListener = fl;
        this.mStatusListener = sl;
        this.mIsFocusPolicy = isFocusPolicy;
        this.mIsTestFocusPolicy = isTestFocusPolicy;
        this.mVolCb = vc;
        this.mProjection = projection;
    }

    public static class Builder {
        private Context mContext;
        private AudioPolicyFocusListener mFocusListener;
        private boolean mIsFocusPolicy = false;
        private boolean mIsTestFocusPolicy = false;
        private Looper mLooper;
        private ArrayList<AudioMix> mMixes = new ArrayList<>();
        private MediaProjection mProjection;
        private AudioPolicyStatusListener mStatusListener;
        private AudioPolicyVolumeCallback mVolCb;

        public Builder(Context context) {
            this.mContext = context;
        }

        public Builder addMix(AudioMix mix) throws IllegalArgumentException {
            if (mix != null) {
                this.mMixes.add(mix);
                return this;
            }
            throw new IllegalArgumentException("Illegal null AudioMix argument");
        }

        public Builder setLooper(Looper looper) throws IllegalArgumentException {
            if (looper != null) {
                this.mLooper = looper;
                return this;
            }
            throw new IllegalArgumentException("Illegal null Looper argument");
        }

        public void setAudioPolicyFocusListener(AudioPolicyFocusListener l) {
            this.mFocusListener = l;
        }

        public Builder setIsAudioFocusPolicy(boolean isFocusPolicy) {
            this.mIsFocusPolicy = isFocusPolicy;
            return this;
        }

        public Builder setIsTestFocusPolicy(boolean isTestFocusPolicy) {
            this.mIsTestFocusPolicy = isTestFocusPolicy;
            return this;
        }

        public void setAudioPolicyStatusListener(AudioPolicyStatusListener l) {
            this.mStatusListener = l;
        }

        public Builder setAudioPolicyVolumeCallback(AudioPolicyVolumeCallback vc) {
            if (vc != null) {
                this.mVolCb = vc;
                return this;
            }
            throw new IllegalArgumentException("Invalid null volume callback");
        }

        public Builder setMediaProjection(MediaProjection projection) {
            if (projection != null) {
                this.mProjection = projection;
                return this;
            }
            throw new IllegalArgumentException("Invalid null volume callback");
        }

        public AudioPolicy build() {
            if (this.mStatusListener != null) {
                Iterator<AudioMix> it = this.mMixes.iterator();
                while (it.hasNext()) {
                    it.next().mCallbackFlags |= 1;
                }
            }
            if (!this.mIsFocusPolicy || this.mFocusListener != null) {
                return new AudioPolicy(new AudioPolicyConfig(this.mMixes), this.mContext, this.mLooper, this.mFocusListener, this.mStatusListener, this.mIsFocusPolicy, this.mIsTestFocusPolicy, this.mVolCb, this.mProjection);
            }
            throw new IllegalStateException("Cannot be a focus policy without an AudioPolicyFocusListener");
        }
    }

    public int attachMixes(List<AudioMix> mixes) {
        int status;
        if (mixes != null) {
            synchronized (this.mLock) {
                if (this.mStatus == 2) {
                    ArrayList<AudioMix> zeMixes = new ArrayList<>(mixes.size());
                    for (AudioMix mix : mixes) {
                        if (mix != null) {
                            zeMixes.add(mix);
                        } else {
                            throw new IllegalArgumentException("Illegal null AudioMix in attachMixes");
                        }
                    }
                    AudioPolicyConfig cfg = new AudioPolicyConfig(zeMixes);
                    try {
                        status = getService().addMixForPolicy(cfg, cb());
                        if (status == 0) {
                            this.mConfig.add(zeMixes);
                        }
                    } catch (RemoteException e) {
                        Log.e(TAG, "Dead object in attachMixes", e);
                        return -1;
                    }
                } else {
                    throw new IllegalStateException("Cannot alter unregistered AudioPolicy");
                }
            }
            return status;
        }
        throw new IllegalArgumentException("Illegal null list of AudioMix");
    }

    public int detachMixes(List<AudioMix> mixes) {
        int status;
        if (mixes != null) {
            synchronized (this.mLock) {
                if (this.mStatus == 2) {
                    ArrayList<AudioMix> zeMixes = new ArrayList<>(mixes.size());
                    for (AudioMix mix : mixes) {
                        if (mix != null) {
                            zeMixes.add(mix);
                        } else {
                            throw new IllegalArgumentException("Illegal null AudioMix in detachMixes");
                        }
                    }
                    AudioPolicyConfig cfg = new AudioPolicyConfig(zeMixes);
                    try {
                        status = getService().removeMixForPolicy(cfg, cb());
                        if (status == 0) {
                            this.mConfig.remove(zeMixes);
                        }
                    } catch (RemoteException e) {
                        Log.e(TAG, "Dead object in detachMixes", e);
                        return -1;
                    }
                } else {
                    throw new IllegalStateException("Cannot alter unregistered AudioPolicy");
                }
            }
            return status;
        }
        throw new IllegalArgumentException("Illegal null list of AudioMix");
    }

    @SystemApi
    public boolean setUidDeviceAffinity(int uid, List<AudioDeviceInfo> devices) {
        boolean z;
        if (devices != null) {
            synchronized (this.mLock) {
                if (this.mStatus == 2) {
                    int[] deviceTypes = new int[devices.size()];
                    String[] deviceAdresses = new String[devices.size()];
                    int i = 0;
                    for (AudioDeviceInfo device : devices) {
                        if (device != null) {
                            deviceTypes[i] = AudioDeviceInfo.convertDeviceTypeToInternalDevice(device.getType());
                            deviceAdresses[i] = device.getAddress();
                            i++;
                        } else {
                            throw new IllegalArgumentException("Illegal null AudioDeviceInfo in setUidDeviceAffinity");
                        }
                    }
                    z = false;
                    try {
                        if (getService().setUidDeviceAffinity(cb(), uid, deviceTypes, deviceAdresses) == 0) {
                            z = true;
                        }
                    } catch (RemoteException e) {
                        Log.e(TAG, "Dead object in setUidDeviceAffinity", e);
                        return false;
                    }
                } else {
                    throw new IllegalStateException("Cannot use unregistered AudioPolicy");
                }
            }
            return z;
        }
        throw new IllegalArgumentException("Illegal null list of audio devices");
    }

    @SystemApi
    public boolean removeUidDeviceAffinity(int uid) {
        boolean z;
        synchronized (this.mLock) {
            if (this.mStatus == 2) {
                z = false;
                try {
                    if (getService().removeUidDeviceAffinity(cb(), uid) == 0) {
                        z = true;
                    }
                } catch (RemoteException e) {
                    Log.e(TAG, "Dead object in removeUidDeviceAffinity", e);
                    return false;
                }
            } else {
                throw new IllegalStateException("Cannot use unregistered AudioPolicy");
            }
        }
        return z;
    }

    public void setRegistration(String regId) {
        synchronized (this.mLock) {
            this.mRegistrationId = regId;
            this.mConfig.setRegistration(regId);
            if (regId != null) {
                this.mStatus = 2;
            } else {
                this.mStatus = 1;
            }
        }
        sendMsg(0);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0027, code lost:
        if (checkCallingOrSelfPermission(android.Manifest.permission.MODIFY_AUDIO_ROUTING) != 0) goto L_0x002b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0029, code lost:
        r0 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x002b, code lost:
        r0 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x002e, code lost:
        if (r6.mProjection == null) goto L_0x003e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x003a, code lost:
        if (r6.mProjection.getProjection().canProjectAudio() == false) goto L_0x003e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x003c, code lost:
        r2 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x003e, code lost:
        r2 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0045, code lost:
        if (isLoopbackRenderPolicy() == false) goto L_0x0049;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0047, code lost:
        if (r2 != false) goto L_0x0077;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0049, code lost:
        if (r0 != false) goto L_0x0077;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x004b, code lost:
        android.util.Slog.w(TAG, "Cannot use AudioPolicy for pid " + android.os.Binder.getCallingPid() + " / uid " + android.os.Binder.getCallingUid() + ", needs MODIFY_AUDIO_ROUTING or MediaProjection that can project audio.");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0076, code lost:
        return false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0077, code lost:
        return true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0078, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0079, code lost:
        android.util.Log.e(TAG, "Failed to check if MediaProjection#canProjectAudio");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0084, code lost:
        throw r1.rethrowFromSystemServer();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean policyReadyToUse() {
        /*
            r6 = this;
            java.lang.Object r0 = r6.mLock
            monitor-enter(r0)
            int r1 = r6.mStatus     // Catch:{ all -> 0x0085 }
            r2 = 2
            r3 = 0
            if (r1 == r2) goto L_0x0012
            java.lang.String r1 = "AudioPolicy"
            java.lang.String r2 = "Cannot use unregistered AudioPolicy"
            android.util.Log.e(r1, r2)     // Catch:{ all -> 0x0085 }
            monitor-exit(r0)     // Catch:{ all -> 0x0085 }
            return r3
        L_0x0012:
            java.lang.String r1 = r6.mRegistrationId     // Catch:{ all -> 0x0085 }
            if (r1 != 0) goto L_0x001f
            java.lang.String r1 = "AudioPolicy"
            java.lang.String r2 = "Cannot use unregistered AudioPolicy"
            android.util.Log.e(r1, r2)     // Catch:{ all -> 0x0085 }
            monitor-exit(r0)     // Catch:{ all -> 0x0085 }
            return r3
        L_0x001f:
            monitor-exit(r0)     // Catch:{ all -> 0x0085 }
            java.lang.String r0 = "android.permission.MODIFY_AUDIO_ROUTING"
            int r0 = r6.checkCallingOrSelfPermission(r0)
            r1 = 1
            if (r0 != 0) goto L_0x002b
            r0 = r1
            goto L_0x002c
        L_0x002b:
            r0 = r3
        L_0x002c:
            android.media.projection.MediaProjection r2 = r6.mProjection     // Catch:{ RemoteException -> 0x0078 }
            if (r2 == 0) goto L_0x003e
            android.media.projection.MediaProjection r2 = r6.mProjection     // Catch:{ RemoteException -> 0x0078 }
            android.media.projection.IMediaProjection r2 = r2.getProjection()     // Catch:{ RemoteException -> 0x0078 }
            boolean r2 = r2.canProjectAudio()     // Catch:{ RemoteException -> 0x0078 }
            if (r2 == 0) goto L_0x003e
            r2 = r1
            goto L_0x003f
        L_0x003e:
            r2 = r3
        L_0x003f:
            boolean r4 = r6.isLoopbackRenderPolicy()
            if (r4 == 0) goto L_0x0049
            if (r2 != 0) goto L_0x0077
        L_0x0049:
            if (r0 != 0) goto L_0x0077
            java.lang.String r1 = "AudioPolicy"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "Cannot use AudioPolicy for pid "
            r4.append(r5)
            int r5 = android.os.Binder.getCallingPid()
            r4.append(r5)
            java.lang.String r5 = " / uid "
            r4.append(r5)
            int r5 = android.os.Binder.getCallingUid()
            r4.append(r5)
            java.lang.String r5 = ", needs MODIFY_AUDIO_ROUTING or MediaProjection that can project audio."
            r4.append(r5)
            java.lang.String r4 = r4.toString()
            android.util.Slog.w((java.lang.String) r1, (java.lang.String) r4)
            return r3
        L_0x0077:
            return r1
        L_0x0078:
            r1 = move-exception
            java.lang.String r2 = "AudioPolicy"
            java.lang.String r3 = "Failed to check if MediaProjection#canProjectAudio"
            android.util.Log.e(r2, r3)
            java.lang.RuntimeException r2 = r1.rethrowFromSystemServer()
            throw r2
        L_0x0085:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0085 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.media.audiopolicy.AudioPolicy.policyReadyToUse():boolean");
    }

    private boolean isLoopbackRenderPolicy() {
        boolean allMatch;
        synchronized (this.mLock) {
            allMatch = this.mConfig.mMixes.stream().allMatch($$Lambda$AudioPolicy$ztOT0FT3tzGMUr4lm1gv6dBE4c.INSTANCE);
        }
        return allMatch;
    }

    static /* synthetic */ boolean lambda$isLoopbackRenderPolicy$0(AudioMix mix) {
        return mix.getRouteFlags() == 3;
    }

    private int checkCallingOrSelfPermission(String permission) {
        if (this.mContext != null) {
            return this.mContext.checkCallingOrSelfPermission(permission);
        }
        Slog.v(TAG, "Null context, checking permission via ActivityManager");
        try {
            return ActivityManager.getService().checkPermission(permission, Binder.getCallingPid(), Binder.getCallingUid());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private void checkMixReadyToUse(AudioMix mix, boolean forTrack) throws IllegalArgumentException {
        if (mix == null) {
            throw new IllegalArgumentException(forTrack ? "Invalid null AudioMix for AudioTrack creation" : "Invalid null AudioMix for AudioRecord creation");
        } else if (!this.mConfig.mMixes.contains(mix)) {
            throw new IllegalArgumentException("Invalid mix: not part of this policy");
        } else if ((mix.getRouteFlags() & 2) != 2) {
            throw new IllegalArgumentException("Invalid AudioMix: not defined for loop back");
        } else if (forTrack && mix.getMixType() != 1) {
            throw new IllegalArgumentException("Invalid AudioMix: not defined for being a recording source");
        } else if (!forTrack && mix.getMixType() != 0) {
            throw new IllegalArgumentException("Invalid AudioMix: not defined for capturing playback");
        }
    }

    public int getFocusDuckingBehavior() {
        return this.mConfig.mDuckingPolicy;
    }

    public int setFocusDuckingBehavior(int behavior) throws IllegalArgumentException, IllegalStateException {
        int status;
        if (behavior == 0 || behavior == 1) {
            synchronized (this.mLock) {
                if (this.mStatus == 2) {
                    if (behavior == 1) {
                        if (this.mFocusListener == null) {
                            throw new IllegalStateException("Cannot handle ducking without an audio focus listener");
                        }
                    }
                    try {
                        status = getService().setFocusPropertiesForPolicy(behavior, cb());
                        if (status == 0) {
                            this.mConfig.mDuckingPolicy = behavior;
                        }
                    } catch (RemoteException e) {
                        Log.e(TAG, "Dead object in setFocusPropertiesForPolicy for behavior", e);
                        return -1;
                    }
                } else {
                    throw new IllegalStateException("Cannot change ducking behavior for unregistered policy");
                }
            }
            return status;
        }
        throw new IllegalArgumentException("Invalid ducking behavior " + behavior);
    }

    public AudioRecord createAudioRecordSink(AudioMix mix) throws IllegalArgumentException {
        if (!policyReadyToUse()) {
            Log.e(TAG, "Cannot create AudioRecord sink for AudioMix");
            return null;
        }
        checkMixReadyToUse(mix, false);
        AudioRecord ar = new AudioRecord(new AudioAttributes.Builder().setInternalCapturePreset(8).addTag(addressForTag(mix)).addTag(AudioRecord.SUBMIX_FIXED_VOLUME).build(), new AudioFormat.Builder(mix.getFormat()).setChannelMask(AudioFormat.inChannelMaskFromOutChannelMask(mix.getFormat().getChannelMask())).build(), AudioRecord.getMinBufferSize(mix.getFormat().getSampleRate(), 12, mix.getFormat().getEncoding()), 0);
        synchronized (this.mLock) {
            if (this.mCaptors == null) {
                this.mCaptors = new ArrayList<>(1);
            }
            this.mCaptors.add(new WeakReference(ar));
        }
        return ar;
    }

    public AudioTrack createAudioTrackSource(AudioMix mix) throws IllegalArgumentException {
        if (!policyReadyToUse()) {
            Log.e(TAG, "Cannot create AudioTrack source for AudioMix");
            return null;
        }
        checkMixReadyToUse(mix, true);
        AudioTrack at = new AudioTrack(new AudioAttributes.Builder().setUsage(15).addTag(addressForTag(mix)).build(), mix.getFormat(), AudioTrack.getMinBufferSize(mix.getFormat().getSampleRate(), mix.getFormat().getChannelMask(), mix.getFormat().getEncoding()), 1, 0);
        synchronized (this.mLock) {
            if (this.mInjectors == null) {
                this.mInjectors = new ArrayList<>(1);
            }
            this.mInjectors.add(new WeakReference(at));
        }
        return at;
    }

    public void invalidateCaptorsAndInjectors() {
        if (policyReadyToUse()) {
            synchronized (this.mLock) {
                if (this.mInjectors != null) {
                    Iterator<WeakReference<AudioTrack>> it = this.mInjectors.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        AudioTrack track = (AudioTrack) it.next().get();
                        if (track == null) {
                            break;
                        }
                        track.stop();
                        track.flush();
                    }
                }
                if (this.mCaptors != null) {
                    Iterator<WeakReference<AudioRecord>> it2 = this.mCaptors.iterator();
                    while (true) {
                        if (!it2.hasNext()) {
                            break;
                        }
                        AudioRecord record = (AudioRecord) it2.next().get();
                        if (record == null) {
                            break;
                        }
                        record.stop();
                    }
                }
            }
        }
    }

    public int getStatus() {
        return this.mStatus;
    }

    public static abstract class AudioPolicyStatusListener {
        public void onStatusChange() {
        }

        public void onMixStateUpdate(AudioMix mix) {
        }
    }

    public static abstract class AudioPolicyFocusListener {
        public void onAudioFocusGrant(AudioFocusInfo afi, int requestResult) {
        }

        public void onAudioFocusLoss(AudioFocusInfo afi, boolean wasNotified) {
        }

        public void onAudioFocusRequest(AudioFocusInfo afi, int requestResult) {
        }

        public void onAudioFocusAbandon(AudioFocusInfo afi) {
        }
    }

    public static abstract class AudioPolicyVolumeCallback {
        public void onVolumeAdjustment(int adjustment) {
        }
    }

    /* access modifiers changed from: private */
    public void onPolicyStatusChange() {
        synchronized (this.mLock) {
            if (this.mStatusListener != null) {
                AudioPolicyStatusListener l = this.mStatusListener;
                l.onStatusChange();
            }
        }
    }

    public IAudioPolicyCallback cb() {
        return this.mPolicyCb;
    }

    private class EventHandler extends Handler {
        public EventHandler(AudioPolicy ap, Looper looper) {
            super(looper);
        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    AudioPolicy.this.onPolicyStatusChange();
                    return;
                case 1:
                    if (AudioPolicy.this.mFocusListener != null) {
                        AudioPolicy.this.mFocusListener.onAudioFocusGrant((AudioFocusInfo) msg.obj, msg.arg1);
                        return;
                    }
                    return;
                case 2:
                    if (AudioPolicy.this.mFocusListener != null) {
                        AudioPolicy.this.mFocusListener.onAudioFocusLoss((AudioFocusInfo) msg.obj, msg.arg1 != 0);
                        return;
                    }
                    return;
                case 3:
                    if (AudioPolicy.this.mStatusListener != null) {
                        AudioPolicy.this.mStatusListener.onMixStateUpdate((AudioMix) msg.obj);
                        return;
                    }
                    return;
                case 4:
                    if (AudioPolicy.this.mFocusListener != null) {
                        AudioPolicy.this.mFocusListener.onAudioFocusRequest((AudioFocusInfo) msg.obj, msg.arg1);
                        return;
                    } else {
                        Log.e(AudioPolicy.TAG, "Invalid null focus listener for focus request event");
                        return;
                    }
                case 5:
                    if (AudioPolicy.this.mFocusListener != null) {
                        AudioPolicy.this.mFocusListener.onAudioFocusAbandon((AudioFocusInfo) msg.obj);
                        return;
                    } else {
                        Log.e(AudioPolicy.TAG, "Invalid null focus listener for focus abandon event");
                        return;
                    }
                case 6:
                    if (AudioPolicy.this.mVolCb != null) {
                        AudioPolicy.this.mVolCb.onVolumeAdjustment(msg.arg1);
                        return;
                    } else {
                        Log.e(AudioPolicy.TAG, "Invalid null volume event");
                        return;
                    }
                default:
                    Log.e(AudioPolicy.TAG, "Unknown event " + msg.what);
                    return;
            }
        }
    }

    private static String addressForTag(AudioMix mix) {
        return "addr=" + mix.getRegistration();
    }

    private void sendMsg(int msg) {
        if (this.mEventHandler != null) {
            this.mEventHandler.sendEmptyMessage(msg);
        }
    }

    /* access modifiers changed from: private */
    public void sendMsg(int msg, Object obj, int i) {
        if (this.mEventHandler != null) {
            this.mEventHandler.sendMessage(this.mEventHandler.obtainMessage(msg, i, 0, obj));
        }
    }

    private static IAudioService getService() {
        if (sService != null) {
            return sService;
        }
        sService = IAudioService.Stub.asInterface(ServiceManager.getService("audio"));
        return sService;
    }

    public String toLogFriendlyString() {
        String textDump = new String("android.media.audiopolicy.AudioPolicy:\n");
        return textDump + "config=" + this.mConfig.toLogFriendlyString();
    }
}
