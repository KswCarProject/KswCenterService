package android.app;

import android.annotation.UnsupportedAppUsage;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.pm.ParceledListSlice;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.UserHandle;
import android.service.notification.Adjustment;
import android.service.notification.Condition;
import android.service.notification.IConditionProvider;
import android.service.notification.INotificationListener;
import android.service.notification.StatusBarNotification;
import android.service.notification.ZenModeConfig;
import java.util.List;

public interface INotificationManager extends IInterface {
    String addAutomaticZenRule(AutomaticZenRule automaticZenRule) throws RemoteException;

    void allowAssistantAdjustment(String str) throws RemoteException;

    void applyAdjustmentFromAssistant(INotificationListener iNotificationListener, Adjustment adjustment) throws RemoteException;

    void applyAdjustmentsFromAssistant(INotificationListener iNotificationListener, List<Adjustment> list) throws RemoteException;

    void applyEnqueuedAdjustmentFromAssistant(INotificationListener iNotificationListener, Adjustment adjustment) throws RemoteException;

    void applyRestore(byte[] bArr, int i) throws RemoteException;

    boolean areBubblesAllowed(String str) throws RemoteException;

    boolean areBubblesAllowedForPackage(String str, int i) throws RemoteException;

    boolean areChannelsBypassingDnd() throws RemoteException;

    boolean areNotificationsEnabled(String str) throws RemoteException;

    @UnsupportedAppUsage
    boolean areNotificationsEnabledForPackage(String str, int i) throws RemoteException;

    boolean canNotifyAsPackage(String str, String str2, int i) throws RemoteException;

    boolean canShowBadge(String str, int i) throws RemoteException;

    @UnsupportedAppUsage
    void cancelAllNotifications(String str, int i) throws RemoteException;

    void cancelNotificationFromListener(INotificationListener iNotificationListener, String str, String str2, int i) throws RemoteException;

    @UnsupportedAppUsage
    void cancelNotificationWithTag(String str, String str2, int i, int i2) throws RemoteException;

    void cancelNotificationsFromListener(INotificationListener iNotificationListener, String[] strArr) throws RemoteException;

    @UnsupportedAppUsage
    void cancelToast(String str, ITransientNotification iTransientNotification) throws RemoteException;

    void clearData(String str, int i, boolean z) throws RemoteException;

    void clearRequestedListenerHints(INotificationListener iNotificationListener) throws RemoteException;

    void createNotificationChannelGroups(String str, ParceledListSlice parceledListSlice) throws RemoteException;

    void createNotificationChannels(String str, ParceledListSlice parceledListSlice) throws RemoteException;

    void createNotificationChannelsForPackage(String str, int i, ParceledListSlice parceledListSlice) throws RemoteException;

    void deleteNotificationChannel(String str, String str2) throws RemoteException;

    void deleteNotificationChannelGroup(String str, String str2) throws RemoteException;

    void disallowAssistantAdjustment(String str) throws RemoteException;

    void enqueueNotificationWithTag(String str, String str2, String str3, int i, Notification notification, int i2) throws RemoteException;

    @UnsupportedAppUsage
    void enqueueToast(String str, ITransientNotification iTransientNotification, int i, int i2) throws RemoteException;

    void finishToken(String str, ITransientNotification iTransientNotification) throws RemoteException;

    @UnsupportedAppUsage
    StatusBarNotification[] getActiveNotifications(String str) throws RemoteException;

    ParceledListSlice getActiveNotificationsFromListener(INotificationListener iNotificationListener, String[] strArr, int i) throws RemoteException;

    List<String> getAllowedAssistantAdjustments(String str) throws RemoteException;

    ComponentName getAllowedNotificationAssistant() throws RemoteException;

    ComponentName getAllowedNotificationAssistantForUser(int i) throws RemoteException;

    ParceledListSlice getAppActiveNotifications(String str, int i) throws RemoteException;

    int getAppsBypassingDndCount(int i) throws RemoteException;

    AutomaticZenRule getAutomaticZenRule(String str) throws RemoteException;

    byte[] getBackupPayload(int i) throws RemoteException;

    int getBlockedAppCount(int i) throws RemoteException;

    int getBlockedChannelCount(String str, int i) throws RemoteException;

    NotificationManager.Policy getConsolidatedNotificationPolicy() throws RemoteException;

    int getDeletedChannelCount(String str, int i) throws RemoteException;

    ComponentName getEffectsSuppressor() throws RemoteException;

    List<String> getEnabledNotificationListenerPackages() throws RemoteException;

    List<ComponentName> getEnabledNotificationListeners(int i) throws RemoteException;

    int getHintsFromListener(INotificationListener iNotificationListener) throws RemoteException;

    @UnsupportedAppUsage
    StatusBarNotification[] getHistoricalNotifications(String str, int i) throws RemoteException;

    int getInterruptionFilterFromListener(INotificationListener iNotificationListener) throws RemoteException;

    NotificationChannel getNotificationChannel(String str, int i, String str2, String str3) throws RemoteException;

    NotificationChannel getNotificationChannelForPackage(String str, int i, String str2, boolean z) throws RemoteException;

    NotificationChannelGroup getNotificationChannelGroup(String str, String str2) throws RemoteException;

    NotificationChannelGroup getNotificationChannelGroupForPackage(String str, String str2, int i) throws RemoteException;

    ParceledListSlice getNotificationChannelGroups(String str) throws RemoteException;

    ParceledListSlice getNotificationChannelGroupsForPackage(String str, int i, boolean z) throws RemoteException;

    ParceledListSlice getNotificationChannelGroupsFromPrivilegedListener(INotificationListener iNotificationListener, String str, UserHandle userHandle) throws RemoteException;

    ParceledListSlice getNotificationChannels(String str, String str2, int i) throws RemoteException;

    ParceledListSlice getNotificationChannelsBypassingDnd(String str, int i) throws RemoteException;

    ParceledListSlice getNotificationChannelsForPackage(String str, int i, boolean z) throws RemoteException;

    ParceledListSlice getNotificationChannelsFromPrivilegedListener(INotificationListener iNotificationListener, String str, UserHandle userHandle) throws RemoteException;

    String getNotificationDelegate(String str) throws RemoteException;

    NotificationManager.Policy getNotificationPolicy(String str) throws RemoteException;

    int getNumNotificationChannelsForPackage(String str, int i, boolean z) throws RemoteException;

    int getPackageImportance(String str) throws RemoteException;

    NotificationChannelGroup getPopulatedNotificationChannelGroupForPackage(String str, int i, String str2, boolean z) throws RemoteException;

    boolean getPrivateNotificationsAllowed() throws RemoteException;

    int getRuleInstanceCount(ComponentName componentName) throws RemoteException;

    ParceledListSlice getSnoozedNotificationsFromListener(INotificationListener iNotificationListener, int i) throws RemoteException;

    @UnsupportedAppUsage
    int getZenMode() throws RemoteException;

    @UnsupportedAppUsage
    ZenModeConfig getZenModeConfig() throws RemoteException;

    List<ZenModeConfig.ZenRule> getZenRules() throws RemoteException;

    boolean hasUserApprovedBubblesForPackage(String str, int i) throws RemoteException;

    boolean isNotificationAssistantAccessGranted(ComponentName componentName) throws RemoteException;

    boolean isNotificationListenerAccessGranted(ComponentName componentName) throws RemoteException;

    boolean isNotificationListenerAccessGrantedForUser(ComponentName componentName, int i) throws RemoteException;

    boolean isNotificationPolicyAccessGranted(String str) throws RemoteException;

    boolean isNotificationPolicyAccessGrantedForPackage(String str) throws RemoteException;

    boolean isPackagePaused(String str) throws RemoteException;

    boolean isSystemConditionProviderEnabled(String str) throws RemoteException;

    boolean matchesCallFilter(Bundle bundle) throws RemoteException;

    void notifyConditions(String str, IConditionProvider iConditionProvider, Condition[] conditionArr) throws RemoteException;

    boolean onlyHasDefaultChannel(String str, int i) throws RemoteException;

    void registerListener(INotificationListener iNotificationListener, ComponentName componentName, int i) throws RemoteException;

    boolean removeAutomaticZenRule(String str) throws RemoteException;

    boolean removeAutomaticZenRules(String str) throws RemoteException;

    void requestBindListener(ComponentName componentName) throws RemoteException;

    void requestBindProvider(ComponentName componentName) throws RemoteException;

    void requestHintsFromListener(INotificationListener iNotificationListener, int i) throws RemoteException;

    void requestInterruptionFilterFromListener(INotificationListener iNotificationListener, int i) throws RemoteException;

    void requestUnbindListener(INotificationListener iNotificationListener) throws RemoteException;

    void requestUnbindProvider(IConditionProvider iConditionProvider) throws RemoteException;

    void setAutomaticZenRuleState(String str, Condition condition) throws RemoteException;

    void setBubblesAllowed(String str, int i, boolean z) throws RemoteException;

    void setHideSilentStatusIcons(boolean z) throws RemoteException;

    void setInterruptionFilter(String str, int i) throws RemoteException;

    void setNotificationAssistantAccessGranted(ComponentName componentName, boolean z) throws RemoteException;

    void setNotificationAssistantAccessGrantedForUser(ComponentName componentName, int i, boolean z) throws RemoteException;

    void setNotificationDelegate(String str, String str2) throws RemoteException;

    void setNotificationListenerAccessGranted(ComponentName componentName, boolean z) throws RemoteException;

    void setNotificationListenerAccessGrantedForUser(ComponentName componentName, int i, boolean z) throws RemoteException;

    void setNotificationPolicy(String str, NotificationManager.Policy policy) throws RemoteException;

    void setNotificationPolicyAccessGranted(String str, boolean z) throws RemoteException;

    void setNotificationPolicyAccessGrantedForUser(String str, int i, boolean z) throws RemoteException;

    void setNotificationsEnabledForPackage(String str, int i, boolean z) throws RemoteException;

    void setNotificationsEnabledWithImportanceLockForPackage(String str, int i, boolean z) throws RemoteException;

    void setNotificationsShownFromListener(INotificationListener iNotificationListener, String[] strArr) throws RemoteException;

    void setOnNotificationPostedTrimFromListener(INotificationListener iNotificationListener, int i) throws RemoteException;

    void setPrivateNotificationsAllowed(boolean z) throws RemoteException;

    void setShowBadge(String str, int i, boolean z) throws RemoteException;

    void setZenMode(int i, Uri uri, String str) throws RemoteException;

    boolean shouldHideSilentStatusIcons(String str) throws RemoteException;

    void snoozeNotificationUntilContextFromListener(INotificationListener iNotificationListener, String str, String str2) throws RemoteException;

    void snoozeNotificationUntilFromListener(INotificationListener iNotificationListener, String str, long j) throws RemoteException;

    void unregisterListener(INotificationListener iNotificationListener, int i) throws RemoteException;

    void unsnoozeNotificationFromAssistant(INotificationListener iNotificationListener, String str) throws RemoteException;

    boolean updateAutomaticZenRule(String str, AutomaticZenRule automaticZenRule) throws RemoteException;

    void updateNotificationChannelForPackage(String str, int i, NotificationChannel notificationChannel) throws RemoteException;

    void updateNotificationChannelFromPrivilegedListener(INotificationListener iNotificationListener, String str, UserHandle userHandle, NotificationChannel notificationChannel) throws RemoteException;

    void updateNotificationChannelGroupForPackage(String str, int i, NotificationChannelGroup notificationChannelGroup) throws RemoteException;

    void updateNotificationChannelGroupFromPrivilegedListener(INotificationListener iNotificationListener, String str, UserHandle userHandle, NotificationChannelGroup notificationChannelGroup) throws RemoteException;

    public static class Default implements INotificationManager {
        public void cancelAllNotifications(String pkg, int userId) throws RemoteException {
        }

        public void clearData(String pkg, int uid, boolean fromApp) throws RemoteException {
        }

        public void enqueueToast(String pkg, ITransientNotification callback, int duration, int displayId) throws RemoteException {
        }

        public void cancelToast(String pkg, ITransientNotification callback) throws RemoteException {
        }

        public void finishToken(String pkg, ITransientNotification callback) throws RemoteException {
        }

        public void enqueueNotificationWithTag(String pkg, String opPkg, String tag, int id, Notification notification, int userId) throws RemoteException {
        }

        public void cancelNotificationWithTag(String pkg, String tag, int id, int userId) throws RemoteException {
        }

        public void setShowBadge(String pkg, int uid, boolean showBadge) throws RemoteException {
        }

        public boolean canShowBadge(String pkg, int uid) throws RemoteException {
            return false;
        }

        public void setNotificationsEnabledForPackage(String pkg, int uid, boolean enabled) throws RemoteException {
        }

        public void setNotificationsEnabledWithImportanceLockForPackage(String pkg, int uid, boolean enabled) throws RemoteException {
        }

        public boolean areNotificationsEnabledForPackage(String pkg, int uid) throws RemoteException {
            return false;
        }

        public boolean areNotificationsEnabled(String pkg) throws RemoteException {
            return false;
        }

        public int getPackageImportance(String pkg) throws RemoteException {
            return 0;
        }

        public List<String> getAllowedAssistantAdjustments(String pkg) throws RemoteException {
            return null;
        }

        public void allowAssistantAdjustment(String adjustmentType) throws RemoteException {
        }

        public void disallowAssistantAdjustment(String adjustmentType) throws RemoteException {
        }

        public boolean shouldHideSilentStatusIcons(String callingPkg) throws RemoteException {
            return false;
        }

        public void setHideSilentStatusIcons(boolean hide) throws RemoteException {
        }

        public void setBubblesAllowed(String pkg, int uid, boolean allowed) throws RemoteException {
        }

        public boolean areBubblesAllowed(String pkg) throws RemoteException {
            return false;
        }

        public boolean areBubblesAllowedForPackage(String pkg, int uid) throws RemoteException {
            return false;
        }

        public boolean hasUserApprovedBubblesForPackage(String pkg, int uid) throws RemoteException {
            return false;
        }

        public void createNotificationChannelGroups(String pkg, ParceledListSlice channelGroupList) throws RemoteException {
        }

        public void createNotificationChannels(String pkg, ParceledListSlice channelsList) throws RemoteException {
        }

        public void createNotificationChannelsForPackage(String pkg, int uid, ParceledListSlice channelsList) throws RemoteException {
        }

        public ParceledListSlice getNotificationChannelGroupsForPackage(String pkg, int uid, boolean includeDeleted) throws RemoteException {
            return null;
        }

        public NotificationChannelGroup getNotificationChannelGroupForPackage(String groupId, String pkg, int uid) throws RemoteException {
            return null;
        }

        public NotificationChannelGroup getPopulatedNotificationChannelGroupForPackage(String pkg, int uid, String groupId, boolean includeDeleted) throws RemoteException {
            return null;
        }

        public void updateNotificationChannelGroupForPackage(String pkg, int uid, NotificationChannelGroup group) throws RemoteException {
        }

        public void updateNotificationChannelForPackage(String pkg, int uid, NotificationChannel channel) throws RemoteException {
        }

        public NotificationChannel getNotificationChannel(String callingPkg, int userId, String pkg, String channelId) throws RemoteException {
            return null;
        }

        public NotificationChannel getNotificationChannelForPackage(String pkg, int uid, String channelId, boolean includeDeleted) throws RemoteException {
            return null;
        }

        public void deleteNotificationChannel(String pkg, String channelId) throws RemoteException {
        }

        public ParceledListSlice getNotificationChannels(String callingPkg, String targetPkg, int userId) throws RemoteException {
            return null;
        }

        public ParceledListSlice getNotificationChannelsForPackage(String pkg, int uid, boolean includeDeleted) throws RemoteException {
            return null;
        }

        public int getNumNotificationChannelsForPackage(String pkg, int uid, boolean includeDeleted) throws RemoteException {
            return 0;
        }

        public int getDeletedChannelCount(String pkg, int uid) throws RemoteException {
            return 0;
        }

