package com.android.internal.p016os;

import android.annotation.UnsupportedAppUsage;
import android.app.ActivityManager;
import android.app.ActivityThread;
import android.app.ApplicationErrorReport;
import android.ddm.DdmRegister;
import android.p007os.Build;
import android.p007os.DeadObjectException;
import android.p007os.Debug;
import android.p007os.IBinder;
import android.p007os.Process;
import android.p007os.SystemProperties;
import android.p007os.Trace;
import android.util.Log;
import android.util.Slog;
import com.android.internal.logging.AndroidConfig;
import com.android.server.NetworkManagementSocketTagger;
import com.wits.pms.test.BuildConfig;
import dalvik.system.RuntimeHooks;
import dalvik.system.VMRuntime;
import java.lang.Thread;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.logging.LogManager;

/* renamed from: com.android.internal.os.RuntimeInit */
/* loaded from: classes4.dex */
public class RuntimeInit {
    static final boolean DEBUG = false;
    static final String TAG = "AndroidRuntime";
    @UnsupportedAppUsage
    private static boolean initialized;
    @UnsupportedAppUsage
    private static IBinder mApplicationObject;
    private static volatile boolean mCrashing = false;

    private static final native void nativeFinishInit();

    private static final native void nativeSetExitWithoutCleanup(boolean z);

    /* JADX INFO: Access modifiers changed from: private */
    public static int Clog_e(String tag, String msg, Throwable tr) {
        return Log.printlns(4, 6, tag, msg, tr);
    }

    /* renamed from: com.android.internal.os.RuntimeInit$LoggingHandler */
    /* loaded from: classes4.dex */
    private static class LoggingHandler implements Thread.UncaughtExceptionHandler {
        public volatile boolean mTriggered;

        private LoggingHandler() {
            this.mTriggered = false;
        }

        @Override // java.lang.Thread.UncaughtExceptionHandler
        public void uncaughtException(Thread t, Throwable e) {
            this.mTriggered = true;
            if (RuntimeInit.mCrashing) {
                return;
            }
            if (RuntimeInit.mApplicationObject == null && 1000 == Process.myUid()) {
                RuntimeInit.Clog_e(RuntimeInit.TAG, "*** FATAL EXCEPTION IN SYSTEM PROCESS: " + t.getName(), e);
                return;
            }
            StringBuilder message = new StringBuilder();
            message.append("FATAL EXCEPTION: ");
            message.append(t.getName());
            message.append("\n");
            String processName = ActivityThread.currentProcessName();
            if (processName != null) {
                message.append("Process: ");
                message.append(processName);
                message.append(", ");
            }
            message.append("PID: ");
            message.append(Process.myPid());
            RuntimeInit.Clog_e(RuntimeInit.TAG, message.toString(), e);
        }
    }

    /* renamed from: com.android.internal.os.RuntimeInit$KillApplicationHandler */
    /* loaded from: classes4.dex */
    private static class KillApplicationHandler implements Thread.UncaughtExceptionHandler {
        private final LoggingHandler mLoggingHandler;

        public KillApplicationHandler(LoggingHandler loggingHandler) {
            this.mLoggingHandler = (LoggingHandler) Objects.requireNonNull(loggingHandler);
        }

        @Override // java.lang.Thread.UncaughtExceptionHandler
        public void uncaughtException(Thread t, Throwable e) {
            try {
                ensureLogging(t, e);
            } catch (Throwable t2) {
                try {
                    if (!(t2 instanceof DeadObjectException)) {
                        try {
                            RuntimeInit.Clog_e(RuntimeInit.TAG, "Error reporting crash", t2);
                        } catch (Throwable th) {
                        }
                    }
                } finally {
                    Process.killProcess(Process.myPid());
                    System.exit(10);
                }
            }
            if (RuntimeInit.mCrashing) {
                return;
            }
            boolean unused = RuntimeInit.mCrashing = true;
            if (ActivityThread.currentActivityThread() != null) {
                ActivityThread.currentActivityThread().stopProfiling();
            }
            ActivityManager.getService().handleApplicationCrash(RuntimeInit.mApplicationObject, new ApplicationErrorReport.ParcelableCrashInfo(e));
        }

        private void ensureLogging(Thread t, Throwable e) {
            if (!this.mLoggingHandler.mTriggered) {
                try {
                    this.mLoggingHandler.uncaughtException(t, e);
                } catch (Throwable th) {
                }
            }
        }
    }

    @UnsupportedAppUsage
    protected static final void commonInit() {
        LoggingHandler loggingHandler = new LoggingHandler();
        RuntimeHooks.setUncaughtExceptionPreHandler(loggingHandler);
        Thread.setDefaultUncaughtExceptionHandler(new KillApplicationHandler(loggingHandler));
        RuntimeHooks.setTimeZoneIdSupplier(new Supplier() { // from class: com.android.internal.os.-$$Lambda$RuntimeInit$ep4ioD9YINkHI5Q1wZ0N_7VFAOg
            @Override // java.util.function.Supplier
            public final Object get() {
                String str;
                str = SystemProperties.get("persist.sys.timezone");
                return str;
            }
        });
        LogManager.getLogManager().reset();
        new AndroidConfig();
        String userAgent = getDefaultUserAgent();
        System.setProperty("http.agent", userAgent);
        NetworkManagementSocketTagger.install();
        String trace = SystemProperties.get("ro.kernel.android.tracing");
        if (trace.equals("1")) {
            Slog.m54i(TAG, "NOTE: emulator trace profiling enabled");
            Debug.enableEmulatorTraceOutput();
        }
        initialized = true;
    }

