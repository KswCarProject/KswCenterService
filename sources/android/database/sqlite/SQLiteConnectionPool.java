package android.database.sqlite;

import android.database.sqlite.SQLiteDebug;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.Looper;
import android.os.OperationCanceledException;
import android.os.SystemClock;
import android.provider.SettingsStringUtil;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Log;
import android.util.PrefixPrinter;
import android.util.Printer;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.annotations.VisibleForTesting;
import dalvik.system.CloseGuard;
import java.io.Closeable;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.LockSupport;

public final class SQLiteConnectionPool implements Closeable {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final int CONNECTION_FLAG_INTERACTIVE = 4;
    public static final int CONNECTION_FLAG_PRIMARY_CONNECTION_AFFINITY = 2;
    public static final int CONNECTION_FLAG_READ_ONLY = 1;
    private static final long CONNECTION_POOL_BUSY_MILLIS = 30000;
    private static final String TAG = "SQLiteConnectionPool";
    private final WeakHashMap<SQLiteConnection, AcquiredConnectionStatus> mAcquiredConnections = new WeakHashMap<>();
    private final ArrayList<SQLiteConnection> mAvailableNonPrimaryConnections = new ArrayList<>();
    private SQLiteConnection mAvailablePrimaryConnection;
    private final CloseGuard mCloseGuard = CloseGuard.get();
    /* access modifiers changed from: private */
    public final SQLiteDatabaseConfiguration mConfiguration;
    private final AtomicBoolean mConnectionLeaked = new AtomicBoolean();
    private ConnectionWaiter mConnectionWaiterPool;
    private ConnectionWaiter mConnectionWaiterQueue;
    /* access modifiers changed from: private */
    @GuardedBy({"mLock"})
    public IdleConnectionHandler mIdleConnectionHandler;
    private boolean mIsOpen;
    /* access modifiers changed from: private */
    public final Object mLock = new Object();
    private int mMaxConnectionPoolSize;
    private int mNextConnectionId;
    private final AtomicLong mTotalExecutionTimeCounter = new AtomicLong(0);

    enum AcquiredConnectionStatus {
        NORMAL,
        RECONFIGURE,
        DISCARD
    }

    private SQLiteConnectionPool(SQLiteDatabaseConfiguration configuration) {
        this.mConfiguration = new SQLiteDatabaseConfiguration(configuration);
        setMaxConnectionPoolSizeLocked();
        if (this.mConfiguration.idleConnectionTimeoutMs != Long.MAX_VALUE) {
            setupIdleConnectionHandler(Looper.getMainLooper(), this.mConfiguration.idleConnectionTimeoutMs);
        }
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        try {
            dispose(true);
        } finally {
            super.finalize();
        }
    }

    public static SQLiteConnectionPool open(SQLiteDatabaseConfiguration configuration) {
        if (configuration != null) {
            SQLiteConnectionPool pool = new SQLiteConnectionPool(configuration);
            pool.open();
            return pool;
        }
        throw new IllegalArgumentException("configuration must not be null.");
    }

    private void open() {
        this.mAvailablePrimaryConnection = openConnectionLocked(this.mConfiguration, true);
        synchronized (this.mLock) {
            if (this.mIdleConnectionHandler != null) {
                this.mIdleConnectionHandler.connectionReleased(this.mAvailablePrimaryConnection);
            }
        }
        this.mIsOpen = true;
        this.mCloseGuard.open("close");
    }

    public void close() {
        dispose(false);
    }

    private void dispose(boolean finalized) {
        if (this.mCloseGuard != null) {
            if (finalized) {
                this.mCloseGuard.warnIfOpen();
            }
            this.mCloseGuard.close();
        }
        if (!finalized) {
            synchronized (this.mLock) {
                throwIfClosedLocked();
                this.mIsOpen = false;
                closeAvailableConnectionsAndLogExceptionsLocked();
                int pendingCount = this.mAcquiredConnections.size();
                if (pendingCount != 0) {
                    Log.i(TAG, "The connection pool for " + this.mConfiguration.label + " has been closed but there are still " + pendingCount + " connections in use.  They will be closed as they are released back to the pool.");
                }
                wakeConnectionWaitersLocked();
            }
        }
    }

    public void reconfigure(SQLiteDatabaseConfiguration configuration) {
        if (configuration != null) {
            synchronized (this.mLock) {
                throwIfClosedLocked();
                boolean onlyCompatWalChanged = false;
                boolean walModeChanged = ((configuration.openFlags ^ this.mConfiguration.openFlags) & 536870912) != 0;
                if (walModeChanged) {
                    if (this.mAcquiredConnections.isEmpty()) {
                        closeAvailableNonPrimaryConnectionsAndLogExceptionsLocked();
                    } else {
                        throw new IllegalStateException("Write Ahead Logging (WAL) mode cannot be enabled or disabled while there are transactions in progress.  Finish all transactions and release all active database connections first.");
                    }
                }
                if (configuration.foreignKeyConstraintsEnabled != this.mConfiguration.foreignKeyConstraintsEnabled) {
                    if (!this.mAcquiredConnections.isEmpty()) {
                        throw new IllegalStateException("Foreign Key Constraints cannot be enabled or disabled while there are transactions in progress.  Finish all transactions and release all active database connections first.");
                    }
                }
                if ((this.mConfiguration.openFlags ^ configuration.openFlags) == Integer.MIN_VALUE) {
                    onlyCompatWalChanged = true;
                }
                if (onlyCompatWalChanged || this.mConfiguration.openFlags == configuration.openFlags) {
                    this.mConfiguration.updateParametersFrom(configuration);
                    setMaxConnectionPoolSizeLocked();
                    closeExcessConnectionsAndLogExceptionsLocked();
                    reconfigureAllConnectionsLocked();
                } else {
                    if (walModeChanged) {
                        closeAvailableConnectionsAndLogExceptionsLocked();
                    }
                    SQLiteConnection newPrimaryConnection = openConnectionLocked(configuration, true);
                    closeAvailableConnectionsAndLogExceptionsLocked();
                    discardAcquiredConnectionsLocked();
                    this.mAvailablePrimaryConnection = newPrimaryConnection;
                    this.mConfiguration.updateParametersFrom(configuration);
                    setMaxConnectionPoolSizeLocked();
                }
                wakeConnectionWaitersLocked();
            }
            return;
        }
        throw new IllegalArgumentException("configuration must not be null.");
    }

