package android.app;

import android.app.ActivityManager;
import android.app.assist.AssistContent;
import android.app.assist.AssistStructure;
import android.content.ComponentName;
import android.content.IIntentSender;
import android.content.Intent;
import android.content.pm.ConfigurationInfo;
import android.content.pm.ParceledListSlice;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.PersistableBundle;
import android.os.RemoteException;
import android.service.voice.IVoiceInteractionSession;
import android.text.TextUtils;
import android.view.IRecentsAnimationRunner;
import android.view.RemoteAnimationAdapter;
import android.view.RemoteAnimationDefinition;
import com.android.internal.app.IVoiceInteractor;
import com.android.internal.policy.IKeyguardDismissCallback;
import java.util.List;

public interface IActivityTaskManager extends IInterface {
    void activityDestroyed(IBinder iBinder) throws RemoteException;

    void activityIdle(IBinder iBinder, Configuration configuration, boolean z) throws RemoteException;

    void activityPaused(IBinder iBinder) throws RemoteException;

    void activityRelaunched(IBinder iBinder) throws RemoteException;

    void activityResumed(IBinder iBinder) throws RemoteException;

    void activitySlept(IBinder iBinder) throws RemoteException;

    void activityStopped(IBinder iBinder, Bundle bundle, PersistableBundle persistableBundle, CharSequence charSequence) throws RemoteException;

    void activityTopResumedStateLost() throws RemoteException;

    int addAppTask(IBinder iBinder, Intent intent, ActivityManager.TaskDescription taskDescription, Bitmap bitmap) throws RemoteException;

    void alwaysShowUnsupportedCompileSdkWarning(ComponentName componentName) throws RemoteException;

    void cancelRecentsAnimation(boolean z) throws RemoteException;

    void cancelTaskWindowTransition(int i) throws RemoteException;

    void clearLaunchParamsForPackages(List<String> list) throws RemoteException;

    boolean convertFromTranslucent(IBinder iBinder) throws RemoteException;

    boolean convertToTranslucent(IBinder iBinder, Bundle bundle) throws RemoteException;

    void dismissKeyguard(IBinder iBinder, IKeyguardDismissCallback iKeyguardDismissCallback, CharSequence charSequence) throws RemoteException;

    void dismissPip(boolean z, int i) throws RemoteException;

    void dismissSplitScreenMode(boolean z) throws RemoteException;

    boolean enterPictureInPictureMode(IBinder iBinder, PictureInPictureParams pictureInPictureParams) throws RemoteException;

    boolean finishActivity(IBinder iBinder, int i, Intent intent, int i2) throws RemoteException;

    boolean finishActivityAffinity(IBinder iBinder) throws RemoteException;

    void finishSubActivity(IBinder iBinder, String str, int i) throws RemoteException;

    void finishVoiceTask(IVoiceInteractionSession iVoiceInteractionSession) throws RemoteException;

    ComponentName getActivityClassForToken(IBinder iBinder) throws RemoteException;

    int getActivityDisplayId(IBinder iBinder) throws RemoteException;

    Bundle getActivityOptions(IBinder iBinder) throws RemoteException;

    List<ActivityManager.StackInfo> getAllStackInfos() throws RemoteException;

    Point getAppTaskThumbnailSize() throws RemoteException;

    List<IBinder> getAppTasks(String str) throws RemoteException;

    Bundle getAssistContextExtras(int i) throws RemoteException;

    ComponentName getCallingActivity(IBinder iBinder) throws RemoteException;

    String getCallingPackage(IBinder iBinder) throws RemoteException;

    ConfigurationInfo getDeviceConfigurationInfo() throws RemoteException;

    List<ActivityManager.RunningTaskInfo> getFilteredTasks(int i, int i2, int i3) throws RemoteException;

    ActivityManager.StackInfo getFocusedStackInfo() throws RemoteException;

    int getFrontActivityScreenCompatMode() throws RemoteException;

    int getLastResumedActivityUserId() throws RemoteException;

    String getLaunchedFromPackage(IBinder iBinder) throws RemoteException;

    int getLaunchedFromUid(IBinder iBinder) throws RemoteException;

    int getLockTaskModeState() throws RemoteException;

    int getMaxNumPictureInPictureActions(IBinder iBinder) throws RemoteException;

    boolean getPackageAskScreenCompat(String str) throws RemoteException;

    String getPackageForToken(IBinder iBinder) throws RemoteException;

    int getPackageScreenCompatMode(String str) throws RemoteException;

    ParceledListSlice getRecentTasks(int i, int i2, int i3) throws RemoteException;

    int getRequestedOrientation(IBinder iBinder) throws RemoteException;

    ActivityManager.StackInfo getStackInfo(int i, int i2) throws RemoteException;

    Rect getTaskBounds(int i) throws RemoteException;

    ActivityManager.TaskDescription getTaskDescription(int i) throws RemoteException;

    Bitmap getTaskDescriptionIcon(String str, int i) throws RemoteException;

    int getTaskForActivity(IBinder iBinder, boolean z) throws RemoteException;

    ActivityManager.TaskSnapshot getTaskSnapshot(int i, boolean z) throws RemoteException;

    List<ActivityManager.RunningTaskInfo> getTasks(int i) throws RemoteException;

    IBinder getUriPermissionOwnerForActivity(IBinder iBinder) throws RemoteException;

    boolean isActivityStartAllowedOnDisplay(int i, Intent intent, String str, int i2) throws RemoteException;

    boolean isAssistDataAllowedOnCurrentActivity() throws RemoteException;

    boolean isImmersive(IBinder iBinder) throws RemoteException;

    boolean isInLockTaskMode() throws RemoteException;

    boolean isInMultiWindowMode(IBinder iBinder) throws RemoteException;

    boolean isInPictureInPictureMode(IBinder iBinder) throws RemoteException;

    boolean isRootVoiceInteraction(IBinder iBinder) throws RemoteException;

    boolean isTopActivityImmersive() throws RemoteException;

    boolean isTopOfTask(IBinder iBinder) throws RemoteException;

    void keyguardGoingAway(int i) throws RemoteException;

    boolean launchAssistIntent(Intent intent, int i, String str, int i2, Bundle bundle) throws RemoteException;

    boolean moveActivityTaskToBack(IBinder iBinder, boolean z) throws RemoteException;

    void moveStackToDisplay(int i, int i2) throws RemoteException;

    void moveTaskToFront(IApplicationThread iApplicationThread, String str, int i, int i2, Bundle bundle) throws RemoteException;

    void moveTaskToStack(int i, int i2, boolean z) throws RemoteException;

    void moveTasksToFullscreenStack(int i, boolean z) throws RemoteException;

    boolean moveTopActivityToPinnedStack(int i, Rect rect) throws RemoteException;

    boolean navigateUpTo(IBinder iBinder, Intent intent, int i, Intent intent2) throws RemoteException;

    void notifyActivityDrawn(IBinder iBinder) throws RemoteException;

    void notifyEnterAnimationComplete(IBinder iBinder) throws RemoteException;

    void notifyLaunchTaskBehindComplete(IBinder iBinder) throws RemoteException;

    void notifyPinnedStackAnimationEnded() throws RemoteException;

    void notifyPinnedStackAnimationStarted() throws RemoteException;

    void offsetPinnedStackBounds(int i, Rect rect, int i2, int i3, int i4) throws RemoteException;

    void onBackPressedOnTaskRoot(IBinder iBinder, IRequestFinishCallback iRequestFinishCallback) throws RemoteException;

    void overridePendingTransition(IBinder iBinder, String str, int i, int i2) throws RemoteException;

    void positionTaskInStack(int i, int i2, int i3) throws RemoteException;

    void registerRemoteAnimationForNextActivityStart(String str, RemoteAnimationAdapter remoteAnimationAdapter) throws RemoteException;

    void registerRemoteAnimations(IBinder iBinder, RemoteAnimationDefinition remoteAnimationDefinition) throws RemoteException;

    void registerRemoteAnimationsForDisplay(int i, RemoteAnimationDefinition remoteAnimationDefinition) throws RemoteException;

    void registerTaskStackListener(ITaskStackListener iTaskStackListener) throws RemoteException;

    boolean releaseActivityInstance(IBinder iBinder) throws RemoteException;

    void releaseSomeActivities(IApplicationThread iApplicationThread) throws RemoteException;

    void removeAllVisibleRecentTasks() throws RemoteException;

    void removeStack(int i) throws RemoteException;

    void removeStacksInWindowingModes(int[] iArr) throws RemoteException;

    void removeStacksWithActivityTypes(int[] iArr) throws RemoteException;

    boolean removeTask(int i) throws RemoteException;

    void reportActivityFullyDrawn(IBinder iBinder, boolean z) throws RemoteException;

    void reportAssistContextExtras(IBinder iBinder, Bundle bundle, AssistStructure assistStructure, AssistContent assistContent, Uri uri) throws RemoteException;

    void reportSizeConfigurations(IBinder iBinder, int[] iArr, int[] iArr2, int[] iArr3) throws RemoteException;

    boolean requestAssistContextExtras(int i, IAssistDataReceiver iAssistDataReceiver, Bundle bundle, IBinder iBinder, boolean z, boolean z2) throws RemoteException;

    boolean requestAutofillData(IAssistDataReceiver iAssistDataReceiver, Bundle bundle, IBinder iBinder, int i) throws RemoteException;

    IBinder requestStartActivityPermissionToken(IBinder iBinder) throws RemoteException;

    void resizeDockedStack(Rect rect, Rect rect2, Rect rect3, Rect rect4, Rect rect5) throws RemoteException;

    void resizePinnedStack(Rect rect, Rect rect2) throws RemoteException;

    void resizeStack(int i, Rect rect, boolean z, boolean z2, boolean z3, int i2) throws RemoteException;

    void resizeTask(int i, Rect rect, int i2) throws RemoteException;

    void restartActivityProcessIfVisible(IBinder iBinder) throws RemoteException;

    void resumeAppSwitches() throws RemoteException;

    void setActivityController(IActivityController iActivityController, boolean z) throws RemoteException;

    void setDisablePreviewScreenshots(IBinder iBinder, boolean z) throws RemoteException;

    void setDisplayToSingleTaskInstance(int i) throws RemoteException;

    void setFocusedStack(int i) throws RemoteException;

    void setFocusedTask(int i) throws RemoteException;

    void setFrontActivityScreenCompatMode(int i) throws RemoteException;

    void setImmersive(IBinder iBinder, boolean z) throws RemoteException;

    void setInheritShowWhenLocked(IBinder iBinder, boolean z) throws RemoteException;

    void setLockScreenShown(boolean z, boolean z2) throws RemoteException;

    void setPackageAskScreenCompat(String str, boolean z) throws RemoteException;

    void setPackageScreenCompatMode(String str, int i) throws RemoteException;

    void setPersistentVrThread(int i) throws RemoteException;

    void setPictureInPictureParams(IBinder iBinder, PictureInPictureParams pictureInPictureParams) throws RemoteException;

    void setRequestedOrientation(IBinder iBinder, int i) throws RemoteException;

    void setShowWhenLocked(IBinder iBinder, boolean z) throws RemoteException;

    void setSplitScreenResizing(boolean z) throws RemoteException;

    void setTaskDescription(IBinder iBinder, ActivityManager.TaskDescription taskDescription) throws RemoteException;

    void setTaskResizeable(int i, int i2) throws RemoteException;

    void setTaskWindowingMode(int i, int i2, boolean z) throws RemoteException;

    boolean setTaskWindowingModeSplitScreenPrimary(int i, int i2, boolean z, boolean z2, Rect rect, boolean z3) throws RemoteException;

    void setTurnScreenOn(IBinder iBinder, boolean z) throws RemoteException;

    void setVoiceKeepAwake(IVoiceInteractionSession iVoiceInteractionSession, boolean z) throws RemoteException;

    int setVrMode(IBinder iBinder, boolean z, ComponentName componentName) throws RemoteException;

    void setVrThread(int i) throws RemoteException;

    boolean shouldUpRecreateTask(IBinder iBinder, String str) throws RemoteException;

    boolean showAssistFromActivity(IBinder iBinder, Bundle bundle) throws RemoteException;

    void showLockTaskEscapeMessage(IBinder iBinder) throws RemoteException;

    int startActivities(IApplicationThread iApplicationThread, String str, Intent[] intentArr, String[] strArr, IBinder iBinder, Bundle bundle, int i) throws RemoteException;

    int startActivity(IApplicationThread iApplicationThread, String str, Intent intent, String str2, IBinder iBinder, String str3, int i, int i2, ProfilerInfo profilerInfo, Bundle bundle) throws RemoteException;

    WaitResult startActivityAndWait(IApplicationThread iApplicationThread, String str, Intent intent, String str2, IBinder iBinder, String str3, int i, int i2, ProfilerInfo profilerInfo, Bundle bundle, int i3) throws RemoteException;

    int startActivityAsCaller(IApplicationThread iApplicationThread, String str, Intent intent, String str2, IBinder iBinder, String str3, int i, int i2, ProfilerInfo profilerInfo, Bundle bundle, IBinder iBinder2, boolean z, int i3) throws RemoteException;

    int startActivityAsUser(IApplicationThread iApplicationThread, String str, Intent intent, String str2, IBinder iBinder, String str3, int i, int i2, ProfilerInfo profilerInfo, Bundle bundle, int i3) throws RemoteException;

    int startActivityFromRecents(int i, Bundle bundle) throws RemoteException;

    int startActivityIntentSender(IApplicationThread iApplicationThread, IIntentSender iIntentSender, IBinder iBinder, Intent intent, String str, IBinder iBinder2, String str2, int i, int i2, int i3, Bundle bundle) throws RemoteException;

    int startActivityWithConfig(IApplicationThread iApplicationThread, String str, Intent intent, String str2, IBinder iBinder, String str3, int i, int i2, Configuration configuration, Bundle bundle, int i3) throws RemoteException;

    int startAssistantActivity(String str, int i, int i2, Intent intent, String str2, Bundle bundle, int i3) throws RemoteException;

    void startInPlaceAnimationOnFrontMostApplication(Bundle bundle) throws RemoteException;

    void startLocalVoiceInteraction(IBinder iBinder, Bundle bundle) throws RemoteException;

    void startLockTaskModeByToken(IBinder iBinder) throws RemoteException;

    boolean startNextMatchingActivity(IBinder iBinder, Intent intent, Bundle bundle) throws RemoteException;

    void startRecentsActivity(Intent intent, IAssistDataReceiver iAssistDataReceiver, IRecentsAnimationRunner iRecentsAnimationRunner) throws RemoteException;

    void startSystemLockTaskMode(int i) throws RemoteException;

    int startVoiceActivity(String str, int i, int i2, Intent intent, String str2, IVoiceInteractionSession iVoiceInteractionSession, IVoiceInteractor iVoiceInteractor, int i3, ProfilerInfo profilerInfo, Bundle bundle, int i4) throws RemoteException;

    void stopAppSwitches() throws RemoteException;

    void stopLocalVoiceInteraction(IBinder iBinder) throws RemoteException;

    void stopLockTaskModeByToken(IBinder iBinder) throws RemoteException;

    void stopSystemLockTaskMode() throws RemoteException;

    boolean supportsLocalVoiceInteraction() throws RemoteException;

    void suppressResizeConfigChanges(boolean z) throws RemoteException;

    void toggleFreeformWindowingMode(IBinder iBinder) throws RemoteException;

    void unhandledBack() throws RemoteException;

    void unregisterTaskStackListener(ITaskStackListener iTaskStackListener) throws RemoteException;

    boolean updateConfiguration(Configuration configuration) throws RemoteException;

    boolean updateDisplayOverrideConfiguration(Configuration configuration, int i) throws RemoteException;

    void updateLockTaskFeatures(int i, int i2) throws RemoteException;

    void updateLockTaskPackages(int i, String[] strArr) throws RemoteException;

    boolean willActivityBeVisible(IBinder iBinder) throws RemoteException;

    public static class Default implements IActivityTaskManager {
        public int startActivity(IApplicationThread caller, String callingPackage, Intent intent, String resolvedType, IBinder resultTo, String resultWho, int requestCode, int flags, ProfilerInfo profilerInfo, Bundle options) throws RemoteException {
            return 0;
        }

        public int startActivities(IApplicationThread caller, String callingPackage, Intent[] intents, String[] resolvedTypes, IBinder resultTo, Bundle options, int userId) throws RemoteException {
            return 0;
        }

        public int startActivityAsUser(IApplicationThread caller, String callingPackage, Intent intent, String resolvedType, IBinder resultTo, String resultWho, int requestCode, int flags, ProfilerInfo profilerInfo, Bundle options, int userId) throws RemoteException {
            return 0;
        }

        public boolean startNextMatchingActivity(IBinder callingActivity, Intent intent, Bundle options) throws RemoteException {
            return false;
        }

        public int startActivityIntentSender(IApplicationThread caller, IIntentSender target, IBinder whitelistToken, Intent fillInIntent, String resolvedType, IBinder resultTo, String resultWho, int requestCode, int flagsMask, int flagsValues, Bundle options) throws RemoteException {
            return 0;
        }

        public WaitResult startActivityAndWait(IApplicationThread caller, String callingPackage, Intent intent, String resolvedType, IBinder resultTo, String resultWho, int requestCode, int flags, ProfilerInfo profilerInfo, Bundle options, int userId) throws RemoteException {
            return null;
        }

        public int startActivityWithConfig(IApplicationThread caller, String callingPackage, Intent intent, String resolvedType, IBinder resultTo, String resultWho, int requestCode, int startFlags, Configuration newConfig, Bundle options, int userId) throws RemoteException {
            return 0;
        }

        public int startVoiceActivity(String callingPackage, int callingPid, int callingUid, Intent intent, String resolvedType, IVoiceInteractionSession session, IVoiceInteractor interactor, int flags, ProfilerInfo profilerInfo, Bundle options, int userId) throws RemoteException {
            return 0;
        }

        public int startAssistantActivity(String callingPackage, int callingPid, int callingUid, Intent intent, String resolvedType, Bundle options, int userId) throws RemoteException {
            return 0;
        }

        public void startRecentsActivity(Intent intent, IAssistDataReceiver assistDataReceiver, IRecentsAnimationRunner recentsAnimationRunner) throws RemoteException {
        }

        public int startActivityFromRecents(int taskId, Bundle options) throws RemoteException {
            return 0;
        }

        public int startActivityAsCaller(IApplicationThread caller, String callingPackage, Intent intent, String resolvedType, IBinder resultTo, String resultWho, int requestCode, int flags, ProfilerInfo profilerInfo, Bundle options, IBinder permissionToken, boolean ignoreTargetSecurity, int userId) throws RemoteException {
            return 0;
        }

        public boolean isActivityStartAllowedOnDisplay(int displayId, Intent intent, String resolvedType, int userId) throws RemoteException {
            return false;
        }

        public void unhandledBack() throws RemoteException {
        }

        public boolean finishActivity(IBinder token, int code, Intent data, int finishTask) throws RemoteException {
            return false;
        }

        public boolean finishActivityAffinity(IBinder token) throws RemoteException {
            return false;
        }

        public void activityIdle(IBinder token, Configuration config, boolean stopProfiling) throws RemoteException {
        }

        public void activityResumed(IBinder token) throws RemoteException {
        }

        public void activityTopResumedStateLost() throws RemoteException {
        }

        public void activityPaused(IBinder token) throws RemoteException {
        }

        public void activityStopped(IBinder token, Bundle state, PersistableBundle persistentState, CharSequence description) throws RemoteException {
        }

        public void activityDestroyed(IBinder token) throws RemoteException {
        }

        public void activityRelaunched(IBinder token) throws RemoteException {
        }

        public void activitySlept(IBinder token) throws RemoteException {
        }

        public int getFrontActivityScreenCompatMode() throws RemoteException {
            return 0;
        }

        public void setFrontActivityScreenCompatMode(int mode) throws RemoteException {
        }

        public String getCallingPackage(IBinder token) throws RemoteException {
            return null;
        }

        public ComponentName getCallingActivity(IBinder token) throws RemoteException {
            return null;
        }

        public void setFocusedTask(int taskId) throws RemoteException {
        }

        public boolean removeTask(int taskId) throws RemoteException {
            return false;
        }

        public void removeAllVisibleRecentTasks() throws RemoteException {
        }

        public List<ActivityManager.RunningTaskInfo> getTasks(int maxNum) throws RemoteException {
            return null;
        }

        public List<ActivityManager.RunningTaskInfo> getFilteredTasks(int maxNum, int ignoreActivityType, int ignoreWindowingMode) throws RemoteException {
            return null;
        }

        public boolean shouldUpRecreateTask(IBinder token, String destAffinity) throws RemoteException {
            return false;
        }

        public boolean navigateUpTo(IBinder token, Intent target, int resultCode, Intent resultData) throws RemoteException {
            return false;
        }

        public void moveTaskToFront(IApplicationThread app, String callingPackage, int task, int flags, Bundle options) throws RemoteException {
        }

        public int getTaskForActivity(IBinder token, boolean onlyRoot) throws RemoteException {
            return 0;
        }

        public void finishSubActivity(IBinder token, String resultWho, int requestCode) throws RemoteException {
        }

        public ParceledListSlice getRecentTasks(int maxNum, int flags, int userId) throws RemoteException {
            return null;
        }

        public boolean willActivityBeVisible(IBinder token) throws RemoteException {
            return false;
        }

        public void setRequestedOrientation(IBinder token, int requestedOrientation) throws RemoteException {
        }

        public int getRequestedOrientation(IBinder token) throws RemoteException {
            return 0;
        }

        public boolean convertFromTranslucent(IBinder token) throws RemoteException {
            return false;
        }

        public boolean convertToTranslucent(IBinder token, Bundle options) throws RemoteException {
            return false;
        }

        public void notifyActivityDrawn(IBinder token) throws RemoteException {
        }

        public void reportActivityFullyDrawn(IBinder token, boolean restoredFromBundle) throws RemoteException {
        }

        public int getActivityDisplayId(IBinder activityToken) throws RemoteException {
            return 0;
        }

        public boolean isImmersive(IBinder token) throws RemoteException {
            return false;
        }

        public void setImmersive(IBinder token, boolean immersive) throws RemoteException {
        }

        public boolean isTopActivityImmersive() throws RemoteException {
            return false;
        }

        public boolean moveActivityTaskToBack(IBinder token, boolean nonRoot) throws RemoteException {
            return false;
        }

        public ActivityManager.TaskDescription getTaskDescription(int taskId) throws RemoteException {
            return null;
        }

        public void overridePendingTransition(IBinder token, String packageName, int enterAnim, int exitAnim) throws RemoteException {
        }

        public int getLaunchedFromUid(IBinder activityToken) throws RemoteException {
            return 0;
        }

        public String getLaunchedFromPackage(IBinder activityToken) throws RemoteException {
            return null;
        }

        public void reportAssistContextExtras(IBinder token, Bundle extras, AssistStructure structure, AssistContent content, Uri referrer) throws RemoteException {
        }

        public void setFocusedStack(int stackId) throws RemoteException {
        }

