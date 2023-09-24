package android.p007os;

import android.media.AudioAttributes;

/* renamed from: android.os.NullVibrator */
/* loaded from: classes3.dex */
public class NullVibrator extends Vibrator {
    private static final NullVibrator sInstance = new NullVibrator();

    private NullVibrator() {
    }

    public static NullVibrator getInstance() {
        return sInstance;
    }

    @Override // android.p007os.Vibrator
    public boolean hasVibrator() {
        return false;
    }

    @Override // android.p007os.Vibrator
    public boolean hasAmplitudeControl() {
        return false;
    }

    @Override // android.p007os.Vibrator
    public void vibrate(int uid, String opPkg, VibrationEffect effect, String reason, AudioAttributes attributes) {
    }

    @Override // android.p007os.Vibrator
    public void cancel() {
    }
}
