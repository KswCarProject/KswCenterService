package android.database.sqlite;

import android.annotation.UnsupportedAppUsage;
import android.app.ActivityManager;
import android.app.ActivityThread;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.DatabaseUtils;
import android.database.DefaultDatabaseErrorHandler;
import android.database.SQLException;
import android.database.sqlite.SQLiteDebug;
import android.net.wifi.WifiEnterpriseConfig;
import android.os.CancellationSignal;
import android.os.Looper;
import android.os.SystemProperties;
import android.provider.SettingsStringUtil;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.EventLog;
import android.util.Log;
import android.util.Pair;
import android.util.Printer;
import com.android.internal.util.Preconditions;
import dalvik.system.CloseGuard;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.function.Supplier;

public final class SQLiteDatabase extends SQLiteClosable {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final int CONFLICT_ABORT = 2;
    public static final int CONFLICT_FAIL = 3;
    public static final int CONFLICT_IGNORE = 4;
    public static final int CONFLICT_NONE = 0;
    public static final int CONFLICT_REPLACE = 5;
    public static final int CONFLICT_ROLLBACK = 1;
    @UnsupportedAppUsage
    public static final String[] CONFLICT_VALUES = {"", " OR ROLLBACK ", " OR ABORT ", " OR FAIL ", " OR IGNORE ", " OR REPLACE "};
    public static final int CREATE_IF_NECESSARY = 268435456;
    private static final boolean DEBUG_CLOSE_IDLE_CONNECTIONS = SystemProperties.getBoolean("persist.debug.sqlite.close_idle_connections", false);
    public static final int ENABLE_LEGACY_COMPATIBILITY_WAL = Integer.MIN_VALUE;
    public static final int ENABLE_WRITE_AHEAD_LOGGING = 536870912;
    private static final int EVENT_DB_CORRUPT = 75004;
    public static final int MAX_SQL_CACHE_SIZE = 100;
    public static final int NO_LOCALIZED_COLLATORS = 16;
    public static final int OPEN_READONLY = 1;
    public static final int OPEN_READWRITE = 0;
    private static final int OPEN_READ_MASK = 1;
    public static final int SQLITE_MAX_LIKE_PATTERN_LENGTH = 50000;
    private static final String TAG = "SQLiteDatabase";
    private static WeakHashMap<SQLiteDatabase, Object> sActiveDatabases = new WeakHashMap<>();
    private final CloseGuard mCloseGuardLocked = CloseGuard.get();
    @UnsupportedAppUsage
    private final SQLiteDatabaseConfiguration mConfigurationLocked;
    @UnsupportedAppUsage
    private SQLiteConnectionPool mConnectionPoolLocked;
    private final CursorFactory mCursorFactory;
    private final DatabaseErrorHandler mErrorHandler;
    private boolean mHasAttachedDbsLocked;
    private final Object mLock = new Object();
    @UnsupportedAppUsage
    private final ThreadLocal<SQLiteSession> mThreadSession = ThreadLocal.withInitial(new Supplier() {
        public final Object get() {
            return SQLiteDatabase.this.createSession();
        }
    });

    public interface CursorFactory {
        Cursor newCursor(SQLiteDatabase sQLiteDatabase, SQLiteCursorDriver sQLiteCursorDriver, String str, SQLiteQuery sQLiteQuery);
    }

    public interface CustomFunction {
        void callback(String[] strArr);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface DatabaseOpenFlags {
    }