        public ActivityManager.StackInfo getFocusedStackInfo() throws RemoteException {
            return null;
        }

        public Rect getTaskBounds(int taskId) throws RemoteException {
            return null;
        }

        public void cancelRecentsAnimation(boolean restoreHomeStackPosition) throws RemoteException {
        }

        public void startLockTaskModeByToken(IBinder token) throws RemoteException {
        }

        public void stopLockTaskModeByToken(IBinder token) throws RemoteException {
        }

        public void updateLockTaskPackages(int userId, String[] packages) throws RemoteException {
        }

        public boolean isInLockTaskMode() throws RemoteException {
            return false;
        }

        public int getLockTaskModeState() throws RemoteException {
            return 0;
        }

        public void setTaskDescription(IBinder token, ActivityManager.TaskDescription values) throws RemoteException {
        }

        public Bundle getActivityOptions(IBinder token) throws RemoteException {
            return null;
        }

        public List<IBinder> getAppTasks(String callingPackage) throws RemoteException {
            return null;
        }

        public void startSystemLockTaskMode(int taskId) throws RemoteException {
        }

        public void stopSystemLockTaskMode() throws RemoteException {
        }

        public void finishVoiceTask(IVoiceInteractionSession session) throws RemoteException {
        }

        public boolean isTopOfTask(IBinder token) throws RemoteException {
            return false;
        }

        public void notifyLaunchTaskBehindComplete(IBinder token) throws RemoteException {
        }

        public void notifyEnterAnimationComplete(IBinder token) throws RemoteException {
        }

        public int addAppTask(IBinder activityToken, Intent intent, ActivityManager.TaskDescription description, Bitmap thumbnail) throws RemoteException {
            return 0;
        }

        public Point getAppTaskThumbnailSize() throws RemoteException {
            return null;
        }

        public boolean releaseActivityInstance(IBinder token) throws RemoteException {
            return false;
        }

        public IBinder requestStartActivityPermissionToken(IBinder delegatorToken) throws RemoteException {
            return null;
        }

        public void releaseSomeActivities(IApplicationThread app) throws RemoteException {
        }

        public Bitmap getTaskDescriptionIcon(String filename, int userId) throws RemoteException {
            return null;
        }

        public void startInPlaceAnimationOnFrontMostApplication(Bundle opts) throws RemoteException {
        }

        public void registerTaskStackListener(ITaskStackListener listener) throws RemoteException {
        }

        public void unregisterTaskStackListener(ITaskStackListener listener) throws RemoteException {
        }

        public void setTaskResizeable(int taskId, int resizeableMode) throws RemoteException {
        }

        public void toggleFreeformWindowingMode(IBinder token) throws RemoteException {
        }

        public void resizeTask(int taskId, Rect bounds, int resizeMode) throws RemoteException {
        }

        public void moveStackToDisplay(int stackId, int displayId) throws RemoteException {
        }

        public void removeStack(int stackId) throws RemoteException {
        }

        public void setTaskWindowingMode(int taskId, int windowingMode, boolean toTop) throws RemoteException {
        }

        public void moveTaskToStack(int taskId, int stackId, boolean toTop) throws RemoteException {
        }

        public void resizeStack(int stackId, Rect bounds, boolean allowResizeInDockedMode, boolean preserveWindows, boolean animate, int animationDuration) throws RemoteException {
        }

        public boolean setTaskWindowingModeSplitScreenPrimary(int taskId, int createMode, boolean toTop, boolean animate, Rect initialBounds, boolean showRecents) throws RemoteException {
            return false;
        }

        public void offsetPinnedStackBounds(int stackId, Rect compareBounds, int xOffset, int yOffset, int animationDuration) throws RemoteException {
        }

        public void removeStacksInWindowingModes(int[] windowingModes) throws RemoteException {
        }

        public void removeStacksWithActivityTypes(int[] activityTypes) throws RemoteException {
        }

        public List<ActivityManager.StackInfo> getAllStackInfos() throws RemoteException {
            return null;
        }

        public ActivityManager.StackInfo getStackInfo(int windowingMode, int activityType) throws RemoteException {
            return null;
        }

        public void setLockScreenShown(boolean showingKeyguard, boolean showingAod) throws RemoteException {
        }

        public Bundle getAssistContextExtras(int requestType) throws RemoteException {
            return null;
        }

        public boolean launchAssistIntent(Intent intent, int requestType, String hint, int userHandle, Bundle args) throws RemoteException {
            return false;
        }

        public boolean requestAssistContextExtras(int requestType, IAssistDataReceiver receiver, Bundle receiverExtras, IBinder activityToken, boolean focused, boolean newSessionId) throws RemoteException {
            return false;
        }

        public boolean requestAutofillData(IAssistDataReceiver receiver, Bundle receiverExtras, IBinder activityToken, int flags) throws RemoteException {
            return false;
        }

        public boolean isAssistDataAllowedOnCurrentActivity() throws RemoteException {
            return false;
        }

        public boolean showAssistFromActivity(IBinder token, Bundle args) throws RemoteException {
            return false;
        }

        public boolean isRootVoiceInteraction(IBinder token) throws RemoteException {
            return false;
        }

        public void showLockTaskEscapeMessage(IBinder token) throws RemoteException {
        }

        public void keyguardGoingAway(int flags) throws RemoteException {
        }

        public ComponentName getActivityClassForToken(IBinder token) throws RemoteException {
            return null;
        }

        public String getPackageForToken(IBinder token) throws RemoteException {
            return null;
        }

        public void positionTaskInStack(int taskId, int stackId, int position) throws RemoteException {
        }

        public void reportSizeConfigurations(IBinder token, int[] horizontalSizeConfiguration, int[] verticalSizeConfigurations, int[] smallestWidthConfigurations) throws RemoteException {
        }

        public void dismissSplitScreenMode(boolean toTop) throws RemoteException {
        }

        public void dismissPip(boolean animate, int animationDuration) throws RemoteException {
        }

        public void suppressResizeConfigChanges(boolean suppress) throws RemoteException {
        }

        public void moveTasksToFullscreenStack(int fromStackId, boolean onTop) throws RemoteException {
        }

        public boolean moveTopActivityToPinnedStack(int stackId, Rect bounds) throws RemoteException {
            return false;
        }

        public boolean isInMultiWindowMode(IBinder token) throws RemoteException {
            return false;
        }

        public boolean isInPictureInPictureMode(IBinder token) throws RemoteException {
            return false;
        }

        public boolean enterPictureInPictureMode(IBinder token, PictureInPictureParams params) throws RemoteException {
            return false;
        }

        public void setPictureInPictureParams(IBinder token, PictureInPictureParams params) throws RemoteException {
        }

        public int getMaxNumPictureInPictureActions(IBinder token) throws RemoteException {
            return 0;
        }

        public IBinder getUriPermissionOwnerForActivity(IBinder activityToken) throws RemoteException {
            return null;
        }

        public void resizeDockedStack(Rect dockedBounds, Rect tempDockedTaskBounds, Rect tempDockedTaskInsetBounds, Rect tempOtherTaskBounds, Rect tempOtherTaskInsetBounds) throws RemoteException {
        }

        public void setSplitScreenResizing(boolean resizing) throws RemoteException {
        }

        public int setVrMode(IBinder token, boolean enabled, ComponentName packageName) throws RemoteException {
            return 0;
        }

        public void startLocalVoiceInteraction(IBinder token, Bundle options) throws RemoteException {
        }

        public void stopLocalVoiceInteraction(IBinder token) throws RemoteException {
        }

        public boolean supportsLocalVoiceInteraction() throws RemoteException {
            return false;
        }

        public void notifyPinnedStackAnimationStarted() throws RemoteException {
        }

        public void notifyPinnedStackAnimationEnded() throws RemoteException {
        }

        public ConfigurationInfo getDeviceConfigurationInfo() throws RemoteException {
            return null;
        }

        public void resizePinnedStack(Rect pinnedBounds, Rect tempPinnedTaskBounds) throws RemoteException {
        }

        public boolean updateDisplayOverrideConfiguration(Configuration values, int displayId) throws RemoteException {
            return false;
        }

        public void dismissKeyguard(IBinder token, IKeyguardDismissCallback callback, CharSequence message) throws RemoteException {
        }

        public void cancelTaskWindowTransition(int taskId) throws RemoteException {
        }

        public ActivityManager.TaskSnapshot getTaskSnapshot(int taskId, boolean reducedResolution) throws RemoteException {
            return null;
        }

        public void setDisablePreviewScreenshots(IBinder token, boolean disable) throws RemoteException {
        }

        public int getLastResumedActivityUserId() throws RemoteException {
            return 0;
        }

        public boolean updateConfiguration(Configuration values) throws RemoteException {
            return false;
        }

        public void updateLockTaskFeatures(int userId, int flags) throws RemoteException {
        }

        public void setShowWhenLocked(IBinder token, boolean showWhenLocked) throws RemoteException {
        }

        public void setInheritShowWhenLocked(IBinder token, boolean setInheritShownWhenLocked) throws RemoteException {
        }

        public void setTurnScreenOn(IBinder token, boolean turnScreenOn) throws RemoteException {
        }

        public void registerRemoteAnimations(IBinder token, RemoteAnimationDefinition definition) throws RemoteException {
        }

        public void registerRemoteAnimationForNextActivityStart(String packageName, RemoteAnimationAdapter adapter) throws RemoteException {
        }

        public void registerRemoteAnimationsForDisplay(int displayId, RemoteAnimationDefinition definition) throws RemoteException {
        }

        public void alwaysShowUnsupportedCompileSdkWarning(ComponentName activity) throws RemoteException {
        }

        public void setVrThread(int tid) throws RemoteException {
        }

        public void setPersistentVrThread(int tid) throws RemoteException {
        }

        public void stopAppSwitches() throws RemoteException {
        }

        public void resumeAppSwitches() throws RemoteException {
        }

        public void setActivityController(IActivityController watcher, boolean imAMonkey) throws RemoteException {
        }

        public void setVoiceKeepAwake(IVoiceInteractionSession session, boolean keepAwake) throws RemoteException {
        }

        public int getPackageScreenCompatMode(String packageName) throws RemoteException {
            return 0;
        }

        public void setPackageScreenCompatMode(String packageName, int mode) throws RemoteException {
        }

        public boolean getPackageAskScreenCompat(String packageName) throws RemoteException {
            return false;
        }

        public void setPackageAskScreenCompat(String packageName, boolean ask) throws RemoteException {
        }

        public void clearLaunchParamsForPackages(List<String> list) throws RemoteException {
        }

        public void setDisplayToSingleTaskInstance(int displayId) throws RemoteException {
        }

        public void restartActivityProcessIfVisible(IBinder activityToken) throws RemoteException {
        }

