package android.os;

import android.annotation.UnsupportedAppUsage;
import android.app.backup.FullBackup;
import android.content.ContentResolver;
import android.os.FileUtils;
import android.provider.DocumentsContract;
import android.system.ErrnoException;
import android.system.Int64Ref;
import android.system.Os;
import android.system.OsConstants;
import android.system.StructStat;
import android.telecom.Logging.Session;
import android.text.TextUtils;
import android.util.Log;
import android.util.Slog;
import android.webkit.MimeTypeMap;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.SizedInputStream;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.regex.Pattern;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;
import libcore.io.IoUtils;
import libcore.util.EmptyArray;

public final class FileUtils {
    private static final long COPY_CHECKPOINT_BYTES = 524288;
    public static final int S_IRGRP = 32;
    public static final int S_IROTH = 4;
    public static final int S_IRUSR = 256;
    public static final int S_IRWXG = 56;
    public static final int S_IRWXO = 7;
    public static final int S_IRWXU = 448;
    public static final int S_IWGRP = 16;
    public static final int S_IWOTH = 2;
    public static final int S_IWUSR = 128;
    public static final int S_IXGRP = 8;
    public static final int S_IXOTH = 1;
    public static final int S_IXUSR = 64;
    private static final String TAG = "FileUtils";
    private static boolean sEnableCopyOptimizations = true;

    public interface ProgressListener {
        void onProgress(long j);
    }

    @UnsupportedAppUsage
    private FileUtils() {
    }

    private static class NoImagePreloadHolder {
        public static final Pattern SAFE_FILENAME_PATTERN = Pattern.compile("[\\w%+,./=_-]+");

        private NoImagePreloadHolder() {
        }
    }

    @UnsupportedAppUsage
    public static int setPermissions(File path, int mode, int uid, int gid) {
        return setPermissions(path.getAbsolutePath(), mode, uid, gid);
    }

    @UnsupportedAppUsage
    public static int setPermissions(String path, int mode, int uid, int gid) {
        try {
            Os.chmod(path, mode);
            if (uid < 0 && gid < 0) {
                return 0;
            }
            try {
                Os.chown(path, uid, gid);
                return 0;
            } catch (ErrnoException e) {
                Slog.w(TAG, "Failed to chown(" + path + "): " + e);
                return e.errno;
            }
        } catch (ErrnoException e2) {
            Slog.w(TAG, "Failed to chmod(" + path + "): " + e2);
            return e2.errno;
        }
    }

    @UnsupportedAppUsage
    public static int setPermissions(FileDescriptor fd, int mode, int uid, int gid) {
        try {
            Os.fchmod(fd, mode);
            if (uid < 0 && gid < 0) {
                return 0;
            }
            try {
                Os.fchown(fd, uid, gid);
                return 0;
            } catch (ErrnoException e) {
                Slog.w(TAG, "Failed to fchown(): " + e);
                return e.errno;
            }
        } catch (ErrnoException e2) {
            Slog.w(TAG, "Failed to fchmod(): " + e2);
            return e2.errno;
        }
    }

    public static void copyPermissions(File from, File to) throws IOException {
        try {
            StructStat stat = Os.stat(from.getAbsolutePath());
            Os.chmod(to.getAbsolutePath(), stat.st_mode);
            Os.chown(to.getAbsolutePath(), stat.st_uid, stat.st_gid);
        } catch (ErrnoException e) {
            throw e.rethrowAsIOException();
        }
    }

    @Deprecated
    public static int getUid(String path) {
        try {
            return Os.stat(path).st_uid;
        } catch (ErrnoException e) {
            return -1;
        }
    }

