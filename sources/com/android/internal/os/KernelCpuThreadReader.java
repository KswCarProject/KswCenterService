package com.android.internal.os;

import android.os.Process;
import android.util.Slog;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.Preconditions;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

public class KernelCpuThreadReader {
    private static final String CPU_STATISTICS_FILENAME = "time_in_state";
    private static final boolean DEBUG = false;
    private static final Path DEFAULT_INITIAL_TIME_IN_STATE_PATH = DEFAULT_PROC_PATH.resolve("self/time_in_state");
    private static final String DEFAULT_PROCESS_NAME = "unknown_process";
    private static final Path DEFAULT_PROC_PATH = Paths.get("/proc", new String[0]);
    private static final String DEFAULT_THREAD_NAME = "unknown_thread";
    private static final int ID_ERROR = -1;
    private static final String PROCESS_DIRECTORY_FILTER = "[0-9]*";
    private static final String PROCESS_NAME_FILENAME = "cmdline";
    private static final String TAG = "KernelCpuThreadReader";
    private static final String THREAD_NAME_FILENAME = "comm";
    private int[] mFrequenciesKhz;
    private FrequencyBucketCreator mFrequencyBucketCreator;
    private final Injector mInjector;
    private final Path mProcPath;
    private final ProcTimeInStateReader mProcTimeInStateReader;
    private Predicate<Integer> mUidPredicate;

    @VisibleForTesting
    public KernelCpuThreadReader(int numBuckets, Predicate<Integer> uidPredicate, Path procPath, Path initialTimeInStatePath, Injector injector) throws IOException {
        this.mUidPredicate = uidPredicate;
        this.mProcPath = procPath;
        this.mProcTimeInStateReader = new ProcTimeInStateReader(initialTimeInStatePath);
        this.mInjector = injector;
        setNumBuckets(numBuckets);
    }

