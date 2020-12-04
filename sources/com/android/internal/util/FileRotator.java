package com.android.internal.util;

import android.os.FileUtils;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import libcore.io.IoUtils;

public class FileRotator {
    private static final boolean LOGD = false;
    private static final String SUFFIX_BACKUP = ".backup";
    private static final String SUFFIX_NO_BACKUP = ".no_backup";
    private static final String TAG = "FileRotator";
    private final File mBasePath;
    private final long mDeleteAgeMillis;
    private final String mPrefix;
    private final long mRotateAgeMillis;

    public interface Reader {
        void read(InputStream inputStream) throws IOException;
    }

    public interface Rewriter extends Reader, Writer {
        void reset();

        boolean shouldWrite();
    }

    public interface Writer {
        void write(OutputStream outputStream) throws IOException;
    }

    public FileRotator(File basePath, String prefix, long rotateAgeMillis, long deleteAgeMillis) {
        this.mBasePath = (File) Preconditions.checkNotNull(basePath);
        this.mPrefix = (String) Preconditions.checkNotNull(prefix);
        this.mRotateAgeMillis = rotateAgeMillis;
        this.mDeleteAgeMillis = deleteAgeMillis;
        this.mBasePath.mkdirs();
        for (String name : this.mBasePath.list()) {
            if (name.startsWith(this.mPrefix)) {
                if (name.endsWith(SUFFIX_BACKUP)) {
                    new File(this.mBasePath, name).renameTo(new File(this.mBasePath, name.substring(0, name.length() - SUFFIX_BACKUP.length())));
                } else if (name.endsWith(SUFFIX_NO_BACKUP)) {
                    File noBackupFile = new File(this.mBasePath, name);
                    File file = new File(this.mBasePath, name.substring(0, name.length() - SUFFIX_NO_BACKUP.length()));
                    noBackupFile.delete();
                    file.delete();
                }
            }
        }
    }

