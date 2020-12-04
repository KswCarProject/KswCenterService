package android.app;

import android.annotation.UnsupportedAppUsage;
import android.app.IServiceConnection;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.IIntentReceiver;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageParser;
import android.content.pm.SharedLibraryInfo;
import android.content.pm.dex.ArtManager;
import android.content.pm.split.SplitDependencyLoader;
import android.content.res.AssetManager;
import android.content.res.CompatibilityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.wifi.WifiEnterpriseConfig;
import android.os.Bundle;
import android.os.FileUtils;
import android.os.Handler;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.os.StrictMode;
import android.os.SystemProperties;
import android.os.Trace;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Slog;
import android.util.SparseArray;
import android.view.DisplayAdjustments;
import com.android.internal.util.ArrayUtils;
import dalvik.system.BaseDexClassLoader;
import dalvik.system.VMRuntime;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executor;

public final class LoadedApk {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static final boolean DEBUG = false;
    private static final String PROPERTY_NAME_APPEND_NATIVE = "pi.append_native_lib_paths";
    static final String TAG = "LoadedApk";
    @UnsupportedAppUsage
    private final ActivityThread mActivityThread;
    private AppComponentFactory mAppComponentFactory;
    @UnsupportedAppUsage
    private String mAppDir;
    @UnsupportedAppUsage
    private Application mApplication;
    @UnsupportedAppUsage
    private ApplicationInfo mApplicationInfo;
    @UnsupportedAppUsage
    private final ClassLoader mBaseClassLoader;
    /* access modifiers changed from: private */
    @UnsupportedAppUsage
    public ClassLoader mClassLoader;
    private File mCredentialProtectedDataDirFile;
    @UnsupportedAppUsage
    private String mDataDir;
    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
    private File mDataDirFile;
    private ClassLoader mDefaultClassLoader;
    private File mDeviceProtectedDataDirFile;
    @UnsupportedAppUsage
    private final DisplayAdjustments mDisplayAdjustments = new DisplayAdjustments();
    private final boolean mIncludeCode;
    @UnsupportedAppUsage
    private String mLibDir;
    private String[] mOverlayDirs;
    @UnsupportedAppUsage
    final String mPackageName;
    @UnsupportedAppUsage
    private final ArrayMap<Context, ArrayMap<BroadcastReceiver, ReceiverDispatcher>> mReceivers = new ArrayMap<>();
    private final boolean mRegisterPackage;
    @UnsupportedAppUsage
    private String mResDir;
    @UnsupportedAppUsage
    Resources mResources;
    private final boolean mSecurityViolation;
    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
    private final ArrayMap<Context, ArrayMap<ServiceConnection, ServiceDispatcher>> mServices = new ArrayMap<>();
    /* access modifiers changed from: private */
    public String[] mSplitAppDirs;
    /* access modifiers changed from: private */
    public String[] mSplitClassLoaderNames;
    private SplitDependencyLoaderImpl mSplitLoader;
    /* access modifiers changed from: private */
    public String[] mSplitNames;
    /* access modifiers changed from: private */
    @UnsupportedAppUsage
    public String[] mSplitResDirs;
    private final ArrayMap<Context, ArrayMap<ServiceConnection, ServiceDispatcher>> mUnboundServices = new ArrayMap<>();
    private final ArrayMap<Context, ArrayMap<BroadcastReceiver, ReceiverDispatcher>> mUnregisteredReceivers = new ArrayMap<>();

    /* access modifiers changed from: package-private */
    public Application getApplication() {
        return this.mApplication;
    }

    public LoadedApk(ActivityThread activityThread, ApplicationInfo aInfo, CompatibilityInfo compatInfo, ClassLoader baseLoader, boolean securityViolation, boolean includeCode, boolean registerPackage) {
        this.mActivityThread = activityThread;
        setApplicationInfo(aInfo);
        this.mPackageName = aInfo.packageName;
        this.mBaseClassLoader = baseLoader;
        this.mSecurityViolation = securityViolation;
        this.mIncludeCode = includeCode;
        this.mRegisterPackage = registerPackage;
        this.mDisplayAdjustments.setCompatibilityInfo(compatInfo);
        this.mAppComponentFactory = createAppFactory(this.mApplicationInfo, this.mBaseClassLoader);
    }

    private static ApplicationInfo adjustNativeLibraryPaths(ApplicationInfo info) {
        if (!(info.primaryCpuAbi == null || info.secondaryCpuAbi == null)) {
            String runtimeIsa = VMRuntime.getRuntime().vmInstructionSet();
            String secondaryIsa = VMRuntime.getInstructionSet(info.secondaryCpuAbi);
            String secondaryDexCodeIsa = SystemProperties.get("ro.dalvik.vm.isa." + secondaryIsa);
            if (runtimeIsa.equals(secondaryDexCodeIsa.isEmpty() ? secondaryIsa : secondaryDexCodeIsa)) {
                ApplicationInfo modified = new ApplicationInfo(info);
                modified.nativeLibraryDir = modified.secondaryNativeLibraryDir;
                modified.primaryCpuAbi = modified.secondaryCpuAbi;
                return modified;
            }
        }
        return info;
    }

    LoadedApk(ActivityThread activityThread) {
        this.mActivityThread = activityThread;
        this.mApplicationInfo = new ApplicationInfo();
        this.mApplicationInfo.packageName = "android";
        this.mPackageName = "android";
        this.mAppDir = null;
        this.mResDir = null;
        this.mSplitAppDirs = null;
        this.mSplitResDirs = null;
        this.mSplitClassLoaderNames = null;
        this.mOverlayDirs = null;
        this.mDataDir = null;
        this.mDataDirFile = null;
        this.mDeviceProtectedDataDirFile = null;
        this.mCredentialProtectedDataDirFile = null;
        this.mLibDir = null;
        this.mBaseClassLoader = null;
        this.mSecurityViolation = false;
        this.mIncludeCode = true;
        this.mRegisterPackage = false;
        this.mResources = Resources.getSystem();
        this.mDefaultClassLoader = ClassLoader.getSystemClassLoader();
        this.mAppComponentFactory = createAppFactory(this.mApplicationInfo, this.mDefaultClassLoader);
        this.mClassLoader = this.mAppComponentFactory.instantiateClassLoader(this.mDefaultClassLoader, new ApplicationInfo(this.mApplicationInfo));
    }

    /* access modifiers changed from: package-private */
    public void installSystemApplicationInfo(ApplicationInfo info, ClassLoader classLoader) {
        this.mApplicationInfo = info;
        this.mDefaultClassLoader = classLoader;
        this.mAppComponentFactory = createAppFactory(info, this.mDefaultClassLoader);
        this.mClassLoader = this.mAppComponentFactory.instantiateClassLoader(this.mDefaultClassLoader, new ApplicationInfo(this.mApplicationInfo));
    }

    private AppComponentFactory createAppFactory(ApplicationInfo appInfo, ClassLoader cl) {
        if (!(appInfo.appComponentFactory == null || cl == null)) {
            try {
                return (AppComponentFactory) cl.loadClass(appInfo.appComponentFactory).newInstance();
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                Slog.e(TAG, "Unable to instantiate appComponentFactory", e);
            }
        }
        return AppComponentFactory.DEFAULT;
    }

    public AppComponentFactory getAppFactory() {
        return this.mAppComponentFactory;
    }

    @UnsupportedAppUsage
    public String getPackageName() {
        return this.mPackageName;
    }

    @UnsupportedAppUsage
    public ApplicationInfo getApplicationInfo() {
        return this.mApplicationInfo;
    }

    public int getTargetSdkVersion() {
        return this.mApplicationInfo.targetSdkVersion;
    }

    public boolean isSecurityViolation() {
        return this.mSecurityViolation;
    }

    @UnsupportedAppUsage
    public CompatibilityInfo getCompatibilityInfo() {
        return this.mDisplayAdjustments.getCompatibilityInfo();
    }

    public void setCompatibilityInfo(CompatibilityInfo compatInfo) {
        this.mDisplayAdjustments.setCompatibilityInfo(compatInfo);
    }

