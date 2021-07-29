package android.app;

import android.annotation.UnsupportedAppUsage;
import android.app.SharedPreferencesImpl;
import android.content.SharedPreferences;
import android.os.FileUtils;
import android.os.Looper;
import android.system.ErrnoException;
import android.system.Os;
import android.system.StructStat;
import android.system.StructTimespec;
import android.util.Log;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.util.ExponentiallyBucketedHistogram;
import com.android.internal.util.XmlUtils;
import com.ibm.icu.text.PluralRules;
import dalvik.system.BlockGuard;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.CountDownLatch;
import org.xmlpull.v1.XmlPullParserException;

final class SharedPreferencesImpl implements SharedPreferences {
    private static final Object CONTENT = new Object();
    private static final boolean DEBUG = false;
    private static final long MAX_FSYNC_DURATION_MILLIS = 256;
    private static final String TAG = "SharedPreferencesImpl";
    private final File mBackupFile;
    /* access modifiers changed from: private */
    @GuardedBy({"this"})
    public long mCurrentMemoryStateGeneration;
    @GuardedBy({"mWritingToDiskLock"})
    private long mDiskStateGeneration;
    /* access modifiers changed from: private */
    @GuardedBy({"mLock"})
    public int mDiskWritesInFlight = 0;
    @UnsupportedAppUsage
    private final File mFile;
    /* access modifiers changed from: private */
    @GuardedBy({"mLock"})
    public final WeakHashMap<SharedPreferences.OnSharedPreferenceChangeListener, Object> mListeners = new WeakHashMap<>();
    @GuardedBy({"mLock"})
    private boolean mLoaded = false;
    /* access modifiers changed from: private */
    public final Object mLock = new Object();
    /* access modifiers changed from: private */
    @GuardedBy({"mLock"})
    public Map<String, Object> mMap;
    private final int mMode;
    private int mNumSync = 0;
    @GuardedBy({"mLock"})
    private long mStatSize;
    @GuardedBy({"mLock"})
    private StructTimespec mStatTimestamp;
    @GuardedBy({"mWritingToDiskLock"})
    private final ExponentiallyBucketedHistogram mSyncTimes = new ExponentiallyBucketedHistogram(16);
    @GuardedBy({"mLock"})
    private Throwable mThrowable;
    /* access modifiers changed from: private */
    public final Object mWritingToDiskLock = new Object();

    static /* synthetic */ int access$308(SharedPreferencesImpl x0) {
        int i = x0.mDiskWritesInFlight;
        x0.mDiskWritesInFlight = i + 1;
        return i;
    }

    static /* synthetic */ int access$310(SharedPreferencesImpl x0) {
        int i = x0.mDiskWritesInFlight;
        x0.mDiskWritesInFlight = i - 1;
        return i;
    }

    static /* synthetic */ long access$608(SharedPreferencesImpl x0) {
        long j = x0.mCurrentMemoryStateGeneration;
        x0.mCurrentMemoryStateGeneration = 1 + j;
        return j;
    }

    @UnsupportedAppUsage
    SharedPreferencesImpl(File file, int mode) {
        this.mFile = file;
        this.mBackupFile = makeBackupFile(file);
        this.mMode = mode;
        this.mLoaded = false;
        this.mMap = null;
        this.mThrowable = null;
        startLoadFromDisk();
    }