    public SQLiteConnection acquireConnection(String sql, int connectionFlags, CancellationSignal cancellationSignal) {
        SQLiteConnection con = waitForConnection(sql, connectionFlags, cancellationSignal);
        synchronized (this.mLock) {
            if (this.mIdleConnectionHandler != null) {
                this.mIdleConnectionHandler.connectionAcquired(con);
            }
        }
        return con;
    }

    public void releaseConnection(SQLiteConnection connection) {
        synchronized (this.mLock) {
            if (this.mIdleConnectionHandler != null) {
                this.mIdleConnectionHandler.connectionReleased(connection);
            }
            AcquiredConnectionStatus status = this.mAcquiredConnections.remove(connection);
            if (status == null) {
                throw new IllegalStateException("Cannot perform this operation because the specified connection was not acquired from this pool or has already been released.");
            } else if (!this.mIsOpen) {
                closeConnectionAndLogExceptionsLocked(connection);
            } else if (connection.isPrimaryConnection()) {
                if (recycleConnectionLocked(connection, status)) {
                    this.mAvailablePrimaryConnection = connection;
                }
                wakeConnectionWaitersLocked();
            } else if (this.mAvailableNonPrimaryConnections.size() >= this.mMaxConnectionPoolSize - 1) {
                closeConnectionAndLogExceptionsLocked(connection);
            } else {
                if (recycleConnectionLocked(connection, status)) {
                    this.mAvailableNonPrimaryConnections.add(connection);
                }
                wakeConnectionWaitersLocked();
            }
        }
    }

    @GuardedBy({"mLock"})
    private boolean recycleConnectionLocked(SQLiteConnection connection, AcquiredConnectionStatus status) {
        if (status == AcquiredConnectionStatus.RECONFIGURE) {
            try {
                connection.reconfigure(this.mConfiguration);
            } catch (RuntimeException ex) {
                Log.e(TAG, "Failed to reconfigure released connection, closing it: " + connection, ex);
                status = AcquiredConnectionStatus.DISCARD;
            }
        }
        if (status != AcquiredConnectionStatus.DISCARD) {
            return true;
        }
        closeConnectionAndLogExceptionsLocked(connection);
        return false;
    }

    public boolean shouldYieldConnection(SQLiteConnection connection, int connectionFlags) {
        synchronized (this.mLock) {
            if (!this.mAcquiredConnections.containsKey(connection)) {
                throw new IllegalStateException("Cannot perform this operation because the specified connection was not acquired from this pool or has already been released.");
            } else if (!this.mIsOpen) {
                return false;
            } else {
                boolean isSessionBlockingImportantConnectionWaitersLocked = isSessionBlockingImportantConnectionWaitersLocked(connection.isPrimaryConnection(), connectionFlags);
                return isSessionBlockingImportantConnectionWaitersLocked;
            }
        }
    }

    public void collectDbStats(ArrayList<SQLiteDebug.DbStats> dbStatsList) {
        synchronized (this.mLock) {
            if (this.mAvailablePrimaryConnection != null) {
                this.mAvailablePrimaryConnection.collectDbStats(dbStatsList);
            }
            Iterator<SQLiteConnection> it = this.mAvailableNonPrimaryConnections.iterator();
            while (it.hasNext()) {
                it.next().collectDbStats(dbStatsList);
            }
            for (SQLiteConnection connection : this.mAcquiredConnections.keySet()) {
                connection.collectDbStatsUnsafe(dbStatsList);
            }
        }
    }

    private SQLiteConnection openConnectionLocked(SQLiteDatabaseConfiguration configuration, boolean primaryConnection) {
        int connectionId = this.mNextConnectionId;
        this.mNextConnectionId = connectionId + 1;
        return SQLiteConnection.open(this, configuration, connectionId, primaryConnection);
    }

    /* access modifiers changed from: package-private */
    public void onConnectionLeaked() {
        Log.w(TAG, "A SQLiteConnection object for database '" + this.mConfiguration.label + "' was leaked!  Please fix your application to end transactions in progress properly and to close the database when it is no longer needed.");
        this.mConnectionLeaked.set(true);
    }

    /* access modifiers changed from: package-private */
    public void onStatementExecuted(long executionTimeMs) {
        this.mTotalExecutionTimeCounter.addAndGet(executionTimeMs);
    }

    @GuardedBy({"mLock"})
    private void closeAvailableConnectionsAndLogExceptionsLocked() {
        closeAvailableNonPrimaryConnectionsAndLogExceptionsLocked();
        if (this.mAvailablePrimaryConnection != null) {
            closeConnectionAndLogExceptionsLocked(this.mAvailablePrimaryConnection);
            this.mAvailablePrimaryConnection = null;
        }
    }

