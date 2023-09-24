package com.android.internal.app;

import android.app.usage.UsageStatsManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.p002pm.PackageManager;
import android.content.p002pm.ResolveInfo;
import android.p007os.Handler;
import android.p007os.Looper;
import android.p007os.Message;
import android.p007os.UserHandle;
import android.util.Log;
import com.android.internal.app.ResolverActivity;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/* loaded from: classes4.dex */
abstract class AbstractResolverComparator implements Comparator<ResolverActivity.ResolvedComponentInfo> {
    private static final boolean DEBUG = false;
    private static final int NUM_OF_TOP_ANNOTATIONS_TO_USE = 3;
    static final int RANKER_RESULT_TIMEOUT = 1;
    static final int RANKER_SERVICE_RESULT = 0;
    private static final String TAG = "AbstractResolverComp";
    private static final int WATCHDOG_TIMEOUT_MILLIS = 500;
    protected AfterCompute mAfterCompute;
    protected String[] mAnnotations;
    protected String mContentType;
    private final String mDefaultBrowserPackageName;
    protected final Handler mHandler = new Handler(Looper.getMainLooper()) { // from class: com.android.internal.app.AbstractResolverComparator.1
        @Override // android.p007os.Handler
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    if (AbstractResolverComparator.this.mHandler.hasMessages(1)) {
                        AbstractResolverComparator.this.handleResultMessage(msg);
                        AbstractResolverComparator.this.mHandler.removeMessages(1);
                        AbstractResolverComparator.this.afterCompute();
                        return;
                    }
                    return;
                case 1:
                    AbstractResolverComparator.this.mHandler.removeMessages(0);
                    AbstractResolverComparator.this.afterCompute();
                    return;
                default:
                    super.handleMessage(msg);
                    return;
            }
        }
    };
    private final boolean mHttp;
    protected final PackageManager mPm;
    protected final UsageStatsManager mUsm;

    /* loaded from: classes4.dex */
    interface AfterCompute {
        void afterCompute();
    }

    abstract int compare(ResolveInfo resolveInfo, ResolveInfo resolveInfo2);

    abstract void doCompute(List<ResolverActivity.ResolvedComponentInfo> list);

    abstract float getScore(ComponentName componentName);

    abstract void handleResultMessage(Message message);

    AbstractResolverComparator(Context context, Intent intent) {
        String str;
        String scheme = intent.getScheme();
        this.mHttp = IntentFilter.SCHEME_HTTP.equals(scheme) || IntentFilter.SCHEME_HTTPS.equals(scheme);
        this.mContentType = intent.getType();
        getContentAnnotations(intent);
        this.mPm = context.getPackageManager();
        this.mUsm = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
        if (this.mHttp) {
            str = this.mPm.getDefaultBrowserPackageNameAsUser(UserHandle.myUserId());
        } else {
            str = null;
        }
        this.mDefaultBrowserPackageName = str;
    }

    private void getContentAnnotations(Intent intent) {
        ArrayList<String> annotations = intent.getStringArrayListExtra(Intent.EXTRA_CONTENT_ANNOTATIONS);
        if (annotations != null) {
            int size = annotations.size();
            if (size > 3) {
                size = 3;
            }
            this.mAnnotations = new String[size];
            for (int i = 0; i < size; i++) {
                this.mAnnotations[i] = annotations.get(i);
            }
        }
    }

    void setCallBack(AfterCompute afterCompute) {
        this.mAfterCompute = afterCompute;
    }

    protected final void afterCompute() {
        AfterCompute afterCompute = this.mAfterCompute;
        if (afterCompute != null) {
            afterCompute.afterCompute();
        }
    }

    @Override // java.util.Comparator
    public final int compare(ResolverActivity.ResolvedComponentInfo lhsp, ResolverActivity.ResolvedComponentInfo rhsp) {
        ResolveInfo lhs = lhsp.getResolveInfoAt(0);
        ResolveInfo rhs = rhsp.getResolveInfoAt(0);
        if (lhs.targetUserId != -2) {
            return rhs.targetUserId != -2 ? 0 : 1;
        } else if (rhs.targetUserId != -2) {
            return -1;
        } else {
            if (this.mHttp) {
                if (isDefaultBrowser(lhs)) {
                    return -1;
                }
                if (isDefaultBrowser(rhs)) {
                    return 1;
                }
                boolean lhsSpecific = ResolverActivity.isSpecificUriMatch(lhs.match);
                boolean rhsSpecific = ResolverActivity.isSpecificUriMatch(rhs.match);
                if (lhsSpecific != rhsSpecific) {
                    return lhsSpecific ? -1 : 1;
                }
            }
            return compare(lhs, rhs);
        }
    }

    final void compute(List<ResolverActivity.ResolvedComponentInfo> targets) {
        beforeCompute();
        doCompute(targets);
    }

    final void updateChooserCounts(String packageName, int userId, String action) {
        if (this.mUsm != null) {
            this.mUsm.reportChooserSelection(packageName, userId, this.mContentType, this.mAnnotations, action);
        }
    }

    void updateModel(ComponentName componentName) {
    }

    void beforeCompute() {
        if (this.mHandler == null) {
            Log.m72d(TAG, "Error: Handler is Null; Needs to be initialized.");
        } else {
            this.mHandler.sendEmptyMessageDelayed(1, 500L);
        }
    }

    void destroy() {
        this.mHandler.removeMessages(0);
        this.mHandler.removeMessages(1);
        afterCompute();
    }

    private boolean isDefaultBrowser(ResolveInfo ri) {
        return ri.targetUserId == -2 && ri.activityInfo.packageName != null && ri.activityInfo.packageName.equals(this.mDefaultBrowserPackageName);
    }
}
