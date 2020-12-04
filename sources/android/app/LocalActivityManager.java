package android.app;

import android.annotation.UnsupportedAppUsage;
import android.app.Activity;
import android.app.ActivityThread;
import android.app.servertransaction.PendingTransactionActions;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Window;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Deprecated
public class LocalActivityManager {
    static final int CREATED = 2;
    static final int DESTROYED = 5;
    static final int INITIALIZING = 1;
    static final int RESTORED = 0;
    static final int RESUMED = 4;
    static final int STARTED = 3;
    private static final String TAG = "LocalActivityManager";
    private static final boolean localLOGV = false;
    @UnsupportedAppUsage
    private final Map<String, LocalActivityRecord> mActivities = new HashMap();
    @UnsupportedAppUsage
    private final ArrayList<LocalActivityRecord> mActivityArray = new ArrayList<>();
    private final ActivityThread mActivityThread = ActivityThread.currentActivityThread();
    private int mCurState = 1;
    private boolean mFinishing;
    @UnsupportedAppUsage
    private final Activity mParent;
    @UnsupportedAppUsage
    private LocalActivityRecord mResumed;
    @UnsupportedAppUsage
    private boolean mSingleMode;

    private static class LocalActivityRecord extends Binder {
        Activity activity;
        ActivityInfo activityInfo;
        int curState = 0;
        final String id;
        Bundle instanceState;
        Intent intent;
        Window window;

        LocalActivityRecord(String _id, Intent _intent) {
            this.id = _id;
            this.intent = _intent;
        }
    }

    public LocalActivityManager(Activity parent, boolean singleMode) {
        this.mParent = parent;
        this.mSingleMode = singleMode;
    }

