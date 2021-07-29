package android.widget;

import android.annotation.UnsupportedAppUsage;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.database.DataSetObservable;
import android.os.AsyncTask;
import android.os.Looper;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;
import com.android.internal.content.PackageMonitor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.xmlpull.v1.XmlSerializer;

public class ActivityChooserModel extends DataSetObservable {
    private static final String ATTRIBUTE_ACTIVITY = "activity";
    private static final String ATTRIBUTE_TIME = "time";
    private static final String ATTRIBUTE_WEIGHT = "weight";
    private static final boolean DEBUG = false;
    private static final int DEFAULT_ACTIVITY_INFLATION = 5;
    private static final float DEFAULT_HISTORICAL_RECORD_WEIGHT = 1.0f;
    public static final String DEFAULT_HISTORY_FILE_NAME = "activity_choser_model_history.xml";
    public static final int DEFAULT_HISTORY_MAX_LENGTH = 50;
    private static final String HISTORY_FILE_EXTENSION = ".xml";
    private static final int INVALID_INDEX = -1;
    /* access modifiers changed from: private */
    public static final String LOG_TAG = ActivityChooserModel.class.getSimpleName();
    private static final String TAG_HISTORICAL_RECORD = "historical-record";
    private static final String TAG_HISTORICAL_RECORDS = "historical-records";
    private static final Map<String, ActivityChooserModel> sDataModelRegistry = new HashMap();
    private static final Object sRegistryLock = new Object();
    private final List<ActivityResolveInfo> mActivities = new ArrayList();
    private OnChooseActivityListener mActivityChoserModelPolicy;
    private ActivitySorter mActivitySorter = new DefaultSorter();
    /* access modifiers changed from: private */
    public boolean mCanReadHistoricalData = true;
    /* access modifiers changed from: private */
    public final Context mContext;
    private final List<HistoricalRecord> mHistoricalRecords = new ArrayList();
    private boolean mHistoricalRecordsChanged = true;
    /* access modifiers changed from: private */
    public final String mHistoryFileName;
    private int mHistoryMaxSize = 50;
    private final Object mInstanceLock = new Object();
    private Intent mIntent;
    private final PackageMonitor mPackageMonitor = new DataModelPackageMonitor();
    private boolean mReadShareHistoryCalled = false;
    /* access modifiers changed from: private */
    public boolean mReloadActivities = false;

    public interface ActivityChooserModelClient {
        void setActivityChooserModel(ActivityChooserModel activityChooserModel);
    }

    public interface ActivitySorter {
        void sort(Intent intent, List<ActivityResolveInfo> list, List<HistoricalRecord> list2);
    }

    public interface OnChooseActivityListener {
        boolean onChooseActivity(ActivityChooserModel activityChooserModel, Intent intent);
    }

    @UnsupportedAppUsage
    public static ActivityChooserModel get(Context context, String historyFileName) {
        ActivityChooserModel dataModel;
        synchronized (sRegistryLock) {
            dataModel = sDataModelRegistry.get(historyFileName);
            if (dataModel == null) {
                dataModel = new ActivityChooserModel(context, historyFileName);
                sDataModelRegistry.put(historyFileName, dataModel);
            }
        }
        return dataModel;
    }

    private ActivityChooserModel(Context context, String historyFileName) {
        this.mContext = context.getApplicationContext();
        if (TextUtils.isEmpty(historyFileName) || historyFileName.endsWith(HISTORY_FILE_EXTENSION)) {
            this.mHistoryFileName = historyFileName;
        } else {
            this.mHistoryFileName = historyFileName + HISTORY_FILE_EXTENSION;
        }
        this.mPackageMonitor.register(this.mContext, (Looper) null, true);
    }

    @UnsupportedAppUsage
    public void setIntent(Intent intent) {
        synchronized (this.mInstanceLock) {
            if (this.mIntent != intent) {
                this.mIntent = intent;
                this.mReloadActivities = true;
                ensureConsistentState();
            }
        }
    }

