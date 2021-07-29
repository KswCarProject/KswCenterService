package android.app.admin;

import android.annotation.UnsupportedAppUsage;
import android.app.IApplicationThread;
import android.app.IServiceConnection;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.IPackageDataObserver;
import android.content.pm.ParceledListSlice;
import android.content.pm.StringParceledListSlice;
import android.graphics.Bitmap;
import android.net.ProxyInfo;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.PersistableBundle;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.os.UserHandle;
import android.security.keymaster.KeymasterCertificateChain;
import android.security.keystore.ParcelableKeyGenParameterSpec;
import android.telephony.data.ApnSetting;
import android.text.TextUtils;
import java.util.List;

public interface IDevicePolicyManager extends IInterface {
    void addCrossProfileIntentFilter(ComponentName componentName, IntentFilter intentFilter, int i) throws RemoteException;

    boolean addCrossProfileWidgetProvider(ComponentName componentName, String str) throws RemoteException;

    int addOverrideApn(ComponentName componentName, ApnSetting apnSetting) throws RemoteException;

    void addPersistentPreferredActivity(ComponentName componentName, IntentFilter intentFilter, ComponentName componentName2) throws RemoteException;

    boolean approveCaCert(String str, int i, boolean z) throws RemoteException;

    boolean bindDeviceAdminServiceAsUser(ComponentName componentName, IApplicationThread iApplicationThread, IBinder iBinder, Intent intent, IServiceConnection iServiceConnection, int i, int i2) throws RemoteException;

    boolean checkDeviceIdentifierAccess(String str, int i, int i2) throws RemoteException;

    int checkProvisioningPreCondition(String str, String str2) throws RemoteException;

    void choosePrivateKeyAlias(int i, Uri uri, String str, IBinder iBinder) throws RemoteException;

    void clearApplicationUserData(ComponentName componentName, String str, IPackageDataObserver iPackageDataObserver) throws RemoteException;

    void clearCrossProfileIntentFilters(ComponentName componentName) throws RemoteException;

    void clearDeviceOwner(String str) throws RemoteException;

    void clearPackagePersistentPreferredActivities(ComponentName componentName, String str) throws RemoteException;

    void clearProfileOwner(ComponentName componentName) throws RemoteException;

    boolean clearResetPasswordToken(ComponentName componentName) throws RemoteException;

    void clearSystemUpdatePolicyFreezePeriodRecord() throws RemoteException;

    Intent createAdminSupportIntent(String str) throws RemoteException;

    UserHandle createAndManageUser(ComponentName componentName, String str, ComponentName componentName2, PersistableBundle persistableBundle, int i) throws RemoteException;

    void enableSystemApp(ComponentName componentName, String str, String str2) throws RemoteException;

    int enableSystemAppWithIntent(ComponentName componentName, String str, Intent intent) throws RemoteException;

    void enforceCanManageCaCerts(ComponentName componentName, String str) throws RemoteException;

    long forceNetworkLogs() throws RemoteException;

    void forceRemoveActiveAdmin(ComponentName componentName, int i) throws RemoteException;

    long forceSecurityLogs() throws RemoteException;

    void forceUpdateUserSetupComplete() throws RemoteException;

    boolean generateKeyPair(ComponentName componentName, String str, String str2, ParcelableKeyGenParameterSpec parcelableKeyGenParameterSpec, int i, KeymasterCertificateChain keymasterCertificateChain) throws RemoteException;

    String[] getAccountTypesWithManagementDisabled() throws RemoteException;

    String[] getAccountTypesWithManagementDisabledAsUser(int i) throws RemoteException;

    List<ComponentName> getActiveAdmins(int i) throws RemoteException;

    List<String> getAffiliationIds(ComponentName componentName) throws RemoteException;

    List<String> getAlwaysOnVpnLockdownWhitelist(ComponentName componentName) throws RemoteException;

    String getAlwaysOnVpnPackage(ComponentName componentName) throws RemoteException;

    Bundle getApplicationRestrictions(ComponentName componentName, String str, String str2) throws RemoteException;

    String getApplicationRestrictionsManagingPackage(ComponentName componentName) throws RemoteException;

    boolean getAutoTimeRequired() throws RemoteException;

    List<UserHandle> getBindDeviceAdminTargetUsers(ComponentName componentName) throws RemoteException;

    boolean getBluetoothContactSharingDisabled(ComponentName componentName) throws RemoteException;

    boolean getBluetoothContactSharingDisabledForUser(int i) throws RemoteException;

    boolean getCameraDisabled(ComponentName componentName, int i) throws RemoteException;

    String getCertInstallerPackage(ComponentName componentName) throws RemoteException;

    List<String> getCrossProfileCalendarPackages(ComponentName componentName) throws RemoteException;

    List<String> getCrossProfileCalendarPackagesForUser(int i) throws RemoteException;

    boolean getCrossProfileCallerIdDisabled(ComponentName componentName) throws RemoteException;

    boolean getCrossProfileCallerIdDisabledForUser(int i) throws RemoteException;

    boolean getCrossProfileContactsSearchDisabled(ComponentName componentName) throws RemoteException;

    boolean getCrossProfileContactsSearchDisabledForUser(int i) throws RemoteException;

    List<String> getCrossProfileWidgetProviders(ComponentName componentName) throws RemoteException;

    int getCurrentFailedPasswordAttempts(int i, boolean z) throws RemoteException;

    List<String> getDelegatePackages(ComponentName componentName, String str) throws RemoteException;

    List<String> getDelegatedScopes(ComponentName componentName, String str) throws RemoteException;

    ComponentName getDeviceOwnerComponent(boolean z) throws RemoteException;

    CharSequence getDeviceOwnerLockScreenInfo() throws RemoteException;

    String getDeviceOwnerName() throws RemoteException;

    CharSequence getDeviceOwnerOrganizationName() throws RemoteException;

    int getDeviceOwnerUserId() throws RemoteException;

    List<String> getDisallowedSystemApps(ComponentName componentName, int i, String str) throws RemoteException;

    boolean getDoNotAskCredentialsOnBoot() throws RemoteException;

    CharSequence getEndUserSessionMessage(ComponentName componentName) throws RemoteException;

    boolean getForceEphemeralUsers(ComponentName componentName) throws RemoteException;

    String getGlobalPrivateDnsHost(ComponentName componentName) throws RemoteException;

    int getGlobalPrivateDnsMode(ComponentName componentName) throws RemoteException;

    ComponentName getGlobalProxyAdmin(int i) throws RemoteException;

    List<String> getKeepUninstalledPackages(ComponentName componentName, String str) throws RemoteException;

    int getKeyguardDisabledFeatures(ComponentName componentName, int i, boolean z) throws RemoteException;

    long getLastBugReportRequestTime() throws RemoteException;

    long getLastNetworkLogRetrievalTime() throws RemoteException;

    long getLastSecurityLogRetrievalTime() throws RemoteException;

    int getLockTaskFeatures(ComponentName componentName) throws RemoteException;

    String[] getLockTaskPackages(ComponentName componentName) throws RemoteException;

    CharSequence getLongSupportMessage(ComponentName componentName) throws RemoteException;

    CharSequence getLongSupportMessageForUser(ComponentName componentName, int i) throws RemoteException;

    int getMaximumFailedPasswordsForWipe(ComponentName componentName, int i, boolean z) throws RemoteException;

    long getMaximumTimeToLock(ComponentName componentName, int i, boolean z) throws RemoteException;

    List<String> getMeteredDataDisabledPackages(ComponentName componentName) throws RemoteException;

    int getOrganizationColor(ComponentName componentName) throws RemoteException;

    int getOrganizationColorForUser(int i) throws RemoteException;

    CharSequence getOrganizationName(ComponentName componentName) throws RemoteException;

    CharSequence getOrganizationNameForUser(int i) throws RemoteException;

    List<ApnSetting> getOverrideApns(ComponentName componentName) throws RemoteException;

    StringParceledListSlice getOwnerInstalledCaCerts(UserHandle userHandle) throws RemoteException;

    int getPasswordComplexity() throws RemoteException;

    long getPasswordExpiration(ComponentName componentName, int i, boolean z) throws RemoteException;

    long getPasswordExpirationTimeout(ComponentName componentName, int i, boolean z) throws RemoteException;

    int getPasswordHistoryLength(ComponentName componentName, int i, boolean z) throws RemoteException;

    int getPasswordMinimumLength(ComponentName componentName, int i, boolean z) throws RemoteException;

    int getPasswordMinimumLetters(ComponentName componentName, int i, boolean z) throws RemoteException;

    int getPasswordMinimumLowerCase(ComponentName componentName, int i, boolean z) throws RemoteException;

    int getPasswordMinimumNonLetter(ComponentName componentName, int i, boolean z) throws RemoteException;

    int getPasswordMinimumNumeric(ComponentName componentName, int i, boolean z) throws RemoteException;

    int getPasswordMinimumSymbols(ComponentName componentName, int i, boolean z) throws RemoteException;

    int getPasswordMinimumUpperCase(ComponentName componentName, int i, boolean z) throws RemoteException;

    int getPasswordQuality(ComponentName componentName, int i, boolean z) throws RemoteException;

    SystemUpdateInfo getPendingSystemUpdate(ComponentName componentName) throws RemoteException;

    int getPermissionGrantState(ComponentName componentName, String str, String str2, String str3) throws RemoteException;

    int getPermissionPolicy(ComponentName componentName) throws RemoteException;

    List getPermittedAccessibilityServices(ComponentName componentName) throws RemoteException;

    List getPermittedAccessibilityServicesForUser(int i) throws RemoteException;

    List<String> getPermittedCrossProfileNotificationListeners(ComponentName componentName) throws RemoteException;

    List getPermittedInputMethods(ComponentName componentName) throws RemoteException;

    List getPermittedInputMethodsForCurrentUser() throws RemoteException;

    ComponentName getProfileOwner(int i) throws RemoteException;

    ComponentName getProfileOwnerAsUser(int i) throws RemoteException;

    String getProfileOwnerName(int i) throws RemoteException;

    int getProfileWithMinimumFailedPasswordsForWipe(int i, boolean z) throws RemoteException;

    void getRemoveWarning(ComponentName componentName, RemoteCallback remoteCallback, int i) throws RemoteException;

    long getRequiredStrongAuthTimeout(ComponentName componentName, int i, boolean z) throws RemoteException;

    ComponentName getRestrictionsProvider(int i) throws RemoteException;

    boolean getScreenCaptureDisabled(ComponentName componentName, int i) throws RemoteException;

    List<UserHandle> getSecondaryUsers(ComponentName componentName) throws RemoteException;

    CharSequence getShortSupportMessage(ComponentName componentName) throws RemoteException;

    CharSequence getShortSupportMessageForUser(ComponentName componentName, int i) throws RemoteException;

    CharSequence getStartUserSessionMessage(ComponentName componentName) throws RemoteException;

    boolean getStorageEncryption(ComponentName componentName, int i) throws RemoteException;

    int getStorageEncryptionStatus(String str, int i) throws RemoteException;

    SystemUpdatePolicy getSystemUpdatePolicy() throws RemoteException;

    PersistableBundle getTransferOwnershipBundle() throws RemoteException;

    List<PersistableBundle> getTrustAgentConfiguration(ComponentName componentName, ComponentName componentName2, int i, boolean z) throws RemoteException;

    int getUserProvisioningState() throws RemoteException;

    Bundle getUserRestrictions(ComponentName componentName) throws RemoteException;

    String getWifiMacAddress(ComponentName componentName) throws RemoteException;

    void grantDeviceIdsAccessToProfileOwner(ComponentName componentName, int i) throws RemoteException;

    boolean hasDeviceOwner() throws RemoteException;

    boolean hasGrantedPolicy(ComponentName componentName, int i, int i2) throws RemoteException;

    boolean hasUserSetupCompleted() throws RemoteException;

    boolean installCaCert(ComponentName componentName, String str, byte[] bArr) throws RemoteException;

    boolean installExistingPackage(ComponentName componentName, String str, String str2) throws RemoteException;

    boolean installKeyPair(ComponentName componentName, String str, byte[] bArr, byte[] bArr2, byte[] bArr3, String str2, boolean z, boolean z2) throws RemoteException;

    void installUpdateFromFile(ComponentName componentName, ParcelFileDescriptor parcelFileDescriptor, StartInstallingUpdateCallback startInstallingUpdateCallback) throws RemoteException;

    boolean isAccessibilityServicePermittedByAdmin(ComponentName componentName, String str, int i) throws RemoteException;

    boolean isActivePasswordSufficient(int i, boolean z) throws RemoteException;

    boolean isAdminActive(ComponentName componentName, int i) throws RemoteException;

    boolean isAffiliatedUser() throws RemoteException;

    boolean isAlwaysOnVpnLockdownEnabled(ComponentName componentName) throws RemoteException;

    boolean isApplicationHidden(ComponentName componentName, String str, String str2) throws RemoteException;

    boolean isBackupServiceEnabled(ComponentName componentName) throws RemoteException;

    boolean isCaCertApproved(String str, int i) throws RemoteException;

    boolean isCallerApplicationRestrictionsManagingPackage(String str) throws RemoteException;

    boolean isCurrentInputMethodSetByOwner() throws RemoteException;

    boolean isDeviceProvisioned() throws RemoteException;

    boolean isDeviceProvisioningConfigApplied() throws RemoteException;

    boolean isEphemeralUser(ComponentName componentName) throws RemoteException;

    boolean isInputMethodPermittedByAdmin(ComponentName componentName, String str, int i) throws RemoteException;

    boolean isLockTaskPermitted(String str) throws RemoteException;

    boolean isLogoutEnabled() throws RemoteException;

    boolean isManagedKiosk() throws RemoteException;

    boolean isManagedProfile(ComponentName componentName) throws RemoteException;

    boolean isMasterVolumeMuted(ComponentName componentName) throws RemoteException;

    boolean isMeteredDataDisabledPackageForUser(ComponentName componentName, String str, int i) throws RemoteException;

    boolean isNetworkLoggingEnabled(ComponentName componentName, String str) throws RemoteException;

    boolean isNotificationListenerServicePermitted(String str, int i) throws RemoteException;

    boolean isOverrideApnEnabled(ComponentName componentName) throws RemoteException;

    boolean isPackageAllowedToAccessCalendarForUser(String str, int i) throws RemoteException;

    boolean isPackageSuspended(ComponentName componentName, String str, String str2) throws RemoteException;

    boolean isProfileActivePasswordSufficientForParent(int i) throws RemoteException;

    boolean isProvisioningAllowed(String str, String str2) throws RemoteException;

    boolean isRemovingAdmin(ComponentName componentName, int i) throws RemoteException;

    boolean isResetPasswordTokenActive(ComponentName componentName) throws RemoteException;

    boolean isSecurityLoggingEnabled(ComponentName componentName) throws RemoteException;

    boolean isSeparateProfileChallengeAllowed(int i) throws RemoteException;

    boolean isSystemOnlyUser(ComponentName componentName) throws RemoteException;

    boolean isUnattendedManagedKiosk() throws RemoteException;

    boolean isUninstallBlocked(ComponentName componentName, String str) throws RemoteException;

    boolean isUninstallInQueue(String str) throws RemoteException;

    boolean isUsingUnifiedPassword(ComponentName componentName) throws RemoteException;

    void lockNow(int i, boolean z) throws RemoteException;

    int logoutUser(ComponentName componentName) throws RemoteException;

    void notifyLockTaskModeChanged(boolean z, String str, int i) throws RemoteException;

    void notifyPendingSystemUpdate(SystemUpdateInfo systemUpdateInfo) throws RemoteException;

    @UnsupportedAppUsage
    boolean packageHasActiveAdmins(String str, int i) throws RemoteException;

    void reboot(ComponentName componentName) throws RemoteException;

    void removeActiveAdmin(ComponentName componentName, int i) throws RemoteException;

    boolean removeCrossProfileWidgetProvider(ComponentName componentName, String str) throws RemoteException;

    boolean removeKeyPair(ComponentName componentName, String str, String str2) throws RemoteException;

    boolean removeOverrideApn(ComponentName componentName, int i) throws RemoteException;

    boolean removeUser(ComponentName componentName, UserHandle userHandle) throws RemoteException;

    void reportFailedBiometricAttempt(int i) throws RemoteException;

    void reportFailedPasswordAttempt(int i) throws RemoteException;

    void reportKeyguardDismissed(int i) throws RemoteException;

    void reportKeyguardSecured(int i) throws RemoteException;

    void reportPasswordChanged(int i) throws RemoteException;

    void reportSuccessfulBiometricAttempt(int i) throws RemoteException;

    void reportSuccessfulPasswordAttempt(int i) throws RemoteException;

    boolean requestBugreport(ComponentName componentName) throws RemoteException;

    boolean resetPassword(String str, int i) throws RemoteException;

    boolean resetPasswordWithToken(ComponentName componentName, String str, byte[] bArr, int i) throws RemoteException;

    List<NetworkEvent> retrieveNetworkLogs(ComponentName componentName, String str, long j) throws RemoteException;

    ParceledListSlice retrievePreRebootSecurityLogs(ComponentName componentName) throws RemoteException;

    ParceledListSlice retrieveSecurityLogs(ComponentName componentName) throws RemoteException;

    void setAccountManagementDisabled(ComponentName componentName, String str, boolean z) throws RemoteException;

    void setActiveAdmin(ComponentName componentName, boolean z, int i) throws RemoteException;

    void setActivePasswordState(PasswordMetrics passwordMetrics, int i) throws RemoteException;

    void setAffiliationIds(ComponentName componentName, List<String> list) throws RemoteException;

    boolean setAlwaysOnVpnPackage(ComponentName componentName, String str, boolean z, List<String> list) throws RemoteException;

    boolean setApplicationHidden(ComponentName componentName, String str, String str2, boolean z) throws RemoteException;

    void setApplicationRestrictions(ComponentName componentName, String str, String str2, Bundle bundle) throws RemoteException;

    boolean setApplicationRestrictionsManagingPackage(ComponentName componentName, String str) throws RemoteException;

    void setAutoTimeRequired(ComponentName componentName, boolean z) throws RemoteException;

    void setBackupServiceEnabled(ComponentName componentName, boolean z) throws RemoteException;

    void setBluetoothContactSharingDisabled(ComponentName componentName, boolean z) throws RemoteException;

    void setCameraDisabled(ComponentName componentName, boolean z) throws RemoteException;

    void setCertInstallerPackage(ComponentName componentName, String str) throws RemoteException;

    void setCrossProfileCalendarPackages(ComponentName componentName, List<String> list) throws RemoteException;

    void setCrossProfileCallerIdDisabled(ComponentName componentName, boolean z) throws RemoteException;

    void setCrossProfileContactsSearchDisabled(ComponentName componentName, boolean z) throws RemoteException;

    void setDefaultSmsApplication(ComponentName componentName, String str) throws RemoteException;

    void setDelegatedScopes(ComponentName componentName, String str, List<String> list) throws RemoteException;

    boolean setDeviceOwner(ComponentName componentName, String str, int i) throws RemoteException;

    void setDeviceOwnerLockScreenInfo(ComponentName componentName, CharSequence charSequence) throws RemoteException;

    void setDeviceProvisioningConfigApplied() throws RemoteException;

    void setEndUserSessionMessage(ComponentName componentName, CharSequence charSequence) throws RemoteException;

    void setForceEphemeralUsers(ComponentName componentName, boolean z) throws RemoteException;

    int setGlobalPrivateDns(ComponentName componentName, int i, String str) throws RemoteException;

    ComponentName setGlobalProxy(ComponentName componentName, String str, String str2) throws RemoteException;

    void setGlobalSetting(ComponentName componentName, String str, String str2) throws RemoteException;

    void setKeepUninstalledPackages(ComponentName componentName, String str, List<String> list) throws RemoteException;

    boolean setKeyPairCertificate(ComponentName componentName, String str, String str2, byte[] bArr, byte[] bArr2, boolean z) throws RemoteException;

    boolean setKeyguardDisabled(ComponentName componentName, boolean z) throws RemoteException;

    void setKeyguardDisabledFeatures(ComponentName componentName, int i, boolean z) throws RemoteException;

    void setLockTaskFeatures(ComponentName componentName, int i) throws RemoteException;

    void setLockTaskPackages(ComponentName componentName, String[] strArr) throws RemoteException;

    void setLogoutEnabled(ComponentName componentName, boolean z) throws RemoteException;

    void setLongSupportMessage(ComponentName componentName, CharSequence charSequence) throws RemoteException;

    void setMasterVolumeMuted(ComponentName componentName, boolean z) throws RemoteException;

    void setMaximumFailedPasswordsForWipe(ComponentName componentName, int i, boolean z) throws RemoteException;

    void setMaximumTimeToLock(ComponentName componentName, long j, boolean z) throws RemoteException;

    List<String> setMeteredDataDisabledPackages(ComponentName componentName, List<String> list) throws RemoteException;

    void setNetworkLoggingEnabled(ComponentName componentName, String str, boolean z) throws RemoteException;

    void setOrganizationColor(ComponentName componentName, int i) throws RemoteException;

    void setOrganizationColorForUser(int i, int i2) throws RemoteException;

    void setOrganizationName(ComponentName componentName, CharSequence charSequence) throws RemoteException;

    void setOverrideApnsEnabled(ComponentName componentName, boolean z) throws RemoteException;

    String[] setPackagesSuspended(ComponentName componentName, String str, String[] strArr, boolean z) throws RemoteException;

    void setPasswordExpirationTimeout(ComponentName componentName, long j, boolean z) throws RemoteException;

    void setPasswordHistoryLength(ComponentName componentName, int i, boolean z) throws RemoteException;

    void setPasswordMinimumLength(ComponentName componentName, int i, boolean z) throws RemoteException;

    void setPasswordMinimumLetters(ComponentName componentName, int i, boolean z) throws RemoteException;

    void setPasswordMinimumLowerCase(ComponentName componentName, int i, boolean z) throws RemoteException;

    void setPasswordMinimumNonLetter(ComponentName componentName, int i, boolean z) throws RemoteException;

    void setPasswordMinimumNumeric(ComponentName componentName, int i, boolean z) throws RemoteException;

    void setPasswordMinimumSymbols(ComponentName componentName, int i, boolean z) throws RemoteException;

    void setPasswordMinimumUpperCase(ComponentName componentName, int i, boolean z) throws RemoteException;

    void setPasswordQuality(ComponentName componentName, int i, boolean z) throws RemoteException;

    void setPermissionGrantState(ComponentName componentName, String str, String str2, String str3, int i, RemoteCallback remoteCallback) throws RemoteException;

    void setPermissionPolicy(ComponentName componentName, String str, int i) throws RemoteException;

    boolean setPermittedAccessibilityServices(ComponentName componentName, List list) throws RemoteException;

    boolean setPermittedCrossProfileNotificationListeners(ComponentName componentName, List<String> list) throws RemoteException;

    boolean setPermittedInputMethods(ComponentName componentName, List list) throws RemoteException;

    void setProfileEnabled(ComponentName componentName) throws RemoteException;

    void setProfileName(ComponentName componentName, String str) throws RemoteException;

    boolean setProfileOwner(ComponentName componentName, String str, int i) throws RemoteException;

    void setRecommendedGlobalProxy(ComponentName componentName, ProxyInfo proxyInfo) throws RemoteException;

    void setRequiredStrongAuthTimeout(ComponentName componentName, long j, boolean z) throws RemoteException;

    boolean setResetPasswordToken(ComponentName componentName, byte[] bArr) throws RemoteException;

    void setRestrictionsProvider(ComponentName componentName, ComponentName componentName2) throws RemoteException;

    void setScreenCaptureDisabled(ComponentName componentName, boolean z) throws RemoteException;

    void setSecureSetting(ComponentName componentName, String str, String str2) throws RemoteException;

    void setSecurityLoggingEnabled(ComponentName componentName, boolean z) throws RemoteException;

    void setShortSupportMessage(ComponentName componentName, CharSequence charSequence) throws RemoteException;

    void setStartUserSessionMessage(ComponentName componentName, CharSequence charSequence) throws RemoteException;

    boolean setStatusBarDisabled(ComponentName componentName, boolean z) throws RemoteException;

    int setStorageEncryption(ComponentName componentName, boolean z) throws RemoteException;

    void setSystemSetting(ComponentName componentName, String str, String str2) throws RemoteException;

    void setSystemUpdatePolicy(ComponentName componentName, SystemUpdatePolicy systemUpdatePolicy) throws RemoteException;

    boolean setTime(ComponentName componentName, long j) throws RemoteException;

    boolean setTimeZone(ComponentName componentName, String str) throws RemoteException;

    void setTrustAgentConfiguration(ComponentName componentName, ComponentName componentName2, PersistableBundle persistableBundle, boolean z) throws RemoteException;

    void setUninstallBlocked(ComponentName componentName, String str, String str2, boolean z) throws RemoteException;

    void setUserIcon(ComponentName componentName, Bitmap bitmap) throws RemoteException;

    void setUserProvisioningState(int i, int i2) throws RemoteException;

    void setUserRestriction(ComponentName componentName, String str, boolean z) throws RemoteException;

    void startManagedQuickContact(String str, long j, boolean z, long j2, Intent intent) throws RemoteException;

    int startUserInBackground(ComponentName componentName, UserHandle userHandle) throws RemoteException;

    boolean startViewCalendarEventInManagedProfile(String str, long j, long j2, long j3, boolean z, int i) throws RemoteException;

    int stopUser(ComponentName componentName, UserHandle userHandle) throws RemoteException;

    boolean switchUser(ComponentName componentName, UserHandle userHandle) throws RemoteException;

    void transferOwnership(ComponentName componentName, ComponentName componentName2, PersistableBundle persistableBundle) throws RemoteException;

    void uninstallCaCerts(ComponentName componentName, String str, String[] strArr) throws RemoteException;

    void uninstallPackageWithActiveAdmins(String str) throws RemoteException;

    boolean updateOverrideApn(ComponentName componentName, int i, ApnSetting apnSetting) throws RemoteException;

    void wipeDataWithReason(int i, String str) throws RemoteException;

    public static class Default implements IDevicePolicyManager {
        public void setPasswordQuality(ComponentName who, int quality, boolean parent) throws RemoteException {
        }

        public int getPasswordQuality(ComponentName who, int userHandle, boolean parent) throws RemoteException {
            return 0;
        }

        public void setPasswordMinimumLength(ComponentName who, int length, boolean parent) throws RemoteException {
        }

        public int getPasswordMinimumLength(ComponentName who, int userHandle, boolean parent) throws RemoteException {
            return 0;
        }

        public void setPasswordMinimumUpperCase(ComponentName who, int length, boolean parent) throws RemoteException {
        }

        public int getPasswordMinimumUpperCase(ComponentName who, int userHandle, boolean parent) throws RemoteException {
            return 0;
        }

        public void setPasswordMinimumLowerCase(ComponentName who, int length, boolean parent) throws RemoteException {
        }

        public int getPasswordMinimumLowerCase(ComponentName who, int userHandle, boolean parent) throws RemoteException {
            return 0;
        }

        public void setPasswordMinimumLetters(ComponentName who, int length, boolean parent) throws RemoteException {
        }

        public int getPasswordMinimumLetters(ComponentName who, int userHandle, boolean parent) throws RemoteException {
            return 0;
        }

        public void setPasswordMinimumNumeric(ComponentName who, int length, boolean parent) throws RemoteException {
        }

        public int getPasswordMinimumNumeric(ComponentName who, int userHandle, boolean parent) throws RemoteException {
            return 0;
        }

        public void setPasswordMinimumSymbols(ComponentName who, int length, boolean parent) throws RemoteException {
        }

        public int getPasswordMinimumSymbols(ComponentName who, int userHandle, boolean parent) throws RemoteException {
            return 0;
        }

        public void setPasswordMinimumNonLetter(ComponentName who, int length, boolean parent) throws RemoteException {
        }

        public int getPasswordMinimumNonLetter(ComponentName who, int userHandle, boolean parent) throws RemoteException {
            return 0;
        }

        public void setPasswordHistoryLength(ComponentName who, int length, boolean parent) throws RemoteException {
        }

        public int getPasswordHistoryLength(ComponentName who, int userHandle, boolean parent) throws RemoteException {
            return 0;
        }

        public void setPasswordExpirationTimeout(ComponentName who, long expiration, boolean parent) throws RemoteException {
        }

        public long getPasswordExpirationTimeout(ComponentName who, int userHandle, boolean parent) throws RemoteException {
            return 0;
        }

        public long getPasswordExpiration(ComponentName who, int userHandle, boolean parent) throws RemoteException {
            return 0;
        }

        public boolean isActivePasswordSufficient(int userHandle, boolean parent) throws RemoteException {
            return false;
        }

        public boolean isProfileActivePasswordSufficientForParent(int userHandle) throws RemoteException {
            return false;
        }

        public int getPasswordComplexity() throws RemoteException {
            return 0;
        }

        public boolean isUsingUnifiedPassword(ComponentName admin) throws RemoteException {
            return false;
        }

        public int getCurrentFailedPasswordAttempts(int userHandle, boolean parent) throws RemoteException {
            return 0;
        }

        public int getProfileWithMinimumFailedPasswordsForWipe(int userHandle, boolean parent) throws RemoteException {
            return 0;
        }

        public void setMaximumFailedPasswordsForWipe(ComponentName admin, int num, boolean parent) throws RemoteException {
        }

        public int getMaximumFailedPasswordsForWipe(ComponentName admin, int userHandle, boolean parent) throws RemoteException {
            return 0;
        }

        public boolean resetPassword(String password, int flags) throws RemoteException {
            return false;
        }

        public void setMaximumTimeToLock(ComponentName who, long timeMs, boolean parent) throws RemoteException {
        }

        public long getMaximumTimeToLock(ComponentName who, int userHandle, boolean parent) throws RemoteException {
            return 0;
        }

        public void setRequiredStrongAuthTimeout(ComponentName who, long timeMs, boolean parent) throws RemoteException {
        }

        public long getRequiredStrongAuthTimeout(ComponentName who, int userId, boolean parent) throws RemoteException {
            return 0;
        }

        public void lockNow(int flags, boolean parent) throws RemoteException {
        }

        public void wipeDataWithReason(int flags, String wipeReasonForUser) throws RemoteException {
        }

        public ComponentName setGlobalProxy(ComponentName admin, String proxySpec, String exclusionList) throws RemoteException {
            return null;
        }

        public ComponentName getGlobalProxyAdmin(int userHandle) throws RemoteException {
            return null;
        }

        public void setRecommendedGlobalProxy(ComponentName admin, ProxyInfo proxyInfo) throws RemoteException {
        }

        public int setStorageEncryption(ComponentName who, boolean encrypt) throws RemoteException {
            return 0;
        }

        public boolean getStorageEncryption(ComponentName who, int userHandle) throws RemoteException {
            return false;
        }

        public int getStorageEncryptionStatus(String callerPackage, int userHandle) throws RemoteException {
            return 0;
        }

        public boolean requestBugreport(ComponentName who) throws RemoteException {
            return false;
        }

        public void setCameraDisabled(ComponentName who, boolean disabled) throws RemoteException {
        }

        public boolean getCameraDisabled(ComponentName who, int userHandle) throws RemoteException {
            return false;
        }

        public void setScreenCaptureDisabled(ComponentName who, boolean disabled) throws RemoteException {
        }

        public boolean getScreenCaptureDisabled(ComponentName who, int userHandle) throws RemoteException {
            return false;
        }

        public void setKeyguardDisabledFeatures(ComponentName who, int which, boolean parent) throws RemoteException {
        }

        public int getKeyguardDisabledFeatures(ComponentName who, int userHandle, boolean parent) throws RemoteException {
            return 0;
        }

        public void setActiveAdmin(ComponentName policyReceiver, boolean refreshing, int userHandle) throws RemoteException {
        }

        public boolean isAdminActive(ComponentName policyReceiver, int userHandle) throws RemoteException {
            return false;
        }

        public List<ComponentName> getActiveAdmins(int userHandle) throws RemoteException {
            return null;
        }

        public boolean packageHasActiveAdmins(String packageName, int userHandle) throws RemoteException {
            return false;
        }

        public void getRemoveWarning(ComponentName policyReceiver, RemoteCallback result, int userHandle) throws RemoteException {
        }

        public void removeActiveAdmin(ComponentName policyReceiver, int userHandle) throws RemoteException {
        }

        public void forceRemoveActiveAdmin(ComponentName policyReceiver, int userHandle) throws RemoteException {
        }

        public boolean hasGrantedPolicy(ComponentName policyReceiver, int usesPolicy, int userHandle) throws RemoteException {
            return false;
        }

        public void setActivePasswordState(PasswordMetrics metrics, int userHandle) throws RemoteException {
        }

        public void reportPasswordChanged(int userId) throws RemoteException {
        }

        public void reportFailedPasswordAttempt(int userHandle) throws RemoteException {
        }

        public void reportSuccessfulPasswordAttempt(int userHandle) throws RemoteException {
        }

        public void reportFailedBiometricAttempt(int userHandle) throws RemoteException {
        }

        public void reportSuccessfulBiometricAttempt(int userHandle) throws RemoteException {
        }

        public void reportKeyguardDismissed(int userHandle) throws RemoteException {
        }

        public void reportKeyguardSecured(int userHandle) throws RemoteException {
        }

        public boolean setDeviceOwner(ComponentName who, String ownerName, int userId) throws RemoteException {
            return false;
        }

        public ComponentName getDeviceOwnerComponent(boolean callingUserOnly) throws RemoteException {
            return null;
        }

        public boolean hasDeviceOwner() throws RemoteException {
            return false;
        }

        public String getDeviceOwnerName() throws RemoteException {
            return null;
        }

        public void clearDeviceOwner(String packageName) throws RemoteException {
        }

        public int getDeviceOwnerUserId() throws RemoteException {
            return 0;
        }

        public boolean setProfileOwner(ComponentName who, String ownerName, int userHandle) throws RemoteException {
            return false;
        }

        public ComponentName getProfileOwnerAsUser(int userHandle) throws RemoteException {
            return null;
        }

        public ComponentName getProfileOwner(int userHandle) throws RemoteException {
            return null;
        }

        public String getProfileOwnerName(int userHandle) throws RemoteException {
            return null;
        }

        public void setProfileEnabled(ComponentName who) throws RemoteException {
        }

        public void setProfileName(ComponentName who, String profileName) throws RemoteException {
        }

        public void clearProfileOwner(ComponentName who) throws RemoteException {
        }

        public boolean hasUserSetupCompleted() throws RemoteException {
            return false;
        }

        public boolean checkDeviceIdentifierAccess(String packageName, int pid, int uid) throws RemoteException {
            return false;
        }

        public void setDeviceOwnerLockScreenInfo(ComponentName who, CharSequence deviceOwnerInfo) throws RemoteException {
        }

        public CharSequence getDeviceOwnerLockScreenInfo() throws RemoteException {
            return null;
        }

        public String[] setPackagesSuspended(ComponentName admin, String callerPackage, String[] packageNames, boolean suspended) throws RemoteException {
            return null;
        }

        public boolean isPackageSuspended(ComponentName admin, String callerPackage, String packageName) throws RemoteException {
            return false;
        }

        public boolean installCaCert(ComponentName admin, String callerPackage, byte[] certBuffer) throws RemoteException {
            return false;
        }

        public void uninstallCaCerts(ComponentName admin, String callerPackage, String[] aliases) throws RemoteException {
        }

        public void enforceCanManageCaCerts(ComponentName admin, String callerPackage) throws RemoteException {
        }

        public boolean approveCaCert(String alias, int userHandle, boolean approval) throws RemoteException {
            return false;
        }

        public boolean isCaCertApproved(String alias, int userHandle) throws RemoteException {
            return false;
        }

        public boolean installKeyPair(ComponentName who, String callerPackage, byte[] privKeyBuffer, byte[] certBuffer, byte[] certChainBuffer, String alias, boolean requestAccess, boolean isUserSelectable) throws RemoteException {
            return false;
        }

        public boolean removeKeyPair(ComponentName who, String callerPackage, String alias) throws RemoteException {
            return false;
        }

        public boolean generateKeyPair(ComponentName who, String callerPackage, String algorithm, ParcelableKeyGenParameterSpec keySpec, int idAttestationFlags, KeymasterCertificateChain attestationChain) throws RemoteException {
            return false;
        }

        public boolean setKeyPairCertificate(ComponentName who, String callerPackage, String alias, byte[] certBuffer, byte[] certChainBuffer, boolean isUserSelectable) throws RemoteException {
            return false;
        }

        public void choosePrivateKeyAlias(int uid, Uri uri, String alias, IBinder aliasCallback) throws RemoteException {
        }

        public void setDelegatedScopes(ComponentName who, String delegatePackage, List<String> list) throws RemoteException {
        }

        public List<String> getDelegatedScopes(ComponentName who, String delegatePackage) throws RemoteException {
            return null;
        }

        public List<String> getDelegatePackages(ComponentName who, String scope) throws RemoteException {
            return null;
        }

        public void setCertInstallerPackage(ComponentName who, String installerPackage) throws RemoteException {
        }

