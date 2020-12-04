package android.database;

import android.annotation.UnsupportedAppUsage;
import android.content.res.Resources;
import android.database.sqlite.SQLiteClosable;
import android.os.Binder;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.util.LongSparseArray;
import com.android.internal.R;
import dalvik.system.CloseGuard;

public class CursorWindow extends SQLiteClosable implements Parcelable {
    public static final Parcelable.Creator<CursorWindow> CREATOR = new Parcelable.Creator<CursorWindow>() {
        public CursorWindow createFromParcel(Parcel source) {
            return new CursorWindow(source);
        }

        public CursorWindow[] newArray(int size) {
            return new CursorWindow[size];
        }
    };
    private static final String STATS_TAG = "CursorWindowStats";
    @UnsupportedAppUsage
    private static int sCursorWindowSize = -1;
    @UnsupportedAppUsage
    private static final LongSparseArray<Integer> sWindowToPidMap = new LongSparseArray<>();
    private final CloseGuard mCloseGuard;
    private final String mName;
    private int mStartPos;
    @UnsupportedAppUsage
    public long mWindowPtr;

    private static native boolean nativeAllocRow(long j);

    private static native void nativeClear(long j);

    private static native void nativeCopyStringToBuffer(long j, int i, int i2, CharArrayBuffer charArrayBuffer);

    private static native long nativeCreate(String str, int i);

    private static native long nativeCreateFromParcel(Parcel parcel);

    private static native void nativeDispose(long j);

    private static native void nativeFreeLastRow(long j);

    private static native byte[] nativeGetBlob(long j, int i, int i2);

    private static native double nativeGetDouble(long j, int i, int i2);

    private static native long nativeGetLong(long j, int i, int i2);

    private static native String nativeGetName(long j);

    private static native int nativeGetNumRows(long j);

    private static native String nativeGetString(long j, int i, int i2);

    private static native int nativeGetType(long j, int i, int i2);

    private static native boolean nativePutBlob(long j, byte[] bArr, int i, int i2);

    private static native boolean nativePutDouble(long j, double d, int i, int i2);

    private static native boolean nativePutLong(long j, long j2, int i, int i2);

    private static native boolean nativePutNull(long j, int i, int i2);

    private static native boolean nativePutString(long j, String str, int i, int i2);

    private static native boolean nativeSetNumColumns(long j, int i);

    private static native void nativeWriteToParcel(long j, Parcel parcel);

    public CursorWindow(String name) {
        this(name, (long) getCursorWindowSize());
    }

    public CursorWindow(String name, long windowSizeBytes) {
        this.mCloseGuard = CloseGuard.get();
        this.mStartPos = 0;
        this.mName = (name == null || name.length() == 0) ? "<unnamed>" : name;
        this.mWindowPtr = nativeCreate(this.mName, (int) windowSizeBytes);
        if (this.mWindowPtr != 0) {
            this.mCloseGuard.open("close");
            recordNewWindow(Binder.getCallingPid(), this.mWindowPtr);
            return;
        }
        throw new AssertionError();
    }

    @Deprecated
    public CursorWindow(boolean localWindow) {
        this((String) null);
    }

    private CursorWindow(Parcel source) {
        this.mCloseGuard = CloseGuard.get();
        this.mStartPos = source.readInt();
        this.mWindowPtr = nativeCreateFromParcel(source);
        if (this.mWindowPtr != 0) {
            this.mName = nativeGetName(this.mWindowPtr);
            this.mCloseGuard.open("close");
            return;
        }
        throw new AssertionError();
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        try {
            if (this.mCloseGuard != null) {
                this.mCloseGuard.warnIfOpen();
            }
            dispose();
        } finally {
            super.finalize();
        }
    }

    private void dispose() {
        if (this.mCloseGuard != null) {
            this.mCloseGuard.close();
        }
        if (this.mWindowPtr != 0) {
            recordClosingOfWindow(this.mWindowPtr);
            nativeDispose(this.mWindowPtr);
            this.mWindowPtr = 0;
        }
    }

    public String getName() {
        return this.mName;
    }

    public void clear() {
        acquireReference();
        try {
            this.mStartPos = 0;
            nativeClear(this.mWindowPtr);
        } finally {
            releaseReference();
        }
    }

    public int getStartPosition() {
        return this.mStartPos;
    }

    public void setStartPosition(int pos) {
        this.mStartPos = pos;
    }

    public int getNumRows() {
        acquireReference();
        try {
            return nativeGetNumRows(this.mWindowPtr);
        } finally {
            releaseReference();
        }
    }

    public boolean setNumColumns(int columnNum) {
        acquireReference();
        try {
            return nativeSetNumColumns(this.mWindowPtr, columnNum);
        } finally {
            releaseReference();
        }
    }