    @UnsupportedAppUsage
    public static boolean sync(FileOutputStream stream) {
        if (stream == null) {
            return true;
        }
        try {
            stream.getFD().sync();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Deprecated
    @UnsupportedAppUsage
    public static boolean copyFile(File srcFile, File destFile) {
        try {
            copyFileOrThrow(srcFile, destFile);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0014, code lost:
        throw r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:5:0x000d, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0011, code lost:
        $closeResource(r1, r0);
     */
    @java.lang.Deprecated
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void copyFileOrThrow(java.io.File r3, java.io.File r4) throws java.io.IOException {
        /*
            java.io.FileInputStream r0 = new java.io.FileInputStream
            r0.<init>(r3)
            r1 = 0
            copyToFileOrThrow(r0, r4)     // Catch:{ Throwable -> 0x000f }
            $closeResource(r1, r0)
            return
        L_0x000d:
            r2 = move-exception
            goto L_0x0011
        L_0x000f:
            r1 = move-exception
            throw r1     // Catch:{ all -> 0x000d }
        L_0x0011:
            $closeResource(r1, r0)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: android.os.FileUtils.copyFileOrThrow(java.io.File, java.io.File):void");
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

    @Deprecated
    @UnsupportedAppUsage
    public static boolean copyToFile(InputStream inputStream, File destFile) {
        try {
            copyToFileOrThrow(inputStream, destFile);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0024, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0028, code lost:
        $closeResource(r1, r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x002b, code lost:
        throw r2;
     */
    @java.lang.Deprecated
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void copyToFileOrThrow(java.io.InputStream r4, java.io.File r5) throws java.io.IOException {
        /*
            boolean r0 = r5.exists()
            if (r0 == 0) goto L_0x0009
            r5.delete()
        L_0x0009:
            java.io.FileOutputStream r0 = new java.io.FileOutputStream
            r0.<init>(r5)
            r1 = 0
            copy((java.io.InputStream) r4, (java.io.OutputStream) r0)     // Catch:{ Throwable -> 0x0026 }
            java.io.FileDescriptor r2 = r0.getFD()     // Catch:{ ErrnoException -> 0x001e }
            android.system.Os.fsync(r2)     // Catch:{ ErrnoException -> 0x001e }
            $closeResource(r1, r0)
            return
        L_0x001e:
            r2 = move-exception
            java.io.IOException r3 = r2.rethrowAsIOException()     // Catch:{ Throwable -> 0x0026 }
            throw r3     // Catch:{ Throwable -> 0x0026 }
        L_0x0024:
            r2 = move-exception
            goto L_0x0028
        L_0x0026:
            r1 = move-exception
            throw r1     // Catch:{ all -> 0x0024 }
        L_0x0028:
            $closeResource(r1, r0)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: android.os.FileUtils.copyToFileOrThrow(java.io.InputStream, java.io.File):void");
    }

    public static long copy(File from, File to) throws IOException {
        return copy(from, to, (CancellationSignal) null, (Executor) null, (ProgressListener) null);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0018, code lost:
        r4 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x001c, code lost:
        r4 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x001d, code lost:
        r5 = r4;
        r4 = r3;
        r3 = r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0024, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0028, code lost:
        $closeResource(r1, r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x002b, code lost:
        throw r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0017, code lost:
        r3 = th;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static long copy(java.io.File r6, java.io.File r7, android.os.CancellationSignal r8, java.util.concurrent.Executor r9, android.os.FileUtils.ProgressListener r10) throws java.io.IOException {
        /*
            java.io.FileInputStream r0 = new java.io.FileInputStream
            r0.<init>(r6)
            r1 = 0
            java.io.FileOutputStream r2 = new java.io.FileOutputStream     // Catch:{ Throwable -> 0x0026 }
            r2.<init>(r7)     // Catch:{ Throwable -> 0x0026 }
            long r3 = copy((java.io.InputStream) r0, (java.io.OutputStream) r2, (android.os.CancellationSignal) r8, (java.util.concurrent.Executor) r9, (android.os.FileUtils.ProgressListener) r10)     // Catch:{ Throwable -> 0x001a, all -> 0x0017 }
            $closeResource(r1, r2)     // Catch:{ Throwable -> 0x0026 }
            $closeResource(r1, r0)
            return r3
        L_0x0017:
            r3 = move-exception
            r4 = r1
            goto L_0x0020
        L_0x001a:
            r3 = move-exception
            throw r3     // Catch:{ all -> 0x001c }
        L_0x001c:
            r4 = move-exception
            r5 = r4
            r4 = r3
            r3 = r5
        L_0x0020:
            $closeResource(r4, r2)     // Catch:{ Throwable -> 0x0026 }
            throw r3     // Catch:{ Throwable -> 0x0026 }
        L_0x0024:
            r2 = move-exception
            goto L_0x0028
        L_0x0026:
            r1 = move-exception
            throw r1     // Catch:{ all -> 0x0024 }
        L_0x0028:
            $closeResource(r1, r0)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: android.os.FileUtils.copy(java.io.File, java.io.File, android.os.CancellationSignal, java.util.concurrent.Executor, android.os.FileUtils$ProgressListener):long");
    }

    public static long copy(InputStream in, OutputStream out) throws IOException {
        return copy(in, out, (CancellationSignal) null, (Executor) null, (ProgressListener) null);
    }

    public static long copy(InputStream in, OutputStream out, CancellationSignal signal, Executor executor, ProgressListener listener) throws IOException {
        if (!sEnableCopyOptimizations || !(in instanceof FileInputStream) || !(out instanceof FileOutputStream)) {
            return copyInternalUserspace(in, out, signal, executor, listener);
        }
        return copy(((FileInputStream) in).getFD(), ((FileOutputStream) out).getFD(), signal, executor, listener);
    }

    public static long copy(FileDescriptor in, FileDescriptor out) throws IOException {
        return copy(in, out, (CancellationSignal) null, (Executor) null, (ProgressListener) null);
    }

    public static long copy(FileDescriptor in, FileDescriptor out, CancellationSignal signal, Executor executor, ProgressListener listener) throws IOException {
        return copy(in, out, Long.MAX_VALUE, signal, executor, listener);
    }

    public static long copy(FileDescriptor in, FileDescriptor out, long count, CancellationSignal signal, Executor executor, ProgressListener listener) throws IOException {
        if (sEnableCopyOptimizations) {
            try {
                StructStat st_in = Os.fstat(in);
                StructStat st_out = Os.fstat(out);
                if (OsConstants.S_ISREG(st_in.st_mode) && OsConstants.S_ISREG(st_out.st_mode)) {
                    return copyInternalSendfile(in, out, count, signal, executor, listener);
                }
                if (!OsConstants.S_ISFIFO(st_in.st_mode)) {
                    if (OsConstants.S_ISFIFO(st_out.st_mode)) {
                    }
                }
                return copyInternalSplice(in, out, count, signal, executor, listener);
            } catch (ErrnoException e) {
                throw e.rethrowAsIOException();
            }
        }
        return copyInternalUserspace(in, out, count, signal, executor, listener);
    }

    @VisibleForTesting
    public static long copyInternalSplice(FileDescriptor in, FileDescriptor out, long count, CancellationSignal signal, Executor executor, ProgressListener listener) throws ErrnoException {
        Executor executor2 = executor;
        ProgressListener progressListener = listener;
        long count2 = count;
        long progress = 0;
        long checkpoint = 0;
        while (true) {
            long splice = Os.splice(in, (Int64Ref) null, out, (Int64Ref) null, Math.min(count2, 524288), OsConstants.SPLICE_F_MOVE | OsConstants.SPLICE_F_MORE);
            long t = splice;
            if (splice == 0) {
                break;
            }
            progress += t;
            checkpoint += t;
            count2 -= t;
            if (checkpoint >= 524288) {
                if (signal != null) {
                    signal.throwIfCanceled();
                }
                if (!(executor2 == null || progressListener == null)) {
                    executor2.execute(new Runnable(progress) {
                        private final /* synthetic */ long f$1;

                        {
                            this.f$1 = r2;
                        }

                        public final void run() {
                            FileUtils.ProgressListener.this.onProgress(this.f$1);
                        }
                    });
                }
                checkpoint = 0;
            }
        }
        if (!(executor2 == null || progressListener == null)) {
            executor2.execute(new Runnable(progress) {
                private final /* synthetic */ long f$1;

                {
                    this.f$1 = r2;
                }

                public final void run() {
                    FileUtils.ProgressListener.this.onProgress(this.f$1);
                }
            });
        }
        return progress;
    }

