package android.os;

import android.content.Context;
import android.os.storage.StorageManager;
import android.system.ErrnoException;
import android.system.Os;
import android.util.Slog;
import com.android.internal.annotations.VisibleForTesting;
import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.Arrays;
import libcore.io.IoUtils;
import libcore.util.EmptyArray;

public class RedactingFileDescriptor {
    private static final boolean DEBUG = true;
    private static final String TAG = "RedactingFileDescriptor";
    private final ProxyFileDescriptorCallback mCallback = new ProxyFileDescriptorCallback() {
        public long onGetSize() throws ErrnoException {
            return Os.fstat(RedactingFileDescriptor.this.mInner).st_size;
        }

        public int onRead(long offset, int size, byte[] data) throws ErrnoException {
            int n;
            AnonymousClass1 r1 = this;
            long j = offset;
            int i = size;
            byte b = 0;
            int res = 0;
            while (true) {
                n = res;
                if (n >= i) {
                    break;
                }
                try {
                    int res2 = Os.pread(RedactingFileDescriptor.this.mInner, data, n, i - n, j + ((long) n));
                    if (res2 == 0) {
                        break;
                    }
                    res = res2 + n;
                } catch (InterruptedIOException e) {
                    res = n + e.bytesTransferred;
                }
            }
            long[] ranges = RedactingFileDescriptor.this.mRedactRanges;
            int i2 = 0;
            while (i2 < ranges.length) {
                long start = Math.max(j, ranges[i2]);
                long end = Math.min(((long) i) + j, ranges[i2 + 1]);
                long j2 = start;
                while (j2 < end) {
                    data[(int) (j2 - j)] = b;
                    j2++;
                    i2 = i2;
                    int i3 = size;
                }
                int i4 = i2;
                long[] access$200 = RedactingFileDescriptor.this.mFreeOffsets;
                int length = access$200.length;
                int i5 = b;
                while (i5 < length) {
                    long freeOffset = access$200[i5];
                    int n2 = n;
                    long redactFreeStart = Math.max(freeOffset, start);
                    long redactFreeEnd = Math.min(freeOffset + 4, end);
                    long j3 = redactFreeStart;
                    while (j3 < redactFreeEnd) {
                        data[(int) (j3 - j)] = (byte) "free".charAt((int) (j3 - freeOffset));
                        j3++;
                        ranges = ranges;
                        j = offset;
                    }
                    i5++;
                    n = n2;
                    j = offset;
                }
                int i6 = n;
                i2 = i4 + 2;
                r1 = this;
                j = offset;
                i = size;
                b = 0;
            }
            return n;
        }

        public int onWrite(long offset, int size, byte[] data) throws ErrnoException {
            int n = 0;
            while (n < size) {
                try {
                    byte[] bArr = data;
                    int i = n;
                    int res = Os.pwrite(RedactingFileDescriptor.this.mInner, bArr, i, size - n, offset + ((long) n));
                    if (res == 0) {
                        break;
                    }
                    n += res;
                } catch (InterruptedIOException e) {
                    n += e.bytesTransferred;
                }
            }
            long[] unused = RedactingFileDescriptor.this.mRedactRanges = RedactingFileDescriptor.removeRange(RedactingFileDescriptor.this.mRedactRanges, offset, ((long) n) + offset);
            return n;
        }

        public void onFsync() throws ErrnoException {
            Os.fsync(RedactingFileDescriptor.this.mInner);
        }

        public void onRelease() {
            Slog.v(RedactingFileDescriptor.TAG, "onRelease()");
            IoUtils.closeQuietly(RedactingFileDescriptor.this.mInner);
        }
    };
    /* access modifiers changed from: private */
    public volatile long[] mFreeOffsets;
    /* access modifiers changed from: private */
    public FileDescriptor mInner = null;
    private ParcelFileDescriptor mOuter = null;
    /* access modifiers changed from: private */
    public volatile long[] mRedactRanges;

    private RedactingFileDescriptor(Context context, File file, int mode, long[] redactRanges, long[] freeOffsets) throws IOException {
        this.mRedactRanges = checkRangesArgument(redactRanges);
        this.mFreeOffsets = freeOffsets;
        try {
            this.mInner = Os.open(file.getAbsolutePath(), FileUtils.translateModePfdToPosix(mode), 0);
            this.mOuter = ((StorageManager) context.getSystemService(StorageManager.class)).openProxyFileDescriptor(mode, this.mCallback);
        } catch (ErrnoException e) {
            throw e.rethrowAsIOException();
        } catch (IOException e2) {
            IoUtils.closeQuietly(this.mInner);
            IoUtils.closeQuietly(this.mOuter);
            throw e2;
        }
    }

    private static long[] checkRangesArgument(long[] ranges) {
        if (ranges.length % 2 == 0) {
            int i = 0;
            while (i < ranges.length - 1) {
                if (ranges[i] <= ranges[i + 1]) {
                    i += 2;
                } else {
                    throw new IllegalArgumentException();
                }
            }
            return ranges;
        }
        throw new IllegalArgumentException();
    }

    public static ParcelFileDescriptor open(Context context, File file, int mode, long[] redactRanges, long[] freePositions) throws IOException {
        return new RedactingFileDescriptor(context, file, mode, redactRanges, freePositions).mOuter;
    }

    @VisibleForTesting
    public static long[] removeRange(long[] ranges, long start, long end) {
        if (start == end) {
            return ranges;
        }
        if (start <= end) {
            long[] res = EmptyArray.LONG;
            for (int i = 0; i < ranges.length; i += 2) {
                if (start > ranges[i] || end < ranges[i + 1]) {
                    if (start < ranges[i] || end > ranges[i + 1]) {
                        res = Arrays.copyOf(res, res.length + 2);
                        if (end < ranges[i] || end > ranges[i + 1]) {
                            res[res.length - 2] = ranges[i];
                        } else {
                            res[res.length - 2] = Math.max(ranges[i], end);
                        }
                        if (start < ranges[i] || start > ranges[i + 1]) {
                            res[res.length - 1] = ranges[i + 1];
                        } else {
                            res[res.length - 1] = Math.min(ranges[i + 1], start);
                        }
                    } else {
                        res = Arrays.copyOf(res, res.length + 4);
                        res[res.length - 4] = ranges[i];
                        res[res.length - 3] = start;
                        res[res.length - 2] = end;
                        res[res.length - 1] = ranges[i + 1];
                    }
                }
            }
            return res;
        }
        throw new IllegalArgumentException();
    }
}