    @UnsupportedAppUsage
    private void startLoadFromDisk() {
        synchronized (this.mLock) {
            this.mLoaded = false;
        }
        new Thread("SharedPreferencesImpl-load") {
            public void run() {
                SharedPreferencesImpl.this.loadFromDisk();
            }
        }.start();
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0024, code lost:
        if (r8.mFile.exists() == false) goto L_0x004b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x002c, code lost:
        if (r8.mFile.canRead() != false) goto L_0x004b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x002e, code lost:
        android.util.Log.w(TAG, "Attempt to read preferences file " + r8.mFile + " without permission");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x004b, code lost:
        r0 = null;
        r1 = null;
        r3 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:?, code lost:
        r1 = android.system.Os.stat(r8.mFile.getPath());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0060, code lost:
        if (r8.mFile.canRead() == false) goto L_0x00a7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:?, code lost:
        r2 = new java.io.BufferedInputStream(new java.io.FileInputStream(r8.mFile), 16384);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0076, code lost:
        r0 = com.android.internal.util.XmlUtils.readMapXml(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:?, code lost:
        libcore.io.IoUtils.closeQuietly(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x007b, code lost:
        r4 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x007d, code lost:
        r4 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:?, code lost:
        android.util.Log.w(TAG, "Cannot read " + r8.mFile.getAbsolutePath(), r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:?, code lost:
        libcore.io.IoUtils.closeQuietly((java.lang.AutoCloseable) null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x009e, code lost:
        libcore.io.IoUtils.closeQuietly((java.lang.AutoCloseable) null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00a1, code lost:
        throw r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00a2, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00a3, code lost:
        r3 = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x00a7, code lost:
        r2 = r1;
        r1 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x00ab, code lost:
        monitor-enter(r8.mLock);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:?, code lost:
        r8.mLoaded = true;
        r8.mThrowable = r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00b1, code lost:
        if (r3 == null) goto L_0x00b3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x00b3, code lost:
        if (r1 != null) goto L_0x00b5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:?, code lost:
        r8.mMap = r1;
        r8.mStatTimestamp = r2.st_mtim;
        r8.mStatSize = r2.st_size;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x00c0, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x00c2, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x00c4, code lost:
        r8.mMap = new java.util.HashMap();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:?, code lost:
        r8.mThrowable = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:?, code lost:
        r0 = r8.mLock;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x00d2, code lost:
        r8.mLock.notifyAll();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x00d7, code lost:
        throw r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x00d8, code lost:
        r0 = r8.mLock;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x00da, code lost:
        r0.notifyAll();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x00df, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void loadFromDisk() {
        /*
            r8 = this;
            java.lang.Object r0 = r8.mLock
            monitor-enter(r0)
            boolean r1 = r8.mLoaded     // Catch:{ all -> 0x00e3 }
            if (r1 == 0) goto L_0x0009
            monitor-exit(r0)     // Catch:{ all -> 0x00e3 }
            return
        L_0x0009:
            java.io.File r1 = r8.mBackupFile     // Catch:{ all -> 0x00e3 }
            boolean r1 = r1.exists()     // Catch:{ all -> 0x00e3 }
            if (r1 == 0) goto L_0x001d
            java.io.File r1 = r8.mFile     // Catch:{ all -> 0x00e3 }
            r1.delete()     // Catch:{ all -> 0x00e3 }
            java.io.File r1 = r8.mBackupFile     // Catch:{ all -> 0x00e3 }
            java.io.File r2 = r8.mFile     // Catch:{ all -> 0x00e3 }
            r1.renameTo(r2)     // Catch:{ all -> 0x00e3 }
        L_0x001d:
            monitor-exit(r0)     // Catch:{ all -> 0x00e3 }
            java.io.File r0 = r8.mFile
            boolean r0 = r0.exists()
            if (r0 == 0) goto L_0x004b
            java.io.File r0 = r8.mFile
            boolean r0 = r0.canRead()
            if (r0 != 0) goto L_0x004b
            java.lang.String r0 = "SharedPreferencesImpl"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "Attempt to read preferences file "
            r1.append(r2)
            java.io.File r2 = r8.mFile
            r1.append(r2)
            java.lang.String r2 = " without permission"
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            android.util.Log.w((java.lang.String) r0, (java.lang.String) r1)
        L_0x004b:
            r0 = 0
            r1 = 0
            r2 = 0
            r3 = r2
            java.io.File r4 = r8.mFile     // Catch:{ ErrnoException -> 0x00a5, Throwable -> 0x00a2 }
            java.lang.String r4 = r4.getPath()     // Catch:{ ErrnoException -> 0x00a5, Throwable -> 0x00a2 }
            android.system.StructStat r4 = android.system.Os.stat(r4)     // Catch:{ ErrnoException -> 0x00a5, Throwable -> 0x00a2 }
            r1 = r4
            java.io.File r4 = r8.mFile     // Catch:{ ErrnoException -> 0x00a5, Throwable -> 0x00a2 }
            boolean r4 = r4.canRead()     // Catch:{ ErrnoException -> 0x00a5, Throwable -> 0x00a2 }
            if (r4 == 0) goto L_0x00a6
            java.io.BufferedInputStream r4 = new java.io.BufferedInputStream     // Catch:{ Exception -> 0x007d }
            java.io.FileInputStream r5 = new java.io.FileInputStream     // Catch:{ Exception -> 0x007d }
            java.io.File r6 = r8.mFile     // Catch:{ Exception -> 0x007d }
            r5.<init>(r6)     // Catch:{ Exception -> 0x007d }
            r6 = 16384(0x4000, float:2.2959E-41)
            r4.<init>(r5, r6)     // Catch:{ Exception -> 0x007d }
            r2 = r4
            java.util.HashMap r4 = com.android.internal.util.XmlUtils.readMapXml(r2)     // Catch:{ Exception -> 0x007d }
            r0 = r4
            libcore.io.IoUtils.closeQuietly(r2)     // Catch:{ ErrnoException -> 0x00a5, Throwable -> 0x00a2 }
        L_0x007a:
            goto L_0x00a6
        L_0x007b:
            r4 = move-exception
            goto L_0x009e
        L_0x007d:
            r4 = move-exception
            java.lang.String r5 = "SharedPreferencesImpl"
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x007b }
            r6.<init>()     // Catch:{ all -> 0x007b }
            java.lang.String r7 = "Cannot read "
            r6.append(r7)     // Catch:{ all -> 0x007b }
            java.io.File r7 = r8.mFile     // Catch:{ all -> 0x007b }
            java.lang.String r7 = r7.getAbsolutePath()     // Catch:{ all -> 0x007b }
            r6.append(r7)     // Catch:{ all -> 0x007b }
            java.lang.String r6 = r6.toString()     // Catch:{ all -> 0x007b }
            android.util.Log.w(r5, r6, r4)     // Catch:{ all -> 0x007b }
            libcore.io.IoUtils.closeQuietly(r2)     // Catch:{ ErrnoException -> 0x00a5, Throwable -> 0x00a2 }
            goto L_0x007a
        L_0x009e:
            libcore.io.IoUtils.closeQuietly(r2)     // Catch:{ ErrnoException -> 0x00a5, Throwable -> 0x00a2 }
            throw r4     // Catch:{ ErrnoException -> 0x00a5, Throwable -> 0x00a2 }
        L_0x00a2:
            r2 = move-exception
            r3 = r2
            goto L_0x00a7
        L_0x00a5:
            r2 = move-exception
        L_0x00a6:
        L_0x00a7:
            r2 = r1
            r1 = r0
            java.lang.Object r4 = r8.mLock
            monitor-enter(r4)
            r0 = 1
            r8.mLoaded = r0     // Catch:{ all -> 0x00e0 }
            r8.mThrowable = r3     // Catch:{ all -> 0x00e0 }
            if (r3 != 0) goto L_0x00d8
            if (r1 == 0) goto L_0x00c4
            r8.mMap = r1     // Catch:{ Throwable -> 0x00c2 }
            android.system.StructTimespec r0 = r2.st_mtim     // Catch:{ Throwable -> 0x00c2 }
            r8.mStatTimestamp = r0     // Catch:{ Throwable -> 0x00c2 }
            long r5 = r2.st_size     // Catch:{ Throwable -> 0x00c2 }
            r8.mStatSize = r5     // Catch:{ Throwable -> 0x00c2 }
            goto L_0x00d8
        L_0x00c0:
            r0 = move-exception
            goto L_0x00d2
        L_0x00c2:
            r0 = move-exception
            goto L_0x00cc
        L_0x00c4:
            java.util.HashMap r0 = new java.util.HashMap     // Catch:{ Throwable -> 0x00c2 }
            r0.<init>()     // Catch:{ Throwable -> 0x00c2 }
            r8.mMap = r0     // Catch:{ Throwable -> 0x00c2 }
            goto L_0x00d8
        L_0x00cc:
            r8.mThrowable = r0     // Catch:{ all -> 0x00c0 }
            java.lang.Object r0 = r8.mLock     // Catch:{ all -> 0x00e0 }
            goto L_0x00da
        L_0x00d2:
            java.lang.Object r5 = r8.mLock     // Catch:{ all -> 0x00e0 }
            r5.notifyAll()     // Catch:{ all -> 0x00e0 }
            throw r0     // Catch:{ all -> 0x00e0 }
        L_0x00d8:
            java.lang.Object r0 = r8.mLock     // Catch:{ all -> 0x00e0 }
        L_0x00da:
            r0.notifyAll()     // Catch:{ all -> 0x00e0 }
            monitor-exit(r4)     // Catch:{ all -> 0x00e0 }
            return
        L_0x00e0:
            r0 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x00e0 }
            throw r0
        L_0x00e3:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x00e3 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.app.SharedPreferencesImpl.loadFromDisk():void");
    }

    static File makeBackupFile(File prefsFile) {
        return new File(prefsFile.getPath() + ".bak");
    }

    /* access modifiers changed from: package-private */
    @UnsupportedAppUsage
    public void startReloadIfChangedUnexpectedly() {
        synchronized (this.mLock) {
            if (hasFileChangedUnexpectedly()) {
                startLoadFromDisk();
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:?, code lost:
        dalvik.system.BlockGuard.getThreadPolicy().onReadFromDisk();
        r1 = android.system.Os.stat(r8.mFile.getPath());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x001d, code lost:
        r3 = r8.mLock;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0021, code lost:
        monitor-enter(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x002a, code lost:
        if (r1.st_mtim.equals(r8.mStatTimestamp) == false) goto L_0x0037;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0032, code lost:
        if (r8.mStatSize == r1.st_size) goto L_0x0035;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0035, code lost:
        r0 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0037, code lost:
        monitor-exit(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0038, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x003d, code lost:
        return true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x000b, code lost:
        r0 = true;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean hasFileChangedUnexpectedly() {
        /*
            r8 = this;
            java.lang.Object r0 = r8.mLock
            monitor-enter(r0)
            int r1 = r8.mDiskWritesInFlight     // Catch:{ all -> 0x003e }
            r2 = 0
            if (r1 <= 0) goto L_0x000a
            monitor-exit(r0)     // Catch:{ all -> 0x003e }
            return r2
        L_0x000a:
            monitor-exit(r0)     // Catch:{ all -> 0x003e }
            r0 = 1
            dalvik.system.BlockGuard$Policy r1 = dalvik.system.BlockGuard.getThreadPolicy()     // Catch:{ ErrnoException -> 0x003c }
            r1.onReadFromDisk()     // Catch:{ ErrnoException -> 0x003c }
            java.io.File r1 = r8.mFile     // Catch:{ ErrnoException -> 0x003c }
            java.lang.String r1 = r1.getPath()     // Catch:{ ErrnoException -> 0x003c }
            android.system.StructStat r1 = android.system.Os.stat(r1)     // Catch:{ ErrnoException -> 0x003c }
            java.lang.Object r3 = r8.mLock
            monitor-enter(r3)
            android.system.StructTimespec r4 = r1.st_mtim     // Catch:{ all -> 0x0039 }
            android.system.StructTimespec r5 = r8.mStatTimestamp     // Catch:{ all -> 0x0039 }
            boolean r4 = r4.equals(r5)     // Catch:{ all -> 0x0039 }
            if (r4 == 0) goto L_0x0037
            long r4 = r8.mStatSize     // Catch:{ all -> 0x0039 }
            long r6 = r1.st_size     // Catch:{ all -> 0x0039 }
            int r4 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r4 == 0) goto L_0x0035
            goto L_0x0037
        L_0x0035:
            r0 = r2
        L_0x0037:
            monitor-exit(r3)     // Catch:{ all -> 0x0039 }
            return r0
        L_0x0039:
            r0 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x0039 }
            throw r0
        L_0x003c:
            r1 = move-exception
            return r0
        L_0x003e:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x003e }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.app.SharedPreferencesImpl.hasFileChangedUnexpectedly():boolean");
    }

    public void registerOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener listener) {
        synchronized (this.mLock) {
            this.mListeners.put(listener, CONTENT);
        }
    }

    public void unregisterOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener listener) {
        synchronized (this.mLock) {
            this.mListeners.remove(listener);
        }
    }

    @GuardedBy({"mLock"})
    private void awaitLoadedLocked() {
        if (!this.mLoaded) {
            BlockGuard.getThreadPolicy().onReadFromDisk();
        }
        while (!this.mLoaded) {
            try {
                this.mLock.wait();
            } catch (InterruptedException e) {
            }
        }
        if (this.mThrowable != null) {
            throw new IllegalStateException(this.mThrowable);
        }
    }

    public Map<String, ?> getAll() {
        HashMap hashMap;
        synchronized (this.mLock) {
            awaitLoadedLocked();
            hashMap = new HashMap(this.mMap);
        }
        return hashMap;
    }

    public String getString(String key, String defValue) {
        String str;
        synchronized (this.mLock) {
            awaitLoadedLocked();
            String v = (String) this.mMap.get(key);
            str = v != null ? v : defValue;
        }
        return str;
    }

    public Set<String> getStringSet(String key, Set<String> defValues) {
        Set<String> set;
        synchronized (this.mLock) {
            awaitLoadedLocked();
            Set<String> v = (Set) this.mMap.get(key);
            set = v != null ? v : defValues;
        }
        return set;
    }

    public int getInt(String key, int defValue) {
        int intValue;
        synchronized (this.mLock) {
            awaitLoadedLocked();
            Integer v = (Integer) this.mMap.get(key);
            intValue = v != null ? v.intValue() : defValue;
        }
        return intValue;
    }

    public long getLong(String key, long defValue) {
        long longValue;
        synchronized (this.mLock) {
            awaitLoadedLocked();
            Long v = (Long) this.mMap.get(key);
            longValue = v != null ? v.longValue() : defValue;
        }
        return longValue;
    }

    public float getFloat(String key, float defValue) {
        float floatValue;
        synchronized (this.mLock) {
            awaitLoadedLocked();
            Float v = (Float) this.mMap.get(key);
            floatValue = v != null ? v.floatValue() : defValue;
        }
        return floatValue;
    }

    public boolean getBoolean(String key, boolean defValue) {
        boolean booleanValue;
        synchronized (this.mLock) {
            awaitLoadedLocked();
            Boolean v = (Boolean) this.mMap.get(key);
            booleanValue = v != null ? v.booleanValue() : defValue;
        }
        return booleanValue;
    }

    public boolean contains(String key) {
        boolean containsKey;
        synchronized (this.mLock) {
            awaitLoadedLocked();
            containsKey = this.mMap.containsKey(key);
        }
        return containsKey;
    }

    public SharedPreferences.Editor edit() {
        synchronized (this.mLock) {
            awaitLoadedLocked();
        }
        return new EditorImpl();
    }

    private static class MemoryCommitResult {
        final List<String> keysModified;
        final Set<SharedPreferences.OnSharedPreferenceChangeListener> listeners;
        final Map<String, Object> mapToWriteToDisk;
        final long memoryStateGeneration;
        boolean wasWritten;
        @GuardedBy({"mWritingToDiskLock"})
        volatile boolean writeToDiskResult;
        final CountDownLatch writtenToDiskLatch;

        private MemoryCommitResult(long memoryStateGeneration2, List<String> keysModified2, Set<SharedPreferences.OnSharedPreferenceChangeListener> listeners2, Map<String, Object> mapToWriteToDisk2) {
            this.writtenToDiskLatch = new CountDownLatch(1);
            this.writeToDiskResult = false;
            this.wasWritten = false;
            this.memoryStateGeneration = memoryStateGeneration2;
            this.keysModified = keysModified2;
            this.listeners = listeners2;
            this.mapToWriteToDisk = mapToWriteToDisk2;
        }

        /* access modifiers changed from: package-private */
        public void setDiskWriteResult(boolean wasWritten2, boolean result) {
            this.wasWritten = wasWritten2;
            this.writeToDiskResult = result;
            this.writtenToDiskLatch.countDown();
        }
    }

    public final class EditorImpl implements SharedPreferences.Editor {
        @GuardedBy({"mEditorLock"})
        private boolean mClear = false;
        private final Object mEditorLock = new Object();
        @GuardedBy({"mEditorLock"})
        private final Map<String, Object> mModified = new HashMap();

        public EditorImpl() {
        }

        public SharedPreferences.Editor putString(String key, String value) {
            synchronized (this.mEditorLock) {
                this.mModified.put(key, value);
            }
            return this;
        }

        public SharedPreferences.Editor putStringSet(String key, Set<String> values) {
            synchronized (this.mEditorLock) {
                this.mModified.put(key, values == null ? null : new HashSet(values));
            }
            return this;
        }

        public SharedPreferences.Editor putInt(String key, int value) {
            synchronized (this.mEditorLock) {
                this.mModified.put(key, Integer.valueOf(value));
            }
            return this;
        }

        public SharedPreferences.Editor putLong(String key, long value) {
            synchronized (this.mEditorLock) {
                this.mModified.put(key, Long.valueOf(value));
            }
            return this;
        }

        public SharedPreferences.Editor putFloat(String key, float value) {
            synchronized (this.mEditorLock) {
                this.mModified.put(key, Float.valueOf(value));
            }
            return this;
        }

        public SharedPreferences.Editor putBoolean(String key, boolean value) {
            synchronized (this.mEditorLock) {
                this.mModified.put(key, Boolean.valueOf(value));
            }
            return this;
        }

        public SharedPreferences.Editor remove(String key) {
            synchronized (this.mEditorLock) {
                this.mModified.put(key, this);
            }
            return this;
        }

        public SharedPreferences.Editor clear() {
            synchronized (this.mEditorLock) {
                this.mClear = true;
            }
            return this;
        }

        public void apply() {
            final long startTime = System.currentTimeMillis();
            final MemoryCommitResult mcr = commitToMemory();
            final Runnable awaitCommit = new Runnable() {
                public void run() {
                    try {
                        mcr.writtenToDiskLatch.await();
                    } catch (InterruptedException e) {
                    }
                }
            };
            QueuedWork.addFinisher(awaitCommit);
            SharedPreferencesImpl.this.enqueueDiskWrite(mcr, new Runnable() {
                public void run() {
                    awaitCommit.run();
                    QueuedWork.removeFinisher(awaitCommit);
                }
            });
            notifyListeners(mcr);
        }

        /* JADX WARNING: Removed duplicated region for block: B:43:0x00b2  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private android.app.SharedPreferencesImpl.MemoryCommitResult commitToMemory() {
            /*
                r13 = this;
                r0 = 0
                r1 = 0
                android.app.SharedPreferencesImpl r2 = android.app.SharedPreferencesImpl.this
                java.lang.Object r2 = r2.mLock
                monitor-enter(r2)
                android.app.SharedPreferencesImpl r3 = android.app.SharedPreferencesImpl.this     // Catch:{ all -> 0x00d8 }
                int r3 = r3.mDiskWritesInFlight     // Catch:{ all -> 0x00d8 }
                if (r3 <= 0) goto L_0x0021
                android.app.SharedPreferencesImpl r3 = android.app.SharedPreferencesImpl.this     // Catch:{ all -> 0x00d8 }
                java.util.HashMap r4 = new java.util.HashMap     // Catch:{ all -> 0x00d8 }
                android.app.SharedPreferencesImpl r5 = android.app.SharedPreferencesImpl.this     // Catch:{ all -> 0x00d8 }
                java.util.Map r5 = r5.mMap     // Catch:{ all -> 0x00d8 }
                r4.<init>(r5)     // Catch:{ all -> 0x00d8 }
                java.util.Map unused = r3.mMap = r4     // Catch:{ all -> 0x00d8 }
            L_0x0021:
                android.app.SharedPreferencesImpl r3 = android.app.SharedPreferencesImpl.this     // Catch:{ all -> 0x00d8 }
                java.util.Map r3 = r3.mMap     // Catch:{ all -> 0x00d8 }
                r9 = r3
                android.app.SharedPreferencesImpl r3 = android.app.SharedPreferencesImpl.this     // Catch:{ all -> 0x00d8 }
                android.app.SharedPreferencesImpl.access$308(r3)     // Catch:{ all -> 0x00d8 }
                android.app.SharedPreferencesImpl r3 = android.app.SharedPreferencesImpl.this     // Catch:{ all -> 0x00d8 }
                java.util.WeakHashMap r3 = r3.mListeners     // Catch:{ all -> 0x00d8 }
                int r3 = r3.size()     // Catch:{ all -> 0x00d8 }
                r4 = 0
                if (r3 <= 0) goto L_0x003c
                r3 = 1
                goto L_0x003d
            L_0x003c:
                r3 = r4
            L_0x003d:
                if (r3 == 0) goto L_0x0055
                java.util.ArrayList r5 = new java.util.ArrayList     // Catch:{ all -> 0x00d8 }
                r5.<init>()     // Catch:{ all -> 0x00d8 }
                r0 = r5
                java.util.HashSet r5 = new java.util.HashSet     // Catch:{ all -> 0x00d8 }
                android.app.SharedPreferencesImpl r6 = android.app.SharedPreferencesImpl.this     // Catch:{ all -> 0x00d8 }
                java.util.WeakHashMap r6 = r6.mListeners     // Catch:{ all -> 0x00d8 }
                java.util.Set r6 = r6.keySet()     // Catch:{ all -> 0x00d8 }
                r5.<init>(r6)     // Catch:{ all -> 0x00d8 }
                r1 = r5
            L_0x0055:
                java.lang.Object r7 = r13.mEditorLock     // Catch:{ all -> 0x00d8 }
                monitor-enter(r7)     // Catch:{ all -> 0x00d8 }
                r5 = 0
                boolean r6 = r13.mClear     // Catch:{ all -> 0x00d5 }
                if (r6 == 0) goto L_0x0069
                boolean r6 = r9.isEmpty()     // Catch:{ all -> 0x00d5 }
                if (r6 != 0) goto L_0x0067
                r5 = 1
                r9.clear()     // Catch:{ all -> 0x00d5 }
            L_0x0067:
                r13.mClear = r4     // Catch:{ all -> 0x00d5 }
            L_0x0069:
                java.util.Map<java.lang.String, java.lang.Object> r4 = r13.mModified     // Catch:{ all -> 0x00d5 }
                java.util.Set r4 = r4.entrySet()     // Catch:{ all -> 0x00d5 }
                java.util.Iterator r4 = r4.iterator()     // Catch:{ all -> 0x00d5 }
            L_0x0073:
                boolean r6 = r4.hasNext()     // Catch:{ all -> 0x00d5 }
                if (r6 == 0) goto L_0x00b6
                java.lang.Object r6 = r4.next()     // Catch:{ all -> 0x00d5 }
                java.util.Map$Entry r6 = (java.util.Map.Entry) r6     // Catch:{ all -> 0x00d5 }
                java.lang.Object r8 = r6.getKey()     // Catch:{ all -> 0x00d5 }
                java.lang.String r8 = (java.lang.String) r8     // Catch:{ all -> 0x00d5 }
                java.lang.Object r10 = r6.getValue()     // Catch:{ all -> 0x00d5 }
                if (r10 == r13) goto L_0x00a5
                if (r10 != 0) goto L_0x008e
                goto L_0x00a5
            L_0x008e:
                boolean r11 = r9.containsKey(r8)     // Catch:{ all -> 0x00d5 }
                if (r11 == 0) goto L_0x00a1
                java.lang.Object r11 = r9.get(r8)     // Catch:{ all -> 0x00d5 }
                if (r11 == 0) goto L_0x00a1
                boolean r12 = r11.equals(r10)     // Catch:{ all -> 0x00d5 }
                if (r12 == 0) goto L_0x00a1
                goto L_0x0073
            L_0x00a1:
                r9.put(r8, r10)     // Catch:{ all -> 0x00d5 }
                goto L_0x00af
            L_0x00a5:
                boolean r11 = r9.containsKey(r8)     // Catch:{ all -> 0x00d5 }
                if (r11 != 0) goto L_0x00ac
                goto L_0x0073
            L_0x00ac:
                r9.remove(r8)     // Catch:{ all -> 0x00d5 }
            L_0x00af:
                r5 = 1
                if (r3 == 0) goto L_0x00b5
                r0.add(r8)     // Catch:{ all -> 0x00d5 }
            L_0x00b5:
                goto L_0x0073
            L_0x00b6:
                java.util.Map<java.lang.String, java.lang.Object> r4 = r13.mModified     // Catch:{ all -> 0x00d5 }
                r4.clear()     // Catch:{ all -> 0x00d5 }
                if (r5 == 0) goto L_0x00c2
                android.app.SharedPreferencesImpl r4 = android.app.SharedPreferencesImpl.this     // Catch:{ all -> 0x00d5 }
                android.app.SharedPreferencesImpl.access$608(r4)     // Catch:{ all -> 0x00d5 }
            L_0x00c2:
                android.app.SharedPreferencesImpl r4 = android.app.SharedPreferencesImpl.this     // Catch:{ all -> 0x00d5 }
                long r10 = r4.mCurrentMemoryStateGeneration     // Catch:{ all -> 0x00d5 }
                r5 = r10
                monitor-exit(r7)     // Catch:{ all -> 0x00d5 }
                monitor-exit(r2)     // Catch:{ all -> 0x00d8 }
                android.app.SharedPreferencesImpl$MemoryCommitResult r2 = new android.app.SharedPreferencesImpl$MemoryCommitResult
                r10 = 0
                r4 = r2
                r7 = r0
                r8 = r1
                r4.<init>(r5, r7, r8, r9)
                return r2
            L_0x00d5:
                r4 = move-exception
                monitor-exit(r7)     // Catch:{ all -> 0x00d5 }
                throw r4     // Catch:{ all -> 0x00d8 }
            L_0x00d8:
                r3 = move-exception
                monitor-exit(r2)     // Catch:{ all -> 0x00d8 }
                throw r3
            */
            throw new UnsupportedOperationException("Method not decompiled: android.app.SharedPreferencesImpl.EditorImpl.commitToMemory():android.app.SharedPreferencesImpl$MemoryCommitResult");
        }

        /* JADX WARNING: Code restructure failed: missing block: B:8:0x001b, code lost:
            return false;
         */
        /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean commit() {
            /*
                r5 = this;
                r0 = 0
                android.app.SharedPreferencesImpl$MemoryCommitResult r2 = r5.commitToMemory()
                android.app.SharedPreferencesImpl r3 = android.app.SharedPreferencesImpl.this
                r4 = 0
                r3.enqueueDiskWrite(r2, r4)
                java.util.concurrent.CountDownLatch r3 = r2.writtenToDiskLatch     // Catch:{ InterruptedException -> 0x001a, all -> 0x0018 }
                r3.await()     // Catch:{ InterruptedException -> 0x001a, all -> 0x0018 }
                r5.notifyListeners(r2)
                boolean r3 = r2.writeToDiskResult
                return r3
            L_0x0018:
                r3 = move-exception
                throw r3
            L_0x001a:
                r3 = move-exception
                r4 = 0
                return r4
            */
            throw new UnsupportedOperationException("Method not decompiled: android.app.SharedPreferencesImpl.EditorImpl.commit():boolean");
        }

        /* access modifiers changed from: private */
        public void notifyListeners(MemoryCommitResult mcr) {
            if (mcr.listeners != null && mcr.keysModified != null && mcr.keysModified.size() != 0) {
                if (Looper.myLooper() == Looper.getMainLooper()) {
                    for (int i = mcr.keysModified.size() - 1; i >= 0; i--) {
                        String key = mcr.keysModified.get(i);
                        for (SharedPreferences.OnSharedPreferenceChangeListener listener : mcr.listeners) {
                            if (listener != null) {
                                listener.onSharedPreferenceChanged(SharedPreferencesImpl.this, key);
                            }
                        }
                    }
                    return;
                }
                ActivityThread.sMainThreadHandler.post(new Runnable(mcr) {
                    private final /* synthetic */ SharedPreferencesImpl.MemoryCommitResult f$1;

                    {
                        this.f$1 = r2;
                    }

                    public final void run() {
                        SharedPreferencesImpl.EditorImpl.this.notifyListeners(this.f$1);
                    }
                });
            }
        }
    }

    /* access modifiers changed from: private */
    public void enqueueDiskWrite(final MemoryCommitResult mcr, final Runnable postWriteRunnable) {
        boolean wasEmpty;
        boolean z = false;
        final boolean isFromSyncCommit = postWriteRunnable == null;
        Runnable writeToDiskRunnable = new Runnable() {
            public void run() {
                synchronized (SharedPreferencesImpl.this.mWritingToDiskLock) {
                    SharedPreferencesImpl.this.writeToFile(mcr, isFromSyncCommit);
                }
                synchronized (SharedPreferencesImpl.this.mLock) {
                    SharedPreferencesImpl.access$310(SharedPreferencesImpl.this);
                }
                if (postWriteRunnable != null) {
                    postWriteRunnable.run();
                }
            }
        };
        if (isFromSyncCommit) {
            synchronized (this.mLock) {
                wasEmpty = this.mDiskWritesInFlight == 1;
            }
            if (wasEmpty) {
                writeToDiskRunnable.run();
                return;
            }
        }
        if (!isFromSyncCommit) {
            z = true;
        }
        QueuedWork.queue(writeToDiskRunnable, z);
    }

    private static FileOutputStream createFileOutputStream(File file) {
        try {
            return new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            File parent = file.getParentFile();
            if (!parent.mkdir()) {
                Log.e(TAG, "Couldn't create directory for SharedPreferences file " + file);
                return null;
            }
            FileUtils.setPermissions(parent.getPath(), 505, -1, -1);
            try {
                return new FileOutputStream(file);
            } catch (FileNotFoundException e2) {
                Log.e(TAG, "Couldn't create SharedPreferences file " + file, e2);
                return null;
            }
        }
    }

    /* access modifiers changed from: private */
    @GuardedBy({"mWritingToDiskLock"})
    public void writeToFile(MemoryCommitResult mcr, boolean isFromSyncCommit) {
        MemoryCommitResult memoryCommitResult = mcr;
        if (this.mFile.exists()) {
            boolean needsWrite = false;
            if (this.mDiskStateGeneration < memoryCommitResult.memoryStateGeneration) {
                if (isFromSyncCommit) {
                    needsWrite = true;
                } else {
                    synchronized (this.mLock) {
                        try {
                            if (this.mCurrentMemoryStateGeneration == memoryCommitResult.memoryStateGeneration) {
                                needsWrite = true;
                            }
                        } catch (Throwable th) {
                            th = th;
                            throw th;
                        }
                    }
                }
            }
            if (!needsWrite) {
                memoryCommitResult.setDiskWriteResult(false, true);
                return;
            } else if (this.mBackupFile.exists()) {
                this.mFile.delete();
            } else if (!this.mFile.renameTo(this.mBackupFile)) {
                Log.e(TAG, "Couldn't rename file " + this.mFile + " to backup file " + this.mBackupFile);
                memoryCommitResult.setDiskWriteResult(false, false);
                return;
            }
        }
        try {
            FileOutputStream str = createFileOutputStream(this.mFile);
            if (str == null) {
                memoryCommitResult.setDiskWriteResult(false, false);
                return;
            }
            XmlUtils.writeMapXml(memoryCommitResult.mapToWriteToDisk, str);
            long writeTime = System.currentTimeMillis();
            FileUtils.sync(str);
            long fsyncTime = System.currentTimeMillis();
            str.close();
            ContextImpl.setFilePermissionsFromMode(this.mFile.getPath(), this.mMode, 0);
            try {
                StructStat stat = Os.stat(this.mFile.getPath());
                synchronized (this.mLock) {
                    this.mStatTimestamp = stat.st_mtim;
                    this.mStatSize = stat.st_size;
                }
            } catch (ErrnoException e) {
            }
            this.mBackupFile.delete();
            this.mDiskStateGeneration = memoryCommitResult.memoryStateGeneration;
            memoryCommitResult.setDiskWriteResult(true, true);
            long fsyncDuration = fsyncTime - writeTime;
            this.mSyncTimes.add((int) fsyncDuration);
            this.mNumSync++;
            if (this.mNumSync % 1024 == 0 || fsyncDuration > 256) {
                this.mSyncTimes.log(TAG, "Time required to fsync " + this.mFile + PluralRules.KEYWORD_RULE_SEPARATOR);
            }
        } catch (XmlPullParserException e2) {
            Log.w(TAG, "writeToFile: Got exception:", e2);
            if (this.mFile.exists() && !this.mFile.delete()) {
                Log.e(TAG, "Couldn't clean up partially-written file " + this.mFile);
            }
            memoryCommitResult.setDiskWriteResult(false, false);
        } catch (IOException e3) {
            Log.w(TAG, "writeToFile: Got exception:", e3);
            Log.e(TAG, "Couldn't clean up partially-written file " + this.mFile);
            memoryCommitResult.setDiskWriteResult(false, false);
        }
    }
}