    @VisibleForTesting
    public static long copyInternalSendfile(FileDescriptor in, FileDescriptor out, long count, CancellationSignal signal, Executor executor, ProgressListener listener) throws ErrnoException {
        Executor executor2 = executor;
        ProgressListener progressListener = listener;
        long count2 = count;
        long progress = 0;
        long checkpoint = 0;
        while (true) {
            long sendfile = Os.sendfile(out, in, (Int64Ref) null, Math.min(count2, 524288));
            long t = sendfile;
            if (sendfile == 0) {
                break;
            }
            progress += t;
            checkpoint += t;
            count2 -= t;
            if (checkpoint >= 524288) {
                if (signal != null) {
                    signal.throwIfCanceled();
                }
                if (!(executor2 == null || progressListener == null)) {
                    executor2.execute(new Runnable(progress) {
                        private final /* synthetic */ long f$1;

                        {
                            this.f$1 = r2;
                        }

                        public final void run() {
                            FileUtils.ProgressListener.this.onProgress(this.f$1);
                        }
                    });
                }
                checkpoint = 0;
            }
        }
        if (!(executor2 == null || progressListener == null)) {
            executor2.execute(new Runnable(progress) {
                private final /* synthetic */ long f$1;

                {
                    this.f$1 = r2;
                }

                public final void run() {
                    FileUtils.ProgressListener.this.onProgress(this.f$1);
                }
            });
        }
        return progress;
    }

    @VisibleForTesting
    @Deprecated
    public static long copyInternalUserspace(FileDescriptor in, FileDescriptor out, ProgressListener listener, CancellationSignal signal, long count) throws IOException {
        return copyInternalUserspace(in, out, count, signal, $$Lambda$_14QHG018Z6p13d3hzJuGTWnNeo.INSTANCE, listener);
    }

    @VisibleForTesting
    public static long copyInternalUserspace(FileDescriptor in, FileDescriptor out, long count, CancellationSignal signal, Executor executor, ProgressListener listener) throws IOException {
        if (count != Long.MAX_VALUE) {
            return copyInternalUserspace((InputStream) new SizedInputStream(new FileInputStream(in), count), (OutputStream) new FileOutputStream(out), signal, executor, listener);
        }
        return copyInternalUserspace((InputStream) new FileInputStream(in), (OutputStream) new FileOutputStream(out), signal, executor, listener);
    }

    @VisibleForTesting
    public static long copyInternalUserspace(InputStream in, OutputStream out, CancellationSignal signal, Executor executor, ProgressListener listener) throws IOException {
        long progress = 0;
        long checkpoint = 0;
        byte[] buffer = new byte[8192];
        while (true) {
            int read = in.read(buffer);
            int t = read;
            if (read == -1) {
                break;
            }
            out.write(buffer, 0, t);
            progress += (long) t;
            checkpoint += (long) t;
            if (checkpoint >= 524288) {
                if (signal != null) {
                    signal.throwIfCanceled();
                }
                if (!(executor == null || listener == null)) {
                    executor.execute(new Runnable(progress) {
                        private final /* synthetic */ long f$1;

                        {
                            this.f$1 = r2;
                        }

                        public final void run() {
                            FileUtils.ProgressListener.this.onProgress(this.f$1);
                        }
                    });
                }
                checkpoint = 0;
            }
        }
        if (!(executor == null || listener == null)) {
            executor.execute(new Runnable(progress) {
                private final /* synthetic */ long f$1;

                {
                    this.f$1 = r2;
                }

                public final void run() {
                    FileUtils.ProgressListener.this.onProgress(this.f$1);
                }
            });
        }
        return progress;
    }