    /* access modifiers changed from: private */
    @GuardedBy({"mLock"})
    public boolean closeAvailableConnectionLocked(int connectionId) {
        for (int i = this.mAvailableNonPrimaryConnections.size() - 1; i >= 0; i--) {
            SQLiteConnection c = this.mAvailableNonPrimaryConnections.get(i);
            if (c.getConnectionId() == connectionId) {
                closeConnectionAndLogExceptionsLocked(c);
                this.mAvailableNonPrimaryConnections.remove(i);
                return true;
            }
        }
        if (this.mAvailablePrimaryConnection == null || this.mAvailablePrimaryConnection.getConnectionId() != connectionId) {
            return false;
        }
        closeConnectionAndLogExceptionsLocked(this.mAvailablePrimaryConnection);
        this.mAvailablePrimaryConnection = null;
        return true;
    }

    @GuardedBy({"mLock"})
    private void closeAvailableNonPrimaryConnectionsAndLogExceptionsLocked() {
        int count = this.mAvailableNonPrimaryConnections.size();
        for (int i = 0; i < count; i++) {
            closeConnectionAndLogExceptionsLocked(this.mAvailableNonPrimaryConnections.get(i));
        }
        this.mAvailableNonPrimaryConnections.clear();
    }

    /* access modifiers changed from: package-private */
    public void closeAvailableNonPrimaryConnectionsAndLogExceptions() {
        synchronized (this.mLock) {
            closeAvailableNonPrimaryConnectionsAndLogExceptionsLocked();
        }
    }

    @GuardedBy({"mLock"})
    private void closeExcessConnectionsAndLogExceptionsLocked() {
        int availableCount = this.mAvailableNonPrimaryConnections.size();
        while (true) {
            int availableCount2 = availableCount - 1;
            if (availableCount > this.mMaxConnectionPoolSize - 1) {
                closeConnectionAndLogExceptionsLocked(this.mAvailableNonPrimaryConnections.remove(availableCount2));
                availableCount = availableCount2;
            } else {
                return;
            }
        }
    }

    @GuardedBy({"mLock"})
    private void closeConnectionAndLogExceptionsLocked(SQLiteConnection connection) {
        try {
            connection.close();
            if (this.mIdleConnectionHandler != null) {
                this.mIdleConnectionHandler.connectionClosed(connection);
            }
        } catch (RuntimeException ex) {
            Log.e(TAG, "Failed to close connection, its fate is now in the hands of the merciful GC: " + connection, ex);
        }
    }

    private void discardAcquiredConnectionsLocked() {
        markAcquiredConnectionsLocked(AcquiredConnectionStatus.DISCARD);
    }

    @GuardedBy({"mLock"})
    private void reconfigureAllConnectionsLocked() {
        if (this.mAvailablePrimaryConnection != null) {
            try {
                this.mAvailablePrimaryConnection.reconfigure(this.mConfiguration);
            } catch (RuntimeException ex) {
                Log.e(TAG, "Failed to reconfigure available primary connection, closing it: " + this.mAvailablePrimaryConnection, ex);
                closeConnectionAndLogExceptionsLocked(this.mAvailablePrimaryConnection);
                this.mAvailablePrimaryConnection = null;
            }
        }
        int count = this.mAvailableNonPrimaryConnections.size();
        int i = 0;
        while (i < count) {
            SQLiteConnection connection = this.mAvailableNonPrimaryConnections.get(i);
            try {
                connection.reconfigure(this.mConfiguration);
            } catch (RuntimeException ex2) {
                Log.e(TAG, "Failed to reconfigure available non-primary connection, closing it: " + connection, ex2);
                closeConnectionAndLogExceptionsLocked(connection);
                this.mAvailableNonPrimaryConnections.remove(i);
                count += -1;
                i--;
            }
            i++;
        }
        markAcquiredConnectionsLocked(AcquiredConnectionStatus.RECONFIGURE);
    }