    private SQLiteDatabase(String path, int openFlags, CursorFactory cursorFactory, DatabaseErrorHandler errorHandler, int lookasideSlotSize, int lookasideSlotCount, long idleConnectionTimeoutMs, String journalMode, String syncMode) {
        this.mCursorFactory = cursorFactory;
        this.mErrorHandler = errorHandler != null ? errorHandler : new DefaultDatabaseErrorHandler();
        this.mConfigurationLocked = new SQLiteDatabaseConfiguration(path, openFlags);
        this.mConfigurationLocked.lookasideSlotSize = lookasideSlotSize;
        this.mConfigurationLocked.lookasideSlotCount = lookasideSlotCount;
        if (ActivityManager.isLowRamDeviceStatic()) {
            this.mConfigurationLocked.lookasideSlotCount = 0;
            this.mConfigurationLocked.lookasideSlotSize = 0;
        }
        long effectiveTimeoutMs = Long.MAX_VALUE;
        if (!this.mConfigurationLocked.isInMemoryDb()) {
            if (idleConnectionTimeoutMs >= 0) {
                effectiveTimeoutMs = idleConnectionTimeoutMs;
            } else if (DEBUG_CLOSE_IDLE_CONNECTIONS) {
                effectiveTimeoutMs = (long) SQLiteGlobal.getIdleConnectionTimeout();
            }
        }
        this.mConfigurationLocked.idleConnectionTimeoutMs = effectiveTimeoutMs;
        this.mConfigurationLocked.journalMode = journalMode;
        this.mConfigurationLocked.syncMode = syncMode;
        if (SQLiteCompatibilityWalFlags.isLegacyCompatibilityWalEnabled()) {
            this.mConfigurationLocked.openFlags |= Integer.MIN_VALUE;
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

    /* access modifiers changed from: protected */
    public void onAllReferencesReleased() {
        dispose(false);
    }

    private void dispose(boolean finalized) {
        SQLiteConnectionPool pool;
        synchronized (this.mLock) {
            if (this.mCloseGuardLocked != null) {
                if (finalized) {
                    this.mCloseGuardLocked.warnIfOpen();
                }
                this.mCloseGuardLocked.close();
            }
            pool = this.mConnectionPoolLocked;
            this.mConnectionPoolLocked = null;
        }
        if (!finalized) {
            synchronized (sActiveDatabases) {
                sActiveDatabases.remove(this);
            }
            if (pool != null) {
                pool.close();
            }
        }
    }

    public static int releaseMemory() {
        return SQLiteGlobal.releaseMemory();
    }

    @Deprecated
    public void setLockingEnabled(boolean lockingEnabled) {
    }

    /* access modifiers changed from: package-private */
    public String getLabel() {
        String str;
        synchronized (this.mLock) {
            str = this.mConfigurationLocked.label;
        }
        return str;
    }

    /* access modifiers changed from: package-private */
    public void onCorruption() {
        EventLog.writeEvent((int) EVENT_DB_CORRUPT, getLabel());
        this.mErrorHandler.onCorruption(this);
    }

    /* access modifiers changed from: package-private */
    @UnsupportedAppUsage
    public SQLiteSession getThreadSession() {
        return this.mThreadSession.get();
    }

    /* access modifiers changed from: package-private */
    public SQLiteSession createSession() {
        SQLiteConnectionPool pool;
        synchronized (this.mLock) {
            throwIfNotOpenLocked();
            pool = this.mConnectionPoolLocked;
        }
        return new SQLiteSession(pool);
    }

    /* access modifiers changed from: package-private */
    public int getThreadDefaultConnectionFlags(boolean readOnly) {
        int flags;
        if (readOnly) {
            flags = 1;
        } else {
            flags = 2;
        }
        if (isMainThread()) {
            return flags | 4;
        }
        return flags;
    }

    private static boolean isMainThread() {
        Looper looper = Looper.myLooper();
        return looper != null && looper == Looper.getMainLooper();
    }

    public void beginTransaction() {
        beginTransaction((SQLiteTransactionListener) null, true);
    }

    public void beginTransactionNonExclusive() {
        beginTransaction((SQLiteTransactionListener) null, false);
    }

    public void beginTransactionWithListener(SQLiteTransactionListener transactionListener) {
        beginTransaction(transactionListener, true);
    }

    public void beginTransactionWithListenerNonExclusive(SQLiteTransactionListener transactionListener) {
        beginTransaction(transactionListener, false);
    }

    @UnsupportedAppUsage
    private void beginTransaction(SQLiteTransactionListener transactionListener, boolean exclusive) {
        int i;
        acquireReference();
        try {
            SQLiteSession threadSession = getThreadSession();
            if (exclusive) {
                i = 2;
            } else {
                i = 1;
            }
            threadSession.beginTransaction(i, transactionListener, getThreadDefaultConnectionFlags(false), (CancellationSignal) null);
        } finally {
            releaseReference();
        }
    }

    public void endTransaction() {
        acquireReference();
        try {
            getThreadSession().endTransaction((CancellationSignal) null);
        } finally {
            releaseReference();
        }
    }

    public void setTransactionSuccessful() {
        acquireReference();
        try {
            getThreadSession().setTransactionSuccessful();
        } finally {
            releaseReference();
        }
    }

    public boolean inTransaction() {
        acquireReference();
        try {
            return getThreadSession().hasTransaction();
        } finally {
            releaseReference();
        }
    }

    public boolean isDbLockedByCurrentThread() {
        acquireReference();
        try {
            return getThreadSession().hasConnection();
        } finally {
            releaseReference();
        }
    }

    @Deprecated
    public boolean isDbLockedByOtherThreads() {
        return false;
    }

    @Deprecated
    public boolean yieldIfContended() {
        return yieldIfContendedHelper(false, -1);
    }

    public boolean yieldIfContendedSafely() {
        return yieldIfContendedHelper(true, -1);
    }

    public boolean yieldIfContendedSafely(long sleepAfterYieldDelay) {
        return yieldIfContendedHelper(true, sleepAfterYieldDelay);
    }

    private boolean yieldIfContendedHelper(boolean throwIfUnsafe, long sleepAfterYieldDelay) {
        acquireReference();
        try {
            return getThreadSession().yieldTransaction(sleepAfterYieldDelay, throwIfUnsafe, (CancellationSignal) null);
        } finally {
            releaseReference();
        }
    }

    @Deprecated
    public Map<String, String> getSyncedTables() {
        return new HashMap(0);
    }

    public static SQLiteDatabase openDatabase(String path, CursorFactory factory, int flags) {
        return openDatabase(path, factory, flags, (DatabaseErrorHandler) null);
    }

    public static SQLiteDatabase openDatabase(File path, OpenParams openParams) {
        return openDatabase(path.getPath(), openParams);
    }

    @UnsupportedAppUsage
    private static SQLiteDatabase openDatabase(String path, OpenParams openParams) {
        Preconditions.checkArgument(openParams != null, "OpenParams cannot be null");
        SQLiteDatabase sQLiteDatabase = new SQLiteDatabase(path, openParams.mOpenFlags, openParams.mCursorFactory, openParams.mErrorHandler, openParams.mLookasideSlotSize, openParams.mLookasideSlotCount, openParams.mIdleConnectionTimeout, openParams.mJournalMode, openParams.mSyncMode);
        sQLiteDatabase.open();
        return sQLiteDatabase;
    }

    public static SQLiteDatabase openDatabase(String path, CursorFactory factory, int flags, DatabaseErrorHandler errorHandler) {
        SQLiteDatabase db = new SQLiteDatabase(path, flags, factory, errorHandler, -1, -1, -1, (String) null, (String) null);
        db.open();
        return db;
    }

    public static SQLiteDatabase openOrCreateDatabase(File file, CursorFactory factory) {
        return openOrCreateDatabase(file.getPath(), factory);
    }

    public static SQLiteDatabase openOrCreateDatabase(String path, CursorFactory factory) {
        return openDatabase(path, factory, 268435456, (DatabaseErrorHandler) null);
    }

    public static SQLiteDatabase openOrCreateDatabase(String path, CursorFactory factory, DatabaseErrorHandler errorHandler) {
        return openDatabase(path, factory, 268435456, errorHandler);
    }

    public static boolean deleteDatabase(File file) {
        return deleteDatabase(file, true);
    }

    public static boolean deleteDatabase(File file, boolean removeCheckFile) {
        if (file != null) {
            boolean deleted = false | file.delete() | new File(file.getPath() + "-journal").delete() | new File(file.getPath() + "-shm").delete() | new File(file.getPath() + "-wal").delete();
            StringBuilder sb = new StringBuilder();
            sb.append(file.getPath());
            sb.append("-wipecheck");
            new File(sb.toString()).delete();
            File dir = file.getParentFile();
            if (dir != null) {
                final String prefix = file.getName() + "-mj";
                File[] files = dir.listFiles(new FileFilter() {
                    public boolean accept(File candidate) {
                        return candidate.getName().startsWith(prefix);
                    }
                });
                if (files != null) {
                    for (File masterJournal : files) {
                        deleted |= masterJournal.delete();
                    }
                }
            }
            return deleted;
        }
        throw new IllegalArgumentException("file must not be null");
    }

    @UnsupportedAppUsage
    public void reopenReadWrite() {
        synchronized (this.mLock) {
            throwIfNotOpenLocked();
            if (isReadOnlyLocked()) {
                int oldOpenFlags = this.mConfigurationLocked.openFlags;
                this.mConfigurationLocked.openFlags = (this.mConfigurationLocked.openFlags & -2) | 0;
                try {
                    this.mConnectionPoolLocked.reconfigure(this.mConfigurationLocked);
                } catch (RuntimeException ex) {
                    this.mConfigurationLocked.openFlags = oldOpenFlags;
                    throw ex;
                }
            }
        }
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void open() {
        /*
            r3 = this;
            r3.openInner()     // Catch:{ RuntimeException -> 0x0006 }
            goto L_0x001a
        L_0x0004:
            r0 = move-exception
            goto L_0x001d
        L_0x0006:
            r0 = move-exception
            boolean r1 = android.database.sqlite.SQLiteDatabaseCorruptException.isCorruptException(r0)     // Catch:{ SQLiteException -> 0x0004 }
            if (r1 == 0) goto L_0x001c
            java.lang.String r1 = "SQLiteDatabase"
            java.lang.String r2 = "Database corruption detected in open()"
            android.util.Log.e(r1, r2, r0)     // Catch:{ SQLiteException -> 0x0004 }
            r3.onCorruption()     // Catch:{ SQLiteException -> 0x0004 }
            r3.openInner()     // Catch:{ SQLiteException -> 0x0004 }
        L_0x001a:
            return
        L_0x001c:
            throw r0     // Catch:{ SQLiteException -> 0x0004 }
        L_0x001d:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "Failed to open database '"
            r1.append(r2)
            java.lang.String r2 = r3.getLabel()
            r1.append(r2)
            java.lang.String r2 = "'."
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            java.lang.String r2 = "SQLiteDatabase"
            android.util.Log.e(r2, r1, r0)
            r3.close()
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.database.sqlite.SQLiteDatabase.open():void");
    }

    /* JADX WARNING: CFG modification limit reached, blocks count: 120 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void openInner() {
        /*
            r3 = this;
            java.lang.Object r0 = r3.mLock
            monitor-enter(r0)
            android.database.sqlite.SQLiteDatabaseConfiguration r1 = r3.mConfigurationLocked     // Catch:{ all -> 0x0024 }
            android.database.sqlite.SQLiteConnectionPool r1 = android.database.sqlite.SQLiteConnectionPool.open(r1)     // Catch:{ all -> 0x0024 }
            r3.mConnectionPoolLocked = r1     // Catch:{ all -> 0x0024 }
            dalvik.system.CloseGuard r1 = r3.mCloseGuardLocked     // Catch:{ all -> 0x0024 }
            java.lang.String r2 = "close"
            r1.open(r2)     // Catch:{ all -> 0x0024 }
            monitor-exit(r0)     // Catch:{ all -> 0x0024 }
            java.util.WeakHashMap<android.database.sqlite.SQLiteDatabase, java.lang.Object> r1 = sActiveDatabases
            monitor-enter(r1)
            java.util.WeakHashMap<android.database.sqlite.SQLiteDatabase, java.lang.Object> r0 = sActiveDatabases     // Catch:{ all -> 0x001f }
            r2 = 0
            r0.put(r3, r2)     // Catch:{ all -> 0x001f }
            monitor-exit(r1)     // Catch:{ all -> 0x001f }
            return
        L_0x001f:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x001f }
            throw r0
        L_0x0022:
            monitor-exit(r0)     // Catch:{ all -> 0x0024 }
            throw r1
        L_0x0024:
            r1 = move-exception
            goto L_0x0022
        */
        throw new UnsupportedOperationException("Method not decompiled: android.database.sqlite.SQLiteDatabase.openInner():void");
    }

    public static SQLiteDatabase create(CursorFactory factory) {
        return openDatabase(SQLiteDatabaseConfiguration.MEMORY_DB_PATH, factory, 268435456);
    }

    public static SQLiteDatabase createInMemory(OpenParams openParams) {
        return openDatabase(SQLiteDatabaseConfiguration.MEMORY_DB_PATH, openParams.toBuilder().addOpenFlags(268435456).build());
    }

    public void addCustomFunction(String name, int numArgs, CustomFunction function) {
        SQLiteCustomFunction wrapper = new SQLiteCustomFunction(name, numArgs, function);
        synchronized (this.mLock) {
            throwIfNotOpenLocked();
            this.mConfigurationLocked.customFunctions.add(wrapper);
            try {
                this.mConnectionPoolLocked.reconfigure(this.mConfigurationLocked);
            } catch (RuntimeException ex) {
                this.mConfigurationLocked.customFunctions.remove(wrapper);
                throw ex;
            }
        }
    }

    public int getVersion() {
        return Long.valueOf(DatabaseUtils.longForQuery(this, "PRAGMA user_version;", (String[]) null)).intValue();
    }

    public void setVersion(int version) {
        execSQL("PRAGMA user_version = " + version);
    }

    public long getMaximumSize() {
        return getPageSize() * DatabaseUtils.longForQuery(this, "PRAGMA max_page_count;", (String[]) null);
    }

    public long setMaximumSize(long numBytes) {
        long pageSize = getPageSize();
        long numPages = numBytes / pageSize;
        if (numBytes % pageSize != 0) {
            numPages++;
        }
        return DatabaseUtils.longForQuery(this, "PRAGMA max_page_count = " + numPages, (String[]) null) * pageSize;
    }

    public long getPageSize() {
        return DatabaseUtils.longForQuery(this, "PRAGMA page_size;", (String[]) null);
    }

    public void setPageSize(long numBytes) {
        execSQL("PRAGMA page_size = " + numBytes);
    }

    @Deprecated
    public void markTableSyncable(String table, String deletedTable) {
    }

    @Deprecated
    public void markTableSyncable(String table, String foreignKey, String updateTable) {
    }

    public static String findEditTable(String tables) {
        if (!TextUtils.isEmpty(tables)) {
            int spacepos = tables.indexOf(32);
            int commapos = tables.indexOf(44);
            if (spacepos > 0 && (spacepos < commapos || commapos < 0)) {
                return tables.substring(0, spacepos);
            }
            if (commapos <= 0 || (commapos >= spacepos && spacepos >= 0)) {
                return tables;
            }
            return tables.substring(0, commapos);
        }
        throw new IllegalStateException("Invalid tables");
    }

    public SQLiteStatement compileStatement(String sql) throws SQLException {
        acquireReference();
        try {
            return new SQLiteStatement(this, sql, (Object[]) null);
        } finally {
            releaseReference();
        }
    }

    public Cursor query(boolean distinct, String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit) {
        return queryWithFactory((CursorFactory) null, distinct, table, columns, selection, selectionArgs, groupBy, having, orderBy, limit, (CancellationSignal) null);
    }

    public Cursor query(boolean distinct, String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit, CancellationSignal cancellationSignal) {
        return queryWithFactory((CursorFactory) null, distinct, table, columns, selection, selectionArgs, groupBy, having, orderBy, limit, cancellationSignal);
    }

    public Cursor queryWithFactory(CursorFactory cursorFactory, boolean distinct, String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit) {
        return queryWithFactory(cursorFactory, distinct, table, columns, selection, selectionArgs, groupBy, having, orderBy, limit, (CancellationSignal) null);
    }

    public Cursor queryWithFactory(CursorFactory cursorFactory, boolean distinct, String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit, CancellationSignal cancellationSignal) {
        acquireReference();
        try {
            return rawQueryWithFactory(cursorFactory, SQLiteQueryBuilder.buildQueryString(distinct, table, columns, selection, groupBy, having, orderBy, limit), selectionArgs, findEditTable(table), cancellationSignal);
        } finally {
            releaseReference();
        }
    }

    public Cursor query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        return query(false, table, columns, selection, selectionArgs, groupBy, having, orderBy, (String) null);
    }

    public Cursor query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit) {
        return query(false, table, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
    }

    public Cursor rawQuery(String sql, String[] selectionArgs) {
        return rawQueryWithFactory((CursorFactory) null, sql, selectionArgs, (String) null, (CancellationSignal) null);
    }

    public Cursor rawQuery(String sql, String[] selectionArgs, CancellationSignal cancellationSignal) {
        return rawQueryWithFactory((CursorFactory) null, sql, selectionArgs, (String) null, cancellationSignal);
    }

    public Cursor rawQueryWithFactory(CursorFactory cursorFactory, String sql, String[] selectionArgs, String editTable) {
        return rawQueryWithFactory(cursorFactory, sql, selectionArgs, editTable, (CancellationSignal) null);
    }

    public Cursor rawQueryWithFactory(CursorFactory cursorFactory, String sql, String[] selectionArgs, String editTable, CancellationSignal cancellationSignal) {
        acquireReference();
        try {
            return new SQLiteDirectCursorDriver(this, sql, editTable, cancellationSignal).query(cursorFactory != null ? cursorFactory : this.mCursorFactory, selectionArgs);
        } finally {
            releaseReference();
        }
    }

    public long insert(String table, String nullColumnHack, ContentValues values) {
        try {
            return insertWithOnConflict(table, nullColumnHack, values, 0);
        } catch (SQLException e) {
            Log.e(TAG, "Error inserting " + values, e);
            return -1;
        }
    }

    public long insertOrThrow(String table, String nullColumnHack, ContentValues values) throws SQLException {
        return insertWithOnConflict(table, nullColumnHack, values, 0);
    }

    public long replace(String table, String nullColumnHack, ContentValues initialValues) {
        try {
            return insertWithOnConflict(table, nullColumnHack, initialValues, 5);
        } catch (SQLException e) {
            Log.e(TAG, "Error inserting " + initialValues, e);
            return -1;
        }
    }

    public long replaceOrThrow(String table, String nullColumnHack, ContentValues initialValues) throws SQLException {
        return insertWithOnConflict(table, nullColumnHack, initialValues, 5);
    }

    public long insertWithOnConflict(String table, String nullColumnHack, ContentValues initialValues, int conflictAlgorithm) {
        SQLiteStatement statement;
        acquireReference();
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("INSERT");
            sql.append(CONFLICT_VALUES[conflictAlgorithm]);
            sql.append(" INTO ");
            sql.append(table);
            sql.append('(');
            Object[] bindArgs = null;
            int size = (initialValues == null || initialValues.isEmpty()) ? 0 : initialValues.size();
            if (size > 0) {
                bindArgs = new Object[size];
                int i = 0;
                for (String colName : initialValues.keySet()) {
                    sql.append(i > 0 ? SmsManager.REGEX_PREFIX_DELIMITER : "");
                    sql.append(colName);
                    bindArgs[i] = initialValues.get(colName);
                    i++;
                }
                sql.append(')');
                sql.append(" VALUES (");
                int i2 = 0;
                while (i2 < size) {
                    sql.append(i2 > 0 ? ",?" : "?");
                    i2++;
                }
            } else {
                sql.append(nullColumnHack + ") VALUES (NULL");
            }
            sql.append(')');
            statement = new SQLiteStatement(this, sql.toString(), bindArgs);
            long executeInsert = statement.executeInsert();
            statement.close();
            releaseReference();
            return executeInsert;
        } catch (Throwable th) {
            releaseReference();
            throw th;
        }
    }

    public int delete(String table, String whereClause, String[] whereArgs) {
        SQLiteStatement statement;
        String str;
        acquireReference();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("DELETE FROM ");
            sb.append(table);
            if (!TextUtils.isEmpty(whereClause)) {
                str = " WHERE " + whereClause;
            } else {
                str = "";
            }
            sb.append(str);
            statement = new SQLiteStatement(this, sb.toString(), whereArgs);
            int executeUpdateDelete = statement.executeUpdateDelete();
            statement.close();
            releaseReference();
            return executeUpdateDelete;
        } catch (Throwable th) {
            releaseReference();
            throw th;
        }
    }

