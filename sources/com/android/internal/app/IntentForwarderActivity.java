package com.android.internal.app;

import android.annotation.UnsupportedAppUsage;
import android.app.Activity;
import android.app.ActivityTaskManager;
import android.app.ActivityThread;
import android.app.AppGlobals;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.IPackageManager;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.UserInfo;
import android.metrics.LogMaker;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.Slog;
import android.widget.Toast;
import com.android.internal.R;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.nano.MetricsProto;
import com.android.internal.telephony.PhoneConstants;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class IntentForwarderActivity extends Activity {
    private static final Set<String> ALLOWED_TEXT_MESSAGE_SCHEMES = new HashSet(Arrays.asList(new String[]{"sms", "smsto", PhoneConstants.APN_TYPE_MMS, "mmsto"}));
    public static String FORWARD_INTENT_TO_MANAGED_PROFILE = "com.android.internal.app.ForwardIntentToManagedProfile";
    public static String FORWARD_INTENT_TO_PARENT = "com.android.internal.app.ForwardIntentToParent";
    @UnsupportedAppUsage
    public static String TAG = "IntentForwarderActivity";
    private static final String TEL_SCHEME = "tel";
    private Injector mInjector;
    private MetricsLogger mMetricsLogger;

    public interface Injector {
        IPackageManager getIPackageManager();

        PackageManager getPackageManager();

        UserManager getUserManager();

        ResolveInfo resolveActivityAsUser(Intent intent, int i, int i2);

        void showToast(int i, int i2);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        int targetUserId;
        int userMessageId;
        super.onCreate(savedInstanceState);
        this.mInjector = createInjector();
        Intent intentReceived = getIntent();
        String className = intentReceived.getComponent().getClassName();
        if (className.equals(FORWARD_INTENT_TO_PARENT)) {
            userMessageId = R.string.forward_intent_to_owner;
            targetUserId = getProfileParent();
            getMetricsLogger().write(new LogMaker((int) MetricsProto.MetricsEvent.ACTION_SWITCH_SHARE_PROFILE).setSubtype(1));
        } else if (className.equals(FORWARD_INTENT_TO_MANAGED_PROFILE)) {
            userMessageId = R.string.forward_intent_to_work;
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
                startActivityAsCaller(newIntent, (Bundle) null, (IBinder) null, false, targetUserId2);
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
        if (!"android".equals(activityInfo.packageName)) {
            return false;
        }
        if (ResolverActivity.class.getName().equals(activityInfo.name) || ChooserActivity.class.getName().equals(activityInfo.name)) {
            return true;
        }
        return false;
    }

    /* JADX WARNING: type inference failed for: r2v10, types: [android.os.Parcelable] */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.content.Intent canForward(android.content.Intent r8, int r9) {
        /*
            r7 = this;
            android.content.Intent r0 = new android.content.Intent
            r0.<init>((android.content.Intent) r8)
            r1 = 50331648(0x3000000, float:3.761582E-37)
            r0.addFlags(r1)
            r7.sanitizeIntent(r0)
            r1 = r0
            java.lang.String r2 = "android.intent.action.CHOOSER"
            java.lang.String r3 = r0.getAction()
            boolean r2 = r2.equals(r3)
            r3 = 0
            if (r2 == 0) goto L_0x004e
            java.lang.String r2 = "android.intent.extra.INITIAL_INTENTS"
            boolean r2 = r0.hasExtra(r2)
            if (r2 == 0) goto L_0x002b
            java.lang.String r2 = TAG
            java.lang.String r4 = "An chooser intent with extra initial intents cannot be forwarded to a different user"
            android.util.Slog.wtf((java.lang.String) r2, (java.lang.String) r4)
            return r3
        L_0x002b:
            java.lang.String r2 = "android.intent.extra.REPLACEMENT_EXTRAS"
            boolean r2 = r0.hasExtra(r2)
            if (r2 == 0) goto L_0x003b
            java.lang.String r2 = TAG
            java.lang.String r4 = "A chooser intent with replacement extras cannot be forwarded to a different user"
            android.util.Slog.wtf((java.lang.String) r2, (java.lang.String) r4)
            return r3
        L_0x003b:
            java.lang.String r2 = "android.intent.extra.INTENT"
            android.os.Parcelable r2 = r0.getParcelableExtra(r2)
            r1 = r2
            android.content.Intent r1 = (android.content.Intent) r1
            if (r1 != 0) goto L_0x004e
            java.lang.String r2 = TAG
            java.lang.String r4 = "Cannot forward a chooser intent with no extra android.intent.extra.INTENT"
            android.util.Slog.wtf((java.lang.String) r2, (java.lang.String) r4)
            return r3
        L_0x004e:
            android.content.Intent r2 = r0.getSelector()
            if (r2 == 0) goto L_0x0058
            android.content.Intent r1 = r0.getSelector()
        L_0x0058:
            android.content.ContentResolver r2 = r7.getContentResolver()
            java.lang.String r2 = r1.resolveTypeIfNeeded(r2)
            r7.sanitizeIntent(r1)
            com.android.internal.app.IntentForwarderActivity$Injector r4 = r7.mInjector     // Catch:{ RemoteException -> 0x0075 }
            android.content.pm.IPackageManager r4 = r4.getIPackageManager()     // Catch:{ RemoteException -> 0x0075 }
            int r5 = r7.getUserId()     // Catch:{ RemoteException -> 0x0075 }
            boolean r4 = r4.canForwardTo(r1, r2, r5, r9)     // Catch:{ RemoteException -> 0x0075 }
            if (r4 == 0) goto L_0x0074
            return r0
        L_0x0074:
            goto L_0x007d
        L_0x0075:
            r4 = move-exception
            java.lang.String r5 = TAG
            java.lang.String r6 = "PackageManagerService is dead?"
            android.util.Slog.e(r5, r6)
        L_0x007d:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.app.IntentForwarderActivity.canForward(android.content.Intent, int):android.content.Intent");
    }

    private int getManagedProfile() {
        for (UserInfo userInfo : this.mInjector.getUserManager().getProfiles(UserHandle.myUserId())) {
            if (userInfo.isManagedProfile()) {
                return userInfo.id;
            }
        }
        String str = TAG;
        Slog.wtf(str, FORWARD_INTENT_TO_MANAGED_PROFILE + " has been called, but there is no managed profile");
        return -10000;
    }

    private int getProfileParent() {
        UserInfo parent = this.mInjector.getUserManager().getProfileParent(UserHandle.myUserId());
        if (parent != null) {
            return parent.id;
        }
        String str = TAG;
        Slog.wtf(str, FORWARD_INTENT_TO_PARENT + " has been called, but there is no parent");
        return -10000;
    }

    private void sanitizeIntent(Intent intent) {
        intent.setPackage((String) null);
        intent.setComponent((ComponentName) null);
    }

    /* access modifiers changed from: protected */
    public MetricsLogger getMetricsLogger() {
        if (this.mMetricsLogger == null) {
            this.mMetricsLogger = new MetricsLogger();
        }
        return this.mMetricsLogger;
    }

    /* access modifiers changed from: protected */
    @VisibleForTesting
    public Injector createInjector() {
        return new InjectorImpl();
    }

    private class InjectorImpl implements Injector {
        private InjectorImpl() {
        }

        public IPackageManager getIPackageManager() {
            return AppGlobals.getPackageManager();
        }

        public UserManager getUserManager() {
            return (UserManager) IntentForwarderActivity.this.getSystemService(UserManager.class);
        }

        public PackageManager getPackageManager() {
            return IntentForwarderActivity.this.getPackageManager();
        }

        public ResolveInfo resolveActivityAsUser(Intent intent, int flags, int userId) {
            return getPackageManager().resolveActivityAsUser(intent, flags, userId);
        }

        public void showToast(int messageId, int duration) {
            Toast.makeText((Context) IntentForwarderActivity.this, (CharSequence) IntentForwarderActivity.this.getString(messageId), duration).show();
        }
    }
}
