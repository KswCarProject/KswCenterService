package android.support.p011v4.media;

import android.media.VolumeProvider;
import android.support.annotation.RequiresApi;

@RequiresApi(21)
/* renamed from: android.support.v4.media.VolumeProviderCompatApi21 */
/* loaded from: classes3.dex */
class VolumeProviderCompatApi21 {

    /* renamed from: android.support.v4.media.VolumeProviderCompatApi21$Delegate */
    /* loaded from: classes3.dex */
    public interface Delegate {
        void onAdjustVolume(int i);

        void onSetVolumeTo(int i);
    }

    VolumeProviderCompatApi21() {
    }

    public static Object createVolumeProvider(int volumeControl, int maxVolume, int currentVolume, final Delegate delegate) {
        return new VolumeProvider(volumeControl, maxVolume, currentVolume) { // from class: android.support.v4.media.VolumeProviderCompatApi21.1
            @Override // android.media.VolumeProvider
            public void onSetVolumeTo(int volume) {
                delegate.onSetVolumeTo(volume);
            }

            @Override // android.media.VolumeProvider
            public void onAdjustVolume(int direction) {
                delegate.onAdjustVolume(direction);
            }
        };
    }

    public static void setCurrentVolume(Object volumeProviderObj, int currentVolume) {
        ((VolumeProvider) volumeProviderObj).setCurrentVolume(currentVolume);
    }
}
