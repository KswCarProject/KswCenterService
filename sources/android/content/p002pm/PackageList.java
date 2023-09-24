package android.content.p002pm;

import android.content.p002pm.PackageManagerInternal;
import com.android.server.LocalServices;
import java.util.List;

/* renamed from: android.content.pm.PackageList */
/* loaded from: classes.dex */
public class PackageList implements PackageManagerInternal.PackageListObserver, AutoCloseable {
    private final List<String> mPackageNames;
    private final PackageManagerInternal.PackageListObserver mWrappedObserver;

    public PackageList(List<String> packageNames, PackageManagerInternal.PackageListObserver observer) {
        this.mPackageNames = packageNames;
        this.mWrappedObserver = observer;
    }

    @Override // android.content.p002pm.PackageManagerInternal.PackageListObserver
    public void onPackageAdded(String packageName, int uid) {
        if (this.mWrappedObserver != null) {
            this.mWrappedObserver.onPackageAdded(packageName, uid);
        }
    }

    @Override // android.content.p002pm.PackageManagerInternal.PackageListObserver
    public void onPackageRemoved(String packageName, int uid) {
        if (this.mWrappedObserver != null) {
            this.mWrappedObserver.onPackageRemoved(packageName, uid);
        }
    }

    @Override // java.lang.AutoCloseable
    public void close() throws Exception {
        ((PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class)).removePackageListObserver(this);
    }

    public List<String> getPackageNames() {
        return this.mPackageNames;
    }
}
