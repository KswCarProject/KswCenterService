package android.app;

import android.annotation.UnsupportedAppUsage;
import android.app.Activity;
import android.app.ActivityThread;
import android.app.IApplicationThread;
import android.app.assist.AssistContent;
import android.app.assist.AssistStructure;
import android.app.backup.BackupAgent;
import android.app.backup.FullBackup;
import android.app.servertransaction.ActivityLifecycleItem;
import android.app.servertransaction.ActivityRelaunchItem;
import android.app.servertransaction.ActivityResultItem;
import android.app.servertransaction.ClientTransaction;
import android.app.servertransaction.ClientTransactionItem;
import android.app.servertransaction.PendingTransactionActions;
import android.app.servertransaction.TransactionExecutor;
import android.app.servertransaction.TransactionExecutorHelper;
import android.app.slice.Slice;
import android.content.AutofillOptions;
import android.content.BroadcastReceiver;
import android.content.ComponentCallbacks2;
import android.content.ComponentName;
import android.content.ContentCaptureOptions;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.Context;
import android.content.IContentProvider;
import android.content.IIntentReceiver;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageManager;
import android.content.pm.InstrumentationInfo;
import android.content.pm.ParceledListSlice;
import android.content.pm.ProviderInfo;
import android.content.pm.ServiceInfo;
import android.content.res.AssetManager;
import android.content.res.CompatibilityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDebug;
import android.ddm.DdmHandleAppName;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.HardwareRenderer;
import android.hardware.display.DisplayManagerGlobal;
import android.net.ConnectivityManager;
import android.net.Proxy;
import android.net.TrafficStats;
import android.net.Uri;
import android.net.wifi.WifiEnterpriseConfig;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Debug;
import android.os.Environment;
import android.os.FileUtils;
import android.os.GraphicsEnvironment;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.IBinder;
import android.os.ICancellationSignal;
import android.os.LocaleList;
import android.os.Looper;
import android.os.Message;
import android.os.MessageQueue;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.PersistableBundle;
import android.os.Process;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.StrictMode;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.Trace;
import android.os.UserHandle;
import android.provider.BlockedNumberContract;
import android.provider.CalendarContract;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.provider.DeviceConfig;
import android.provider.Downloads;
import android.provider.Settings;
import android.renderscript.RenderScriptCacheDir;
import android.security.NetworkSecurityPolicy;
import android.system.ErrnoException;
import android.system.OsConstants;
import android.system.StructStat;
import android.util.AndroidRuntimeException;
import android.util.ArrayMap;
import android.util.DisplayMetrics;
import android.util.EventLog;
import android.util.Log;
import android.util.MergedConfiguration;
import android.util.Pair;
import android.util.PrintWriterPrinter;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.util.SuperNotCalledException;
import android.util.proto.ProtoOutputStream;
import android.view.Choreographer;
import android.view.ContextThemeWrapper;
import android.view.ThreadedRenderer;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewManager;
import android.view.ViewRootImpl;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManagerGlobal;
import android.webkit.WebView;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.app.IVoiceInteractor;
import com.android.internal.content.ReferrerIntent;
import com.android.internal.os.BinderInternal;
import com.android.internal.os.RuntimeInit;
import com.android.internal.os.SomeArgs;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.FastPrintWriter;
import com.android.internal.util.Preconditions;
import com.android.internal.util.function.pooled.PooledLambda;
import com.android.org.conscrypt.OpenSSLSocketImpl;
import com.android.org.conscrypt.TrustedCertificateStore;
import com.ibm.icu.text.PluralRules;
import dalvik.system.CloseGuard;
import dalvik.system.VMDebug;
import dalvik.system.VMRuntime;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.net.InetAddress;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.TimeZone;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import libcore.io.ForwardingOs;
import libcore.io.IoUtils;
import libcore.io.Os;
import libcore.net.event.NetworkEventDispatcher;

public final class ActivityThread extends ClientTransactionHandler {
    private static final int ACTIVITY_THREAD_CHECKIN_VERSION = 4;
    private static final long CONTENT_PROVIDER_RETAIN_TIME = 1000;
    private static final boolean DEBUG_BACKUP = false;
    public static final boolean DEBUG_BROADCAST = false;
    public static final boolean DEBUG_CONFIGURATION = false;
    public static final boolean DEBUG_MEMORY_TRIM = false;
    static final boolean DEBUG_MESSAGES = false;
    public static final boolean DEBUG_ORDER = false;
    private static final boolean DEBUG_PROVIDER = false;
    private static final boolean DEBUG_RESULTS = false;
    private static final boolean DEBUG_SERVICE = false;
    private static final String HEAP_COLUMN = "%13s %8s %8s %8s %8s %8s %8s %8s";
    private static final String HEAP_FULL_COLUMN = "%13s %8s %8s %8s %8s %8s %8s %8s %8s %8s %8s";
    public static final long INVALID_PROC_STATE_SEQ = -1;
    private static final long MIN_TIME_BETWEEN_GCS = 5000;
    private static final String ONE_COUNT_COLUMN = "%21s %8d";
    private static final String ONE_COUNT_COLUMN_HEADER = "%21s %8s";
    private static final long PENDING_TOP_PROCESS_STATE_TIMEOUT = 1000;
    public static final String PROC_START_SEQ_IDENT = "seq=";
    private static final boolean REPORT_TO_ACTIVITY = true;
    public static final int SERVICE_DONE_EXECUTING_ANON = 0;
    public static final int SERVICE_DONE_EXECUTING_START = 1;
    public static final int SERVICE_DONE_EXECUTING_STOP = 2;
    private static final int SQLITE_MEM_RELEASED_EVENT_LOG_TAG = 75003;
    public static final String TAG = "ActivityThread";
    private static final Bitmap.Config THUMBNAIL_FORMAT = Bitmap.Config.RGB_565;
    private static final String TWO_COUNT_COLUMNS = "%21s %8d %21s %8d";
    private static final int VM_PROCESS_STATE_JANK_IMPERCEPTIBLE = 1;
    private static final int VM_PROCESS_STATE_JANK_PERCEPTIBLE = 0;
    static final boolean localLOGV = false;
    @UnsupportedAppUsage
    private static volatile ActivityThread sCurrentActivityThread;
    private static final ThreadLocal<Intent> sCurrentBroadcastIntent = new ThreadLocal<>();
    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
    static volatile Handler sMainThreadHandler;
    @UnsupportedAppUsage
    static volatile IPackageManager sPackageManager;
    @UnsupportedAppUsage
    final ArrayMap<IBinder, ActivityClientRecord> mActivities = new ArrayMap<>();
    final Map<IBinder, ClientTransactionItem> mActivitiesToBeDestroyed = Collections.synchronizedMap(new ArrayMap());
    @UnsupportedAppUsage
    final ArrayList<Application> mAllApplications = new ArrayList<>();
    @UnsupportedAppUsage
    final ApplicationThread mAppThread = new ApplicationThread();
    private final SparseArray<ArrayMap<String, BackupAgent>> mBackupAgentsByUser = new SparseArray<>();
    @UnsupportedAppUsage
    AppBindData mBoundApplication;
    Configuration mCompatConfiguration;
    @UnsupportedAppUsage
    Configuration mConfiguration;
    Bundle mCoreSettings = null;
    @UnsupportedAppUsage
    int mCurDefaultDisplayDpi;
    @UnsupportedAppUsage
    boolean mDensityCompatMode;
    final Executor mExecutor = new HandlerExecutor(this.mH);
    final GcIdler mGcIdler = new GcIdler();
    boolean mGcIdlerScheduled = false;
    @GuardedBy({"mGetProviderLocks"})
    final ArrayMap<ProviderKey, Object> mGetProviderLocks = new ArrayMap<>();
    @UnsupportedAppUsage
    final H mH = new H();
    boolean mHiddenApiWarningShown = false;
    @UnsupportedAppUsage
    Application mInitialApplication;
    @UnsupportedAppUsage
    Instrumentation mInstrumentation;
    @UnsupportedAppUsage
    String mInstrumentationAppDir = null;
    String mInstrumentationLibDir = null;
    String mInstrumentationPackageName = null;
    String[] mInstrumentationSplitAppDirs = null;
    @UnsupportedAppUsage
    String mInstrumentedAppDir = null;
    String mInstrumentedLibDir = null;
    String[] mInstrumentedSplitAppDirs = null;
    ArrayList<WeakReference<AssistStructure>> mLastAssistStructures = new ArrayList<>();
    @GuardedBy({"mAppThread"})
    private int mLastProcessState = -1;
    private int mLastSessionId;
    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
    final ArrayMap<IBinder, ProviderClientRecord> mLocalProviders = new ArrayMap<>();
    @UnsupportedAppUsage
    final ArrayMap<ComponentName, ProviderClientRecord> mLocalProvidersByName = new ArrayMap<>();
    @UnsupportedAppUsage
    final Looper mLooper = Looper.myLooper();
    private Configuration mMainThreadConfig = new Configuration();
    /* access modifiers changed from: private */
    @GuardedBy({"mNetworkPolicyLock"})
    public long mNetworkBlockSeq = -1;
    /* access modifiers changed from: private */
    public final Object mNetworkPolicyLock = new Object();
    ActivityClientRecord mNewActivities = null;
    private final AtomicInteger mNumLaunchingActivities = new AtomicInteger();
    @UnsupportedAppUsage
    int mNumVisibleActivities = 0;
    final ArrayMap<Activity, ArrayList<OnActivityPausedListener>> mOnPauseListeners = new ArrayMap<>();
    @GuardedBy({"mResourcesManager"})
    @UnsupportedAppUsage
    final ArrayMap<String, WeakReference<LoadedApk>> mPackages = new ArrayMap<>();
    @GuardedBy({"mResourcesManager"})
    @UnsupportedAppUsage
    Configuration mPendingConfiguration = null;
    @GuardedBy({"mAppThread"})
    private int mPendingProcessState = -1;
    Profiler mProfiler;
    @UnsupportedAppUsage
    final ArrayMap<ProviderKey, ProviderClientRecord> mProviderMap = new ArrayMap<>();
    @UnsupportedAppUsage
    final ArrayMap<IBinder, ProviderRefCount> mProviderRefCountMap = new ArrayMap<>();
    final PurgeIdler mPurgeIdler = new PurgeIdler();
    boolean mPurgeIdlerScheduled = false;
    @GuardedBy({"mResourcesManager"})
    final ArrayList<ActivityClientRecord> mRelaunchingActivities = new ArrayList<>();
    @GuardedBy({"this"})
    private Map<SafeCancellationTransport, CancellationSignal> mRemoteCancellations;
    @GuardedBy({"mResourcesManager"})
    @UnsupportedAppUsage
    final ArrayMap<String, WeakReference<LoadedApk>> mResourcePackages = new ArrayMap<>();
    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
    private final ResourcesManager mResourcesManager = ResourcesManager.getInstance();
    @UnsupportedAppUsage
    final ArrayMap<IBinder, Service> mServices = new ArrayMap<>();
    boolean mSomeActivitiesChanged = false;
    @UnsupportedAppUsage
    private ContextImpl mSystemContext;
    boolean mSystemThread = false;
    private ContextImpl mSystemUiContext;
    /* access modifiers changed from: private */
    public final TransactionExecutor mTransactionExecutor = new TransactionExecutor(this);
    boolean mUpdatingSystemConfig = false;

    /* access modifiers changed from: private */
    public native void nDumpGraphicsInfo(FileDescriptor fileDescriptor);

    private native void nInitZygoteChildHeapProfiling();

    private native void nPurgePendingResources();

    private static final class ProviderKey {
        final String authority;
        final int userId;

        public ProviderKey(String authority2, int userId2) {
            this.authority = authority2;
            this.userId = userId2;
        }

        public boolean equals(Object o) {
            if (!(o instanceof ProviderKey)) {
                return false;
            }
            ProviderKey other = (ProviderKey) o;
            if (!Objects.equals(this.authority, other.authority) || this.userId != other.userId) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            return (this.authority != null ? this.authority.hashCode() : 0) ^ this.userId;
        }
    }

    public static final class ActivityClientRecord {
        @UnsupportedAppUsage
        Activity activity;
        @UnsupportedAppUsage
        ActivityInfo activityInfo;
        public IBinder assistToken;
        @UnsupportedAppUsage
        CompatibilityInfo compatInfo;
        ViewRootImpl.ActivityConfigCallback configCallback;
        Configuration createdConfig;
        String embeddedID;
        boolean hideForNow;
        int ident;
        @UnsupportedAppUsage
        Intent intent;
        public final boolean isForward;
        boolean isTopResumedActivity;
        Activity.NonConfigurationInstances lastNonConfigurationInstances;
        boolean lastReportedTopResumedState;
        private int mLifecycleState;
        /* access modifiers changed from: private */
        @GuardedBy({"this"})
        public Configuration mPendingOverrideConfig;
        Window mPendingRemoveWindow;
        WindowManager mPendingRemoveWindowManager;
        @UnsupportedAppUsage
        boolean mPreserveWindow;
        Configuration newConfig;
        ActivityClientRecord nextIdle;
        Configuration overrideConfig;
        @UnsupportedAppUsage
        public LoadedApk packageInfo;
        Activity parent;
        @UnsupportedAppUsage
        boolean paused;
        int pendingConfigChanges;
        List<ReferrerIntent> pendingIntents;
        List<ResultInfo> pendingResults;
        PersistableBundle persistentState;
        ProfilerInfo profilerInfo;
        String referrer;
        boolean startsNotResumed;
        Bundle state;
        @UnsupportedAppUsage
        boolean stopped;
        /* access modifiers changed from: private */
        public Configuration tmpConfig;
        @UnsupportedAppUsage
        public IBinder token;
        IVoiceInteractor voiceInteractor;
        Window window;

        @VisibleForTesting
        @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
        public ActivityClientRecord() {
            this.tmpConfig = new Configuration();
            this.mLifecycleState = 0;
            this.isForward = false;
            init();
        }

        public ActivityClientRecord(IBinder token2, Intent intent2, int ident2, ActivityInfo info, Configuration overrideConfig2, CompatibilityInfo compatInfo2, String referrer2, IVoiceInteractor voiceInteractor2, Bundle state2, PersistableBundle persistentState2, List<ResultInfo> pendingResults2, List<ReferrerIntent> pendingNewIntents, boolean isForward2, ProfilerInfo profilerInfo2, ClientTransactionHandler client, IBinder assistToken2) {
            CompatibilityInfo compatibilityInfo = compatInfo2;
            this.tmpConfig = new Configuration();
            this.mLifecycleState = 0;
            this.token = token2;
            this.assistToken = assistToken2;
            this.ident = ident2;
            this.intent = intent2;
            this.referrer = referrer2;
            this.voiceInteractor = voiceInteractor2;
            this.activityInfo = info;
            this.compatInfo = compatibilityInfo;
            this.state = state2;
            this.persistentState = persistentState2;
            this.pendingResults = pendingResults2;
            this.pendingIntents = pendingNewIntents;
            this.isForward = isForward2;
            this.profilerInfo = profilerInfo2;
            this.overrideConfig = overrideConfig2;
            this.packageInfo = client.getPackageInfoNoCheck(this.activityInfo.applicationInfo, compatibilityInfo);
            init();
        }

        private void init() {
            this.parent = null;
            this.embeddedID = null;
            this.paused = false;
            this.stopped = false;
            this.hideForNow = false;
            this.nextIdle = null;
            this.configCallback = new ViewRootImpl.ActivityConfigCallback() {
                public final void onConfigurationChanged(Configuration configuration, int i) {
                    ActivityThread.ActivityClientRecord.lambda$init$0(ActivityThread.ActivityClientRecord.this, configuration, i);
                }
            };
        }

        public static /* synthetic */ void lambda$init$0(ActivityClientRecord activityClientRecord, Configuration overrideConfig2, int newDisplayId) {
            if (activityClientRecord.activity != null) {
                activityClientRecord.activity.mMainThread.handleActivityConfigurationChanged(activityClientRecord.token, overrideConfig2, newDisplayId);
                return;
            }
            throw new IllegalStateException("Received config update for non-existing activity");
        }

        public int getLifecycleState() {
            return this.mLifecycleState;
        }

        public void setState(int newLifecycleState) {
            this.mLifecycleState = newLifecycleState;
            switch (this.mLifecycleState) {
                case 1:
                    this.paused = true;
                    this.stopped = true;
                    return;
                case 2:
                    this.paused = true;
                    this.stopped = false;
                    return;
                case 3:
                    this.paused = false;
                    this.stopped = false;
                    return;
                case 4:
                    this.paused = true;
                    this.stopped = false;
                    return;
                case 5:
                    this.paused = true;
                    this.stopped = true;
                    return;
                default:
                    return;
            }
        }

        /* access modifiers changed from: private */
        public boolean isPreHoneycomb() {
            return this.activity != null && this.activity.getApplicationInfo().targetSdkVersion < 11;
        }

        /* access modifiers changed from: private */
        public boolean isPreP() {
            return this.activity != null && this.activity.getApplicationInfo().targetSdkVersion < 28;
        }

        public boolean isPersistable() {
            return this.activityInfo.persistableMode == 2;
        }

        public boolean isVisibleFromServer() {
            return this.activity != null && this.activity.mVisibleFromServer;
        }

        public String toString() {
            ComponentName componentName = this.intent != null ? this.intent.getComponent() : null;
            StringBuilder sb = new StringBuilder();
            sb.append("ActivityRecord{");
            sb.append(Integer.toHexString(System.identityHashCode(this)));
            sb.append(" token=");
            sb.append(this.token);
            sb.append(WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER);
            sb.append(componentName == null ? "no component name" : componentName.toShortString());
            sb.append("}");
            return sb.toString();
        }

        public String getStateString() {
            StringBuilder sb = new StringBuilder();
            sb.append("ActivityClientRecord{");
            sb.append("paused=");
            sb.append(this.paused);
            sb.append(", stopped=");
            sb.append(this.stopped);
            sb.append(", hideForNow=");
            sb.append(this.hideForNow);
            sb.append(", startsNotResumed=");
            sb.append(this.startsNotResumed);
            sb.append(", isForward=");
            sb.append(this.isForward);
            sb.append(", pendingConfigChanges=");
            sb.append(this.pendingConfigChanges);
            sb.append(", preserveWindow=");
            sb.append(this.mPreserveWindow);
            if (this.activity != null) {
                sb.append(", Activity{");
                sb.append("resumed=");
                sb.append(this.activity.mResumed);
                sb.append(", stopped=");
                sb.append(this.activity.mStopped);
                sb.append(", finished=");
                sb.append(this.activity.isFinishing());
                sb.append(", destroyed=");
                sb.append(this.activity.isDestroyed());
                sb.append(", startedActivity=");
                sb.append(this.activity.mStartedActivity);
                sb.append(", changingConfigurations=");
                sb.append(this.activity.mChangingConfigurations);
                sb.append("}");
            }
            sb.append("}");
            return sb.toString();
        }
    }

    final class ProviderClientRecord {
        @UnsupportedAppUsage
        final ContentProviderHolder mHolder;
        @UnsupportedAppUsage
        final ContentProvider mLocalProvider;
        final String[] mNames;
        @UnsupportedAppUsage
        final IContentProvider mProvider;

        ProviderClientRecord(String[] names, IContentProvider provider, ContentProvider localProvider, ContentProviderHolder holder) {
            this.mNames = names;
            this.mProvider = provider;
            this.mLocalProvider = localProvider;
            this.mHolder = holder;
        }
    }

    static final class ReceiverData extends BroadcastReceiver.PendingResult {
        @UnsupportedAppUsage
        CompatibilityInfo compatInfo;
        @UnsupportedAppUsage
        ActivityInfo info;
        @UnsupportedAppUsage
        Intent intent;

        public ReceiverData(Intent intent2, int resultCode, String resultData, Bundle resultExtras, boolean ordered, boolean sticky, IBinder token, int sendingUser) {
            super(resultCode, resultData, resultExtras, 0, ordered, sticky, token, sendingUser, intent2.getFlags());
            this.intent = intent2;
        }

        public String toString() {
            return "ReceiverData{intent=" + this.intent + " packageName=" + this.info.packageName + " resultCode=" + getResultCode() + " resultData=" + getResultData() + " resultExtras=" + getResultExtras(false) + "}";
        }
    }

    static final class CreateBackupAgentData {
        ApplicationInfo appInfo;
        int backupMode;
        CompatibilityInfo compatInfo;
        int userId;

        CreateBackupAgentData() {
        }

        public String toString() {
            return "CreateBackupAgentData{appInfo=" + this.appInfo + " backupAgent=" + this.appInfo.backupAgentName + " mode=" + this.backupMode + " userId=" + this.userId + "}";
        }
    }

    static final class CreateServiceData {
        @UnsupportedAppUsage
        CompatibilityInfo compatInfo;
        @UnsupportedAppUsage
        ServiceInfo info;
        @UnsupportedAppUsage
        Intent intent;
        @UnsupportedAppUsage
        IBinder token;

        CreateServiceData() {
        }

        public String toString() {
            return "CreateServiceData{token=" + this.token + " className=" + this.info.name + " packageName=" + this.info.packageName + " intent=" + this.intent + "}";
        }
    }

    static final class BindServiceData {
        @UnsupportedAppUsage
        Intent intent;
        boolean rebind;
        @UnsupportedAppUsage
        IBinder token;

        BindServiceData() {
        }

        public String toString() {
            return "BindServiceData{token=" + this.token + " intent=" + this.intent + "}";
        }
    }

    static final class ServiceArgsData {
        @UnsupportedAppUsage
        Intent args;
        int flags;
        int startId;
        boolean taskRemoved;
        @UnsupportedAppUsage
        IBinder token;

        ServiceArgsData() {
        }

        public String toString() {
            return "ServiceArgsData{token=" + this.token + " startId=" + this.startId + " args=" + this.args + "}";
        }
    }

    static final class AppBindData {
        @UnsupportedAppUsage
        ApplicationInfo appInfo;
        AutofillOptions autofillOptions;
        String buildSerial;
        @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
        CompatibilityInfo compatInfo;
        Configuration config;
        ContentCaptureOptions contentCaptureOptions;
        int debugMode;
        boolean enableBinderTracking;
        @UnsupportedAppUsage
        LoadedApk info;
        ProfilerInfo initProfilerInfo;
        @UnsupportedAppUsage
        Bundle instrumentationArgs;
        ComponentName instrumentationName;
        IUiAutomationConnection instrumentationUiAutomationConnection;
        IInstrumentationWatcher instrumentationWatcher;
        @UnsupportedAppUsage
        boolean persistent;
        @UnsupportedAppUsage
        String processName;
        @UnsupportedAppUsage
        List<ProviderInfo> providers;
        @UnsupportedAppUsage
        boolean restrictedBackupMode;
        boolean trackAllocation;

        AppBindData() {
        }

        public String toString() {
            return "AppBindData{appInfo=" + this.appInfo + "}";
        }
    }

    static final class Profiler {
        boolean autoStopProfiler;
        boolean handlingProfiling;
        ParcelFileDescriptor profileFd;
        String profileFile;
        boolean profiling;
        int samplingInterval;
        boolean streamingOutput;

        Profiler() {
        }

        public void setProfiler(ProfilerInfo profilerInfo) {
            ParcelFileDescriptor fd = profilerInfo.profileFd;
            if (!this.profiling) {
                if (this.profileFd != null) {
                    try {
                        this.profileFd.close();
                    } catch (IOException e) {
                    }
                }
                this.profileFile = profilerInfo.profileFile;
                this.profileFd = fd;
                this.samplingInterval = profilerInfo.samplingInterval;
                this.autoStopProfiler = profilerInfo.autoStopProfiler;
                this.streamingOutput = profilerInfo.streamingOutput;
            } else if (fd != null) {
                try {
                    fd.close();
                } catch (IOException e2) {
                }
            }
        }

        public void startProfiling() {
            if (this.profileFd != null && !this.profiling) {
                try {
                    VMDebug.startMethodTracing(this.profileFile, this.profileFd.getFileDescriptor(), SystemProperties.getInt("debug.traceview-buffer-size-mb", 8) * 1024 * 1024, 0, this.samplingInterval != 0, this.samplingInterval, this.streamingOutput);
                    this.profiling = true;
                } catch (RuntimeException e) {
                    Slog.w(ActivityThread.TAG, "Profiling failed on path " + this.profileFile, e);
                    try {
                        this.profileFd.close();
                        this.profileFd = null;
                    } catch (IOException e2) {
                        Slog.w(ActivityThread.TAG, "Failure closing profile fd", e2);
                    }
                }
            }
        }

        public void stopProfiling() {
            if (this.profiling) {
                this.profiling = false;
                Debug.stopMethodTracing();
                if (this.profileFd != null) {
                    try {
                        this.profileFd.close();
                    } catch (IOException e) {
                    }
                }
                this.profileFd = null;
                this.profileFile = null;
            }
        }
    }

    static final class DumpComponentInfo {
        String[] args;
        ParcelFileDescriptor fd;
        String prefix;
        IBinder token;

        DumpComponentInfo() {
        }
    }

    static final class ContextCleanupInfo {
        ContextImpl context;
        String what;
        String who;

        ContextCleanupInfo() {
        }
    }

    static final class DumpHeapData {
        ParcelFileDescriptor fd;
        RemoteCallback finishCallback;
        public boolean mallocInfo;
        public boolean managed;
        String path;
        public boolean runGc;

        DumpHeapData() {
        }
    }

    static final class UpdateCompatibilityData {
        CompatibilityInfo info;
        String pkg;

        UpdateCompatibilityData() {
        }
    }

    static final class RequestAssistContextExtras {
        IBinder activityToken;
        int flags;
        IBinder requestToken;
        int requestType;
        int sessionId;

        RequestAssistContextExtras() {
        }
    }

    private class ApplicationThread extends IApplicationThread.Stub {
        private static final String DB_INFO_FORMAT = "  %8s %8s %14s %14s  %s";

        private ApplicationThread() {
        }

        public final void scheduleSleeping(IBinder token, boolean sleeping) {
            ActivityThread.this.sendMessage(137, token, sleeping);
        }

        public final void scheduleReceiver(Intent intent, ActivityInfo info, CompatibilityInfo compatInfo, int resultCode, String data, Bundle extras, boolean sync, int sendingUser, int processState) {
            ActivityThread.this.updateProcessState(processState, false);
            ReceiverData receiverData = new ReceiverData(intent, resultCode, data, extras, sync, false, ActivityThread.this.mAppThread.asBinder(), sendingUser);
            receiverData.info = info;
            receiverData.compatInfo = compatInfo;
            ActivityThread.this.sendMessage(113, receiverData);
        }

        public final void scheduleCreateBackupAgent(ApplicationInfo app, CompatibilityInfo compatInfo, int backupMode, int userId) {
            CreateBackupAgentData d = new CreateBackupAgentData();
            d.appInfo = app;
            d.compatInfo = compatInfo;
            d.backupMode = backupMode;
            d.userId = userId;
            ActivityThread.this.sendMessage(128, d);
        }

        public final void scheduleDestroyBackupAgent(ApplicationInfo app, CompatibilityInfo compatInfo, int userId) {
            CreateBackupAgentData d = new CreateBackupAgentData();
            d.appInfo = app;
            d.compatInfo = compatInfo;
            d.userId = userId;
            ActivityThread.this.sendMessage(129, d);
        }

        public final void scheduleCreateService(IBinder token, ServiceInfo info, CompatibilityInfo compatInfo, int processState) {
            ActivityThread.this.updateProcessState(processState, false);
            CreateServiceData s = new CreateServiceData();
            s.token = token;
            s.info = info;
            s.compatInfo = compatInfo;
            ActivityThread.this.sendMessage(114, s);
        }

        public final void scheduleBindService(IBinder token, Intent intent, boolean rebind, int processState) {
            ActivityThread.this.updateProcessState(processState, false);
            BindServiceData s = new BindServiceData();
            s.token = token;
            s.intent = intent;
            s.rebind = rebind;
            ActivityThread.this.sendMessage(121, s);
        }

        public final void scheduleUnbindService(IBinder token, Intent intent) {
            BindServiceData s = new BindServiceData();
            s.token = token;
            s.intent = intent;
            ActivityThread.this.sendMessage(122, s);
        }

        public final void scheduleServiceArgs(IBinder token, ParceledListSlice args) {
            List<ServiceStartArgs> list = args.getList();
            for (int i = 0; i < list.size(); i++) {
                ServiceStartArgs ssa = list.get(i);
                ServiceArgsData s = new ServiceArgsData();
                s.token = token;
                s.taskRemoved = ssa.taskRemoved;
                s.startId = ssa.startId;
                s.flags = ssa.flags;
                s.args = ssa.args;
                ActivityThread.this.sendMessage(115, s);
            }
        }

        public final void scheduleStopService(IBinder token) {
            ActivityThread.this.sendMessage(116, token);
        }

        public final void bindApplication(String processName, ApplicationInfo appInfo, List<ProviderInfo> providers, ComponentName instrumentationName, ProfilerInfo profilerInfo, Bundle instrumentationArgs, IInstrumentationWatcher instrumentationWatcher, IUiAutomationConnection instrumentationUiConnection, int debugMode, boolean enableBinderTracking, boolean trackAllocation, boolean isRestrictedBackupMode, boolean persistent, Configuration config, CompatibilityInfo compatInfo, Map services, Bundle coreSettings, String buildSerial, AutofillOptions autofillOptions, ContentCaptureOptions contentCaptureOptions) {
            if (services != null) {
                ServiceManager.initServiceCache(services);
            }
            setCoreSettings(coreSettings);
            AppBindData data = new AppBindData();
            data.processName = processName;
            data.appInfo = appInfo;
            data.providers = providers;
            data.instrumentationName = instrumentationName;
            data.instrumentationArgs = instrumentationArgs;
            data.instrumentationWatcher = instrumentationWatcher;
            data.instrumentationUiAutomationConnection = instrumentationUiConnection;
            data.debugMode = debugMode;
            data.enableBinderTracking = enableBinderTracking;
            data.trackAllocation = trackAllocation;
            data.restrictedBackupMode = isRestrictedBackupMode;
            data.persistent = persistent;
            data.config = config;
            data.compatInfo = compatInfo;
            data.initProfilerInfo = profilerInfo;
            data.buildSerial = buildSerial;
            data.autofillOptions = autofillOptions;
            data.contentCaptureOptions = contentCaptureOptions;
            ActivityThread.this.sendMessage(110, data);
        }

        public final void runIsolatedEntryPoint(String entryPoint, String[] entryPointArgs) {
            SomeArgs args = SomeArgs.obtain();
            args.arg1 = entryPoint;
            args.arg2 = entryPointArgs;
            ActivityThread.this.sendMessage(158, args);
        }

        public final void scheduleExit() {
            ActivityThread.this.sendMessage(111, (Object) null);
        }

        public final void scheduleSuicide() {
            ActivityThread.this.sendMessage(130, (Object) null);
        }

        public void scheduleApplicationInfoChanged(ApplicationInfo ai) {
            ActivityThread.this.mH.removeMessages(156, ai);
            ActivityThread.this.sendMessage(156, ai);
        }

        public void updateTimeZone() {
            TimeZone.setDefault((TimeZone) null);
        }

        public void clearDnsCache() {
            InetAddress.clearDnsCache();
            NetworkEventDispatcher.getInstance().onNetworkConfigurationChanged();
        }

        public void updateHttpProxy() {
            ActivityThread.updateHttpProxy(ActivityThread.this.getApplication() != null ? ActivityThread.this.getApplication() : ActivityThread.this.getSystemContext());
        }

        public void processInBackground() {
            ActivityThread.this.mH.removeMessages(120);
            ActivityThread.this.mH.sendMessage(ActivityThread.this.mH.obtainMessage(120));
        }

        public void dumpService(ParcelFileDescriptor pfd, IBinder servicetoken, String[] args) {
            DumpComponentInfo data = new DumpComponentInfo();
            try {
                data.fd = pfd.dup();
                data.token = servicetoken;
                data.args = args;
                ActivityThread.this.sendMessage(123, (Object) data, 0, 0, true);
            } catch (IOException e) {
                Slog.w(ActivityThread.TAG, "dumpService failed", e);
            } catch (Throwable th) {
                IoUtils.closeQuietly(pfd);
                throw th;
            }
            IoUtils.closeQuietly(pfd);
        }

        public void scheduleRegisteredReceiver(IIntentReceiver receiver, Intent intent, int resultCode, String dataStr, Bundle extras, boolean ordered, boolean sticky, int sendingUser, int processState) throws RemoteException {
            ActivityThread.this.updateProcessState(processState, false);
            receiver.performReceive(intent, resultCode, dataStr, extras, ordered, sticky, sendingUser);
        }

        public void scheduleLowMemory() {
            ActivityThread.this.sendMessage(124, (Object) null);
        }

        public void profilerControl(boolean start, ProfilerInfo profilerInfo, int profileType) {
            ActivityThread.this.sendMessage(127, profilerInfo, start, profileType);
        }

        public void dumpHeap(boolean managed, boolean mallocInfo, boolean runGc, String path, ParcelFileDescriptor fd, RemoteCallback finishCallback) {
            DumpHeapData dhd = new DumpHeapData();
            dhd.managed = managed;
            dhd.mallocInfo = mallocInfo;
            dhd.runGc = runGc;
            dhd.path = path;
            try {
                dhd.fd = fd.dup();
                dhd.finishCallback = finishCallback;
                ActivityThread.this.sendMessage(135, (Object) dhd, 0, 0, true);
            } catch (IOException e) {
                Slog.e(ActivityThread.TAG, "Failed to duplicate heap dump file descriptor", e);
            }
        }

        public void attachAgent(String agent) {
            ActivityThread.this.sendMessage(155, agent);
        }

        public void setSchedulingGroup(int group) {
            try {
                Process.setProcessGroup(Process.myPid(), group);
            } catch (Exception e) {
                Slog.w(ActivityThread.TAG, "Failed setting process group to " + group, e);
            }
        }

        public void dispatchPackageBroadcast(int cmd, String[] packages) {
            ActivityThread.this.sendMessage(133, packages, cmd);
        }

        public void scheduleCrash(String msg) {
            ActivityThread.this.sendMessage(134, msg);
        }

        public void dumpActivity(ParcelFileDescriptor pfd, IBinder activitytoken, String prefix, String[] args) {
            DumpComponentInfo data = new DumpComponentInfo();
            try {
                data.fd = pfd.dup();
                data.token = activitytoken;
                data.prefix = prefix;
                data.args = args;
                ActivityThread.this.sendMessage(136, (Object) data, 0, 0, true);
            } catch (IOException e) {
                Slog.w(ActivityThread.TAG, "dumpActivity failed", e);
            } catch (Throwable th) {
                IoUtils.closeQuietly(pfd);
                throw th;
            }
            IoUtils.closeQuietly(pfd);
        }

        public void dumpProvider(ParcelFileDescriptor pfd, IBinder providertoken, String[] args) {
            DumpComponentInfo data = new DumpComponentInfo();
            try {
                data.fd = pfd.dup();
                data.token = providertoken;
                data.args = args;
                ActivityThread.this.sendMessage(141, (Object) data, 0, 0, true);
            } catch (IOException e) {
                Slog.w(ActivityThread.TAG, "dumpProvider failed", e);
            } catch (Throwable th) {
                IoUtils.closeQuietly(pfd);
                throw th;
            }
            IoUtils.closeQuietly(pfd);
        }

        public void dumpMemInfo(ParcelFileDescriptor pfd, Debug.MemoryInfo mem, boolean checkin, boolean dumpFullInfo, boolean dumpDalvik, boolean dumpSummaryOnly, boolean dumpUnreachable, String[] args) {
            PrintWriter pw = new FastPrintWriter((OutputStream) new FileOutputStream(pfd.getFileDescriptor()));
            try {
                dumpMemInfo(pw, mem, checkin, dumpFullInfo, dumpDalvik, dumpSummaryOnly, dumpUnreachable);
                pw.flush();
                IoUtils.closeQuietly(pfd);
            } catch (Throwable th) {
                Throwable th2 = th;
                pw.flush();
                IoUtils.closeQuietly(pfd);
                throw th2;
            }
        }

        private void dumpMemInfo(PrintWriter pw, Debug.MemoryInfo memInfo, boolean checkin, boolean dumpFullInfo, boolean dumpDalvik, boolean dumpSummaryOnly, boolean dumpUnreachable) {
            PrintWriter printWriter = pw;
            long nativeMax = Debug.getNativeHeapSize() / 1024;
            long nativeAllocated = Debug.getNativeHeapAllocatedSize() / 1024;
            long nativeFree = Debug.getNativeHeapFreeSize() / 1024;
            Runtime runtime = Runtime.getRuntime();
            runtime.gc();
            long dalvikMax = runtime.totalMemory() / 1024;
            long dalvikFree = runtime.freeMemory() / 1024;
            long dalvikAllocated = dalvikMax - dalvikFree;
            int i = 0;
            Class[] classesToCount = {ContextImpl.class, Activity.class, WebView.class, OpenSSLSocketImpl.class};
            long[] instanceCounts = VMDebug.countInstancesOfClasses(classesToCount, true);
            long appContextInstanceCount = instanceCounts[0];
            long activityInstanceCount = instanceCounts[1];
            long webviewInstanceCount = instanceCounts[2];
            long openSslSocketCount = instanceCounts[3];
            long viewInstanceCount = ViewDebug.getViewInstanceCount();
            long viewRootInstanceCount = ViewDebug.getViewRootImplCount();
            int globalAssetCount = AssetManager.getGlobalAssetCount();
            int globalAssetManagerCount = AssetManager.getGlobalAssetManagerCount();
            int binderLocalObjectCount = Debug.getBinderLocalObjectCount();
            int binderProxyObjectCount = Debug.getBinderProxyObjectCount();
            int binderProxyObjectCount2 = Debug.getBinderDeathObjectCount();
            long parcelSize = Parcel.getGlobalAllocSize();
            long parcelCount = Parcel.getGlobalAllocCount();
            int binderDeathObjectCount = binderProxyObjectCount2;
            SQLiteDebug.PagerStats stats = SQLiteDebug.getDatabaseInfo();
            int myPid = Process.myPid();
            String str = ActivityThread.this.mBoundApplication != null ? ActivityThread.this.mBoundApplication.processName : "unknown";
            long viewRootInstanceCount2 = viewRootInstanceCount;
            long viewInstanceCount2 = viewInstanceCount;
            long openSslSocketCount2 = openSslSocketCount;
            long activityInstanceCount2 = activityInstanceCount;
            long appContextInstanceCount2 = appContextInstanceCount;
            Class[] clsArr = classesToCount;
            boolean showContents = true;
            SQLiteDebug.PagerStats stats2 = stats;
            int globalAssetCount2 = globalAssetCount;
            int globalAssetManagerCount2 = globalAssetManagerCount;
            int binderLocalObjectCount2 = binderLocalObjectCount;
            int binderProxyObjectCount3 = binderProxyObjectCount;
            int binderDeathObjectCount2 = binderDeathObjectCount;
            PrintWriter printWriter2 = pw;
            ActivityThread.dumpMemInfoTable(pw, memInfo, checkin, dumpFullInfo, dumpDalvik, dumpSummaryOnly, myPid, str, nativeMax, nativeAllocated, nativeFree, dalvikMax, dalvikAllocated, dalvikFree);
            if (checkin) {
                printWriter2.print(viewInstanceCount2);
                printWriter2.print(',');
                printWriter2.print(viewRootInstanceCount2);
                printWriter2.print(',');
                printWriter2.print(appContextInstanceCount2);
                printWriter2.print(',');
                printWriter2.print(activityInstanceCount2);
                printWriter2.print(',');
                printWriter2.print(globalAssetCount2);
                printWriter2.print(',');
                int globalAssetManagerCount3 = globalAssetManagerCount2;
                printWriter2.print(globalAssetManagerCount3);
                printWriter2.print(',');
                int binderLocalObjectCount3 = binderLocalObjectCount2;
                printWriter2.print(binderLocalObjectCount3);
                printWriter2.print(',');
                int binderProxyObjectCount4 = binderProxyObjectCount3;
                printWriter2.print(binderProxyObjectCount4);
                printWriter2.print(',');
                int binderDeathObjectCount3 = binderDeathObjectCount2;
                printWriter2.print(binderDeathObjectCount3);
                printWriter2.print(',');
                int i2 = binderProxyObjectCount4;
                int i3 = binderDeathObjectCount3;
                long openSslSocketCount3 = openSslSocketCount2;
                printWriter2.print(openSslSocketCount3);
                printWriter2.print(',');
                SQLiteDebug.PagerStats stats3 = stats2;
                printWriter2.print(stats3.memoryUsed / 1024);
                printWriter2.print(',');
                printWriter2.print(stats3.memoryUsed / 1024);
                printWriter2.print(',');
                printWriter2.print(stats3.pageCacheOverflow / 1024);
                printWriter2.print(',');
                printWriter2.print(stats3.largestMemAlloc / 1024);
                while (true) {
                    int i4 = i;
                    long openSslSocketCount4 = openSslSocketCount3;
                    if (i4 < stats3.dbStats.size()) {
                        SQLiteDebug.DbStats dbStats = stats3.dbStats.get(i4);
                        printWriter2.print(',');
                        printWriter2.print(dbStats.dbName);
                        printWriter2.print(',');
                        printWriter2.print(dbStats.pageSize);
                        printWriter2.print(',');
                        printWriter2.print(dbStats.dbSize);
                        printWriter2.print(',');
                        printWriter2.print(dbStats.lookaside);
                        printWriter2.print(',');
                        printWriter2.print(dbStats.cache);
                        printWriter2.print(',');
                        printWriter2.print(dbStats.cache);
                        i = i4 + 1;
                        openSslSocketCount3 = openSslSocketCount4;
                        globalAssetManagerCount3 = globalAssetManagerCount3;
                        binderLocalObjectCount3 = binderLocalObjectCount3;
                    } else {
                        int i5 = binderLocalObjectCount3;
                        pw.println();
                        return;
                    }
                }
            } else {
                long viewInstanceCount3 = viewInstanceCount2;
                SQLiteDebug.PagerStats stats4 = stats2;
                printWriter2.println(WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER);
                printWriter2.println(" Objects");
                ActivityThread.printRow(printWriter2, ActivityThread.TWO_COUNT_COLUMNS, "Views:", Long.valueOf(viewInstanceCount3), "ViewRootImpl:", Long.valueOf(viewRootInstanceCount2));
                ActivityThread.printRow(printWriter2, ActivityThread.TWO_COUNT_COLUMNS, "AppContexts:", Long.valueOf(appContextInstanceCount2), "Activities:", Long.valueOf(activityInstanceCount2));
                ActivityThread.printRow(printWriter2, ActivityThread.TWO_COUNT_COLUMNS, "Assets:", Integer.valueOf(globalAssetCount2), "AssetManagers:", Integer.valueOf(globalAssetManagerCount2));
                ActivityThread.printRow(printWriter2, ActivityThread.TWO_COUNT_COLUMNS, "Local Binders:", Integer.valueOf(binderLocalObjectCount2), "Proxy Binders:", Integer.valueOf(binderProxyObjectCount3));
                ActivityThread.printRow(printWriter2, ActivityThread.TWO_COUNT_COLUMNS, "Parcel memory:", Long.valueOf(parcelSize / 1024), "Parcel count:", Long.valueOf(parcelCount));
                ActivityThread.printRow(printWriter2, ActivityThread.TWO_COUNT_COLUMNS, "Death Recipients:", Integer.valueOf(binderDeathObjectCount2), "OpenSSL Sockets:", Long.valueOf(openSslSocketCount2));
                ActivityThread.printRow(printWriter2, ActivityThread.ONE_COUNT_COLUMN, "WebViews:", Long.valueOf(webviewInstanceCount));
                printWriter2.println(WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER);
                printWriter2.println(" SQL");
                ActivityThread.printRow(printWriter2, ActivityThread.ONE_COUNT_COLUMN, "MEMORY_USED:", Integer.valueOf(stats4.memoryUsed / 1024));
                ActivityThread.printRow(printWriter2, ActivityThread.TWO_COUNT_COLUMNS, "PAGECACHE_OVERFLOW:", Integer.valueOf(stats4.pageCacheOverflow / 1024), "MALLOC_SIZE:", Integer.valueOf(stats4.largestMemAlloc / 1024));
                printWriter2.println(WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER);
                int N = stats4.dbStats.size();
                if (N > 0) {
                    printWriter2.println(" DATABASES");
                    ActivityThread.printRow(printWriter2, DB_INFO_FORMAT, "pgsz", "dbsz", "Lookaside(b)", "cache", "Dbname");
                    int i6 = 0;
                    while (i6 < N) {
                        SQLiteDebug.DbStats dbStats2 = stats4.dbStats.get(i6);
                        int N2 = N;
                        Object[] objArr = new Object[5];
                        long viewInstanceCount4 = viewInstanceCount3;
                        objArr[0] = dbStats2.pageSize > 0 ? String.valueOf(dbStats2.pageSize) : WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER;
                        objArr[1] = dbStats2.dbSize > 0 ? String.valueOf(dbStats2.dbSize) : WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER;
                        objArr[2] = dbStats2.lookaside > 0 ? String.valueOf(dbStats2.lookaside) : WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER;
                        objArr[3] = dbStats2.cache;
                        objArr[4] = dbStats2.dbName;
                        ActivityThread.printRow(printWriter2, DB_INFO_FORMAT, objArr);
                        i6++;
                        N = N2;
                        viewInstanceCount3 = viewInstanceCount4;
                    }
                }
                long j = viewInstanceCount3;
                String assetAlloc = AssetManager.getAssetAllocations();
                if (assetAlloc != null) {
                    printWriter2.println(WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER);
                    printWriter2.println(" Asset Allocations");
                    printWriter2.print(assetAlloc);
                }
                if (dumpUnreachable) {
                    if ((ActivityThread.this.mBoundApplication == null || (2 & ActivityThread.this.mBoundApplication.appInfo.flags) == 0) && !Build.IS_DEBUGGABLE) {
                        showContents = false;
                    }
                    printWriter2.println(WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER);
                    printWriter2.println(" Unreachable memory");
                    printWriter2.print(Debug.getUnreachableMemory(100, showContents));
                    return;
                }
            }
        }

        public void dumpMemInfoProto(ParcelFileDescriptor pfd, Debug.MemoryInfo mem, boolean dumpFullInfo, boolean dumpDalvik, boolean dumpSummaryOnly, boolean dumpUnreachable, String[] args) {
            ProtoOutputStream proto = new ProtoOutputStream(pfd.getFileDescriptor());
            try {
                dumpMemInfo(proto, mem, dumpFullInfo, dumpDalvik, dumpSummaryOnly, dumpUnreachable);
            } finally {
                proto.flush();
                IoUtils.closeQuietly(pfd);
            }
        }

        private void dumpMemInfo(ProtoOutputStream proto, Debug.MemoryInfo memInfo, boolean dumpFullInfo, boolean dumpDalvik, boolean dumpSummaryOnly, boolean dumpUnreachable) {
            ProtoOutputStream protoOutputStream = proto;
            long nativeMax = Debug.getNativeHeapSize() / 1024;
            long nativeAllocated = Debug.getNativeHeapAllocatedSize() / 1024;
            long nativeFree = Debug.getNativeHeapFreeSize() / 1024;
            Runtime runtime = Runtime.getRuntime();
            runtime.gc();
            long dalvikMax = runtime.totalMemory() / 1024;
            long dalvikFree = runtime.freeMemory() / 1024;
            long dalvikAllocated = dalvikMax - dalvikFree;
            boolean showContents = false;
            Class[] classesToCount = {ContextImpl.class, Activity.class, WebView.class, OpenSSLSocketImpl.class};
            long[] instanceCounts = VMDebug.countInstancesOfClasses(classesToCount, true);
            long appContextInstanceCount = instanceCounts[0];
            long activityInstanceCount = instanceCounts[1];
            long webviewInstanceCount = instanceCounts[2];
            long openSslSocketCount = instanceCounts[3];
            long viewInstanceCount = ViewDebug.getViewInstanceCount();
            long viewRootInstanceCount = ViewDebug.getViewRootImplCount();
            int globalAssetCount = AssetManager.getGlobalAssetCount();
            int globalAssetManagerCount = AssetManager.getGlobalAssetManagerCount();
            int binderLocalObjectCount = Debug.getBinderLocalObjectCount();
            int binderProxyObjectCount = Debug.getBinderProxyObjectCount();
            int binderProxyObjectCount2 = Debug.getBinderDeathObjectCount();
            long parcelSize = Parcel.getGlobalAllocSize();
            int binderDeathObjectCount = binderProxyObjectCount2;
            Class[] classesToCount2 = classesToCount;
            long parcelCount = Parcel.getGlobalAllocCount();
            SQLiteDebug.PagerStats stats = SQLiteDebug.getDatabaseInfo();
            long viewRootInstanceCount2 = viewRootInstanceCount;
            long mToken = protoOutputStream.start(1146756268033L);
            long appContextInstanceCount2 = appContextInstanceCount;
            protoOutputStream.write(1120986464257L, Process.myPid());
            protoOutputStream.write(1138166333442L, ActivityThread.this.mBoundApplication != null ? ActivityThread.this.mBoundApplication.processName : "unknown");
            long viewInstanceCount2 = viewInstanceCount;
            long openSslSocketCount2 = openSslSocketCount;
            long webviewInstanceCount2 = webviewInstanceCount;
            long activityInstanceCount2 = activityInstanceCount;
            Class[] clsArr = classesToCount2;
            ProtoOutputStream protoOutputStream2 = protoOutputStream;
            ActivityThread.dumpMemInfoTable(proto, memInfo, dumpDalvik, dumpSummaryOnly, nativeMax, nativeAllocated, nativeFree, dalvikMax, dalvikAllocated, dalvikFree);
            long mToken2 = mToken;
            protoOutputStream2.end(mToken2);
            long oToken = protoOutputStream2.start(1146756268034L);
            long viewInstanceCount3 = viewInstanceCount2;
            protoOutputStream2.write(1120986464257L, viewInstanceCount3);
            protoOutputStream2.write(1120986464258L, viewRootInstanceCount2);
            long appContextInstanceCount3 = appContextInstanceCount2;
            protoOutputStream2.write(1120986464259L, appContextInstanceCount3);
            long activityInstanceCount3 = activityInstanceCount2;
            protoOutputStream2.write(1120986464260L, activityInstanceCount3);
            int globalAssetCount2 = globalAssetCount;
            protoOutputStream2.write(1120986464261L, globalAssetCount2);
            long j = mToken2;
            int globalAssetManagerCount2 = globalAssetManagerCount;
            protoOutputStream2.write(1120986464262L, globalAssetManagerCount2);
            int binderLocalObjectCount2 = binderLocalObjectCount;
            protoOutputStream2.write(1120986464263L, binderLocalObjectCount2);
            int i = globalAssetManagerCount2;
            int binderProxyObjectCount3 = binderProxyObjectCount;
            protoOutputStream2.write(1120986464264L, binderProxyObjectCount3);
            int i2 = binderProxyObjectCount3;
            int i3 = binderLocalObjectCount2;
            protoOutputStream2.write(1112396529673L, parcelSize / 1024);
            protoOutputStream2.write(1120986464266L, parcelCount);
            long j2 = viewInstanceCount3;
            int binderDeathObjectCount2 = binderDeathObjectCount;
            protoOutputStream2.write(1120986464267L, binderDeathObjectCount2);
            int i4 = binderDeathObjectCount2;
            long openSslSocketCount3 = openSslSocketCount2;
            protoOutputStream2.write(1120986464268L, openSslSocketCount3);
            long j3 = openSslSocketCount3;
            long webviewInstanceCount3 = webviewInstanceCount2;
            protoOutputStream2.write(1120986464269L, webviewInstanceCount3);
            protoOutputStream2.end(oToken);
            long sToken = protoOutputStream2.start(1146756268035L);
            long j4 = oToken;
            SQLiteDebug.PagerStats stats2 = stats;
            long j5 = webviewInstanceCount3;
            protoOutputStream2.write(1120986464257L, stats2.memoryUsed / 1024);
            protoOutputStream2.write(1120986464258L, stats2.pageCacheOverflow / 1024);
            protoOutputStream2.write(1120986464259L, stats2.largestMemAlloc / 1024);
            int n = stats2.dbStats.size();
            int i5 = 0;
            while (true) {
                long activityInstanceCount4 = activityInstanceCount3;
                if (i5 >= n) {
                    break;
                }
                SQLiteDebug.DbStats dbStats = stats2.dbStats.get(i5);
                long dToken = protoOutputStream2.start(2246267895812L);
                protoOutputStream2.write(1138166333441L, dbStats.dbName);
                protoOutputStream2.write(1120986464258L, dbStats.pageSize);
                protoOutputStream2.write(1120986464259L, dbStats.dbSize);
                protoOutputStream2.write(1120986464260L, dbStats.lookaside);
                protoOutputStream2.write(1138166333445L, dbStats.cache);
                protoOutputStream2.end(dToken);
                i5++;
                activityInstanceCount3 = activityInstanceCount4;
                stats2 = stats2;
                n = n;
                appContextInstanceCount3 = appContextInstanceCount3;
            }
            int i6 = n;
            long j6 = appContextInstanceCount3;
            protoOutputStream2.end(sToken);
            String assetAlloc = AssetManager.getAssetAllocations();
            if (assetAlloc != null) {
                protoOutputStream2.write(1138166333444L, assetAlloc);
            }
            if (dumpUnreachable) {
                int i7 = globalAssetCount2;
                if (((ActivityThread.this.mBoundApplication == null ? 0 : ActivityThread.this.mBoundApplication.appInfo.flags) & 2) != 0 || Build.IS_DEBUGGABLE) {
                    showContents = true;
                }
                long j7 = sToken;
                protoOutputStream2.write(1138166333445L, Debug.getUnreachableMemory(100, showContents));
                return;
            }
            int i8 = globalAssetCount2;
        }

        public void dumpGfxInfo(ParcelFileDescriptor pfd, String[] args) {
            ActivityThread.this.nDumpGraphicsInfo(pfd.getFileDescriptor());
            WindowManagerGlobal.getInstance().dumpGfxInfo(pfd.getFileDescriptor(), args);
            IoUtils.closeQuietly(pfd);
        }

        private File getDatabasesDir(Context context) {
            return context.getDatabasePath(FullBackup.APK_TREE_TOKEN).getParentFile();
        }

        /* access modifiers changed from: private */
        public void dumpDatabaseInfo(ParcelFileDescriptor pfd, String[] args, boolean isSystem) {
            PrintWriter pw = new FastPrintWriter((OutputStream) new FileOutputStream(pfd.getFileDescriptor()));
            SQLiteDebug.dump(new PrintWriterPrinter(pw), args, isSystem);
            pw.flush();
        }

        public void dumpDbInfo(ParcelFileDescriptor pfd, final String[] args) {
            if (ActivityThread.this.mSystemThread) {
                try {
                    final ParcelFileDescriptor dup = pfd.dup();
                    IoUtils.closeQuietly(pfd);
                    AsyncTask.THREAD_POOL_EXECUTOR.execute(new Runnable() {
                        public void run() {
                            try {
                                ApplicationThread.this.dumpDatabaseInfo(dup, args, true);
                            } finally {
                                IoUtils.closeQuietly(dup);
                            }
                        }
                    });
                } catch (IOException e) {
                    Log.w(ActivityThread.TAG, "Could not dup FD " + pfd.getFileDescriptor().getInt$());
                    IoUtils.closeQuietly(pfd);
                } catch (Throwable th) {
                    IoUtils.closeQuietly(pfd);
                    throw th;
                }
            } else {
                dumpDatabaseInfo(pfd, args, false);
                IoUtils.closeQuietly(pfd);
            }
        }

        public void unstableProviderDied(IBinder provider) {
            ActivityThread.this.sendMessage(142, provider);
        }

        public void requestAssistContextExtras(IBinder activityToken, IBinder requestToken, int requestType, int sessionId, int flags) {
            RequestAssistContextExtras cmd = new RequestAssistContextExtras();
            cmd.activityToken = activityToken;
            cmd.requestToken = requestToken;
            cmd.requestType = requestType;
            cmd.sessionId = sessionId;
            cmd.flags = flags;
            ActivityThread.this.sendMessage(143, cmd);
        }

        public void setCoreSettings(Bundle coreSettings) {
            ActivityThread.this.sendMessage(138, coreSettings);
        }

        public void updatePackageCompatibilityInfo(String pkg, CompatibilityInfo info) {
            UpdateCompatibilityData ucd = new UpdateCompatibilityData();
            ucd.pkg = pkg;
            ucd.info = info;
            ActivityThread.this.sendMessage(139, ucd);
        }

        public void scheduleTrimMemory(int level) {
            Runnable r = PooledLambda.obtainRunnable($$Lambda$ActivityThread$ApplicationThread$tUGFX7CUhzB4Pg5wFd5yeqOnu38.INSTANCE, ActivityThread.this, Integer.valueOf(level)).recycleOnUse();
            Choreographer choreographer = Choreographer.getMainThreadInstance();
            if (choreographer != null) {
                choreographer.postCallback(4, r, (Object) null);
            } else {
                ActivityThread.this.mH.post(r);
            }
        }

        public void scheduleTranslucentConversionComplete(IBinder token, boolean drawComplete) {
            ActivityThread.this.sendMessage(144, token, drawComplete);
        }

        public void scheduleOnNewActivityOptions(IBinder token, Bundle options) {
            ActivityThread.this.sendMessage(146, new Pair(token, ActivityOptions.fromBundle(options)));
        }

        public void setProcessState(int state) {
            ActivityThread.this.updateProcessState(state, true);
        }

        public void setNetworkBlockSeq(long procStateSeq) {
            synchronized (ActivityThread.this.mNetworkPolicyLock) {
                long unused = ActivityThread.this.mNetworkBlockSeq = procStateSeq;
            }
        }

        public void scheduleInstallProvider(ProviderInfo provider) {
            ActivityThread.this.sendMessage(145, provider);
        }

        public final void updateTimePrefs(int timeFormatPreference) {
            Boolean timeFormatPreferenceBool;
            if (timeFormatPreference == 0) {
                timeFormatPreferenceBool = Boolean.FALSE;
            } else if (timeFormatPreference == 1) {
                timeFormatPreferenceBool = Boolean.TRUE;
            } else {
                timeFormatPreferenceBool = null;
            }
            DateFormat.set24HourTimePref(timeFormatPreferenceBool);
        }

        public void scheduleEnterAnimationComplete(IBinder token) {
            ActivityThread.this.sendMessage(149, token);
        }

        public void notifyCleartextNetwork(byte[] firstPacket) {
            if (StrictMode.vmCleartextNetworkEnabled()) {
                StrictMode.onCleartextNetworkDetected(firstPacket);
            }
        }

        public void startBinderTracking() {
            ActivityThread.this.sendMessage(150, (Object) null);
        }

        public void stopBinderTrackingAndDump(ParcelFileDescriptor pfd) {
            try {
                ActivityThread.this.sendMessage(151, pfd.dup());
            } catch (IOException e) {
            } catch (Throwable th) {
                IoUtils.closeQuietly(pfd);
                throw th;
            }
            IoUtils.closeQuietly(pfd);
        }

        public void scheduleLocalVoiceInteractionStarted(IBinder token, IVoiceInteractor voiceInteractor) throws RemoteException {
            SomeArgs args = SomeArgs.obtain();
            args.arg1 = token;
            args.arg2 = voiceInteractor;
            ActivityThread.this.sendMessage(154, args);
        }

        public void handleTrustStorageUpdate() {
            NetworkSecurityPolicy.getInstance().handleTrustStorageUpdate();
        }

        public void scheduleTransaction(ClientTransaction transaction) throws RemoteException {
            ActivityThread.this.scheduleTransaction(transaction);
        }

        public void requestDirectActions(IBinder activityToken, IVoiceInteractor interactor, RemoteCallback cancellationCallback, RemoteCallback callback) {
            CancellationSignal cancellationSignal = new CancellationSignal();
            if (cancellationCallback != null) {
                ICancellationSignal transport = ActivityThread.this.createSafeCancellationTransport(cancellationSignal);
                Bundle cancellationResult = new Bundle();
                cancellationResult.putBinder(VoiceInteractor.KEY_CANCELLATION_SIGNAL, transport.asBinder());
                cancellationCallback.sendResult(cancellationResult);
            }
            ActivityThread.this.mH.sendMessage(PooledLambda.obtainMessage($$Lambda$ActivityThread$ApplicationThread$uR_ee5oPoxu4U_by7wU55jwtdU.INSTANCE, ActivityThread.this, activityToken, interactor, cancellationSignal, callback));
        }

        public void performDirectAction(IBinder activityToken, String actionId, Bundle arguments, RemoteCallback cancellationCallback, RemoteCallback resultCallback) {
            CancellationSignal cancellationSignal = new CancellationSignal();
            if (cancellationCallback != null) {
                ICancellationSignal transport = ActivityThread.this.createSafeCancellationTransport(cancellationSignal);
                Bundle cancellationResult = new Bundle();
                cancellationResult.putBinder(VoiceInteractor.KEY_CANCELLATION_SIGNAL, transport.asBinder());
                cancellationCallback.sendResult(cancellationResult);
            }
            ActivityThread.this.mH.sendMessage(PooledLambda.obtainMessage($$Lambda$ActivityThread$ApplicationThread$nBC_BR7B9W6ftKAxur3BC53SJYc.INSTANCE, ActivityThread.this, activityToken, actionId, arguments, cancellationSignal, resultCallback));
        }
    }

    /* access modifiers changed from: private */
    public SafeCancellationTransport createSafeCancellationTransport(CancellationSignal cancellationSignal) {
        SafeCancellationTransport transport;
        synchronized (this) {
            if (this.mRemoteCancellations == null) {
                this.mRemoteCancellations = new ArrayMap();
            }
            transport = new SafeCancellationTransport(this, cancellationSignal);
            this.mRemoteCancellations.put(transport, cancellationSignal);
        }
        return transport;
    }

    /* access modifiers changed from: private */
    public CancellationSignal removeSafeCancellationTransport(SafeCancellationTransport transport) {
        CancellationSignal cancellation;
        synchronized (this) {
            cancellation = this.mRemoteCancellations.remove(transport);
            if (this.mRemoteCancellations.isEmpty()) {
                this.mRemoteCancellations = null;
            }
        }
        return cancellation;
    }

    private static final class SafeCancellationTransport extends ICancellationSignal.Stub {
        private final WeakReference<ActivityThread> mWeakActivityThread;

        SafeCancellationTransport(ActivityThread activityThread, CancellationSignal cancellation) {
            this.mWeakActivityThread = new WeakReference<>(activityThread);
        }

        public void cancel() {
            CancellationSignal cancellation;
            ActivityThread activityThread = (ActivityThread) this.mWeakActivityThread.get();
            if (activityThread != null && (cancellation = activityThread.removeSafeCancellationTransport(this)) != null) {
                cancellation.cancel();
            }
        }
    }

    class H extends Handler {
        public static final int APPLICATION_INFO_CHANGED = 156;
        public static final int ATTACH_AGENT = 155;
        public static final int BIND_APPLICATION = 110;
        @UnsupportedAppUsage
        public static final int BIND_SERVICE = 121;
        public static final int CLEAN_UP_CONTEXT = 119;
        public static final int CONFIGURATION_CHANGED = 118;
        public static final int CREATE_BACKUP_AGENT = 128;
        @UnsupportedAppUsage
        public static final int CREATE_SERVICE = 114;
        public static final int DESTROY_BACKUP_AGENT = 129;
        public static final int DISPATCH_PACKAGE_BROADCAST = 133;
        public static final int DUMP_ACTIVITY = 136;
        public static final int DUMP_HEAP = 135;
        @UnsupportedAppUsage
        public static final int DUMP_PROVIDER = 141;
        public static final int DUMP_SERVICE = 123;
        @UnsupportedAppUsage
        public static final int ENTER_ANIMATION_COMPLETE = 149;
        public static final int EXECUTE_TRANSACTION = 159;
        @UnsupportedAppUsage
        public static final int EXIT_APPLICATION = 111;
        @UnsupportedAppUsage
        public static final int GC_WHEN_IDLE = 120;
        @UnsupportedAppUsage
        public static final int INSTALL_PROVIDER = 145;
        public static final int LOCAL_VOICE_INTERACTION_STARTED = 154;
        public static final int LOW_MEMORY = 124;
        public static final int ON_NEW_ACTIVITY_OPTIONS = 146;
        public static final int PROFILER_CONTROL = 127;
        public static final int PURGE_RESOURCES = 161;
        @UnsupportedAppUsage
        public static final int RECEIVER = 113;
        public static final int RELAUNCH_ACTIVITY = 160;
        @UnsupportedAppUsage
        public static final int REMOVE_PROVIDER = 131;
        public static final int REQUEST_ASSIST_CONTEXT_EXTRAS = 143;
        public static final int RUN_ISOLATED_ENTRY_POINT = 158;
        @UnsupportedAppUsage
        public static final int SCHEDULE_CRASH = 134;
        @UnsupportedAppUsage
        public static final int SERVICE_ARGS = 115;
        public static final int SET_CORE_SETTINGS = 138;
        public static final int SLEEPING = 137;
        public static final int START_BINDER_TRACKING = 150;
        public static final int STOP_BINDER_TRACKING_AND_DUMP = 151;
        @UnsupportedAppUsage
        public static final int STOP_SERVICE = 116;
        public static final int SUICIDE = 130;
        public static final int TRANSLUCENT_CONVERSION_COMPLETE = 144;
        @UnsupportedAppUsage
        public static final int UNBIND_SERVICE = 122;
        public static final int UNSTABLE_PROVIDER_DIED = 142;
        public static final int UPDATE_PACKAGE_COMPATIBILITY_INFO = 139;

        H() {
        }

        /* access modifiers changed from: package-private */
        public String codeToString(int code) {
            return Integer.toString(code);
        }

        public void handleMessage(Message msg) {
            boolean z = true;
            switch (msg.what) {
                case 110:
                    Trace.traceBegin(64, "bindApplication");
                    ActivityThread.this.handleBindApplication((AppBindData) msg.obj);
                    Trace.traceEnd(64);
                    break;
                case 111:
                    if (ActivityThread.this.mInitialApplication != null) {
                        ActivityThread.this.mInitialApplication.onTerminate();
                    }
                    Looper.myLooper().quit();
                    break;
                case 113:
                    Trace.traceBegin(64, "broadcastReceiveComp");
                    ActivityThread.this.handleReceiver((ReceiverData) msg.obj);
                    Trace.traceEnd(64);
                    break;
                case 114:
                    Trace.traceBegin(64, "serviceCreate: " + String.valueOf(msg.obj));
                    ActivityThread.this.handleCreateService((CreateServiceData) msg.obj);
                    Trace.traceEnd(64);
                    break;
                case 115:
                    Trace.traceBegin(64, "serviceStart: " + String.valueOf(msg.obj));
                    ActivityThread.this.handleServiceArgs((ServiceArgsData) msg.obj);
                    Trace.traceEnd(64);
                    break;
                case 116:
                    Trace.traceBegin(64, "serviceStop");
                    ActivityThread.this.handleStopService((IBinder) msg.obj);
                    ActivityThread.this.schedulePurgeIdler();
                    Trace.traceEnd(64);
                    break;
                case 118:
                    ActivityThread.this.handleConfigurationChanged((Configuration) msg.obj);
                    break;
                case 119:
                    ContextCleanupInfo cci = (ContextCleanupInfo) msg.obj;
                    cci.context.performFinalCleanup(cci.who, cci.what);
                    break;
                case 120:
                    ActivityThread.this.scheduleGcIdler();
                    break;
                case 121:
                    Trace.traceBegin(64, "serviceBind");
                    ActivityThread.this.handleBindService((BindServiceData) msg.obj);
                    Trace.traceEnd(64);
                    break;
                case 122:
                    Trace.traceBegin(64, "serviceUnbind");
                    ActivityThread.this.handleUnbindService((BindServiceData) msg.obj);
                    ActivityThread.this.schedulePurgeIdler();
                    Trace.traceEnd(64);
                    break;
                case 123:
                    ActivityThread.this.handleDumpService((DumpComponentInfo) msg.obj);
                    break;
                case 124:
                    Trace.traceBegin(64, "lowMemory");
                    ActivityThread.this.handleLowMemory();
                    Trace.traceEnd(64);
                    break;
                case 127:
                    ActivityThread activityThread = ActivityThread.this;
                    if (msg.arg1 == 0) {
                        z = false;
                    }
                    activityThread.handleProfilerControl(z, (ProfilerInfo) msg.obj, msg.arg2);
                    break;
                case 128:
                    Trace.traceBegin(64, "backupCreateAgent");
                    ActivityThread.this.handleCreateBackupAgent((CreateBackupAgentData) msg.obj);
                    Trace.traceEnd(64);
                    break;
                case 129:
                    Trace.traceBegin(64, "backupDestroyAgent");
                    ActivityThread.this.handleDestroyBackupAgent((CreateBackupAgentData) msg.obj);
                    Trace.traceEnd(64);
                    break;
                case 130:
                    Process.killProcess(Process.myPid());
                    break;
                case 131:
                    Trace.traceBegin(64, "providerRemove");
                    ActivityThread.this.completeRemoveProvider((ProviderRefCount) msg.obj);
                    Trace.traceEnd(64);
                    break;
                case 133:
                    Trace.traceBegin(64, "broadcastPackage");
                    ActivityThread.this.handleDispatchPackageBroadcast(msg.arg1, (String[]) msg.obj);
                    Trace.traceEnd(64);
                    break;
                case 134:
                    throw new RemoteServiceException((String) msg.obj);
                case 135:
                    ActivityThread.handleDumpHeap((DumpHeapData) msg.obj);
                    break;
                case 136:
                    ActivityThread.this.handleDumpActivity((DumpComponentInfo) msg.obj);
                    break;
                case 137:
                    Trace.traceBegin(64, "sleeping");
                    ActivityThread activityThread2 = ActivityThread.this;
                    IBinder iBinder = (IBinder) msg.obj;
                    if (msg.arg1 == 0) {
                        z = false;
                    }
                    activityThread2.handleSleeping(iBinder, z);
                    Trace.traceEnd(64);
                    break;
                case 138:
                    Trace.traceBegin(64, "setCoreSettings");
                    ActivityThread.this.handleSetCoreSettings((Bundle) msg.obj);
                    Trace.traceEnd(64);
                    break;
                case 139:
                    ActivityThread.this.handleUpdatePackageCompatibilityInfo((UpdateCompatibilityData) msg.obj);
                    break;
                case 141:
                    ActivityThread.this.handleDumpProvider((DumpComponentInfo) msg.obj);
                    break;
                case 142:
                    ActivityThread.this.handleUnstableProviderDied((IBinder) msg.obj, false);
                    break;
                case 143:
                    ActivityThread.this.handleRequestAssistContextExtras((RequestAssistContextExtras) msg.obj);
                    break;
                case 144:
                    ActivityThread activityThread3 = ActivityThread.this;
                    IBinder iBinder2 = (IBinder) msg.obj;
                    if (msg.arg1 != 1) {
                        z = false;
                    }
                    activityThread3.handleTranslucentConversionComplete(iBinder2, z);
                    break;
                case 145:
                    ActivityThread.this.handleInstallProvider((ProviderInfo) msg.obj);
                    break;
                case 146:
                    Pair<IBinder, ActivityOptions> pair = (Pair) msg.obj;
                    ActivityThread.this.onNewActivityOptions((IBinder) pair.first, (ActivityOptions) pair.second);
                    break;
                case 149:
                    ActivityThread.this.handleEnterAnimationComplete((IBinder) msg.obj);
                    break;
                case 150:
                    ActivityThread.this.handleStartBinderTracking();
                    break;
                case 151:
                    ActivityThread.this.handleStopBinderTrackingAndDump((ParcelFileDescriptor) msg.obj);
                    break;
                case 154:
                    ActivityThread.this.handleLocalVoiceInteractionStarted((IBinder) ((SomeArgs) msg.obj).arg1, (IVoiceInteractor) ((SomeArgs) msg.obj).arg2);
                    break;
                case 155:
                    Application app = ActivityThread.this.getApplication();
                    ActivityThread.handleAttachAgent((String) msg.obj, app != null ? app.mLoadedApk : null);
                    break;
                case 156:
                    ActivityThread.this.mUpdatingSystemConfig = true;
                    try {
                        ActivityThread.this.handleApplicationInfoChanged((ApplicationInfo) msg.obj);
                        break;
                    } finally {
                        ActivityThread.this.mUpdatingSystemConfig = false;
                    }
                case 158:
                    ActivityThread.this.handleRunIsolatedEntryPoint((String) ((SomeArgs) msg.obj).arg1, (String[]) ((SomeArgs) msg.obj).arg2);
                    break;
                case 159:
                    ClientTransaction transaction = (ClientTransaction) msg.obj;
                    ActivityThread.this.mTransactionExecutor.execute(transaction);
                    if (ActivityThread.isSystem()) {
                        transaction.recycle();
                        break;
                    }
                    break;
                case 160:
                    ActivityThread.this.handleRelaunchActivityLocally((IBinder) msg.obj);
                    break;
                case 161:
                    ActivityThread.this.schedulePurgeIdler();
                    break;
            }
            Object obj = msg.obj;
            if (obj instanceof SomeArgs) {
                ((SomeArgs) obj).recycle();
            }
        }
    }

    private class Idler implements MessageQueue.IdleHandler {
        private Idler() {
        }

        public final boolean queueIdle() {
            ActivityClientRecord a = ActivityThread.this.mNewActivities;
            boolean stopProfiling = false;
            if (!(ActivityThread.this.mBoundApplication == null || ActivityThread.this.mProfiler.profileFd == null || !ActivityThread.this.mProfiler.autoStopProfiler)) {
                stopProfiling = true;
            }
            if (a != null) {
                ActivityThread.this.mNewActivities = null;
                IActivityTaskManager am = ActivityTaskManager.getService();
                do {
                    if (a.activity != null && !a.activity.mFinished) {
                        try {
                            am.activityIdle(a.token, a.createdConfig, stopProfiling);
                            a.createdConfig = null;
                        } catch (RemoteException ex) {
                            throw ex.rethrowFromSystemServer();
                        }
                    }
                    ActivityClientRecord prev = a;
                    a = a.nextIdle;
                    prev.nextIdle = null;
                } while (a != null);
            }
            if (stopProfiling) {
                ActivityThread.this.mProfiler.stopProfiling();
            }
            ActivityThread.this.applyPendingProcessState();
            return false;
        }
    }

    final class GcIdler implements MessageQueue.IdleHandler {
        GcIdler() {
        }

        public final boolean queueIdle() {
            ActivityThread.this.doGcIfNeeded();
            ActivityThread.this.purgePendingResources();
            return false;
        }
    }

    final class PurgeIdler implements MessageQueue.IdleHandler {
        PurgeIdler() {
        }

        public boolean queueIdle() {
            ActivityThread.this.purgePendingResources();
            return false;
        }
    }

    @UnsupportedAppUsage
    public static ActivityThread currentActivityThread() {
        return sCurrentActivityThread;
    }

    public static boolean isSystem() {
        if (sCurrentActivityThread != null) {
            return sCurrentActivityThread.mSystemThread;
        }
        return false;
    }

    public static String currentOpPackageName() {
        ActivityThread am = currentActivityThread();
        if (am == null || am.getApplication() == null) {
            return null;
        }
        return am.getApplication().getOpPackageName();
    }

    @UnsupportedAppUsage
    public static String currentPackageName() {
        ActivityThread am = currentActivityThread();
        if (am == null || am.mBoundApplication == null) {
            return null;
        }
        return am.mBoundApplication.appInfo.packageName;
    }

    @UnsupportedAppUsage
    public static String currentProcessName() {
        ActivityThread am = currentActivityThread();
        if (am == null || am.mBoundApplication == null) {
            return null;
        }
        return am.mBoundApplication.processName;
    }

    @UnsupportedAppUsage
    public static Application currentApplication() {
        ActivityThread am = currentActivityThread();
        if (am != null) {
            return am.mInitialApplication;
        }
        return null;
    }

    @UnsupportedAppUsage
    public static IPackageManager getPackageManager() {
        if (sPackageManager != null) {
            return sPackageManager;
        }
        sPackageManager = IPackageManager.Stub.asInterface(ServiceManager.getService("package"));
        return sPackageManager;
    }

    /* access modifiers changed from: package-private */
    public Configuration applyConfigCompatMainThread(int displayDensity, Configuration config, CompatibilityInfo compat) {
        if (config == null) {
            return null;
        }
        if (compat.supportsScreen()) {
            return config;
        }
        this.mMainThreadConfig.setTo(config);
        Configuration config2 = this.mMainThreadConfig;
        compat.applyToConfiguration(displayDensity, config2);
        return config2;
    }

    /* access modifiers changed from: package-private */
    public Resources getTopLevelResources(String resDir, String[] splitResDirs, String[] overlayDirs, String[] libDirs, int displayId, LoadedApk pkgInfo) {
        return this.mResourcesManager.getResources((IBinder) null, resDir, splitResDirs, overlayDirs, libDirs, displayId, (Configuration) null, pkgInfo.getCompatibilityInfo(), pkgInfo.getClassLoader());
    }

    /* access modifiers changed from: package-private */
    @UnsupportedAppUsage
    public final Handler getHandler() {
        return this.mH;
    }

    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
    public final LoadedApk getPackageInfo(String packageName, CompatibilityInfo compatInfo, int flags) {
        return getPackageInfo(packageName, compatInfo, flags, UserHandle.myUserId());
    }

    /* JADX WARNING: Code restructure failed: missing block: B:38:0x0091, code lost:
        return r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x0093, code lost:
        if (r1 == null) goto L_0x009a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x0099, code lost:
        return getPackageInfo(r1, r10, r11);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x009a, code lost:
        return null;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final android.app.LoadedApk getPackageInfo(java.lang.String r9, android.content.res.CompatibilityInfo r10, int r11, int r12) {
        /*
            r8 = this;
            int r0 = android.os.UserHandle.myUserId()
            if (r0 == r12) goto L_0x0008
            r0 = 1
            goto L_0x0009
        L_0x0008:
            r0 = 0
        L_0x0009:
            android.content.pm.IPackageManager r1 = getPackageManager()     // Catch:{ RemoteException -> 0x009d }
            r2 = 268436480(0x10000400, float:2.524663E-29)
            if (r12 >= 0) goto L_0x0017
            int r3 = android.os.UserHandle.myUserId()     // Catch:{ RemoteException -> 0x009d }
            goto L_0x0018
        L_0x0017:
            r3 = r12
        L_0x0018:
            android.content.pm.ApplicationInfo r1 = r1.getApplicationInfo(r9, r2, r3)     // Catch:{ RemoteException -> 0x009d }
            android.app.ResourcesManager r2 = r8.mResourcesManager
            monitor-enter(r2)
            if (r0 == 0) goto L_0x0025
            r3 = 0
            goto L_0x003c
        L_0x0025:
            r3 = r11 & 1
            if (r3 == 0) goto L_0x0034
            android.util.ArrayMap<java.lang.String, java.lang.ref.WeakReference<android.app.LoadedApk>> r3 = r8.mPackages     // Catch:{ all -> 0x0032 }
            java.lang.Object r3 = r3.get(r9)     // Catch:{ all -> 0x0032 }
            java.lang.ref.WeakReference r3 = (java.lang.ref.WeakReference) r3     // Catch:{ all -> 0x0032 }
            goto L_0x003c
        L_0x0032:
            r3 = move-exception
            goto L_0x009b
        L_0x0034:
            android.util.ArrayMap<java.lang.String, java.lang.ref.WeakReference<android.app.LoadedApk>> r3 = r8.mResourcePackages     // Catch:{ all -> 0x0032 }
            java.lang.Object r3 = r3.get(r9)     // Catch:{ all -> 0x0032 }
            java.lang.ref.WeakReference r3 = (java.lang.ref.WeakReference) r3     // Catch:{ all -> 0x0032 }
        L_0x003c:
            r4 = 0
            if (r3 == 0) goto L_0x0046
            java.lang.Object r5 = r3.get()     // Catch:{ all -> 0x0032 }
            android.app.LoadedApk r5 = (android.app.LoadedApk) r5     // Catch:{ all -> 0x0032 }
            goto L_0x0047
        L_0x0046:
            r5 = r4
        L_0x0047:
            if (r1 == 0) goto L_0x0092
            if (r5 == 0) goto L_0x0092
            boolean r6 = isLoadedApkResourceDirsUpToDate(r5, r1)     // Catch:{ all -> 0x0032 }
            if (r6 != 0) goto L_0x0054
            r5.updateApplicationInfo(r1, r4)     // Catch:{ all -> 0x0032 }
        L_0x0054:
            boolean r4 = r5.isSecurityViolation()     // Catch:{ all -> 0x0032 }
            if (r4 == 0) goto L_0x0090
            r4 = r11 & 2
            if (r4 == 0) goto L_0x005f
            goto L_0x0090
        L_0x005f:
            java.lang.SecurityException r4 = new java.lang.SecurityException     // Catch:{ all -> 0x0032 }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x0032 }
            r6.<init>()     // Catch:{ all -> 0x0032 }
            java.lang.String r7 = "Requesting code from "
            r6.append(r7)     // Catch:{ all -> 0x0032 }
            r6.append(r9)     // Catch:{ all -> 0x0032 }
            java.lang.String r7 = " to be run in process "
            r6.append(r7)     // Catch:{ all -> 0x0032 }
            android.app.ActivityThread$AppBindData r7 = r8.mBoundApplication     // Catch:{ all -> 0x0032 }
            java.lang.String r7 = r7.processName     // Catch:{ all -> 0x0032 }
            r6.append(r7)     // Catch:{ all -> 0x0032 }
            java.lang.String r7 = "/"
            r6.append(r7)     // Catch:{ all -> 0x0032 }
            android.app.ActivityThread$AppBindData r7 = r8.mBoundApplication     // Catch:{ all -> 0x0032 }
            android.content.pm.ApplicationInfo r7 = r7.appInfo     // Catch:{ all -> 0x0032 }
            int r7 = r7.uid     // Catch:{ all -> 0x0032 }
            r6.append(r7)     // Catch:{ all -> 0x0032 }
            java.lang.String r6 = r6.toString()     // Catch:{ all -> 0x0032 }
            r4.<init>(r6)     // Catch:{ all -> 0x0032 }
            throw r4     // Catch:{ all -> 0x0032 }
        L_0x0090:
            monitor-exit(r2)     // Catch:{ all -> 0x0032 }
            return r5
        L_0x0092:
            monitor-exit(r2)     // Catch:{ all -> 0x0032 }
            if (r1 == 0) goto L_0x009a
            android.app.LoadedApk r2 = r8.getPackageInfo((android.content.pm.ApplicationInfo) r1, (android.content.res.CompatibilityInfo) r10, (int) r11)
            return r2
        L_0x009a:
            return r4
        L_0x009b:
            monitor-exit(r2)     // Catch:{ all -> 0x0032 }
            throw r3
        L_0x009d:
            r1 = move-exception
            java.lang.RuntimeException r2 = r1.rethrowFromSystemServer()
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: android.app.ActivityThread.getPackageInfo(java.lang.String, android.content.res.CompatibilityInfo, int, int):android.app.LoadedApk");
    }

    @UnsupportedAppUsage
    public final LoadedApk getPackageInfo(ApplicationInfo ai, CompatibilityInfo compatInfo, int flags) {
        boolean includeCode = (flags & 1) != 0;
        boolean securityViolation = includeCode && ai.uid != 0 && ai.uid != 1000 && (this.mBoundApplication == null || !UserHandle.isSameApp(ai.uid, this.mBoundApplication.appInfo.uid));
        boolean registerPackage = includeCode && (1073741824 & flags) != 0;
        if ((flags & 3) != 1 || !securityViolation) {
            return getPackageInfo(ai, compatInfo, (ClassLoader) null, securityViolation, includeCode, registerPackage);
        }
        String msg = "Requesting code from " + ai.packageName + " (with uid " + ai.uid + ")";
        if (this.mBoundApplication != null) {
            msg = msg + " to be run in process " + this.mBoundApplication.processName + " (with uid " + this.mBoundApplication.appInfo.uid + ")";
        }
        throw new SecurityException(msg);
    }

    @UnsupportedAppUsage
    public final LoadedApk getPackageInfoNoCheck(ApplicationInfo ai, CompatibilityInfo compatInfo) {
        return getPackageInfo(ai, compatInfo, (ClassLoader) null, false, true, false);
    }

    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
    public final LoadedApk peekPackageInfo(String packageName, boolean includeCode) {
        WeakReference<LoadedApk> ref;
        LoadedApk loadedApk;
        synchronized (this.mResourcesManager) {
            if (includeCode) {
                try {
                    ref = this.mPackages.get(packageName);
                } catch (Throwable th) {
                    throw th;
                }
            } else {
                ref = this.mResourcePackages.get(packageName);
            }
            loadedApk = ref != null ? (LoadedApk) ref.get() : null;
        }
        return loadedApk;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:24:0x004e, code lost:
        return r13;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00a7, code lost:
        return r1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private android.app.LoadedApk getPackageInfo(android.content.pm.ApplicationInfo r16, android.content.res.CompatibilityInfo r17, java.lang.ClassLoader r18, boolean r19, boolean r20, boolean r21) {
        /*
            r15 = this;
            r9 = r15
            r10 = r16
            int r0 = android.os.UserHandle.myUserId()
            int r1 = r10.uid
            int r1 = android.os.UserHandle.getUserId(r1)
            r2 = 0
            r3 = 1
            if (r0 == r1) goto L_0x0013
            r0 = r3
            goto L_0x0014
        L_0x0013:
            r0 = r2
        L_0x0014:
            r11 = r0
            android.app.ResourcesManager r12 = r9.mResourcesManager
            monitor-enter(r12)
            if (r11 == 0) goto L_0x001c
            r0 = 0
            goto L_0x0036
        L_0x001c:
            if (r20 == 0) goto L_0x002c
            android.util.ArrayMap<java.lang.String, java.lang.ref.WeakReference<android.app.LoadedApk>> r0 = r9.mPackages     // Catch:{ all -> 0x0029 }
            java.lang.String r1 = r10.packageName     // Catch:{ all -> 0x0029 }
            java.lang.Object r0 = r0.get(r1)     // Catch:{ all -> 0x0029 }
            java.lang.ref.WeakReference r0 = (java.lang.ref.WeakReference) r0     // Catch:{ all -> 0x0029 }
            goto L_0x0036
        L_0x0029:
            r0 = move-exception
            goto L_0x00a8
        L_0x002c:
            android.util.ArrayMap<java.lang.String, java.lang.ref.WeakReference<android.app.LoadedApk>> r0 = r9.mResourcePackages     // Catch:{ all -> 0x0029 }
            java.lang.String r1 = r10.packageName     // Catch:{ all -> 0x0029 }
            java.lang.Object r0 = r0.get(r1)     // Catch:{ all -> 0x0029 }
            java.lang.ref.WeakReference r0 = (java.lang.ref.WeakReference) r0     // Catch:{ all -> 0x0029 }
        L_0x0036:
            r1 = 0
            if (r0 == 0) goto L_0x0040
            java.lang.Object r4 = r0.get()     // Catch:{ all -> 0x0029 }
            android.app.LoadedApk r4 = (android.app.LoadedApk) r4     // Catch:{ all -> 0x0029 }
            goto L_0x0041
        L_0x0040:
            r4 = r1
        L_0x0041:
            r13 = r4
            if (r13 == 0) goto L_0x004f
            boolean r2 = isLoadedApkResourceDirsUpToDate(r13, r10)     // Catch:{ all -> 0x0029 }
            if (r2 != 0) goto L_0x004d
            r13.updateApplicationInfo(r10, r1)     // Catch:{ all -> 0x0029 }
        L_0x004d:
            monitor-exit(r12)     // Catch:{ all -> 0x0029 }
            return r13
        L_0x004f:
            android.app.LoadedApk r14 = new android.app.LoadedApk     // Catch:{ all -> 0x0029 }
            if (r20 == 0) goto L_0x005b
            int r1 = r10.flags     // Catch:{ all -> 0x0029 }
            r1 = r1 & 4
            if (r1 == 0) goto L_0x005b
            r7 = r3
            goto L_0x005c
        L_0x005b:
            r7 = r2
        L_0x005c:
            r1 = r14
            r2 = r15
            r3 = r16
            r4 = r17
            r5 = r18
            r6 = r19
            r8 = r21
            r1.<init>(r2, r3, r4, r5, r6, r7, r8)     // Catch:{ all -> 0x0029 }
            r1 = r14
            boolean r2 = r9.mSystemThread     // Catch:{ all -> 0x0029 }
            if (r2 == 0) goto L_0x0088
            java.lang.String r2 = "android"
            java.lang.String r3 = r10.packageName     // Catch:{ all -> 0x0029 }
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0029 }
            if (r2 == 0) goto L_0x0088
            android.app.ContextImpl r2 = r15.getSystemContext()     // Catch:{ all -> 0x0029 }
            android.app.LoadedApk r2 = r2.mPackageInfo     // Catch:{ all -> 0x0029 }
            java.lang.ClassLoader r2 = r2.getClassLoader()     // Catch:{ all -> 0x0029 }
            r1.installSystemApplicationInfo(r10, r2)     // Catch:{ all -> 0x0029 }
        L_0x0088:
            if (r11 == 0) goto L_0x008b
            goto L_0x00a6
        L_0x008b:
            if (r20 == 0) goto L_0x009a
            android.util.ArrayMap<java.lang.String, java.lang.ref.WeakReference<android.app.LoadedApk>> r2 = r9.mPackages     // Catch:{ all -> 0x0029 }
            java.lang.String r3 = r10.packageName     // Catch:{ all -> 0x0029 }
            java.lang.ref.WeakReference r4 = new java.lang.ref.WeakReference     // Catch:{ all -> 0x0029 }
            r4.<init>(r1)     // Catch:{ all -> 0x0029 }
            r2.put(r3, r4)     // Catch:{ all -> 0x0029 }
            goto L_0x00a6
        L_0x009a:
            android.util.ArrayMap<java.lang.String, java.lang.ref.WeakReference<android.app.LoadedApk>> r2 = r9.mResourcePackages     // Catch:{ all -> 0x0029 }
            java.lang.String r3 = r10.packageName     // Catch:{ all -> 0x0029 }
            java.lang.ref.WeakReference r4 = new java.lang.ref.WeakReference     // Catch:{ all -> 0x0029 }
            r4.<init>(r1)     // Catch:{ all -> 0x0029 }
            r2.put(r3, r4)     // Catch:{ all -> 0x0029 }
        L_0x00a6:
            monitor-exit(r12)     // Catch:{ all -> 0x0029 }
            return r1
        L_0x00a8:
            monitor-exit(r12)     // Catch:{ all -> 0x0029 }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.app.ActivityThread.getPackageInfo(android.content.pm.ApplicationInfo, android.content.res.CompatibilityInfo, java.lang.ClassLoader, boolean, boolean, boolean):android.app.LoadedApk");
    }

    private static boolean isLoadedApkResourceDirsUpToDate(LoadedApk loadedApk, ApplicationInfo appInfo) {
        Resources packageResources = loadedApk.mResources;
        String[] overlayDirs = ArrayUtils.defeatNullable(loadedApk.getOverlayDirs());
        String[] resourceDirs = ArrayUtils.defeatNullable(appInfo.resourceDirs);
        return (packageResources == null || packageResources.getAssets().isUpToDate()) && overlayDirs.length == resourceDirs.length && ArrayUtils.containsAll((T[]) overlayDirs, (T[]) resourceDirs);
    }

    @UnsupportedAppUsage
    ActivityThread() {
    }

    @UnsupportedAppUsage
    public ApplicationThread getApplicationThread() {
        return this.mAppThread;
    }

    @UnsupportedAppUsage
    public Instrumentation getInstrumentation() {
        return this.mInstrumentation;
    }

    public boolean isProfiling() {
        return (this.mProfiler == null || this.mProfiler.profileFile == null || this.mProfiler.profileFd != null) ? false : true;
    }

    public String getProfileFilePath() {
        return this.mProfiler.profileFile;
    }

    @UnsupportedAppUsage
    public Looper getLooper() {
        return this.mLooper;
    }

    public Executor getExecutor() {
        return this.mExecutor;
    }

    @UnsupportedAppUsage
    public Application getApplication() {
        return this.mInitialApplication;
    }

    @UnsupportedAppUsage
    public String getProcessName() {
        return this.mBoundApplication.processName;
    }

    @UnsupportedAppUsage
    public ContextImpl getSystemContext() {
        ContextImpl contextImpl;
        synchronized (this) {
            if (this.mSystemContext == null) {
                this.mSystemContext = ContextImpl.createSystemContext(this);
            }
            contextImpl = this.mSystemContext;
        }
        return contextImpl;
    }

    public ContextImpl getSystemUiContext() {
        ContextImpl contextImpl;
        synchronized (this) {
            if (this.mSystemUiContext == null) {
                this.mSystemUiContext = ContextImpl.createSystemUiContext(getSystemContext());
            }
            contextImpl = this.mSystemUiContext;
        }
        return contextImpl;
    }

    public ContextImpl createSystemUiContext(int displayId) {
        return ContextImpl.createSystemUiContext(getSystemUiContext(), displayId);
    }

    public void installSystemApplicationInfo(ApplicationInfo info, ClassLoader classLoader) {
        synchronized (this) {
            getSystemContext().installSystemApplicationInfo(info, classLoader);
            getSystemUiContext().installSystemApplicationInfo(info, classLoader);
            this.mProfiler = new Profiler();
        }
    }

    /* access modifiers changed from: package-private */
    @UnsupportedAppUsage
    public void scheduleGcIdler() {
        if (!this.mGcIdlerScheduled) {
            this.mGcIdlerScheduled = true;
            Looper.myQueue().addIdleHandler(this.mGcIdler);
        }
        this.mH.removeMessages(120);
    }

    /* access modifiers changed from: package-private */
    public void unscheduleGcIdler() {
        if (this.mGcIdlerScheduled) {
            this.mGcIdlerScheduled = false;
            Looper.myQueue().removeIdleHandler(this.mGcIdler);
        }
        this.mH.removeMessages(120);
    }

    /* access modifiers changed from: package-private */
    public void schedulePurgeIdler() {
        if (!this.mPurgeIdlerScheduled) {
            this.mPurgeIdlerScheduled = true;
            Looper.myQueue().addIdleHandler(this.mPurgeIdler);
        }
        this.mH.removeMessages(161);
    }

    /* access modifiers changed from: package-private */
    public void unschedulePurgeIdler() {
        if (this.mPurgeIdlerScheduled) {
            this.mPurgeIdlerScheduled = false;
            Looper.myQueue().removeIdleHandler(this.mPurgeIdler);
        }
        this.mH.removeMessages(161);
    }

    /* access modifiers changed from: package-private */
    public void doGcIfNeeded() {
        doGcIfNeeded("bg");
    }

    /* access modifiers changed from: package-private */
    public void doGcIfNeeded(String reason) {
        this.mGcIdlerScheduled = false;
        if (BinderInternal.getLastGcTime() + 5000 < SystemClock.uptimeMillis()) {
            BinderInternal.forceGc(reason);
        }
    }

    static void printRow(PrintWriter pw, String format, Object... objs) {
        pw.println(String.format(format, objs));
    }

    public static void dumpMemInfoTable(PrintWriter pw, Debug.MemoryInfo memInfo, boolean checkin, boolean dumpFullInfo, boolean dumpDalvik, boolean dumpSummaryOnly, int pid, String processName, long nativeMax, long nativeAllocated, long nativeFree, long dalvikMax, long dalvikAllocated, long dalvikFree) {
        int otherSharedDirty;
        int otherSwappablePss;
        int i;
        int i2;
        int i3;
        int i4;
        PrintWriter printWriter = pw;
        Debug.MemoryInfo memoryInfo = memInfo;
        long j = nativeMax;
        long j2 = nativeAllocated;
        long j3 = nativeFree;
        long j4 = dalvikMax;
        long j5 = dalvikAllocated;
        long j6 = dalvikFree;
        int i5 = 0;
        if (checkin) {
            printWriter.print(4);
            printWriter.print(',');
            printWriter.print(pid);
            printWriter.print(',');
            printWriter.print(processName);
            printWriter.print(',');
            printWriter.print(j);
            printWriter.print(',');
            printWriter.print(j4);
            printWriter.print(',');
            printWriter.print("N/A,");
            printWriter.print(j + j4);
            printWriter.print(',');
            printWriter.print(j2);
            printWriter.print(',');
            printWriter.print(j5);
            printWriter.print(',');
            printWriter.print("N/A,");
            printWriter.print(j2 + j5);
            printWriter.print(',');
            printWriter.print(j3);
            printWriter.print(',');
            printWriter.print(j6);
            printWriter.print(',');
            printWriter.print("N/A,");
            printWriter.print(j3 + j6);
            printWriter.print(',');
            Debug.MemoryInfo memoryInfo2 = memInfo;
            printWriter.print(memoryInfo2.nativePss);
            printWriter.print(',');
            printWriter.print(memoryInfo2.dalvikPss);
            printWriter.print(',');
            printWriter.print(memoryInfo2.otherPss);
            printWriter.print(',');
            printWriter.print(memInfo.getTotalPss());
            printWriter.print(',');
            printWriter.print(memoryInfo2.nativeSwappablePss);
            printWriter.print(',');
            printWriter.print(memoryInfo2.dalvikSwappablePss);
            printWriter.print(',');
            printWriter.print(memoryInfo2.otherSwappablePss);
            printWriter.print(',');
            printWriter.print(memInfo.getTotalSwappablePss());
            printWriter.print(',');
            printWriter.print(memoryInfo2.nativeSharedDirty);
            printWriter.print(',');
            printWriter.print(memoryInfo2.dalvikSharedDirty);
            printWriter.print(',');
            printWriter.print(memoryInfo2.otherSharedDirty);
            printWriter.print(',');
            printWriter.print(memInfo.getTotalSharedDirty());
            printWriter.print(',');
            printWriter.print(memoryInfo2.nativeSharedClean);
            printWriter.print(',');
            printWriter.print(memoryInfo2.dalvikSharedClean);
            printWriter.print(',');
            printWriter.print(memoryInfo2.otherSharedClean);
            printWriter.print(',');
            printWriter.print(memInfo.getTotalSharedClean());
            printWriter.print(',');
            printWriter.print(memoryInfo2.nativePrivateDirty);
            printWriter.print(',');
            printWriter.print(memoryInfo2.dalvikPrivateDirty);
            printWriter.print(',');
            printWriter.print(memoryInfo2.otherPrivateDirty);
            printWriter.print(',');
            printWriter.print(memInfo.getTotalPrivateDirty());
            printWriter.print(',');
            printWriter.print(memoryInfo2.nativePrivateClean);
            printWriter.print(',');
            printWriter.print(memoryInfo2.dalvikPrivateClean);
            printWriter.print(',');
            printWriter.print(memoryInfo2.otherPrivateClean);
            printWriter.print(',');
            printWriter.print(memInfo.getTotalPrivateClean());
            printWriter.print(',');
            printWriter.print(memoryInfo2.nativeSwappedOut);
            printWriter.print(',');
            printWriter.print(memoryInfo2.dalvikSwappedOut);
            printWriter.print(',');
            printWriter.print(memoryInfo2.otherSwappedOut);
            printWriter.print(',');
            printWriter.print(memInfo.getTotalSwappedOut());
            printWriter.print(',');
            if (memoryInfo2.hasSwappedOutPss) {
                printWriter.print(memoryInfo2.nativeSwappedOutPss);
                printWriter.print(',');
                printWriter.print(memoryInfo2.dalvikSwappedOutPss);
                printWriter.print(',');
                printWriter.print(memoryInfo2.otherSwappedOutPss);
                printWriter.print(',');
                printWriter.print(memInfo.getTotalSwappedOutPss());
                printWriter.print(',');
            } else {
                printWriter.print("N/A,");
                printWriter.print("N/A,");
                printWriter.print("N/A,");
                printWriter.print("N/A,");
            }
            while (true) {
                int i6 = i5;
                if (i6 < 17) {
                    printWriter.print(Debug.MemoryInfo.getOtherLabel(i6));
                    printWriter.print(',');
                    printWriter.print(memoryInfo2.getOtherPss(i6));
                    printWriter.print(',');
                    printWriter.print(memoryInfo2.getOtherSwappablePss(i6));
                    printWriter.print(',');
                    printWriter.print(memoryInfo2.getOtherSharedDirty(i6));
                    printWriter.print(',');
                    printWriter.print(memoryInfo2.getOtherSharedClean(i6));
                    printWriter.print(',');
                    printWriter.print(memoryInfo2.getOtherPrivateDirty(i6));
                    printWriter.print(',');
                    printWriter.print(memoryInfo2.getOtherPrivateClean(i6));
                    printWriter.print(',');
                    printWriter.print(memoryInfo2.getOtherSwappedOut(i6));
                    printWriter.print(',');
                    if (memoryInfo2.hasSwappedOutPss) {
                        printWriter.print(memoryInfo2.getOtherSwappedOutPss(i6));
                        printWriter.print(',');
                    } else {
                        printWriter.print("N/A,");
                    }
                    i5 = i6 + 1;
                } else {
                    return;
                }
            }
        } else {
            Debug.MemoryInfo memoryInfo3 = memoryInfo;
            if (!dumpSummaryOnly) {
                if (dumpFullInfo) {
                    Object[] objArr = new Object[11];
                    objArr[0] = "";
                    objArr[1] = "Pss";
                    objArr[2] = "Pss";
                    objArr[3] = "Shared";
                    objArr[4] = "Private";
                    objArr[5] = "Shared";
                    objArr[6] = "Private";
                    objArr[7] = memoryInfo3.hasSwappedOutPss ? "SwapPss" : "Swap";
                    objArr[8] = "Heap";
                    objArr[9] = "Heap";
                    objArr[10] = "Heap";
                    printRow(printWriter, HEAP_FULL_COLUMN, objArr);
                    printRow(printWriter, HEAP_FULL_COLUMN, "", "Total", "Clean", "Dirty", "Dirty", "Clean", "Clean", "Dirty", "Size", "Alloc", "Free");
                    printRow(printWriter, HEAP_FULL_COLUMN, "", "------", "------", "------", "------", "------", "------", "------", "------", "------", "------");
                    Object[] objArr2 = new Object[11];
                    objArr2[0] = "Native Heap";
                    objArr2[1] = Integer.valueOf(memoryInfo3.nativePss);
                    objArr2[2] = Integer.valueOf(memoryInfo3.nativeSwappablePss);
                    objArr2[3] = Integer.valueOf(memoryInfo3.nativeSharedDirty);
                    objArr2[4] = Integer.valueOf(memoryInfo3.nativePrivateDirty);
                    objArr2[5] = Integer.valueOf(memoryInfo3.nativeSharedClean);
                    objArr2[6] = Integer.valueOf(memoryInfo3.nativePrivateClean);
                    objArr2[7] = Integer.valueOf(memoryInfo3.hasSwappedOutPss ? memoryInfo3.nativeSwappedOutPss : memoryInfo3.nativeSwappedOut);
                    objArr2[8] = Long.valueOf(nativeMax);
                    objArr2[9] = Long.valueOf(nativeAllocated);
                    objArr2[10] = Long.valueOf(nativeFree);
                    printRow(printWriter, HEAP_FULL_COLUMN, objArr2);
                    Object[] objArr3 = new Object[11];
                    objArr3[0] = "Dalvik Heap";
                    objArr3[1] = Integer.valueOf(memoryInfo3.dalvikPss);
                    objArr3[2] = Integer.valueOf(memoryInfo3.dalvikSwappablePss);
                    objArr3[3] = Integer.valueOf(memoryInfo3.dalvikSharedDirty);
                    objArr3[4] = Integer.valueOf(memoryInfo3.dalvikPrivateDirty);
                    objArr3[5] = Integer.valueOf(memoryInfo3.dalvikSharedClean);
                    objArr3[6] = Integer.valueOf(memoryInfo3.dalvikPrivateClean);
                    objArr3[7] = Integer.valueOf(memoryInfo3.hasSwappedOutPss ? memoryInfo3.dalvikSwappedOutPss : memoryInfo3.dalvikSwappedOut);
                    objArr3[8] = Long.valueOf(dalvikMax);
                    objArr3[9] = Long.valueOf(dalvikAllocated);
                    objArr3[10] = Long.valueOf(dalvikFree);
                    printRow(printWriter, HEAP_FULL_COLUMN, objArr3);
                } else {
                    Object[] objArr4 = new Object[8];
                    objArr4[0] = "";
                    objArr4[1] = "Pss";
                    objArr4[2] = "Private";
                    objArr4[3] = "Private";
                    objArr4[4] = memoryInfo3.hasSwappedOutPss ? "SwapPss" : "Swap";
                    objArr4[5] = "Heap";
                    objArr4[6] = "Heap";
                    objArr4[7] = "Heap";
                    printRow(printWriter, HEAP_COLUMN, objArr4);
                    printRow(printWriter, HEAP_COLUMN, "", "Total", "Dirty", "Clean", "Dirty", "Size", "Alloc", "Free");
                    printRow(printWriter, HEAP_COLUMN, "", "------", "------", "------", "------", "------", "------", "------", "------");
                    Object[] objArr5 = new Object[8];
                    objArr5[0] = "Native Heap";
                    objArr5[1] = Integer.valueOf(memoryInfo3.nativePss);
                    objArr5[2] = Integer.valueOf(memoryInfo3.nativePrivateDirty);
                    objArr5[3] = Integer.valueOf(memoryInfo3.nativePrivateClean);
                    if (memoryInfo3.hasSwappedOutPss) {
                        i3 = memoryInfo3.nativeSwappedOutPss;
                    } else {
                        i3 = memoryInfo3.nativeSwappedOut;
                    }
                    objArr5[4] = Integer.valueOf(i3);
                    objArr5[5] = Long.valueOf(nativeMax);
                    objArr5[6] = Long.valueOf(nativeAllocated);
                    objArr5[7] = Long.valueOf(nativeFree);
                    printRow(printWriter, HEAP_COLUMN, objArr5);
                    Object[] objArr6 = new Object[8];
                    objArr6[0] = "Dalvik Heap";
                    objArr6[1] = Integer.valueOf(memoryInfo3.dalvikPss);
                    objArr6[2] = Integer.valueOf(memoryInfo3.dalvikPrivateDirty);
                    objArr6[3] = Integer.valueOf(memoryInfo3.dalvikPrivateClean);
                    if (memoryInfo3.hasSwappedOutPss) {
                        i4 = memoryInfo3.dalvikSwappedOutPss;
                    } else {
                        i4 = memoryInfo3.dalvikSwappedOut;
                    }
                    objArr6[4] = Integer.valueOf(i4);
                    objArr6[5] = Long.valueOf(dalvikMax);
                    objArr6[6] = Long.valueOf(dalvikAllocated);
                    objArr6[7] = Long.valueOf(dalvikFree);
                    printRow(printWriter, HEAP_COLUMN, objArr6);
                }
                int otherPss = memoryInfo3.otherPss;
                int otherSwappablePss2 = memoryInfo3.otherSwappablePss;
                int otherSharedDirty2 = memoryInfo3.otherSharedDirty;
                int otherPrivateDirty = memoryInfo3.otherPrivateDirty;
                int otherPss2 = otherPss;
                int otherSharedClean = memoryInfo3.otherSharedClean;
                int otherPrivateClean = memoryInfo3.otherPrivateClean;
                int otherSwappedOut = memoryInfo3.otherSwappedOut;
                int otherSwappedOutPss = memoryInfo3.otherSwappedOutPss;
                int i7 = 0;
                while (i7 < 17) {
                    int myPss = memoryInfo3.getOtherPss(i7);
                    int mySwappablePss = memoryInfo3.getOtherSwappablePss(i7);
                    int mySharedDirty = memoryInfo3.getOtherSharedDirty(i7);
                    int myPrivateDirty = memoryInfo3.getOtherPrivateDirty(i7);
                    int mySharedClean = memoryInfo3.getOtherSharedClean(i7);
                    int myPrivateClean = memoryInfo3.getOtherPrivateClean(i7);
                    int mySwappedOut = memoryInfo3.getOtherSwappedOut(i7);
                    int mySwappedOutPss = memoryInfo3.getOtherSwappedOutPss(i7);
                    if (myPss == 0 && mySharedDirty == 0 && myPrivateDirty == 0 && mySharedClean == 0 && myPrivateClean == 0) {
                        if ((memoryInfo3.hasSwappedOutPss ? mySwappedOutPss : mySwappedOut) == 0) {
                            i7++;
                            long j7 = nativeAllocated;
                            long j8 = nativeFree;
                            long j9 = dalvikFree;
                        }
                    }
                    if (dumpFullInfo) {
                        Object[] objArr7 = new Object[11];
                        objArr7[0] = Debug.MemoryInfo.getOtherLabel(i7);
                        objArr7[1] = Integer.valueOf(myPss);
                        objArr7[2] = Integer.valueOf(mySwappablePss);
                        objArr7[3] = Integer.valueOf(mySharedDirty);
                        objArr7[4] = Integer.valueOf(myPrivateDirty);
                        objArr7[5] = Integer.valueOf(mySharedClean);
                        objArr7[6] = Integer.valueOf(myPrivateClean);
                        objArr7[7] = Integer.valueOf(memoryInfo3.hasSwappedOutPss ? mySwappedOutPss : mySwappedOut);
                        objArr7[8] = "";
                        objArr7[9] = "";
                        objArr7[10] = "";
                        printRow(printWriter, HEAP_FULL_COLUMN, objArr7);
                    } else {
                        Object[] objArr8 = new Object[8];
                        objArr8[0] = Debug.MemoryInfo.getOtherLabel(i7);
                        objArr8[1] = Integer.valueOf(myPss);
                        objArr8[2] = Integer.valueOf(myPrivateDirty);
                        objArr8[3] = Integer.valueOf(myPrivateClean);
                        objArr8[4] = Integer.valueOf(memoryInfo3.hasSwappedOutPss ? mySwappedOutPss : mySwappedOut);
                        objArr8[5] = "";
                        objArr8[6] = "";
                        objArr8[7] = "";
                        printRow(printWriter, HEAP_COLUMN, objArr8);
                    }
                    otherPss2 -= myPss;
                    otherSwappablePss2 -= mySwappablePss;
                    otherSharedDirty2 -= mySharedDirty;
                    otherPrivateDirty -= myPrivateDirty;
                    otherSharedClean -= mySharedClean;
                    otherPrivateClean -= myPrivateClean;
                    otherSwappedOut -= mySwappedOut;
                    otherSwappedOutPss -= mySwappedOutPss;
                    i7++;
                    long j72 = nativeAllocated;
                    long j82 = nativeFree;
                    long j92 = dalvikFree;
                }
                if (dumpFullInfo) {
                    Object[] objArr9 = new Object[11];
                    objArr9[0] = "Unknown";
                    objArr9[1] = Integer.valueOf(otherPss2);
                    objArr9[2] = Integer.valueOf(otherSwappablePss2);
                    objArr9[3] = Integer.valueOf(otherSharedDirty2);
                    objArr9[4] = Integer.valueOf(otherPrivateDirty);
                    objArr9[5] = Integer.valueOf(otherSharedClean);
                    objArr9[6] = Integer.valueOf(otherPrivateClean);
                    objArr9[7] = Integer.valueOf(memoryInfo3.hasSwappedOutPss ? otherSwappedOutPss : otherSwappedOut);
                    objArr9[8] = "";
                    objArr9[9] = "";
                    objArr9[10] = "";
                    printRow(printWriter, HEAP_FULL_COLUMN, objArr9);
                    Object[] objArr10 = new Object[11];
                    objArr10[0] = "TOTAL";
                    objArr10[1] = Integer.valueOf(memInfo.getTotalPss());
                    objArr10[2] = Integer.valueOf(memInfo.getTotalSwappablePss());
                    objArr10[3] = Integer.valueOf(memInfo.getTotalSharedDirty());
                    objArr10[4] = Integer.valueOf(memInfo.getTotalPrivateDirty());
                    objArr10[5] = Integer.valueOf(memInfo.getTotalSharedClean());
                    objArr10[6] = Integer.valueOf(memInfo.getTotalPrivateClean());
                    if (memoryInfo3.hasSwappedOutPss) {
                        i2 = memInfo.getTotalSwappedOutPss();
                    } else {
                        i2 = memInfo.getTotalSwappedOut();
                    }
                    objArr10[7] = Integer.valueOf(i2);
                    objArr10[8] = Long.valueOf(nativeMax + j4);
                    objArr10[9] = Long.valueOf(nativeAllocated + j5);
                    objArr10[10] = Long.valueOf(nativeFree + dalvikFree);
                    printRow(printWriter, HEAP_FULL_COLUMN, objArr10);
                } else {
                    long j10 = nativeMax;
                    long j11 = nativeAllocated;
                    long j12 = nativeFree;
                    long j13 = dalvikFree;
                    Object[] objArr11 = new Object[8];
                    objArr11[0] = "Unknown";
                    objArr11[1] = Integer.valueOf(otherPss2);
                    objArr11[2] = Integer.valueOf(otherPrivateDirty);
                    objArr11[3] = Integer.valueOf(otherPrivateClean);
                    objArr11[4] = Integer.valueOf(memoryInfo3.hasSwappedOutPss ? otherSwappedOutPss : otherSwappedOut);
                    objArr11[5] = "";
                    objArr11[6] = "";
                    objArr11[7] = "";
                    printRow(printWriter, HEAP_COLUMN, objArr11);
                    Object[] objArr12 = new Object[8];
                    objArr12[0] = "TOTAL";
                    objArr12[1] = Integer.valueOf(memInfo.getTotalPss());
                    objArr12[2] = Integer.valueOf(memInfo.getTotalPrivateDirty());
                    objArr12[3] = Integer.valueOf(memInfo.getTotalPrivateClean());
                    if (memoryInfo3.hasSwappedOutPss) {
                        i = memInfo.getTotalSwappedOutPss();
                    } else {
                        i = memInfo.getTotalSwappedOut();
                    }
                    objArr12[4] = Integer.valueOf(i);
                    objArr12[5] = Long.valueOf(j10 + j4);
                    objArr12[6] = Long.valueOf(j11 + j5);
                    objArr12[7] = Long.valueOf(j12 + j13);
                    printRow(printWriter, HEAP_COLUMN, objArr12);
                }
                if (dumpDalvik) {
                    printWriter.println(WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER);
                    printWriter.println(" Dalvik Details");
                    int myPrivateClean2 = 17;
                    while (true) {
                        int i8 = myPrivateClean2;
                        if (i8 >= 31) {
                            break;
                        }
                        int myPss2 = memoryInfo3.getOtherPss(i8);
                        int mySwappablePss2 = memoryInfo3.getOtherSwappablePss(i8);
                        int mySharedDirty2 = memoryInfo3.getOtherSharedDirty(i8);
                        int myPrivateDirty2 = memoryInfo3.getOtherPrivateDirty(i8);
                        int mySharedClean2 = memoryInfo3.getOtherSharedClean(i8);
                        int myPrivateClean3 = memoryInfo3.getOtherPrivateClean(i8);
                        int mySwappedOut2 = memoryInfo3.getOtherSwappedOut(i8);
                        int mySwappedOutPss2 = memoryInfo3.getOtherSwappedOutPss(i8);
                        if (myPss2 == 0 && mySharedDirty2 == 0 && myPrivateDirty2 == 0 && mySharedClean2 == 0 && myPrivateClean3 == 0) {
                            if ((memoryInfo3.hasSwappedOutPss ? mySwappedOutPss2 : mySwappedOut2) == 0) {
                                otherSwappablePss = otherSwappablePss2;
                                otherSharedDirty = otherSharedDirty2;
                                myPrivateClean2 = i8 + 1;
                                otherSwappablePss2 = otherSwappablePss;
                                otherSharedDirty2 = otherSharedDirty;
                                long j14 = nativeMax;
                            }
                        }
                        if (dumpFullInfo) {
                            otherSwappablePss = otherSwappablePss2;
                            otherSharedDirty = otherSharedDirty2;
                            Object[] objArr13 = new Object[11];
                            objArr13[0] = Debug.MemoryInfo.getOtherLabel(i8);
                            objArr13[1] = Integer.valueOf(myPss2);
                            objArr13[2] = Integer.valueOf(mySwappablePss2);
                            objArr13[3] = Integer.valueOf(mySharedDirty2);
                            objArr13[4] = Integer.valueOf(myPrivateDirty2);
                            objArr13[5] = Integer.valueOf(mySharedClean2);
                            objArr13[6] = Integer.valueOf(myPrivateClean3);
                            objArr13[7] = Integer.valueOf(memoryInfo3.hasSwappedOutPss ? mySwappedOutPss2 : mySwappedOut2);
                            objArr13[8] = "";
                            objArr13[9] = "";
                            objArr13[10] = "";
                            printRow(printWriter, HEAP_FULL_COLUMN, objArr13);
                        } else {
                            otherSwappablePss = otherSwappablePss2;
                            otherSharedDirty = otherSharedDirty2;
                            Object[] objArr14 = new Object[8];
                            objArr14[0] = Debug.MemoryInfo.getOtherLabel(i8);
                            objArr14[1] = Integer.valueOf(myPss2);
                            objArr14[2] = Integer.valueOf(myPrivateDirty2);
                            objArr14[3] = Integer.valueOf(myPrivateClean3);
                            objArr14[4] = Integer.valueOf(memoryInfo3.hasSwappedOutPss ? mySwappedOutPss2 : mySwappedOut2);
                            objArr14[5] = "";
                            objArr14[6] = "";
                            objArr14[7] = "";
                            printRow(printWriter, HEAP_COLUMN, objArr14);
                        }
                        myPrivateClean2 = i8 + 1;
                        otherSwappablePss2 = otherSwappablePss;
                        otherSharedDirty2 = otherSharedDirty;
                        long j142 = nativeMax;
                    }
                }
            } else {
                long j15 = j3;
                long j16 = j6;
                long j17 = j2;
            }
            printWriter.println(WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER);
            printWriter.println(" App Summary");
            printRow(printWriter, ONE_COUNT_COLUMN_HEADER, "", "Pss(KB)");
            printRow(printWriter, ONE_COUNT_COLUMN_HEADER, "", "------");
            printRow(printWriter, ONE_COUNT_COLUMN, "Java Heap:", Integer.valueOf(memInfo.getSummaryJavaHeap()));
            printRow(printWriter, ONE_COUNT_COLUMN, "Native Heap:", Integer.valueOf(memInfo.getSummaryNativeHeap()));
            printRow(printWriter, ONE_COUNT_COLUMN, "Code:", Integer.valueOf(memInfo.getSummaryCode()));
            printRow(printWriter, ONE_COUNT_COLUMN, "Stack:", Integer.valueOf(memInfo.getSummaryStack()));
            printRow(printWriter, ONE_COUNT_COLUMN, "Graphics:", Integer.valueOf(memInfo.getSummaryGraphics()));
            printRow(printWriter, ONE_COUNT_COLUMN, "Private Other:", Integer.valueOf(memInfo.getSummaryPrivateOther()));
            printRow(printWriter, ONE_COUNT_COLUMN, "System:", Integer.valueOf(memInfo.getSummarySystem()));
            printWriter.println(WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER);
            if (memoryInfo3.hasSwappedOutPss) {
                printRow(printWriter, TWO_COUNT_COLUMNS, "TOTAL:", Integer.valueOf(memInfo.getSummaryTotalPss()), "TOTAL SWAP PSS:", Integer.valueOf(memInfo.getSummaryTotalSwapPss()));
            } else {
                printRow(printWriter, TWO_COUNT_COLUMNS, "TOTAL:", Integer.valueOf(memInfo.getSummaryTotalPss()), "TOTAL SWAP (KB):", Integer.valueOf(memInfo.getSummaryTotalSwap()));
            }
        }
    }

    private static void dumpMemoryInfo(ProtoOutputStream proto, long fieldId, String name, int pss, int cleanPss, int sharedDirty, int privateDirty, int sharedClean, int privateClean, boolean hasSwappedOutPss, int dirtySwap, int dirtySwapPss) {
        ProtoOutputStream protoOutputStream = proto;
        long token = proto.start(fieldId);
        proto.write(1138166333441L, name);
        proto.write(1120986464258L, pss);
        proto.write(1120986464259L, cleanPss);
        proto.write(1120986464260L, sharedDirty);
        proto.write(1120986464261L, privateDirty);
        proto.write(1120986464262L, sharedClean);
        proto.write(1120986464263L, privateClean);
        if (hasSwappedOutPss) {
            proto.write(1120986464265L, dirtySwapPss);
            int i = dirtySwap;
        } else {
            int i2 = dirtySwapPss;
            proto.write(1120986464264L, dirtySwap);
        }
        proto.end(token);
    }

    public static void dumpMemInfoTable(ProtoOutputStream proto, Debug.MemoryInfo memInfo, boolean dumpDalvik, boolean dumpSummaryOnly, long nativeMax, long nativeAllocated, long nativeFree, long dalvikMax, long dalvikAllocated, long dalvikFree) {
        ProtoOutputStream protoOutputStream;
        Debug.MemoryInfo memoryInfo;
        long tToken;
        long dvToken;
        long j;
        int i;
        long j2;
        long j3;
        Debug.MemoryInfo memoryInfo2;
        ProtoOutputStream protoOutputStream2 = proto;
        Debug.MemoryInfo memoryInfo3 = memInfo;
        long j4 = nativeMax;
        long j5 = nativeAllocated;
        long j6 = nativeFree;
        long j7 = dalvikMax;
        long j8 = dalvikAllocated;
        long j9 = dalvikFree;
        if (!dumpSummaryOnly) {
            long nhToken = protoOutputStream2.start(1146756268035L);
            dumpMemoryInfo(proto, 1146756268033L, "Native Heap", memoryInfo3.nativePss, memoryInfo3.nativeSwappablePss, memoryInfo3.nativeSharedDirty, memoryInfo3.nativePrivateDirty, memoryInfo3.nativeSharedClean, memoryInfo3.nativePrivateClean, memoryInfo3.hasSwappedOutPss, memoryInfo3.nativeSwappedOut, memoryInfo3.nativeSwappedOutPss);
            protoOutputStream = proto;
            protoOutputStream.write(1120986464258L, nativeMax);
            protoOutputStream.write(1120986464259L, j5);
            long j10 = nativeFree;
            protoOutputStream.write(1120986464260L, j10);
            long nhToken2 = nhToken;
            protoOutputStream.end(nhToken2);
            Debug.MemoryInfo memoryInfo4 = memInfo;
            Debug.MemoryInfo memoryInfo5 = memoryInfo4;
            long dvToken2 = protoOutputStream.start(1146756268036L);
            long j11 = nhToken2;
            long j12 = j10;
            long j13 = nativeMax;
            dumpMemoryInfo(proto, 1146756268033L, "Dalvik Heap", memoryInfo4.dalvikPss, memoryInfo4.dalvikSwappablePss, memoryInfo4.dalvikSharedDirty, memoryInfo4.dalvikPrivateDirty, memoryInfo4.dalvikSharedClean, memoryInfo4.dalvikPrivateClean, memoryInfo4.hasSwappedOutPss, memoryInfo4.dalvikSwappedOut, memoryInfo4.dalvikSwappedOutPss);
            long j14 = dalvikMax;
            protoOutputStream.write(1120986464258L, j14);
            long j15 = dalvikAllocated;
            protoOutputStream.write(1120986464259L, j15);
            long j16 = dalvikFree;
            protoOutputStream.write(1120986464260L, j16);
            protoOutputStream.end(dvToken2);
            Debug.MemoryInfo memoryInfo6 = memInfo;
            int otherPss = memoryInfo6.otherPss;
            int otherSwappablePss = memoryInfo6.otherSwappablePss;
            int otherSharedDirty = memoryInfo6.otherSharedDirty;
            int otherPrivateDirty = memoryInfo6.otherPrivateDirty;
            int otherSharedClean = memoryInfo6.otherSharedClean;
            int otherPrivateClean = memoryInfo6.otherPrivateClean;
            int otherPss2 = otherPss;
            int otherSwappedOut = memoryInfo6.otherSwappedOut;
            int myPss = 0;
            int otherSwappedOutPss = memoryInfo6.otherSwappedOutPss;
            int otherSwappablePss2 = otherSwappablePss;
            int otherSharedDirty2 = otherSharedDirty;
            int otherPrivateDirty2 = otherPrivateDirty;
            int otherSharedClean2 = otherSharedClean;
            int otherPrivateClean2 = otherPrivateClean;
            while (true) {
                int i2 = myPss;
                if (i2 >= 17) {
                    break;
                }
                int myPss2 = memoryInfo6.getOtherPss(i2);
                int mySwappablePss = memoryInfo6.getOtherSwappablePss(i2);
                int mySharedDirty = memoryInfo6.getOtherSharedDirty(i2);
                int myPrivateDirty = memoryInfo6.getOtherPrivateDirty(i2);
                int mySharedClean = memoryInfo6.getOtherSharedClean(i2);
                int myPrivateClean = memoryInfo6.getOtherPrivateClean(i2);
                int mySwappedOut = memoryInfo6.getOtherSwappedOut(i2);
                int mySwappedOutPss = memoryInfo6.getOtherSwappedOutPss(i2);
                if (myPss2 == 0 && mySharedDirty == 0 && myPrivateDirty == 0 && mySharedClean == 0 && myPrivateClean == 0) {
                    if ((memoryInfo6.hasSwappedOutPss ? mySwappedOutPss : mySwappedOut) == 0) {
                        j3 = j16;
                        j2 = j15;
                        i = i2;
                        j = j14;
                        dvToken = dvToken2;
                        memoryInfo2 = memoryInfo6;
                        myPss = i + 1;
                        memoryInfo6 = memoryInfo2;
                        j16 = j3;
                        j15 = j2;
                        j14 = j;
                        dvToken2 = dvToken;
                    }
                }
                dvToken = dvToken2;
                memoryInfo2 = memoryInfo6;
                j3 = j16;
                j2 = j15;
                i = i2;
                j = j14;
                dumpMemoryInfo(proto, 2246267895813L, Debug.MemoryInfo.getOtherLabel(i2), myPss2, mySwappablePss, mySharedDirty, myPrivateDirty, mySharedClean, myPrivateClean, memoryInfo6.hasSwappedOutPss, mySwappedOut, mySwappedOutPss);
                otherPss2 -= myPss2;
                otherSwappablePss2 -= mySwappablePss;
                otherSharedDirty2 -= mySharedDirty;
                otherPrivateDirty2 -= myPrivateDirty;
                otherSharedClean2 -= mySharedClean;
                otherPrivateClean2 -= myPrivateClean;
                otherSwappedOut -= mySwappedOut;
                otherSwappedOutPss -= mySwappedOutPss;
                myPss = i + 1;
                memoryInfo6 = memoryInfo2;
                j16 = j3;
                j15 = j2;
                j14 = j;
                dvToken2 = dvToken;
            }
            long j17 = j14;
            long dvToken3 = dvToken2;
            memoryInfo = memoryInfo6;
            int i3 = 17;
            dumpMemoryInfo(proto, 1146756268038L, "Unknown", otherPss2, otherSwappablePss2, otherSharedDirty2, otherPrivateDirty2, otherSharedClean2, otherPrivateClean2, memoryInfo.hasSwappedOutPss, otherSwappedOut, otherSwappedOutPss);
            long tToken2 = protoOutputStream.start(1146756268039L);
            dumpMemoryInfo(proto, 1146756268033L, "TOTAL", memInfo.getTotalPss(), memInfo.getTotalSwappablePss(), memInfo.getTotalSharedDirty(), memInfo.getTotalPrivateDirty(), memInfo.getTotalSharedClean(), memInfo.getTotalPrivateClean(), memoryInfo.hasSwappedOutPss, memInfo.getTotalSwappedOut(), memInfo.getTotalSwappedOutPss());
            protoOutputStream.write(1120986464258L, j13 + j17);
            long j18 = dvToken3;
            protoOutputStream.write(1120986464259L, nativeAllocated + j15);
            protoOutputStream.write(1120986464260L, j12 + j16);
            long tToken3 = tToken2;
            protoOutputStream.end(tToken3);
            if (dumpDalvik) {
                while (i3 < 31) {
                    int myPss3 = memoryInfo.getOtherPss(i3);
                    int mySwappablePss2 = memoryInfo.getOtherSwappablePss(i3);
                    int mySharedDirty2 = memoryInfo.getOtherSharedDirty(i3);
                    int myPrivateDirty2 = memoryInfo.getOtherPrivateDirty(i3);
                    int mySharedClean2 = memoryInfo.getOtherSharedClean(i3);
                    int myPrivateClean2 = memoryInfo.getOtherPrivateClean(i3);
                    int mySwappedOut2 = memoryInfo.getOtherSwappedOut(i3);
                    int mySwappedOutPss2 = memoryInfo.getOtherSwappedOutPss(i3);
                    if (myPss3 == 0 && mySharedDirty2 == 0 && myPrivateDirty2 == 0 && mySharedClean2 == 0 && myPrivateClean2 == 0) {
                        if ((memoryInfo.hasSwappedOutPss ? mySwappedOutPss2 : mySwappedOut2) == 0) {
                            tToken = tToken3;
                            i3++;
                            tToken3 = tToken;
                        }
                    }
                    tToken = tToken3;
                    dumpMemoryInfo(proto, 2246267895816L, Debug.MemoryInfo.getOtherLabel(i3), myPss3, mySwappablePss2, mySharedDirty2, myPrivateDirty2, mySharedClean2, myPrivateClean2, memoryInfo.hasSwappedOutPss, mySwappedOut2, mySwappedOutPss2);
                    i3++;
                    tToken3 = tToken;
                }
            }
        } else {
            long j19 = j8;
            long j20 = j7;
            long j21 = j6;
            long j22 = j5;
            long j23 = j4;
            protoOutputStream = protoOutputStream2;
            memoryInfo = memoryInfo3;
            long j24 = dalvikFree;
        }
        long asToken = protoOutputStream.start(1146756268041L);
        protoOutputStream.write(1120986464257L, memInfo.getSummaryJavaHeap());
        protoOutputStream.write(1120986464258L, memInfo.getSummaryNativeHeap());
        protoOutputStream.write(1120986464259L, memInfo.getSummaryCode());
        protoOutputStream.write(1120986464260L, memInfo.getSummaryStack());
        protoOutputStream.write(1120986464261L, memInfo.getSummaryGraphics());
        protoOutputStream.write(1120986464262L, memInfo.getSummaryPrivateOther());
        protoOutputStream.write(1120986464263L, memInfo.getSummarySystem());
        if (memoryInfo.hasSwappedOutPss) {
            protoOutputStream.write(1120986464264L, memInfo.getSummaryTotalSwapPss());
        } else {
            protoOutputStream.write(1120986464264L, memInfo.getSummaryTotalSwap());
        }
        protoOutputStream.end(asToken);
    }

    @UnsupportedAppUsage
    public void registerOnActivityPausedListener(Activity activity, OnActivityPausedListener listener) {
        synchronized (this.mOnPauseListeners) {
            ArrayList<OnActivityPausedListener> list = this.mOnPauseListeners.get(activity);
            if (list == null) {
                list = new ArrayList<>();
                this.mOnPauseListeners.put(activity, list);
            }
            list.add(listener);
        }
    }

    @UnsupportedAppUsage
    public void unregisterOnActivityPausedListener(Activity activity, OnActivityPausedListener listener) {
        synchronized (this.mOnPauseListeners) {
            ArrayList<OnActivityPausedListener> list = this.mOnPauseListeners.get(activity);
            if (list != null) {
                list.remove(listener);
            }
        }
    }

    public final ActivityInfo resolveActivityInfo(Intent intent) {
        ActivityInfo aInfo = intent.resolveActivityInfo(this.mInitialApplication.getPackageManager(), 1024);
        if (aInfo == null) {
            Instrumentation.checkStartActivityResult(-92, intent);
        }
        return aInfo;
    }

    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
    public final Activity startActivityNow(Activity parent, String id, Intent intent, ActivityInfo activityInfo, IBinder token, Bundle state, Activity.NonConfigurationInstances lastNonConfigurationInstances, IBinder assistToken) {
        ActivityClientRecord r = new ActivityClientRecord();
        r.token = token;
        r.assistToken = assistToken;
        r.ident = 0;
        r.intent = intent;
        r.state = state;
        r.parent = parent;
        r.embeddedID = id;
        r.activityInfo = activityInfo;
        r.lastNonConfigurationInstances = lastNonConfigurationInstances;
        return performLaunchActivity(r, (Intent) null);
    }

    @UnsupportedAppUsage
    public final Activity getActivity(IBinder token) {
        ActivityClientRecord activityRecord = this.mActivities.get(token);
        if (activityRecord != null) {
            return activityRecord.activity;
        }
        return null;
    }

    public ActivityClientRecord getActivityClient(IBinder token) {
        return this.mActivities.get(token);
    }

    public void updatePendingConfiguration(Configuration config) {
        synchronized (this.mResourcesManager) {
            if (this.mPendingConfiguration == null || this.mPendingConfiguration.isOtherSeqNewer(config)) {
                this.mPendingConfiguration = config;
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x002c, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void updateProcessState(int r6, boolean r7) {
        /*
            r5 = this;
            android.app.ActivityThread$ApplicationThread r0 = r5.mAppThread
            monitor-enter(r0)
            int r1 = r5.mLastProcessState     // Catch:{ all -> 0x002d }
            if (r1 != r6) goto L_0x0009
            monitor-exit(r0)     // Catch:{ all -> 0x002d }
            return
        L_0x0009:
            r5.mLastProcessState = r6     // Catch:{ all -> 0x002d }
            r1 = 2
            if (r6 != r1) goto L_0x0025
            java.util.concurrent.atomic.AtomicInteger r1 = r5.mNumLaunchingActivities     // Catch:{ all -> 0x002d }
            int r1 = r1.get()     // Catch:{ all -> 0x002d }
            if (r1 <= 0) goto L_0x0025
            r5.mPendingProcessState = r6     // Catch:{ all -> 0x002d }
            android.app.ActivityThread$H r1 = r5.mH     // Catch:{ all -> 0x002d }
            android.app.-$$Lambda$ActivityThread$A4ykhsPb8qV3ffTqpQDklHSMDJ0 r2 = new android.app.-$$Lambda$ActivityThread$A4ykhsPb8qV3ffTqpQDklHSMDJ0     // Catch:{ all -> 0x002d }
            r2.<init>()     // Catch:{ all -> 0x002d }
            r3 = 1000(0x3e8, double:4.94E-321)
            r1.postDelayed(r2, r3)     // Catch:{ all -> 0x002d }
            goto L_0x002b
        L_0x0025:
            r1 = -1
            r5.mPendingProcessState = r1     // Catch:{ all -> 0x002d }
            r5.updateVmProcessState(r6)     // Catch:{ all -> 0x002d }
        L_0x002b:
            monitor-exit(r0)     // Catch:{ all -> 0x002d }
            return
        L_0x002d:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x002d }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.app.ActivityThread.updateProcessState(int, boolean):void");
    }

    private void updateVmProcessState(int processState) {
        int state;
        if (processState <= 7) {
            state = 0;
        } else {
            state = 1;
        }
        VMRuntime.getRuntime().updateProcessState(state);
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0016, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void applyPendingProcessState() {
        /*
            r3 = this;
            android.app.ActivityThread$ApplicationThread r0 = r3.mAppThread
            monitor-enter(r0)
            int r1 = r3.mPendingProcessState     // Catch:{ all -> 0x0017 }
            r2 = -1
            if (r1 != r2) goto L_0x000a
            monitor-exit(r0)     // Catch:{ all -> 0x0017 }
            return
        L_0x000a:
            int r1 = r3.mPendingProcessState     // Catch:{ all -> 0x0017 }
            r3.mPendingProcessState = r2     // Catch:{ all -> 0x0017 }
            int r2 = r3.mLastProcessState     // Catch:{ all -> 0x0017 }
            if (r1 != r2) goto L_0x0015
            r3.updateVmProcessState(r1)     // Catch:{ all -> 0x0017 }
        L_0x0015:
            monitor-exit(r0)     // Catch:{ all -> 0x0017 }
            return
        L_0x0017:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0017 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.app.ActivityThread.applyPendingProcessState():void");
    }

    public void countLaunchingActivities(int num) {
        this.mNumLaunchingActivities.getAndAdd(num);
    }

    @UnsupportedAppUsage
    public final void sendActivityResult(IBinder token, String id, int requestCode, int resultCode, Intent data) {
        ArrayList<ResultInfo> list = new ArrayList<>();
        list.add(new ResultInfo(id, requestCode, resultCode, data));
        ClientTransaction clientTransaction = ClientTransaction.obtain(this.mAppThread, token);
        clientTransaction.addCallback(ActivityResultItem.obtain(list));
        try {
            this.mAppThread.scheduleTransaction(clientTransaction);
        } catch (RemoteException e) {
        }
    }

    /* access modifiers changed from: package-private */
    public TransactionExecutor getTransactionExecutor() {
        return this.mTransactionExecutor;
    }

    /* access modifiers changed from: package-private */
    public void sendMessage(int what, Object obj) {
        sendMessage(what, obj, 0, 0, false);
    }

    /* access modifiers changed from: private */
    public void sendMessage(int what, Object obj, int arg1) {
        sendMessage(what, obj, arg1, 0, false);
    }

    /* access modifiers changed from: private */
    public void sendMessage(int what, Object obj, int arg1, int arg2) {
        sendMessage(what, obj, arg1, arg2, false);
    }

    /* access modifiers changed from: private */
    public void sendMessage(int what, Object obj, int arg1, int arg2, boolean async) {
        Message msg = Message.obtain();
        msg.what = what;
        msg.obj = obj;
        msg.arg1 = arg1;
        msg.arg2 = arg2;
        if (async) {
            msg.setAsynchronous(true);
        }
        this.mH.sendMessage(msg);
    }

    private void sendMessage(int what, Object obj, int arg1, int arg2, int seq) {
        Message msg = Message.obtain();
        msg.what = what;
        SomeArgs args = SomeArgs.obtain();
        args.arg1 = obj;
        args.argi1 = arg1;
        args.argi2 = arg2;
        args.argi3 = seq;
        msg.obj = args;
        this.mH.sendMessage(msg);
    }

    /* access modifiers changed from: package-private */
    public final void scheduleContextCleanup(ContextImpl context, String who, String what) {
        ContextCleanupInfo cci = new ContextCleanupInfo();
        cci.context = context;
        cci.who = who;
        cci.what = what;
        sendMessage(119, cci);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:119:0x0234, code lost:
        r7 = r29;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:122:0x0242, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:123:0x0243, code lost:
        r1 = r5;
        r28 = r8;
        r29 = r9;
        r21 = r12;
        r2 = r13;
        r3 = r14;
        r4 = r15;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:129:0x027b, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:130:0x027c, code lost:
        r1 = r5;
        r28 = r8;
        r7 = r9;
        r21 = r12;
        r2 = r13;
        r3 = r14;
        r4 = r15;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:84:0x01aa, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:85:0x01ab, code lost:
        r1 = r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:86:0x01ac, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:87:0x01ad, code lost:
        r4 = r30;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:122:0x0242 A[ExcHandler: Exception (e java.lang.Exception), Splitter:B:18:0x0081] */
    /* JADX WARNING: Removed duplicated region for block: B:127:0x0256  */
    /* JADX WARNING: Removed duplicated region for block: B:84:0x01aa A[Catch:{ SuperNotCalledException -> 0x0233, Exception -> 0x0231 }, ExcHandler: Exception (e java.lang.Exception), Splitter:B:65:0x0147] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private android.app.Activity performLaunchActivity(android.app.ActivityThread.ActivityClientRecord r31, android.content.Intent r32) {
        /*
            r30 = this;
            r15 = r30
            r14 = r31
            r13 = r32
            android.content.pm.ActivityInfo r12 = r14.activityInfo
            android.app.LoadedApk r0 = r14.packageInfo
            r11 = 1
            if (r0 != 0) goto L_0x0017
            android.content.pm.ApplicationInfo r0 = r12.applicationInfo
            android.content.res.CompatibilityInfo r1 = r14.compatInfo
            android.app.LoadedApk r0 = r15.getPackageInfo((android.content.pm.ApplicationInfo) r0, (android.content.res.CompatibilityInfo) r1, (int) r11)
            r14.packageInfo = r0
        L_0x0017:
            android.content.Intent r0 = r14.intent
            android.content.ComponentName r0 = r0.getComponent()
            if (r0 != 0) goto L_0x0030
            android.content.Intent r1 = r14.intent
            android.app.Application r2 = r15.mInitialApplication
            android.content.pm.PackageManager r2 = r2.getPackageManager()
            android.content.ComponentName r0 = r1.resolveActivity(r2)
            android.content.Intent r1 = r14.intent
            r1.setComponent(r0)
        L_0x0030:
            android.content.pm.ActivityInfo r1 = r14.activityInfo
            java.lang.String r1 = r1.targetActivity
            if (r1 == 0) goto L_0x0044
            android.content.ComponentName r1 = new android.content.ComponentName
            android.content.pm.ActivityInfo r2 = r14.activityInfo
            java.lang.String r2 = r2.packageName
            android.content.pm.ActivityInfo r3 = r14.activityInfo
            java.lang.String r3 = r3.targetActivity
            r1.<init>((java.lang.String) r2, (java.lang.String) r3)
            r0 = r1
        L_0x0044:
            r9 = r0
            android.app.ContextImpl r8 = r30.createBaseContextForActivity(r31)
            r6 = 0
            r1 = r6
            java.lang.ClassLoader r0 = r8.getClassLoader()     // Catch:{ Exception -> 0x0077 }
            android.app.Instrumentation r2 = r15.mInstrumentation     // Catch:{ Exception -> 0x0077 }
            java.lang.String r3 = r9.getClassName()     // Catch:{ Exception -> 0x0077 }
            android.content.Intent r4 = r14.intent     // Catch:{ Exception -> 0x0077 }
            android.app.Activity r2 = r2.newActivity(r0, r3, r4)     // Catch:{ Exception -> 0x0077 }
            r1 = r2
            java.lang.Class r2 = r1.getClass()     // Catch:{ Exception -> 0x0077 }
            android.os.StrictMode.incrementExpectedActivityCount(r2)     // Catch:{ Exception -> 0x0077 }
            android.content.Intent r2 = r14.intent     // Catch:{ Exception -> 0x0077 }
            r2.setExtrasClassLoader(r0)     // Catch:{ Exception -> 0x0077 }
            android.content.Intent r2 = r14.intent     // Catch:{ Exception -> 0x0077 }
            r2.prepareToEnterProcess()     // Catch:{ Exception -> 0x0077 }
            android.os.Bundle r2 = r14.state     // Catch:{ Exception -> 0x0077 }
            if (r2 == 0) goto L_0x0076
            android.os.Bundle r2 = r14.state     // Catch:{ Exception -> 0x0077 }
            r2.setClassLoader(r0)     // Catch:{ Exception -> 0x0077 }
        L_0x0076:
            goto L_0x0080
        L_0x0077:
            r0 = move-exception
            android.app.Instrumentation r2 = r15.mInstrumentation
            boolean r2 = r2.onException(r1, r0)
            if (r2 == 0) goto L_0x0286
        L_0x0080:
            r5 = r1
            android.app.LoadedApk r0 = r14.packageInfo     // Catch:{ SuperNotCalledException -> 0x027b, Exception -> 0x0242 }
            android.app.Instrumentation r1 = r15.mInstrumentation     // Catch:{ SuperNotCalledException -> 0x0237, Exception -> 0x0242 }
            r4 = 0
            android.app.Application r7 = r0.makeApplication(r4, r1)     // Catch:{ SuperNotCalledException -> 0x0237, Exception -> 0x0242 }
            if (r5 == 0) goto L_0x0214
            android.content.pm.ActivityInfo r0 = r14.activityInfo     // Catch:{ SuperNotCalledException -> 0x0237, Exception -> 0x0242 }
            android.content.pm.PackageManager r1 = r8.getPackageManager()     // Catch:{ SuperNotCalledException -> 0x0237, Exception -> 0x0242 }
            java.lang.CharSequence r10 = r0.loadLabel(r1)     // Catch:{ SuperNotCalledException -> 0x0237, Exception -> 0x0242 }
            android.content.res.Configuration r0 = new android.content.res.Configuration     // Catch:{ SuperNotCalledException -> 0x0237, Exception -> 0x0242 }
            android.content.res.Configuration r1 = r15.mCompatConfiguration     // Catch:{ SuperNotCalledException -> 0x0237, Exception -> 0x0242 }
            r0.<init>((android.content.res.Configuration) r1)     // Catch:{ SuperNotCalledException -> 0x0237, Exception -> 0x0242 }
            android.content.res.Configuration r1 = r14.overrideConfig     // Catch:{ SuperNotCalledException -> 0x0237, Exception -> 0x0242 }
            if (r1 == 0) goto L_0x00c0
            android.content.res.Configuration r1 = r14.overrideConfig     // Catch:{ SuperNotCalledException -> 0x00b4, Exception -> 0x00a7 }
            r0.updateFrom(r1)     // Catch:{ SuperNotCalledException -> 0x00b4, Exception -> 0x00a7 }
            goto L_0x00c0
        L_0x00a7:
            r0 = move-exception
            r1 = r5
            r28 = r8
            r29 = r9
            r21 = r12
            r2 = r13
            r3 = r14
            r4 = r15
            goto L_0x024d
        L_0x00b4:
            r0 = move-exception
            r1 = r5
            r28 = r8
            r7 = r9
            r21 = r12
            r2 = r13
            r3 = r14
            r4 = r15
            goto L_0x0285
        L_0x00c0:
            r1 = 0
            android.view.Window r2 = r14.mPendingRemoveWindow     // Catch:{ SuperNotCalledException -> 0x0237, Exception -> 0x0242 }
            if (r2 == 0) goto L_0x00d0
            boolean r2 = r14.mPreserveWindow     // Catch:{ SuperNotCalledException -> 0x00b4, Exception -> 0x00a7 }
            if (r2 == 0) goto L_0x00d0
            android.view.Window r2 = r14.mPendingRemoveWindow     // Catch:{ SuperNotCalledException -> 0x00b4, Exception -> 0x00a7 }
            r1 = r2
            r14.mPendingRemoveWindow = r6     // Catch:{ SuperNotCalledException -> 0x00b4, Exception -> 0x00a7 }
            r14.mPendingRemoveWindowManager = r6     // Catch:{ SuperNotCalledException -> 0x00b4, Exception -> 0x00a7 }
        L_0x00d0:
            r20 = r1
            r8.setOuterContext(r5)     // Catch:{ SuperNotCalledException -> 0x0237, Exception -> 0x0242 }
            android.app.Instrumentation r16 = r30.getInstrumentation()     // Catch:{ SuperNotCalledException -> 0x0237, Exception -> 0x0242 }
            android.os.IBinder r3 = r14.token     // Catch:{ SuperNotCalledException -> 0x0237, Exception -> 0x0242 }
            int r2 = r14.ident     // Catch:{ SuperNotCalledException -> 0x0237, Exception -> 0x0242 }
            android.content.Intent r1 = r14.intent     // Catch:{ SuperNotCalledException -> 0x0237, Exception -> 0x0242 }
            android.content.pm.ActivityInfo r11 = r14.activityInfo     // Catch:{ SuperNotCalledException -> 0x0237, Exception -> 0x0242 }
            r22 = r12
            android.app.Activity r12 = r14.parent     // Catch:{ SuperNotCalledException -> 0x0208, Exception -> 0x01fb }
            java.lang.String r13 = r14.embeddedID     // Catch:{ SuperNotCalledException -> 0x01f0, Exception -> 0x01e3 }
            android.app.Activity$NonConfigurationInstances r15 = r14.lastNonConfigurationInstances     // Catch:{ SuperNotCalledException -> 0x01d7, Exception -> 0x01c9 }
            r23 = r7
            java.lang.String r7 = r14.referrer     // Catch:{ SuperNotCalledException -> 0x01d7, Exception -> 0x01c9 }
            r24 = r10
            com.android.internal.app.IVoiceInteractor r10 = r14.voiceInteractor     // Catch:{ SuperNotCalledException -> 0x01d7, Exception -> 0x01c9 }
            r25 = r10
            android.view.ViewRootImpl$ActivityConfigCallback r10 = r14.configCallback     // Catch:{ SuperNotCalledException -> 0x01d7, Exception -> 0x01c9 }
            r26 = r10
            android.os.IBinder r10 = r14.assistToken     // Catch:{ SuperNotCalledException -> 0x01d7, Exception -> 0x01c9 }
            r17 = r1
            r1 = r5
            r18 = r2
            r2 = r8
            r19 = r3
            r3 = r30
            r4 = r16
            r27 = r5
            r5 = r19
            r6 = r18
            r28 = r8
            r8 = r17
            r29 = r9
            r9 = r11
            r11 = r12
            r21 = r22
            r12 = r13
            r13 = r15
            r15 = r14
            r14 = r0
            r15 = r7
            r16 = r25
            r17 = r20
            r18 = r26
            r19 = r10
            r7 = r23
            r10 = r24
            r1.attach(r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18, r19)     // Catch:{ SuperNotCalledException -> 0x01bc, Exception -> 0x01b1 }
            r2 = r32
            if (r2 == 0) goto L_0x0142
            r1 = r27
            r1.mIntent = r2     // Catch:{ SuperNotCalledException -> 0x0139, Exception -> 0x0132 }
            goto L_0x0144
        L_0x0132:
            r0 = move-exception
            r3 = r31
        L_0x0135:
            r4 = r30
            goto L_0x024d
        L_0x0139:
            r0 = move-exception
            r7 = r29
            r3 = r31
        L_0x013e:
            r4 = r30
            goto L_0x0285
        L_0x0142:
            r1 = r27
        L_0x0144:
            r3 = r31
            r4 = 0
            r3.lastNonConfigurationInstances = r4     // Catch:{ SuperNotCalledException -> 0x01ac, Exception -> 0x01aa }
            r30.checkAndBlockForNetworkAccess()     // Catch:{ SuperNotCalledException -> 0x01ac, Exception -> 0x01aa }
            r4 = 0
            r1.mStartedActivity = r4     // Catch:{ SuperNotCalledException -> 0x01ac, Exception -> 0x01aa }
            android.content.pm.ActivityInfo r5 = r3.activityInfo     // Catch:{ SuperNotCalledException -> 0x01ac, Exception -> 0x01aa }
            int r5 = r5.getThemeResource()     // Catch:{ SuperNotCalledException -> 0x01ac, Exception -> 0x01aa }
            if (r5 == 0) goto L_0x015f
            r1.setTheme(r5)     // Catch:{ SuperNotCalledException -> 0x015b, Exception -> 0x01aa }
            goto L_0x015f
        L_0x015b:
            r0 = move-exception
            r7 = r29
            goto L_0x013e
        L_0x015f:
            r1.mCalled = r4     // Catch:{ SuperNotCalledException -> 0x01ac, Exception -> 0x01aa }
            boolean r4 = r31.isPersistable()     // Catch:{ SuperNotCalledException -> 0x01ac, Exception -> 0x01aa }
            if (r4 == 0) goto L_0x0173
            r4 = r30
            android.app.Instrumentation r6 = r4.mInstrumentation     // Catch:{ SuperNotCalledException -> 0x0233, Exception -> 0x0231 }
            android.os.Bundle r8 = r3.state     // Catch:{ SuperNotCalledException -> 0x0233, Exception -> 0x0231 }
            android.os.PersistableBundle r9 = r3.persistentState     // Catch:{ SuperNotCalledException -> 0x0233, Exception -> 0x0231 }
            r6.callActivityOnCreate(r1, r8, r9)     // Catch:{ SuperNotCalledException -> 0x0233, Exception -> 0x0231 }
            goto L_0x017c
        L_0x0173:
            r4 = r30
            android.app.Instrumentation r6 = r4.mInstrumentation     // Catch:{ SuperNotCalledException -> 0x0233, Exception -> 0x0231 }
            android.os.Bundle r8 = r3.state     // Catch:{ SuperNotCalledException -> 0x0233, Exception -> 0x0231 }
            r6.callActivityOnCreate(r1, r8)     // Catch:{ SuperNotCalledException -> 0x0233, Exception -> 0x0231 }
        L_0x017c:
            boolean r6 = r1.mCalled     // Catch:{ SuperNotCalledException -> 0x0233, Exception -> 0x0231 }
            if (r6 == 0) goto L_0x0184
            r3.activity = r1     // Catch:{ SuperNotCalledException -> 0x0233, Exception -> 0x0231 }
            goto L_0x021e
        L_0x0184:
            android.util.SuperNotCalledException r6 = new android.util.SuperNotCalledException     // Catch:{ SuperNotCalledException -> 0x0233, Exception -> 0x0231 }
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ SuperNotCalledException -> 0x0233, Exception -> 0x0231 }
            r8.<init>()     // Catch:{ SuperNotCalledException -> 0x0233, Exception -> 0x0231 }
            java.lang.String r9 = "Activity "
            r8.append(r9)     // Catch:{ SuperNotCalledException -> 0x0233, Exception -> 0x0231 }
            android.content.Intent r9 = r3.intent     // Catch:{ SuperNotCalledException -> 0x0233, Exception -> 0x0231 }
            android.content.ComponentName r9 = r9.getComponent()     // Catch:{ SuperNotCalledException -> 0x0233, Exception -> 0x0231 }
            java.lang.String r9 = r9.toShortString()     // Catch:{ SuperNotCalledException -> 0x0233, Exception -> 0x0231 }
            r8.append(r9)     // Catch:{ SuperNotCalledException -> 0x0233, Exception -> 0x0231 }
            java.lang.String r9 = " did not call through to super.onCreate()"
            r8.append(r9)     // Catch:{ SuperNotCalledException -> 0x0233, Exception -> 0x0231 }
            java.lang.String r8 = r8.toString()     // Catch:{ SuperNotCalledException -> 0x0233, Exception -> 0x0231 }
            r6.<init>(r8)     // Catch:{ SuperNotCalledException -> 0x0233, Exception -> 0x0231 }
            throw r6     // Catch:{ SuperNotCalledException -> 0x0233, Exception -> 0x0231 }
        L_0x01aa:
            r0 = move-exception
            goto L_0x0135
        L_0x01ac:
            r0 = move-exception
            r4 = r30
            goto L_0x0234
        L_0x01b1:
            r0 = move-exception
            r1 = r27
            r2 = r32
            r3 = r31
            r4 = r30
            goto L_0x024d
        L_0x01bc:
            r0 = move-exception
            r1 = r27
            r2 = r32
            r3 = r31
            r4 = r30
            r7 = r29
            goto L_0x0285
        L_0x01c9:
            r0 = move-exception
            r1 = r5
            r28 = r8
            r29 = r9
            r3 = r14
            r21 = r22
            r2 = r32
            r4 = r30
            goto L_0x0206
        L_0x01d7:
            r0 = move-exception
            r1 = r5
            r28 = r8
            r3 = r14
            r21 = r22
            r2 = r32
            r4 = r30
            goto L_0x0211
        L_0x01e3:
            r0 = move-exception
            r1 = r5
            r28 = r8
            r29 = r9
            r3 = r14
            r4 = r15
            r21 = r22
            r2 = r32
            goto L_0x0206
        L_0x01f0:
            r0 = move-exception
            r1 = r5
            r28 = r8
            r3 = r14
            r4 = r15
            r21 = r22
            r2 = r32
            goto L_0x0211
        L_0x01fb:
            r0 = move-exception
            r1 = r5
            r28 = r8
            r29 = r9
            r2 = r13
            r3 = r14
            r4 = r15
            r21 = r22
        L_0x0206:
            goto L_0x024d
        L_0x0208:
            r0 = move-exception
            r1 = r5
            r28 = r8
            r2 = r13
            r3 = r14
            r4 = r15
            r21 = r22
        L_0x0211:
            r7 = r9
            goto L_0x0285
        L_0x0214:
            r1 = r5
            r28 = r8
            r29 = r9
            r21 = r12
            r2 = r13
            r3 = r14
            r4 = r15
        L_0x021e:
            r5 = 1
            r3.setState(r5)     // Catch:{ SuperNotCalledException -> 0x0233, Exception -> 0x0231 }
            android.app.ResourcesManager r5 = r4.mResourcesManager     // Catch:{ SuperNotCalledException -> 0x0233, Exception -> 0x0231 }
            monitor-enter(r5)     // Catch:{ SuperNotCalledException -> 0x0233, Exception -> 0x0231 }
            android.util.ArrayMap<android.os.IBinder, android.app.ActivityThread$ActivityClientRecord> r0 = r4.mActivities     // Catch:{ all -> 0x022e }
            android.os.IBinder r6 = r3.token     // Catch:{ all -> 0x022e }
            r0.put(r6, r3)     // Catch:{ all -> 0x022e }
            monitor-exit(r5)     // Catch:{ all -> 0x022e }
            goto L_0x0255
        L_0x022e:
            r0 = move-exception
            monitor-exit(r5)     // Catch:{ all -> 0x022e }
            throw r0     // Catch:{ SuperNotCalledException -> 0x0233, Exception -> 0x0231 }
        L_0x0231:
            r0 = move-exception
            goto L_0x024d
        L_0x0233:
            r0 = move-exception
        L_0x0234:
            r7 = r29
            goto L_0x0285
        L_0x0237:
            r0 = move-exception
            r1 = r5
            r28 = r8
            r21 = r12
            r2 = r13
            r3 = r14
            r4 = r15
            r7 = r9
            goto L_0x0285
        L_0x0242:
            r0 = move-exception
            r1 = r5
            r28 = r8
            r29 = r9
            r21 = r12
            r2 = r13
            r3 = r14
            r4 = r15
        L_0x024d:
            android.app.Instrumentation r5 = r4.mInstrumentation
            boolean r5 = r5.onException(r1, r0)
            if (r5 == 0) goto L_0x0256
        L_0x0255:
            return r1
        L_0x0256:
            java.lang.RuntimeException r5 = new java.lang.RuntimeException
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = "Unable to start activity "
            r6.append(r7)
            r7 = r29
            r6.append(r7)
            java.lang.String r8 = ": "
            r6.append(r8)
            java.lang.String r8 = r0.toString()
            r6.append(r8)
            java.lang.String r6 = r6.toString()
            r5.<init>(r6, r0)
            throw r5
        L_0x027b:
            r0 = move-exception
            r1 = r5
            r28 = r8
            r7 = r9
            r21 = r12
            r2 = r13
            r3 = r14
            r4 = r15
        L_0x0285:
            throw r0
        L_0x0286:
            r28 = r8
            r7 = r9
            r21 = r12
            r2 = r13
            r3 = r14
            r4 = r15
            java.lang.RuntimeException r5 = new java.lang.RuntimeException
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r8 = "Unable to instantiate activity "
            r6.append(r8)
            r6.append(r7)
            java.lang.String r8 = ": "
            r6.append(r8)
            java.lang.String r8 = r0.toString()
            r6.append(r8)
            java.lang.String r6 = r6.toString()
            r5.<init>(r6, r0)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: android.app.ActivityThread.performLaunchActivity(android.app.ActivityThread$ActivityClientRecord, android.content.Intent):android.app.Activity");
    }

    public void handleStartActivity(ActivityClientRecord r, PendingTransactionActions pendingActions) {
        Activity activity = r.activity;
        if (r.activity != null) {
            if (!r.stopped) {
                throw new IllegalStateException("Can't start activity that is not stopped.");
            } else if (!r.activity.mFinished) {
                activity.performStart("handleStartActivity");
                r.setState(2);
                if (pendingActions != null) {
                    if (pendingActions.shouldRestoreInstanceState()) {
                        if (r.isPersistable()) {
                            if (!(r.state == null && r.persistentState == null)) {
                                this.mInstrumentation.callActivityOnRestoreInstanceState(activity, r.state, r.persistentState);
                            }
                        } else if (r.state != null) {
                            this.mInstrumentation.callActivityOnRestoreInstanceState(activity, r.state);
                        }
                    }
                    if (pendingActions.shouldCallOnPostCreate()) {
                        activity.mCalled = false;
                        if (r.isPersistable()) {
                            this.mInstrumentation.callActivityOnPostCreate(activity, r.state, r.persistentState);
                        } else {
                            this.mInstrumentation.callActivityOnPostCreate(activity, r.state);
                        }
                        if (!activity.mCalled) {
                            throw new SuperNotCalledException("Activity " + r.intent.getComponent().toShortString() + " did not call through to super.onPostCreate()");
                        }
                    }
                }
            }
        }
    }

    private void checkAndBlockForNetworkAccess() {
        synchronized (this.mNetworkPolicyLock) {
            if (this.mNetworkBlockSeq != -1) {
                try {
                    ActivityManager.getService().waitForNetworkStateUpdate(this.mNetworkBlockSeq);
                    this.mNetworkBlockSeq = -1;
                } catch (RemoteException e) {
                }
            }
        }
    }

    private ContextImpl createBaseContextForActivity(ActivityClientRecord r) {
        try {
            ContextImpl appContext = ContextImpl.createActivityContext(this, r.packageInfo, r.activityInfo, r.token, ActivityTaskManager.getService().getActivityDisplayId(r.token), r.overrideConfig);
            DisplayManagerGlobal dm = DisplayManagerGlobal.getInstance();
            String pkgName = SystemProperties.get("debug.second-display.pkg");
            if (pkgName == null || pkgName.isEmpty() || !r.packageInfo.mPackageName.contains(pkgName)) {
                return appContext;
            }
            for (int id : dm.getDisplayIds()) {
                if (id != 0) {
                    return (ContextImpl) appContext.createDisplayContext(dm.getCompatibleDisplay(id, appContext.getResources()));
                }
            }
            return appContext;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public Activity handleLaunchActivity(ActivityClientRecord r, PendingTransactionActions pendingActions, Intent customIntent) {
        unscheduleGcIdler();
        this.mSomeActivitiesChanged = true;
        if (r.profilerInfo != null) {
            this.mProfiler.setProfiler(r.profilerInfo);
            this.mProfiler.startProfiling();
        }
        handleConfigurationChanged((Configuration) null, (CompatibilityInfo) null);
        if (!ThreadedRenderer.sRendererDisabled && (r.activityInfo.flags & 512) != 0) {
            HardwareRenderer.preload();
        }
        WindowManagerGlobal.initialize();
        GraphicsEnvironment.hintActivityLaunch();
        Activity a = performLaunchActivity(r, customIntent);
        if (a != null) {
            r.createdConfig = new Configuration(this.mConfiguration);
            reportSizeConfigurations(r);
            if (!r.activity.mFinished && pendingActions != null) {
                pendingActions.setOldState(r.state);
                pendingActions.setRestoreInstanceState(true);
                pendingActions.setCallOnPostCreate(true);
            }
        } else {
            try {
                ActivityTaskManager.getService().finishActivity(r.token, 0, (Intent) null, 0);
            } catch (RemoteException ex) {
                throw ex.rethrowFromSystemServer();
            }
        }
        return a;
    }

    private void reportSizeConfigurations(ActivityClientRecord r) {
        Configuration[] configurations;
        if (!this.mActivitiesToBeDestroyed.containsKey(r.token) && (configurations = r.activity.getResources().getSizeConfigurations()) != null) {
            SparseIntArray horizontal = new SparseIntArray();
            SparseIntArray vertical = new SparseIntArray();
            SparseIntArray smallest = new SparseIntArray();
            for (int i = configurations.length - 1; i >= 0; i--) {
                Configuration config = configurations[i];
                if (config.screenHeightDp != 0) {
                    vertical.put(config.screenHeightDp, 0);
                }
                if (config.screenWidthDp != 0) {
                    horizontal.put(config.screenWidthDp, 0);
                }
                if (config.smallestScreenWidthDp != 0) {
                    smallest.put(config.smallestScreenWidthDp, 0);
                }
            }
            try {
                ActivityTaskManager.getService().reportSizeConfigurations(r.token, horizontal.copyKeys(), vertical.copyKeys(), smallest.copyKeys());
            } catch (RemoteException ex) {
                throw ex.rethrowFromSystemServer();
            }
        }
    }

    private void deliverNewIntents(ActivityClientRecord r, List<ReferrerIntent> intents) {
        int N = intents.size();
        for (int i = 0; i < N; i++) {
            ReferrerIntent intent = intents.get(i);
            intent.setExtrasClassLoader(r.activity.getClassLoader());
            intent.prepareToEnterProcess();
            r.activity.mFragments.noteStateNotSaved();
            this.mInstrumentation.callActivityOnNewIntent(r.activity, intent);
        }
    }

    public void handleNewIntent(IBinder token, List<ReferrerIntent> intents) {
        ActivityClientRecord r = this.mActivities.get(token);
        if (r != null) {
            checkAndBlockForNetworkAccess();
            deliverNewIntents(r, intents);
        }
    }

    public void handleRequestAssistContextExtras(RequestAssistContextExtras cmd) {
        AssistStructure structure;
        boolean notSecure = false;
        boolean forAutofill = cmd.requestType == 2;
        if (this.mLastSessionId != cmd.sessionId) {
            this.mLastSessionId = cmd.sessionId;
            for (int i = this.mLastAssistStructures.size() - 1; i >= 0; i--) {
                AssistStructure structure2 = (AssistStructure) this.mLastAssistStructures.get(i).get();
                if (structure2 != null) {
                    structure2.clearSendChannel();
                }
                this.mLastAssistStructures.remove(i);
            }
        }
        Bundle data = new Bundle();
        AssistStructure structure3 = null;
        AssistContent content = forAutofill ? null : new AssistContent();
        long startTime = SystemClock.uptimeMillis();
        ActivityClientRecord r = this.mActivities.get(cmd.activityToken);
        Uri referrer = null;
        if (r != null) {
            if (!forAutofill) {
                r.activity.getApplication().dispatchOnProvideAssistData(r.activity, data);
                r.activity.onProvideAssistData(data);
                referrer = r.activity.onProvideReferrer();
            }
            if (cmd.requestType == 1 || forAutofill) {
                structure3 = new AssistStructure(r.activity, forAutofill, cmd.flags);
                Intent activityIntent = r.activity.getIntent();
                if (r.window == null || (r.window.getAttributes().flags & 8192) == 0) {
                    notSecure = true;
                }
                if (activityIntent == null || !notSecure) {
                    if (!forAutofill) {
                        content.setDefaultIntent(new Intent());
                    }
                } else if (!forAutofill) {
                    Intent intent = new Intent(activityIntent);
                    intent.setFlags(intent.getFlags() & -67);
                    intent.removeUnsafeExtras();
                    content.setDefaultIntent(intent);
                }
                if (!forAutofill) {
                    r.activity.onProvideAssistContent(content);
                }
            }
        }
        Uri referrer2 = referrer;
        if (structure3 == null) {
            structure = new AssistStructure();
        } else {
            structure = structure3;
        }
        structure.setAcquisitionStartTime(startTime);
        structure.setAcquisitionEndTime(SystemClock.uptimeMillis());
        this.mLastAssistStructures.add(new WeakReference(structure));
        try {
            ActivityTaskManager.getService().reportAssistContextExtras(cmd.requestToken, data, structure, content, referrer2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* access modifiers changed from: private */
    public void handleRequestDirectActions(IBinder activityToken, IVoiceInteractor interactor, CancellationSignal cancellationSignal, RemoteCallback callback) {
        ActivityClientRecord r = this.mActivities.get(activityToken);
        if (r == null) {
            Log.w(TAG, "requestDirectActions(): no activity for " + activityToken);
            callback.sendResult((Bundle) null);
            return;
        }
        int lifecycleState = r.getLifecycleState();
        if (lifecycleState < 2 || lifecycleState >= 5) {
            Log.w(TAG, "requestDirectActions(" + r + "): wrong lifecycle: " + lifecycleState);
            callback.sendResult((Bundle) null);
            return;
        }
        if (r.activity.mVoiceInteractor == null || r.activity.mVoiceInteractor.mInteractor.asBinder() != interactor.asBinder()) {
            if (r.activity.mVoiceInteractor != null) {
                r.activity.mVoiceInteractor.destroy();
            }
            r.activity.mVoiceInteractor = new VoiceInteractor(interactor, r.activity, r.activity, Looper.myLooper());
        }
        r.activity.onGetDirectActions(cancellationSignal, new Consumer(callback) {
            private final /* synthetic */ RemoteCallback f$1;

            {
                this.f$1 = r2;
            }

            public final void accept(Object obj) {
                ActivityThread.lambda$handleRequestDirectActions$0(ActivityThread.ActivityClientRecord.this, this.f$1, (List) obj);
            }
        });
    }

    static /* synthetic */ void lambda$handleRequestDirectActions$0(ActivityClientRecord r, RemoteCallback callback, List actions) {
        Preconditions.checkNotNull(actions);
        Preconditions.checkCollectionElementsNotNull(actions, Slice.HINT_ACTIONS);
        if (!actions.isEmpty()) {
            int actionCount = actions.size();
            for (int i = 0; i < actionCount; i++) {
                ((DirectAction) actions.get(i)).setSource(r.activity.getTaskId(), r.activity.getAssistToken());
            }
            Bundle result = new Bundle();
            result.putParcelable(DirectAction.KEY_ACTIONS_LIST, new ParceledListSlice(actions));
            callback.sendResult(result);
            return;
        }
        callback.sendResult((Bundle) null);
    }

    /* access modifiers changed from: private */
    public void handlePerformDirectAction(IBinder activityToken, String actionId, Bundle arguments, CancellationSignal cancellationSignal, RemoteCallback resultCallback) {
        ActivityClientRecord r = this.mActivities.get(activityToken);
        if (r != null) {
            int lifecycleState = r.getLifecycleState();
            if (lifecycleState < 2 || lifecycleState >= 5) {
                resultCallback.sendResult((Bundle) null);
                return;
            }
            Bundle nonNullArguments = arguments != null ? arguments : Bundle.EMPTY;
            Activity activity = r.activity;
            Objects.requireNonNull(resultCallback);
            activity.onPerformDirectAction(actionId, nonNullArguments, cancellationSignal, new Consumer() {
                public final void accept(Object obj) {
                    RemoteCallback.this.sendResult((Bundle) obj);
                }
            });
            return;
        }
        resultCallback.sendResult((Bundle) null);
    }

    public void handleTranslucentConversionComplete(IBinder token, boolean drawComplete) {
        ActivityClientRecord r = this.mActivities.get(token);
        if (r != null) {
            r.activity.onTranslucentConversionComplete(drawComplete);
        }
    }

    public void onNewActivityOptions(IBinder token, ActivityOptions options) {
        ActivityClientRecord r = this.mActivities.get(token);
        if (r != null) {
            r.activity.onNewActivityOptions(options);
        }
    }

    public void handleInstallProvider(ProviderInfo info) {
        StrictMode.ThreadPolicy oldPolicy = StrictMode.allowThreadDiskWrites();
        try {
            installContentProviders(this.mInitialApplication, Arrays.asList(new ProviderInfo[]{info}));
        } finally {
            StrictMode.setThreadPolicy(oldPolicy);
        }
    }

    /* access modifiers changed from: private */
    public void handleEnterAnimationComplete(IBinder token) {
        ActivityClientRecord r = this.mActivities.get(token);
        if (r != null) {
            r.activity.dispatchEnterAnimationComplete();
        }
    }

    /* access modifiers changed from: private */
    public void handleStartBinderTracking() {
        Binder.enableTracing();
    }

    /* access modifiers changed from: private */
    public void handleStopBinderTrackingAndDump(ParcelFileDescriptor fd) {
        try {
            Binder.disableTracing();
            Binder.getTransactionTracker().writeTracesToFile(fd);
        } finally {
            IoUtils.closeQuietly(fd);
            Binder.getTransactionTracker().clearTraces();
        }
    }

    public void handleMultiWindowModeChanged(IBinder token, boolean isInMultiWindowMode, Configuration overrideConfig) {
        ActivityClientRecord r = this.mActivities.get(token);
        if (r != null) {
            Configuration newConfig = new Configuration(this.mConfiguration);
            if (overrideConfig != null) {
                newConfig.updateFrom(overrideConfig);
            }
            r.activity.dispatchMultiWindowModeChanged(isInMultiWindowMode, newConfig);
        }
    }

    public void handlePictureInPictureModeChanged(IBinder token, boolean isInPipMode, Configuration overrideConfig) {
        ActivityClientRecord r = this.mActivities.get(token);
        if (r != null) {
            Configuration newConfig = new Configuration(this.mConfiguration);
            if (overrideConfig != null) {
                newConfig.updateFrom(overrideConfig);
            }
            r.activity.dispatchPictureInPictureModeChanged(isInPipMode, newConfig);
        }
    }

    /* access modifiers changed from: private */
    public void handleLocalVoiceInteractionStarted(IBinder token, IVoiceInteractor interactor) {
        ActivityClientRecord r = this.mActivities.get(token);
        if (r != null) {
            r.voiceInteractor = interactor;
            r.activity.setVoiceInteractor(interactor);
            if (interactor == null) {
                r.activity.onLocalVoiceInteractionStopped();
            } else {
                r.activity.onLocalVoiceInteractionStarted();
            }
        }
    }

    private static boolean attemptAttachAgent(String agent, ClassLoader classLoader) {
        try {
            VMDebug.attachAgent(agent, classLoader);
            return true;
        } catch (IOException e) {
            Slog.e(TAG, "Attaching agent with " + classLoader + " failed: " + agent);
            return false;
        }
    }

    static void handleAttachAgent(String agent, LoadedApk loadedApk) {
        ClassLoader classLoader = loadedApk != null ? loadedApk.getClassLoader() : null;
        if (!attemptAttachAgent(agent, classLoader) && classLoader != null) {
            attemptAttachAgent(agent, (ClassLoader) null);
        }
    }

    public static Intent getIntentBeingBroadcast() {
        return sCurrentBroadcastIntent.get();
    }

    /* access modifiers changed from: private */
    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
    public void handleReceiver(ReceiverData data) {
        unscheduleGcIdler();
        String component = data.intent.getComponent().getClassName();
        LoadedApk packageInfo = getPackageInfoNoCheck(data.info.applicationInfo, data.compatInfo);
        IActivityManager mgr = ActivityManager.getService();
        try {
            ContextImpl context = (ContextImpl) packageInfo.makeApplication(false, this.mInstrumentation).getBaseContext();
            if (data.info.splitName != null) {
                context = (ContextImpl) context.createContextForSplit(data.info.splitName);
            }
            ClassLoader cl = context.getClassLoader();
            data.intent.setExtrasClassLoader(cl);
            data.intent.prepareToEnterProcess();
            data.setExtrasClassLoader(cl);
            BroadcastReceiver receiver = packageInfo.getAppFactory().instantiateReceiver(cl, data.info.name, data.intent);
            try {
                sCurrentBroadcastIntent.set(data.intent);
                receiver.setPendingResult(data);
                receiver.onReceive(context.getReceiverRestrictedContext(), data.intent);
            } catch (Exception e) {
                data.sendFinished(mgr);
                if (!this.mInstrumentation.onException(receiver, e)) {
                    throw new RuntimeException("Unable to start receiver " + component + PluralRules.KEYWORD_RULE_SEPARATOR + e.toString(), e);
                }
            } catch (Throwable th) {
                sCurrentBroadcastIntent.set((Object) null);
                throw th;
            }
            sCurrentBroadcastIntent.set((Object) null);
            if (receiver.getPendingResult() != null) {
                data.finish();
            }
        } catch (Exception e2) {
            data.sendFinished(mgr);
            throw new RuntimeException("Unable to instantiate receiver " + component + PluralRules.KEYWORD_RULE_SEPARATOR + e2.toString(), e2);
        }
    }

    /* access modifiers changed from: private */
    public void handleCreateBackupAgent(CreateBackupAgentData data) {
        try {
            if (getPackageManager().getPackageInfo(data.appInfo.packageName, 0, UserHandle.myUserId()).applicationInfo.uid != Process.myUid()) {
                Slog.w(TAG, "Asked to instantiate non-matching package " + data.appInfo.packageName);
                return;
            }
            unscheduleGcIdler();
            LoadedApk packageInfo = getPackageInfoNoCheck(data.appInfo, data.compatInfo);
            String packageName = packageInfo.mPackageName;
            if (packageName == null) {
                Slog.d(TAG, "Asked to create backup agent for nonexistent package");
                return;
            }
            String classname = data.appInfo.backupAgentName;
            if (classname == null && (data.backupMode == 1 || data.backupMode == 3)) {
                classname = "android.app.backup.FullBackupAgent";
            }
            IBinder binder = null;
            try {
                ArrayMap<String, BackupAgent> backupAgents = getBackupAgentsForUser(data.userId);
                BackupAgent agent = backupAgents.get(packageName);
                if (agent != null) {
                    binder = agent.onBind();
                } else {
                    BackupAgent agent2 = (BackupAgent) packageInfo.getClassLoader().loadClass(classname).newInstance();
                    ContextImpl context = ContextImpl.createAppContext(this, packageInfo);
                    context.setOuterContext(agent2);
                    agent2.attach(context);
                    agent2.onCreate(UserHandle.of(data.userId));
                    binder = agent2.onBind();
                    backupAgents.put(packageName, agent2);
                }
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            } catch (Exception e2) {
                Slog.e(TAG, "Agent threw during creation: " + e2);
                if (data.backupMode != 2) {
                    if (data.backupMode != 3) {
                        throw e2;
                    }
                }
            } catch (Exception e3) {
                throw new RuntimeException("Unable to create BackupAgent " + classname + PluralRules.KEYWORD_RULE_SEPARATOR + e3.toString(), e3);
            }
            ActivityManager.getService().backupAgentCreated(packageName, binder, data.userId);
        } catch (RemoteException e4) {
            throw e4.rethrowFromSystemServer();
        }
    }

    /* access modifiers changed from: private */
    public void handleDestroyBackupAgent(CreateBackupAgentData data) {
        String packageName = getPackageInfoNoCheck(data.appInfo, data.compatInfo).mPackageName;
        ArrayMap<String, BackupAgent> backupAgents = getBackupAgentsForUser(data.userId);
        BackupAgent agent = backupAgents.get(packageName);
        if (agent != null) {
            try {
                agent.onDestroy();
            } catch (Exception e) {
                Slog.w(TAG, "Exception thrown in onDestroy by backup agent of " + data.appInfo);
                e.printStackTrace();
            }
            backupAgents.remove(packageName);
            return;
        }
        Slog.w(TAG, "Attempt to destroy unknown backup agent " + data);
    }

    private ArrayMap<String, BackupAgent> getBackupAgentsForUser(int userId) {
        ArrayMap<String, BackupAgent> backupAgents = this.mBackupAgentsByUser.get(userId);
        if (backupAgents != null) {
            return backupAgents;
        }
        ArrayMap<String, BackupAgent> backupAgents2 = new ArrayMap<>();
        this.mBackupAgentsByUser.put(userId, backupAgents2);
        return backupAgents2;
    }

    /* access modifiers changed from: private */
    @UnsupportedAppUsage
    public void handleCreateService(CreateServiceData data) {
        unscheduleGcIdler();
        LoadedApk packageInfo = getPackageInfoNoCheck(data.info.applicationInfo, data.compatInfo);
        Service service = null;
        try {
            service = packageInfo.getAppFactory().instantiateService(packageInfo.getClassLoader(), data.info.name, data.intent);
        } catch (Exception e) {
            if (!this.mInstrumentation.onException((Object) null, e)) {
                throw new RuntimeException("Unable to instantiate service " + data.info.name + PluralRules.KEYWORD_RULE_SEPARATOR + e.toString(), e);
            }
        }
        try {
            ContextImpl context = ContextImpl.createAppContext(this, packageInfo);
            context.setOuterContext(service);
            Service service2 = service;
            ContextImpl contextImpl = context;
            service2.attach(contextImpl, this, data.info.name, data.token, packageInfo.makeApplication(false, this.mInstrumentation), ActivityManager.getService());
            service.onCreate();
            this.mServices.put(data.token, service);
            ActivityManager.getService().serviceDoneExecuting(data.token, 0, 0, 0);
        } catch (RemoteException e2) {
            throw e2.rethrowFromSystemServer();
        } catch (Exception e3) {
            if (!this.mInstrumentation.onException(service, e3)) {
                throw new RuntimeException("Unable to create service " + data.info.name + PluralRules.KEYWORD_RULE_SEPARATOR + e3.toString(), e3);
            }
        }
    }

    /* access modifiers changed from: private */
    public void handleBindService(BindServiceData data) {
        Service s = this.mServices.get(data.token);
        if (s != null) {
            try {
                data.intent.setExtrasClassLoader(s.getClassLoader());
                data.intent.prepareToEnterProcess();
                if (!data.rebind) {
                    ActivityManager.getService().publishService(data.token, data.intent, s.onBind(data.intent));
                    return;
                }
                s.onRebind(data.intent);
                ActivityManager.getService().serviceDoneExecuting(data.token, 0, 0, 0);
            } catch (RemoteException ex) {
                throw ex.rethrowFromSystemServer();
            } catch (Exception e) {
                if (!this.mInstrumentation.onException(s, e)) {
                    throw new RuntimeException("Unable to bind to service " + s + " with " + data.intent + PluralRules.KEYWORD_RULE_SEPARATOR + e.toString(), e);
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public void handleUnbindService(BindServiceData data) {
        Service s = this.mServices.get(data.token);
        if (s != null) {
            try {
                data.intent.setExtrasClassLoader(s.getClassLoader());
                data.intent.prepareToEnterProcess();
                boolean doRebind = s.onUnbind(data.intent);
                if (doRebind) {
                    ActivityManager.getService().unbindFinished(data.token, data.intent, doRebind);
                } else {
                    ActivityManager.getService().serviceDoneExecuting(data.token, 0, 0, 0);
                }
            } catch (RemoteException ex) {
                throw ex.rethrowFromSystemServer();
            } catch (Exception e) {
                if (!this.mInstrumentation.onException(s, e)) {
                    throw new RuntimeException("Unable to unbind to service " + s + " with " + data.intent + PluralRules.KEYWORD_RULE_SEPARATOR + e.toString(), e);
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public void handleDumpService(DumpComponentInfo info) {
        StrictMode.ThreadPolicy oldPolicy = StrictMode.allowThreadDiskWrites();
        try {
            Service s = this.mServices.get(info.token);
            if (s != null) {
                PrintWriter pw = new FastPrintWriter((OutputStream) new FileOutputStream(info.fd.getFileDescriptor()));
                s.dump(info.fd.getFileDescriptor(), pw, info.args);
                pw.flush();
            }
        } finally {
            IoUtils.closeQuietly(info.fd);
            StrictMode.setThreadPolicy(oldPolicy);
        }
    }

    /* access modifiers changed from: private */
    public void handleDumpActivity(DumpComponentInfo info) {
        StrictMode.ThreadPolicy oldPolicy = StrictMode.allowThreadDiskWrites();
        try {
            ActivityClientRecord r = this.mActivities.get(info.token);
            if (!(r == null || r.activity == null)) {
                PrintWriter pw = new FastPrintWriter((OutputStream) new FileOutputStream(info.fd.getFileDescriptor()));
                r.activity.dump(info.prefix, info.fd.getFileDescriptor(), pw, info.args);
                pw.flush();
            }
        } finally {
            IoUtils.closeQuietly(info.fd);
            StrictMode.setThreadPolicy(oldPolicy);
        }
    }

    /* access modifiers changed from: private */
    public void handleDumpProvider(DumpComponentInfo info) {
        StrictMode.ThreadPolicy oldPolicy = StrictMode.allowThreadDiskWrites();
        try {
            ProviderClientRecord r = this.mLocalProviders.get(info.token);
            if (!(r == null || r.mLocalProvider == null)) {
                PrintWriter pw = new FastPrintWriter((OutputStream) new FileOutputStream(info.fd.getFileDescriptor()));
                r.mLocalProvider.dump(info.fd.getFileDescriptor(), pw, info.args);
                pw.flush();
            }
        } finally {
            IoUtils.closeQuietly(info.fd);
            StrictMode.setThreadPolicy(oldPolicy);
        }
    }

    /* access modifiers changed from: private */
    public void handleServiceArgs(ServiceArgsData data) {
        int res;
        Service s = this.mServices.get(data.token);
        if (s != null) {
            try {
                if (data.args != null) {
                    data.args.setExtrasClassLoader(s.getClassLoader());
                    data.args.prepareToEnterProcess();
                }
                if (!data.taskRemoved) {
                    res = s.onStartCommand(data.args, data.flags, data.startId);
                } else {
                    s.onTaskRemoved(data.args);
                    res = 1000;
                }
                QueuedWork.waitToFinish();
                ActivityManager.getService().serviceDoneExecuting(data.token, 1, data.startId, res);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            } catch (Exception e2) {
                if (!this.mInstrumentation.onException(s, e2)) {
                    throw new RuntimeException("Unable to start service " + s + " with " + data.args + PluralRules.KEYWORD_RULE_SEPARATOR + e2.toString(), e2);
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public void handleStopService(IBinder token) {
        Service s = this.mServices.remove(token);
        if (s != null) {
            try {
                s.onDestroy();
                s.detachAndCleanUp();
                Context context = s.getBaseContext();
                if (context instanceof ContextImpl) {
                    ((ContextImpl) context).scheduleFinalCleanup(s.getClassName(), "Service");
                }
                QueuedWork.waitToFinish();
                ActivityManager.getService().serviceDoneExecuting(token, 2, 0, 0);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            } catch (Exception e2) {
                if (this.mInstrumentation.onException(s, e2)) {
                    Slog.i(TAG, "handleStopService: exception for " + token, e2);
                    return;
                }
                throw new RuntimeException("Unable to stop service " + s + PluralRules.KEYWORD_RULE_SEPARATOR + e2.toString(), e2);
            }
        } else {
            Slog.i(TAG, "handleStopService: token=" + token + " not found.");
        }
    }

    @VisibleForTesting
    public ActivityClientRecord performResumeActivity(IBinder token, boolean finalStateRequest, String reason) {
        ActivityClientRecord r = this.mActivities.get(token);
        if (r == null || r.activity.mFinished) {
            return null;
        }
        if (r.getLifecycleState() == 3) {
            if (!finalStateRequest) {
                RuntimeException e = new IllegalStateException("Trying to resume activity which is already resumed");
                Slog.e(TAG, e.getMessage(), e);
                Slog.e(TAG, r.getStateString());
            }
            return null;
        }
        if (finalStateRequest) {
            r.hideForNow = false;
            r.activity.mStartedActivity = false;
        }
        try {
            r.activity.onStateNotSaved();
            r.activity.mFragments.noteStateNotSaved();
            checkAndBlockForNetworkAccess();
            if (r.pendingIntents != null) {
                deliverNewIntents(r, r.pendingIntents);
                r.pendingIntents = null;
            }
            if (r.pendingResults != null) {
                deliverResults(r, r.pendingResults, reason);
                r.pendingResults = null;
            }
            r.activity.performResume(r.startsNotResumed, reason);
            r.state = null;
            r.persistentState = null;
            r.setState(3);
            reportTopResumedActivityChanged(r, r.isTopResumedActivity, "topWhenResuming");
        } catch (Exception e2) {
            if (!this.mInstrumentation.onException(r.activity, e2)) {
                throw new RuntimeException("Unable to resume activity " + r.intent.getComponent().toShortString() + PluralRules.KEYWORD_RULE_SEPARATOR + e2.toString(), e2);
            }
        }
        return r;
    }

    static final void cleanUpPendingRemoveWindows(ActivityClientRecord r, boolean force) {
        if (!r.mPreserveWindow || force) {
            if (r.mPendingRemoveWindow != null) {
                r.mPendingRemoveWindowManager.removeViewImmediate(r.mPendingRemoveWindow.getDecorView());
                IBinder wtoken = r.mPendingRemoveWindow.getDecorView().getWindowToken();
                if (wtoken != null) {
                    WindowManagerGlobal.getInstance().closeAll(wtoken, r.activity.getClass().getName(), "Activity");
                }
            }
            r.mPendingRemoveWindow = null;
            r.mPendingRemoveWindowManager = null;
        }
    }

    public void handleResumeActivity(IBinder token, boolean finalStateRequest, boolean isForward, String reason) {
        unscheduleGcIdler();
        this.mSomeActivitiesChanged = true;
        ActivityClientRecord r = performResumeActivity(token, finalStateRequest, reason);
        if (r != null && !this.mActivitiesToBeDestroyed.containsKey(token)) {
            Activity a = r.activity;
            int forwardBit = isForward ? 256 : 0;
            boolean willBeVisible = !a.mStartedActivity;
            if (!willBeVisible) {
                try {
                    willBeVisible = ActivityTaskManager.getService().willActivityBeVisible(a.getActivityToken());
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
            if (r.window == null && !a.mFinished && willBeVisible) {
                r.window = r.activity.getWindow();
                View decor = r.window.getDecorView();
                decor.setVisibility(4);
                ViewManager wm = a.getWindowManager();
                WindowManager.LayoutParams l = r.window.getAttributes();
                a.mDecor = decor;
                l.type = 1;
                l.softInputMode |= forwardBit;
                if (r.mPreserveWindow) {
                    a.mWindowAdded = true;
                    r.mPreserveWindow = false;
                    ViewRootImpl impl = decor.getViewRootImpl();
                    if (impl != null) {
                        impl.notifyChildRebuilt();
                    }
                }
                if (a.mVisibleFromClient) {
                    if (!a.mWindowAdded) {
                        a.mWindowAdded = true;
                        wm.addView(decor, l);
                    } else {
                        a.onWindowAttributesChanged(l);
                    }
                }
            } else if (!willBeVisible) {
                r.hideForNow = true;
            }
            cleanUpPendingRemoveWindows(r, false);
            if (!r.activity.mFinished && willBeVisible && r.activity.mDecor != null && !r.hideForNow) {
                if (r.newConfig != null) {
                    performConfigurationChangedForActivity(r, r.newConfig);
                    r.newConfig = null;
                }
                WindowManager.LayoutParams l2 = r.window.getAttributes();
                if ((256 & l2.softInputMode) != forwardBit) {
                    l2.softInputMode = (l2.softInputMode & TrafficStats.TAG_NETWORK_STACK_RANGE_END) | forwardBit;
                    if (r.activity.mVisibleFromClient) {
                        a.getWindowManager().updateViewLayout(r.window.getDecorView(), l2);
                    }
                }
                r.activity.mVisibleFromServer = true;
                this.mNumVisibleActivities++;
                if (r.activity.mVisibleFromClient) {
                    r.activity.makeVisible();
                }
            }
            r.nextIdle = this.mNewActivities;
            this.mNewActivities = r;
            Looper.myQueue().addIdleHandler(new Idler());
        }
    }

    public void handleTopResumedActivityChanged(IBinder token, boolean onTop, String reason) {
        ActivityClientRecord r = this.mActivities.get(token);
        if (r == null || r.activity == null) {
            Slog.w(TAG, "Not found target activity to report position change for token: " + token);
        } else if (r.isTopResumedActivity != onTop) {
            r.isTopResumedActivity = onTop;
            if (r.getLifecycleState() == 3) {
                reportTopResumedActivityChanged(r, onTop, "topStateChangedWhenResumed");
            }
        } else {
            throw new IllegalStateException("Activity top position already set to onTop=" + onTop);
        }
    }

    private void reportTopResumedActivityChanged(ActivityClientRecord r, boolean onTop, String reason) {
        if (r.lastReportedTopResumedState != onTop) {
            r.lastReportedTopResumedState = onTop;
            r.activity.performTopResumedActivityChanged(onTop, reason);
        }
    }

    public void handlePauseActivity(IBinder token, boolean finished, boolean userLeaving, int configChanges, PendingTransactionActions pendingActions, String reason) {
        ActivityClientRecord r = this.mActivities.get(token);
        if (r != null) {
            if (userLeaving) {
                performUserLeavingActivity(r);
            }
            r.activity.mConfigChangeFlags |= configChanges;
            performPauseActivity(r, finished, reason, pendingActions);
            if (r.isPreHoneycomb()) {
                QueuedWork.waitToFinish();
            }
            this.mSomeActivitiesChanged = true;
        }
    }

    /* access modifiers changed from: package-private */
    public final void performUserLeavingActivity(ActivityClientRecord r) {
        this.mInstrumentation.callActivityOnUserLeaving(r.activity);
    }

    /* access modifiers changed from: package-private */
    public final Bundle performPauseActivity(IBinder token, boolean finished, String reason, PendingTransactionActions pendingActions) {
        ActivityClientRecord r = this.mActivities.get(token);
        if (r != null) {
            return performPauseActivity(r, finished, reason, pendingActions);
        }
        return null;
    }

    private Bundle performPauseActivity(ActivityClientRecord r, boolean finished, String reason, PendingTransactionActions pendingActions) {
        ArrayList<OnActivityPausedListener> listeners;
        if (r.paused) {
            if (r.activity.mFinished) {
                return null;
            }
            RuntimeException e = new RuntimeException("Performing pause of activity that is not resumed: " + r.intent.getComponent().toShortString());
            Slog.e(TAG, e.getMessage(), e);
        }
        boolean shouldSaveState = true;
        if (finished) {
            r.activity.mFinished = true;
        }
        if (r.activity.mFinished || !r.isPreHoneycomb()) {
            shouldSaveState = false;
        }
        if (shouldSaveState) {
            callActivityOnSaveInstanceState(r);
        }
        performPauseActivityIfNeeded(r, reason);
        synchronized (this.mOnPauseListeners) {
            listeners = this.mOnPauseListeners.remove(r.activity);
        }
        int size = listeners != null ? listeners.size() : 0;
        for (int i = 0; i < size; i++) {
            listeners.get(i).onPaused(r.activity);
        }
        Bundle oldState = pendingActions != null ? pendingActions.getOldState() : null;
        if (oldState != null && r.isPreHoneycomb()) {
            r.state = oldState;
        }
        if (shouldSaveState) {
            return r.state;
        }
        return null;
    }

    private void performPauseActivityIfNeeded(ActivityClientRecord r, String reason) {
        if (!r.paused) {
            reportTopResumedActivityChanged(r, false, "pausing");
            try {
                r.activity.mCalled = false;
                this.mInstrumentation.callActivityOnPause(r.activity);
                if (r.activity.mCalled) {
                    r.setState(4);
                    return;
                }
                throw new SuperNotCalledException("Activity " + safeToComponentShortString(r.intent) + " did not call through to super.onPause()");
            } catch (SuperNotCalledException e) {
                throw e;
            } catch (Exception e2) {
                if (!this.mInstrumentation.onException(r.activity, e2)) {
                    throw new RuntimeException("Unable to pause activity " + safeToComponentShortString(r.intent) + PluralRules.KEYWORD_RULE_SEPARATOR + e2.toString(), e2);
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    @UnsupportedAppUsage
    public final void performStopActivity(IBinder token, boolean saveState, String reason) {
        performStopActivityInner(this.mActivities.get(token), (PendingTransactionActions.StopInfo) null, false, saveState, false, reason);
    }

    private static final class ProviderRefCount {
        public final ProviderClientRecord client;
        public final ContentProviderHolder holder;
        public boolean removePending;
        public int stableCount;
        public int unstableCount;

        ProviderRefCount(ContentProviderHolder inHolder, ProviderClientRecord inClient, int sCount, int uCount) {
            this.holder = inHolder;
            this.client = inClient;
            this.stableCount = sCount;
            this.unstableCount = uCount;
        }
    }

    private void performStopActivityInner(ActivityClientRecord r, PendingTransactionActions.StopInfo info, boolean keepShown, boolean saveState, boolean finalStateRequest, String reason) {
        if (r != null) {
            if (!keepShown && r.stopped) {
                if (!r.activity.mFinished) {
                    if (!finalStateRequest) {
                        RuntimeException e = new RuntimeException("Performing stop of activity that is already stopped: " + r.intent.getComponent().toShortString());
                        Slog.e(TAG, e.getMessage(), e);
                        Slog.e(TAG, r.getStateString());
                    }
                } else {
                    return;
                }
            }
            performPauseActivityIfNeeded(r, reason);
            if (info != null) {
                try {
                    info.setDescription(r.activity.onCreateDescription());
                } catch (Exception e2) {
                    if (!this.mInstrumentation.onException(r.activity, e2)) {
                        throw new RuntimeException("Unable to save state of activity " + r.intent.getComponent().toShortString() + PluralRules.KEYWORD_RULE_SEPARATOR + e2.toString(), e2);
                    }
                }
            }
            if (!keepShown) {
                callActivityOnStop(r, saveState, reason);
            }
        }
    }

    private void callActivityOnStop(ActivityClientRecord r, boolean saveState, String reason) {
        boolean shouldSaveState = saveState && !r.activity.mFinished && r.state == null && !r.isPreHoneycomb();
        boolean isPreP = r.isPreP();
        if (shouldSaveState && isPreP) {
            callActivityOnSaveInstanceState(r);
        }
        try {
            r.activity.performStop(r.mPreserveWindow, reason);
        } catch (SuperNotCalledException e) {
            throw e;
        } catch (Exception e2) {
            if (!this.mInstrumentation.onException(r.activity, e2)) {
                throw new RuntimeException("Unable to stop activity " + r.intent.getComponent().toShortString() + PluralRules.KEYWORD_RULE_SEPARATOR + e2.toString(), e2);
            }
        }
        r.setState(5);
        if (shouldSaveState && !isPreP) {
            callActivityOnSaveInstanceState(r);
        }
    }

    private void updateVisibility(ActivityClientRecord r, boolean show) {
        View v = r.activity.mDecor;
        if (v == null) {
            return;
        }
        if (show) {
            if (!r.activity.mVisibleFromServer) {
                r.activity.mVisibleFromServer = true;
                this.mNumVisibleActivities++;
                if (r.activity.mVisibleFromClient) {
                    r.activity.makeVisible();
                }
            }
            if (r.newConfig != null) {
                performConfigurationChangedForActivity(r, r.newConfig);
                r.newConfig = null;
            }
        } else if (r.activity.mVisibleFromServer) {
            r.activity.mVisibleFromServer = false;
            this.mNumVisibleActivities--;
            v.setVisibility(4);
        }
    }

    public void handleStopActivity(IBinder token, boolean show, int configChanges, PendingTransactionActions pendingActions, boolean finalStateRequest, String reason) {
        ActivityClientRecord r = this.mActivities.get(token);
        r.activity.mConfigChangeFlags |= configChanges;
        PendingTransactionActions.StopInfo stopInfo = new PendingTransactionActions.StopInfo();
        performStopActivityInner(r, stopInfo, show, true, finalStateRequest, reason);
        updateVisibility(r, show);
        if (!r.isPreHoneycomb()) {
            QueuedWork.waitToFinish();
        }
        stopInfo.setActivity(r);
        stopInfo.setState(r.state);
        stopInfo.setPersistentState(r.persistentState);
        pendingActions.setStopInfo(stopInfo);
        this.mSomeActivitiesChanged = true;
    }

    public void reportStop(PendingTransactionActions pendingActions) {
        this.mH.post(pendingActions.getStopInfo());
    }

    public void performRestartActivity(IBinder token, boolean start) {
        ActivityClientRecord r = this.mActivities.get(token);
        if (r.stopped) {
            r.activity.performRestart(start, "performRestartActivity");
            if (start) {
                r.setState(2);
            }
        }
    }

    public void handleWindowVisibility(IBinder token, boolean show) {
        ActivityClientRecord r = this.mActivities.get(token);
        if (r == null) {
            Log.w(TAG, "handleWindowVisibility: no activity for token " + token);
            return;
        }
        if (!show && !r.stopped) {
            performStopActivityInner(r, (PendingTransactionActions.StopInfo) null, show, false, false, "handleWindowVisibility");
        } else if (show && r.getLifecycleState() == 5) {
            unscheduleGcIdler();
            r.activity.performRestart(true, "handleWindowVisibility");
            r.setState(2);
        }
        if (r.activity.mDecor != null) {
            updateVisibility(r, show);
        }
        this.mSomeActivitiesChanged = true;
    }

    /* access modifiers changed from: private */
    public void handleSleeping(IBinder token, boolean sleeping) {
        ActivityClientRecord r = this.mActivities.get(token);
        if (r == null) {
            Log.w(TAG, "handleSleeping: no activity for token " + token);
        } else if (sleeping) {
            if (!r.stopped && !r.isPreHoneycomb()) {
                callActivityOnStop(r, true, "sleeping");
            }
            if (!r.isPreHoneycomb()) {
                QueuedWork.waitToFinish();
            }
            try {
                ActivityTaskManager.getService().activitySlept(r.token);
            } catch (RemoteException ex) {
                throw ex.rethrowFromSystemServer();
            }
        } else if (r.stopped && r.activity.mVisibleFromServer) {
            r.activity.performRestart(true, "handleSleeping");
            r.setState(2);
        }
    }

    /* access modifiers changed from: private */
    public void handleSetCoreSettings(Bundle coreSettings) {
        synchronized (this.mResourcesManager) {
            this.mCoreSettings = coreSettings;
        }
        onCoreSettingsChange();
    }

    private void onCoreSettingsChange() {
        if (updateDebugViewAttributeState()) {
            relaunchAllActivities(false);
        }
    }

    private boolean updateDebugViewAttributeState() {
        boolean previousState = View.sDebugViewAttributes;
        View.sDebugViewAttributesApplicationPackage = this.mCoreSettings.getString(Settings.Global.DEBUG_VIEW_ATTRIBUTES_APPLICATION_PACKAGE, "");
        View.sDebugViewAttributes = this.mCoreSettings.getInt(Settings.Global.DEBUG_VIEW_ATTRIBUTES, 0) != 0 || View.sDebugViewAttributesApplicationPackage.equals((this.mBoundApplication == null || this.mBoundApplication.appInfo == null) ? "" : this.mBoundApplication.appInfo.packageName);
        if (previousState != View.sDebugViewAttributes) {
            return true;
        }
        return false;
    }

    private void relaunchAllActivities(boolean preserveWindows) {
        for (Map.Entry<IBinder, ActivityClientRecord> entry : this.mActivities.entrySet()) {
            ActivityClientRecord r = entry.getValue();
            if (!r.activity.mFinished) {
                if (preserveWindows && r.window != null) {
                    r.mPreserveWindow = true;
                }
                scheduleRelaunchActivity(entry.getKey());
            }
        }
    }

    /* access modifiers changed from: private */
    public void handleUpdatePackageCompatibilityInfo(UpdateCompatibilityData data) {
        LoadedApk apk = peekPackageInfo(data.pkg, false);
        if (apk != null) {
            apk.setCompatibilityInfo(data.info);
        }
        LoadedApk apk2 = peekPackageInfo(data.pkg, true);
        if (apk2 != null) {
            apk2.setCompatibilityInfo(data.info);
        }
        handleConfigurationChanged(this.mConfiguration, data.info);
        WindowManagerGlobal.getInstance().reportNewConfiguration(this.mConfiguration);
    }

    private void deliverResults(ActivityClientRecord r, List<ResultInfo> results, String reason) {
        int N = results.size();
        for (int i = 0; i < N; i++) {
            ResultInfo ri = results.get(i);
            try {
                if (ri.mData != null) {
                    ri.mData.setExtrasClassLoader(r.activity.getClassLoader());
                    ri.mData.prepareToEnterProcess();
                }
                r.activity.dispatchActivityResult(ri.mResultWho, ri.mRequestCode, ri.mResultCode, ri.mData, reason);
            } catch (Exception e) {
                if (!this.mInstrumentation.onException(r.activity, e)) {
                    throw new RuntimeException("Failure delivering result " + ri + " to activity " + r.intent.getComponent().toShortString() + PluralRules.KEYWORD_RULE_SEPARATOR + e.toString(), e);
                }
            }
        }
    }

    public void handleSendResult(IBinder token, List<ResultInfo> results, String reason) {
        ActivityClientRecord r = this.mActivities.get(token);
        if (r != null) {
            boolean resumed = !r.paused;
            if (!r.activity.mFinished && r.activity.mDecor != null && r.hideForNow && resumed) {
                updateVisibility(r, true);
            }
            if (resumed) {
                try {
                    r.activity.mCalled = false;
                    this.mInstrumentation.callActivityOnPause(r.activity);
                    if (!r.activity.mCalled) {
                        throw new SuperNotCalledException("Activity " + r.intent.getComponent().toShortString() + " did not call through to super.onPause()");
                    }
                } catch (SuperNotCalledException e) {
                    throw e;
                } catch (Exception e2) {
                    if (!this.mInstrumentation.onException(r.activity, e2)) {
                        throw new RuntimeException("Unable to pause activity " + r.intent.getComponent().toShortString() + PluralRules.KEYWORD_RULE_SEPARATOR + e2.toString(), e2);
                    }
                }
            }
            checkAndBlockForNetworkAccess();
            deliverResults(r, results, reason);
            if (resumed) {
                r.activity.performResume(false, reason);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public ActivityClientRecord performDestroyActivity(IBinder token, boolean finishing, int configChanges, boolean getNonConfigInstance, String reason) {
        ActivityClientRecord r = this.mActivities.get(token);
        Class<?> cls = null;
        if (r != null) {
            cls = r.activity.getClass();
            r.activity.mConfigChangeFlags |= configChanges;
            if (finishing) {
                r.activity.mFinished = true;
            }
            performPauseActivityIfNeeded(r, "destroy");
            if (!r.stopped) {
                callActivityOnStop(r, false, "destroy");
            }
            if (getNonConfigInstance) {
                try {
                    r.lastNonConfigurationInstances = r.activity.retainNonConfigurationInstances();
                } catch (Exception e) {
                    if (!this.mInstrumentation.onException(r.activity, e)) {
                        throw new RuntimeException("Unable to retain activity " + r.intent.getComponent().toShortString() + PluralRules.KEYWORD_RULE_SEPARATOR + e.toString(), e);
                    }
                }
            }
            try {
                r.activity.mCalled = false;
                this.mInstrumentation.callActivityOnDestroy(r.activity);
                if (r.activity.mCalled) {
                    if (r.window != null) {
                        r.window.closeAllPanels();
                    }
                    r.setState(6);
                } else {
                    throw new SuperNotCalledException("Activity " + safeToComponentShortString(r.intent) + " did not call through to super.onDestroy()");
                }
            } catch (SuperNotCalledException e2) {
                throw e2;
            } catch (Exception e3) {
                if (!this.mInstrumentation.onException(r.activity, e3)) {
                    throw new RuntimeException("Unable to destroy activity " + safeToComponentShortString(r.intent) + PluralRules.KEYWORD_RULE_SEPARATOR + e3.toString(), e3);
                }
            }
        }
        schedulePurgeIdler();
        synchronized (this.mResourcesManager) {
            this.mActivities.remove(token);
        }
        StrictMode.decrementExpectedActivityCount(cls);
        return r;
    }

    private static String safeToComponentShortString(Intent intent) {
        ComponentName component = intent.getComponent();
        return component == null ? "[Unknown]" : component.toShortString();
    }

    public Map<IBinder, ClientTransactionItem> getActivitiesToBeDestroyed() {
        return this.mActivitiesToBeDestroyed;
    }

    public void handleDestroyActivity(IBinder token, boolean finishing, int configChanges, boolean getNonConfigInstance, String reason) {
        ActivityClientRecord r = performDestroyActivity(token, finishing, configChanges, getNonConfigInstance, reason);
        if (r != null) {
            cleanUpPendingRemoveWindows(r, finishing);
            WindowManager wm = r.activity.getWindowManager();
            View v = r.activity.mDecor;
            if (v != null) {
                if (r.activity.mVisibleFromServer) {
                    this.mNumVisibleActivities--;
                }
                IBinder wtoken = v.getWindowToken();
                if (r.activity.mWindowAdded) {
                    if (r.mPreserveWindow) {
                        r.mPendingRemoveWindow = r.window;
                        r.mPendingRemoveWindowManager = wm;
                        r.window.clearContentView();
                    } else {
                        wm.removeViewImmediate(v);
                    }
                }
                if (wtoken != null && r.mPendingRemoveWindow == null) {
                    WindowManagerGlobal.getInstance().closeAll(wtoken, r.activity.getClass().getName(), "Activity");
                } else if (r.mPendingRemoveWindow != null) {
                    WindowManagerGlobal.getInstance().closeAllExceptView(token, v, r.activity.getClass().getName(), "Activity");
                }
                r.activity.mDecor = null;
            }
            if (r.mPendingRemoveWindow == null) {
                WindowManagerGlobal.getInstance().closeAll(token, r.activity.getClass().getName(), "Activity");
            }
            Context c = r.activity.getBaseContext();
            if (c instanceof ContextImpl) {
                ((ContextImpl) c).scheduleFinalCleanup(r.activity.getClass().getName(), "Activity");
            }
        }
        if (finishing) {
            try {
                ActivityTaskManager.getService().activityDestroyed(token);
            } catch (RemoteException ex) {
                throw ex.rethrowFromSystemServer();
            }
        }
        this.mSomeActivitiesChanged = true;
    }

    public ActivityClientRecord prepareRelaunchActivity(IBinder token, List<ResultInfo> pendingResults, List<ReferrerIntent> pendingNewIntents, int configChanges, MergedConfiguration config, boolean preserveWindow) {
        ActivityClientRecord target = null;
        boolean scheduleRelaunch = false;
        synchronized (this.mResourcesManager) {
            int i = 0;
            while (true) {
                if (i >= this.mRelaunchingActivities.size()) {
                    break;
                }
                ActivityClientRecord r = this.mRelaunchingActivities.get(i);
                if (r.token == token) {
                    target = r;
                    if (pendingResults != null) {
                        if (r.pendingResults != null) {
                            r.pendingResults.addAll(pendingResults);
                        } else {
                            r.pendingResults = pendingResults;
                        }
                    }
                    if (pendingNewIntents != null) {
                        if (r.pendingIntents != null) {
                            r.pendingIntents.addAll(pendingNewIntents);
                        } else {
                            r.pendingIntents = pendingNewIntents;
                        }
                    }
                } else {
                    i++;
                }
            }
            if (target == null) {
                target = new ActivityClientRecord();
                target.token = token;
                target.pendingResults = pendingResults;
                target.pendingIntents = pendingNewIntents;
                target.mPreserveWindow = preserveWindow;
                this.mRelaunchingActivities.add(target);
                scheduleRelaunch = true;
            }
            target.createdConfig = config.getGlobalConfiguration();
            target.overrideConfig = config.getOverrideConfiguration();
            target.pendingConfigChanges |= configChanges;
        }
        if (scheduleRelaunch) {
            return target;
        }
        return null;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:36:0x005c, code lost:
        if (r12.createdConfig == null) goto L_0x0082;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x0060, code lost:
        if (r10.mConfiguration == null) goto L_0x0076;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x006a, code lost:
        if (r12.createdConfig.isOtherSeqNewer(r10.mConfiguration) == false) goto L_0x0082;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x0074, code lost:
        if (r10.mConfiguration.diff(r12.createdConfig) == 0) goto L_0x0082;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x0076, code lost:
        if (r1 == null) goto L_0x0080;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x007e, code lost:
        if (r12.createdConfig.isOtherSeqNewer(r1) == false) goto L_0x0082;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x0080, code lost:
        r1 = r12.createdConfig;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x0082, code lost:
        r14 = r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x0083, code lost:
        if (r14 == null) goto L_0x008f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x0085, code lost:
        r10.mCurDefaultDisplayDpi = r14.densityDpi;
        updateDefaultDensity();
        handleConfigurationChanged(r14, (android.content.res.CompatibilityInfo) null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x008f, code lost:
        r15 = r10.mActivities.get(r12.token);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x009a, code lost:
        if (r15 != null) goto L_0x009d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x009c, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x009d, code lost:
        r15.activity.mConfigChangeFlags |= r13;
        r15.mPreserveWindow = r12.mPreserveWindow;
        r15.activity.mChangingConfigurations = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x00ae, code lost:
        if (r15.mPreserveWindow == false) goto L_0x00b9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x00b0, code lost:
        android.view.WindowManagerGlobal.getWindowSession().prepareToReplaceWindows(r15.token, true);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x00b9, code lost:
        handleRelaunchActivityInner(r15, r13, r12.pendingResults, r12.pendingIntents, r18, r12.startsNotResumed, r12.overrideConfig, "handleRelaunchActivity");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x00cd, code lost:
        if (r11 == null) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x00cf, code lost:
        r11.setReportRelaunchToWindowManager(true);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x00d3, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x00d8, code lost:
        throw r0.rethrowFromSystemServer();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:78:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:79:?, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void handleRelaunchActivity(android.app.ActivityThread.ActivityClientRecord r17, android.app.servertransaction.PendingTransactionActions r18) {
        /*
            r16 = this;
            r10 = r16
            r11 = r18
            r16.unscheduleGcIdler()
            r0 = 1
            r10.mSomeActivitiesChanged = r0
            r1 = 0
            r2 = 0
            android.app.ResourcesManager r3 = r10.mResourcesManager
            monitor-enter(r3)
            java.util.ArrayList<android.app.ActivityThread$ActivityClientRecord> r4 = r10.mRelaunchingActivities     // Catch:{ all -> 0x00db }
            int r4 = r4.size()     // Catch:{ all -> 0x00db }
            r5 = r17
            android.os.IBinder r6 = r5.token     // Catch:{ all -> 0x00d9 }
            r5 = 0
            r7 = 0
            r13 = r2
            r12 = r5
        L_0x001d:
            r2 = r7
            if (r2 >= r4) goto L_0x004b
            java.util.ArrayList<android.app.ActivityThread$ActivityClientRecord> r5 = r10.mRelaunchingActivities     // Catch:{ all -> 0x0047 }
            java.lang.Object r5 = r5.get(r2)     // Catch:{ all -> 0x0047 }
            android.app.ActivityThread$ActivityClientRecord r5 = (android.app.ActivityThread.ActivityClientRecord) r5     // Catch:{ all -> 0x0047 }
            android.os.IBinder r7 = r5.token     // Catch:{ all -> 0x0047 }
            if (r7 != r6) goto L_0x0044
            r7 = r5
            int r8 = r7.pendingConfigChanges     // Catch:{ all -> 0x0041 }
            r8 = r8 | r13
            java.util.ArrayList<android.app.ActivityThread$ActivityClientRecord> r9 = r10.mRelaunchingActivities     // Catch:{ all -> 0x003c }
            r9.remove(r2)     // Catch:{ all -> 0x003c }
            int r2 = r2 + -1
            int r4 = r4 + -1
            r12 = r7
            r13 = r8
            goto L_0x0044
        L_0x003c:
            r0 = move-exception
            r12 = r7
            r2 = r8
            goto L_0x00df
        L_0x0041:
            r0 = move-exception
            r12 = r7
            goto L_0x0048
        L_0x0044:
            int r7 = r2 + 1
            goto L_0x001d
        L_0x0047:
            r0 = move-exception
        L_0x0048:
            r2 = r13
            goto L_0x00df
        L_0x004b:
            if (r12 != 0) goto L_0x004f
            monitor-exit(r3)     // Catch:{ all -> 0x0047 }
            return
        L_0x004f:
            android.content.res.Configuration r2 = r10.mPendingConfiguration     // Catch:{ all -> 0x0047 }
            r5 = 0
            if (r2 == 0) goto L_0x0059
            android.content.res.Configuration r2 = r10.mPendingConfiguration     // Catch:{ all -> 0x0047 }
            r1 = r2
            r10.mPendingConfiguration = r5     // Catch:{ all -> 0x0047 }
        L_0x0059:
            monitor-exit(r3)     // Catch:{ all -> 0x0047 }
            android.content.res.Configuration r2 = r12.createdConfig
            if (r2 == 0) goto L_0x0082
            android.content.res.Configuration r2 = r10.mConfiguration
            if (r2 == 0) goto L_0x0076
            android.content.res.Configuration r2 = r12.createdConfig
            android.content.res.Configuration r3 = r10.mConfiguration
            boolean r2 = r2.isOtherSeqNewer(r3)
            if (r2 == 0) goto L_0x0082
            android.content.res.Configuration r2 = r10.mConfiguration
            android.content.res.Configuration r3 = r12.createdConfig
            int r2 = r2.diff(r3)
            if (r2 == 0) goto L_0x0082
        L_0x0076:
            if (r1 == 0) goto L_0x0080
            android.content.res.Configuration r2 = r12.createdConfig
            boolean r2 = r2.isOtherSeqNewer(r1)
            if (r2 == 0) goto L_0x0082
        L_0x0080:
            android.content.res.Configuration r1 = r12.createdConfig
        L_0x0082:
            r14 = r1
            if (r14 == 0) goto L_0x008f
            int r1 = r14.densityDpi
            r10.mCurDefaultDisplayDpi = r1
            r16.updateDefaultDensity()
            r10.handleConfigurationChanged(r14, r5)
        L_0x008f:
            android.util.ArrayMap<android.os.IBinder, android.app.ActivityThread$ActivityClientRecord> r1 = r10.mActivities
            android.os.IBinder r2 = r12.token
            java.lang.Object r1 = r1.get(r2)
            r15 = r1
            android.app.ActivityThread$ActivityClientRecord r15 = (android.app.ActivityThread.ActivityClientRecord) r15
            if (r15 != 0) goto L_0x009d
            return
        L_0x009d:
            android.app.Activity r1 = r15.activity
            int r2 = r1.mConfigChangeFlags
            r2 = r2 | r13
            r1.mConfigChangeFlags = r2
            boolean r1 = r12.mPreserveWindow
            r15.mPreserveWindow = r1
            android.app.Activity r1 = r15.activity
            r1.mChangingConfigurations = r0
            boolean r1 = r15.mPreserveWindow     // Catch:{ RemoteException -> 0x00d3 }
            if (r1 == 0) goto L_0x00b9
            android.view.IWindowSession r1 = android.view.WindowManagerGlobal.getWindowSession()     // Catch:{ RemoteException -> 0x00d3 }
            android.os.IBinder r2 = r15.token     // Catch:{ RemoteException -> 0x00d3 }
            r1.prepareToReplaceWindows(r2, r0)     // Catch:{ RemoteException -> 0x00d3 }
        L_0x00b9:
            java.util.List<android.app.ResultInfo> r4 = r12.pendingResults
            java.util.List<com.android.internal.content.ReferrerIntent> r5 = r12.pendingIntents
            boolean r7 = r12.startsNotResumed
            android.content.res.Configuration r8 = r12.overrideConfig
            java.lang.String r9 = "handleRelaunchActivity"
            r1 = r16
            r2 = r15
            r3 = r13
            r6 = r18
            r1.handleRelaunchActivityInner(r2, r3, r4, r5, r6, r7, r8, r9)
            if (r11 == 0) goto L_0x00d2
            r11.setReportRelaunchToWindowManager(r0)
        L_0x00d2:
            return
        L_0x00d3:
            r0 = move-exception
            java.lang.RuntimeException r1 = r0.rethrowFromSystemServer()
            throw r1
        L_0x00d9:
            r0 = move-exception
            goto L_0x00de
        L_0x00db:
            r0 = move-exception
            r5 = r17
        L_0x00de:
            r12 = r5
        L_0x00df:
            monitor-exit(r3)     // Catch:{ all -> 0x00e1 }
            throw r0
        L_0x00e1:
            r0 = move-exception
            goto L_0x00df
        */
        throw new UnsupportedOperationException("Method not decompiled: android.app.ActivityThread.handleRelaunchActivity(android.app.ActivityThread$ActivityClientRecord, android.app.servertransaction.PendingTransactionActions):void");
    }

    /* access modifiers changed from: package-private */
    public void scheduleRelaunchActivity(IBinder token) {
        this.mH.removeMessages(160, token);
        sendMessage(160, token);
    }

    /* access modifiers changed from: private */
    public void handleRelaunchActivityLocally(IBinder token) {
        ActivityClientRecord r = this.mActivities.get(token);
        if (r == null) {
            Log.w(TAG, "Activity to relaunch no longer exists");
            return;
        }
        int prevState = r.getLifecycleState();
        if (prevState < 3 || prevState > 5) {
            Log.w(TAG, "Activity state must be in [ON_RESUME..ON_STOP] in order to be relaunched,current state is " + prevState);
            return;
        }
        ActivityRelaunchItem activityRelaunchItem = ActivityRelaunchItem.obtain((List<ResultInfo>) null, (List<ReferrerIntent>) null, 0, new MergedConfiguration(r.createdConfig != null ? r.createdConfig : this.mConfiguration, r.overrideConfig), r.mPreserveWindow);
        ActivityLifecycleItem lifecycleRequest = TransactionExecutorHelper.getLifecycleRequestForCurrentState(r);
        ClientTransaction transaction = ClientTransaction.obtain(this.mAppThread, r.token);
        transaction.addCallback(activityRelaunchItem);
        transaction.setLifecycleStateRequest(lifecycleRequest);
        executeTransaction(transaction);
    }

    private void handleRelaunchActivityInner(ActivityClientRecord r, int configChanges, List<ResultInfo> pendingResults, List<ReferrerIntent> pendingIntents, PendingTransactionActions pendingActions, boolean startsNotResumed, Configuration overrideConfig, String reason) {
        ActivityClientRecord activityClientRecord = r;
        List<ResultInfo> list = pendingResults;
        List<ReferrerIntent> list2 = pendingIntents;
        String str = reason;
        Intent customIntent = activityClientRecord.activity.mIntent;
        if (!activityClientRecord.paused) {
            performPauseActivity(r, false, str, (PendingTransactionActions) null);
        }
        if (!activityClientRecord.stopped) {
            callActivityOnStop(r, true, str);
        }
        handleDestroyActivity(activityClientRecord.token, false, configChanges, true, reason);
        activityClientRecord.activity = null;
        activityClientRecord.window = null;
        activityClientRecord.hideForNow = false;
        activityClientRecord.nextIdle = null;
        if (list != null) {
            if (activityClientRecord.pendingResults == null) {
                activityClientRecord.pendingResults = list;
            } else {
                activityClientRecord.pendingResults.addAll(list);
            }
        }
        if (list2 != null) {
            if (activityClientRecord.pendingIntents == null) {
                activityClientRecord.pendingIntents = list2;
            } else {
                activityClientRecord.pendingIntents.addAll(list2);
            }
        }
        activityClientRecord.startsNotResumed = startsNotResumed;
        activityClientRecord.overrideConfig = overrideConfig;
        handleLaunchActivity(r, pendingActions, customIntent);
    }

    public void reportRelaunch(IBinder token, PendingTransactionActions pendingActions) {
        try {
            ActivityTaskManager.getService().activityRelaunched(token);
            ActivityClientRecord r = this.mActivities.get(token);
            if (pendingActions.shouldReportRelaunchToWindowManager() && r != null && r.window != null) {
                r.window.reportActivityRelaunched();
            }
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private void callActivityOnSaveInstanceState(ActivityClientRecord r) {
        r.state = new Bundle();
        r.state.setAllowFds(false);
        if (r.isPersistable()) {
            r.persistentState = new PersistableBundle();
            this.mInstrumentation.callActivityOnSaveInstanceState(r.activity, r.state, r.persistentState);
            return;
        }
        this.mInstrumentation.callActivityOnSaveInstanceState(r.activity, r.state);
    }

    /* access modifiers changed from: package-private */
    public ArrayList<ComponentCallbacks2> collectComponentCallbacks(boolean allActivities, Configuration newConfig) {
        int i;
        ArrayList<ComponentCallbacks2> callbacks = new ArrayList<>();
        synchronized (this.mResourcesManager) {
            int NAPP = this.mAllApplications.size();
            for (int i2 = 0; i2 < NAPP; i2++) {
                callbacks.add(this.mAllApplications.get(i2));
            }
            int NACT = this.mActivities.size();
            for (int i3 = 0; i3 < NACT; i3++) {
                ActivityClientRecord ar = this.mActivities.valueAt(i3);
                Activity a = ar.activity;
                if (a != null) {
                    Configuration thisConfig = applyConfigCompatMainThread(this.mCurDefaultDisplayDpi, newConfig, ar.packageInfo.getCompatibilityInfo());
                    if (!ar.activity.mFinished && (allActivities || !ar.paused)) {
                        callbacks.add(a);
                    } else if (thisConfig != null) {
                        ar.newConfig = thisConfig;
                    }
                }
            }
            int NSVC = this.mServices.size();
            for (int i4 = 0; i4 < NSVC; i4++) {
                callbacks.add(this.mServices.valueAt(i4));
            }
        }
        synchronized (this.mProviderMap) {
            int NPRV = this.mLocalProviders.size();
            for (i = 0; i < NPRV; i++) {
                callbacks.add(this.mLocalProviders.valueAt(i).mLocalProvider);
            }
        }
        return callbacks;
    }

    private void performConfigurationChangedForActivity(ActivityClientRecord r, Configuration newBaseConfig) {
        performConfigurationChangedForActivity(r, newBaseConfig, r.activity.getDisplayId(), false);
    }

    private Configuration performConfigurationChangedForActivity(ActivityClientRecord r, Configuration newBaseConfig, int displayId, boolean movedToDifferentDisplay) {
        r.tmpConfig.setTo(newBaseConfig);
        if (r.overrideConfig != null) {
            r.tmpConfig.updateFrom(r.overrideConfig);
        }
        Configuration reportedConfig = performActivityConfigurationChanged(r.activity, r.tmpConfig, r.overrideConfig, displayId, movedToDifferentDisplay);
        freeTextLayoutCachesIfNeeded(r.activity.mCurrentConfig.diff(r.tmpConfig));
        return reportedConfig;
    }

    private static Configuration createNewConfigAndUpdateIfNotNull(Configuration base, Configuration override) {
        if (override == null) {
            return base;
        }
        Configuration newConfig = new Configuration(base);
        newConfig.updateFrom(override);
        return newConfig;
    }

    private void performConfigurationChanged(ComponentCallbacks2 cb, Configuration newConfig) {
        Configuration contextThemeWrapperOverrideConfig = null;
        if (cb instanceof ContextThemeWrapper) {
            contextThemeWrapperOverrideConfig = ((ContextThemeWrapper) cb).getOverrideConfiguration();
        }
        cb.onConfigurationChanged(createNewConfigAndUpdateIfNotNull(newConfig, contextThemeWrapperOverrideConfig));
    }

    private Configuration performActivityConfigurationChanged(Activity activity, Configuration newConfig, Configuration amOverrideConfig, int displayId, boolean movedToDifferentDisplay) {
        if (activity != null) {
            IBinder activityToken = activity.getActivityToken();
            if (activityToken != null) {
                boolean shouldChangeConfig = false;
                if (activity.mCurrentConfig == null) {
                    shouldChangeConfig = true;
                } else {
                    int diff = activity.mCurrentConfig.diffPublicOnly(newConfig);
                    if ((diff != 0 || !this.mResourcesManager.isSameResourcesOverrideConfig(activityToken, amOverrideConfig)) && (!this.mUpdatingSystemConfig || ((~activity.mActivityInfo.getRealConfigChanged()) & diff) == 0)) {
                        shouldChangeConfig = true;
                    }
                }
                if (!shouldChangeConfig && !movedToDifferentDisplay) {
                    return null;
                }
                Configuration contextThemeWrapperOverrideConfig = activity.getOverrideConfiguration();
                this.mResourcesManager.updateResourcesForActivity(activityToken, createNewConfigAndUpdateIfNotNull(amOverrideConfig, contextThemeWrapperOverrideConfig), displayId, movedToDifferentDisplay);
                activity.mConfigChangeFlags = 0;
                activity.mCurrentConfig = new Configuration(newConfig);
                Configuration configToReport = createNewConfigAndUpdateIfNotNull(newConfig, contextThemeWrapperOverrideConfig);
                if (movedToDifferentDisplay) {
                    activity.dispatchMovedToDisplay(displayId, configToReport);
                }
                if (shouldChangeConfig) {
                    activity.mCalled = false;
                    activity.onConfigurationChanged(configToReport);
                    if (!activity.mCalled) {
                        throw new SuperNotCalledException("Activity " + activity.getLocalClassName() + " did not call through to super.onConfigurationChanged()");
                    }
                }
                return configToReport;
            }
            throw new IllegalArgumentException("Activity token not set. Is the activity attached?");
        }
        throw new IllegalArgumentException("No activity provided.");
    }

    public final void applyConfigurationToResources(Configuration config) {
        synchronized (this.mResourcesManager) {
            this.mResourcesManager.applyConfigurationToResourcesLocked(config, (CompatibilityInfo) null);
        }
    }

    /* access modifiers changed from: package-private */
    public final Configuration applyCompatConfiguration(int displayDensity) {
        Configuration config = this.mConfiguration;
        if (this.mCompatConfiguration == null) {
            this.mCompatConfiguration = new Configuration();
        }
        this.mCompatConfiguration.setTo(this.mConfiguration);
        if (this.mResourcesManager.applyCompatConfigurationLocked(displayDensity, this.mCompatConfiguration)) {
            return this.mCompatConfiguration;
        }
        return config;
    }

    /* JADX INFO: finally extract failed */
    public void handleConfigurationChanged(Configuration config) {
        Trace.traceBegin(64, "configChanged");
        this.mCurDefaultDisplayDpi = config.densityDpi;
        this.mUpdatingSystemConfig = true;
        try {
            handleConfigurationChanged(config, (CompatibilityInfo) null);
            this.mUpdatingSystemConfig = false;
            Trace.traceEnd(64);
        } catch (Throwable th) {
            this.mUpdatingSystemConfig = false;
            throw th;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0091, code lost:
        r2 = collectComponentCallbacks(false, r12);
        freeTextLayoutCachesIfNeeded(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0098, code lost:
        if (r2 == null) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x009a, code lost:
        r6 = r2.size();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x009f, code lost:
        if (r4 >= r6) goto L_0x00c6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x00a1, code lost:
        r7 = r2.get(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x00a9, code lost:
        if ((r7 instanceof android.app.Activity) == false) goto L_0x00be;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x00ab, code lost:
        performConfigurationChangedForActivity(r11.mActivities.get(((android.app.Activity) r7).getActivityToken()), r12);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x00be, code lost:
        if (r3 != false) goto L_0x00c3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00c0, code lost:
        performConfigurationChanged(r7, r12);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00c3, code lost:
        r4 = r4 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:?, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void handleConfigurationChanged(android.content.res.Configuration r12, android.content.res.CompatibilityInfo r13) {
        /*
            r11 = this;
            android.app.ContextImpl r0 = r11.getSystemContext()
            android.content.res.Resources$Theme r0 = r0.getTheme()
            android.app.ContextImpl r1 = r11.getSystemUiContext()
            android.content.res.Resources$Theme r1 = r1.getTheme()
            android.app.ResourcesManager r2 = r11.mResourcesManager
            monitor-enter(r2)
            android.content.res.Configuration r3 = r11.mPendingConfiguration     // Catch:{ all -> 0x00c7 }
            if (r3 == 0) goto L_0x002c
            android.content.res.Configuration r3 = r11.mPendingConfiguration     // Catch:{ all -> 0x00c7 }
            boolean r3 = r3.isOtherSeqNewer(r12)     // Catch:{ all -> 0x00c7 }
            if (r3 != 0) goto L_0x0029
            android.content.res.Configuration r3 = r11.mPendingConfiguration     // Catch:{ all -> 0x00c7 }
            r12 = r3
            int r3 = r12.densityDpi     // Catch:{ all -> 0x00c7 }
            r11.mCurDefaultDisplayDpi = r3     // Catch:{ all -> 0x00c7 }
            r11.updateDefaultDensity()     // Catch:{ all -> 0x00c7 }
        L_0x0029:
            r3 = 0
            r11.mPendingConfiguration = r3     // Catch:{ all -> 0x00c7 }
        L_0x002c:
            if (r12 != 0) goto L_0x0030
            monitor-exit(r2)     // Catch:{ all -> 0x00c7 }
            return
        L_0x0030:
            android.content.res.Configuration r3 = r11.mConfiguration     // Catch:{ all -> 0x00c7 }
            r4 = 0
            if (r3 == 0) goto L_0x003f
            android.content.res.Configuration r3 = r11.mConfiguration     // Catch:{ all -> 0x00c7 }
            int r3 = r3.diffPublicOnly(r12)     // Catch:{ all -> 0x00c7 }
            if (r3 != 0) goto L_0x003f
            r3 = 1
            goto L_0x0040
        L_0x003f:
            r3 = r4
        L_0x0040:
            android.app.ResourcesManager r5 = r11.mResourcesManager     // Catch:{ all -> 0x00c7 }
            r5.applyConfigurationToResourcesLocked(r12, r13)     // Catch:{ all -> 0x00c7 }
            android.app.Application r5 = r11.mInitialApplication     // Catch:{ all -> 0x00c7 }
            android.content.Context r5 = r5.getApplicationContext()     // Catch:{ all -> 0x00c7 }
            android.app.ResourcesManager r6 = r11.mResourcesManager     // Catch:{ all -> 0x00c7 }
            android.content.res.Configuration r6 = r6.getConfiguration()     // Catch:{ all -> 0x00c7 }
            android.os.LocaleList r6 = r6.getLocales()     // Catch:{ all -> 0x00c7 }
            r11.updateLocaleListFromAppContext(r5, r6)     // Catch:{ all -> 0x00c7 }
            android.content.res.Configuration r5 = r11.mConfiguration     // Catch:{ all -> 0x00c7 }
            if (r5 != 0) goto L_0x0063
            android.content.res.Configuration r5 = new android.content.res.Configuration     // Catch:{ all -> 0x00c7 }
            r5.<init>()     // Catch:{ all -> 0x00c7 }
            r11.mConfiguration = r5     // Catch:{ all -> 0x00c7 }
        L_0x0063:
            android.content.res.Configuration r5 = r11.mConfiguration     // Catch:{ all -> 0x00c7 }
            boolean r5 = r5.isOtherSeqNewer(r12)     // Catch:{ all -> 0x00c7 }
            if (r5 != 0) goto L_0x006f
            if (r13 != 0) goto L_0x006f
            monitor-exit(r2)     // Catch:{ all -> 0x00c7 }
            return
        L_0x006f:
            android.content.res.Configuration r5 = r11.mConfiguration     // Catch:{ all -> 0x00c7 }
            int r5 = r5.updateFrom(r12)     // Catch:{ all -> 0x00c7 }
            int r6 = r11.mCurDefaultDisplayDpi     // Catch:{ all -> 0x00c7 }
            android.content.res.Configuration r6 = r11.applyCompatConfiguration(r6)     // Catch:{ all -> 0x00c7 }
            r12 = r6
            int r6 = r0.getChangingConfigurations()     // Catch:{ all -> 0x00c7 }
            r6 = r6 & r5
            if (r6 == 0) goto L_0x0086
            r0.rebase()     // Catch:{ all -> 0x00c7 }
        L_0x0086:
            int r6 = r1.getChangingConfigurations()     // Catch:{ all -> 0x00c7 }
            r6 = r6 & r5
            if (r6 == 0) goto L_0x0090
            r1.rebase()     // Catch:{ all -> 0x00c7 }
        L_0x0090:
            monitor-exit(r2)     // Catch:{ all -> 0x00c7 }
            java.util.ArrayList r2 = r11.collectComponentCallbacks(r4, r12)
            freeTextLayoutCachesIfNeeded(r5)
            if (r2 == 0) goto L_0x00c6
            int r6 = r2.size()
        L_0x009f:
            if (r4 >= r6) goto L_0x00c6
            java.lang.Object r7 = r2.get(r4)
            android.content.ComponentCallbacks2 r7 = (android.content.ComponentCallbacks2) r7
            boolean r8 = r7 instanceof android.app.Activity
            if (r8 == 0) goto L_0x00be
            r8 = r7
            android.app.Activity r8 = (android.app.Activity) r8
            android.util.ArrayMap<android.os.IBinder, android.app.ActivityThread$ActivityClientRecord> r9 = r11.mActivities
            android.os.IBinder r10 = r8.getActivityToken()
            java.lang.Object r9 = r9.get(r10)
            android.app.ActivityThread$ActivityClientRecord r9 = (android.app.ActivityThread.ActivityClientRecord) r9
            r11.performConfigurationChangedForActivity(r9, r12)
            goto L_0x00c3
        L_0x00be:
            if (r3 != 0) goto L_0x00c3
            r11.performConfigurationChanged(r7, r12)
        L_0x00c3:
            int r4 = r4 + 1
            goto L_0x009f
        L_0x00c6:
            return
        L_0x00c7:
            r3 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x00c7 }
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: android.app.ActivityThread.handleConfigurationChanged(android.content.res.Configuration, android.content.res.CompatibilityInfo):void");
    }

    public void handleSystemApplicationInfoChanged(ApplicationInfo ai) {
        Preconditions.checkState(this.mSystemThread, "Must only be called in the system process");
        handleApplicationInfoChanged(ai);
    }

    @VisibleForTesting(visibility = VisibleForTesting.Visibility.PACKAGE)
    public void handleApplicationInfoChanged(ApplicationInfo ai) {
        LoadedApk apk;
        LoadedApk resApk;
        synchronized (this.mResourcesManager) {
            WeakReference<LoadedApk> ref = this.mPackages.get(ai.packageName);
            apk = ref != null ? (LoadedApk) ref.get() : null;
            WeakReference<LoadedApk> ref2 = this.mResourcePackages.get(ai.packageName);
            resApk = ref2 != null ? (LoadedApk) ref2.get() : null;
        }
        String[] oldResDirs = new String[2];
        int i = 0;
        if (apk != null) {
            oldResDirs[0] = apk.getResDir();
            ArrayList<String> oldPaths = new ArrayList<>();
            LoadedApk.makePaths(this, apk.getApplicationInfo(), oldPaths);
            apk.updateApplicationInfo(ai, oldPaths);
        }
        if (resApk != null) {
            oldResDirs[1] = resApk.getResDir();
            ArrayList<String> oldPaths2 = new ArrayList<>();
            LoadedApk.makePaths(this, resApk.getApplicationInfo(), oldPaths2);
            resApk.updateApplicationInfo(ai, oldPaths2);
        }
        synchronized (this.mResourcesManager) {
            this.mResourcesManager.applyNewResourceDirsLocked(ai, oldResDirs);
        }
        ApplicationPackageManager.configurationChanged();
        Configuration newConfig = new Configuration();
        if (this.mConfiguration != null) {
            i = this.mConfiguration.assetsSeq;
        }
        newConfig.assetsSeq = i + 1;
        handleConfigurationChanged(newConfig, (CompatibilityInfo) null);
        relaunchAllActivities(true);
    }

    static void freeTextLayoutCachesIfNeeded(int configDiff) {
        if (configDiff != 0) {
            if ((configDiff & 4) != 0) {
                Canvas.freeTextLayoutCaches();
            }
        }
    }

    public void updatePendingActivityConfiguration(IBinder activityToken, Configuration overrideConfig) {
        ActivityClientRecord r;
        synchronized (this.mResourcesManager) {
            r = this.mActivities.get(activityToken);
        }
        if (r != null) {
            synchronized (r) {
                Configuration unused = r.mPendingOverrideConfig = overrideConfig;
            }
        }
    }

    public void handleActivityConfigurationChanged(IBinder activityToken, Configuration overrideConfig, int displayId) {
        ViewRootImpl viewRoot;
        ActivityClientRecord r = this.mActivities.get(activityToken);
        if (r != null && r.activity != null) {
            boolean movedToDifferentDisplay = (displayId == -1 || displayId == r.activity.getDisplayId()) ? false : true;
            synchronized (r) {
                if (r.mPendingOverrideConfig != null && !r.mPendingOverrideConfig.isOtherSeqNewer(overrideConfig)) {
                    overrideConfig = r.mPendingOverrideConfig;
                }
                viewRoot = null;
                Configuration unused = r.mPendingOverrideConfig = null;
            }
            if (r.overrideConfig == null || r.overrideConfig.isOtherSeqNewer(overrideConfig) || movedToDifferentDisplay) {
                r.overrideConfig = overrideConfig;
                if (r.activity.mDecor != null) {
                    viewRoot = r.activity.mDecor.getViewRootImpl();
                }
                if (movedToDifferentDisplay) {
                    Configuration reportedConfig = performConfigurationChangedForActivity(r, this.mCompatConfiguration, displayId, true);
                    if (viewRoot != null) {
                        viewRoot.onMovedToDisplay(displayId, reportedConfig);
                    }
                } else {
                    performConfigurationChangedForActivity(r, this.mCompatConfiguration);
                }
                if (viewRoot != null) {
                    viewRoot.updateConfiguration(displayId);
                }
                this.mSomeActivitiesChanged = true;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public final void handleProfilerControl(boolean start, ProfilerInfo profilerInfo, int profileType) {
        if (start) {
            try {
                this.mProfiler.setProfiler(profilerInfo);
                this.mProfiler.startProfiling();
            } catch (RuntimeException e) {
                Slog.w(TAG, "Profiling failed on path " + profilerInfo.profileFile + " -- can the process access this path?");
            } catch (Throwable th) {
                profilerInfo.closeFd();
                throw th;
            }
            profilerInfo.closeFd();
            return;
        }
        this.mProfiler.stopProfiling();
    }

    public void stopProfiling() {
        if (this.mProfiler != null) {
            this.mProfiler.stopProfiling();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0037, code lost:
        r2 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0038, code lost:
        r3 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x003c, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x003d, code lost:
        r5 = r3;
        r3 = r2;
        r2 = r5;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static void handleDumpHeap(android.app.ActivityThread.DumpHeapData r6) {
        /*
            boolean r0 = r6.runGc
            if (r0 == 0) goto L_0x000d
            java.lang.System.gc()
            java.lang.System.runFinalization()
            java.lang.System.gc()
        L_0x000d:
            r0 = 0
            android.os.ParcelFileDescriptor r1 = r6.fd     // Catch:{ IOException -> 0x005a, RuntimeException -> 0x0051 }
            boolean r2 = r6.managed     // Catch:{ Throwable -> 0x003a, all -> 0x0037 }
            if (r2 == 0) goto L_0x001e
            java.lang.String r2 = r6.path     // Catch:{ Throwable -> 0x003a, all -> 0x0037 }
            java.io.FileDescriptor r3 = r1.getFileDescriptor()     // Catch:{ Throwable -> 0x003a, all -> 0x0037 }
            android.os.Debug.dumpHprofData(r2, r3)     // Catch:{ Throwable -> 0x003a, all -> 0x0037 }
            goto L_0x0031
        L_0x001e:
            boolean r2 = r6.mallocInfo     // Catch:{ Throwable -> 0x003a, all -> 0x0037 }
            if (r2 == 0) goto L_0x002a
            java.io.FileDescriptor r2 = r1.getFileDescriptor()     // Catch:{ Throwable -> 0x003a, all -> 0x0037 }
            android.os.Debug.dumpNativeMallocInfo(r2)     // Catch:{ Throwable -> 0x003a, all -> 0x0037 }
            goto L_0x0031
        L_0x002a:
            java.io.FileDescriptor r2 = r1.getFileDescriptor()     // Catch:{ Throwable -> 0x003a, all -> 0x0037 }
            android.os.Debug.dumpNativeHeap(r2)     // Catch:{ Throwable -> 0x003a, all -> 0x0037 }
        L_0x0031:
            if (r1 == 0) goto L_0x0084
            r1.close()     // Catch:{ IOException -> 0x005a, RuntimeException -> 0x0051 }
            goto L_0x0084
        L_0x0037:
            r2 = move-exception
            r3 = r0
            goto L_0x0040
        L_0x003a:
            r2 = move-exception
            throw r2     // Catch:{ all -> 0x003c }
        L_0x003c:
            r3 = move-exception
            r5 = r3
            r3 = r2
            r2 = r5
        L_0x0040:
            if (r1 == 0) goto L_0x0050
            if (r3 == 0) goto L_0x004d
            r1.close()     // Catch:{ Throwable -> 0x0048 }
            goto L_0x0050
        L_0x0048:
            r4 = move-exception
            r3.addSuppressed(r4)     // Catch:{ IOException -> 0x005a, RuntimeException -> 0x0051 }
            goto L_0x0050
        L_0x004d:
            r1.close()     // Catch:{ IOException -> 0x005a, RuntimeException -> 0x0051 }
        L_0x0050:
            throw r2     // Catch:{ IOException -> 0x005a, RuntimeException -> 0x0051 }
        L_0x0051:
            r1 = move-exception
            java.lang.String r2 = "ActivityThread"
            java.lang.String r3 = "Heap dumper threw a runtime exception"
            android.util.Slog.wtf(r2, r3, r1)
            goto L_0x0085
        L_0x005a:
            r1 = move-exception
            boolean r2 = r6.managed
            if (r2 == 0) goto L_0x007d
            java.lang.String r2 = "ActivityThread"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "Managed heap dump failed on path "
            r3.append(r4)
            java.lang.String r4 = r6.path
            r3.append(r4)
            java.lang.String r4 = " -- can the process access this path?"
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            android.util.Slog.w(r2, r3, r1)
            goto L_0x0084
        L_0x007d:
            java.lang.String r2 = "ActivityThread"
            java.lang.String r3 = "Failed to dump heap"
            android.util.Slog.w(r2, r3, r1)
        L_0x0084:
        L_0x0085:
            android.app.IActivityManager r1 = android.app.ActivityManager.getService()     // Catch:{ RemoteException -> 0x0099 }
            java.lang.String r2 = r6.path     // Catch:{ RemoteException -> 0x0099 }
            r1.dumpHeapFinished(r2)     // Catch:{ RemoteException -> 0x0099 }
            android.os.RemoteCallback r1 = r6.finishCallback
            if (r1 == 0) goto L_0x0098
            android.os.RemoteCallback r1 = r6.finishCallback
            r1.sendResult(r0)
        L_0x0098:
            return
        L_0x0099:
            r0 = move-exception
            java.lang.RuntimeException r1 = r0.rethrowFromSystemServer()
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.app.ActivityThread.handleDumpHeap(android.app.ActivityThread$DumpHeapData):void");
    }

    /*  JADX ERROR: IndexOutOfBoundsException in pass: RegionMakerVisitor
        java.lang.IndexOutOfBoundsException: Index: 0, Size: 0
        	at java.util.ArrayList.rangeCheck(ArrayList.java:659)
        	at java.util.ArrayList.get(ArrayList.java:435)
        	at jadx.core.dex.nodes.InsnNode.getArg(InsnNode.java:101)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:611)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.processMonitorEnter(RegionMaker.java:561)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:133)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processIf(RegionMaker.java:693)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:123)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processIf(RegionMaker.java:693)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:123)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeEndlessLoop(RegionMaker.java:368)
        	at jadx.core.dex.visitors.regions.RegionMaker.processLoop(RegionMaker.java:172)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processMonitorEnter(RegionMaker.java:598)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:133)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processIf(RegionMaker.java:693)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:123)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processSwitch(RegionMaker.java:871)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:128)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processIf(RegionMaker.java:693)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:123)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:49)
        */
    final void handleDispatchPackageBroadcast(int r17, java.lang.String[] r18) {
        /*
            r16 = this;
            r1 = r16
            r2 = r17
            r3 = r18
            r4 = 0
            r5 = 0
            r6 = 1
            if (r2 == 0) goto L_0x00df
            switch(r2) {
                case 2: goto L_0x00df;
                case 3: goto L_0x0010;
                default: goto L_0x000e;
            }
        L_0x000e:
            goto L_0x012d
        L_0x0010:
            if (r3 != 0) goto L_0x0014
            goto L_0x012d
        L_0x0014:
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r7 = r0
            android.app.ResourcesManager r8 = r1.mResourcesManager
            monitor-enter(r8)
            int r0 = r3.length     // Catch:{ all -> 0x00dc }
            int r0 = r0 - r6
        L_0x001f:
            r9 = r0
            if (r9 < 0) goto L_0x00c9
            r0 = r3[r9]     // Catch:{ all -> 0x00dc }
            r10 = r0
            android.util.ArrayMap<java.lang.String, java.lang.ref.WeakReference<android.app.LoadedApk>> r0 = r1.mPackages     // Catch:{ all -> 0x00dc }
            java.lang.Object r0 = r0.get(r10)     // Catch:{ all -> 0x00dc }
            java.lang.ref.WeakReference r0 = (java.lang.ref.WeakReference) r0     // Catch:{ all -> 0x00dc }
            r11 = 0
            if (r0 == 0) goto L_0x0037
            java.lang.Object r12 = r0.get()     // Catch:{ all -> 0x00dc }
            android.app.LoadedApk r12 = (android.app.LoadedApk) r12     // Catch:{ all -> 0x00dc }
            goto L_0x0038
        L_0x0037:
            r12 = r11
        L_0x0038:
            if (r12 == 0) goto L_0x003e
            r4 = 1
        L_0x003b:
            r11 = r4
            r4 = r0
            goto L_0x0055
        L_0x003e:
            android.util.ArrayMap<java.lang.String, java.lang.ref.WeakReference<android.app.LoadedApk>> r13 = r1.mResourcePackages     // Catch:{ all -> 0x00dc }
            java.lang.Object r13 = r13.get(r10)     // Catch:{ all -> 0x00dc }
            java.lang.ref.WeakReference r13 = (java.lang.ref.WeakReference) r13     // Catch:{ all -> 0x00dc }
            r0 = r13
            if (r0 == 0) goto L_0x0050
            java.lang.Object r11 = r0.get()     // Catch:{ all -> 0x00dc }
            android.app.LoadedApk r11 = (android.app.LoadedApk) r11     // Catch:{ all -> 0x00dc }
        L_0x0050:
            r12 = r11
            if (r12 == 0) goto L_0x003b
            r4 = 1
            goto L_0x003b
        L_0x0055:
            if (r12 == 0) goto L_0x00c3
            r7.add(r10)     // Catch:{ all -> 0x00c0 }
            android.content.pm.IPackageManager r0 = sPackageManager     // Catch:{ RemoteException -> 0x00be }
            r13 = 1024(0x400, float:1.435E-42)
            int r14 = android.os.UserHandle.myUserId()     // Catch:{ RemoteException -> 0x00be }
            android.content.pm.ApplicationInfo r0 = r0.getApplicationInfo(r10, r13, r14)     // Catch:{ RemoteException -> 0x00be }
            r13 = r0
            android.util.ArrayMap<android.os.IBinder, android.app.ActivityThread$ActivityClientRecord> r0 = r1.mActivities     // Catch:{ RemoteException -> 0x00be }
            int r0 = r0.size()     // Catch:{ RemoteException -> 0x00be }
            if (r0 <= 0) goto L_0x0098
            android.util.ArrayMap<android.os.IBinder, android.app.ActivityThread$ActivityClientRecord> r0 = r1.mActivities     // Catch:{ RemoteException -> 0x00be }
            java.util.Collection r0 = r0.values()     // Catch:{ RemoteException -> 0x00be }
            java.util.Iterator r0 = r0.iterator()     // Catch:{ RemoteException -> 0x00be }
        L_0x0079:
            boolean r14 = r0.hasNext()     // Catch:{ RemoteException -> 0x00be }
            if (r14 == 0) goto L_0x0098
            java.lang.Object r14 = r0.next()     // Catch:{ RemoteException -> 0x00be }
            android.app.ActivityThread$ActivityClientRecord r14 = (android.app.ActivityThread.ActivityClientRecord) r14     // Catch:{ RemoteException -> 0x00be }
            android.content.pm.ActivityInfo r15 = r14.activityInfo     // Catch:{ RemoteException -> 0x00be }
            android.content.pm.ApplicationInfo r15 = r15.applicationInfo     // Catch:{ RemoteException -> 0x00be }
            java.lang.String r15 = r15.packageName     // Catch:{ RemoteException -> 0x00be }
            boolean r15 = r15.equals(r10)     // Catch:{ RemoteException -> 0x00be }
            if (r15 == 0) goto L_0x0097
            android.content.pm.ActivityInfo r15 = r14.activityInfo     // Catch:{ RemoteException -> 0x00be }
            r15.applicationInfo = r13     // Catch:{ RemoteException -> 0x00be }
            r14.packageInfo = r12     // Catch:{ RemoteException -> 0x00be }
        L_0x0097:
            goto L_0x0079
        L_0x0098:
            java.lang.String[] r0 = new java.lang.String[r6]     // Catch:{ RemoteException -> 0x00be }
            java.lang.String r14 = r12.getResDir()     // Catch:{ RemoteException -> 0x00be }
            r0[r5] = r14     // Catch:{ RemoteException -> 0x00be }
            r14 = r0
            java.util.ArrayList r0 = new java.util.ArrayList     // Catch:{ RemoteException -> 0x00be }
            r0.<init>()     // Catch:{ RemoteException -> 0x00be }
            r15 = r0
            android.content.pm.ApplicationInfo r0 = r12.getApplicationInfo()     // Catch:{ RemoteException -> 0x00be }
            android.app.LoadedApk.makePaths(r1, r0, r15)     // Catch:{ RemoteException -> 0x00be }
            r12.updateApplicationInfo(r13, r15)     // Catch:{ RemoteException -> 0x00be }
            android.app.ResourcesManager r6 = r1.mResourcesManager     // Catch:{ RemoteException -> 0x00be }
            monitor-enter(r6)     // Catch:{ RemoteException -> 0x00be }
            android.app.ResourcesManager r0 = r1.mResourcesManager     // Catch:{ all -> 0x00bb }
            r0.applyNewResourceDirsLocked(r13, r14)     // Catch:{ all -> 0x00bb }
            monitor-exit(r6)     // Catch:{ all -> 0x00bb }
            goto L_0x00c3
        L_0x00bb:
            r0 = move-exception
            monitor-exit(r6)     // Catch:{ all -> 0x00bb }
            throw r0     // Catch:{ RemoteException -> 0x00be }
        L_0x00be:
            r0 = move-exception
            goto L_0x00c3
        L_0x00c0:
            r0 = move-exception
            r4 = r11
            goto L_0x00dd
        L_0x00c3:
            int r0 = r9 + -1
            r4 = r11
            r6 = 1
            goto L_0x001f
        L_0x00c9:
            monitor-exit(r8)     // Catch:{ all -> 0x00dc }
            android.content.pm.IPackageManager r0 = getPackageManager()     // Catch:{ RemoteException -> 0x00da }
            java.lang.String[] r5 = new java.lang.String[r5]     // Catch:{ RemoteException -> 0x00da }
            java.lang.Object[] r5 = r7.toArray(r5)     // Catch:{ RemoteException -> 0x00da }
            java.lang.String[] r5 = (java.lang.String[]) r5     // Catch:{ RemoteException -> 0x00da }
            r0.notifyPackagesReplacedReceived(r5)     // Catch:{ RemoteException -> 0x00da }
            goto L_0x012d
        L_0x00da:
            r0 = move-exception
            goto L_0x012d
        L_0x00dc:
            r0 = move-exception
        L_0x00dd:
            monitor-exit(r8)     // Catch:{ all -> 0x00dc }
            throw r0
        L_0x00df:
            if (r2 != 0) goto L_0x00e3
            r5 = 1
        L_0x00e3:
            if (r3 != 0) goto L_0x00e6
            goto L_0x012d
        L_0x00e6:
            android.app.ResourcesManager r6 = r1.mResourcesManager
            monitor-enter(r6)
            int r0 = r3.length     // Catch:{ all -> 0x0131 }
            r7 = 1
            int r0 = r0 - r7
        L_0x00ec:
            if (r0 < 0) goto L_0x012b
            if (r4 != 0) goto L_0x0118
            android.util.ArrayMap<java.lang.String, java.lang.ref.WeakReference<android.app.LoadedApk>> r7 = r1.mPackages     // Catch:{ all -> 0x0131 }
            r8 = r3[r0]     // Catch:{ all -> 0x0131 }
            java.lang.Object r7 = r7.get(r8)     // Catch:{ all -> 0x0131 }
            java.lang.ref.WeakReference r7 = (java.lang.ref.WeakReference) r7     // Catch:{ all -> 0x0131 }
            if (r7 == 0) goto L_0x0104
            java.lang.Object r8 = r7.get()     // Catch:{ all -> 0x0131 }
            if (r8 == 0) goto L_0x0104
            r4 = 1
            goto L_0x0118
        L_0x0104:
            android.util.ArrayMap<java.lang.String, java.lang.ref.WeakReference<android.app.LoadedApk>> r8 = r1.mResourcePackages     // Catch:{ all -> 0x0131 }
            r9 = r3[r0]     // Catch:{ all -> 0x0131 }
            java.lang.Object r8 = r8.get(r9)     // Catch:{ all -> 0x0131 }
            java.lang.ref.WeakReference r8 = (java.lang.ref.WeakReference) r8     // Catch:{ all -> 0x0131 }
            r7 = r8
            if (r7 == 0) goto L_0x0118
            java.lang.Object r8 = r7.get()     // Catch:{ all -> 0x0131 }
            if (r8 == 0) goto L_0x0118
            r4 = 1
        L_0x0118:
            if (r5 == 0) goto L_0x0128
            android.util.ArrayMap<java.lang.String, java.lang.ref.WeakReference<android.app.LoadedApk>> r7 = r1.mPackages     // Catch:{ all -> 0x0131 }
            r8 = r3[r0]     // Catch:{ all -> 0x0131 }
            r7.remove(r8)     // Catch:{ all -> 0x0131 }
            android.util.ArrayMap<java.lang.String, java.lang.ref.WeakReference<android.app.LoadedApk>> r7 = r1.mResourcePackages     // Catch:{ all -> 0x0131 }
            r8 = r3[r0]     // Catch:{ all -> 0x0131 }
            r7.remove(r8)     // Catch:{ all -> 0x0131 }
        L_0x0128:
            int r0 = r0 + -1
            goto L_0x00ec
        L_0x012b:
            monitor-exit(r6)     // Catch:{ all -> 0x0131 }
        L_0x012d:
            android.app.ApplicationPackageManager.handlePackageBroadcast(r2, r3, r4)
            return
        L_0x0131:
            r0 = move-exception
            monitor-exit(r6)     // Catch:{ all -> 0x0131 }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.app.ActivityThread.handleDispatchPackageBroadcast(int, java.lang.String[]):void");
    }

    /* access modifiers changed from: package-private */
    public final void handleLowMemory() {
        ArrayList<ComponentCallbacks2> callbacks = collectComponentCallbacks(true, (Configuration) null);
        int N = callbacks.size();
        for (int i = 0; i < N; i++) {
            callbacks.get(i).onLowMemory();
        }
        if (Process.myUid() != 1000) {
            EventLog.writeEvent((int) SQLITE_MEM_RELEASED_EVENT_LOG_TAG, SQLiteDatabase.releaseMemory());
        }
        Canvas.freeCaches();
        Canvas.freeTextLayoutCaches();
        BinderInternal.forceGc("mem");
    }

    /* access modifiers changed from: private */
    public void handleTrimMemory(int level) {
        Trace.traceBegin(64, "trimMemory");
        ArrayList<ComponentCallbacks2> callbacks = collectComponentCallbacks(true, (Configuration) null);
        int N = callbacks.size();
        for (int i = 0; i < N; i++) {
            callbacks.get(i).onTrimMemory(level);
        }
        WindowManagerGlobal.getInstance().trimMemory(level);
        Trace.traceEnd(64);
        if (SystemProperties.getInt("debug.am.run_gc_trim_level", Integer.MAX_VALUE) <= level) {
            unscheduleGcIdler();
            doGcIfNeeded("tm");
        }
        if (SystemProperties.getInt("debug.am.run_mallopt_trim_level", Integer.MAX_VALUE) <= level) {
            unschedulePurgeIdler();
            purgePendingResources();
        }
    }

    private void setupGraphicsSupport(Context context) {
        Trace.traceBegin(64, "setupGraphicsSupport");
        if (!"android".equals(context.getPackageName())) {
            File cacheDir = context.getCacheDir();
            if (cacheDir != null) {
                System.setProperty("java.io.tmpdir", cacheDir.getAbsolutePath());
            } else {
                Log.v(TAG, "Unable to initialize \"java.io.tmpdir\" property due to missing cache directory");
            }
            File codeCacheDir = context.createDeviceProtectedStorageContext().getCodeCacheDir();
            if (codeCacheDir != null) {
                try {
                    if (getPackageManager().getPackagesForUid(Process.myUid()) != null) {
                        HardwareRenderer.setupDiskCache(codeCacheDir);
                        RenderScriptCacheDir.setupDiskCache(codeCacheDir);
                    }
                } catch (RemoteException e) {
                    Trace.traceEnd(64);
                    throw e.rethrowFromSystemServer();
                }
            } else {
                Log.w(TAG, "Unable to use shader/script cache: missing code-cache directory");
            }
        }
        GraphicsEnvironment.getInstance().setup(context, this.mCoreSettings);
        Trace.traceEnd(64);
    }

    private void updateDefaultDensity() {
        int densityDpi = this.mCurDefaultDisplayDpi;
        if (!this.mDensityCompatMode && densityDpi != 0 && densityDpi != DisplayMetrics.DENSITY_DEVICE) {
            DisplayMetrics.DENSITY_DEVICE = densityDpi;
            Bitmap.setDefaultDensity(densityDpi);
        }
    }

    private String getInstrumentationLibrary(ApplicationInfo appInfo, InstrumentationInfo insInfo) {
        if (!(appInfo.primaryCpuAbi == null || appInfo.secondaryCpuAbi == null || !appInfo.secondaryCpuAbi.equals(insInfo.secondaryCpuAbi))) {
            String secondaryIsa = VMRuntime.getInstructionSet(appInfo.secondaryCpuAbi);
            String secondaryDexCodeIsa = SystemProperties.get("ro.dalvik.vm.isa." + secondaryIsa);
            if (VMRuntime.getRuntime().vmInstructionSet().equals(secondaryDexCodeIsa.isEmpty() ? secondaryIsa : secondaryDexCodeIsa)) {
                return insInfo.secondaryNativeLibraryDir;
            }
        }
        return insInfo.nativeLibraryDir;
    }

    private void updateLocaleListFromAppContext(Context context, LocaleList newLocaleList) {
        Locale bestLocale = context.getResources().getConfiguration().getLocales().get(0);
        int newLocaleListSize = newLocaleList.size();
        for (int i = 0; i < newLocaleListSize; i++) {
            if (bestLocale.equals(newLocaleList.get(i))) {
                LocaleList.setDefault(newLocaleList, i);
                return;
            }
        }
        LocaleList.setDefault(new LocaleList(bestLocale, newLocaleList));
    }

    /* JADX INFO: finally extract failed */
    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:220:0x05db, code lost:
        r0 = th;
     */
    @android.annotation.UnsupportedAppUsage
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void handleBindApplication(android.app.ActivityThread.AppBindData r34) {
        /*
            r33 = this;
            r8 = r33
            r9 = r34
            long r10 = android.os.SystemClock.uptimeMillis()
            r1 = 0
            dalvik.system.VMRuntime.registerSensitiveThread()
            java.lang.String r0 = "debug.allocTracker.stackDepth"
            java.lang.String r12 = android.os.SystemProperties.get(r0)
            int r0 = r12.length()
            if (r0 == 0) goto L_0x001f
            int r0 = java.lang.Integer.parseInt(r12)
            dalvik.system.VMDebug.setAllocTrackerStackDepth(r0)
        L_0x001f:
            boolean r0 = r9.trackAllocation
            r13 = 1
            if (r0 == 0) goto L_0x0027
            org.apache.harmony.dalvik.ddmc.DdmVmInternal.enableRecentAllocations(r13)
        L_0x0027:
            long r2 = android.os.SystemClock.elapsedRealtime()
            long r4 = android.os.SystemClock.uptimeMillis()
            android.os.Process.setStartTimes(r2, r4)
            r8.mBoundApplication = r9
            android.content.res.Configuration r0 = new android.content.res.Configuration
            android.content.res.Configuration r2 = r9.config
            r0.<init>((android.content.res.Configuration) r2)
            r8.mConfiguration = r0
            android.content.res.Configuration r0 = new android.content.res.Configuration
            android.content.res.Configuration r2 = r9.config
            r0.<init>((android.content.res.Configuration) r2)
            r8.mCompatConfiguration = r0
            android.app.ActivityThread$Profiler r0 = new android.app.ActivityThread$Profiler
            r0.<init>()
            r8.mProfiler = r0
            r0 = 0
            android.app.ProfilerInfo r2 = r9.initProfilerInfo
            if (r2 == 0) goto L_0x0084
            android.app.ActivityThread$Profiler r2 = r8.mProfiler
            android.app.ProfilerInfo r3 = r9.initProfilerInfo
            java.lang.String r3 = r3.profileFile
            r2.profileFile = r3
            android.app.ActivityThread$Profiler r2 = r8.mProfiler
            android.app.ProfilerInfo r3 = r9.initProfilerInfo
            android.os.ParcelFileDescriptor r3 = r3.profileFd
            r2.profileFd = r3
            android.app.ActivityThread$Profiler r2 = r8.mProfiler
            android.app.ProfilerInfo r3 = r9.initProfilerInfo
            int r3 = r3.samplingInterval
            r2.samplingInterval = r3
            android.app.ActivityThread$Profiler r2 = r8.mProfiler
            android.app.ProfilerInfo r3 = r9.initProfilerInfo
            boolean r3 = r3.autoStopProfiler
            r2.autoStopProfiler = r3
            android.app.ActivityThread$Profiler r2 = r8.mProfiler
            android.app.ProfilerInfo r3 = r9.initProfilerInfo
            boolean r3 = r3.streamingOutput
            r2.streamingOutput = r3
            android.app.ProfilerInfo r2 = r9.initProfilerInfo
            boolean r2 = r2.attachAgentDuringBind
            if (r2 == 0) goto L_0x0084
            android.app.ProfilerInfo r2 = r9.initProfilerInfo
            java.lang.String r0 = r2.agent
        L_0x0084:
            r14 = r0
            java.lang.String r0 = r9.processName
            android.os.Process.setArgV0(r0)
            java.lang.String r0 = r9.processName
            int r2 = android.os.UserHandle.myUserId()
            android.ddm.DdmHandleAppName.setAppName(r0, r2)
            android.content.pm.ApplicationInfo r0 = r9.appInfo
            java.lang.String r0 = r0.packageName
            dalvik.system.VMRuntime.setProcessPackageName(r0)
            android.content.pm.ApplicationInfo r0 = r9.appInfo
            java.lang.String r0 = r0.dataDir
            dalvik.system.VMRuntime.setProcessDataDirectory(r0)
            android.app.ActivityThread$Profiler r0 = r8.mProfiler
            android.os.ParcelFileDescriptor r0 = r0.profileFd
            if (r0 == 0) goto L_0x00ac
            android.app.ActivityThread$Profiler r0 = r8.mProfiler
            r0.startProfiling()
        L_0x00ac:
            android.content.pm.ApplicationInfo r0 = r9.appInfo
            int r0 = r0.targetSdkVersion
            r2 = 12
            if (r0 > r2) goto L_0x00b9
            java.util.concurrent.Executor r0 = android.os.AsyncTask.THREAD_POOL_EXECUTOR
            android.os.AsyncTask.setDefaultExecutor(r0)
        L_0x00b9:
            android.content.pm.ApplicationInfo r0 = r9.appInfo
            int r0 = r0.targetSdkVersion
            r2 = 29
            r15 = 0
            if (r0 < r2) goto L_0x00c4
            r0 = r13
            goto L_0x00c5
        L_0x00c4:
            r0 = r15
        L_0x00c5:
            android.util.UtilConfig.setThrowExceptionForUpperArrayOutOfBounds(r0)
            android.content.pm.ApplicationInfo r0 = r9.appInfo
            int r0 = r0.targetSdkVersion
            android.os.Message.updateCheckRecycle(r0)
            android.content.pm.ApplicationInfo r0 = r9.appInfo
            int r0 = r0.targetSdkVersion
            android.graphics.ImageDecoder.sApiLevel = r0
            r7 = 0
            java.util.TimeZone.setDefault(r7)
            android.content.res.Configuration r0 = r9.config
            android.os.LocaleList r0 = r0.getLocales()
            android.os.LocaleList.setDefault(r0)
            android.app.ResourcesManager r2 = r8.mResourcesManager
            monitor-enter(r2)
            android.app.ResourcesManager r0 = r8.mResourcesManager     // Catch:{ all -> 0x05d6 }
            android.content.res.Configuration r3 = r9.config     // Catch:{ all -> 0x05d6 }
            android.content.res.CompatibilityInfo r4 = r9.compatInfo     // Catch:{ all -> 0x05d6 }
            r0.applyConfigurationToResourcesLocked(r3, r4)     // Catch:{ all -> 0x05d6 }
            android.content.res.Configuration r0 = r9.config     // Catch:{ all -> 0x05d6 }
            int r0 = r0.densityDpi     // Catch:{ all -> 0x05d6 }
            r8.mCurDefaultDisplayDpi = r0     // Catch:{ all -> 0x05d6 }
            int r0 = r8.mCurDefaultDisplayDpi     // Catch:{ all -> 0x05d6 }
            r8.applyCompatConfiguration(r0)     // Catch:{ all -> 0x05d6 }
            monitor-exit(r2)     // Catch:{ all -> 0x05d6 }
            android.content.pm.ApplicationInfo r0 = r9.appInfo
            android.content.res.CompatibilityInfo r2 = r9.compatInfo
            android.app.LoadedApk r0 = r8.getPackageInfoNoCheck(r0, r2)
            r9.info = r0
            if (r14 == 0) goto L_0x010b
            android.app.LoadedApk r0 = r9.info
            handleAttachAgent(r14, r0)
        L_0x010b:
            android.content.pm.ApplicationInfo r0 = r9.appInfo
            int r0 = r0.flags
            r0 = r0 & 8192(0x2000, float:1.14794E-41)
            if (r0 != 0) goto L_0x011b
            r8.mDensityCompatMode = r13
            r0 = 160(0xa0, float:2.24E-43)
            android.graphics.Bitmap.setDefaultDensity(r0)
            goto L_0x0149
        L_0x011b:
            android.content.pm.ApplicationInfo r0 = r9.appInfo
            int r0 = r0.getOverrideDensity()
            if (r0 == 0) goto L_0x0149
            java.lang.String r2 = "ActivityThread"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "override app density from "
            r3.append(r4)
            int r4 = android.util.DisplayMetrics.DENSITY_DEVICE
            r3.append(r4)
            java.lang.String r4 = " to "
            r3.append(r4)
            r3.append(r0)
            java.lang.String r3 = r3.toString()
            android.util.Log.d(r2, r3)
            r8.mDensityCompatMode = r13
            android.graphics.Bitmap.setDefaultDensity(r0)
        L_0x0149:
            r33.updateDefaultDensity()
            android.os.Bundle r0 = r8.mCoreSettings
            java.lang.String r2 = "time_12_24"
            java.lang.String r6 = r0.getString(r2)
            r0 = 0
            if (r6 == 0) goto L_0x0166
            java.lang.String r2 = "24"
            boolean r2 = r2.equals(r6)
            if (r2 == 0) goto L_0x0163
            java.lang.Boolean r2 = java.lang.Boolean.TRUE
            goto L_0x0165
        L_0x0163:
            java.lang.Boolean r2 = java.lang.Boolean.FALSE
        L_0x0165:
            r0 = r2
        L_0x0166:
            r16 = r0
            java.text.DateFormat.set24HourTimePref(r16)
            r33.updateDebugViewAttributeState()
            android.content.pm.ApplicationInfo r0 = r9.appInfo
            android.os.StrictMode.initThreadDefaults(r0)
            android.content.pm.ApplicationInfo r0 = r9.appInfo
            android.os.StrictMode.initVmDefaults(r0)
            int r0 = r9.debugMode
            r2 = 2
            if (r0 == 0) goto L_0x01e9
            r0 = 8100(0x1fa4, float:1.135E-41)
            android.os.Debug.changeDebugPort(r0)
            int r0 = r9.debugMode
            if (r0 != r2) goto L_0x01c8
            java.lang.String r0 = "ActivityThread"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "Application "
            r3.append(r4)
            android.app.LoadedApk r4 = r9.info
            java.lang.String r4 = r4.getPackageName()
            r3.append(r4)
            java.lang.String r4 = " is waiting for the debugger on port 8100..."
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            android.util.Slog.w((java.lang.String) r0, (java.lang.String) r3)
            android.app.IActivityManager r0 = android.app.ActivityManager.getService()
            r3 = r0
            android.app.ActivityThread$ApplicationThread r0 = r8.mAppThread     // Catch:{ RemoteException -> 0x01c2 }
            r3.showWaitingForDebugger(r0, r13)     // Catch:{ RemoteException -> 0x01c2 }
            android.os.Debug.waitForDebugger()
            android.app.ActivityThread$ApplicationThread r0 = r8.mAppThread     // Catch:{ RemoteException -> 0x01bc }
            r3.showWaitingForDebugger(r0, r15)     // Catch:{ RemoteException -> 0x01bc }
            goto L_0x01e9
        L_0x01bc:
            r0 = move-exception
            java.lang.RuntimeException r2 = r0.rethrowFromSystemServer()
            throw r2
        L_0x01c2:
            r0 = move-exception
            java.lang.RuntimeException r2 = r0.rethrowFromSystemServer()
            throw r2
        L_0x01c8:
            java.lang.String r0 = "ActivityThread"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "Application "
            r3.append(r4)
            android.app.LoadedApk r4 = r9.info
            java.lang.String r4 = r4.getPackageName()
            r3.append(r4)
            java.lang.String r4 = " can be debugged on port 8100..."
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            android.util.Slog.w((java.lang.String) r0, (java.lang.String) r3)
        L_0x01e9:
            android.content.pm.ApplicationInfo r0 = r9.appInfo
            boolean r17 = r0.isProfileableByShell()
            android.os.Trace.setAppTracingAllowed(r17)
            if (r17 == 0) goto L_0x01fb
            boolean r0 = r9.enableBinderTracking
            if (r0 == 0) goto L_0x01fb
            android.os.Binder.enableTracing()
        L_0x01fb:
            if (r17 != 0) goto L_0x0201
            boolean r0 = android.os.Build.IS_DEBUGGABLE
            if (r0 == 0) goto L_0x0204
        L_0x0201:
            r33.nInitZygoteChildHeapProfiling()
        L_0x0204:
            android.content.pm.ApplicationInfo r0 = r9.appInfo
            int r0 = r0.flags
            r0 = r0 & r2
            if (r0 == 0) goto L_0x020d
            r0 = r13
            goto L_0x020e
        L_0x020d:
            r0 = r15
        L_0x020e:
            r18 = r0
            if (r18 != 0) goto L_0x0219
            boolean r0 = android.os.Build.IS_DEBUGGABLE
            if (r0 == 0) goto L_0x0217
            goto L_0x0219
        L_0x0217:
            r0 = r15
            goto L_0x021a
        L_0x0219:
            r0 = r13
        L_0x021a:
            android.graphics.HardwareRenderer.setDebuggingEnabled(r0)
            android.content.pm.ApplicationInfo r0 = r9.appInfo
            java.lang.String r0 = r0.packageName
            android.graphics.HardwareRenderer.setPackageName(r0)
            java.lang.String r0 = "Setup proxies"
            r2 = 64
            android.os.Trace.traceBegin(r2, r0)
            java.lang.String r0 = "connectivity"
            android.os.IBinder r19 = android.os.ServiceManager.getService(r0)
            if (r19 == 0) goto L_0x0249
            android.net.IConnectivityManager r0 = android.net.IConnectivityManager.Stub.asInterface(r19)
            r4 = r0
            android.net.ProxyInfo r0 = r4.getProxyForNetwork(r7)     // Catch:{ RemoteException -> 0x0240 }
            android.net.Proxy.setHttpProxySystemProperty(r0)     // Catch:{ RemoteException -> 0x0240 }
            goto L_0x0249
        L_0x0240:
            r0 = move-exception
            android.os.Trace.traceEnd(r2)
            java.lang.RuntimeException r2 = r0.rethrowFromSystemServer()
            throw r2
        L_0x0249:
            android.os.Trace.traceEnd(r2)
            android.content.ComponentName r0 = r9.instrumentationName
            if (r0 == 0) goto L_0x0310
            android.app.ApplicationPackageManager r0 = new android.app.ApplicationPackageManager     // Catch:{ NameNotFoundException -> 0x02f6 }
            android.content.pm.IPackageManager r4 = getPackageManager()     // Catch:{ NameNotFoundException -> 0x02f6 }
            r0.<init>(r7, r4)     // Catch:{ NameNotFoundException -> 0x02f6 }
            android.content.ComponentName r4 = r9.instrumentationName     // Catch:{ NameNotFoundException -> 0x02f6 }
            android.content.pm.InstrumentationInfo r0 = r0.getInstrumentationInfo(r4, r15)     // Catch:{ NameNotFoundException -> 0x02f6 }
            android.content.pm.ApplicationInfo r4 = r9.appInfo
            java.lang.String r4 = r4.primaryCpuAbi
            java.lang.String r5 = r0.primaryCpuAbi
            boolean r4 = java.util.Objects.equals(r4, r5)
            if (r4 == 0) goto L_0x0279
            android.content.pm.ApplicationInfo r4 = r9.appInfo
            java.lang.String r4 = r4.secondaryCpuAbi
            java.lang.String r5 = r0.secondaryCpuAbi
            boolean r4 = java.util.Objects.equals(r4, r5)
            if (r4 != 0) goto L_0x02c9
        L_0x0279:
            java.lang.String r4 = "ActivityThread"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r7 = "Package uses different ABI(s) than its instrumentation: package["
            r5.append(r7)
            android.content.pm.ApplicationInfo r7 = r9.appInfo
            java.lang.String r7 = r7.packageName
            r5.append(r7)
            java.lang.String r7 = "]: "
            r5.append(r7)
            android.content.pm.ApplicationInfo r7 = r9.appInfo
            java.lang.String r7 = r7.primaryCpuAbi
            r5.append(r7)
            java.lang.String r7 = ", "
            r5.append(r7)
            android.content.pm.ApplicationInfo r7 = r9.appInfo
            java.lang.String r7 = r7.secondaryCpuAbi
            r5.append(r7)
            java.lang.String r7 = " instrumentation["
            r5.append(r7)
            java.lang.String r7 = r0.packageName
            r5.append(r7)
            java.lang.String r7 = "]: "
            r5.append(r7)
            java.lang.String r7 = r0.primaryCpuAbi
            r5.append(r7)
            java.lang.String r7 = ", "
            r5.append(r7)
            java.lang.String r7 = r0.secondaryCpuAbi
            r5.append(r7)
            java.lang.String r5 = r5.toString()
            android.util.Slog.w((java.lang.String) r4, (java.lang.String) r5)
        L_0x02c9:
            java.lang.String r4 = r0.packageName
            r8.mInstrumentationPackageName = r4
            java.lang.String r4 = r0.sourceDir
            r8.mInstrumentationAppDir = r4
            java.lang.String[] r4 = r0.splitSourceDirs
            r8.mInstrumentationSplitAppDirs = r4
            android.content.pm.ApplicationInfo r4 = r9.appInfo
            java.lang.String r4 = r8.getInstrumentationLibrary(r4, r0)
            r8.mInstrumentationLibDir = r4
            android.app.LoadedApk r4 = r9.info
            java.lang.String r4 = r4.getAppDir()
            r8.mInstrumentedAppDir = r4
            android.app.LoadedApk r4 = r9.info
            java.lang.String[] r4 = r4.getSplitAppDirs()
            r8.mInstrumentedSplitAppDirs = r4
            android.app.LoadedApk r4 = r9.info
            java.lang.String r4 = r4.getLibDir()
            r8.mInstrumentedLibDir = r4
            goto L_0x0311
        L_0x02f6:
            r0 = move-exception
            java.lang.RuntimeException r2 = new java.lang.RuntimeException
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "Unable to find instrumentation info for: "
            r3.append(r4)
            android.content.ComponentName r4 = r9.instrumentationName
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            r2.<init>(r3)
            throw r2
        L_0x0310:
            r0 = 0
        L_0x0311:
            r7 = r0
            android.app.LoadedApk r0 = r9.info
            android.app.ContextImpl r5 = android.app.ContextImpl.createAppContext(r8, r0)
            android.app.ResourcesManager r0 = r8.mResourcesManager
            android.content.res.Configuration r0 = r0.getConfiguration()
            android.os.LocaleList r0 = r0.getLocales()
            r8.updateLocaleListFromAppContext(r5, r0)
            boolean r0 = android.os.Process.isIsolated()
            if (r0 != 0) goto L_0x033f
            int r0 = android.os.StrictMode.allowThreadDiskWritesMask()
            r4 = r0
            android.util.BoostFramework r0 = new android.util.BoostFramework     // Catch:{ all -> 0x033a }
            r0.<init>((android.content.Context) r5)     // Catch:{ all -> 0x033a }
            r1 = r0
            android.os.StrictMode.setThreadPolicyMask(r4)
            goto L_0x033f
        L_0x033a:
            r0 = move-exception
            android.os.StrictMode.setThreadPolicyMask(r4)
            throw r0
        L_0x033f:
            r26 = r1
            boolean r0 = android.os.Process.isIsolated()
            if (r0 != 0) goto L_0x035a
            int r0 = android.os.StrictMode.allowThreadDiskWritesMask()
            r1 = r0
            r8.setupGraphicsSupport(r5)     // Catch:{ all -> 0x0354 }
            android.os.StrictMode.setThreadPolicyMask(r1)
            goto L_0x035d
        L_0x0354:
            r0 = move-exception
            r2 = r0
            android.os.StrictMode.setThreadPolicyMask(r1)
            throw r2
        L_0x035a:
            android.graphics.HardwareRenderer.setIsolatedProcess(r13)
        L_0x035d:
            java.lang.String r0 = "NetworkSecurityConfigProvider.install"
            android.os.Trace.traceBegin(r2, r0)
            android.security.net.config.NetworkSecurityConfigProvider.install(r5)
            android.os.Trace.traceEnd(r2)
            if (r7 == 0) goto L_0x043e
            android.content.pm.IPackageManager r0 = getPackageManager()     // Catch:{ RemoteException -> 0x0379 }
            java.lang.String r1 = r7.packageName     // Catch:{ RemoteException -> 0x0379 }
            int r2 = android.os.UserHandle.myUserId()     // Catch:{ RemoteException -> 0x0379 }
            android.content.pm.ApplicationInfo r0 = r0.getApplicationInfo(r1, r15, r2)     // Catch:{ RemoteException -> 0x0379 }
            goto L_0x037b
        L_0x0379:
            r0 = move-exception
            r0 = 0
        L_0x037b:
            if (r0 != 0) goto L_0x0383
            android.content.pm.ApplicationInfo r1 = new android.content.pm.ApplicationInfo
            r1.<init>()
            r0 = r1
        L_0x0383:
            r4 = r0
            r7.copyTo(r4)
            int r0 = android.os.UserHandle.myUserId()
            r4.initForUser(r0)
            android.content.res.CompatibilityInfo r3 = r9.compatInfo
            java.lang.ClassLoader r0 = r5.getClassLoader()
            r21 = 0
            r22 = 1
            r23 = 0
            r1 = r33
            r2 = r4
            r24 = r4
            r4 = r0
            r27 = r5
            r5 = r21
            r28 = r6
            r6 = r22
            r15 = r7
            r7 = r23
            android.app.LoadedApk r7 = r1.getPackageInfo(r2, r3, r4, r5, r6, r7)
            java.lang.String r0 = r27.getOpPackageName()
            android.app.ContextImpl r3 = android.app.ContextImpl.createAppContext(r8, r7, r0)
            java.lang.ClassLoader r0 = r3.getClassLoader()     // Catch:{ Exception -> 0x0416 }
            android.content.ComponentName r1 = r9.instrumentationName     // Catch:{ Exception -> 0x0416 }
            java.lang.String r1 = r1.getClassName()     // Catch:{ Exception -> 0x0416 }
            java.lang.Class r1 = r0.loadClass(r1)     // Catch:{ Exception -> 0x0416 }
            java.lang.Object r1 = r1.newInstance()     // Catch:{ Exception -> 0x0416 }
            android.app.Instrumentation r1 = (android.app.Instrumentation) r1     // Catch:{ Exception -> 0x0416 }
            r8.mInstrumentation = r1     // Catch:{ Exception -> 0x0416 }
            android.content.ComponentName r5 = new android.content.ComponentName
            java.lang.String r0 = r15.packageName
            java.lang.String r1 = r15.name
            r5.<init>((java.lang.String) r0, (java.lang.String) r1)
            android.app.Instrumentation r1 = r8.mInstrumentation
            android.app.IInstrumentationWatcher r6 = r9.instrumentationWatcher
            android.app.IUiAutomationConnection r0 = r9.instrumentationUiAutomationConnection
            r2 = r33
            r4 = r27
            r20 = r7
            r7 = r0
            r1.init(r2, r3, r4, r5, r6, r7)
            android.app.ActivityThread$Profiler r0 = r8.mProfiler
            java.lang.String r0 = r0.profileFile
            if (r0 == 0) goto L_0x0415
            boolean r0 = r15.handleProfiling
            if (r0 != 0) goto L_0x0415
            android.app.ActivityThread$Profiler r0 = r8.mProfiler
            android.os.ParcelFileDescriptor r0 = r0.profileFd
            if (r0 != 0) goto L_0x0415
            android.app.ActivityThread$Profiler r0 = r8.mProfiler
            r0.handlingProfiling = r13
            java.io.File r0 = new java.io.File
            android.app.ActivityThread$Profiler r1 = r8.mProfiler
            java.lang.String r1 = r1.profileFile
            r0.<init>(r1)
            java.io.File r1 = r0.getParentFile()
            r1.mkdirs()
            java.lang.String r1 = r0.toString()
            r2 = 8388608(0x800000, float:1.17549435E-38)
            android.os.Debug.startMethodTracing(r1, r2)
        L_0x0415:
            goto L_0x044f
        L_0x0416:
            r0 = move-exception
            r20 = r7
            java.lang.RuntimeException r1 = new java.lang.RuntimeException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r4 = "Unable to instantiate instrumentation "
            r2.append(r4)
            android.content.ComponentName r4 = r9.instrumentationName
            r2.append(r4)
            java.lang.String r4 = ": "
            r2.append(r4)
            java.lang.String r4 = r0.toString()
            r2.append(r4)
            java.lang.String r2 = r2.toString()
            r1.<init>(r2, r0)
            throw r1
        L_0x043e:
            r27 = r5
            r28 = r6
            r15 = r7
            android.app.Instrumentation r0 = new android.app.Instrumentation
            r0.<init>()
            r8.mInstrumentation = r0
            android.app.Instrumentation r0 = r8.mInstrumentation
            r0.basicInit(r8)
        L_0x044f:
            android.content.pm.ApplicationInfo r0 = r9.appInfo
            int r0 = r0.flags
            r1 = 1048576(0x100000, float:1.469368E-39)
            r0 = r0 & r1
            if (r0 == 0) goto L_0x0460
            dalvik.system.VMRuntime r0 = dalvik.system.VMRuntime.getRuntime()
            r0.clearGrowthLimit()
            goto L_0x0467
        L_0x0460:
            dalvik.system.VMRuntime r0 = dalvik.system.VMRuntime.getRuntime()
            r0.clampGrowthLimit()
        L_0x0467:
            android.os.StrictMode$ThreadPolicy r1 = android.os.StrictMode.allowThreadDiskWrites()
            android.os.StrictMode$ThreadPolicy r0 = android.os.StrictMode.getThreadPolicy()
            r2 = r0
            r3 = 27
            android.app.LoadedApk r0 = r9.info     // Catch:{ all -> 0x05bf }
            boolean r4 = r9.restrictedBackupMode     // Catch:{ all -> 0x05bf }
            r5 = 0
            android.app.Application r0 = r0.makeApplication(r4, r5)     // Catch:{ all -> 0x05bf }
            r4 = r0
            android.content.AutofillOptions r0 = r9.autofillOptions     // Catch:{ all -> 0x05bf }
            r4.setAutofillOptions(r0)     // Catch:{ all -> 0x05bf }
            android.content.ContentCaptureOptions r0 = r9.contentCaptureOptions     // Catch:{ all -> 0x05bf }
            r4.setContentCaptureOptions(r0)     // Catch:{ all -> 0x05bf }
            r8.mInitialApplication = r4     // Catch:{ all -> 0x05bf }
            boolean r0 = r9.restrictedBackupMode     // Catch:{ all -> 0x05bf }
            if (r0 != 0) goto L_0x049f
            java.util.List<android.content.pm.ProviderInfo> r0 = r9.providers     // Catch:{ all -> 0x049a }
            boolean r0 = com.android.internal.util.ArrayUtils.isEmpty((java.util.Collection<?>) r0)     // Catch:{ all -> 0x049a }
            if (r0 != 0) goto L_0x049f
            java.util.List<android.content.pm.ProviderInfo> r0 = r9.providers     // Catch:{ all -> 0x049a }
            r8.installContentProviders(r4, r0)     // Catch:{ all -> 0x049a }
            goto L_0x049f
        L_0x049a:
            r0 = move-exception
            r29 = r12
            goto L_0x05c2
        L_0x049f:
            android.app.Instrumentation r0 = r8.mInstrumentation     // Catch:{ Exception -> 0x0595 }
            android.os.Bundle r6 = r9.instrumentationArgs     // Catch:{ Exception -> 0x0595 }
            r0.onCreate(r6)     // Catch:{ Exception -> 0x0595 }
            android.app.Instrumentation r0 = r8.mInstrumentation     // Catch:{ Exception -> 0x04ad }
            r0.callApplicationOnCreate(r4)     // Catch:{ Exception -> 0x04ad }
            goto L_0x04b6
        L_0x04ad:
            r0 = move-exception
            android.app.Instrumentation r6 = r8.mInstrumentation     // Catch:{ all -> 0x05bf }
            boolean r6 = r6.onException(r4, r0)     // Catch:{ all -> 0x05bf }
            if (r6 == 0) goto L_0x0568
        L_0x04b6:
            android.content.pm.ApplicationInfo r0 = r9.appInfo
            int r0 = r0.targetSdkVersion
            if (r0 < r3) goto L_0x04c6
            android.os.StrictMode$ThreadPolicy r0 = android.os.StrictMode.getThreadPolicy()
            boolean r0 = r0.equals(r2)
            if (r0 == 0) goto L_0x04c9
        L_0x04c6:
            android.os.StrictMode.setThreadPolicy(r1)
        L_0x04c9:
            android.provider.FontsContract.setApplicationContextForResources(r27)
            boolean r0 = android.os.Process.isIsolated()
            if (r0 != 0) goto L_0x0504
            android.content.pm.IPackageManager r0 = getPackageManager()     // Catch:{ RemoteException -> 0x04fe }
            android.content.pm.ApplicationInfo r3 = r9.appInfo     // Catch:{ RemoteException -> 0x04fe }
            java.lang.String r3 = r3.packageName     // Catch:{ RemoteException -> 0x04fe }
            r6 = 128(0x80, float:1.794E-43)
            int r7 = android.os.UserHandle.myUserId()     // Catch:{ RemoteException -> 0x04fe }
            android.content.pm.ApplicationInfo r0 = r0.getApplicationInfo(r3, r6, r7)     // Catch:{ RemoteException -> 0x04fe }
            android.os.Bundle r3 = r0.metaData     // Catch:{ RemoteException -> 0x04fe }
            if (r3 == 0) goto L_0x04fd
            android.os.Bundle r3 = r0.metaData     // Catch:{ RemoteException -> 0x04fe }
            java.lang.String r6 = "preloaded_fonts"
            r7 = 0
            int r3 = r3.getInt(r6, r7)     // Catch:{ RemoteException -> 0x04fe }
            if (r3 == 0) goto L_0x04fd
            android.app.LoadedApk r6 = r9.info     // Catch:{ RemoteException -> 0x04fe }
            android.content.res.Resources r6 = r6.getResources()     // Catch:{ RemoteException -> 0x04fe }
            r6.preloadFonts(r3)     // Catch:{ RemoteException -> 0x04fe }
        L_0x04fd:
            goto L_0x0504
        L_0x04fe:
            r0 = move-exception
            java.lang.RuntimeException r3 = r0.rethrowFromSystemServer()
            throw r3
        L_0x0504:
            long r6 = android.os.SystemClock.uptimeMillis()
            r29 = r12
            long r12 = r6 - r10
            int r3 = (int) r12
            r0 = 0
            if (r27 == 0) goto L_0x0514
            java.lang.String r0 = r27.getPackageName()
        L_0x0514:
            r12 = r0
            if (r26 == 0) goto L_0x0565
            boolean r0 = android.os.Process.isIsolated()
            if (r0 != 0) goto L_0x0565
            if (r12 == 0) goto L_0x0565
            java.lang.String r0 = r27.getPackageCodePath()     // Catch:{ Exception -> 0x053a }
            r13 = 47
            int r13 = r0.lastIndexOf(r13)     // Catch:{ Exception -> 0x053a }
            r30 = r5
            r5 = 0
            java.lang.String r5 = r0.substring(r5, r13)     // Catch:{ Exception -> 0x0538 }
            r0 = r5
            r30 = r0
            r31 = r6
            goto L_0x0555
        L_0x0538:
            r0 = move-exception
            goto L_0x053d
        L_0x053a:
            r0 = move-exception
            r30 = r5
        L_0x053d:
            java.lang.String r5 = "ActivityThread"
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            r13.<init>()
            r31 = r6
            java.lang.String r6 = "HeavyGameThread () : Exception_1 = "
            r13.append(r6)
            r13.append(r0)
            java.lang.String r6 = r13.toString()
            android.util.Slog.e(r5, r6)
        L_0x0555:
            r21 = 2
            r22 = 0
            r20 = r26
            r23 = r12
            r24 = r3
            r25 = r30
            r20.perfUXEngine_events(r21, r22, r23, r24, r25)
            goto L_0x0567
        L_0x0565:
            r31 = r6
        L_0x0567:
            return
        L_0x0568:
            r29 = r12
            java.lang.RuntimeException r5 = new java.lang.RuntimeException     // Catch:{ all -> 0x05bd }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x05bd }
            r6.<init>()     // Catch:{ all -> 0x05bd }
            java.lang.String r7 = "Unable to create application "
            r6.append(r7)     // Catch:{ all -> 0x05bd }
            java.lang.Class r7 = r4.getClass()     // Catch:{ all -> 0x05bd }
            java.lang.String r7 = r7.getName()     // Catch:{ all -> 0x05bd }
            r6.append(r7)     // Catch:{ all -> 0x05bd }
            java.lang.String r7 = ": "
            r6.append(r7)     // Catch:{ all -> 0x05bd }
            java.lang.String r7 = r0.toString()     // Catch:{ all -> 0x05bd }
            r6.append(r7)     // Catch:{ all -> 0x05bd }
            java.lang.String r6 = r6.toString()     // Catch:{ all -> 0x05bd }
            r5.<init>(r6, r0)     // Catch:{ all -> 0x05bd }
            throw r5     // Catch:{ all -> 0x05bd }
        L_0x0595:
            r0 = move-exception
            r29 = r12
            java.lang.RuntimeException r5 = new java.lang.RuntimeException     // Catch:{ all -> 0x05bd }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x05bd }
            r6.<init>()     // Catch:{ all -> 0x05bd }
            java.lang.String r7 = "Exception thrown in onCreate() of "
            r6.append(r7)     // Catch:{ all -> 0x05bd }
            android.content.ComponentName r7 = r9.instrumentationName     // Catch:{ all -> 0x05bd }
            r6.append(r7)     // Catch:{ all -> 0x05bd }
            java.lang.String r7 = ": "
            r6.append(r7)     // Catch:{ all -> 0x05bd }
            java.lang.String r7 = r0.toString()     // Catch:{ all -> 0x05bd }
            r6.append(r7)     // Catch:{ all -> 0x05bd }
            java.lang.String r6 = r6.toString()     // Catch:{ all -> 0x05bd }
            r5.<init>(r6, r0)     // Catch:{ all -> 0x05bd }
            throw r5     // Catch:{ all -> 0x05bd }
        L_0x05bd:
            r0 = move-exception
            goto L_0x05c2
        L_0x05bf:
            r0 = move-exception
            r29 = r12
        L_0x05c2:
            android.content.pm.ApplicationInfo r4 = r9.appInfo
            int r4 = r4.targetSdkVersion
            if (r4 < r3) goto L_0x05d2
            android.os.StrictMode$ThreadPolicy r3 = android.os.StrictMode.getThreadPolicy()
            boolean r3 = r3.equals(r2)
            if (r3 == 0) goto L_0x05d5
        L_0x05d2:
            android.os.StrictMode.setThreadPolicy(r1)
        L_0x05d5:
            throw r0
        L_0x05d6:
            r0 = move-exception
            r29 = r12
        L_0x05d9:
            monitor-exit(r2)     // Catch:{ all -> 0x05db }
            throw r0
        L_0x05db:
            r0 = move-exception
            goto L_0x05d9
        */
        throw new UnsupportedOperationException("Method not decompiled: android.app.ActivityThread.handleBindApplication(android.app.ActivityThread$AppBindData):void");
    }

    /* access modifiers changed from: package-private */
    public final void finishInstrumentation(int resultCode, Bundle results) {
        IActivityManager am = ActivityManager.getService();
        if (this.mProfiler.profileFile != null && this.mProfiler.handlingProfiling && this.mProfiler.profileFd == null) {
            Debug.stopMethodTracing();
        }
        try {
            am.finishInstrumentation(this.mAppThread, resultCode, results);
        } catch (RemoteException ex) {
            throw ex.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    private void installContentProviders(Context context, List<ProviderInfo> providers) {
        ArrayList<ContentProviderHolder> results = new ArrayList<>();
        for (ProviderInfo cpi : providers) {
            ContentProviderHolder cph = installProvider(context, (ContentProviderHolder) null, cpi, false, true, true);
            if (cph != null) {
                cph.noReleaseNeeded = true;
                results.add(cph);
            }
        }
        try {
            ActivityManager.getService().publishContentProviders(getApplicationThread(), results);
        } catch (RemoteException ex) {
            throw ex.rethrowFromSystemServer();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x002c, code lost:
        if (r1 != null) goto L_0x0045;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x002e, code lost:
        android.util.Slog.e(TAG, "Failed to find provider info for " + r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0044, code lost:
        return null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0059, code lost:
        return installProvider(r18, r1, r1.info, true, r1.noReleaseNeeded, r21).provider;
     */
    @android.annotation.UnsupportedAppUsage
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final android.content.IContentProvider acquireProvider(android.content.Context r18, java.lang.String r19, int r20, boolean r21) {
        /*
            r17 = this;
            r7 = r19
            android.content.IContentProvider r8 = r17.acquireExistingProvider(r18, r19, r20, r21)
            if (r8 == 0) goto L_0x0009
            return r8
        L_0x0009:
            r0 = 0
            r9 = r0
            r15 = r17
            r14 = r20
            java.lang.Object r10 = r15.getGetProviderLock(r7, r14)     // Catch:{ RemoteException -> 0x0060 }
            monitor-enter(r10)     // Catch:{ RemoteException -> 0x0060 }
            android.app.IActivityManager r1 = android.app.ActivityManager.getService()     // Catch:{ all -> 0x005d }
            android.app.ActivityThread$ApplicationThread r2 = r17.getApplicationThread()     // Catch:{ all -> 0x005d }
            java.lang.String r3 = r18.getOpPackageName()     // Catch:{ all -> 0x005d }
            r4 = r19
            r5 = r20
            r6 = r21
            android.app.ContentProviderHolder r1 = r1.getContentProvider(r2, r3, r4, r5, r6)     // Catch:{ all -> 0x005d }
            monitor-exit(r10)     // Catch:{ all -> 0x005a }
            if (r1 != 0) goto L_0x0045
            java.lang.String r2 = "ActivityThread"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "Failed to find provider info for "
            r3.append(r4)
            r3.append(r7)
            java.lang.String r3 = r3.toString()
            android.util.Slog.e(r2, r3)
            return r0
        L_0x0045:
            android.content.pm.ProviderInfo r13 = r1.info
            r0 = 1
            boolean r2 = r1.noReleaseNeeded
            r10 = r17
            r11 = r18
            r12 = r1
            r14 = r0
            r15 = r2
            r16 = r21
            android.app.ContentProviderHolder r0 = r10.installProvider(r11, r12, r13, r14, r15, r16)
            android.content.IContentProvider r1 = r0.provider
            return r1
        L_0x005a:
            r0 = move-exception
            r9 = r1
            goto L_0x005e
        L_0x005d:
            r0 = move-exception
        L_0x005e:
            monitor-exit(r10)     // Catch:{ all -> 0x005d }
            throw r0     // Catch:{ RemoteException -> 0x0060 }
        L_0x0060:
            r0 = move-exception
            java.lang.RuntimeException r1 = r0.rethrowFromSystemServer()
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.app.ActivityThread.acquireProvider(android.content.Context, java.lang.String, int, boolean):android.content.IContentProvider");
    }

    private Object getGetProviderLock(String auth, int userId) {
        Object lock;
        ProviderKey key = new ProviderKey(auth, userId);
        synchronized (this.mGetProviderLocks) {
            lock = this.mGetProviderLocks.get(key);
            if (lock == null) {
                lock = key;
                this.mGetProviderLocks.put(key, lock);
            }
        }
        return lock;
    }

    private final void incProviderRefLocked(ProviderRefCount prc, boolean stable) {
        int unstableDelta = 0;
        if (stable) {
            prc.stableCount++;
            if (prc.stableCount == 1) {
                if (prc.removePending) {
                    prc.removePending = false;
                    this.mH.removeMessages(131, prc);
                    unstableDelta = -1;
                }
                try {
                    ActivityManager.getService().refContentProvider(prc.holder.connection, 1, unstableDelta);
                } catch (RemoteException e) {
                }
            }
        } else {
            prc.unstableCount++;
            if (prc.unstableCount != 1) {
                return;
            }
            if (prc.removePending) {
                prc.removePending = false;
                this.mH.removeMessages(131, prc);
                return;
            }
            try {
                ActivityManager.getService().refContentProvider(prc.holder.connection, 0, 1);
            } catch (RemoteException e2) {
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0058, code lost:
        return r4;
     */
    @android.annotation.UnsupportedAppUsage
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final android.content.IContentProvider acquireExistingProvider(android.content.Context r10, java.lang.String r11, int r12, boolean r13) {
        /*
            r9 = this;
            android.util.ArrayMap<android.app.ActivityThread$ProviderKey, android.app.ActivityThread$ProviderClientRecord> r0 = r9.mProviderMap
            monitor-enter(r0)
            android.app.ActivityThread$ProviderKey r1 = new android.app.ActivityThread$ProviderKey     // Catch:{ all -> 0x0059 }
            r1.<init>(r11, r12)     // Catch:{ all -> 0x0059 }
            android.util.ArrayMap<android.app.ActivityThread$ProviderKey, android.app.ActivityThread$ProviderClientRecord> r2 = r9.mProviderMap     // Catch:{ all -> 0x0059 }
            java.lang.Object r2 = r2.get(r1)     // Catch:{ all -> 0x0059 }
            android.app.ActivityThread$ProviderClientRecord r2 = (android.app.ActivityThread.ProviderClientRecord) r2     // Catch:{ all -> 0x0059 }
            r3 = 0
            if (r2 != 0) goto L_0x0015
            monitor-exit(r0)     // Catch:{ all -> 0x0059 }
            return r3
        L_0x0015:
            android.content.IContentProvider r4 = r2.mProvider     // Catch:{ all -> 0x0059 }
            android.os.IBinder r5 = r4.asBinder()     // Catch:{ all -> 0x0059 }
            boolean r6 = r5.isBinderAlive()     // Catch:{ all -> 0x0059 }
            if (r6 != 0) goto L_0x004a
            java.lang.String r6 = "ActivityThread"
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ all -> 0x0059 }
            r7.<init>()     // Catch:{ all -> 0x0059 }
            java.lang.String r8 = "Acquiring provider "
            r7.append(r8)     // Catch:{ all -> 0x0059 }
            r7.append(r11)     // Catch:{ all -> 0x0059 }
            java.lang.String r8 = " for user "
            r7.append(r8)     // Catch:{ all -> 0x0059 }
            r7.append(r12)     // Catch:{ all -> 0x0059 }
            java.lang.String r8 = ": existing object's process dead"
            r7.append(r8)     // Catch:{ all -> 0x0059 }
            java.lang.String r7 = r7.toString()     // Catch:{ all -> 0x0059 }
            android.util.Log.i(r6, r7)     // Catch:{ all -> 0x0059 }
            r6 = 1
            r9.handleUnstableProviderDiedLocked(r5, r6)     // Catch:{ all -> 0x0059 }
            monitor-exit(r0)     // Catch:{ all -> 0x0059 }
            return r3
        L_0x004a:
            android.util.ArrayMap<android.os.IBinder, android.app.ActivityThread$ProviderRefCount> r3 = r9.mProviderRefCountMap     // Catch:{ all -> 0x0059 }
            java.lang.Object r3 = r3.get(r5)     // Catch:{ all -> 0x0059 }
            android.app.ActivityThread$ProviderRefCount r3 = (android.app.ActivityThread.ProviderRefCount) r3     // Catch:{ all -> 0x0059 }
            if (r3 == 0) goto L_0x0057
            r9.incProviderRefLocked(r3, r13)     // Catch:{ all -> 0x0059 }
        L_0x0057:
            monitor-exit(r0)     // Catch:{ all -> 0x0059 }
            return r4
        L_0x0059:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0059 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.app.ActivityThread.acquireExistingProvider(android.content.Context, java.lang.String, int, boolean):android.content.IContentProvider");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:52:0x00a1, code lost:
        return true;
     */
    @android.annotation.UnsupportedAppUsage
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean releaseProvider(android.content.IContentProvider r10, boolean r11) {
        /*
            r9 = this;
            r0 = 0
            if (r10 != 0) goto L_0x0004
            return r0
        L_0x0004:
            android.os.IBinder r1 = r10.asBinder()
            android.util.ArrayMap<android.app.ActivityThread$ProviderKey, android.app.ActivityThread$ProviderClientRecord> r2 = r9.mProviderMap
            monitor-enter(r2)
            android.util.ArrayMap<android.os.IBinder, android.app.ActivityThread$ProviderRefCount> r3 = r9.mProviderRefCountMap     // Catch:{ all -> 0x00a2 }
            java.lang.Object r3 = r3.get(r1)     // Catch:{ all -> 0x00a2 }
            android.app.ActivityThread$ProviderRefCount r3 = (android.app.ActivityThread.ProviderRefCount) r3     // Catch:{ all -> 0x00a2 }
            if (r3 != 0) goto L_0x0017
            monitor-exit(r2)     // Catch:{ all -> 0x00a2 }
            return r0
        L_0x0017:
            r4 = 0
            r5 = -1
            r6 = 1
            if (r11 == 0) goto L_0x0046
            int r7 = r3.stableCount     // Catch:{ all -> 0x00a2 }
            if (r7 != 0) goto L_0x0022
            monitor-exit(r2)     // Catch:{ all -> 0x00a2 }
            return r0
        L_0x0022:
            int r7 = r3.stableCount     // Catch:{ all -> 0x00a2 }
            int r7 = r7 - r6
            r3.stableCount = r7     // Catch:{ all -> 0x00a2 }
            int r7 = r3.stableCount     // Catch:{ all -> 0x00a2 }
            if (r7 != 0) goto L_0x006c
            int r7 = r3.unstableCount     // Catch:{ all -> 0x00a2 }
            if (r7 != 0) goto L_0x0031
            r7 = r6
            goto L_0x0032
        L_0x0031:
            r7 = r0
        L_0x0032:
            r4 = r7
            android.app.IActivityManager r7 = android.app.ActivityManager.getService()     // Catch:{ RemoteException -> 0x0044 }
            android.app.ContentProviderHolder r8 = r3.holder     // Catch:{ RemoteException -> 0x0044 }
            android.os.IBinder r8 = r8.connection     // Catch:{ RemoteException -> 0x0044 }
            if (r4 == 0) goto L_0x003f
            r0 = r6
            goto L_0x0040
        L_0x003f:
        L_0x0040:
            r7.refContentProvider(r8, r5, r0)     // Catch:{ RemoteException -> 0x0044 }
            goto L_0x0045
        L_0x0044:
            r0 = move-exception
        L_0x0045:
            goto L_0x006c
        L_0x0046:
            int r7 = r3.unstableCount     // Catch:{ all -> 0x00a2 }
            if (r7 != 0) goto L_0x004c
            monitor-exit(r2)     // Catch:{ all -> 0x00a2 }
            return r0
        L_0x004c:
            int r7 = r3.unstableCount     // Catch:{ all -> 0x00a2 }
            int r7 = r7 - r6
            r3.unstableCount = r7     // Catch:{ all -> 0x00a2 }
            int r7 = r3.unstableCount     // Catch:{ all -> 0x00a2 }
            if (r7 != 0) goto L_0x006c
            int r7 = r3.stableCount     // Catch:{ all -> 0x00a2 }
            if (r7 != 0) goto L_0x005b
            r7 = r6
            goto L_0x005c
        L_0x005b:
            r7 = r0
        L_0x005c:
            r4 = r7
            if (r4 != 0) goto L_0x006c
            android.app.IActivityManager r7 = android.app.ActivityManager.getService()     // Catch:{ RemoteException -> 0x006b }
            android.app.ContentProviderHolder r8 = r3.holder     // Catch:{ RemoteException -> 0x006b }
            android.os.IBinder r8 = r8.connection     // Catch:{ RemoteException -> 0x006b }
            r7.refContentProvider(r8, r0, r5)     // Catch:{ RemoteException -> 0x006b }
            goto L_0x006c
        L_0x006b:
            r0 = move-exception
        L_0x006c:
            if (r4 == 0) goto L_0x00a0
            boolean r0 = r3.removePending     // Catch:{ all -> 0x00a2 }
            if (r0 != 0) goto L_0x0084
            r3.removePending = r6     // Catch:{ all -> 0x00a2 }
            android.app.ActivityThread$H r0 = r9.mH     // Catch:{ all -> 0x00a2 }
            r5 = 131(0x83, float:1.84E-43)
            android.os.Message r0 = r0.obtainMessage(r5, r3)     // Catch:{ all -> 0x00a2 }
            android.app.ActivityThread$H r5 = r9.mH     // Catch:{ all -> 0x00a2 }
            r7 = 1000(0x3e8, double:4.94E-321)
            r5.sendMessageDelayed(r0, r7)     // Catch:{ all -> 0x00a2 }
            goto L_0x00a0
        L_0x0084:
            java.lang.String r0 = "ActivityThread"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x00a2 }
            r5.<init>()     // Catch:{ all -> 0x00a2 }
            java.lang.String r7 = "Duplicate remove pending of provider "
            r5.append(r7)     // Catch:{ all -> 0x00a2 }
            android.app.ContentProviderHolder r7 = r3.holder     // Catch:{ all -> 0x00a2 }
            android.content.pm.ProviderInfo r7 = r7.info     // Catch:{ all -> 0x00a2 }
            java.lang.String r7 = r7.name     // Catch:{ all -> 0x00a2 }
            r5.append(r7)     // Catch:{ all -> 0x00a2 }
            java.lang.String r5 = r5.toString()     // Catch:{ all -> 0x00a2 }
            android.util.Slog.w((java.lang.String) r0, (java.lang.String) r5)     // Catch:{ all -> 0x00a2 }
        L_0x00a0:
            monitor-exit(r2)     // Catch:{ all -> 0x00a2 }
            return r6
        L_0x00a2:
            r0 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x00a2 }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.app.ActivityThread.releaseProvider(android.content.IContentProvider, boolean):boolean");
    }

    /* access modifiers changed from: package-private */
    public final void completeRemoveProvider(ProviderRefCount prc) {
        synchronized (this.mProviderMap) {
            if (prc.removePending) {
                prc.removePending = false;
                IBinder jBinder = prc.holder.provider.asBinder();
                if (this.mProviderRefCountMap.get(jBinder) == prc) {
                    this.mProviderRefCountMap.remove(jBinder);
                }
                for (int i = this.mProviderMap.size() - 1; i >= 0; i--) {
                    if (this.mProviderMap.valueAt(i).mProvider.asBinder() == jBinder) {
                        this.mProviderMap.removeAt(i);
                    }
                }
                try {
                    ActivityManager.getService().removeContentProvider(prc.holder.connection, false);
                } catch (RemoteException e) {
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    @UnsupportedAppUsage
    public final void handleUnstableProviderDied(IBinder provider, boolean fromClient) {
        synchronized (this.mProviderMap) {
            handleUnstableProviderDiedLocked(provider, fromClient);
        }
    }

    /* access modifiers changed from: package-private */
    public final void handleUnstableProviderDiedLocked(IBinder provider, boolean fromClient) {
        ProviderRefCount prc = this.mProviderRefCountMap.get(provider);
        if (prc != null) {
            this.mProviderRefCountMap.remove(provider);
            for (int i = this.mProviderMap.size() - 1; i >= 0; i--) {
                ProviderClientRecord pr = this.mProviderMap.valueAt(i);
                if (pr != null && pr.mProvider.asBinder() == provider) {
                    Slog.i(TAG, "Removing dead content provider:" + pr.mProvider.toString());
                    this.mProviderMap.removeAt(i);
                }
            }
            if (fromClient) {
                try {
                    ActivityManager.getService().unstableProviderDied(prc.holder.connection);
                } catch (RemoteException e) {
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public final void appNotRespondingViaProvider(IBinder provider) {
        synchronized (this.mProviderMap) {
            ProviderRefCount prc = this.mProviderRefCountMap.get(provider);
            if (prc != null) {
                try {
                    ActivityManager.getService().appNotRespondingViaProvider(prc.holder.connection);
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
        }
    }

    private ProviderClientRecord installProviderAuthoritiesLocked(IContentProvider provider, ContentProvider localProvider, ContentProviderHolder holder) {
        String[] auths = holder.info.authority.split(";");
        int userId = UserHandle.getUserId(holder.info.applicationInfo.uid);
        if (provider != null) {
            for (String auth : auths) {
                char c = 65535;
                switch (auth.hashCode()) {
                    case -845193793:
                        if (auth.equals(ContactsContract.AUTHORITY)) {
                            c = 0;
                            break;
                        }
                        break;
                    case -456066902:
                        if (auth.equals(CalendarContract.AUTHORITY)) {
                            c = 4;
                            break;
                        }
                        break;
                    case -172298781:
                        if (auth.equals(CallLog.AUTHORITY)) {
                            c = 1;
                            break;
                        }
                        break;
                    case 63943420:
                        if (auth.equals(CallLog.SHADOW_AUTHORITY)) {
                            c = 2;
                            break;
                        }
                        break;
                    case 783201304:
                        if (auth.equals(DeviceConfig.NAMESPACE_TELEPHONY)) {
                            c = 6;
                            break;
                        }
                        break;
                    case 1312704747:
                        if (auth.equals(Downloads.Impl.AUTHORITY)) {
                            c = 5;
                            break;
                        }
                        break;
                    case 1995645513:
                        if (auth.equals(BlockedNumberContract.AUTHORITY)) {
                            c = 3;
                            break;
                        }
                        break;
                }
                switch (c) {
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                        Binder.allowBlocking(provider.asBinder());
                        break;
                }
            }
        }
        ProviderClientRecord pcr = new ProviderClientRecord(auths, provider, localProvider, holder);
        for (String auth2 : auths) {
            ProviderKey key = new ProviderKey(auth2, userId);
            if (this.mProviderMap.get(key) != null) {
                Slog.w(TAG, "Content provider " + pcr.mHolder.info.name + " already published as " + auth2);
            } else {
                this.mProviderMap.put(key, pcr);
            }
        }
        return pcr;
    }

    /* JADX WARNING: Removed duplicated region for block: B:28:0x0072  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0095  */
    @android.annotation.UnsupportedAppUsage
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private android.app.ContentProviderHolder installProvider(android.content.Context r15, android.app.ContentProviderHolder r16, android.content.pm.ProviderInfo r17, boolean r18, boolean r19, boolean r20) {
        /*
            r14 = this;
            r1 = r14
            r2 = r16
            r3 = r17
            r4 = r20
            r5 = 0
            r6 = 1
            if (r2 == 0) goto L_0x0017
            android.content.IContentProvider r0 = r2.provider
            if (r0 != 0) goto L_0x0010
            goto L_0x0017
        L_0x0010:
            android.content.IContentProvider r0 = r2.provider
            r9 = r15
            r12 = r0
        L_0x0014:
            r10 = r5
            goto L_0x00f9
        L_0x0017:
            if (r18 == 0) goto L_0x003b
            java.lang.String r0 = "ActivityThread"
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r8 = "Loading provider "
            r7.append(r8)
            java.lang.String r8 = r3.authority
            r7.append(r8)
            java.lang.String r8 = ": "
            r7.append(r8)
            java.lang.String r8 = r3.name
            r7.append(r8)
            java.lang.String r7 = r7.toString()
            android.util.Slog.d(r0, r7)
        L_0x003b:
            r7 = 0
            android.content.pm.ApplicationInfo r8 = r3.applicationInfo
            java.lang.String r0 = r15.getPackageName()
            java.lang.String r9 = r8.packageName
            boolean r0 = r0.equals(r9)
            if (r0 == 0) goto L_0x004d
            r7 = r15
        L_0x004b:
            r9 = r15
            goto L_0x006f
        L_0x004d:
            android.app.Application r0 = r1.mInitialApplication
            if (r0 == 0) goto L_0x0062
            android.app.Application r0 = r1.mInitialApplication
            java.lang.String r0 = r0.getPackageName()
            java.lang.String r9 = r8.packageName
            boolean r0 = r0.equals(r9)
            if (r0 == 0) goto L_0x0062
            android.app.Application r7 = r1.mInitialApplication
            goto L_0x004b
        L_0x0062:
            java.lang.String r0 = r8.packageName     // Catch:{ NameNotFoundException -> 0x006d }
            r9 = r15
            android.content.Context r0 = r15.createPackageContext(r0, r6)     // Catch:{ NameNotFoundException -> 0x006b }
            r7 = r0
            goto L_0x006f
        L_0x006b:
            r0 = move-exception
            goto L_0x006f
        L_0x006d:
            r0 = move-exception
            r9 = r15
        L_0x006f:
            r10 = 0
            if (r7 != 0) goto L_0x0095
            java.lang.String r0 = "ActivityThread"
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r11 = "Unable to get context for package "
            r6.append(r11)
            java.lang.String r11 = r8.packageName
            r6.append(r11)
            java.lang.String r11 = " while loading content provider "
            r6.append(r11)
            java.lang.String r11 = r3.name
            r6.append(r11)
            java.lang.String r6 = r6.toString()
            android.util.Slog.w((java.lang.String) r0, (java.lang.String) r6)
            return r10
        L_0x0095:
            java.lang.String r0 = r3.splitName
            if (r0 == 0) goto L_0x00a8
            java.lang.String r0 = r3.splitName     // Catch:{ NameNotFoundException -> 0x00a1 }
            android.content.Context r0 = r7.createContextForSplit(r0)     // Catch:{ NameNotFoundException -> 0x00a1 }
            r7 = r0
            goto L_0x00a8
        L_0x00a1:
            r0 = move-exception
            java.lang.RuntimeException r6 = new java.lang.RuntimeException
            r6.<init>(r0)
            throw r6
        L_0x00a8:
            java.lang.ClassLoader r0 = r7.getClassLoader()     // Catch:{ Exception -> 0x017f }
            java.lang.String r11 = r8.packageName     // Catch:{ Exception -> 0x017f }
            android.app.LoadedApk r11 = r14.peekPackageInfo(r11, r6)     // Catch:{ Exception -> 0x017f }
            if (r11 != 0) goto L_0x00bb
            android.app.ContextImpl r12 = r14.getSystemContext()     // Catch:{ Exception -> 0x017f }
            android.app.LoadedApk r12 = r12.mPackageInfo     // Catch:{ Exception -> 0x017f }
            r11 = r12
        L_0x00bb:
            android.app.AppComponentFactory r12 = r11.getAppFactory()     // Catch:{ Exception -> 0x017f }
            java.lang.String r13 = r3.name     // Catch:{ Exception -> 0x017f }
            android.content.ContentProvider r12 = r12.instantiateProvider(r0, r13)     // Catch:{ Exception -> 0x017f }
            r5 = r12
            android.content.IContentProvider r12 = r5.getIContentProvider()     // Catch:{ Exception -> 0x017f }
            if (r12 != 0) goto L_0x00f2
            java.lang.String r6 = "ActivityThread"
            java.lang.StringBuilder r13 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x017f }
            r13.<init>()     // Catch:{ Exception -> 0x017f }
            java.lang.String r10 = "Failed to instantiate class "
            r13.append(r10)     // Catch:{ Exception -> 0x017f }
            java.lang.String r10 = r3.name     // Catch:{ Exception -> 0x017f }
            r13.append(r10)     // Catch:{ Exception -> 0x017f }
            java.lang.String r10 = " from sourceDir "
            r13.append(r10)     // Catch:{ Exception -> 0x017f }
            android.content.pm.ApplicationInfo r10 = r3.applicationInfo     // Catch:{ Exception -> 0x017f }
            java.lang.String r10 = r10.sourceDir     // Catch:{ Exception -> 0x017f }
            r13.append(r10)     // Catch:{ Exception -> 0x017f }
            java.lang.String r10 = r13.toString()     // Catch:{ Exception -> 0x017f }
            android.util.Slog.e(r6, r10)     // Catch:{ Exception -> 0x017f }
            r6 = 0
            return r6
        L_0x00f2:
            r5.attachInfo(r7, r3)     // Catch:{ Exception -> 0x017f }
            goto L_0x0014
        L_0x00f9:
            r5 = r12
            android.util.ArrayMap<android.app.ActivityThread$ProviderKey, android.app.ActivityThread$ProviderClientRecord> r11 = r1.mProviderMap
            monitor-enter(r11)
            android.os.IBinder r0 = r5.asBinder()     // Catch:{ all -> 0x017c }
            r7 = r0
            if (r10 == 0) goto L_0x0138
            android.content.ComponentName r0 = new android.content.ComponentName     // Catch:{ all -> 0x017c }
            java.lang.String r8 = r3.packageName     // Catch:{ all -> 0x017c }
            java.lang.String r12 = r3.name     // Catch:{ all -> 0x017c }
            r0.<init>((java.lang.String) r8, (java.lang.String) r12)     // Catch:{ all -> 0x017c }
            android.util.ArrayMap<android.content.ComponentName, android.app.ActivityThread$ProviderClientRecord> r8 = r1.mLocalProvidersByName     // Catch:{ all -> 0x017c }
            java.lang.Object r8 = r8.get(r0)     // Catch:{ all -> 0x017c }
            android.app.ActivityThread$ProviderClientRecord r8 = (android.app.ActivityThread.ProviderClientRecord) r8     // Catch:{ all -> 0x017c }
            if (r8 == 0) goto L_0x011b
            android.content.IContentProvider r6 = r8.mProvider     // Catch:{ all -> 0x017c }
            r5 = r6
            goto L_0x0134
        L_0x011b:
            android.app.ContentProviderHolder r12 = new android.app.ContentProviderHolder     // Catch:{ all -> 0x017c }
            r12.<init>((android.content.pm.ProviderInfo) r3)     // Catch:{ all -> 0x017c }
            r2 = r12
            r2.provider = r5     // Catch:{ all -> 0x017c }
            r2.noReleaseNeeded = r6     // Catch:{ all -> 0x017c }
            android.app.ActivityThread$ProviderClientRecord r6 = r14.installProviderAuthoritiesLocked(r5, r10, r2)     // Catch:{ all -> 0x017c }
            r8 = r6
            android.util.ArrayMap<android.os.IBinder, android.app.ActivityThread$ProviderClientRecord> r6 = r1.mLocalProviders     // Catch:{ all -> 0x017c }
            r6.put(r7, r8)     // Catch:{ all -> 0x017c }
            android.util.ArrayMap<android.content.ComponentName, android.app.ActivityThread$ProviderClientRecord> r6 = r1.mLocalProvidersByName     // Catch:{ all -> 0x017c }
            r6.put(r0, r8)     // Catch:{ all -> 0x017c }
        L_0x0134:
            android.app.ContentProviderHolder r6 = r8.mHolder     // Catch:{ all -> 0x017c }
            r0 = r6
            goto L_0x017a
        L_0x0138:
            android.util.ArrayMap<android.os.IBinder, android.app.ActivityThread$ProviderRefCount> r0 = r1.mProviderRefCountMap     // Catch:{ all -> 0x017c }
            java.lang.Object r0 = r0.get(r7)     // Catch:{ all -> 0x017c }
            android.app.ActivityThread$ProviderRefCount r0 = (android.app.ActivityThread.ProviderRefCount) r0     // Catch:{ all -> 0x017c }
            r8 = r0
            if (r8 == 0) goto L_0x0154
            if (r19 != 0) goto L_0x0178
            r14.incProviderRefLocked(r8, r4)     // Catch:{ all -> 0x017c }
            android.app.IActivityManager r0 = android.app.ActivityManager.getService()     // Catch:{ RemoteException -> 0x0152 }
            android.os.IBinder r6 = r2.connection     // Catch:{ RemoteException -> 0x0152 }
            r0.removeContentProvider(r6, r4)     // Catch:{ RemoteException -> 0x0152 }
            goto L_0x0153
        L_0x0152:
            r0 = move-exception
        L_0x0153:
            goto L_0x0178
        L_0x0154:
            android.app.ActivityThread$ProviderClientRecord r0 = r14.installProviderAuthoritiesLocked(r5, r10, r2)     // Catch:{ all -> 0x017c }
            if (r19 == 0) goto L_0x0163
            android.app.ActivityThread$ProviderRefCount r6 = new android.app.ActivityThread$ProviderRefCount     // Catch:{ all -> 0x017c }
            r12 = 1000(0x3e8, float:1.401E-42)
            r6.<init>(r2, r0, r12, r12)     // Catch:{ all -> 0x017c }
        L_0x0161:
            r8 = r6
            goto L_0x0173
        L_0x0163:
            r12 = 0
            if (r4 == 0) goto L_0x016c
            android.app.ActivityThread$ProviderRefCount r13 = new android.app.ActivityThread$ProviderRefCount     // Catch:{ all -> 0x017c }
            r13.<init>(r2, r0, r6, r12)     // Catch:{ all -> 0x017c }
            goto L_0x0171
        L_0x016c:
            android.app.ActivityThread$ProviderRefCount r13 = new android.app.ActivityThread$ProviderRefCount     // Catch:{ all -> 0x017c }
            r13.<init>(r2, r0, r12, r6)     // Catch:{ all -> 0x017c }
        L_0x0171:
            r6 = r13
            goto L_0x0161
        L_0x0173:
            android.util.ArrayMap<android.os.IBinder, android.app.ActivityThread$ProviderRefCount> r6 = r1.mProviderRefCountMap     // Catch:{ all -> 0x017c }
            r6.put(r7, r8)     // Catch:{ all -> 0x017c }
        L_0x0178:
            android.app.ContentProviderHolder r0 = r8.holder     // Catch:{ all -> 0x017c }
        L_0x017a:
            monitor-exit(r11)     // Catch:{ all -> 0x017c }
            return r0
        L_0x017c:
            r0 = move-exception
            monitor-exit(r11)     // Catch:{ all -> 0x017c }
            throw r0
        L_0x017f:
            r0 = move-exception
            android.app.Instrumentation r6 = r1.mInstrumentation
            r10 = 0
            boolean r6 = r6.onException(r10, r0)
            if (r6 == 0) goto L_0x018a
            return r10
        L_0x018a:
            java.lang.RuntimeException r6 = new java.lang.RuntimeException
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            java.lang.String r11 = "Unable to get provider "
            r10.append(r11)
            java.lang.String r11 = r3.name
            r10.append(r11)
            java.lang.String r11 = ": "
            r10.append(r11)
            java.lang.String r11 = r0.toString()
            r10.append(r11)
            java.lang.String r10 = r10.toString()
            r6.<init>(r10, r0)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: android.app.ActivityThread.installProvider(android.content.Context, android.app.ContentProviderHolder, android.content.pm.ProviderInfo, boolean, boolean, boolean):android.app.ContentProviderHolder");
    }

    /* access modifiers changed from: private */
    public void handleRunIsolatedEntryPoint(String entryPoint, String[] entryPointArgs) {
        try {
            Class.forName(entryPoint).getMethod("main", new Class[]{String[].class}).invoke((Object) null, new Object[]{entryPointArgs});
            System.exit(0);
        } catch (ReflectiveOperationException e) {
            throw new AndroidRuntimeException("runIsolatedEntryPoint failed", e);
        }
    }

    @UnsupportedAppUsage
    private void attach(boolean system, long startSeq) {
        sCurrentActivityThread = this;
        this.mSystemThread = system;
        if (!system) {
            DdmHandleAppName.setAppName("<pre-initialized>", UserHandle.myUserId());
            RuntimeInit.setApplicationObject(this.mAppThread.asBinder());
            try {
                ActivityManager.getService().attachApplication(this.mAppThread, startSeq);
                BinderInternal.addGcWatcher(new Runnable() {
                    public void run() {
                        if (ActivityThread.this.mSomeActivitiesChanged) {
                            Runtime runtime = Runtime.getRuntime();
                            if (runtime.totalMemory() - runtime.freeMemory() > (3 * runtime.maxMemory()) / 4) {
                                ActivityThread.this.mSomeActivitiesChanged = false;
                                try {
                                    ActivityTaskManager.getService().releaseSomeActivities(ActivityThread.this.mAppThread);
                                } catch (RemoteException e) {
                                    throw e.rethrowFromSystemServer();
                                }
                            }
                        }
                    }
                });
            } catch (RemoteException ex) {
                throw ex.rethrowFromSystemServer();
            }
        } else {
            DdmHandleAppName.setAppName("system_process", UserHandle.myUserId());
            try {
                this.mInstrumentation = new Instrumentation();
                this.mInstrumentation.basicInit(this);
                this.mInitialApplication = ContextImpl.createAppContext(this, getSystemContext().mPackageInfo).mPackageInfo.makeApplication(true, (Instrumentation) null);
                this.mInitialApplication.onCreate();
            } catch (Exception e) {
                throw new RuntimeException("Unable to instantiate Application():" + e.toString(), e);
            }
        }
        ViewRootImpl.addConfigCallback(new ViewRootImpl.ConfigChangedCallback() {
            public final void onConfigurationChanged(Configuration configuration) {
                ActivityThread.lambda$attach$1(ActivityThread.this, configuration);
            }
        });
    }

    public static /* synthetic */ void lambda$attach$1(ActivityThread activityThread, Configuration globalConfig) {
        synchronized (activityThread.mResourcesManager) {
            if (activityThread.mResourcesManager.applyConfigurationToResourcesLocked(globalConfig, (CompatibilityInfo) null)) {
                activityThread.updateLocaleListFromAppContext(activityThread.mInitialApplication.getApplicationContext(), activityThread.mResourcesManager.getConfiguration().getLocales());
                if (activityThread.mPendingConfiguration == null || activityThread.mPendingConfiguration.isOtherSeqNewer(globalConfig)) {
                    activityThread.mPendingConfiguration = globalConfig;
                    activityThread.sendMessage(118, globalConfig);
                }
            }
        }
    }

    @UnsupportedAppUsage
    public static ActivityThread systemMain() {
        if (!ActivityManager.isHighEndGfx()) {
            ThreadedRenderer.disable(true);
        } else {
            ThreadedRenderer.enableForegroundTrimming();
        }
        ActivityThread thread = new ActivityThread();
        thread.attach(true, 0);
        return thread;
    }

    public static void updateHttpProxy(Context context) {
        Proxy.setHttpProxySystemProperty(ConnectivityManager.from(context).getDefaultProxy());
    }

    @UnsupportedAppUsage
    public final void installSystemProviders(List<ProviderInfo> providers) {
        if (providers != null) {
            installContentProviders(this.mInitialApplication, providers);
        }
    }

    public int getIntCoreSetting(String key, int defaultValue) {
        synchronized (this.mResourcesManager) {
            if (this.mCoreSettings == null) {
                return defaultValue;
            }
            int i = this.mCoreSettings.getInt(key, defaultValue);
            return i;
        }
    }

    private static class AndroidOs extends ForwardingOs {
        public static void install() {
            Os def;
            if (ContentResolver.DEPRECATE_DATA_COLUMNS) {
                do {
                    def = Os.getDefault();
                } while (!Os.compareAndSetDefault(def, new AndroidOs(def)));
            }
        }

        private AndroidOs(Os os) {
            super(os);
        }

        private FileDescriptor openDeprecatedDataPath(String path, int mode) throws ErrnoException {
            Uri uri = ContentResolver.translateDeprecatedDataPath(path);
            Log.v(ActivityThread.TAG, "Redirecting " + path + " to " + uri);
            ContentResolver cr = ActivityThread.currentActivityThread().getApplication().getContentResolver();
            try {
                FileDescriptor fd = new FileDescriptor();
                fd.setInt$(cr.openFileDescriptor(uri, FileUtils.translateModePosixToString(mode)).detachFd());
                return fd;
            } catch (SecurityException e) {
                throw new ErrnoException(e.getMessage(), OsConstants.EACCES);
            } catch (FileNotFoundException e2) {
                throw new ErrnoException(e2.getMessage(), OsConstants.ENOENT);
            }
        }

        private void deleteDeprecatedDataPath(String path) throws ErrnoException {
            Uri uri = ContentResolver.translateDeprecatedDataPath(path);
            Log.v(ActivityThread.TAG, "Redirecting " + path + " to " + uri);
            try {
                if (ActivityThread.currentActivityThread().getApplication().getContentResolver().delete(uri, (String) null, (String[]) null) == 0) {
                    throw new FileNotFoundException();
                }
            } catch (SecurityException e) {
                throw new ErrnoException(e.getMessage(), OsConstants.EACCES);
            } catch (FileNotFoundException e2) {
                throw new ErrnoException(e2.getMessage(), OsConstants.ENOENT);
            }
        }

        public boolean access(String path, int mode) throws ErrnoException {
            if (path == null || !path.startsWith(ContentResolver.DEPRECATE_DATA_PREFIX)) {
                return ActivityThread.super.access(path, mode);
            }
            IoUtils.closeQuietly(openDeprecatedDataPath(path, FileUtils.translateModeAccessToPosix(mode)));
            return true;
        }

        public FileDescriptor open(String path, int flags, int mode) throws ErrnoException {
            if (path == null || !path.startsWith(ContentResolver.DEPRECATE_DATA_PREFIX)) {
                return ActivityThread.super.open(path, flags, mode);
            }
            return openDeprecatedDataPath(path, mode);
        }

        public StructStat stat(String path) throws ErrnoException {
            if (path == null || !path.startsWith(ContentResolver.DEPRECATE_DATA_PREFIX)) {
                return ActivityThread.super.stat(path);
            }
            FileDescriptor fd = openDeprecatedDataPath(path, OsConstants.O_RDONLY);
            try {
                return android.system.Os.fstat(fd);
            } finally {
                IoUtils.closeQuietly(fd);
            }
        }

        public void unlink(String path) throws ErrnoException {
            if (path == null || !path.startsWith(ContentResolver.DEPRECATE_DATA_PREFIX)) {
                ActivityThread.super.unlink(path);
            } else {
                deleteDeprecatedDataPath(path);
            }
        }

        public void remove(String path) throws ErrnoException {
            if (path == null || !path.startsWith(ContentResolver.DEPRECATE_DATA_PREFIX)) {
                ActivityThread.super.remove(path);
            } else {
                deleteDeprecatedDataPath(path);
            }
        }

        public void rename(String oldPath, String newPath) throws ErrnoException {
            try {
                ActivityThread.super.rename(oldPath, newPath);
            } catch (ErrnoException e) {
                if (e.errno == OsConstants.EXDEV) {
                    Log.v(ActivityThread.TAG, "Recovering failed rename " + oldPath + " to " + newPath);
                    try {
                        Files.move(new File(oldPath).toPath(), new File(newPath).toPath(), new CopyOption[0]);
                    } catch (IOException e2) {
                        throw e;
                    }
                } else {
                    throw e;
                }
            }
        }
    }

    public static void main(String[] args) {
        Trace.traceBegin(64, "ActivityThreadMain");
        AndroidOs.install();
        CloseGuard.setEnabled(false);
        Environment.initForCurrentUser();
        TrustedCertificateStore.setDefaultUserDirectory(Environment.getUserConfigDirectory(UserHandle.myUserId()));
        Process.setArgV0("<pre-initialized>");
        Looper.prepareMainLooper();
        long startSeq = 0;
        if (args != null) {
            for (int i = args.length - 1; i >= 0; i--) {
                if (args[i] != null && args[i].startsWith(PROC_START_SEQ_IDENT)) {
                    startSeq = Long.parseLong(args[i].substring(PROC_START_SEQ_IDENT.length()));
                }
            }
        }
        ActivityThread thread = new ActivityThread();
        thread.attach(false, startSeq);
        if (sMainThreadHandler == null) {
            sMainThreadHandler = thread.getHandler();
        }
        Trace.traceEnd(64);
        Looper.loop();
        throw new RuntimeException("Main thread loop unexpectedly exited");
    }

    /* access modifiers changed from: private */
    public void purgePendingResources() {
        Trace.traceBegin(64, "purgePendingResources");
        nPurgePendingResources();
        Trace.traceEnd(64);
    }
}