        public String getCertInstallerPackage(ComponentName who) throws RemoteException {
            return null;
        }

        public boolean setAlwaysOnVpnPackage(ComponentName who, String vpnPackage, boolean lockdown, List<String> list) throws RemoteException {
            return false;
        }

        public String getAlwaysOnVpnPackage(ComponentName who) throws RemoteException {
            return null;
        }

        public boolean isAlwaysOnVpnLockdownEnabled(ComponentName who) throws RemoteException {
            return false;
        }

        public List<String> getAlwaysOnVpnLockdownWhitelist(ComponentName who) throws RemoteException {
            return null;
        }

        public void addPersistentPreferredActivity(ComponentName admin, IntentFilter filter, ComponentName activity) throws RemoteException {
        }

        public void clearPackagePersistentPreferredActivities(ComponentName admin, String packageName) throws RemoteException {
        }

        public void setDefaultSmsApplication(ComponentName admin, String packageName) throws RemoteException {
        }

        public void setApplicationRestrictions(ComponentName who, String callerPackage, String packageName, Bundle settings) throws RemoteException {
        }

        public Bundle getApplicationRestrictions(ComponentName who, String callerPackage, String packageName) throws RemoteException {
            return null;
        }

        public boolean setApplicationRestrictionsManagingPackage(ComponentName admin, String packageName) throws RemoteException {
            return false;
        }

        public String getApplicationRestrictionsManagingPackage(ComponentName admin) throws RemoteException {
            return null;
        }

        public boolean isCallerApplicationRestrictionsManagingPackage(String callerPackage) throws RemoteException {
            return false;
        }

        public void setRestrictionsProvider(ComponentName who, ComponentName provider) throws RemoteException {
        }

        public ComponentName getRestrictionsProvider(int userHandle) throws RemoteException {
            return null;
        }

        public void setUserRestriction(ComponentName who, String key, boolean enable) throws RemoteException {
        }

        public Bundle getUserRestrictions(ComponentName who) throws RemoteException {
            return null;
        }

        public void addCrossProfileIntentFilter(ComponentName admin, IntentFilter filter, int flags) throws RemoteException {
        }

        public void clearCrossProfileIntentFilters(ComponentName admin) throws RemoteException {
        }

        public boolean setPermittedAccessibilityServices(ComponentName admin, List packageList) throws RemoteException {
            return false;
        }

        public List getPermittedAccessibilityServices(ComponentName admin) throws RemoteException {
            return null;
        }

        public List getPermittedAccessibilityServicesForUser(int userId) throws RemoteException {
            return null;
        }

        public boolean isAccessibilityServicePermittedByAdmin(ComponentName admin, String packageName, int userId) throws RemoteException {
            return false;
        }

        public boolean setPermittedInputMethods(ComponentName admin, List packageList) throws RemoteException {
            return false;
        }

        public List getPermittedInputMethods(ComponentName admin) throws RemoteException {
            return null;
        }

        public List getPermittedInputMethodsForCurrentUser() throws RemoteException {
            return null;
        }

        public boolean isInputMethodPermittedByAdmin(ComponentName admin, String packageName, int userId) throws RemoteException {
            return false;
        }

        public boolean setPermittedCrossProfileNotificationListeners(ComponentName admin, List<String> list) throws RemoteException {
            return false;
        }

        public List<String> getPermittedCrossProfileNotificationListeners(ComponentName admin) throws RemoteException {
            return null;
        }

        public boolean isNotificationListenerServicePermitted(String packageName, int userId) throws RemoteException {
            return false;
        }

        public Intent createAdminSupportIntent(String restriction) throws RemoteException {
            return null;
        }

        public boolean setApplicationHidden(ComponentName admin, String callerPackage, String packageName, boolean hidden) throws RemoteException {
            return false;
        }

        public boolean isApplicationHidden(ComponentName admin, String callerPackage, String packageName) throws RemoteException {
            return false;
        }

        public UserHandle createAndManageUser(ComponentName who, String name, ComponentName profileOwner, PersistableBundle adminExtras, int flags) throws RemoteException {
            return null;
        }

        public boolean removeUser(ComponentName who, UserHandle userHandle) throws RemoteException {
            return false;
        }

        public boolean switchUser(ComponentName who, UserHandle userHandle) throws RemoteException {
            return false;
        }

        public int startUserInBackground(ComponentName who, UserHandle userHandle) throws RemoteException {
            return 0;
        }

        public int stopUser(ComponentName who, UserHandle userHandle) throws RemoteException {
            return 0;
        }

        public int logoutUser(ComponentName who) throws RemoteException {
            return 0;
        }

        public List<UserHandle> getSecondaryUsers(ComponentName who) throws RemoteException {
            return null;
        }

        public void enableSystemApp(ComponentName admin, String callerPackage, String packageName) throws RemoteException {
        }

        public int enableSystemAppWithIntent(ComponentName admin, String callerPackage, Intent intent) throws RemoteException {
            return 0;
        }

        public boolean installExistingPackage(ComponentName admin, String callerPackage, String packageName) throws RemoteException {
            return false;
        }

        public void setAccountManagementDisabled(ComponentName who, String accountType, boolean disabled) throws RemoteException {
        }

        public String[] getAccountTypesWithManagementDisabled() throws RemoteException {
            return null;
        }

        public String[] getAccountTypesWithManagementDisabledAsUser(int userId) throws RemoteException {
            return null;
        }

        public void setLockTaskPackages(ComponentName who, String[] packages) throws RemoteException {
        }

        public String[] getLockTaskPackages(ComponentName who) throws RemoteException {
            return null;
        }

        public boolean isLockTaskPermitted(String pkg) throws RemoteException {
            return false;
        }

        public void setLockTaskFeatures(ComponentName who, int flags) throws RemoteException {
        }

        public int getLockTaskFeatures(ComponentName who) throws RemoteException {
            return 0;
        }

        public void setGlobalSetting(ComponentName who, String setting, String value) throws RemoteException {
        }

        public void setSystemSetting(ComponentName who, String setting, String value) throws RemoteException {
        }

        public void setSecureSetting(ComponentName who, String setting, String value) throws RemoteException {
        }

        public boolean setTime(ComponentName who, long millis) throws RemoteException {
            return false;
        }

        public boolean setTimeZone(ComponentName who, String timeZone) throws RemoteException {
            return false;
        }

        public void setMasterVolumeMuted(ComponentName admin, boolean on) throws RemoteException {
        }

        public boolean isMasterVolumeMuted(ComponentName admin) throws RemoteException {
            return false;
        }

        public void notifyLockTaskModeChanged(boolean isEnabled, String pkg, int userId) throws RemoteException {
        }

        public void setUninstallBlocked(ComponentName admin, String callerPackage, String packageName, boolean uninstallBlocked) throws RemoteException {
        }

        public boolean isUninstallBlocked(ComponentName admin, String packageName) throws RemoteException {
            return false;
        }

        public void setCrossProfileCallerIdDisabled(ComponentName who, boolean disabled) throws RemoteException {
        }

        public boolean getCrossProfileCallerIdDisabled(ComponentName who) throws RemoteException {
            return false;
        }

        public boolean getCrossProfileCallerIdDisabledForUser(int userId) throws RemoteException {
            return false;
        }

        public void setCrossProfileContactsSearchDisabled(ComponentName who, boolean disabled) throws RemoteException {
        }

        public boolean getCrossProfileContactsSearchDisabled(ComponentName who) throws RemoteException {
            return false;
        }

        public boolean getCrossProfileContactsSearchDisabledForUser(int userId) throws RemoteException {
            return false;
        }

        public void startManagedQuickContact(String lookupKey, long contactId, boolean isContactIdIgnored, long directoryId, Intent originalIntent) throws RemoteException {
        }

        public void setBluetoothContactSharingDisabled(ComponentName who, boolean disabled) throws RemoteException {
        }

        public boolean getBluetoothContactSharingDisabled(ComponentName who) throws RemoteException {
            return false;
        }

        public boolean getBluetoothContactSharingDisabledForUser(int userId) throws RemoteException {
            return false;
        }

        public void setTrustAgentConfiguration(ComponentName admin, ComponentName agent, PersistableBundle args, boolean parent) throws RemoteException {
        }

        public List<PersistableBundle> getTrustAgentConfiguration(ComponentName admin, ComponentName agent, int userId, boolean parent) throws RemoteException {
            return null;
        }

        public boolean addCrossProfileWidgetProvider(ComponentName admin, String packageName) throws RemoteException {
            return false;
        }

        public boolean removeCrossProfileWidgetProvider(ComponentName admin, String packageName) throws RemoteException {
            return false;
        }

        public List<String> getCrossProfileWidgetProviders(ComponentName admin) throws RemoteException {
            return null;
        }

        public void setAutoTimeRequired(ComponentName who, boolean required) throws RemoteException {
        }

        public boolean getAutoTimeRequired() throws RemoteException {
            return false;
        }

        public void setForceEphemeralUsers(ComponentName who, boolean forceEpehemeralUsers) throws RemoteException {
        }

        public boolean getForceEphemeralUsers(ComponentName who) throws RemoteException {
            return false;
        }

        public boolean isRemovingAdmin(ComponentName adminReceiver, int userHandle) throws RemoteException {
            return false;
        }

        public void setUserIcon(ComponentName admin, Bitmap icon) throws RemoteException {
        }

        public void setSystemUpdatePolicy(ComponentName who, SystemUpdatePolicy policy) throws RemoteException {
        }

        public SystemUpdatePolicy getSystemUpdatePolicy() throws RemoteException {
            return null;
        }

        public void clearSystemUpdatePolicyFreezePeriodRecord() throws RemoteException {
        }

        public boolean setKeyguardDisabled(ComponentName admin, boolean disabled) throws RemoteException {
            return false;
        }

        public boolean setStatusBarDisabled(ComponentName who, boolean disabled) throws RemoteException {
            return false;
        }

        public boolean getDoNotAskCredentialsOnBoot() throws RemoteException {
            return false;
        }

        public void notifyPendingSystemUpdate(SystemUpdateInfo info) throws RemoteException {
        }

        public SystemUpdateInfo getPendingSystemUpdate(ComponentName admin) throws RemoteException {
            return null;
        }

        public void setPermissionPolicy(ComponentName admin, String callerPackage, int policy) throws RemoteException {
        }

        public int getPermissionPolicy(ComponentName admin) throws RemoteException {
            return 0;
        }

        public void setPermissionGrantState(ComponentName admin, String callerPackage, String packageName, String permission, int grantState, RemoteCallback resultReceiver) throws RemoteException {
        }

        public int getPermissionGrantState(ComponentName admin, String callerPackage, String packageName, String permission) throws RemoteException {
            return 0;
        }

        public boolean isProvisioningAllowed(String action, String packageName) throws RemoteException {
            return false;
        }

        public int checkProvisioningPreCondition(String action, String packageName) throws RemoteException {
            return 0;
        }

        public void setKeepUninstalledPackages(ComponentName admin, String callerPackage, List<String> list) throws RemoteException {
        }

        public List<String> getKeepUninstalledPackages(ComponentName admin, String callerPackage) throws RemoteException {
            return null;
        }

        public boolean isManagedProfile(ComponentName admin) throws RemoteException {
            return false;
        }

        public boolean isSystemOnlyUser(ComponentName admin) throws RemoteException {
            return false;
        }

        public String getWifiMacAddress(ComponentName admin) throws RemoteException {
            return null;
        }

        public void reboot(ComponentName admin) throws RemoteException {
        }

        public void setShortSupportMessage(ComponentName admin, CharSequence message) throws RemoteException {
        }

        public CharSequence getShortSupportMessage(ComponentName admin) throws RemoteException {
            return null;
        }

        public void setLongSupportMessage(ComponentName admin, CharSequence message) throws RemoteException {
        }

        public CharSequence getLongSupportMessage(ComponentName admin) throws RemoteException {
            return null;
        }

        public CharSequence getShortSupportMessageForUser(ComponentName admin, int userHandle) throws RemoteException {
            return null;
        }

        public CharSequence getLongSupportMessageForUser(ComponentName admin, int userHandle) throws RemoteException {
            return null;
        }

        public boolean isSeparateProfileChallengeAllowed(int userHandle) throws RemoteException {
            return false;
        }

        public void setOrganizationColor(ComponentName admin, int color) throws RemoteException {
        }

        public void setOrganizationColorForUser(int color, int userId) throws RemoteException {
        }

        public int getOrganizationColor(ComponentName admin) throws RemoteException {
            return 0;
        }

        public int getOrganizationColorForUser(int userHandle) throws RemoteException {
            return 0;
        }

        public void setOrganizationName(ComponentName admin, CharSequence title) throws RemoteException {
        }

        public CharSequence getOrganizationName(ComponentName admin) throws RemoteException {
            return null;
        }

        public CharSequence getDeviceOwnerOrganizationName() throws RemoteException {
            return null;
        }

        public CharSequence getOrganizationNameForUser(int userHandle) throws RemoteException {
            return null;
        }

        public int getUserProvisioningState() throws RemoteException {
            return 0;
        }

        public void setUserProvisioningState(int state, int userHandle) throws RemoteException {
        }

        public void setAffiliationIds(ComponentName admin, List<String> list) throws RemoteException {
        }

        public List<String> getAffiliationIds(ComponentName admin) throws RemoteException {
            return null;
        }

        public boolean isAffiliatedUser() throws RemoteException {
            return false;
        }

        public void setSecurityLoggingEnabled(ComponentName admin, boolean enabled) throws RemoteException {
        }

        public boolean isSecurityLoggingEnabled(ComponentName admin) throws RemoteException {
            return false;
        }

        public ParceledListSlice retrieveSecurityLogs(ComponentName admin) throws RemoteException {
            return null;
        }

        public ParceledListSlice retrievePreRebootSecurityLogs(ComponentName admin) throws RemoteException {
            return null;
        }

        public long forceNetworkLogs() throws RemoteException {
            return 0;
        }

        public long forceSecurityLogs() throws RemoteException {
            return 0;
        }

        public boolean isUninstallInQueue(String packageName) throws RemoteException {
            return false;
        }

        public void uninstallPackageWithActiveAdmins(String packageName) throws RemoteException {
        }

        public boolean isDeviceProvisioned() throws RemoteException {
            return false;
        }

        public boolean isDeviceProvisioningConfigApplied() throws RemoteException {
            return false;
        }

        public void setDeviceProvisioningConfigApplied() throws RemoteException {
        }

        public void forceUpdateUserSetupComplete() throws RemoteException {
        }

        public void setBackupServiceEnabled(ComponentName admin, boolean enabled) throws RemoteException {
        }

        public boolean isBackupServiceEnabled(ComponentName admin) throws RemoteException {
            return false;
        }

        public void setNetworkLoggingEnabled(ComponentName admin, String packageName, boolean enabled) throws RemoteException {
        }

        public boolean isNetworkLoggingEnabled(ComponentName admin, String packageName) throws RemoteException {
            return false;
        }

        public List<NetworkEvent> retrieveNetworkLogs(ComponentName admin, String packageName, long batchToken) throws RemoteException {
            return null;
        }

        public boolean bindDeviceAdminServiceAsUser(ComponentName admin, IApplicationThread caller, IBinder token, Intent service, IServiceConnection connection, int flags, int targetUserId) throws RemoteException {
            return false;
        }

        public List<UserHandle> getBindDeviceAdminTargetUsers(ComponentName admin) throws RemoteException {
            return null;
        }

        public boolean isEphemeralUser(ComponentName admin) throws RemoteException {
            return false;
        }

        public long getLastSecurityLogRetrievalTime() throws RemoteException {
            return 0;
        }

        public long getLastBugReportRequestTime() throws RemoteException {
            return 0;
        }

        public long getLastNetworkLogRetrievalTime() throws RemoteException {
            return 0;
        }

        public boolean setResetPasswordToken(ComponentName admin, byte[] token) throws RemoteException {
            return false;
        }

        public boolean clearResetPasswordToken(ComponentName admin) throws RemoteException {
            return false;
        }

        public boolean isResetPasswordTokenActive(ComponentName admin) throws RemoteException {
            return false;
        }

        public boolean resetPasswordWithToken(ComponentName admin, String password, byte[] token, int flags) throws RemoteException {
            return false;
        }

        public boolean isCurrentInputMethodSetByOwner() throws RemoteException {
            return false;
        }

        public StringParceledListSlice getOwnerInstalledCaCerts(UserHandle user) throws RemoteException {
            return null;
        }

        public void clearApplicationUserData(ComponentName admin, String packageName, IPackageDataObserver callback) throws RemoteException {
        }

        public void setLogoutEnabled(ComponentName admin, boolean enabled) throws RemoteException {
        }

        public boolean isLogoutEnabled() throws RemoteException {
            return false;
        }

        public List<String> getDisallowedSystemApps(ComponentName admin, int userId, String provisioningAction) throws RemoteException {
            return null;
        }

        public void transferOwnership(ComponentName admin, ComponentName target, PersistableBundle bundle) throws RemoteException {
        }

        public PersistableBundle getTransferOwnershipBundle() throws RemoteException {
            return null;
        }

        public void setStartUserSessionMessage(ComponentName admin, CharSequence startUserSessionMessage) throws RemoteException {
        }

        public void setEndUserSessionMessage(ComponentName admin, CharSequence endUserSessionMessage) throws RemoteException {
        }

        public CharSequence getStartUserSessionMessage(ComponentName admin) throws RemoteException {
            return null;
        }

        public CharSequence getEndUserSessionMessage(ComponentName admin) throws RemoteException {
            return null;
        }

        public List<String> setMeteredDataDisabledPackages(ComponentName admin, List<String> list) throws RemoteException {
            return null;
        }

        public List<String> getMeteredDataDisabledPackages(ComponentName admin) throws RemoteException {
            return null;
        }

        public int addOverrideApn(ComponentName admin, ApnSetting apnSetting) throws RemoteException {
            return 0;
        }

        public boolean updateOverrideApn(ComponentName admin, int apnId, ApnSetting apnSetting) throws RemoteException {
            return false;
        }

        public boolean removeOverrideApn(ComponentName admin, int apnId) throws RemoteException {
            return false;
        }

        public List<ApnSetting> getOverrideApns(ComponentName admin) throws RemoteException {
            return null;
        }

        public void setOverrideApnsEnabled(ComponentName admin, boolean enabled) throws RemoteException {
        }

        public boolean isOverrideApnEnabled(ComponentName admin) throws RemoteException {
            return false;
        }

        public boolean isMeteredDataDisabledPackageForUser(ComponentName admin, String packageName, int userId) throws RemoteException {
            return false;
        }

        public int setGlobalPrivateDns(ComponentName admin, int mode, String privateDnsHost) throws RemoteException {
            return 0;
        }

        public int getGlobalPrivateDnsMode(ComponentName admin) throws RemoteException {
            return 0;
        }

        public String getGlobalPrivateDnsHost(ComponentName admin) throws RemoteException {
            return null;
        }

        public void grantDeviceIdsAccessToProfileOwner(ComponentName who, int userId) throws RemoteException {
        }

        public void installUpdateFromFile(ComponentName admin, ParcelFileDescriptor updateFileDescriptor, StartInstallingUpdateCallback listener) throws RemoteException {
        }

        public void setCrossProfileCalendarPackages(ComponentName admin, List<String> list) throws RemoteException {
        }

        public List<String> getCrossProfileCalendarPackages(ComponentName admin) throws RemoteException {
            return null;
        }

        public boolean isPackageAllowedToAccessCalendarForUser(String packageName, int userHandle) throws RemoteException {
            return false;
        }

        public List<String> getCrossProfileCalendarPackagesForUser(int userHandle) throws RemoteException {
            return null;
        }

        public boolean isManagedKiosk() throws RemoteException {
            return false;
        }

        public boolean isUnattendedManagedKiosk() throws RemoteException {
            return false;
        }

