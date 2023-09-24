package com.android.internal.app;

import android.annotation.UnsupportedAppUsage;
import android.app.Activity;
import android.app.ActivityTaskManager;
import android.app.ActivityThread;
import android.app.AppGlobals;
import android.content.Intent;
import android.content.p002pm.ActivityInfo;
import android.content.p002pm.IPackageManager;
import android.content.p002pm.PackageManager;
import android.content.p002pm.ResolveInfo;
import android.content.p002pm.UserInfo;
import android.metrics.LogMaker;
import android.p007os.Bundle;
import android.p007os.RemoteException;
import android.p007os.UserHandle;
import android.p007os.UserManager;
import android.util.Slog;
import android.view.View;
import android.widget.Toast;
import com.android.internal.C3132R;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.nano.MetricsProto;
import com.android.internal.telephony.PhoneConstants;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/* loaded from: classes4.dex */
public class IntentForwarderActivity extends Activity {
    private static final String TEL_SCHEME = "tel";
    private Injector mInjector;
    private MetricsLogger mMetricsLogger;
    @UnsupportedAppUsage
    public static String TAG = "IntentForwarderActivity";
    public static String FORWARD_INTENT_TO_PARENT = "com.android.internal.app.ForwardIntentToParent";
    public static String FORWARD_INTENT_TO_MANAGED_PROFILE = "com.android.internal.app.ForwardIntentToManagedProfile";
    private static final Set<String> ALLOWED_TEXT_MESSAGE_SCHEMES = new HashSet(Arrays.asList("sms", "smsto", PhoneConstants.APN_TYPE_MMS, "mmsto"));

    /* loaded from: classes4.dex */
    public interface Injector {
        IPackageManager getIPackageManager();

        PackageManager getPackageManager();

        UserManager getUserManager();

        ResolveInfo resolveActivityAsUser(Intent intent, int i, int i2);

