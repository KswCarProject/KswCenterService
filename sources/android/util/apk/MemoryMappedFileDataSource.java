package android.util.apk;

import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import java.io.FileDescriptor;
import java.io.IOException;
import java.nio.DirectByteBuffer;
import java.security.DigestException;

/* loaded from: classes4.dex */
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

    @Override // android.util.apk.DataSource
    public long size() {
        return this.mSize;
    }

    /* JADX WARN: Removed duplicated region for block: B:57:0x00c8 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    @Override // android.util.apk.DataSource
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void feedIntoDataDigester(DataDigester md, long offset, int size) throws IOException, DigestException {
        long mmapRegionSize;
        ErrnoException errnoException;
        long mmapPtr;
        long filePosition = this.mFilePosition + offset;
        long mmapFilePosition = (filePosition / MEMORY_PAGE_SIZE_BYTES) * MEMORY_PAGE_SIZE_BYTES;
        int dataStartOffsetInMmapRegion = (int) (filePosition - mmapFilePosition);
        long mmapRegionSize2 = size + dataStartOffsetInMmapRegion;
        long mmapPtr2 = 0;
        try {
            try {
                long mmapPtr3 = Os.mmap(0L, mmapRegionSize2, OsConstants.PROT_READ, OsConstants.MAP_SHARED | OsConstants.MAP_POPULATE, this.mFd, mmapFilePosition);
                try {
                    try {
                    } catch (ErrnoException e) {
                        e = e;
                    } catch (Throwable th) {
                        th = th;
                    }
                    try {
                        md.consume(new DirectByteBuffer(size, mmapPtr3 + dataStartOffsetInMmapRegion, this.mFd, (Runnable) null, true));
                        if (mmapPtr3 != 0) {
                            try {
                                Os.munmap(mmapPtr3, mmapRegionSize2);
                            } catch (ErrnoException e2) {
                            }
                        }
                    } catch (ErrnoException e3) {
                        e = e3;
                        mmapRegionSize = mmapRegionSize2;
                        mmapPtr2 = mmapPtr3;
                        try {
                            throw new IOException("Failed to mmap " + mmapRegionSize + " bytes", e);
                        } catch (Throwable e4) {
                            errnoException = e4;
                            mmapPtr = mmapPtr2;
                            if (mmapPtr != 0) {
                                try {
                                    Os.munmap(mmapPtr, mmapRegionSize);
                                } catch (ErrnoException e5) {
                                }
                            }
                            throw errnoException;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        mmapRegionSize = mmapRegionSize2;
                        mmapPtr = mmapPtr3;
                        errnoException = th;
                        if (mmapPtr != 0) {
                        }
                        throw errnoException;
                    }
                } catch (ErrnoException e6) {
                    e = e6;
                    mmapRegionSize = mmapRegionSize2;
                    mmapPtr2 = mmapPtr3;
                } catch (Throwable th3) {
                    mmapRegionSize = mmapRegionSize2;
                    mmapPtr = mmapPtr3;
                    errnoException = th3;
                }
            } catch (ErrnoException e7) {
                e = e7;
                mmapRegionSize = mmapRegionSize2;
            } catch (Throwable th4) {
                mmapRegionSize = mmapRegionSize2;
                errnoException = th4;
                mmapPtr = 0;
            }
        } catch (ErrnoException e8) {
            e = e8;
            mmapRegionSize = mmapRegionSize2;
        } catch (Throwable th5) {
            mmapRegionSize = mmapRegionSize2;
            errnoException = th5;
            mmapPtr = 0;
        }
    }
}
