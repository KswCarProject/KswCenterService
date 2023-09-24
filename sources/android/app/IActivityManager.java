package android.app;

import android.annotation.UnsupportedAppUsage;
import android.app.ActivityManager;
import android.app.ApplicationErrorReport;
import android.app.IActivityController;
import android.app.IApplicationThread;
import android.app.IAssistDataReceiver;
import android.app.IInstrumentationWatcher;
import android.app.IProcessObserver;
import android.app.IServiceConnection;
import android.app.IStopUserCallback;
import android.app.ITaskStackListener;
import android.app.IUiAutomationConnection;
import android.app.IUidObserver;
import android.app.IUserSwitchObserver;
import android.content.ComponentName;
import android.content.IIntentReceiver;
import android.content.IIntentSender;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.p002pm.ApplicationInfo;
import android.content.p002pm.IPackageDataObserver;
import android.content.p002pm.ParceledListSlice;
import android.content.p002pm.UserInfo;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.net.Uri;
import android.p007os.Binder;
import android.p007os.Bundle;
import android.p007os.Debug;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.IProgressListener;
import android.p007os.Parcel;
import android.p007os.ParcelFileDescriptor;
import android.p007os.RemoteCallback;
import android.p007os.RemoteException;
import android.p007os.StrictMode;
import android.p007os.WorkSource;
import android.text.TextUtils;
import android.view.IRecentsAnimationRunner;
import com.android.internal.p016os.IResultReceiver;
import java.util.List;

/* loaded from: classes.dex */
public interface IActivityManager extends IInterface {
    void addInstrumentationResults(IApplicationThread iApplicationThread, Bundle bundle) throws RemoteException;

    void addPackageDependency(String str) throws RemoteException;

    void appNotRespondingViaProvider(IBinder iBinder) throws RemoteException;

    void attachApplication(IApplicationThread iApplicationThread, long j) throws RemoteException;

    void backgroundWhitelistUid(int i) throws RemoteException;

    void backupAgentCreated(String str, IBinder iBinder, int i) throws RemoteException;

    boolean bindBackupAgent(String str, int i, int i2) throws RemoteException;

    int bindIsolatedService(IApplicationThread iApplicationThread, IBinder iBinder, Intent intent, String str, IServiceConnection iServiceConnection, int i, String str2, String str3, int i2) throws RemoteException;

    @UnsupportedAppUsage
    int bindService(IApplicationThread iApplicationThread, IBinder iBinder, Intent intent, String str, IServiceConnection iServiceConnection, int i, String str2, int i2) throws RemoteException;

    void bootAnimationComplete() throws RemoteException;

    @UnsupportedAppUsage
    int broadcastIntent(IApplicationThread iApplicationThread, Intent intent, String str, IIntentReceiver iIntentReceiver, int i, String str2, Bundle bundle, String[] strArr, int i2, Bundle bundle2, boolean z, boolean z2, int i3) throws RemoteException;

    void cancelIntentSender(IIntentSender iIntentSender) throws RemoteException;

    @UnsupportedAppUsage
    void cancelRecentsAnimation(boolean z) throws RemoteException;

    @UnsupportedAppUsage
    void cancelTaskWindowTransition(int i) throws RemoteException;

    @UnsupportedAppUsage
    int checkPermission(String str, int i, int i2) throws RemoteException;

    int checkPermissionWithToken(String str, int i, int i2, IBinder iBinder) throws RemoteException;

    int checkUriPermission(Uri uri, int i, int i2, int i3, int i4, IBinder iBinder) throws RemoteException;

    boolean clearApplicationUserData(String str, boolean z, IPackageDataObserver iPackageDataObserver, int i) throws RemoteException;

    @UnsupportedAppUsage
    void closeSystemDialogs(String str) throws RemoteException;

    void crashApplication(int i, int i2, String str, int i3, String str2) throws RemoteException;

    boolean dumpHeap(String str, int i, boolean z, boolean z2, boolean z3, String str2, ParcelFileDescriptor parcelFileDescriptor, RemoteCallback remoteCallback) throws RemoteException;

    void dumpHeapFinished(String str) throws RemoteException;

    void enterSafeMode() throws RemoteException;

    @UnsupportedAppUsage
    boolean finishActivity(IBinder iBinder, int i, Intent intent, int i2) throws RemoteException;

    @UnsupportedAppUsage
    void finishHeavyWeightApp() throws RemoteException;

    void finishInstrumentation(IApplicationThread iApplicationThread, int i, Bundle bundle) throws RemoteException;

    void finishReceiver(IBinder iBinder, int i, String str, Bundle bundle, boolean z, int i2) throws RemoteException;

    @UnsupportedAppUsage
    void forceStopPackage(String str, int i) throws RemoteException;

    @UnsupportedAppUsage
    List<ActivityManager.StackInfo> getAllStackInfos() throws RemoteException;

    @UnsupportedAppUsage
    Configuration getConfiguration() throws RemoteException;

    ContentProviderHolder getContentProvider(IApplicationThread iApplicationThread, String str, String str2, int i, boolean z) throws RemoteException;

    ContentProviderHolder getContentProviderExternal(String str, int i, IBinder iBinder, String str2) throws RemoteException;

    @UnsupportedAppUsage
    UserInfo getCurrentUser() throws RemoteException;

    @UnsupportedAppUsage
    List<ActivityManager.RunningTaskInfo> getFilteredTasks(int i, int i2, int i3) throws RemoteException;

    ActivityManager.StackInfo getFocusedStackInfo() throws RemoteException;

    int getForegroundServiceType(ComponentName componentName, IBinder iBinder) throws RemoteException;

    @UnsupportedAppUsage
    Intent getIntentForIntentSender(IIntentSender iIntentSender) throws RemoteException;

    @UnsupportedAppUsage
    IIntentSender getIntentSender(int i, String str, IBinder iBinder, String str2, int i2, Intent[] intentArr, String[] strArr, int i3, Bundle bundle, int i4) throws RemoteException;

    @UnsupportedAppUsage
    String getLaunchedFromPackage(IBinder iBinder) throws RemoteException;

    @UnsupportedAppUsage
    int getLaunchedFromUid(IBinder iBinder) throws RemoteException;

    ParcelFileDescriptor getLifeMonitor() throws RemoteException;

    @UnsupportedAppUsage
    int getLockTaskModeState() throws RemoteException;

    @UnsupportedAppUsage
    void getMemoryInfo(ActivityManager.MemoryInfo memoryInfo) throws RemoteException;

    int getMemoryTrimLevel() throws RemoteException;

    void getMyMemoryState(ActivityManager.RunningAppProcessInfo runningAppProcessInfo) throws RemoteException;

    String getPackageForIntentSender(IIntentSender iIntentSender) throws RemoteException;

    @UnsupportedAppUsage
    int getPackageProcessState(String str, String str2) throws RemoteException;

    @UnsupportedAppUsage
    int getProcessLimit() throws RemoteException;

    @UnsupportedAppUsage
    Debug.MemoryInfo[] getProcessMemoryInfo(int[] iArr) throws RemoteException;

    @UnsupportedAppUsage
    long[] getProcessPss(int[] iArr) throws RemoteException;

    List<ActivityManager.ProcessErrorStateInfo> getProcessesInErrorState() throws RemoteException;

    @UnsupportedAppUsage
    String getProviderMimeType(Uri uri, int i) throws RemoteException;

    @UnsupportedAppUsage
    ParceledListSlice getRecentTasks(int i, int i2, int i3) throws RemoteException;

    @UnsupportedAppUsage
    List<ActivityManager.RunningAppProcessInfo> getRunningAppProcesses() throws RemoteException;

    List<ApplicationInfo> getRunningExternalApplications() throws RemoteException;

    PendingIntent getRunningServiceControlPanel(ComponentName componentName) throws RemoteException;

    int[] getRunningUserIds() throws RemoteException;

    @UnsupportedAppUsage
    List<ActivityManager.RunningServiceInfo> getServices(int i, int i2) throws RemoteException;

    String getTagForIntentSender(IIntentSender iIntentSender, String str) throws RemoteException;

    @UnsupportedAppUsage
    Rect getTaskBounds(int i) throws RemoteException;

    @UnsupportedAppUsage
    int getTaskForActivity(IBinder iBinder, boolean z) throws RemoteException;

    @UnsupportedAppUsage
    ActivityManager.TaskSnapshot getTaskSnapshot(int i, boolean z) throws RemoteException;

    List<ActivityManager.RunningTaskInfo> getTasks(int i) throws RemoteException;

    int getUidForIntentSender(IIntentSender iIntentSender) throws RemoteException;

    int getUidProcessState(int i, String str) throws RemoteException;

    void grantUriPermission(IApplicationThread iApplicationThread, String str, Uri uri, int i, int i2) throws RemoteException;

    void handleApplicationCrash(IBinder iBinder, ApplicationErrorReport.ParcelableCrashInfo parcelableCrashInfo) throws RemoteException;

    @UnsupportedAppUsage
    void handleApplicationStrictModeViolation(IBinder iBinder, int i, StrictMode.ViolationInfo violationInfo) throws RemoteException;

    boolean handleApplicationWtf(IBinder iBinder, String str, boolean z, ApplicationErrorReport.ParcelableCrashInfo parcelableCrashInfo) throws RemoteException;

    int handleIncomingUser(int i, int i2, int i3, boolean z, boolean z2, String str, String str2) throws RemoteException;

    @UnsupportedAppUsage
    void hang(IBinder iBinder, boolean z) throws RemoteException;

    boolean isAppStartModeDisabled(int i, String str) throws RemoteException;

    boolean isBackgroundRestricted(String str) throws RemoteException;

    @UnsupportedAppUsage
    boolean isInLockTaskMode() throws RemoteException;

    boolean isIntentSenderABroadcast(IIntentSender iIntentSender) throws RemoteException;

    boolean isIntentSenderAForegroundService(IIntentSender iIntentSender) throws RemoteException;

    @UnsupportedAppUsage
    boolean isIntentSenderAnActivity(IIntentSender iIntentSender) throws RemoteException;

    boolean isIntentSenderTargetedToPackage(IIntentSender iIntentSender) throws RemoteException;

    boolean isTopActivityImmersive() throws RemoteException;

    @UnsupportedAppUsage
    boolean isTopOfTask(IBinder iBinder) throws RemoteException;

    boolean isUidActive(int i, String str) throws RemoteException;

    boolean isUserAMonkey() throws RemoteException;

    @UnsupportedAppUsage
    boolean isUserRunning(int i, int i2) throws RemoteException;

    boolean isVrModePackageEnabled(ComponentName componentName) throws RemoteException;

    @UnsupportedAppUsage
    void killAllBackgroundProcesses() throws RemoteException;

    void killApplication(String str, int i, int i2, String str2) throws RemoteException;

    void killApplicationProcess(String str, int i) throws RemoteException;

    @UnsupportedAppUsage
    void killBackgroundProcesses(String str, int i) throws RemoteException;

    void killPackageDependents(String str, int i) throws RemoteException;

    boolean killPids(int[] iArr, String str, boolean z) throws RemoteException;

    boolean killProcessesBelowForeground(String str) throws RemoteException;

    void killUid(int i, int i2, String str) throws RemoteException;

    void makePackageIdle(String str, int i) throws RemoteException;

    @UnsupportedAppUsage
    boolean moveActivityTaskToBack(IBinder iBinder, boolean z) throws RemoteException;

    @UnsupportedAppUsage
    void moveTaskToFront(IApplicationThread iApplicationThread, String str, int i, int i2, Bundle bundle) throws RemoteException;

    @UnsupportedAppUsage
    void moveTaskToStack(int i, int i2, boolean z) throws RemoteException;

    @UnsupportedAppUsage
    boolean moveTopActivityToPinnedStack(int i, Rect rect) throws RemoteException;

    void noteAlarmFinish(IIntentSender iIntentSender, WorkSource workSource, int i, String str) throws RemoteException;

    void noteAlarmStart(IIntentSender iIntentSender, WorkSource workSource, int i, String str) throws RemoteException;

    void noteWakeupAlarm(IIntentSender iIntentSender, WorkSource workSource, int i, String str, String str2) throws RemoteException;

    void notifyCleartextNetwork(int i, byte[] bArr) throws RemoteException;

    void notifyLockedProfile(int i) throws RemoteException;

    ParcelFileDescriptor openContentUri(String str) throws RemoteException;

    IBinder peekService(Intent intent, String str, String str2) throws RemoteException;

    void performIdleMaintenance() throws RemoteException;

    @UnsupportedAppUsage
    void positionTaskInStack(int i, int i2, int i3) throws RemoteException;

    @UnsupportedAppUsage
    boolean profileControl(String str, int i, boolean z, ProfilerInfo profilerInfo, int i2) throws RemoteException;

    @UnsupportedAppUsage
    void publishContentProviders(IApplicationThread iApplicationThread, List<ContentProviderHolder> list) throws RemoteException;

    void publishService(IBinder iBinder, Intent intent, IBinder iBinder2) throws RemoteException;

    boolean refContentProvider(IBinder iBinder, int i, int i2) throws RemoteException;

    void registerIntentSenderCancelListener(IIntentSender iIntentSender, IResultReceiver iResultReceiver) throws RemoteException;

    @UnsupportedAppUsage
    void registerProcessObserver(IProcessObserver iProcessObserver) throws RemoteException;

    @UnsupportedAppUsage
    Intent registerReceiver(IApplicationThread iApplicationThread, String str, IIntentReceiver iIntentReceiver, IntentFilter intentFilter, String str2, int i, int i2) throws RemoteException;

    @UnsupportedAppUsage
    void registerTaskStackListener(ITaskStackListener iTaskStackListener) throws RemoteException;

    void registerUidObserver(IUidObserver iUidObserver, int i, int i2, String str) throws RemoteException;

    @UnsupportedAppUsage
    void registerUserSwitchObserver(IUserSwitchObserver iUserSwitchObserver, String str) throws RemoteException;

    void removeContentProvider(IBinder iBinder, boolean z) throws RemoteException;

    @UnsupportedAppUsage
    void removeContentProviderExternal(String str, IBinder iBinder) throws RemoteException;

    void removeContentProviderExternalAsUser(String str, IBinder iBinder, int i) throws RemoteException;

    @UnsupportedAppUsage
    void removeStack(int i) throws RemoteException;

    @UnsupportedAppUsage
    boolean removeTask(int i) throws RemoteException;

    @UnsupportedAppUsage
    void requestBugReport(int i) throws RemoteException;

    void requestSystemServerHeapDump() throws RemoteException;

    void requestTelephonyBugReport(String str, String str2) throws RemoteException;

    void requestWifiBugReport(String str, String str2) throws RemoteException;

    @UnsupportedAppUsage
    void resizeDockedStack(Rect rect, Rect rect2, Rect rect3, Rect rect4, Rect rect5) throws RemoteException;

    @UnsupportedAppUsage
    void resizeStack(int i, Rect rect, boolean z, boolean z2, boolean z3, int i2) throws RemoteException;

    @UnsupportedAppUsage
    void resizeTask(int i, Rect rect, int i2) throws RemoteException;

    @UnsupportedAppUsage
    void restart() throws RemoteException;

    int restartUserInBackground(int i) throws RemoteException;

    @UnsupportedAppUsage
    void resumeAppSwitches() throws RemoteException;

    void revokeUriPermission(IApplicationThread iApplicationThread, String str, Uri uri, int i, int i2) throws RemoteException;

    void scheduleApplicationInfoChanged(List<String> list, int i) throws RemoteException;

    @UnsupportedAppUsage
    void sendIdleJobTrigger() throws RemoteException;

    int sendIntentSender(IIntentSender iIntentSender, IBinder iBinder, int i, Intent intent, String str, IIntentReceiver iIntentReceiver, String str2, Bundle bundle) throws RemoteException;

    void serviceDoneExecuting(IBinder iBinder, int i, int i2, int i3) throws RemoteException;

    @UnsupportedAppUsage
    void setActivityController(IActivityController iActivityController, boolean z) throws RemoteException;

    void setAgentApp(String str, String str2) throws RemoteException;

    @UnsupportedAppUsage
    void setAlwaysFinish(boolean z) throws RemoteException;

    @UnsupportedAppUsage
    void setDebugApp(String str, boolean z, boolean z2) throws RemoteException;

    @UnsupportedAppUsage
    void setDumpHeapDebugLimit(String str, int i, long j, String str2) throws RemoteException;

    void setFocusedStack(int i) throws RemoteException;

    void setHasTopUi(boolean z) throws RemoteException;

    @UnsupportedAppUsage
    void setPackageScreenCompatMode(String str, int i) throws RemoteException;

    void setPersistentVrThread(int i) throws RemoteException;

    @UnsupportedAppUsage
    void setProcessImportant(IBinder iBinder, int i, boolean z, String str) throws RemoteException;

    @UnsupportedAppUsage
    void setProcessLimit(int i) throws RemoteException;

    @UnsupportedAppUsage
    boolean setProcessMemoryTrimLevel(String str, int i, int i2) throws RemoteException;

    void setRenderThread(int i) throws RemoteException;

    @UnsupportedAppUsage
    void setRequestedOrientation(IBinder iBinder, int i) throws RemoteException;

    void setServiceForeground(ComponentName componentName, IBinder iBinder, int i, Notification notification, int i2, int i3) throws RemoteException;

    @UnsupportedAppUsage
    void setTaskResizeable(int i, int i2) throws RemoteException;

    void setUserIsMonkey(boolean z) throws RemoteException;

    void showBootMessage(CharSequence charSequence, boolean z) throws RemoteException;

    void showWaitingForDebugger(IApplicationThread iApplicationThread, boolean z) throws RemoteException;

    @UnsupportedAppUsage
    boolean shutdown(int i) throws RemoteException;

    void signalPersistentProcesses(int i) throws RemoteException;

    @UnsupportedAppUsage
    int startActivity(IApplicationThread iApplicationThread, String str, Intent intent, String str2, IBinder iBinder, String str3, int i, int i2, ProfilerInfo profilerInfo, Bundle bundle) throws RemoteException;

    @UnsupportedAppUsage
    int startActivityAsUser(IApplicationThread iApplicationThread, String str, Intent intent, String str2, IBinder iBinder, String str3, int i, int i2, ProfilerInfo profilerInfo, Bundle bundle, int i3) throws RemoteException;

    @UnsupportedAppUsage
    int startActivityFromRecents(int i, Bundle bundle) throws RemoteException;

    @UnsupportedAppUsage
    boolean startBinderTracking() throws RemoteException;

    void startConfirmDeviceCredentialIntent(Intent intent, Bundle bundle) throws RemoteException;

    void startDelegateShellPermissionIdentity(int i, String[] strArr) throws RemoteException;

    @UnsupportedAppUsage
    boolean startInstrumentation(ComponentName componentName, String str, int i, Bundle bundle, IInstrumentationWatcher iInstrumentationWatcher, IUiAutomationConnection iUiAutomationConnection, int i2, String str2) throws RemoteException;

    @UnsupportedAppUsage
    void startRecentsActivity(Intent intent, IAssistDataReceiver iAssistDataReceiver, IRecentsAnimationRunner iRecentsAnimationRunner) throws RemoteException;

    ComponentName startService(IApplicationThread iApplicationThread, Intent intent, String str, boolean z, String str2, int i) throws RemoteException;

    @UnsupportedAppUsage
    void startSystemLockTaskMode(int i) throws RemoteException;

    @UnsupportedAppUsage
    boolean startUserInBackground(int i) throws RemoteException;

    boolean startUserInBackgroundWithListener(int i, IProgressListener iProgressListener) throws RemoteException;

    boolean startUserInForegroundWithListener(int i, IProgressListener iProgressListener) throws RemoteException;

    @UnsupportedAppUsage
    void stopAppSwitches() throws RemoteException;

    @UnsupportedAppUsage
    boolean stopBinderTrackingAndDump(ParcelFileDescriptor parcelFileDescriptor) throws RemoteException;

    void stopDelegateShellPermissionIdentity() throws RemoteException;

    @UnsupportedAppUsage
    int stopService(IApplicationThread iApplicationThread, Intent intent, String str, int i) throws RemoteException;

    boolean stopServiceToken(ComponentName componentName, IBinder iBinder, int i) throws RemoteException;

    @UnsupportedAppUsage
    int stopUser(int i, boolean z, IStopUserCallback iStopUserCallback) throws RemoteException;

    @UnsupportedAppUsage
    void suppressResizeConfigChanges(boolean z) throws RemoteException;

    @UnsupportedAppUsage
    boolean switchUser(int i) throws RemoteException;

    void unbindBackupAgent(ApplicationInfo applicationInfo) throws RemoteException;

    void unbindFinished(IBinder iBinder, Intent intent, boolean z) throws RemoteException;

    @UnsupportedAppUsage
    boolean unbindService(IServiceConnection iServiceConnection) throws RemoteException;

    void unbroadcastIntent(IApplicationThread iApplicationThread, Intent intent, int i) throws RemoteException;

    @UnsupportedAppUsage
    void unhandledBack() throws RemoteException;

    @UnsupportedAppUsage
    boolean unlockUser(int i, byte[] bArr, byte[] bArr2, IProgressListener iProgressListener) throws RemoteException;

    void unregisterIntentSenderCancelListener(IIntentSender iIntentSender, IResultReceiver iResultReceiver) throws RemoteException;

    @UnsupportedAppUsage
    void unregisterProcessObserver(IProcessObserver iProcessObserver) throws RemoteException;

    @UnsupportedAppUsage
    void unregisterReceiver(IIntentReceiver iIntentReceiver) throws RemoteException;

    void unregisterTaskStackListener(ITaskStackListener iTaskStackListener) throws RemoteException;

    void unregisterUidObserver(IUidObserver iUidObserver) throws RemoteException;

    void unregisterUserSwitchObserver(IUserSwitchObserver iUserSwitchObserver) throws RemoteException;

    @UnsupportedAppUsage
    void unstableProviderDied(IBinder iBinder) throws RemoteException;

    @UnsupportedAppUsage
    boolean updateConfiguration(Configuration configuration) throws RemoteException;

    void updateDeviceOwner(String str) throws RemoteException;

    void updateLockTaskPackages(int i, String[] strArr) throws RemoteException;

    @UnsupportedAppUsage
    void updatePersistentConfiguration(Configuration configuration) throws RemoteException;

    void updateServiceGroup(IServiceConnection iServiceConnection, int i, int i2) throws RemoteException;

    void waitForNetworkStateUpdate(long j) throws RemoteException;

