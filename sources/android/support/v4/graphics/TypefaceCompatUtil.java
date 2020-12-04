package android.support.v4.graphics;

import android.content.Context;
import android.content.res.Resources;
import android.os.Process;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.util.Log;
import com.android.internal.content.NativeLibraryHelper;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class TypefaceCompatUtil {
    private static final String CACHE_FILE_PREFIX = ".font";
    private static final String TAG = "TypefaceCompatUtil";

    private TypefaceCompatUtil() {
    }

    public static File getTempFile(Context context) {
        String prefix = CACHE_FILE_PREFIX + Process.myPid() + NativeLibraryHelper.CLEAR_ABI_OVERRIDE + Process.myTid() + NativeLibraryHelper.CLEAR_ABI_OVERRIDE;
        int i = 0;
        while (i < 100) {
            File file = new File(context.getCacheDir(), prefix + i);
            try {
                if (file.createNewFile()) {
                    return file;
                }
                i++;
            } catch (IOException e) {
            }
        }
        return null;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x001f, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0020, code lost:
        r8 = r3;
        r3 = r2;
        r2 = r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x001a, code lost:
        r2 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x001b, code lost:
        r3 = null;
     */
    @android.support.annotation.RequiresApi(19)
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.nio.ByteBuffer mmap(java.io.File r9) {
        /*
            r0 = 0
            java.io.FileInputStream r1 = new java.io.FileInputStream     // Catch:{ IOException -> 0x0032 }
            r1.<init>(r9)     // Catch:{ IOException -> 0x0032 }
            java.nio.channels.FileChannel r2 = r1.getChannel()     // Catch:{ Throwable -> 0x001d, all -> 0x001a }
            long r6 = r2.size()     // Catch:{ Throwable -> 0x001d, all -> 0x001a }
            java.nio.channels.FileChannel$MapMode r3 = java.nio.channels.FileChannel.MapMode.READ_ONLY     // Catch:{ Throwable -> 0x001d, all -> 0x001a }
            r4 = 0
            java.nio.MappedByteBuffer r3 = r2.map(r3, r4, r6)     // Catch:{ Throwable -> 0x001d, all -> 0x001a }
            r1.close()     // Catch:{ IOException -> 0x0032 }
            return r3
        L_0x001a:
            r2 = move-exception
            r3 = r0
            goto L_0x0023
        L_0x001d:
            r2 = move-exception
            throw r2     // Catch:{ all -> 0x001f }
        L_0x001f:
            r3 = move-exception
            r8 = r3
            r3 = r2
            r2 = r8
        L_0x0023:
            if (r3 == 0) goto L_0x002e
            r1.close()     // Catch:{ Throwable -> 0x0029 }
            goto L_0x0031
        L_0x0029:
            r4 = move-exception
            r3.addSuppressed(r4)     // Catch:{ IOException -> 0x0032 }
            goto L_0x0031
        L_0x002e:
            r1.close()     // Catch:{ IOException -> 0x0032 }
        L_0x0031:
            throw r2     // Catch:{ IOException -> 0x0032 }
        L_0x0032:
            r1 = move-exception
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.graphics.TypefaceCompatUtil.mmap(java.io.File):java.nio.ByteBuffer");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x002f, code lost:
        r4 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0030, code lost:
        r5 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0034, code lost:
        r5 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0035, code lost:
        r10 = r5;
        r5 = r4;
        r4 = r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0047, code lost:
        r3 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0048, code lost:
        r4 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x004c, code lost:
        r4 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x004d, code lost:
        r10 = r4;
        r4 = r3;
        r3 = r10;
     */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0047 A[ExcHandler: all (th java.lang.Throwable)] */
    @android.support.annotation.RequiresApi(19)
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.nio.ByteBuffer mmap(android.content.Context r11, android.os.CancellationSignal r12, android.net.Uri r13) {
        /*
            android.content.ContentResolver r0 = r11.getContentResolver()
            r1 = 0
            java.lang.String r2 = "r"
            android.os.ParcelFileDescriptor r2 = r0.openFileDescriptor(r13, r2, r12)     // Catch:{ IOException -> 0x0061 }
            java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch:{ Throwable -> 0x004a, all -> 0x0047 }
            java.io.FileDescriptor r4 = r2.getFileDescriptor()     // Catch:{ Throwable -> 0x004a, all -> 0x0047 }
            r3.<init>(r4)     // Catch:{ Throwable -> 0x004a, all -> 0x0047 }
            java.nio.channels.FileChannel r4 = r3.getChannel()     // Catch:{ Throwable -> 0x0032, all -> 0x002f }
            long r8 = r4.size()     // Catch:{ Throwable -> 0x0032, all -> 0x002f }
            java.nio.channels.FileChannel$MapMode r5 = java.nio.channels.FileChannel.MapMode.READ_ONLY     // Catch:{ Throwable -> 0x0032, all -> 0x002f }
            r6 = 0
            java.nio.MappedByteBuffer r5 = r4.map(r5, r6, r8)     // Catch:{ Throwable -> 0x0032, all -> 0x002f }
            r3.close()     // Catch:{ Throwable -> 0x004a, all -> 0x0047 }
            if (r2 == 0) goto L_0x002e
            r2.close()     // Catch:{ IOException -> 0x0061 }
        L_0x002e:
            return r5
        L_0x002f:
            r4 = move-exception
            r5 = r1
            goto L_0x0038
        L_0x0032:
            r4 = move-exception
            throw r4     // Catch:{ all -> 0x0034 }
        L_0x0034:
            r5 = move-exception
            r10 = r5
            r5 = r4
            r4 = r10
        L_0x0038:
            if (r5 == 0) goto L_0x0043
            r3.close()     // Catch:{ Throwable -> 0x003e, all -> 0x0047 }
            goto L_0x0046
        L_0x003e:
            r6 = move-exception
            r5.addSuppressed(r6)     // Catch:{ Throwable -> 0x004a, all -> 0x0047 }
            goto L_0x0046
        L_0x0043:
            r3.close()     // Catch:{ Throwable -> 0x004a, all -> 0x0047 }
        L_0x0046:
            throw r4     // Catch:{ Throwable -> 0x004a, all -> 0x0047 }
        L_0x0047:
            r3 = move-exception
            r4 = r1
            goto L_0x0050
        L_0x004a:
            r3 = move-exception
            throw r3     // Catch:{ all -> 0x004c }
        L_0x004c:
            r4 = move-exception
            r10 = r4
            r4 = r3
            r3 = r10
        L_0x0050:
            if (r2 == 0) goto L_0x0060
            if (r4 == 0) goto L_0x005d
            r2.close()     // Catch:{ Throwable -> 0x0058 }
            goto L_0x0060
        L_0x0058:
            r5 = move-exception
            r4.addSuppressed(r5)     // Catch:{ IOException -> 0x0061 }
            goto L_0x0060
        L_0x005d:
            r2.close()     // Catch:{ IOException -> 0x0061 }
        L_0x0060:
            throw r3     // Catch:{ IOException -> 0x0061 }
        L_0x0061:
            r2 = move-exception
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.graphics.TypefaceCompatUtil.mmap(android.content.Context, android.os.CancellationSignal, android.net.Uri):java.nio.ByteBuffer");
    }

    @RequiresApi(19)
    public static ByteBuffer copyToDirectBuffer(Context context, Resources res, int id) {
        File tmpFile = getTempFile(context);
        ByteBuffer byteBuffer = null;
        if (tmpFile == null) {
            return null;
        }
        try {
            if (copyToFile(tmpFile, res, id)) {
                byteBuffer = mmap(tmpFile);
            }
            return byteBuffer;
        } finally {
            tmpFile.delete();
        }
    }

    public static boolean copyToFile(File file, InputStream is) {
        FileOutputStream os = null;
        boolean z = false;
        try {
            os = new FileOutputStream(file, false);
            byte[] buffer = new byte[1024];
            while (true) {
                int read = is.read(buffer);
                int readLen = read;
                if (read == -1) {
                    break;
                }
                os.write(buffer, 0, readLen);
            }
            z = true;
        } catch (IOException e) {
            Log.e(TAG, "Error copying resource contents to temp file: " + e.getMessage());
        } catch (Throwable th) {
            closeQuietly((Closeable) null);
            throw th;
        }
        closeQuietly(os);
        return z;
    }

    public static boolean copyToFile(File file, Resources res, int id) {
        InputStream is = null;
        try {
            is = res.openRawResource(id);
            return copyToFile(file, is);
        } finally {
            closeQuietly(is);
        }
    }

    public static void closeQuietly(Closeable c) {
        if (c != null) {
            try {
                c.close();
            } catch (IOException e) {
            }
        }
    }
}
