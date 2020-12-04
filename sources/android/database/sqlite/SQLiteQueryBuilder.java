package android.database.sqlite;

import android.annotation.UnsupportedAppUsage;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.CancellationSignal;
import android.provider.BaseColumns;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import libcore.util.EmptyArray;

public class SQLiteQueryBuilder {
    private static final String TAG = "SQLiteQueryBuilder";
    private static final Pattern sAggregationPattern = Pattern.compile("(?i)(AVG|COUNT|MAX|MIN|SUM|TOTAL|GROUP_CONCAT)\\((.+)\\)");
    private static final Pattern sLimitPattern = Pattern.compile("\\s*\\d+\\s*(,\\s*\\d+\\s*)?");
    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
    private boolean mDistinct = false;
    private SQLiteDatabase.CursorFactory mFactory = null;
    private boolean mProjectionAggregationAllowed = false;
    private List<Pattern> mProjectionGreylist = null;
    private Map<String, String> mProjectionMap = null;
    private boolean mStrict;
    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
    private String mTables = "";
    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
    private StringBuilder mWhereClause = null;

    public void setDistinct(boolean distinct) {
        this.mDistinct = distinct;
    }

    public boolean isDistinct() {
        return this.mDistinct;
    }

    public String getTables() {
        return this.mTables;
    }

    public void setTables(String inTables) {
        this.mTables = inTables;
    }

    public void appendWhere(CharSequence inWhere) {
        if (this.mWhereClause == null) {
            this.mWhereClause = new StringBuilder(inWhere.length() + 16);
        }
        this.mWhereClause.append(inWhere);
    }

    public void appendWhereEscapeString(String inWhere) {
        if (this.mWhereClause == null) {
            this.mWhereClause = new StringBuilder(inWhere.length() + 16);
        }
        DatabaseUtils.appendEscapedSQLString(this.mWhereClause, inWhere);
    }

    public void appendWhereStandalone(CharSequence inWhere) {
        if (this.mWhereClause == null) {
            this.mWhereClause = new StringBuilder(inWhere.length() + 16);
        }
        if (this.mWhereClause.length() > 0) {
            this.mWhereClause.append(" AND ");
        }
        StringBuilder sb = this.mWhereClause;
        sb.append('(');
        sb.append(inWhere);
        sb.append(')');
    }

    public void setProjectionMap(Map<String, String> columnMap) {
        this.mProjectionMap = columnMap;
    }

    public Map<String, String> getProjectionMap() {
        return this.mProjectionMap;
    }

    public void setProjectionGreylist(List<Pattern> projectionGreylist) {
        this.mProjectionGreylist = projectionGreylist;
    }

    public List<Pattern> getProjectionGreylist() {
        return this.mProjectionGreylist;
    }

    public void setProjectionAggregationAllowed(boolean projectionAggregationAllowed) {
        this.mProjectionAggregationAllowed = projectionAggregationAllowed;
    }

    public boolean isProjectionAggregationAllowed() {
        return this.mProjectionAggregationAllowed;
    }

    public void setCursorFactory(SQLiteDatabase.CursorFactory factory) {
        this.mFactory = factory;
    }

    public SQLiteDatabase.CursorFactory getCursorFactory() {
        return this.mFactory;
    }

    public void setStrict(boolean flag) {
        this.mStrict = flag;
    }

    public boolean isStrict() {
        return this.mStrict;
    }

    public static String buildQueryString(boolean distinct, String tables, String[] columns, String where, String groupBy, String having, String orderBy, String limit) {
        if (TextUtils.isEmpty(groupBy) && !TextUtils.isEmpty(having)) {
            throw new IllegalArgumentException("HAVING clauses are only permitted when using a groupBy clause");
        } else if (TextUtils.isEmpty(limit) || sLimitPattern.matcher(limit).matches()) {
            StringBuilder query = new StringBuilder(120);
            query.append("SELECT ");
            if (distinct) {
                query.append("DISTINCT ");
            }
            if (columns == null || columns.length == 0) {
                query.append("* ");
            } else {
                appendColumns(query, columns);
            }
            query.append("FROM ");
            query.append(tables);
            appendClause(query, " WHERE ", where);
            appendClause(query, " GROUP BY ", groupBy);
            appendClause(query, " HAVING ", having);
            appendClause(query, " ORDER BY ", orderBy);
            appendClause(query, " LIMIT ", limit);
            return query.toString();
        } else {
            throw new IllegalArgumentException("invalid LIMIT clauses:" + limit);
        }
    }