    @UnsupportedAppUsage
    public static boolean isFilenameSafe(File file) {
        return NoImagePreloadHolder.SAFE_FILENAME_PATTERN.matcher(file.getPath()).matches();
    }

    @UnsupportedAppUsage
    public static String readTextFile(File file, int max, String ellipsis) throws IOException {
        int len;
        int len2;
        InputStream input = new FileInputStream(file);
        BufferedInputStream bis = new BufferedInputStream(input);
        try {
            long size = file.length();
            if (max <= 0) {
                if (size <= 0 || max != 0) {
                    if (max < 0) {
                        boolean rolled = false;
                        byte[] last = null;
                        byte[] data = null;
                        do {
                            if (last != null) {
                                rolled = true;
                            }
                            byte[] tmp = last;
                            last = data;
                            data = tmp;
                            if (data == null) {
                                data = new byte[(-max)];
                            }
                            len2 = bis.read(data);
                        } while (len2 == data.length);
                        if (last == null && len2 <= 0) {
                            bis.close();
                            input.close();
                            return "";
                        } else if (last == null) {
                            String str = new String(data, 0, len2);
                            bis.close();
                            input.close();
                            return str;
                        } else {
                            if (len2 > 0) {
                                rolled = true;
                                System.arraycopy(last, len2, last, 0, last.length - len2);
                                System.arraycopy(data, 0, last, last.length - len2, len2);
                            }
                            if (ellipsis != null) {
                                if (rolled) {
                                    String str2 = ellipsis + new String(last);
                                    bis.close();
                                    input.close();
                                    return str2;
                                }
                            }
                            String str3 = new String(last);
                            bis.close();
                            input.close();
                            return str3;
                        }
                    } else {
                        ByteArrayOutputStream contents = new ByteArrayOutputStream();
                        byte[] data2 = new byte[1024];
                        do {
                            len = bis.read(data2);
                            if (len > 0) {
                                contents.write(data2, 0, len);
                            }
                        } while (len == data2.length);
                        String byteArrayOutputStream = contents.toString();
                        bis.close();
                        input.close();
                        return byteArrayOutputStream;
                    }
                }
            }
            if (size > 0 && (max == 0 || size < ((long) max))) {
                max = (int) size;
            }
            byte[] data3 = new byte[(max + 1)];
            int length = bis.read(data3);
            if (length <= 0) {
                return "";
            }
            if (length <= max) {
                String str4 = new String(data3, 0, length);
                bis.close();
                input.close();
                return str4;
            } else if (ellipsis == null) {
                String str5 = new String(data3, 0, max);
                bis.close();
                input.close();
                return str5;
            } else {
                String str6 = new String(data3, 0, max) + ellipsis;
                bis.close();
                input.close();
                return str6;
            }
        } finally {
            bis.close();
            input.close();
        }
    }

