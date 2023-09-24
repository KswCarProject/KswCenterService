package com.android.internal.p016os;

import android.p007os.Process;
import android.p007os.StrictMode;
import android.p007os.SystemClock;
import android.util.Slog;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.p016os.KernelWakelockStats;
import com.ibm.icu.text.DateFormat;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

/* renamed from: com.android.internal.os.KernelWakelockReader */
/* loaded from: classes4.dex */
public class KernelWakelockReader {
    private static final String TAG = "KernelWakelockReader";
    private static final String sWakelockFile = "/proc/wakelocks";
    private static final String sWakeupSourceFile = "/d/wakeup_sources";
    private static int sKernelWakelockUpdateVersion = 0;
    private static final int[] PROC_WAKELOCKS_FORMAT = {5129, 8201, 9, 9, 9, 8201};
    private static final int[] WAKEUP_SOURCES_FORMAT = {4105, 8457, 265, 265, 265, 265, 8457};
    private final String[] mProcWakelocksName = new String[3];
    private final long[] mProcWakelocksData = new long[3];

    public final KernelWakelockStats readKernelWakelockStats(KernelWakelockStats staleStats) {
        FileInputStream is;
        boolean wakeup_sources;
        byte[] buffer = new byte[32768];
        int len = 0;
        long startTime = SystemClock.uptimeMillis();
        int oldMask = StrictMode.allowThreadDiskReadsMask();
        try {
            try {
                is = new FileInputStream(sWakelockFile);
                wakeup_sources = false;
            } catch (FileNotFoundException e) {
                try {
                    FileInputStream is2 = new FileInputStream(sWakeupSourceFile);
                    is = is2;
                    wakeup_sources = true;
                } catch (FileNotFoundException e2) {
                    Slog.wtf(TAG, "neither /proc/wakelocks nor /d/wakeup_sources exists");
                    return null;
                }
            }
            while (true) {
                int cnt = is.read(buffer, len, buffer.length - len);
                if (cnt <= 0) {
                    break;
                }
                len += cnt;
            }
            is.close();
            StrictMode.setThreadPolicyMask(oldMask);
            long readTime = SystemClock.uptimeMillis() - startTime;
            if (readTime > 100) {
                Slog.m50w(TAG, "Reading wakelock stats took " + readTime + DateFormat.MINUTE_SECOND);
            }
            if (len > 0) {
                if (len >= buffer.length) {
                    Slog.wtf(TAG, "Kernel wake locks exceeded buffer size " + buffer.length);
                }
                int i = 0;
                while (true) {
                    if (i >= len) {
                        break;
                    } else if (buffer[i] == 0) {
                        len = i;
                        break;
                    } else {
                        i++;
                    }
                }
            }
            return parseProcWakelocks(buffer, len, wakeup_sources, staleStats);
        } catch (IOException e3) {
            Slog.wtf(TAG, "failed to read kernel wakelocks", e3);
            return null;
        } finally {
            StrictMode.setThreadPolicyMask(oldMask);
        }
    }

    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:78:0x0133 -> B:79:0x0134). Please submit an issue!!! */
    @VisibleForTesting
    public KernelWakelockStats parseProcWakelocks(byte[] wlBuffer, int len, boolean wakeup_sources, KernelWakelockStats staleStats) {
        int i;
        byte b;
        int startIndex;
        char c = 0;
        int i2 = 0;
        while (true) {
            i = i2;
            b = 10;
            if (i >= len || wlBuffer[i] == 10 || wlBuffer[i] == 0) {
                break;
            }
            i2 = i + 1;
        }
        int startIndex2 = i + 1;
        int endIndex = startIndex2;
        synchronized (this) {
            try {
                sKernelWakelockUpdateVersion++;
                int startIndex3 = startIndex2;
                while (true) {
                    if (endIndex < len) {
                        int endIndex2 = startIndex3;
                        while (endIndex2 < len) {
                            try {
                                if (wlBuffer[endIndex2] == b || wlBuffer[endIndex2] == 0) {
                                    break;
                                }
                                endIndex2++;
                            } catch (Throwable th) {
                                th = th;
                                throw th;
                            }
                        }
                        if (endIndex2 > len - 1) {
                            startIndex = startIndex3;
                            break;
                        }
                        try {
                            String[] nameStringArray = this.mProcWakelocksName;
                            long[] wlData = this.mProcWakelocksData;
                            for (int j = startIndex3; j < endIndex2; j++) {
                                if ((wlBuffer[j] & 128) != 0) {
                                    wlBuffer[j] = 63;
                                }
                            }
                            int endIndex3 = endIndex2;
                            int startIndex4 = startIndex3;
                            try {
                                boolean parsed = Process.parseProcLine(wlBuffer, startIndex3, endIndex2, wakeup_sources ? WAKEUP_SOURCES_FORMAT : PROC_WAKELOCKS_FORMAT, nameStringArray, wlData, null);
                                String name = nameStringArray[c].trim();
                                int count = (int) wlData[1];
                                long totalTime = wakeup_sources ? wlData[2] * 1000 : (wlData[2] + 500) / 1000;
                                if (parsed && name.length() > 0) {
                                    if (!staleStats.containsKey(name)) {
                                        staleStats.put(name, new KernelWakelockStats.Entry(count, totalTime, sKernelWakelockUpdateVersion));
                                    } else {
                                        KernelWakelockStats.Entry kwlStats = (KernelWakelockStats.Entry) staleStats.get(name);
                                        if (kwlStats.mVersion == sKernelWakelockUpdateVersion) {
                                            kwlStats.mCount += count;
                                            kwlStats.mTotalTime += totalTime;
                                        } else {
                                            kwlStats.mCount = count;
                                            kwlStats.mTotalTime = totalTime;
                                            kwlStats.mVersion = sKernelWakelockUpdateVersion;
                                        }
                                    }
                                } else if (!parsed) {
                                    try {
                                        Slog.wtf(TAG, "Failed to parse proc line: " + new String(wlBuffer, startIndex4, endIndex3 - startIndex4));
                                    } catch (Exception e) {
                                        Slog.wtf(TAG, "Failed to parse proc line!");
                                    }
                                }
                                startIndex3 = endIndex3 + 1;
                                endIndex = endIndex3;
                                c = 0;
                                b = 10;
                            } catch (Throwable th2) {
                                th = th2;
                                throw th;
                            }
                        } catch (Throwable th3) {
                            th = th3;
                        }
                    } else {
                        startIndex = startIndex3;
                        break;
                    }
                }
            } catch (Throwable th4) {
                th = th4;
            }
            try {
                Iterator<KernelWakelockStats.Entry> itr = staleStats.values().iterator();
                while (itr.hasNext()) {
                    if (itr.next().mVersion != sKernelWakelockUpdateVersion) {
                        itr.remove();
                    }
                }
                staleStats.kernelWakelockVersion = sKernelWakelockUpdateVersion;
                return staleStats;
            } catch (Throwable th5) {
                th = th5;
                throw th;
            }
        }
    }
}
