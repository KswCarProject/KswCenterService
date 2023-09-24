package com.android.internal.p016os;

import android.net.wifi.WifiEnterpriseConfig;
import android.p007os.StrictMode;
import android.p007os.SystemClock;
import android.util.IntArray;
import android.util.Slog;
import android.util.SparseArray;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.content.NativeLibraryHelper;
import com.android.internal.p016os.KernelCpuProcStringReader;
import com.android.internal.util.Preconditions;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.CharBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/* renamed from: com.android.internal.os.KernelCpuUidTimeReader */
/* loaded from: classes4.dex */
public abstract class KernelCpuUidTimeReader<T> {
    protected static final boolean DEBUG = false;
    private static final long DEFAULT_MIN_TIME_BETWEEN_READ = 1000;
    final KernelCpuProcStringReader mReader;
    final boolean mThrottle;
    final String mTag = getClass().getSimpleName();
    final SparseArray<T> mLastTimes = new SparseArray<>();
    private long mMinTimeBetweenRead = 1000;
    private long mLastReadTimeMs = 0;

    /* renamed from: com.android.internal.os.KernelCpuUidTimeReader$Callback */
    /* loaded from: classes4.dex */
    public interface Callback<T> {
        void onUidCpuTime(int i, T t);
    }

    abstract void readAbsoluteImpl(Callback<T> callback);

    abstract void readDeltaImpl(Callback<T> callback);

    KernelCpuUidTimeReader(KernelCpuProcStringReader reader, boolean throttle) {
        this.mReader = reader;
        this.mThrottle = throttle;
    }

    public void readDelta(Callback<T> cb) {
        if (!this.mThrottle) {
            readDeltaImpl(cb);
            return;
        }
        long currTimeMs = SystemClock.elapsedRealtime();
        if (currTimeMs < this.mLastReadTimeMs + this.mMinTimeBetweenRead) {
            return;
        }
        readDeltaImpl(cb);
        this.mLastReadTimeMs = currTimeMs;
    }

    public void readAbsolute(Callback<T> cb) {
        if (!this.mThrottle) {
            readAbsoluteImpl(cb);
            return;
        }
        long currTimeMs = SystemClock.elapsedRealtime();
        if (currTimeMs < this.mLastReadTimeMs + this.mMinTimeBetweenRead) {
            return;
        }
        readAbsoluteImpl(cb);
        this.mLastReadTimeMs = currTimeMs;
    }

    public void removeUid(int uid) {
        this.mLastTimes.delete(uid);
    }

    public void removeUidsInRange(int startUid, int endUid) {
        if (endUid < startUid) {
            String str = this.mTag;
            Slog.m56e(str, "start UID " + startUid + " > end UID " + endUid);
            return;
        }
        this.mLastTimes.put(startUid, null);
        this.mLastTimes.put(endUid, null);
        int firstIndex = this.mLastTimes.indexOfKey(startUid);
        int lastIndex = this.mLastTimes.indexOfKey(endUid);
        this.mLastTimes.removeAtRange(firstIndex, (lastIndex - firstIndex) + 1);
    }

    public void setThrottle(long minTimeBetweenRead) {
        if (this.mThrottle && minTimeBetweenRead >= 0) {
            this.mMinTimeBetweenRead = minTimeBetweenRead;
        }
    }

    /* renamed from: com.android.internal.os.KernelCpuUidTimeReader$KernelCpuUidUserSysTimeReader */
    /* loaded from: classes4.dex */
    public static class KernelCpuUidUserSysTimeReader extends KernelCpuUidTimeReader<long[]> {
        private static final String REMOVE_UID_PROC_FILE = "/proc/uid_cputime/remove_uid_range";
        private final long[] mBuffer;
        private final long[] mUsrSysTime;

        public KernelCpuUidUserSysTimeReader(boolean throttle) {
            super(KernelCpuProcStringReader.getUserSysTimeReaderInstance(), throttle);
            this.mBuffer = new long[4];
            this.mUsrSysTime = new long[2];
        }

        @VisibleForTesting
        public KernelCpuUidUserSysTimeReader(KernelCpuProcStringReader reader, boolean throttle) {
            super(reader, throttle);
            this.mBuffer = new long[4];
            this.mUsrSysTime = new long[2];
        }