    public int update(String table, ContentValues values, String whereClause, String[] whereArgs) {
        return updateWithOnConflict(table, values, whereClause, whereArgs, 0);
    }

    public int updateWithOnConflict(String table, ContentValues values, String whereClause, String[] whereArgs, int conflictAlgorithm) {
        SQLiteStatement statement;
        if (values == null || values.isEmpty()) {
            throw new IllegalArgumentException("Empty values");
        }
        acquireReference();
        try {
            StringBuilder sql = new StringBuilder(120);
            sql.append("UPDATE ");
            sql.append(CONFLICT_VALUES[conflictAlgorithm]);
            sql.append(table);
            sql.append(" SET ");
            int setValuesSize = values.size();
            int bindArgsSize = whereArgs == null ? setValuesSize : whereArgs.length + setValuesSize;
            Object[] bindArgs = new Object[bindArgsSize];
            int i = 0;
            for (String colName : values.keySet()) {
                sql.append(i > 0 ? SmsManager.REGEX_PREFIX_DELIMITER : "");
                sql.append(colName);
                bindArgs[i] = values.get(colName);
                sql.append("=?");
                i++;
            }
            if (whereArgs != null) {
                for (int i2 = setValuesSize; i2 < bindArgsSize; i2++) {
                    bindArgs[i2] = whereArgs[i2 - setValuesSize];
                }
            }
            if (!TextUtils.isEmpty(whereClause)) {
                sql.append(" WHERE ");
                sql.append(whereClause);
            }
            statement = new SQLiteStatement(this, sql.toString(), bindArgs);
            int executeUpdateDelete = statement.executeUpdateDelete();
            statement.close();
            releaseReference();
            return executeUpdateDelete;
        } catch (Throwable th) {
            releaseReference();
            throw th;
        }
    }

