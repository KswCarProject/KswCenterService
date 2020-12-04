package android.app;

import android.annotation.UnsupportedAppUsage;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageParser;
import android.content.res.ApkAssets;
import android.content.res.AssetManager;
import android.content.res.CompatResources;
import android.content.res.CompatibilityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.ResourcesImpl;
import android.content.res.ResourcesKey;
import android.hardware.display.DisplayManagerGlobal;
import android.os.IBinder;
import android.os.Process;
import android.os.Trace;
import android.util.ArrayMap;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.LruCache;
import android.util.Pair;
import android.util.Slog;
import android.view.Display;
import android.view.DisplayAdjustments;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.IndentingPrintWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.WeakHashMap;
import java.util.function.Predicate;

public class ResourcesManager {
    private static final boolean DEBUG = false;
    private static final boolean ENABLE_APK_ASSETS_CACHE = true;
    static final String TAG = "ResourcesManager";
    private static final Predicate<WeakReference<Resources>> sEmptyReferencePredicate = $$Lambda$ResourcesManager$QJ7UiVk_XS90KuXAsIjIEym1DnM.INSTANCE;
    private static ResourcesManager sResourcesManager;
    @UnsupportedAppUsage
    private final WeakHashMap<IBinder, ActivityResources> mActivityResourceReferences = new WeakHashMap<>();
    private final ArrayMap<Pair<Integer, DisplayAdjustments>, WeakReference<Display>> mAdjustedDisplays = new ArrayMap<>();
    private final ArrayMap<ApkKey, WeakReference<ApkAssets>> mCachedApkAssets = new ArrayMap<>();
    private final LruCache<ApkKey, ApkAssets> mLoadedApkAssets = new LruCache<>(3);
    private CompatibilityInfo mResCompatibilityInfo;
    @UnsupportedAppUsage
    private final Configuration mResConfiguration = new Configuration();
    @UnsupportedAppUsage
    private final ArrayMap<ResourcesKey, WeakReference<ResourcesImpl>> mResourceImpls = new ArrayMap<>();
    @UnsupportedAppUsage
    private final ArrayList<WeakReference<Resources>> mResourceReferences = new ArrayList<>();

    static /* synthetic */ boolean lambda$static$0(WeakReference weakRef) {
        return weakRef == null || weakRef.get() == null;
    }

    private static class ApkKey {
        public final boolean overlay;
        public final String path;
        public final boolean sharedLib;

        ApkKey(String path2, boolean sharedLib2, boolean overlay2) {
            this.path = path2;
            this.sharedLib = sharedLib2;
            this.overlay = overlay2;
        }

