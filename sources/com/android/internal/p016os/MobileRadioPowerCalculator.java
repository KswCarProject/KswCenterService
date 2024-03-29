package com.android.internal.p016os;

import android.p007os.BatteryStats;

/* renamed from: com.android.internal.os.MobileRadioPowerCalculator */
/* loaded from: classes4.dex */
public class MobileRadioPowerCalculator extends PowerCalculator {
    private static final boolean DEBUG = false;
    private static final String TAG = "MobileRadioPowerController";
    private final double mPowerRadioOn;
    private final double mPowerScan;
    private BatteryStats mStats;
    private final double[] mPowerBins = new double[5];
    private long mTotalAppMobileActiveMs = 0;

    private double getMobilePowerPerPacket(long rawRealtimeUs, int statsType) {
        double MOBILE_POWER = this.mPowerRadioOn / 3600.0d;
        long mobileRx = this.mStats.getNetworkActivityPackets(0, statsType);
        long mobileTx = this.mStats.getNetworkActivityPackets(1, statsType);
        long mobileData = mobileRx + mobileTx;
        long radioDataUptimeMs = this.mStats.getMobileRadioActiveTime(rawRealtimeUs, statsType) / 1000;
        double mobilePps = (mobileData == 0 || radioDataUptimeMs == 0) ? 12.20703125d : mobileData / radioDataUptimeMs;
        return (MOBILE_POWER / mobilePps) / 3600.0d;
    }

    public MobileRadioPowerCalculator(PowerProfile profile, BatteryStats stats) {
        double temp = profile.getAveragePowerOrDefault(PowerProfile.POWER_RADIO_ACTIVE, -1.0d);
        int i = 0;
        int i2 = 1;
        if (temp != -1.0d) {
            this.mPowerRadioOn = temp;
        } else {
            double sum = 0.0d + profile.getAveragePower(PowerProfile.POWER_MODEM_CONTROLLER_RX);
            for (int i3 = 0; i3 < this.mPowerBins.length; i3++) {
                sum += profile.getAveragePower(PowerProfile.POWER_MODEM_CONTROLLER_TX, i3);
            }
            this.mPowerRadioOn = sum / (this.mPowerBins.length + 1);
        }
        if (profile.getAveragePowerOrDefault(PowerProfile.POWER_RADIO_ON, -1.0d) == -1.0d) {
            double idle = profile.getAveragePower(PowerProfile.POWER_MODEM_CONTROLLER_IDLE);
            this.mPowerBins[0] = (25.0d * idle) / 180.0d;
            while (true) {
                int i4 = i2;
                if (i4 >= this.mPowerBins.length) {
                    break;
                }
                this.mPowerBins[i4] = Math.max(1.0d, idle / 256.0d);
                i2 = i4 + 1;
            }
        } else {
            while (true) {
                int i5 = i;
                if (i5 >= this.mPowerBins.length) {
                    break;
                }
                this.mPowerBins[i5] = profile.getAveragePower(PowerProfile.POWER_RADIO_ON, i5);
                i = i5 + 1;
            }
        }
        this.mPowerScan = profile.getAveragePowerOrDefault(PowerProfile.POWER_RADIO_SCANNING, 0.0d);
        this.mStats = stats;
    }

    @Override // com.android.internal.p016os.PowerCalculator
    public void calculateApp(BatterySipper app, BatteryStats.Uid u, long rawRealtimeUs, long rawUptimeUs, int statsType) {
        app.mobileRxPackets = u.getNetworkActivityPackets(0, statsType);
        app.mobileTxPackets = u.getNetworkActivityPackets(1, statsType);
        app.mobileActive = u.getMobileRadioActiveTime(statsType) / 1000;
        app.mobileActiveCount = u.getMobileRadioActiveCount(statsType);
        app.mobileRxBytes = u.getNetworkActivityBytes(0, statsType);
        app.mobileTxBytes = u.getNetworkActivityBytes(1, statsType);
        if (app.mobileActive > 0) {
            this.mTotalAppMobileActiveMs += app.mobileActive;
            app.mobileRadioPowerMah = (app.mobileActive * this.mPowerRadioOn) / 3600000.0d;
            return;
        }
        app.mobileRadioPowerMah = (app.mobileRxPackets + app.mobileTxPackets) * getMobilePowerPerPacket(rawRealtimeUs, statsType);
    }

    @Override // com.android.internal.p016os.PowerCalculator
    public void calculateRemaining(BatterySipper app, BatteryStats stats, long rawRealtimeUs, long rawUptimeUs, int statsType) {
        double power = 0.0d;
        long signalTimeMs = 0;
        long noCoverageTimeMs = 0;
        for (int i = 0; i < this.mPowerBins.length; i++) {
            long strengthTimeMs = stats.getPhoneSignalStrengthTime(i, rawRealtimeUs, statsType) / 1000;
            long noCoverageTimeMs2 = noCoverageTimeMs;
            double p = (strengthTimeMs * this.mPowerBins[i]) / 3600000.0d;
            power += p;
            signalTimeMs += strengthTimeMs;
            if (i != 0) {
                noCoverageTimeMs = noCoverageTimeMs2;
            } else {
                noCoverageTimeMs = strengthTimeMs;
            }
        }
        long noCoverageTimeMs3 = noCoverageTimeMs;
        long noCoverageTimeMs4 = stats.getPhoneSignalScanningTime(rawRealtimeUs, statsType);
        long scanningTimeMs = noCoverageTimeMs4 / 1000;
        double p2 = (scanningTimeMs * this.mPowerScan) / 3600000.0d;
        double power2 = power + p2;
        long radioActiveTimeMs = this.mStats.getMobileRadioActiveTime(rawRealtimeUs, statsType) / 1000;
        long remainingActiveTimeMs = radioActiveTimeMs - this.mTotalAppMobileActiveMs;
        if (remainingActiveTimeMs > 0) {
            double p3 = remainingActiveTimeMs;
            power2 += (this.mPowerRadioOn * p3) / 3600000.0d;
        }
        if (power2 != 0.0d) {
            if (signalTimeMs != 0) {
                app.noCoveragePercent = (noCoverageTimeMs3 * 100.0d) / signalTimeMs;
            }
            app.mobileActive = remainingActiveTimeMs;
            app.mobileActiveCount = stats.getMobileRadioActiveUnknownCount(statsType);
            app.mobileRadioPowerMah = power2;
        }
    }

    @Override // com.android.internal.p016os.PowerCalculator
    public void reset() {
        this.mTotalAppMobileActiveMs = 0L;
    }

    public void reset(BatteryStats stats) {
        reset();
        this.mStats = stats;
    }
}
