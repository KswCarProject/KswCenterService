package com.android.internal.os;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.util.SparseArray;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.logging.nano.MetricsProto;
import com.android.internal.os.CachedDeviceState;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ThreadLocalRandom;

public class LooperStats implements Looper.Observer {
    public static final String DEBUG_ENTRY_PREFIX = "__DEBUG_";
    private static final boolean DISABLED_SCREEN_STATE_TRACKING_VALUE = false;
    private static final int SESSION_POOL_SIZE = 50;
    private boolean mAddDebugEntries = false;
    private CachedDeviceState.TimeInStateStopwatch mBatteryStopwatch;
    private CachedDeviceState.Readonly mDeviceState;
    @GuardedBy({"mLock"})
    private final SparseArray<Entry> mEntries = new SparseArray<>(512);
    private final int mEntriesSizeCap;
    private final Entry mHashCollisionEntry = new Entry("HASH_COLLISION");
    private final Object mLock = new Object();
    private final Entry mOverflowEntry = new Entry("OVERFLOW");
    private int mSamplingInterval;
    private final ConcurrentLinkedQueue<DispatchSession> mSessionPool = new ConcurrentLinkedQueue<>();
    private long mStartCurrentTime = System.currentTimeMillis();
    private long mStartElapsedTime = SystemClock.elapsedRealtime();
    private boolean mTrackScreenInteractive = false;

    public LooperStats(int samplingInterval, int entriesSizeCap) {
        this.mSamplingInterval = samplingInterval;
        this.mEntriesSizeCap = entriesSizeCap;
    }

    public void setDeviceState(CachedDeviceState.Readonly deviceState) {
        if (this.mBatteryStopwatch != null) {
            this.mBatteryStopwatch.close();
        }
        this.mDeviceState = deviceState;
        this.mBatteryStopwatch = deviceState.createTimeOnBatteryStopwatch();
    }

    public void setAddDebugEntries(boolean addDebugEntries) {
        this.mAddDebugEntries = addDebugEntries;
    }

    public Object messageDispatchStarting() {
        if (!deviceStateAllowsCollection() || !shouldCollectDetailedData()) {
            return DispatchSession.NOT_SAMPLED;
        }
        DispatchSession session = this.mSessionPool.poll();
        DispatchSession session2 = session == null ? new DispatchSession() : session;
        session2.startTimeMicro = getElapsedRealtimeMicro();
        session2.cpuStartMicro = getThreadTimeMicro();
        session2.systemUptimeMillis = getSystemUptimeMillis();
        return session2;
    }

    public void messageDispatched(Object token, Message msg) {
        if (deviceStateAllowsCollection()) {
            DispatchSession session = (DispatchSession) token;
            Entry entry = findEntry(msg, session != DispatchSession.NOT_SAMPLED);
            if (entry != null) {
                synchronized (entry) {
                    entry.messageCount++;
                    if (session != DispatchSession.NOT_SAMPLED) {
                        entry.recordedMessageCount++;
                        long latency = getElapsedRealtimeMicro() - session.startTimeMicro;
                        long cpuUsage = getThreadTimeMicro() - session.cpuStartMicro;
                        entry.totalLatencyMicro += latency;
                        entry.maxLatencyMicro = Math.max(entry.maxLatencyMicro, latency);
                        entry.cpuUsageMicro += cpuUsage;
                        entry.maxCpuUsageMicro = Math.max(entry.maxCpuUsageMicro, cpuUsage);
                        if (msg.getWhen() > 0) {
                            long delay = Math.max(0, session.systemUptimeMillis - msg.getWhen());
                            entry.delayMillis += delay;
                            entry.maxDelayMillis = Math.max(entry.maxDelayMillis, delay);
                            entry.recordedDelayMessageCount++;
                        }
                    }
                }
            }
            recycleSession(session);
        }
    }

    public void dispatchingThrewException(Object token, Message msg, Exception exception) {
        if (deviceStateAllowsCollection()) {
            DispatchSession session = (DispatchSession) token;
            Entry entry = findEntry(msg, session != DispatchSession.NOT_SAMPLED);
            if (entry != null) {
                synchronized (entry) {
                    entry.exceptionCount++;
                }
            }
            recycleSession(session);
        }
    }

