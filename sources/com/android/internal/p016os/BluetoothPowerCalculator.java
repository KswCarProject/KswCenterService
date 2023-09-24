package com.android.internal.p016os;

import android.p007os.BatteryStats;

/* renamed from: com.android.internal.os.BluetoothPowerCalculator */
/* loaded from: classes4.dex */
public class BluetoothPowerCalculator extends PowerCalculator {
    private static final boolean DEBUG = false;
    private static final String TAG = "BluetoothPowerCalculator";
    private double mAppTotalPowerMah = 0.0d;
    private long mAppTotalTimeMs = 0;
    private final double mIdleMa;
    private final double mRxMa;
    private final double mTxMa;

    public BluetoothPowerCalculator(PowerProfile profile) {
        this.mIdleMa = profile.getAveragePower(PowerProfile.POWER_BLUETOOTH_CONTROLLER_IDLE);
        this.mRxMa = profile.getAveragePower(PowerProfile.POWER_BLUETOOTH_CONTROLLER_RX);
        this.mTxMa = profile.getAveragePower(PowerProfile.POWER_BLUETOOTH_CONTROLLER_TX);
    }

    @Override // com.android.internal.p016os.PowerCalculator
    public void calculateApp(BatterySipper app, BatteryStats.Uid u, long rawRealtimeUs, long rawUptimeUs, int statsType) {
        BatteryStats.ControllerActivityCounter counter = u.getBluetoothControllerActivity();
        if (counter == null) {
            return;
        }
        long idleTimeMs = counter.getIdleTimeCounter().getCountLocked(statsType);
        long rxTimeMs = counter.getRxTimeCounter().getCountLocked(statsType);
        long txTimeMs = counter.getTxTimeCounters()[0].getCountLocked(statsType);
        long totalTimeMs = idleTimeMs + txTimeMs + rxTimeMs;
        double powerMah = counter.getPowerCounter().getCountLocked(statsType) / 3600000.0d;
        if (powerMah == 0.0d) {
            powerMah = (((idleTimeMs * this.mIdleMa) + (rxTimeMs * this.mRxMa)) + (txTimeMs * this.mTxMa)) / 3600000.0d;
        }
        app.bluetoothPowerMah = powerMah;
        app.bluetoothRunningTimeMs = totalTimeMs;
        app.btRxBytes = u.getNetworkActivityBytes(4, statsType);
        app.btTxBytes = u.getNetworkActivityBytes(5, statsType);
        this.mAppTotalPowerMah += powerMah;
        this.mAppTotalTimeMs += totalTimeMs;
    }

    @Override // com.android.internal.p016os.PowerCalculator
    public void calculateRemaining(BatterySipper app, BatteryStats stats, long rawRealtimeUs, long rawUptimeUs, int statsType) {
        BatteryStats.ControllerActivityCounter counter = stats.getBluetoothControllerActivity();
        long idleTimeMs = counter.getIdleTimeCounter().getCountLocked(statsType);
        long txTimeMs = counter.getTxTimeCounters()[0].getCountLocked(statsType);
        long rxTimeMs = counter.getRxTimeCounter().getCountLocked(statsType);
        long totalTimeMs = idleTimeMs + txTimeMs + rxTimeMs;
        double powerMah = counter.getPowerCounter().getCountLocked(statsType) / 3600000.0d;
        if (powerMah == 0.0d) {
            powerMah = (((idleTimeMs * this.mIdleMa) + (rxTimeMs * this.mRxMa)) + (txTimeMs * this.mTxMa)) / 3600000.0d;
        }
        app.bluetoothPowerMah = Math.max(0.0d, powerMah - this.mAppTotalPowerMah);
        app.bluetoothRunningTimeMs = Math.max(0L, totalTimeMs - this.mAppTotalTimeMs);
    }

    @Override // com.android.internal.p016os.PowerCalculator
    public void reset() {
        this.mAppTotalPowerMah = 0.0d;
        this.mAppTotalTimeMs = 0L;
    }
}
