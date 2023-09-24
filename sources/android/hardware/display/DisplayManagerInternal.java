package android.hardware.display;

import android.hardware.SensorManager;
import android.p007os.Handler;
import android.util.IntArray;
import android.util.SparseArray;
import android.view.Display;
import android.view.DisplayInfo;
import android.view.SurfaceControl;

/* loaded from: classes.dex */
public abstract class DisplayManagerInternal {

    /* loaded from: classes.dex */
    public interface DisplayPowerCallbacks {
        void acquireSuspendBlocker();

        void onDisplayStateChange(int i);

        void onProximityNegative();

        void onProximityPositive();

        void onStateChanged();

        void releaseSuspendBlocker();
    }

    /* loaded from: classes.dex */
    public interface DisplayTransactionListener {
        void onDisplayTransaction(SurfaceControl.Transaction transaction);
    }

    public abstract DisplayInfo getDisplayInfo(int i);

    public abstract DisplayedContentSample getDisplayedContentSample(int i, long j, long j2);

    public abstract DisplayedContentSamplingAttributes getDisplayedContentSamplingAttributes(int i);

    public abstract void getNonOverrideDisplayInfo(int i, DisplayInfo displayInfo);

    public abstract void initPowerManagement(DisplayPowerCallbacks displayPowerCallbacks, Handler handler, SensorManager sensorManager);

    public abstract boolean isProximitySensorAvailable();

    public abstract void onOverlayChanged();

    public abstract void performTraversal(SurfaceControl.Transaction transaction);

    public abstract void persistBrightnessTrackerState();

    public abstract void registerDisplayTransactionListener(DisplayTransactionListener displayTransactionListener);

    public abstract boolean requestPowerState(DisplayPowerRequest displayPowerRequest, boolean z);

    public abstract SurfaceControl.ScreenshotGraphicBuffer screenshot(int i);

    public abstract void setDisplayAccessUIDs(SparseArray<IntArray> sparseArray);

    public abstract void setDisplayInfoOverrideFromWindowManager(int i, DisplayInfo displayInfo);

    public abstract void setDisplayOffsets(int i, int i2, int i3);

    public abstract void setDisplayProperties(int i, boolean z, float f, int i2, boolean z2);

    public abstract void setDisplayScalingDisabled(int i, boolean z);

    public abstract boolean setDisplayedContentSamplingEnabled(int i, boolean z, int i2, int i3);

    public abstract void unregisterDisplayTransactionListener(DisplayTransactionListener displayTransactionListener);

    /* loaded from: classes.dex */
    public static final class DisplayPowerRequest {
        public static final int POLICY_BRIGHT = 3;
        public static final int POLICY_DIM = 2;
        public static final int POLICY_DOZE = 1;
        public static final int POLICY_OFF = 0;
        public static final int POLICY_VR = 4;
        public boolean blockScreenOn;
        public boolean boostScreenBrightness;
        public int dozeScreenBrightness;
        public int dozeScreenState;
        public boolean lowPowerMode;
        public int policy;
        public float screenAutoBrightnessAdjustmentOverride;
        public int screenBrightnessOverride;
        public float screenLowPowerBrightnessFactor;
        public boolean useAutoBrightness;
        public boolean useProximitySensor;

        public DisplayPowerRequest() {
            this.policy = 3;
            this.useProximitySensor = false;
            this.screenBrightnessOverride = -1;
            this.useAutoBrightness = false;
            this.screenAutoBrightnessAdjustmentOverride = Float.NaN;
            this.screenLowPowerBrightnessFactor = 0.5f;
            this.blockScreenOn = false;
            this.dozeScreenBrightness = -1;
            this.dozeScreenState = 0;
        }

        public DisplayPowerRequest(DisplayPowerRequest other) {
            copyFrom(other);
        }

        public boolean isBrightOrDim() {
            return this.policy == 3 || this.policy == 2;
        }

        public boolean isVr() {
            return this.policy == 4;
        }

        public void copyFrom(DisplayPowerRequest other) {
            this.policy = other.policy;
            this.useProximitySensor = other.useProximitySensor;
            this.screenBrightnessOverride = other.screenBrightnessOverride;
            this.useAutoBrightness = other.useAutoBrightness;
            this.screenAutoBrightnessAdjustmentOverride = other.screenAutoBrightnessAdjustmentOverride;
            this.screenLowPowerBrightnessFactor = other.screenLowPowerBrightnessFactor;
            this.blockScreenOn = other.blockScreenOn;
            this.lowPowerMode = other.lowPowerMode;
            this.boostScreenBrightness = other.boostScreenBrightness;
            this.dozeScreenBrightness = other.dozeScreenBrightness;
            this.dozeScreenState = other.dozeScreenState;
        }

        public boolean equals(Object o) {
            return (o instanceof DisplayPowerRequest) && equals((DisplayPowerRequest) o);
        }

        public boolean equals(DisplayPowerRequest other) {
            return other != null && this.policy == other.policy && this.useProximitySensor == other.useProximitySensor && this.screenBrightnessOverride == other.screenBrightnessOverride && this.useAutoBrightness == other.useAutoBrightness && floatEquals(this.screenAutoBrightnessAdjustmentOverride, other.screenAutoBrightnessAdjustmentOverride) && this.screenLowPowerBrightnessFactor == other.screenLowPowerBrightnessFactor && this.blockScreenOn == other.blockScreenOn && this.lowPowerMode == other.lowPowerMode && this.boostScreenBrightness == other.boostScreenBrightness && this.dozeScreenBrightness == other.dozeScreenBrightness && this.dozeScreenState == other.dozeScreenState;
        }

        private boolean floatEquals(float f1, float f2) {
            return f1 == f2 || (Float.isNaN(f1) && Float.isNaN(f2));
        }

        public int hashCode() {
            return 0;
        }

        public String toString() {
            return "policy=" + policyToString(this.policy) + ", useProximitySensor=" + this.useProximitySensor + ", screenBrightnessOverride=" + this.screenBrightnessOverride + ", useAutoBrightness=" + this.useAutoBrightness + ", screenAutoBrightnessAdjustmentOverride=" + this.screenAutoBrightnessAdjustmentOverride + ", screenLowPowerBrightnessFactor=" + this.screenLowPowerBrightnessFactor + ", blockScreenOn=" + this.blockScreenOn + ", lowPowerMode=" + this.lowPowerMode + ", boostScreenBrightness=" + this.boostScreenBrightness + ", dozeScreenBrightness=" + this.dozeScreenBrightness + ", dozeScreenState=" + Display.stateToString(this.dozeScreenState);
        }

        public static String policyToString(int policy) {
            switch (policy) {
                case 0:
                    return "OFF";
                case 1:
                    return "DOZE";
                case 2:
                    return "DIM";
                case 3:
                    return "BRIGHT";
                case 4:
                    return "VR";
                default:
                    return Integer.toString(policy);
            }
        }
    }
}