        /*  JADX ERROR: JadxRuntimeException in pass: BlockProcessor
            jadx.core.utils.exceptions.JadxRuntimeException: Found unreachable blocks
            	at jadx.core.dex.visitors.blocks.DominatorTree.sortBlocks(DominatorTree.java:35)
            	at jadx.core.dex.visitors.blocks.DominatorTree.compute(DominatorTree.java:25)
            	at jadx.core.dex.visitors.blocks.BlockProcessor.computeDominators(BlockProcessor.java:202)
            	at jadx.core.dex.visitors.blocks.BlockProcessor.processBlocksTree(BlockProcessor.java:45)
            	at jadx.core.dex.visitors.blocks.BlockProcessor.visit(BlockProcessor.java:39)
            */
        @Override // com.android.internal.p016os.KernelCpuUidTimeReader
        void readDeltaImpl(com.android.internal.p016os.KernelCpuUidTimeReader.Callback<long[]> r20) {
            /*
                Method dump skipped, instructions count: 253
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.internal.p016os.KernelCpuUidTimeReader.KernelCpuUidUserSysTimeReader.readDeltaImpl(com.android.internal.os.KernelCpuUidTimeReader$Callback):void");
        }

        private static /* synthetic */ void $closeResource(Throwable x0, AutoCloseable x1) {
            if (x0 == null) {
                x1.close();
                return;
            }
            try {
                x1.close();
            } catch (Throwable th) {
                x0.addSuppressed(th);
            }
        }

        @Override // com.android.internal.p016os.KernelCpuUidTimeReader
        void readAbsoluteImpl(Callback<long[]> cb) {
            KernelCpuProcStringReader.ProcFileIterator iter = this.mReader.open(!this.mThrottle);
            if (iter == null) {
                if (iter != null) {
                    $closeResource(null, iter);
                    return;
                }
                return;
            }
            while (true) {
                try {
                    CharBuffer buf = iter.nextLine();
                    if (buf == null) {
                        break;
                    } else if (KernelCpuProcStringReader.asLongs(buf, this.mBuffer) < 3) {
                        String str = this.mTag;
                        Slog.wtf(str, "Invalid line: " + buf.toString());
                    } else {
                        this.mUsrSysTime[0] = this.mBuffer[1];
                        this.mUsrSysTime[1] = this.mBuffer[2];
                        cb.onUidCpuTime((int) this.mBuffer[0], this.mUsrSysTime);
                    }
                } catch (Throwable th) {
                    try {
                        throw th;
                    } catch (Throwable th2) {
                        if (iter != null) {
                            $closeResource(th, iter);
                        }
                        throw th2;
                    }
                }
            }
            if (iter != null) {
                $closeResource(null, iter);
            }
        }

        @Override // com.android.internal.p016os.KernelCpuUidTimeReader
        public void removeUid(int uid) {
            super.removeUid(uid);
            removeUidsFromKernelModule(uid, uid);
        }

        @Override // com.android.internal.p016os.KernelCpuUidTimeReader
        public void removeUidsInRange(int startUid, int endUid) {
            super.removeUidsInRange(startUid, endUid);
            removeUidsFromKernelModule(startUid, endUid);
        }

        private void removeUidsFromKernelModule(int startUid, int endUid) {
            FileWriter writer;
            String str = this.mTag;
            Slog.m58d(str, "Removing uids " + startUid + NativeLibraryHelper.CLEAR_ABI_OVERRIDE + endUid);
            int oldMask = StrictMode.allowThreadDiskWritesMask();
            try {
                try {
                    writer = new FileWriter(REMOVE_UID_PROC_FILE);
                } catch (IOException e) {
                    String str2 = this.mTag;
                    Slog.m55e(str2, "failed to remove uids " + startUid + " - " + endUid + " from uid_cputime module", e);
                }
                try {
                    writer.write(startUid + NativeLibraryHelper.CLEAR_ABI_OVERRIDE + endUid);
                    writer.flush();
                    $closeResource(null, writer);
                } finally {
                }
            } finally {
                StrictMode.setThreadPolicyMask(oldMask);
            }
        }
    }

    /* renamed from: com.android.internal.os.KernelCpuUidTimeReader$KernelCpuUidFreqTimeReader */
    /* loaded from: classes4.dex */
    public static class KernelCpuUidFreqTimeReader extends KernelCpuUidTimeReader<long[]> {
        private static final int MAX_ERROR_COUNT = 5;
        private static final String UID_TIMES_PROC_FILE = "/proc/uid_time_in_state";
        private boolean mAllUidTimesAvailable;
        private long[] mBuffer;
        private long[] mCpuFreqs;
        private long[] mCurTimes;
        private long[] mDeltaTimes;
        private int mErrors;
        private int mFreqCount;
        private boolean mPerClusterTimesAvailable;
        private final Path mProcFilePath;

