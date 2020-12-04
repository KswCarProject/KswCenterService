package com.android.internal.os;

import android.os.Binder;
import android.os.Process;
import android.os.SystemClock;
import android.text.format.DateFormat;
import android.util.ArrayMap;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseArray;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.logging.nano.MetricsProto;
import com.android.internal.os.BinderInternal;
import com.android.internal.os.CachedDeviceState;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;
import java.util.function.ToDoubleFunction;

public class BinderCallsStats implements BinderInternal.Observer {
    private static final int CALL_SESSIONS_POOL_SIZE = 100;
    private static final String DEBUG_ENTRY_PREFIX = "__DEBUG_";
    public static final boolean DEFAULT_TRACK_DIRECT_CALLING_UID = true;
    public static final boolean DEFAULT_TRACK_SCREEN_INTERACTIVE = false;
    public static final boolean DETAILED_TRACKING_DEFAULT = true;
    public static final boolean ENABLED_DEFAULT = true;
    private static final String EXCEPTION_COUNT_OVERFLOW_NAME = "overflow";
    public static final int MAX_BINDER_CALL_STATS_COUNT_DEFAULT = 1500;
    private static final int MAX_EXCEPTION_COUNT_SIZE = 50;
    /* access modifiers changed from: private */
    public static final Class<? extends Binder> OVERFLOW_BINDER = OverflowBinder.class;
    private static final int OVERFLOW_DIRECT_CALLING_UID = -1;
    private static final boolean OVERFLOW_SCREEN_INTERACTIVE = false;
    private static final int OVERFLOW_TRANSACTION_CODE = -1;
    public static final int PERIODIC_SAMPLING_INTERVAL_DEFAULT = 1000;
    private static final String TAG = "BinderCallsStats";
    private boolean mAddDebugEntries = false;
    private CachedDeviceState.TimeInStateStopwatch mBatteryStopwatch;
    private final Queue<BinderInternal.CallSession> mCallSessionsPool = new ConcurrentLinkedQueue();
    private long mCallStatsCount = 0;
    private boolean mDetailedTracking = true;
    private CachedDeviceState.Readonly mDeviceState;
    @GuardedBy({"mLock"})
    private final ArrayMap<String, Integer> mExceptionCounts = new ArrayMap<>();
    private final Object mLock = new Object();
    private int mMaxBinderCallStatsCount = 1500;
    private int mPeriodicSamplingInterval = 1000;
    private final Random mRandom;
    private long mStartCurrentTime = System.currentTimeMillis();
    private long mStartElapsedTime = SystemClock.elapsedRealtime();
    private boolean mTrackDirectCallingUid = true;
    private boolean mTrackScreenInteractive = false;
    @GuardedBy({"mLock"})
    private final SparseArray<UidEntry> mUidEntries = new SparseArray<>();

    public static class ExportedCallStat {
        Class<? extends Binder> binderClass;
        public long callCount;
        public int callingUid;
        public String className;
        public long cpuTimeMicros;
        public long exceptionCount;
        public long latencyMicros;
        public long maxCpuTimeMicros;
        public long maxLatencyMicros;
        public long maxReplySizeBytes;
        public long maxRequestSizeBytes;
        public String methodName;
        public long recordedCallCount;
        public boolean screenInteractive;
        int transactionCode;
        public int workSourceUid;
    }

    private static class OverflowBinder extends Binder {
        private OverflowBinder() {
        }
    }

    public static class Injector {
        public Random getRandomGenerator() {
            return new Random();
        }
    }

    public BinderCallsStats(Injector injector) {
        this.mRandom = injector.getRandomGenerator();
    }

    public void setDeviceState(CachedDeviceState.Readonly deviceState) {
        if (this.mBatteryStopwatch != null) {
            this.mBatteryStopwatch.close();
        }
        this.mDeviceState = deviceState;
        this.mBatteryStopwatch = deviceState.createTimeOnBatteryStopwatch();
    }

    public BinderInternal.CallSession callStarted(Binder binder, int code, int workSourceUid) {
        if (this.mDeviceState == null || this.mDeviceState.isCharging()) {
            return null;
        }
        BinderInternal.CallSession s = obtainCallSession();
        s.binderClass = binder.getClass();
        s.transactionCode = code;
        s.exceptionThrown = false;
        s.cpuTimeStarted = -1;
        s.timeStarted = -1;
        if (shouldRecordDetailedData()) {
            s.cpuTimeStarted = getThreadTimeMicro();
            s.timeStarted = getElapsedRealtimeMicro();
        }
        return s;
    }

