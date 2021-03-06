package com.android.internal.util;

import android.annotation.UnsupportedAppUsage;
import android.os.Debug;
import android.os.StrictMode;

public final class MemInfoReader {
    final long[] mInfos = new long[15];

    @UnsupportedAppUsage
    public void readMemInfo() {
        StrictMode.ThreadPolicy savedPolicy = StrictMode.allowThreadDiskReads();
        try {
            Debug.getMemInfo(this.mInfos);
        } finally {
            StrictMode.setThreadPolicy(savedPolicy);
        }
    }

    @UnsupportedAppUsage
    public long getTotalSize() {
        return this.mInfos[0] * 1024;
    }

    @UnsupportedAppUsage
    public long getFreeSize() {
        return this.mInfos[1] * 1024;
    }

    @UnsupportedAppUsage
    public long getCachedSize() {
        return getCachedSizeKb() * 1024;
    }

    public long getKernelUsedSize() {
        return getKernelUsedSizeKb() * 1024;
    }

    public long getTotalSizeKb() {
        return this.mInfos[0];
    }

    public long getFreeSizeKb() {
        return this.mInfos[1];
    }

    public long getCachedSizeKb() {
        return ((this.mInfos[2] + this.mInfos[6]) + this.mInfos[3]) - this.mInfos[11];
    }

    public long getKernelUsedSizeKb() {
        return this.mInfos[4] + this.mInfos[7] + this.mInfos[12] + this.mInfos[13] + this.mInfos[14];
    }

    public long getSwapTotalSizeKb() {
        return this.mInfos[8];
    }

    public long getSwapFreeSizeKb() {
        return this.mInfos[9];
    }

    public long getZramTotalSizeKb() {
        return this.mInfos[10];
    }

    @UnsupportedAppUsage
    public long[] getRawInfo() {
        return this.mInfos;
    }
}