        public boolean startViewCalendarEventInManagedProfile(String packageName, long eventId, long start, long end, boolean allDay, int flags) throws RemoteException {
            return false;
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IDevicePolicyManager {
        private static final String DESCRIPTOR = "android.app.admin.IDevicePolicyManager";
        static final int TRANSACTION_addCrossProfileIntentFilter = 116;
        static final int TRANSACTION_addCrossProfileWidgetProvider = 172;
        static final int TRANSACTION_addOverrideApn = 262;
        static final int TRANSACTION_addPersistentPreferredActivity = 104;
        static final int TRANSACTION_approveCaCert = 88;
        static final int TRANSACTION_bindDeviceAdminServiceAsUser = 238;
        static final int TRANSACTION_checkDeviceIdentifierAccess = 80;
        static final int TRANSACTION_checkProvisioningPreCondition = 194;
        static final int TRANSACTION_choosePrivateKeyAlias = 94;
        static final int TRANSACTION_clearApplicationUserData = 250;
        static final int TRANSACTION_clearCrossProfileIntentFilters = 117;
        static final int TRANSACTION_clearDeviceOwner = 70;
        static final int TRANSACTION_clearPackagePersistentPreferredActivities = 105;
        static final int TRANSACTION_clearProfileOwner = 78;
        static final int TRANSACTION_clearResetPasswordToken = 245;
        static final int TRANSACTION_clearSystemUpdatePolicyFreezePeriodRecord = 183;
        static final int TRANSACTION_createAdminSupportIntent = 129;
        static final int TRANSACTION_createAndManageUser = 132;
        static final int TRANSACTION_enableSystemApp = 139;
        static final int TRANSACTION_enableSystemAppWithIntent = 140;
        static final int TRANSACTION_enforceCanManageCaCerts = 87;
        static final int TRANSACTION_forceNetworkLogs = 225;
        static final int TRANSACTION_forceRemoveActiveAdmin = 56;
        static final int TRANSACTION_forceSecurityLogs = 226;
        static final int TRANSACTION_forceUpdateUserSetupComplete = 232;
        static final int TRANSACTION_generateKeyPair = 92;
        static final int TRANSACTION_getAccountTypesWithManagementDisabled = 143;
        static final int TRANSACTION_getAccountTypesWithManagementDisabledAsUser = 144;
        static final int TRANSACTION_getActiveAdmins = 52;
        static final int TRANSACTION_getAffiliationIds = 219;
        static final int TRANSACTION_getAlwaysOnVpnLockdownWhitelist = 103;
        static final int TRANSACTION_getAlwaysOnVpnPackage = 101;
        static final int TRANSACTION_getApplicationRestrictions = 108;
        static final int TRANSACTION_getApplicationRestrictionsManagingPackage = 110;
        static final int TRANSACTION_getAutoTimeRequired = 176;
        static final int TRANSACTION_getBindDeviceAdminTargetUsers = 239;
        static final int TRANSACTION_getBluetoothContactSharingDisabled = 168;
        static final int TRANSACTION_getBluetoothContactSharingDisabledForUser = 169;
        static final int TRANSACTION_getCameraDisabled = 45;
        static final int TRANSACTION_getCertInstallerPackage = 99;
        static final int TRANSACTION_getCrossProfileCalendarPackages = 275;
        static final int TRANSACTION_getCrossProfileCalendarPackagesForUser = 277;
        static final int TRANSACTION_getCrossProfileCallerIdDisabled = 161;
        static final int TRANSACTION_getCrossProfileCallerIdDisabledForUser = 162;
        static final int TRANSACTION_getCrossProfileContactsSearchDisabled = 164;
        static final int TRANSACTION_getCrossProfileContactsSearchDisabledForUser = 165;
        static final int TRANSACTION_getCrossProfileWidgetProviders = 174;
        static final int TRANSACTION_getCurrentFailedPasswordAttempts = 26;
        static final int TRANSACTION_getDelegatePackages = 97;
        static final int TRANSACTION_getDelegatedScopes = 96;
        static final int TRANSACTION_getDeviceOwnerComponent = 67;
        static final int TRANSACTION_getDeviceOwnerLockScreenInfo = 82;
        static final int TRANSACTION_getDeviceOwnerName = 69;
        static final int TRANSACTION_getDeviceOwnerOrganizationName = 214;
        static final int TRANSACTION_getDeviceOwnerUserId = 71;
        static final int TRANSACTION_getDisallowedSystemApps = 253;
        static final int TRANSACTION_getDoNotAskCredentialsOnBoot = 186;
        static final int TRANSACTION_getEndUserSessionMessage = 259;
        static final int TRANSACTION_getForceEphemeralUsers = 178;
        static final int TRANSACTION_getGlobalPrivateDnsHost = 271;
        static final int TRANSACTION_getGlobalPrivateDnsMode = 270;
        static final int TRANSACTION_getGlobalProxyAdmin = 38;
        static final int TRANSACTION_getKeepUninstalledPackages = 196;
        static final int TRANSACTION_getKeyguardDisabledFeatures = 49;
        static final int TRANSACTION_getLastBugReportRequestTime = 242;
        static final int TRANSACTION_getLastNetworkLogRetrievalTime = 243;
        static final int TRANSACTION_getLastSecurityLogRetrievalTime = 241;
        static final int TRANSACTION_getLockTaskFeatures = 149;
        static final int TRANSACTION_getLockTaskPackages = 146;
        static final int TRANSACTION_getLongSupportMessage = 204;
        static final int TRANSACTION_getLongSupportMessageForUser = 206;
        static final int TRANSACTION_getMaximumFailedPasswordsForWipe = 29;
        static final int TRANSACTION_getMaximumTimeToLock = 32;
        static final int TRANSACTION_getMeteredDataDisabledPackages = 261;
        static final int TRANSACTION_getOrganizationColor = 210;
        static final int TRANSACTION_getOrganizationColorForUser = 211;
        static final int TRANSACTION_getOrganizationName = 213;
        static final int TRANSACTION_getOrganizationNameForUser = 215;
        static final int TRANSACTION_getOverrideApns = 265;
        static final int TRANSACTION_getOwnerInstalledCaCerts = 249;
        static final int TRANSACTION_getPasswordComplexity = 24;
        static final int TRANSACTION_getPasswordExpiration = 21;
        static final int TRANSACTION_getPasswordExpirationTimeout = 20;
        static final int TRANSACTION_getPasswordHistoryLength = 18;
        static final int TRANSACTION_getPasswordMinimumLength = 4;
        static final int TRANSACTION_getPasswordMinimumLetters = 10;
        static final int TRANSACTION_getPasswordMinimumLowerCase = 8;
        static final int TRANSACTION_getPasswordMinimumNonLetter = 16;
        static final int TRANSACTION_getPasswordMinimumNumeric = 12;
        static final int TRANSACTION_getPasswordMinimumSymbols = 14;
        static final int TRANSACTION_getPasswordMinimumUpperCase = 6;
        static final int TRANSACTION_getPasswordQuality = 2;
        static final int TRANSACTION_getPendingSystemUpdate = 188;
        static final int TRANSACTION_getPermissionGrantState = 192;
        static final int TRANSACTION_getPermissionPolicy = 190;
        static final int TRANSACTION_getPermittedAccessibilityServices = 119;
        static final int TRANSACTION_getPermittedAccessibilityServicesForUser = 120;
        static final int TRANSACTION_getPermittedCrossProfileNotificationListeners = 127;
        static final int TRANSACTION_getPermittedInputMethods = 123;
        static final int TRANSACTION_getPermittedInputMethodsForCurrentUser = 124;
        static final int TRANSACTION_getProfileOwner = 74;
        static final int TRANSACTION_getProfileOwnerAsUser = 73;
        static final int TRANSACTION_getProfileOwnerName = 75;
        static final int TRANSACTION_getProfileWithMinimumFailedPasswordsForWipe = 27;
        static final int TRANSACTION_getRemoveWarning = 54;
        static final int TRANSACTION_getRequiredStrongAuthTimeout = 34;
        static final int TRANSACTION_getRestrictionsProvider = 113;
        static final int TRANSACTION_getScreenCaptureDisabled = 47;
        static final int TRANSACTION_getSecondaryUsers = 138;
        static final int TRANSACTION_getShortSupportMessage = 202;
        static final int TRANSACTION_getShortSupportMessageForUser = 205;
        static final int TRANSACTION_getStartUserSessionMessage = 258;
        static final int TRANSACTION_getStorageEncryption = 41;
        static final int TRANSACTION_getStorageEncryptionStatus = 42;
        static final int TRANSACTION_getSystemUpdatePolicy = 182;
        static final int TRANSACTION_getTransferOwnershipBundle = 255;
        static final int TRANSACTION_getTrustAgentConfiguration = 171;
        static final int TRANSACTION_getUserProvisioningState = 216;
        static final int TRANSACTION_getUserRestrictions = 115;
        static final int TRANSACTION_getWifiMacAddress = 199;
        static final int TRANSACTION_grantDeviceIdsAccessToProfileOwner = 272;
        static final int TRANSACTION_hasDeviceOwner = 68;
        static final int TRANSACTION_hasGrantedPolicy = 57;
        static final int TRANSACTION_hasUserSetupCompleted = 79;
        static final int TRANSACTION_installCaCert = 85;
        static final int TRANSACTION_installExistingPackage = 141;
        static final int TRANSACTION_installKeyPair = 90;
        static final int TRANSACTION_installUpdateFromFile = 273;
        static final int TRANSACTION_isAccessibilityServicePermittedByAdmin = 121;
        static final int TRANSACTION_isActivePasswordSufficient = 22;
        static final int TRANSACTION_isAdminActive = 51;
        static final int TRANSACTION_isAffiliatedUser = 220;
        static final int TRANSACTION_isAlwaysOnVpnLockdownEnabled = 102;
        static final int TRANSACTION_isApplicationHidden = 131;
        static final int TRANSACTION_isBackupServiceEnabled = 234;
        static final int TRANSACTION_isCaCertApproved = 89;
        static final int TRANSACTION_isCallerApplicationRestrictionsManagingPackage = 111;
        static final int TRANSACTION_isCurrentInputMethodSetByOwner = 248;
        static final int TRANSACTION_isDeviceProvisioned = 229;
        static final int TRANSACTION_isDeviceProvisioningConfigApplied = 230;
        static final int TRANSACTION_isEphemeralUser = 240;
        static final int TRANSACTION_isInputMethodPermittedByAdmin = 125;
        static final int TRANSACTION_isLockTaskPermitted = 147;
        static final int TRANSACTION_isLogoutEnabled = 252;
        static final int TRANSACTION_isManagedKiosk = 278;
        static final int TRANSACTION_isManagedProfile = 197;
        static final int TRANSACTION_isMasterVolumeMuted = 156;
        static final int TRANSACTION_isMeteredDataDisabledPackageForUser = 268;
        static final int TRANSACTION_isNetworkLoggingEnabled = 236;
        static final int TRANSACTION_isNotificationListenerServicePermitted = 128;
        static final int TRANSACTION_isOverrideApnEnabled = 267;
        static final int TRANSACTION_isPackageAllowedToAccessCalendarForUser = 276;
        static final int TRANSACTION_isPackageSuspended = 84;
        static final int TRANSACTION_isProfileActivePasswordSufficientForParent = 23;
        static final int TRANSACTION_isProvisioningAllowed = 193;
        static final int TRANSACTION_isRemovingAdmin = 179;
        static final int TRANSACTION_isResetPasswordTokenActive = 246;
        static final int TRANSACTION_isSecurityLoggingEnabled = 222;
        static final int TRANSACTION_isSeparateProfileChallengeAllowed = 207;
        static final int TRANSACTION_isSystemOnlyUser = 198;
        static final int TRANSACTION_isUnattendedManagedKiosk = 279;
        static final int TRANSACTION_isUninstallBlocked = 159;
        static final int TRANSACTION_isUninstallInQueue = 227;
        static final int TRANSACTION_isUsingUnifiedPassword = 25;
        static final int TRANSACTION_lockNow = 35;
        static final int TRANSACTION_logoutUser = 137;
        static final int TRANSACTION_notifyLockTaskModeChanged = 157;
        static final int TRANSACTION_notifyPendingSystemUpdate = 187;
        static final int TRANSACTION_packageHasActiveAdmins = 53;
        static final int TRANSACTION_reboot = 200;
        static final int TRANSACTION_removeActiveAdmin = 55;
        static final int TRANSACTION_removeCrossProfileWidgetProvider = 173;
        static final int TRANSACTION_removeKeyPair = 91;
        static final int TRANSACTION_removeOverrideApn = 264;
        static final int TRANSACTION_removeUser = 133;
        static final int TRANSACTION_reportFailedBiometricAttempt = 62;
        static final int TRANSACTION_reportFailedPasswordAttempt = 60;
        static final int TRANSACTION_reportKeyguardDismissed = 64;
        static final int TRANSACTION_reportKeyguardSecured = 65;
        static final int TRANSACTION_reportPasswordChanged = 59;
        static final int TRANSACTION_reportSuccessfulBiometricAttempt = 63;
        static final int TRANSACTION_reportSuccessfulPasswordAttempt = 61;
        static final int TRANSACTION_requestBugreport = 43;
        static final int TRANSACTION_resetPassword = 30;
        static final int TRANSACTION_resetPasswordWithToken = 247;
        static final int TRANSACTION_retrieveNetworkLogs = 237;
        static final int TRANSACTION_retrievePreRebootSecurityLogs = 224;
        static final int TRANSACTION_retrieveSecurityLogs = 223;
        static final int TRANSACTION_setAccountManagementDisabled = 142;
        static final int TRANSACTION_setActiveAdmin = 50;
        static final int TRANSACTION_setActivePasswordState = 58;
        static final int TRANSACTION_setAffiliationIds = 218;
        static final int TRANSACTION_setAlwaysOnVpnPackage = 100;
        static final int TRANSACTION_setApplicationHidden = 130;
        static final int TRANSACTION_setApplicationRestrictions = 107;
        static final int TRANSACTION_setApplicationRestrictionsManagingPackage = 109;
        static final int TRANSACTION_setAutoTimeRequired = 175;
        static final int TRANSACTION_setBackupServiceEnabled = 233;
        static final int TRANSACTION_setBluetoothContactSharingDisabled = 167;
        static final int TRANSACTION_setCameraDisabled = 44;
        static final int TRANSACTION_setCertInstallerPackage = 98;
        static final int TRANSACTION_setCrossProfileCalendarPackages = 274;
        static final int TRANSACTION_setCrossProfileCallerIdDisabled = 160;
        static final int TRANSACTION_setCrossProfileContactsSearchDisabled = 163;
        static final int TRANSACTION_setDefaultSmsApplication = 106;
        static final int TRANSACTION_setDelegatedScopes = 95;
        static final int TRANSACTION_setDeviceOwner = 66;
        static final int TRANSACTION_setDeviceOwnerLockScreenInfo = 81;
        static final int TRANSACTION_setDeviceProvisioningConfigApplied = 231;
        static final int TRANSACTION_setEndUserSessionMessage = 257;
        static final int TRANSACTION_setForceEphemeralUsers = 177;
        static final int TRANSACTION_setGlobalPrivateDns = 269;
        static final int TRANSACTION_setGlobalProxy = 37;
        static final int TRANSACTION_setGlobalSetting = 150;
        static final int TRANSACTION_setKeepUninstalledPackages = 195;
        static final int TRANSACTION_setKeyPairCertificate = 93;
        static final int TRANSACTION_setKeyguardDisabled = 184;
        static final int TRANSACTION_setKeyguardDisabledFeatures = 48;
        static final int TRANSACTION_setLockTaskFeatures = 148;
        static final int TRANSACTION_setLockTaskPackages = 145;
        static final int TRANSACTION_setLogoutEnabled = 251;
        static final int TRANSACTION_setLongSupportMessage = 203;
        static final int TRANSACTION_setMasterVolumeMuted = 155;
        static final int TRANSACTION_setMaximumFailedPasswordsForWipe = 28;
        static final int TRANSACTION_setMaximumTimeToLock = 31;
        static final int TRANSACTION_setMeteredDataDisabledPackages = 260;
        static final int TRANSACTION_setNetworkLoggingEnabled = 235;
        static final int TRANSACTION_setOrganizationColor = 208;
        static final int TRANSACTION_setOrganizationColorForUser = 209;
        static final int TRANSACTION_setOrganizationName = 212;
        static final int TRANSACTION_setOverrideApnsEnabled = 266;
        static final int TRANSACTION_setPackagesSuspended = 83;
        static final int TRANSACTION_setPasswordExpirationTimeout = 19;
        static final int TRANSACTION_setPasswordHistoryLength = 17;
        static final int TRANSACTION_setPasswordMinimumLength = 3;
        static final int TRANSACTION_setPasswordMinimumLetters = 9;
        static final int TRANSACTION_setPasswordMinimumLowerCase = 7;
        static final int TRANSACTION_setPasswordMinimumNonLetter = 15;
        static final int TRANSACTION_setPasswordMinimumNumeric = 11;
        static final int TRANSACTION_setPasswordMinimumSymbols = 13;
        static final int TRANSACTION_setPasswordMinimumUpperCase = 5;
        static final int TRANSACTION_setPasswordQuality = 1;
        static final int TRANSACTION_setPermissionGrantState = 191;
        static final int TRANSACTION_setPermissionPolicy = 189;
        static final int TRANSACTION_setPermittedAccessibilityServices = 118;
        static final int TRANSACTION_setPermittedCrossProfileNotificationListeners = 126;
        static final int TRANSACTION_setPermittedInputMethods = 122;
        static final int TRANSACTION_setProfileEnabled = 76;
        static final int TRANSACTION_setProfileName = 77;
        static final int TRANSACTION_setProfileOwner = 72;
        static final int TRANSACTION_setRecommendedGlobalProxy = 39;
        static final int TRANSACTION_setRequiredStrongAuthTimeout = 33;
        static final int TRANSACTION_setResetPasswordToken = 244;
        static final int TRANSACTION_setRestrictionsProvider = 112;
        static final int TRANSACTION_setScreenCaptureDisabled = 46;
        static final int TRANSACTION_setSecureSetting = 152;
        static final int TRANSACTION_setSecurityLoggingEnabled = 221;
        static final int TRANSACTION_setShortSupportMessage = 201;
        static final int TRANSACTION_setStartUserSessionMessage = 256;
        static final int TRANSACTION_setStatusBarDisabled = 185;
        static final int TRANSACTION_setStorageEncryption = 40;
        static final int TRANSACTION_setSystemSetting = 151;
        static final int TRANSACTION_setSystemUpdatePolicy = 181;
        static final int TRANSACTION_setTime = 153;
        static final int TRANSACTION_setTimeZone = 154;
        static final int TRANSACTION_setTrustAgentConfiguration = 170;
        static final int TRANSACTION_setUninstallBlocked = 158;
        static final int TRANSACTION_setUserIcon = 180;
        static final int TRANSACTION_setUserProvisioningState = 217;
        static final int TRANSACTION_setUserRestriction = 114;
        static final int TRANSACTION_startManagedQuickContact = 166;
        static final int TRANSACTION_startUserInBackground = 135;
        static final int TRANSACTION_startViewCalendarEventInManagedProfile = 280;
        static final int TRANSACTION_stopUser = 136;
        static final int TRANSACTION_switchUser = 134;
        static final int TRANSACTION_transferOwnership = 254;
        static final int TRANSACTION_uninstallCaCerts = 86;
        static final int TRANSACTION_uninstallPackageWithActiveAdmins = 228;
        static final int TRANSACTION_updateOverrideApn = 263;
        static final int TRANSACTION_wipeDataWithReason = 36;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IDevicePolicyManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IDevicePolicyManager)) {
                return new Proxy(obj);
            }
            return (IDevicePolicyManager) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "setPasswordQuality";
                case 2:
                    return "getPasswordQuality";
                case 3:
                    return "setPasswordMinimumLength";
                case 4:
                    return "getPasswordMinimumLength";
                case 5:
                    return "setPasswordMinimumUpperCase";
                case 6:
                    return "getPasswordMinimumUpperCase";
                case 7:
                    return "setPasswordMinimumLowerCase";
                case 8:
                    return "getPasswordMinimumLowerCase";
                case 9:
                    return "setPasswordMinimumLetters";
                case 10:
                    return "getPasswordMinimumLetters";
                case 11:
                    return "setPasswordMinimumNumeric";
                case 12:
                    return "getPasswordMinimumNumeric";
                case 13:
                    return "setPasswordMinimumSymbols";
                case 14:
                    return "getPasswordMinimumSymbols";
                case 15:
                    return "setPasswordMinimumNonLetter";
                case 16:
                    return "getPasswordMinimumNonLetter";
                case 17:
                    return "setPasswordHistoryLength";
                case 18:
                    return "getPasswordHistoryLength";
                case 19:
                    return "setPasswordExpirationTimeout";
                case 20:
                    return "getPasswordExpirationTimeout";
                case 21:
                    return "getPasswordExpiration";
                case 22:
                    return "isActivePasswordSufficient";
                case 23:
                    return "isProfileActivePasswordSufficientForParent";
                case 24:
                    return "getPasswordComplexity";
                case 25:
                    return "isUsingUnifiedPassword";
                case 26:
                    return "getCurrentFailedPasswordAttempts";
                case 27:
                    return "getProfileWithMinimumFailedPasswordsForWipe";
                case 28:
                    return "setMaximumFailedPasswordsForWipe";
                case 29:
                    return "getMaximumFailedPasswordsForWipe";
                case 30:
                    return "resetPassword";
                case 31:
                    return "setMaximumTimeToLock";
                case 32:
                    return "getMaximumTimeToLock";
                case 33:
                    return "setRequiredStrongAuthTimeout";
                case 34:
                    return "getRequiredStrongAuthTimeout";
                case 35:
                    return "lockNow";
                case 36:
                    return "wipeDataWithReason";
                case 37:
                    return "setGlobalProxy";
                case 38:
                    return "getGlobalProxyAdmin";
                case 39:
                    return "setRecommendedGlobalProxy";
                case 40:
                    return "setStorageEncryption";
                case 41:
                    return "getStorageEncryption";
                case 42:
                    return "getStorageEncryptionStatus";
                case 43:
                    return "requestBugreport";
                case 44:
                    return "setCameraDisabled";
                case 45:
                    return "getCameraDisabled";
                case 46:
                    return "setScreenCaptureDisabled";
                case 47:
                    return "getScreenCaptureDisabled";
                case 48:
                    return "setKeyguardDisabledFeatures";
                case 49:
                    return "getKeyguardDisabledFeatures";
                case 50:
                    return "setActiveAdmin";
                case 51:
                    return "isAdminActive";
                case 52:
                    return "getActiveAdmins";
                case 53:
                    return "packageHasActiveAdmins";
                case 54:
                    return "getRemoveWarning";
                case 55:
                    return "removeActiveAdmin";
                case 56:
                    return "forceRemoveActiveAdmin";
                case 57:
                    return "hasGrantedPolicy";
                case 58:
                    return "setActivePasswordState";
                case 59:
                    return "reportPasswordChanged";
                case 60:
                    return "reportFailedPasswordAttempt";
                case 61:
                    return "reportSuccessfulPasswordAttempt";
                case 62:
                    return "reportFailedBiometricAttempt";
                case 63:
                    return "reportSuccessfulBiometricAttempt";
                case 64:
                    return "reportKeyguardDismissed";
                case 65:
                    return "reportKeyguardSecured";
                case 66:
                    return "setDeviceOwner";
                case 67:
                    return "getDeviceOwnerComponent";
                case 68:
                    return "hasDeviceOwner";
                case 69:
                    return "getDeviceOwnerName";
                case 70:
                    return "clearDeviceOwner";
                case 71:
                    return "getDeviceOwnerUserId";
                case 72:
                    return "setProfileOwner";
                case 73:
                    return "getProfileOwnerAsUser";
                case 74:
                    return "getProfileOwner";
                case 75:
                    return "getProfileOwnerName";
                case 76:
                    return "setProfileEnabled";
                case 77:
                    return "setProfileName";
                case 78:
                    return "clearProfileOwner";
                case 79:
                    return "hasUserSetupCompleted";
                case 80:
                    return "checkDeviceIdentifierAccess";
                case 81:
                    return "setDeviceOwnerLockScreenInfo";
                case 82:
                    return "getDeviceOwnerLockScreenInfo";
                case 83:
                    return "setPackagesSuspended";
                case 84:
                    return "isPackageSuspended";
                case 85:
                    return "installCaCert";
                case 86:
                    return "uninstallCaCerts";
                case 87:
                    return "enforceCanManageCaCerts";
                case 88:
                    return "approveCaCert";
                case 89:
                    return "isCaCertApproved";
                case 90:
                    return "installKeyPair";
                case 91:
                    return "removeKeyPair";
                case 92:
                    return "generateKeyPair";
                case 93:
                    return "setKeyPairCertificate";
                case 94:
                    return "choosePrivateKeyAlias";
                case 95:
                    return "setDelegatedScopes";
                case 96:
                    return "getDelegatedScopes";
                case 97:
                    return "getDelegatePackages";
                case 98:
                    return "setCertInstallerPackage";
                case 99:
                    return "getCertInstallerPackage";
                case 100:
                    return "setAlwaysOnVpnPackage";
                case 101:
                    return "getAlwaysOnVpnPackage";
                case 102:
                    return "isAlwaysOnVpnLockdownEnabled";
                case 103:
                    return "getAlwaysOnVpnLockdownWhitelist";
                case 104:
                    return "addPersistentPreferredActivity";
                case 105:
                    return "clearPackagePersistentPreferredActivities";
                case 106:
                    return "setDefaultSmsApplication";
                case 107:
                    return "setApplicationRestrictions";
                case 108:
                    return "getApplicationRestrictions";
                case 109:
                    return "setApplicationRestrictionsManagingPackage";
                case 110:
                    return "getApplicationRestrictionsManagingPackage";
                case 111:
                    return "isCallerApplicationRestrictionsManagingPackage";
                case 112:
                    return "setRestrictionsProvider";
                case 113:
                    return "getRestrictionsProvider";
                case 114:
                    return "setUserRestriction";
                case 115:
                    return "getUserRestrictions";
                case 116:
                    return "addCrossProfileIntentFilter";
                case 117:
                    return "clearCrossProfileIntentFilters";
                case 118:
                    return "setPermittedAccessibilityServices";
                case 119:
                    return "getPermittedAccessibilityServices";
                case 120:
                    return "getPermittedAccessibilityServicesForUser";
                case 121:
                    return "isAccessibilityServicePermittedByAdmin";
                case 122:
                    return "setPermittedInputMethods";
                case 123:
                    return "getPermittedInputMethods";
                case 124:
                    return "getPermittedInputMethodsForCurrentUser";
                case 125:
                    return "isInputMethodPermittedByAdmin";
                case 126:
                    return "setPermittedCrossProfileNotificationListeners";
                case 127:
                    return "getPermittedCrossProfileNotificationListeners";
                case 128:
                    return "isNotificationListenerServicePermitted";
                case 129:
                    return "createAdminSupportIntent";
                case 130:
                    return "setApplicationHidden";
                case 131:
                    return "isApplicationHidden";
                case 132:
                    return "createAndManageUser";
                case 133:
                    return "removeUser";
                case 134:
                    return "switchUser";
                case 135:
                    return "startUserInBackground";
                case 136:
                    return "stopUser";
                case 137:
                    return "logoutUser";
                case 138:
                    return "getSecondaryUsers";
                case 139:
                    return "enableSystemApp";
                case 140:
                    return "enableSystemAppWithIntent";
                case 141:
                    return "installExistingPackage";
                case 142:
                    return "setAccountManagementDisabled";
                case 143:
                    return "getAccountTypesWithManagementDisabled";
                case 144:
                    return "getAccountTypesWithManagementDisabledAsUser";
                case 145:
                    return "setLockTaskPackages";
                case 146:
                    return "getLockTaskPackages";
                case 147:
                    return "isLockTaskPermitted";
                case 148:
                    return "setLockTaskFeatures";
                case 149:
                    return "getLockTaskFeatures";
                case 150:
                    return "setGlobalSetting";
                case 151:
                    return "setSystemSetting";
                case 152:
                    return "setSecureSetting";
                case 153:
                    return "setTime";
                case 154:
                    return "setTimeZone";
                case 155:
                    return "setMasterVolumeMuted";
                case 156:
                    return "isMasterVolumeMuted";
                case 157:
                    return "notifyLockTaskModeChanged";
                case 158:
                    return "setUninstallBlocked";
                case 159:
                    return "isUninstallBlocked";
                case 160:
                    return "setCrossProfileCallerIdDisabled";
                case 161:
                    return "getCrossProfileCallerIdDisabled";
                case 162:
                    return "getCrossProfileCallerIdDisabledForUser";
                case 163:
                    return "setCrossProfileContactsSearchDisabled";
                case 164:
                    return "getCrossProfileContactsSearchDisabled";
                case 165:
                    return "getCrossProfileContactsSearchDisabledForUser";
                case 166:
                    return "startManagedQuickContact";
                case 167:
                    return "setBluetoothContactSharingDisabled";
                case 168:
                    return "getBluetoothContactSharingDisabled";
                case 169:
                    return "getBluetoothContactSharingDisabledForUser";
                case 170:
                    return "setTrustAgentConfiguration";
                case 171:
                    return "getTrustAgentConfiguration";
                case 172:
                    return "addCrossProfileWidgetProvider";
                case 173:
                    return "removeCrossProfileWidgetProvider";
                case 174:
                    return "getCrossProfileWidgetProviders";
                case 175:
                    return "setAutoTimeRequired";
                case 176:
                    return "getAutoTimeRequired";
                case 177:
                    return "setForceEphemeralUsers";
                case 178:
                    return "getForceEphemeralUsers";
                case 179:
                    return "isRemovingAdmin";
                case 180:
                    return "setUserIcon";
                case 181:
                    return "setSystemUpdatePolicy";
                case 182:
                    return "getSystemUpdatePolicy";
                case 183:
                    return "clearSystemUpdatePolicyFreezePeriodRecord";
                case 184:
                    return "setKeyguardDisabled";
                case 185:
                    return "setStatusBarDisabled";
                case 186:
                    return "getDoNotAskCredentialsOnBoot";
                case 187:
                    return "notifyPendingSystemUpdate";
                case 188:
                    return "getPendingSystemUpdate";
                case 189:
                    return "setPermissionPolicy";
                case 190:
                    return "getPermissionPolicy";
                case 191:
                    return "setPermissionGrantState";
                case 192:
                    return "getPermissionGrantState";
                case 193:
                    return "isProvisioningAllowed";
                case 194:
                    return "checkProvisioningPreCondition";
                case 195:
                    return "setKeepUninstalledPackages";
                case 196:
                    return "getKeepUninstalledPackages";
                case 197:
                    return "isManagedProfile";
                case 198:
                    return "isSystemOnlyUser";
                case 199:
                    return "getWifiMacAddress";
                case 200:
                    return "reboot";
                case 201:
                    return "setShortSupportMessage";
                case 202:
                    return "getShortSupportMessage";
                case 203:
                    return "setLongSupportMessage";
                case 204:
                    return "getLongSupportMessage";
                case 205:
                    return "getShortSupportMessageForUser";
                case 206:
                    return "getLongSupportMessageForUser";
                case 207:
                    return "isSeparateProfileChallengeAllowed";
                case 208:
                    return "setOrganizationColor";
                case 209:
                    return "setOrganizationColorForUser";
                case 210:
                    return "getOrganizationColor";
                case 211:
                    return "getOrganizationColorForUser";
                case 212:
                    return "setOrganizationName";
                case 213:
                    return "getOrganizationName";
                case 214:
                    return "getDeviceOwnerOrganizationName";
                case 215:
                    return "getOrganizationNameForUser";
                case 216:
                    return "getUserProvisioningState";
                case 217:
                    return "setUserProvisioningState";
                case 218:
                    return "setAffiliationIds";
                case 219:
                    return "getAffiliationIds";
                case 220:
                    return "isAffiliatedUser";
                case 221:
                    return "setSecurityLoggingEnabled";
                case 222:
                    return "isSecurityLoggingEnabled";
                case 223:
                    return "retrieveSecurityLogs";
                case 224:
                    return "retrievePreRebootSecurityLogs";
                case 225:
                    return "forceNetworkLogs";
                case 226:
                    return "forceSecurityLogs";
                case 227:
                    return "isUninstallInQueue";
                case 228:
                    return "uninstallPackageWithActiveAdmins";
                case 229:
                    return "isDeviceProvisioned";
                case 230:
                    return "isDeviceProvisioningConfigApplied";
                case 231:
                    return "setDeviceProvisioningConfigApplied";
                case 232:
                    return "forceUpdateUserSetupComplete";
                case 233:
                    return "setBackupServiceEnabled";
                case 234:
                    return "isBackupServiceEnabled";
                case 235:
                    return "setNetworkLoggingEnabled";
                case 236:
                    return "isNetworkLoggingEnabled";
                case 237:
                    return "retrieveNetworkLogs";
                case 238:
                    return "bindDeviceAdminServiceAsUser";
                case 239:
                    return "getBindDeviceAdminTargetUsers";
                case 240:
                    return "isEphemeralUser";
                case 241:
                    return "getLastSecurityLogRetrievalTime";
                case 242:
                    return "getLastBugReportRequestTime";
                case 243:
                    return "getLastNetworkLogRetrievalTime";
                case 244:
                    return "setResetPasswordToken";
                case 245:
                    return "clearResetPasswordToken";
                case 246:
                    return "isResetPasswordTokenActive";
                case 247:
                    return "resetPasswordWithToken";
                case 248:
                    return "isCurrentInputMethodSetByOwner";
                case 249:
                    return "getOwnerInstalledCaCerts";
                case 250:
                    return "clearApplicationUserData";
                case 251:
                    return "setLogoutEnabled";
                case 252:
                    return "isLogoutEnabled";
                case 253:
                    return "getDisallowedSystemApps";
                case 254:
                    return "transferOwnership";
                case 255:
                    return "getTransferOwnershipBundle";
                case 256:
                    return "setStartUserSessionMessage";
                case 257:
                    return "setEndUserSessionMessage";
                case 258:
                    return "getStartUserSessionMessage";
                case 259:
                    return "getEndUserSessionMessage";
                case 260:
                    return "setMeteredDataDisabledPackages";
                case 261:
                    return "getMeteredDataDisabledPackages";
                case 262:
                    return "addOverrideApn";
                case 263:
                    return "updateOverrideApn";
                case 264:
                    return "removeOverrideApn";
                case 265:
                    return "getOverrideApns";
                case 266:
                    return "setOverrideApnsEnabled";
                case 267:
                    return "isOverrideApnEnabled";
                case 268:
                    return "isMeteredDataDisabledPackageForUser";
                case 269:
                    return "setGlobalPrivateDns";
                case 270:
                    return "getGlobalPrivateDnsMode";
                case 271:
                    return "getGlobalPrivateDnsHost";
                case 272:
                    return "grantDeviceIdsAccessToProfileOwner";
                case 273:
                    return "installUpdateFromFile";
                case 274:
                    return "setCrossProfileCalendarPackages";
                case 275:
                    return "getCrossProfileCalendarPackages";
                case 276:
                    return "isPackageAllowedToAccessCalendarForUser";
                case 277:
                    return "getCrossProfileCalendarPackagesForUser";
                case 278:
                    return "isManagedKiosk";
                case 279:
                    return "isUnattendedManagedKiosk";
                case 280:
                    return "startViewCalendarEventInManagedProfile";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v0, resolved type: android.os.Parcel} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v2, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v6, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v10, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v14, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v18, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v22, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v26, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v30, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v34, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v38, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v42, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v46, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v50, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v54, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v58, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v62, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v66, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v70, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v74, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v78, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v82, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v89, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v95, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v99, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v104, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v108, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v112, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v116, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v122, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v127, resolved type: android.net.ProxyInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v131, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v135, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v140, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v144, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v148, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v152, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v156, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v160, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v164, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v168, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v172, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v178, resolved type: android.os.RemoteCallback} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v182, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v186, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v190, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v194, resolved type: android.app.admin.PasswordMetrics} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v205, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v217, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v224, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v228, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v232, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v243, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v247, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v251, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v255, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v259, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v266, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v282, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v286, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v290, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v294, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v298, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v302, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v306, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v310, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v314, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v318, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v322, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v326, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v330, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v334, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v338, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v342, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v347, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v352, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v356, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v364, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v368, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v372, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v377, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v381, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v385, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v390, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v394, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v398, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v404, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v408, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v418, resolved type: android.os.UserHandle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v422, resolved type: android.os.UserHandle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v426, resolved type: android.os.UserHandle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v430, resolved type: android.os.UserHandle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v434, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v438, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v442, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v450, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v454, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v460, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v464, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v469, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v473, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v477, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v481, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v485, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v489, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v493, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v497, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v501, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v507, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v511, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v515, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v519, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v524, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v528, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v538, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v542, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v547, resolved type: android.os.PersistableBundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v551, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v555, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v559, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v563, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v567, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v572, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v576, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v580, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v584, resolved type: android.graphics.Bitmap} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v588, resolved type: android.app.admin.SystemUpdatePolicy} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v593, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v597, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v602, resolved type: android.app.admin.SystemUpdateInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v606, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v610, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v614, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v619, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v625, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v629, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v633, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v637, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v641, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v645, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v653, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v661, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v665, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v669, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v674, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v679, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v688, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v696, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v700, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v705, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v709, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v713, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v717, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v727, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v731, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v735, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v739, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v743, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v748, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v752, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v759, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v763, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v767, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v771, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v776, resolved type: android.os.UserHandle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v780, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v784, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v789, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v793, resolved type: android.os.PersistableBundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v806, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v810, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v814, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v818, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v822, resolved type: android.telephony.data.ApnSetting} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v826, resolved type: android.telephony.data.ApnSetting} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v830, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v834, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v838, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v842, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v846, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v850, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v854, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v858, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v862, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v870, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v874, resolved type: android.content.ComponentName} */
        /* JADX WARNING: type inference failed for: r0v1 */
        /* JADX WARNING: type inference failed for: r0v238, types: [java.lang.CharSequence] */
        /* JADX WARNING: type inference failed for: r0v270 */
        /* JADX WARNING: type inference failed for: r0v278, types: [android.net.Uri] */
        /* JADX WARNING: type inference failed for: r0v360, types: [android.content.IntentFilter] */
        /* JADX WARNING: type inference failed for: r0v412 */
        /* JADX WARNING: type inference failed for: r0v446, types: [android.content.Intent] */
        /* JADX WARNING: type inference failed for: r0v533 */
        /* JADX WARNING: type inference failed for: r0v649, types: [java.lang.CharSequence] */
        /* JADX WARNING: type inference failed for: r0v657, types: [java.lang.CharSequence] */
        /* JADX WARNING: type inference failed for: r0v684, types: [java.lang.CharSequence] */
        /* JADX WARNING: type inference failed for: r0v798, types: [java.lang.CharSequence] */
        /* JADX WARNING: type inference failed for: r0v802, types: [java.lang.CharSequence] */
        /* JADX WARNING: type inference failed for: r0v866, types: [android.os.ParcelFileDescriptor] */
        /* JADX WARNING: type inference failed for: r0v884 */
        /* JADX WARNING: type inference failed for: r0v885 */
        /* JADX WARNING: type inference failed for: r0v886 */
        /* JADX WARNING: type inference failed for: r0v887 */
        /* JADX WARNING: type inference failed for: r0v888 */
        /* JADX WARNING: type inference failed for: r0v889 */
        /* JADX WARNING: type inference failed for: r0v890 */
        /* JADX WARNING: type inference failed for: r0v891 */
        /* JADX WARNING: type inference failed for: r0v892 */
        /* JADX WARNING: type inference failed for: r0v893 */
        /* JADX WARNING: type inference failed for: r0v894 */
        /* JADX WARNING: type inference failed for: r0v895 */
        /* JADX WARNING: type inference failed for: r0v896 */
        /* JADX WARNING: type inference failed for: r0v897 */
        /* JADX WARNING: type inference failed for: r0v898 */
        /* JADX WARNING: type inference failed for: r0v899 */
        /* JADX WARNING: type inference failed for: r0v900 */
        /* JADX WARNING: type inference failed for: r0v901 */
        /* JADX WARNING: type inference failed for: r0v902 */
        /* JADX WARNING: type inference failed for: r0v903 */
        /* JADX WARNING: type inference failed for: r0v904 */
        /* JADX WARNING: type inference failed for: r0v905 */
        /* JADX WARNING: type inference failed for: r0v906 */
        /* JADX WARNING: type inference failed for: r0v907 */
        /* JADX WARNING: type inference failed for: r0v908 */
        /* JADX WARNING: type inference failed for: r0v909 */
        /* JADX WARNING: type inference failed for: r0v910 */
        /* JADX WARNING: type inference failed for: r0v911 */
        /* JADX WARNING: type inference failed for: r0v912 */
        /* JADX WARNING: type inference failed for: r0v913 */
        /* JADX WARNING: type inference failed for: r0v914 */
        /* JADX WARNING: type inference failed for: r0v915 */
        /* JADX WARNING: type inference failed for: r0v916 */
        /* JADX WARNING: type inference failed for: r0v917 */
        /* JADX WARNING: type inference failed for: r0v918 */
        /* JADX WARNING: type inference failed for: r0v919 */
        /* JADX WARNING: type inference failed for: r0v920 */
        /* JADX WARNING: type inference failed for: r0v921 */
        /* JADX WARNING: type inference failed for: r0v922 */
        /* JADX WARNING: type inference failed for: r0v923 */
        /* JADX WARNING: type inference failed for: r0v924 */
        /* JADX WARNING: type inference failed for: r0v925 */
        /* JADX WARNING: type inference failed for: r0v926 */
        /* JADX WARNING: type inference failed for: r0v927 */
        /* JADX WARNING: type inference failed for: r0v928 */
        /* JADX WARNING: type inference failed for: r0v929 */
        /* JADX WARNING: type inference failed for: r0v930 */
        /* JADX WARNING: type inference failed for: r0v931 */
        /* JADX WARNING: type inference failed for: r0v932 */
        /* JADX WARNING: type inference failed for: r0v933 */
        /* JADX WARNING: type inference failed for: r0v934 */
        /* JADX WARNING: type inference failed for: r0v935 */
        /* JADX WARNING: type inference failed for: r0v936 */
        /* JADX WARNING: type inference failed for: r0v937 */
        /* JADX WARNING: type inference failed for: r0v938 */
        /* JADX WARNING: type inference failed for: r0v939 */
        /* JADX WARNING: type inference failed for: r0v940 */
        /* JADX WARNING: type inference failed for: r0v941 */
        /* JADX WARNING: type inference failed for: r0v942 */
        /* JADX WARNING: type inference failed for: r0v943 */
        /* JADX WARNING: type inference failed for: r0v944 */
        /* JADX WARNING: type inference failed for: r0v945 */
        /* JADX WARNING: type inference failed for: r0v946 */
        /* JADX WARNING: type inference failed for: r0v947 */
        /* JADX WARNING: type inference failed for: r0v948 */
        /* JADX WARNING: type inference failed for: r0v949 */
        /* JADX WARNING: type inference failed for: r0v950 */
        /* JADX WARNING: type inference failed for: r0v951 */
        /* JADX WARNING: type inference failed for: r0v952 */
        /* JADX WARNING: type inference failed for: r0v953 */
        /* JADX WARNING: type inference failed for: r0v954 */
        /* JADX WARNING: type inference failed for: r0v955 */
        /* JADX WARNING: type inference failed for: r0v956 */
        /* JADX WARNING: type inference failed for: r0v957 */
        /* JADX WARNING: type inference failed for: r0v958 */
        /* JADX WARNING: type inference failed for: r0v959 */
        /* JADX WARNING: type inference failed for: r0v960 */
        /* JADX WARNING: type inference failed for: r0v961 */
        /* JADX WARNING: type inference failed for: r0v962 */
        /* JADX WARNING: type inference failed for: r0v963 */
        /* JADX WARNING: type inference failed for: r0v964 */
        /* JADX WARNING: type inference failed for: r0v965 */
        /* JADX WARNING: type inference failed for: r0v966 */
        /* JADX WARNING: type inference failed for: r0v967 */
        /* JADX WARNING: type inference failed for: r0v968 */
        /* JADX WARNING: type inference failed for: r0v969 */
        /* JADX WARNING: type inference failed for: r0v970 */
        /* JADX WARNING: type inference failed for: r0v971 */
        /* JADX WARNING: type inference failed for: r0v972 */
        /* JADX WARNING: type inference failed for: r0v973 */
        /* JADX WARNING: type inference failed for: r0v974 */
        /* JADX WARNING: type inference failed for: r0v975 */
        /* JADX WARNING: type inference failed for: r0v976 */
        /* JADX WARNING: type inference failed for: r0v977 */
        /* JADX WARNING: type inference failed for: r0v978 */
        /* JADX WARNING: type inference failed for: r0v979 */
        /* JADX WARNING: type inference failed for: r0v980 */
        /* JADX WARNING: type inference failed for: r0v981 */
        /* JADX WARNING: type inference failed for: r0v982 */
        /* JADX WARNING: type inference failed for: r0v983 */
        /* JADX WARNING: type inference failed for: r0v984 */
        /* JADX WARNING: type inference failed for: r0v985 */
        /* JADX WARNING: type inference failed for: r0v986 */
        /* JADX WARNING: type inference failed for: r0v987 */
        /* JADX WARNING: type inference failed for: r0v988 */
        /* JADX WARNING: type inference failed for: r0v989 */
        /* JADX WARNING: type inference failed for: r0v990 */
        /* JADX WARNING: type inference failed for: r0v991 */
        /* JADX WARNING: type inference failed for: r0v992 */
        /* JADX WARNING: type inference failed for: r0v993 */
        /* JADX WARNING: type inference failed for: r0v994 */
        /* JADX WARNING: type inference failed for: r0v995 */
        /* JADX WARNING: type inference failed for: r0v996 */
        /* JADX WARNING: type inference failed for: r0v997 */
        /* JADX WARNING: type inference failed for: r0v998 */
        /* JADX WARNING: type inference failed for: r0v999 */
        /* JADX WARNING: type inference failed for: r0v1000 */
        /* JADX WARNING: type inference failed for: r0v1001 */
        /* JADX WARNING: type inference failed for: r0v1002 */
        /* JADX WARNING: type inference failed for: r0v1003 */
        /* JADX WARNING: type inference failed for: r0v1004 */
        /* JADX WARNING: type inference failed for: r0v1005 */
        /* JADX WARNING: type inference failed for: r0v1006 */
        /* JADX WARNING: type inference failed for: r0v1007 */
        /* JADX WARNING: type inference failed for: r0v1008 */
        /* JADX WARNING: type inference failed for: r0v1009 */
        /* JADX WARNING: type inference failed for: r0v1010 */
        /* JADX WARNING: type inference failed for: r0v1011 */
        /* JADX WARNING: type inference failed for: r0v1012 */
        /* JADX WARNING: type inference failed for: r0v1013 */
        /* JADX WARNING: type inference failed for: r0v1014 */
        /* JADX WARNING: type inference failed for: r0v1015 */
        /* JADX WARNING: type inference failed for: r0v1016 */
        /* JADX WARNING: type inference failed for: r0v1017 */
        /* JADX WARNING: type inference failed for: r0v1018 */
        /* JADX WARNING: type inference failed for: r0v1019 */
        /* JADX WARNING: type inference failed for: r0v1020 */
        /* JADX WARNING: type inference failed for: r0v1021 */
        /* JADX WARNING: type inference failed for: r0v1022 */
        /* JADX WARNING: type inference failed for: r0v1023 */
        /* JADX WARNING: type inference failed for: r0v1024 */
        /* JADX WARNING: type inference failed for: r0v1025 */
        /* JADX WARNING: type inference failed for: r0v1026 */
        /* JADX WARNING: type inference failed for: r0v1027 */
        /* JADX WARNING: type inference failed for: r0v1028 */
        /* JADX WARNING: type inference failed for: r0v1029 */
        /* JADX WARNING: type inference failed for: r0v1030 */
        /* JADX WARNING: type inference failed for: r0v1031 */
        /* JADX WARNING: type inference failed for: r0v1032 */
        /* JADX WARNING: type inference failed for: r0v1033 */
        /* JADX WARNING: type inference failed for: r0v1034 */
        /* JADX WARNING: type inference failed for: r0v1035 */
        /* JADX WARNING: type inference failed for: r0v1036 */
        /* JADX WARNING: type inference failed for: r0v1037 */
        /* JADX WARNING: type inference failed for: r0v1038 */
        /* JADX WARNING: type inference failed for: r0v1039 */
        /* JADX WARNING: type inference failed for: r0v1040 */
        /* JADX WARNING: type inference failed for: r0v1041 */
        /* JADX WARNING: type inference failed for: r0v1042 */
        /* JADX WARNING: type inference failed for: r0v1043 */
        /* JADX WARNING: type inference failed for: r0v1044 */
        /* JADX WARNING: type inference failed for: r0v1045 */
        /* JADX WARNING: type inference failed for: r0v1046 */
        /* JADX WARNING: type inference failed for: r0v1047 */
        /* JADX WARNING: type inference failed for: r0v1048 */
        /* JADX WARNING: type inference failed for: r0v1049 */
        /* JADX WARNING: type inference failed for: r0v1050 */
        /* JADX WARNING: type inference failed for: r0v1051 */
        /* JADX WARNING: type inference failed for: r0v1052 */
        /* JADX WARNING: type inference failed for: r0v1053 */
        /* JADX WARNING: type inference failed for: r0v1054 */
        /* JADX WARNING: type inference failed for: r0v1055 */
        /* JADX WARNING: type inference failed for: r0v1056 */
        /* JADX WARNING: type inference failed for: r0v1057 */
        /* JADX WARNING: type inference failed for: r0v1058 */
        /* JADX WARNING: type inference failed for: r0v1059 */
        /* JADX WARNING: type inference failed for: r0v1060 */
        /* JADX WARNING: type inference failed for: r0v1061 */
        /* JADX WARNING: type inference failed for: r0v1062 */
        /* JADX WARNING: type inference failed for: r0v1063 */
        /* JADX WARNING: type inference failed for: r0v1064 */
        /* JADX WARNING: type inference failed for: r0v1065 */
        /* JADX WARNING: type inference failed for: r0v1066 */
        /* JADX WARNING: type inference failed for: r0v1067 */
        /* JADX WARNING: type inference failed for: r0v1068 */
        /* JADX WARNING: type inference failed for: r0v1069 */
        /* JADX WARNING: type inference failed for: r0v1070 */
        /* JADX WARNING: type inference failed for: r0v1071 */
        /* JADX WARNING: type inference failed for: r0v1072 */
        /* JADX WARNING: type inference failed for: r0v1073 */
        /* JADX WARNING: type inference failed for: r0v1074 */
        /* JADX WARNING: type inference failed for: r0v1075 */
        /* JADX WARNING: type inference failed for: r0v1076 */
        /* JADX WARNING: type inference failed for: r0v1077 */
        /* JADX WARNING: type inference failed for: r0v1078 */
        /* JADX WARNING: type inference failed for: r0v1079 */
        /* JADX WARNING: type inference failed for: r0v1080 */
        /* JADX WARNING: type inference failed for: r0v1081 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r21, android.os.Parcel r22, android.os.Parcel r23, int r24) throws android.os.RemoteException {
            /*
                r20 = this;
                r8 = r20
                r9 = r21
                r10 = r22
                r11 = r23
                java.lang.String r12 = "android.app.admin.IDevicePolicyManager"
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r13 = 1
                if (r9 == r0) goto L_0x22c1
                r6 = 0
                r0 = 0
                switch(r9) {
                    case 1: goto L_0x229a;
                    case 2: goto L_0x226f;
                    case 3: goto L_0x2248;
                    case 4: goto L_0x221d;
                    case 5: goto L_0x21f6;
                    case 6: goto L_0x21cb;
                    case 7: goto L_0x21a4;
                    case 8: goto L_0x2179;
                    case 9: goto L_0x2152;
                    case 10: goto L_0x2127;
                    case 11: goto L_0x2100;
                    case 12: goto L_0x20d5;
                    case 13: goto L_0x20ae;
                    case 14: goto L_0x2083;
                    case 15: goto L_0x205c;
                    case 16: goto L_0x2031;
                    case 17: goto L_0x200a;
                    case 18: goto L_0x1fdf;
                    case 19: goto L_0x1fb8;
                    case 20: goto L_0x1f8d;
                    case 21: goto L_0x1f62;
                    case 22: goto L_0x1f47;
                    case 23: goto L_0x1f35;
                    case 24: goto L_0x1f27;
                    case 25: goto L_0x1f09;
                    case 26: goto L_0x1eee;
                    case 27: goto L_0x1ed3;
                    case 28: goto L_0x1eac;
                    case 29: goto L_0x1e81;
                    case 30: goto L_0x1e6b;
                    case 31: goto L_0x1e44;
                    case 32: goto L_0x1e19;
                    case 33: goto L_0x1df2;
                    case 34: goto L_0x1dc7;
                    case 35: goto L_0x1db0;
                    case 36: goto L_0x1d9e;
                    case 37: goto L_0x1d6f;
                    case 38: goto L_0x1d54;
                    case 39: goto L_0x1d2a;
                    case 40: goto L_0x1d03;
                    case 41: goto L_0x1ce1;
                    case 42: goto L_0x1ccb;
                    case 43: goto L_0x1cad;
                    case 44: goto L_0x1c8a;
                    case 45: goto L_0x1c68;
                    case 46: goto L_0x1c45;
                    case 47: goto L_0x1c23;
                    case 48: goto L_0x1bfc;
                    case 49: goto L_0x1bd1;
                    case 50: goto L_0x1baa;
                    case 51: goto L_0x1b88;
                    case 52: goto L_0x1b76;
                    case 53: goto L_0x1b60;
                    case 54: goto L_0x1b32;
                    case 55: goto L_0x1b14;
                    case 56: goto L_0x1af6;
                    case 57: goto L_0x1ad0;
                    case 58: goto L_0x1ab2;
                    case 59: goto L_0x1aa4;
                    case 60: goto L_0x1a96;
                    case 61: goto L_0x1a88;
                    case 62: goto L_0x1a7a;
                    case 63: goto L_0x1a6c;
                    case 64: goto L_0x1a5e;
                    case 65: goto L_0x1a50;
                    case 66: goto L_0x1a2a;
                    case 67: goto L_0x1a0a;
                    case 68: goto L_0x19fc;
                    case 69: goto L_0x19ee;
                    case 70: goto L_0x19e0;
                    case 71: goto L_0x19d2;
                    case 72: goto L_0x19ac;
                    case 73: goto L_0x1991;
                    case 74: goto L_0x1976;
                    case 75: goto L_0x1964;
                    case 76: goto L_0x194a;
                    case 77: goto L_0x192c;
                    case 78: goto L_0x1912;
                    case 79: goto L_0x1904;
                    case 80: goto L_0x18ea;
                    case 81: goto L_0x18c0;
                    case 82: goto L_0x18a9;
                    case 83: goto L_0x187a;
                    case 84: goto L_0x1854;
                    case 85: goto L_0x182e;
                    case 86: goto L_0x180c;
                    case 87: goto L_0x17ee;
                    case 88: goto L_0x17cf;
                    case 89: goto L_0x17b9;
                    case 90: goto L_0x17b4;
                    case 91: goto L_0x178e;
                    case 92: goto L_0x173d;
                    case 93: goto L_0x1738;
                    case 94: goto L_0x1712;
                    case 95: goto L_0x16f0;
                    case 96: goto L_0x16ce;
                    case 97: goto L_0x16ac;
                    case 98: goto L_0x168e;
                    case 99: goto L_0x1670;
                    case 100: goto L_0x1641;
                    case 101: goto L_0x1623;
                    case 102: goto L_0x1605;
                    case 103: goto L_0x15e7;
                    case 104: goto L_0x15ad;
                    case 105: goto L_0x158f;
                    case 106: goto L_0x1571;
                    case 107: goto L_0x153f;
                    case 108: goto L_0x1510;
                    case 109: goto L_0x14ee;
                    case 110: goto L_0x14d0;
                    case 111: goto L_0x14be;
                    case 112: goto L_0x1494;
                    case 113: goto L_0x1479;
                    case 114: goto L_0x1452;
                    case 115: goto L_0x142b;
                    case 116: goto L_0x13fd;
                    case 117: goto L_0x13e3;
                    case 118: goto L_0x13b9;
                    case 119: goto L_0x139b;
                    case 120: goto L_0x1389;
                    case 121: goto L_0x1363;
                    case 122: goto L_0x1339;
                    case 123: goto L_0x131b;
                    case 124: goto L_0x130d;
                    case 125: goto L_0x12e7;
                    case 126: goto L_0x12c5;
                    case 127: goto L_0x12a7;
                    case 128: goto L_0x1291;
                    case 129: goto L_0x1276;
                    case 130: goto L_0x1247;
                    case 131: goto L_0x1221;
                    case 132: goto L_0x11cc;
                    case 133: goto L_0x119e;
                    case 134: goto L_0x1170;
                    case 135: goto L_0x1142;
                    case 136: goto L_0x1114;
                    case 137: goto L_0x10f6;
                    case 138: goto L_0x10d8;
                    case 139: goto L_0x10b6;
                    case 140: goto L_0x1084;
                    case 141: goto L_0x105e;
                    case 142: goto L_0x1037;
                    case 143: goto L_0x1029;
                    case 144: goto L_0x1017;
                    case 145: goto L_0x0ff9;
                    case 146: goto L_0x0fdb;
                    case 147: goto L_0x0fc9;
                    case 148: goto L_0x0fab;
                    case 149: goto L_0x0f8d;
                    case 150: goto L_0x0f6b;
                    case 151: goto L_0x0f49;
                    case 152: goto L_0x0f27;
                    case 153: goto L_0x0f05;
                    case 154: goto L_0x0ee3;
                    case 155: goto L_0x0ec0;
                    case 156: goto L_0x0ea2;
                    case 157: goto L_0x0e87;
                    case 158: goto L_0x0e5c;
                    case 159: goto L_0x0e3a;
                    case 160: goto L_0x0e17;
                    case 161: goto L_0x0df9;
                    case 162: goto L_0x0de7;
                    case 163: goto L_0x0dc4;
                    case 164: goto L_0x0da6;
                    case 165: goto L_0x0d94;
                    case 166: goto L_0x0d5e;
                    case 167: goto L_0x0d3b;
                    case 168: goto L_0x0d1d;
                    case 169: goto L_0x0d0b;
                    case 170: goto L_0x0cc8;
                    case 171: goto L_0x0c8d;
                    case 172: goto L_0x0c6b;
                    case 173: goto L_0x0c49;
                    case 174: goto L_0x0c2b;
                    case 175: goto L_0x0c08;
                    case 176: goto L_0x0bfa;
                    case 177: goto L_0x0bd7;
                    case 178: goto L_0x0bb9;
                    case 179: goto L_0x0b97;
                    case 180: goto L_0x0b6d;
                    case 181: goto L_0x0b43;
                    case 182: goto L_0x0b2c;
                    case 183: goto L_0x0b22;
                    case 184: goto L_0x0afb;
                    case 185: goto L_0x0ad4;
                    case 186: goto L_0x0ac6;
                    case 187: goto L_0x0aac;
                    case 188: goto L_0x0a85;
                    case 189: goto L_0x0a63;
                    case 190: goto L_0x0a45;
                    case 191: goto L_0x0a40;
                    case 192: goto L_0x0a16;
                    case 193: goto L_0x0a00;
                    case 194: goto L_0x09ea;
                    case 195: goto L_0x09c8;
                    case 196: goto L_0x09a6;
                    case 197: goto L_0x0988;
                    case 198: goto L_0x096a;
                    case 199: goto L_0x094c;
                    case 200: goto L_0x0932;
                    case 201: goto L_0x0908;
                    case 202: goto L_0x08e1;
                    case 203: goto L_0x08b7;
                    case 204: goto L_0x0890;
                    case 205: goto L_0x0865;
                    case 206: goto L_0x083a;
                    case 207: goto L_0x0828;
                    case 208: goto L_0x080a;
                    case 209: goto L_0x07f8;
                    case 210: goto L_0x07da;
                    case 211: goto L_0x07c8;
                    case 212: goto L_0x079e;
                    case 213: goto L_0x0777;
                    case 214: goto L_0x0760;
                    case 215: goto L_0x0745;
                    case 216: goto L_0x0737;
                    case 217: goto L_0x0725;
                    case 218: goto L_0x0707;
                    case 219: goto L_0x06e9;
                    case 220: goto L_0x06db;
                    case 221: goto L_0x06b8;
                    case 222: goto L_0x069a;
                    case 223: goto L_0x0673;
                    case 224: goto L_0x064c;
                    case 225: goto L_0x063e;
                    case 226: goto L_0x0630;
                    case 227: goto L_0x061e;
                    case 228: goto L_0x0610;
                    case 229: goto L_0x0602;
                    case 230: goto L_0x05f4;
                    case 231: goto L_0x05ea;
                    case 232: goto L_0x05e0;
                    case 233: goto L_0x05bd;
                    case 234: goto L_0x059f;
                    case 235: goto L_0x0578;
                    case 236: goto L_0x0556;
                    case 237: goto L_0x0530;
                    case 238: goto L_0x052b;
                    case 239: goto L_0x050d;
                    case 240: goto L_0x04ef;
                    case 241: goto L_0x04e1;
                    case 242: goto L_0x04d3;
                    case 243: goto L_0x04c5;
                    case 244: goto L_0x04a3;
                    case 245: goto L_0x0485;
                    case 246: goto L_0x0467;
                    case 247: goto L_0x043d;
                    case 248: goto L_0x042f;
                    case 249: goto L_0x0408;
                    case 250: goto L_0x03e2;
                    case 251: goto L_0x03bf;
                    case 252: goto L_0x03b1;
                    case 253: goto L_0x038b;
                    case 254: goto L_0x0351;
                    case 255: goto L_0x033a;
                    case 256: goto L_0x0310;
                    case 257: goto L_0x02e6;
                    case 258: goto L_0x02bf;
                    case 259: goto L_0x0298;
                    case 260: goto L_0x0276;
                    case 261: goto L_0x0258;
                    case 262: goto L_0x022a;
                    case 263: goto L_0x01f8;
                    case 264: goto L_0x01d6;
                    case 265: goto L_0x01b8;
                    case 266: goto L_0x0195;
                    case 267: goto L_0x0177;
                    case 268: goto L_0x0151;
                    case 269: goto L_0x012b;
                    case 270: goto L_0x010d;
                    case 271: goto L_0x00ef;
                    case 272: goto L_0x00d1;
                    case 273: goto L_0x009f;
                    case 274: goto L_0x0081;
                    case 275: goto L_0x0063;
                    case 276: goto L_0x004d;
                    case 277: goto L_0x003b;
                    case 278: goto L_0x002d;
                    case 279: goto L_0x001f;
                    case 280: goto L_0x001a;
                    default: goto L_0x0015;
                }
            L_0x0015:
                boolean r0 = super.onTransact(r21, r22, r23, r24)
                return r0
            L_0x001a:
                boolean r0 = r8.onTransact$startViewCalendarEventInManagedProfile$(r10, r11)
                return r0
            L_0x001f:
                r10.enforceInterface(r12)
                boolean r0 = r20.isUnattendedManagedKiosk()
                r23.writeNoException()
                r11.writeInt(r0)
                return r13
            L_0x002d:
                r10.enforceInterface(r12)
                boolean r0 = r20.isManagedKiosk()
                r23.writeNoException()
                r11.writeInt(r0)
                return r13
            L_0x003b:
                r10.enforceInterface(r12)
                int r0 = r22.readInt()
                java.util.List r1 = r8.getCrossProfileCalendarPackagesForUser(r0)
                r23.writeNoException()
                r11.writeStringList(r1)
                return r13
            L_0x004d:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                int r1 = r22.readInt()
                boolean r2 = r8.isPackageAllowedToAccessCalendarForUser(r0, r1)
                r23.writeNoException()
                r11.writeInt(r2)
                return r13
            L_0x0063:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x0075
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x0076
            L_0x0075:
            L_0x0076:
                java.util.List r1 = r8.getCrossProfileCalendarPackages(r0)
                r23.writeNoException()
                r11.writeStringList(r1)
                return r13
            L_0x0081:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x0093
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x0094
            L_0x0093:
            L_0x0094:
                java.util.ArrayList r1 = r22.createStringArrayList()
                r8.setCrossProfileCalendarPackages(r0, r1)
                r23.writeNoException()
                return r13
            L_0x009f:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x00b1
                android.os.Parcelable$Creator<android.content.ComponentName> r1 = android.content.ComponentName.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r10)
                android.content.ComponentName r1 = (android.content.ComponentName) r1
                goto L_0x00b2
            L_0x00b1:
                r1 = r0
            L_0x00b2:
                int r2 = r22.readInt()
                if (r2 == 0) goto L_0x00c1
                android.os.Parcelable$Creator<android.os.ParcelFileDescriptor> r0 = android.os.ParcelFileDescriptor.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.os.ParcelFileDescriptor r0 = (android.os.ParcelFileDescriptor) r0
                goto L_0x00c2
            L_0x00c1:
            L_0x00c2:
                android.os.IBinder r2 = r22.readStrongBinder()
                android.app.admin.StartInstallingUpdateCallback r2 = android.app.admin.StartInstallingUpdateCallback.Stub.asInterface(r2)
                r8.installUpdateFromFile(r1, r0, r2)
                r23.writeNoException()
                return r13
            L_0x00d1:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x00e3
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x00e4
            L_0x00e3:
            L_0x00e4:
                int r1 = r22.readInt()
                r8.grantDeviceIdsAccessToProfileOwner(r0, r1)
                r23.writeNoException()
                return r13
            L_0x00ef:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x0101
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x0102
            L_0x0101:
            L_0x0102:
                java.lang.String r1 = r8.getGlobalPrivateDnsHost(r0)
                r23.writeNoException()
                r11.writeString(r1)
                return r13
            L_0x010d:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x011f
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x0120
            L_0x011f:
            L_0x0120:
                int r1 = r8.getGlobalPrivateDnsMode(r0)
                r23.writeNoException()
                r11.writeInt(r1)
                return r13
            L_0x012b:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x013d
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x013e
            L_0x013d:
            L_0x013e:
                int r1 = r22.readInt()
                java.lang.String r2 = r22.readString()
                int r3 = r8.setGlobalPrivateDns(r0, r1, r2)
                r23.writeNoException()
                r11.writeInt(r3)
                return r13
            L_0x0151:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x0163
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x0164
            L_0x0163:
            L_0x0164:
                java.lang.String r1 = r22.readString()
                int r2 = r22.readInt()
                boolean r3 = r8.isMeteredDataDisabledPackageForUser(r0, r1, r2)
                r23.writeNoException()
                r11.writeInt(r3)
                return r13
            L_0x0177:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x0189
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x018a
            L_0x0189:
            L_0x018a:
                boolean r1 = r8.isOverrideApnEnabled(r0)
                r23.writeNoException()
                r11.writeInt(r1)
                return r13
            L_0x0195:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x01a7
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x01a8
            L_0x01a7:
            L_0x01a8:
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x01b0
                r6 = r13
            L_0x01b0:
                r1 = r6
                r8.setOverrideApnsEnabled(r0, r1)
                r23.writeNoException()
                return r13
            L_0x01b8:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x01ca
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x01cb
            L_0x01ca:
            L_0x01cb:
                java.util.List r1 = r8.getOverrideApns(r0)
                r23.writeNoException()
                r11.writeTypedList(r1)
                return r13
            L_0x01d6:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x01e8
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x01e9
            L_0x01e8:
            L_0x01e9:
                int r1 = r22.readInt()
                boolean r2 = r8.removeOverrideApn(r0, r1)
                r23.writeNoException()
                r11.writeInt(r2)
                return r13
            L_0x01f8:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x020a
                android.os.Parcelable$Creator<android.content.ComponentName> r1 = android.content.ComponentName.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r10)
                android.content.ComponentName r1 = (android.content.ComponentName) r1
                goto L_0x020b
            L_0x020a:
                r1 = r0
            L_0x020b:
                int r2 = r22.readInt()
                int r3 = r22.readInt()
                if (r3 == 0) goto L_0x021e
                android.os.Parcelable$Creator<android.telephony.data.ApnSetting> r0 = android.telephony.data.ApnSetting.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.telephony.data.ApnSetting r0 = (android.telephony.data.ApnSetting) r0
                goto L_0x021f
            L_0x021e:
            L_0x021f:
                boolean r3 = r8.updateOverrideApn(r1, r2, r0)
                r23.writeNoException()
                r11.writeInt(r3)
                return r13
            L_0x022a:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x023c
                android.os.Parcelable$Creator<android.content.ComponentName> r1 = android.content.ComponentName.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r10)
                android.content.ComponentName r1 = (android.content.ComponentName) r1
                goto L_0x023d
            L_0x023c:
                r1 = r0
            L_0x023d:
                int r2 = r22.readInt()
                if (r2 == 0) goto L_0x024c
                android.os.Parcelable$Creator<android.telephony.data.ApnSetting> r0 = android.telephony.data.ApnSetting.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.telephony.data.ApnSetting r0 = (android.telephony.data.ApnSetting) r0
                goto L_0x024d
            L_0x024c:
            L_0x024d:
                int r2 = r8.addOverrideApn(r1, r0)
                r23.writeNoException()
                r11.writeInt(r2)
                return r13
            L_0x0258:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x026a
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x026b
            L_0x026a:
            L_0x026b:
                java.util.List r1 = r8.getMeteredDataDisabledPackages(r0)
                r23.writeNoException()
                r11.writeStringList(r1)
                return r13
            L_0x0276:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x0288
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x0289
            L_0x0288:
            L_0x0289:
                java.util.ArrayList r1 = r22.createStringArrayList()
                java.util.List r2 = r8.setMeteredDataDisabledPackages(r0, r1)
                r23.writeNoException()
                r11.writeStringList(r2)
                return r13
            L_0x0298:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x02aa
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x02ab
            L_0x02aa:
            L_0x02ab:
                java.lang.CharSequence r1 = r8.getEndUserSessionMessage(r0)
                r23.writeNoException()
                if (r1 == 0) goto L_0x02bb
                r11.writeInt(r13)
                android.text.TextUtils.writeToParcel(r1, r11, r13)
                goto L_0x02be
            L_0x02bb:
                r11.writeInt(r6)
            L_0x02be:
                return r13
            L_0x02bf:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x02d1
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x02d2
            L_0x02d1:
            L_0x02d2:
                java.lang.CharSequence r1 = r8.getStartUserSessionMessage(r0)
                r23.writeNoException()
                if (r1 == 0) goto L_0x02e2
                r11.writeInt(r13)
                android.text.TextUtils.writeToParcel(r1, r11, r13)
                goto L_0x02e5
            L_0x02e2:
                r11.writeInt(r6)
            L_0x02e5:
                return r13
            L_0x02e6:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x02f8
                android.os.Parcelable$Creator<android.content.ComponentName> r1 = android.content.ComponentName.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r10)
                android.content.ComponentName r1 = (android.content.ComponentName) r1
                goto L_0x02f9
            L_0x02f8:
                r1 = r0
            L_0x02f9:
                int r2 = r22.readInt()
                if (r2 == 0) goto L_0x0308
                android.os.Parcelable$Creator<java.lang.CharSequence> r0 = android.text.TextUtils.CHAR_SEQUENCE_CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                java.lang.CharSequence r0 = (java.lang.CharSequence) r0
                goto L_0x0309
            L_0x0308:
            L_0x0309:
                r8.setEndUserSessionMessage(r1, r0)
                r23.writeNoException()
                return r13
            L_0x0310:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x0322
                android.os.Parcelable$Creator<android.content.ComponentName> r1 = android.content.ComponentName.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r10)
                android.content.ComponentName r1 = (android.content.ComponentName) r1
                goto L_0x0323
            L_0x0322:
                r1 = r0
            L_0x0323:
                int r2 = r22.readInt()
                if (r2 == 0) goto L_0x0332
                android.os.Parcelable$Creator<java.lang.CharSequence> r0 = android.text.TextUtils.CHAR_SEQUENCE_CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                java.lang.CharSequence r0 = (java.lang.CharSequence) r0
                goto L_0x0333
            L_0x0332:
            L_0x0333:
                r8.setStartUserSessionMessage(r1, r0)
                r23.writeNoException()
                return r13
            L_0x033a:
                r10.enforceInterface(r12)
                android.os.PersistableBundle r0 = r20.getTransferOwnershipBundle()
                r23.writeNoException()
                if (r0 == 0) goto L_0x034d
                r11.writeInt(r13)
                r0.writeToParcel(r11, r13)
                goto L_0x0350
            L_0x034d:
                r11.writeInt(r6)
            L_0x0350:
                return r13
            L_0x0351:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x0363
                android.os.Parcelable$Creator<android.content.ComponentName> r1 = android.content.ComponentName.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r10)
                android.content.ComponentName r1 = (android.content.ComponentName) r1
                goto L_0x0364
            L_0x0363:
                r1 = r0
            L_0x0364:
                int r2 = r22.readInt()
                if (r2 == 0) goto L_0x0373
                android.os.Parcelable$Creator<android.content.ComponentName> r2 = android.content.ComponentName.CREATOR
                java.lang.Object r2 = r2.createFromParcel(r10)
                android.content.ComponentName r2 = (android.content.ComponentName) r2
                goto L_0x0374
            L_0x0373:
                r2 = r0
            L_0x0374:
                int r3 = r22.readInt()
                if (r3 == 0) goto L_0x0383
                android.os.Parcelable$Creator<android.os.PersistableBundle> r0 = android.os.PersistableBundle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.os.PersistableBundle r0 = (android.os.PersistableBundle) r0
                goto L_0x0384
            L_0x0383:
            L_0x0384:
                r8.transferOwnership(r1, r2, r0)
                r23.writeNoException()
                return r13
            L_0x038b:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x039d
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x039e
            L_0x039d:
            L_0x039e:
                int r1 = r22.readInt()
                java.lang.String r2 = r22.readString()
                java.util.List r3 = r8.getDisallowedSystemApps(r0, r1, r2)
                r23.writeNoException()
                r11.writeStringList(r3)
                return r13
            L_0x03b1:
                r10.enforceInterface(r12)
                boolean r0 = r20.isLogoutEnabled()
                r23.writeNoException()
                r11.writeInt(r0)
                return r13
            L_0x03bf:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x03d1
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x03d2
            L_0x03d1:
            L_0x03d2:
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x03da
                r6 = r13
            L_0x03da:
                r1 = r6
                r8.setLogoutEnabled(r0, r1)
                r23.writeNoException()
                return r13
            L_0x03e2:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x03f4
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x03f5
            L_0x03f4:
            L_0x03f5:
                java.lang.String r1 = r22.readString()
                android.os.IBinder r2 = r22.readStrongBinder()
                android.content.pm.IPackageDataObserver r2 = android.content.pm.IPackageDataObserver.Stub.asInterface(r2)
                r8.clearApplicationUserData(r0, r1, r2)
                r23.writeNoException()
                return r13
            L_0x0408:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x041a
                android.os.Parcelable$Creator<android.os.UserHandle> r0 = android.os.UserHandle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.os.UserHandle r0 = (android.os.UserHandle) r0
                goto L_0x041b
            L_0x041a:
            L_0x041b:
                android.content.pm.StringParceledListSlice r1 = r8.getOwnerInstalledCaCerts(r0)
                r23.writeNoException()
                if (r1 == 0) goto L_0x042b
                r11.writeInt(r13)
                r1.writeToParcel(r11, r13)
                goto L_0x042e
            L_0x042b:
                r11.writeInt(r6)
            L_0x042e:
                return r13
            L_0x042f:
                r10.enforceInterface(r12)
                boolean r0 = r20.isCurrentInputMethodSetByOwner()
                r23.writeNoException()
                r11.writeInt(r0)
                return r13
            L_0x043d:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x044f
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x0450
            L_0x044f:
            L_0x0450:
                java.lang.String r1 = r22.readString()
                byte[] r2 = r22.createByteArray()
                int r3 = r22.readInt()
                boolean r4 = r8.resetPasswordWithToken(r0, r1, r2, r3)
                r23.writeNoException()
                r11.writeInt(r4)
                return r13
            L_0x0467:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x0479
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x047a
            L_0x0479:
            L_0x047a:
                boolean r1 = r8.isResetPasswordTokenActive(r0)
                r23.writeNoException()
                r11.writeInt(r1)
                return r13
            L_0x0485:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x0497
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x0498
            L_0x0497:
            L_0x0498:
                boolean r1 = r8.clearResetPasswordToken(r0)
                r23.writeNoException()
                r11.writeInt(r1)
                return r13
            L_0x04a3:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x04b5
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x04b6
            L_0x04b5:
            L_0x04b6:
                byte[] r1 = r22.createByteArray()
                boolean r2 = r8.setResetPasswordToken(r0, r1)
                r23.writeNoException()
                r11.writeInt(r2)
                return r13
            L_0x04c5:
                r10.enforceInterface(r12)
                long r0 = r20.getLastNetworkLogRetrievalTime()
                r23.writeNoException()
                r11.writeLong(r0)
                return r13
            L_0x04d3:
                r10.enforceInterface(r12)
                long r0 = r20.getLastBugReportRequestTime()
                r23.writeNoException()
                r11.writeLong(r0)
                return r13
            L_0x04e1:
                r10.enforceInterface(r12)
                long r0 = r20.getLastSecurityLogRetrievalTime()
                r23.writeNoException()
                r11.writeLong(r0)
                return r13
            L_0x04ef:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x0501
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x0502
            L_0x0501:
            L_0x0502:
                boolean r1 = r8.isEphemeralUser(r0)
                r23.writeNoException()
                r11.writeInt(r1)
                return r13
            L_0x050d:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x051f
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x0520
            L_0x051f:
            L_0x0520:
                java.util.List r1 = r8.getBindDeviceAdminTargetUsers(r0)
                r23.writeNoException()
                r11.writeTypedList(r1)
                return r13
            L_0x052b:
                boolean r0 = r8.onTransact$bindDeviceAdminServiceAsUser$(r10, r11)
                return r0
            L_0x0530:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x0542
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x0543
            L_0x0542:
            L_0x0543:
                java.lang.String r1 = r22.readString()
                long r2 = r22.readLong()
                java.util.List r4 = r8.retrieveNetworkLogs(r0, r1, r2)
                r23.writeNoException()
                r11.writeTypedList(r4)
                return r13
            L_0x0556:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x0568
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x0569
            L_0x0568:
            L_0x0569:
                java.lang.String r1 = r22.readString()
                boolean r2 = r8.isNetworkLoggingEnabled(r0, r1)
                r23.writeNoException()
                r11.writeInt(r2)
                return r13
            L_0x0578:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x058a
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x058b
            L_0x058a:
            L_0x058b:
                java.lang.String r1 = r22.readString()
                int r2 = r22.readInt()
                if (r2 == 0) goto L_0x0597
                r6 = r13
            L_0x0597:
                r2 = r6
                r8.setNetworkLoggingEnabled(r0, r1, r2)
                r23.writeNoException()
                return r13
            L_0x059f:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x05b1
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x05b2
            L_0x05b1:
            L_0x05b2:
                boolean r1 = r8.isBackupServiceEnabled(r0)
                r23.writeNoException()
                r11.writeInt(r1)
                return r13
            L_0x05bd:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x05cf
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x05d0
            L_0x05cf:
            L_0x05d0:
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x05d8
                r6 = r13
            L_0x05d8:
                r1 = r6
                r8.setBackupServiceEnabled(r0, r1)
                r23.writeNoException()
                return r13
            L_0x05e0:
                r10.enforceInterface(r12)
                r20.forceUpdateUserSetupComplete()
                r23.writeNoException()
                return r13
            L_0x05ea:
                r10.enforceInterface(r12)
                r20.setDeviceProvisioningConfigApplied()
                r23.writeNoException()
                return r13
            L_0x05f4:
                r10.enforceInterface(r12)
                boolean r0 = r20.isDeviceProvisioningConfigApplied()
                r23.writeNoException()
                r11.writeInt(r0)
                return r13
            L_0x0602:
                r10.enforceInterface(r12)
                boolean r0 = r20.isDeviceProvisioned()
                r23.writeNoException()
                r11.writeInt(r0)
                return r13
            L_0x0610:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                r8.uninstallPackageWithActiveAdmins(r0)
                r23.writeNoException()
                return r13
            L_0x061e:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                boolean r1 = r8.isUninstallInQueue(r0)
                r23.writeNoException()
                r11.writeInt(r1)
                return r13
            L_0x0630:
                r10.enforceInterface(r12)
                long r0 = r20.forceSecurityLogs()
                r23.writeNoException()
                r11.writeLong(r0)
                return r13
            L_0x063e:
                r10.enforceInterface(r12)
                long r0 = r20.forceNetworkLogs()
                r23.writeNoException()
                r11.writeLong(r0)
                return r13
            L_0x064c:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x065e
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x065f
            L_0x065e:
            L_0x065f:
                android.content.pm.ParceledListSlice r1 = r8.retrievePreRebootSecurityLogs(r0)
                r23.writeNoException()
                if (r1 == 0) goto L_0x066f
                r11.writeInt(r13)
                r1.writeToParcel(r11, r13)
                goto L_0x0672
            L_0x066f:
                r11.writeInt(r6)
            L_0x0672:
                return r13
            L_0x0673:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x0685
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x0686
            L_0x0685:
            L_0x0686:
                android.content.pm.ParceledListSlice r1 = r8.retrieveSecurityLogs(r0)
                r23.writeNoException()
                if (r1 == 0) goto L_0x0696
                r11.writeInt(r13)
                r1.writeToParcel(r11, r13)
                goto L_0x0699
            L_0x0696:
                r11.writeInt(r6)
            L_0x0699:
                return r13
            L_0x069a:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x06ac
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x06ad
            L_0x06ac:
            L_0x06ad:
                boolean r1 = r8.isSecurityLoggingEnabled(r0)
                r23.writeNoException()
                r11.writeInt(r1)
                return r13
            L_0x06b8:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x06ca
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x06cb
            L_0x06ca:
            L_0x06cb:
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x06d3
                r6 = r13
            L_0x06d3:
                r1 = r6
                r8.setSecurityLoggingEnabled(r0, r1)
                r23.writeNoException()
                return r13
            L_0x06db:
                r10.enforceInterface(r12)
                boolean r0 = r20.isAffiliatedUser()
                r23.writeNoException()
                r11.writeInt(r0)
                return r13
            L_0x06e9:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x06fb
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x06fc
            L_0x06fb:
            L_0x06fc:
                java.util.List r1 = r8.getAffiliationIds(r0)
                r23.writeNoException()
                r11.writeStringList(r1)
                return r13
            L_0x0707:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x0719
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x071a
            L_0x0719:
            L_0x071a:
                java.util.ArrayList r1 = r22.createStringArrayList()
                r8.setAffiliationIds(r0, r1)
                r23.writeNoException()
                return r13
            L_0x0725:
                r10.enforceInterface(r12)
                int r0 = r22.readInt()
                int r1 = r22.readInt()
                r8.setUserProvisioningState(r0, r1)
                r23.writeNoException()
                return r13
            L_0x0737:
                r10.enforceInterface(r12)
                int r0 = r20.getUserProvisioningState()
                r23.writeNoException()
                r11.writeInt(r0)
                return r13
            L_0x0745:
                r10.enforceInterface(r12)
                int r0 = r22.readInt()
                java.lang.CharSequence r1 = r8.getOrganizationNameForUser(r0)
                r23.writeNoException()
                if (r1 == 0) goto L_0x075c
                r11.writeInt(r13)
                android.text.TextUtils.writeToParcel(r1, r11, r13)
                goto L_0x075f
            L_0x075c:
                r11.writeInt(r6)
            L_0x075f:
                return r13
            L_0x0760:
                r10.enforceInterface(r12)
                java.lang.CharSequence r0 = r20.getDeviceOwnerOrganizationName()
                r23.writeNoException()
                if (r0 == 0) goto L_0x0773
                r11.writeInt(r13)
                android.text.TextUtils.writeToParcel(r0, r11, r13)
                goto L_0x0776
            L_0x0773:
                r11.writeInt(r6)
            L_0x0776:
                return r13
            L_0x0777:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x0789
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x078a
            L_0x0789:
            L_0x078a:
                java.lang.CharSequence r1 = r8.getOrganizationName(r0)
                r23.writeNoException()
                if (r1 == 0) goto L_0x079a
                r11.writeInt(r13)
                android.text.TextUtils.writeToParcel(r1, r11, r13)
                goto L_0x079d
            L_0x079a:
                r11.writeInt(r6)
            L_0x079d:
                return r13
            L_0x079e:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x07b0
                android.os.Parcelable$Creator<android.content.ComponentName> r1 = android.content.ComponentName.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r10)
                android.content.ComponentName r1 = (android.content.ComponentName) r1
                goto L_0x07b1
            L_0x07b0:
                r1 = r0
            L_0x07b1:
                int r2 = r22.readInt()
                if (r2 == 0) goto L_0x07c0
                android.os.Parcelable$Creator<java.lang.CharSequence> r0 = android.text.TextUtils.CHAR_SEQUENCE_CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                java.lang.CharSequence r0 = (java.lang.CharSequence) r0
                goto L_0x07c1
            L_0x07c0:
            L_0x07c1:
                r8.setOrganizationName(r1, r0)
                r23.writeNoException()
                return r13
            L_0x07c8:
                r10.enforceInterface(r12)
                int r0 = r22.readInt()
                int r1 = r8.getOrganizationColorForUser(r0)
                r23.writeNoException()
                r11.writeInt(r1)
                return r13
            L_0x07da:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x07ec
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x07ed
            L_0x07ec:
            L_0x07ed:
                int r1 = r8.getOrganizationColor(r0)
                r23.writeNoException()
                r11.writeInt(r1)
                return r13
            L_0x07f8:
                r10.enforceInterface(r12)
                int r0 = r22.readInt()
                int r1 = r22.readInt()
                r8.setOrganizationColorForUser(r0, r1)
                r23.writeNoException()
                return r13
            L_0x080a:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x081c
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x081d
            L_0x081c:
            L_0x081d:
                int r1 = r22.readInt()
                r8.setOrganizationColor(r0, r1)
                r23.writeNoException()
                return r13
            L_0x0828:
                r10.enforceInterface(r12)
                int r0 = r22.readInt()
                boolean r1 = r8.isSeparateProfileChallengeAllowed(r0)
                r23.writeNoException()
                r11.writeInt(r1)
                return r13
            L_0x083a:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x084c
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x084d
            L_0x084c:
            L_0x084d:
                int r1 = r22.readInt()
                java.lang.CharSequence r2 = r8.getLongSupportMessageForUser(r0, r1)
                r23.writeNoException()
                if (r2 == 0) goto L_0x0861
                r11.writeInt(r13)
                android.text.TextUtils.writeToParcel(r2, r11, r13)
                goto L_0x0864
            L_0x0861:
                r11.writeInt(r6)
            L_0x0864:
                return r13
            L_0x0865:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x0877
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x0878
            L_0x0877:
            L_0x0878:
                int r1 = r22.readInt()
                java.lang.CharSequence r2 = r8.getShortSupportMessageForUser(r0, r1)
                r23.writeNoException()
                if (r2 == 0) goto L_0x088c
                r11.writeInt(r13)
                android.text.TextUtils.writeToParcel(r2, r11, r13)
                goto L_0x088f
            L_0x088c:
                r11.writeInt(r6)
            L_0x088f:
                return r13
            L_0x0890:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x08a2
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x08a3
            L_0x08a2:
            L_0x08a3:
                java.lang.CharSequence r1 = r8.getLongSupportMessage(r0)
                r23.writeNoException()
                if (r1 == 0) goto L_0x08b3
                r11.writeInt(r13)
                android.text.TextUtils.writeToParcel(r1, r11, r13)
                goto L_0x08b6
            L_0x08b3:
                r11.writeInt(r6)
            L_0x08b6:
                return r13
            L_0x08b7:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x08c9
                android.os.Parcelable$Creator<android.content.ComponentName> r1 = android.content.ComponentName.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r10)
                android.content.ComponentName r1 = (android.content.ComponentName) r1
                goto L_0x08ca
            L_0x08c9:
                r1 = r0
            L_0x08ca:
                int r2 = r22.readInt()
                if (r2 == 0) goto L_0x08d9
                android.os.Parcelable$Creator<java.lang.CharSequence> r0 = android.text.TextUtils.CHAR_SEQUENCE_CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                java.lang.CharSequence r0 = (java.lang.CharSequence) r0
                goto L_0x08da
            L_0x08d9:
            L_0x08da:
                r8.setLongSupportMessage(r1, r0)
                r23.writeNoException()
                return r13
            L_0x08e1:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x08f3
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x08f4
            L_0x08f3:
            L_0x08f4:
                java.lang.CharSequence r1 = r8.getShortSupportMessage(r0)
                r23.writeNoException()
                if (r1 == 0) goto L_0x0904
                r11.writeInt(r13)
                android.text.TextUtils.writeToParcel(r1, r11, r13)
                goto L_0x0907
            L_0x0904:
                r11.writeInt(r6)
            L_0x0907:
                return r13
            L_0x0908:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x091a
                android.os.Parcelable$Creator<android.content.ComponentName> r1 = android.content.ComponentName.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r10)
                android.content.ComponentName r1 = (android.content.ComponentName) r1
                goto L_0x091b
            L_0x091a:
                r1 = r0
            L_0x091b:
                int r2 = r22.readInt()
                if (r2 == 0) goto L_0x092a
                android.os.Parcelable$Creator<java.lang.CharSequence> r0 = android.text.TextUtils.CHAR_SEQUENCE_CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                java.lang.CharSequence r0 = (java.lang.CharSequence) r0
                goto L_0x092b
            L_0x092a:
            L_0x092b:
                r8.setShortSupportMessage(r1, r0)
                r23.writeNoException()
                return r13
            L_0x0932:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x0944
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x0945
            L_0x0944:
            L_0x0945:
                r8.reboot(r0)
                r23.writeNoException()
                return r13
            L_0x094c:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x095e
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x095f
            L_0x095e:
            L_0x095f:
                java.lang.String r1 = r8.getWifiMacAddress(r0)
                r23.writeNoException()
                r11.writeString(r1)
                return r13
            L_0x096a:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x097c
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x097d
            L_0x097c:
            L_0x097d:
                boolean r1 = r8.isSystemOnlyUser(r0)
                r23.writeNoException()
                r11.writeInt(r1)
                return r13
            L_0x0988:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x099a
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x099b
            L_0x099a:
            L_0x099b:
                boolean r1 = r8.isManagedProfile(r0)
                r23.writeNoException()
                r11.writeInt(r1)
                return r13
            L_0x09a6:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x09b8
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x09b9
            L_0x09b8:
            L_0x09b9:
                java.lang.String r1 = r22.readString()
                java.util.List r2 = r8.getKeepUninstalledPackages(r0, r1)
                r23.writeNoException()
                r11.writeStringList(r2)
                return r13
            L_0x09c8:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x09da
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x09db
            L_0x09da:
            L_0x09db:
                java.lang.String r1 = r22.readString()
                java.util.ArrayList r2 = r22.createStringArrayList()
                r8.setKeepUninstalledPackages(r0, r1, r2)
                r23.writeNoException()
                return r13
            L_0x09ea:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                java.lang.String r1 = r22.readString()
                int r2 = r8.checkProvisioningPreCondition(r0, r1)
                r23.writeNoException()
                r11.writeInt(r2)
                return r13
            L_0x0a00:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                java.lang.String r1 = r22.readString()
                boolean r2 = r8.isProvisioningAllowed(r0, r1)
                r23.writeNoException()
                r11.writeInt(r2)
                return r13
            L_0x0a16:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x0a28
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x0a29
            L_0x0a28:
            L_0x0a29:
                java.lang.String r1 = r22.readString()
                java.lang.String r2 = r22.readString()
                java.lang.String r3 = r22.readString()
                int r4 = r8.getPermissionGrantState(r0, r1, r2, r3)
                r23.writeNoException()
                r11.writeInt(r4)
                return r13
            L_0x0a40:
                boolean r0 = r8.onTransact$setPermissionGrantState$(r10, r11)
                return r0
            L_0x0a45:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x0a57
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x0a58
            L_0x0a57:
            L_0x0a58:
                int r1 = r8.getPermissionPolicy(r0)
                r23.writeNoException()
                r11.writeInt(r1)
                return r13
            L_0x0a63:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x0a75
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x0a76
            L_0x0a75:
            L_0x0a76:
                java.lang.String r1 = r22.readString()
                int r2 = r22.readInt()
                r8.setPermissionPolicy(r0, r1, r2)
                r23.writeNoException()
                return r13
            L_0x0a85:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x0a97
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x0a98
            L_0x0a97:
            L_0x0a98:
                android.app.admin.SystemUpdateInfo r1 = r8.getPendingSystemUpdate(r0)
                r23.writeNoException()
                if (r1 == 0) goto L_0x0aa8
                r11.writeInt(r13)
                r1.writeToParcel(r11, r13)
                goto L_0x0aab
            L_0x0aa8:
                r11.writeInt(r6)
            L_0x0aab:
                return r13
            L_0x0aac:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x0abe
                android.os.Parcelable$Creator<android.app.admin.SystemUpdateInfo> r0 = android.app.admin.SystemUpdateInfo.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.app.admin.SystemUpdateInfo r0 = (android.app.admin.SystemUpdateInfo) r0
                goto L_0x0abf
            L_0x0abe:
            L_0x0abf:
                r8.notifyPendingSystemUpdate(r0)
                r23.writeNoException()
                return r13
            L_0x0ac6:
                r10.enforceInterface(r12)
                boolean r0 = r20.getDoNotAskCredentialsOnBoot()
                r23.writeNoException()
                r11.writeInt(r0)
                return r13
            L_0x0ad4:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x0ae6
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x0ae7
            L_0x0ae6:
            L_0x0ae7:
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x0aef
                r6 = r13
            L_0x0aef:
                r1 = r6
                boolean r2 = r8.setStatusBarDisabled(r0, r1)
                r23.writeNoException()
                r11.writeInt(r2)
                return r13
            L_0x0afb:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x0b0d
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x0b0e
            L_0x0b0d:
            L_0x0b0e:
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x0b16
                r6 = r13
            L_0x0b16:
                r1 = r6
                boolean r2 = r8.setKeyguardDisabled(r0, r1)
                r23.writeNoException()
                r11.writeInt(r2)
                return r13
            L_0x0b22:
                r10.enforceInterface(r12)
                r20.clearSystemUpdatePolicyFreezePeriodRecord()
                r23.writeNoException()
                return r13
            L_0x0b2c:
                r10.enforceInterface(r12)
                android.app.admin.SystemUpdatePolicy r0 = r20.getSystemUpdatePolicy()
                r23.writeNoException()
                if (r0 == 0) goto L_0x0b3f
                r11.writeInt(r13)
                r0.writeToParcel(r11, r13)
                goto L_0x0b42
            L_0x0b3f:
                r11.writeInt(r6)
            L_0x0b42:
                return r13
            L_0x0b43:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x0b55
                android.os.Parcelable$Creator<android.content.ComponentName> r1 = android.content.ComponentName.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r10)
                android.content.ComponentName r1 = (android.content.ComponentName) r1
                goto L_0x0b56
            L_0x0b55:
                r1 = r0
            L_0x0b56:
                int r2 = r22.readInt()
                if (r2 == 0) goto L_0x0b65
                android.os.Parcelable$Creator<android.app.admin.SystemUpdatePolicy> r0 = android.app.admin.SystemUpdatePolicy.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.app.admin.SystemUpdatePolicy r0 = (android.app.admin.SystemUpdatePolicy) r0
                goto L_0x0b66
            L_0x0b65:
            L_0x0b66:
                r8.setSystemUpdatePolicy(r1, r0)
                r23.writeNoException()
                return r13
            L_0x0b6d:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x0b7f
                android.os.Parcelable$Creator<android.content.ComponentName> r1 = android.content.ComponentName.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r10)
                android.content.ComponentName r1 = (android.content.ComponentName) r1
                goto L_0x0b80
            L_0x0b7f:
                r1 = r0
            L_0x0b80:
                int r2 = r22.readInt()
                if (r2 == 0) goto L_0x0b8f
                android.os.Parcelable$Creator<android.graphics.Bitmap> r0 = android.graphics.Bitmap.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.graphics.Bitmap r0 = (android.graphics.Bitmap) r0
                goto L_0x0b90
            L_0x0b8f:
            L_0x0b90:
                r8.setUserIcon(r1, r0)
                r23.writeNoException()
                return r13
            L_0x0b97:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x0ba9
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x0baa
            L_0x0ba9:
            L_0x0baa:
                int r1 = r22.readInt()
                boolean r2 = r8.isRemovingAdmin(r0, r1)
                r23.writeNoException()
                r11.writeInt(r2)
                return r13
            L_0x0bb9:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x0bcb
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x0bcc
            L_0x0bcb:
            L_0x0bcc:
                boolean r1 = r8.getForceEphemeralUsers(r0)
                r23.writeNoException()
                r11.writeInt(r1)
                return r13
            L_0x0bd7:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x0be9
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x0bea
            L_0x0be9:
            L_0x0bea:
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x0bf2
                r6 = r13
            L_0x0bf2:
                r1 = r6
                r8.setForceEphemeralUsers(r0, r1)
                r23.writeNoException()
                return r13
            L_0x0bfa:
                r10.enforceInterface(r12)
                boolean r0 = r20.getAutoTimeRequired()
                r23.writeNoException()
                r11.writeInt(r0)
                return r13
            L_0x0c08:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x0c1a
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x0c1b
            L_0x0c1a:
            L_0x0c1b:
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x0c23
                r6 = r13
            L_0x0c23:
                r1 = r6
                r8.setAutoTimeRequired(r0, r1)
                r23.writeNoException()
                return r13
            L_0x0c2b:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x0c3d
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x0c3e
            L_0x0c3d:
            L_0x0c3e:
                java.util.List r1 = r8.getCrossProfileWidgetProviders(r0)
                r23.writeNoException()
                r11.writeStringList(r1)
                return r13
            L_0x0c49:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x0c5b
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x0c5c
            L_0x0c5b:
            L_0x0c5c:
                java.lang.String r1 = r22.readString()
                boolean r2 = r8.removeCrossProfileWidgetProvider(r0, r1)
                r23.writeNoException()
                r11.writeInt(r2)
                return r13
            L_0x0c6b:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x0c7d
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x0c7e
            L_0x0c7d:
            L_0x0c7e:
                java.lang.String r1 = r22.readString()
                boolean r2 = r8.addCrossProfileWidgetProvider(r0, r1)
                r23.writeNoException()
                r11.writeInt(r2)
                return r13
            L_0x0c8d:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x0c9f
                android.os.Parcelable$Creator<android.content.ComponentName> r1 = android.content.ComponentName.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r10)
                android.content.ComponentName r1 = (android.content.ComponentName) r1
                goto L_0x0ca0
            L_0x0c9f:
                r1 = r0
            L_0x0ca0:
                int r2 = r22.readInt()
                if (r2 == 0) goto L_0x0caf
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x0cb0
            L_0x0caf:
            L_0x0cb0:
                int r2 = r22.readInt()
                int r3 = r22.readInt()
                if (r3 == 0) goto L_0x0cbc
                r6 = r13
            L_0x0cbc:
                r3 = r6
                java.util.List r4 = r8.getTrustAgentConfiguration(r1, r0, r2, r3)
                r23.writeNoException()
                r11.writeTypedList(r4)
                return r13
            L_0x0cc8:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x0cda
                android.os.Parcelable$Creator<android.content.ComponentName> r1 = android.content.ComponentName.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r10)
                android.content.ComponentName r1 = (android.content.ComponentName) r1
                goto L_0x0cdb
            L_0x0cda:
                r1 = r0
            L_0x0cdb:
                int r2 = r22.readInt()
                if (r2 == 0) goto L_0x0cea
                android.os.Parcelable$Creator<android.content.ComponentName> r2 = android.content.ComponentName.CREATOR
                java.lang.Object r2 = r2.createFromParcel(r10)
                android.content.ComponentName r2 = (android.content.ComponentName) r2
                goto L_0x0ceb
            L_0x0cea:
                r2 = r0
            L_0x0ceb:
                int r3 = r22.readInt()
                if (r3 == 0) goto L_0x0cfa
                android.os.Parcelable$Creator<android.os.PersistableBundle> r0 = android.os.PersistableBundle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.os.PersistableBundle r0 = (android.os.PersistableBundle) r0
                goto L_0x0cfb
            L_0x0cfa:
            L_0x0cfb:
                int r3 = r22.readInt()
                if (r3 == 0) goto L_0x0d03
                r6 = r13
            L_0x0d03:
                r3 = r6
                r8.setTrustAgentConfiguration(r1, r2, r0, r3)
                r23.writeNoException()
                return r13
            L_0x0d0b:
                r10.enforceInterface(r12)
                int r0 = r22.readInt()
                boolean r1 = r8.getBluetoothContactSharingDisabledForUser(r0)
                r23.writeNoException()
                r11.writeInt(r1)
                return r13
            L_0x0d1d:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x0d2f
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x0d30
            L_0x0d2f:
            L_0x0d30:
                boolean r1 = r8.getBluetoothContactSharingDisabled(r0)
                r23.writeNoException()
                r11.writeInt(r1)
                return r13
            L_0x0d3b:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x0d4d
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x0d4e
            L_0x0d4d:
            L_0x0d4e:
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x0d56
                r6 = r13
            L_0x0d56:
                r1 = r6
                r8.setBluetoothContactSharingDisabled(r0, r1)
                r23.writeNoException()
                return r13
            L_0x0d5e:
                r10.enforceInterface(r12)
                java.lang.String r14 = r22.readString()
                long r15 = r22.readLong()
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x0d71
                r4 = r13
                goto L_0x0d72
            L_0x0d71:
                r4 = r6
            L_0x0d72:
                long r17 = r22.readLong()
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x0d86
                android.os.Parcelable$Creator<android.content.Intent> r0 = android.content.Intent.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.Intent r0 = (android.content.Intent) r0
            L_0x0d84:
                r7 = r0
                goto L_0x0d87
            L_0x0d86:
                goto L_0x0d84
            L_0x0d87:
                r0 = r20
                r1 = r14
                r2 = r15
                r5 = r17
                r0.startManagedQuickContact(r1, r2, r4, r5, r7)
                r23.writeNoException()
                return r13
            L_0x0d94:
                r10.enforceInterface(r12)
                int r0 = r22.readInt()
                boolean r1 = r8.getCrossProfileContactsSearchDisabledForUser(r0)
                r23.writeNoException()
                r11.writeInt(r1)
                return r13
            L_0x0da6:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x0db8
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x0db9
            L_0x0db8:
            L_0x0db9:
                boolean r1 = r8.getCrossProfileContactsSearchDisabled(r0)
                r23.writeNoException()
                r11.writeInt(r1)
                return r13
            L_0x0dc4:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x0dd6
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x0dd7
            L_0x0dd6:
            L_0x0dd7:
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x0ddf
                r6 = r13
            L_0x0ddf:
                r1 = r6
                r8.setCrossProfileContactsSearchDisabled(r0, r1)
                r23.writeNoException()
                return r13
            L_0x0de7:
                r10.enforceInterface(r12)
                int r0 = r22.readInt()
                boolean r1 = r8.getCrossProfileCallerIdDisabledForUser(r0)
                r23.writeNoException()
                r11.writeInt(r1)
                return r13
            L_0x0df9:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x0e0b
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x0e0c
            L_0x0e0b:
            L_0x0e0c:
                boolean r1 = r8.getCrossProfileCallerIdDisabled(r0)
                r23.writeNoException()
                r11.writeInt(r1)
                return r13
            L_0x0e17:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x0e29
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x0e2a
            L_0x0e29:
            L_0x0e2a:
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x0e32
                r6 = r13
            L_0x0e32:
                r1 = r6
                r8.setCrossProfileCallerIdDisabled(r0, r1)
                r23.writeNoException()
                return r13
            L_0x0e3a:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x0e4c
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x0e4d
            L_0x0e4c:
            L_0x0e4d:
                java.lang.String r1 = r22.readString()
                boolean r2 = r8.isUninstallBlocked(r0, r1)
                r23.writeNoException()
                r11.writeInt(r2)
                return r13
            L_0x0e5c:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x0e6e
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x0e6f
            L_0x0e6e:
            L_0x0e6f:
                java.lang.String r1 = r22.readString()
                java.lang.String r2 = r22.readString()
                int r3 = r22.readInt()
                if (r3 == 0) goto L_0x0e7f
                r6 = r13
            L_0x0e7f:
                r3 = r6
                r8.setUninstallBlocked(r0, r1, r2, r3)
                r23.writeNoException()
                return r13
            L_0x0e87:
                r10.enforceInterface(r12)
                int r0 = r22.readInt()
                if (r0 == 0) goto L_0x0e92
                r6 = r13
            L_0x0e92:
                r0 = r6
                java.lang.String r1 = r22.readString()
                int r2 = r22.readInt()
                r8.notifyLockTaskModeChanged(r0, r1, r2)
                r23.writeNoException()
                return r13
            L_0x0ea2:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x0eb4
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x0eb5
            L_0x0eb4:
            L_0x0eb5:
                boolean r1 = r8.isMasterVolumeMuted(r0)
                r23.writeNoException()
                r11.writeInt(r1)
                return r13
            L_0x0ec0:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x0ed2
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x0ed3
            L_0x0ed2:
            L_0x0ed3:
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x0edb
                r6 = r13
            L_0x0edb:
                r1 = r6
                r8.setMasterVolumeMuted(r0, r1)
                r23.writeNoException()
                return r13
            L_0x0ee3:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x0ef5
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x0ef6
            L_0x0ef5:
            L_0x0ef6:
                java.lang.String r1 = r22.readString()
                boolean r2 = r8.setTimeZone(r0, r1)
                r23.writeNoException()
                r11.writeInt(r2)
                return r13
            L_0x0f05:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x0f17
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x0f18
            L_0x0f17:
            L_0x0f18:
                long r1 = r22.readLong()
                boolean r3 = r8.setTime(r0, r1)
                r23.writeNoException()
                r11.writeInt(r3)
                return r13
            L_0x0f27:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x0f39
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x0f3a
            L_0x0f39:
            L_0x0f3a:
                java.lang.String r1 = r22.readString()
                java.lang.String r2 = r22.readString()
                r8.setSecureSetting(r0, r1, r2)
                r23.writeNoException()
                return r13
            L_0x0f49:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x0f5b
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x0f5c
            L_0x0f5b:
            L_0x0f5c:
                java.lang.String r1 = r22.readString()
                java.lang.String r2 = r22.readString()
                r8.setSystemSetting(r0, r1, r2)
                r23.writeNoException()
                return r13
            L_0x0f6b:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x0f7d
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x0f7e
            L_0x0f7d:
            L_0x0f7e:
                java.lang.String r1 = r22.readString()
                java.lang.String r2 = r22.readString()
                r8.setGlobalSetting(r0, r1, r2)
                r23.writeNoException()
                return r13
            L_0x0f8d:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x0f9f
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x0fa0
            L_0x0f9f:
            L_0x0fa0:
                int r1 = r8.getLockTaskFeatures(r0)
                r23.writeNoException()
                r11.writeInt(r1)
                return r13
            L_0x0fab:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x0fbd
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x0fbe
            L_0x0fbd:
            L_0x0fbe:
                int r1 = r22.readInt()
                r8.setLockTaskFeatures(r0, r1)
                r23.writeNoException()
                return r13
            L_0x0fc9:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                boolean r1 = r8.isLockTaskPermitted(r0)
                r23.writeNoException()
                r11.writeInt(r1)
                return r13
            L_0x0fdb:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x0fed
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x0fee
            L_0x0fed:
            L_0x0fee:
                java.lang.String[] r1 = r8.getLockTaskPackages(r0)
                r23.writeNoException()
                r11.writeStringArray(r1)
                return r13
            L_0x0ff9:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x100b
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x100c
            L_0x100b:
            L_0x100c:
                java.lang.String[] r1 = r22.createStringArray()
                r8.setLockTaskPackages(r0, r1)
                r23.writeNoException()
                return r13
            L_0x1017:
                r10.enforceInterface(r12)
                int r0 = r22.readInt()
                java.lang.String[] r1 = r8.getAccountTypesWithManagementDisabledAsUser(r0)
                r23.writeNoException()
                r11.writeStringArray(r1)
                return r13
            L_0x1029:
                r10.enforceInterface(r12)
                java.lang.String[] r0 = r20.getAccountTypesWithManagementDisabled()
                r23.writeNoException()
                r11.writeStringArray(r0)
                return r13
            L_0x1037:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x1049
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x104a
            L_0x1049:
            L_0x104a:
                java.lang.String r1 = r22.readString()
                int r2 = r22.readInt()
                if (r2 == 0) goto L_0x1056
                r6 = r13
            L_0x1056:
                r2 = r6
                r8.setAccountManagementDisabled(r0, r1, r2)
                r23.writeNoException()
                return r13
            L_0x105e:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x1070
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x1071
            L_0x1070:
            L_0x1071:
                java.lang.String r1 = r22.readString()
                java.lang.String r2 = r22.readString()
                boolean r3 = r8.installExistingPackage(r0, r1, r2)
                r23.writeNoException()
                r11.writeInt(r3)
                return r13
            L_0x1084:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x1096
                android.os.Parcelable$Creator<android.content.ComponentName> r1 = android.content.ComponentName.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r10)
                android.content.ComponentName r1 = (android.content.ComponentName) r1
                goto L_0x1097
            L_0x1096:
                r1 = r0
            L_0x1097:
                java.lang.String r2 = r22.readString()
                int r3 = r22.readInt()
                if (r3 == 0) goto L_0x10aa
                android.os.Parcelable$Creator<android.content.Intent> r0 = android.content.Intent.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.Intent r0 = (android.content.Intent) r0
                goto L_0x10ab
            L_0x10aa:
            L_0x10ab:
                int r3 = r8.enableSystemAppWithIntent(r1, r2, r0)
                r23.writeNoException()
                r11.writeInt(r3)
                return r13
            L_0x10b6:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x10c8
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x10c9
            L_0x10c8:
            L_0x10c9:
                java.lang.String r1 = r22.readString()
                java.lang.String r2 = r22.readString()
                r8.enableSystemApp(r0, r1, r2)
                r23.writeNoException()
                return r13
            L_0x10d8:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x10ea
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x10eb
            L_0x10ea:
            L_0x10eb:
                java.util.List r1 = r8.getSecondaryUsers(r0)
                r23.writeNoException()
                r11.writeTypedList(r1)
                return r13
            L_0x10f6:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x1108
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x1109
            L_0x1108:
            L_0x1109:
                int r1 = r8.logoutUser(r0)
                r23.writeNoException()
                r11.writeInt(r1)
                return r13
            L_0x1114:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x1126
                android.os.Parcelable$Creator<android.content.ComponentName> r1 = android.content.ComponentName.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r10)
                android.content.ComponentName r1 = (android.content.ComponentName) r1
                goto L_0x1127
            L_0x1126:
                r1 = r0
            L_0x1127:
                int r2 = r22.readInt()
                if (r2 == 0) goto L_0x1136
                android.os.Parcelable$Creator<android.os.UserHandle> r0 = android.os.UserHandle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.os.UserHandle r0 = (android.os.UserHandle) r0
                goto L_0x1137
            L_0x1136:
            L_0x1137:
                int r2 = r8.stopUser(r1, r0)
                r23.writeNoException()
                r11.writeInt(r2)
                return r13
            L_0x1142:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x1154
                android.os.Parcelable$Creator<android.content.ComponentName> r1 = android.content.ComponentName.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r10)
                android.content.ComponentName r1 = (android.content.ComponentName) r1
                goto L_0x1155
            L_0x1154:
                r1 = r0
            L_0x1155:
                int r2 = r22.readInt()
                if (r2 == 0) goto L_0x1164
                android.os.Parcelable$Creator<android.os.UserHandle> r0 = android.os.UserHandle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.os.UserHandle r0 = (android.os.UserHandle) r0
                goto L_0x1165
            L_0x1164:
            L_0x1165:
                int r2 = r8.startUserInBackground(r1, r0)
                r23.writeNoException()
                r11.writeInt(r2)
                return r13
            L_0x1170:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x1182
                android.os.Parcelable$Creator<android.content.ComponentName> r1 = android.content.ComponentName.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r10)
                android.content.ComponentName r1 = (android.content.ComponentName) r1
                goto L_0x1183
            L_0x1182:
                r1 = r0
            L_0x1183:
                int r2 = r22.readInt()
                if (r2 == 0) goto L_0x1192
                android.os.Parcelable$Creator<android.os.UserHandle> r0 = android.os.UserHandle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.os.UserHandle r0 = (android.os.UserHandle) r0
                goto L_0x1193
            L_0x1192:
            L_0x1193:
                boolean r2 = r8.switchUser(r1, r0)
                r23.writeNoException()
                r11.writeInt(r2)
                return r13
            L_0x119e:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x11b0
                android.os.Parcelable$Creator<android.content.ComponentName> r1 = android.content.ComponentName.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r10)
                android.content.ComponentName r1 = (android.content.ComponentName) r1
                goto L_0x11b1
            L_0x11b0:
                r1 = r0
            L_0x11b1:
                int r2 = r22.readInt()
                if (r2 == 0) goto L_0x11c0
                android.os.Parcelable$Creator<android.os.UserHandle> r0 = android.os.UserHandle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.os.UserHandle r0 = (android.os.UserHandle) r0
                goto L_0x11c1
            L_0x11c0:
            L_0x11c1:
                boolean r2 = r8.removeUser(r1, r0)
                r23.writeNoException()
                r11.writeInt(r2)
                return r13
            L_0x11cc:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x11de
                android.os.Parcelable$Creator<android.content.ComponentName> r1 = android.content.ComponentName.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r10)
                android.content.ComponentName r1 = (android.content.ComponentName) r1
                goto L_0x11df
            L_0x11de:
                r1 = r0
            L_0x11df:
                java.lang.String r7 = r22.readString()
                int r2 = r22.readInt()
                if (r2 == 0) goto L_0x11f3
                android.os.Parcelable$Creator<android.content.ComponentName> r2 = android.content.ComponentName.CREATOR
                java.lang.Object r2 = r2.createFromParcel(r10)
                android.content.ComponentName r2 = (android.content.ComponentName) r2
                r3 = r2
                goto L_0x11f4
            L_0x11f3:
                r3 = r0
            L_0x11f4:
                int r2 = r22.readInt()
                if (r2 == 0) goto L_0x1204
                android.os.Parcelable$Creator<android.os.PersistableBundle> r0 = android.os.PersistableBundle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.os.PersistableBundle r0 = (android.os.PersistableBundle) r0
            L_0x1202:
                r4 = r0
                goto L_0x1205
            L_0x1204:
                goto L_0x1202
            L_0x1205:
                int r14 = r22.readInt()
                r0 = r20
                r2 = r7
                r5 = r14
                android.os.UserHandle r0 = r0.createAndManageUser(r1, r2, r3, r4, r5)
                r23.writeNoException()
                if (r0 == 0) goto L_0x121d
                r11.writeInt(r13)
                r0.writeToParcel((android.os.Parcel) r11, (int) r13)
                goto L_0x1220
            L_0x121d:
                r11.writeInt(r6)
            L_0x1220:
                return r13
            L_0x1221:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x1233
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x1234
            L_0x1233:
            L_0x1234:
                java.lang.String r1 = r22.readString()
                java.lang.String r2 = r22.readString()
                boolean r3 = r8.isApplicationHidden(r0, r1, r2)
                r23.writeNoException()
                r11.writeInt(r3)
                return r13
            L_0x1247:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x1259
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x125a
            L_0x1259:
            L_0x125a:
                java.lang.String r1 = r22.readString()
                java.lang.String r2 = r22.readString()
                int r3 = r22.readInt()
                if (r3 == 0) goto L_0x126a
                r6 = r13
            L_0x126a:
                r3 = r6
                boolean r4 = r8.setApplicationHidden(r0, r1, r2, r3)
                r23.writeNoException()
                r11.writeInt(r4)
                return r13
            L_0x1276:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                android.content.Intent r1 = r8.createAdminSupportIntent(r0)
                r23.writeNoException()
                if (r1 == 0) goto L_0x128d
                r11.writeInt(r13)
                r1.writeToParcel(r11, r13)
                goto L_0x1290
            L_0x128d:
                r11.writeInt(r6)
            L_0x1290:
                return r13
            L_0x1291:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                int r1 = r22.readInt()
                boolean r2 = r8.isNotificationListenerServicePermitted(r0, r1)
                r23.writeNoException()
                r11.writeInt(r2)
                return r13
            L_0x12a7:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x12b9
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x12ba
            L_0x12b9:
            L_0x12ba:
                java.util.List r1 = r8.getPermittedCrossProfileNotificationListeners(r0)
                r23.writeNoException()
                r11.writeStringList(r1)
                return r13
            L_0x12c5:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x12d7
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x12d8
            L_0x12d7:
            L_0x12d8:
                java.util.ArrayList r1 = r22.createStringArrayList()
                boolean r2 = r8.setPermittedCrossProfileNotificationListeners(r0, r1)
                r23.writeNoException()
                r11.writeInt(r2)
                return r13
            L_0x12e7:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x12f9
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x12fa
            L_0x12f9:
            L_0x12fa:
                java.lang.String r1 = r22.readString()
                int r2 = r22.readInt()
                boolean r3 = r8.isInputMethodPermittedByAdmin(r0, r1, r2)
                r23.writeNoException()
                r11.writeInt(r3)
                return r13
            L_0x130d:
                r10.enforceInterface(r12)
                java.util.List r0 = r20.getPermittedInputMethodsForCurrentUser()
                r23.writeNoException()
                r11.writeList(r0)
                return r13
            L_0x131b:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x132d
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x132e
            L_0x132d:
            L_0x132e:
                java.util.List r1 = r8.getPermittedInputMethods(r0)
                r23.writeNoException()
                r11.writeList(r1)
                return r13
            L_0x1339:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x134b
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x134c
            L_0x134b:
            L_0x134c:
                java.lang.Class r1 = r20.getClass()
                java.lang.ClassLoader r1 = r1.getClassLoader()
                java.util.ArrayList r2 = r10.readArrayList(r1)
                boolean r3 = r8.setPermittedInputMethods(r0, r2)
                r23.writeNoException()
                r11.writeInt(r3)
                return r13
            L_0x1363:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x1375
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x1376
            L_0x1375:
            L_0x1376:
                java.lang.String r1 = r22.readString()
                int r2 = r22.readInt()
                boolean r3 = r8.isAccessibilityServicePermittedByAdmin(r0, r1, r2)
                r23.writeNoException()
                r11.writeInt(r3)
                return r13
            L_0x1389:
                r10.enforceInterface(r12)
                int r0 = r22.readInt()
                java.util.List r1 = r8.getPermittedAccessibilityServicesForUser(r0)
                r23.writeNoException()
                r11.writeList(r1)
                return r13
            L_0x139b:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x13ad
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x13ae
            L_0x13ad:
            L_0x13ae:
                java.util.List r1 = r8.getPermittedAccessibilityServices(r0)
                r23.writeNoException()
                r11.writeList(r1)
                return r13
            L_0x13b9:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x13cb
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x13cc
            L_0x13cb:
            L_0x13cc:
                java.lang.Class r1 = r20.getClass()
                java.lang.ClassLoader r1 = r1.getClassLoader()
                java.util.ArrayList r2 = r10.readArrayList(r1)
                boolean r3 = r8.setPermittedAccessibilityServices(r0, r2)
                r23.writeNoException()
                r11.writeInt(r3)
                return r13
            L_0x13e3:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x13f5
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x13f6
            L_0x13f5:
            L_0x13f6:
                r8.clearCrossProfileIntentFilters(r0)
                r23.writeNoException()
                return r13
            L_0x13fd:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x140f
                android.os.Parcelable$Creator<android.content.ComponentName> r1 = android.content.ComponentName.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r10)
                android.content.ComponentName r1 = (android.content.ComponentName) r1
                goto L_0x1410
            L_0x140f:
                r1 = r0
            L_0x1410:
                int r2 = r22.readInt()
                if (r2 == 0) goto L_0x141f
                android.os.Parcelable$Creator<android.content.IntentFilter> r0 = android.content.IntentFilter.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.IntentFilter r0 = (android.content.IntentFilter) r0
                goto L_0x1420
            L_0x141f:
            L_0x1420:
                int r2 = r22.readInt()
                r8.addCrossProfileIntentFilter(r1, r0, r2)
                r23.writeNoException()
                return r13
            L_0x142b:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x143d
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x143e
            L_0x143d:
            L_0x143e:
                android.os.Bundle r1 = r8.getUserRestrictions(r0)
                r23.writeNoException()
                if (r1 == 0) goto L_0x144e
                r11.writeInt(r13)
                r1.writeToParcel(r11, r13)
                goto L_0x1451
            L_0x144e:
                r11.writeInt(r6)
            L_0x1451:
                return r13
            L_0x1452:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x1464
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x1465
            L_0x1464:
            L_0x1465:
                java.lang.String r1 = r22.readString()
                int r2 = r22.readInt()
                if (r2 == 0) goto L_0x1471
                r6 = r13
            L_0x1471:
                r2 = r6
                r8.setUserRestriction(r0, r1, r2)
                r23.writeNoException()
                return r13
            L_0x1479:
                r10.enforceInterface(r12)
                int r0 = r22.readInt()
                android.content.ComponentName r1 = r8.getRestrictionsProvider(r0)
                r23.writeNoException()
                if (r1 == 0) goto L_0x1490
                r11.writeInt(r13)
                r1.writeToParcel((android.os.Parcel) r11, (int) r13)
                goto L_0x1493
            L_0x1490:
                r11.writeInt(r6)
            L_0x1493:
                return r13
            L_0x1494:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x14a6
                android.os.Parcelable$Creator<android.content.ComponentName> r1 = android.content.ComponentName.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r10)
                android.content.ComponentName r1 = (android.content.ComponentName) r1
                goto L_0x14a7
            L_0x14a6:
                r1 = r0
            L_0x14a7:
                int r2 = r22.readInt()
                if (r2 == 0) goto L_0x14b6
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x14b7
            L_0x14b6:
            L_0x14b7:
                r8.setRestrictionsProvider(r1, r0)
                r23.writeNoException()
                return r13
            L_0x14be:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                boolean r1 = r8.isCallerApplicationRestrictionsManagingPackage(r0)
                r23.writeNoException()
                r11.writeInt(r1)
                return r13
            L_0x14d0:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x14e2
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x14e3
            L_0x14e2:
            L_0x14e3:
                java.lang.String r1 = r8.getApplicationRestrictionsManagingPackage(r0)
                r23.writeNoException()
                r11.writeString(r1)
                return r13
            L_0x14ee:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x1500
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x1501
            L_0x1500:
            L_0x1501:
                java.lang.String r1 = r22.readString()
                boolean r2 = r8.setApplicationRestrictionsManagingPackage(r0, r1)
                r23.writeNoException()
                r11.writeInt(r2)
                return r13
            L_0x1510:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x1522
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x1523
            L_0x1522:
            L_0x1523:
                java.lang.String r1 = r22.readString()
                java.lang.String r2 = r22.readString()
                android.os.Bundle r3 = r8.getApplicationRestrictions(r0, r1, r2)
                r23.writeNoException()
                if (r3 == 0) goto L_0x153b
                r11.writeInt(r13)
                r3.writeToParcel(r11, r13)
                goto L_0x153e
            L_0x153b:
                r11.writeInt(r6)
            L_0x153e:
                return r13
            L_0x153f:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x1551
                android.os.Parcelable$Creator<android.content.ComponentName> r1 = android.content.ComponentName.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r10)
                android.content.ComponentName r1 = (android.content.ComponentName) r1
                goto L_0x1552
            L_0x1551:
                r1 = r0
            L_0x1552:
                java.lang.String r2 = r22.readString()
                java.lang.String r3 = r22.readString()
                int r4 = r22.readInt()
                if (r4 == 0) goto L_0x1569
                android.os.Parcelable$Creator<android.os.Bundle> r0 = android.os.Bundle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.os.Bundle r0 = (android.os.Bundle) r0
                goto L_0x156a
            L_0x1569:
            L_0x156a:
                r8.setApplicationRestrictions(r1, r2, r3, r0)
                r23.writeNoException()
                return r13
            L_0x1571:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x1583
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x1584
            L_0x1583:
            L_0x1584:
                java.lang.String r1 = r22.readString()
                r8.setDefaultSmsApplication(r0, r1)
                r23.writeNoException()
                return r13
            L_0x158f:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x15a1
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x15a2
            L_0x15a1:
            L_0x15a2:
                java.lang.String r1 = r22.readString()
                r8.clearPackagePersistentPreferredActivities(r0, r1)
                r23.writeNoException()
                return r13
            L_0x15ad:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x15bf
                android.os.Parcelable$Creator<android.content.ComponentName> r1 = android.content.ComponentName.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r10)
                android.content.ComponentName r1 = (android.content.ComponentName) r1
                goto L_0x15c0
            L_0x15bf:
                r1 = r0
            L_0x15c0:
                int r2 = r22.readInt()
                if (r2 == 0) goto L_0x15cf
                android.os.Parcelable$Creator<android.content.IntentFilter> r2 = android.content.IntentFilter.CREATOR
                java.lang.Object r2 = r2.createFromParcel(r10)
                android.content.IntentFilter r2 = (android.content.IntentFilter) r2
                goto L_0x15d0
            L_0x15cf:
                r2 = r0
            L_0x15d0:
                int r3 = r22.readInt()
                if (r3 == 0) goto L_0x15df
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x15e0
            L_0x15df:
            L_0x15e0:
                r8.addPersistentPreferredActivity(r1, r2, r0)
                r23.writeNoException()
                return r13
            L_0x15e7:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x15f9
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x15fa
            L_0x15f9:
            L_0x15fa:
                java.util.List r1 = r8.getAlwaysOnVpnLockdownWhitelist(r0)
                r23.writeNoException()
                r11.writeStringList(r1)
                return r13
            L_0x1605:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x1617
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x1618
            L_0x1617:
            L_0x1618:
                boolean r1 = r8.isAlwaysOnVpnLockdownEnabled(r0)
                r23.writeNoException()
                r11.writeInt(r1)
                return r13
            L_0x1623:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x1635
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x1636
            L_0x1635:
            L_0x1636:
                java.lang.String r1 = r8.getAlwaysOnVpnPackage(r0)
                r23.writeNoException()
                r11.writeString(r1)
                return r13
            L_0x1641:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x1653
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x1654
            L_0x1653:
            L_0x1654:
                java.lang.String r1 = r22.readString()
                int r2 = r22.readInt()
                if (r2 == 0) goto L_0x1660
                r6 = r13
            L_0x1660:
                r2 = r6
                java.util.ArrayList r3 = r22.createStringArrayList()
                boolean r4 = r8.setAlwaysOnVpnPackage(r0, r1, r2, r3)
                r23.writeNoException()
                r11.writeInt(r4)
                return r13
            L_0x1670:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x1682
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x1683
            L_0x1682:
            L_0x1683:
                java.lang.String r1 = r8.getCertInstallerPackage(r0)
                r23.writeNoException()
                r11.writeString(r1)
                return r13
            L_0x168e:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x16a0
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x16a1
            L_0x16a0:
            L_0x16a1:
                java.lang.String r1 = r22.readString()
                r8.setCertInstallerPackage(r0, r1)
                r23.writeNoException()
                return r13
            L_0x16ac:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x16be
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x16bf
            L_0x16be:
            L_0x16bf:
                java.lang.String r1 = r22.readString()
                java.util.List r2 = r8.getDelegatePackages(r0, r1)
                r23.writeNoException()
                r11.writeStringList(r2)
                return r13
            L_0x16ce:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x16e0
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x16e1
            L_0x16e0:
            L_0x16e1:
                java.lang.String r1 = r22.readString()
                java.util.List r2 = r8.getDelegatedScopes(r0, r1)
                r23.writeNoException()
                r11.writeStringList(r2)
                return r13
            L_0x16f0:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x1702
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x1703
            L_0x1702:
            L_0x1703:
                java.lang.String r1 = r22.readString()
                java.util.ArrayList r2 = r22.createStringArrayList()
                r8.setDelegatedScopes(r0, r1, r2)
                r23.writeNoException()
                return r13
            L_0x1712:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                int r2 = r22.readInt()
                if (r2 == 0) goto L_0x1728
                android.os.Parcelable$Creator<android.net.Uri> r0 = android.net.Uri.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.net.Uri r0 = (android.net.Uri) r0
                goto L_0x1729
            L_0x1728:
            L_0x1729:
                java.lang.String r2 = r22.readString()
                android.os.IBinder r3 = r22.readStrongBinder()
                r8.choosePrivateKeyAlias(r1, r0, r2, r3)
                r23.writeNoException()
                return r13
            L_0x1738:
                boolean r0 = r8.onTransact$setKeyPairCertificate$(r10, r11)
                return r0
            L_0x173d:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x174f
                android.os.Parcelable$Creator<android.content.ComponentName> r1 = android.content.ComponentName.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r10)
                android.content.ComponentName r1 = (android.content.ComponentName) r1
                goto L_0x1750
            L_0x174f:
                r1 = r0
            L_0x1750:
                java.lang.String r7 = r22.readString()
                java.lang.String r14 = r22.readString()
                int r2 = r22.readInt()
                if (r2 == 0) goto L_0x1768
                android.os.Parcelable$Creator<android.security.keystore.ParcelableKeyGenParameterSpec> r0 = android.security.keystore.ParcelableKeyGenParameterSpec.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.security.keystore.ParcelableKeyGenParameterSpec r0 = (android.security.keystore.ParcelableKeyGenParameterSpec) r0
            L_0x1766:
                r4 = r0
                goto L_0x1769
            L_0x1768:
                goto L_0x1766
            L_0x1769:
                int r15 = r22.readInt()
                android.security.keymaster.KeymasterCertificateChain r0 = new android.security.keymaster.KeymasterCertificateChain
                r0.<init>()
                r6 = r0
                r0 = r20
                r2 = r7
                r3 = r14
                r5 = r15
                r19 = r6
                boolean r0 = r0.generateKeyPair(r1, r2, r3, r4, r5, r6)
                r23.writeNoException()
                r11.writeInt(r0)
                r11.writeInt(r13)
                r2 = r19
                r2.writeToParcel(r11, r13)
                return r13
            L_0x178e:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x17a0
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x17a1
            L_0x17a0:
            L_0x17a1:
                java.lang.String r1 = r22.readString()
                java.lang.String r2 = r22.readString()
                boolean r3 = r8.removeKeyPair(r0, r1, r2)
                r23.writeNoException()
                r11.writeInt(r3)
                return r13
            L_0x17b4:
                boolean r0 = r8.onTransact$installKeyPair$(r10, r11)
                return r0
            L_0x17b9:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                int r1 = r22.readInt()
                boolean r2 = r8.isCaCertApproved(r0, r1)
                r23.writeNoException()
                r11.writeInt(r2)
                return r13
            L_0x17cf:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                int r1 = r22.readInt()
                int r2 = r22.readInt()
                if (r2 == 0) goto L_0x17e2
                r6 = r13
            L_0x17e2:
                r2 = r6
                boolean r3 = r8.approveCaCert(r0, r1, r2)
                r23.writeNoException()
                r11.writeInt(r3)
                return r13
            L_0x17ee:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x1800
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x1801
            L_0x1800:
            L_0x1801:
                java.lang.String r1 = r22.readString()
                r8.enforceCanManageCaCerts(r0, r1)
                r23.writeNoException()
                return r13
            L_0x180c:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x181e
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x181f
            L_0x181e:
            L_0x181f:
                java.lang.String r1 = r22.readString()
                java.lang.String[] r2 = r22.createStringArray()
                r8.uninstallCaCerts(r0, r1, r2)
                r23.writeNoException()
                return r13
            L_0x182e:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x1840
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x1841
            L_0x1840:
            L_0x1841:
                java.lang.String r1 = r22.readString()
                byte[] r2 = r22.createByteArray()
                boolean r3 = r8.installCaCert(r0, r1, r2)
                r23.writeNoException()
                r11.writeInt(r3)
                return r13
            L_0x1854:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x1866
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x1867
            L_0x1866:
            L_0x1867:
                java.lang.String r1 = r22.readString()
                java.lang.String r2 = r22.readString()
                boolean r3 = r8.isPackageSuspended(r0, r1, r2)
                r23.writeNoException()
                r11.writeInt(r3)
                return r13
            L_0x187a:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x188c
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x188d
            L_0x188c:
            L_0x188d:
                java.lang.String r1 = r22.readString()
                java.lang.String[] r2 = r22.createStringArray()
                int r3 = r22.readInt()
                if (r3 == 0) goto L_0x189d
                r6 = r13
            L_0x189d:
                r3 = r6
                java.lang.String[] r4 = r8.setPackagesSuspended(r0, r1, r2, r3)
                r23.writeNoException()
                r11.writeStringArray(r4)
                return r13
            L_0x18a9:
                r10.enforceInterface(r12)
                java.lang.CharSequence r0 = r20.getDeviceOwnerLockScreenInfo()
                r23.writeNoException()
                if (r0 == 0) goto L_0x18bc
                r11.writeInt(r13)
                android.text.TextUtils.writeToParcel(r0, r11, r13)
                goto L_0x18bf
            L_0x18bc:
                r11.writeInt(r6)
            L_0x18bf:
                return r13
            L_0x18c0:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x18d2
                android.os.Parcelable$Creator<android.content.ComponentName> r1 = android.content.ComponentName.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r10)
                android.content.ComponentName r1 = (android.content.ComponentName) r1
                goto L_0x18d3
            L_0x18d2:
                r1 = r0
            L_0x18d3:
                int r2 = r22.readInt()
                if (r2 == 0) goto L_0x18e2
                android.os.Parcelable$Creator<java.lang.CharSequence> r0 = android.text.TextUtils.CHAR_SEQUENCE_CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                java.lang.CharSequence r0 = (java.lang.CharSequence) r0
                goto L_0x18e3
            L_0x18e2:
            L_0x18e3:
                r8.setDeviceOwnerLockScreenInfo(r1, r0)
                r23.writeNoException()
                return r13
            L_0x18ea:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                int r1 = r22.readInt()
                int r2 = r22.readInt()
                boolean r3 = r8.checkDeviceIdentifierAccess(r0, r1, r2)
                r23.writeNoException()
                r11.writeInt(r3)
                return r13
            L_0x1904:
                r10.enforceInterface(r12)
                boolean r0 = r20.hasUserSetupCompleted()
                r23.writeNoException()
                r11.writeInt(r0)
                return r13
            L_0x1912:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x1924
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x1925
            L_0x1924:
            L_0x1925:
                r8.clearProfileOwner(r0)
                r23.writeNoException()
                return r13
            L_0x192c:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x193e
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x193f
            L_0x193e:
            L_0x193f:
                java.lang.String r1 = r22.readString()
                r8.setProfileName(r0, r1)
                r23.writeNoException()
                return r13
            L_0x194a:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x195c
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x195d
            L_0x195c:
            L_0x195d:
                r8.setProfileEnabled(r0)
                r23.writeNoException()
                return r13
            L_0x1964:
                r10.enforceInterface(r12)
                int r0 = r22.readInt()
                java.lang.String r1 = r8.getProfileOwnerName(r0)
                r23.writeNoException()
                r11.writeString(r1)
                return r13
            L_0x1976:
                r10.enforceInterface(r12)
                int r0 = r22.readInt()
                android.content.ComponentName r1 = r8.getProfileOwner(r0)
                r23.writeNoException()
                if (r1 == 0) goto L_0x198d
                r11.writeInt(r13)
                r1.writeToParcel((android.os.Parcel) r11, (int) r13)
                goto L_0x1990
            L_0x198d:
                r11.writeInt(r6)
            L_0x1990:
                return r13
            L_0x1991:
                r10.enforceInterface(r12)
                int r0 = r22.readInt()
                android.content.ComponentName r1 = r8.getProfileOwnerAsUser(r0)
                r23.writeNoException()
                if (r1 == 0) goto L_0x19a8
                r11.writeInt(r13)
                r1.writeToParcel((android.os.Parcel) r11, (int) r13)
                goto L_0x19ab
            L_0x19a8:
                r11.writeInt(r6)
            L_0x19ab:
                return r13
            L_0x19ac:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x19be
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x19bf
            L_0x19be:
            L_0x19bf:
                java.lang.String r1 = r22.readString()
                int r2 = r22.readInt()
                boolean r3 = r8.setProfileOwner(r0, r1, r2)
                r23.writeNoException()
                r11.writeInt(r3)
                return r13
            L_0x19d2:
                r10.enforceInterface(r12)
                int r0 = r20.getDeviceOwnerUserId()
                r23.writeNoException()
                r11.writeInt(r0)
                return r13
            L_0x19e0:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                r8.clearDeviceOwner(r0)
                r23.writeNoException()
                return r13
            L_0x19ee:
                r10.enforceInterface(r12)
                java.lang.String r0 = r20.getDeviceOwnerName()
                r23.writeNoException()
                r11.writeString(r0)
                return r13
            L_0x19fc:
                r10.enforceInterface(r12)
                boolean r0 = r20.hasDeviceOwner()
                r23.writeNoException()
                r11.writeInt(r0)
                return r13
            L_0x1a0a:
                r10.enforceInterface(r12)
                int r0 = r22.readInt()
                if (r0 == 0) goto L_0x1a15
                r0 = r13
                goto L_0x1a16
            L_0x1a15:
                r0 = r6
            L_0x1a16:
                android.content.ComponentName r1 = r8.getDeviceOwnerComponent(r0)
                r23.writeNoException()
                if (r1 == 0) goto L_0x1a26
                r11.writeInt(r13)
                r1.writeToParcel((android.os.Parcel) r11, (int) r13)
                goto L_0x1a29
            L_0x1a26:
                r11.writeInt(r6)
            L_0x1a29:
                return r13
            L_0x1a2a:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x1a3c
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x1a3d
            L_0x1a3c:
            L_0x1a3d:
                java.lang.String r1 = r22.readString()
                int r2 = r22.readInt()
                boolean r3 = r8.setDeviceOwner(r0, r1, r2)
                r23.writeNoException()
                r11.writeInt(r3)
                return r13
            L_0x1a50:
                r10.enforceInterface(r12)
                int r0 = r22.readInt()
                r8.reportKeyguardSecured(r0)
                r23.writeNoException()
                return r13
            L_0x1a5e:
                r10.enforceInterface(r12)
                int r0 = r22.readInt()
                r8.reportKeyguardDismissed(r0)
                r23.writeNoException()
                return r13
            L_0x1a6c:
                r10.enforceInterface(r12)
                int r0 = r22.readInt()
                r8.reportSuccessfulBiometricAttempt(r0)
                r23.writeNoException()
                return r13
            L_0x1a7a:
                r10.enforceInterface(r12)
                int r0 = r22.readInt()
                r8.reportFailedBiometricAttempt(r0)
                r23.writeNoException()
                return r13
            L_0x1a88:
                r10.enforceInterface(r12)
                int r0 = r22.readInt()
                r8.reportSuccessfulPasswordAttempt(r0)
                r23.writeNoException()
                return r13
            L_0x1a96:
                r10.enforceInterface(r12)
                int r0 = r22.readInt()
                r8.reportFailedPasswordAttempt(r0)
                r23.writeNoException()
                return r13
            L_0x1aa4:
                r10.enforceInterface(r12)
                int r0 = r22.readInt()
                r8.reportPasswordChanged(r0)
                r23.writeNoException()
                return r13
            L_0x1ab2:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x1ac4
                android.os.Parcelable$Creator<android.app.admin.PasswordMetrics> r0 = android.app.admin.PasswordMetrics.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.app.admin.PasswordMetrics r0 = (android.app.admin.PasswordMetrics) r0
                goto L_0x1ac5
            L_0x1ac4:
            L_0x1ac5:
                int r1 = r22.readInt()
                r8.setActivePasswordState(r0, r1)
                r23.writeNoException()
                return r13
            L_0x1ad0:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x1ae2
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x1ae3
            L_0x1ae2:
            L_0x1ae3:
                int r1 = r22.readInt()
                int r2 = r22.readInt()
                boolean r3 = r8.hasGrantedPolicy(r0, r1, r2)
                r23.writeNoException()
                r11.writeInt(r3)
                return r13
            L_0x1af6:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x1b08
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x1b09
            L_0x1b08:
            L_0x1b09:
                int r1 = r22.readInt()
                r8.forceRemoveActiveAdmin(r0, r1)
                r23.writeNoException()
                return r13
            L_0x1b14:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x1b26
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x1b27
            L_0x1b26:
            L_0x1b27:
                int r1 = r22.readInt()
                r8.removeActiveAdmin(r0, r1)
                r23.writeNoException()
                return r13
            L_0x1b32:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x1b44
                android.os.Parcelable$Creator<android.content.ComponentName> r1 = android.content.ComponentName.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r10)
                android.content.ComponentName r1 = (android.content.ComponentName) r1
                goto L_0x1b45
            L_0x1b44:
                r1 = r0
            L_0x1b45:
                int r2 = r22.readInt()
                if (r2 == 0) goto L_0x1b54
                android.os.Parcelable$Creator<android.os.RemoteCallback> r0 = android.os.RemoteCallback.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.os.RemoteCallback r0 = (android.os.RemoteCallback) r0
                goto L_0x1b55
            L_0x1b54:
            L_0x1b55:
                int r2 = r22.readInt()
                r8.getRemoveWarning(r1, r0, r2)
                r23.writeNoException()
                return r13
            L_0x1b60:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                int r1 = r22.readInt()
                boolean r2 = r8.packageHasActiveAdmins(r0, r1)
                r23.writeNoException()
                r11.writeInt(r2)
                return r13
            L_0x1b76:
                r10.enforceInterface(r12)
                int r0 = r22.readInt()
                java.util.List r1 = r8.getActiveAdmins(r0)
                r23.writeNoException()
                r11.writeTypedList(r1)
                return r13
            L_0x1b88:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x1b9a
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x1b9b
            L_0x1b9a:
            L_0x1b9b:
                int r1 = r22.readInt()
                boolean r2 = r8.isAdminActive(r0, r1)
                r23.writeNoException()
                r11.writeInt(r2)
                return r13
            L_0x1baa:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x1bbc
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x1bbd
            L_0x1bbc:
            L_0x1bbd:
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x1bc5
                r6 = r13
            L_0x1bc5:
                r1 = r6
                int r2 = r22.readInt()
                r8.setActiveAdmin(r0, r1, r2)
                r23.writeNoException()
                return r13
            L_0x1bd1:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x1be3
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x1be4
            L_0x1be3:
            L_0x1be4:
                int r1 = r22.readInt()
                int r2 = r22.readInt()
                if (r2 == 0) goto L_0x1bf0
                r6 = r13
            L_0x1bf0:
                r2 = r6
                int r3 = r8.getKeyguardDisabledFeatures(r0, r1, r2)
                r23.writeNoException()
                r11.writeInt(r3)
                return r13
            L_0x1bfc:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x1c0e
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x1c0f
            L_0x1c0e:
            L_0x1c0f:
                int r1 = r22.readInt()
                int r2 = r22.readInt()
                if (r2 == 0) goto L_0x1c1b
                r6 = r13
            L_0x1c1b:
                r2 = r6
                r8.setKeyguardDisabledFeatures(r0, r1, r2)
                r23.writeNoException()
                return r13
            L_0x1c23:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x1c35
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x1c36
            L_0x1c35:
            L_0x1c36:
                int r1 = r22.readInt()
                boolean r2 = r8.getScreenCaptureDisabled(r0, r1)
                r23.writeNoException()
                r11.writeInt(r2)
                return r13
            L_0x1c45:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x1c57
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x1c58
            L_0x1c57:
            L_0x1c58:
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x1c60
                r6 = r13
            L_0x1c60:
                r1 = r6
                r8.setScreenCaptureDisabled(r0, r1)
                r23.writeNoException()
                return r13
            L_0x1c68:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x1c7a
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x1c7b
            L_0x1c7a:
            L_0x1c7b:
                int r1 = r22.readInt()
                boolean r2 = r8.getCameraDisabled(r0, r1)
                r23.writeNoException()
                r11.writeInt(r2)
                return r13
            L_0x1c8a:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x1c9c
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x1c9d
            L_0x1c9c:
            L_0x1c9d:
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x1ca5
                r6 = r13
            L_0x1ca5:
                r1 = r6
                r8.setCameraDisabled(r0, r1)
                r23.writeNoException()
                return r13
            L_0x1cad:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x1cbf
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x1cc0
            L_0x1cbf:
            L_0x1cc0:
                boolean r1 = r8.requestBugreport(r0)
                r23.writeNoException()
                r11.writeInt(r1)
                return r13
            L_0x1ccb:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                int r1 = r22.readInt()
                int r2 = r8.getStorageEncryptionStatus(r0, r1)
                r23.writeNoException()
                r11.writeInt(r2)
                return r13
            L_0x1ce1:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x1cf3
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x1cf4
            L_0x1cf3:
            L_0x1cf4:
                int r1 = r22.readInt()
                boolean r2 = r8.getStorageEncryption(r0, r1)
                r23.writeNoException()
                r11.writeInt(r2)
                return r13
            L_0x1d03:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x1d15
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x1d16
            L_0x1d15:
            L_0x1d16:
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x1d1e
                r6 = r13
            L_0x1d1e:
                r1 = r6
                int r2 = r8.setStorageEncryption(r0, r1)
                r23.writeNoException()
                r11.writeInt(r2)
                return r13
            L_0x1d2a:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x1d3c
                android.os.Parcelable$Creator<android.content.ComponentName> r1 = android.content.ComponentName.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r10)
                android.content.ComponentName r1 = (android.content.ComponentName) r1
                goto L_0x1d3d
            L_0x1d3c:
                r1 = r0
            L_0x1d3d:
                int r2 = r22.readInt()
                if (r2 == 0) goto L_0x1d4c
                android.os.Parcelable$Creator<android.net.ProxyInfo> r0 = android.net.ProxyInfo.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.net.ProxyInfo r0 = (android.net.ProxyInfo) r0
                goto L_0x1d4d
            L_0x1d4c:
            L_0x1d4d:
                r8.setRecommendedGlobalProxy(r1, r0)
                r23.writeNoException()
                return r13
            L_0x1d54:
                r10.enforceInterface(r12)
                int r0 = r22.readInt()
                android.content.ComponentName r1 = r8.getGlobalProxyAdmin(r0)
                r23.writeNoException()
                if (r1 == 0) goto L_0x1d6b
                r11.writeInt(r13)
                r1.writeToParcel((android.os.Parcel) r11, (int) r13)
                goto L_0x1d6e
            L_0x1d6b:
                r11.writeInt(r6)
            L_0x1d6e:
                return r13
            L_0x1d6f:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x1d81
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x1d82
            L_0x1d81:
            L_0x1d82:
                java.lang.String r1 = r22.readString()
                java.lang.String r2 = r22.readString()
                android.content.ComponentName r3 = r8.setGlobalProxy(r0, r1, r2)
                r23.writeNoException()
                if (r3 == 0) goto L_0x1d9a
                r11.writeInt(r13)
                r3.writeToParcel((android.os.Parcel) r11, (int) r13)
                goto L_0x1d9d
            L_0x1d9a:
                r11.writeInt(r6)
            L_0x1d9d:
                return r13
            L_0x1d9e:
                r10.enforceInterface(r12)
                int r0 = r22.readInt()
                java.lang.String r1 = r22.readString()
                r8.wipeDataWithReason(r0, r1)
                r23.writeNoException()
                return r13
            L_0x1db0:
                r10.enforceInterface(r12)
                int r0 = r22.readInt()
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x1dbf
                r6 = r13
            L_0x1dbf:
                r1 = r6
                r8.lockNow(r0, r1)
                r23.writeNoException()
                return r13
            L_0x1dc7:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x1dd9
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x1dda
            L_0x1dd9:
            L_0x1dda:
                int r1 = r22.readInt()
                int r2 = r22.readInt()
                if (r2 == 0) goto L_0x1de6
                r6 = r13
            L_0x1de6:
                r2 = r6
                long r3 = r8.getRequiredStrongAuthTimeout(r0, r1, r2)
                r23.writeNoException()
                r11.writeLong(r3)
                return r13
            L_0x1df2:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x1e04
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x1e05
            L_0x1e04:
            L_0x1e05:
                long r1 = r22.readLong()
                int r3 = r22.readInt()
                if (r3 == 0) goto L_0x1e11
                r6 = r13
            L_0x1e11:
                r3 = r6
                r8.setRequiredStrongAuthTimeout(r0, r1, r3)
                r23.writeNoException()
                return r13
            L_0x1e19:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x1e2b
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x1e2c
            L_0x1e2b:
            L_0x1e2c:
                int r1 = r22.readInt()
                int r2 = r22.readInt()
                if (r2 == 0) goto L_0x1e38
                r6 = r13
            L_0x1e38:
                r2 = r6
                long r3 = r8.getMaximumTimeToLock(r0, r1, r2)
                r23.writeNoException()
                r11.writeLong(r3)
                return r13
            L_0x1e44:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x1e56
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x1e57
            L_0x1e56:
            L_0x1e57:
                long r1 = r22.readLong()
                int r3 = r22.readInt()
                if (r3 == 0) goto L_0x1e63
                r6 = r13
            L_0x1e63:
                r3 = r6
                r8.setMaximumTimeToLock(r0, r1, r3)
                r23.writeNoException()
                return r13
            L_0x1e6b:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                int r1 = r22.readInt()
                boolean r2 = r8.resetPassword(r0, r1)
                r23.writeNoException()
                r11.writeInt(r2)
                return r13
            L_0x1e81:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x1e93
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x1e94
            L_0x1e93:
            L_0x1e94:
                int r1 = r22.readInt()
                int r2 = r22.readInt()
                if (r2 == 0) goto L_0x1ea0
                r6 = r13
            L_0x1ea0:
                r2 = r6
                int r3 = r8.getMaximumFailedPasswordsForWipe(r0, r1, r2)
                r23.writeNoException()
                r11.writeInt(r3)
                return r13
            L_0x1eac:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x1ebe
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x1ebf
            L_0x1ebe:
            L_0x1ebf:
                int r1 = r22.readInt()
                int r2 = r22.readInt()
                if (r2 == 0) goto L_0x1ecb
                r6 = r13
            L_0x1ecb:
                r2 = r6
                r8.setMaximumFailedPasswordsForWipe(r0, r1, r2)
                r23.writeNoException()
                return r13
            L_0x1ed3:
                r10.enforceInterface(r12)
                int r0 = r22.readInt()
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x1ee2
                r6 = r13
            L_0x1ee2:
                r1 = r6
                int r2 = r8.getProfileWithMinimumFailedPasswordsForWipe(r0, r1)
                r23.writeNoException()
                r11.writeInt(r2)
                return r13
            L_0x1eee:
                r10.enforceInterface(r12)
                int r0 = r22.readInt()
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x1efd
                r6 = r13
            L_0x1efd:
                r1 = r6
                int r2 = r8.getCurrentFailedPasswordAttempts(r0, r1)
                r23.writeNoException()
                r11.writeInt(r2)
                return r13
            L_0x1f09:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x1f1b
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x1f1c
            L_0x1f1b:
            L_0x1f1c:
                boolean r1 = r8.isUsingUnifiedPassword(r0)
                r23.writeNoException()
                r11.writeInt(r1)
                return r13
            L_0x1f27:
                r10.enforceInterface(r12)
                int r0 = r20.getPasswordComplexity()
                r23.writeNoException()
                r11.writeInt(r0)
                return r13
            L_0x1f35:
                r10.enforceInterface(r12)
                int r0 = r22.readInt()
                boolean r1 = r8.isProfileActivePasswordSufficientForParent(r0)
                r23.writeNoException()
                r11.writeInt(r1)
                return r13
            L_0x1f47:
                r10.enforceInterface(r12)
                int r0 = r22.readInt()
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x1f56
                r6 = r13
            L_0x1f56:
                r1 = r6
                boolean r2 = r8.isActivePasswordSufficient(r0, r1)
                r23.writeNoException()
                r11.writeInt(r2)
                return r13
            L_0x1f62:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x1f74
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x1f75
            L_0x1f74:
            L_0x1f75:
                int r1 = r22.readInt()
                int r2 = r22.readInt()
                if (r2 == 0) goto L_0x1f81
                r6 = r13
            L_0x1f81:
                r2 = r6
                long r3 = r8.getPasswordExpiration(r0, r1, r2)
                r23.writeNoException()
                r11.writeLong(r3)
                return r13
            L_0x1f8d:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x1f9f
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x1fa0
            L_0x1f9f:
            L_0x1fa0:
                int r1 = r22.readInt()
                int r2 = r22.readInt()
                if (r2 == 0) goto L_0x1fac
                r6 = r13
            L_0x1fac:
                r2 = r6
                long r3 = r8.getPasswordExpirationTimeout(r0, r1, r2)
                r23.writeNoException()
                r11.writeLong(r3)
                return r13
            L_0x1fb8:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x1fca
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x1fcb
            L_0x1fca:
            L_0x1fcb:
                long r1 = r22.readLong()
                int r3 = r22.readInt()
                if (r3 == 0) goto L_0x1fd7
                r6 = r13
            L_0x1fd7:
                r3 = r6
                r8.setPasswordExpirationTimeout(r0, r1, r3)
                r23.writeNoException()
                return r13
            L_0x1fdf:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x1ff1
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x1ff2
            L_0x1ff1:
            L_0x1ff2:
                int r1 = r22.readInt()
                int r2 = r22.readInt()
                if (r2 == 0) goto L_0x1ffe
                r6 = r13
            L_0x1ffe:
                r2 = r6
                int r3 = r8.getPasswordHistoryLength(r0, r1, r2)
                r23.writeNoException()
                r11.writeInt(r3)
                return r13
            L_0x200a:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x201c
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x201d
            L_0x201c:
            L_0x201d:
                int r1 = r22.readInt()
                int r2 = r22.readInt()
                if (r2 == 0) goto L_0x2029
                r6 = r13
            L_0x2029:
                r2 = r6
                r8.setPasswordHistoryLength(r0, r1, r2)
                r23.writeNoException()
                return r13
            L_0x2031:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x2043
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x2044
            L_0x2043:
            L_0x2044:
                int r1 = r22.readInt()
                int r2 = r22.readInt()
                if (r2 == 0) goto L_0x2050
                r6 = r13
            L_0x2050:
                r2 = r6
                int r3 = r8.getPasswordMinimumNonLetter(r0, r1, r2)
                r23.writeNoException()
                r11.writeInt(r3)
                return r13
            L_0x205c:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x206e
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x206f
            L_0x206e:
            L_0x206f:
                int r1 = r22.readInt()
                int r2 = r22.readInt()
                if (r2 == 0) goto L_0x207b
                r6 = r13
            L_0x207b:
                r2 = r6
                r8.setPasswordMinimumNonLetter(r0, r1, r2)
                r23.writeNoException()
                return r13
            L_0x2083:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x2095
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x2096
            L_0x2095:
            L_0x2096:
                int r1 = r22.readInt()
                int r2 = r22.readInt()
                if (r2 == 0) goto L_0x20a2
                r6 = r13
            L_0x20a2:
                r2 = r6
                int r3 = r8.getPasswordMinimumSymbols(r0, r1, r2)
                r23.writeNoException()
                r11.writeInt(r3)
                return r13
            L_0x20ae:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x20c0
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x20c1
            L_0x20c0:
            L_0x20c1:
                int r1 = r22.readInt()
                int r2 = r22.readInt()
                if (r2 == 0) goto L_0x20cd
                r6 = r13
            L_0x20cd:
                r2 = r6
                r8.setPasswordMinimumSymbols(r0, r1, r2)
                r23.writeNoException()
                return r13
            L_0x20d5:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x20e7
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x20e8
            L_0x20e7:
            L_0x20e8:
                int r1 = r22.readInt()
                int r2 = r22.readInt()
                if (r2 == 0) goto L_0x20f4
                r6 = r13
            L_0x20f4:
                r2 = r6
                int r3 = r8.getPasswordMinimumNumeric(r0, r1, r2)
                r23.writeNoException()
                r11.writeInt(r3)
                return r13
            L_0x2100:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x2112
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x2113
            L_0x2112:
            L_0x2113:
                int r1 = r22.readInt()
                int r2 = r22.readInt()
                if (r2 == 0) goto L_0x211f
                r6 = r13
            L_0x211f:
                r2 = r6
                r8.setPasswordMinimumNumeric(r0, r1, r2)
                r23.writeNoException()
                return r13
            L_0x2127:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x2139
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x213a
            L_0x2139:
            L_0x213a:
                int r1 = r22.readInt()
                int r2 = r22.readInt()
                if (r2 == 0) goto L_0x2146
                r6 = r13
            L_0x2146:
                r2 = r6
                int r3 = r8.getPasswordMinimumLetters(r0, r1, r2)
                r23.writeNoException()
                r11.writeInt(r3)
                return r13
            L_0x2152:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x2164
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x2165
            L_0x2164:
            L_0x2165:
                int r1 = r22.readInt()
                int r2 = r22.readInt()
                if (r2 == 0) goto L_0x2171
                r6 = r13
            L_0x2171:
                r2 = r6
                r8.setPasswordMinimumLetters(r0, r1, r2)
                r23.writeNoException()
                return r13
            L_0x2179:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x218b
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x218c
            L_0x218b:
            L_0x218c:
                int r1 = r22.readInt()
                int r2 = r22.readInt()
                if (r2 == 0) goto L_0x2198
                r6 = r13
            L_0x2198:
                r2 = r6
                int r3 = r8.getPasswordMinimumLowerCase(r0, r1, r2)
                r23.writeNoException()
                r11.writeInt(r3)
                return r13
            L_0x21a4:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x21b6
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x21b7
            L_0x21b6:
            L_0x21b7:
                int r1 = r22.readInt()
                int r2 = r22.readInt()
                if (r2 == 0) goto L_0x21c3
                r6 = r13
            L_0x21c3:
                r2 = r6
                r8.setPasswordMinimumLowerCase(r0, r1, r2)
                r23.writeNoException()
                return r13
            L_0x21cb:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x21dd
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x21de
            L_0x21dd:
            L_0x21de:
                int r1 = r22.readInt()
                int r2 = r22.readInt()
                if (r2 == 0) goto L_0x21ea
                r6 = r13
            L_0x21ea:
                r2 = r6
                int r3 = r8.getPasswordMinimumUpperCase(r0, r1, r2)
                r23.writeNoException()
                r11.writeInt(r3)
                return r13
            L_0x21f6:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x2208
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x2209
            L_0x2208:
            L_0x2209:
                int r1 = r22.readInt()
                int r2 = r22.readInt()
                if (r2 == 0) goto L_0x2215
                r6 = r13
            L_0x2215:
                r2 = r6
                r8.setPasswordMinimumUpperCase(r0, r1, r2)
                r23.writeNoException()
                return r13
            L_0x221d:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x222f
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x2230
            L_0x222f:
            L_0x2230:
                int r1 = r22.readInt()
                int r2 = r22.readInt()
                if (r2 == 0) goto L_0x223c
                r6 = r13
            L_0x223c:
                r2 = r6
                int r3 = r8.getPasswordMinimumLength(r0, r1, r2)
                r23.writeNoException()
                r11.writeInt(r3)
                return r13
            L_0x2248:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x225a
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x225b
            L_0x225a:
            L_0x225b:
                int r1 = r22.readInt()
                int r2 = r22.readInt()
                if (r2 == 0) goto L_0x2267
                r6 = r13
            L_0x2267:
                r2 = r6
                r8.setPasswordMinimumLength(r0, r1, r2)
                r23.writeNoException()
                return r13
            L_0x226f:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x2281
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x2282
            L_0x2281:
            L_0x2282:
                int r1 = r22.readInt()
                int r2 = r22.readInt()
                if (r2 == 0) goto L_0x228e
                r6 = r13
            L_0x228e:
                r2 = r6
                int r3 = r8.getPasswordQuality(r0, r1, r2)
                r23.writeNoException()
                r11.writeInt(r3)
                return r13
            L_0x229a:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x22ac
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x22ad
            L_0x22ac:
            L_0x22ad:
                int r1 = r22.readInt()
                int r2 = r22.readInt()
                if (r2 == 0) goto L_0x22b9
                r6 = r13
            L_0x22b9:
                r2 = r6
                r8.setPasswordQuality(r0, r1, r2)
                r23.writeNoException()
                return r13
            L_0x22c1:
                r11.writeString(r12)
                return r13
            */
            throw new UnsupportedOperationException("Method not decompiled: android.app.admin.IDevicePolicyManager.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IDevicePolicyManager {
            public static IDevicePolicyManager sDefaultImpl;
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

            public void setPasswordQuality(ComponentName who, int quality, boolean parent) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (who != null) {
                        _data.writeInt(1);
                        who.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(quality);
                    _data.writeInt(parent);
                    if (this.mRemote.transact(1, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setPasswordQuality(who, quality, parent);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getPasswordQuality(ComponentName who, int userHandle, boolean parent) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (who != null) {
                        _data.writeInt(1);
                        who.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(userHandle);
                    _data.writeInt(parent);
                    if (!this.mRemote.transact(2, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPasswordQuality(who, userHandle, parent);
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

            public void setPasswordMinimumLength(ComponentName who, int length, boolean parent) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (who != null) {
                        _data.writeInt(1);
                        who.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(length);
                    _data.writeInt(parent);
                    if (this.mRemote.transact(3, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setPasswordMinimumLength(who, length, parent);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getPasswordMinimumLength(ComponentName who, int userHandle, boolean parent) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (who != null) {
                        _data.writeInt(1);
                        who.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(userHandle);
                    _data.writeInt(parent);
                    if (!this.mRemote.transact(4, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPasswordMinimumLength(who, userHandle, parent);
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

            public void setPasswordMinimumUpperCase(ComponentName who, int length, boolean parent) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (who != null) {
                        _data.writeInt(1);
                        who.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(length);
                    _data.writeInt(parent);
                    if (this.mRemote.transact(5, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setPasswordMinimumUpperCase(who, length, parent);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getPasswordMinimumUpperCase(ComponentName who, int userHandle, boolean parent) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (who != null) {
                        _data.writeInt(1);
                        who.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(userHandle);
                    _data.writeInt(parent);
                    if (!this.mRemote.transact(6, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPasswordMinimumUpperCase(who, userHandle, parent);
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

            public void setPasswordMinimumLowerCase(ComponentName who, int length, boolean parent) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (who != null) {
                        _data.writeInt(1);
                        who.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(length);
                    _data.writeInt(parent);
                    if (this.mRemote.transact(7, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setPasswordMinimumLowerCase(who, length, parent);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getPasswordMinimumLowerCase(ComponentName who, int userHandle, boolean parent) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (who != null) {
                        _data.writeInt(1);
                        who.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(userHandle);
                    _data.writeInt(parent);
                    if (!this.mRemote.transact(8, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPasswordMinimumLowerCase(who, userHandle, parent);
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

            public void setPasswordMinimumLetters(ComponentName who, int length, boolean parent) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (who != null) {
                        _data.writeInt(1);
                        who.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(length);
                    _data.writeInt(parent);
                    if (this.mRemote.transact(9, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setPasswordMinimumLetters(who, length, parent);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getPasswordMinimumLetters(ComponentName who, int userHandle, boolean parent) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (who != null) {
                        _data.writeInt(1);
                        who.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(userHandle);
                    _data.writeInt(parent);
                    if (!this.mRemote.transact(10, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPasswordMinimumLetters(who, userHandle, parent);
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

            public void setPasswordMinimumNumeric(ComponentName who, int length, boolean parent) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (who != null) {
                        _data.writeInt(1);
                        who.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(length);
                    _data.writeInt(parent);
                    if (this.mRemote.transact(11, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setPasswordMinimumNumeric(who, length, parent);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getPasswordMinimumNumeric(ComponentName who, int userHandle, boolean parent) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (who != null) {
                        _data.writeInt(1);
                        who.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(userHandle);
                    _data.writeInt(parent);
                    if (!this.mRemote.transact(12, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPasswordMinimumNumeric(who, userHandle, parent);
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

            public void setPasswordMinimumSymbols(ComponentName who, int length, boolean parent) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (who != null) {
                        _data.writeInt(1);
                        who.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(length);
                    _data.writeInt(parent);
                    if (this.mRemote.transact(13, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setPasswordMinimumSymbols(who, length, parent);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getPasswordMinimumSymbols(ComponentName who, int userHandle, boolean parent) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (who != null) {
                        _data.writeInt(1);
                        who.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(userHandle);
                    _data.writeInt(parent);
                    if (!this.mRemote.transact(14, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPasswordMinimumSymbols(who, userHandle, parent);
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

            public void setPasswordMinimumNonLetter(ComponentName who, int length, boolean parent) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (who != null) {
                        _data.writeInt(1);
                        who.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(length);
                    _data.writeInt(parent);
                    if (this.mRemote.transact(15, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setPasswordMinimumNonLetter(who, length, parent);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getPasswordMinimumNonLetter(ComponentName who, int userHandle, boolean parent) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (who != null) {
                        _data.writeInt(1);
                        who.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(userHandle);
                    _data.writeInt(parent);
                    if (!this.mRemote.transact(16, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPasswordMinimumNonLetter(who, userHandle, parent);
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

            public void setPasswordHistoryLength(ComponentName who, int length, boolean parent) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (who != null) {
                        _data.writeInt(1);
                        who.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(length);
                    _data.writeInt(parent);
                    if (this.mRemote.transact(17, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setPasswordHistoryLength(who, length, parent);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getPasswordHistoryLength(ComponentName who, int userHandle, boolean parent) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (who != null) {
                        _data.writeInt(1);
                        who.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(userHandle);
                    _data.writeInt(parent);
                    if (!this.mRemote.transact(18, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPasswordHistoryLength(who, userHandle, parent);
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

            public void setPasswordExpirationTimeout(ComponentName who, long expiration, boolean parent) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (who != null) {
                        _data.writeInt(1);
                        who.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeLong(expiration);
                    _data.writeInt(parent);
                    if (this.mRemote.transact(19, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setPasswordExpirationTimeout(who, expiration, parent);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public long getPasswordExpirationTimeout(ComponentName who, int userHandle, boolean parent) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (who != null) {
                        _data.writeInt(1);
                        who.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(userHandle);
                    _data.writeInt(parent);
                    if (!this.mRemote.transact(20, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPasswordExpirationTimeout(who, userHandle, parent);
                    }
                    _reply.readException();
                    long _result = _reply.readLong();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public long getPasswordExpiration(ComponentName who, int userHandle, boolean parent) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (who != null) {
                        _data.writeInt(1);
                        who.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(userHandle);
                    _data.writeInt(parent);
                    if (!this.mRemote.transact(21, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPasswordExpiration(who, userHandle, parent);
                    }
                    _reply.readException();
                    long _result = _reply.readLong();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isActivePasswordSufficient(int userHandle, boolean parent) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userHandle);
                    _data.writeInt(parent);
                    boolean z = false;
                    if (!this.mRemote.transact(22, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isActivePasswordSufficient(userHandle, parent);
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

            public boolean isProfileActivePasswordSufficientForParent(int userHandle) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userHandle);
                    boolean z = false;
                    if (!this.mRemote.transact(23, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isProfileActivePasswordSufficientForParent(userHandle);
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

            public int getPasswordComplexity() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(24, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPasswordComplexity();
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

            public boolean isUsingUnifiedPassword(ComponentName admin) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (admin != null) {
                        _data.writeInt(1);
                        admin.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(25, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isUsingUnifiedPassword(admin);
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

            public int getCurrentFailedPasswordAttempts(int userHandle, boolean parent) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userHandle);
                    _data.writeInt(parent);
                    if (!this.mRemote.transact(26, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getCurrentFailedPasswordAttempts(userHandle, parent);
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

            public int getProfileWithMinimumFailedPasswordsForWipe(int userHandle, boolean parent) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userHandle);
                    _data.writeInt(parent);
                    if (!this.mRemote.transact(27, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getProfileWithMinimumFailedPasswordsForWipe(userHandle, parent);
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

            public void setMaximumFailedPasswordsForWipe(ComponentName admin, int num, boolean parent) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (admin != null) {
                        _data.writeInt(1);
                        admin.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(num);
                    _data.writeInt(parent);
                    if (this.mRemote.transact(28, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setMaximumFailedPasswordsForWipe(admin, num, parent);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getMaximumFailedPasswordsForWipe(ComponentName admin, int userHandle, boolean parent) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (admin != null) {
                        _data.writeInt(1);
                        admin.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(userHandle);
                    _data.writeInt(parent);
                    if (!this.mRemote.transact(29, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getMaximumFailedPasswordsForWipe(admin, userHandle, parent);
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

            public boolean resetPassword(String password, int flags) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(password);
                    _data.writeInt(flags);
                    boolean z = false;
                    if (!this.mRemote.transact(30, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().resetPassword(password, flags);
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

            public void setMaximumTimeToLock(ComponentName who, long timeMs, boolean parent) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (who != null) {
                        _data.writeInt(1);
                        who.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeLong(timeMs);
                    _data.writeInt(parent);
                    if (this.mRemote.transact(31, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setMaximumTimeToLock(who, timeMs, parent);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public long getMaximumTimeToLock(ComponentName who, int userHandle, boolean parent) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (who != null) {
                        _data.writeInt(1);
                        who.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(userHandle);
                    _data.writeInt(parent);
                    if (!this.mRemote.transact(32, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getMaximumTimeToLock(who, userHandle, parent);
                    }
                    _reply.readException();
                    long _result = _reply.readLong();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setRequiredStrongAuthTimeout(ComponentName who, long timeMs, boolean parent) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (who != null) {
                        _data.writeInt(1);
                        who.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeLong(timeMs);
                    _data.writeInt(parent);
                    if (this.mRemote.transact(33, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setRequiredStrongAuthTimeout(who, timeMs, parent);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public long getRequiredStrongAuthTimeout(ComponentName who, int userId, boolean parent) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (who != null) {
                        _data.writeInt(1);
                        who.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(userId);
                    _data.writeInt(parent);
                    if (!this.mRemote.transact(34, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getRequiredStrongAuthTimeout(who, userId, parent);
                    }
                    _reply.readException();
                    long _result = _reply.readLong();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void lockNow(int flags, boolean parent) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(flags);
                    _data.writeInt(parent);
                    if (this.mRemote.transact(35, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().lockNow(flags, parent);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void wipeDataWithReason(int flags, String wipeReasonForUser) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(flags);
                    _data.writeString(wipeReasonForUser);
                    if (this.mRemote.transact(36, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().wipeDataWithReason(flags, wipeReasonForUser);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public ComponentName setGlobalProxy(ComponentName admin, String proxySpec, String exclusionList) throws RemoteException {
                ComponentName _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (admin != null) {
                        _data.writeInt(1);
                        admin.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(proxySpec);
                    _data.writeString(exclusionList);
                    if (!this.mRemote.transact(37, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setGlobalProxy(admin, proxySpec, exclusionList);
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

            public ComponentName getGlobalProxyAdmin(int userHandle) throws RemoteException {
                ComponentName _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userHandle);
                    if (!this.mRemote.transact(38, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getGlobalProxyAdmin(userHandle);
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

            public void setRecommendedGlobalProxy(ComponentName admin, ProxyInfo proxyInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (admin != null) {
                        _data.writeInt(1);
                        admin.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (proxyInfo != null) {
                        _data.writeInt(1);
                        proxyInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(39, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setRecommendedGlobalProxy(admin, proxyInfo);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int setStorageEncryption(ComponentName who, boolean encrypt) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (who != null) {
                        _data.writeInt(1);
                        who.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(encrypt);
                    if (!this.mRemote.transact(40, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setStorageEncryption(who, encrypt);
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

            public boolean getStorageEncryption(ComponentName who, int userHandle) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (who != null) {
                        _data.writeInt(1);
                        who.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(userHandle);
                    if (!this.mRemote.transact(41, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getStorageEncryption(who, userHandle);
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

            public int getStorageEncryptionStatus(String callerPackage, int userHandle) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callerPackage);
                    _data.writeInt(userHandle);
                    if (!this.mRemote.transact(42, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getStorageEncryptionStatus(callerPackage, userHandle);
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

            public boolean requestBugreport(ComponentName who) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (who != null) {
                        _data.writeInt(1);
                        who.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(43, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().requestBugreport(who);
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

            public void setCameraDisabled(ComponentName who, boolean disabled) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (who != null) {
                        _data.writeInt(1);
                        who.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(disabled);
                    if (this.mRemote.transact(44, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setCameraDisabled(who, disabled);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean getCameraDisabled(ComponentName who, int userHandle) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (who != null) {
                        _data.writeInt(1);
                        who.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(userHandle);
                    if (!this.mRemote.transact(45, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getCameraDisabled(who, userHandle);
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

            public void setScreenCaptureDisabled(ComponentName who, boolean disabled) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (who != null) {
                        _data.writeInt(1);
                        who.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(disabled);
                    if (this.mRemote.transact(46, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setScreenCaptureDisabled(who, disabled);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean getScreenCaptureDisabled(ComponentName who, int userHandle) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (who != null) {
                        _data.writeInt(1);
                        who.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(userHandle);
                    if (!this.mRemote.transact(47, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getScreenCaptureDisabled(who, userHandle);
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

            public void setKeyguardDisabledFeatures(ComponentName who, int which, boolean parent) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (who != null) {
                        _data.writeInt(1);
                        who.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(which);
                    _data.writeInt(parent);
                    if (this.mRemote.transact(48, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setKeyguardDisabledFeatures(who, which, parent);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getKeyguardDisabledFeatures(ComponentName who, int userHandle, boolean parent) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (who != null) {
                        _data.writeInt(1);
                        who.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(userHandle);
                    _data.writeInt(parent);
                    if (!this.mRemote.transact(49, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getKeyguardDisabledFeatures(who, userHandle, parent);
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

            public void setActiveAdmin(ComponentName policyReceiver, boolean refreshing, int userHandle) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (policyReceiver != null) {
                        _data.writeInt(1);
                        policyReceiver.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(refreshing);
                    _data.writeInt(userHandle);
                    if (this.mRemote.transact(50, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setActiveAdmin(policyReceiver, refreshing, userHandle);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isAdminActive(ComponentName policyReceiver, int userHandle) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (policyReceiver != null) {
                        _data.writeInt(1);
                        policyReceiver.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(userHandle);
                    if (!this.mRemote.transact(51, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isAdminActive(policyReceiver, userHandle);
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

            public List<ComponentName> getActiveAdmins(int userHandle) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userHandle);
                    if (!this.mRemote.transact(52, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getActiveAdmins(userHandle);
                    }
                    _reply.readException();
                    List<ComponentName> _result = _reply.createTypedArrayList(ComponentName.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean packageHasActiveAdmins(String packageName, int userHandle) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userHandle);
                    boolean z = false;
                    if (!this.mRemote.transact(53, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().packageHasActiveAdmins(packageName, userHandle);
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

            public void getRemoveWarning(ComponentName policyReceiver, RemoteCallback result, int userHandle) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (policyReceiver != null) {
                        _data.writeInt(1);
                        policyReceiver.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (result != null) {
                        _data.writeInt(1);
                        result.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(userHandle);
                    if (this.mRemote.transact(54, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().getRemoveWarning(policyReceiver, result, userHandle);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void removeActiveAdmin(ComponentName policyReceiver, int userHandle) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (policyReceiver != null) {
                        _data.writeInt(1);
                        policyReceiver.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(userHandle);
                    if (this.mRemote.transact(55, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().removeActiveAdmin(policyReceiver, userHandle);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void forceRemoveActiveAdmin(ComponentName policyReceiver, int userHandle) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (policyReceiver != null) {
                        _data.writeInt(1);
                        policyReceiver.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(userHandle);
                    if (this.mRemote.transact(56, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().forceRemoveActiveAdmin(policyReceiver, userHandle);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean hasGrantedPolicy(ComponentName policyReceiver, int usesPolicy, int userHandle) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (policyReceiver != null) {
                        _data.writeInt(1);
                        policyReceiver.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(usesPolicy);
                    _data.writeInt(userHandle);
                    if (!this.mRemote.transact(57, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().hasGrantedPolicy(policyReceiver, usesPolicy, userHandle);
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

            public void setActivePasswordState(PasswordMetrics metrics, int userHandle) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (metrics != null) {
                        _data.writeInt(1);
                        metrics.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(userHandle);
                    if (this.mRemote.transact(58, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setActivePasswordState(metrics, userHandle);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void reportPasswordChanged(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(59, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().reportPasswordChanged(userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void reportFailedPasswordAttempt(int userHandle) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userHandle);
                    if (this.mRemote.transact(60, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().reportFailedPasswordAttempt(userHandle);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void reportSuccessfulPasswordAttempt(int userHandle) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userHandle);
                    if (this.mRemote.transact(61, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().reportSuccessfulPasswordAttempt(userHandle);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void reportFailedBiometricAttempt(int userHandle) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userHandle);
                    if (this.mRemote.transact(62, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().reportFailedBiometricAttempt(userHandle);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void reportSuccessfulBiometricAttempt(int userHandle) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userHandle);
                    if (this.mRemote.transact(63, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().reportSuccessfulBiometricAttempt(userHandle);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void reportKeyguardDismissed(int userHandle) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userHandle);
                    if (this.mRemote.transact(64, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().reportKeyguardDismissed(userHandle);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void reportKeyguardSecured(int userHandle) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userHandle);
                    if (this.mRemote.transact(65, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().reportKeyguardSecured(userHandle);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean setDeviceOwner(ComponentName who, String ownerName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (who != null) {
                        _data.writeInt(1);
                        who.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(ownerName);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(66, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setDeviceOwner(who, ownerName, userId);
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

            public ComponentName getDeviceOwnerComponent(boolean callingUserOnly) throws RemoteException {
                ComponentName _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(callingUserOnly);
                    if (!this.mRemote.transact(67, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getDeviceOwnerComponent(callingUserOnly);
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

            public boolean hasDeviceOwner() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(68, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().hasDeviceOwner();
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

            public String getDeviceOwnerName() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(69, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getDeviceOwnerName();
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

            public void clearDeviceOwner(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    if (this.mRemote.transact(70, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().clearDeviceOwner(packageName);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getDeviceOwnerUserId() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(71, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getDeviceOwnerUserId();
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

            public boolean setProfileOwner(ComponentName who, String ownerName, int userHandle) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (who != null) {
                        _data.writeInt(1);
                        who.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(ownerName);
                    _data.writeInt(userHandle);
                    if (!this.mRemote.transact(72, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setProfileOwner(who, ownerName, userHandle);
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

            public ComponentName getProfileOwnerAsUser(int userHandle) throws RemoteException {
                ComponentName _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userHandle);
                    if (!this.mRemote.transact(73, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getProfileOwnerAsUser(userHandle);
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

            public ComponentName getProfileOwner(int userHandle) throws RemoteException {
                ComponentName _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userHandle);
                    if (!this.mRemote.transact(74, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getProfileOwner(userHandle);
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

            public String getProfileOwnerName(int userHandle) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userHandle);
                    if (!this.mRemote.transact(75, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getProfileOwnerName(userHandle);
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

            public void setProfileEnabled(ComponentName who) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (who != null) {
                        _data.writeInt(1);
                        who.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(76, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setProfileEnabled(who);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setProfileName(ComponentName who, String profileName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (who != null) {
                        _data.writeInt(1);
                        who.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(profileName);
                    if (this.mRemote.transact(77, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setProfileName(who, profileName);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void clearProfileOwner(ComponentName who) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (who != null) {
                        _data.writeInt(1);
                        who.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(78, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().clearProfileOwner(who);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean hasUserSetupCompleted() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(79, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().hasUserSetupCompleted();
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

            public boolean checkDeviceIdentifierAccess(String packageName, int pid, int uid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(pid);
                    _data.writeInt(uid);
                    boolean z = false;
                    if (!this.mRemote.transact(80, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().checkDeviceIdentifierAccess(packageName, pid, uid);
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

            public void setDeviceOwnerLockScreenInfo(ComponentName who, CharSequence deviceOwnerInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (who != null) {
                        _data.writeInt(1);
                        who.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (deviceOwnerInfo != null) {
                        _data.writeInt(1);
                        TextUtils.writeToParcel(deviceOwnerInfo, _data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(81, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setDeviceOwnerLockScreenInfo(who, deviceOwnerInfo);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public CharSequence getDeviceOwnerLockScreenInfo() throws RemoteException {
                CharSequence _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(82, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getDeviceOwnerLockScreenInfo();
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

            public String[] setPackagesSuspended(ComponentName admin, String callerPackage, String[] packageNames, boolean suspended) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (admin != null) {
                        _data.writeInt(1);
                        admin.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(callerPackage);
                    _data.writeStringArray(packageNames);
                    _data.writeInt(suspended);
                    if (!this.mRemote.transact(83, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setPackagesSuspended(admin, callerPackage, packageNames, suspended);
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

            public boolean isPackageSuspended(ComponentName admin, String callerPackage, String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (admin != null) {
                        _data.writeInt(1);
                        admin.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(callerPackage);
                    _data.writeString(packageName);
                    if (!this.mRemote.transact(84, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isPackageSuspended(admin, callerPackage, packageName);
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

            public boolean installCaCert(ComponentName admin, String callerPackage, byte[] certBuffer) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (admin != null) {
                        _data.writeInt(1);
                        admin.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(callerPackage);
                    _data.writeByteArray(certBuffer);
                    if (!this.mRemote.transact(85, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().installCaCert(admin, callerPackage, certBuffer);
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

            public void uninstallCaCerts(ComponentName admin, String callerPackage, String[] aliases) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (admin != null) {
                        _data.writeInt(1);
                        admin.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(callerPackage);
                    _data.writeStringArray(aliases);
                    if (this.mRemote.transact(86, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().uninstallCaCerts(admin, callerPackage, aliases);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void enforceCanManageCaCerts(ComponentName admin, String callerPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (admin != null) {
                        _data.writeInt(1);
                        admin.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(callerPackage);
                    if (this.mRemote.transact(87, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().enforceCanManageCaCerts(admin, callerPackage);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean approveCaCert(String alias, int userHandle, boolean approval) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(alias);
                    _data.writeInt(userHandle);
                    _data.writeInt(approval);
                    boolean z = false;
                    if (!this.mRemote.transact(88, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().approveCaCert(alias, userHandle, approval);
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

            public boolean isCaCertApproved(String alias, int userHandle) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(alias);
                    _data.writeInt(userHandle);
                    boolean z = false;
                    if (!this.mRemote.transact(89, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isCaCertApproved(alias, userHandle);
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

            public boolean installKeyPair(ComponentName who, String callerPackage, byte[] privKeyBuffer, byte[] certBuffer, byte[] certChainBuffer, String alias, boolean requestAccess, boolean isUserSelectable) throws RemoteException {
                ComponentName componentName = who;
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
                        _data.writeString(callerPackage);
                        try {
                            _data.writeByteArray(privKeyBuffer);
                        } catch (Throwable th) {
                            th = th;
                            byte[] bArr = certBuffer;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                        try {
                            _data.writeByteArray(certBuffer);
                            _data.writeByteArray(certChainBuffer);
                            _data.writeString(alias);
                            _data.writeInt(requestAccess ? 1 : 0);
                            _data.writeInt(isUserSelectable ? 1 : 0);
                            if (this.mRemote.transact(90, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                _reply.readException();
                                if (_reply.readInt() == 0) {
                                    _result = false;
                                }
                                _reply.recycle();
                                _data.recycle();
                                return _result;
                            }
                            boolean installKeyPair = Stub.getDefaultImpl().installKeyPair(who, callerPackage, privKeyBuffer, certBuffer, certChainBuffer, alias, requestAccess, isUserSelectable);
                            _reply.recycle();
                            _data.recycle();
                            return installKeyPair;
                        } catch (Throwable th2) {
                            th = th2;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        byte[] bArr2 = privKeyBuffer;
                        byte[] bArr3 = certBuffer;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th4) {
                    th = th4;
                    String str = callerPackage;
                    byte[] bArr22 = privKeyBuffer;
                    byte[] bArr32 = certBuffer;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public boolean removeKeyPair(ComponentName who, String callerPackage, String alias) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (who != null) {
                        _data.writeInt(1);
                        who.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(callerPackage);
                    _data.writeString(alias);
                    if (!this.mRemote.transact(91, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().removeKeyPair(who, callerPackage, alias);
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

            public boolean generateKeyPair(ComponentName who, String callerPackage, String algorithm, ParcelableKeyGenParameterSpec keySpec, int idAttestationFlags, KeymasterCertificateChain attestationChain) throws RemoteException {
                ComponentName componentName = who;
                ParcelableKeyGenParameterSpec parcelableKeyGenParameterSpec = keySpec;
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
                        _data.writeString(callerPackage);
                        try {
                            _data.writeString(algorithm);
                            if (parcelableKeyGenParameterSpec != null) {
                                _data.writeInt(1);
                                parcelableKeyGenParameterSpec.writeToParcel(_data, 0);
                            } else {
                                _data.writeInt(0);
                            }
                        } catch (Throwable th) {
                            th = th;
                            int i = idAttestationFlags;
                            KeymasterCertificateChain keymasterCertificateChain = attestationChain;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        String str = algorithm;
                        int i2 = idAttestationFlags;
                        KeymasterCertificateChain keymasterCertificateChain2 = attestationChain;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(idAttestationFlags);
                        try {
                            if (this.mRemote.transact(92, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                _reply.readException();
                                if (_reply.readInt() == 0) {
                                    _result = false;
                                }
                                if (_reply.readInt() != 0) {
                                    try {
                                        attestationChain.readFromParcel(_reply);
                                    } catch (Throwable th3) {
                                        th = th3;
                                    }
                                } else {
                                    KeymasterCertificateChain keymasterCertificateChain3 = attestationChain;
                                }
                                _reply.recycle();
                                _data.recycle();
                                return _result;
                            }
                            boolean generateKeyPair = Stub.getDefaultImpl().generateKeyPair(who, callerPackage, algorithm, keySpec, idAttestationFlags, attestationChain);
                            _reply.recycle();
                            _data.recycle();
                            return generateKeyPair;
                        } catch (Throwable th4) {
                            th = th4;
                            KeymasterCertificateChain keymasterCertificateChain22 = attestationChain;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th5) {
                        th = th5;
                        KeymasterCertificateChain keymasterCertificateChain222 = attestationChain;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th6) {
                    th = th6;
                    String str2 = callerPackage;
                    String str3 = algorithm;
                    int i22 = idAttestationFlags;
                    KeymasterCertificateChain keymasterCertificateChain2222 = attestationChain;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public boolean setKeyPairCertificate(ComponentName who, String callerPackage, String alias, byte[] certBuffer, byte[] certChainBuffer, boolean isUserSelectable) throws RemoteException {
                ComponentName componentName = who;
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
                        _data.writeString(callerPackage);
                        try {
                            _data.writeString(alias);
                        } catch (Throwable th) {
                            th = th;
                            byte[] bArr = certBuffer;
                            byte[] bArr2 = certChainBuffer;
                            boolean z = isUserSelectable;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                        try {
                            _data.writeByteArray(certBuffer);
                            try {
                                _data.writeByteArray(certChainBuffer);
                            } catch (Throwable th2) {
                                th = th2;
                                boolean z2 = isUserSelectable;
                                _reply.recycle();
                                _data.recycle();
                                throw th;
                            }
                            try {
                                _data.writeInt(isUserSelectable ? 1 : 0);
                                if (this.mRemote.transact(93, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                    _reply.readException();
                                    if (_reply.readInt() == 0) {
                                        _result = false;
                                    }
                                    _reply.recycle();
                                    _data.recycle();
                                    return _result;
                                }
                                boolean keyPairCertificate = Stub.getDefaultImpl().setKeyPairCertificate(who, callerPackage, alias, certBuffer, certChainBuffer, isUserSelectable);
                                _reply.recycle();
                                _data.recycle();
                                return keyPairCertificate;
                            } catch (Throwable th3) {
                                th = th3;
                                _reply.recycle();
                                _data.recycle();
                                throw th;
                            }
                        } catch (Throwable th4) {
                            th = th4;
                            byte[] bArr22 = certChainBuffer;
                            boolean z22 = isUserSelectable;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th5) {
                        th = th5;
                        String str = alias;
                        byte[] bArr3 = certBuffer;
                        byte[] bArr222 = certChainBuffer;
                        boolean z222 = isUserSelectable;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th6) {
                    th = th6;
                    String str2 = callerPackage;
                    String str3 = alias;
                    byte[] bArr32 = certBuffer;
                    byte[] bArr2222 = certChainBuffer;
                    boolean z2222 = isUserSelectable;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public void choosePrivateKeyAlias(int uid, Uri uri, String alias, IBinder aliasCallback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    if (uri != null) {
                        _data.writeInt(1);
                        uri.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(alias);
                    _data.writeStrongBinder(aliasCallback);
                    if (this.mRemote.transact(94, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().choosePrivateKeyAlias(uid, uri, alias, aliasCallback);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setDelegatedScopes(ComponentName who, String delegatePackage, List<String> scopes) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (who != null) {
                        _data.writeInt(1);
                        who.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(delegatePackage);
                    _data.writeStringList(scopes);
                    if (this.mRemote.transact(95, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setDelegatedScopes(who, delegatePackage, scopes);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public List<String> getDelegatedScopes(ComponentName who, String delegatePackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (who != null) {
                        _data.writeInt(1);
                        who.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(delegatePackage);
                    if (!this.mRemote.transact(96, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getDelegatedScopes(who, delegatePackage);
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

            public List<String> getDelegatePackages(ComponentName who, String scope) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (who != null) {
                        _data.writeInt(1);
                        who.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(scope);
                    if (!this.mRemote.transact(97, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getDelegatePackages(who, scope);
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

            public void setCertInstallerPackage(ComponentName who, String installerPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (who != null) {
                        _data.writeInt(1);
                        who.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(installerPackage);
                    if (this.mRemote.transact(98, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setCertInstallerPackage(who, installerPackage);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String getCertInstallerPackage(ComponentName who) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (who != null) {
                        _data.writeInt(1);
                        who.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(99, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getCertInstallerPackage(who);
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

            public boolean setAlwaysOnVpnPackage(ComponentName who, String vpnPackage, boolean lockdown, List<String> lockdownWhitelist) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (who != null) {
                        _data.writeInt(1);
                        who.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(vpnPackage);
                    _data.writeInt(lockdown);
                    _data.writeStringList(lockdownWhitelist);
                    if (!this.mRemote.transact(100, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setAlwaysOnVpnPackage(who, vpnPackage, lockdown, lockdownWhitelist);
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

            public String getAlwaysOnVpnPackage(ComponentName who) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (who != null) {
                        _data.writeInt(1);
                        who.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(101, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAlwaysOnVpnPackage(who);
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

            public boolean isAlwaysOnVpnLockdownEnabled(ComponentName who) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (who != null) {
                        _data.writeInt(1);
                        who.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(102, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isAlwaysOnVpnLockdownEnabled(who);
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

            public List<String> getAlwaysOnVpnLockdownWhitelist(ComponentName who) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (who != null) {
                        _data.writeInt(1);
                        who.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(103, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAlwaysOnVpnLockdownWhitelist(who);
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

            public void addPersistentPreferredActivity(ComponentName admin, IntentFilter filter, ComponentName activity) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (admin != null) {
                        _data.writeInt(1);
                        admin.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
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
                    if (this.mRemote.transact(104, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().addPersistentPreferredActivity(admin, filter, activity);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void clearPackagePersistentPreferredActivities(ComponentName admin, String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (admin != null) {
                        _data.writeInt(1);
                        admin.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(packageName);
                    if (this.mRemote.transact(105, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().clearPackagePersistentPreferredActivities(admin, packageName);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setDefaultSmsApplication(ComponentName admin, String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (admin != null) {
                        _data.writeInt(1);
                        admin.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(packageName);
                    if (this.mRemote.transact(106, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setDefaultSmsApplication(admin, packageName);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setApplicationRestrictions(ComponentName who, String callerPackage, String packageName, Bundle settings) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (who != null) {
                        _data.writeInt(1);
                        who.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(callerPackage);
                    _data.writeString(packageName);
                    if (settings != null) {
                        _data.writeInt(1);
                        settings.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(107, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setApplicationRestrictions(who, callerPackage, packageName, settings);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public Bundle getApplicationRestrictions(ComponentName who, String callerPackage, String packageName) throws RemoteException {
                Bundle _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (who != null) {
                        _data.writeInt(1);
                        who.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(callerPackage);
                    _data.writeString(packageName);
                    if (!this.mRemote.transact(108, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getApplicationRestrictions(who, callerPackage, packageName);
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

            public boolean setApplicationRestrictionsManagingPackage(ComponentName admin, String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (admin != null) {
                        _data.writeInt(1);
                        admin.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(packageName);
                    if (!this.mRemote.transact(109, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setApplicationRestrictionsManagingPackage(admin, packageName);
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

            public String getApplicationRestrictionsManagingPackage(ComponentName admin) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (admin != null) {
                        _data.writeInt(1);
                        admin.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(110, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getApplicationRestrictionsManagingPackage(admin);
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

            public boolean isCallerApplicationRestrictionsManagingPackage(String callerPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callerPackage);
                    boolean z = false;
                    if (!this.mRemote.transact(111, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isCallerApplicationRestrictionsManagingPackage(callerPackage);
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

            public void setRestrictionsProvider(ComponentName who, ComponentName provider) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (who != null) {
                        _data.writeInt(1);
                        who.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (provider != null) {
                        _data.writeInt(1);
                        provider.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(112, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setRestrictionsProvider(who, provider);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public ComponentName getRestrictionsProvider(int userHandle) throws RemoteException {
                ComponentName _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userHandle);
                    if (!this.mRemote.transact(113, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getRestrictionsProvider(userHandle);
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

            public void setUserRestriction(ComponentName who, String key, boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (who != null) {
                        _data.writeInt(1);
                        who.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(key);
                    _data.writeInt(enable);
                    if (this.mRemote.transact(114, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setUserRestriction(who, key, enable);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public Bundle getUserRestrictions(ComponentName who) throws RemoteException {
                Bundle _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (who != null) {
                        _data.writeInt(1);
                        who.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(115, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getUserRestrictions(who);
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

            public void addCrossProfileIntentFilter(ComponentName admin, IntentFilter filter, int flags) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (admin != null) {
                        _data.writeInt(1);
                        admin.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (filter != null) {
                        _data.writeInt(1);
                        filter.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(flags);
                    if (this.mRemote.transact(116, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().addCrossProfileIntentFilter(admin, filter, flags);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void clearCrossProfileIntentFilters(ComponentName admin) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (admin != null) {
                        _data.writeInt(1);
                        admin.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(117, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().clearCrossProfileIntentFilters(admin);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean setPermittedAccessibilityServices(ComponentName admin, List packageList) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (admin != null) {
                        _data.writeInt(1);
                        admin.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeList(packageList);
                    if (!this.mRemote.transact(118, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setPermittedAccessibilityServices(admin, packageList);
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

            public List getPermittedAccessibilityServices(ComponentName admin) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (admin != null) {
                        _data.writeInt(1);
                        admin.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(119, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPermittedAccessibilityServices(admin);
                    }
                    _reply.readException();
                    List _result = _reply.readArrayList(getClass().getClassLoader());
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public List getPermittedAccessibilityServicesForUser(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(120, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPermittedAccessibilityServicesForUser(userId);
                    }
                    _reply.readException();
                    List _result = _reply.readArrayList(getClass().getClassLoader());
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isAccessibilityServicePermittedByAdmin(ComponentName admin, String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (admin != null) {
                        _data.writeInt(1);
                        admin.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(121, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isAccessibilityServicePermittedByAdmin(admin, packageName, userId);
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

            public boolean setPermittedInputMethods(ComponentName admin, List packageList) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (admin != null) {
                        _data.writeInt(1);
                        admin.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeList(packageList);
                    if (!this.mRemote.transact(122, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setPermittedInputMethods(admin, packageList);
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

            public List getPermittedInputMethods(ComponentName admin) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (admin != null) {
                        _data.writeInt(1);
                        admin.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(123, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPermittedInputMethods(admin);
                    }
                    _reply.readException();
                    List _result = _reply.readArrayList(getClass().getClassLoader());
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public List getPermittedInputMethodsForCurrentUser() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(124, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPermittedInputMethodsForCurrentUser();
                    }
                    _reply.readException();
                    List _result = _reply.readArrayList(getClass().getClassLoader());
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isInputMethodPermittedByAdmin(ComponentName admin, String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (admin != null) {
                        _data.writeInt(1);
                        admin.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(125, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isInputMethodPermittedByAdmin(admin, packageName, userId);
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

            public boolean setPermittedCrossProfileNotificationListeners(ComponentName admin, List<String> packageList) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (admin != null) {
                        _data.writeInt(1);
                        admin.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeStringList(packageList);
                    if (!this.mRemote.transact(126, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setPermittedCrossProfileNotificationListeners(admin, packageList);
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

            public List<String> getPermittedCrossProfileNotificationListeners(ComponentName admin) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (admin != null) {
                        _data.writeInt(1);
                        admin.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(127, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPermittedCrossProfileNotificationListeners(admin);
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

            public boolean isNotificationListenerServicePermitted(String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    boolean z = false;
                    if (!this.mRemote.transact(128, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isNotificationListenerServicePermitted(packageName, userId);
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

            public Intent createAdminSupportIntent(String restriction) throws RemoteException {
                Intent _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(restriction);
                    if (!this.mRemote.transact(129, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().createAdminSupportIntent(restriction);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = Intent.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    Intent _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean setApplicationHidden(ComponentName admin, String callerPackage, String packageName, boolean hidden) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (admin != null) {
                        _data.writeInt(1);
                        admin.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(callerPackage);
                    _data.writeString(packageName);
                    _data.writeInt(hidden);
                    if (!this.mRemote.transact(130, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setApplicationHidden(admin, callerPackage, packageName, hidden);
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

            public boolean isApplicationHidden(ComponentName admin, String callerPackage, String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (admin != null) {
                        _data.writeInt(1);
                        admin.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(callerPackage);
                    _data.writeString(packageName);
                    if (!this.mRemote.transact(131, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isApplicationHidden(admin, callerPackage, packageName);
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

            public UserHandle createAndManageUser(ComponentName who, String name, ComponentName profileOwner, PersistableBundle adminExtras, int flags) throws RemoteException {
                UserHandle _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (who != null) {
                        _data.writeInt(1);
                        who.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(name);
                    if (profileOwner != null) {
                        _data.writeInt(1);
                        profileOwner.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (adminExtras != null) {
                        _data.writeInt(1);
                        adminExtras.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(flags);
                    if (!this.mRemote.transact(132, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().createAndManageUser(who, name, profileOwner, adminExtras, flags);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = UserHandle.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    UserHandle _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean removeUser(ComponentName who, UserHandle userHandle) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (who != null) {
                        _data.writeInt(1);
                        who.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (userHandle != null) {
                        _data.writeInt(1);
                        userHandle.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(133, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().removeUser(who, userHandle);
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

            public boolean switchUser(ComponentName who, UserHandle userHandle) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (who != null) {
                        _data.writeInt(1);
                        who.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (userHandle != null) {
                        _data.writeInt(1);
                        userHandle.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(134, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().switchUser(who, userHandle);
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

            public int startUserInBackground(ComponentName who, UserHandle userHandle) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (who != null) {
                        _data.writeInt(1);
                        who.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (userHandle != null) {
                        _data.writeInt(1);
                        userHandle.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(135, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().startUserInBackground(who, userHandle);
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

            public int stopUser(ComponentName who, UserHandle userHandle) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (who != null) {
                        _data.writeInt(1);
                        who.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (userHandle != null) {
                        _data.writeInt(1);
                        userHandle.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(136, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().stopUser(who, userHandle);
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

            public int logoutUser(ComponentName who) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (who != null) {
                        _data.writeInt(1);
                        who.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(137, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().logoutUser(who);
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

            public List<UserHandle> getSecondaryUsers(ComponentName who) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (who != null) {
                        _data.writeInt(1);
                        who.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(138, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getSecondaryUsers(who);
                    }
                    _reply.readException();
                    List<UserHandle> _result = _reply.createTypedArrayList(UserHandle.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void enableSystemApp(ComponentName admin, String callerPackage, String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (admin != null) {
                        _data.writeInt(1);
                        admin.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(callerPackage);
                    _data.writeString(packageName);
                    if (this.mRemote.transact(139, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().enableSystemApp(admin, callerPackage, packageName);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int enableSystemAppWithIntent(ComponentName admin, String callerPackage, Intent intent) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (admin != null) {
                        _data.writeInt(1);
                        admin.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(callerPackage);
                    if (intent != null) {
                        _data.writeInt(1);
                        intent.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(140, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().enableSystemAppWithIntent(admin, callerPackage, intent);
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

            public boolean installExistingPackage(ComponentName admin, String callerPackage, String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (admin != null) {
                        _data.writeInt(1);
                        admin.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(callerPackage);
                    _data.writeString(packageName);
                    if (!this.mRemote.transact(141, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().installExistingPackage(admin, callerPackage, packageName);
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

            public void setAccountManagementDisabled(ComponentName who, String accountType, boolean disabled) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (who != null) {
                        _data.writeInt(1);
                        who.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(accountType);
                    _data.writeInt(disabled);
                    if (this.mRemote.transact(142, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setAccountManagementDisabled(who, accountType, disabled);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String[] getAccountTypesWithManagementDisabled() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(143, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAccountTypesWithManagementDisabled();
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

            public String[] getAccountTypesWithManagementDisabledAsUser(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(144, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAccountTypesWithManagementDisabledAsUser(userId);
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

            public void setLockTaskPackages(ComponentName who, String[] packages) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (who != null) {
                        _data.writeInt(1);
                        who.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeStringArray(packages);
                    if (this.mRemote.transact(145, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setLockTaskPackages(who, packages);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String[] getLockTaskPackages(ComponentName who) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (who != null) {
                        _data.writeInt(1);
                        who.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(146, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getLockTaskPackages(who);
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

            public boolean isLockTaskPermitted(String pkg) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(pkg);
                    boolean z = false;
                    if (!this.mRemote.transact(147, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isLockTaskPermitted(pkg);
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

            public void setLockTaskFeatures(ComponentName who, int flags) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (who != null) {
                        _data.writeInt(1);
                        who.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(flags);
                    if (this.mRemote.transact(148, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setLockTaskFeatures(who, flags);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getLockTaskFeatures(ComponentName who) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (who != null) {
                        _data.writeInt(1);
                        who.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(149, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getLockTaskFeatures(who);
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

            public void setGlobalSetting(ComponentName who, String setting, String value) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (who != null) {
                        _data.writeInt(1);
                        who.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(setting);
                    _data.writeString(value);
                    if (this.mRemote.transact(150, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setGlobalSetting(who, setting, value);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setSystemSetting(ComponentName who, String setting, String value) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (who != null) {
                        _data.writeInt(1);
                        who.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(setting);
                    _data.writeString(value);
                    if (this.mRemote.transact(151, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setSystemSetting(who, setting, value);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setSecureSetting(ComponentName who, String setting, String value) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (who != null) {
                        _data.writeInt(1);
                        who.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(setting);
                    _data.writeString(value);
                    if (this.mRemote.transact(152, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setSecureSetting(who, setting, value);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean setTime(ComponentName who, long millis) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (who != null) {
                        _data.writeInt(1);
                        who.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeLong(millis);
                    if (!this.mRemote.transact(153, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setTime(who, millis);
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

            public boolean setTimeZone(ComponentName who, String timeZone) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (who != null) {
                        _data.writeInt(1);
                        who.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(timeZone);
                    if (!this.mRemote.transact(154, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setTimeZone(who, timeZone);
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

            public void setMasterVolumeMuted(ComponentName admin, boolean on) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (admin != null) {
                        _data.writeInt(1);
                        admin.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(on);
                    if (this.mRemote.transact(155, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setMasterVolumeMuted(admin, on);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isMasterVolumeMuted(ComponentName admin) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (admin != null) {
                        _data.writeInt(1);
                        admin.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(156, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isMasterVolumeMuted(admin);
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

            public void notifyLockTaskModeChanged(boolean isEnabled, String pkg, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(isEnabled);
                    _data.writeString(pkg);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(157, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().notifyLockTaskModeChanged(isEnabled, pkg, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setUninstallBlocked(ComponentName admin, String callerPackage, String packageName, boolean uninstallBlocked) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (admin != null) {
                        _data.writeInt(1);
                        admin.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(callerPackage);
                    _data.writeString(packageName);
                    _data.writeInt(uninstallBlocked);
                    if (this.mRemote.transact(158, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setUninstallBlocked(admin, callerPackage, packageName, uninstallBlocked);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isUninstallBlocked(ComponentName admin, String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (admin != null) {
                        _data.writeInt(1);
                        admin.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(packageName);
                    if (!this.mRemote.transact(159, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isUninstallBlocked(admin, packageName);
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

            public void setCrossProfileCallerIdDisabled(ComponentName who, boolean disabled) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (who != null) {
                        _data.writeInt(1);
                        who.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(disabled);
                    if (this.mRemote.transact(160, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setCrossProfileCallerIdDisabled(who, disabled);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean getCrossProfileCallerIdDisabled(ComponentName who) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (who != null) {
                        _data.writeInt(1);
                        who.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(161, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getCrossProfileCallerIdDisabled(who);
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

            public boolean getCrossProfileCallerIdDisabledForUser(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    boolean z = false;
                    if (!this.mRemote.transact(162, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getCrossProfileCallerIdDisabledForUser(userId);
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

            public void setCrossProfileContactsSearchDisabled(ComponentName who, boolean disabled) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (who != null) {
                        _data.writeInt(1);
                        who.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(disabled);
                    if (this.mRemote.transact(163, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setCrossProfileContactsSearchDisabled(who, disabled);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean getCrossProfileContactsSearchDisabled(ComponentName who) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (who != null) {
                        _data.writeInt(1);
                        who.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(164, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getCrossProfileContactsSearchDisabled(who);
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

            public boolean getCrossProfileContactsSearchDisabledForUser(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    boolean z = false;
                    if (!this.mRemote.transact(165, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getCrossProfileContactsSearchDisabledForUser(userId);
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

            public void startManagedQuickContact(String lookupKey, long contactId, boolean isContactIdIgnored, long directoryId, Intent originalIntent) throws RemoteException {
                Intent intent = originalIntent;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeString(lookupKey);
                        try {
                            _data.writeLong(contactId);
                        } catch (Throwable th) {
                            th = th;
                            boolean z = isContactIdIgnored;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                        try {
                            _data.writeInt(isContactIdIgnored ? 1 : 0);
                            _data.writeLong(directoryId);
                            if (intent != null) {
                                _data.writeInt(1);
                                intent.writeToParcel(_data, 0);
                            } else {
                                _data.writeInt(0);
                            }
                            if (this.mRemote.transact(166, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                _reply.readException();
                                _reply.recycle();
                                _data.recycle();
                                return;
                            }
                            Stub.getDefaultImpl().startManagedQuickContact(lookupKey, contactId, isContactIdIgnored, directoryId, originalIntent);
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
                        long j = contactId;
                        boolean z2 = isContactIdIgnored;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th4) {
                    th = th4;
                    String str = lookupKey;
                    long j2 = contactId;
                    boolean z22 = isContactIdIgnored;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public void setBluetoothContactSharingDisabled(ComponentName who, boolean disabled) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (who != null) {
                        _data.writeInt(1);
                        who.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(disabled);
                    if (this.mRemote.transact(167, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setBluetoothContactSharingDisabled(who, disabled);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean getBluetoothContactSharingDisabled(ComponentName who) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (who != null) {
                        _data.writeInt(1);
                        who.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(168, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getBluetoothContactSharingDisabled(who);
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

            public boolean getBluetoothContactSharingDisabledForUser(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    boolean z = false;
                    if (!this.mRemote.transact(169, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getBluetoothContactSharingDisabledForUser(userId);
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

            public void setTrustAgentConfiguration(ComponentName admin, ComponentName agent, PersistableBundle args, boolean parent) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (admin != null) {
                        _data.writeInt(1);
                        admin.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (agent != null) {
                        _data.writeInt(1);
                        agent.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (args != null) {
                        _data.writeInt(1);
                        args.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(parent);
                    if (this.mRemote.transact(170, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setTrustAgentConfiguration(admin, agent, args, parent);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public List<PersistableBundle> getTrustAgentConfiguration(ComponentName admin, ComponentName agent, int userId, boolean parent) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (admin != null) {
                        _data.writeInt(1);
                        admin.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (agent != null) {
                        _data.writeInt(1);
                        agent.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(userId);
                    _data.writeInt(parent);
                    if (!this.mRemote.transact(171, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getTrustAgentConfiguration(admin, agent, userId, parent);
                    }
                    _reply.readException();
                    List<PersistableBundle> _result = _reply.createTypedArrayList(PersistableBundle.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean addCrossProfileWidgetProvider(ComponentName admin, String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (admin != null) {
                        _data.writeInt(1);
                        admin.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(packageName);
                    if (!this.mRemote.transact(172, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().addCrossProfileWidgetProvider(admin, packageName);
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

            public boolean removeCrossProfileWidgetProvider(ComponentName admin, String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (admin != null) {
                        _data.writeInt(1);
                        admin.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(packageName);
                    if (!this.mRemote.transact(173, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().removeCrossProfileWidgetProvider(admin, packageName);
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

            public List<String> getCrossProfileWidgetProviders(ComponentName admin) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (admin != null) {
                        _data.writeInt(1);
                        admin.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(174, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getCrossProfileWidgetProviders(admin);
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

            public void setAutoTimeRequired(ComponentName who, boolean required) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (who != null) {
                        _data.writeInt(1);
                        who.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(required);
                    if (this.mRemote.transact(175, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setAutoTimeRequired(who, required);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean getAutoTimeRequired() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(176, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAutoTimeRequired();
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

            public void setForceEphemeralUsers(ComponentName who, boolean forceEpehemeralUsers) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (who != null) {
                        _data.writeInt(1);
                        who.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(forceEpehemeralUsers);
                    if (this.mRemote.transact(177, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setForceEphemeralUsers(who, forceEpehemeralUsers);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean getForceEphemeralUsers(ComponentName who) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (who != null) {
                        _data.writeInt(1);
                        who.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(178, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getForceEphemeralUsers(who);
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

            public boolean isRemovingAdmin(ComponentName adminReceiver, int userHandle) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (adminReceiver != null) {
                        _data.writeInt(1);
                        adminReceiver.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(userHandle);
                    if (!this.mRemote.transact(179, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isRemovingAdmin(adminReceiver, userHandle);
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

            public void setUserIcon(ComponentName admin, Bitmap icon) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (admin != null) {
                        _data.writeInt(1);
                        admin.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (icon != null) {
                        _data.writeInt(1);
                        icon.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(180, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setUserIcon(admin, icon);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setSystemUpdatePolicy(ComponentName who, SystemUpdatePolicy policy) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (who != null) {
                        _data.writeInt(1);
                        who.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (policy != null) {
                        _data.writeInt(1);
                        policy.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(181, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setSystemUpdatePolicy(who, policy);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public SystemUpdatePolicy getSystemUpdatePolicy() throws RemoteException {
                SystemUpdatePolicy _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(182, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getSystemUpdatePolicy();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = SystemUpdatePolicy.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    SystemUpdatePolicy _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void clearSystemUpdatePolicyFreezePeriodRecord() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(183, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().clearSystemUpdatePolicyFreezePeriodRecord();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean setKeyguardDisabled(ComponentName admin, boolean disabled) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (admin != null) {
                        _data.writeInt(1);
                        admin.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(disabled);
                    if (!this.mRemote.transact(184, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setKeyguardDisabled(admin, disabled);
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

            public boolean setStatusBarDisabled(ComponentName who, boolean disabled) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (who != null) {
                        _data.writeInt(1);
                        who.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(disabled);
                    if (!this.mRemote.transact(185, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setStatusBarDisabled(who, disabled);
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

            public boolean getDoNotAskCredentialsOnBoot() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(186, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getDoNotAskCredentialsOnBoot();
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

            public void notifyPendingSystemUpdate(SystemUpdateInfo info) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (info != null) {
                        _data.writeInt(1);
                        info.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(187, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().notifyPendingSystemUpdate(info);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public SystemUpdateInfo getPendingSystemUpdate(ComponentName admin) throws RemoteException {
                SystemUpdateInfo _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (admin != null) {
                        _data.writeInt(1);
                        admin.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(188, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPendingSystemUpdate(admin);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = SystemUpdateInfo.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    SystemUpdateInfo _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setPermissionPolicy(ComponentName admin, String callerPackage, int policy) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (admin != null) {
                        _data.writeInt(1);
                        admin.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(callerPackage);
                    _data.writeInt(policy);
                    if (this.mRemote.transact(189, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setPermissionPolicy(admin, callerPackage, policy);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getPermissionPolicy(ComponentName admin) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (admin != null) {
                        _data.writeInt(1);
                        admin.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(190, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPermissionPolicy(admin);
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

            public void setPermissionGrantState(ComponentName admin, String callerPackage, String packageName, String permission, int grantState, RemoteCallback resultReceiver) throws RemoteException {
                ComponentName componentName = admin;
                RemoteCallback remoteCallback = resultReceiver;
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
                        _data.writeString(callerPackage);
                        try {
                            _data.writeString(packageName);
                        } catch (Throwable th) {
                            th = th;
                            String str = permission;
                            int i = grantState;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                        try {
                            _data.writeString(permission);
                        } catch (Throwable th2) {
                            th = th2;
                            int i2 = grantState;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        String str2 = packageName;
                        String str3 = permission;
                        int i22 = grantState;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(grantState);
                        if (remoteCallback != null) {
                            _data.writeInt(1);
                            remoteCallback.writeToParcel(_data, 0);
                        } else {
                            _data.writeInt(0);
                        }
                        if (this.mRemote.transact(191, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                            _reply.readException();
                            _reply.recycle();
                            _data.recycle();
                            return;
                        }
                        Stub.getDefaultImpl().setPermissionGrantState(admin, callerPackage, packageName, permission, grantState, resultReceiver);
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
                    String str4 = callerPackage;
                    String str22 = packageName;
                    String str32 = permission;
                    int i222 = grantState;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public int getPermissionGrantState(ComponentName admin, String callerPackage, String packageName, String permission) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (admin != null) {
                        _data.writeInt(1);
                        admin.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(callerPackage);
                    _data.writeString(packageName);
                    _data.writeString(permission);
                    if (!this.mRemote.transact(192, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPermissionGrantState(admin, callerPackage, packageName, permission);
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

            public boolean isProvisioningAllowed(String action, String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(action);
                    _data.writeString(packageName);
                    boolean z = false;
                    if (!this.mRemote.transact(193, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isProvisioningAllowed(action, packageName);
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

            public int checkProvisioningPreCondition(String action, String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(action);
                    _data.writeString(packageName);
                    if (!this.mRemote.transact(194, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().checkProvisioningPreCondition(action, packageName);
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

            public void setKeepUninstalledPackages(ComponentName admin, String callerPackage, List<String> packageList) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (admin != null) {
                        _data.writeInt(1);
                        admin.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(callerPackage);
                    _data.writeStringList(packageList);
                    if (this.mRemote.transact(195, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setKeepUninstalledPackages(admin, callerPackage, packageList);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public List<String> getKeepUninstalledPackages(ComponentName admin, String callerPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (admin != null) {
                        _data.writeInt(1);
                        admin.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(callerPackage);
                    if (!this.mRemote.transact(196, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getKeepUninstalledPackages(admin, callerPackage);
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

            public boolean isManagedProfile(ComponentName admin) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (admin != null) {
                        _data.writeInt(1);
                        admin.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(197, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isManagedProfile(admin);
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

            public boolean isSystemOnlyUser(ComponentName admin) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (admin != null) {
                        _data.writeInt(1);
                        admin.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(198, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isSystemOnlyUser(admin);
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

            public String getWifiMacAddress(ComponentName admin) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (admin != null) {
                        _data.writeInt(1);
                        admin.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(199, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getWifiMacAddress(admin);
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

            public void reboot(ComponentName admin) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (admin != null) {
                        _data.writeInt(1);
                        admin.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(200, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().reboot(admin);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setShortSupportMessage(ComponentName admin, CharSequence message) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (admin != null) {
                        _data.writeInt(1);
                        admin.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (message != null) {
                        _data.writeInt(1);
                        TextUtils.writeToParcel(message, _data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(201, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setShortSupportMessage(admin, message);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public CharSequence getShortSupportMessage(ComponentName admin) throws RemoteException {
                CharSequence _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (admin != null) {
                        _data.writeInt(1);
                        admin.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(202, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getShortSupportMessage(admin);
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

            public void setLongSupportMessage(ComponentName admin, CharSequence message) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (admin != null) {
                        _data.writeInt(1);
                        admin.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (message != null) {
                        _data.writeInt(1);
                        TextUtils.writeToParcel(message, _data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(203, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setLongSupportMessage(admin, message);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public CharSequence getLongSupportMessage(ComponentName admin) throws RemoteException {
                CharSequence _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (admin != null) {
                        _data.writeInt(1);
                        admin.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(204, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getLongSupportMessage(admin);
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

            public CharSequence getShortSupportMessageForUser(ComponentName admin, int userHandle) throws RemoteException {
                CharSequence _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (admin != null) {
                        _data.writeInt(1);
                        admin.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(userHandle);
                    if (!this.mRemote.transact(205, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getShortSupportMessageForUser(admin, userHandle);
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

            public CharSequence getLongSupportMessageForUser(ComponentName admin, int userHandle) throws RemoteException {
                CharSequence _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (admin != null) {
                        _data.writeInt(1);
                        admin.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(userHandle);
                    if (!this.mRemote.transact(206, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getLongSupportMessageForUser(admin, userHandle);
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

            public boolean isSeparateProfileChallengeAllowed(int userHandle) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userHandle);
                    boolean z = false;
                    if (!this.mRemote.transact(207, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isSeparateProfileChallengeAllowed(userHandle);
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

            public void setOrganizationColor(ComponentName admin, int color) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (admin != null) {
                        _data.writeInt(1);
                        admin.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(color);
                    if (this.mRemote.transact(208, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setOrganizationColor(admin, color);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setOrganizationColorForUser(int color, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(color);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(209, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setOrganizationColorForUser(color, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getOrganizationColor(ComponentName admin) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (admin != null) {
                        _data.writeInt(1);
                        admin.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(210, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getOrganizationColor(admin);
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

            public int getOrganizationColorForUser(int userHandle) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userHandle);
                    if (!this.mRemote.transact(211, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getOrganizationColorForUser(userHandle);
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

            public void setOrganizationName(ComponentName admin, CharSequence title) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (admin != null) {
                        _data.writeInt(1);
                        admin.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (title != null) {
                        _data.writeInt(1);
                        TextUtils.writeToParcel(title, _data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(212, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setOrganizationName(admin, title);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public CharSequence getOrganizationName(ComponentName admin) throws RemoteException {
                CharSequence _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (admin != null) {
                        _data.writeInt(1);
                        admin.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(213, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getOrganizationName(admin);
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

            public CharSequence getDeviceOwnerOrganizationName() throws RemoteException {
                CharSequence _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(214, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getDeviceOwnerOrganizationName();
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

            public CharSequence getOrganizationNameForUser(int userHandle) throws RemoteException {
                CharSequence _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userHandle);
                    if (!this.mRemote.transact(215, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getOrganizationNameForUser(userHandle);
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

            public int getUserProvisioningState() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(216, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getUserProvisioningState();
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

            public void setUserProvisioningState(int state, int userHandle) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(state);
                    _data.writeInt(userHandle);
                    if (this.mRemote.transact(217, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setUserProvisioningState(state, userHandle);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setAffiliationIds(ComponentName admin, List<String> ids) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (admin != null) {
                        _data.writeInt(1);
                        admin.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeStringList(ids);
                    if (this.mRemote.transact(218, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setAffiliationIds(admin, ids);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public List<String> getAffiliationIds(ComponentName admin) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (admin != null) {
                        _data.writeInt(1);
                        admin.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(219, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAffiliationIds(admin);
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

            public boolean isAffiliatedUser() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(220, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isAffiliatedUser();
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

            public void setSecurityLoggingEnabled(ComponentName admin, boolean enabled) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (admin != null) {
                        _data.writeInt(1);
                        admin.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(enabled);
                    if (this.mRemote.transact(221, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setSecurityLoggingEnabled(admin, enabled);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isSecurityLoggingEnabled(ComponentName admin) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (admin != null) {
                        _data.writeInt(1);
                        admin.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(222, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isSecurityLoggingEnabled(admin);
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

            public ParceledListSlice retrieveSecurityLogs(ComponentName admin) throws RemoteException {
                ParceledListSlice _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (admin != null) {
                        _data.writeInt(1);
                        admin.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(223, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().retrieveSecurityLogs(admin);
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

            public ParceledListSlice retrievePreRebootSecurityLogs(ComponentName admin) throws RemoteException {
                ParceledListSlice _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (admin != null) {
                        _data.writeInt(1);
                        admin.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(224, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().retrievePreRebootSecurityLogs(admin);
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

            public long forceNetworkLogs() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(225, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().forceNetworkLogs();
                    }
                    _reply.readException();
                    long _result = _reply.readLong();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public long forceSecurityLogs() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(226, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().forceSecurityLogs();
                    }
                    _reply.readException();
                    long _result = _reply.readLong();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isUninstallInQueue(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    boolean z = false;
                    if (!this.mRemote.transact(227, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isUninstallInQueue(packageName);
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

            public void uninstallPackageWithActiveAdmins(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    if (this.mRemote.transact(228, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().uninstallPackageWithActiveAdmins(packageName);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isDeviceProvisioned() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(229, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isDeviceProvisioned();
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

            public boolean isDeviceProvisioningConfigApplied() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(230, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isDeviceProvisioningConfigApplied();
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

            public void setDeviceProvisioningConfigApplied() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(231, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setDeviceProvisioningConfigApplied();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void forceUpdateUserSetupComplete() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(232, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().forceUpdateUserSetupComplete();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setBackupServiceEnabled(ComponentName admin, boolean enabled) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (admin != null) {
                        _data.writeInt(1);
                        admin.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(enabled);
                    if (this.mRemote.transact(233, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setBackupServiceEnabled(admin, enabled);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isBackupServiceEnabled(ComponentName admin) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (admin != null) {
                        _data.writeInt(1);
                        admin.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(234, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isBackupServiceEnabled(admin);
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

            public void setNetworkLoggingEnabled(ComponentName admin, String packageName, boolean enabled) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (admin != null) {
                        _data.writeInt(1);
                        admin.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(packageName);
                    _data.writeInt(enabled);
                    if (this.mRemote.transact(235, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setNetworkLoggingEnabled(admin, packageName, enabled);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isNetworkLoggingEnabled(ComponentName admin, String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (admin != null) {
                        _data.writeInt(1);
                        admin.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(packageName);
                    if (!this.mRemote.transact(236, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isNetworkLoggingEnabled(admin, packageName);
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

            public List<NetworkEvent> retrieveNetworkLogs(ComponentName admin, String packageName, long batchToken) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (admin != null) {
                        _data.writeInt(1);
                        admin.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(packageName);
                    _data.writeLong(batchToken);
                    if (!this.mRemote.transact(237, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().retrieveNetworkLogs(admin, packageName, batchToken);
                    }
                    _reply.readException();
                    List<NetworkEvent> _result = _reply.createTypedArrayList(NetworkEvent.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean bindDeviceAdminServiceAsUser(ComponentName admin, IApplicationThread caller, IBinder token, Intent service, IServiceConnection connection, int flags, int targetUserId) throws RemoteException {
                ComponentName componentName = admin;
                Intent intent = service;
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
                        if (connection != null) {
                            iBinder = connection.asBinder();
                        }
                        _data.writeStrongBinder(iBinder);
                        _data.writeInt(flags);
                        _data.writeInt(targetUserId);
                        if (this.mRemote.transact(238, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                            _reply.readException();
                            if (_reply.readInt() == 0) {
                                _result = false;
                            }
                            _reply.recycle();
                            _data.recycle();
                            return _result;
                        }
                        boolean bindDeviceAdminServiceAsUser = Stub.getDefaultImpl().bindDeviceAdminServiceAsUser(admin, caller, token, service, connection, flags, targetUserId);
                        _reply.recycle();
                        _data.recycle();
                        return bindDeviceAdminServiceAsUser;
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

            public List<UserHandle> getBindDeviceAdminTargetUsers(ComponentName admin) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (admin != null) {
                        _data.writeInt(1);
                        admin.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(239, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getBindDeviceAdminTargetUsers(admin);
                    }
                    _reply.readException();
                    List<UserHandle> _result = _reply.createTypedArrayList(UserHandle.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isEphemeralUser(ComponentName admin) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (admin != null) {
                        _data.writeInt(1);
                        admin.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(240, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isEphemeralUser(admin);
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

            public long getLastSecurityLogRetrievalTime() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(241, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getLastSecurityLogRetrievalTime();
                    }
                    _reply.readException();
                    long _result = _reply.readLong();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public long getLastBugReportRequestTime() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(242, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getLastBugReportRequestTime();
                    }
                    _reply.readException();
                    long _result = _reply.readLong();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public long getLastNetworkLogRetrievalTime() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(243, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getLastNetworkLogRetrievalTime();
                    }
                    _reply.readException();
                    long _result = _reply.readLong();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean setResetPasswordToken(ComponentName admin, byte[] token) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (admin != null) {
                        _data.writeInt(1);
                        admin.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeByteArray(token);
                    if (!this.mRemote.transact(244, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setResetPasswordToken(admin, token);
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

            public boolean clearResetPasswordToken(ComponentName admin) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (admin != null) {
                        _data.writeInt(1);
                        admin.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(245, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().clearResetPasswordToken(admin);
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

            public boolean isResetPasswordTokenActive(ComponentName admin) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (admin != null) {
                        _data.writeInt(1);
                        admin.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(246, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isResetPasswordTokenActive(admin);
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

            public boolean resetPasswordWithToken(ComponentName admin, String password, byte[] token, int flags) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (admin != null) {
                        _data.writeInt(1);
                        admin.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(password);
                    _data.writeByteArray(token);
                    _data.writeInt(flags);
                    if (!this.mRemote.transact(247, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().resetPasswordWithToken(admin, password, token, flags);
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

            public boolean isCurrentInputMethodSetByOwner() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(248, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isCurrentInputMethodSetByOwner();
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

            public StringParceledListSlice getOwnerInstalledCaCerts(UserHandle user) throws RemoteException {
                StringParceledListSlice _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (user != null) {
                        _data.writeInt(1);
                        user.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(249, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getOwnerInstalledCaCerts(user);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = StringParceledListSlice.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    StringParceledListSlice _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void clearApplicationUserData(ComponentName admin, String packageName, IPackageDataObserver callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (admin != null) {
                        _data.writeInt(1);
                        admin.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(packageName);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(250, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().clearApplicationUserData(admin, packageName, callback);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setLogoutEnabled(ComponentName admin, boolean enabled) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (admin != null) {
                        _data.writeInt(1);
                        admin.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(enabled);
                    if (this.mRemote.transact(251, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setLogoutEnabled(admin, enabled);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isLogoutEnabled() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(252, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isLogoutEnabled();
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

            public List<String> getDisallowedSystemApps(ComponentName admin, int userId, String provisioningAction) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (admin != null) {
                        _data.writeInt(1);
                        admin.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(userId);
                    _data.writeString(provisioningAction);
                    if (!this.mRemote.transact(253, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getDisallowedSystemApps(admin, userId, provisioningAction);
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

            public void transferOwnership(ComponentName admin, ComponentName target, PersistableBundle bundle) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (admin != null) {
                        _data.writeInt(1);
                        admin.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (target != null) {
                        _data.writeInt(1);
                        target.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (bundle != null) {
                        _data.writeInt(1);
                        bundle.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(254, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().transferOwnership(admin, target, bundle);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public PersistableBundle getTransferOwnershipBundle() throws RemoteException {
                PersistableBundle _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(255, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getTransferOwnershipBundle();
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

            public void setStartUserSessionMessage(ComponentName admin, CharSequence startUserSessionMessage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (admin != null) {
                        _data.writeInt(1);
                        admin.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (startUserSessionMessage != null) {
                        _data.writeInt(1);
                        TextUtils.writeToParcel(startUserSessionMessage, _data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(256, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setStartUserSessionMessage(admin, startUserSessionMessage);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setEndUserSessionMessage(ComponentName admin, CharSequence endUserSessionMessage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (admin != null) {
                        _data.writeInt(1);
                        admin.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (endUserSessionMessage != null) {
                        _data.writeInt(1);
                        TextUtils.writeToParcel(endUserSessionMessage, _data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(257, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setEndUserSessionMessage(admin, endUserSessionMessage);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public CharSequence getStartUserSessionMessage(ComponentName admin) throws RemoteException {
                CharSequence _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (admin != null) {
                        _data.writeInt(1);
                        admin.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(258, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getStartUserSessionMessage(admin);
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

            public CharSequence getEndUserSessionMessage(ComponentName admin) throws RemoteException {
                CharSequence _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (admin != null) {
                        _data.writeInt(1);
                        admin.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(259, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getEndUserSessionMessage(admin);
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

            public List<String> setMeteredDataDisabledPackages(ComponentName admin, List<String> packageNames) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (admin != null) {
                        _data.writeInt(1);
                        admin.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeStringList(packageNames);
                    if (!this.mRemote.transact(260, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setMeteredDataDisabledPackages(admin, packageNames);
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

            public List<String> getMeteredDataDisabledPackages(ComponentName admin) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (admin != null) {
                        _data.writeInt(1);
                        admin.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(261, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getMeteredDataDisabledPackages(admin);
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

            public int addOverrideApn(ComponentName admin, ApnSetting apnSetting) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (admin != null) {
                        _data.writeInt(1);
                        admin.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (apnSetting != null) {
                        _data.writeInt(1);
                        apnSetting.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(262, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().addOverrideApn(admin, apnSetting);
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

            public boolean updateOverrideApn(ComponentName admin, int apnId, ApnSetting apnSetting) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (admin != null) {
                        _data.writeInt(1);
                        admin.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(apnId);
                    if (apnSetting != null) {
                        _data.writeInt(1);
                        apnSetting.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(263, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().updateOverrideApn(admin, apnId, apnSetting);
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

            public boolean removeOverrideApn(ComponentName admin, int apnId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (admin != null) {
                        _data.writeInt(1);
                        admin.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(apnId);
                    if (!this.mRemote.transact(264, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().removeOverrideApn(admin, apnId);
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

            public List<ApnSetting> getOverrideApns(ComponentName admin) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (admin != null) {
                        _data.writeInt(1);
                        admin.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(265, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getOverrideApns(admin);
                    }
                    _reply.readException();
                    List<ApnSetting> _result = _reply.createTypedArrayList(ApnSetting.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setOverrideApnsEnabled(ComponentName admin, boolean enabled) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (admin != null) {
                        _data.writeInt(1);
                        admin.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(enabled);
                    if (this.mRemote.transact(266, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setOverrideApnsEnabled(admin, enabled);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isOverrideApnEnabled(ComponentName admin) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (admin != null) {
                        _data.writeInt(1);
                        admin.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(267, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isOverrideApnEnabled(admin);
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

            public boolean isMeteredDataDisabledPackageForUser(ComponentName admin, String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (admin != null) {
                        _data.writeInt(1);
                        admin.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(268, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isMeteredDataDisabledPackageForUser(admin, packageName, userId);
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

            public int setGlobalPrivateDns(ComponentName admin, int mode, String privateDnsHost) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (admin != null) {
                        _data.writeInt(1);
                        admin.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(mode);
                    _data.writeString(privateDnsHost);
                    if (!this.mRemote.transact(269, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setGlobalPrivateDns(admin, mode, privateDnsHost);
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

            public int getGlobalPrivateDnsMode(ComponentName admin) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (admin != null) {
                        _data.writeInt(1);
                        admin.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(270, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getGlobalPrivateDnsMode(admin);
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

            public String getGlobalPrivateDnsHost(ComponentName admin) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (admin != null) {
                        _data.writeInt(1);
                        admin.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(271, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getGlobalPrivateDnsHost(admin);
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

            public void grantDeviceIdsAccessToProfileOwner(ComponentName who, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (who != null) {
                        _data.writeInt(1);
                        who.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(userId);
                    if (this.mRemote.transact(272, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().grantDeviceIdsAccessToProfileOwner(who, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void installUpdateFromFile(ComponentName admin, ParcelFileDescriptor updateFileDescriptor, StartInstallingUpdateCallback listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (admin != null) {
                        _data.writeInt(1);
                        admin.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (updateFileDescriptor != null) {
                        _data.writeInt(1);
                        updateFileDescriptor.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    if (this.mRemote.transact(273, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().installUpdateFromFile(admin, updateFileDescriptor, listener);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setCrossProfileCalendarPackages(ComponentName admin, List<String> packageNames) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (admin != null) {
                        _data.writeInt(1);
                        admin.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeStringList(packageNames);
                    if (this.mRemote.transact(274, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setCrossProfileCalendarPackages(admin, packageNames);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public List<String> getCrossProfileCalendarPackages(ComponentName admin) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (admin != null) {
                        _data.writeInt(1);
                        admin.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(275, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getCrossProfileCalendarPackages(admin);
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

            public boolean isPackageAllowedToAccessCalendarForUser(String packageName, int userHandle) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userHandle);
                    boolean z = false;
                    if (!this.mRemote.transact(276, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isPackageAllowedToAccessCalendarForUser(packageName, userHandle);
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

            public List<String> getCrossProfileCalendarPackagesForUser(int userHandle) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userHandle);
                    if (!this.mRemote.transact(277, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getCrossProfileCalendarPackagesForUser(userHandle);
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

            public boolean isManagedKiosk() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(278, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isManagedKiosk();
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

            public boolean isUnattendedManagedKiosk() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(279, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isUnattendedManagedKiosk();
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

            public boolean startViewCalendarEventInManagedProfile(String packageName, long eventId, long start, long end, boolean allDay, int flags) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeString(packageName);
                        try {
                            _data.writeLong(eventId);
                            _data.writeLong(start);
                            _data.writeLong(end);
                            _data.writeInt(allDay ? 1 : 0);
                            _data.writeInt(flags);
                            boolean z = false;
                            if (this.mRemote.transact(280, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                _reply.readException();
                                if (_reply.readInt() != 0) {
                                    z = true;
                                }
                                boolean _result = z;
                                _reply.recycle();
                                _data.recycle();
                                return _result;
                            }
                            boolean startViewCalendarEventInManagedProfile = Stub.getDefaultImpl().startViewCalendarEventInManagedProfile(packageName, eventId, start, end, allDay, flags);
                            _reply.recycle();
                            _data.recycle();
                            return startViewCalendarEventInManagedProfile;
                        } catch (Throwable th) {
                            th = th;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        long j = eventId;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    String str = packageName;
                    long j2 = eventId;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }
        }

        private boolean onTransact$installKeyPair$(Parcel data, Parcel reply) throws RemoteException {
            ComponentName _arg0;
            Parcel parcel = data;
            parcel.enforceInterface(DESCRIPTOR);
            if (data.readInt() != 0) {
                _arg0 = ComponentName.CREATOR.createFromParcel(parcel);
            } else {
                _arg0 = null;
            }
            boolean _result = installKeyPair(_arg0, data.readString(), data.createByteArray(), data.createByteArray(), data.createByteArray(), data.readString(), data.readInt() != 0, data.readInt() != 0);
            reply.writeNoException();
            reply.writeInt(_result);
            return true;
        }

        private boolean onTransact$setKeyPairCertificate$(Parcel data, Parcel reply) throws RemoteException {
            ComponentName _arg0;
            data.enforceInterface(DESCRIPTOR);
            if (data.readInt() != 0) {
                _arg0 = ComponentName.CREATOR.createFromParcel(data);
            } else {
                _arg0 = null;
            }
            boolean _result = setKeyPairCertificate(_arg0, data.readString(), data.readString(), data.createByteArray(), data.createByteArray(), data.readInt() != 0);
            reply.writeNoException();
            reply.writeInt(_result);
            return true;
        }

        private boolean onTransact$setPermissionGrantState$(Parcel data, Parcel reply) throws RemoteException {
            ComponentName _arg0;
            data.enforceInterface(DESCRIPTOR);
            RemoteCallback _arg5 = null;
            if (data.readInt() != 0) {
                _arg0 = ComponentName.CREATOR.createFromParcel(data);
            } else {
                _arg0 = null;
            }
            String _arg1 = data.readString();
            String _arg2 = data.readString();
            String _arg3 = data.readString();
            int _arg4 = data.readInt();
            if (data.readInt() != 0) {
                _arg5 = RemoteCallback.CREATOR.createFromParcel(data);
            }
            setPermissionGrantState(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$bindDeviceAdminServiceAsUser$(Parcel data, Parcel reply) throws RemoteException {
            ComponentName _arg0;
            data.enforceInterface(DESCRIPTOR);
            Intent _arg3 = null;
            if (data.readInt() != 0) {
                _arg0 = ComponentName.CREATOR.createFromParcel(data);
            } else {
                _arg0 = null;
            }
            IApplicationThread _arg1 = IApplicationThread.Stub.asInterface(data.readStrongBinder());
            IBinder _arg2 = data.readStrongBinder();
            if (data.readInt() != 0) {
                _arg3 = Intent.CREATOR.createFromParcel(data);
            }
            boolean _result = bindDeviceAdminServiceAsUser(_arg0, _arg1, _arg2, _arg3, IServiceConnection.Stub.asInterface(data.readStrongBinder()), data.readInt(), data.readInt());
            reply.writeNoException();
            reply.writeInt(_result);
            return true;
        }

        private boolean onTransact$startViewCalendarEventInManagedProfile$(Parcel data, Parcel reply) throws RemoteException {
            data.enforceInterface(DESCRIPTOR);
            boolean _result = startViewCalendarEventInManagedProfile(data.readString(), data.readLong(), data.readLong(), data.readLong(), data.readInt() != 0, data.readInt());
            reply.writeNoException();
            reply.writeInt(_result);
            return true;
        }

        public static boolean setDefaultImpl(IDevicePolicyManager impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IDevicePolicyManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
