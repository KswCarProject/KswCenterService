package android.content.pm;

import android.annotation.UnsupportedAppUsage;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.IPackageInstaller;
import android.content.pm.dex.IArtManager;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.PersistableBundle;
import android.os.RemoteException;
import android.text.TextUtils;
import java.util.List;

public interface IPackageManager extends IInterface {
    boolean activitySupportsIntent(ComponentName componentName, Intent intent, String str) throws RemoteException;

    void addCrossProfileIntentFilter(IntentFilter intentFilter, String str, int i, int i2, int i3) throws RemoteException;

    void addOnPermissionsChangeListener(IOnPermissionsChangeListener iOnPermissionsChangeListener) throws RemoteException;

    @UnsupportedAppUsage
    boolean addPermission(PermissionInfo permissionInfo) throws RemoteException;

    @UnsupportedAppUsage
    boolean addPermissionAsync(PermissionInfo permissionInfo) throws RemoteException;

    void addPersistentPreferredActivity(IntentFilter intentFilter, ComponentName componentName, int i) throws RemoteException;

    void addPreferredActivity(IntentFilter intentFilter, int i, ComponentName[] componentNameArr, ComponentName componentName, int i2) throws RemoteException;

    boolean addWhitelistedRestrictedPermission(String str, String str2, int i, int i2) throws RemoteException;

    boolean canForwardTo(Intent intent, String str, int i, int i2) throws RemoteException;

    boolean canRequestPackageInstalls(String str, int i) throws RemoteException;

    @UnsupportedAppUsage
    String[] canonicalToCurrentPackageNames(String[] strArr) throws RemoteException;

    void checkPackageStartable(String str, int i) throws RemoteException;

    @UnsupportedAppUsage
    int checkPermission(String str, String str2, int i) throws RemoteException;

    @UnsupportedAppUsage
    int checkSignatures(String str, String str2) throws RemoteException;

    int checkUidPermission(String str, int i) throws RemoteException;

    @UnsupportedAppUsage
    int checkUidSignatures(int i, int i2) throws RemoteException;

    void clearApplicationProfileData(String str) throws RemoteException;

    void clearApplicationUserData(String str, IPackageDataObserver iPackageDataObserver, int i) throws RemoteException;

    void clearCrossProfileIntentFilters(int i, String str) throws RemoteException;

    void clearPackagePersistentPreferredActivities(String str, int i) throws RemoteException;

    @UnsupportedAppUsage
    void clearPackagePreferredActivities(String str) throws RemoteException;

    boolean compileLayouts(String str) throws RemoteException;

    @UnsupportedAppUsage
    String[] currentToCanonicalPackageNames(String[] strArr) throws RemoteException;

    @UnsupportedAppUsage
    void deleteApplicationCacheFiles(String str, IPackageDataObserver iPackageDataObserver) throws RemoteException;

    void deleteApplicationCacheFilesAsUser(String str, int i, IPackageDataObserver iPackageDataObserver) throws RemoteException;

    void deletePackageAsUser(String str, int i, IPackageDeleteObserver iPackageDeleteObserver, int i2, int i3) throws RemoteException;

    void deletePackageVersioned(VersionedPackage versionedPackage, IPackageDeleteObserver2 iPackageDeleteObserver2, int i, int i2) throws RemoteException;

    void deletePreloadsFileCache() throws RemoteException;

    void dumpProfiles(String str) throws RemoteException;

    void enterSafeMode() throws RemoteException;

    void extendVerificationTimeout(int i, int i2, long j) throws RemoteException;

    ResolveInfo findPersistentPreferredActivity(Intent intent, int i) throws RemoteException;

    void finishPackageInstall(int i, boolean z) throws RemoteException;

    void flushPackageRestrictionsAsUser(int i) throws RemoteException;

    void forceDexOpt(String str) throws RemoteException;

    void freeStorage(String str, long j, int i, IntentSender intentSender) throws RemoteException;

    void freeStorageAndNotify(String str, long j, int i, IPackageDataObserver iPackageDataObserver) throws RemoteException;

    @UnsupportedAppUsage
    ActivityInfo getActivityInfo(ComponentName componentName, int i, int i2) throws RemoteException;

    ParceledListSlice getAllIntentFilters(String str) throws RemoteException;

    List<String> getAllPackages() throws RemoteException;

    ParceledListSlice getAllPermissionGroups(int i) throws RemoteException;

    @UnsupportedAppUsage
    String[] getAppOpPermissionPackages(String str) throws RemoteException;

    String getAppPredictionServicePackageName() throws RemoteException;

    @UnsupportedAppUsage
    int getApplicationEnabledSetting(String str, int i) throws RemoteException;

    boolean getApplicationHiddenSettingAsUser(String str, int i) throws RemoteException;

    @UnsupportedAppUsage
    ApplicationInfo getApplicationInfo(String str, int i, int i2) throws RemoteException;

    IArtManager getArtManager() throws RemoteException;

    String getAttentionServicePackageName() throws RemoteException;

    @UnsupportedAppUsage
    boolean getBlockUninstallForUser(String str, int i) throws RemoteException;

    ChangedPackages getChangedPackages(int i, int i2) throws RemoteException;

    @UnsupportedAppUsage
    int getComponentEnabledSetting(ComponentName componentName, int i) throws RemoteException;

    ParceledListSlice getDeclaredSharedLibraries(String str, int i, int i2) throws RemoteException;

    byte[] getDefaultAppsBackup(int i) throws RemoteException;

    String getDefaultBrowserPackageName(int i) throws RemoteException;

    @UnsupportedAppUsage
    int getFlagsForUid(int i) throws RemoteException;

    CharSequence getHarmfulAppWarning(String str, int i) throws RemoteException;

    @UnsupportedAppUsage
    ComponentName getHomeActivities(List<ResolveInfo> list) throws RemoteException;

    String getIncidentReportApproverPackageName() throws RemoteException;

    @UnsupportedAppUsage
    int getInstallLocation() throws RemoteException;

    int getInstallReason(String str, int i) throws RemoteException;

    @UnsupportedAppUsage
    ParceledListSlice getInstalledApplications(int i, int i2) throws RemoteException;

    List<ModuleInfo> getInstalledModules(int i) throws RemoteException;

    @UnsupportedAppUsage
    ParceledListSlice getInstalledPackages(int i, int i2) throws RemoteException;

    @UnsupportedAppUsage
    String getInstallerPackageName(String str) throws RemoteException;

    String getInstantAppAndroidId(String str, int i) throws RemoteException;

    byte[] getInstantAppCookie(String str, int i) throws RemoteException;

    Bitmap getInstantAppIcon(String str, int i) throws RemoteException;

    ComponentName getInstantAppInstallerComponent() throws RemoteException;

    ComponentName getInstantAppResolverComponent() throws RemoteException;

    ComponentName getInstantAppResolverSettingsComponent() throws RemoteException;

    ParceledListSlice getInstantApps(int i) throws RemoteException;

    @UnsupportedAppUsage
    InstrumentationInfo getInstrumentationInfo(ComponentName componentName, int i) throws RemoteException;

    byte[] getIntentFilterVerificationBackup(int i) throws RemoteException;

    ParceledListSlice getIntentFilterVerifications(String str) throws RemoteException;

    int getIntentVerificationStatus(String str, int i) throws RemoteException;

    KeySet getKeySetByAlias(String str, String str2) throws RemoteException;

    @UnsupportedAppUsage
    ResolveInfo getLastChosenActivity(Intent intent, String str, int i) throws RemoteException;

    ModuleInfo getModuleInfo(String str, int i) throws RemoteException;

    int getMoveStatus(int i) throws RemoteException;

    @UnsupportedAppUsage
    String getNameForUid(int i) throws RemoteException;

    String[] getNamesForUids(int[] iArr) throws RemoteException;

    int[] getPackageGids(String str, int i, int i2) throws RemoteException;

    @UnsupportedAppUsage
    PackageInfo getPackageInfo(String str, int i, int i2) throws RemoteException;

    PackageInfo getPackageInfoVersioned(VersionedPackage versionedPackage, int i, int i2) throws RemoteException;

    @UnsupportedAppUsage
    IPackageInstaller getPackageInstaller() throws RemoteException;

    void getPackageSizeInfo(String str, int i, IPackageStatsObserver iPackageStatsObserver) throws RemoteException;

    @UnsupportedAppUsage
    int getPackageUid(String str, int i, int i2) throws RemoteException;

    @UnsupportedAppUsage
    String[] getPackagesForUid(int i) throws RemoteException;

    ParceledListSlice getPackagesHoldingPermissions(String[] strArr, int i, int i2) throws RemoteException;

    @UnsupportedAppUsage
    String getPermissionControllerPackageName() throws RemoteException;

    int getPermissionFlags(String str, String str2, int i) throws RemoteException;

    @UnsupportedAppUsage
    PermissionGroupInfo getPermissionGroupInfo(String str, int i) throws RemoteException;

    PermissionInfo getPermissionInfo(String str, String str2, int i) throws RemoteException;

    ParceledListSlice getPersistentApplications(int i) throws RemoteException;

    @UnsupportedAppUsage
    int getPreferredActivities(List<IntentFilter> list, List<ComponentName> list2, String str) throws RemoteException;

    byte[] getPreferredActivityBackup(int i) throws RemoteException;

    int getPrivateFlagsForUid(int i) throws RemoteException;

    @UnsupportedAppUsage
    ProviderInfo getProviderInfo(ComponentName componentName, int i, int i2) throws RemoteException;

    @UnsupportedAppUsage
    ActivityInfo getReceiverInfo(ComponentName componentName, int i, int i2) throws RemoteException;

    int getRuntimePermissionsVersion(int i) throws RemoteException;

    @UnsupportedAppUsage
    ServiceInfo getServiceInfo(ComponentName componentName, int i, int i2) throws RemoteException;

    @UnsupportedAppUsage
    String getServicesSystemSharedLibraryPackageName() throws RemoteException;

    ParceledListSlice getSharedLibraries(String str, int i, int i2) throws RemoteException;

    @UnsupportedAppUsage
    String getSharedSystemSharedLibraryPackageName() throws RemoteException;

    KeySet getSigningKeySet(String str) throws RemoteException;

    PersistableBundle getSuspendedPackageAppExtras(String str, int i) throws RemoteException;

    ParceledListSlice getSystemAvailableFeatures() throws RemoteException;

    String getSystemCaptionsServicePackageName() throws RemoteException;

    @UnsupportedAppUsage
    String[] getSystemSharedLibraryNames() throws RemoteException;

    String getSystemTextClassifierPackageName() throws RemoteException;

    @UnsupportedAppUsage
    int getUidForSharedUser(String str) throws RemoteException;

    String[] getUnsuspendablePackagesForUser(String[] strArr, int i) throws RemoteException;

    VerifierDeviceIdentity getVerifierDeviceIdentity() throws RemoteException;

    String getWellbeingPackageName() throws RemoteException;

    List<String> getWhitelistedRestrictedPermissions(String str, int i, int i2) throws RemoteException;

    void grantDefaultPermissionsToActiveLuiApp(String str, int i) throws RemoteException;

    void grantDefaultPermissionsToEnabledCarrierApps(String[] strArr, int i) throws RemoteException;

    void grantDefaultPermissionsToEnabledImsServices(String[] strArr, int i) throws RemoteException;

    void grantDefaultPermissionsToEnabledTelephonyDataServices(String[] strArr, int i) throws RemoteException;

    @UnsupportedAppUsage
    void grantRuntimePermission(String str, String str2, int i) throws RemoteException;

    boolean hasSigningCertificate(String str, byte[] bArr, int i) throws RemoteException;

    boolean hasSystemFeature(String str, int i) throws RemoteException;

    @UnsupportedAppUsage
    boolean hasSystemUidErrors() throws RemoteException;

    boolean hasUidSigningCertificate(int i, byte[] bArr, int i2) throws RemoteException;

    int installExistingPackageAsUser(String str, int i, int i2, int i3, List<String> list) throws RemoteException;

    boolean isDeviceUpgrading() throws RemoteException;

    boolean isFirstBoot() throws RemoteException;

    boolean isInstantApp(String str, int i) throws RemoteException;

    boolean isOnlyCoreApps() throws RemoteException;

    @UnsupportedAppUsage
    boolean isPackageAvailable(String str, int i) throws RemoteException;

    boolean isPackageDeviceAdminOnAnyUser(String str) throws RemoteException;

    boolean isPackageSignedByKeySet(String str, KeySet keySet) throws RemoteException;

    boolean isPackageSignedByKeySetExactly(String str, KeySet keySet) throws RemoteException;

    boolean isPackageStateProtected(String str, int i) throws RemoteException;

    boolean isPackageSuspendedForUser(String str, int i) throws RemoteException;

    boolean isPermissionEnforced(String str) throws RemoteException;

    boolean isPermissionRevokedByPolicy(String str, String str2, int i) throws RemoteException;

    boolean isProtectedBroadcast(String str) throws RemoteException;

    @UnsupportedAppUsage
    boolean isSafeMode() throws RemoteException;

    @UnsupportedAppUsage
    boolean isStorageLow() throws RemoteException;

    @UnsupportedAppUsage
    boolean isUidPrivileged(int i) throws RemoteException;

    void logAppProcessStartIfNeeded(String str, int i, String str2, String str3, int i2) throws RemoteException;

    int movePackage(String str, String str2) throws RemoteException;

    int movePrimaryStorage(String str) throws RemoteException;

    void notifyDexLoad(String str, List<String> list, List<String> list2, String str2) throws RemoteException;

    void notifyPackageUse(String str, int i) throws RemoteException;

    void notifyPackagesReplacedReceived(String[] strArr) throws RemoteException;

    boolean performDexOptMode(String str, boolean z, String str2, boolean z2, boolean z3, String str3) throws RemoteException;

    boolean performDexOptSecondary(String str, String str2, boolean z) throws RemoteException;

    void performFstrimIfNeeded() throws RemoteException;

    ParceledListSlice queryContentProviders(String str, int i, int i2, String str2) throws RemoteException;

    @UnsupportedAppUsage
    ParceledListSlice queryInstrumentation(String str, int i) throws RemoteException;

    @UnsupportedAppUsage
    ParceledListSlice queryIntentActivities(Intent intent, String str, int i, int i2) throws RemoteException;

    ParceledListSlice queryIntentActivityOptions(ComponentName componentName, Intent[] intentArr, String[] strArr, Intent intent, String str, int i, int i2) throws RemoteException;

    ParceledListSlice queryIntentContentProviders(Intent intent, String str, int i, int i2) throws RemoteException;

    ParceledListSlice queryIntentReceivers(Intent intent, String str, int i, int i2) throws RemoteException;

    ParceledListSlice queryIntentServices(Intent intent, String str, int i, int i2) throws RemoteException;

    ParceledListSlice queryPermissionsByGroup(String str, int i) throws RemoteException;

    @UnsupportedAppUsage
    void querySyncProviders(List<String> list, List<ProviderInfo> list2) throws RemoteException;

    void reconcileSecondaryDexFiles(String str) throws RemoteException;

    void registerDexModule(String str, String str2, boolean z, IDexModuleRegisterCallback iDexModuleRegisterCallback) throws RemoteException;

    void registerMoveCallback(IPackageMoveObserver iPackageMoveObserver) throws RemoteException;

    void removeOnPermissionsChangeListener(IOnPermissionsChangeListener iOnPermissionsChangeListener) throws RemoteException;

    @UnsupportedAppUsage
    void removePermission(String str) throws RemoteException;

    boolean removeWhitelistedRestrictedPermission(String str, String str2, int i, int i2) throws RemoteException;

    @UnsupportedAppUsage
    void replacePreferredActivity(IntentFilter intentFilter, int i, ComponentName[] componentNameArr, ComponentName componentName, int i2) throws RemoteException;

    void resetApplicationPreferences(int i) throws RemoteException;

    void resetRuntimePermissions() throws RemoteException;

    ProviderInfo resolveContentProvider(String str, int i, int i2) throws RemoteException;

    @UnsupportedAppUsage
    ResolveInfo resolveIntent(Intent intent, String str, int i, int i2) throws RemoteException;

    ResolveInfo resolveService(Intent intent, String str, int i, int i2) throws RemoteException;

    void restoreDefaultApps(byte[] bArr, int i) throws RemoteException;

    void restoreIntentFilterVerification(byte[] bArr, int i) throws RemoteException;

    void restorePreferredActivities(byte[] bArr, int i) throws RemoteException;

    void revokeDefaultPermissionsFromDisabledTelephonyDataServices(String[] strArr, int i) throws RemoteException;

    void revokeDefaultPermissionsFromLuiApps(String[] strArr, int i) throws RemoteException;

    void revokeRuntimePermission(String str, String str2, int i) throws RemoteException;

    boolean runBackgroundDexoptJob(List<String> list) throws RemoteException;

    void sendDeviceCustomizationReadyBroadcast() throws RemoteException;

    void setApplicationCategoryHint(String str, int i, String str2) throws RemoteException;

    @UnsupportedAppUsage
    void setApplicationEnabledSetting(String str, int i, int i2, int i3, String str2) throws RemoteException;

    @UnsupportedAppUsage
    boolean setApplicationHiddenSettingAsUser(String str, boolean z, int i) throws RemoteException;

    boolean setBlockUninstallForUser(String str, boolean z, int i) throws RemoteException;

    @UnsupportedAppUsage
    void setComponentEnabledSetting(ComponentName componentName, int i, int i2, int i3) throws RemoteException;

    boolean setDefaultBrowserPackageName(String str, int i) throws RemoteException;

    String[] setDistractingPackageRestrictionsAsUser(String[] strArr, int i, int i2) throws RemoteException;

    void setHarmfulAppWarning(String str, CharSequence charSequence, int i) throws RemoteException;

    void setHomeActivity(ComponentName componentName, int i) throws RemoteException;

    boolean setInstallLocation(int i) throws RemoteException;

    @UnsupportedAppUsage
    void setInstallerPackageName(String str, String str2) throws RemoteException;

    boolean setInstantAppCookie(String str, byte[] bArr, int i) throws RemoteException;

    @UnsupportedAppUsage
    void setLastChosenActivity(Intent intent, String str, int i, IntentFilter intentFilter, int i2, ComponentName componentName) throws RemoteException;

    @UnsupportedAppUsage
    void setPackageStoppedState(String str, boolean z, int i) throws RemoteException;

    String[] setPackagesSuspendedAsUser(String[] strArr, boolean z, PersistableBundle persistableBundle, PersistableBundle persistableBundle2, SuspendDialogInfo suspendDialogInfo, String str, int i) throws RemoteException;

    void setPermissionEnforced(String str, boolean z) throws RemoteException;

    boolean setRequiredForSystemUser(String str, boolean z) throws RemoteException;

    void setRuntimePermissionsVersion(int i, int i2) throws RemoteException;

    void setSystemAppHiddenUntilInstalled(String str, boolean z) throws RemoteException;

    boolean setSystemAppInstallState(String str, boolean z, int i) throws RemoteException;

    void setUpdateAvailable(String str, boolean z) throws RemoteException;

    boolean shouldShowRequestPermissionRationale(String str, String str2, int i) throws RemoteException;

    void systemReady() throws RemoteException;

    void unregisterMoveCallback(IPackageMoveObserver iPackageMoveObserver) throws RemoteException;

    boolean updateIntentVerificationStatus(String str, int i, int i2) throws RemoteException;

    void updatePackagesIfNeeded() throws RemoteException;

    void updatePermissionFlags(String str, String str2, int i, int i2, boolean z, int i3) throws RemoteException;

    void updatePermissionFlagsForAllApps(int i, int i2, int i3) throws RemoteException;

    void verifyIntentFilter(int i, int i2, List<String> list) throws RemoteException;

    void verifyPendingInstall(int i, int i2) throws RemoteException;

    public static class Default implements IPackageManager {
        public void checkPackageStartable(String packageName, int userId) throws RemoteException {
        }

        public boolean isPackageAvailable(String packageName, int userId) throws RemoteException {
            return false;
        }

        public PackageInfo getPackageInfo(String packageName, int flags, int userId) throws RemoteException {
            return null;
        }

        public PackageInfo getPackageInfoVersioned(VersionedPackage versionedPackage, int flags, int userId) throws RemoteException {
            return null;
        }

        public int getPackageUid(String packageName, int flags, int userId) throws RemoteException {
            return 0;
        }

        public int[] getPackageGids(String packageName, int flags, int userId) throws RemoteException {
            return null;
        }

        public String[] currentToCanonicalPackageNames(String[] names) throws RemoteException {
            return null;
        }

        public String[] canonicalToCurrentPackageNames(String[] names) throws RemoteException {
            return null;
        }

        public PermissionInfo getPermissionInfo(String name, String packageName, int flags) throws RemoteException {
            return null;
        }

        public ParceledListSlice queryPermissionsByGroup(String group, int flags) throws RemoteException {
            return null;
        }

        public PermissionGroupInfo getPermissionGroupInfo(String name, int flags) throws RemoteException {
            return null;
        }

        public ParceledListSlice getAllPermissionGroups(int flags) throws RemoteException {
            return null;
        }

        public ApplicationInfo getApplicationInfo(String packageName, int flags, int userId) throws RemoteException {
            return null;
        }

        public ActivityInfo getActivityInfo(ComponentName className, int flags, int userId) throws RemoteException {
            return null;
        }

        public boolean activitySupportsIntent(ComponentName className, Intent intent, String resolvedType) throws RemoteException {
            return false;
        }

        public ActivityInfo getReceiverInfo(ComponentName className, int flags, int userId) throws RemoteException {
            return null;
        }

        public ServiceInfo getServiceInfo(ComponentName className, int flags, int userId) throws RemoteException {
            return null;
        }

        public ProviderInfo getProviderInfo(ComponentName className, int flags, int userId) throws RemoteException {
            return null;
        }

        public int checkPermission(String permName, String pkgName, int userId) throws RemoteException {
            return 0;
        }

        public int checkUidPermission(String permName, int uid) throws RemoteException {
            return 0;
        }

        public boolean addPermission(PermissionInfo info) throws RemoteException {
            return false;
        }

        public void removePermission(String name) throws RemoteException {
        }

        public void grantRuntimePermission(String packageName, String permissionName, int userId) throws RemoteException {
        }

        public void revokeRuntimePermission(String packageName, String permissionName, int userId) throws RemoteException {
        }

        public void resetRuntimePermissions() throws RemoteException {
        }

        public int getPermissionFlags(String permissionName, String packageName, int userId) throws RemoteException {
            return 0;
        }

        public void updatePermissionFlags(String permissionName, String packageName, int flagMask, int flagValues, boolean checkAdjustPolicyFlagPermission, int userId) throws RemoteException {
        }

        public void updatePermissionFlagsForAllApps(int flagMask, int flagValues, int userId) throws RemoteException {
        }

        public List<String> getWhitelistedRestrictedPermissions(String packageName, int flags, int userId) throws RemoteException {
            return null;
        }

        public boolean addWhitelistedRestrictedPermission(String packageName, String permission, int whitelistFlags, int userId) throws RemoteException {
            return false;
        }

        public boolean removeWhitelistedRestrictedPermission(String packageName, String permission, int whitelistFlags, int userId) throws RemoteException {
            return false;
        }

        public boolean shouldShowRequestPermissionRationale(String permissionName, String packageName, int userId) throws RemoteException {
            return false;
        }

        public boolean isProtectedBroadcast(String actionName) throws RemoteException {
            return false;
        }

        public int checkSignatures(String pkg1, String pkg2) throws RemoteException {
            return 0;
        }

        public int checkUidSignatures(int uid1, int uid2) throws RemoteException {
            return 0;
        }

        public List<String> getAllPackages() throws RemoteException {
            return null;
        }

        public String[] getPackagesForUid(int uid) throws RemoteException {
            return null;
        }

        public String getNameForUid(int uid) throws RemoteException {
            return null;
        }

        public String[] getNamesForUids(int[] uids) throws RemoteException {
            return null;
        }

        public int getUidForSharedUser(String sharedUserName) throws RemoteException {
            return 0;
        }

        public int getFlagsForUid(int uid) throws RemoteException {
            return 0;
        }

        public int getPrivateFlagsForUid(int uid) throws RemoteException {
            return 0;
        }

        public boolean isUidPrivileged(int uid) throws RemoteException {
            return false;
        }

        public String[] getAppOpPermissionPackages(String permissionName) throws RemoteException {
            return null;
        }

        public ResolveInfo resolveIntent(Intent intent, String resolvedType, int flags, int userId) throws RemoteException {
            return null;
        }

        public ResolveInfo findPersistentPreferredActivity(Intent intent, int userId) throws RemoteException {
            return null;
        }

        public boolean canForwardTo(Intent intent, String resolvedType, int sourceUserId, int targetUserId) throws RemoteException {
            return false;
        }

        public ParceledListSlice queryIntentActivities(Intent intent, String resolvedType, int flags, int userId) throws RemoteException {
            return null;
        }

        public ParceledListSlice queryIntentActivityOptions(ComponentName caller, Intent[] specifics, String[] specificTypes, Intent intent, String resolvedType, int flags, int userId) throws RemoteException {
            return null;
        }

        public ParceledListSlice queryIntentReceivers(Intent intent, String resolvedType, int flags, int userId) throws RemoteException {
            return null;
        }

