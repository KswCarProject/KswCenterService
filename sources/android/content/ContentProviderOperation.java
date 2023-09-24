package android.content;

import android.annotation.UnsupportedAppUsage;
import android.database.Cursor;
import android.net.Uri;
import android.p007os.Parcel;
import android.p007os.Parcelable;
import android.util.Log;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public class ContentProviderOperation implements Parcelable {
    public static final Parcelable.Creator<ContentProviderOperation> CREATOR = new Parcelable.Creator<ContentProviderOperation>() { // from class: android.content.ContentProviderOperation.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public ContentProviderOperation createFromParcel(Parcel source) {
            return new ContentProviderOperation(source);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public ContentProviderOperation[] newArray(int size) {
            return new ContentProviderOperation[size];
        }
    };
    private static final String TAG = "ContentProviderOperation";
    public static final int TYPE_ASSERT = 4;
    @UnsupportedAppUsage
    public static final int TYPE_DELETE = 3;
    @UnsupportedAppUsage
    public static final int TYPE_INSERT = 1;
    @UnsupportedAppUsage
    public static final int TYPE_UPDATE = 2;
    private final Integer mExpectedCount;
    private final boolean mFailureAllowed;
    @UnsupportedAppUsage
    private final String mSelection;
    private final String[] mSelectionArgs;
    private final Map<Integer, Integer> mSelectionArgsBackReferences;
    @UnsupportedAppUsage
    private final int mType;
    @UnsupportedAppUsage
    private final Uri mUri;
    private final ContentValues mValues;
    private final ContentValues mValuesBackReferences;
    private final boolean mYieldAllowed;

    private ContentProviderOperation(Builder builder) {
        this.mType = builder.mType;
        this.mUri = builder.mUri;
        this.mValues = builder.mValues;
        this.mSelection = builder.mSelection;
        this.mSelectionArgs = builder.mSelectionArgs;
        this.mExpectedCount = builder.mExpectedCount;
        this.mSelectionArgsBackReferences = builder.mSelectionArgsBackReferences;
        this.mValuesBackReferences = builder.mValuesBackReferences;
        this.mYieldAllowed = builder.mYieldAllowed;
        this.mFailureAllowed = builder.mFailureAllowed;
    }

    private ContentProviderOperation(Parcel source) {
        ContentValues contentValues;
        this.mType = source.readInt();
        this.mUri = Uri.CREATOR.createFromParcel(source);
        this.mValues = source.readInt() != 0 ? ContentValues.CREATOR.createFromParcel(source) : null;
        this.mSelection = source.readInt() != 0 ? source.readString() : null;
        this.mSelectionArgs = source.readInt() != 0 ? source.readStringArray() : null;
        this.mExpectedCount = source.readInt() != 0 ? Integer.valueOf(source.readInt()) : null;
        if (source.readInt() != 0) {
            contentValues = ContentValues.CREATOR.createFromParcel(source);
        } else {
            contentValues = null;
        }
        this.mValuesBackReferences = contentValues;
        this.mSelectionArgsBackReferences = source.readInt() != 0 ? new HashMap() : null;
        if (this.mSelectionArgsBackReferences != null) {
            int count = source.readInt();
            for (int i = 0; i < count; i++) {
                this.mSelectionArgsBackReferences.put(Integer.valueOf(source.readInt()), Integer.valueOf(source.readInt()));
            }
        }
        int count2 = source.readInt();
        this.mYieldAllowed = count2 != 0;
        this.mFailureAllowed = source.readInt() != 0;
    }

    public ContentProviderOperation(ContentProviderOperation cpo, Uri withUri) {
        this.mType = cpo.mType;
        this.mUri = withUri;
        this.mValues = cpo.mValues;
        this.mSelection = cpo.mSelection;
        this.mSelectionArgs = cpo.mSelectionArgs;
        this.mExpectedCount = cpo.mExpectedCount;
        this.mSelectionArgsBackReferences = cpo.mSelectionArgsBackReferences;
        this.mValuesBackReferences = cpo.mValuesBackReferences;
        this.mYieldAllowed = cpo.mYieldAllowed;
        this.mFailureAllowed = cpo.mFailureAllowed;
    }

    @Override // android.p007os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mType);
        Uri.writeToParcel(dest, this.mUri);
        if (this.mValues != null) {
            dest.writeInt(1);
            this.mValues.writeToParcel(dest, 0);
        } else {
            dest.writeInt(0);
        }
        if (this.mSelection != null) {
            dest.writeInt(1);
            dest.writeString(this.mSelection);
        } else {
            dest.writeInt(0);
        }
        if (this.mSelectionArgs != null) {
            dest.writeInt(1);
            dest.writeStringArray(this.mSelectionArgs);
        } else {
            dest.writeInt(0);
        }
        if (this.mExpectedCount != null) {
            dest.writeInt(1);
            dest.writeInt(this.mExpectedCount.intValue());
        } else {
            dest.writeInt(0);
        }
        if (this.mValuesBackReferences != null) {
            dest.writeInt(1);
            this.mValuesBackReferences.writeToParcel(dest, 0);
        } else {
            dest.writeInt(0);
        }
        if (this.mSelectionArgsBackReferences != null) {
            dest.writeInt(1);
            dest.writeInt(this.mSelectionArgsBackReferences.size());
            for (Map.Entry<Integer, Integer> entry : this.mSelectionArgsBackReferences.entrySet()) {
                dest.writeInt(entry.getKey().intValue());
                dest.writeInt(entry.getValue().intValue());
            }
        } else {
            dest.writeInt(0);
        }
        dest.writeInt(this.mYieldAllowed ? 1 : 0);
        dest.writeInt(this.mFailureAllowed ? 1 : 0);
    }

    public static Builder newInsert(Uri uri) {
        return new Builder(1, uri);
    }

    public static Builder newUpdate(Uri uri) {
        return new Builder(2, uri);
    }

    public static Builder newDelete(Uri uri) {
        return new Builder(3, uri);
    }

    public static Builder newAssertQuery(Uri uri) {
        return new Builder(4, uri);
    }

    public Uri getUri() {
        return this.mUri;
    }

    public boolean isYieldAllowed() {
        return this.mYieldAllowed;
    }

    public boolean isFailureAllowed() {
        return this.mFailureAllowed;
    }

    @UnsupportedAppUsage
    public int getType() {
        return this.mType;
    }

    public boolean isInsert() {
        return this.mType == 1;
    }

    public boolean isDelete() {
        return this.mType == 3;
    }

    public boolean isUpdate() {
        return this.mType == 2;
    }

    public boolean isAssertQuery() {
        return this.mType == 4;
    }

    public boolean isWriteOperation() {
        return this.mType == 3 || this.mType == 1 || this.mType == 2;
    }

    public boolean isReadOperation() {
        return this.mType == 4;
    }

    public ContentProviderResult apply(ContentProvider provider, ContentProviderResult[] backRefs, int numBackRefs) throws OperationApplicationException {
        if (this.mFailureAllowed) {
            try {
                return applyInternal(provider, backRefs, numBackRefs);
            } catch (Exception e) {
                return new ContentProviderResult(e.getMessage());
            }
        }
        return applyInternal(provider, backRefs, numBackRefs);
    }

    /* JADX WARN: Code restructure failed: missing block: B:27:0x00a1, code lost:
        if (r8 != null) goto L38;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x00a7, code lost:
        if (r1.moveToNext() == false) goto L51;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x00a9, code lost:
        r3 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x00ab, code lost:
        if (r3 >= r8.length) goto L50;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x00ad, code lost:
        r4 = r1.getString(r3);
        r5 = r0.getAsString(r8[r3]);
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x00bb, code lost:
        if (android.text.TextUtils.equals(r4, r5) == false) goto L46;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x00bd, code lost:
        r3 = r3 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x00e8, code lost:
        throw new android.content.OperationApplicationException("Found value " + r4 + " when expected " + r5 + " for column " + r8[r3]);
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x00e9, code lost:
        r1.close();
        r1 = r2;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private ContentProviderResult applyInternal(ContentProvider provider, ContentProviderResult[] backRefs, int numBackRefs) throws OperationApplicationException {
        int numRows;
        ContentValues values = resolveValueBackReferences(backRefs, numBackRefs);
        String[] selectionArgs = resolveSelectionArgsBackReferences(backRefs, numBackRefs);
        if (this.mType == 1) {
            Uri newUri = provider.insert(this.mUri, values);
            if (newUri != null) {
                return new ContentProviderResult(newUri);
            }
            throw new OperationApplicationException("Insert into " + this.mUri + " returned no result");
        }
        if (this.mType == 3) {
            numRows = provider.delete(this.mUri, this.mSelection, selectionArgs);
        } else if (this.mType == 2) {
            numRows = provider.update(this.mUri, values, this.mSelection, selectionArgs);
        } else if (this.mType == 4) {
            String[] projection = null;
            if (values != null) {
                ArrayList<String> projectionList = new ArrayList<>();
                for (Map.Entry<String, Object> entry : values.valueSet()) {
                    projectionList.add(entry.getKey());
                }
                projection = (String[]) projectionList.toArray(new String[projectionList.size()]);
            }
            String[] projection2 = projection;
            Cursor cursor = provider.query(this.mUri, projection2, this.mSelection, selectionArgs, null);
            try {
                int numRows2 = cursor.getCount();
            } catch (Throwable th) {
                cursor.close();
                throw th;
            }
        } else {
            throw new IllegalStateException("bad type, " + this.mType);
        }
        if (this.mExpectedCount != null && this.mExpectedCount.intValue() != numRows) {
            throw new OperationApplicationException("Expected " + this.mExpectedCount + " rows but actual " + numRows);
        }
        return new ContentProviderResult(numRows);
    }

    public ContentValues resolveValueBackReferences(ContentProviderResult[] backRefs, int numBackRefs) {
        ContentValues values;
        if (this.mValuesBackReferences == null) {
            return this.mValues;
        }
        if (this.mValues == null) {
            values = new ContentValues();
        } else {
            values = new ContentValues(this.mValues);
        }
        for (Map.Entry<String, Object> entry : this.mValuesBackReferences.valueSet()) {
            String key = entry.getKey();
            Integer backRefIndex = this.mValuesBackReferences.getAsInteger(key);
            if (backRefIndex == null) {
                Log.m70e(TAG, toString());
                throw new IllegalArgumentException("values backref " + key + " is not an integer");
            }
            values.put(key, Long.valueOf(backRefToValue(backRefs, numBackRefs, backRefIndex)));
        }
        return values;
    }

    public String[] resolveSelectionArgsBackReferences(ContentProviderResult[] backRefs, int numBackRefs) {
        if (this.mSelectionArgsBackReferences == null) {
            return this.mSelectionArgs;
        }
        String[] newArgs = new String[this.mSelectionArgs.length];
        System.arraycopy(this.mSelectionArgs, 0, newArgs, 0, this.mSelectionArgs.length);
        for (Map.Entry<Integer, Integer> selectionArgBackRef : this.mSelectionArgsBackReferences.entrySet()) {
            Integer selectionArgIndex = selectionArgBackRef.getKey();
            int backRefIndex = selectionArgBackRef.getValue().intValue();
            newArgs[selectionArgIndex.intValue()] = String.valueOf(backRefToValue(backRefs, numBackRefs, Integer.valueOf(backRefIndex)));
        }
        return newArgs;
    }

    public String toString() {
        return "mType: " + this.mType + ", mUri: " + this.mUri + ", mSelection: " + this.mSelection + ", mExpectedCount: " + this.mExpectedCount + ", mYieldAllowed: " + this.mYieldAllowed + ", mValues: " + this.mValues + ", mValuesBackReferences: " + this.mValuesBackReferences + ", mSelectionArgsBackReferences: " + this.mSelectionArgsBackReferences;
    }

    private long backRefToValue(ContentProviderResult[] backRefs, int numBackRefs, Integer backRefIndex) {
        if (backRefIndex.intValue() >= numBackRefs) {
            Log.m70e(TAG, toString());
            throw new ArrayIndexOutOfBoundsException("asked for back ref " + backRefIndex + " but there are only " + numBackRefs + " back refs");
        }
        ContentProviderResult backRef = backRefs[backRefIndex.intValue()];
        if (backRef.uri != null) {
            long backRefValue = ContentUris.parseId(backRef.uri);
            return backRefValue;
        }
        long backRefValue2 = backRef.count.intValue();
        return backRefValue2;
    }

    @Override // android.p007os.Parcelable
    public int describeContents() {
        return 0;
    }

    /* loaded from: classes.dex */
    public static class Builder {
        private Integer mExpectedCount;
        private boolean mFailureAllowed;
        private String mSelection;
        private String[] mSelectionArgs;
        private Map<Integer, Integer> mSelectionArgsBackReferences;
        private final int mType;
        private final Uri mUri;
        private ContentValues mValues;
        private ContentValues mValuesBackReferences;
        private boolean mYieldAllowed;

        private Builder(int type, Uri uri) {
            if (uri == null) {
                throw new IllegalArgumentException("uri must not be null");
            }
            this.mType = type;
            this.mUri = uri;
        }

        public ContentProviderOperation build() {
            if (this.mType == 2 && ((this.mValues == null || this.mValues.isEmpty()) && (this.mValuesBackReferences == null || this.mValuesBackReferences.isEmpty()))) {
                throw new IllegalArgumentException("Empty values");
            }
            if (this.mType == 4 && ((this.mValues == null || this.mValues.isEmpty()) && ((this.mValuesBackReferences == null || this.mValuesBackReferences.isEmpty()) && this.mExpectedCount == null))) {
                throw new IllegalArgumentException("Empty values");
            }
            return new ContentProviderOperation(this);
        }

        public Builder withValueBackReferences(ContentValues backReferences) {
            if (this.mType != 1 && this.mType != 2 && this.mType != 4) {
                throw new IllegalArgumentException("only inserts, updates, and asserts can have value back-references");
            }
            this.mValuesBackReferences = backReferences;
            return this;
        }

        public Builder withValueBackReference(String key, int previousResult) {
            if (this.mType != 1 && this.mType != 2 && this.mType != 4) {
                throw new IllegalArgumentException("only inserts, updates, and asserts can have value back-references");
            }
            if (this.mValuesBackReferences == null) {
                this.mValuesBackReferences = new ContentValues();
            }
            this.mValuesBackReferences.put(key, Integer.valueOf(previousResult));
            return this;
        }

        public Builder withSelectionBackReference(int selectionArgIndex, int previousResult) {
            if (this.mType != 2 && this.mType != 3 && this.mType != 4) {
                throw new IllegalArgumentException("only updates, deletes, and asserts can have selection back-references");
            }
            if (this.mSelectionArgsBackReferences == null) {
                this.mSelectionArgsBackReferences = new HashMap();
            }
            this.mSelectionArgsBackReferences.put(Integer.valueOf(selectionArgIndex), Integer.valueOf(previousResult));
            return this;
        }

        public Builder withValues(ContentValues values) {
            if (this.mType != 1 && this.mType != 2 && this.mType != 4) {
                throw new IllegalArgumentException("only inserts, updates, and asserts can have values");
            }
            if (this.mValues == null) {
                this.mValues = new ContentValues();
            }
            this.mValues.putAll(values);
            return this;
        }

        public Builder withValue(String key, Object value) {
            if (this.mType != 1 && this.mType != 2 && this.mType != 4) {
                throw new IllegalArgumentException("only inserts and updates can have values");
            }
            if (this.mValues == null) {
                this.mValues = new ContentValues();
            }
            if (value == null) {
                this.mValues.putNull(key);
            } else if (value instanceof String) {
                this.mValues.put(key, (String) value);
            } else if (value instanceof Byte) {
                this.mValues.put(key, (Byte) value);
            } else if (value instanceof Short) {
                this.mValues.put(key, (Short) value);
            } else if (value instanceof Integer) {
                this.mValues.put(key, (Integer) value);
            } else if (value instanceof Long) {
                this.mValues.put(key, (Long) value);
            } else if (value instanceof Float) {
                this.mValues.put(key, (Float) value);
            } else if (value instanceof Double) {
                this.mValues.put(key, (Double) value);
            } else if (value instanceof Boolean) {
                this.mValues.put(key, (Boolean) value);
            } else if (value instanceof byte[]) {
                this.mValues.put(key, (byte[]) value);
            } else {
                throw new IllegalArgumentException("bad value type: " + value.getClass().getName());
            }
            return this;
        }

        public Builder withSelection(String selection, String[] selectionArgs) {
            if (this.mType != 2 && this.mType != 3 && this.mType != 4) {
                throw new IllegalArgumentException("only updates, deletes, and asserts can have selections");
            }
            this.mSelection = selection;
            if (selectionArgs == null) {
                this.mSelectionArgs = null;
            } else {
                this.mSelectionArgs = new String[selectionArgs.length];
                System.arraycopy(selectionArgs, 0, this.mSelectionArgs, 0, selectionArgs.length);
            }
            return this;
        }

        public Builder withExpectedCount(int count) {
            if (this.mType != 2 && this.mType != 3 && this.mType != 4) {
                throw new IllegalArgumentException("only updates, deletes, and asserts can have expected counts");
            }
            this.mExpectedCount = Integer.valueOf(count);
            return this;
        }

        public Builder withYieldAllowed(boolean yieldAllowed) {
            this.mYieldAllowed = yieldAllowed;
            return this;
        }

        public Builder withFailureAllowed(boolean failureAllowed) {
            this.mFailureAllowed = failureAllowed;
            return this;
        }
    }
}