    private static void appendClause(StringBuilder s, String name, String clause) {
        if (!TextUtils.isEmpty(clause)) {
            s.append(name);
            s.append(clause);
        }
    }

    public static void appendColumns(StringBuilder s, String[] columns) {
        int n = columns.length;
        for (int i = 0; i < n; i++) {
            String column = columns[i];
            if (column != null) {
                if (i > 0) {
                    s.append(", ");
                }
                s.append(column);
            }
        }
        s.append(' ');
    }

    public Cursor query(SQLiteDatabase db, String[] projectionIn, String selection, String[] selectionArgs, String groupBy, String having, String sortOrder) {
        return query(db, projectionIn, selection, selectionArgs, groupBy, having, sortOrder, (String) null, (CancellationSignal) null);
    }

    public Cursor query(SQLiteDatabase db, String[] projectionIn, String selection, String[] selectionArgs, String groupBy, String having, String sortOrder, String limit) {
        return query(db, projectionIn, selection, selectionArgs, groupBy, having, sortOrder, limit, (CancellationSignal) null);
    }

    public Cursor query(SQLiteDatabase db, String[] projectionIn, String selection, String[] selectionArgs, String groupBy, String having, String sortOrder, String limit, CancellationSignal cancellationSignal) {
        String sql;
        String str = selection;
        if (this.mTables == null) {
            return null;
        }
        String unwrappedSql = buildQuery(projectionIn, selection, groupBy, having, sortOrder, limit);
        if (!this.mStrict || str == null || selection.length() <= 0) {
            SQLiteDatabase sQLiteDatabase = db;
            CancellationSignal cancellationSignal2 = cancellationSignal;
            sql = unwrappedSql;
        } else {
            SQLiteDatabase sQLiteDatabase2 = db;
            db.validateSql(unwrappedSql, cancellationSignal);
            sql = buildQuery(projectionIn, wrap(selection), groupBy, having, sortOrder, limit);
        }
        String[] sqlArgs = selectionArgs;
        if (Log.isLoggable(TAG, 3)) {
            if (Build.IS_DEBUGGABLE) {
                Log.d(TAG, sql + " with args " + Arrays.toString(sqlArgs));
            } else {
                Log.d(TAG, sql);
            }
        }
        return db.rawQueryWithFactory(this.mFactory, sql, sqlArgs, SQLiteDatabase.findEditTable(this.mTables), cancellationSignal);
    }

    public int update(SQLiteDatabase db, ContentValues values, String selection, String[] selectionArgs) {
        String sql;
        Objects.requireNonNull(this.mTables, "No tables defined");
        Objects.requireNonNull(db, "No database defined");
        Objects.requireNonNull(values, "No values defined");
        String unwrappedSql = buildUpdate(values, selection);
        if (this.mStrict) {
            db.validateSql(unwrappedSql, (CancellationSignal) null);
            sql = buildUpdate(values, wrap(selection));
        } else {
            sql = unwrappedSql;
        }
        if (selectionArgs == null) {
            selectionArgs = EmptyArray.STRING;
        }
        ArrayMap<String, Object> rawValues = values.getValues();
        int valuesLength = rawValues.size();
        Object[] sqlArgs = new Object[(selectionArgs.length + valuesLength)];
        for (int i = 0; i < sqlArgs.length; i++) {
            if (i < valuesLength) {
                sqlArgs[i] = rawValues.valueAt(i);
            } else {
                sqlArgs[i] = selectionArgs[i - valuesLength];
            }
        }
        if (Log.isLoggable(TAG, 3)) {
            if (Build.IS_DEBUGGABLE) {
                Log.d(TAG, sql + " with args " + Arrays.toString(sqlArgs));
            } else {
                Log.d(TAG, sql);
            }
        }
        return db.executeSql(sql, sqlArgs);
    }

