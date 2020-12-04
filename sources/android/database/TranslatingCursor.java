package android.database;

import android.net.Uri;
import android.util.ArraySet;
import com.android.internal.util.ArrayUtils;
import java.util.Arrays;
import java.util.Objects;

public class TranslatingCursor extends CrossProcessCursorWrapper {
    private final int mAuxiliaryColumnIndex;
    private final Config mConfig;
    private final boolean mDropLast;
    private final ArraySet<Integer> mTranslateColumnIndices = new ArraySet<>();
    private final Translator mTranslator;

    public interface Translator {
        String translate(String str, int i, String str2, Cursor cursor);
    }

    public static class Config {
        public final String auxiliaryColumn;
        public final Uri baseUri;
        public final String[] translateColumns;

        public Config(Uri baseUri2, String auxiliaryColumn2, String... translateColumns2) {
            this.baseUri = baseUri2;
            this.auxiliaryColumn = auxiliaryColumn2;
            this.translateColumns = translateColumns2;
        }
    }

    public TranslatingCursor(Cursor cursor, Config config, Translator translator, boolean dropLast) {
        super(cursor);
        this.mConfig = (Config) Objects.requireNonNull(config);
        this.mTranslator = (Translator) Objects.requireNonNull(translator);
        this.mDropLast = dropLast;
        this.mAuxiliaryColumnIndex = cursor.getColumnIndexOrThrow(config.auxiliaryColumn);
        for (int i = 0; i < cursor.getColumnCount(); i++) {
            if (ArrayUtils.contains((T[]) config.translateColumns, cursor.getColumnName(i))) {
                this.mTranslateColumnIndices.add(Integer.valueOf(i));
            }
        }
    }

    public int getColumnCount() {
        if (this.mDropLast) {
            return super.getColumnCount() - 1;
        }
        return super.getColumnCount();
    }

    public String[] getColumnNames() {
        if (this.mDropLast) {
            return (String[]) Arrays.copyOfRange(super.getColumnNames(), 0, super.getColumnCount() - 1);
        }
        return super.getColumnNames();
    }

