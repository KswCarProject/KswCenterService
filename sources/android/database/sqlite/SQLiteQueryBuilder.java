package android.database.sqlite;

import android.annotation.UnsupportedAppUsage;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.p007os.Build;
import android.p007os.CancellationSignal;
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

/* loaded from: classes.dex */
public class SQLiteQueryBuilder {
    private static final String TAG = "SQLiteQueryBuilder";
    private boolean mStrict;
    private static final Pattern sLimitPattern = Pattern.compile("\\s*\\d+\\s*(,\\s*\\d+\\s*)?");
    private static final Pattern sAggregationPattern = Pattern.compile("(?i)(AVG|COUNT|MAX|MIN|SUM|TOTAL|GROUP_CONCAT)\\((.+)\\)");
    private Map<String, String> mProjectionMap = null;
    private List<Pattern> mProjectionGreylist = null;
    private boolean mProjectionAggregationAllowed = false;
    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
    private String mTables = "";
    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
    private StringBuilder mWhereClause = null;
    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
    private boolean mDistinct = false;
    private SQLiteDatabase.CursorFactory mFactory = null;

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
        }
        if (!TextUtils.isEmpty(limit) && !sLimitPattern.matcher(limit).matches()) {
            throw new IllegalArgumentException("invalid LIMIT clauses:" + limit);
        }
        StringBuilder query = new StringBuilder(120);
        query.append("SELECT ");
        if (distinct) {
            query.append("DISTINCT ");
        }
        if (columns != null && columns.length != 0) {
            appendColumns(query, columns);
        } else {
            query.append("* ");
        }
        query.append("FROM ");
        query.append(tables);
        appendClause(query, " WHERE ", where);
        appendClause(query, " GROUP BY ", groupBy);
        appendClause(query, " HAVING ", having);
        appendClause(query, " ORDER BY ", orderBy);
        appendClause(query, " LIMIT ", limit);
        return query.toString();
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
        return query(db, projectionIn, selection, selectionArgs, groupBy, having, sortOrder, null, null);
    }

    public Cursor query(SQLiteDatabase db, String[] projectionIn, String selection, String[] selectionArgs, String groupBy, String having, String sortOrder, String limit) {
        return query(db, projectionIn, selection, selectionArgs, groupBy, having, sortOrder, limit, null);
    }

    public Cursor query(SQLiteDatabase db, String[] projectionIn, String selection, String[] selectionArgs, String groupBy, String having, String sortOrder, String limit, CancellationSignal cancellationSignal) {
        String sql;
        if (this.mTables == null) {
            return null;
        }
        String unwrappedSql = buildQuery(projectionIn, selection, groupBy, having, sortOrder, limit);
        if (!this.mStrict || selection == null || selection.length() <= 0) {
            sql = unwrappedSql;
        } else {
            db.validateSql(unwrappedSql, cancellationSignal);
            sql = buildQuery(projectionIn, wrap(selection), groupBy, having, sortOrder, limit);
        }
        if (Log.isLoggable(TAG, 3)) {
            if (Build.IS_DEBUGGABLE) {
                Log.m72d(TAG, sql + " with args " + Arrays.toString(selectionArgs));
            } else {
                Log.m72d(TAG, sql);
            }
        }
        return db.rawQueryWithFactory(this.mFactory, sql, selectionArgs, SQLiteDatabase.findEditTable(this.mTables), cancellationSignal);
    }

    public int update(SQLiteDatabase db, ContentValues values, String selection, String[] selectionArgs) {
        String sql;
        Objects.requireNonNull(this.mTables, "No tables defined");
        Objects.requireNonNull(db, "No database defined");
        Objects.requireNonNull(values, "No values defined");
        String unwrappedSql = buildUpdate(values, selection);
        if (this.mStrict) {
            db.validateSql(unwrappedSql, null);
            sql = buildUpdate(values, wrap(selection));
        } else {
            sql = unwrappedSql;
        }
        if (selectionArgs == null) {
            selectionArgs = EmptyArray.STRING;
        }
        ArrayMap<String, Object> rawValues = values.getValues();
        int valuesLength = rawValues.size();
        Object[] sqlArgs = new Object[selectionArgs.length + valuesLength];
        for (int i = 0; i < sqlArgs.length; i++) {
            if (i < valuesLength) {
                sqlArgs[i] = rawValues.valueAt(i);
            } else {
                sqlArgs[i] = selectionArgs[i - valuesLength];
            }
        }
        if (Log.isLoggable(TAG, 3)) {
            if (Build.IS_DEBUGGABLE) {
                Log.m72d(TAG, sql + " with args " + Arrays.toString(sqlArgs));
            } else {
                Log.m72d(TAG, sql);
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
            db.validateSql(unwrappedSql, null);
            sql = buildDelete(wrap(selection));
        } else {
            sql = unwrappedSql;
        }
        if (Log.isLoggable(TAG, 3)) {
            if (Build.IS_DEBUGGABLE) {
                Log.m72d(TAG, sql + " with args " + Arrays.toString(selectionArgs));
            } else {
                Log.m72d(TAG, sql);
            }
        }
        return db.executeSql(sql, selectionArgs);
    }

    public String buildQuery(String[] projectionIn, String selection, String groupBy, String having, String sortOrder, String limit) {
        String[] projection = computeProjection(projectionIn);
        String where = computeWhere(selection);
        return buildQueryString(this.mDistinct, this.mTables, projection, where, groupBy, having, sortOrder, limit);
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
        String where = computeWhere(selection);
        appendClause(sql, " WHERE ", where);
        return sql.toString();
    }

    public String buildDelete(String selection) {
        StringBuilder sql = new StringBuilder(120);
        sql.append("DELETE FROM ");
        sql.append(this.mTables);
        String where = computeWhere(selection);
        appendClause(sql, " WHERE ", where);
        return sql.toString();
    }

    public String buildUnionSubQuery(String typeDiscriminatorColumn, String[] unionColumns, Set<String> columnsPresentInTable, int computedColumnsOffset, String typeDiscriminatorValue, String selection, String groupBy, String having) {
        int unionColumnsCount = unionColumns.length;
        String[] projectionIn = new String[unionColumnsCount];
        for (int i = 0; i < unionColumnsCount; i++) {
            String unionColumn = unionColumns[i];
            if (unionColumn.equals(typeDiscriminatorColumn)) {
                projectionIn[i] = "'" + typeDiscriminatorValue + "' AS " + typeDiscriminatorColumn;
            } else {
                if (i > computedColumnsOffset && !columnsPresentInTable.contains(unionColumn)) {
                    projectionIn[i] = "NULL AS " + unionColumn;
                }
                projectionIn[i] = unionColumn;
            }
        }
        return buildQuery(projectionIn, selection, groupBy, having, null, null);
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
        if (operator != null) {
            return operator + "(" + column + ")";
        }
        return column;
    }

    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
    public String[] computeProjection(String[] projectionIn) {
        int i = 0;
        if (projectionIn != null && projectionIn.length > 0) {
            if (this.mProjectionMap != null) {
                String[] projection = new String[projectionIn.length];
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
                        projection[i] = maybeWithOperator(operator, column);
                    } else if (!this.mStrict && (userColumn.contains(" AS ") || userColumn.contains(" as "))) {
                        projection[i] = maybeWithOperator(operator, userColumn);
                    } else {
                        if (this.mProjectionGreylist != null) {
                            boolean match = false;
                            Iterator<Pattern> it = this.mProjectionGreylist.iterator();
                            while (true) {
                                if (!it.hasNext()) {
                                    break;
                                }
                                Pattern p = it.next();
                                if (p.matcher(userColumn).matches()) {
                                    match = true;
                                    break;
                                }
                            }
                            if (match) {
                                Log.m64w(TAG, "Allowing abusive custom column: " + userColumn);
                                projection[i] = maybeWithOperator(operator, userColumn);
                            }
                        }
                        throw new IllegalArgumentException("Invalid column " + projectionIn[i]);
                    }
                    i++;
                }
                return projection;
            }
            return projectionIn;
        } else if (this.mProjectionMap != null) {
            Set<Map.Entry<String, String>> entrySet = this.mProjectionMap.entrySet();
            String[] projection2 = new String[entrySet.size()];
            for (Map.Entry<String, String> entry : entrySet) {
                if (!entry.getKey().equals(BaseColumns._COUNT)) {
                    projection2[i] = entry.getValue();
                    i++;
                }
            }
            return projection2;
        } else {
            return null;
        }
    }

    public String computeWhere(String selection) {
        boolean hasInternal = !TextUtils.isEmpty(this.mWhereClause);
        boolean hasExternal = !TextUtils.isEmpty(selection);
        if (hasInternal || hasExternal) {
            StringBuilder where = new StringBuilder();
            if (hasInternal) {
                where.append('(');
                where.append((CharSequence) this.mWhereClause);
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
        return null;
    }

    private String wrap(String arg) {
        if (TextUtils.isEmpty(arg)) {
            return arg;
        }
        return "(" + arg + ")";
    }
}