    /* loaded from: classes.dex */
    public static class Default implements IActivityManager {
        @Override // android.app.IActivityManager
        public ParcelFileDescriptor openContentUri(String uriString) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public void registerUidObserver(IUidObserver observer, int which, int cutpoint, String callingPackage) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void unregisterUidObserver(IUidObserver observer) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public boolean isUidActive(int uid, String callingPackage) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public int getUidProcessState(int uid, String callingPackage) throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public void handleApplicationCrash(IBinder app, ApplicationErrorReport.ParcelableCrashInfo crashInfo) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public int startActivity(IApplicationThread caller, String callingPackage, Intent intent, String resolvedType, IBinder resultTo, String resultWho, int requestCode, int flags, ProfilerInfo profilerInfo, Bundle options) throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public void unhandledBack() throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public boolean finishActivity(IBinder token, int code, Intent data, int finishTask) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public Intent registerReceiver(IApplicationThread caller, String callerPackage, IIntentReceiver receiver, IntentFilter filter, String requiredPermission, int userId, int flags) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public void unregisterReceiver(IIntentReceiver receiver) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public int broadcastIntent(IApplicationThread caller, Intent intent, String resolvedType, IIntentReceiver resultTo, int resultCode, String resultData, Bundle map, String[] requiredPermissions, int appOp, Bundle options, boolean serialized, boolean sticky, int userId) throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public void unbroadcastIntent(IApplicationThread caller, Intent intent, int userId) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void finishReceiver(IBinder who, int resultCode, String resultData, Bundle map, boolean abortBroadcast, int flags) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void attachApplication(IApplicationThread app, long startSeq) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public List<ActivityManager.RunningTaskInfo> getTasks(int maxNum) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public List<ActivityManager.RunningTaskInfo> getFilteredTasks(int maxNum, int ignoreActivityType, int ignoreWindowingMode) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public void moveTaskToFront(IApplicationThread caller, String callingPackage, int task, int flags, Bundle options) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public int getTaskForActivity(IBinder token, boolean onlyRoot) throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public ContentProviderHolder getContentProvider(IApplicationThread caller, String callingPackage, String name, int userId, boolean stable) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public void publishContentProviders(IApplicationThread caller, List<ContentProviderHolder> providers) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public boolean refContentProvider(IBinder connection, int stableDelta, int unstableDelta) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public PendingIntent getRunningServiceControlPanel(ComponentName service) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public ComponentName startService(IApplicationThread caller, Intent service, String resolvedType, boolean requireForeground, String callingPackage, int userId) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public int stopService(IApplicationThread caller, Intent service, String resolvedType, int userId) throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public int bindService(IApplicationThread caller, IBinder token, Intent service, String resolvedType, IServiceConnection connection, int flags, String callingPackage, int userId) throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public int bindIsolatedService(IApplicationThread caller, IBinder token, Intent service, String resolvedType, IServiceConnection connection, int flags, String instanceName, String callingPackage, int userId) throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public void updateServiceGroup(IServiceConnection connection, int group, int importance) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public boolean unbindService(IServiceConnection connection) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public void publishService(IBinder token, Intent intent, IBinder service) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void setDebugApp(String packageName, boolean waitForDebugger, boolean persistent) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void setAgentApp(String packageName, String agent) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void setAlwaysFinish(boolean enabled) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public boolean startInstrumentation(ComponentName className, String profileFile, int flags, Bundle arguments, IInstrumentationWatcher watcher, IUiAutomationConnection connection, int userId, String abiOverride) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public void addInstrumentationResults(IApplicationThread target, Bundle results) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void finishInstrumentation(IApplicationThread target, int resultCode, Bundle results) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public Configuration getConfiguration() throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public boolean updateConfiguration(Configuration values) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public boolean stopServiceToken(ComponentName className, IBinder token, int startId) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public void setProcessLimit(int max) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public int getProcessLimit() throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public int checkPermission(String permission, int pid, int uid) throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public int checkUriPermission(Uri uri, int pid, int uid, int mode, int userId, IBinder callerToken) throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public void grantUriPermission(IApplicationThread caller, String targetPkg, Uri uri, int mode, int userId) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void revokeUriPermission(IApplicationThread caller, String targetPkg, Uri uri, int mode, int userId) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void setActivityController(IActivityController watcher, boolean imAMonkey) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void showWaitingForDebugger(IApplicationThread who, boolean waiting) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void signalPersistentProcesses(int signal) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public ParceledListSlice getRecentTasks(int maxNum, int flags, int userId) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public void serviceDoneExecuting(IBinder token, int type, int startId, int res) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public IIntentSender getIntentSender(int type, String packageName, IBinder token, String resultWho, int requestCode, Intent[] intents, String[] resolvedTypes, int flags, Bundle options, int userId) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public void cancelIntentSender(IIntentSender sender) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public String getPackageForIntentSender(IIntentSender sender) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public void registerIntentSenderCancelListener(IIntentSender sender, IResultReceiver receiver) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void unregisterIntentSenderCancelListener(IIntentSender sender, IResultReceiver receiver) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void enterSafeMode() throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void noteWakeupAlarm(IIntentSender sender, WorkSource workSource, int sourceUid, String sourcePkg, String tag) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void removeContentProvider(IBinder connection, boolean stable) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void setRequestedOrientation(IBinder token, int requestedOrientation) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void unbindFinished(IBinder token, Intent service, boolean doRebind) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void setProcessImportant(IBinder token, int pid, boolean isForeground, String reason) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void setServiceForeground(ComponentName className, IBinder token, int id, Notification notification, int flags, int foregroundServiceType) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public int getForegroundServiceType(ComponentName className, IBinder token) throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public boolean moveActivityTaskToBack(IBinder token, boolean nonRoot) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public void getMemoryInfo(ActivityManager.MemoryInfo outInfo) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public List<ActivityManager.ProcessErrorStateInfo> getProcessesInErrorState() throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public boolean clearApplicationUserData(String packageName, boolean keepState, IPackageDataObserver observer, int userId) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public void forceStopPackage(String packageName, int userId) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public boolean killPids(int[] pids, String reason, boolean secure) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public List<ActivityManager.RunningServiceInfo> getServices(int maxNum, int flags) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public List<ActivityManager.RunningAppProcessInfo> getRunningAppProcesses() throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public IBinder peekService(Intent service, String resolvedType, String callingPackage) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public boolean profileControl(String process, int userId, boolean start, ProfilerInfo profilerInfo, int profileType) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public boolean shutdown(int timeout) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public void stopAppSwitches() throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void resumeAppSwitches() throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public boolean bindBackupAgent(String packageName, int backupRestoreMode, int targetUserId) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public void backupAgentCreated(String packageName, IBinder agent, int userId) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void unbindBackupAgent(ApplicationInfo appInfo) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public int getUidForIntentSender(IIntentSender sender) throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public int handleIncomingUser(int callingPid, int callingUid, int userId, boolean allowAll, boolean requireFull, String name, String callerPackage) throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public void addPackageDependency(String packageName) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void killApplication(String pkg, int appId, int userId, String reason) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void closeSystemDialogs(String reason) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public Debug.MemoryInfo[] getProcessMemoryInfo(int[] pids) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public void killApplicationProcess(String processName, int uid) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public boolean handleApplicationWtf(IBinder app, String tag, boolean system, ApplicationErrorReport.ParcelableCrashInfo crashInfo) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public void killBackgroundProcesses(String packageName, int userId) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public boolean isUserAMonkey() throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public List<ApplicationInfo> getRunningExternalApplications() throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public void finishHeavyWeightApp() throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void handleApplicationStrictModeViolation(IBinder app, int penaltyMask, StrictMode.ViolationInfo crashInfo) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public boolean isTopActivityImmersive() throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public void crashApplication(int uid, int initialPid, String packageName, int userId, String message) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public String getProviderMimeType(Uri uri, int userId) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public boolean dumpHeap(String process, int userId, boolean managed, boolean mallocInfo, boolean runGc, String path, ParcelFileDescriptor fd, RemoteCallback finishCallback) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public boolean isUserRunning(int userid, int flags) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public void setPackageScreenCompatMode(String packageName, int mode) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public boolean switchUser(int userid) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public boolean removeTask(int taskId) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public void registerProcessObserver(IProcessObserver observer) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void unregisterProcessObserver(IProcessObserver observer) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public boolean isIntentSenderTargetedToPackage(IIntentSender sender) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public void updatePersistentConfiguration(Configuration values) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public long[] getProcessPss(int[] pids) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public void showBootMessage(CharSequence msg, boolean always) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void killAllBackgroundProcesses() throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public ContentProviderHolder getContentProviderExternal(String name, int userId, IBinder token, String tag) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public void removeContentProviderExternal(String name, IBinder token) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void removeContentProviderExternalAsUser(String name, IBinder token, int userId) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void getMyMemoryState(ActivityManager.RunningAppProcessInfo outInfo) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public boolean killProcessesBelowForeground(String reason) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public UserInfo getCurrentUser() throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public int getLaunchedFromUid(IBinder activityToken) throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public void unstableProviderDied(IBinder connection) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public boolean isIntentSenderAnActivity(IIntentSender sender) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public boolean isIntentSenderAForegroundService(IIntentSender sender) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public boolean isIntentSenderABroadcast(IIntentSender sender) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public int startActivityAsUser(IApplicationThread caller, String callingPackage, Intent intent, String resolvedType, IBinder resultTo, String resultWho, int requestCode, int flags, ProfilerInfo profilerInfo, Bundle options, int userId) throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public int stopUser(int userid, boolean force, IStopUserCallback callback) throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public void registerUserSwitchObserver(IUserSwitchObserver observer, String name) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void unregisterUserSwitchObserver(IUserSwitchObserver observer) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public int[] getRunningUserIds() throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public void requestSystemServerHeapDump() throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void requestBugReport(int bugreportType) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void requestTelephonyBugReport(String shareTitle, String shareDescription) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void requestWifiBugReport(String shareTitle, String shareDescription) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public Intent getIntentForIntentSender(IIntentSender sender) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public String getLaunchedFromPackage(IBinder activityToken) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public void killUid(int appId, int userId, String reason) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void setUserIsMonkey(boolean monkey) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void hang(IBinder who, boolean allowRestart) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public List<ActivityManager.StackInfo> getAllStackInfos() throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public void moveTaskToStack(int taskId, int stackId, boolean toTop) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void resizeStack(int stackId, Rect bounds, boolean allowResizeInDockedMode, boolean preserveWindows, boolean animate, int animationDuration) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void setFocusedStack(int stackId) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public ActivityManager.StackInfo getFocusedStackInfo() throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public void restart() throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void performIdleMaintenance() throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void appNotRespondingViaProvider(IBinder connection) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public Rect getTaskBounds(int taskId) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public boolean setProcessMemoryTrimLevel(String process, int uid, int level) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public String getTagForIntentSender(IIntentSender sender, String prefix) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public boolean startUserInBackground(int userid) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public boolean isInLockTaskMode() throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public void startRecentsActivity(Intent intent, IAssistDataReceiver assistDataReceiver, IRecentsAnimationRunner recentsAnimationRunner) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void cancelRecentsAnimation(boolean restoreHomeStackPosition) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public int startActivityFromRecents(int taskId, Bundle options) throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public void startSystemLockTaskMode(int taskId) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public boolean isTopOfTask(IBinder token) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public void bootAnimationComplete() throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public int checkPermissionWithToken(String permission, int pid, int uid, IBinder callerToken) throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public void registerTaskStackListener(ITaskStackListener listener) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void unregisterTaskStackListener(ITaskStackListener listener) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void notifyCleartextNetwork(int uid, byte[] firstPacket) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void setTaskResizeable(int taskId, int resizeableMode) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void resizeTask(int taskId, Rect bounds, int resizeMode) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public int getLockTaskModeState() throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public void setDumpHeapDebugLimit(String processName, int uid, long maxMemSize, String reportPackage) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void dumpHeapFinished(String path) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void updateLockTaskPackages(int userId, String[] packages) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void noteAlarmStart(IIntentSender sender, WorkSource workSource, int sourceUid, String tag) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void noteAlarmFinish(IIntentSender sender, WorkSource workSource, int sourceUid, String tag) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public int getPackageProcessState(String packageName, String callingPackage) throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public void updateDeviceOwner(String packageName) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public boolean startBinderTracking() throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public boolean stopBinderTrackingAndDump(ParcelFileDescriptor fd) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public void positionTaskInStack(int taskId, int stackId, int position) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void suppressResizeConfigChanges(boolean suppress) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public boolean moveTopActivityToPinnedStack(int stackId, Rect bounds) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public boolean isAppStartModeDisabled(int uid, String packageName) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public boolean unlockUser(int userid, byte[] token, byte[] secret, IProgressListener listener) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public void killPackageDependents(String packageName, int userId) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void resizeDockedStack(Rect dockedBounds, Rect tempDockedTaskBounds, Rect tempDockedTaskInsetBounds, Rect tempOtherTaskBounds, Rect tempOtherTaskInsetBounds) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void removeStack(int stackId) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void makePackageIdle(String packageName, int userId) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public int getMemoryTrimLevel() throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public boolean isVrModePackageEnabled(ComponentName packageName) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public void notifyLockedProfile(int userId) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void startConfirmDeviceCredentialIntent(Intent intent, Bundle options) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void sendIdleJobTrigger() throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public int sendIntentSender(IIntentSender target, IBinder whitelistToken, int code, Intent intent, String resolvedType, IIntentReceiver finishedReceiver, String requiredPermission, Bundle options) throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public boolean isBackgroundRestricted(String packageName) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public void setRenderThread(int tid) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void setHasTopUi(boolean hasTopUi) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public int restartUserInBackground(int userId) throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public void cancelTaskWindowTransition(int taskId) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public ActivityManager.TaskSnapshot getTaskSnapshot(int taskId, boolean reducedResolution) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public void scheduleApplicationInfoChanged(List<String> packageNames, int userId) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void setPersistentVrThread(int tid) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void waitForNetworkStateUpdate(long procStateSeq) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void backgroundWhitelistUid(int uid) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public boolean startUserInBackgroundWithListener(int userid, IProgressListener unlockProgressListener) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public void startDelegateShellPermissionIdentity(int uid, String[] permissions) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void stopDelegateShellPermissionIdentity() throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public ParcelFileDescriptor getLifeMonitor() throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public boolean startUserInForegroundWithListener(int userid, IProgressListener unlockProgressListener) throws RemoteException {
            return false;
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* loaded from: classes.dex */
    public static abstract class Stub extends Binder implements IActivityManager {
        private static final String DESCRIPTOR = "android.app.IActivityManager";
        static final int TRANSACTION_addInstrumentationResults = 35;
        static final int TRANSACTION_addPackageDependency = 82;
        static final int TRANSACTION_appNotRespondingViaProvider = 140;
        static final int TRANSACTION_attachApplication = 15;
        static final int TRANSACTION_backgroundWhitelistUid = 192;
        static final int TRANSACTION_backupAgentCreated = 78;
        static final int TRANSACTION_bindBackupAgent = 77;
        static final int TRANSACTION_bindIsolatedService = 27;
        static final int TRANSACTION_bindService = 26;
        static final int TRANSACTION_bootAnimationComplete = 151;
        static final int TRANSACTION_broadcastIntent = 12;
        static final int TRANSACTION_cancelIntentSender = 52;
        static final int TRANSACTION_cancelRecentsAnimation = 147;
        static final int TRANSACTION_cancelTaskWindowTransition = 187;
        static final int TRANSACTION_checkPermission = 42;
        static final int TRANSACTION_checkPermissionWithToken = 152;
        static final int TRANSACTION_checkUriPermission = 43;
        static final int TRANSACTION_clearApplicationUserData = 67;
        static final int TRANSACTION_closeSystemDialogs = 84;
        static final int TRANSACTION_crashApplication = 94;
        static final int TRANSACTION_dumpHeap = 96;
        static final int TRANSACTION_dumpHeapFinished = 160;
        static final int TRANSACTION_enterSafeMode = 56;
        static final int TRANSACTION_finishActivity = 9;
        static final int TRANSACTION_finishHeavyWeightApp = 91;
        static final int TRANSACTION_finishInstrumentation = 36;
        static final int TRANSACTION_finishReceiver = 14;
        static final int TRANSACTION_forceStopPackage = 68;
        static final int TRANSACTION_getAllStackInfos = 133;
        static final int TRANSACTION_getConfiguration = 37;
        static final int TRANSACTION_getContentProvider = 20;
        static final int TRANSACTION_getContentProviderExternal = 108;
        static final int TRANSACTION_getCurrentUser = 113;
        static final int TRANSACTION_getFilteredTasks = 17;
        static final int TRANSACTION_getFocusedStackInfo = 137;
        static final int TRANSACTION_getForegroundServiceType = 63;
        static final int TRANSACTION_getIntentForIntentSender = 128;
        static final int TRANSACTION_getIntentSender = 51;
        static final int TRANSACTION_getLaunchedFromPackage = 129;
        static final int TRANSACTION_getLaunchedFromUid = 114;
        static final int TRANSACTION_getLifeMonitor = 196;
        static final int TRANSACTION_getLockTaskModeState = 158;
        static final int TRANSACTION_getMemoryInfo = 65;
        static final int TRANSACTION_getMemoryTrimLevel = 177;
        static final int TRANSACTION_getMyMemoryState = 111;
        static final int TRANSACTION_getPackageForIntentSender = 53;
        static final int TRANSACTION_getPackageProcessState = 164;
        static final int TRANSACTION_getProcessLimit = 41;
        static final int TRANSACTION_getProcessMemoryInfo = 85;
        static final int TRANSACTION_getProcessPss = 105;
        static final int TRANSACTION_getProcessesInErrorState = 66;
        static final int TRANSACTION_getProviderMimeType = 95;
        static final int TRANSACTION_getRecentTasks = 49;
        static final int TRANSACTION_getRunningAppProcesses = 71;
        static final int TRANSACTION_getRunningExternalApplications = 90;
        static final int TRANSACTION_getRunningServiceControlPanel = 23;
        static final int TRANSACTION_getRunningUserIds = 123;
        static final int TRANSACTION_getServices = 70;
        static final int TRANSACTION_getTagForIntentSender = 143;
        static final int TRANSACTION_getTaskBounds = 141;
        static final int TRANSACTION_getTaskForActivity = 19;
        static final int TRANSACTION_getTaskSnapshot = 188;
        static final int TRANSACTION_getTasks = 16;
        static final int TRANSACTION_getUidForIntentSender = 80;
        static final int TRANSACTION_getUidProcessState = 5;
        static final int TRANSACTION_grantUriPermission = 44;
        static final int TRANSACTION_handleApplicationCrash = 6;
        static final int TRANSACTION_handleApplicationStrictModeViolation = 92;
        static final int TRANSACTION_handleApplicationWtf = 87;
        static final int TRANSACTION_handleIncomingUser = 81;
        static final int TRANSACTION_hang = 132;
        static final int TRANSACTION_isAppStartModeDisabled = 171;
        static final int TRANSACTION_isBackgroundRestricted = 183;
        static final int TRANSACTION_isInLockTaskMode = 145;
        static final int TRANSACTION_isIntentSenderABroadcast = 118;
        static final int TRANSACTION_isIntentSenderAForegroundService = 117;
        static final int TRANSACTION_isIntentSenderAnActivity = 116;
        static final int TRANSACTION_isIntentSenderTargetedToPackage = 103;
        static final int TRANSACTION_isTopActivityImmersive = 93;
        static final int TRANSACTION_isTopOfTask = 150;
        static final int TRANSACTION_isUidActive = 4;
        static final int TRANSACTION_isUserAMonkey = 89;
        static final int TRANSACTION_isUserRunning = 97;
        static final int TRANSACTION_isVrModePackageEnabled = 178;
        static final int TRANSACTION_killAllBackgroundProcesses = 107;
        static final int TRANSACTION_killApplication = 83;
        static final int TRANSACTION_killApplicationProcess = 86;
        static final int TRANSACTION_killBackgroundProcesses = 88;
        static final int TRANSACTION_killPackageDependents = 173;
        static final int TRANSACTION_killPids = 69;
        static final int TRANSACTION_killProcessesBelowForeground = 112;
        static final int TRANSACTION_killUid = 130;
        static final int TRANSACTION_makePackageIdle = 176;
        static final int TRANSACTION_moveActivityTaskToBack = 64;
        static final int TRANSACTION_moveTaskToFront = 18;
        static final int TRANSACTION_moveTaskToStack = 134;
        static final int TRANSACTION_moveTopActivityToPinnedStack = 170;
        static final int TRANSACTION_noteAlarmFinish = 163;
        static final int TRANSACTION_noteAlarmStart = 162;
        static final int TRANSACTION_noteWakeupAlarm = 57;
        static final int TRANSACTION_notifyCleartextNetwork = 155;
        static final int TRANSACTION_notifyLockedProfile = 179;
        static final int TRANSACTION_openContentUri = 1;
        static final int TRANSACTION_peekService = 72;
        static final int TRANSACTION_performIdleMaintenance = 139;
        static final int TRANSACTION_positionTaskInStack = 168;
        static final int TRANSACTION_profileControl = 73;
        static final int TRANSACTION_publishContentProviders = 21;
        static final int TRANSACTION_publishService = 30;
        static final int TRANSACTION_refContentProvider = 22;
        static final int TRANSACTION_registerIntentSenderCancelListener = 54;
        static final int TRANSACTION_registerProcessObserver = 101;
        static final int TRANSACTION_registerReceiver = 10;
        static final int TRANSACTION_registerTaskStackListener = 153;
        static final int TRANSACTION_registerUidObserver = 2;
        static final int TRANSACTION_registerUserSwitchObserver = 121;
        static final int TRANSACTION_removeContentProvider = 58;
        static final int TRANSACTION_removeContentProviderExternal = 109;
        static final int TRANSACTION_removeContentProviderExternalAsUser = 110;
        static final int TRANSACTION_removeStack = 175;
        static final int TRANSACTION_removeTask = 100;
        static final int TRANSACTION_requestBugReport = 125;
        static final int TRANSACTION_requestSystemServerHeapDump = 124;
        static final int TRANSACTION_requestTelephonyBugReport = 126;
        static final int TRANSACTION_requestWifiBugReport = 127;
        static final int TRANSACTION_resizeDockedStack = 174;
        static final int TRANSACTION_resizeStack = 135;
        static final int TRANSACTION_resizeTask = 157;
        static final int TRANSACTION_restart = 138;
        static final int TRANSACTION_restartUserInBackground = 186;
        static final int TRANSACTION_resumeAppSwitches = 76;
        static final int TRANSACTION_revokeUriPermission = 45;
        static final int TRANSACTION_scheduleApplicationInfoChanged = 189;
        static final int TRANSACTION_sendIdleJobTrigger = 181;
        static final int TRANSACTION_sendIntentSender = 182;
        static final int TRANSACTION_serviceDoneExecuting = 50;
        static final int TRANSACTION_setActivityController = 46;
        static final int TRANSACTION_setAgentApp = 32;
        static final int TRANSACTION_setAlwaysFinish = 33;
        static final int TRANSACTION_setDebugApp = 31;
        static final int TRANSACTION_setDumpHeapDebugLimit = 159;
        static final int TRANSACTION_setFocusedStack = 136;
        static final int TRANSACTION_setHasTopUi = 185;
        static final int TRANSACTION_setPackageScreenCompatMode = 98;
        static final int TRANSACTION_setPersistentVrThread = 190;
        static final int TRANSACTION_setProcessImportant = 61;
        static final int TRANSACTION_setProcessLimit = 40;
        static final int TRANSACTION_setProcessMemoryTrimLevel = 142;
        static final int TRANSACTION_setRenderThread = 184;
        static final int TRANSACTION_setRequestedOrientation = 59;
        static final int TRANSACTION_setServiceForeground = 62;
        static final int TRANSACTION_setTaskResizeable = 156;
        static final int TRANSACTION_setUserIsMonkey = 131;
        static final int TRANSACTION_showBootMessage = 106;
        static final int TRANSACTION_showWaitingForDebugger = 47;
        static final int TRANSACTION_shutdown = 74;
        static final int TRANSACTION_signalPersistentProcesses = 48;
        static final int TRANSACTION_startActivity = 7;
        static final int TRANSACTION_startActivityAsUser = 119;
        static final int TRANSACTION_startActivityFromRecents = 148;
        static final int TRANSACTION_startBinderTracking = 166;
        static final int TRANSACTION_startConfirmDeviceCredentialIntent = 180;
        static final int TRANSACTION_startDelegateShellPermissionIdentity = 194;
        static final int TRANSACTION_startInstrumentation = 34;
        static final int TRANSACTION_startRecentsActivity = 146;
        static final int TRANSACTION_startService = 24;
        static final int TRANSACTION_startSystemLockTaskMode = 149;
        static final int TRANSACTION_startUserInBackground = 144;
        static final int TRANSACTION_startUserInBackgroundWithListener = 193;
        static final int TRANSACTION_startUserInForegroundWithListener = 197;
        static final int TRANSACTION_stopAppSwitches = 75;
        static final int TRANSACTION_stopBinderTrackingAndDump = 167;
        static final int TRANSACTION_stopDelegateShellPermissionIdentity = 195;
        static final int TRANSACTION_stopService = 25;
        static final int TRANSACTION_stopServiceToken = 39;
        static final int TRANSACTION_stopUser = 120;
        static final int TRANSACTION_suppressResizeConfigChanges = 169;
        static final int TRANSACTION_switchUser = 99;
        static final int TRANSACTION_unbindBackupAgent = 79;
        static final int TRANSACTION_unbindFinished = 60;
        static final int TRANSACTION_unbindService = 29;
        static final int TRANSACTION_unbroadcastIntent = 13;
        static final int TRANSACTION_unhandledBack = 8;
        static final int TRANSACTION_unlockUser = 172;
        static final int TRANSACTION_unregisterIntentSenderCancelListener = 55;
        static final int TRANSACTION_unregisterProcessObserver = 102;
        static final int TRANSACTION_unregisterReceiver = 11;
        static final int TRANSACTION_unregisterTaskStackListener = 154;
        static final int TRANSACTION_unregisterUidObserver = 3;
        static final int TRANSACTION_unregisterUserSwitchObserver = 122;
        static final int TRANSACTION_unstableProviderDied = 115;
        static final int TRANSACTION_updateConfiguration = 38;
        static final int TRANSACTION_updateDeviceOwner = 165;
        static final int TRANSACTION_updateLockTaskPackages = 161;
        static final int TRANSACTION_updatePersistentConfiguration = 104;
        static final int TRANSACTION_updateServiceGroup = 28;
        static final int TRANSACTION_waitForNetworkStateUpdate = 191;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IActivityManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IActivityManager)) {
                return (IActivityManager) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "openContentUri";
                case 2:
                    return "registerUidObserver";
                case 3:
                    return "unregisterUidObserver";
                case 4:
                    return "isUidActive";
                case 5:
                    return "getUidProcessState";
                case 6:
                    return "handleApplicationCrash";
                case 7:
                    return "startActivity";
                case 8:
                    return "unhandledBack";
                case 9:
                    return "finishActivity";
                case 10:
                    return "registerReceiver";
                case 11:
                    return "unregisterReceiver";
                case 12:
                    return "broadcastIntent";
                case 13:
                    return "unbroadcastIntent";
                case 14:
                    return "finishReceiver";
                case 15:
                    return "attachApplication";
                case 16:
                    return "getTasks";
                case 17:
                    return "getFilteredTasks";
                case 18:
                    return "moveTaskToFront";
                case 19:
                    return "getTaskForActivity";
                case 20:
                    return "getContentProvider";
                case 21:
                    return "publishContentProviders";
                case 22:
                    return "refContentProvider";
                case 23:
                    return "getRunningServiceControlPanel";
                case 24:
                    return "startService";
                case 25:
                    return "stopService";
                case 26:
                    return "bindService";
                case 27:
                    return "bindIsolatedService";
                case 28:
                    return "updateServiceGroup";
                case 29:
                    return "unbindService";
                case 30:
                    return "publishService";
                case 31:
                    return "setDebugApp";
                case 32:
                    return "setAgentApp";
                case 33:
                    return "setAlwaysFinish";
                case 34:
                    return "startInstrumentation";
                case 35:
                    return "addInstrumentationResults";
                case 36:
                    return "finishInstrumentation";
                case 37:
                    return "getConfiguration";
                case 38:
                    return "updateConfiguration";
                case 39:
                    return "stopServiceToken";
                case 40:
                    return "setProcessLimit";
                case 41:
                    return "getProcessLimit";
                case 42:
                    return "checkPermission";
                case 43:
                    return "checkUriPermission";
                case 44:
                    return "grantUriPermission";
                case 45:
                    return "revokeUriPermission";
                case 46:
                    return "setActivityController";
                case 47:
                    return "showWaitingForDebugger";
                case 48:
                    return "signalPersistentProcesses";
                case 49:
                    return "getRecentTasks";
                case 50:
                    return "serviceDoneExecuting";
                case 51:
                    return "getIntentSender";
                case 52:
                    return "cancelIntentSender";
                case 53:
                    return "getPackageForIntentSender";
                case 54:
                    return "registerIntentSenderCancelListener";
                case 55:
                    return "unregisterIntentSenderCancelListener";
                case 56:
                    return "enterSafeMode";
                case 57:
                    return "noteWakeupAlarm";
                case 58:
                    return "removeContentProvider";
                case 59:
                    return "setRequestedOrientation";
                case 60:
                    return "unbindFinished";
                case 61:
                    return "setProcessImportant";
                case 62:
                    return "setServiceForeground";
                case 63:
                    return "getForegroundServiceType";
                case 64:
                    return "moveActivityTaskToBack";
                case 65:
                    return "getMemoryInfo";
                case 66:
                    return "getProcessesInErrorState";
                case 67:
                    return "clearApplicationUserData";
                case 68:
                    return "forceStopPackage";
                case 69:
                    return "killPids";
                case 70:
                    return "getServices";
                case 71:
                    return "getRunningAppProcesses";
                case 72:
                    return "peekService";
                case 73:
                    return "profileControl";
                case 74:
                    return "shutdown";
                case 75:
                    return "stopAppSwitches";
                case 76:
                    return "resumeAppSwitches";
                case 77:
                    return "bindBackupAgent";
                case 78:
                    return "backupAgentCreated";
                case 79:
                    return "unbindBackupAgent";
                case 80:
                    return "getUidForIntentSender";
                case 81:
                    return "handleIncomingUser";
                case 82:
                    return "addPackageDependency";
                case 83:
                    return "killApplication";
                case 84:
                    return "closeSystemDialogs";
                case 85:
                    return "getProcessMemoryInfo";
                case 86:
                    return "killApplicationProcess";
                case 87:
                    return "handleApplicationWtf";
                case 88:
                    return "killBackgroundProcesses";
                case 89:
                    return "isUserAMonkey";
                case 90:
                    return "getRunningExternalApplications";
                case 91:
                    return "finishHeavyWeightApp";
                case 92:
                    return "handleApplicationStrictModeViolation";
                case 93:
                    return "isTopActivityImmersive";
                case 94:
                    return "crashApplication";
                case 95:
                    return "getProviderMimeType";
                case 96:
                    return "dumpHeap";
                case 97:
                    return "isUserRunning";
                case 98:
                    return "setPackageScreenCompatMode";
                case 99:
                    return "switchUser";
                case 100:
                    return "removeTask";
                case 101:
                    return "registerProcessObserver";
                case 102:
                    return "unregisterProcessObserver";
                case 103:
                    return "isIntentSenderTargetedToPackage";
                case 104:
                    return "updatePersistentConfiguration";
                case 105:
                    return "getProcessPss";
                case 106:
                    return "showBootMessage";
                case 107:
                    return "killAllBackgroundProcesses";
                case 108:
                    return "getContentProviderExternal";
                case 109:
                    return "removeContentProviderExternal";
                case 110:
                    return "removeContentProviderExternalAsUser";
                case 111:
                    return "getMyMemoryState";
                case 112:
                    return "killProcessesBelowForeground";
                case 113:
                    return "getCurrentUser";
                case 114:
                    return "getLaunchedFromUid";
                case 115:
                    return "unstableProviderDied";
                case 116:
                    return "isIntentSenderAnActivity";
                case 117:
                    return "isIntentSenderAForegroundService";
                case 118:
                    return "isIntentSenderABroadcast";
                case 119:
                    return "startActivityAsUser";
                case 120:
                    return "stopUser";
                case 121:
                    return "registerUserSwitchObserver";
                case 122:
                    return "unregisterUserSwitchObserver";
                case 123:
                    return "getRunningUserIds";
                case 124:
                    return "requestSystemServerHeapDump";
                case 125:
                    return "requestBugReport";
                case 126:
                    return "requestTelephonyBugReport";
                case 127:
                    return "requestWifiBugReport";
                case 128:
                    return "getIntentForIntentSender";
                case 129:
                    return "getLaunchedFromPackage";
                case 130:
                    return "killUid";
                case 131:
                    return "setUserIsMonkey";
                case 132:
                    return "hang";
                case 133:
                    return "getAllStackInfos";
                case 134:
                    return "moveTaskToStack";
                case 135:
                    return "resizeStack";
                case 136:
                    return "setFocusedStack";
                case 137:
                    return "getFocusedStackInfo";
                case 138:
                    return "restart";
                case 139:
                    return "performIdleMaintenance";
                case 140:
                    return "appNotRespondingViaProvider";
                case 141:
                    return "getTaskBounds";
                case 142:
                    return "setProcessMemoryTrimLevel";
                case 143:
                    return "getTagForIntentSender";
                case 144:
                    return "startUserInBackground";
                case 145:
                    return "isInLockTaskMode";
                case 146:
                    return "startRecentsActivity";
                case 147:
                    return "cancelRecentsAnimation";
                case 148:
                    return "startActivityFromRecents";
                case 149:
                    return "startSystemLockTaskMode";
                case 150:
                    return "isTopOfTask";
                case 151:
                    return "bootAnimationComplete";
                case 152:
                    return "checkPermissionWithToken";
                case 153:
                    return "registerTaskStackListener";
                case 154:
                    return "unregisterTaskStackListener";
                case 155:
                    return "notifyCleartextNetwork";
                case 156:
                    return "setTaskResizeable";
                case 157:
                    return "resizeTask";
                case 158:
                    return "getLockTaskModeState";
                case 159:
                    return "setDumpHeapDebugLimit";
                case 160:
                    return "dumpHeapFinished";
                case 161:
                    return "updateLockTaskPackages";
                case 162:
                    return "noteAlarmStart";
                case 163:
                    return "noteAlarmFinish";
                case 164:
                    return "getPackageProcessState";
                case 165:
                    return "updateDeviceOwner";
                case 166:
                    return "startBinderTracking";
                case 167:
                    return "stopBinderTrackingAndDump";
                case 168:
                    return "positionTaskInStack";
                case 169:
                    return "suppressResizeConfigChanges";
                case 170:
                    return "moveTopActivityToPinnedStack";
                case 171:
                    return "isAppStartModeDisabled";
                case 172:
                    return "unlockUser";
                case 173:
                    return "killPackageDependents";
                case 174:
                    return "resizeDockedStack";
                case 175:
                    return "removeStack";
                case 176:
                    return "makePackageIdle";
                case 177:
                    return "getMemoryTrimLevel";
                case 178:
                    return "isVrModePackageEnabled";
                case 179:
                    return "notifyLockedProfile";
                case 180:
                    return "startConfirmDeviceCredentialIntent";
                case 181:
                    return "sendIdleJobTrigger";
                case 182:
                    return "sendIntentSender";
                case 183:
                    return "isBackgroundRestricted";
                case 184:
                    return "setRenderThread";
                case 185:
                    return "setHasTopUi";
                case 186:
                    return "restartUserInBackground";
                case 187:
                    return "cancelTaskWindowTransition";
                case 188:
                    return "getTaskSnapshot";
                case 189:
                    return "scheduleApplicationInfoChanged";
                case 190:
                    return "setPersistentVrThread";
                case 191:
                    return "waitForNetworkStateUpdate";
                case 192:
                    return "backgroundWhitelistUid";
                case 193:
                    return "startUserInBackgroundWithListener";
                case 194:
                    return "startDelegateShellPermissionIdentity";
                case 195:
                    return "stopDelegateShellPermissionIdentity";
                case 196:
                    return "getLifeMonitor";
                case 197:
                    return "startUserInForegroundWithListener";
                default:
                    return null;
            }
        }

