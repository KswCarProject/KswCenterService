package com.android.internal.p016os;

import android.p007os.BatteryStats;

/* renamed from: com.android.internal.os.CameraPowerCalculator */
/* loaded from: classes4.dex */
public class CameraPowerCalculator extends PowerCalculator {
    private final double mCameraPowerOnAvg;

    public CameraPowerCalculator(PowerProfile profile) {
        this.mCameraPowerOnAvg = profile.getAveragePower(PowerProfile.POWER_CAMERA);
    }

    @Override // com.android.internal.p016os.PowerCalculator
    public void calculateApp(BatterySipper app, BatteryStats.Uid u, long rawRealtimeUs, long rawUptimeUs, int statsType) {
        BatteryStats.Timer timer = u.getCameraTurnedOnTimer();
        if (timer != null) {
            long totalTime = timer.getTotalTimeLocked(rawRealtimeUs, statsType) / 1000;
            app.cameraTimeMs = totalTime;
            app.cameraPowerMah = (totalTime * this.mCameraPowerOnAvg) / 3600000.0d;
            return;
        }
        app.cameraTimeMs = 0L;
        app.cameraPowerMah = 0.0d;
    }
}