    private void markAcquiredConnectionsLocked(AcquiredConnectionStatus status) {
        if (!this.mAcquiredConnections.isEmpty()) {
            ArrayList<SQLiteConnection> keysToUpdate = new ArrayList<>(this.mAcquiredConnections.size());
            for (Map.Entry<SQLiteConnection, AcquiredConnectionStatus> entry : this.mAcquiredConnections.entrySet()) {
                AcquiredConnectionStatus oldStatus = entry.getValue();
                if (!(status == oldStatus || oldStatus == AcquiredConnectionStatus.DISCARD)) {
                    keysToUpdate.add(entry.getKey());
                }
            }
            int updateCount = keysToUpdate.size();
            for (int i = 0; i < updateCount; i++) {
                this.mAcquiredConnections.put(keysToUpdate.get(i), status);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0069, code lost:
        if (r11 == null) goto L_0x0073;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x006b, code lost:
        r11.setOnCancelListener(new android.database.sqlite.SQLiteConnectionPool.AnonymousClass1(r9));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x0073, code lost:
        r3 = 30000;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:?, code lost:
        r6 = r1.mStartTime + 30000;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x007f, code lost:
        if (r9.mConnectionLeaked.compareAndSet(r13, false) == false) goto L_0x0090;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:?, code lost:
        r12 = r9.mLock;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x0083, code lost:
        monitor-enter(r12);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:?, code lost:
        wakeConnectionWaitersLocked();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x0087, code lost:
        monitor-exit(r12);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x008c, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x008d, code lost:
        r17 = r14;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x0090, code lost:
        r17 = r14;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:?, code lost:
        java.util.concurrent.locks.LockSupport.parkNanos(r9, r3 * android.util.TimeUtils.NANOS_PER_MS);
        java.lang.Thread.interrupted();
        r12 = r9.mLock;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x009f, code lost:
        monitor-enter(r12);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:?, code lost:
        throwIfClosedLocked();
        r0 = r1.mAssignedConnection;
        r13 = r1.mException;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x00a7, code lost:
        if (r0 != null) goto L_0x00cb;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x00a9, code lost:
        if (r13 == null) goto L_0x00ae;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x00ab, code lost:
        r18 = r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:0x00ae, code lost:
        r14 = android.os.SystemClock.uptimeMillis();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:66:0x00b4, code lost:
        if (r14 >= r6) goto L_0x00b9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x00b6, code lost:
        r3 = r14 - r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:68:0x00b9, code lost:
        r18 = r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:70:?, code lost:
        logConnectionPoolBusyLocked(r14 - r1.mStartTime, r10);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:71:0x00c2, code lost:
        r3 = 30000;
        r6 = r14 + 30000;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:73:?, code lost:
        monitor-exit(r12);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:74:0x00c7, code lost:
        r14 = r17;
        r13 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:75:0x00cb, code lost:
        r18 = r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:77:?, code lost:
        recycleConnectionWaiterLocked(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:78:0x00d0, code lost:
        if (r0 == null) goto L_0x00da;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:79:0x00d2, code lost:
        monitor-exit(r12);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:80:0x00d3, code lost:
        if (r11 == null) goto L_0x00d9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:81:0x00d5, code lost:
        r11.setOnCancelListener((android.os.CancellationSignal.OnCancelListener) null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:82:0x00d9, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:84:?, code lost:
        throw r13;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:85:0x00db, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:86:0x00dc, code lost:
        r6 = r18;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:87:0x00df, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:88:0x00e0, code lost:
        r18 = r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:90:?, code lost:
        monitor-exit(r12);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:92:?, code lost:
        throw r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:93:0x00e4, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:94:0x00e6, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:95:0x00e8, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:96:0x00e9, code lost:
        r17 = r14;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:97:0x00eb, code lost:
        if (r11 != null) goto L_0x00ed;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:98:0x00ed, code lost:
        r11.setOnCancelListener((android.os.CancellationSignal.OnCancelListener) null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:99:0x00f1, code lost:
        throw r0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private android.database.sqlite.SQLiteConnection waitForConnection(java.lang.String r21, int r22, android.os.CancellationSignal r23) {
        /*
            r20 = this;
            r9 = r20
            r10 = r22
            r11 = r23
            r0 = r10 & 2
            r13 = 1
            if (r0 == 0) goto L_0x000d
            r0 = r13
            goto L_0x000e
        L_0x000d:
            r0 = 0
        L_0x000e:
            r14 = r0
            java.lang.Object r15 = r9.mLock
            monitor-enter(r15)
            r20.throwIfClosedLocked()     // Catch:{ all -> 0x00f2 }
            if (r11 == 0) goto L_0x0020
            r23.throwIfCanceled()     // Catch:{ all -> 0x001b }
            goto L_0x0020
        L_0x001b:
            r0 = move-exception
            r17 = r14
            goto L_0x00f5
        L_0x0020:
            r0 = 0
            if (r14 != 0) goto L_0x0028
            android.database.sqlite.SQLiteConnection r1 = r20.tryAcquireNonPrimaryConnectionLocked(r21, r22)     // Catch:{ all -> 0x001b }
            r0 = r1
        L_0x0028:
            if (r0 != 0) goto L_0x002f
            android.database.sqlite.SQLiteConnection r1 = r9.tryAcquirePrimaryConnectionLocked(r10)     // Catch:{ all -> 0x001b }
            r0 = r1
        L_0x002f:
            if (r0 == 0) goto L_0x0033
            monitor-exit(r15)     // Catch:{ all -> 0x001b }
            return r0
        L_0x0033:
            int r1 = getPriority(r22)     // Catch:{ all -> 0x00f2 }
            r8 = r1
            long r3 = android.os.SystemClock.uptimeMillis()     // Catch:{ all -> 0x00f2 }
            java.lang.Thread r2 = java.lang.Thread.currentThread()     // Catch:{ all -> 0x00f2 }
            r1 = r20
            r5 = r8
            r6 = r14
            r7 = r21
            r12 = r8
            r8 = r22
            android.database.sqlite.SQLiteConnectionPool$ConnectionWaiter r1 = r1.obtainConnectionWaiterLocked(r2, r3, r5, r6, r7, r8)     // Catch:{ all -> 0x00f2 }
            r2 = 0
            android.database.sqlite.SQLiteConnectionPool$ConnectionWaiter r5 = r9.mConnectionWaiterQueue     // Catch:{ all -> 0x00f2 }
        L_0x0050:
            if (r5 == 0) goto L_0x005e
            int r6 = r5.mPriority     // Catch:{ all -> 0x001b }
            if (r12 <= r6) goto L_0x0059
            r1.mNext = r5     // Catch:{ all -> 0x001b }
            goto L_0x005e
        L_0x0059:
            r2 = r5
            android.database.sqlite.SQLiteConnectionPool$ConnectionWaiter r6 = r5.mNext     // Catch:{ all -> 0x001b }
            r5 = r6
            goto L_0x0050
        L_0x005e:
            if (r2 == 0) goto L_0x0063
            r2.mNext = r1     // Catch:{ all -> 0x001b }
            goto L_0x0065
        L_0x0063:
            r9.mConnectionWaiterQueue = r1     // Catch:{ all -> 0x00f2 }
        L_0x0065:
            int r6 = r1.mNonce     // Catch:{ all -> 0x00f2 }
            r2 = r6
            monitor-exit(r15)     // Catch:{ all -> 0x00f2 }
            if (r11 == 0) goto L_0x0073
            android.database.sqlite.SQLiteConnectionPool$1 r0 = new android.database.sqlite.SQLiteConnectionPool$1
            r0.<init>(r1, r2)
            r11.setOnCancelListener(r0)
        L_0x0073:
            r3 = 30000(0x7530, double:1.4822E-319)
            long r6 = r1.mStartTime     // Catch:{ all -> 0x00e8 }
            long r6 = r6 + r3
        L_0x0078:
            java.util.concurrent.atomic.AtomicBoolean r0 = r9.mConnectionLeaked     // Catch:{ all -> 0x00e8 }
            r8 = 0
            boolean r0 = r0.compareAndSet(r13, r8)     // Catch:{ all -> 0x00e8 }
            if (r0 == 0) goto L_0x0090
            java.lang.Object r12 = r9.mLock     // Catch:{ all -> 0x008c }
            monitor-enter(r12)     // Catch:{ all -> 0x008c }
            r20.wakeConnectionWaitersLocked()     // Catch:{ all -> 0x0089 }
            monitor-exit(r12)     // Catch:{ all -> 0x0089 }
            goto L_0x0090
        L_0x0089:
            r0 = move-exception
            monitor-exit(r12)     // Catch:{ all -> 0x0089 }
            throw r0     // Catch:{ all -> 0x008c }
        L_0x008c:
            r0 = move-exception
            r17 = r14
            goto L_0x00eb
        L_0x0090:
            r15 = 1000000(0xf4240, double:4.940656E-318)
            r17 = r14
            long r13 = r3 * r15
            java.util.concurrent.locks.LockSupport.parkNanos(r9, r13)     // Catch:{ all -> 0x00e6 }
            java.lang.Thread.interrupted()     // Catch:{ all -> 0x00e6 }
            java.lang.Object r12 = r9.mLock     // Catch:{ all -> 0x00e6 }
            monitor-enter(r12)     // Catch:{ all -> 0x00e6 }
            r20.throwIfClosedLocked()     // Catch:{ all -> 0x00df }
            android.database.sqlite.SQLiteConnection r0 = r1.mAssignedConnection     // Catch:{ all -> 0x00df }
            java.lang.RuntimeException r13 = r1.mException     // Catch:{ all -> 0x00df }
            if (r0 != 0) goto L_0x00cb
            if (r13 == 0) goto L_0x00ae
            r18 = r6
            goto L_0x00cd
        L_0x00ae:
            long r14 = android.os.SystemClock.uptimeMillis()     // Catch:{ all -> 0x00df }
            int r16 = (r14 > r6 ? 1 : (r14 == r6 ? 0 : -1))
            if (r16 >= 0) goto L_0x00b9
            long r3 = r14 - r6
            goto L_0x00c6
        L_0x00b9:
            r18 = r6
            long r5 = r1.mStartTime     // Catch:{ all -> 0x00db }
            long r5 = r14 - r5
            r9.logConnectionPoolBusyLocked(r5, r10)     // Catch:{ all -> 0x00db }
            r3 = 30000(0x7530, double:1.4822E-319)
            long r14 = r14 + r3
            r6 = r14
        L_0x00c6:
            monitor-exit(r12)     // Catch:{ all -> 0x00e4 }
            r14 = r17
            r13 = 1
            goto L_0x0078
        L_0x00cb:
            r18 = r6
        L_0x00cd:
            r9.recycleConnectionWaiterLocked(r1)     // Catch:{ all -> 0x00db }
            if (r0 == 0) goto L_0x00da
            monitor-exit(r12)     // Catch:{ all -> 0x00db }
            if (r11 == 0) goto L_0x00d9
            r5 = 0
            r11.setOnCancelListener(r5)
        L_0x00d9:
            return r0
        L_0x00da:
            throw r13     // Catch:{ all -> 0x00db }
        L_0x00db:
            r0 = move-exception
            r6 = r18
            goto L_0x00e2
        L_0x00df:
            r0 = move-exception
            r18 = r6
        L_0x00e2:
            monitor-exit(r12)     // Catch:{ all -> 0x00e4 }
            throw r0     // Catch:{ all -> 0x00e6 }
        L_0x00e4:
            r0 = move-exception
            goto L_0x00e2
        L_0x00e6:
            r0 = move-exception
            goto L_0x00eb
        L_0x00e8:
            r0 = move-exception
            r17 = r14
        L_0x00eb:
            if (r11 == 0) goto L_0x00f1
            r3 = 0
            r11.setOnCancelListener(r3)
        L_0x00f1:
            throw r0
        L_0x00f2:
            r0 = move-exception
            r17 = r14
        L_0x00f5:
            monitor-exit(r15)     // Catch:{ all -> 0x00f7 }
            throw r0
        L_0x00f7:
            r0 = move-exception
            goto L_0x00f5
        */
        throw new UnsupportedOperationException("Method not decompiled: android.database.sqlite.SQLiteConnectionPool.waitForConnection(java.lang.String, int, android.os.CancellationSignal):android.database.sqlite.SQLiteConnection");
    }

    /* access modifiers changed from: private */
    @GuardedBy({"mLock"})
    public void cancelConnectionWaiterLocked(ConnectionWaiter waiter) {
        if (waiter.mAssignedConnection == null && waiter.mException == null) {
            ConnectionWaiter predecessor = null;
            for (ConnectionWaiter current = this.mConnectionWaiterQueue; current != waiter; current = current.mNext) {
                predecessor = current;
            }
            if (predecessor != null) {
                predecessor.mNext = waiter.mNext;
            } else {
                this.mConnectionWaiterQueue = waiter.mNext;
            }
            waiter.mException = new OperationCanceledException();
            LockSupport.unpark(waiter.mThread);
            wakeConnectionWaitersLocked();
        }
    }

    private void logConnectionPoolBusyLocked(long waitMillis, int connectionFlags) {
        Thread thread = Thread.currentThread();
        StringBuilder msg = new StringBuilder();
        msg.append("The connection pool for database '");
        msg.append(this.mConfiguration.label);
        msg.append("' has been unable to grant a connection to thread ");
        msg.append(thread.getId());
        msg.append(" (");
        msg.append(thread.getName());
        msg.append(") ");
        msg.append("with flags 0x");
        msg.append(Integer.toHexString(connectionFlags));
        msg.append(" for ");
        msg.append(((float) waitMillis) * 0.001f);
        msg.append(" seconds.\n");
        ArrayList<String> requests = new ArrayList<>();
        int activeConnections = 0;
        int idleConnections = 0;
        if (!this.mAcquiredConnections.isEmpty()) {
            for (SQLiteConnection connection : this.mAcquiredConnections.keySet()) {
                String description = connection.describeCurrentOperationUnsafe();
                if (description != null) {
                    requests.add(description);
                    activeConnections++;
                } else {
                    idleConnections++;
                }
            }
        }
        int availableConnections = this.mAvailableNonPrimaryConnections.size();
        if (this.mAvailablePrimaryConnection != null) {
            availableConnections++;
        }
        msg.append("Connections: ");
        msg.append(activeConnections);
        msg.append(" active, ");
        msg.append(idleConnections);
        msg.append(" idle, ");
        msg.append(availableConnections);
        msg.append(" available.\n");
        if (!requests.isEmpty()) {
            msg.append("\nRequests in progress:\n");
            Iterator<String> it = requests.iterator();
            while (it.hasNext()) {
                msg.append("  ");
                msg.append(it.next());
                msg.append("\n");
            }
        }
        Log.w(TAG, msg.toString());
    }

    @GuardedBy({"mLock"})
    private void wakeConnectionWaitersLocked() {
        ConnectionWaiter predecessor = null;
        ConnectionWaiter waiter = this.mConnectionWaiterQueue;
        boolean primaryConnectionNotAvailable = false;
        boolean nonPrimaryConnectionNotAvailable = false;
        while (waiter != null) {
            boolean unpark = false;
            if (!this.mIsOpen) {
                unpark = true;
            } else {
                SQLiteConnection connection = null;
                try {
                    if (!waiter.mWantPrimaryConnection && !nonPrimaryConnectionNotAvailable && (connection = tryAcquireNonPrimaryConnectionLocked(waiter.mSql, waiter.mConnectionFlags)) == null) {
                        nonPrimaryConnectionNotAvailable = true;
                    }
                    if (connection == null && !primaryConnectionNotAvailable && (connection = tryAcquirePrimaryConnectionLocked(waiter.mConnectionFlags)) == null) {
                        primaryConnectionNotAvailable = true;
                    }
                    if (connection != null) {
                        waiter.mAssignedConnection = connection;
                        unpark = true;
                    } else if (nonPrimaryConnectionNotAvailable && primaryConnectionNotAvailable) {
                        return;
                    }
                } catch (RuntimeException ex) {
                    waiter.mException = ex;
                    unpark = true;
                }
            }
            ConnectionWaiter successor = waiter.mNext;
            if (unpark) {
                if (predecessor != null) {
                    predecessor.mNext = successor;
                } else {
                    this.mConnectionWaiterQueue = successor;
                }
                waiter.mNext = null;
                LockSupport.unpark(waiter.mThread);
            } else {
                predecessor = waiter;
            }
            waiter = successor;
        }
    }

    @GuardedBy({"mLock"})
    private SQLiteConnection tryAcquirePrimaryConnectionLocked(int connectionFlags) {
        SQLiteConnection connection = this.mAvailablePrimaryConnection;
        if (connection != null) {
            this.mAvailablePrimaryConnection = null;
            finishAcquireConnectionLocked(connection, connectionFlags);
            return connection;
        }
        for (SQLiteConnection acquiredConnection : this.mAcquiredConnections.keySet()) {
            if (acquiredConnection.isPrimaryConnection()) {
                return null;
            }
        }
        SQLiteConnection connection2 = openConnectionLocked(this.mConfiguration, true);
        finishAcquireConnectionLocked(connection2, connectionFlags);
        return connection2;
    }

    @GuardedBy({"mLock"})
    private SQLiteConnection tryAcquireNonPrimaryConnectionLocked(String sql, int connectionFlags) {
        int availableCount = this.mAvailableNonPrimaryConnections.size();
        if (availableCount > 1 && sql != null) {
            for (int i = 0; i < availableCount; i++) {
                SQLiteConnection connection = this.mAvailableNonPrimaryConnections.get(i);
                if (connection.isPreparedStatementInCache(sql)) {
                    this.mAvailableNonPrimaryConnections.remove(i);
                    finishAcquireConnectionLocked(connection, connectionFlags);
                    return connection;
                }
            }
        }
        if (availableCount > 0) {
            SQLiteConnection connection2 = this.mAvailableNonPrimaryConnections.remove(availableCount - 1);
            finishAcquireConnectionLocked(connection2, connectionFlags);
            return connection2;
        }
        int openConnections = this.mAcquiredConnections.size();
        if (this.mAvailablePrimaryConnection != null) {
            openConnections++;
        }
        if (openConnections >= this.mMaxConnectionPoolSize) {
            return null;
        }
        SQLiteConnection connection3 = openConnectionLocked(this.mConfiguration, false);
        finishAcquireConnectionLocked(connection3, connectionFlags);
        return connection3;
    }

    @GuardedBy({"mLock"})
    private void finishAcquireConnectionLocked(SQLiteConnection connection, int connectionFlags) {
        try {
            connection.setOnlyAllowReadOnlyOperations((connectionFlags & 1) != 0);
            this.mAcquiredConnections.put(connection, AcquiredConnectionStatus.NORMAL);
        } catch (RuntimeException ex) {
            Log.e(TAG, "Failed to prepare acquired connection for session, closing it: " + connection + ", connectionFlags=" + connectionFlags);
            closeConnectionAndLogExceptionsLocked(connection);
            throw ex;
        }
    }

    private boolean isSessionBlockingImportantConnectionWaitersLocked(boolean holdingPrimaryConnection, int connectionFlags) {
        ConnectionWaiter waiter = this.mConnectionWaiterQueue;
        if (waiter == null) {
            return false;
        }
        int priority = getPriority(connectionFlags);
        while (priority <= waiter.mPriority) {
            if (holdingPrimaryConnection || !waiter.mWantPrimaryConnection) {
                return true;
            }
            waiter = waiter.mNext;
            if (waiter == null) {
                return false;
            }
        }
        return false;
    }

    private static int getPriority(int connectionFlags) {
        return (connectionFlags & 4) != 0 ? 1 : 0;
    }

    private void setMaxConnectionPoolSizeLocked() {
        if (this.mConfiguration.isInMemoryDb() || (this.mConfiguration.openFlags & 536870912) == 0) {
            this.mMaxConnectionPoolSize = 1;
        } else {
            this.mMaxConnectionPoolSize = SQLiteGlobal.getWALConnectionPoolSize();
        }
    }

    @VisibleForTesting
    public void setupIdleConnectionHandler(Looper looper, long timeoutMs) {
        synchronized (this.mLock) {
            this.mIdleConnectionHandler = new IdleConnectionHandler(looper, timeoutMs);
        }
    }

    /* access modifiers changed from: package-private */
    public void disableIdleConnectionHandler() {
        synchronized (this.mLock) {
            this.mIdleConnectionHandler = null;
        }
    }

    private void throwIfClosedLocked() {
        if (!this.mIsOpen) {
            throw new IllegalStateException("Cannot perform this operation because the connection pool has been closed.");
        }
    }

    private ConnectionWaiter obtainConnectionWaiterLocked(Thread thread, long startTime, int priority, boolean wantPrimaryConnection, String sql, int connectionFlags) {
        ConnectionWaiter waiter = this.mConnectionWaiterPool;
        if (waiter != null) {
            this.mConnectionWaiterPool = waiter.mNext;
            waiter.mNext = null;
        } else {
            waiter = new ConnectionWaiter();
        }
        waiter.mThread = thread;
        waiter.mStartTime = startTime;
        waiter.mPriority = priority;
        waiter.mWantPrimaryConnection = wantPrimaryConnection;
        waiter.mSql = sql;
        waiter.mConnectionFlags = connectionFlags;
        return waiter;
    }

    private void recycleConnectionWaiterLocked(ConnectionWaiter waiter) {
        waiter.mNext = this.mConnectionWaiterPool;
        waiter.mThread = null;
        waiter.mSql = null;
        waiter.mAssignedConnection = null;
        waiter.mException = null;
        waiter.mNonce++;
        this.mConnectionWaiterPool = waiter;
    }

    public void dump(Printer printer, boolean verbose, ArraySet<String> directories) {
        Printer indentedPrinter = PrefixPrinter.create(printer, "    ");
        synchronized (this.mLock) {
            if (directories != null) {
                try {
                    directories.add(new File(this.mConfiguration.path).getParent());
                } catch (Throwable th) {
                    throw th;
                }
            }
            boolean isCompatibilityWalEnabled = this.mConfiguration.isLegacyCompatibilityWalEnabled();
            printer.println("Connection pool for " + this.mConfiguration.path + SettingsStringUtil.DELIMITER);
            StringBuilder sb = new StringBuilder();
            sb.append("  Open: ");
            sb.append(this.mIsOpen);
            printer.println(sb.toString());
            printer.println("  Max connections: " + this.mMaxConnectionPoolSize);
            printer.println("  Total execution time: " + this.mTotalExecutionTimeCounter);
            printer.println("  Configuration: openFlags=" + this.mConfiguration.openFlags + ", isLegacyCompatibilityWalEnabled=" + isCompatibilityWalEnabled + ", journalMode=" + TextUtils.emptyIfNull(this.mConfiguration.journalMode) + ", syncMode=" + TextUtils.emptyIfNull(this.mConfiguration.syncMode));
            if (isCompatibilityWalEnabled) {
                printer.println("  Compatibility WAL enabled: wal_syncmode=" + SQLiteCompatibilityWalFlags.getWALSyncMode());
            }
            if (this.mConfiguration.isLookasideConfigSet()) {
                printer.println("  Lookaside config: sz=" + this.mConfiguration.lookasideSlotSize + " cnt=" + this.mConfiguration.lookasideSlotCount);
            }
            if (this.mConfiguration.idleConnectionTimeoutMs != Long.MAX_VALUE) {
                printer.println("  Idle connection timeout: " + this.mConfiguration.idleConnectionTimeoutMs);
            }
            printer.println("  Available primary connection:");
            if (this.mAvailablePrimaryConnection != null) {
                this.mAvailablePrimaryConnection.dump(indentedPrinter, verbose);
            } else {
                indentedPrinter.println("<none>");
            }
            printer.println("  Available non-primary connections:");
            if (!this.mAvailableNonPrimaryConnections.isEmpty()) {
                int count = this.mAvailableNonPrimaryConnections.size();
                for (int i = 0; i < count; i++) {
                    this.mAvailableNonPrimaryConnections.get(i).dump(indentedPrinter, verbose);
                }
            } else {
                indentedPrinter.println("<none>");
            }
            printer.println("  Acquired connections:");
            if (!this.mAcquiredConnections.isEmpty()) {
                for (Map.Entry<SQLiteConnection, AcquiredConnectionStatus> entry : this.mAcquiredConnections.entrySet()) {
                    entry.getKey().dumpUnsafe(indentedPrinter, verbose);
                    indentedPrinter.println("  Status: " + entry.getValue());
                }
            } else {
                indentedPrinter.println("<none>");
            }
            printer.println("  Connection waiters:");
            if (this.mConnectionWaiterQueue != null) {
                int i2 = 0;
                long now = SystemClock.uptimeMillis();
                ConnectionWaiter waiter = this.mConnectionWaiterQueue;
                while (waiter != null) {
                    indentedPrinter.println(i2 + ": waited for " + (((float) (now - waiter.mStartTime)) * 0.001f) + " ms - thread=" + waiter.mThread + ", priority=" + waiter.mPriority + ", sql='" + waiter.mSql + "'");
                    waiter = waiter.mNext;
                    i2++;
                }
            } else {
                indentedPrinter.println("<none>");
            }
        }
    }

    public String toString() {
        return "SQLiteConnectionPool: " + this.mConfiguration.path;
    }

    public String getPath() {
        return this.mConfiguration.path;
    }

    private static final class ConnectionWaiter {
        public SQLiteConnection mAssignedConnection;
        public int mConnectionFlags;
        public RuntimeException mException;
        public ConnectionWaiter mNext;
        public int mNonce;
        public int mPriority;
        public String mSql;
        public long mStartTime;
        public Thread mThread;
        public boolean mWantPrimaryConnection;

        private ConnectionWaiter() {
        }
    }

    private class IdleConnectionHandler extends Handler {
        private final long mTimeout;

        IdleConnectionHandler(Looper looper, long timeout) {
            super(looper);
            this.mTimeout = timeout;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:13:0x0057, code lost:
            return;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void handleMessage(android.os.Message r6) {
            /*
                r5 = this;
                android.database.sqlite.SQLiteConnectionPool r0 = android.database.sqlite.SQLiteConnectionPool.this
                java.lang.Object r0 = r0.mLock
                monitor-enter(r0)
                android.database.sqlite.SQLiteConnectionPool r1 = android.database.sqlite.SQLiteConnectionPool.this     // Catch:{ all -> 0x0058 }
                android.database.sqlite.SQLiteConnectionPool$IdleConnectionHandler r1 = r1.mIdleConnectionHandler     // Catch:{ all -> 0x0058 }
                if (r5 == r1) goto L_0x0011
                monitor-exit(r0)     // Catch:{ all -> 0x0058 }
                return
            L_0x0011:
                android.database.sqlite.SQLiteConnectionPool r1 = android.database.sqlite.SQLiteConnectionPool.this     // Catch:{ all -> 0x0058 }
                int r2 = r6.what     // Catch:{ all -> 0x0058 }
                boolean r1 = r1.closeAvailableConnectionLocked(r2)     // Catch:{ all -> 0x0058 }
                if (r1 == 0) goto L_0x0056
                java.lang.String r1 = "SQLiteConnectionPool"
                r2 = 3
                boolean r1 = android.util.Log.isLoggable(r1, r2)     // Catch:{ all -> 0x0058 }
                if (r1 == 0) goto L_0x0056
                java.lang.String r1 = "SQLiteConnectionPool"
                java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x0058 }
                r2.<init>()     // Catch:{ all -> 0x0058 }
                java.lang.String r3 = "Closed idle connection "
                r2.append(r3)     // Catch:{ all -> 0x0058 }
                android.database.sqlite.SQLiteConnectionPool r3 = android.database.sqlite.SQLiteConnectionPool.this     // Catch:{ all -> 0x0058 }
                android.database.sqlite.SQLiteDatabaseConfiguration r3 = r3.mConfiguration     // Catch:{ all -> 0x0058 }
                java.lang.String r3 = r3.label     // Catch:{ all -> 0x0058 }
                r2.append(r3)     // Catch:{ all -> 0x0058 }
                java.lang.String r3 = " "
                r2.append(r3)     // Catch:{ all -> 0x0058 }
                int r3 = r6.what     // Catch:{ all -> 0x0058 }
                r2.append(r3)     // Catch:{ all -> 0x0058 }
                java.lang.String r3 = " after "
                r2.append(r3)     // Catch:{ all -> 0x0058 }
                long r3 = r5.mTimeout     // Catch:{ all -> 0x0058 }
                r2.append(r3)     // Catch:{ all -> 0x0058 }
                java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x0058 }
                android.util.Log.d(r1, r2)     // Catch:{ all -> 0x0058 }
            L_0x0056:
                monitor-exit(r0)     // Catch:{ all -> 0x0058 }
                return
            L_0x0058:
                r1 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x0058 }
                throw r1
            */
            throw new UnsupportedOperationException("Method not decompiled: android.database.sqlite.SQLiteConnectionPool.IdleConnectionHandler.handleMessage(android.os.Message):void");
        }

        /* access modifiers changed from: package-private */
        public void connectionReleased(SQLiteConnection con) {
            sendEmptyMessageDelayed(con.getConnectionId(), this.mTimeout);
        }

        /* access modifiers changed from: package-private */
        public void connectionAcquired(SQLiteConnection con) {
            removeMessages(con.getConnectionId());
        }

        /* access modifiers changed from: package-private */
        public void connectionClosed(SQLiteConnection con) {
            removeMessages(con.getConnectionId());
        }
    }
}