        public ResolveInfo resolveService(Intent intent, String resolvedType, int flags, int userId) throws RemoteException {
            return null;
        }

        public ParceledListSlice queryIntentServices(Intent intent, String resolvedType, int flags, int userId) throws RemoteException {
            return null;
        }

        public ParceledListSlice queryIntentContentProviders(Intent intent, String resolvedType, int flags, int userId) throws RemoteException {
            return null;
        }

        public ParceledListSlice getInstalledPackages(int flags, int userId) throws RemoteException {
            return null;
        }

        public ParceledListSlice getPackagesHoldingPermissions(String[] permissions, int flags, int userId) throws RemoteException {
            return null;
        }

        public ParceledListSlice getInstalledApplications(int flags, int userId) throws RemoteException {
            return null;
        }

        public ParceledListSlice getPersistentApplications(int flags) throws RemoteException {
            return null;
        }

        public ProviderInfo resolveContentProvider(String name, int flags, int userId) throws RemoteException {
            return null;
        }

        public void querySyncProviders(List<String> list, List<ProviderInfo> list2) throws RemoteException {
        }

        public ParceledListSlice queryContentProviders(String processName, int uid, int flags, String metaDataKey) throws RemoteException {
            return null;
        }

        public InstrumentationInfo getInstrumentationInfo(ComponentName className, int flags) throws RemoteException {
            return null;
        }

        public ParceledListSlice queryInstrumentation(String targetPackage, int flags) throws RemoteException {
            return null;
        }

        public void finishPackageInstall(int token, boolean didLaunch) throws RemoteException {
        }

        public void setInstallerPackageName(String targetPackage, String installerPackageName) throws RemoteException {
        }

        public void setApplicationCategoryHint(String packageName, int categoryHint, String callerPackageName) throws RemoteException {
        }

        public void deletePackageAsUser(String packageName, int versionCode, IPackageDeleteObserver observer, int userId, int flags) throws RemoteException {
        }

        public void deletePackageVersioned(VersionedPackage versionedPackage, IPackageDeleteObserver2 observer, int userId, int flags) throws RemoteException {
        }

        public String getInstallerPackageName(String packageName) throws RemoteException {
            return null;
        }

        public void resetApplicationPreferences(int userId) throws RemoteException {
        }

        public ResolveInfo getLastChosenActivity(Intent intent, String resolvedType, int flags) throws RemoteException {
            return null;
        }

        public void setLastChosenActivity(Intent intent, String resolvedType, int flags, IntentFilter filter, int match, ComponentName activity) throws RemoteException {
        }

        public void addPreferredActivity(IntentFilter filter, int match, ComponentName[] set, ComponentName activity, int userId) throws RemoteException {
        }

        public void replacePreferredActivity(IntentFilter filter, int match, ComponentName[] set, ComponentName activity, int userId) throws RemoteException {
        }

        public void clearPackagePreferredActivities(String packageName) throws RemoteException {
        }

        public int getPreferredActivities(List<IntentFilter> list, List<ComponentName> list2, String packageName) throws RemoteException {
            return 0;
        }

        public void addPersistentPreferredActivity(IntentFilter filter, ComponentName activity, int userId) throws RemoteException {
        }

        public void clearPackagePersistentPreferredActivities(String packageName, int userId) throws RemoteException {
        }

        public void addCrossProfileIntentFilter(IntentFilter intentFilter, String ownerPackage, int sourceUserId, int targetUserId, int flags) throws RemoteException {
        }

        public void clearCrossProfileIntentFilters(int sourceUserId, String ownerPackage) throws RemoteException {
        }

        public String[] setDistractingPackageRestrictionsAsUser(String[] packageNames, int restrictionFlags, int userId) throws RemoteException {
            return null;
        }

        public String[] setPackagesSuspendedAsUser(String[] packageNames, boolean suspended, PersistableBundle appExtras, PersistableBundle launcherExtras, SuspendDialogInfo dialogInfo, String callingPackage, int userId) throws RemoteException {
            return null;
        }

        public String[] getUnsuspendablePackagesForUser(String[] packageNames, int userId) throws RemoteException {
            return null;
        }

        public boolean isPackageSuspendedForUser(String packageName, int userId) throws RemoteException {
            return false;
        }

        public PersistableBundle getSuspendedPackageAppExtras(String packageName, int userId) throws RemoteException {
            return null;
        }

        public byte[] getPreferredActivityBackup(int userId) throws RemoteException {
            return null;
        }

        public void restorePreferredActivities(byte[] backup, int userId) throws RemoteException {
        }

        public byte[] getDefaultAppsBackup(int userId) throws RemoteException {
            return null;
        }

        public void restoreDefaultApps(byte[] backup, int userId) throws RemoteException {
        }

        public byte[] getIntentFilterVerificationBackup(int userId) throws RemoteException {
            return null;
        }

        public void restoreIntentFilterVerification(byte[] backup, int userId) throws RemoteException {
        }

        public ComponentName getHomeActivities(List<ResolveInfo> list) throws RemoteException {
            return null;
        }

        public void setHomeActivity(ComponentName className, int userId) throws RemoteException {
        }

        public void setComponentEnabledSetting(ComponentName componentName, int newState, int flags, int userId) throws RemoteException {
        }

        public int getComponentEnabledSetting(ComponentName componentName, int userId) throws RemoteException {
            return 0;
        }

        public void setApplicationEnabledSetting(String packageName, int newState, int flags, int userId, String callingPackage) throws RemoteException {
        }

        public int getApplicationEnabledSetting(String packageName, int userId) throws RemoteException {
            return 0;
        }

        public void logAppProcessStartIfNeeded(String processName, int uid, String seinfo, String apkFile, int pid) throws RemoteException {
        }

        public void flushPackageRestrictionsAsUser(int userId) throws RemoteException {
        }

        public void setPackageStoppedState(String packageName, boolean stopped, int userId) throws RemoteException {
        }

        public void freeStorageAndNotify(String volumeUuid, long freeStorageSize, int storageFlags, IPackageDataObserver observer) throws RemoteException {
        }

        public void freeStorage(String volumeUuid, long freeStorageSize, int storageFlags, IntentSender pi) throws RemoteException {
        }

        public void deleteApplicationCacheFiles(String packageName, IPackageDataObserver observer) throws RemoteException {
        }

        public void deleteApplicationCacheFilesAsUser(String packageName, int userId, IPackageDataObserver observer) throws RemoteException {
        }

        public void clearApplicationUserData(String packageName, IPackageDataObserver observer, int userId) throws RemoteException {
        }

        public void clearApplicationProfileData(String packageName) throws RemoteException {
        }

        public void getPackageSizeInfo(String packageName, int userHandle, IPackageStatsObserver observer) throws RemoteException {
        }

        public String[] getSystemSharedLibraryNames() throws RemoteException {
            return null;
        }

        public ParceledListSlice getSystemAvailableFeatures() throws RemoteException {
            return null;
        }

        public boolean hasSystemFeature(String name, int version) throws RemoteException {
            return false;
        }

        public void enterSafeMode() throws RemoteException {
        }

        public boolean isSafeMode() throws RemoteException {
            return false;
        }

        public void systemReady() throws RemoteException {
        }

        public boolean hasSystemUidErrors() throws RemoteException {
            return false;
        }

        public void performFstrimIfNeeded() throws RemoteException {
        }

        public void updatePackagesIfNeeded() throws RemoteException {
        }

        public void notifyPackageUse(String packageName, int reason) throws RemoteException {
        }

        public void notifyDexLoad(String loadingPackageName, List<String> list, List<String> list2, String loaderIsa) throws RemoteException {
        }

        public void registerDexModule(String packageName, String dexModulePath, boolean isSharedModule, IDexModuleRegisterCallback callback) throws RemoteException {
        }

        public boolean performDexOptMode(String packageName, boolean checkProfiles, String targetCompilerFilter, boolean force, boolean bootComplete, String splitName) throws RemoteException {
            return false;
        }

        public boolean performDexOptSecondary(String packageName, String targetCompilerFilter, boolean force) throws RemoteException {
            return false;
        }

        public boolean compileLayouts(String packageName) throws RemoteException {
            return false;
        }

        public void dumpProfiles(String packageName) throws RemoteException {
        }

        public void forceDexOpt(String packageName) throws RemoteException {
        }

        public boolean runBackgroundDexoptJob(List<String> list) throws RemoteException {
            return false;
        }

        public void reconcileSecondaryDexFiles(String packageName) throws RemoteException {
        }

        public int getMoveStatus(int moveId) throws RemoteException {
            return 0;
        }

        public void registerMoveCallback(IPackageMoveObserver callback) throws RemoteException {
        }

        public void unregisterMoveCallback(IPackageMoveObserver callback) throws RemoteException {
        }

        public int movePackage(String packageName, String volumeUuid) throws RemoteException {
            return 0;
        }

        public int movePrimaryStorage(String volumeUuid) throws RemoteException {
            return 0;
        }

        public boolean addPermissionAsync(PermissionInfo info) throws RemoteException {
            return false;
        }

        public boolean setInstallLocation(int loc) throws RemoteException {
            return false;
        }

        public int getInstallLocation() throws RemoteException {
            return 0;
        }

        public int installExistingPackageAsUser(String packageName, int userId, int installFlags, int installReason, List<String> list) throws RemoteException {
            return 0;
        }

        public void verifyPendingInstall(int id, int verificationCode) throws RemoteException {
        }

        public void extendVerificationTimeout(int id, int verificationCodeAtTimeout, long millisecondsToDelay) throws RemoteException {
        }

        public void verifyIntentFilter(int id, int verificationCode, List<String> list) throws RemoteException {
        }

        public int getIntentVerificationStatus(String packageName, int userId) throws RemoteException {
            return 0;
        }

        public boolean updateIntentVerificationStatus(String packageName, int status, int userId) throws RemoteException {
            return false;
        }

        public ParceledListSlice getIntentFilterVerifications(String packageName) throws RemoteException {
            return null;
        }

        public ParceledListSlice getAllIntentFilters(String packageName) throws RemoteException {
            return null;
        }

        public boolean setDefaultBrowserPackageName(String packageName, int userId) throws RemoteException {
            return false;
        }

        public String getDefaultBrowserPackageName(int userId) throws RemoteException {
            return null;
        }

        public VerifierDeviceIdentity getVerifierDeviceIdentity() throws RemoteException {
            return null;
        }

        public boolean isFirstBoot() throws RemoteException {
            return false;
        }

        public boolean isOnlyCoreApps() throws RemoteException {
            return false;
        }

        public boolean isDeviceUpgrading() throws RemoteException {
            return false;
        }

        public void setPermissionEnforced(String permission, boolean enforced) throws RemoteException {
        }

        public boolean isPermissionEnforced(String permission) throws RemoteException {
            return false;
        }

        public boolean isStorageLow() throws RemoteException {
            return false;
        }

        public boolean setApplicationHiddenSettingAsUser(String packageName, boolean hidden, int userId) throws RemoteException {
            return false;
        }

        public boolean getApplicationHiddenSettingAsUser(String packageName, int userId) throws RemoteException {
            return false;
        }

        public void setSystemAppHiddenUntilInstalled(String packageName, boolean hidden) throws RemoteException {
        }

        public boolean setSystemAppInstallState(String packageName, boolean installed, int userId) throws RemoteException {
            return false;
        }

        public IPackageInstaller getPackageInstaller() throws RemoteException {
            return null;
        }

        public boolean setBlockUninstallForUser(String packageName, boolean blockUninstall, int userId) throws RemoteException {
            return false;
        }

        public boolean getBlockUninstallForUser(String packageName, int userId) throws RemoteException {
            return false;
        }

        public KeySet getKeySetByAlias(String packageName, String alias) throws RemoteException {
            return null;
        }

        public KeySet getSigningKeySet(String packageName) throws RemoteException {
            return null;
        }

        public boolean isPackageSignedByKeySet(String packageName, KeySet ks) throws RemoteException {
            return false;
        }

        public boolean isPackageSignedByKeySetExactly(String packageName, KeySet ks) throws RemoteException {
            return false;
        }

        public void addOnPermissionsChangeListener(IOnPermissionsChangeListener listener) throws RemoteException {
        }

        public void removeOnPermissionsChangeListener(IOnPermissionsChangeListener listener) throws RemoteException {
        }

        public void grantDefaultPermissionsToEnabledCarrierApps(String[] packageNames, int userId) throws RemoteException {
        }

        public void grantDefaultPermissionsToEnabledImsServices(String[] packageNames, int userId) throws RemoteException {
        }

        public void grantDefaultPermissionsToEnabledTelephonyDataServices(String[] packageNames, int userId) throws RemoteException {
        }

        public void revokeDefaultPermissionsFromDisabledTelephonyDataServices(String[] packageNames, int userId) throws RemoteException {
        }

        public void grantDefaultPermissionsToActiveLuiApp(String packageName, int userId) throws RemoteException {
        }

        public void revokeDefaultPermissionsFromLuiApps(String[] packageNames, int userId) throws RemoteException {
        }

        public boolean isPermissionRevokedByPolicy(String permission, String packageName, int userId) throws RemoteException {
            return false;
        }

        public String getPermissionControllerPackageName() throws RemoteException {
            return null;
        }

        public ParceledListSlice getInstantApps(int userId) throws RemoteException {
            return null;
        }

        public byte[] getInstantAppCookie(String packageName, int userId) throws RemoteException {
            return null;
        }

        public boolean setInstantAppCookie(String packageName, byte[] cookie, int userId) throws RemoteException {
            return false;
        }

        public Bitmap getInstantAppIcon(String packageName, int userId) throws RemoteException {
            return null;
        }

        public boolean isInstantApp(String packageName, int userId) throws RemoteException {
            return false;
        }

        public boolean setRequiredForSystemUser(String packageName, boolean systemUserApp) throws RemoteException {
            return false;
        }

        public void setUpdateAvailable(String packageName, boolean updateAvaialble) throws RemoteException {
        }

        public String getServicesSystemSharedLibraryPackageName() throws RemoteException {
            return null;
        }

        public String getSharedSystemSharedLibraryPackageName() throws RemoteException {
            return null;
        }

        public ChangedPackages getChangedPackages(int sequenceNumber, int userId) throws RemoteException {
            return null;
        }

        public boolean isPackageDeviceAdminOnAnyUser(String packageName) throws RemoteException {
            return false;
        }

        public int getInstallReason(String packageName, int userId) throws RemoteException {
            return 0;
        }

        public ParceledListSlice getSharedLibraries(String packageName, int flags, int userId) throws RemoteException {
            return null;
        }

        public ParceledListSlice getDeclaredSharedLibraries(String packageName, int flags, int userId) throws RemoteException {
            return null;
        }

        public boolean canRequestPackageInstalls(String packageName, int userId) throws RemoteException {
            return false;
        }

        public void deletePreloadsFileCache() throws RemoteException {
        }

        public ComponentName getInstantAppResolverComponent() throws RemoteException {
            return null;
        }

        public ComponentName getInstantAppResolverSettingsComponent() throws RemoteException {
            return null;
        }

        public ComponentName getInstantAppInstallerComponent() throws RemoteException {
            return null;
        }

        public String getInstantAppAndroidId(String packageName, int userId) throws RemoteException {
            return null;
        }

        public IArtManager getArtManager() throws RemoteException {
            return null;
        }

        public void setHarmfulAppWarning(String packageName, CharSequence warning, int userId) throws RemoteException {
        }

        public CharSequence getHarmfulAppWarning(String packageName, int userId) throws RemoteException {
            return null;
        }

        public boolean hasSigningCertificate(String packageName, byte[] signingCertificate, int flags) throws RemoteException {
            return false;
        }

        public boolean hasUidSigningCertificate(int uid, byte[] signingCertificate, int flags) throws RemoteException {
            return false;
        }

        public String getSystemTextClassifierPackageName() throws RemoteException {
            return null;
        }

        public String getAttentionServicePackageName() throws RemoteException {
            return null;
        }

        public String getWellbeingPackageName() throws RemoteException {
            return null;
        }

        public String getAppPredictionServicePackageName() throws RemoteException {
            return null;
        }

        public String getSystemCaptionsServicePackageName() throws RemoteException {
            return null;
        }

        public String getIncidentReportApproverPackageName() throws RemoteException {
            return null;
        }

        public boolean isPackageStateProtected(String packageName, int userId) throws RemoteException {
            return false;
        }

        public void sendDeviceCustomizationReadyBroadcast() throws RemoteException {
        }

        public List<ModuleInfo> getInstalledModules(int flags) throws RemoteException {
            return null;
        }

        public ModuleInfo getModuleInfo(String packageName, int flags) throws RemoteException {
            return null;
        }

        public int getRuntimePermissionsVersion(int userId) throws RemoteException {
            return 0;
        }

        public void setRuntimePermissionsVersion(int version, int userId) throws RemoteException {
        }