        public KernelCpuUidFreqTimeReader(boolean throttle) {
            this(UID_TIMES_PROC_FILE, KernelCpuProcStringReader.getFreqTimeReaderInstance(), throttle);
        }

        @VisibleForTesting
        public KernelCpuUidFreqTimeReader(String procFile, KernelCpuProcStringReader reader, boolean throttle) {
            super(reader, throttle);
            this.mFreqCount = 0;
            this.mErrors = 0;
            this.mAllUidTimesAvailable = true;
            this.mProcFilePath = Paths.get(procFile, new String[0]);
        }

        public boolean perClusterTimesAvailable() {
            return this.mPerClusterTimesAvailable;
        }

        public boolean allUidTimesAvailable() {
            return this.mAllUidTimesAvailable;
        }

        public SparseArray<long[]> getAllUidCpuFreqTimeMs() {
            return (SparseArray<T>) this.mLastTimes;
        }

        public long[] readFreqs(PowerProfile powerProfile) {
            Preconditions.checkNotNull(powerProfile);
            if (this.mCpuFreqs != null) {
                return this.mCpuFreqs;
            }
            if (!this.mAllUidTimesAvailable) {
                return null;
            }
            int oldMask = StrictMode.allowThreadDiskReadsMask();
            try {
                BufferedReader reader = Files.newBufferedReader(this.mProcFilePath);
                try {
                    if (readFreqs(reader.readLine()) == null) {
                        if (reader != null) {
                            $closeResource(null, reader);
                        }
                        return null;
                    }
                    if (reader != null) {
                        $closeResource(null, reader);
                    }
                    StrictMode.setThreadPolicyMask(oldMask);
                    IntArray numClusterFreqs = extractClusterInfoFromProcFileFreqs();
                    int numClusters = powerProfile.getNumCpuClusters();
                    if (numClusterFreqs.size() == numClusters) {
                        this.mPerClusterTimesAvailable = true;
                        int i = 0;
                        while (true) {
                            if (i >= numClusters) {
                                break;
                            } else if (numClusterFreqs.get(i) != powerProfile.getNumSpeedStepsInCpuCluster(i)) {
                                this.mPerClusterTimesAvailable = false;
                                break;
                            } else {
                                i++;
                            }
                        }
                    } else {
                        this.mPerClusterTimesAvailable = false;
                    }
                    Slog.m54i(this.mTag, "mPerClusterTimesAvailable=" + this.mPerClusterTimesAvailable);
                    return this.mCpuFreqs;
                } finally {
                }
            } catch (IOException e) {
                int i2 = this.mErrors + 1;
                this.mErrors = i2;
                if (i2 >= 5) {
                    this.mAllUidTimesAvailable = false;
                }
                Slog.m56e(this.mTag, "Failed to read /proc/uid_time_in_state: " + e);
                return null;
            } finally {
                StrictMode.setThreadPolicyMask(oldMask);
            }
        }

        private static /* synthetic */ void $closeResource(Throwable x0, AutoCloseable x1) {
            if (x0 == null) {
                x1.close();
                return;
            }
            try {
                x1.close();
            } catch (Throwable th) {
                x0.addSuppressed(th);
            }
        }

        private long[] readFreqs(String line) {
            if (line == null) {
                return null;
            }
            String[] lineArray = line.split(WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER);
            if (lineArray.length <= 1) {
                String str = this.mTag;
                Slog.wtf(str, "Malformed freq line: " + line);
                return null;
            }
            this.mFreqCount = lineArray.length - 1;
            this.mCpuFreqs = new long[this.mFreqCount];
            this.mCurTimes = new long[this.mFreqCount];
            this.mDeltaTimes = new long[this.mFreqCount];
            this.mBuffer = new long[this.mFreqCount + 1];
            for (int i = 0; i < this.mFreqCount; i++) {
                this.mCpuFreqs[i] = Long.parseLong(lineArray[i + 1], 10);
            }
            return this.mCpuFreqs;
        }