    /* JADX WARNING: type inference failed for: r6v3, types: [java.lang.Object[]] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static android.database.Cursor query(android.database.TranslatingCursor.Config r14, android.database.TranslatingCursor.Translator r15, android.database.sqlite.SQLiteQueryBuilder r16, android.database.sqlite.SQLiteDatabase r17, java.lang.String[] r18, java.lang.String r19, java.lang.String[] r20, java.lang.String r21, java.lang.String r22, java.lang.String r23, java.lang.String r24, android.os.CancellationSignal r25) {
        /*
            r0 = r14
            r1 = r18
            boolean r2 = com.android.internal.util.ArrayUtils.isEmpty((T[]) r18)
            r3 = 0
            r4 = 1
            if (r2 != 0) goto L_0x0016
            java.lang.String r2 = r0.auxiliaryColumn
            boolean r2 = com.android.internal.util.ArrayUtils.contains((T[]) r1, r2)
            if (r2 == 0) goto L_0x0014
            goto L_0x0016
        L_0x0014:
            r2 = r3
            goto L_0x0017
        L_0x0016:
            r2 = r4
        L_0x0017:
            boolean r5 = com.android.internal.util.ArrayUtils.isEmpty((T[]) r18)
            if (r5 != 0) goto L_0x0028
            java.lang.String[] r5 = r0.translateColumns
            boolean r5 = com.android.internal.util.ArrayUtils.containsAny(r1, r5)
            if (r5 == 0) goto L_0x0026
            goto L_0x0028
        L_0x0026:
            r5 = r3
            goto L_0x0029
        L_0x0028:
            r5 = r4
        L_0x0029:
            if (r5 != 0) goto L_0x0030
            android.database.Cursor r3 = r16.query(r17, r18, r19, r20, r21, r22, r23, r24, r25)
            return r3
        L_0x0030:
            if (r2 != 0) goto L_0x003d
            java.lang.Class<java.lang.String> r6 = java.lang.String.class
            java.lang.String r7 = r0.auxiliaryColumn
            java.lang.Object[] r6 = com.android.internal.util.ArrayUtils.appendElement(r6, r1, r7)
            r1 = r6
            java.lang.String[] r1 = (java.lang.String[]) r1
        L_0x003d:
            r6 = r16
            r7 = r17
            r8 = r1
            r9 = r19
            r10 = r20
            r11 = r21
            r12 = r22
            r13 = r23
            android.database.Cursor r6 = r6.query(r7, r8, r9, r10, r11, r12, r13)
            android.database.TranslatingCursor r7 = new android.database.TranslatingCursor
            if (r2 != 0) goto L_0x0055
            goto L_0x0056
        L_0x0055:
            r4 = r3
        L_0x0056:
            r3 = r15
            r7.<init>(r6, r14, r15, r4)
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: android.database.TranslatingCursor.query(android.database.TranslatingCursor$Config, android.database.TranslatingCursor$Translator, android.database.sqlite.SQLiteQueryBuilder, android.database.sqlite.SQLiteDatabase, java.lang.String[], java.lang.String, java.lang.String[], java.lang.String, java.lang.String, java.lang.String, java.lang.String, android.os.CancellationSignal):android.database.Cursor");
    }

    public void fillWindow(int position, CursorWindow window) {
        DatabaseUtils.cursorFillWindow(this, position, window);
    }

    public CursorWindow getWindow() {
        return null;
    }

    public Cursor getWrappedCursor() {
        throw new UnsupportedOperationException("Returning underlying cursor risks leaking data");
    }

    public double getDouble(int columnIndex) {
        if (!ArrayUtils.contains(this.mTranslateColumnIndices, Integer.valueOf(columnIndex))) {
            return super.getDouble(columnIndex);
        }
        throw new IllegalArgumentException();
    }

    public float getFloat(int columnIndex) {
        if (!ArrayUtils.contains(this.mTranslateColumnIndices, Integer.valueOf(columnIndex))) {
            return super.getFloat(columnIndex);
        }
        throw new IllegalArgumentException();
    }

    public int getInt(int columnIndex) {
        if (!ArrayUtils.contains(this.mTranslateColumnIndices, Integer.valueOf(columnIndex))) {
            return super.getInt(columnIndex);
        }
        throw new IllegalArgumentException();
    }

    public long getLong(int columnIndex) {
        if (!ArrayUtils.contains(this.mTranslateColumnIndices, Integer.valueOf(columnIndex))) {
            return super.getLong(columnIndex);
        }
        throw new IllegalArgumentException();
    }

    public short getShort(int columnIndex) {
        if (!ArrayUtils.contains(this.mTranslateColumnIndices, Integer.valueOf(columnIndex))) {
            return super.getShort(columnIndex);
        }
        throw new IllegalArgumentException();
    }

    public String getString(int columnIndex) {
        if (ArrayUtils.contains(this.mTranslateColumnIndices, Integer.valueOf(columnIndex))) {
            return this.mTranslator.translate(super.getString(columnIndex), this.mAuxiliaryColumnIndex, getColumnName(columnIndex), this);
        }
        return super.getString(columnIndex);
    }

    public void copyStringToBuffer(int columnIndex, CharArrayBuffer buffer) {
        if (!ArrayUtils.contains(this.mTranslateColumnIndices, Integer.valueOf(columnIndex))) {
            super.copyStringToBuffer(columnIndex, buffer);
            return;
        }
        throw new IllegalArgumentException();
    }

    public byte[] getBlob(int columnIndex) {
        if (!ArrayUtils.contains(this.mTranslateColumnIndices, Integer.valueOf(columnIndex))) {
            return super.getBlob(columnIndex);
        }
        throw new IllegalArgumentException();
    }

    public int getType(int columnIndex) {
        if (ArrayUtils.contains(this.mTranslateColumnIndices, Integer.valueOf(columnIndex))) {
            return 3;
        }
        return super.getType(columnIndex);
    }

    public boolean isNull(int columnIndex) {
        if (ArrayUtils.contains(this.mTranslateColumnIndices, Integer.valueOf(columnIndex))) {
            return getString(columnIndex) == null;
        }
        return super.isNull(columnIndex);
    }
}
