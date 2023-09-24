package com.android.internal.app;

import android.Manifest;
import android.annotation.UnsupportedAppUsage;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.app.ActivityThread;
import android.app.VoiceInteractor;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.p002pm.ActivityInfo;
import android.content.p002pm.ApplicationInfo;
import android.content.p002pm.LabeledIntent;
import android.content.p002pm.PackageManager;
import android.content.p002pm.ResolveInfo;
import android.content.p002pm.UserInfo;
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
import android.p007os.AsyncTask;
import android.p007os.Bundle;
import android.p007os.IBinder;
import android.p007os.PatternMatcher;
import android.p007os.Process;
import android.p007os.RemoteException;
import android.p007os.StrictMode;
import android.p007os.UserHandle;
import android.p007os.UserManager;
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
import android.widget.ListView;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;
import com.android.internal.C3132R;
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

/* loaded from: classes4.dex */
public class ResolverActivity extends Activity {
    private static final boolean DEBUG = false;
    private static final String EXTRA_FRAGMENT_ARG_KEY = ":settings:fragment_args_key";
    private static final String EXTRA_SHOW_FRAGMENT_ARGS = ":settings:show_fragment_args";
    private static final String OPEN_LINKS_COMPONENT_KEY = "app_link_state";
    private static final String TAG = "ResolverActivity";
    @UnsupportedAppUsage
    protected ResolveListAdapter mAdapter;
    protected AbsListView mAdapterView;
    private Button mAlwaysButton;
    private int mDefaultTitleResId;
    private int mIconDpi;
    protected int mLaunchedFromUid;
    private int mLayoutId;
    private Button mOnceButton;
    private PickTargetOptionRequest mPickOptionRequest;
    @UnsupportedAppUsage
    protected PackageManager mPm;
    private Runnable mPostListReadyRunnable;
    protected View mProfileView;
    private String mReferrerPackage;
    private boolean mRegistered;
    protected ResolverDrawerLayout mResolverDrawerLayout;
    private boolean mRetainInOnStop;
    private boolean mSafeForwardingMode;
    private boolean mSupportsAlwaysUseOption;
    private ColorMatrixColorFilter mSuspendedMatrixColorFilter;
    private CharSequence mTitle;
    private boolean mUseLayoutForBrowsables;
    boolean mEnableChooserDelegate = true;
    private int mLastSelected = -1;
    private boolean mResolvingHome = false;
    private int mProfileSwitchMessageId = -1;
    private final ArrayList<Intent> mIntents = new ArrayList<>();
    protected Insets mSystemWindowInsets = null;
    private Space mFooterSpacer = null;
    private final PackageMonitor mPackageMonitor = createPackageMonitor();

    /* loaded from: classes4.dex */
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

    /* loaded from: classes4.dex */
    private enum ActionTitle {
        VIEW("android.intent.action.VIEW", C3132R.string.whichViewApplication, C3132R.string.whichViewApplicationNamed, C3132R.string.whichViewApplicationLabel),
        EDIT(Intent.ACTION_EDIT, C3132R.string.whichEditApplication, C3132R.string.whichEditApplicationNamed, C3132R.string.whichEditApplicationLabel),
        SEND(Intent.ACTION_SEND, C3132R.string.whichSendApplication, C3132R.string.whichSendApplicationNamed, C3132R.string.whichSendApplicationLabel),
        SENDTO(Intent.ACTION_SENDTO, C3132R.string.whichSendToApplication, C3132R.string.whichSendToApplicationNamed, C3132R.string.whichSendToApplicationLabel),
        SEND_MULTIPLE(Intent.ACTION_SEND_MULTIPLE, C3132R.string.whichSendApplication, C3132R.string.whichSendApplicationNamed, C3132R.string.whichSendApplicationLabel),
        CAPTURE_IMAGE(MediaStore.ACTION_IMAGE_CAPTURE, C3132R.string.whichImageCaptureApplication, C3132R.string.whichImageCaptureApplicationNamed, C3132R.string.whichImageCaptureApplicationLabel),
        DEFAULT(null, C3132R.string.whichApplication, C3132R.string.whichApplicationNamed, C3132R.string.whichApplicationLabel),
        HOME(Intent.ACTION_MAIN, C3132R.string.whichHomeApplication, C3132R.string.whichHomeApplicationNamed, C3132R.string.whichHomeApplicationLabel);
        
        public static final int BROWSABLE_APP_TITLE_RES = 17041266;
        public static final int BROWSABLE_HOST_APP_TITLE_RES = 17041264;
        public static final int BROWSABLE_HOST_TITLE_RES = 17041263;
        public static final int BROWSABLE_TITLE_RES = 17041265;
        public final String action;
        public final int labelRes;
        public final int namedTitleRes;
        public final int titleRes;

        ActionTitle(String action, int titleRes, int namedTitleRes, int labelRes) {
            this.action = action;
            this.titleRes = titleRes;
            this.namedTitleRes = namedTitleRes;
            this.labelRes = labelRes;
        }

        public static ActionTitle forAction(String action) {
            ActionTitle[] values;
            for (ActionTitle title : values()) {
                if (title != HOME && action != null && action.equals(title.action)) {
                    return title;
                }
            }
            return DEFAULT;
        }
    }

    protected PackageMonitor createPackageMonitor() {
        return new PackageMonitor() { // from class: com.android.internal.app.ResolverActivity.1
            @Override // com.android.internal.content.PackageMonitor
            public void onSomePackagesChanged() {
                ResolverActivity.this.mAdapter.handlePackagesChanged();
                ResolverActivity.this.bindProfileView();
            }

            @Override // com.android.internal.content.PackageMonitor
            public boolean onPackageChanged(String packageName, int uid, String[] components) {
                return true;
            }
        };
    }

