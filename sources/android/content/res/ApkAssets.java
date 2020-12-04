package android.content.res;

import android.annotation.UnsupportedAppUsage;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.util.Preconditions;
import java.io.FileDescriptor;
import java.io.IOException;

public final class ApkAssets {
    @GuardedBy({"this"})
    private final long mNativePtr;
    @GuardedBy({"this"})
    private boolean mOpen = true;
    @GuardedBy({"this"})
    private final StringBlock mStringBlock;

    private static native void nativeDestroy(long j);

    private static native String nativeGetAssetPath(long j);

    private static native long nativeGetStringBlock(long j);

    private static native boolean nativeIsUpToDate(long j);

    private static native long nativeLoad(String str, boolean z, boolean z2, boolean z3) throws IOException;

    private static native long nativeLoadFromFd(FileDescriptor fileDescriptor, String str, boolean z, boolean z2) throws IOException;

    private static native long nativeOpenXml(long j, String str) throws IOException;

    public static ApkAssets loadFromPath(String path) throws IOException {
        return new ApkAssets(path, false, false, false);
    }

    public static ApkAssets loadFromPath(String path, boolean system) throws IOException {
        return new ApkAssets(path, system, false, false);
    }

    public static ApkAssets loadFromPath(String path, boolean system, boolean forceSharedLibrary) throws IOException {
        return new ApkAssets(path, system, forceSharedLibrary, false);
    }

    public static ApkAssets loadFromFd(FileDescriptor fd, String friendlyName, boolean system, boolean forceSharedLibrary) throws IOException {
        return new ApkAssets(fd, friendlyName, system, forceSharedLibrary);
    }

    public static ApkAssets loadOverlayFromPath(String idmapPath, boolean system) throws IOException {
        return new ApkAssets(idmapPath, system, false, true);
    }

    private ApkAssets(String path, boolean system, boolean forceSharedLib, boolean overlay) throws IOException {
        Preconditions.checkNotNull(path, "path");
        this.mNativePtr = nativeLoad(path, system, forceSharedLib, overlay);
        this.mStringBlock = new StringBlock(nativeGetStringBlock(this.mNativePtr), true);
    }

    private ApkAssets(FileDescriptor fd, String friendlyName, boolean system, boolean forceSharedLib) throws IOException {
        Preconditions.checkNotNull(fd, "fd");
        Preconditions.checkNotNull(friendlyName, "friendlyName");
        this.mNativePtr = nativeLoadFromFd(fd, friendlyName, system, forceSharedLib);
        this.mStringBlock = new StringBlock(nativeGetStringBlock(this.mNativePtr), true);
    }

    @UnsupportedAppUsage
    public String getAssetPath() {
        String nativeGetAssetPath;
        synchronized (this) {
            nativeGetAssetPath = nativeGetAssetPath(this.mNativePtr);
        }
        return nativeGetAssetPath;
    }

    /* access modifiers changed from: package-private */
    public CharSequence getStringFromPool(int idx) {
        CharSequence charSequence;
        synchronized (this) {
            charSequence = this.mStringBlock.get(idx);
        }
        return charSequence;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0026, code lost:
        r4 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x002a, code lost:
        if (r3 != null) goto L_0x002c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:?, code lost:
        r2.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0030, code lost:
        r5 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:?, code lost:
        r3.addSuppressed(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0035, code lost:
        r2.close();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.content.res.XmlResourceParser openXml(java.lang.String r8) throws java.io.IOException {
        /*
            r7 = this;
            java.lang.String r0 = "fileName"
            com.android.internal.util.Preconditions.checkNotNull(r8, r0)
            monitor-enter(r7)
            long r0 = r7.mNativePtr     // Catch:{ all -> 0x0039 }
            long r0 = nativeOpenXml(r0, r8)     // Catch:{ all -> 0x0039 }
            android.content.res.XmlBlock r2 = new android.content.res.XmlBlock     // Catch:{ all -> 0x0039 }
            r3 = 0
            r2.<init>(r3, r0)     // Catch:{ all -> 0x0039 }
            android.content.res.XmlResourceParser r4 = r2.newParser()     // Catch:{ Throwable -> 0x0028 }
            if (r4 == 0) goto L_0x001e
            r2.close()     // Catch:{ all -> 0x0039 }
            monitor-exit(r7)     // Catch:{ all -> 0x0039 }
            return r4
        L_0x001e:
            java.lang.AssertionError r5 = new java.lang.AssertionError     // Catch:{ Throwable -> 0x0028 }
            java.lang.String r6 = "block.newParser() returned a null parser"
            r5.<init>(r6)     // Catch:{ Throwable -> 0x0028 }
            throw r5     // Catch:{ Throwable -> 0x0028 }
        L_0x0026:
            r4 = move-exception
            goto L_0x002a
        L_0x0028:
            r3 = move-exception
            throw r3     // Catch:{ all -> 0x0026 }
        L_0x002a:
            if (r3 == 0) goto L_0x0035
            r2.close()     // Catch:{ Throwable -> 0x0030 }
            goto L_0x0038
        L_0x0030:
            r5 = move-exception
            r3.addSuppressed(r5)     // Catch:{ all -> 0x0039 }
            goto L_0x0038
        L_0x0035:
            r2.close()     // Catch:{ all -> 0x0039 }
        L_0x0038:
            throw r4     // Catch:{ all -> 0x0039 }
        L_0x0039:
            r0 = move-exception
            monitor-exit(r7)     // Catch:{ all -> 0x0039 }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.content.res.ApkAssets.openXml(java.lang.String):android.content.res.XmlResourceParser");
    }

    public boolean isUpToDate() {
        boolean nativeIsUpToDate;
        synchronized (this) {
            nativeIsUpToDate = nativeIsUpToDate(this.mNativePtr);
        }
        return nativeIsUpToDate;
    }

    public String toString() {
        return "ApkAssets{path=" + getAssetPath() + "}";
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        close();
    }

    public void close() throws Throwable {
        synchronized (this) {
            if (this.mOpen) {
                this.mOpen = false;
                this.mStringBlock.close();
                nativeDestroy(this.mNativePtr);
            }
        }
    }
}