    private BinderInternal.CallSession obtainCallSession() {
        BinderInternal.CallSession s = this.mCallSessionsPool.poll();
        return s == null ? new BinderInternal.CallSession() : s;
    }

    public void callEnded(BinderInternal.CallSession s, int parcelRequestSize, int parcelReplySize, int workSourceUid) {
        if (s != null) {
            processCallEnded(s, parcelRequestSize, parcelReplySize, workSourceUid);
            if (this.mCallSessionsPool.size() < 100) {
                this.mCallSessionsPool.add(s);
            }
        }
    }

    /* JADX WARNING: type inference failed for: r5v0 */
    /* JADX WARNING: type inference failed for: r5v1 */
    /* JADX WARNING: type inference failed for: r5v14 */
    /* JADX WARNING: type inference failed for: r5v17 */
    /* JADX WARNING: type inference failed for: r5v18 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void processCallEnded(com.android.internal.os.BinderInternal.CallSession r27, int r28, int r29, int r30) {
        /*
            r26 = this;
            r1 = r26
            r2 = r27
            long r3 = r2.cpuTimeStarted
            r5 = 0
            int r0 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r0 < 0) goto L_0x000e
            r0 = 1
            goto L_0x000f
        L_0x000e:
            r0 = 0
        L_0x000f:
            r7 = r0
            if (r7 == 0) goto L_0x0021
            long r8 = r26.getThreadTimeMicro()
            long r10 = r2.cpuTimeStarted
            long r8 = r8 - r10
            long r10 = r26.getElapsedRealtimeMicro()
            long r12 = r2.timeStarted
            long r10 = r10 - r12
            goto L_0x0024
        L_0x0021:
            r8 = 0
            r10 = r5
        L_0x0024:
            boolean r0 = r1.mTrackScreenInteractive
            if (r0 == 0) goto L_0x002f
            com.android.internal.os.CachedDeviceState$Readonly r0 = r1.mDeviceState
            boolean r0 = r0.isScreenInteractive()
            goto L_0x0030
        L_0x002f:
            r0 = 0
        L_0x0030:
            r15 = r0
            boolean r0 = r1.mTrackDirectCallingUid
            if (r0 == 0) goto L_0x003a
            int r0 = r26.getCallingUid()
            goto L_0x003b
        L_0x003a:
            r0 = -1
        L_0x003b:
            r14 = r0
            java.lang.Object r13 = r1.mLock
            monitor-enter(r13)
            com.android.internal.os.CachedDeviceState$Readonly r0 = r1.mDeviceState     // Catch:{ all -> 0x0150 }
            if (r0 == 0) goto L_0x0143
            com.android.internal.os.CachedDeviceState$Readonly r0 = r1.mDeviceState     // Catch:{ all -> 0x0150 }
            boolean r0 = r0.isCharging()     // Catch:{ all -> 0x0150 }
            if (r0 == 0) goto L_0x0058
            r21 = r7
            r23 = r8
            r5 = r13
            r6 = r14
            r3 = r15
            r14 = r28
            r9 = r29
            goto L_0x014e
        L_0x0058:
            r12 = r30
            com.android.internal.os.BinderCallsStats$UidEntry r0 = r1.getUidEntry(r12)     // Catch:{ all -> 0x0150 }
            long r3 = r0.callCount     // Catch:{ all -> 0x0150 }
            r19 = 1
            long r3 = r3 + r19
            r0.callCount = r3     // Catch:{ all -> 0x0150 }
            if (r7 == 0) goto L_0x0126
            long r3 = r0.cpuTimeMicros     // Catch:{ all -> 0x0150 }
            long r3 = r3 + r8
            r0.cpuTimeMicros = r3     // Catch:{ all -> 0x0150 }
            long r3 = r0.recordedCallCount     // Catch:{ all -> 0x0150 }
            long r3 = r3 + r19
            r0.recordedCallCount = r3     // Catch:{ all -> 0x0150 }
            java.lang.Class<? extends android.os.Binder> r3 = r2.binderClass     // Catch:{ all -> 0x0150 }
            int r4 = r2.transactionCode     // Catch:{ all -> 0x0150 }
            long r5 = r1.mCallStatsCount     // Catch:{ all -> 0x0150 }
            r21 = r7
            int r7 = r1.mMaxBinderCallStatsCount     // Catch:{ all -> 0x011b }
            r22 = r13
            long r12 = (long) r7
            int r5 = (r5 > r12 ? 1 : (r5 == r12 ? 0 : -1))
            if (r5 < 0) goto L_0x0087
            r17 = 1
            goto L_0x0089
        L_0x0087:
            r17 = 0
        L_0x0089:
            r12 = r0
            r5 = r22
            r13 = r14
            r6 = r14
            r14 = r3
            r3 = r15
            r15 = r4
            r16 = r3
            com.android.internal.os.BinderCallsStats$CallStat r4 = r12.getOrCreate(r13, r14, r15, r16, r17)     // Catch:{ all -> 0x0113 }
            long r12 = r4.callCount     // Catch:{ all -> 0x0113 }
            r14 = 0
            int r7 = (r12 > r14 ? 1 : (r12 == r14 ? 0 : -1))
            if (r7 != 0) goto L_0x00a2
            r18 = 1
            goto L_0x00a4
        L_0x00a2:
            r18 = 0
        L_0x00a4:
            r7 = r18
            if (r7 == 0) goto L_0x00b8
            long r12 = r1.mCallStatsCount     // Catch:{ all -> 0x00af }
            long r12 = r12 + r19
            r1.mCallStatsCount = r12     // Catch:{ all -> 0x00af }
            goto L_0x00b8
        L_0x00af:
            r0 = move-exception
            r14 = r28
            r23 = r8
        L_0x00b4:
            r9 = r29
            goto L_0x015c
        L_0x00b8:
            long r12 = r4.callCount     // Catch:{ all -> 0x0113 }
            long r12 = r12 + r19
            r4.callCount = r12     // Catch:{ all -> 0x0113 }
            long r12 = r4.recordedCallCount     // Catch:{ all -> 0x0113 }
            long r12 = r12 + r19
            r4.recordedCallCount = r12     // Catch:{ all -> 0x0113 }
            long r12 = r4.cpuTimeMicros     // Catch:{ all -> 0x0113 }
            long r12 = r12 + r8
            r4.cpuTimeMicros = r12     // Catch:{ all -> 0x0113 }
            long r12 = r4.maxCpuTimeMicros     // Catch:{ all -> 0x0113 }
            long r12 = java.lang.Math.max(r12, r8)     // Catch:{ all -> 0x0113 }
            r4.maxCpuTimeMicros = r12     // Catch:{ all -> 0x0113 }
            long r12 = r4.latencyMicros     // Catch:{ all -> 0x0113 }
            long r12 = r12 + r10
            r4.latencyMicros = r12     // Catch:{ all -> 0x0113 }
            long r12 = r4.maxLatencyMicros     // Catch:{ all -> 0x0113 }
            long r12 = java.lang.Math.max(r12, r10)     // Catch:{ all -> 0x0113 }
            r4.maxLatencyMicros = r12     // Catch:{ all -> 0x0113 }
            boolean r12 = r1.mDetailedTracking     // Catch:{ all -> 0x0113 }
            if (r12 == 0) goto L_0x010c
            long r12 = r4.exceptionCount     // Catch:{ all -> 0x0113 }
            boolean r14 = r2.exceptionThrown     // Catch:{ all -> 0x0113 }
            if (r14 == 0) goto L_0x00e9
            goto L_0x00eb
        L_0x00e9:
            r19 = 0
        L_0x00eb:
            long r12 = r12 + r19
            r4.exceptionCount = r12     // Catch:{ all -> 0x0113 }
            long r12 = r4.maxRequestSizeBytes     // Catch:{ all -> 0x0113 }
            r14 = r28
            r25 = r7
            r23 = r8
            long r7 = (long) r14
            long r7 = java.lang.Math.max(r12, r7)     // Catch:{ all -> 0x010a }
            r4.maxRequestSizeBytes = r7     // Catch:{ all -> 0x010a }
            long r7 = r4.maxReplySizeBytes     // Catch:{ all -> 0x010a }
            r9 = r29
            long r12 = (long) r9
            long r7 = java.lang.Math.max(r7, r12)     // Catch:{ all -> 0x015e }
            r4.maxReplySizeBytes = r7     // Catch:{ all -> 0x015e }
            goto L_0x0112
        L_0x010a:
            r0 = move-exception
            goto L_0x00b4
        L_0x010c:
            r14 = r28
            r23 = r8
            r9 = r29
        L_0x0112:
            goto L_0x0141
        L_0x0113:
            r0 = move-exception
            r14 = r28
            r23 = r8
            r9 = r29
            goto L_0x015c
        L_0x011b:
            r0 = move-exception
            r23 = r8
            r5 = r13
            r6 = r14
            r3 = r15
            r14 = r28
            r9 = r29
            goto L_0x015c
        L_0x0126:
            r21 = r7
            r23 = r8
            r5 = r13
            r6 = r14
            r3 = r15
            r14 = r28
            r9 = r29
            java.lang.Class<? extends android.os.Binder> r4 = r2.binderClass     // Catch:{ all -> 0x015e }
            int r7 = r2.transactionCode     // Catch:{ all -> 0x015e }
            com.android.internal.os.BinderCallsStats$CallStat r4 = r0.get(r6, r4, r7, r3)     // Catch:{ all -> 0x015e }
            if (r4 == 0) goto L_0x0141
            long r7 = r4.callCount     // Catch:{ all -> 0x015e }
            long r7 = r7 + r19
            r4.callCount = r7     // Catch:{ all -> 0x015e }
        L_0x0141:
            monitor-exit(r5)     // Catch:{ all -> 0x015e }
            return
        L_0x0143:
            r21 = r7
            r23 = r8
            r5 = r13
            r6 = r14
            r3 = r15
            r14 = r28
            r9 = r29
        L_0x014e:
            monitor-exit(r5)     // Catch:{ all -> 0x015e }
            return
        L_0x0150:
            r0 = move-exception
            r21 = r7
            r23 = r8
            r5 = r13
            r6 = r14
            r3 = r15
            r14 = r28
            r9 = r29
        L_0x015c:
            monitor-exit(r5)     // Catch:{ all -> 0x015e }
            throw r0
        L_0x015e:
            r0 = move-exception
            goto L_0x015c
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.os.BinderCallsStats.processCallEnded(com.android.internal.os.BinderInternal$CallSession, int, int, int):void");
    }

    private UidEntry getUidEntry(int uid) {
        UidEntry uidEntry = this.mUidEntries.get(uid);
        if (uidEntry != null) {
            return uidEntry;
        }
        UidEntry uidEntry2 = new UidEntry(uid);
        this.mUidEntries.put(uid, uidEntry2);
        return uidEntry2;
    }

    public void callThrewException(BinderInternal.CallSession s, Exception exception) {
        if (s != null) {
            int i = 1;
            s.exceptionThrown = true;
            try {
                String className = exception.getClass().getName();
                synchronized (this.mLock) {
                    if (this.mExceptionCounts.size() >= 50) {
                        className = EXCEPTION_COUNT_OVERFLOW_NAME;
                    }
                    Integer count = this.mExceptionCounts.get(className);
                    ArrayMap<String, Integer> arrayMap = this.mExceptionCounts;
                    if (count != null) {
                        i = 1 + count.intValue();
                    }
                    arrayMap.put(className, Integer.valueOf(i));
                }
            } catch (RuntimeException e) {
                Slog.wtf(TAG, "Unexpected exception while updating mExceptionCounts");
            }
        }
    }

    private Method getDefaultTransactionNameMethod(Class<? extends Binder> binder) {
        try {
            return binder.getMethod("getDefaultTransactionName", new Class[]{Integer.TYPE});
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    private String resolveTransactionCode(Method getDefaultTransactionName, int transactionCode) {
        if (getDefaultTransactionName == null) {
            return null;
        }
        try {
            return (String) getDefaultTransactionName.invoke((Object) null, new Object[]{Integer.valueOf(transactionCode)});
        } catch (ClassCastException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<ExportedCallStat> getExportedCallStats() {
        String methodName;
        String str;
        if (!this.mDetailedTracking) {
            return new ArrayList<>();
        }
        ArrayList<ExportedCallStat> resultCallStats = new ArrayList<>();
        synchronized (this.mLock) {
            int uidEntriesSize = this.mUidEntries.size();
            for (int entryIdx = 0; entryIdx < uidEntriesSize; entryIdx++) {
                UidEntry entry = this.mUidEntries.valueAt(entryIdx);
                for (CallStat stat : entry.getCallStatsList()) {
                    ExportedCallStat exported = new ExportedCallStat();
                    exported.workSourceUid = entry.workSourceUid;
                    exported.callingUid = stat.callingUid;
                    exported.className = stat.binderClass.getName();
                    exported.binderClass = stat.binderClass;
                    exported.transactionCode = stat.transactionCode;
                    exported.screenInteractive = stat.screenInteractive;
                    exported.cpuTimeMicros = stat.cpuTimeMicros;
                    exported.maxCpuTimeMicros = stat.maxCpuTimeMicros;
                    exported.latencyMicros = stat.latencyMicros;
                    exported.maxLatencyMicros = stat.maxLatencyMicros;
                    exported.recordedCallCount = stat.recordedCallCount;
                    exported.callCount = stat.callCount;
                    exported.maxRequestSizeBytes = stat.maxRequestSizeBytes;
                    exported.maxReplySizeBytes = stat.maxReplySizeBytes;
                    exported.exceptionCount = stat.exceptionCount;
                    resultCallStats.add(exported);
                }
            }
        }
        Method getDefaultTransactionName = null;
        String previousMethodName = null;
        resultCallStats.sort($$Lambda$BinderCallsStats$sqXweH5BoxhmZvI188ctqYiACRk.INSTANCE);
        Iterator<ExportedCallStat> it = resultCallStats.iterator();
        while (it.hasNext()) {
            ExportedCallStat exported2 = it.next();
            boolean isCodeDifferent = true;
            boolean isClassDifferent = 0 == 0 || !null.className.equals(exported2.className);
            if (isClassDifferent) {
                getDefaultTransactionName = getDefaultTransactionNameMethod(exported2.binderClass);
            }
            if (0 != 0 && null.transactionCode == exported2.transactionCode) {
                isCodeDifferent = false;
            }
            if (isClassDifferent || isCodeDifferent) {
                String resolvedCode = resolveTransactionCode(getDefaultTransactionName, exported2.transactionCode);
                if (resolvedCode == null) {
                    str = String.valueOf(exported2.transactionCode);
                } else {
                    str = resolvedCode;
                }
                methodName = str;
            } else {
                methodName = previousMethodName;
            }
            previousMethodName = methodName;
            exported2.methodName = methodName;
        }
        if (this.mAddDebugEntries && this.mBatteryStopwatch != null) {
            resultCallStats.add(createDebugEntry("start_time_millis", this.mStartElapsedTime));
            resultCallStats.add(createDebugEntry("end_time_millis", SystemClock.elapsedRealtime()));
            resultCallStats.add(createDebugEntry("battery_time_millis", this.mBatteryStopwatch.getMillis()));
            resultCallStats.add(createDebugEntry("sampling_interval", (long) this.mPeriodicSamplingInterval));
        }
        return resultCallStats;
    }

    private ExportedCallStat createDebugEntry(String variableName, long value) {
        int uid = Process.myUid();
        ExportedCallStat callStat = new ExportedCallStat();
        callStat.className = "";
        callStat.workSourceUid = uid;
        callStat.callingUid = uid;
        callStat.recordedCallCount = 1;
        callStat.callCount = 1;
        callStat.methodName = "__DEBUG_" + variableName;
        callStat.latencyMicros = value;
        return callStat;
    }

    public ArrayMap<String, Integer> getExportedExceptionStats() {
        ArrayMap<String, Integer> arrayMap;
        synchronized (this.mLock) {
            arrayMap = new ArrayMap<>(this.mExceptionCounts);
        }
        return arrayMap;
    }

    public void dump(PrintWriter pw, AppIdToPackageMap packageMap, boolean verbose) {
        synchronized (this.mLock) {
            dumpLocked(pw, packageMap, verbose);
        }
    }

    private void dumpLocked(PrintWriter pw, AppIdToPackageMap packageMap, boolean verbose) {
        PrintWriter printWriter = pw;
        AppIdToPackageMap appIdToPackageMap = packageMap;
        long totalRecordedCallsCount = 0;
        long totalCpuTime = 0;
        printWriter.print("Start time: ");
        printWriter.println(DateFormat.format((CharSequence) "yyyy-MM-dd HH:mm:ss", this.mStartCurrentTime));
        printWriter.print("On battery time (ms): ");
        printWriter.println(this.mBatteryStopwatch != null ? this.mBatteryStopwatch.getMillis() : 0);
        printWriter.println("Sampling interval period: " + this.mPeriodicSamplingInterval);
        List<UidEntry> entries = new ArrayList<>();
        int uidEntriesSize = this.mUidEntries.size();
        long totalCallsCount = 0;
        for (int i = 0; i < uidEntriesSize; i++) {
            UidEntry e = this.mUidEntries.valueAt(i);
            entries.add(e);
            totalCpuTime += e.cpuTimeMicros;
            totalRecordedCallsCount += e.recordedCallCount;
            totalCallsCount += e.callCount;
        }
        long totalCallsCount2 = totalCallsCount;
        entries.sort(Comparator.comparingDouble($$Lambda$BinderCallsStats$iPOmTqbqUiHzgsAugINuZgf9tls.INSTANCE).reversed());
        String datasetSizeDesc = verbose ? "" : "(top 90% by cpu time) ";
        StringBuilder sb = new StringBuilder();
        printWriter.println("Per-UID raw data " + datasetSizeDesc + "(package/uid, worksource, call_desc, screen_interactive, cpu_time_micros, max_cpu_time_micros, latency_time_micros, max_latency_time_micros, exception_count, max_request_size_bytes, max_reply_size_bytes, recorded_call_count, call_count):");
        List<ExportedCallStat> exportedCallStats = getExportedCallStats();
        exportedCallStats.sort($$Lambda$BinderCallsStats$233x_Qux4c_AiqShYaWwvFplEXs.INSTANCE);
        for (ExportedCallStat e2 : exportedCallStats) {
            int uidEntriesSize2 = uidEntriesSize;
            if (e2.methodName.startsWith("__DEBUG_")) {
                uidEntriesSize = uidEntriesSize2;
            } else {
                sb.setLength(0);
                sb.append("    ");
                sb.append(appIdToPackageMap.mapUid(e2.callingUid));
                sb.append(',');
                sb.append(appIdToPackageMap.mapUid(e2.workSourceUid));
                sb.append(',');
                sb.append(e2.className);
                sb.append('#');
                sb.append(e2.methodName);
                sb.append(',');
                sb.append(e2.screenInteractive);
                sb.append(',');
                List<ExportedCallStat> exportedCallStats2 = exportedCallStats;
                sb.append(e2.cpuTimeMicros);
                sb.append(',');
                sb.append(e2.maxCpuTimeMicros);
                sb.append(',');
                sb.append(e2.latencyMicros);
                sb.append(',');
                sb.append(e2.maxLatencyMicros);
                sb.append(',');
                sb.append(this.mDetailedTracking ? e2.exceptionCount : 95);
                sb.append(',');
                sb.append(this.mDetailedTracking ? e2.maxRequestSizeBytes : 95);
                sb.append(',');
                sb.append(this.mDetailedTracking ? e2.maxReplySizeBytes : 95);
                sb.append(',');
                sb.append(e2.recordedCallCount);
                sb.append(',');
                sb.append(e2.callCount);
                printWriter.println(sb);
                uidEntriesSize = uidEntriesSize2;
                exportedCallStats = exportedCallStats2;
            }
        }
        List<ExportedCallStat> list = exportedCallStats;
        pw.println();
        printWriter.println("Per-UID Summary " + datasetSizeDesc + "(cpu_time, % of total cpu_time, recorded_call_count, call_count, package/uid):");
        List<UidEntry> summaryEntries = verbose ? entries : getHighestValues(entries, $$Lambda$BinderCallsStats$xI0E0RpviGYsokEB7ojNx8LEbUc.INSTANCE, 0.9d);
        for (UidEntry entry : summaryEntries) {
            printWriter.println(String.format("  %10d %3.0f%% %8d %8d %s", new Object[]{Long.valueOf(entry.cpuTimeMicros), Double.valueOf((((double) entry.cpuTimeMicros) * 100.0d) / ((double) totalCpuTime)), Long.valueOf(entry.recordedCallCount), Long.valueOf(entry.callCount), appIdToPackageMap.mapUid(entry.workSourceUid)}));
            datasetSizeDesc = datasetSizeDesc;
            entries = entries;
            summaryEntries = summaryEntries;
            appIdToPackageMap = packageMap;
        }
        List<UidEntry> list2 = entries;
        List<UidEntry> list3 = summaryEntries;
        pw.println();
        printWriter.println(String.format("  Summary: total_cpu_time=%d, calls_count=%d, avg_call_cpu_time=%.0f", new Object[]{Long.valueOf(totalCpuTime), Long.valueOf(totalCallsCount2), Double.valueOf(((double) totalCpuTime) / ((double) totalRecordedCallsCount))}));
        pw.println();
        printWriter.println("Exceptions thrown (exception_count, class_name):");
        List<Pair<String, Integer>> exceptionEntries = new ArrayList<>();
        this.mExceptionCounts.entrySet().iterator().forEachRemaining(new Consumer(exceptionEntries) {
            private final /* synthetic */ List f$0;

