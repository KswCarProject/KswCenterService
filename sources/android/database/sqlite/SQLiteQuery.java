package android.database.sqlite;

import android.database.CursorWindow;
import android.p007os.CancellationSignal;
import android.util.Log;

/* loaded from: classes.dex */
public final class SQLiteQuery extends SQLiteProgram {
    private static final String TAG = "SQLiteQuery";
    private final CancellationSignal mCancellationSignal;

    SQLiteQuery(SQLiteDatabase db, String query, CancellationSignal cancellationSignal) {
        super(db, query, null, cancellationSignal);
        this.mCancellationSignal = cancellationSignal;
    }

    int fillWindow(CursorWindow window, int startPos, int requiredPos, boolean countAllRows) {
        acquireReference();
        try {
            window.acquireReference();
            try {
                int numRows = getSession().executeForCursorWindow(getSql(), getBindArgs(), window, startPos, requiredPos, countAllRows, getConnectionFlags(), this.mCancellationSignal);
                window.releaseReference();
                return numRows;
            } catch (SQLiteDatabaseCorruptException ex) {
                onCorruption();
                throw ex;
            } catch (SQLiteException ex2) {
                Log.m70e(TAG, "exception: " + ex2.getMessage() + "; query: " + getSql());
                throw ex2;
            }
        } finally {
            releaseReference();
        }
    }

    public String toString() {
        return "SQLiteQuery: " + getSql();
    }
}