    public Intent getIntent() {
        Intent intent;
        synchronized (this.mInstanceLock) {
            intent = this.mIntent;
        }
        return intent;
    }

    @UnsupportedAppUsage
    public int getActivityCount() {
        int size;
        synchronized (this.mInstanceLock) {
            ensureConsistentState();
            size = this.mActivities.size();
        }
        return size;
    }

    @UnsupportedAppUsage
    public ResolveInfo getActivity(int index) {
        ResolveInfo resolveInfo;
        synchronized (this.mInstanceLock) {
            ensureConsistentState();
            resolveInfo = this.mActivities.get(index).resolveInfo;
        }
        return resolveInfo;
    }

    public int getActivityIndex(ResolveInfo activity) {
        synchronized (this.mInstanceLock) {
            ensureConsistentState();
            List<ActivityResolveInfo> activities = this.mActivities;
            int activityCount = activities.size();
            for (int i = 0; i < activityCount; i++) {
                if (activities.get(i).resolveInfo == activity) {
                    return i;
                }
            }
            return -1;
        }
    }

    @UnsupportedAppUsage
    public Intent chooseActivity(int index) {
        synchronized (this.mInstanceLock) {
            if (this.mIntent == null) {
                return null;
            }
            ensureConsistentState();
            ActivityResolveInfo chosenActivity = this.mActivities.get(index);
            ComponentName chosenName = new ComponentName(chosenActivity.resolveInfo.activityInfo.packageName, chosenActivity.resolveInfo.activityInfo.name);
            Intent choiceIntent = new Intent(this.mIntent);
            choiceIntent.setComponent(chosenName);
            if (this.mActivityChoserModelPolicy != null) {
                if (this.mActivityChoserModelPolicy.onChooseActivity(this, new Intent(choiceIntent))) {
                    return null;
                }
            }
            addHisoricalRecord(new HistoricalRecord(chosenName, System.currentTimeMillis(), 1.0f));
            return choiceIntent;
        }
    }

    @UnsupportedAppUsage
    public void setOnChooseActivityListener(OnChooseActivityListener listener) {
        synchronized (this.mInstanceLock) {
            this.mActivityChoserModelPolicy = listener;
        }
    }

    public ResolveInfo getDefaultActivity() {
        synchronized (this.mInstanceLock) {
            ensureConsistentState();
            if (this.mActivities.isEmpty()) {
                return null;
            }
            ResolveInfo resolveInfo = this.mActivities.get(0).resolveInfo;
            return resolveInfo;
        }
    }

    public void setDefaultActivity(int index) {
        float weight;
        synchronized (this.mInstanceLock) {
            ensureConsistentState();
            ActivityResolveInfo newDefaultActivity = this.mActivities.get(index);
            ActivityResolveInfo oldDefaultActivity = this.mActivities.get(0);
            if (oldDefaultActivity != null) {
                weight = (oldDefaultActivity.weight - newDefaultActivity.weight) + 5.0f;
            } else {
                weight = 1.0f;
            }
            addHisoricalRecord(new HistoricalRecord(new ComponentName(newDefaultActivity.resolveInfo.activityInfo.packageName, newDefaultActivity.resolveInfo.activityInfo.name), System.currentTimeMillis(), weight));
        }
    }

