package android.p007os;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.media.AudioAttributes;
import android.p007os.IVibratorService;
import android.util.Log;

/* renamed from: android.os.SystemVibrator */
/* loaded from: classes3.dex */
public class SystemVibrator extends Vibrator {
    private static final String TAG = "Vibrator";
    private final IVibratorService mService;
    private final Binder mToken;

    @UnsupportedAppUsage
    public SystemVibrator() {
        this.mToken = new Binder();
        this.mService = IVibratorService.Stub.asInterface(ServiceManager.getService(Context.VIBRATOR_SERVICE));
    }

    @UnsupportedAppUsage
    public SystemVibrator(Context context) {
        super(context);
        this.mToken = new Binder();
        this.mService = IVibratorService.Stub.asInterface(ServiceManager.getService(Context.VIBRATOR_SERVICE));
    }

    @Override // android.p007os.Vibrator
    public boolean hasVibrator() {
        if (this.mService == null) {
            Log.m64w(TAG, "Failed to vibrate; no vibrator service.");
            return false;
        }
        try {
            return this.mService.hasVibrator();
        } catch (RemoteException e) {
            return false;
        }
    }

    @Override // android.p007os.Vibrator
    public boolean hasAmplitudeControl() {
        if (this.mService == null) {
            Log.m64w(TAG, "Failed to check amplitude control; no vibrator service.");
            return false;
        }
        try {
            return this.mService.hasAmplitudeControl();
        } catch (RemoteException e) {
            return false;
        }
    }

    @Override // android.p007os.Vibrator
    public void vibrate(int uid, String opPkg, VibrationEffect effect, String reason, AudioAttributes attributes) {
        if (this.mService == null) {
            Log.m64w(TAG, "Failed to vibrate; no vibrator service.");
            return;
        }
        try {
            this.mService.vibrate(uid, opPkg, effect, usageForAttributes(attributes), reason, this.mToken);
        } catch (RemoteException e) {
            Log.m63w(TAG, "Failed to vibrate.", e);
        }
    }

    private static int usageForAttributes(AudioAttributes attributes) {
        if (attributes != null) {
            return attributes.getUsage();
        }
        return 0;
    }

    @Override // android.p007os.Vibrator
    public void cancel() {
        if (this.mService == null) {
            return;
        }
        try {
            this.mService.cancelVibrate(this.mToken);
        } catch (RemoteException e) {
            Log.m63w(TAG, "Failed to cancel vibration.", e);
        }
    }
}
