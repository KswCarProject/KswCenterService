package android.content.p002pm;

import android.content.p002pm.PackageParser;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.util.ArrayUtils;
import java.util.ArrayList;

@VisibleForTesting
/* renamed from: android.content.pm.PackageSharedLibraryUpdater */
/* loaded from: classes.dex */
public abstract class PackageSharedLibraryUpdater {
    public abstract void updatePackage(PackageParser.Package r1);

    static void removeLibrary(PackageParser.Package pkg, String libraryName) {
        pkg.usesLibraries = ArrayUtils.remove(pkg.usesLibraries, libraryName);
        pkg.usesOptionalLibraries = ArrayUtils.remove(pkg.usesOptionalLibraries, libraryName);
    }

    static <T> ArrayList<T> prefix(ArrayList<T> cur, T val) {
        if (cur == null) {
            cur = new ArrayList<>();
        }
        cur.add(0, val);
        return cur;
    }

    private static boolean isLibraryPresent(ArrayList<String> usesLibraries, ArrayList<String> usesOptionalLibraries, String apacheHttpLegacy) {
        return ArrayUtils.contains(usesLibraries, apacheHttpLegacy) || ArrayUtils.contains(usesOptionalLibraries, apacheHttpLegacy);
    }

    void prefixImplicitDependency(PackageParser.Package pkg, String existingLibrary, String implicitDependency) {
        ArrayList<String> usesLibraries = pkg.usesLibraries;
        ArrayList<String> usesOptionalLibraries = pkg.usesOptionalLibraries;
        if (!isLibraryPresent(usesLibraries, usesOptionalLibraries, implicitDependency)) {
            if (ArrayUtils.contains(usesLibraries, existingLibrary)) {
                prefix(usesLibraries, implicitDependency);
            } else if (ArrayUtils.contains(usesOptionalLibraries, existingLibrary)) {
                prefix(usesOptionalLibraries, implicitDependency);
            }
            pkg.usesLibraries = usesLibraries;
            pkg.usesOptionalLibraries = usesOptionalLibraries;
        }
    }

    void prefixRequiredLibrary(PackageParser.Package pkg, String libraryName) {
        ArrayList<String> usesLibraries = pkg.usesLibraries;
        ArrayList<String> usesOptionalLibraries = pkg.usesOptionalLibraries;
        boolean alreadyPresent = isLibraryPresent(usesLibraries, usesOptionalLibraries, libraryName);
        if (!alreadyPresent) {
            pkg.usesLibraries = prefix(usesLibraries, libraryName);
        }
    }
}
