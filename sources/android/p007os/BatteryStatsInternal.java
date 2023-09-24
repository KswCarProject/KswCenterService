package android.p007os;

/* renamed from: android.os.BatteryStatsInternal */
/* loaded from: classes3.dex */
public abstract class BatteryStatsInternal {
    public abstract String[] getMobileIfaces();

    public abstract String[] getWifiIfaces();

    public abstract void noteJobsDeferred(int i, int i2, long j);
}