    private static String[] getLibrariesFor(String packageName) {
        try {
            ApplicationInfo ai = ActivityThread.getPackageManager().getApplicationInfo(packageName, 1024, UserHandle.myUserId());
            if (ai == null) {
                return null;
            }
            return ai.sharedLibraryFiles;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void updateApplicationInfo(ApplicationInfo aInfo, List<String> oldPaths) {
        setApplicationInfo(aInfo);
        List<String> newPaths = new ArrayList<>();
        makePaths(this.mActivityThread, aInfo, newPaths);
        List<String> addedPaths = new ArrayList<>(newPaths.size());
        if (oldPaths != null) {
            for (String path : newPaths) {
                String apkName = path.substring(path.lastIndexOf(File.separator));
                boolean match = false;
                Iterator<String> it = oldPaths.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    String oldPath = it.next();
                    if (apkName.equals(oldPath.substring(oldPath.lastIndexOf(File.separator)))) {
                        match = true;
                        break;
                    }
                }
                if (!match) {
                    addedPaths.add(path);
                }
            }
        } else {
            addedPaths.addAll(newPaths);
        }
        synchronized (this) {
            createOrUpdateClassLoaderLocked(addedPaths);
            if (this.mResources != null) {
                try {
                    this.mResources = ResourcesManager.getInstance().getResources((IBinder) null, this.mResDir, getSplitPaths((String) null), this.mOverlayDirs, this.mApplicationInfo.sharedLibraryFiles, 0, (Configuration) null, getCompatibilityInfo(), getClassLoader());
                } catch (PackageManager.NameNotFoundException e) {
                    throw new AssertionError("null split not found");
                }
            }
        }
        this.mAppComponentFactory = createAppFactory(aInfo, this.mDefaultClassLoader);
    }

    private void setApplicationInfo(ApplicationInfo aInfo) {
        int myUid = Process.myUid();
        ApplicationInfo aInfo2 = adjustNativeLibraryPaths(aInfo);
        this.mApplicationInfo = aInfo2;
        this.mAppDir = aInfo2.sourceDir;
        this.mResDir = aInfo2.uid == myUid ? aInfo2.sourceDir : aInfo2.publicSourceDir;
        this.mOverlayDirs = aInfo2.resourceDirs;
        this.mDataDir = aInfo2.dataDir;
        this.mLibDir = aInfo2.nativeLibraryDir;
        this.mDataDirFile = FileUtils.newFileOrNull(aInfo2.dataDir);
        this.mDeviceProtectedDataDirFile = FileUtils.newFileOrNull(aInfo2.deviceProtectedDataDir);
        this.mCredentialProtectedDataDirFile = FileUtils.newFileOrNull(aInfo2.credentialProtectedDataDir);
        this.mSplitNames = aInfo2.splitNames;
        this.mSplitAppDirs = aInfo2.splitSourceDirs;
        this.mSplitResDirs = aInfo2.uid == myUid ? aInfo2.splitSourceDirs : aInfo2.splitPublicSourceDirs;
        this.mSplitClassLoaderNames = aInfo2.splitClassLoaderNames;
        if (aInfo2.requestsIsolatedSplitLoading() && !ArrayUtils.isEmpty((T[]) this.mSplitNames)) {
            this.mSplitLoader = new SplitDependencyLoaderImpl(aInfo2.splitDependencies);
        }
    }

    public static void makePaths(ActivityThread activityThread, ApplicationInfo aInfo, List<String> outZipPaths) {
        makePaths(activityThread, false, aInfo, outZipPaths, (List<String>) null);
    }

    private static void appendSharedLibrariesLibPathsIfNeeded(List<SharedLibraryInfo> sharedLibraries, ApplicationInfo aInfo, Set<String> outSeenPaths, List<String> outLibPaths) {
        if (sharedLibraries != null) {
            for (SharedLibraryInfo lib : sharedLibraries) {
                List<String> paths = lib.getAllCodePaths();
                outSeenPaths.addAll(paths);
                for (String path : paths) {
                    appendApkLibPathIfNeeded(path, aInfo, outLibPaths);
                }
                appendSharedLibrariesLibPathsIfNeeded(lib.getDependencies(), aInfo, outSeenPaths, outLibPaths);
            }
        }
    }

    public static void makePaths(ActivityThread activityThread, boolean isBundledApp, ApplicationInfo aInfo, List<String> outZipPaths, List<String> outLibPaths) {
        String appDir = aInfo.sourceDir;
        String libDir = aInfo.nativeLibraryDir;
        outZipPaths.clear();
        outZipPaths.add(appDir);
        if (aInfo.splitSourceDirs != null && !aInfo.requestsIsolatedSplitLoading()) {
            Collections.addAll(outZipPaths, aInfo.splitSourceDirs);
        }
        if (outLibPaths != null) {
            outLibPaths.clear();
        }
        String[] instrumentationLibs = null;
        if (activityThread != null) {
            String instrumentationPackageName = activityThread.mInstrumentationPackageName;
            String instrumentationAppDir = activityThread.mInstrumentationAppDir;
            String[] instrumentationSplitAppDirs = activityThread.mInstrumentationSplitAppDirs;
            String instrumentationLibDir = activityThread.mInstrumentationLibDir;
            String instrumentedAppDir = activityThread.mInstrumentedAppDir;
            String[] instrumentedSplitAppDirs = activityThread.mInstrumentedSplitAppDirs;
            String instrumentedLibDir = activityThread.mInstrumentedLibDir;
            if (appDir.equals(instrumentationAppDir) || appDir.equals(instrumentedAppDir)) {
                outZipPaths.clear();
                outZipPaths.add(instrumentationAppDir);
                if (!aInfo.requestsIsolatedSplitLoading()) {
                    if (instrumentationSplitAppDirs != null) {
                        Collections.addAll(outZipPaths, instrumentationSplitAppDirs);
                    }
                    if (!instrumentationAppDir.equals(instrumentedAppDir)) {
                        outZipPaths.add(instrumentedAppDir);
                        if (instrumentedSplitAppDirs != null) {
                            Collections.addAll(outZipPaths, instrumentedSplitAppDirs);
                        }
                    }
                }
                if (outLibPaths != null) {
                    outLibPaths.add(instrumentationLibDir);
                    if (!instrumentationLibDir.equals(instrumentedLibDir)) {
                        outLibPaths.add(instrumentedLibDir);
                    }
                }
                if (!instrumentedAppDir.equals(instrumentationAppDir)) {
                    instrumentationLibs = getLibrariesFor(instrumentationPackageName);
                }
            }
        }
        if (outLibPaths != null) {
            if (outLibPaths.isEmpty()) {
                outLibPaths.add(libDir);
            }
            if (aInfo.primaryCpuAbi != null) {
                if (aInfo.targetSdkVersion < 24) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("/system/fake-libs");
                    sb.append(VMRuntime.is64BitAbi(aInfo.primaryCpuAbi) ? "64" : "");
                    outLibPaths.add(sb.toString());
                }
                for (String apk : outZipPaths) {
                    outLibPaths.add(apk + "!/lib/" + aInfo.primaryCpuAbi);
                }
            }
            if (isBundledApp) {
                outLibPaths.add(System.getProperty("java.library.path"));
            }
        }
        Set<String> outSeenPaths = new LinkedHashSet<>();
        appendSharedLibrariesLibPathsIfNeeded(aInfo.sharedLibraryInfos, aInfo, outSeenPaths, outLibPaths);
        if (aInfo.sharedLibraryFiles != null) {
            int index = 0;
            for (String lib : aInfo.sharedLibraryFiles) {
                if (!outSeenPaths.contains(lib) && !outZipPaths.contains(lib)) {
                    outZipPaths.add(index, lib);
                    index++;
                    appendApkLibPathIfNeeded(lib, aInfo, outLibPaths);
                }
            }
        }
        if (instrumentationLibs != null) {
            for (String lib2 : instrumentationLibs) {
                if (!outZipPaths.contains(lib2)) {
                    outZipPaths.add(0, lib2);
                    appendApkLibPathIfNeeded(lib2, aInfo, outLibPaths);
                }
            }
        }
    }

    private static void appendApkLibPathIfNeeded(String path, ApplicationInfo applicationInfo, List<String> outLibPaths) {
        if (outLibPaths != null && applicationInfo.primaryCpuAbi != null && path.endsWith(PackageParser.APK_FILE_EXTENSION) && applicationInfo.targetSdkVersion >= 26) {
            outLibPaths.add(path + "!/lib/" + applicationInfo.primaryCpuAbi);
        }
    }

    private class SplitDependencyLoaderImpl extends SplitDependencyLoader<PackageManager.NameNotFoundException> {
        private final ClassLoader[] mCachedClassLoaders;
        private final String[][] mCachedResourcePaths;

        SplitDependencyLoaderImpl(SparseArray<int[]> dependencies) {
            super(dependencies);
            this.mCachedResourcePaths = new String[(LoadedApk.this.mSplitNames.length + 1)][];
            this.mCachedClassLoaders = new ClassLoader[(LoadedApk.this.mSplitNames.length + 1)];
        }

        /* access modifiers changed from: protected */
        public boolean isSplitCached(int splitIdx) {
            return this.mCachedClassLoaders[splitIdx] != null;
        }