        public void notifyPackagesReplacedReceived(String[] packages) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IPackageManager {
        private static final String DESCRIPTOR = "android.content.pm.IPackageManager";
        static final int TRANSACTION_activitySupportsIntent = 15;
        static final int TRANSACTION_addCrossProfileIntentFilter = 78;
        static final int TRANSACTION_addOnPermissionsChangeListener = 162;
        static final int TRANSACTION_addPermission = 21;
        static final int TRANSACTION_addPermissionAsync = 131;
        static final int TRANSACTION_addPersistentPreferredActivity = 76;
        static final int TRANSACTION_addPreferredActivity = 72;
        static final int TRANSACTION_addWhitelistedRestrictedPermission = 30;
        static final int TRANSACTION_canForwardTo = 47;
        static final int TRANSACTION_canRequestPackageInstalls = 186;
        static final int TRANSACTION_canonicalToCurrentPackageNames = 8;
        static final int TRANSACTION_checkPackageStartable = 1;
        static final int TRANSACTION_checkPermission = 19;
        static final int TRANSACTION_checkSignatures = 34;
        static final int TRANSACTION_checkUidPermission = 20;
        static final int TRANSACTION_checkUidSignatures = 35;
        static final int TRANSACTION_clearApplicationProfileData = 105;
        static final int TRANSACTION_clearApplicationUserData = 104;
        static final int TRANSACTION_clearCrossProfileIntentFilters = 79;
        static final int TRANSACTION_clearPackagePersistentPreferredActivities = 77;
        static final int TRANSACTION_clearPackagePreferredActivities = 74;
        static final int TRANSACTION_compileLayouts = 121;
        static final int TRANSACTION_currentToCanonicalPackageNames = 7;
        static final int TRANSACTION_deleteApplicationCacheFiles = 102;
        static final int TRANSACTION_deleteApplicationCacheFilesAsUser = 103;
        static final int TRANSACTION_deletePackageAsUser = 66;
        static final int TRANSACTION_deletePackageVersioned = 67;
        static final int TRANSACTION_deletePreloadsFileCache = 187;
        static final int TRANSACTION_dumpProfiles = 122;
        static final int TRANSACTION_enterSafeMode = 110;
        static final int TRANSACTION_extendVerificationTimeout = 136;
        static final int TRANSACTION_findPersistentPreferredActivity = 46;
        static final int TRANSACTION_finishPackageInstall = 63;
        static final int TRANSACTION_flushPackageRestrictionsAsUser = 98;
        static final int TRANSACTION_forceDexOpt = 123;
        static final int TRANSACTION_freeStorage = 101;
        static final int TRANSACTION_freeStorageAndNotify = 100;
        static final int TRANSACTION_getActivityInfo = 14;
        static final int TRANSACTION_getAllIntentFilters = 141;
        static final int TRANSACTION_getAllPackages = 36;
        static final int TRANSACTION_getAllPermissionGroups = 12;
        static final int TRANSACTION_getAppOpPermissionPackages = 44;
        static final int TRANSACTION_getAppPredictionServicePackageName = 200;
        static final int TRANSACTION_getApplicationEnabledSetting = 96;
        static final int TRANSACTION_getApplicationHiddenSettingAsUser = 152;
        static final int TRANSACTION_getApplicationInfo = 13;
        static final int TRANSACTION_getArtManager = 192;
        static final int TRANSACTION_getAttentionServicePackageName = 198;
        static final int TRANSACTION_getBlockUninstallForUser = 157;
        static final int TRANSACTION_getChangedPackages = 181;
        static final int TRANSACTION_getComponentEnabledSetting = 94;
        static final int TRANSACTION_getDeclaredSharedLibraries = 185;
        static final int TRANSACTION_getDefaultAppsBackup = 87;
        static final int TRANSACTION_getDefaultBrowserPackageName = 143;
        static final int TRANSACTION_getFlagsForUid = 41;
        static final int TRANSACTION_getHarmfulAppWarning = 194;
        static final int TRANSACTION_getHomeActivities = 91;
        static final int TRANSACTION_getIncidentReportApproverPackageName = 202;
        static final int TRANSACTION_getInstallLocation = 133;
        static final int TRANSACTION_getInstallReason = 183;
        static final int TRANSACTION_getInstalledApplications = 56;
        static final int TRANSACTION_getInstalledModules = 205;
        static final int TRANSACTION_getInstalledPackages = 54;
        static final int TRANSACTION_getInstallerPackageName = 68;
        static final int TRANSACTION_getInstantAppAndroidId = 191;
        static final int TRANSACTION_getInstantAppCookie = 173;
        static final int TRANSACTION_getInstantAppIcon = 175;
        static final int TRANSACTION_getInstantAppInstallerComponent = 190;
        static final int TRANSACTION_getInstantAppResolverComponent = 188;
        static final int TRANSACTION_getInstantAppResolverSettingsComponent = 189;
        static final int TRANSACTION_getInstantApps = 172;
        static final int TRANSACTION_getInstrumentationInfo = 61;
        static final int TRANSACTION_getIntentFilterVerificationBackup = 89;
        static final int TRANSACTION_getIntentFilterVerifications = 140;
        static final int TRANSACTION_getIntentVerificationStatus = 138;
        static final int TRANSACTION_getKeySetByAlias = 158;
        static final int TRANSACTION_getLastChosenActivity = 70;
        static final int TRANSACTION_getModuleInfo = 206;
        static final int TRANSACTION_getMoveStatus = 126;
        static final int TRANSACTION_getNameForUid = 38;
        static final int TRANSACTION_getNamesForUids = 39;
        static final int TRANSACTION_getPackageGids = 6;
        static final int TRANSACTION_getPackageInfo = 3;
        static final int TRANSACTION_getPackageInfoVersioned = 4;
        static final int TRANSACTION_getPackageInstaller = 155;
        static final int TRANSACTION_getPackageSizeInfo = 106;
        static final int TRANSACTION_getPackageUid = 5;
        static final int TRANSACTION_getPackagesForUid = 37;
        static final int TRANSACTION_getPackagesHoldingPermissions = 55;
        static final int TRANSACTION_getPermissionControllerPackageName = 171;
        static final int TRANSACTION_getPermissionFlags = 26;
        static final int TRANSACTION_getPermissionGroupInfo = 11;
        static final int TRANSACTION_getPermissionInfo = 9;
        static final int TRANSACTION_getPersistentApplications = 57;
        static final int TRANSACTION_getPreferredActivities = 75;
        static final int TRANSACTION_getPreferredActivityBackup = 85;
        static final int TRANSACTION_getPrivateFlagsForUid = 42;
        static final int TRANSACTION_getProviderInfo = 18;
        static final int TRANSACTION_getReceiverInfo = 16;
        static final int TRANSACTION_getRuntimePermissionsVersion = 207;
        static final int TRANSACTION_getServiceInfo = 17;
        static final int TRANSACTION_getServicesSystemSharedLibraryPackageName = 179;
        static final int TRANSACTION_getSharedLibraries = 184;
        static final int TRANSACTION_getSharedSystemSharedLibraryPackageName = 180;
        static final int TRANSACTION_getSigningKeySet = 159;
        static final int TRANSACTION_getSuspendedPackageAppExtras = 84;
        static final int TRANSACTION_getSystemAvailableFeatures = 108;
        static final int TRANSACTION_getSystemCaptionsServicePackageName = 201;
        static final int TRANSACTION_getSystemSharedLibraryNames = 107;
        static final int TRANSACTION_getSystemTextClassifierPackageName = 197;
        static final int TRANSACTION_getUidForSharedUser = 40;
        static final int TRANSACTION_getUnsuspendablePackagesForUser = 82;
        static final int TRANSACTION_getVerifierDeviceIdentity = 144;
        static final int TRANSACTION_getWellbeingPackageName = 199;
        static final int TRANSACTION_getWhitelistedRestrictedPermissions = 29;
        static final int TRANSACTION_grantDefaultPermissionsToActiveLuiApp = 168;
        static final int TRANSACTION_grantDefaultPermissionsToEnabledCarrierApps = 164;
        static final int TRANSACTION_grantDefaultPermissionsToEnabledImsServices = 165;
        static final int TRANSACTION_grantDefaultPermissionsToEnabledTelephonyDataServices = 166;
        static final int TRANSACTION_grantRuntimePermission = 23;
        static final int TRANSACTION_hasSigningCertificate = 195;
        static final int TRANSACTION_hasSystemFeature = 109;
        static final int TRANSACTION_hasSystemUidErrors = 113;
        static final int TRANSACTION_hasUidSigningCertificate = 196;
        static final int TRANSACTION_installExistingPackageAsUser = 134;
        static final int TRANSACTION_isDeviceUpgrading = 147;
        static final int TRANSACTION_isFirstBoot = 145;
        static final int TRANSACTION_isInstantApp = 176;
        static final int TRANSACTION_isOnlyCoreApps = 146;
        static final int TRANSACTION_isPackageAvailable = 2;
        static final int TRANSACTION_isPackageDeviceAdminOnAnyUser = 182;
        static final int TRANSACTION_isPackageSignedByKeySet = 160;
        static final int TRANSACTION_isPackageSignedByKeySetExactly = 161;
        static final int TRANSACTION_isPackageStateProtected = 203;
        static final int TRANSACTION_isPackageSuspendedForUser = 83;
        static final int TRANSACTION_isPermissionEnforced = 149;
        static final int TRANSACTION_isPermissionRevokedByPolicy = 170;
        static final int TRANSACTION_isProtectedBroadcast = 33;
        static final int TRANSACTION_isSafeMode = 111;
        static final int TRANSACTION_isStorageLow = 150;
        static final int TRANSACTION_isUidPrivileged = 43;
        static final int TRANSACTION_logAppProcessStartIfNeeded = 97;
        static final int TRANSACTION_movePackage = 129;
        static final int TRANSACTION_movePrimaryStorage = 130;
        static final int TRANSACTION_notifyDexLoad = 117;
        static final int TRANSACTION_notifyPackageUse = 116;
        static final int TRANSACTION_notifyPackagesReplacedReceived = 209;
        static final int TRANSACTION_performDexOptMode = 119;
        static final int TRANSACTION_performDexOptSecondary = 120;
        static final int TRANSACTION_performFstrimIfNeeded = 114;
        static final int TRANSACTION_queryContentProviders = 60;
        static final int TRANSACTION_queryInstrumentation = 62;
        static final int TRANSACTION_queryIntentActivities = 48;
        static final int TRANSACTION_queryIntentActivityOptions = 49;
        static final int TRANSACTION_queryIntentContentProviders = 53;
        static final int TRANSACTION_queryIntentReceivers = 50;
        static final int TRANSACTION_queryIntentServices = 52;
        static final int TRANSACTION_queryPermissionsByGroup = 10;
        static final int TRANSACTION_querySyncProviders = 59;
        static final int TRANSACTION_reconcileSecondaryDexFiles = 125;
        static final int TRANSACTION_registerDexModule = 118;
        static final int TRANSACTION_registerMoveCallback = 127;
        static final int TRANSACTION_removeOnPermissionsChangeListener = 163;
        static final int TRANSACTION_removePermission = 22;
        static final int TRANSACTION_removeWhitelistedRestrictedPermission = 31;
        static final int TRANSACTION_replacePreferredActivity = 73;
        static final int TRANSACTION_resetApplicationPreferences = 69;
        static final int TRANSACTION_resetRuntimePermissions = 25;
        static final int TRANSACTION_resolveContentProvider = 58;
        static final int TRANSACTION_resolveIntent = 45;
        static final int TRANSACTION_resolveService = 51;
        static final int TRANSACTION_restoreDefaultApps = 88;
        static final int TRANSACTION_restoreIntentFilterVerification = 90;
        static final int TRANSACTION_restorePreferredActivities = 86;
        static final int TRANSACTION_revokeDefaultPermissionsFromDisabledTelephonyDataServices = 167;
        static final int TRANSACTION_revokeDefaultPermissionsFromLuiApps = 169;
        static final int TRANSACTION_revokeRuntimePermission = 24;
        static final int TRANSACTION_runBackgroundDexoptJob = 124;
        static final int TRANSACTION_sendDeviceCustomizationReadyBroadcast = 204;
        static final int TRANSACTION_setApplicationCategoryHint = 65;
        static final int TRANSACTION_setApplicationEnabledSetting = 95;
        static final int TRANSACTION_setApplicationHiddenSettingAsUser = 151;
        static final int TRANSACTION_setBlockUninstallForUser = 156;
        static final int TRANSACTION_setComponentEnabledSetting = 93;
        static final int TRANSACTION_setDefaultBrowserPackageName = 142;
        static final int TRANSACTION_setDistractingPackageRestrictionsAsUser = 80;
        static final int TRANSACTION_setHarmfulAppWarning = 193;
        static final int TRANSACTION_setHomeActivity = 92;
        static final int TRANSACTION_setInstallLocation = 132;
        static final int TRANSACTION_setInstallerPackageName = 64;
        static final int TRANSACTION_setInstantAppCookie = 174;
        static final int TRANSACTION_setLastChosenActivity = 71;
        static final int TRANSACTION_setPackageStoppedState = 99;
        static final int TRANSACTION_setPackagesSuspendedAsUser = 81;
        static final int TRANSACTION_setPermissionEnforced = 148;
        static final int TRANSACTION_setRequiredForSystemUser = 177;
        static final int TRANSACTION_setRuntimePermissionsVersion = 208;
        static final int TRANSACTION_setSystemAppHiddenUntilInstalled = 153;
        static final int TRANSACTION_setSystemAppInstallState = 154;
        static final int TRANSACTION_setUpdateAvailable = 178;
        static final int TRANSACTION_shouldShowRequestPermissionRationale = 32;
        static final int TRANSACTION_systemReady = 112;
        static final int TRANSACTION_unregisterMoveCallback = 128;
        static final int TRANSACTION_updateIntentVerificationStatus = 139;
        static final int TRANSACTION_updatePackagesIfNeeded = 115;
        static final int TRANSACTION_updatePermissionFlags = 27;
        static final int TRANSACTION_updatePermissionFlagsForAllApps = 28;
        static final int TRANSACTION_verifyIntentFilter = 137;
        static final int TRANSACTION_verifyPendingInstall = 135;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IPackageManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IPackageManager)) {
                return new Proxy(obj);
            }
            return (IPackageManager) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "checkPackageStartable";
                case 2:
                    return "isPackageAvailable";
                case 3:
                    return "getPackageInfo";
                case 4:
                    return "getPackageInfoVersioned";
                case 5:
                    return "getPackageUid";
                case 6:
                    return "getPackageGids";
                case 7:
                    return "currentToCanonicalPackageNames";
                case 8:
                    return "canonicalToCurrentPackageNames";
                case 9:
                    return "getPermissionInfo";
                case 10:
                    return "queryPermissionsByGroup";
                case 11:
                    return "getPermissionGroupInfo";
                case 12:
                    return "getAllPermissionGroups";
                case 13:
                    return "getApplicationInfo";
                case 14:
                    return "getActivityInfo";
                case 15:
                    return "activitySupportsIntent";
                case 16:
                    return "getReceiverInfo";
                case 17:
                    return "getServiceInfo";
                case 18:
                    return "getProviderInfo";
                case 19:
                    return "checkPermission";
                case 20:
                    return "checkUidPermission";
                case 21:
                    return "addPermission";
                case 22:
                    return "removePermission";
                case 23:
                    return "grantRuntimePermission";
                case 24:
                    return "revokeRuntimePermission";
                case 25:
                    return "resetRuntimePermissions";
                case 26:
                    return "getPermissionFlags";
                case 27:
                    return "updatePermissionFlags";
                case 28:
                    return "updatePermissionFlagsForAllApps";
                case 29:
                    return "getWhitelistedRestrictedPermissions";
                case 30:
                    return "addWhitelistedRestrictedPermission";
                case 31:
                    return "removeWhitelistedRestrictedPermission";
                case 32:
                    return "shouldShowRequestPermissionRationale";
                case 33:
                    return "isProtectedBroadcast";
                case 34:
                    return "checkSignatures";
                case 35:
                    return "checkUidSignatures";
                case 36:
                    return "getAllPackages";
                case 37:
                    return "getPackagesForUid";
                case 38:
                    return "getNameForUid";
                case 39:
                    return "getNamesForUids";
                case 40:
                    return "getUidForSharedUser";
                case 41:
                    return "getFlagsForUid";
                case 42:
                    return "getPrivateFlagsForUid";
                case 43:
                    return "isUidPrivileged";
                case 44:
                    return "getAppOpPermissionPackages";
                case 45:
                    return "resolveIntent";
                case 46:
                    return "findPersistentPreferredActivity";
                case 47:
                    return "canForwardTo";
                case 48:
                    return "queryIntentActivities";
                case 49:
                    return "queryIntentActivityOptions";
                case 50:
                    return "queryIntentReceivers";
                case 51:
                    return "resolveService";
                case 52:
                    return "queryIntentServices";
                case 53:
                    return "queryIntentContentProviders";
                case 54:
                    return "getInstalledPackages";
                case 55:
                    return "getPackagesHoldingPermissions";
                case 56:
                    return "getInstalledApplications";
                case 57:
                    return "getPersistentApplications";
                case 58:
                    return "resolveContentProvider";
                case 59:
                    return "querySyncProviders";
                case 60:
                    return "queryContentProviders";
                case 61:
                    return "getInstrumentationInfo";
                case 62:
                    return "queryInstrumentation";
                case 63:
                    return "finishPackageInstall";
                case 64:
                    return "setInstallerPackageName";
                case 65:
                    return "setApplicationCategoryHint";
                case 66:
                    return "deletePackageAsUser";
                case 67:
                    return "deletePackageVersioned";
                case 68:
                    return "getInstallerPackageName";
                case 69:
                    return "resetApplicationPreferences";
                case 70:
                    return "getLastChosenActivity";
                case 71:
                    return "setLastChosenActivity";
                case 72:
                    return "addPreferredActivity";
                case 73:
                    return "replacePreferredActivity";
                case 74:
                    return "clearPackagePreferredActivities";
                case 75:
                    return "getPreferredActivities";
                case 76:
                    return "addPersistentPreferredActivity";
                case 77:
                    return "clearPackagePersistentPreferredActivities";
                case 78:
                    return "addCrossProfileIntentFilter";
                case 79:
                    return "clearCrossProfileIntentFilters";
                case 80:
                    return "setDistractingPackageRestrictionsAsUser";
                case 81:
                    return "setPackagesSuspendedAsUser";
                case 82:
                    return "getUnsuspendablePackagesForUser";
                case 83:
                    return "isPackageSuspendedForUser";
                case 84:
                    return "getSuspendedPackageAppExtras";
                case 85:
                    return "getPreferredActivityBackup";
                case 86:
                    return "restorePreferredActivities";
                case 87:
                    return "getDefaultAppsBackup";
                case 88:
                    return "restoreDefaultApps";
                case 89:
                    return "getIntentFilterVerificationBackup";
                case 90:
                    return "restoreIntentFilterVerification";
                case 91:
                    return "getHomeActivities";
                case 92:
                    return "setHomeActivity";
                case 93:
                    return "setComponentEnabledSetting";
                case 94:
                    return "getComponentEnabledSetting";
                case 95:
                    return "setApplicationEnabledSetting";
                case 96:
                    return "getApplicationEnabledSetting";
                case 97:
                    return "logAppProcessStartIfNeeded";
                case 98:
                    return "flushPackageRestrictionsAsUser";
                case 99:
                    return "setPackageStoppedState";
                case 100:
                    return "freeStorageAndNotify";
                case 101:
                    return "freeStorage";
                case 102:
                    return "deleteApplicationCacheFiles";
                case 103:
                    return "deleteApplicationCacheFilesAsUser";
                case 104:
                    return "clearApplicationUserData";
                case 105:
                    return "clearApplicationProfileData";
                case 106:
                    return "getPackageSizeInfo";
                case 107:
                    return "getSystemSharedLibraryNames";
                case 108:
                    return "getSystemAvailableFeatures";
                case 109:
                    return "hasSystemFeature";
                case 110:
                    return "enterSafeMode";
                case 111:
                    return "isSafeMode";
                case 112:
                    return "systemReady";
                case 113:
                    return "hasSystemUidErrors";
                case 114:
                    return "performFstrimIfNeeded";
                case 115:
                    return "updatePackagesIfNeeded";
                case 116:
                    return "notifyPackageUse";
                case 117:
                    return "notifyDexLoad";
                case 118:
                    return "registerDexModule";
                case 119:
                    return "performDexOptMode";
                case 120:
                    return "performDexOptSecondary";
                case 121:
                    return "compileLayouts";
                case 122:
                    return "dumpProfiles";
                case 123:
                    return "forceDexOpt";
                case 124:
                    return "runBackgroundDexoptJob";
                case 125:
                    return "reconcileSecondaryDexFiles";
                case 126:
                    return "getMoveStatus";
                case 127:
                    return "registerMoveCallback";
                case 128:
                    return "unregisterMoveCallback";
                case 129:
                    return "movePackage";
                case 130:
                    return "movePrimaryStorage";
                case 131:
                    return "addPermissionAsync";
                case 132:
                    return "setInstallLocation";
                case 133:
                    return "getInstallLocation";
                case 134:
                    return "installExistingPackageAsUser";
                case 135:
                    return "verifyPendingInstall";
                case 136:
                    return "extendVerificationTimeout";
                case 137:
                    return "verifyIntentFilter";
                case 138:
                    return "getIntentVerificationStatus";
                case 139:
                    return "updateIntentVerificationStatus";
                case 140:
                    return "getIntentFilterVerifications";
                case 141:
                    return "getAllIntentFilters";
                case 142:
                    return "setDefaultBrowserPackageName";
                case 143:
                    return "getDefaultBrowserPackageName";
                case 144:
                    return "getVerifierDeviceIdentity";
                case 145:
                    return "isFirstBoot";
                case 146:
                    return "isOnlyCoreApps";
                case 147:
                    return "isDeviceUpgrading";
                case 148:
                    return "setPermissionEnforced";
                case 149:
                    return "isPermissionEnforced";
                case 150:
                    return "isStorageLow";
                case 151:
                    return "setApplicationHiddenSettingAsUser";
                case 152:
                    return "getApplicationHiddenSettingAsUser";
                case 153:
                    return "setSystemAppHiddenUntilInstalled";
                case 154:
                    return "setSystemAppInstallState";
                case 155:
                    return "getPackageInstaller";
                case 156:
                    return "setBlockUninstallForUser";
                case 157:
                    return "getBlockUninstallForUser";
                case 158:
                    return "getKeySetByAlias";
                case 159:
                    return "getSigningKeySet";
                case 160:
                    return "isPackageSignedByKeySet";
                case 161:
                    return "isPackageSignedByKeySetExactly";
                case 162:
                    return "addOnPermissionsChangeListener";
                case 163:
                    return "removeOnPermissionsChangeListener";
                case 164:
                    return "grantDefaultPermissionsToEnabledCarrierApps";
                case 165:
                    return "grantDefaultPermissionsToEnabledImsServices";
                case 166:
                    return "grantDefaultPermissionsToEnabledTelephonyDataServices";
                case 167:
                    return "revokeDefaultPermissionsFromDisabledTelephonyDataServices";
                case 168:
                    return "grantDefaultPermissionsToActiveLuiApp";
                case 169:
                    return "revokeDefaultPermissionsFromLuiApps";
                case 170:
                    return "isPermissionRevokedByPolicy";
                case 171:
                    return "getPermissionControllerPackageName";
                case 172:
                    return "getInstantApps";
                case 173:
                    return "getInstantAppCookie";
                case 174:
                    return "setInstantAppCookie";
                case 175:
                    return "getInstantAppIcon";
                case 176:
                    return "isInstantApp";
                case 177:
                    return "setRequiredForSystemUser";
                case 178:
                    return "setUpdateAvailable";
                case 179:
                    return "getServicesSystemSharedLibraryPackageName";
                case 180:
                    return "getSharedSystemSharedLibraryPackageName";
                case 181:
                    return "getChangedPackages";
                case 182:
                    return "isPackageDeviceAdminOnAnyUser";
                case 183:
                    return "getInstallReason";
                case 184:
                    return "getSharedLibraries";
                case 185:
                    return "getDeclaredSharedLibraries";
                case 186:
                    return "canRequestPackageInstalls";
                case 187:
                    return "deletePreloadsFileCache";
                case 188:
                    return "getInstantAppResolverComponent";
                case 189:
                    return "getInstantAppResolverSettingsComponent";
                case 190:
                    return "getInstantAppInstallerComponent";
                case 191:
                    return "getInstantAppAndroidId";
                case 192:
                    return "getArtManager";
                case 193:
                    return "setHarmfulAppWarning";
                case 194:
                    return "getHarmfulAppWarning";
                case 195:
                    return "hasSigningCertificate";
                case 196:
                    return "hasUidSigningCertificate";
                case 197:
                    return "getSystemTextClassifierPackageName";
                case 198:
                    return "getAttentionServicePackageName";
                case 199:
                    return "getWellbeingPackageName";
                case 200:
                    return "getAppPredictionServicePackageName";
                case 201:
                    return "getSystemCaptionsServicePackageName";
                case 202:
                    return "getIncidentReportApproverPackageName";
                case 203:
                    return "isPackageStateProtected";
                case 204:
                    return "sendDeviceCustomizationReadyBroadcast";
                case 205:
                    return "getInstalledModules";
                case 206:
                    return "getModuleInfo";
                case 207:
                    return "getRuntimePermissionsVersion";
                case 208:
                    return "setRuntimePermissionsVersion";
                case 209:
                    return "notifyPackagesReplacedReceived";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v5, resolved type: android.content.pm.VersionedPackage} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v18, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v26, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v30, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v34, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v40, resolved type: android.content.pm.PermissionInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v112, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v122, resolved type: android.content.pm.VersionedPackage} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v149, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v177, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v181, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v185, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v232, resolved type: android.content.pm.PermissionInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v266, resolved type: android.content.pm.KeySet} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v270, resolved type: android.content.pm.KeySet} */
        /* JADX WARNING: type inference failed for: r0v1 */
        /* JADX WARNING: type inference failed for: r0v22, types: [android.content.Intent] */
        /* JADX WARNING: type inference failed for: r0v67, types: [android.content.Intent] */
        /* JADX WARNING: type inference failed for: r0v71, types: [android.content.Intent] */
        /* JADX WARNING: type inference failed for: r0v75, types: [android.content.Intent] */
        /* JADX WARNING: type inference failed for: r0v79, types: [android.content.Intent] */
        /* JADX WARNING: type inference failed for: r0v83 */
        /* JADX WARNING: type inference failed for: r0v89, types: [android.content.Intent] */
        /* JADX WARNING: type inference failed for: r0v93, types: [android.content.Intent] */
        /* JADX WARNING: type inference failed for: r0v97, types: [android.content.Intent] */
        /* JADX WARNING: type inference failed for: r0v101, types: [android.content.Intent] */
        /* JADX WARNING: type inference failed for: r0v128, types: [android.content.Intent] */
        /* JADX WARNING: type inference failed for: r0v132 */
        /* JADX WARNING: type inference failed for: r0v137 */
        /* JADX WARNING: type inference failed for: r0v142 */
        /* JADX WARNING: type inference failed for: r0v154 */
        /* JADX WARNING: type inference failed for: r0v161 */
        /* JADX WARNING: type inference failed for: r0v196 */
        /* JADX WARNING: type inference failed for: r0v260, types: [android.os.IBinder] */
        /* JADX WARNING: type inference failed for: r0v305, types: [android.os.IBinder] */
        /* JADX WARNING: type inference failed for: r0v307, types: [java.lang.CharSequence] */
        /* JADX WARNING: type inference failed for: r0v327 */
        /* JADX WARNING: type inference failed for: r0v328 */
        /* JADX WARNING: type inference failed for: r0v329 */
        /* JADX WARNING: type inference failed for: r0v330 */
        /* JADX WARNING: type inference failed for: r0v331 */
        /* JADX WARNING: type inference failed for: r0v332 */
        /* JADX WARNING: type inference failed for: r0v333 */
        /* JADX WARNING: type inference failed for: r0v334 */
        /* JADX WARNING: type inference failed for: r0v335 */
        /* JADX WARNING: type inference failed for: r0v336 */
        /* JADX WARNING: type inference failed for: r0v337 */
        /* JADX WARNING: type inference failed for: r0v338 */
        /* JADX WARNING: type inference failed for: r0v339 */
        /* JADX WARNING: type inference failed for: r0v340 */
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
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r21, android.os.Parcel r22, android.os.Parcel r23, int r24) throws android.os.RemoteException {
            /*
                r20 = this;
                r8 = r20
                r9 = r21
                r10 = r22
                r11 = r23
                java.lang.String r12 = "android.content.pm.IPackageManager"
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r13 = 1
                if (r9 == r0) goto L_0x15c0
                r0 = 0
                r14 = 0
                switch(r9) {
                    case 1: goto L_0x15ae;
                    case 2: goto L_0x1598;
                    case 3: goto L_0x1575;
                    case 4: goto L_0x1546;
                    case 5: goto L_0x152c;
                    case 6: goto L_0x1512;
                    case 7: goto L_0x1500;
                    case 8: goto L_0x14ee;
                    case 9: goto L_0x14cb;
                    case 10: goto L_0x14ac;
                    case 11: goto L_0x148d;
                    case 12: goto L_0x1472;
                    case 13: goto L_0x144f;
                    case 14: goto L_0x1420;
                    case 15: goto L_0x13ee;
                    case 16: goto L_0x13bf;
                    case 17: goto L_0x1390;
                    case 18: goto L_0x1361;
                    case 19: goto L_0x1347;
                    case 20: goto L_0x1331;
                    case 21: goto L_0x1313;
                    case 22: goto L_0x1305;
                    case 23: goto L_0x12ef;
                    case 24: goto L_0x12d9;
                    case 25: goto L_0x12cf;
                    case 26: goto L_0x12b5;
                    case 27: goto L_0x1285;
                    case 28: goto L_0x126f;
                    case 29: goto L_0x1255;
                    case 30: goto L_0x1237;
                    case 31: goto L_0x1219;
                    case 32: goto L_0x11ff;
                    case 33: goto L_0x11ed;
                    case 34: goto L_0x11d7;
                    case 35: goto L_0x11c1;
                    case 36: goto L_0x11b3;
                    case 37: goto L_0x11a1;
                    case 38: goto L_0x118f;
                    case 39: goto L_0x117d;
                    case 40: goto L_0x116b;
                    case 41: goto L_0x1159;
                    case 42: goto L_0x1147;
                    case 43: goto L_0x1135;
                    case 44: goto L_0x1123;
                    case 45: goto L_0x10f0;
                    case 46: goto L_0x10c5;
                    case 47: goto L_0x109b;
                    case 48: goto L_0x1068;
                    case 49: goto L_0x100c;
                    case 50: goto L_0x0fd9;
                    case 51: goto L_0x0fa6;
                    case 52: goto L_0x0f73;
                    case 53: goto L_0x0f40;
                    case 54: goto L_0x0f21;
                    case 55: goto L_0x0efe;
                    case 56: goto L_0x0edf;
                    case 57: goto L_0x0ec4;
                    case 58: goto L_0x0ea1;
                    case 59: goto L_0x0e87;
                    case 60: goto L_0x0e60;
                    case 61: goto L_0x0e35;
                    case 62: goto L_0x0e16;
                    case 63: goto L_0x0dff;
                    case 64: goto L_0x0ded;
                    case 65: goto L_0x0dd7;
                    case 66: goto L_0x0dad;
                    case 67: goto L_0x0d83;
                    case 68: goto L_0x0d71;
                    case 69: goto L_0x0d63;
                    case 70: goto L_0x0d34;
                    case 71: goto L_0x0ce7;
                    case 72: goto L_0x0ca6;
                    case 73: goto L_0x0c65;
                    case 74: goto L_0x0c57;
                    case 75: goto L_0x0c35;
                    case 76: goto L_0x0c07;
                    case 77: goto L_0x0bf5;
                    case 78: goto L_0x0bc4;
                    case 79: goto L_0x0bb2;
                    case 80: goto L_0x0b98;
                    case 81: goto L_0x0b3c;
                    case 82: goto L_0x0b26;
                    case 83: goto L_0x0b10;
                    case 84: goto L_0x0af1;
                    case 85: goto L_0x0adf;
                    case 86: goto L_0x0acd;
                    case 87: goto L_0x0abb;
                    case 88: goto L_0x0aa9;
                    case 89: goto L_0x0a97;
                    case 90: goto L_0x0a85;
                    case 91: goto L_0x0a66;
                    case 92: goto L_0x0a48;
                    case 93: goto L_0x0a22;
                    case 94: goto L_0x0a00;
                    case 95: goto L_0x09da;
                    case 96: goto L_0x09c4;
                    case 97: goto L_0x099e;
                    case 98: goto L_0x0990;
                    case 99: goto L_0x0975;
                    case 100: goto L_0x0950;
                    case 101: goto L_0x0924;
                    case 102: goto L_0x090e;
                    case 103: goto L_0x08f4;
                    case 104: goto L_0x08da;
                    case 105: goto L_0x08cc;
                    case 106: goto L_0x08b2;
                    case 107: goto L_0x08a4;
                    case 108: goto L_0x088d;
                    case 109: goto L_0x0877;
                    case 110: goto L_0x086d;
                    case 111: goto L_0x085f;
                    case 112: goto L_0x0855;
                    case 113: goto L_0x0847;
                    case 114: goto L_0x083d;
                    case 115: goto L_0x0833;
                    case 116: goto L_0x0824;
                    case 117: goto L_0x080d;
                    case 118: goto L_0x07ed;
                    case 119: goto L_0x07b3;
                    case 120: goto L_0x0794;
                    case 121: goto L_0x0782;
                    case 122: goto L_0x0774;
                    case 123: goto L_0x0766;
                    case 124: goto L_0x0754;
                    case 125: goto L_0x0746;
                    case 126: goto L_0x0734;
                    case 127: goto L_0x0722;
                    case 128: goto L_0x0710;
                    case 129: goto L_0x06fa;
                    case 130: goto L_0x06e8;
                    case 131: goto L_0x06ca;
                    case 132: goto L_0x06b8;
                    case 133: goto L_0x06aa;
                    case 134: goto L_0x0680;
                    case 135: goto L_0x066e;
                    case 136: goto L_0x0658;
                    case 137: goto L_0x0642;
                    case 138: goto L_0x062c;
                    case 139: goto L_0x0612;
                    case 140: goto L_0x05f7;
                    case 141: goto L_0x05dc;
                    case 142: goto L_0x05c6;
                    case 143: goto L_0x05b4;
                    case 144: goto L_0x059d;
                    case 145: goto L_0x058f;
                    case 146: goto L_0x0581;
                    case 147: goto L_0x0573;
                    case 148: goto L_0x055c;
                    case 149: goto L_0x054a;
                    case 150: goto L_0x053c;
                    case 151: goto L_0x051d;
                    case 152: goto L_0x0507;
                    case 153: goto L_0x04f0;
                    case 154: goto L_0x04d1;
                    case 155: goto L_0x04bc;
                    case 156: goto L_0x049d;
                    case 157: goto L_0x0487;
                    case 158: goto L_0x0468;
                    case 159: goto L_0x044d;
                    case 160: goto L_0x042b;
                    case 161: goto L_0x0409;
                    case 162: goto L_0x03f7;
                    case 163: goto L_0x03e5;
                    case 164: goto L_0x03d3;
                    case 165: goto L_0x03c1;
                    case 166: goto L_0x03af;
                    case 167: goto L_0x039d;
                    case 168: goto L_0x038b;
                    case 169: goto L_0x0379;
                    case 170: goto L_0x035f;
                    case 171: goto L_0x0351;
                    case 172: goto L_0x0336;
                    case 173: goto L_0x0320;
                    case 174: goto L_0x0306;
                    case 175: goto L_0x02e7;
                    case 176: goto L_0x02d1;
                    case 177: goto L_0x02b6;
                    case 178: goto L_0x029f;
                    case 179: goto L_0x0291;
                    case 180: goto L_0x0283;
                    case 181: goto L_0x0264;
                    case 182: goto L_0x0252;
                    case 183: goto L_0x023c;
                    case 184: goto L_0x0219;
                    case 185: goto L_0x01f6;
                    case 186: goto L_0x01e0;
                    case 187: goto L_0x01d6;
                    case 188: goto L_0x01bf;
                    case 189: goto L_0x01a8;
                    case 190: goto L_0x0191;
                    case 191: goto L_0x017b;
                    case 192: goto L_0x0166;
                    case 193: goto L_0x0144;
                    case 194: goto L_0x0125;
                    case 195: goto L_0x010b;
                    case 196: goto L_0x00f1;
                    case 197: goto L_0x00e3;
                    case 198: goto L_0x00d5;
                    case 199: goto L_0x00c7;
                    case 200: goto L_0x00b9;
                    case 201: goto L_0x00ab;
                    case 202: goto L_0x009d;
                    case 203: goto L_0x0087;
                    case 204: goto L_0x007d;
                    case 205: goto L_0x006b;
                    case 206: goto L_0x004c;
                    case 207: goto L_0x003a;
                    case 208: goto L_0x0028;
                    case 209: goto L_0x001a;
                    default: goto L_0x0015;
                }
            L_0x0015:
                boolean r0 = super.onTransact(r21, r22, r23, r24)
                return r0
            L_0x001a:
                r10.enforceInterface(r12)
                java.lang.String[] r0 = r22.createStringArray()
                r8.notifyPackagesReplacedReceived(r0)
                r23.writeNoException()
                return r13
            L_0x0028:
                r10.enforceInterface(r12)
                int r0 = r22.readInt()
                int r1 = r22.readInt()
                r8.setRuntimePermissionsVersion(r0, r1)
                r23.writeNoException()
                return r13
            L_0x003a:
                r10.enforceInterface(r12)
                int r0 = r22.readInt()
                int r1 = r8.getRuntimePermissionsVersion(r0)
                r23.writeNoException()
                r11.writeInt(r1)
                return r13
            L_0x004c:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                int r1 = r22.readInt()
                android.content.pm.ModuleInfo r2 = r8.getModuleInfo(r0, r1)
                r23.writeNoException()
                if (r2 == 0) goto L_0x0067
                r11.writeInt(r13)
                r2.writeToParcel(r11, r13)
                goto L_0x006a
            L_0x0067:
                r11.writeInt(r14)
            L_0x006a:
                return r13
            L_0x006b:
                r10.enforceInterface(r12)
                int r0 = r22.readInt()
                java.util.List r1 = r8.getInstalledModules(r0)
                r23.writeNoException()
                r11.writeTypedList(r1)
                return r13
            L_0x007d:
                r10.enforceInterface(r12)
                r20.sendDeviceCustomizationReadyBroadcast()
                r23.writeNoException()
                return r13
            L_0x0087:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                int r1 = r22.readInt()
                boolean r2 = r8.isPackageStateProtected(r0, r1)
                r23.writeNoException()
                r11.writeInt(r2)
                return r13
            L_0x009d:
                r10.enforceInterface(r12)
                java.lang.String r0 = r20.getIncidentReportApproverPackageName()
                r23.writeNoException()
                r11.writeString(r0)
                return r13
            L_0x00ab:
                r10.enforceInterface(r12)
                java.lang.String r0 = r20.getSystemCaptionsServicePackageName()
                r23.writeNoException()
                r11.writeString(r0)
                return r13
            L_0x00b9:
                r10.enforceInterface(r12)
                java.lang.String r0 = r20.getAppPredictionServicePackageName()
                r23.writeNoException()
                r11.writeString(r0)
                return r13
            L_0x00c7:
                r10.enforceInterface(r12)
                java.lang.String r0 = r20.getWellbeingPackageName()
                r23.writeNoException()
                r11.writeString(r0)
                return r13
            L_0x00d5:
                r10.enforceInterface(r12)
                java.lang.String r0 = r20.getAttentionServicePackageName()
                r23.writeNoException()
                r11.writeString(r0)
                return r13
            L_0x00e3:
                r10.enforceInterface(r12)
                java.lang.String r0 = r20.getSystemTextClassifierPackageName()
                r23.writeNoException()
                r11.writeString(r0)
                return r13
            L_0x00f1:
                r10.enforceInterface(r12)
                int r0 = r22.readInt()
                byte[] r1 = r22.createByteArray()
                int r2 = r22.readInt()
                boolean r3 = r8.hasUidSigningCertificate(r0, r1, r2)
                r23.writeNoException()
                r11.writeInt(r3)
                return r13
            L_0x010b:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                byte[] r1 = r22.createByteArray()
                int r2 = r22.readInt()
                boolean r3 = r8.hasSigningCertificate(r0, r1, r2)
                r23.writeNoException()
                r11.writeInt(r3)
                return r13
            L_0x0125:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                int r1 = r22.readInt()
                java.lang.CharSequence r2 = r8.getHarmfulAppWarning(r0, r1)
                r23.writeNoException()
                if (r2 == 0) goto L_0x0140
                r11.writeInt(r13)
                android.text.TextUtils.writeToParcel(r2, r11, r13)
                goto L_0x0143
            L_0x0140:
                r11.writeInt(r14)
            L_0x0143:
                return r13
            L_0x0144:
                r10.enforceInterface(r12)
                java.lang.String r1 = r22.readString()
                int r2 = r22.readInt()
                if (r2 == 0) goto L_0x015a
                android.os.Parcelable$Creator<java.lang.CharSequence> r0 = android.text.TextUtils.CHAR_SEQUENCE_CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                java.lang.CharSequence r0 = (java.lang.CharSequence) r0
                goto L_0x015b
            L_0x015a:
            L_0x015b:
                int r2 = r22.readInt()
                r8.setHarmfulAppWarning(r1, r0, r2)
                r23.writeNoException()
                return r13
            L_0x0166:
                r10.enforceInterface(r12)
                android.content.pm.dex.IArtManager r1 = r20.getArtManager()
                r23.writeNoException()
                if (r1 == 0) goto L_0x0177
                android.os.IBinder r0 = r1.asBinder()
            L_0x0177:
                r11.writeStrongBinder(r0)
                return r13
            L_0x017b:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                int r1 = r22.readInt()
                java.lang.String r2 = r8.getInstantAppAndroidId(r0, r1)
                r23.writeNoException()
                r11.writeString(r2)
                return r13
            L_0x0191:
                r10.enforceInterface(r12)
                android.content.ComponentName r0 = r20.getInstantAppInstallerComponent()
                r23.writeNoException()
                if (r0 == 0) goto L_0x01a4
                r11.writeInt(r13)
                r0.writeToParcel((android.os.Parcel) r11, (int) r13)
                goto L_0x01a7
            L_0x01a4:
                r11.writeInt(r14)
            L_0x01a7:
                return r13
            L_0x01a8:
                r10.enforceInterface(r12)
                android.content.ComponentName r0 = r20.getInstantAppResolverSettingsComponent()
                r23.writeNoException()
                if (r0 == 0) goto L_0x01bb
                r11.writeInt(r13)
                r0.writeToParcel((android.os.Parcel) r11, (int) r13)
                goto L_0x01be
            L_0x01bb:
                r11.writeInt(r14)
            L_0x01be:
                return r13
            L_0x01bf:
                r10.enforceInterface(r12)
                android.content.ComponentName r0 = r20.getInstantAppResolverComponent()
                r23.writeNoException()
                if (r0 == 0) goto L_0x01d2
                r11.writeInt(r13)
                r0.writeToParcel((android.os.Parcel) r11, (int) r13)
                goto L_0x01d5
            L_0x01d2:
                r11.writeInt(r14)
            L_0x01d5:
                return r13
            L_0x01d6:
                r10.enforceInterface(r12)
                r20.deletePreloadsFileCache()
                r23.writeNoException()
                return r13
            L_0x01e0:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                int r1 = r22.readInt()
                boolean r2 = r8.canRequestPackageInstalls(r0, r1)
                r23.writeNoException()
                r11.writeInt(r2)
                return r13
            L_0x01f6:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                int r1 = r22.readInt()
                int r2 = r22.readInt()
                android.content.pm.ParceledListSlice r3 = r8.getDeclaredSharedLibraries(r0, r1, r2)
                r23.writeNoException()
                if (r3 == 0) goto L_0x0215
                r11.writeInt(r13)
                r3.writeToParcel(r11, r13)
                goto L_0x0218
            L_0x0215:
                r11.writeInt(r14)
            L_0x0218:
                return r13
            L_0x0219:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                int r1 = r22.readInt()
                int r2 = r22.readInt()
                android.content.pm.ParceledListSlice r3 = r8.getSharedLibraries(r0, r1, r2)
                r23.writeNoException()
                if (r3 == 0) goto L_0x0238
                r11.writeInt(r13)
                r3.writeToParcel(r11, r13)
                goto L_0x023b
            L_0x0238:
                r11.writeInt(r14)
            L_0x023b:
                return r13
            L_0x023c:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                int r1 = r22.readInt()
                int r2 = r8.getInstallReason(r0, r1)
                r23.writeNoException()
                r11.writeInt(r2)
                return r13
            L_0x0252:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                boolean r1 = r8.isPackageDeviceAdminOnAnyUser(r0)
                r23.writeNoException()
                r11.writeInt(r1)
                return r13
            L_0x0264:
                r10.enforceInterface(r12)
                int r0 = r22.readInt()
                int r1 = r22.readInt()
                android.content.pm.ChangedPackages r2 = r8.getChangedPackages(r0, r1)
                r23.writeNoException()
                if (r2 == 0) goto L_0x027f
                r11.writeInt(r13)
                r2.writeToParcel(r11, r13)
                goto L_0x0282
            L_0x027f:
                r11.writeInt(r14)
            L_0x0282:
                return r13
            L_0x0283:
                r10.enforceInterface(r12)
                java.lang.String r0 = r20.getSharedSystemSharedLibraryPackageName()
                r23.writeNoException()
                r11.writeString(r0)
                return r13
            L_0x0291:
                r10.enforceInterface(r12)
                java.lang.String r0 = r20.getServicesSystemSharedLibraryPackageName()
                r23.writeNoException()
                r11.writeString(r0)
                return r13
            L_0x029f:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x02ae
                r14 = r13
            L_0x02ae:
                r1 = r14
                r8.setUpdateAvailable(r0, r1)
                r23.writeNoException()
                return r13
            L_0x02b6:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x02c5
                r14 = r13
            L_0x02c5:
                r1 = r14
                boolean r2 = r8.setRequiredForSystemUser(r0, r1)
                r23.writeNoException()
                r11.writeInt(r2)
                return r13
            L_0x02d1:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                int r1 = r22.readInt()
                boolean r2 = r8.isInstantApp(r0, r1)
                r23.writeNoException()
                r11.writeInt(r2)
                return r13
            L_0x02e7:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                int r1 = r22.readInt()
                android.graphics.Bitmap r2 = r8.getInstantAppIcon(r0, r1)
                r23.writeNoException()
                if (r2 == 0) goto L_0x0302
                r11.writeInt(r13)
                r2.writeToParcel(r11, r13)
                goto L_0x0305
            L_0x0302:
                r11.writeInt(r14)
            L_0x0305:
                return r13
            L_0x0306:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                byte[] r1 = r22.createByteArray()
                int r2 = r22.readInt()
                boolean r3 = r8.setInstantAppCookie(r0, r1, r2)
                r23.writeNoException()
                r11.writeInt(r3)
                return r13
            L_0x0320:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                int r1 = r22.readInt()
                byte[] r2 = r8.getInstantAppCookie(r0, r1)
                r23.writeNoException()
                r11.writeByteArray(r2)
                return r13
            L_0x0336:
                r10.enforceInterface(r12)
                int r0 = r22.readInt()
                android.content.pm.ParceledListSlice r1 = r8.getInstantApps(r0)
                r23.writeNoException()
                if (r1 == 0) goto L_0x034d
                r11.writeInt(r13)
                r1.writeToParcel(r11, r13)
                goto L_0x0350
            L_0x034d:
                r11.writeInt(r14)
            L_0x0350:
                return r13
            L_0x0351:
                r10.enforceInterface(r12)
                java.lang.String r0 = r20.getPermissionControllerPackageName()
                r23.writeNoException()
                r11.writeString(r0)
                return r13
            L_0x035f:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                java.lang.String r1 = r22.readString()
                int r2 = r22.readInt()
                boolean r3 = r8.isPermissionRevokedByPolicy(r0, r1, r2)
                r23.writeNoException()
                r11.writeInt(r3)
                return r13
            L_0x0379:
                r10.enforceInterface(r12)
                java.lang.String[] r0 = r22.createStringArray()
                int r1 = r22.readInt()
                r8.revokeDefaultPermissionsFromLuiApps(r0, r1)
                r23.writeNoException()
                return r13
            L_0x038b:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                int r1 = r22.readInt()
                r8.grantDefaultPermissionsToActiveLuiApp(r0, r1)
                r23.writeNoException()
                return r13
            L_0x039d:
                r10.enforceInterface(r12)
                java.lang.String[] r0 = r22.createStringArray()
                int r1 = r22.readInt()
                r8.revokeDefaultPermissionsFromDisabledTelephonyDataServices(r0, r1)
                r23.writeNoException()
                return r13
            L_0x03af:
                r10.enforceInterface(r12)
                java.lang.String[] r0 = r22.createStringArray()
                int r1 = r22.readInt()
                r8.grantDefaultPermissionsToEnabledTelephonyDataServices(r0, r1)
                r23.writeNoException()
                return r13
            L_0x03c1:
                r10.enforceInterface(r12)
                java.lang.String[] r0 = r22.createStringArray()
                int r1 = r22.readInt()
                r8.grantDefaultPermissionsToEnabledImsServices(r0, r1)
                r23.writeNoException()
                return r13
            L_0x03d3:
                r10.enforceInterface(r12)
                java.lang.String[] r0 = r22.createStringArray()
                int r1 = r22.readInt()
                r8.grantDefaultPermissionsToEnabledCarrierApps(r0, r1)
                r23.writeNoException()
                return r13
            L_0x03e5:
                r10.enforceInterface(r12)
                android.os.IBinder r0 = r22.readStrongBinder()
                android.content.pm.IOnPermissionsChangeListener r0 = android.content.pm.IOnPermissionsChangeListener.Stub.asInterface(r0)
                r8.removeOnPermissionsChangeListener(r0)
                r23.writeNoException()
                return r13
            L_0x03f7:
                r10.enforceInterface(r12)
                android.os.IBinder r0 = r22.readStrongBinder()
                android.content.pm.IOnPermissionsChangeListener r0 = android.content.pm.IOnPermissionsChangeListener.Stub.asInterface(r0)
                r8.addOnPermissionsChangeListener(r0)
                r23.writeNoException()
                return r13
            L_0x0409:
                r10.enforceInterface(r12)
                java.lang.String r1 = r22.readString()
                int r2 = r22.readInt()
                if (r2 == 0) goto L_0x041f
                android.os.Parcelable$Creator<android.content.pm.KeySet> r0 = android.content.pm.KeySet.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.pm.KeySet r0 = (android.content.pm.KeySet) r0
                goto L_0x0420
            L_0x041f:
            L_0x0420:
                boolean r2 = r8.isPackageSignedByKeySetExactly(r1, r0)
                r23.writeNoException()
                r11.writeInt(r2)
                return r13
            L_0x042b:
                r10.enforceInterface(r12)
                java.lang.String r1 = r22.readString()
                int r2 = r22.readInt()
                if (r2 == 0) goto L_0x0441
                android.os.Parcelable$Creator<android.content.pm.KeySet> r0 = android.content.pm.KeySet.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.pm.KeySet r0 = (android.content.pm.KeySet) r0
                goto L_0x0442
            L_0x0441:
            L_0x0442:
                boolean r2 = r8.isPackageSignedByKeySet(r1, r0)
                r23.writeNoException()
                r11.writeInt(r2)
                return r13
            L_0x044d:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                android.content.pm.KeySet r1 = r8.getSigningKeySet(r0)
                r23.writeNoException()
                if (r1 == 0) goto L_0x0464
                r11.writeInt(r13)
                r1.writeToParcel(r11, r13)
                goto L_0x0467
            L_0x0464:
                r11.writeInt(r14)
            L_0x0467:
                return r13
            L_0x0468:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                java.lang.String r1 = r22.readString()
                android.content.pm.KeySet r2 = r8.getKeySetByAlias(r0, r1)
                r23.writeNoException()
                if (r2 == 0) goto L_0x0483
                r11.writeInt(r13)
                r2.writeToParcel(r11, r13)
                goto L_0x0486
            L_0x0483:
                r11.writeInt(r14)
            L_0x0486:
                return r13
            L_0x0487:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                int r1 = r22.readInt()
                boolean r2 = r8.getBlockUninstallForUser(r0, r1)
                r23.writeNoException()
                r11.writeInt(r2)
                return r13
            L_0x049d:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x04ac
                r14 = r13
            L_0x04ac:
                r1 = r14
                int r2 = r22.readInt()
                boolean r3 = r8.setBlockUninstallForUser(r0, r1, r2)
                r23.writeNoException()
                r11.writeInt(r3)
                return r13
            L_0x04bc:
                r10.enforceInterface(r12)
                android.content.pm.IPackageInstaller r1 = r20.getPackageInstaller()
                r23.writeNoException()
                if (r1 == 0) goto L_0x04cd
                android.os.IBinder r0 = r1.asBinder()
            L_0x04cd:
                r11.writeStrongBinder(r0)
                return r13
            L_0x04d1:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x04e0
                r14 = r13
            L_0x04e0:
                r1 = r14
                int r2 = r22.readInt()
                boolean r3 = r8.setSystemAppInstallState(r0, r1, r2)
                r23.writeNoException()
                r11.writeInt(r3)
                return r13
            L_0x04f0:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x04ff
                r14 = r13
            L_0x04ff:
                r1 = r14
                r8.setSystemAppHiddenUntilInstalled(r0, r1)
                r23.writeNoException()
                return r13
            L_0x0507:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                int r1 = r22.readInt()
                boolean r2 = r8.getApplicationHiddenSettingAsUser(r0, r1)
                r23.writeNoException()
                r11.writeInt(r2)
                return r13
            L_0x051d:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x052c
                r14 = r13
            L_0x052c:
                r1 = r14
                int r2 = r22.readInt()
                boolean r3 = r8.setApplicationHiddenSettingAsUser(r0, r1, r2)
                r23.writeNoException()
                r11.writeInt(r3)
                return r13
            L_0x053c:
                r10.enforceInterface(r12)
                boolean r0 = r20.isStorageLow()
                r23.writeNoException()
                r11.writeInt(r0)
                return r13
            L_0x054a:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                boolean r1 = r8.isPermissionEnforced(r0)
                r23.writeNoException()
                r11.writeInt(r1)
                return r13
            L_0x055c:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x056b
                r14 = r13
            L_0x056b:
                r1 = r14
                r8.setPermissionEnforced(r0, r1)
                r23.writeNoException()
                return r13
            L_0x0573:
                r10.enforceInterface(r12)
                boolean r0 = r20.isDeviceUpgrading()
                r23.writeNoException()
                r11.writeInt(r0)
                return r13
            L_0x0581:
                r10.enforceInterface(r12)
                boolean r0 = r20.isOnlyCoreApps()
                r23.writeNoException()
                r11.writeInt(r0)
                return r13
            L_0x058f:
                r10.enforceInterface(r12)
                boolean r0 = r20.isFirstBoot()
                r23.writeNoException()
                r11.writeInt(r0)
                return r13
            L_0x059d:
                r10.enforceInterface(r12)
                android.content.pm.VerifierDeviceIdentity r0 = r20.getVerifierDeviceIdentity()
                r23.writeNoException()
                if (r0 == 0) goto L_0x05b0
                r11.writeInt(r13)
                r0.writeToParcel(r11, r13)
                goto L_0x05b3
            L_0x05b0:
                r11.writeInt(r14)
            L_0x05b3:
                return r13
            L_0x05b4:
                r10.enforceInterface(r12)
                int r0 = r22.readInt()
                java.lang.String r1 = r8.getDefaultBrowserPackageName(r0)
                r23.writeNoException()
                r11.writeString(r1)
                return r13
            L_0x05c6:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                int r1 = r22.readInt()
                boolean r2 = r8.setDefaultBrowserPackageName(r0, r1)
                r23.writeNoException()
                r11.writeInt(r2)
                return r13
            L_0x05dc:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                android.content.pm.ParceledListSlice r1 = r8.getAllIntentFilters(r0)
                r23.writeNoException()
                if (r1 == 0) goto L_0x05f3
                r11.writeInt(r13)
                r1.writeToParcel(r11, r13)
                goto L_0x05f6
            L_0x05f3:
                r11.writeInt(r14)
            L_0x05f6:
                return r13
            L_0x05f7:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                android.content.pm.ParceledListSlice r1 = r8.getIntentFilterVerifications(r0)
                r23.writeNoException()
                if (r1 == 0) goto L_0x060e
                r11.writeInt(r13)
                r1.writeToParcel(r11, r13)
                goto L_0x0611
            L_0x060e:
                r11.writeInt(r14)
            L_0x0611:
                return r13
            L_0x0612:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                int r1 = r22.readInt()
                int r2 = r22.readInt()
                boolean r3 = r8.updateIntentVerificationStatus(r0, r1, r2)
                r23.writeNoException()
                r11.writeInt(r3)
                return r13
            L_0x062c:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                int r1 = r22.readInt()
                int r2 = r8.getIntentVerificationStatus(r0, r1)
                r23.writeNoException()
                r11.writeInt(r2)
                return r13
            L_0x0642:
                r10.enforceInterface(r12)
                int r0 = r22.readInt()
                int r1 = r22.readInt()
                java.util.ArrayList r2 = r22.createStringArrayList()
                r8.verifyIntentFilter(r0, r1, r2)
                r23.writeNoException()
                return r13
            L_0x0658:
                r10.enforceInterface(r12)
                int r0 = r22.readInt()
                int r1 = r22.readInt()
                long r2 = r22.readLong()
                r8.extendVerificationTimeout(r0, r1, r2)
                r23.writeNoException()
                return r13
            L_0x066e:
                r10.enforceInterface(r12)
                int r0 = r22.readInt()
                int r1 = r22.readInt()
                r8.verifyPendingInstall(r0, r1)
                r23.writeNoException()
                return r13
            L_0x0680:
                r10.enforceInterface(r12)
                java.lang.String r6 = r22.readString()
                int r7 = r22.readInt()
                int r14 = r22.readInt()
                int r15 = r22.readInt()
                java.util.ArrayList r16 = r22.createStringArrayList()
                r0 = r20
                r1 = r6
                r2 = r7
                r3 = r14
                r4 = r15
                r5 = r16
                int r0 = r0.installExistingPackageAsUser(r1, r2, r3, r4, r5)
                r23.writeNoException()
                r11.writeInt(r0)
                return r13
            L_0x06aa:
                r10.enforceInterface(r12)
                int r0 = r20.getInstallLocation()
                r23.writeNoException()
                r11.writeInt(r0)
                return r13
            L_0x06b8:
                r10.enforceInterface(r12)
                int r0 = r22.readInt()
                boolean r1 = r8.setInstallLocation(r0)
                r23.writeNoException()
                r11.writeInt(r1)
                return r13
            L_0x06ca:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x06dc
                android.os.Parcelable$Creator<android.content.pm.PermissionInfo> r0 = android.content.pm.PermissionInfo.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.pm.PermissionInfo r0 = (android.content.pm.PermissionInfo) r0
                goto L_0x06dd
            L_0x06dc:
            L_0x06dd:
                boolean r1 = r8.addPermissionAsync(r0)
                r23.writeNoException()
                r11.writeInt(r1)
                return r13
            L_0x06e8:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                int r1 = r8.movePrimaryStorage(r0)
                r23.writeNoException()
                r11.writeInt(r1)
                return r13
            L_0x06fa:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                java.lang.String r1 = r22.readString()
                int r2 = r8.movePackage(r0, r1)
                r23.writeNoException()
                r11.writeInt(r2)
                return r13
            L_0x0710:
                r10.enforceInterface(r12)
                android.os.IBinder r0 = r22.readStrongBinder()
                android.content.pm.IPackageMoveObserver r0 = android.content.pm.IPackageMoveObserver.Stub.asInterface(r0)
                r8.unregisterMoveCallback(r0)
                r23.writeNoException()
                return r13
            L_0x0722:
                r10.enforceInterface(r12)
                android.os.IBinder r0 = r22.readStrongBinder()
                android.content.pm.IPackageMoveObserver r0 = android.content.pm.IPackageMoveObserver.Stub.asInterface(r0)
                r8.registerMoveCallback(r0)
                r23.writeNoException()
                return r13
            L_0x0734:
                r10.enforceInterface(r12)
                int r0 = r22.readInt()
                int r1 = r8.getMoveStatus(r0)
                r23.writeNoException()
                r11.writeInt(r1)
                return r13
            L_0x0746:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                r8.reconcileSecondaryDexFiles(r0)
                r23.writeNoException()
                return r13
            L_0x0754:
                r10.enforceInterface(r12)
                java.util.ArrayList r0 = r22.createStringArrayList()
                boolean r1 = r8.runBackgroundDexoptJob(r0)
                r23.writeNoException()
                r11.writeInt(r1)
                return r13
            L_0x0766:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                r8.forceDexOpt(r0)
                r23.writeNoException()
                return r13
            L_0x0774:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                r8.dumpProfiles(r0)
                r23.writeNoException()
                return r13
            L_0x0782:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                boolean r1 = r8.compileLayouts(r0)
                r23.writeNoException()
                r11.writeInt(r1)
                return r13
            L_0x0794:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                java.lang.String r1 = r22.readString()
                int r2 = r22.readInt()
                if (r2 == 0) goto L_0x07a7
                r14 = r13
            L_0x07a7:
                r2 = r14
                boolean r3 = r8.performDexOptSecondary(r0, r1, r2)
                r23.writeNoException()
                r11.writeInt(r3)
                return r13
            L_0x07b3:
                r10.enforceInterface(r12)
                java.lang.String r7 = r22.readString()
                int r0 = r22.readInt()
                if (r0 == 0) goto L_0x07c2
                r2 = r13
                goto L_0x07c3
            L_0x07c2:
                r2 = r14
            L_0x07c3:
                java.lang.String r15 = r22.readString()
                int r0 = r22.readInt()
                if (r0 == 0) goto L_0x07cf
                r4 = r13
                goto L_0x07d0
            L_0x07cf:
                r4 = r14
            L_0x07d0:
                int r0 = r22.readInt()
                if (r0 == 0) goto L_0x07d8
                r5 = r13
                goto L_0x07d9
            L_0x07d8:
                r5 = r14
            L_0x07d9:
                java.lang.String r14 = r22.readString()
                r0 = r20
                r1 = r7
                r3 = r15
                r6 = r14
                boolean r0 = r0.performDexOptMode(r1, r2, r3, r4, r5, r6)
                r23.writeNoException()
                r11.writeInt(r0)
                return r13
            L_0x07ed:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                java.lang.String r1 = r22.readString()
                int r2 = r22.readInt()
                if (r2 == 0) goto L_0x0800
                r14 = r13
            L_0x0800:
                r2 = r14
                android.os.IBinder r3 = r22.readStrongBinder()
                android.content.pm.IDexModuleRegisterCallback r3 = android.content.pm.IDexModuleRegisterCallback.Stub.asInterface(r3)
                r8.registerDexModule(r0, r1, r2, r3)
                return r13
            L_0x080d:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                java.util.ArrayList r1 = r22.createStringArrayList()
                java.util.ArrayList r2 = r22.createStringArrayList()
                java.lang.String r3 = r22.readString()
                r8.notifyDexLoad(r0, r1, r2, r3)
                return r13
            L_0x0824:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                int r1 = r22.readInt()
                r8.notifyPackageUse(r0, r1)
                return r13
            L_0x0833:
                r10.enforceInterface(r12)
                r20.updatePackagesIfNeeded()
                r23.writeNoException()
                return r13
            L_0x083d:
                r10.enforceInterface(r12)
                r20.performFstrimIfNeeded()
                r23.writeNoException()
                return r13
            L_0x0847:
                r10.enforceInterface(r12)
                boolean r0 = r20.hasSystemUidErrors()
                r23.writeNoException()
                r11.writeInt(r0)
                return r13
            L_0x0855:
                r10.enforceInterface(r12)
                r20.systemReady()
                r23.writeNoException()
                return r13
            L_0x085f:
                r10.enforceInterface(r12)
                boolean r0 = r20.isSafeMode()
                r23.writeNoException()
                r11.writeInt(r0)
                return r13
            L_0x086d:
                r10.enforceInterface(r12)
                r20.enterSafeMode()
                r23.writeNoException()
                return r13
            L_0x0877:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                int r1 = r22.readInt()
                boolean r2 = r8.hasSystemFeature(r0, r1)
                r23.writeNoException()
                r11.writeInt(r2)
                return r13
            L_0x088d:
                r10.enforceInterface(r12)
                android.content.pm.ParceledListSlice r0 = r20.getSystemAvailableFeatures()
                r23.writeNoException()
                if (r0 == 0) goto L_0x08a0
                r11.writeInt(r13)
                r0.writeToParcel(r11, r13)
                goto L_0x08a3
            L_0x08a0:
                r11.writeInt(r14)
            L_0x08a3:
                return r13
            L_0x08a4:
                r10.enforceInterface(r12)
                java.lang.String[] r0 = r20.getSystemSharedLibraryNames()
                r23.writeNoException()
                r11.writeStringArray(r0)
                return r13
            L_0x08b2:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                int r1 = r22.readInt()
                android.os.IBinder r2 = r22.readStrongBinder()
                android.content.pm.IPackageStatsObserver r2 = android.content.pm.IPackageStatsObserver.Stub.asInterface(r2)
                r8.getPackageSizeInfo(r0, r1, r2)
                r23.writeNoException()
                return r13
            L_0x08cc:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                r8.clearApplicationProfileData(r0)
                r23.writeNoException()
                return r13
            L_0x08da:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                android.os.IBinder r1 = r22.readStrongBinder()
                android.content.pm.IPackageDataObserver r1 = android.content.pm.IPackageDataObserver.Stub.asInterface(r1)
                int r2 = r22.readInt()
                r8.clearApplicationUserData(r0, r1, r2)
                r23.writeNoException()
                return r13
            L_0x08f4:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                int r1 = r22.readInt()
                android.os.IBinder r2 = r22.readStrongBinder()
                android.content.pm.IPackageDataObserver r2 = android.content.pm.IPackageDataObserver.Stub.asInterface(r2)
                r8.deleteApplicationCacheFilesAsUser(r0, r1, r2)
                r23.writeNoException()
                return r13
            L_0x090e:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                android.os.IBinder r1 = r22.readStrongBinder()
                android.content.pm.IPackageDataObserver r1 = android.content.pm.IPackageDataObserver.Stub.asInterface(r1)
                r8.deleteApplicationCacheFiles(r0, r1)
                r23.writeNoException()
                return r13
            L_0x0924:
                r10.enforceInterface(r12)
                java.lang.String r6 = r22.readString()
                long r14 = r22.readLong()
                int r7 = r22.readInt()
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x0943
                android.os.Parcelable$Creator<android.content.IntentSender> r0 = android.content.IntentSender.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.IntentSender r0 = (android.content.IntentSender) r0
            L_0x0941:
                r5 = r0
                goto L_0x0944
            L_0x0943:
                goto L_0x0941
            L_0x0944:
                r0 = r20
                r1 = r6
                r2 = r14
                r4 = r7
                r0.freeStorage(r1, r2, r4, r5)
                r23.writeNoException()
                return r13
            L_0x0950:
                r10.enforceInterface(r12)
                java.lang.String r6 = r22.readString()
                long r14 = r22.readLong()
                int r7 = r22.readInt()
                android.os.IBinder r0 = r22.readStrongBinder()
                android.content.pm.IPackageDataObserver r16 = android.content.pm.IPackageDataObserver.Stub.asInterface(r0)
                r0 = r20
                r1 = r6
                r2 = r14
                r4 = r7
                r5 = r16
                r0.freeStorageAndNotify(r1, r2, r4, r5)
                r23.writeNoException()
                return r13
            L_0x0975:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x0984
                r14 = r13
            L_0x0984:
                r1 = r14
                int r2 = r22.readInt()
                r8.setPackageStoppedState(r0, r1, r2)
                r23.writeNoException()
                return r13
            L_0x0990:
                r10.enforceInterface(r12)
                int r0 = r22.readInt()
                r8.flushPackageRestrictionsAsUser(r0)
                r23.writeNoException()
                return r13
            L_0x099e:
                r10.enforceInterface(r12)
                java.lang.String r6 = r22.readString()
                int r7 = r22.readInt()
                java.lang.String r14 = r22.readString()
                java.lang.String r15 = r22.readString()
                int r16 = r22.readInt()
                r0 = r20
                r1 = r6
                r2 = r7
                r3 = r14
                r4 = r15
                r5 = r16
                r0.logAppProcessStartIfNeeded(r1, r2, r3, r4, r5)
                r23.writeNoException()
                return r13
            L_0x09c4:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                int r1 = r22.readInt()
                int r2 = r8.getApplicationEnabledSetting(r0, r1)
                r23.writeNoException()
                r11.writeInt(r2)
                return r13
            L_0x09da:
                r10.enforceInterface(r12)
                java.lang.String r6 = r22.readString()
                int r7 = r22.readInt()
                int r14 = r22.readInt()
                int r15 = r22.readInt()
                java.lang.String r16 = r22.readString()
                r0 = r20
                r1 = r6
                r2 = r7
                r3 = r14
                r4 = r15
                r5 = r16
                r0.setApplicationEnabledSetting(r1, r2, r3, r4, r5)
                r23.writeNoException()
                return r13
            L_0x0a00:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x0a12
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x0a13
            L_0x0a12:
            L_0x0a13:
                int r1 = r22.readInt()
                int r2 = r8.getComponentEnabledSetting(r0, r1)
                r23.writeNoException()
                r11.writeInt(r2)
                return r13
            L_0x0a22:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x0a34
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x0a35
            L_0x0a34:
            L_0x0a35:
                int r1 = r22.readInt()
                int r2 = r22.readInt()
                int r3 = r22.readInt()
                r8.setComponentEnabledSetting(r0, r1, r2, r3)
                r23.writeNoException()
                return r13
            L_0x0a48:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x0a5a
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x0a5b
            L_0x0a5a:
            L_0x0a5b:
                int r1 = r22.readInt()
                r8.setHomeActivity(r0, r1)
                r23.writeNoException()
                return r13
            L_0x0a66:
                r10.enforceInterface(r12)
                java.util.ArrayList r0 = new java.util.ArrayList
                r0.<init>()
                android.content.ComponentName r1 = r8.getHomeActivities(r0)
                r23.writeNoException()
                if (r1 == 0) goto L_0x0a7e
                r11.writeInt(r13)
                r1.writeToParcel((android.os.Parcel) r11, (int) r13)
                goto L_0x0a81
            L_0x0a7e:
                r11.writeInt(r14)
            L_0x0a81:
                r11.writeTypedList(r0)
                return r13
            L_0x0a85:
                r10.enforceInterface(r12)
                byte[] r0 = r22.createByteArray()
                int r1 = r22.readInt()
                r8.restoreIntentFilterVerification(r0, r1)
                r23.writeNoException()
                return r13
            L_0x0a97:
                r10.enforceInterface(r12)
                int r0 = r22.readInt()
                byte[] r1 = r8.getIntentFilterVerificationBackup(r0)
                r23.writeNoException()
                r11.writeByteArray(r1)
                return r13
            L_0x0aa9:
                r10.enforceInterface(r12)
                byte[] r0 = r22.createByteArray()
                int r1 = r22.readInt()
                r8.restoreDefaultApps(r0, r1)
                r23.writeNoException()
                return r13
            L_0x0abb:
                r10.enforceInterface(r12)
                int r0 = r22.readInt()
                byte[] r1 = r8.getDefaultAppsBackup(r0)
                r23.writeNoException()
                r11.writeByteArray(r1)
                return r13
            L_0x0acd:
                r10.enforceInterface(r12)
                byte[] r0 = r22.createByteArray()
                int r1 = r22.readInt()
                r8.restorePreferredActivities(r0, r1)
                r23.writeNoException()
                return r13
            L_0x0adf:
                r10.enforceInterface(r12)
                int r0 = r22.readInt()
                byte[] r1 = r8.getPreferredActivityBackup(r0)
                r23.writeNoException()
                r11.writeByteArray(r1)
                return r13
            L_0x0af1:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                int r1 = r22.readInt()
                android.os.PersistableBundle r2 = r8.getSuspendedPackageAppExtras(r0, r1)
                r23.writeNoException()
                if (r2 == 0) goto L_0x0b0c
                r11.writeInt(r13)
                r2.writeToParcel(r11, r13)
                goto L_0x0b0f
            L_0x0b0c:
                r11.writeInt(r14)
            L_0x0b0f:
                return r13
            L_0x0b10:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                int r1 = r22.readInt()
                boolean r2 = r8.isPackageSuspendedForUser(r0, r1)
                r23.writeNoException()
                r11.writeInt(r2)
                return r13
            L_0x0b26:
                r10.enforceInterface(r12)
                java.lang.String[] r0 = r22.createStringArray()
                int r1 = r22.readInt()
                java.lang.String[] r2 = r8.getUnsuspendablePackagesForUser(r0, r1)
                r23.writeNoException()
                r11.writeStringArray(r2)
                return r13
            L_0x0b3c:
                r10.enforceInterface(r12)
                java.lang.String[] r15 = r22.createStringArray()
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x0b4b
                r2 = r13
                goto L_0x0b4c
            L_0x0b4b:
                r2 = r14
            L_0x0b4c:
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x0b5c
                android.os.Parcelable$Creator<android.os.PersistableBundle> r1 = android.os.PersistableBundle.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r10)
                android.os.PersistableBundle r1 = (android.os.PersistableBundle) r1
                r3 = r1
                goto L_0x0b5d
            L_0x0b5c:
                r3 = r0
            L_0x0b5d:
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x0b6d
                android.os.Parcelable$Creator<android.os.PersistableBundle> r1 = android.os.PersistableBundle.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r10)
                android.os.PersistableBundle r1 = (android.os.PersistableBundle) r1
                r4 = r1
                goto L_0x0b6e
            L_0x0b6d:
                r4 = r0
            L_0x0b6e:
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x0b7e
                android.os.Parcelable$Creator<android.content.pm.SuspendDialogInfo> r0 = android.content.pm.SuspendDialogInfo.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.pm.SuspendDialogInfo r0 = (android.content.pm.SuspendDialogInfo) r0
            L_0x0b7c:
                r5 = r0
                goto L_0x0b7f
            L_0x0b7e:
                goto L_0x0b7c
            L_0x0b7f:
                java.lang.String r14 = r22.readString()
                int r16 = r22.readInt()
                r0 = r20
                r1 = r15
                r6 = r14
                r7 = r16
                java.lang.String[] r0 = r0.setPackagesSuspendedAsUser(r1, r2, r3, r4, r5, r6, r7)
                r23.writeNoException()
                r11.writeStringArray(r0)
                return r13
            L_0x0b98:
                r10.enforceInterface(r12)
                java.lang.String[] r0 = r22.createStringArray()
                int r1 = r22.readInt()
                int r2 = r22.readInt()
                java.lang.String[] r3 = r8.setDistractingPackageRestrictionsAsUser(r0, r1, r2)
                r23.writeNoException()
                r11.writeStringArray(r3)
                return r13
            L_0x0bb2:
                r10.enforceInterface(r12)
                int r0 = r22.readInt()
                java.lang.String r1 = r22.readString()
                r8.clearCrossProfileIntentFilters(r0, r1)
                r23.writeNoException()
                return r13
            L_0x0bc4:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x0bd7
                android.os.Parcelable$Creator<android.content.IntentFilter> r0 = android.content.IntentFilter.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.IntentFilter r0 = (android.content.IntentFilter) r0
            L_0x0bd5:
                r1 = r0
                goto L_0x0bd8
            L_0x0bd7:
                goto L_0x0bd5
            L_0x0bd8:
                java.lang.String r6 = r22.readString()
                int r7 = r22.readInt()
                int r14 = r22.readInt()
                int r15 = r22.readInt()
                r0 = r20
                r2 = r6
                r3 = r7
                r4 = r14
                r5 = r15
                r0.addCrossProfileIntentFilter(r1, r2, r3, r4, r5)
                r23.writeNoException()
                return r13
            L_0x0bf5:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                int r1 = r22.readInt()
                r8.clearPackagePersistentPreferredActivities(r0, r1)
                r23.writeNoException()
                return r13
            L_0x0c07:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x0c19
                android.os.Parcelable$Creator<android.content.IntentFilter> r1 = android.content.IntentFilter.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r10)
                android.content.IntentFilter r1 = (android.content.IntentFilter) r1
                goto L_0x0c1a
            L_0x0c19:
                r1 = r0
            L_0x0c1a:
                int r2 = r22.readInt()
                if (r2 == 0) goto L_0x0c29
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x0c2a
            L_0x0c29:
            L_0x0c2a:
                int r2 = r22.readInt()
                r8.addPersistentPreferredActivity(r1, r0, r2)
                r23.writeNoException()
                return r13
            L_0x0c35:
                r10.enforceInterface(r12)
                java.util.ArrayList r0 = new java.util.ArrayList
                r0.<init>()
                java.util.ArrayList r1 = new java.util.ArrayList
                r1.<init>()
                java.lang.String r2 = r22.readString()
                int r3 = r8.getPreferredActivities(r0, r1, r2)
                r23.writeNoException()
                r11.writeInt(r3)
                r11.writeTypedList(r0)
                r11.writeTypedList(r1)
                return r13
            L_0x0c57:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                r8.clearPackagePreferredActivities(r0)
                r23.writeNoException()
                return r13
            L_0x0c65:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x0c77
                android.os.Parcelable$Creator<android.content.IntentFilter> r1 = android.content.IntentFilter.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r10)
                android.content.IntentFilter r1 = (android.content.IntentFilter) r1
                goto L_0x0c78
            L_0x0c77:
                r1 = r0
            L_0x0c78:
                int r6 = r22.readInt()
                android.os.Parcelable$Creator<android.content.ComponentName> r2 = android.content.ComponentName.CREATOR
                java.lang.Object[] r2 = r10.createTypedArray(r2)
                r7 = r2
                android.content.ComponentName[] r7 = (android.content.ComponentName[]) r7
                int r2 = r22.readInt()
                if (r2 == 0) goto L_0x0c95
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
            L_0x0c93:
                r4 = r0
                goto L_0x0c96
            L_0x0c95:
                goto L_0x0c93
            L_0x0c96:
                int r14 = r22.readInt()
                r0 = r20
                r2 = r6
                r3 = r7
                r5 = r14
                r0.replacePreferredActivity(r1, r2, r3, r4, r5)
                r23.writeNoException()
                return r13
            L_0x0ca6:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x0cb8
                android.os.Parcelable$Creator<android.content.IntentFilter> r1 = android.content.IntentFilter.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r10)
                android.content.IntentFilter r1 = (android.content.IntentFilter) r1
                goto L_0x0cb9
            L_0x0cb8:
                r1 = r0
            L_0x0cb9:
                int r6 = r22.readInt()
                android.os.Parcelable$Creator<android.content.ComponentName> r2 = android.content.ComponentName.CREATOR
                java.lang.Object[] r2 = r10.createTypedArray(r2)
                r7 = r2
                android.content.ComponentName[] r7 = (android.content.ComponentName[]) r7
                int r2 = r22.readInt()
                if (r2 == 0) goto L_0x0cd6
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
            L_0x0cd4:
                r4 = r0
                goto L_0x0cd7
            L_0x0cd6:
                goto L_0x0cd4
            L_0x0cd7:
                int r14 = r22.readInt()
                r0 = r20
                r2 = r6
                r3 = r7
                r5 = r14
                r0.addPreferredActivity(r1, r2, r3, r4, r5)
                r23.writeNoException()
                return r13
            L_0x0ce7:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x0cf9
                android.os.Parcelable$Creator<android.content.Intent> r1 = android.content.Intent.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r10)
                android.content.Intent r1 = (android.content.Intent) r1
                goto L_0x0cfa
            L_0x0cf9:
                r1 = r0
            L_0x0cfa:
                java.lang.String r7 = r22.readString()
                int r14 = r22.readInt()
                int r2 = r22.readInt()
                if (r2 == 0) goto L_0x0d12
                android.os.Parcelable$Creator<android.content.IntentFilter> r2 = android.content.IntentFilter.CREATOR
                java.lang.Object r2 = r2.createFromParcel(r10)
                android.content.IntentFilter r2 = (android.content.IntentFilter) r2
                r4 = r2
                goto L_0x0d13
            L_0x0d12:
                r4 = r0
            L_0x0d13:
                int r15 = r22.readInt()
                int r2 = r22.readInt()
                if (r2 == 0) goto L_0x0d27
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
            L_0x0d25:
                r6 = r0
                goto L_0x0d28
            L_0x0d27:
                goto L_0x0d25
            L_0x0d28:
                r0 = r20
                r2 = r7
                r3 = r14
                r5 = r15
                r0.setLastChosenActivity(r1, r2, r3, r4, r5, r6)
                r23.writeNoException()
                return r13
            L_0x0d34:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x0d46
                android.os.Parcelable$Creator<android.content.Intent> r0 = android.content.Intent.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.Intent r0 = (android.content.Intent) r0
                goto L_0x0d47
            L_0x0d46:
            L_0x0d47:
                java.lang.String r1 = r22.readString()
                int r2 = r22.readInt()
                android.content.pm.ResolveInfo r3 = r8.getLastChosenActivity(r0, r1, r2)
                r23.writeNoException()
                if (r3 == 0) goto L_0x0d5f
                r11.writeInt(r13)
                r3.writeToParcel(r11, r13)
                goto L_0x0d62
            L_0x0d5f:
                r11.writeInt(r14)
            L_0x0d62:
                return r13
            L_0x0d63:
                r10.enforceInterface(r12)
                int r0 = r22.readInt()
                r8.resetApplicationPreferences(r0)
                r23.writeNoException()
                return r13
            L_0x0d71:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                java.lang.String r1 = r8.getInstallerPackageName(r0)
                r23.writeNoException()
                r11.writeString(r1)
                return r13
            L_0x0d83:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x0d95
                android.os.Parcelable$Creator<android.content.pm.VersionedPackage> r0 = android.content.pm.VersionedPackage.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.pm.VersionedPackage r0 = (android.content.pm.VersionedPackage) r0
                goto L_0x0d96
            L_0x0d95:
            L_0x0d96:
                android.os.IBinder r1 = r22.readStrongBinder()
                android.content.pm.IPackageDeleteObserver2 r1 = android.content.pm.IPackageDeleteObserver2.Stub.asInterface(r1)
                int r2 = r22.readInt()
                int r3 = r22.readInt()
                r8.deletePackageVersioned(r0, r1, r2, r3)
                r23.writeNoException()
                return r13
            L_0x0dad:
                r10.enforceInterface(r12)
                java.lang.String r6 = r22.readString()
                int r7 = r22.readInt()
                android.os.IBinder r0 = r22.readStrongBinder()
                android.content.pm.IPackageDeleteObserver r14 = android.content.pm.IPackageDeleteObserver.Stub.asInterface(r0)
                int r15 = r22.readInt()
                int r16 = r22.readInt()
                r0 = r20
                r1 = r6
                r2 = r7
                r3 = r14
                r4 = r15
                r5 = r16
                r0.deletePackageAsUser(r1, r2, r3, r4, r5)
                r23.writeNoException()
                return r13
            L_0x0dd7:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                int r1 = r22.readInt()
                java.lang.String r2 = r22.readString()
                r8.setApplicationCategoryHint(r0, r1, r2)
                r23.writeNoException()
                return r13
            L_0x0ded:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                java.lang.String r1 = r22.readString()
                r8.setInstallerPackageName(r0, r1)
                r23.writeNoException()
                return r13
            L_0x0dff:
                r10.enforceInterface(r12)
                int r0 = r22.readInt()
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x0e0e
                r14 = r13
            L_0x0e0e:
                r1 = r14
                r8.finishPackageInstall(r0, r1)
                r23.writeNoException()
                return r13
            L_0x0e16:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                int r1 = r22.readInt()
                android.content.pm.ParceledListSlice r2 = r8.queryInstrumentation(r0, r1)
                r23.writeNoException()
                if (r2 == 0) goto L_0x0e31
                r11.writeInt(r13)
                r2.writeToParcel(r11, r13)
                goto L_0x0e34
            L_0x0e31:
                r11.writeInt(r14)
            L_0x0e34:
                return r13
            L_0x0e35:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x0e47
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x0e48
            L_0x0e47:
            L_0x0e48:
                int r1 = r22.readInt()
                android.content.pm.InstrumentationInfo r2 = r8.getInstrumentationInfo(r0, r1)
                r23.writeNoException()
                if (r2 == 0) goto L_0x0e5c
                r11.writeInt(r13)
                r2.writeToParcel(r11, r13)
                goto L_0x0e5f
            L_0x0e5c:
                r11.writeInt(r14)
            L_0x0e5f:
                return r13
            L_0x0e60:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                int r1 = r22.readInt()
                int r2 = r22.readInt()
                java.lang.String r3 = r22.readString()
                android.content.pm.ParceledListSlice r4 = r8.queryContentProviders(r0, r1, r2, r3)
                r23.writeNoException()
                if (r4 == 0) goto L_0x0e83
                r11.writeInt(r13)
                r4.writeToParcel(r11, r13)
                goto L_0x0e86
            L_0x0e83:
                r11.writeInt(r14)
            L_0x0e86:
                return r13
            L_0x0e87:
                r10.enforceInterface(r12)
                java.util.ArrayList r0 = r22.createStringArrayList()
                android.os.Parcelable$Creator<android.content.pm.ProviderInfo> r1 = android.content.pm.ProviderInfo.CREATOR
                java.util.ArrayList r1 = r10.createTypedArrayList(r1)
                r8.querySyncProviders(r0, r1)
                r23.writeNoException()
                r11.writeStringList(r0)
                r11.writeTypedList(r1)
                return r13
            L_0x0ea1:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                int r1 = r22.readInt()
                int r2 = r22.readInt()
                android.content.pm.ProviderInfo r3 = r8.resolveContentProvider(r0, r1, r2)
                r23.writeNoException()
                if (r3 == 0) goto L_0x0ec0
                r11.writeInt(r13)
                r3.writeToParcel(r11, r13)
                goto L_0x0ec3
            L_0x0ec0:
                r11.writeInt(r14)
            L_0x0ec3:
                return r13
            L_0x0ec4:
                r10.enforceInterface(r12)
                int r0 = r22.readInt()
                android.content.pm.ParceledListSlice r1 = r8.getPersistentApplications(r0)
                r23.writeNoException()
                if (r1 == 0) goto L_0x0edb
                r11.writeInt(r13)
                r1.writeToParcel(r11, r13)
                goto L_0x0ede
            L_0x0edb:
                r11.writeInt(r14)
            L_0x0ede:
                return r13
            L_0x0edf:
                r10.enforceInterface(r12)
                int r0 = r22.readInt()
                int r1 = r22.readInt()
                android.content.pm.ParceledListSlice r2 = r8.getInstalledApplications(r0, r1)
                r23.writeNoException()
                if (r2 == 0) goto L_0x0efa
                r11.writeInt(r13)
                r2.writeToParcel(r11, r13)
                goto L_0x0efd
            L_0x0efa:
                r11.writeInt(r14)
            L_0x0efd:
                return r13
            L_0x0efe:
                r10.enforceInterface(r12)
                java.lang.String[] r0 = r22.createStringArray()
                int r1 = r22.readInt()
                int r2 = r22.readInt()
                android.content.pm.ParceledListSlice r3 = r8.getPackagesHoldingPermissions(r0, r1, r2)
                r23.writeNoException()
                if (r3 == 0) goto L_0x0f1d
                r11.writeInt(r13)
                r3.writeToParcel(r11, r13)
                goto L_0x0f20
            L_0x0f1d:
                r11.writeInt(r14)
            L_0x0f20:
                return r13
            L_0x0f21:
                r10.enforceInterface(r12)
                int r0 = r22.readInt()
                int r1 = r22.readInt()
                android.content.pm.ParceledListSlice r2 = r8.getInstalledPackages(r0, r1)
                r23.writeNoException()
                if (r2 == 0) goto L_0x0f3c
                r11.writeInt(r13)
                r2.writeToParcel(r11, r13)
                goto L_0x0f3f
            L_0x0f3c:
                r11.writeInt(r14)
            L_0x0f3f:
                return r13
            L_0x0f40:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x0f52
                android.os.Parcelable$Creator<android.content.Intent> r0 = android.content.Intent.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.Intent r0 = (android.content.Intent) r0
                goto L_0x0f53
            L_0x0f52:
            L_0x0f53:
                java.lang.String r1 = r22.readString()
                int r2 = r22.readInt()
                int r3 = r22.readInt()
                android.content.pm.ParceledListSlice r4 = r8.queryIntentContentProviders(r0, r1, r2, r3)
                r23.writeNoException()
                if (r4 == 0) goto L_0x0f6f
                r11.writeInt(r13)
                r4.writeToParcel(r11, r13)
                goto L_0x0f72
            L_0x0f6f:
                r11.writeInt(r14)
            L_0x0f72:
                return r13
            L_0x0f73:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x0f85
                android.os.Parcelable$Creator<android.content.Intent> r0 = android.content.Intent.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.Intent r0 = (android.content.Intent) r0
                goto L_0x0f86
            L_0x0f85:
            L_0x0f86:
                java.lang.String r1 = r22.readString()
                int r2 = r22.readInt()
                int r3 = r22.readInt()
                android.content.pm.ParceledListSlice r4 = r8.queryIntentServices(r0, r1, r2, r3)
                r23.writeNoException()
                if (r4 == 0) goto L_0x0fa2
                r11.writeInt(r13)
                r4.writeToParcel(r11, r13)
                goto L_0x0fa5
            L_0x0fa2:
                r11.writeInt(r14)
            L_0x0fa5:
                return r13
            L_0x0fa6:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x0fb8
                android.os.Parcelable$Creator<android.content.Intent> r0 = android.content.Intent.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.Intent r0 = (android.content.Intent) r0
                goto L_0x0fb9
            L_0x0fb8:
            L_0x0fb9:
                java.lang.String r1 = r22.readString()
                int r2 = r22.readInt()
                int r3 = r22.readInt()
                android.content.pm.ResolveInfo r4 = r8.resolveService(r0, r1, r2, r3)
                r23.writeNoException()
                if (r4 == 0) goto L_0x0fd5
                r11.writeInt(r13)
                r4.writeToParcel(r11, r13)
                goto L_0x0fd8
            L_0x0fd5:
                r11.writeInt(r14)
            L_0x0fd8:
                return r13
            L_0x0fd9:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x0feb
                android.os.Parcelable$Creator<android.content.Intent> r0 = android.content.Intent.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.Intent r0 = (android.content.Intent) r0
                goto L_0x0fec
            L_0x0feb:
            L_0x0fec:
                java.lang.String r1 = r22.readString()
                int r2 = r22.readInt()
                int r3 = r22.readInt()
                android.content.pm.ParceledListSlice r4 = r8.queryIntentReceivers(r0, r1, r2, r3)
                r23.writeNoException()
                if (r4 == 0) goto L_0x1008
                r11.writeInt(r13)
                r4.writeToParcel(r11, r13)
                goto L_0x100b
            L_0x1008:
                r11.writeInt(r14)
            L_0x100b:
                return r13
            L_0x100c:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x101e
                android.os.Parcelable$Creator<android.content.ComponentName> r1 = android.content.ComponentName.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r10)
                android.content.ComponentName r1 = (android.content.ComponentName) r1
                goto L_0x101f
            L_0x101e:
                r1 = r0
            L_0x101f:
                android.os.Parcelable$Creator<android.content.Intent> r2 = android.content.Intent.CREATOR
                java.lang.Object[] r2 = r10.createTypedArray(r2)
                r15 = r2
                android.content.Intent[] r15 = (android.content.Intent[]) r15
                java.lang.String[] r16 = r22.createStringArray()
                int r2 = r22.readInt()
                if (r2 == 0) goto L_0x103c
                android.os.Parcelable$Creator<android.content.Intent> r0 = android.content.Intent.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.Intent r0 = (android.content.Intent) r0
            L_0x103a:
                r4 = r0
                goto L_0x103d
            L_0x103c:
                goto L_0x103a
            L_0x103d:
                java.lang.String r17 = r22.readString()
                int r18 = r22.readInt()
                int r19 = r22.readInt()
                r0 = r20
                r2 = r15
                r3 = r16
                r5 = r17
                r6 = r18
                r7 = r19
                android.content.pm.ParceledListSlice r0 = r0.queryIntentActivityOptions(r1, r2, r3, r4, r5, r6, r7)
                r23.writeNoException()
                if (r0 == 0) goto L_0x1064
                r11.writeInt(r13)
                r0.writeToParcel(r11, r13)
                goto L_0x1067
            L_0x1064:
                r11.writeInt(r14)
            L_0x1067:
                return r13
            L_0x1068:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x107a
                android.os.Parcelable$Creator<android.content.Intent> r0 = android.content.Intent.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.Intent r0 = (android.content.Intent) r0
                goto L_0x107b
            L_0x107a:
            L_0x107b:
                java.lang.String r1 = r22.readString()
                int r2 = r22.readInt()
                int r3 = r22.readInt()
                android.content.pm.ParceledListSlice r4 = r8.queryIntentActivities(r0, r1, r2, r3)
                r23.writeNoException()
                if (r4 == 0) goto L_0x1097
                r11.writeInt(r13)
                r4.writeToParcel(r11, r13)
                goto L_0x109a
            L_0x1097:
                r11.writeInt(r14)
            L_0x109a:
                return r13
            L_0x109b:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x10ad
                android.os.Parcelable$Creator<android.content.Intent> r0 = android.content.Intent.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.Intent r0 = (android.content.Intent) r0
                goto L_0x10ae
            L_0x10ad:
            L_0x10ae:
                java.lang.String r1 = r22.readString()
                int r2 = r22.readInt()
                int r3 = r22.readInt()
                boolean r4 = r8.canForwardTo(r0, r1, r2, r3)
                r23.writeNoException()
                r11.writeInt(r4)
                return r13
            L_0x10c5:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x10d7
                android.os.Parcelable$Creator<android.content.Intent> r0 = android.content.Intent.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.Intent r0 = (android.content.Intent) r0
                goto L_0x10d8
            L_0x10d7:
            L_0x10d8:
                int r1 = r22.readInt()
                android.content.pm.ResolveInfo r2 = r8.findPersistentPreferredActivity(r0, r1)
                r23.writeNoException()
                if (r2 == 0) goto L_0x10ec
                r11.writeInt(r13)
                r2.writeToParcel(r11, r13)
                goto L_0x10ef
            L_0x10ec:
                r11.writeInt(r14)
            L_0x10ef:
                return r13
            L_0x10f0:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x1102
                android.os.Parcelable$Creator<android.content.Intent> r0 = android.content.Intent.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.Intent r0 = (android.content.Intent) r0
                goto L_0x1103
            L_0x1102:
            L_0x1103:
                java.lang.String r1 = r22.readString()
                int r2 = r22.readInt()
                int r3 = r22.readInt()
                android.content.pm.ResolveInfo r4 = r8.resolveIntent(r0, r1, r2, r3)
                r23.writeNoException()
                if (r4 == 0) goto L_0x111f
                r11.writeInt(r13)
                r4.writeToParcel(r11, r13)
                goto L_0x1122
            L_0x111f:
                r11.writeInt(r14)
            L_0x1122:
                return r13
            L_0x1123:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                java.lang.String[] r1 = r8.getAppOpPermissionPackages(r0)
                r23.writeNoException()
                r11.writeStringArray(r1)
                return r13
            L_0x1135:
                r10.enforceInterface(r12)
                int r0 = r22.readInt()
                boolean r1 = r8.isUidPrivileged(r0)
                r23.writeNoException()
                r11.writeInt(r1)
                return r13
            L_0x1147:
                r10.enforceInterface(r12)
                int r0 = r22.readInt()
                int r1 = r8.getPrivateFlagsForUid(r0)
                r23.writeNoException()
                r11.writeInt(r1)
                return r13
            L_0x1159:
                r10.enforceInterface(r12)
                int r0 = r22.readInt()
                int r1 = r8.getFlagsForUid(r0)
                r23.writeNoException()
                r11.writeInt(r1)
                return r13
            L_0x116b:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                int r1 = r8.getUidForSharedUser(r0)
                r23.writeNoException()
                r11.writeInt(r1)
                return r13
            L_0x117d:
                r10.enforceInterface(r12)
                int[] r0 = r22.createIntArray()
                java.lang.String[] r1 = r8.getNamesForUids(r0)
                r23.writeNoException()
                r11.writeStringArray(r1)
                return r13
            L_0x118f:
                r10.enforceInterface(r12)
                int r0 = r22.readInt()
                java.lang.String r1 = r8.getNameForUid(r0)
                r23.writeNoException()
                r11.writeString(r1)
                return r13
            L_0x11a1:
                r10.enforceInterface(r12)
                int r0 = r22.readInt()
                java.lang.String[] r1 = r8.getPackagesForUid(r0)
                r23.writeNoException()
                r11.writeStringArray(r1)
                return r13
            L_0x11b3:
                r10.enforceInterface(r12)
                java.util.List r0 = r20.getAllPackages()
                r23.writeNoException()
                r11.writeStringList(r0)
                return r13
            L_0x11c1:
                r10.enforceInterface(r12)
                int r0 = r22.readInt()
                int r1 = r22.readInt()
                int r2 = r8.checkUidSignatures(r0, r1)
                r23.writeNoException()
                r11.writeInt(r2)
                return r13
            L_0x11d7:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                java.lang.String r1 = r22.readString()
                int r2 = r8.checkSignatures(r0, r1)
                r23.writeNoException()
                r11.writeInt(r2)
                return r13
            L_0x11ed:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                boolean r1 = r8.isProtectedBroadcast(r0)
                r23.writeNoException()
                r11.writeInt(r1)
                return r13
            L_0x11ff:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                java.lang.String r1 = r22.readString()
                int r2 = r22.readInt()
                boolean r3 = r8.shouldShowRequestPermissionRationale(r0, r1, r2)
                r23.writeNoException()
                r11.writeInt(r3)
                return r13
            L_0x1219:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                java.lang.String r1 = r22.readString()
                int r2 = r22.readInt()
                int r3 = r22.readInt()
                boolean r4 = r8.removeWhitelistedRestrictedPermission(r0, r1, r2, r3)
                r23.writeNoException()
                r11.writeInt(r4)
                return r13
            L_0x1237:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                java.lang.String r1 = r22.readString()
                int r2 = r22.readInt()
                int r3 = r22.readInt()
                boolean r4 = r8.addWhitelistedRestrictedPermission(r0, r1, r2, r3)
                r23.writeNoException()
                r11.writeInt(r4)
                return r13
            L_0x1255:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                int r1 = r22.readInt()
                int r2 = r22.readInt()
                java.util.List r3 = r8.getWhitelistedRestrictedPermissions(r0, r1, r2)
                r23.writeNoException()
                r11.writeStringList(r3)
                return r13
            L_0x126f:
                r10.enforceInterface(r12)
                int r0 = r22.readInt()
                int r1 = r22.readInt()
                int r2 = r22.readInt()
                r8.updatePermissionFlagsForAllApps(r0, r1, r2)
                r23.writeNoException()
                return r13
            L_0x1285:
                r10.enforceInterface(r12)
                java.lang.String r7 = r22.readString()
                java.lang.String r15 = r22.readString()
                int r16 = r22.readInt()
                int r17 = r22.readInt()
                int r0 = r22.readInt()
                if (r0 == 0) goto L_0x12a0
                r5 = r13
                goto L_0x12a1
            L_0x12a0:
                r5 = r14
            L_0x12a1:
                int r14 = r22.readInt()
                r0 = r20
                r1 = r7
                r2 = r15
                r3 = r16
                r4 = r17
                r6 = r14
                r0.updatePermissionFlags(r1, r2, r3, r4, r5, r6)
                r23.writeNoException()
                return r13
            L_0x12b5:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                java.lang.String r1 = r22.readString()
                int r2 = r22.readInt()
                int r3 = r8.getPermissionFlags(r0, r1, r2)
                r23.writeNoException()
                r11.writeInt(r3)
                return r13
            L_0x12cf:
                r10.enforceInterface(r12)
                r20.resetRuntimePermissions()
                r23.writeNoException()
                return r13
            L_0x12d9:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                java.lang.String r1 = r22.readString()
                int r2 = r22.readInt()
                r8.revokeRuntimePermission(r0, r1, r2)
                r23.writeNoException()
                return r13
            L_0x12ef:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                java.lang.String r1 = r22.readString()
                int r2 = r22.readInt()
                r8.grantRuntimePermission(r0, r1, r2)
                r23.writeNoException()
                return r13
            L_0x1305:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                r8.removePermission(r0)
                r23.writeNoException()
                return r13
            L_0x1313:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x1325
                android.os.Parcelable$Creator<android.content.pm.PermissionInfo> r0 = android.content.pm.PermissionInfo.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.pm.PermissionInfo r0 = (android.content.pm.PermissionInfo) r0
                goto L_0x1326
            L_0x1325:
            L_0x1326:
                boolean r1 = r8.addPermission(r0)
                r23.writeNoException()
                r11.writeInt(r1)
                return r13
            L_0x1331:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                int r1 = r22.readInt()
                int r2 = r8.checkUidPermission(r0, r1)
                r23.writeNoException()
                r11.writeInt(r2)
                return r13
            L_0x1347:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                java.lang.String r1 = r22.readString()
                int r2 = r22.readInt()
                int r3 = r8.checkPermission(r0, r1, r2)
                r23.writeNoException()
                r11.writeInt(r3)
                return r13
            L_0x1361:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x1373
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x1374
            L_0x1373:
            L_0x1374:
                int r1 = r22.readInt()
                int r2 = r22.readInt()
                android.content.pm.ProviderInfo r3 = r8.getProviderInfo(r0, r1, r2)
                r23.writeNoException()
                if (r3 == 0) goto L_0x138c
                r11.writeInt(r13)
                r3.writeToParcel(r11, r13)
                goto L_0x138f
            L_0x138c:
                r11.writeInt(r14)
            L_0x138f:
                return r13
            L_0x1390:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x13a2
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x13a3
            L_0x13a2:
            L_0x13a3:
                int r1 = r22.readInt()
                int r2 = r22.readInt()
                android.content.pm.ServiceInfo r3 = r8.getServiceInfo(r0, r1, r2)
                r23.writeNoException()
                if (r3 == 0) goto L_0x13bb
                r11.writeInt(r13)
                r3.writeToParcel(r11, r13)
                goto L_0x13be
            L_0x13bb:
                r11.writeInt(r14)
            L_0x13be:
                return r13
            L_0x13bf:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x13d1
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x13d2
            L_0x13d1:
            L_0x13d2:
                int r1 = r22.readInt()
                int r2 = r22.readInt()
                android.content.pm.ActivityInfo r3 = r8.getReceiverInfo(r0, r1, r2)
                r23.writeNoException()
                if (r3 == 0) goto L_0x13ea
                r11.writeInt(r13)
                r3.writeToParcel(r11, r13)
                goto L_0x13ed
            L_0x13ea:
                r11.writeInt(r14)
            L_0x13ed:
                return r13
            L_0x13ee:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x1400
                android.os.Parcelable$Creator<android.content.ComponentName> r1 = android.content.ComponentName.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r10)
                android.content.ComponentName r1 = (android.content.ComponentName) r1
                goto L_0x1401
            L_0x1400:
                r1 = r0
            L_0x1401:
                int r2 = r22.readInt()
                if (r2 == 0) goto L_0x1410
                android.os.Parcelable$Creator<android.content.Intent> r0 = android.content.Intent.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.Intent r0 = (android.content.Intent) r0
                goto L_0x1411
            L_0x1410:
            L_0x1411:
                java.lang.String r2 = r22.readString()
                boolean r3 = r8.activitySupportsIntent(r1, r0, r2)
                r23.writeNoException()
                r11.writeInt(r3)
                return r13
            L_0x1420:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x1432
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x1433
            L_0x1432:
            L_0x1433:
                int r1 = r22.readInt()
                int r2 = r22.readInt()
                android.content.pm.ActivityInfo r3 = r8.getActivityInfo(r0, r1, r2)
                r23.writeNoException()
                if (r3 == 0) goto L_0x144b
                r11.writeInt(r13)
                r3.writeToParcel(r11, r13)
                goto L_0x144e
            L_0x144b:
                r11.writeInt(r14)
            L_0x144e:
                return r13
            L_0x144f:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                int r1 = r22.readInt()
                int r2 = r22.readInt()
                android.content.pm.ApplicationInfo r3 = r8.getApplicationInfo(r0, r1, r2)
                r23.writeNoException()
                if (r3 == 0) goto L_0x146e
                r11.writeInt(r13)
                r3.writeToParcel(r11, r13)
                goto L_0x1471
            L_0x146e:
                r11.writeInt(r14)
            L_0x1471:
                return r13
            L_0x1472:
                r10.enforceInterface(r12)
                int r0 = r22.readInt()
                android.content.pm.ParceledListSlice r1 = r8.getAllPermissionGroups(r0)
                r23.writeNoException()
                if (r1 == 0) goto L_0x1489
                r11.writeInt(r13)
                r1.writeToParcel(r11, r13)
                goto L_0x148c
            L_0x1489:
                r11.writeInt(r14)
            L_0x148c:
                return r13
            L_0x148d:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                int r1 = r22.readInt()
                android.content.pm.PermissionGroupInfo r2 = r8.getPermissionGroupInfo(r0, r1)
                r23.writeNoException()
                if (r2 == 0) goto L_0x14a8
                r11.writeInt(r13)
                r2.writeToParcel(r11, r13)
                goto L_0x14ab
            L_0x14a8:
                r11.writeInt(r14)
            L_0x14ab:
                return r13
            L_0x14ac:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                int r1 = r22.readInt()
                android.content.pm.ParceledListSlice r2 = r8.queryPermissionsByGroup(r0, r1)
                r23.writeNoException()
                if (r2 == 0) goto L_0x14c7
                r11.writeInt(r13)
                r2.writeToParcel(r11, r13)
                goto L_0x14ca
            L_0x14c7:
                r11.writeInt(r14)
            L_0x14ca:
                return r13
            L_0x14cb:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                java.lang.String r1 = r22.readString()
                int r2 = r22.readInt()
                android.content.pm.PermissionInfo r3 = r8.getPermissionInfo(r0, r1, r2)
                r23.writeNoException()
                if (r3 == 0) goto L_0x14ea
                r11.writeInt(r13)
                r3.writeToParcel(r11, r13)
                goto L_0x14ed
            L_0x14ea:
                r11.writeInt(r14)
            L_0x14ed:
                return r13
            L_0x14ee:
                r10.enforceInterface(r12)
                java.lang.String[] r0 = r22.createStringArray()
                java.lang.String[] r1 = r8.canonicalToCurrentPackageNames(r0)
                r23.writeNoException()
                r11.writeStringArray(r1)
                return r13
            L_0x1500:
                r10.enforceInterface(r12)
                java.lang.String[] r0 = r22.createStringArray()
                java.lang.String[] r1 = r8.currentToCanonicalPackageNames(r0)
                r23.writeNoException()
                r11.writeStringArray(r1)
                return r13
            L_0x1512:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                int r1 = r22.readInt()
                int r2 = r22.readInt()
                int[] r3 = r8.getPackageGids(r0, r1, r2)
                r23.writeNoException()
                r11.writeIntArray(r3)
                return r13
            L_0x152c:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                int r1 = r22.readInt()
                int r2 = r22.readInt()
                int r3 = r8.getPackageUid(r0, r1, r2)
                r23.writeNoException()
                r11.writeInt(r3)
                return r13
            L_0x1546:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x1558
                android.os.Parcelable$Creator<android.content.pm.VersionedPackage> r0 = android.content.pm.VersionedPackage.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.pm.VersionedPackage r0 = (android.content.pm.VersionedPackage) r0
                goto L_0x1559
            L_0x1558:
            L_0x1559:
                int r1 = r22.readInt()
                int r2 = r22.readInt()
                android.content.pm.PackageInfo r3 = r8.getPackageInfoVersioned(r0, r1, r2)
                r23.writeNoException()
                if (r3 == 0) goto L_0x1571
                r11.writeInt(r13)
                r3.writeToParcel(r11, r13)
                goto L_0x1574
            L_0x1571:
                r11.writeInt(r14)
            L_0x1574:
                return r13
            L_0x1575:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                int r1 = r22.readInt()
                int r2 = r22.readInt()
                android.content.pm.PackageInfo r3 = r8.getPackageInfo(r0, r1, r2)
                r23.writeNoException()
                if (r3 == 0) goto L_0x1594
                r11.writeInt(r13)
                r3.writeToParcel(r11, r13)
                goto L_0x1597
            L_0x1594:
                r11.writeInt(r14)
            L_0x1597:
                return r13
            L_0x1598:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                int r1 = r22.readInt()
                boolean r2 = r8.isPackageAvailable(r0, r1)
                r23.writeNoException()
                r11.writeInt(r2)
                return r13
            L_0x15ae:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                int r1 = r22.readInt()
                r8.checkPackageStartable(r0, r1)
                r23.writeNoException()
                return r13
            L_0x15c0:
                r11.writeString(r12)
                return r13
            */
            throw new UnsupportedOperationException("Method not decompiled: android.content.pm.IPackageManager.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IPackageManager {
            public static IPackageManager sDefaultImpl;
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

            public void checkPackageStartable(String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(1, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().checkPackageStartable(packageName, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isPackageAvailable(String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    boolean z = false;
                    if (!this.mRemote.transact(2, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isPackageAvailable(packageName, userId);
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

            public PackageInfo getPackageInfo(String packageName, int flags, int userId) throws RemoteException {
                PackageInfo _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(flags);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(3, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPackageInfo(packageName, flags, userId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = PackageInfo.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    PackageInfo _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public PackageInfo getPackageInfoVersioned(VersionedPackage versionedPackage, int flags, int userId) throws RemoteException {
                PackageInfo _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (versionedPackage != null) {
                        _data.writeInt(1);
                        versionedPackage.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(flags);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(4, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPackageInfoVersioned(versionedPackage, flags, userId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = PackageInfo.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    PackageInfo _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getPackageUid(String packageName, int flags, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(flags);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(5, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPackageUid(packageName, flags, userId);
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

            public int[] getPackageGids(String packageName, int flags, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(flags);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(6, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPackageGids(packageName, flags, userId);
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

            public String[] currentToCanonicalPackageNames(String[] names) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStringArray(names);
                    if (!this.mRemote.transact(7, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().currentToCanonicalPackageNames(names);
                    }
                    _reply.readException();
                    String[] _result = _reply.createStringArray();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String[] canonicalToCurrentPackageNames(String[] names) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStringArray(names);
                    if (!this.mRemote.transact(8, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().canonicalToCurrentPackageNames(names);
                    }
                    _reply.readException();
                    String[] _result = _reply.createStringArray();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public PermissionInfo getPermissionInfo(String name, String packageName, int flags) throws RemoteException {
                PermissionInfo _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    _data.writeString(packageName);
                    _data.writeInt(flags);
                    if (!this.mRemote.transact(9, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPermissionInfo(name, packageName, flags);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = PermissionInfo.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    PermissionInfo _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public ParceledListSlice queryPermissionsByGroup(String group, int flags) throws RemoteException {
                ParceledListSlice _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(group);
                    _data.writeInt(flags);
                    if (!this.mRemote.transact(10, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().queryPermissionsByGroup(group, flags);
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

            public PermissionGroupInfo getPermissionGroupInfo(String name, int flags) throws RemoteException {
                PermissionGroupInfo _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    _data.writeInt(flags);
                    if (!this.mRemote.transact(11, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPermissionGroupInfo(name, flags);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = PermissionGroupInfo.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    PermissionGroupInfo _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public ParceledListSlice getAllPermissionGroups(int flags) throws RemoteException {
                ParceledListSlice _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(flags);
                    if (!this.mRemote.transact(12, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAllPermissionGroups(flags);
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

            public ApplicationInfo getApplicationInfo(String packageName, int flags, int userId) throws RemoteException {
                ApplicationInfo _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(flags);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(13, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getApplicationInfo(packageName, flags, userId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = ApplicationInfo.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    ApplicationInfo _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public ActivityInfo getActivityInfo(ComponentName className, int flags, int userId) throws RemoteException {
                ActivityInfo _result;
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
                    _data.writeInt(flags);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(14, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getActivityInfo(className, flags, userId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = ActivityInfo.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    ActivityInfo _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean activitySupportsIntent(ComponentName className, Intent intent, String resolvedType) throws RemoteException {
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
                    if (intent != null) {
                        _data.writeInt(1);
                        intent.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(resolvedType);
                    if (!this.mRemote.transact(15, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().activitySupportsIntent(className, intent, resolvedType);
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

            public ActivityInfo getReceiverInfo(ComponentName className, int flags, int userId) throws RemoteException {
                ActivityInfo _result;
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
                    _data.writeInt(flags);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(16, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getReceiverInfo(className, flags, userId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = ActivityInfo.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    ActivityInfo _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public ServiceInfo getServiceInfo(ComponentName className, int flags, int userId) throws RemoteException {
                ServiceInfo _result;
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
                    _data.writeInt(flags);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(17, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getServiceInfo(className, flags, userId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = ServiceInfo.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    ServiceInfo _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public ProviderInfo getProviderInfo(ComponentName className, int flags, int userId) throws RemoteException {
                ProviderInfo _result;
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
                    _data.writeInt(flags);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(18, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getProviderInfo(className, flags, userId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = ProviderInfo.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    ProviderInfo _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int checkPermission(String permName, String pkgName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(permName);
                    _data.writeString(pkgName);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(19, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().checkPermission(permName, pkgName, userId);
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

            public int checkUidPermission(String permName, int uid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(permName);
                    _data.writeInt(uid);
                    if (!this.mRemote.transact(20, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().checkUidPermission(permName, uid);
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

            public boolean addPermission(PermissionInfo info) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (info != null) {
                        _data.writeInt(1);
                        info.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(21, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().addPermission(info);
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

            public void removePermission(String name) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    if (this.mRemote.transact(22, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().removePermission(name);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void grantRuntimePermission(String packageName, String permissionName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeString(permissionName);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(23, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().grantRuntimePermission(packageName, permissionName, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void revokeRuntimePermission(String packageName, String permissionName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeString(permissionName);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(24, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().revokeRuntimePermission(packageName, permissionName, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void resetRuntimePermissions() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(25, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().resetRuntimePermissions();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getPermissionFlags(String permissionName, String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(permissionName);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(26, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPermissionFlags(permissionName, packageName, userId);
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

            public void updatePermissionFlags(String permissionName, String packageName, int flagMask, int flagValues, boolean checkAdjustPolicyFlagPermission, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeString(permissionName);
                        try {
                            _data.writeString(packageName);
                            try {
                                _data.writeInt(flagMask);
                                try {
                                    _data.writeInt(flagValues);
                                } catch (Throwable th) {
                                    th = th;
                                    boolean z = checkAdjustPolicyFlagPermission;
                                    int i = userId;
                                    _reply.recycle();
                                    _data.recycle();
                                    throw th;
                                }
                            } catch (Throwable th2) {
                                th = th2;
                                int i2 = flagValues;
                                boolean z2 = checkAdjustPolicyFlagPermission;
                                int i3 = userId;
                                _reply.recycle();
                                _data.recycle();
                                throw th;
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            int i4 = flagMask;
                            int i22 = flagValues;
                            boolean z22 = checkAdjustPolicyFlagPermission;
                            int i32 = userId;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                        try {
                            _data.writeInt(checkAdjustPolicyFlagPermission ? 1 : 0);
                            try {
                                _data.writeInt(userId);
                                if (this.mRemote.transact(27, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                    _reply.readException();
                                    _reply.recycle();
                                    _data.recycle();
                                    return;
                                }
                                Stub.getDefaultImpl().updatePermissionFlags(permissionName, packageName, flagMask, flagValues, checkAdjustPolicyFlagPermission, userId);
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
                            int i322 = userId;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th6) {
                        th = th6;
                        String str = packageName;
                        int i42 = flagMask;
                        int i222 = flagValues;
                        boolean z222 = checkAdjustPolicyFlagPermission;
                        int i3222 = userId;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th7) {
                    th = th7;
                    String str2 = permissionName;
                    String str3 = packageName;
                    int i422 = flagMask;
                    int i2222 = flagValues;
                    boolean z2222 = checkAdjustPolicyFlagPermission;
                    int i32222 = userId;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public void updatePermissionFlagsForAllApps(int flagMask, int flagValues, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(flagMask);
                    _data.writeInt(flagValues);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(28, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().updatePermissionFlagsForAllApps(flagMask, flagValues, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public List<String> getWhitelistedRestrictedPermissions(String packageName, int flags, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(flags);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(29, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getWhitelistedRestrictedPermissions(packageName, flags, userId);
                    }
                    _reply.readException();
                    List<String> _result = _reply.createStringArrayList();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean addWhitelistedRestrictedPermission(String packageName, String permission, int whitelistFlags, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeString(permission);
                    _data.writeInt(whitelistFlags);
                    _data.writeInt(userId);
                    boolean z = false;
                    if (!this.mRemote.transact(30, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().addWhitelistedRestrictedPermission(packageName, permission, whitelistFlags, userId);
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

            public boolean removeWhitelistedRestrictedPermission(String packageName, String permission, int whitelistFlags, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeString(permission);
                    _data.writeInt(whitelistFlags);
                    _data.writeInt(userId);
                    boolean z = false;
                    if (!this.mRemote.transact(31, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().removeWhitelistedRestrictedPermission(packageName, permission, whitelistFlags, userId);
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

            public boolean shouldShowRequestPermissionRationale(String permissionName, String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(permissionName);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    boolean z = false;
                    if (!this.mRemote.transact(32, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().shouldShowRequestPermissionRationale(permissionName, packageName, userId);
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

            public boolean isProtectedBroadcast(String actionName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(actionName);
                    boolean z = false;
                    if (!this.mRemote.transact(33, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isProtectedBroadcast(actionName);
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

            public int checkSignatures(String pkg1, String pkg2) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(pkg1);
                    _data.writeString(pkg2);
                    if (!this.mRemote.transact(34, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().checkSignatures(pkg1, pkg2);
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

            public int checkUidSignatures(int uid1, int uid2) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid1);
                    _data.writeInt(uid2);
                    if (!this.mRemote.transact(35, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().checkUidSignatures(uid1, uid2);
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

            public List<String> getAllPackages() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(36, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAllPackages();
                    }
                    _reply.readException();
                    List<String> _result = _reply.createStringArrayList();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String[] getPackagesForUid(int uid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    if (!this.mRemote.transact(37, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPackagesForUid(uid);
                    }
                    _reply.readException();
                    String[] _result = _reply.createStringArray();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String getNameForUid(int uid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    if (!this.mRemote.transact(38, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getNameForUid(uid);
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

            public String[] getNamesForUids(int[] uids) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeIntArray(uids);
                    if (!this.mRemote.transact(39, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getNamesForUids(uids);
                    }
                    _reply.readException();
                    String[] _result = _reply.createStringArray();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getUidForSharedUser(String sharedUserName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(sharedUserName);
                    if (!this.mRemote.transact(40, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getUidForSharedUser(sharedUserName);
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

            public int getFlagsForUid(int uid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    if (!this.mRemote.transact(41, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getFlagsForUid(uid);
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

            public int getPrivateFlagsForUid(int uid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    if (!this.mRemote.transact(42, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPrivateFlagsForUid(uid);
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

            public boolean isUidPrivileged(int uid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    boolean z = false;
                    if (!this.mRemote.transact(43, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isUidPrivileged(uid);
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

            public String[] getAppOpPermissionPackages(String permissionName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(permissionName);
                    if (!this.mRemote.transact(44, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAppOpPermissionPackages(permissionName);
                    }
                    _reply.readException();
                    String[] _result = _reply.createStringArray();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public ResolveInfo resolveIntent(Intent intent, String resolvedType, int flags, int userId) throws RemoteException {
                ResolveInfo _result;
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
                    _data.writeString(resolvedType);
                    _data.writeInt(flags);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(45, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().resolveIntent(intent, resolvedType, flags, userId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = ResolveInfo.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    ResolveInfo _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public ResolveInfo findPersistentPreferredActivity(Intent intent, int userId) throws RemoteException {
                ResolveInfo _result;
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
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(46, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().findPersistentPreferredActivity(intent, userId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = ResolveInfo.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    ResolveInfo _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean canForwardTo(Intent intent, String resolvedType, int sourceUserId, int targetUserId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (intent != null) {
                        _data.writeInt(1);
                        intent.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(resolvedType);
                    _data.writeInt(sourceUserId);
                    _data.writeInt(targetUserId);
                    if (!this.mRemote.transact(47, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().canForwardTo(intent, resolvedType, sourceUserId, targetUserId);
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

            public ParceledListSlice queryIntentActivities(Intent intent, String resolvedType, int flags, int userId) throws RemoteException {
                ParceledListSlice _result;
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
                    _data.writeString(resolvedType);
                    _data.writeInt(flags);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(48, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().queryIntentActivities(intent, resolvedType, flags, userId);
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

            public ParceledListSlice queryIntentActivityOptions(ComponentName caller, Intent[] specifics, String[] specificTypes, Intent intent, String resolvedType, int flags, int userId) throws RemoteException {
                ParceledListSlice _result;
                ComponentName componentName = caller;
                Intent intent2 = intent;
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
                        _data.writeTypedArray(specifics, 0);
                        try {
                            _data.writeStringArray(specificTypes);
                            if (intent2 != null) {
                                _data.writeInt(1);
                                intent2.writeToParcel(_data, 0);
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
                    } catch (Throwable th2) {
                        th = th2;
                        String[] strArr = specificTypes;
                        String str2 = resolvedType;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeString(resolvedType);
                        _data.writeInt(flags);
                        _data.writeInt(userId);
                        if (this.mRemote.transact(49, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
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
                        }
                        ParceledListSlice queryIntentActivityOptions = Stub.getDefaultImpl().queryIntentActivityOptions(caller, specifics, specificTypes, intent, resolvedType, flags, userId);
                        _reply.recycle();
                        _data.recycle();
                        return queryIntentActivityOptions;
                    } catch (Throwable th3) {
                        th = th3;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th4) {
                    th = th4;
                    Intent[] intentArr = specifics;
                    String[] strArr2 = specificTypes;
                    String str22 = resolvedType;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public ParceledListSlice queryIntentReceivers(Intent intent, String resolvedType, int flags, int userId) throws RemoteException {
                ParceledListSlice _result;
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
                    _data.writeString(resolvedType);
                    _data.writeInt(flags);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(50, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().queryIntentReceivers(intent, resolvedType, flags, userId);
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

            public ResolveInfo resolveService(Intent intent, String resolvedType, int flags, int userId) throws RemoteException {
                ResolveInfo _result;
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
                    _data.writeString(resolvedType);
                    _data.writeInt(flags);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(51, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().resolveService(intent, resolvedType, flags, userId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = ResolveInfo.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    ResolveInfo _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public ParceledListSlice queryIntentServices(Intent intent, String resolvedType, int flags, int userId) throws RemoteException {
                ParceledListSlice _result;
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
                    _data.writeString(resolvedType);
                    _data.writeInt(flags);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(52, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().queryIntentServices(intent, resolvedType, flags, userId);
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

            public ParceledListSlice queryIntentContentProviders(Intent intent, String resolvedType, int flags, int userId) throws RemoteException {
                ParceledListSlice _result;
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
                    _data.writeString(resolvedType);
                    _data.writeInt(flags);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(53, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().queryIntentContentProviders(intent, resolvedType, flags, userId);
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

            public ParceledListSlice getInstalledPackages(int flags, int userId) throws RemoteException {
                ParceledListSlice _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(flags);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(54, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getInstalledPackages(flags, userId);
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

            public ParceledListSlice getPackagesHoldingPermissions(String[] permissions, int flags, int userId) throws RemoteException {
                ParceledListSlice _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStringArray(permissions);
                    _data.writeInt(flags);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(55, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPackagesHoldingPermissions(permissions, flags, userId);
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

            public ParceledListSlice getInstalledApplications(int flags, int userId) throws RemoteException {
                ParceledListSlice _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(flags);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(56, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getInstalledApplications(flags, userId);
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

            public ParceledListSlice getPersistentApplications(int flags) throws RemoteException {
                ParceledListSlice _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(flags);
                    if (!this.mRemote.transact(57, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPersistentApplications(flags);
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

            public ProviderInfo resolveContentProvider(String name, int flags, int userId) throws RemoteException {
                ProviderInfo _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    _data.writeInt(flags);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(58, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().resolveContentProvider(name, flags, userId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = ProviderInfo.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    ProviderInfo _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void querySyncProviders(List<String> outNames, List<ProviderInfo> outInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStringList(outNames);
                    _data.writeTypedList(outInfo);
                    if (this.mRemote.transact(59, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.readStringList(outNames);
                        _reply.readTypedList(outInfo, ProviderInfo.CREATOR);
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().querySyncProviders(outNames, outInfo);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public ParceledListSlice queryContentProviders(String processName, int uid, int flags, String metaDataKey) throws RemoteException {
                ParceledListSlice _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(processName);
                    _data.writeInt(uid);
                    _data.writeInt(flags);
                    _data.writeString(metaDataKey);
                    if (!this.mRemote.transact(60, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().queryContentProviders(processName, uid, flags, metaDataKey);
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

            public InstrumentationInfo getInstrumentationInfo(ComponentName className, int flags) throws RemoteException {
                InstrumentationInfo _result;
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
                    _data.writeInt(flags);
                    if (!this.mRemote.transact(61, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getInstrumentationInfo(className, flags);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = InstrumentationInfo.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    InstrumentationInfo _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public ParceledListSlice queryInstrumentation(String targetPackage, int flags) throws RemoteException {
                ParceledListSlice _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(targetPackage);
                    _data.writeInt(flags);
                    if (!this.mRemote.transact(62, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().queryInstrumentation(targetPackage, flags);
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

            public void finishPackageInstall(int token, boolean didLaunch) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(token);
                    _data.writeInt(didLaunch);
                    if (this.mRemote.transact(63, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().finishPackageInstall(token, didLaunch);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setInstallerPackageName(String targetPackage, String installerPackageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(targetPackage);
                    _data.writeString(installerPackageName);
                    if (this.mRemote.transact(64, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setInstallerPackageName(targetPackage, installerPackageName);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setApplicationCategoryHint(String packageName, int categoryHint, String callerPackageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(categoryHint);
                    _data.writeString(callerPackageName);
                    if (this.mRemote.transact(65, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setApplicationCategoryHint(packageName, categoryHint, callerPackageName);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void deletePackageAsUser(String packageName, int versionCode, IPackageDeleteObserver observer, int userId, int flags) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(versionCode);
                    _data.writeStrongBinder(observer != null ? observer.asBinder() : null);
                    _data.writeInt(userId);
                    _data.writeInt(flags);
                    if (this.mRemote.transact(66, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().deletePackageAsUser(packageName, versionCode, observer, userId, flags);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void deletePackageVersioned(VersionedPackage versionedPackage, IPackageDeleteObserver2 observer, int userId, int flags) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (versionedPackage != null) {
                        _data.writeInt(1);
                        versionedPackage.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeStrongBinder(observer != null ? observer.asBinder() : null);
                    _data.writeInt(userId);
                    _data.writeInt(flags);
                    if (this.mRemote.transact(67, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().deletePackageVersioned(versionedPackage, observer, userId, flags);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String getInstallerPackageName(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    if (!this.mRemote.transact(68, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getInstallerPackageName(packageName);
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

            public void resetApplicationPreferences(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(69, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().resetApplicationPreferences(userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public ResolveInfo getLastChosenActivity(Intent intent, String resolvedType, int flags) throws RemoteException {
                ResolveInfo _result;
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
                    _data.writeString(resolvedType);
                    _data.writeInt(flags);
                    if (!this.mRemote.transact(70, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getLastChosenActivity(intent, resolvedType, flags);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = ResolveInfo.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    ResolveInfo _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setLastChosenActivity(Intent intent, String resolvedType, int flags, IntentFilter filter, int match, ComponentName activity) throws RemoteException {
                Intent intent2 = intent;
                IntentFilter intentFilter = filter;
                ComponentName componentName = activity;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (intent2 != null) {
                        _data.writeInt(1);
                        intent2.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    try {
                        _data.writeString(resolvedType);
                    } catch (Throwable th) {
                        th = th;
                        int i = flags;
                        int i2 = match;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(flags);
                        if (intentFilter != null) {
                            _data.writeInt(1);
                            intentFilter.writeToParcel(_data, 0);
                        } else {
                            _data.writeInt(0);
                        }
                        try {
                            _data.writeInt(match);
                            if (componentName != null) {
                                _data.writeInt(1);
                                componentName.writeToParcel(_data, 0);
                            } else {
                                _data.writeInt(0);
                            }
                            if (this.mRemote.transact(71, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                _reply.readException();
                                _reply.recycle();
                                _data.recycle();
                                return;
                            }
                            Stub.getDefaultImpl().setLastChosenActivity(intent, resolvedType, flags, filter, match, activity);
                            _reply.recycle();
                            _data.recycle();
                        } catch (Throwable th2) {
                            th = th2;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        int i22 = match;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th4) {
                    th = th4;
                    String str = resolvedType;
                    int i3 = flags;
                    int i222 = match;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public void addPreferredActivity(IntentFilter filter, int match, ComponentName[] set, ComponentName activity, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (filter != null) {
                        _data.writeInt(1);
                        filter.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(match);
                    _data.writeTypedArray(set, 0);
                    if (activity != null) {
                        _data.writeInt(1);
                        activity.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(userId);
                    if (this.mRemote.transact(72, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().addPreferredActivity(filter, match, set, activity, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void replacePreferredActivity(IntentFilter filter, int match, ComponentName[] set, ComponentName activity, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (filter != null) {
                        _data.writeInt(1);
                        filter.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(match);
                    _data.writeTypedArray(set, 0);
                    if (activity != null) {
                        _data.writeInt(1);
                        activity.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(userId);
                    if (this.mRemote.transact(73, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().replacePreferredActivity(filter, match, set, activity, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void clearPackagePreferredActivities(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    if (this.mRemote.transact(74, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().clearPackagePreferredActivities(packageName);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getPreferredActivities(List<IntentFilter> outFilters, List<ComponentName> outActivities, String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    if (!this.mRemote.transact(75, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPreferredActivities(outFilters, outActivities, packageName);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.readTypedList(outFilters, IntentFilter.CREATOR);
                    _reply.readTypedList(outActivities, ComponentName.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void addPersistentPreferredActivity(IntentFilter filter, ComponentName activity, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (filter != null) {
                        _data.writeInt(1);
                        filter.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (activity != null) {
                        _data.writeInt(1);
                        activity.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(userId);
                    if (this.mRemote.transact(76, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().addPersistentPreferredActivity(filter, activity, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void clearPackagePersistentPreferredActivities(String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(77, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().clearPackagePersistentPreferredActivities(packageName, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void addCrossProfileIntentFilter(IntentFilter intentFilter, String ownerPackage, int sourceUserId, int targetUserId, int flags) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (intentFilter != null) {
                        _data.writeInt(1);
                        intentFilter.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(ownerPackage);
                    _data.writeInt(sourceUserId);
                    _data.writeInt(targetUserId);
                    _data.writeInt(flags);
                    if (this.mRemote.transact(78, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().addCrossProfileIntentFilter(intentFilter, ownerPackage, sourceUserId, targetUserId, flags);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void clearCrossProfileIntentFilters(int sourceUserId, String ownerPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sourceUserId);
                    _data.writeString(ownerPackage);
                    if (this.mRemote.transact(79, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().clearCrossProfileIntentFilters(sourceUserId, ownerPackage);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String[] setDistractingPackageRestrictionsAsUser(String[] packageNames, int restrictionFlags, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStringArray(packageNames);
                    _data.writeInt(restrictionFlags);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(80, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setDistractingPackageRestrictionsAsUser(packageNames, restrictionFlags, userId);
                    }
                    _reply.readException();
                    String[] _result = _reply.createStringArray();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String[] setPackagesSuspendedAsUser(String[] packageNames, boolean suspended, PersistableBundle appExtras, PersistableBundle launcherExtras, SuspendDialogInfo dialogInfo, String callingPackage, int userId) throws RemoteException {
                PersistableBundle persistableBundle = appExtras;
                PersistableBundle persistableBundle2 = launcherExtras;
                SuspendDialogInfo suspendDialogInfo = dialogInfo;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeStringArray(packageNames);
                        try {
                            _data.writeInt(suspended ? 1 : 0);
                            if (persistableBundle != null) {
                                _data.writeInt(1);
                                persistableBundle.writeToParcel(_data, 0);
                            } else {
                                _data.writeInt(0);
                            }
                            if (persistableBundle2 != null) {
                                _data.writeInt(1);
                                persistableBundle2.writeToParcel(_data, 0);
                            } else {
                                _data.writeInt(0);
                            }
                            if (suspendDialogInfo != null) {
                                _data.writeInt(1);
                                suspendDialogInfo.writeToParcel(_data, 0);
                            } else {
                                _data.writeInt(0);
                            }
                            _data.writeString(callingPackage);
                            _data.writeInt(userId);
                            if (this.mRemote.transact(81, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                _reply.readException();
                                String[] _result = _reply.createStringArray();
                                _reply.recycle();
                                _data.recycle();
                                return _result;
                            }
                            String[] packagesSuspendedAsUser = Stub.getDefaultImpl().setPackagesSuspendedAsUser(packageNames, suspended, appExtras, launcherExtras, dialogInfo, callingPackage, userId);
                            _reply.recycle();
                            _data.recycle();
                            return packagesSuspendedAsUser;
                        } catch (Throwable th) {
                            th = th;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        boolean z = suspended;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    String[] strArr = packageNames;
                    boolean z2 = suspended;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public String[] getUnsuspendablePackagesForUser(String[] packageNames, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStringArray(packageNames);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(82, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getUnsuspendablePackagesForUser(packageNames, userId);
                    }
                    _reply.readException();
                    String[] _result = _reply.createStringArray();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isPackageSuspendedForUser(String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    boolean z = false;
                    if (!this.mRemote.transact(83, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isPackageSuspendedForUser(packageName, userId);
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

            public PersistableBundle getSuspendedPackageAppExtras(String packageName, int userId) throws RemoteException {
                PersistableBundle _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(84, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getSuspendedPackageAppExtras(packageName, userId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = PersistableBundle.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    PersistableBundle _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public byte[] getPreferredActivityBackup(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(85, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPreferredActivityBackup(userId);
                    }
                    _reply.readException();
                    byte[] _result = _reply.createByteArray();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void restorePreferredActivities(byte[] backup, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByteArray(backup);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(86, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().restorePreferredActivities(backup, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public byte[] getDefaultAppsBackup(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(87, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getDefaultAppsBackup(userId);
                    }
                    _reply.readException();
                    byte[] _result = _reply.createByteArray();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void restoreDefaultApps(byte[] backup, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByteArray(backup);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(88, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().restoreDefaultApps(backup, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public byte[] getIntentFilterVerificationBackup(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(89, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getIntentFilterVerificationBackup(userId);
                    }
                    _reply.readException();
                    byte[] _result = _reply.createByteArray();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void restoreIntentFilterVerification(byte[] backup, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByteArray(backup);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(90, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().restoreIntentFilterVerification(backup, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public ComponentName getHomeActivities(List<ResolveInfo> outHomeCandidates) throws RemoteException {
                ComponentName _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(91, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getHomeActivities(outHomeCandidates);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = ComponentName.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    _reply.readTypedList(outHomeCandidates, ResolveInfo.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setHomeActivity(ComponentName className, int userId) throws RemoteException {
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
                    _data.writeInt(userId);
                    if (this.mRemote.transact(92, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setHomeActivity(className, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setComponentEnabledSetting(ComponentName componentName, int newState, int flags, int userId) throws RemoteException {
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
                    _data.writeInt(newState);
                    _data.writeInt(flags);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(93, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setComponentEnabledSetting(componentName, newState, flags, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getComponentEnabledSetting(ComponentName componentName, int userId) throws RemoteException {
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
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(94, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getComponentEnabledSetting(componentName, userId);
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

            public void setApplicationEnabledSetting(String packageName, int newState, int flags, int userId, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(newState);
                    _data.writeInt(flags);
                    _data.writeInt(userId);
                    _data.writeString(callingPackage);
                    if (this.mRemote.transact(95, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setApplicationEnabledSetting(packageName, newState, flags, userId, callingPackage);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getApplicationEnabledSetting(String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(96, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getApplicationEnabledSetting(packageName, userId);
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

            public void logAppProcessStartIfNeeded(String processName, int uid, String seinfo, String apkFile, int pid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(processName);
                    _data.writeInt(uid);
                    _data.writeString(seinfo);
                    _data.writeString(apkFile);
                    _data.writeInt(pid);
                    if (this.mRemote.transact(97, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().logAppProcessStartIfNeeded(processName, uid, seinfo, apkFile, pid);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void flushPackageRestrictionsAsUser(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(98, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().flushPackageRestrictionsAsUser(userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setPackageStoppedState(String packageName, boolean stopped, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(stopped);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(99, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setPackageStoppedState(packageName, stopped, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void freeStorageAndNotify(String volumeUuid, long freeStorageSize, int storageFlags, IPackageDataObserver observer) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(volumeUuid);
                    _data.writeLong(freeStorageSize);
                    _data.writeInt(storageFlags);
                    _data.writeStrongBinder(observer != null ? observer.asBinder() : null);
                    if (this.mRemote.transact(100, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().freeStorageAndNotify(volumeUuid, freeStorageSize, storageFlags, observer);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void freeStorage(String volumeUuid, long freeStorageSize, int storageFlags, IntentSender pi) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(volumeUuid);
                    _data.writeLong(freeStorageSize);
                    _data.writeInt(storageFlags);
                    if (pi != null) {
                        _data.writeInt(1);
                        pi.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(101, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().freeStorage(volumeUuid, freeStorageSize, storageFlags, pi);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void deleteApplicationCacheFiles(String packageName, IPackageDataObserver observer) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeStrongBinder(observer != null ? observer.asBinder() : null);
                    if (this.mRemote.transact(102, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().deleteApplicationCacheFiles(packageName, observer);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void deleteApplicationCacheFilesAsUser(String packageName, int userId, IPackageDataObserver observer) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    _data.writeStrongBinder(observer != null ? observer.asBinder() : null);
                    if (this.mRemote.transact(103, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().deleteApplicationCacheFilesAsUser(packageName, userId, observer);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void clearApplicationUserData(String packageName, IPackageDataObserver observer, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeStrongBinder(observer != null ? observer.asBinder() : null);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(104, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().clearApplicationUserData(packageName, observer, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void clearApplicationProfileData(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    if (this.mRemote.transact(105, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().clearApplicationProfileData(packageName);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void getPackageSizeInfo(String packageName, int userHandle, IPackageStatsObserver observer) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userHandle);
                    _data.writeStrongBinder(observer != null ? observer.asBinder() : null);
                    if (this.mRemote.transact(106, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().getPackageSizeInfo(packageName, userHandle, observer);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String[] getSystemSharedLibraryNames() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(107, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getSystemSharedLibraryNames();
                    }
                    _reply.readException();
                    String[] _result = _reply.createStringArray();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public ParceledListSlice getSystemAvailableFeatures() throws RemoteException {
                ParceledListSlice _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(108, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getSystemAvailableFeatures();
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

            public boolean hasSystemFeature(String name, int version) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    _data.writeInt(version);
                    boolean z = false;
                    if (!this.mRemote.transact(109, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().hasSystemFeature(name, version);
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

            public void enterSafeMode() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(110, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
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

            public boolean isSafeMode() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(111, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isSafeMode();
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

            public void systemReady() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(112, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().systemReady();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean hasSystemUidErrors() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(113, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().hasSystemUidErrors();
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

            public void performFstrimIfNeeded() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(114, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().performFstrimIfNeeded();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void updatePackagesIfNeeded() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(115, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().updatePackagesIfNeeded();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void notifyPackageUse(String packageName, int reason) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(reason);
                    if (this.mRemote.transact(116, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().notifyPackageUse(packageName, reason);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void notifyDexLoad(String loadingPackageName, List<String> classLoadersNames, List<String> classPaths, String loaderIsa) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(loadingPackageName);
                    _data.writeStringList(classLoadersNames);
                    _data.writeStringList(classPaths);
                    _data.writeString(loaderIsa);
                    if (this.mRemote.transact(117, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().notifyDexLoad(loadingPackageName, classLoadersNames, classPaths, loaderIsa);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void registerDexModule(String packageName, String dexModulePath, boolean isSharedModule, IDexModuleRegisterCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeString(dexModulePath);
                    _data.writeInt(isSharedModule);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(118, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().registerDexModule(packageName, dexModulePath, isSharedModule, callback);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public boolean performDexOptMode(String packageName, boolean checkProfiles, String targetCompilerFilter, boolean force, boolean bootComplete, String splitName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeString(packageName);
                        try {
                            _data.writeInt(checkProfiles ? 1 : 0);
                            try {
                                _data.writeString(targetCompilerFilter);
                                try {
                                    _data.writeInt(force ? 1 : 0);
                                } catch (Throwable th) {
                                    th = th;
                                    boolean z = bootComplete;
                                    String str = splitName;
                                    _reply.recycle();
                                    _data.recycle();
                                    throw th;
                                }
                            } catch (Throwable th2) {
                                th = th2;
                                boolean z2 = force;
                                boolean z3 = bootComplete;
                                String str2 = splitName;
                                _reply.recycle();
                                _data.recycle();
                                throw th;
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            String str3 = targetCompilerFilter;
                            boolean z22 = force;
                            boolean z32 = bootComplete;
                            String str22 = splitName;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                        try {
                            _data.writeInt(bootComplete ? 1 : 0);
                            try {
                                _data.writeString(splitName);
                                boolean z4 = false;
                                if (this.mRemote.transact(119, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                    _reply.readException();
                                    if (_reply.readInt() != 0) {
                                        z4 = true;
                                    }
                                    boolean _result = z4;
                                    _reply.recycle();
                                    _data.recycle();
                                    return _result;
                                }
                                boolean performDexOptMode = Stub.getDefaultImpl().performDexOptMode(packageName, checkProfiles, targetCompilerFilter, force, bootComplete, splitName);
                                _reply.recycle();
                                _data.recycle();
                                return performDexOptMode;
                            } catch (Throwable th4) {
                                th = th4;
                                _reply.recycle();
                                _data.recycle();
                                throw th;
                            }
                        } catch (Throwable th5) {
                            th = th5;
                            String str222 = splitName;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th6) {
                        th = th6;
                        boolean z5 = checkProfiles;
                        String str32 = targetCompilerFilter;
                        boolean z222 = force;
                        boolean z322 = bootComplete;
                        String str2222 = splitName;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th7) {
                    th = th7;
                    String str4 = packageName;
                    boolean z52 = checkProfiles;
                    String str322 = targetCompilerFilter;
                    boolean z2222 = force;
                    boolean z3222 = bootComplete;
                    String str22222 = splitName;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public boolean performDexOptSecondary(String packageName, String targetCompilerFilter, boolean force) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeString(targetCompilerFilter);
                    _data.writeInt(force);
                    boolean z = false;
                    if (!this.mRemote.transact(120, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().performDexOptSecondary(packageName, targetCompilerFilter, force);
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

            public boolean compileLayouts(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    boolean z = false;
                    if (!this.mRemote.transact(121, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().compileLayouts(packageName);
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

            public void dumpProfiles(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    if (this.mRemote.transact(122, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().dumpProfiles(packageName);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void forceDexOpt(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    if (this.mRemote.transact(123, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().forceDexOpt(packageName);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean runBackgroundDexoptJob(List<String> packageNames) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStringList(packageNames);
                    boolean z = false;
                    if (!this.mRemote.transact(124, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().runBackgroundDexoptJob(packageNames);
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

            public void reconcileSecondaryDexFiles(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    if (this.mRemote.transact(125, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().reconcileSecondaryDexFiles(packageName);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getMoveStatus(int moveId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(moveId);
                    if (!this.mRemote.transact(126, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getMoveStatus(moveId);
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

            public void registerMoveCallback(IPackageMoveObserver callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(127, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().registerMoveCallback(callback);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void unregisterMoveCallback(IPackageMoveObserver callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(128, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().unregisterMoveCallback(callback);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int movePackage(String packageName, String volumeUuid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeString(volumeUuid);
                    if (!this.mRemote.transact(129, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().movePackage(packageName, volumeUuid);
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

            public int movePrimaryStorage(String volumeUuid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(volumeUuid);
                    if (!this.mRemote.transact(130, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().movePrimaryStorage(volumeUuid);
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

            public boolean addPermissionAsync(PermissionInfo info) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (info != null) {
                        _data.writeInt(1);
                        info.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(131, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().addPermissionAsync(info);
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

            public boolean setInstallLocation(int loc) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(loc);
                    boolean z = false;
                    if (!this.mRemote.transact(132, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setInstallLocation(loc);
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

            public int getInstallLocation() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(133, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getInstallLocation();
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

            public int installExistingPackageAsUser(String packageName, int userId, int installFlags, int installReason, List<String> whiteListedPermissions) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    _data.writeInt(installFlags);
                    _data.writeInt(installReason);
                    _data.writeStringList(whiteListedPermissions);
                    if (!this.mRemote.transact(134, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().installExistingPackageAsUser(packageName, userId, installFlags, installReason, whiteListedPermissions);
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

            public void verifyPendingInstall(int id, int verificationCode) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(id);
                    _data.writeInt(verificationCode);
                    if (this.mRemote.transact(135, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().verifyPendingInstall(id, verificationCode);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void extendVerificationTimeout(int id, int verificationCodeAtTimeout, long millisecondsToDelay) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(id);
                    _data.writeInt(verificationCodeAtTimeout);
                    _data.writeLong(millisecondsToDelay);
                    if (this.mRemote.transact(136, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().extendVerificationTimeout(id, verificationCodeAtTimeout, millisecondsToDelay);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void verifyIntentFilter(int id, int verificationCode, List<String> failedDomains) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(id);
                    _data.writeInt(verificationCode);
                    _data.writeStringList(failedDomains);
                    if (this.mRemote.transact(137, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().verifyIntentFilter(id, verificationCode, failedDomains);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getIntentVerificationStatus(String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(138, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getIntentVerificationStatus(packageName, userId);
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

            public boolean updateIntentVerificationStatus(String packageName, int status, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(status);
                    _data.writeInt(userId);
                    boolean z = false;
                    if (!this.mRemote.transact(139, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().updateIntentVerificationStatus(packageName, status, userId);
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

            public ParceledListSlice getIntentFilterVerifications(String packageName) throws RemoteException {
                ParceledListSlice _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    if (!this.mRemote.transact(140, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getIntentFilterVerifications(packageName);
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

            public ParceledListSlice getAllIntentFilters(String packageName) throws RemoteException {
                ParceledListSlice _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    if (!this.mRemote.transact(141, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAllIntentFilters(packageName);
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

            public boolean setDefaultBrowserPackageName(String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    boolean z = false;
                    if (!this.mRemote.transact(142, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setDefaultBrowserPackageName(packageName, userId);
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

            public String getDefaultBrowserPackageName(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(143, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getDefaultBrowserPackageName(userId);
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

            public VerifierDeviceIdentity getVerifierDeviceIdentity() throws RemoteException {
                VerifierDeviceIdentity _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(144, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getVerifierDeviceIdentity();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = VerifierDeviceIdentity.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    VerifierDeviceIdentity _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isFirstBoot() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(145, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isFirstBoot();
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

            public boolean isOnlyCoreApps() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(146, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isOnlyCoreApps();
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

            public boolean isDeviceUpgrading() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(147, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isDeviceUpgrading();
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

            public void setPermissionEnforced(String permission, boolean enforced) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(permission);
                    _data.writeInt(enforced);
                    if (this.mRemote.transact(148, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setPermissionEnforced(permission, enforced);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isPermissionEnforced(String permission) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(permission);
                    boolean z = false;
                    if (!this.mRemote.transact(149, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isPermissionEnforced(permission);
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

            public boolean isStorageLow() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(150, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isStorageLow();
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

            public boolean setApplicationHiddenSettingAsUser(String packageName, boolean hidden, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(hidden);
                    _data.writeInt(userId);
                    boolean z = false;
                    if (!this.mRemote.transact(151, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setApplicationHiddenSettingAsUser(packageName, hidden, userId);
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

            public boolean getApplicationHiddenSettingAsUser(String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    boolean z = false;
                    if (!this.mRemote.transact(152, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getApplicationHiddenSettingAsUser(packageName, userId);
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

            public void setSystemAppHiddenUntilInstalled(String packageName, boolean hidden) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(hidden);
                    if (this.mRemote.transact(153, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setSystemAppHiddenUntilInstalled(packageName, hidden);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean setSystemAppInstallState(String packageName, boolean installed, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(installed);
                    _data.writeInt(userId);
                    boolean z = false;
                    if (!this.mRemote.transact(154, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setSystemAppInstallState(packageName, installed, userId);
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

            public IPackageInstaller getPackageInstaller() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(155, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPackageInstaller();
                    }
                    _reply.readException();
                    IPackageInstaller _result = IPackageInstaller.Stub.asInterface(_reply.readStrongBinder());
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean setBlockUninstallForUser(String packageName, boolean blockUninstall, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(blockUninstall);
                    _data.writeInt(userId);
                    boolean z = false;
                    if (!this.mRemote.transact(156, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setBlockUninstallForUser(packageName, blockUninstall, userId);
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

            public boolean getBlockUninstallForUser(String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    boolean z = false;
                    if (!this.mRemote.transact(157, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getBlockUninstallForUser(packageName, userId);
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

            public KeySet getKeySetByAlias(String packageName, String alias) throws RemoteException {
                KeySet _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeString(alias);
                    if (!this.mRemote.transact(158, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getKeySetByAlias(packageName, alias);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = KeySet.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    KeySet _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public KeySet getSigningKeySet(String packageName) throws RemoteException {
                KeySet _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    if (!this.mRemote.transact(159, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getSigningKeySet(packageName);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = KeySet.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    KeySet _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isPackageSignedByKeySet(String packageName, KeySet ks) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    boolean _result = true;
                    if (ks != null) {
                        _data.writeInt(1);
                        ks.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(160, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isPackageSignedByKeySet(packageName, ks);
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

            public boolean isPackageSignedByKeySetExactly(String packageName, KeySet ks) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    boolean _result = true;
                    if (ks != null) {
                        _data.writeInt(1);
                        ks.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(161, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isPackageSignedByKeySetExactly(packageName, ks);
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

            public void addOnPermissionsChangeListener(IOnPermissionsChangeListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    if (this.mRemote.transact(162, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().addOnPermissionsChangeListener(listener);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void removeOnPermissionsChangeListener(IOnPermissionsChangeListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    if (this.mRemote.transact(163, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().removeOnPermissionsChangeListener(listener);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void grantDefaultPermissionsToEnabledCarrierApps(String[] packageNames, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStringArray(packageNames);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(164, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().grantDefaultPermissionsToEnabledCarrierApps(packageNames, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void grantDefaultPermissionsToEnabledImsServices(String[] packageNames, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStringArray(packageNames);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(165, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().grantDefaultPermissionsToEnabledImsServices(packageNames, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void grantDefaultPermissionsToEnabledTelephonyDataServices(String[] packageNames, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStringArray(packageNames);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(166, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().grantDefaultPermissionsToEnabledTelephonyDataServices(packageNames, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void revokeDefaultPermissionsFromDisabledTelephonyDataServices(String[] packageNames, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStringArray(packageNames);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(167, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().revokeDefaultPermissionsFromDisabledTelephonyDataServices(packageNames, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void grantDefaultPermissionsToActiveLuiApp(String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(168, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().grantDefaultPermissionsToActiveLuiApp(packageName, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void revokeDefaultPermissionsFromLuiApps(String[] packageNames, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStringArray(packageNames);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(169, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().revokeDefaultPermissionsFromLuiApps(packageNames, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isPermissionRevokedByPolicy(String permission, String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(permission);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    boolean z = false;
                    if (!this.mRemote.transact(170, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isPermissionRevokedByPolicy(permission, packageName, userId);
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

            public String getPermissionControllerPackageName() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(171, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPermissionControllerPackageName();
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

            public ParceledListSlice getInstantApps(int userId) throws RemoteException {
                ParceledListSlice _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(172, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getInstantApps(userId);
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

            public byte[] getInstantAppCookie(String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(173, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getInstantAppCookie(packageName, userId);
                    }
                    _reply.readException();
                    byte[] _result = _reply.createByteArray();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean setInstantAppCookie(String packageName, byte[] cookie, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeByteArray(cookie);
                    _data.writeInt(userId);
                    boolean z = false;
                    if (!this.mRemote.transact(174, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setInstantAppCookie(packageName, cookie, userId);
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

            public Bitmap getInstantAppIcon(String packageName, int userId) throws RemoteException {
                Bitmap _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(175, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getInstantAppIcon(packageName, userId);
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

            public boolean isInstantApp(String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    boolean z = false;
                    if (!this.mRemote.transact(176, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isInstantApp(packageName, userId);
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

            public boolean setRequiredForSystemUser(String packageName, boolean systemUserApp) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(systemUserApp);
                    boolean z = false;
                    if (!this.mRemote.transact(177, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setRequiredForSystemUser(packageName, systemUserApp);
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

            public void setUpdateAvailable(String packageName, boolean updateAvaialble) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(updateAvaialble);
                    if (this.mRemote.transact(178, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setUpdateAvailable(packageName, updateAvaialble);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String getServicesSystemSharedLibraryPackageName() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(179, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getServicesSystemSharedLibraryPackageName();
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

            public String getSharedSystemSharedLibraryPackageName() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(180, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getSharedSystemSharedLibraryPackageName();
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

            public ChangedPackages getChangedPackages(int sequenceNumber, int userId) throws RemoteException {
                ChangedPackages _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sequenceNumber);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(181, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getChangedPackages(sequenceNumber, userId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = ChangedPackages.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    ChangedPackages _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isPackageDeviceAdminOnAnyUser(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    boolean z = false;
                    if (!this.mRemote.transact(182, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isPackageDeviceAdminOnAnyUser(packageName);
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

            public int getInstallReason(String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(183, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getInstallReason(packageName, userId);
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

            public ParceledListSlice getSharedLibraries(String packageName, int flags, int userId) throws RemoteException {
                ParceledListSlice _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(flags);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(184, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getSharedLibraries(packageName, flags, userId);
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

            public ParceledListSlice getDeclaredSharedLibraries(String packageName, int flags, int userId) throws RemoteException {
                ParceledListSlice _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(flags);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(185, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getDeclaredSharedLibraries(packageName, flags, userId);
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

            public boolean canRequestPackageInstalls(String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    boolean z = false;
                    if (!this.mRemote.transact(186, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().canRequestPackageInstalls(packageName, userId);
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

            public void deletePreloadsFileCache() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(187, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().deletePreloadsFileCache();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public ComponentName getInstantAppResolverComponent() throws RemoteException {
                ComponentName _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(188, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getInstantAppResolverComponent();
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

            public ComponentName getInstantAppResolverSettingsComponent() throws RemoteException {
                ComponentName _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(189, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getInstantAppResolverSettingsComponent();
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

            public ComponentName getInstantAppInstallerComponent() throws RemoteException {
                ComponentName _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(190, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getInstantAppInstallerComponent();
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

            public String getInstantAppAndroidId(String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(191, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getInstantAppAndroidId(packageName, userId);
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

            public IArtManager getArtManager() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(192, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getArtManager();
                    }
                    _reply.readException();
                    IArtManager _result = IArtManager.Stub.asInterface(_reply.readStrongBinder());
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setHarmfulAppWarning(String packageName, CharSequence warning, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    if (warning != null) {
                        _data.writeInt(1);
                        TextUtils.writeToParcel(warning, _data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(userId);
                    if (this.mRemote.transact(193, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setHarmfulAppWarning(packageName, warning, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public CharSequence getHarmfulAppWarning(String packageName, int userId) throws RemoteException {
                CharSequence _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(194, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getHarmfulAppWarning(packageName, userId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    CharSequence _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean hasSigningCertificate(String packageName, byte[] signingCertificate, int flags) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeByteArray(signingCertificate);
                    _data.writeInt(flags);
                    boolean z = false;
                    if (!this.mRemote.transact(195, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().hasSigningCertificate(packageName, signingCertificate, flags);
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

            public boolean hasUidSigningCertificate(int uid, byte[] signingCertificate, int flags) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeByteArray(signingCertificate);
                    _data.writeInt(flags);
                    boolean z = false;
                    if (!this.mRemote.transact(196, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().hasUidSigningCertificate(uid, signingCertificate, flags);
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

            public String getSystemTextClassifierPackageName() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(197, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getSystemTextClassifierPackageName();
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

            public String getAttentionServicePackageName() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(198, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAttentionServicePackageName();
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

            public String getWellbeingPackageName() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(199, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getWellbeingPackageName();
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

            public String getAppPredictionServicePackageName() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(200, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAppPredictionServicePackageName();
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

            public String getSystemCaptionsServicePackageName() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(201, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getSystemCaptionsServicePackageName();
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

            public String getIncidentReportApproverPackageName() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(202, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getIncidentReportApproverPackageName();
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

            public boolean isPackageStateProtected(String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    boolean z = false;
                    if (!this.mRemote.transact(203, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isPackageStateProtected(packageName, userId);
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

            public void sendDeviceCustomizationReadyBroadcast() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(204, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().sendDeviceCustomizationReadyBroadcast();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public List<ModuleInfo> getInstalledModules(int flags) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(flags);
                    if (!this.mRemote.transact(205, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getInstalledModules(flags);
                    }
                    _reply.readException();
                    List<ModuleInfo> _result = _reply.createTypedArrayList(ModuleInfo.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public ModuleInfo getModuleInfo(String packageName, int flags) throws RemoteException {
                ModuleInfo _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(flags);
                    if (!this.mRemote.transact(206, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getModuleInfo(packageName, flags);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = ModuleInfo.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    ModuleInfo _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getRuntimePermissionsVersion(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(207, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getRuntimePermissionsVersion(userId);
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

            public void setRuntimePermissionsVersion(int version, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(version);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(208, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setRuntimePermissionsVersion(version, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void notifyPackagesReplacedReceived(String[] packages) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStringArray(packages);
                    if (this.mRemote.transact(209, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().notifyPackagesReplacedReceived(packages);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IPackageManager impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IPackageManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