    public void execSQL(String sql) throws SQLException {
        executeSql(sql, (Object[]) null);
    }

    public void execSQL(String sql, Object[] bindArgs) throws SQLException {
        if (bindArgs != null) {
            executeSql(sql, bindArgs);
            return;
        }
        throw new IllegalArgumentException("Empty bindArgs");
    }

    public int executeSql(String sql, Object[] bindArgs) throws SQLException {
        SQLiteStatement statement;
        acquireReference();
        try {
            int statementType = DatabaseUtils.getSqlStatementType(sql);
            if (statementType == 3) {
                boolean disableWal = false;
                synchronized (this.mLock) {
                    if (!this.mHasAttachedDbsLocked) {
                        this.mHasAttachedDbsLocked = true;
                        disableWal = true;
                        this.mConnectionPoolLocked.disableIdleConnectionHandler();
                    }
                }
                if (disableWal) {
                    disableWriteAheadLogging();
                }
            }
            try {
                statement = new SQLiteStatement(this, sql, bindArgs);
                int executeUpdateDelete = statement.executeUpdateDelete();
                statement.close();
                if (statementType == 8) {
                    this.mConnectionPoolLocked.closeAvailableNonPrimaryConnectionsAndLogExceptions();
                }
                releaseReference();
                return executeUpdateDelete;
            } catch (Throwable th) {
                if (statementType == 8) {
                    this.mConnectionPoolLocked.closeAvailableNonPrimaryConnectionsAndLogExceptions();
                }
                throw th;
            }
            throw th;
        } catch (Throwable th2) {
            releaseReference();
            throw th2;
        }
    }