        /* access modifiers changed from: protected */
        public void constructSplit(int splitIdx, int[] configSplitIndices, int parentSplitIdx) throws PackageManager.NameNotFoundException {
            ArrayList<String> splitPaths = new ArrayList<>();
            if (splitIdx == 0) {
                LoadedApk.this.createOrUpdateClassLoaderLocked((List<String>) null);
                this.mCachedClassLoaders[0] = LoadedApk.this.mClassLoader;
                int length = configSplitIndices.length;
                for (int i = 0; i < length; i++) {
                    splitPaths.add(LoadedApk.this.mSplitResDirs[configSplitIndices[i] - 1]);
                }
                this.mCachedResourcePaths[0] = (String[]) splitPaths.toArray(new String[splitPaths.size()]);
                return;
            }
            this.mCachedClassLoaders[splitIdx] = ApplicationLoaders.getDefault().getClassLoader(LoadedApk.this.mSplitAppDirs[splitIdx - 1], LoadedApk.this.getTargetSdkVersion(), false, (String) null, (String) null, this.mCachedClassLoaders[parentSplitIdx], LoadedApk.this.mSplitClassLoaderNames[splitIdx - 1]);
            Collections.addAll(splitPaths, this.mCachedResourcePaths[parentSplitIdx]);
            splitPaths.add(LoadedApk.this.mSplitResDirs[splitIdx - 1]);
            int length2 = configSplitIndices.length;
            for (int i2 = 0; i2 < length2; i2++) {
                splitPaths.add(LoadedApk.this.mSplitResDirs[configSplitIndices[i2] - 1]);
            }
            this.mCachedResourcePaths[splitIdx] = (String[]) splitPaths.toArray(new String[splitPaths.size()]);
        }

        private int ensureSplitLoaded(String splitName) throws PackageManager.NameNotFoundException {
            int idx = 0;
            if (splitName != null) {
                int idx2 = Arrays.binarySearch(LoadedApk.this.mSplitNames, splitName);
                if (idx2 >= 0) {
                    idx = idx2 + 1;
                } else {
                    throw new PackageManager.NameNotFoundException("Split name '" + splitName + "' is not installed");
                }
            }
            loadDependenciesForSplit(idx);
            return idx;
        }

        /* access modifiers changed from: package-private */
        public ClassLoader getClassLoaderForSplit(String splitName) throws PackageManager.NameNotFoundException {
            return this.mCachedClassLoaders[ensureSplitLoaded(splitName)];
        }

        /* access modifiers changed from: package-private */
        public String[] getSplitPathsForSplit(String splitName) throws PackageManager.NameNotFoundException {
            return this.mCachedResourcePaths[ensureSplitLoaded(splitName)];
        }
    }

    /* access modifiers changed from: package-private */
    public ClassLoader getSplitClassLoader(String splitName) throws PackageManager.NameNotFoundException {
        if (this.mSplitLoader == null) {
            return this.mClassLoader;
        }
        return this.mSplitLoader.getClassLoaderForSplit(splitName);
    }

    /* access modifiers changed from: package-private */
    public String[] getSplitPaths(String splitName) throws PackageManager.NameNotFoundException {
        if (this.mSplitLoader == null) {
            return this.mSplitResDirs;
        }
        return this.mSplitLoader.getSplitPathsForSplit(splitName);
    }

    /* access modifiers changed from: package-private */
    public ClassLoader createSharedLibraryLoader(SharedLibraryInfo sharedLibrary, boolean isBundledApp, String librarySearchPath, String libraryPermittedPath) {
        String jars;
        List<String> paths = sharedLibrary.getAllCodePaths();
        List<ClassLoader> sharedLibraries = createSharedLibrariesLoaders(sharedLibrary.getDependencies(), isBundledApp, librarySearchPath, libraryPermittedPath);
        if (paths.size() == 1) {
            jars = paths.get(0);
        } else {
            jars = TextUtils.join((CharSequence) File.pathSeparator, (Iterable) paths);
        }
        return ApplicationLoaders.getDefault().getSharedLibraryClassLoaderWithSharedLibraries(jars, this.mApplicationInfo.targetSdkVersion, isBundledApp, librarySearchPath, libraryPermittedPath, (ClassLoader) null, (String) null, sharedLibraries);
    }

    private List<ClassLoader> createSharedLibrariesLoaders(List<SharedLibraryInfo> sharedLibraries, boolean isBundledApp, String librarySearchPath, String libraryPermittedPath) {
        if (sharedLibraries == null) {
            return null;
        }
        List<ClassLoader> loaders = new ArrayList<>();
        for (SharedLibraryInfo info : sharedLibraries) {
            loaders.add(createSharedLibraryLoader(info, isBundledApp, librarySearchPath, libraryPermittedPath));
        }
        return loaders;
    }

    private StrictMode.ThreadPolicy allowThreadDiskReads() {
        if (this.mActivityThread == null) {
            return null;
        }
        return StrictMode.allowThreadDiskReads();
    }

    private void setThreadPolicy(StrictMode.ThreadPolicy policy) {
        if (this.mActivityThread != null && policy != null) {
            StrictMode.setThreadPolicy(policy);
        }
    }

