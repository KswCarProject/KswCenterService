package com.android.internal.p016os;

import android.p007os.StrictMode;
import android.p007os.SystemClock;
import android.text.TextUtils;
import android.util.LongSparseLongArray;
import android.util.Slog;
import android.util.TimeUtils;
import com.android.internal.annotations.VisibleForTesting;
import com.ibm.icu.text.DateFormat;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/* renamed from: com.android.internal.os.KernelMemoryBandwidthStats */
/* loaded from: classes4.dex */
public class KernelMemoryBandwidthStats {
    private static final boolean DEBUG = false;
    private static final String TAG = "KernelMemoryBandwidthStats";
    private static final String mSysfsFile = "/sys/kernel/memory_state_time/show_stat";
    protected final LongSparseLongArray mBandwidthEntries = new LongSparseLongArray();
    private boolean mStatsDoNotExist = false;

    public void updateStats() {
        if (this.mStatsDoNotExist) {
            return;
        }
        long startTime = SystemClock.uptimeMillis();
        StrictMode.ThreadPolicy policy = StrictMode.allowThreadDiskReads();
        try {
            try {
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(mSysfsFile));
                    try {
                        parseStats(reader);
                        reader.close();
                    } catch (Throwable th) {
                        try {
                            throw th;
                        } catch (Throwable th2) {
                            if (th != null) {
                                try {
                                    reader.close();
                                } catch (Throwable th3) {
                                    th.addSuppressed(th3);
                                }
                            } else {
                                reader.close();
                            }
                            throw th2;
                        }
                    }
                } catch (FileNotFoundException e) {
                    Slog.m50w(TAG, "No kernel memory bandwidth stats available");
                    this.mBandwidthEntries.clear();
                    this.mStatsDoNotExist = true;
                }
            } catch (IOException e2) {
                Slog.m56e(TAG, "Failed to read memory bandwidth: " + e2.getMessage());
                this.mBandwidthEntries.clear();
            }
            StrictMode.setThreadPolicy(policy);
            long readTime = SystemClock.uptimeMillis() - startTime;
            if (readTime > 100) {
                Slog.m50w(TAG, "Reading memory bandwidth file took " + readTime + DateFormat.MINUTE_SECOND);
            }
        } catch (Throwable th4) {
            StrictMode.setThreadPolicy(policy);
            throw th4;
        }
    }

    @VisibleForTesting
    public void parseStats(BufferedReader reader) throws IOException {
        TextUtils.SimpleStringSplitter splitter = new TextUtils.SimpleStringSplitter(' ');
        this.mBandwidthEntries.clear();
        while (true) {
            String line = reader.readLine();
            if (line != null) {
                splitter.setString(line);
                splitter.next();
                int bandwidth = 0;
                do {
                    int index = this.mBandwidthEntries.indexOfKey(bandwidth);
                    if (index >= 0) {
                        this.mBandwidthEntries.put(bandwidth, this.mBandwidthEntries.valueAt(index) + (Long.parseLong(splitter.next()) / TimeUtils.NANOS_PER_MS));
                    } else {
                        this.mBandwidthEntries.put(bandwidth, Long.parseLong(splitter.next()) / TimeUtils.NANOS_PER_MS);
                    }
                    bandwidth++;
                } while (splitter.hasNext());
            } else {
                return;
            }
        }
    }

    public LongSparseLongArray getBandwidthEntries() {
        return this.mBandwidthEntries;
    }
}
