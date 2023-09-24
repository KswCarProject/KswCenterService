package android.app;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.app.INotificationManager;
import android.app.Notification;
import android.content.ComponentName;
import android.content.Context;
import android.content.p002pm.ParceledListSlice;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.p007os.Bundle;
import android.p007os.Handler;
import android.p007os.IBinder;
import android.p007os.Parcel;
import android.p007os.Parcelable;
import android.p007os.RemoteException;
import android.p007os.ServiceManager;
import android.p007os.StrictMode;
import android.p007os.UserHandle;
import android.service.notification.Condition;
import android.service.notification.StatusBarNotification;
import android.service.notification.ZenModeConfig;
import android.util.Log;
import android.util.proto.ProtoOutputStream;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/* loaded from: classes.dex */
public class NotificationManager {
    public static final String ACTION_APP_BLOCK_STATE_CHANGED = "android.app.action.APP_BLOCK_STATE_CHANGED";
    public static final String ACTION_AUTOMATIC_ZEN_RULE = "android.app.action.AUTOMATIC_ZEN_RULE";
    public static final String ACTION_EFFECTS_SUPPRESSOR_CHANGED = "android.os.action.ACTION_EFFECTS_SUPPRESSOR_CHANGED";
    public static final String ACTION_INTERRUPTION_FILTER_CHANGED = "android.app.action.INTERRUPTION_FILTER_CHANGED";
    public static final String ACTION_INTERRUPTION_FILTER_CHANGED_INTERNAL = "android.app.action.INTERRUPTION_FILTER_CHANGED_INTERNAL";
    public static final String ACTION_NOTIFICATION_CHANNEL_BLOCK_STATE_CHANGED = "android.app.action.NOTIFICATION_CHANNEL_BLOCK_STATE_CHANGED";
    public static final String ACTION_NOTIFICATION_CHANNEL_GROUP_BLOCK_STATE_CHANGED = "android.app.action.NOTIFICATION_CHANNEL_GROUP_BLOCK_STATE_CHANGED";
    public static final String ACTION_NOTIFICATION_POLICY_ACCESS_GRANTED_CHANGED = "android.app.action.NOTIFICATION_POLICY_ACCESS_GRANTED_CHANGED";
    public static final String ACTION_NOTIFICATION_POLICY_CHANGED = "android.app.action.NOTIFICATION_POLICY_CHANGED";
    public static final String EXTRA_AUTOMATIC_RULE_ID = "android.app.extra.AUTOMATIC_RULE_ID";
    public static final String EXTRA_BLOCKED_STATE = "android.app.extra.BLOCKED_STATE";
    public static final String EXTRA_NOTIFICATION_CHANNEL_GROUP_ID = "android.app.extra.NOTIFICATION_CHANNEL_GROUP_ID";
    public static final String EXTRA_NOTIFICATION_CHANNEL_ID = "android.app.extra.NOTIFICATION_CHANNEL_ID";
    public static final int IMPORTANCE_DEFAULT = 3;
    public static final int IMPORTANCE_HIGH = 4;
    public static final int IMPORTANCE_LOW = 2;
    public static final int IMPORTANCE_MAX = 5;
    public static final int IMPORTANCE_MIN = 1;
    public static final int IMPORTANCE_NONE = 0;
    public static final int IMPORTANCE_UNSPECIFIED = -1000;
    public static final int INTERRUPTION_FILTER_ALARMS = 4;
    public static final int INTERRUPTION_FILTER_ALL = 1;
    public static final int INTERRUPTION_FILTER_NONE = 3;
    public static final int INTERRUPTION_FILTER_PRIORITY = 2;
    public static final int INTERRUPTION_FILTER_UNKNOWN = 0;
    public static final String META_DATA_AUTOMATIC_RULE_TYPE = "android.service.zen.automatic.ruleType";
    public static final String META_DATA_RULE_INSTANCE_LIMIT = "android.service.zen.automatic.ruleInstanceLimit";
    public static final int VISIBILITY_NO_OVERRIDE = -1000;
    @UnsupportedAppUsage
    private static INotificationManager sService;
    private Context mContext;
    private static String TAG = "NotificationManager";
    private static boolean localLOGV = false;

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes.dex */
    public @interface Importance {
    }

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes.dex */
    public @interface InterruptionFilter {
    }