        public void onBackPressedOnTaskRoot(IBinder activityToken, IRequestFinishCallback callback) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IActivityTaskManager {
        private static final String DESCRIPTOR = "android.app.IActivityTaskManager";
        static final int TRANSACTION_activityDestroyed = 22;
        static final int TRANSACTION_activityIdle = 17;
        static final int TRANSACTION_activityPaused = 20;
        static final int TRANSACTION_activityRelaunched = 23;
        static final int TRANSACTION_activityResumed = 18;
        static final int TRANSACTION_activitySlept = 24;
        static final int TRANSACTION_activityStopped = 21;
        static final int TRANSACTION_activityTopResumedStateLost = 19;
        static final int TRANSACTION_addAppTask = 75;
        static final int TRANSACTION_alwaysShowUnsupportedCompileSdkWarning = 147;
        static final int TRANSACTION_cancelRecentsAnimation = 60;
        static final int TRANSACTION_cancelTaskWindowTransition = 135;
        static final int TRANSACTION_clearLaunchParamsForPackages = 158;
        static final int TRANSACTION_convertFromTranslucent = 43;
        static final int TRANSACTION_convertToTranslucent = 44;
        static final int TRANSACTION_dismissKeyguard = 134;
        static final int TRANSACTION_dismissPip = 113;
        static final int TRANSACTION_dismissSplitScreenMode = 112;
        static final int TRANSACTION_enterPictureInPictureMode = 119;
        static final int TRANSACTION_finishActivity = 15;
        static final int TRANSACTION_finishActivityAffinity = 16;
        static final int TRANSACTION_finishSubActivity = 38;
        static final int TRANSACTION_finishVoiceTask = 71;
        static final int TRANSACTION_getActivityClassForToken = 108;
        static final int TRANSACTION_getActivityDisplayId = 47;
        static final int TRANSACTION_getActivityOptions = 67;
        static final int TRANSACTION_getAllStackInfos = 96;
        static final int TRANSACTION_getAppTaskThumbnailSize = 76;
        static final int TRANSACTION_getAppTasks = 68;
        static final int TRANSACTION_getAssistContextExtras = 99;
        static final int TRANSACTION_getCallingActivity = 28;
        static final int TRANSACTION_getCallingPackage = 27;
        static final int TRANSACTION_getDeviceConfigurationInfo = 131;
        static final int TRANSACTION_getFilteredTasks = 33;
        static final int TRANSACTION_getFocusedStackInfo = 58;
        static final int TRANSACTION_getFrontActivityScreenCompatMode = 25;
        static final int TRANSACTION_getLastResumedActivityUserId = 138;
        static final int TRANSACTION_getLaunchedFromPackage = 55;
        static final int TRANSACTION_getLaunchedFromUid = 54;
        static final int TRANSACTION_getLockTaskModeState = 65;
        static final int TRANSACTION_getMaxNumPictureInPictureActions = 121;
        static final int TRANSACTION_getPackageAskScreenCompat = 156;
        static final int TRANSACTION_getPackageForToken = 109;
        static final int TRANSACTION_getPackageScreenCompatMode = 154;
        static final int TRANSACTION_getRecentTasks = 39;
        static final int TRANSACTION_getRequestedOrientation = 42;
        static final int TRANSACTION_getStackInfo = 97;
        static final int TRANSACTION_getTaskBounds = 59;
        static final int TRANSACTION_getTaskDescription = 52;
        static final int TRANSACTION_getTaskDescriptionIcon = 80;
        static final int TRANSACTION_getTaskForActivity = 37;
        static final int TRANSACTION_getTaskSnapshot = 136;
        static final int TRANSACTION_getTasks = 32;
        static final int TRANSACTION_getUriPermissionOwnerForActivity = 122;
        static final int TRANSACTION_isActivityStartAllowedOnDisplay = 13;
        static final int TRANSACTION_isAssistDataAllowedOnCurrentActivity = 103;
        static final int TRANSACTION_isImmersive = 48;
        static final int TRANSACTION_isInLockTaskMode = 64;
        static final int TRANSACTION_isInMultiWindowMode = 117;
        static final int TRANSACTION_isInPictureInPictureMode = 118;
        static final int TRANSACTION_isRootVoiceInteraction = 105;
        static final int TRANSACTION_isTopActivityImmersive = 50;
        static final int TRANSACTION_isTopOfTask = 72;
        static final int TRANSACTION_keyguardGoingAway = 107;
        static final int TRANSACTION_launchAssistIntent = 100;
        static final int TRANSACTION_moveActivityTaskToBack = 51;
        static final int TRANSACTION_moveStackToDisplay = 87;
        static final int TRANSACTION_moveTaskToFront = 36;
        static final int TRANSACTION_moveTaskToStack = 90;
        static final int TRANSACTION_moveTasksToFullscreenStack = 115;
        static final int TRANSACTION_moveTopActivityToPinnedStack = 116;
        static final int TRANSACTION_navigateUpTo = 35;
        static final int TRANSACTION_notifyActivityDrawn = 45;
        static final int TRANSACTION_notifyEnterAnimationComplete = 74;
        static final int TRANSACTION_notifyLaunchTaskBehindComplete = 73;
        static final int TRANSACTION_notifyPinnedStackAnimationEnded = 130;
        static final int TRANSACTION_notifyPinnedStackAnimationStarted = 129;
        static final int TRANSACTION_offsetPinnedStackBounds = 93;
        static final int TRANSACTION_onBackPressedOnTaskRoot = 161;
        static final int TRANSACTION_overridePendingTransition = 53;
        static final int TRANSACTION_positionTaskInStack = 110;
        static final int TRANSACTION_registerRemoteAnimationForNextActivityStart = 145;
        static final int TRANSACTION_registerRemoteAnimations = 144;
        static final int TRANSACTION_registerRemoteAnimationsForDisplay = 146;
        static final int TRANSACTION_registerTaskStackListener = 82;
        static final int TRANSACTION_releaseActivityInstance = 77;
        static final int TRANSACTION_releaseSomeActivities = 79;
        static final int TRANSACTION_removeAllVisibleRecentTasks = 31;
        static final int TRANSACTION_removeStack = 88;
        static final int TRANSACTION_removeStacksInWindowingModes = 94;
        static final int TRANSACTION_removeStacksWithActivityTypes = 95;
        static final int TRANSACTION_removeTask = 30;
        static final int TRANSACTION_reportActivityFullyDrawn = 46;
        static final int TRANSACTION_reportAssistContextExtras = 56;
        static final int TRANSACTION_reportSizeConfigurations = 111;
        static final int TRANSACTION_requestAssistContextExtras = 101;
        static final int TRANSACTION_requestAutofillData = 102;
        static final int TRANSACTION_requestStartActivityPermissionToken = 78;
        static final int TRANSACTION_resizeDockedStack = 123;
        static final int TRANSACTION_resizePinnedStack = 132;
        static final int TRANSACTION_resizeStack = 91;
        static final int TRANSACTION_resizeTask = 86;
        static final int TRANSACTION_restartActivityProcessIfVisible = 160;
        static final int TRANSACTION_resumeAppSwitches = 151;
        static final int TRANSACTION_setActivityController = 152;
        static final int TRANSACTION_setDisablePreviewScreenshots = 137;
        static final int TRANSACTION_setDisplayToSingleTaskInstance = 159;
        static final int TRANSACTION_setFocusedStack = 57;
        static final int TRANSACTION_setFocusedTask = 29;
        static final int TRANSACTION_setFrontActivityScreenCompatMode = 26;
        static final int TRANSACTION_setImmersive = 49;
        static final int TRANSACTION_setInheritShowWhenLocked = 142;
        static final int TRANSACTION_setLockScreenShown = 98;
        static final int TRANSACTION_setPackageAskScreenCompat = 157;
        static final int TRANSACTION_setPackageScreenCompatMode = 155;
        static final int TRANSACTION_setPersistentVrThread = 149;
        static final int TRANSACTION_setPictureInPictureParams = 120;
        static final int TRANSACTION_setRequestedOrientation = 41;
        static final int TRANSACTION_setShowWhenLocked = 141;
        static final int TRANSACTION_setSplitScreenResizing = 124;
        static final int TRANSACTION_setTaskDescription = 66;
        static final int TRANSACTION_setTaskResizeable = 84;
        static final int TRANSACTION_setTaskWindowingMode = 89;
        static final int TRANSACTION_setTaskWindowingModeSplitScreenPrimary = 92;
        static final int TRANSACTION_setTurnScreenOn = 143;
        static final int TRANSACTION_setVoiceKeepAwake = 153;
        static final int TRANSACTION_setVrMode = 125;
        static final int TRANSACTION_setVrThread = 148;
        static final int TRANSACTION_shouldUpRecreateTask = 34;
        static final int TRANSACTION_showAssistFromActivity = 104;
        static final int TRANSACTION_showLockTaskEscapeMessage = 106;
        static final int TRANSACTION_startActivities = 2;
        static final int TRANSACTION_startActivity = 1;
        static final int TRANSACTION_startActivityAndWait = 6;
        static final int TRANSACTION_startActivityAsCaller = 12;
        static final int TRANSACTION_startActivityAsUser = 3;
        static final int TRANSACTION_startActivityFromRecents = 11;
        static final int TRANSACTION_startActivityIntentSender = 5;
        static final int TRANSACTION_startActivityWithConfig = 7;
        static final int TRANSACTION_startAssistantActivity = 9;
        static final int TRANSACTION_startInPlaceAnimationOnFrontMostApplication = 81;
        static final int TRANSACTION_startLocalVoiceInteraction = 126;
        static final int TRANSACTION_startLockTaskModeByToken = 61;
        static final int TRANSACTION_startNextMatchingActivity = 4;
        static final int TRANSACTION_startRecentsActivity = 10;
        static final int TRANSACTION_startSystemLockTaskMode = 69;
        static final int TRANSACTION_startVoiceActivity = 8;
        static final int TRANSACTION_stopAppSwitches = 150;
        static final int TRANSACTION_stopLocalVoiceInteraction = 127;
        static final int TRANSACTION_stopLockTaskModeByToken = 62;
        static final int TRANSACTION_stopSystemLockTaskMode = 70;
        static final int TRANSACTION_supportsLocalVoiceInteraction = 128;
        static final int TRANSACTION_suppressResizeConfigChanges = 114;
        static final int TRANSACTION_toggleFreeformWindowingMode = 85;
        static final int TRANSACTION_unhandledBack = 14;
        static final int TRANSACTION_unregisterTaskStackListener = 83;
        static final int TRANSACTION_updateConfiguration = 139;
        static final int TRANSACTION_updateDisplayOverrideConfiguration = 133;
        static final int TRANSACTION_updateLockTaskFeatures = 140;
        static final int TRANSACTION_updateLockTaskPackages = 63;
        static final int TRANSACTION_willActivityBeVisible = 40;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IActivityTaskManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IActivityTaskManager)) {
                return new Proxy(obj);
            }
            return (IActivityTaskManager) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "startActivity";
                case 2:
                    return "startActivities";
                case 3:
                    return "startActivityAsUser";
                case 4:
                    return "startNextMatchingActivity";
                case 5:
                    return "startActivityIntentSender";
                case 6:
                    return "startActivityAndWait";
                case 7:
                    return "startActivityWithConfig";
                case 8:
                    return "startVoiceActivity";
                case 9:
                    return "startAssistantActivity";
                case 10:
                    return "startRecentsActivity";
                case 11:
                    return "startActivityFromRecents";
                case 12:
                    return "startActivityAsCaller";
                case 13:
                    return "isActivityStartAllowedOnDisplay";
                case 14:
                    return "unhandledBack";
                case 15:
                    return "finishActivity";
                case 16:
                    return "finishActivityAffinity";
                case 17:
                    return "activityIdle";
                case 18:
                    return "activityResumed";
                case 19:
                    return "activityTopResumedStateLost";
                case 20:
                    return "activityPaused";
                case 21:
                    return "activityStopped";
                case 22:
                    return "activityDestroyed";
                case 23:
                    return "activityRelaunched";
                case 24:
                    return "activitySlept";
                case 25:
                    return "getFrontActivityScreenCompatMode";
                case 26:
                    return "setFrontActivityScreenCompatMode";
                case 27:
                    return "getCallingPackage";
                case 28:
                    return "getCallingActivity";
                case 29:
                    return "setFocusedTask";
                case 30:
                    return "removeTask";
                case 31:
                    return "removeAllVisibleRecentTasks";
                case 32:
                    return "getTasks";
                case 33:
                    return "getFilteredTasks";
                case 34:
                    return "shouldUpRecreateTask";
                case 35:
                    return "navigateUpTo";
                case 36:
                    return "moveTaskToFront";
                case 37:
                    return "getTaskForActivity";
                case 38:
                    return "finishSubActivity";
                case 39:
                    return "getRecentTasks";
                case 40:
                    return "willActivityBeVisible";
                case 41:
                    return "setRequestedOrientation";
                case 42:
                    return "getRequestedOrientation";
                case 43:
                    return "convertFromTranslucent";
                case 44:
                    return "convertToTranslucent";
                case 45:
                    return "notifyActivityDrawn";
                case 46:
                    return "reportActivityFullyDrawn";
                case 47:
                    return "getActivityDisplayId";
                case 48:
                    return "isImmersive";
                case 49:
                    return "setImmersive";
                case 50:
                    return "isTopActivityImmersive";
                case 51:
                    return "moveActivityTaskToBack";
                case 52:
                    return "getTaskDescription";
                case 53:
                    return "overridePendingTransition";
                case 54:
                    return "getLaunchedFromUid";
                case 55:
                    return "getLaunchedFromPackage";
                case 56:
                    return "reportAssistContextExtras";
                case 57:
                    return "setFocusedStack";
                case 58:
                    return "getFocusedStackInfo";
                case 59:
                    return "getTaskBounds";
                case 60:
                    return "cancelRecentsAnimation";
                case 61:
                    return "startLockTaskModeByToken";
                case 62:
                    return "stopLockTaskModeByToken";
                case 63:
                    return "updateLockTaskPackages";
                case 64:
                    return "isInLockTaskMode";
                case 65:
                    return "getLockTaskModeState";
                case 66:
                    return "setTaskDescription";
                case 67:
                    return "getActivityOptions";
                case 68:
                    return "getAppTasks";
                case 69:
                    return "startSystemLockTaskMode";
                case 70:
                    return "stopSystemLockTaskMode";
                case 71:
                    return "finishVoiceTask";
                case 72:
                    return "isTopOfTask";
                case 73:
                    return "notifyLaunchTaskBehindComplete";
                case 74:
                    return "notifyEnterAnimationComplete";
                case 75:
                    return "addAppTask";
                case 76:
                    return "getAppTaskThumbnailSize";
                case 77:
                    return "releaseActivityInstance";
                case 78:
                    return "requestStartActivityPermissionToken";
                case 79:
                    return "releaseSomeActivities";
                case 80:
                    return "getTaskDescriptionIcon";
                case 81:
                    return "startInPlaceAnimationOnFrontMostApplication";
                case 82:
                    return "registerTaskStackListener";
                case 83:
                    return "unregisterTaskStackListener";
                case 84:
                    return "setTaskResizeable";
                case 85:
                    return "toggleFreeformWindowingMode";
                case 86:
                    return "resizeTask";
                case 87:
                    return "moveStackToDisplay";
                case 88:
                    return "removeStack";
                case 89:
                    return "setTaskWindowingMode";
                case 90:
                    return "moveTaskToStack";
                case 91:
                    return "resizeStack";
                case 92:
                    return "setTaskWindowingModeSplitScreenPrimary";
                case 93:
                    return "offsetPinnedStackBounds";
                case 94:
                    return "removeStacksInWindowingModes";
                case 95:
                    return "removeStacksWithActivityTypes";
                case 96:
                    return "getAllStackInfos";
                case 97:
                    return "getStackInfo";
                case 98:
                    return "setLockScreenShown";
                case 99:
                    return "getAssistContextExtras";
                case 100:
                    return "launchAssistIntent";
                case 101:
                    return "requestAssistContextExtras";
                case 102:
                    return "requestAutofillData";
                case 103:
                    return "isAssistDataAllowedOnCurrentActivity";
                case 104:
                    return "showAssistFromActivity";
                case 105:
                    return "isRootVoiceInteraction";
                case 106:
                    return "showLockTaskEscapeMessage";
                case 107:
                    return "keyguardGoingAway";
                case 108:
                    return "getActivityClassForToken";
                case 109:
                    return "getPackageForToken";
                case 110:
                    return "positionTaskInStack";
                case 111:
                    return "reportSizeConfigurations";
                case 112:
                    return "dismissSplitScreenMode";
                case 113:
                    return "dismissPip";
                case 114:
                    return "suppressResizeConfigChanges";
                case 115:
                    return "moveTasksToFullscreenStack";
                case 116:
                    return "moveTopActivityToPinnedStack";
                case 117:
                    return "isInMultiWindowMode";
                case 118:
                    return "isInPictureInPictureMode";
                case 119:
                    return "enterPictureInPictureMode";
                case 120:
                    return "setPictureInPictureParams";
                case 121:
                    return "getMaxNumPictureInPictureActions";
                case 122:
                    return "getUriPermissionOwnerForActivity";
                case 123:
                    return "resizeDockedStack";
                case 124:
                    return "setSplitScreenResizing";
                case 125:
                    return "setVrMode";
                case 126:
                    return "startLocalVoiceInteraction";
                case 127:
                    return "stopLocalVoiceInteraction";
                case 128:
                    return "supportsLocalVoiceInteraction";
                case 129:
                    return "notifyPinnedStackAnimationStarted";
                case 130:
                    return "notifyPinnedStackAnimationEnded";
                case 131:
                    return "getDeviceConfigurationInfo";
                case 132:
                    return "resizePinnedStack";
                case 133:
                    return "updateDisplayOverrideConfiguration";
                case 134:
                    return "dismissKeyguard";
                case 135:
                    return "cancelTaskWindowTransition";
                case 136:
                    return "getTaskSnapshot";
                case 137:
                    return "setDisablePreviewScreenshots";
                case 138:
                    return "getLastResumedActivityUserId";
                case 139:
                    return "updateConfiguration";
                case 140:
                    return "updateLockTaskFeatures";
                case 141:
                    return "setShowWhenLocked";
                case 142:
                    return "setInheritShowWhenLocked";
                case 143:
                    return "setTurnScreenOn";
                case 144:
                    return "registerRemoteAnimations";
                case 145:
                    return "registerRemoteAnimationForNextActivityStart";
                case 146:
                    return "registerRemoteAnimationsForDisplay";
                case 147:
                    return "alwaysShowUnsupportedCompileSdkWarning";
                case 148:
                    return "setVrThread";
                case 149:
                    return "setPersistentVrThread";
                case 150:
                    return "stopAppSwitches";
                case 151:
                    return "resumeAppSwitches";
                case 152:
                    return "setActivityController";
                case 153:
                    return "setVoiceKeepAwake";
                case 154:
                    return "getPackageScreenCompatMode";
                case 155:
                    return "setPackageScreenCompatMode";
                case 156:
                    return "getPackageAskScreenCompat";
                case 157:
                    return "setPackageAskScreenCompat";
                case 158:
                    return "clearLaunchParamsForPackages";
                case 159:
                    return "setDisplayToSingleTaskInstance";
                case 160:
                    return "restartActivityProcessIfVisible";
                case 161:
                    return "onBackPressedOnTaskRoot";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v20, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v58, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v78, resolved type: android.content.res.Configuration} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v116, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v146, resolved type: android.app.ActivityManager$TaskDescription} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v158, resolved type: android.graphics.Bitmap} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v168, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v178, resolved type: android.graphics.Rect} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v229, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v234, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v252, resolved type: android.graphics.Rect} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v258, resolved type: android.app.PictureInPictureParams} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v262, resolved type: android.app.PictureInPictureParams} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v275, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v279, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v286, resolved type: android.graphics.Rect} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v290, resolved type: android.content.res.Configuration} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v302, resolved type: android.content.res.Configuration} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v310, resolved type: android.view.RemoteAnimationDefinition} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v314, resolved type: android.view.RemoteAnimationAdapter} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v318, resolved type: android.view.RemoteAnimationDefinition} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v322, resolved type: android.content.ComponentName} */
        /* JADX WARNING: type inference failed for: r0v1 */
        /* JADX WARNING: type inference failed for: r0v2 */
        /* JADX WARNING: type inference failed for: r0v8 */
        /* JADX WARNING: type inference failed for: r0v14 */
        /* JADX WARNING: type inference failed for: r0v24 */
        /* JADX WARNING: type inference failed for: r0v30 */
        /* JADX WARNING: type inference failed for: r0v36 */
        /* JADX WARNING: type inference failed for: r0v42 */
        /* JADX WARNING: type inference failed for: r0v48 */
        /* JADX WARNING: type inference failed for: r0v54, types: [android.content.Intent] */
        /* JADX WARNING: type inference failed for: r0v62 */
        /* JADX WARNING: type inference failed for: r0v69, types: [android.content.Intent] */
        /* JADX WARNING: type inference failed for: r0v73, types: [android.content.Intent] */
        /* JADX WARNING: type inference failed for: r0v84, types: [java.lang.CharSequence] */
        /* JADX WARNING: type inference failed for: r0v100, types: [android.content.Intent] */
        /* JADX WARNING: type inference failed for: r0v104 */
        /* JADX WARNING: type inference failed for: r0v131 */
        /* JADX WARNING: type inference failed for: r0v186 */
        /* JADX WARNING: type inference failed for: r0v194 */
        /* JADX WARNING: type inference failed for: r0v201 */
        /* JADX WARNING: type inference failed for: r0v215 */
        /* JADX WARNING: type inference failed for: r0v221 */
        /* JADX WARNING: type inference failed for: r0v268 */
        /* JADX WARNING: type inference failed for: r0v294, types: [java.lang.CharSequence] */
        /* JADX WARNING: type inference failed for: r0v341 */
        /* JADX WARNING: type inference failed for: r0v342 */
        /* JADX WARNING: type inference failed for: r0v343 */
        /* JADX WARNING: type inference failed for: r0v344 */
        /* JADX WARNING: type inference failed for: r0v345 */
        /* JADX WARNING: type inference failed for: r0v346 */
        /* JADX WARNING: type inference failed for: r0v347 */
        /* JADX WARNING: type inference failed for: r0v348 */
        /* JADX WARNING: type inference failed for: r0v349 */
        /* JADX WARNING: type inference failed for: r0v350 */
        /* JADX WARNING: type inference failed for: r0v351 */
        /* JADX WARNING: type inference failed for: r0v352 */
        /* JADX WARNING: type inference failed for: r0v353 */
        /* JADX WARNING: type inference failed for: r0v354 */
        /* JADX WARNING: type inference failed for: r0v355 */
        /* JADX WARNING: type inference failed for: r0v356 */
        /* JADX WARNING: type inference failed for: r0v357 */
        /* JADX WARNING: type inference failed for: r0v358 */
        /* JADX WARNING: type inference failed for: r0v359 */
        /* JADX WARNING: type inference failed for: r0v360 */
        /* JADX WARNING: type inference failed for: r0v361 */
        /* JADX WARNING: type inference failed for: r0v362 */
        /* JADX WARNING: type inference failed for: r0v363 */
        /* JADX WARNING: type inference failed for: r0v364 */
        /* JADX WARNING: type inference failed for: r0v365 */
        /* JADX WARNING: type inference failed for: r0v366 */
        /* JADX WARNING: type inference failed for: r0v367 */
        /* JADX WARNING: type inference failed for: r0v368 */
        /* JADX WARNING: type inference failed for: r0v369 */
        /* JADX WARNING: type inference failed for: r0v370 */
        /* JADX WARNING: type inference failed for: r0v371 */
        /* JADX WARNING: type inference failed for: r0v372 */
        /* JADX WARNING: type inference failed for: r0v373 */
        /* JADX WARNING: type inference failed for: r0v374 */
        /* JADX WARNING: type inference failed for: r0v375 */
        /* JADX WARNING: type inference failed for: r0v376 */
        /* JADX WARNING: type inference failed for: r0v377 */
        /* JADX WARNING: type inference failed for: r0v378 */
        /* JADX WARNING: type inference failed for: r0v379 */
        /* JADX WARNING: type inference failed for: r0v380 */
        /* JADX WARNING: type inference failed for: r0v381 */
        /* JADX WARNING: type inference failed for: r0v382 */
        /* JADX WARNING: type inference failed for: r0v383 */
        /* JADX WARNING: type inference failed for: r0v384 */
        /* JADX WARNING: type inference failed for: r0v385 */
        /*  JADX ERROR: NullPointerException in pass: CodeShrinkVisitor
            java.lang.NullPointerException
            */
        /* JADX WARNING: Multi-variable type inference failed */
        public boolean onTransact(int r27, android.os.Parcel r28, android.os.Parcel r29, int r30) throws android.os.RemoteException {
            /*
                r26 = this;
                r14 = r26
                r15 = r27
                r13 = r28
                r11 = r29
                java.lang.String r8 = "android.app.IActivityTaskManager"
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r7 = 1
                if (r15 == r0) goto L_0x12f4
                r12 = 0
                r0 = 0
                switch(r15) {
                    case 1: goto L_0x1280;
                    case 2: goto L_0x1232;
                    case 3: goto L_0x11b7;
                    case 4: goto L_0x1181;
                    case 5: goto L_0x110c;
                    case 6: goto L_0x1087;
                    case 7: goto L_0x100c;
                    case 8: goto L_0x0f8d;
                    case 9: goto L_0x0f3d;
                    case 10: goto L_0x0f0f;
                    case 11: goto L_0x0ee3;
                    case 12: goto L_0x0e58;
                    case 13: goto L_0x0e2e;
                    case 14: goto L_0x0e24;
                    case 15: goto L_0x0dfa;
                    case 16: goto L_0x0de8;
                    case 17: goto L_0x0dc4;
                    case 18: goto L_0x0db6;
                    case 19: goto L_0x0dac;
                    case 20: goto L_0x0d9e;
                    case 21: goto L_0x0d60;
                    case 22: goto L_0x0d55;
                    case 23: goto L_0x0d47;
                    case 24: goto L_0x0d3c;
                    case 25: goto L_0x0d2e;
                    case 26: goto L_0x0d20;
                    case 27: goto L_0x0d0e;
                    case 28: goto L_0x0cf3;
                    case 29: goto L_0x0ce5;
                    case 30: goto L_0x0cd3;
                    case 31: goto L_0x0cc9;
                    case 32: goto L_0x0cb7;
                    case 33: goto L_0x0c9d;
                    case 34: goto L_0x0c87;
                    case 35: goto L_0x0c51;
                    case 36: goto L_0x0c1c;
                    case 37: goto L_0x0c01;
                    case 38: goto L_0x0beb;
                    case 39: goto L_0x0bc8;
                    case 40: goto L_0x0bb6;
                    case 41: goto L_0x0ba4;
                    case 42: goto L_0x0b92;
                    case 43: goto L_0x0b80;
                    case 44: goto L_0x0b5e;
                    case 45: goto L_0x0b50;
                    case 46: goto L_0x0b39;
                    case 47: goto L_0x0b27;
                    case 48: goto L_0x0b15;
                    case 49: goto L_0x0afe;
                    case 50: goto L_0x0af0;
                    case 51: goto L_0x0ad5;
                    case 52: goto L_0x0aba;
                    case 53: goto L_0x0aa0;
                    case 54: goto L_0x0a8e;
                    case 55: goto L_0x0a7c;
                    case 56: goto L_0x0a27;
                    case 57: goto L_0x0a19;
                    case 58: goto L_0x0a02;
                    case 59: goto L_0x09e7;
                    case 60: goto L_0x09d4;
                    case 61: goto L_0x09c6;
                    case 62: goto L_0x09b8;
                    case 63: goto L_0x09a6;
                    case 64: goto L_0x0998;
                    case 65: goto L_0x098a;
                    case 66: goto L_0x096c;
                    case 67: goto L_0x0951;
                    case 68: goto L_0x093f;
                    case 69: goto L_0x0931;
                    case 70: goto L_0x0927;
                    case 71: goto L_0x0915;
                    case 72: goto L_0x0903;
                    case 73: goto L_0x08f5;
                    case 74: goto L_0x08e7;
                    case 75: goto L_0x08a5;
                    case 76: goto L_0x088e;
                    case 77: goto L_0x087c;
                    case 78: goto L_0x086a;
                    case 79: goto L_0x0858;
                    case 80: goto L_0x0839;
                    case 81: goto L_0x081f;
                    case 82: goto L_0x080d;
                    case 83: goto L_0x07fb;
                    case 84: goto L_0x07e9;
                    case 85: goto L_0x07db;
                    case 86: goto L_0x07b9;
                    case 87: goto L_0x07a7;
                    case 88: goto L_0x0799;
                    case 89: goto L_0x077e;
                    case 90: goto L_0x0763;
                    case 91: goto L_0x0721;
                    case 92: goto L_0x06db;
                    case 93: goto L_0x06aa;
                    case 94: goto L_0x069c;
                    case 95: goto L_0x068e;
                    case 96: goto L_0x0680;
                    case 97: goto L_0x0661;
                    case 98: goto L_0x0645;
                    case 99: goto L_0x062a;
                    case 100: goto L_0x05ea;
                    case 101: goto L_0x05a3;
                    case 102: goto L_0x0575;
                    case 103: goto L_0x0567;
                    case 104: goto L_0x0545;
                    case 105: goto L_0x0533;
                    case 106: goto L_0x0528;
                    case 107: goto L_0x051a;
                    case 108: goto L_0x04ff;
                    case 109: goto L_0x04ed;
                    case 110: goto L_0x04d7;
                    case 111: goto L_0x04bd;
                    case 112: goto L_0x04aa;
                    case 113: goto L_0x0493;
                    case 114: goto L_0x0480;
                    case 115: goto L_0x0469;
                    case 116: goto L_0x0447;
                    case 117: goto L_0x0435;
                    case 118: goto L_0x0423;
                    case 119: goto L_0x0401;
                    case 120: goto L_0x03e3;
                    case 121: goto L_0x03d1;
                    case 122: goto L_0x03bf;
                    case 123: goto L_0x0362;
                    case 124: goto L_0x034f;
                    case 125: goto L_0x0324;
                    case 126: goto L_0x0306;
                    case 127: goto L_0x02f8;
                    case 128: goto L_0x02ea;
                    case 129: goto L_0x02e0;
                    case 130: goto L_0x02d6;
                    case 131: goto L_0x02bf;
                    case 132: goto L_0x0295;
                    case 133: goto L_0x0273;
                    case 134: goto L_0x024d;
                    case 135: goto L_0x023f;
                    case 136: goto L_0x021b;
                    case 137: goto L_0x0204;
                    case 138: goto L_0x01f6;
                    case 139: goto L_0x01d8;
                    case 140: goto L_0x01c6;
                    case 141: goto L_0x01af;
                    case 142: goto L_0x0198;
                    case 143: goto L_0x0181;
                    case 144: goto L_0x0163;
                    case 145: goto L_0x0145;
                    case 146: goto L_0x0127;
                    case 147: goto L_0x010d;
                    case 148: goto L_0x00ff;
                    case 149: goto L_0x00f1;
                    case 150: goto L_0x00e7;
                    case 151: goto L_0x00dd;
                    case 152: goto L_0x00c2;
                    case 153: goto L_0x00a7;
                    case 154: goto L_0x0095;
                    case 155: goto L_0x0083;
                    case 156: goto L_0x0071;
                    case 157: goto L_0x005a;
                    case 158: goto L_0x004c;
                    case 159: goto L_0x003e;
                    case 160: goto L_0x0030;
                    case 161: goto L_0x001a;
                    default: goto L_0x0015;
                }
            L_0x0015:
                boolean r0 = super.onTransact(r27, r28, r29, r30)
                return r0
            L_0x001a:
                r13.enforceInterface(r8)
                android.os.IBinder r0 = r28.readStrongBinder()
                android.os.IBinder r1 = r28.readStrongBinder()
                android.app.IRequestFinishCallback r1 = android.app.IRequestFinishCallback.Stub.asInterface(r1)
                r14.onBackPressedOnTaskRoot(r0, r1)
                r29.writeNoException()
                return r7
            L_0x0030:
                r13.enforceInterface(r8)
                android.os.IBinder r0 = r28.readStrongBinder()
                r14.restartActivityProcessIfVisible(r0)
                r29.writeNoException()
                return r7
            L_0x003e:
                r13.enforceInterface(r8)
                int r0 = r28.readInt()
                r14.setDisplayToSingleTaskInstance(r0)
                r29.writeNoException()
                return r7
            L_0x004c:
                r13.enforceInterface(r8)
                java.util.ArrayList r0 = r28.createStringArrayList()
                r14.clearLaunchParamsForPackages(r0)
                r29.writeNoException()
                return r7
            L_0x005a:
                r13.enforceInterface(r8)
                java.lang.String r0 = r28.readString()
                int r1 = r28.readInt()
                if (r1 == 0) goto L_0x0069
                r12 = r7
            L_0x0069:
                r1 = r12
                r14.setPackageAskScreenCompat(r0, r1)
                r29.writeNoException()
                return r7
            L_0x0071:
                r13.enforceInterface(r8)
                java.lang.String r0 = r28.readString()
                boolean r1 = r14.getPackageAskScreenCompat(r0)
                r29.writeNoException()
                r11.writeInt(r1)
                return r7
            L_0x0083:
                r13.enforceInterface(r8)
                java.lang.String r0 = r28.readString()
                int r1 = r28.readInt()
                r14.setPackageScreenCompatMode(r0, r1)
                r29.writeNoException()
                return r7
            L_0x0095:
                r13.enforceInterface(r8)
                java.lang.String r0 = r28.readString()
                int r1 = r14.getPackageScreenCompatMode(r0)
                r29.writeNoException()
                r11.writeInt(r1)
                return r7
            L_0x00a7:
                r13.enforceInterface(r8)
                android.os.IBinder r0 = r28.readStrongBinder()
                android.service.voice.IVoiceInteractionSession r0 = android.service.voice.IVoiceInteractionSession.Stub.asInterface(r0)
                int r1 = r28.readInt()
                if (r1 == 0) goto L_0x00ba
                r12 = r7
            L_0x00ba:
                r1 = r12
                r14.setVoiceKeepAwake(r0, r1)
                r29.writeNoException()
                return r7
            L_0x00c2:
                r13.enforceInterface(r8)
                android.os.IBinder r0 = r28.readStrongBinder()
                android.app.IActivityController r0 = android.app.IActivityController.Stub.asInterface(r0)
                int r1 = r28.readInt()
                if (r1 == 0) goto L_0x00d5
                r12 = r7
            L_0x00d5:
                r1 = r12
                r14.setActivityController(r0, r1)
                r29.writeNoException()
                return r7
            L_0x00dd:
                r13.enforceInterface(r8)
                r26.resumeAppSwitches()
                r29.writeNoException()
                return r7
            L_0x00e7:
                r13.enforceInterface(r8)
                r26.stopAppSwitches()
                r29.writeNoException()
                return r7
            L_0x00f1:
                r13.enforceInterface(r8)
                int r0 = r28.readInt()
                r14.setPersistentVrThread(r0)
                r29.writeNoException()
                return r7
            L_0x00ff:
                r13.enforceInterface(r8)
                int r0 = r28.readInt()
                r14.setVrThread(r0)
                r29.writeNoException()
                return r7
            L_0x010d:
                r13.enforceInterface(r8)
                int r1 = r28.readInt()
                if (r1 == 0) goto L_0x011f
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x0120
            L_0x011f:
            L_0x0120:
                r14.alwaysShowUnsupportedCompileSdkWarning(r0)
                r29.writeNoException()
                return r7
            L_0x0127:
                r13.enforceInterface(r8)
                int r1 = r28.readInt()
                int r2 = r28.readInt()
                if (r2 == 0) goto L_0x013d
                android.os.Parcelable$Creator<android.view.RemoteAnimationDefinition> r0 = android.view.RemoteAnimationDefinition.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.view.RemoteAnimationDefinition r0 = (android.view.RemoteAnimationDefinition) r0
                goto L_0x013e
            L_0x013d:
            L_0x013e:
                r14.registerRemoteAnimationsForDisplay(r1, r0)
                r29.writeNoException()
                return r7
            L_0x0145:
                r13.enforceInterface(r8)
                java.lang.String r1 = r28.readString()
                int r2 = r28.readInt()
                if (r2 == 0) goto L_0x015b
                android.os.Parcelable$Creator<android.view.RemoteAnimationAdapter> r0 = android.view.RemoteAnimationAdapter.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.view.RemoteAnimationAdapter r0 = (android.view.RemoteAnimationAdapter) r0
                goto L_0x015c
            L_0x015b:
            L_0x015c:
                r14.registerRemoteAnimationForNextActivityStart(r1, r0)
                r29.writeNoException()
                return r7
            L_0x0163:
                r13.enforceInterface(r8)
                android.os.IBinder r1 = r28.readStrongBinder()
                int r2 = r28.readInt()
                if (r2 == 0) goto L_0x0179
                android.os.Parcelable$Creator<android.view.RemoteAnimationDefinition> r0 = android.view.RemoteAnimationDefinition.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.view.RemoteAnimationDefinition r0 = (android.view.RemoteAnimationDefinition) r0
                goto L_0x017a
            L_0x0179:
            L_0x017a:
                r14.registerRemoteAnimations(r1, r0)
                r29.writeNoException()
                return r7
            L_0x0181:
                r13.enforceInterface(r8)
                android.os.IBinder r0 = r28.readStrongBinder()
                int r1 = r28.readInt()
                if (r1 == 0) goto L_0x0190
                r12 = r7
            L_0x0190:
                r1 = r12
                r14.setTurnScreenOn(r0, r1)
                r29.writeNoException()
                return r7
            L_0x0198:
                r13.enforceInterface(r8)
                android.os.IBinder r0 = r28.readStrongBinder()
                int r1 = r28.readInt()
                if (r1 == 0) goto L_0x01a7
                r12 = r7
            L_0x01a7:
                r1 = r12
                r14.setInheritShowWhenLocked(r0, r1)
                r29.writeNoException()
                return r7
            L_0x01af:
                r13.enforceInterface(r8)
                android.os.IBinder r0 = r28.readStrongBinder()
                int r1 = r28.readInt()
                if (r1 == 0) goto L_0x01be
                r12 = r7
            L_0x01be:
                r1 = r12
                r14.setShowWhenLocked(r0, r1)
                r29.writeNoException()
                return r7
            L_0x01c6:
                r13.enforceInterface(r8)
                int r0 = r28.readInt()
                int r1 = r28.readInt()
                r14.updateLockTaskFeatures(r0, r1)
                r29.writeNoException()
                return r7
            L_0x01d8:
                r13.enforceInterface(r8)
                int r1 = r28.readInt()
                if (r1 == 0) goto L_0x01ea
                android.os.Parcelable$Creator<android.content.res.Configuration> r0 = android.content.res.Configuration.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.content.res.Configuration r0 = (android.content.res.Configuration) r0
                goto L_0x01eb
            L_0x01ea:
            L_0x01eb:
                boolean r1 = r14.updateConfiguration(r0)
                r29.writeNoException()
                r11.writeInt(r1)
                return r7
            L_0x01f6:
                r13.enforceInterface(r8)
                int r0 = r26.getLastResumedActivityUserId()
                r29.writeNoException()
                r11.writeInt(r0)
                return r7
            L_0x0204:
                r13.enforceInterface(r8)
                android.os.IBinder r0 = r28.readStrongBinder()
                int r1 = r28.readInt()
                if (r1 == 0) goto L_0x0213
                r12 = r7
            L_0x0213:
                r1 = r12
                r14.setDisablePreviewScreenshots(r0, r1)
                r29.writeNoException()
                return r7
            L_0x021b:
                r13.enforceInterface(r8)
                int r0 = r28.readInt()
                int r1 = r28.readInt()
                if (r1 == 0) goto L_0x022a
                r1 = r7
                goto L_0x022b
            L_0x022a:
                r1 = r12
            L_0x022b:
                android.app.ActivityManager$TaskSnapshot r2 = r14.getTaskSnapshot(r0, r1)
                r29.writeNoException()
                if (r2 == 0) goto L_0x023b
                r11.writeInt(r7)
                r2.writeToParcel(r11, r7)
                goto L_0x023e
            L_0x023b:
                r11.writeInt(r12)
            L_0x023e:
                return r7
            L_0x023f:
                r13.enforceInterface(r8)
                int r0 = r28.readInt()
                r14.cancelTaskWindowTransition(r0)
                r29.writeNoException()
                return r7
            L_0x024d:
                r13.enforceInterface(r8)
                android.os.IBinder r1 = r28.readStrongBinder()
                android.os.IBinder r2 = r28.readStrongBinder()
                com.android.internal.policy.IKeyguardDismissCallback r2 = com.android.internal.policy.IKeyguardDismissCallback.Stub.asInterface(r2)
                int r3 = r28.readInt()
                if (r3 == 0) goto L_0x026b
                android.os.Parcelable$Creator<java.lang.CharSequence> r0 = android.text.TextUtils.CHAR_SEQUENCE_CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                java.lang.CharSequence r0 = (java.lang.CharSequence) r0
                goto L_0x026c
            L_0x026b:
            L_0x026c:
                r14.dismissKeyguard(r1, r2, r0)
                r29.writeNoException()
                return r7
            L_0x0273:
                r13.enforceInterface(r8)
                int r1 = r28.readInt()
                if (r1 == 0) goto L_0x0285
                android.os.Parcelable$Creator<android.content.res.Configuration> r0 = android.content.res.Configuration.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.content.res.Configuration r0 = (android.content.res.Configuration) r0
                goto L_0x0286
            L_0x0285:
            L_0x0286:
                int r1 = r28.readInt()
                boolean r2 = r14.updateDisplayOverrideConfiguration(r0, r1)
                r29.writeNoException()
                r11.writeInt(r2)
                return r7
            L_0x0295:
                r13.enforceInterface(r8)
                int r1 = r28.readInt()
                if (r1 == 0) goto L_0x02a7
                android.os.Parcelable$Creator<android.graphics.Rect> r1 = android.graphics.Rect.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r13)
                android.graphics.Rect r1 = (android.graphics.Rect) r1
                goto L_0x02a8
            L_0x02a7:
                r1 = r0
            L_0x02a8:
                int r2 = r28.readInt()
                if (r2 == 0) goto L_0x02b7
                android.os.Parcelable$Creator<android.graphics.Rect> r0 = android.graphics.Rect.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.graphics.Rect r0 = (android.graphics.Rect) r0
                goto L_0x02b8
            L_0x02b7:
            L_0x02b8:
                r14.resizePinnedStack(r1, r0)
                r29.writeNoException()
                return r7
            L_0x02bf:
                r13.enforceInterface(r8)
                android.content.pm.ConfigurationInfo r0 = r26.getDeviceConfigurationInfo()
                r29.writeNoException()
                if (r0 == 0) goto L_0x02d2
                r11.writeInt(r7)
                r0.writeToParcel(r11, r7)
                goto L_0x02d5
            L_0x02d2:
                r11.writeInt(r12)
            L_0x02d5:
                return r7
            L_0x02d6:
                r13.enforceInterface(r8)
                r26.notifyPinnedStackAnimationEnded()
                r29.writeNoException()
                return r7
            L_0x02e0:
                r13.enforceInterface(r8)
                r26.notifyPinnedStackAnimationStarted()
                r29.writeNoException()
                return r7
            L_0x02ea:
                r13.enforceInterface(r8)
                boolean r0 = r26.supportsLocalVoiceInteraction()
                r29.writeNoException()
                r11.writeInt(r0)
                return r7
            L_0x02f8:
                r13.enforceInterface(r8)
                android.os.IBinder r0 = r28.readStrongBinder()
                r14.stopLocalVoiceInteraction(r0)
                r29.writeNoException()
                return r7
            L_0x0306:
                r13.enforceInterface(r8)
                android.os.IBinder r1 = r28.readStrongBinder()
                int r2 = r28.readInt()
                if (r2 == 0) goto L_0x031c
                android.os.Parcelable$Creator<android.os.Bundle> r0 = android.os.Bundle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.os.Bundle r0 = (android.os.Bundle) r0
                goto L_0x031d
            L_0x031c:
            L_0x031d:
                r14.startLocalVoiceInteraction(r1, r0)
                r29.writeNoException()
                return r7
            L_0x0324:
                r13.enforceInterface(r8)
                android.os.IBinder r1 = r28.readStrongBinder()
                int r2 = r28.readInt()
                if (r2 == 0) goto L_0x0333
                r12 = r7
            L_0x0333:
                r2 = r12
                int r3 = r28.readInt()
                if (r3 == 0) goto L_0x0343
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x0344
            L_0x0343:
            L_0x0344:
                int r3 = r14.setVrMode(r1, r2, r0)
                r29.writeNoException()
                r11.writeInt(r3)
                return r7
            L_0x034f:
                r13.enforceInterface(r8)
                int r0 = r28.readInt()
                if (r0 == 0) goto L_0x035a
                r12 = r7
            L_0x035a:
                r0 = r12
                r14.setSplitScreenResizing(r0)
                r29.writeNoException()
                return r7
            L_0x0362:
                r13.enforceInterface(r8)
                int r1 = r28.readInt()
                if (r1 == 0) goto L_0x0374
                android.os.Parcelable$Creator<android.graphics.Rect> r1 = android.graphics.Rect.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r13)
                android.graphics.Rect r1 = (android.graphics.Rect) r1
                goto L_0x0375
            L_0x0374:
                r1 = r0
            L_0x0375:
                int r2 = r28.readInt()
                if (r2 == 0) goto L_0x0384
                android.os.Parcelable$Creator<android.graphics.Rect> r2 = android.graphics.Rect.CREATOR
                java.lang.Object r2 = r2.createFromParcel(r13)
                android.graphics.Rect r2 = (android.graphics.Rect) r2
                goto L_0x0385
            L_0x0384:
                r2 = r0
            L_0x0385:
                int r3 = r28.readInt()
                if (r3 == 0) goto L_0x0394
                android.os.Parcelable$Creator<android.graphics.Rect> r3 = android.graphics.Rect.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r13)
                android.graphics.Rect r3 = (android.graphics.Rect) r3
                goto L_0x0395
            L_0x0394:
                r3 = r0
            L_0x0395:
                int r4 = r28.readInt()
                if (r4 == 0) goto L_0x03a4
                android.os.Parcelable$Creator<android.graphics.Rect> r4 = android.graphics.Rect.CREATOR
                java.lang.Object r4 = r4.createFromParcel(r13)
                android.graphics.Rect r4 = (android.graphics.Rect) r4
                goto L_0x03a5
            L_0x03a4:
                r4 = r0
            L_0x03a5:
                int r5 = r28.readInt()
                if (r5 == 0) goto L_0x03b5
                android.os.Parcelable$Creator<android.graphics.Rect> r0 = android.graphics.Rect.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.graphics.Rect r0 = (android.graphics.Rect) r0
            L_0x03b3:
                r5 = r0
                goto L_0x03b6
            L_0x03b5:
                goto L_0x03b3
            L_0x03b6:
                r0 = r26
                r0.resizeDockedStack(r1, r2, r3, r4, r5)
                r29.writeNoException()
                return r7
            L_0x03bf:
                r13.enforceInterface(r8)
                android.os.IBinder r0 = r28.readStrongBinder()
                android.os.IBinder r1 = r14.getUriPermissionOwnerForActivity(r0)
                r29.writeNoException()
                r11.writeStrongBinder(r1)
                return r7
            L_0x03d1:
                r13.enforceInterface(r8)
                android.os.IBinder r0 = r28.readStrongBinder()
                int r1 = r14.getMaxNumPictureInPictureActions(r0)
                r29.writeNoException()
                r11.writeInt(r1)
                return r7
            L_0x03e3:
                r13.enforceInterface(r8)
                android.os.IBinder r1 = r28.readStrongBinder()
                int r2 = r28.readInt()
                if (r2 == 0) goto L_0x03f9
                android.os.Parcelable$Creator<android.app.PictureInPictureParams> r0 = android.app.PictureInPictureParams.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.app.PictureInPictureParams r0 = (android.app.PictureInPictureParams) r0
                goto L_0x03fa
            L_0x03f9:
            L_0x03fa:
                r14.setPictureInPictureParams(r1, r0)
                r29.writeNoException()
                return r7
            L_0x0401:
                r13.enforceInterface(r8)
                android.os.IBinder r1 = r28.readStrongBinder()
                int r2 = r28.readInt()
                if (r2 == 0) goto L_0x0417
                android.os.Parcelable$Creator<android.app.PictureInPictureParams> r0 = android.app.PictureInPictureParams.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.app.PictureInPictureParams r0 = (android.app.PictureInPictureParams) r0
                goto L_0x0418
            L_0x0417:
            L_0x0418:
                boolean r2 = r14.enterPictureInPictureMode(r1, r0)
                r29.writeNoException()
                r11.writeInt(r2)
                return r7
            L_0x0423:
                r13.enforceInterface(r8)
                android.os.IBinder r0 = r28.readStrongBinder()
                boolean r1 = r14.isInPictureInPictureMode(r0)
                r29.writeNoException()
                r11.writeInt(r1)
                return r7
            L_0x0435:
                r13.enforceInterface(r8)
                android.os.IBinder r0 = r28.readStrongBinder()
                boolean r1 = r14.isInMultiWindowMode(r0)
                r29.writeNoException()
                r11.writeInt(r1)
                return r7
            L_0x0447:
                r13.enforceInterface(r8)
                int r1 = r28.readInt()
                int r2 = r28.readInt()
                if (r2 == 0) goto L_0x045d
                android.os.Parcelable$Creator<android.graphics.Rect> r0 = android.graphics.Rect.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.graphics.Rect r0 = (android.graphics.Rect) r0
                goto L_0x045e
            L_0x045d:
            L_0x045e:
                boolean r2 = r14.moveTopActivityToPinnedStack(r1, r0)
                r29.writeNoException()
                r11.writeInt(r2)
                return r7
            L_0x0469:
                r13.enforceInterface(r8)
                int r0 = r28.readInt()
                int r1 = r28.readInt()
                if (r1 == 0) goto L_0x0478
                r12 = r7
            L_0x0478:
                r1 = r12
                r14.moveTasksToFullscreenStack(r0, r1)
                r29.writeNoException()
                return r7
            L_0x0480:
                r13.enforceInterface(r8)
                int r0 = r28.readInt()
                if (r0 == 0) goto L_0x048b
                r12 = r7
            L_0x048b:
                r0 = r12
                r14.suppressResizeConfigChanges(r0)
                r29.writeNoException()
                return r7
            L_0x0493:
                r13.enforceInterface(r8)
                int r0 = r28.readInt()
                if (r0 == 0) goto L_0x049e
                r12 = r7
            L_0x049e:
                r0 = r12
                int r1 = r28.readInt()
                r14.dismissPip(r0, r1)
                r29.writeNoException()
                return r7
            L_0x04aa:
                r13.enforceInterface(r8)
                int r0 = r28.readInt()
                if (r0 == 0) goto L_0x04b5
                r12 = r7
            L_0x04b5:
                r0 = r12
                r14.dismissSplitScreenMode(r0)
                r29.writeNoException()
                return r7
            L_0x04bd:
                r13.enforceInterface(r8)
                android.os.IBinder r0 = r28.readStrongBinder()
                int[] r1 = r28.createIntArray()
                int[] r2 = r28.createIntArray()
                int[] r3 = r28.createIntArray()
                r14.reportSizeConfigurations(r0, r1, r2, r3)
                r29.writeNoException()
                return r7
            L_0x04d7:
                r13.enforceInterface(r8)
                int r0 = r28.readInt()
                int r1 = r28.readInt()
                int r2 = r28.readInt()
                r14.positionTaskInStack(r0, r1, r2)
                r29.writeNoException()
                return r7
            L_0x04ed:
                r13.enforceInterface(r8)
                android.os.IBinder r0 = r28.readStrongBinder()
                java.lang.String r1 = r14.getPackageForToken(r0)
                r29.writeNoException()
                r11.writeString(r1)
                return r7
            L_0x04ff:
                r13.enforceInterface(r8)
                android.os.IBinder r0 = r28.readStrongBinder()
                android.content.ComponentName r1 = r14.getActivityClassForToken(r0)
                r29.writeNoException()
                if (r1 == 0) goto L_0x0516
                r11.writeInt(r7)
                r1.writeToParcel((android.os.Parcel) r11, (int) r7)
                goto L_0x0519
            L_0x0516:
                r11.writeInt(r12)
            L_0x0519:
                return r7
            L_0x051a:
                r13.enforceInterface(r8)
                int r0 = r28.readInt()
                r14.keyguardGoingAway(r0)
                r29.writeNoException()
                return r7
            L_0x0528:
                r13.enforceInterface(r8)
                android.os.IBinder r0 = r28.readStrongBinder()
                r14.showLockTaskEscapeMessage(r0)
                return r7
            L_0x0533:
                r13.enforceInterface(r8)
                android.os.IBinder r0 = r28.readStrongBinder()
                boolean r1 = r14.isRootVoiceInteraction(r0)
                r29.writeNoException()
                r11.writeInt(r1)
                return r7
            L_0x0545:
                r13.enforceInterface(r8)
                android.os.IBinder r1 = r28.readStrongBinder()
                int r2 = r28.readInt()
                if (r2 == 0) goto L_0x055b
                android.os.Parcelable$Creator<android.os.Bundle> r0 = android.os.Bundle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.os.Bundle r0 = (android.os.Bundle) r0
                goto L_0x055c
            L_0x055b:
            L_0x055c:
                boolean r2 = r14.showAssistFromActivity(r1, r0)
                r29.writeNoException()
                r11.writeInt(r2)
                return r7
            L_0x0567:
                r13.enforceInterface(r8)
                boolean r0 = r26.isAssistDataAllowedOnCurrentActivity()
                r29.writeNoException()
                r11.writeInt(r0)
                return r7
            L_0x0575:
                r13.enforceInterface(r8)
                android.os.IBinder r1 = r28.readStrongBinder()
                android.app.IAssistDataReceiver r1 = android.app.IAssistDataReceiver.Stub.asInterface(r1)
                int r2 = r28.readInt()
                if (r2 == 0) goto L_0x058f
                android.os.Parcelable$Creator<android.os.Bundle> r0 = android.os.Bundle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.os.Bundle r0 = (android.os.Bundle) r0
                goto L_0x0590
            L_0x058f:
            L_0x0590:
                android.os.IBinder r2 = r28.readStrongBinder()
                int r3 = r28.readInt()
                boolean r4 = r14.requestAutofillData(r1, r0, r2, r3)
                r29.writeNoException()
                r11.writeInt(r4)
                return r7
            L_0x05a3:
                r13.enforceInterface(r8)
                int r9 = r28.readInt()
                android.os.IBinder r1 = r28.readStrongBinder()
                android.app.IAssistDataReceiver r10 = android.app.IAssistDataReceiver.Stub.asInterface(r1)
                int r1 = r28.readInt()
                if (r1 == 0) goto L_0x05c2
                android.os.Parcelable$Creator<android.os.Bundle> r0 = android.os.Bundle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.os.Bundle r0 = (android.os.Bundle) r0
            L_0x05c0:
                r3 = r0
                goto L_0x05c3
            L_0x05c2:
                goto L_0x05c0
            L_0x05c3:
                android.os.IBinder r16 = r28.readStrongBinder()
                int r0 = r28.readInt()
                if (r0 == 0) goto L_0x05cf
                r5 = r7
                goto L_0x05d0
            L_0x05cf:
                r5 = r12
            L_0x05d0:
                int r0 = r28.readInt()
                if (r0 == 0) goto L_0x05d8
                r6 = r7
                goto L_0x05d9
            L_0x05d8:
                r6 = r12
            L_0x05d9:
                r0 = r26
                r1 = r9
                r2 = r10
                r4 = r16
                boolean r0 = r0.requestAssistContextExtras(r1, r2, r3, r4, r5, r6)
                r29.writeNoException()
                r11.writeInt(r0)
                return r7
            L_0x05ea:
                r13.enforceInterface(r8)
                int r1 = r28.readInt()
                if (r1 == 0) goto L_0x05fc
                android.os.Parcelable$Creator<android.content.Intent> r1 = android.content.Intent.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r13)
                android.content.Intent r1 = (android.content.Intent) r1
                goto L_0x05fd
            L_0x05fc:
                r1 = r0
            L_0x05fd:
                int r6 = r28.readInt()
                java.lang.String r9 = r28.readString()
                int r10 = r28.readInt()
                int r2 = r28.readInt()
                if (r2 == 0) goto L_0x0619
                android.os.Parcelable$Creator<android.os.Bundle> r0 = android.os.Bundle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.os.Bundle r0 = (android.os.Bundle) r0
            L_0x0617:
                r5 = r0
                goto L_0x061a
            L_0x0619:
                goto L_0x0617
            L_0x061a:
                r0 = r26
                r2 = r6
                r3 = r9
                r4 = r10
                boolean r0 = r0.launchAssistIntent(r1, r2, r3, r4, r5)
                r29.writeNoException()
                r11.writeInt(r0)
                return r7
            L_0x062a:
                r13.enforceInterface(r8)
                int r0 = r28.readInt()
                android.os.Bundle r1 = r14.getAssistContextExtras(r0)
                r29.writeNoException()
                if (r1 == 0) goto L_0x0641
                r11.writeInt(r7)
                r1.writeToParcel(r11, r7)
                goto L_0x0644
            L_0x0641:
                r11.writeInt(r12)
            L_0x0644:
                return r7
            L_0x0645:
                r13.enforceInterface(r8)
                int r0 = r28.readInt()
                if (r0 == 0) goto L_0x0650
                r0 = r7
                goto L_0x0651
            L_0x0650:
                r0 = r12
            L_0x0651:
                int r1 = r28.readInt()
                if (r1 == 0) goto L_0x0659
                r12 = r7
            L_0x0659:
                r1 = r12
                r14.setLockScreenShown(r0, r1)
                r29.writeNoException()
                return r7
            L_0x0661:
                r13.enforceInterface(r8)
                int r0 = r28.readInt()
                int r1 = r28.readInt()
                android.app.ActivityManager$StackInfo r2 = r14.getStackInfo(r0, r1)
                r29.writeNoException()
                if (r2 == 0) goto L_0x067c
                r11.writeInt(r7)
                r2.writeToParcel(r11, r7)
                goto L_0x067f
            L_0x067c:
                r11.writeInt(r12)
            L_0x067f:
                return r7
            L_0x0680:
                r13.enforceInterface(r8)
                java.util.List r0 = r26.getAllStackInfos()
                r29.writeNoException()
                r11.writeTypedList(r0)
                return r7
            L_0x068e:
                r13.enforceInterface(r8)
                int[] r0 = r28.createIntArray()
                r14.removeStacksWithActivityTypes(r0)
                r29.writeNoException()
                return r7
            L_0x069c:
                r13.enforceInterface(r8)
                int[] r0 = r28.createIntArray()
                r14.removeStacksInWindowingModes(r0)
                r29.writeNoException()
                return r7
            L_0x06aa:
                r13.enforceInterface(r8)
                int r6 = r28.readInt()
                int r1 = r28.readInt()
                if (r1 == 0) goto L_0x06c1
                android.os.Parcelable$Creator<android.graphics.Rect> r0 = android.graphics.Rect.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.graphics.Rect r0 = (android.graphics.Rect) r0
            L_0x06bf:
                r2 = r0
                goto L_0x06c2
            L_0x06c1:
                goto L_0x06bf
            L_0x06c2:
                int r9 = r28.readInt()
                int r10 = r28.readInt()
                int r12 = r28.readInt()
                r0 = r26
                r1 = r6
                r3 = r9
                r4 = r10
                r5 = r12
                r0.offsetPinnedStackBounds(r1, r2, r3, r4, r5)
                r29.writeNoException()
                return r7
            L_0x06db:
                r13.enforceInterface(r8)
                int r9 = r28.readInt()
                int r10 = r28.readInt()
                int r1 = r28.readInt()
                if (r1 == 0) goto L_0x06ee
                r3 = r7
                goto L_0x06ef
            L_0x06ee:
                r3 = r12
            L_0x06ef:
                int r1 = r28.readInt()
                if (r1 == 0) goto L_0x06f7
                r4 = r7
                goto L_0x06f8
            L_0x06f7:
                r4 = r12
            L_0x06f8:
                int r1 = r28.readInt()
                if (r1 == 0) goto L_0x0708
                android.os.Parcelable$Creator<android.graphics.Rect> r0 = android.graphics.Rect.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.graphics.Rect r0 = (android.graphics.Rect) r0
            L_0x0706:
                r5 = r0
                goto L_0x0709
            L_0x0708:
                goto L_0x0706
            L_0x0709:
                int r0 = r28.readInt()
                if (r0 == 0) goto L_0x0711
                r6 = r7
                goto L_0x0712
            L_0x0711:
                r6 = r12
            L_0x0712:
                r0 = r26
                r1 = r9
                r2 = r10
                boolean r0 = r0.setTaskWindowingModeSplitScreenPrimary(r1, r2, r3, r4, r5, r6)
                r29.writeNoException()
                r11.writeInt(r0)
                return r7
            L_0x0721:
                r13.enforceInterface(r8)
                int r9 = r28.readInt()
                int r1 = r28.readInt()
                if (r1 == 0) goto L_0x0738
                android.os.Parcelable$Creator<android.graphics.Rect> r0 = android.graphics.Rect.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.graphics.Rect r0 = (android.graphics.Rect) r0
            L_0x0736:
                r2 = r0
                goto L_0x0739
            L_0x0738:
                goto L_0x0736
            L_0x0739:
                int r0 = r28.readInt()
                if (r0 == 0) goto L_0x0741
                r3 = r7
                goto L_0x0742
            L_0x0741:
                r3 = r12
            L_0x0742:
                int r0 = r28.readInt()
                if (r0 == 0) goto L_0x074a
                r4 = r7
                goto L_0x074b
            L_0x074a:
                r4 = r12
            L_0x074b:
                int r0 = r28.readInt()
                if (r0 == 0) goto L_0x0753
                r5 = r7
                goto L_0x0754
            L_0x0753:
                r5 = r12
            L_0x0754:
                int r10 = r28.readInt()
                r0 = r26
                r1 = r9
                r6 = r10
                r0.resizeStack(r1, r2, r3, r4, r5, r6)
                r29.writeNoException()
                return r7
            L_0x0763:
                r13.enforceInterface(r8)
                int r0 = r28.readInt()
                int r1 = r28.readInt()
                int r2 = r28.readInt()
                if (r2 == 0) goto L_0x0776
                r12 = r7
            L_0x0776:
                r2 = r12
                r14.moveTaskToStack(r0, r1, r2)
                r29.writeNoException()
                return r7
            L_0x077e:
                r13.enforceInterface(r8)
                int r0 = r28.readInt()
                int r1 = r28.readInt()
                int r2 = r28.readInt()
                if (r2 == 0) goto L_0x0791
                r12 = r7
            L_0x0791:
                r2 = r12
                r14.setTaskWindowingMode(r0, r1, r2)
                r29.writeNoException()
                return r7
            L_0x0799:
                r13.enforceInterface(r8)
                int r0 = r28.readInt()
                r14.removeStack(r0)
                r29.writeNoException()
                return r7
            L_0x07a7:
                r13.enforceInterface(r8)
                int r0 = r28.readInt()
                int r1 = r28.readInt()
                r14.moveStackToDisplay(r0, r1)
                r29.writeNoException()
                return r7
            L_0x07b9:
                r13.enforceInterface(r8)
                int r1 = r28.readInt()
                int r2 = r28.readInt()
                if (r2 == 0) goto L_0x07cf
                android.os.Parcelable$Creator<android.graphics.Rect> r0 = android.graphics.Rect.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.graphics.Rect r0 = (android.graphics.Rect) r0
                goto L_0x07d0
            L_0x07cf:
            L_0x07d0:
                int r2 = r28.readInt()
                r14.resizeTask(r1, r0, r2)
                r29.writeNoException()
                return r7
            L_0x07db:
                r13.enforceInterface(r8)
                android.os.IBinder r0 = r28.readStrongBinder()
                r14.toggleFreeformWindowingMode(r0)
                r29.writeNoException()
                return r7
            L_0x07e9:
                r13.enforceInterface(r8)
                int r0 = r28.readInt()
                int r1 = r28.readInt()
                r14.setTaskResizeable(r0, r1)
                r29.writeNoException()
                return r7
            L_0x07fb:
                r13.enforceInterface(r8)
                android.os.IBinder r0 = r28.readStrongBinder()
                android.app.ITaskStackListener r0 = android.app.ITaskStackListener.Stub.asInterface(r0)
                r14.unregisterTaskStackListener(r0)
                r29.writeNoException()
                return r7
            L_0x080d:
                r13.enforceInterface(r8)
                android.os.IBinder r0 = r28.readStrongBinder()
                android.app.ITaskStackListener r0 = android.app.ITaskStackListener.Stub.asInterface(r0)
                r14.registerTaskStackListener(r0)
                r29.writeNoException()
                return r7
            L_0x081f:
                r13.enforceInterface(r8)
                int r1 = r28.readInt()
                if (r1 == 0) goto L_0x0831
                android.os.Parcelable$Creator<android.os.Bundle> r0 = android.os.Bundle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.os.Bundle r0 = (android.os.Bundle) r0
                goto L_0x0832
            L_0x0831:
            L_0x0832:
                r14.startInPlaceAnimationOnFrontMostApplication(r0)
                r29.writeNoException()
                return r7
            L_0x0839:
                r13.enforceInterface(r8)
                java.lang.String r0 = r28.readString()
                int r1 = r28.readInt()
                android.graphics.Bitmap r2 = r14.getTaskDescriptionIcon(r0, r1)
                r29.writeNoException()
                if (r2 == 0) goto L_0x0854
                r11.writeInt(r7)
                r2.writeToParcel(r11, r7)
                goto L_0x0857
            L_0x0854:
                r11.writeInt(r12)
            L_0x0857:
                return r7
            L_0x0858:
                r13.enforceInterface(r8)
                android.os.IBinder r0 = r28.readStrongBinder()
                android.app.IApplicationThread r0 = android.app.IApplicationThread.Stub.asInterface(r0)
                r14.releaseSomeActivities(r0)
                r29.writeNoException()
                return r7
            L_0x086a:
                r13.enforceInterface(r8)
                android.os.IBinder r0 = r28.readStrongBinder()
                android.os.IBinder r1 = r14.requestStartActivityPermissionToken(r0)
                r29.writeNoException()
                r11.writeStrongBinder(r1)
                return r7
            L_0x087c:
                r13.enforceInterface(r8)
                android.os.IBinder r0 = r28.readStrongBinder()
                boolean r1 = r14.releaseActivityInstance(r0)
                r29.writeNoException()
                r11.writeInt(r1)
                return r7
            L_0x088e:
                r13.enforceInterface(r8)
                android.graphics.Point r0 = r26.getAppTaskThumbnailSize()
                r29.writeNoException()
                if (r0 == 0) goto L_0x08a1
                r11.writeInt(r7)
                r0.writeToParcel(r11, r7)
                goto L_0x08a4
            L_0x08a1:
                r11.writeInt(r12)
            L_0x08a4:
                return r7
            L_0x08a5:
                r13.enforceInterface(r8)
                android.os.IBinder r1 = r28.readStrongBinder()
                int r2 = r28.readInt()
                if (r2 == 0) goto L_0x08bb
                android.os.Parcelable$Creator<android.content.Intent> r2 = android.content.Intent.CREATOR
                java.lang.Object r2 = r2.createFromParcel(r13)
                android.content.Intent r2 = (android.content.Intent) r2
                goto L_0x08bc
            L_0x08bb:
                r2 = r0
            L_0x08bc:
                int r3 = r28.readInt()
                if (r3 == 0) goto L_0x08cb
                android.os.Parcelable$Creator<android.app.ActivityManager$TaskDescription> r3 = android.app.ActivityManager.TaskDescription.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r13)
                android.app.ActivityManager$TaskDescription r3 = (android.app.ActivityManager.TaskDescription) r3
                goto L_0x08cc
            L_0x08cb:
                r3 = r0
            L_0x08cc:
                int r4 = r28.readInt()
                if (r4 == 0) goto L_0x08db
                android.os.Parcelable$Creator<android.graphics.Bitmap> r0 = android.graphics.Bitmap.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.graphics.Bitmap r0 = (android.graphics.Bitmap) r0
                goto L_0x08dc
            L_0x08db:
            L_0x08dc:
                int r4 = r14.addAppTask(r1, r2, r3, r0)
                r29.writeNoException()
                r11.writeInt(r4)
                return r7
            L_0x08e7:
                r13.enforceInterface(r8)
                android.os.IBinder r0 = r28.readStrongBinder()
                r14.notifyEnterAnimationComplete(r0)
                r29.writeNoException()
                return r7
            L_0x08f5:
                r13.enforceInterface(r8)
                android.os.IBinder r0 = r28.readStrongBinder()
                r14.notifyLaunchTaskBehindComplete(r0)
                r29.writeNoException()
                return r7
            L_0x0903:
                r13.enforceInterface(r8)
                android.os.IBinder r0 = r28.readStrongBinder()
                boolean r1 = r14.isTopOfTask(r0)
                r29.writeNoException()
                r11.writeInt(r1)
                return r7
            L_0x0915:
                r13.enforceInterface(r8)
                android.os.IBinder r0 = r28.readStrongBinder()
                android.service.voice.IVoiceInteractionSession r0 = android.service.voice.IVoiceInteractionSession.Stub.asInterface(r0)
                r14.finishVoiceTask(r0)
                r29.writeNoException()
                return r7
            L_0x0927:
                r13.enforceInterface(r8)
                r26.stopSystemLockTaskMode()
                r29.writeNoException()
                return r7
            L_0x0931:
                r13.enforceInterface(r8)
                int r0 = r28.readInt()
                r14.startSystemLockTaskMode(r0)
                r29.writeNoException()
                return r7
            L_0x093f:
                r13.enforceInterface(r8)
                java.lang.String r0 = r28.readString()
                java.util.List r1 = r14.getAppTasks(r0)
                r29.writeNoException()
                r11.writeBinderList(r1)
                return r7
            L_0x0951:
                r13.enforceInterface(r8)
                android.os.IBinder r0 = r28.readStrongBinder()
                android.os.Bundle r1 = r14.getActivityOptions(r0)
                r29.writeNoException()
                if (r1 == 0) goto L_0x0968
                r11.writeInt(r7)
                r1.writeToParcel(r11, r7)
                goto L_0x096b
            L_0x0968:
                r11.writeInt(r12)
            L_0x096b:
                return r7
            L_0x096c:
                r13.enforceInterface(r8)
                android.os.IBinder r1 = r28.readStrongBinder()
                int r2 = r28.readInt()
                if (r2 == 0) goto L_0x0982
                android.os.Parcelable$Creator<android.app.ActivityManager$TaskDescription> r0 = android.app.ActivityManager.TaskDescription.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.app.ActivityManager$TaskDescription r0 = (android.app.ActivityManager.TaskDescription) r0
                goto L_0x0983
            L_0x0982:
            L_0x0983:
                r14.setTaskDescription(r1, r0)
                r29.writeNoException()
                return r7
            L_0x098a:
                r13.enforceInterface(r8)
                int r0 = r26.getLockTaskModeState()
                r29.writeNoException()
                r11.writeInt(r0)
                return r7
            L_0x0998:
                r13.enforceInterface(r8)
                boolean r0 = r26.isInLockTaskMode()
                r29.writeNoException()
                r11.writeInt(r0)
                return r7
            L_0x09a6:
                r13.enforceInterface(r8)
                int r0 = r28.readInt()
                java.lang.String[] r1 = r28.createStringArray()
                r14.updateLockTaskPackages(r0, r1)
                r29.writeNoException()
                return r7
            L_0x09b8:
                r13.enforceInterface(r8)
                android.os.IBinder r0 = r28.readStrongBinder()
                r14.stopLockTaskModeByToken(r0)
                r29.writeNoException()
                return r7
            L_0x09c6:
                r13.enforceInterface(r8)
                android.os.IBinder r0 = r28.readStrongBinder()
                r14.startLockTaskModeByToken(r0)
                r29.writeNoException()
                return r7
            L_0x09d4:
                r13.enforceInterface(r8)
                int r0 = r28.readInt()
                if (r0 == 0) goto L_0x09df
                r12 = r7
            L_0x09df:
                r0 = r12
                r14.cancelRecentsAnimation(r0)
                r29.writeNoException()
                return r7
            L_0x09e7:
                r13.enforceInterface(r8)
                int r0 = r28.readInt()
                android.graphics.Rect r1 = r14.getTaskBounds(r0)
                r29.writeNoException()
                if (r1 == 0) goto L_0x09fe
                r11.writeInt(r7)
                r1.writeToParcel(r11, r7)
                goto L_0x0a01
            L_0x09fe:
                r11.writeInt(r12)
            L_0x0a01:
                return r7
            L_0x0a02:
                r13.enforceInterface(r8)
                android.app.ActivityManager$StackInfo r0 = r26.getFocusedStackInfo()
                r29.writeNoException()
                if (r0 == 0) goto L_0x0a15
                r11.writeInt(r7)
                r0.writeToParcel(r11, r7)
                goto L_0x0a18
            L_0x0a15:
                r11.writeInt(r12)
            L_0x0a18:
                return r7
            L_0x0a19:
                r13.enforceInterface(r8)
                int r0 = r28.readInt()
                r14.setFocusedStack(r0)
                r29.writeNoException()
                return r7
            L_0x0a27:
                r13.enforceInterface(r8)
                android.os.IBinder r6 = r28.readStrongBinder()
                int r1 = r28.readInt()
                if (r1 == 0) goto L_0x0a3e
                android.os.Parcelable$Creator<android.os.Bundle> r1 = android.os.Bundle.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r13)
                android.os.Bundle r1 = (android.os.Bundle) r1
                r2 = r1
                goto L_0x0a3f
            L_0x0a3e:
                r2 = r0
            L_0x0a3f:
                int r1 = r28.readInt()
                if (r1 == 0) goto L_0x0a4f
                android.os.Parcelable$Creator<android.app.assist.AssistStructure> r1 = android.app.assist.AssistStructure.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r13)
                android.app.assist.AssistStructure r1 = (android.app.assist.AssistStructure) r1
                r3 = r1
                goto L_0x0a50
            L_0x0a4f:
                r3 = r0
            L_0x0a50:
                int r1 = r28.readInt()
                if (r1 == 0) goto L_0x0a60
                android.os.Parcelable$Creator<android.app.assist.AssistContent> r1 = android.app.assist.AssistContent.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r13)
                android.app.assist.AssistContent r1 = (android.app.assist.AssistContent) r1
                r4 = r1
                goto L_0x0a61
            L_0x0a60:
                r4 = r0
            L_0x0a61:
                int r1 = r28.readInt()
                if (r1 == 0) goto L_0x0a71
                android.os.Parcelable$Creator<android.net.Uri> r0 = android.net.Uri.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.net.Uri r0 = (android.net.Uri) r0
            L_0x0a6f:
                r5 = r0
                goto L_0x0a72
            L_0x0a71:
                goto L_0x0a6f
            L_0x0a72:
                r0 = r26
                r1 = r6
                r0.reportAssistContextExtras(r1, r2, r3, r4, r5)
                r29.writeNoException()
                return r7
            L_0x0a7c:
                r13.enforceInterface(r8)
                android.os.IBinder r0 = r28.readStrongBinder()
                java.lang.String r1 = r14.getLaunchedFromPackage(r0)
                r29.writeNoException()
                r11.writeString(r1)
                return r7
            L_0x0a8e:
                r13.enforceInterface(r8)
                android.os.IBinder r0 = r28.readStrongBinder()
                int r1 = r14.getLaunchedFromUid(r0)
                r29.writeNoException()
                r11.writeInt(r1)
                return r7
            L_0x0aa0:
                r13.enforceInterface(r8)
                android.os.IBinder r0 = r28.readStrongBinder()
                java.lang.String r1 = r28.readString()
                int r2 = r28.readInt()
                int r3 = r28.readInt()
                r14.overridePendingTransition(r0, r1, r2, r3)
                r29.writeNoException()
                return r7
            L_0x0aba:
                r13.enforceInterface(r8)
                int r0 = r28.readInt()
                android.app.ActivityManager$TaskDescription r1 = r14.getTaskDescription(r0)
                r29.writeNoException()
                if (r1 == 0) goto L_0x0ad1
                r11.writeInt(r7)
                r1.writeToParcel(r11, r7)
                goto L_0x0ad4
            L_0x0ad1:
                r11.writeInt(r12)
            L_0x0ad4:
                return r7
            L_0x0ad5:
                r13.enforceInterface(r8)
                android.os.IBinder r0 = r28.readStrongBinder()
                int r1 = r28.readInt()
                if (r1 == 0) goto L_0x0ae4
                r12 = r7
            L_0x0ae4:
                r1 = r12
                boolean r2 = r14.moveActivityTaskToBack(r0, r1)
                r29.writeNoException()
                r11.writeInt(r2)
                return r7
            L_0x0af0:
                r13.enforceInterface(r8)
                boolean r0 = r26.isTopActivityImmersive()
                r29.writeNoException()
                r11.writeInt(r0)
                return r7
            L_0x0afe:
                r13.enforceInterface(r8)
                android.os.IBinder r0 = r28.readStrongBinder()
                int r1 = r28.readInt()
                if (r1 == 0) goto L_0x0b0d
                r12 = r7
            L_0x0b0d:
                r1 = r12
                r14.setImmersive(r0, r1)
                r29.writeNoException()
                return r7
            L_0x0b15:
                r13.enforceInterface(r8)
                android.os.IBinder r0 = r28.readStrongBinder()
                boolean r1 = r14.isImmersive(r0)
                r29.writeNoException()
                r11.writeInt(r1)
                return r7
            L_0x0b27:
                r13.enforceInterface(r8)
                android.os.IBinder r0 = r28.readStrongBinder()
                int r1 = r14.getActivityDisplayId(r0)
                r29.writeNoException()
                r11.writeInt(r1)
                return r7
            L_0x0b39:
                r13.enforceInterface(r8)
                android.os.IBinder r0 = r28.readStrongBinder()
                int r1 = r28.readInt()
                if (r1 == 0) goto L_0x0b48
                r12 = r7
            L_0x0b48:
                r1 = r12
                r14.reportActivityFullyDrawn(r0, r1)
                r29.writeNoException()
                return r7
            L_0x0b50:
                r13.enforceInterface(r8)
                android.os.IBinder r0 = r28.readStrongBinder()
                r14.notifyActivityDrawn(r0)
                r29.writeNoException()
                return r7
            L_0x0b5e:
                r13.enforceInterface(r8)
                android.os.IBinder r1 = r28.readStrongBinder()
                int r2 = r28.readInt()
                if (r2 == 0) goto L_0x0b74
                android.os.Parcelable$Creator<android.os.Bundle> r0 = android.os.Bundle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.os.Bundle r0 = (android.os.Bundle) r0
                goto L_0x0b75
            L_0x0b74:
            L_0x0b75:
                boolean r2 = r14.convertToTranslucent(r1, r0)
                r29.writeNoException()
                r11.writeInt(r2)
                return r7
            L_0x0b80:
                r13.enforceInterface(r8)
                android.os.IBinder r0 = r28.readStrongBinder()
                boolean r1 = r14.convertFromTranslucent(r0)
                r29.writeNoException()
                r11.writeInt(r1)
                return r7
            L_0x0b92:
                r13.enforceInterface(r8)
                android.os.IBinder r0 = r28.readStrongBinder()
                int r1 = r14.getRequestedOrientation(r0)
                r29.writeNoException()
                r11.writeInt(r1)
                return r7
            L_0x0ba4:
                r13.enforceInterface(r8)
                android.os.IBinder r0 = r28.readStrongBinder()
                int r1 = r28.readInt()
                r14.setRequestedOrientation(r0, r1)
                r29.writeNoException()
                return r7
            L_0x0bb6:
                r13.enforceInterface(r8)
                android.os.IBinder r0 = r28.readStrongBinder()
                boolean r1 = r14.willActivityBeVisible(r0)
                r29.writeNoException()
                r11.writeInt(r1)
                return r7
            L_0x0bc8:
                r13.enforceInterface(r8)
                int r0 = r28.readInt()
                int r1 = r28.readInt()
                int r2 = r28.readInt()
                android.content.pm.ParceledListSlice r3 = r14.getRecentTasks(r0, r1, r2)
                r29.writeNoException()
                if (r3 == 0) goto L_0x0be7
                r11.writeInt(r7)
                r3.writeToParcel(r11, r7)
                goto L_0x0bea
            L_0x0be7:
                r11.writeInt(r12)
            L_0x0bea:
                return r7
            L_0x0beb:
                r13.enforceInterface(r8)
                android.os.IBinder r0 = r28.readStrongBinder()
                java.lang.String r1 = r28.readString()
                int r2 = r28.readInt()
                r14.finishSubActivity(r0, r1, r2)
                r29.writeNoException()
                return r7
            L_0x0c01:
                r13.enforceInterface(r8)
                android.os.IBinder r0 = r28.readStrongBinder()
                int r1 = r28.readInt()
                if (r1 == 0) goto L_0x0c10
                r12 = r7
            L_0x0c10:
                r1 = r12
                int r2 = r14.getTaskForActivity(r0, r1)
                r29.writeNoException()
                r11.writeInt(r2)
                return r7
            L_0x0c1c:
                r13.enforceInterface(r8)
                android.os.IBinder r1 = r28.readStrongBinder()
                android.app.IApplicationThread r6 = android.app.IApplicationThread.Stub.asInterface(r1)
                java.lang.String r9 = r28.readString()
                int r10 = r28.readInt()
                int r12 = r28.readInt()
                int r1 = r28.readInt()
                if (r1 == 0) goto L_0x0c43
                android.os.Parcelable$Creator<android.os.Bundle> r0 = android.os.Bundle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.os.Bundle r0 = (android.os.Bundle) r0
            L_0x0c41:
                r5 = r0
                goto L_0x0c44
            L_0x0c43:
                goto L_0x0c41
            L_0x0c44:
                r0 = r26
                r1 = r6
                r2 = r9
                r3 = r10
                r4 = r12
                r0.moveTaskToFront(r1, r2, r3, r4, r5)
                r29.writeNoException()
                return r7
            L_0x0c51:
                r13.enforceInterface(r8)
                android.os.IBinder r1 = r28.readStrongBinder()
                int r2 = r28.readInt()
                if (r2 == 0) goto L_0x0c67
                android.os.Parcelable$Creator<android.content.Intent> r2 = android.content.Intent.CREATOR
                java.lang.Object r2 = r2.createFromParcel(r13)
                android.content.Intent r2 = (android.content.Intent) r2
                goto L_0x0c68
            L_0x0c67:
                r2 = r0
            L_0x0c68:
                int r3 = r28.readInt()
                int r4 = r28.readInt()
                if (r4 == 0) goto L_0x0c7b
                android.os.Parcelable$Creator<android.content.Intent> r0 = android.content.Intent.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.content.Intent r0 = (android.content.Intent) r0
                goto L_0x0c7c
            L_0x0c7b:
            L_0x0c7c:
                boolean r4 = r14.navigateUpTo(r1, r2, r3, r0)
                r29.writeNoException()
                r11.writeInt(r4)
                return r7
            L_0x0c87:
                r13.enforceInterface(r8)
                android.os.IBinder r0 = r28.readStrongBinder()
                java.lang.String r1 = r28.readString()
                boolean r2 = r14.shouldUpRecreateTask(r0, r1)
                r29.writeNoException()
                r11.writeInt(r2)
                return r7
            L_0x0c9d:
                r13.enforceInterface(r8)
                int r0 = r28.readInt()
                int r1 = r28.readInt()
                int r2 = r28.readInt()
                java.util.List r3 = r14.getFilteredTasks(r0, r1, r2)
                r29.writeNoException()
                r11.writeTypedList(r3)
                return r7
            L_0x0cb7:
                r13.enforceInterface(r8)
                int r0 = r28.readInt()
                java.util.List r1 = r14.getTasks(r0)
                r29.writeNoException()
                r11.writeTypedList(r1)
                return r7
            L_0x0cc9:
                r13.enforceInterface(r8)
                r26.removeAllVisibleRecentTasks()
                r29.writeNoException()
                return r7
            L_0x0cd3:
                r13.enforceInterface(r8)
                int r0 = r28.readInt()
                boolean r1 = r14.removeTask(r0)
                r29.writeNoException()
                r11.writeInt(r1)
                return r7
            L_0x0ce5:
                r13.enforceInterface(r8)
                int r0 = r28.readInt()
                r14.setFocusedTask(r0)
                r29.writeNoException()
                return r7
            L_0x0cf3:
                r13.enforceInterface(r8)
                android.os.IBinder r0 = r28.readStrongBinder()
                android.content.ComponentName r1 = r14.getCallingActivity(r0)
                r29.writeNoException()
                if (r1 == 0) goto L_0x0d0a
                r11.writeInt(r7)
                r1.writeToParcel((android.os.Parcel) r11, (int) r7)
                goto L_0x0d0d
            L_0x0d0a:
                r11.writeInt(r12)
            L_0x0d0d:
                return r7
            L_0x0d0e:
                r13.enforceInterface(r8)
                android.os.IBinder r0 = r28.readStrongBinder()
                java.lang.String r1 = r14.getCallingPackage(r0)
                r29.writeNoException()
                r11.writeString(r1)
                return r7
            L_0x0d20:
                r13.enforceInterface(r8)
                int r0 = r28.readInt()
                r14.setFrontActivityScreenCompatMode(r0)
                r29.writeNoException()
                return r7
            L_0x0d2e:
                r13.enforceInterface(r8)
                int r0 = r26.getFrontActivityScreenCompatMode()
                r29.writeNoException()
                r11.writeInt(r0)
                return r7
            L_0x0d3c:
                r13.enforceInterface(r8)
                android.os.IBinder r0 = r28.readStrongBinder()
                r14.activitySlept(r0)
                return r7
            L_0x0d47:
                r13.enforceInterface(r8)
                android.os.IBinder r0 = r28.readStrongBinder()
                r14.activityRelaunched(r0)
                r29.writeNoException()
                return r7
            L_0x0d55:
                r13.enforceInterface(r8)
                android.os.IBinder r0 = r28.readStrongBinder()
                r14.activityDestroyed(r0)
                return r7
            L_0x0d60:
                r13.enforceInterface(r8)
                android.os.IBinder r1 = r28.readStrongBinder()
                int r2 = r28.readInt()
                if (r2 == 0) goto L_0x0d76
                android.os.Parcelable$Creator<android.os.Bundle> r2 = android.os.Bundle.CREATOR
                java.lang.Object r2 = r2.createFromParcel(r13)
                android.os.Bundle r2 = (android.os.Bundle) r2
                goto L_0x0d77
            L_0x0d76:
                r2 = r0
            L_0x0d77:
                int r3 = r28.readInt()
                if (r3 == 0) goto L_0x0d86
                android.os.Parcelable$Creator<android.os.PersistableBundle> r3 = android.os.PersistableBundle.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r13)
                android.os.PersistableBundle r3 = (android.os.PersistableBundle) r3
                goto L_0x0d87
            L_0x0d86:
                r3 = r0
            L_0x0d87:
                int r4 = r28.readInt()
                if (r4 == 0) goto L_0x0d96
                android.os.Parcelable$Creator<java.lang.CharSequence> r0 = android.text.TextUtils.CHAR_SEQUENCE_CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                java.lang.CharSequence r0 = (java.lang.CharSequence) r0
                goto L_0x0d97
            L_0x0d96:
            L_0x0d97:
                r14.activityStopped(r1, r2, r3, r0)
                r29.writeNoException()
                return r7
            L_0x0d9e:
                r13.enforceInterface(r8)
                android.os.IBinder r0 = r28.readStrongBinder()
                r14.activityPaused(r0)
                r29.writeNoException()
                return r7
            L_0x0dac:
                r13.enforceInterface(r8)
                r26.activityTopResumedStateLost()
                r29.writeNoException()
                return r7
            L_0x0db6:
                r13.enforceInterface(r8)
                android.os.IBinder r0 = r28.readStrongBinder()
                r14.activityResumed(r0)
                r29.writeNoException()
                return r7
            L_0x0dc4:
                r13.enforceInterface(r8)
                android.os.IBinder r1 = r28.readStrongBinder()
                int r2 = r28.readInt()
                if (r2 == 0) goto L_0x0dda
                android.os.Parcelable$Creator<android.content.res.Configuration> r0 = android.content.res.Configuration.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.content.res.Configuration r0 = (android.content.res.Configuration) r0
                goto L_0x0ddb
            L_0x0dda:
            L_0x0ddb:
                int r2 = r28.readInt()
                if (r2 == 0) goto L_0x0de3
                r12 = r7
            L_0x0de3:
                r2 = r12
                r14.activityIdle(r1, r0, r2)
                return r7
            L_0x0de8:
                r13.enforceInterface(r8)
                android.os.IBinder r0 = r28.readStrongBinder()
                boolean r1 = r14.finishActivityAffinity(r0)
                r29.writeNoException()
                r11.writeInt(r1)
                return r7
            L_0x0dfa:
                r13.enforceInterface(r8)
                android.os.IBinder r1 = r28.readStrongBinder()
                int r2 = r28.readInt()
                int r3 = r28.readInt()
                if (r3 == 0) goto L_0x0e14
                android.os.Parcelable$Creator<android.content.Intent> r0 = android.content.Intent.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.content.Intent r0 = (android.content.Intent) r0
                goto L_0x0e15
            L_0x0e14:
            L_0x0e15:
                int r3 = r28.readInt()
                boolean r4 = r14.finishActivity(r1, r2, r0, r3)
                r29.writeNoException()
                r11.writeInt(r4)
                return r7
            L_0x0e24:
                r13.enforceInterface(r8)
                r26.unhandledBack()
                r29.writeNoException()
                return r7
            L_0x0e2e:
                r13.enforceInterface(r8)
                int r1 = r28.readInt()
                int r2 = r28.readInt()
                if (r2 == 0) goto L_0x0e44
                android.os.Parcelable$Creator<android.content.Intent> r0 = android.content.Intent.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.content.Intent r0 = (android.content.Intent) r0
                goto L_0x0e45
            L_0x0e44:
            L_0x0e45:
                java.lang.String r2 = r28.readString()
                int r3 = r28.readInt()
                boolean r4 = r14.isActivityStartAllowedOnDisplay(r1, r0, r2, r3)
                r29.writeNoException()
                r11.writeInt(r4)
                return r7
            L_0x0e58:
                r13.enforceInterface(r8)
                android.os.IBinder r1 = r28.readStrongBinder()
                android.app.IApplicationThread r16 = android.app.IApplicationThread.Stub.asInterface(r1)
                java.lang.String r17 = r28.readString()
                int r1 = r28.readInt()
                if (r1 == 0) goto L_0x0e77
                android.os.Parcelable$Creator<android.content.Intent> r1 = android.content.Intent.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r13)
                android.content.Intent r1 = (android.content.Intent) r1
                r3 = r1
                goto L_0x0e78
            L_0x0e77:
                r3 = r0
            L_0x0e78:
                java.lang.String r18 = r28.readString()
                android.os.IBinder r19 = r28.readStrongBinder()
                java.lang.String r20 = r28.readString()
                int r21 = r28.readInt()
                int r22 = r28.readInt()
                int r1 = r28.readInt()
                if (r1 == 0) goto L_0x0e9c
                android.os.Parcelable$Creator<android.app.ProfilerInfo> r1 = android.app.ProfilerInfo.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r13)
                android.app.ProfilerInfo r1 = (android.app.ProfilerInfo) r1
                r9 = r1
                goto L_0x0e9d
            L_0x0e9c:
                r9 = r0
            L_0x0e9d:
                int r1 = r28.readInt()
                if (r1 == 0) goto L_0x0ead
                android.os.Parcelable$Creator<android.os.Bundle> r0 = android.os.Bundle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.os.Bundle r0 = (android.os.Bundle) r0
            L_0x0eab:
                r10 = r0
                goto L_0x0eae
            L_0x0ead:
                goto L_0x0eab
            L_0x0eae:
                android.os.IBinder r23 = r28.readStrongBinder()
                int r0 = r28.readInt()
                if (r0 == 0) goto L_0x0eba
                r12 = r7
            L_0x0eba:
                int r24 = r28.readInt()
                r0 = r26
                r1 = r16
                r2 = r17
                r4 = r18
                r5 = r19
                r6 = r20
                r15 = r7
                r7 = r21
                r25 = r8
                r8 = r22
                r15 = r11
                r11 = r23
                r14 = r13
                r13 = r24
                int r0 = r0.startActivityAsCaller(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13)
                r29.writeNoException()
                r15.writeInt(r0)
                r1 = 1
                return r1
            L_0x0ee3:
                r25 = r8
                r15 = r11
                r14 = r13
                r13 = r25
                r14.enforceInterface(r13)
                int r1 = r28.readInt()
                int r2 = r28.readInt()
                if (r2 == 0) goto L_0x0eff
                android.os.Parcelable$Creator<android.os.Bundle> r0 = android.os.Bundle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r14)
                android.os.Bundle r0 = (android.os.Bundle) r0
                goto L_0x0f00
            L_0x0eff:
            L_0x0f00:
                r12 = r14
                r14 = r26
                int r2 = r14.startActivityFromRecents(r1, r0)
                r29.writeNoException()
                r15.writeInt(r2)
                r3 = 1
                return r3
            L_0x0f0f:
                r15 = r11
                r12 = r13
                r13 = r8
                r12.enforceInterface(r13)
                int r1 = r28.readInt()
                if (r1 == 0) goto L_0x0f24
                android.os.Parcelable$Creator<android.content.Intent> r0 = android.content.Intent.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r12)
                android.content.Intent r0 = (android.content.Intent) r0
                goto L_0x0f25
            L_0x0f24:
            L_0x0f25:
                android.os.IBinder r1 = r28.readStrongBinder()
                android.app.IAssistDataReceiver r1 = android.app.IAssistDataReceiver.Stub.asInterface(r1)
                android.os.IBinder r2 = r28.readStrongBinder()
                android.view.IRecentsAnimationRunner r2 = android.view.IRecentsAnimationRunner.Stub.asInterface(r2)
                r14.startRecentsActivity(r0, r1, r2)
                r29.writeNoException()
                r3 = 1
                return r3
            L_0x0f3d:
                r15 = r11
                r12 = r13
                r13 = r8
                r12.enforceInterface(r13)
                java.lang.String r8 = r28.readString()
                int r9 = r28.readInt()
                int r10 = r28.readInt()
                int r1 = r28.readInt()
                if (r1 == 0) goto L_0x0f5f
                android.os.Parcelable$Creator<android.content.Intent> r1 = android.content.Intent.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r12)
                android.content.Intent r1 = (android.content.Intent) r1
                r4 = r1
                goto L_0x0f60
            L_0x0f5f:
                r4 = r0
            L_0x0f60:
                java.lang.String r11 = r28.readString()
                int r1 = r28.readInt()
                if (r1 == 0) goto L_0x0f74
                android.os.Parcelable$Creator<android.os.Bundle> r0 = android.os.Bundle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r12)
                android.os.Bundle r0 = (android.os.Bundle) r0
            L_0x0f72:
                r6 = r0
                goto L_0x0f75
            L_0x0f74:
                goto L_0x0f72
            L_0x0f75:
                int r16 = r28.readInt()
                r0 = r26
                r1 = r8
                r2 = r9
                r3 = r10
                r5 = r11
                r7 = r16
                int r0 = r0.startAssistantActivity(r1, r2, r3, r4, r5, r6, r7)
                r29.writeNoException()
                r15.writeInt(r0)
                r1 = 1
                return r1
            L_0x0f8d:
                r15 = r11
                r12 = r13
                r13 = r8
                r12.enforceInterface(r13)
                java.lang.String r16 = r28.readString()
                int r17 = r28.readInt()
                int r18 = r28.readInt()
                int r1 = r28.readInt()
                if (r1 == 0) goto L_0x0faf
                android.os.Parcelable$Creator<android.content.Intent> r1 = android.content.Intent.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r12)
                android.content.Intent r1 = (android.content.Intent) r1
                r4 = r1
                goto L_0x0fb0
            L_0x0faf:
                r4 = r0
            L_0x0fb0:
                java.lang.String r19 = r28.readString()
                android.os.IBinder r1 = r28.readStrongBinder()
                android.service.voice.IVoiceInteractionSession r20 = android.service.voice.IVoiceInteractionSession.Stub.asInterface(r1)
                android.os.IBinder r1 = r28.readStrongBinder()
                com.android.internal.app.IVoiceInteractor r21 = com.android.internal.app.IVoiceInteractor.Stub.asInterface(r1)
                int r22 = r28.readInt()
                int r1 = r28.readInt()
                if (r1 == 0) goto L_0x0fd8
                android.os.Parcelable$Creator<android.app.ProfilerInfo> r1 = android.app.ProfilerInfo.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r12)
                android.app.ProfilerInfo r1 = (android.app.ProfilerInfo) r1
                r9 = r1
                goto L_0x0fd9
            L_0x0fd8:
                r9 = r0
            L_0x0fd9:
                int r1 = r28.readInt()
                if (r1 == 0) goto L_0x0fe9
                android.os.Parcelable$Creator<android.os.Bundle> r0 = android.os.Bundle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r12)
                android.os.Bundle r0 = (android.os.Bundle) r0
            L_0x0fe7:
                r10 = r0
                goto L_0x0fea
            L_0x0fe9:
                goto L_0x0fe7
            L_0x0fea:
                int r23 = r28.readInt()
                r0 = r26
                r1 = r16
                r2 = r17
                r3 = r18
                r5 = r19
                r6 = r20
                r7 = r21
                r8 = r22
                r11 = r23
                int r0 = r0.startVoiceActivity(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11)
                r29.writeNoException()
                r15.writeInt(r0)
                r1 = 1
                return r1
            L_0x100c:
                r15 = r11
                r12 = r13
                r13 = r8
                r12.enforceInterface(r13)
                android.os.IBinder r1 = r28.readStrongBinder()
                android.app.IApplicationThread r16 = android.app.IApplicationThread.Stub.asInterface(r1)
                java.lang.String r17 = r28.readString()
                int r1 = r28.readInt()
                if (r1 == 0) goto L_0x102e
                android.os.Parcelable$Creator<android.content.Intent> r1 = android.content.Intent.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r12)
                android.content.Intent r1 = (android.content.Intent) r1
                r3 = r1
                goto L_0x102f
            L_0x102e:
                r3 = r0
            L_0x102f:
                java.lang.String r18 = r28.readString()
                android.os.IBinder r19 = r28.readStrongBinder()
                java.lang.String r20 = r28.readString()
                int r21 = r28.readInt()
                int r22 = r28.readInt()
                int r1 = r28.readInt()
                if (r1 == 0) goto L_0x1053
                android.os.Parcelable$Creator<android.content.res.Configuration> r1 = android.content.res.Configuration.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r12)
                android.content.res.Configuration r1 = (android.content.res.Configuration) r1
                r9 = r1
                goto L_0x1054
            L_0x1053:
                r9 = r0
            L_0x1054:
                int r1 = r28.readInt()
                if (r1 == 0) goto L_0x1064
                android.os.Parcelable$Creator<android.os.Bundle> r0 = android.os.Bundle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r12)
                android.os.Bundle r0 = (android.os.Bundle) r0
            L_0x1062:
                r10 = r0
                goto L_0x1065
            L_0x1064:
                goto L_0x1062
            L_0x1065:
                int r23 = r28.readInt()
                r0 = r26
                r1 = r16
                r2 = r17
                r4 = r18
                r5 = r19
                r6 = r20
                r7 = r21
                r8 = r22
                r11 = r23
                int r0 = r0.startActivityWithConfig(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11)
                r29.writeNoException()
                r15.writeInt(r0)
                r1 = 1
                return r1
            L_0x1087:
                r15 = r11
                r11 = r13
                r13 = r8
                r11.enforceInterface(r13)
                android.os.IBinder r1 = r28.readStrongBinder()
                android.app.IApplicationThread r16 = android.app.IApplicationThread.Stub.asInterface(r1)
                java.lang.String r17 = r28.readString()
                int r1 = r28.readInt()
                if (r1 == 0) goto L_0x10a9
                android.os.Parcelable$Creator<android.content.Intent> r1 = android.content.Intent.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r11)
                android.content.Intent r1 = (android.content.Intent) r1
                r3 = r1
                goto L_0x10aa
            L_0x10a9:
                r3 = r0
            L_0x10aa:
                java.lang.String r18 = r28.readString()
                android.os.IBinder r19 = r28.readStrongBinder()
                java.lang.String r20 = r28.readString()
                int r21 = r28.readInt()
                int r22 = r28.readInt()
                int r1 = r28.readInt()
                if (r1 == 0) goto L_0x10ce
                android.os.Parcelable$Creator<android.app.ProfilerInfo> r1 = android.app.ProfilerInfo.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r11)
                android.app.ProfilerInfo r1 = (android.app.ProfilerInfo) r1
                r9 = r1
                goto L_0x10cf
            L_0x10ce:
                r9 = r0
            L_0x10cf:
                int r1 = r28.readInt()
                if (r1 == 0) goto L_0x10df
                android.os.Parcelable$Creator<android.os.Bundle> r0 = android.os.Bundle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r11)
                android.os.Bundle r0 = (android.os.Bundle) r0
            L_0x10dd:
                r10 = r0
                goto L_0x10e0
            L_0x10df:
                goto L_0x10dd
            L_0x10e0:
                int r23 = r28.readInt()
                r0 = r26
                r1 = r16
                r2 = r17
                r4 = r18
                r5 = r19
                r6 = r20
                r7 = r21
                r8 = r22
                r11 = r23
                android.app.WaitResult r0 = r0.startActivityAndWait(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11)
                r29.writeNoException()
                if (r0 == 0) goto L_0x1107
                r1 = 1
                r15.writeInt(r1)
                r0.writeToParcel(r15, r1)
                goto L_0x110b
            L_0x1107:
                r1 = 1
                r15.writeInt(r12)
            L_0x110b:
                return r1
            L_0x110c:
                r13 = r8
                r15 = r11
                r12 = r28
                r12.enforceInterface(r13)
                android.os.IBinder r1 = r28.readStrongBinder()
                android.app.IApplicationThread r16 = android.app.IApplicationThread.Stub.asInterface(r1)
                android.os.IBinder r1 = r28.readStrongBinder()
                android.content.IIntentSender r17 = android.content.IIntentSender.Stub.asInterface(r1)
                android.os.IBinder r18 = r28.readStrongBinder()
                int r1 = r28.readInt()
                if (r1 == 0) goto L_0x1137
                android.os.Parcelable$Creator<android.content.Intent> r1 = android.content.Intent.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r12)
                android.content.Intent r1 = (android.content.Intent) r1
                r4 = r1
                goto L_0x1138
            L_0x1137:
                r4 = r0
            L_0x1138:
                java.lang.String r19 = r28.readString()
                android.os.IBinder r20 = r28.readStrongBinder()
                java.lang.String r21 = r28.readString()
                int r22 = r28.readInt()
                int r23 = r28.readInt()
                int r24 = r28.readInt()
                int r1 = r28.readInt()
                if (r1 == 0) goto L_0x1160
                android.os.Parcelable$Creator<android.os.Bundle> r0 = android.os.Bundle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r12)
                android.os.Bundle r0 = (android.os.Bundle) r0
            L_0x115e:
                r11 = r0
                goto L_0x1161
            L_0x1160:
                goto L_0x115e
            L_0x1161:
                r0 = r26
                r1 = r16
                r2 = r17
                r3 = r18
                r5 = r19
                r6 = r20
                r7 = r21
                r8 = r22
                r9 = r23
                r10 = r24
                int r0 = r0.startActivityIntentSender(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11)
                r29.writeNoException()
                r15.writeInt(r0)
                r1 = 1
                return r1
            L_0x1181:
                r15 = r11
                r12 = r13
                r13 = r8
                r12.enforceInterface(r13)
                android.os.IBinder r1 = r28.readStrongBinder()
                int r2 = r28.readInt()
                if (r2 == 0) goto L_0x119a
                android.os.Parcelable$Creator<android.content.Intent> r2 = android.content.Intent.CREATOR
                java.lang.Object r2 = r2.createFromParcel(r12)
                android.content.Intent r2 = (android.content.Intent) r2
                goto L_0x119b
            L_0x119a:
                r2 = r0
            L_0x119b:
                int r3 = r28.readInt()
                if (r3 == 0) goto L_0x11aa
                android.os.Parcelable$Creator<android.os.Bundle> r0 = android.os.Bundle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r12)
                android.os.Bundle r0 = (android.os.Bundle) r0
                goto L_0x11ab
            L_0x11aa:
            L_0x11ab:
                boolean r3 = r14.startNextMatchingActivity(r1, r2, r0)
                r29.writeNoException()
                r15.writeInt(r3)
                r4 = 1
                return r4
            L_0x11b7:
                r15 = r11
                r12 = r13
                r13 = r8
                r12.enforceInterface(r13)
                android.os.IBinder r1 = r28.readStrongBinder()
                android.app.IApplicationThread r16 = android.app.IApplicationThread.Stub.asInterface(r1)
                java.lang.String r17 = r28.readString()
                int r1 = r28.readInt()
                if (r1 == 0) goto L_0x11d9
                android.os.Parcelable$Creator<android.content.Intent> r1 = android.content.Intent.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r12)
                android.content.Intent r1 = (android.content.Intent) r1
                r3 = r1
                goto L_0x11da
            L_0x11d9:
                r3 = r0
            L_0x11da:
                java.lang.String r18 = r28.readString()
                android.os.IBinder r19 = r28.readStrongBinder()
                java.lang.String r20 = r28.readString()
                int r21 = r28.readInt()
                int r22 = r28.readInt()
                int r1 = r28.readInt()
                if (r1 == 0) goto L_0x11fe
                android.os.Parcelable$Creator<android.app.ProfilerInfo> r1 = android.app.ProfilerInfo.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r12)
                android.app.ProfilerInfo r1 = (android.app.ProfilerInfo) r1
                r9 = r1
                goto L_0x11ff
            L_0x11fe:
                r9 = r0
            L_0x11ff:
                int r1 = r28.readInt()
                if (r1 == 0) goto L_0x120f
                android.os.Parcelable$Creator<android.os.Bundle> r0 = android.os.Bundle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r12)
                android.os.Bundle r0 = (android.os.Bundle) r0
            L_0x120d:
                r10 = r0
                goto L_0x1210
            L_0x120f:
                goto L_0x120d
            L_0x1210:
                int r23 = r28.readInt()
                r0 = r26
                r1 = r16
                r2 = r17
                r4 = r18
                r5 = r19
                r6 = r20
                r7 = r21
                r8 = r22
                r11 = r23
                int r0 = r0.startActivityAsUser(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11)
                r29.writeNoException()
                r15.writeInt(r0)
                r1 = 1
                return r1
            L_0x1232:
                r15 = r11
                r12 = r13
                r13 = r8
                r12.enforceInterface(r13)
                android.os.IBinder r1 = r28.readStrongBinder()
                android.app.IApplicationThread r8 = android.app.IApplicationThread.Stub.asInterface(r1)
                java.lang.String r9 = r28.readString()
                android.os.Parcelable$Creator<android.content.Intent> r1 = android.content.Intent.CREATOR
                java.lang.Object[] r1 = r12.createTypedArray(r1)
                r10 = r1
                android.content.Intent[] r10 = (android.content.Intent[]) r10
                java.lang.String[] r11 = r28.createStringArray()
                android.os.IBinder r16 = r28.readStrongBinder()
                int r1 = r28.readInt()
                if (r1 == 0) goto L_0x1265
                android.os.Parcelable$Creator<android.os.Bundle> r0 = android.os.Bundle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r12)
                android.os.Bundle r0 = (android.os.Bundle) r0
            L_0x1263:
                r6 = r0
                goto L_0x1266
            L_0x1265:
                goto L_0x1263
            L_0x1266:
                int r17 = r28.readInt()
                r0 = r26
                r1 = r8
                r2 = r9
                r3 = r10
                r4 = r11
                r5 = r16
                r7 = r17
                int r0 = r0.startActivities(r1, r2, r3, r4, r5, r6, r7)
                r29.writeNoException()
                r15.writeInt(r0)
                r1 = 1
                return r1
            L_0x1280:
                r15 = r11
                r12 = r13
                r13 = r8
                r12.enforceInterface(r13)
                android.os.IBinder r1 = r28.readStrongBinder()
                android.app.IApplicationThread r11 = android.app.IApplicationThread.Stub.asInterface(r1)
                java.lang.String r16 = r28.readString()
                int r1 = r28.readInt()
                if (r1 == 0) goto L_0x12a2
                android.os.Parcelable$Creator<android.content.Intent> r1 = android.content.Intent.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r12)
                android.content.Intent r1 = (android.content.Intent) r1
                r3 = r1
                goto L_0x12a3
            L_0x12a2:
                r3 = r0
            L_0x12a3:
                java.lang.String r17 = r28.readString()
                android.os.IBinder r18 = r28.readStrongBinder()
                java.lang.String r19 = r28.readString()
                int r20 = r28.readInt()
                int r21 = r28.readInt()
                int r1 = r28.readInt()
                if (r1 == 0) goto L_0x12c7
                android.os.Parcelable$Creator<android.app.ProfilerInfo> r1 = android.app.ProfilerInfo.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r12)
                android.app.ProfilerInfo r1 = (android.app.ProfilerInfo) r1
                r9 = r1
                goto L_0x12c8
            L_0x12c7:
                r9 = r0
            L_0x12c8:
                int r1 = r28.readInt()
                if (r1 == 0) goto L_0x12d8
                android.os.Parcelable$Creator<android.os.Bundle> r0 = android.os.Bundle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r12)
                android.os.Bundle r0 = (android.os.Bundle) r0
            L_0x12d6:
                r10 = r0
                goto L_0x12d9
            L_0x12d8:
                goto L_0x12d6
            L_0x12d9:
                r0 = r26
                r1 = r11
                r2 = r16
                r4 = r17
                r5 = r18
                r6 = r19
                r7 = r20
                r8 = r21
                int r0 = r0.startActivity(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10)
                r29.writeNoException()
                r15.writeInt(r0)
                r1 = 1
                return r1
            L_0x12f4:
                r1 = r7
                r15 = r11
                r12 = r13
                r13 = r8
                r15.writeString(r13)
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: android.app.IActivityTaskManager.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IActivityTaskManager {
            public static IActivityTaskManager sDefaultImpl;
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
                    if (this.mRemote.transact(1, _data, _reply2, 0) || Stub.getDefaultImpl() == null) {
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

            public int startActivities(IApplicationThread caller, String callingPackage, Intent[] intents, String[] resolvedTypes, IBinder resultTo, Bundle options, int userId) throws RemoteException {
                Bundle bundle = options;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(caller != null ? caller.asBinder() : null);
                    try {
                        _data.writeString(callingPackage);
                    } catch (Throwable th) {
                        th = th;
                        Intent[] intentArr = intents;
                        String[] strArr = resolvedTypes;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeTypedArray(intents, 0);
                    } catch (Throwable th2) {
                        th = th2;
                        String[] strArr2 = resolvedTypes;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeStringArray(resolvedTypes);
                        _data.writeStrongBinder(resultTo);
                        if (bundle != null) {
                            _data.writeInt(1);
                            bundle.writeToParcel(_data, 0);
                        } else {
                            _data.writeInt(0);
                        }
                        _data.writeInt(userId);
                        if (this.mRemote.transact(2, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                            _reply.readException();
                            int _result = _reply.readInt();
                            _reply.recycle();
                            _data.recycle();
                            return _result;
                        }
                        int startActivities = Stub.getDefaultImpl().startActivities(caller, callingPackage, intents, resolvedTypes, resultTo, options, userId);
                        _reply.recycle();
                        _data.recycle();
                        return startActivities;
                    } catch (Throwable th3) {
                        th = th3;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th4) {
                    th = th4;
                    String str = callingPackage;
                    Intent[] intentArr2 = intents;
                    String[] strArr22 = resolvedTypes;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
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
                    if (this.mRemote.transact(3, _data2, _reply2, 0) || Stub.getDefaultImpl() == null) {
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

            public boolean startNextMatchingActivity(IBinder callingActivity, Intent intent, Bundle options) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(callingActivity);
                    boolean _result = true;
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
                    if (!this.mRemote.transact(4, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().startNextMatchingActivity(callingActivity, intent, options);
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

            public int startActivityIntentSender(IApplicationThread caller, IIntentSender target, IBinder whitelistToken, Intent fillInIntent, String resolvedType, IBinder resultTo, String resultWho, int requestCode, int flagsMask, int flagsValues, Bundle options) throws RemoteException {
                Parcel _reply;
                IBinder iBinder;
                Intent intent = fillInIntent;
                Bundle bundle = options;
                Parcel _data = Parcel.obtain();
                Parcel _reply2 = Parcel.obtain();
                _data.writeInterfaceToken(Stub.DESCRIPTOR);
                IBinder iBinder2 = null;
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
                try {
                    _data.writeStrongBinder(iBinder);
                    if (target != null) {
                        iBinder2 = target.asBinder();
                    }
                    _data.writeStrongBinder(iBinder2);
                    _data.writeStrongBinder(whitelistToken);
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
                    _data.writeInt(flagsMask);
                    _data.writeInt(flagsValues);
                    if (bundle != null) {
                        _data.writeInt(1);
                        bundle.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(5, _data, _reply2, 0) || Stub.getDefaultImpl() == null) {
                        _reply = _reply2;
                        _reply.readException();
                        int _result = _reply.readInt();
                        _reply.recycle();
                        _data.recycle();
                        return _result;
                    }
                    _reply = _reply2;
                    try {
                        int startActivityIntentSender = Stub.getDefaultImpl().startActivityIntentSender(caller, target, whitelistToken, fillInIntent, resolvedType, resultTo, resultWho, requestCode, flagsMask, flagsValues, options);
                        _reply.recycle();
                        _data.recycle();
                        return startActivityIntentSender;
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

            public WaitResult startActivityAndWait(IApplicationThread caller, String callingPackage, Intent intent, String resolvedType, IBinder resultTo, String resultWho, int requestCode, int flags, ProfilerInfo profilerInfo, Bundle options, int userId) throws RemoteException {
                Parcel _data;
                Parcel _reply;
                IBinder iBinder;
                WaitResult _result;
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
                    if (!this.mRemote.transact(6, _data2, _reply2, 0)) {
                        try {
                            if (Stub.getDefaultImpl() != null) {
                                Parcel _reply3 = _reply2;
                                _data = _data2;
                                try {
                                    WaitResult startActivityAndWait = Stub.getDefaultImpl().startActivityAndWait(caller, callingPackage, intent, resolvedType, resultTo, resultWho, requestCode, flags, profilerInfo, options, userId);
                                    _reply3.recycle();
                                    _data.recycle();
                                    return startActivityAndWait;
                                } catch (Throwable th2) {
                                    th = th2;
                                    _reply = _reply3;
                                    _reply.recycle();
                                    _data.recycle();
                                    throw th;
                                }
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            _data = _data2;
                            _reply = _reply2;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    }
                    Parcel _reply4 = _reply2;
                    _data = _data2;
                    try {
                        _reply4.readException();
                        if (_reply4.readInt() != 0) {
                            _reply = _reply4;
                            try {
                                _result = WaitResult.CREATOR.createFromParcel(_reply);
                            } catch (Throwable th4) {
                                th = th4;
                                _reply.recycle();
                                _data.recycle();
                                throw th;
                            }
                        } else {
                            _reply = _reply4;
                            _result = null;
                        }
                        WaitResult _result2 = _result;
                        _reply.recycle();
                        _data.recycle();
                        return _result2;
                    } catch (Throwable th5) {
                        th = th5;
                        _reply = _reply4;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th6) {
                    th = th6;
                    _reply = _reply2;
                    _data = _data2;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public int startActivityWithConfig(IApplicationThread caller, String callingPackage, Intent intent, String resolvedType, IBinder resultTo, String resultWho, int requestCode, int startFlags, Configuration newConfig, Bundle options, int userId) throws RemoteException {
                Parcel _data;
                Parcel _reply;
                IBinder iBinder;
                Intent intent2 = intent;
                Configuration configuration = newConfig;
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
                    _data2.writeInt(startFlags);
                    if (configuration != null) {
                        _data2.writeInt(1);
                        configuration.writeToParcel(_data2, 0);
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
                    if (this.mRemote.transact(7, _data2, _reply2, 0) || Stub.getDefaultImpl() == null) {
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
                        int startActivityWithConfig = Stub.getDefaultImpl().startActivityWithConfig(caller, callingPackage, intent, resolvedType, resultTo, resultWho, requestCode, startFlags, newConfig, options, userId);
                        _reply.recycle();
                        _data.recycle();
                        return startActivityWithConfig;
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

            public int startVoiceActivity(String callingPackage, int callingPid, int callingUid, Intent intent, String resolvedType, IVoiceInteractionSession session, IVoiceInteractor interactor, int flags, ProfilerInfo profilerInfo, Bundle options, int userId) throws RemoteException {
                Parcel _data;
                Parcel _reply;
                Intent intent2 = intent;
                ProfilerInfo profilerInfo2 = profilerInfo;
                Bundle bundle = options;
                Parcel _data2 = Parcel.obtain();
                Parcel _reply2 = Parcel.obtain();
                _data2.writeInterfaceToken(Stub.DESCRIPTOR);
                _data2.writeString(callingPackage);
                _data2.writeInt(callingPid);
                _data2.writeInt(callingUid);
                if (intent2 != null) {
                    try {
                        _data2.writeInt(1);
                        intent2.writeToParcel(_data2, 0);
                    } catch (Throwable th) {
                        th = th;
                        _reply = _reply2;
                        _data = _data2;
                    }
                } else {
                    _data2.writeInt(0);
                }
                _data2.writeString(resolvedType);
                IBinder iBinder = null;
                try {
                    _data2.writeStrongBinder(session != null ? session.asBinder() : null);
                    if (interactor != null) {
                        iBinder = interactor.asBinder();
                    }
                    _data2.writeStrongBinder(iBinder);
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
                    if (this.mRemote.transact(8, _data2, _reply2, 0) || Stub.getDefaultImpl() == null) {
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
                        int startVoiceActivity = Stub.getDefaultImpl().startVoiceActivity(callingPackage, callingPid, callingUid, intent, resolvedType, session, interactor, flags, profilerInfo, options, userId);
                        _reply.recycle();
                        _data.recycle();
                        return startVoiceActivity;
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

            public int startAssistantActivity(String callingPackage, int callingPid, int callingUid, Intent intent, String resolvedType, Bundle options, int userId) throws RemoteException {
                Intent intent2 = intent;
                Bundle bundle = options;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeString(callingPackage);
                    } catch (Throwable th) {
                        th = th;
                        int i = callingPid;
                        int i2 = callingUid;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(callingPid);
                        try {
                            _data.writeInt(callingUid);
                            if (intent2 != null) {
                                _data.writeInt(1);
                                intent2.writeToParcel(_data, 0);
                            } else {
                                _data.writeInt(0);
                            }
                            _data.writeString(resolvedType);
                            if (bundle != null) {
                                _data.writeInt(1);
                                bundle.writeToParcel(_data, 0);
                            } else {
                                _data.writeInt(0);
                            }
                            _data.writeInt(userId);
                            if (this.mRemote.transact(9, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                _reply.readException();
                                int _result = _reply.readInt();
                                _reply.recycle();
                                _data.recycle();
                                return _result;
                            }
                            int startAssistantActivity = Stub.getDefaultImpl().startAssistantActivity(callingPackage, callingPid, callingUid, intent, resolvedType, options, userId);
                            _reply.recycle();
                            _data.recycle();
                            return startAssistantActivity;
                        } catch (Throwable th2) {
                            th = th2;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        int i22 = callingUid;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th4) {
                    th = th4;
                    String str = callingPackage;
                    int i3 = callingPid;
                    int i222 = callingUid;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
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
                    if (this.mRemote.transact(10, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
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
                    if (!this.mRemote.transact(11, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            public int startActivityAsCaller(IApplicationThread caller, String callingPackage, Intent intent, String resolvedType, IBinder resultTo, String resultWho, int requestCode, int flags, ProfilerInfo profilerInfo, Bundle options, IBinder permissionToken, boolean ignoreTargetSecurity, int userId) throws RemoteException {
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
                    _data2.writeStrongBinder(permissionToken);
                    _data2.writeInt(ignoreTargetSecurity ? 1 : 0);
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
                        int startActivityAsCaller = Stub.getDefaultImpl().startActivityAsCaller(caller, callingPackage, intent, resolvedType, resultTo, resultWho, requestCode, flags, profilerInfo, options, permissionToken, ignoreTargetSecurity, userId);
                        _reply.recycle();
                        _data.recycle();
                        return startActivityAsCaller;
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

            public boolean isActivityStartAllowedOnDisplay(int displayId, Intent intent, String resolvedType, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    boolean _result = true;
                    if (intent != null) {
                        _data.writeInt(1);
                        intent.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(resolvedType);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(13, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isActivityStartAllowedOnDisplay(displayId, intent, resolvedType, userId);
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

            public void unhandledBack() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(14, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
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
                    if (!this.mRemote.transact(15, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            public boolean finishActivityAffinity(IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    boolean z = false;
                    if (!this.mRemote.transact(16, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().finishActivityAffinity(token);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _result = z;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void activityIdle(IBinder token, Configuration config, boolean stopProfiling) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    if (config != null) {
                        _data.writeInt(1);
                        config.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(stopProfiling);
                    if (this.mRemote.transact(17, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().activityIdle(token, config, stopProfiling);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void activityResumed(IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    if (this.mRemote.transact(18, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().activityResumed(token);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void activityTopResumedStateLost() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(19, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().activityTopResumedStateLost();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void activityPaused(IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    if (this.mRemote.transact(20, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().activityPaused(token);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void activityStopped(IBinder token, Bundle state, PersistableBundle persistentState, CharSequence description) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    if (state != null) {
                        _data.writeInt(1);
                        state.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (persistentState != null) {
                        _data.writeInt(1);
                        persistentState.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (description != null) {
                        _data.writeInt(1);
                        TextUtils.writeToParcel(description, _data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(21, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().activityStopped(token, state, persistentState, description);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void activityDestroyed(IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    if (this.mRemote.transact(22, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().activityDestroyed(token);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void activityRelaunched(IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    if (this.mRemote.transact(23, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().activityRelaunched(token);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void activitySlept(IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    if (this.mRemote.transact(24, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().activitySlept(token);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public int getFrontActivityScreenCompatMode() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(25, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getFrontActivityScreenCompatMode();
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

            public void setFrontActivityScreenCompatMode(int mode) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(mode);
                    if (this.mRemote.transact(26, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setFrontActivityScreenCompatMode(mode);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String getCallingPackage(IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    if (!this.mRemote.transact(27, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getCallingPackage(token);
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

            public ComponentName getCallingActivity(IBinder token) throws RemoteException {
                ComponentName _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    if (!this.mRemote.transact(28, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getCallingActivity(token);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = ComponentName.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    ComponentName _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setFocusedTask(int taskId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(taskId);
                    if (this.mRemote.transact(29, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setFocusedTask(taskId);
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
                    if (!this.mRemote.transact(30, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().removeTask(taskId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _result = z;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void removeAllVisibleRecentTasks() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(31, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().removeAllVisibleRecentTasks();
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
                    if (!this.mRemote.transact(32, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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
                    if (!this.mRemote.transact(33, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            public boolean shouldUpRecreateTask(IBinder token, String destAffinity) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeString(destAffinity);
                    boolean z = false;
                    if (!this.mRemote.transact(34, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().shouldUpRecreateTask(token, destAffinity);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _result = z;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean navigateUpTo(IBinder token, Intent target, int resultCode, Intent resultData) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    boolean _result = true;
                    if (target != null) {
                        _data.writeInt(1);
                        target.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(resultCode);
                    if (resultData != null) {
                        _data.writeInt(1);
                        resultData.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(35, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().navigateUpTo(token, target, resultCode, resultData);
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

            public void moveTaskToFront(IApplicationThread app, String callingPackage, int task, int flags, Bundle options) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(app != null ? app.asBinder() : null);
                    _data.writeString(callingPackage);
                    _data.writeInt(task);
                    _data.writeInt(flags);
                    if (options != null) {
                        _data.writeInt(1);
                        options.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(36, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().moveTaskToFront(app, callingPackage, task, flags, options);
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
                    if (!this.mRemote.transact(37, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            public void finishSubActivity(IBinder token, String resultWho, int requestCode) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeString(resultWho);
                    _data.writeInt(requestCode);
                    if (this.mRemote.transact(38, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().finishSubActivity(token, resultWho, requestCode);
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
                    if (!this.mRemote.transact(39, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            public boolean willActivityBeVisible(IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    boolean z = false;
                    if (!this.mRemote.transact(40, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().willActivityBeVisible(token);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _result = z;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
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
                    if (this.mRemote.transact(41, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
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

            public int getRequestedOrientation(IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    if (!this.mRemote.transact(42, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getRequestedOrientation(token);
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

            public boolean convertFromTranslucent(IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    boolean z = false;
                    if (!this.mRemote.transact(43, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().convertFromTranslucent(token);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _result = z;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean convertToTranslucent(IBinder token, Bundle options) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    boolean _result = true;
                    if (options != null) {
                        _data.writeInt(1);
                        options.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(44, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().convertToTranslucent(token, options);
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

            public void notifyActivityDrawn(IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    if (this.mRemote.transact(45, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().notifyActivityDrawn(token);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void reportActivityFullyDrawn(IBinder token, boolean restoredFromBundle) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeInt(restoredFromBundle);
                    if (this.mRemote.transact(46, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().reportActivityFullyDrawn(token, restoredFromBundle);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getActivityDisplayId(IBinder activityToken) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(activityToken);
                    if (!this.mRemote.transact(47, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getActivityDisplayId(activityToken);
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

            public boolean isImmersive(IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    boolean z = false;
                    if (!this.mRemote.transact(48, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isImmersive(token);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _result = z;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setImmersive(IBinder token, boolean immersive) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeInt(immersive);
                    if (this.mRemote.transact(49, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setImmersive(token, immersive);
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
                    if (!this.mRemote.transact(50, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isTopActivityImmersive();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _result = z;
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
                    if (!this.mRemote.transact(51, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().moveActivityTaskToBack(token, nonRoot);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _result = z;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public ActivityManager.TaskDescription getTaskDescription(int taskId) throws RemoteException {
                ActivityManager.TaskDescription _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(taskId);
                    if (!this.mRemote.transact(52, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getTaskDescription(taskId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = ActivityManager.TaskDescription.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    ActivityManager.TaskDescription _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void overridePendingTransition(IBinder token, String packageName, int enterAnim, int exitAnim) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeString(packageName);
                    _data.writeInt(enterAnim);
                    _data.writeInt(exitAnim);
                    if (this.mRemote.transact(53, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().overridePendingTransition(token, packageName, enterAnim, exitAnim);
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
                    if (!this.mRemote.transact(54, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            public String getLaunchedFromPackage(IBinder activityToken) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(activityToken);
                    if (!this.mRemote.transact(55, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            public void reportAssistContextExtras(IBinder token, Bundle extras, AssistStructure structure, AssistContent content, Uri referrer) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    if (extras != null) {
                        _data.writeInt(1);
                        extras.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (structure != null) {
                        _data.writeInt(1);
                        structure.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (content != null) {
                        _data.writeInt(1);
                        content.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (referrer != null) {
                        _data.writeInt(1);
                        referrer.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(56, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().reportAssistContextExtras(token, extras, structure, content, referrer);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setFocusedStack(int stackId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(stackId);
                    if (this.mRemote.transact(57, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
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
                    if (!this.mRemote.transact(58, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            public Rect getTaskBounds(int taskId) throws RemoteException {
                Rect _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(taskId);
                    if (!this.mRemote.transact(59, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            public void cancelRecentsAnimation(boolean restoreHomeStackPosition) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(restoreHomeStackPosition);
                    if (this.mRemote.transact(60, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
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

            public void startLockTaskModeByToken(IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    if (this.mRemote.transact(61, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().startLockTaskModeByToken(token);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void stopLockTaskModeByToken(IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    if (this.mRemote.transact(62, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().stopLockTaskModeByToken(token);
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
                    if (this.mRemote.transact(63, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
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

            public boolean isInLockTaskMode() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(64, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isInLockTaskMode();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _result = z;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
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
                    if (!this.mRemote.transact(65, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            public void setTaskDescription(IBinder token, ActivityManager.TaskDescription values) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    if (values != null) {
                        _data.writeInt(1);
                        values.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(66, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setTaskDescription(token, values);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public Bundle getActivityOptions(IBinder token) throws RemoteException {
                Bundle _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    if (!this.mRemote.transact(67, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getActivityOptions(token);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = Bundle.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    Bundle _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public List<IBinder> getAppTasks(String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(68, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAppTasks(callingPackage);
                    }
                    _reply.readException();
                    List<IBinder> _result = _reply.createBinderArrayList();
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
                    if (this.mRemote.transact(69, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
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

            public void stopSystemLockTaskMode() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(70, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().stopSystemLockTaskMode();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void finishVoiceTask(IVoiceInteractionSession session) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(session != null ? session.asBinder() : null);
                    if (this.mRemote.transact(71, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().finishVoiceTask(session);
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
                    if (!this.mRemote.transact(72, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isTopOfTask(token);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _result = z;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void notifyLaunchTaskBehindComplete(IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    if (this.mRemote.transact(73, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().notifyLaunchTaskBehindComplete(token);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void notifyEnterAnimationComplete(IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    if (this.mRemote.transact(74, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().notifyEnterAnimationComplete(token);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int addAppTask(IBinder activityToken, Intent intent, ActivityManager.TaskDescription description, Bitmap thumbnail) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(activityToken);
                    if (intent != null) {
                        _data.writeInt(1);
                        intent.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (description != null) {
                        _data.writeInt(1);
                        description.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (thumbnail != null) {
                        _data.writeInt(1);
                        thumbnail.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(75, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().addAppTask(activityToken, intent, description, thumbnail);
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

            public Point getAppTaskThumbnailSize() throws RemoteException {
                Point _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(76, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAppTaskThumbnailSize();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = Point.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    Point _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean releaseActivityInstance(IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    boolean z = false;
                    if (!this.mRemote.transact(77, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().releaseActivityInstance(token);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _result = z;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public IBinder requestStartActivityPermissionToken(IBinder delegatorToken) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(delegatorToken);
                    if (!this.mRemote.transact(78, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().requestStartActivityPermissionToken(delegatorToken);
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

            public void releaseSomeActivities(IApplicationThread app) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(app != null ? app.asBinder() : null);
                    if (this.mRemote.transact(79, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().releaseSomeActivities(app);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public Bitmap getTaskDescriptionIcon(String filename, int userId) throws RemoteException {
                Bitmap _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(filename);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(80, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getTaskDescriptionIcon(filename, userId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = Bitmap.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    Bitmap _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void startInPlaceAnimationOnFrontMostApplication(Bundle opts) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (opts != null) {
                        _data.writeInt(1);
                        opts.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(81, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().startInPlaceAnimationOnFrontMostApplication(opts);
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
                    if (this.mRemote.transact(82, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
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
                    if (this.mRemote.transact(83, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
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

            public void setTaskResizeable(int taskId, int resizeableMode) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(taskId);
                    _data.writeInt(resizeableMode);
                    if (this.mRemote.transact(84, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
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

            public void toggleFreeformWindowingMode(IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    if (this.mRemote.transact(85, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().toggleFreeformWindowingMode(token);
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
                    if (this.mRemote.transact(86, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
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

            public void moveStackToDisplay(int stackId, int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(stackId);
                    _data.writeInt(displayId);
                    if (this.mRemote.transact(87, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().moveStackToDisplay(stackId, displayId);
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
                    if (this.mRemote.transact(88, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
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

            public void setTaskWindowingMode(int taskId, int windowingMode, boolean toTop) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(taskId);
                    _data.writeInt(windowingMode);
                    _data.writeInt(toTop);
                    if (this.mRemote.transact(89, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setTaskWindowingMode(taskId, windowingMode, toTop);
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
                    if (this.mRemote.transact(90, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
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
                            if (this.mRemote.transact(91, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
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

            public boolean setTaskWindowingModeSplitScreenPrimary(int taskId, int createMode, boolean toTop, boolean animate, Rect initialBounds, boolean showRecents) throws RemoteException {
                Rect rect = initialBounds;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeInt(taskId);
                        try {
                            _data.writeInt(createMode);
                        } catch (Throwable th) {
                            th = th;
                            boolean z = toTop;
                            boolean z2 = animate;
                            boolean z3 = showRecents;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                        try {
                            _data.writeInt(toTop ? 1 : 0);
                        } catch (Throwable th2) {
                            th = th2;
                            boolean z22 = animate;
                            boolean z32 = showRecents;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        int i = createMode;
                        boolean z4 = toTop;
                        boolean z222 = animate;
                        boolean z322 = showRecents;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(animate ? 1 : 0);
                        boolean _result = true;
                        if (rect != null) {
                            _data.writeInt(1);
                            rect.writeToParcel(_data, 0);
                        } else {
                            _data.writeInt(0);
                        }
                        try {
                            _data.writeInt(showRecents ? 1 : 0);
                            if (this.mRemote.transact(92, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                _reply.readException();
                                if (_reply.readInt() == 0) {
                                    _result = false;
                                }
                                _reply.recycle();
                                _data.recycle();
                                return _result;
                            }
                            boolean taskWindowingModeSplitScreenPrimary = Stub.getDefaultImpl().setTaskWindowingModeSplitScreenPrimary(taskId, createMode, toTop, animate, initialBounds, showRecents);
                            _reply.recycle();
                            _data.recycle();
                            return taskWindowingModeSplitScreenPrimary;
                        } catch (Throwable th4) {
                            th = th4;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th5) {
                        th = th5;
                        boolean z3222 = showRecents;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th6) {
                    th = th6;
                    int i2 = taskId;
                    int i3 = createMode;
                    boolean z42 = toTop;
                    boolean z2222 = animate;
                    boolean z32222 = showRecents;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public void offsetPinnedStackBounds(int stackId, Rect compareBounds, int xOffset, int yOffset, int animationDuration) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(stackId);
                    if (compareBounds != null) {
                        _data.writeInt(1);
                        compareBounds.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(xOffset);
                    _data.writeInt(yOffset);
                    _data.writeInt(animationDuration);
                    if (this.mRemote.transact(93, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().offsetPinnedStackBounds(stackId, compareBounds, xOffset, yOffset, animationDuration);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void removeStacksInWindowingModes(int[] windowingModes) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeIntArray(windowingModes);
                    if (this.mRemote.transact(94, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().removeStacksInWindowingModes(windowingModes);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void removeStacksWithActivityTypes(int[] activityTypes) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeIntArray(activityTypes);
                    if (this.mRemote.transact(95, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().removeStacksWithActivityTypes(activityTypes);
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
                    if (!this.mRemote.transact(96, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            public ActivityManager.StackInfo getStackInfo(int windowingMode, int activityType) throws RemoteException {
                ActivityManager.StackInfo _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(windowingMode);
                    _data.writeInt(activityType);
                    if (!this.mRemote.transact(97, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getStackInfo(windowingMode, activityType);
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

            public void setLockScreenShown(boolean showingKeyguard, boolean showingAod) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(showingKeyguard);
                    _data.writeInt(showingAod);
                    if (this.mRemote.transact(98, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setLockScreenShown(showingKeyguard, showingAod);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public Bundle getAssistContextExtras(int requestType) throws RemoteException {
                Bundle _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(requestType);
                    if (!this.mRemote.transact(99, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAssistContextExtras(requestType);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = Bundle.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    Bundle _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean launchAssistIntent(Intent intent, int requestType, String hint, int userHandle, Bundle args) throws RemoteException {
                Intent intent2 = intent;
                Bundle bundle = args;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (intent2 != null) {
                        _data.writeInt(1);
                        intent2.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    try {
                        _data.writeInt(requestType);
                        try {
                            _data.writeString(hint);
                        } catch (Throwable th) {
                            th = th;
                            int i = userHandle;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                        try {
                            _data.writeInt(userHandle);
                            if (bundle != null) {
                                _data.writeInt(1);
                                bundle.writeToParcel(_data, 0);
                            } else {
                                _data.writeInt(0);
                            }
                            try {
                                if (this.mRemote.transact(100, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                    _reply.readException();
                                    if (_reply.readInt() == 0) {
                                        _result = false;
                                    }
                                    _reply.recycle();
                                    _data.recycle();
                                    return _result;
                                }
                                boolean launchAssistIntent = Stub.getDefaultImpl().launchAssistIntent(intent, requestType, hint, userHandle, args);
                                _reply.recycle();
                                _data.recycle();
                                return launchAssistIntent;
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
                    } catch (Throwable th4) {
                        th = th4;
                        String str = hint;
                        int i2 = userHandle;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th5) {
                    th = th5;
                    int i3 = requestType;
                    String str2 = hint;
                    int i22 = userHandle;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public boolean requestAssistContextExtras(int requestType, IAssistDataReceiver receiver, Bundle receiverExtras, IBinder activityToken, boolean focused, boolean newSessionId) throws RemoteException {
                boolean _result;
                Bundle bundle = receiverExtras;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeInt(requestType);
                        _data.writeStrongBinder(receiver != null ? receiver.asBinder() : null);
                        _result = true;
                        if (bundle != null) {
                            _data.writeInt(1);
                            bundle.writeToParcel(_data, 0);
                        } else {
                            _data.writeInt(0);
                        }
                    } catch (Throwable th) {
                        th = th;
                        IBinder iBinder = activityToken;
                        boolean z = focused;
                        boolean z2 = newSessionId;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeStrongBinder(activityToken);
                        try {
                            _data.writeInt(focused ? 1 : 0);
                        } catch (Throwable th2) {
                            th = th2;
                            boolean z22 = newSessionId;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                        try {
                            _data.writeInt(newSessionId ? 1 : 0);
                            if (this.mRemote.transact(101, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                _reply.readException();
                                if (_reply.readInt() == 0) {
                                    _result = false;
                                }
                                _reply.recycle();
                                _data.recycle();
                                return _result;
                            }
                            boolean requestAssistContextExtras = Stub.getDefaultImpl().requestAssistContextExtras(requestType, receiver, receiverExtras, activityToken, focused, newSessionId);
                            _reply.recycle();
                            _data.recycle();
                            return requestAssistContextExtras;
                        } catch (Throwable th3) {
                            th = th3;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th4) {
                        th = th4;
                        boolean z3 = focused;
                        boolean z222 = newSessionId;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th5) {
                    th = th5;
                    int i = requestType;
                    IBinder iBinder2 = activityToken;
                    boolean z32 = focused;
                    boolean z2222 = newSessionId;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public boolean requestAutofillData(IAssistDataReceiver receiver, Bundle receiverExtras, IBinder activityToken, int flags) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(receiver != null ? receiver.asBinder() : null);
                    boolean _result = true;
                    if (receiverExtras != null) {
                        _data.writeInt(1);
                        receiverExtras.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeStrongBinder(activityToken);
                    _data.writeInt(flags);
                    if (!this.mRemote.transact(102, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().requestAutofillData(receiver, receiverExtras, activityToken, flags);
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

            public boolean isAssistDataAllowedOnCurrentActivity() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(103, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isAssistDataAllowedOnCurrentActivity();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _result = z;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean showAssistFromActivity(IBinder token, Bundle args) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    boolean _result = true;
                    if (args != null) {
                        _data.writeInt(1);
                        args.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(104, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().showAssistFromActivity(token, args);
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

            public boolean isRootVoiceInteraction(IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    boolean z = false;
                    if (!this.mRemote.transact(105, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isRootVoiceInteraction(token);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _result = z;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void showLockTaskEscapeMessage(IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    if (this.mRemote.transact(106, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().showLockTaskEscapeMessage(token);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void keyguardGoingAway(int flags) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(flags);
                    if (this.mRemote.transact(107, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().keyguardGoingAway(flags);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public ComponentName getActivityClassForToken(IBinder token) throws RemoteException {
                ComponentName _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    if (!this.mRemote.transact(108, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getActivityClassForToken(token);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = ComponentName.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    ComponentName _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String getPackageForToken(IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    if (!this.mRemote.transact(109, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPackageForToken(token);
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

            public void positionTaskInStack(int taskId, int stackId, int position) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(taskId);
                    _data.writeInt(stackId);
                    _data.writeInt(position);
                    if (this.mRemote.transact(110, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
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

            public void reportSizeConfigurations(IBinder token, int[] horizontalSizeConfiguration, int[] verticalSizeConfigurations, int[] smallestWidthConfigurations) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeIntArray(horizontalSizeConfiguration);
                    _data.writeIntArray(verticalSizeConfigurations);
                    _data.writeIntArray(smallestWidthConfigurations);
                    if (this.mRemote.transact(111, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().reportSizeConfigurations(token, horizontalSizeConfiguration, verticalSizeConfigurations, smallestWidthConfigurations);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void dismissSplitScreenMode(boolean toTop) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(toTop);
                    if (this.mRemote.transact(112, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().dismissSplitScreenMode(toTop);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void dismissPip(boolean animate, int animationDuration) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(animate);
                    _data.writeInt(animationDuration);
                    if (this.mRemote.transact(113, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().dismissPip(animate, animationDuration);
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
                    if (this.mRemote.transact(114, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
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

            public void moveTasksToFullscreenStack(int fromStackId, boolean onTop) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(fromStackId);
                    _data.writeInt(onTop);
                    if (this.mRemote.transact(115, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().moveTasksToFullscreenStack(fromStackId, onTop);
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
                    if (!this.mRemote.transact(116, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            public boolean isInMultiWindowMode(IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    boolean z = false;
                    if (!this.mRemote.transact(117, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isInMultiWindowMode(token);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _result = z;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isInPictureInPictureMode(IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    boolean z = false;
                    if (!this.mRemote.transact(118, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isInPictureInPictureMode(token);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _result = z;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean enterPictureInPictureMode(IBinder token, PictureInPictureParams params) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    boolean _result = true;
                    if (params != null) {
                        _data.writeInt(1);
                        params.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(119, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().enterPictureInPictureMode(token, params);
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

            public void setPictureInPictureParams(IBinder token, PictureInPictureParams params) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    if (params != null) {
                        _data.writeInt(1);
                        params.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(120, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setPictureInPictureParams(token, params);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getMaxNumPictureInPictureActions(IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    if (!this.mRemote.transact(121, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getMaxNumPictureInPictureActions(token);
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

            public IBinder getUriPermissionOwnerForActivity(IBinder activityToken) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(activityToken);
                    if (!this.mRemote.transact(122, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getUriPermissionOwnerForActivity(activityToken);
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
                    if (this.mRemote.transact(123, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
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

            public void setSplitScreenResizing(boolean resizing) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(resizing);
                    if (this.mRemote.transact(124, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setSplitScreenResizing(resizing);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int setVrMode(IBinder token, boolean enabled, ComponentName packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeInt(enabled);
                    if (packageName != null) {
                        _data.writeInt(1);
                        packageName.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(125, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setVrMode(token, enabled, packageName);
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

            public void startLocalVoiceInteraction(IBinder token, Bundle options) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    if (options != null) {
                        _data.writeInt(1);
                        options.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(126, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().startLocalVoiceInteraction(token, options);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void stopLocalVoiceInteraction(IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    if (this.mRemote.transact(127, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().stopLocalVoiceInteraction(token);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean supportsLocalVoiceInteraction() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(128, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().supportsLocalVoiceInteraction();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _result = z;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void notifyPinnedStackAnimationStarted() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(129, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().notifyPinnedStackAnimationStarted();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void notifyPinnedStackAnimationEnded() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(130, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().notifyPinnedStackAnimationEnded();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public ConfigurationInfo getDeviceConfigurationInfo() throws RemoteException {
                ConfigurationInfo _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(131, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getDeviceConfigurationInfo();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = ConfigurationInfo.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    ConfigurationInfo _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void resizePinnedStack(Rect pinnedBounds, Rect tempPinnedTaskBounds) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (pinnedBounds != null) {
                        _data.writeInt(1);
                        pinnedBounds.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (tempPinnedTaskBounds != null) {
                        _data.writeInt(1);
                        tempPinnedTaskBounds.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(132, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().resizePinnedStack(pinnedBounds, tempPinnedTaskBounds);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean updateDisplayOverrideConfiguration(Configuration values, int displayId) throws RemoteException {
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
                    _data.writeInt(displayId);
                    if (!this.mRemote.transact(133, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().updateDisplayOverrideConfiguration(values, displayId);
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

            public void dismissKeyguard(IBinder token, IKeyguardDismissCallback callback, CharSequence message) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (message != null) {
                        _data.writeInt(1);
                        TextUtils.writeToParcel(message, _data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(134, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().dismissKeyguard(token, callback, message);
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
                    if (this.mRemote.transact(135, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
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
                    if (!this.mRemote.transact(136, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            public void setDisablePreviewScreenshots(IBinder token, boolean disable) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeInt(disable);
                    if (this.mRemote.transact(137, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setDisablePreviewScreenshots(token, disable);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getLastResumedActivityUserId() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(138, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getLastResumedActivityUserId();
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
                    if (!this.mRemote.transact(139, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            public void updateLockTaskFeatures(int userId, int flags) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeInt(flags);
                    if (this.mRemote.transact(140, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().updateLockTaskFeatures(userId, flags);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setShowWhenLocked(IBinder token, boolean showWhenLocked) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeInt(showWhenLocked);
                    if (this.mRemote.transact(141, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setShowWhenLocked(token, showWhenLocked);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setInheritShowWhenLocked(IBinder token, boolean setInheritShownWhenLocked) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeInt(setInheritShownWhenLocked);
                    if (this.mRemote.transact(142, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setInheritShowWhenLocked(token, setInheritShownWhenLocked);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setTurnScreenOn(IBinder token, boolean turnScreenOn) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeInt(turnScreenOn);
                    if (this.mRemote.transact(143, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setTurnScreenOn(token, turnScreenOn);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void registerRemoteAnimations(IBinder token, RemoteAnimationDefinition definition) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    if (definition != null) {
                        _data.writeInt(1);
                        definition.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(144, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().registerRemoteAnimations(token, definition);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void registerRemoteAnimationForNextActivityStart(String packageName, RemoteAnimationAdapter adapter) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    if (adapter != null) {
                        _data.writeInt(1);
                        adapter.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(145, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().registerRemoteAnimationForNextActivityStart(packageName, adapter);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void registerRemoteAnimationsForDisplay(int displayId, RemoteAnimationDefinition definition) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    if (definition != null) {
                        _data.writeInt(1);
                        definition.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(146, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().registerRemoteAnimationsForDisplay(displayId, definition);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void alwaysShowUnsupportedCompileSdkWarning(ComponentName activity) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (activity != null) {
                        _data.writeInt(1);
                        activity.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(147, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().alwaysShowUnsupportedCompileSdkWarning(activity);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setVrThread(int tid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(tid);
                    if (this.mRemote.transact(148, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setVrThread(tid);
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
                    if (this.mRemote.transact(149, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
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

            public void stopAppSwitches() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(150, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
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
                    if (this.mRemote.transact(151, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
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

            public void setActivityController(IActivityController watcher, boolean imAMonkey) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(watcher != null ? watcher.asBinder() : null);
                    _data.writeInt(imAMonkey);
                    if (this.mRemote.transact(152, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
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

            public void setVoiceKeepAwake(IVoiceInteractionSession session, boolean keepAwake) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(session != null ? session.asBinder() : null);
                    _data.writeInt(keepAwake);
                    if (this.mRemote.transact(153, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setVoiceKeepAwake(session, keepAwake);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getPackageScreenCompatMode(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    if (!this.mRemote.transact(154, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPackageScreenCompatMode(packageName);
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

            public void setPackageScreenCompatMode(String packageName, int mode) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(mode);
                    if (this.mRemote.transact(155, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
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

            public boolean getPackageAskScreenCompat(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    boolean z = false;
                    if (!this.mRemote.transact(156, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPackageAskScreenCompat(packageName);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _result = z;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setPackageAskScreenCompat(String packageName, boolean ask) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(ask);
                    if (this.mRemote.transact(157, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setPackageAskScreenCompat(packageName, ask);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void clearLaunchParamsForPackages(List<String> packageNames) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStringList(packageNames);
                    if (this.mRemote.transact(158, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().clearLaunchParamsForPackages(packageNames);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setDisplayToSingleTaskInstance(int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    if (this.mRemote.transact(159, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setDisplayToSingleTaskInstance(displayId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void restartActivityProcessIfVisible(IBinder activityToken) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(activityToken);
                    if (this.mRemote.transact(160, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().restartActivityProcessIfVisible(activityToken);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void onBackPressedOnTaskRoot(IBinder activityToken, IRequestFinishCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(activityToken);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(161, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onBackPressedOnTaskRoot(activityToken, callback);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IActivityTaskManager impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IActivityTaskManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
