package com.android.internal.os;

import android.net.wifi.WifiEnterpriseConfig;
import android.os.StrictMode;
import android.os.SystemClock;
import android.util.IntArray;
import android.util.Slog;
import android.util.SparseArray;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.content.NativeLibraryHelper;
import com.android.internal.os.KernelCpuProcStringReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.CharBuffer;
import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class KernelCpuUidTimeReader<T> {
    protected static final boolean DEBUG = false;
    private static final long DEFAULT_MIN_TIME_BETWEEN_READ = 1000;
    private long mLastReadTimeMs = 0;
    final SparseArray<T> mLastTimes = new SparseArray<>();
    private long mMinTimeBetweenRead = 1000;
    final KernelCpuProcStringReader mReader;
    final String mTag = getClass().getSimpleName();
    final boolean mThrottle;

    public interface Callback<T> {
        void onUidCpuTime(int i, T t);
    }

    /* access modifiers changed from: package-private */
    public abstract void readAbsoluteImpl(Callback<T> callback);

    /* access modifiers changed from: package-private */
    public abstract void readDeltaImpl(Callback<T> callback);

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
        if (currTimeMs >= this.mLastReadTimeMs + this.mMinTimeBetweenRead) {
            readDeltaImpl(cb);
            this.mLastReadTimeMs = currTimeMs;
        }
    }

    public void readAbsolute(Callback<T> cb) {
        if (!this.mThrottle) {
            readAbsoluteImpl(cb);
            return;
        }
        long currTimeMs = SystemClock.elapsedRealtime();
        if (currTimeMs >= this.mLastReadTimeMs + this.mMinTimeBetweenRead) {
            readAbsoluteImpl(cb);
            this.mLastReadTimeMs = currTimeMs;
        }
    }

    public void removeUid(int uid) {
        this.mLastTimes.delete(uid);
    }

    public void removeUidsInRange(int startUid, int endUid) {
        if (endUid < startUid) {
            String str = this.mTag;
            Slog.e(str, "start UID " + startUid + " > end UID " + endUid);
            return;
        }
        this.mLastTimes.put(startUid, null);
        this.mLastTimes.put(endUid, null);
        int firstIndex = this.mLastTimes.indexOfKey(startUid);
        this.mLastTimes.removeAtRange(firstIndex, (this.mLastTimes.indexOfKey(endUid) - firstIndex) + 1);
    }

    public void setThrottle(long minTimeBetweenRead) {
        if (this.mThrottle && minTimeBetweenRead >= 0) {
            this.mMinTimeBetweenRead = minTimeBetweenRead;
        }
    }

    public static class KernelCpuUidUserSysTimeReader extends KernelCpuUidTimeReader<long[]> {
        private static final String REMOVE_UID_PROC_FILE = "/proc/uid_cputime/remove_uid_range";
        private final long[] mBuffer = new long[4];
        private final long[] mUsrSysTime = new long[2];

        public KernelCpuUidUserSysTimeReader(boolean throttle) {
            super(KernelCpuProcStringReader.getUserSysTimeReaderInstance(), throttle);
        }

        @VisibleForTesting
        public KernelCpuUidUserSysTimeReader(KernelCpuProcStringReader reader, boolean throttle) {
            super(reader, throttle);
        }

        /* access modifiers changed from: package-private */
        /* JADX WARNING: Code restructure failed: missing block: B:39:0x00e6, code lost:
            r0 = th;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:40:0x00e7, code lost:
            r5 = null;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:45:0x00f3, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:46:0x00f4, code lost:
            r5 = r0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:48:?, code lost:
            throw r5;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Removed duplicated region for block: B:45:0x00f3 A[ExcHandler: Throwable (r0v2 'th' java.lang.Throwable A[CUSTOM_DECLARE]), Splitter:B:35:0x00a2] */
        /* JADX WARNING: Removed duplicated region for block: B:51:0x00f9  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void readDeltaImpl(com.android.internal.os.KernelCpuUidTimeReader.Callback<long[]> r20) {
            /*
                r19 = this;
                r1 = r19
                r2 = r20
                com.android.internal.os.KernelCpuProcStringReader r0 = r1.mReader
                boolean r3 = r1.mThrottle
                r4 = 1
                r3 = r3 ^ r4
                com.android.internal.os.KernelCpuProcStringReader$ProcFileIterator r3 = r0.open(r3)
                r5 = 0
                if (r3 != 0) goto L_0x0017
                if (r3 == 0) goto L_0x0016
                $closeResource(r5, r3)
            L_0x0016:
                return
            L_0x0017:
                java.nio.CharBuffer r0 = r3.nextLine()     // Catch:{ Throwable -> 0x00f3, all -> 0x00f0 }
                r6 = r0
                if (r0 == 0) goto L_0x00e9
                long[] r0 = r1.mBuffer     // Catch:{ Throwable -> 0x00f3, all -> 0x00e6 }
                int r0 = com.android.internal.os.KernelCpuProcStringReader.asLongs(r6, r0)     // Catch:{ Throwable -> 0x00f3, all -> 0x00e6 }
                r7 = 3
                if (r0 >= r7) goto L_0x0042
                java.lang.String r0 = r1.mTag     // Catch:{ Throwable -> 0x00f3 }
                java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00f3 }
                r7.<init>()     // Catch:{ Throwable -> 0x00f3 }
                java.lang.String r8 = "Invalid line: "
                r7.append(r8)     // Catch:{ Throwable -> 0x00f3 }
                java.lang.String r8 = r6.toString()     // Catch:{ Throwable -> 0x00f3 }
                r7.append(r8)     // Catch:{ Throwable -> 0x00f3 }
                java.lang.String r7 = r7.toString()     // Catch:{ Throwable -> 0x00f3 }
                android.util.Slog.wtf((java.lang.String) r0, (java.lang.String) r7)     // Catch:{ Throwable -> 0x00f3 }
                goto L_0x0017
            L_0x0042:
                long[] r0 = r1.mBuffer     // Catch:{ Throwable -> 0x00f3, all -> 0x00e6 }
                r7 = 0
                r8 = r0[r7]     // Catch:{ Throwable -> 0x00f3, all -> 0x00e6 }
                int r0 = (int) r8     // Catch:{ Throwable -> 0x00f3, all -> 0x00e6 }
                android.util.SparseArray r8 = r1.mLastTimes     // Catch:{ Throwable -> 0x00f3, all -> 0x00e6 }
                java.lang.Object r8 = r8.get(r0)     // Catch:{ Throwable -> 0x00f3, all -> 0x00e6 }
                long[] r8 = (long[]) r8     // Catch:{ Throwable -> 0x00f3, all -> 0x00e6 }
                r9 = 2
                if (r8 != 0) goto L_0x005b
                long[] r10 = new long[r9]     // Catch:{ Throwable -> 0x00f3 }
                r8 = r10
                android.util.SparseArray r10 = r1.mLastTimes     // Catch:{ Throwable -> 0x00f3 }
                r10.put(r0, r8)     // Catch:{ Throwable -> 0x00f3 }
            L_0x005b:
                long[] r10 = r1.mBuffer     // Catch:{ Throwable -> 0x00f3, all -> 0x00e6 }
                r11 = r10[r4]     // Catch:{ Throwable -> 0x00f3, all -> 0x00e6 }
                r10 = r11
                long[] r12 = r1.mBuffer     // Catch:{ Throwable -> 0x00f3, all -> 0x00e6 }
                r13 = r12[r9]     // Catch:{ Throwable -> 0x00f3, all -> 0x00e6 }
                r12 = r13
                long[] r9 = r1.mUsrSysTime     // Catch:{ Throwable -> 0x00f3, all -> 0x00e6 }
                r14 = r8[r7]     // Catch:{ Throwable -> 0x00f3, all -> 0x00e6 }
                long r14 = r10 - r14
                r9[r7] = r14     // Catch:{ Throwable -> 0x00f3, all -> 0x00e6 }
                long[] r9 = r1.mUsrSysTime     // Catch:{ Throwable -> 0x00f3, all -> 0x00e6 }
                r14 = r8[r4]     // Catch:{ Throwable -> 0x00f3, all -> 0x00e6 }
                long r14 = r12 - r14
                r9[r4] = r14     // Catch:{ Throwable -> 0x00f3, all -> 0x00e6 }
                long[] r9 = r1.mUsrSysTime     // Catch:{ Throwable -> 0x00f3, all -> 0x00e6 }
                r14 = r9[r7]     // Catch:{ Throwable -> 0x00f3, all -> 0x00e6 }
                r16 = 0
                int r9 = (r14 > r16 ? 1 : (r14 == r16 ? 0 : -1))
                if (r9 < 0) goto L_0x00a2
                long[] r9 = r1.mUsrSysTime     // Catch:{ Throwable -> 0x00f3 }
                r14 = r9[r4]     // Catch:{ Throwable -> 0x00f3 }
                int r9 = (r14 > r16 ? 1 : (r14 == r16 ? 0 : -1))
                if (r9 >= 0) goto L_0x0088
                goto L_0x00a2
            L_0x0088:
                long[] r9 = r1.mUsrSysTime     // Catch:{ Throwable -> 0x00f3 }
                r14 = r9[r7]     // Catch:{ Throwable -> 0x00f3 }
                int r9 = (r14 > r16 ? 1 : (r14 == r16 ? 0 : -1))
                if (r9 > 0) goto L_0x0098
                long[] r9 = r1.mUsrSysTime     // Catch:{ Throwable -> 0x00f3 }
                r14 = r9[r4]     // Catch:{ Throwable -> 0x00f3 }
                int r9 = (r14 > r16 ? 1 : (r14 == r16 ? 0 : -1))
                if (r9 <= 0) goto L_0x009f
            L_0x0098:
                if (r2 == 0) goto L_0x009f
                long[] r9 = r1.mUsrSysTime     // Catch:{ Throwable -> 0x00f3 }
                r2.onUidCpuTime(r0, r9)     // Catch:{ Throwable -> 0x00f3 }
            L_0x009f:
                r18 = r6
                goto L_0x00de
            L_0x00a2:
                java.lang.String r9 = r1.mTag     // Catch:{ Throwable -> 0x00f3, all -> 0x00e6 }
                java.lang.StringBuilder r14 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00f3, all -> 0x00e6 }
                r14.<init>()     // Catch:{ Throwable -> 0x00f3, all -> 0x00e6 }
                java.lang.String r15 = "Negative user/sys time delta for UID="
                r14.append(r15)     // Catch:{ Throwable -> 0x00f3, all -> 0x00e6 }
                r14.append(r0)     // Catch:{ Throwable -> 0x00f3, all -> 0x00e6 }
                java.lang.String r15 = "\nPrev times: u="
                r14.append(r15)     // Catch:{ Throwable -> 0x00f3, all -> 0x00e6 }
                r18 = r6
                r5 = r8[r7]     // Catch:{ Throwable -> 0x00f3, all -> 0x00e6 }
                r14.append(r5)     // Catch:{ Throwable -> 0x00f3, all -> 0x00e6 }
                java.lang.String r5 = " s="
                r14.append(r5)     // Catch:{ Throwable -> 0x00f3, all -> 0x00e6 }
                r5 = r8[r4]     // Catch:{ Throwable -> 0x00f3, all -> 0x00e6 }
                r14.append(r5)     // Catch:{ Throwable -> 0x00f3, all -> 0x00e6 }
                java.lang.String r5 = " Curr times: u="
                r14.append(r5)     // Catch:{ Throwable -> 0x00f3, all -> 0x00e6 }
                r14.append(r10)     // Catch:{ Throwable -> 0x00f3, all -> 0x00e6 }
                java.lang.String r5 = " s="
                r14.append(r5)     // Catch:{ Throwable -> 0x00f3, all -> 0x00e6 }
                r14.append(r12)     // Catch:{ Throwable -> 0x00f3, all -> 0x00e6 }
                java.lang.String r5 = r14.toString()     // Catch:{ Throwable -> 0x00f3, all -> 0x00e6 }
                android.util.Slog.e(r9, r5)     // Catch:{ Throwable -> 0x00f3, all -> 0x00e6 }
            L_0x00de:
                r8[r7] = r10     // Catch:{ Throwable -> 0x00f3, all -> 0x00e6 }
                r8[r4] = r12     // Catch:{ Throwable -> 0x00f3, all -> 0x00e6 }
                r5 = 0
                goto L_0x0017
            L_0x00e6:
                r0 = move-exception
                r5 = 0
                goto L_0x00f7
            L_0x00e9:
                if (r3 == 0) goto L_0x00ef
                r4 = 0
                $closeResource(r4, r3)
            L_0x00ef:
                return
            L_0x00f0:
                r0 = move-exception
                r4 = r5
                goto L_0x00f7
            L_0x00f3:
                r0 = move-exception
                r5 = r0
                throw r5     // Catch:{ all -> 0x00f6 }
            L_0x00f6:
                r0 = move-exception
            L_0x00f7:
                if (r3 == 0) goto L_0x00fc
                $closeResource(r5, r3)
            L_0x00fc:
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.internal.os.KernelCpuUidTimeReader.KernelCpuUidUserSysTimeReader.readDeltaImpl(com.android.internal.os.KernelCpuUidTimeReader$Callback):void");
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

        /* access modifiers changed from: package-private */
        /* JADX WARNING: Code restructure failed: missing block: B:13:0x0061, code lost:
            r2 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:17:0x0065, code lost:
            if (r0 != null) goto L_0x0067;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:18:0x0067, code lost:
            $closeResource(r1, r0);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:19:0x006a, code lost:
            throw r2;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void readAbsoluteImpl(com.android.internal.os.KernelCpuUidTimeReader.Callback<long[]> r10) {
            /*
                r9 = this;
                com.android.internal.os.KernelCpuProcStringReader r0 = r9.mReader
                boolean r1 = r9.mThrottle
                r2 = 1
                r1 = r1 ^ r2
                com.android.internal.os.KernelCpuProcStringReader$ProcFileIterator r0 = r0.open(r1)
                r1 = 0
                if (r0 != 0) goto L_0x0013
                if (r0 == 0) goto L_0x0012
                $closeResource(r1, r0)
            L_0x0012:
                return
            L_0x0013:
                java.nio.CharBuffer r3 = r0.nextLine()     // Catch:{ Throwable -> 0x0063 }
                r4 = r3
                if (r3 == 0) goto L_0x005b
                long[] r3 = r9.mBuffer     // Catch:{ Throwable -> 0x0063 }
                int r3 = com.android.internal.os.KernelCpuProcStringReader.asLongs(r4, r3)     // Catch:{ Throwable -> 0x0063 }
                r5 = 3
                if (r3 >= r5) goto L_0x003e
                java.lang.String r3 = r9.mTag     // Catch:{ Throwable -> 0x0063 }
                java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0063 }
                r5.<init>()     // Catch:{ Throwable -> 0x0063 }
                java.lang.String r6 = "Invalid line: "
                r5.append(r6)     // Catch:{ Throwable -> 0x0063 }
                java.lang.String r6 = r4.toString()     // Catch:{ Throwable -> 0x0063 }
                r5.append(r6)     // Catch:{ Throwable -> 0x0063 }
                java.lang.String r5 = r5.toString()     // Catch:{ Throwable -> 0x0063 }
                android.util.Slog.wtf((java.lang.String) r3, (java.lang.String) r5)     // Catch:{ Throwable -> 0x0063 }
                goto L_0x0013
            L_0x003e:
                long[] r3 = r9.mUsrSysTime     // Catch:{ Throwable -> 0x0063 }
                long[] r5 = r9.mBuffer     // Catch:{ Throwable -> 0x0063 }
                r6 = r5[r2]     // Catch:{ Throwable -> 0x0063 }
                r5 = 0
                r3[r5] = r6     // Catch:{ Throwable -> 0x0063 }
                long[] r3 = r9.mUsrSysTime     // Catch:{ Throwable -> 0x0063 }
                long[] r6 = r9.mBuffer     // Catch:{ Throwable -> 0x0063 }
                r7 = 2
                r7 = r6[r7]     // Catch:{ Throwable -> 0x0063 }
                r3[r2] = r7     // Catch:{ Throwable -> 0x0063 }
                long[] r3 = r9.mBuffer     // Catch:{ Throwable -> 0x0063 }
                r5 = r3[r5]     // Catch:{ Throwable -> 0x0063 }
                int r3 = (int) r5     // Catch:{ Throwable -> 0x0063 }
                long[] r5 = r9.mUsrSysTime     // Catch:{ Throwable -> 0x0063 }
                r10.onUidCpuTime(r3, r5)     // Catch:{ Throwable -> 0x0063 }
                goto L_0x0013
            L_0x005b:
                if (r0 == 0) goto L_0x0060
                $closeResource(r1, r0)
            L_0x0060:
                return
            L_0x0061:
                r2 = move-exception
                goto L_0x0065
            L_0x0063:
                r1 = move-exception
                throw r1     // Catch:{ all -> 0x0061 }
            L_0x0065:
                if (r0 == 0) goto L_0x006a
                $closeResource(r1, r0)
            L_0x006a:
                throw r2
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.internal.os.KernelCpuUidTimeReader.KernelCpuUidUserSysTimeReader.readAbsoluteImpl(com.android.internal.os.KernelCpuUidTimeReader$Callback):void");
        }

        public void removeUid(int uid) {
            KernelCpuUidTimeReader.super.removeUid(uid);
            removeUidsFromKernelModule(uid, uid);
        }

        public void removeUidsInRange(int startUid, int endUid) {
            KernelCpuUidTimeReader.super.removeUidsInRange(startUid, endUid);
            removeUidsFromKernelModule(startUid, endUid);
        }

        private void removeUidsFromKernelModule(int startUid, int endUid) {
            FileWriter writer;
            String str = this.mTag;
            Slog.d(str, "Removing uids " + startUid + NativeLibraryHelper.CLEAR_ABI_OVERRIDE + endUid);
            int oldMask = StrictMode.allowThreadDiskWritesMask();
            try {
                writer = new FileWriter(REMOVE_UID_PROC_FILE);
                writer.write(startUid + NativeLibraryHelper.CLEAR_ABI_OVERRIDE + endUid);
                writer.flush();
                $closeResource((Throwable) null, writer);
            } catch (IOException e) {
                try {
                    String str2 = this.mTag;
                    Slog.e(str2, "failed to remove uids " + startUid + " - " + endUid + " from uid_cputime module", e);
                } catch (Throwable th) {
                    StrictMode.setThreadPolicyMask(oldMask);
                    throw th;
                }
            } catch (Throwable th2) {
                $closeResource(r2, writer);
                throw th2;
            }
            StrictMode.setThreadPolicyMask(oldMask);
        }
    }

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
            return this.mLastTimes;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:32:0x007a, code lost:
            r5 = th;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:33:0x007b, code lost:
            r6 = null;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:37:0x007f, code lost:
            r6 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:38:0x0080, code lost:
            r7 = r6;
            r6 = r5;
            r5 = r7;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public long[] readFreqs(com.android.internal.os.PowerProfile r9) {
            /*
                r8 = this;
                com.android.internal.util.Preconditions.checkNotNull(r9)
                long[] r0 = r8.mCpuFreqs
                if (r0 == 0) goto L_0x000a
                long[] r0 = r8.mCpuFreqs
                return r0
            L_0x000a:
                boolean r0 = r8.mAllUidTimesAvailable
                r1 = 0
                if (r0 != 0) goto L_0x0010
                return r1
            L_0x0010:
                int r0 = android.os.StrictMode.allowThreadDiskReadsMask()
                r2 = 1
                r3 = 0
                java.nio.file.Path r4 = r8.mProcFilePath     // Catch:{ IOException -> 0x008b }
                java.io.BufferedReader r4 = java.nio.file.Files.newBufferedReader(r4)     // Catch:{ IOException -> 0x008b }
                java.lang.String r5 = r4.readLine()     // Catch:{ Throwable -> 0x007d, all -> 0x007a }
                long[] r5 = r8.readFreqs((java.lang.String) r5)     // Catch:{ Throwable -> 0x007d, all -> 0x007a }
                if (r5 != 0) goto L_0x0030
                if (r4 == 0) goto L_0x002c
                $closeResource(r1, r4)     // Catch:{ IOException -> 0x008b }
            L_0x002c:
                android.os.StrictMode.setThreadPolicyMask(r0)
                return r1
            L_0x0030:
                if (r4 == 0) goto L_0x0035
                $closeResource(r1, r4)     // Catch:{ IOException -> 0x008b }
            L_0x0035:
                android.os.StrictMode.setThreadPolicyMask(r0)
                android.util.IntArray r1 = r8.extractClusterInfoFromProcFileFreqs()
                int r4 = r9.getNumCpuClusters()
                int r5 = r1.size()
                if (r5 != r4) goto L_0x005c
                r8.mPerClusterTimesAvailable = r2
                r2 = r3
            L_0x004a:
                if (r2 >= r4) goto L_0x005e
                int r5 = r1.get(r2)
                int r6 = r9.getNumSpeedStepsInCpuCluster(r2)
                if (r5 == r6) goto L_0x0059
                r8.mPerClusterTimesAvailable = r3
                goto L_0x005e
            L_0x0059:
                int r2 = r2 + 1
                goto L_0x004a
            L_0x005c:
                r8.mPerClusterTimesAvailable = r3
            L_0x005e:
                java.lang.String r2 = r8.mTag
                java.lang.StringBuilder r3 = new java.lang.StringBuilder
                r3.<init>()
                java.lang.String r5 = "mPerClusterTimesAvailable="
                r3.append(r5)
                boolean r5 = r8.mPerClusterTimesAvailable
                r3.append(r5)
                java.lang.String r3 = r3.toString()
                android.util.Slog.i(r2, r3)
                long[] r2 = r8.mCpuFreqs
                return r2
            L_0x007a:
                r5 = move-exception
                r6 = r1
                goto L_0x0083
            L_0x007d:
                r5 = move-exception
                throw r5     // Catch:{ all -> 0x007f }
            L_0x007f:
                r6 = move-exception
                r7 = r6
                r6 = r5
                r5 = r7
            L_0x0083:
                if (r4 == 0) goto L_0x0088
                $closeResource(r6, r4)     // Catch:{ IOException -> 0x008b }
            L_0x0088:
                throw r5     // Catch:{ IOException -> 0x008b }
            L_0x0089:
                r1 = move-exception
                goto L_0x00b1
            L_0x008b:
                r4 = move-exception
                int r5 = r8.mErrors     // Catch:{ all -> 0x0089 }
                int r5 = r5 + r2
                r8.mErrors = r5     // Catch:{ all -> 0x0089 }
                r2 = 5
                if (r5 < r2) goto L_0x0096
                r8.mAllUidTimesAvailable = r3     // Catch:{ all -> 0x0089 }
            L_0x0096:
                java.lang.String r2 = r8.mTag     // Catch:{ all -> 0x0089 }
                java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x0089 }
                r3.<init>()     // Catch:{ all -> 0x0089 }
                java.lang.String r5 = "Failed to read /proc/uid_time_in_state: "
                r3.append(r5)     // Catch:{ all -> 0x0089 }
                r3.append(r4)     // Catch:{ all -> 0x0089 }
                java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x0089 }
                android.util.Slog.e(r2, r3)     // Catch:{ all -> 0x0089 }
                android.os.StrictMode.setThreadPolicyMask(r0)
                return r1
            L_0x00b1:
                android.os.StrictMode.setThreadPolicyMask(r0)
                throw r1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.internal.os.KernelCpuUidTimeReader.KernelCpuUidFreqTimeReader.readFreqs(com.android.internal.os.PowerProfile):long[]");
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
            this.mBuffer = new long[(this.mFreqCount + 1)];
            for (int i = 0; i < this.mFreqCount; i++) {
                this.mCpuFreqs[i] = Long.parseLong(lineArray[i + 1], 10);
            }
            return this.mCpuFreqs;
        }

        /* access modifiers changed from: package-private */
        /* JADX WARNING: Code restructure failed: missing block: B:44:0x00d5, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:45:0x00d6, code lost:
            r5 = r0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:47:?, code lost:
            throw r5;
         */
        /* JADX WARNING: Removed duplicated region for block: B:44:0x00d5 A[ExcHandler: Throwable (r0v2 'th' java.lang.Throwable A[CUSTOM_DECLARE]), Splitter:B:20:0x0062] */
        /* JADX WARNING: Removed duplicated region for block: B:50:0x00db  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void readDeltaImpl(com.android.internal.os.KernelCpuUidTimeReader.Callback<long[]> r19) {
            /*
                r18 = this;
                r1 = r18
                r2 = r19
                com.android.internal.os.KernelCpuProcStringReader r0 = r1.mReader
                boolean r3 = r1.mThrottle
                r4 = 1
                r3 = r3 ^ r4
                com.android.internal.os.KernelCpuProcStringReader$ProcFileIterator r3 = r0.open(r3)
                r5 = 0
                boolean r0 = r1.checkPrecondition(r3)     // Catch:{ Throwable -> 0x00d5, all -> 0x00d2 }
                if (r0 != 0) goto L_0x001b
                if (r3 == 0) goto L_0x001a
                $closeResource(r5, r3)
            L_0x001a:
                return
            L_0x001b:
                java.nio.CharBuffer r0 = r3.nextLine()     // Catch:{ Throwable -> 0x00d5, all -> 0x00d2 }
                r6 = r0
                if (r0 == 0) goto L_0x00cb
                long[] r0 = r1.mBuffer     // Catch:{ Throwable -> 0x00d5, all -> 0x00c8 }
                int r0 = com.android.internal.os.KernelCpuProcStringReader.asLongs(r6, r0)     // Catch:{ Throwable -> 0x00d5, all -> 0x00c8 }
                long[] r7 = r1.mBuffer     // Catch:{ Throwable -> 0x00d5, all -> 0x00c8 }
                int r7 = r7.length     // Catch:{ Throwable -> 0x00d5, all -> 0x00c8 }
                if (r0 == r7) goto L_0x0048
                java.lang.String r0 = r1.mTag     // Catch:{ Throwable -> 0x00d5 }
                java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00d5 }
                r7.<init>()     // Catch:{ Throwable -> 0x00d5 }
                java.lang.String r8 = "Invalid line: "
                r7.append(r8)     // Catch:{ Throwable -> 0x00d5 }
                java.lang.String r8 = r6.toString()     // Catch:{ Throwable -> 0x00d5 }
                r7.append(r8)     // Catch:{ Throwable -> 0x00d5 }
                java.lang.String r7 = r7.toString()     // Catch:{ Throwable -> 0x00d5 }
                android.util.Slog.wtf((java.lang.String) r0, (java.lang.String) r7)     // Catch:{ Throwable -> 0x00d5 }
                goto L_0x001b
            L_0x0048:
                long[] r0 = r1.mBuffer     // Catch:{ Throwable -> 0x00d5, all -> 0x00c8 }
                r7 = 0
                r8 = r0[r7]     // Catch:{ Throwable -> 0x00d5, all -> 0x00c8 }
                int r0 = (int) r8     // Catch:{ Throwable -> 0x00d5, all -> 0x00c8 }
                android.util.SparseArray r8 = r1.mLastTimes     // Catch:{ Throwable -> 0x00d5, all -> 0x00c8 }
                java.lang.Object r8 = r8.get(r0)     // Catch:{ Throwable -> 0x00d5, all -> 0x00c8 }
                long[] r8 = (long[]) r8     // Catch:{ Throwable -> 0x00d5, all -> 0x00c8 }
                if (r8 != 0) goto L_0x0062
                int r9 = r1.mFreqCount     // Catch:{ Throwable -> 0x00d5 }
                long[] r9 = new long[r9]     // Catch:{ Throwable -> 0x00d5 }
                r8 = r9
                android.util.SparseArray r9 = r1.mLastTimes     // Catch:{ Throwable -> 0x00d5 }
                r9.put(r0, r8)     // Catch:{ Throwable -> 0x00d5 }
            L_0x0062:
                r18.copyToCurTimes()     // Catch:{ Throwable -> 0x00d5, all -> 0x00c8 }
                r9 = 0
                r10 = 1
                r11 = r10
                r10 = r9
                r9 = r7
            L_0x006a:
                int r12 = r1.mFreqCount     // Catch:{ Throwable -> 0x00d5, all -> 0x00c8 }
                if (r9 >= r12) goto L_0x00b1
                long[] r12 = r1.mDeltaTimes     // Catch:{ Throwable -> 0x00d5, all -> 0x00c8 }
                long[] r13 = r1.mCurTimes     // Catch:{ Throwable -> 0x00d5, all -> 0x00c8 }
                r14 = r13[r9]     // Catch:{ Throwable -> 0x00d5, all -> 0x00c8 }
                r16 = r8[r9]     // Catch:{ Throwable -> 0x00d5, all -> 0x00c8 }
                long r14 = r14 - r16
                r12[r9] = r14     // Catch:{ Throwable -> 0x00d5, all -> 0x00c8 }
                long[] r12 = r1.mDeltaTimes     // Catch:{ Throwable -> 0x00d5, all -> 0x00c8 }
                r13 = r12[r9]     // Catch:{ Throwable -> 0x00d5, all -> 0x00c8 }
                r15 = 0
                int r12 = (r13 > r15 ? 1 : (r13 == r15 ? 0 : -1))
                if (r12 >= 0) goto L_0x00a0
                java.lang.String r12 = r1.mTag     // Catch:{ Throwable -> 0x00d5, all -> 0x00c8 }
                java.lang.StringBuilder r13 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00d5, all -> 0x00c8 }
                r13.<init>()     // Catch:{ Throwable -> 0x00d5, all -> 0x00c8 }
                java.lang.String r14 = "Negative delta from freq time proc: "
                r13.append(r14)     // Catch:{ Throwable -> 0x00d5, all -> 0x00c8 }
                long[] r14 = r1.mDeltaTimes     // Catch:{ Throwable -> 0x00d5, all -> 0x00c8 }
                r4 = r14[r9]     // Catch:{ Throwable -> 0x00d5, all -> 0x00c8 }
                r13.append(r4)     // Catch:{ Throwable -> 0x00d5, all -> 0x00c8 }
                java.lang.String r4 = r13.toString()     // Catch:{ Throwable -> 0x00d5, all -> 0x00c8 }
                android.util.Slog.e(r12, r4)     // Catch:{ Throwable -> 0x00d5, all -> 0x00c8 }
                r4 = 0
                r11 = r4
            L_0x00a0:
                long[] r4 = r1.mDeltaTimes     // Catch:{ Throwable -> 0x00d5, all -> 0x00c8 }
                r12 = r4[r9]     // Catch:{ Throwable -> 0x00d5, all -> 0x00c8 }
                int r4 = (r12 > r15 ? 1 : (r12 == r15 ? 0 : -1))
                if (r4 <= 0) goto L_0x00aa
                r4 = 1
                goto L_0x00ab
            L_0x00aa:
                r4 = r7
            L_0x00ab:
                r10 = r10 | r4
                int r9 = r9 + 1
                r4 = 1
                r5 = 0
                goto L_0x006a
            L_0x00b1:
                if (r10 == 0) goto L_0x00c3
                if (r11 == 0) goto L_0x00c3
                long[] r4 = r1.mCurTimes     // Catch:{ Throwable -> 0x00d5, all -> 0x00c8 }
                int r5 = r1.mFreqCount     // Catch:{ Throwable -> 0x00d5, all -> 0x00c8 }
                java.lang.System.arraycopy(r4, r7, r8, r7, r5)     // Catch:{ Throwable -> 0x00d5, all -> 0x00c8 }
                if (r2 == 0) goto L_0x00c3
                long[] r4 = r1.mDeltaTimes     // Catch:{ Throwable -> 0x00d5, all -> 0x00c8 }
                r2.onUidCpuTime(r0, r4)     // Catch:{ Throwable -> 0x00d5, all -> 0x00c8 }
            L_0x00c3:
                r4 = 1
                r5 = 0
                goto L_0x001b
            L_0x00c8:
                r0 = move-exception
                r5 = 0
                goto L_0x00d9
            L_0x00cb:
                if (r3 == 0) goto L_0x00d1
                r4 = 0
                $closeResource(r4, r3)
            L_0x00d1:
                return
            L_0x00d2:
                r0 = move-exception
                r4 = r5
                goto L_0x00d9
            L_0x00d5:
                r0 = move-exception
                r5 = r0
                throw r5     // Catch:{ all -> 0x00d8 }
            L_0x00d8:
                r0 = move-exception
            L_0x00d9:
                if (r3 == 0) goto L_0x00de
                $closeResource(r5, r3)
            L_0x00de:
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.internal.os.KernelCpuUidTimeReader.KernelCpuUidFreqTimeReader.readDeltaImpl(com.android.internal.os.KernelCpuUidTimeReader$Callback):void");
        }

        /* access modifiers changed from: package-private */
        /* JADX WARNING: Code restructure failed: missing block: B:15:0x0059, code lost:
            r2 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:19:0x005d, code lost:
            if (r0 != null) goto L_0x005f;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:20:0x005f, code lost:
            $closeResource(r1, r0);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:21:0x0062, code lost:
            throw r2;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void readAbsoluteImpl(com.android.internal.os.KernelCpuUidTimeReader.Callback<long[]> r7) {
            /*
                r6 = this;
                com.android.internal.os.KernelCpuProcStringReader r0 = r6.mReader
                boolean r1 = r6.mThrottle
                r1 = r1 ^ 1
                com.android.internal.os.KernelCpuProcStringReader$ProcFileIterator r0 = r0.open(r1)
                r1 = 0
                boolean r2 = r6.checkPrecondition(r0)     // Catch:{ Throwable -> 0x005b }
                if (r2 != 0) goto L_0x0017
                if (r0 == 0) goto L_0x0016
                $closeResource(r1, r0)
            L_0x0016:
                return
            L_0x0017:
                java.nio.CharBuffer r2 = r0.nextLine()     // Catch:{ Throwable -> 0x005b }
                r3 = r2
                if (r2 == 0) goto L_0x0053
                long[] r2 = r6.mBuffer     // Catch:{ Throwable -> 0x005b }
                int r2 = com.android.internal.os.KernelCpuProcStringReader.asLongs(r3, r2)     // Catch:{ Throwable -> 0x005b }
                long[] r4 = r6.mBuffer     // Catch:{ Throwable -> 0x005b }
                int r4 = r4.length     // Catch:{ Throwable -> 0x005b }
                if (r2 == r4) goto L_0x0044
                java.lang.String r2 = r6.mTag     // Catch:{ Throwable -> 0x005b }
                java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x005b }
                r4.<init>()     // Catch:{ Throwable -> 0x005b }
                java.lang.String r5 = "Invalid line: "
                r4.append(r5)     // Catch:{ Throwable -> 0x005b }
                java.lang.String r5 = r3.toString()     // Catch:{ Throwable -> 0x005b }
                r4.append(r5)     // Catch:{ Throwable -> 0x005b }
                java.lang.String r4 = r4.toString()     // Catch:{ Throwable -> 0x005b }
                android.util.Slog.wtf((java.lang.String) r2, (java.lang.String) r4)     // Catch:{ Throwable -> 0x005b }
                goto L_0x0017
            L_0x0044:
                r6.copyToCurTimes()     // Catch:{ Throwable -> 0x005b }
                long[] r2 = r6.mBuffer     // Catch:{ Throwable -> 0x005b }
                r4 = 0
                r4 = r2[r4]     // Catch:{ Throwable -> 0x005b }
                int r2 = (int) r4     // Catch:{ Throwable -> 0x005b }
                long[] r4 = r6.mCurTimes     // Catch:{ Throwable -> 0x005b }
                r7.onUidCpuTime(r2, r4)     // Catch:{ Throwable -> 0x005b }
                goto L_0x0017
            L_0x0053:
                if (r0 == 0) goto L_0x0058
                $closeResource(r1, r0)
            L_0x0058:
                return
            L_0x0059:
                r2 = move-exception
                goto L_0x005d
            L_0x005b:
                r1 = move-exception
                throw r1     // Catch:{ all -> 0x0059 }
            L_0x005d:
                if (r0 == 0) goto L_0x0062
                $closeResource(r1, r0)
            L_0x0062:
                throw r2
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.internal.os.KernelCpuUidTimeReader.KernelCpuUidFreqTimeReader.readAbsoluteImpl(com.android.internal.os.KernelCpuUidTimeReader$Callback):void");
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
            if (this.mCpuFreqs != null) {
                return true;
            }
            if (readFreqs(line.toString()) != null) {
                return true;
            }
            return false;
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

    public static class KernelCpuUidActiveTimeReader extends KernelCpuUidTimeReader<Long> {
        private long[] mBuffer;
        private int mCores = 0;

        public KernelCpuUidActiveTimeReader(boolean throttle) {
            super(KernelCpuProcStringReader.getActiveTimeReaderInstance(), throttle);
        }

        @VisibleForTesting
        public KernelCpuUidActiveTimeReader(KernelCpuProcStringReader reader, boolean throttle) {
            super(reader, throttle);
        }

        /* access modifiers changed from: package-private */
        /* JADX WARNING: Code restructure failed: missing block: B:24:0x00a1, code lost:
            r2 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:28:0x00a5, code lost:
            if (r0 != null) goto L_0x00a7;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:29:0x00a7, code lost:
            $closeResource(r1, r0);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:30:0x00aa, code lost:
            throw r2;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void readDeltaImpl(com.android.internal.os.KernelCpuUidTimeReader.Callback<java.lang.Long> r12) {
            /*
                r11 = this;
                com.android.internal.os.KernelCpuProcStringReader r0 = r11.mReader
                boolean r1 = r11.mThrottle
                r1 = r1 ^ 1
                com.android.internal.os.KernelCpuProcStringReader$ProcFileIterator r0 = r0.open(r1)
                r1 = 0
                boolean r2 = r11.checkPrecondition(r0)     // Catch:{ Throwable -> 0x00a3 }
                if (r2 != 0) goto L_0x0017
                if (r0 == 0) goto L_0x0016
                $closeResource(r1, r0)
            L_0x0016:
                return
            L_0x0017:
                java.nio.CharBuffer r2 = r0.nextLine()     // Catch:{ Throwable -> 0x00a3 }
                r3 = r2
                if (r2 == 0) goto L_0x009b
                long[] r2 = r11.mBuffer     // Catch:{ Throwable -> 0x00a3 }
                int r2 = com.android.internal.os.KernelCpuProcStringReader.asLongs(r3, r2)     // Catch:{ Throwable -> 0x00a3 }
                long[] r4 = r11.mBuffer     // Catch:{ Throwable -> 0x00a3 }
                int r4 = r4.length     // Catch:{ Throwable -> 0x00a3 }
                if (r2 == r4) goto L_0x0044
                java.lang.String r2 = r11.mTag     // Catch:{ Throwable -> 0x00a3 }
                java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00a3 }
                r4.<init>()     // Catch:{ Throwable -> 0x00a3 }
                java.lang.String r5 = "Invalid line: "
                r4.append(r5)     // Catch:{ Throwable -> 0x00a3 }
                java.lang.String r5 = r3.toString()     // Catch:{ Throwable -> 0x00a3 }
                r4.append(r5)     // Catch:{ Throwable -> 0x00a3 }
                java.lang.String r4 = r4.toString()     // Catch:{ Throwable -> 0x00a3 }
                android.util.Slog.wtf((java.lang.String) r2, (java.lang.String) r4)     // Catch:{ Throwable -> 0x00a3 }
                goto L_0x0017
            L_0x0044:
                long[] r2 = r11.mBuffer     // Catch:{ Throwable -> 0x00a3 }
                r4 = 0
                r4 = r2[r4]     // Catch:{ Throwable -> 0x00a3 }
                int r2 = (int) r4     // Catch:{ Throwable -> 0x00a3 }
                long[] r4 = r11.mBuffer     // Catch:{ Throwable -> 0x00a3 }
                long r4 = sumActiveTime(r4)     // Catch:{ Throwable -> 0x00a3 }
                r6 = 0
                int r8 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
                if (r8 <= 0) goto L_0x0099
                android.util.SparseArray r8 = r11.mLastTimes     // Catch:{ Throwable -> 0x00a3 }
                java.lang.Long r9 = java.lang.Long.valueOf(r6)     // Catch:{ Throwable -> 0x00a3 }
                java.lang.Object r8 = r8.get(r2, r9)     // Catch:{ Throwable -> 0x00a3 }
                java.lang.Long r8 = (java.lang.Long) r8     // Catch:{ Throwable -> 0x00a3 }
                long r8 = r8.longValue()     // Catch:{ Throwable -> 0x00a3 }
                long r8 = r4 - r8
                int r10 = (r8 > r6 ? 1 : (r8 == r6 ? 0 : -1))
                if (r10 <= 0) goto L_0x007f
                android.util.SparseArray r6 = r11.mLastTimes     // Catch:{ Throwable -> 0x00a3 }
                java.lang.Long r7 = java.lang.Long.valueOf(r4)     // Catch:{ Throwable -> 0x00a3 }
                r6.put(r2, r7)     // Catch:{ Throwable -> 0x00a3 }
                if (r12 == 0) goto L_0x0099
                java.lang.Long r6 = java.lang.Long.valueOf(r8)     // Catch:{ Throwable -> 0x00a3 }
                r12.onUidCpuTime(r2, r6)     // Catch:{ Throwable -> 0x00a3 }
                goto L_0x0099
            L_0x007f:
                int r6 = (r8 > r6 ? 1 : (r8 == r6 ? 0 : -1))
                if (r6 >= 0) goto L_0x0099
                java.lang.String r6 = r11.mTag     // Catch:{ Throwable -> 0x00a3 }
                java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00a3 }
                r7.<init>()     // Catch:{ Throwable -> 0x00a3 }
                java.lang.String r10 = "Negative delta from active time proc: "
                r7.append(r10)     // Catch:{ Throwable -> 0x00a3 }
                r7.append(r8)     // Catch:{ Throwable -> 0x00a3 }
                java.lang.String r7 = r7.toString()     // Catch:{ Throwable -> 0x00a3 }
                android.util.Slog.e(r6, r7)     // Catch:{ Throwable -> 0x00a3 }
            L_0x0099:
                goto L_0x0017
            L_0x009b:
                if (r0 == 0) goto L_0x00a0
                $closeResource(r1, r0)
            L_0x00a0:
                return
            L_0x00a1:
                r2 = move-exception
                goto L_0x00a5
            L_0x00a3:
                r1 = move-exception
                throw r1     // Catch:{ all -> 0x00a1 }
            L_0x00a5:
                if (r0 == 0) goto L_0x00aa
                $closeResource(r1, r0)
            L_0x00aa:
                throw r2
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.internal.os.KernelCpuUidTimeReader.KernelCpuUidActiveTimeReader.readDeltaImpl(com.android.internal.os.KernelCpuUidTimeReader$Callback):void");
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

        /* access modifiers changed from: package-private */
        /* JADX WARNING: Code restructure failed: missing block: B:17:0x0064, code lost:
            r2 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:21:0x0068, code lost:
            if (r0 != null) goto L_0x006a;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:22:0x006a, code lost:
            $closeResource(r1, r0);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:23:0x006d, code lost:
            throw r2;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void readAbsoluteImpl(com.android.internal.os.KernelCpuUidTimeReader.Callback<java.lang.Long> r9) {
            /*
                r8 = this;
                com.android.internal.os.KernelCpuProcStringReader r0 = r8.mReader
                boolean r1 = r8.mThrottle
                r1 = r1 ^ 1
                com.android.internal.os.KernelCpuProcStringReader$ProcFileIterator r0 = r0.open(r1)
                r1 = 0
                boolean r2 = r8.checkPrecondition(r0)     // Catch:{ Throwable -> 0x0066 }
                if (r2 != 0) goto L_0x0017
                if (r0 == 0) goto L_0x0016
                $closeResource(r1, r0)
            L_0x0016:
                return
            L_0x0017:
                java.nio.CharBuffer r2 = r0.nextLine()     // Catch:{ Throwable -> 0x0066 }
                r3 = r2
                if (r2 == 0) goto L_0x005e
                long[] r2 = r8.mBuffer     // Catch:{ Throwable -> 0x0066 }
                int r2 = com.android.internal.os.KernelCpuProcStringReader.asLongs(r3, r2)     // Catch:{ Throwable -> 0x0066 }
                long[] r4 = r8.mBuffer     // Catch:{ Throwable -> 0x0066 }
                int r4 = r4.length     // Catch:{ Throwable -> 0x0066 }
                if (r2 == r4) goto L_0x0044
                java.lang.String r2 = r8.mTag     // Catch:{ Throwable -> 0x0066 }
                java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0066 }
                r4.<init>()     // Catch:{ Throwable -> 0x0066 }
                java.lang.String r5 = "Invalid line: "
                r4.append(r5)     // Catch:{ Throwable -> 0x0066 }
                java.lang.String r5 = r3.toString()     // Catch:{ Throwable -> 0x0066 }
                r4.append(r5)     // Catch:{ Throwable -> 0x0066 }
                java.lang.String r4 = r4.toString()     // Catch:{ Throwable -> 0x0066 }
                android.util.Slog.wtf((java.lang.String) r2, (java.lang.String) r4)     // Catch:{ Throwable -> 0x0066 }
                goto L_0x0017
            L_0x0044:
                long[] r2 = r8.mBuffer     // Catch:{ Throwable -> 0x0066 }
                long r4 = sumActiveTime(r2)     // Catch:{ Throwable -> 0x0066 }
                r6 = 0
                int r2 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
                if (r2 <= 0) goto L_0x005d
                long[] r2 = r8.mBuffer     // Catch:{ Throwable -> 0x0066 }
                r6 = 0
                r6 = r2[r6]     // Catch:{ Throwable -> 0x0066 }
                int r2 = (int) r6     // Catch:{ Throwable -> 0x0066 }
                java.lang.Long r6 = java.lang.Long.valueOf(r4)     // Catch:{ Throwable -> 0x0066 }
                r9.onUidCpuTime(r2, r6)     // Catch:{ Throwable -> 0x0066 }
            L_0x005d:
                goto L_0x0017
            L_0x005e:
                if (r0 == 0) goto L_0x0063
                $closeResource(r1, r0)
            L_0x0063:
                return
            L_0x0064:
                r2 = move-exception
                goto L_0x0068
            L_0x0066:
                r1 = move-exception
                throw r1     // Catch:{ all -> 0x0064 }
            L_0x0068:
                if (r0 == 0) goto L_0x006d
                $closeResource(r1, r0)
            L_0x006d:
                throw r2
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.internal.os.KernelCpuUidTimeReader.KernelCpuUidActiveTimeReader.readAbsoluteImpl(com.android.internal.os.KernelCpuUidTimeReader$Callback):void");
        }

        private static long sumActiveTime(long[] times) {
            double sum = 0.0d;
            for (int i = 1; i < times.length; i++) {
                sum += (((double) times[i]) * 10.0d) / ((double) i);
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
                Slog.wtf(str2, "Malformed uid_concurrent_active_time line: " + line);
                return false;
            }
            int cores = Integer.parseInt(str.substring(5).trim(), 10);
            if (cores <= 0) {
                String str3 = this.mTag;
                Slog.wtf(str3, "Malformed uid_concurrent_active_time line: " + line);
                return false;
            }
            this.mCores = cores;
            this.mBuffer = new long[(this.mCores + 1)];
            return true;
        }
    }

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

        /* access modifiers changed from: package-private */
        /* JADX WARNING: Code restructure failed: missing block: B:44:0x00d5, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:45:0x00d6, code lost:
            r5 = r0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:47:?, code lost:
            throw r5;
         */
        /* JADX WARNING: Removed duplicated region for block: B:44:0x00d5 A[ExcHandler: Throwable (r0v2 'th' java.lang.Throwable A[CUSTOM_DECLARE]), Splitter:B:20:0x0062] */
        /* JADX WARNING: Removed duplicated region for block: B:50:0x00db  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void readDeltaImpl(com.android.internal.os.KernelCpuUidTimeReader.Callback<long[]> r19) {
            /*
                r18 = this;
                r1 = r18
                r2 = r19
                com.android.internal.os.KernelCpuProcStringReader r0 = r1.mReader
                boolean r3 = r1.mThrottle
                r4 = 1
                r3 = r3 ^ r4
                com.android.internal.os.KernelCpuProcStringReader$ProcFileIterator r3 = r0.open(r3)
                r5 = 0
                boolean r0 = r1.checkPrecondition(r3)     // Catch:{ Throwable -> 0x00d5, all -> 0x00d2 }
                if (r0 != 0) goto L_0x001b
                if (r3 == 0) goto L_0x001a
                $closeResource(r5, r3)
            L_0x001a:
                return
            L_0x001b:
                java.nio.CharBuffer r0 = r3.nextLine()     // Catch:{ Throwable -> 0x00d5, all -> 0x00d2 }
                r6 = r0
                if (r0 == 0) goto L_0x00cb
                long[] r0 = r1.mBuffer     // Catch:{ Throwable -> 0x00d5, all -> 0x00c8 }
                int r0 = com.android.internal.os.KernelCpuProcStringReader.asLongs(r6, r0)     // Catch:{ Throwable -> 0x00d5, all -> 0x00c8 }
                long[] r7 = r1.mBuffer     // Catch:{ Throwable -> 0x00d5, all -> 0x00c8 }
                int r7 = r7.length     // Catch:{ Throwable -> 0x00d5, all -> 0x00c8 }
                if (r0 == r7) goto L_0x0048
                java.lang.String r0 = r1.mTag     // Catch:{ Throwable -> 0x00d5 }
                java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00d5 }
                r7.<init>()     // Catch:{ Throwable -> 0x00d5 }
                java.lang.String r8 = "Invalid line: "
                r7.append(r8)     // Catch:{ Throwable -> 0x00d5 }
                java.lang.String r8 = r6.toString()     // Catch:{ Throwable -> 0x00d5 }
                r7.append(r8)     // Catch:{ Throwable -> 0x00d5 }
                java.lang.String r7 = r7.toString()     // Catch:{ Throwable -> 0x00d5 }
                android.util.Slog.wtf((java.lang.String) r0, (java.lang.String) r7)     // Catch:{ Throwable -> 0x00d5 }
                goto L_0x001b
            L_0x0048:
                long[] r0 = r1.mBuffer     // Catch:{ Throwable -> 0x00d5, all -> 0x00c8 }
                r7 = 0
                r8 = r0[r7]     // Catch:{ Throwable -> 0x00d5, all -> 0x00c8 }
                int r0 = (int) r8     // Catch:{ Throwable -> 0x00d5, all -> 0x00c8 }
                android.util.SparseArray r8 = r1.mLastTimes     // Catch:{ Throwable -> 0x00d5, all -> 0x00c8 }
                java.lang.Object r8 = r8.get(r0)     // Catch:{ Throwable -> 0x00d5, all -> 0x00c8 }
                long[] r8 = (long[]) r8     // Catch:{ Throwable -> 0x00d5, all -> 0x00c8 }
                if (r8 != 0) goto L_0x0062
                int r9 = r1.mNumClusters     // Catch:{ Throwable -> 0x00d5 }
                long[] r9 = new long[r9]     // Catch:{ Throwable -> 0x00d5 }
                r8 = r9
                android.util.SparseArray r9 = r1.mLastTimes     // Catch:{ Throwable -> 0x00d5 }
                r9.put(r0, r8)     // Catch:{ Throwable -> 0x00d5 }
            L_0x0062:
                r18.sumClusterTime()     // Catch:{ Throwable -> 0x00d5, all -> 0x00c8 }
                r9 = 1
                r10 = 0
                r11 = r10
                r10 = r9
                r9 = r7
            L_0x006a:
                int r12 = r1.mNumClusters     // Catch:{ Throwable -> 0x00d5, all -> 0x00c8 }
                if (r9 >= r12) goto L_0x00b1
                long[] r12 = r1.mDeltaTime     // Catch:{ Throwable -> 0x00d5, all -> 0x00c8 }
                long[] r13 = r1.mCurTime     // Catch:{ Throwable -> 0x00d5, all -> 0x00c8 }
                r14 = r13[r9]     // Catch:{ Throwable -> 0x00d5, all -> 0x00c8 }
                r16 = r8[r9]     // Catch:{ Throwable -> 0x00d5, all -> 0x00c8 }
                long r14 = r14 - r16
                r12[r9] = r14     // Catch:{ Throwable -> 0x00d5, all -> 0x00c8 }
                long[] r12 = r1.mDeltaTime     // Catch:{ Throwable -> 0x00d5, all -> 0x00c8 }
                r13 = r12[r9]     // Catch:{ Throwable -> 0x00d5, all -> 0x00c8 }
                r15 = 0
                int r12 = (r13 > r15 ? 1 : (r13 == r15 ? 0 : -1))
                if (r12 >= 0) goto L_0x00a0
                java.lang.String r12 = r1.mTag     // Catch:{ Throwable -> 0x00d5, all -> 0x00c8 }
                java.lang.StringBuilder r13 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00d5, all -> 0x00c8 }
                r13.<init>()     // Catch:{ Throwable -> 0x00d5, all -> 0x00c8 }
                java.lang.String r14 = "Negative delta from cluster time proc: "
                r13.append(r14)     // Catch:{ Throwable -> 0x00d5, all -> 0x00c8 }
                long[] r14 = r1.mDeltaTime     // Catch:{ Throwable -> 0x00d5, all -> 0x00c8 }
                r4 = r14[r9]     // Catch:{ Throwable -> 0x00d5, all -> 0x00c8 }
                r13.append(r4)     // Catch:{ Throwable -> 0x00d5, all -> 0x00c8 }
                java.lang.String r4 = r13.toString()     // Catch:{ Throwable -> 0x00d5, all -> 0x00c8 }
                android.util.Slog.e(r12, r4)     // Catch:{ Throwable -> 0x00d5, all -> 0x00c8 }
                r4 = 0
                r10 = r4
            L_0x00a0:
                long[] r4 = r1.mDeltaTime     // Catch:{ Throwable -> 0x00d5, all -> 0x00c8 }
                r12 = r4[r9]     // Catch:{ Throwable -> 0x00d5, all -> 0x00c8 }
                int r4 = (r12 > r15 ? 1 : (r12 == r15 ? 0 : -1))
                if (r4 <= 0) goto L_0x00aa
                r4 = 1
                goto L_0x00ab
            L_0x00aa:
                r4 = r7
            L_0x00ab:
                r11 = r11 | r4
                int r9 = r9 + 1
                r4 = 1
                r5 = 0
                goto L_0x006a
            L_0x00b1:
                if (r11 == 0) goto L_0x00c3
                if (r10 == 0) goto L_0x00c3
                long[] r4 = r1.mCurTime     // Catch:{ Throwable -> 0x00d5, all -> 0x00c8 }
                int r5 = r1.mNumClusters     // Catch:{ Throwable -> 0x00d5, all -> 0x00c8 }
                java.lang.System.arraycopy(r4, r7, r8, r7, r5)     // Catch:{ Throwable -> 0x00d5, all -> 0x00c8 }
                if (r2 == 0) goto L_0x00c3
                long[] r4 = r1.mDeltaTime     // Catch:{ Throwable -> 0x00d5, all -> 0x00c8 }
                r2.onUidCpuTime(r0, r4)     // Catch:{ Throwable -> 0x00d5, all -> 0x00c8 }
            L_0x00c3:
                r4 = 1
                r5 = 0
                goto L_0x001b
            L_0x00c8:
                r0 = move-exception
                r5 = 0
                goto L_0x00d9
            L_0x00cb:
                if (r3 == 0) goto L_0x00d1
                r4 = 0
                $closeResource(r4, r3)
            L_0x00d1:
                return
            L_0x00d2:
                r0 = move-exception
                r4 = r5
                goto L_0x00d9
            L_0x00d5:
                r0 = move-exception
                r5 = r0
                throw r5     // Catch:{ all -> 0x00d8 }
            L_0x00d8:
                r0 = move-exception
            L_0x00d9:
                if (r3 == 0) goto L_0x00de
                $closeResource(r5, r3)
            L_0x00de:
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.internal.os.KernelCpuUidTimeReader.KernelCpuUidClusterTimeReader.readDeltaImpl(com.android.internal.os.KernelCpuUidTimeReader$Callback):void");
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

        /* access modifiers changed from: package-private */
        /* JADX WARNING: Code restructure failed: missing block: B:15:0x0059, code lost:
            r2 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:19:0x005d, code lost:
            if (r0 != null) goto L_0x005f;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:20:0x005f, code lost:
            $closeResource(r1, r0);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:21:0x0062, code lost:
            throw r2;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void readAbsoluteImpl(com.android.internal.os.KernelCpuUidTimeReader.Callback<long[]> r7) {
            /*
                r6 = this;
                com.android.internal.os.KernelCpuProcStringReader r0 = r6.mReader
                boolean r1 = r6.mThrottle
                r1 = r1 ^ 1
                com.android.internal.os.KernelCpuProcStringReader$ProcFileIterator r0 = r0.open(r1)
                r1 = 0
                boolean r2 = r6.checkPrecondition(r0)     // Catch:{ Throwable -> 0x005b }
                if (r2 != 0) goto L_0x0017
                if (r0 == 0) goto L_0x0016
                $closeResource(r1, r0)
            L_0x0016:
                return
            L_0x0017:
                java.nio.CharBuffer r2 = r0.nextLine()     // Catch:{ Throwable -> 0x005b }
                r3 = r2
                if (r2 == 0) goto L_0x0053
                long[] r2 = r6.mBuffer     // Catch:{ Throwable -> 0x005b }
                int r2 = com.android.internal.os.KernelCpuProcStringReader.asLongs(r3, r2)     // Catch:{ Throwable -> 0x005b }
                long[] r4 = r6.mBuffer     // Catch:{ Throwable -> 0x005b }
                int r4 = r4.length     // Catch:{ Throwable -> 0x005b }
                if (r2 == r4) goto L_0x0044
                java.lang.String r2 = r6.mTag     // Catch:{ Throwable -> 0x005b }
                java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x005b }
                r4.<init>()     // Catch:{ Throwable -> 0x005b }
                java.lang.String r5 = "Invalid line: "
                r4.append(r5)     // Catch:{ Throwable -> 0x005b }
                java.lang.String r5 = r3.toString()     // Catch:{ Throwable -> 0x005b }
                r4.append(r5)     // Catch:{ Throwable -> 0x005b }
                java.lang.String r4 = r4.toString()     // Catch:{ Throwable -> 0x005b }
                android.util.Slog.wtf((java.lang.String) r2, (java.lang.String) r4)     // Catch:{ Throwable -> 0x005b }
                goto L_0x0017
            L_0x0044:
                r6.sumClusterTime()     // Catch:{ Throwable -> 0x005b }
                long[] r2 = r6.mBuffer     // Catch:{ Throwable -> 0x005b }
                r4 = 0
                r4 = r2[r4]     // Catch:{ Throwable -> 0x005b }
                int r2 = (int) r4     // Catch:{ Throwable -> 0x005b }
                long[] r4 = r6.mCurTime     // Catch:{ Throwable -> 0x005b }
                r7.onUidCpuTime(r2, r4)     // Catch:{ Throwable -> 0x005b }
                goto L_0x0017
            L_0x0053:
                if (r0 == 0) goto L_0x0058
                $closeResource(r1, r0)
            L_0x0058:
                return
            L_0x0059:
                r2 = move-exception
                goto L_0x005d
            L_0x005b:
                r1 = move-exception
                throw r1     // Catch:{ all -> 0x0059 }
            L_0x005d:
                if (r0 == 0) goto L_0x0062
                $closeResource(r1, r0)
            L_0x0062:
                throw r2
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.internal.os.KernelCpuUidTimeReader.KernelCpuUidClusterTimeReader.readAbsoluteImpl(com.android.internal.os.KernelCpuUidTimeReader$Callback):void");
        }

        private void sumClusterTime() {
            int core = 1;
            for (int i = 0; i < this.mNumClusters; i++) {
                double sum = 0.0d;
                int j = 1;
                while (j <= this.mCoresOnClusters[i]) {
                    sum += (((double) this.mBuffer[core]) * 10.0d) / ((double) j);
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
                Slog.wtf(this.mTag, "Malformed uid_concurrent_policy_time line: " + line);
                return false;
            }
            int[] clusters = new int[(lineArray.length / 2)];
            int cores = 0;
            for (int i = 0; i < clusters.length; i++) {
                if (!lineArray[i * 2].startsWith("policy")) {
                    Slog.wtf(this.mTag, "Malformed uid_concurrent_policy_time line: " + line);
                    return false;
                }
                clusters[i] = Integer.parseInt(lineArray[(i * 2) + 1], 10);
                cores += clusters[i];
            }
            this.mNumClusters = clusters.length;
            this.mNumCores = cores;
            this.mCoresOnClusters = clusters;
            this.mBuffer = new long[(cores + 1)];
            this.mCurTime = new long[this.mNumClusters];
            this.mDeltaTime = new long[this.mNumClusters];
            return true;
        }
    }
}