        /*  JADX ERROR: JadxRuntimeException in pass: BlockProcessor
            jadx.core.utils.exceptions.JadxRuntimeException: Found unreachable blocks
            	at jadx.core.dex.visitors.blocks.DominatorTree.sortBlocks(DominatorTree.java:35)
            	at jadx.core.dex.visitors.blocks.DominatorTree.compute(DominatorTree.java:25)
            	at jadx.core.dex.visitors.blocks.BlockProcessor.computeDominators(BlockProcessor.java:202)
            	at jadx.core.dex.visitors.blocks.BlockProcessor.processBlocksTree(BlockProcessor.java:45)
            	at jadx.core.dex.visitors.blocks.BlockProcessor.visit(BlockProcessor.java:39)
            */
        @Override // com.android.internal.p016os.KernelCpuUidTimeReader
        void readDeltaImpl(com.android.internal.p016os.KernelCpuUidTimeReader.Callback<long[]> r19) {
            /*
                Method dump skipped, instructions count: 223
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.internal.p016os.KernelCpuUidTimeReader.KernelCpuUidFreqTimeReader.readDeltaImpl(com.android.internal.os.KernelCpuUidTimeReader$Callback):void");
        }

        @Override // com.android.internal.p016os.KernelCpuUidTimeReader
        void readAbsoluteImpl(Callback<long[]> cb) {
            KernelCpuProcStringReader.ProcFileIterator iter = this.mReader.open(!this.mThrottle);
            try {
                if (!checkPrecondition(iter)) {
                    if (iter != null) {
                        $closeResource(null, iter);
                        return;
                    }
                    return;
                }
                while (true) {
                    CharBuffer buf = iter.nextLine();
                    if (buf == null) {
                        break;
                    } else if (KernelCpuProcStringReader.asLongs(buf, this.mBuffer) != this.mBuffer.length) {
                        String str = this.mTag;
                        Slog.wtf(str, "Invalid line: " + buf.toString());
                    } else {
                        copyToCurTimes();
                        cb.onUidCpuTime((int) this.mBuffer[0], this.mCurTimes);
                    }
                }
                if (iter != null) {
                    $closeResource(null, iter);
                }
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    if (iter != null) {
                        $closeResource(th, iter);
                    }
                    throw th2;
                }
            }
        }

        private void copyToCurTimes() {
            for (int i = 0; i < this.mFreqCount; i++) {
                this.mCurTimes[i] = this.mBuffer[i + 1] * 10;
            }
        }

        private boolean checkPrecondition(KernelCpuProcStringReader.ProcFileIterator iter) {
            if (iter == null || !iter.hasNextLine()) {
                return false;
            }
            CharBuffer line = iter.nextLine();
            return (this.mCpuFreqs == null && readFreqs(line.toString()) == null) ? false : true;
        }

        private IntArray extractClusterInfoFromProcFileFreqs() {
            IntArray numClusterFreqs = new IntArray();
            int freqsFound = 0;
            for (int i = 0; i < this.mFreqCount; i++) {
                freqsFound++;
                if (i + 1 == this.mFreqCount || this.mCpuFreqs[i + 1] <= this.mCpuFreqs[i]) {
                    numClusterFreqs.add(freqsFound);
                    freqsFound = 0;
                }
            }
            return numClusterFreqs;
        }
    }

    /* renamed from: com.android.internal.os.KernelCpuUidTimeReader$KernelCpuUidActiveTimeReader */
    /* loaded from: classes4.dex */
    public static class KernelCpuUidActiveTimeReader extends KernelCpuUidTimeReader<Long> {
        private long[] mBuffer;
        private int mCores;

        public KernelCpuUidActiveTimeReader(boolean throttle) {
            super(KernelCpuProcStringReader.getActiveTimeReaderInstance(), throttle);
            this.mCores = 0;
        }

        @VisibleForTesting
        public KernelCpuUidActiveTimeReader(KernelCpuProcStringReader reader, boolean throttle) {
            super(reader, throttle);
            this.mCores = 0;
        }

        @Override // com.android.internal.p016os.KernelCpuUidTimeReader
        void readDeltaImpl(Callback<Long> cb) {
            KernelCpuProcStringReader.ProcFileIterator iter = this.mReader.open(!this.mThrottle);
            try {
                if (!checkPrecondition(iter)) {
                    if (iter != null) {
                        $closeResource(null, iter);
                        return;
                    }
                    return;
                }
                while (true) {
                    CharBuffer buf = iter.nextLine();
                    if (buf == null) {
                        break;
                    } else if (KernelCpuProcStringReader.asLongs(buf, this.mBuffer) != this.mBuffer.length) {
                        String str = this.mTag;
                        Slog.wtf(str, "Invalid line: " + buf.toString());
                    } else {
                        int uid = (int) this.mBuffer[0];
                        long cpuActiveTime = sumActiveTime(this.mBuffer);
                        if (cpuActiveTime > 0) {
                            long delta = cpuActiveTime - ((Long) this.mLastTimes.get(uid, 0L)).longValue();
                            if (delta > 0) {
                                this.mLastTimes.put(uid, Long.valueOf(cpuActiveTime));
                                if (cb != null) {
                                    cb.onUidCpuTime(uid, Long.valueOf(delta));
                                }
                            } else if (delta < 0) {
                                String str2 = this.mTag;
                                Slog.m56e(str2, "Negative delta from active time proc: " + delta);
                            }
                        }
                    }
                }
                if (iter != null) {
                    $closeResource(null, iter);
                }
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    if (iter != null) {
                        $closeResource(th, iter);
                    }
                    throw th2;
                }
            }
        }

        private static /* synthetic */ void $closeResource(Throwable x0, AutoCloseable x1) {
            if (x0 == null) {
                x1.close();
                return;
            }
            try {
                x1.close();
            } catch (Throwable th) {
                x0.addSuppressed(th);
            }
        }

        @Override // com.android.internal.p016os.KernelCpuUidTimeReader
        void readAbsoluteImpl(Callback<Long> cb) {
            KernelCpuProcStringReader.ProcFileIterator iter = this.mReader.open(!this.mThrottle);
            try {
                if (!checkPrecondition(iter)) {
                    if (iter != null) {
                        $closeResource(null, iter);
                        return;
                    }
                    return;
                }
                while (true) {
                    CharBuffer buf = iter.nextLine();
                    if (buf == null) {
                        break;
                    } else if (KernelCpuProcStringReader.asLongs(buf, this.mBuffer) != this.mBuffer.length) {
                        String str = this.mTag;
                        Slog.wtf(str, "Invalid line: " + buf.toString());
                    } else {
                        long cpuActiveTime = sumActiveTime(this.mBuffer);
                        if (cpuActiveTime > 0) {
                            cb.onUidCpuTime((int) this.mBuffer[0], Long.valueOf(cpuActiveTime));
                        }
                    }
                }
                if (iter != null) {
                    $closeResource(null, iter);
                }
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    if (iter != null) {
                        $closeResource(th, iter);
                    }
                    throw th2;
                }
            }
        }