    public void deleteAll() {
        FileInfo info = new FileInfo(this.mPrefix);
        for (String name : this.mBasePath.list()) {
            if (info.parse(name)) {
                new File(this.mBasePath, name).delete();
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x003d, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0041, code lost:
        throw r2;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void dumpAll(java.io.OutputStream r10) throws java.io.IOException {
        /*
            r9 = this;
            java.util.zip.ZipOutputStream r0 = new java.util.zip.ZipOutputStream
            r0.<init>(r10)
            com.android.internal.util.FileRotator$FileInfo r1 = new com.android.internal.util.FileRotator$FileInfo     // Catch:{ all -> 0x004a }
            java.lang.String r2 = r9.mPrefix     // Catch:{ all -> 0x004a }
            r1.<init>(r2)     // Catch:{ all -> 0x004a }
            java.io.File r2 = r9.mBasePath     // Catch:{ all -> 0x004a }
            java.lang.String[] r2 = r2.list()     // Catch:{ all -> 0x004a }
            int r3 = r2.length     // Catch:{ all -> 0x004a }
            r4 = 0
        L_0x0014:
            if (r4 >= r3) goto L_0x0045
            r5 = r2[r4]     // Catch:{ all -> 0x004a }
            boolean r6 = r1.parse(r5)     // Catch:{ all -> 0x004a }
            if (r6 == 0) goto L_0x0042
            java.util.zip.ZipEntry r6 = new java.util.zip.ZipEntry     // Catch:{ all -> 0x004a }
            r6.<init>(r5)     // Catch:{ all -> 0x004a }
            r0.putNextEntry(r6)     // Catch:{ all -> 0x004a }
            java.io.File r7 = new java.io.File     // Catch:{ all -> 0x004a }
            java.io.File r8 = r9.mBasePath     // Catch:{ all -> 0x004a }
            r7.<init>(r8, r5)     // Catch:{ all -> 0x004a }
            java.io.FileInputStream r8 = new java.io.FileInputStream     // Catch:{ all -> 0x004a }
            r8.<init>(r7)     // Catch:{ all -> 0x004a }
            android.os.FileUtils.copy((java.io.InputStream) r8, (java.io.OutputStream) r0)     // Catch:{ all -> 0x003d }
            libcore.io.IoUtils.closeQuietly(r8)     // Catch:{ all -> 0x004a }
            r0.closeEntry()     // Catch:{ all -> 0x004a }
            goto L_0x0042
        L_0x003d:
            r2 = move-exception
            libcore.io.IoUtils.closeQuietly(r8)     // Catch:{ all -> 0x004a }
            throw r2     // Catch:{ all -> 0x004a }
        L_0x0042:
            int r4 = r4 + 1
            goto L_0x0014
        L_0x0045:
            libcore.io.IoUtils.closeQuietly(r0)
            return
        L_0x004a:
            r1 = move-exception
            libcore.io.IoUtils.closeQuietly(r0)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.util.FileRotator.dumpAll(java.io.OutputStream):void");
    }

    public void rewriteActive(Rewriter rewriter, long currentTimeMillis) throws IOException {
        rewriteSingle(rewriter, getActiveName(currentTimeMillis));
    }

    @Deprecated
    public void combineActive(final Reader reader, final Writer writer, long currentTimeMillis) throws IOException {
        rewriteActive(new Rewriter() {
            public void reset() {
            }

            public void read(InputStream in) throws IOException {
                reader.read(in);
            }

            public boolean shouldWrite() {
                return true;
            }

            public void write(OutputStream out) throws IOException {
                writer.write(out);
            }
        }, currentTimeMillis);
    }

    public void rewriteAll(Rewriter rewriter) throws IOException {
        FileInfo info = new FileInfo(this.mPrefix);
        for (String name : this.mBasePath.list()) {
            if (info.parse(name)) {
                rewriteSingle(rewriter, name);
            }
        }
    }

    private void rewriteSingle(Rewriter rewriter, String name) throws IOException {
        File file = new File(this.mBasePath, name);
        rewriter.reset();
        if (file.exists()) {
            readFile(file, rewriter);
            if (rewriter.shouldWrite()) {
                File file2 = this.mBasePath;
                File backupFile = new File(file2, name + SUFFIX_BACKUP);
                file.renameTo(backupFile);
                try {
                    writeFile(file, rewriter);
                    backupFile.delete();
                } catch (Throwable t) {
                    file.delete();
                    backupFile.renameTo(file);
                    throw rethrowAsIoException(t);
                }
            }
        } else {
            File file3 = this.mBasePath;
            File backupFile2 = new File(file3, name + SUFFIX_NO_BACKUP);
            backupFile2.createNewFile();
            try {
                writeFile(file, rewriter);
                backupFile2.delete();
            } catch (Throwable t2) {
                file.delete();
                backupFile2.delete();
                throw rethrowAsIoException(t2);
            }
        }
    }

    public void readMatching(Reader reader, long matchStartMillis, long matchEndMillis) throws IOException {
        FileInfo info = new FileInfo(this.mPrefix);
        for (String name : this.mBasePath.list()) {
            if (info.parse(name) && info.startMillis <= matchEndMillis && matchStartMillis <= info.endMillis) {
                readFile(new File(this.mBasePath, name), reader);
            }
        }
    }

    private String getActiveName(long currentTimeMillis) {
        String oldestActiveName = null;
        long oldestActiveStart = Long.MAX_VALUE;
        FileInfo info = new FileInfo(this.mPrefix);
        for (String name : this.mBasePath.list()) {
            if (info.parse(name) && info.isActive() && info.startMillis < currentTimeMillis && info.startMillis < oldestActiveStart) {
                oldestActiveName = name;
                oldestActiveStart = info.startMillis;
            }
        }
        if (oldestActiveName != null) {
            return oldestActiveName;
        }
        info.startMillis = currentTimeMillis;
        info.endMillis = Long.MAX_VALUE;
        return info.build();
    }

    public void maybeRotate(long currentTimeMillis) {
        long rotateBefore = currentTimeMillis - this.mRotateAgeMillis;
        long deleteBefore = currentTimeMillis - this.mDeleteAgeMillis;
        FileInfo info = new FileInfo(this.mPrefix);
        String[] baseFiles = this.mBasePath.list();
        if (baseFiles != null) {
            for (String name : baseFiles) {
                if (info.parse(name)) {
                    if (info.isActive()) {
                        if (info.startMillis <= rotateBefore) {
                            info.endMillis = currentTimeMillis;
                            new File(this.mBasePath, name).renameTo(new File(this.mBasePath, info.build()));
                        }
                    } else if (info.endMillis <= deleteBefore) {
                        new File(this.mBasePath, name).delete();
                    }
                }
            }
        }
    }

    private static void readFile(File file, Reader reader) throws IOException {
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
        try {
            reader.read(bis);
        } finally {
            IoUtils.closeQuietly(bis);
        }
    }

    private static void writeFile(File file, Writer writer) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        try {
            writer.write(bos);
            bos.flush();
        } finally {
            FileUtils.sync(fos);
            IoUtils.closeQuietly(bos);
        }
    }

    private static IOException rethrowAsIoException(Throwable t) throws IOException {
        if (t instanceof IOException) {
            throw ((IOException) t);
        }
        throw new IOException(t.getMessage(), t);
    }

    private static class FileInfo {
        public long endMillis;
        public final String prefix;
        public long startMillis;

        public FileInfo(String prefix2) {
            this.prefix = (String) Preconditions.checkNotNull(prefix2);
        }

        public boolean parse(String name) {
            this.endMillis = -1;
            this.startMillis = -1;
            int dotIndex = name.lastIndexOf(46);
            int dashIndex = name.lastIndexOf(45);
            if (dotIndex == -1 || dashIndex == -1 || !this.prefix.equals(name.substring(0, dotIndex))) {
                return false;
            }
            try {
                this.startMillis = Long.parseLong(name.substring(dotIndex + 1, dashIndex));
                if (name.length() - dashIndex == 1) {
                    this.endMillis = Long.MAX_VALUE;
                } else {
                    this.endMillis = Long.parseLong(name.substring(dashIndex + 1));
                }
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }

        public String build() {
            StringBuilder name = new StringBuilder();
            name.append(this.prefix);
            name.append('.');
            name.append(this.startMillis);
            name.append('-');
            if (this.endMillis != Long.MAX_VALUE) {
                name.append(this.endMillis);
            }
            return name.toString();
        }

        public boolean isActive() {
            return this.endMillis == Long.MAX_VALUE;
        }
    }
}
