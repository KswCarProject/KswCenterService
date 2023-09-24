package com.android.internal.p016os;

import android.p007os.BatteryStats;

/* renamed from: com.android.internal.os.WifiPowerEstimator */
/* loaded from: classes4.dex */
public class WifiPowerEstimator extends PowerCalculator {
    private static final boolean DEBUG = false;
    private static final String TAG = "WifiPowerEstimator";
    private long mTotalAppWifiRunningTimeMs = 0;
    private final double mWifiPowerBatchScan;
    private final double mWifiPowerOn;
    private final double mWifiPowerPerPacket;
    private final double mWifiPowerScan;

    public WifiPowerEstimator(PowerProfile profile) {
        this.mWifiPowerPerPacket = getWifiPowerPerPacket(profile);
        this.mWifiPowerOn = profile.getAveragePower(PowerProfile.POWER_WIFI_ON);
        this.mWifiPowerScan = profile.getAveragePower(PowerProfile.POWER_WIFI_SCAN);
        this.mWifiPowerBatchScan = profile.getAveragePower(PowerProfile.POWER_WIFI_BATCHED_SCAN);
    }

    private static double getWifiPowerPerPacket(PowerProfile profile) {
        double WIFI_POWER = profile.getAveragePower(PowerProfile.POWER_WIFI_ACTIVE) / 3600.0d;
        return WIFI_POWER / 61.03515625d;
    }

    @Override // com.android.internal.p016os.PowerCalculator
    public void calculateApp(BatterySipper app, BatteryStats.Uid u, long rawRealtimeUs, long rawUptimeUs, int statsType) {
        BatteryStats.Uid uid = u;
        long j = rawRealtimeUs;
        int i = statsType;
        app.wifiRxPackets = uid.getNetworkActivityPackets(2, i);
        app.wifiTxPackets = uid.getNetworkActivityPackets(3, i);
        app.wifiRxBytes = uid.getNetworkActivityBytes(2, i);
        app.wifiTxBytes = uid.getNetworkActivityBytes(3, i);
        double wifiPacketPower = (app.wifiRxPackets + app.wifiTxPackets) * this.mWifiPowerPerPacket;
        app.wifiRunningTimeMs = uid.getWifiRunningTime(j, i) / 1000;
        this.mTotalAppWifiRunningTimeMs += app.wifiRunningTimeMs;
        double wifiLockPower = (app.wifiRunningTimeMs * this.mWifiPowerOn) / 3600000.0d;
        long wifiScanTimeMs = uid.getWifiScanTime(j, i) / 1000;
        double wifiScanPower = (wifiScanTimeMs * this.mWifiPowerScan) / 3600000.0d;
        double wifiBatchScanPower = 0.0d;
        int bin = 0;
        while (true) {
            int bin2 = bin;
            if (bin2 < 5) {
                long batchScanTimeMs = uid.getWifiBatchedScanTime(bin2, j, i) / 1000;
                double batchScanPower = (batchScanTimeMs * this.mWifiPowerBatchScan) / 3600000.0d;
                wifiBatchScanPower += batchScanPower;
                bin = bin2 + 1;
                uid = u;
                j = rawRealtimeUs;
                i = statsType;
            } else {
                app.wifiPowerMah = wifiPacketPower + wifiLockPower + wifiScanPower + wifiBatchScanPower;
                return;
            }
        }
    }

    @Override // com.android.internal.p016os.PowerCalculator
    public void calculateRemaining(BatterySipper app, BatteryStats stats, long rawRealtimeUs, long rawUptimeUs, int statsType) {
        long totalRunningTimeMs = stats.getGlobalWifiRunningTime(rawRealtimeUs, statsType) / 1000;
        double powerDrain = ((totalRunningTimeMs - this.mTotalAppWifiRunningTimeMs) * this.mWifiPowerOn) / 3600000.0d;
        app.wifiRunningTimeMs = totalRunningTimeMs;
        app.wifiPowerMah = Math.max(0.0d, powerDrain);
    }

    @Override // com.android.internal.p016os.PowerCalculator
    public void reset() {
        this.mTotalAppWifiRunningTimeMs = 0L;
    }
}