    private Intent makeMyIntent() {
        Intent intent = new Intent(getIntent());
        intent.setComponent(null);
        intent.setFlags(intent.getFlags() & (-8388609));
        return intent;
    }

    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = makeMyIntent();
        Set<String> categories = intent.getCategories();
        if (Intent.ACTION_MAIN.equals(intent.getAction()) && categories != null && categories.size() == 1 && categories.contains(Intent.CATEGORY_HOME)) {
            this.mResolvingHome = true;
        }
        setSafeForwardingMode(true);
        onCreate(savedInstanceState, intent, null, 0, null, null, true);
    }

    @UnsupportedAppUsage
    protected void onCreate(Bundle savedInstanceState, Intent intent, CharSequence title, Intent[] initialIntents, List<ResolveInfo> rList, boolean supportsAlwaysUseOption) {
        onCreate(savedInstanceState, intent, title, 0, initialIntents, rList, supportsAlwaysUseOption);
    }

    protected void onCreate(Bundle savedInstanceState, Intent intent, CharSequence title, int defaultTitleRes, Intent[] initialIntents, List<ResolveInfo> rList, boolean supportsAlwaysUseOption) {
        int i;
        setTheme(C3132R.C3136style.Theme_DeviceDefault_Resolver);
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
        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        this.mIconDpi = am.getLauncherLargeIconDensity();
        this.mIntents.add(0, new Intent(intent));
        this.mTitle = title;
        this.mDefaultTitleResId = defaultTitleRes;
        this.mUseLayoutForBrowsables = getTargetIntent() == null ? false : isHttpSchemeAndViewAction(getTargetIntent());
        this.mSupportsAlwaysUseOption = supportsAlwaysUseOption;
        if (configureContentView(this.mIntents, initialIntents, rList)) {
            return;
        }
        ResolverDrawerLayout rdl = (ResolverDrawerLayout) findViewById(C3132R.C3134id.contentPanel);
        if (rdl != null) {
            rdl.setOnDismissedListener(new ResolverDrawerLayout.OnDismissedListener() { // from class: com.android.internal.app.ResolverActivity.2
                @Override // com.android.internal.widget.ResolverDrawerLayout.OnDismissedListener
                public void onDismissed() {
                    ResolverActivity.this.finish();
                }
            });
            if (isVoiceInteraction()) {
                rdl.setCollapsed(false);
            }
            rdl.setSystemUiVisibility(768);
            rdl.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() { // from class: com.android.internal.app.-$$Lambda$yRChr-JGmMwuDQFg-BsC_mE_wmc
                @Override // android.view.View.OnApplyWindowInsetsListener
                public final WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
                    return ResolverActivity.this.onApplyWindowInsets(view, windowInsets);
                }
            });
            this.mResolverDrawerLayout = rdl;
        }
        this.mProfileView = findViewById(C3132R.C3134id.profile_button);
        if (this.mProfileView != null) {
            this.mProfileView.setOnClickListener(new View.OnClickListener() { // from class: com.android.internal.app.-$$Lambda$fPZctSH683BQhFNSBKdl6Wz99qg
                @Override // android.view.View.OnClickListener
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
        MetricsLogger.action(this, i, sb.toString());
    }

    protected void onProfileClick(View v) {
        DisplayResolveInfo dri = this.mAdapter.getOtherProfile();
        if (dri == null) {
            return;
        }
        this.mProfileSwitchMessageId = -1;
        onTargetSelected(dri, false);
        finish();
    }

    protected WindowInsets onApplyWindowInsets(View v, WindowInsets insets) {
        this.mSystemWindowInsets = insets.getSystemWindowInsets();
        this.mResolverDrawerLayout.setPadding(this.mSystemWindowInsets.left, this.mSystemWindowInsets.top, this.mSystemWindowInsets.right, 0);
        View emptyView = findViewById(16908292);
        if (emptyView != null) {
            emptyView.setPadding(0, 0, 0, this.mSystemWindowInsets.bottom + (getResources().getDimensionPixelSize(C3132R.dimen.chooser_edge_margin_normal) * 2));
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

    @Override // android.app.Activity, android.content.ComponentCallbacks
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
        mat[4] = 127;
        mat[9] = 127;
        mat[14] = 127;
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(0.0f);
        matrix.preConcat(tempBrightnessMatrix);
        this.mSuspendedMatrixColorFilter = new ColorMatrixColorFilter(matrix);
    }

    public void onSetupVoiceInteraction() {
        sendVoiceChoicesIfNeeded();
    }

    public void sendVoiceChoicesIfNeeded() {
        if (!isVoiceInteraction()) {
            return;
        }
        VoiceInteractor.PickOptionRequest.Option[] options = new VoiceInteractor.PickOptionRequest.Option[this.mAdapter.getCount()];
        int N = options.length;
        for (int i = 0; i < N; i++) {
            options[i] = optionForChooserTarget(this.mAdapter.getItem(i), i);
        }
        this.mPickOptionRequest = new PickTargetOptionRequest(new VoiceInteractor.Prompt(getTitle()), options, null);
        getVoiceInteractor().submitRequest(this.mPickOptionRequest);
    }

    VoiceInteractor.PickOptionRequest.Option optionForChooserTarget(TargetInfo target, int index) {
        return new VoiceInteractor.PickOptionRequest.Option(target.getDisplayLabel(), index);
    }

    protected final void setAdditionalTargets(Intent[] intents) {
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

    protected String getReferrerPackageName() {
        Uri referrer = getReferrer();
        if (referrer != null && "android-app".equals(referrer.getScheme())) {
            return referrer.getHost();
        }
        return null;
    }

    public int getLayoutResource() {
        return C3132R.layout.resolver_list;
    }

    protected void bindProfileView() {
        if (this.mProfileView == null) {
            return;
        }
        DisplayResolveInfo dri = this.mAdapter.getOtherProfile();
        if (dri != null) {
            this.mProfileView.setVisibility(0);
            View text = this.mProfileView.findViewById(C3132R.C3134id.profile_button);
            if (!(text instanceof TextView)) {
                text = this.mProfileView.findViewById(16908308);
            }
            ((TextView) text).setText(dri.getDisplayLabel());
            return;
        }
        this.mProfileView.setVisibility(8);
    }

    private void setProfileSwitchMessageId(int contentUserHint) {
        if (contentUserHint != -2 && contentUserHint != UserHandle.myUserId()) {
            UserManager userManager = (UserManager) getSystemService("user");
            UserInfo originUserInfo = userManager.getUserInfo(contentUserHint);
            boolean originIsManaged = originUserInfo != null ? originUserInfo.isManagedProfile() : false;
            boolean targetIsManaged = userManager.isManagedProfile();
            if (originIsManaged && !targetIsManaged) {
                this.mProfileSwitchMessageId = C3132R.string.forward_intent_to_owner;
            } else if (!originIsManaged && targetIsManaged) {
                this.mProfileSwitchMessageId = C3132R.string.forward_intent_to_work;
            }
        }
    }

    public void setSafeForwardingMode(boolean safeForwarding) {
        this.mSafeForwardingMode = safeForwarding;
    }

    protected CharSequence getTitleForAction(Intent intent, int defaultTitleRes) {
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
        if (!isHttpSchemeAndViewAction(intent)) {
            return named ? getString(title.namedTitleRes, this.mAdapter.getFilteredItem().getDisplayLabel()) : getString(title.titleRes);
        } else if (named && !this.mUseLayoutForBrowsables) {
            String dialogTitle = getString(17041266, this.mAdapter.getFilteredItem().getDisplayLabel());
            return dialogTitle;
        } else if (named && this.mUseLayoutForBrowsables) {
            String dialogTitle2 = getString(17041264, intent.getData().getHost(), this.mAdapter.getFilteredItem().getDisplayLabel());
            return dialogTitle2;
        } else if (this.mAdapter.areAllTargetsBrowsers()) {
            String dialogTitle3 = getString(17041265);
            return dialogTitle3;
        } else {
            String dialogTitle4 = getString(17041263, intent.getData().getHost());
            return dialogTitle4;
        }
    }

    void dismiss() {
        if (!isFinishing()) {
            finish();
        }
    }

    /* loaded from: classes4.dex */
    private static abstract class TargetPresentationGetter {
        private final ApplicationInfo mAi;
        private Context mCtx;
        private final boolean mHasSubstitutePermission;
        private final int mIconDpi;
        protected PackageManager mPm;

        abstract String getAppSubLabelInternal();

        abstract Drawable getIconSubstituteInternal();

        TargetPresentationGetter(Context ctx, int iconDpi, ApplicationInfo ai) {
            this.mCtx = ctx;
            this.mPm = ctx.getPackageManager();
            this.mAi = ai;
            this.mIconDpi = iconDpi;
            this.mHasSubstitutePermission = this.mPm.checkPermission(Manifest.C0000permission.SUBSTITUTE_SHARE_TARGET_APP_NAME_AND_ICON, this.mAi.packageName) == 0;
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
                String label2 = (String) this.mAi.loadLabel(this.mPm);
                return label2;
            }
            return label;
        }

        public String getSubLabel() {
            if (this.mHasSubstitutePermission) {
                return null;
            }
            return getAppSubLabelInternal();
        }

        protected String loadLabelFromResource(Resources res, int resId) {
            return res.getString(resId);
        }

        protected Drawable loadIconFromResource(Resources res, int resId) {
            return res.getDrawableForDensity(resId, this.mIconDpi);
        }
    }

    @VisibleForTesting
    /* loaded from: classes4.dex */
    public static class ResolveInfoPresentationGetter extends ActivityInfoPresentationGetter {
        private final ResolveInfo mRi;

        public ResolveInfoPresentationGetter(Context ctx, int iconDpi, ResolveInfo ri) {
            super(ctx, iconDpi, ri.activityInfo);
            this.mRi = ri;
        }

        @Override // com.android.internal.app.ResolverActivity.ActivityInfoPresentationGetter, com.android.internal.app.ResolverActivity.TargetPresentationGetter
        Drawable getIconSubstituteInternal() {
            Drawable dr = null;
            try {
                if (this.mRi.resolvePackageName != null && this.mRi.icon != 0) {
                    dr = loadIconFromResource(this.mPm.getResourcesForApplication(this.mRi.resolvePackageName), this.mRi.icon);
                }
            } catch (PackageManager.NameNotFoundException e) {
                Log.m69e(ResolverActivity.TAG, "SUBSTITUTE_SHARE_TARGET_APP_NAME_AND_ICON permission granted but couldn't find resources for package", e);
            }
            return dr == null ? super.getIconSubstituteInternal() : dr;
        }

        @Override // com.android.internal.app.ResolverActivity.ActivityInfoPresentationGetter, com.android.internal.app.ResolverActivity.TargetPresentationGetter
        String getAppSubLabelInternal() {
            return (String) this.mRi.loadLabel(this.mPm);
        }
    }

    ResolveInfoPresentationGetter makePresentationGetter(ResolveInfo ri) {
        return new ResolveInfoPresentationGetter(this, this.mIconDpi, ri);
    }

    @VisibleForTesting
    /* loaded from: classes4.dex */
    public static class ActivityInfoPresentationGetter extends TargetPresentationGetter {
        private final ActivityInfo mActivityInfo;

        @Override // com.android.internal.app.ResolverActivity.TargetPresentationGetter
        public /* bridge */ /* synthetic */ Drawable getIcon(UserHandle userHandle) {
            return super.getIcon(userHandle);
        }

        @Override // com.android.internal.app.ResolverActivity.TargetPresentationGetter
        public /* bridge */ /* synthetic */ Bitmap getIconBitmap(UserHandle userHandle) {
            return super.getIconBitmap(userHandle);
        }

        @Override // com.android.internal.app.ResolverActivity.TargetPresentationGetter
        public /* bridge */ /* synthetic */ String getLabel() {
            return super.getLabel();
        }

        @Override // com.android.internal.app.ResolverActivity.TargetPresentationGetter
        public /* bridge */ /* synthetic */ String getSubLabel() {
            return super.getSubLabel();
        }

        public ActivityInfoPresentationGetter(Context ctx, int iconDpi, ActivityInfo activityInfo) {
            super(ctx, iconDpi, activityInfo.applicationInfo);
            this.mActivityInfo = activityInfo;
        }

        @Override // com.android.internal.app.ResolverActivity.TargetPresentationGetter
        Drawable getIconSubstituteInternal() {
            try {
                if (this.mActivityInfo.icon == 0) {
                    return null;
                }
                Drawable dr = loadIconFromResource(this.mPm.getResourcesForApplication(this.mActivityInfo.applicationInfo), this.mActivityInfo.icon);
                return dr;
            } catch (PackageManager.NameNotFoundException e) {
                Log.m69e(ResolverActivity.TAG, "SUBSTITUTE_SHARE_TARGET_APP_NAME_AND_ICON permission granted but couldn't find resources for package", e);
                return null;
            }
        }

        @Override // com.android.internal.app.ResolverActivity.TargetPresentationGetter
        String getAppSubLabelInternal() {
            return (String) this.mActivityInfo.loadLabel(this.mPm);
        }
    }

    protected ActivityInfoPresentationGetter makePresentationGetter(ActivityInfo ai) {
        return new ActivityInfoPresentationGetter(this, this.mIconDpi, ai);
    }

    Drawable loadIconForResolveInfo(ResolveInfo ri) {
        return makePresentationGetter(ri).getIcon(Process.myUserHandle());
    }

    @Override // android.app.Activity
    protected void onRestart() {
        super.onRestart();
        if (!this.mRegistered) {
            this.mPackageMonitor.register(this, getMainLooper(), false);
            this.mRegistered = true;
        }
        this.mAdapter.handlePackagesChanged();
        bindProfileView();
    }

    @Override // android.app.Activity
    protected void onStop() {
        super.onStop();
        if (this.mRegistered) {
            this.mPackageMonitor.unregister();
            this.mRegistered = false;
        }
        Intent intent = getIntent();
        if ((intent.getFlags() & 268435456) != 0 && !isVoiceInteraction() && !this.mResolvingHome && !this.mRetainInOnStop && !isChangingConfigurations()) {
            finish();
        }
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        if (!isChangingConfigurations() && this.mPickOptionRequest != null) {
            this.mPickOptionRequest.cancel();
        }
        if (this.mPostListReadyRunnable != null) {
            getMainThreadHandler().removeCallbacks(this.mPostListReadyRunnable);
            this.mPostListReadyRunnable = null;
        }
        if (this.mAdapter == null || this.mAdapter.mResolverListController == null) {
            return;
        }
        this.mAdapter.mResolverListController.destroy();
    }

    @Override // android.app.Activity
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
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
            List<UserInfo> profiles = userManager.getProfiles(getUserId());
            for (UserInfo userInfo : profiles) {
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
            ApplicationInfo appInfo = getPackageManager().getApplicationInfo(resolveInfo.activityInfo.packageName, 0);
            return appInfo.targetSdkVersion >= 21;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setAlwaysButtonEnabled(boolean hasValidSelection, int checkedPos, boolean filtered) {
        boolean enabled = false;
        if (hasValidSelection) {
            ResolveInfo ri = this.mAdapter.resolveInfoForPosition(checkedPos, filtered);
            if (ri == null) {
                Log.m70e(TAG, "Invalid position supplied to setAlwaysButtonEnabled");
                return;
            } else if (ri.targetUserId != -2) {
                Log.m70e(TAG, "Attempted to set selection to resolve info for another user");
                return;
            } else {
                enabled = true;
                if (this.mUseLayoutForBrowsables && !ri.handleAllWebDataURI) {
                    this.mAlwaysButton.setText(getResources().getString(C3132R.string.activity_resolver_set_always));
                } else {
                    this.mAlwaysButton.setText(getResources().getString(C3132R.string.activity_resolver_use_always));
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
        boolean hasIndexBeenFiltered = !this.mAdapter.hasFilteredItem();
        ResolveInfo ri = this.mAdapter.resolveInfoForPosition(which, hasIndexBeenFiltered);
        if (this.mUseLayoutForBrowsables && !ri.handleAllWebDataURI && id == 16908779) {
            showSettingsForSelected(ri);
        } else {
            startSelected(which, id == 16908779, hasIndexBeenFiltered);
        }
    }

    private void showSettingsForSelected(ResolveInfo ri) {
        Intent intent = new Intent();
        String packageName = ri.activityInfo.packageName;
        Bundle showFragmentArgs = new Bundle();
        showFragmentArgs.putString(EXTRA_FRAGMENT_ARG_KEY, OPEN_LINKS_COMPONENT_KEY);
        showFragmentArgs.putString("package", packageName);
        intent.setAction(Settings.ACTION_APP_OPEN_BY_DEFAULT_SETTINGS).setData(Uri.fromParts("package", packageName, null)).addFlags(524288).putExtra(EXTRA_FRAGMENT_ARG_KEY, OPEN_LINKS_COMPONENT_KEY).putExtra(EXTRA_SHOW_FRAGMENT_ARGS, showFragmentArgs);
        startActivity(intent);
    }

    public void startSelected(int which, boolean always, boolean hasIndexBeenFiltered) {
        int i;
        if (isFinishing()) {
            return;
        }
        ResolveInfo ri = this.mAdapter.resolveInfoForPosition(which, hasIndexBeenFiltered);
        if (this.mResolvingHome && hasManagedProfile() && !supportsManagedProfiles(ri)) {
            Toast.makeText(this, String.format(getResources().getString(C3132R.string.activity_resolver_work_profiles_support), ri.activityInfo.loadLabel(getPackageManager()).toString()), 1).show();
            return;
        }
        TargetInfo target = this.mAdapter.targetInfoForPosition(which, hasIndexBeenFiltered);
        if (target != null && onTargetSelected(target, always)) {
            if (always && this.mSupportsAlwaysUseOption) {
                MetricsLogger.action(this, (int) MetricsProto.MetricsEvent.ACTION_APP_DISAMBIG_ALWAYS);
            } else if (this.mSupportsAlwaysUseOption) {
                MetricsLogger.action(this, (int) MetricsProto.MetricsEvent.ACTION_APP_DISAMBIG_JUST_ONCE);
            } else {
                MetricsLogger.action(this, (int) MetricsProto.MetricsEvent.ACTION_APP_DISAMBIG_TAP);
            }
            if (this.mAdapter.hasFilteredItem()) {
                i = MetricsProto.MetricsEvent.ACTION_HIDE_APP_DISAMBIG_APP_FEATURED;
            } else {
                i = MetricsProto.MetricsEvent.ACTION_HIDE_APP_DISAMBIG_NONE_FEATURED;
            }
            MetricsLogger.action(this, i);
            finish();
        }
    }

    public Intent getReplacementIntent(ActivityInfo aInfo, Intent defIntent) {
        return defIntent;
    }

    /* JADX WARN: Removed duplicated region for block: B:124:0x021d  */
    /* JADX WARN: Removed duplicated region for block: B:127:0x0229 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:130:0x022e A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:141:0x0263  */
    /* JADX WARN: Removed duplicated region for block: B:145:0x026e A[ORIG_RETURN, RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected boolean onTargetSelected(TargetInfo target, boolean alwaysCheck) {
        Intent filterIntent;
        ComponentName[] set;
        boolean isHttpOrHttps;
        boolean isViewAction;
        boolean hasCategoryBrowsable;
        String mimeType;
        ResolveInfo ri = target.getResolveInfo();
        Intent intent = target != null ? target.getResolvedIntent() : null;
        if (intent != null) {
            if (this.mSupportsAlwaysUseOption || this.mAdapter.hasFilteredItem()) {
                if (this.mAdapter.mUnfilteredResolveList != null) {
                    IntentFilter filter = new IntentFilter();
                    if (intent.getSelector() != null) {
                        filterIntent = intent.getSelector();
                    } else {
                        filterIntent = intent;
                    }
                    Intent filterIntent2 = filterIntent;
                    String action = filterIntent2.getAction();
                    if (action != null) {
                        filter.addAction(action);
                    }
                    Set<String> categories = filterIntent2.getCategories();
                    if (categories != null) {
                        for (String cat : categories) {
                            filter.addCategory(cat);
                        }
                    }
                    filter.addCategory(Intent.CATEGORY_DEFAULT);
                    int cat2 = 268369920 & ri.match;
                    Uri data = filterIntent2.getData();
                    if (cat2 == 6291456 && (mimeType = filterIntent2.resolveType(this)) != null) {
                        try {
                            filter.addDataType(mimeType);
                        } catch (IntentFilter.MalformedMimeTypeException e) {
                            Log.m62w(TAG, e);
                            filter = null;
                        }
                    }
                    if (data != null && data.getScheme() != null && (cat2 != 6291456 || (!ContentResolver.SCHEME_FILE.equals(data.getScheme()) && !"content".equals(data.getScheme())))) {
                        filter.addDataScheme(data.getScheme());
                        Iterator<PatternMatcher> pIt = ri.filter.schemeSpecificPartsIterator();
                        if (pIt != null) {
                            String ssp = data.getSchemeSpecificPart();
                            while (true) {
                                if (ssp == null || !pIt.hasNext()) {
                                    break;
                                }
                                PatternMatcher p = pIt.next();
                                if (p.match(ssp)) {
                                    filter.addDataSchemeSpecificPart(p.getPath(), p.getType());
                                    break;
                                }
                            }
                        }
                        Iterator<IntentFilter.AuthorityEntry> aIt = ri.filter.authoritiesIterator();
                        if (aIt != null) {
                            while (true) {
                                if (!aIt.hasNext()) {
                                    break;
                                }
                                IntentFilter.AuthorityEntry a = aIt.next();
                                if (a.match(data) >= 0) {
                                    int port = a.getPort();
                                    filter.addDataAuthority(a.getHost(), port >= 0 ? Integer.toString(port) : null);
                                }
                            }
                        }
                        Iterator<PatternMatcher> pIt2 = ri.filter.pathsIterator();
                        if (pIt2 != null) {
                            String path = data.getPath();
                            while (true) {
                                if (path == null || !pIt2.hasNext()) {
                                    break;
                                }
                                PatternMatcher p2 = pIt2.next();
                                if (p2.match(path)) {
                                    filter.addDataPath(p2.getPath(), p2.getType());
                                    break;
                                }
                            }
                        }
                    }
                    if (filter != null) {
                        int N = this.mAdapter.mUnfilteredResolveList.size();
                        boolean needToAddBackProfileForwardingComponent = this.mAdapter.mOtherProfile != null;
                        if (!needToAddBackProfileForwardingComponent) {
                            set = new ComponentName[N];
                        } else {
                            set = new ComponentName[N + 1];
                        }
                        ComponentName[] set2 = set;
                        int bestMatch = 0;
                        int bestMatch2 = 0;
                        while (bestMatch2 < N) {
                            Intent filterIntent3 = filterIntent2;
                            ResolveInfo r = this.mAdapter.mUnfilteredResolveList.get(bestMatch2).getResolveInfoAt(0);
                            int cat3 = cat2;
                            set2[bestMatch2] = new ComponentName(r.activityInfo.packageName, r.activityInfo.name);
                            if (r.match > bestMatch) {
                                bestMatch = r.match;
                            }
                            bestMatch2++;
                            filterIntent2 = filterIntent3;
                            cat2 = cat3;
                        }
                        if (needToAddBackProfileForwardingComponent) {
                            set2[N] = this.mAdapter.mOtherProfile.getResolvedComponentName();
                            int otherProfileMatch = this.mAdapter.mOtherProfile.getResolveInfo().match;
                            if (otherProfileMatch > bestMatch) {
                                bestMatch = otherProfileMatch;
                            }
                        }
                        if (alwaysCheck) {
                            int userId = getUserId();
                            PackageManager pm = getPackageManager();
                            pm.addPreferredActivity(filter, bestMatch, set2, intent.getComponent());
                            if (ri.handleAllWebDataURI) {
                                String packageName = pm.getDefaultBrowserPackageNameAsUser(userId);
                                if (TextUtils.isEmpty(packageName)) {
                                    pm.setDefaultBrowserPackageNameAsUser(ri.activityInfo.packageName, userId);
                                }
                            } else {
                                ComponentName cn = intent.getComponent();
                                String packageName2 = cn.getPackageName();
                                String dataScheme = data != null ? data.getScheme() : null;
                                if (dataScheme != null && (dataScheme.equals(IntentFilter.SCHEME_HTTP) || dataScheme.equals(IntentFilter.SCHEME_HTTPS))) {
                                    isHttpOrHttps = true;
                                    if (action != null && action.equals("android.intent.action.VIEW")) {
                                        isViewAction = true;
                                        if (categories != null && categories.contains(Intent.CATEGORY_BROWSABLE)) {
                                            hasCategoryBrowsable = true;
                                            if (isHttpOrHttps && isViewAction && hasCategoryBrowsable) {
                                                pm.updateIntentVerificationStatusAsUser(packageName2, 2, userId);
                                            }
                                        }
                                        hasCategoryBrowsable = false;
                                        if (isHttpOrHttps) {
                                            pm.updateIntentVerificationStatusAsUser(packageName2, 2, userId);
                                        }
                                    }
                                    isViewAction = false;
                                    if (categories != null) {
                                        hasCategoryBrowsable = true;
                                        if (isHttpOrHttps) {
                                        }
                                    }
                                    hasCategoryBrowsable = false;
                                    if (isHttpOrHttps) {
                                    }
                                }
                                isHttpOrHttps = false;
                                if (action != null) {
                                    isViewAction = true;
                                    if (categories != null) {
                                    }
                                    hasCategoryBrowsable = false;
                                    if (isHttpOrHttps) {
                                    }
                                }
                                isViewAction = false;
                                if (categories != null) {
                                }
                                hasCategoryBrowsable = false;
                                if (isHttpOrHttps) {
                                }
                            }
                        } else {
                            try {
                                this.mAdapter.mResolverListController.setLastChosen(intent, filter, bestMatch);
                            } catch (RemoteException re) {
                                Log.m72d(TAG, "Error calling setLastChosenActivity\n" + re);
                            }
                        }
                    }
                }
            }
            if (target == null) {
                safelyStartActivity(target);
                if (target.isSuspended()) {
                    return false;
                }
                return true;
            }
            return true;
        }
        if (target == null) {
        }
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
            Toast.makeText(this, getString(this.mProfileSwitchMessageId), 1).show();
        }
        if (!this.mSafeForwardingMode) {
            if (cti.start(this, null)) {
                onActivityStarted(cti);
                return;
            }
            return;
        }
        try {
            if (cti.startAsCaller(this, null, -10000)) {
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
    }

    boolean startAsCallerImpl(Intent intent, Bundle options, boolean ignoreTargetSecurity, int userId) {
        try {
            IBinder permissionToken = ActivityTaskManager.getService().requestStartActivityPermissionToken(getActivityToken());
            Intent chooserIntent = new Intent();
            ComponentName delegateActivity = ComponentName.unflattenFromString(Resources.getSystem().getString(C3132R.string.config_chooserActivity));
            chooserIntent.setClassName(delegateActivity.getPackageName(), delegateActivity.getClassName());
            chooserIntent.putExtra(ActivityTaskManager.EXTRA_PERMISSION_TOKEN, permissionToken);
            chooserIntent.putExtra(Intent.EXTRA_INTENT, intent);
            chooserIntent.putExtra(ActivityTaskManager.EXTRA_OPTIONS, options);
            chooserIntent.putExtra(ActivityTaskManager.EXTRA_IGNORE_TARGET_SECURITY, ignoreTargetSecurity);
            chooserIntent.putExtra(Intent.EXTRA_USER_ID, userId);
            chooserIntent.addFlags(View.SCROLLBARS_OUTSIDE_INSET);
            startActivity(chooserIntent);
            return true;
        } catch (RemoteException e) {
            Log.m70e(TAG, e.toString());
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
        Intent in = new Intent().setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).setData(Uri.fromParts("package", ri.activityInfo.packageName, null)).addFlags(524288);
        startActivity(in);
    }

    public ResolveListAdapter createAdapter(Context context, List<Intent> payloadIntents, Intent[] initialIntents, List<ResolveInfo> rList, int launchedFromUid, boolean filterLastUsed) {
        return new ResolveListAdapter(context, payloadIntents, initialIntents, rList, launchedFromUid, filterLastUsed, createListController());
    }

    @VisibleForTesting
    protected ResolverListController createListController() {
        return new ResolverListController(this, this.mPm, getTargetIntent(), getReferrerPackageName(), this.mLaunchedFromUid);
    }

    public boolean configureContentView(List<Intent> payloadIntents, Intent[] initialIntents, List<ResolveInfo> rList) {
        this.mAdapter = createAdapter(this, payloadIntents, initialIntents, rList, this.mLaunchedFromUid, this.mSupportsAlwaysUseOption && !isVoiceInteraction());
        boolean rebuildCompleted = this.mAdapter.rebuildList();
        if (useLayoutWithDefault()) {
            this.mLayoutId = C3132R.layout.resolver_list_with_default;
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
        this.mAdapterView = (AbsListView) findViewById(C3132R.C3134id.resolver_list);
        if (count == 0 && this.mAdapter.mPlaceholderCount == 0) {
            TextView emptyView = (TextView) findViewById(16908292);
            emptyView.setVisibility(0);
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
        adapterView.setAdapter(this.mAdapter);
        ItemClickListener listener = new ItemClickListener();
        adapterView.setOnItemClickListener(listener);
        adapterView.setOnItemLongClickListener(listener);
        if (this.mSupportsAlwaysUseOption || this.mUseLayoutForBrowsables) {
            listView.setChoiceMode(1);
        }
        if (useHeader && listView != null && listView.getHeaderViewsCount() == 0) {
            listView.addHeaderView(LayoutInflater.from(this).inflate(C3132R.layout.resolver_different_item_header, (ViewGroup) listView, false));
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
            new LoadIconTask(iconInfo, iconView).execute(new Void[0]);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resetButtonBar() {
        if (!this.mSupportsAlwaysUseOption && !this.mUseLayoutForBrowsables) {
            return;
        }
        ViewGroup buttonLayout = (ViewGroup) findViewById(C3132R.C3134id.button_bar);
        if (buttonLayout != null) {
            buttonLayout.setVisibility(0);
            int inset = this.mSystemWindowInsets != null ? this.mSystemWindowInsets.bottom : 0;
            buttonLayout.setPadding(buttonLayout.getPaddingLeft(), buttonLayout.getPaddingTop(), buttonLayout.getPaddingRight(), getResources().getDimensionPixelSize(C3132R.dimen.resolver_button_bar_spacing) + inset);
            this.mOnceButton = (Button) buttonLayout.findViewById(C3132R.C3134id.button_once);
            this.mAlwaysButton = (Button) buttonLayout.findViewById(C3132R.C3134id.button_always);
            resetAlwaysOrOnceButtonBar();
            return;
        }
        Log.m70e(TAG, "Layout unexpectedly does not have a button bar");
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

    /* JADX INFO: Access modifiers changed from: private */
    public boolean useLayoutWithDefault() {
        return this.mSupportsAlwaysUseOption && this.mAdapter.hasFilteredItem();
    }

    protected void setRetainInOnStop(boolean retainInOnStop) {
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

    /* loaded from: classes4.dex */
    public final class DisplayResolveInfo implements TargetInfo {
        private Drawable mBadge;
        private Drawable mDisplayIcon;
        private final CharSequence mDisplayLabel;
        private final CharSequence mExtendedInfo;
        private boolean mIsSuspended;
        private final ResolveInfo mResolveInfo;
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

        @Override // com.android.internal.app.ResolverActivity.TargetInfo
        public ResolveInfo getResolveInfo() {
            return this.mResolveInfo;
        }

        @Override // com.android.internal.app.ResolverActivity.TargetInfo
        public CharSequence getDisplayLabel() {
            return this.mDisplayLabel;
        }

        @Override // com.android.internal.app.ResolverActivity.TargetInfo
        public Drawable getDisplayIcon() {
            return this.mDisplayIcon;
        }

        @Override // com.android.internal.app.ResolverActivity.TargetInfo
        public TargetInfo cloneFilledIn(Intent fillInIntent, int flags) {
            return new DisplayResolveInfo(this, fillInIntent, flags);
        }

        @Override // com.android.internal.app.ResolverActivity.TargetInfo
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

        @Override // com.android.internal.app.ResolverActivity.TargetInfo
        public CharSequence getExtendedInfo() {
            return this.mExtendedInfo;
        }

        @Override // com.android.internal.app.ResolverActivity.TargetInfo
        public Intent getResolvedIntent() {
            return this.mResolvedIntent;
        }

        @Override // com.android.internal.app.ResolverActivity.TargetInfo
        public ComponentName getResolvedComponentName() {
            return new ComponentName(this.mResolveInfo.activityInfo.packageName, this.mResolveInfo.activityInfo.name);
        }

        @Override // com.android.internal.app.ResolverActivity.TargetInfo
        public boolean start(Activity activity, Bundle options) {
            activity.startActivity(this.mResolvedIntent, options);
            return true;
        }

        @Override // com.android.internal.app.ResolverActivity.TargetInfo
        public boolean startAsCaller(ResolverActivity activity, Bundle options, int userId) {
            if (ResolverActivity.this.mEnableChooserDelegate) {
                return activity.startAsCallerImpl(this.mResolvedIntent, options, false, userId);
            }
            activity.startActivityAsCaller(this.mResolvedIntent, options, null, false, userId);
            return true;
        }

        @Override // com.android.internal.app.ResolverActivity.TargetInfo
        public boolean startAsUser(Activity activity, Bundle options, UserHandle user) {
            activity.startActivityAsUser(this.mResolvedIntent, options, user);
            return false;
        }

        @Override // com.android.internal.app.ResolverActivity.TargetInfo
        public boolean isSuspended() {
            return this.mIsSuspended;
        }
    }

    List<DisplayResolveInfo> getDisplayList() {
        return this.mAdapter.mDisplayList;
    }

    /* loaded from: classes4.dex */
    public class ResolveListAdapter extends BaseAdapter {
        private final List<ResolveInfo> mBaseResolveList;
        List<DisplayResolveInfo> mDisplayList;
        private boolean mFilterLastUsed;
        protected final LayoutInflater mInflater;
        private final Intent[] mInitialIntents;
        private final List<Intent> mIntents;
        protected ResolveInfo mLastChosen;
        private DisplayResolveInfo mOtherProfile;
        private int mPlaceholderCount;
        private ResolverListController mResolverListController;
        List<ResolvedComponentInfo> mUnfilteredResolveList;
        private boolean mAllTargetsAreBrowsers = false;
        private int mLastChosenPosition = -1;

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
            if (this.mFilterLastUsed && this.mLastChosenPosition >= 0) {
                return this.mDisplayList.get(this.mLastChosenPosition);
            }
            return null;
        }

        public DisplayResolveInfo getOtherProfile() {
            return this.mOtherProfile;
        }

        public int getFilteredPosition() {
            if (this.mFilterLastUsed && this.mLastChosenPosition >= 0) {
                return this.mLastChosenPosition;
            }
            return -1;
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

        protected boolean rebuildList() {
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
                    Log.m72d(ResolverActivity.TAG, "Error calling getLastChosenActivity\n" + re);
                }
            }
            if (currentResolveList != null && currentResolveList.size() > 0) {
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
                    AsyncTask<List<ResolvedComponentInfo>, Void, List<ResolvedComponentInfo>> sortingTask = new AsyncTask<List<ResolvedComponentInfo>, Void, List<ResolvedComponentInfo>>() { // from class: com.android.internal.app.ResolverActivity.ResolveListAdapter.1
                        /* JADX INFO: Access modifiers changed from: protected */
                        @Override // android.p007os.AsyncTask
                        public List<ResolvedComponentInfo> doInBackground(List<ResolvedComponentInfo>... params) {
                            ResolveListAdapter.this.mResolverListController.sort(params[0]);
                            return params[0];
                        }

                        /* JADX INFO: Access modifiers changed from: protected */
                        @Override // android.p007os.AsyncTask
                        public void onPostExecute(List<ResolvedComponentInfo> sortedComponents) {
                            ResolveListAdapter.this.processSortedList(sortedComponents);
                            ResolverActivity.this.bindProfileView();
                            ResolveListAdapter.this.notifyDataSetChanged();
                        }
                    };
                    sortingTask.execute(currentResolveList);
                    postListReadyRunnable();
                    return false;
                }
                processSortedList(currentResolveList);
                return true;
            }
            processSortedList(currentResolveList);
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void processSortedList(List<ResolvedComponentInfo> sortedComponents) {
            if (sortedComponents != null && sortedComponents.size() != 0) {
                this.mAllTargetsAreBrowsers = ResolverActivity.this.mUseLayoutForBrowsables;
                int i = 0;
                if (this.mInitialIntents != null) {
                    int i2 = 0;
                    while (i2 < this.mInitialIntents.length) {
                        Intent ii = this.mInitialIntents[i2];
                        if (ii != null) {
                            ActivityInfo ai = ii.resolveActivityInfo(ResolverActivity.this.getPackageManager(), i);
                            if (ai == null) {
                                Log.m64w(ResolverActivity.TAG, "No activity found for " + ii);
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
                                    ri.icon = i;
                                }
                                this.mAllTargetsAreBrowsers &= ri.handleAllWebDataURI;
                                addResolveInfo(new DisplayResolveInfo(ii, ri, ri.loadLabel(ResolverActivity.this.getPackageManager()), null, ii));
                            }
                        }
                        i2++;
                        i = 0;
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
            postListReadyRunnable();
        }

        private void postListReadyRunnable() {
            if (ResolverActivity.this.mPostListReadyRunnable == null) {
                ResolverActivity.this.mPostListReadyRunnable = new Runnable() { // from class: com.android.internal.app.ResolverActivity.ResolveListAdapter.2
                    @Override // java.lang.Runnable
                    public void run() {
                        ResolverActivity.this.setHeader();
                        ResolverActivity.this.resetButtonBar();
                        ResolveListAdapter.this.onListRebuilt();
                        ResolverActivity.this.mPostListReadyRunnable = null;
                    }
                };
                ResolverActivity.this.getMainThreadHandler().post(ResolverActivity.this.mPostListReadyRunnable);
            }
        }

        public void onListRebuilt() {
            int count = getUnfilteredCount();
            if (count == 1 && getOtherProfile() == null) {
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
                for (int i = 1; i < count; i++) {
                    Intent altIntent = rci.getIntentAt(i);
                    dri.addAlternateSourceIntent(altIntent);
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

        @Override // android.widget.Adapter
        public int getCount() {
            int totalSize;
            if (this.mDisplayList == null || this.mDisplayList.isEmpty()) {
                totalSize = this.mPlaceholderCount;
            } else {
                totalSize = this.mDisplayList.size();
            }
            if (this.mFilterLastUsed && this.mLastChosenPosition >= 0) {
                return totalSize - 1;
            }
            return totalSize;
        }

        public int getUnfilteredCount() {
            return this.mDisplayList.size();
        }

        @Override // android.widget.Adapter
        public TargetInfo getItem(int position) {
            if (this.mFilterLastUsed && this.mLastChosenPosition >= 0 && position >= this.mLastChosenPosition) {
                position++;
            }
            if (this.mDisplayList.size() > position) {
                return this.mDisplayList.get(position);
            }
            return null;
        }

        @Override // android.widget.Adapter
        public long getItemId(int position) {
            return position;
        }

        public int getDisplayResolveInfoCount() {
            return this.mDisplayList.size();
        }

        public DisplayResolveInfo getDisplayResolveInfo(int index) {
            return this.mDisplayList.get(index);
        }

        @Override // android.widget.Adapter
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
            ViewHolder holder = new ViewHolder(view);
            view.setTag(holder);
            return view;
        }

        public View onCreateView(ViewGroup parent) {
            return this.mInflater.inflate(C3132R.layout.resolve_list_item, parent, false);
        }

        public final void bindView(int position, View view) {
            onBindView(view, getItem(position));
        }

        protected void onBindView(View view, TargetInfo info) {
            ViewHolder holder = (ViewHolder) view.getTag();
            if (info == null) {
                holder.icon.setImageDrawable(ResolverActivity.this.getDrawable(C3132R.C3133drawable.resolver_icon_placeholder));
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
                holder.icon.setColorFilter(ResolverActivity.this.mSuspendedMatrixColorFilter);
            } else {
                holder.icon.setColorFilter((ColorFilter) null);
            }
            if ((info instanceof DisplayResolveInfo) && !((DisplayResolveInfo) info).hasDisplayIcon()) {
                new LoadIconTask((DisplayResolveInfo) info, holder.icon).execute(new Void[0]);
            } else {
                holder.icon.setImageDrawable(info.getDisplayIcon());
            }
        }
    }

    @VisibleForTesting
    /* loaded from: classes4.dex */
    public static final class ResolvedComponentInfo {
        private final List<Intent> mIntents = new ArrayList();
        private final List<ResolveInfo> mResolveInfos = new ArrayList();
        public final ComponentName name;

        public ResolvedComponentInfo(ComponentName name, Intent intent, ResolveInfo info) {
            this.name = name;
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

    /* loaded from: classes4.dex */
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

    /* loaded from: classes4.dex */
    class ItemClickListener implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
        ItemClickListener() {
        }

        @Override // android.widget.AdapterView.OnItemClickListener
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            ListView listView = parent instanceof ListView ? (ListView) parent : null;
            if (listView != null) {
                position -= listView.getHeaderViewsCount();
            }
            if (position < 0 || ResolverActivity.this.mAdapter.resolveInfoForPosition(position, true) == null) {
                return;
            }
            int checkedPos = ResolverActivity.this.mAdapterView.getCheckedItemPosition();
            boolean hasValidSelection = checkedPos != -1;
            if (!ResolverActivity.this.useLayoutWithDefault() && ((!hasValidSelection || ResolverActivity.this.mLastSelected != checkedPos) && ResolverActivity.this.mAlwaysButton != null)) {
                ResolverActivity.this.setAlwaysButtonEnabled(hasValidSelection, checkedPos, true);
                ResolverActivity.this.mOnceButton.setEnabled(hasValidSelection);
                if (hasValidSelection) {
                    ResolverActivity.this.mAdapterView.smoothScrollToPosition(checkedPos);
                }
                ResolverActivity.this.mLastSelected = checkedPos;
                return;
            }
            ResolverActivity.this.startSelected(position, false, true);
        }

        @Override // android.widget.AdapterView.OnItemLongClickListener
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            ListView listView = parent instanceof ListView ? (ListView) parent : null;
            if (listView != null) {
                position -= listView.getHeaderViewsCount();
            }
            if (position < 0) {
                return false;
            }
            ResolveInfo ri = ResolverActivity.this.mAdapter.resolveInfoForPosition(position, true);
            ResolverActivity.this.showTargetDetails(ri);
            return true;
        }
    }

    /* loaded from: classes4.dex */
    class LoadIconTask extends AsyncTask<Void, Void, Drawable> {
        protected final DisplayResolveInfo mDisplayResolveInfo;
        private final ResolveInfo mResolveInfo;
        private final ImageView mTargetView;

        LoadIconTask(DisplayResolveInfo dri, ImageView target) {
            this.mDisplayResolveInfo = dri;
            this.mResolveInfo = dri.getResolveInfo();
            this.mTargetView = target;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.p007os.AsyncTask
        public Drawable doInBackground(Void... params) {
            return ResolverActivity.this.loadIconForResolveInfo(this.mResolveInfo);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.p007os.AsyncTask
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

    /* loaded from: classes4.dex */
    static class PickTargetOptionRequest extends VoiceInteractor.PickOptionRequest {
        public PickTargetOptionRequest(VoiceInteractor.Prompt prompt, VoiceInteractor.PickOptionRequest.Option[] options, Bundle extras) {
            super(prompt, options, extras);
        }

        @Override // android.app.VoiceInteractor.Request
        public void onCancel() {
            super.onCancel();
            ResolverActivity ra = (ResolverActivity) getActivity();
            if (ra != null) {
                ra.mPickOptionRequest = null;
                ra.finish();
            }
        }

        @Override // android.app.VoiceInteractor.PickOptionRequest
        public void onPickOptionResult(boolean finished, VoiceInteractor.PickOptionRequest.Option[] selections, Bundle result) {
            ResolverActivity ra;
            super.onPickOptionResult(finished, selections, result);
            if (selections.length == 1 && (ra = (ResolverActivity) getActivity()) != null) {
                TargetInfo ti = ra.mAdapter.getItem(selections[0].getIndex());
                if (ra.onTargetSelected(ti, false)) {
                    ra.mPickOptionRequest = null;
                    ra.finish();
                }
            }
        }
    }
}