    @UnsupportedAppUsage
    public static void stringToFile(File file, String string) throws IOException {
        stringToFile(file.getAbsolutePath(), string);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0036, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x003a, code lost:
        $closeResource(r1, r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x003d, code lost:
        throw r2;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void bytesToFile(java.lang.String r4, byte[] r5) throws java.io.IOException {
        /*
            java.lang.String r0 = "/proc/"
            boolean r0 = r4.startsWith(r0)
            r1 = 0
            if (r0 == 0) goto L_0x002a
            int r0 = android.os.StrictMode.allowThreadDiskWritesMask()
            java.io.FileOutputStream r2 = new java.io.FileOutputStream     // Catch:{ all -> 0x0025 }
            r2.<init>(r4)     // Catch:{ all -> 0x0025 }
            r2.write(r5)     // Catch:{ Throwable -> 0x001f }
            $closeResource(r1, r2)     // Catch:{ all -> 0x0025 }
            android.os.StrictMode.setThreadPolicyMask(r0)
            goto L_0x0035
        L_0x001d:
            r3 = move-exception
            goto L_0x0021
        L_0x001f:
            r1 = move-exception
            throw r1     // Catch:{ all -> 0x001d }
        L_0x0021:
            $closeResource(r1, r2)     // Catch:{ all -> 0x0025 }
            throw r3     // Catch:{ all -> 0x0025 }
        L_0x0025:
            r1 = move-exception
            android.os.StrictMode.setThreadPolicyMask(r0)
            throw r1
        L_0x002a:
            java.io.FileOutputStream r0 = new java.io.FileOutputStream
            r0.<init>(r4)
            r0.write(r5)     // Catch:{ Throwable -> 0x0038 }
            $closeResource(r1, r0)
        L_0x0035:
            return
        L_0x0036:
            r2 = move-exception
            goto L_0x003a
        L_0x0038:
            r1 = move-exception
            throw r1     // Catch:{ all -> 0x0036 }
        L_0x003a:
            $closeResource(r1, r0)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: android.os.FileUtils.bytesToFile(java.lang.String, byte[]):void");
    }

    @UnsupportedAppUsage
    public static void stringToFile(String filename, String string) throws IOException {
        bytesToFile(filename, string.getBytes(StandardCharsets.UTF_8));
    }

    @Deprecated
    @UnsupportedAppUsage
    public static long checksumCrc32(File file) throws FileNotFoundException, IOException {
        CRC32 checkSummer = new CRC32();
        CheckedInputStream cis = null;
        try {
            CheckedInputStream cis2 = new CheckedInputStream(new FileInputStream(file), checkSummer);
            while (cis2.read(new byte[128]) >= 0) {
            }
            long value = checkSummer.getValue();
            try {
                cis2.close();
            } catch (IOException e) {
            }
            return value;
        } catch (Throwable th) {
            if (cis != null) {
                try {
                    cis.close();
                } catch (IOException e2) {
                }
            }
            throw th;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0015, code lost:
        throw r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:5:0x000e, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0012, code lost:
        $closeResource(r1, r0);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static byte[] digest(java.io.File r3, java.lang.String r4) throws java.io.IOException, java.security.NoSuchAlgorithmException {
        /*
            java.io.FileInputStream r0 = new java.io.FileInputStream
            r0.<init>(r3)
            r1 = 0
            byte[] r2 = digest((java.io.InputStream) r0, (java.lang.String) r4)     // Catch:{ Throwable -> 0x0010 }
            $closeResource(r1, r0)
            return r2
        L_0x000e:
            r2 = move-exception
            goto L_0x0012
        L_0x0010:
            r1 = move-exception
            throw r1     // Catch:{ all -> 0x000e }
        L_0x0012:
            $closeResource(r1, r0)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: android.os.FileUtils.digest(java.io.File, java.lang.String):byte[]");
    }

    public static byte[] digest(InputStream in, String algorithm) throws IOException, NoSuchAlgorithmException {
        return digestInternalUserspace(in, algorithm);
    }

    public static byte[] digest(FileDescriptor fd, String algorithm) throws IOException, NoSuchAlgorithmException {
        return digestInternalUserspace(new FileInputStream(fd), algorithm);
    }

    private static byte[] digestInternalUserspace(InputStream in, String algorithm) throws IOException, NoSuchAlgorithmException {
        Throwable th;
        MessageDigest digest = MessageDigest.getInstance(algorithm);
        DigestInputStream digestStream = new DigestInputStream(in, digest);
        try {
            do {
            } while (digestStream.read(new byte[8192]) != -1);
            $closeResource((Throwable) null, digestStream);
            return digest.digest();
        } catch (Throwable th2) {
            $closeResource(th, digestStream);
            throw th2;
        }
    }

    @UnsupportedAppUsage
    public static boolean deleteOlderFiles(File dir, int minCount, long minAgeMs) {
        if (minCount < 0 || minAgeMs < 0) {
            throw new IllegalArgumentException("Constraints must be positive or 0");
        }
        File[] files = dir.listFiles();
        if (files == null) {
            return false;
        }
        Arrays.sort(files, new Comparator<File>() {
            public int compare(File lhs, File rhs) {
                return Long.compare(rhs.lastModified(), lhs.lastModified());
            }
        });
        boolean deleted = false;
        for (int i = minCount; i < files.length; i++) {
            File file = files[i];
            if (System.currentTimeMillis() - file.lastModified() > minAgeMs && file.delete()) {
                Log.d(TAG, "Deleted old file " + file);
                deleted = true;
            }
        }
        return deleted;
    }

    public static boolean contains(File[] dirs, File file) {
        for (File dir : dirs) {
            if (contains(dir, file)) {
                return true;
            }
        }
        return false;
    }

    public static boolean contains(Collection<File> dirs, File file) {
        for (File dir : dirs) {
            if (contains(dir, file)) {
                return true;
            }
        }
        return false;
    }

    public static boolean contains(File dir, File file) {
        if (dir == null || file == null) {
            return false;
        }
        return contains(dir.getAbsolutePath(), file.getAbsolutePath());
    }

    public static boolean contains(String dirPath, String filePath) {
        if (dirPath.equals(filePath)) {
            return true;
        }
        if (!dirPath.endsWith("/")) {
            dirPath = dirPath + "/";
        }
        return filePath.startsWith(dirPath);
    }

    public static boolean deleteContentsAndDir(File dir) {
        if (deleteContents(dir)) {
            return dir.delete();
        }
        return false;
    }

    @UnsupportedAppUsage
    public static boolean deleteContents(File dir) {
        File[] files = dir.listFiles();
        boolean success = true;
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    success &= deleteContents(file);
                }
                if (!file.delete()) {
                    Log.w(TAG, "Failed to delete " + file);
                    success = false;
                }
            }
        }
        return success;
    }

    private static boolean isValidExtFilenameChar(char c) {
        if (c == 0 || c == '/') {
            return false;
        }
        return true;
    }

    public static boolean isValidExtFilename(String name) {
        return name != null && name.equals(buildValidExtFilename(name));
    }

    public static String buildValidExtFilename(String name) {
        if (TextUtils.isEmpty(name) || ".".equals(name) || "..".equals(name)) {
            return "(invalid)";
        }
        StringBuilder res = new StringBuilder(name.length());
        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            if (isValidExtFilenameChar(c)) {
                res.append(c);
            } else {
                res.append('_');
            }
        }
        trimFilename(res, 255);
        return res.toString();
    }

    private static boolean isValidFatFilenameChar(char c) {
        if (!((c >= 0 && c <= 31) || c == '\"' || c == '*' || c == '/' || c == ':' || c == '<' || c == '\\' || c == '|' || c == 127)) {
            switch (c) {
                case '>':
                case '?':
                    break;
                default:
                    return true;
            }
        }
        return false;
    }

    public static boolean isValidFatFilename(String name) {
        return name != null && name.equals(buildValidFatFilename(name));
    }

    public static String buildValidFatFilename(String name) {
        if (TextUtils.isEmpty(name) || ".".equals(name) || "..".equals(name)) {
            return "(invalid)";
        }
        StringBuilder res = new StringBuilder(name.length());
        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            if (isValidFatFilenameChar(c)) {
                res.append(c);
            } else {
                res.append('_');
            }
        }
        trimFilename(res, 255);
        return res.toString();
    }

    @VisibleForTesting
    public static String trimFilename(String str, int maxBytes) {
        StringBuilder res = new StringBuilder(str);
        trimFilename(res, maxBytes);
        return res.toString();
    }

    private static void trimFilename(StringBuilder res, int maxBytes) {
        byte[] raw = res.toString().getBytes(StandardCharsets.UTF_8);
        if (raw.length > maxBytes) {
            int maxBytes2 = maxBytes - 3;
            while (raw.length > maxBytes2) {
                res.deleteCharAt(res.length() / 2);
                raw = res.toString().getBytes(StandardCharsets.UTF_8);
            }
            res.insert(res.length() / 2, Session.TRUNCATE_STRING);
        }
    }

    public static String rewriteAfterRename(File beforeDir, File afterDir, String path) {
        File result;
        if (path == null || (result = rewriteAfterRename(beforeDir, afterDir, new File(path))) == null) {
            return null;
        }
        return result.getAbsolutePath();
    }

    public static String[] rewriteAfterRename(File beforeDir, File afterDir, String[] paths) {
        if (paths == null) {
            return null;
        }
        String[] result = new String[paths.length];
        for (int i = 0; i < paths.length; i++) {
            result[i] = rewriteAfterRename(beforeDir, afterDir, paths[i]);
        }
        return result;
    }

    public static File rewriteAfterRename(File beforeDir, File afterDir, File file) {
        if (file == null || beforeDir == null || afterDir == null || !contains(beforeDir, file)) {
            return null;
        }
        return new File(afterDir, file.getAbsolutePath().substring(beforeDir.getAbsolutePath().length()));
    }

    private static File buildUniqueFileWithExtension(File parent, String name, String ext) throws FileNotFoundException {
        File file = buildFile(parent, name, ext);
        int n = 0;
        while (file.exists()) {
            int n2 = n + 1;
            if (n < 32) {
                file = buildFile(parent, name + " (" + n2 + ")", ext);
                n = n2;
            } else {
                throw new FileNotFoundException("Failed to create unique file");
            }
        }
        return file;
    }

    public static File buildUniqueFile(File parent, String mimeType, String displayName) throws FileNotFoundException {
        String[] parts = splitFileName(mimeType, displayName);
        return buildUniqueFileWithExtension(parent, parts[0], parts[1]);
    }

    public static File buildNonUniqueFile(File parent, String mimeType, String displayName) {
        String[] parts = splitFileName(mimeType, displayName);
        return buildFile(parent, parts[0], parts[1]);
    }

    public static File buildUniqueFile(File parent, String displayName) throws FileNotFoundException {
        String ext;
        String name;
        int lastDot = displayName.lastIndexOf(46);
        if (lastDot >= 0) {
            name = displayName.substring(0, lastDot);
            ext = displayName.substring(lastDot + 1);
        } else {
            name = displayName;
            ext = null;
        }
        return buildUniqueFileWithExtension(parent, name, ext);
    }

    public static String[] splitFileName(String mimeType, String displayName) {
        String name;
        String ext;
        String name2;
        String ext2;
        String mimeTypeFromExt;
        String extFromMimeType;
        if (DocumentsContract.Document.MIME_TYPE_DIR.equals(mimeType)) {
            ext = null;
            name = displayName;
        } else {
            int lastDot = displayName.lastIndexOf(46);
            if (lastDot >= 0) {
                name2 = displayName.substring(0, lastDot);
                ext2 = displayName.substring(lastDot + 1);
                mimeTypeFromExt = MimeTypeMap.getSingleton().getMimeTypeFromExtension(ext2.toLowerCase());
            } else {
                name2 = displayName;
                ext2 = null;
                mimeTypeFromExt = null;
            }
            String str = ext2;
            name = name2;
            ext = str;
            if (mimeTypeFromExt == null) {
                mimeTypeFromExt = ContentResolver.MIME_TYPE_DEFAULT;
            }
            if (ContentResolver.MIME_TYPE_DEFAULT.equals(mimeType)) {
                extFromMimeType = null;
            } else {
                extFromMimeType = MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType);
            }
            if (!Objects.equals(mimeType, mimeTypeFromExt) && !Objects.equals(ext, extFromMimeType)) {
                name = displayName;
                ext = extFromMimeType;
            }
        }
        if (ext == null) {
            ext = "";
        }
        return new String[]{name, ext};
    }

    private static File buildFile(File parent, String name, String ext) {
        if (TextUtils.isEmpty(ext)) {
            return new File(parent, name);
        }
        return new File(parent, name + "." + ext);
    }

    public static String[] listOrEmpty(File dir) {
        if (dir != null) {
            return ArrayUtils.defeatNullable(dir.list());
        }
        return EmptyArray.STRING;
    }

    public static File[] listFilesOrEmpty(File dir) {
        if (dir != null) {
            return ArrayUtils.defeatNullable(dir.listFiles());
        }
        return ArrayUtils.EMPTY_FILE;
    }

    public static File[] listFilesOrEmpty(File dir, FilenameFilter filter) {
        if (dir != null) {
            return ArrayUtils.defeatNullable(dir.listFiles(filter));
        }
        return ArrayUtils.EMPTY_FILE;
    }

    public static File newFileOrNull(String path) {
        if (path != null) {
            return new File(path);
        }
        return null;
    }

    public static File createDir(File baseDir, String name) {
        File dir = new File(baseDir, name);
        if (createDir(dir)) {
            return dir;
        }
        return null;
    }

    public static boolean createDir(File dir) {
        if (dir.exists()) {
            return dir.isDirectory();
        }
        return dir.mkdir();
    }

    public static long roundStorageSize(long size) {
        long val = 1;
        long pow = 1;
        while (val * pow < size) {
            val <<= 1;
            if (val > 512) {
                val = 1;
                pow *= 1000;
            }
        }
        return val * pow;
    }

    public static void closeQuietly(AutoCloseable closeable) {
        IoUtils.closeQuietly(closeable);
    }

    public static void closeQuietly(FileDescriptor fd) {
        IoUtils.closeQuietly(fd);
    }

    public static int translateModeStringToPosix(String mode) {
        int res;
        int i = 0;
        while (i < mode.length()) {
            char charAt = mode.charAt(i);
            if (charAt == 'a' || charAt == 'r' || charAt == 't' || charAt == 'w') {
                i++;
            } else {
                throw new IllegalArgumentException("Bad mode: " + mode);
            }
        }
        if (mode.startsWith("rw")) {
            res = OsConstants.O_RDWR | OsConstants.O_CREAT;
        } else if (mode.startsWith("w")) {
            res = OsConstants.O_WRONLY | OsConstants.O_CREAT;
        } else if (mode.startsWith("r")) {
            res = OsConstants.O_RDONLY;
        } else {
            throw new IllegalArgumentException("Bad mode: " + mode);
        }
        if (mode.indexOf(116) != -1) {
            res |= OsConstants.O_TRUNC;
        }
        if (mode.indexOf(97) != -1) {
            return res | OsConstants.O_APPEND;
        }
        return res;
    }

    public static String translateModePosixToString(int mode) {
        String res;
        if ((OsConstants.O_ACCMODE & mode) == OsConstants.O_RDWR) {
            res = "rw";
        } else if ((OsConstants.O_ACCMODE & mode) == OsConstants.O_WRONLY) {
            res = "w";
        } else if ((OsConstants.O_ACCMODE & mode) == OsConstants.O_RDONLY) {
            res = "r";
        } else {
            throw new IllegalArgumentException("Bad mode: " + mode);
        }
        if ((OsConstants.O_TRUNC & mode) == OsConstants.O_TRUNC) {
            res = res + IncidentManager.URI_PARAM_TIMESTAMP;
        }
        if ((OsConstants.O_APPEND & mode) != OsConstants.O_APPEND) {
            return res;
        }
        return res + FullBackup.APK_TREE_TOKEN;
    }

    public static int translateModePosixToPfd(int mode) {
        int res;
        if ((OsConstants.O_ACCMODE & mode) == OsConstants.O_RDWR) {
            res = 805306368;
        } else if ((OsConstants.O_ACCMODE & mode) == OsConstants.O_WRONLY) {
            res = 536870912;
        } else if ((OsConstants.O_ACCMODE & mode) == OsConstants.O_RDONLY) {
            res = 268435456;
        } else {
            throw new IllegalArgumentException("Bad mode: " + mode);
        }
        if ((OsConstants.O_CREAT & mode) == OsConstants.O_CREAT) {
            res |= 134217728;
        }
        if ((OsConstants.O_TRUNC & mode) == OsConstants.O_TRUNC) {
            res |= 67108864;
        }
        if ((OsConstants.O_APPEND & mode) == OsConstants.O_APPEND) {
            return res | 33554432;
        }
        return res;
    }

    public static int translateModePfdToPosix(int mode) {
        int res;
        if ((mode & 805306368) == 805306368) {
            res = OsConstants.O_RDWR;
        } else if ((mode & 536870912) == 536870912) {
            res = OsConstants.O_WRONLY;
        } else if ((mode & 268435456) == 268435456) {
            res = OsConstants.O_RDONLY;
        } else {
            throw new IllegalArgumentException("Bad mode: " + mode);
        }
        if ((mode & 134217728) == 134217728) {
            res |= OsConstants.O_CREAT;
        }
        if ((mode & 67108864) == 67108864) {
            res |= OsConstants.O_TRUNC;
        }
        if ((mode & 33554432) == 33554432) {
            return res | OsConstants.O_APPEND;
        }
        return res;
    }

    public static int translateModeAccessToPosix(int mode) {
        if (mode == OsConstants.F_OK) {
            return OsConstants.O_RDONLY;
        }
        if (((OsConstants.R_OK | OsConstants.W_OK) & mode) == (OsConstants.R_OK | OsConstants.W_OK)) {
            return OsConstants.O_RDWR;
        }
        if ((OsConstants.R_OK & mode) == OsConstants.R_OK) {
            return OsConstants.O_RDONLY;
        }
        if ((OsConstants.W_OK & mode) == OsConstants.W_OK) {
            return OsConstants.O_WRONLY;
        }
        throw new IllegalArgumentException("Bad mode: " + mode);
    }

    @VisibleForTesting
    public static class MemoryPipe extends Thread implements AutoCloseable {
        private final byte[] data;
        private final FileDescriptor[] pipe;
        private final boolean sink;

        private MemoryPipe(byte[] data2, boolean sink2) throws IOException {
            try {
                this.pipe = Os.pipe();
                this.data = data2;
                this.sink = sink2;
            } catch (ErrnoException e) {
                throw e.rethrowAsIOException();
            }
        }

        private MemoryPipe startInternal() {
            super.start();
            return this;
        }

        public static MemoryPipe createSource(byte[] data2) throws IOException {
            return new MemoryPipe(data2, false).startInternal();
        }

        public static MemoryPipe createSink(byte[] data2) throws IOException {
            return new MemoryPipe(data2, true).startInternal();
        }

        public FileDescriptor getFD() {
            return this.sink ? this.pipe[1] : this.pipe[0];
        }

        public FileDescriptor getInternalFD() {
            return this.sink ? this.pipe[0] : this.pipe[1];
        }

        /* JADX WARNING: Code restructure failed: missing block: B:11:0x002a, code lost:
            if (r6.sink != 0) goto L_0x0044;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:20:0x0042, code lost:
            if (r6.sink == false) goto L_0x004d;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:21:0x0044, code lost:
            android.os.SystemClock.sleep(java.util.concurrent.TimeUnit.SECONDS.toMillis(1));
         */
        /* JADX WARNING: Code restructure failed: missing block: B:22:0x004d, code lost:
            libcore.io.IoUtils.closeQuietly(r0);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:23:0x0051, code lost:
            return;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run() {
            /*
                r6 = this;
                java.io.FileDescriptor r0 = r6.getInternalFD()
                r1 = 0
            L_0x0005:
                r2 = 1
                byte[] r4 = r6.data     // Catch:{ ErrnoException | IOException -> 0x003f, all -> 0x002d }
                int r4 = r4.length     // Catch:{ ErrnoException | IOException -> 0x003f, all -> 0x002d }
                if (r1 >= r4) goto L_0x0028
                boolean r4 = r6.sink     // Catch:{ ErrnoException | IOException -> 0x003f, all -> 0x002d }
                if (r4 == 0) goto L_0x001c
                byte[] r4 = r6.data     // Catch:{ ErrnoException | IOException -> 0x003f, all -> 0x002d }
                byte[] r5 = r6.data     // Catch:{ ErrnoException | IOException -> 0x003f, all -> 0x002d }
                int r5 = r5.length     // Catch:{ ErrnoException | IOException -> 0x003f, all -> 0x002d }
                int r5 = r5 - r1
                int r4 = android.system.Os.read(r0, r4, r1, r5)     // Catch:{ ErrnoException | IOException -> 0x003f, all -> 0x002d }
                int r1 = r1 + r4
                goto L_0x0005
            L_0x001c:
                byte[] r4 = r6.data     // Catch:{ ErrnoException | IOException -> 0x003f, all -> 0x002d }
                byte[] r5 = r6.data     // Catch:{ ErrnoException | IOException -> 0x003f, all -> 0x002d }
                int r5 = r5.length     // Catch:{ ErrnoException | IOException -> 0x003f, all -> 0x002d }
                int r5 = r5 - r1
                int r4 = android.system.Os.write(r0, r4, r1, r5)     // Catch:{ ErrnoException | IOException -> 0x003f, all -> 0x002d }
                int r1 = r1 + r4
                goto L_0x0005
            L_0x0028:
                boolean r1 = r6.sink
                if (r1 == 0) goto L_0x004d
                goto L_0x0044
            L_0x002d:
                r1 = move-exception
                boolean r4 = r6.sink
                if (r4 == 0) goto L_0x003b
                java.util.concurrent.TimeUnit r4 = java.util.concurrent.TimeUnit.SECONDS
                long r2 = r4.toMillis(r2)
                android.os.SystemClock.sleep(r2)
            L_0x003b:
                libcore.io.IoUtils.closeQuietly(r0)
                throw r1
            L_0x003f:
                r1 = move-exception
                boolean r1 = r6.sink
                if (r1 == 0) goto L_0x004d
            L_0x0044:
                java.util.concurrent.TimeUnit r1 = java.util.concurrent.TimeUnit.SECONDS
                long r1 = r1.toMillis(r2)
                android.os.SystemClock.sleep(r1)
            L_0x004d:
                libcore.io.IoUtils.closeQuietly(r0)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: android.os.FileUtils.MemoryPipe.run():void");
        }

        public void close() throws Exception {
            IoUtils.closeQuietly(getFD());
        }
    }
}
