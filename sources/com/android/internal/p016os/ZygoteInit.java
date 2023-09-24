package com.android.internal.p016os;

import android.annotation.UnsupportedAppUsage;
import android.app.ApplicationLoaders;
import android.content.p002pm.SharedLibraryInfo;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.p007os.Build;
import android.p007os.Environment;
import android.p007os.IInstalld;
import android.p007os.Process;
import android.p007os.RemoteException;
import android.p007os.ServiceManager;
import android.p007os.SystemClock;
import android.p007os.SystemProperties;
import android.p007os.Trace;
import android.p007os.UserHandle;
import android.p007os.ZygoteProcess;
import android.provider.SettingsStringUtil;
import android.security.keystore.AndroidKeyStoreProvider;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.system.StructCapUserData;
import android.system.StructCapUserHeader;
import android.telephony.SmsManager;
import android.text.Hyphenator;
import android.util.EventLog;
import android.util.Log;
import android.util.TimingsTraceLog;
import android.webkit.WebViewFactory;
import android.widget.TextView;
import com.android.internal.C3132R;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.p016os.RuntimeInit;
import com.android.internal.util.Preconditions;
import com.ibm.icu.text.PluralRules;
import dalvik.system.VMRuntime;
import dalvik.system.ZygoteHooks;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.Provider;
import java.security.Security;
import libcore.io.IoUtils;

/* renamed from: com.android.internal.os.ZygoteInit */
/* loaded from: classes4.dex */
public class ZygoteInit {
    private static final String ABI_LIST_ARG = "--abi-list=";
    private static final int LOG_BOOT_PROGRESS_PRELOAD_END = 3030;
    private static final int LOG_BOOT_PROGRESS_PRELOAD_START = 3020;
    private static final String PRELOADED_CLASSES = "/system/etc/preloaded-classes";
    private static final int PRELOAD_GC_THRESHOLD = 50000;
    public static final boolean PRELOAD_RESOURCES = true;
    private static final String PROPERTY_DISABLE_GRAPHICS_DRIVER_PRELOADING = "ro.zygote.disable_gl_preload";
    private static final int ROOT_GID = 0;
    private static final int ROOT_UID = 0;
    private static final String SOCKET_NAME_ARG = "--socket-name=";
    private static final String TAG = "Zygote";
    private static final int UNPRIVILEGED_GID = 9999;
    private static final int UNPRIVILEGED_UID = 9999;
    @UnsupportedAppUsage
    private static Resources mResources;
    private static ClassLoader sCachedSystemServerClassLoader = null;
    private static boolean sPreloadComplete;

    private static native void nativePreloadAppProcessHALs();

    static native void nativePreloadGraphicsDriver();

    private static final native void nativeZygoteInit();

    static void preload(TimingsTraceLog bootTimingsTraceLog) {
        Log.m72d(TAG, "begin preload");
        bootTimingsTraceLog.traceBegin("BeginPreload");
        beginPreload();
        bootTimingsTraceLog.traceEnd();
        bootTimingsTraceLog.traceBegin("PreloadClasses");
        preloadClasses();
        bootTimingsTraceLog.traceEnd();
        bootTimingsTraceLog.traceBegin("CacheNonBootClasspathClassLoaders");
        cacheNonBootClasspathClassLoaders();
        bootTimingsTraceLog.traceEnd();
        bootTimingsTraceLog.traceBegin("PreloadResources");
        preloadResources();
        bootTimingsTraceLog.traceEnd();
        Trace.traceBegin(16384L, "PreloadAppProcessHALs");
        nativePreloadAppProcessHALs();
        Trace.traceEnd(16384L);
        Trace.traceBegin(16384L, "PreloadGraphicsDriver");
        maybePreloadGraphicsDriver();
        Trace.traceEnd(16384L);
        preloadSharedLibraries();
        preloadTextResources();
        WebViewFactory.prepareWebViewInZygote();
        endPreload();
        warmUpJcaProviders();
        Log.m72d(TAG, "end preload");
        sPreloadComplete = true;
    }

