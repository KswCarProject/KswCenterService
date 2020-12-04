package android.view.inputmethod;

import android.annotation.UnsupportedAppUsage;
import android.os.Parcel;
import android.util.Slog;
import java.util.List;

public class InputMethodSubtypeArray {
    private static final String TAG = "InputMethodSubtypeArray";
    private volatile byte[] mCompressedData;
    private final int mCount;
    private volatile int mDecompressedSize;
    private volatile InputMethodSubtype[] mInstance;
    private final Object mLockObject = new Object();

    @UnsupportedAppUsage
    public InputMethodSubtypeArray(List<InputMethodSubtype> subtypes) {
        if (subtypes == null) {
            this.mCount = 0;
            return;
        }
        this.mCount = subtypes.size();
        this.mInstance = (InputMethodSubtype[]) subtypes.toArray(new InputMethodSubtype[this.mCount]);
    }

    public InputMethodSubtypeArray(Parcel source) {
        this.mCount = source.readInt();
        if (this.mCount > 0) {
            this.mDecompressedSize = source.readInt();
            this.mCompressedData = source.createByteArray();
        }
    }

    public void writeToParcel(Parcel dest) {
        if (this.mCount == 0) {
            dest.writeInt(this.mCount);
            return;
        }
        byte[] compressedData = this.mCompressedData;
        int decompressedSize = this.mDecompressedSize;
        if (compressedData == null && decompressedSize == 0) {
            synchronized (this.mLockObject) {
                compressedData = this.mCompressedData;
                decompressedSize = this.mDecompressedSize;
                if (compressedData == null && decompressedSize == 0) {
                    byte[] decompressedData = marshall(this.mInstance);
                    compressedData = compress(decompressedData);
                    if (compressedData == null) {
                        decompressedSize = -1;
                        Slog.i(TAG, "Failed to compress data.");
                    } else {
                        decompressedSize = decompressedData.length;
                    }
                    this.mDecompressedSize = decompressedSize;
                    this.mCompressedData = compressedData;
                }
            }
        }
        if (compressedData == null || decompressedSize <= 0) {
            Slog.i(TAG, "Unexpected state. Behaving as an empty array.");
            dest.writeInt(0);
            return;
        }
        dest.writeInt(this.mCount);
        dest.writeInt(decompressedSize);
        dest.writeByteArray(compressedData);
    }

    public InputMethodSubtype get(int index) {
        if (index < 0 || this.mCount <= index) {
            throw new ArrayIndexOutOfBoundsException();
        }
        InputMethodSubtype[] instance = this.mInstance;
        if (instance == null) {
            synchronized (this.mLockObject) {
                instance = this.mInstance;
                if (instance == null) {
                    byte[] decompressedData = decompress(this.mCompressedData, this.mDecompressedSize);
                    this.mCompressedData = null;
                    this.mDecompressedSize = 0;
                    if (decompressedData != null) {
                        instance = unmarshall(decompressedData);
                    } else {
                        Slog.e(TAG, "Failed to decompress data. Returns null as fallback.");
                        instance = new InputMethodSubtype[this.mCount];
                    }
                    this.mInstance = instance;
                }
            }
        }
        return instance[index];
    }

    public int getCount() {
        return this.mCount;
    }

    private static byte[] marshall(InputMethodSubtype[] array) {
        Parcel parcel = null;
        try {
            parcel = Parcel.obtain();
            parcel.writeTypedArray(array, 0);
            return parcel.marshall();
        } finally {
            if (parcel != null) {
                parcel.recycle();
            }
        }
    }