    public boolean allocRow() {
        acquireReference();
        try {
            return nativeAllocRow(this.mWindowPtr);
        } finally {
            releaseReference();
        }
    }

    public void freeLastRow() {
        acquireReference();
        try {
            nativeFreeLastRow(this.mWindowPtr);
        } finally {
            releaseReference();
        }
    }

    @Deprecated
    public boolean isNull(int row, int column) {
        return getType(row, column) == 0;
    }

    @Deprecated
    public boolean isBlob(int row, int column) {
        int type = getType(row, column);
        return type == 4 || type == 0;
    }

    @Deprecated
    public boolean isLong(int row, int column) {
        return getType(row, column) == 1;
    }

    @Deprecated
    public boolean isFloat(int row, int column) {
        return getType(row, column) == 2;
    }

    @Deprecated
    public boolean isString(int row, int column) {
        int type = getType(row, column);
        return type == 3 || type == 0;
    }

    public int getType(int row, int column) {
        acquireReference();
        try {
            return nativeGetType(this.mWindowPtr, row - this.mStartPos, column);
        } finally {
            releaseReference();
        }
    }

    public byte[] getBlob(int row, int column) {
        acquireReference();
        try {
            return nativeGetBlob(this.mWindowPtr, row - this.mStartPos, column);
        } finally {
            releaseReference();
        }
    }

    public String getString(int row, int column) {
        acquireReference();
        try {
            return nativeGetString(this.mWindowPtr, row - this.mStartPos, column);
        } finally {
            releaseReference();
        }
    }

    public void copyStringToBuffer(int row, int column, CharArrayBuffer buffer) {
        if (buffer != null) {
            acquireReference();
            try {
                nativeCopyStringToBuffer(this.mWindowPtr, row - this.mStartPos, column, buffer);
            } finally {
                releaseReference();
            }
        } else {
            throw new IllegalArgumentException("CharArrayBuffer should not be null");
        }
    }

    public long getLong(int row, int column) {
        acquireReference();
        try {
            return nativeGetLong(this.mWindowPtr, row - this.mStartPos, column);
        } finally {
            releaseReference();
        }
    }

    public double getDouble(int row, int column) {
        acquireReference();
        try {
            return nativeGetDouble(this.mWindowPtr, row - this.mStartPos, column);
        } finally {
            releaseReference();
        }
    }

    public short getShort(int row, int column) {
        return (short) ((int) getLong(row, column));
    }

    public int getInt(int row, int column) {
        return (int) getLong(row, column);
    }

    public float getFloat(int row, int column) {
        return (float) getDouble(row, column);
    }

    public boolean putBlob(byte[] value, int row, int column) {
        acquireReference();
        try {
            return nativePutBlob(this.mWindowPtr, value, row - this.mStartPos, column);
        } finally {
            releaseReference();
        }
    }

    public boolean putString(String value, int row, int column) {
        acquireReference();
        try {
            return nativePutString(this.mWindowPtr, value, row - this.mStartPos, column);
        } finally {
            releaseReference();
        }
    }

    public boolean putLong(long value, int row, int column) {
        acquireReference();
        try {
            return nativePutLong(this.mWindowPtr, value, row - this.mStartPos, column);
        } finally {
            releaseReference();
        }
    }

    public boolean putDouble(double value, int row, int column) {
        acquireReference();
        try {
            return nativePutDouble(this.mWindowPtr, value, row - this.mStartPos, column);
        } finally {
            releaseReference();
        }
    }

    public boolean putNull(int row, int column) {
        acquireReference();
        try {
            return nativePutNull(this.mWindowPtr, row - this.mStartPos, column);
        } finally {
            releaseReference();
        }
    }

    public static CursorWindow newFromParcel(Parcel p) {
        return CREATOR.createFromParcel(p);
    }

    public int describeContents() {
        return 0;
    }

    /* JADX INFO: finally extract failed */
    public void writeToParcel(Parcel dest, int flags) {
        acquireReference();
        try {
            dest.writeInt(this.mStartPos);
            nativeWriteToParcel(this.mWindowPtr, dest);
            releaseReference();
            if ((flags & 1) != 0) {
                releaseReference();
            }
        } catch (Throwable th) {
            releaseReference();
            throw th;
        }
    }

    /* access modifiers changed from: protected */
    public void onAllReferencesReleased() {
        dispose();
    }

    private void recordNewWindow(int pid, long window) {
        synchronized (sWindowToPidMap) {
            sWindowToPidMap.put(window, Integer.valueOf(pid));
            if (Log.isLoggable(STATS_TAG, 2)) {
                Log.i(STATS_TAG, "Created a new Cursor. " + printStats());
            }
        }
    }