    public static void lazyPreload() {
        Preconditions.checkState(!sPreloadComplete);
        Log.m68i(TAG, "Lazily preloading resources.");
        preload(new TimingsTraceLog("ZygoteInitTiming_lazy", 16384L));
    }

    private static void beginPreload() {
        Log.m68i(TAG, "Calling ZygoteHooks.beginPreload()");
        ZygoteHooks.onBeginPreload();
    }

    private static void endPreload() {
        ZygoteHooks.onEndPreload();
        Log.m68i(TAG, "Called ZygoteHooks.endPreload()");
    }

    private static void preloadSharedLibraries() {
        Log.m68i(TAG, "Preloading shared libraries...");
        System.loadLibrary("android");
        System.loadLibrary("compiler_rt");
        System.loadLibrary("jnigraphics");
        try {
            System.loadLibrary("qti_performance");
        } catch (UnsatisfiedLinkError e) {
            Log.m70e(TAG, "Couldn't load qti_performance");
        }
    }

    private static void maybePreloadGraphicsDriver() {
        if (!SystemProperties.getBoolean(PROPERTY_DISABLE_GRAPHICS_DRIVER_PRELOADING, false)) {
            nativePreloadGraphicsDriver();
        }
    }

    private static void preloadTextResources() {
        Hyphenator.init();
        TextView.preloadFontCache();
    }

    private static void warmUpJcaProviders() {
        Provider[] providers;
        long startTime = SystemClock.uptimeMillis();
        Trace.traceBegin(16384L, "Starting installation of AndroidKeyStoreProvider");
        AndroidKeyStoreProvider.install();
        Log.m68i(TAG, "Installed AndroidKeyStoreProvider in " + (SystemClock.uptimeMillis() - startTime) + "ms.");
        Trace.traceEnd(16384L);
        long startTime2 = SystemClock.uptimeMillis();
        Trace.traceBegin(16384L, "Starting warm up of JCA providers");
        for (Provider p : Security.getProviders()) {
            p.warmUpServiceProvision();
        }
        Log.m68i(TAG, "Warmed up JCA providers in " + (SystemClock.uptimeMillis() - startTime2) + "ms.");
        Trace.traceEnd(16384L);
    }

