package android.support.p011v4.graphics;

import android.content.Context;
import android.content.res.Resources;
import android.p007os.Process;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.util.Log;
import com.android.internal.content.NativeLibraryHelper;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* renamed from: android.support.v4.graphics.TypefaceCompatUtil */
/* loaded from: classes3.dex */
public class TypefaceCompatUtil {
    private static final String CACHE_FILE_PREFIX = ".font";
    private static final String TAG = "TypefaceCompatUtil";

    private TypefaceCompatUtil() {
    }

    public static File getTempFile(Context context) {
        String prefix = CACHE_FILE_PREFIX + Process.myPid() + NativeLibraryHelper.CLEAR_ABI_OVERRIDE + Process.myTid() + NativeLibraryHelper.CLEAR_ABI_OVERRIDE;
        for (int i = 0; i < 100; i++) {
            File file = new File(context.getCacheDir(), prefix + i);
            if (file.createNewFile()) {
                return file;
            }
        }
        return null;
    }

    @RequiresApi(19)
    private static ByteBuffer mmap(File file) {
        try {
            FileInputStream fis = new FileInputStream(file);
            FileChannel channel = fis.getChannel();
            long size = channel.size();
            MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_ONLY, 0L, size);
            fis.close();
            return map;
        } catch (IOException e) {
            return null;
        }
    }

    /*  JADX ERROR: JadxRuntimeException in pass: BlockProcessor
        jadx.core.utils.exceptions.JadxRuntimeException: Found unreachable blocks
        	at jadx.core.dex.visitors.blocks.DominatorTree.sortBlocks(DominatorTree.java:35)
        	at jadx.core.dex.visitors.blocks.DominatorTree.compute(DominatorTree.java:25)
        	at jadx.core.dex.visitors.blocks.BlockProcessor.computeDominators(BlockProcessor.java:202)
        	at jadx.core.dex.visitors.blocks.BlockProcessor.processBlocksTree(BlockProcessor.java:45)
        	at jadx.core.dex.visitors.blocks.BlockProcessor.visit(BlockProcessor.java:39)
        */
    @android.support.annotation.RequiresApi(19)
    public static java.nio.ByteBuffer mmap(android.content.Context r11, android.p007os.CancellationSignal r12, android.net.Uri r13) {
        /*
            android.content.ContentResolver r0 = r11.getContentResolver()
            r1 = 0
            java.lang.String r2 = "r"
            android.os.ParcelFileDescriptor r2 = r0.openFileDescriptor(r13, r2, r12)     // Catch: java.io.IOException -> L61
            java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L4a
            java.io.FileDescriptor r4 = r2.getFileDescriptor()     // Catch: java.lang.Throwable -> L4a
            r3.<init>(r4)     // Catch: java.lang.Throwable -> L4a
            java.nio.channels.FileChannel r4 = r3.getChannel()     // Catch: java.lang.Throwable -> L32
            long r8 = r4.size()     // Catch: java.lang.Throwable -> L32
            java.nio.channels.FileChannel$MapMode r5 = java.nio.channels.FileChannel.MapMode.READ_ONLY     // Catch: java.lang.Throwable -> L32
            r6 = 0
            java.nio.MappedByteBuffer r5 = r4.map(r5, r6, r8)     // Catch: java.lang.Throwable -> L32
            r3.close()     // Catch: java.lang.Throwable -> L4a
            if (r2 == 0) goto L2e
            r2.close()     // Catch: java.io.IOException -> L61
        L2e:
            return r5
        L2f:
            r4 = move-exception
            r5 = r1
            goto L38
        L32:
            r4 = move-exception
            throw r4     // Catch: java.lang.Throwable -> L34
        L34:
            r5 = move-exception
            r10 = r5
            r5 = r4
            r4 = r10
        L38:
            if (r5 == 0) goto L43
            r3.close()     // Catch: java.lang.Throwable -> L3e
            goto L46
        L3e:
            r6 = move-exception
            r5.addSuppressed(r6)     // Catch: java.lang.Throwable -> L4a
            goto L46
        L43:
            r3.close()     // Catch: java.lang.Throwable -> L4a
        L46:
            throw r4     // Catch: java.lang.Throwable -> L4a
        L47:
            r3 = move-exception
            r4 = r1
            goto L50
        L4a:
            r3 = move-exception
            throw r3     // Catch: java.lang.Throwable -> L4c
        L4c:
            r4 = move-exception
            r10 = r4
            r4 = r3
            r3 = r10
        L50:
            if (r2 == 0) goto L60
            if (r4 == 0) goto L5d
            r2.close()     // Catch: java.lang.Throwable -> L58
            goto L60
        L58:
            r5 = move-exception
            r4.addSuppressed(r5)     // Catch: java.io.IOException -> L61
            goto L60
        L5d:
            r2.close()     // Catch: java.io.IOException -> L61
        L60:
            throw r3     // Catch: java.io.IOException -> L61
        L61:
            r2 = move-exception
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.p011v4.graphics.TypefaceCompatUtil.mmap(android.content.Context, android.os.CancellationSignal, android.net.Uri):java.nio.ByteBuffer");
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
            try {
                os = new FileOutputStream(file, false);
                byte[] buffer = new byte[1024];
                while (true) {
                    int readLen = is.read(buffer);
                    if (readLen == -1) {
                        break;
                    }
                    os.write(buffer, 0, readLen);
                }
                z = true;
            } catch (IOException e) {
                Log.m70e(TAG, "Error copying resource contents to temp file: " + e.getMessage());
            }
            return z;
        } finally {
            closeQuietly(os);
        }
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