    @UnsupportedAppUsage
    public static INotificationManager getService() {
        if (sService != null) {
            return sService;
        }
        IBinder b = ServiceManager.getService("notification");
        sService = INotificationManager.Stub.asInterface(b);
        return sService;
    }

    @UnsupportedAppUsage
    NotificationManager(Context context, Handler handler) {
        this.mContext = context;
    }

    @UnsupportedAppUsage
    public static NotificationManager from(Context context) {
        return (NotificationManager) context.getSystemService("notification");
    }

    public void notify(int id, Notification notification) {
        notify(null, id, notification);
    }

    public void notify(String tag, int id, Notification notification) {
        notifyAsUser(tag, id, notification, this.mContext.getUser());
    }

    public void notifyAsPackage(String targetPackage, String tag, int id, Notification notification) {
        INotificationManager service = getService();
        String sender = this.mContext.getPackageName();
        try {
            if (localLOGV) {
                String str = TAG;
                Log.m66v(str, sender + ": notify(" + id + ", " + notification + ")");
            }
            service.enqueueNotificationWithTag(targetPackage, sender, tag, id, fixNotification(notification), this.mContext.getUser().getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    public void notifyAsUser(String tag, int id, Notification notification, UserHandle user) {
        INotificationManager service = getService();
        String pkg = this.mContext.getPackageName();
        try {
            if (localLOGV) {
                String str = TAG;
                Log.m66v(str, pkg + ": notify(" + id + ", " + notification + ")");
            }
            service.enqueueNotificationWithTag(pkg, this.mContext.getOpPackageName(), tag, id, fixNotification(notification), user.getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private Notification fixNotification(Notification notification) {
        String pkg = this.mContext.getPackageName();
        Notification.addFieldsFromContext(this.mContext, notification);
        if (notification.sound != null) {
            notification.sound = notification.sound.getCanonicalUri();
            if (StrictMode.vmFileUriExposureEnabled()) {
                notification.sound.checkFileUriExposed("Notification.sound");
            }
        }
        fixLegacySmallIcon(notification, pkg);
        if (this.mContext.getApplicationInfo().targetSdkVersion > 22 && notification.getSmallIcon() == null) {
            throw new IllegalArgumentException("Invalid notification (no valid small icon): " + notification);
        }
        notification.reduceImageSizes(this.mContext);
        ActivityManager am = (ActivityManager) this.mContext.getSystemService(Context.ACTIVITY_SERVICE);
        boolean isLowRam = am.isLowRamDevice();
        return Notification.Builder.maybeCloneStrippedForDelivery(notification, isLowRam, this.mContext);
    }

    private void fixLegacySmallIcon(Notification n, String pkg) {
        if (n.getSmallIcon() == null && n.icon != 0) {
            n.setSmallIcon(Icon.createWithResource(pkg, n.icon));
        }
    }

    public void cancel(int id) {
        cancel(null, id);
    }

    public void cancel(String tag, int id) {
        cancelAsUser(tag, id, this.mContext.getUser());
    }

    @UnsupportedAppUsage
    public void cancelAsUser(String tag, int id, UserHandle user) {
        INotificationManager service = getService();
        String pkg = this.mContext.getPackageName();
        if (localLOGV) {
            String str = TAG;
            Log.m66v(str, pkg + ": cancel(" + id + ")");
        }
        try {
            service.cancelNotificationWithTag(pkg, tag, id, user.getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void cancelAll() {
        INotificationManager service = getService();
        String pkg = this.mContext.getPackageName();
        if (localLOGV) {
            String str = TAG;
            Log.m66v(str, pkg + ": cancelAll()");
        }
        try {
            service.cancelAllNotifications(pkg, this.mContext.getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setNotificationDelegate(String delegate) {
        INotificationManager service = getService();
        String pkg = this.mContext.getPackageName();
        if (localLOGV) {
            String str = TAG;
            Log.m66v(str, pkg + ": cancelAll()");
        }
        try {
            service.setNotificationDelegate(pkg, delegate);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String getNotificationDelegate() {
        INotificationManager service = getService();
        String pkg = this.mContext.getPackageName();
        try {
            return service.getNotificationDelegate(pkg);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean canNotifyAsPackage(String pkg) {
        INotificationManager service = getService();
        try {
            return service.canNotifyAsPackage(this.mContext.getPackageName(), pkg, this.mContext.getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void createNotificationChannelGroup(NotificationChannelGroup group) {
        createNotificationChannelGroups(Arrays.asList(group));
    }

    public void createNotificationChannelGroups(List<NotificationChannelGroup> groups) {
        INotificationManager service = getService();
        try {
            service.createNotificationChannelGroups(this.mContext.getPackageName(), new ParceledListSlice(groups));
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void createNotificationChannel(NotificationChannel channel) {
        createNotificationChannels(Arrays.asList(channel));
    }

    public void createNotificationChannels(List<NotificationChannel> channels) {
        INotificationManager service = getService();
        try {
            service.createNotificationChannels(this.mContext.getPackageName(), new ParceledListSlice(channels));
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public NotificationChannel getNotificationChannel(String channelId) {
        INotificationManager service = getService();
        try {
            return service.getNotificationChannel(this.mContext.getOpPackageName(), this.mContext.getUserId(), this.mContext.getPackageName(), channelId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<NotificationChannel> getNotificationChannels() {
        INotificationManager service = getService();
        try {
            return service.getNotificationChannels(this.mContext.getOpPackageName(), this.mContext.getPackageName(), this.mContext.getUserId()).getList();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void deleteNotificationChannel(String channelId) {
        INotificationManager service = getService();
        try {
            service.deleteNotificationChannel(this.mContext.getPackageName(), channelId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public NotificationChannelGroup getNotificationChannelGroup(String channelGroupId) {
        INotificationManager service = getService();
        try {
            return service.getNotificationChannelGroup(this.mContext.getPackageName(), channelGroupId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<NotificationChannelGroup> getNotificationChannelGroups() {
        INotificationManager service = getService();
        try {
            ParceledListSlice<NotificationChannelGroup> parceledList = service.getNotificationChannelGroups(this.mContext.getPackageName());
            if (parceledList != null) {
                return parceledList.getList();
            }
            return new ArrayList();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void deleteNotificationChannelGroup(String groupId) {
        INotificationManager service = getService();
        try {
            service.deleteNotificationChannelGroup(this.mContext.getPackageName(), groupId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public ComponentName getEffectsSuppressor() {
        INotificationManager service = getService();
        try {
            return service.getEffectsSuppressor();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean matchesCallFilter(Bundle extras) {
        INotificationManager service = getService();
        try {
            return service.matchesCallFilter(extras);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isSystemConditionProviderEnabled(String path) {
        INotificationManager service = getService();
        try {
            return service.isSystemConditionProviderEnabled(path);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    public void setZenMode(int mode, Uri conditionId, String reason) {
        INotificationManager service = getService();
        try {
            service.setZenMode(mode, conditionId, reason);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getZenMode() {
        INotificationManager service = getService();
        try {
            return service.getZenMode();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    public ZenModeConfig getZenModeConfig() {
        INotificationManager service = getService();
        try {
            return service.getZenModeConfig();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public Policy getConsolidatedNotificationPolicy() {
        INotificationManager service = getService();
        try {
            return service.getConsolidatedNotificationPolicy();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getRuleInstanceCount(ComponentName owner) {
        INotificationManager service = getService();
        try {
            return service.getRuleInstanceCount(owner);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public Map<String, AutomaticZenRule> getAutomaticZenRules() {
        INotificationManager service = getService();
        try {
            List<ZenModeConfig.ZenRule> rules = service.getZenRules();
            Map<String, AutomaticZenRule> ruleMap = new HashMap<>();
            Iterator<ZenModeConfig.ZenRule> it = rules.iterator();
            while (it.hasNext()) {
                ZenModeConfig.ZenRule rule = it.next();
                List<ZenModeConfig.ZenRule> rules2 = rules;
                INotificationManager service2 = service;
                try {
                    Iterator<ZenModeConfig.ZenRule> it2 = it;
                    ruleMap.put(rule.f242id, new AutomaticZenRule(rule.name, rule.component, rule.configurationActivity, rule.conditionId, rule.zenPolicy, zenModeToInterruptionFilter(rule.zenMode), rule.enabled, rule.creationTime));
                    service = service2;
                    rules = rules2;
                    it = it2;
                } catch (RemoteException e) {
                    e = e;
                    throw e.rethrowFromSystemServer();
                }
            }
            return ruleMap;
        } catch (RemoteException e2) {
            e = e2;
        }
    }

    public AutomaticZenRule getAutomaticZenRule(String id) {
        INotificationManager service = getService();
        try {
            return service.getAutomaticZenRule(id);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String addAutomaticZenRule(AutomaticZenRule automaticZenRule) {
        INotificationManager service = getService();
        try {
            return service.addAutomaticZenRule(automaticZenRule);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean updateAutomaticZenRule(String id, AutomaticZenRule automaticZenRule) {
        INotificationManager service = getService();
        try {
            return service.updateAutomaticZenRule(id, automaticZenRule);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setAutomaticZenRuleState(String id, Condition condition) {
        INotificationManager service = getService();
        try {
            service.setAutomaticZenRuleState(id, condition);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean removeAutomaticZenRule(String id) {
        INotificationManager service = getService();
        try {
            return service.removeAutomaticZenRule(id);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean removeAutomaticZenRules(String packageName) {
        INotificationManager service = getService();
        try {
            return service.removeAutomaticZenRules(packageName);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getImportance() {
        INotificationManager service = getService();
        try {
            return service.getPackageImportance(this.mContext.getPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean areNotificationsEnabled() {
        INotificationManager service = getService();
        try {
            return service.areNotificationsEnabled(this.mContext.getPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean areBubblesAllowed() {
        INotificationManager service = getService();
        try {
            return service.areBubblesAllowed(this.mContext.getPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean areNotificationsPaused() {
        INotificationManager service = getService();
        try {
            return service.isPackagePaused(this.mContext.getPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isNotificationPolicyAccessGranted() {
        INotificationManager service = getService();
        try {
            return service.isNotificationPolicyAccessGranted(this.mContext.getOpPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isNotificationListenerAccessGranted(ComponentName listener) {
        INotificationManager service = getService();
        try {
            return service.isNotificationListenerAccessGranted(listener);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean isNotificationAssistantAccessGranted(ComponentName assistant) {
        INotificationManager service = getService();
        try {
            return service.isNotificationAssistantAccessGranted(assistant);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean shouldHideSilentStatusBarIcons() {
        INotificationManager service = getService();
        try {
            return service.shouldHideSilentStatusIcons(this.mContext.getOpPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public List<String> getAllowedAssistantAdjustments() {
        INotificationManager service = getService();
        try {
            return service.getAllowedAssistantAdjustments(this.mContext.getOpPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void allowAssistantAdjustment(String capability) {
        INotificationManager service = getService();
        try {
            service.allowAssistantAdjustment(capability);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void disallowAssistantAdjustment(String capability) {
        INotificationManager service = getService();
        try {
            service.disallowAssistantAdjustment(capability);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isNotificationPolicyAccessGrantedForPackage(String pkg) {
        INotificationManager service = getService();
        try {
            return service.isNotificationPolicyAccessGrantedForPackage(pkg);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<String> getEnabledNotificationListenerPackages() {
        INotificationManager service = getService();
        try {
            return service.getEnabledNotificationListenerPackages();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public Policy getNotificationPolicy() {
        INotificationManager service = getService();
        try {
            return service.getNotificationPolicy(this.mContext.getOpPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setNotificationPolicy(Policy policy) {
        checkRequired("policy", policy);
        INotificationManager service = getService();
        try {
            service.setNotificationPolicy(this.mContext.getOpPackageName(), policy);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setNotificationPolicyAccessGranted(String pkg, boolean granted) {
        INotificationManager service = getService();
        try {
            service.setNotificationPolicyAccessGranted(pkg, granted);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setNotificationListenerAccessGranted(ComponentName listener, boolean granted) {
        INotificationManager service = getService();
        try {
            service.setNotificationListenerAccessGranted(listener, granted);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setNotificationListenerAccessGrantedForUser(ComponentName listener, int userId, boolean granted) {
        INotificationManager service = getService();
        try {
            service.setNotificationListenerAccessGrantedForUser(listener, userId, granted);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void setNotificationAssistantAccessGranted(ComponentName assistant, boolean granted) {
        INotificationManager service = getService();
        try {
            service.setNotificationAssistantAccessGranted(assistant, granted);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<ComponentName> getEnabledNotificationListeners(int userId) {
        INotificationManager service = getService();
        try {
            return service.getEnabledNotificationListeners(userId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public ComponentName getAllowedNotificationAssistant() {
        INotificationManager service = getService();
        try {
            return service.getAllowedNotificationAssistant();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private static void checkRequired(String name, Object value) {
        if (value == null) {
            throw new IllegalArgumentException(name + " is required");
        }
    }

    /* loaded from: classes.dex */
    public static class Policy implements Parcelable {
        public static final int PRIORITY_CATEGORY_ALARMS = 32;
        public static final int PRIORITY_CATEGORY_CALLS = 8;
        public static final int PRIORITY_CATEGORY_EVENTS = 2;
        public static final int PRIORITY_CATEGORY_MEDIA = 64;
        public static final int PRIORITY_CATEGORY_MESSAGES = 4;
        public static final int PRIORITY_CATEGORY_REMINDERS = 1;
        public static final int PRIORITY_CATEGORY_REPEAT_CALLERS = 16;
        public static final int PRIORITY_CATEGORY_SYSTEM = 128;
        public static final int PRIORITY_SENDERS_ANY = 0;
        public static final int PRIORITY_SENDERS_CONTACTS = 1;
        public static final int PRIORITY_SENDERS_STARRED = 2;
        public static final int STATE_CHANNELS_BYPASSING_DND = 1;
        public static final int STATE_UNSET = -1;
        public static final int SUPPRESSED_EFFECTS_UNSET = -1;
        public static final int SUPPRESSED_EFFECT_AMBIENT = 128;
        public static final int SUPPRESSED_EFFECT_BADGE = 64;
        public static final int SUPPRESSED_EFFECT_FULL_SCREEN_INTENT = 4;
        public static final int SUPPRESSED_EFFECT_LIGHTS = 8;
        public static final int SUPPRESSED_EFFECT_NOTIFICATION_LIST = 256;
        public static final int SUPPRESSED_EFFECT_PEEK = 16;
        @Deprecated
        public static final int SUPPRESSED_EFFECT_SCREEN_OFF = 1;
        @Deprecated
        public static final int SUPPRESSED_EFFECT_SCREEN_ON = 2;
        public static final int SUPPRESSED_EFFECT_STATUS_BAR = 32;
        public final int priorityCallSenders;
        public final int priorityCategories;
        public final int priorityMessageSenders;
        public final int state;
        public final int suppressedVisualEffects;
        public static final int[] ALL_PRIORITY_CATEGORIES = {32, 64, 128, 1, 2, 4, 8, 16};
        private static final int[] ALL_SUPPRESSED_EFFECTS = {1, 2, 4, 8, 16, 32, 64, 128, 256};
        private static final int[] SCREEN_OFF_SUPPRESSED_EFFECTS = {1, 4, 8, 128};
        private static final int[] SCREEN_ON_SUPPRESSED_EFFECTS = {2, 16, 32, 64, 256};
        public static final Parcelable.Creator<Policy> CREATOR = new Parcelable.Creator<Policy>() { // from class: android.app.NotificationManager.Policy.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.p007os.Parcelable.Creator
            public Policy createFromParcel(Parcel in) {
                return new Policy(in);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.p007os.Parcelable.Creator
            public Policy[] newArray(int size) {
                return new Policy[size];
            }
        };

        public Policy(int priorityCategories, int priorityCallSenders, int priorityMessageSenders) {
            this(priorityCategories, priorityCallSenders, priorityMessageSenders, -1, -1);
        }

        public Policy(int priorityCategories, int priorityCallSenders, int priorityMessageSenders, int suppressedVisualEffects) {
            this.priorityCategories = priorityCategories;
            this.priorityCallSenders = priorityCallSenders;
            this.priorityMessageSenders = priorityMessageSenders;
            this.suppressedVisualEffects = suppressedVisualEffects;
            this.state = -1;
        }

        public Policy(int priorityCategories, int priorityCallSenders, int priorityMessageSenders, int suppressedVisualEffects, int state) {
            this.priorityCategories = priorityCategories;
            this.priorityCallSenders = priorityCallSenders;
            this.priorityMessageSenders = priorityMessageSenders;
            this.suppressedVisualEffects = suppressedVisualEffects;
            this.state = state;
        }

        public Policy(Parcel source) {
            this(source.readInt(), source.readInt(), source.readInt(), source.readInt(), source.readInt());
        }

        @Override // android.p007os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.priorityCategories);
            dest.writeInt(this.priorityCallSenders);
            dest.writeInt(this.priorityMessageSenders);
            dest.writeInt(this.suppressedVisualEffects);
            dest.writeInt(this.state);
        }

        @Override // android.p007os.Parcelable
        public int describeContents() {
            return 0;
        }

        public int hashCode() {
            return Objects.hash(Integer.valueOf(this.priorityCategories), Integer.valueOf(this.priorityCallSenders), Integer.valueOf(this.priorityMessageSenders), Integer.valueOf(this.suppressedVisualEffects));
        }

        public boolean equals(Object o) {
            if (o instanceof Policy) {
                if (o == this) {
                    return true;
                }
                Policy other = (Policy) o;
                return other.priorityCategories == this.priorityCategories && other.priorityCallSenders == this.priorityCallSenders && other.priorityMessageSenders == this.priorityMessageSenders && suppressedVisualEffectsEqual(this.suppressedVisualEffects, other.suppressedVisualEffects);
            }
            return false;
        }

        private boolean suppressedVisualEffectsEqual(int suppressedEffects, int otherSuppressedVisualEffects) {
            if (suppressedEffects == otherSuppressedVisualEffects) {
                return true;
            }
            if ((suppressedEffects & 2) != 0) {
                suppressedEffects |= 16;
            }
            if ((suppressedEffects & 1) != 0) {
                suppressedEffects = suppressedEffects | 4 | 8 | 128;
            }
            if ((otherSuppressedVisualEffects & 2) != 0) {
                otherSuppressedVisualEffects |= 16;
            }
            if ((otherSuppressedVisualEffects & 1) != 0) {
                otherSuppressedVisualEffects = otherSuppressedVisualEffects | 4 | 8 | 128;
            }
            if ((suppressedEffects & 2) != (otherSuppressedVisualEffects & 2)) {
                int currSuppressedEffects = (suppressedEffects & 2) != 0 ? otherSuppressedVisualEffects : suppressedEffects;
                if ((currSuppressedEffects & 16) == 0) {
                    return false;
                }
            }
            int currSuppressedEffects2 = suppressedEffects & 1;
            if (currSuppressedEffects2 != (otherSuppressedVisualEffects & 1)) {
                int currSuppressedEffects3 = (suppressedEffects & 1) != 0 ? otherSuppressedVisualEffects : suppressedEffects;
                if ((currSuppressedEffects3 & 4) == 0 || (currSuppressedEffects3 & 8) == 0 || (currSuppressedEffects3 & 128) == 0) {
                    return false;
                }
            }
            int currSuppressedEffects4 = suppressedEffects & (-3);
            int thisWithoutOldEffects = currSuppressedEffects4 & (-2);
            int otherWithoutOldEffects = otherSuppressedVisualEffects & (-3) & (-2);
            return thisWithoutOldEffects == otherWithoutOldEffects;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("NotificationManager.Policy[priorityCategories=");
            sb.append(priorityCategoriesToString(this.priorityCategories));
            sb.append(",priorityCallSenders=");
            sb.append(prioritySendersToString(this.priorityCallSenders));
            sb.append(",priorityMessageSenders=");
            sb.append(prioritySendersToString(this.priorityMessageSenders));
            sb.append(",suppressedVisualEffects=");
            sb.append(suppressedEffectsToString(this.suppressedVisualEffects));
            sb.append(",areChannelsBypassingDnd=");
            sb.append((this.state & 1) != 0 ? "true" : "false");
            sb.append("]");
            return sb.toString();
        }

        public void writeToProto(ProtoOutputStream proto, long fieldId) {
            long pToken = proto.start(fieldId);
            bitwiseToProtoEnum(proto, 2259152797697L, this.priorityCategories);
            proto.write(1159641169922L, this.priorityCallSenders);
            proto.write(1159641169923L, this.priorityMessageSenders);
            bitwiseToProtoEnum(proto, 2259152797700L, this.suppressedVisualEffects);
            proto.end(pToken);
        }

        private static void bitwiseToProtoEnum(ProtoOutputStream proto, long fieldId, int data) {
            int data2 = 1;
            for (int data3 = data; data3 > 0; data3 >>>= 1) {
                if ((data3 & 1) == 1) {
                    proto.write(fieldId, data2);
                }
                data2++;
            }
        }

        public static int getAllSuppressedVisualEffects() {
            int effects = 0;
            for (int i = 0; i < ALL_SUPPRESSED_EFFECTS.length; i++) {
                effects |= ALL_SUPPRESSED_EFFECTS[i];
            }
            return effects;
        }

        public static boolean areAllVisualEffectsSuppressed(int effects) {
            for (int i = 0; i < ALL_SUPPRESSED_EFFECTS.length; i++) {
                int effect = ALL_SUPPRESSED_EFFECTS[i];
                if ((effects & effect) == 0) {
                    return false;
                }
            }
            return true;
        }

        private static int toggleEffects(int currentEffects, int[] effects, boolean suppress) {
            for (int effect : effects) {
                if (suppress) {
                    currentEffects |= effect;
                } else {
                    currentEffects &= ~effect;
                }
            }
            return currentEffects;
        }

        public static String suppressedEffectsToString(int effects) {
            if (effects <= 0) {
                return "";
            }
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < ALL_SUPPRESSED_EFFECTS.length; i++) {
                int effect = ALL_SUPPRESSED_EFFECTS[i];
                if ((effects & effect) != 0) {
                    if (sb.length() > 0) {
                        sb.append(',');
                    }
                    sb.append(effectToString(effect));
                }
                effects &= ~effect;
            }
            if (effects != 0) {
                if (sb.length() > 0) {
                    sb.append(',');
                }
                sb.append("UNKNOWN_");
                sb.append(effects);
            }
            return sb.toString();
        }

        public static String priorityCategoriesToString(int priorityCategories) {
            if (priorityCategories == 0) {
                return "";
            }
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < ALL_PRIORITY_CATEGORIES.length; i++) {
                int priorityCategory = ALL_PRIORITY_CATEGORIES[i];
                if ((priorityCategories & priorityCategory) != 0) {
                    if (sb.length() > 0) {
                        sb.append(',');
                    }
                    sb.append(priorityCategoryToString(priorityCategory));
                }
                priorityCategories &= ~priorityCategory;
            }
            if (priorityCategories != 0) {
                if (sb.length() > 0) {
                    sb.append(',');
                }
                sb.append("PRIORITY_CATEGORY_UNKNOWN_");
                sb.append(priorityCategories);
            }
            return sb.toString();
        }

        private static String effectToString(int effect) {
            if (effect != -1) {
                if (effect != 4) {
                    if (effect != 8) {
                        if (effect != 16) {
                            if (effect != 32) {
                                if (effect != 64) {
                                    if (effect != 128) {
                                        if (effect == 256) {
                                            return "SUPPRESSED_EFFECT_NOTIFICATION_LIST";
                                        }
                                        switch (effect) {
                                            case 1:
                                                return "SUPPRESSED_EFFECT_SCREEN_OFF";
                                            case 2:
                                                return "SUPPRESSED_EFFECT_SCREEN_ON";
                                            default:
                                                return "UNKNOWN_" + effect;
                                        }
                                    }
                                    return "SUPPRESSED_EFFECT_AMBIENT";
                                }
                                return "SUPPRESSED_EFFECT_BADGE";
                            }
                            return "SUPPRESSED_EFFECT_STATUS_BAR";
                        }
                        return "SUPPRESSED_EFFECT_PEEK";
                    }
                    return "SUPPRESSED_EFFECT_LIGHTS";
                }
                return "SUPPRESSED_EFFECT_FULL_SCREEN_INTENT";
            }
            return "SUPPRESSED_EFFECTS_UNSET";
        }

        private static String priorityCategoryToString(int priorityCategory) {
            if (priorityCategory != 4) {
                if (priorityCategory != 8) {
                    if (priorityCategory != 16) {
                        if (priorityCategory != 32) {
                            if (priorityCategory != 64) {
                                if (priorityCategory != 128) {
                                    switch (priorityCategory) {
                                        case 1:
                                            return "PRIORITY_CATEGORY_REMINDERS";
                                        case 2:
                                            return "PRIORITY_CATEGORY_EVENTS";
                                        default:
                                            return "PRIORITY_CATEGORY_UNKNOWN_" + priorityCategory;
                                    }
                                }
                                return "PRIORITY_CATEGORY_SYSTEM";
                            }
                            return "PRIORITY_CATEGORY_MEDIA";
                        }
                        return "PRIORITY_CATEGORY_ALARMS";
                    }
                    return "PRIORITY_CATEGORY_REPEAT_CALLERS";
                }
                return "PRIORITY_CATEGORY_CALLS";
            }
            return "PRIORITY_CATEGORY_MESSAGES";
        }

        public static String prioritySendersToString(int prioritySenders) {
            switch (prioritySenders) {
                case 0:
                    return "PRIORITY_SENDERS_ANY";
                case 1:
                    return "PRIORITY_SENDERS_CONTACTS";
                case 2:
                    return "PRIORITY_SENDERS_STARRED";
                default:
                    return "PRIORITY_SENDERS_UNKNOWN_" + prioritySenders;
            }
        }

        public boolean allowAlarms() {
            return (this.priorityCategories & 32) != 0;
        }

        public boolean allowMedia() {
            return (this.priorityCategories & 64) != 0;
        }

        public boolean allowSystem() {
            return (this.priorityCategories & 128) != 0;
        }

        public boolean allowRepeatCallers() {
            return (this.priorityCategories & 16) != 0;
        }

        public boolean allowCalls() {
            return (this.priorityCategories & 8) != 0;
        }

        public boolean allowMessages() {
            return (this.priorityCategories & 4) != 0;
        }

        public boolean allowEvents() {
            return (this.priorityCategories & 2) != 0;
        }

        public boolean allowReminders() {
            return (this.priorityCategories & 1) != 0;
        }

        public int allowCallsFrom() {
            return this.priorityCallSenders;
        }

        public int allowMessagesFrom() {
            return this.priorityMessageSenders;
        }

        public boolean showFullScreenIntents() {
            return (this.suppressedVisualEffects & 4) == 0;
        }

        public boolean showLights() {
            return (this.suppressedVisualEffects & 8) == 0;
        }

        public boolean showPeeking() {
            return (this.suppressedVisualEffects & 16) == 0;
        }

        public boolean showStatusBarIcons() {
            return (this.suppressedVisualEffects & 32) == 0;
        }

        public boolean showAmbient() {
            return (this.suppressedVisualEffects & 128) == 0;
        }

        public boolean showBadges() {
            return (this.suppressedVisualEffects & 64) == 0;
        }

        public boolean showInNotificationList() {
            return (this.suppressedVisualEffects & 256) == 0;
        }

        public Policy copy() {
            Parcel parcel = Parcel.obtain();
            try {
                writeToParcel(parcel, 0);
                parcel.setDataPosition(0);
                return new Policy(parcel);
            } finally {
                parcel.recycle();
            }
        }
    }

    public StatusBarNotification[] getActiveNotifications() {
        INotificationManager service = getService();
        String pkg = this.mContext.getPackageName();
        try {
            ParceledListSlice<StatusBarNotification> parceledList = service.getAppActiveNotifications(pkg, this.mContext.getUserId());
            if (parceledList != null) {
                List<StatusBarNotification> list = parceledList.getList();
                return (StatusBarNotification[]) list.toArray(new StatusBarNotification[list.size()]);
            }
            return new StatusBarNotification[0];
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public final int getCurrentInterruptionFilter() {
        INotificationManager service = getService();
        try {
            return zenModeToInterruptionFilter(service.getZenMode());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public final void setInterruptionFilter(int interruptionFilter) {
        INotificationManager service = getService();
        try {
            service.setInterruptionFilter(this.mContext.getOpPackageName(), interruptionFilter);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static int zenModeToInterruptionFilter(int zen) {
        switch (zen) {
            case 0:
                return 1;
            case 1:
                return 2;
            case 2:
                return 3;
            case 3:
                return 4;
            default:
                return 0;
        }
    }

    public static int zenModeFromInterruptionFilter(int interruptionFilter, int defValue) {
        switch (interruptionFilter) {
            case 1:
                return 0;
            case 2:
                return 1;
            case 3:
                return 2;
            case 4:
                return 3;
            default:
                return defValue;
        }
    }
}