        @Override // android.p007os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.p007os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            Bundle _arg9;
            Intent _arg0;
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg02 = data.readString();
                    ParcelFileDescriptor _result = openContentUri(_arg02);
                    reply.writeNoException();
                    if (_result != null) {
                        reply.writeInt(1);
                        _result.writeToParcel(reply, 1);
                        return true;
                    }
                    reply.writeInt(0);
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    IUidObserver _arg03 = IUidObserver.Stub.asInterface(data.readStrongBinder());
                    int _arg1 = data.readInt();
                    int _arg2 = data.readInt();
                    String _arg3 = data.readString();
                    registerUidObserver(_arg03, _arg1, _arg2, _arg3);
                    reply.writeNoException();
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    IUidObserver _arg04 = IUidObserver.Stub.asInterface(data.readStrongBinder());
                    unregisterUidObserver(_arg04);
                    reply.writeNoException();
                    return true;
                case 4:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg05 = data.readInt();
                    String _arg12 = data.readString();
                    boolean isUidActive = isUidActive(_arg05, _arg12);
                    reply.writeNoException();
                    reply.writeInt(isUidActive ? 1 : 0);
                    return true;
                case 5:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg06 = data.readInt();
                    String _arg13 = data.readString();
                    int _result2 = getUidProcessState(_arg06, _arg13);
                    reply.writeNoException();
                    reply.writeInt(_result2);
                    return true;
                case 6:
                    data.enforceInterface(DESCRIPTOR);
                    IBinder _arg07 = data.readStrongBinder();
                    ApplicationErrorReport.ParcelableCrashInfo _arg14 = data.readInt() != 0 ? ApplicationErrorReport.ParcelableCrashInfo.CREATOR.createFromParcel(data) : null;
                    handleApplicationCrash(_arg07, _arg14);
                    reply.writeNoException();
                    return true;
                case 7:
                    data.enforceInterface(DESCRIPTOR);
                    IApplicationThread _arg08 = IApplicationThread.Stub.asInterface(data.readStrongBinder());
                    String _arg15 = data.readString();
                    Intent _arg22 = data.readInt() != 0 ? Intent.CREATOR.createFromParcel(data) : null;
                    String _arg32 = data.readString();
                    IBinder _arg4 = data.readStrongBinder();
                    String _arg5 = data.readString();
                    int _arg6 = data.readInt();
                    int _arg7 = data.readInt();
                    ProfilerInfo _arg8 = data.readInt() != 0 ? ProfilerInfo.CREATOR.createFromParcel(data) : null;
                    Bundle _arg92 = data.readInt() != 0 ? Bundle.CREATOR.createFromParcel(data) : null;
                    int _result3 = startActivity(_arg08, _arg15, _arg22, _arg32, _arg4, _arg5, _arg6, _arg7, _arg8, _arg92);
                    reply.writeNoException();
                    reply.writeInt(_result3);
                    return true;
                case 8:
                    data.enforceInterface(DESCRIPTOR);
                    unhandledBack();
                    reply.writeNoException();
                    return true;
                case 9:
                    data.enforceInterface(DESCRIPTOR);
                    IBinder _arg09 = data.readStrongBinder();
                    int _arg16 = data.readInt();
                    Intent _arg23 = data.readInt() != 0 ? Intent.CREATOR.createFromParcel(data) : null;
                    int _arg33 = data.readInt();
                    boolean finishActivity = finishActivity(_arg09, _arg16, _arg23, _arg33);
                    reply.writeNoException();
                    reply.writeInt(finishActivity ? 1 : 0);
                    return true;
                case 10:
                    data.enforceInterface(DESCRIPTOR);
                    IApplicationThread _arg010 = IApplicationThread.Stub.asInterface(data.readStrongBinder());
                    String _arg17 = data.readString();
                    IIntentReceiver _arg24 = IIntentReceiver.Stub.asInterface(data.readStrongBinder());
                    IntentFilter _arg34 = data.readInt() != 0 ? IntentFilter.CREATOR.createFromParcel(data) : null;
                    String _arg42 = data.readString();
                    int _arg52 = data.readInt();
                    int _arg62 = data.readInt();
                    Intent _result4 = registerReceiver(_arg010, _arg17, _arg24, _arg34, _arg42, _arg52, _arg62);
                    reply.writeNoException();
                    if (_result4 != null) {
                        reply.writeInt(1);
                        _result4.writeToParcel(reply, 1);
                        return true;
                    }
                    reply.writeInt(0);
                    return true;
                case 11:
                    data.enforceInterface(DESCRIPTOR);
                    IIntentReceiver _arg011 = IIntentReceiver.Stub.asInterface(data.readStrongBinder());
                    unregisterReceiver(_arg011);
                    reply.writeNoException();
                    return true;
                case 12:
                    data.enforceInterface(DESCRIPTOR);
                    IApplicationThread _arg012 = IApplicationThread.Stub.asInterface(data.readStrongBinder());
                    Intent _arg18 = data.readInt() != 0 ? Intent.CREATOR.createFromParcel(data) : null;
                    String _arg25 = data.readString();
                    IIntentReceiver _arg35 = IIntentReceiver.Stub.asInterface(data.readStrongBinder());
                    int _arg43 = data.readInt();
                    String _arg53 = data.readString();
                    Bundle _arg63 = data.readInt() != 0 ? Bundle.CREATOR.createFromParcel(data) : null;
                    String[] _arg72 = data.createStringArray();
                    int _arg82 = data.readInt();
                    Bundle _arg93 = data.readInt() != 0 ? Bundle.CREATOR.createFromParcel(data) : null;
                    boolean _arg10 = data.readInt() != 0;
                    boolean _arg11 = data.readInt() != 0;
                    int _arg122 = data.readInt();
                    int _result5 = broadcastIntent(_arg012, _arg18, _arg25, _arg35, _arg43, _arg53, _arg63, _arg72, _arg82, _arg93, _arg10, _arg11, _arg122);
                    reply.writeNoException();
                    reply.writeInt(_result5);
                    return true;
                case 13:
                    data.enforceInterface(DESCRIPTOR);
                    IApplicationThread _arg013 = IApplicationThread.Stub.asInterface(data.readStrongBinder());
                    Intent _arg19 = data.readInt() != 0 ? Intent.CREATOR.createFromParcel(data) : null;
                    int _arg26 = data.readInt();
                    unbroadcastIntent(_arg013, _arg19, _arg26);
                    reply.writeNoException();
                    return true;
                case 14:
                    data.enforceInterface(DESCRIPTOR);
                    IBinder _arg014 = data.readStrongBinder();
                    int _arg110 = data.readInt();
                    String _arg27 = data.readString();
                    Bundle _arg36 = data.readInt() != 0 ? Bundle.CREATOR.createFromParcel(data) : null;
                    boolean _arg44 = data.readInt() != 0;
                    int _arg54 = data.readInt();
                    finishReceiver(_arg014, _arg110, _arg27, _arg36, _arg44, _arg54);
                    return true;
                case 15:
                    data.enforceInterface(DESCRIPTOR);
                    IApplicationThread _arg015 = IApplicationThread.Stub.asInterface(data.readStrongBinder());
                    long _arg111 = data.readLong();
                    attachApplication(_arg015, _arg111);
                    reply.writeNoException();
                    return true;
                case 16:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg016 = data.readInt();
                    List<ActivityManager.RunningTaskInfo> _result6 = getTasks(_arg016);
                    reply.writeNoException();
                    reply.writeTypedList(_result6);
                    return true;
                case 17:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg017 = data.readInt();
                    int _arg112 = data.readInt();
                    int _arg28 = data.readInt();
                    List<ActivityManager.RunningTaskInfo> _result7 = getFilteredTasks(_arg017, _arg112, _arg28);
                    reply.writeNoException();
                    reply.writeTypedList(_result7);
                    return true;
                case 18:
                    data.enforceInterface(DESCRIPTOR);
                    IApplicationThread _arg018 = IApplicationThread.Stub.asInterface(data.readStrongBinder());
                    String _arg113 = data.readString();
                    int _arg29 = data.readInt();
                    int _arg37 = data.readInt();
                    Bundle _arg45 = data.readInt() != 0 ? Bundle.CREATOR.createFromParcel(data) : null;
                    moveTaskToFront(_arg018, _arg113, _arg29, _arg37, _arg45);
                    reply.writeNoException();
                    return true;
                case 19:
                    data.enforceInterface(DESCRIPTOR);
                    IBinder _arg019 = data.readStrongBinder();
                    boolean _arg114 = data.readInt() != 0;
                    int _result8 = getTaskForActivity(_arg019, _arg114);
                    reply.writeNoException();
                    reply.writeInt(_result8);
                    return true;
                case 20:
                    data.enforceInterface(DESCRIPTOR);
                    IApplicationThread _arg020 = IApplicationThread.Stub.asInterface(data.readStrongBinder());
                    String _arg115 = data.readString();
                    String _arg210 = data.readString();
                    int _arg38 = data.readInt();
                    boolean _arg46 = data.readInt() != 0;
                    ContentProviderHolder _result9 = getContentProvider(_arg020, _arg115, _arg210, _arg38, _arg46);
                    reply.writeNoException();
                    if (_result9 != null) {
                        reply.writeInt(1);
                        _result9.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 21:
                    data.enforceInterface(DESCRIPTOR);
                    IApplicationThread _arg021 = IApplicationThread.Stub.asInterface(data.readStrongBinder());
                    List<ContentProviderHolder> _arg116 = data.createTypedArrayList(ContentProviderHolder.CREATOR);
                    publishContentProviders(_arg021, _arg116);
                    reply.writeNoException();
                    return true;
                case 22:
                    data.enforceInterface(DESCRIPTOR);
                    IBinder _arg022 = data.readStrongBinder();
                    int _arg117 = data.readInt();
                    int _arg211 = data.readInt();
                    boolean refContentProvider = refContentProvider(_arg022, _arg117, _arg211);
                    reply.writeNoException();
                    reply.writeInt(refContentProvider ? 1 : 0);
                    return true;
                case 23:
                    data.enforceInterface(DESCRIPTOR);
                    ComponentName _arg023 = data.readInt() != 0 ? ComponentName.CREATOR.createFromParcel(data) : null;
                    PendingIntent _result10 = getRunningServiceControlPanel(_arg023);
                    reply.writeNoException();
                    if (_result10 != null) {
                        reply.writeInt(1);
                        _result10.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 24:
                    data.enforceInterface(DESCRIPTOR);
                    IApplicationThread _arg024 = IApplicationThread.Stub.asInterface(data.readStrongBinder());
                    Intent _arg118 = data.readInt() != 0 ? Intent.CREATOR.createFromParcel(data) : null;
                    String _arg212 = data.readString();
                    boolean _arg39 = data.readInt() != 0;
                    String _arg47 = data.readString();
                    int _arg55 = data.readInt();
                    ComponentName _result11 = startService(_arg024, _arg118, _arg212, _arg39, _arg47, _arg55);
                    reply.writeNoException();
                    if (_result11 != null) {
                        reply.writeInt(1);
                        _result11.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 25:
                    data.enforceInterface(DESCRIPTOR);
                    IApplicationThread _arg025 = IApplicationThread.Stub.asInterface(data.readStrongBinder());
                    Intent _arg119 = data.readInt() != 0 ? Intent.CREATOR.createFromParcel(data) : null;
                    String _arg213 = data.readString();
                    int _arg310 = data.readInt();
                    int _result12 = stopService(_arg025, _arg119, _arg213, _arg310);
                    reply.writeNoException();
                    reply.writeInt(_result12);
                    return true;
                case 26:
                    data.enforceInterface(DESCRIPTOR);
                    IApplicationThread _arg026 = IApplicationThread.Stub.asInterface(data.readStrongBinder());
                    IBinder _arg120 = data.readStrongBinder();
                    Intent _arg214 = data.readInt() != 0 ? Intent.CREATOR.createFromParcel(data) : null;
                    String _arg311 = data.readString();
                    IServiceConnection _arg48 = IServiceConnection.Stub.asInterface(data.readStrongBinder());
                    int _arg56 = data.readInt();
                    String _arg64 = data.readString();
                    int _arg73 = data.readInt();
                    int _result13 = bindService(_arg026, _arg120, _arg214, _arg311, _arg48, _arg56, _arg64, _arg73);
                    reply.writeNoException();
                    reply.writeInt(_result13);
                    return true;
                case 27:
                    data.enforceInterface(DESCRIPTOR);
                    IApplicationThread _arg027 = IApplicationThread.Stub.asInterface(data.readStrongBinder());
                    IBinder _arg121 = data.readStrongBinder();
                    Intent _arg215 = data.readInt() != 0 ? Intent.CREATOR.createFromParcel(data) : null;
                    String _arg312 = data.readString();
                    IServiceConnection _arg49 = IServiceConnection.Stub.asInterface(data.readStrongBinder());
                    int _arg57 = data.readInt();
                    String _arg65 = data.readString();
                    String _arg74 = data.readString();
                    int _arg83 = data.readInt();
                    int _result14 = bindIsolatedService(_arg027, _arg121, _arg215, _arg312, _arg49, _arg57, _arg65, _arg74, _arg83);
                    reply.writeNoException();
                    reply.writeInt(_result14);
                    return true;
                case 28:
                    data.enforceInterface(DESCRIPTOR);
                    IServiceConnection _arg028 = IServiceConnection.Stub.asInterface(data.readStrongBinder());
                    int _arg123 = data.readInt();
                    int _arg216 = data.readInt();
                    updateServiceGroup(_arg028, _arg123, _arg216);
                    reply.writeNoException();
                    return true;
                case 29:
                    data.enforceInterface(DESCRIPTOR);
                    IServiceConnection _arg029 = IServiceConnection.Stub.asInterface(data.readStrongBinder());
                    boolean unbindService = unbindService(_arg029);
                    reply.writeNoException();
                    reply.writeInt(unbindService ? 1 : 0);
                    return true;
                case 30:
                    data.enforceInterface(DESCRIPTOR);
                    IBinder _arg030 = data.readStrongBinder();
                    Intent _arg124 = data.readInt() != 0 ? Intent.CREATOR.createFromParcel(data) : null;
                    IBinder _arg217 = data.readStrongBinder();
                    publishService(_arg030, _arg124, _arg217);
                    reply.writeNoException();
                    return true;
                case 31:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg031 = data.readString();
                    boolean _arg125 = data.readInt() != 0;
                    boolean _arg218 = data.readInt() != 0;
                    setDebugApp(_arg031, _arg125, _arg218);
                    reply.writeNoException();
                    return true;
                case 32:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg032 = data.readString();
                    String _arg126 = data.readString();
                    setAgentApp(_arg032, _arg126);
                    reply.writeNoException();
                    return true;
                case 33:
                    data.enforceInterface(DESCRIPTOR);
                    boolean _arg033 = data.readInt() != 0;
                    setAlwaysFinish(_arg033);
                    reply.writeNoException();
                    return true;
                case 34:
                    data.enforceInterface(DESCRIPTOR);
                    ComponentName _arg034 = data.readInt() != 0 ? ComponentName.CREATOR.createFromParcel(data) : null;
                    String _arg127 = data.readString();
                    int _arg219 = data.readInt();
                    Bundle _arg313 = data.readInt() != 0 ? Bundle.CREATOR.createFromParcel(data) : null;
                    IInstrumentationWatcher _arg410 = IInstrumentationWatcher.Stub.asInterface(data.readStrongBinder());
                    IUiAutomationConnection _arg58 = IUiAutomationConnection.Stub.asInterface(data.readStrongBinder());
                    int _arg66 = data.readInt();
                    String _arg75 = data.readString();
                    boolean startInstrumentation = startInstrumentation(_arg034, _arg127, _arg219, _arg313, _arg410, _arg58, _arg66, _arg75);
                    reply.writeNoException();
                    reply.writeInt(startInstrumentation ? 1 : 0);
                    return true;
                case 35:
                    data.enforceInterface(DESCRIPTOR);
                    IApplicationThread _arg035 = IApplicationThread.Stub.asInterface(data.readStrongBinder());
                    Bundle _arg128 = data.readInt() != 0 ? Bundle.CREATOR.createFromParcel(data) : null;
                    addInstrumentationResults(_arg035, _arg128);
                    reply.writeNoException();
                    return true;
                case 36:
                    data.enforceInterface(DESCRIPTOR);
                    IApplicationThread _arg036 = IApplicationThread.Stub.asInterface(data.readStrongBinder());
                    int _arg129 = data.readInt();
                    Bundle _arg220 = data.readInt() != 0 ? Bundle.CREATOR.createFromParcel(data) : null;
                    finishInstrumentation(_arg036, _arg129, _arg220);
                    reply.writeNoException();
                    return true;
                case 37:
                    data.enforceInterface(DESCRIPTOR);
                    Configuration _result15 = getConfiguration();
                    reply.writeNoException();
                    if (_result15 != null) {
                        reply.writeInt(1);
                        _result15.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 38:
                    data.enforceInterface(DESCRIPTOR);
                    Configuration _arg037 = data.readInt() != 0 ? Configuration.CREATOR.createFromParcel(data) : null;
                    boolean updateConfiguration = updateConfiguration(_arg037);
                    reply.writeNoException();
                    reply.writeInt(updateConfiguration ? 1 : 0);
                    return true;
                case 39:
                    data.enforceInterface(DESCRIPTOR);
                    ComponentName _arg038 = data.readInt() != 0 ? ComponentName.CREATOR.createFromParcel(data) : null;
                    IBinder _arg130 = data.readStrongBinder();
                    int _arg221 = data.readInt();
                    boolean stopServiceToken = stopServiceToken(_arg038, _arg130, _arg221);
                    reply.writeNoException();
                    reply.writeInt(stopServiceToken ? 1 : 0);
                    return true;
                case 40:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg039 = data.readInt();
                    setProcessLimit(_arg039);
                    reply.writeNoException();
                    return true;
                case 41:
                    data.enforceInterface(DESCRIPTOR);
                    int _result16 = getProcessLimit();
                    reply.writeNoException();
                    reply.writeInt(_result16);
                    return true;
                case 42:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg040 = data.readString();
                    int _arg131 = data.readInt();
                    int _arg222 = data.readInt();
                    int _result17 = checkPermission(_arg040, _arg131, _arg222);
                    reply.writeNoException();
                    reply.writeInt(_result17);
                    return true;
                case 43:
                    data.enforceInterface(DESCRIPTOR);
                    Uri _arg041 = data.readInt() != 0 ? Uri.CREATOR.createFromParcel(data) : null;
                    int _arg132 = data.readInt();
                    int _arg223 = data.readInt();
                    int _arg314 = data.readInt();
                    int _arg411 = data.readInt();
                    IBinder _arg59 = data.readStrongBinder();
                    int _result18 = checkUriPermission(_arg041, _arg132, _arg223, _arg314, _arg411, _arg59);
                    reply.writeNoException();
                    reply.writeInt(_result18);
                    return true;
                case 44:
                    data.enforceInterface(DESCRIPTOR);
                    IApplicationThread _arg042 = IApplicationThread.Stub.asInterface(data.readStrongBinder());
                    String _arg133 = data.readString();
                    Uri _arg224 = data.readInt() != 0 ? Uri.CREATOR.createFromParcel(data) : null;
                    int _arg315 = data.readInt();
                    int _arg412 = data.readInt();
                    grantUriPermission(_arg042, _arg133, _arg224, _arg315, _arg412);
                    reply.writeNoException();
                    return true;
                case 45:
                    data.enforceInterface(DESCRIPTOR);
                    IApplicationThread _arg043 = IApplicationThread.Stub.asInterface(data.readStrongBinder());
                    String _arg134 = data.readString();
                    Uri _arg225 = data.readInt() != 0 ? Uri.CREATOR.createFromParcel(data) : null;
                    int _arg316 = data.readInt();
                    int _arg413 = data.readInt();
                    revokeUriPermission(_arg043, _arg134, _arg225, _arg316, _arg413);
                    reply.writeNoException();
                    return true;
                case 46:
                    data.enforceInterface(DESCRIPTOR);
                    IActivityController _arg044 = IActivityController.Stub.asInterface(data.readStrongBinder());
                    boolean _arg135 = data.readInt() != 0;
                    setActivityController(_arg044, _arg135);
                    reply.writeNoException();
                    return true;
                case 47:
                    data.enforceInterface(DESCRIPTOR);
                    IApplicationThread _arg045 = IApplicationThread.Stub.asInterface(data.readStrongBinder());
                    boolean _arg136 = data.readInt() != 0;
                    showWaitingForDebugger(_arg045, _arg136);
                    reply.writeNoException();
                    return true;
                case 48:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg046 = data.readInt();
                    signalPersistentProcesses(_arg046);
                    reply.writeNoException();
                    return true;
                case 49:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg047 = data.readInt();
                    int _arg137 = data.readInt();
                    int _arg226 = data.readInt();
                    ParceledListSlice _result19 = getRecentTasks(_arg047, _arg137, _arg226);
                    reply.writeNoException();
                    if (_result19 != null) {
                        reply.writeInt(1);
                        _result19.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 50:
                    data.enforceInterface(DESCRIPTOR);
                    IBinder _arg048 = data.readStrongBinder();
                    int _arg138 = data.readInt();
                    int _arg227 = data.readInt();
                    int _arg317 = data.readInt();
                    serviceDoneExecuting(_arg048, _arg138, _arg227, _arg317);
                    return true;
                case 51:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg049 = data.readInt();
                    String _arg139 = data.readString();
                    IBinder _arg228 = data.readStrongBinder();
                    String _arg318 = data.readString();
                    int _arg414 = data.readInt();
                    Intent[] _arg510 = (Intent[]) data.createTypedArray(Intent.CREATOR);
                    String[] _arg67 = data.createStringArray();
                    int _arg76 = data.readInt();
                    Bundle _arg84 = data.readInt() != 0 ? Bundle.CREATOR.createFromParcel(data) : null;
                    int _arg94 = data.readInt();
                    IIntentSender _result20 = getIntentSender(_arg049, _arg139, _arg228, _arg318, _arg414, _arg510, _arg67, _arg76, _arg84, _arg94);
                    reply.writeNoException();
                    reply.writeStrongBinder(_result20 != null ? _result20.asBinder() : null);
                    return true;
                case 52:
                    data.enforceInterface(DESCRIPTOR);
                    IIntentSender _arg050 = IIntentSender.Stub.asInterface(data.readStrongBinder());
                    cancelIntentSender(_arg050);
                    reply.writeNoException();
                    return true;
                case 53:
                    data.enforceInterface(DESCRIPTOR);
                    IIntentSender _arg051 = IIntentSender.Stub.asInterface(data.readStrongBinder());
                    String _result21 = getPackageForIntentSender(_arg051);
                    reply.writeNoException();
                    reply.writeString(_result21);
                    return true;
                case 54:
                    data.enforceInterface(DESCRIPTOR);
                    IIntentSender _arg052 = IIntentSender.Stub.asInterface(data.readStrongBinder());
                    IResultReceiver _arg140 = IResultReceiver.Stub.asInterface(data.readStrongBinder());
                    registerIntentSenderCancelListener(_arg052, _arg140);
                    reply.writeNoException();
                    return true;
                case 55:
                    data.enforceInterface(DESCRIPTOR);
                    IIntentSender _arg053 = IIntentSender.Stub.asInterface(data.readStrongBinder());
                    IResultReceiver _arg141 = IResultReceiver.Stub.asInterface(data.readStrongBinder());
                    unregisterIntentSenderCancelListener(_arg053, _arg141);
                    reply.writeNoException();
                    return true;
                case 56:
                    data.enforceInterface(DESCRIPTOR);
                    enterSafeMode();
                    reply.writeNoException();
                    return true;
                case 57:
                    data.enforceInterface(DESCRIPTOR);
                    IIntentSender _arg054 = IIntentSender.Stub.asInterface(data.readStrongBinder());
                    WorkSource _arg142 = data.readInt() != 0 ? WorkSource.CREATOR.createFromParcel(data) : null;
                    int _arg229 = data.readInt();
                    String _arg319 = data.readString();
                    String _arg415 = data.readString();
                    noteWakeupAlarm(_arg054, _arg142, _arg229, _arg319, _arg415);
                    reply.writeNoException();
                    return true;
                case 58:
                    data.enforceInterface(DESCRIPTOR);
                    IBinder _arg055 = data.readStrongBinder();
                    boolean _arg143 = data.readInt() != 0;
                    removeContentProvider(_arg055, _arg143);
                    return true;
                case 59:
                    data.enforceInterface(DESCRIPTOR);
                    IBinder _arg056 = data.readStrongBinder();
                    int _arg144 = data.readInt();
                    setRequestedOrientation(_arg056, _arg144);
                    reply.writeNoException();
                    return true;
                case 60:
                    data.enforceInterface(DESCRIPTOR);
                    IBinder _arg057 = data.readStrongBinder();
                    Intent _arg145 = data.readInt() != 0 ? Intent.CREATOR.createFromParcel(data) : null;
                    Intent _arg146 = _arg145;
                    boolean _arg230 = data.readInt() != 0;
                    unbindFinished(_arg057, _arg146, _arg230);
                    reply.writeNoException();
                    return true;
                case 61:
                    data.enforceInterface(DESCRIPTOR);
                    IBinder _arg058 = data.readStrongBinder();
                    int _arg147 = data.readInt();
                    boolean _arg231 = data.readInt() != 0;
                    String _arg320 = data.readString();
                    setProcessImportant(_arg058, _arg147, _arg231, _arg320);
                    reply.writeNoException();
                    return true;
                case 62:
                    data.enforceInterface(DESCRIPTOR);
                    ComponentName _arg059 = data.readInt() != 0 ? ComponentName.CREATOR.createFromParcel(data) : null;
                    IBinder _arg148 = data.readStrongBinder();
                    int _arg232 = data.readInt();
                    Notification _arg321 = data.readInt() != 0 ? Notification.CREATOR.createFromParcel(data) : null;
                    int _arg416 = data.readInt();
                    int _arg511 = data.readInt();
                    setServiceForeground(_arg059, _arg148, _arg232, _arg321, _arg416, _arg511);
                    reply.writeNoException();
                    return true;
                case 63:
                    data.enforceInterface(DESCRIPTOR);
                    ComponentName _arg060 = data.readInt() != 0 ? ComponentName.CREATOR.createFromParcel(data) : null;
                    IBinder _arg149 = data.readStrongBinder();
                    int _result22 = getForegroundServiceType(_arg060, _arg149);
                    reply.writeNoException();
                    reply.writeInt(_result22);
                    return true;
                case 64:
                    data.enforceInterface(DESCRIPTOR);
                    IBinder _arg061 = data.readStrongBinder();
                    boolean _arg150 = data.readInt() != 0;
                    boolean moveActivityTaskToBack = moveActivityTaskToBack(_arg061, _arg150);
                    reply.writeNoException();
                    reply.writeInt(moveActivityTaskToBack ? 1 : 0);
                    return true;
                case 65:
                    data.enforceInterface(DESCRIPTOR);
                    ActivityManager.MemoryInfo _arg062 = new ActivityManager.MemoryInfo();
                    getMemoryInfo(_arg062);
                    reply.writeNoException();
                    reply.writeInt(1);
                    _arg062.writeToParcel(reply, 1);
                    return true;
                case 66:
                    data.enforceInterface(DESCRIPTOR);
                    List<ActivityManager.ProcessErrorStateInfo> _result23 = getProcessesInErrorState();
                    reply.writeNoException();
                    reply.writeTypedList(_result23);
                    return true;
                case 67:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg063 = data.readString();
                    boolean _arg151 = data.readInt() != 0;
                    IPackageDataObserver _arg233 = IPackageDataObserver.Stub.asInterface(data.readStrongBinder());
                    int _arg322 = data.readInt();
                    boolean clearApplicationUserData = clearApplicationUserData(_arg063, _arg151, _arg233, _arg322);
                    reply.writeNoException();
                    reply.writeInt(clearApplicationUserData ? 1 : 0);
                    return true;
                case 68:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg064 = data.readString();
                    int _arg152 = data.readInt();
                    forceStopPackage(_arg064, _arg152);
                    reply.writeNoException();
                    return true;
                case 69:
                    data.enforceInterface(DESCRIPTOR);
                    int[] _arg065 = data.createIntArray();
                    String _arg153 = data.readString();
                    boolean _arg234 = data.readInt() != 0;
                    boolean killPids = killPids(_arg065, _arg153, _arg234);
                    reply.writeNoException();
                    reply.writeInt(killPids ? 1 : 0);
                    return true;
                case 70:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg066 = data.readInt();
                    int _arg154 = data.readInt();
                    List<ActivityManager.RunningServiceInfo> _result24 = getServices(_arg066, _arg154);
                    reply.writeNoException();
                    reply.writeTypedList(_result24);
                    return true;
                case 71:
                    data.enforceInterface(DESCRIPTOR);
                    List<ActivityManager.RunningAppProcessInfo> _result25 = getRunningAppProcesses();
                    reply.writeNoException();
                    reply.writeTypedList(_result25);
                    return true;
                case 72:
                    data.enforceInterface(DESCRIPTOR);
                    Intent _arg067 = data.readInt() != 0 ? Intent.CREATOR.createFromParcel(data) : null;
                    String _arg155 = data.readString();
                    String _arg235 = data.readString();
                    IBinder _result26 = peekService(_arg067, _arg155, _arg235);
                    reply.writeNoException();
                    reply.writeStrongBinder(_result26);
                    return true;
                case 73:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg068 = data.readString();
                    int _arg156 = data.readInt();
                    boolean _arg236 = data.readInt() != 0;
                    ProfilerInfo _arg323 = data.readInt() != 0 ? ProfilerInfo.CREATOR.createFromParcel(data) : null;
                    int _arg417 = data.readInt();
                    boolean profileControl = profileControl(_arg068, _arg156, _arg236, _arg323, _arg417);
                    reply.writeNoException();
                    reply.writeInt(profileControl ? 1 : 0);
                    return true;
                case 74:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg069 = data.readInt();
                    boolean shutdown = shutdown(_arg069);
                    reply.writeNoException();
                    reply.writeInt(shutdown ? 1 : 0);
                    return true;
                case 75:
                    data.enforceInterface(DESCRIPTOR);
                    stopAppSwitches();
                    reply.writeNoException();
                    return true;
                case 76:
                    data.enforceInterface(DESCRIPTOR);
                    resumeAppSwitches();
                    reply.writeNoException();
                    return true;
                case 77:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg070 = data.readString();
                    int _arg157 = data.readInt();
                    int _arg237 = data.readInt();
                    boolean bindBackupAgent = bindBackupAgent(_arg070, _arg157, _arg237);
                    reply.writeNoException();
                    reply.writeInt(bindBackupAgent ? 1 : 0);
                    return true;
                case 78:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg071 = data.readString();
                    IBinder _arg158 = data.readStrongBinder();
                    int _arg238 = data.readInt();
                    backupAgentCreated(_arg071, _arg158, _arg238);
                    reply.writeNoException();
                    return true;
                case 79:
                    data.enforceInterface(DESCRIPTOR);
                    ApplicationInfo _arg072 = data.readInt() != 0 ? ApplicationInfo.CREATOR.createFromParcel(data) : null;
                    unbindBackupAgent(_arg072);
                    reply.writeNoException();
                    return true;
                case 80:
                    data.enforceInterface(DESCRIPTOR);
                    IIntentSender _arg073 = IIntentSender.Stub.asInterface(data.readStrongBinder());
                    int _result27 = getUidForIntentSender(_arg073);
                    reply.writeNoException();
                    reply.writeInt(_result27);
                    return true;
                case 81:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg074 = data.readInt();
                    int _arg159 = data.readInt();
                    int _arg239 = data.readInt();
                    boolean _arg324 = data.readInt() != 0;
                    boolean _arg418 = data.readInt() != 0;
                    String _arg512 = data.readString();
                    String _arg68 = data.readString();
                    int _result28 = handleIncomingUser(_arg074, _arg159, _arg239, _arg324, _arg418, _arg512, _arg68);
                    reply.writeNoException();
                    reply.writeInt(_result28);
                    return true;
                case 82:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg075 = data.readString();
                    addPackageDependency(_arg075);
                    reply.writeNoException();
                    return true;
                case 83:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg076 = data.readString();
                    int _arg160 = data.readInt();
                    int _arg240 = data.readInt();
                    String _arg325 = data.readString();
                    killApplication(_arg076, _arg160, _arg240, _arg325);
                    reply.writeNoException();
                    return true;
                case 84:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg077 = data.readString();
                    closeSystemDialogs(_arg077);
                    reply.writeNoException();
                    return true;
                case 85:
                    data.enforceInterface(DESCRIPTOR);
                    int[] _arg078 = data.createIntArray();
                    Debug.MemoryInfo[] _result29 = getProcessMemoryInfo(_arg078);
                    reply.writeNoException();
                    reply.writeTypedArray(_result29, 1);
                    return true;
                case 86:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg079 = data.readString();
                    int _arg161 = data.readInt();
                    killApplicationProcess(_arg079, _arg161);
                    reply.writeNoException();
                    return true;
                case 87:
                    data.enforceInterface(DESCRIPTOR);
                    IBinder _arg080 = data.readStrongBinder();
                    String _arg162 = data.readString();
                    boolean _arg241 = data.readInt() != 0;
                    ApplicationErrorReport.ParcelableCrashInfo _arg326 = data.readInt() != 0 ? ApplicationErrorReport.ParcelableCrashInfo.CREATOR.createFromParcel(data) : null;
                    boolean handleApplicationWtf = handleApplicationWtf(_arg080, _arg162, _arg241, _arg326);
                    reply.writeNoException();
                    reply.writeInt(handleApplicationWtf ? 1 : 0);
                    return true;
                case 88:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg081 = data.readString();
                    int _arg163 = data.readInt();
                    killBackgroundProcesses(_arg081, _arg163);
                    reply.writeNoException();
                    return true;
                case 89:
                    data.enforceInterface(DESCRIPTOR);
                    boolean isUserAMonkey = isUserAMonkey();
                    reply.writeNoException();
                    reply.writeInt(isUserAMonkey ? 1 : 0);
                    return true;
                case 90:
                    data.enforceInterface(DESCRIPTOR);
                    List<ApplicationInfo> _result30 = getRunningExternalApplications();
                    reply.writeNoException();
                    reply.writeTypedList(_result30);
                    return true;
                case 91:
                    data.enforceInterface(DESCRIPTOR);
                    finishHeavyWeightApp();
                    reply.writeNoException();
                    return true;
                case 92:
                    data.enforceInterface(DESCRIPTOR);
                    IBinder _arg082 = data.readStrongBinder();
                    int _arg164 = data.readInt();
                    StrictMode.ViolationInfo _arg242 = data.readInt() != 0 ? StrictMode.ViolationInfo.CREATOR.createFromParcel(data) : null;
                    handleApplicationStrictModeViolation(_arg082, _arg164, _arg242);
                    reply.writeNoException();
                    return true;
                case 93:
                    data.enforceInterface(DESCRIPTOR);
                    boolean isTopActivityImmersive = isTopActivityImmersive();
                    reply.writeNoException();
                    reply.writeInt(isTopActivityImmersive ? 1 : 0);
                    return true;
                case 94:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg083 = data.readInt();
                    int _arg165 = data.readInt();
                    String _arg243 = data.readString();
                    int _arg327 = data.readInt();
                    String _arg419 = data.readString();
                    crashApplication(_arg083, _arg165, _arg243, _arg327, _arg419);
                    reply.writeNoException();
                    return true;
                case 95:
                    data.enforceInterface(DESCRIPTOR);
                    Uri _arg084 = data.readInt() != 0 ? Uri.CREATOR.createFromParcel(data) : null;
                    int _arg166 = data.readInt();
                    String _result31 = getProviderMimeType(_arg084, _arg166);
                    reply.writeNoException();
                    reply.writeString(_result31);
                    return true;
                case 96:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg085 = data.readString();
                    int _arg167 = data.readInt();
                    boolean _arg244 = data.readInt() != 0;
                    boolean _arg328 = data.readInt() != 0;
                    boolean _arg420 = data.readInt() != 0;
                    String _arg513 = data.readString();
                    ParcelFileDescriptor _arg69 = data.readInt() != 0 ? ParcelFileDescriptor.CREATOR.createFromParcel(data) : null;
                    RemoteCallback _arg77 = data.readInt() != 0 ? RemoteCallback.CREATOR.createFromParcel(data) : null;
                    boolean dumpHeap = dumpHeap(_arg085, _arg167, _arg244, _arg328, _arg420, _arg513, _arg69, _arg77);
                    reply.writeNoException();
                    reply.writeInt(dumpHeap ? 1 : 0);
                    return true;
                case 97:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg086 = data.readInt();
                    int _arg168 = data.readInt();
                    boolean isUserRunning = isUserRunning(_arg086, _arg168);
                    reply.writeNoException();
                    reply.writeInt(isUserRunning ? 1 : 0);
                    return true;
                case 98:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg087 = data.readString();
                    int _arg169 = data.readInt();
                    setPackageScreenCompatMode(_arg087, _arg169);
                    reply.writeNoException();
                    return true;
                case 99:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg088 = data.readInt();
                    boolean switchUser = switchUser(_arg088);
                    reply.writeNoException();
                    reply.writeInt(switchUser ? 1 : 0);
                    return true;
                case 100:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg089 = data.readInt();
                    boolean removeTask = removeTask(_arg089);
                    reply.writeNoException();
                    reply.writeInt(removeTask ? 1 : 0);
                    return true;
                case 101:
                    data.enforceInterface(DESCRIPTOR);
                    IProcessObserver _arg090 = IProcessObserver.Stub.asInterface(data.readStrongBinder());
                    registerProcessObserver(_arg090);
                    reply.writeNoException();
                    return true;
                case 102:
                    data.enforceInterface(DESCRIPTOR);
                    IProcessObserver _arg091 = IProcessObserver.Stub.asInterface(data.readStrongBinder());
                    unregisterProcessObserver(_arg091);
                    reply.writeNoException();
                    return true;
                case 103:
                    data.enforceInterface(DESCRIPTOR);
                    IIntentSender _arg092 = IIntentSender.Stub.asInterface(data.readStrongBinder());
                    boolean isIntentSenderTargetedToPackage = isIntentSenderTargetedToPackage(_arg092);
                    reply.writeNoException();
                    reply.writeInt(isIntentSenderTargetedToPackage ? 1 : 0);
                    return true;
                case 104:
                    data.enforceInterface(DESCRIPTOR);
                    Configuration _arg093 = data.readInt() != 0 ? Configuration.CREATOR.createFromParcel(data) : null;
                    updatePersistentConfiguration(_arg093);
                    reply.writeNoException();
                    return true;
                case 105:
                    data.enforceInterface(DESCRIPTOR);
                    int[] _arg094 = data.createIntArray();
                    long[] _result32 = getProcessPss(_arg094);
                    reply.writeNoException();
                    reply.writeLongArray(_result32);
                    return true;
                case 106:
                    data.enforceInterface(DESCRIPTOR);
                    CharSequence _arg095 = data.readInt() != 0 ? TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(data) : null;
                    CharSequence _arg096 = _arg095;
                    boolean _arg170 = data.readInt() != 0;
                    showBootMessage(_arg096, _arg170);
                    reply.writeNoException();
                    return true;
                case 107:
                    data.enforceInterface(DESCRIPTOR);
                    killAllBackgroundProcesses();
                    reply.writeNoException();
                    return true;
                case 108:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg097 = data.readString();
                    int _arg171 = data.readInt();
                    IBinder _arg245 = data.readStrongBinder();
                    String _arg329 = data.readString();
                    ContentProviderHolder _result33 = getContentProviderExternal(_arg097, _arg171, _arg245, _arg329);
                    reply.writeNoException();
                    if (_result33 != null) {
                        reply.writeInt(1);
                        _result33.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 109:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg098 = data.readString();
                    IBinder _arg172 = data.readStrongBinder();
                    removeContentProviderExternal(_arg098, _arg172);
                    reply.writeNoException();
                    return true;
                case 110:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg099 = data.readString();
                    IBinder _arg173 = data.readStrongBinder();
                    int _arg246 = data.readInt();
                    removeContentProviderExternalAsUser(_arg099, _arg173, _arg246);
                    reply.writeNoException();
                    return true;
                case 111:
                    data.enforceInterface(DESCRIPTOR);
                    ActivityManager.RunningAppProcessInfo _arg0100 = new ActivityManager.RunningAppProcessInfo();
                    getMyMemoryState(_arg0100);
                    reply.writeNoException();
                    reply.writeInt(1);
                    _arg0100.writeToParcel(reply, 1);
                    return true;
                case 112:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg0101 = data.readString();
                    boolean killProcessesBelowForeground = killProcessesBelowForeground(_arg0101);
                    reply.writeNoException();
                    reply.writeInt(killProcessesBelowForeground ? 1 : 0);
                    return true;
                case 113:
                    data.enforceInterface(DESCRIPTOR);
                    UserInfo _result34 = getCurrentUser();
                    reply.writeNoException();
                    if (_result34 != null) {
                        reply.writeInt(1);
                        _result34.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 114:
                    data.enforceInterface(DESCRIPTOR);
                    IBinder _arg0102 = data.readStrongBinder();
                    int _result35 = getLaunchedFromUid(_arg0102);
                    reply.writeNoException();
                    reply.writeInt(_result35);
                    return true;
                case 115:
                    data.enforceInterface(DESCRIPTOR);
                    IBinder _arg0103 = data.readStrongBinder();
                    unstableProviderDied(_arg0103);
                    reply.writeNoException();
                    return true;
                case 116:
                    data.enforceInterface(DESCRIPTOR);
                    IIntentSender _arg0104 = IIntentSender.Stub.asInterface(data.readStrongBinder());
                    boolean isIntentSenderAnActivity = isIntentSenderAnActivity(_arg0104);
                    reply.writeNoException();
                    reply.writeInt(isIntentSenderAnActivity ? 1 : 0);
                    return true;
                case 117:
                    data.enforceInterface(DESCRIPTOR);
                    IIntentSender _arg0105 = IIntentSender.Stub.asInterface(data.readStrongBinder());
                    boolean isIntentSenderAForegroundService = isIntentSenderAForegroundService(_arg0105);
                    reply.writeNoException();
                    reply.writeInt(isIntentSenderAForegroundService ? 1 : 0);
                    return true;
                case 118:
                    data.enforceInterface(DESCRIPTOR);
                    IIntentSender _arg0106 = IIntentSender.Stub.asInterface(data.readStrongBinder());
                    boolean isIntentSenderABroadcast = isIntentSenderABroadcast(_arg0106);
                    reply.writeNoException();
                    reply.writeInt(isIntentSenderABroadcast ? 1 : 0);
                    return true;
                case 119:
                    data.enforceInterface(DESCRIPTOR);
                    IApplicationThread _arg0107 = IApplicationThread.Stub.asInterface(data.readStrongBinder());
                    String _arg174 = data.readString();
                    Intent _arg247 = data.readInt() != 0 ? Intent.CREATOR.createFromParcel(data) : null;
                    String _arg330 = data.readString();
                    IBinder _arg421 = data.readStrongBinder();
                    String _arg514 = data.readString();
                    int _arg610 = data.readInt();
                    int _arg78 = data.readInt();
                    ProfilerInfo _arg85 = data.readInt() != 0 ? ProfilerInfo.CREATOR.createFromParcel(data) : null;
                    if (data.readInt() != 0) {
                        _arg9 = Bundle.CREATOR.createFromParcel(data);
                    } else {
                        _arg9 = null;
                    }
                    int _arg102 = data.readInt();
                    int _result36 = startActivityAsUser(_arg0107, _arg174, _arg247, _arg330, _arg421, _arg514, _arg610, _arg78, _arg85, _arg9, _arg102);
                    reply.writeNoException();
                    reply.writeInt(_result36);
                    return true;
                case 120:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg0108 = data.readInt();
                    boolean _arg175 = data.readInt() != 0;
                    IStopUserCallback _arg248 = IStopUserCallback.Stub.asInterface(data.readStrongBinder());
                    int _result37 = stopUser(_arg0108, _arg175, _arg248);
                    reply.writeNoException();
                    reply.writeInt(_result37);
                    return true;
                case 121:
                    data.enforceInterface(DESCRIPTOR);
                    IUserSwitchObserver _arg0109 = IUserSwitchObserver.Stub.asInterface(data.readStrongBinder());
                    String _arg176 = data.readString();
                    registerUserSwitchObserver(_arg0109, _arg176);
                    reply.writeNoException();
                    return true;
                case 122:
                    data.enforceInterface(DESCRIPTOR);
                    IUserSwitchObserver _arg0110 = IUserSwitchObserver.Stub.asInterface(data.readStrongBinder());
                    unregisterUserSwitchObserver(_arg0110);
                    reply.writeNoException();
                    return true;
                case 123:
                    data.enforceInterface(DESCRIPTOR);
                    int[] _result38 = getRunningUserIds();
                    reply.writeNoException();
                    reply.writeIntArray(_result38);
                    return true;
                case 124:
                    data.enforceInterface(DESCRIPTOR);
                    requestSystemServerHeapDump();
                    reply.writeNoException();
                    return true;
                case 125:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg0111 = data.readInt();
                    requestBugReport(_arg0111);
                    reply.writeNoException();
                    return true;
                case 126:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg0112 = data.readString();
                    String _arg177 = data.readString();
                    requestTelephonyBugReport(_arg0112, _arg177);
                    reply.writeNoException();
                    return true;
                case 127:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg0113 = data.readString();
                    String _arg178 = data.readString();
                    requestWifiBugReport(_arg0113, _arg178);
                    reply.writeNoException();
                    return true;
                case 128:
                    data.enforceInterface(DESCRIPTOR);
                    IIntentSender _arg0114 = IIntentSender.Stub.asInterface(data.readStrongBinder());
                    Intent _result39 = getIntentForIntentSender(_arg0114);
                    reply.writeNoException();
                    if (_result39 != null) {
                        reply.writeInt(1);
                        _result39.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 129:
                    data.enforceInterface(DESCRIPTOR);
                    IBinder _arg0115 = data.readStrongBinder();
                    String _result40 = getLaunchedFromPackage(_arg0115);
                    reply.writeNoException();
                    reply.writeString(_result40);
                    return true;
                case 130:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg0116 = data.readInt();
                    int _arg179 = data.readInt();
                    String _arg249 = data.readString();
                    killUid(_arg0116, _arg179, _arg249);
                    reply.writeNoException();
                    return true;
                case 131:
                    data.enforceInterface(DESCRIPTOR);
                    boolean _arg0117 = data.readInt() != 0;
                    setUserIsMonkey(_arg0117);
                    reply.writeNoException();
                    return true;
                case 132:
                    data.enforceInterface(DESCRIPTOR);
                    IBinder _arg0118 = data.readStrongBinder();
                    boolean _arg180 = data.readInt() != 0;
                    hang(_arg0118, _arg180);
                    reply.writeNoException();
                    return true;
                case 133:
                    data.enforceInterface(DESCRIPTOR);
                    List<ActivityManager.StackInfo> _result41 = getAllStackInfos();
                    reply.writeNoException();
                    reply.writeTypedList(_result41);
                    return true;
                case 134:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg0119 = data.readInt();
                    int _arg181 = data.readInt();
                    boolean _arg250 = data.readInt() != 0;
                    moveTaskToStack(_arg0119, _arg181, _arg250);
                    reply.writeNoException();
                    return true;
                case 135:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg0120 = data.readInt();
                    Rect _arg182 = data.readInt() != 0 ? Rect.CREATOR.createFromParcel(data) : null;
                    boolean _arg251 = data.readInt() != 0;
                    boolean _arg331 = data.readInt() != 0;
                    boolean _arg422 = data.readInt() != 0;
                    int _arg515 = data.readInt();
                    resizeStack(_arg0120, _arg182, _arg251, _arg331, _arg422, _arg515);
                    reply.writeNoException();
                    return true;
                case 136:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg0121 = data.readInt();
                    setFocusedStack(_arg0121);
                    reply.writeNoException();
                    return true;
                case 137:
                    data.enforceInterface(DESCRIPTOR);
                    ActivityManager.StackInfo _result42 = getFocusedStackInfo();
                    reply.writeNoException();
                    if (_result42 != null) {
                        reply.writeInt(1);
                        _result42.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 138:
                    data.enforceInterface(DESCRIPTOR);
                    restart();
                    reply.writeNoException();
                    return true;
                case 139:
                    data.enforceInterface(DESCRIPTOR);
                    performIdleMaintenance();
                    reply.writeNoException();
                    return true;
                case 140:
                    data.enforceInterface(DESCRIPTOR);
                    IBinder _arg0122 = data.readStrongBinder();
                    appNotRespondingViaProvider(_arg0122);
                    reply.writeNoException();
                    return true;
                case 141:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg0123 = data.readInt();
                    Rect _result43 = getTaskBounds(_arg0123);
                    reply.writeNoException();
                    if (_result43 != null) {
                        reply.writeInt(1);
                        _result43.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 142:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg0124 = data.readString();
                    int _arg183 = data.readInt();
                    int _arg252 = data.readInt();
                    boolean processMemoryTrimLevel = setProcessMemoryTrimLevel(_arg0124, _arg183, _arg252);
                    reply.writeNoException();
                    reply.writeInt(processMemoryTrimLevel ? 1 : 0);
                    return true;
                case 143:
                    data.enforceInterface(DESCRIPTOR);
                    IIntentSender _arg0125 = IIntentSender.Stub.asInterface(data.readStrongBinder());
                    String _arg184 = data.readString();
                    String _result44 = getTagForIntentSender(_arg0125, _arg184);
                    reply.writeNoException();
                    reply.writeString(_result44);
                    return true;
                case 144:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg0126 = data.readInt();
                    boolean startUserInBackground = startUserInBackground(_arg0126);
                    reply.writeNoException();
                    reply.writeInt(startUserInBackground ? 1 : 0);
                    return true;
                case 145:
                    data.enforceInterface(DESCRIPTOR);
                    boolean isInLockTaskMode = isInLockTaskMode();
                    reply.writeNoException();
                    reply.writeInt(isInLockTaskMode ? 1 : 0);
                    return true;
                case 146:
                    data.enforceInterface(DESCRIPTOR);
                    Intent _arg0127 = data.readInt() != 0 ? Intent.CREATOR.createFromParcel(data) : null;
                    IAssistDataReceiver _arg185 = IAssistDataReceiver.Stub.asInterface(data.readStrongBinder());
                    IRecentsAnimationRunner _arg253 = IRecentsAnimationRunner.Stub.asInterface(data.readStrongBinder());
                    startRecentsActivity(_arg0127, _arg185, _arg253);
                    reply.writeNoException();
                    return true;
                case 147:
                    data.enforceInterface(DESCRIPTOR);
                    boolean _arg0128 = data.readInt() != 0;
                    cancelRecentsAnimation(_arg0128);
                    reply.writeNoException();
                    return true;
                case 148:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg0129 = data.readInt();
                    Bundle _arg186 = data.readInt() != 0 ? Bundle.CREATOR.createFromParcel(data) : null;
                    int _result45 = startActivityFromRecents(_arg0129, _arg186);
                    reply.writeNoException();
                    reply.writeInt(_result45);
                    return true;
                case 149:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg0130 = data.readInt();
                    startSystemLockTaskMode(_arg0130);
                    reply.writeNoException();
                    return true;
                case 150:
                    data.enforceInterface(DESCRIPTOR);
                    IBinder _arg0131 = data.readStrongBinder();
                    boolean isTopOfTask = isTopOfTask(_arg0131);
                    reply.writeNoException();
                    reply.writeInt(isTopOfTask ? 1 : 0);
                    return true;
                case 151:
                    data.enforceInterface(DESCRIPTOR);
                    bootAnimationComplete();
                    reply.writeNoException();
                    return true;
                case 152:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg0132 = data.readString();
                    int _arg187 = data.readInt();
                    int _arg254 = data.readInt();
                    IBinder _arg332 = data.readStrongBinder();
                    int _result46 = checkPermissionWithToken(_arg0132, _arg187, _arg254, _arg332);
                    reply.writeNoException();
                    reply.writeInt(_result46);
                    return true;
                case 153:
                    data.enforceInterface(DESCRIPTOR);
                    ITaskStackListener _arg0133 = ITaskStackListener.Stub.asInterface(data.readStrongBinder());
                    registerTaskStackListener(_arg0133);
                    reply.writeNoException();
                    return true;
                case 154:
                    data.enforceInterface(DESCRIPTOR);
                    ITaskStackListener _arg0134 = ITaskStackListener.Stub.asInterface(data.readStrongBinder());
                    unregisterTaskStackListener(_arg0134);
                    reply.writeNoException();
                    return true;
                case 155:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg0135 = data.readInt();
                    byte[] _arg188 = data.createByteArray();
                    notifyCleartextNetwork(_arg0135, _arg188);
                    reply.writeNoException();
                    return true;
                case 156:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg0136 = data.readInt();
                    int _arg189 = data.readInt();
                    setTaskResizeable(_arg0136, _arg189);
                    reply.writeNoException();
                    return true;
                case 157:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg0137 = data.readInt();
                    Rect _arg190 = data.readInt() != 0 ? Rect.CREATOR.createFromParcel(data) : null;
                    int _arg255 = data.readInt();
                    resizeTask(_arg0137, _arg190, _arg255);
                    reply.writeNoException();
                    return true;
                case 158:
                    data.enforceInterface(DESCRIPTOR);
                    int _result47 = getLockTaskModeState();
                    reply.writeNoException();
                    reply.writeInt(_result47);
                    return true;
                case 159:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg0138 = data.readString();
                    int _arg191 = data.readInt();
                    long _arg256 = data.readLong();
                    String _arg333 = data.readString();
                    setDumpHeapDebugLimit(_arg0138, _arg191, _arg256, _arg333);
                    reply.writeNoException();
                    return true;
                case 160:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg0139 = data.readString();
                    dumpHeapFinished(_arg0139);
                    reply.writeNoException();
                    return true;
                case 161:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg0140 = data.readInt();
                    String[] _arg192 = data.createStringArray();
                    updateLockTaskPackages(_arg0140, _arg192);
                    reply.writeNoException();
                    return true;
                case 162:
                    data.enforceInterface(DESCRIPTOR);
                    IIntentSender _arg0141 = IIntentSender.Stub.asInterface(data.readStrongBinder());
                    WorkSource _arg193 = data.readInt() != 0 ? WorkSource.CREATOR.createFromParcel(data) : null;
                    int _arg257 = data.readInt();
                    String _arg334 = data.readString();
                    noteAlarmStart(_arg0141, _arg193, _arg257, _arg334);
                    reply.writeNoException();
                    return true;
                case 163:
                    data.enforceInterface(DESCRIPTOR);
                    IIntentSender _arg0142 = IIntentSender.Stub.asInterface(data.readStrongBinder());
                    WorkSource _arg194 = data.readInt() != 0 ? WorkSource.CREATOR.createFromParcel(data) : null;
                    int _arg258 = data.readInt();
                    String _arg335 = data.readString();
                    noteAlarmFinish(_arg0142, _arg194, _arg258, _arg335);
                    reply.writeNoException();
                    return true;
                case 164:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg0143 = data.readString();
                    String _arg195 = data.readString();
                    int _result48 = getPackageProcessState(_arg0143, _arg195);
                    reply.writeNoException();
                    reply.writeInt(_result48);
                    return true;
                case 165:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg0144 = data.readString();
                    updateDeviceOwner(_arg0144);
                    reply.writeNoException();
                    return true;
                case 166:
                    data.enforceInterface(DESCRIPTOR);
                    boolean startBinderTracking = startBinderTracking();
                    reply.writeNoException();
                    reply.writeInt(startBinderTracking ? 1 : 0);
                    return true;
                case 167:
                    data.enforceInterface(DESCRIPTOR);
                    ParcelFileDescriptor _arg0145 = data.readInt() != 0 ? ParcelFileDescriptor.CREATOR.createFromParcel(data) : null;
                    boolean stopBinderTrackingAndDump = stopBinderTrackingAndDump(_arg0145);
                    reply.writeNoException();
                    reply.writeInt(stopBinderTrackingAndDump ? 1 : 0);
                    return true;
                case 168:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg0146 = data.readInt();
                    int _arg196 = data.readInt();
                    int _arg259 = data.readInt();
                    positionTaskInStack(_arg0146, _arg196, _arg259);
                    reply.writeNoException();
                    return true;
                case 169:
                    data.enforceInterface(DESCRIPTOR);
                    boolean _arg0147 = data.readInt() != 0;
                    suppressResizeConfigChanges(_arg0147);
                    reply.writeNoException();
                    return true;
                case 170:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg0148 = data.readInt();
                    Rect _arg197 = data.readInt() != 0 ? Rect.CREATOR.createFromParcel(data) : null;
                    boolean moveTopActivityToPinnedStack = moveTopActivityToPinnedStack(_arg0148, _arg197);
                    reply.writeNoException();
                    reply.writeInt(moveTopActivityToPinnedStack ? 1 : 0);
                    return true;
                case 171:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg0149 = data.readInt();
                    String _arg198 = data.readString();
                    boolean isAppStartModeDisabled = isAppStartModeDisabled(_arg0149, _arg198);
                    reply.writeNoException();
                    reply.writeInt(isAppStartModeDisabled ? 1 : 0);
                    return true;
                case 172:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg0150 = data.readInt();
                    byte[] _arg199 = data.createByteArray();
                    byte[] _arg260 = data.createByteArray();
                    IProgressListener _arg336 = IProgressListener.Stub.asInterface(data.readStrongBinder());
                    boolean unlockUser = unlockUser(_arg0150, _arg199, _arg260, _arg336);
                    reply.writeNoException();
                    reply.writeInt(unlockUser ? 1 : 0);
                    return true;
                case 173:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg0151 = data.readString();
                    int _arg1100 = data.readInt();
                    killPackageDependents(_arg0151, _arg1100);
                    reply.writeNoException();
                    return true;
                case 174:
                    data.enforceInterface(DESCRIPTOR);
                    Rect _arg0152 = data.readInt() != 0 ? Rect.CREATOR.createFromParcel(data) : null;
                    Rect _arg1101 = data.readInt() != 0 ? Rect.CREATOR.createFromParcel(data) : null;
                    Rect _arg261 = data.readInt() != 0 ? Rect.CREATOR.createFromParcel(data) : null;
                    Rect _arg337 = data.readInt() != 0 ? Rect.CREATOR.createFromParcel(data) : null;
                    Rect _arg423 = data.readInt() != 0 ? Rect.CREATOR.createFromParcel(data) : null;
                    resizeDockedStack(_arg0152, _arg1101, _arg261, _arg337, _arg423);
                    reply.writeNoException();
                    return true;
                case 175:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg0153 = data.readInt();
                    removeStack(_arg0153);
                    reply.writeNoException();
                    return true;
                case 176:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg0154 = data.readString();
                    int _arg1102 = data.readInt();
                    makePackageIdle(_arg0154, _arg1102);
                    reply.writeNoException();
                    return true;
                case 177:
                    data.enforceInterface(DESCRIPTOR);
                    int _result49 = getMemoryTrimLevel();
                    reply.writeNoException();
                    reply.writeInt(_result49);
                    return true;
                case 178:
                    data.enforceInterface(DESCRIPTOR);
                    ComponentName _arg0155 = data.readInt() != 0 ? ComponentName.CREATOR.createFromParcel(data) : null;
                    boolean isVrModePackageEnabled = isVrModePackageEnabled(_arg0155);
                    reply.writeNoException();
                    reply.writeInt(isVrModePackageEnabled ? 1 : 0);
                    return true;
                case 179:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg0156 = data.readInt();
                    notifyLockedProfile(_arg0156);
                    reply.writeNoException();
                    return true;
                case 180:
                    data.enforceInterface(DESCRIPTOR);
                    if (data.readInt() != 0) {
                        _arg0 = Intent.CREATOR.createFromParcel(data);
                    } else {
                        _arg0 = null;
                    }
                    Bundle _arg1103 = data.readInt() != 0 ? Bundle.CREATOR.createFromParcel(data) : null;
                    startConfirmDeviceCredentialIntent(_arg0, _arg1103);
                    reply.writeNoException();
                    return true;
                case 181:
                    data.enforceInterface(DESCRIPTOR);
                    sendIdleJobTrigger();
                    reply.writeNoException();
                    return true;
                case 182:
                    data.enforceInterface(DESCRIPTOR);
                    IIntentSender _arg0157 = IIntentSender.Stub.asInterface(data.readStrongBinder());
                    IBinder _arg1104 = data.readStrongBinder();
                    int _arg262 = data.readInt();
                    Intent _arg338 = data.readInt() != 0 ? Intent.CREATOR.createFromParcel(data) : null;
                    String _arg424 = data.readString();
                    IIntentReceiver _arg516 = IIntentReceiver.Stub.asInterface(data.readStrongBinder());
                    String _arg611 = data.readString();
                    Bundle _arg79 = data.readInt() != 0 ? Bundle.CREATOR.createFromParcel(data) : null;
                    int _result50 = sendIntentSender(_arg0157, _arg1104, _arg262, _arg338, _arg424, _arg516, _arg611, _arg79);
                    reply.writeNoException();
                    reply.writeInt(_result50);
                    return true;
                case 183:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg0158 = data.readString();
                    boolean isBackgroundRestricted = isBackgroundRestricted(_arg0158);
                    reply.writeNoException();
                    reply.writeInt(isBackgroundRestricted ? 1 : 0);
                    return true;
                case 184:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg0159 = data.readInt();
                    setRenderThread(_arg0159);
                    reply.writeNoException();
                    return true;
                case 185:
                    data.enforceInterface(DESCRIPTOR);
                    boolean _arg0160 = data.readInt() != 0;
                    setHasTopUi(_arg0160);
                    reply.writeNoException();
                    return true;
                case 186:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg0161 = data.readInt();
                    int _result51 = restartUserInBackground(_arg0161);
                    reply.writeNoException();
                    reply.writeInt(_result51);
                    return true;
                case 187:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg0162 = data.readInt();
                    cancelTaskWindowTransition(_arg0162);
                    reply.writeNoException();
                    return true;
                case 188:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg0163 = data.readInt();
                    boolean _arg1105 = data.readInt() != 0;
                    ActivityManager.TaskSnapshot _result52 = getTaskSnapshot(_arg0163, _arg1105);
                    reply.writeNoException();
                    if (_result52 != null) {
                        reply.writeInt(1);
                        _result52.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 189:
                    data.enforceInterface(DESCRIPTOR);
                    List<String> _arg0164 = data.createStringArrayList();
                    int _arg1106 = data.readInt();
                    scheduleApplicationInfoChanged(_arg0164, _arg1106);
                    reply.writeNoException();
                    return true;
                case 190:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg0165 = data.readInt();
                    setPersistentVrThread(_arg0165);
                    reply.writeNoException();
                    return true;
                case 191:
                    data.enforceInterface(DESCRIPTOR);
                    long _arg0166 = data.readLong();
                    waitForNetworkStateUpdate(_arg0166);
                    reply.writeNoException();
                    return true;
                case 192:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg0167 = data.readInt();
                    backgroundWhitelistUid(_arg0167);
                    reply.writeNoException();
                    return true;
                case 193:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg0168 = data.readInt();
                    IProgressListener _arg1107 = IProgressListener.Stub.asInterface(data.readStrongBinder());
                    boolean startUserInBackgroundWithListener = startUserInBackgroundWithListener(_arg0168, _arg1107);
                    reply.writeNoException();
                    reply.writeInt(startUserInBackgroundWithListener ? 1 : 0);
                    return true;
                case 194:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg0169 = data.readInt();
                    String[] _arg1108 = data.createStringArray();
                    startDelegateShellPermissionIdentity(_arg0169, _arg1108);
                    reply.writeNoException();
                    return true;
                case 195:
                    data.enforceInterface(DESCRIPTOR);
                    stopDelegateShellPermissionIdentity();
                    reply.writeNoException();
                    return true;
                case 196:
                    data.enforceInterface(DESCRIPTOR);
                    ParcelFileDescriptor _result53 = getLifeMonitor();
                    reply.writeNoException();
                    if (_result53 != null) {
                        reply.writeInt(1);
                        _result53.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 197:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg0170 = data.readInt();
                    IProgressListener _arg1109 = IProgressListener.Stub.asInterface(data.readStrongBinder());
                    boolean startUserInForegroundWithListener = startUserInForegroundWithListener(_arg0170, _arg1109);
                    reply.writeNoException();
                    reply.writeInt(startUserInForegroundWithListener ? 1 : 0);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* loaded from: classes.dex */
        private static class Proxy implements IActivityManager {
            public static IActivityManager sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p007os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.app.IActivityManager
            public ParcelFileDescriptor openContentUri(String uriString) throws RemoteException {
                ParcelFileDescriptor _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(uriString);
                    boolean _status = this.mRemote.transact(1, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().openContentUri(uriString);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = ParcelFileDescriptor.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void registerUidObserver(IUidObserver observer, int which, int cutpoint, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(observer != null ? observer.asBinder() : null);
                    _data.writeInt(which);
                    _data.writeInt(cutpoint);
                    _data.writeString(callingPackage);
                    boolean _status = this.mRemote.transact(2, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registerUidObserver(observer, which, cutpoint, callingPackage);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void unregisterUidObserver(IUidObserver observer) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(observer != null ? observer.asBinder() : null);
                    boolean _status = this.mRemote.transact(3, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unregisterUidObserver(observer);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean isUidActive(int uid, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeString(callingPackage);
                    boolean _status = this.mRemote.transact(4, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isUidActive(uid, callingPackage);
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int getUidProcessState(int uid, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeString(callingPackage);
                    boolean _status = this.mRemote.transact(5, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getUidProcessState(uid, callingPackage);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void handleApplicationCrash(IBinder app, ApplicationErrorReport.ParcelableCrashInfo crashInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(app);
                    if (crashInfo != null) {
                        _data.writeInt(1);
                        crashInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(6, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().handleApplicationCrash(app, crashInfo);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int startActivity(IApplicationThread caller, String callingPackage, Intent intent, String resolvedType, IBinder resultTo, String resultWho, int requestCode, int flags, ProfilerInfo profilerInfo, Bundle options) throws RemoteException {
                Parcel _reply;
                IBinder asBinder;
                boolean _status;
                Parcel _data = Parcel.obtain();
                Parcel _reply2 = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (caller != null) {
                        try {
                            asBinder = caller.asBinder();
                        } catch (Throwable th) {
                            th = th;
                            _reply = _reply2;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } else {
                        asBinder = null;
                    }
                    _data.writeStrongBinder(asBinder);
                    _data.writeString(callingPackage);
                    if (intent != null) {
                        _data.writeInt(1);
                        intent.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(resolvedType);
                    _data.writeStrongBinder(resultTo);
                    _data.writeString(resultWho);
                    _data.writeInt(requestCode);
                    _data.writeInt(flags);
                    if (profilerInfo != null) {
                        _data.writeInt(1);
                        profilerInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (options != null) {
                        _data.writeInt(1);
                        options.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _status = this.mRemote.transact(7, _data, _reply2, 0);
                } catch (Throwable th2) {
                    th = th2;
                    _reply = _reply2;
                }
                try {
                    if (!_status && Stub.getDefaultImpl() != null) {
                        int startActivity = Stub.getDefaultImpl().startActivity(caller, callingPackage, intent, resolvedType, resultTo, resultWho, requestCode, flags, profilerInfo, options);
                        _reply2.recycle();
                        _data.recycle();
                        return startActivity;
                    }
                    _reply2.readException();
                    int _result = _reply2.readInt();
                    _reply2.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th3) {
                    th = th3;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            @Override // android.app.IActivityManager
            public void unhandledBack() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(8, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unhandledBack();
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean finishActivity(IBinder token, int code, Intent data, int finishTask) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeInt(code);
                    if (data != null) {
                        _data.writeInt(1);
                        data.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(finishTask);
                    boolean _status = this.mRemote.transact(9, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().finishActivity(token, code, data, finishTask);
                    }
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public Intent registerReceiver(IApplicationThread caller, String callerPackage, IIntentReceiver receiver, IntentFilter filter, String requiredPermission, int userId, int flags) throws RemoteException {
                Intent _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _result = null;
                    _data.writeStrongBinder(caller != null ? caller.asBinder() : null);
                } catch (Throwable th) {
                    th = th;
                }
                try {
                    _data.writeString(callerPackage);
                    _data.writeStrongBinder(receiver != null ? receiver.asBinder() : null);
                    if (filter != null) {
                        _data.writeInt(1);
                        filter.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                } catch (Throwable th2) {
                    th = th2;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeString(requiredPermission);
                    _data.writeInt(userId);
                    _data.writeInt(flags);
                    boolean _status = this.mRemote.transact(10, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Intent registerReceiver = Stub.getDefaultImpl().registerReceiver(caller, callerPackage, receiver, filter, requiredPermission, userId, flags);
                        _reply.recycle();
                        _data.recycle();
                        return registerReceiver;
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = Intent.CREATOR.createFromParcel(_reply);
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th3) {
                    th = th3;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            @Override // android.app.IActivityManager
            public void unregisterReceiver(IIntentReceiver receiver) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(receiver != null ? receiver.asBinder() : null);
                    boolean _status = this.mRemote.transact(11, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unregisterReceiver(receiver);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int broadcastIntent(IApplicationThread caller, Intent intent, String resolvedType, IIntentReceiver resultTo, int resultCode, String resultData, Bundle map, String[] requiredPermissions, int appOp, Bundle options, boolean serialized, boolean sticky, int userId) throws RemoteException {
                Parcel _reply;
                Parcel _data;
                IBinder asBinder;
                boolean _status;
                Parcel _data2 = Parcel.obtain();
                Parcel _reply2 = Parcel.obtain();
                try {
                    _data2.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (caller != null) {
                        try {
                            asBinder = caller.asBinder();
                        } catch (Throwable th) {
                            th = th;
                            _reply = _reply2;
                            _data = _data2;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } else {
                        asBinder = null;
                    }
                    _data2.writeStrongBinder(asBinder);
                    if (intent != null) {
                        _data2.writeInt(1);
                        intent.writeToParcel(_data2, 0);
                    } else {
                        _data2.writeInt(0);
                    }
                    _data2.writeString(resolvedType);
                    _data2.writeStrongBinder(resultTo != null ? resultTo.asBinder() : null);
                    _data2.writeInt(resultCode);
                    _data2.writeString(resultData);
                    if (map != null) {
                        _data2.writeInt(1);
                        map.writeToParcel(_data2, 0);
                    } else {
                        _data2.writeInt(0);
                    }
                    _data2.writeStringArray(requiredPermissions);
                    _data2.writeInt(appOp);
                    if (options != null) {
                        _data2.writeInt(1);
                        options.writeToParcel(_data2, 0);
                    } else {
                        _data2.writeInt(0);
                    }
                    _data2.writeInt(serialized ? 1 : 0);
                    _data2.writeInt(sticky ? 1 : 0);
                    _data2.writeInt(userId);
                    _status = this.mRemote.transact(12, _data2, _reply2, 0);
                } catch (Throwable th2) {
                    th = th2;
                    _reply = _reply2;
                    _data = _data2;
                }
                try {
                    if (!_status && Stub.getDefaultImpl() != null) {
                        int broadcastIntent = Stub.getDefaultImpl().broadcastIntent(caller, intent, resolvedType, resultTo, resultCode, resultData, map, requiredPermissions, appOp, options, serialized, sticky, userId);
                        _reply2.recycle();
                        _data2.recycle();
                        return broadcastIntent;
                    }
                    _reply2.readException();
                    int _result = _reply2.readInt();
                    _reply2.recycle();
                    _data2.recycle();
                    return _result;
                } catch (Throwable th3) {
                    th = th3;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            @Override // android.app.IActivityManager
            public void unbroadcastIntent(IApplicationThread caller, Intent intent, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(caller != null ? caller.asBinder() : null);
                    if (intent != null) {
                        _data.writeInt(1);
                        intent.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(userId);
                    boolean _status = this.mRemote.transact(13, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unbroadcastIntent(caller, intent, userId);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void finishReceiver(IBinder who, int resultCode, String resultData, Bundle map, boolean abortBroadcast, int flags) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                } catch (Throwable th) {
                    th = th;
                }
                try {
                    _data.writeStrongBinder(who);
                } catch (Throwable th2) {
                    th = th2;
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeInt(resultCode);
                    try {
                        _data.writeString(resultData);
                        if (map != null) {
                            _data.writeInt(1);
                            map.writeToParcel(_data, 0);
                        } else {
                            _data.writeInt(0);
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(abortBroadcast ? 1 : 0);
                        try {
                            _data.writeInt(flags);
                        } catch (Throwable th4) {
                            th = th4;
                        }
                    } catch (Throwable th5) {
                        th = th5;
                        _data.recycle();
                        throw th;
                    }
                    try {
                        boolean _status = this.mRemote.transact(14, _data, null, 1);
                        if (!_status && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().finishReceiver(who, resultCode, resultData, map, abortBroadcast, flags);
                            _data.recycle();
                            return;
                        }
                        _data.recycle();
                    } catch (Throwable th6) {
                        th = th6;
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th7) {
                    th = th7;
                    _data.recycle();
                    throw th;
                }
            }

            @Override // android.app.IActivityManager
            public void attachApplication(IApplicationThread app, long startSeq) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(app != null ? app.asBinder() : null);
                    _data.writeLong(startSeq);
                    boolean _status = this.mRemote.transact(15, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().attachApplication(app, startSeq);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public List<ActivityManager.RunningTaskInfo> getTasks(int maxNum) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(maxNum);
                    boolean _status = this.mRemote.transact(16, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getTasks(maxNum);
                    }
                    _reply.readException();
                    List<ActivityManager.RunningTaskInfo> _result = _reply.createTypedArrayList(ActivityManager.RunningTaskInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public List<ActivityManager.RunningTaskInfo> getFilteredTasks(int maxNum, int ignoreActivityType, int ignoreWindowingMode) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(maxNum);
                    _data.writeInt(ignoreActivityType);
                    _data.writeInt(ignoreWindowingMode);
                    boolean _status = this.mRemote.transact(17, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getFilteredTasks(maxNum, ignoreActivityType, ignoreWindowingMode);
                    }
                    _reply.readException();
                    List<ActivityManager.RunningTaskInfo> _result = _reply.createTypedArrayList(ActivityManager.RunningTaskInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void moveTaskToFront(IApplicationThread caller, String callingPackage, int task, int flags, Bundle options) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(caller != null ? caller.asBinder() : null);
                    _data.writeString(callingPackage);
                    _data.writeInt(task);
                    _data.writeInt(flags);
                    if (options != null) {
                        _data.writeInt(1);
                        options.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(18, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().moveTaskToFront(caller, callingPackage, task, flags, options);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int getTaskForActivity(IBinder token, boolean onlyRoot) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeInt(onlyRoot ? 1 : 0);
                    boolean _status = this.mRemote.transact(19, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getTaskForActivity(token, onlyRoot);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public ContentProviderHolder getContentProvider(IApplicationThread caller, String callingPackage, String name, int userId, boolean stable) throws RemoteException {
                ContentProviderHolder _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _result = null;
                    _data.writeStrongBinder(caller != null ? caller.asBinder() : null);
                } catch (Throwable th) {
                    th = th;
                }
                try {
                    _data.writeString(callingPackage);
                    try {
                        _data.writeString(name);
                    } catch (Throwable th2) {
                        th = th2;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(userId);
                        try {
                            _data.writeInt(stable ? 1 : 0);
                        } catch (Throwable th3) {
                            th = th3;
                        }
                    } catch (Throwable th4) {
                        th = th4;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        boolean _status = this.mRemote.transact(20, _data, _reply, 0);
                        if (!_status && Stub.getDefaultImpl() != null) {
                            ContentProviderHolder contentProvider = Stub.getDefaultImpl().getContentProvider(caller, callingPackage, name, userId, stable);
                            _reply.recycle();
                            _data.recycle();
                            return contentProvider;
                        }
                        _reply.readException();
                        if (_reply.readInt() != 0) {
                            _result = ContentProviderHolder.CREATOR.createFromParcel(_reply);
                        }
                        _reply.recycle();
                        _data.recycle();
                        return _result;
                    } catch (Throwable th5) {
                        th = th5;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th6) {
                    th = th6;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            @Override // android.app.IActivityManager
            public void publishContentProviders(IApplicationThread caller, List<ContentProviderHolder> providers) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(caller != null ? caller.asBinder() : null);
                    _data.writeTypedList(providers);
                    boolean _status = this.mRemote.transact(21, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().publishContentProviders(caller, providers);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean refContentProvider(IBinder connection, int stableDelta, int unstableDelta) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(connection);
                    _data.writeInt(stableDelta);
                    _data.writeInt(unstableDelta);
                    boolean _status = this.mRemote.transact(22, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().refContentProvider(connection, stableDelta, unstableDelta);
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public PendingIntent getRunningServiceControlPanel(ComponentName service) throws RemoteException {
                PendingIntent _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (service != null) {
                        _data.writeInt(1);
                        service.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(23, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getRunningServiceControlPanel(service);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = PendingIntent.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public ComponentName startService(IApplicationThread caller, Intent service, String resolvedType, boolean requireForeground, String callingPackage, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    ComponentName _result = null;
                    _data.writeStrongBinder(caller != null ? caller.asBinder() : null);
                    if (service != null) {
                        _data.writeInt(1);
                        service.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    try {
                        _data.writeString(resolvedType);
                    } catch (Throwable th) {
                        th = th;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(requireForeground ? 1 : 0);
                    } catch (Throwable th2) {
                        th = th2;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeString(callingPackage);
                    } catch (Throwable th3) {
                        th = th3;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(userId);
                        boolean _status = this.mRemote.transact(24, _data, _reply, 0);
                        if (!_status && Stub.getDefaultImpl() != null) {
                            ComponentName startService = Stub.getDefaultImpl().startService(caller, service, resolvedType, requireForeground, callingPackage, userId);
                            _reply.recycle();
                            _data.recycle();
                            return startService;
                        }
                        _reply.readException();
                        if (_reply.readInt() != 0) {
                            _result = ComponentName.CREATOR.createFromParcel(_reply);
                        }
                        _reply.recycle();
                        _data.recycle();
                        return _result;
                    } catch (Throwable th4) {
                        th = th4;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th5) {
                    th = th5;
                }
            }

            @Override // android.app.IActivityManager
            public int stopService(IApplicationThread caller, Intent service, String resolvedType, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(caller != null ? caller.asBinder() : null);
                    if (service != null) {
                        _data.writeInt(1);
                        service.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(resolvedType);
                    _data.writeInt(userId);
                    boolean _status = this.mRemote.transact(25, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().stopService(caller, service, resolvedType, userId);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int bindService(IApplicationThread caller, IBinder token, Intent service, String resolvedType, IServiceConnection connection, int flags, String callingPackage, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(caller != null ? caller.asBinder() : null);
                } catch (Throwable th) {
                    th = th;
                }
                try {
                    _data.writeStrongBinder(token);
                    if (service != null) {
                        _data.writeInt(1);
                        service.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    try {
                        _data.writeString(resolvedType);
                        _data.writeStrongBinder(connection != null ? connection.asBinder() : null);
                        _data.writeInt(flags);
                        _data.writeString(callingPackage);
                        _data.writeInt(userId);
                        boolean _status = this.mRemote.transact(26, _data, _reply, 0);
                        if (!_status && Stub.getDefaultImpl() != null) {
                            int bindService = Stub.getDefaultImpl().bindService(caller, token, service, resolvedType, connection, flags, callingPackage, userId);
                            _reply.recycle();
                            _data.recycle();
                            return bindService;
                        }
                        _reply.readException();
                        int _result = _reply.readInt();
                        _reply.recycle();
                        _data.recycle();
                        return _result;
                    } catch (Throwable th2) {
                        th = th2;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            @Override // android.app.IActivityManager
            public int bindIsolatedService(IApplicationThread caller, IBinder token, Intent service, String resolvedType, IServiceConnection connection, int flags, String instanceName, String callingPackage, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(caller != null ? caller.asBinder() : null);
                } catch (Throwable th) {
                    th = th;
                }
                try {
                    _data.writeStrongBinder(token);
                    if (service != null) {
                        _data.writeInt(1);
                        service.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(resolvedType);
                    _data.writeStrongBinder(connection != null ? connection.asBinder() : null);
                    _data.writeInt(flags);
                    _data.writeString(instanceName);
                    _data.writeString(callingPackage);
                    _data.writeInt(userId);
                    boolean _status = this.mRemote.transact(27, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        int bindIsolatedService = Stub.getDefaultImpl().bindIsolatedService(caller, token, service, resolvedType, connection, flags, instanceName, callingPackage, userId);
                        _reply.recycle();
                        _data.recycle();
                        return bindIsolatedService;
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th2) {
                    th = th2;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            @Override // android.app.IActivityManager
            public void updateServiceGroup(IServiceConnection connection, int group, int importance) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(connection != null ? connection.asBinder() : null);
                    _data.writeInt(group);
                    _data.writeInt(importance);
                    boolean _status = this.mRemote.transact(28, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().updateServiceGroup(connection, group, importance);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean unbindService(IServiceConnection connection) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(connection != null ? connection.asBinder() : null);
                    boolean _status = this.mRemote.transact(29, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().unbindService(connection);
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void publishService(IBinder token, Intent intent, IBinder service) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    if (intent != null) {
                        _data.writeInt(1);
                        intent.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeStrongBinder(service);
                    boolean _status = this.mRemote.transact(30, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().publishService(token, intent, service);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setDebugApp(String packageName, boolean waitForDebugger, boolean persistent) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(waitForDebugger ? 1 : 0);
                    _data.writeInt(persistent ? 1 : 0);
                    boolean _status = this.mRemote.transact(31, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setDebugApp(packageName, waitForDebugger, persistent);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setAgentApp(String packageName, String agent) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeString(agent);
                    boolean _status = this.mRemote.transact(32, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setAgentApp(packageName, agent);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setAlwaysFinish(boolean enabled) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(enabled ? 1 : 0);
                    boolean _status = this.mRemote.transact(33, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setAlwaysFinish(enabled);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean startInstrumentation(ComponentName className, String profileFile, int flags, Bundle arguments, IInstrumentationWatcher watcher, IUiAutomationConnection connection, int userId, String abiOverride) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (className != null) {
                        _data.writeInt(1);
                        className.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    try {
                        _data.writeString(profileFile);
                    } catch (Throwable th) {
                        th = th;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(flags);
                        if (arguments != null) {
                            _data.writeInt(1);
                            arguments.writeToParcel(_data, 0);
                        } else {
                            _data.writeInt(0);
                        }
                        _data.writeStrongBinder(watcher != null ? watcher.asBinder() : null);
                        _data.writeStrongBinder(connection != null ? connection.asBinder() : null);
                        _data.writeInt(userId);
                        _data.writeString(abiOverride);
                        boolean _status = this.mRemote.transact(34, _data, _reply, 0);
                        if (!_status && Stub.getDefaultImpl() != null) {
                            boolean startInstrumentation = Stub.getDefaultImpl().startInstrumentation(className, profileFile, flags, arguments, watcher, connection, userId, abiOverride);
                            _reply.recycle();
                            _data.recycle();
                            return startInstrumentation;
                        }
                        _reply.readException();
                        boolean _result = _reply.readInt() != 0;
                        _reply.recycle();
                        _data.recycle();
                        return _result;
                    } catch (Throwable th2) {
                        th = th2;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                }
            }

            @Override // android.app.IActivityManager
            public void addInstrumentationResults(IApplicationThread target, Bundle results) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(target != null ? target.asBinder() : null);
                    if (results != null) {
                        _data.writeInt(1);
                        results.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(35, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().addInstrumentationResults(target, results);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void finishInstrumentation(IApplicationThread target, int resultCode, Bundle results) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(target != null ? target.asBinder() : null);
                    _data.writeInt(resultCode);
                    if (results != null) {
                        _data.writeInt(1);
                        results.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(36, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().finishInstrumentation(target, resultCode, results);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public Configuration getConfiguration() throws RemoteException {
                Configuration _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(37, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getConfiguration();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = Configuration.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean updateConfiguration(Configuration values) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (values != null) {
                        _data.writeInt(1);
                        values.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(38, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().updateConfiguration(values);
                    }
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean stopServiceToken(ComponentName className, IBinder token, int startId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (className != null) {
                        _data.writeInt(1);
                        className.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeStrongBinder(token);
                    _data.writeInt(startId);
                    boolean _status = this.mRemote.transact(39, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().stopServiceToken(className, token, startId);
                    }
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setProcessLimit(int max) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(max);
                    boolean _status = this.mRemote.transact(40, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setProcessLimit(max);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int getProcessLimit() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(41, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getProcessLimit();
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int checkPermission(String permission, int pid, int uid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(permission);
                    _data.writeInt(pid);
                    _data.writeInt(uid);
                    boolean _status = this.mRemote.transact(42, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().checkPermission(permission, pid, uid);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int checkUriPermission(Uri uri, int pid, int uid, int mode, int userId, IBinder callerToken) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (uri != null) {
                        _data.writeInt(1);
                        uri.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                } catch (Throwable th) {
                    th = th;
                }
                try {
                    _data.writeInt(pid);
                } catch (Throwable th2) {
                    th = th2;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeInt(uid);
                } catch (Throwable th3) {
                    th = th3;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeInt(mode);
                } catch (Throwable th4) {
                    th = th4;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeInt(userId);
                } catch (Throwable th5) {
                    th = th5;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeStrongBinder(callerToken);
                    boolean _status = this.mRemote.transact(43, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        int checkUriPermission = Stub.getDefaultImpl().checkUriPermission(uri, pid, uid, mode, userId, callerToken);
                        _reply.recycle();
                        _data.recycle();
                        return checkUriPermission;
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th6) {
                    th = th6;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            @Override // android.app.IActivityManager
            public void grantUriPermission(IApplicationThread caller, String targetPkg, Uri uri, int mode, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(caller != null ? caller.asBinder() : null);
                    _data.writeString(targetPkg);
                    if (uri != null) {
                        _data.writeInt(1);
                        uri.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(mode);
                    _data.writeInt(userId);
                    boolean _status = this.mRemote.transact(44, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().grantUriPermission(caller, targetPkg, uri, mode, userId);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void revokeUriPermission(IApplicationThread caller, String targetPkg, Uri uri, int mode, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(caller != null ? caller.asBinder() : null);
                    _data.writeString(targetPkg);
                    if (uri != null) {
                        _data.writeInt(1);
                        uri.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(mode);
                    _data.writeInt(userId);
                    boolean _status = this.mRemote.transact(45, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().revokeUriPermission(caller, targetPkg, uri, mode, userId);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setActivityController(IActivityController watcher, boolean imAMonkey) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(watcher != null ? watcher.asBinder() : null);
                    _data.writeInt(imAMonkey ? 1 : 0);
                    boolean _status = this.mRemote.transact(46, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setActivityController(watcher, imAMonkey);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void showWaitingForDebugger(IApplicationThread who, boolean waiting) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(who != null ? who.asBinder() : null);
                    _data.writeInt(waiting ? 1 : 0);
                    boolean _status = this.mRemote.transact(47, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().showWaitingForDebugger(who, waiting);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void signalPersistentProcesses(int signal) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(signal);
                    boolean _status = this.mRemote.transact(48, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().signalPersistentProcesses(signal);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public ParceledListSlice getRecentTasks(int maxNum, int flags, int userId) throws RemoteException {
                ParceledListSlice _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(maxNum);
                    _data.writeInt(flags);
                    _data.writeInt(userId);
                    boolean _status = this.mRemote.transact(49, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getRecentTasks(maxNum, flags, userId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = ParceledListSlice.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void serviceDoneExecuting(IBinder token, int type, int startId, int res) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeInt(type);
                    _data.writeInt(startId);
                    _data.writeInt(res);
                    boolean _status = this.mRemote.transact(50, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().serviceDoneExecuting(token, type, startId, res);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public IIntentSender getIntentSender(int type, String packageName, IBinder token, String resultWho, int requestCode, Intent[] intents, String[] resolvedTypes, int flags, Bundle options, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                } catch (Throwable th) {
                    th = th;
                }
                try {
                    _data.writeInt(type);
                    _data.writeString(packageName);
                    _data.writeStrongBinder(token);
                    _data.writeString(resultWho);
                    _data.writeInt(requestCode);
                    _data.writeTypedArray(intents, 0);
                    _data.writeStringArray(resolvedTypes);
                    _data.writeInt(flags);
                    if (options != null) {
                        _data.writeInt(1);
                        options.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(userId);
                    boolean _status = this.mRemote.transact(51, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        IIntentSender intentSender = Stub.getDefaultImpl().getIntentSender(type, packageName, token, resultWho, requestCode, intents, resolvedTypes, flags, options, userId);
                        _reply.recycle();
                        _data.recycle();
                        return intentSender;
                    }
                    _reply.readException();
                    IIntentSender _result = IIntentSender.Stub.asInterface(_reply.readStrongBinder());
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th2) {
                    th = th2;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            @Override // android.app.IActivityManager
            public void cancelIntentSender(IIntentSender sender) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(sender != null ? sender.asBinder() : null);
                    boolean _status = this.mRemote.transact(52, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().cancelIntentSender(sender);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public String getPackageForIntentSender(IIntentSender sender) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(sender != null ? sender.asBinder() : null);
                    boolean _status = this.mRemote.transact(53, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPackageForIntentSender(sender);
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void registerIntentSenderCancelListener(IIntentSender sender, IResultReceiver receiver) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(sender != null ? sender.asBinder() : null);
                    _data.writeStrongBinder(receiver != null ? receiver.asBinder() : null);
                    boolean _status = this.mRemote.transact(54, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registerIntentSenderCancelListener(sender, receiver);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void unregisterIntentSenderCancelListener(IIntentSender sender, IResultReceiver receiver) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(sender != null ? sender.asBinder() : null);
                    _data.writeStrongBinder(receiver != null ? receiver.asBinder() : null);
                    boolean _status = this.mRemote.transact(55, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unregisterIntentSenderCancelListener(sender, receiver);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void enterSafeMode() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(56, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().enterSafeMode();
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void noteWakeupAlarm(IIntentSender sender, WorkSource workSource, int sourceUid, String sourcePkg, String tag) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(sender != null ? sender.asBinder() : null);
                    if (workSource != null) {
                        _data.writeInt(1);
                        workSource.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(sourceUid);
                    _data.writeString(sourcePkg);
                    _data.writeString(tag);
                    boolean _status = this.mRemote.transact(57, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().noteWakeupAlarm(sender, workSource, sourceUid, sourcePkg, tag);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void removeContentProvider(IBinder connection, boolean stable) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(connection);
                    _data.writeInt(stable ? 1 : 0);
                    boolean _status = this.mRemote.transact(58, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeContentProvider(connection, stable);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setRequestedOrientation(IBinder token, int requestedOrientation) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeInt(requestedOrientation);
                    boolean _status = this.mRemote.transact(59, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setRequestedOrientation(token, requestedOrientation);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void unbindFinished(IBinder token, Intent service, boolean doRebind) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    if (service != null) {
                        _data.writeInt(1);
                        service.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(doRebind ? 1 : 0);
                    boolean _status = this.mRemote.transact(60, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unbindFinished(token, service, doRebind);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setProcessImportant(IBinder token, int pid, boolean isForeground, String reason) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeInt(pid);
                    _data.writeInt(isForeground ? 1 : 0);
                    _data.writeString(reason);
                    boolean _status = this.mRemote.transact(61, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setProcessImportant(token, pid, isForeground, reason);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setServiceForeground(ComponentName className, IBinder token, int id, Notification notification, int flags, int foregroundServiceType) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (className != null) {
                        _data.writeInt(1);
                        className.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                } catch (Throwable th) {
                    th = th;
                }
                try {
                    _data.writeStrongBinder(token);
                } catch (Throwable th2) {
                    th = th2;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeInt(id);
                    if (notification != null) {
                        _data.writeInt(1);
                        notification.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                } catch (Throwable th3) {
                    th = th3;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeInt(flags);
                } catch (Throwable th4) {
                    th = th4;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeInt(foregroundServiceType);
                    boolean _status = this.mRemote.transact(62, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setServiceForeground(className, token, id, notification, flags, foregroundServiceType);
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    _reply.readException();
                    _reply.recycle();
                    _data.recycle();
                } catch (Throwable th5) {
                    th = th5;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            @Override // android.app.IActivityManager
            public int getForegroundServiceType(ComponentName className, IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (className != null) {
                        _data.writeInt(1);
                        className.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeStrongBinder(token);
                    boolean _status = this.mRemote.transact(63, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getForegroundServiceType(className, token);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean moveActivityTaskToBack(IBinder token, boolean nonRoot) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeInt(nonRoot ? 1 : 0);
                    boolean _status = this.mRemote.transact(64, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().moveActivityTaskToBack(token, nonRoot);
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void getMemoryInfo(ActivityManager.MemoryInfo outInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(65, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().getMemoryInfo(outInfo);
                        return;
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        outInfo.readFromParcel(_reply);
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public List<ActivityManager.ProcessErrorStateInfo> getProcessesInErrorState() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(66, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getProcessesInErrorState();
                    }
                    _reply.readException();
                    List<ActivityManager.ProcessErrorStateInfo> _result = _reply.createTypedArrayList(ActivityManager.ProcessErrorStateInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean clearApplicationUserData(String packageName, boolean keepState, IPackageDataObserver observer, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(keepState ? 1 : 0);
                    _data.writeStrongBinder(observer != null ? observer.asBinder() : null);
                    _data.writeInt(userId);
                    boolean _status = this.mRemote.transact(67, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().clearApplicationUserData(packageName, keepState, observer, userId);
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void forceStopPackage(String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    boolean _status = this.mRemote.transact(68, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().forceStopPackage(packageName, userId);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean killPids(int[] pids, String reason, boolean secure) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeIntArray(pids);
                    _data.writeString(reason);
                    _data.writeInt(secure ? 1 : 0);
                    boolean _status = this.mRemote.transact(69, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().killPids(pids, reason, secure);
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public List<ActivityManager.RunningServiceInfo> getServices(int maxNum, int flags) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(maxNum);
                    _data.writeInt(flags);
                    boolean _status = this.mRemote.transact(70, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getServices(maxNum, flags);
                    }
                    _reply.readException();
                    List<ActivityManager.RunningServiceInfo> _result = _reply.createTypedArrayList(ActivityManager.RunningServiceInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public List<ActivityManager.RunningAppProcessInfo> getRunningAppProcesses() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(71, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getRunningAppProcesses();
                    }
                    _reply.readException();
                    List<ActivityManager.RunningAppProcessInfo> _result = _reply.createTypedArrayList(ActivityManager.RunningAppProcessInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public IBinder peekService(Intent service, String resolvedType, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (service != null) {
                        _data.writeInt(1);
                        service.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(resolvedType);
                    _data.writeString(callingPackage);
                    boolean _status = this.mRemote.transact(72, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().peekService(service, resolvedType, callingPackage);
                    }
                    _reply.readException();
                    IBinder _result = _reply.readStrongBinder();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean profileControl(String process, int userId, boolean start, ProfilerInfo profilerInfo, int profileType) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                } catch (Throwable th) {
                    th = th;
                }
                try {
                    _data.writeString(process);
                    try {
                        _data.writeInt(userId);
                    } catch (Throwable th2) {
                        th = th2;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(start ? 1 : 0);
                        if (profilerInfo != null) {
                            _data.writeInt(1);
                            profilerInfo.writeToParcel(_data, 0);
                        } else {
                            _data.writeInt(0);
                        }
                        try {
                            _data.writeInt(profileType);
                        } catch (Throwable th3) {
                            th = th3;
                        }
                    } catch (Throwable th4) {
                        th = th4;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        boolean _status = this.mRemote.transact(73, _data, _reply, 0);
                        if (!_status && Stub.getDefaultImpl() != null) {
                            boolean profileControl = Stub.getDefaultImpl().profileControl(process, userId, start, profilerInfo, profileType);
                            _reply.recycle();
                            _data.recycle();
                            return profileControl;
                        }
                        _reply.readException();
                        boolean _result = _reply.readInt() != 0;
                        _reply.recycle();
                        _data.recycle();
                        return _result;
                    } catch (Throwable th5) {
                        th = th5;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th6) {
                    th = th6;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            @Override // android.app.IActivityManager
            public boolean shutdown(int timeout) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(timeout);
                    boolean _status = this.mRemote.transact(74, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().shutdown(timeout);
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void stopAppSwitches() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(75, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().stopAppSwitches();
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void resumeAppSwitches() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(76, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().resumeAppSwitches();
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean bindBackupAgent(String packageName, int backupRestoreMode, int targetUserId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(backupRestoreMode);
                    _data.writeInt(targetUserId);
                    boolean _status = this.mRemote.transact(77, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().bindBackupAgent(packageName, backupRestoreMode, targetUserId);
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void backupAgentCreated(String packageName, IBinder agent, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeStrongBinder(agent);
                    _data.writeInt(userId);
                    boolean _status = this.mRemote.transact(78, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().backupAgentCreated(packageName, agent, userId);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void unbindBackupAgent(ApplicationInfo appInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (appInfo != null) {
                        _data.writeInt(1);
                        appInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(79, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unbindBackupAgent(appInfo);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int getUidForIntentSender(IIntentSender sender) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(sender != null ? sender.asBinder() : null);
                    boolean _status = this.mRemote.transact(80, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getUidForIntentSender(sender);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int handleIncomingUser(int callingPid, int callingUid, int userId, boolean allowAll, boolean requireFull, String name, String callerPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                } catch (Throwable th) {
                    th = th;
                }
                try {
                    _data.writeInt(callingPid);
                } catch (Throwable th2) {
                    th = th2;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeInt(callingUid);
                } catch (Throwable th3) {
                    th = th3;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeInt(userId);
                } catch (Throwable th4) {
                    th = th4;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeInt(allowAll ? 1 : 0);
                } catch (Throwable th5) {
                    th = th5;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeInt(requireFull ? 1 : 0);
                    _data.writeString(name);
                    _data.writeString(callerPackage);
                    boolean _status = this.mRemote.transact(81, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        int handleIncomingUser = Stub.getDefaultImpl().handleIncomingUser(callingPid, callingUid, userId, allowAll, requireFull, name, callerPackage);
                        _reply.recycle();
                        _data.recycle();
                        return handleIncomingUser;
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th6) {
                    th = th6;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            @Override // android.app.IActivityManager
            public void addPackageDependency(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    boolean _status = this.mRemote.transact(82, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().addPackageDependency(packageName);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void killApplication(String pkg, int appId, int userId, String reason) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(pkg);
                    _data.writeInt(appId);
                    _data.writeInt(userId);
                    _data.writeString(reason);
                    boolean _status = this.mRemote.transact(83, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().killApplication(pkg, appId, userId, reason);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void closeSystemDialogs(String reason) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(reason);
                    boolean _status = this.mRemote.transact(84, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().closeSystemDialogs(reason);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public Debug.MemoryInfo[] getProcessMemoryInfo(int[] pids) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeIntArray(pids);
                    boolean _status = this.mRemote.transact(85, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getProcessMemoryInfo(pids);
                    }
                    _reply.readException();
                    Debug.MemoryInfo[] _result = (Debug.MemoryInfo[]) _reply.createTypedArray(Debug.MemoryInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void killApplicationProcess(String processName, int uid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(processName);
                    _data.writeInt(uid);
                    boolean _status = this.mRemote.transact(86, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().killApplicationProcess(processName, uid);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean handleApplicationWtf(IBinder app, String tag, boolean system, ApplicationErrorReport.ParcelableCrashInfo crashInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(app);
                    _data.writeString(tag);
                    _data.writeInt(system ? 1 : 0);
                    if (crashInfo != null) {
                        _data.writeInt(1);
                        crashInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(87, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().handleApplicationWtf(app, tag, system, crashInfo);
                    }
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void killBackgroundProcesses(String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    boolean _status = this.mRemote.transact(88, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().killBackgroundProcesses(packageName, userId);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean isUserAMonkey() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(89, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isUserAMonkey();
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public List<ApplicationInfo> getRunningExternalApplications() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(90, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getRunningExternalApplications();
                    }
                    _reply.readException();
                    List<ApplicationInfo> _result = _reply.createTypedArrayList(ApplicationInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void finishHeavyWeightApp() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(91, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().finishHeavyWeightApp();
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void handleApplicationStrictModeViolation(IBinder app, int penaltyMask, StrictMode.ViolationInfo crashInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(app);
                    _data.writeInt(penaltyMask);
                    if (crashInfo != null) {
                        _data.writeInt(1);
                        crashInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(92, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().handleApplicationStrictModeViolation(app, penaltyMask, crashInfo);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean isTopActivityImmersive() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(93, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isTopActivityImmersive();
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void crashApplication(int uid, int initialPid, String packageName, int userId, String message) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeInt(initialPid);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    _data.writeString(message);
                    boolean _status = this.mRemote.transact(94, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().crashApplication(uid, initialPid, packageName, userId, message);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public String getProviderMimeType(Uri uri, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (uri != null) {
                        _data.writeInt(1);
                        uri.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(userId);
                    boolean _status = this.mRemote.transact(95, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getProviderMimeType(uri, userId);
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean dumpHeap(String process, int userId, boolean managed, boolean mallocInfo, boolean runGc, String path, ParcelFileDescriptor fd, RemoteCallback finishCallback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                } catch (Throwable th) {
                    th = th;
                }
                try {
                    _data.writeString(process);
                } catch (Throwable th2) {
                    th = th2;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeInt(userId);
                    _data.writeInt(managed ? 1 : 0);
                    _data.writeInt(mallocInfo ? 1 : 0);
                    _data.writeInt(runGc ? 1 : 0);
                    _data.writeString(path);
                    if (fd != null) {
                        _data.writeInt(1);
                        fd.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (finishCallback != null) {
                        _data.writeInt(1);
                        finishCallback.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(96, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        boolean dumpHeap = Stub.getDefaultImpl().dumpHeap(process, userId, managed, mallocInfo, runGc, path, fd, finishCallback);
                        _reply.recycle();
                        _data.recycle();
                        return dumpHeap;
                    }
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th3) {
                    th = th3;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            @Override // android.app.IActivityManager
            public boolean isUserRunning(int userid, int flags) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userid);
                    _data.writeInt(flags);
                    boolean _status = this.mRemote.transact(97, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isUserRunning(userid, flags);
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setPackageScreenCompatMode(String packageName, int mode) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(mode);
                    boolean _status = this.mRemote.transact(98, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setPackageScreenCompatMode(packageName, mode);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean switchUser(int userid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userid);
                    boolean _status = this.mRemote.transact(99, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().switchUser(userid);
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean removeTask(int taskId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(taskId);
                    boolean _status = this.mRemote.transact(100, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().removeTask(taskId);
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void registerProcessObserver(IProcessObserver observer) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(observer != null ? observer.asBinder() : null);
                    boolean _status = this.mRemote.transact(101, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registerProcessObserver(observer);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void unregisterProcessObserver(IProcessObserver observer) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(observer != null ? observer.asBinder() : null);
                    boolean _status = this.mRemote.transact(102, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unregisterProcessObserver(observer);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean isIntentSenderTargetedToPackage(IIntentSender sender) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(sender != null ? sender.asBinder() : null);
                    boolean _status = this.mRemote.transact(103, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isIntentSenderTargetedToPackage(sender);
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void updatePersistentConfiguration(Configuration values) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (values != null) {
                        _data.writeInt(1);
                        values.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(104, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().updatePersistentConfiguration(values);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public long[] getProcessPss(int[] pids) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeIntArray(pids);
                    boolean _status = this.mRemote.transact(105, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getProcessPss(pids);
                    }
                    _reply.readException();
                    long[] _result = _reply.createLongArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void showBootMessage(CharSequence msg, boolean always) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (msg != null) {
                        _data.writeInt(1);
                        TextUtils.writeToParcel(msg, _data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(always ? 1 : 0);
                    boolean _status = this.mRemote.transact(106, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().showBootMessage(msg, always);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void killAllBackgroundProcesses() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(107, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().killAllBackgroundProcesses();
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public ContentProviderHolder getContentProviderExternal(String name, int userId, IBinder token, String tag) throws RemoteException {
                ContentProviderHolder _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    _data.writeInt(userId);
                    _data.writeStrongBinder(token);
                    _data.writeString(tag);
                    boolean _status = this.mRemote.transact(108, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getContentProviderExternal(name, userId, token, tag);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = ContentProviderHolder.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void removeContentProviderExternal(String name, IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    _data.writeStrongBinder(token);
                    boolean _status = this.mRemote.transact(109, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeContentProviderExternal(name, token);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void removeContentProviderExternalAsUser(String name, IBinder token, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    _data.writeStrongBinder(token);
                    _data.writeInt(userId);
                    boolean _status = this.mRemote.transact(110, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeContentProviderExternalAsUser(name, token, userId);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void getMyMemoryState(ActivityManager.RunningAppProcessInfo outInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(111, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().getMyMemoryState(outInfo);
                        return;
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        outInfo.readFromParcel(_reply);
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean killProcessesBelowForeground(String reason) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(reason);
                    boolean _status = this.mRemote.transact(112, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().killProcessesBelowForeground(reason);
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public UserInfo getCurrentUser() throws RemoteException {
                UserInfo _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(113, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getCurrentUser();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = UserInfo.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int getLaunchedFromUid(IBinder activityToken) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(activityToken);
                    boolean _status = this.mRemote.transact(114, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getLaunchedFromUid(activityToken);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void unstableProviderDied(IBinder connection) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(connection);
                    boolean _status = this.mRemote.transact(115, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unstableProviderDied(connection);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean isIntentSenderAnActivity(IIntentSender sender) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(sender != null ? sender.asBinder() : null);
                    boolean _status = this.mRemote.transact(116, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isIntentSenderAnActivity(sender);
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean isIntentSenderAForegroundService(IIntentSender sender) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(sender != null ? sender.asBinder() : null);
                    boolean _status = this.mRemote.transact(117, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isIntentSenderAForegroundService(sender);
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean isIntentSenderABroadcast(IIntentSender sender) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(sender != null ? sender.asBinder() : null);
                    boolean _status = this.mRemote.transact(118, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isIntentSenderABroadcast(sender);
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int startActivityAsUser(IApplicationThread caller, String callingPackage, Intent intent, String resolvedType, IBinder resultTo, String resultWho, int requestCode, int flags, ProfilerInfo profilerInfo, Bundle options, int userId) throws RemoteException {
                Parcel _reply;
                Parcel _data;
                IBinder asBinder;
                Parcel _data2 = Parcel.obtain();
                Parcel _reply2 = Parcel.obtain();
                try {
                    _data2.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (caller != null) {
                        try {
                            asBinder = caller.asBinder();
                        } catch (Throwable th) {
                            th = th;
                            _reply = _reply2;
                            _data = _data2;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } else {
                        asBinder = null;
                    }
                    _data2.writeStrongBinder(asBinder);
                    _data2.writeString(callingPackage);
                    if (intent != null) {
                        _data2.writeInt(1);
                        intent.writeToParcel(_data2, 0);
                    } else {
                        _data2.writeInt(0);
                    }
                    _data2.writeString(resolvedType);
                    _data2.writeStrongBinder(resultTo);
                    _data2.writeString(resultWho);
                    _data2.writeInt(requestCode);
                    _data2.writeInt(flags);
                    if (profilerInfo != null) {
                        _data2.writeInt(1);
                        profilerInfo.writeToParcel(_data2, 0);
                    } else {
                        _data2.writeInt(0);
                    }
                    if (options != null) {
                        _data2.writeInt(1);
                        options.writeToParcel(_data2, 0);
                    } else {
                        _data2.writeInt(0);
                    }
                    _data2.writeInt(userId);
                    boolean _status = this.mRemote.transact(119, _data2, _reply2, 0);
                    try {
                        if (!_status && Stub.getDefaultImpl() != null) {
                            int startActivityAsUser = Stub.getDefaultImpl().startActivityAsUser(caller, callingPackage, intent, resolvedType, resultTo, resultWho, requestCode, flags, profilerInfo, options, userId);
                            _reply2.recycle();
                            _data2.recycle();
                            return startActivityAsUser;
                        }
                        _reply2.readException();
                        int _result = _reply2.readInt();
                        _reply2.recycle();
                        _data2.recycle();
                        return _result;
                    } catch (Throwable th2) {
                        th = th2;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    _reply = _reply2;
                    _data = _data2;
                }
            }

            @Override // android.app.IActivityManager
            public int stopUser(int userid, boolean force, IStopUserCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userid);
                    _data.writeInt(force ? 1 : 0);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    boolean _status = this.mRemote.transact(120, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().stopUser(userid, force, callback);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void registerUserSwitchObserver(IUserSwitchObserver observer, String name) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(observer != null ? observer.asBinder() : null);
                    _data.writeString(name);
                    boolean _status = this.mRemote.transact(121, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registerUserSwitchObserver(observer, name);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void unregisterUserSwitchObserver(IUserSwitchObserver observer) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(observer != null ? observer.asBinder() : null);
                    boolean _status = this.mRemote.transact(122, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unregisterUserSwitchObserver(observer);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int[] getRunningUserIds() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(123, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getRunningUserIds();
                    }
                    _reply.readException();
                    int[] _result = _reply.createIntArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void requestSystemServerHeapDump() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(124, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().requestSystemServerHeapDump();
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void requestBugReport(int bugreportType) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(bugreportType);
                    boolean _status = this.mRemote.transact(125, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().requestBugReport(bugreportType);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void requestTelephonyBugReport(String shareTitle, String shareDescription) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(shareTitle);
                    _data.writeString(shareDescription);
                    boolean _status = this.mRemote.transact(126, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().requestTelephonyBugReport(shareTitle, shareDescription);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void requestWifiBugReport(String shareTitle, String shareDescription) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(shareTitle);
                    _data.writeString(shareDescription);
                    boolean _status = this.mRemote.transact(127, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().requestWifiBugReport(shareTitle, shareDescription);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public Intent getIntentForIntentSender(IIntentSender sender) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    Intent _result = null;
                    _data.writeStrongBinder(sender != null ? sender.asBinder() : null);
                    boolean _status = this.mRemote.transact(128, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getIntentForIntentSender(sender);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = Intent.CREATOR.createFromParcel(_reply);
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public String getLaunchedFromPackage(IBinder activityToken) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(activityToken);
                    boolean _status = this.mRemote.transact(129, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getLaunchedFromPackage(activityToken);
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void killUid(int appId, int userId, String reason) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(appId);
                    _data.writeInt(userId);
                    _data.writeString(reason);
                    boolean _status = this.mRemote.transact(130, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().killUid(appId, userId, reason);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setUserIsMonkey(boolean monkey) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(monkey ? 1 : 0);
                    boolean _status = this.mRemote.transact(131, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setUserIsMonkey(monkey);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void hang(IBinder who, boolean allowRestart) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(who);
                    _data.writeInt(allowRestart ? 1 : 0);
                    boolean _status = this.mRemote.transact(132, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().hang(who, allowRestart);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public List<ActivityManager.StackInfo> getAllStackInfos() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(133, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAllStackInfos();
                    }
                    _reply.readException();
                    List<ActivityManager.StackInfo> _result = _reply.createTypedArrayList(ActivityManager.StackInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void moveTaskToStack(int taskId, int stackId, boolean toTop) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(taskId);
                    _data.writeInt(stackId);
                    _data.writeInt(toTop ? 1 : 0);
                    boolean _status = this.mRemote.transact(134, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().moveTaskToStack(taskId, stackId, toTop);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void resizeStack(int stackId, Rect bounds, boolean allowResizeInDockedMode, boolean preserveWindows, boolean animate, int animationDuration) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                } catch (Throwable th) {
                    th = th;
                }
                try {
                    _data.writeInt(stackId);
                    if (bounds != null) {
                        _data.writeInt(1);
                        bounds.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                } catch (Throwable th2) {
                    th = th2;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeInt(allowResizeInDockedMode ? 1 : 0);
                } catch (Throwable th3) {
                    th = th3;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeInt(preserveWindows ? 1 : 0);
                } catch (Throwable th4) {
                    th = th4;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeInt(animate ? 1 : 0);
                    try {
                        _data.writeInt(animationDuration);
                        boolean _status = this.mRemote.transact(135, _data, _reply, 0);
                        if (!_status && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().resizeStack(stackId, bounds, allowResizeInDockedMode, preserveWindows, animate, animationDuration);
                            _reply.recycle();
                            _data.recycle();
                            return;
                        }
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                    } catch (Throwable th5) {
                        th = th5;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th6) {
                    th = th6;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            @Override // android.app.IActivityManager
            public void setFocusedStack(int stackId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(stackId);
                    boolean _status = this.mRemote.transact(136, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setFocusedStack(stackId);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public ActivityManager.StackInfo getFocusedStackInfo() throws RemoteException {
                ActivityManager.StackInfo _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(137, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getFocusedStackInfo();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = ActivityManager.StackInfo.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void restart() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(138, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().restart();
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void performIdleMaintenance() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(139, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().performIdleMaintenance();
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void appNotRespondingViaProvider(IBinder connection) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(connection);
                    boolean _status = this.mRemote.transact(140, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().appNotRespondingViaProvider(connection);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public Rect getTaskBounds(int taskId) throws RemoteException {
                Rect _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(taskId);
                    boolean _status = this.mRemote.transact(141, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getTaskBounds(taskId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = Rect.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean setProcessMemoryTrimLevel(String process, int uid, int level) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(process);
                    _data.writeInt(uid);
                    _data.writeInt(level);
                    boolean _status = this.mRemote.transact(142, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setProcessMemoryTrimLevel(process, uid, level);
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public String getTagForIntentSender(IIntentSender sender, String prefix) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(sender != null ? sender.asBinder() : null);
                    _data.writeString(prefix);
                    boolean _status = this.mRemote.transact(143, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getTagForIntentSender(sender, prefix);
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean startUserInBackground(int userid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userid);
                    boolean _status = this.mRemote.transact(144, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().startUserInBackground(userid);
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean isInLockTaskMode() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(145, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isInLockTaskMode();
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void startRecentsActivity(Intent intent, IAssistDataReceiver assistDataReceiver, IRecentsAnimationRunner recentsAnimationRunner) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (intent != null) {
                        _data.writeInt(1);
                        intent.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeStrongBinder(assistDataReceiver != null ? assistDataReceiver.asBinder() : null);
                    _data.writeStrongBinder(recentsAnimationRunner != null ? recentsAnimationRunner.asBinder() : null);
                    boolean _status = this.mRemote.transact(146, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().startRecentsActivity(intent, assistDataReceiver, recentsAnimationRunner);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void cancelRecentsAnimation(boolean restoreHomeStackPosition) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(restoreHomeStackPosition ? 1 : 0);
                    boolean _status = this.mRemote.transact(147, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().cancelRecentsAnimation(restoreHomeStackPosition);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int startActivityFromRecents(int taskId, Bundle options) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(taskId);
                    if (options != null) {
                        _data.writeInt(1);
                        options.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(148, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().startActivityFromRecents(taskId, options);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void startSystemLockTaskMode(int taskId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(taskId);
                    boolean _status = this.mRemote.transact(149, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().startSystemLockTaskMode(taskId);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean isTopOfTask(IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    boolean _status = this.mRemote.transact(150, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isTopOfTask(token);
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void bootAnimationComplete() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(151, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().bootAnimationComplete();
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int checkPermissionWithToken(String permission, int pid, int uid, IBinder callerToken) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(permission);
                    _data.writeInt(pid);
                    _data.writeInt(uid);
                    _data.writeStrongBinder(callerToken);
                    boolean _status = this.mRemote.transact(152, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().checkPermissionWithToken(permission, pid, uid, callerToken);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void registerTaskStackListener(ITaskStackListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    boolean _status = this.mRemote.transact(153, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registerTaskStackListener(listener);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void unregisterTaskStackListener(ITaskStackListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    boolean _status = this.mRemote.transact(154, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unregisterTaskStackListener(listener);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void notifyCleartextNetwork(int uid, byte[] firstPacket) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeByteArray(firstPacket);
                    boolean _status = this.mRemote.transact(155, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifyCleartextNetwork(uid, firstPacket);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setTaskResizeable(int taskId, int resizeableMode) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(taskId);
                    _data.writeInt(resizeableMode);
                    boolean _status = this.mRemote.transact(156, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setTaskResizeable(taskId, resizeableMode);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void resizeTask(int taskId, Rect bounds, int resizeMode) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(taskId);
                    if (bounds != null) {
                        _data.writeInt(1);
                        bounds.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(resizeMode);
                    boolean _status = this.mRemote.transact(157, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().resizeTask(taskId, bounds, resizeMode);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int getLockTaskModeState() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(158, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getLockTaskModeState();
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setDumpHeapDebugLimit(String processName, int uid, long maxMemSize, String reportPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(processName);
                    _data.writeInt(uid);
                    _data.writeLong(maxMemSize);
                    _data.writeString(reportPackage);
                    boolean _status = this.mRemote.transact(159, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setDumpHeapDebugLimit(processName, uid, maxMemSize, reportPackage);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void dumpHeapFinished(String path) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(path);
                    boolean _status = this.mRemote.transact(160, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().dumpHeapFinished(path);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void updateLockTaskPackages(int userId, String[] packages) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeStringArray(packages);
                    boolean _status = this.mRemote.transact(161, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().updateLockTaskPackages(userId, packages);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void noteAlarmStart(IIntentSender sender, WorkSource workSource, int sourceUid, String tag) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(sender != null ? sender.asBinder() : null);
                    if (workSource != null) {
                        _data.writeInt(1);
                        workSource.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(sourceUid);
                    _data.writeString(tag);
                    boolean _status = this.mRemote.transact(162, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().noteAlarmStart(sender, workSource, sourceUid, tag);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void noteAlarmFinish(IIntentSender sender, WorkSource workSource, int sourceUid, String tag) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(sender != null ? sender.asBinder() : null);
                    if (workSource != null) {
                        _data.writeInt(1);
                        workSource.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(sourceUid);
                    _data.writeString(tag);
                    boolean _status = this.mRemote.transact(163, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().noteAlarmFinish(sender, workSource, sourceUid, tag);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int getPackageProcessState(String packageName, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeString(callingPackage);
                    boolean _status = this.mRemote.transact(164, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPackageProcessState(packageName, callingPackage);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void updateDeviceOwner(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    boolean _status = this.mRemote.transact(165, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().updateDeviceOwner(packageName);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean startBinderTracking() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(166, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().startBinderTracking();
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean stopBinderTrackingAndDump(ParcelFileDescriptor fd) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (fd != null) {
                        _data.writeInt(1);
                        fd.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(167, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().stopBinderTrackingAndDump(fd);
                    }
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void positionTaskInStack(int taskId, int stackId, int position) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(taskId);
                    _data.writeInt(stackId);
                    _data.writeInt(position);
                    boolean _status = this.mRemote.transact(168, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().positionTaskInStack(taskId, stackId, position);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void suppressResizeConfigChanges(boolean suppress) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(suppress ? 1 : 0);
                    boolean _status = this.mRemote.transact(169, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().suppressResizeConfigChanges(suppress);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean moveTopActivityToPinnedStack(int stackId, Rect bounds) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(stackId);
                    if (bounds != null) {
                        _data.writeInt(1);
                        bounds.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(170, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().moveTopActivityToPinnedStack(stackId, bounds);
                    }
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean isAppStartModeDisabled(int uid, String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeString(packageName);
                    boolean _status = this.mRemote.transact(171, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isAppStartModeDisabled(uid, packageName);
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean unlockUser(int userid, byte[] token, byte[] secret, IProgressListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userid);
                    _data.writeByteArray(token);
                    _data.writeByteArray(secret);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    boolean _status = this.mRemote.transact(172, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().unlockUser(userid, token, secret, listener);
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void killPackageDependents(String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    boolean _status = this.mRemote.transact(173, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().killPackageDependents(packageName, userId);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void resizeDockedStack(Rect dockedBounds, Rect tempDockedTaskBounds, Rect tempDockedTaskInsetBounds, Rect tempOtherTaskBounds, Rect tempOtherTaskInsetBounds) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (dockedBounds != null) {
                        _data.writeInt(1);
                        dockedBounds.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (tempDockedTaskBounds != null) {
                        _data.writeInt(1);
                        tempDockedTaskBounds.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (tempDockedTaskInsetBounds != null) {
                        _data.writeInt(1);
                        tempDockedTaskInsetBounds.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (tempOtherTaskBounds != null) {
                        _data.writeInt(1);
                        tempOtherTaskBounds.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (tempOtherTaskInsetBounds != null) {
                        _data.writeInt(1);
                        tempOtherTaskInsetBounds.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(174, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().resizeDockedStack(dockedBounds, tempDockedTaskBounds, tempDockedTaskInsetBounds, tempOtherTaskBounds, tempOtherTaskInsetBounds);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void removeStack(int stackId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(stackId);
                    boolean _status = this.mRemote.transact(175, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeStack(stackId);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void makePackageIdle(String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    boolean _status = this.mRemote.transact(176, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().makePackageIdle(packageName, userId);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int getMemoryTrimLevel() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(177, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getMemoryTrimLevel();
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean isVrModePackageEnabled(ComponentName packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (packageName != null) {
                        _data.writeInt(1);
                        packageName.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(178, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isVrModePackageEnabled(packageName);
                    }
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void notifyLockedProfile(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    boolean _status = this.mRemote.transact(179, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifyLockedProfile(userId);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void startConfirmDeviceCredentialIntent(Intent intent, Bundle options) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (intent != null) {
                        _data.writeInt(1);
                        intent.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (options != null) {
                        _data.writeInt(1);
                        options.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(180, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().startConfirmDeviceCredentialIntent(intent, options);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void sendIdleJobTrigger() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(181, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().sendIdleJobTrigger();
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int sendIntentSender(IIntentSender target, IBinder whitelistToken, int code, Intent intent, String resolvedType, IIntentReceiver finishedReceiver, String requiredPermission, Bundle options) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(target != null ? target.asBinder() : null);
                } catch (Throwable th) {
                    th = th;
                }
                try {
                    _data.writeStrongBinder(whitelistToken);
                    _data.writeInt(code);
                    if (intent != null) {
                        _data.writeInt(1);
                        intent.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(resolvedType);
                    _data.writeStrongBinder(finishedReceiver != null ? finishedReceiver.asBinder() : null);
                    _data.writeString(requiredPermission);
                    if (options != null) {
                        _data.writeInt(1);
                        options.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(182, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        int sendIntentSender = Stub.getDefaultImpl().sendIntentSender(target, whitelistToken, code, intent, resolvedType, finishedReceiver, requiredPermission, options);
                        _reply.recycle();
                        _data.recycle();
                        return sendIntentSender;
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th2) {
                    th = th2;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            @Override // android.app.IActivityManager
            public boolean isBackgroundRestricted(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    boolean _status = this.mRemote.transact(183, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isBackgroundRestricted(packageName);
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setRenderThread(int tid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(tid);
                    boolean _status = this.mRemote.transact(184, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setRenderThread(tid);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setHasTopUi(boolean hasTopUi) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(hasTopUi ? 1 : 0);
                    boolean _status = this.mRemote.transact(185, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setHasTopUi(hasTopUi);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int restartUserInBackground(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    boolean _status = this.mRemote.transact(186, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().restartUserInBackground(userId);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void cancelTaskWindowTransition(int taskId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(taskId);
                    boolean _status = this.mRemote.transact(187, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().cancelTaskWindowTransition(taskId);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public ActivityManager.TaskSnapshot getTaskSnapshot(int taskId, boolean reducedResolution) throws RemoteException {
                ActivityManager.TaskSnapshot _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(taskId);
                    _data.writeInt(reducedResolution ? 1 : 0);
                    boolean _status = this.mRemote.transact(188, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getTaskSnapshot(taskId, reducedResolution);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = ActivityManager.TaskSnapshot.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void scheduleApplicationInfoChanged(List<String> packageNames, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStringList(packageNames);
                    _data.writeInt(userId);
                    boolean _status = this.mRemote.transact(189, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().scheduleApplicationInfoChanged(packageNames, userId);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setPersistentVrThread(int tid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(tid);
                    boolean _status = this.mRemote.transact(190, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setPersistentVrThread(tid);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void waitForNetworkStateUpdate(long procStateSeq) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(procStateSeq);
                    boolean _status = this.mRemote.transact(191, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().waitForNetworkStateUpdate(procStateSeq);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void backgroundWhitelistUid(int uid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    boolean _status = this.mRemote.transact(192, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().backgroundWhitelistUid(uid);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean startUserInBackgroundWithListener(int userid, IProgressListener unlockProgressListener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userid);
                    _data.writeStrongBinder(unlockProgressListener != null ? unlockProgressListener.asBinder() : null);
                    boolean _status = this.mRemote.transact(193, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().startUserInBackgroundWithListener(userid, unlockProgressListener);
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void startDelegateShellPermissionIdentity(int uid, String[] permissions) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeStringArray(permissions);
                    boolean _status = this.mRemote.transact(194, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().startDelegateShellPermissionIdentity(uid, permissions);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void stopDelegateShellPermissionIdentity() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(195, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().stopDelegateShellPermissionIdentity();
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public ParcelFileDescriptor getLifeMonitor() throws RemoteException {
                ParcelFileDescriptor _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(196, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getLifeMonitor();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = ParcelFileDescriptor.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean startUserInForegroundWithListener(int userid, IProgressListener unlockProgressListener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userid);
                    _data.writeStrongBinder(unlockProgressListener != null ? unlockProgressListener.asBinder() : null);
                    boolean _status = this.mRemote.transact(197, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().startUserInForegroundWithListener(userid, unlockProgressListener);
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IActivityManager impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static IActivityManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