    private static void preloadClasses() {
        int count;
        VMRuntime runtime = VMRuntime.getRuntime();
        try {
            InputStream is = new FileInputStream(PRELOADED_CLASSES);
            Log.m68i(TAG, "Preloading classes...");
            long startTime = SystemClock.uptimeMillis();
            int reuid = Os.getuid();
            int regid = Os.getgid();
            boolean droppedPriviliges = false;
            if (reuid == 0 && regid == 0) {
                try {
                    Os.setregid(0, Process.NOBODY_UID);
                    Os.setreuid(0, Process.NOBODY_UID);
                    droppedPriviliges = true;
                } catch (ErrnoException ex) {
                    throw new RuntimeException("Failed to drop root", ex);
                }
            }
            float defaultUtilization = runtime.getTargetHeapUtilization();
            runtime.setTargetHeapUtilization(0.8f);
            long j = 16384;
            try {
                try {
                    BufferedReader br = new BufferedReader(new InputStreamReader(is), 256);
                    int count2 = 0;
                    while (true) {
                        count = count2;
                        String line = br.readLine();
                        if (line == null) {
                            break;
                        }
                        String line2 = line.trim();
                        if (!line2.startsWith("#") && !line2.equals("")) {
                            Trace.traceBegin(j, line2);
                            try {
                                Class.forName(line2, true, null);
                                count++;
                            } catch (ClassNotFoundException e) {
                                Log.m64w(TAG, "Class not found for preloading: " + line2);
                            } catch (UnsatisfiedLinkError e2) {
                                Log.m64w(TAG, "Problem preloading " + line2 + PluralRules.KEYWORD_RULE_SEPARATOR + e2);
                            } catch (Throwable t) {
                                Log.m69e(TAG, "Error preloading " + line2 + ".", t);
                                if (t instanceof Error) {
                                    throw ((Error) t);
                                }
                                if (!(t instanceof RuntimeException)) {
                                    throw new RuntimeException(t);
                                }
                                throw ((RuntimeException) t);
                            }
                            count2 = count;
                            Trace.traceEnd(16384L);
                            j = 16384;
                        }
                        count2 = count;
                        j = 16384;
                    }
                    Log.m68i(TAG, "...preloaded " + count + " classes in " + (SystemClock.uptimeMillis() - startTime) + "ms.");
                    IoUtils.closeQuietly(is);
                    runtime.setTargetHeapUtilization(defaultUtilization);
                    Trace.traceBegin(16384L, "PreloadDexCaches");
                    runtime.preloadDexCaches();
                    Trace.traceEnd(16384L);
                    if (droppedPriviliges) {
                        try {
                            Os.setreuid(0, 0);
                            Os.setregid(0, 0);
                        } catch (ErrnoException ex2) {
                            throw new RuntimeException("Failed to restore root", ex2);
                        }
                    }
                } catch (IOException e3) {
                    Log.m69e(TAG, "Error reading /system/etc/preloaded-classes.", e3);
                    IoUtils.closeQuietly(is);
                    runtime.setTargetHeapUtilization(defaultUtilization);
                    Trace.traceBegin(16384L, "PreloadDexCaches");
                    runtime.preloadDexCaches();
                    Trace.traceEnd(16384L);
                    if (droppedPriviliges) {
                        try {
                            Os.setreuid(0, 0);
                            Os.setregid(0, 0);
                        } catch (ErrnoException ex3) {
                            throw new RuntimeException("Failed to restore root", ex3);
                        }
                    }
                }
            } catch (Throwable ex4) {
                IoUtils.closeQuietly(is);
                runtime.setTargetHeapUtilization(defaultUtilization);
                Trace.traceBegin(16384L, "PreloadDexCaches");
                runtime.preloadDexCaches();
                Trace.traceEnd(16384L);
                if (droppedPriviliges) {
                    try {
                        Os.setreuid(0, 0);
                        Os.setregid(0, 0);
                    } catch (ErrnoException ex5) {
                        throw new RuntimeException("Failed to restore root", ex5);
                    }
                }
                throw ex4;
            }
        } catch (FileNotFoundException e4) {
            Log.m70e(TAG, "Couldn't find /system/etc/preloaded-classes.");
        }
    }

    private static void cacheNonBootClasspathClassLoaders() {
        SharedLibraryInfo hidlBase = new SharedLibraryInfo("/system/framework/android.hidl.base-V1.0-java.jar", null, null, null, 0L, 0, null, null, null);
        SharedLibraryInfo hidlManager = new SharedLibraryInfo("/system/framework/android.hidl.manager-V1.0-java.jar", null, null, null, 0L, 0, null, null, null);
        hidlManager.addDependency(hidlBase);
        ApplicationLoaders.getDefault().createAndCacheNonBootclasspathSystemClassLoaders(new SharedLibraryInfo[]{hidlBase, hidlManager});
    }