    public int delete(SQLiteDatabase db, String selection, String[] selectionArgs) {
        String sql;
        Objects.requireNonNull(this.mTables, "No tables defined");
        Objects.requireNonNull(db, "No database defined");
        String unwrappedSql = buildDelete(selection);
        if (this.mStrict) {
            db.validateSql(unwrappedSql, (CancellationSignal) null);
            sql = buildDelete(wrap(selection));
        } else {
            sql = unwrappedSql;
        }
        String[] sqlArgs = selectionArgs;
        if (Log.isLoggable(TAG, 3)) {
            if (Build.IS_DEBUGGABLE) {
                Log.d(TAG, sql + " with args " + Arrays.toString(sqlArgs));
            } else {
                Log.d(TAG, sql);
            }
        }
        return db.executeSql(sql, sqlArgs);
    }

    public String buildQuery(String[] projectionIn, String selection, String groupBy, String having, String sortOrder, String limit) {
        String str = selection;
        return buildQueryString(this.mDistinct, this.mTables, computeProjection(projectionIn), computeWhere(selection), groupBy, having, sortOrder, limit);
    }

    @Deprecated
    public String buildQuery(String[] projectionIn, String selection, String[] selectionArgs, String groupBy, String having, String sortOrder, String limit) {
        return buildQuery(projectionIn, selection, groupBy, having, sortOrder, limit);
    }

    public String buildUpdate(ContentValues values, String selection) {
        if (values == null || values.isEmpty()) {
            throw new IllegalArgumentException("Empty values");
        }
        StringBuilder sql = new StringBuilder(120);
        sql.append("UPDATE ");
        sql.append(this.mTables);
        sql.append(" SET ");
        ArrayMap<String, Object> rawValues = values.getValues();
        for (int i = 0; i < rawValues.size(); i++) {
            if (i > 0) {
                sql.append(',');
            }
            sql.append(rawValues.keyAt(i));
            sql.append("=?");
        }
        appendClause(sql, " WHERE ", computeWhere(selection));
        return sql.toString();
    }

    public String buildDelete(String selection) {
        StringBuilder sql = new StringBuilder(120);
        sql.append("DELETE FROM ");
        sql.append(this.mTables);
        appendClause(sql, " WHERE ", computeWhere(selection));
        return sql.toString();
    }

    public String buildUnionSubQuery(String typeDiscriminatorColumn, String[] unionColumns, Set<String> columnsPresentInTable, int computedColumnsOffset, String typeDiscriminatorValue, String selection, String groupBy, String having) {
        String str = typeDiscriminatorColumn;
        String[] strArr = unionColumns;
        int unionColumnsCount = strArr.length;
        String[] projectionIn = new String[unionColumnsCount];
        for (int i = 0; i < unionColumnsCount; i++) {
            String unionColumn = strArr[i];
            if (unionColumn.equals(typeDiscriminatorColumn)) {
                projectionIn[i] = "'" + typeDiscriminatorValue + "' AS " + typeDiscriminatorColumn;
                Set<String> set = columnsPresentInTable;
                int i2 = computedColumnsOffset;
            } else {
                String str2 = typeDiscriminatorValue;
                if (i <= computedColumnsOffset) {
                    Set<String> set2 = columnsPresentInTable;
                } else if (!columnsPresentInTable.contains(unionColumn)) {
                    projectionIn[i] = "NULL AS " + unionColumn;
                }
                projectionIn[i] = unionColumn;
            }
        }
        Set<String> set3 = columnsPresentInTable;
        int i3 = computedColumnsOffset;
        String str3 = typeDiscriminatorValue;
        return buildQuery(projectionIn, selection, groupBy, having, (String) null, (String) null);
    }