    private static InputMethodSubtype[] unmarshall(byte[] data) {
        Parcel parcel = null;
        try {
            parcel = Parcel.obtain();
            parcel.unmarshall(data, 0, data.length);
            parcel.setDataPosition(0);
            return (InputMethodSubtype[]) parcel.createTypedArray(InputMethodSubtype.CREATOR);
        } finally {
            if (parcel != null) {
                parcel.recycle();
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x001d, code lost:
        r3 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x001e, code lost:
        r4 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0022, code lost:
        r4 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0023, code lost:
        r5 = r4;
        r4 = r3;
        r3 = r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x002a, code lost:
        r2 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x002b, code lost:
        r3 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x002f, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0030, code lost:
        r5 = r3;
        r3 = r2;
        r2 = r5;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static byte[] compress(byte[] r6) {
        /*
            r0 = 0
            java.io.ByteArrayOutputStream r1 = new java.io.ByteArrayOutputStream     // Catch:{ Exception -> 0x0037 }
            r1.<init>()     // Catch:{ Exception -> 0x0037 }
            java.util.zip.GZIPOutputStream r2 = new java.util.zip.GZIPOutputStream     // Catch:{ Throwable -> 0x002d, all -> 0x002a }
            r2.<init>(r1)     // Catch:{ Throwable -> 0x002d, all -> 0x002a }
            r2.write(r6)     // Catch:{ Throwable -> 0x0020, all -> 0x001d }
            r2.finish()     // Catch:{ Throwable -> 0x0020, all -> 0x001d }
            byte[] r3 = r1.toByteArray()     // Catch:{ Throwable -> 0x0020, all -> 0x001d }
            $closeResource(r0, r2)     // Catch:{ Throwable -> 0x002d, all -> 0x002a }
            $closeResource(r0, r1)     // Catch:{ Exception -> 0x0037 }
            return r3
        L_0x001d:
            r3 = move-exception
            r4 = r0
            goto L_0x0026
        L_0x0020:
            r3 = move-exception
            throw r3     // Catch:{ all -> 0x0022 }
        L_0x0022:
            r4 = move-exception
            r5 = r4
            r4 = r3
            r3 = r5
        L_0x0026:
            $closeResource(r4, r2)     // Catch:{ Throwable -> 0x002d, all -> 0x002a }
            throw r3     // Catch:{ Throwable -> 0x002d, all -> 0x002a }
        L_0x002a:
            r2 = move-exception
            r3 = r0
            goto L_0x0033
        L_0x002d:
            r2 = move-exception
            throw r2     // Catch:{ all -> 0x002f }
        L_0x002f:
            r3 = move-exception
            r5 = r3
            r3 = r2
            r2 = r5
        L_0x0033:
            $closeResource(r3, r1)     // Catch:{ Exception -> 0x0037 }
            throw r2     // Catch:{ Exception -> 0x0037 }
        L_0x0037:
            r1 = move-exception
            java.lang.String r2 = "InputMethodSubtypeArray"
            java.lang.String r3 = "Failed to compress the data."
            android.util.Slog.e(r2, r3, r1)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.inputmethod.InputMethodSubtypeArray.compress(byte[]):byte[]");
    }

    private static /* synthetic */ void $closeResource(Throwable x0, AutoCloseable x1) {
        if (x0 != null) {
            try {
                x1.close();
            } catch (Throwable th) {
                x0.addSuppressed(th);
            }
        } else {
            x1.close();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:23:0x002f, code lost:
        r3 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0030, code lost:
        r4 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0034, code lost:
        r4 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0035, code lost:
        r7 = r4;
        r4 = r3;
        r3 = r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x003c, code lost:
        r2 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x003d, code lost:
        r3 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x0041, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x0042, code lost:
        r7 = r3;
        r3 = r2;
        r2 = r7;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static byte[] decompress(byte[] r8, int r9) {
        /*
            r0 = 0
            java.io.ByteArrayInputStream r1 = new java.io.ByteArrayInputStream     // Catch:{ Exception -> 0x0049 }
            r1.<init>(r8)     // Catch:{ Exception -> 0x0049 }
            java.util.zip.GZIPInputStream r2 = new java.util.zip.GZIPInputStream     // Catch:{ Throwable -> 0x003f, all -> 0x003c }
            r2.<init>(r1)     // Catch:{ Throwable -> 0x003f, all -> 0x003c }
            byte[] r3 = new byte[r9]     // Catch:{ Throwable -> 0x0032, all -> 0x002f }
            r4 = 0
        L_0x000f:
            int r5 = r3.length     // Catch:{ Throwable -> 0x0032, all -> 0x002f }
            if (r4 >= r5) goto L_0x001d
            int r5 = r3.length     // Catch:{ Throwable -> 0x0032, all -> 0x002f }
            int r5 = r5 - r4
            int r6 = r2.read(r3, r4, r5)     // Catch:{ Throwable -> 0x0032, all -> 0x002f }
            if (r6 >= 0) goto L_0x001b
            goto L_0x001d
        L_0x001b:
            int r4 = r4 + r6
            goto L_0x000f
        L_0x001d:
            if (r9 == r4) goto L_0x0027
            $closeResource(r0, r2)     // Catch:{ Throwable -> 0x003f, all -> 0x003c }
            $closeResource(r0, r1)     // Catch:{ Exception -> 0x0049 }
            return r0
        L_0x0027:
            $closeResource(r0, r2)     // Catch:{ Throwable -> 0x003f, all -> 0x003c }
            $closeResource(r0, r1)     // Catch:{ Exception -> 0x0049 }
            return r3
        L_0x002f:
            r3 = move-exception
            r4 = r0
            goto L_0x0038
        L_0x0032:
            r3 = move-exception
            throw r3     // Catch:{ all -> 0x0034 }
        L_0x0034:
            r4 = move-exception
            r7 = r4
            r4 = r3
            r3 = r7
        L_0x0038:
            $closeResource(r4, r2)     // Catch:{ Throwable -> 0x003f, all -> 0x003c }
            throw r3     // Catch:{ Throwable -> 0x003f, all -> 0x003c }
        L_0x003c:
            r2 = move-exception
            r3 = r0
            goto L_0x0045
        L_0x003f:
            r2 = move-exception
            throw r2     // Catch:{ all -> 0x0041 }
        L_0x0041:
            r3 = move-exception
            r7 = r3
            r3 = r2
            r2 = r7
        L_0x0045:
            $closeResource(r3, r1)     // Catch:{ Exception -> 0x0049 }
            throw r2     // Catch:{ Exception -> 0x0049 }
        L_0x0049:
            r1 = move-exception
            java.lang.String r2 = "InputMethodSubtypeArray"
            java.lang.String r3 = "Failed to decompress the data."
            android.util.Slog.e(r2, r3, r1)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.inputmethod.InputMethodSubtypeArray.decompress(byte[], int):byte[]");
    }
}
