package com.android.internal.app;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.job.JobInfo;
import android.app.prediction.AppPredictionContext;
import android.app.prediction.AppPredictionManager;
import android.app.prediction.AppPredictor;
import android.app.prediction.AppTarget;
import android.app.prediction.AppTargetEvent;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.LabeledIntent;
import android.content.pm.LauncherApps;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.metrics.LogMaker;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Parcelable;
import android.os.Process;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.DeviceConfig;
import android.service.chooser.ChooserTarget;
import android.service.chooser.ChooserTargetService;
import android.service.chooser.IChooserTargetResult;
import android.service.chooser.IChooserTargetService;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.HashedStringCache;
import android.util.Log;
import android.util.Size;
import android.util.Slog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.internal.R;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.app.AbstractResolverComparator;
import com.android.internal.app.ChooserActivity;
import com.android.internal.app.ResolverActivity;
import com.android.internal.config.sysui.SystemUiDeviceConfigFlags;
import com.android.internal.content.PackageMonitor;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.nano.MetricsProto;
import com.android.internal.util.ImageUtils;
import com.android.internal.widget.MessagingMessage;
import com.android.internal.widget.ResolverDrawerLayout;
import com.google.android.collect.Lists;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ChooserActivity extends ResolverActivity {
    public static final String APP_PREDICTION_INTENT_FILTER_KEY = "intent_filter";
    private static final int APP_PREDICTION_SHARE_TARGET_QUERY_PACKAGE_LIMIT = 20;
    private static final String APP_PREDICTION_SHARE_UI_SURFACE = "share";
    private static final float CALLER_TARGET_SCORE_BOOST = 900.0f;
    private static final int CONTENT_PREVIEW_FILE = 2;
    private static final int CONTENT_PREVIEW_IMAGE = 1;
    private static final int CONTENT_PREVIEW_TEXT = 3;
    private static final boolean DEBUG = false;
    private static final int DEFAULT_SALT_EXPIRATION_DAYS = 7;
    private static final float DIRECT_SHARE_EXPANSION_RATE = 0.78f;
    public static final String EXTRA_PRIVATE_RETAIN_IN_ON_STOP = "com.android.internal.app.ChooserActivity.EXTRA_PRIVATE_RETAIN_IN_ON_STOP";
    public static final String LAUNCH_LOCATON_DIRECT_SHARE = "direct_share";
    @VisibleForTesting
    public static final int LIST_VIEW_UPDATE_INTERVAL_IN_MILLIS = 250;
    private static final int MAX_EXTRA_CHOOSER_TARGETS = 2;
    private static final int MAX_EXTRA_INITIAL_INTENTS = 2;
    private static final int MAX_LOG_RANK_POSITION = 12;
    private static final int MAX_RANKED_TARGETS = 4;
    private static final int NO_DIRECT_SHARE_ANIM_IN_MILLIS = 200;
    private static final String PREF_NUM_SHEET_EXPANSIONS = "pref_num_sheet_expansions";
    private static final int QUERY_TARGET_SERVICE_LIMIT = 5;
    private static final int SHARE_TARGET_QUERY_PACKAGE_LIMIT = 20;
    private static final float SHORTCUT_TARGET_SCORE_BOOST = 90.0f;
    private static final String TAG = "ChooserActivity";
    private static final String TARGET_DETAILS_FRAGMENT_TAG = "targetDetailsFragment";
    private static final boolean USE_CHOOSER_TARGET_SERVICE_FOR_DIRECT_TARGETS = true;
    private static final boolean USE_PREDICTION_MANAGER_FOR_DIRECT_TARGETS = true;
    private static final boolean USE_PREDICTION_MANAGER_FOR_SHARE_ACTIVITIES = true;
    private static final boolean USE_SHORTCUT_MANAGER_FOR_DIRECT_TARGETS = true;
    private AppPredictor mAppPredictor;
    private AppPredictor.Callback mAppPredictorCallback;
    private ChooserTarget[] mCallerChooserTargets;
    /* access modifiers changed from: private */
    public final ChooserHandler mChooserHandler = new ChooserHandler();
    /* access modifiers changed from: private */
    public ChooserListAdapter mChooserListAdapter;
    /* access modifiers changed from: private */
    public ChooserRowAdapter mChooserRowAdapter;
    private int mChooserRowServiceSpacing;
    private long mChooserShownTime;
    private IntentSender mChosenComponentSender;
    private int mCurrAvailableWidth = 0;
    private Map<ChooserTarget, AppTarget> mDirectShareAppTargetCache;
    /* access modifiers changed from: private */
    public ComponentName[] mFilteredComponentNames;
    protected boolean mIsSuccessfullySelected;
    /* access modifiers changed from: private */
    public boolean mListViewDataChanged = false;
    private int mMaxHashSaltDays = DeviceConfig.getInt(DeviceConfig.NAMESPACE_SYSTEMUI, SystemUiDeviceConfigFlags.HASH_SALT_MAX_DAYS, 7);
    protected MetricsLogger mMetricsLogger;
    private ContentPreviewCoordinator mPreviewCoord;
    private long mQueriedSharingShortcutsTimeMs;
    private long mQueriedTargetServicesTimeMs;
    /* access modifiers changed from: private */
    public Intent mReferrerFillInIntent;
    private IntentSender mRefinementIntentSender;
    private RefinementResultReceiver mRefinementResultReceiver;
    private Bundle mReplacementExtras;
    /* access modifiers changed from: private */
    public final List<ChooserTargetServiceConnection> mServiceConnections = new ArrayList();
    /* access modifiers changed from: private */
    public final Set<ComponentName> mServicesRequested = new HashSet();
    /* access modifiers changed from: private */
    public List<ResolverActivity.DisplayResolveInfo> mSortedList = new ArrayList();

    @Retention(RetentionPolicy.SOURCE)
    private @interface ContentPreviewType {
    }

    private class ContentPreviewCoordinator {
        private static final int IMAGE_FADE_IN_MILLIS = 150;
        private static final int IMAGE_LOAD_INTO_VIEW = 2;
        private static final int IMAGE_LOAD_TIMEOUT = 1;
        /* access modifiers changed from: private */
        public boolean mAtLeastOneLoaded = false;
        private final Handler mHandler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        ContentPreviewCoordinator.this.maybeHideContentPreview();
                        return;
                    case 2:
                        if (!ChooserActivity.this.isFinishing()) {
                            LoadUriTask task = (LoadUriTask) msg.obj;
                            RoundedRectImageView imageView = (RoundedRectImageView) ContentPreviewCoordinator.this.mParentView.findViewById(task.mImageResourceId);
                            if (task.mBmp == null) {
                                imageView.setVisibility(8);
                                ContentPreviewCoordinator.this.maybeHideContentPreview();
                                return;
                            }
                            boolean unused = ContentPreviewCoordinator.this.mAtLeastOneLoaded = true;
                            imageView.setVisibility(0);
                            imageView.setAlpha(0.0f);
                            imageView.setImageBitmap(task.mBmp);
                            ValueAnimator fadeAnim = ObjectAnimator.ofFloat((Object) imageView, "alpha", 0.0f, 1.0f);
                            fadeAnim.setInterpolator(new DecelerateInterpolator(1.0f));
                            fadeAnim.setDuration(150);
                            fadeAnim.start();
                            if (task.mExtraCount > 0) {
                                imageView.setExtraImageCount(task.mExtraCount);
                                return;
                            }
                            return;
                        }
                        return;
                    default:
                        return;
                }
            }
        };
        private boolean mHideParentOnFail;
        private final int mImageLoadTimeoutMillis = ChooserActivity.this.getResources().getInteger(17694720);
        /* access modifiers changed from: private */
        public final View mParentView;

        class LoadUriTask {
            public final Bitmap mBmp;
            public final int mExtraCount;
            public final int mImageResourceId;
            public final Uri mUri;

            LoadUriTask(int imageResourceId, Uri uri, int extraCount, Bitmap bmp) {
                this.mImageResourceId = imageResourceId;
                this.mUri = uri;
                this.mExtraCount = extraCount;
                this.mBmp = bmp;
            }
        }

        ContentPreviewCoordinator(View parentView, boolean hideParentOnFail) {
            this.mParentView = parentView;
            this.mHideParentOnFail = hideParentOnFail;
        }

        /* access modifiers changed from: private */
        public void loadUriIntoView(int imageResourceId, Uri uri, int extraImages) {
            this.mHandler.sendEmptyMessageDelayed(1, (long) this.mImageLoadTimeoutMillis);
            AsyncTask.THREAD_POOL_EXECUTOR.execute(new Runnable(uri, imageResourceId, extraImages) {
                private final /* synthetic */ Uri f$1;
                private final /* synthetic */ int f$2;
                private final /* synthetic */ int f$3;

                {
                    this.f$1 = r2;
                    this.f$2 = r3;
                    this.f$3 = r4;
                }

                public final void run() {
                    ChooserActivity.ContentPreviewCoordinator.lambda$loadUriIntoView$0(ChooserActivity.ContentPreviewCoordinator.this, this.f$1, this.f$2, this.f$3);
                }
            });
        }

        public static /* synthetic */ void lambda$loadUriIntoView$0(ContentPreviewCoordinator contentPreviewCoordinator, Uri uri, int imageResourceId, int extraImages) {
            Bitmap bmp = ChooserActivity.this.loadThumbnail(uri, new Size(200, 200));
            Message msg = Message.obtain();
            msg.what = 2;
            msg.obj = new LoadUriTask(imageResourceId, uri, extraImages, bmp);
            contentPreviewCoordinator.mHandler.sendMessage(msg);
        }

        /* access modifiers changed from: private */
        public void cancelLoads() {
            this.mHandler.removeMessages(2);
            this.mHandler.removeMessages(1);
        }

        /* access modifiers changed from: private */
        public void maybeHideContentPreview() {
            if (!this.mAtLeastOneLoaded && this.mHideParentOnFail) {
                Log.i(ChooserActivity.TAG, "Hiding image preview area. Timed out waiting for preview to load within " + this.mImageLoadTimeoutMillis + "ms.");
                collapseParentView();
                if (ChooserActivity.this.mChooserRowAdapter != null) {
                    ChooserActivity.this.mChooserRowAdapter.hideContentPreview();
                }
                this.mHideParentOnFail = false;
            }
        }

        private void collapseParentView() {
            View v = this.mParentView;
            v.measure(View.MeasureSpec.makeMeasureSpec(v.getWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(0, 1073741824));
            v.getLayoutParams().height = 0;
            v.layout(v.getLeft(), v.getTop(), v.getRight(), v.getTop());
            v.invalidate();
        }
    }

    private class ChooserHandler extends Handler {
        private static final int CHOOSER_TARGET_SERVICE_RESULT = 1;
        private static final int CHOOSER_TARGET_SERVICE_WATCHDOG_MAX_TIMEOUT = 3;
        private static final int CHOOSER_TARGET_SERVICE_WATCHDOG_MIN_TIMEOUT = 2;
        private static final int LIST_VIEW_UPDATE_MESSAGE = 6;
        private static final int SHORTCUT_MANAGER_SHARE_TARGET_RESULT = 4;
        private static final int SHORTCUT_MANAGER_SHARE_TARGET_RESULT_COMPLETED = 5;
        private static final int WATCHDOG_TIMEOUT_MAX_MILLIS = 10000;
        private static final int WATCHDOG_TIMEOUT_MIN_MILLIS = 3000;
        private boolean mMinTimeoutPassed;

        private ChooserHandler() {
            this.mMinTimeoutPassed = false;
        }

        /* access modifiers changed from: private */
        public void removeAllMessages() {
            removeMessages(6);
            removeMessages(2);
            removeMessages(3);
            removeMessages(1);
            removeMessages(4);
            removeMessages(5);
        }

        /* access modifiers changed from: private */
        public void restartServiceRequestTimer() {
            this.mMinTimeoutPassed = false;
            removeMessages(2);
            removeMessages(3);
            sendEmptyMessageDelayed(2, 3000);
            sendEmptyMessageDelayed(3, JobInfo.MIN_BACKOFF_MILLIS);
        }

        private void maybeStopServiceRequestTimer() {
            if (this.mMinTimeoutPassed && ChooserActivity.this.mServiceConnections.isEmpty()) {
                ChooserActivity.this.logDirectShareTargetReceived(MetricsProto.MetricsEvent.ACTION_DIRECT_SHARE_TARGETS_LOADED_CHOOSER_SERVICE);
                ChooserActivity.this.sendVoiceChoicesIfNeeded();
                ChooserActivity.this.mChooserListAdapter.completeServiceTargetLoading();
            }
        }

        public void handleMessage(Message msg) {
            if (ChooserActivity.this.mChooserListAdapter != null && !ChooserActivity.this.isDestroyed()) {
                switch (msg.what) {
                    case 1:
                        ServiceResultInfo sri = (ServiceResultInfo) msg.obj;
                        if (!ChooserActivity.this.mServiceConnections.contains(sri.connection)) {
                            Log.w(ChooserActivity.TAG, "ChooserTargetServiceConnection " + sri.connection + " returned after being removed from active connections. Have you considered returning results faster?");
                            return;
                        }
                        if (sri.resultTargets != null) {
                            ChooserActivity.this.mChooserListAdapter.addServiceResults(sri.originalTarget, sri.resultTargets, false);
                        }
                        ChooserActivity.this.unbindService(sri.connection);
                        sri.connection.destroy();
                        ChooserActivity.this.mServiceConnections.remove(sri.connection);
                        maybeStopServiceRequestTimer();
                        return;
                    case 2:
                        this.mMinTimeoutPassed = true;
                        maybeStopServiceRequestTimer();
                        return;
                    case 3:
                        ChooserActivity.this.unbindRemainingServices();
                        maybeStopServiceRequestTimer();
                        return;
                    case 4:
                        ServiceResultInfo resultInfo = (ServiceResultInfo) msg.obj;
                        if (resultInfo.resultTargets != null) {
                            ChooserActivity.this.mChooserListAdapter.addServiceResults(resultInfo.originalTarget, resultInfo.resultTargets, true);
                            return;
                        }
                        return;
                    case 5:
                        ChooserActivity.this.logDirectShareTargetReceived(MetricsProto.MetricsEvent.ACTION_DIRECT_SHARE_TARGETS_LOADED_SHORTCUT_MANAGER);
                        ChooserActivity.this.sendVoiceChoicesIfNeeded();
                        return;
                    case 6:
                        ChooserActivity.this.mChooserListAdapter.refreshListView();
                        return;
                    default:
                        super.handleMessage(msg);
                        return;
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        Intent target;
        long intentReceivedTime = System.currentTimeMillis();
        this.mIsSuccessfullySelected = false;
        Intent intent = getIntent();
        Parcelable targetParcelable = intent.getParcelableExtra(Intent.EXTRA_INTENT);
        if (!(targetParcelable instanceof Intent)) {
            Log.w(TAG, "Target is not an intent: " + targetParcelable);
            finish();
            super.onCreate((Bundle) null);
            return;
        }
        Intent target2 = (Intent) targetParcelable;
        if (target2 != null) {
            modifyTargetIntent(target2);
        }
        Parcelable[] targetsParcelable = intent.getParcelableArrayExtra(Intent.EXTRA_ALTERNATE_INTENTS);
        int i = 1;
        if (targetsParcelable != null) {
            boolean offset = target2 == null;
            Intent[] additionalTargets = new Intent[(offset ? targetsParcelable.length - 1 : targetsParcelable.length)];
            Intent target3 = target2;
            for (int i2 = 0; i2 < targetsParcelable.length; i2++) {
                if (!(targetsParcelable[i2] instanceof Intent)) {
                    Log.w(TAG, "EXTRA_ALTERNATE_INTENTS array entry #" + i2 + " is not an Intent: " + targetsParcelable[i2]);
                    finish();
                    super.onCreate((Bundle) null);
                    return;
                }
                Intent additionalTarget = (Intent) targetsParcelable[i2];
                if (i2 == 0 && target3 == null) {
                    target3 = additionalTarget;
                    modifyTargetIntent(target3);
                } else {
                    additionalTargets[offset ? i2 - 1 : i2] = additionalTarget;
                    modifyTargetIntent(additionalTarget);
                }
            }
            setAdditionalTargets(additionalTargets);
            target = target3;
        } else {
            target = target2;
        }
        this.mReplacementExtras = intent.getBundleExtra(Intent.EXTRA_REPLACEMENT_EXTRAS);
        CharSequence title = null;
        if (target != null) {
            if (!isSendAction(target)) {
                title = intent.getCharSequenceExtra(Intent.EXTRA_TITLE);
            } else {
                Log.w(TAG, "Ignoring intent's EXTRA_TITLE, deprecated in P. You may wish to set a preview title by using EXTRA_TITLE property of the wrapped EXTRA_INTENT.");
            }
        }
        CharSequence title2 = title;
        int defaultTitleRes = 0;
        if (title2 == null) {
            defaultTitleRes = R.string.chooseActivity;
        }
        int defaultTitleRes2 = defaultTitleRes;
        Parcelable[] pa = intent.getParcelableArrayExtra(Intent.EXTRA_INITIAL_INTENTS);
        Intent[] initialIntents = null;
        if (pa != null) {
            int count = Math.min(pa.length, 2);
            initialIntents = new Intent[count];
            for (int i3 = 0; i3 < count; i3++) {
                if (!(pa[i3] instanceof Intent)) {
                    Log.w(TAG, "Initial intent #" + i3 + " not an Intent: " + pa[i3]);
                    finish();
                    super.onCreate((Bundle) null);
                    return;
                }
                Intent in = (Intent) pa[i3];
                modifyTargetIntent(in);
                initialIntents[i3] = in;
            }
        }
        Intent[] initialIntents2 = initialIntents;
        this.mReferrerFillInIntent = new Intent().putExtra(Intent.EXTRA_REFERRER, (Parcelable) getReferrer());
        this.mChosenComponentSender = (IntentSender) intent.getParcelableExtra(Intent.EXTRA_CHOSEN_COMPONENT_INTENT_SENDER);
        this.mRefinementIntentSender = (IntentSender) intent.getParcelableExtra(Intent.EXTRA_CHOOSER_REFINEMENT_INTENT_SENDER);
        setSafeForwardingMode(true);
        Parcelable[] pa2 = intent.getParcelableArrayExtra(Intent.EXTRA_EXCLUDE_COMPONENTS);
        if (pa2 != null) {
            ComponentName[] names = new ComponentName[pa2.length];
            int i4 = 0;
            while (true) {
                if (i4 >= pa2.length) {
                    break;
                } else if (!(pa2[i4] instanceof ComponentName)) {
                    Log.w(TAG, "Filtered component #" + i4 + " not a ComponentName: " + pa2[i4]);
                    names = null;
                    break;
                } else {
                    names[i4] = (ComponentName) pa2[i4];
                    i4++;
                }
            }
            this.mFilteredComponentNames = names;
        }
        Parcelable[] pa3 = intent.getParcelableArrayExtra(Intent.EXTRA_CHOOSER_TARGETS);
        if (pa3 != null) {
            int count2 = Math.min(pa3.length, 2);
            ChooserTarget[] targets = new ChooserTarget[count2];
            int i5 = 0;
            while (true) {
                if (i5 >= count2) {
                    break;
                } else if (!(pa3[i5] instanceof ChooserTarget)) {
                    Log.w(TAG, "Chooser target #" + i5 + " not a ChooserTarget: " + pa3[i5]);
                    targets = null;
                    break;
                } else {
                    targets[i5] = (ChooserTarget) pa3[i5];
                    i5++;
                }
            }
            this.mCallerChooserTargets = targets;
        }
        setRetainInOnStop(intent.getBooleanExtra(EXTRA_PRIVATE_RETAIN_IN_ON_STOP, false));
        Parcelable[] parcelableArr = pa3;
        super.onCreate(savedInstanceState, target, title2, defaultTitleRes2, initialIntents2, (List<ResolveInfo>) null, false);
        this.mChooserShownTime = System.currentTimeMillis();
        long systemCost = this.mChooserShownTime - intentReceivedTime;
        MetricsLogger metricsLogger = getMetricsLogger();
        LogMaker logMaker = new LogMaker(214);
        if (isWorkProfile()) {
            i = 2;
        }
        metricsLogger.write(logMaker.setSubtype(i).addTaggedData(MetricsProto.MetricsEvent.FIELD_SHARESHEET_MIMETYPE, target.getType()).addTaggedData(MetricsProto.MetricsEvent.FIELD_TIME_TO_APP_TARGETS, Long.valueOf(systemCost)));
        AppPredictor appPredictor = getAppPredictorForDirectShareIfEnabled();
        if (appPredictor != null) {
            this.mDirectShareAppTargetCache = new HashMap();
            this.mAppPredictorCallback = new AppPredictor.Callback() {
                public final void onTargetsAvailable(List list) {
                    ChooserActivity.lambda$onCreate$0(ChooserActivity.this, list);
                }
            };
            appPredictor.registerPredictionUpdates(getMainExecutor(), this.mAppPredictorCallback);
        }
        this.mChooserRowServiceSpacing = getResources().getDimensionPixelSize(R.dimen.chooser_service_spacing);
        if (this.mResolverDrawerLayout != null) {
            this.mResolverDrawerLayout.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                    ChooserActivity.this.handleLayoutChange(view, i, i2, i3, i4, i5, i6, i7, i8);
                }
            });
            if (isSendAction(target)) {
                this.mResolverDrawerLayout.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                    public final void onScrollChange(View view, int i, int i2, int i3, int i4) {
                        ChooserActivity.this.handleScroll(view, i, i2, i3, i4);
                    }
                });
            }
            final View chooserHeader = this.mResolverDrawerLayout.findViewById(R.id.chooser_header);
            final float defaultElevation = chooserHeader.getElevation();
            final float chooserHeaderScrollElevation = (float) getResources().getDimensionPixelSize(R.dimen.chooser_header_scroll_elevation);
            this.mAdapterView.setOnScrollListener(new AbsListView.OnScrollListener() {
                public void onScrollStateChanged(AbsListView view, int scrollState) {
                }

                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                    if (view.getChildCount() <= 0 || (firstVisibleItem <= 0 && view.getChildAt(0).getTop() >= 0)) {
                        chooserHeader.setElevation(defaultElevation);
                    } else {
                        chooserHeader.setElevation(chooserHeaderScrollElevation);
                    }
                }
            });
            this.mResolverDrawerLayout.setOnCollapsedChangedListener(new ResolverDrawerLayout.OnCollapsedChangedListener() {
                private boolean mWrittenOnce = false;

                public void onCollapsedChanged(boolean isCollapsed) {
                    if (!isCollapsed && !this.mWrittenOnce) {
                        ChooserActivity.this.incrementNumSheetExpansions();
                        this.mWrittenOnce = true;
                    }
                }
            });
        }
    }

    public static /* synthetic */ void lambda$onCreate$0(ChooserActivity chooserActivity, List resultList) {
        if (!chooserActivity.isFinishing() && !chooserActivity.isDestroyed() && chooserActivity.mChooserListAdapter != null) {
            if (resultList.isEmpty()) {
                chooserActivity.queryDirectShareTargets(chooserActivity.mChooserListAdapter, true);
                return;
            }
            List<ResolverActivity.DisplayResolveInfo> driList = chooserActivity.getDisplayResolveInfos(chooserActivity.mChooserListAdapter);
            List<ShortcutManager.ShareShortcutInfo> shareShortcutInfos = new ArrayList<>();
            Iterator it = resultList.iterator();
            while (it.hasNext()) {
                AppTarget appTarget = (AppTarget) it.next();
                if (appTarget.getShortcutInfo() != null) {
                    shareShortcutInfos.add(new ShortcutManager.ShareShortcutInfo(appTarget.getShortcutInfo(), new ComponentName(appTarget.getPackageName(), appTarget.getClassName())));
                }
            }
            chooserActivity.sendShareShortcutInfoList(shareShortcutInfos, driList, resultList);
        }
    }

    /* access modifiers changed from: protected */
    public boolean isWorkProfile() {
        return ((UserManager) getSystemService("user")).getUserInfo(UserHandle.myUserId()).isManagedProfile();
    }

    /* access modifiers changed from: protected */
    public PackageMonitor createPackageMonitor() {
        return new PackageMonitor() {
            public void onSomePackagesChanged() {
                ChooserActivity.this.mAdapter.handlePackagesChanged();
                ChooserActivity.this.bindProfileView();
            }
        };
    }

    /* access modifiers changed from: private */
    public void onCopyButtonClicked(View v) {
        ClipData clipData;
        Intent targetIntent = getTargetIntent();
        if (targetIntent == null) {
            finish();
            return;
        }
        String action = targetIntent.getAction();
        if (Intent.ACTION_SEND.equals(action)) {
            String extraText = targetIntent.getStringExtra(Intent.EXTRA_TEXT);
            Uri extraStream = (Uri) targetIntent.getParcelableExtra(Intent.EXTRA_STREAM);
            if (extraText != null) {
                clipData = ClipData.newPlainText((CharSequence) null, extraText);
            } else if (extraStream != null) {
                clipData = ClipData.newUri(getContentResolver(), (CharSequence) null, extraStream);
            } else {
                Log.w(TAG, "No data available to copy to clipboard");
                return;
            }
        } else if (Intent.ACTION_SEND_MULTIPLE.equals(action)) {
            ArrayList<Uri> streams = targetIntent.getParcelableArrayListExtra(Intent.EXTRA_STREAM);
            clipData = ClipData.newUri(getContentResolver(), (CharSequence) null, streams.get(0));
            for (int i = 1; i < streams.size(); i++) {
                clipData.addItem(getContentResolver(), new ClipData.Item(streams.get(i)));
            }
        } else {
            Log.w(TAG, "Action (" + action + ") not supported for copying to clipboard");
            return;
        }
        ((ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE)).setPrimaryClip(clipData);
        Toast.makeText(getApplicationContext(), (int) R.string.copied, 0).show();
        finish();
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        adjustPreviewWidth(newConfig.orientation, (View) null);
    }

    /* access modifiers changed from: private */
    public boolean shouldDisplayLandscape(int orientation) {
        return orientation == 2 && !isInMultiWindowMode();
    }

    private void adjustPreviewWidth(int orientation, View parent) {
        int width = -1;
        if (shouldDisplayLandscape(orientation)) {
            width = getResources().getDimensionPixelSize(R.dimen.chooser_preview_width);
        }
        View parent2 = parent == null ? getWindow().getDecorView() : parent;
        updateLayoutWidth(R.id.content_preview_text_layout, width, parent2);
        updateLayoutWidth(R.id.content_preview_title_layout, width, parent2);
        updateLayoutWidth(R.id.content_preview_file_layout, width, parent2);
    }

    private void updateLayoutWidth(int layoutResourceId, int width, View parent) {
        View view = parent.findViewById(layoutResourceId);
        if (view != null && view.getLayoutParams() != null) {
            ViewGroup.LayoutParams params = view.getLayoutParams();
            params.width = width;
            view.setLayoutParams(params);
        }
    }

    /* access modifiers changed from: private */
    public ViewGroup displayContentPreview(int previewType, Intent targetIntent, LayoutInflater layoutInflater, ViewGroup convertView, ViewGroup parent) {
        if (convertView != null) {
            return convertView;
        }
        ViewGroup layout = null;
        switch (previewType) {
            case 1:
                layout = displayImageContentPreview(targetIntent, layoutInflater, parent);
                break;
            case 2:
                layout = displayFileContentPreview(targetIntent, layoutInflater, parent);
                break;
            case 3:
                layout = displayTextContentPreview(targetIntent, layoutInflater, parent);
                break;
            default:
                Log.e(TAG, "Unexpected content preview type: " + previewType);
                break;
        }
        if (layout != null) {
            adjustPreviewWidth(getResources().getConfiguration().orientation, layout);
        }
        return layout;
    }

    private ViewGroup displayTextContentPreview(Intent targetIntent, LayoutInflater layoutInflater, ViewGroup parent) {
        ViewGroup contentPreviewLayout = (ViewGroup) layoutInflater.inflate((int) R.layout.chooser_grid_preview_text, parent, false);
        contentPreviewLayout.findViewById(R.id.copy_button).setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                ChooserActivity.this.onCopyButtonClicked(view);
            }
        });
        CharSequence sharingText = targetIntent.getCharSequenceExtra(Intent.EXTRA_TEXT);
        if (sharingText == null) {
            contentPreviewLayout.findViewById(R.id.content_preview_text_layout).setVisibility(8);
        } else {
            ((TextView) contentPreviewLayout.findViewById(R.id.content_preview_text)).setText(sharingText);
        }
        String previewTitle = targetIntent.getStringExtra(Intent.EXTRA_TITLE);
        if (TextUtils.isEmpty(previewTitle)) {
            contentPreviewLayout.findViewById(R.id.content_preview_title_layout).setVisibility(8);
        } else {
            ((TextView) contentPreviewLayout.findViewById(R.id.content_preview_title)).setText((CharSequence) previewTitle);
            ClipData previewData = targetIntent.getClipData();
            Uri previewThumbnail = null;
            if (previewData != null && previewData.getItemCount() > 0) {
                previewThumbnail = previewData.getItemAt(0).getUri();
            }
            ImageView previewThumbnailView = (ImageView) contentPreviewLayout.findViewById(R.id.content_preview_thumbnail);
            if (previewThumbnail == null) {
                previewThumbnailView.setVisibility(8);
            } else {
                this.mPreviewCoord = new ContentPreviewCoordinator(contentPreviewLayout, false);
                this.mPreviewCoord.loadUriIntoView(R.id.content_preview_thumbnail, previewThumbnail, 0);
            }
        }
        return contentPreviewLayout;
    }

    private ViewGroup displayImageContentPreview(Intent targetIntent, LayoutInflater layoutInflater, ViewGroup parent) {
        ViewGroup contentPreviewLayout = (ViewGroup) layoutInflater.inflate((int) R.layout.chooser_grid_preview_image, parent, false);
        this.mPreviewCoord = new ContentPreviewCoordinator(contentPreviewLayout, true);
        if (Intent.ACTION_SEND.equals(targetIntent.getAction())) {
            this.mPreviewCoord.loadUriIntoView(R.id.content_preview_image_1_large, (Uri) targetIntent.getParcelableExtra(Intent.EXTRA_STREAM), 0);
        } else {
            ContentResolver resolver = getContentResolver();
            List<Uri> uris = targetIntent.getParcelableArrayListExtra(Intent.EXTRA_STREAM);
            List<Uri> imageUris = new ArrayList<>();
            for (Uri uri : uris) {
                if (isImageType(resolver.getType(uri))) {
                    imageUris.add(uri);
                }
            }
            if (imageUris.size() == 0) {
                Log.i(TAG, "Attempted to display image preview area with zero available images detected in EXTRA_STREAM list");
                contentPreviewLayout.setVisibility(8);
                return contentPreviewLayout;
            }
            this.mPreviewCoord.loadUriIntoView(R.id.content_preview_image_1_large, imageUris.get(0), 0);
            if (imageUris.size() == 2) {
                this.mPreviewCoord.loadUriIntoView(R.id.content_preview_image_2_large, imageUris.get(1), 0);
            } else if (imageUris.size() > 2) {
                this.mPreviewCoord.loadUriIntoView(R.id.content_preview_image_2_small, imageUris.get(1), 0);
                this.mPreviewCoord.loadUriIntoView(R.id.content_preview_image_3_small, imageUris.get(2), imageUris.size() - 3);
            }
        }
        return contentPreviewLayout;
    }

    private static class FileInfo {
        public final boolean hasThumbnail;
        public final String name;

        FileInfo(String name2, boolean hasThumbnail2) {
            this.name = name2;
            this.hasThumbnail = hasThumbnail2;
        }
    }

    @VisibleForTesting
    public Cursor queryResolver(ContentResolver resolver, Uri uri) {
        return resolver.query(uri, (String[]) null, (String) null, (String[]) null, (String) null);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0051, code lost:
        r6 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:?, code lost:
        r5.addSuppressed(r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x0061, code lost:
        logContentPreviewWarning(r11);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x006a, code lost:
        r0 = r11.getPath();
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x0060 A[ExcHandler: NullPointerException | SecurityException (e java.lang.Throwable), Splitter:B:1:0x0004] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.android.internal.app.ChooserActivity.FileInfo extractFileInfo(android.net.Uri r11, android.content.ContentResolver r12) {
        /*
            r10 = this;
            r0 = 0
            r1 = 0
            r2 = r1
            r3 = -1
            android.database.Cursor r4 = r10.queryResolver(r12, r11)     // Catch:{ NullPointerException | SecurityException -> 0x0060 }
            r5 = 0
            if (r4 == 0) goto L_0x005a
            int r6 = r4.getCount()     // Catch:{ Throwable -> 0x0046 }
            if (r6 <= 0) goto L_0x005a
            java.lang.String r6 = "_display_name"
            int r6 = r4.getColumnIndex(r6)     // Catch:{ Throwable -> 0x0046 }
            java.lang.String r7 = "title"
            int r7 = r4.getColumnIndex(r7)     // Catch:{ Throwable -> 0x0046 }
            java.lang.String r8 = "flags"
            int r8 = r4.getColumnIndex(r8)     // Catch:{ Throwable -> 0x0046 }
            r4.moveToFirst()     // Catch:{ Throwable -> 0x0046 }
            if (r6 == r3) goto L_0x002f
            java.lang.String r9 = r4.getString(r6)     // Catch:{ Throwable -> 0x0046 }
            r0 = r9
            goto L_0x0036
        L_0x002f:
            if (r7 == r3) goto L_0x0036
            java.lang.String r9 = r4.getString(r7)     // Catch:{ Throwable -> 0x0046 }
            r0 = r9
        L_0x0036:
            if (r8 == r3) goto L_0x005a
            int r9 = r4.getInt(r8)     // Catch:{ Throwable -> 0x0046 }
            r5 = 1
            r9 = r9 & r5
            if (r9 == 0) goto L_0x0042
            r1 = r5
        L_0x0042:
            r2 = r1
            goto L_0x005a
        L_0x0044:
            r1 = move-exception
            goto L_0x0049
        L_0x0046:
            r1 = move-exception
            r5 = r1
            throw r5     // Catch:{ all -> 0x0044 }
        L_0x0049:
            if (r4 == 0) goto L_0x0059
            if (r5 == 0) goto L_0x0056
            r4.close()     // Catch:{ Throwable -> 0x0051, NullPointerException | SecurityException -> 0x0060 }
            goto L_0x0059
        L_0x0051:
            r6 = move-exception
            r5.addSuppressed(r6)     // Catch:{ NullPointerException | SecurityException -> 0x0060 }
            goto L_0x0059
        L_0x0056:
            r4.close()     // Catch:{ NullPointerException | SecurityException -> 0x0060 }
        L_0x0059:
            throw r1     // Catch:{ NullPointerException | SecurityException -> 0x0060 }
        L_0x005a:
            if (r4 == 0) goto L_0x005f
            r4.close()     // Catch:{ NullPointerException | SecurityException -> 0x0060 }
        L_0x005f:
            goto L_0x0064
        L_0x0060:
            r1 = move-exception
            r10.logContentPreviewWarning(r11)
        L_0x0064:
            boolean r1 = android.text.TextUtils.isEmpty(r0)
            if (r1 == 0) goto L_0x007c
            java.lang.String r0 = r11.getPath()
            r1 = 47
            int r1 = r0.lastIndexOf(r1)
            if (r1 == r3) goto L_0x007c
            int r3 = r1 + 1
            java.lang.String r0 = r0.substring(r3)
        L_0x007c:
            com.android.internal.app.ChooserActivity$FileInfo r1 = new com.android.internal.app.ChooserActivity$FileInfo
            r1.<init>(r0, r2)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.app.ChooserActivity.extractFileInfo(android.net.Uri, android.content.ContentResolver):com.android.internal.app.ChooserActivity$FileInfo");
    }

    private void logContentPreviewWarning(Uri uri) {
        Log.w(TAG, "Could not load (" + uri.toString() + ") thumbnail/name for preview. If desired, consider using Intent#createChooser to launch the ChooserActivity, and set your Intent's clipData and flags in accordance with that method's documentation");
    }

    private ViewGroup displayFileContentPreview(Intent targetIntent, LayoutInflater layoutInflater, ViewGroup parent) {
        Intent intent = targetIntent;
        ViewGroup contentPreviewLayout = (ViewGroup) layoutInflater.inflate((int) R.layout.chooser_grid_preview_file, parent, false);
        contentPreviewLayout.findViewById(R.id.file_copy_button).setVisibility(8);
        if (Intent.ACTION_SEND.equals(targetIntent.getAction())) {
            loadFileUriIntoView((Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM), contentPreviewLayout);
        } else {
            List<Uri> uris = intent.getParcelableArrayListExtra(Intent.EXTRA_STREAM);
            int uriCount = uris.size();
            if (uriCount == 0) {
                contentPreviewLayout.setVisibility(8);
                Log.i(TAG, "Appears to be no uris available in EXTRA_STREAM, removing preview area");
                return contentPreviewLayout;
            } else if (uriCount == 1) {
                loadFileUriIntoView(uris.get(0), contentPreviewLayout);
            } else {
                int remUriCount = uriCount - 1;
                ((TextView) contentPreviewLayout.findViewById(R.id.content_preview_filename)).setText((CharSequence) getResources().getQuantityString(R.plurals.file_count, remUriCount, extractFileInfo(uris.get(0), getContentResolver()).name, Integer.valueOf(remUriCount)));
                contentPreviewLayout.findViewById(R.id.content_preview_file_thumbnail).setVisibility(8);
                ImageView fileIconView = (ImageView) contentPreviewLayout.findViewById(R.id.content_preview_file_icon);
                fileIconView.setVisibility(0);
                fileIconView.setImageResource(R.drawable.ic_file_copy);
            }
        }
        return contentPreviewLayout;
    }

    private void loadFileUriIntoView(Uri uri, View parent) {
        FileInfo fileInfo = extractFileInfo(uri, getContentResolver());
        ((TextView) parent.findViewById(R.id.content_preview_filename)).setText((CharSequence) fileInfo.name);
        if (fileInfo.hasThumbnail) {
            this.mPreviewCoord = new ContentPreviewCoordinator(parent, false);
            this.mPreviewCoord.loadUriIntoView(R.id.content_preview_file_thumbnail, uri, 0);
            return;
        }
        parent.findViewById(R.id.content_preview_file_thumbnail).setVisibility(8);
        ImageView fileIconView = (ImageView) parent.findViewById(R.id.content_preview_file_icon);
        fileIconView.setVisibility(0);
        fileIconView.setImageResource(R.drawable.chooser_file_generic);
    }

    /* access modifiers changed from: protected */
    @VisibleForTesting
    public boolean isImageType(String mimeType) {
        return mimeType != null && mimeType.startsWith(MessagingMessage.IMAGE_MIME_TYPE_PREFIX);
    }

    private int findPreferredContentPreview(Uri uri, ContentResolver resolver) {
        if (uri == null) {
            return 3;
        }
        return isImageType(resolver.getType(uri)) ? 1 : 2;
    }

    /* access modifiers changed from: private */
    public int findPreferredContentPreview(Intent targetIntent, ContentResolver resolver) {
        List<Uri> uris;
        String action = targetIntent.getAction();
        if (Intent.ACTION_SEND.equals(action)) {
            return findPreferredContentPreview((Uri) targetIntent.getParcelableExtra(Intent.EXTRA_STREAM), resolver);
        }
        if (!Intent.ACTION_SEND_MULTIPLE.equals(action) || (uris = targetIntent.getParcelableArrayListExtra(Intent.EXTRA_STREAM)) == null || uris.isEmpty()) {
            return 3;
        }
        for (Uri uri : uris) {
            if (findPreferredContentPreview(uri, resolver) == 2) {
                return 2;
            }
        }
        return 1;
    }

    /* access modifiers changed from: private */
    public int getNumSheetExpansions() {
        return getPreferences(0).getInt(PREF_NUM_SHEET_EXPANSIONS, 0);
    }

    /* access modifiers changed from: private */
    public void incrementNumSheetExpansions() {
        getPreferences(0).edit().putInt(PREF_NUM_SHEET_EXPANSIONS, getNumSheetExpansions() + 1).apply();
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        if (this.mRefinementResultReceiver != null) {
            this.mRefinementResultReceiver.destroy();
            this.mRefinementResultReceiver = null;
        }
        unbindRemainingServices();
        this.mChooserHandler.removeAllMessages();
        if (this.mPreviewCoord != null) {
            this.mPreviewCoord.cancelLoads();
        }
        if (this.mAppPredictor != null) {
            this.mAppPredictor.unregisterPredictionUpdates(this.mAppPredictorCallback);
            this.mAppPredictor.destroy();
        }
    }

    public Intent getReplacementIntent(ActivityInfo aInfo, Intent defIntent) {
        Bundle replExtras;
        Intent result = defIntent;
        if (!(this.mReplacementExtras == null || (replExtras = this.mReplacementExtras.getBundle(aInfo.packageName)) == null)) {
            result = new Intent(defIntent);
            result.putExtras(replExtras);
        }
        if (!aInfo.name.equals(IntentForwarderActivity.FORWARD_INTENT_TO_PARENT) && !aInfo.name.equals(IntentForwarderActivity.FORWARD_INTENT_TO_MANAGED_PROFILE)) {
            return result;
        }
        Intent result2 = Intent.createChooser(result, getIntent().getCharSequenceExtra(Intent.EXTRA_TITLE));
        result2.putExtra(Intent.EXTRA_AUTO_LAUNCH_SINGLE_CHOICE, false);
        return result2;
    }

    public void onActivityStarted(ResolverActivity.TargetInfo cti) {
        ComponentName target;
        if (this.mChosenComponentSender != null && (target = cti.getResolvedComponentName()) != null) {
            try {
                this.mChosenComponentSender.sendIntent(this, -1, new Intent().putExtra(Intent.EXTRA_CHOSEN_COMPONENT, (Parcelable) target), (IntentSender.OnFinished) null, (Handler) null);
            } catch (IntentSender.SendIntentException e) {
                Slog.e(TAG, "Unable to launch supplied IntentSender to report the chosen component: " + e);
            }
        }
    }

    public void onPrepareAdapterView(AbsListView adapterView, ResolverActivity.ResolveListAdapter adapter) {
        ListView listView = adapterView instanceof ListView ? (ListView) adapterView : null;
        this.mChooserListAdapter = (ChooserListAdapter) adapter;
        if (this.mCallerChooserTargets != null && this.mCallerChooserTargets.length > 0) {
            this.mChooserListAdapter.addServiceResults((ResolverActivity.DisplayResolveInfo) null, Lists.newArrayList(this.mCallerChooserTargets), false);
        }
        this.mChooserRowAdapter = new ChooserRowAdapter(this.mChooserListAdapter);
        if (listView != null) {
            listView.setItemsCanFocus(true);
        }
    }

    public int getLayoutResource() {
        return R.layout.chooser_grid;
    }

    public boolean shouldGetActivityMetadata() {
        return true;
    }

    public boolean shouldAutoLaunchSingleChoice(ResolverActivity.TargetInfo target) {
        if (!super.shouldAutoLaunchSingleChoice(target)) {
            return false;
        }
        return getIntent().getBooleanExtra(Intent.EXTRA_AUTO_LAUNCH_SINGLE_CHOICE, true);
    }

    public void showTargetDetails(ResolveInfo ri) {
        if (ri != null) {
            new ResolverTargetActionsDialogFragment(ri.loadLabel(getPackageManager()), ri.activityInfo.getComponentName()).show(getFragmentManager(), TARGET_DETAILS_FRAGMENT_TAG);
        }
    }

    private void modifyTargetIntent(Intent in) {
        if (isSendAction(in)) {
            in.addFlags(134742016);
        }
    }

    /* access modifiers changed from: protected */
    public boolean onTargetSelected(ResolverActivity.TargetInfo target, boolean alwaysCheck) {
        if (this.mRefinementIntentSender != null) {
            Intent fillIn = new Intent();
            List<Intent> sourceIntents = target.getAllSourceIntents();
            if (!sourceIntents.isEmpty()) {
                fillIn.putExtra(Intent.EXTRA_INTENT, (Parcelable) sourceIntents.get(0));
                if (sourceIntents.size() > 1) {
                    Intent[] alts = new Intent[(sourceIntents.size() - 1)];
                    int N = sourceIntents.size();
                    for (int i = 1; i < N; i++) {
                        alts[i - 1] = sourceIntents.get(i);
                    }
                    fillIn.putExtra(Intent.EXTRA_ALTERNATE_INTENTS, (Parcelable[]) alts);
                }
                if (this.mRefinementResultReceiver != null) {
                    this.mRefinementResultReceiver.destroy();
                }
                this.mRefinementResultReceiver = new RefinementResultReceiver(this, target, (Handler) null);
                fillIn.putExtra(Intent.EXTRA_RESULT_RECEIVER, (Parcelable) this.mRefinementResultReceiver);
                try {
                    this.mRefinementIntentSender.sendIntent(this, 0, fillIn, (IntentSender.OnFinished) null, (Handler) null);
                    return false;
                } catch (IntentSender.SendIntentException e) {
                    Log.e(TAG, "Refinement IntentSender failed to send", e);
                }
            }
        }
        updateModelAndChooserCounts(target);
        return super.onTargetSelected(target, alwaysCheck);
    }

    public void startSelected(int which, boolean always, boolean filtered) {
        int i = which;
        ResolverActivity.TargetInfo targetInfo = this.mChooserListAdapter.targetInfoForPosition(i, filtered);
        if (targetInfo == null || !(targetInfo instanceof NotSelectableTargetInfo)) {
            long selectionCost = System.currentTimeMillis() - this.mChooserShownTime;
            super.startSelected(which, always, filtered);
            if (this.mChooserListAdapter != null) {
                int cat = 0;
                int value = which;
                int directTargetAlsoRanked = -1;
                int numCallerProvided = 0;
                HashedStringCache.HashResult directTargetHashed = null;
                switch (this.mChooserListAdapter.getPositionTargetType(i)) {
                    case 0:
                    case 2:
                        cat = 215;
                        value -= this.mChooserListAdapter.getSelectableServiceTargetCount();
                        numCallerProvided = this.mChooserListAdapter.getCallerTargetCount();
                        break;
                    case 1:
                        cat = 216;
                        ChooserTarget target = ((ChooserTargetInfo) this.mChooserListAdapter.mServiceTargets.get(value)).getChooserTarget();
                        HashedStringCache instance = HashedStringCache.getInstance();
                        directTargetHashed = instance.hashString(this, TAG, target.getComponentName().getPackageName() + target.getTitle().toString(), this.mMaxHashSaltDays);
                        directTargetAlsoRanked = getRankedPosition((SelectableTargetInfo) targetInfo);
                        if (this.mCallerChooserTargets != null) {
                            numCallerProvided = this.mCallerChooserTargets.length;
                            break;
                        }
                        break;
                    case 3:
                        value = -1;
                        cat = 217;
                        break;
                }
                if (cat != 0) {
                    LogMaker targetLogMaker = new LogMaker(cat).setSubtype(value);
                    if (directTargetHashed != null) {
                        targetLogMaker.addTaggedData(MetricsProto.MetricsEvent.FIELD_HASHED_TARGET_NAME, directTargetHashed.hashedString);
                        targetLogMaker.addTaggedData(MetricsProto.MetricsEvent.FIELD_HASHED_TARGET_SALT_GEN, Integer.valueOf(directTargetHashed.saltGeneration));
                        targetLogMaker.addTaggedData(1087, Integer.valueOf(directTargetAlsoRanked));
                    }
                    targetLogMaker.addTaggedData(1086, Integer.valueOf(numCallerProvided));
                    getMetricsLogger().write(targetLogMaker);
                }
                if (this.mIsSuccessfullySelected) {
                    MetricsLogger.histogram((Context) null, "user_selection_cost_for_smart_sharing", (int) selectionCost);
                    MetricsLogger.histogram((Context) null, "app_position_for_smart_sharing", value);
                }
            }
        }
    }

    private int getRankedPosition(SelectableTargetInfo targetInfo) {
        String targetPackageName = targetInfo.getChooserTarget().getComponentName().getPackageName();
        int maxRankedResults = Math.min(this.mChooserListAdapter.mDisplayList.size(), 12);
        for (int i = 0; i < maxRankedResults; i++) {
            if (((ResolverActivity.DisplayResolveInfo) this.mChooserListAdapter.mDisplayList.get(i)).getResolveInfo().activityInfo.packageName.equals(targetPackageName)) {
                return i;
            }
        }
        return -1;
    }

    /* access modifiers changed from: package-private */
    public void queryTargetServices(ChooserListAdapter adapter) {
        String str;
        ChooserListAdapter chooserListAdapter = adapter;
        this.mQueriedTargetServicesTimeMs = System.currentTimeMillis();
        PackageManager pm = getPackageManager();
        ShortcutManager sm = (ShortcutManager) getSystemService(ShortcutManager.class);
        int i = 0;
        int N = adapter.getDisplayResolveInfoCount();
        int targetsToQuery = 0;
        while (i < N) {
            ResolverActivity.DisplayResolveInfo dri = chooserListAdapter.getDisplayResolveInfo(i);
            if (chooserListAdapter.getScore(dri) != 0.0f) {
                ActivityInfo ai = dri.getResolveInfo().activityInfo;
                if (!sm.hasShareTargets(ai.packageName)) {
                    Bundle md = ai.metaData;
                    if (md != null) {
                        str = convertServiceName(ai.packageName, md.getString(ChooserTargetService.META_DATA_NAME));
                    } else {
                        str = null;
                    }
                    String serviceName = str;
                    if (serviceName != null) {
                        ComponentName serviceComponent = new ComponentName(ai.packageName, serviceName);
                        if (this.mServicesRequested.contains(serviceComponent)) {
                            continue;
                        } else {
                            this.mServicesRequested.add(serviceComponent);
                            Intent serviceIntent = new Intent(ChooserTargetService.SERVICE_INTERFACE).setComponent(serviceComponent);
                            try {
                                if (!"android.permission.BIND_CHOOSER_TARGET_SERVICE".equals(pm.getServiceInfo(serviceComponent, 0).permission)) {
                                    Log.w(TAG, "ChooserTargetService " + serviceComponent + " does not require permission " + "android.permission.BIND_CHOOSER_TARGET_SERVICE" + " - this service will not be queried for ChooserTargets. add android:permission=\"" + "android.permission.BIND_CHOOSER_TARGET_SERVICE" + "\" to the <service> tag for " + serviceComponent + " in the manifest.");
                                } else {
                                    ChooserTargetServiceConnection conn = new ChooserTargetServiceConnection(this, dri);
                                    if (bindServiceAsUser(serviceIntent, conn, 5, Process.myUserHandle())) {
                                        this.mServiceConnections.add(conn);
                                        targetsToQuery++;
                                    }
                                }
                            } catch (PackageManager.NameNotFoundException e) {
                                Log.e(TAG, "Could not look up service " + serviceComponent + "; component name not found");
                            }
                        }
                    }
                    if (targetsToQuery >= 5) {
                        break;
                    }
                } else {
                    continue;
                }
            }
            i++;
            chooserListAdapter = adapter;
        }
        this.mChooserHandler.restartServiceRequestTimer();
    }

    private IntentFilter getTargetIntentFilter() {
        try {
            Intent intent = getTargetIntent();
            String dataString = intent.getDataString();
            if (TextUtils.isEmpty(dataString)) {
                dataString = intent.getType();
            }
            return new IntentFilter(intent.getAction(), dataString);
        } catch (Exception e) {
            Log.e(TAG, "failed to get target intent filter " + e);
            return null;
        }
    }

    private List<ResolverActivity.DisplayResolveInfo> getDisplayResolveInfos(ChooserListAdapter adapter) {
        List<ResolverActivity.DisplayResolveInfo> driList = new ArrayList<>();
        int targetsToQuery = 0;
        int n = adapter.getDisplayResolveInfoCount();
        for (int i = 0; i < n; i++) {
            ResolverActivity.DisplayResolveInfo dri = adapter.getDisplayResolveInfo(i);
            if (adapter.getScore(dri) != 0.0f) {
                driList.add(dri);
                targetsToQuery++;
                if (targetsToQuery >= 20) {
                    break;
                }
            }
        }
        return driList;
    }

    /* access modifiers changed from: private */
    public void queryDirectShareTargets(ChooserListAdapter adapter, boolean skipAppPredictionService) {
        AppPredictor appPredictor;
        this.mQueriedSharingShortcutsTimeMs = System.currentTimeMillis();
        if (skipAppPredictionService || (appPredictor = getAppPredictorForDirectShareIfEnabled()) == null) {
            IntentFilter filter = getTargetIntentFilter();
            if (filter != null) {
                AsyncTask.execute((Runnable) new Runnable(filter, getDisplayResolveInfos(adapter)) {
                    private final /* synthetic */ IntentFilter f$1;
                    private final /* synthetic */ List f$2;

                    {
                        this.f$1 = r2;
                        this.f$2 = r3;
                    }

                    public final void run() {
                        ChooserActivity.this.sendShareShortcutInfoList(((ShortcutManager) ChooserActivity.this.getSystemService("shortcut")).getShareTargets(this.f$1), this.f$2, (List<AppTarget>) null);
                    }
                });
                return;
            }
            return;
        }
        appPredictor.requestPredictionUpdate();
    }

    /* access modifiers changed from: private */
    public void sendShareShortcutInfoList(List<ShortcutManager.ShareShortcutInfo> resultList, List<ResolverActivity.DisplayResolveInfo> driList, List<AppTarget> appTargets) {
        if (appTargets == null || appTargets.size() == resultList.size()) {
            for (int i = resultList.size() - 1; i >= 0; i--) {
                if (!isPackageEnabled(resultList.get(i).getTargetComponent().getPackageName())) {
                    resultList.remove(i);
                    if (appTargets != null) {
                        appTargets.remove(i);
                    }
                }
            }
            boolean resultMessageSent = false;
            for (int i2 = 0; i2 < driList.size(); i2++) {
                List<ChooserTarget> chooserTargets = new ArrayList<>();
                for (int j = 0; j < resultList.size(); j++) {
                    if (driList.get(i2).getResolvedComponentName().equals(resultList.get(j).getTargetComponent())) {
                        ChooserTarget chooserTarget = convertToChooserTarget(resultList.get(j), Math.max(1.0f - (((float) j) * 0.05f), 0.0f));
                        chooserTargets.add(chooserTarget);
                        if (!(this.mDirectShareAppTargetCache == null || appTargets == null)) {
                            this.mDirectShareAppTargetCache.put(chooserTarget, appTargets.get(j));
                        }
                    }
                }
                if (chooserTargets.isEmpty() == 0) {
                    Message msg = Message.obtain();
                    msg.what = 4;
                    msg.obj = new ServiceResultInfo(driList.get(i2), chooserTargets, (ChooserTargetServiceConnection) null);
                    this.mChooserHandler.sendMessage(msg);
                    resultMessageSent = true;
                }
            }
            if (resultMessageSent) {
                sendShortcutManagerShareTargetResultCompleted();
                return;
            }
            return;
        }
        throw new RuntimeException("resultList and appTargets must have the same size. resultList.size()=" + resultList.size() + " appTargets.size()=" + appTargets.size());
    }

    private void sendShortcutManagerShareTargetResultCompleted() {
        Message msg = Message.obtain();
        msg.what = 5;
        this.mChooserHandler.sendMessage(msg);
    }

    private boolean isPackageEnabled(String packageName) {
        if (TextUtils.isEmpty(packageName)) {
            return false;
        }
        try {
            ApplicationInfo appInfo = getPackageManager().getApplicationInfo(packageName, 0);
            if (appInfo == null || !appInfo.enabled || (appInfo.flags & 1073741824) != 0) {
                return false;
            }
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    private ChooserTarget convertToChooserTarget(ShortcutManager.ShareShortcutInfo shareShortcut, float score) {
        ShortcutInfo shortcutInfo = shareShortcut.getShortcutInfo();
        Bundle extras = new Bundle();
        extras.putString(Intent.EXTRA_SHORTCUT_ID, shortcutInfo.getId());
        return new ChooserTarget(shortcutInfo.getShortLabel(), (Icon) null, score, shareShortcut.getTargetComponent().clone(), extras);
    }

    private String convertServiceName(String packageName, String serviceName) {
        String fullName = null;
        if (TextUtils.isEmpty(serviceName)) {
            return null;
        }
        if (serviceName.startsWith(".")) {
            fullName = packageName + serviceName;
        } else if (serviceName.indexOf(46) >= 0) {
            fullName = serviceName;
        }
        return fullName;
    }

    /* access modifiers changed from: package-private */
    public void unbindRemainingServices() {
        int N = this.mServiceConnections.size();
        for (int i = 0; i < N; i++) {
            ChooserTargetServiceConnection conn = this.mServiceConnections.get(i);
            unbindService(conn);
            conn.destroy();
        }
        this.mServicesRequested.clear();
        this.mServiceConnections.clear();
    }

    public void onSetupVoiceInteraction() {
    }

    /* access modifiers changed from: private */
    public void logDirectShareTargetReceived(int logCategory) {
        getMetricsLogger().write(new LogMaker(logCategory).setSubtype((int) (System.currentTimeMillis() - (logCategory == 1718 ? this.mQueriedSharingShortcutsTimeMs : this.mQueriedTargetServicesTimeMs))));
    }

    /* access modifiers changed from: package-private */
    public void updateModelAndChooserCounts(ResolverActivity.TargetInfo info) {
        if (info != null) {
            sendClickToAppPredictor(info);
            ResolveInfo ri = info.getResolveInfo();
            Intent targetIntent = getTargetIntent();
            if (!(ri == null || ri.activityInfo == null || targetIntent == null || this.mAdapter == null)) {
                this.mAdapter.updateModel(info.getResolvedComponentName());
                this.mAdapter.updateChooserCounts(ri.activityInfo.packageName, getUserId(), targetIntent.getAction());
            }
        }
        this.mIsSuccessfullySelected = true;
    }

    private void sendClickToAppPredictor(ResolverActivity.TargetInfo targetInfo) {
        AppPredictor directShareAppPredictor = getAppPredictorForDirectShareIfEnabled();
        if (directShareAppPredictor != null && (targetInfo instanceof ChooserTargetInfo)) {
            ChooserTarget chooserTarget = ((ChooserTargetInfo) targetInfo).getChooserTarget();
            AppTarget appTarget = null;
            if (this.mDirectShareAppTargetCache != null) {
                appTarget = this.mDirectShareAppTargetCache.get(chooserTarget);
            }
            if (appTarget != null) {
                directShareAppPredictor.notifyAppTargetEvent(new AppTargetEvent.Builder(appTarget, 1).setLaunchLocation(LAUNCH_LOCATON_DIRECT_SHARE).build());
            }
        }
    }

    private AppPredictor getAppPredictor() {
        if (this.mAppPredictor == null && getPackageManager().getAppPredictionServicePackageName() != null) {
            IntentFilter filter = getTargetIntentFilter();
            Bundle extras = new Bundle();
            extras.putParcelable(APP_PREDICTION_INTENT_FILTER_KEY, filter);
            this.mAppPredictor = ((AppPredictionManager) getSystemService(AppPredictionManager.class)).createAppPredictionSession(new AppPredictionContext.Builder(this).setUiSurface(APP_PREDICTION_SHARE_UI_SURFACE).setPredictedTargetCount(20).setExtras(extras).build());
        }
        return this.mAppPredictor;
    }

    /* access modifiers changed from: private */
    public AppPredictor getAppPredictorForDirectShareIfEnabled() {
        if (!ActivityManager.isLowRamDeviceStatic()) {
            return getAppPredictor();
        }
        return null;
    }

    private AppPredictor getAppPredictorForShareActivitesIfEnabled() {
        return getAppPredictor();
    }

    /* access modifiers changed from: package-private */
    public void onRefinementResult(ResolverActivity.TargetInfo selectedTarget, Intent matchingIntent) {
        if (this.mRefinementResultReceiver != null) {
            this.mRefinementResultReceiver.destroy();
            this.mRefinementResultReceiver = null;
        }
        if (selectedTarget == null) {
            Log.e(TAG, "Refinement result intent did not match any known targets; canceling");
        } else if (!checkTargetSourceIntent(selectedTarget, matchingIntent)) {
            Log.e(TAG, "onRefinementResult: Selected target " + selectedTarget + " cannot match refined source intent " + matchingIntent);
        } else {
            ResolverActivity.TargetInfo clonedTarget = selectedTarget.cloneFilledIn(matchingIntent, 0);
            if (super.onTargetSelected(clonedTarget, false)) {
                updateModelAndChooserCounts(clonedTarget);
                finish();
                return;
            }
        }
        onRefinementCanceled();
    }

    /* access modifiers changed from: package-private */
    public void onRefinementCanceled() {
        if (this.mRefinementResultReceiver != null) {
            this.mRefinementResultReceiver.destroy();
            this.mRefinementResultReceiver = null;
        }
        finish();
    }

    /* access modifiers changed from: package-private */
    public boolean checkTargetSourceIntent(ResolverActivity.TargetInfo target, Intent matchingIntent) {
        List<Intent> targetIntents = target.getAllSourceIntents();
        int N = targetIntents.size();
        for (int i = 0; i < N; i++) {
            if (targetIntents.get(i).filterEquals(matchingIntent)) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public void filterServiceTargets(String packageName, List<ChooserTarget> targets) {
        if (targets != null) {
            PackageManager pm = getPackageManager();
            for (int i = targets.size() - 1; i >= 0; i--) {
                ChooserTarget target = targets.get(i);
                ComponentName targetName = target.getComponentName();
                if (packageName == null || !packageName.equals(targetName.getPackageName())) {
                    PackageManager.NameNotFoundException e = null;
                    try {
                        ActivityInfo ai = pm.getActivityInfo(targetName, 0);
                        if (!ai.exported || ai.permission != null) {
                            e = 1;
                        }
                    } catch (PackageManager.NameNotFoundException e2) {
                        Log.e(TAG, "Target " + target + " returned by " + packageName + " component not found");
                        e = 1;
                    }
                    if (e != null) {
                        targets.remove(i);
                    }
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public void updateAlphabeticalList() {
        this.mSortedList.clear();
        this.mSortedList.addAll(getDisplayList());
        Collections.sort(this.mSortedList, new AzInfoComparator(this));
    }

    class AzInfoComparator implements Comparator<ResolverActivity.DisplayResolveInfo> {
        Collator mCollator;

        AzInfoComparator(Context context) {
            this.mCollator = Collator.getInstance(context.getResources().getConfiguration().locale);
        }

        public int compare(ResolverActivity.DisplayResolveInfo lhsp, ResolverActivity.DisplayResolveInfo rhsp) {
            return this.mCollator.compare(lhsp.getDisplayLabel(), rhsp.getDisplayLabel());
        }
    }

    /* access modifiers changed from: protected */
    public MetricsLogger getMetricsLogger() {
        if (this.mMetricsLogger == null) {
            this.mMetricsLogger = new MetricsLogger();
        }
        return this.mMetricsLogger;
    }

    public class ChooserListController extends ResolverListController {
        public ChooserListController(Context context, PackageManager pm, Intent targetIntent, String referrerPackageName, int launchedFromUid, AbstractResolverComparator resolverComparator) {
            super(context, pm, targetIntent, referrerPackageName, launchedFromUid, resolverComparator);
        }

        /* access modifiers changed from: package-private */
        public boolean isComponentFiltered(ComponentName name) {
            if (ChooserActivity.this.mFilteredComponentNames == null) {
                return false;
            }
            for (ComponentName filteredComponentName : ChooserActivity.this.mFilteredComponentNames) {
                if (name.equals(filteredComponentName)) {
                    return true;
                }
            }
            return false;
        }
    }

    public ResolverActivity.ResolveListAdapter createAdapter(Context context, List<Intent> payloadIntents, Intent[] initialIntents, List<ResolveInfo> rList, int launchedFromUid, boolean filterLastUsed) {
        return new ChooserListAdapter(this, context, payloadIntents, initialIntents, rList, launchedFromUid, filterLastUsed, createListController());
    }

    /* access modifiers changed from: protected */
    @VisibleForTesting
    public ResolverListController createListController() {
        AbstractResolverComparator resolverComparator;
        AppPredictor appPredictor = getAppPredictorForShareActivitesIfEnabled();
        if (appPredictor != null) {
            resolverComparator = new AppPredictionServiceResolverComparator(this, getTargetIntent(), getReferrerPackageName(), appPredictor, getUser());
        } else {
            resolverComparator = new ResolverRankerServiceResolverComparator(this, getTargetIntent(), getReferrerPackageName(), (AbstractResolverComparator.AfterCompute) null);
        }
        return new ChooserListController(this, this.mPm, getTargetIntent(), getReferrerPackageName(), this.mLaunchedFromUid, resolverComparator);
    }

    /* access modifiers changed from: protected */
    @VisibleForTesting
    public Bitmap loadThumbnail(Uri uri, Size size) {
        if (uri == null || size == null) {
            return null;
        }
        try {
            return ImageUtils.loadThumbnail(getContentResolver(), uri, size);
        } catch (IOException | NullPointerException | SecurityException e) {
            logContentPreviewWarning(uri);
            return null;
        }
    }

    interface ChooserTargetInfo extends ResolverActivity.TargetInfo {
        ChooserTarget getChooserTarget();

        float getModifiedScore();

        boolean isSimilar(ChooserTargetInfo other) {
            if (other == null) {
                return false;
            }
            ChooserTarget ct1 = getChooserTarget();
            ChooserTarget ct2 = other.getChooserTarget();
            if (ct1 == null || ct2 == null || !ct1.getComponentName().equals(ct2.getComponentName()) || !TextUtils.equals(getDisplayLabel(), other.getDisplayLabel()) || !TextUtils.equals(getExtendedInfo(), other.getExtendedInfo())) {
                return false;
            }
            return true;
        }
    }

    abstract class NotSelectableTargetInfo implements ChooserTargetInfo {
        NotSelectableTargetInfo() {
        }

        public Intent getResolvedIntent() {
            return null;
        }

        public ComponentName getResolvedComponentName() {
            return null;
        }

        public boolean start(Activity activity, Bundle options) {
            return false;
        }

        public boolean startAsCaller(ResolverActivity activity, Bundle options, int userId) {
            return false;
        }

        public boolean startAsUser(Activity activity, Bundle options, UserHandle user) {
            return false;
        }

        public ResolveInfo getResolveInfo() {
            return null;
        }

        public CharSequence getDisplayLabel() {
            return null;
        }

        public CharSequence getExtendedInfo() {
            return null;
        }

        public ResolverActivity.TargetInfo cloneFilledIn(Intent fillInIntent, int flags) {
            return null;
        }

        public List<Intent> getAllSourceIntents() {
            return null;
        }

        public float getModifiedScore() {
            return -0.1f;
        }

        public ChooserTarget getChooserTarget() {
            return null;
        }

        public boolean isSuspended() {
            return false;
        }
    }

    final class PlaceHolderTargetInfo extends NotSelectableTargetInfo {
        PlaceHolderTargetInfo() {
            super();
        }

        public Drawable getDisplayIcon() {
            AnimatedVectorDrawable avd = (AnimatedVectorDrawable) ChooserActivity.this.getDrawable(R.drawable.chooser_direct_share_icon_placeholder);
            avd.start();
            return avd;
        }
    }

    final class EmptyTargetInfo extends NotSelectableTargetInfo {
        EmptyTargetInfo() {
            super();
        }

        public Drawable getDisplayIcon() {
            return null;
        }
    }

    final class SelectableTargetInfo implements ChooserTargetInfo {
        private final ResolveInfo mBackupResolveInfo;
        private CharSequence mBadgeContentDescription;
        private Drawable mBadgeIcon = null;
        private final ChooserTarget mChooserTarget;
        private Drawable mDisplayIcon;
        private final String mDisplayLabel;
        private final int mFillInFlags;
        private final Intent mFillInIntent;
        private boolean mIsSuspended = false;
        private final float mModifiedScore;
        private final ResolverActivity.DisplayResolveInfo mSourceInfo;

        SelectableTargetInfo(ResolverActivity.DisplayResolveInfo sourceInfo, ChooserTarget chooserTarget, float modifiedScore) {
            ResolveInfo ri;
            ActivityInfo ai;
            this.mSourceInfo = sourceInfo;
            this.mChooserTarget = chooserTarget;
            this.mModifiedScore = modifiedScore;
            if (!(sourceInfo == null || (ri = sourceInfo.getResolveInfo()) == null || (ai = ri.activityInfo) == null || ai.applicationInfo == null)) {
                PackageManager pm = ChooserActivity.this.getPackageManager();
                this.mBadgeIcon = pm.getApplicationIcon(ai.applicationInfo);
                this.mBadgeContentDescription = pm.getApplicationLabel(ai.applicationInfo);
                this.mIsSuspended = (ai.applicationInfo.flags & 1073741824) != 0;
            }
            this.mDisplayIcon = getChooserTargetIconDrawable(chooserTarget);
            if (sourceInfo != null) {
                this.mBackupResolveInfo = null;
            } else {
                this.mBackupResolveInfo = ChooserActivity.this.getPackageManager().resolveActivity(getResolvedIntent(), 0);
            }
            this.mFillInIntent = null;
            this.mFillInFlags = 0;
            this.mDisplayLabel = sanitizeDisplayLabel(chooserTarget.getTitle());
        }

        private SelectableTargetInfo(SelectableTargetInfo other, Intent fillInIntent, int flags) {
            this.mSourceInfo = other.mSourceInfo;
            this.mBackupResolveInfo = other.mBackupResolveInfo;
            this.mChooserTarget = other.mChooserTarget;
            this.mBadgeIcon = other.mBadgeIcon;
            this.mBadgeContentDescription = other.mBadgeContentDescription;
            this.mDisplayIcon = other.mDisplayIcon;
            this.mFillInIntent = fillInIntent;
            this.mFillInFlags = flags;
            this.mModifiedScore = other.mModifiedScore;
            this.mDisplayLabel = sanitizeDisplayLabel(this.mChooserTarget.getTitle());
        }

        private String sanitizeDisplayLabel(CharSequence label) {
            SpannableStringBuilder sb = new SpannableStringBuilder(label);
            sb.clearSpans();
            return sb.toString();
        }

        public boolean isSuspended() {
            return this.mIsSuspended;
        }

        private Drawable getChooserTargetIconDrawable(ChooserTarget target) {
            Drawable directShareIcon = null;
            Icon icon = target.getIcon();
            if (icon != null) {
                directShareIcon = icon.loadDrawable(ChooserActivity.this);
            } else {
                Bundle extras = target.getIntentExtras();
                if (extras != null && extras.containsKey(Intent.EXTRA_SHORTCUT_ID)) {
                    CharSequence shortcutId = extras.getCharSequence(Intent.EXTRA_SHORTCUT_ID);
                    LauncherApps launcherApps = (LauncherApps) ChooserActivity.this.getSystemService(Context.LAUNCHER_APPS_SERVICE);
                    LauncherApps.ShortcutQuery q = new LauncherApps.ShortcutQuery();
                    q.setPackage(target.getComponentName().getPackageName());
                    q.setShortcutIds(Arrays.asList(new String[]{shortcutId.toString()}));
                    q.setQueryFlags(1);
                    List<ShortcutInfo> shortcuts = launcherApps.getShortcuts(q, ChooserActivity.this.getUser());
                    if (shortcuts != null && shortcuts.size() > 0) {
                        directShareIcon = launcherApps.getShortcutIconDrawable(shortcuts.get(0), 0);
                    }
                }
            }
            if (directShareIcon == null) {
                return null;
            }
            ActivityInfo info = null;
            try {
                info = ChooserActivity.this.mPm.getActivityInfo(target.getComponentName(), 0);
            } catch (PackageManager.NameNotFoundException e) {
                Log.e(ChooserActivity.TAG, "Could not find activity associated with ChooserTarget");
            }
            if (info == null) {
                return null;
            }
            Bitmap appIcon = ChooserActivity.this.makePresentationGetter(info).getIconBitmap(UserHandle.getUserHandleForUid(UserHandle.myUserId()));
            SimpleIconFactory sif = SimpleIconFactory.obtain(ChooserActivity.this);
            Bitmap directShareBadgedIcon = sif.createAppBadgedIconBitmap(directShareIcon, appIcon);
            sif.recycle();
            return new BitmapDrawable(ChooserActivity.this.getResources(), directShareBadgedIcon);
        }

        public float getModifiedScore() {
            return this.mModifiedScore;
        }

        public Intent getResolvedIntent() {
            if (this.mSourceInfo != null) {
                return this.mSourceInfo.getResolvedIntent();
            }
            Intent targetIntent = new Intent(ChooserActivity.this.getTargetIntent());
            targetIntent.setComponent(this.mChooserTarget.getComponentName());
            targetIntent.putExtras(this.mChooserTarget.getIntentExtras());
            return targetIntent;
        }

        public ComponentName getResolvedComponentName() {
            if (this.mSourceInfo != null) {
                return this.mSourceInfo.getResolvedComponentName();
            }
            if (this.mBackupResolveInfo != null) {
                return new ComponentName(this.mBackupResolveInfo.activityInfo.packageName, this.mBackupResolveInfo.activityInfo.name);
            }
            return null;
        }

        private Intent getBaseIntentToSend() {
            Intent result = getResolvedIntent();
            if (result == null) {
                Log.e(ChooserActivity.TAG, "ChooserTargetInfo: no base intent available to send");
            } else {
                result = new Intent(result);
                if (this.mFillInIntent != null) {
                    result.fillIn(this.mFillInIntent, this.mFillInFlags);
                }
                result.fillIn(ChooserActivity.this.mReferrerFillInIntent, 0);
            }
            return result;
        }

        public boolean start(Activity activity, Bundle options) {
            throw new RuntimeException("ChooserTargets should be started as caller.");
        }

        public boolean startAsCaller(ResolverActivity activity, Bundle options, int userId) {
            Intent intent = getBaseIntentToSend();
            boolean ignoreTargetSecurity = false;
            if (intent == null) {
                return false;
            }
            intent.setComponent(this.mChooserTarget.getComponentName());
            intent.putExtras(this.mChooserTarget.getIntentExtras());
            if (this.mSourceInfo != null && this.mSourceInfo.getResolvedComponentName().getPackageName().equals(this.mChooserTarget.getComponentName().getPackageName())) {
                ignoreTargetSecurity = true;
            }
            return activity.startAsCallerImpl(intent, options, ignoreTargetSecurity, userId);
        }

        public boolean startAsUser(Activity activity, Bundle options, UserHandle user) {
            throw new RuntimeException("ChooserTargets should be started as caller.");
        }

        public ResolveInfo getResolveInfo() {
            return this.mSourceInfo != null ? this.mSourceInfo.getResolveInfo() : this.mBackupResolveInfo;
        }

        public CharSequence getDisplayLabel() {
            return this.mDisplayLabel;
        }

        public CharSequence getExtendedInfo() {
            return null;
        }

        public Drawable getDisplayIcon() {
            return this.mDisplayIcon;
        }

        public ChooserTarget getChooserTarget() {
            return this.mChooserTarget;
        }

        public ResolverActivity.TargetInfo cloneFilledIn(Intent fillInIntent, int flags) {
            return new SelectableTargetInfo(this, fillInIntent, flags);
        }

        public List<Intent> getAllSourceIntents() {
            List<Intent> results = new ArrayList<>();
            if (this.mSourceInfo != null) {
                results.add(this.mSourceInfo.getAllSourceIntents().get(0));
            }
            return results;
        }
    }

    /* access modifiers changed from: private */
    public void handleScroll(View view, int x, int y, int oldx, int oldy) {
        if (this.mChooserRowAdapter != null) {
            this.mChooserRowAdapter.handleScroll(view, y, oldy);
        }
    }

    /* access modifiers changed from: private */
    public void handleLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        if (this.mChooserRowAdapter != null && this.mAdapterView != null) {
            int availableWidth = ((right - left) - v.getPaddingLeft()) - v.getPaddingRight();
            if (this.mChooserRowAdapter.consumeLayoutRequest() || this.mChooserRowAdapter.calculateChooserTargetWidth(availableWidth) || this.mAdapterView.getAdapter() == null || availableWidth != this.mCurrAvailableWidth) {
                this.mCurrAvailableWidth = availableWidth;
                this.mAdapterView.setAdapter((ListAdapter) this.mChooserRowAdapter);
                getMainThreadHandler().post(new Runnable(bottom, top) {
                    private final /* synthetic */ int f$1;
                    private final /* synthetic */ int f$2;

                    {
                        this.f$1 = r2;
                        this.f$2 = r3;
                    }

                    public final void run() {
                        ChooserActivity.lambda$handleLayoutChange$2(ChooserActivity.this, this.f$1, this.f$2);
                    }
                });
            }
        }
    }

    public static /* synthetic */ void lambda$handleLayoutChange$2(ChooserActivity chooserActivity, int bottom, int top) {
        if (chooserActivity.mResolverDrawerLayout != null && chooserActivity.mChooserRowAdapter != null) {
            int topInset = 0;
            int bottomInset = chooserActivity.mSystemWindowInsets != null ? chooserActivity.mSystemWindowInsets.bottom : 0;
            int offset = bottomInset;
            int rowsToShow = chooserActivity.mChooserRowAdapter.getContentPreviewRowCount() + chooserActivity.mChooserRowAdapter.getProfileRowCount() + chooserActivity.mChooserRowAdapter.getServiceTargetRowCount() + chooserActivity.mChooserRowAdapter.getCallerAndRankedTargetRowCount();
            if (rowsToShow == 0) {
                rowsToShow = chooserActivity.mChooserRowAdapter.getCount();
            }
            if (rowsToShow == 0) {
                chooserActivity.mResolverDrawerLayout.setCollapsibleHeightReserved(offset + chooserActivity.getResources().getDimensionPixelSize(R.dimen.chooser_max_collapsed_height));
                return;
            }
            int rowsToShow2 = Math.min(4, rowsToShow);
            int directShareHeight = 0;
            int offset2 = offset;
            for (int i = 0; i < Math.min(rowsToShow2, chooserActivity.mAdapterView.getChildCount()); i++) {
                View child = chooserActivity.mAdapterView.getChildAt(i);
                int height = child.getHeight();
                offset2 += height;
                if (child.getTag() != null && (child.getTag() instanceof DirectShareViewHolder)) {
                    directShareHeight = height;
                }
            }
            boolean z = true;
            if (chooserActivity.getResources().getConfiguration().orientation != 1 || chooserActivity.isInMultiWindowMode()) {
                z = false;
            }
            boolean isExpandable = z;
            if (directShareHeight != 0 && chooserActivity.isSendAction(chooserActivity.getTargetIntent()) && isExpandable) {
                int requiredExpansionHeight = (int) (((float) directShareHeight) / DIRECT_SHARE_EXPANSION_RATE);
                if (chooserActivity.mSystemWindowInsets != null) {
                    topInset = chooserActivity.mSystemWindowInsets.top;
                }
                offset2 = Math.min(offset2, ((((bottom - top) - chooserActivity.mResolverDrawerLayout.getAlwaysShowHeight()) - requiredExpansionHeight) - topInset) - bottomInset);
            }
            chooserActivity.mResolverDrawerLayout.setCollapsibleHeightReserved(Math.min(offset2, bottom - top));
        }
    }

    public class ChooserListAdapter extends ResolverActivity.ResolveListAdapter {
        private static final int MAX_CHOOSER_TARGETS_PER_APP = 2;
        private static final int MAX_SERVICE_TARGETS = 8;
        private static final int MAX_SUGGESTED_APP_TARGETS = 4;
        public static final int TARGET_BAD = -1;
        public static final int TARGET_CALLER = 0;
        public static final int TARGET_SERVICE = 1;
        public static final int TARGET_STANDARD = 2;
        public static final int TARGET_STANDARD_AZ = 3;
        private final BaseChooserTargetComparator mBaseTargetComparator;
        private final List<ResolverActivity.TargetInfo> mCallerTargets;
        private final int mMaxShortcutTargetsPerApp = this.this$0.getResources().getInteger(R.integer.config_maxShortcutTargetsPerApp);
        private int mNumShortcutResults;
        private ChooserTargetInfo mPlaceHolderTargetInfo;
        /* access modifiers changed from: private */
        public final List<ChooserTargetInfo> mServiceTargets;
        final /* synthetic */ ChooserActivity this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public ChooserListAdapter(ChooserActivity this$02, Context context, List<Intent> payloadIntents, Intent[] initialIntents, List<ResolveInfo> rList, int launchedFromUid, boolean filterLastUsed, ResolverListController resolverListController) {
            super(context, payloadIntents, (Intent[]) null, rList, launchedFromUid, filterLastUsed, resolverListController);
            ResolveInfo ri;
            ChooserActivity chooserActivity = this$02;
            Intent[] intentArr = initialIntents;
            this.this$0 = chooserActivity;
            int i = 0;
            this.mNumShortcutResults = 0;
            this.mPlaceHolderTargetInfo = new PlaceHolderTargetInfo();
            this.mServiceTargets = new ArrayList();
            this.mCallerTargets = new ArrayList();
            this.mBaseTargetComparator = new BaseChooserTargetComparator();
            createPlaceHolders();
            if (intentArr != null) {
                PackageManager pm = this$02.getPackageManager();
                int i2 = 0;
                while (true) {
                    int i3 = i2;
                    if (i3 < intentArr.length) {
                        Intent ii = intentArr[i3];
                        if (ii != null) {
                            ResolveInfo ri2 = null;
                            ActivityInfo ai = null;
                            if (ii.getComponent() != null) {
                                try {
                                    ai = pm.getActivityInfo(ii.getComponent(), i);
                                    ri2 = new ResolveInfo();
                                    ri2.activityInfo = ai;
                                } catch (PackageManager.NameNotFoundException e) {
                                }
                            }
                            if (ai == null) {
                                ResolveInfo ri3 = pm.resolveActivity(ii, 65536);
                                ai = ri3 != null ? ri3.activityInfo : null;
                                ri = ri3;
                            } else {
                                ri = ri2;
                            }
                            ActivityInfo ai2 = ai;
                            if (ai2 == null) {
                                Log.w(ChooserActivity.TAG, "No activity found for " + ii);
                            } else {
                                UserManager userManager = (UserManager) chooserActivity.getSystemService("user");
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
                                ResolverActivity.ResolveInfoPresentationGetter getter = chooserActivity.makePresentationGetter(ri);
                                List<ResolverActivity.TargetInfo> list = this.mCallerTargets;
                                ResolverActivity.DisplayResolveInfo displayResolveInfo = r1;
                                ActivityInfo activityInfo = ai2;
                                ResolveInfo resolveInfo = ri;
                                ResolverActivity.DisplayResolveInfo displayResolveInfo2 = new ResolverActivity.DisplayResolveInfo(ii, ri, getter.getLabel(), getter.getSubLabel(), ii);
                                list.add(displayResolveInfo);
                            }
                        }
                        i2 = i3 + 1;
                        i = 0;
                    } else {
                        return;
                    }
                }
            }
        }

        public void handlePackagesChanged() {
            createPlaceHolders();
            this.this$0.mServicesRequested.clear();
            notifyDataSetChanged();
            super.handlePackagesChanged();
        }

        public void notifyDataSetChanged() {
            if (!this.this$0.mListViewDataChanged) {
                this.this$0.mChooserHandler.sendEmptyMessageDelayed(6, 250);
                boolean unused = this.this$0.mListViewDataChanged = true;
            }
        }

        /* access modifiers changed from: private */
        public void refreshListView() {
            if (this.this$0.mListViewDataChanged) {
                super.notifyDataSetChanged();
            }
            boolean unused = this.this$0.mListViewDataChanged = false;
        }

        private void createPlaceHolders() {
            this.mNumShortcutResults = 0;
            this.mServiceTargets.clear();
            for (int i = 0; i < 8; i++) {
                this.mServiceTargets.add(this.mPlaceHolderTargetInfo);
            }
        }

        public View onCreateView(ViewGroup parent) {
            return this.mInflater.inflate((int) R.layout.resolve_grid_item, parent, false);
        }

        /* access modifiers changed from: protected */
        public void onBindView(View view, ResolverActivity.TargetInfo info) {
            super.onBindView(view, info);
            ResolverActivity.ViewHolder holder = (ResolverActivity.ViewHolder) view.getTag();
            if (info instanceof PlaceHolderTargetInfo) {
                holder.text.setMaxWidth(this.this$0.getResources().getDimensionPixelSize(R.dimen.chooser_direct_share_label_placeholder_max_width));
                holder.text.setBackground(this.this$0.getResources().getDrawable(R.drawable.chooser_direct_share_label_placeholder, this.this$0.getTheme()));
                holder.itemView.setBackground((Drawable) null);
                return;
            }
            holder.text.setMaxWidth(Integer.MAX_VALUE);
            holder.text.setBackground((Drawable) null);
            holder.itemView.setBackground(holder.defaultItemViewBackground);
        }

        public void onListRebuilt() {
            this.this$0.updateAlphabeticalList();
            if (!ActivityManager.isLowRamDeviceStatic()) {
                this.this$0.queryDirectShareTargets(this, false);
                this.this$0.queryTargetServices(this);
            }
        }

        public boolean shouldGetResolvedFilter() {
            return true;
        }

        public int getCount() {
            return getRankedTargetCount() + getAlphaTargetCount() + getSelectableServiceTargetCount() + getCallerTargetCount();
        }

        public int getUnfilteredCount() {
            int appTargets = super.getUnfilteredCount();
            if (appTargets > getMaxRankedTargets()) {
                appTargets += getMaxRankedTargets();
            }
            return getSelectableServiceTargetCount() + appTargets + getCallerTargetCount();
        }

        public int getCallerTargetCount() {
            return Math.min(this.mCallerTargets.size(), 4);
        }

        public int getSelectableServiceTargetCount() {
            int count = 0;
            for (ChooserTargetInfo info : this.mServiceTargets) {
                if (info instanceof SelectableTargetInfo) {
                    count++;
                }
            }
            return count;
        }

        public int getServiceTargetCount() {
            if (!this.this$0.isSendAction(this.this$0.getTargetIntent()) || ActivityManager.isLowRamDeviceStatic()) {
                return 0;
            }
            return Math.min(this.mServiceTargets.size(), 8);
        }

        /* access modifiers changed from: package-private */
        public int getAlphaTargetCount() {
            int standardCount = super.getCount();
            if (standardCount > getMaxRankedTargets()) {
                return standardCount;
            }
            return 0;
        }

        /* access modifiers changed from: package-private */
        public int getRankedTargetCount() {
            return Math.min(getMaxRankedTargets() - getCallerTargetCount(), super.getCount());
        }

        private int getMaxRankedTargets() {
            if (this.this$0.mChooserRowAdapter == null) {
                return 4;
            }
            return this.this$0.mChooserRowAdapter.getMaxTargetsPerRow();
        }

        public int getPositionTargetType(int position) {
            int serviceTargetCount = getServiceTargetCount();
            if (position < serviceTargetCount) {
                return 1;
            }
            int offset = 0 + serviceTargetCount;
            int callerTargetCount = getCallerTargetCount();
            if (position - offset < callerTargetCount) {
                return 0;
            }
            int offset2 = offset + callerTargetCount;
            int rankedTargetCount = getRankedTargetCount();
            if (position - offset2 < rankedTargetCount) {
                return 2;
            }
            if (position - (offset2 + rankedTargetCount) < getAlphaTargetCount()) {
                return 3;
            }
            return -1;
        }

        public ResolverActivity.TargetInfo getItem(int position) {
            return targetInfoForPosition(position, true);
        }

        public ResolverActivity.TargetInfo targetInfoForPosition(int position, boolean filtered) {
            int serviceTargetCount;
            if (filtered) {
                serviceTargetCount = getServiceTargetCount();
            } else {
                serviceTargetCount = getSelectableServiceTargetCount();
            }
            if (position < serviceTargetCount) {
                return this.mServiceTargets.get(position);
            }
            int offset = 0 + serviceTargetCount;
            int callerTargetCount = getCallerTargetCount();
            if (position - offset < callerTargetCount) {
                return this.mCallerTargets.get(position - offset);
            }
            int offset2 = offset + callerTargetCount;
            int rankedTargetCount = getRankedTargetCount();
            if (position - offset2 >= rankedTargetCount) {
                int offset3 = offset2 + rankedTargetCount;
                if (position - offset3 >= getAlphaTargetCount() || this.this$0.mSortedList.isEmpty()) {
                    return null;
                }
                return (ResolverActivity.TargetInfo) this.this$0.mSortedList.get(position - offset3);
            } else if (filtered) {
                return super.getItem(position - offset2);
            } else {
                return getDisplayResolveInfo(position - offset2);
            }
        }

        public void addServiceResults(ResolverActivity.DisplayResolveInfo origTarget, List<ChooserTarget> targets, boolean isShortcutResult) {
            int maxTargets;
            if (targets.size() != 0) {
                float baseScore = getBaseScore(origTarget, isShortcutResult);
                Collections.sort(targets, this.mBaseTargetComparator);
                if (isShortcutResult) {
                    maxTargets = this.mMaxShortcutTargetsPerApp;
                } else {
                    maxTargets = 2;
                }
                float lastScore = 0.0f;
                boolean shouldNotify = false;
                int count = Math.min(targets.size(), maxTargets);
                for (int i = 0; i < count; i++) {
                    ChooserTarget target = targets.get(i);
                    float targetScore = target.getScore() * baseScore;
                    if (i > 0 && targetScore >= lastScore) {
                        targetScore = lastScore * 0.95f;
                    }
                    boolean isInserted = insertServiceTarget(new SelectableTargetInfo(origTarget, target, targetScore));
                    if (isInserted && isShortcutResult) {
                        this.mNumShortcutResults++;
                    }
                    shouldNotify |= isInserted;
                    lastScore = targetScore;
                }
                if (shouldNotify) {
                    notifyDataSetChanged();
                }
            }
        }

        /* access modifiers changed from: private */
        public int getNumShortcutResults() {
            return this.mNumShortcutResults;
        }

        private float getBaseScore(ResolverActivity.DisplayResolveInfo target, boolean isShortcutResult) {
            if (target == null) {
                return ChooserActivity.CALLER_TARGET_SCORE_BOOST;
            }
            if (isShortcutResult && this.this$0.getAppPredictorForDirectShareIfEnabled() != null) {
                return ChooserActivity.SHORTCUT_TARGET_SCORE_BOOST;
            }
            float score = super.getScore(target);
            if (isShortcutResult) {
                return ChooserActivity.SHORTCUT_TARGET_SCORE_BOOST * score;
            }
            return score;
        }

        static /* synthetic */ boolean lambda$completeServiceTargetLoading$0(ChooserTargetInfo o) {
            return o instanceof PlaceHolderTargetInfo;
        }

        public void completeServiceTargetLoading() {
            this.mServiceTargets.removeIf($$Lambda$ChooserActivity$ChooserListAdapter$0o9wjP10lRaguhZLgVIZcGRo0w.INSTANCE);
            if (this.mServiceTargets.isEmpty()) {
                this.mServiceTargets.add(new EmptyTargetInfo());
            }
            notifyDataSetChanged();
        }

        private boolean insertServiceTarget(ChooserTargetInfo chooserTargetInfo) {
            if (this.mServiceTargets.size() == 1 && (this.mServiceTargets.get(0) instanceof EmptyTargetInfo)) {
                return false;
            }
            for (ChooserTargetInfo otherTargetInfo : this.mServiceTargets) {
                if (chooserTargetInfo.isSimilar(otherTargetInfo)) {
                    return false;
                }
            }
            int currentSize = this.mServiceTargets.size();
            float newScore = chooserTargetInfo.getModifiedScore();
            int i = 0;
            while (i < Math.min(currentSize, 8)) {
                ChooserTargetInfo serviceTarget = this.mServiceTargets.get(i);
                if (serviceTarget == null) {
                    this.mServiceTargets.set(i, chooserTargetInfo);
                    return true;
                } else if (newScore > serviceTarget.getModifiedScore()) {
                    this.mServiceTargets.add(i, chooserTargetInfo);
                    return true;
                } else {
                    i++;
                }
            }
            if (currentSize >= 8) {
                return false;
            }
            this.mServiceTargets.add(chooserTargetInfo);
            return true;
        }
    }

    static class BaseChooserTargetComparator implements Comparator<ChooserTarget> {
        BaseChooserTargetComparator() {
        }

        public int compare(ChooserTarget lhs, ChooserTarget rhs) {
            return (int) Math.signum(rhs.getScore() - lhs.getScore());
        }
    }

    /* access modifiers changed from: private */
    public boolean isSendAction(Intent targetIntent) {
        String action;
        if (targetIntent == null || (action = targetIntent.getAction()) == null) {
            return false;
        }
        if (Intent.ACTION_SEND.equals(action) || Intent.ACTION_SEND_MULTIPLE.equals(action)) {
            return true;
        }
        return false;
    }

    class ChooserRowAdapter extends BaseAdapter {
        private static final int MAX_TARGETS_PER_ROW_LANDSCAPE = 8;
        private static final int MAX_TARGETS_PER_ROW_PORTRAIT = 4;
        private static final int NUM_EXPANSIONS_TO_HIDE_AZ_LABEL = 20;
        private static final int VIEW_TYPE_AZ_LABEL = 4;
        private static final int VIEW_TYPE_CONTENT_PREVIEW = 2;
        private static final int VIEW_TYPE_DIRECT_SHARE = 0;
        private static final int VIEW_TYPE_NORMAL = 1;
        private static final int VIEW_TYPE_PROFILE = 3;
        /* access modifiers changed from: private */
        public ChooserListAdapter mChooserListAdapter;
        private int mChooserTargetWidth = 0;
        private DirectShareViewHolder mDirectShareViewHolder;
        private boolean mHideContentPreview = false;
        private final LayoutInflater mLayoutInflater;
        private boolean mLayoutRequested = false;
        private boolean mShowAzLabelIfPoss;

        public ChooserRowAdapter(ChooserListAdapter wrappedAdapter) {
            boolean z = false;
            this.mChooserListAdapter = wrappedAdapter;
            this.mLayoutInflater = LayoutInflater.from(ChooserActivity.this);
            this.mShowAzLabelIfPoss = ChooserActivity.this.getNumSheetExpansions() < 20 ? true : z;
            wrappedAdapter.registerDataSetObserver(new DataSetObserver(ChooserActivity.this) {
                public void onChanged() {
                    super.onChanged();
                    ChooserRowAdapter.this.notifyDataSetChanged();
                }

                public void onInvalidated() {
                    super.onInvalidated();
                    ChooserRowAdapter.this.notifyDataSetInvalidated();
                }
            });
        }

        public boolean calculateChooserTargetWidth(int width) {
            int newWidth;
            if (width == 0 || (newWidth = width / getMaxTargetsPerRow()) == this.mChooserTargetWidth) {
                return false;
            }
            this.mChooserTargetWidth = newWidth;
            return true;
        }

        /* access modifiers changed from: private */
        public int getMaxTargetsPerRow() {
            if (ChooserActivity.this.shouldDisplayLandscape(ChooserActivity.this.getResources().getConfiguration().orientation)) {
                return 8;
            }
            return 4;
        }

        public void hideContentPreview() {
            this.mHideContentPreview = true;
            this.mLayoutRequested = true;
            notifyDataSetChanged();
        }

        public boolean consumeLayoutRequest() {
            boolean oldValue = this.mLayoutRequested;
            this.mLayoutRequested = false;
            return oldValue;
        }

        public boolean areAllItemsEnabled() {
            return false;
        }

        public boolean isEnabled(int position) {
            int viewType = getItemViewType(position);
            if (viewType == 2 || viewType == 4) {
                return false;
            }
            return true;
        }

        public int getCount() {
            return (int) (((double) (getContentPreviewRowCount() + getProfileRowCount() + getServiceTargetRowCount() + getCallerAndRankedTargetRowCount() + getAzLabelRowCount())) + Math.ceil((double) (((float) this.mChooserListAdapter.getAlphaTargetCount()) / ((float) getMaxTargetsPerRow()))));
        }

        public int getContentPreviewRowCount() {
            if (ChooserActivity.this.isSendAction(ChooserActivity.this.getTargetIntent()) && !this.mHideContentPreview && this.mChooserListAdapter != null && this.mChooserListAdapter.getCount() != 0) {
                return 1;
            }
            return 0;
        }

        public int getProfileRowCount() {
            return this.mChooserListAdapter.getOtherProfile() == null ? 0 : 1;
        }

        public int getCallerAndRankedTargetRowCount() {
            return (int) Math.ceil((double) ((((float) this.mChooserListAdapter.getCallerTargetCount()) + ((float) this.mChooserListAdapter.getRankedTargetCount())) / ((float) getMaxTargetsPerRow())));
        }

        public int getServiceTargetRowCount() {
            if (!ChooserActivity.this.isSendAction(ChooserActivity.this.getTargetIntent()) || ActivityManager.isLowRamDeviceStatic()) {
                return 0;
            }
            return 1;
        }

        public int getAzLabelRowCount() {
            return (!this.mShowAzLabelIfPoss || this.mChooserListAdapter.getAlphaTargetCount() <= 0) ? 0 : 1;
        }

        public Object getItem(int position) {
            return Integer.valueOf(position);
        }

        public long getItemId(int position) {
            return (long) position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            RowViewHolder holder;
            int viewType = getItemViewType(position);
            if (viewType == 2) {
                return createContentPreviewView(convertView, parent);
            }
            if (viewType == 3) {
                return createProfileView(convertView, parent);
            }
            if (viewType == 4) {
                return createAzLabelView(parent);
            }
            if (convertView == null) {
                holder = createViewHolder(viewType, parent);
            } else {
                holder = (RowViewHolder) convertView.getTag();
            }
            bindViewHolder(position, holder);
            return holder.getViewGroup();
        }

        public int getItemViewType(int position) {
            int countSum = getContentPreviewRowCount();
            if (countSum > 0 && position < countSum) {
                return 2;
            }
            int count = getProfileRowCount();
            int countSum2 = countSum + count;
            if (count > 0 && position < countSum2) {
                return 3;
            }
            int count2 = getServiceTargetRowCount();
            int countSum3 = countSum2 + count2;
            if (count2 > 0 && position < countSum3) {
                return 0;
            }
            int count3 = getCallerAndRankedTargetRowCount();
            int countSum4 = countSum3 + count3;
            if (count3 > 0 && position < countSum4) {
                return 1;
            }
            int count4 = getAzLabelRowCount();
            int countSum5 = countSum4 + count4;
            if (count4 <= 0 || position >= countSum5) {
                return 1;
            }
            return 4;
        }

        public int getViewTypeCount() {
            return 5;
        }

        private ViewGroup createContentPreviewView(View convertView, ViewGroup parent) {
            Intent targetIntent = ChooserActivity.this.getTargetIntent();
            int previewType = ChooserActivity.this.findPreferredContentPreview(targetIntent, ChooserActivity.this.getContentResolver());
            if (convertView == null) {
                ChooserActivity.this.getMetricsLogger().write(new LogMaker((int) MetricsProto.MetricsEvent.ACTION_SHARE_WITH_PREVIEW).setSubtype(previewType));
            }
            return ChooserActivity.this.displayContentPreview(previewType, targetIntent, this.mLayoutInflater, (ViewGroup) convertView, parent);
        }

        private View createProfileView(View convertView, ViewGroup parent) {
            View profileRow = convertView != null ? convertView : this.mLayoutInflater.inflate((int) R.layout.chooser_profile_row, parent, false);
            profileRow.setBackground(ChooserActivity.this.getResources().getDrawable(R.drawable.chooser_row_layer_list, (Resources.Theme) null));
            ChooserActivity.this.mProfileView = profileRow.findViewById(R.id.profile_button);
            ChooserActivity.this.mProfileView.setOnClickListener(new View.OnClickListener() {
                public final void onClick(View view) {
                    ChooserActivity.this.onProfileClick(view);
                }
            });
            ChooserActivity.this.bindProfileView();
            return profileRow;
        }

        private View createAzLabelView(ViewGroup parent) {
            return this.mLayoutInflater.inflate((int) R.layout.chooser_az_label_row, parent, false);
        }

        private RowViewHolder loadViewsIntoRow(final RowViewHolder holder) {
            int spec = View.MeasureSpec.makeMeasureSpec(0, 0);
            int exactSpec = View.MeasureSpec.makeMeasureSpec(this.mChooserTargetWidth, 1073741824);
            int columnCount = holder.getColumnCount();
            boolean isDirectShare = holder instanceof DirectShareViewHolder;
            for (int i = 0; i < columnCount; i++) {
                View v = this.mChooserListAdapter.createView(holder.getRowByIndex(i));
                final int column = i;
                v.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        ChooserActivity.this.startSelected(holder.getItemIndex(column), false, true);
                    }
                });
                v.setOnLongClickListener(new View.OnLongClickListener() {
                    public boolean onLongClick(View v) {
                        ChooserActivity.this.showTargetDetails(ChooserRowAdapter.this.mChooserListAdapter.resolveInfoForPosition(holder.getItemIndex(column), true));
                        return true;
                    }
                });
                ViewGroup addView = holder.addView(i, v);
                if (isDirectShare) {
                    ResolverActivity.ViewHolder vh = (ResolverActivity.ViewHolder) v.getTag();
                    vh.text.setLines(2);
                    vh.text.setHorizontallyScrolling(false);
                    vh.text2.setVisibility(8);
                }
                v.measure(exactSpec, spec);
                setViewBounds(v, v.getMeasuredWidth(), v.getMeasuredHeight());
            }
            ViewGroup viewGroup = holder.getViewGroup();
            holder.measure();
            setViewBounds(viewGroup, -1, holder.getMeasuredRowHeight());
            if (isDirectShare) {
                DirectShareViewHolder dsvh = (DirectShareViewHolder) holder;
                setViewBounds(dsvh.getRow(0), -1, dsvh.getMinRowHeight());
                setViewBounds(dsvh.getRow(1), -1, dsvh.getMinRowHeight());
            }
            viewGroup.setTag(holder);
            return holder;
        }

        private void setViewBounds(View view, int widthPx, int heightPx) {
            ViewGroup.LayoutParams lp = view.getLayoutParams();
            if (lp == null) {
                view.setLayoutParams(new ViewGroup.LayoutParams(widthPx, heightPx));
                return;
            }
            lp.height = heightPx;
            lp.width = widthPx;
        }

        /* access modifiers changed from: package-private */
        public RowViewHolder createViewHolder(int viewType, ViewGroup parent) {
            if (viewType == 0) {
                ViewGroup parentGroup = (ViewGroup) this.mLayoutInflater.inflate((int) R.layout.chooser_row_direct_share, parent, false);
                ViewGroup row1 = (ViewGroup) this.mLayoutInflater.inflate((int) R.layout.chooser_row, parentGroup, false);
                ViewGroup row2 = (ViewGroup) this.mLayoutInflater.inflate((int) R.layout.chooser_row, parentGroup, false);
                parentGroup.addView(row1);
                parentGroup.addView(row2);
                this.mDirectShareViewHolder = new DirectShareViewHolder(parentGroup, Lists.newArrayList(row1, row2), getMaxTargetsPerRow());
                loadViewsIntoRow(this.mDirectShareViewHolder);
                return this.mDirectShareViewHolder;
            }
            RowViewHolder holder = new SingleRowViewHolder((ViewGroup) this.mLayoutInflater.inflate((int) R.layout.chooser_row, parent, false), getMaxTargetsPerRow());
            loadViewsIntoRow(holder);
            return holder;
        }

        /* access modifiers changed from: package-private */
        public int getRowType(int rowPosition) {
            int positionType = this.mChooserListAdapter.getPositionTargetType(rowPosition);
            if (positionType == 0) {
                return 2;
            }
            if (getAzLabelRowCount() <= 0 || positionType != 3) {
                return positionType;
            }
            return 2;
        }

        /* access modifiers changed from: package-private */
        public void bindViewHolder(int rowPosition, RowViewHolder holder) {
            int i = rowPosition;
            RowViewHolder rowViewHolder = holder;
            int start = getFirstRowPosition(rowPosition);
            int startType = getRowType(start);
            int lastStartType = getRowType(getFirstRowPosition(i - 1));
            ViewGroup row = holder.getViewGroup();
            if (startType != lastStartType || i == getContentPreviewRowCount() + getProfileRowCount()) {
                row.setForeground(ChooserActivity.this.getResources().getDrawable(R.drawable.chooser_row_layer_list, (Resources.Theme) null));
            } else {
                row.setForeground((Drawable) null);
            }
            int columnCount = holder.getColumnCount();
            int end = (start + columnCount) - 1;
            while (getRowType(end) != startType && end >= start) {
                end--;
            }
            if (end == start && (this.mChooserListAdapter.getItem(start) instanceof EmptyTargetInfo)) {
                TextView textView = (TextView) row.findViewById(R.id.chooser_row_text_option);
                if (textView.getVisibility() != 0) {
                    textView.setAlpha(0.0f);
                    textView.setVisibility(0);
                    textView.setText((int) R.string.chooser_no_direct_share_targets);
                    ValueAnimator fadeAnim = ObjectAnimator.ofFloat((Object) textView, "alpha", 0.0f, 1.0f);
                    fadeAnim.setInterpolator(new DecelerateInterpolator(1.0f));
                    textView.setTranslationY((float) ChooserActivity.this.getResources().getDimensionPixelSize(R.dimen.chooser_row_text_option_translate));
                    ValueAnimator translateAnim = ObjectAnimator.ofFloat((Object) textView, "translationY", 0.0f);
                    translateAnim.setInterpolator(new DecelerateInterpolator(1.0f));
                    AnimatorSet animSet = new AnimatorSet();
                    animSet.setDuration(200);
                    animSet.setStartDelay(200);
                    animSet.playTogether(fadeAnim, translateAnim);
                    animSet.start();
                }
            }
            for (int i2 = 0; i2 < columnCount; i2++) {
                View v = rowViewHolder.getView(i2);
                if (start + i2 <= end) {
                    rowViewHolder.setViewVisibility(i2, 0);
                    rowViewHolder.setItemIndex(i2, start + i2);
                    this.mChooserListAdapter.bindView(rowViewHolder.getItemIndex(i2), v);
                } else {
                    rowViewHolder.setViewVisibility(i2, 4);
                }
            }
        }

        /* access modifiers changed from: package-private */
        public int getFirstRowPosition(int row) {
            int row2 = row - (getContentPreviewRowCount() + getProfileRowCount());
            int serviceCount = this.mChooserListAdapter.getServiceTargetCount();
            int serviceRows = (int) Math.ceil((double) (((float) serviceCount) / 8.0f));
            if (row2 < serviceRows) {
                return getMaxTargetsPerRow() * row2;
            }
            int callerAndRankedCount = this.mChooserListAdapter.getCallerTargetCount() + this.mChooserListAdapter.getRankedTargetCount();
            int callerAndRankedRows = getCallerAndRankedTargetRowCount();
            if (row2 < callerAndRankedRows + serviceRows) {
                return ((row2 - serviceRows) * getMaxTargetsPerRow()) + serviceCount;
            }
            return callerAndRankedCount + serviceCount + ((((row2 - getAzLabelRowCount()) - callerAndRankedRows) - serviceRows) * getMaxTargetsPerRow());
        }

        public void handleScroll(View v, int y, int oldy) {
            int orientation = ChooserActivity.this.getResources().getConfiguration().orientation;
            boolean z = true;
            if (this.mChooserListAdapter.getNumShortcutResults() <= getMaxTargetsPerRow() || orientation != 1 || ChooserActivity.this.isInMultiWindowMode()) {
                z = false;
            }
            boolean canExpandDirectShare = z;
            if (this.mDirectShareViewHolder != null && canExpandDirectShare) {
                this.mDirectShareViewHolder.handleScroll(ChooserActivity.this.mAdapterView, y, oldy, getMaxTargetsPerRow());
            }
        }
    }

    abstract class RowViewHolder {
        protected final View[] mCells;
        private final int mColumnCount;
        private int[] mItemIndices;
        protected int mMeasuredRowHeight;

        /* access modifiers changed from: package-private */
        public abstract ViewGroup addView(int i, View view);

        /* access modifiers changed from: package-private */
        public abstract ViewGroup getRow(int i);

        /* access modifiers changed from: package-private */
        public abstract ViewGroup getRowByIndex(int i);

        /* access modifiers changed from: package-private */
        public abstract ViewGroup getViewGroup();

        /* access modifiers changed from: package-private */
        public abstract void setViewVisibility(int i, int i2);

        RowViewHolder(int cellCount) {
            this.mCells = new View[cellCount];
            this.mItemIndices = new int[cellCount];
            this.mColumnCount = cellCount;
        }

        public int getColumnCount() {
            return this.mColumnCount;
        }

        public void measure() {
            int spec = View.MeasureSpec.makeMeasureSpec(0, 0);
            getViewGroup().measure(spec, spec);
            this.mMeasuredRowHeight = getViewGroup().getMeasuredHeight();
        }

        public int getMeasuredRowHeight() {
            return this.mMeasuredRowHeight;
        }

        public void setItemIndex(int itemIndex, int listIndex) {
            this.mItemIndices[itemIndex] = listIndex;
        }

        public int getItemIndex(int itemIndex) {
            return this.mItemIndices[itemIndex];
        }

        public View getView(int index) {
            return this.mCells[index];
        }
    }

    class SingleRowViewHolder extends RowViewHolder {
        private final ViewGroup mRow;

        SingleRowViewHolder(ViewGroup row, int cellCount) {
            super(cellCount);
            this.mRow = row;
        }

        public ViewGroup getViewGroup() {
            return this.mRow;
        }

        public ViewGroup getRowByIndex(int index) {
            return this.mRow;
        }

        public ViewGroup getRow(int rowNumber) {
            if (rowNumber == 0) {
                return this.mRow;
            }
            return null;
        }

        public ViewGroup addView(int index, View v) {
            this.mRow.addView(v);
            this.mCells[index] = v;
            return this.mRow;
        }

        public void setViewVisibility(int i, int visibility) {
            getView(i).setVisibility(visibility);
        }
    }

    class DirectShareViewHolder extends RowViewHolder {
        private int mCellCountPerRow;
        private final boolean[] mCellVisibility;
        private int mDirectShareCurrHeight = 0;
        private int mDirectShareMaxHeight = 0;
        private int mDirectShareMinHeight = 0;
        private boolean mHideDirectShareExpansion = false;
        private final ViewGroup mParent;
        private final List<ViewGroup> mRows;

        DirectShareViewHolder(ViewGroup parent, List<ViewGroup> rows, int cellCountPerRow) {
            super(rows.size() * cellCountPerRow);
            this.mParent = parent;
            this.mRows = rows;
            this.mCellCountPerRow = cellCountPerRow;
            this.mCellVisibility = new boolean[(rows.size() * cellCountPerRow)];
        }

        public ViewGroup addView(int index, View v) {
            ViewGroup row = getRowByIndex(index);
            row.addView(v);
            this.mCells[index] = v;
            return row;
        }

        public ViewGroup getViewGroup() {
            return this.mParent;
        }

        public ViewGroup getRowByIndex(int index) {
            return this.mRows.get(index / this.mCellCountPerRow);
        }

        public ViewGroup getRow(int rowNumber) {
            return this.mRows.get(rowNumber);
        }

        public void measure() {
            int spec = View.MeasureSpec.makeMeasureSpec(0, 0);
            getRow(0).measure(spec, spec);
            getRow(1).measure(spec, spec);
            this.mDirectShareMinHeight = getRow(0).getMeasuredHeight();
            this.mDirectShareCurrHeight = this.mDirectShareCurrHeight > 0 ? this.mDirectShareCurrHeight : this.mDirectShareMinHeight;
            this.mDirectShareMaxHeight = this.mDirectShareMinHeight * 2;
        }

        public int getMeasuredRowHeight() {
            return this.mDirectShareCurrHeight;
        }

        public int getMinRowHeight() {
            return this.mDirectShareMinHeight;
        }

        public void setViewVisibility(int i, int visibility) {
            final View v = getView(i);
            if (visibility == 0) {
                this.mCellVisibility[i] = true;
                v.setVisibility(visibility);
                v.setAlpha(1.0f);
            } else if (visibility == 4 && this.mCellVisibility[i]) {
                this.mCellVisibility[i] = false;
                ValueAnimator fadeAnim = ObjectAnimator.ofFloat((Object) v, "alpha", 1.0f, 0.0f);
                fadeAnim.setDuration(200);
                fadeAnim.setInterpolator(new AccelerateInterpolator(1.0f));
                fadeAnim.addListener(new AnimatorListenerAdapter() {
                    public void onAnimationEnd(Animator animation) {
                        v.setVisibility(4);
                    }
                });
                fadeAnim.start();
            }
        }

        public void handleScroll(AbsListView view, int y, int oldy, int maxTargetsPerRow) {
            boolean notExpanded;
            AbsListView absListView = view;
            int i = 0;
            boolean notExpanded2 = this.mDirectShareCurrHeight == this.mDirectShareMinHeight;
            if (!notExpanded2) {
                int i2 = maxTargetsPerRow;
            } else if (!this.mHideDirectShareExpansion) {
                if (ChooserActivity.this.mChooserListAdapter.getSelectableServiceTargetCount() <= maxTargetsPerRow) {
                    this.mHideDirectShareExpansion = true;
                    return;
                }
            } else {
                return;
            }
            int prevHeight = this.mDirectShareCurrHeight;
            int newHeight = Math.max(Math.min(prevHeight + ((int) (((float) (oldy - y)) * ChooserActivity.DIRECT_SHARE_EXPANSION_RATE)), this.mDirectShareMaxHeight), this.mDirectShareMinHeight);
            int yDiff = newHeight - prevHeight;
            if (absListView == null || view.getChildCount() == 0) {
            } else if (yDiff == 0) {
                boolean z = notExpanded2;
            } else {
                boolean foundExpansion = false;
                while (i < view.getChildCount()) {
                    View child = absListView.getChildAt(i);
                    if (foundExpansion) {
                        child.offsetTopAndBottom(yDiff);
                        notExpanded = notExpanded2;
                    } else if (child.getTag() == null || !(child.getTag() instanceof DirectShareViewHolder)) {
                        notExpanded = notExpanded2;
                    } else {
                        child.measure(View.MeasureSpec.makeMeasureSpec(child.getWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(newHeight, 1073741824));
                        child.getLayoutParams().height = child.getMeasuredHeight();
                        notExpanded = notExpanded2;
                        child.layout(child.getLeft(), child.getTop(), child.getRight(), child.getTop() + child.getMeasuredHeight());
                        foundExpansion = true;
                    }
                    i++;
                    notExpanded2 = notExpanded;
                    absListView = view;
                }
                if (foundExpansion) {
                    this.mDirectShareCurrHeight = newHeight;
                }
            }
        }
    }

    static class ChooserTargetServiceConnection implements ServiceConnection {
        /* access modifiers changed from: private */
        public ChooserActivity mChooserActivity;
        private final IChooserTargetResult mChooserTargetResult = new IChooserTargetResult.Stub() {
            public void sendResult(List<ChooserTarget> targets) throws RemoteException {
                synchronized (ChooserTargetServiceConnection.this.mLock) {
                    if (ChooserTargetServiceConnection.this.mChooserActivity == null) {
                        Log.e(ChooserActivity.TAG, "destroyed ChooserTargetServiceConnection received result from " + ChooserTargetServiceConnection.this.mConnectedComponent + "; ignoring...");
                        return;
                    }
                    ChooserTargetServiceConnection.this.mChooserActivity.filterServiceTargets(ChooserTargetServiceConnection.this.mOriginalTarget.getResolveInfo().activityInfo.packageName, targets);
                    Message msg = Message.obtain();
                    msg.what = 1;
                    msg.obj = new ServiceResultInfo(ChooserTargetServiceConnection.this.mOriginalTarget, targets, ChooserTargetServiceConnection.this);
                    ChooserTargetServiceConnection.this.mChooserActivity.mChooserHandler.sendMessage(msg);
                }
            }
        };
        /* access modifiers changed from: private */
        public ComponentName mConnectedComponent;
        /* access modifiers changed from: private */
        public final Object mLock = new Object();
        /* access modifiers changed from: private */
        public ResolverActivity.DisplayResolveInfo mOriginalTarget;

        public ChooserTargetServiceConnection(ChooserActivity chooserActivity, ResolverActivity.DisplayResolveInfo dri) {
            this.mChooserActivity = chooserActivity;
            this.mOriginalTarget = dri;
        }

        public void onServiceConnected(ComponentName name, IBinder service) {
            synchronized (this.mLock) {
                if (this.mChooserActivity == null) {
                    Log.e(ChooserActivity.TAG, "destroyed ChooserTargetServiceConnection got onServiceConnected");
                    return;
                }
                try {
                    IChooserTargetService.Stub.asInterface(service).getChooserTargets(this.mOriginalTarget.getResolvedComponentName(), this.mOriginalTarget.getResolveInfo().filter, this.mChooserTargetResult);
                } catch (RemoteException e) {
                    Log.e(ChooserActivity.TAG, "Querying ChooserTargetService " + name + " failed.", e);
                    this.mChooserActivity.unbindService(this);
                    this.mChooserActivity.mServiceConnections.remove(this);
                    destroy();
                }
            }
        }

        public void onServiceDisconnected(ComponentName name) {
            synchronized (this.mLock) {
                if (this.mChooserActivity == null) {
                    Log.e(ChooserActivity.TAG, "destroyed ChooserTargetServiceConnection got onServiceDisconnected");
                    return;
                }
                this.mChooserActivity.unbindService(this);
                this.mChooserActivity.mServiceConnections.remove(this);
                if (this.mChooserActivity.mServiceConnections.isEmpty()) {
                    this.mChooserActivity.sendVoiceChoicesIfNeeded();
                }
                this.mConnectedComponent = null;
                destroy();
            }
        }

        public void destroy() {
            synchronized (this.mLock) {
                this.mChooserActivity = null;
                this.mOriginalTarget = null;
            }
        }

        public String toString() {
            String str;
            StringBuilder sb = new StringBuilder();
            sb.append("ChooserTargetServiceConnection{service=");
            sb.append(this.mConnectedComponent);
            sb.append(", activity=");
            if (this.mOriginalTarget != null) {
                str = this.mOriginalTarget.getResolveInfo().activityInfo.toString();
            } else {
                str = "<connection destroyed>";
            }
            sb.append(str);
            sb.append("}");
            return sb.toString();
        }
    }

    static class ServiceResultInfo {
        public final ChooserTargetServiceConnection connection;
        public final ResolverActivity.DisplayResolveInfo originalTarget;
        public final List<ChooserTarget> resultTargets;

        public ServiceResultInfo(ResolverActivity.DisplayResolveInfo ot, List<ChooserTarget> rt, ChooserTargetServiceConnection c) {
            this.originalTarget = ot;
            this.resultTargets = rt;
            this.connection = c;
        }
    }

    static class RefinementResultReceiver extends ResultReceiver {
        private ChooserActivity mChooserActivity;
        private ResolverActivity.TargetInfo mSelectedTarget;

        public RefinementResultReceiver(ChooserActivity host, ResolverActivity.TargetInfo target, Handler handler) {
            super(handler);
            this.mChooserActivity = host;
            this.mSelectedTarget = target;
        }

        /* access modifiers changed from: protected */
        public void onReceiveResult(int resultCode, Bundle resultData) {
            if (this.mChooserActivity == null) {
                Log.e(ChooserActivity.TAG, "Destroyed RefinementResultReceiver received a result");
            } else if (resultData == null) {
                Log.e(ChooserActivity.TAG, "RefinementResultReceiver received null resultData");
            } else {
                switch (resultCode) {
                    case -1:
                        Parcelable intentParcelable = resultData.getParcelable(Intent.EXTRA_INTENT);
                        if (intentParcelable instanceof Intent) {
                            this.mChooserActivity.onRefinementResult(this.mSelectedTarget, (Intent) intentParcelable);
                            return;
                        } else {
                            Log.e(ChooserActivity.TAG, "RefinementResultReceiver received RESULT_OK but no Intent in resultData with key Intent.EXTRA_INTENT");
                            return;
                        }
                    case 0:
                        this.mChooserActivity.onRefinementCanceled();
                        return;
                    default:
                        Log.w(ChooserActivity.TAG, "Unknown result code " + resultCode + " sent to RefinementResultReceiver");
                        return;
                }
            }
        }

        public void destroy() {
            this.mChooserActivity = null;
            this.mSelectedTarget = null;
        }
    }

    public static class RoundedRectImageView extends ImageView {
        private String mExtraImageCount;
        private Paint mOverlayPaint;
        private Path mPath;
        private int mRadius;
        private Paint mRoundRectPaint;
        private Paint mTextPaint;

        public RoundedRectImageView(Context context) {
            super(context);
            this.mRadius = 0;
            this.mPath = new Path();
            this.mOverlayPaint = new Paint(0);
            this.mRoundRectPaint = new Paint(0);
            this.mTextPaint = new Paint(1);
            this.mExtraImageCount = null;
        }

        public RoundedRectImageView(Context context, AttributeSet attrs) {
            this(context, attrs, 0);
        }

        public RoundedRectImageView(Context context, AttributeSet attrs, int defStyleAttr) {
            this(context, attrs, defStyleAttr, 0);
        }

        public RoundedRectImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
            this.mRadius = 0;
            this.mPath = new Path();
            this.mOverlayPaint = new Paint(0);
            this.mRoundRectPaint = new Paint(0);
            this.mTextPaint = new Paint(1);
            this.mExtraImageCount = null;
            this.mRadius = context.getResources().getDimensionPixelSize(R.dimen.chooser_corner_radius);
            this.mOverlayPaint.setColor(-1728053248);
            this.mOverlayPaint.setStyle(Paint.Style.FILL);
            this.mRoundRectPaint.setColor(context.getResources().getColor(R.color.chooser_row_divider));
            this.mRoundRectPaint.setStyle(Paint.Style.STROKE);
            this.mRoundRectPaint.setStrokeWidth((float) context.getResources().getDimensionPixelSize(R.dimen.chooser_preview_image_border));
            this.mTextPaint.setColor(-1);
            this.mTextPaint.setTextSize((float) context.getResources().getDimensionPixelSize(R.dimen.chooser_preview_image_font_size));
            this.mTextPaint.setTextAlign(Paint.Align.CENTER);
        }

        private void updatePath(int width, int height) {
            this.mPath.reset();
            this.mPath.addRoundRect((float) getPaddingLeft(), (float) getPaddingTop(), (float) ((width - getPaddingRight()) - getPaddingLeft()), (float) ((height - getPaddingBottom()) - getPaddingTop()), (float) this.mRadius, (float) this.mRadius, Path.Direction.CW);
        }

        public void setRadius(int radius) {
            this.mRadius = radius;
            updatePath(getWidth(), getHeight());
        }

        public void setExtraImageCount(int count) {
            if (count > 0) {
                this.mExtraImageCount = "+" + count;
                return;
            }
            this.mExtraImageCount = null;
        }

        /* access modifiers changed from: protected */
        public void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
            super.onSizeChanged(width, height, oldWidth, oldHeight);
            updatePath(width, height);
        }

        /* access modifiers changed from: protected */
        public void onDraw(Canvas canvas) {
            if (this.mRadius != 0) {
                canvas.clipPath(this.mPath);
            }
            super.onDraw(canvas);
            int x = getPaddingLeft();
            int y = getPaddingRight();
            int width = (getWidth() - getPaddingRight()) - getPaddingLeft();
            int height = (getHeight() - getPaddingBottom()) - getPaddingTop();
            if (this.mExtraImageCount != null) {
                canvas.drawRect((float) x, (float) y, (float) width, (float) height, this.mOverlayPaint);
                canvas.drawText(this.mExtraImageCount, (float) (canvas.getWidth() / 2), (float) ((int) ((((float) canvas.getHeight()) / 2.0f) - ((this.mTextPaint.descent() + this.mTextPaint.ascent()) / 2.0f))), this.mTextPaint);
            }
            canvas.drawRoundRect((float) x, (float) y, (float) width, (float) height, (float) this.mRadius, (float) this.mRadius, this.mRoundRectPaint);
        }
    }
}
