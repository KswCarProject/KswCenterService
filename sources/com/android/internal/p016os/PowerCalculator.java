package com.android.internal.p016os;

import android.p007os.BatteryStats;

/* renamed from: com.android.internal.os.PowerCalculator */
/* loaded from: classes4.dex */
public abstract class PowerCalculator {
    public abstract void calculateApp(BatterySipper batterySipper, BatteryStats.Uid uid, long j, long j2, int i);

    public void calculateRemaining(BatterySipper app, BatteryStats stats, long rawRealtimeUs, long rawUptimeUs, int statsType) {
    }

    public void reset() {
    }
}
