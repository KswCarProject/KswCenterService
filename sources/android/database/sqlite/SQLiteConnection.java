package android.database.sqlite;

import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDebug;
import android.net.wifi.WifiEnterpriseConfig;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.os.SystemClock;
import android.os.Trace;
import android.provider.SettingsStringUtil;
import android.security.keymaster.KeymasterDefs;
import android.util.Log;
import android.util.LruCache;
import android.util.Printer;
import dalvik.system.BlockGuard;
import dalvik.system.CloseGuard;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public final class SQLiteConnection implements CancellationSignal.OnCancelListener {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final boolean DEBUG = false;
    /* access modifiers changed from: private */
    public static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
    private static final String[] EMPTY_STRING_ARRAY = new String[0];
    private static final String TAG = "SQLiteConnection";
    private int mCancellationSignalAttachCount;
    private final CloseGuard mCloseGuard = CloseGuard.get();
    private final SQLiteDatabaseConfiguration mConfiguration;
    private final int mConnectionId;
    private long mConnectionPtr;
    private final boolean mIsPrimaryConnection;
    private final boolean mIsReadOnlyConnection;
    private boolean mOnlyAllowReadOnlyOperations;
    private final SQLiteConnectionPool mPool;
    private final PreparedStatementCache mPreparedStatementCache;
    private PreparedStatement mPreparedStatementPool;
    private final OperationLog mRecentOperations;

    private static native void nativeBindBlob(long j, long j2, int i, byte[] bArr);

    private static native void nativeBindDouble(long j, long j2, int i, double d);

    private static native void nativeBindLong(long j, long j2, int i, long j3);

    private static native void nativeBindNull(long j, long j2, int i);

    private static native void nativeBindString(long j, long j2, int i, String str);

    private static native void nativeCancel(long j);

    private static native void nativeClose(long j);

    private static native void nativeExecute(long j, long j2);

    private static native int nativeExecuteForBlobFileDescriptor(long j, long j2);

    private static native int nativeExecuteForChangedRowCount(long j, long j2);

    private static native long nativeExecuteForCursorWindow(long j, long j2, long j3, int i, int i2, boolean z);

    private static native long nativeExecuteForLastInsertedRowId(long j, long j2);

    private static native long nativeExecuteForLong(long j, long j2);

    private static native String nativeExecuteForString(long j, long j2);

    private static native void nativeFinalizeStatement(long j, long j2);

    private static native int nativeGetColumnCount(long j, long j2);

    private static native String nativeGetColumnName(long j, long j2, int i);

    private static native int nativeGetDbLookaside(long j);

    private static native int nativeGetParameterCount(long j, long j2);

    private static native boolean nativeIsReadOnly(long j, long j2);

    private static native long nativeOpen(String str, int i, String str2, boolean z, boolean z2, int i2, int i3);

    private static native long nativePrepareStatement(long j, String str);

    private static native void nativeRegisterCustomFunction(long j, SQLiteCustomFunction sQLiteCustomFunction);

    private static native void nativeRegisterLocalizedCollators(long j, String str);

    private static native void nativeResetCancel(long j, boolean z);

    private static native void nativeResetStatementAndClearBindings(long j, long j2);

    private SQLiteConnection(SQLiteConnectionPool pool, SQLiteDatabaseConfiguration configuration, int connectionId, boolean primaryConnection) {
        this.mPool = pool;
        this.mRecentOperations = new OperationLog(this.mPool);
        this.mConfiguration = new SQLiteDatabaseConfiguration(configuration);
        this.mConnectionId = connectionId;
        this.mIsPrimaryConnection = primaryConnection;
        this.mIsReadOnlyConnection = (configuration.openFlags & 1) == 0 ? false : true;
        this.mPreparedStatementCache = new PreparedStatementCache(this.mConfiguration.maxSqlCacheSize);
        this.mCloseGuard.open("close");
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        try {
            if (!(this.mPool == null || this.mConnectionPtr == 0)) {
                this.mPool.onConnectionLeaked();
            }
            dispose(true);
        } finally {
            super.finalize();
        }
    }

    static SQLiteConnection open(SQLiteConnectionPool pool, SQLiteDatabaseConfiguration configuration, int connectionId, boolean primaryConnection) {
        SQLiteConnection connection = new SQLiteConnection(pool, configuration, connectionId, primaryConnection);
        try {
            connection.open();
            return connection;
        } catch (SQLiteException ex) {
            connection.dispose(false);
            throw ex;
        }
    }

    /* access modifiers changed from: package-private */
    public void close() {
        dispose(false);
    }

    /* JADX INFO: finally extract failed */
    private void open() {
        int cookie = this.mRecentOperations.beginOperation("open", (String) null, (Object[]) null);
        try {
            this.mConnectionPtr = nativeOpen(this.mConfiguration.path, this.mConfiguration.openFlags, this.mConfiguration.label, SQLiteDebug.NoPreloadHolder.DEBUG_SQL_STATEMENTS, SQLiteDebug.NoPreloadHolder.DEBUG_SQL_TIME, this.mConfiguration.lookasideSlotSize, this.mConfiguration.lookasideSlotCount);
            this.mRecentOperations.endOperation(cookie);
            setPageSize();
            setForeignKeyModeFromConfiguration();
            setWalModeFromConfiguration();
            setJournalSizeLimit();
            setAutoCheckpointInterval();
            setLocaleFromConfiguration();
            int functionCount = this.mConfiguration.customFunctions.size();
            for (int i = 0; i < functionCount; i++) {
                nativeRegisterCustomFunction(this.mConnectionPtr, this.mConfiguration.customFunctions.get(i));
            }
        } catch (Throwable th) {
            this.mRecentOperations.endOperation(cookie);
            throw th;
        }
    }

    private void dispose(boolean finalized) {
        if (this.mCloseGuard != null) {
            if (finalized) {
                this.mCloseGuard.warnIfOpen();
            }
            this.mCloseGuard.close();
        }
        if (this.mConnectionPtr != 0) {
            int cookie = this.mRecentOperations.beginOperation("close", (String) null, (Object[]) null);
            try {
                this.mPreparedStatementCache.evictAll();
                nativeClose(this.mConnectionPtr);
                this.mConnectionPtr = 0;
            } finally {
                this.mRecentOperations.endOperation(cookie);
            }
        }
    }

    private void setPageSize() {
        if (!this.mConfiguration.isInMemoryDb() && !this.mIsReadOnlyConnection) {
            long newValue = (long) SQLiteGlobal.getDefaultPageSize();
            if (executeForLong("PRAGMA page_size", (Object[]) null, (CancellationSignal) null) != newValue) {
                execute("PRAGMA page_size=" + newValue, (Object[]) null, (CancellationSignal) null);
            }
        }
    }

    private void setAutoCheckpointInterval() {
        if (!this.mConfiguration.isInMemoryDb() && !this.mIsReadOnlyConnection) {
            long newValue = (long) SQLiteGlobal.getWALAutoCheckpoint();
            if (executeForLong("PRAGMA wal_autocheckpoint", (Object[]) null, (CancellationSignal) null) != newValue) {
                executeForLong("PRAGMA wal_autocheckpoint=" + newValue, (Object[]) null, (CancellationSignal) null);
            }
        }
    }

    private void setJournalSizeLimit() {
        if (!this.mConfiguration.isInMemoryDb() && !this.mIsReadOnlyConnection) {
            long newValue = (long) SQLiteGlobal.getJournalSizeLimit();
            if (executeForLong("PRAGMA journal_size_limit", (Object[]) null, (CancellationSignal) null) != newValue) {
                executeForLong("PRAGMA journal_size_limit=" + newValue, (Object[]) null, (CancellationSignal) null);
            }
        }
    }

    private void setForeignKeyModeFromConfiguration() {
        if (!this.mIsReadOnlyConnection) {
            long newValue = this.mConfiguration.foreignKeyConstraintsEnabled ? 1 : 0;
            if (executeForLong("PRAGMA foreign_keys", (Object[]) null, (CancellationSignal) null) != newValue) {
                execute("PRAGMA foreign_keys=" + newValue, (Object[]) null, (CancellationSignal) null);
            }
        }
    }

    private void setWalModeFromConfiguration() {
        if (!this.mConfiguration.isInMemoryDb() && !this.mIsReadOnlyConnection) {
            boolean walEnabled = (this.mConfiguration.openFlags & 536870912) != 0;
            boolean isCompatibilityWalEnabled = this.mConfiguration.isLegacyCompatibilityWalEnabled();
            if (walEnabled || isCompatibilityWalEnabled) {
                setJournalMode("WAL");
                if (this.mConfiguration.syncMode != null) {
                    setSyncMode(this.mConfiguration.syncMode);
                } else if (isCompatibilityWalEnabled) {
                    setSyncMode(SQLiteCompatibilityWalFlags.getWALSyncMode());
                } else {
                    setSyncMode(SQLiteGlobal.getWALSyncMode());
                }
                maybeTruncateWalFile();
                return;
            }
            setJournalMode(this.mConfiguration.journalMode == null ? SQLiteGlobal.getDefaultJournalMode() : this.mConfiguration.journalMode);
            setSyncMode(this.mConfiguration.syncMode == null ? SQLiteGlobal.getDefaultSyncMode() : this.mConfiguration.syncMode);
        }
    }

    private void maybeTruncateWalFile() {
        long threshold = SQLiteGlobal.getWALTruncateSize();
        if (threshold != 0) {
            File walFile = new File(this.mConfiguration.path + "-wal");
            if (walFile.isFile()) {
                long size = walFile.length();
                if (size >= threshold) {
                    Log.i(TAG, walFile.getAbsolutePath() + WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER + size + " bytes: Bigger than " + threshold + "; truncating");
                    try {
                        executeForString("PRAGMA wal_checkpoint(TRUNCATE)", (Object[]) null, (CancellationSignal) null);
                    } catch (SQLiteException e) {
                        Log.w(TAG, "Failed to truncate the -wal file", e);
                    }
                }
            }
        }
    }

    private void setSyncMode(String newValue) {
        if (!canonicalizeSyncMode(executeForString("PRAGMA synchronous", (Object[]) null, (CancellationSignal) null)).equalsIgnoreCase(canonicalizeSyncMode(newValue))) {
            execute("PRAGMA synchronous=" + newValue, (Object[]) null, (CancellationSignal) null);
        }
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String canonicalizeSyncMode(java.lang.String r1) {
        /*
            int r0 = r1.hashCode()
            switch(r0) {
                case 48: goto L_0x001c;
                case 49: goto L_0x0012;
                case 50: goto L_0x0008;
                default: goto L_0x0007;
            }
        L_0x0007:
            goto L_0x0026
        L_0x0008:
            java.lang.String r0 = "2"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x0026
            r0 = 2
            goto L_0x0027
        L_0x0012:
            java.lang.String r0 = "1"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x0026
            r0 = 1
            goto L_0x0027
        L_0x001c:
            java.lang.String r0 = "0"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x0026
            r0 = 0
            goto L_0x0027
        L_0x0026:
            r0 = -1
        L_0x0027:
            switch(r0) {
                case 0: goto L_0x0031;
                case 1: goto L_0x002e;
                case 2: goto L_0x002b;
                default: goto L_0x002a;
            }
        L_0x002a:
            return r1
        L_0x002b:
            java.lang.String r0 = "FULL"
            return r0
        L_0x002e:
            java.lang.String r0 = "NORMAL"
            return r0
        L_0x0031:
            java.lang.String r0 = "OFF"
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.database.sqlite.SQLiteConnection.canonicalizeSyncMode(java.lang.String):java.lang.String");
    }

    private void setJournalMode(String newValue) {
        String value = executeForString("PRAGMA journal_mode", (Object[]) null, (CancellationSignal) null);
        if (!value.equalsIgnoreCase(newValue)) {
            try {
                if (executeForString("PRAGMA journal_mode=" + newValue, (Object[]) null, (CancellationSignal) null).equalsIgnoreCase(newValue)) {
                    return;
                }
            } catch (SQLiteDatabaseLockedException e) {
            }
            Log.w(TAG, "Could not change the database journal mode of '" + this.mConfiguration.label + "' from '" + value + "' to '" + newValue + "' because the database is locked.  This usually means that there are other open connections to the database which prevents the database from enabling or disabling write-ahead logging mode.  Proceeding without changing the journal mode.");
        }
    }

    private void setLocaleFromConfiguration() {
        if ((this.mConfiguration.openFlags & 16) == 0) {
            String newLocale = this.mConfiguration.locale.toString();
            nativeRegisterLocalizedCollators(this.mConnectionPtr, newLocale);
            if (!this.mConfiguration.isInMemoryDb()) {
                checkDatabaseWiped();
            }
            if (!this.mIsReadOnlyConnection) {
                try {
                    execute("CREATE TABLE IF NOT EXISTS android_metadata (locale TEXT)", (Object[]) null, (CancellationSignal) null);
                    String oldLocale = executeForString("SELECT locale FROM android_metadata UNION SELECT NULL ORDER BY locale DESC LIMIT 1", (Object[]) null, (CancellationSignal) null);
                    if (oldLocale == null || !oldLocale.equals(newLocale)) {
                        execute("BEGIN", (Object[]) null, (CancellationSignal) null);
                        execute("DELETE FROM android_metadata", (Object[]) null, (CancellationSignal) null);
                        execute("INSERT INTO android_metadata (locale) VALUES(?)", new Object[]{newLocale}, (CancellationSignal) null);
                        execute("REINDEX LOCALIZED", (Object[]) null, (CancellationSignal) null);
                        execute(1 != 0 ? "COMMIT" : "ROLLBACK", (Object[]) null, (CancellationSignal) null);
                    }
                } catch (SQLiteException ex) {
                    throw ex;
                } catch (RuntimeException ex2) {
                    throw new SQLiteException("Failed to change locale for db '" + this.mConfiguration.label + "' to '" + newLocale + "'.", ex2);
                } catch (Throwable th) {
                    execute(0 != 0 ? "COMMIT" : "ROLLBACK", (Object[]) null, (CancellationSignal) null);
                    throw th;
                }
            }
        }
    }

    private void checkDatabaseWiped() {
        if (SQLiteGlobal.checkDbWipe()) {
            try {
                File checkFile = new File(this.mConfiguration.path + "-wipecheck");
                boolean hasMetadataTable = executeForLong("SELECT count(*) FROM sqlite_master WHERE type='table' AND name='android_metadata'", (Object[]) null, (CancellationSignal) null) > 0;
                boolean hasCheckFile = checkFile.exists();
                if (!this.mIsReadOnlyConnection && !hasCheckFile) {
                    checkFile.createNewFile();
                }
                if (!hasMetadataTable && hasCheckFile) {
                    SQLiteDatabase.wipeDetected(this.mConfiguration.path, "unknown");
                }
            } catch (IOException | RuntimeException ex) {
                SQLiteDatabase.wtfAsSystemServer(TAG, "Unexpected exception while checking for wipe", ex);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void reconfigure(SQLiteDatabaseConfiguration configuration) {
        boolean walModeChanged = false;
        this.mOnlyAllowReadOnlyOperations = false;
        int functionCount = configuration.customFunctions.size();
        for (int i = 0; i < functionCount; i++) {
            SQLiteCustomFunction function = configuration.customFunctions.get(i);
            if (!this.mConfiguration.customFunctions.contains(function)) {
                nativeRegisterCustomFunction(this.mConnectionPtr, function);
            }
        }
        boolean foreignKeyModeChanged = configuration.foreignKeyConstraintsEnabled != this.mConfiguration.foreignKeyConstraintsEnabled;
        if (((configuration.openFlags ^ this.mConfiguration.openFlags) & KeymasterDefs.KM_ULONG_REP) != 0) {
            walModeChanged = true;
        }
        boolean localeChanged = !configuration.locale.equals(this.mConfiguration.locale);
        this.mConfiguration.updateParametersFrom(configuration);
        this.mPreparedStatementCache.resize(configuration.maxSqlCacheSize);
        if (foreignKeyModeChanged) {
            setForeignKeyModeFromConfiguration();
        }
        if (walModeChanged) {
            setWalModeFromConfiguration();
        }
        if (localeChanged) {
            setLocaleFromConfiguration();
        }
    }

    /* access modifiers changed from: package-private */
    public void setOnlyAllowReadOnlyOperations(boolean readOnly) {
        this.mOnlyAllowReadOnlyOperations = readOnly;
    }

    /* access modifiers changed from: package-private */
    public boolean isPreparedStatementInCache(String sql) {
        return this.mPreparedStatementCache.get(sql) != null;
    }

    public int getConnectionId() {
        return this.mConnectionId;
    }

    public boolean isPrimaryConnection() {
        return this.mIsPrimaryConnection;
    }

    public void prepare(String sql, SQLiteStatementInfo outStatementInfo) {
        PreparedStatement statement;
        if (sql != null) {
            int cookie = this.mRecentOperations.beginOperation("prepare", sql, (Object[]) null);
            try {
                statement = acquirePreparedStatement(sql);
                if (outStatementInfo != null) {
                    outStatementInfo.numParameters = statement.mNumParameters;
                    outStatementInfo.readOnly = statement.mReadOnly;
                    int columnCount = nativeGetColumnCount(this.mConnectionPtr, statement.mStatementPtr);
                    if (columnCount == 0) {
                        outStatementInfo.columnNames = EMPTY_STRING_ARRAY;
                    } else {
                        outStatementInfo.columnNames = new String[columnCount];
                        for (int i = 0; i < columnCount; i++) {
                            outStatementInfo.columnNames[i] = nativeGetColumnName(this.mConnectionPtr, statement.mStatementPtr, i);
                        }
                    }
                }
                releasePreparedStatement(statement);
                this.mRecentOperations.endOperation(cookie);
            } catch (RuntimeException ex) {
                try {
                    this.mRecentOperations.failOperation(cookie, ex);
                    throw ex;
                } catch (Throwable th) {
                    this.mRecentOperations.endOperation(cookie);
                    throw th;
                }
            } catch (Throwable th2) {
                releasePreparedStatement(statement);
                throw th2;
            }
        } else {
            throw new IllegalArgumentException("sql must not be null.");
        }
    }

    public void execute(String sql, Object[] bindArgs, CancellationSignal cancellationSignal) {
        if (sql != null) {
            int cookie = this.mRecentOperations.beginOperation("execute", sql, bindArgs);
            try {
                PreparedStatement statement = acquirePreparedStatement(sql);
                try {
                    throwIfStatementForbidden(statement);
                    bindArguments(statement, bindArgs);
                    applyBlockGuardPolicy(statement);
                    attachCancellationSignal(cancellationSignal);
                    nativeExecute(this.mConnectionPtr, statement.mStatementPtr);
                    detachCancellationSignal(cancellationSignal);
                    releasePreparedStatement(statement);
                    this.mRecentOperations.endOperation(cookie);
                } catch (Throwable th) {
                    releasePreparedStatement(statement);
                    throw th;
                }
            } catch (RuntimeException ex) {
                try {
                    this.mRecentOperations.failOperation(cookie, ex);
                    throw ex;
                } catch (Throwable th2) {
                    this.mRecentOperations.endOperation(cookie);
                    throw th2;
                }
            }
        } else {
            throw new IllegalArgumentException("sql must not be null.");
        }
    }

    public long executeForLong(String sql, Object[] bindArgs, CancellationSignal cancellationSignal) {
        if (sql != null) {
            int cookie = this.mRecentOperations.beginOperation("executeForLong", sql, bindArgs);
            try {
                PreparedStatement statement = acquirePreparedStatement(sql);
                try {
                    throwIfStatementForbidden(statement);
                    bindArguments(statement, bindArgs);
                    applyBlockGuardPolicy(statement);
                    attachCancellationSignal(cancellationSignal);
                    long ret = nativeExecuteForLong(this.mConnectionPtr, statement.mStatementPtr);
                    this.mRecentOperations.setResult(ret);
                    detachCancellationSignal(cancellationSignal);
                    releasePreparedStatement(statement);
                    this.mRecentOperations.endOperation(cookie);
                    return ret;
                } catch (Throwable th) {
                    releasePreparedStatement(statement);
                    throw th;
                }
            } catch (RuntimeException ex) {
                try {
                    this.mRecentOperations.failOperation(cookie, ex);
                    throw ex;
                } catch (Throwable th2) {
                    this.mRecentOperations.endOperation(cookie);
                    throw th2;
                }
            }
        } else {
            throw new IllegalArgumentException("sql must not be null.");
        }
    }

    public String executeForString(String sql, Object[] bindArgs, CancellationSignal cancellationSignal) {
        if (sql != null) {
            int cookie = this.mRecentOperations.beginOperation("executeForString", sql, bindArgs);
            try {
                PreparedStatement statement = acquirePreparedStatement(sql);
                try {
                    throwIfStatementForbidden(statement);
                    bindArguments(statement, bindArgs);
                    applyBlockGuardPolicy(statement);
                    attachCancellationSignal(cancellationSignal);
                    String ret = nativeExecuteForString(this.mConnectionPtr, statement.mStatementPtr);
                    this.mRecentOperations.setResult(ret);
                    detachCancellationSignal(cancellationSignal);
                    releasePreparedStatement(statement);
                    this.mRecentOperations.endOperation(cookie);
                    return ret;
                } catch (Throwable th) {
                    releasePreparedStatement(statement);
                    throw th;
                }
            } catch (RuntimeException ex) {
                try {
                    this.mRecentOperations.failOperation(cookie, ex);
                    throw ex;
                } catch (Throwable th2) {
                    this.mRecentOperations.endOperation(cookie);
                    throw th2;
                }
            }
        } else {
            throw new IllegalArgumentException("sql must not be null.");
        }
    }

    public ParcelFileDescriptor executeForBlobFileDescriptor(String sql, Object[] bindArgs, CancellationSignal cancellationSignal) {
        if (sql != null) {
            int cookie = this.mRecentOperations.beginOperation("executeForBlobFileDescriptor", sql, bindArgs);
            try {
                PreparedStatement statement = acquirePreparedStatement(sql);
                try {
                    throwIfStatementForbidden(statement);
                    bindArguments(statement, bindArgs);
                    applyBlockGuardPolicy(statement);
                    attachCancellationSignal(cancellationSignal);
                    int fd = nativeExecuteForBlobFileDescriptor(this.mConnectionPtr, statement.mStatementPtr);
                    ParcelFileDescriptor adoptFd = fd >= 0 ? ParcelFileDescriptor.adoptFd(fd) : null;
                    detachCancellationSignal(cancellationSignal);
                    releasePreparedStatement(statement);
                    this.mRecentOperations.endOperation(cookie);
                    return adoptFd;
                } catch (Throwable th) {
                    releasePreparedStatement(statement);
                    throw th;
                }
            } catch (RuntimeException ex) {
                try {
                    this.mRecentOperations.failOperation(cookie, ex);
                    throw ex;
                } catch (Throwable th2) {
                    this.mRecentOperations.endOperation(cookie);
                    throw th2;
                }
            }
        } else {
            throw new IllegalArgumentException("sql must not be null.");
        }
    }

    public int executeForChangedRowCount(String sql, Object[] bindArgs, CancellationSignal cancellationSignal) {
        if (sql != null) {
            int changedRows = 0;
            int cookie = this.mRecentOperations.beginOperation("executeForChangedRowCount", sql, bindArgs);
            try {
                PreparedStatement statement = acquirePreparedStatement(sql);
                try {
                    throwIfStatementForbidden(statement);
                    bindArguments(statement, bindArgs);
                    applyBlockGuardPolicy(statement);
                    attachCancellationSignal(cancellationSignal);
                    changedRows = nativeExecuteForChangedRowCount(this.mConnectionPtr, statement.mStatementPtr);
                    detachCancellationSignal(cancellationSignal);
                    releasePreparedStatement(statement);
                    if (this.mRecentOperations.endOperationDeferLog(cookie)) {
                        OperationLog operationLog = this.mRecentOperations;
                        operationLog.logOperation(cookie, "changedRows=" + changedRows);
                    }
                    return changedRows;
                } catch (Throwable th) {
                    releasePreparedStatement(statement);
                    throw th;
                }
            } catch (RuntimeException ex) {
                try {
                    this.mRecentOperations.failOperation(cookie, ex);
                    throw ex;
                } catch (Throwable th2) {
                    if (this.mRecentOperations.endOperationDeferLog(cookie)) {
                        OperationLog operationLog2 = this.mRecentOperations;
                        operationLog2.logOperation(cookie, "changedRows=" + changedRows);
                    }
                    throw th2;
                }
            }
        } else {
            throw new IllegalArgumentException("sql must not be null.");
        }
    }

    public long executeForLastInsertedRowId(String sql, Object[] bindArgs, CancellationSignal cancellationSignal) {
        if (sql != null) {
            int cookie = this.mRecentOperations.beginOperation("executeForLastInsertedRowId", sql, bindArgs);
            try {
                PreparedStatement statement = acquirePreparedStatement(sql);
                try {
                    throwIfStatementForbidden(statement);
                    bindArguments(statement, bindArgs);
                    applyBlockGuardPolicy(statement);
                    attachCancellationSignal(cancellationSignal);
                    long nativeExecuteForLastInsertedRowId = nativeExecuteForLastInsertedRowId(this.mConnectionPtr, statement.mStatementPtr);
                    detachCancellationSignal(cancellationSignal);
                    releasePreparedStatement(statement);
                    this.mRecentOperations.endOperation(cookie);
                    return nativeExecuteForLastInsertedRowId;
                } catch (Throwable th) {
                    releasePreparedStatement(statement);
                    throw th;
                }
            } catch (RuntimeException ex) {
                try {
                    this.mRecentOperations.failOperation(cookie, ex);
                    throw ex;
                } catch (Throwable th2) {
                    this.mRecentOperations.endOperation(cookie);
                    throw th2;
                }
            }
        } else {
            throw new IllegalArgumentException("sql must not be null.");
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:67:0x00f1 A[Catch:{ all -> 0x0129 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int executeForCursorWindow(java.lang.String r20, java.lang.Object[] r21, android.database.CursorWindow r22, int r23, int r24, boolean r25, android.os.CancellationSignal r26) {
        /*
            r19 = this;
            r1 = r19
            r2 = r20
            r3 = r21
            r4 = r22
            r14 = r23
            r15 = r26
            if (r2 == 0) goto L_0x0137
            if (r4 == 0) goto L_0x012e
            r22.acquireReference()
            r16 = -1
            r17 = -1
            r18 = -1
            android.database.sqlite.SQLiteConnection$OperationLog r0 = r1.mRecentOperations     // Catch:{ all -> 0x0129 }
            java.lang.String r5 = "executeForCursorWindow"
            int r0 = r0.beginOperation(r5, r2, r3)     // Catch:{ all -> 0x0129 }
            r13 = r0
            android.database.sqlite.SQLiteConnection$PreparedStatement r0 = r19.acquirePreparedStatement(r20)     // Catch:{ RuntimeException -> 0x00da, all -> 0x00d1 }
            r12 = r0
            r1.throwIfStatementForbidden(r12)     // Catch:{ all -> 0x00c8 }
            r1.bindArguments(r12, r3)     // Catch:{ all -> 0x00c8 }
            r1.applyBlockGuardPolicy(r12)     // Catch:{ all -> 0x00c8 }
            r1.attachCancellationSignal(r15)     // Catch:{ all -> 0x00c8 }
            long r5 = r1.mConnectionPtr     // Catch:{ all -> 0x00bf }
            long r7 = r12.mStatementPtr     // Catch:{ all -> 0x00bf }
            long r9 = r4.mWindowPtr     // Catch:{ all -> 0x00bf }
            r11 = r23
            r2 = r12
            r12 = r24
            r3 = r13
            r13 = r25
            long r5 = nativeExecuteForCursorWindow(r5, r7, r9, r11, r12, r13)     // Catch:{ all -> 0x00bd }
            r0 = 32
            long r7 = r5 >> r0
            int r7 = (int) r7
            int r8 = (int) r5
            int r0 = r22.getNumRows()     // Catch:{ all -> 0x00b7 }
            r9 = r0
            r4.setStartPosition(r7)     // Catch:{ all -> 0x00af }
            r1.detachCancellationSignal(r15)     // Catch:{ all -> 0x00a7 }
            r1.releasePreparedStatement(r2)     // Catch:{ RuntimeException -> 0x009f, all -> 0x009d }
            android.database.sqlite.SQLiteConnection$OperationLog r0 = r1.mRecentOperations     // Catch:{ all -> 0x0129 }
            boolean r0 = r0.endOperationDeferLog(r3)     // Catch:{ all -> 0x0129 }
            if (r0 == 0) goto L_0x0099
            android.database.sqlite.SQLiteConnection$OperationLog r0 = r1.mRecentOperations     // Catch:{ all -> 0x0129 }
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ all -> 0x0129 }
            r10.<init>()     // Catch:{ all -> 0x0129 }
            java.lang.String r11 = "window='"
            r10.append(r11)     // Catch:{ all -> 0x0129 }
            r10.append(r4)     // Catch:{ all -> 0x0129 }
            java.lang.String r11 = "', startPos="
            r10.append(r11)     // Catch:{ all -> 0x0129 }
            r10.append(r14)     // Catch:{ all -> 0x0129 }
            java.lang.String r11 = ", actualPos="
            r10.append(r11)     // Catch:{ all -> 0x0129 }
            r10.append(r7)     // Catch:{ all -> 0x0129 }
            java.lang.String r11 = ", filledRows="
            r10.append(r11)     // Catch:{ all -> 0x0129 }
            r10.append(r9)     // Catch:{ all -> 0x0129 }
            java.lang.String r11 = ", countedRows="
            r10.append(r11)     // Catch:{ all -> 0x0129 }
            r10.append(r8)     // Catch:{ all -> 0x0129 }
            java.lang.String r10 = r10.toString()     // Catch:{ all -> 0x0129 }
            r0.logOperation(r3, r10)     // Catch:{ all -> 0x0129 }
        L_0x0099:
            r22.releaseReference()
            return r8
        L_0x009d:
            r0 = move-exception
            goto L_0x00e9
        L_0x009f:
            r0 = move-exception
            r16 = r7
            r17 = r8
            r18 = r9
            goto L_0x00dc
        L_0x00a7:
            r0 = move-exception
            r16 = r7
            r17 = r8
            r18 = r9
            goto L_0x00cb
        L_0x00af:
            r0 = move-exception
            r16 = r7
            r17 = r8
            r18 = r9
            goto L_0x00c2
        L_0x00b7:
            r0 = move-exception
            r16 = r7
            r17 = r8
            goto L_0x00c2
        L_0x00bd:
            r0 = move-exception
            goto L_0x00c2
        L_0x00bf:
            r0 = move-exception
            r2 = r12
            r3 = r13
        L_0x00c2:
            r1.detachCancellationSignal(r15)     // Catch:{ all -> 0x00c6 }
            throw r0     // Catch:{ all -> 0x00c6 }
        L_0x00c6:
            r0 = move-exception
            goto L_0x00cb
        L_0x00c8:
            r0 = move-exception
            r2 = r12
            r3 = r13
        L_0x00cb:
            r1.releasePreparedStatement(r2)     // Catch:{ RuntimeException -> 0x00cf }
            throw r0     // Catch:{ RuntimeException -> 0x00cf }
        L_0x00cf:
            r0 = move-exception
            goto L_0x00dc
        L_0x00d1:
            r0 = move-exception
            r3 = r13
            r7 = r16
            r8 = r17
            r9 = r18
            goto L_0x00e9
        L_0x00da:
            r0 = move-exception
            r3 = r13
        L_0x00dc:
            android.database.sqlite.SQLiteConnection$OperationLog r2 = r1.mRecentOperations     // Catch:{ all -> 0x00e2 }
            r2.failOperation(r3, r0)     // Catch:{ all -> 0x00e2 }
            throw r0     // Catch:{ all -> 0x00e2 }
        L_0x00e2:
            r0 = move-exception
            r7 = r16
            r8 = r17
            r9 = r18
        L_0x00e9:
            android.database.sqlite.SQLiteConnection$OperationLog r2 = r1.mRecentOperations     // Catch:{ all -> 0x0129 }
            boolean r2 = r2.endOperationDeferLog(r3)     // Catch:{ all -> 0x0129 }
            if (r2 == 0) goto L_0x0128
            android.database.sqlite.SQLiteConnection$OperationLog r2 = r1.mRecentOperations     // Catch:{ all -> 0x0129 }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x0129 }
            r5.<init>()     // Catch:{ all -> 0x0129 }
            java.lang.String r6 = "window='"
            r5.append(r6)     // Catch:{ all -> 0x0129 }
            r5.append(r4)     // Catch:{ all -> 0x0129 }
            java.lang.String r6 = "', startPos="
            r5.append(r6)     // Catch:{ all -> 0x0129 }
            r5.append(r14)     // Catch:{ all -> 0x0129 }
            java.lang.String r6 = ", actualPos="
            r5.append(r6)     // Catch:{ all -> 0x0129 }
            r5.append(r7)     // Catch:{ all -> 0x0129 }
            java.lang.String r6 = ", filledRows="
            r5.append(r6)     // Catch:{ all -> 0x0129 }
            r5.append(r9)     // Catch:{ all -> 0x0129 }
            java.lang.String r6 = ", countedRows="
            r5.append(r6)     // Catch:{ all -> 0x0129 }
            r5.append(r8)     // Catch:{ all -> 0x0129 }
            java.lang.String r5 = r5.toString()     // Catch:{ all -> 0x0129 }
            r2.logOperation(r3, r5)     // Catch:{ all -> 0x0129 }
        L_0x0128:
            throw r0     // Catch:{ all -> 0x0129 }
        L_0x0129:
            r0 = move-exception
            r22.releaseReference()
            throw r0
        L_0x012e:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.String r2 = "window must not be null."
            r0.<init>(r2)
            throw r0
        L_0x0137:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.String r2 = "sql must not be null."
            r0.<init>(r2)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.database.sqlite.SQLiteConnection.executeForCursorWindow(java.lang.String, java.lang.Object[], android.database.CursorWindow, int, int, boolean, android.os.CancellationSignal):int");
    }

    private PreparedStatement acquirePreparedStatement(String sql) {
        PreparedStatement statement = (PreparedStatement) this.mPreparedStatementCache.get(sql);
        boolean skipCache = false;
        if (statement != null) {
            if (!statement.mInUse) {
                return statement;
            }
            skipCache = true;
        }
        long statementPtr = nativePrepareStatement(this.mConnectionPtr, sql);
        try {
            int numParameters = nativeGetParameterCount(this.mConnectionPtr, statementPtr);
            int type = DatabaseUtils.getSqlStatementType(sql);
            PreparedStatement statement2 = obtainPreparedStatement(sql, statementPtr, numParameters, type, nativeIsReadOnly(this.mConnectionPtr, statementPtr));
            if (!skipCache && isCacheable(type)) {
                this.mPreparedStatementCache.put(sql, statement2);
                statement2.mInCache = true;
            }
            statement2.mInUse = true;
            return statement2;
        } catch (RuntimeException ex) {
            if (statement == null || !statement.mInCache) {
                nativeFinalizeStatement(this.mConnectionPtr, statementPtr);
            }
            throw ex;
        }
    }

    private void releasePreparedStatement(PreparedStatement statement) {
        statement.mInUse = false;
        if (statement.mInCache) {
            try {
                nativeResetStatementAndClearBindings(this.mConnectionPtr, statement.mStatementPtr);
            } catch (SQLiteException e) {
                this.mPreparedStatementCache.remove(statement.mSql);
            }
        } else {
            finalizePreparedStatement(statement);
        }
    }

    /* access modifiers changed from: private */
    public void finalizePreparedStatement(PreparedStatement statement) {
        nativeFinalizeStatement(this.mConnectionPtr, statement.mStatementPtr);
        recyclePreparedStatement(statement);
    }

    private void attachCancellationSignal(CancellationSignal cancellationSignal) {
        if (cancellationSignal != null) {
            cancellationSignal.throwIfCanceled();
            this.mCancellationSignalAttachCount++;
            if (this.mCancellationSignalAttachCount == 1) {
                nativeResetCancel(this.mConnectionPtr, true);
                cancellationSignal.setOnCancelListener(this);
            }
        }
    }

    private void detachCancellationSignal(CancellationSignal cancellationSignal) {
        if (cancellationSignal != null) {
            this.mCancellationSignalAttachCount--;
            if (this.mCancellationSignalAttachCount == 0) {
                cancellationSignal.setOnCancelListener((CancellationSignal.OnCancelListener) null);
                nativeResetCancel(this.mConnectionPtr, false);
            }
        }
    }

    public void onCancel() {
        nativeCancel(this.mConnectionPtr);
    }

    private void bindArguments(PreparedStatement statement, Object[] bindArgs) {
        int count = bindArgs != null ? bindArgs.length : 0;
        if (count != statement.mNumParameters) {
            throw new SQLiteBindOrColumnIndexOutOfRangeException("Expected " + statement.mNumParameters + " bind arguments but " + count + " were provided.");
        } else if (count != 0) {
            long statementPtr = statement.mStatementPtr;
            for (int i = 0; i < count; i++) {
                Boolean bool = bindArgs[i];
                int typeOfObject = DatabaseUtils.getTypeOfObject(bool);
                if (typeOfObject != 4) {
                    switch (typeOfObject) {
                        case 0:
                            nativeBindNull(this.mConnectionPtr, statementPtr, i + 1);
                            break;
                        case 1:
                            nativeBindLong(this.mConnectionPtr, statementPtr, i + 1, bool.longValue());
                            break;
                        case 2:
                            nativeBindDouble(this.mConnectionPtr, statementPtr, i + 1, bool.doubleValue());
                            break;
                        default:
                            if (!(bool instanceof Boolean)) {
                                nativeBindString(this.mConnectionPtr, statementPtr, i + 1, bool.toString());
                                break;
                            } else {
                                nativeBindLong(this.mConnectionPtr, statementPtr, i + 1, bool.booleanValue() ? 1 : 0);
                                break;
                            }
                    }
                } else {
                    nativeBindBlob(this.mConnectionPtr, statementPtr, i + 1, bool);
                }
            }
        }
    }

    private void throwIfStatementForbidden(PreparedStatement statement) {
        if (this.mOnlyAllowReadOnlyOperations && !statement.mReadOnly) {
            throw new SQLiteException("Cannot execute this statement because it might modify the database but the connection is read-only.");
        }
    }

    private static boolean isCacheable(int statementType) {
        if (statementType == 2 || statementType == 1) {
            return true;
        }
        return false;
    }

    private void applyBlockGuardPolicy(PreparedStatement statement) {
        if (this.mConfiguration.isInMemoryDb()) {
            return;
        }
        if (statement.mReadOnly) {
            BlockGuard.getThreadPolicy().onReadFromDisk();
        } else {
            BlockGuard.getThreadPolicy().onWriteToDisk();
        }
    }

    public void dump(Printer printer, boolean verbose) {
        dumpUnsafe(printer, verbose);
    }

    /* access modifiers changed from: package-private */
    public void dumpUnsafe(Printer printer, boolean verbose) {
        printer.println("Connection #" + this.mConnectionId + SettingsStringUtil.DELIMITER);
        if (verbose) {
            printer.println("  connectionPtr: 0x" + Long.toHexString(this.mConnectionPtr));
        }
        printer.println("  isPrimaryConnection: " + this.mIsPrimaryConnection);
        printer.println("  onlyAllowReadOnlyOperations: " + this.mOnlyAllowReadOnlyOperations);
        this.mRecentOperations.dump(printer);
        if (verbose) {
            this.mPreparedStatementCache.dump(printer);
        }
    }

    /* access modifiers changed from: package-private */
    public String describeCurrentOperationUnsafe() {
        return this.mRecentOperations.describeCurrentOperation();
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x00e2, code lost:
        r0 = th;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x00e2 A[ExcHandler: all (th java.lang.Throwable), Splitter:B:11:0x0050] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void collectDbStats(java.util.ArrayList<android.database.sqlite.SQLiteDebug.DbStats> r28) {
        /*
            r27 = this;
            r9 = r27
            r10 = r28
            long r0 = r9.mConnectionPtr
            int r11 = nativeGetDbLookaside(r0)
            r1 = 0
            r3 = 0
            r12 = 0
            java.lang.String r0 = "PRAGMA page_count;"
            long r5 = r9.executeForLong(r0, r12, r12)     // Catch:{ SQLiteException -> 0x001e }
            r1 = r5
            java.lang.String r0 = "PRAGMA page_size;"
            long r5 = r9.executeForLong(r0, r12, r12)     // Catch:{ SQLiteException -> 0x001e }
            r3 = r5
            goto L_0x001f
        L_0x001e:
            r0 = move-exception
        L_0x001f:
            r13 = r1
            r15 = r3
            r1 = r27
            r2 = r11
            r3 = r13
            r5 = r15
            android.database.sqlite.SQLiteDebug$DbStats r0 = r1.getMainDbStatsUnsafe(r2, r3, r5)
            r10.add(r0)
            android.database.CursorWindow r0 = new android.database.CursorWindow
            java.lang.String r1 = "collectDbStats"
            r0.<init>((java.lang.String) r1)
            r8 = r0
            java.lang.String r2 = "PRAGMA database_list;"
            r3 = 0
            r5 = 0
            r6 = 0
            r7 = 0
            r0 = 0
            r1 = r27
            r4 = r8
            r17 = r8
            r8 = r0
            r1.executeForCursorWindow(r2, r3, r4, r5, r6, r7, r8)     // Catch:{ SQLiteException -> 0x00ed, all -> 0x00e9 }
            r1 = 1
            r0 = r1
        L_0x0047:
            r2 = r0
            int r0 = r17.getNumRows()     // Catch:{ SQLiteException -> 0x00ed, all -> 0x00e9 }
            if (r2 >= r0) goto L_0x00e6
            r3 = r17
            java.lang.String r0 = r3.getString(r2, r1)     // Catch:{ SQLiteException -> 0x00e4, all -> 0x00e2 }
            r4 = r0
            r0 = 2
            java.lang.String r0 = r3.getString(r2, r0)     // Catch:{ SQLiteException -> 0x00e4, all -> 0x00e2 }
            r5 = r0
            r13 = 0
            r15 = 0
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ SQLiteException -> 0x0097, all -> 0x00e2 }
            r0.<init>()     // Catch:{ SQLiteException -> 0x0097, all -> 0x00e2 }
            java.lang.String r6 = "PRAGMA "
            r0.append(r6)     // Catch:{ SQLiteException -> 0x0097, all -> 0x00e2 }
            r0.append(r4)     // Catch:{ SQLiteException -> 0x0097, all -> 0x00e2 }
            java.lang.String r6 = ".page_count;"
            r0.append(r6)     // Catch:{ SQLiteException -> 0x0097, all -> 0x00e2 }
            java.lang.String r0 = r0.toString()     // Catch:{ SQLiteException -> 0x0097, all -> 0x00e2 }
            long r6 = r9.executeForLong(r0, r12, r12)     // Catch:{ SQLiteException -> 0x0097, all -> 0x00e2 }
            r13 = r6
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ SQLiteException -> 0x0097, all -> 0x00e2 }
            r0.<init>()     // Catch:{ SQLiteException -> 0x0097, all -> 0x00e2 }
            java.lang.String r6 = "PRAGMA "
            r0.append(r6)     // Catch:{ SQLiteException -> 0x0097, all -> 0x00e2 }
            r0.append(r4)     // Catch:{ SQLiteException -> 0x0097, all -> 0x00e2 }
            java.lang.String r6 = ".page_size;"
            r0.append(r6)     // Catch:{ SQLiteException -> 0x0097, all -> 0x00e2 }
            java.lang.String r0 = r0.toString()     // Catch:{ SQLiteException -> 0x0097, all -> 0x00e2 }
            long r6 = r9.executeForLong(r0, r12, r12)     // Catch:{ SQLiteException -> 0x0097, all -> 0x00e2 }
            r15 = r6
            goto L_0x0098
        L_0x0097:
            r0 = move-exception
        L_0x0098:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ SQLiteException -> 0x00e4, all -> 0x00e2 }
            r0.<init>()     // Catch:{ SQLiteException -> 0x00e4, all -> 0x00e2 }
            java.lang.String r6 = "  (attached) "
            r0.append(r6)     // Catch:{ SQLiteException -> 0x00e4, all -> 0x00e2 }
            r0.append(r4)     // Catch:{ SQLiteException -> 0x00e4, all -> 0x00e2 }
            java.lang.String r0 = r0.toString()     // Catch:{ SQLiteException -> 0x00e4, all -> 0x00e2 }
            boolean r6 = r5.isEmpty()     // Catch:{ SQLiteException -> 0x00e4, all -> 0x00e2 }
            if (r6 != 0) goto L_0x00c4
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ SQLiteException -> 0x00e4, all -> 0x00e2 }
            r6.<init>()     // Catch:{ SQLiteException -> 0x00e4, all -> 0x00e2 }
            r6.append(r0)     // Catch:{ SQLiteException -> 0x00e4, all -> 0x00e2 }
            java.lang.String r7 = ": "
            r6.append(r7)     // Catch:{ SQLiteException -> 0x00e4, all -> 0x00e2 }
            r6.append(r5)     // Catch:{ SQLiteException -> 0x00e4, all -> 0x00e2 }
            java.lang.String r6 = r6.toString()     // Catch:{ SQLiteException -> 0x00e4, all -> 0x00e2 }
            r0 = r6
        L_0x00c4:
            android.database.sqlite.SQLiteDebug$DbStats r6 = new android.database.sqlite.SQLiteDebug$DbStats     // Catch:{ SQLiteException -> 0x00e4, all -> 0x00e2 }
            r23 = 0
            r24 = 0
            r25 = 0
            r26 = 0
            r17 = r6
            r18 = r0
            r19 = r13
            r21 = r15
            r17.<init>(r18, r19, r21, r23, r24, r25, r26)     // Catch:{ SQLiteException -> 0x00e4, all -> 0x00e2 }
            r10.add(r6)     // Catch:{ SQLiteException -> 0x00e4, all -> 0x00e2 }
            int r0 = r2 + 1
            r17 = r3
            goto L_0x0047
        L_0x00e2:
            r0 = move-exception
            goto L_0x00f3
        L_0x00e4:
            r0 = move-exception
            goto L_0x00f9
        L_0x00e6:
            r3 = r17
            goto L_0x00f9
        L_0x00e9:
            r0 = move-exception
            r3 = r17
            goto L_0x00f3
        L_0x00ed:
            r0 = move-exception
            r3 = r17
            goto L_0x00f9
        L_0x00f1:
            r0 = move-exception
            r3 = r8
        L_0x00f3:
            r3.close()
            throw r0
        L_0x00f7:
            r0 = move-exception
            r3 = r8
        L_0x00f9:
            r3.close()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.database.sqlite.SQLiteConnection.collectDbStats(java.util.ArrayList):void");
    }

    /* access modifiers changed from: package-private */
    public void collectDbStatsUnsafe(ArrayList<SQLiteDebug.DbStats> dbStatsList) {
        dbStatsList.add(getMainDbStatsUnsafe(0, 0, 0));
    }

    private SQLiteDebug.DbStats getMainDbStatsUnsafe(int lookaside, long pageCount, long pageSize) {
        String label = this.mConfiguration.path;
        if (!this.mIsPrimaryConnection) {
            label = label + " (" + this.mConnectionId + ")";
        }
        return new SQLiteDebug.DbStats(label, pageCount, pageSize, lookaside, this.mPreparedStatementCache.hitCount(), this.mPreparedStatementCache.missCount(), this.mPreparedStatementCache.size());
    }

    public String toString() {
        return "SQLiteConnection: " + this.mConfiguration.path + " (" + this.mConnectionId + ")";
    }

    private PreparedStatement obtainPreparedStatement(String sql, long statementPtr, int numParameters, int type, boolean readOnly) {
        PreparedStatement statement = this.mPreparedStatementPool;
        if (statement != null) {
            this.mPreparedStatementPool = statement.mPoolNext;
            statement.mPoolNext = null;
            statement.mInCache = false;
        } else {
            statement = new PreparedStatement();
        }
        statement.mSql = sql;
        statement.mStatementPtr = statementPtr;
        statement.mNumParameters = numParameters;
        statement.mType = type;
        statement.mReadOnly = readOnly;
        return statement;
    }

    private void recyclePreparedStatement(PreparedStatement statement) {
        statement.mSql = null;
        statement.mPoolNext = this.mPreparedStatementPool;
        this.mPreparedStatementPool = statement;
    }

    /* access modifiers changed from: private */
    public static String trimSqlForDisplay(String sql) {
        return sql.replaceAll("[\\s]*\\n+[\\s]*", WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER);
    }

    private static final class PreparedStatement {
        public boolean mInCache;
        public boolean mInUse;
        public int mNumParameters;
        public PreparedStatement mPoolNext;
        public boolean mReadOnly;
        public String mSql;
        public long mStatementPtr;
        public int mType;

        private PreparedStatement() {
        }
    }

    private final class PreparedStatementCache extends LruCache<String, PreparedStatement> {
        public PreparedStatementCache(int size) {
            super(size);
        }

        /* access modifiers changed from: protected */
        public void entryRemoved(boolean evicted, String key, PreparedStatement oldValue, PreparedStatement newValue) {
            oldValue.mInCache = false;
            if (!oldValue.mInUse) {
                SQLiteConnection.this.finalizePreparedStatement(oldValue);
            }
        }

        public void dump(Printer printer) {
            printer.println("  Prepared statement cache:");
            Map<String, PreparedStatement> cache = snapshot();
            if (!cache.isEmpty()) {
                int i = 0;
                for (Map.Entry<String, PreparedStatement> entry : cache.entrySet()) {
                    PreparedStatement statement = entry.getValue();
                    if (statement.mInCache) {
                        printer.println("    " + i + ": statementPtr=0x" + Long.toHexString(statement.mStatementPtr) + ", numParameters=" + statement.mNumParameters + ", type=" + statement.mType + ", readOnly=" + statement.mReadOnly + ", sql=\"" + SQLiteConnection.trimSqlForDisplay(entry.getKey()) + "\"");
                    }
                    i++;
                }
                return;
            }
            printer.println("    <none>");
        }
    }

    private static final class OperationLog {
        private static final int COOKIE_GENERATION_SHIFT = 8;
        private static final int COOKIE_INDEX_MASK = 255;
        private static final int MAX_RECENT_OPERATIONS = 20;
        private int mGeneration;
        private int mIndex;
        private final Operation[] mOperations = new Operation[20];
        private final SQLiteConnectionPool mPool;
        private long mResultLong = Long.MIN_VALUE;
        private String mResultString;

        OperationLog(SQLiteConnectionPool pool) {
            this.mPool = pool;
        }

        public int beginOperation(String kind, String sql, Object[] bindArgs) {
            int i;
            this.mResultLong = Long.MIN_VALUE;
            this.mResultString = null;
            synchronized (this.mOperations) {
                int index = (this.mIndex + 1) % 20;
                Operation operation = this.mOperations[index];
                int i2 = 0;
                if (operation == null) {
                    operation = new Operation();
                    this.mOperations[index] = operation;
                } else {
                    operation.mFinished = false;
                    operation.mException = null;
                    if (operation.mBindArgs != null) {
                        operation.mBindArgs.clear();
                    }
                }
                operation.mStartWallTime = System.currentTimeMillis();
                operation.mStartTime = SystemClock.uptimeMillis();
                operation.mKind = kind;
                operation.mSql = sql;
                operation.mPath = this.mPool.getPath();
                operation.mResultLong = Long.MIN_VALUE;
                operation.mResultString = null;
                if (bindArgs != null) {
                    if (operation.mBindArgs == null) {
                        operation.mBindArgs = new ArrayList<>();
                    } else {
                        operation.mBindArgs.clear();
                    }
                    while (true) {
                        int i3 = i2;
                        if (i3 >= bindArgs.length) {
                            break;
                        }
                        Object arg = bindArgs[i3];
                        if (arg == null || !(arg instanceof byte[])) {
                            operation.mBindArgs.add(arg);
                        } else {
                            operation.mBindArgs.add(SQLiteConnection.EMPTY_BYTE_ARRAY);
                        }
                        i2 = i3 + 1;
                    }
                }
                operation.mCookie = newOperationCookieLocked(index);
                if (Trace.isTagEnabled(1048576)) {
                    Trace.asyncTraceBegin(1048576, operation.getTraceMethodName(), operation.mCookie);
                }
                this.mIndex = index;
                i = operation.mCookie;
            }
            return i;
        }

        public void failOperation(int cookie, Exception ex) {
            synchronized (this.mOperations) {
                Operation operation = getOperationLocked(cookie);
                if (operation != null) {
                    operation.mException = ex;
                }
            }
        }

        public void endOperation(int cookie) {
            synchronized (this.mOperations) {
                if (endOperationDeferLogLocked(cookie)) {
                    logOperationLocked(cookie, (String) null);
                }
            }
        }

        public boolean endOperationDeferLog(int cookie) {
            boolean endOperationDeferLogLocked;
            synchronized (this.mOperations) {
                endOperationDeferLogLocked = endOperationDeferLogLocked(cookie);
            }
            return endOperationDeferLogLocked;
        }

        public void logOperation(int cookie, String detail) {
            synchronized (this.mOperations) {
                logOperationLocked(cookie, detail);
            }
        }

        public void setResult(long longResult) {
            this.mResultLong = longResult;
        }

        public void setResult(String stringResult) {
            this.mResultString = stringResult;
        }

        private boolean endOperationDeferLogLocked(int cookie) {
            Operation operation = getOperationLocked(cookie);
            if (operation == null) {
                return false;
            }
            if (Trace.isTagEnabled(1048576)) {
                Trace.asyncTraceEnd(1048576, operation.getTraceMethodName(), operation.mCookie);
            }
            operation.mEndTime = SystemClock.uptimeMillis();
            operation.mFinished = true;
            long execTime = operation.mEndTime - operation.mStartTime;
            this.mPool.onStatementExecuted(execTime);
            if (!SQLiteDebug.NoPreloadHolder.DEBUG_LOG_SLOW_QUERIES || !SQLiteDebug.shouldLogSlowQuery(execTime)) {
                return false;
            }
            return true;
        }

        private void logOperationLocked(int cookie, String detail) {
            Operation operation = getOperationLocked(cookie);
            operation.mResultLong = this.mResultLong;
            operation.mResultString = this.mResultString;
            StringBuilder msg = new StringBuilder();
            operation.describe(msg, true);
            if (detail != null) {
                msg.append(", ");
                msg.append(detail);
            }
            Log.d(SQLiteConnection.TAG, msg.toString());
        }

        private int newOperationCookieLocked(int index) {
            int generation = this.mGeneration;
            this.mGeneration = generation + 1;
            return (generation << 8) | index;
        }

        private Operation getOperationLocked(int cookie) {
            Operation operation = this.mOperations[cookie & 255];
            if (operation.mCookie == cookie) {
                return operation;
            }
            return null;
        }

        public String describeCurrentOperation() {
            synchronized (this.mOperations) {
                Operation operation = this.mOperations[this.mIndex];
                if (operation == null || operation.mFinished) {
                    return null;
                }
                StringBuilder msg = new StringBuilder();
                operation.describe(msg, false);
                String sb = msg.toString();
                return sb;
            }
        }

        /* JADX WARNING: Removed duplicated region for block: B:10:0x004f  */
        /* JADX WARNING: Removed duplicated region for block: B:9:0x004c  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void dump(android.util.Printer r11) {
            /*
                r10 = this;
                android.database.sqlite.SQLiteConnection$Operation[] r0 = r10.mOperations
                monitor-enter(r0)
                java.lang.String r1 = "  Most recently executed operations:"
                r11.println(r1)     // Catch:{ all -> 0x0066 }
                int r1 = r10.mIndex     // Catch:{ all -> 0x0066 }
                android.database.sqlite.SQLiteConnection$Operation[] r2 = r10.mOperations     // Catch:{ all -> 0x0066 }
                r2 = r2[r1]     // Catch:{ all -> 0x0066 }
                if (r2 == 0) goto L_0x005f
                java.text.SimpleDateFormat r3 = new java.text.SimpleDateFormat     // Catch:{ all -> 0x0066 }
                java.lang.String r4 = "yyyy-MM-dd HH:mm:ss.SSS"
                r3.<init>(r4)     // Catch:{ all -> 0x0066 }
                r4 = 0
                r5 = r1
                r1 = r4
            L_0x001b:
                java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x0066 }
                r6.<init>()     // Catch:{ all -> 0x0066 }
                java.lang.String r7 = "    "
                r6.append(r7)     // Catch:{ all -> 0x0066 }
                r6.append(r1)     // Catch:{ all -> 0x0066 }
                java.lang.String r7 = ": ["
                r6.append(r7)     // Catch:{ all -> 0x0066 }
                java.util.Date r7 = new java.util.Date     // Catch:{ all -> 0x0066 }
                long r8 = r2.mStartWallTime     // Catch:{ all -> 0x0066 }
                r7.<init>(r8)     // Catch:{ all -> 0x0066 }
                java.lang.String r7 = r3.format(r7)     // Catch:{ all -> 0x0066 }
                r6.append(r7)     // Catch:{ all -> 0x0066 }
                java.lang.String r8 = "] "
                r6.append(r8)     // Catch:{ all -> 0x0066 }
                r2.describe(r6, r4)     // Catch:{ all -> 0x0066 }
                java.lang.String r8 = r6.toString()     // Catch:{ all -> 0x0066 }
                r11.println(r8)     // Catch:{ all -> 0x0066 }
                if (r5 <= 0) goto L_0x004f
                int r5 = r5 + -1
                goto L_0x0051
            L_0x004f:
                r5 = 19
            L_0x0051:
                int r1 = r1 + 1
                android.database.sqlite.SQLiteConnection$Operation[] r8 = r10.mOperations     // Catch:{ all -> 0x0066 }
                r8 = r8[r5]     // Catch:{ all -> 0x0066 }
                r2 = r8
                if (r2 == 0) goto L_0x005e
                r6 = 20
                if (r1 < r6) goto L_0x001b
            L_0x005e:
                goto L_0x0064
            L_0x005f:
                java.lang.String r3 = "    <none>"
                r11.println(r3)     // Catch:{ all -> 0x0066 }
            L_0x0064:
                monitor-exit(r0)     // Catch:{ all -> 0x0066 }
                return
            L_0x0066:
                r1 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x0066 }
                throw r1
            */
            throw new UnsupportedOperationException("Method not decompiled: android.database.sqlite.SQLiteConnection.OperationLog.dump(android.util.Printer):void");
        }
    }

    private static final class Operation {
        private static final int MAX_TRACE_METHOD_NAME_LEN = 256;
        public ArrayList<Object> mBindArgs;
        public int mCookie;
        public long mEndTime;
        public Exception mException;
        public boolean mFinished;
        public String mKind;
        public String mPath;
        public long mResultLong;
        public String mResultString;
        public String mSql;
        public long mStartTime;
        public long mStartWallTime;

        private Operation() {
        }

        public void describe(StringBuilder msg, boolean allowDetailedLog) {
            msg.append(this.mKind);
            if (this.mFinished) {
                msg.append(" took ");
                msg.append(this.mEndTime - this.mStartTime);
                msg.append("ms");
            } else {
                msg.append(" started ");
                msg.append(System.currentTimeMillis() - this.mStartWallTime);
                msg.append("ms ago");
            }
            msg.append(" - ");
            msg.append(getStatus());
            if (this.mSql != null) {
                msg.append(", sql=\"");
                msg.append(SQLiteConnection.trimSqlForDisplay(this.mSql));
                msg.append("\"");
            }
            if (allowDetailedLog && SQLiteDebug.NoPreloadHolder.DEBUG_LOG_DETAILED && this.mBindArgs != null && this.mBindArgs.size() != 0) {
                msg.append(", bindArgs=[");
                int count = this.mBindArgs.size();
                for (int i = 0; i < count; i++) {
                    Object arg = this.mBindArgs.get(i);
                    if (i != 0) {
                        msg.append(", ");
                    }
                    if (arg == null) {
                        msg.append("null");
                    } else if (arg instanceof byte[]) {
                        msg.append("<byte[]>");
                    } else if (arg instanceof String) {
                        msg.append("\"");
                        msg.append((String) arg);
                        msg.append("\"");
                    } else {
                        msg.append(arg);
                    }
                }
                msg.append("]");
            }
            msg.append(", path=");
            msg.append(this.mPath);
            if (this.mException != null) {
                msg.append(", exception=\"");
                msg.append(this.mException.getMessage());
                msg.append("\"");
            }
            if (this.mResultLong != Long.MIN_VALUE) {
                msg.append(", result=");
                msg.append(this.mResultLong);
            }
            if (this.mResultString != null) {
                msg.append(", result=\"");
                msg.append(this.mResultString);
                msg.append("\"");
            }
        }

        private String getStatus() {
            if (!this.mFinished) {
                return "running";
            }
            return this.mException != null ? "failed" : "succeeded";
        }

        /* access modifiers changed from: private */
        public String getTraceMethodName() {
            String methodName = this.mKind + WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER + this.mSql;
            if (methodName.length() > 256) {
                return methodName.substring(0, 256);
            }
            return methodName;
        }
    }
}