    private void recordClosingOfWindow(long window) {
        synchronized (sWindowToPidMap) {
            if (sWindowToPidMap.size() != 0) {
                sWindowToPidMap.delete(window);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x003b, code lost:
        r4 = r3.size();
        r5 = 0;
        r2 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0041, code lost:
        if (r2 >= r4) goto L_0x008b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0043, code lost:
        r0.append(" (# cursors opened by ");
        r7 = r3.keyAt(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x004c, code lost:
        if (r7 != r1) goto L_0x0055;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x004e, code lost:
        r0.append("this proc=");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0055, code lost:
        r0.append("pid " + r7 + "=");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x006f, code lost:
        r8 = r3.get(r7);
        r0.append(r8 + ")");
        r5 = r5 + r8;
        r2 = r2 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0091, code lost:
        if (r0.length() <= 980) goto L_0x0098;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0093, code lost:
        r2 = r0.substring(0, 980);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0098, code lost:
        r2 = r0.toString();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x00b0, code lost:
        return "# Open Cursors=" + r5 + r2;
     */
    @android.annotation.UnsupportedAppUsage
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.String printStats() {
        /*
            r11 = this;
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            int r1 = android.os.Process.myPid()
            r2 = 0
            android.util.SparseIntArray r3 = new android.util.SparseIntArray
            r3.<init>()
            android.util.LongSparseArray<java.lang.Integer> r4 = sWindowToPidMap
            monitor-enter(r4)
            android.util.LongSparseArray<java.lang.Integer> r5 = sWindowToPidMap     // Catch:{ all -> 0x00b1 }
            int r5 = r5.size()     // Catch:{ all -> 0x00b1 }
            if (r5 != 0) goto L_0x001e
            java.lang.String r6 = ""
            monitor-exit(r4)     // Catch:{ all -> 0x00b1 }
            return r6
        L_0x001e:
            r6 = 0
            r7 = r6
        L_0x0020:
            if (r7 >= r5) goto L_0x003a
            android.util.LongSparseArray<java.lang.Integer> r8 = sWindowToPidMap     // Catch:{ all -> 0x00b1 }
            java.lang.Object r8 = r8.valueAt(r7)     // Catch:{ all -> 0x00b1 }
            java.lang.Integer r8 = (java.lang.Integer) r8     // Catch:{ all -> 0x00b1 }
            int r8 = r8.intValue()     // Catch:{ all -> 0x00b1 }
            int r9 = r3.get(r8)     // Catch:{ all -> 0x00b1 }
            int r9 = r9 + 1
            r3.put(r8, r9)     // Catch:{ all -> 0x00b1 }
            int r7 = r7 + 1
            goto L_0x0020
        L_0x003a:
            monitor-exit(r4)     // Catch:{ all -> 0x00b1 }
            int r4 = r3.size()
            r5 = r2
            r2 = r6
        L_0x0041:
            if (r2 >= r4) goto L_0x008b
            java.lang.String r7 = " (# cursors opened by "
            r0.append(r7)
            int r7 = r3.keyAt(r2)
            if (r7 != r1) goto L_0x0055
            java.lang.String r8 = "this proc="
            r0.append(r8)
            goto L_0x006f
        L_0x0055:
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r9 = "pid "
            r8.append(r9)
            r8.append(r7)
            java.lang.String r9 = "="
            r8.append(r9)
            java.lang.String r8 = r8.toString()
            r0.append(r8)
        L_0x006f:
            int r8 = r3.get(r7)
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            r9.append(r8)
            java.lang.String r10 = ")"
            r9.append(r10)
            java.lang.String r9 = r9.toString()
            r0.append(r9)
            int r5 = r5 + r8
            int r2 = r2 + 1
            goto L_0x0041
        L_0x008b:
            int r2 = r0.length()
            r7 = 980(0x3d4, float:1.373E-42)
            if (r2 <= r7) goto L_0x0098
            java.lang.String r2 = r0.substring(r6, r7)
            goto L_0x009c
        L_0x0098:
            java.lang.String r2 = r0.toString()
        L_0x009c:
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = "# Open Cursors="
            r6.append(r7)
            r6.append(r5)
            r6.append(r2)
            java.lang.String r6 = r6.toString()
            return r6
        L_0x00b1:
            r5 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x00b1 }
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: android.database.CursorWindow.printStats():java.lang.String");
    }

    private static int getCursorWindowSize() {
        if (sCursorWindowSize < 0) {
            sCursorWindowSize = Resources.getSystem().getInteger(R.integer.config_cursorWindowSize) * 1024;
        }
        return sCursorWindowSize;
    }

    public String toString() {
        return getName() + " {" + Long.toHexString(this.mWindowPtr) + "}";
    }
}
