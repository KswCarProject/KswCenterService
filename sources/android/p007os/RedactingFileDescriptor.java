package android.p007os;

import android.content.Context;
import android.p007os.storage.StorageManager;
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

/* renamed from: android.os.RedactingFileDescriptor */
/* loaded from: classes3.dex */
public class RedactingFileDescriptor {
    private static final boolean DEBUG = true;
    private static final String TAG = "RedactingFileDescriptor";
    private final ProxyFileDescriptorCallback mCallback = new ProxyFileDescriptorCallback() { // from class: android.os.RedactingFileDescriptor.1
        @Override // android.p007os.ProxyFileDescriptorCallback
        public long onGetSize() throws ErrnoException {
            return Os.fstat(RedactingFileDescriptor.this.mInner).st_size;
        }

        @Override // android.p007os.ProxyFileDescriptorCallback
        public int onRead(long offset, int size, byte[] data) throws ErrnoException {
            int n;
            int res;
            ProxyFileDescriptorCallbackC15151 proxyFileDescriptorCallbackC15151 = this;
            long j = offset;
            int i = size;
            byte b = 0;
            int n2 = 0;
            while (true) {
                n = n2;
                if (n >= i) {
                    break;
                }
                try {
                    res = Os.pread(RedactingFileDescriptor.this.mInner, data, n, i - n, j + n);
                } catch (InterruptedIOException e) {
                    n2 = n + e.bytesTransferred;
                }
                if (res == 0) {
                    break;
                }
                n2 = res + n;
            }
            long[] ranges = RedactingFileDescriptor.this.mRedactRanges;
            int i2 = 0;
            while (i2 < ranges.length) {
                long start = Math.max(j, ranges[i2]);
                long end = Math.min(i + j, ranges[i2 + 1]);
                long j2 = start;
                while (j2 < end) {
                    data[(int) (j2 - j)] = b;
                    j2++;
                    i2 = i2;
                }
                int i3 = i2;
                long[] jArr = RedactingFileDescriptor.this.mFreeOffsets;
                int length = jArr.length;
                int i4 = b;
                while (i4 < length) {
                    long freeOffset = jArr[i4];
                    int n3 = n;
                    long freeEnd = freeOffset + 4;
                    long redactFreeStart = Math.max(freeOffset, start);
                    long redactFreeEnd = Math.min(freeEnd, end);
                    long j3 = redactFreeStart;
                    while (j3 < redactFreeEnd) {
                        data[(int) (j3 - j)] = (byte) "free".charAt((int) (j3 - freeOffset));
                        j3++;
                        ranges = ranges;
                        j = offset;
                    }
                    i4++;
                    n = n3;
                    j = offset;
                }
                i2 = i3 + 2;
                proxyFileDescriptorCallbackC15151 = this;
                j = offset;
                i = size;
                b = 0;
            }
            return n;
        }

        @Override // android.p007os.ProxyFileDescriptorCallback
        public int onWrite(long offset, int size, byte[] data) throws ErrnoException {
            int res;
            int n = 0;
            while (n < size) {
                try {
                    res = Os.pwrite(RedactingFileDescriptor.this.mInner, data, n, size - n, offset + n);
                } catch (InterruptedIOException e) {
                    n += e.bytesTransferred;
                }
                if (res == 0) {
                    break;
                }
                n += res;
            }
            RedactingFileDescriptor.this.mRedactRanges = RedactingFileDescriptor.removeRange(RedactingFileDescriptor.this.mRedactRanges, offset, n + offset);
            return n;
        }

        @Override // android.p007os.ProxyFileDescriptorCallback
        public void onFsync() throws ErrnoException {
            Os.fsync(RedactingFileDescriptor.this.mInner);
        }

        @Override // android.p007os.ProxyFileDescriptorCallback
        public void onRelease() {
            Slog.m52v(RedactingFileDescriptor.TAG, "onRelease()");
            IoUtils.closeQuietly(RedactingFileDescriptor.this.mInner);
        }
    };
    private volatile long[] mFreeOffsets;
    private FileDescriptor mInner;
    private ParcelFileDescriptor mOuter;
    private volatile long[] mRedactRanges;

    private RedactingFileDescriptor(Context context, File file, int mode, long[] redactRanges, long[] freeOffsets) throws IOException {
        this.mInner = null;
        this.mOuter = null;
        this.mRedactRanges = checkRangesArgument(redactRanges);
        this.mFreeOffsets = freeOffsets;
        try {
            try {
                this.mInner = Os.open(file.getAbsolutePath(), FileUtils.translateModePfdToPosix(mode), 0);
                this.mOuter = ((StorageManager) context.getSystemService(StorageManager.class)).openProxyFileDescriptor(mode, this.mCallback);
            } catch (ErrnoException e) {
                throw e.rethrowAsIOException();
            }
        } catch (IOException e2) {
            IoUtils.closeQuietly(this.mInner);
            IoUtils.closeQuietly(this.mOuter);
            throw e2;
        }
    }

    private static long[] checkRangesArgument(long[] ranges) {
        if (ranges.length % 2 != 0) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < ranges.length - 1; i += 2) {
            if (ranges[i] > ranges[i + 1]) {
                throw new IllegalArgumentException();
            }
        }
        return ranges;
    }

    public static ParcelFileDescriptor open(Context context, File file, int mode, long[] redactRanges, long[] freePositions) throws IOException {
        return new RedactingFileDescriptor(context, file, mode, redactRanges, freePositions).mOuter;
    }

    @VisibleForTesting
    public static long[] removeRange(long[] ranges, long start, long end) {
        if (start == end) {
            return ranges;
        }
        if (start > end) {
            throw new IllegalArgumentException();
        }
        long[] res = EmptyArray.LONG;
        for (int i = 0; i < ranges.length; i += 2) {
            if (start > ranges[i] || end < ranges[i + 1]) {
                if (start >= ranges[i] && end <= ranges[i + 1]) {
                    res = Arrays.copyOf(res, res.length + 4);
                    res[res.length - 4] = ranges[i];
                    res[res.length - 3] = start;
                    res[res.length - 2] = end;
                    res[res.length - 1] = ranges[i + 1];
                } else {
                    res = Arrays.copyOf(res, res.length + 2);
                    if (end >= ranges[i] && end <= ranges[i + 1]) {
                        res[res.length - 2] = Math.max(ranges[i], end);
                    } else {
                        res[res.length - 2] = ranges[i];
                    }
                    if (start >= ranges[i] && start <= ranges[i + 1]) {
                        res[res.length - 1] = Math.min(ranges[i + 1], start);
                    } else {
                        res[res.length - 1] = ranges[i + 1];
                    }
                }
            }
        }
        return res;
    }
}