        public int getBlockedChannelCount(String pkg, int uid) throws RemoteException {
            return 0;
        }

        public void deleteNotificationChannelGroup(String pkg, String channelGroupId) throws RemoteException {
        }

        public NotificationChannelGroup getNotificationChannelGroup(String pkg, String channelGroupId) throws RemoteException {
            return null;
        }

        public ParceledListSlice getNotificationChannelGroups(String pkg) throws RemoteException {
            return null;
        }

        public boolean onlyHasDefaultChannel(String pkg, int uid) throws RemoteException {
            return false;
        }

        public int getBlockedAppCount(int userId) throws RemoteException {
            return 0;
        }

        public boolean areChannelsBypassingDnd() throws RemoteException {
            return false;
        }

        public int getAppsBypassingDndCount(int uid) throws RemoteException {
            return 0;
        }

        public ParceledListSlice getNotificationChannelsBypassingDnd(String pkg, int userId) throws RemoteException {
            return null;
        }

        public boolean isPackagePaused(String pkg) throws RemoteException {
            return false;
        }

        public StatusBarNotification[] getActiveNotifications(String callingPkg) throws RemoteException {
            return null;
        }

        public StatusBarNotification[] getHistoricalNotifications(String callingPkg, int count) throws RemoteException {
            return null;
        }

        public void registerListener(INotificationListener listener, ComponentName component, int userid) throws RemoteException {
        }

        public void unregisterListener(INotificationListener listener, int userid) throws RemoteException {
        }

        public void cancelNotificationFromListener(INotificationListener token, String pkg, String tag, int id) throws RemoteException {
        }

        public void cancelNotificationsFromListener(INotificationListener token, String[] keys) throws RemoteException {
        }

        public void snoozeNotificationUntilContextFromListener(INotificationListener token, String key, String snoozeCriterionId) throws RemoteException {
        }

        public void snoozeNotificationUntilFromListener(INotificationListener token, String key, long until) throws RemoteException {
        }

        public void requestBindListener(ComponentName component) throws RemoteException {
        }

        public void requestUnbindListener(INotificationListener token) throws RemoteException {
        }

        public void requestBindProvider(ComponentName component) throws RemoteException {
        }

        public void requestUnbindProvider(IConditionProvider token) throws RemoteException {
        }

        public void setNotificationsShownFromListener(INotificationListener token, String[] keys) throws RemoteException {
        }

        public ParceledListSlice getActiveNotificationsFromListener(INotificationListener token, String[] keys, int trim) throws RemoteException {
            return null;
        }

        public ParceledListSlice getSnoozedNotificationsFromListener(INotificationListener token, int trim) throws RemoteException {
            return null;
        }

        public void clearRequestedListenerHints(INotificationListener token) throws RemoteException {
        }

        public void requestHintsFromListener(INotificationListener token, int hints) throws RemoteException {
        }

        public int getHintsFromListener(INotificationListener token) throws RemoteException {
            return 0;
        }

        public void requestInterruptionFilterFromListener(INotificationListener token, int interruptionFilter) throws RemoteException {
        }

        public int getInterruptionFilterFromListener(INotificationListener token) throws RemoteException {
            return 0;
        }

        public void setOnNotificationPostedTrimFromListener(INotificationListener token, int trim) throws RemoteException {
        }

        public void setInterruptionFilter(String pkg, int interruptionFilter) throws RemoteException {
        }

        public void updateNotificationChannelGroupFromPrivilegedListener(INotificationListener token, String pkg, UserHandle user, NotificationChannelGroup group) throws RemoteException {
        }

        public void updateNotificationChannelFromPrivilegedListener(INotificationListener token, String pkg, UserHandle user, NotificationChannel channel) throws RemoteException {
        }

        public ParceledListSlice getNotificationChannelsFromPrivilegedListener(INotificationListener token, String pkg, UserHandle user) throws RemoteException {
            return null;
        }

        public ParceledListSlice getNotificationChannelGroupsFromPrivilegedListener(INotificationListener token, String pkg, UserHandle user) throws RemoteException {
            return null;
        }

        public void applyEnqueuedAdjustmentFromAssistant(INotificationListener token, Adjustment adjustment) throws RemoteException {
        }

        public void applyAdjustmentFromAssistant(INotificationListener token, Adjustment adjustment) throws RemoteException {
        }

        public void applyAdjustmentsFromAssistant(INotificationListener token, List<Adjustment> list) throws RemoteException {
        }

        public void unsnoozeNotificationFromAssistant(INotificationListener token, String key) throws RemoteException {
        }

        public ComponentName getEffectsSuppressor() throws RemoteException {
            return null;
        }

        public boolean matchesCallFilter(Bundle extras) throws RemoteException {
            return false;
        }

        public boolean isSystemConditionProviderEnabled(String path) throws RemoteException {
            return false;
        }

        public boolean isNotificationListenerAccessGranted(ComponentName listener) throws RemoteException {
            return false;
        }

        public boolean isNotificationListenerAccessGrantedForUser(ComponentName listener, int userId) throws RemoteException {
            return false;
        }

        public boolean isNotificationAssistantAccessGranted(ComponentName assistant) throws RemoteException {
            return false;
        }

        public void setNotificationListenerAccessGranted(ComponentName listener, boolean enabled) throws RemoteException {
        }

        public void setNotificationAssistantAccessGranted(ComponentName assistant, boolean enabled) throws RemoteException {
        }

        public void setNotificationListenerAccessGrantedForUser(ComponentName listener, int userId, boolean enabled) throws RemoteException {
        }

        public void setNotificationAssistantAccessGrantedForUser(ComponentName assistant, int userId, boolean enabled) throws RemoteException {
        }

        public List<String> getEnabledNotificationListenerPackages() throws RemoteException {
            return null;
        }

        public List<ComponentName> getEnabledNotificationListeners(int userId) throws RemoteException {
            return null;
        }

        public ComponentName getAllowedNotificationAssistantForUser(int userId) throws RemoteException {
            return null;
        }

        public ComponentName getAllowedNotificationAssistant() throws RemoteException {
            return null;
        }

        public int getZenMode() throws RemoteException {
            return 0;
        }

        public ZenModeConfig getZenModeConfig() throws RemoteException {
            return null;
        }

        public NotificationManager.Policy getConsolidatedNotificationPolicy() throws RemoteException {
            return null;
        }

        public void setZenMode(int mode, Uri conditionId, String reason) throws RemoteException {
        }

        public void notifyConditions(String pkg, IConditionProvider provider, Condition[] conditions) throws RemoteException {
        }

        public boolean isNotificationPolicyAccessGranted(String pkg) throws RemoteException {
            return false;
        }

        public NotificationManager.Policy getNotificationPolicy(String pkg) throws RemoteException {
            return null;
        }

        public void setNotificationPolicy(String pkg, NotificationManager.Policy policy) throws RemoteException {
        }

        public boolean isNotificationPolicyAccessGrantedForPackage(String pkg) throws RemoteException {
            return false;
        }

        public void setNotificationPolicyAccessGranted(String pkg, boolean granted) throws RemoteException {
        }

        public void setNotificationPolicyAccessGrantedForUser(String pkg, int userId, boolean granted) throws RemoteException {
        }

        public AutomaticZenRule getAutomaticZenRule(String id) throws RemoteException {
            return null;
        }

        public List<ZenModeConfig.ZenRule> getZenRules() throws RemoteException {
            return null;
        }

        public String addAutomaticZenRule(AutomaticZenRule automaticZenRule) throws RemoteException {
            return null;
        }

        public boolean updateAutomaticZenRule(String id, AutomaticZenRule automaticZenRule) throws RemoteException {
            return false;
        }

        public boolean removeAutomaticZenRule(String id) throws RemoteException {
            return false;
        }

        public boolean removeAutomaticZenRules(String packageName) throws RemoteException {
            return false;
        }

        public int getRuleInstanceCount(ComponentName owner) throws RemoteException {
            return 0;
        }

        public void setAutomaticZenRuleState(String id, Condition condition) throws RemoteException {
        }

        public byte[] getBackupPayload(int user) throws RemoteException {
            return null;
        }

        public void applyRestore(byte[] payload, int user) throws RemoteException {
        }

        public ParceledListSlice getAppActiveNotifications(String callingPkg, int userId) throws RemoteException {
            return null;
        }

        public void setNotificationDelegate(String callingPkg, String delegate) throws RemoteException {
        }

        public String getNotificationDelegate(String callingPkg) throws RemoteException {
            return null;
        }

        public boolean canNotifyAsPackage(String callingPkg, String targetPkg, int userId) throws RemoteException {
            return false;
        }

        public void setPrivateNotificationsAllowed(boolean allow) throws RemoteException {
        }

