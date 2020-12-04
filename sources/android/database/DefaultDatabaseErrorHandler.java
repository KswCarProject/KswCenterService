package android.database;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseConfiguration;
import android.util.Log;
import java.io.File;

public final class DefaultDatabaseErrorHandler implements DatabaseErrorHandler {
    private static final String TAG = "DefaultDatabaseErrorHandler";

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x003f, code lost:
        if (r0 != null) goto L_0x0041;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0041, code lost:
        r2 = r0.iterator();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0049, code lost:
        if (r2.hasNext() != false) goto L_0x004b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x004b, code lost:
        deleteDatabaseFile((java.lang.String) r2.next().second);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0059, code lost:
        deleteDatabaseFile(r6.getPath());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0060, code lost:
        throw r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0038, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0038 A[ExcHandler: all (r1v7 'th' java.lang.Throwable A[CUSTOM_DECLARE]), PHI: r0 
      PHI: (r0v5 'attachedDbs' java.util.List<android.util.Pair<java.lang.String, java.lang.String>>) = (r0v3 'attachedDbs' java.util.List<android.util.Pair<java.lang.String, java.lang.String>>), (r0v4 'attachedDbs' java.util.List<android.util.Pair<java.lang.String, java.lang.String>>), (r0v4 'attachedDbs' java.util.List<android.util.Pair<java.lang.String, java.lang.String>>) binds: [B:5:0x0032, B:10:0x003b, B:11:?] A[DONT_GENERATE, DONT_INLINE], Splitter:B:5:0x0032] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onCorruption(android.database.sqlite.SQLiteDatabase r6) {
        /*
            r5 = this;
            java.lang.String r0 = "DefaultDatabaseErrorHandler"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "Corruption reported by sqlite on database: "
            r1.append(r2)
            java.lang.String r2 = r6.getPath()
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            android.util.Log.e(r0, r1)
            java.lang.String r0 = r6.getPath()
            java.lang.String r1 = "corruption"
            android.database.sqlite.SQLiteDatabase.wipeDetected(r0, r1)
            boolean r0 = r6.isOpen()
            if (r0 != 0) goto L_0x0031
            java.lang.String r0 = r6.getPath()
            r5.deleteDatabaseFile(r0)
            return
        L_0x0031:
            r0 = 0
            java.util.List r1 = r6.getAttachedDbs()     // Catch:{ SQLiteException -> 0x003a, all -> 0x0038 }
            r0 = r1
            goto L_0x003b
        L_0x0038:
            r1 = move-exception
            goto L_0x003f
        L_0x003a:
            r1 = move-exception
        L_0x003b:
            r6.close()     // Catch:{ SQLiteException -> 0x0061, all -> 0x0038 }
            goto L_0x0062
        L_0x003f:
            if (r0 == 0) goto L_0x0059
            java.util.Iterator r2 = r0.iterator()
        L_0x0045:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L_0x0060
            java.lang.Object r3 = r2.next()
            android.util.Pair r3 = (android.util.Pair) r3
            S r4 = r3.second
            java.lang.String r4 = (java.lang.String) r4
            r5.deleteDatabaseFile(r4)
            goto L_0x0045
        L_0x0059:
            java.lang.String r2 = r6.getPath()
            r5.deleteDatabaseFile(r2)
        L_0x0060:
            throw r1
        L_0x0061:
            r1 = move-exception
        L_0x0062:
            if (r0 == 0) goto L_0x007c
            java.util.Iterator r1 = r0.iterator()
        L_0x0068:
            boolean r2 = r1.hasNext()
            if (r2 == 0) goto L_0x0084
            java.lang.Object r2 = r1.next()
            android.util.Pair r2 = (android.util.Pair) r2
            S r3 = r2.second
            java.lang.String r3 = (java.lang.String) r3
            r5.deleteDatabaseFile(r3)
            goto L_0x0068
        L_0x007c:
            java.lang.String r1 = r6.getPath()
            r5.deleteDatabaseFile(r1)
        L_0x0084:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.database.DefaultDatabaseErrorHandler.onCorruption(android.database.sqlite.SQLiteDatabase):void");
    }

    private void deleteDatabaseFile(String fileName) {
        if (!fileName.equalsIgnoreCase(SQLiteDatabaseConfiguration.MEMORY_DB_PATH) && fileName.trim().length() != 0) {
            Log.e(TAG, "deleting the database file: " + fileName);
            try {
                SQLiteDatabase.deleteDatabase(new File(fileName), false);
            } catch (Exception e) {
                Log.w(TAG, "delete failed: " + e.getMessage());
            }
        }
    }
}
