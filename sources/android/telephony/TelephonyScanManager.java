package android.telephony;

import android.p007os.Binder;
import android.p007os.Bundle;
import android.p007os.Handler;
import android.p007os.HandlerThread;
import android.p007os.Looper;
import android.p007os.Message;
import android.p007os.Messenger;
import android.p007os.Parcelable;
import android.p007os.RemoteException;
import android.p007os.ServiceManager;
import android.telephony.TelephonyScanManager;
import android.util.SparseArray;
import com.android.internal.telephony.ITelephony;
import com.android.internal.util.Preconditions;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;

/* loaded from: classes.dex */
public final class TelephonyScanManager {
    public static final int CALLBACK_RESTRICTED_SCAN_RESULTS = 4;
    public static final int CALLBACK_SCAN_COMPLETE = 3;
    public static final int CALLBACK_SCAN_ERROR = 2;
    public static final int CALLBACK_SCAN_RESULTS = 1;
    public static final int INVALID_SCAN_ID = -1;
    public static final String SCAN_RESULT_KEY = "scanResult";
    private static final String TAG = "TelephonyScanManager";
    private final Looper mLooper;
    private final Messenger mMessenger;
    private SparseArray<NetworkScanInfo> mScanInfo = new SparseArray<>();

    /* loaded from: classes.dex */
    public static abstract class NetworkScanCallback {
        public void onResults(List<CellInfo> results) {
        }

        public void onComplete() {
        }

        public void onError(int error) {
        }
    }

    /* loaded from: classes.dex */
    private static class NetworkScanInfo {
        private final NetworkScanCallback mCallback;
        private final Executor mExecutor;
        private final NetworkScanRequest mRequest;

        NetworkScanInfo(NetworkScanRequest request, Executor executor, NetworkScanCallback callback) {
            this.mRequest = request;
            this.mExecutor = executor;
            this.mCallback = callback;
        }
    }

    public TelephonyScanManager() {
        HandlerThread thread = new HandlerThread(TAG);
        thread.start();
        this.mLooper = thread.getLooper();
        this.mMessenger = new Messenger(new HandlerC24281(this.mLooper));
    }

    /* renamed from: android.telephony.TelephonyScanManager$1 */
    /* loaded from: classes.dex */
    class HandlerC24281 extends Handler {
        HandlerC24281(Looper x0) {
            super(x0);
        }

        @Override // android.p007os.Handler
        public void handleMessage(Message message) {
            NetworkScanInfo nsi;
            Preconditions.checkNotNull(message, "message cannot be null");
            synchronized (TelephonyScanManager.this.mScanInfo) {
                nsi = (NetworkScanInfo) TelephonyScanManager.this.mScanInfo.get(message.arg2);
            }
            if (nsi != null) {
                final NetworkScanCallback callback = nsi.mCallback;
                Executor executor = nsi.mExecutor;
                if (callback == null) {
                    throw new RuntimeException("Failed to find NetworkScanCallback with id " + message.arg2);
                } else if (executor == null) {
                    throw new RuntimeException("Failed to find Executor with id " + message.arg2);
                } else {
                    switch (message.what) {
                        case 1:
                        case 4:
                            try {
                                Bundle b = message.getData();
                                Parcelable[] parcelables = b.getParcelableArray(TelephonyScanManager.SCAN_RESULT_KEY);
                                final CellInfo[] ci = new CellInfo[parcelables.length];
                                for (int i = 0; i < parcelables.length; i++) {
                                    ci[i] = (CellInfo) parcelables[i];
                                }
                                executor.execute(new Runnable() { // from class: android.telephony.-$$Lambda$TelephonyScanManager$1$jmXulbd8FzO5Qb8_Hi-Z6s_nJWI
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        TelephonyScanManager.HandlerC24281.lambda$handleMessage$0(ci, callback);
                                    }
                                });
                                return;
                            } catch (Exception e) {
                                Rlog.m85e(TelephonyScanManager.TAG, "Exception in networkscan callback onResults", e);
                                return;
                            }
                        case 2:
                            try {
                                final int errorCode = message.arg1;
                                executor.execute(new Runnable() { // from class: android.telephony.-$$Lambda$TelephonyScanManager$1$X9SMshZoHjJ6SzCbmgVMwQip2Q0
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        TelephonyScanManager.HandlerC24281.lambda$handleMessage$1(errorCode, callback);
                                    }
                                });
                                return;
                            } catch (Exception e2) {
                                Rlog.m85e(TelephonyScanManager.TAG, "Exception in networkscan callback onError", e2);
                                return;
                            }
                        case 3:
                            try {
                                executor.execute(new Runnable() { // from class: android.telephony.-$$Lambda$TelephonyScanManager$1$tGSpVQaVhc4GKIxjcECV-jCGYw4
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        TelephonyScanManager.HandlerC24281.lambda$handleMessage$2(TelephonyScanManager.NetworkScanCallback.this);
                                    }
                                });
                                TelephonyScanManager.this.mScanInfo.remove(message.arg2);
                                return;
                            } catch (Exception e3) {
                                Rlog.m85e(TelephonyScanManager.TAG, "Exception in networkscan callback onComplete", e3);
                                return;
                            }
                        default:
                            Rlog.m86e(TelephonyScanManager.TAG, "Unhandled message " + Integer.toHexString(message.what));
                            return;
                    }
                }
            }
            throw new RuntimeException("Failed to find NetworkScanInfo with id " + message.arg2);
        }

        static /* synthetic */ void lambda$handleMessage$0(CellInfo[] ci, NetworkScanCallback callback) {
            Rlog.m88d(TelephonyScanManager.TAG, "onResults: " + ci.toString());
            callback.onResults(Arrays.asList(ci));
        }

        static /* synthetic */ void lambda$handleMessage$1(int errorCode, NetworkScanCallback callback) {
            Rlog.m88d(TelephonyScanManager.TAG, "onError: " + errorCode);
            callback.onError(errorCode);
        }

        static /* synthetic */ void lambda$handleMessage$2(NetworkScanCallback callback) {
            Rlog.m88d(TelephonyScanManager.TAG, "onComplete");
            callback.onComplete();
        }
    }

    public NetworkScan requestNetworkScan(int subId, NetworkScanRequest request, Executor executor, NetworkScanCallback callback, String callingPackage) {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                int scanId = telephony.requestNetworkScan(subId, request, this.mMessenger, new Binder(), callingPackage);
                if (scanId == -1) {
                    Rlog.m86e(TAG, "Failed to initiate network scan");
                    return null;
                }
                saveScanInfo(scanId, request, executor, callback);
                return new NetworkScan(scanId, subId);
            }
        } catch (RemoteException ex) {
            Rlog.m85e(TAG, "requestNetworkScan RemoteException", ex);
        } catch (NullPointerException ex2) {
            Rlog.m85e(TAG, "requestNetworkScan NPE", ex2);
        }
        return null;
    }

    private void saveScanInfo(int id, NetworkScanRequest request, Executor executor, NetworkScanCallback callback) {
        synchronized (this.mScanInfo) {
            this.mScanInfo.put(id, new NetworkScanInfo(request, executor, callback));
        }
    }

    private ITelephony getITelephony() {
        return ITelephony.Stub.asInterface(ServiceManager.getService("phone"));
    }
}