    private void persistHistoricalDataIfNeeded() {
        if (!this.mReadShareHistoryCalled) {
            throw new IllegalStateException("No preceding call to #readHistoricalData");
        } else if (this.mHistoricalRecordsChanged) {
            this.mHistoricalRecordsChanged = false;
            if (!TextUtils.isEmpty(this.mHistoryFileName)) {
                new PersistHistoryAsyncTask().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, new ArrayList(this.mHistoricalRecords), this.mHistoryFileName);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0015, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setActivitySorter(android.widget.ActivityChooserModel.ActivitySorter r3) {
        /*
            r2 = this;
            java.lang.Object r0 = r2.mInstanceLock
            monitor-enter(r0)
            android.widget.ActivityChooserModel$ActivitySorter r1 = r2.mActivitySorter     // Catch:{ all -> 0x0016 }
            if (r1 != r3) goto L_0x0009
            monitor-exit(r0)     // Catch:{ all -> 0x0016 }
            return
        L_0x0009:
            r2.mActivitySorter = r3     // Catch:{ all -> 0x0016 }
            boolean r1 = r2.sortActivitiesIfNeeded()     // Catch:{ all -> 0x0016 }
            if (r1 == 0) goto L_0x0014
            r2.notifyChanged()     // Catch:{ all -> 0x0016 }
        L_0x0014:
            monitor-exit(r0)     // Catch:{ all -> 0x0016 }
            return
        L_0x0016:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0016 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.widget.ActivityChooserModel.setActivitySorter(android.widget.ActivityChooserModel$ActivitySorter):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0018, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setHistoryMaxSize(int r3) {
        /*
            r2 = this;
            java.lang.Object r0 = r2.mInstanceLock
            monitor-enter(r0)
            int r1 = r2.mHistoryMaxSize     // Catch:{ all -> 0x0019 }
            if (r1 != r3) goto L_0x0009
            monitor-exit(r0)     // Catch:{ all -> 0x0019 }
            return
        L_0x0009:
            r2.mHistoryMaxSize = r3     // Catch:{ all -> 0x0019 }
            r2.pruneExcessiveHistoricalRecordsIfNeeded()     // Catch:{ all -> 0x0019 }
            boolean r1 = r2.sortActivitiesIfNeeded()     // Catch:{ all -> 0x0019 }
            if (r1 == 0) goto L_0x0017
            r2.notifyChanged()     // Catch:{ all -> 0x0019 }
        L_0x0017:
            monitor-exit(r0)     // Catch:{ all -> 0x0019 }
            return
        L_0x0019:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0019 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.widget.ActivityChooserModel.setHistoryMaxSize(int):void");
    }

    public int getHistoryMaxSize() {
        int i;
        synchronized (this.mInstanceLock) {
            i = this.mHistoryMaxSize;
        }
        return i;
    }

    public int getHistorySize() {
        int size;
        synchronized (this.mInstanceLock) {
            ensureConsistentState();
            size = this.mHistoricalRecords.size();
        }
        return size;
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        super.finalize();
        this.mPackageMonitor.unregister();
    }

    private void ensureConsistentState() {
        boolean stateChanged = loadActivitiesIfNeeded() | readHistoricalDataIfNeeded();
        pruneExcessiveHistoricalRecordsIfNeeded();
        if (stateChanged) {
            sortActivitiesIfNeeded();
            notifyChanged();
        }
    }

    private boolean sortActivitiesIfNeeded() {
        if (this.mActivitySorter == null || this.mIntent == null || this.mActivities.isEmpty() || this.mHistoricalRecords.isEmpty()) {
            return false;
        }
        this.mActivitySorter.sort(this.mIntent, this.mActivities, Collections.unmodifiableList(this.mHistoricalRecords));
        return true;
    }

    private boolean loadActivitiesIfNeeded() {
        if (!this.mReloadActivities || this.mIntent == null) {
            return false;
        }
        this.mReloadActivities = false;
        this.mActivities.clear();
        List<ResolveInfo> resolveInfos = this.mContext.getPackageManager().queryIntentActivities(this.mIntent, 0);
        int resolveInfoCount = resolveInfos.size();
        for (int i = 0; i < resolveInfoCount; i++) {
            ResolveInfo resolveInfo = resolveInfos.get(i);
            ActivityInfo activityInfo = resolveInfo.activityInfo;
            if (ActivityManager.checkComponentPermission(activityInfo.permission, Process.myUid(), activityInfo.applicationInfo.uid, activityInfo.exported) == 0) {
                this.mActivities.add(new ActivityResolveInfo(resolveInfo));
            }
        }
        return true;
    }

    private boolean readHistoricalDataIfNeeded() {
        if (!this.mCanReadHistoricalData || !this.mHistoricalRecordsChanged || TextUtils.isEmpty(this.mHistoryFileName)) {
            return false;
        }
        this.mCanReadHistoricalData = false;
        this.mReadShareHistoryCalled = true;
        readHistoricalDataImpl();
        return true;
    }

    private boolean addHisoricalRecord(HistoricalRecord historicalRecord) {
        boolean added = this.mHistoricalRecords.add(historicalRecord);
        if (added) {
            this.mHistoricalRecordsChanged = true;
            pruneExcessiveHistoricalRecordsIfNeeded();
            persistHistoricalDataIfNeeded();
            sortActivitiesIfNeeded();
            notifyChanged();
        }
        return added;
    }

    private void pruneExcessiveHistoricalRecordsIfNeeded() {
        int pruneCount = this.mHistoricalRecords.size() - this.mHistoryMaxSize;
        if (pruneCount > 0) {
            this.mHistoricalRecordsChanged = true;
            for (int i = 0; i < pruneCount; i++) {
                HistoricalRecord remove = this.mHistoricalRecords.remove(0);
            }
        }
    }

    public static final class HistoricalRecord {
        public final ComponentName activity;
        public final long time;
        public final float weight;

        public HistoricalRecord(String activityName, long time2, float weight2) {
            this(ComponentName.unflattenFromString(activityName), time2, weight2);
        }

        public HistoricalRecord(ComponentName activityName, long time2, float weight2) {
            this.activity = activityName;
            this.time = time2;
            this.weight = weight2;
        }

        public int hashCode() {
            return (((((1 * 31) + (this.activity == null ? 0 : this.activity.hashCode())) * 31) + ((int) (this.time ^ (this.time >>> 32)))) * 31) + Float.floatToIntBits(this.weight);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            HistoricalRecord other = (HistoricalRecord) obj;
            if (this.activity == null) {
                if (other.activity != null) {
                    return false;
                }
            } else if (!this.activity.equals(other.activity)) {
                return false;
            }
            if (this.time == other.time && Float.floatToIntBits(this.weight) == Float.floatToIntBits(other.weight)) {
                return true;
            }
            return false;
        }

        public String toString() {
            return "[" + "; activity:" + this.activity + "; time:" + this.time + "; weight:" + new BigDecimal((double) this.weight) + "]";
        }
    }

    public final class ActivityResolveInfo implements Comparable<ActivityResolveInfo> {
        public final ResolveInfo resolveInfo;
        public float weight;

        public ActivityResolveInfo(ResolveInfo resolveInfo2) {
            this.resolveInfo = resolveInfo2;
        }

        public int hashCode() {
            return Float.floatToIntBits(this.weight) + 31;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj != null && getClass() == obj.getClass() && Float.floatToIntBits(this.weight) == Float.floatToIntBits(((ActivityResolveInfo) obj).weight)) {
                return true;
            }
            return false;
        }

        public int compareTo(ActivityResolveInfo another) {
            return Float.floatToIntBits(another.weight) - Float.floatToIntBits(this.weight);
        }

        public String toString() {
            return "[" + "resolveInfo:" + this.resolveInfo.toString() + "; weight:" + new BigDecimal((double) this.weight) + "]";
        }
    }

    private final class DefaultSorter implements ActivitySorter {
        private static final float WEIGHT_DECAY_COEFFICIENT = 0.95f;
        private final Map<ComponentName, ActivityResolveInfo> mPackageNameToActivityMap;

        private DefaultSorter() {
            this.mPackageNameToActivityMap = new HashMap();
        }

        public void sort(Intent intent, List<ActivityResolveInfo> activities, List<HistoricalRecord> historicalRecords) {
            Map<ComponentName, ActivityResolveInfo> componentNameToActivityMap = this.mPackageNameToActivityMap;
            componentNameToActivityMap.clear();
            int activityCount = activities.size();
            for (int i = 0; i < activityCount; i++) {
                ActivityResolveInfo activity = activities.get(i);
                activity.weight = 0.0f;
                componentNameToActivityMap.put(new ComponentName(activity.resolveInfo.activityInfo.packageName, activity.resolveInfo.activityInfo.name), activity);
            }
            float nextRecordWeight = 1.0f;
            for (int i2 = historicalRecords.size() - 1; i2 >= 0; i2--) {
                HistoricalRecord historicalRecord = historicalRecords.get(i2);
                ActivityResolveInfo activity2 = componentNameToActivityMap.get(historicalRecord.activity);
                if (activity2 != null) {
                    activity2.weight += historicalRecord.weight * nextRecordWeight;
                    nextRecordWeight *= 0.95f;
                }
            }
            Collections.sort(activities);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:17:0x003f, code lost:
        if (r1 == null) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:?, code lost:
        r1.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:?, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void readHistoricalDataImpl() {
        /*
            r12 = this;
            r0 = 0
            r1 = r0
            android.content.Context r2 = r12.mContext     // Catch:{ FileNotFoundException -> 0x00d9 }
            java.lang.String r3 = r12.mHistoryFileName     // Catch:{ FileNotFoundException -> 0x00d9 }
            java.io.FileInputStream r2 = r2.openFileInput(r3)     // Catch:{ FileNotFoundException -> 0x00d9 }
            r1 = r2
            org.xmlpull.v1.XmlPullParser r2 = android.util.Xml.newPullParser()     // Catch:{ XmlPullParserException -> 0x00af, IOException -> 0x0090 }
            java.nio.charset.Charset r3 = java.nio.charset.StandardCharsets.UTF_8     // Catch:{ XmlPullParserException -> 0x00af, IOException -> 0x0090 }
            java.lang.String r3 = r3.name()     // Catch:{ XmlPullParserException -> 0x00af, IOException -> 0x0090 }
            r2.setInput(r1, r3)     // Catch:{ XmlPullParserException -> 0x00af, IOException -> 0x0090 }
            r3 = 0
        L_0x001a:
            r4 = 1
            if (r3 == r4) goto L_0x0026
            r5 = 2
            if (r3 == r5) goto L_0x0026
            int r4 = r2.next()     // Catch:{ XmlPullParserException -> 0x00af, IOException -> 0x0090 }
            r3 = r4
            goto L_0x001a
        L_0x0026:
            java.lang.String r5 = "historical-records"
            java.lang.String r6 = r2.getName()     // Catch:{ XmlPullParserException -> 0x00af, IOException -> 0x0090 }
            boolean r5 = r5.equals(r6)     // Catch:{ XmlPullParserException -> 0x00af, IOException -> 0x0090 }
            if (r5 == 0) goto L_0x0086
            java.util.List<android.widget.ActivityChooserModel$HistoricalRecord> r5 = r12.mHistoricalRecords     // Catch:{ XmlPullParserException -> 0x00af, IOException -> 0x0090 }
            r5.clear()     // Catch:{ XmlPullParserException -> 0x00af, IOException -> 0x0090 }
        L_0x0037:
            int r6 = r2.next()     // Catch:{ XmlPullParserException -> 0x00af, IOException -> 0x0090 }
            r3 = r6
            if (r3 != r4) goto L_0x0046
            if (r1 == 0) goto L_0x00d0
            r1.close()     // Catch:{ IOException -> 0x00ce }
            goto L_0x00cd
        L_0x0046:
            r6 = 3
            if (r3 == r6) goto L_0x0037
            r6 = 4
            if (r3 != r6) goto L_0x004d
            goto L_0x0037
        L_0x004d:
            java.lang.String r6 = r2.getName()     // Catch:{ XmlPullParserException -> 0x00af, IOException -> 0x0090 }
            java.lang.String r7 = "historical-record"
            boolean r7 = r7.equals(r6)     // Catch:{ XmlPullParserException -> 0x00af, IOException -> 0x0090 }
            if (r7 == 0) goto L_0x007e
            java.lang.String r7 = "activity"
            java.lang.String r7 = r2.getAttributeValue(r0, r7)     // Catch:{ XmlPullParserException -> 0x00af, IOException -> 0x0090 }
            java.lang.String r8 = "time"
            java.lang.String r8 = r2.getAttributeValue(r0, r8)     // Catch:{ XmlPullParserException -> 0x00af, IOException -> 0x0090 }
            long r8 = java.lang.Long.parseLong(r8)     // Catch:{ XmlPullParserException -> 0x00af, IOException -> 0x0090 }
            java.lang.String r10 = "weight"
            java.lang.String r10 = r2.getAttributeValue(r0, r10)     // Catch:{ XmlPullParserException -> 0x00af, IOException -> 0x0090 }
            float r10 = java.lang.Float.parseFloat(r10)     // Catch:{ XmlPullParserException -> 0x00af, IOException -> 0x0090 }
            android.widget.ActivityChooserModel$HistoricalRecord r11 = new android.widget.ActivityChooserModel$HistoricalRecord     // Catch:{ XmlPullParserException -> 0x00af, IOException -> 0x0090 }
            r11.<init>((java.lang.String) r7, (long) r8, (float) r10)     // Catch:{ XmlPullParserException -> 0x00af, IOException -> 0x0090 }
            r5.add(r11)     // Catch:{ XmlPullParserException -> 0x00af, IOException -> 0x0090 }
            goto L_0x0037
        L_0x007e:
            org.xmlpull.v1.XmlPullParserException r0 = new org.xmlpull.v1.XmlPullParserException     // Catch:{ XmlPullParserException -> 0x00af, IOException -> 0x0090 }
            java.lang.String r4 = "Share records file not well-formed."
            r0.<init>(r4)     // Catch:{ XmlPullParserException -> 0x00af, IOException -> 0x0090 }
            throw r0     // Catch:{ XmlPullParserException -> 0x00af, IOException -> 0x0090 }
        L_0x0086:
            org.xmlpull.v1.XmlPullParserException r0 = new org.xmlpull.v1.XmlPullParserException     // Catch:{ XmlPullParserException -> 0x00af, IOException -> 0x0090 }
            java.lang.String r4 = "Share records file does not start with historical-records tag."
            r0.<init>(r4)     // Catch:{ XmlPullParserException -> 0x00af, IOException -> 0x0090 }
            throw r0     // Catch:{ XmlPullParserException -> 0x00af, IOException -> 0x0090 }
        L_0x008e:
            r0 = move-exception
            goto L_0x00d1
        L_0x0090:
            r0 = move-exception
            java.lang.String r2 = LOG_TAG     // Catch:{ all -> 0x008e }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x008e }
            r3.<init>()     // Catch:{ all -> 0x008e }
            java.lang.String r4 = "Error reading historical recrod file: "
            r3.append(r4)     // Catch:{ all -> 0x008e }
            java.lang.String r4 = r12.mHistoryFileName     // Catch:{ all -> 0x008e }
            r3.append(r4)     // Catch:{ all -> 0x008e }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x008e }
            android.util.Log.e(r2, r3, r0)     // Catch:{ all -> 0x008e }
            if (r1 == 0) goto L_0x00d0
            r1.close()     // Catch:{ IOException -> 0x00ce }
            goto L_0x00cd
        L_0x00af:
            r0 = move-exception
            java.lang.String r2 = LOG_TAG     // Catch:{ all -> 0x008e }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x008e }
            r3.<init>()     // Catch:{ all -> 0x008e }
            java.lang.String r4 = "Error reading historical recrod file: "
            r3.append(r4)     // Catch:{ all -> 0x008e }
            java.lang.String r4 = r12.mHistoryFileName     // Catch:{ all -> 0x008e }
            r3.append(r4)     // Catch:{ all -> 0x008e }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x008e }
            android.util.Log.e(r2, r3, r0)     // Catch:{ all -> 0x008e }
            if (r1 == 0) goto L_0x00d0
            r1.close()     // Catch:{ IOException -> 0x00ce }
        L_0x00cd:
            goto L_0x00d0
        L_0x00ce:
            r0 = move-exception
            goto L_0x00cd
        L_0x00d0:
            return
        L_0x00d1:
            if (r1 == 0) goto L_0x00d8
            r1.close()     // Catch:{ IOException -> 0x00d7 }
            goto L_0x00d8
        L_0x00d7:
            r2 = move-exception
        L_0x00d8:
            throw r0
        L_0x00d9:
            r0 = move-exception
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.widget.ActivityChooserModel.readHistoricalDataImpl():void");
    }

    private final class PersistHistoryAsyncTask extends AsyncTask<Object, Void, Void> {
        private PersistHistoryAsyncTask() {
        }

        public Void doInBackground(Object... args) {
            List<HistoricalRecord> historicalRecords = args[0];
            String hostoryFileName = args[1];
            try {
                FileOutputStream fos = ActivityChooserModel.this.mContext.openFileOutput(hostoryFileName, 0);
                XmlSerializer serializer = Xml.newSerializer();
                try {
                    serializer.setOutput(fos, (String) null);
                    serializer.startDocument(StandardCharsets.UTF_8.name(), true);
                    serializer.startTag((String) null, ActivityChooserModel.TAG_HISTORICAL_RECORDS);
                    int recordCount = historicalRecords.size();
                    for (int i = 0; i < recordCount; i++) {
                        HistoricalRecord record = historicalRecords.remove(0);
                        serializer.startTag((String) null, ActivityChooserModel.TAG_HISTORICAL_RECORD);
                        serializer.attribute((String) null, "activity", record.activity.flattenToString());
                        serializer.attribute((String) null, "time", String.valueOf(record.time));
                        serializer.attribute((String) null, "weight", String.valueOf(record.weight));
                        serializer.endTag((String) null, ActivityChooserModel.TAG_HISTORICAL_RECORD);
                    }
                    serializer.endTag((String) null, ActivityChooserModel.TAG_HISTORICAL_RECORDS);
                    serializer.endDocument();
                    boolean unused = ActivityChooserModel.this.mCanReadHistoricalData = true;
                    if (fos != null) {
                        try {
                            fos.close();
                        } catch (IOException e) {
                        }
                    }
                } catch (IllegalArgumentException iae) {
                    Log.e(ActivityChooserModel.LOG_TAG, "Error writing historical recrod file: " + ActivityChooserModel.this.mHistoryFileName, iae);
                    boolean unused2 = ActivityChooserModel.this.mCanReadHistoricalData = true;
                    if (fos != null) {
                        fos.close();
                    }
                } catch (IllegalStateException ise) {
                    Log.e(ActivityChooserModel.LOG_TAG, "Error writing historical recrod file: " + ActivityChooserModel.this.mHistoryFileName, ise);
                    boolean unused3 = ActivityChooserModel.this.mCanReadHistoricalData = true;
                    if (fos != null) {
                        fos.close();
                    }
                } catch (IOException ioe) {
                    Log.e(ActivityChooserModel.LOG_TAG, "Error writing historical recrod file: " + ActivityChooserModel.this.mHistoryFileName, ioe);
                    boolean unused4 = ActivityChooserModel.this.mCanReadHistoricalData = true;
                    if (fos != null) {
                        fos.close();
                    }
                } catch (Throwable th) {
                    boolean unused5 = ActivityChooserModel.this.mCanReadHistoricalData = true;
                    if (fos != null) {
                        try {
                            fos.close();
                        } catch (IOException e2) {
                        }
                    }
                    throw th;
                }
                return null;
            } catch (FileNotFoundException fnfe) {
                Log.e(ActivityChooserModel.LOG_TAG, "Error writing historical recrod file: " + hostoryFileName, fnfe);
                return null;
            }
        }
    }

    private final class DataModelPackageMonitor extends PackageMonitor {
        private DataModelPackageMonitor() {
        }

        public void onSomePackagesChanged() {
            boolean unused = ActivityChooserModel.this.mReloadActivities = true;
        }
    }
}
