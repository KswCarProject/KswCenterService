package com.android.internal.app;

import android.app.prediction.AppPredictor;
import android.app.prediction.AppTarget;
import android.app.prediction.AppTargetEvent;
import android.app.prediction.AppTargetId;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.p002pm.ResolveInfo;
import android.p007os.Message;
import android.p007os.UserHandle;
import android.util.Log;
import com.android.internal.app.AbstractResolverComparator;
import com.android.internal.app.ResolverActivity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

/* loaded from: classes4.dex */
class AppPredictionServiceResolverComparator extends AbstractResolverComparator {
    private static final boolean DEBUG = false;
    private static final String TAG = "APSResolverComparator";
    private final AppPredictor mAppPredictor;
    private final Context mContext;
    private final Intent mIntent;
    private final String mReferrerPackage;
    private ResolverRankerServiceResolverComparator mResolverRankerService;
    private final Map<ComponentName, Integer> mTargetRanks;
    private final UserHandle mUser;

    AppPredictionServiceResolverComparator(Context context, Intent intent, String referrerPackage, AppPredictor appPredictor, UserHandle user) {
        super(context, intent);
        this.mTargetRanks = new HashMap();
        this.mContext = context;
        this.mIntent = intent;
        this.mAppPredictor = appPredictor;
        this.mUser = user;
        this.mReferrerPackage = referrerPackage;
    }

    @Override // com.android.internal.app.AbstractResolverComparator
    int compare(ResolveInfo lhs, ResolveInfo rhs) {
        if (this.mResolverRankerService != null) {
            return this.mResolverRankerService.compare(lhs, rhs);
        }
        Integer lhsRank = this.mTargetRanks.get(new ComponentName(lhs.activityInfo.packageName, lhs.activityInfo.name));
        Integer rhsRank = this.mTargetRanks.get(new ComponentName(rhs.activityInfo.packageName, rhs.activityInfo.name));
        if (lhsRank == null && rhsRank == null) {
            return 0;
        }
        if (lhsRank == null) {
            return -1;
        }
        if (rhsRank == null) {
            return 1;
        }
        return lhsRank.intValue() - rhsRank.intValue();
    }

    @Override // com.android.internal.app.AbstractResolverComparator
    void doCompute(final List<ResolverActivity.ResolvedComponentInfo> targets) {
        if (targets.isEmpty()) {
            this.mHandler.sendEmptyMessage(0);
            return;
        }
        List<AppTarget> appTargets = new ArrayList<>();
        for (ResolverActivity.ResolvedComponentInfo target : targets) {
            appTargets.add(new AppTarget.Builder(new AppTargetId(target.name.flattenToString()), target.name.getPackageName(), this.mUser).setClassName(target.name.getClassName()).build());
        }
        this.mAppPredictor.sortTargets(appTargets, Executors.newSingleThreadExecutor(), new Consumer() { // from class: com.android.internal.app.-$$Lambda$AppPredictionServiceResolverComparator$PQ-i16vesHTtkDyBgU_HkS0uF1A
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                AppPredictionServiceResolverComparator.lambda$doCompute$1(AppPredictionServiceResolverComparator.this, targets, (List) obj);
            }
        });
    }

    public static /* synthetic */ void lambda$doCompute$1(final AppPredictionServiceResolverComparator appPredictionServiceResolverComparator, List targets, List sortedAppTargets) {
        if (sortedAppTargets.isEmpty()) {
            appPredictionServiceResolverComparator.mResolverRankerService = new ResolverRankerServiceResolverComparator(appPredictionServiceResolverComparator.mContext, appPredictionServiceResolverComparator.mIntent, appPredictionServiceResolverComparator.mReferrerPackage, new AbstractResolverComparator.AfterCompute() { // from class: com.android.internal.app.-$$Lambda$AppPredictionServiceResolverComparator$25gj8kU_BfxuxUXCZ0QzLVhZs9Y
                @Override // com.android.internal.app.AbstractResolverComparator.AfterCompute
                public final void afterCompute() {
                    AppPredictionServiceResolverComparator.this.mHandler.sendEmptyMessage(0);
                }
            });
            appPredictionServiceResolverComparator.mResolverRankerService.compute(targets);
            return;
        }
        Message msg = Message.obtain(appPredictionServiceResolverComparator.mHandler, 0, sortedAppTargets);
        msg.sendToTarget();
    }

    @Override // com.android.internal.app.AbstractResolverComparator
    void handleResultMessage(Message msg) {
        if (msg.what != 0 || msg.obj == null) {
            if (msg.obj == null && this.mResolverRankerService == null) {
                Log.m70e(TAG, "Unexpected null result");
                return;
            }
            return;
        }
        List<AppTarget> sortedAppTargets = (List) msg.obj;
        for (int i = 0; i < sortedAppTargets.size(); i++) {
            this.mTargetRanks.put(new ComponentName(sortedAppTargets.get(i).getPackageName(), sortedAppTargets.get(i).getClassName()), Integer.valueOf(i));
        }
    }

    @Override // com.android.internal.app.AbstractResolverComparator
    float getScore(ComponentName name) {
        if (this.mResolverRankerService != null) {
            return this.mResolverRankerService.getScore(name);
        }
        Integer rank = this.mTargetRanks.get(name);
        if (rank == null) {
            Log.m64w(TAG, "Score requested for unknown component.");
            return 0.0f;
        }
        int consecutiveSumOfRanks = ((this.mTargetRanks.size() - 1) * this.mTargetRanks.size()) / 2;
        return 1.0f - (rank.intValue() / consecutiveSumOfRanks);
    }

    @Override // com.android.internal.app.AbstractResolverComparator
    void updateModel(ComponentName componentName) {
        if (this.mResolverRankerService != null) {
            this.mResolverRankerService.updateModel(componentName);
        } else {
            this.mAppPredictor.notifyAppTargetEvent(new AppTargetEvent.Builder(new AppTarget.Builder(new AppTargetId(componentName.toString()), componentName.getPackageName(), this.mUser).setClassName(componentName.getClassName()).build(), 1).build());
        }
    }

    @Override // com.android.internal.app.AbstractResolverComparator
    void destroy() {
        if (this.mResolverRankerService != null) {
            this.mResolverRankerService.destroy();
            this.mResolverRankerService = null;
        }
    }
}