    private boolean deviceStateAllowsCollection() {
        return this.mDeviceState != null && !this.mDeviceState.isCharging();
    }

    public List<ExportedEntry> getEntries() {
        ArrayList<ExportedEntry> exportedEntries;
        synchronized (this.mLock) {
            int size = this.mEntries.size();
            exportedEntries = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                Entry entry = this.mEntries.valueAt(i);
                synchronized (entry) {
                    exportedEntries.add(new ExportedEntry(entry));
                }
            }
        }
        ArrayList<ExportedEntry> exportedEntries2 = exportedEntries;
        maybeAddSpecialEntry(exportedEntries2, this.mOverflowEntry);
        maybeAddSpecialEntry(exportedEntries2, this.mHashCollisionEntry);
        if (this.mAddDebugEntries && this.mBatteryStopwatch != null) {
            exportedEntries2.add(createDebugEntry("start_time_millis", this.mStartElapsedTime));
            exportedEntries2.add(createDebugEntry("end_time_millis", SystemClock.elapsedRealtime()));
            exportedEntries2.add(createDebugEntry("battery_time_millis", this.mBatteryStopwatch.getMillis()));
            exportedEntries2.add(createDebugEntry("sampling_interval", (long) this.mSamplingInterval));
        }
        return exportedEntries2;
    }

    private ExportedEntry createDebugEntry(String variableName, long value) {
        Entry entry = new Entry(DEBUG_ENTRY_PREFIX + variableName);
        entry.messageCount = 1;
        entry.recordedMessageCount = 1;
        entry.totalLatencyMicro = value;
        return new ExportedEntry(entry);
    }

    public long getStartTimeMillis() {
        return this.mStartCurrentTime;
    }

    public long getStartElapsedTimeMillis() {
        return this.mStartElapsedTime;
    }

    public long getBatteryTimeMillis() {
        if (this.mBatteryStopwatch != null) {
            return this.mBatteryStopwatch.getMillis();
        }
        return 0;
    }

    private void maybeAddSpecialEntry(List<ExportedEntry> exportedEntries, Entry specialEntry) {
        synchronized (specialEntry) {
            if (specialEntry.messageCount > 0 || specialEntry.exceptionCount > 0) {
                exportedEntries.add(new ExportedEntry(specialEntry));
            }
        }
    }

    public void reset() {
        synchronized (this.mLock) {
            this.mEntries.clear();
        }
        synchronized (this.mHashCollisionEntry) {
            this.mHashCollisionEntry.reset();
        }
        synchronized (this.mOverflowEntry) {
            this.mOverflowEntry.reset();
        }
        this.mStartCurrentTime = System.currentTimeMillis();
        this.mStartElapsedTime = SystemClock.elapsedRealtime();
        if (this.mBatteryStopwatch != null) {
            this.mBatteryStopwatch.reset();
        }
    }

    public void setSamplingInterval(int samplingInterval) {
        this.mSamplingInterval = samplingInterval;
    }

    public void setTrackScreenInteractive(boolean enabled) {
        this.mTrackScreenInteractive = enabled;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:20:0x003c, code lost:
        r2 = r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0041, code lost:
        if (r2.workSourceUid != r7.workSourceUid) goto L_0x0071;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0051, code lost:
        if (r2.handler.getClass() != r7.getTarget().getClass()) goto L_0x0071;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0069, code lost:
        if (r2.handler.getLooper().getThread() != r7.getTarget().getLooper().getThread()) goto L_0x0071;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x006d, code lost:
        if (r2.isInteractive == r0) goto L_0x0070;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0070, code lost:
        return r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0073, code lost:
        return r6.mHashCollisionEntry;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.android.internal.os.LooperStats.Entry findEntry(android.os.Message r7, boolean r8) {
        /*
            r6 = this;
            boolean r0 = r6.mTrackScreenInteractive
            if (r0 == 0) goto L_0x000b
            com.android.internal.os.CachedDeviceState$Readonly r0 = r6.mDeviceState
            boolean r0 = r0.isScreenInteractive()
            goto L_0x000c
        L_0x000b:
            r0 = 0
        L_0x000c:
            int r1 = com.android.internal.os.LooperStats.Entry.idFor(r7, r0)
            java.lang.Object r2 = r6.mLock
            monitor-enter(r2)
            android.util.SparseArray<com.android.internal.os.LooperStats$Entry> r3 = r6.mEntries     // Catch:{ all -> 0x0074 }
            java.lang.Object r3 = r3.get(r1)     // Catch:{ all -> 0x0074 }
            com.android.internal.os.LooperStats$Entry r3 = (com.android.internal.os.LooperStats.Entry) r3     // Catch:{ all -> 0x0074 }
            if (r3 != 0) goto L_0x003b
            if (r8 != 0) goto L_0x0022
            r4 = 0
            monitor-exit(r2)     // Catch:{ all -> 0x0074 }
            return r4
        L_0x0022:
            android.util.SparseArray<com.android.internal.os.LooperStats$Entry> r4 = r6.mEntries     // Catch:{ all -> 0x0074 }
            int r4 = r4.size()     // Catch:{ all -> 0x0074 }
            int r5 = r6.mEntriesSizeCap     // Catch:{ all -> 0x0074 }
            if (r4 < r5) goto L_0x0030
            com.android.internal.os.LooperStats$Entry r4 = r6.mOverflowEntry     // Catch:{ all -> 0x0074 }
            monitor-exit(r2)     // Catch:{ all -> 0x0074 }
            return r4
        L_0x0030:
            com.android.internal.os.LooperStats$Entry r4 = new com.android.internal.os.LooperStats$Entry     // Catch:{ all -> 0x0074 }
            r4.<init>(r7, r0)     // Catch:{ all -> 0x0074 }
            r3 = r4
            android.util.SparseArray<com.android.internal.os.LooperStats$Entry> r4 = r6.mEntries     // Catch:{ all -> 0x0074 }
            r4.put(r1, r3)     // Catch:{ all -> 0x0074 }
        L_0x003b:
            monitor-exit(r2)     // Catch:{ all -> 0x0074 }
            r2 = r3
            int r3 = r2.workSourceUid
            int r4 = r7.workSourceUid
            if (r3 != r4) goto L_0x0071
            android.os.Handler r3 = r2.handler
            java.lang.Class r3 = r3.getClass()
            android.os.Handler r4 = r7.getTarget()
            java.lang.Class r4 = r4.getClass()
            if (r3 != r4) goto L_0x0071
            android.os.Handler r3 = r2.handler
            android.os.Looper r3 = r3.getLooper()
            java.lang.Thread r3 = r3.getThread()
            android.os.Handler r4 = r7.getTarget()
            android.os.Looper r4 = r4.getLooper()
            java.lang.Thread r4 = r4.getThread()
            if (r3 != r4) goto L_0x0071
            boolean r3 = r2.isInteractive
            if (r3 == r0) goto L_0x0070
            goto L_0x0071
        L_0x0070:
            return r2
        L_0x0071:
            com.android.internal.os.LooperStats$Entry r3 = r6.mHashCollisionEntry
            return r3
        L_0x0074:
            r3 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x0074 }
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.os.LooperStats.findEntry(android.os.Message, boolean):com.android.internal.os.LooperStats$Entry");
    }

    private void recycleSession(DispatchSession session) {
        if (session != DispatchSession.NOT_SAMPLED && this.mSessionPool.size() < 50) {
            this.mSessionPool.add(session);
        }
    }

    /* access modifiers changed from: protected */
    public long getThreadTimeMicro() {
        return SystemClock.currentThreadTimeMicro();
    }

    /* access modifiers changed from: protected */
    public long getElapsedRealtimeMicro() {
        return SystemClock.elapsedRealtimeNanos() / 1000;
    }

    /* access modifiers changed from: protected */
    public long getSystemUptimeMillis() {
        return SystemClock.uptimeMillis();
    }

    /* access modifiers changed from: protected */
    public boolean shouldCollectDetailedData() {
        return ThreadLocalRandom.current().nextInt() % this.mSamplingInterval == 0;
    }

    private static class DispatchSession {
        static final DispatchSession NOT_SAMPLED = new DispatchSession();
        public long cpuStartMicro;
        public long startTimeMicro;
        public long systemUptimeMillis;

        private DispatchSession() {
        }
    }

    private static class Entry {
        public long cpuUsageMicro;
        public long delayMillis;
        public long exceptionCount;
        public final Handler handler;
        public final boolean isInteractive;
        public long maxCpuUsageMicro;
        public long maxDelayMillis;
        public long maxLatencyMicro;
        public long messageCount;
        public final String messageName;
        public long recordedDelayMessageCount;
        public long recordedMessageCount;
        public long totalLatencyMicro;
        public final int workSourceUid;

        Entry(Message msg, boolean isInteractive2) {
            this.workSourceUid = msg.workSourceUid;
            this.handler = msg.getTarget();
            this.messageName = this.handler.getMessageName(msg);
            this.isInteractive = isInteractive2;
        }

        Entry(String specialEntryName) {
            this.workSourceUid = -1;
            this.messageName = specialEntryName;
            this.handler = null;
            this.isInteractive = false;
        }

        /* access modifiers changed from: package-private */
        public void reset() {
            this.messageCount = 0;
            this.recordedMessageCount = 0;
            this.exceptionCount = 0;
            this.totalLatencyMicro = 0;
            this.maxLatencyMicro = 0;
            this.cpuUsageMicro = 0;
            this.maxCpuUsageMicro = 0;
            this.delayMillis = 0;
            this.maxDelayMillis = 0;
            this.recordedDelayMessageCount = 0;
        }

        static int idFor(Message msg, boolean isInteractive2) {
            int result = (((((((7 * 31) + msg.workSourceUid) * 31) + msg.getTarget().getLooper().getThread().hashCode()) * 31) + msg.getTarget().getClass().hashCode()) * 31) + (isInteractive2 ? MetricsProto.MetricsEvent.AUTOFILL_SERVICE_DISABLED_APP : MetricsProto.MetricsEvent.ANOMALY_TYPE_UNOPTIMIZED_BT);
            if (msg.getCallback() != null) {
                return (result * 31) + msg.getCallback().getClass().hashCode();
            }
            return (result * 31) + msg.what;
        }
    }

    public static class ExportedEntry {
        public final long cpuUsageMicros;
        public final long delayMillis;
        public final long exceptionCount;
        public final String handlerClassName;
        public final boolean isInteractive;
        public final long maxCpuUsageMicros;
        public final long maxDelayMillis;
        public final long maxLatencyMicros;
        public final long messageCount;
        public final String messageName;
        public final long recordedDelayMessageCount;
        public final long recordedMessageCount;
        public final String threadName;
        public final long totalLatencyMicros;
        public final int workSourceUid;

        ExportedEntry(Entry entry) {
            this.workSourceUid = entry.workSourceUid;
            if (entry.handler != null) {
                this.handlerClassName = entry.handler.getClass().getName();
                this.threadName = entry.handler.getLooper().getThread().getName();
            } else {
                this.handlerClassName = "";
                this.threadName = "";
            }
            this.isInteractive = entry.isInteractive;
            this.messageName = entry.messageName;
            this.messageCount = entry.messageCount;
            this.recordedMessageCount = entry.recordedMessageCount;
            this.exceptionCount = entry.exceptionCount;
            this.totalLatencyMicros = entry.totalLatencyMicro;
            this.maxLatencyMicros = entry.maxLatencyMicro;
            this.cpuUsageMicros = entry.cpuUsageMicro;
            this.maxCpuUsageMicros = entry.maxCpuUsageMicro;
            this.delayMillis = entry.delayMillis;
            this.maxDelayMillis = entry.maxDelayMillis;
            this.recordedDelayMessageCount = entry.recordedDelayMessageCount;
        }
    }
}