        public int hashCode() {
            return (((((1 * 31) + this.path.hashCode()) * 31) + Boolean.hashCode(this.sharedLib)) * 31) + Boolean.hashCode(this.overlay);
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof ApkKey)) {
                return false;
            }
            ApkKey other = (ApkKey) obj;
            if (this.path.equals(other.path) && this.sharedLib == other.sharedLib && this.overlay == other.overlay) {
                return true;
            }
            return false;
        }
    }

    private static class ActivityResources {
        public final ArrayList<WeakReference<Resources>> activityResources;
        public final Configuration overrideConfig;

        private ActivityResources() {
            this.overrideConfig = new Configuration();
            this.activityResources = new ArrayList<>();
        }
    }

    @UnsupportedAppUsage
    public static ResourcesManager getInstance() {
        ResourcesManager resourcesManager;
        synchronized (ResourcesManager.class) {
            if (sResourcesManager == null) {
                sResourcesManager = new ResourcesManager();
            }
            resourcesManager = sResourcesManager;
        }
        return resourcesManager;
    }

    public void invalidatePath(String path) {
        synchronized (this) {
            int count = 0;
            int i = 0;
            while (i < this.mResourceImpls.size()) {
                ResourcesKey key = this.mResourceImpls.keyAt(i);
                if (key.isPathReferenced(path)) {
                    cleanupResourceImpl(key);
                    count++;
                } else {
                    i++;
                }
            }
            Log.i(TAG, "Invalidated " + count + " asset managers that referenced " + path);
        }
    }

    public Configuration getConfiguration() {
        Configuration configuration;
        synchronized (this) {
            configuration = this.mResConfiguration;
        }
        return configuration;
    }

    /* access modifiers changed from: package-private */
    public DisplayMetrics getDisplayMetrics() {
        return getDisplayMetrics(0, DisplayAdjustments.DEFAULT_DISPLAY_ADJUSTMENTS);
    }

    /* access modifiers changed from: protected */
    @VisibleForTesting
    public DisplayMetrics getDisplayMetrics(int displayId, DisplayAdjustments da) {
        DisplayMetrics dm = new DisplayMetrics();
        Display display = getAdjustedDisplay(displayId, da);
        if (display != null) {
            display.getMetrics(dm);
        } else {
            dm.setToDefaults();
        }
        return dm;
    }

    private static void applyNonDefaultDisplayMetricsToConfiguration(DisplayMetrics dm, Configuration config) {
        config.touchscreen = 1;
        config.densityDpi = dm.densityDpi;
        config.screenWidthDp = (int) (((float) dm.widthPixels) / dm.density);
        config.screenHeightDp = (int) (((float) dm.heightPixels) / dm.density);
        int sl = Configuration.resetScreenLayout(config.screenLayout);
        if (dm.widthPixels > dm.heightPixels) {
            config.orientation = 2;
            config.screenLayout = Configuration.reduceScreenLayout(sl, config.screenWidthDp, config.screenHeightDp);
        } else {
            config.orientation = 1;
            config.screenLayout = Configuration.reduceScreenLayout(sl, config.screenHeightDp, config.screenWidthDp);
        }
        config.smallestScreenWidthDp = Math.min(config.screenWidthDp, config.screenHeightDp);
        config.compatScreenWidthDp = config.screenWidthDp;
        config.compatScreenHeightDp = config.screenHeightDp;
        config.compatSmallestScreenWidthDp = config.smallestScreenWidthDp;
    }

    public boolean applyCompatConfigurationLocked(int displayDensity, Configuration compatConfiguration) {
        if (this.mResCompatibilityInfo == null || this.mResCompatibilityInfo.supportsScreen()) {
            return false;
        }
        this.mResCompatibilityInfo.applyToConfiguration(displayDensity, compatConfiguration);
        return true;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0049, code lost:
        return r4;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private android.view.Display getAdjustedDisplay(int r8, android.view.DisplayAdjustments r9) {
        /*
            r7 = this;
            if (r9 == 0) goto L_0x0008
            android.view.DisplayAdjustments r0 = new android.view.DisplayAdjustments
            r0.<init>((android.view.DisplayAdjustments) r9)
            goto L_0x000d
        L_0x0008:
            android.view.DisplayAdjustments r0 = new android.view.DisplayAdjustments
            r0.<init>()
        L_0x000d:
            java.lang.Integer r1 = java.lang.Integer.valueOf(r8)
            android.util.Pair r1 = android.util.Pair.create(r1, r0)
            monitor-enter(r7)
            android.util.ArrayMap<android.util.Pair<java.lang.Integer, android.view.DisplayAdjustments>, java.lang.ref.WeakReference<android.view.Display>> r2 = r7.mAdjustedDisplays     // Catch:{ all -> 0x004a }
            java.lang.Object r2 = r2.get(r1)     // Catch:{ all -> 0x004a }
            java.lang.ref.WeakReference r2 = (java.lang.ref.WeakReference) r2     // Catch:{ all -> 0x004a }
            if (r2 == 0) goto L_0x002b
            java.lang.Object r3 = r2.get()     // Catch:{ all -> 0x004a }
            android.view.Display r3 = (android.view.Display) r3     // Catch:{ all -> 0x004a }
            if (r3 == 0) goto L_0x002b
            monitor-exit(r7)     // Catch:{ all -> 0x004a }
            return r3
        L_0x002b:
            android.hardware.display.DisplayManagerGlobal r3 = android.hardware.display.DisplayManagerGlobal.getInstance()     // Catch:{ all -> 0x004a }
            if (r3 != 0) goto L_0x0034
            r4 = 0
            monitor-exit(r7)     // Catch:{ all -> 0x004a }
            return r4
        L_0x0034:
            S r4 = r1.second     // Catch:{ all -> 0x004a }
            android.view.DisplayAdjustments r4 = (android.view.DisplayAdjustments) r4     // Catch:{ all -> 0x004a }
            android.view.Display r4 = r3.getCompatibleDisplay((int) r8, (android.view.DisplayAdjustments) r4)     // Catch:{ all -> 0x004a }
            if (r4 == 0) goto L_0x0048
            android.util.ArrayMap<android.util.Pair<java.lang.Integer, android.view.DisplayAdjustments>, java.lang.ref.WeakReference<android.view.Display>> r5 = r7.mAdjustedDisplays     // Catch:{ all -> 0x004a }
            java.lang.ref.WeakReference r6 = new java.lang.ref.WeakReference     // Catch:{ all -> 0x004a }
            r6.<init>(r4)     // Catch:{ all -> 0x004a }
            r5.put(r1, r6)     // Catch:{ all -> 0x004a }
        L_0x0048:
            monitor-exit(r7)     // Catch:{ all -> 0x004a }
            return r4
        L_0x004a:
            r2 = move-exception
            monitor-exit(r7)     // Catch:{ all -> 0x004a }
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: android.app.ResourcesManager.getAdjustedDisplay(int, android.view.DisplayAdjustments):android.view.Display");
    }

    public Display getAdjustedDisplay(int displayId, Resources resources) {
        synchronized (this) {
            DisplayManagerGlobal dm = DisplayManagerGlobal.getInstance();
            if (dm == null) {
                return null;
            }
            Display compatibleDisplay = dm.getCompatibleDisplay(displayId, resources);
            return compatibleDisplay;
        }
    }

    private void cleanupResourceImpl(ResourcesKey removedKey) {
        ResourcesImpl res = (ResourcesImpl) this.mResourceImpls.remove(removedKey).get();
        if (res != null) {
            res.flushLayoutCache();
        }
    }

    private static String overlayPathToIdmapPath(String path) {
        return "/data/resource-cache/" + path.substring(1).replace('/', '@') + "@idmap";
    }

    private ApkAssets loadApkAssets(String path, boolean sharedLib, boolean overlay) throws IOException {
        ApkAssets apkAssets;
        ApkAssets apkAssets2;
        ApkKey newKey = new ApkKey(path, sharedLib, overlay);
        if (this.mLoadedApkAssets != null && (apkAssets2 = this.mLoadedApkAssets.get(newKey)) != null) {
            return apkAssets2;
        }
        WeakReference<ApkAssets> apkAssetsRef = this.mCachedApkAssets.get(newKey);
        if (apkAssetsRef != null) {
            ApkAssets apkAssets3 = (ApkAssets) apkAssetsRef.get();
            if (apkAssets3 != null) {
                if (this.mLoadedApkAssets != null) {
                    this.mLoadedApkAssets.put(newKey, apkAssets3);
                }
                return apkAssets3;
            }
            this.mCachedApkAssets.remove(newKey);
        }
        if (overlay) {
            apkAssets = ApkAssets.loadOverlayFromPath(overlayPathToIdmapPath(path), false);
        } else {
            apkAssets = ApkAssets.loadFromPath(path, false, sharedLib);
        }
        if (this.mLoadedApkAssets != null) {
            this.mLoadedApkAssets.put(newKey, apkAssets);
        }
        this.mCachedApkAssets.put(newKey, new WeakReference(apkAssets));
        return apkAssets;
    }

    /* access modifiers changed from: protected */
    @VisibleForTesting
    @UnsupportedAppUsage
    public AssetManager createAssetManager(ResourcesKey key) {
        AssetManager.Builder builder = new AssetManager.Builder();
        if (key.mResDir != null) {
            try {
                builder.addApkAssets(loadApkAssets(key.mResDir, false, false));
            } catch (IOException e) {
                Log.e(TAG, "failed to add asset path " + key.mResDir);
                return null;
            }
        }
        if (key.mSplitResDirs != null) {
            String[] strArr = key.mSplitResDirs;
            int length = strArr.length;
            int i = 0;
            while (i < length) {
                String splitResDir = strArr[i];
                try {
                    builder.addApkAssets(loadApkAssets(splitResDir, false, false));
                    i++;
                } catch (IOException e2) {
                    Log.e(TAG, "failed to add split asset path " + splitResDir);
                    return null;
                }
            }
        }
        if (key.mOverlayDirs != null) {
            for (String idmapPath : key.mOverlayDirs) {
                try {
                    builder.addApkAssets(loadApkAssets(idmapPath, false, true));
                } catch (IOException e3) {
                    Log.w(TAG, "failed to add overlay path " + idmapPath);
                }
            }
        }
        if (key.mLibDirs != null) {
            for (String libDir : key.mLibDirs) {
                if (libDir.endsWith(PackageParser.APK_FILE_EXTENSION)) {
                    try {
                        builder.addApkAssets(loadApkAssets(libDir, true, false));
                    } catch (IOException e4) {
                        Log.w(TAG, "Asset path '" + libDir + "' does not exist or contains no resources.");
                    }
                }
            }
        }
        return builder.build();
    }

    private static <T> int countLiveReferences(Collection<WeakReference<T>> collection) {
        int count = 0;
        Iterator<WeakReference<T>> it = collection.iterator();
        while (it.hasNext()) {
            WeakReference<T> ref = it.next();
            if ((ref != null ? ref.get() : null) != null) {
                count++;
            }
        }
        return count;
    }

    public void dump(String prefix, PrintWriter printWriter) {
        synchronized (this) {
            IndentingPrintWriter pw = new IndentingPrintWriter(printWriter, "  ");
            for (int i = 0; i < prefix.length() / 2; i++) {
                pw.increaseIndent();
            }
            pw.println("ResourcesManager:");
            pw.increaseIndent();
            if (this.mLoadedApkAssets != null) {
                pw.print("cached apks: total=");
                pw.print(this.mLoadedApkAssets.size());
                pw.print(" created=");
                pw.print(this.mLoadedApkAssets.createCount());
                pw.print(" evicted=");
                pw.print(this.mLoadedApkAssets.evictionCount());
                pw.print(" hit=");
                pw.print(this.mLoadedApkAssets.hitCount());
                pw.print(" miss=");
                pw.print(this.mLoadedApkAssets.missCount());
                pw.print(" max=");
                pw.print(this.mLoadedApkAssets.maxSize());
            } else {
                pw.print("cached apks: 0 [cache disabled]");
            }
            pw.println();
            pw.print("total apks: ");
            pw.println(countLiveReferences(this.mCachedApkAssets.values()));
            pw.print("resources: ");
            int references = countLiveReferences(this.mResourceReferences);
            for (ActivityResources activityResources : this.mActivityResourceReferences.values()) {
                references += countLiveReferences(activityResources.activityResources);
            }
            pw.println(references);
            pw.print("resource impls: ");
            pw.println(countLiveReferences(this.mResourceImpls.values()));
        }
    }

    private Configuration generateConfig(ResourcesKey key, DisplayMetrics dm) {
        boolean isDefaultDisplay = key.mDisplayId == 0;
        boolean hasOverrideConfig = key.hasOverrideConfiguration();
        if (isDefaultDisplay && !hasOverrideConfig) {
            return getConfiguration();
        }
        Configuration config = new Configuration(getConfiguration());
        if (!isDefaultDisplay) {
            applyNonDefaultDisplayMetricsToConfiguration(dm, config);
        }
        if (!hasOverrideConfig) {
            return config;
        }
        config.updateFrom(key.mOverrideConfiguration);
        return config;
    }

    private ResourcesImpl createResourcesImpl(ResourcesKey key) {
        DisplayAdjustments daj = new DisplayAdjustments(key.mOverrideConfiguration);
        daj.setCompatibilityInfo(key.mCompatInfo);
        AssetManager assets = createAssetManager(key);
        if (assets == null) {
            return null;
        }
        DisplayMetrics dm = getDisplayMetrics(key.mDisplayId, daj);
        return new ResourcesImpl(assets, dm, generateConfig(key, dm), daj);
    }

    private ResourcesImpl findResourcesImplForKeyLocked(ResourcesKey key) {
        WeakReference<ResourcesImpl> weakImplRef = this.mResourceImpls.get(key);
        ResourcesImpl impl = weakImplRef != null ? (ResourcesImpl) weakImplRef.get() : null;
        if (impl == null || !impl.getAssets().isUpToDate()) {
            return null;
        }
        return impl;
    }

    private ResourcesImpl findOrCreateResourcesImplForKeyLocked(ResourcesKey key) {
        ResourcesImpl impl = findResourcesImplForKeyLocked(key);
        if (impl == null && (impl = createResourcesImpl(key)) != null) {
            this.mResourceImpls.put(key, new WeakReference(impl));
        }
        return impl;
    }

    private ResourcesKey findKeyForResourceImplLocked(ResourcesImpl resourceImpl) {
        int refCount = this.mResourceImpls.size();
        int i = 0;
        while (true) {
            ResourcesImpl impl = null;
            if (i >= refCount) {
                return null;
            }
            WeakReference<ResourcesImpl> weakImplRef = this.mResourceImpls.valueAt(i);
            if (weakImplRef != null) {
                impl = (ResourcesImpl) weakImplRef.get();
            }
            if (impl != null && resourceImpl == impl) {
                return this.mResourceImpls.keyAt(i);
            }
            i++;
        }
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0018, code lost:
        return r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0033, code lost:
        return r1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean isSameResourcesOverrideConfig(android.os.IBinder r5, android.content.res.Configuration r6) {
        /*
            r4 = this;
            monitor-enter(r4)
            if (r5 == 0) goto L_0x000e
            java.util.WeakHashMap<android.os.IBinder, android.app.ResourcesManager$ActivityResources> r0 = r4.mActivityResourceReferences     // Catch:{ all -> 0x000c }
            java.lang.Object r0 = r0.get(r5)     // Catch:{ all -> 0x000c }
            android.app.ResourcesManager$ActivityResources r0 = (android.app.ResourcesManager.ActivityResources) r0     // Catch:{ all -> 0x000c }
            goto L_0x000f
        L_0x000c:
            r0 = move-exception
            goto L_0x0034
        L_0x000e:
            r0 = 0
        L_0x000f:
            r1 = 0
            r2 = 1
            if (r0 != 0) goto L_0x0019
            if (r6 != 0) goto L_0x0017
            r1 = r2
        L_0x0017:
            monitor-exit(r4)     // Catch:{ all -> 0x000c }
            return r1
        L_0x0019:
            android.content.res.Configuration r3 = r0.overrideConfig     // Catch:{ all -> 0x000c }
            boolean r3 = java.util.Objects.equals(r3, r6)     // Catch:{ all -> 0x000c }
            if (r3 != 0) goto L_0x0031
            if (r6 == 0) goto L_0x0030
            android.content.res.Configuration r3 = r0.overrideConfig     // Catch:{ all -> 0x000c }
            if (r3 == 0) goto L_0x0030
            android.content.res.Configuration r3 = r0.overrideConfig     // Catch:{ all -> 0x000c }
            int r3 = r6.diffPublicOnly(r3)     // Catch:{ all -> 0x000c }
            if (r3 != 0) goto L_0x0030
            goto L_0x0031
        L_0x0030:
            goto L_0x0032
        L_0x0031:
            r1 = r2
        L_0x0032:
            monitor-exit(r4)     // Catch:{ all -> 0x000c }
            return r1
        L_0x0034:
            monitor-exit(r4)     // Catch:{ all -> 0x000c }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.app.ResourcesManager.isSameResourcesOverrideConfig(android.os.IBinder, android.content.res.Configuration):boolean");
    }

    private ActivityResources getOrCreateActivityResourcesStructLocked(IBinder activityToken) {
        ActivityResources activityResources = this.mActivityResourceReferences.get(activityToken);
        if (activityResources != null) {
            return activityResources;
        }
        ActivityResources activityResources2 = new ActivityResources();
        this.mActivityResourceReferences.put(activityToken, activityResources2);
        return activityResources2;
    }

    private Resources getOrCreateResourcesForActivityLocked(IBinder activityToken, ClassLoader classLoader, ResourcesImpl impl, CompatibilityInfo compatInfo) {
        Resources resources;
        ActivityResources activityResources = getOrCreateActivityResourcesStructLocked(activityToken);
        int refCount = activityResources.activityResources.size();
        for (int i = 0; i < refCount; i++) {
            Resources resources2 = (Resources) activityResources.activityResources.get(i).get();
            if (resources2 != null && Objects.equals(resources2.getClassLoader(), classLoader) && resources2.getImpl() == impl) {
                return resources2;
            }
        }
        if (compatInfo.needsCompatResources() != 0) {
            resources = new CompatResources(classLoader);
        } else {
            resources = new Resources(classLoader);
        }
        resources.setImpl(impl);
        activityResources.activityResources.add(new WeakReference(resources));
        return resources;
    }

    private Resources getOrCreateResourcesLocked(ClassLoader classLoader, ResourcesImpl impl, CompatibilityInfo compatInfo) {
        Resources resources;
        int refCount = this.mResourceReferences.size();
        for (int i = 0; i < refCount; i++) {
            Resources resources2 = (Resources) this.mResourceReferences.get(i).get();
            if (resources2 != null && Objects.equals(resources2.getClassLoader(), classLoader) && resources2.getImpl() == impl) {
                return resources2;
            }
        }
        if (compatInfo.needsCompatResources() != 0) {
            resources = new CompatResources(classLoader);
        } else {
            resources = new Resources(classLoader);
        }
        resources.setImpl(impl);
        this.mResourceReferences.add(new WeakReference(resources));
        return resources;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:28:0x004c, code lost:
        r0 = th;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.content.res.Resources createBaseActivityResources(android.os.IBinder r15, java.lang.String r16, java.lang.String[] r17, java.lang.String[] r18, java.lang.String[] r19, int r20, android.content.res.Configuration r21, android.content.res.CompatibilityInfo r22, java.lang.ClassLoader r23) {
        /*
            r14 = this;
            r1 = r14
            r2 = r15
            r3 = r21
            r4 = 8192(0x2000, double:4.0474E-320)
            java.lang.String r0 = "ResourcesManager#createBaseActivityResources"
            android.os.Trace.traceBegin(r4, r0)     // Catch:{ all -> 0x0052 }
            android.content.res.ResourcesKey r0 = new android.content.res.ResourcesKey     // Catch:{ all -> 0x0052 }
            if (r3 == 0) goto L_0x0015
            android.content.res.Configuration r6 = new android.content.res.Configuration     // Catch:{ all -> 0x0052 }
            r6.<init>((android.content.res.Configuration) r3)     // Catch:{ all -> 0x0052 }
            goto L_0x0016
        L_0x0015:
            r6 = 0
        L_0x0016:
            r12 = r6
            r6 = r0
            r7 = r16
            r8 = r17
            r9 = r18
            r10 = r19
            r11 = r20
            r13 = r22
            r6.<init>(r7, r8, r9, r10, r11, r12, r13)     // Catch:{ all -> 0x0052 }
            r6 = r0
            if (r23 == 0) goto L_0x002d
            r0 = r23
            goto L_0x0031
        L_0x002d:
            java.lang.ClassLoader r0 = java.lang.ClassLoader.getSystemClassLoader()     // Catch:{ all -> 0x0052 }
        L_0x0031:
            r7 = r0
            monitor-enter(r14)     // Catch:{ all -> 0x004e }
            r14.getOrCreateActivityResourcesStructLocked(r15)     // Catch:{ all -> 0x0045 }
            monitor-exit(r14)     // Catch:{ all -> 0x0045 }
            r0 = 0
            r8 = r20
            r14.updateResourcesForActivity(r15, r3, r8, r0)     // Catch:{ all -> 0x004a }
            android.content.res.Resources r0 = r14.getOrCreateResources(r15, r6, r7)     // Catch:{ all -> 0x004a }
            android.os.Trace.traceEnd(r4)
            return r0
        L_0x0045:
            r0 = move-exception
            r8 = r20
        L_0x0048:
            monitor-exit(r14)     // Catch:{ all -> 0x004c }
            throw r0     // Catch:{ all -> 0x004a }
        L_0x004a:
            r0 = move-exception
            goto L_0x0057
        L_0x004c:
            r0 = move-exception
            goto L_0x0048
        L_0x004e:
            r0 = move-exception
            r8 = r20
            goto L_0x0057
        L_0x0052:
            r0 = move-exception
            r8 = r20
            r7 = r23
        L_0x0057:
            android.os.Trace.traceEnd(r4)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.app.ResourcesManager.createBaseActivityResources(android.os.IBinder, java.lang.String, java.lang.String[], java.lang.String[], java.lang.String[], int, android.content.res.Configuration, android.content.res.CompatibilityInfo, java.lang.ClassLoader):android.content.res.Resources");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0079, code lost:
        return r1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private android.content.res.Resources getOrCreateResources(android.os.IBinder r4, android.content.res.ResourcesKey r5, java.lang.ClassLoader r6) {
        /*
            r3 = this;
            monitor-enter(r3)
            if (r4 == 0) goto L_0x0041
            android.app.ResourcesManager$ActivityResources r0 = r3.getOrCreateActivityResourcesStructLocked(r4)     // Catch:{ all -> 0x003f }
            java.util.ArrayList<java.lang.ref.WeakReference<android.content.res.Resources>> r1 = r0.activityResources     // Catch:{ all -> 0x003f }
            java.util.function.Predicate<java.lang.ref.WeakReference<android.content.res.Resources>> r2 = sEmptyReferencePredicate     // Catch:{ all -> 0x003f }
            com.android.internal.util.ArrayUtils.unstableRemoveIf(r1, r2)     // Catch:{ all -> 0x003f }
            boolean r1 = r5.hasOverrideConfiguration()     // Catch:{ all -> 0x003f }
            if (r1 == 0) goto L_0x0030
            android.content.res.Configuration r1 = r0.overrideConfig     // Catch:{ all -> 0x003f }
            android.content.res.Configuration r2 = android.content.res.Configuration.EMPTY     // Catch:{ all -> 0x003f }
            boolean r1 = r1.equals((android.content.res.Configuration) r2)     // Catch:{ all -> 0x003f }
            if (r1 != 0) goto L_0x0030
            android.content.res.Configuration r1 = new android.content.res.Configuration     // Catch:{ all -> 0x003f }
            android.content.res.Configuration r2 = r0.overrideConfig     // Catch:{ all -> 0x003f }
            r1.<init>((android.content.res.Configuration) r2)     // Catch:{ all -> 0x003f }
            android.content.res.Configuration r2 = r5.mOverrideConfiguration     // Catch:{ all -> 0x003f }
            r1.updateFrom(r2)     // Catch:{ all -> 0x003f }
            android.content.res.Configuration r2 = r5.mOverrideConfiguration     // Catch:{ all -> 0x003f }
            r2.setTo(r1)     // Catch:{ all -> 0x003f }
        L_0x0030:
            android.content.res.ResourcesImpl r1 = r3.findResourcesImplForKeyLocked(r5)     // Catch:{ all -> 0x003f }
            if (r1 == 0) goto L_0x003e
            android.content.res.CompatibilityInfo r2 = r5.mCompatInfo     // Catch:{ all -> 0x003f }
            android.content.res.Resources r2 = r3.getOrCreateResourcesForActivityLocked(r4, r6, r1, r2)     // Catch:{ all -> 0x003f }
            monitor-exit(r3)     // Catch:{ all -> 0x003f }
            return r2
        L_0x003e:
            goto L_0x0056
        L_0x003f:
            r0 = move-exception
            goto L_0x007a
        L_0x0041:
            java.util.ArrayList<java.lang.ref.WeakReference<android.content.res.Resources>> r0 = r3.mResourceReferences     // Catch:{ all -> 0x003f }
            java.util.function.Predicate<java.lang.ref.WeakReference<android.content.res.Resources>> r1 = sEmptyReferencePredicate     // Catch:{ all -> 0x003f }
            com.android.internal.util.ArrayUtils.unstableRemoveIf(r0, r1)     // Catch:{ all -> 0x003f }
            android.content.res.ResourcesImpl r0 = r3.findResourcesImplForKeyLocked(r5)     // Catch:{ all -> 0x003f }
            if (r0 == 0) goto L_0x0056
            android.content.res.CompatibilityInfo r1 = r5.mCompatInfo     // Catch:{ all -> 0x003f }
            android.content.res.Resources r1 = r3.getOrCreateResourcesLocked(r6, r0, r1)     // Catch:{ all -> 0x003f }
            monitor-exit(r3)     // Catch:{ all -> 0x003f }
            return r1
        L_0x0056:
            android.content.res.ResourcesImpl r0 = r3.createResourcesImpl(r5)     // Catch:{ all -> 0x003f }
            if (r0 != 0) goto L_0x005f
            r1 = 0
            monitor-exit(r3)     // Catch:{ all -> 0x003f }
            return r1
        L_0x005f:
            android.util.ArrayMap<android.content.res.ResourcesKey, java.lang.ref.WeakReference<android.content.res.ResourcesImpl>> r1 = r3.mResourceImpls     // Catch:{ all -> 0x003f }
            java.lang.ref.WeakReference r2 = new java.lang.ref.WeakReference     // Catch:{ all -> 0x003f }
            r2.<init>(r0)     // Catch:{ all -> 0x003f }
            r1.put(r5, r2)     // Catch:{ all -> 0x003f }
            if (r4 == 0) goto L_0x0072
            android.content.res.CompatibilityInfo r1 = r5.mCompatInfo     // Catch:{ all -> 0x003f }
            android.content.res.Resources r1 = r3.getOrCreateResourcesForActivityLocked(r4, r6, r0, r1)     // Catch:{ all -> 0x003f }
            goto L_0x0078
        L_0x0072:
            android.content.res.CompatibilityInfo r1 = r5.mCompatInfo     // Catch:{ all -> 0x003f }
            android.content.res.Resources r1 = r3.getOrCreateResourcesLocked(r6, r0, r1)     // Catch:{ all -> 0x003f }
        L_0x0078:
            monitor-exit(r3)     // Catch:{ all -> 0x003f }
            return r1
        L_0x007a:
            monitor-exit(r3)     // Catch:{ all -> 0x003f }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.app.ResourcesManager.getOrCreateResources(android.os.IBinder, android.content.res.ResourcesKey, java.lang.ClassLoader):android.content.res.Resources");
    }

    public Resources getResources(IBinder activityToken, String resDir, String[] splitResDirs, String[] overlayDirs, String[] libDirs, int displayId, Configuration overrideConfig, CompatibilityInfo compatInfo, ClassLoader classLoader) {
        Configuration configuration = overrideConfig;
        try {
            Trace.traceBegin(8192, "ResourcesManager#getResources");
            IBinder iBinder = activityToken;
            try {
                Resources orCreateResources = getOrCreateResources(activityToken, new ResourcesKey(resDir, splitResDirs, overlayDirs, libDirs, displayId, configuration != null ? new Configuration(configuration) : null, compatInfo), classLoader != null ? classLoader : ClassLoader.getSystemClassLoader());
                Trace.traceEnd(8192);
                return orCreateResources;
            } catch (Throwable th) {
                th = th;
                Trace.traceEnd(8192);
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            IBinder iBinder2 = activityToken;
            ClassLoader classLoader2 = classLoader;
            Trace.traceEnd(8192);
            throw th;
        }
    }

    public void updateResourcesForActivity(IBinder activityToken, Configuration overrideConfig, int displayId, boolean movedToDifferentDisplay) {
        ActivityResources activityResources;
        Configuration configuration = overrideConfig;
        try {
            Trace.traceBegin(8192, "ResourcesManager#updateResourcesForActivity");
            synchronized (this) {
                ActivityResources activityResources2 = getOrCreateActivityResourcesStructLocked(activityToken);
                if (!Objects.equals(activityResources2.overrideConfig, configuration) || movedToDifferentDisplay) {
                    Configuration oldConfig = new Configuration(activityResources2.overrideConfig);
                    if (configuration != null) {
                        activityResources2.overrideConfig.setTo(configuration);
                    } else {
                        activityResources2.overrideConfig.unset();
                    }
                    boolean activityHasOverrideConfig = !activityResources2.overrideConfig.equals(Configuration.EMPTY);
                    int refCount = activityResources2.activityResources.size();
                    int i = 0;
                    while (i < refCount) {
                        Resources resources = (Resources) activityResources2.activityResources.get(i).get();
                        if (resources != null) {
                            ResourcesKey oldKey = findKeyForResourceImplLocked(resources.getImpl());
                            if (oldKey == null) {
                                Slog.e(TAG, "can't find ResourcesKey for resources impl=" + resources.getImpl());
                            } else {
                                Configuration rebasedOverrideConfig = new Configuration();
                                if (configuration != null) {
                                    rebasedOverrideConfig.setTo(configuration);
                                }
                                if (activityHasOverrideConfig && oldKey.hasOverrideConfiguration()) {
                                    rebasedOverrideConfig.updateFrom(Configuration.generateDelta(oldConfig, oldKey.mOverrideConfiguration));
                                }
                                activityResources = activityResources2;
                                ResourcesKey newKey = new ResourcesKey(oldKey.mResDir, oldKey.mSplitResDirs, oldKey.mOverlayDirs, oldKey.mLibDirs, displayId, rebasedOverrideConfig, oldKey.mCompatInfo);
                                ResourcesImpl resourcesImpl = findResourcesImplForKeyLocked(newKey);
                                if (resourcesImpl == null && (resourcesImpl = createResourcesImpl(newKey)) != null) {
                                    this.mResourceImpls.put(newKey, new WeakReference(resourcesImpl));
                                }
                                if (!(resourcesImpl == null || resourcesImpl == resources.getImpl())) {
                                    resources.setImpl(resourcesImpl);
                                }
                                i++;
                                activityResources2 = activityResources;
                            }
                        }
                        activityResources = activityResources2;
                        i++;
                        activityResources2 = activityResources;
                    }
                    Trace.traceEnd(8192);
                    return;
                }
                Trace.traceEnd(8192);
            }
        } catch (Throwable th) {
            Trace.traceEnd(8192);
            throw th;
        }
    }

    public final boolean applyConfigurationToResourcesLocked(Configuration config, CompatibilityInfo compat) {
        DisplayAdjustments daj;
        Configuration configuration = config;
        CompatibilityInfo compatibilityInfo = compat;
        try {
            Trace.traceBegin(8192, "ResourcesManager#applyConfigurationToResourcesLocked");
            boolean z = false;
            if (this.mResConfiguration.isOtherSeqNewer(configuration) || compatibilityInfo != null) {
                int changes = this.mResConfiguration.updateFrom(configuration);
                this.mAdjustedDisplays.clear();
                DisplayMetrics defaultDisplayMetrics = getDisplayMetrics();
                if (compatibilityInfo != null && (this.mResCompatibilityInfo == null || !this.mResCompatibilityInfo.equals(compatibilityInfo))) {
                    this.mResCompatibilityInfo = compatibilityInfo;
                    changes |= 3328;
                }
                Resources.updateSystemConfiguration(configuration, defaultDisplayMetrics, compatibilityInfo);
                ApplicationPackageManager.configurationChanged();
                Configuration tmpConfig = null;
                boolean z2 = true;
                int i = this.mResourceImpls.size() - 1;
                while (i >= 0) {
                    ResourcesKey key = this.mResourceImpls.keyAt(i);
                    WeakReference<ResourcesImpl> weakImplRef = this.mResourceImpls.valueAt(i);
                    ResourcesImpl r = weakImplRef != null ? (ResourcesImpl) weakImplRef.get() : null;
                    if (r != null) {
                        int displayId = key.mDisplayId;
                        boolean isDefaultDisplay = displayId == 0 ? z2 : z;
                        DisplayMetrics dm = defaultDisplayMetrics;
                        boolean hasOverrideConfiguration = key.hasOverrideConfiguration();
                        if (!isDefaultDisplay) {
                        } else if (hasOverrideConfiguration) {
                            DisplayMetrics displayMetrics = dm;
                        } else {
                            r.updateConfiguration(configuration, dm, compatibilityInfo);
                        }
                        if (tmpConfig == null) {
                            tmpConfig = new Configuration();
                        }
                        tmpConfig.setTo(configuration);
                        DisplayAdjustments daj2 = r.getDisplayAdjustments();
                        if (compatibilityInfo != null) {
                            daj = new DisplayAdjustments(daj2);
                            daj.setCompatibilityInfo(compatibilityInfo);
                        } else {
                            daj = daj2;
                        }
                        DisplayMetrics dm2 = getDisplayMetrics(displayId, daj);
                        if (!isDefaultDisplay) {
                            applyNonDefaultDisplayMetricsToConfiguration(dm2, tmpConfig);
                        }
                        if (hasOverrideConfiguration) {
                            tmpConfig.updateFrom(key.mOverrideConfiguration);
                        }
                        r.updateConfiguration(tmpConfig, dm2, compatibilityInfo);
                    } else {
                        this.mResourceImpls.removeAt(i);
                    }
                    i--;
                    z = false;
                    z2 = true;
                }
                return changes != 0;
            }
            Trace.traceEnd(8192);
            return false;
        } finally {
            Trace.traceEnd(8192);
        }
    }

    @UnsupportedAppUsage
    public void appendLibAssetForMainAssetPath(String assetPath, String libAsset) {
        appendLibAssetsForMainAssetPath(assetPath, new String[]{libAsset});
    }

    public void appendLibAssetsForMainAssetPath(String assetPath, String[] libAssets) {
        String[] strArr = libAssets;
        synchronized (this) {
            try {
                ArrayMap<ResourcesImpl, ResourcesKey> updatedResourceKeys = new ArrayMap<>();
                int implCount = this.mResourceImpls.size();
                int i = 0;
                while (i < implCount) {
                    ResourcesKey key = this.mResourceImpls.keyAt(i);
                    WeakReference<ResourcesImpl> weakImplRef = this.mResourceImpls.valueAt(i);
                    ResourcesImpl impl = weakImplRef != null ? (ResourcesImpl) weakImplRef.get() : null;
                    if (impl == null) {
                        String str = assetPath;
                    } else if (Objects.equals(key.mResDir, assetPath)) {
                        String[] newLibAssets = key.mLibDirs;
                        String[] newLibAssets2 = newLibAssets;
                        for (String libAsset : strArr) {
                            newLibAssets2 = (String[]) ArrayUtils.appendElement(String.class, newLibAssets2, libAsset);
                        }
                        if (newLibAssets2 != key.mLibDirs) {
                            updatedResourceKeys.put(impl, new ResourcesKey(key.mResDir, key.mSplitResDirs, key.mOverlayDirs, newLibAssets2, key.mDisplayId, key.mOverrideConfiguration, key.mCompatInfo));
                        }
                    }
                    i++;
                    strArr = libAssets;
                }
                String str2 = assetPath;
                redirectResourcesToNewImplLocked(updatedResourceKeys);
            } catch (Throwable th) {
                th = th;
                throw th;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public final void applyNewResourceDirsLocked(ApplicationInfo appInfo, String[] oldPaths) {
        String[] newSplitDirs;
        String baseCodePath;
        int i;
        int implCount;
        ApplicationInfo applicationInfo = appInfo;
        try {
            Trace.traceBegin(8192, "ResourcesManager#applyNewResourceDirsLocked");
            String baseCodePath2 = appInfo.getBaseCodePath();
            if (applicationInfo.uid == Process.myUid()) {
                newSplitDirs = applicationInfo.splitSourceDirs;
            } else {
                newSplitDirs = applicationInfo.splitPublicSourceDirs;
            }
            String[] copiedSplitDirs = (String[]) ArrayUtils.cloneOrNull((T[]) newSplitDirs);
            String[] copiedResourceDirs = (String[]) ArrayUtils.cloneOrNull((T[]) applicationInfo.resourceDirs);
            ArrayMap arrayMap = new ArrayMap();
            int implCount2 = this.mResourceImpls.size();
            int i2 = 0;
            while (true) {
                int i3 = i2;
                if (i3 < implCount2) {
                    ResourcesKey key = this.mResourceImpls.keyAt(i3);
                    WeakReference<ResourcesImpl> weakImplRef = this.mResourceImpls.valueAt(i3);
                    ResourcesImpl impl = weakImplRef != null ? (ResourcesImpl) weakImplRef.get() : null;
                    if (impl != null) {
                        if (key.mResDir != null) {
                            if (!key.mResDir.equals(baseCodePath2)) {
                                if (ArrayUtils.contains((T[]) oldPaths, key.mResDir)) {
                                    String[] strArr = key.mLibDirs;
                                    int i4 = key.mDisplayId;
                                    Configuration configuration = key.mOverrideConfiguration;
                                    int implCount3 = implCount2;
                                    CompatibilityInfo compatibilityInfo = key.mCompatInfo;
                                    ResourcesKey resourcesKey = r5;
                                    String str = baseCodePath2;
                                    baseCodePath = baseCodePath2;
                                    ResourcesImpl impl2 = impl;
                                    String[] strArr2 = strArr;
                                    ResourcesKey resourcesKey2 = key;
                                    int i5 = i4;
                                    i = i3;
                                    Configuration configuration2 = configuration;
                                    implCount = implCount3;
                                    ResourcesKey resourcesKey3 = new ResourcesKey(str, copiedSplitDirs, copiedResourceDirs, strArr2, i5, configuration2, compatibilityInfo);
                                    arrayMap.put(impl2, resourcesKey);
                                    i2 = i + 1;
                                    implCount2 = implCount;
                                    baseCodePath2 = baseCodePath;
                                    ApplicationInfo applicationInfo2 = appInfo;
                                }
                            }
                        }
                        String[] strArr3 = oldPaths;
                        String[] strArr4 = key.mLibDirs;
                        int i42 = key.mDisplayId;
                        Configuration configuration3 = key.mOverrideConfiguration;
                        int implCount32 = implCount2;
                        CompatibilityInfo compatibilityInfo2 = key.mCompatInfo;
                        ResourcesKey resourcesKey4 = resourcesKey3;
                        String str2 = baseCodePath2;
                        baseCodePath = baseCodePath2;
                        ResourcesImpl impl22 = impl;
                        String[] strArr22 = strArr4;
                        ResourcesKey resourcesKey22 = key;
                        int i52 = i42;
                        i = i3;
                        Configuration configuration22 = configuration3;
                        implCount = implCount32;
                        ResourcesKey resourcesKey32 = new ResourcesKey(str2, copiedSplitDirs, copiedResourceDirs, strArr22, i52, configuration22, compatibilityInfo2);
                        arrayMap.put(impl22, resourcesKey4);
                        i2 = i + 1;
                        implCount2 = implCount;
                        baseCodePath2 = baseCodePath;
                        ApplicationInfo applicationInfo22 = appInfo;
                    }
                    baseCodePath = baseCodePath2;
                    i = i3;
                    implCount = implCount2;
                    i2 = i + 1;
                    implCount2 = implCount;
                    baseCodePath2 = baseCodePath;
                    ApplicationInfo applicationInfo222 = appInfo;
                } else {
                    int i6 = implCount2;
                    redirectResourcesToNewImplLocked(arrayMap);
                    Trace.traceEnd(8192);
                    return;
                }
            }
        } catch (Throwable th) {
            th = th;
            Trace.traceEnd(8192);
            throw th;
        }
    }

    private void redirectResourcesToNewImplLocked(ArrayMap<ResourcesImpl, ResourcesKey> updatedResourceKeys) {
        ResourcesKey key;
        ResourcesKey key2;
        if (!updatedResourceKeys.isEmpty()) {
            int resourcesCount = this.mResourceReferences.size();
            int i = 0;
            while (true) {
                Resources r = null;
                if (i < resourcesCount) {
                    WeakReference<Resources> ref = this.mResourceReferences.get(i);
                    if (ref != null) {
                        r = (Resources) ref.get();
                    }
                    if (!(r == null || (key2 = updatedResourceKeys.get(r.getImpl())) == null)) {
                        ResourcesImpl impl = findOrCreateResourcesImplForKeyLocked(key2);
                        if (impl != null) {
                            r.setImpl(impl);
                        } else {
                            throw new Resources.NotFoundException("failed to redirect ResourcesImpl");
                        }
                    }
                    i++;
                } else {
                    for (ActivityResources activityResources : this.mActivityResourceReferences.values()) {
                        int resCount = activityResources.activityResources.size();
                        int i2 = 0;
                        while (true) {
                            if (i2 < resCount) {
                                WeakReference<Resources> ref2 = activityResources.activityResources.get(i2);
                                Resources r2 = ref2 != null ? (Resources) ref2.get() : null;
                                if (!(r2 == null || (key = updatedResourceKeys.get(r2.getImpl())) == null)) {
                                    ResourcesImpl impl2 = findOrCreateResourcesImplForKeyLocked(key);
                                    if (impl2 != null) {
                                        r2.setImpl(impl2);
                                    } else {
                                        throw new Resources.NotFoundException("failed to redirect ResourcesImpl");
                                    }
                                }
                                i2++;
                            }
                        }
                    }
                    return;
                }
            }
        }
    }
}
