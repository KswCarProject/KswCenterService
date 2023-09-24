package android.p007os.storage;

import android.p007os.IVold;

/* renamed from: android.os.storage.StorageManagerInternal */
/* loaded from: classes3.dex */
public abstract class StorageManagerInternal {

    /* renamed from: android.os.storage.StorageManagerInternal$ExternalStorageMountPolicy */
    /* loaded from: classes3.dex */
    public interface ExternalStorageMountPolicy {
        int getMountMode(int i, String str);

        boolean hasExternalStorage(int i, String str);
    }

    /* renamed from: android.os.storage.StorageManagerInternal$ResetListener */
    /* loaded from: classes3.dex */
    public interface ResetListener {
        void onReset(IVold iVold);
    }

    public abstract void addExternalStoragePolicy(ExternalStorageMountPolicy externalStorageMountPolicy);

    public abstract void addResetListener(ResetListener resetListener);

    public abstract int getExternalStorageMountMode(int i, String str);

    public abstract void onAppOpsChanged(int i, int i2, String str, int i3);

    public abstract void onExternalStoragePolicyChanged(int i, String str);
}