    private static String getDefaultUserAgent() {
        StringBuilder result = new StringBuilder(64);
        result.append("Dalvik/");
        result.append(System.getProperty("java.vm.version"));
        result.append(" (Linux; U; Android ");
        String version = Build.VERSION.RELEASE;
        result.append(version.length() > 0 ? version : BuildConfig.VERSION_NAME);
        if ("REL".equals(Build.VERSION.CODENAME)) {
            String model = Build.MODEL;
            if (model.length() > 0) {
                result.append("; ");
                result.append(model);
            }
        }
        String model2 = Build.f137ID;
        if (model2.length() > 0) {
            result.append(" Build/");
            result.append(model2);
        }
        result.append(")");
        return result.toString();
    }

    protected static Runnable findStaticMain(String className, String[] argv, ClassLoader classLoader) {
        try {
            Class<?> cl = Class.forName(className, true, classLoader);
            try {
                Method m = cl.getMethod("main", String[].class);
                int modifiers = m.getModifiers();
                if (!Modifier.isStatic(modifiers) || !Modifier.isPublic(modifiers)) {
                    throw new RuntimeException("Main method is not public and static on " + className);
                }
                return new MethodAndArgsCaller(m, argv);
            } catch (NoSuchMethodException ex) {
                throw new RuntimeException("Missing static main on " + className, ex);
            } catch (SecurityException ex2) {
                throw new RuntimeException("Problem getting static main on " + className, ex2);
            }
        } catch (ClassNotFoundException ex3) {
            throw new RuntimeException("Missing class when invoking static main " + className, ex3);
        }
    }

    @UnsupportedAppUsage
    public static final void main(String[] argv) {
        enableDdms();
        if (argv.length == 2 && argv[1].equals("application")) {
            redirectLogStreams();
        }
        commonInit();
        nativeFinishInit();
    }

    protected static Runnable applicationInit(int targetSdkVersion, String[] argv, ClassLoader classLoader) {
        nativeSetExitWithoutCleanup(true);
        VMRuntime.getRuntime().setTargetHeapUtilization(0.75f);
        VMRuntime.getRuntime().setTargetSdkVersion(targetSdkVersion);
        Arguments args = new Arguments(argv);
        Trace.traceEnd(64L);
        return findStaticMain(args.startClass, args.startArgs, classLoader);
    }

    public static void redirectLogStreams() {
        System.out.close();
        System.setOut(new AndroidPrintStream(4, "System.out"));
        System.err.close();
        System.setErr(new AndroidPrintStream(5, "System.err"));
    }

    public static void wtf(String tag, Throwable t, boolean system) {
        try {
            if (ActivityManager.getService().handleApplicationWtf(mApplicationObject, tag, system, new ApplicationErrorReport.ParcelableCrashInfo(t))) {
                Process.killProcess(Process.myPid());
                System.exit(10);
            }
        } catch (Throwable t2) {
            if (!(t2 instanceof DeadObjectException)) {
                Slog.m55e(TAG, "Error reporting WTF", t2);
                Slog.m55e(TAG, "Original WTF:", t);
            }
        }
    }

    public static final void setApplicationObject(IBinder app) {
        mApplicationObject = app;
    }

    @UnsupportedAppUsage
    public static final IBinder getApplicationObject() {
        return mApplicationObject;
    }

    static final void enableDdms() {
        DdmRegister.registerHandlers();
    }

    /* renamed from: com.android.internal.os.RuntimeInit$Arguments */
    /* loaded from: classes4.dex */
    static class Arguments {
        String[] startArgs;
        String startClass;

        Arguments(String[] args) throws IllegalArgumentException {
            parseArgs(args);
        }

        private void parseArgs(String[] args) throws IllegalArgumentException {
            int curArg = 0;
            while (true) {
                if (curArg >= args.length) {
                    break;
                }
                String arg = args[curArg];
                if (arg.equals("--")) {
                    curArg++;
                    break;
                } else if (!arg.startsWith("--")) {
                    break;
                } else {
                    curArg++;
                }
            }
            if (curArg == args.length) {
                throw new IllegalArgumentException("Missing classname argument to RuntimeInit!");
            }
            int curArg2 = curArg + 1;
            this.startClass = args[curArg];
            this.startArgs = new String[args.length - curArg2];
            System.arraycopy(args, curArg2, this.startArgs, 0, this.startArgs.length);
        }
    }

    /* renamed from: com.android.internal.os.RuntimeInit$MethodAndArgsCaller */
    /* loaded from: classes4.dex */
    static class MethodAndArgsCaller implements Runnable {
        private final String[] mArgs;
        private final Method mMethod;

        public MethodAndArgsCaller(Method method, String[] args) {
            this.mMethod = method;
            this.mArgs = args;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                this.mMethod.invoke(null, this.mArgs);
            } catch (IllegalAccessException ex) {
                throw new RuntimeException(ex);
            } catch (InvocationTargetException ex2) {
                Throwable cause = ex2.getCause();
                if (cause instanceof RuntimeException) {
                    throw ((RuntimeException) cause);
                }
                if (cause instanceof Error) {
                    throw ((Error) cause);
                }
                throw new RuntimeException(ex2);
            }
        }
    }
}