        private static long sumActiveTime(long[] times) {
            double sum = 0.0d;
            for (int i = 1; i < times.length; i++) {
                sum += (times[i] * 10.0d) / i;
            }
            return (long) sum;
        }

        private boolean checkPrecondition(KernelCpuProcStringReader.ProcFileIterator iter) {
            if (iter == null || !iter.hasNextLine()) {
                return false;
            }
            CharBuffer line = iter.nextLine();
            if (this.mCores > 0) {
                return true;
            }
            String str = line.toString();
            if (!str.startsWith("cpus:")) {
                String str2 = this.mTag;
                Slog.wtf(str2, "Malformed uid_concurrent_active_time line: " + ((Object) line));
                return false;
            }
            int cores = Integer.parseInt(str.substring(5).trim(), 10);
            if (cores <= 0) {
                String str3 = this.mTag;
                Slog.wtf(str3, "Malformed uid_concurrent_active_time line: " + ((Object) line));
                return false;
            }
            this.mCores = cores;
            this.mBuffer = new long[this.mCores + 1];
            return true;
        }
    }

    /* renamed from: com.android.internal.os.KernelCpuUidTimeReader$KernelCpuUidClusterTimeReader */
    /* loaded from: classes4.dex */
    public static class KernelCpuUidClusterTimeReader extends KernelCpuUidTimeReader<long[]> {
        private long[] mBuffer;
        private int[] mCoresOnClusters;
        private long[] mCurTime;
        private long[] mDeltaTime;
        private int mNumClusters;
        private int mNumCores;

        public KernelCpuUidClusterTimeReader(boolean throttle) {
            super(KernelCpuProcStringReader.getClusterTimeReaderInstance(), throttle);
        }

