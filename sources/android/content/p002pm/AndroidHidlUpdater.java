package android.content.p002pm;

import android.content.p002pm.PackageParser;
import com.android.internal.annotations.VisibleForTesting;

@VisibleForTesting
/* renamed from: android.content.pm.AndroidHidlUpdater */
/* loaded from: classes.dex */
public class AndroidHidlUpdater extends PackageSharedLibraryUpdater {
    @Override // android.content.p002pm.PackageSharedLibraryUpdater
    public void updatePackage(PackageParser.Package pkg) {
        ApplicationInfo info = pkg.applicationInfo;
        boolean isSystem = false;
        boolean isLegacy = info.targetSdkVersion <= 28;
        if (info.isSystemApp() || info.isUpdatedSystemApp()) {
            isSystem = true;
        }
        if (isLegacy && isSystem) {
            prefixRequiredLibrary(pkg, "android.hidl.base-V1.0-java");
            prefixRequiredLibrary(pkg, "android.hidl.manager-V1.0-java");
            return;
        }
        removeLibrary(pkg, "android.hidl.base-V1.0-java");
        removeLibrary(pkg, "android.hidl.manager-V1.0-java");
    }
}