    private static void preloadResources() {
        VMRuntime.getRuntime();
        try {
            mResources = Resources.getSystem();
            mResources.startPreloading();
            Log.m68i(TAG, "Preloading resources...");
            long startTime = SystemClock.uptimeMillis();
            TypedArray ar = mResources.obtainTypedArray(C3132R.array.preloaded_drawables);
            int N = preloadDrawables(ar);
            ar.recycle();
            Log.m68i(TAG, "...preloaded " + N + " resources in " + (SystemClock.uptimeMillis() - startTime) + "ms.");
            long startTime2 = SystemClock.uptimeMillis();
            TypedArray ar2 = mResources.obtainTypedArray(C3132R.array.preloaded_color_state_lists);
            int N2 = preloadColorStateLists(ar2);
            ar2.recycle();
            Log.m68i(TAG, "...preloaded " + N2 + " resources in " + (SystemClock.uptimeMillis() - startTime2) + "ms.");
            if (mResources.getBoolean(C3132R.bool.config_freeformWindowManagement)) {
                long startTime3 = SystemClock.uptimeMillis();
                TypedArray ar3 = mResources.obtainTypedArray(C3132R.array.preloaded_freeform_multi_window_drawables);
                int N3 = preloadDrawables(ar3);
                ar3.recycle();
                Log.m68i(TAG, "...preloaded " + N3 + " resource in " + (SystemClock.uptimeMillis() - startTime3) + "ms.");
            }
            mResources.finishPreloading();
        } catch (RuntimeException e) {
            Log.m63w(TAG, "Failure preloading resources", e);
        }
    }

    private static int preloadColorStateLists(TypedArray ar) {
        int N = ar.length();
        for (int i = 0; i < N; i++) {
            int id = ar.getResourceId(i, 0);
            if (id != 0 && mResources.getColorStateList(id, null) == null) {
                throw new IllegalArgumentException("Unable to find preloaded color resource #0x" + Integer.toHexString(id) + " (" + ar.getString(i) + ")");
            }
        }
        return N;
    }

    private static int preloadDrawables(TypedArray ar) {
        int N = ar.length();
        for (int i = 0; i < N; i++) {
            int id = ar.getResourceId(i, 0);
            if (id != 0 && mResources.getDrawable(id, null) == null) {
                throw new IllegalArgumentException("Unable to find preloaded drawable resource #0x" + Integer.toHexString(id) + " (" + ar.getString(i) + ")");
            }
        }
        return N;
    }

    private static void gcAndFinalize() {
        ZygoteHooks.gcAndFinalize();
    }

    private static Runnable handleSystemServerProcess(ZygoteArguments parsedArgs) {
        Os.umask(OsConstants.S_IRWXG | OsConstants.S_IRWXO);
        if (parsedArgs.mNiceName != null) {
            Process.setArgV0(parsedArgs.mNiceName);
        }
        String systemServerClasspath = Os.getenv("SYSTEMSERVERCLASSPATH");
        if (systemServerClasspath != null) {
            if (performSystemServerDexOpt(systemServerClasspath)) {
                sCachedSystemServerClassLoader = null;
            }
            boolean profileSystemServer = SystemProperties.getBoolean("dalvik.vm.profilesystemserver", false);
            if (profileSystemServer && (Build.IS_USERDEBUG || Build.IS_ENG)) {
                try {
                    prepareSystemServerProfile(systemServerClasspath);
                } catch (Exception e) {
                    Log.wtf(TAG, "Failed to set up system server profile", e);
                }
            }
        }
        if (parsedArgs.mInvokeWith != null) {
            String[] args = parsedArgs.mRemainingArgs;
            if (systemServerClasspath != null) {
                String[] amendedArgs = new String[args.length + 2];
                amendedArgs[0] = "-cp";
                amendedArgs[1] = systemServerClasspath;
                System.arraycopy(args, 0, amendedArgs, 2, args.length);
                args = amendedArgs;
            }
            WrapperInit.execApplication(parsedArgs.mInvokeWith, parsedArgs.mNiceName, parsedArgs.mTargetSdkVersion, VMRuntime.getCurrentInstructionSet(), null, args);
            throw new IllegalStateException("Unexpected return from WrapperInit.execApplication");
        }
        createSystemServerClassLoader();
        ClassLoader cl = sCachedSystemServerClassLoader;
        if (cl != null) {
            Thread.currentThread().setContextClassLoader(cl);
        }
        return zygoteInit(parsedArgs.mTargetSdkVersion, parsedArgs.mRemainingArgs, cl);
    }