            {
                this.f$0 = r1;
            }

            public final void accept(Object obj) {
                this.f$0.add(Pair.create((String) ((Map.Entry) obj).getKey(), (Integer) ((Map.Entry) obj).getValue()));
            }
        });
        exceptionEntries.sort($$Lambda$BinderCallsStats$YP7pwoNn8TN0iTmo5Q1r2lQz0.INSTANCE);
        for (Pair<String, Integer> entry2 : exceptionEntries) {
            printWriter.println(String.format("  %6d %s", new Object[]{entry2.second, entry2.first}));
        }
        if (this.mPeriodicSamplingInterval != 1) {
            printWriter.println("");
            printWriter.println("/!\\ Displayed data is sampled. See sampling interval at the top.");
        }
    }

    static /* synthetic */ double lambda$dumpLocked$0(UidEntry value) {
        return (double) value.cpuTimeMicros;
    }

    static /* synthetic */ double lambda$dumpLocked$1(UidEntry value) {
        return (double) value.cpuTimeMicros;
    }

    /* access modifiers changed from: protected */
    public long getThreadTimeMicro() {
        return SystemClock.currentThreadTimeMicro();
    }

    /* access modifiers changed from: protected */
    public int getCallingUid() {
        return Binder.getCallingUid();
    }

    /* access modifiers changed from: protected */
    public long getElapsedRealtimeMicro() {
        return SystemClock.elapsedRealtimeNanos() / 1000;
    }

    /* access modifiers changed from: protected */
    public boolean shouldRecordDetailedData() {
        return this.mRandom.nextInt() % this.mPeriodicSamplingInterval == 0;
    }

    public void setDetailedTracking(boolean enabled) {
        synchronized (this.mLock) {
            if (enabled != this.mDetailedTracking) {
                this.mDetailedTracking = enabled;
                reset();
            }
        }
    }

    public void setTrackScreenInteractive(boolean enabled) {
        synchronized (this.mLock) {
            if (enabled != this.mTrackScreenInteractive) {
                this.mTrackScreenInteractive = enabled;
                reset();
            }
        }
    }

    public void setTrackDirectCallerUid(boolean enabled) {
        synchronized (this.mLock) {
            if (enabled != this.mTrackDirectCallingUid) {
                this.mTrackDirectCallingUid = enabled;
                reset();
            }
        }
    }

    public void setAddDebugEntries(boolean addDebugEntries) {
        this.mAddDebugEntries = addDebugEntries;
    }

    public void setMaxBinderCallStats(int maxKeys) {
        if (maxKeys <= 0) {
            Slog.w(TAG, "Ignored invalid max value (value must be positive): " + maxKeys);
            return;
        }
        synchronized (this.mLock) {
            if (maxKeys != this.mMaxBinderCallStatsCount) {
                this.mMaxBinderCallStatsCount = maxKeys;
                reset();
            }
        }
    }

    public void setSamplingInterval(int samplingInterval) {
        if (samplingInterval <= 0) {
            Slog.w(TAG, "Ignored invalid sampling interval (value must be positive): " + samplingInterval);
            return;
        }
        synchronized (this.mLock) {
            if (samplingInterval != this.mPeriodicSamplingInterval) {
                this.mPeriodicSamplingInterval = samplingInterval;
                reset();
            }
        }
    }

    public void reset() {
        synchronized (this.mLock) {
            this.mCallStatsCount = 0;
            this.mUidEntries.clear();
            this.mExceptionCounts.clear();
            this.mStartCurrentTime = System.currentTimeMillis();
            this.mStartElapsedTime = SystemClock.elapsedRealtime();
            if (this.mBatteryStopwatch != null) {
                this.mBatteryStopwatch.reset();
            }
        }
    }

    @VisibleForTesting
    public static class CallStat {
        public final Class<? extends Binder> binderClass;
        public long callCount;
        public final int callingUid;
        public long cpuTimeMicros;
        public long exceptionCount;
        public long latencyMicros;
        public long maxCpuTimeMicros;
        public long maxLatencyMicros;
        public long maxReplySizeBytes;
        public long maxRequestSizeBytes;
        public long recordedCallCount;
        public final boolean screenInteractive;
        public final int transactionCode;

        CallStat(int callingUid2, Class<? extends Binder> binderClass2, int transactionCode2, boolean screenInteractive2) {
            this.callingUid = callingUid2;
            this.binderClass = binderClass2;
            this.transactionCode = transactionCode2;
            this.screenInteractive = screenInteractive2;
        }
    }

    public static class CallStatKey {
        public Class<? extends Binder> binderClass;
        public int callingUid;
        /* access modifiers changed from: private */
        public boolean screenInteractive;
        public int transactionCode;

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            CallStatKey key = (CallStatKey) o;
            if (this.callingUid == key.callingUid && this.transactionCode == key.transactionCode && this.screenInteractive == key.screenInteractive && this.binderClass.equals(key.binderClass)) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return (((((this.binderClass.hashCode() * 31) + this.transactionCode) * 31) + this.callingUid) * 31) + (this.screenInteractive ? MetricsProto.MetricsEvent.AUTOFILL_SERVICE_DISABLED_APP : MetricsProto.MetricsEvent.ANOMALY_TYPE_UNOPTIMIZED_BT);
        }
    }

    @VisibleForTesting
    public static class UidEntry {
        public long callCount;
        public long cpuTimeMicros;
        private Map<CallStatKey, CallStat> mCallStats = new ArrayMap();
        private CallStatKey mTempKey = new CallStatKey();
        public long recordedCallCount;
        public int workSourceUid;

        UidEntry(int uid) {
            this.workSourceUid = uid;
        }

        /* access modifiers changed from: package-private */
        public CallStat get(int callingUid, Class<? extends Binder> binderClass, int transactionCode, boolean screenInteractive) {
            this.mTempKey.callingUid = callingUid;
            this.mTempKey.binderClass = binderClass;
            this.mTempKey.transactionCode = transactionCode;
            boolean unused = this.mTempKey.screenInteractive = screenInteractive;
            return this.mCallStats.get(this.mTempKey);
        }

        /* access modifiers changed from: package-private */
        public CallStat getOrCreate(int callingUid, Class<? extends Binder> binderClass, int transactionCode, boolean screenInteractive, boolean maxCallStatsReached) {
            CallStat mapCallStat = get(callingUid, binderClass, transactionCode, screenInteractive);
            if (mapCallStat != null) {
                return mapCallStat;
            }
            if (maxCallStatsReached) {
                CallStat mapCallStat2 = get(-1, BinderCallsStats.OVERFLOW_BINDER, -1, false);
                if (mapCallStat2 != null) {
                    return mapCallStat2;
                }
                callingUid = -1;
                binderClass = BinderCallsStats.OVERFLOW_BINDER;
                transactionCode = -1;
                screenInteractive = false;
            }
            CallStat mapCallStat3 = new CallStat(callingUid, binderClass, transactionCode, screenInteractive);
            CallStatKey key = new CallStatKey();
            key.callingUid = callingUid;
            key.binderClass = binderClass;
            key.transactionCode = transactionCode;
            boolean unused = key.screenInteractive = screenInteractive;
            this.mCallStats.put(key, mapCallStat3);
            return mapCallStat3;
        }

        public Collection<CallStat> getCallStatsList() {
            return this.mCallStats.values();
        }

        public String toString() {
            return "UidEntry{cpuTimeMicros=" + this.cpuTimeMicros + ", callCount=" + this.callCount + ", mCallStats=" + this.mCallStats + '}';
        }

        public boolean equals(Object o) {
            if (this == o || this.workSourceUid == ((UidEntry) o).workSourceUid) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return this.workSourceUid;
        }
    }

    @VisibleForTesting
    public SparseArray<UidEntry> getUidEntries() {
        return this.mUidEntries;
    }

    @VisibleForTesting
    public ArrayMap<String, Integer> getExceptionCounts() {
        return this.mExceptionCounts;
    }

    @VisibleForTesting
    public static <T> List<T> getHighestValues(List<T> list, ToDoubleFunction<T> toDouble, double percentile) {
        List<T> sortedList = new ArrayList<>(list);
        sortedList.sort(Comparator.comparingDouble(toDouble).reversed());
        double total = 0.0d;
        for (T item : list) {
            total += toDouble.applyAsDouble(item);
        }
        List<T> result = new ArrayList<>();
        double runningSum = 0.0d;
        for (T item2 : sortedList) {
            if (runningSum > percentile * total) {
                break;
            }
            result.add(item2);
            runningSum += toDouble.applyAsDouble(item2);
        }
        return result;
    }

    /* access modifiers changed from: private */
    public static int compareByCpuDesc(ExportedCallStat a, ExportedCallStat b) {
        return Long.compare(b.cpuTimeMicros, a.cpuTimeMicros);
    }

    /* access modifiers changed from: private */
    public static int compareByBinderClassAndCode(ExportedCallStat a, ExportedCallStat b) {
        int result = a.className.compareTo(b.className);
        if (result != 0) {
            return result;
        }
        return Integer.compare(a.transactionCode, b.transactionCode);
    }
}
