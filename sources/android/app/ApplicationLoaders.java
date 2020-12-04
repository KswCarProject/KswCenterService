package android.app;

import android.annotation.UnsupportedAppUsage;
import android.content.pm.SharedLibraryInfo;
import android.os.Build;
import android.util.ArrayMap;
import android.util.Log;
import dalvik.system.PathClassLoader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApplicationLoaders {
    private static final String TAG = "ApplicationLoaders";
    private static final ApplicationLoaders gApplicationLoaders = new ApplicationLoaders();
    @UnsupportedAppUsage
    private final ArrayMap<String, ClassLoader> mLoaders = new ArrayMap<>();
    private Map<String, CachedClassLoader> mSystemLibsCacheMap = null;

    @UnsupportedAppUsage
    public static ApplicationLoaders getDefault() {
        return gApplicationLoaders;
    }

    /* access modifiers changed from: package-private */
    public ClassLoader getClassLoader(String zip, int targetSdkVersion, boolean isBundled, String librarySearchPath, String libraryPermittedPath, ClassLoader parent, String classLoaderName) {
        return getClassLoaderWithSharedLibraries(zip, targetSdkVersion, isBundled, librarySearchPath, libraryPermittedPath, parent, classLoaderName, (List<ClassLoader>) null);
    }

    /* access modifiers changed from: package-private */
    public ClassLoader getClassLoaderWithSharedLibraries(String zip, int targetSdkVersion, boolean isBundled, String librarySearchPath, String libraryPermittedPath, ClassLoader parent, String classLoaderName, List<ClassLoader> sharedLibraries) {
        return getClassLoader(zip, targetSdkVersion, isBundled, librarySearchPath, libraryPermittedPath, parent, zip, classLoaderName, sharedLibraries);
    }

    /* access modifiers changed from: package-private */
    public ClassLoader getSharedLibraryClassLoaderWithSharedLibraries(String zip, int targetSdkVersion, boolean isBundled, String librarySearchPath, String libraryPermittedPath, ClassLoader parent, String classLoaderName, List<ClassLoader> sharedLibraries) {
        ClassLoader loader = getCachedNonBootclasspathSystemLib(zip, parent, classLoaderName, sharedLibraries);
        if (loader != null) {
            return loader;
        }
        return getClassLoaderWithSharedLibraries(zip, targetSdkVersion, isBundled, librarySearchPath, libraryPermittedPath, parent, classLoaderName, sharedLibraries);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:19:0x005f, code lost:
        return r2;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.ClassLoader getClassLoader(java.lang.String r18, int r19, boolean r20, java.lang.String r21, java.lang.String r22, java.lang.ClassLoader r23, java.lang.String r24, java.lang.String r25, java.util.List<java.lang.ClassLoader> r26) {
        /*
            r17 = this;
            r1 = r17
            r10 = r18
            r11 = r24
            java.lang.ClassLoader r0 = java.lang.ClassLoader.getSystemClassLoader()
            java.lang.ClassLoader r12 = r0.getParent()
            android.util.ArrayMap<java.lang.String, java.lang.ClassLoader> r13 = r1.mLoaders
            monitor-enter(r13)
            if (r23 != 0) goto L_0x0016
            r0 = r12
            r14 = r0
            goto L_0x0018
        L_0x0016:
            r14 = r23
        L_0x0018:
            r8 = 64
            if (r14 != r12) goto L_0x006a
            android.util.ArrayMap<java.lang.String, java.lang.ClassLoader> r0 = r1.mLoaders     // Catch:{ all -> 0x0060 }
            java.lang.Object r0 = r0.get(r11)     // Catch:{ all -> 0x0060 }
            java.lang.ClassLoader r0 = (java.lang.ClassLoader) r0     // Catch:{ all -> 0x0060 }
            if (r0 == 0) goto L_0x0028
            monitor-exit(r13)     // Catch:{ all -> 0x0060 }
            return r0
        L_0x0028:
            android.os.Trace.traceBegin(r8, r10)     // Catch:{ all -> 0x0060 }
            r2 = r18
            r3 = r21
            r4 = r22
            r5 = r14
            r6 = r19
            r7 = r20
            r15 = r8
            r8 = r25
            r9 = r26
            java.lang.ClassLoader r2 = com.android.internal.os.ClassLoaderFactory.createClassLoader(r2, r3, r4, r5, r6, r7, r8, r9)     // Catch:{ all -> 0x0060 }
            android.os.Trace.traceEnd(r15)     // Catch:{ all -> 0x0060 }
            java.lang.String r3 = "setLayerPaths"
            r4 = r15
            android.os.Trace.traceBegin(r4, r3)     // Catch:{ all -> 0x0060 }
            android.os.GraphicsEnvironment r3 = android.os.GraphicsEnvironment.getInstance()     // Catch:{ all -> 0x0060 }
            r6 = r21
            r7 = r22
            r3.setLayerPaths(r2, r6, r7)     // Catch:{ all -> 0x0082 }
            android.os.Trace.traceEnd(r4)     // Catch:{ all -> 0x0082 }
            if (r11 == 0) goto L_0x005e
            android.util.ArrayMap<java.lang.String, java.lang.ClassLoader> r3 = r1.mLoaders     // Catch:{ all -> 0x0082 }
            r3.put(r11, r2)     // Catch:{ all -> 0x0082 }
        L_0x005e:
            monitor-exit(r13)     // Catch:{ all -> 0x0082 }
            return r2
        L_0x0060:
            r0 = move-exception
            r6 = r21
            r7 = r22
        L_0x0065:
            r2 = r25
            r3 = r26
            goto L_0x0084
        L_0x006a:
            r6 = r21
            r7 = r22
            r4 = r8
            android.os.Trace.traceBegin(r4, r10)     // Catch:{ all -> 0x0082 }
            r0 = 0
            r2 = r25
            r3 = r26
            java.lang.ClassLoader r0 = com.android.internal.os.ClassLoaderFactory.createClassLoader(r10, r0, r14, r2, r3)     // Catch:{ all -> 0x0080 }
            android.os.Trace.traceEnd(r4)     // Catch:{ all -> 0x0080 }
            monitor-exit(r13)     // Catch:{ all -> 0x0080 }
            return r0
        L_0x0080:
            r0 = move-exception
            goto L_0x0084
        L_0x0082:
            r0 = move-exception
            goto L_0x0065
        L_0x0084:
            monitor-exit(r13)     // Catch:{ all -> 0x0080 }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.app.ApplicationLoaders.getClassLoader(java.lang.String, int, boolean, java.lang.String, java.lang.String, java.lang.ClassLoader, java.lang.String, java.lang.String, java.util.List):java.lang.ClassLoader");
    }

    public void createAndCacheNonBootclasspathSystemClassLoaders(SharedLibraryInfo[] libs) {
        if (this.mSystemLibsCacheMap == null) {
            this.mSystemLibsCacheMap = new HashMap();
            for (SharedLibraryInfo lib : libs) {
                createAndCacheNonBootclasspathSystemClassLoader(lib);
            }
            return;
        }
        throw new IllegalStateException("Already cached.");
    }

    private void createAndCacheNonBootclasspathSystemClassLoader(SharedLibraryInfo lib) {
        String path = lib.getPath();
        List<SharedLibraryInfo> dependencies = lib.getDependencies();
        ArrayList<ClassLoader> sharedLibraries = null;
        if (dependencies != null) {
            sharedLibraries = new ArrayList<>(dependencies.size());
            for (SharedLibraryInfo dependency : dependencies) {
                String dependencyPath = dependency.getPath();
                CachedClassLoader cached = this.mSystemLibsCacheMap.get(dependencyPath);
                if (cached != null) {
                    sharedLibraries.add(cached.loader);
                } else {
                    throw new IllegalStateException("Failed to find dependency " + dependencyPath + " of cachedlibrary " + path);
                }
            }
        }
        ArrayList<ClassLoader> sharedLibraries2 = sharedLibraries;
        ClassLoader classLoader = getClassLoader(path, Build.VERSION.SDK_INT, true, (String) null, (String) null, (ClassLoader) null, (String) null, (String) null, sharedLibraries2);
        if (classLoader != null) {
            CachedClassLoader cached2 = new CachedClassLoader();
            cached2.loader = classLoader;
            cached2.sharedLibraries = sharedLibraries2;
            Log.d(TAG, "Created zygote-cached class loader: " + path);
            this.mSystemLibsCacheMap.put(path, cached2);
            return;
        }
        throw new IllegalStateException("Failed to cache " + path);
    }

    private static boolean sharedLibrariesEquals(List<ClassLoader> lhs, List<ClassLoader> rhs) {
        if (lhs == null) {
            return rhs == null;
        }
        return lhs.equals(rhs);
    }

    public ClassLoader getCachedNonBootclasspathSystemLib(String zip, ClassLoader parent, String classLoaderName, List<ClassLoader> sharedLibraries) {
        CachedClassLoader cached;
        if (this.mSystemLibsCacheMap == null || parent != null || classLoaderName != null || (cached = this.mSystemLibsCacheMap.get(zip)) == null) {
            return null;
        }
        if (!sharedLibrariesEquals(sharedLibraries, cached.sharedLibraries)) {
            Log.w(TAG, "Unexpected environment for cached library: (" + sharedLibraries + "|" + cached.sharedLibraries + ")");
            return null;
        }
        Log.d(TAG, "Returning zygote-cached class loader: " + zip);
        return cached.loader;
    }

    public ClassLoader createAndCacheWebViewClassLoader(String packagePath, String libsPath, String cacheKey) {
        return getClassLoader(packagePath, Build.VERSION.SDK_INT, false, libsPath, (String) null, (ClassLoader) null, cacheKey, (String) null, (List<ClassLoader>) null);
    }

    /* access modifiers changed from: package-private */
    public void addPath(ClassLoader classLoader, String dexPath) {
        if (classLoader instanceof PathClassLoader) {
            ((PathClassLoader) classLoader).addDexPath(dexPath);
            return;
        }
        throw new IllegalStateException("class loader is not a PathClassLoader");
    }

    /* access modifiers changed from: package-private */
    public void addNative(ClassLoader classLoader, Collection<String> libPaths) {
        if (classLoader instanceof PathClassLoader) {
            ((PathClassLoader) classLoader).addNativePath(libPaths);
            return;
        }
        throw new IllegalStateException("class loader is not a PathClassLoader");
    }

    private static class CachedClassLoader {
        ClassLoader loader;
        List<ClassLoader> sharedLibraries;

        private CachedClassLoader() {
        }
    }
}
