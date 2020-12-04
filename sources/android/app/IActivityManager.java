package android.app;

import android.annotation.UnsupportedAppUsage;
import android.app.ActivityManager;
import android.app.ApplicationErrorReport;
import android.content.ComponentName;
import android.content.IIntentReceiver;
import android.content.IIntentSender;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageDataObserver;
import android.content.pm.ParceledListSlice;
import android.content.pm.UserInfo;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.Debug;
import android.os.IBinder;
import android.os.IInterface;
import android.os.IProgressListener;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.os.StrictMode;
import android.os.WorkSource;
import android.text.TextUtils;
import android.view.IRecentsAnimationRunner;
import com.android.internal.os.IResultReceiver;
import java.util.List;

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

    public static class Default implements IActivityManager {
        public ParcelFileDescriptor openContentUri(String uriString) throws RemoteException {
            return null;
        }

        public void registerUidObserver(IUidObserver observer, int which, int cutpoint, String callingPackage) throws RemoteException {
        }

        public void unregisterUidObserver(IUidObserver observer) throws RemoteException {
        }

        public boolean isUidActive(int uid, String callingPackage) throws RemoteException {
            return false;
        }

        public int getUidProcessState(int uid, String callingPackage) throws RemoteException {
            return 0;
        }

        public void handleApplicationCrash(IBinder app, ApplicationErrorReport.ParcelableCrashInfo crashInfo) throws RemoteException {
        }

        public int startActivity(IApplicationThread caller, String callingPackage, Intent intent, String resolvedType, IBinder resultTo, String resultWho, int requestCode, int flags, ProfilerInfo profilerInfo, Bundle options) throws RemoteException {
            return 0;
        }

        public void unhandledBack() throws RemoteException {
        }

        public boolean finishActivity(IBinder token, int code, Intent data, int finishTask) throws RemoteException {
            return false;
        }

        public Intent registerReceiver(IApplicationThread caller, String callerPackage, IIntentReceiver receiver, IntentFilter filter, String requiredPermission, int userId, int flags) throws RemoteException {
            return null;
        }

        public void unregisterReceiver(IIntentReceiver receiver) throws RemoteException {
        }

        public int broadcastIntent(IApplicationThread caller, Intent intent, String resolvedType, IIntentReceiver resultTo, int resultCode, String resultData, Bundle map, String[] requiredPermissions, int appOp, Bundle options, boolean serialized, boolean sticky, int userId) throws RemoteException {
            return 0;
        }

        public void unbroadcastIntent(IApplicationThread caller, Intent intent, int userId) throws RemoteException {
        }

        public void finishReceiver(IBinder who, int resultCode, String resultData, Bundle map, boolean abortBroadcast, int flags) throws RemoteException {
        }

        public void attachApplication(IApplicationThread app, long startSeq) throws RemoteException {
        }

        public List<ActivityManager.RunningTaskInfo> getTasks(int maxNum) throws RemoteException {
            return null;
        }

        public List<ActivityManager.RunningTaskInfo> getFilteredTasks(int maxNum, int ignoreActivityType, int ignoreWindowingMode) throws RemoteException {
            return null;
        }

        public void moveTaskToFront(IApplicationThread caller, String callingPackage, int task, int flags, Bundle options) throws RemoteException {
        }

        public int getTaskForActivity(IBinder token, boolean onlyRoot) throws RemoteException {
            return 0;
        }

        public ContentProviderHolder getContentProvider(IApplicationThread caller, String callingPackage, String name, int userId, boolean stable) throws RemoteException {
            return null;
        }

        public void publishContentProviders(IApplicationThread caller, List<ContentProviderHolder> list) throws RemoteException {
        }

        public boolean refContentProvider(IBinder connection, int stableDelta, int unstableDelta) throws RemoteException {
            return false;
        }

        public PendingIntent getRunningServiceControlPanel(ComponentName service) throws RemoteException {
            return null;
        }

        public ComponentName startService(IApplicationThread caller, Intent service, String resolvedType, boolean requireForeground, String callingPackage, int userId) throws RemoteException {
            return null;
        }

        public int stopService(IApplicationThread caller, Intent service, String resolvedType, int userId) throws RemoteException {
            return 0;
        }

        public int bindService(IApplicationThread caller, IBinder token, Intent service, String resolvedType, IServiceConnection connection, int flags, String callingPackage, int userId) throws RemoteException {
            return 0;
        }

        public int bindIsolatedService(IApplicationThread caller, IBinder token, Intent service, String resolvedType, IServiceConnection connection, int flags, String instanceName, String callingPackage, int userId) throws RemoteException {
            return 0;
        }

        public void updateServiceGroup(IServiceConnection connection, int group, int importance) throws RemoteException {
        }

        public boolean unbindService(IServiceConnection connection) throws RemoteException {
            return false;
        }

        public void publishService(IBinder token, Intent intent, IBinder service) throws RemoteException {
        }

        public void setDebugApp(String packageName, boolean waitForDebugger, boolean persistent) throws RemoteException {
        }

        public void setAgentApp(String packageName, String agent) throws RemoteException {
        }

        public void setAlwaysFinish(boolean enabled) throws RemoteException {
        }

        public boolean startInstrumentation(ComponentName className, String profileFile, int flags, Bundle arguments, IInstrumentationWatcher watcher, IUiAutomationConnection connection, int userId, String abiOverride) throws RemoteException {
            return false;
        }

        public void addInstrumentationResults(IApplicationThread target, Bundle results) throws RemoteException {
        }

        public void finishInstrumentation(IApplicationThread target, int resultCode, Bundle results) throws RemoteException {
        }

        public Configuration getConfiguration() throws RemoteException {
            return null;
        }

        public boolean updateConfiguration(Configuration values) throws RemoteException {
            return false;
        }

        public boolean stopServiceToken(ComponentName className, IBinder token, int startId) throws RemoteException {
            return false;
        }

        public void setProcessLimit(int max) throws RemoteException {
        }

        public int getProcessLimit() throws RemoteException {
            return 0;
        }

        public int checkPermission(String permission, int pid, int uid) throws RemoteException {
            return 0;
        }

        public int checkUriPermission(Uri uri, int pid, int uid, int mode, int userId, IBinder callerToken) throws RemoteException {
            return 0;
        }

        public void grantUriPermission(IApplicationThread caller, String targetPkg, Uri uri, int mode, int userId) throws RemoteException {
        }

        public void revokeUriPermission(IApplicationThread caller, String targetPkg, Uri uri, int mode, int userId) throws RemoteException {
        }

        public void setActivityController(IActivityController watcher, boolean imAMonkey) throws RemoteException {
        }

        public void showWaitingForDebugger(IApplicationThread who, boolean waiting) throws RemoteException {
        }

        public void signalPersistentProcesses(int signal) throws RemoteException {
        }

        public ParceledListSlice getRecentTasks(int maxNum, int flags, int userId) throws RemoteException {
            return null;
        }

        public void serviceDoneExecuting(IBinder token, int type, int startId, int res) throws RemoteException {
        }

        public IIntentSender getIntentSender(int type, String packageName, IBinder token, String resultWho, int requestCode, Intent[] intents, String[] resolvedTypes, int flags, Bundle options, int userId) throws RemoteException {
            return null;
        }

        public void cancelIntentSender(IIntentSender sender) throws RemoteException {
        }

        public String getPackageForIntentSender(IIntentSender sender) throws RemoteException {
            return null;
        }

        public void registerIntentSenderCancelListener(IIntentSender sender, IResultReceiver receiver) throws RemoteException {
        }

        public void unregisterIntentSenderCancelListener(IIntentSender sender, IResultReceiver receiver) throws RemoteException {
        }

        public void enterSafeMode() throws RemoteException {
        }

        public void noteWakeupAlarm(IIntentSender sender, WorkSource workSource, int sourceUid, String sourcePkg, String tag) throws RemoteException {
        }

        public void removeContentProvider(IBinder connection, boolean stable) throws RemoteException {
        }

        public void setRequestedOrientation(IBinder token, int requestedOrientation) throws RemoteException {
        }

        public void unbindFinished(IBinder token, Intent service, boolean doRebind) throws RemoteException {
        }

        public void setProcessImportant(IBinder token, int pid, boolean isForeground, String reason) throws RemoteException {
        }

        public void setServiceForeground(ComponentName className, IBinder token, int id, Notification notification, int flags, int foregroundServiceType) throws RemoteException {
        }

        public int getForegroundServiceType(ComponentName className, IBinder token) throws RemoteException {
            return 0;
        }

        public boolean moveActivityTaskToBack(IBinder token, boolean nonRoot) throws RemoteException {
            return false;
        }

        public void getMemoryInfo(ActivityManager.MemoryInfo outInfo) throws RemoteException {
        }

        public List<ActivityManager.ProcessErrorStateInfo> getProcessesInErrorState() throws RemoteException {
            return null;
        }

        public boolean clearApplicationUserData(String packageName, boolean keepState, IPackageDataObserver observer, int userId) throws RemoteException {
            return false;
        }

        public void forceStopPackage(String packageName, int userId) throws RemoteException {
        }

        public boolean killPids(int[] pids, String reason, boolean secure) throws RemoteException {
            return false;
        }

        public List<ActivityManager.RunningServiceInfo> getServices(int maxNum, int flags) throws RemoteException {
            return null;
        }

        public List<ActivityManager.RunningAppProcessInfo> getRunningAppProcesses() throws RemoteException {
            return null;
        }

        public IBinder peekService(Intent service, String resolvedType, String callingPackage) throws RemoteException {
            return null;
        }

        public boolean profileControl(String process, int userId, boolean start, ProfilerInfo profilerInfo, int profileType) throws RemoteException {
            return false;
        }

        public boolean shutdown(int timeout) throws RemoteException {
            return false;
        }

        public void stopAppSwitches() throws RemoteException {
        }

        public void resumeAppSwitches() throws RemoteException {
        }

        public boolean bindBackupAgent(String packageName, int backupRestoreMode, int targetUserId) throws RemoteException {
            return false;
        }

        public void backupAgentCreated(String packageName, IBinder agent, int userId) throws RemoteException {
        }

        public void unbindBackupAgent(ApplicationInfo appInfo) throws RemoteException {
        }

        public int getUidForIntentSender(IIntentSender sender) throws RemoteException {
            return 0;
        }

        public int handleIncomingUser(int callingPid, int callingUid, int userId, boolean allowAll, boolean requireFull, String name, String callerPackage) throws RemoteException {
            return 0;
        }

        public void addPackageDependency(String packageName) throws RemoteException {
        }

        public void killApplication(String pkg, int appId, int userId, String reason) throws RemoteException {
        }

        public void closeSystemDialogs(String reason) throws RemoteException {
        }

        public Debug.MemoryInfo[] getProcessMemoryInfo(int[] pids) throws RemoteException {
            return null;
        }

        public void killApplicationProcess(String processName, int uid) throws RemoteException {
        }

        public boolean handleApplicationWtf(IBinder app, String tag, boolean system, ApplicationErrorReport.ParcelableCrashInfo crashInfo) throws RemoteException {
            return false;
        }

        public void killBackgroundProcesses(String packageName, int userId) throws RemoteException {
        }

        public boolean isUserAMonkey() throws RemoteException {
            return false;
        }

        public List<ApplicationInfo> getRunningExternalApplications() throws RemoteException {
            return null;
        }

        public void finishHeavyWeightApp() throws RemoteException {
        }

        public void handleApplicationStrictModeViolation(IBinder app, int penaltyMask, StrictMode.ViolationInfo crashInfo) throws RemoteException {
        }

        public boolean isTopActivityImmersive() throws RemoteException {
            return false;
        }

        public void crashApplication(int uid, int initialPid, String packageName, int userId, String message) throws RemoteException {
        }

        public String getProviderMimeType(Uri uri, int userId) throws RemoteException {
            return null;
        }

        public boolean dumpHeap(String process, int userId, boolean managed, boolean mallocInfo, boolean runGc, String path, ParcelFileDescriptor fd, RemoteCallback finishCallback) throws RemoteException {
            return false;
        }

        public boolean isUserRunning(int userid, int flags) throws RemoteException {
            return false;
        }

        public void setPackageScreenCompatMode(String packageName, int mode) throws RemoteException {
        }

        public boolean switchUser(int userid) throws RemoteException {
            return false;
        }

        public boolean removeTask(int taskId) throws RemoteException {
            return false;
        }

        public void registerProcessObserver(IProcessObserver observer) throws RemoteException {
        }

        public void unregisterProcessObserver(IProcessObserver observer) throws RemoteException {
        }

        public boolean isIntentSenderTargetedToPackage(IIntentSender sender) throws RemoteException {
            return false;
        }

        public void updatePersistentConfiguration(Configuration values) throws RemoteException {
        }

        public long[] getProcessPss(int[] pids) throws RemoteException {
            return null;
        }

        public void showBootMessage(CharSequence msg, boolean always) throws RemoteException {
        }

        public void killAllBackgroundProcesses() throws RemoteException {
        }

        public ContentProviderHolder getContentProviderExternal(String name, int userId, IBinder token, String tag) throws RemoteException {
            return null;
        }

        public void removeContentProviderExternal(String name, IBinder token) throws RemoteException {
        }

        public void removeContentProviderExternalAsUser(String name, IBinder token, int userId) throws RemoteException {
        }

        public void getMyMemoryState(ActivityManager.RunningAppProcessInfo outInfo) throws RemoteException {
        }

        public boolean killProcessesBelowForeground(String reason) throws RemoteException {
            return false;
        }

        public UserInfo getCurrentUser() throws RemoteException {
            return null;
        }

        public int getLaunchedFromUid(IBinder activityToken) throws RemoteException {
            return 0;
        }

        public void unstableProviderDied(IBinder connection) throws RemoteException {
        }

        public boolean isIntentSenderAnActivity(IIntentSender sender) throws RemoteException {
            return false;
        }

        public boolean isIntentSenderAForegroundService(IIntentSender sender) throws RemoteException {
            return false;
        }

        public boolean isIntentSenderABroadcast(IIntentSender sender) throws RemoteException {
            return false;
        }

        public int startActivityAsUser(IApplicationThread caller, String callingPackage, Intent intent, String resolvedType, IBinder resultTo, String resultWho, int requestCode, int flags, ProfilerInfo profilerInfo, Bundle options, int userId) throws RemoteException {
            return 0;
        }

        public int stopUser(int userid, boolean force, IStopUserCallback callback) throws RemoteException {
            return 0;
        }

        public void registerUserSwitchObserver(IUserSwitchObserver observer, String name) throws RemoteException {
        }

        public void unregisterUserSwitchObserver(IUserSwitchObserver observer) throws RemoteException {
        }

        public int[] getRunningUserIds() throws RemoteException {
            return null;
        }

        public void requestSystemServerHeapDump() throws RemoteException {
        }

        public void requestBugReport(int bugreportType) throws RemoteException {
        }

        public void requestTelephonyBugReport(String shareTitle, String shareDescription) throws RemoteException {
        }

        public void requestWifiBugReport(String shareTitle, String shareDescription) throws RemoteException {
        }

        public Intent getIntentForIntentSender(IIntentSender sender) throws RemoteException {
            return null;
        }

        public String getLaunchedFromPackage(IBinder activityToken) throws RemoteException {
            return null;
        }

        public void killUid(int appId, int userId, String reason) throws RemoteException {
        }

        public void setUserIsMonkey(boolean monkey) throws RemoteException {
        }

        public void hang(IBinder who, boolean allowRestart) throws RemoteException {
        }

        public List<ActivityManager.StackInfo> getAllStackInfos() throws RemoteException {
            return null;
        }

        public void moveTaskToStack(int taskId, int stackId, boolean toTop) throws RemoteException {
        }

        public void resizeStack(int stackId, Rect bounds, boolean allowResizeInDockedMode, boolean preserveWindows, boolean animate, int animationDuration) throws RemoteException {
        }

        public void setFocusedStack(int stackId) throws RemoteException {
        }

        public ActivityManager.StackInfo getFocusedStackInfo() throws RemoteException {
            return null;
        }

        public void restart() throws RemoteException {
        }

        public void performIdleMaintenance() throws RemoteException {
        }

        public void appNotRespondingViaProvider(IBinder connection) throws RemoteException {
        }

        public Rect getTaskBounds(int taskId) throws RemoteException {
            return null;
        }

        public boolean setProcessMemoryTrimLevel(String process, int uid, int level) throws RemoteException {
            return false;
        }

        public String getTagForIntentSender(IIntentSender sender, String prefix) throws RemoteException {
            return null;
        }

        public boolean startUserInBackground(int userid) throws RemoteException {
            return false;
        }

        public boolean isInLockTaskMode() throws RemoteException {
            return false;
        }

        public void startRecentsActivity(Intent intent, IAssistDataReceiver assistDataReceiver, IRecentsAnimationRunner recentsAnimationRunner) throws RemoteException {
        }

        public void cancelRecentsAnimation(boolean restoreHomeStackPosition) throws RemoteException {
        }

        public int startActivityFromRecents(int taskId, Bundle options) throws RemoteException {
            return 0;
        }

        public void startSystemLockTaskMode(int taskId) throws RemoteException {
        }

        public boolean isTopOfTask(IBinder token) throws RemoteException {
            return false;
        }

        public void bootAnimationComplete() throws RemoteException {
        }

        public int checkPermissionWithToken(String permission, int pid, int uid, IBinder callerToken) throws RemoteException {
            return 0;
        }

        public void registerTaskStackListener(ITaskStackListener listener) throws RemoteException {
        }

        public void unregisterTaskStackListener(ITaskStackListener listener) throws RemoteException {
        }

        public void notifyCleartextNetwork(int uid, byte[] firstPacket) throws RemoteException {
        }

        public void setTaskResizeable(int taskId, int resizeableMode) throws RemoteException {
        }

        public void resizeTask(int taskId, Rect bounds, int resizeMode) throws RemoteException {
        }

        public int getLockTaskModeState() throws RemoteException {
            return 0;
        }

        public void setDumpHeapDebugLimit(String processName, int uid, long maxMemSize, String reportPackage) throws RemoteException {
        }

        public void dumpHeapFinished(String path) throws RemoteException {
        }

        public void updateLockTaskPackages(int userId, String[] packages) throws RemoteException {
        }

        public void noteAlarmStart(IIntentSender sender, WorkSource workSource, int sourceUid, String tag) throws RemoteException {
        }

        public void noteAlarmFinish(IIntentSender sender, WorkSource workSource, int sourceUid, String tag) throws RemoteException {
        }

        public int getPackageProcessState(String packageName, String callingPackage) throws RemoteException {
            return 0;
        }

        public void updateDeviceOwner(String packageName) throws RemoteException {
        }

        public boolean startBinderTracking() throws RemoteException {
            return false;
        }

        public boolean stopBinderTrackingAndDump(ParcelFileDescriptor fd) throws RemoteException {
            return false;
        }

        public void positionTaskInStack(int taskId, int stackId, int position) throws RemoteException {
        }

        public void suppressResizeConfigChanges(boolean suppress) throws RemoteException {
        }

        public boolean moveTopActivityToPinnedStack(int stackId, Rect bounds) throws RemoteException {
            return false;
        }

        public boolean isAppStartModeDisabled(int uid, String packageName) throws RemoteException {
            return false;
        }

        public boolean unlockUser(int userid, byte[] token, byte[] secret, IProgressListener listener) throws RemoteException {
            return false;
        }

        public void killPackageDependents(String packageName, int userId) throws RemoteException {
        }

        public void resizeDockedStack(Rect dockedBounds, Rect tempDockedTaskBounds, Rect tempDockedTaskInsetBounds, Rect tempOtherTaskBounds, Rect tempOtherTaskInsetBounds) throws RemoteException {
        }

        public void removeStack(int stackId) throws RemoteException {
        }

        public void makePackageIdle(String packageName, int userId) throws RemoteException {
        }

        public int getMemoryTrimLevel() throws RemoteException {
            return 0;
        }

        public boolean isVrModePackageEnabled(ComponentName packageName) throws RemoteException {
            return false;
        }

        public void notifyLockedProfile(int userId) throws RemoteException {
        }

        public void startConfirmDeviceCredentialIntent(Intent intent, Bundle options) throws RemoteException {
        }

        public void sendIdleJobTrigger() throws RemoteException {
        }

        public int sendIntentSender(IIntentSender target, IBinder whitelistToken, int code, Intent intent, String resolvedType, IIntentReceiver finishedReceiver, String requiredPermission, Bundle options) throws RemoteException {
            return 0;
        }

        public boolean isBackgroundRestricted(String packageName) throws RemoteException {
            return false;
        }

        public void setRenderThread(int tid) throws RemoteException {
        }

        public void setHasTopUi(boolean hasTopUi) throws RemoteException {
        }

        public int restartUserInBackground(int userId) throws RemoteException {
            return 0;
        }

        public void cancelTaskWindowTransition(int taskId) throws RemoteException {
        }

        public ActivityManager.TaskSnapshot getTaskSnapshot(int taskId, boolean reducedResolution) throws RemoteException {
            return null;
        }

        public void scheduleApplicationInfoChanged(List<String> list, int userId) throws RemoteException {
        }

        public void setPersistentVrThread(int tid) throws RemoteException {
        }

        public void waitForNetworkStateUpdate(long procStateSeq) throws RemoteException {
        }

        public void backgroundWhitelistUid(int uid) throws RemoteException {
        }

        public boolean startUserInBackgroundWithListener(int userid, IProgressListener unlockProgressListener) throws RemoteException {
            return false;
        }

        public void startDelegateShellPermissionIdentity(int uid, String[] permissions) throws RemoteException {
        }

        public void stopDelegateShellPermissionIdentity() throws RemoteException {
        }

        public ParcelFileDescriptor getLifeMonitor() throws RemoteException {
            return null;
        }

        public boolean startUserInForegroundWithListener(int userid, IProgressListener unlockProgressListener) throws RemoteException {
            return false;
        }

        public IBinder asBinder() {
            return null;
        }
    }

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
            if (iin == null || !(iin instanceof IActivityManager)) {
                return new Proxy(obj);
            }
            return (IActivityManager) iin;
        }

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

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v0, resolved type: android.app.ApplicationErrorReport$ParcelableCrashInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v1, resolved type: android.app.ApplicationErrorReport$ParcelableCrashInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v4, resolved type: android.app.ApplicationErrorReport$ParcelableCrashInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v9, resolved type: android.app.ApplicationErrorReport$ParcelableCrashInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v12, resolved type: android.app.ApplicationErrorReport$ParcelableCrashInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v82, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v16, resolved type: android.app.ApplicationErrorReport$ParcelableCrashInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v21, resolved type: android.app.ApplicationErrorReport$ParcelableCrashInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v25, resolved type: android.app.ApplicationErrorReport$ParcelableCrashInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v55, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v28, resolved type: android.app.ApplicationErrorReport$ParcelableCrashInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v37, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v31, resolved type: android.app.ApplicationErrorReport$ParcelableCrashInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v138, resolved type: android.content.res.Configuration} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v34, resolved type: android.app.ApplicationErrorReport$ParcelableCrashInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v142, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v38, resolved type: android.app.ApplicationErrorReport$ParcelableCrashInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v40, resolved type: android.app.ApplicationErrorReport$ParcelableCrashInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v43, resolved type: android.app.ApplicationErrorReport$ParcelableCrashInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v209, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v46, resolved type: android.app.ApplicationErrorReport$ParcelableCrashInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v49, resolved type: android.app.ApplicationErrorReport$ParcelableCrashInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v235, resolved type: android.content.pm.ApplicationInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v53, resolved type: android.app.ApplicationErrorReport$ParcelableCrashInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v56, resolved type: android.app.ApplicationErrorReport$ParcelableCrashInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v72, resolved type: android.os.StrictMode$ViolationInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v59, resolved type: android.app.ApplicationErrorReport$ParcelableCrashInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v62, resolved type: android.app.ApplicationErrorReport$ParcelableCrashInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v284, resolved type: android.content.res.Configuration} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v65, resolved type: android.app.ApplicationErrorReport$ParcelableCrashInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v69, resolved type: android.app.ApplicationErrorReport$ParcelableCrashInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v72, resolved type: android.app.ApplicationErrorReport$ParcelableCrashInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v152, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v75, resolved type: android.app.ApplicationErrorReport$ParcelableCrashInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v160, resolved type: android.graphics.Rect} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v79, resolved type: android.app.ApplicationErrorReport$ParcelableCrashInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v166, resolved type: android.os.WorkSource} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v82, resolved type: android.app.ApplicationErrorReport$ParcelableCrashInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v170, resolved type: android.os.WorkSource} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v85, resolved type: android.app.ApplicationErrorReport$ParcelableCrashInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v88, resolved type: android.app.ApplicationErrorReport$ParcelableCrashInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v177, resolved type: android.graphics.Rect} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v91, resolved type: android.app.ApplicationErrorReport$ParcelableCrashInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v422, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v94, resolved type: android.app.ApplicationErrorReport$ParcelableCrashInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v189, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v97, resolved type: android.app.ApplicationErrorReport$ParcelableCrashInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v98, resolved type: android.app.ApplicationErrorReport$ParcelableCrashInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v99, resolved type: android.app.ApplicationErrorReport$ParcelableCrashInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v100, resolved type: android.app.ApplicationErrorReport$ParcelableCrashInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v101, resolved type: android.app.ApplicationErrorReport$ParcelableCrashInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v102, resolved type: android.app.ApplicationErrorReport$ParcelableCrashInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v103, resolved type: android.app.ApplicationErrorReport$ParcelableCrashInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v104, resolved type: android.app.ApplicationErrorReport$ParcelableCrashInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v105, resolved type: android.app.ApplicationErrorReport$ParcelableCrashInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v106, resolved type: android.app.ApplicationErrorReport$ParcelableCrashInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v107, resolved type: android.app.ApplicationErrorReport$ParcelableCrashInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v108, resolved type: android.app.ApplicationErrorReport$ParcelableCrashInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v109, resolved type: android.app.ApplicationErrorReport$ParcelableCrashInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v110, resolved type: android.app.ApplicationErrorReport$ParcelableCrashInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v111, resolved type: android.app.ApplicationErrorReport$ParcelableCrashInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v112, resolved type: android.app.ApplicationErrorReport$ParcelableCrashInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v113, resolved type: android.app.ApplicationErrorReport$ParcelableCrashInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v114, resolved type: android.app.ApplicationErrorReport$ParcelableCrashInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v115, resolved type: android.app.ApplicationErrorReport$ParcelableCrashInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v116, resolved type: android.app.ApplicationErrorReport$ParcelableCrashInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v117, resolved type: android.app.ApplicationErrorReport$ParcelableCrashInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v118, resolved type: android.app.ApplicationErrorReport$ParcelableCrashInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v119, resolved type: android.app.ApplicationErrorReport$ParcelableCrashInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v120, resolved type: android.app.ApplicationErrorReport$ParcelableCrashInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v121, resolved type: android.app.ApplicationErrorReport$ParcelableCrashInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v122, resolved type: android.app.ApplicationErrorReport$ParcelableCrashInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v123, resolved type: android.app.ApplicationErrorReport$ParcelableCrashInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v124, resolved type: android.app.ApplicationErrorReport$ParcelableCrashInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v125, resolved type: android.app.ApplicationErrorReport$ParcelableCrashInfo} */
        /* JADX WARNING: type inference failed for: r2v9, types: [android.content.Intent] */
        /* JADX WARNING: type inference failed for: r16v6, types: [android.content.Intent] */
        /* JADX WARNING: type inference failed for: r1v18, types: [android.content.Intent] */
        /* JADX WARNING: type inference failed for: r16v11, types: [android.content.Intent] */
        /* JADX WARNING: type inference failed for: r16v14, types: [android.content.ComponentName] */
        /* JADX WARNING: type inference failed for: r1v35, types: [android.content.Intent] */
        /* JADX WARNING: type inference failed for: r16v18, types: [android.content.Intent] */
        /* JADX WARNING: type inference failed for: r1v43, types: [android.content.Intent] */
        /* JADX WARNING: type inference failed for: r16v23, types: [android.content.Intent] */
        /* JADX WARNING: type inference failed for: r16v27, types: [android.os.Bundle] */
        /* JADX WARNING: type inference failed for: r16v30, types: [android.os.Bundle] */
        /* JADX WARNING: type inference failed for: r16v33, types: [android.content.res.Configuration] */
        /* JADX WARNING: type inference failed for: r16v36, types: [android.content.ComponentName] */
        /* JADX WARNING: type inference failed for: r1v74, types: [android.os.IBinder] */
        /* JADX WARNING: type inference failed for: r16v39, types: [android.os.IBinder] */
        /* JADX WARNING: type inference failed for: r1v85, types: [android.content.Intent] */
        /* JADX WARNING: type inference failed for: r16v42, types: [android.content.Intent] */
        /* JADX WARNING: type inference failed for: r16v45, types: [android.content.ComponentName] */
        /* JADX WARNING: type inference failed for: r0v221, types: [android.content.Intent] */
        /* JADX WARNING: type inference failed for: r16v48, types: [android.content.Intent] */
        /* JADX WARNING: type inference failed for: r16v51, types: [android.content.pm.ApplicationInfo] */
        /* JADX WARNING: type inference failed for: r16v58, types: [android.os.StrictMode$ViolationInfo] */
        /* JADX WARNING: type inference failed for: r0v257, types: [android.net.Uri] */
        /* JADX WARNING: type inference failed for: r16v61, types: [android.net.Uri] */
        /* JADX WARNING: type inference failed for: r16v64, types: [android.content.res.Configuration] */
        /* JADX WARNING: type inference failed for: r0v289, types: [java.lang.CharSequence] */
        /* JADX WARNING: type inference failed for: r16v67, types: [java.lang.CharSequence] */
        /* JADX WARNING: type inference failed for: r0v359, types: [android.content.Intent] */
        /* JADX WARNING: type inference failed for: r16v71, types: [android.content.Intent] */
        /* JADX WARNING: type inference failed for: r16v74, types: [android.os.Bundle] */
        /* JADX WARNING: type inference failed for: r16v77, types: [android.graphics.Rect] */
        /* JADX WARNING: type inference failed for: r16v81, types: [android.os.WorkSource] */
        /* JADX WARNING: type inference failed for: r16v84, types: [android.os.WorkSource] */
        /* JADX WARNING: type inference failed for: r0v387, types: [android.os.ParcelFileDescriptor] */
        /* JADX WARNING: type inference failed for: r16v87, types: [android.os.ParcelFileDescriptor] */
        /* JADX WARNING: type inference failed for: r16v90, types: [android.graphics.Rect] */
        /* JADX WARNING: type inference failed for: r16v93, types: [android.content.ComponentName] */
        /* JADX WARNING: type inference failed for: r16v96, types: [android.os.Bundle] */
        /*  JADX ERROR: NullPointerException in pass: CodeShrinkVisitor
            java.lang.NullPointerException
            	at jadx.core.dex.instructions.args.InsnArg.wrapInstruction(InsnArg.java:118)
            	at jadx.core.dex.visitors.shrink.CodeShrinkVisitor.inline(CodeShrinkVisitor.java:146)
            	at jadx.core.dex.visitors.shrink.CodeShrinkVisitor.shrinkBlock(CodeShrinkVisitor.java:71)
            	at jadx.core.dex.visitors.shrink.CodeShrinkVisitor.shrinkMethod(CodeShrinkVisitor.java:43)
            	at jadx.core.dex.visitors.shrink.CodeShrinkVisitor.visit(CodeShrinkVisitor.java:35)
            */
        /* JADX WARNING: Multi-variable type inference failed */
        public boolean onTransact(int r28, android.os.Parcel r29, android.os.Parcel r30, int r31) throws android.os.RemoteException {
            /*
                r27 = this;
                r14 = r27
                r15 = r28
                r13 = r29
                r12 = r30
                java.lang.String r11 = "android.app.IActivityManager"
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r10 = 1
                if (r15 == r0) goto L_0x17d7
                r8 = 0
                r16 = 0
                switch(r15) {
                    case 1: goto L_0x17b6;
                    case 2: goto L_0x1793;
                    case 3: goto L_0x177c;
                    case 4: goto L_0x1761;
                    case 5: goto L_0x1746;
                    case 6: goto L_0x171f;
                    case 7: goto L_0x16a7;
                    case 8: goto L_0x1698;
                    case 9: goto L_0x1665;
                    case 10: goto L_0x160b;
                    case 11: goto L_0x15ef;
                    case 12: goto L_0x1554;
                    case 13: goto L_0x1529;
                    case 14: goto L_0x14f0;
                    case 15: goto L_0x14d9;
                    case 16: goto L_0x14c6;
                    case 17: goto L_0x14ab;
                    case 18: goto L_0x1474;
                    case 19: goto L_0x1458;
                    case 20: goto L_0x141d;
                    case 21: goto L_0x1404;
                    case 22: goto L_0x13e9;
                    case 23: goto L_0x13bd;
                    case 24: goto L_0x136f;
                    case 25: goto L_0x133c;
                    case 26: goto L_0x12e9;
                    case 27: goto L_0x128f;
                    case 28: goto L_0x1274;
                    case 29: goto L_0x125d;
                    case 30: goto L_0x1236;
                    case 31: goto L_0x1215;
                    case 32: goto L_0x1202;
                    case 33: goto L_0x11ee;
                    case 34: goto L_0x118f;
                    case 35: goto L_0x1168;
                    case 36: goto L_0x113d;
                    case 37: goto L_0x1125;
                    case 38: goto L_0x1102;
                    case 39: goto L_0x10d7;
                    case 40: goto L_0x10c8;
                    case 41: goto L_0x10b9;
                    case 42: goto L_0x109e;
                    case 43: goto L_0x1061;
                    case 44: goto L_0x102a;
                    case 45: goto L_0x0ff3;
                    case 46: goto L_0x0fd7;
                    case 47: goto L_0x0fbb;
                    case 48: goto L_0x0fac;
                    case 49: goto L_0x0f88;
                    case 50: goto L_0x0f70;
                    case 51: goto L_0x0f07;
                    case 52: goto L_0x0ef4;
                    case 53: goto L_0x0edd;
                    case 54: goto L_0x0ec2;
                    case 55: goto L_0x0ea7;
                    case 56: goto L_0x0e9c;
                    case 57: goto L_0x0e65;
                    case 58: goto L_0x0e50;
                    case 59: goto L_0x0e3d;
                    case 60: goto L_0x0e11;
                    case 61: goto L_0x0df1;
                    case 62: goto L_0x0dac;
                    case 63: goto L_0x0d85;
                    case 64: goto L_0x0d69;
                    case 65: goto L_0x0d52;
                    case 66: goto L_0x0d43;
                    case 67: goto L_0x0d1b;
                    case 68: goto L_0x0d08;
                    case 69: goto L_0x0ce8;
                    case 70: goto L_0x0cd1;
                    case 71: goto L_0x0cc2;
                    case 72: goto L_0x0c97;
                    case 73: goto L_0x0c5c;
                    case 74: goto L_0x0c49;
                    case 75: goto L_0x0c3e;
                    case 76: goto L_0x0c33;
                    case 77: goto L_0x0c18;
                    case 78: goto L_0x0c01;
                    case 79: goto L_0x0be2;
                    case 80: goto L_0x0bcb;
                    case 81: goto L_0x0b8d;
                    case 82: goto L_0x0b7e;
                    case 83: goto L_0x0b63;
                    case 84: goto L_0x0b54;
                    case 85: goto L_0x0b41;
                    case 86: goto L_0x0b2e;
                    case 87: goto L_0x0afa;
                    case 88: goto L_0x0ae7;
                    case 89: goto L_0x0ad8;
                    case 90: goto L_0x0ac9;
                    case 91: goto L_0x0abe;
                    case 92: goto L_0x0a97;
                    case 93: goto L_0x0a88;
                    case 94: goto L_0x0a62;
                    case 95: goto L_0x0a3b;
                    case 96: goto L_0x09db;
                    case 97: goto L_0x09c4;
                    case 98: goto L_0x09b1;
                    case 99: goto L_0x099e;
                    case 100: goto L_0x098b;
                    case 101: goto L_0x0978;
                    case 102: goto L_0x0965;
                    case 103: goto L_0x094e;
                    case 104: goto L_0x092f;
                    case 105: goto L_0x091c;
                    case 106: goto L_0x08f4;
                    case 107: goto L_0x08e9;
                    case 108: goto L_0x08c1;
                    case 109: goto L_0x08ae;
                    case 110: goto L_0x0897;
                    case 111: goto L_0x0880;
                    case 112: goto L_0x086d;
                    case 113: goto L_0x0855;
                    case 114: goto L_0x0842;
                    case 115: goto L_0x0833;
                    case 116: goto L_0x081c;
                    case 117: goto L_0x0805;
                    case 118: goto L_0x07ec;
                    case 119: goto L_0x076e;
                    case 120: goto L_0x074b;
                    case 121: goto L_0x0735;
                    case 122: goto L_0x0723;
                    case 123: goto L_0x0715;
                    case 124: goto L_0x070b;
                    case 125: goto L_0x06fd;
                    case 126: goto L_0x06eb;
                    case 127: goto L_0x06d9;
                    case 128: goto L_0x06ba;
                    case 129: goto L_0x06a8;
                    case 130: goto L_0x0692;
                    case 131: goto L_0x067f;
                    case 132: goto L_0x0668;
                    case 133: goto L_0x065a;
                    case 134: goto L_0x063f;
                    case 135: goto L_0x05fc;
                    case 136: goto L_0x05ee;
                    case 137: goto L_0x05d7;
                    case 138: goto L_0x05cd;
                    case 139: goto L_0x05c3;
                    case 140: goto L_0x05b5;
                    case 141: goto L_0x059a;
                    case 142: goto L_0x0580;
                    case 143: goto L_0x0566;
                    case 144: goto L_0x0554;
                    case 145: goto L_0x0546;
                    case 146: goto L_0x0518;
                    case 147: goto L_0x0505;
                    case 148: goto L_0x04df;
                    case 149: goto L_0x04d1;
                    case 150: goto L_0x04bf;
                    case 151: goto L_0x04b5;
                    case 152: goto L_0x0497;
                    case 153: goto L_0x0485;
                    case 154: goto L_0x0473;
                    case 155: goto L_0x0461;
                    case 156: goto L_0x044f;
                    case 157: goto L_0x0429;
                    case 158: goto L_0x041b;
                    case 159: goto L_0x03fa;
                    case 160: goto L_0x03ec;
                    case 161: goto L_0x03da;
                    case 162: goto L_0x03ac;
                    case 163: goto L_0x037e;
                    case 164: goto L_0x0368;
                    case 165: goto L_0x035a;
                    case 166: goto L_0x034c;
                    case 167: goto L_0x032a;
                    case 168: goto L_0x0314;
                    case 169: goto L_0x0301;
                    case 170: goto L_0x02db;
                    case 171: goto L_0x02c5;
                    case 172: goto L_0x02a3;
                    case 173: goto L_0x0291;
                    case 174: goto L_0x022b;
                    case 175: goto L_0x021d;
                    case 176: goto L_0x020b;
                    case 177: goto L_0x01fd;
                    case 178: goto L_0x01db;
                    case 179: goto L_0x01cd;
                    case 180: goto L_0x019e;
                    case 181: goto L_0x0194;
                    case 182: goto L_0x0135;
                    case 183: goto L_0x0123;
                    case 184: goto L_0x0115;
                    case 185: goto L_0x0102;
                    case 186: goto L_0x00f0;
                    case 187: goto L_0x00e2;
                    case 188: goto L_0x00be;
                    case 189: goto L_0x00ac;
                    case 190: goto L_0x009e;
                    case 191: goto L_0x0090;
                    case 192: goto L_0x0082;
                    case 193: goto L_0x0068;
                    case 194: goto L_0x0056;
                    case 195: goto L_0x004c;
                    case 196: goto L_0x0035;
                    case 197: goto L_0x001b;
                    default: goto L_0x0016;
                }
            L_0x0016:
                boolean r0 = super.onTransact(r28, r29, r30, r31)
                return r0
            L_0x001b:
                r13.enforceInterface(r11)
                int r0 = r29.readInt()
                android.os.IBinder r1 = r29.readStrongBinder()
                android.os.IProgressListener r1 = android.os.IProgressListener.Stub.asInterface(r1)
                boolean r2 = r14.startUserInForegroundWithListener(r0, r1)
                r30.writeNoException()
                r12.writeInt(r2)
                return r10
            L_0x0035:
                r13.enforceInterface(r11)
                android.os.ParcelFileDescriptor r0 = r27.getLifeMonitor()
                r30.writeNoException()
                if (r0 == 0) goto L_0x0048
                r12.writeInt(r10)
                r0.writeToParcel(r12, r10)
                goto L_0x004b
            L_0x0048:
                r12.writeInt(r8)
            L_0x004b:
                return r10
            L_0x004c:
                r13.enforceInterface(r11)
                r27.stopDelegateShellPermissionIdentity()
                r30.writeNoException()
                return r10
            L_0x0056:
                r13.enforceInterface(r11)
                int r0 = r29.readInt()
                java.lang.String[] r1 = r29.createStringArray()
                r14.startDelegateShellPermissionIdentity(r0, r1)
                r30.writeNoException()
                return r10
            L_0x0068:
                r13.enforceInterface(r11)
                int r0 = r29.readInt()
                android.os.IBinder r1 = r29.readStrongBinder()
                android.os.IProgressListener r1 = android.os.IProgressListener.Stub.asInterface(r1)
                boolean r2 = r14.startUserInBackgroundWithListener(r0, r1)
                r30.writeNoException()
                r12.writeInt(r2)
                return r10
            L_0x0082:
                r13.enforceInterface(r11)
                int r0 = r29.readInt()
                r14.backgroundWhitelistUid(r0)
                r30.writeNoException()
                return r10
            L_0x0090:
                r13.enforceInterface(r11)
                long r0 = r29.readLong()
                r14.waitForNetworkStateUpdate(r0)
                r30.writeNoException()
                return r10
            L_0x009e:
                r13.enforceInterface(r11)
                int r0 = r29.readInt()
                r14.setPersistentVrThread(r0)
                r30.writeNoException()
                return r10
            L_0x00ac:
                r13.enforceInterface(r11)
                java.util.ArrayList r0 = r29.createStringArrayList()
                int r1 = r29.readInt()
                r14.scheduleApplicationInfoChanged(r0, r1)
                r30.writeNoException()
                return r10
            L_0x00be:
                r13.enforceInterface(r11)
                int r0 = r29.readInt()
                int r1 = r29.readInt()
                if (r1 == 0) goto L_0x00cd
                r1 = r10
                goto L_0x00ce
            L_0x00cd:
                r1 = r8
            L_0x00ce:
                android.app.ActivityManager$TaskSnapshot r2 = r14.getTaskSnapshot(r0, r1)
                r30.writeNoException()
                if (r2 == 0) goto L_0x00de
                r12.writeInt(r10)
                r2.writeToParcel(r12, r10)
                goto L_0x00e1
            L_0x00de:
                r12.writeInt(r8)
            L_0x00e1:
                return r10
            L_0x00e2:
                r13.enforceInterface(r11)
                int r0 = r29.readInt()
                r14.cancelTaskWindowTransition(r0)
                r30.writeNoException()
                return r10
            L_0x00f0:
                r13.enforceInterface(r11)
                int r0 = r29.readInt()
                int r1 = r14.restartUserInBackground(r0)
                r30.writeNoException()
                r12.writeInt(r1)
                return r10
            L_0x0102:
                r13.enforceInterface(r11)
                int r0 = r29.readInt()
                if (r0 == 0) goto L_0x010d
                r8 = r10
            L_0x010d:
                r0 = r8
                r14.setHasTopUi(r0)
                r30.writeNoException()
                return r10
            L_0x0115:
                r13.enforceInterface(r11)
                int r0 = r29.readInt()
                r14.setRenderThread(r0)
                r30.writeNoException()
                return r10
            L_0x0123:
                r13.enforceInterface(r11)
                java.lang.String r0 = r29.readString()
                boolean r1 = r14.isBackgroundRestricted(r0)
                r30.writeNoException()
                r12.writeInt(r1)
                return r10
            L_0x0135:
                r13.enforceInterface(r11)
                android.os.IBinder r0 = r29.readStrongBinder()
                android.content.IIntentSender r9 = android.content.IIntentSender.Stub.asInterface(r0)
                android.os.IBinder r17 = r29.readStrongBinder()
                int r18 = r29.readInt()
                int r0 = r29.readInt()
                if (r0 == 0) goto L_0x0158
                android.os.Parcelable$Creator<android.content.Intent> r0 = android.content.Intent.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.content.Intent r0 = (android.content.Intent) r0
                r4 = r0
                goto L_0x015a
            L_0x0158:
                r4 = r16
            L_0x015a:
                java.lang.String r19 = r29.readString()
                android.os.IBinder r0 = r29.readStrongBinder()
                android.content.IIntentReceiver r20 = android.content.IIntentReceiver.Stub.asInterface(r0)
                java.lang.String r21 = r29.readString()
                int r0 = r29.readInt()
                if (r0 == 0) goto L_0x017a
                android.os.Parcelable$Creator<android.os.Bundle> r0 = android.os.Bundle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.os.Bundle r0 = (android.os.Bundle) r0
                r8 = r0
                goto L_0x017c
            L_0x017a:
                r8 = r16
            L_0x017c:
                r0 = r27
                r1 = r9
                r2 = r17
                r3 = r18
                r5 = r19
                r6 = r20
                r7 = r21
                int r0 = r0.sendIntentSender(r1, r2, r3, r4, r5, r6, r7, r8)
                r30.writeNoException()
                r12.writeInt(r0)
                return r10
            L_0x0194:
                r13.enforceInterface(r11)
                r27.sendIdleJobTrigger()
                r30.writeNoException()
                return r10
            L_0x019e:
                r13.enforceInterface(r11)
                int r0 = r29.readInt()
                if (r0 == 0) goto L_0x01b0
                android.os.Parcelable$Creator<android.content.Intent> r0 = android.content.Intent.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.content.Intent r0 = (android.content.Intent) r0
                goto L_0x01b2
            L_0x01b0:
                r0 = r16
            L_0x01b2:
                int r1 = r29.readInt()
                if (r1 == 0) goto L_0x01c3
                android.os.Parcelable$Creator<android.os.Bundle> r1 = android.os.Bundle.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r13)
                r16 = r1
                android.os.Bundle r16 = (android.os.Bundle) r16
                goto L_0x01c4
            L_0x01c3:
            L_0x01c4:
                r1 = r16
                r14.startConfirmDeviceCredentialIntent(r0, r1)
                r30.writeNoException()
                return r10
            L_0x01cd:
                r13.enforceInterface(r11)
                int r0 = r29.readInt()
                r14.notifyLockedProfile(r0)
                r30.writeNoException()
                return r10
            L_0x01db:
                r13.enforceInterface(r11)
                int r0 = r29.readInt()
                if (r0 == 0) goto L_0x01ef
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                r16 = r0
                android.content.ComponentName r16 = (android.content.ComponentName) r16
                goto L_0x01f0
            L_0x01ef:
            L_0x01f0:
                r0 = r16
                boolean r1 = r14.isVrModePackageEnabled(r0)
                r30.writeNoException()
                r12.writeInt(r1)
                return r10
            L_0x01fd:
                r13.enforceInterface(r11)
                int r0 = r27.getMemoryTrimLevel()
                r30.writeNoException()
                r12.writeInt(r0)
                return r10
            L_0x020b:
                r13.enforceInterface(r11)
                java.lang.String r0 = r29.readString()
                int r1 = r29.readInt()
                r14.makePackageIdle(r0, r1)
                r30.writeNoException()
                return r10
            L_0x021d:
                r13.enforceInterface(r11)
                int r0 = r29.readInt()
                r14.removeStack(r0)
                r30.writeNoException()
                return r10
            L_0x022b:
                r13.enforceInterface(r11)
                int r0 = r29.readInt()
                if (r0 == 0) goto L_0x023e
                android.os.Parcelable$Creator<android.graphics.Rect> r0 = android.graphics.Rect.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.graphics.Rect r0 = (android.graphics.Rect) r0
                r1 = r0
                goto L_0x0240
            L_0x023e:
                r1 = r16
            L_0x0240:
                int r0 = r29.readInt()
                if (r0 == 0) goto L_0x0250
                android.os.Parcelable$Creator<android.graphics.Rect> r0 = android.graphics.Rect.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.graphics.Rect r0 = (android.graphics.Rect) r0
                r2 = r0
                goto L_0x0252
            L_0x0250:
                r2 = r16
            L_0x0252:
                int r0 = r29.readInt()
                if (r0 == 0) goto L_0x0262
                android.os.Parcelable$Creator<android.graphics.Rect> r0 = android.graphics.Rect.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.graphics.Rect r0 = (android.graphics.Rect) r0
                r3 = r0
                goto L_0x0264
            L_0x0262:
                r3 = r16
            L_0x0264:
                int r0 = r29.readInt()
                if (r0 == 0) goto L_0x0274
                android.os.Parcelable$Creator<android.graphics.Rect> r0 = android.graphics.Rect.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.graphics.Rect r0 = (android.graphics.Rect) r0
                r4 = r0
                goto L_0x0276
            L_0x0274:
                r4 = r16
            L_0x0276:
                int r0 = r29.readInt()
                if (r0 == 0) goto L_0x0286
                android.os.Parcelable$Creator<android.graphics.Rect> r0 = android.graphics.Rect.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.graphics.Rect r0 = (android.graphics.Rect) r0
                r5 = r0
                goto L_0x0288
            L_0x0286:
                r5 = r16
            L_0x0288:
                r0 = r27
                r0.resizeDockedStack(r1, r2, r3, r4, r5)
                r30.writeNoException()
                return r10
            L_0x0291:
                r13.enforceInterface(r11)
                java.lang.String r0 = r29.readString()
                int r1 = r29.readInt()
                r14.killPackageDependents(r0, r1)
                r30.writeNoException()
                return r10
            L_0x02a3:
                r13.enforceInterface(r11)
                int r0 = r29.readInt()
                byte[] r1 = r29.createByteArray()
                byte[] r2 = r29.createByteArray()
                android.os.IBinder r3 = r29.readStrongBinder()
                android.os.IProgressListener r3 = android.os.IProgressListener.Stub.asInterface(r3)
                boolean r4 = r14.unlockUser(r0, r1, r2, r3)
                r30.writeNoException()
                r12.writeInt(r4)
                return r10
            L_0x02c5:
                r13.enforceInterface(r11)
                int r0 = r29.readInt()
                java.lang.String r1 = r29.readString()
                boolean r2 = r14.isAppStartModeDisabled(r0, r1)
                r30.writeNoException()
                r12.writeInt(r2)
                return r10
            L_0x02db:
                r13.enforceInterface(r11)
                int r0 = r29.readInt()
                int r1 = r29.readInt()
                if (r1 == 0) goto L_0x02f3
                android.os.Parcelable$Creator<android.graphics.Rect> r1 = android.graphics.Rect.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r13)
                r16 = r1
                android.graphics.Rect r16 = (android.graphics.Rect) r16
                goto L_0x02f4
            L_0x02f3:
            L_0x02f4:
                r1 = r16
                boolean r2 = r14.moveTopActivityToPinnedStack(r0, r1)
                r30.writeNoException()
                r12.writeInt(r2)
                return r10
            L_0x0301:
                r13.enforceInterface(r11)
                int r0 = r29.readInt()
                if (r0 == 0) goto L_0x030c
                r8 = r10
            L_0x030c:
                r0 = r8
                r14.suppressResizeConfigChanges(r0)
                r30.writeNoException()
                return r10
            L_0x0314:
                r13.enforceInterface(r11)
                int r0 = r29.readInt()
                int r1 = r29.readInt()
                int r2 = r29.readInt()
                r14.positionTaskInStack(r0, r1, r2)
                r30.writeNoException()
                return r10
            L_0x032a:
                r13.enforceInterface(r11)
                int r0 = r29.readInt()
                if (r0 == 0) goto L_0x033e
                android.os.Parcelable$Creator<android.os.ParcelFileDescriptor> r0 = android.os.ParcelFileDescriptor.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                r16 = r0
                android.os.ParcelFileDescriptor r16 = (android.os.ParcelFileDescriptor) r16
                goto L_0x033f
            L_0x033e:
            L_0x033f:
                r0 = r16
                boolean r1 = r14.stopBinderTrackingAndDump(r0)
                r30.writeNoException()
                r12.writeInt(r1)
                return r10
            L_0x034c:
                r13.enforceInterface(r11)
                boolean r0 = r27.startBinderTracking()
                r30.writeNoException()
                r12.writeInt(r0)
                return r10
            L_0x035a:
                r13.enforceInterface(r11)
                java.lang.String r0 = r29.readString()
                r14.updateDeviceOwner(r0)
                r30.writeNoException()
                return r10
            L_0x0368:
                r13.enforceInterface(r11)
                java.lang.String r0 = r29.readString()
                java.lang.String r1 = r29.readString()
                int r2 = r14.getPackageProcessState(r0, r1)
                r30.writeNoException()
                r12.writeInt(r2)
                return r10
            L_0x037e:
                r13.enforceInterface(r11)
                android.os.IBinder r0 = r29.readStrongBinder()
                android.content.IIntentSender r0 = android.content.IIntentSender.Stub.asInterface(r0)
                int r1 = r29.readInt()
                if (r1 == 0) goto L_0x039a
                android.os.Parcelable$Creator<android.os.WorkSource> r1 = android.os.WorkSource.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r13)
                r16 = r1
                android.os.WorkSource r16 = (android.os.WorkSource) r16
                goto L_0x039b
            L_0x039a:
            L_0x039b:
                r1 = r16
                int r2 = r29.readInt()
                java.lang.String r3 = r29.readString()
                r14.noteAlarmFinish(r0, r1, r2, r3)
                r30.writeNoException()
                return r10
            L_0x03ac:
                r13.enforceInterface(r11)
                android.os.IBinder r0 = r29.readStrongBinder()
                android.content.IIntentSender r0 = android.content.IIntentSender.Stub.asInterface(r0)
                int r1 = r29.readInt()
                if (r1 == 0) goto L_0x03c8
                android.os.Parcelable$Creator<android.os.WorkSource> r1 = android.os.WorkSource.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r13)
                r16 = r1
                android.os.WorkSource r16 = (android.os.WorkSource) r16
                goto L_0x03c9
            L_0x03c8:
            L_0x03c9:
                r1 = r16
                int r2 = r29.readInt()
                java.lang.String r3 = r29.readString()
                r14.noteAlarmStart(r0, r1, r2, r3)
                r30.writeNoException()
                return r10
            L_0x03da:
                r13.enforceInterface(r11)
                int r0 = r29.readInt()
                java.lang.String[] r1 = r29.createStringArray()
                r14.updateLockTaskPackages(r0, r1)
                r30.writeNoException()
                return r10
            L_0x03ec:
                r13.enforceInterface(r11)
                java.lang.String r0 = r29.readString()
                r14.dumpHeapFinished(r0)
                r30.writeNoException()
                return r10
            L_0x03fa:
                r13.enforceInterface(r11)
                java.lang.String r6 = r29.readString()
                int r7 = r29.readInt()
                long r8 = r29.readLong()
                java.lang.String r16 = r29.readString()
                r0 = r27
                r1 = r6
                r2 = r7
                r3 = r8
                r5 = r16
                r0.setDumpHeapDebugLimit(r1, r2, r3, r5)
                r30.writeNoException()
                return r10
            L_0x041b:
                r13.enforceInterface(r11)
                int r0 = r27.getLockTaskModeState()
                r30.writeNoException()
                r12.writeInt(r0)
                return r10
            L_0x0429:
                r13.enforceInterface(r11)
                int r0 = r29.readInt()
                int r1 = r29.readInt()
                if (r1 == 0) goto L_0x0441
                android.os.Parcelable$Creator<android.graphics.Rect> r1 = android.graphics.Rect.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r13)
                r16 = r1
                android.graphics.Rect r16 = (android.graphics.Rect) r16
                goto L_0x0442
            L_0x0441:
            L_0x0442:
                r1 = r16
                int r2 = r29.readInt()
                r14.resizeTask(r0, r1, r2)
                r30.writeNoException()
                return r10
            L_0x044f:
                r13.enforceInterface(r11)
                int r0 = r29.readInt()
                int r1 = r29.readInt()
                r14.setTaskResizeable(r0, r1)
                r30.writeNoException()
                return r10
            L_0x0461:
                r13.enforceInterface(r11)
                int r0 = r29.readInt()
                byte[] r1 = r29.createByteArray()
                r14.notifyCleartextNetwork(r0, r1)
                r30.writeNoException()
                return r10
            L_0x0473:
                r13.enforceInterface(r11)
                android.os.IBinder r0 = r29.readStrongBinder()
                android.app.ITaskStackListener r0 = android.app.ITaskStackListener.Stub.asInterface(r0)
                r14.unregisterTaskStackListener(r0)
                r30.writeNoException()
                return r10
            L_0x0485:
                r13.enforceInterface(r11)
                android.os.IBinder r0 = r29.readStrongBinder()
                android.app.ITaskStackListener r0 = android.app.ITaskStackListener.Stub.asInterface(r0)
                r14.registerTaskStackListener(r0)
                r30.writeNoException()
                return r10
            L_0x0497:
                r13.enforceInterface(r11)
                java.lang.String r0 = r29.readString()
                int r1 = r29.readInt()
                int r2 = r29.readInt()
                android.os.IBinder r3 = r29.readStrongBinder()
                int r4 = r14.checkPermissionWithToken(r0, r1, r2, r3)
                r30.writeNoException()
                r12.writeInt(r4)
                return r10
            L_0x04b5:
                r13.enforceInterface(r11)
                r27.bootAnimationComplete()
                r30.writeNoException()
                return r10
            L_0x04bf:
                r13.enforceInterface(r11)
                android.os.IBinder r0 = r29.readStrongBinder()
                boolean r1 = r14.isTopOfTask(r0)
                r30.writeNoException()
                r12.writeInt(r1)
                return r10
            L_0x04d1:
                r13.enforceInterface(r11)
                int r0 = r29.readInt()
                r14.startSystemLockTaskMode(r0)
                r30.writeNoException()
                return r10
            L_0x04df:
                r13.enforceInterface(r11)
                int r0 = r29.readInt()
                int r1 = r29.readInt()
                if (r1 == 0) goto L_0x04f7
                android.os.Parcelable$Creator<android.os.Bundle> r1 = android.os.Bundle.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r13)
                r16 = r1
                android.os.Bundle r16 = (android.os.Bundle) r16
                goto L_0x04f8
            L_0x04f7:
            L_0x04f8:
                r1 = r16
                int r2 = r14.startActivityFromRecents(r0, r1)
                r30.writeNoException()
                r12.writeInt(r2)
                return r10
            L_0x0505:
                r13.enforceInterface(r11)
                int r0 = r29.readInt()
                if (r0 == 0) goto L_0x0510
                r8 = r10
            L_0x0510:
                r0 = r8
                r14.cancelRecentsAnimation(r0)
                r30.writeNoException()
                return r10
            L_0x0518:
                r13.enforceInterface(r11)
                int r0 = r29.readInt()
                if (r0 == 0) goto L_0x052c
                android.os.Parcelable$Creator<android.content.Intent> r0 = android.content.Intent.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                r16 = r0
                android.content.Intent r16 = (android.content.Intent) r16
                goto L_0x052d
            L_0x052c:
            L_0x052d:
                r0 = r16
                android.os.IBinder r1 = r29.readStrongBinder()
                android.app.IAssistDataReceiver r1 = android.app.IAssistDataReceiver.Stub.asInterface(r1)
                android.os.IBinder r2 = r29.readStrongBinder()
                android.view.IRecentsAnimationRunner r2 = android.view.IRecentsAnimationRunner.Stub.asInterface(r2)
                r14.startRecentsActivity(r0, r1, r2)
                r30.writeNoException()
                return r10
            L_0x0546:
                r13.enforceInterface(r11)
                boolean r0 = r27.isInLockTaskMode()
                r30.writeNoException()
                r12.writeInt(r0)
                return r10
            L_0x0554:
                r13.enforceInterface(r11)
                int r0 = r29.readInt()
                boolean r1 = r14.startUserInBackground(r0)
                r30.writeNoException()
                r12.writeInt(r1)
                return r10
            L_0x0566:
                r13.enforceInterface(r11)
                android.os.IBinder r0 = r29.readStrongBinder()
                android.content.IIntentSender r0 = android.content.IIntentSender.Stub.asInterface(r0)
                java.lang.String r1 = r29.readString()
                java.lang.String r2 = r14.getTagForIntentSender(r0, r1)
                r30.writeNoException()
                r12.writeString(r2)
                return r10
            L_0x0580:
                r13.enforceInterface(r11)
                java.lang.String r0 = r29.readString()
                int r1 = r29.readInt()
                int r2 = r29.readInt()
                boolean r3 = r14.setProcessMemoryTrimLevel(r0, r1, r2)
                r30.writeNoException()
                r12.writeInt(r3)
                return r10
            L_0x059a:
                r13.enforceInterface(r11)
                int r0 = r29.readInt()
                android.graphics.Rect r1 = r14.getTaskBounds(r0)
                r30.writeNoException()
                if (r1 == 0) goto L_0x05b1
                r12.writeInt(r10)
                r1.writeToParcel(r12, r10)
                goto L_0x05b4
            L_0x05b1:
                r12.writeInt(r8)
            L_0x05b4:
                return r10
            L_0x05b5:
                r13.enforceInterface(r11)
                android.os.IBinder r0 = r29.readStrongBinder()
                r14.appNotRespondingViaProvider(r0)
                r30.writeNoException()
                return r10
            L_0x05c3:
                r13.enforceInterface(r11)
                r27.performIdleMaintenance()
                r30.writeNoException()
                return r10
            L_0x05cd:
                r13.enforceInterface(r11)
                r27.restart()
                r30.writeNoException()
                return r10
            L_0x05d7:
                r13.enforceInterface(r11)
                android.app.ActivityManager$StackInfo r0 = r27.getFocusedStackInfo()
                r30.writeNoException()
                if (r0 == 0) goto L_0x05ea
                r12.writeInt(r10)
                r0.writeToParcel(r12, r10)
                goto L_0x05ed
            L_0x05ea:
                r12.writeInt(r8)
            L_0x05ed:
                return r10
            L_0x05ee:
                r13.enforceInterface(r11)
                int r0 = r29.readInt()
                r14.setFocusedStack(r0)
                r30.writeNoException()
                return r10
            L_0x05fc:
                r13.enforceInterface(r11)
                int r7 = r29.readInt()
                int r0 = r29.readInt()
                if (r0 == 0) goto L_0x0613
                android.os.Parcelable$Creator<android.graphics.Rect> r0 = android.graphics.Rect.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.graphics.Rect r0 = (android.graphics.Rect) r0
                r2 = r0
                goto L_0x0615
            L_0x0613:
                r2 = r16
            L_0x0615:
                int r0 = r29.readInt()
                if (r0 == 0) goto L_0x061d
                r3 = r10
                goto L_0x061e
            L_0x061d:
                r3 = r8
            L_0x061e:
                int r0 = r29.readInt()
                if (r0 == 0) goto L_0x0626
                r4 = r10
                goto L_0x0627
            L_0x0626:
                r4 = r8
            L_0x0627:
                int r0 = r29.readInt()
                if (r0 == 0) goto L_0x062f
                r5 = r10
                goto L_0x0630
            L_0x062f:
                r5 = r8
            L_0x0630:
                int r8 = r29.readInt()
                r0 = r27
                r1 = r7
                r6 = r8
                r0.resizeStack(r1, r2, r3, r4, r5, r6)
                r30.writeNoException()
                return r10
            L_0x063f:
                r13.enforceInterface(r11)
                int r0 = r29.readInt()
                int r1 = r29.readInt()
                int r2 = r29.readInt()
                if (r2 == 0) goto L_0x0652
                r8 = r10
            L_0x0652:
                r2 = r8
                r14.moveTaskToStack(r0, r1, r2)
                r30.writeNoException()
                return r10
            L_0x065a:
                r13.enforceInterface(r11)
                java.util.List r0 = r27.getAllStackInfos()
                r30.writeNoException()
                r12.writeTypedList(r0)
                return r10
            L_0x0668:
                r13.enforceInterface(r11)
                android.os.IBinder r0 = r29.readStrongBinder()
                int r1 = r29.readInt()
                if (r1 == 0) goto L_0x0677
                r8 = r10
            L_0x0677:
                r1 = r8
                r14.hang(r0, r1)
                r30.writeNoException()
                return r10
            L_0x067f:
                r13.enforceInterface(r11)
                int r0 = r29.readInt()
                if (r0 == 0) goto L_0x068a
                r8 = r10
            L_0x068a:
                r0 = r8
                r14.setUserIsMonkey(r0)
                r30.writeNoException()
                return r10
            L_0x0692:
                r13.enforceInterface(r11)
                int r0 = r29.readInt()
                int r1 = r29.readInt()
                java.lang.String r2 = r29.readString()
                r14.killUid(r0, r1, r2)
                r30.writeNoException()
                return r10
            L_0x06a8:
                r13.enforceInterface(r11)
                android.os.IBinder r0 = r29.readStrongBinder()
                java.lang.String r1 = r14.getLaunchedFromPackage(r0)
                r30.writeNoException()
                r12.writeString(r1)
                return r10
            L_0x06ba:
                r13.enforceInterface(r11)
                android.os.IBinder r0 = r29.readStrongBinder()
                android.content.IIntentSender r0 = android.content.IIntentSender.Stub.asInterface(r0)
                android.content.Intent r1 = r14.getIntentForIntentSender(r0)
                r30.writeNoException()
                if (r1 == 0) goto L_0x06d5
                r12.writeInt(r10)
                r1.writeToParcel(r12, r10)
                goto L_0x06d8
            L_0x06d5:
                r12.writeInt(r8)
            L_0x06d8:
                return r10
            L_0x06d9:
                r13.enforceInterface(r11)
                java.lang.String r0 = r29.readString()
                java.lang.String r1 = r29.readString()
                r14.requestWifiBugReport(r0, r1)
                r30.writeNoException()
                return r10
            L_0x06eb:
                r13.enforceInterface(r11)
                java.lang.String r0 = r29.readString()
                java.lang.String r1 = r29.readString()
                r14.requestTelephonyBugReport(r0, r1)
                r30.writeNoException()
                return r10
            L_0x06fd:
                r13.enforceInterface(r11)
                int r0 = r29.readInt()
                r14.requestBugReport(r0)
                r30.writeNoException()
                return r10
            L_0x070b:
                r13.enforceInterface(r11)
                r27.requestSystemServerHeapDump()
                r30.writeNoException()
                return r10
            L_0x0715:
                r13.enforceInterface(r11)
                int[] r0 = r27.getRunningUserIds()
                r30.writeNoException()
                r12.writeIntArray(r0)
                return r10
            L_0x0723:
                r13.enforceInterface(r11)
                android.os.IBinder r0 = r29.readStrongBinder()
                android.app.IUserSwitchObserver r0 = android.app.IUserSwitchObserver.Stub.asInterface(r0)
                r14.unregisterUserSwitchObserver(r0)
                r30.writeNoException()
                return r10
            L_0x0735:
                r13.enforceInterface(r11)
                android.os.IBinder r0 = r29.readStrongBinder()
                android.app.IUserSwitchObserver r0 = android.app.IUserSwitchObserver.Stub.asInterface(r0)
                java.lang.String r1 = r29.readString()
                r14.registerUserSwitchObserver(r0, r1)
                r30.writeNoException()
                return r10
            L_0x074b:
                r13.enforceInterface(r11)
                int r0 = r29.readInt()
                int r1 = r29.readInt()
                if (r1 == 0) goto L_0x075a
                r8 = r10
            L_0x075a:
                r1 = r8
                android.os.IBinder r2 = r29.readStrongBinder()
                android.app.IStopUserCallback r2 = android.app.IStopUserCallback.Stub.asInterface(r2)
                int r3 = r14.stopUser(r0, r1, r2)
                r30.writeNoException()
                r12.writeInt(r3)
                return r10
            L_0x076e:
                r13.enforceInterface(r11)
                android.os.IBinder r0 = r29.readStrongBinder()
                android.app.IApplicationThread r17 = android.app.IApplicationThread.Stub.asInterface(r0)
                java.lang.String r18 = r29.readString()
                int r0 = r29.readInt()
                if (r0 == 0) goto L_0x078d
                android.os.Parcelable$Creator<android.content.Intent> r0 = android.content.Intent.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.content.Intent r0 = (android.content.Intent) r0
                r3 = r0
                goto L_0x078f
            L_0x078d:
                r3 = r16
            L_0x078f:
                java.lang.String r19 = r29.readString()
                android.os.IBinder r20 = r29.readStrongBinder()
                java.lang.String r21 = r29.readString()
                int r22 = r29.readInt()
                int r23 = r29.readInt()
                int r0 = r29.readInt()
                if (r0 == 0) goto L_0x07b3
                android.os.Parcelable$Creator<android.app.ProfilerInfo> r0 = android.app.ProfilerInfo.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.app.ProfilerInfo r0 = (android.app.ProfilerInfo) r0
                r9 = r0
                goto L_0x07b5
            L_0x07b3:
                r9 = r16
            L_0x07b5:
                int r0 = r29.readInt()
                if (r0 == 0) goto L_0x07c4
                android.os.Parcelable$Creator<android.os.Bundle> r0 = android.os.Bundle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.os.Bundle r0 = (android.os.Bundle) r0
                goto L_0x07c6
            L_0x07c4:
                r0 = r16
            L_0x07c6:
                r8 = r10
                r10 = r0
                int r16 = r29.readInt()
                r0 = r27
                r1 = r17
                r2 = r18
                r4 = r19
                r5 = r20
                r6 = r21
                r7 = r22
                r15 = r8
                r8 = r23
                r24 = r11
                r11 = r16
                int r0 = r0.startActivityAsUser(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11)
                r30.writeNoException()
                r12.writeInt(r0)
                return r15
            L_0x07ec:
                r15 = r10
                r24 = r11
                r13.enforceInterface(r11)
                android.os.IBinder r0 = r29.readStrongBinder()
                android.content.IIntentSender r0 = android.content.IIntentSender.Stub.asInterface(r0)
                boolean r1 = r14.isIntentSenderABroadcast(r0)
                r30.writeNoException()
                r12.writeInt(r1)
                return r15
            L_0x0805:
                r15 = r10
                r13.enforceInterface(r11)
                android.os.IBinder r0 = r29.readStrongBinder()
                android.content.IIntentSender r0 = android.content.IIntentSender.Stub.asInterface(r0)
                boolean r1 = r14.isIntentSenderAForegroundService(r0)
                r30.writeNoException()
                r12.writeInt(r1)
                return r15
            L_0x081c:
                r15 = r10
                r13.enforceInterface(r11)
                android.os.IBinder r0 = r29.readStrongBinder()
                android.content.IIntentSender r0 = android.content.IIntentSender.Stub.asInterface(r0)
                boolean r1 = r14.isIntentSenderAnActivity(r0)
                r30.writeNoException()
                r12.writeInt(r1)
                return r15
            L_0x0833:
                r15 = r10
                r13.enforceInterface(r11)
                android.os.IBinder r0 = r29.readStrongBinder()
                r14.unstableProviderDied(r0)
                r30.writeNoException()
                return r15
            L_0x0842:
                r15 = r10
                r13.enforceInterface(r11)
                android.os.IBinder r0 = r29.readStrongBinder()
                int r1 = r14.getLaunchedFromUid(r0)
                r30.writeNoException()
                r12.writeInt(r1)
                return r15
            L_0x0855:
                r15 = r10
                r13.enforceInterface(r11)
                android.content.pm.UserInfo r0 = r27.getCurrentUser()
                r30.writeNoException()
                if (r0 == 0) goto L_0x0869
                r12.writeInt(r15)
                r0.writeToParcel(r12, r15)
                goto L_0x086c
            L_0x0869:
                r12.writeInt(r8)
            L_0x086c:
                return r15
            L_0x086d:
                r15 = r10
                r13.enforceInterface(r11)
                java.lang.String r0 = r29.readString()
                boolean r1 = r14.killProcessesBelowForeground(r0)
                r30.writeNoException()
                r12.writeInt(r1)
                return r15
            L_0x0880:
                r15 = r10
                r13.enforceInterface(r11)
                android.app.ActivityManager$RunningAppProcessInfo r0 = new android.app.ActivityManager$RunningAppProcessInfo
                r0.<init>()
                r14.getMyMemoryState(r0)
                r30.writeNoException()
                r12.writeInt(r15)
                r0.writeToParcel(r12, r15)
                return r15
            L_0x0897:
                r15 = r10
                r13.enforceInterface(r11)
                java.lang.String r0 = r29.readString()
                android.os.IBinder r1 = r29.readStrongBinder()
                int r2 = r29.readInt()
                r14.removeContentProviderExternalAsUser(r0, r1, r2)
                r30.writeNoException()
                return r15
            L_0x08ae:
                r15 = r10
                r13.enforceInterface(r11)
                java.lang.String r0 = r29.readString()
                android.os.IBinder r1 = r29.readStrongBinder()
                r14.removeContentProviderExternal(r0, r1)
                r30.writeNoException()
                return r15
            L_0x08c1:
                r15 = r10
                r13.enforceInterface(r11)
                java.lang.String r0 = r29.readString()
                int r1 = r29.readInt()
                android.os.IBinder r2 = r29.readStrongBinder()
                java.lang.String r3 = r29.readString()
                android.app.ContentProviderHolder r4 = r14.getContentProviderExternal(r0, r1, r2, r3)
                r30.writeNoException()
                if (r4 == 0) goto L_0x08e5
                r12.writeInt(r15)
                r4.writeToParcel(r12, r15)
                goto L_0x08e8
            L_0x08e5:
                r12.writeInt(r8)
            L_0x08e8:
                return r15
            L_0x08e9:
                r15 = r10
                r13.enforceInterface(r11)
                r27.killAllBackgroundProcesses()
                r30.writeNoException()
                return r15
            L_0x08f4:
                r15 = r10
                r13.enforceInterface(r11)
                int r0 = r29.readInt()
                if (r0 == 0) goto L_0x0909
                android.os.Parcelable$Creator<java.lang.CharSequence> r0 = android.text.TextUtils.CHAR_SEQUENCE_CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                r16 = r0
                java.lang.CharSequence r16 = (java.lang.CharSequence) r16
                goto L_0x090a
            L_0x0909:
            L_0x090a:
                r0 = r16
                int r1 = r29.readInt()
                if (r1 == 0) goto L_0x0914
                r8 = r15
            L_0x0914:
                r1 = r8
                r14.showBootMessage(r0, r1)
                r30.writeNoException()
                return r15
            L_0x091c:
                r15 = r10
                r13.enforceInterface(r11)
                int[] r0 = r29.createIntArray()
                long[] r1 = r14.getProcessPss(r0)
                r30.writeNoException()
                r12.writeLongArray(r1)
                return r15
            L_0x092f:
                r15 = r10
                r13.enforceInterface(r11)
                int r0 = r29.readInt()
                if (r0 == 0) goto L_0x0944
                android.os.Parcelable$Creator<android.content.res.Configuration> r0 = android.content.res.Configuration.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                r16 = r0
                android.content.res.Configuration r16 = (android.content.res.Configuration) r16
                goto L_0x0945
            L_0x0944:
            L_0x0945:
                r0 = r16
                r14.updatePersistentConfiguration(r0)
                r30.writeNoException()
                return r15
            L_0x094e:
                r15 = r10
                r13.enforceInterface(r11)
                android.os.IBinder r0 = r29.readStrongBinder()
                android.content.IIntentSender r0 = android.content.IIntentSender.Stub.asInterface(r0)
                boolean r1 = r14.isIntentSenderTargetedToPackage(r0)
                r30.writeNoException()
                r12.writeInt(r1)
                return r15
            L_0x0965:
                r15 = r10
                r13.enforceInterface(r11)
                android.os.IBinder r0 = r29.readStrongBinder()
                android.app.IProcessObserver r0 = android.app.IProcessObserver.Stub.asInterface(r0)
                r14.unregisterProcessObserver(r0)
                r30.writeNoException()
                return r15
            L_0x0978:
                r15 = r10
                r13.enforceInterface(r11)
                android.os.IBinder r0 = r29.readStrongBinder()
                android.app.IProcessObserver r0 = android.app.IProcessObserver.Stub.asInterface(r0)
                r14.registerProcessObserver(r0)
                r30.writeNoException()
                return r15
            L_0x098b:
                r15 = r10
                r13.enforceInterface(r11)
                int r0 = r29.readInt()
                boolean r1 = r14.removeTask(r0)
                r30.writeNoException()
                r12.writeInt(r1)
                return r15
            L_0x099e:
                r15 = r10
                r13.enforceInterface(r11)
                int r0 = r29.readInt()
                boolean r1 = r14.switchUser(r0)
                r30.writeNoException()
                r12.writeInt(r1)
                return r15
            L_0x09b1:
                r15 = r10
                r13.enforceInterface(r11)
                java.lang.String r0 = r29.readString()
                int r1 = r29.readInt()
                r14.setPackageScreenCompatMode(r0, r1)
                r30.writeNoException()
                return r15
            L_0x09c4:
                r15 = r10
                r13.enforceInterface(r11)
                int r0 = r29.readInt()
                int r1 = r29.readInt()
                boolean r2 = r14.isUserRunning(r0, r1)
                r30.writeNoException()
                r12.writeInt(r2)
                return r15
            L_0x09db:
                r15 = r10
                r13.enforceInterface(r11)
                java.lang.String r9 = r29.readString()
                int r10 = r29.readInt()
                int r0 = r29.readInt()
                if (r0 == 0) goto L_0x09ef
                r3 = r15
                goto L_0x09f0
            L_0x09ef:
                r3 = r8
            L_0x09f0:
                int r0 = r29.readInt()
                if (r0 == 0) goto L_0x09f8
                r4 = r15
                goto L_0x09f9
            L_0x09f8:
                r4 = r8
            L_0x09f9:
                int r0 = r29.readInt()
                if (r0 == 0) goto L_0x0a01
                r5 = r15
                goto L_0x0a02
            L_0x0a01:
                r5 = r8
            L_0x0a02:
                java.lang.String r17 = r29.readString()
                int r0 = r29.readInt()
                if (r0 == 0) goto L_0x0a16
                android.os.Parcelable$Creator<android.os.ParcelFileDescriptor> r0 = android.os.ParcelFileDescriptor.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.os.ParcelFileDescriptor r0 = (android.os.ParcelFileDescriptor) r0
                r7 = r0
                goto L_0x0a18
            L_0x0a16:
                r7 = r16
            L_0x0a18:
                int r0 = r29.readInt()
                if (r0 == 0) goto L_0x0a28
                android.os.Parcelable$Creator<android.os.RemoteCallback> r0 = android.os.RemoteCallback.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.os.RemoteCallback r0 = (android.os.RemoteCallback) r0
                r8 = r0
                goto L_0x0a2a
            L_0x0a28:
                r8 = r16
            L_0x0a2a:
                r0 = r27
                r1 = r9
                r2 = r10
                r6 = r17
                boolean r0 = r0.dumpHeap(r1, r2, r3, r4, r5, r6, r7, r8)
                r30.writeNoException()
                r12.writeInt(r0)
                return r15
            L_0x0a3b:
                r15 = r10
                r13.enforceInterface(r11)
                int r0 = r29.readInt()
                if (r0 == 0) goto L_0x0a50
                android.os.Parcelable$Creator<android.net.Uri> r0 = android.net.Uri.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                r16 = r0
                android.net.Uri r16 = (android.net.Uri) r16
                goto L_0x0a51
            L_0x0a50:
            L_0x0a51:
                r0 = r16
                int r1 = r29.readInt()
                java.lang.String r2 = r14.getProviderMimeType(r0, r1)
                r30.writeNoException()
                r12.writeString(r2)
                return r15
            L_0x0a62:
                r15 = r10
                r13.enforceInterface(r11)
                int r6 = r29.readInt()
                int r7 = r29.readInt()
                java.lang.String r8 = r29.readString()
                int r9 = r29.readInt()
                java.lang.String r10 = r29.readString()
                r0 = r27
                r1 = r6
                r2 = r7
                r3 = r8
                r4 = r9
                r5 = r10
                r0.crashApplication(r1, r2, r3, r4, r5)
                r30.writeNoException()
                return r15
            L_0x0a88:
                r15 = r10
                r13.enforceInterface(r11)
                boolean r0 = r27.isTopActivityImmersive()
                r30.writeNoException()
                r12.writeInt(r0)
                return r15
            L_0x0a97:
                r15 = r10
                r13.enforceInterface(r11)
                android.os.IBinder r0 = r29.readStrongBinder()
                int r1 = r29.readInt()
                int r2 = r29.readInt()
                if (r2 == 0) goto L_0x0ab4
                android.os.Parcelable$Creator<android.os.StrictMode$ViolationInfo> r2 = android.os.StrictMode.ViolationInfo.CREATOR
                java.lang.Object r2 = r2.createFromParcel(r13)
                r16 = r2
                android.os.StrictMode$ViolationInfo r16 = (android.os.StrictMode.ViolationInfo) r16
                goto L_0x0ab5
            L_0x0ab4:
            L_0x0ab5:
                r2 = r16
                r14.handleApplicationStrictModeViolation(r0, r1, r2)
                r30.writeNoException()
                return r15
            L_0x0abe:
                r15 = r10
                r13.enforceInterface(r11)
                r27.finishHeavyWeightApp()
                r30.writeNoException()
                return r15
            L_0x0ac9:
                r15 = r10
                r13.enforceInterface(r11)
                java.util.List r0 = r27.getRunningExternalApplications()
                r30.writeNoException()
                r12.writeTypedList(r0)
                return r15
            L_0x0ad8:
                r15 = r10
                r13.enforceInterface(r11)
                boolean r0 = r27.isUserAMonkey()
                r30.writeNoException()
                r12.writeInt(r0)
                return r15
            L_0x0ae7:
                r15 = r10
                r13.enforceInterface(r11)
                java.lang.String r0 = r29.readString()
                int r1 = r29.readInt()
                r14.killBackgroundProcesses(r0, r1)
                r30.writeNoException()
                return r15
            L_0x0afa:
                r15 = r10
                r13.enforceInterface(r11)
                android.os.IBinder r0 = r29.readStrongBinder()
                java.lang.String r1 = r29.readString()
                int r2 = r29.readInt()
                if (r2 == 0) goto L_0x0b0e
                r8 = r15
            L_0x0b0e:
                r2 = r8
                int r3 = r29.readInt()
                if (r3 == 0) goto L_0x0b20
                android.os.Parcelable$Creator<android.app.ApplicationErrorReport$ParcelableCrashInfo> r3 = android.app.ApplicationErrorReport.ParcelableCrashInfo.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r13)
                r16 = r3
                android.app.ApplicationErrorReport$ParcelableCrashInfo r16 = (android.app.ApplicationErrorReport.ParcelableCrashInfo) r16
                goto L_0x0b21
            L_0x0b20:
            L_0x0b21:
                r3 = r16
                boolean r4 = r14.handleApplicationWtf(r0, r1, r2, r3)
                r30.writeNoException()
                r12.writeInt(r4)
                return r15
            L_0x0b2e:
                r15 = r10
                r13.enforceInterface(r11)
                java.lang.String r0 = r29.readString()
                int r1 = r29.readInt()
                r14.killApplicationProcess(r0, r1)
                r30.writeNoException()
                return r15
            L_0x0b41:
                r15 = r10
                r13.enforceInterface(r11)
                int[] r0 = r29.createIntArray()
                android.os.Debug$MemoryInfo[] r1 = r14.getProcessMemoryInfo(r0)
                r30.writeNoException()
                r12.writeTypedArray(r1, r15)
                return r15
            L_0x0b54:
                r15 = r10
                r13.enforceInterface(r11)
                java.lang.String r0 = r29.readString()
                r14.closeSystemDialogs(r0)
                r30.writeNoException()
                return r15
            L_0x0b63:
                r15 = r10
                r13.enforceInterface(r11)
                java.lang.String r0 = r29.readString()
                int r1 = r29.readInt()
                int r2 = r29.readInt()
                java.lang.String r3 = r29.readString()
                r14.killApplication(r0, r1, r2, r3)
                r30.writeNoException()
                return r15
            L_0x0b7e:
                r15 = r10
                r13.enforceInterface(r11)
                java.lang.String r0 = r29.readString()
                r14.addPackageDependency(r0)
                r30.writeNoException()
                return r15
            L_0x0b8d:
                r15 = r10
                r13.enforceInterface(r11)
                int r9 = r29.readInt()
                int r10 = r29.readInt()
                int r16 = r29.readInt()
                int r0 = r29.readInt()
                if (r0 == 0) goto L_0x0ba5
                r4 = r15
                goto L_0x0ba6
            L_0x0ba5:
                r4 = r8
            L_0x0ba6:
                int r0 = r29.readInt()
                if (r0 == 0) goto L_0x0bae
                r5 = r15
                goto L_0x0baf
            L_0x0bae:
                r5 = r8
            L_0x0baf:
                java.lang.String r8 = r29.readString()
                java.lang.String r17 = r29.readString()
                r0 = r27
                r1 = r9
                r2 = r10
                r3 = r16
                r6 = r8
                r7 = r17
                int r0 = r0.handleIncomingUser(r1, r2, r3, r4, r5, r6, r7)
                r30.writeNoException()
                r12.writeInt(r0)
                return r15
            L_0x0bcb:
                r15 = r10
                r13.enforceInterface(r11)
                android.os.IBinder r0 = r29.readStrongBinder()
                android.content.IIntentSender r0 = android.content.IIntentSender.Stub.asInterface(r0)
                int r1 = r14.getUidForIntentSender(r0)
                r30.writeNoException()
                r12.writeInt(r1)
                return r15
            L_0x0be2:
                r15 = r10
                r13.enforceInterface(r11)
                int r0 = r29.readInt()
                if (r0 == 0) goto L_0x0bf7
                android.os.Parcelable$Creator<android.content.pm.ApplicationInfo> r0 = android.content.pm.ApplicationInfo.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                r16 = r0
                android.content.pm.ApplicationInfo r16 = (android.content.pm.ApplicationInfo) r16
                goto L_0x0bf8
            L_0x0bf7:
            L_0x0bf8:
                r0 = r16
                r14.unbindBackupAgent(r0)
                r30.writeNoException()
                return r15
            L_0x0c01:
                r15 = r10
                r13.enforceInterface(r11)
                java.lang.String r0 = r29.readString()
                android.os.IBinder r1 = r29.readStrongBinder()
                int r2 = r29.readInt()
                r14.backupAgentCreated(r0, r1, r2)
                r30.writeNoException()
                return r15
            L_0x0c18:
                r15 = r10
                r13.enforceInterface(r11)
                java.lang.String r0 = r29.readString()
                int r1 = r29.readInt()
                int r2 = r29.readInt()
                boolean r3 = r14.bindBackupAgent(r0, r1, r2)
                r30.writeNoException()
                r12.writeInt(r3)
                return r15
            L_0x0c33:
                r15 = r10
                r13.enforceInterface(r11)
                r27.resumeAppSwitches()
                r30.writeNoException()
                return r15
            L_0x0c3e:
                r15 = r10
                r13.enforceInterface(r11)
                r27.stopAppSwitches()
                r30.writeNoException()
                return r15
            L_0x0c49:
                r15 = r10
                r13.enforceInterface(r11)
                int r0 = r29.readInt()
                boolean r1 = r14.shutdown(r0)
                r30.writeNoException()
                r12.writeInt(r1)
                return r15
            L_0x0c5c:
                r15 = r10
                r13.enforceInterface(r11)
                java.lang.String r6 = r29.readString()
                int r7 = r29.readInt()
                int r0 = r29.readInt()
                if (r0 == 0) goto L_0x0c70
                r3 = r15
                goto L_0x0c71
            L_0x0c70:
                r3 = r8
            L_0x0c71:
                int r0 = r29.readInt()
                if (r0 == 0) goto L_0x0c81
                android.os.Parcelable$Creator<android.app.ProfilerInfo> r0 = android.app.ProfilerInfo.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.app.ProfilerInfo r0 = (android.app.ProfilerInfo) r0
                r4 = r0
                goto L_0x0c83
            L_0x0c81:
                r4 = r16
            L_0x0c83:
                int r8 = r29.readInt()
                r0 = r27
                r1 = r6
                r2 = r7
                r5 = r8
                boolean r0 = r0.profileControl(r1, r2, r3, r4, r5)
                r30.writeNoException()
                r12.writeInt(r0)
                return r15
            L_0x0c97:
                r15 = r10
                r13.enforceInterface(r11)
                int r0 = r29.readInt()
                if (r0 == 0) goto L_0x0cac
                android.os.Parcelable$Creator<android.content.Intent> r0 = android.content.Intent.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                r16 = r0
                android.content.Intent r16 = (android.content.Intent) r16
                goto L_0x0cad
            L_0x0cac:
            L_0x0cad:
                r0 = r16
                java.lang.String r1 = r29.readString()
                java.lang.String r2 = r29.readString()
                android.os.IBinder r3 = r14.peekService(r0, r1, r2)
                r30.writeNoException()
                r12.writeStrongBinder(r3)
                return r15
            L_0x0cc2:
                r15 = r10
                r13.enforceInterface(r11)
                java.util.List r0 = r27.getRunningAppProcesses()
                r30.writeNoException()
                r12.writeTypedList(r0)
                return r15
            L_0x0cd1:
                r15 = r10
                r13.enforceInterface(r11)
                int r0 = r29.readInt()
                int r1 = r29.readInt()
                java.util.List r2 = r14.getServices(r0, r1)
                r30.writeNoException()
                r12.writeTypedList(r2)
                return r15
            L_0x0ce8:
                r15 = r10
                r13.enforceInterface(r11)
                int[] r0 = r29.createIntArray()
                java.lang.String r1 = r29.readString()
                int r2 = r29.readInt()
                if (r2 == 0) goto L_0x0cfc
                r8 = r15
            L_0x0cfc:
                r2 = r8
                boolean r3 = r14.killPids(r0, r1, r2)
                r30.writeNoException()
                r12.writeInt(r3)
                return r15
            L_0x0d08:
                r15 = r10
                r13.enforceInterface(r11)
                java.lang.String r0 = r29.readString()
                int r1 = r29.readInt()
                r14.forceStopPackage(r0, r1)
                r30.writeNoException()
                return r15
            L_0x0d1b:
                r15 = r10
                r13.enforceInterface(r11)
                java.lang.String r0 = r29.readString()
                int r1 = r29.readInt()
                if (r1 == 0) goto L_0x0d2b
                r8 = r15
            L_0x0d2b:
                r1 = r8
                android.os.IBinder r2 = r29.readStrongBinder()
                android.content.pm.IPackageDataObserver r2 = android.content.pm.IPackageDataObserver.Stub.asInterface(r2)
                int r3 = r29.readInt()
                boolean r4 = r14.clearApplicationUserData(r0, r1, r2, r3)
                r30.writeNoException()
                r12.writeInt(r4)
                return r15
            L_0x0d43:
                r15 = r10
                r13.enforceInterface(r11)
                java.util.List r0 = r27.getProcessesInErrorState()
                r30.writeNoException()
                r12.writeTypedList(r0)
                return r15
            L_0x0d52:
                r15 = r10
                r13.enforceInterface(r11)
                android.app.ActivityManager$MemoryInfo r0 = new android.app.ActivityManager$MemoryInfo
                r0.<init>()
                r14.getMemoryInfo(r0)
                r30.writeNoException()
                r12.writeInt(r15)
                r0.writeToParcel(r12, r15)
                return r15
            L_0x0d69:
                r15 = r10
                r13.enforceInterface(r11)
                android.os.IBinder r0 = r29.readStrongBinder()
                int r1 = r29.readInt()
                if (r1 == 0) goto L_0x0d79
                r8 = r15
            L_0x0d79:
                r1 = r8
                boolean r2 = r14.moveActivityTaskToBack(r0, r1)
                r30.writeNoException()
                r12.writeInt(r2)
                return r15
            L_0x0d85:
                r15 = r10
                r13.enforceInterface(r11)
                int r0 = r29.readInt()
                if (r0 == 0) goto L_0x0d9a
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                r16 = r0
                android.content.ComponentName r16 = (android.content.ComponentName) r16
                goto L_0x0d9b
            L_0x0d9a:
            L_0x0d9b:
                r0 = r16
                android.os.IBinder r1 = r29.readStrongBinder()
                int r2 = r14.getForegroundServiceType(r0, r1)
                r30.writeNoException()
                r12.writeInt(r2)
                return r15
            L_0x0dac:
                r15 = r10
                r13.enforceInterface(r11)
                int r0 = r29.readInt()
                if (r0 == 0) goto L_0x0dc0
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                r1 = r0
                goto L_0x0dc2
            L_0x0dc0:
                r1 = r16
            L_0x0dc2:
                android.os.IBinder r7 = r29.readStrongBinder()
                int r8 = r29.readInt()
                int r0 = r29.readInt()
                if (r0 == 0) goto L_0x0dda
                android.os.Parcelable$Creator<android.app.Notification> r0 = android.app.Notification.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.app.Notification r0 = (android.app.Notification) r0
                r4 = r0
                goto L_0x0ddc
            L_0x0dda:
                r4 = r16
            L_0x0ddc:
                int r9 = r29.readInt()
                int r10 = r29.readInt()
                r0 = r27
                r2 = r7
                r3 = r8
                r5 = r9
                r6 = r10
                r0.setServiceForeground(r1, r2, r3, r4, r5, r6)
                r30.writeNoException()
                return r15
            L_0x0df1:
                r15 = r10
                r13.enforceInterface(r11)
                android.os.IBinder r0 = r29.readStrongBinder()
                int r1 = r29.readInt()
                int r2 = r29.readInt()
                if (r2 == 0) goto L_0x0e05
                r8 = r15
            L_0x0e05:
                r2 = r8
                java.lang.String r3 = r29.readString()
                r14.setProcessImportant(r0, r1, r2, r3)
                r30.writeNoException()
                return r15
            L_0x0e11:
                r15 = r10
                r13.enforceInterface(r11)
                android.os.IBinder r0 = r29.readStrongBinder()
                int r1 = r29.readInt()
                if (r1 == 0) goto L_0x0e2a
                android.os.Parcelable$Creator<android.content.Intent> r1 = android.content.Intent.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r13)
                r16 = r1
                android.content.Intent r16 = (android.content.Intent) r16
                goto L_0x0e2b
            L_0x0e2a:
            L_0x0e2b:
                r1 = r16
                int r2 = r29.readInt()
                if (r2 == 0) goto L_0x0e35
                r8 = r15
            L_0x0e35:
                r2 = r8
                r14.unbindFinished(r0, r1, r2)
                r30.writeNoException()
                return r15
            L_0x0e3d:
                r15 = r10
                r13.enforceInterface(r11)
                android.os.IBinder r0 = r29.readStrongBinder()
                int r1 = r29.readInt()
                r14.setRequestedOrientation(r0, r1)
                r30.writeNoException()
                return r15
            L_0x0e50:
                r15 = r10
                r13.enforceInterface(r11)
                android.os.IBinder r0 = r29.readStrongBinder()
                int r1 = r29.readInt()
                if (r1 == 0) goto L_0x0e60
                r8 = r15
            L_0x0e60:
                r1 = r8
                r14.removeContentProvider(r0, r1)
                return r15
            L_0x0e65:
                r15 = r10
                r13.enforceInterface(r11)
                android.os.IBinder r0 = r29.readStrongBinder()
                android.content.IIntentSender r6 = android.content.IIntentSender.Stub.asInterface(r0)
                int r0 = r29.readInt()
                if (r0 == 0) goto L_0x0e81
                android.os.Parcelable$Creator<android.os.WorkSource> r0 = android.os.WorkSource.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.os.WorkSource r0 = (android.os.WorkSource) r0
                r2 = r0
                goto L_0x0e83
            L_0x0e81:
                r2 = r16
            L_0x0e83:
                int r7 = r29.readInt()
                java.lang.String r8 = r29.readString()
                java.lang.String r9 = r29.readString()
                r0 = r27
                r1 = r6
                r3 = r7
                r4 = r8
                r5 = r9
                r0.noteWakeupAlarm(r1, r2, r3, r4, r5)
                r30.writeNoException()
                return r15
            L_0x0e9c:
                r15 = r10
                r13.enforceInterface(r11)
                r27.enterSafeMode()
                r30.writeNoException()
                return r15
            L_0x0ea7:
                r15 = r10
                r13.enforceInterface(r11)
                android.os.IBinder r0 = r29.readStrongBinder()
                android.content.IIntentSender r0 = android.content.IIntentSender.Stub.asInterface(r0)
                android.os.IBinder r1 = r29.readStrongBinder()
                com.android.internal.os.IResultReceiver r1 = com.android.internal.os.IResultReceiver.Stub.asInterface(r1)
                r14.unregisterIntentSenderCancelListener(r0, r1)
                r30.writeNoException()
                return r15
            L_0x0ec2:
                r15 = r10
                r13.enforceInterface(r11)
                android.os.IBinder r0 = r29.readStrongBinder()
                android.content.IIntentSender r0 = android.content.IIntentSender.Stub.asInterface(r0)
                android.os.IBinder r1 = r29.readStrongBinder()
                com.android.internal.os.IResultReceiver r1 = com.android.internal.os.IResultReceiver.Stub.asInterface(r1)
                r14.registerIntentSenderCancelListener(r0, r1)
                r30.writeNoException()
                return r15
            L_0x0edd:
                r15 = r10
                r13.enforceInterface(r11)
                android.os.IBinder r0 = r29.readStrongBinder()
                android.content.IIntentSender r0 = android.content.IIntentSender.Stub.asInterface(r0)
                java.lang.String r1 = r14.getPackageForIntentSender(r0)
                r30.writeNoException()
                r12.writeString(r1)
                return r15
            L_0x0ef4:
                r15 = r10
                r13.enforceInterface(r11)
                android.os.IBinder r0 = r29.readStrongBinder()
                android.content.IIntentSender r0 = android.content.IIntentSender.Stub.asInterface(r0)
                r14.cancelIntentSender(r0)
                r30.writeNoException()
                return r15
            L_0x0f07:
                r15 = r10
                r13.enforceInterface(r11)
                int r17 = r29.readInt()
                java.lang.String r18 = r29.readString()
                android.os.IBinder r19 = r29.readStrongBinder()
                java.lang.String r20 = r29.readString()
                int r21 = r29.readInt()
                android.os.Parcelable$Creator<android.content.Intent> r0 = android.content.Intent.CREATOR
                java.lang.Object[] r0 = r13.createTypedArray(r0)
                r22 = r0
                android.content.Intent[] r22 = (android.content.Intent[]) r22
                java.lang.String[] r23 = r29.createStringArray()
                int r24 = r29.readInt()
                int r0 = r29.readInt()
                if (r0 == 0) goto L_0x0f41
                android.os.Parcelable$Creator<android.os.Bundle> r0 = android.os.Bundle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.os.Bundle r0 = (android.os.Bundle) r0
                r9 = r0
                goto L_0x0f43
            L_0x0f41:
                r9 = r16
            L_0x0f43:
                int r25 = r29.readInt()
                r0 = r27
                r1 = r17
                r2 = r18
                r3 = r19
                r4 = r20
                r5 = r21
                r6 = r22
                r7 = r23
                r8 = r24
                r10 = r25
                android.content.IIntentSender r0 = r0.getIntentSender(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10)
                r30.writeNoException()
                if (r0 == 0) goto L_0x0f6b
                android.os.IBinder r16 = r0.asBinder()
            L_0x0f68:
                r1 = r16
                goto L_0x0f6c
            L_0x0f6b:
                goto L_0x0f68
            L_0x0f6c:
                r12.writeStrongBinder(r1)
                return r15
            L_0x0f70:
                r15 = r10
                r13.enforceInterface(r11)
                android.os.IBinder r0 = r29.readStrongBinder()
                int r1 = r29.readInt()
                int r2 = r29.readInt()
                int r3 = r29.readInt()
                r14.serviceDoneExecuting(r0, r1, r2, r3)
                return r15
            L_0x0f88:
                r15 = r10
                r13.enforceInterface(r11)
                int r0 = r29.readInt()
                int r1 = r29.readInt()
                int r2 = r29.readInt()
                android.content.pm.ParceledListSlice r3 = r14.getRecentTasks(r0, r1, r2)
                r30.writeNoException()
                if (r3 == 0) goto L_0x0fa8
                r12.writeInt(r15)
                r3.writeToParcel(r12, r15)
                goto L_0x0fab
            L_0x0fa8:
                r12.writeInt(r8)
            L_0x0fab:
                return r15
            L_0x0fac:
                r15 = r10
                r13.enforceInterface(r11)
                int r0 = r29.readInt()
                r14.signalPersistentProcesses(r0)
                r30.writeNoException()
                return r15
            L_0x0fbb:
                r15 = r10
                r13.enforceInterface(r11)
                android.os.IBinder r0 = r29.readStrongBinder()
                android.app.IApplicationThread r0 = android.app.IApplicationThread.Stub.asInterface(r0)
                int r1 = r29.readInt()
                if (r1 == 0) goto L_0x0fcf
                r8 = r15
            L_0x0fcf:
                r1 = r8
                r14.showWaitingForDebugger(r0, r1)
                r30.writeNoException()
                return r15
            L_0x0fd7:
                r15 = r10
                r13.enforceInterface(r11)
                android.os.IBinder r0 = r29.readStrongBinder()
                android.app.IActivityController r0 = android.app.IActivityController.Stub.asInterface(r0)
                int r1 = r29.readInt()
                if (r1 == 0) goto L_0x0feb
                r8 = r15
            L_0x0feb:
                r1 = r8
                r14.setActivityController(r0, r1)
                r30.writeNoException()
                return r15
            L_0x0ff3:
                r15 = r10
                r13.enforceInterface(r11)
                android.os.IBinder r0 = r29.readStrongBinder()
                android.app.IApplicationThread r6 = android.app.IApplicationThread.Stub.asInterface(r0)
                java.lang.String r7 = r29.readString()
                int r0 = r29.readInt()
                if (r0 == 0) goto L_0x1013
                android.os.Parcelable$Creator<android.net.Uri> r0 = android.net.Uri.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.net.Uri r0 = (android.net.Uri) r0
                r3 = r0
                goto L_0x1015
            L_0x1013:
                r3 = r16
            L_0x1015:
                int r8 = r29.readInt()
                int r9 = r29.readInt()
                r0 = r27
                r1 = r6
                r2 = r7
                r4 = r8
                r5 = r9
                r0.revokeUriPermission(r1, r2, r3, r4, r5)
                r30.writeNoException()
                return r15
            L_0x102a:
                r15 = r10
                r13.enforceInterface(r11)
                android.os.IBinder r0 = r29.readStrongBinder()
                android.app.IApplicationThread r6 = android.app.IApplicationThread.Stub.asInterface(r0)
                java.lang.String r7 = r29.readString()
                int r0 = r29.readInt()
                if (r0 == 0) goto L_0x104a
                android.os.Parcelable$Creator<android.net.Uri> r0 = android.net.Uri.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.net.Uri r0 = (android.net.Uri) r0
                r3 = r0
                goto L_0x104c
            L_0x104a:
                r3 = r16
            L_0x104c:
                int r8 = r29.readInt()
                int r9 = r29.readInt()
                r0 = r27
                r1 = r6
                r2 = r7
                r4 = r8
                r5 = r9
                r0.grantUriPermission(r1, r2, r3, r4, r5)
                r30.writeNoException()
                return r15
            L_0x1061:
                r15 = r10
                r13.enforceInterface(r11)
                int r0 = r29.readInt()
                if (r0 == 0) goto L_0x1075
                android.os.Parcelable$Creator<android.net.Uri> r0 = android.net.Uri.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.net.Uri r0 = (android.net.Uri) r0
                r1 = r0
                goto L_0x1077
            L_0x1075:
                r1 = r16
            L_0x1077:
                int r7 = r29.readInt()
                int r8 = r29.readInt()
                int r9 = r29.readInt()
                int r10 = r29.readInt()
                android.os.IBinder r16 = r29.readStrongBinder()
                r0 = r27
                r2 = r7
                r3 = r8
                r4 = r9
                r5 = r10
                r6 = r16
                int r0 = r0.checkUriPermission(r1, r2, r3, r4, r5, r6)
                r30.writeNoException()
                r12.writeInt(r0)
                return r15
            L_0x109e:
                r15 = r10
                r13.enforceInterface(r11)
                java.lang.String r0 = r29.readString()
                int r1 = r29.readInt()
                int r2 = r29.readInt()
                int r3 = r14.checkPermission(r0, r1, r2)
                r30.writeNoException()
                r12.writeInt(r3)
                return r15
            L_0x10b9:
                r15 = r10
                r13.enforceInterface(r11)
                int r0 = r27.getProcessLimit()
                r30.writeNoException()
                r12.writeInt(r0)
                return r15
            L_0x10c8:
                r15 = r10
                r13.enforceInterface(r11)
                int r0 = r29.readInt()
                r14.setProcessLimit(r0)
                r30.writeNoException()
                return r15
            L_0x10d7:
                r15 = r10
                r13.enforceInterface(r11)
                int r0 = r29.readInt()
                if (r0 == 0) goto L_0x10ec
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                r16 = r0
                android.content.ComponentName r16 = (android.content.ComponentName) r16
                goto L_0x10ed
            L_0x10ec:
            L_0x10ed:
                r0 = r16
                android.os.IBinder r1 = r29.readStrongBinder()
                int r2 = r29.readInt()
                boolean r3 = r14.stopServiceToken(r0, r1, r2)
                r30.writeNoException()
                r12.writeInt(r3)
                return r15
            L_0x1102:
                r15 = r10
                r13.enforceInterface(r11)
                int r0 = r29.readInt()
                if (r0 == 0) goto L_0x1117
                android.os.Parcelable$Creator<android.content.res.Configuration> r0 = android.content.res.Configuration.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                r16 = r0
                android.content.res.Configuration r16 = (android.content.res.Configuration) r16
                goto L_0x1118
            L_0x1117:
            L_0x1118:
                r0 = r16
                boolean r1 = r14.updateConfiguration(r0)
                r30.writeNoException()
                r12.writeInt(r1)
                return r15
            L_0x1125:
                r15 = r10
                r13.enforceInterface(r11)
                android.content.res.Configuration r0 = r27.getConfiguration()
                r30.writeNoException()
                if (r0 == 0) goto L_0x1139
                r12.writeInt(r15)
                r0.writeToParcel(r12, r15)
                goto L_0x113c
            L_0x1139:
                r12.writeInt(r8)
            L_0x113c:
                return r15
            L_0x113d:
                r15 = r10
                r13.enforceInterface(r11)
                android.os.IBinder r0 = r29.readStrongBinder()
                android.app.IApplicationThread r0 = android.app.IApplicationThread.Stub.asInterface(r0)
                int r1 = r29.readInt()
                int r2 = r29.readInt()
                if (r2 == 0) goto L_0x115e
                android.os.Parcelable$Creator<android.os.Bundle> r2 = android.os.Bundle.CREATOR
                java.lang.Object r2 = r2.createFromParcel(r13)
                r16 = r2
                android.os.Bundle r16 = (android.os.Bundle) r16
                goto L_0x115f
            L_0x115e:
            L_0x115f:
                r2 = r16
                r14.finishInstrumentation(r0, r1, r2)
                r30.writeNoException()
                return r15
            L_0x1168:
                r15 = r10
                r13.enforceInterface(r11)
                android.os.IBinder r0 = r29.readStrongBinder()
                android.app.IApplicationThread r0 = android.app.IApplicationThread.Stub.asInterface(r0)
                int r1 = r29.readInt()
                if (r1 == 0) goto L_0x1185
                android.os.Parcelable$Creator<android.os.Bundle> r1 = android.os.Bundle.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r13)
                r16 = r1
                android.os.Bundle r16 = (android.os.Bundle) r16
                goto L_0x1186
            L_0x1185:
            L_0x1186:
                r1 = r16
                r14.addInstrumentationResults(r0, r1)
                r30.writeNoException()
                return r15
            L_0x118f:
                r15 = r10
                r13.enforceInterface(r11)
                int r0 = r29.readInt()
                if (r0 == 0) goto L_0x11a3
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                r1 = r0
                goto L_0x11a5
            L_0x11a3:
                r1 = r16
            L_0x11a5:
                java.lang.String r9 = r29.readString()
                int r10 = r29.readInt()
                int r0 = r29.readInt()
                if (r0 == 0) goto L_0x11bd
                android.os.Parcelable$Creator<android.os.Bundle> r0 = android.os.Bundle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.os.Bundle r0 = (android.os.Bundle) r0
                r4 = r0
                goto L_0x11bf
            L_0x11bd:
                r4 = r16
            L_0x11bf:
                android.os.IBinder r0 = r29.readStrongBinder()
                android.app.IInstrumentationWatcher r16 = android.app.IInstrumentationWatcher.Stub.asInterface(r0)
                android.os.IBinder r0 = r29.readStrongBinder()
                android.app.IUiAutomationConnection r17 = android.app.IUiAutomationConnection.Stub.asInterface(r0)
                int r18 = r29.readInt()
                java.lang.String r19 = r29.readString()
                r0 = r27
                r2 = r9
                r3 = r10
                r5 = r16
                r6 = r17
                r7 = r18
                r8 = r19
                boolean r0 = r0.startInstrumentation(r1, r2, r3, r4, r5, r6, r7, r8)
                r30.writeNoException()
                r12.writeInt(r0)
                return r15
            L_0x11ee:
                r15 = r10
                r13.enforceInterface(r11)
                int r0 = r29.readInt()
                if (r0 == 0) goto L_0x11fa
                r8 = r15
            L_0x11fa:
                r0 = r8
                r14.setAlwaysFinish(r0)
                r30.writeNoException()
                return r15
            L_0x1202:
                r15 = r10
                r13.enforceInterface(r11)
                java.lang.String r0 = r29.readString()
                java.lang.String r1 = r29.readString()
                r14.setAgentApp(r0, r1)
                r30.writeNoException()
                return r15
            L_0x1215:
                r15 = r10
                r13.enforceInterface(r11)
                java.lang.String r0 = r29.readString()
                int r1 = r29.readInt()
                if (r1 == 0) goto L_0x1225
                r1 = r15
                goto L_0x1226
            L_0x1225:
                r1 = r8
            L_0x1226:
                int r2 = r29.readInt()
                if (r2 == 0) goto L_0x122e
                r8 = r15
            L_0x122e:
                r2 = r8
                r14.setDebugApp(r0, r1, r2)
                r30.writeNoException()
                return r15
            L_0x1236:
                r15 = r10
                r13.enforceInterface(r11)
                android.os.IBinder r0 = r29.readStrongBinder()
                int r1 = r29.readInt()
                if (r1 == 0) goto L_0x124f
                android.os.Parcelable$Creator<android.content.Intent> r1 = android.content.Intent.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r13)
                r16 = r1
                android.content.Intent r16 = (android.content.Intent) r16
                goto L_0x1250
            L_0x124f:
            L_0x1250:
                r1 = r16
                android.os.IBinder r2 = r29.readStrongBinder()
                r14.publishService(r0, r1, r2)
                r30.writeNoException()
                return r15
            L_0x125d:
                r15 = r10
                r13.enforceInterface(r11)
                android.os.IBinder r0 = r29.readStrongBinder()
                android.app.IServiceConnection r0 = android.app.IServiceConnection.Stub.asInterface(r0)
                boolean r1 = r14.unbindService(r0)
                r30.writeNoException()
                r12.writeInt(r1)
                return r15
            L_0x1274:
                r15 = r10
                r13.enforceInterface(r11)
                android.os.IBinder r0 = r29.readStrongBinder()
                android.app.IServiceConnection r0 = android.app.IServiceConnection.Stub.asInterface(r0)
                int r1 = r29.readInt()
                int r2 = r29.readInt()
                r14.updateServiceGroup(r0, r1, r2)
                r30.writeNoException()
                return r15
            L_0x128f:
                r15 = r10
                r13.enforceInterface(r11)
                android.os.IBinder r0 = r29.readStrongBinder()
                android.app.IApplicationThread r10 = android.app.IApplicationThread.Stub.asInterface(r0)
                android.os.IBinder r17 = r29.readStrongBinder()
                int r0 = r29.readInt()
                if (r0 == 0) goto L_0x12af
                android.os.Parcelable$Creator<android.content.Intent> r0 = android.content.Intent.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.content.Intent r0 = (android.content.Intent) r0
                r3 = r0
                goto L_0x12b1
            L_0x12af:
                r3 = r16
            L_0x12b1:
                java.lang.String r16 = r29.readString()
                android.os.IBinder r0 = r29.readStrongBinder()
                android.app.IServiceConnection r18 = android.app.IServiceConnection.Stub.asInterface(r0)
                int r19 = r29.readInt()
                java.lang.String r20 = r29.readString()
                java.lang.String r21 = r29.readString()
                int r22 = r29.readInt()
                r0 = r27
                r1 = r10
                r2 = r17
                r4 = r16
                r5 = r18
                r6 = r19
                r7 = r20
                r8 = r21
                r9 = r22
                int r0 = r0.bindIsolatedService(r1, r2, r3, r4, r5, r6, r7, r8, r9)
                r30.writeNoException()
                r12.writeInt(r0)
                return r15
            L_0x12e9:
                r15 = r10
                r13.enforceInterface(r11)
                android.os.IBinder r0 = r29.readStrongBinder()
                android.app.IApplicationThread r9 = android.app.IApplicationThread.Stub.asInterface(r0)
                android.os.IBinder r10 = r29.readStrongBinder()
                int r0 = r29.readInt()
                if (r0 == 0) goto L_0x1309
                android.os.Parcelable$Creator<android.content.Intent> r0 = android.content.Intent.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.content.Intent r0 = (android.content.Intent) r0
                r3 = r0
                goto L_0x130b
            L_0x1309:
                r3 = r16
            L_0x130b:
                java.lang.String r16 = r29.readString()
                android.os.IBinder r0 = r29.readStrongBinder()
                android.app.IServiceConnection r17 = android.app.IServiceConnection.Stub.asInterface(r0)
                int r18 = r29.readInt()
                java.lang.String r19 = r29.readString()
                int r20 = r29.readInt()
                r0 = r27
                r1 = r9
                r2 = r10
                r4 = r16
                r5 = r17
                r6 = r18
                r7 = r19
                r8 = r20
                int r0 = r0.bindService(r1, r2, r3, r4, r5, r6, r7, r8)
                r30.writeNoException()
                r12.writeInt(r0)
                return r15
            L_0x133c:
                r15 = r10
                r13.enforceInterface(r11)
                android.os.IBinder r0 = r29.readStrongBinder()
                android.app.IApplicationThread r0 = android.app.IApplicationThread.Stub.asInterface(r0)
                int r1 = r29.readInt()
                if (r1 == 0) goto L_0x1359
                android.os.Parcelable$Creator<android.content.Intent> r1 = android.content.Intent.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r13)
                r16 = r1
                android.content.Intent r16 = (android.content.Intent) r16
                goto L_0x135a
            L_0x1359:
            L_0x135a:
                r1 = r16
                java.lang.String r2 = r29.readString()
                int r3 = r29.readInt()
                int r4 = r14.stopService(r0, r1, r2, r3)
                r30.writeNoException()
                r12.writeInt(r4)
                return r15
            L_0x136f:
                r15 = r10
                r13.enforceInterface(r11)
                android.os.IBinder r0 = r29.readStrongBinder()
                android.app.IApplicationThread r7 = android.app.IApplicationThread.Stub.asInterface(r0)
                int r0 = r29.readInt()
                if (r0 == 0) goto L_0x138b
                android.os.Parcelable$Creator<android.content.Intent> r0 = android.content.Intent.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.content.Intent r0 = (android.content.Intent) r0
                r2 = r0
                goto L_0x138d
            L_0x138b:
                r2 = r16
            L_0x138d:
                java.lang.String r9 = r29.readString()
                int r0 = r29.readInt()
                if (r0 == 0) goto L_0x1399
                r4 = r15
                goto L_0x139a
            L_0x1399:
                r4 = r8
            L_0x139a:
                java.lang.String r10 = r29.readString()
                int r16 = r29.readInt()
                r0 = r27
                r1 = r7
                r3 = r9
                r5 = r10
                r6 = r16
                android.content.ComponentName r0 = r0.startService(r1, r2, r3, r4, r5, r6)
                r30.writeNoException()
                if (r0 == 0) goto L_0x13b9
                r12.writeInt(r15)
                r0.writeToParcel((android.os.Parcel) r12, (int) r15)
                goto L_0x13bc
            L_0x13b9:
                r12.writeInt(r8)
            L_0x13bc:
                return r15
            L_0x13bd:
                r15 = r10
                r13.enforceInterface(r11)
                int r0 = r29.readInt()
                if (r0 == 0) goto L_0x13d2
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                r16 = r0
                android.content.ComponentName r16 = (android.content.ComponentName) r16
                goto L_0x13d3
            L_0x13d2:
            L_0x13d3:
                r0 = r16
                android.app.PendingIntent r1 = r14.getRunningServiceControlPanel(r0)
                r30.writeNoException()
                if (r1 == 0) goto L_0x13e5
                r12.writeInt(r15)
                r1.writeToParcel(r12, r15)
                goto L_0x13e8
            L_0x13e5:
                r12.writeInt(r8)
            L_0x13e8:
                return r15
            L_0x13e9:
                r15 = r10
                r13.enforceInterface(r11)
                android.os.IBinder r0 = r29.readStrongBinder()
                int r1 = r29.readInt()
                int r2 = r29.readInt()
                boolean r3 = r14.refContentProvider(r0, r1, r2)
                r30.writeNoException()
                r12.writeInt(r3)
                return r15
            L_0x1404:
                r15 = r10
                r13.enforceInterface(r11)
                android.os.IBinder r0 = r29.readStrongBinder()
                android.app.IApplicationThread r0 = android.app.IApplicationThread.Stub.asInterface(r0)
                android.os.Parcelable$Creator<android.app.ContentProviderHolder> r1 = android.app.ContentProviderHolder.CREATOR
                java.util.ArrayList r1 = r13.createTypedArrayList(r1)
                r14.publishContentProviders(r0, r1)
                r30.writeNoException()
                return r15
            L_0x141d:
                r15 = r10
                r13.enforceInterface(r11)
                android.os.IBinder r0 = r29.readStrongBinder()
                android.app.IApplicationThread r6 = android.app.IApplicationThread.Stub.asInterface(r0)
                java.lang.String r7 = r29.readString()
                java.lang.String r9 = r29.readString()
                int r10 = r29.readInt()
                int r0 = r29.readInt()
                if (r0 == 0) goto L_0x143d
                r5 = r15
                goto L_0x143e
            L_0x143d:
                r5 = r8
            L_0x143e:
                r0 = r27
                r1 = r6
                r2 = r7
                r3 = r9
                r4 = r10
                android.app.ContentProviderHolder r0 = r0.getContentProvider(r1, r2, r3, r4, r5)
                r30.writeNoException()
                if (r0 == 0) goto L_0x1454
                r12.writeInt(r15)
                r0.writeToParcel(r12, r15)
                goto L_0x1457
            L_0x1454:
                r12.writeInt(r8)
            L_0x1457:
                return r15
            L_0x1458:
                r15 = r10
                r13.enforceInterface(r11)
                android.os.IBinder r0 = r29.readStrongBinder()
                int r1 = r29.readInt()
                if (r1 == 0) goto L_0x1468
                r8 = r15
            L_0x1468:
                r1 = r8
                int r2 = r14.getTaskForActivity(r0, r1)
                r30.writeNoException()
                r12.writeInt(r2)
                return r15
            L_0x1474:
                r15 = r10
                r13.enforceInterface(r11)
                android.os.IBinder r0 = r29.readStrongBinder()
                android.app.IApplicationThread r6 = android.app.IApplicationThread.Stub.asInterface(r0)
                java.lang.String r7 = r29.readString()
                int r8 = r29.readInt()
                int r9 = r29.readInt()
                int r0 = r29.readInt()
                if (r0 == 0) goto L_0x149c
                android.os.Parcelable$Creator<android.os.Bundle> r0 = android.os.Bundle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.os.Bundle r0 = (android.os.Bundle) r0
                r5 = r0
                goto L_0x149e
            L_0x149c:
                r5 = r16
            L_0x149e:
                r0 = r27
                r1 = r6
                r2 = r7
                r3 = r8
                r4 = r9
                r0.moveTaskToFront(r1, r2, r3, r4, r5)
                r30.writeNoException()
                return r15
            L_0x14ab:
                r15 = r10
                r13.enforceInterface(r11)
                int r0 = r29.readInt()
                int r1 = r29.readInt()
                int r2 = r29.readInt()
                java.util.List r3 = r14.getFilteredTasks(r0, r1, r2)
                r30.writeNoException()
                r12.writeTypedList(r3)
                return r15
            L_0x14c6:
                r15 = r10
                r13.enforceInterface(r11)
                int r0 = r29.readInt()
                java.util.List r1 = r14.getTasks(r0)
                r30.writeNoException()
                r12.writeTypedList(r1)
                return r15
            L_0x14d9:
                r15 = r10
                r13.enforceInterface(r11)
                android.os.IBinder r0 = r29.readStrongBinder()
                android.app.IApplicationThread r0 = android.app.IApplicationThread.Stub.asInterface(r0)
                long r1 = r29.readLong()
                r14.attachApplication(r0, r1)
                r30.writeNoException()
                return r15
            L_0x14f0:
                r15 = r10
                r13.enforceInterface(r11)
                android.os.IBinder r7 = r29.readStrongBinder()
                int r9 = r29.readInt()
                java.lang.String r10 = r29.readString()
                int r0 = r29.readInt()
                if (r0 == 0) goto L_0x1510
                android.os.Parcelable$Creator<android.os.Bundle> r0 = android.os.Bundle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.os.Bundle r0 = (android.os.Bundle) r0
                r4 = r0
                goto L_0x1512
            L_0x1510:
                r4 = r16
            L_0x1512:
                int r0 = r29.readInt()
                if (r0 == 0) goto L_0x151a
                r5 = r15
                goto L_0x151b
            L_0x151a:
                r5 = r8
            L_0x151b:
                int r8 = r29.readInt()
                r0 = r27
                r1 = r7
                r2 = r9
                r3 = r10
                r6 = r8
                r0.finishReceiver(r1, r2, r3, r4, r5, r6)
                return r15
            L_0x1529:
                r15 = r10
                r13.enforceInterface(r11)
                android.os.IBinder r0 = r29.readStrongBinder()
                android.app.IApplicationThread r0 = android.app.IApplicationThread.Stub.asInterface(r0)
                int r1 = r29.readInt()
                if (r1 == 0) goto L_0x1546
                android.os.Parcelable$Creator<android.content.Intent> r1 = android.content.Intent.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r13)
                r16 = r1
                android.content.Intent r16 = (android.content.Intent) r16
                goto L_0x1547
            L_0x1546:
            L_0x1547:
                r1 = r16
                int r2 = r29.readInt()
                r14.unbroadcastIntent(r0, r1, r2)
                r30.writeNoException()
                return r15
            L_0x1554:
                r15 = r10
                r13.enforceInterface(r11)
                android.os.IBinder r0 = r29.readStrongBinder()
                android.app.IApplicationThread r17 = android.app.IApplicationThread.Stub.asInterface(r0)
                int r0 = r29.readInt()
                if (r0 == 0) goto L_0x1570
                android.os.Parcelable$Creator<android.content.Intent> r0 = android.content.Intent.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.content.Intent r0 = (android.content.Intent) r0
                r2 = r0
                goto L_0x1572
            L_0x1570:
                r2 = r16
            L_0x1572:
                java.lang.String r18 = r29.readString()
                android.os.IBinder r0 = r29.readStrongBinder()
                android.content.IIntentReceiver r19 = android.content.IIntentReceiver.Stub.asInterface(r0)
                int r20 = r29.readInt()
                java.lang.String r21 = r29.readString()
                int r0 = r29.readInt()
                if (r0 == 0) goto L_0x1596
                android.os.Parcelable$Creator<android.os.Bundle> r0 = android.os.Bundle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.os.Bundle r0 = (android.os.Bundle) r0
                r7 = r0
                goto L_0x1598
            L_0x1596:
                r7 = r16
            L_0x1598:
                java.lang.String[] r22 = r29.createStringArray()
                int r23 = r29.readInt()
                int r0 = r29.readInt()
                if (r0 == 0) goto L_0x15b0
                android.os.Parcelable$Creator<android.os.Bundle> r0 = android.os.Bundle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.os.Bundle r0 = (android.os.Bundle) r0
                r10 = r0
                goto L_0x15b2
            L_0x15b0:
                r10 = r16
            L_0x15b2:
                int r0 = r29.readInt()
                if (r0 == 0) goto L_0x15ba
                r0 = r15
                goto L_0x15bb
            L_0x15ba:
                r0 = r8
            L_0x15bb:
                r9 = r11
                r11 = r0
                int r0 = r29.readInt()
                if (r0 == 0) goto L_0x15c5
                r8 = r15
            L_0x15c5:
                r6 = r12
                r12 = r8
                int r16 = r29.readInt()
                r0 = r27
                r1 = r17
                r3 = r18
                r4 = r19
                r5 = r20
                r8 = r6
                r6 = r21
                r15 = r8
                r8 = r22
                r14 = r9
                r9 = r23
                r26 = r14
                r14 = r13
                r13 = r16
                int r0 = r0.broadcastIntent(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13)
                r30.writeNoException()
                r15.writeInt(r0)
                r1 = 1
                return r1
            L_0x15ef:
                r1 = r10
                r26 = r11
                r15 = r12
                r14 = r13
                r0 = r26
                r14.enforceInterface(r0)
                android.os.IBinder r2 = r29.readStrongBinder()
                android.content.IIntentReceiver r2 = android.content.IIntentReceiver.Stub.asInterface(r2)
                r12 = r0
                r11 = r27
                r11.unregisterReceiver(r2)
                r30.writeNoException()
                return r1
            L_0x160b:
                r15 = r12
                r12 = r11
                r11 = r14
                r14 = r13
                r14.enforceInterface(r12)
                android.os.IBinder r0 = r29.readStrongBinder()
                android.app.IApplicationThread r9 = android.app.IApplicationThread.Stub.asInterface(r0)
                java.lang.String r10 = r29.readString()
                android.os.IBinder r0 = r29.readStrongBinder()
                android.content.IIntentReceiver r13 = android.content.IIntentReceiver.Stub.asInterface(r0)
                int r0 = r29.readInt()
                if (r0 == 0) goto L_0x1636
                android.os.Parcelable$Creator<android.content.IntentFilter> r0 = android.content.IntentFilter.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r14)
                android.content.IntentFilter r0 = (android.content.IntentFilter) r0
                r4 = r0
                goto L_0x1638
            L_0x1636:
                r4 = r16
            L_0x1638:
                java.lang.String r16 = r29.readString()
                int r17 = r29.readInt()
                int r18 = r29.readInt()
                r0 = r27
                r1 = r9
                r2 = r10
                r3 = r13
                r5 = r16
                r6 = r17
                r7 = r18
                android.content.Intent r0 = r0.registerReceiver(r1, r2, r3, r4, r5, r6, r7)
                r30.writeNoException()
                if (r0 == 0) goto L_0x1660
                r1 = 1
                r15.writeInt(r1)
                r0.writeToParcel(r15, r1)
                goto L_0x1664
            L_0x1660:
                r1 = 1
                r15.writeInt(r8)
            L_0x1664:
                return r1
            L_0x1665:
                r15 = r12
                r12 = r11
                r11 = r14
                r14 = r13
                r14.enforceInterface(r12)
                android.os.IBinder r0 = r29.readStrongBinder()
                int r1 = r29.readInt()
                int r2 = r29.readInt()
                if (r2 == 0) goto L_0x1685
                android.os.Parcelable$Creator<android.content.Intent> r2 = android.content.Intent.CREATOR
                java.lang.Object r2 = r2.createFromParcel(r14)
                r16 = r2
                android.content.Intent r16 = (android.content.Intent) r16
                goto L_0x1686
            L_0x1685:
            L_0x1686:
                r2 = r16
                int r3 = r29.readInt()
                boolean r4 = r11.finishActivity(r0, r1, r2, r3)
                r30.writeNoException()
                r15.writeInt(r4)
                r5 = 1
                return r5
            L_0x1698:
                r5 = r10
                r15 = r12
                r12 = r11
                r11 = r14
                r14 = r13
                r14.enforceInterface(r12)
                r27.unhandledBack()
                r30.writeNoException()
                return r5
            L_0x16a7:
                r15 = r12
                r12 = r11
                r11 = r14
                r14 = r13
                r14.enforceInterface(r12)
                android.os.IBinder r0 = r29.readStrongBinder()
                android.app.IApplicationThread r13 = android.app.IApplicationThread.Stub.asInterface(r0)
                java.lang.String r17 = r29.readString()
                int r0 = r29.readInt()
                if (r0 == 0) goto L_0x16ca
                android.os.Parcelable$Creator<android.content.Intent> r0 = android.content.Intent.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r14)
                android.content.Intent r0 = (android.content.Intent) r0
                r3 = r0
                goto L_0x16cc
            L_0x16ca:
                r3 = r16
            L_0x16cc:
                java.lang.String r18 = r29.readString()
                android.os.IBinder r19 = r29.readStrongBinder()
                java.lang.String r20 = r29.readString()
                int r21 = r29.readInt()
                int r22 = r29.readInt()
                int r0 = r29.readInt()
                if (r0 == 0) goto L_0x16f0
                android.os.Parcelable$Creator<android.app.ProfilerInfo> r0 = android.app.ProfilerInfo.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r14)
                android.app.ProfilerInfo r0 = (android.app.ProfilerInfo) r0
                r9 = r0
                goto L_0x16f2
            L_0x16f0:
                r9 = r16
            L_0x16f2:
                int r0 = r29.readInt()
                if (r0 == 0) goto L_0x1702
                android.os.Parcelable$Creator<android.os.Bundle> r0 = android.os.Bundle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r14)
                android.os.Bundle r0 = (android.os.Bundle) r0
                r10 = r0
                goto L_0x1704
            L_0x1702:
                r10 = r16
            L_0x1704:
                r0 = r27
                r1 = r13
                r2 = r17
                r4 = r18
                r5 = r19
                r6 = r20
                r7 = r21
                r8 = r22
                int r0 = r0.startActivity(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10)
                r30.writeNoException()
                r15.writeInt(r0)
                r1 = 1
                return r1
            L_0x171f:
                r15 = r12
                r12 = r11
                r11 = r14
                r14 = r13
                r14.enforceInterface(r12)
                android.os.IBinder r0 = r29.readStrongBinder()
                int r1 = r29.readInt()
                if (r1 == 0) goto L_0x173b
                android.os.Parcelable$Creator<android.app.ApplicationErrorReport$ParcelableCrashInfo> r1 = android.app.ApplicationErrorReport.ParcelableCrashInfo.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r14)
                r16 = r1
                android.app.ApplicationErrorReport$ParcelableCrashInfo r16 = (android.app.ApplicationErrorReport.ParcelableCrashInfo) r16
                goto L_0x173c
            L_0x173b:
            L_0x173c:
                r1 = r16
                r11.handleApplicationCrash(r0, r1)
                r30.writeNoException()
                r2 = 1
                return r2
            L_0x1746:
                r15 = r12
                r12 = r11
                r11 = r14
                r14 = r13
                r14.enforceInterface(r12)
                int r0 = r29.readInt()
                java.lang.String r1 = r29.readString()
                int r2 = r11.getUidProcessState(r0, r1)
                r30.writeNoException()
                r15.writeInt(r2)
                r3 = 1
                return r3
            L_0x1761:
                r15 = r12
                r12 = r11
                r11 = r14
                r14 = r13
                r14.enforceInterface(r12)
                int r0 = r29.readInt()
                java.lang.String r1 = r29.readString()
                boolean r2 = r11.isUidActive(r0, r1)
                r30.writeNoException()
                r15.writeInt(r2)
                r3 = 1
                return r3
            L_0x177c:
                r3 = r10
                r15 = r12
                r12 = r11
                r11 = r14
                r14 = r13
                r14.enforceInterface(r12)
                android.os.IBinder r0 = r29.readStrongBinder()
                android.app.IUidObserver r0 = android.app.IUidObserver.Stub.asInterface(r0)
                r11.unregisterUidObserver(r0)
                r30.writeNoException()
                return r3
            L_0x1793:
                r15 = r12
                r12 = r11
                r11 = r14
                r14 = r13
                r14.enforceInterface(r12)
                android.os.IBinder r0 = r29.readStrongBinder()
                android.app.IUidObserver r0 = android.app.IUidObserver.Stub.asInterface(r0)
                int r1 = r29.readInt()
                int r2 = r29.readInt()
                java.lang.String r3 = r29.readString()
                r11.registerUidObserver(r0, r1, r2, r3)
                r30.writeNoException()
                r4 = 1
                return r4
            L_0x17b6:
                r15 = r12
                r12 = r11
                r11 = r14
                r14 = r13
                r14.enforceInterface(r12)
                java.lang.String r0 = r29.readString()
                android.os.ParcelFileDescriptor r1 = r11.openContentUri(r0)
                r30.writeNoException()
                if (r1 == 0) goto L_0x17d2
                r2 = 1
                r15.writeInt(r2)
                r1.writeToParcel(r15, r2)
                goto L_0x17d6
            L_0x17d2:
                r2 = 1
                r15.writeInt(r8)
            L_0x17d6:
                return r2
            L_0x17d7:
                r2 = r10
                r15 = r12
                r12 = r11
                r11 = r14
                r14 = r13
                r15.writeString(r12)
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: android.app.IActivityManager.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IActivityManager {
            public static IActivityManager sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            public ParcelFileDescriptor openContentUri(String uriString) throws RemoteException {
                ParcelFileDescriptor _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(uriString);
                    if (!this.mRemote.transact(1, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().openContentUri(uriString);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = ParcelFileDescriptor.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    ParcelFileDescriptor _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void registerUidObserver(IUidObserver observer, int which, int cutpoint, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(observer != null ? observer.asBinder() : null);
                    _data.writeInt(which);
                    _data.writeInt(cutpoint);
                    _data.writeString(callingPackage);
                    if (this.mRemote.transact(2, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().registerUidObserver(observer, which, cutpoint, callingPackage);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void unregisterUidObserver(IUidObserver observer) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(observer != null ? observer.asBinder() : null);
                    if (this.mRemote.transact(3, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().unregisterUidObserver(observer);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isUidActive(int uid, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeString(callingPackage);
                    boolean z = false;
                    if (!this.mRemote.transact(4, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isUidActive(uid, callingPackage);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _status = z;
                    _reply.recycle();
                    _data.recycle();
                    return _status;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getUidProcessState(int uid, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(5, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getUidProcessState(uid, callingPackage);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

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
                    if (this.mRemote.transact(6, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().handleApplicationCrash(app, crashInfo);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int startActivity(IApplicationThread caller, String callingPackage, Intent intent, String resolvedType, IBinder resultTo, String resultWho, int requestCode, int flags, ProfilerInfo profilerInfo, Bundle options) throws RemoteException {
                Parcel _reply;
                IBinder iBinder;
                Intent intent2 = intent;
                ProfilerInfo profilerInfo2 = profilerInfo;
                Bundle bundle = options;
                Parcel _data = Parcel.obtain();
                Parcel _reply2 = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (caller != null) {
                        try {
                            iBinder = caller.asBinder();
                        } catch (Throwable th) {
                            th = th;
                            _reply = _reply2;
                        }
                    } else {
                        iBinder = null;
                    }
                    _data.writeStrongBinder(iBinder);
                    _data.writeString(callingPackage);
                    if (intent2 != null) {
                        _data.writeInt(1);
                        intent2.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(resolvedType);
                    _data.writeStrongBinder(resultTo);
                    _data.writeString(resultWho);
                    _data.writeInt(requestCode);
                    _data.writeInt(flags);
                    if (profilerInfo2 != null) {
                        _data.writeInt(1);
                        profilerInfo2.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (bundle != null) {
                        _data.writeInt(1);
                        bundle.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(7, _data, _reply2, 0) || Stub.getDefaultImpl() == null) {
                        _reply = _reply2;
                        _reply.readException();
                        int _result = _reply.readInt();
                        _reply.recycle();
                        _data.recycle();
                        return _result;
                    }
                    _reply = _reply2;
                    try {
                        int startActivity = Stub.getDefaultImpl().startActivity(caller, callingPackage, intent, resolvedType, resultTo, resultWho, requestCode, flags, profilerInfo, options);
                        _reply.recycle();
                        _data.recycle();
                        return startActivity;
                    } catch (Throwable th2) {
                        th = th2;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    _reply = _reply2;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public void unhandledBack() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(8, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().unhandledBack();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean finishActivity(IBinder token, int code, Intent data, int finishTask) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeInt(code);
                    boolean _result = true;
                    if (data != null) {
                        _data.writeInt(1);
                        data.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(finishTask);
                    if (!this.mRemote.transact(9, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().finishActivity(token, code, data, finishTask);
                    }
                    _reply.readException();
                    if (_reply.readInt() == 0) {
                        _result = false;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public Intent registerReceiver(IApplicationThread caller, String callerPackage, IIntentReceiver receiver, IntentFilter filter, String requiredPermission, int userId, int flags) throws RemoteException {
                IntentFilter intentFilter = filter;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    Intent _result = null;
                    _data.writeStrongBinder(caller != null ? caller.asBinder() : null);
                    try {
                        _data.writeString(callerPackage);
                        _data.writeStrongBinder(receiver != null ? receiver.asBinder() : null);
                        if (intentFilter != null) {
                            _data.writeInt(1);
                            intentFilter.writeToParcel(_data, 0);
                        } else {
                            _data.writeInt(0);
                        }
                    } catch (Throwable th) {
                        th = th;
                        String str = requiredPermission;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeString(requiredPermission);
                        _data.writeInt(userId);
                        _data.writeInt(flags);
                        if (this.mRemote.transact(10, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                            _reply.readException();
                            if (_reply.readInt() != 0) {
                                _result = Intent.CREATOR.createFromParcel(_reply);
                            }
                            _reply.recycle();
                            _data.recycle();
                            return _result;
                        }
                        Intent registerReceiver = Stub.getDefaultImpl().registerReceiver(caller, callerPackage, receiver, filter, requiredPermission, userId, flags);
                        _reply.recycle();
                        _data.recycle();
                        return registerReceiver;
                    } catch (Throwable th2) {
                        th = th2;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    String str2 = callerPackage;
                    String str3 = requiredPermission;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public void unregisterReceiver(IIntentReceiver receiver) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(receiver != null ? receiver.asBinder() : null);
                    if (this.mRemote.transact(11, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().unregisterReceiver(receiver);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int broadcastIntent(IApplicationThread caller, Intent intent, String resolvedType, IIntentReceiver resultTo, int resultCode, String resultData, Bundle map, String[] requiredPermissions, int appOp, Bundle options, boolean serialized, boolean sticky, int userId) throws RemoteException {
                Parcel _data;
                Parcel _reply;
                IBinder iBinder;
                Intent intent2 = intent;
                Bundle bundle = map;
                Bundle bundle2 = options;
                Parcel _data2 = Parcel.obtain();
                Parcel _reply2 = Parcel.obtain();
                _data2.writeInterfaceToken(Stub.DESCRIPTOR);
                IBinder iBinder2 = null;
                if (caller != null) {
                    try {
                        iBinder = caller.asBinder();
                    } catch (Throwable th) {
                        th = th;
                        _reply = _reply2;
                        _data = _data2;
                    }
                } else {
                    iBinder = null;
                }
                try {
                    _data2.writeStrongBinder(iBinder);
                    if (intent2 != null) {
                        _data2.writeInt(1);
                        intent2.writeToParcel(_data2, 0);
                    } else {
                        _data2.writeInt(0);
                    }
                    _data2.writeString(resolvedType);
                    if (resultTo != null) {
                        iBinder2 = resultTo.asBinder();
                    }
                    _data2.writeStrongBinder(iBinder2);
                    _data2.writeInt(resultCode);
                    _data2.writeString(resultData);
                    if (bundle != null) {
                        _data2.writeInt(1);
                        bundle.writeToParcel(_data2, 0);
                    } else {
                        _data2.writeInt(0);
                    }
                    _data2.writeStringArray(requiredPermissions);
                    _data2.writeInt(appOp);
                    if (bundle2 != null) {
                        _data2.writeInt(1);
                        bundle2.writeToParcel(_data2, 0);
                    } else {
                        _data2.writeInt(0);
                    }
                    _data2.writeInt(serialized ? 1 : 0);
                    _data2.writeInt(sticky ? 1 : 0);
                    _data2.writeInt(userId);
                    if (this.mRemote.transact(12, _data2, _reply2, 0) || Stub.getDefaultImpl() == null) {
                        _reply = _reply2;
                        _data = _data2;
                        _reply.readException();
                        int _result = _reply.readInt();
                        _reply.recycle();
                        _data.recycle();
                        return _result;
                    }
                    _reply = _reply2;
                    _data = _data2;
                    try {
                        int broadcastIntent = Stub.getDefaultImpl().broadcastIntent(caller, intent, resolvedType, resultTo, resultCode, resultData, map, requiredPermissions, appOp, options, serialized, sticky, userId);
                        _reply.recycle();
                        _data.recycle();
                        return broadcastIntent;
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
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

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
                    if (this.mRemote.transact(13, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().unbroadcastIntent(caller, intent, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void finishReceiver(IBinder who, int resultCode, String resultData, Bundle map, boolean abortBroadcast, int flags) throws RemoteException {
                Bundle bundle = map;
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeStrongBinder(who);
                    } catch (Throwable th) {
                        th = th;
                        int i = resultCode;
                        String str = resultData;
                        boolean z = abortBroadcast;
                        int i2 = flags;
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(resultCode);
                        try {
                            _data.writeString(resultData);
                            if (bundle != null) {
                                _data.writeInt(1);
                                bundle.writeToParcel(_data, 0);
                            } else {
                                _data.writeInt(0);
                            }
                            try {
                                _data.writeInt(abortBroadcast ? 1 : 0);
                            } catch (Throwable th2) {
                                th = th2;
                                int i22 = flags;
                                _data.recycle();
                                throw th;
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            boolean z2 = abortBroadcast;
                            int i222 = flags;
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th4) {
                        th = th4;
                        String str2 = resultData;
                        boolean z22 = abortBroadcast;
                        int i2222 = flags;
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(flags);
                        try {
                            if (this.mRemote.transact(14, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                                _data.recycle();
                                return;
                            }
                            Stub.getDefaultImpl().finishReceiver(who, resultCode, resultData, map, abortBroadcast, flags);
                            _data.recycle();
                        } catch (Throwable th5) {
                            th = th5;
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th6) {
                        th = th6;
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th7) {
                    th = th7;
                    IBinder iBinder = who;
                    int i3 = resultCode;
                    String str22 = resultData;
                    boolean z222 = abortBroadcast;
                    int i22222 = flags;
                    _data.recycle();
                    throw th;
                }
            }

            public void attachApplication(IApplicationThread app, long startSeq) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(app != null ? app.asBinder() : null);
                    _data.writeLong(startSeq);
                    if (this.mRemote.transact(15, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().attachApplication(app, startSeq);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public List<ActivityManager.RunningTaskInfo> getTasks(int maxNum) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(maxNum);
                    if (!this.mRemote.transact(16, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getTasks(maxNum);
                    }
                    _reply.readException();
                    List<ActivityManager.RunningTaskInfo> _result = _reply.createTypedArrayList(ActivityManager.RunningTaskInfo.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public List<ActivityManager.RunningTaskInfo> getFilteredTasks(int maxNum, int ignoreActivityType, int ignoreWindowingMode) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(maxNum);
                    _data.writeInt(ignoreActivityType);
                    _data.writeInt(ignoreWindowingMode);
                    if (!this.mRemote.transact(17, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getFilteredTasks(maxNum, ignoreActivityType, ignoreWindowingMode);
                    }
                    _reply.readException();
                    List<ActivityManager.RunningTaskInfo> _result = _reply.createTypedArrayList(ActivityManager.RunningTaskInfo.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

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
                    if (this.mRemote.transact(18, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().moveTaskToFront(caller, callingPackage, task, flags, options);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getTaskForActivity(IBinder token, boolean onlyRoot) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeInt(onlyRoot);
                    if (!this.mRemote.transact(19, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getTaskForActivity(token, onlyRoot);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public ContentProviderHolder getContentProvider(IApplicationThread caller, String callingPackage, String name, int userId, boolean stable) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    ContentProviderHolder _result = null;
                    _data.writeStrongBinder(caller != null ? caller.asBinder() : null);
                    try {
                        _data.writeString(callingPackage);
                    } catch (Throwable th) {
                        th = th;
                        String str = name;
                        int i = userId;
                        boolean z = stable;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeString(name);
                        try {
                            _data.writeInt(userId);
                        } catch (Throwable th2) {
                            th = th2;
                            boolean z2 = stable;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                        try {
                            _data.writeInt(stable ? 1 : 0);
                            try {
                                if (this.mRemote.transact(20, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                    _reply.readException();
                                    if (_reply.readInt() != 0) {
                                        _result = ContentProviderHolder.CREATOR.createFromParcel(_reply);
                                    }
                                    _reply.recycle();
                                    _data.recycle();
                                    return _result;
                                }
                                ContentProviderHolder contentProvider = Stub.getDefaultImpl().getContentProvider(caller, callingPackage, name, userId, stable);
                                _reply.recycle();
                                _data.recycle();
                                return contentProvider;
                            } catch (Throwable th3) {
                                th = th3;
                                _reply.recycle();
                                _data.recycle();
                                throw th;
                            }
                        } catch (Throwable th4) {
                            th = th4;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th5) {
                        th = th5;
                        int i2 = userId;
                        boolean z22 = stable;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th6) {
                    th = th6;
                    String str2 = callingPackage;
                    String str3 = name;
                    int i22 = userId;
                    boolean z222 = stable;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public void publishContentProviders(IApplicationThread caller, List<ContentProviderHolder> providers) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(caller != null ? caller.asBinder() : null);
                    _data.writeTypedList(providers);
                    if (this.mRemote.transact(21, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().publishContentProviders(caller, providers);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean refContentProvider(IBinder connection, int stableDelta, int unstableDelta) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(connection);
                    _data.writeInt(stableDelta);
                    _data.writeInt(unstableDelta);
                    boolean z = false;
                    if (!this.mRemote.transact(22, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().refContentProvider(connection, stableDelta, unstableDelta);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _status = z;
                    _reply.recycle();
                    _data.recycle();
                    return _status;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

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
                    if (!this.mRemote.transact(23, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getRunningServiceControlPanel(service);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = PendingIntent.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    PendingIntent _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public ComponentName startService(IApplicationThread caller, Intent service, String resolvedType, boolean requireForeground, String callingPackage, int userId) throws RemoteException {
                Intent intent = service;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    ComponentName _result = null;
                    _data.writeStrongBinder(caller != null ? caller.asBinder() : null);
                    if (intent != null) {
                        _data.writeInt(1);
                        intent.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    try {
                        _data.writeString(resolvedType);
                    } catch (Throwable th) {
                        th = th;
                        boolean z = requireForeground;
                        String str = callingPackage;
                        int i = userId;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(requireForeground ? 1 : 0);
                        try {
                            _data.writeString(callingPackage);
                        } catch (Throwable th2) {
                            th = th2;
                            int i2 = userId;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                        try {
                            _data.writeInt(userId);
                            if (this.mRemote.transact(24, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                _reply.readException();
                                if (_reply.readInt() != 0) {
                                    _result = ComponentName.CREATOR.createFromParcel(_reply);
                                }
                                _reply.recycle();
                                _data.recycle();
                                return _result;
                            }
                            ComponentName startService = Stub.getDefaultImpl().startService(caller, service, resolvedType, requireForeground, callingPackage, userId);
                            _reply.recycle();
                            _data.recycle();
                            return startService;
                        } catch (Throwable th3) {
                            th = th3;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th4) {
                        th = th4;
                        String str2 = callingPackage;
                        int i22 = userId;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th5) {
                    th = th5;
                    String str3 = resolvedType;
                    boolean z2 = requireForeground;
                    String str22 = callingPackage;
                    int i222 = userId;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

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
                    if (!this.mRemote.transact(25, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().stopService(caller, service, resolvedType, userId);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int bindService(IApplicationThread caller, IBinder token, Intent service, String resolvedType, IServiceConnection connection, int flags, String callingPackage, int userId) throws RemoteException {
                Intent intent = service;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = null;
                    _data.writeStrongBinder(caller != null ? caller.asBinder() : null);
                    try {
                        _data.writeStrongBinder(token);
                        if (intent != null) {
                            _data.writeInt(1);
                            intent.writeToParcel(_data, 0);
                        } else {
                            _data.writeInt(0);
                        }
                    } catch (Throwable th) {
                        th = th;
                        String str = resolvedType;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeString(resolvedType);
                        if (connection != null) {
                            iBinder = connection.asBinder();
                        }
                        _data.writeStrongBinder(iBinder);
                        _data.writeInt(flags);
                        _data.writeString(callingPackage);
                        _data.writeInt(userId);
                        if (this.mRemote.transact(26, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                            _reply.readException();
                            int _result = _reply.readInt();
                            _reply.recycle();
                            _data.recycle();
                            return _result;
                        }
                        int bindService = Stub.getDefaultImpl().bindService(caller, token, service, resolvedType, connection, flags, callingPackage, userId);
                        _reply.recycle();
                        _data.recycle();
                        return bindService;
                    } catch (Throwable th2) {
                        th = th2;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    IBinder iBinder2 = token;
                    String str2 = resolvedType;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public int bindIsolatedService(IApplicationThread caller, IBinder token, Intent service, String resolvedType, IServiceConnection connection, int flags, String instanceName, String callingPackage, int userId) throws RemoteException {
                Intent intent = service;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = null;
                    _data.writeStrongBinder(caller != null ? caller.asBinder() : null);
                    try {
                        _data.writeStrongBinder(token);
                        if (intent != null) {
                            _data.writeInt(1);
                            intent.writeToParcel(_data, 0);
                        } else {
                            _data.writeInt(0);
                        }
                        _data.writeString(resolvedType);
                        if (connection != null) {
                            iBinder = connection.asBinder();
                        }
                        _data.writeStrongBinder(iBinder);
                        _data.writeInt(flags);
                        _data.writeString(instanceName);
                        _data.writeString(callingPackage);
                        _data.writeInt(userId);
                        if (this.mRemote.transact(27, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                            _reply.readException();
                            int _result = _reply.readInt();
                            _reply.recycle();
                            _data.recycle();
                            return _result;
                        }
                        int bindIsolatedService = Stub.getDefaultImpl().bindIsolatedService(caller, token, service, resolvedType, connection, flags, instanceName, callingPackage, userId);
                        _reply.recycle();
                        _data.recycle();
                        return bindIsolatedService;
                    } catch (Throwable th) {
                        th = th;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    IBinder iBinder2 = token;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public void updateServiceGroup(IServiceConnection connection, int group, int importance) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(connection != null ? connection.asBinder() : null);
                    _data.writeInt(group);
                    _data.writeInt(importance);
                    if (this.mRemote.transact(28, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().updateServiceGroup(connection, group, importance);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean unbindService(IServiceConnection connection) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(connection != null ? connection.asBinder() : null);
                    boolean z = false;
                    if (!this.mRemote.transact(29, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().unbindService(connection);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _status = z;
                    _reply.recycle();
                    _data.recycle();
                    return _status;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

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
                    if (this.mRemote.transact(30, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().publishService(token, intent, service);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setDebugApp(String packageName, boolean waitForDebugger, boolean persistent) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(waitForDebugger);
                    _data.writeInt(persistent);
                    if (this.mRemote.transact(31, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setDebugApp(packageName, waitForDebugger, persistent);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setAgentApp(String packageName, String agent) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeString(agent);
                    if (this.mRemote.transact(32, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setAgentApp(packageName, agent);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setAlwaysFinish(boolean enabled) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(enabled);
                    if (this.mRemote.transact(33, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setAlwaysFinish(enabled);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean startInstrumentation(ComponentName className, String profileFile, int flags, Bundle arguments, IInstrumentationWatcher watcher, IUiAutomationConnection connection, int userId, String abiOverride) throws RemoteException {
                ComponentName componentName = className;
                Bundle bundle = arguments;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (componentName != null) {
                        _data.writeInt(1);
                        componentName.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    try {
                        _data.writeString(profileFile);
                        try {
                            _data.writeInt(flags);
                            if (bundle != null) {
                                _data.writeInt(1);
                                bundle.writeToParcel(_data, 0);
                            } else {
                                _data.writeInt(0);
                            }
                            IBinder iBinder = null;
                            _data.writeStrongBinder(watcher != null ? watcher.asBinder() : null);
                            if (connection != null) {
                                iBinder = connection.asBinder();
                            }
                            _data.writeStrongBinder(iBinder);
                            _data.writeInt(userId);
                            _data.writeString(abiOverride);
                            if (this.mRemote.transact(34, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                _reply.readException();
                                if (_reply.readInt() == 0) {
                                    _result = false;
                                }
                                _reply.recycle();
                                _data.recycle();
                                return _result;
                            }
                            boolean startInstrumentation = Stub.getDefaultImpl().startInstrumentation(className, profileFile, flags, arguments, watcher, connection, userId, abiOverride);
                            _reply.recycle();
                            _data.recycle();
                            return startInstrumentation;
                        } catch (Throwable th) {
                            th = th;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        int i = flags;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    String str = profileFile;
                    int i2 = flags;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

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
                    if (this.mRemote.transact(35, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().addInstrumentationResults(target, results);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

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
                    if (this.mRemote.transact(36, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().finishInstrumentation(target, resultCode, results);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public Configuration getConfiguration() throws RemoteException {
                Configuration _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(37, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getConfiguration();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = Configuration.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    Configuration _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean updateConfiguration(Configuration values) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (values != null) {
                        _data.writeInt(1);
                        values.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(38, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().updateConfiguration(values);
                    }
                    _reply.readException();
                    if (_reply.readInt() == 0) {
                        _result = false;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean stopServiceToken(ComponentName className, IBinder token, int startId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (className != null) {
                        _data.writeInt(1);
                        className.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeStrongBinder(token);
                    _data.writeInt(startId);
                    if (!this.mRemote.transact(39, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().stopServiceToken(className, token, startId);
                    }
                    _reply.readException();
                    if (_reply.readInt() == 0) {
                        _result = false;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setProcessLimit(int max) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(max);
                    if (this.mRemote.transact(40, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setProcessLimit(max);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getProcessLimit() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(41, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getProcessLimit();
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int checkPermission(String permission, int pid, int uid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(permission);
                    _data.writeInt(pid);
                    _data.writeInt(uid);
                    if (!this.mRemote.transact(42, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().checkPermission(permission, pid, uid);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int checkUriPermission(Uri uri, int pid, int uid, int mode, int userId, IBinder callerToken) throws RemoteException {
                Uri uri2 = uri;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (uri2 != null) {
                        _data.writeInt(1);
                        uri2.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    try {
                        _data.writeInt(pid);
                        try {
                            _data.writeInt(uid);
                        } catch (Throwable th) {
                            th = th;
                            int i = mode;
                            int i2 = userId;
                            IBinder iBinder = callerToken;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                        try {
                            _data.writeInt(mode);
                            try {
                                _data.writeInt(userId);
                            } catch (Throwable th2) {
                                th = th2;
                                IBinder iBinder2 = callerToken;
                                _reply.recycle();
                                _data.recycle();
                                throw th;
                            }
                            try {
                                _data.writeStrongBinder(callerToken);
                                if (this.mRemote.transact(43, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                    _reply.readException();
                                    int _result = _reply.readInt();
                                    _reply.recycle();
                                    _data.recycle();
                                    return _result;
                                }
                                int checkUriPermission = Stub.getDefaultImpl().checkUriPermission(uri, pid, uid, mode, userId, callerToken);
                                _reply.recycle();
                                _data.recycle();
                                return checkUriPermission;
                            } catch (Throwable th3) {
                                th = th3;
                                _reply.recycle();
                                _data.recycle();
                                throw th;
                            }
                        } catch (Throwable th4) {
                            th = th4;
                            int i22 = userId;
                            IBinder iBinder22 = callerToken;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th5) {
                        th = th5;
                        int i3 = uid;
                        int i4 = mode;
                        int i222 = userId;
                        IBinder iBinder222 = callerToken;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th6) {
                    th = th6;
                    int i5 = pid;
                    int i32 = uid;
                    int i42 = mode;
                    int i2222 = userId;
                    IBinder iBinder2222 = callerToken;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

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
                    if (this.mRemote.transact(44, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().grantUriPermission(caller, targetPkg, uri, mode, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

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
                    if (this.mRemote.transact(45, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().revokeUriPermission(caller, targetPkg, uri, mode, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setActivityController(IActivityController watcher, boolean imAMonkey) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(watcher != null ? watcher.asBinder() : null);
                    _data.writeInt(imAMonkey);
                    if (this.mRemote.transact(46, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setActivityController(watcher, imAMonkey);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void showWaitingForDebugger(IApplicationThread who, boolean waiting) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(who != null ? who.asBinder() : null);
                    _data.writeInt(waiting);
                    if (this.mRemote.transact(47, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().showWaitingForDebugger(who, waiting);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void signalPersistentProcesses(int signal) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(signal);
                    if (this.mRemote.transact(48, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().signalPersistentProcesses(signal);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public ParceledListSlice getRecentTasks(int maxNum, int flags, int userId) throws RemoteException {
                ParceledListSlice _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(maxNum);
                    _data.writeInt(flags);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(49, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getRecentTasks(maxNum, flags, userId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = ParceledListSlice.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    ParceledListSlice _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void serviceDoneExecuting(IBinder token, int type, int startId, int res) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeInt(type);
                    _data.writeInt(startId);
                    _data.writeInt(res);
                    if (this.mRemote.transact(50, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().serviceDoneExecuting(token, type, startId, res);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public IIntentSender getIntentSender(int type, String packageName, IBinder token, String resultWho, int requestCode, Intent[] intents, String[] resolvedTypes, int flags, Bundle options, int userId) throws RemoteException {
                Bundle bundle = options;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeInt(type);
                        _data.writeString(packageName);
                        _data.writeStrongBinder(token);
                        _data.writeString(resultWho);
                        _data.writeInt(requestCode);
                        _data.writeTypedArray(intents, 0);
                        _data.writeStringArray(resolvedTypes);
                        _data.writeInt(flags);
                        if (bundle != null) {
                            _data.writeInt(1);
                            bundle.writeToParcel(_data, 0);
                        } else {
                            _data.writeInt(0);
                        }
                        _data.writeInt(userId);
                        if (this.mRemote.transact(51, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                            _reply.readException();
                            IIntentSender _result = IIntentSender.Stub.asInterface(_reply.readStrongBinder());
                            _reply.recycle();
                            _data.recycle();
                            return _result;
                        }
                        IIntentSender intentSender = Stub.getDefaultImpl().getIntentSender(type, packageName, token, resultWho, requestCode, intents, resolvedTypes, flags, options, userId);
                        _reply.recycle();
                        _data.recycle();
                        return intentSender;
                    } catch (Throwable th) {
                        th = th;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    int i = type;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public void cancelIntentSender(IIntentSender sender) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(sender != null ? sender.asBinder() : null);
                    if (this.mRemote.transact(52, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().cancelIntentSender(sender);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String getPackageForIntentSender(IIntentSender sender) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(sender != null ? sender.asBinder() : null);
                    if (!this.mRemote.transact(53, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPackageForIntentSender(sender);
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void registerIntentSenderCancelListener(IIntentSender sender, IResultReceiver receiver) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = null;
                    _data.writeStrongBinder(sender != null ? sender.asBinder() : null);
                    if (receiver != null) {
                        iBinder = receiver.asBinder();
                    }
                    _data.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(54, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().registerIntentSenderCancelListener(sender, receiver);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void unregisterIntentSenderCancelListener(IIntentSender sender, IResultReceiver receiver) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = null;
                    _data.writeStrongBinder(sender != null ? sender.asBinder() : null);
                    if (receiver != null) {
                        iBinder = receiver.asBinder();
                    }
                    _data.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(55, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().unregisterIntentSenderCancelListener(sender, receiver);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void enterSafeMode() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(56, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().enterSafeMode();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

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
                    if (this.mRemote.transact(57, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().noteWakeupAlarm(sender, workSource, sourceUid, sourcePkg, tag);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void removeContentProvider(IBinder connection, boolean stable) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(connection);
                    _data.writeInt(stable);
                    if (this.mRemote.transact(58, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().removeContentProvider(connection, stable);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void setRequestedOrientation(IBinder token, int requestedOrientation) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeInt(requestedOrientation);
                    if (this.mRemote.transact(59, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setRequestedOrientation(token, requestedOrientation);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

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
                    _data.writeInt(doRebind);
                    if (this.mRemote.transact(60, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().unbindFinished(token, service, doRebind);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setProcessImportant(IBinder token, int pid, boolean isForeground, String reason) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeInt(pid);
                    _data.writeInt(isForeground);
                    _data.writeString(reason);
                    if (this.mRemote.transact(61, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setProcessImportant(token, pid, isForeground, reason);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setServiceForeground(ComponentName className, IBinder token, int id, Notification notification, int flags, int foregroundServiceType) throws RemoteException {
                ComponentName componentName = className;
                Notification notification2 = notification;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (componentName != null) {
                        _data.writeInt(1);
                        componentName.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    try {
                        _data.writeStrongBinder(token);
                        try {
                            _data.writeInt(id);
                            if (notification2 != null) {
                                _data.writeInt(1);
                                notification2.writeToParcel(_data, 0);
                            } else {
                                _data.writeInt(0);
                            }
                        } catch (Throwable th) {
                            th = th;
                            int i = flags;
                            int i2 = foregroundServiceType;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        int i3 = id;
                        int i4 = flags;
                        int i22 = foregroundServiceType;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(flags);
                        try {
                            _data.writeInt(foregroundServiceType);
                            if (this.mRemote.transact(62, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                _reply.readException();
                                _reply.recycle();
                                _data.recycle();
                                return;
                            }
                            Stub.getDefaultImpl().setServiceForeground(className, token, id, notification, flags, foregroundServiceType);
                            _reply.recycle();
                            _data.recycle();
                        } catch (Throwable th3) {
                            th = th3;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th4) {
                        th = th4;
                        int i222 = foregroundServiceType;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th5) {
                    th = th5;
                    IBinder iBinder = token;
                    int i32 = id;
                    int i42 = flags;
                    int i2222 = foregroundServiceType;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

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
                    if (!this.mRemote.transact(63, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getForegroundServiceType(className, token);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean moveActivityTaskToBack(IBinder token, boolean nonRoot) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeInt(nonRoot);
                    boolean z = false;
                    if (!this.mRemote.transact(64, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().moveActivityTaskToBack(token, nonRoot);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _status = z;
                    _reply.recycle();
                    _data.recycle();
                    return _status;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void getMemoryInfo(ActivityManager.MemoryInfo outInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(65, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        if (_reply.readInt() != 0) {
                            outInfo.readFromParcel(_reply);
                        }
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().getMemoryInfo(outInfo);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public List<ActivityManager.ProcessErrorStateInfo> getProcessesInErrorState() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(66, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getProcessesInErrorState();
                    }
                    _reply.readException();
                    List<ActivityManager.ProcessErrorStateInfo> _result = _reply.createTypedArrayList(ActivityManager.ProcessErrorStateInfo.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean clearApplicationUserData(String packageName, boolean keepState, IPackageDataObserver observer, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(keepState);
                    _data.writeStrongBinder(observer != null ? observer.asBinder() : null);
                    _data.writeInt(userId);
                    boolean z = false;
                    if (!this.mRemote.transact(67, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().clearApplicationUserData(packageName, keepState, observer, userId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _status = z;
                    _reply.recycle();
                    _data.recycle();
                    return _status;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void forceStopPackage(String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(68, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().forceStopPackage(packageName, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean killPids(int[] pids, String reason, boolean secure) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeIntArray(pids);
                    _data.writeString(reason);
                    _data.writeInt(secure);
                    boolean z = false;
                    if (!this.mRemote.transact(69, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().killPids(pids, reason, secure);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _status = z;
                    _reply.recycle();
                    _data.recycle();
                    return _status;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public List<ActivityManager.RunningServiceInfo> getServices(int maxNum, int flags) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(maxNum);
                    _data.writeInt(flags);
                    if (!this.mRemote.transact(70, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getServices(maxNum, flags);
                    }
                    _reply.readException();
                    List<ActivityManager.RunningServiceInfo> _result = _reply.createTypedArrayList(ActivityManager.RunningServiceInfo.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public List<ActivityManager.RunningAppProcessInfo> getRunningAppProcesses() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(71, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getRunningAppProcesses();
                    }
                    _reply.readException();
                    List<ActivityManager.RunningAppProcessInfo> _result = _reply.createTypedArrayList(ActivityManager.RunningAppProcessInfo.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

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
                    if (!this.mRemote.transact(72, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().peekService(service, resolvedType, callingPackage);
                    }
                    _reply.readException();
                    IBinder _result = _reply.readStrongBinder();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean profileControl(String process, int userId, boolean start, ProfilerInfo profilerInfo, int profileType) throws RemoteException {
                boolean _result;
                ProfilerInfo profilerInfo2 = profilerInfo;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeString(process);
                        try {
                            _data.writeInt(userId);
                        } catch (Throwable th) {
                            th = th;
                            boolean z = start;
                            int i = profileType;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                        try {
                            _data.writeInt(start ? 1 : 0);
                            _result = true;
                            if (profilerInfo2 != null) {
                                _data.writeInt(1);
                                profilerInfo2.writeToParcel(_data, 0);
                            } else {
                                _data.writeInt(0);
                            }
                            try {
                                _data.writeInt(profileType);
                            } catch (Throwable th2) {
                                th = th2;
                                _reply.recycle();
                                _data.recycle();
                                throw th;
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            int i2 = profileType;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                        try {
                            if (this.mRemote.transact(73, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                _reply.readException();
                                if (_reply.readInt() == 0) {
                                    _result = false;
                                }
                                _reply.recycle();
                                _data.recycle();
                                return _result;
                            }
                            boolean profileControl = Stub.getDefaultImpl().profileControl(process, userId, start, profilerInfo, profileType);
                            _reply.recycle();
                            _data.recycle();
                            return profileControl;
                        } catch (Throwable th4) {
                            th = th4;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th5) {
                        th = th5;
                        int i3 = userId;
                        boolean z2 = start;
                        int i22 = profileType;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th6) {
                    th = th6;
                    String str = process;
                    int i32 = userId;
                    boolean z22 = start;
                    int i222 = profileType;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public boolean shutdown(int timeout) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(timeout);
                    boolean z = false;
                    if (!this.mRemote.transact(74, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().shutdown(timeout);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _status = z;
                    _reply.recycle();
                    _data.recycle();
                    return _status;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void stopAppSwitches() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(75, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().stopAppSwitches();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void resumeAppSwitches() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(76, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().resumeAppSwitches();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean bindBackupAgent(String packageName, int backupRestoreMode, int targetUserId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(backupRestoreMode);
                    _data.writeInt(targetUserId);
                    boolean z = false;
                    if (!this.mRemote.transact(77, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().bindBackupAgent(packageName, backupRestoreMode, targetUserId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _status = z;
                    _reply.recycle();
                    _data.recycle();
                    return _status;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void backupAgentCreated(String packageName, IBinder agent, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeStrongBinder(agent);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(78, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().backupAgentCreated(packageName, agent, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

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
                    if (this.mRemote.transact(79, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().unbindBackupAgent(appInfo);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getUidForIntentSender(IIntentSender sender) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(sender != null ? sender.asBinder() : null);
                    if (!this.mRemote.transact(80, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getUidForIntentSender(sender);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int handleIncomingUser(int callingPid, int callingUid, int userId, boolean allowAll, boolean requireFull, String name, String callerPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeInt(callingPid);
                        try {
                            _data.writeInt(callingUid);
                        } catch (Throwable th) {
                            th = th;
                            int i = userId;
                            boolean z = allowAll;
                            boolean z2 = requireFull;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                        try {
                            _data.writeInt(userId);
                            try {
                                _data.writeInt(allowAll ? 1 : 0);
                            } catch (Throwable th2) {
                                th = th2;
                                boolean z22 = requireFull;
                                _reply.recycle();
                                _data.recycle();
                                throw th;
                            }
                            try {
                                _data.writeInt(requireFull ? 1 : 0);
                                _data.writeString(name);
                                _data.writeString(callerPackage);
                                if (this.mRemote.transact(81, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                    _reply.readException();
                                    int _result = _reply.readInt();
                                    _reply.recycle();
                                    _data.recycle();
                                    return _result;
                                }
                                int handleIncomingUser = Stub.getDefaultImpl().handleIncomingUser(callingPid, callingUid, userId, allowAll, requireFull, name, callerPackage);
                                _reply.recycle();
                                _data.recycle();
                                return handleIncomingUser;
                            } catch (Throwable th3) {
                                th = th3;
                                _reply.recycle();
                                _data.recycle();
                                throw th;
                            }
                        } catch (Throwable th4) {
                            th = th4;
                            boolean z3 = allowAll;
                            boolean z222 = requireFull;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th5) {
                        th = th5;
                        int i2 = callingUid;
                        int i3 = userId;
                        boolean z32 = allowAll;
                        boolean z2222 = requireFull;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th6) {
                    th = th6;
                    int i4 = callingPid;
                    int i22 = callingUid;
                    int i32 = userId;
                    boolean z322 = allowAll;
                    boolean z22222 = requireFull;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public void addPackageDependency(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    if (this.mRemote.transact(82, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().addPackageDependency(packageName);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void killApplication(String pkg, int appId, int userId, String reason) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(pkg);
                    _data.writeInt(appId);
                    _data.writeInt(userId);
                    _data.writeString(reason);
                    if (this.mRemote.transact(83, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().killApplication(pkg, appId, userId, reason);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void closeSystemDialogs(String reason) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(reason);
                    if (this.mRemote.transact(84, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().closeSystemDialogs(reason);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public Debug.MemoryInfo[] getProcessMemoryInfo(int[] pids) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeIntArray(pids);
                    if (!this.mRemote.transact(85, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getProcessMemoryInfo(pids);
                    }
                    _reply.readException();
                    Debug.MemoryInfo[] _result = (Debug.MemoryInfo[]) _reply.createTypedArray(Debug.MemoryInfo.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void killApplicationProcess(String processName, int uid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(processName);
                    _data.writeInt(uid);
                    if (this.mRemote.transact(86, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().killApplicationProcess(processName, uid);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean handleApplicationWtf(IBinder app, String tag, boolean system, ApplicationErrorReport.ParcelableCrashInfo crashInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(app);
                    _data.writeString(tag);
                    _data.writeInt(system);
                    boolean _result = true;
                    if (crashInfo != null) {
                        _data.writeInt(1);
                        crashInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(87, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().handleApplicationWtf(app, tag, system, crashInfo);
                    }
                    _reply.readException();
                    if (_reply.readInt() == 0) {
                        _result = false;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void killBackgroundProcesses(String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(88, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().killBackgroundProcesses(packageName, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isUserAMonkey() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(89, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isUserAMonkey();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _status = z;
                    _reply.recycle();
                    _data.recycle();
                    return _status;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public List<ApplicationInfo> getRunningExternalApplications() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(90, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getRunningExternalApplications();
                    }
                    _reply.readException();
                    List<ApplicationInfo> _result = _reply.createTypedArrayList(ApplicationInfo.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void finishHeavyWeightApp() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(91, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().finishHeavyWeightApp();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

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
                    if (this.mRemote.transact(92, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().handleApplicationStrictModeViolation(app, penaltyMask, crashInfo);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isTopActivityImmersive() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(93, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isTopActivityImmersive();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _status = z;
                    _reply.recycle();
                    _data.recycle();
                    return _status;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

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
                    if (this.mRemote.transact(94, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().crashApplication(uid, initialPid, packageName, userId, message);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

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
                    if (!this.mRemote.transact(95, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getProviderMimeType(uri, userId);
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean dumpHeap(String process, int userId, boolean managed, boolean mallocInfo, boolean runGc, String path, ParcelFileDescriptor fd, RemoteCallback finishCallback) throws RemoteException {
                ParcelFileDescriptor parcelFileDescriptor = fd;
                RemoteCallback remoteCallback = finishCallback;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeString(process);
                        try {
                            _data.writeInt(userId);
                            _data.writeInt(managed ? 1 : 0);
                            _data.writeInt(mallocInfo ? 1 : 0);
                            _data.writeInt(runGc ? 1 : 0);
                            _data.writeString(path);
                            boolean _result = true;
                            if (parcelFileDescriptor != null) {
                                _data.writeInt(1);
                                parcelFileDescriptor.writeToParcel(_data, 0);
                            } else {
                                _data.writeInt(0);
                            }
                            if (remoteCallback != null) {
                                _data.writeInt(1);
                                remoteCallback.writeToParcel(_data, 0);
                            } else {
                                _data.writeInt(0);
                            }
                            if (this.mRemote.transact(96, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                _reply.readException();
                                if (_reply.readInt() == 0) {
                                    _result = false;
                                }
                                _reply.recycle();
                                _data.recycle();
                                return _result;
                            }
                            boolean dumpHeap = Stub.getDefaultImpl().dumpHeap(process, userId, managed, mallocInfo, runGc, path, fd, finishCallback);
                            _reply.recycle();
                            _data.recycle();
                            return dumpHeap;
                        } catch (Throwable th) {
                            th = th;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        int i = userId;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    String str = process;
                    int i2 = userId;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public boolean isUserRunning(int userid, int flags) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userid);
                    _data.writeInt(flags);
                    boolean z = false;
                    if (!this.mRemote.transact(97, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isUserRunning(userid, flags);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _status = z;
                    _reply.recycle();
                    _data.recycle();
                    return _status;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setPackageScreenCompatMode(String packageName, int mode) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(mode);
                    if (this.mRemote.transact(98, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setPackageScreenCompatMode(packageName, mode);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean switchUser(int userid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userid);
                    boolean z = false;
                    if (!this.mRemote.transact(99, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().switchUser(userid);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _status = z;
                    _reply.recycle();
                    _data.recycle();
                    return _status;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean removeTask(int taskId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(taskId);
                    boolean z = false;
                    if (!this.mRemote.transact(100, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().removeTask(taskId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _status = z;
                    _reply.recycle();
                    _data.recycle();
                    return _status;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void registerProcessObserver(IProcessObserver observer) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(observer != null ? observer.asBinder() : null);
                    if (this.mRemote.transact(101, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().registerProcessObserver(observer);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void unregisterProcessObserver(IProcessObserver observer) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(observer != null ? observer.asBinder() : null);
                    if (this.mRemote.transact(102, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().unregisterProcessObserver(observer);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isIntentSenderTargetedToPackage(IIntentSender sender) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(sender != null ? sender.asBinder() : null);
                    boolean z = false;
                    if (!this.mRemote.transact(103, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isIntentSenderTargetedToPackage(sender);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _status = z;
                    _reply.recycle();
                    _data.recycle();
                    return _status;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

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
                    if (this.mRemote.transact(104, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().updatePersistentConfiguration(values);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public long[] getProcessPss(int[] pids) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeIntArray(pids);
                    if (!this.mRemote.transact(105, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getProcessPss(pids);
                    }
                    _reply.readException();
                    long[] _result = _reply.createLongArray();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

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
                    _data.writeInt(always);
                    if (this.mRemote.transact(106, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().showBootMessage(msg, always);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void killAllBackgroundProcesses() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(107, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().killAllBackgroundProcesses();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

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
                    if (!this.mRemote.transact(108, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getContentProviderExternal(name, userId, token, tag);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = ContentProviderHolder.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    ContentProviderHolder _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void removeContentProviderExternal(String name, IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    _data.writeStrongBinder(token);
                    if (this.mRemote.transact(109, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().removeContentProviderExternal(name, token);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void removeContentProviderExternalAsUser(String name, IBinder token, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    _data.writeStrongBinder(token);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(110, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().removeContentProviderExternalAsUser(name, token, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void getMyMemoryState(ActivityManager.RunningAppProcessInfo outInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(111, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        if (_reply.readInt() != 0) {
                            outInfo.readFromParcel(_reply);
                        }
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().getMyMemoryState(outInfo);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean killProcessesBelowForeground(String reason) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(reason);
                    boolean z = false;
                    if (!this.mRemote.transact(112, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().killProcessesBelowForeground(reason);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _status = z;
                    _reply.recycle();
                    _data.recycle();
                    return _status;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public UserInfo getCurrentUser() throws RemoteException {
                UserInfo _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(113, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getCurrentUser();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = UserInfo.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    UserInfo _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getLaunchedFromUid(IBinder activityToken) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(activityToken);
                    if (!this.mRemote.transact(114, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getLaunchedFromUid(activityToken);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void unstableProviderDied(IBinder connection) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(connection);
                    if (this.mRemote.transact(115, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().unstableProviderDied(connection);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isIntentSenderAnActivity(IIntentSender sender) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(sender != null ? sender.asBinder() : null);
                    boolean z = false;
                    if (!this.mRemote.transact(116, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isIntentSenderAnActivity(sender);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _status = z;
                    _reply.recycle();
                    _data.recycle();
                    return _status;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isIntentSenderAForegroundService(IIntentSender sender) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(sender != null ? sender.asBinder() : null);
                    boolean z = false;
                    if (!this.mRemote.transact(117, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isIntentSenderAForegroundService(sender);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _status = z;
                    _reply.recycle();
                    _data.recycle();
                    return _status;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isIntentSenderABroadcast(IIntentSender sender) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(sender != null ? sender.asBinder() : null);
                    boolean z = false;
                    if (!this.mRemote.transact(118, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isIntentSenderABroadcast(sender);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _status = z;
                    _reply.recycle();
                    _data.recycle();
                    return _status;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int startActivityAsUser(IApplicationThread caller, String callingPackage, Intent intent, String resolvedType, IBinder resultTo, String resultWho, int requestCode, int flags, ProfilerInfo profilerInfo, Bundle options, int userId) throws RemoteException {
                Parcel _data;
                Parcel _reply;
                IBinder iBinder;
                Intent intent2 = intent;
                ProfilerInfo profilerInfo2 = profilerInfo;
                Bundle bundle = options;
                Parcel _data2 = Parcel.obtain();
                Parcel _reply2 = Parcel.obtain();
                try {
                    _data2.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (caller != null) {
                        try {
                            iBinder = caller.asBinder();
                        } catch (Throwable th) {
                            th = th;
                            _reply = _reply2;
                            _data = _data2;
                        }
                    } else {
                        iBinder = null;
                    }
                    _data2.writeStrongBinder(iBinder);
                    _data2.writeString(callingPackage);
                    if (intent2 != null) {
                        _data2.writeInt(1);
                        intent2.writeToParcel(_data2, 0);
                    } else {
                        _data2.writeInt(0);
                    }
                    _data2.writeString(resolvedType);
                    _data2.writeStrongBinder(resultTo);
                    _data2.writeString(resultWho);
                    _data2.writeInt(requestCode);
                    _data2.writeInt(flags);
                    if (profilerInfo2 != null) {
                        _data2.writeInt(1);
                        profilerInfo2.writeToParcel(_data2, 0);
                    } else {
                        _data2.writeInt(0);
                    }
                    if (bundle != null) {
                        _data2.writeInt(1);
                        bundle.writeToParcel(_data2, 0);
                    } else {
                        _data2.writeInt(0);
                    }
                    _data2.writeInt(userId);
                    if (this.mRemote.transact(119, _data2, _reply2, 0) || Stub.getDefaultImpl() == null) {
                        _reply = _reply2;
                        _data = _data2;
                        _reply.readException();
                        int _result = _reply.readInt();
                        _reply.recycle();
                        _data.recycle();
                        return _result;
                    }
                    _reply = _reply2;
                    _data = _data2;
                    try {
                        int startActivityAsUser = Stub.getDefaultImpl().startActivityAsUser(caller, callingPackage, intent, resolvedType, resultTo, resultWho, requestCode, flags, profilerInfo, options, userId);
                        _reply.recycle();
                        _data.recycle();
                        return startActivityAsUser;
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
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public int stopUser(int userid, boolean force, IStopUserCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userid);
                    _data.writeInt(force);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (!this.mRemote.transact(120, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().stopUser(userid, force, callback);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void registerUserSwitchObserver(IUserSwitchObserver observer, String name) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(observer != null ? observer.asBinder() : null);
                    _data.writeString(name);
                    if (this.mRemote.transact(121, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().registerUserSwitchObserver(observer, name);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void unregisterUserSwitchObserver(IUserSwitchObserver observer) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(observer != null ? observer.asBinder() : null);
                    if (this.mRemote.transact(122, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().unregisterUserSwitchObserver(observer);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int[] getRunningUserIds() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(123, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getRunningUserIds();
                    }
                    _reply.readException();
                    int[] _result = _reply.createIntArray();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void requestSystemServerHeapDump() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(124, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().requestSystemServerHeapDump();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void requestBugReport(int bugreportType) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(bugreportType);
                    if (this.mRemote.transact(125, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().requestBugReport(bugreportType);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void requestTelephonyBugReport(String shareTitle, String shareDescription) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(shareTitle);
                    _data.writeString(shareDescription);
                    if (this.mRemote.transact(126, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().requestTelephonyBugReport(shareTitle, shareDescription);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void requestWifiBugReport(String shareTitle, String shareDescription) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(shareTitle);
                    _data.writeString(shareDescription);
                    if (this.mRemote.transact(127, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().requestWifiBugReport(shareTitle, shareDescription);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public Intent getIntentForIntentSender(IIntentSender sender) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    Intent _result = null;
                    _data.writeStrongBinder(sender != null ? sender.asBinder() : null);
                    if (!this.mRemote.transact(128, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getIntentForIntentSender(sender);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = Intent.CREATOR.createFromParcel(_reply);
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String getLaunchedFromPackage(IBinder activityToken) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(activityToken);
                    if (!this.mRemote.transact(129, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getLaunchedFromPackage(activityToken);
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void killUid(int appId, int userId, String reason) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(appId);
                    _data.writeInt(userId);
                    _data.writeString(reason);
                    if (this.mRemote.transact(130, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().killUid(appId, userId, reason);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setUserIsMonkey(boolean monkey) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(monkey);
                    if (this.mRemote.transact(131, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setUserIsMonkey(monkey);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void hang(IBinder who, boolean allowRestart) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(who);
                    _data.writeInt(allowRestart);
                    if (this.mRemote.transact(132, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().hang(who, allowRestart);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public List<ActivityManager.StackInfo> getAllStackInfos() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(133, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAllStackInfos();
                    }
                    _reply.readException();
                    List<ActivityManager.StackInfo> _result = _reply.createTypedArrayList(ActivityManager.StackInfo.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void moveTaskToStack(int taskId, int stackId, boolean toTop) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(taskId);
                    _data.writeInt(stackId);
                    _data.writeInt(toTop);
                    if (this.mRemote.transact(134, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().moveTaskToStack(taskId, stackId, toTop);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void resizeStack(int stackId, Rect bounds, boolean allowResizeInDockedMode, boolean preserveWindows, boolean animate, int animationDuration) throws RemoteException {
                Rect rect = bounds;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeInt(stackId);
                        if (rect != null) {
                            _data.writeInt(1);
                            rect.writeToParcel(_data, 0);
                        } else {
                            _data.writeInt(0);
                        }
                        try {
                            _data.writeInt(allowResizeInDockedMode ? 1 : 0);
                        } catch (Throwable th) {
                            th = th;
                            boolean z = preserveWindows;
                            boolean z2 = animate;
                            int i = animationDuration;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        boolean z3 = allowResizeInDockedMode;
                        boolean z4 = preserveWindows;
                        boolean z22 = animate;
                        int i2 = animationDuration;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(preserveWindows ? 1 : 0);
                        try {
                            _data.writeInt(animate ? 1 : 0);
                        } catch (Throwable th3) {
                            th = th3;
                            int i22 = animationDuration;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                        try {
                            _data.writeInt(animationDuration);
                            if (this.mRemote.transact(135, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                _reply.readException();
                                _reply.recycle();
                                _data.recycle();
                                return;
                            }
                            Stub.getDefaultImpl().resizeStack(stackId, bounds, allowResizeInDockedMode, preserveWindows, animate, animationDuration);
                            _reply.recycle();
                            _data.recycle();
                        } catch (Throwable th4) {
                            th = th4;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th5) {
                        th = th5;
                        boolean z222 = animate;
                        int i222 = animationDuration;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th6) {
                    th = th6;
                    int i3 = stackId;
                    boolean z32 = allowResizeInDockedMode;
                    boolean z42 = preserveWindows;
                    boolean z2222 = animate;
                    int i2222 = animationDuration;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public void setFocusedStack(int stackId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(stackId);
                    if (this.mRemote.transact(136, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setFocusedStack(stackId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public ActivityManager.StackInfo getFocusedStackInfo() throws RemoteException {
                ActivityManager.StackInfo _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(137, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getFocusedStackInfo();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = ActivityManager.StackInfo.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    ActivityManager.StackInfo _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void restart() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(138, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().restart();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void performIdleMaintenance() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(139, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().performIdleMaintenance();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void appNotRespondingViaProvider(IBinder connection) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(connection);
                    if (this.mRemote.transact(140, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().appNotRespondingViaProvider(connection);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public Rect getTaskBounds(int taskId) throws RemoteException {
                Rect _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(taskId);
                    if (!this.mRemote.transact(141, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getTaskBounds(taskId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = Rect.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    Rect _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean setProcessMemoryTrimLevel(String process, int uid, int level) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(process);
                    _data.writeInt(uid);
                    _data.writeInt(level);
                    boolean z = false;
                    if (!this.mRemote.transact(142, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setProcessMemoryTrimLevel(process, uid, level);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _status = z;
                    _reply.recycle();
                    _data.recycle();
                    return _status;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String getTagForIntentSender(IIntentSender sender, String prefix) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(sender != null ? sender.asBinder() : null);
                    _data.writeString(prefix);
                    if (!this.mRemote.transact(143, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getTagForIntentSender(sender, prefix);
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean startUserInBackground(int userid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userid);
                    boolean z = false;
                    if (!this.mRemote.transact(144, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().startUserInBackground(userid);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _status = z;
                    _reply.recycle();
                    _data.recycle();
                    return _status;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isInLockTaskMode() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(145, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isInLockTaskMode();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _status = z;
                    _reply.recycle();
                    _data.recycle();
                    return _status;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

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
                    IBinder iBinder = null;
                    _data.writeStrongBinder(assistDataReceiver != null ? assistDataReceiver.asBinder() : null);
                    if (recentsAnimationRunner != null) {
                        iBinder = recentsAnimationRunner.asBinder();
                    }
                    _data.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(146, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().startRecentsActivity(intent, assistDataReceiver, recentsAnimationRunner);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void cancelRecentsAnimation(boolean restoreHomeStackPosition) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(restoreHomeStackPosition);
                    if (this.mRemote.transact(147, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().cancelRecentsAnimation(restoreHomeStackPosition);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

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
                    if (!this.mRemote.transact(148, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().startActivityFromRecents(taskId, options);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void startSystemLockTaskMode(int taskId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(taskId);
                    if (this.mRemote.transact(149, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().startSystemLockTaskMode(taskId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isTopOfTask(IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    boolean z = false;
                    if (!this.mRemote.transact(150, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isTopOfTask(token);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _status = z;
                    _reply.recycle();
                    _data.recycle();
                    return _status;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void bootAnimationComplete() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(151, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().bootAnimationComplete();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int checkPermissionWithToken(String permission, int pid, int uid, IBinder callerToken) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(permission);
                    _data.writeInt(pid);
                    _data.writeInt(uid);
                    _data.writeStrongBinder(callerToken);
                    if (!this.mRemote.transact(152, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().checkPermissionWithToken(permission, pid, uid, callerToken);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void registerTaskStackListener(ITaskStackListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    if (this.mRemote.transact(153, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().registerTaskStackListener(listener);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void unregisterTaskStackListener(ITaskStackListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    if (this.mRemote.transact(154, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().unregisterTaskStackListener(listener);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void notifyCleartextNetwork(int uid, byte[] firstPacket) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeByteArray(firstPacket);
                    if (this.mRemote.transact(155, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().notifyCleartextNetwork(uid, firstPacket);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setTaskResizeable(int taskId, int resizeableMode) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(taskId);
                    _data.writeInt(resizeableMode);
                    if (this.mRemote.transact(156, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setTaskResizeable(taskId, resizeableMode);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

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
                    if (this.mRemote.transact(157, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().resizeTask(taskId, bounds, resizeMode);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getLockTaskModeState() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(158, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getLockTaskModeState();
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setDumpHeapDebugLimit(String processName, int uid, long maxMemSize, String reportPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(processName);
                    _data.writeInt(uid);
                    _data.writeLong(maxMemSize);
                    _data.writeString(reportPackage);
                    if (this.mRemote.transact(159, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setDumpHeapDebugLimit(processName, uid, maxMemSize, reportPackage);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void dumpHeapFinished(String path) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(path);
                    if (this.mRemote.transact(160, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().dumpHeapFinished(path);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void updateLockTaskPackages(int userId, String[] packages) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeStringArray(packages);
                    if (this.mRemote.transact(161, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().updateLockTaskPackages(userId, packages);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

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
                    if (this.mRemote.transact(162, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().noteAlarmStart(sender, workSource, sourceUid, tag);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

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
                    if (this.mRemote.transact(163, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().noteAlarmFinish(sender, workSource, sourceUid, tag);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getPackageProcessState(String packageName, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(164, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPackageProcessState(packageName, callingPackage);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void updateDeviceOwner(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    if (this.mRemote.transact(165, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().updateDeviceOwner(packageName);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean startBinderTracking() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(166, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().startBinderTracking();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _status = z;
                    _reply.recycle();
                    _data.recycle();
                    return _status;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean stopBinderTrackingAndDump(ParcelFileDescriptor fd) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (fd != null) {
                        _data.writeInt(1);
                        fd.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(167, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().stopBinderTrackingAndDump(fd);
                    }
                    _reply.readException();
                    if (_reply.readInt() == 0) {
                        _result = false;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void positionTaskInStack(int taskId, int stackId, int position) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(taskId);
                    _data.writeInt(stackId);
                    _data.writeInt(position);
                    if (this.mRemote.transact(168, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().positionTaskInStack(taskId, stackId, position);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void suppressResizeConfigChanges(boolean suppress) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(suppress);
                    if (this.mRemote.transact(169, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().suppressResizeConfigChanges(suppress);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean moveTopActivityToPinnedStack(int stackId, Rect bounds) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(stackId);
                    boolean _result = true;
                    if (bounds != null) {
                        _data.writeInt(1);
                        bounds.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(170, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().moveTopActivityToPinnedStack(stackId, bounds);
                    }
                    _reply.readException();
                    if (_reply.readInt() == 0) {
                        _result = false;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isAppStartModeDisabled(int uid, String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeString(packageName);
                    boolean z = false;
                    if (!this.mRemote.transact(171, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isAppStartModeDisabled(uid, packageName);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _status = z;
                    _reply.recycle();
                    _data.recycle();
                    return _status;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean unlockUser(int userid, byte[] token, byte[] secret, IProgressListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userid);
                    _data.writeByteArray(token);
                    _data.writeByteArray(secret);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    boolean z = false;
                    if (!this.mRemote.transact(172, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().unlockUser(userid, token, secret, listener);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _status = z;
                    _reply.recycle();
                    _data.recycle();
                    return _status;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void killPackageDependents(String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(173, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().killPackageDependents(packageName, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

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
                    if (this.mRemote.transact(174, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().resizeDockedStack(dockedBounds, tempDockedTaskBounds, tempDockedTaskInsetBounds, tempOtherTaskBounds, tempOtherTaskInsetBounds);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void removeStack(int stackId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(stackId);
                    if (this.mRemote.transact(175, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().removeStack(stackId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void makePackageIdle(String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(176, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().makePackageIdle(packageName, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getMemoryTrimLevel() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(177, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getMemoryTrimLevel();
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isVrModePackageEnabled(ComponentName packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (packageName != null) {
                        _data.writeInt(1);
                        packageName.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(178, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isVrModePackageEnabled(packageName);
                    }
                    _reply.readException();
                    if (_reply.readInt() == 0) {
                        _result = false;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void notifyLockedProfile(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(179, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().notifyLockedProfile(userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

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
                    if (this.mRemote.transact(180, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().startConfirmDeviceCredentialIntent(intent, options);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void sendIdleJobTrigger() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(181, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().sendIdleJobTrigger();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int sendIntentSender(IIntentSender target, IBinder whitelistToken, int code, Intent intent, String resolvedType, IIntentReceiver finishedReceiver, String requiredPermission, Bundle options) throws RemoteException {
                Intent intent2 = intent;
                Bundle bundle = options;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = null;
                    _data.writeStrongBinder(target != null ? target.asBinder() : null);
                    try {
                        _data.writeStrongBinder(whitelistToken);
                        _data.writeInt(code);
                        if (intent2 != null) {
                            _data.writeInt(1);
                            intent2.writeToParcel(_data, 0);
                        } else {
                            _data.writeInt(0);
                        }
                        _data.writeString(resolvedType);
                        if (finishedReceiver != null) {
                            iBinder = finishedReceiver.asBinder();
                        }
                        _data.writeStrongBinder(iBinder);
                        _data.writeString(requiredPermission);
                        if (bundle != null) {
                            _data.writeInt(1);
                            bundle.writeToParcel(_data, 0);
                        } else {
                            _data.writeInt(0);
                        }
                        if (this.mRemote.transact(182, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                            _reply.readException();
                            int _result = _reply.readInt();
                            _reply.recycle();
                            _data.recycle();
                            return _result;
                        }
                        int sendIntentSender = Stub.getDefaultImpl().sendIntentSender(target, whitelistToken, code, intent, resolvedType, finishedReceiver, requiredPermission, options);
                        _reply.recycle();
                        _data.recycle();
                        return sendIntentSender;
                    } catch (Throwable th) {
                        th = th;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    IBinder iBinder2 = whitelistToken;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public boolean isBackgroundRestricted(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    boolean z = false;
                    if (!this.mRemote.transact(183, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isBackgroundRestricted(packageName);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _status = z;
                    _reply.recycle();
                    _data.recycle();
                    return _status;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setRenderThread(int tid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(tid);
                    if (this.mRemote.transact(184, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setRenderThread(tid);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setHasTopUi(boolean hasTopUi) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(hasTopUi);
                    if (this.mRemote.transact(185, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setHasTopUi(hasTopUi);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int restartUserInBackground(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(186, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().restartUserInBackground(userId);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void cancelTaskWindowTransition(int taskId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(taskId);
                    if (this.mRemote.transact(187, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().cancelTaskWindowTransition(taskId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public ActivityManager.TaskSnapshot getTaskSnapshot(int taskId, boolean reducedResolution) throws RemoteException {
                ActivityManager.TaskSnapshot _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(taskId);
                    _data.writeInt(reducedResolution);
                    if (!this.mRemote.transact(188, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getTaskSnapshot(taskId, reducedResolution);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = ActivityManager.TaskSnapshot.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    ActivityManager.TaskSnapshot _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void scheduleApplicationInfoChanged(List<String> packageNames, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStringList(packageNames);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(189, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().scheduleApplicationInfoChanged(packageNames, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setPersistentVrThread(int tid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(tid);
                    if (this.mRemote.transact(190, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setPersistentVrThread(tid);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void waitForNetworkStateUpdate(long procStateSeq) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(procStateSeq);
                    if (this.mRemote.transact(191, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().waitForNetworkStateUpdate(procStateSeq);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void backgroundWhitelistUid(int uid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    if (this.mRemote.transact(192, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().backgroundWhitelistUid(uid);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean startUserInBackgroundWithListener(int userid, IProgressListener unlockProgressListener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userid);
                    _data.writeStrongBinder(unlockProgressListener != null ? unlockProgressListener.asBinder() : null);
                    boolean z = false;
                    if (!this.mRemote.transact(193, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().startUserInBackgroundWithListener(userid, unlockProgressListener);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _status = z;
                    _reply.recycle();
                    _data.recycle();
                    return _status;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void startDelegateShellPermissionIdentity(int uid, String[] permissions) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeStringArray(permissions);
                    if (this.mRemote.transact(194, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().startDelegateShellPermissionIdentity(uid, permissions);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void stopDelegateShellPermissionIdentity() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(195, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().stopDelegateShellPermissionIdentity();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public ParcelFileDescriptor getLifeMonitor() throws RemoteException {
                ParcelFileDescriptor _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(196, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getLifeMonitor();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = ParcelFileDescriptor.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    ParcelFileDescriptor _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean startUserInForegroundWithListener(int userid, IProgressListener unlockProgressListener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userid);
                    _data.writeStrongBinder(unlockProgressListener != null ? unlockProgressListener.asBinder() : null);
                    boolean z = false;
                    if (!this.mRemote.transact(197, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().startUserInForegroundWithListener(userid, unlockProgressListener);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _status = z;
                    _reply.recycle();
                    _data.recycle();
                    return _status;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IActivityManager impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IActivityManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