    private static void createSystemServerClassLoader() {
        String systemServerClasspath;
        if (sCachedSystemServerClassLoader == null && (systemServerClasspath = Os.getenv("SYSTEMSERVERCLASSPATH")) != null) {
            sCachedSystemServerClassLoader = createPathClassLoader(systemServerClasspath, 10000);
        }
    }

    private static void prepareSystemServerProfile(String systemServerClasspath) throws RemoteException {
        if (systemServerClasspath.isEmpty()) {
            return;
        }
        String[] codePaths = systemServerClasspath.split(SettingsStringUtil.DELIMITER);
        IInstalld installd = IInstalld.Stub.asInterface(ServiceManager.getService("installd"));
        installd.prepareAppProfile("android", 0, UserHandle.getAppId(1000), "primary.prof", codePaths[0], null);
        File profileDir = Environment.getDataProfilesDePackageDirectory(0, "android");
        String profilePath = new File(profileDir, "primary.prof").getAbsolutePath();
        VMRuntime.registerAppInfo(profilePath, codePaths);
    }

    public static void setApiBlacklistExemptions(String[] exemptions) {
        VMRuntime.getRuntime().setHiddenApiExemptions(exemptions);
    }

    public static void setHiddenApiAccessLogSampleRate(int percent) {
        VMRuntime.getRuntime().setHiddenApiAccessLogSamplingRate(percent);
    }

    public static void setHiddenApiUsageLogger(VMRuntime.HiddenApiUsageLogger logger) {
        VMRuntime.getRuntime();
        VMRuntime.setHiddenApiUsageLogger(logger);
    }

    static ClassLoader createPathClassLoader(String classPath, int targetSdkVersion) {
        String libraryPath = System.getProperty("java.library.path");
        ClassLoader parent = ClassLoader.getSystemClassLoader().getParent();
        return ClassLoaderFactory.createClassLoader(classPath, libraryPath, libraryPath, parent, targetSdkVersion, true, null);
    }

