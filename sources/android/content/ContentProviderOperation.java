package android.content;

import android.annotation.UnsupportedAppUsage;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import java.util.HashMap;
import java.util.Map;

public class ContentProviderOperation implements Parcelable {
    public static final Parcelable.Creator<ContentProviderOperation> CREATOR = new Parcelable.Creator<ContentProviderOperation>() {
        public ContentProviderOperation createFromParcel(Parcel source) {
            return new ContentProviderOperation(source);
        }

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
        HashMap hashMap = null;
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
        this.mSelectionArgsBackReferences = source.readInt() != 0 ? new HashMap() : hashMap;
        boolean z = false;
        if (this.mSelectionArgsBackReferences != null) {
            int count = source.readInt();
            for (int i = 0; i < count; i++) {
                this.mSelectionArgsBackReferences.put(Integer.valueOf(source.readInt()), Integer.valueOf(source.readInt()));
            }
        }
        this.mYieldAllowed = source.readInt() != 0;
        this.mFailureAllowed = source.readInt() != 0 ? true : z;
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
        if (!this.mFailureAllowed) {
            return applyInternal(provider, backRefs, numBackRefs);
        }
        try {
            return applyInternal(provider, backRefs, numBackRefs);
        } catch (Exception e) {
            return new ContentProviderResult(e.getMessage());
        }
    }