    /* access modifiers changed from: private */
    public void createOrUpdateClassLoaderLocked(List<String> addedPaths) {
        String join;
        boolean needToSetupJitProfiles;
        List<String> list = addedPaths;
        if (!this.mPackageName.equals("android")) {
            if (this.mActivityThread != null && !Objects.equals(this.mPackageName, ActivityThread.currentPackageName()) && this.mIncludeCode) {
                try {
                    ActivityThread.getPackageManager().notifyPackageUse(this.mPackageName, 6);
                } catch (RemoteException re) {
                    throw re.rethrowFromSystemServer();
                }
            }
            if (this.mRegisterPackage) {
                try {
                    ActivityManager.getService().addPackageDependency(this.mPackageName);
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
            List<String> zipPaths = new ArrayList<>(10);
            List<String> libPaths = new ArrayList<>(10);
            boolean isBundledApp = this.mApplicationInfo.isSystemApp() && !this.mApplicationInfo.isUpdatedSystemApp();
            String defaultSearchPaths = System.getProperty("java.library.path");
            boolean treatVendorApkAsUnbundled = !defaultSearchPaths.contains("/vendor/lib");
            if (this.mApplicationInfo.getCodePath() != null && this.mApplicationInfo.isVendor() && treatVendorApkAsUnbundled) {
                isBundledApp = false;
            }
            boolean isBundledApp2 = isBundledApp;
            makePaths(this.mActivityThread, isBundledApp2, this.mApplicationInfo, zipPaths, libPaths);
            String libraryPermittedPath = this.mDataDir;
            if (isBundledApp2) {
                libraryPermittedPath = (libraryPermittedPath + File.pathSeparator + Paths.get(getAppDir(), new String[0]).getParent().toString()) + File.pathSeparator + defaultSearchPaths;
            }
            String libraryPermittedPath2 = libraryPermittedPath;
            String librarySearchPath = TextUtils.join((CharSequence) File.pathSeparator, (Iterable) libPaths);
            if (!this.mIncludeCode) {
                if (this.mDefaultClassLoader == null) {
                    StrictMode.ThreadPolicy oldPolicy = allowThreadDiskReads();
                    String str = librarySearchPath;
                    String str2 = libraryPermittedPath2;
                    boolean z = isBundledApp2;
                    this.mDefaultClassLoader = ApplicationLoaders.getDefault().getClassLoader("", this.mApplicationInfo.targetSdkVersion, isBundledApp2, librarySearchPath, libraryPermittedPath2, this.mBaseClassLoader, (String) null);
                    setThreadPolicy(oldPolicy);
                    this.mAppComponentFactory = AppComponentFactory.DEFAULT;
                } else {
                    String str3 = libraryPermittedPath2;
                    boolean z2 = isBundledApp2;
                }
                if (this.mClassLoader == null) {
                    this.mClassLoader = this.mAppComponentFactory.instantiateClassLoader(this.mDefaultClassLoader, new ApplicationInfo(this.mApplicationInfo));
                    return;
                }
                return;
            }
            String libraryPermittedPath3 = libraryPermittedPath2;
            boolean isBundledApp3 = isBundledApp2;
            String librarySearchPath2 = librarySearchPath;
            if (zipPaths.size() == 1) {
                join = zipPaths.get(0);
            } else {
                join = TextUtils.join((CharSequence) File.pathSeparator, (Iterable) zipPaths);
            }
            String zip = join;
            if (this.mDefaultClassLoader == null) {
                StrictMode.ThreadPolicy oldPolicy2 = allowThreadDiskReads();
                String libraryPermittedPath4 = libraryPermittedPath3;
                boolean isBundledApp4 = isBundledApp3;
                boolean z3 = isBundledApp4;
                String str4 = librarySearchPath2;
                this.mDefaultClassLoader = ApplicationLoaders.getDefault().getClassLoaderWithSharedLibraries(zip, this.mApplicationInfo.targetSdkVersion, isBundledApp4, librarySearchPath2, libraryPermittedPath4, this.mBaseClassLoader, this.mApplicationInfo.classLoaderName, createSharedLibrariesLoaders(this.mApplicationInfo.sharedLibraryInfos, isBundledApp4, librarySearchPath2, libraryPermittedPath4));
                this.mAppComponentFactory = createAppFactory(this.mApplicationInfo, this.mDefaultClassLoader);
                setThreadPolicy(oldPolicy2);
                needToSetupJitProfiles = true;
            } else {
                boolean z4 = isBundledApp3;
                String str5 = libraryPermittedPath3;
                needToSetupJitProfiles = false;
            }
            if (!libPaths.isEmpty() && SystemProperties.getBoolean(PROPERTY_NAME_APPEND_NATIVE, true)) {
                StrictMode.ThreadPolicy oldPolicy3 = allowThreadDiskReads();
                try {
                    ApplicationLoaders.getDefault().addNative(this.mDefaultClassLoader, libPaths);
                } finally {
                    setThreadPolicy(oldPolicy3);
                }
            }
            List<String> extraLibPaths = new ArrayList<>(4);
            String abiSuffix = VMRuntime.getRuntime().is64Bit() ? "64" : "";
            if (!defaultSearchPaths.contains("/apex/com.android.runtime/lib")) {
                extraLibPaths.add("/apex/com.android.runtime/lib" + abiSuffix);
            }
            if (!defaultSearchPaths.contains("/vendor/lib")) {
                extraLibPaths.add("/vendor/lib" + abiSuffix);
            }
            if (!defaultSearchPaths.contains("/odm/lib")) {
                extraLibPaths.add("/odm/lib" + abiSuffix);
            }
            if (!defaultSearchPaths.contains("/product/lib")) {
                extraLibPaths.add("/product/lib" + abiSuffix);
            }
            if (!extraLibPaths.isEmpty()) {
                StrictMode.ThreadPolicy oldPolicy4 = allowThreadDiskReads();
                try {
                    ApplicationLoaders.getDefault().addNative(this.mDefaultClassLoader, extraLibPaths);
                } finally {
                    setThreadPolicy(oldPolicy4);
                }
            }
            if (list != null && addedPaths.size() > 0) {
                ApplicationLoaders.getDefault().addPath(this.mDefaultClassLoader, TextUtils.join((CharSequence) File.pathSeparator, (Iterable) list));
                needToSetupJitProfiles = true;
            }
            if (needToSetupJitProfiles && !ActivityThread.isSystem() && this.mActivityThread != null) {
                setupJitProfileSupport();
            }
            if (this.mClassLoader == null) {
                this.mClassLoader = this.mAppComponentFactory.instantiateClassLoader(this.mDefaultClassLoader, new ApplicationInfo(this.mApplicationInfo));
            }
        } else if (this.mClassLoader == null) {
            if (this.mBaseClassLoader != null) {
                this.mDefaultClassLoader = this.mBaseClassLoader;
            } else {
                this.mDefaultClassLoader = ClassLoader.getSystemClassLoader();
            }
            this.mAppComponentFactory = createAppFactory(this.mApplicationInfo, this.mDefaultClassLoader);
            this.mClassLoader = this.mAppComponentFactory.instantiateClassLoader(this.mDefaultClassLoader, new ApplicationInfo(this.mApplicationInfo));
        }
    }

    @UnsupportedAppUsage
    public ClassLoader getClassLoader() {
        ClassLoader classLoader;
        synchronized (this) {
            if (this.mClassLoader == null) {
                createOrUpdateClassLoaderLocked((List<String>) null);
            }
            classLoader = this.mClassLoader;
        }
        return classLoader;
    }

    private void setupJitProfileSupport() {
        if (SystemProperties.getBoolean("dalvik.vm.usejitprofiles", false)) {
            BaseDexClassLoader.setReporter(DexLoadReporter.getInstance());
            if (this.mApplicationInfo.uid == Process.myUid()) {
                List<String> codePaths = new ArrayList<>();
                if ((this.mApplicationInfo.flags & 4) != 0) {
                    codePaths.add(this.mApplicationInfo.sourceDir);
                }
                if (this.mApplicationInfo.splitSourceDirs != null) {
                    Collections.addAll(codePaths, this.mApplicationInfo.splitSourceDirs);
                }
                if (!codePaths.isEmpty()) {
                    int i = codePaths.size() - 1;
                    while (i >= 0) {
                        VMRuntime.registerAppInfo(ArtManager.getCurrentProfilePath(this.mPackageName, UserHandle.myUserId(), i == 0 ? null : this.mApplicationInfo.splitNames[i - 1]), new String[]{codePaths.get(i)});
                        i--;
                    }
                    DexLoadReporter.getInstance().registerAppDataDir(this.mPackageName, this.mDataDir);
                }
            }
        }
    }

    private void initializeJavaContextClassLoader() {
        ClassLoader contextClassLoader;
        try {
            PackageInfo pi = ActivityThread.getPackageManager().getPackageInfo(this.mPackageName, 268435456, UserHandle.myUserId());
            if (pi != null) {
                boolean sharable = false;
                boolean sharedUserIdSet = pi.sharedUserId != null;
                boolean processNameNotDefault = pi.applicationInfo != null && !this.mPackageName.equals(pi.applicationInfo.processName);
                if (sharedUserIdSet || processNameNotDefault) {
                    sharable = true;
                }
                if (sharable) {
                    contextClassLoader = new WarningContextClassLoader();
                } else {
                    contextClassLoader = this.mClassLoader;
                }
                Thread.currentThread().setContextClassLoader(contextClassLoader);
                return;
            }
            throw new IllegalStateException("Unable to get package info for " + this.mPackageName + "; is package not installed?");
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private static class WarningContextClassLoader extends ClassLoader {
        private static boolean warned = false;

        private WarningContextClassLoader() {
        }

        private void warn(String methodName) {
            if (!warned) {
                warned = true;
                Thread.currentThread().setContextClassLoader(getParent());
                Slog.w(ActivityThread.TAG, "ClassLoader." + methodName + ": The class loader returned by Thread.getContextClassLoader() may fail for processes that host multiple applications. You should explicitly specify a context class loader. For example: Thread.setContextClassLoader(getClass().getClassLoader());");
            }
        }

        public URL getResource(String resName) {
            warn("getResource");
            return getParent().getResource(resName);
        }

        public Enumeration<URL> getResources(String resName) throws IOException {
            warn("getResources");
            return getParent().getResources(resName);
        }

        public InputStream getResourceAsStream(String resName) {
            warn("getResourceAsStream");
            return getParent().getResourceAsStream(resName);
        }

        public Class<?> loadClass(String className) throws ClassNotFoundException {
            warn("loadClass");
            return getParent().loadClass(className);
        }

        public void setClassAssertionStatus(String cname, boolean enable) {
            warn("setClassAssertionStatus");
            getParent().setClassAssertionStatus(cname, enable);
        }

        public void setPackageAssertionStatus(String pname, boolean enable) {
            warn("setPackageAssertionStatus");
            getParent().setPackageAssertionStatus(pname, enable);
        }

        public void setDefaultAssertionStatus(boolean enable) {
            warn("setDefaultAssertionStatus");
            getParent().setDefaultAssertionStatus(enable);
        }

        public void clearAssertionStatus() {
            warn("clearAssertionStatus");
            getParent().clearAssertionStatus();
        }
    }

    @UnsupportedAppUsage
    public String getAppDir() {
        return this.mAppDir;
    }

    public String getLibDir() {
        return this.mLibDir;
    }

    @UnsupportedAppUsage
    public String getResDir() {
        return this.mResDir;
    }

    public String[] getSplitAppDirs() {
        return this.mSplitAppDirs;
    }

    @UnsupportedAppUsage
    public String[] getSplitResDirs() {
        return this.mSplitResDirs;
    }

    @UnsupportedAppUsage
    public String[] getOverlayDirs() {
        return this.mOverlayDirs;
    }

    public String getDataDir() {
        return this.mDataDir;
    }

    @UnsupportedAppUsage
    public File getDataDirFile() {
        return this.mDataDirFile;
    }

    public File getDeviceProtectedDataDirFile() {
        return this.mDeviceProtectedDataDirFile;
    }

    public File getCredentialProtectedDataDirFile() {
        return this.mCredentialProtectedDataDirFile;
    }

    @UnsupportedAppUsage
    public AssetManager getAssets() {
        return getResources().getAssets();
    }

    @UnsupportedAppUsage
    public Resources getResources() {
        if (this.mResources == null) {
            try {
                this.mResources = ResourcesManager.getInstance().getResources((IBinder) null, this.mResDir, getSplitPaths((String) null), this.mOverlayDirs, this.mApplicationInfo.sharedLibraryFiles, 0, (Configuration) null, getCompatibilityInfo(), getClassLoader());
            } catch (PackageManager.NameNotFoundException e) {
                throw new AssertionError("null split not found");
            }
        }
        return this.mResources;
    }

    @UnsupportedAppUsage
    public Application makeApplication(boolean forceDefaultAppClass, Instrumentation instrumentation) {
        if (this.mApplication != null) {
            return this.mApplication;
        }
        Trace.traceBegin(64, "makeApplication");
        Application app = null;
        String appClass = this.mApplicationInfo.className;
        if (forceDefaultAppClass || appClass == null) {
            appClass = "android.app.Application";
        }
        try {
            ClassLoader cl = getClassLoader();
            if (!this.mPackageName.equals("android")) {
                Trace.traceBegin(64, "initializeJavaContextClassLoader");
                initializeJavaContextClassLoader();
                Trace.traceEnd(64);
            }
            ContextImpl appContext = ContextImpl.createAppContext(this.mActivityThread, this);
            app = this.mActivityThread.mInstrumentation.newApplication(cl, appClass, appContext);
            appContext.setOuterContext(app);
        } catch (Exception e) {
            if (!this.mActivityThread.mInstrumentation.onException((Object) null, e)) {
                Trace.traceEnd(64);
                throw new RuntimeException("Unable to instantiate application " + appClass + ": " + e.toString(), e);
            }
        }
        this.mActivityThread.mAllApplications.add(app);
        this.mApplication = app;
        if (instrumentation != null) {
            try {
                instrumentation.callApplicationOnCreate(app);
            } catch (Exception e2) {
                if (!instrumentation.onException(app, e2)) {
                    Trace.traceEnd(64);
                    throw new RuntimeException("Unable to create application " + app.getClass().getName() + ": " + e2.toString(), e2);
                }
            }
        }
        SparseArray<String> packageIdentifiers = getAssets().getAssignedPackageIdentifiers();
        int N = packageIdentifiers.size();
        for (int i = 0; i < N; i++) {
            int id = packageIdentifiers.keyAt(i);
            if (!(id == 1 || id == 127)) {
                rewriteRValues(getClassLoader(), packageIdentifiers.valueAt(i), id);
            }
        }
        Trace.traceEnd(64);
        return app;
    }

    @UnsupportedAppUsage
    private void rewriteRValues(ClassLoader cl, String packageName, int id) {
        try {
            Class<?> rClazz = cl.loadClass(packageName + ".R");
            try {
                Method callback = rClazz.getMethod("onResourcesLoaded", new Class[]{Integer.TYPE});
                try {
                    callback.invoke((Object) null, new Object[]{Integer.valueOf(id)});
                } catch (IllegalAccessException e) {
                    cause = e;
                    throw new RuntimeException("Failed to rewrite resource references for " + packageName, cause);
                } catch (InvocationTargetException e2) {
                    cause = e2.getCause();
                    throw new RuntimeException("Failed to rewrite resource references for " + packageName, cause);
                }
            } catch (NoSuchMethodException e3) {
            }
        } catch (ClassNotFoundException e4) {
            Log.i(TAG, "No resource references to update in package " + packageName);
        }
    }

    public void removeContextRegistrations(Context context, String who, String what) {
        int i;
        boolean reportRegistrationLeaks = StrictMode.vmRegistrationLeaksEnabled();
        synchronized (this.mReceivers) {
            ArrayMap<BroadcastReceiver, ReceiverDispatcher> rmap = this.mReceivers.remove(context);
            i = 0;
            if (rmap != null) {
                int i2 = 0;
                while (i2 < rmap.size()) {
                    ReceiverDispatcher rd = rmap.valueAt(i2);
                    IntentReceiverLeaked leak = new IntentReceiverLeaked(what + WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER + who + " has leaked IntentReceiver " + rd.getIntentReceiver() + " that was originally registered here. Are you missing a call to unregisterReceiver()?");
                    leak.setStackTrace(rd.getLocation().getStackTrace());
                    Slog.e(ActivityThread.TAG, leak.getMessage(), leak);
                    if (reportRegistrationLeaks) {
                        StrictMode.onIntentReceiverLeaked(leak);
                    }
                    try {
                        ActivityManager.getService().unregisterReceiver(rd.getIIntentReceiver());
                        i2++;
                    } catch (RemoteException e) {
                        throw e.rethrowFromSystemServer();
                    }
                }
            }
            this.mUnregisteredReceivers.remove(context);
        }
        synchronized (this.mServices) {
            ArrayMap<ServiceConnection, ServiceDispatcher> smap = this.mServices.remove(context);
            if (smap != null) {
                while (i < smap.size()) {
                    ServiceDispatcher sd = smap.valueAt(i);
                    ServiceConnectionLeaked leak2 = new ServiceConnectionLeaked(what + WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER + who + " has leaked ServiceConnection " + sd.getServiceConnection() + " that was originally bound here");
                    leak2.setStackTrace(sd.getLocation().getStackTrace());
                    Slog.e(ActivityThread.TAG, leak2.getMessage(), leak2);
                    if (reportRegistrationLeaks) {
                        StrictMode.onServiceConnectionLeaked(leak2);
                    }
                    try {
                        ActivityManager.getService().unbindService(sd.getIServiceConnection());
                        sd.doForget();
                        i++;
                    } catch (RemoteException e2) {
                        throw e2.rethrowFromSystemServer();
                    }
                }
            }
            this.mUnboundServices.remove(context);
        }
    }

    public IIntentReceiver getReceiverDispatcher(BroadcastReceiver r, Context context, Handler handler, Instrumentation instrumentation, boolean registered) {
        IIntentReceiver iIntentReceiver;
        synchronized (this.mReceivers) {
            ReceiverDispatcher rd = null;
            ArrayMap<BroadcastReceiver, ReceiverDispatcher> map = null;
            if (registered) {
                try {
                    map = this.mReceivers.get(context);
                    if (map != null) {
                        rd = map.get(r);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            if (rd == null) {
                rd = new ReceiverDispatcher(r, context, handler, instrumentation, registered);
                if (registered) {
                    if (map == null) {
                        map = new ArrayMap<>();
                        this.mReceivers.put(context, map);
                    }
                    map.put(r, rd);
                }
            } else {
                rd.validate(context, handler);
            }
            rd.mForgotten = false;
            iIntentReceiver = rd.getIIntentReceiver();
        }
        return iIntentReceiver;
    }

    public IIntentReceiver forgetReceiverDispatcher(Context context, BroadcastReceiver r) {
        ReceiverDispatcher rd;
        IIntentReceiver iIntentReceiver;
        synchronized (this.mReceivers) {
            ArrayMap<BroadcastReceiver, ReceiverDispatcher> map = this.mReceivers.get(context);
            if (map == null || (rd = map.get(r)) == null) {
                ArrayMap<BroadcastReceiver, ReceiverDispatcher> holder = this.mUnregisteredReceivers.get(context);
                if (holder != null) {
                    ReceiverDispatcher rd2 = holder.get(r);
                    if (rd2 != null) {
                        RuntimeException ex = rd2.getUnregisterLocation();
                        throw new IllegalArgumentException("Unregistering Receiver " + r + " that was already unregistered", ex);
                    }
                }
                if (context == null) {
                    throw new IllegalStateException("Unbinding Receiver " + r + " from Context that is no longer in use: " + context);
                }
                throw new IllegalArgumentException("Receiver not registered: " + r);
            }
            map.remove(r);
            if (map.size() == 0) {
                this.mReceivers.remove(context);
            }
            if (r.getDebugUnregister()) {
                ArrayMap<BroadcastReceiver, ReceiverDispatcher> holder2 = this.mUnregisteredReceivers.get(context);
                if (holder2 == null) {
                    holder2 = new ArrayMap<>();
                    this.mUnregisteredReceivers.put(context, holder2);
                }
                RuntimeException ex2 = new IllegalArgumentException("Originally unregistered here:");
                ex2.fillInStackTrace();
                rd.setUnregisterLocation(ex2);
                holder2.put(r, rd);
            }
            rd.mForgotten = true;
            iIntentReceiver = rd.getIIntentReceiver();
        }
        return iIntentReceiver;
    }

    static final class ReceiverDispatcher {
        final Handler mActivityThread;
        @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
        final Context mContext;
        boolean mForgotten;
        final IIntentReceiver.Stub mIIntentReceiver;
        final Instrumentation mInstrumentation;
        final IntentReceiverLeaked mLocation;
        @UnsupportedAppUsage
        final BroadcastReceiver mReceiver;
        final boolean mRegistered;
        RuntimeException mUnregisterLocation;

        static final class InnerReceiver extends IIntentReceiver.Stub {
            final WeakReference<ReceiverDispatcher> mDispatcher;
            final ReceiverDispatcher mStrongRef;

            InnerReceiver(ReceiverDispatcher rd, boolean strong) {
                this.mDispatcher = new WeakReference<>(rd);
                this.mStrongRef = strong ? rd : null;
            }

            public void performReceive(Intent intent, int resultCode, String data, Bundle extras, boolean ordered, boolean sticky, int sendingUser) {
                ReceiverDispatcher rd;
                Bundle bundle = extras;
                if (intent == null) {
                    Log.wtf(LoadedApk.TAG, "Null intent received");
                    rd = null;
                } else {
                    rd = (ReceiverDispatcher) this.mDispatcher.get();
                }
                ReceiverDispatcher rd2 = rd;
                if (rd2 != null) {
                    rd2.performReceive(intent, resultCode, data, extras, ordered, sticky, sendingUser);
                    return;
                }
                IActivityManager mgr = ActivityManager.getService();
                if (bundle != null) {
                    try {
                        bundle.setAllowFds(false);
                    } catch (RemoteException e) {
                        throw e.rethrowFromSystemServer();
                    }
                }
                mgr.finishReceiver(this, resultCode, data, extras, false, intent.getFlags());
            }
        }

        final class Args extends BroadcastReceiver.PendingResult {
            private Intent mCurIntent;
            private boolean mDispatched;
            private final boolean mOrdered;
            private boolean mRunCalled;
            final /* synthetic */ ReceiverDispatcher this$0;

            /* JADX WARNING: Illegal instructions before constructor call */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public Args(android.app.LoadedApk.ReceiverDispatcher r13, android.content.Intent r14, int r15, java.lang.String r16, android.os.Bundle r17, boolean r18, boolean r19, int r20) {
                /*
                    r12 = this;
                    r10 = r12
                    r11 = r13
                    r10.this$0 = r11
                    boolean r0 = r11.mRegistered
                    if (r0 == 0) goto L_0x000c
                    r0 = 1
                L_0x000a:
                    r4 = r0
                    goto L_0x000e
                L_0x000c:
                    r0 = 2
                    goto L_0x000a
                L_0x000e:
                    android.content.IIntentReceiver$Stub r0 = r11.mIIntentReceiver
                    android.os.IBinder r7 = r0.asBinder()
                    int r9 = r14.getFlags()
                    r0 = r12
                    r1 = r15
                    r2 = r16
                    r3 = r17
                    r5 = r18
                    r6 = r19
                    r8 = r20
                    r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8, r9)
                    r0 = r14
                    r10.mCurIntent = r0
                    r1 = r18
                    r10.mOrdered = r1
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: android.app.LoadedApk.ReceiverDispatcher.Args.<init>(android.app.LoadedApk$ReceiverDispatcher, android.content.Intent, int, java.lang.String, android.os.Bundle, boolean, boolean, int):void");
            }

            public final Runnable getRunnable() {
                return 
                /*  JADX ERROR: Method code generation error
                    jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x0005: RETURN  
                      (wrap: android.app.-$$Lambda$LoadedApk$ReceiverDispatcher$Args$_BumDX2UKsnxLVrE6UJsJZkotuA : 0x0002: CONSTRUCTOR  (r0v0 android.app.-$$Lambda$LoadedApk$ReceiverDispatcher$Args$_BumDX2UKsnxLVrE6UJsJZkotuA) = 
                      (r1v0 'this' android.app.LoadedApk$ReceiverDispatcher$Args A[THIS])
                     call: android.app.-$$Lambda$LoadedApk$ReceiverDispatcher$Args$_BumDX2UKsnxLVrE6UJsJZkotuA.<init>(android.app.LoadedApk$ReceiverDispatcher$Args):void type: CONSTRUCTOR)
                     in method: android.app.LoadedApk.ReceiverDispatcher.Args.getRunnable():java.lang.Runnable, dex: classes2.dex
                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:256)
                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:221)
                    	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:109)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:55)
                    	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                    	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:211)
                    	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:204)
                    	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:318)
                    	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:271)
                    	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$2(ClassGen.java:240)
                    	at java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:184)
                    	at java.util.ArrayList.forEach(ArrayList.java:1257)
                    	at java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:390)
                    	at java.util.stream.Sink$ChainedReference.end(Sink.java:258)
                    	at java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:482)
                    	at java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:471)
                    	at java.util.stream.ForEachOps$ForEachOp.evaluateSequential(ForEachOps.java:151)
                    	at java.util.stream.ForEachOps$ForEachOp$OfRef.evaluateSequential(ForEachOps.java:174)
                    	at java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
                    	at java.util.stream.ReferencePipeline.forEach(ReferencePipeline.java:418)
                    	at jadx.core.codegen.ClassGen.addInnerClsAndMethods(ClassGen.java:236)
                    	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:227)
                    	at jadx.core.codegen.ClassGen.addClassCode(ClassGen.java:112)
                    	at jadx.core.codegen.ClassGen.addInnerClass(ClassGen.java:249)
                    	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$2(ClassGen.java:238)
                    	at java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:184)
                    	at java.util.ArrayList.forEach(ArrayList.java:1257)
                    	at java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:390)
                    	at java.util.stream.Sink$ChainedReference.end(Sink.java:258)
                    	at java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:482)
                    	at java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:471)
                    	at java.util.stream.ForEachOps$ForEachOp.evaluateSequential(ForEachOps.java:151)
                    	at java.util.stream.ForEachOps$ForEachOp$OfRef.evaluateSequential(ForEachOps.java:174)
                    	at java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
                    	at java.util.stream.ReferencePipeline.forEach(ReferencePipeline.java:418)
                    	at jadx.core.codegen.ClassGen.addInnerClsAndMethods(ClassGen.java:236)
                    	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:227)
                    	at jadx.core.codegen.ClassGen.addClassCode(ClassGen.java:112)
                    	at jadx.core.codegen.ClassGen.addInnerClass(ClassGen.java:249)
                    	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$2(ClassGen.java:238)
                    	at java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:184)
                    	at java.util.ArrayList.forEach(ArrayList.java:1257)
                    	at java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:390)
                    	at java.util.stream.Sink$ChainedReference.end(Sink.java:258)
                    	at java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:482)
                    	at java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:471)
                    	at java.util.stream.ForEachOps$ForEachOp.evaluateSequential(ForEachOps.java:151)
                    	at java.util.stream.ForEachOps$ForEachOp$OfRef.evaluateSequential(ForEachOps.java:174)
                    	at java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
                    	at java.util.stream.ReferencePipeline.forEach(ReferencePipeline.java:418)
                    	at jadx.core.codegen.ClassGen.addInnerClsAndMethods(ClassGen.java:236)
                    	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:227)
                    	at jadx.core.codegen.ClassGen.addClassCode(ClassGen.java:112)
                    	at jadx.core.codegen.ClassGen.makeClass(ClassGen.java:78)
                    	at jadx.core.codegen.CodeGen.wrapCodeGen(CodeGen.java:44)
                    	at jadx.core.codegen.CodeGen.generateJavaCode(CodeGen.java:33)
                    	at jadx.core.codegen.CodeGen.generate(CodeGen.java:21)
                    	at jadx.core.ProcessClass.generateCode(ProcessClass.java:61)
                    	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:273)
                    Caused by: jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x0002: CONSTRUCTOR  (r0v0 android.app.-$$Lambda$LoadedApk$ReceiverDispatcher$Args$_BumDX2UKsnxLVrE6UJsJZkotuA) = 
                      (r1v0 'this' android.app.LoadedApk$ReceiverDispatcher$Args A[THIS])
                     call: android.app.-$$Lambda$LoadedApk$ReceiverDispatcher$Args$_BumDX2UKsnxLVrE6UJsJZkotuA.<init>(android.app.LoadedApk$ReceiverDispatcher$Args):void type: CONSTRUCTOR in method: android.app.LoadedApk.ReceiverDispatcher.Args.getRunnable():java.lang.Runnable, dex: classes2.dex
                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:256)
                    	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:123)
                    	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:107)
                    	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:314)
                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:250)
                    	... 59 more
                    Caused by: jadx.core.utils.exceptions.JadxRuntimeException: Expected class to be processed at this point, class: android.app.-$$Lambda$LoadedApk$ReceiverDispatcher$Args$_BumDX2UKsnxLVrE6UJsJZkotuA, state: NOT_LOADED
                    	at jadx.core.dex.nodes.ClassNode.ensureProcessed(ClassNode.java:260)
                    	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:606)
                    	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:364)
                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:231)
                    	... 63 more
                    */
                /*
                    this = this;
                    android.app.-$$Lambda$LoadedApk$ReceiverDispatcher$Args$_BumDX2UKsnxLVrE6UJsJZkotuA r0 = new android.app.-$$Lambda$LoadedApk$ReceiverDispatcher$Args$_BumDX2UKsnxLVrE6UJsJZkotuA
                    r0.<init>(r1)
                    return r0
                */
                throw new UnsupportedOperationException("Method not decompiled: android.app.LoadedApk.ReceiverDispatcher.Args.getRunnable():java.lang.Runnable");
            }

            public static /* synthetic */ void lambda$getRunnable$0(Args args) {
                BroadcastReceiver receiver = args.this$0.mReceiver;
                boolean ordered = args.mOrdered;
                IActivityManager mgr = ActivityManager.getService();
                Intent intent = args.mCurIntent;
                if (intent == null) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Null intent being dispatched, mDispatched=");
                    sb.append(args.mDispatched);
                    sb.append(args.mRunCalled ? ", run() has already been called" : "");
                    Log.wtf(LoadedApk.TAG, sb.toString());
                }
                args.mCurIntent = null;
                args.mDispatched = true;
                args.mRunCalled = true;
                if (receiver != null && intent != null && !args.this$0.mForgotten) {
                    Trace.traceBegin(64, "broadcastReceiveReg");
                    try {
                        ClassLoader cl = args.this$0.mReceiver.getClass().getClassLoader();
                        intent.setExtrasClassLoader(cl);
                        intent.prepareToEnterProcess();
                        args.setExtrasClassLoader(cl);
                        receiver.setPendingResult(args);
                        receiver.onReceive(args.this$0.mContext, intent);
                    } catch (Exception e) {
                        if (args.this$0.mRegistered && ordered) {
                            args.sendFinished(mgr);
                        }
                        if (args.this$0.mInstrumentation == null || !args.this$0.mInstrumentation.onException(args.this$0.mReceiver, e)) {
                            Trace.traceEnd(64);
                            throw new RuntimeException("Error receiving broadcast " + intent + " in " + args.this$0.mReceiver, e);
                        }
                    }
                    if (receiver.getPendingResult() != null) {
                        args.finish();
                    }
                    Trace.traceEnd(64);
                } else if (args.this$0.mRegistered && ordered) {
                    args.sendFinished(mgr);
                }
            }
        }

        ReceiverDispatcher(BroadcastReceiver receiver, Context context, Handler activityThread, Instrumentation instrumentation, boolean registered) {
            if (activityThread != null) {
                this.mIIntentReceiver = new InnerReceiver(this, !registered);
                this.mReceiver = receiver;
                this.mContext = context;
                this.mActivityThread = activityThread;
                this.mInstrumentation = instrumentation;
                this.mRegistered = registered;
                this.mLocation = new IntentReceiverLeaked((String) null);
                this.mLocation.fillInStackTrace();
                return;
            }
            throw new NullPointerException("Handler must not be null");
        }

        /* access modifiers changed from: package-private */
        public void validate(Context context, Handler activityThread) {
            if (this.mContext != context) {
                throw new IllegalStateException("Receiver " + this.mReceiver + " registered with differing Context (was " + this.mContext + " now " + context + ")");
            } else if (this.mActivityThread != activityThread) {
                throw new IllegalStateException("Receiver " + this.mReceiver + " registered with differing handler (was " + this.mActivityThread + " now " + activityThread + ")");
            }
        }

        /* access modifiers changed from: package-private */
        public IntentReceiverLeaked getLocation() {
            return this.mLocation;
        }

        /* access modifiers changed from: package-private */
        @UnsupportedAppUsage
        public BroadcastReceiver getIntentReceiver() {
            return this.mReceiver;
        }

        /* access modifiers changed from: package-private */
        @UnsupportedAppUsage
        public IIntentReceiver getIIntentReceiver() {
            return this.mIIntentReceiver;
        }

        /* access modifiers changed from: package-private */
        public void setUnregisterLocation(RuntimeException ex) {
            this.mUnregisterLocation = ex;
        }

        /* access modifiers changed from: package-private */
        public RuntimeException getUnregisterLocation() {
            return this.mUnregisterLocation;
        }

        public void performReceive(Intent intent, int resultCode, String data, Bundle extras, boolean ordered, boolean sticky, int sendingUser) {
            Args args = new Args(this, intent, resultCode, data, extras, ordered, sticky, sendingUser);
            if (intent == null) {
                Log.wtf(LoadedApk.TAG, "Null intent received");
            }
            if ((intent == null || !this.mActivityThread.post(args.getRunnable())) && this.mRegistered && ordered) {
                args.sendFinished(ActivityManager.getService());
            }
        }
    }

    @UnsupportedAppUsage
    public final IServiceConnection getServiceDispatcher(ServiceConnection c, Context context, Handler handler, int flags) {
        return getServiceDispatcherCommon(c, context, handler, (Executor) null, flags);
    }

    public final IServiceConnection getServiceDispatcher(ServiceConnection c, Context context, Executor executor, int flags) {
        return getServiceDispatcherCommon(c, context, (Handler) null, executor, flags);
    }

    private IServiceConnection getServiceDispatcherCommon(ServiceConnection c, Context context, Handler handler, Executor executor, int flags) {
        ServiceDispatcher sd;
        IServiceConnection iServiceConnection;
        synchronized (this.mServices) {
            ServiceDispatcher sd2 = null;
            ArrayMap<ServiceConnection, ServiceDispatcher> map = this.mServices.get(context);
            if (map != null) {
                sd2 = map.get(c);
            }
            if (sd == null) {
                if (executor != null) {
                    sd = new ServiceDispatcher(c, context, executor, flags);
                } else {
                    sd = new ServiceDispatcher(c, context, handler, flags);
                }
                if (map == null) {
                    map = new ArrayMap<>();
                    this.mServices.put(context, map);
                }
                map.put(c, sd);
            } else {
                sd.validate(context, handler, executor);
            }
            iServiceConnection = sd.getIServiceConnection();
        }
        return iServiceConnection;
    }

    @UnsupportedAppUsage
    public IServiceConnection lookupServiceDispatcher(ServiceConnection c, Context context) {
        IServiceConnection iServiceConnection;
        synchronized (this.mServices) {
            ServiceDispatcher sd = null;
            ArrayMap<ServiceConnection, ServiceDispatcher> map = this.mServices.get(context);
            if (map != null) {
                sd = map.get(c);
            }
            iServiceConnection = sd != null ? sd.getIServiceConnection() : null;
        }
        return iServiceConnection;
    }

    public final IServiceConnection forgetServiceDispatcher(Context context, ServiceConnection c) {
        ServiceDispatcher sd;
        IServiceConnection iServiceConnection;
        synchronized (this.mServices) {
            ArrayMap<ServiceConnection, ServiceDispatcher> map = this.mServices.get(context);
            if (map == null || (sd = map.get(c)) == null) {
                ArrayMap<ServiceConnection, ServiceDispatcher> holder = this.mUnboundServices.get(context);
                if (holder != null) {
                    ServiceDispatcher sd2 = holder.get(c);
                    if (sd2 != null) {
                        RuntimeException ex = sd2.getUnbindLocation();
                        throw new IllegalArgumentException("Unbinding Service " + c + " that was already unbound", ex);
                    }
                }
                if (context == null) {
                    throw new IllegalStateException("Unbinding Service " + c + " from Context that is no longer in use: " + context);
                }
                throw new IllegalArgumentException("Service not registered: " + c);
            }
            map.remove(c);
            sd.doForget();
            if (map.size() == 0) {
                this.mServices.remove(context);
            }
            if ((sd.getFlags() & 2) != 0) {
                ArrayMap<ServiceConnection, ServiceDispatcher> holder2 = this.mUnboundServices.get(context);
                if (holder2 == null) {
                    holder2 = new ArrayMap<>();
                    this.mUnboundServices.put(context, holder2);
                }
                RuntimeException ex2 = new IllegalArgumentException("Originally unbound here:");
                ex2.fillInStackTrace();
                sd.setUnbindLocation(ex2);
                holder2.put(c, sd);
            }
            iServiceConnection = sd.getIServiceConnection();
        }
        return iServiceConnection;
    }

    static final class ServiceDispatcher {
        private final ArrayMap<ComponentName, ConnectionInfo> mActiveConnections = new ArrayMap<>();
        private final Executor mActivityExecutor;
        private final Handler mActivityThread;
        @UnsupportedAppUsage
        private final ServiceConnection mConnection;
        @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
        private final Context mContext;
        private final int mFlags;
        private boolean mForgotten;
        private final InnerConnection mIServiceConnection = new InnerConnection(this);
        private final ServiceConnectionLeaked mLocation;
        private RuntimeException mUnbindLocation;

        private static class ConnectionInfo {
            IBinder binder;
            IBinder.DeathRecipient deathMonitor;

            private ConnectionInfo() {
            }
        }

        private static class InnerConnection extends IServiceConnection.Stub {
            @UnsupportedAppUsage
            final WeakReference<ServiceDispatcher> mDispatcher;

            InnerConnection(ServiceDispatcher sd) {
                this.mDispatcher = new WeakReference<>(sd);
            }

            public void connected(ComponentName name, IBinder service, boolean dead) throws RemoteException {
                ServiceDispatcher sd = (ServiceDispatcher) this.mDispatcher.get();
                if (sd != null) {
                    sd.connected(name, service, dead);
                }
            }
        }

        @UnsupportedAppUsage
        ServiceDispatcher(ServiceConnection conn, Context context, Handler activityThread, int flags) {
            this.mConnection = conn;
            this.mContext = context;
            this.mActivityThread = activityThread;
            this.mActivityExecutor = null;
            this.mLocation = new ServiceConnectionLeaked((String) null);
            this.mLocation.fillInStackTrace();
            this.mFlags = flags;
        }

        ServiceDispatcher(ServiceConnection conn, Context context, Executor activityExecutor, int flags) {
            this.mConnection = conn;
            this.mContext = context;
            this.mActivityThread = null;
            this.mActivityExecutor = activityExecutor;
            this.mLocation = new ServiceConnectionLeaked((String) null);
            this.mLocation.fillInStackTrace();
            this.mFlags = flags;
        }

        /* access modifiers changed from: package-private */
        public void validate(Context context, Handler activityThread, Executor activityExecutor) {
            if (this.mContext != context) {
                throw new RuntimeException("ServiceConnection " + this.mConnection + " registered with differing Context (was " + this.mContext + " now " + context + ")");
            } else if (this.mActivityThread != activityThread) {
                throw new RuntimeException("ServiceConnection " + this.mConnection + " registered with differing handler (was " + this.mActivityThread + " now " + activityThread + ")");
            } else if (this.mActivityExecutor != activityExecutor) {
                throw new RuntimeException("ServiceConnection " + this.mConnection + " registered with differing executor (was " + this.mActivityExecutor + " now " + activityExecutor + ")");
            }
        }

        /* access modifiers changed from: package-private */
        public void doForget() {
            synchronized (this) {
                for (int i = 0; i < this.mActiveConnections.size(); i++) {
                    ConnectionInfo ci = this.mActiveConnections.valueAt(i);
                    ci.binder.unlinkToDeath(ci.deathMonitor, 0);
                }
                this.mActiveConnections.clear();
                this.mForgotten = true;
            }
        }

        /* access modifiers changed from: package-private */
        public ServiceConnectionLeaked getLocation() {
            return this.mLocation;
        }

        /* access modifiers changed from: package-private */
        public ServiceConnection getServiceConnection() {
            return this.mConnection;
        }

        /* access modifiers changed from: package-private */
        @UnsupportedAppUsage
        public IServiceConnection getIServiceConnection() {
            return this.mIServiceConnection;
        }

        /* access modifiers changed from: package-private */
        public int getFlags() {
            return this.mFlags;
        }

        /* access modifiers changed from: package-private */
        public void setUnbindLocation(RuntimeException ex) {
            this.mUnbindLocation = ex;
        }

        /* access modifiers changed from: package-private */
        public RuntimeException getUnbindLocation() {
            return this.mUnbindLocation;
        }

        public void connected(ComponentName name, IBinder service, boolean dead) {
            if (this.mActivityExecutor != null) {
                this.mActivityExecutor.execute(new RunConnection(name, service, 0, dead));
            } else if (this.mActivityThread != null) {
                this.mActivityThread.post(new RunConnection(name, service, 0, dead));
            } else {
                doConnected(name, service, dead);
            }
        }

        public void death(ComponentName name, IBinder service) {
            if (this.mActivityExecutor != null) {
                this.mActivityExecutor.execute(new RunConnection(name, service, 1, false));
            } else if (this.mActivityThread != null) {
                this.mActivityThread.post(new RunConnection(name, service, 1, false));
            } else {
                doDeath(name, service);
            }
        }

        /* JADX WARNING: Code restructure failed: missing block: B:26:0x004b, code lost:
            if (r0 == null) goto L_0x0052;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:27:0x004d, code lost:
            r4.mConnection.onServiceDisconnected(r5);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:28:0x0052, code lost:
            if (r7 == false) goto L_0x0059;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:29:0x0054, code lost:
            r4.mConnection.onBindingDied(r5);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:30:0x0059, code lost:
            if (r6 == null) goto L_0x0061;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:31:0x005b, code lost:
            r4.mConnection.onServiceConnected(r5, r6);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:32:0x0061, code lost:
            r4.mConnection.onNullBinding(r5);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:41:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:42:?, code lost:
            return;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void doConnected(android.content.ComponentName r5, android.os.IBinder r6, boolean r7) {
            /*
                r4 = this;
                monitor-enter(r4)
                boolean r0 = r4.mForgotten     // Catch:{ all -> 0x0067 }
                if (r0 == 0) goto L_0x0007
                monitor-exit(r4)     // Catch:{ all -> 0x0067 }
                return
            L_0x0007:
                android.util.ArrayMap<android.content.ComponentName, android.app.LoadedApk$ServiceDispatcher$ConnectionInfo> r0 = r4.mActiveConnections     // Catch:{ all -> 0x0067 }
                java.lang.Object r0 = r0.get(r5)     // Catch:{ all -> 0x0067 }
                android.app.LoadedApk$ServiceDispatcher$ConnectionInfo r0 = (android.app.LoadedApk.ServiceDispatcher.ConnectionInfo) r0     // Catch:{ all -> 0x0067 }
                if (r0 == 0) goto L_0x0017
                android.os.IBinder r1 = r0.binder     // Catch:{ all -> 0x0067 }
                if (r1 != r6) goto L_0x0017
                monitor-exit(r4)     // Catch:{ all -> 0x0067 }
                return
            L_0x0017:
                r1 = 0
                if (r6 == 0) goto L_0x003c
                android.app.LoadedApk$ServiceDispatcher$ConnectionInfo r2 = new android.app.LoadedApk$ServiceDispatcher$ConnectionInfo     // Catch:{ all -> 0x0067 }
                r3 = 0
                r2.<init>()     // Catch:{ all -> 0x0067 }
                r2.binder = r6     // Catch:{ all -> 0x0067 }
                android.app.LoadedApk$ServiceDispatcher$DeathMonitor r3 = new android.app.LoadedApk$ServiceDispatcher$DeathMonitor     // Catch:{ all -> 0x0067 }
                r3.<init>(r5, r6)     // Catch:{ all -> 0x0067 }
                r2.deathMonitor = r3     // Catch:{ all -> 0x0067 }
                android.os.IBinder$DeathRecipient r3 = r2.deathMonitor     // Catch:{ RemoteException -> 0x0034 }
                r6.linkToDeath(r3, r1)     // Catch:{ RemoteException -> 0x0034 }
                android.util.ArrayMap<android.content.ComponentName, android.app.LoadedApk$ServiceDispatcher$ConnectionInfo> r3 = r4.mActiveConnections     // Catch:{ RemoteException -> 0x0034 }
                r3.put(r5, r2)     // Catch:{ RemoteException -> 0x0034 }
                goto L_0x0041
            L_0x0034:
                r1 = move-exception
                android.util.ArrayMap<android.content.ComponentName, android.app.LoadedApk$ServiceDispatcher$ConnectionInfo> r3 = r4.mActiveConnections     // Catch:{ all -> 0x0067 }
                r3.remove(r5)     // Catch:{ all -> 0x0067 }
                monitor-exit(r4)     // Catch:{ all -> 0x0067 }
                return
            L_0x003c:
                android.util.ArrayMap<android.content.ComponentName, android.app.LoadedApk$ServiceDispatcher$ConnectionInfo> r2 = r4.mActiveConnections     // Catch:{ all -> 0x0067 }
                r2.remove(r5)     // Catch:{ all -> 0x0067 }
            L_0x0041:
                if (r0 == 0) goto L_0x004a
                android.os.IBinder r2 = r0.binder     // Catch:{ all -> 0x0067 }
                android.os.IBinder$DeathRecipient r3 = r0.deathMonitor     // Catch:{ all -> 0x0067 }
                r2.unlinkToDeath(r3, r1)     // Catch:{ all -> 0x0067 }
            L_0x004a:
                monitor-exit(r4)     // Catch:{ all -> 0x0067 }
                if (r0 == 0) goto L_0x0052
                android.content.ServiceConnection r1 = r4.mConnection
                r1.onServiceDisconnected(r5)
            L_0x0052:
                if (r7 == 0) goto L_0x0059
                android.content.ServiceConnection r1 = r4.mConnection
                r1.onBindingDied(r5)
            L_0x0059:
                if (r6 == 0) goto L_0x0061
                android.content.ServiceConnection r1 = r4.mConnection
                r1.onServiceConnected(r5, r6)
                goto L_0x0066
            L_0x0061:
                android.content.ServiceConnection r1 = r4.mConnection
                r1.onNullBinding(r5)
            L_0x0066:
                return
            L_0x0067:
                r0 = move-exception
                monitor-exit(r4)     // Catch:{ all -> 0x0067 }
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: android.app.LoadedApk.ServiceDispatcher.doConnected(android.content.ComponentName, android.os.IBinder, boolean):void");
        }

        public void doDeath(ComponentName name, IBinder service) {
            synchronized (this) {
                ConnectionInfo old = this.mActiveConnections.get(name);
                if (old != null) {
                    if (old.binder == service) {
                        this.mActiveConnections.remove(name);
                        old.binder.unlinkToDeath(old.deathMonitor, 0);
                        this.mConnection.onServiceDisconnected(name);
                    }
                }
            }
        }

        private final class RunConnection implements Runnable {
            final int mCommand;
            final boolean mDead;
            final ComponentName mName;
            final IBinder mService;

            RunConnection(ComponentName name, IBinder service, int command, boolean dead) {
                this.mName = name;
                this.mService = service;
                this.mCommand = command;
                this.mDead = dead;
            }

            public void run() {
                if (this.mCommand == 0) {
                    ServiceDispatcher.this.doConnected(this.mName, this.mService, this.mDead);
                } else if (this.mCommand == 1) {
                    ServiceDispatcher.this.doDeath(this.mName, this.mService);
                }
            }
        }

        private final class DeathMonitor implements IBinder.DeathRecipient {
            final ComponentName mName;
            final IBinder mService;

            DeathMonitor(ComponentName name, IBinder service) {
                this.mName = name;
                this.mService = service;
            }

            public void binderDied() {
                ServiceDispatcher.this.death(this.mName, this.mService);
            }
        }
    }
}
