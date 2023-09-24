package com.android.internal.p016os;

import android.p007os.BatteryStats;
import android.util.LongSparseArray;

/* renamed from: com.android.internal.os.MemoryPowerCalculator */
/* loaded from: classes4.dex */
public class MemoryPowerCalculator extends PowerCalculator {
    private static final boolean DEBUG = false;
    public static final String TAG = "MemoryPowerCalculator";
    private final double[] powerAverages;

    public MemoryPowerCalculator(PowerProfile profile) {
        int numBuckets = profile.getNumElements(PowerProfile.POWER_MEMORY);
        this.powerAverages = new double[numBuckets];
        for (int i = 0; i < numBuckets; i++) {
            this.powerAverages[i] = profile.getAveragePower(PowerProfile.POWER_MEMORY, i);
            double d = this.powerAverages[i];
        }
    }

    @Override // com.android.internal.p016os.PowerCalculator
    public void calculateApp(BatterySipper app, BatteryStats.Uid u, long rawRealtimeUs, long rawUptimeUs, int statsType) {
    }

    @Override // com.android.internal.p016os.PowerCalculator
    public void calculateRemaining(BatterySipper app, BatteryStats stats, long rawRealtimeUs, long rawUptimeUs, int statsType) {
        double totalMah = 0.0d;
        long totalTimeMs = 0;
        LongSparseArray<? extends BatteryStats.Timer> timers = stats.getKernelMemoryStats();
        for (int i = 0; i < timers.size() && i < this.powerAverages.length; i++) {
            double mAatRail = this.powerAverages[(int) timers.keyAt(i)];
            long timeMs = timers.valueAt(i).getTotalTimeLocked(rawRealtimeUs, statsType);
            double mAm = (timeMs * mAatRail) / 60000.0d;
            totalMah += mAm / 60.0d;
            totalTimeMs += timeMs;
        }
        app.usagePowerMah = totalMah;
        app.usageTimeMs = totalTimeMs;
    }
}
