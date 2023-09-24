package android.content.p002pm;

import android.content.p002pm.PackageParser;
import com.android.internal.annotations.VisibleForTesting;

@VisibleForTesting
/* renamed from: android.content.pm.OrgApacheHttpLegacyUpdater */
/* loaded from: classes.dex */
public class OrgApacheHttpLegacyUpdater extends PackageSharedLibraryUpdater {
    private static boolean apkTargetsApiLevelLessThanOrEqualToOMR1(PackageParser.Package pkg) {
        int targetSdkVersion = pkg.applicationInfo.targetSdkVersion;
        return targetSdkVersion < 28;
    }

    @Override // android.content.p002pm.PackageSharedLibraryUpdater
    public void updatePackage(PackageParser.Package pkg) {
        if (apkTargetsApiLevelLessThanOrEqualToOMR1(pkg)) {
            prefixRequiredLibrary(pkg, SharedLibraryNames.ORG_APACHE_HTTP_LEGACY);
        }
    }
}