    /*  JADX ERROR: JadxRuntimeException in pass: BlockProcessor
        jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:18:0x00a5
        	at jadx.core.dex.visitors.blocks.BlockProcessor.checkForUnreachableBlocks(BlockProcessor.java:81)
        	at jadx.core.dex.visitors.blocks.BlockProcessor.processBlocksTree(BlockProcessor.java:47)
        	at jadx.core.dex.visitors.blocks.BlockProcessor.visit(BlockProcessor.java:39)
        */
    private static boolean performSystemServerDexOpt(java.lang.String r37) {
        /*
            Method dump skipped, instructions count: 255
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.p016os.ZygoteInit.performSystemServerDexOpt(java.lang.String):boolean");
    }

    private static String getSystemServerClassLoaderContext(String classPath) {
        if (classPath == null) {
            return "PCL[]";
        }
        return "PCL[" + classPath + "]";
    }

    private static String encodeSystemServerClassPath(String classPath, String newElement) {
        if (classPath == null || classPath.isEmpty()) {
            return newElement;
        }
        return classPath + SettingsStringUtil.DELIMITER + newElement;
    }

    private static Runnable forkSystemServer(String abiList, String socketName, ZygoteServer zygoteServer) {
        long capabilities = posixCapabilitiesAsBits(OsConstants.CAP_IPC_LOCK, OsConstants.CAP_KILL, OsConstants.CAP_NET_ADMIN, OsConstants.CAP_NET_BIND_SERVICE, OsConstants.CAP_NET_BROADCAST, OsConstants.CAP_NET_RAW, OsConstants.CAP_SYS_MODULE, OsConstants.CAP_SYS_NICE, OsConstants.CAP_SYS_PTRACE, OsConstants.CAP_SYS_TIME, OsConstants.CAP_SYS_TTY_CONFIG, OsConstants.CAP_WAKE_ALARM, OsConstants.CAP_BLOCK_SUSPEND);
        StructCapUserHeader header = new StructCapUserHeader(OsConstants._LINUX_CAPABILITY_VERSION_3, 0);
        try {
            StructCapUserData[] data = Os.capget(header);
            long capabilities2 = ((data[1].effective << 32) | data[0].effective) & capabilities;
            String[] args = {"--setuid=1000", "--setgid=1000", "--setgroups=1001,1002,1003,1004,1005,1006,1007,1008,1009,1010,1018,1021,1023,1024,1032,1065,3001,3002,3003,3006,3007,3009,3010", "--capabilities=" + capabilities2 + SmsManager.REGEX_PREFIX_DELIMITER + capabilities2, "--nice-name=system_server", "--runtime-args", "--target-sdk-version=10000", "com.android.server.SystemServer"};
            try {
                ZygoteArguments parsedArgs = new ZygoteArguments(args);
                Zygote.applyDebuggerSystemProperty(parsedArgs);
                Zygote.applyInvokeWithSystemProperty(parsedArgs);
                boolean profileSystemServer = SystemProperties.getBoolean("dalvik.vm.profilesystemserver", false);
                if (profileSystemServer) {
                    try {
                        parsedArgs.mRuntimeFlags |= 16384;
                    } catch (IllegalArgumentException e) {
                        ex = e;
                        throw new RuntimeException(ex);
                    }
                }
                try {
                    int pid = Zygote.forkSystemServer(parsedArgs.mUid, parsedArgs.mGid, parsedArgs.mGids, parsedArgs.mRuntimeFlags, null, parsedArgs.mPermittedCapabilities, parsedArgs.mEffectiveCapabilities);
                    if (pid == 0) {
                        if (hasSecondZygote(abiList)) {
                            waitForSecondaryZygote(socketName);
                        }
                        zygoteServer.closeServerSocket();
                        return handleSystemServerProcess(parsedArgs);
                    }
                    return null;
                } catch (IllegalArgumentException e2) {
                    ex = e2;
                    throw new RuntimeException(ex);
                }
            } catch (IllegalArgumentException e3) {
                ex = e3;
            }
        } catch (ErrnoException e4) {
            throw new RuntimeException("Failed to capget()", e4);
        }
    }

    private static long posixCapabilitiesAsBits(int... capabilities) {
        long result = 0;
        for (int capability : capabilities) {
            if (capability < 0 || capability > OsConstants.CAP_LAST_CAP) {
                throw new IllegalArgumentException(String.valueOf(capability));
            }
            result |= 1 << capability;
        }
        return result;
    }

    @UnsupportedAppUsage
    public static void main(String[] argv) {
        Runnable r;
        ZygoteServer zygoteServer = null;
        ZygoteHooks.startZygoteNoThreadCreation();
        try {
            Os.setpgid(0, 0);
            try {
                if (!"1".equals(SystemProperties.get("sys.boot_completed"))) {
                    MetricsLogger.histogram(null, "boot_zygote_init", (int) SystemClock.elapsedRealtime());
                }
                String bootTimeTag = Process.is64Bit() ? "Zygote64Timing" : "Zygote32Timing";
                TimingsTraceLog bootTimingsTraceLog = new TimingsTraceLog(bootTimeTag, 16384L);
                bootTimingsTraceLog.traceBegin("ZygoteInit");
                RuntimeInit.enableDdms();
                boolean startSystemServer = false;
                String zygoteSocketName = Zygote.PRIMARY_SOCKET_NAME;
                String abiList = null;
                boolean enableLazyPreload = false;
                for (int i = 1; i < argv.length; i++) {
                    if ("start-system-server".equals(argv[i])) {
                        startSystemServer = true;
                    } else if ("--enable-lazy-preload".equals(argv[i])) {
                        enableLazyPreload = true;
                    } else if (argv[i].startsWith("--abi-list=")) {
                        abiList = argv[i].substring("--abi-list=".length());
                    } else if (argv[i].startsWith(SOCKET_NAME_ARG)) {
                        zygoteSocketName = argv[i].substring(SOCKET_NAME_ARG.length());
                    } else {
                        throw new RuntimeException("Unknown command line argument: " + argv[i]);
                    }
                }
                boolean isPrimaryZygote = zygoteSocketName.equals(Zygote.PRIMARY_SOCKET_NAME);
                if (abiList == null) {
                    throw new RuntimeException("No ABI list supplied.");
                }
                if (!enableLazyPreload) {
                    bootTimingsTraceLog.traceBegin("ZygotePreload");
                    EventLog.writeEvent((int) LOG_BOOT_PROGRESS_PRELOAD_START, SystemClock.uptimeMillis());
                    preload(bootTimingsTraceLog);
                    EventLog.writeEvent((int) LOG_BOOT_PROGRESS_PRELOAD_END, SystemClock.uptimeMillis());
                    bootTimingsTraceLog.traceEnd();
                } else {
                    Zygote.resetNicePriority();
                }
                bootTimingsTraceLog.traceBegin("PostZygoteInitGC");
                gcAndFinalize();
                bootTimingsTraceLog.traceEnd();
                bootTimingsTraceLog.traceEnd();
                Trace.setTracingEnabled(false, 0);
                Zygote.initNativeState(isPrimaryZygote);
                ZygoteHooks.stopZygoteNoThreadCreation();
                ZygoteServer zygoteServer2 = new ZygoteServer(isPrimaryZygote);
                if (startSystemServer && (r = forkSystemServer(abiList, zygoteSocketName, zygoteServer2)) != null) {
                    r.run();
                    zygoteServer2.closeServerSocket();
                    return;
                }
                Log.m68i(TAG, "Accepting command socket connections");
                Runnable caller = zygoteServer2.runSelectLoop(abiList);
                zygoteServer2.closeServerSocket();
                if (caller != null) {
                    caller.run();
                }
            } catch (Throwable ex) {
                try {
                    Log.m69e(TAG, "System zygote died with exception", ex);
                    throw ex;
                } catch (Throwable th) {
                    if (0 != 0) {
                        zygoteServer.closeServerSocket();
                    }
                    throw th;
                }
            }
        } catch (ErrnoException ex2) {
            throw new RuntimeException("Failed to setpgid(0,0)", ex2);
        }
    }

    private static boolean hasSecondZygote(String abiList) {
        return !SystemProperties.get("ro.product.cpu.abilist").equals(abiList);
    }

    private static void waitForSecondaryZygote(String socketName) {
        String otherZygoteName = Zygote.PRIMARY_SOCKET_NAME.equals(socketName) ? Zygote.SECONDARY_SOCKET_NAME : Zygote.PRIMARY_SOCKET_NAME;
        ZygoteProcess.waitForConnectionToZygote(otherZygoteName);
    }

    static boolean isPreloadComplete() {
        return sPreloadComplete;
    }

    private ZygoteInit() {
    }

    public static final Runnable zygoteInit(int targetSdkVersion, String[] argv, ClassLoader classLoader) {
        Trace.traceBegin(64L, "ZygoteInit");
        RuntimeInit.redirectLogStreams();
        RuntimeInit.commonInit();
        nativeZygoteInit();
        return RuntimeInit.applicationInit(targetSdkVersion, argv, classLoader);
    }

    static final Runnable childZygoteInit(int targetSdkVersion, String[] argv, ClassLoader classLoader) {
        RuntimeInit.Arguments args = new RuntimeInit.Arguments(argv);
        return RuntimeInit.findStaticMain(args.startClass, args.startArgs, classLoader);
    }
}
