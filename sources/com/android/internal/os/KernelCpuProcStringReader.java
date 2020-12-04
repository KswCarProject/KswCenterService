package com.android.internal.os;

import android.os.SystemClock;
import java.nio.CharBuffer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class KernelCpuProcStringReader {
    private static final KernelCpuProcStringReader ACTIVE_TIME_READER = new KernelCpuProcStringReader(PROC_UID_ACTIVE_TIME);
    private static final KernelCpuProcStringReader CLUSTER_TIME_READER = new KernelCpuProcStringReader(PROC_UID_CLUSTER_TIME);
    private static final int ERROR_THRESHOLD = 5;
    private static final KernelCpuProcStringReader FREQ_TIME_READER = new KernelCpuProcStringReader(PROC_UID_FREQ_TIME);
    private static final long FRESHNESS = 500;
    private static final int MAX_BUFFER_SIZE = 1048576;
    private static final String PROC_UID_ACTIVE_TIME = "/proc/uid_concurrent_active_time";
    private static final String PROC_UID_CLUSTER_TIME = "/proc/uid_concurrent_policy_time";
    private static final String PROC_UID_FREQ_TIME = "/proc/uid_time_in_state";
    private static final String PROC_UID_USER_SYS_TIME = "/proc/uid_cputime/show_uid_stat";
    private static final String TAG = KernelCpuProcStringReader.class.getSimpleName();
    private static final KernelCpuProcStringReader USER_SYS_TIME_READER = new KernelCpuProcStringReader(PROC_UID_USER_SYS_TIME);
    /* access modifiers changed from: private */
    public char[] mBuf;
    private int mErrors = 0;
    private final Path mFile;
    private long mLastReadTime = 0;
    private final ReentrantReadWriteLock mLock = new ReentrantReadWriteLock();
    /* access modifiers changed from: private */
    public final ReentrantReadWriteLock.ReadLock mReadLock = this.mLock.readLock();
    private int mSize;
    private final ReentrantReadWriteLock.WriteLock mWriteLock = this.mLock.writeLock();

    static KernelCpuProcStringReader getFreqTimeReaderInstance() {
        return FREQ_TIME_READER;
    }

    static KernelCpuProcStringReader getActiveTimeReaderInstance() {
        return ACTIVE_TIME_READER;
    }

    static KernelCpuProcStringReader getClusterTimeReaderInstance() {
        return CLUSTER_TIME_READER;
    }

    static KernelCpuProcStringReader getUserSysTimeReaderInstance() {
        return USER_SYS_TIME_READER;
    }

    public KernelCpuProcStringReader(String file) {
        this.mFile = Paths.get(file, new String[0]);
    }

    public ProcFileIterator open() {
        return open(false);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0076, code lost:
        r9.mErrors++;
        android.util.Slog.e(TAG, "Proc file too large: " + r9.mFile);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0095, code lost:
        if (r3 == null) goto L_0x009a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:?, code lost:
        r3.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x009a, code lost:
        android.os.StrictMode.setThreadPolicyMask(r2);
        r9.mWriteLock.unlock();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x00a2, code lost:
        return null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00b5, code lost:
        r9.mSize = r0;
        r9.mLastReadTime = android.os.SystemClock.elapsedRealtime();
        r9.mReadLock.lock();
        r4 = new com.android.internal.os.KernelCpuProcStringReader.ProcFileIterator(r9, r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00c7, code lost:
        if (r3 == null) goto L_0x00cc;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:?, code lost:
        r3.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x00cc, code lost:
        android.os.StrictMode.setThreadPolicyMask(r2);
        r9.mWriteLock.unlock();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x00d4, code lost:
        return r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x00d5, code lost:
        r4 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00d6, code lost:
        r5 = r0;
        r0 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x00db, code lost:
        r5 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x00dc, code lost:
        r8 = r5;
        r5 = r0;
        r0 = r4;
        r4 = r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x00f8, code lost:
        r0 = r5;
     */
    /* JADX WARNING: Removed duplicated region for block: B:60:0x00f7 A[ExcHandler: FileNotFoundException | NoSuchFileException (e java.lang.Throwable), Splitter:B:52:0x00e9] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.android.internal.os.KernelCpuProcStringReader.ProcFileIterator open(boolean r10) {
        /*
            r9 = this;
            int r0 = r9.mErrors
            r1 = 0
            r2 = 5
            if (r0 < r2) goto L_0x0007
            return r1
        L_0x0007:
            if (r10 == 0) goto L_0x000f
            java.util.concurrent.locks.ReentrantReadWriteLock$WriteLock r0 = r9.mWriteLock
            r0.lock()
            goto L_0x0044
        L_0x000f:
            java.util.concurrent.locks.ReentrantReadWriteLock$ReadLock r0 = r9.mReadLock
            r0.lock()
            boolean r0 = r9.dataValid()
            if (r0 == 0) goto L_0x0022
            com.android.internal.os.KernelCpuProcStringReader$ProcFileIterator r0 = new com.android.internal.os.KernelCpuProcStringReader$ProcFileIterator
            int r1 = r9.mSize
            r0.<init>(r1)
            return r0
        L_0x0022:
            java.util.concurrent.locks.ReentrantReadWriteLock$ReadLock r0 = r9.mReadLock
            r0.unlock()
            java.util.concurrent.locks.ReentrantReadWriteLock$WriteLock r0 = r9.mWriteLock
            r0.lock()
            boolean r0 = r9.dataValid()
            if (r0 == 0) goto L_0x0044
            java.util.concurrent.locks.ReentrantReadWriteLock$ReadLock r0 = r9.mReadLock
            r0.lock()
            java.util.concurrent.locks.ReentrantReadWriteLock$WriteLock r0 = r9.mWriteLock
            r0.unlock()
            com.android.internal.os.KernelCpuProcStringReader$ProcFileIterator r0 = new com.android.internal.os.KernelCpuProcStringReader$ProcFileIterator
            int r1 = r9.mSize
            r0.<init>(r1)
            return r0
        L_0x0044:
            r0 = 0
            r2 = 0
            r9.mSize = r2
            int r2 = android.os.StrictMode.allowThreadDiskReadsMask()
            java.nio.file.Path r3 = r9.mFile     // Catch:{ FileNotFoundException | NoSuchFileException -> 0x011c, IOException -> 0x00fc }
            java.io.BufferedReader r3 = java.nio.file.Files.newBufferedReader(r3)     // Catch:{ FileNotFoundException | NoSuchFileException -> 0x011c, IOException -> 0x00fc }
            char[] r4 = r9.mBuf     // Catch:{ Throwable -> 0x00d9, all -> 0x00d5 }
            if (r4 != 0) goto L_0x005c
            r4 = 1024(0x400, float:1.435E-42)
            char[] r4 = new char[r4]     // Catch:{ Throwable -> 0x00d9, all -> 0x00d5 }
            r9.mBuf = r4     // Catch:{ Throwable -> 0x00d9, all -> 0x00d5 }
        L_0x005c:
            char[] r4 = r9.mBuf     // Catch:{ Throwable -> 0x00d9, all -> 0x00d5 }
            char[] r5 = r9.mBuf     // Catch:{ Throwable -> 0x00d9, all -> 0x00d5 }
            int r5 = r5.length     // Catch:{ Throwable -> 0x00d9, all -> 0x00d5 }
            int r5 = r5 - r0
            int r4 = r3.read(r4, r0, r5)     // Catch:{ Throwable -> 0x00d9, all -> 0x00d5 }
            r5 = r4
            if (r4 < 0) goto L_0x00b5
            int r0 = r0 + r5
            char[] r4 = r9.mBuf     // Catch:{ Throwable -> 0x00d9, all -> 0x00d5 }
            int r4 = r4.length     // Catch:{ Throwable -> 0x00d9, all -> 0x00d5 }
            if (r0 != r4) goto L_0x005c
            char[] r4 = r9.mBuf     // Catch:{ Throwable -> 0x00d9, all -> 0x00d5 }
            int r4 = r4.length     // Catch:{ Throwable -> 0x00d9, all -> 0x00d5 }
            r6 = 1048576(0x100000, float:1.469368E-39)
            if (r4 != r6) goto L_0x00a3
            int r4 = r9.mErrors     // Catch:{ Throwable -> 0x00d9, all -> 0x00d5 }
            int r4 = r4 + 1
            r9.mErrors = r4     // Catch:{ Throwable -> 0x00d9, all -> 0x00d5 }
            java.lang.String r4 = TAG     // Catch:{ Throwable -> 0x00d9, all -> 0x00d5 }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00d9, all -> 0x00d5 }
            r6.<init>()     // Catch:{ Throwable -> 0x00d9, all -> 0x00d5 }
            java.lang.String r7 = "Proc file too large: "
            r6.append(r7)     // Catch:{ Throwable -> 0x00d9, all -> 0x00d5 }
            java.nio.file.Path r7 = r9.mFile     // Catch:{ Throwable -> 0x00d9, all -> 0x00d5 }
            r6.append(r7)     // Catch:{ Throwable -> 0x00d9, all -> 0x00d5 }
            java.lang.String r6 = r6.toString()     // Catch:{ Throwable -> 0x00d9, all -> 0x00d5 }
            android.util.Slog.e(r4, r6)     // Catch:{ Throwable -> 0x00d9, all -> 0x00d5 }
            if (r3 == 0) goto L_0x009a
            r3.close()     // Catch:{ FileNotFoundException | NoSuchFileException -> 0x011c, IOException -> 0x00fc }
        L_0x009a:
            android.os.StrictMode.setThreadPolicyMask(r2)
            java.util.concurrent.locks.ReentrantReadWriteLock$WriteLock r4 = r9.mWriteLock
            r4.unlock()
            return r1
        L_0x00a3:
            char[] r4 = r9.mBuf     // Catch:{ Throwable -> 0x00d9, all -> 0x00d5 }
            char[] r7 = r9.mBuf     // Catch:{ Throwable -> 0x00d9, all -> 0x00d5 }
            int r7 = r7.length     // Catch:{ Throwable -> 0x00d9, all -> 0x00d5 }
            int r7 = r7 << 1
            int r6 = java.lang.Math.min(r7, r6)     // Catch:{ Throwable -> 0x00d9, all -> 0x00d5 }
            char[] r4 = java.util.Arrays.copyOf(r4, r6)     // Catch:{ Throwable -> 0x00d9, all -> 0x00d5 }
            r9.mBuf = r4     // Catch:{ Throwable -> 0x00d9, all -> 0x00d5 }
            goto L_0x005c
        L_0x00b5:
            r9.mSize = r0     // Catch:{ Throwable -> 0x00d9, all -> 0x00d5 }
            long r6 = android.os.SystemClock.elapsedRealtime()     // Catch:{ Throwable -> 0x00d9, all -> 0x00d5 }
            r9.mLastReadTime = r6     // Catch:{ Throwable -> 0x00d9, all -> 0x00d5 }
            java.util.concurrent.locks.ReentrantReadWriteLock$ReadLock r4 = r9.mReadLock     // Catch:{ Throwable -> 0x00d9, all -> 0x00d5 }
            r4.lock()     // Catch:{ Throwable -> 0x00d9, all -> 0x00d5 }
            com.android.internal.os.KernelCpuProcStringReader$ProcFileIterator r4 = new com.android.internal.os.KernelCpuProcStringReader$ProcFileIterator     // Catch:{ Throwable -> 0x00d9, all -> 0x00d5 }
            r4.<init>(r0)     // Catch:{ Throwable -> 0x00d9, all -> 0x00d5 }
            if (r3 == 0) goto L_0x00cc
            r3.close()     // Catch:{ FileNotFoundException | NoSuchFileException -> 0x011c, IOException -> 0x00fc }
        L_0x00cc:
            android.os.StrictMode.setThreadPolicyMask(r2)
            java.util.concurrent.locks.ReentrantReadWriteLock$WriteLock r1 = r9.mWriteLock
            r1.unlock()
            return r4
        L_0x00d5:
            r4 = move-exception
            r5 = r0
            r0 = r1
            goto L_0x00e0
        L_0x00d9:
            r4 = move-exception
            throw r4     // Catch:{ all -> 0x00db }
        L_0x00db:
            r5 = move-exception
            r8 = r5
            r5 = r0
            r0 = r4
            r4 = r8
        L_0x00e0:
            if (r3 == 0) goto L_0x00f0
            if (r0 == 0) goto L_0x00ed
            r3.close()     // Catch:{ Throwable -> 0x00e8 }
            goto L_0x00f0
        L_0x00e8:
            r6 = move-exception
            r0.addSuppressed(r6)     // Catch:{ FileNotFoundException | NoSuchFileException -> 0x00f7, FileNotFoundException | NoSuchFileException -> 0x00f7, IOException -> 0x00f4, all -> 0x00f1 }
            goto L_0x00f0
        L_0x00ed:
            r3.close()     // Catch:{ FileNotFoundException | NoSuchFileException -> 0x00f7, FileNotFoundException | NoSuchFileException -> 0x00f7, IOException -> 0x00f4, all -> 0x00f1 }
        L_0x00f0:
            throw r4     // Catch:{ FileNotFoundException | NoSuchFileException -> 0x00f7, FileNotFoundException | NoSuchFileException -> 0x00f7, IOException -> 0x00f4, all -> 0x00f1 }
        L_0x00f1:
            r1 = move-exception
            r0 = r5
            goto L_0x0145
        L_0x00f4:
            r3 = move-exception
            r0 = r5
            goto L_0x00fd
        L_0x00f7:
            r3 = move-exception
            r0 = r5
            goto L_0x011d
        L_0x00fa:
            r1 = move-exception
            goto L_0x0145
        L_0x00fc:
            r3 = move-exception
        L_0x00fd:
            int r4 = r9.mErrors     // Catch:{ all -> 0x00fa }
            int r4 = r4 + 1
            r9.mErrors = r4     // Catch:{ all -> 0x00fa }
            java.lang.String r4 = TAG     // Catch:{ all -> 0x00fa }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x00fa }
            r5.<init>()     // Catch:{ all -> 0x00fa }
            java.lang.String r6 = "Error reading "
            r5.append(r6)     // Catch:{ all -> 0x00fa }
            java.nio.file.Path r6 = r9.mFile     // Catch:{ all -> 0x00fa }
            r5.append(r6)     // Catch:{ all -> 0x00fa }
            java.lang.String r5 = r5.toString()     // Catch:{ all -> 0x00fa }
            android.util.Slog.e(r4, r5, r3)     // Catch:{ all -> 0x00fa }
            goto L_0x013b
        L_0x011c:
            r3 = move-exception
        L_0x011d:
            int r4 = r9.mErrors     // Catch:{ all -> 0x00fa }
            int r4 = r4 + 1
            r9.mErrors = r4     // Catch:{ all -> 0x00fa }
            java.lang.String r4 = TAG     // Catch:{ all -> 0x00fa }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x00fa }
            r5.<init>()     // Catch:{ all -> 0x00fa }
            java.lang.String r6 = "File not found. It's normal if not implemented: "
            r5.append(r6)     // Catch:{ all -> 0x00fa }
            java.nio.file.Path r6 = r9.mFile     // Catch:{ all -> 0x00fa }
            r5.append(r6)     // Catch:{ all -> 0x00fa }
            java.lang.String r5 = r5.toString()     // Catch:{ all -> 0x00fa }
            android.util.Slog.w((java.lang.String) r4, (java.lang.String) r5)     // Catch:{ all -> 0x00fa }
        L_0x013b:
            android.os.StrictMode.setThreadPolicyMask(r2)
            java.util.concurrent.locks.ReentrantReadWriteLock$WriteLock r3 = r9.mWriteLock
            r3.unlock()
            return r1
        L_0x0145:
            android.os.StrictMode.setThreadPolicyMask(r2)
            java.util.concurrent.locks.ReentrantReadWriteLock$WriteLock r3 = r9.mWriteLock
            r3.unlock()
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.os.KernelCpuProcStringReader.open(boolean):com.android.internal.os.KernelCpuProcStringReader$ProcFileIterator");
    }

    private boolean dataValid() {
        return this.mSize > 0 && SystemClock.elapsedRealtime() - this.mLastReadTime < FRESHNESS;
    }

    public class ProcFileIterator implements AutoCloseable {
        private int mPos;
        private final int mSize;

        public ProcFileIterator(int size) {
            this.mSize = size;
        }

        public boolean hasNextLine() {
            return this.mPos < this.mSize;
        }

        public CharBuffer nextLine() {
            if (this.mPos >= this.mSize) {
                return null;
            }
            int i = this.mPos;
            while (i < this.mSize && KernelCpuProcStringReader.this.mBuf[i] != 10) {
                i++;
            }
            int start = this.mPos;
            this.mPos = i + 1;
            return CharBuffer.wrap(KernelCpuProcStringReader.this.mBuf, start, i - start);
        }

        public int size() {
            return this.mSize;
        }

        public void close() {
            KernelCpuProcStringReader.this.mReadLock.unlock();
        }
    }

    public static int asLongs(CharBuffer buf, long[] array) {
        if (buf == null) {
            return -1;
        }
        int initialPos = buf.position();
        int count = 0;
        long num = -1;
        while (buf.remaining() > 0 && count < array.length) {
            char c = buf.get();
            if (!isNumber(c) && c != ' ' && c != ':') {
                buf.position(initialPos);
                return -2;
            } else if (num < 0) {
                if (isNumber(c)) {
                    num = (long) (c - '0');
                }
            } else if (isNumber(c)) {
                num = ((10 * num) + ((long) c)) - 48;
                if (num < 0) {
                    buf.position(initialPos);
                    return -3;
                }
            } else {
                array[count] = num;
                num = -1;
                count++;
            }
        }
        if (num >= 0) {
            array[count] = num;
            count++;
        }
        buf.position(initialPos);
        return count;
    }

    private static boolean isNumber(char c) {
        return c >= '0' && c <= '9';
    }
}
