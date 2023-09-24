package android.media;

import android.media.IAudioService;
import android.media.IPlayer;
import android.media.VolumeShaper;
import android.p007os.IBinder;
import android.p007os.Parcel;
import android.p007os.Parcelable;
import android.p007os.RemoteException;
import android.p007os.ServiceManager;
import android.util.Log;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.app.IAppOpsCallback;
import com.android.internal.app.IAppOpsService;
import java.lang.ref.WeakReference;
import java.util.Objects;

/* loaded from: classes3.dex */
public abstract class PlayerBase {
    private static final boolean DEBUG = false;
    private static final boolean DEBUG_APP_OPS = false;
    private static final String TAG = "PlayerBase";
    private static final boolean USE_AUDIOFLINGER_MUTING_FOR_OP = true;
    private static IAudioService sService;
    private IAppOpsService mAppOps;
    private IAppOpsCallback mAppOpsCallback;
    protected AudioAttributes mAttributes;
    private final int mImplType;
    @GuardedBy({"mLock"})
    private int mState;
    protected float mLeftVolume = 1.0f;
    protected float mRightVolume = 1.0f;
    protected float mAuxEffectSendLevel = 0.0f;
    private final Object mLock = new Object();
    @GuardedBy({"mLock"})
    private boolean mHasAppOpsPlayAudio = true;
    private int mPlayerIId = -1;
    @GuardedBy({"mLock"})
    private int mStartDelayMs = 0;
    @GuardedBy({"mLock"})
    private float mPanMultiplierL = 1.0f;
    @GuardedBy({"mLock"})
    private float mPanMultiplierR = 1.0f;
    @GuardedBy({"mLock"})
    private float mVolMultiplier = 1.0f;

    abstract int playerApplyVolumeShaper(VolumeShaper.Configuration configuration, VolumeShaper.Operation operation);

    abstract VolumeShaper.State playerGetVolumeShaperState(int i);

    abstract void playerPause();

    abstract int playerSetAuxEffectSendLevel(boolean z, float f);

    abstract void playerSetVolume(boolean z, float f, float f2);

    abstract void playerStart();

    abstract void playerStop();

    PlayerBase(AudioAttributes attr, int implType) {
        if (attr == null) {
            throw new IllegalArgumentException("Illegal null AudioAttributes");
        }
        this.mAttributes = attr;
        this.mImplType = implType;
        this.mState = 1;
    }

    protected void baseRegisterPlayer() {
        try {
            this.mPlayerIId = getService().trackPlayer(new PlayerIdCard(this.mImplType, this.mAttributes, new IPlayerWrapper(this)));
        } catch (RemoteException e) {
            Log.m69e(TAG, "Error talking to audio service, player will not be tracked", e);
        }
    }

    void baseUpdateAudioAttributes(AudioAttributes attr) {
        if (attr == null) {
            throw new IllegalArgumentException("Illegal null AudioAttributes");
        }
        try {
            getService().playerAttributes(this.mPlayerIId, attr);
        } catch (RemoteException e) {
            Log.m69e(TAG, "Error talking to audio service, STARTED state will not be tracked", e);
        }
        synchronized (this.mLock) {
            boolean attributesChanged = this.mAttributes != attr;
            this.mAttributes = attr;
            updateAppOpsPlayAudio_sync(attributesChanged);
        }
    }

    private void updateState(int state) {
        int piid;
        synchronized (this.mLock) {
            this.mState = state;
            piid = this.mPlayerIId;
        }
        try {
            getService().playerEvent(piid, state);
        } catch (RemoteException e) {
            Log.m69e(TAG, "Error talking to audio service, " + AudioPlaybackConfiguration.toLogFriendlyPlayerState(state) + " state will not be tracked for piid=" + piid, e);
        }
    }

    void baseStart() {
        updateState(2);
        synchronized (this.mLock) {
            if (isRestricted_sync()) {
                playerSetVolume(true, 0.0f, 0.0f);
            }
        }
    }