    /* JADX INFO: finally extract failed */
    /* JADX WARNING: type inference failed for: r3v13, types: [java.lang.Object[]] */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x00a1, code lost:
        if (r8 != null) goto L_0x00a3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x00a7, code lost:
        if (r1.moveToNext() == false) goto L_0x00e9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x00a9, code lost:
        r3 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x00ab, code lost:
        if (r3 >= r8.length) goto L_0x00a3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x00ad, code lost:
        r4 = r1.getString(r3);
        r5 = r0.getAsString(r8[r3]);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00bb, code lost:
        if (android.text.TextUtils.equals(r4, r5) == false) goto L_0x00c0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00bd, code lost:
        r3 = r3 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x00e8, code lost:
        throw new android.content.OperationApplicationException("Found value " + r4 + " when expected " + r5 + " for column " + r8[r3]);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x00e9, code lost:
        r1.close();
        r1 = r2;
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private android.content.ContentProviderResult applyInternal(android.content.ContentProvider r12, android.content.ContentProviderResult[] r13, int r14) throws android.content.OperationApplicationException {
        /*
            r11 = this;
            android.content.ContentValues r0 = r11.resolveValueBackReferences(r13, r14)
            java.lang.String[] r7 = r11.resolveSelectionArgsBackReferences(r13, r14)
            int r1 = r11.mType
            r2 = 1
            if (r1 != r2) goto L_0x003a
            android.net.Uri r1 = r11.mUri
            android.net.Uri r1 = r12.insert(r1, r0)
            if (r1 == 0) goto L_0x001c
            android.content.ContentProviderResult r2 = new android.content.ContentProviderResult
            r2.<init>((android.net.Uri) r1)
            return r2
        L_0x001c:
            android.content.OperationApplicationException r2 = new android.content.OperationApplicationException
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "Insert into "
            r3.append(r4)
            android.net.Uri r4 = r11.mUri
            r3.append(r4)
            java.lang.String r4 = " returned no result"
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            r2.<init>((java.lang.String) r3)
            throw r2
        L_0x003a:
            int r1 = r11.mType
            r2 = 3
            if (r1 != r2) goto L_0x0049
            android.net.Uri r1 = r11.mUri
            java.lang.String r2 = r11.mSelection
            int r1 = r12.delete(r1, r2, r7)
            goto L_0x00ef
        L_0x0049:
            int r1 = r11.mType
            r2 = 2
            if (r1 != r2) goto L_0x0058
            android.net.Uri r1 = r11.mUri
            java.lang.String r2 = r11.mSelection
            int r1 = r12.update(r1, r0, r2, r7)
            goto L_0x00ef
        L_0x0058:
            int r1 = r11.mType
            r2 = 4
            if (r1 != r2) goto L_0x0129
            r1 = 0
            if (r0 == 0) goto L_0x0090
            java.util.ArrayList r2 = new java.util.ArrayList
            r2.<init>()
            java.util.Set r3 = r0.valueSet()
            java.util.Iterator r3 = r3.iterator()
        L_0x006d:
            boolean r4 = r3.hasNext()
            if (r4 == 0) goto L_0x0083
            java.lang.Object r4 = r3.next()
            java.util.Map$Entry r4 = (java.util.Map.Entry) r4
            java.lang.Object r5 = r4.getKey()
            java.lang.String r5 = (java.lang.String) r5
            r2.add(r5)
            goto L_0x006d
        L_0x0083:
            int r3 = r2.size()
            java.lang.String[] r3 = new java.lang.String[r3]
            java.lang.Object[] r3 = r2.toArray(r3)
            r1 = r3
            java.lang.String[] r1 = (java.lang.String[]) r1
        L_0x0090:
            r8 = r1
            android.net.Uri r2 = r11.mUri
            java.lang.String r4 = r11.mSelection
            r6 = 0
            r1 = r12
            r3 = r8
            r5 = r7
            android.database.Cursor r1 = r1.query(r2, r3, r4, r5, r6)
            int r2 = r1.getCount()     // Catch:{ all -> 0x0124 }
            if (r8 == 0) goto L_0x00e9
        L_0x00a3:
            boolean r3 = r1.moveToNext()     // Catch:{ all -> 0x0124 }
            if (r3 == 0) goto L_0x00e9
            r3 = 0
        L_0x00aa:
            int r4 = r8.length     // Catch:{ all -> 0x0124 }
            if (r3 >= r4) goto L_0x00a3
            java.lang.String r4 = r1.getString(r3)     // Catch:{ all -> 0x0124 }
            r5 = r8[r3]     // Catch:{ all -> 0x0124 }
            java.lang.String r5 = r0.getAsString(r5)     // Catch:{ all -> 0x0124 }
            boolean r6 = android.text.TextUtils.equals(r4, r5)     // Catch:{ all -> 0x0124 }
            if (r6 == 0) goto L_0x00c0
            int r3 = r3 + 1
            goto L_0x00aa
        L_0x00c0:
            android.content.OperationApplicationException r6 = new android.content.OperationApplicationException     // Catch:{ all -> 0x0124 }
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ all -> 0x0124 }
            r9.<init>()     // Catch:{ all -> 0x0124 }
            java.lang.String r10 = "Found value "
            r9.append(r10)     // Catch:{ all -> 0x0124 }
            r9.append(r4)     // Catch:{ all -> 0x0124 }
            java.lang.String r10 = " when expected "
            r9.append(r10)     // Catch:{ all -> 0x0124 }
            r9.append(r5)     // Catch:{ all -> 0x0124 }
            java.lang.String r10 = " for column "
            r9.append(r10)     // Catch:{ all -> 0x0124 }
            r10 = r8[r3]     // Catch:{ all -> 0x0124 }
            r9.append(r10)     // Catch:{ all -> 0x0124 }
            java.lang.String r9 = r9.toString()     // Catch:{ all -> 0x0124 }
            r6.<init>((java.lang.String) r9)     // Catch:{ all -> 0x0124 }
            throw r6     // Catch:{ all -> 0x0124 }
        L_0x00e9:
            r1.close()
            r1 = r2
        L_0x00ef:
            java.lang.Integer r2 = r11.mExpectedCount
            if (r2 == 0) goto L_0x011e
            java.lang.Integer r2 = r11.mExpectedCount
            int r2 = r2.intValue()
            if (r2 != r1) goto L_0x00fd
            goto L_0x011e
        L_0x00fd:
            android.content.OperationApplicationException r2 = new android.content.OperationApplicationException
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "Expected "
            r3.append(r4)
            java.lang.Integer r4 = r11.mExpectedCount
            r3.append(r4)
            java.lang.String r4 = " rows but actual "
            r3.append(r4)
            r3.append(r1)
            java.lang.String r3 = r3.toString()
            r2.<init>((java.lang.String) r3)
            throw r2
        L_0x011e:
            android.content.ContentProviderResult r2 = new android.content.ContentProviderResult
            r2.<init>((int) r1)
            return r2
        L_0x0124:
            r2 = move-exception
            r1.close()
            throw r2
        L_0x0129:
            java.lang.IllegalStateException r1 = new java.lang.IllegalStateException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "bad type, "
            r2.append(r3)
            int r3 = r11.mType
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            r1.<init>(r2)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.content.ContentProviderOperation.applyInternal(android.content.ContentProvider, android.content.ContentProviderResult[], int):android.content.ContentProviderResult");
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
            if (backRefIndex != null) {
                values.put(key, Long.valueOf(backRefToValue(backRefs, numBackRefs, backRefIndex)));
            } else {
                Log.e(TAG, toString());
                throw new IllegalArgumentException("values backref " + key + " is not an integer");
            }
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
            newArgs[selectionArgBackRef.getKey().intValue()] = String.valueOf(backRefToValue(backRefs, numBackRefs, Integer.valueOf(selectionArgBackRef.getValue().intValue())));
        }
        return newArgs;
    }

    public String toString() {
        return "mType: " + this.mType + ", mUri: " + this.mUri + ", mSelection: " + this.mSelection + ", mExpectedCount: " + this.mExpectedCount + ", mYieldAllowed: " + this.mYieldAllowed + ", mValues: " + this.mValues + ", mValuesBackReferences: " + this.mValuesBackReferences + ", mSelectionArgsBackReferences: " + this.mSelectionArgsBackReferences;
    }

    private long backRefToValue(ContentProviderResult[] backRefs, int numBackRefs, Integer backRefIndex) {
        if (backRefIndex.intValue() < numBackRefs) {
            ContentProviderResult backRef = backRefs[backRefIndex.intValue()];
            if (backRef.uri != null) {
                return ContentUris.parseId(backRef.uri);
            }
            return (long) backRef.count.intValue();
        }
        Log.e(TAG, toString());
        throw new ArrayIndexOutOfBoundsException("asked for back ref " + backRefIndex + " but there are only " + numBackRefs + " back refs");
    }

    public int describeContents() {
        return 0;
    }

    public static class Builder {
        /* access modifiers changed from: private */
        public Integer mExpectedCount;
        /* access modifiers changed from: private */
        public boolean mFailureAllowed;
        /* access modifiers changed from: private */
        public String mSelection;
        /* access modifiers changed from: private */
        public String[] mSelectionArgs;
        /* access modifiers changed from: private */
        public Map<Integer, Integer> mSelectionArgsBackReferences;
        /* access modifiers changed from: private */
        public final int mType;
        /* access modifiers changed from: private */
        public final Uri mUri;
        /* access modifiers changed from: private */
        public ContentValues mValues;
        /* access modifiers changed from: private */
        public ContentValues mValuesBackReferences;
        /* access modifiers changed from: private */
        public boolean mYieldAllowed;

        private Builder(int type, Uri uri) {
            if (uri != null) {
                this.mType = type;
                this.mUri = uri;
                return;
            }
            throw new IllegalArgumentException("uri must not be null");
        }

        public ContentProviderOperation build() {
            if (this.mType == 2 && ((this.mValues == null || this.mValues.isEmpty()) && (this.mValuesBackReferences == null || this.mValuesBackReferences.isEmpty()))) {
                throw new IllegalArgumentException("Empty values");
            } else if (this.mType != 4 || ((this.mValues != null && !this.mValues.isEmpty()) || ((this.mValuesBackReferences != null && !this.mValuesBackReferences.isEmpty()) || this.mExpectedCount != null))) {
                return new ContentProviderOperation(this);
            } else {
                throw new IllegalArgumentException("Empty values");
            }
        }

        public Builder withValueBackReferences(ContentValues backReferences) {
            if (this.mType == 1 || this.mType == 2 || this.mType == 4) {
                this.mValuesBackReferences = backReferences;
                return this;
            }
            throw new IllegalArgumentException("only inserts, updates, and asserts can have value back-references");
        }

        public Builder withValueBackReference(String key, int previousResult) {
            if (this.mType == 1 || this.mType == 2 || this.mType == 4) {
                if (this.mValuesBackReferences == null) {
                    this.mValuesBackReferences = new ContentValues();
                }
                this.mValuesBackReferences.put(key, Integer.valueOf(previousResult));
                return this;
            }
            throw new IllegalArgumentException("only inserts, updates, and asserts can have value back-references");
        }

        public Builder withSelectionBackReference(int selectionArgIndex, int previousResult) {
            if (this.mType == 2 || this.mType == 3 || this.mType == 4) {
                if (this.mSelectionArgsBackReferences == null) {
                    this.mSelectionArgsBackReferences = new HashMap();
                }
                this.mSelectionArgsBackReferences.put(Integer.valueOf(selectionArgIndex), Integer.valueOf(previousResult));
                return this;
            }
            throw new IllegalArgumentException("only updates, deletes, and asserts can have selection back-references");
        }

        public Builder withValues(ContentValues values) {
            if (this.mType == 1 || this.mType == 2 || this.mType == 4) {
                if (this.mValues == null) {
                    this.mValues = new ContentValues();
                }
                this.mValues.putAll(values);
                return this;
            }
            throw new IllegalArgumentException("only inserts, updates, and asserts can have values");
        }

        public Builder withValue(String key, Object value) {
            if (this.mType == 1 || this.mType == 2 || this.mType == 4) {
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
            throw new IllegalArgumentException("only inserts and updates can have values");
        }

        public Builder withSelection(String selection, String[] selectionArgs) {
            if (this.mType == 2 || this.mType == 3 || this.mType == 4) {
                this.mSelection = selection;
                if (selectionArgs == null) {
                    this.mSelectionArgs = null;
                } else {
                    this.mSelectionArgs = new String[selectionArgs.length];
                    System.arraycopy(selectionArgs, 0, this.mSelectionArgs, 0, selectionArgs.length);
                }
                return this;
            }
            throw new IllegalArgumentException("only updates, deletes, and asserts can have selections");
        }

        public Builder withExpectedCount(int count) {
            if (this.mType == 2 || this.mType == 3 || this.mType == 4) {
                this.mExpectedCount = Integer.valueOf(count);
                return this;
            }
            throw new IllegalArgumentException("only updates, deletes, and asserts can have expected counts");
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
