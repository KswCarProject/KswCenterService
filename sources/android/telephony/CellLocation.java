package android.telephony;

import android.annotation.UnsupportedAppUsage;
import android.p007os.Bundle;
import android.p007os.RemoteException;
import android.p007os.ServiceManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import com.android.internal.telephony.ITelephony;

/* loaded from: classes.dex */
public abstract class CellLocation {
    @UnsupportedAppUsage
    public abstract void fillInNotifierBundle(Bundle bundle);

    @UnsupportedAppUsage
    public abstract boolean isEmpty();

    public abstract void setStateInvalid();

    public static void requestLocationUpdate() {
        try {
            ITelephony phone = ITelephony.Stub.asInterface(ServiceManager.getService("phone"));
            if (phone != null) {
                phone.updateServiceLocation();
            }
        } catch (RemoteException e) {
        }
    }

    @UnsupportedAppUsage
    public static CellLocation newFromBundle(Bundle bundle) {
        switch (TelephonyManager.getDefault().getCurrentPhoneType()) {
            case 1:
                return new GsmCellLocation(bundle);
            case 2:
                return new CdmaCellLocation(bundle);
            default:
                return null;
        }
    }

    public static CellLocation getEmpty() {
        switch (TelephonyManager.getDefault().getCurrentPhoneType()) {
            case 1:
                return new GsmCellLocation();
            case 2:
                return new CdmaCellLocation();
            default:
                return null;
        }
    }
}