    void baseSetStartDelayMs(int delayMs) {
        synchronized (this.mLock) {
            this.mStartDelayMs = Math.max(delayMs, 0);
        }
    }

    protected int getStartDelayMs() {
        int i;
        synchronized (this.mLock) {
            i = this.mStartDelayMs;
        }
        return i;
    }

    void basePause() {
        updateState(3);
    }

    void baseStop() {
        updateState(4);
    }

    void baseSetPan(float pan) {
        float p = Math.min(Math.max(-1.0f, pan), 1.0f);
        synchronized (this.mLock) {
            try {
                if (p >= 0.0f) {
                    this.mPanMultiplierL = 1.0f - p;
                    this.mPanMultiplierR = 1.0f;
                } else {
                    this.mPanMultiplierL = 1.0f;
                    this.mPanMultiplierR = 1.0f + p;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        updatePlayerVolume();
    }

    private void updatePlayerVolume() {
        float finalLeftVol;
        float finalRightVol;
        boolean isRestricted;
        synchronized (this.mLock) {
            finalLeftVol = this.mVolMultiplier * this.mLeftVolume * this.mPanMultiplierL;
            finalRightVol = this.mVolMultiplier * this.mRightVolume * this.mPanMultiplierR;
            isRestricted = isRestricted_sync();
        }
        playerSetVolume(isRestricted, finalLeftVol, finalRightVol);
    }

    void setVolumeMultiplier(float vol) {
        synchronized (this.mLock) {
            this.mVolMultiplier = vol;
        }
        updatePlayerVolume();
    }

    void baseSetVolume(float leftVolume, float rightVolume) {
        synchronized (this.mLock) {
            this.mLeftVolume = leftVolume;
            this.mRightVolume = rightVolume;
        }
        updatePlayerVolume();
    }

    int baseSetAuxEffectSendLevel(float level) {
        synchronized (this.mLock) {
            this.mAuxEffectSendLevel = level;
            if (isRestricted_sync()) {
                return 0;
            }
            return playerSetAuxEffectSendLevel(false, level);
        }
    }

    void baseRelease() {
        boolean releasePlayer = false;
        synchronized (this.mLock) {
            if (this.mState != 0) {
                releasePlayer = true;
                this.mState = 0;
            }
        }
        if (releasePlayer) {
            try {
                getService().releasePlayer(this.mPlayerIId);
            } catch (RemoteException e) {
                Log.m69e(TAG, "Error talking to audio service, the player will still be tracked", e);
            }
        }
        try {
            if (this.mAppOps != null) {
                this.mAppOps.stopWatchingMode(this.mAppOpsCallback);
            }
        } catch (Exception e2) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateAppOpsPlayAudio() {
        synchronized (this.mLock) {
            updateAppOpsPlayAudio_sync(false);
        }
    }

    void updateAppOpsPlayAudio_sync(boolean attributesChanged) {
    }

    boolean isRestricted_sync() {
        return false;
    }

    private static IAudioService getService() {
        if (sService != null) {
            return sService;
        }
        IBinder b = ServiceManager.getService("audio");
        sService = IAudioService.Stub.asInterface(b);
        return sService;
    }

    public void setStartDelayMs(int delayMs) {
        baseSetStartDelayMs(delayMs);
    }

    /* loaded from: classes3.dex */
    private static class IAppOpsCallbackWrapper extends IAppOpsCallback.Stub {
        private final WeakReference<PlayerBase> mWeakPB;

        public IAppOpsCallbackWrapper(PlayerBase pb) {
            this.mWeakPB = new WeakReference<>(pb);
        }

        @Override // com.android.internal.app.IAppOpsCallback
        public void opChanged(int op, int uid, String packageName) {
            PlayerBase pb;
            if (op == 28 && (pb = this.mWeakPB.get()) != null) {
                pb.updateAppOpsPlayAudio();
            }
        }
    }

    /* loaded from: classes3.dex */
    private static class IPlayerWrapper extends IPlayer.Stub {
        private final WeakReference<PlayerBase> mWeakPB;

        public IPlayerWrapper(PlayerBase pb) {
            this.mWeakPB = new WeakReference<>(pb);
        }

        @Override // android.media.IPlayer
        public void start() {
            PlayerBase pb = this.mWeakPB.get();
            if (pb != null) {
                pb.playerStart();
            }
        }

        @Override // android.media.IPlayer
        public void pause() {
            PlayerBase pb = this.mWeakPB.get();
            if (pb != null) {
                pb.playerPause();
            }
        }

        @Override // android.media.IPlayer
        public void stop() {
            PlayerBase pb = this.mWeakPB.get();
            if (pb != null) {
                pb.playerStop();
            }
        }

        @Override // android.media.IPlayer
        public void setVolume(float vol) {
            PlayerBase pb = this.mWeakPB.get();
            if (pb != null) {
                pb.setVolumeMultiplier(vol);
            }
        }

        @Override // android.media.IPlayer
        public void setPan(float pan) {
            PlayerBase pb = this.mWeakPB.get();
            if (pb != null) {
                pb.baseSetPan(pan);
            }
        }

        @Override // android.media.IPlayer
        public void setStartDelayMs(int delayMs) {
            PlayerBase pb = this.mWeakPB.get();
            if (pb != null) {
                pb.baseSetStartDelayMs(delayMs);
            }
        }

        @Override // android.media.IPlayer
        public void applyVolumeShaper(VolumeShaper.Configuration configuration, VolumeShaper.Operation operation) {
            PlayerBase pb = this.mWeakPB.get();
            if (pb != null) {
                pb.playerApplyVolumeShaper(configuration, operation);
            }
        }
    }

    /* loaded from: classes3.dex */
    public static class PlayerIdCard implements Parcelable {
        public static final int AUDIO_ATTRIBUTES_DEFINED = 1;
        public static final int AUDIO_ATTRIBUTES_NONE = 0;
        public static final Parcelable.Creator<PlayerIdCard> CREATOR = new Parcelable.Creator<PlayerIdCard>() { // from class: android.media.PlayerBase.PlayerIdCard.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.p007os.Parcelable.Creator
            public PlayerIdCard createFromParcel(Parcel p) {
                return new PlayerIdCard(p);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.p007os.Parcelable.Creator
            public PlayerIdCard[] newArray(int size) {
                return new PlayerIdCard[size];
            }
        };
        public final AudioAttributes mAttributes;
        public final IPlayer mIPlayer;
        public final int mPlayerType;

        PlayerIdCard(int type, AudioAttributes attr, IPlayer iplayer) {
            this.mPlayerType = type;
            this.mAttributes = attr;
            this.mIPlayer = iplayer;
        }

        public int hashCode() {
            return Objects.hash(Integer.valueOf(this.mPlayerType));
        }

        @Override // android.p007os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.p007os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.mPlayerType);
            this.mAttributes.writeToParcel(dest, 0);
            dest.writeStrongBinder(this.mIPlayer == null ? null : this.mIPlayer.asBinder());
        }

        private PlayerIdCard(Parcel in) {
            this.mPlayerType = in.readInt();
            this.mAttributes = AudioAttributes.CREATOR.createFromParcel(in);
            IBinder b = in.readStrongBinder();
            this.mIPlayer = b == null ? null : IPlayer.Stub.asInterface(b);
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || !(o instanceof PlayerIdCard)) {
                return false;
            }
            PlayerIdCard that = (PlayerIdCard) o;
            if (this.mPlayerType == that.mPlayerType && this.mAttributes.equals(that.mAttributes)) {
                return true;
            }
            return false;
        }
    }

    public static void deprecateStreamTypeForPlayback(int streamType, String className, String opName) throws IllegalArgumentException {
        if (streamType == 10) {
            throw new IllegalArgumentException("Use of STREAM_ACCESSIBILITY is reserved for volume control");
        }
        Log.m64w(className, "Use of stream types is deprecated for operations other than volume control");
        Log.m64w(className, "See the documentation of " + opName + " for what to use instead with android.media.AudioAttributes to qualify your playback use case");
    }
}
