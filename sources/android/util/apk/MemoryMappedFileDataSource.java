package android.util.apk;

import android.system.Os;
import android.system.OsConstants;
import java.io.FileDescriptor;

class MemoryMappedFileDataSource implements DataSource {
    private static final long MEMORY_PAGE_SIZE_BYTES = Os.sysconf(OsConstants._SC_PAGESIZE);
    private final FileDescriptor mFd;
    private final long mFilePosition;
    private final long mSize;

    MemoryMappedFileDataSource(FileDescriptor fd, long position, long size) {
        this.mFd = fd;
        this.mFilePosition = position;
        this.mSize = size;
    }

    public long size() {
        return this.mSize;
    }

    /* JADX WARNING: Removed duplicated region for block: B:49:0x00c8 A[SYNTHETIC, Splitter:B:49:0x00c8] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void feedIntoDataDigester(android.util.apk.DataDigester r26, long r27, int r29) throws java.io.IOException, java.security.DigestException {
        /*
            r25 = this;
            r1 = r25
            long r2 = r1.mFilePosition
            long r2 = r2 + r27
            long r4 = MEMORY_PAGE_SIZE_BYTES
            long r4 = r2 / r4
            long r6 = MEMORY_PAGE_SIZE_BYTES
            long r4 = r4 * r6
            long r6 = r2 - r4
            int r6 = (int) r6
            int r0 = r29 + r6
            long r12 = (long) r0
            r17 = 0
            r19 = r17
            r8 = 0
            int r0 = android.system.OsConstants.PROT_READ     // Catch:{ ErrnoException -> 0x009e, all -> 0x0094 }
            int r7 = android.system.OsConstants.MAP_SHARED     // Catch:{ ErrnoException -> 0x009e, all -> 0x0094 }
            int r10 = android.system.OsConstants.MAP_POPULATE     // Catch:{ ErrnoException -> 0x009e, all -> 0x0094 }
            r7 = r7 | r10
            java.io.FileDescriptor r10 = r1.mFd     // Catch:{ ErrnoException -> 0x009e, all -> 0x0094 }
            r14 = r10
            r10 = r12
            r21 = r12
            r12 = r0
            r13 = r7
            r15 = r4
            long r7 = android.system.Os.mmap(r8, r10, r12, r13, r14, r15)     // Catch:{ ErrnoException -> 0x008c, all -> 0x0081 }
            r12 = r7
            java.nio.DirectByteBuffer r0 = new java.nio.DirectByteBuffer     // Catch:{ ErrnoException -> 0x0076, all -> 0x006b }
            long r7 = (long) r6     // Catch:{ ErrnoException -> 0x0076, all -> 0x006b }
            long r9 = r12 + r7
            java.io.FileDescriptor r11 = r1.mFd     // Catch:{ ErrnoException -> 0x0076, all -> 0x006b }
            r14 = 0
            r15 = 1
            r7 = r0
            r8 = r29
            r23 = r2
            r1 = r12
            r12 = r14
            r13 = r15
            r7.<init>(r8, r9, r11, r12, r13)     // Catch:{ ErrnoException -> 0x0063, all -> 0x005a }
            r3 = r26
            r3.consume(r0)     // Catch:{ ErrnoException -> 0x0058, all -> 0x0056 }
            int r0 = (r1 > r17 ? 1 : (r1 == r17 ? 0 : -1))
            if (r0 == 0) goto L_0x0053
            r7 = r21
            android.system.Os.munmap(r1, r7)     // Catch:{ ErrnoException -> 0x0051 }
            goto L_0x0055
        L_0x0051:
            r0 = move-exception
            goto L_0x0055
        L_0x0053:
            r7 = r21
        L_0x0055:
            return
        L_0x0056:
            r0 = move-exception
            goto L_0x005d
        L_0x0058:
            r0 = move-exception
            goto L_0x0066
        L_0x005a:
            r0 = move-exception
            r3 = r26
        L_0x005d:
            r7 = r21
            r9 = r1
            r1 = r0
            goto L_0x00c4
        L_0x0063:
            r0 = move-exception
            r3 = r26
        L_0x0066:
            r7 = r21
            r19 = r1
            goto L_0x00a4
        L_0x006b:
            r0 = move-exception
            r23 = r2
            r1 = r12
            r7 = r21
            r3 = r26
            r9 = r1
            r1 = r0
            goto L_0x00c4
        L_0x0076:
            r0 = move-exception
            r23 = r2
            r1 = r12
            r7 = r21
            r3 = r26
            r19 = r1
            goto L_0x00a4
        L_0x0081:
            r0 = move-exception
            r23 = r2
            r7 = r21
            r3 = r26
            r1 = r0
            r9 = r19
            goto L_0x00c4
        L_0x008c:
            r0 = move-exception
            r23 = r2
            r7 = r21
            r3 = r26
            goto L_0x00a4
        L_0x0094:
            r0 = move-exception
            r23 = r2
            r7 = r12
            r3 = r26
            r1 = r0
            r9 = r19
            goto L_0x00c4
        L_0x009e:
            r0 = move-exception
            r23 = r2
            r7 = r12
            r3 = r26
        L_0x00a4:
            java.io.IOException r1 = new java.io.IOException     // Catch:{ all -> 0x00c0 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x00c0 }
            r2.<init>()     // Catch:{ all -> 0x00c0 }
            java.lang.String r9 = "Failed to mmap "
            r2.append(r9)     // Catch:{ all -> 0x00c0 }
            r2.append(r7)     // Catch:{ all -> 0x00c0 }
            java.lang.String r9 = " bytes"
            r2.append(r9)     // Catch:{ all -> 0x00c0 }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x00c0 }
            r1.<init>(r2, r0)     // Catch:{ all -> 0x00c0 }
            throw r1     // Catch:{ all -> 0x00c0 }
        L_0x00c0:
            r0 = move-exception
            r1 = r0
            r9 = r19
        L_0x00c4:
            int r0 = (r9 > r17 ? 1 : (r9 == r17 ? 0 : -1))
            if (r0 == 0) goto L_0x00cd
            android.system.Os.munmap(r9, r7)     // Catch:{ ErrnoException -> 0x00cc }
            goto L_0x00cd
        L_0x00cc:
            r0 = move-exception
        L_0x00cd:
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.util.apk.MemoryMappedFileDataSource.feedIntoDataDigester(android.util.apk.DataDigester, long, int):void");
    }
}