    public void validateSql(String sql, CancellationSignal cancellationSignal) {
        getThreadSession().prepare(sql, getThreadDefaultConnectionFlags(true), cancellationSignal, (SQLiteStatementInfo) null);
    }

    public boolean isReadOnly() {
        boolean isReadOnlyLocked;
        synchronized (this.mLock) {
            isReadOnlyLocked = isReadOnlyLocked();
        }
        return isReadOnlyLocked;
    }

    private boolean isReadOnlyLocked() {
        return (this.mConfigurationLocked.openFlags & 1) == 1;
    }

    public boolean isInMemoryDatabase() {
        boolean isInMemoryDb;
        synchronized (this.mLock) {
            isInMemoryDb = this.mConfigurationLocked.isInMemoryDb();
        }
        return isInMemoryDb;
    }

    public boolean isOpen() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mConnectionPoolLocked != null;
        }
        return z;
    }

    public boolean needUpgrade(int newVersion) {
        return newVersion > getVersion();
    }

    public final String getPath() {
        String str;
        synchronized (this.mLock) {
            str = this.mConfigurationLocked.path;
        }
        return str;
    }

    public void setLocale(Locale locale) {
        if (locale != null) {
            synchronized (this.mLock) {
                throwIfNotOpenLocked();
                Locale oldLocale = this.mConfigurationLocked.locale;
                this.mConfigurationLocked.locale = locale;
                try {
                    this.mConnectionPoolLocked.reconfigure(this.mConfigurationLocked);
                } catch (RuntimeException ex) {
                    this.mConfigurationLocked.locale = oldLocale;
                    throw ex;
                }
            }
            return;
        }
        throw new IllegalArgumentException("locale must not be null.");
    }

    public void setMaxSqlCacheSize(int cacheSize) {
        if (cacheSize > 100 || cacheSize < 0) {
            throw new IllegalStateException("expected value between 0 and 100");
        }
        synchronized (this.mLock) {
            throwIfNotOpenLocked();
            int oldMaxSqlCacheSize = this.mConfigurationLocked.maxSqlCacheSize;
            this.mConfigurationLocked.maxSqlCacheSize = cacheSize;
            try {
                this.mConnectionPoolLocked.reconfigure(this.mConfigurationLocked);
            } catch (RuntimeException ex) {
                this.mConfigurationLocked.maxSqlCacheSize = oldMaxSqlCacheSize;
                throw ex;
            }
        }
    }

    public void setForeignKeyConstraintsEnabled(boolean enable) {
        synchronized (this.mLock) {
            throwIfNotOpenLocked();
            if (this.mConfigurationLocked.foreignKeyConstraintsEnabled != enable) {
                this.mConfigurationLocked.foreignKeyConstraintsEnabled = enable;
                try {
                    this.mConnectionPoolLocked.reconfigure(this.mConfigurationLocked);
                } catch (RuntimeException ex) {
                    this.mConfigurationLocked.foreignKeyConstraintsEnabled = !enable;
                    throw ex;
                }
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:22:0x005a, code lost:
        return false;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean enableWriteAheadLogging() {
        /*
            r5 = this;
            java.lang.Object r0 = r5.mLock
            monitor-enter(r0)
            r5.throwIfNotOpenLocked()     // Catch:{ all -> 0x0078 }
            android.database.sqlite.SQLiteDatabaseConfiguration r1 = r5.mConfigurationLocked     // Catch:{ all -> 0x0078 }
            int r1 = r1.openFlags     // Catch:{ all -> 0x0078 }
            r2 = 536870912(0x20000000, float:1.0842022E-19)
            r1 = r1 & r2
            r3 = 1
            if (r1 == 0) goto L_0x0012
            monitor-exit(r0)     // Catch:{ all -> 0x0078 }
            return r3
        L_0x0012:
            boolean r1 = r5.isReadOnlyLocked()     // Catch:{ all -> 0x0078 }
            r4 = 0
            if (r1 == 0) goto L_0x001b
            monitor-exit(r0)     // Catch:{ all -> 0x0078 }
            return r4
        L_0x001b:
            android.database.sqlite.SQLiteDatabaseConfiguration r1 = r5.mConfigurationLocked     // Catch:{ all -> 0x0078 }
            boolean r1 = r1.isInMemoryDb()     // Catch:{ all -> 0x0078 }
            if (r1 == 0) goto L_0x002c
            java.lang.String r1 = "SQLiteDatabase"
            java.lang.String r2 = "can't enable WAL for memory databases."
            android.util.Log.i(r1, r2)     // Catch:{ all -> 0x0078 }
            monitor-exit(r0)     // Catch:{ all -> 0x0078 }
            return r4
        L_0x002c:
            boolean r1 = r5.mHasAttachedDbsLocked     // Catch:{ all -> 0x0078 }
            if (r1 == 0) goto L_0x005b
            java.lang.String r1 = "SQLiteDatabase"
            r2 = 3
            boolean r1 = android.util.Log.isLoggable(r1, r2)     // Catch:{ all -> 0x0078 }
            if (r1 == 0) goto L_0x0059
            java.lang.String r1 = "SQLiteDatabase"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x0078 }
            r2.<init>()     // Catch:{ all -> 0x0078 }
            java.lang.String r3 = "this database: "
            r2.append(r3)     // Catch:{ all -> 0x0078 }
            android.database.sqlite.SQLiteDatabaseConfiguration r3 = r5.mConfigurationLocked     // Catch:{ all -> 0x0078 }
            java.lang.String r3 = r3.label     // Catch:{ all -> 0x0078 }
            r2.append(r3)     // Catch:{ all -> 0x0078 }
            java.lang.String r3 = " has attached databases. can't  enable WAL."
            r2.append(r3)     // Catch:{ all -> 0x0078 }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x0078 }
            android.util.Log.d(r1, r2)     // Catch:{ all -> 0x0078 }
        L_0x0059:
            monitor-exit(r0)     // Catch:{ all -> 0x0078 }
            return r4
        L_0x005b:
            android.database.sqlite.SQLiteDatabaseConfiguration r1 = r5.mConfigurationLocked     // Catch:{ all -> 0x0078 }
            int r4 = r1.openFlags     // Catch:{ all -> 0x0078 }
            r2 = r2 | r4
            r1.openFlags = r2     // Catch:{ all -> 0x0078 }
            android.database.sqlite.SQLiteConnectionPool r1 = r5.mConnectionPoolLocked     // Catch:{ RuntimeException -> 0x006c }
            android.database.sqlite.SQLiteDatabaseConfiguration r2 = r5.mConfigurationLocked     // Catch:{ RuntimeException -> 0x006c }
            r1.reconfigure(r2)     // Catch:{ RuntimeException -> 0x006c }
            monitor-exit(r0)     // Catch:{ all -> 0x0078 }
            return r3
        L_0x006c:
            r1 = move-exception
            android.database.sqlite.SQLiteDatabaseConfiguration r2 = r5.mConfigurationLocked     // Catch:{ all -> 0x0078 }
            int r3 = r2.openFlags     // Catch:{ all -> 0x0078 }
            r4 = -536870913(0xffffffffdfffffff, float:-3.6893486E19)
            r3 = r3 & r4
            r2.openFlags = r3     // Catch:{ all -> 0x0078 }
            throw r1     // Catch:{ all -> 0x0078 }
        L_0x0078:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0078 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.database.sqlite.SQLiteDatabase.enableWriteAheadLogging():boolean");
    }

    public void disableWriteAheadLogging() {
        synchronized (this.mLock) {
            throwIfNotOpenLocked();
            int oldFlags = this.mConfigurationLocked.openFlags;
            boolean compatibilityWalEnabled = false;
            boolean walEnabled = (536870912 & oldFlags) != 0;
            if ((Integer.MIN_VALUE & oldFlags) != 0) {
                compatibilityWalEnabled = true;
            }
            if (walEnabled || compatibilityWalEnabled) {
                this.mConfigurationLocked.openFlags &= -536870913;
                this.mConfigurationLocked.openFlags &= Integer.MAX_VALUE;
                try {
                    this.mConnectionPoolLocked.reconfigure(this.mConfigurationLocked);
                } catch (RuntimeException ex) {
                    this.mConfigurationLocked.openFlags = oldFlags;
                    throw ex;
                }
            }
        }
    }

    public boolean isWriteAheadLoggingEnabled() {
        boolean z;
        synchronized (this.mLock) {
            throwIfNotOpenLocked();
            z = (this.mConfigurationLocked.openFlags & 536870912) != 0;
        }
        return z;
    }

    static ArrayList<SQLiteDebug.DbStats> getDbStats() {
        ArrayList<SQLiteDebug.DbStats> dbStatsList = new ArrayList<>();
        Iterator<SQLiteDatabase> it = getActiveDatabases().iterator();
        while (it.hasNext()) {
            it.next().collectDbStats(dbStatsList);
        }
        return dbStatsList;
    }

    @UnsupportedAppUsage
    private void collectDbStats(ArrayList<SQLiteDebug.DbStats> dbStatsList) {
        synchronized (this.mLock) {
            if (this.mConnectionPoolLocked != null) {
                this.mConnectionPoolLocked.collectDbStats(dbStatsList);
            }
        }
    }

    @UnsupportedAppUsage
    private static ArrayList<SQLiteDatabase> getActiveDatabases() {
        ArrayList<SQLiteDatabase> databases = new ArrayList<>();
        synchronized (sActiveDatabases) {
            databases.addAll(sActiveDatabases.keySet());
        }
        return databases;
    }

    static void dumpAll(Printer printer, boolean verbose, boolean isSystem) {
        ArraySet<String> directories = new ArraySet<>();
        Iterator<SQLiteDatabase> it = getActiveDatabases().iterator();
        while (it.hasNext()) {
            it.next().dump(printer, verbose, isSystem, directories);
        }
        if (directories.size() > 0) {
            String[] dirs = (String[]) directories.toArray(new String[directories.size()]);
            Arrays.sort(dirs);
            for (String dir : dirs) {
                dumpDatabaseDirectory(printer, new File(dir), isSystem);
            }
        }
    }

    private void dump(Printer printer, boolean verbose, boolean isSystem, ArraySet directories) {
        synchronized (this.mLock) {
            if (this.mConnectionPoolLocked != null) {
                printer.println("");
                this.mConnectionPoolLocked.dump(printer, verbose, directories);
            }
        }
    }

    private static void dumpDatabaseDirectory(Printer pw, File dir, boolean isSystem) {
        pw.println("");
        pw.println("Database files in " + dir.getAbsolutePath() + SettingsStringUtil.DELIMITER);
        File[] files = dir.listFiles();
        if (files == null || files.length == 0) {
            pw.println("  [none]");
            return;
        }
        Arrays.sort(files, $$Lambda$SQLiteDatabase$1FsSJH2q7x3eeDFXCAu9l4piDsE.INSTANCE);
        for (File f : files) {
            if (isSystem) {
                String name = f.getName();
                if (!name.endsWith(".db") && !name.endsWith(".db-wal") && !name.endsWith(".db-journal") && !name.endsWith("-wipecheck")) {
                }
            }
            pw.println(String.format("  %-40s %7db %s", new Object[]{f.getName(), Long.valueOf(f.length()), getFileTimestamps(f.getAbsolutePath())}));
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0027, code lost:
        r1 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:?, code lost:
        r1 = rawQuery("pragma database_list;", (java.lang.String[]) null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0034, code lost:
        if (r1.moveToNext() == false) goto L_0x0049;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0036, code lost:
        r0.add(new android.util.Pair(r1.getString(1), r1.getString(2)));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0049, code lost:
        if (r1 == null) goto L_0x004e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:?, code lost:
        r1.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x004e, code lost:
        releaseReference();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0052, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0053, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0054, code lost:
        if (r1 != null) goto L_0x0056;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:?, code lost:
        r1.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x005a, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x005c, code lost:
        throw r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x005d, code lost:
        releaseReference();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0060, code lost:
        throw r1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.util.List<android.util.Pair<java.lang.String, java.lang.String>> getAttachedDbs() {
        /*
            r5 = this;
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            java.lang.Object r1 = r5.mLock
            monitor-enter(r1)
            android.database.sqlite.SQLiteConnectionPool r2 = r5.mConnectionPoolLocked     // Catch:{ all -> 0x0061 }
            r3 = 0
            if (r2 != 0) goto L_0x000f
            monitor-exit(r1)     // Catch:{ all -> 0x0061 }
            return r3
        L_0x000f:
            boolean r2 = r5.mHasAttachedDbsLocked     // Catch:{ all -> 0x0061 }
            if (r2 != 0) goto L_0x0023
            android.util.Pair r2 = new android.util.Pair     // Catch:{ all -> 0x0061 }
            java.lang.String r3 = "main"
            android.database.sqlite.SQLiteDatabaseConfiguration r4 = r5.mConfigurationLocked     // Catch:{ all -> 0x0061 }
            java.lang.String r4 = r4.path     // Catch:{ all -> 0x0061 }
            r2.<init>(r3, r4)     // Catch:{ all -> 0x0061 }
            r0.add(r2)     // Catch:{ all -> 0x0061 }
            monitor-exit(r1)     // Catch:{ all -> 0x0061 }
            return r0
        L_0x0023:
            r5.acquireReference()     // Catch:{ all -> 0x0061 }
            monitor-exit(r1)     // Catch:{ all -> 0x0061 }
            r1 = r3
            java.lang.String r2 = "pragma database_list;"
            android.database.Cursor r2 = r5.rawQuery(r2, r3)     // Catch:{ all -> 0x0053 }
            r1 = r2
        L_0x0030:
            boolean r2 = r1.moveToNext()     // Catch:{ all -> 0x0053 }
            if (r2 == 0) goto L_0x0049
            android.util.Pair r2 = new android.util.Pair     // Catch:{ all -> 0x0053 }
            r3 = 1
            java.lang.String r3 = r1.getString(r3)     // Catch:{ all -> 0x0053 }
            r4 = 2
            java.lang.String r4 = r1.getString(r4)     // Catch:{ all -> 0x0053 }
            r2.<init>(r3, r4)     // Catch:{ all -> 0x0053 }
            r0.add(r2)     // Catch:{ all -> 0x0053 }
            goto L_0x0030
        L_0x0049:
            if (r1 == 0) goto L_0x004e
            r1.close()     // Catch:{ all -> 0x005a }
        L_0x004e:
            r5.releaseReference()
            return r0
        L_0x0053:
            r2 = move-exception
            if (r1 == 0) goto L_0x005c
            r1.close()     // Catch:{ all -> 0x005a }
            goto L_0x005c
        L_0x005a:
            r1 = move-exception
            goto L_0x005d
        L_0x005c:
            throw r2     // Catch:{ all -> 0x005a }
        L_0x005d:
            r5.releaseReference()
            throw r1
        L_0x0061:
            r2 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0061 }
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: android.database.sqlite.SQLiteDatabase.getAttachedDbs():java.util.List");
    }

    public boolean isDatabaseIntegrityOk() {
        List<Pair<String, String>> attachedDbs;
        SQLiteStatement prog;
        acquireReference();
        try {
            attachedDbs = getAttachedDbs();
            if (attachedDbs != null) {
                for (int i = 0; i < attachedDbs.size(); i++) {
                    Pair<String, String> p = attachedDbs.get(i);
                    prog = null;
                    SQLiteStatement prog2 = compileStatement("PRAGMA " + ((String) p.first) + ".integrity_check(1);");
                    String rslt = prog2.simpleQueryForString();
                    if (!rslt.equalsIgnoreCase("ok")) {
                        Log.e(TAG, "PRAGMA integrity_check on " + ((String) p.second) + " returned: " + rslt);
                        if (prog2 != null) {
                            prog2.close();
                        }
                        releaseReference();
                        return false;
                    }
                    if (prog2 != null) {
                        prog2.close();
                    }
                }
                releaseReference();
                return true;
            }
            throw new IllegalStateException("databaselist for: " + getPath() + " couldn't be retrieved. probably because the database is closed");
        } catch (SQLiteException e) {
            attachedDbs = new ArrayList<>();
            attachedDbs.add(new Pair("main", getPath()));
        } catch (Throwable th) {
            releaseReference();
            throw th;
        }
    }

    public String toString() {
        return "SQLiteDatabase: " + getPath();
    }

    private void throwIfNotOpenLocked() {
        if (this.mConnectionPoolLocked == null) {
            throw new IllegalStateException("The database '" + this.mConfigurationLocked.label + "' is not open.");
        }
    }

    public static final class OpenParams {
        /* access modifiers changed from: private */
        public final CursorFactory mCursorFactory;
        /* access modifiers changed from: private */
        public final DatabaseErrorHandler mErrorHandler;
        /* access modifiers changed from: private */
        public final long mIdleConnectionTimeout;
        /* access modifiers changed from: private */
        public final String mJournalMode;
        /* access modifiers changed from: private */
        public final int mLookasideSlotCount;
        /* access modifiers changed from: private */
        public final int mLookasideSlotSize;
        /* access modifiers changed from: private */
        public final int mOpenFlags;
        /* access modifiers changed from: private */
        public final String mSyncMode;

        private OpenParams(int openFlags, CursorFactory cursorFactory, DatabaseErrorHandler errorHandler, int lookasideSlotSize, int lookasideSlotCount, long idleConnectionTimeout, String journalMode, String syncMode) {
            this.mOpenFlags = openFlags;
            this.mCursorFactory = cursorFactory;
            this.mErrorHandler = errorHandler;
            this.mLookasideSlotSize = lookasideSlotSize;
            this.mLookasideSlotCount = lookasideSlotCount;
            this.mIdleConnectionTimeout = idleConnectionTimeout;
            this.mJournalMode = journalMode;
            this.mSyncMode = syncMode;
        }

        public int getLookasideSlotSize() {
            return this.mLookasideSlotSize;
        }

        public int getLookasideSlotCount() {
            return this.mLookasideSlotCount;
        }

        public int getOpenFlags() {
            return this.mOpenFlags;
        }

        public CursorFactory getCursorFactory() {
            return this.mCursorFactory;
        }

        public DatabaseErrorHandler getErrorHandler() {
            return this.mErrorHandler;
        }

        public long getIdleConnectionTimeout() {
            return this.mIdleConnectionTimeout;
        }

        public String getJournalMode() {
            return this.mJournalMode;
        }

        public String getSynchronousMode() {
            return this.mSyncMode;
        }

        public Builder toBuilder() {
            return new Builder(this);
        }

        public static final class Builder {
            private CursorFactory mCursorFactory;
            private DatabaseErrorHandler mErrorHandler;
            private long mIdleConnectionTimeout = -1;
            private String mJournalMode;
            private int mLookasideSlotCount = -1;
            private int mLookasideSlotSize = -1;
            private int mOpenFlags;
            private String mSyncMode;

            public Builder() {
            }

            public Builder(OpenParams params) {
                this.mLookasideSlotSize = params.mLookasideSlotSize;
                this.mLookasideSlotCount = params.mLookasideSlotCount;
                this.mOpenFlags = params.mOpenFlags;
                this.mCursorFactory = params.mCursorFactory;
                this.mErrorHandler = params.mErrorHandler;
                this.mJournalMode = params.mJournalMode;
                this.mSyncMode = params.mSyncMode;
            }

            public Builder setLookasideConfig(int slotSize, int slotCount) {
                boolean z = false;
                Preconditions.checkArgument(slotSize >= 0, "lookasideSlotCount cannot be negative");
                Preconditions.checkArgument(slotCount >= 0, "lookasideSlotSize cannot be negative");
                if ((slotSize > 0 && slotCount > 0) || (slotCount == 0 && slotSize == 0)) {
                    z = true;
                }
                Preconditions.checkArgument(z, "Invalid configuration: " + slotSize + ", " + slotCount);
                this.mLookasideSlotSize = slotSize;
                this.mLookasideSlotCount = slotCount;
                return this;
            }

            public boolean isWriteAheadLoggingEnabled() {
                return (this.mOpenFlags & 536870912) != 0;
            }

            public Builder setOpenFlags(int openFlags) {
                this.mOpenFlags = openFlags;
                return this;
            }

            public Builder addOpenFlags(int openFlags) {
                this.mOpenFlags |= openFlags;
                return this;
            }

            public Builder removeOpenFlags(int openFlags) {
                this.mOpenFlags &= ~openFlags;
                return this;
            }

            public void setWriteAheadLoggingEnabled(boolean enabled) {
                if (enabled) {
                    addOpenFlags(536870912);
                } else {
                    removeOpenFlags(536870912);
                }
            }

            public Builder setCursorFactory(CursorFactory cursorFactory) {
                this.mCursorFactory = cursorFactory;
                return this;
            }

            public Builder setErrorHandler(DatabaseErrorHandler errorHandler) {
                this.mErrorHandler = errorHandler;
                return this;
            }

            @Deprecated
            public Builder setIdleConnectionTimeout(long idleConnectionTimeoutMs) {
                Preconditions.checkArgument(idleConnectionTimeoutMs >= 0, "idle connection timeout cannot be negative");
                this.mIdleConnectionTimeout = idleConnectionTimeoutMs;
                return this;
            }

            public Builder setJournalMode(String journalMode) {
                Preconditions.checkNotNull(journalMode);
                this.mJournalMode = journalMode;
                return this;
            }

            public Builder setSynchronousMode(String syncMode) {
                Preconditions.checkNotNull(syncMode);
                this.mSyncMode = syncMode;
                return this;
            }

            public OpenParams build() {
                return new OpenParams(this.mOpenFlags, this.mCursorFactory, this.mErrorHandler, this.mLookasideSlotSize, this.mLookasideSlotCount, this.mIdleConnectionTimeout, this.mJournalMode, this.mSyncMode);
            }
        }
    }

    public static void wipeDetected(String filename, String reason) {
        StringBuilder sb = new StringBuilder();
        sb.append("DB wipe detected: package=");
        sb.append(ActivityThread.currentPackageName());
        sb.append(" reason=");
        sb.append(reason);
        sb.append(" file=");
        sb.append(filename);
        sb.append(WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER);
        sb.append(getFileTimestamps(filename));
        sb.append(" checkfile ");
        sb.append(getFileTimestamps(filename + "-wipecheck"));
        wtfAsSystemServer(TAG, sb.toString(), new Throwable("STACKTRACE"));
    }

    public static String getFileTimestamps(String path) {
        try {
            BasicFileAttributes attr = Files.readAttributes(FileSystems.getDefault().getPath(path, new String[0]), BasicFileAttributes.class, new LinkOption[0]);
            return "ctime=" + attr.creationTime() + " mtime=" + attr.lastModifiedTime() + " atime=" + attr.lastAccessTime();
        } catch (IOException e) {
            return "[unable to obtain timestamp]";
        }
    }

    static void wtfAsSystemServer(String tag, String message, Throwable stacktrace) {
        Log.e(tag, message, stacktrace);
        ContentResolver.onDbCorruption(tag, message, stacktrace);
    }
}
