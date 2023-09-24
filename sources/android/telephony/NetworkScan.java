package android.telephony;

import android.p007os.RemoteException;
import android.p007os.ServiceManager;
import com.android.internal.telephony.ITelephony;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes.dex */
public class NetworkScan {
    public static final int ERROR_INTERRUPTED = 10002;
    public static final int ERROR_INVALID_SCAN = 2;
    public static final int ERROR_INVALID_SCANID = 10001;
    public static final int ERROR_MODEM_ERROR = 1;
    public static final int ERROR_MODEM_UNAVAILABLE = 3;
    public static final int ERROR_RADIO_INTERFACE_ERROR = 10000;
    public static final int ERROR_UNSUPPORTED = 4;
    public static final int SUCCESS = 0;
    private static final String TAG = "NetworkScan";
    private final int mScanId;
    private final int mSubId;

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes.dex */
    public @interface ScanErrorCode {
    }

    public void stopScan() {
        ITelephony telephony = getITelephony();
        if (telephony == null) {
            Rlog.m86e(TAG, "Failed to get the ITelephony instance.");
        }
        try {
            telephony.stopNetworkScan(this.mSubId, this.mScanId);
        } catch (RemoteException ex) {
            Rlog.m85e(TAG, "stopNetworkScan  RemoteException", ex);
        } catch (IllegalArgumentException e) {
            Rlog.m88d(TAG, "stopNetworkScan - no active scan for ScanID=" + this.mScanId);
        } catch (RuntimeException ex2) {
            Rlog.m85e(TAG, "stopNetworkScan  RuntimeException", ex2);
        }
    }

    @Deprecated
    public void stop() throws RemoteException {
        try {
            stopScan();
        } catch (RuntimeException e) {
            throw new RemoteException("Failed to stop the network scan with id " + this.mScanId);
        }
    }

    public NetworkScan(int scanId, int subId) {
        this.mScanId = scanId;
        this.mSubId = subId;
    }

    private ITelephony getITelephony() {
        return ITelephony.Stub.asInterface(ServiceManager.getService("phone"));
    }
}
