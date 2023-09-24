package android.p007os.storage;

import android.annotation.UnsupportedAppUsage;

/* renamed from: android.os.storage.StorageEventListener */
/* loaded from: classes3.dex */
public class StorageEventListener {
    @UnsupportedAppUsage
    public void onUsbMassStorageConnectionChanged(boolean connected) {
    }

    @UnsupportedAppUsage
    public void onStorageStateChanged(String path, String oldState, String newState) {
    }

    @UnsupportedAppUsage
    public void onVolumeStateChanged(VolumeInfo vol, int oldState, int newState) {
    }

    @UnsupportedAppUsage
    public void onVolumeRecordChanged(VolumeRecord rec) {
    }

    @UnsupportedAppUsage
    public void onVolumeForgotten(String fsUuid) {
    }

    @UnsupportedAppUsage
    public void onDiskScanned(DiskInfo disk, int volumeCount) {
    }

    @UnsupportedAppUsage
    public void onDiskDestroyed(DiskInfo disk) {
    }
}
