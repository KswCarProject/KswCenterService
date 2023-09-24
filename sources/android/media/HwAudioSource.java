package android.media;

import android.annotation.SystemApi;
import android.media.AudioAttributes;
import android.media.VolumeShaper;
import com.android.internal.util.Preconditions;

@SystemApi
/* loaded from: classes3.dex */
public class HwAudioSource extends PlayerBase {
    private final AudioAttributes mAudioAttributes;
    private final AudioDeviceInfo mAudioDeviceInfo;
    private int mNativeHandle;

    private HwAudioSource(AudioDeviceInfo device, AudioAttributes attributes) {
        super(attributes, 14);
        Preconditions.checkNotNull(device);
        Preconditions.checkNotNull(attributes);
        Preconditions.checkArgument(device.isSource(), "Requires a source device");
        this.mAudioDeviceInfo = device;
        this.mAudioAttributes = attributes;
        baseRegisterPlayer();
    }

    @Override // android.media.PlayerBase
    void playerSetVolume(boolean muting, float leftVolume, float rightVolume) {
    }

    @Override // android.media.PlayerBase
    int playerApplyVolumeShaper(VolumeShaper.Configuration configuration, VolumeShaper.Operation operation) {
        return 0;
    }

    @Override // android.media.PlayerBase
    VolumeShaper.State playerGetVolumeShaperState(int id) {
        return new VolumeShaper.State(1.0f, 1.0f);
    }

    @Override // android.media.PlayerBase
    int playerSetAuxEffectSendLevel(boolean muting, float level) {
        return 0;
    }

    @Override // android.media.PlayerBase
    void playerStart() {
        start();
    }

    @Override // android.media.PlayerBase
    void playerPause() {
        stop();
    }

    @Override // android.media.PlayerBase
    void playerStop() {
        stop();
    }

    public void start() {
        Preconditions.checkState(!isPlaying(), "HwAudioSource is currently playing");
        baseStart();
        this.mNativeHandle = AudioSystem.startAudioSource(this.mAudioDeviceInfo.getPort().activeConfig(), this.mAudioAttributes);
    }

    public boolean isPlaying() {
        return this.mNativeHandle != 0;
    }

    public void stop() {
        baseStop();
        if (this.mNativeHandle > 0) {
            AudioSystem.stopAudioSource(this.mNativeHandle);
            this.mNativeHandle = 0;
        }
    }

    /* loaded from: classes3.dex */
    public static final class Builder {
        private AudioAttributes mAudioAttributes;
        private AudioDeviceInfo mAudioDeviceInfo;

        public Builder setAudioAttributes(AudioAttributes attributes) {
            Preconditions.checkNotNull(attributes);
            this.mAudioAttributes = attributes;
            return this;
        }

        public Builder setAudioDeviceInfo(AudioDeviceInfo info) {
            Preconditions.checkNotNull(info);
            Preconditions.checkArgument(info.isSource());
            this.mAudioDeviceInfo = info;
            return this;
        }

        public HwAudioSource build() {
            Preconditions.checkNotNull(this.mAudioDeviceInfo);
            if (this.mAudioAttributes == null) {
                this.mAudioAttributes = new AudioAttributes.Builder().setUsage(1).build();
            }
            return new HwAudioSource(this.mAudioDeviceInfo, this.mAudioAttributes);
        }
    }
}