    public static KernelCpuThreadReader create(int numBuckets, Predicate<Integer> uidPredicate) {
        try {
            return new KernelCpuThreadReader(numBuckets, uidPredicate, DEFAULT_PROC_PATH, DEFAULT_INITIAL_TIME_IN_STATE_PATH, new Injector());
        } catch (IOException e) {
            Slog.e(TAG, "Failed to initialize KernelCpuThreadReader", e);
            return null;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:25:0x005c, code lost:
        r3 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x005d, code lost:
        r4 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0061, code lost:
        r4 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0062, code lost:
        r9 = r4;
        r4 = r3;
        r3 = r9;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.util.ArrayList<com.android.internal.os.KernelCpuThreadReader.ProcessCpuUsage> getProcessCpuUsage() {
        /*
            r10 = this;
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r1 = 0
            java.nio.file.Path r2 = r10.mProcPath     // Catch:{ IOException -> 0x006b }
            java.lang.String r3 = "[0-9]*"
            java.nio.file.DirectoryStream r2 = java.nio.file.Files.newDirectoryStream(r2, r3)     // Catch:{ IOException -> 0x006b }
            java.util.Iterator r3 = r2.iterator()     // Catch:{ Throwable -> 0x005f, all -> 0x005c }
        L_0x0013:
            boolean r4 = r3.hasNext()     // Catch:{ Throwable -> 0x005f, all -> 0x005c }
            if (r4 == 0) goto L_0x0047
            java.lang.Object r4 = r3.next()     // Catch:{ Throwable -> 0x005f, all -> 0x005c }
            java.nio.file.Path r4 = (java.nio.file.Path) r4     // Catch:{ Throwable -> 0x005f, all -> 0x005c }
            int r5 = r10.getProcessId(r4)     // Catch:{ Throwable -> 0x005f, all -> 0x005c }
            com.android.internal.os.KernelCpuThreadReader$Injector r6 = r10.mInjector     // Catch:{ Throwable -> 0x005f, all -> 0x005c }
            int r6 = r6.getUidForPid(r5)     // Catch:{ Throwable -> 0x005f, all -> 0x005c }
            r7 = -1
            if (r6 == r7) goto L_0x0013
            if (r5 != r7) goto L_0x002f
            goto L_0x0013
        L_0x002f:
            java.util.function.Predicate<java.lang.Integer> r7 = r10.mUidPredicate     // Catch:{ Throwable -> 0x005f, all -> 0x005c }
            java.lang.Integer r8 = java.lang.Integer.valueOf(r6)     // Catch:{ Throwable -> 0x005f, all -> 0x005c }
            boolean r7 = r7.test(r8)     // Catch:{ Throwable -> 0x005f, all -> 0x005c }
            if (r7 != 0) goto L_0x003c
            goto L_0x0013
        L_0x003c:
            com.android.internal.os.KernelCpuThreadReader$ProcessCpuUsage r7 = r10.getProcessCpuUsage(r4, r5, r6)     // Catch:{ Throwable -> 0x005f, all -> 0x005c }
            if (r7 == 0) goto L_0x0046
            r0.add(r7)     // Catch:{ Throwable -> 0x005f, all -> 0x005c }
        L_0x0046:
            goto L_0x0013
        L_0x0047:
            if (r2 == 0) goto L_0x004c
            $closeResource(r1, r2)     // Catch:{ IOException -> 0x006b }
        L_0x004c:
            boolean r2 = r0.isEmpty()
            if (r2 == 0) goto L_0x005b
            java.lang.String r2 = "KernelCpuThreadReader"
            java.lang.String r3 = "Didn't successfully get any process CPU information for UIDs specified"
            android.util.Slog.w((java.lang.String) r2, (java.lang.String) r3)
            return r1
        L_0x005b:
            return r0
        L_0x005c:
            r3 = move-exception
            r4 = r1
            goto L_0x0065
        L_0x005f:
            r3 = move-exception
            throw r3     // Catch:{ all -> 0x0061 }
        L_0x0061:
            r4 = move-exception
            r9 = r4
            r4 = r3
            r3 = r9
        L_0x0065:
            if (r2 == 0) goto L_0x006a
            $closeResource(r4, r2)     // Catch:{ IOException -> 0x006b }
        L_0x006a:
            throw r3     // Catch:{ IOException -> 0x006b }
        L_0x006b:
            r2 = move-exception
            java.lang.String r3 = "KernelCpuThreadReader"
            java.lang.String r4 = "Failed to iterate over process paths"
            android.util.Slog.w(r3, r4, r2)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.os.KernelCpuThreadReader.getProcessCpuUsage():java.util.ArrayList");
    }

    private static /* synthetic */ void $closeResource(Throwable x0, AutoCloseable x1) {
        if (x0 != null) {
            try {
                x1.close();
            } catch (Throwable th) {
                x0.addSuppressed(th);
            }
        } else {
            x1.close();
        }
    }

    public int[] getCpuFrequenciesKhz() {
        return this.mFrequenciesKhz;
    }

    /* access modifiers changed from: package-private */
    public void setNumBuckets(int numBuckets) {
        if (numBuckets < 1) {
            Slog.w(TAG, "Number of buckets must be at least 1, but was " + numBuckets);
        } else if (this.mFrequenciesKhz == null || this.mFrequenciesKhz.length != numBuckets) {
            this.mFrequencyBucketCreator = new FrequencyBucketCreator(this.mProcTimeInStateReader.getFrequenciesKhz(), numBuckets);
            this.mFrequenciesKhz = this.mFrequencyBucketCreator.bucketFrequencies(this.mProcTimeInStateReader.getFrequenciesKhz());
        }
    }

    /* access modifiers changed from: package-private */
    public void setUidPredicate(Predicate<Integer> uidPredicate) {
        this.mUidPredicate = uidPredicate;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0043, code lost:
        r4 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0044, code lost:
        r5 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0048, code lost:
        r5 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0049, code lost:
        r7 = r5;
        r5 = r4;
        r4 = r7;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.android.internal.os.KernelCpuThreadReader.ProcessCpuUsage getProcessCpuUsage(java.nio.file.Path r9, int r10, int r11) {
        /*
            r8 = this;
            java.lang.String r0 = "task"
            java.nio.file.Path r0 = r9.resolve(r0)
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            r2 = 0
            java.nio.file.DirectoryStream r3 = java.nio.file.Files.newDirectoryStream(r0)     // Catch:{ IOException -> 0x0052 }
            java.util.Iterator r4 = r3.iterator()     // Catch:{ Throwable -> 0x0046, all -> 0x0043 }
        L_0x0015:
            boolean r5 = r4.hasNext()     // Catch:{ Throwable -> 0x0046, all -> 0x0043 }
            if (r5 == 0) goto L_0x002c
            java.lang.Object r5 = r4.next()     // Catch:{ Throwable -> 0x0046, all -> 0x0043 }
            java.nio.file.Path r5 = (java.nio.file.Path) r5     // Catch:{ Throwable -> 0x0046, all -> 0x0043 }
            com.android.internal.os.KernelCpuThreadReader$ThreadCpuUsage r6 = r8.getThreadCpuUsage(r5)     // Catch:{ Throwable -> 0x0046, all -> 0x0043 }
            if (r6 != 0) goto L_0x0028
            goto L_0x0015
        L_0x0028:
            r1.add(r6)     // Catch:{ Throwable -> 0x0046, all -> 0x0043 }
            goto L_0x0015
        L_0x002c:
            if (r3 == 0) goto L_0x0031
            $closeResource(r2, r3)     // Catch:{ IOException -> 0x0052 }
        L_0x0031:
            boolean r3 = r1.isEmpty()
            if (r3 == 0) goto L_0x0039
            return r2
        L_0x0039:
            com.android.internal.os.KernelCpuThreadReader$ProcessCpuUsage r2 = new com.android.internal.os.KernelCpuThreadReader$ProcessCpuUsage
            java.lang.String r3 = r8.getProcessName(r9)
            r2.<init>(r10, r3, r11, r1)
            return r2
        L_0x0043:
            r4 = move-exception
            r5 = r2
            goto L_0x004c
        L_0x0046:
            r4 = move-exception
            throw r4     // Catch:{ all -> 0x0048 }
        L_0x0048:
            r5 = move-exception
            r7 = r5
            r5 = r4
            r4 = r7
        L_0x004c:
            if (r3 == 0) goto L_0x0051
            $closeResource(r5, r3)     // Catch:{ IOException -> 0x0052 }
        L_0x0051:
            throw r4     // Catch:{ IOException -> 0x0052 }
        L_0x0052:
            r3 = move-exception
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.os.KernelCpuThreadReader.getProcessCpuUsage(java.nio.file.Path, int, int):com.android.internal.os.KernelCpuThreadReader$ProcessCpuUsage");
    }

    private ThreadCpuUsage getThreadCpuUsage(Path threadDirectory) {
        try {
            int threadId = Integer.parseInt(threadDirectory.getFileName().toString());
            String threadName = getThreadName(threadDirectory);
            long[] cpuUsagesLong = this.mProcTimeInStateReader.getUsageTimesMillis(threadDirectory.resolve(CPU_STATISTICS_FILENAME));
            if (cpuUsagesLong == null) {
                return null;
            }
            return new ThreadCpuUsage(threadId, threadName, this.mFrequencyBucketCreator.bucketValues(cpuUsagesLong));
        } catch (NumberFormatException e) {
            Slog.w(TAG, "Failed to parse thread ID when iterating over /proc/*/task", e);
            return null;
        }
    }

    private String getProcessName(Path processPath) {
        String processName = ProcStatsUtil.readSingleLineProcFile(processPath.resolve(PROCESS_NAME_FILENAME).toString());
        if (processName != null) {
            return processName;
        }
        return DEFAULT_PROCESS_NAME;
    }

    private String getThreadName(Path threadPath) {
        String threadName = ProcStatsUtil.readNullSeparatedFile(threadPath.resolve(THREAD_NAME_FILENAME).toString());
        if (threadName == null) {
            return DEFAULT_THREAD_NAME;
        }
        return threadName;
    }

    private int getProcessId(Path processPath) {
        String fileName = processPath.getFileName().toString();
        try {
            return Integer.parseInt(fileName);
        } catch (NumberFormatException e) {
            Slog.w(TAG, "Failed to parse " + fileName + " as process ID", e);
            return -1;
        }
    }

    @VisibleForTesting
    public static class FrequencyBucketCreator {
        private final int[] mBucketStartIndices;
        private final int mNumBuckets = this.mBucketStartIndices.length;
        private final int mNumFrequencies;

        @VisibleForTesting
        public FrequencyBucketCreator(long[] frequencies, int targetNumBuckets) {
            this.mNumFrequencies = frequencies.length;
            this.mBucketStartIndices = getBucketStartIndices(getClusterStartIndices(frequencies), targetNumBuckets, this.mNumFrequencies);
        }

        @VisibleForTesting
        public int[] bucketValues(long[] values) {
            int bucketStartIdx = 0;
            Preconditions.checkArgument(values.length == this.mNumFrequencies);
            int[] buckets = new int[this.mNumBuckets];
            while (true) {
                int bucketIdx = bucketStartIdx;
                if (bucketIdx >= this.mNumBuckets) {
                    return buckets;
                }
                int bucketStartIdx2 = getLowerBound(bucketIdx, this.mBucketStartIndices);
                int bucketEndIdx = getUpperBound(bucketIdx, this.mBucketStartIndices, values.length);
                for (int valuesIdx = bucketStartIdx2; valuesIdx < bucketEndIdx; valuesIdx++) {
                    buckets[bucketIdx] = (int) (((long) buckets[bucketIdx]) + values[valuesIdx]);
                }
                bucketStartIdx = bucketIdx + 1;
            }
        }

        @VisibleForTesting
        public int[] bucketFrequencies(long[] frequencies) {
            int i = 0;
            Preconditions.checkArgument(frequencies.length == this.mNumFrequencies);
            int[] buckets = new int[this.mNumBuckets];
            while (true) {
                int i2 = i;
                if (i2 >= buckets.length) {
                    return buckets;
                }
                buckets[i2] = (int) frequencies[this.mBucketStartIndices[i2]];
                i = i2 + 1;
            }
        }

        private static int[] getClusterStartIndices(long[] frequencies) {
            ArrayList<Integer> indices = new ArrayList<>();
            indices.add(0);
            for (int i = 0; i < frequencies.length - 1; i++) {
                if (frequencies[i] >= frequencies[i + 1]) {
                    indices.add(Integer.valueOf(i + 1));
                }
            }
            return ArrayUtils.convertToIntArray(indices);
        }

        private static int[] getBucketStartIndices(int[] clusterStartIndices, int targetNumBuckets, int numFrequencies) {
            int previousBucketsInCluster;
            int numClusters = clusterStartIndices.length;
            if (numClusters > targetNumBuckets) {
                return Arrays.copyOfRange(clusterStartIndices, 0, targetNumBuckets);
            }
            ArrayList<Integer> bucketStartIndices = new ArrayList<>();
            for (int clusterIdx = 0; clusterIdx < numClusters; clusterIdx++) {
                int clusterStartIdx = getLowerBound(clusterIdx, clusterStartIndices);
                int clusterEndIdx = getUpperBound(clusterIdx, clusterStartIndices, numFrequencies);
                if (clusterIdx != numClusters - 1) {
                    previousBucketsInCluster = targetNumBuckets / numClusters;
                } else {
                    previousBucketsInCluster = targetNumBuckets - ((numClusters - 1) * (targetNumBuckets / numClusters));
                }
                int numFrequenciesInBucket = Math.max(1, (clusterEndIdx - clusterStartIdx) / previousBucketsInCluster);
                for (int bucketIdx = 0; bucketIdx < previousBucketsInCluster; bucketIdx++) {
                    int bucketStartIdx = (bucketIdx * numFrequenciesInBucket) + clusterStartIdx;
                    if (bucketStartIdx >= clusterEndIdx) {
                        break;
                    }
                    bucketStartIndices.add(Integer.valueOf(bucketStartIdx));
                }
            }
            return ArrayUtils.convertToIntArray(bucketStartIndices);
        }

        private static int getLowerBound(int index, int[] startIndices) {
            return startIndices[index];
        }

        private static int getUpperBound(int index, int[] startIndices, int max) {
            if (index != startIndices.length - 1) {
                return startIndices[index + 1];
            }
            return max;
        }
    }

    public static class ProcessCpuUsage {
        public final int processId;
        public final String processName;
        public ArrayList<ThreadCpuUsage> threadCpuUsages;
        public final int uid;

        @VisibleForTesting
        public ProcessCpuUsage(int processId2, String processName2, int uid2, ArrayList<ThreadCpuUsage> threadCpuUsages2) {
            this.processId = processId2;
            this.processName = processName2;
            this.uid = uid2;
            this.threadCpuUsages = threadCpuUsages2;
        }
    }

    public static class ThreadCpuUsage {
        public final int threadId;
        public final String threadName;
        public int[] usageTimesMillis;

        @VisibleForTesting
        public ThreadCpuUsage(int threadId2, String threadName2, int[] usageTimesMillis2) {
            this.threadId = threadId2;
            this.threadName = threadName2;
            this.usageTimesMillis = usageTimesMillis2;
        }
    }

    @VisibleForTesting
    public static class Injector {
        public int getUidForPid(int pid) {
            return Process.getUidForPid(pid);
        }
    }
}
