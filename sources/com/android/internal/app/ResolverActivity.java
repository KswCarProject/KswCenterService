package com.android.internal.app;

import android.Manifest;
import android.annotation.UnsupportedAppUsage;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.app.ActivityThread;
import android.app.VoiceInteractor;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.LabeledIntent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.UserInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Insets;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.os.Process;
import android.os.RemoteException;
import android.os.StrictMode;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.MediaStore;
import android.provider.Settings;
import android.provider.SettingsStringUtil;
import android.text.TextUtils;
import android.util.Log;
import android.util.Slog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;
import com.android.internal.R;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.content.PackageMonitor;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.nano.MetricsProto;
import com.android.internal.widget.ResolverDrawerLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class ResolverActivity extends Activity {
    private static final boolean DEBUG = false;
    private static final String EXTRA_FRAGMENT_ARG_KEY = ":settings:fragment_args_key";
    private static final String EXTRA_SHOW_FRAGMENT_ARGS = ":settings:show_fragment_args";
    private static final String OPEN_LINKS_COMPONENT_KEY = "app_link_state";
    private static final String TAG = "ResolverActivity";
    @UnsupportedAppUsage
    protected ResolveListAdapter mAdapter;
    protected AbsListView mAdapterView;
    /* access modifiers changed from: private */
    public Button mAlwaysButton;
    private int mDefaultTitleResId;
    boolean mEnableChooserDelegate = true;
    private Space mFooterSpacer = null;
    private int mIconDpi;
    private final ArrayList<Intent> mIntents = new ArrayList<>();
    /* access modifiers changed from: private */
    public int mLastSelected = -1;
    protected int mLaunchedFromUid;
    private int mLayoutId;
    /* access modifiers changed from: private */
    public Button mOnceButton;
    private final PackageMonitor mPackageMonitor = createPackageMonitor();
    /* access modifiers changed from: private */
    public PickTargetOptionRequest mPickOptionRequest;
    @UnsupportedAppUsage
    protected PackageManager mPm;
    /* access modifiers changed from: private */
    public Runnable mPostListReadyRunnable;
    private int mProfileSwitchMessageId = -1;
    protected View mProfileView;
    private String mReferrerPackage;
    private boolean mRegistered;
    protected ResolverDrawerLayout mResolverDrawerLayout;
    private boolean mResolvingHome = false;
    private boolean mRetainInOnStop;
    private boolean mSafeForwardingMode;
    private boolean mSupportsAlwaysUseOption;
    /* access modifiers changed from: private */
    public ColorMatrixColorFilter mSuspendedMatrixColorFilter;
    protected Insets mSystemWindowInsets = null;
    private CharSequence mTitle;
    /* access modifiers changed from: private */
    public boolean mUseLayoutForBrowsables;

    public interface TargetInfo {
        TargetInfo cloneFilledIn(Intent intent, int i);

        List<Intent> getAllSourceIntents();

        Drawable getDisplayIcon();

        CharSequence getDisplayLabel();

        CharSequence getExtendedInfo();

        ResolveInfo getResolveInfo();

        ComponentName getResolvedComponentName();

        Intent getResolvedIntent();

        boolean isSuspended();

        boolean start(Activity activity, Bundle bundle);

        boolean startAsCaller(ResolverActivity resolverActivity, Bundle bundle, int i);

        boolean startAsUser(Activity activity, Bundle bundle, UserHandle userHandle);
    }

    public static int getLabelRes(String action) {
        return ActionTitle.forAction(action).labelRes;
    }

    private enum ActionTitle {
        VIEW("android.intent.action.VIEW", R.string.whichViewApplication, R.string.whichViewApplicationNamed, R.string.whichViewApplicationLabel),
        EDIT(Intent.ACTION_EDIT, R.string.whichEditApplication, R.string.whichEditApplicationNamed, R.string.whichEditApplicationLabel),
        SEND(Intent.ACTION_SEND, R.string.whichSendApplication, R.string.whichSendApplicationNamed, R.string.whichSendApplicationLabel),
        SENDTO(Intent.ACTION_SENDTO, R.string.whichSendToApplication, R.string.whichSendToApplicationNamed, R.string.whichSendToApplicationLabel),
        SEND_MULTIPLE(Intent.ACTION_SEND_MULTIPLE, R.string.whichSendApplication, R.string.whichSendApplicationNamed, R.string.whichSendApplicationLabel),
        CAPTURE_IMAGE(MediaStore.ACTION_IMAGE_CAPTURE, R.string.whichImageCaptureApplication, R.string.whichImageCaptureApplicationNamed, R.string.whichImageCaptureApplicationLabel),
        DEFAULT((String) null, R.string.whichApplication, R.string.whichApplicationNamed, R.string.whichApplicationLabel),
        HOME(Intent.ACTION_MAIN, R.string.whichHomeApplication, R.string.whichHomeApplicationNamed, R.string.whichHomeApplicationLabel);
        
        public static final int BROWSABLE_APP_TITLE_RES = 17041266;
        public static final int BROWSABLE_HOST_APP_TITLE_RES = 17041264;
        public static final int BROWSABLE_HOST_TITLE_RES = 17041263;
        public static final int BROWSABLE_TITLE_RES = 17041265;
        public final String action;
        public final int labelRes;
        public final int namedTitleRes;
        public final int titleRes;

        private ActionTitle(String action2, int titleRes2, int namedTitleRes2, int labelRes2) {
            this.action = action2;
            this.titleRes = titleRes2;
            this.namedTitleRes = namedTitleRes2;
            this.labelRes = labelRes2;
        }

        public static ActionTitle forAction(String action2) {
            for (ActionTitle title : values()) {
                if (title != HOME && action2 != null && action2.equals(title.action)) {
                    return title;
                }
            }
            return DEFAULT;
        }
    }

    /* access modifiers changed from: protected */
    public PackageMonitor createPackageMonitor() {
        return new PackageMonitor() {
            public void onSomePackagesChanged() {
                ResolverActivity.this.mAdapter.handlePackagesChanged();
                ResolverActivity.this.bindProfileView();
            }

            public boolean onPackageChanged(String packageName, int uid, String[] components) {
                return true;
            }
        };
    }

    private Intent makeMyIntent() {
        Intent intent = new Intent(getIntent());
        intent.setComponent((ComponentName) null);
        intent.setFlags(intent.getFlags() & -8388609);
        return intent;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        Intent intent = makeMyIntent();
        Set<String> categories = intent.getCategories();
        if (Intent.ACTION_MAIN.equals(intent.getAction()) && categories != null && categories.size() == 1 && categories.contains(Intent.CATEGORY_HOME)) {
            this.mResolvingHome = true;
        }
        setSafeForwardingMode(true);
        onCreate(savedInstanceState, intent, (CharSequence) null, 0, (Intent[]) null, (List<ResolveInfo>) null, true);
    }

    /* access modifiers changed from: protected */
    @UnsupportedAppUsage
    public void onCreate(Bundle savedInstanceState, Intent intent, CharSequence title, Intent[] initialIntents, List<ResolveInfo> rList, boolean supportsAlwaysUseOption) {
        onCreate(savedInstanceState, intent, title, 0, initialIntents, rList, supportsAlwaysUseOption);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState, Intent intent, CharSequence title, int defaultTitleRes, Intent[] initialIntents, List<ResolveInfo> rList, boolean supportsAlwaysUseOption) {
        int i;
        setTheme(R.style.Theme_DeviceDefault_Resolver);
        super.onCreate(savedInstanceState);
        setProfileSwitchMessageId(intent.getContentUserHint());
        try {
            this.mLaunchedFromUid = ActivityTaskManager.getService().getLaunchedFromUid(getActivityToken());
        } catch (RemoteException e) {
            this.mLaunchedFromUid = -1;
        }
        if (this.mLaunchedFromUid < 0 || UserHandle.isIsolated(this.mLaunchedFromUid)) {
            finish();
            return;
        }
        this.mPm = getPackageManager();
        this.mPackageMonitor.register(this, getMainLooper(), false);
        this.mRegistered = true;
        this.mReferrerPackage = getReferrerPackageName();
        this.mIconDpi = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE)).getLauncherLargeIconDensity();
        this.mIntents.add(0, new Intent(intent));
        this.mTitle = title;
        this.mDefaultTitleResId = defaultTitleRes;
        this.mUseLayoutForBrowsables = getTargetIntent() == null ? false : isHttpSchemeAndViewAction(getTargetIntent());
        this.mSupportsAlwaysUseOption = supportsAlwaysUseOption;
        if (!configureContentView(this.mIntents, initialIntents, rList)) {
            ResolverDrawerLayout rdl = (ResolverDrawerLayout) findViewById(R.id.contentPanel);
            if (rdl != null) {
                rdl.setOnDismissedListener(new ResolverDrawerLayout.OnDismissedListener() {
                    public void onDismissed() {
                        ResolverActivity.this.finish();
                    }
                });
                if (isVoiceInteraction()) {
                    rdl.setCollapsed(false);
                }
                rdl.setSystemUiVisibility(768);
                rdl.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() {
                    public final WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
                        return ResolverActivity.this.onApplyWindowInsets(view, windowInsets);
                    }
                });
                this.mResolverDrawerLayout = rdl;
            }
            this.mProfileView = findViewById(R.id.profile_button);
            if (this.mProfileView != null) {
                this.mProfileView.setOnClickListener(new View.OnClickListener() {
                    public final void onClick(View view) {
                        ResolverActivity.this.onProfileClick(view);
                    }
                });
                bindProfileView();
            }
            initSuspendedColorMatrix();
            if (isVoiceInteraction()) {
                onSetupVoiceInteraction();
            }
            Set<String> categories = intent.getCategories();
            if (this.mAdapter.hasFilteredItem()) {
                i = MetricsProto.MetricsEvent.ACTION_SHOW_APP_DISAMBIG_APP_FEATURED;
            } else {
                i = MetricsProto.MetricsEvent.ACTION_SHOW_APP_DISAMBIG_NONE_FEATURED;
            }
            StringBuilder sb = new StringBuilder();
            sb.append(intent.getAction());
            sb.append(SettingsStringUtil.DELIMITER);
            sb.append(intent.getType());
            sb.append(SettingsStringUtil.DELIMITER);
            sb.append(categories != null ? Arrays.toString(categories.toArray()) : "");
            MetricsLogger.action((Context) this, i, sb.toString());
        }
    }

    /* access modifiers changed from: protected */
    public void onProfileClick(View v) {
        DisplayResolveInfo dri = this.mAdapter.getOtherProfile();
        if (dri != null) {
            this.mProfileSwitchMessageId = -1;
            onTargetSelected(dri, false);
            finish();
        }
    }

    /* access modifiers changed from: protected */
    public WindowInsets onApplyWindowInsets(View v, WindowInsets insets) {
        this.mSystemWindowInsets = insets.getSystemWindowInsets();
        this.mResolverDrawerLayout.setPadding(this.mSystemWindowInsets.left, this.mSystemWindowInsets.top, this.mSystemWindowInsets.right, 0);
        View emptyView = findViewById(16908292);
        if (emptyView != null) {
            emptyView.setPadding(0, 0, 0, this.mSystemWindowInsets.bottom + (getResources().getDimensionPixelSize(R.dimen.chooser_edge_margin_normal) * 2));
        }
        if (this.mFooterSpacer == null) {
            this.mFooterSpacer = new Space(getApplicationContext());
        } else {
            ((ListView) this.mAdapterView).removeFooterView(this.mFooterSpacer);
        }
        this.mFooterSpacer.setLayoutParams(new AbsListView.LayoutParams(-1, this.mSystemWindowInsets.bottom));
        ((ListView) this.mAdapterView).addFooterView(this.mFooterSpacer);
        resetButtonBar();
        return insets.consumeSystemWindowInsets();
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        this.mAdapter.handlePackagesChanged();
        if (this.mSystemWindowInsets != null) {
            this.mResolverDrawerLayout.setPadding(this.mSystemWindowInsets.left, this.mSystemWindowInsets.top, this.mSystemWindowInsets.right, 0);
        }
    }

    private void initSuspendedColorMatrix() {
        ColorMatrix tempBrightnessMatrix = new ColorMatrix();
        float[] mat = tempBrightnessMatrix.getArray();
        mat[0] = 0.5f;
        mat[6] = 0.5f;
        mat[12] = 0.5f;
        mat[4] = (float) 127;
        mat[9] = (float) 127;
        mat[14] = (float) 127;
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(0.0f);
        matrix.preConcat(tempBrightnessMatrix);
        this.mSuspendedMatrixColorFilter = new ColorMatrixColorFilter(matrix);
    }

    public void onSetupVoiceInteraction() {
        sendVoiceChoicesIfNeeded();
    }

    public void sendVoiceChoicesIfNeeded() {
        if (isVoiceInteraction()) {
            VoiceInteractor.PickOptionRequest.Option[] options = new VoiceInteractor.PickOptionRequest.Option[this.mAdapter.getCount()];
            int N = options.length;
            for (int i = 0; i < N; i++) {
                options[i] = optionForChooserTarget(this.mAdapter.getItem(i), i);
            }
            this.mPickOptionRequest = new PickTargetOptionRequest(new VoiceInteractor.Prompt(getTitle()), options, (Bundle) null);
            getVoiceInteractor().submitRequest(this.mPickOptionRequest);
        }
    }

    /* access modifiers changed from: package-private */
    public VoiceInteractor.PickOptionRequest.Option optionForChooserTarget(TargetInfo target, int index) {
        return new VoiceInteractor.PickOptionRequest.Option(target.getDisplayLabel(), index);
    }

    /* access modifiers changed from: protected */
    public final void setAdditionalTargets(Intent[] intents) {
        if (intents != null) {
            for (Intent intent : intents) {
                this.mIntents.add(intent);
            }
        }
    }

    public Intent getTargetIntent() {
        if (this.mIntents.isEmpty()) {
            return null;
        }
        return this.mIntents.get(0);
    }

    /* access modifiers changed from: protected */
    public String getReferrerPackageName() {
        Uri referrer = getReferrer();
        if (referrer == null || !"android-app".equals(referrer.getScheme())) {
            return null;
        }
        return referrer.getHost();
    }

    public int getLayoutResource() {
        return R.layout.resolver_list;
    }

    /* access modifiers changed from: protected */
    public void bindProfileView() {
        if (this.mProfileView != null) {
            DisplayResolveInfo dri = this.mAdapter.getOtherProfile();
            if (dri != null) {
                this.mProfileView.setVisibility(0);
                View text = this.mProfileView.findViewById(R.id.profile_button);
                if (!(text instanceof TextView)) {
                    text = this.mProfileView.findViewById(16908308);
                }
                ((TextView) text).setText(dri.getDisplayLabel());
                return;
            }
            this.mProfileView.setVisibility(8);
        }
    }

    private void setProfileSwitchMessageId(int contentUserHint) {
        boolean originIsManaged;
        if (contentUserHint != -2 && contentUserHint != UserHandle.myUserId()) {
            UserManager userManager = (UserManager) getSystemService("user");
            UserInfo originUserInfo = userManager.getUserInfo(contentUserHint);
            if (originUserInfo != null) {
                originIsManaged = originUserInfo.isManagedProfile();
            } else {
                originIsManaged = false;
            }
            boolean targetIsManaged = userManager.isManagedProfile();
            if (originIsManaged && !targetIsManaged) {
                this.mProfileSwitchMessageId = R.string.forward_intent_to_owner;
            } else if (!originIsManaged && targetIsManaged) {
                this.mProfileSwitchMessageId = R.string.forward_intent_to_work;
            }
        }
    }

    public void setSafeForwardingMode(boolean safeForwarding) {
        this.mSafeForwardingMode = safeForwarding;
    }

    /* access modifiers changed from: protected */
    public CharSequence getTitleForAction(Intent intent, int defaultTitleRes) {
        ActionTitle title;
        if (this.mResolvingHome) {
            title = ActionTitle.HOME;
        } else {
            title = ActionTitle.forAction(intent.getAction());
        }
        boolean named = this.mAdapter.getFilteredPosition() >= 0;
        if (title == ActionTitle.DEFAULT && defaultTitleRes != 0) {
            return getString(defaultTitleRes);
        }
        if (isHttpSchemeAndViewAction(intent)) {
            if (named && !this.mUseLayoutForBrowsables) {
                return getString(17041266, this.mAdapter.getFilteredItem().getDisplayLabel());
            } else if (named && this.mUseLayoutForBrowsables) {
                return getString(17041264, intent.getData().getHost(), this.mAdapter.getFilteredItem().getDisplayLabel());
            } else if (this.mAdapter.areAllTargetsBrowsers()) {
                return getString(17041265);
            } else {
                return getString(17041263, intent.getData().getHost());
            }
        } else if (!named) {
            return getString(title.titleRes);
        } else {
            return getString(title.namedTitleRes, this.mAdapter.getFilteredItem().getDisplayLabel());
        }
    }

    /* access modifiers changed from: package-private */
    public void dismiss() {
        if (!isFinishing()) {
            finish();
        }
    }

    private static abstract class TargetPresentationGetter {
        private final ApplicationInfo mAi;
        private Context mCtx;
        private final boolean mHasSubstitutePermission;
        private final int mIconDpi;
        protected PackageManager mPm;

        /* access modifiers changed from: package-private */
        public abstract String getAppSubLabelInternal();

        /* access modifiers changed from: package-private */
        public abstract Drawable getIconSubstituteInternal();

        TargetPresentationGetter(Context ctx, int iconDpi, ApplicationInfo ai) {
            this.mCtx = ctx;
            this.mPm = ctx.getPackageManager();
            this.mAi = ai;
            this.mIconDpi = iconDpi;
            this.mHasSubstitutePermission = this.mPm.checkPermission(Manifest.permission.SUBSTITUTE_SHARE_TARGET_APP_NAME_AND_ICON, this.mAi.packageName) == 0;
        }

        public Drawable getIcon(UserHandle userHandle) {
            return new BitmapDrawable(this.mCtx.getResources(), getIconBitmap(userHandle));
        }

        public Bitmap getIconBitmap(UserHandle userHandle) {
            Drawable dr = null;
            if (this.mHasSubstitutePermission) {
                dr = getIconSubstituteInternal();
            }
            if (dr == null) {
                try {
                    if (this.mAi.icon != 0) {
                        dr = loadIconFromResource(this.mPm.getResourcesForApplication(this.mAi), this.mAi.icon);
                    }
                } catch (PackageManager.NameNotFoundException e) {
                }
            }
            if (dr == null) {
                dr = this.mAi.loadIcon(this.mPm);
            }
            SimpleIconFactory sif = SimpleIconFactory.obtain(this.mCtx);
            Bitmap icon = sif.createUserBadgedIconBitmap(dr, userHandle);
            sif.recycle();
            return icon;
        }

        public String getLabel() {
            String label = null;
            if (this.mHasSubstitutePermission) {
                label = getAppSubLabelInternal();
            }
            if (label == null) {
                return (String) this.mAi.loadLabel(this.mPm);
            }
            return label;
        }

        public String getSubLabel() {
            if (this.mHasSubstitutePermission) {
                return null;
            }
            return getAppSubLabelInternal();
        }

        /* access modifiers changed from: protected */
        public String loadLabelFromResource(Resources res, int resId) {
            return res.getString(resId);
        }

        /* access modifiers changed from: protected */
        public Drawable loadIconFromResource(Resources res, int resId) {
            return res.getDrawableForDensity(resId, this.mIconDpi);
        }
    }

    @VisibleForTesting
    public static class ResolveInfoPresentationGetter extends ActivityInfoPresentationGetter {
        private final ResolveInfo mRi;

        public ResolveInfoPresentationGetter(Context ctx, int iconDpi, ResolveInfo ri) {
            super(ctx, iconDpi, ri.activityInfo);
            this.mRi = ri;
        }

        /* access modifiers changed from: package-private */
        public Drawable getIconSubstituteInternal() {
            Drawable dr = null;
            try {
                if (!(this.mRi.resolvePackageName == null || this.mRi.icon == 0)) {
                    dr = loadIconFromResource(this.mPm.getResourcesForApplication(this.mRi.resolvePackageName), this.mRi.icon);
                }
            } catch (PackageManager.NameNotFoundException e) {
                Log.e(ResolverActivity.TAG, "SUBSTITUTE_SHARE_TARGET_APP_NAME_AND_ICON permission granted but couldn't find resources for package", e);
            }
            if (dr == null) {
                return super.getIconSubstituteInternal();
            }
            return dr;
        }

        /* access modifiers changed from: package-private */
        public String getAppSubLabelInternal() {
            return (String) this.mRi.loadLabel(this.mPm);
        }
    }

    /* access modifiers changed from: package-private */
    public ResolveInfoPresentationGetter makePresentationGetter(ResolveInfo ri) {
        return new ResolveInfoPresentationGetter(this, this.mIconDpi, ri);
    }

    @VisibleForTesting
    public static class ActivityInfoPresentationGetter extends TargetPresentationGetter {
        private final ActivityInfo mActivityInfo;

        public /* bridge */ /* synthetic */ Drawable getIcon(UserHandle userHandle) {
            return super.getIcon(userHandle);
        }

        public /* bridge */ /* synthetic */ Bitmap getIconBitmap(UserHandle userHandle) {
            return super.getIconBitmap(userHandle);
        }

        public /* bridge */ /* synthetic */ String getLabel() {
            return super.getLabel();
        }

        public /* bridge */ /* synthetic */ String getSubLabel() {
            return super.getSubLabel();
        }

        public ActivityInfoPresentationGetter(Context ctx, int iconDpi, ActivityInfo activityInfo) {
            super(ctx, iconDpi, activityInfo.applicationInfo);
            this.mActivityInfo = activityInfo;
        }

        /* access modifiers changed from: package-private */
        public Drawable getIconSubstituteInternal() {
            try {
                if (this.mActivityInfo.icon != 0) {
                    return loadIconFromResource(this.mPm.getResourcesForApplication(this.mActivityInfo.applicationInfo), this.mActivityInfo.icon);
                }
                return null;
            } catch (PackageManager.NameNotFoundException e) {
                Log.e(ResolverActivity.TAG, "SUBSTITUTE_SHARE_TARGET_APP_NAME_AND_ICON permission granted but couldn't find resources for package", e);
                return null;
            }
        }

        /* access modifiers changed from: package-private */
        public String getAppSubLabelInternal() {
            return (String) this.mActivityInfo.loadLabel(this.mPm);
        }
    }

    /* access modifiers changed from: protected */
    public ActivityInfoPresentationGetter makePresentationGetter(ActivityInfo ai) {
        return new ActivityInfoPresentationGetter(this, this.mIconDpi, ai);
    }

    /* access modifiers changed from: package-private */
    public Drawable loadIconForResolveInfo(ResolveInfo ri) {
        return makePresentationGetter(ri).getIcon(Process.myUserHandle());
    }

    /* access modifiers changed from: protected */
    public void onRestart() {
        super.onRestart();
        if (!this.mRegistered) {
            this.mPackageMonitor.register(this, getMainLooper(), false);
            this.mRegistered = true;
        }
        this.mAdapter.handlePackagesChanged();
        bindProfileView();
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        super.onStop();
        if (this.mRegistered) {
            this.mPackageMonitor.unregister();
            this.mRegistered = false;
        }
        if ((getIntent().getFlags() & 268435456) != 0 && !isVoiceInteraction() && !this.mResolvingHome && !this.mRetainInOnStop && !isChangingConfigurations()) {
            finish();
        }
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        if (!isChangingConfigurations() && this.mPickOptionRequest != null) {
            this.mPickOptionRequest.cancel();
        }
        if (this.mPostListReadyRunnable != null) {
            getMainThreadHandler().removeCallbacks(this.mPostListReadyRunnable);
            this.mPostListReadyRunnable = null;
        }
        if (this.mAdapter != null && this.mAdapter.mResolverListController != null) {
            this.mAdapter.mResolverListController.destroy();
        }
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        resetButtonBar();
    }

    private boolean isHttpSchemeAndViewAction(Intent intent) {
        return (IntentFilter.SCHEME_HTTP.equals(intent.getScheme()) || IntentFilter.SCHEME_HTTPS.equals(intent.getScheme())) && "android.intent.action.VIEW".equals(intent.getAction());
    }

    private boolean hasManagedProfile() {
        UserManager userManager = (UserManager) getSystemService("user");
        if (userManager == null) {
            return false;
        }
        try {
            for (UserInfo userInfo : userManager.getProfiles(getUserId())) {
                if (userInfo != null && userInfo.isManagedProfile()) {
                    return true;
                }
            }
            return false;
        } catch (SecurityException e) {
            return false;
        }
    }

    private boolean supportsManagedProfiles(ResolveInfo resolveInfo) {
        try {
            if (getPackageManager().getApplicationInfo(resolveInfo.activityInfo.packageName, 0).targetSdkVersion >= 21) {
                return true;
            }
            return false;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    /* access modifiers changed from: private */
    public void setAlwaysButtonEnabled(boolean hasValidSelection, int checkedPos, boolean filtered) {
        boolean enabled = false;
        if (hasValidSelection) {
            ResolveInfo ri = this.mAdapter.resolveInfoForPosition(checkedPos, filtered);
            if (ri == null) {
                Log.e(TAG, "Invalid position supplied to setAlwaysButtonEnabled");
                return;
            } else if (ri.targetUserId != -2) {
                Log.e(TAG, "Attempted to set selection to resolve info for another user");
                return;
            } else {
                enabled = true;
                if (!this.mUseLayoutForBrowsables || ri.handleAllWebDataURI) {
                    this.mAlwaysButton.setText((CharSequence) getResources().getString(R.string.activity_resolver_use_always));
                } else {
                    this.mAlwaysButton.setText((CharSequence) getResources().getString(R.string.activity_resolver_set_always));
                }
            }
        }
        this.mAlwaysButton.setEnabled(enabled);
    }

    public void onButtonClick(View v) {
        int which;
        int id = v.getId();
        if (this.mAdapter.hasFilteredItem()) {
            which = this.mAdapter.getFilteredPosition();
        } else {
            which = this.mAdapterView.getCheckedItemPosition();
        }
        boolean z = true;
        boolean hasIndexBeenFiltered = !this.mAdapter.hasFilteredItem();
        ResolveInfo ri = this.mAdapter.resolveInfoForPosition(which, hasIndexBeenFiltered);
        if (!this.mUseLayoutForBrowsables || ri.handleAllWebDataURI || id != 16908779) {
            if (id != 16908779) {
                z = false;
            }
            startSelected(which, z, hasIndexBeenFiltered);
            return;
        }
        showSettingsForSelected(ri);
    }

    private void showSettingsForSelected(ResolveInfo ri) {
        Intent intent = new Intent();
        String packageName = ri.activityInfo.packageName;
        Bundle showFragmentArgs = new Bundle();
        showFragmentArgs.putString(EXTRA_FRAGMENT_ARG_KEY, OPEN_LINKS_COMPONENT_KEY);
        showFragmentArgs.putString("package", packageName);
        intent.setAction(Settings.ACTION_APP_OPEN_BY_DEFAULT_SETTINGS).setData(Uri.fromParts("package", packageName, (String) null)).addFlags(524288).putExtra(EXTRA_FRAGMENT_ARG_KEY, OPEN_LINKS_COMPONENT_KEY).putExtra(EXTRA_SHOW_FRAGMENT_ARGS, showFragmentArgs);
        startActivity(intent);
    }

    public void startSelected(int which, boolean always, boolean hasIndexBeenFiltered) {
        int i;
        if (!isFinishing()) {
            ResolveInfo ri = this.mAdapter.resolveInfoForPosition(which, hasIndexBeenFiltered);
            if (!this.mResolvingHome || !hasManagedProfile() || supportsManagedProfiles(ri)) {
                TargetInfo target = this.mAdapter.targetInfoForPosition(which, hasIndexBeenFiltered);
                if (target != null && onTargetSelected(target, always)) {
                    if (always && this.mSupportsAlwaysUseOption) {
                        MetricsLogger.action((Context) this, (int) MetricsProto.MetricsEvent.ACTION_APP_DISAMBIG_ALWAYS);
                    } else if (this.mSupportsAlwaysUseOption) {
                        MetricsLogger.action((Context) this, (int) MetricsProto.MetricsEvent.ACTION_APP_DISAMBIG_JUST_ONCE);
                    } else {
                        MetricsLogger.action((Context) this, (int) MetricsProto.MetricsEvent.ACTION_APP_DISAMBIG_TAP);
                    }
                    if (this.mAdapter.hasFilteredItem()) {
                        i = MetricsProto.MetricsEvent.ACTION_HIDE_APP_DISAMBIG_APP_FEATURED;
                    } else {
                        i = MetricsProto.MetricsEvent.ACTION_HIDE_APP_DISAMBIG_NONE_FEATURED;
                    }
                    MetricsLogger.action((Context) this, i);
                    finish();
                    return;
                }
                return;
            }
            Toast.makeText((Context) this, (CharSequence) String.format(getResources().getString(R.string.activity_resolver_work_profiles_support), new Object[]{ri.activityInfo.loadLabel(getPackageManager()).toString()}), 1).show();
        }
    }

    public Intent getReplacementIntent(ActivityInfo aInfo, Intent defIntent) {
        return defIntent;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:112:0x020c  */
    /* JADX WARNING: Removed duplicated region for block: B:115:0x0218  */
    /* JADX WARNING: Removed duplicated region for block: B:118:0x021d  */
    /* JADX WARNING: Removed duplicated region for block: B:121:0x0229  */
    /* JADX WARNING: Removed duplicated region for block: B:134:0x0263  */
    /* JADX WARNING: Removed duplicated region for block: B:149:? A[ORIG_RETURN, RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean onTargetSelected(com.android.internal.app.ResolverActivity.TargetInfo r25, boolean r26) {
        /*
            r24 = this;
            r1 = r24
            android.content.pm.ResolveInfo r3 = r25.getResolveInfo()
            if (r25 == 0) goto L_0x000d
            android.content.Intent r0 = r25.getResolvedIntent()
            goto L_0x000e
        L_0x000d:
            r0 = 0
        L_0x000e:
            r5 = r0
            if (r5 == 0) goto L_0x025f
            boolean r0 = r1.mSupportsAlwaysUseOption
            if (r0 != 0) goto L_0x0022
            com.android.internal.app.ResolverActivity$ResolveListAdapter r0 = r1.mAdapter
            boolean r0 = r0.hasFilteredItem()
            if (r0 == 0) goto L_0x001e
            goto L_0x0022
        L_0x001e:
            r20 = r3
            goto L_0x0261
        L_0x0022:
            com.android.internal.app.ResolverActivity$ResolveListAdapter r0 = r1.mAdapter
            java.util.List<com.android.internal.app.ResolverActivity$ResolvedComponentInfo> r0 = r0.mUnfilteredResolveList
            if (r0 == 0) goto L_0x025f
            android.content.IntentFilter r0 = new android.content.IntentFilter
            r0.<init>()
            r8 = r0
            android.content.Intent r0 = r5.getSelector()
            if (r0 == 0) goto L_0x0039
            android.content.Intent r0 = r5.getSelector()
            goto L_0x003a
        L_0x0039:
            r0 = r5
        L_0x003a:
            r9 = r0
            java.lang.String r10 = r9.getAction()
            if (r10 == 0) goto L_0x0044
            r8.addAction(r10)
        L_0x0044:
            java.util.Set r11 = r9.getCategories()
            if (r11 == 0) goto L_0x005e
            java.util.Iterator r0 = r11.iterator()
        L_0x004e:
            boolean r12 = r0.hasNext()
            if (r12 == 0) goto L_0x005e
            java.lang.Object r12 = r0.next()
            java.lang.String r12 = (java.lang.String) r12
            r8.addCategory(r12)
            goto L_0x004e
        L_0x005e:
            java.lang.String r0 = "android.intent.category.DEFAULT"
            r8.addCategory(r0)
            int r0 = r3.match
            r12 = 268369920(0xfff0000, float:2.5144941E-29)
            r12 = r12 & r0
            android.net.Uri r13 = r9.getData()
            r14 = 6291456(0x600000, float:8.816208E-39)
            if (r12 != r14) goto L_0x0085
            java.lang.String r15 = r9.resolveType((android.content.Context) r1)
            if (r15 == 0) goto L_0x0085
            r8.addDataType(r15)     // Catch:{ MalformedMimeTypeException -> 0x007a }
            goto L_0x0085
        L_0x007a:
            r0 = move-exception
            r16 = r0
            r0 = r16
            java.lang.String r4 = "ResolverActivity"
            android.util.Log.w((java.lang.String) r4, (java.lang.Throwable) r0)
            r8 = 0
        L_0x0085:
            if (r13 == 0) goto L_0x013a
            java.lang.String r0 = r13.getScheme()
            if (r0 == 0) goto L_0x013a
            if (r12 != r14) goto L_0x00a7
            java.lang.String r0 = "file"
            java.lang.String r4 = r13.getScheme()
            boolean r0 = r0.equals(r4)
            if (r0 != 0) goto L_0x013a
            java.lang.String r0 = "content"
            java.lang.String r4 = r13.getScheme()
            boolean r0 = r0.equals(r4)
            if (r0 != 0) goto L_0x013a
        L_0x00a7:
            java.lang.String r0 = r13.getScheme()
            r8.addDataScheme(r0)
            android.content.IntentFilter r0 = r3.filter
            java.util.Iterator r0 = r0.schemeSpecificPartsIterator()
            if (r0 == 0) goto L_0x00db
            java.lang.String r4 = r13.getSchemeSpecificPart()
        L_0x00ba:
            if (r4 == 0) goto L_0x00db
            boolean r14 = r0.hasNext()
            if (r14 == 0) goto L_0x00db
            java.lang.Object r14 = r0.next()
            android.os.PatternMatcher r14 = (android.os.PatternMatcher) r14
            boolean r15 = r14.match(r4)
            if (r15 == 0) goto L_0x00da
            java.lang.String r15 = r14.getPath()
            int r7 = r14.getType()
            r8.addDataSchemeSpecificPart(r15, r7)
            goto L_0x00db
        L_0x00da:
            goto L_0x00ba
        L_0x00db:
            android.content.IntentFilter r4 = r3.filter
            java.util.Iterator r4 = r4.authoritiesIterator()
            if (r4 == 0) goto L_0x010d
        L_0x00e3:
            boolean r7 = r4.hasNext()
            if (r7 == 0) goto L_0x010d
            java.lang.Object r7 = r4.next()
            android.content.IntentFilter$AuthorityEntry r7 = (android.content.IntentFilter.AuthorityEntry) r7
            int r14 = r7.match((android.net.Uri) r13)
            if (r14 < 0) goto L_0x010c
            int r14 = r7.getPort()
            java.lang.String r15 = r7.getHost()
            if (r14 < 0) goto L_0x0106
            java.lang.String r16 = java.lang.Integer.toString(r14)
            r6 = r16
            goto L_0x0108
        L_0x0106:
            r6 = 0
        L_0x0108:
            r8.addDataAuthority(r15, r6)
            goto L_0x010d
        L_0x010c:
            goto L_0x00e3
        L_0x010d:
            android.content.IntentFilter r6 = r3.filter
            java.util.Iterator r0 = r6.pathsIterator()
            if (r0 == 0) goto L_0x013a
            java.lang.String r6 = r13.getPath()
        L_0x0119:
            if (r6 == 0) goto L_0x013a
            boolean r7 = r0.hasNext()
            if (r7 == 0) goto L_0x013a
            java.lang.Object r7 = r0.next()
            android.os.PatternMatcher r7 = (android.os.PatternMatcher) r7
            boolean r14 = r7.match(r6)
            if (r14 == 0) goto L_0x0139
            java.lang.String r14 = r7.getPath()
            int r15 = r7.getType()
            r8.addDataPath(r14, r15)
            goto L_0x013a
        L_0x0139:
            goto L_0x0119
        L_0x013a:
            if (r8 == 0) goto L_0x025f
            com.android.internal.app.ResolverActivity$ResolveListAdapter r0 = r1.mAdapter
            java.util.List<com.android.internal.app.ResolverActivity$ResolvedComponentInfo> r0 = r0.mUnfilteredResolveList
            int r4 = r0.size()
            com.android.internal.app.ResolverActivity$ResolveListAdapter r0 = r1.mAdapter
            com.android.internal.app.ResolverActivity$DisplayResolveInfo r0 = r0.mOtherProfile
            if (r0 == 0) goto L_0x014e
            r0 = 1
            goto L_0x014f
        L_0x014e:
            r0 = 0
        L_0x014f:
            r6 = r0
            if (r6 != 0) goto L_0x0155
            android.content.ComponentName[] r0 = new android.content.ComponentName[r4]
            goto L_0x0159
        L_0x0155:
            int r0 = r4 + 1
            android.content.ComponentName[] r0 = new android.content.ComponentName[r0]
        L_0x0159:
            r7 = r0
            r0 = 0
            r14 = r0
            r0 = 0
        L_0x015d:
            if (r0 >= r4) goto L_0x018f
            com.android.internal.app.ResolverActivity$ResolveListAdapter r15 = r1.mAdapter
            java.util.List<com.android.internal.app.ResolverActivity$ResolvedComponentInfo> r15 = r15.mUnfilteredResolveList
            java.lang.Object r15 = r15.get(r0)
            com.android.internal.app.ResolverActivity$ResolvedComponentInfo r15 = (com.android.internal.app.ResolverActivity.ResolvedComponentInfo) r15
            r17 = r9
            r9 = 0
            android.content.pm.ResolveInfo r15 = r15.getResolveInfoAt(r9)
            android.content.ComponentName r9 = new android.content.ComponentName
            r18 = r12
            android.content.pm.ActivityInfo r12 = r15.activityInfo
            java.lang.String r12 = r12.packageName
            android.content.pm.ActivityInfo r2 = r15.activityInfo
            java.lang.String r2 = r2.name
            r9.<init>((java.lang.String) r12, (java.lang.String) r2)
            r7[r0] = r9
            int r2 = r15.match
            if (r2 <= r14) goto L_0x0188
            int r2 = r15.match
            r14 = r2
        L_0x0188:
            int r0 = r0 + 1
            r9 = r17
            r12 = r18
            goto L_0x015d
        L_0x018f:
            r17 = r9
            r18 = r12
            if (r6 == 0) goto L_0x01b0
            com.android.internal.app.ResolverActivity$ResolveListAdapter r0 = r1.mAdapter
            com.android.internal.app.ResolverActivity$DisplayResolveInfo r0 = r0.mOtherProfile
            android.content.ComponentName r0 = r0.getResolvedComponentName()
            r7[r4] = r0
            com.android.internal.app.ResolverActivity$ResolveListAdapter r0 = r1.mAdapter
            com.android.internal.app.ResolverActivity$DisplayResolveInfo r0 = r0.mOtherProfile
            android.content.pm.ResolveInfo r0 = r0.getResolveInfo()
            int r0 = r0.match
            if (r0 <= r14) goto L_0x01b0
            r14 = r0
        L_0x01b0:
            if (r26 == 0) goto L_0x0239
            int r0 = r24.getUserId()
            android.content.pm.PackageManager r9 = r24.getPackageManager()
            android.content.ComponentName r12 = r5.getComponent()
            r9.addPreferredActivity(r8, r14, r7, r12)
            boolean r12 = r3.handleAllWebDataURI
            if (r12 == 0) goto L_0x01dc
            java.lang.String r12 = r9.getDefaultBrowserPackageNameAsUser(r0)
            boolean r15 = android.text.TextUtils.isEmpty(r12)
            if (r15 == 0) goto L_0x01d6
            android.content.pm.ActivityInfo r15 = r3.activityInfo
            java.lang.String r15 = r15.packageName
            r9.setDefaultBrowserPackageNameAsUser(r15, r0)
        L_0x01d6:
            r20 = r3
            r22 = r4
            goto L_0x0238
        L_0x01dc:
            android.content.ComponentName r12 = r5.getComponent()
            java.lang.String r15 = r12.getPackageName()
            if (r13 == 0) goto L_0x01eb
            java.lang.String r16 = r13.getScheme()
            goto L_0x01ed
        L_0x01eb:
            r16 = 0
        L_0x01ed:
            r19 = r16
            r2 = r19
            if (r2 == 0) goto L_0x0207
            r20 = r3
            java.lang.String r3 = "http"
            boolean r3 = r2.equals(r3)
            if (r3 != 0) goto L_0x0205
            java.lang.String r3 = "https"
            boolean r3 = r2.equals(r3)
            if (r3 == 0) goto L_0x0209
        L_0x0205:
            r3 = 1
            goto L_0x020a
        L_0x0207:
            r20 = r3
        L_0x0209:
            r3 = 0
        L_0x020a:
            if (r10 == 0) goto L_0x0218
            r21 = r2
            java.lang.String r2 = "android.intent.action.VIEW"
            boolean r2 = r10.equals(r2)
            if (r2 == 0) goto L_0x021a
            r2 = 1
            goto L_0x021b
        L_0x0218:
            r21 = r2
        L_0x021a:
            r2 = 0
        L_0x021b:
            if (r11 == 0) goto L_0x0229
            r22 = r4
            java.lang.String r4 = "android.intent.category.BROWSABLE"
            boolean r4 = r11.contains(r4)
            if (r4 == 0) goto L_0x022b
            r4 = 1
            goto L_0x022c
        L_0x0229:
            r22 = r4
        L_0x022b:
            r4 = 0
        L_0x022c:
            if (r3 == 0) goto L_0x0238
            if (r2 == 0) goto L_0x0238
            if (r4 == 0) goto L_0x0238
            r23 = r2
            r2 = 2
            r9.updateIntentVerificationStatusAsUser(r15, r2, r0)
        L_0x0238:
            goto L_0x0261
        L_0x0239:
            r20 = r3
            r22 = r4
            com.android.internal.app.ResolverActivity$ResolveListAdapter r0 = r1.mAdapter     // Catch:{ RemoteException -> 0x0247 }
            com.android.internal.app.ResolverListController r0 = r0.mResolverListController     // Catch:{ RemoteException -> 0x0247 }
            r0.setLastChosen(r5, r8, r14)     // Catch:{ RemoteException -> 0x0247 }
            goto L_0x0261
        L_0x0247:
            r0 = move-exception
            java.lang.String r2 = "ResolverActivity"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "Error calling setLastChosenActivity\n"
            r3.append(r4)
            r3.append(r0)
            java.lang.String r3 = r3.toString()
            android.util.Log.d(r2, r3)
            goto L_0x0261
        L_0x025f:
            r20 = r3
        L_0x0261:
            if (r25 == 0) goto L_0x026e
            r24.safelyStartActivity(r25)
            boolean r0 = r25.isSuspended()
            if (r0 == 0) goto L_0x026e
            r3 = 0
            return r3
        L_0x026e:
            r3 = 1
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.app.ResolverActivity.onTargetSelected(com.android.internal.app.ResolverActivity$TargetInfo, boolean):boolean");
    }

    public void safelyStartActivity(TargetInfo cti) {
        StrictMode.disableDeathOnFileUriExposure();
        try {
            safelyStartActivityInternal(cti);
        } finally {
            StrictMode.enableDeathOnFileUriExposure();
        }
    }

    private void safelyStartActivityInternal(TargetInfo cti) {
        String launchedFromPackage;
        if (this.mProfileSwitchMessageId != -1) {
            Toast.makeText((Context) this, (CharSequence) getString(this.mProfileSwitchMessageId), 1).show();
        }
        if (this.mSafeForwardingMode) {
            try {
                if (cti.startAsCaller(this, (Bundle) null, -10000)) {
                    onActivityStarted(cti);
                }
            } catch (RuntimeException e) {
                try {
                    launchedFromPackage = ActivityTaskManager.getService().getLaunchedFromPackage(getActivityToken());
                } catch (RemoteException e2) {
                    launchedFromPackage = "??";
                }
                Slog.wtf(TAG, "Unable to launch as uid " + this.mLaunchedFromUid + " package " + launchedFromPackage + ", while running in " + ActivityThread.currentProcessName(), e);
            }
        } else if (cti.start(this, (Bundle) null)) {
            onActivityStarted(cti);
        }
    }

    /* access modifiers changed from: package-private */
    public boolean startAsCallerImpl(Intent intent, Bundle options, boolean ignoreTargetSecurity, int userId) {
        try {
            IBinder permissionToken = ActivityTaskManager.getService().requestStartActivityPermissionToken(getActivityToken());
            Intent chooserIntent = new Intent();
            ComponentName delegateActivity = ComponentName.unflattenFromString(Resources.getSystem().getString(R.string.config_chooserActivity));
            chooserIntent.setClassName(delegateActivity.getPackageName(), delegateActivity.getClassName());
            chooserIntent.putExtra(ActivityTaskManager.EXTRA_PERMISSION_TOKEN, permissionToken);
            chooserIntent.putExtra(Intent.EXTRA_INTENT, (Parcelable) intent);
            chooserIntent.putExtra(ActivityTaskManager.EXTRA_OPTIONS, options);
            chooserIntent.putExtra(ActivityTaskManager.EXTRA_IGNORE_TARGET_SECURITY, ignoreTargetSecurity);
            chooserIntent.putExtra(Intent.EXTRA_USER_ID, userId);
            chooserIntent.addFlags(View.SCROLLBARS_OUTSIDE_INSET);
            startActivity(chooserIntent);
            return true;
        } catch (RemoteException e) {
            Log.e(TAG, e.toString());
            return true;
        }
    }

    public void onActivityStarted(TargetInfo cti) {
    }

    public boolean shouldGetActivityMetadata() {
        return false;
    }

    public boolean shouldAutoLaunchSingleChoice(TargetInfo target) {
        return !target.isSuspended();
    }

    public void showTargetDetails(ResolveInfo ri) {
        startActivity(new Intent().setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).setData(Uri.fromParts("package", ri.activityInfo.packageName, (String) null)).addFlags(524288));
    }

    public ResolveListAdapter createAdapter(Context context, List<Intent> payloadIntents, Intent[] initialIntents, List<ResolveInfo> rList, int launchedFromUid, boolean filterLastUsed) {
        return new ResolveListAdapter(context, payloadIntents, initialIntents, rList, launchedFromUid, filterLastUsed, createListController());
    }

    /* access modifiers changed from: protected */
    @VisibleForTesting
    public ResolverListController createListController() {
        return new ResolverListController(this, this.mPm, getTargetIntent(), getReferrerPackageName(), this.mLaunchedFromUid);
    }

    public boolean configureContentView(List<Intent> payloadIntents, Intent[] initialIntents, List<ResolveInfo> rList) {
        this.mAdapter = createAdapter(this, payloadIntents, initialIntents, rList, this.mLaunchedFromUid, this.mSupportsAlwaysUseOption && !isVoiceInteraction());
        boolean rebuildCompleted = this.mAdapter.rebuildList();
        if (useLayoutWithDefault()) {
            this.mLayoutId = R.layout.resolver_list_with_default;
        } else {
            this.mLayoutId = getLayoutResource();
        }
        setContentView(this.mLayoutId);
        int count = this.mAdapter.getUnfilteredCount();
        if (rebuildCompleted && count == 1 && this.mAdapter.getOtherProfile() == null) {
            TargetInfo target = this.mAdapter.targetInfoForPosition(0, false);
            if (shouldAutoLaunchSingleChoice(target)) {
                safelyStartActivity(target);
                this.mPackageMonitor.unregister();
                this.mRegistered = false;
                finish();
                return true;
            }
        }
        this.mAdapterView = (AbsListView) findViewById(R.id.resolver_list);
        if (count == 0 && this.mAdapter.mPlaceholderCount == 0) {
            ((TextView) findViewById(16908292)).setVisibility(0);
            this.mAdapterView.setVisibility(8);
        } else {
            this.mAdapterView.setVisibility(0);
            onPrepareAdapterView(this.mAdapterView, this.mAdapter);
        }
        return false;
    }

    public void onPrepareAdapterView(AbsListView adapterView, ResolveListAdapter adapter) {
        boolean useHeader = adapter.hasFilteredItem();
        ListView listView = adapterView instanceof ListView ? (ListView) adapterView : null;
        adapterView.setAdapter((ListAdapter) this.mAdapter);
        ItemClickListener listener = new ItemClickListener();
        adapterView.setOnItemClickListener(listener);
        adapterView.setOnItemLongClickListener(listener);
        if (this.mSupportsAlwaysUseOption || this.mUseLayoutForBrowsables) {
            listView.setChoiceMode(1);
        }
        if (useHeader && listView != null && listView.getHeaderViewsCount() == 0) {
            listView.addHeaderView(LayoutInflater.from(this).inflate((int) R.layout.resolver_different_item_header, (ViewGroup) listView, false));
        }
    }

    public void setHeader() {
        CharSequence title;
        TextView titleView;
        if (this.mAdapter.getCount() == 0 && this.mAdapter.mPlaceholderCount == 0 && (titleView = (TextView) findViewById(16908310)) != null) {
            titleView.setVisibility(8);
        }
        if (this.mTitle != null) {
            title = this.mTitle;
        } else {
            title = getTitleForAction(getTargetIntent(), this.mDefaultTitleResId);
        }
        if (!TextUtils.isEmpty(title)) {
            TextView titleView2 = (TextView) findViewById(16908310);
            if (titleView2 != null) {
                titleView2.setText(title);
            }
            setTitle(title);
        }
        ImageView iconView = (ImageView) findViewById(16908294);
        DisplayResolveInfo iconInfo = this.mAdapter.getFilteredItem();
        if (iconView != null && iconInfo != null) {
            new LoadIconTask(iconInfo, iconView).execute((Params[]) new Void[0]);
        }
    }

    /* access modifiers changed from: private */
    public void resetButtonBar() {
        if (this.mSupportsAlwaysUseOption || this.mUseLayoutForBrowsables) {
            ViewGroup buttonLayout = (ViewGroup) findViewById(R.id.button_bar);
            if (buttonLayout != null) {
                int inset = 0;
                buttonLayout.setVisibility(0);
                if (this.mSystemWindowInsets != null) {
                    inset = this.mSystemWindowInsets.bottom;
                }
                buttonLayout.setPadding(buttonLayout.getPaddingLeft(), buttonLayout.getPaddingTop(), buttonLayout.getPaddingRight(), getResources().getDimensionPixelSize(R.dimen.resolver_button_bar_spacing) + inset);
                this.mOnceButton = (Button) buttonLayout.findViewById(R.id.button_once);
                this.mAlwaysButton = (Button) buttonLayout.findViewById(R.id.button_always);
                resetAlwaysOrOnceButtonBar();
                return;
            }
            Log.e(TAG, "Layout unexpectedly does not have a button bar");
        }
    }

    private void resetAlwaysOrOnceButtonBar() {
        if (useLayoutWithDefault() && this.mAdapter.getFilteredPosition() != -1) {
            setAlwaysButtonEnabled(true, this.mAdapter.getFilteredPosition(), false);
            this.mOnceButton.setEnabled(true);
        } else if (this.mAdapterView != null && this.mAdapterView.getCheckedItemPosition() != -1) {
            setAlwaysButtonEnabled(true, this.mAdapterView.getCheckedItemPosition(), true);
            this.mOnceButton.setEnabled(true);
        }
    }

    /* access modifiers changed from: private */
    public boolean useLayoutWithDefault() {
        return this.mSupportsAlwaysUseOption && this.mAdapter.hasFilteredItem();
    }

    /* access modifiers changed from: protected */
    public void setRetainInOnStop(boolean retainInOnStop) {
        this.mRetainInOnStop = retainInOnStop;
    }

    static boolean resolveInfoMatch(ResolveInfo lhs, ResolveInfo rhs) {
        if (lhs == null) {
            if (rhs != null) {
                return false;
            }
        } else if (lhs.activityInfo == null) {
            if (rhs.activityInfo != null) {
                return false;
            }
        } else if (!Objects.equals(lhs.activityInfo.name, rhs.activityInfo.name) || !Objects.equals(lhs.activityInfo.packageName, rhs.activityInfo.packageName)) {
            return false;
        }
        return true;
    }

    public final class DisplayResolveInfo implements TargetInfo {
        private Drawable mBadge;
        private Drawable mDisplayIcon;
        private final CharSequence mDisplayLabel;
        private final CharSequence mExtendedInfo;
        private boolean mIsSuspended;
        /* access modifiers changed from: private */
        public final ResolveInfo mResolveInfo;
        private final Intent mResolvedIntent;
        private final List<Intent> mSourceIntents = new ArrayList();

        public DisplayResolveInfo(Intent originalIntent, ResolveInfo pri, CharSequence pLabel, CharSequence pInfo, Intent pOrigIntent) {
            this.mSourceIntents.add(originalIntent);
            this.mResolveInfo = pri;
            this.mDisplayLabel = pLabel;
            this.mExtendedInfo = pInfo;
            Intent intent = new Intent(pOrigIntent != null ? pOrigIntent : ResolverActivity.this.getReplacementIntent(pri.activityInfo, ResolverActivity.this.getTargetIntent()));
            intent.addFlags(View.SCROLLBARS_OUTSIDE_INSET);
            ActivityInfo ai = this.mResolveInfo.activityInfo;
            intent.setComponent(new ComponentName(ai.applicationInfo.packageName, ai.name));
            this.mIsSuspended = (ai.applicationInfo.flags & 1073741824) != 0;
            this.mResolvedIntent = intent;
        }

        private DisplayResolveInfo(DisplayResolveInfo other, Intent fillInIntent, int flags) {
            this.mSourceIntents.addAll(other.getAllSourceIntents());
            this.mResolveInfo = other.mResolveInfo;
            this.mDisplayLabel = other.mDisplayLabel;
            this.mDisplayIcon = other.mDisplayIcon;
            this.mExtendedInfo = other.mExtendedInfo;
            this.mResolvedIntent = new Intent(other.mResolvedIntent);
            this.mResolvedIntent.fillIn(fillInIntent, flags);
        }

        public ResolveInfo getResolveInfo() {
            return this.mResolveInfo;
        }

        public CharSequence getDisplayLabel() {
            return this.mDisplayLabel;
        }

        public Drawable getDisplayIcon() {
            return this.mDisplayIcon;
        }

        public TargetInfo cloneFilledIn(Intent fillInIntent, int flags) {
            return new DisplayResolveInfo(this, fillInIntent, flags);
        }

        public List<Intent> getAllSourceIntents() {
            return this.mSourceIntents;
        }

        public void addAlternateSourceIntent(Intent alt) {
            this.mSourceIntents.add(alt);
        }

        public void setDisplayIcon(Drawable icon) {
            this.mDisplayIcon = icon;
        }

        public boolean hasDisplayIcon() {
            return this.mDisplayIcon != null;
        }

        public CharSequence getExtendedInfo() {
            return this.mExtendedInfo;
        }

        public Intent getResolvedIntent() {
            return this.mResolvedIntent;
        }

        public ComponentName getResolvedComponentName() {
            return new ComponentName(this.mResolveInfo.activityInfo.packageName, this.mResolveInfo.activityInfo.name);
        }

        public boolean start(Activity activity, Bundle options) {
            activity.startActivity(this.mResolvedIntent, options);
            return true;
        }

        public boolean startAsCaller(ResolverActivity activity, Bundle options, int userId) {
            if (ResolverActivity.this.mEnableChooserDelegate) {
                return activity.startAsCallerImpl(this.mResolvedIntent, options, false, userId);
            }
            activity.startActivityAsCaller(this.mResolvedIntent, options, (IBinder) null, false, userId);
            return true;
        }

        public boolean startAsUser(Activity activity, Bundle options, UserHandle user) {
            activity.startActivityAsUser(this.mResolvedIntent, options, user);
            return false;
        }

        public boolean isSuspended() {
            return this.mIsSuspended;
        }
    }

    /* access modifiers changed from: package-private */
    public List<DisplayResolveInfo> getDisplayList() {
        return this.mAdapter.mDisplayList;
    }

    public class ResolveListAdapter extends BaseAdapter {
        private boolean mAllTargetsAreBrowsers = false;
        private final List<ResolveInfo> mBaseResolveList;
        List<DisplayResolveInfo> mDisplayList;
        private boolean mFilterLastUsed;
        protected final LayoutInflater mInflater;
        private final Intent[] mInitialIntents;
        private final List<Intent> mIntents;
        protected ResolveInfo mLastChosen;
        private int mLastChosenPosition = -1;
        /* access modifiers changed from: private */
        public DisplayResolveInfo mOtherProfile;
        /* access modifiers changed from: private */
        public int mPlaceholderCount;
        /* access modifiers changed from: private */
        public ResolverListController mResolverListController;
        List<ResolvedComponentInfo> mUnfilteredResolveList;

        public ResolveListAdapter(Context context, List<Intent> payloadIntents, Intent[] initialIntents, List<ResolveInfo> rList, int launchedFromUid, boolean filterLastUsed, ResolverListController resolverListController) {
            this.mIntents = payloadIntents;
            this.mInitialIntents = initialIntents;
            this.mBaseResolveList = rList;
            ResolverActivity.this.mLaunchedFromUid = launchedFromUid;
            this.mInflater = LayoutInflater.from(context);
            this.mDisplayList = new ArrayList();
            this.mFilterLastUsed = filterLastUsed;
            this.mResolverListController = resolverListController;
        }

        public void handlePackagesChanged() {
            rebuildList();
            if (getCount() == 0) {
                ResolverActivity.this.finish();
            }
        }

        public void setPlaceholderCount(int count) {
            this.mPlaceholderCount = count;
        }

        public int getPlaceholderCount() {
            return this.mPlaceholderCount;
        }

        public DisplayResolveInfo getFilteredItem() {
            if (!this.mFilterLastUsed || this.mLastChosenPosition < 0) {
                return null;
            }
            return this.mDisplayList.get(this.mLastChosenPosition);
        }

        public DisplayResolveInfo getOtherProfile() {
            return this.mOtherProfile;
        }

        public int getFilteredPosition() {
            if (!this.mFilterLastUsed || this.mLastChosenPosition < 0) {
                return -1;
            }
            return this.mLastChosenPosition;
        }

        public boolean hasFilteredItem() {
            return this.mFilterLastUsed && this.mLastChosen != null;
        }

        public float getScore(DisplayResolveInfo target) {
            return this.mResolverListController.getScore(target);
        }

        public void updateModel(ComponentName componentName) {
            this.mResolverListController.updateModel(componentName);
        }

        public void updateChooserCounts(String packageName, int userId, String action) {
            this.mResolverListController.updateChooserCounts(packageName, userId, action);
        }

        public boolean areAllTargetsBrowsers() {
            return this.mAllTargetsAreBrowsers;
        }

        /* access modifiers changed from: protected */
        public boolean rebuildList() {
            List<ResolvedComponentInfo> currentResolveList;
            this.mOtherProfile = null;
            this.mLastChosen = null;
            this.mLastChosenPosition = -1;
            this.mAllTargetsAreBrowsers = false;
            this.mDisplayList.clear();
            if (this.mBaseResolveList != null) {
                List<ResolvedComponentInfo> arrayList = new ArrayList<>();
                this.mUnfilteredResolveList = arrayList;
                currentResolveList = arrayList;
                this.mResolverListController.addResolveListDedupe(currentResolveList, ResolverActivity.this.getTargetIntent(), this.mBaseResolveList);
            } else {
                List<ResolvedComponentInfo> resolversForIntent = this.mResolverListController.getResolversForIntent(shouldGetResolvedFilter(), ResolverActivity.this.shouldGetActivityMetadata(), this.mIntents);
                this.mUnfilteredResolveList = resolversForIntent;
                currentResolveList = resolversForIntent;
                if (currentResolveList == null) {
                    processSortedList(currentResolveList);
                    return true;
                }
                List<ResolvedComponentInfo> originalList = this.mResolverListController.filterIneligibleActivities(currentResolveList, true);
                if (originalList != null) {
                    this.mUnfilteredResolveList = originalList;
                }
            }
            Iterator<ResolvedComponentInfo> it = currentResolveList.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                ResolvedComponentInfo info = it.next();
                if (info.getResolveInfoAt(0).targetUserId != -2) {
                    this.mOtherProfile = new DisplayResolveInfo(info.getIntentAt(0), info.getResolveInfoAt(0), info.getResolveInfoAt(0).loadLabel(ResolverActivity.this.mPm), info.getResolveInfoAt(0).loadLabel(ResolverActivity.this.mPm), ResolverActivity.this.getReplacementIntent(info.getResolveInfoAt(0).activityInfo, info.getIntentAt(0)));
                    currentResolveList.remove(info);
                    break;
                }
            }
            if (this.mOtherProfile == null) {
                try {
                    this.mLastChosen = this.mResolverListController.getLastChosen();
                } catch (RemoteException re) {
                    Log.d(ResolverActivity.TAG, "Error calling getLastChosenActivity\n" + re);
                }
            }
            if (currentResolveList != null) {
                int size = currentResolveList.size();
                int i = size;
                if (size > 0) {
                    List<ResolvedComponentInfo> originalList2 = this.mResolverListController.filterLowPriority(currentResolveList, this.mUnfilteredResolveList == currentResolveList);
                    if (originalList2 != null) {
                        this.mUnfilteredResolveList = originalList2;
                    }
                    if (currentResolveList.size() > 1) {
                        int placeholderCount = currentResolveList.size();
                        if (ResolverActivity.this.useLayoutWithDefault()) {
                            placeholderCount--;
                        }
                        setPlaceholderCount(placeholderCount);
                        new AsyncTask<List<ResolvedComponentInfo>, Void, List<ResolvedComponentInfo>>() {
                            /* access modifiers changed from: protected */
                            public List<ResolvedComponentInfo> doInBackground(List<ResolvedComponentInfo>... params) {
                                ResolveListAdapter.this.mResolverListController.sort(params[0]);
                                return params[0];
                            }

                            /* access modifiers changed from: protected */
                            public void onPostExecute(List<ResolvedComponentInfo> sortedComponents) {
                                ResolveListAdapter.this.processSortedList(sortedComponents);
                                ResolverActivity.this.bindProfileView();
                                ResolveListAdapter.this.notifyDataSetChanged();
                            }
                        }.execute((Params[]) new List[]{currentResolveList});
                        postListReadyRunnable();
                        return false;
                    }
                    processSortedList(currentResolveList);
                    return true;
                }
            }
            processSortedList(currentResolveList);
            return true;
        }

        /* access modifiers changed from: private */
        public void processSortedList(List<ResolvedComponentInfo> sortedComponents) {
            if (sortedComponents != null) {
                int size = sortedComponents.size();
                int i = size;
                if (size != 0) {
                    this.mAllTargetsAreBrowsers = ResolverActivity.this.mUseLayoutForBrowsables;
                    int i2 = 0;
                    if (this.mInitialIntents != null) {
                        int i3 = 0;
                        while (i3 < this.mInitialIntents.length) {
                            Intent ii = this.mInitialIntents[i3];
                            if (ii != null) {
                                ActivityInfo ai = ii.resolveActivityInfo(ResolverActivity.this.getPackageManager(), i2);
                                if (ai == null) {
                                    Log.w(ResolverActivity.TAG, "No activity found for " + ii);
                                } else {
                                    ResolveInfo ri = new ResolveInfo();
                                    ri.activityInfo = ai;
                                    UserManager userManager = (UserManager) ResolverActivity.this.getSystemService("user");
                                    if (ii instanceof LabeledIntent) {
                                        LabeledIntent li = (LabeledIntent) ii;
                                        ri.resolvePackageName = li.getSourcePackage();
                                        ri.labelRes = li.getLabelResource();
                                        ri.nonLocalizedLabel = li.getNonLocalizedLabel();
                                        ri.icon = li.getIconResource();
                                        ri.iconResourceId = ri.icon;
                                    }
                                    if (userManager.isManagedProfile()) {
                                        ri.noResourceId = true;
                                        ri.icon = i2;
                                    }
                                    this.mAllTargetsAreBrowsers &= ri.handleAllWebDataURI;
                                    DisplayResolveInfo displayResolveInfo = r5;
                                    DisplayResolveInfo displayResolveInfo2 = new DisplayResolveInfo(ii, ri, ri.loadLabel(ResolverActivity.this.getPackageManager()), (CharSequence) null, ii);
                                    addResolveInfo(displayResolveInfo);
                                }
                            }
                            i3++;
                            i2 = 0;
                        }
                    }
                    for (ResolvedComponentInfo rci : sortedComponents) {
                        ResolveInfo ri2 = rci.getResolveInfoAt(0);
                        if (ri2 != null) {
                            this.mAllTargetsAreBrowsers &= ri2.handleAllWebDataURI;
                            ResolveInfoPresentationGetter pg = ResolverActivity.this.makePresentationGetter(ri2);
                            addResolveInfoWithAlternates(rci, pg.getSubLabel(), pg.getLabel());
                        }
                    }
                }
            }
            postListReadyRunnable();
        }

        private void postListReadyRunnable() {
            if (ResolverActivity.this.mPostListReadyRunnable == null) {
                Runnable unused = ResolverActivity.this.mPostListReadyRunnable = new Runnable() {
                    public void run() {
                        ResolverActivity.this.setHeader();
                        ResolverActivity.this.resetButtonBar();
                        ResolveListAdapter.this.onListRebuilt();
                        Runnable unused = ResolverActivity.this.mPostListReadyRunnable = null;
                    }
                };
                ResolverActivity.this.getMainThreadHandler().post(ResolverActivity.this.mPostListReadyRunnable);
            }
        }

        public void onListRebuilt() {
            if (getUnfilteredCount() == 1 && getOtherProfile() == null) {
                TargetInfo target = targetInfoForPosition(0, false);
                if (ResolverActivity.this.shouldAutoLaunchSingleChoice(target)) {
                    ResolverActivity.this.safelyStartActivity(target);
                    ResolverActivity.this.finish();
                }
            }
        }

        public boolean shouldGetResolvedFilter() {
            return this.mFilterLastUsed;
        }

        private void addResolveInfoWithAlternates(ResolvedComponentInfo rci, CharSequence extraInfo, CharSequence roLabel) {
            int count = rci.getCount();
            Intent intent = rci.getIntentAt(0);
            ResolveInfo add = rci.getResolveInfoAt(0);
            Intent replaceIntent = ResolverActivity.this.getReplacementIntent(add.activityInfo, intent);
            DisplayResolveInfo dri = new DisplayResolveInfo(intent, add, roLabel, extraInfo, replaceIntent);
            addResolveInfo(dri);
            if (replaceIntent == intent) {
                int N = count;
                for (int i = 1; i < N; i++) {
                    dri.addAlternateSourceIntent(rci.getIntentAt(i));
                }
            }
            updateLastChosenPosition(add);
        }

        private void updateLastChosenPosition(ResolveInfo info) {
            if (this.mOtherProfile != null) {
                this.mLastChosenPosition = -1;
            } else if (this.mLastChosen != null && this.mLastChosen.activityInfo.packageName.equals(info.activityInfo.packageName) && this.mLastChosen.activityInfo.name.equals(info.activityInfo.name)) {
                this.mLastChosenPosition = this.mDisplayList.size() - 1;
            }
        }

        private void addResolveInfo(DisplayResolveInfo dri) {
            if (dri != null && dri.mResolveInfo != null && dri.mResolveInfo.targetUserId == -2) {
                for (DisplayResolveInfo existingInfo : this.mDisplayList) {
                    if (ResolverActivity.resolveInfoMatch(dri.mResolveInfo, existingInfo.mResolveInfo)) {
                        return;
                    }
                }
                this.mDisplayList.add(dri);
            }
        }

        public ResolveInfo resolveInfoForPosition(int position, boolean filtered) {
            TargetInfo target = targetInfoForPosition(position, filtered);
            if (target != null) {
                return target.getResolveInfo();
            }
            return null;
        }

        public TargetInfo targetInfoForPosition(int position, boolean filtered) {
            if (filtered) {
                return getItem(position);
            }
            if (this.mDisplayList.size() > position) {
                return this.mDisplayList.get(position);
            }
            return null;
        }

        public int getCount() {
            int totalSize;
            if (this.mDisplayList == null || this.mDisplayList.isEmpty()) {
                totalSize = this.mPlaceholderCount;
            } else {
                totalSize = this.mDisplayList.size();
            }
            if (!this.mFilterLastUsed || this.mLastChosenPosition < 0) {
                return totalSize;
            }
            return totalSize - 1;
        }

        public int getUnfilteredCount() {
            return this.mDisplayList.size();
        }

        public TargetInfo getItem(int position) {
            if (this.mFilterLastUsed && this.mLastChosenPosition >= 0 && position >= this.mLastChosenPosition) {
                position++;
            }
            if (this.mDisplayList.size() > position) {
                return this.mDisplayList.get(position);
            }
            return null;
        }

        public long getItemId(int position) {
            return (long) position;
        }

        public int getDisplayResolveInfoCount() {
            return this.mDisplayList.size();
        }

        public DisplayResolveInfo getDisplayResolveInfo(int index) {
            return this.mDisplayList.get(index);
        }

        public final View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                view = createView(parent);
            }
            onBindView(view, getItem(position));
            return view;
        }

        public final View createView(ViewGroup parent) {
            View view = onCreateView(parent);
            view.setTag(new ViewHolder(view));
            return view;
        }

        public View onCreateView(ViewGroup parent) {
            return this.mInflater.inflate((int) R.layout.resolve_list_item, parent, false);
        }

        public final void bindView(int position, View view) {
            onBindView(view, getItem(position));
        }

        /* access modifiers changed from: protected */
        public void onBindView(View view, TargetInfo info) {
            ViewHolder holder = (ViewHolder) view.getTag();
            if (info == null) {
                holder.icon.setImageDrawable(ResolverActivity.this.getDrawable(R.drawable.resolver_icon_placeholder));
                return;
            }
            CharSequence label = info.getDisplayLabel();
            if (!TextUtils.equals(holder.text.getText(), label)) {
                holder.text.setText(info.getDisplayLabel());
            }
            CharSequence subLabel = info.getExtendedInfo();
            if (TextUtils.equals(label, subLabel)) {
                subLabel = null;
            }
            if (!TextUtils.equals(holder.text2.getText(), subLabel)) {
                holder.text2.setText(subLabel);
            }
            if (info.isSuspended()) {
                holder.icon.setColorFilter((ColorFilter) ResolverActivity.this.mSuspendedMatrixColorFilter);
            } else {
                holder.icon.setColorFilter((ColorFilter) null);
            }
            if (!(info instanceof DisplayResolveInfo) || ((DisplayResolveInfo) info).hasDisplayIcon()) {
                holder.icon.setImageDrawable(info.getDisplayIcon());
            } else {
                new LoadIconTask((DisplayResolveInfo) info, holder.icon).execute((Params[]) new Void[0]);
            }
        }
    }

    @VisibleForTesting
    public static final class ResolvedComponentInfo {
        private final List<Intent> mIntents = new ArrayList();
        private final List<ResolveInfo> mResolveInfos = new ArrayList();
        public final ComponentName name;

        public ResolvedComponentInfo(ComponentName name2, Intent intent, ResolveInfo info) {
            this.name = name2;
            add(intent, info);
        }

        public void add(Intent intent, ResolveInfo info) {
            this.mIntents.add(intent);
            this.mResolveInfos.add(info);
        }

        public int getCount() {
            return this.mIntents.size();
        }

        public Intent getIntentAt(int index) {
            if (index >= 0) {
                return this.mIntents.get(index);
            }
            return null;
        }

        public ResolveInfo getResolveInfoAt(int index) {
            if (index >= 0) {
                return this.mResolveInfos.get(index);
            }
            return null;
        }

        public int findIntent(Intent intent) {
            int N = this.mIntents.size();
            for (int i = 0; i < N; i++) {
                if (intent.equals(this.mIntents.get(i))) {
                    return i;
                }
            }
            return -1;
        }

        public int findResolveInfo(ResolveInfo info) {
            int N = this.mResolveInfos.size();
            for (int i = 0; i < N; i++) {
                if (info.equals(this.mResolveInfos.get(i))) {
                    return i;
                }
            }
            return -1;
        }
    }

    static class ViewHolder {
        public Drawable defaultItemViewBackground;
        public ImageView icon;
        public View itemView;
        public TextView text;
        public TextView text2;

        public ViewHolder(View view) {
            this.itemView = view;
            this.defaultItemViewBackground = view.getBackground();
            this.text = (TextView) view.findViewById(16908308);
            this.text2 = (TextView) view.findViewById(16908309);
            this.icon = (ImageView) view.findViewById(16908294);
        }
    }

    class ItemClickListener implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
        ItemClickListener() {
        }

        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            ListView listView = parent instanceof ListView ? (ListView) parent : null;
            if (listView != null) {
                position -= listView.getHeaderViewsCount();
            }
            if (position >= 0 && ResolverActivity.this.mAdapter.resolveInfoForPosition(position, true) != null) {
                int checkedPos = ResolverActivity.this.mAdapterView.getCheckedItemPosition();
                boolean hasValidSelection = checkedPos != -1;
                if (ResolverActivity.this.useLayoutWithDefault() || ((hasValidSelection && ResolverActivity.this.mLastSelected == checkedPos) || ResolverActivity.this.mAlwaysButton == null)) {
                    ResolverActivity.this.startSelected(position, false, true);
                    return;
                }
                ResolverActivity.this.setAlwaysButtonEnabled(hasValidSelection, checkedPos, true);
                ResolverActivity.this.mOnceButton.setEnabled(hasValidSelection);
                if (hasValidSelection) {
                    ResolverActivity.this.mAdapterView.smoothScrollToPosition(checkedPos);
                }
                int unused = ResolverActivity.this.mLastSelected = checkedPos;
            }
        }

        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            ListView listView = parent instanceof ListView ? (ListView) parent : null;
            if (listView != null) {
                position -= listView.getHeaderViewsCount();
            }
            if (position < 0) {
                return false;
            }
            ResolverActivity.this.showTargetDetails(ResolverActivity.this.mAdapter.resolveInfoForPosition(position, true));
            return true;
        }
    }

    class LoadIconTask extends AsyncTask<Void, Void, Drawable> {
        protected final DisplayResolveInfo mDisplayResolveInfo;
        private final ResolveInfo mResolveInfo;
        private final ImageView mTargetView;

        LoadIconTask(DisplayResolveInfo dri, ImageView target) {
            this.mDisplayResolveInfo = dri;
            this.mResolveInfo = dri.getResolveInfo();
            this.mTargetView = target;
        }

        /* access modifiers changed from: protected */
        public Drawable doInBackground(Void... params) {
            return ResolverActivity.this.loadIconForResolveInfo(this.mResolveInfo);
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Drawable d) {
            if (ResolverActivity.this.mAdapter.getOtherProfile() == this.mDisplayResolveInfo) {
                ResolverActivity.this.bindProfileView();
                return;
            }
            this.mDisplayResolveInfo.setDisplayIcon(d);
            this.mTargetView.setImageDrawable(d);
        }
    }

    static final boolean isSpecificUriMatch(int match) {
        int match2 = match & IntentFilter.MATCH_CATEGORY_MASK;
        return match2 >= 3145728 && match2 <= 5242880;
    }

    static class PickTargetOptionRequest extends VoiceInteractor.PickOptionRequest {
        public PickTargetOptionRequest(VoiceInteractor.Prompt prompt, VoiceInteractor.PickOptionRequest.Option[] options, Bundle extras) {
            super(prompt, options, extras);
        }

        public void onCancel() {
            super.onCancel();
            ResolverActivity ra = (ResolverActivity) getActivity();
            if (ra != null) {
                PickTargetOptionRequest unused = ra.mPickOptionRequest = null;
                ra.finish();
            }
        }

        public void onPickOptionResult(boolean finished, VoiceInteractor.PickOptionRequest.Option[] selections, Bundle result) {
            ResolverActivity ra;
            super.onPickOptionResult(finished, selections, result);
            if (selections.length == 1 && (ra = (ResolverActivity) getActivity()) != null && ra.onTargetSelected(ra.mAdapter.getItem(selections[0].getIndex()), false)) {
                PickTargetOptionRequest unused = ra.mPickOptionRequest = null;
                ra.finish();
            }
        }
    }
}