    @Deprecated
    public String buildUnionSubQuery(String typeDiscriminatorColumn, String[] unionColumns, Set<String> columnsPresentInTable, int computedColumnsOffset, String typeDiscriminatorValue, String selection, String[] selectionArgs, String groupBy, String having) {
        return buildUnionSubQuery(typeDiscriminatorColumn, unionColumns, columnsPresentInTable, computedColumnsOffset, typeDiscriminatorValue, selection, groupBy, having);
    }

    public String buildUnionQuery(String[] subQueries, String sortOrder, String limit) {
        StringBuilder query = new StringBuilder(128);
        int subQueryCount = subQueries.length;
        String unionOperator = this.mDistinct ? " UNION " : " UNION ALL ";
        for (int i = 0; i < subQueryCount; i++) {
            if (i > 0) {
                query.append(unionOperator);
            }
            query.append(subQueries[i]);
        }
        appendClause(query, " ORDER BY ", sortOrder);
        appendClause(query, " LIMIT ", limit);
        return query.toString();
    }

    private static String maybeWithOperator(String operator, String column) {
        if (operator == null) {
            return column;
        }
        return operator + "(" + column + ")";
    }

    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
    public String[] computeProjection(String[] projectionIn) {
        int i = 0;
        if (projectionIn == null || projectionIn.length <= 0) {
            if (this.mProjectionMap == null) {
                return null;
            }
            Set<Map.Entry<String, String>> entrySet = this.mProjectionMap.entrySet();
            String[] projection = new String[entrySet.size()];
            for (Map.Entry<String, String> entry : entrySet) {
                if (!entry.getKey().equals(BaseColumns._COUNT)) {
                    projection[i] = entry.getValue();
                    i++;
                }
            }
            return projection;
        } else if (this.mProjectionMap == null) {
            return projectionIn;
        } else {
            String[] projection2 = new String[projectionIn.length];
            int length = projectionIn.length;
            while (i < length) {
                String operator = null;
                String userColumn = projectionIn[i];
                String column = this.mProjectionMap.get(userColumn);
                if (this.mProjectionAggregationAllowed) {
                    Matcher matcher = sAggregationPattern.matcher(userColumn);
                    if (matcher.matches()) {
                        operator = matcher.group(1);
                        userColumn = matcher.group(2);
                        column = this.mProjectionMap.get(userColumn);
                    }
                }
                if (column != null) {
                    projection2[i] = maybeWithOperator(operator, column);
                } else if (this.mStrict || (!userColumn.contains(" AS ") && !userColumn.contains(" as "))) {
                    if (this.mProjectionGreylist != null) {
                        boolean match = false;
                        Iterator<Pattern> it = this.mProjectionGreylist.iterator();
                        while (true) {
                            if (it.hasNext()) {
                                if (it.next().matcher(userColumn).matches()) {
                                    match = true;
                                    break;
                                }
                            } else {
                                break;
                            }
                        }
                        if (match) {
                            Log.w(TAG, "Allowing abusive custom column: " + userColumn);
                            projection2[i] = maybeWithOperator(operator, userColumn);
                        }
                    }
                    throw new IllegalArgumentException("Invalid column " + projectionIn[i]);
                } else {
                    projection2[i] = maybeWithOperator(operator, userColumn);
                }
                i++;
            }
            return projection2;
        }
    }

    public String computeWhere(String selection) {
        boolean hasInternal = !TextUtils.isEmpty(this.mWhereClause);
        boolean hasExternal = !TextUtils.isEmpty(selection);
        if (!hasInternal && !hasExternal) {
            return null;
        }
        StringBuilder where = new StringBuilder();
        if (hasInternal) {
            where.append('(');
            where.append(this.mWhereClause);
            where.append(')');
        }
        if (hasInternal && hasExternal) {
            where.append(" AND ");
        }
        if (hasExternal) {
            where.append('(');
            where.append(selection);
            where.append(')');
        }
        return where.toString();
    }

    private String wrap(String arg) {
        if (TextUtils.isEmpty(arg)) {
            return arg;
        }
        return "(" + arg + ")";
    }
}