        @VisibleForTesting
        public KernelCpuUidClusterTimeReader(KernelCpuProcStringReader reader, boolean throttle) {
            super(reader, throttle);
        }

        /*  JADX ERROR: JadxRuntimeException in pass: BlockProcessor
            jadx.core.utils.exceptions.JadxRuntimeException: Found unreachable blocks
            	at jadx.core.dex.visitors.blocks.DominatorTree.sortBlocks(DominatorTree.java:35)
            	at jadx.core.dex.visitors.blocks.DominatorTree.compute(DominatorTree.java:25)
            	at jadx.core.dex.visitors.blocks.BlockProcessor.computeDominators(BlockProcessor.java:202)
            	at jadx.core.dex.visitors.blocks.BlockProcessor.processBlocksTree(BlockProcessor.java:45)
            	at jadx.core.dex.visitors.blocks.BlockProcessor.visit(BlockProcessor.java:39)
            */
        @Override // com.android.internal.p016os.KernelCpuUidTimeReader
        void readDeltaImpl(com.android.internal.p016os.KernelCpuUidTimeReader.Callback<long[]> r19) {
            /*
                Method dump skipped, instructions count: 223
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.internal.p016os.KernelCpuUidTimeReader.KernelCpuUidClusterTimeReader.readDeltaImpl(com.android.internal.os.KernelCpuUidTimeReader$Callback):void");
        }

        private static /* synthetic */ void $closeResource(Throwable x0, AutoCloseable x1) {
            if (x0 == null) {
                x1.close();
                return;
            }
            try {
                x1.close();
            } catch (Throwable th) {
                x0.addSuppressed(th);
            }
        }

        @Override // com.android.internal.p016os.KernelCpuUidTimeReader
        void readAbsoluteImpl(Callback<long[]> cb) {
            KernelCpuProcStringReader.ProcFileIterator iter = this.mReader.open(!this.mThrottle);
            try {
                if (!checkPrecondition(iter)) {
                    if (iter != null) {
                        $closeResource(null, iter);
                        return;
                    }
                    return;
                }
                while (true) {
                    CharBuffer buf = iter.nextLine();
                    if (buf == null) {
                        break;
                    } else if (KernelCpuProcStringReader.asLongs(buf, this.mBuffer) != this.mBuffer.length) {
                        String str = this.mTag;
                        Slog.wtf(str, "Invalid line: " + buf.toString());
                    } else {
                        sumClusterTime();
                        cb.onUidCpuTime((int) this.mBuffer[0], this.mCurTime);
                    }
                }
                if (iter != null) {
                    $closeResource(null, iter);
                }
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    if (iter != null) {
                        $closeResource(th, iter);
                    }
                    throw th2;
                }
            }
        }

        private void sumClusterTime() {
            int core = 1;
            for (int i = 0; i < this.mNumClusters; i++) {
                double sum = 0.0d;
                int j = 1;
                while (j <= this.mCoresOnClusters[i]) {
                    sum += (this.mBuffer[core] * 10.0d) / j;
                    j++;
                    core++;
                }
                this.mCurTime[i] = (long) sum;
            }
        }

        private boolean checkPrecondition(KernelCpuProcStringReader.ProcFileIterator iter) {
            if (iter == null || !iter.hasNextLine()) {
                return false;
            }
            CharBuffer line = iter.nextLine();
            if (this.mNumClusters > 0) {
                return true;
            }
            String[] lineArray = line.toString().split(WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER);
            if (lineArray.length % 2 != 0) {
                Slog.wtf(this.mTag, "Malformed uid_concurrent_policy_time line: " + ((Object) line));
                return false;
            }
            int[] clusters = new int[lineArray.length / 2];
            int cores = 0;
            for (int cores2 = 0; cores2 < clusters.length; cores2++) {
                if (!lineArray[cores2 * 2].startsWith("policy")) {
                    Slog.wtf(this.mTag, "Malformed uid_concurrent_policy_time line: " + ((Object) line));
                    return false;
                }
                clusters[cores2] = Integer.parseInt(lineArray[(cores2 * 2) + 1], 10);
                cores += clusters[cores2];
            }
            this.mNumClusters = clusters.length;
            this.mNumCores = cores;
            this.mCoresOnClusters = clusters;
            this.mBuffer = new long[cores + 1];
            this.mCurTime = new long[this.mNumClusters];
            this.mDeltaTime = new long[this.mNumClusters];
            return true;
        }
    }
}