        public boolean getPrivateNotificationsAllowed() throws RemoteException {
            return false;
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements INotificationManager {
        private static final String DESCRIPTOR = "android.app.INotificationManager";
        static final int TRANSACTION_addAutomaticZenRule = 106;
        static final int TRANSACTION_allowAssistantAdjustment = 16;
        static final int TRANSACTION_applyAdjustmentFromAssistant = 76;
        static final int TRANSACTION_applyAdjustmentsFromAssistant = 77;
        static final int TRANSACTION_applyEnqueuedAdjustmentFromAssistant = 75;
        static final int TRANSACTION_applyRestore = 113;
        static final int TRANSACTION_areBubblesAllowed = 21;
        static final int TRANSACTION_areBubblesAllowedForPackage = 22;
        static final int TRANSACTION_areChannelsBypassingDnd = 45;
        static final int TRANSACTION_areNotificationsEnabled = 13;
        static final int TRANSACTION_areNotificationsEnabledForPackage = 12;
        static final int TRANSACTION_canNotifyAsPackage = 117;
        static final int TRANSACTION_canShowBadge = 9;
        static final int TRANSACTION_cancelAllNotifications = 1;
        static final int TRANSACTION_cancelNotificationFromListener = 53;
        static final int TRANSACTION_cancelNotificationWithTag = 7;
        static final int TRANSACTION_cancelNotificationsFromListener = 54;
        static final int TRANSACTION_cancelToast = 4;
        static final int TRANSACTION_clearData = 2;
        static final int TRANSACTION_clearRequestedListenerHints = 64;
        static final int TRANSACTION_createNotificationChannelGroups = 24;
        static final int TRANSACTION_createNotificationChannels = 25;
        static final int TRANSACTION_createNotificationChannelsForPackage = 26;
        static final int TRANSACTION_deleteNotificationChannel = 34;
        static final int TRANSACTION_deleteNotificationChannelGroup = 40;
        static final int TRANSACTION_disallowAssistantAdjustment = 17;
        static final int TRANSACTION_enqueueNotificationWithTag = 6;
        static final int TRANSACTION_enqueueToast = 3;
        static final int TRANSACTION_finishToken = 5;
        static final int TRANSACTION_getActiveNotifications = 49;
        static final int TRANSACTION_getActiveNotificationsFromListener = 62;
        static final int TRANSACTION_getAllowedAssistantAdjustments = 15;
        static final int TRANSACTION_getAllowedNotificationAssistant = 92;
        static final int TRANSACTION_getAllowedNotificationAssistantForUser = 91;
        static final int TRANSACTION_getAppActiveNotifications = 114;
        static final int TRANSACTION_getAppsBypassingDndCount = 46;
        static final int TRANSACTION_getAutomaticZenRule = 104;
        static final int TRANSACTION_getBackupPayload = 112;
        static final int TRANSACTION_getBlockedAppCount = 44;
        static final int TRANSACTION_getBlockedChannelCount = 39;
        static final int TRANSACTION_getConsolidatedNotificationPolicy = 95;
        static final int TRANSACTION_getDeletedChannelCount = 38;
        static final int TRANSACTION_getEffectsSuppressor = 79;
        static final int TRANSACTION_getEnabledNotificationListenerPackages = 89;
        static final int TRANSACTION_getEnabledNotificationListeners = 90;
        static final int TRANSACTION_getHintsFromListener = 66;
        static final int TRANSACTION_getHistoricalNotifications = 50;
        static final int TRANSACTION_getInterruptionFilterFromListener = 68;
        static final int TRANSACTION_getNotificationChannel = 32;
        static final int TRANSACTION_getNotificationChannelForPackage = 33;
        static final int TRANSACTION_getNotificationChannelGroup = 41;
        static final int TRANSACTION_getNotificationChannelGroupForPackage = 28;
        static final int TRANSACTION_getNotificationChannelGroups = 42;
        static final int TRANSACTION_getNotificationChannelGroupsForPackage = 27;
        static final int TRANSACTION_getNotificationChannelGroupsFromPrivilegedListener = 74;
        static final int TRANSACTION_getNotificationChannels = 35;
        static final int TRANSACTION_getNotificationChannelsBypassingDnd = 47;
        static final int TRANSACTION_getNotificationChannelsForPackage = 36;
        static final int TRANSACTION_getNotificationChannelsFromPrivilegedListener = 73;
        static final int TRANSACTION_getNotificationDelegate = 116;
        static final int TRANSACTION_getNotificationPolicy = 99;
        static final int TRANSACTION_getNumNotificationChannelsForPackage = 37;
        static final int TRANSACTION_getPackageImportance = 14;
        static final int TRANSACTION_getPopulatedNotificationChannelGroupForPackage = 29;
        static final int TRANSACTION_getPrivateNotificationsAllowed = 119;
        static final int TRANSACTION_getRuleInstanceCount = 110;
        static final int TRANSACTION_getSnoozedNotificationsFromListener = 63;
        static final int TRANSACTION_getZenMode = 93;
        static final int TRANSACTION_getZenModeConfig = 94;
        static final int TRANSACTION_getZenRules = 105;
        static final int TRANSACTION_hasUserApprovedBubblesForPackage = 23;
        static final int TRANSACTION_isNotificationAssistantAccessGranted = 84;
        static final int TRANSACTION_isNotificationListenerAccessGranted = 82;
        static final int TRANSACTION_isNotificationListenerAccessGrantedForUser = 83;
        static final int TRANSACTION_isNotificationPolicyAccessGranted = 98;
        static final int TRANSACTION_isNotificationPolicyAccessGrantedForPackage = 101;
        static final int TRANSACTION_isPackagePaused = 48;
        static final int TRANSACTION_isSystemConditionProviderEnabled = 81;
        static final int TRANSACTION_matchesCallFilter = 80;
        static final int TRANSACTION_notifyConditions = 97;
        static final int TRANSACTION_onlyHasDefaultChannel = 43;
        static final int TRANSACTION_registerListener = 51;
        static final int TRANSACTION_removeAutomaticZenRule = 108;
        static final int TRANSACTION_removeAutomaticZenRules = 109;
        static final int TRANSACTION_requestBindListener = 57;
        static final int TRANSACTION_requestBindProvider = 59;
        static final int TRANSACTION_requestHintsFromListener = 65;
        static final int TRANSACTION_requestInterruptionFilterFromListener = 67;
        static final int TRANSACTION_requestUnbindListener = 58;
        static final int TRANSACTION_requestUnbindProvider = 60;
        static final int TRANSACTION_setAutomaticZenRuleState = 111;
        static final int TRANSACTION_setBubblesAllowed = 20;
        static final int TRANSACTION_setHideSilentStatusIcons = 19;
        static final int TRANSACTION_setInterruptionFilter = 70;
        static final int TRANSACTION_setNotificationAssistantAccessGranted = 86;
        static final int TRANSACTION_setNotificationAssistantAccessGrantedForUser = 88;
        static final int TRANSACTION_setNotificationDelegate = 115;
        static final int TRANSACTION_setNotificationListenerAccessGranted = 85;
        static final int TRANSACTION_setNotificationListenerAccessGrantedForUser = 87;
        static final int TRANSACTION_setNotificationPolicy = 100;
        static final int TRANSACTION_setNotificationPolicyAccessGranted = 102;
        static final int TRANSACTION_setNotificationPolicyAccessGrantedForUser = 103;
        static final int TRANSACTION_setNotificationsEnabledForPackage = 10;
        static final int TRANSACTION_setNotificationsEnabledWithImportanceLockForPackage = 11;
        static final int TRANSACTION_setNotificationsShownFromListener = 61;
        static final int TRANSACTION_setOnNotificationPostedTrimFromListener = 69;
        static final int TRANSACTION_setPrivateNotificationsAllowed = 118;
        static final int TRANSACTION_setShowBadge = 8;
        static final int TRANSACTION_setZenMode = 96;
        static final int TRANSACTION_shouldHideSilentStatusIcons = 18;
        static final int TRANSACTION_snoozeNotificationUntilContextFromListener = 55;
        static final int TRANSACTION_snoozeNotificationUntilFromListener = 56;
        static final int TRANSACTION_unregisterListener = 52;
        static final int TRANSACTION_unsnoozeNotificationFromAssistant = 78;
        static final int TRANSACTION_updateAutomaticZenRule = 107;
        static final int TRANSACTION_updateNotificationChannelForPackage = 31;
        static final int TRANSACTION_updateNotificationChannelFromPrivilegedListener = 72;
        static final int TRANSACTION_updateNotificationChannelGroupForPackage = 30;
        static final int TRANSACTION_updateNotificationChannelGroupFromPrivilegedListener = 71;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static INotificationManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof INotificationManager)) {
                return new Proxy(obj);
            }
            return (INotificationManager) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "cancelAllNotifications";
                case 2:
                    return "clearData";
                case 3:
                    return "enqueueToast";
                case 4:
                    return "cancelToast";
                case 5:
                    return "finishToken";
                case 6:
                    return "enqueueNotificationWithTag";
                case 7:
                    return "cancelNotificationWithTag";
                case 8:
                    return "setShowBadge";
                case 9:
                    return "canShowBadge";
                case 10:
                    return "setNotificationsEnabledForPackage";
                case 11:
                    return "setNotificationsEnabledWithImportanceLockForPackage";
                case 12:
                    return "areNotificationsEnabledForPackage";
                case 13:
                    return "areNotificationsEnabled";
                case 14:
                    return "getPackageImportance";
                case 15:
                    return "getAllowedAssistantAdjustments";
                case 16:
                    return "allowAssistantAdjustment";
                case 17:
                    return "disallowAssistantAdjustment";
                case 18:
                    return "shouldHideSilentStatusIcons";
                case 19:
                    return "setHideSilentStatusIcons";
                case 20:
                    return "setBubblesAllowed";
                case 21:
                    return "areBubblesAllowed";
                case 22:
                    return "areBubblesAllowedForPackage";
                case 23:
                    return "hasUserApprovedBubblesForPackage";
                case 24:
                    return "createNotificationChannelGroups";
                case 25:
                    return "createNotificationChannels";
                case 26:
                    return "createNotificationChannelsForPackage";
                case 27:
                    return "getNotificationChannelGroupsForPackage";
                case 28:
                    return "getNotificationChannelGroupForPackage";
                case 29:
                    return "getPopulatedNotificationChannelGroupForPackage";
                case 30:
                    return "updateNotificationChannelGroupForPackage";
                case 31:
                    return "updateNotificationChannelForPackage";
                case 32:
                    return "getNotificationChannel";
                case 33:
                    return "getNotificationChannelForPackage";
                case 34:
                    return "deleteNotificationChannel";
                case 35:
                    return "getNotificationChannels";
                case 36:
                    return "getNotificationChannelsForPackage";
                case 37:
                    return "getNumNotificationChannelsForPackage";
                case 38:
                    return "getDeletedChannelCount";
                case 39:
                    return "getBlockedChannelCount";
                case 40:
                    return "deleteNotificationChannelGroup";
                case 41:
                    return "getNotificationChannelGroup";
                case 42:
                    return "getNotificationChannelGroups";
                case 43:
                    return "onlyHasDefaultChannel";
                case 44:
                    return "getBlockedAppCount";
                case 45:
                    return "areChannelsBypassingDnd";
                case 46:
                    return "getAppsBypassingDndCount";
                case 47:
                    return "getNotificationChannelsBypassingDnd";
                case 48:
                    return "isPackagePaused";
                case 49:
                    return "getActiveNotifications";
                case 50:
                    return "getHistoricalNotifications";
                case 51:
                    return "registerListener";
                case 52:
                    return "unregisterListener";
                case 53:
                    return "cancelNotificationFromListener";
                case 54:
                    return "cancelNotificationsFromListener";
                case 55:
                    return "snoozeNotificationUntilContextFromListener";
                case 56:
                    return "snoozeNotificationUntilFromListener";
                case 57:
                    return "requestBindListener";
                case 58:
                    return "requestUnbindListener";
                case 59:
                    return "requestBindProvider";
                case 60:
                    return "requestUnbindProvider";
                case 61:
                    return "setNotificationsShownFromListener";
                case 62:
                    return "getActiveNotificationsFromListener";
                case 63:
                    return "getSnoozedNotificationsFromListener";
                case 64:
                    return "clearRequestedListenerHints";
                case 65:
                    return "requestHintsFromListener";
                case 66:
                    return "getHintsFromListener";
                case 67:
                    return "requestInterruptionFilterFromListener";
                case 68:
                    return "getInterruptionFilterFromListener";
                case 69:
                    return "setOnNotificationPostedTrimFromListener";
                case 70:
                    return "setInterruptionFilter";
                case 71:
                    return "updateNotificationChannelGroupFromPrivilegedListener";
                case 72:
                    return "updateNotificationChannelFromPrivilegedListener";
                case 73:
                    return "getNotificationChannelsFromPrivilegedListener";
                case 74:
                    return "getNotificationChannelGroupsFromPrivilegedListener";
                case 75:
                    return "applyEnqueuedAdjustmentFromAssistant";
                case 76:
                    return "applyAdjustmentFromAssistant";
                case 77:
                    return "applyAdjustmentsFromAssistant";
                case 78:
                    return "unsnoozeNotificationFromAssistant";
                case 79:
                    return "getEffectsSuppressor";
                case 80:
                    return "matchesCallFilter";
                case 81:
                    return "isSystemConditionProviderEnabled";
                case 82:
                    return "isNotificationListenerAccessGranted";
                case 83:
                    return "isNotificationListenerAccessGrantedForUser";
                case 84:
                    return "isNotificationAssistantAccessGranted";
                case 85:
                    return "setNotificationListenerAccessGranted";
                case 86:
                    return "setNotificationAssistantAccessGranted";
                case 87:
                    return "setNotificationListenerAccessGrantedForUser";
                case 88:
                    return "setNotificationAssistantAccessGrantedForUser";
                case 89:
                    return "getEnabledNotificationListenerPackages";
                case 90:
                    return "getEnabledNotificationListeners";
                case 91:
                    return "getAllowedNotificationAssistantForUser";
                case 92:
                    return "getAllowedNotificationAssistant";
                case 93:
                    return "getZenMode";
                case 94:
                    return "getZenModeConfig";
                case 95:
                    return "getConsolidatedNotificationPolicy";
                case 96:
                    return "setZenMode";
                case 97:
                    return "notifyConditions";
                case 98:
                    return "isNotificationPolicyAccessGranted";
                case 99:
                    return "getNotificationPolicy";
                case 100:
                    return "setNotificationPolicy";
                case 101:
                    return "isNotificationPolicyAccessGrantedForPackage";
                case 102:
                    return "setNotificationPolicyAccessGranted";
                case 103:
                    return "setNotificationPolicyAccessGrantedForUser";
                case 104:
                    return "getAutomaticZenRule";
                case 105:
                    return "getZenRules";
                case 106:
                    return "addAutomaticZenRule";
                case 107:
                    return "updateAutomaticZenRule";
                case 108:
                    return "removeAutomaticZenRule";
                case 109:
                    return "removeAutomaticZenRules";
                case 110:
                    return "getRuleInstanceCount";
                case 111:
                    return "setAutomaticZenRuleState";
                case 112:
                    return "getBackupPayload";
                case 113:
                    return "applyRestore";
                case 114:
                    return "getAppActiveNotifications";
                case 115:
                    return "setNotificationDelegate";
                case 116:
                    return "getNotificationDelegate";
                case 117:
                    return "canNotifyAsPackage";
                case 118:
                    return "setPrivateNotificationsAllowed";
                case 119:
                    return "getPrivateNotificationsAllowed";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v30, resolved type: android.content.pm.ParceledListSlice} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v34, resolved type: android.content.pm.ParceledListSlice} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v38, resolved type: android.content.pm.ParceledListSlice} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v45, resolved type: android.app.NotificationChannelGroup} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v49, resolved type: android.app.NotificationChannel} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v72, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v86, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v92, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v117, resolved type: android.app.NotificationChannelGroup} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v121, resolved type: android.app.NotificationChannel} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v125, resolved type: android.os.UserHandle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v129, resolved type: android.os.UserHandle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v133, resolved type: android.service.notification.Adjustment} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v137, resolved type: android.service.notification.Adjustment} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v146, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v151, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v155, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v159, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v163, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v167, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v171, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v175, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v193, resolved type: android.app.NotificationManager$Policy} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v202, resolved type: android.app.AutomaticZenRule} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v206, resolved type: android.app.AutomaticZenRule} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v212, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v216, resolved type: android.service.notification.Condition} */
        /* JADX WARNING: type inference failed for: r0v1 */
        /* JADX WARNING: type inference failed for: r0v7 */
        /* JADX WARNING: type inference failed for: r0v186, types: [android.net.Uri] */
        /* JADX WARNING: type inference failed for: r0v230 */
        /* JADX WARNING: type inference failed for: r0v231 */
        /* JADX WARNING: type inference failed for: r0v232 */
        /* JADX WARNING: type inference failed for: r0v233 */
        /* JADX WARNING: type inference failed for: r0v234 */
        /* JADX WARNING: type inference failed for: r0v235 */
        /* JADX WARNING: type inference failed for: r0v236 */
        /* JADX WARNING: type inference failed for: r0v237 */
        /* JADX WARNING: type inference failed for: r0v238 */
        /* JADX WARNING: type inference failed for: r0v239 */
        /* JADX WARNING: type inference failed for: r0v240 */
        /* JADX WARNING: type inference failed for: r0v241 */
        /* JADX WARNING: type inference failed for: r0v242 */
        /* JADX WARNING: type inference failed for: r0v243 */
        /* JADX WARNING: type inference failed for: r0v244 */
        /* JADX WARNING: type inference failed for: r0v245 */
        /* JADX WARNING: type inference failed for: r0v246 */
        /* JADX WARNING: type inference failed for: r0v247 */
        /* JADX WARNING: type inference failed for: r0v248 */
        /* JADX WARNING: type inference failed for: r0v249 */
        /* JADX WARNING: type inference failed for: r0v250 */
        /* JADX WARNING: type inference failed for: r0v251 */
        /* JADX WARNING: type inference failed for: r0v252 */
        /* JADX WARNING: type inference failed for: r0v253 */
        /* JADX WARNING: type inference failed for: r0v254 */
        /* JADX WARNING: type inference failed for: r0v255 */
        /* JADX WARNING: type inference failed for: r0v256 */
        /* JADX WARNING: type inference failed for: r0v257 */
        /* JADX WARNING: type inference failed for: r0v258 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r19, android.os.Parcel r20, android.os.Parcel r21, int r22) throws android.os.RemoteException {
            /*
                r18 = this;
                r7 = r18
                r8 = r19
                r9 = r20
                r10 = r21
                java.lang.String r11 = "android.app.INotificationManager"
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r12 = 1
                if (r8 == r0) goto L_0x0c52
                r0 = 0
                r1 = 0
                switch(r8) {
                    case 1: goto L_0x0c40;
                    case 2: goto L_0x0c26;
                    case 3: goto L_0x0c08;
                    case 4: goto L_0x0bf2;
                    case 5: goto L_0x0bdc;
                    case 6: goto L_0x0ba4;
                    case 7: goto L_0x0b8a;
                    case 8: goto L_0x0b70;
                    case 9: goto L_0x0b5a;
                    case 10: goto L_0x0b40;
                    case 11: goto L_0x0b26;
                    case 12: goto L_0x0b10;
                    case 13: goto L_0x0afe;
                    case 14: goto L_0x0aec;
                    case 15: goto L_0x0ada;
                    case 16: goto L_0x0acc;
                    case 17: goto L_0x0abe;
                    case 18: goto L_0x0aac;
                    case 19: goto L_0x0a99;
                    case 20: goto L_0x0a7f;
                    case 21: goto L_0x0a6d;
                    case 22: goto L_0x0a57;
                    case 23: goto L_0x0a41;
                    case 24: goto L_0x0a23;
                    case 25: goto L_0x0a05;
                    case 26: goto L_0x09e3;
                    case 27: goto L_0x09bb;
                    case 28: goto L_0x0998;
                    case 29: goto L_0x096c;
                    case 30: goto L_0x094a;
                    case 31: goto L_0x0928;
                    case 32: goto L_0x0901;
                    case 33: goto L_0x08d5;
                    case 34: goto L_0x08c3;
                    case 35: goto L_0x08a0;
                    case 36: goto L_0x0878;
                    case 37: goto L_0x085a;
                    case 38: goto L_0x0844;
                    case 39: goto L_0x082e;
                    case 40: goto L_0x081c;
                    case 41: goto L_0x07fd;
                    case 42: goto L_0x07e2;
                    case 43: goto L_0x07cc;
                    case 44: goto L_0x07ba;
                    case 45: goto L_0x07ac;
                    case 46: goto L_0x079a;
                    case 47: goto L_0x077b;
                    case 48: goto L_0x0769;
                    case 49: goto L_0x0757;
                    case 50: goto L_0x0741;
                    case 51: goto L_0x071b;
                    case 52: goto L_0x0705;
                    case 53: goto L_0x06e7;
                    case 54: goto L_0x06d1;
                    case 55: goto L_0x06b7;
                    case 56: goto L_0x069d;
                    case 57: goto L_0x0683;
                    case 58: goto L_0x0671;
                    case 59: goto L_0x0657;
                    case 60: goto L_0x0645;
                    case 61: goto L_0x062f;
                    case 62: goto L_0x0608;
                    case 63: goto L_0x05e5;
                    case 64: goto L_0x05d3;
                    case 65: goto L_0x05bd;
                    case 66: goto L_0x05a7;
                    case 67: goto L_0x0591;
                    case 68: goto L_0x057b;
                    case 69: goto L_0x0565;
                    case 70: goto L_0x0553;
                    case 71: goto L_0x051d;
                    case 72: goto L_0x04e7;
                    case 73: goto L_0x04b4;
                    case 74: goto L_0x0481;
                    case 75: goto L_0x045f;
                    case 76: goto L_0x043d;
                    case 77: goto L_0x0425;
                    case 78: goto L_0x040f;
                    case 79: goto L_0x03f8;
                    case 80: goto L_0x03da;
                    case 81: goto L_0x03c8;
                    case 82: goto L_0x03aa;
                    case 83: goto L_0x0388;
                    case 84: goto L_0x036a;
                    case 85: goto L_0x0348;
                    case 86: goto L_0x0326;
                    case 87: goto L_0x0300;
                    case 88: goto L_0x02da;
                    case 89: goto L_0x02cc;
                    case 90: goto L_0x02ba;
                    case 91: goto L_0x029f;
                    case 92: goto L_0x0288;
                    case 93: goto L_0x027a;
                    case 94: goto L_0x0263;
                    case 95: goto L_0x024c;
                    case 96: goto L_0x022d;
                    case 97: goto L_0x0212;
                    case 98: goto L_0x0200;
                    case 99: goto L_0x01e5;
                    case 100: goto L_0x01c7;
                    case 101: goto L_0x01b5;
                    case 102: goto L_0x019f;
                    case 103: goto L_0x0185;
                    case 104: goto L_0x016a;
                    case 105: goto L_0x015c;
                    case 106: goto L_0x013e;
                    case 107: goto L_0x011c;
                    case 108: goto L_0x010a;
                    case 109: goto L_0x00f8;
                    case 110: goto L_0x00da;
                    case 111: goto L_0x00bc;
                    case 112: goto L_0x00aa;
                    case 113: goto L_0x0098;
                    case 114: goto L_0x0079;
                    case 115: goto L_0x0067;
                    case 116: goto L_0x0055;
                    case 117: goto L_0x003b;
                    case 118: goto L_0x0028;
                    case 119: goto L_0x001a;
                    default: goto L_0x0015;
                }
            L_0x0015:
                boolean r0 = super.onTransact(r19, r20, r21, r22)
                return r0
            L_0x001a:
                r9.enforceInterface(r11)
                boolean r0 = r18.getPrivateNotificationsAllowed()
                r21.writeNoException()
                r10.writeInt(r0)
                return r12
            L_0x0028:
                r9.enforceInterface(r11)
                int r0 = r20.readInt()
                if (r0 == 0) goto L_0x0033
                r1 = r12
            L_0x0033:
                r0 = r1
                r7.setPrivateNotificationsAllowed(r0)
                r21.writeNoException()
                return r12
            L_0x003b:
                r9.enforceInterface(r11)
                java.lang.String r0 = r20.readString()
                java.lang.String r1 = r20.readString()
                int r2 = r20.readInt()
                boolean r3 = r7.canNotifyAsPackage(r0, r1, r2)
                r21.writeNoException()
                r10.writeInt(r3)
                return r12
            L_0x0055:
                r9.enforceInterface(r11)
                java.lang.String r0 = r20.readString()
                java.lang.String r1 = r7.getNotificationDelegate(r0)
                r21.writeNoException()
                r10.writeString(r1)
                return r12
            L_0x0067:
                r9.enforceInterface(r11)
                java.lang.String r0 = r20.readString()
                java.lang.String r1 = r20.readString()
                r7.setNotificationDelegate(r0, r1)
                r21.writeNoException()
                return r12
            L_0x0079:
                r9.enforceInterface(r11)
                java.lang.String r0 = r20.readString()
                int r2 = r20.readInt()
                android.content.pm.ParceledListSlice r3 = r7.getAppActiveNotifications(r0, r2)
                r21.writeNoException()
                if (r3 == 0) goto L_0x0094
                r10.writeInt(r12)
                r3.writeToParcel(r10, r12)
                goto L_0x0097
            L_0x0094:
                r10.writeInt(r1)
            L_0x0097:
                return r12
            L_0x0098:
                r9.enforceInterface(r11)
                byte[] r0 = r20.createByteArray()
                int r1 = r20.readInt()
                r7.applyRestore(r0, r1)
                r21.writeNoException()
                return r12
            L_0x00aa:
                r9.enforceInterface(r11)
                int r0 = r20.readInt()
                byte[] r1 = r7.getBackupPayload(r0)
                r21.writeNoException()
                r10.writeByteArray(r1)
                return r12
            L_0x00bc:
                r9.enforceInterface(r11)
                java.lang.String r1 = r20.readString()
                int r2 = r20.readInt()
                if (r2 == 0) goto L_0x00d2
                android.os.Parcelable$Creator<android.service.notification.Condition> r0 = android.service.notification.Condition.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.service.notification.Condition r0 = (android.service.notification.Condition) r0
                goto L_0x00d3
            L_0x00d2:
            L_0x00d3:
                r7.setAutomaticZenRuleState(r1, r0)
                r21.writeNoException()
                return r12
            L_0x00da:
                r9.enforceInterface(r11)
                int r1 = r20.readInt()
                if (r1 == 0) goto L_0x00ec
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x00ed
            L_0x00ec:
            L_0x00ed:
                int r1 = r7.getRuleInstanceCount(r0)
                r21.writeNoException()
                r10.writeInt(r1)
                return r12
            L_0x00f8:
                r9.enforceInterface(r11)
                java.lang.String r0 = r20.readString()
                boolean r1 = r7.removeAutomaticZenRules(r0)
                r21.writeNoException()
                r10.writeInt(r1)
                return r12
            L_0x010a:
                r9.enforceInterface(r11)
                java.lang.String r0 = r20.readString()
                boolean r1 = r7.removeAutomaticZenRule(r0)
                r21.writeNoException()
                r10.writeInt(r1)
                return r12
            L_0x011c:
                r9.enforceInterface(r11)
                java.lang.String r1 = r20.readString()
                int r2 = r20.readInt()
                if (r2 == 0) goto L_0x0132
                android.os.Parcelable$Creator<android.app.AutomaticZenRule> r0 = android.app.AutomaticZenRule.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.app.AutomaticZenRule r0 = (android.app.AutomaticZenRule) r0
                goto L_0x0133
            L_0x0132:
            L_0x0133:
                boolean r2 = r7.updateAutomaticZenRule(r1, r0)
                r21.writeNoException()
                r10.writeInt(r2)
                return r12
            L_0x013e:
                r9.enforceInterface(r11)
                int r1 = r20.readInt()
                if (r1 == 0) goto L_0x0150
                android.os.Parcelable$Creator<android.app.AutomaticZenRule> r0 = android.app.AutomaticZenRule.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.app.AutomaticZenRule r0 = (android.app.AutomaticZenRule) r0
                goto L_0x0151
            L_0x0150:
            L_0x0151:
                java.lang.String r1 = r7.addAutomaticZenRule(r0)
                r21.writeNoException()
                r10.writeString(r1)
                return r12
            L_0x015c:
                r9.enforceInterface(r11)
                java.util.List r0 = r18.getZenRules()
                r21.writeNoException()
                r10.writeTypedList(r0)
                return r12
            L_0x016a:
                r9.enforceInterface(r11)
                java.lang.String r0 = r20.readString()
                android.app.AutomaticZenRule r2 = r7.getAutomaticZenRule(r0)
                r21.writeNoException()
                if (r2 == 0) goto L_0x0181
                r10.writeInt(r12)
                r2.writeToParcel(r10, r12)
                goto L_0x0184
            L_0x0181:
                r10.writeInt(r1)
            L_0x0184:
                return r12
            L_0x0185:
                r9.enforceInterface(r11)
                java.lang.String r0 = r20.readString()
                int r2 = r20.readInt()
                int r3 = r20.readInt()
                if (r3 == 0) goto L_0x0198
                r1 = r12
            L_0x0198:
                r7.setNotificationPolicyAccessGrantedForUser(r0, r2, r1)
                r21.writeNoException()
                return r12
            L_0x019f:
                r9.enforceInterface(r11)
                java.lang.String r0 = r20.readString()
                int r2 = r20.readInt()
                if (r2 == 0) goto L_0x01ae
                r1 = r12
            L_0x01ae:
                r7.setNotificationPolicyAccessGranted(r0, r1)
                r21.writeNoException()
                return r12
            L_0x01b5:
                r9.enforceInterface(r11)
                java.lang.String r0 = r20.readString()
                boolean r1 = r7.isNotificationPolicyAccessGrantedForPackage(r0)
                r21.writeNoException()
                r10.writeInt(r1)
                return r12
            L_0x01c7:
                r9.enforceInterface(r11)
                java.lang.String r1 = r20.readString()
                int r2 = r20.readInt()
                if (r2 == 0) goto L_0x01dd
                android.os.Parcelable$Creator<android.app.NotificationManager$Policy> r0 = android.app.NotificationManager.Policy.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.app.NotificationManager$Policy r0 = (android.app.NotificationManager.Policy) r0
                goto L_0x01de
            L_0x01dd:
            L_0x01de:
                r7.setNotificationPolicy(r1, r0)
                r21.writeNoException()
                return r12
            L_0x01e5:
                r9.enforceInterface(r11)
                java.lang.String r0 = r20.readString()
                android.app.NotificationManager$Policy r2 = r7.getNotificationPolicy(r0)
                r21.writeNoException()
                if (r2 == 0) goto L_0x01fc
                r10.writeInt(r12)
                r2.writeToParcel(r10, r12)
                goto L_0x01ff
            L_0x01fc:
                r10.writeInt(r1)
            L_0x01ff:
                return r12
            L_0x0200:
                r9.enforceInterface(r11)
                java.lang.String r0 = r20.readString()
                boolean r1 = r7.isNotificationPolicyAccessGranted(r0)
                r21.writeNoException()
                r10.writeInt(r1)
                return r12
            L_0x0212:
                r9.enforceInterface(r11)
                java.lang.String r0 = r20.readString()
                android.os.IBinder r1 = r20.readStrongBinder()
                android.service.notification.IConditionProvider r1 = android.service.notification.IConditionProvider.Stub.asInterface(r1)
                android.os.Parcelable$Creator<android.service.notification.Condition> r2 = android.service.notification.Condition.CREATOR
                java.lang.Object[] r2 = r9.createTypedArray(r2)
                android.service.notification.Condition[] r2 = (android.service.notification.Condition[]) r2
                r7.notifyConditions(r0, r1, r2)
                return r12
            L_0x022d:
                r9.enforceInterface(r11)
                int r1 = r20.readInt()
                int r2 = r20.readInt()
                if (r2 == 0) goto L_0x0243
                android.os.Parcelable$Creator<android.net.Uri> r0 = android.net.Uri.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.net.Uri r0 = (android.net.Uri) r0
                goto L_0x0244
            L_0x0243:
            L_0x0244:
                java.lang.String r2 = r20.readString()
                r7.setZenMode(r1, r0, r2)
                return r12
            L_0x024c:
                r9.enforceInterface(r11)
                android.app.NotificationManager$Policy r0 = r18.getConsolidatedNotificationPolicy()
                r21.writeNoException()
                if (r0 == 0) goto L_0x025f
                r10.writeInt(r12)
                r0.writeToParcel(r10, r12)
                goto L_0x0262
            L_0x025f:
                r10.writeInt(r1)
            L_0x0262:
                return r12
            L_0x0263:
                r9.enforceInterface(r11)
                android.service.notification.ZenModeConfig r0 = r18.getZenModeConfig()
                r21.writeNoException()
                if (r0 == 0) goto L_0x0276
                r10.writeInt(r12)
                r0.writeToParcel(r10, r12)
                goto L_0x0279
            L_0x0276:
                r10.writeInt(r1)
            L_0x0279:
                return r12
            L_0x027a:
                r9.enforceInterface(r11)
                int r0 = r18.getZenMode()
                r21.writeNoException()
                r10.writeInt(r0)
                return r12
            L_0x0288:
                r9.enforceInterface(r11)
                android.content.ComponentName r0 = r18.getAllowedNotificationAssistant()
                r21.writeNoException()
                if (r0 == 0) goto L_0x029b
                r10.writeInt(r12)
                r0.writeToParcel((android.os.Parcel) r10, (int) r12)
                goto L_0x029e
            L_0x029b:
                r10.writeInt(r1)
            L_0x029e:
                return r12
            L_0x029f:
                r9.enforceInterface(r11)
                int r0 = r20.readInt()
                android.content.ComponentName r2 = r7.getAllowedNotificationAssistantForUser(r0)
                r21.writeNoException()
                if (r2 == 0) goto L_0x02b6
                r10.writeInt(r12)
                r2.writeToParcel((android.os.Parcel) r10, (int) r12)
                goto L_0x02b9
            L_0x02b6:
                r10.writeInt(r1)
            L_0x02b9:
                return r12
            L_0x02ba:
                r9.enforceInterface(r11)
                int r0 = r20.readInt()
                java.util.List r1 = r7.getEnabledNotificationListeners(r0)
                r21.writeNoException()
                r10.writeTypedList(r1)
                return r12
            L_0x02cc:
                r9.enforceInterface(r11)
                java.util.List r0 = r18.getEnabledNotificationListenerPackages()
                r21.writeNoException()
                r10.writeStringList(r0)
                return r12
            L_0x02da:
                r9.enforceInterface(r11)
                int r2 = r20.readInt()
                if (r2 == 0) goto L_0x02ec
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x02ed
            L_0x02ec:
            L_0x02ed:
                int r2 = r20.readInt()
                int r3 = r20.readInt()
                if (r3 == 0) goto L_0x02f9
                r1 = r12
            L_0x02f9:
                r7.setNotificationAssistantAccessGrantedForUser(r0, r2, r1)
                r21.writeNoException()
                return r12
            L_0x0300:
                r9.enforceInterface(r11)
                int r2 = r20.readInt()
                if (r2 == 0) goto L_0x0312
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x0313
            L_0x0312:
            L_0x0313:
                int r2 = r20.readInt()
                int r3 = r20.readInt()
                if (r3 == 0) goto L_0x031f
                r1 = r12
            L_0x031f:
                r7.setNotificationListenerAccessGrantedForUser(r0, r2, r1)
                r21.writeNoException()
                return r12
            L_0x0326:
                r9.enforceInterface(r11)
                int r2 = r20.readInt()
                if (r2 == 0) goto L_0x0338
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x0339
            L_0x0338:
            L_0x0339:
                int r2 = r20.readInt()
                if (r2 == 0) goto L_0x0341
                r1 = r12
            L_0x0341:
                r7.setNotificationAssistantAccessGranted(r0, r1)
                r21.writeNoException()
                return r12
            L_0x0348:
                r9.enforceInterface(r11)
                int r2 = r20.readInt()
                if (r2 == 0) goto L_0x035a
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x035b
            L_0x035a:
            L_0x035b:
                int r2 = r20.readInt()
                if (r2 == 0) goto L_0x0363
                r1 = r12
            L_0x0363:
                r7.setNotificationListenerAccessGranted(r0, r1)
                r21.writeNoException()
                return r12
            L_0x036a:
                r9.enforceInterface(r11)
                int r1 = r20.readInt()
                if (r1 == 0) goto L_0x037c
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x037d
            L_0x037c:
            L_0x037d:
                boolean r1 = r7.isNotificationAssistantAccessGranted(r0)
                r21.writeNoException()
                r10.writeInt(r1)
                return r12
            L_0x0388:
                r9.enforceInterface(r11)
                int r1 = r20.readInt()
                if (r1 == 0) goto L_0x039a
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x039b
            L_0x039a:
            L_0x039b:
                int r1 = r20.readInt()
                boolean r2 = r7.isNotificationListenerAccessGrantedForUser(r0, r1)
                r21.writeNoException()
                r10.writeInt(r2)
                return r12
            L_0x03aa:
                r9.enforceInterface(r11)
                int r1 = r20.readInt()
                if (r1 == 0) goto L_0x03bc
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x03bd
            L_0x03bc:
            L_0x03bd:
                boolean r1 = r7.isNotificationListenerAccessGranted(r0)
                r21.writeNoException()
                r10.writeInt(r1)
                return r12
            L_0x03c8:
                r9.enforceInterface(r11)
                java.lang.String r0 = r20.readString()
                boolean r1 = r7.isSystemConditionProviderEnabled(r0)
                r21.writeNoException()
                r10.writeInt(r1)
                return r12
            L_0x03da:
                r9.enforceInterface(r11)
                int r1 = r20.readInt()
                if (r1 == 0) goto L_0x03ec
                android.os.Parcelable$Creator<android.os.Bundle> r0 = android.os.Bundle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.os.Bundle r0 = (android.os.Bundle) r0
                goto L_0x03ed
            L_0x03ec:
            L_0x03ed:
                boolean r1 = r7.matchesCallFilter(r0)
                r21.writeNoException()
                r10.writeInt(r1)
                return r12
            L_0x03f8:
                r9.enforceInterface(r11)
                android.content.ComponentName r0 = r18.getEffectsSuppressor()
                r21.writeNoException()
                if (r0 == 0) goto L_0x040b
                r10.writeInt(r12)
                r0.writeToParcel((android.os.Parcel) r10, (int) r12)
                goto L_0x040e
            L_0x040b:
                r10.writeInt(r1)
            L_0x040e:
                return r12
            L_0x040f:
                r9.enforceInterface(r11)
                android.os.IBinder r0 = r20.readStrongBinder()
                android.service.notification.INotificationListener r0 = android.service.notification.INotificationListener.Stub.asInterface(r0)
                java.lang.String r1 = r20.readString()
                r7.unsnoozeNotificationFromAssistant(r0, r1)
                r21.writeNoException()
                return r12
            L_0x0425:
                r9.enforceInterface(r11)
                android.os.IBinder r0 = r20.readStrongBinder()
                android.service.notification.INotificationListener r0 = android.service.notification.INotificationListener.Stub.asInterface(r0)
                android.os.Parcelable$Creator<android.service.notification.Adjustment> r1 = android.service.notification.Adjustment.CREATOR
                java.util.ArrayList r1 = r9.createTypedArrayList(r1)
                r7.applyAdjustmentsFromAssistant(r0, r1)
                r21.writeNoException()
                return r12
            L_0x043d:
                r9.enforceInterface(r11)
                android.os.IBinder r1 = r20.readStrongBinder()
                android.service.notification.INotificationListener r1 = android.service.notification.INotificationListener.Stub.asInterface(r1)
                int r2 = r20.readInt()
                if (r2 == 0) goto L_0x0457
                android.os.Parcelable$Creator<android.service.notification.Adjustment> r0 = android.service.notification.Adjustment.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.service.notification.Adjustment r0 = (android.service.notification.Adjustment) r0
                goto L_0x0458
            L_0x0457:
            L_0x0458:
                r7.applyAdjustmentFromAssistant(r1, r0)
                r21.writeNoException()
                return r12
            L_0x045f:
                r9.enforceInterface(r11)
                android.os.IBinder r1 = r20.readStrongBinder()
                android.service.notification.INotificationListener r1 = android.service.notification.INotificationListener.Stub.asInterface(r1)
                int r2 = r20.readInt()
                if (r2 == 0) goto L_0x0479
                android.os.Parcelable$Creator<android.service.notification.Adjustment> r0 = android.service.notification.Adjustment.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.service.notification.Adjustment r0 = (android.service.notification.Adjustment) r0
                goto L_0x047a
            L_0x0479:
            L_0x047a:
                r7.applyEnqueuedAdjustmentFromAssistant(r1, r0)
                r21.writeNoException()
                return r12
            L_0x0481:
                r9.enforceInterface(r11)
                android.os.IBinder r2 = r20.readStrongBinder()
                android.service.notification.INotificationListener r2 = android.service.notification.INotificationListener.Stub.asInterface(r2)
                java.lang.String r3 = r20.readString()
                int r4 = r20.readInt()
                if (r4 == 0) goto L_0x049f
                android.os.Parcelable$Creator<android.os.UserHandle> r0 = android.os.UserHandle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.os.UserHandle r0 = (android.os.UserHandle) r0
                goto L_0x04a0
            L_0x049f:
            L_0x04a0:
                android.content.pm.ParceledListSlice r4 = r7.getNotificationChannelGroupsFromPrivilegedListener(r2, r3, r0)
                r21.writeNoException()
                if (r4 == 0) goto L_0x04b0
                r10.writeInt(r12)
                r4.writeToParcel(r10, r12)
                goto L_0x04b3
            L_0x04b0:
                r10.writeInt(r1)
            L_0x04b3:
                return r12
            L_0x04b4:
                r9.enforceInterface(r11)
                android.os.IBinder r2 = r20.readStrongBinder()
                android.service.notification.INotificationListener r2 = android.service.notification.INotificationListener.Stub.asInterface(r2)
                java.lang.String r3 = r20.readString()
                int r4 = r20.readInt()
                if (r4 == 0) goto L_0x04d2
                android.os.Parcelable$Creator<android.os.UserHandle> r0 = android.os.UserHandle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.os.UserHandle r0 = (android.os.UserHandle) r0
                goto L_0x04d3
            L_0x04d2:
            L_0x04d3:
                android.content.pm.ParceledListSlice r4 = r7.getNotificationChannelsFromPrivilegedListener(r2, r3, r0)
                r21.writeNoException()
                if (r4 == 0) goto L_0x04e3
                r10.writeInt(r12)
                r4.writeToParcel(r10, r12)
                goto L_0x04e6
            L_0x04e3:
                r10.writeInt(r1)
            L_0x04e6:
                return r12
            L_0x04e7:
                r9.enforceInterface(r11)
                android.os.IBinder r1 = r20.readStrongBinder()
                android.service.notification.INotificationListener r1 = android.service.notification.INotificationListener.Stub.asInterface(r1)
                java.lang.String r2 = r20.readString()
                int r3 = r20.readInt()
                if (r3 == 0) goto L_0x0505
                android.os.Parcelable$Creator<android.os.UserHandle> r3 = android.os.UserHandle.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r9)
                android.os.UserHandle r3 = (android.os.UserHandle) r3
                goto L_0x0506
            L_0x0505:
                r3 = r0
            L_0x0506:
                int r4 = r20.readInt()
                if (r4 == 0) goto L_0x0515
                android.os.Parcelable$Creator<android.app.NotificationChannel> r0 = android.app.NotificationChannel.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.app.NotificationChannel r0 = (android.app.NotificationChannel) r0
                goto L_0x0516
            L_0x0515:
            L_0x0516:
                r7.updateNotificationChannelFromPrivilegedListener(r1, r2, r3, r0)
                r21.writeNoException()
                return r12
            L_0x051d:
                r9.enforceInterface(r11)
                android.os.IBinder r1 = r20.readStrongBinder()
                android.service.notification.INotificationListener r1 = android.service.notification.INotificationListener.Stub.asInterface(r1)
                java.lang.String r2 = r20.readString()
                int r3 = r20.readInt()
                if (r3 == 0) goto L_0x053b
                android.os.Parcelable$Creator<android.os.UserHandle> r3 = android.os.UserHandle.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r9)
                android.os.UserHandle r3 = (android.os.UserHandle) r3
                goto L_0x053c
            L_0x053b:
                r3 = r0
            L_0x053c:
                int r4 = r20.readInt()
                if (r4 == 0) goto L_0x054b
                android.os.Parcelable$Creator<android.app.NotificationChannelGroup> r0 = android.app.NotificationChannelGroup.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.app.NotificationChannelGroup r0 = (android.app.NotificationChannelGroup) r0
                goto L_0x054c
            L_0x054b:
            L_0x054c:
                r7.updateNotificationChannelGroupFromPrivilegedListener(r1, r2, r3, r0)
                r21.writeNoException()
                return r12
            L_0x0553:
                r9.enforceInterface(r11)
                java.lang.String r0 = r20.readString()
                int r1 = r20.readInt()
                r7.setInterruptionFilter(r0, r1)
                r21.writeNoException()
                return r12
            L_0x0565:
                r9.enforceInterface(r11)
                android.os.IBinder r0 = r20.readStrongBinder()
                android.service.notification.INotificationListener r0 = android.service.notification.INotificationListener.Stub.asInterface(r0)
                int r1 = r20.readInt()
                r7.setOnNotificationPostedTrimFromListener(r0, r1)
                r21.writeNoException()
                return r12
            L_0x057b:
                r9.enforceInterface(r11)
                android.os.IBinder r0 = r20.readStrongBinder()
                android.service.notification.INotificationListener r0 = android.service.notification.INotificationListener.Stub.asInterface(r0)
                int r1 = r7.getInterruptionFilterFromListener(r0)
                r21.writeNoException()
                r10.writeInt(r1)
                return r12
            L_0x0591:
                r9.enforceInterface(r11)
                android.os.IBinder r0 = r20.readStrongBinder()
                android.service.notification.INotificationListener r0 = android.service.notification.INotificationListener.Stub.asInterface(r0)
                int r1 = r20.readInt()
                r7.requestInterruptionFilterFromListener(r0, r1)
                r21.writeNoException()
                return r12
            L_0x05a7:
                r9.enforceInterface(r11)
                android.os.IBinder r0 = r20.readStrongBinder()
                android.service.notification.INotificationListener r0 = android.service.notification.INotificationListener.Stub.asInterface(r0)
                int r1 = r7.getHintsFromListener(r0)
                r21.writeNoException()
                r10.writeInt(r1)
                return r12
            L_0x05bd:
                r9.enforceInterface(r11)
                android.os.IBinder r0 = r20.readStrongBinder()
                android.service.notification.INotificationListener r0 = android.service.notification.INotificationListener.Stub.asInterface(r0)
                int r1 = r20.readInt()
                r7.requestHintsFromListener(r0, r1)
                r21.writeNoException()
                return r12
            L_0x05d3:
                r9.enforceInterface(r11)
                android.os.IBinder r0 = r20.readStrongBinder()
                android.service.notification.INotificationListener r0 = android.service.notification.INotificationListener.Stub.asInterface(r0)
                r7.clearRequestedListenerHints(r0)
                r21.writeNoException()
                return r12
            L_0x05e5:
                r9.enforceInterface(r11)
                android.os.IBinder r0 = r20.readStrongBinder()
                android.service.notification.INotificationListener r0 = android.service.notification.INotificationListener.Stub.asInterface(r0)
                int r2 = r20.readInt()
                android.content.pm.ParceledListSlice r3 = r7.getSnoozedNotificationsFromListener(r0, r2)
                r21.writeNoException()
                if (r3 == 0) goto L_0x0604
                r10.writeInt(r12)
                r3.writeToParcel(r10, r12)
                goto L_0x0607
            L_0x0604:
                r10.writeInt(r1)
            L_0x0607:
                return r12
            L_0x0608:
                r9.enforceInterface(r11)
                android.os.IBinder r0 = r20.readStrongBinder()
                android.service.notification.INotificationListener r0 = android.service.notification.INotificationListener.Stub.asInterface(r0)
                java.lang.String[] r2 = r20.createStringArray()
                int r3 = r20.readInt()
                android.content.pm.ParceledListSlice r4 = r7.getActiveNotificationsFromListener(r0, r2, r3)
                r21.writeNoException()
                if (r4 == 0) goto L_0x062b
                r10.writeInt(r12)
                r4.writeToParcel(r10, r12)
                goto L_0x062e
            L_0x062b:
                r10.writeInt(r1)
            L_0x062e:
                return r12
            L_0x062f:
                r9.enforceInterface(r11)
                android.os.IBinder r0 = r20.readStrongBinder()
                android.service.notification.INotificationListener r0 = android.service.notification.INotificationListener.Stub.asInterface(r0)
                java.lang.String[] r1 = r20.createStringArray()
                r7.setNotificationsShownFromListener(r0, r1)
                r21.writeNoException()
                return r12
            L_0x0645:
                r9.enforceInterface(r11)
                android.os.IBinder r0 = r20.readStrongBinder()
                android.service.notification.IConditionProvider r0 = android.service.notification.IConditionProvider.Stub.asInterface(r0)
                r7.requestUnbindProvider(r0)
                r21.writeNoException()
                return r12
            L_0x0657:
                r9.enforceInterface(r11)
                int r1 = r20.readInt()
                if (r1 == 0) goto L_0x0669
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x066a
            L_0x0669:
            L_0x066a:
                r7.requestBindProvider(r0)
                r21.writeNoException()
                return r12
            L_0x0671:
                r9.enforceInterface(r11)
                android.os.IBinder r0 = r20.readStrongBinder()
                android.service.notification.INotificationListener r0 = android.service.notification.INotificationListener.Stub.asInterface(r0)
                r7.requestUnbindListener(r0)
                r21.writeNoException()
                return r12
            L_0x0683:
                r9.enforceInterface(r11)
                int r1 = r20.readInt()
                if (r1 == 0) goto L_0x0695
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x0696
            L_0x0695:
            L_0x0696:
                r7.requestBindListener(r0)
                r21.writeNoException()
                return r12
            L_0x069d:
                r9.enforceInterface(r11)
                android.os.IBinder r0 = r20.readStrongBinder()
                android.service.notification.INotificationListener r0 = android.service.notification.INotificationListener.Stub.asInterface(r0)
                java.lang.String r1 = r20.readString()
                long r2 = r20.readLong()
                r7.snoozeNotificationUntilFromListener(r0, r1, r2)
                r21.writeNoException()
                return r12
            L_0x06b7:
                r9.enforceInterface(r11)
                android.os.IBinder r0 = r20.readStrongBinder()
                android.service.notification.INotificationListener r0 = android.service.notification.INotificationListener.Stub.asInterface(r0)
                java.lang.String r1 = r20.readString()
                java.lang.String r2 = r20.readString()
                r7.snoozeNotificationUntilContextFromListener(r0, r1, r2)
                r21.writeNoException()
                return r12
            L_0x06d1:
                r9.enforceInterface(r11)
                android.os.IBinder r0 = r20.readStrongBinder()
                android.service.notification.INotificationListener r0 = android.service.notification.INotificationListener.Stub.asInterface(r0)
                java.lang.String[] r1 = r20.createStringArray()
                r7.cancelNotificationsFromListener(r0, r1)
                r21.writeNoException()
                return r12
            L_0x06e7:
                r9.enforceInterface(r11)
                android.os.IBinder r0 = r20.readStrongBinder()
                android.service.notification.INotificationListener r0 = android.service.notification.INotificationListener.Stub.asInterface(r0)
                java.lang.String r1 = r20.readString()
                java.lang.String r2 = r20.readString()
                int r3 = r20.readInt()
                r7.cancelNotificationFromListener(r0, r1, r2, r3)
                r21.writeNoException()
                return r12
            L_0x0705:
                r9.enforceInterface(r11)
                android.os.IBinder r0 = r20.readStrongBinder()
                android.service.notification.INotificationListener r0 = android.service.notification.INotificationListener.Stub.asInterface(r0)
                int r1 = r20.readInt()
                r7.unregisterListener(r0, r1)
                r21.writeNoException()
                return r12
            L_0x071b:
                r9.enforceInterface(r11)
                android.os.IBinder r1 = r20.readStrongBinder()
                android.service.notification.INotificationListener r1 = android.service.notification.INotificationListener.Stub.asInterface(r1)
                int r2 = r20.readInt()
                if (r2 == 0) goto L_0x0735
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x0736
            L_0x0735:
            L_0x0736:
                int r2 = r20.readInt()
                r7.registerListener(r1, r0, r2)
                r21.writeNoException()
                return r12
            L_0x0741:
                r9.enforceInterface(r11)
                java.lang.String r0 = r20.readString()
                int r1 = r20.readInt()
                android.service.notification.StatusBarNotification[] r2 = r7.getHistoricalNotifications(r0, r1)
                r21.writeNoException()
                r10.writeTypedArray(r2, r12)
                return r12
            L_0x0757:
                r9.enforceInterface(r11)
                java.lang.String r0 = r20.readString()
                android.service.notification.StatusBarNotification[] r1 = r7.getActiveNotifications(r0)
                r21.writeNoException()
                r10.writeTypedArray(r1, r12)
                return r12
            L_0x0769:
                r9.enforceInterface(r11)
                java.lang.String r0 = r20.readString()
                boolean r1 = r7.isPackagePaused(r0)
                r21.writeNoException()
                r10.writeInt(r1)
                return r12
            L_0x077b:
                r9.enforceInterface(r11)
                java.lang.String r0 = r20.readString()
                int r2 = r20.readInt()
                android.content.pm.ParceledListSlice r3 = r7.getNotificationChannelsBypassingDnd(r0, r2)
                r21.writeNoException()
                if (r3 == 0) goto L_0x0796
                r10.writeInt(r12)
                r3.writeToParcel(r10, r12)
                goto L_0x0799
            L_0x0796:
                r10.writeInt(r1)
            L_0x0799:
                return r12
            L_0x079a:
                r9.enforceInterface(r11)
                int r0 = r20.readInt()
                int r1 = r7.getAppsBypassingDndCount(r0)
                r21.writeNoException()
                r10.writeInt(r1)
                return r12
            L_0x07ac:
                r9.enforceInterface(r11)
                boolean r0 = r18.areChannelsBypassingDnd()
                r21.writeNoException()
                r10.writeInt(r0)
                return r12
            L_0x07ba:
                r9.enforceInterface(r11)
                int r0 = r20.readInt()
                int r1 = r7.getBlockedAppCount(r0)
                r21.writeNoException()
                r10.writeInt(r1)
                return r12
            L_0x07cc:
                r9.enforceInterface(r11)
                java.lang.String r0 = r20.readString()
                int r1 = r20.readInt()
                boolean r2 = r7.onlyHasDefaultChannel(r0, r1)
                r21.writeNoException()
                r10.writeInt(r2)
                return r12
            L_0x07e2:
                r9.enforceInterface(r11)
                java.lang.String r0 = r20.readString()
                android.content.pm.ParceledListSlice r2 = r7.getNotificationChannelGroups(r0)
                r21.writeNoException()
                if (r2 == 0) goto L_0x07f9
                r10.writeInt(r12)
                r2.writeToParcel(r10, r12)
                goto L_0x07fc
            L_0x07f9:
                r10.writeInt(r1)
            L_0x07fc:
                return r12
            L_0x07fd:
                r9.enforceInterface(r11)
                java.lang.String r0 = r20.readString()
                java.lang.String r2 = r20.readString()
                android.app.NotificationChannelGroup r3 = r7.getNotificationChannelGroup(r0, r2)
                r21.writeNoException()
                if (r3 == 0) goto L_0x0818
                r10.writeInt(r12)
                r3.writeToParcel(r10, r12)
                goto L_0x081b
            L_0x0818:
                r10.writeInt(r1)
            L_0x081b:
                return r12
            L_0x081c:
                r9.enforceInterface(r11)
                java.lang.String r0 = r20.readString()
                java.lang.String r1 = r20.readString()
                r7.deleteNotificationChannelGroup(r0, r1)
                r21.writeNoException()
                return r12
            L_0x082e:
                r9.enforceInterface(r11)
                java.lang.String r0 = r20.readString()
                int r1 = r20.readInt()
                int r2 = r7.getBlockedChannelCount(r0, r1)
                r21.writeNoException()
                r10.writeInt(r2)
                return r12
            L_0x0844:
                r9.enforceInterface(r11)
                java.lang.String r0 = r20.readString()
                int r1 = r20.readInt()
                int r2 = r7.getDeletedChannelCount(r0, r1)
                r21.writeNoException()
                r10.writeInt(r2)
                return r12
            L_0x085a:
                r9.enforceInterface(r11)
                java.lang.String r0 = r20.readString()
                int r2 = r20.readInt()
                int r3 = r20.readInt()
                if (r3 == 0) goto L_0x086d
                r1 = r12
            L_0x086d:
                int r3 = r7.getNumNotificationChannelsForPackage(r0, r2, r1)
                r21.writeNoException()
                r10.writeInt(r3)
                return r12
            L_0x0878:
                r9.enforceInterface(r11)
                java.lang.String r0 = r20.readString()
                int r2 = r20.readInt()
                int r3 = r20.readInt()
                if (r3 == 0) goto L_0x088b
                r3 = r12
                goto L_0x088c
            L_0x088b:
                r3 = r1
            L_0x088c:
                android.content.pm.ParceledListSlice r4 = r7.getNotificationChannelsForPackage(r0, r2, r3)
                r21.writeNoException()
                if (r4 == 0) goto L_0x089c
                r10.writeInt(r12)
                r4.writeToParcel(r10, r12)
                goto L_0x089f
            L_0x089c:
                r10.writeInt(r1)
            L_0x089f:
                return r12
            L_0x08a0:
                r9.enforceInterface(r11)
                java.lang.String r0 = r20.readString()
                java.lang.String r2 = r20.readString()
                int r3 = r20.readInt()
                android.content.pm.ParceledListSlice r4 = r7.getNotificationChannels(r0, r2, r3)
                r21.writeNoException()
                if (r4 == 0) goto L_0x08bf
                r10.writeInt(r12)
                r4.writeToParcel(r10, r12)
                goto L_0x08c2
            L_0x08bf:
                r10.writeInt(r1)
            L_0x08c2:
                return r12
            L_0x08c3:
                r9.enforceInterface(r11)
                java.lang.String r0 = r20.readString()
                java.lang.String r1 = r20.readString()
                r7.deleteNotificationChannel(r0, r1)
                r21.writeNoException()
                return r12
            L_0x08d5:
                r9.enforceInterface(r11)
                java.lang.String r0 = r20.readString()
                int r2 = r20.readInt()
                java.lang.String r3 = r20.readString()
                int r4 = r20.readInt()
                if (r4 == 0) goto L_0x08ec
                r4 = r12
                goto L_0x08ed
            L_0x08ec:
                r4 = r1
            L_0x08ed:
                android.app.NotificationChannel r5 = r7.getNotificationChannelForPackage(r0, r2, r3, r4)
                r21.writeNoException()
                if (r5 == 0) goto L_0x08fd
                r10.writeInt(r12)
                r5.writeToParcel(r10, r12)
                goto L_0x0900
            L_0x08fd:
                r10.writeInt(r1)
            L_0x0900:
                return r12
            L_0x0901:
                r9.enforceInterface(r11)
                java.lang.String r0 = r20.readString()
                int r2 = r20.readInt()
                java.lang.String r3 = r20.readString()
                java.lang.String r4 = r20.readString()
                android.app.NotificationChannel r5 = r7.getNotificationChannel(r0, r2, r3, r4)
                r21.writeNoException()
                if (r5 == 0) goto L_0x0924
                r10.writeInt(r12)
                r5.writeToParcel(r10, r12)
                goto L_0x0927
            L_0x0924:
                r10.writeInt(r1)
            L_0x0927:
                return r12
            L_0x0928:
                r9.enforceInterface(r11)
                java.lang.String r1 = r20.readString()
                int r2 = r20.readInt()
                int r3 = r20.readInt()
                if (r3 == 0) goto L_0x0942
                android.os.Parcelable$Creator<android.app.NotificationChannel> r0 = android.app.NotificationChannel.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.app.NotificationChannel r0 = (android.app.NotificationChannel) r0
                goto L_0x0943
            L_0x0942:
            L_0x0943:
                r7.updateNotificationChannelForPackage(r1, r2, r0)
                r21.writeNoException()
                return r12
            L_0x094a:
                r9.enforceInterface(r11)
                java.lang.String r1 = r20.readString()
                int r2 = r20.readInt()
                int r3 = r20.readInt()
                if (r3 == 0) goto L_0x0964
                android.os.Parcelable$Creator<android.app.NotificationChannelGroup> r0 = android.app.NotificationChannelGroup.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.app.NotificationChannelGroup r0 = (android.app.NotificationChannelGroup) r0
                goto L_0x0965
            L_0x0964:
            L_0x0965:
                r7.updateNotificationChannelGroupForPackage(r1, r2, r0)
                r21.writeNoException()
                return r12
            L_0x096c:
                r9.enforceInterface(r11)
                java.lang.String r0 = r20.readString()
                int r2 = r20.readInt()
                java.lang.String r3 = r20.readString()
                int r4 = r20.readInt()
                if (r4 == 0) goto L_0x0983
                r4 = r12
                goto L_0x0984
            L_0x0983:
                r4 = r1
            L_0x0984:
                android.app.NotificationChannelGroup r5 = r7.getPopulatedNotificationChannelGroupForPackage(r0, r2, r3, r4)
                r21.writeNoException()
                if (r5 == 0) goto L_0x0994
                r10.writeInt(r12)
                r5.writeToParcel(r10, r12)
                goto L_0x0997
            L_0x0994:
                r10.writeInt(r1)
            L_0x0997:
                return r12
            L_0x0998:
                r9.enforceInterface(r11)
                java.lang.String r0 = r20.readString()
                java.lang.String r2 = r20.readString()
                int r3 = r20.readInt()
                android.app.NotificationChannelGroup r4 = r7.getNotificationChannelGroupForPackage(r0, r2, r3)
                r21.writeNoException()
                if (r4 == 0) goto L_0x09b7
                r10.writeInt(r12)
                r4.writeToParcel(r10, r12)
                goto L_0x09ba
            L_0x09b7:
                r10.writeInt(r1)
            L_0x09ba:
                return r12
            L_0x09bb:
                r9.enforceInterface(r11)
                java.lang.String r0 = r20.readString()
                int r2 = r20.readInt()
                int r3 = r20.readInt()
                if (r3 == 0) goto L_0x09ce
                r3 = r12
                goto L_0x09cf
            L_0x09ce:
                r3 = r1
            L_0x09cf:
                android.content.pm.ParceledListSlice r4 = r7.getNotificationChannelGroupsForPackage(r0, r2, r3)
                r21.writeNoException()
                if (r4 == 0) goto L_0x09df
                r10.writeInt(r12)
                r4.writeToParcel(r10, r12)
                goto L_0x09e2
            L_0x09df:
                r10.writeInt(r1)
            L_0x09e2:
                return r12
            L_0x09e3:
                r9.enforceInterface(r11)
                java.lang.String r1 = r20.readString()
                int r2 = r20.readInt()
                int r3 = r20.readInt()
                if (r3 == 0) goto L_0x09fd
                android.os.Parcelable$ClassLoaderCreator<android.content.pm.ParceledListSlice> r0 = android.content.pm.ParceledListSlice.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.content.pm.ParceledListSlice r0 = (android.content.pm.ParceledListSlice) r0
                goto L_0x09fe
            L_0x09fd:
            L_0x09fe:
                r7.createNotificationChannelsForPackage(r1, r2, r0)
                r21.writeNoException()
                return r12
            L_0x0a05:
                r9.enforceInterface(r11)
                java.lang.String r1 = r20.readString()
                int r2 = r20.readInt()
                if (r2 == 0) goto L_0x0a1b
                android.os.Parcelable$ClassLoaderCreator<android.content.pm.ParceledListSlice> r0 = android.content.pm.ParceledListSlice.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.content.pm.ParceledListSlice r0 = (android.content.pm.ParceledListSlice) r0
                goto L_0x0a1c
            L_0x0a1b:
            L_0x0a1c:
                r7.createNotificationChannels(r1, r0)
                r21.writeNoException()
                return r12
            L_0x0a23:
                r9.enforceInterface(r11)
                java.lang.String r1 = r20.readString()
                int r2 = r20.readInt()
                if (r2 == 0) goto L_0x0a39
                android.os.Parcelable$ClassLoaderCreator<android.content.pm.ParceledListSlice> r0 = android.content.pm.ParceledListSlice.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.content.pm.ParceledListSlice r0 = (android.content.pm.ParceledListSlice) r0
                goto L_0x0a3a
            L_0x0a39:
            L_0x0a3a:
                r7.createNotificationChannelGroups(r1, r0)
                r21.writeNoException()
                return r12
            L_0x0a41:
                r9.enforceInterface(r11)
                java.lang.String r0 = r20.readString()
                int r1 = r20.readInt()
                boolean r2 = r7.hasUserApprovedBubblesForPackage(r0, r1)
                r21.writeNoException()
                r10.writeInt(r2)
                return r12
            L_0x0a57:
                r9.enforceInterface(r11)
                java.lang.String r0 = r20.readString()
                int r1 = r20.readInt()
                boolean r2 = r7.areBubblesAllowedForPackage(r0, r1)
                r21.writeNoException()
                r10.writeInt(r2)
                return r12
            L_0x0a6d:
                r9.enforceInterface(r11)
                java.lang.String r0 = r20.readString()
                boolean r1 = r7.areBubblesAllowed(r0)
                r21.writeNoException()
                r10.writeInt(r1)
                return r12
            L_0x0a7f:
                r9.enforceInterface(r11)
                java.lang.String r0 = r20.readString()
                int r2 = r20.readInt()
                int r3 = r20.readInt()
                if (r3 == 0) goto L_0x0a92
                r1 = r12
            L_0x0a92:
                r7.setBubblesAllowed(r0, r2, r1)
                r21.writeNoException()
                return r12
            L_0x0a99:
                r9.enforceInterface(r11)
                int r0 = r20.readInt()
                if (r0 == 0) goto L_0x0aa4
                r1 = r12
            L_0x0aa4:
                r0 = r1
                r7.setHideSilentStatusIcons(r0)
                r21.writeNoException()
                return r12
            L_0x0aac:
                r9.enforceInterface(r11)
                java.lang.String r0 = r20.readString()
                boolean r1 = r7.shouldHideSilentStatusIcons(r0)
                r21.writeNoException()
                r10.writeInt(r1)
                return r12
            L_0x0abe:
                r9.enforceInterface(r11)
                java.lang.String r0 = r20.readString()
                r7.disallowAssistantAdjustment(r0)
                r21.writeNoException()
                return r12
            L_0x0acc:
                r9.enforceInterface(r11)
                java.lang.String r0 = r20.readString()
                r7.allowAssistantAdjustment(r0)
                r21.writeNoException()
                return r12
            L_0x0ada:
                r9.enforceInterface(r11)
                java.lang.String r0 = r20.readString()
                java.util.List r1 = r7.getAllowedAssistantAdjustments(r0)
                r21.writeNoException()
                r10.writeStringList(r1)
                return r12
            L_0x0aec:
                r9.enforceInterface(r11)
                java.lang.String r0 = r20.readString()
                int r1 = r7.getPackageImportance(r0)
                r21.writeNoException()
                r10.writeInt(r1)
                return r12
            L_0x0afe:
                r9.enforceInterface(r11)
                java.lang.String r0 = r20.readString()
                boolean r1 = r7.areNotificationsEnabled(r0)
                r21.writeNoException()
                r10.writeInt(r1)
                return r12
            L_0x0b10:
                r9.enforceInterface(r11)
                java.lang.String r0 = r20.readString()
                int r1 = r20.readInt()
                boolean r2 = r7.areNotificationsEnabledForPackage(r0, r1)
                r21.writeNoException()
                r10.writeInt(r2)
                return r12
            L_0x0b26:
                r9.enforceInterface(r11)
                java.lang.String r0 = r20.readString()
                int r2 = r20.readInt()
                int r3 = r20.readInt()
                if (r3 == 0) goto L_0x0b39
                r1 = r12
            L_0x0b39:
                r7.setNotificationsEnabledWithImportanceLockForPackage(r0, r2, r1)
                r21.writeNoException()
                return r12
            L_0x0b40:
                r9.enforceInterface(r11)
                java.lang.String r0 = r20.readString()
                int r2 = r20.readInt()
                int r3 = r20.readInt()
                if (r3 == 0) goto L_0x0b53
                r1 = r12
            L_0x0b53:
                r7.setNotificationsEnabledForPackage(r0, r2, r1)
                r21.writeNoException()
                return r12
            L_0x0b5a:
                r9.enforceInterface(r11)
                java.lang.String r0 = r20.readString()
                int r1 = r20.readInt()
                boolean r2 = r7.canShowBadge(r0, r1)
                r21.writeNoException()
                r10.writeInt(r2)
                return r12
            L_0x0b70:
                r9.enforceInterface(r11)
                java.lang.String r0 = r20.readString()
                int r2 = r20.readInt()
                int r3 = r20.readInt()
                if (r3 == 0) goto L_0x0b83
                r1 = r12
            L_0x0b83:
                r7.setShowBadge(r0, r2, r1)
                r21.writeNoException()
                return r12
            L_0x0b8a:
                r9.enforceInterface(r11)
                java.lang.String r0 = r20.readString()
                java.lang.String r1 = r20.readString()
                int r2 = r20.readInt()
                int r3 = r20.readInt()
                r7.cancelNotificationWithTag(r0, r1, r2, r3)
                r21.writeNoException()
                return r12
            L_0x0ba4:
                r9.enforceInterface(r11)
                java.lang.String r13 = r20.readString()
                java.lang.String r14 = r20.readString()
                java.lang.String r15 = r20.readString()
                int r16 = r20.readInt()
                int r1 = r20.readInt()
                if (r1 == 0) goto L_0x0bc7
                android.os.Parcelable$Creator<android.app.Notification> r0 = android.app.Notification.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.app.Notification r0 = (android.app.Notification) r0
            L_0x0bc5:
                r5 = r0
                goto L_0x0bc8
            L_0x0bc7:
                goto L_0x0bc5
            L_0x0bc8:
                int r17 = r20.readInt()
                r0 = r18
                r1 = r13
                r2 = r14
                r3 = r15
                r4 = r16
                r6 = r17
                r0.enqueueNotificationWithTag(r1, r2, r3, r4, r5, r6)
                r21.writeNoException()
                return r12
            L_0x0bdc:
                r9.enforceInterface(r11)
                java.lang.String r0 = r20.readString()
                android.os.IBinder r1 = r20.readStrongBinder()
                android.app.ITransientNotification r1 = android.app.ITransientNotification.Stub.asInterface(r1)
                r7.finishToken(r0, r1)
                r21.writeNoException()
                return r12
            L_0x0bf2:
                r9.enforceInterface(r11)
                java.lang.String r0 = r20.readString()
                android.os.IBinder r1 = r20.readStrongBinder()
                android.app.ITransientNotification r1 = android.app.ITransientNotification.Stub.asInterface(r1)
                r7.cancelToast(r0, r1)
                r21.writeNoException()
                return r12
            L_0x0c08:
                r9.enforceInterface(r11)
                java.lang.String r0 = r20.readString()
                android.os.IBinder r1 = r20.readStrongBinder()
                android.app.ITransientNotification r1 = android.app.ITransientNotification.Stub.asInterface(r1)
                int r2 = r20.readInt()
                int r3 = r20.readInt()
                r7.enqueueToast(r0, r1, r2, r3)
                r21.writeNoException()
                return r12
            L_0x0c26:
                r9.enforceInterface(r11)
                java.lang.String r0 = r20.readString()
                int r2 = r20.readInt()
                int r3 = r20.readInt()
                if (r3 == 0) goto L_0x0c39
                r1 = r12
            L_0x0c39:
                r7.clearData(r0, r2, r1)
                r21.writeNoException()
                return r12
            L_0x0c40:
                r9.enforceInterface(r11)
                java.lang.String r0 = r20.readString()
                int r1 = r20.readInt()
                r7.cancelAllNotifications(r0, r1)
                r21.writeNoException()
                return r12
            L_0x0c52:
                r10.writeString(r11)
                return r12
            */
            throw new UnsupportedOperationException("Method not decompiled: android.app.INotificationManager.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements INotificationManager {
            public static INotificationManager sDefaultImpl;
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

            public void cancelAllNotifications(String pkg, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(pkg);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(1, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().cancelAllNotifications(pkg, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void clearData(String pkg, int uid, boolean fromApp) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(pkg);
                    _data.writeInt(uid);
                    _data.writeInt(fromApp);
                    if (this.mRemote.transact(2, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().clearData(pkg, uid, fromApp);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void enqueueToast(String pkg, ITransientNotification callback, int duration, int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(pkg);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    _data.writeInt(duration);
                    _data.writeInt(displayId);
                    if (this.mRemote.transact(3, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().enqueueToast(pkg, callback, duration, displayId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void cancelToast(String pkg, ITransientNotification callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(pkg);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(4, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().cancelToast(pkg, callback);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void finishToken(String pkg, ITransientNotification callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(pkg);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(5, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().finishToken(pkg, callback);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void enqueueNotificationWithTag(String pkg, String opPkg, String tag, int id, Notification notification, int userId) throws RemoteException {
                Notification notification2 = notification;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeString(pkg);
                        try {
                            _data.writeString(opPkg);
                        } catch (Throwable th) {
                            th = th;
                            String str = tag;
                            int i = id;
                            int i2 = userId;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                        try {
                            _data.writeString(tag);
                        } catch (Throwable th2) {
                            th = th2;
                            int i3 = id;
                            int i22 = userId;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        String str2 = opPkg;
                        String str3 = tag;
                        int i32 = id;
                        int i222 = userId;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(id);
                        if (notification2 != null) {
                            _data.writeInt(1);
                            notification2.writeToParcel(_data, 0);
                        } else {
                            _data.writeInt(0);
                        }
                        try {
                            _data.writeInt(userId);
                            if (this.mRemote.transact(6, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                _reply.readException();
                                _reply.recycle();
                                _data.recycle();
                                return;
                            }
                            Stub.getDefaultImpl().enqueueNotificationWithTag(pkg, opPkg, tag, id, notification, userId);
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
                        int i2222 = userId;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th6) {
                    th = th6;
                    String str4 = pkg;
                    String str22 = opPkg;
                    String str32 = tag;
                    int i322 = id;
                    int i22222 = userId;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public void cancelNotificationWithTag(String pkg, String tag, int id, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(pkg);
                    _data.writeString(tag);
                    _data.writeInt(id);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(7, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().cancelNotificationWithTag(pkg, tag, id, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setShowBadge(String pkg, int uid, boolean showBadge) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(pkg);
                    _data.writeInt(uid);
                    _data.writeInt(showBadge);
                    if (this.mRemote.transact(8, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setShowBadge(pkg, uid, showBadge);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean canShowBadge(String pkg, int uid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(pkg);
                    _data.writeInt(uid);
                    boolean z = false;
                    if (!this.mRemote.transact(9, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().canShowBadge(pkg, uid);
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

            public void setNotificationsEnabledForPackage(String pkg, int uid, boolean enabled) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(pkg);
                    _data.writeInt(uid);
                    _data.writeInt(enabled);
                    if (this.mRemote.transact(10, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setNotificationsEnabledForPackage(pkg, uid, enabled);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setNotificationsEnabledWithImportanceLockForPackage(String pkg, int uid, boolean enabled) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(pkg);
                    _data.writeInt(uid);
                    _data.writeInt(enabled);
                    if (this.mRemote.transact(11, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setNotificationsEnabledWithImportanceLockForPackage(pkg, uid, enabled);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean areNotificationsEnabledForPackage(String pkg, int uid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(pkg);
                    _data.writeInt(uid);
                    boolean z = false;
                    if (!this.mRemote.transact(12, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().areNotificationsEnabledForPackage(pkg, uid);
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

            public boolean areNotificationsEnabled(String pkg) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(pkg);
                    boolean z = false;
                    if (!this.mRemote.transact(13, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().areNotificationsEnabled(pkg);
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

            public int getPackageImportance(String pkg) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(pkg);
                    if (!this.mRemote.transact(14, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPackageImportance(pkg);
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

            public List<String> getAllowedAssistantAdjustments(String pkg) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(pkg);
                    if (!this.mRemote.transact(15, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAllowedAssistantAdjustments(pkg);
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

            public void allowAssistantAdjustment(String adjustmentType) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(adjustmentType);
                    if (this.mRemote.transact(16, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().allowAssistantAdjustment(adjustmentType);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void disallowAssistantAdjustment(String adjustmentType) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(adjustmentType);
                    if (this.mRemote.transact(17, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().disallowAssistantAdjustment(adjustmentType);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean shouldHideSilentStatusIcons(String callingPkg) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPkg);
                    boolean z = false;
                    if (!this.mRemote.transact(18, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().shouldHideSilentStatusIcons(callingPkg);
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

            public void setHideSilentStatusIcons(boolean hide) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(hide);
                    if (this.mRemote.transact(19, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setHideSilentStatusIcons(hide);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setBubblesAllowed(String pkg, int uid, boolean allowed) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(pkg);
                    _data.writeInt(uid);
                    _data.writeInt(allowed);
                    if (this.mRemote.transact(20, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setBubblesAllowed(pkg, uid, allowed);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean areBubblesAllowed(String pkg) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(pkg);
                    boolean z = false;
                    if (!this.mRemote.transact(21, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().areBubblesAllowed(pkg);
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

            public boolean areBubblesAllowedForPackage(String pkg, int uid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(pkg);
                    _data.writeInt(uid);
                    boolean z = false;
                    if (!this.mRemote.transact(22, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().areBubblesAllowedForPackage(pkg, uid);
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

            public boolean hasUserApprovedBubblesForPackage(String pkg, int uid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(pkg);
                    _data.writeInt(uid);
                    boolean z = false;
                    if (!this.mRemote.transact(23, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().hasUserApprovedBubblesForPackage(pkg, uid);
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

            public void createNotificationChannelGroups(String pkg, ParceledListSlice channelGroupList) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(pkg);
                    if (channelGroupList != null) {
                        _data.writeInt(1);
                        channelGroupList.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(24, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().createNotificationChannelGroups(pkg, channelGroupList);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void createNotificationChannels(String pkg, ParceledListSlice channelsList) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(pkg);
                    if (channelsList != null) {
                        _data.writeInt(1);
                        channelsList.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(25, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().createNotificationChannels(pkg, channelsList);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void createNotificationChannelsForPackage(String pkg, int uid, ParceledListSlice channelsList) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(pkg);
                    _data.writeInt(uid);
                    if (channelsList != null) {
                        _data.writeInt(1);
                        channelsList.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(26, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().createNotificationChannelsForPackage(pkg, uid, channelsList);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public ParceledListSlice getNotificationChannelGroupsForPackage(String pkg, int uid, boolean includeDeleted) throws RemoteException {
                ParceledListSlice _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(pkg);
                    _data.writeInt(uid);
                    _data.writeInt(includeDeleted);
                    if (!this.mRemote.transact(27, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getNotificationChannelGroupsForPackage(pkg, uid, includeDeleted);
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

            public NotificationChannelGroup getNotificationChannelGroupForPackage(String groupId, String pkg, int uid) throws RemoteException {
                NotificationChannelGroup _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(groupId);
                    _data.writeString(pkg);
                    _data.writeInt(uid);
                    if (!this.mRemote.transact(28, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getNotificationChannelGroupForPackage(groupId, pkg, uid);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = NotificationChannelGroup.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    NotificationChannelGroup _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public NotificationChannelGroup getPopulatedNotificationChannelGroupForPackage(String pkg, int uid, String groupId, boolean includeDeleted) throws RemoteException {
                NotificationChannelGroup _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(pkg);
                    _data.writeInt(uid);
                    _data.writeString(groupId);
                    _data.writeInt(includeDeleted);
                    if (!this.mRemote.transact(29, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPopulatedNotificationChannelGroupForPackage(pkg, uid, groupId, includeDeleted);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = NotificationChannelGroup.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    NotificationChannelGroup _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void updateNotificationChannelGroupForPackage(String pkg, int uid, NotificationChannelGroup group) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(pkg);
                    _data.writeInt(uid);
                    if (group != null) {
                        _data.writeInt(1);
                        group.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(30, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().updateNotificationChannelGroupForPackage(pkg, uid, group);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void updateNotificationChannelForPackage(String pkg, int uid, NotificationChannel channel) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(pkg);
                    _data.writeInt(uid);
                    if (channel != null) {
                        _data.writeInt(1);
                        channel.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(31, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().updateNotificationChannelForPackage(pkg, uid, channel);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public NotificationChannel getNotificationChannel(String callingPkg, int userId, String pkg, String channelId) throws RemoteException {
                NotificationChannel _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPkg);
                    _data.writeInt(userId);
                    _data.writeString(pkg);
                    _data.writeString(channelId);
                    if (!this.mRemote.transact(32, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getNotificationChannel(callingPkg, userId, pkg, channelId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = NotificationChannel.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    NotificationChannel _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public NotificationChannel getNotificationChannelForPackage(String pkg, int uid, String channelId, boolean includeDeleted) throws RemoteException {
                NotificationChannel _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(pkg);
                    _data.writeInt(uid);
                    _data.writeString(channelId);
                    _data.writeInt(includeDeleted);
                    if (!this.mRemote.transact(33, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getNotificationChannelForPackage(pkg, uid, channelId, includeDeleted);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = NotificationChannel.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    NotificationChannel _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void deleteNotificationChannel(String pkg, String channelId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(pkg);
                    _data.writeString(channelId);
                    if (this.mRemote.transact(34, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().deleteNotificationChannel(pkg, channelId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public ParceledListSlice getNotificationChannels(String callingPkg, String targetPkg, int userId) throws RemoteException {
                ParceledListSlice _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPkg);
                    _data.writeString(targetPkg);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(35, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getNotificationChannels(callingPkg, targetPkg, userId);
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

            public ParceledListSlice getNotificationChannelsForPackage(String pkg, int uid, boolean includeDeleted) throws RemoteException {
                ParceledListSlice _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(pkg);
                    _data.writeInt(uid);
                    _data.writeInt(includeDeleted);
                    if (!this.mRemote.transact(36, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getNotificationChannelsForPackage(pkg, uid, includeDeleted);
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

            public int getNumNotificationChannelsForPackage(String pkg, int uid, boolean includeDeleted) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(pkg);
                    _data.writeInt(uid);
                    _data.writeInt(includeDeleted);
                    if (!this.mRemote.transact(37, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getNumNotificationChannelsForPackage(pkg, uid, includeDeleted);
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

            public int getDeletedChannelCount(String pkg, int uid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(pkg);
                    _data.writeInt(uid);
                    if (!this.mRemote.transact(38, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getDeletedChannelCount(pkg, uid);
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

            public int getBlockedChannelCount(String pkg, int uid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(pkg);
                    _data.writeInt(uid);
                    if (!this.mRemote.transact(39, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getBlockedChannelCount(pkg, uid);
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

            public void deleteNotificationChannelGroup(String pkg, String channelGroupId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(pkg);
                    _data.writeString(channelGroupId);
                    if (this.mRemote.transact(40, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().deleteNotificationChannelGroup(pkg, channelGroupId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public NotificationChannelGroup getNotificationChannelGroup(String pkg, String channelGroupId) throws RemoteException {
                NotificationChannelGroup _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(pkg);
                    _data.writeString(channelGroupId);
                    if (!this.mRemote.transact(41, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getNotificationChannelGroup(pkg, channelGroupId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = NotificationChannelGroup.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    NotificationChannelGroup _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public ParceledListSlice getNotificationChannelGroups(String pkg) throws RemoteException {
                ParceledListSlice _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(pkg);
                    if (!this.mRemote.transact(42, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getNotificationChannelGroups(pkg);
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

            public boolean onlyHasDefaultChannel(String pkg, int uid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(pkg);
                    _data.writeInt(uid);
                    boolean z = false;
                    if (!this.mRemote.transact(43, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().onlyHasDefaultChannel(pkg, uid);
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

            public int getBlockedAppCount(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(44, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getBlockedAppCount(userId);
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

            public boolean areChannelsBypassingDnd() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(45, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().areChannelsBypassingDnd();
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

            public int getAppsBypassingDndCount(int uid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    if (!this.mRemote.transact(46, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAppsBypassingDndCount(uid);
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

            public ParceledListSlice getNotificationChannelsBypassingDnd(String pkg, int userId) throws RemoteException {
                ParceledListSlice _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(pkg);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(47, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getNotificationChannelsBypassingDnd(pkg, userId);
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

            public boolean isPackagePaused(String pkg) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(pkg);
                    boolean z = false;
                    if (!this.mRemote.transact(48, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isPackagePaused(pkg);
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

            public StatusBarNotification[] getActiveNotifications(String callingPkg) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPkg);
                    if (!this.mRemote.transact(49, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getActiveNotifications(callingPkg);
                    }
                    _reply.readException();
                    StatusBarNotification[] _result = (StatusBarNotification[]) _reply.createTypedArray(StatusBarNotification.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public StatusBarNotification[] getHistoricalNotifications(String callingPkg, int count) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPkg);
                    _data.writeInt(count);
                    if (!this.mRemote.transact(50, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getHistoricalNotifications(callingPkg, count);
                    }
                    _reply.readException();
                    StatusBarNotification[] _result = (StatusBarNotification[]) _reply.createTypedArray(StatusBarNotification.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void registerListener(INotificationListener listener, ComponentName component, int userid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    if (component != null) {
                        _data.writeInt(1);
                        component.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(userid);
                    if (this.mRemote.transact(51, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().registerListener(listener, component, userid);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void unregisterListener(INotificationListener listener, int userid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    _data.writeInt(userid);
                    if (this.mRemote.transact(52, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().unregisterListener(listener, userid);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void cancelNotificationFromListener(INotificationListener token, String pkg, String tag, int id) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token != null ? token.asBinder() : null);
                    _data.writeString(pkg);
                    _data.writeString(tag);
                    _data.writeInt(id);
                    if (this.mRemote.transact(53, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().cancelNotificationFromListener(token, pkg, tag, id);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void cancelNotificationsFromListener(INotificationListener token, String[] keys) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token != null ? token.asBinder() : null);
                    _data.writeStringArray(keys);
                    if (this.mRemote.transact(54, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().cancelNotificationsFromListener(token, keys);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void snoozeNotificationUntilContextFromListener(INotificationListener token, String key, String snoozeCriterionId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token != null ? token.asBinder() : null);
                    _data.writeString(key);
                    _data.writeString(snoozeCriterionId);
                    if (this.mRemote.transact(55, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().snoozeNotificationUntilContextFromListener(token, key, snoozeCriterionId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void snoozeNotificationUntilFromListener(INotificationListener token, String key, long until) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token != null ? token.asBinder() : null);
                    _data.writeString(key);
                    _data.writeLong(until);
                    if (this.mRemote.transact(56, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().snoozeNotificationUntilFromListener(token, key, until);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void requestBindListener(ComponentName component) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (component != null) {
                        _data.writeInt(1);
                        component.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(57, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().requestBindListener(component);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void requestUnbindListener(INotificationListener token) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token != null ? token.asBinder() : null);
                    if (this.mRemote.transact(58, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().requestUnbindListener(token);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void requestBindProvider(ComponentName component) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (component != null) {
                        _data.writeInt(1);
                        component.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(59, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().requestBindProvider(component);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void requestUnbindProvider(IConditionProvider token) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token != null ? token.asBinder() : null);
                    if (this.mRemote.transact(60, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().requestUnbindProvider(token);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setNotificationsShownFromListener(INotificationListener token, String[] keys) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token != null ? token.asBinder() : null);
                    _data.writeStringArray(keys);
                    if (this.mRemote.transact(61, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setNotificationsShownFromListener(token, keys);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public ParceledListSlice getActiveNotificationsFromListener(INotificationListener token, String[] keys, int trim) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    ParceledListSlice _result = null;
                    _data.writeStrongBinder(token != null ? token.asBinder() : null);
                    _data.writeStringArray(keys);
                    _data.writeInt(trim);
                    if (!this.mRemote.transact(62, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getActiveNotificationsFromListener(token, keys, trim);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = ParceledListSlice.CREATOR.createFromParcel(_reply);
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public ParceledListSlice getSnoozedNotificationsFromListener(INotificationListener token, int trim) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    ParceledListSlice _result = null;
                    _data.writeStrongBinder(token != null ? token.asBinder() : null);
                    _data.writeInt(trim);
                    if (!this.mRemote.transact(63, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getSnoozedNotificationsFromListener(token, trim);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = ParceledListSlice.CREATOR.createFromParcel(_reply);
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void clearRequestedListenerHints(INotificationListener token) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token != null ? token.asBinder() : null);
                    if (this.mRemote.transact(64, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().clearRequestedListenerHints(token);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void requestHintsFromListener(INotificationListener token, int hints) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token != null ? token.asBinder() : null);
                    _data.writeInt(hints);
                    if (this.mRemote.transact(65, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().requestHintsFromListener(token, hints);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getHintsFromListener(INotificationListener token) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token != null ? token.asBinder() : null);
                    if (!this.mRemote.transact(66, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getHintsFromListener(token);
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

            public void requestInterruptionFilterFromListener(INotificationListener token, int interruptionFilter) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token != null ? token.asBinder() : null);
                    _data.writeInt(interruptionFilter);
                    if (this.mRemote.transact(67, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().requestInterruptionFilterFromListener(token, interruptionFilter);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getInterruptionFilterFromListener(INotificationListener token) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token != null ? token.asBinder() : null);
                    if (!this.mRemote.transact(68, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getInterruptionFilterFromListener(token);
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

            public void setOnNotificationPostedTrimFromListener(INotificationListener token, int trim) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token != null ? token.asBinder() : null);
                    _data.writeInt(trim);
                    if (this.mRemote.transact(69, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setOnNotificationPostedTrimFromListener(token, trim);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setInterruptionFilter(String pkg, int interruptionFilter) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(pkg);
                    _data.writeInt(interruptionFilter);
                    if (this.mRemote.transact(70, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setInterruptionFilter(pkg, interruptionFilter);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void updateNotificationChannelGroupFromPrivilegedListener(INotificationListener token, String pkg, UserHandle user, NotificationChannelGroup group) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token != null ? token.asBinder() : null);
                    _data.writeString(pkg);
                    if (user != null) {
                        _data.writeInt(1);
                        user.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (group != null) {
                        _data.writeInt(1);
                        group.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(71, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().updateNotificationChannelGroupFromPrivilegedListener(token, pkg, user, group);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void updateNotificationChannelFromPrivilegedListener(INotificationListener token, String pkg, UserHandle user, NotificationChannel channel) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token != null ? token.asBinder() : null);
                    _data.writeString(pkg);
                    if (user != null) {
                        _data.writeInt(1);
                        user.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (channel != null) {
                        _data.writeInt(1);
                        channel.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(72, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().updateNotificationChannelFromPrivilegedListener(token, pkg, user, channel);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public ParceledListSlice getNotificationChannelsFromPrivilegedListener(INotificationListener token, String pkg, UserHandle user) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    ParceledListSlice _result = null;
                    _data.writeStrongBinder(token != null ? token.asBinder() : null);
                    _data.writeString(pkg);
                    if (user != null) {
                        _data.writeInt(1);
                        user.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(73, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getNotificationChannelsFromPrivilegedListener(token, pkg, user);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = ParceledListSlice.CREATOR.createFromParcel(_reply);
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public ParceledListSlice getNotificationChannelGroupsFromPrivilegedListener(INotificationListener token, String pkg, UserHandle user) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    ParceledListSlice _result = null;
                    _data.writeStrongBinder(token != null ? token.asBinder() : null);
                    _data.writeString(pkg);
                    if (user != null) {
                        _data.writeInt(1);
                        user.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(74, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getNotificationChannelGroupsFromPrivilegedListener(token, pkg, user);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = ParceledListSlice.CREATOR.createFromParcel(_reply);
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void applyEnqueuedAdjustmentFromAssistant(INotificationListener token, Adjustment adjustment) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token != null ? token.asBinder() : null);
                    if (adjustment != null) {
                        _data.writeInt(1);
                        adjustment.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(75, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().applyEnqueuedAdjustmentFromAssistant(token, adjustment);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void applyAdjustmentFromAssistant(INotificationListener token, Adjustment adjustment) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token != null ? token.asBinder() : null);
                    if (adjustment != null) {
                        _data.writeInt(1);
                        adjustment.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(76, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().applyAdjustmentFromAssistant(token, adjustment);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void applyAdjustmentsFromAssistant(INotificationListener token, List<Adjustment> adjustments) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token != null ? token.asBinder() : null);
                    _data.writeTypedList(adjustments);
                    if (this.mRemote.transact(77, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().applyAdjustmentsFromAssistant(token, adjustments);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void unsnoozeNotificationFromAssistant(INotificationListener token, String key) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token != null ? token.asBinder() : null);
                    _data.writeString(key);
                    if (this.mRemote.transact(78, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().unsnoozeNotificationFromAssistant(token, key);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public ComponentName getEffectsSuppressor() throws RemoteException {
                ComponentName _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(79, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getEffectsSuppressor();
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

            public boolean matchesCallFilter(Bundle extras) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (extras != null) {
                        _data.writeInt(1);
                        extras.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(80, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().matchesCallFilter(extras);
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

            public boolean isSystemConditionProviderEnabled(String path) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(path);
                    boolean z = false;
                    if (!this.mRemote.transact(81, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isSystemConditionProviderEnabled(path);
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

            public boolean isNotificationListenerAccessGranted(ComponentName listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (listener != null) {
                        _data.writeInt(1);
                        listener.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(82, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isNotificationListenerAccessGranted(listener);
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

            public boolean isNotificationListenerAccessGrantedForUser(ComponentName listener, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (listener != null) {
                        _data.writeInt(1);
                        listener.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(83, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isNotificationListenerAccessGrantedForUser(listener, userId);
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

            public boolean isNotificationAssistantAccessGranted(ComponentName assistant) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (assistant != null) {
                        _data.writeInt(1);
                        assistant.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(84, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isNotificationAssistantAccessGranted(assistant);
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

            public void setNotificationListenerAccessGranted(ComponentName listener, boolean enabled) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (listener != null) {
                        _data.writeInt(1);
                        listener.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(enabled);
                    if (this.mRemote.transact(85, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setNotificationListenerAccessGranted(listener, enabled);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setNotificationAssistantAccessGranted(ComponentName assistant, boolean enabled) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (assistant != null) {
                        _data.writeInt(1);
                        assistant.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(enabled);
                    if (this.mRemote.transact(86, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setNotificationAssistantAccessGranted(assistant, enabled);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setNotificationListenerAccessGrantedForUser(ComponentName listener, int userId, boolean enabled) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (listener != null) {
                        _data.writeInt(1);
                        listener.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(userId);
                    _data.writeInt(enabled);
                    if (this.mRemote.transact(87, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setNotificationListenerAccessGrantedForUser(listener, userId, enabled);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setNotificationAssistantAccessGrantedForUser(ComponentName assistant, int userId, boolean enabled) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (assistant != null) {
                        _data.writeInt(1);
                        assistant.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(userId);
                    _data.writeInt(enabled);
                    if (this.mRemote.transact(88, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setNotificationAssistantAccessGrantedForUser(assistant, userId, enabled);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public List<String> getEnabledNotificationListenerPackages() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(89, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getEnabledNotificationListenerPackages();
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

            public List<ComponentName> getEnabledNotificationListeners(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(90, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getEnabledNotificationListeners(userId);
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

            public ComponentName getAllowedNotificationAssistantForUser(int userId) throws RemoteException {
                ComponentName _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(91, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAllowedNotificationAssistantForUser(userId);
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

            public ComponentName getAllowedNotificationAssistant() throws RemoteException {
                ComponentName _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(92, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAllowedNotificationAssistant();
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

            public int getZenMode() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(93, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getZenMode();
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

            public ZenModeConfig getZenModeConfig() throws RemoteException {
                ZenModeConfig _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(94, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getZenModeConfig();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = ZenModeConfig.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    ZenModeConfig _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public NotificationManager.Policy getConsolidatedNotificationPolicy() throws RemoteException {
                NotificationManager.Policy _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(95, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getConsolidatedNotificationPolicy();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = NotificationManager.Policy.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    NotificationManager.Policy _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setZenMode(int mode, Uri conditionId, String reason) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(mode);
                    if (conditionId != null) {
                        _data.writeInt(1);
                        conditionId.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(reason);
                    if (this.mRemote.transact(96, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setZenMode(mode, conditionId, reason);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void notifyConditions(String pkg, IConditionProvider provider, Condition[] conditions) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(pkg);
                    _data.writeStrongBinder(provider != null ? provider.asBinder() : null);
                    _data.writeTypedArray(conditions, 0);
                    if (this.mRemote.transact(97, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().notifyConditions(pkg, provider, conditions);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public boolean isNotificationPolicyAccessGranted(String pkg) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(pkg);
                    boolean z = false;
                    if (!this.mRemote.transact(98, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isNotificationPolicyAccessGranted(pkg);
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

            public NotificationManager.Policy getNotificationPolicy(String pkg) throws RemoteException {
                NotificationManager.Policy _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(pkg);
                    if (!this.mRemote.transact(99, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getNotificationPolicy(pkg);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = NotificationManager.Policy.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    NotificationManager.Policy _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setNotificationPolicy(String pkg, NotificationManager.Policy policy) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(pkg);
                    if (policy != null) {
                        _data.writeInt(1);
                        policy.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(100, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setNotificationPolicy(pkg, policy);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isNotificationPolicyAccessGrantedForPackage(String pkg) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(pkg);
                    boolean z = false;
                    if (!this.mRemote.transact(101, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isNotificationPolicyAccessGrantedForPackage(pkg);
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

            public void setNotificationPolicyAccessGranted(String pkg, boolean granted) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(pkg);
                    _data.writeInt(granted);
                    if (this.mRemote.transact(102, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setNotificationPolicyAccessGranted(pkg, granted);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setNotificationPolicyAccessGrantedForUser(String pkg, int userId, boolean granted) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(pkg);
                    _data.writeInt(userId);
                    _data.writeInt(granted);
                    if (this.mRemote.transact(103, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setNotificationPolicyAccessGrantedForUser(pkg, userId, granted);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public AutomaticZenRule getAutomaticZenRule(String id) throws RemoteException {
                AutomaticZenRule _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(id);
                    if (!this.mRemote.transact(104, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAutomaticZenRule(id);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = AutomaticZenRule.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    AutomaticZenRule _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public List<ZenModeConfig.ZenRule> getZenRules() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(105, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getZenRules();
                    }
                    _reply.readException();
                    List<ZenModeConfig.ZenRule> _result = _reply.createTypedArrayList(ZenModeConfig.ZenRule.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String addAutomaticZenRule(AutomaticZenRule automaticZenRule) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (automaticZenRule != null) {
                        _data.writeInt(1);
                        automaticZenRule.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(106, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().addAutomaticZenRule(automaticZenRule);
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

            public boolean updateAutomaticZenRule(String id, AutomaticZenRule automaticZenRule) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(id);
                    boolean _result = true;
                    if (automaticZenRule != null) {
                        _data.writeInt(1);
                        automaticZenRule.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(107, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().updateAutomaticZenRule(id, automaticZenRule);
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

            public boolean removeAutomaticZenRule(String id) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(id);
                    boolean z = false;
                    if (!this.mRemote.transact(108, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().removeAutomaticZenRule(id);
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

            public boolean removeAutomaticZenRules(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    boolean z = false;
                    if (!this.mRemote.transact(109, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().removeAutomaticZenRules(packageName);
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

            public int getRuleInstanceCount(ComponentName owner) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (owner != null) {
                        _data.writeInt(1);
                        owner.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(110, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getRuleInstanceCount(owner);
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

            public void setAutomaticZenRuleState(String id, Condition condition) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(id);
                    if (condition != null) {
                        _data.writeInt(1);
                        condition.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(111, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setAutomaticZenRuleState(id, condition);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public byte[] getBackupPayload(int user) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(user);
                    if (!this.mRemote.transact(112, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getBackupPayload(user);
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

            public void applyRestore(byte[] payload, int user) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByteArray(payload);
                    _data.writeInt(user);
                    if (this.mRemote.transact(113, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().applyRestore(payload, user);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public ParceledListSlice getAppActiveNotifications(String callingPkg, int userId) throws RemoteException {
                ParceledListSlice _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPkg);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(114, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAppActiveNotifications(callingPkg, userId);
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

            public void setNotificationDelegate(String callingPkg, String delegate) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPkg);
                    _data.writeString(delegate);
                    if (this.mRemote.transact(115, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setNotificationDelegate(callingPkg, delegate);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String getNotificationDelegate(String callingPkg) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPkg);
                    if (!this.mRemote.transact(116, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getNotificationDelegate(callingPkg);
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

            public boolean canNotifyAsPackage(String callingPkg, String targetPkg, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPkg);
                    _data.writeString(targetPkg);
                    _data.writeInt(userId);
                    boolean z = false;
                    if (!this.mRemote.transact(117, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().canNotifyAsPackage(callingPkg, targetPkg, userId);
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

            public void setPrivateNotificationsAllowed(boolean allow) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(allow);
                    if (this.mRemote.transact(118, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setPrivateNotificationsAllowed(allow);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean getPrivateNotificationsAllowed() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(119, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPrivateNotificationsAllowed();
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
        }

        public static boolean setDefaultImpl(INotificationManager impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static INotificationManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