    @UnsupportedAppUsage
    private void moveToState(LocalActivityRecord r, int desiredState) {
        LocalActivityRecord localActivityRecord = r;
        int i = desiredState;
        if (localActivityRecord.curState != 0 && localActivityRecord.curState != 5) {
            PendingTransactionActions pendingActions = null;
            if (localActivityRecord.curState == 1) {
                HashMap<String, Object> lastNonConfigurationInstances = this.mParent.getLastNonConfigurationChildInstances();
                Object instanceObj = null;
                if (lastNonConfigurationInstances != null) {
                    instanceObj = lastNonConfigurationInstances.get(localActivityRecord.id);
                }
                Object instanceObj2 = instanceObj;
                Activity.NonConfigurationInstances instance = null;
                if (instanceObj2 != null) {
                    instance = new Activity.NonConfigurationInstances();
                    instance.activity = instanceObj2;
                }
                Activity.NonConfigurationInstances instance2 = instance;
                if (localActivityRecord.activityInfo == null) {
                    localActivityRecord.activityInfo = this.mActivityThread.resolveActivityInfo(localActivityRecord.intent);
                }
                Object obj = instanceObj2;
                HashMap<String, Object> hashMap = lastNonConfigurationInstances;
                localActivityRecord.activity = this.mActivityThread.startActivityNow(this.mParent, localActivityRecord.id, localActivityRecord.intent, localActivityRecord.activityInfo, r, localActivityRecord.instanceState, instance2, r);
                if (localActivityRecord.activity != null) {
                    localActivityRecord.window = localActivityRecord.activity.getWindow();
                    localActivityRecord.instanceState = null;
                    ActivityThread.ActivityClientRecord clientRecord = this.mActivityThread.getActivityClient(localActivityRecord);
                    if (!localActivityRecord.activity.mFinished) {
                        pendingActions = new PendingTransactionActions();
                        pendingActions.setOldState(clientRecord.state);
                        pendingActions.setRestoreInstanceState(true);
                        pendingActions.setCallOnPostCreate(true);
                    }
                    this.mActivityThread.handleStartActivity(clientRecord, pendingActions);
                    localActivityRecord.curState = 3;
                    if (i == 4) {
                        this.mActivityThread.performResumeActivity(localActivityRecord, true, "moveToState-INITIALIZING");
                        localActivityRecord.curState = 4;
                        return;
                    }
                    return;
                }
                return;
            }
            switch (localActivityRecord.curState) {
                case 2:
                    if (i == 3) {
                        this.mActivityThread.performRestartActivity(localActivityRecord, true);
                        localActivityRecord.curState = 3;
                    }
                    if (i == 4) {
                        this.mActivityThread.performRestartActivity(localActivityRecord, true);
                        this.mActivityThread.performResumeActivity(localActivityRecord, true, "moveToState-CREATED");
                        localActivityRecord.curState = 4;
                        return;
                    }
                    return;
                case 3:
                    if (i == 4) {
                        this.mActivityThread.performResumeActivity(localActivityRecord, true, "moveToState-STARTED");
                        localActivityRecord.instanceState = null;
                        localActivityRecord.curState = 4;
                    }
                    if (i == 2) {
                        this.mActivityThread.performStopActivity(localActivityRecord, false, "moveToState-STARTED");
                        localActivityRecord.curState = 2;
                        return;
                    }
                    return;
                case 4:
                    if (i == 3) {
                        performPause(localActivityRecord, this.mFinishing);
                        localActivityRecord.curState = 3;
                    }
                    if (i == 2) {
                        performPause(localActivityRecord, this.mFinishing);
                        this.mActivityThread.performStopActivity(localActivityRecord, false, "moveToState-RESUMED");
                        localActivityRecord.curState = 2;
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    }

    private void performPause(LocalActivityRecord r, boolean finishing) {
        boolean needState = r.instanceState == null;
        Bundle instanceState = this.mActivityThread.performPauseActivity((IBinder) r, finishing, "performPause", (PendingTransactionActions) null);
        if (needState) {
            r.instanceState = instanceState;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:7:0x001e, code lost:
        r2 = r4.intent.filterEquals(r10);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.view.Window startActivity(java.lang.String r9, android.content.Intent r10) {
        /*
            r8 = this;
            int r0 = r8.mCurState
            r1 = 1
            if (r0 == r1) goto L_0x00db
            r0 = 0
            r2 = 0
            r3 = 0
            java.util.Map<java.lang.String, android.app.LocalActivityManager$LocalActivityRecord> r4 = r8.mActivities
            java.lang.Object r4 = r4.get(r9)
            android.app.LocalActivityManager$LocalActivityRecord r4 = (android.app.LocalActivityManager.LocalActivityRecord) r4
            if (r4 != 0) goto L_0x001a
            android.app.LocalActivityManager$LocalActivityRecord r5 = new android.app.LocalActivityManager$LocalActivityRecord
            r5.<init>(r9, r10)
            r4 = r5
            r0 = 1
            goto L_0x0028
        L_0x001a:
            android.content.Intent r5 = r4.intent
            if (r5 == 0) goto L_0x0028
            android.content.Intent r5 = r4.intent
            boolean r2 = r5.filterEquals(r10)
            if (r2 == 0) goto L_0x0028
            android.content.pm.ActivityInfo r3 = r4.activityInfo
        L_0x0028:
            if (r3 != 0) goto L_0x0030
            android.app.ActivityThread r5 = r8.mActivityThread
            android.content.pm.ActivityInfo r3 = r5.resolveActivityInfo(r10)
        L_0x0030:
            boolean r5 = r8.mSingleMode
            if (r5 == 0) goto L_0x0043
            android.app.LocalActivityManager$LocalActivityRecord r5 = r8.mResumed
            if (r5 == 0) goto L_0x0043
            if (r5 == r4) goto L_0x0043
            int r6 = r8.mCurState
            r7 = 4
            if (r6 != r7) goto L_0x0043
            r6 = 3
            r8.moveToState(r5, r6)
        L_0x0043:
            if (r0 == 0) goto L_0x0051
            java.util.Map<java.lang.String, android.app.LocalActivityManager$LocalActivityRecord> r5 = r8.mActivities
            r5.put(r9, r4)
            java.util.ArrayList<android.app.LocalActivityManager$LocalActivityRecord> r5 = r8.mActivityArray
            r5.add(r4)
            goto L_0x00c7
        L_0x0051:
            android.content.pm.ActivityInfo r5 = r4.activityInfo
            if (r5 == 0) goto L_0x00c7
            android.content.pm.ActivityInfo r5 = r4.activityInfo
            if (r3 == r5) goto L_0x0071
            java.lang.String r5 = r3.name
            android.content.pm.ActivityInfo r6 = r4.activityInfo
            java.lang.String r6 = r6.name
            boolean r5 = r5.equals(r6)
            if (r5 == 0) goto L_0x009a
            java.lang.String r5 = r3.packageName
            android.content.pm.ActivityInfo r6 = r4.activityInfo
            java.lang.String r6 = r6.packageName
            boolean r5 = r5.equals(r6)
            if (r5 == 0) goto L_0x009a
        L_0x0071:
            int r5 = r3.launchMode
            if (r5 != 0) goto L_0x009e
            int r5 = r10.getFlags()
            r6 = 536870912(0x20000000, float:1.0842022E-19)
            r5 = r5 & r6
            if (r5 == 0) goto L_0x007f
            goto L_0x009e
        L_0x007f:
            if (r2 == 0) goto L_0x009a
            int r5 = r10.getFlags()
            r6 = 67108864(0x4000000, float:1.5046328E-36)
            r5 = r5 & r6
            if (r5 != 0) goto L_0x009a
            r4.intent = r10
            int r1 = r8.mCurState
            r8.moveToState(r4, r1)
            boolean r1 = r8.mSingleMode
            if (r1 == 0) goto L_0x0097
            r8.mResumed = r4
        L_0x0097:
            android.view.Window r1 = r4.window
            return r1
        L_0x009a:
            r8.performDestroy(r4, r1)
            goto L_0x00c7
        L_0x009e:
            java.util.ArrayList r5 = new java.util.ArrayList
            r5.<init>(r1)
            r1 = r5
            com.android.internal.content.ReferrerIntent r5 = new com.android.internal.content.ReferrerIntent
            android.app.Activity r6 = r8.mParent
            java.lang.String r6 = r6.getPackageName()
            r5.<init>(r10, r6)
            r1.add(r5)
            android.app.ActivityThread r5 = r8.mActivityThread
            r5.handleNewIntent(r4, r1)
            r4.intent = r10
            int r5 = r8.mCurState
            r8.moveToState(r4, r5)
            boolean r5 = r8.mSingleMode
            if (r5 == 0) goto L_0x00c4
            r8.mResumed = r4
        L_0x00c4:
            android.view.Window r5 = r4.window
            return r5
        L_0x00c7:
            r4.intent = r10
            r4.curState = r1
            r4.activityInfo = r3
            int r1 = r8.mCurState
            r8.moveToState(r4, r1)
            boolean r1 = r8.mSingleMode
            if (r1 == 0) goto L_0x00d8
            r8.mResumed = r4
        L_0x00d8:
            android.view.Window r1 = r4.window
            return r1
        L_0x00db:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "Activities can't be added until the containing group has been created."
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.app.LocalActivityManager.startActivity(java.lang.String, android.content.Intent):android.view.Window");
    }

    private Window performDestroy(LocalActivityRecord r, boolean finish) {
        Window win = r.window;
        if (r.curState == 4 && !finish) {
            performPause(r, finish);
        }
        this.mActivityThread.performDestroyActivity(r, finish, 0, false, "LocalActivityManager::performDestroy");
        r.activity = null;
        r.window = null;
        if (finish) {
            r.instanceState = null;
        }
        r.curState = 5;
        return win;
    }

    public Window destroyActivity(String id, boolean finish) {
        LocalActivityRecord r = this.mActivities.get(id);
        Window win = null;
        if (r != null) {
            win = performDestroy(r, finish);
            if (finish) {
                this.mActivities.remove(id);
                this.mActivityArray.remove(r);
            }
        }
        return win;
    }

    public Activity getCurrentActivity() {
        if (this.mResumed != null) {
            return this.mResumed.activity;
        }
        return null;
    }

    public String getCurrentId() {
        if (this.mResumed != null) {
            return this.mResumed.id;
        }
        return null;
    }

    public Activity getActivity(String id) {
        LocalActivityRecord r = this.mActivities.get(id);
        if (r != null) {
            return r.activity;
        }
        return null;
    }

    public void dispatchCreate(Bundle state) {
        if (state != null) {
            for (String id : state.keySet()) {
                try {
                    Bundle astate = state.getBundle(id);
                    LocalActivityRecord r = this.mActivities.get(id);
                    if (r != null) {
                        r.instanceState = astate;
                    } else {
                        LocalActivityRecord r2 = new LocalActivityRecord(id, (Intent) null);
                        r2.instanceState = astate;
                        this.mActivities.put(id, r2);
                        this.mActivityArray.add(r2);
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Exception thrown when restoring LocalActivityManager state", e);
                }
            }
        }
        this.mCurState = 2;
    }

    public Bundle saveInstanceState() {
        Bundle state = null;
        int N = this.mActivityArray.size();
        for (int i = 0; i < N; i++) {
            LocalActivityRecord r = this.mActivityArray.get(i);
            if (state == null) {
                state = new Bundle();
            }
            if ((r.instanceState != null || r.curState == 4) && r.activity != null) {
                Bundle childState = new Bundle();
                r.activity.performSaveInstanceState(childState);
                r.instanceState = childState;
            }
            if (r.instanceState != null) {
                state.putBundle(r.id, r.instanceState);
            }
        }
        return state;
    }

    public void dispatchResume() {
        this.mCurState = 4;
        if (!this.mSingleMode) {
            int N = this.mActivityArray.size();
            for (int i = 0; i < N; i++) {
                moveToState(this.mActivityArray.get(i), 4);
            }
        } else if (this.mResumed != null) {
            moveToState(this.mResumed, 4);
        }
    }

    public void dispatchPause(boolean finishing) {
        if (finishing) {
            this.mFinishing = true;
        }
        this.mCurState = 3;
        if (!this.mSingleMode) {
            int N = this.mActivityArray.size();
            for (int i = 0; i < N; i++) {
                LocalActivityRecord r = this.mActivityArray.get(i);
                if (r.curState == 4) {
                    moveToState(r, 3);
                }
            }
        } else if (this.mResumed != null) {
            moveToState(this.mResumed, 3);
        }
    }

    public void dispatchStop() {
        this.mCurState = 2;
        int N = this.mActivityArray.size();
        for (int i = 0; i < N; i++) {
            moveToState(this.mActivityArray.get(i), 2);
        }
    }

    public HashMap<String, Object> dispatchRetainNonConfigurationInstance() {
        Object instance;
        HashMap<String, Object> instanceMap = null;
        int N = this.mActivityArray.size();
        for (int i = 0; i < N; i++) {
            LocalActivityRecord r = this.mActivityArray.get(i);
            if (!(r == null || r.activity == null || (instance = r.activity.onRetainNonConfigurationInstance()) == null)) {
                if (instanceMap == null) {
                    instanceMap = new HashMap<>();
                }
                instanceMap.put(r.id, instance);
            }
        }
        return instanceMap;
    }

    public void removeAllActivities() {
        dispatchDestroy(true);
    }

    public void dispatchDestroy(boolean finishing) {
        int N = this.mActivityArray.size();
        for (int i = 0; i < N; i++) {
            this.mActivityThread.performDestroyActivity(this.mActivityArray.get(i), finishing, 0, false, "LocalActivityManager::dispatchDestroy");
        }
        this.mActivities.clear();
        this.mActivityArray.clear();
    }
}