        void showToast(int i, int i2);
    }

    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        int userMessageId;
        int targetUserId;
        super.onCreate(savedInstanceState);
        this.mInjector = createInjector();
        Intent intentReceived = getIntent();
        String className = intentReceived.getComponent().getClassName();
        if (className.equals(FORWARD_INTENT_TO_PARENT)) {
            userMessageId = C3132R.string.forward_intent_to_owner;
            targetUserId = getProfileParent();
            getMetricsLogger().write(new LogMaker((int) MetricsProto.MetricsEvent.ACTION_SWITCH_SHARE_PROFILE).setSubtype(1));
        } else if (className.equals(FORWARD_INTENT_TO_MANAGED_PROFILE)) {
            userMessageId = C3132R.string.forward_intent_to_work;
            targetUserId = getManagedProfile();
            getMetricsLogger().write(new LogMaker((int) MetricsProto.MetricsEvent.ACTION_SWITCH_SHARE_PROFILE).setSubtype(2));
        } else {
            String str = TAG;
            Slog.wtf(str, IntentForwarderActivity.class.getName() + " cannot be called directly");
            userMessageId = -1;
            targetUserId = -10000;
        }
        int targetUserId2 = targetUserId;
        if (targetUserId2 == -10000) {
            finish();
            return;
        }
        int callingUserId = getUserId();
        Intent newIntent = canForward(intentReceived, targetUserId2);
        if (newIntent != null) {
            if (Intent.ACTION_CHOOSER.equals(newIntent.getAction())) {
                Intent innerIntent = (Intent) newIntent.getParcelableExtra(Intent.EXTRA_INTENT);
                innerIntent.prepareToLeaveUser(callingUserId);
                innerIntent.fixUris(callingUserId);
            } else {
                newIntent.prepareToLeaveUser(callingUserId);
            }
            ResolveInfo ri = this.mInjector.resolveActivityAsUser(newIntent, 65536, targetUserId2);
            try {
                startActivityAsCaller(newIntent, null, null, false, targetUserId2);
            } catch (RuntimeException e) {
                int launchedFromUid = -1;
                String launchedFromPackage = "?";
                try {
                    launchedFromUid = ActivityTaskManager.getService().getLaunchedFromUid(getActivityToken());
                    launchedFromPackage = ActivityTaskManager.getService().getLaunchedFromPackage(getActivityToken());
                } catch (RemoteException e2) {
                }
                String str2 = TAG;
                Slog.wtf(str2, "Unable to launch as UID " + launchedFromUid + " package " + launchedFromPackage + ", while running in " + ActivityThread.currentProcessName(), e);
            }
            if (shouldShowDisclosure(ri, intentReceived)) {
                this.mInjector.showToast(userMessageId, 1);
            }
        } else {
            String str3 = TAG;
            Slog.wtf(str3, "the intent: " + intentReceived + " cannot be forwarded from user " + callingUserId + " to user " + targetUserId2);
        }
        finish();
    }

    private boolean shouldShowDisclosure(ResolveInfo ri, Intent intent) {
        if (ri == null || ri.activityInfo == null) {
            return true;
        }
        if (!ri.activityInfo.applicationInfo.isSystemApp() || (!isDialerIntent(intent) && !isTextMessageIntent(intent))) {
            return true ^ isTargetResolverOrChooserActivity(ri.activityInfo);
        }
        return false;
    }

    private boolean isTextMessageIntent(Intent intent) {
        return (Intent.ACTION_SENDTO.equals(intent.getAction()) || isViewActionIntent(intent)) && ALLOWED_TEXT_MESSAGE_SCHEMES.contains(intent.getScheme());
    }

    private boolean isDialerIntent(Intent intent) {
        return Intent.ACTION_DIAL.equals(intent.getAction()) || Intent.ACTION_CALL.equals(intent.getAction()) || Intent.ACTION_CALL_PRIVILEGED.equals(intent.getAction()) || Intent.ACTION_CALL_EMERGENCY.equals(intent.getAction()) || (isViewActionIntent(intent) && "tel".equals(intent.getScheme()));
    }

    private boolean isViewActionIntent(Intent intent) {
        return "android.intent.action.VIEW".equals(intent.getAction()) && intent.hasCategory(Intent.CATEGORY_BROWSABLE);
    }

    private boolean isTargetResolverOrChooserActivity(ActivityInfo activityInfo) {
        if ("android".equals(activityInfo.packageName)) {
            return ResolverActivity.class.getName().equals(activityInfo.name) || ChooserActivity.class.getName().equals(activityInfo.name);
        }
        return false;
    }

    Intent canForward(Intent incomingIntent, int targetUserId) {
        Intent forwardIntent = new Intent(incomingIntent);
        forwardIntent.addFlags(View.SCROLLBARS_OUTSIDE_INSET);
        sanitizeIntent(forwardIntent);
        Intent intentToCheck = forwardIntent;
        if (Intent.ACTION_CHOOSER.equals(forwardIntent.getAction())) {
            if (forwardIntent.hasExtra(Intent.EXTRA_INITIAL_INTENTS)) {
                Slog.wtf(TAG, "An chooser intent with extra initial intents cannot be forwarded to a different user");
                return null;
            } else if (forwardIntent.hasExtra(Intent.EXTRA_REPLACEMENT_EXTRAS)) {
                Slog.wtf(TAG, "A chooser intent with replacement extras cannot be forwarded to a different user");
                return null;
            } else {
                intentToCheck = (Intent) forwardIntent.getParcelableExtra(Intent.EXTRA_INTENT);
                if (intentToCheck == null) {
                    Slog.wtf(TAG, "Cannot forward a chooser intent with no extra android.intent.extra.INTENT");
                    return null;
                }
            }
        }
        if (forwardIntent.getSelector() != null) {
            intentToCheck = forwardIntent.getSelector();
        }
        String resolvedType = intentToCheck.resolveTypeIfNeeded(getContentResolver());
        sanitizeIntent(intentToCheck);
        try {
        } catch (RemoteException e) {
            Slog.m56e(TAG, "PackageManagerService is dead?");
        }
        if (this.mInjector.getIPackageManager().canForwardTo(intentToCheck, resolvedType, getUserId(), targetUserId)) {
            return forwardIntent;
        }
        return null;
    }

    private int getManagedProfile() {
        List<UserInfo> relatedUsers = this.mInjector.getUserManager().getProfiles(UserHandle.myUserId());
        for (UserInfo userInfo : relatedUsers) {
            if (userInfo.isManagedProfile()) {
                return userInfo.f30id;
            }
        }
        String str = TAG;
        Slog.wtf(str, FORWARD_INTENT_TO_MANAGED_PROFILE + " has been called, but there is no managed profile");
        return -10000;
    }

    private int getProfileParent() {
        UserInfo parent = this.mInjector.getUserManager().getProfileParent(UserHandle.myUserId());
        if (parent == null) {
            String str = TAG;
            Slog.wtf(str, FORWARD_INTENT_TO_PARENT + " has been called, but there is no parent");
            return -10000;
        }
        return parent.f30id;
    }

    private void sanitizeIntent(Intent intent) {
        intent.setPackage(null);
        intent.setComponent(null);
    }

    protected MetricsLogger getMetricsLogger() {
        if (this.mMetricsLogger == null) {
            this.mMetricsLogger = new MetricsLogger();
        }
        return this.mMetricsLogger;
    }

    @VisibleForTesting
    protected Injector createInjector() {
        return new InjectorImpl();
    }

    /* loaded from: classes4.dex */
    private class InjectorImpl implements Injector {
        private InjectorImpl() {
        }

        @Override // com.android.internal.app.IntentForwarderActivity.Injector
        public IPackageManager getIPackageManager() {
            return AppGlobals.getPackageManager();
        }

        @Override // com.android.internal.app.IntentForwarderActivity.Injector
        public UserManager getUserManager() {
            return (UserManager) IntentForwarderActivity.this.getSystemService(UserManager.class);
        }

        @Override // com.android.internal.app.IntentForwarderActivity.Injector
        public PackageManager getPackageManager() {
            return IntentForwarderActivity.this.getPackageManager();
        }

        @Override // com.android.internal.app.IntentForwarderActivity.Injector
        public ResolveInfo resolveActivityAsUser(Intent intent, int flags, int userId) {
            return getPackageManager().resolveActivityAsUser(intent, flags, userId);
        }

        @Override // com.android.internal.app.IntentForwarderActivity.Injector
        public void showToast(int messageId, int duration) {
            Toast.makeText(IntentForwarderActivity.this, IntentForwarderActivity.this.getString(messageId), duration).show();
        }
    }
}
