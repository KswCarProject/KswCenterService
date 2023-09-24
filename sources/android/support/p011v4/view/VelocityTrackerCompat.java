package android.support.p011v4.view;

import android.view.VelocityTracker;

@Deprecated
/* renamed from: android.support.v4.view.VelocityTrackerCompat */
/* loaded from: classes3.dex */
public final class VelocityTrackerCompat {
    @Deprecated
    public static float getXVelocity(VelocityTracker tracker, int pointerId) {
        return tracker.getXVelocity(pointerId);
    }

    @Deprecated
    public static float getYVelocity(VelocityTracker tracker, int pointerId) {
        return tracker.getYVelocity(pointerId);
    }

    private VelocityTrackerCompat() {
    }
}
