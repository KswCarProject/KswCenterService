package android.p007os;

/* renamed from: android.os.BatteryManagerInternal */
/* loaded from: classes3.dex */
public abstract class BatteryManagerInternal {
    public abstract int getBatteryChargeCounter();

    public abstract int getBatteryFullCharge();

    public abstract int getBatteryLevel();

    public abstract boolean getBatteryLevelLow();

    public abstract int getInvalidCharger();

    public abstract int getPlugType();

    public abstract boolean isPowered(int i);
}
