package android.bluetooth.le;

import android.annotation.SystemApi;
import android.app.ActivityThread;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.IBluetoothGatt;
import android.bluetooth.IBluetoothManager;
import android.bluetooth.le.IScannerCallback;
import android.bluetooth.le.ScanSettings;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.os.WorkSource;
import android.util.Log;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class BluetoothLeScanner {
    private static final boolean DBG = true;
    public static final String EXTRA_CALLBACK_TYPE = "android.bluetooth.le.extra.CALLBACK_TYPE";
    public static final String EXTRA_ERROR_CODE = "android.bluetooth.le.extra.ERROR_CODE";
    public static final String EXTRA_LIST_SCAN_RESULT = "android.bluetooth.le.extra.LIST_SCAN_RESULT";
    private static final String TAG = "BluetoothLeScanner";
    private static final boolean VDBG = false;
    private BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private final IBluetoothManager mBluetoothManager;
    private final Handler mHandler = new Handler(Looper.getMainLooper());
    /* access modifiers changed from: private */
    public final Map<ScanCallback, BleScanCallbackWrapper> mLeScanClients = new HashMap();

    public BluetoothLeScanner(IBluetoothManager bluetoothManager) {
        this.mBluetoothManager = bluetoothManager;
    }

    public void startScan(ScanCallback callback) {
        startScan((List<ScanFilter>) null, new ScanSettings.Builder().build(), callback);
    }

    public void startScan(List<ScanFilter> filters, ScanSettings settings, ScanCallback callback) {
        startScan(filters, settings, (WorkSource) null, callback, (PendingIntent) null, (List<List<ResultStorageDescriptor>>) null);
    }

    public int startScan(List<ScanFilter> filters, ScanSettings settings, PendingIntent callbackIntent) {
        return startScan(filters, settings != null ? settings : new ScanSettings.Builder().build(), (WorkSource) null, (ScanCallback) null, callbackIntent, (List<List<ResultStorageDescriptor>>) null);
    }

    @SystemApi
    public void startScanFromSource(WorkSource workSource, ScanCallback callback) {
        startScanFromSource((List<ScanFilter>) null, new ScanSettings.Builder().build(), workSource, callback);
    }

    @SystemApi
    public void startScanFromSource(List<ScanFilter> filters, ScanSettings settings, WorkSource workSource, ScanCallback callback) {
        startScan(filters, settings, workSource, callback, (PendingIntent) null, (List<List<ResultStorageDescriptor>>) null);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:82:0x00cb, code lost:
        return 3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:87:0x00d0, code lost:
        r0 = th;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int startScan(java.util.List<android.bluetooth.le.ScanFilter> r17, android.bluetooth.le.ScanSettings r18, android.os.WorkSource r19, android.bluetooth.le.ScanCallback r20, android.app.PendingIntent r21, java.util.List<java.util.List<android.bluetooth.le.ResultStorageDescriptor>> r22) {
        /*
            r16 = this;
            r9 = r16
            r10 = r18
            r11 = r20
            r12 = r21
            android.bluetooth.BluetoothAdapter r0 = r9.mBluetoothAdapter
            android.bluetooth.le.BluetoothLeUtils.checkAdapterStateOn(r0)
            if (r11 != 0) goto L_0x001a
            if (r12 == 0) goto L_0x0012
            goto L_0x001a
        L_0x0012:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.String r1 = "callback is null"
            r0.<init>(r1)
            throw r0
        L_0x001a:
            if (r10 == 0) goto L_0x00d2
            java.util.Map<android.bluetooth.le.ScanCallback, android.bluetooth.le.BluetoothLeScanner$BleScanCallbackWrapper> r13 = r9.mLeScanClients
            monitor-enter(r13)
            r1 = 1
            if (r11 == 0) goto L_0x0035
            java.util.Map<android.bluetooth.le.ScanCallback, android.bluetooth.le.BluetoothLeScanner$BleScanCallbackWrapper> r0 = r9.mLeScanClients     // Catch:{ all -> 0x0030 }
            boolean r0 = r0.containsKey(r11)     // Catch:{ all -> 0x0030 }
            if (r0 == 0) goto L_0x0035
            int r0 = r9.postCallbackErrorOrReturn(r11, r1)     // Catch:{ all -> 0x0030 }
            monitor-exit(r13)     // Catch:{ all -> 0x0030 }
            return r0
        L_0x0030:
            r0 = move-exception
            r15 = r17
            goto L_0x00ce
        L_0x0035:
            android.bluetooth.IBluetoothManager r0 = r9.mBluetoothManager     // Catch:{ RemoteException -> 0x003c }
            android.bluetooth.IBluetoothGatt r0 = r0.getBluetoothGatt()     // Catch:{ RemoteException -> 0x003c }
            goto L_0x003e
        L_0x003c:
            r0 = move-exception
            r0 = 0
        L_0x003e:
            r14 = r0
            r2 = 3
            if (r14 != 0) goto L_0x0048
            int r0 = r9.postCallbackErrorOrReturn(r11, r2)     // Catch:{ all -> 0x0030 }
            monitor-exit(r13)     // Catch:{ all -> 0x0030 }
            return r0
        L_0x0048:
            int r0 = r18.getCallbackType()     // Catch:{ all -> 0x0030 }
            r3 = 8
            r15 = 0
            if (r0 != r3) goto L_0x006d
            if (r17 == 0) goto L_0x0059
            boolean r0 = r17.isEmpty()     // Catch:{ all -> 0x0030 }
            if (r0 == 0) goto L_0x006d
        L_0x0059:
            android.bluetooth.le.ScanFilter$Builder r0 = new android.bluetooth.le.ScanFilter$Builder     // Catch:{ all -> 0x0030 }
            r0.<init>()     // Catch:{ all -> 0x0030 }
            android.bluetooth.le.ScanFilter r0 = r0.build()     // Catch:{ all -> 0x0030 }
            android.bluetooth.le.ScanFilter[] r1 = new android.bluetooth.le.ScanFilter[r1]     // Catch:{ all -> 0x0030 }
            r1[r15] = r0     // Catch:{ all -> 0x0030 }
            java.util.List r1 = java.util.Arrays.asList(r1)     // Catch:{ all -> 0x0030 }
            r0 = r1
            r8 = r0
            goto L_0x006f
        L_0x006d:
            r8 = r17
        L_0x006f:
            boolean r0 = r9.isSettingsConfigAllowedForScan(r10)     // Catch:{ all -> 0x00cc }
            r1 = 4
            if (r0 != 0) goto L_0x007f
            int r0 = r9.postCallbackErrorOrReturn(r11, r1)     // Catch:{ all -> 0x007c }
            monitor-exit(r13)     // Catch:{ all -> 0x007c }
            return r0
        L_0x007c:
            r0 = move-exception
            r15 = r8
            goto L_0x00ce
        L_0x007f:
            boolean r0 = r9.isHardwareResourcesAvailableForScan(r10)     // Catch:{ all -> 0x00cc }
            if (r0 != 0) goto L_0x008c
            r0 = 5
            int r0 = r9.postCallbackErrorOrReturn(r11, r0)     // Catch:{ all -> 0x007c }
            monitor-exit(r13)     // Catch:{ all -> 0x007c }
            return r0
        L_0x008c:
            boolean r0 = r9.isSettingsAndFilterComboAllowed(r10, r8)     // Catch:{ all -> 0x00cc }
            if (r0 != 0) goto L_0x0098
            int r0 = r9.postCallbackErrorOrReturn(r11, r1)     // Catch:{ all -> 0x007c }
            monitor-exit(r13)     // Catch:{ all -> 0x007c }
            return r0
        L_0x0098:
            boolean r0 = r9.isRoutingAllowedForScan(r10)     // Catch:{ all -> 0x00cc }
            if (r0 != 0) goto L_0x00a4
            int r0 = r9.postCallbackErrorOrReturn(r11, r1)     // Catch:{ all -> 0x007c }
            monitor-exit(r13)     // Catch:{ all -> 0x007c }
            return r0
        L_0x00a4:
            if (r11 == 0) goto L_0x00bd
            android.bluetooth.le.BluetoothLeScanner$BleScanCallbackWrapper r0 = new android.bluetooth.le.BluetoothLeScanner$BleScanCallbackWrapper     // Catch:{ all -> 0x00cc }
            r1 = r0
            r2 = r16
            r3 = r14
            r4 = r8
            r5 = r18
            r6 = r19
            r7 = r20
            r15 = r8
            r8 = r22
            r1.<init>(r3, r4, r5, r6, r7, r8)     // Catch:{ all -> 0x00d0 }
            r0.startRegistration()     // Catch:{ all -> 0x00d0 }
            goto L_0x00c6
        L_0x00bd:
            r15 = r8
            java.lang.String r0 = android.app.ActivityThread.currentOpPackageName()     // Catch:{ RemoteException -> 0x00c9 }
            r14.startScanForIntent(r12, r10, r15, r0)     // Catch:{ RemoteException -> 0x00c9 }
        L_0x00c6:
            monitor-exit(r13)     // Catch:{ all -> 0x00d0 }
            r0 = 0
            return r0
        L_0x00c9:
            r0 = move-exception
            monitor-exit(r13)     // Catch:{ all -> 0x00d0 }
            return r2
        L_0x00cc:
            r0 = move-exception
            r15 = r8
        L_0x00ce:
            monitor-exit(r13)     // Catch:{ all -> 0x00d0 }
            throw r0
        L_0x00d0:
            r0 = move-exception
            goto L_0x00ce
        L_0x00d2:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.String r1 = "settings is null"
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.bluetooth.le.BluetoothLeScanner.startScan(java.util.List, android.bluetooth.le.ScanSettings, android.os.WorkSource, android.bluetooth.le.ScanCallback, android.app.PendingIntent, java.util.List):int");
    }

    public void stopScan(ScanCallback callback) {
        BluetoothLeUtils.checkAdapterStateOn(this.mBluetoothAdapter);
        synchronized (this.mLeScanClients) {
            BleScanCallbackWrapper wrapper = this.mLeScanClients.remove(callback);
            if (wrapper == null) {
                Log.d(TAG, "could not find callback wrapper");
            } else {
                wrapper.stopLeScan();
            }
        }
    }

    public void stopScan(PendingIntent callbackIntent) {
        BluetoothLeUtils.checkAdapterStateOn(this.mBluetoothAdapter);
        try {
            this.mBluetoothManager.getBluetoothGatt().stopScanForIntent(callbackIntent, ActivityThread.currentOpPackageName());
        } catch (RemoteException e) {
        }
    }

    public void flushPendingScanResults(ScanCallback callback) {
        BluetoothLeUtils.checkAdapterStateOn(this.mBluetoothAdapter);
        if (callback != null) {
            synchronized (this.mLeScanClients) {
                BleScanCallbackWrapper wrapper = this.mLeScanClients.get(callback);
                if (wrapper != null) {
                    wrapper.flushPendingBatchResults();
                    return;
                }
                return;
            }
        }
        throw new IllegalArgumentException("callback cannot be null!");
    }

    @SystemApi
    public void startTruncatedScan(List<TruncatedFilter> truncatedFilters, ScanSettings settings, ScanCallback callback) {
        int filterSize = truncatedFilters.size();
        ArrayList arrayList = new ArrayList(filterSize);
        List<List<ResultStorageDescriptor>> scanStorages = new ArrayList<>(filterSize);
        for (TruncatedFilter filter : truncatedFilters) {
            arrayList.add(filter.getFilter());
            scanStorages.add(filter.getStorageDescriptors());
        }
        startScan(arrayList, settings, (WorkSource) null, callback, (PendingIntent) null, scanStorages);
    }

    public void cleanup() {
        this.mLeScanClients.clear();
    }

    private class BleScanCallbackWrapper extends IScannerCallback.Stub {
        private static final int REGISTRATION_CALLBACK_TIMEOUT_MILLIS = 2000;
        private IBluetoothGatt mBluetoothGatt;
        private final List<ScanFilter> mFilters;
        private List<List<ResultStorageDescriptor>> mResultStorages;
        /* access modifiers changed from: private */
        public final ScanCallback mScanCallback;
        private int mScannerId = 0;
        private ScanSettings mSettings;
        private final WorkSource mWorkSource;

        public BleScanCallbackWrapper(IBluetoothGatt bluetoothGatt, List<ScanFilter> filters, ScanSettings settings, WorkSource workSource, ScanCallback scanCallback, List<List<ResultStorageDescriptor>> resultStorages) {
            this.mBluetoothGatt = bluetoothGatt;
            this.mFilters = filters;
            this.mSettings = settings;
            this.mWorkSource = workSource;
            this.mScanCallback = scanCallback;
            this.mResultStorages = resultStorages;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:25:0x004e, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:27:0x0050, code lost:
            return;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void startRegistration() {
            /*
                r6 = this;
                monitor-enter(r6)
                int r0 = r6.mScannerId     // Catch:{ all -> 0x0051 }
                r1 = -1
                if (r0 == r1) goto L_0x004f
                int r0 = r6.mScannerId     // Catch:{ all -> 0x0051 }
                r2 = -2
                if (r0 != r2) goto L_0x000c
                goto L_0x004f
            L_0x000c:
                android.bluetooth.IBluetoothGatt r0 = r6.mBluetoothGatt     // Catch:{ RemoteException | InterruptedException -> 0x0019 }
                android.os.WorkSource r3 = r6.mWorkSource     // Catch:{ RemoteException | InterruptedException -> 0x0019 }
                r0.registerScanner(r6, r3)     // Catch:{ RemoteException | InterruptedException -> 0x0019 }
                r3 = 2000(0x7d0, double:9.88E-321)
                r6.wait(r3)     // Catch:{ RemoteException | InterruptedException -> 0x0019 }
                goto L_0x0029
            L_0x0019:
                r0 = move-exception
                java.lang.String r3 = "BluetoothLeScanner"
                java.lang.String r4 = "application registeration exception"
                android.util.Log.e(r3, r4, r0)     // Catch:{ all -> 0x0051 }
                android.bluetooth.le.BluetoothLeScanner r3 = android.bluetooth.le.BluetoothLeScanner.this     // Catch:{ all -> 0x0051 }
                android.bluetooth.le.ScanCallback r4 = r6.mScanCallback     // Catch:{ all -> 0x0051 }
                r5 = 3
                r3.postCallbackError(r4, r5)     // Catch:{ all -> 0x0051 }
            L_0x0029:
                int r0 = r6.mScannerId     // Catch:{ all -> 0x0051 }
                if (r0 <= 0) goto L_0x0039
                android.bluetooth.le.BluetoothLeScanner r0 = android.bluetooth.le.BluetoothLeScanner.this     // Catch:{ all -> 0x0051 }
                java.util.Map r0 = r0.mLeScanClients     // Catch:{ all -> 0x0051 }
                android.bluetooth.le.ScanCallback r1 = r6.mScanCallback     // Catch:{ all -> 0x0051 }
                r0.put(r1, r6)     // Catch:{ all -> 0x0051 }
                goto L_0x004d
            L_0x0039:
                int r0 = r6.mScannerId     // Catch:{ all -> 0x0051 }
                if (r0 != 0) goto L_0x003f
                r6.mScannerId = r1     // Catch:{ all -> 0x0051 }
            L_0x003f:
                int r0 = r6.mScannerId     // Catch:{ all -> 0x0051 }
                if (r0 != r2) goto L_0x0045
                monitor-exit(r6)     // Catch:{ all -> 0x0051 }
                return
            L_0x0045:
                android.bluetooth.le.BluetoothLeScanner r0 = android.bluetooth.le.BluetoothLeScanner.this     // Catch:{ all -> 0x0051 }
                android.bluetooth.le.ScanCallback r1 = r6.mScanCallback     // Catch:{ all -> 0x0051 }
                r2 = 2
                r0.postCallbackError(r1, r2)     // Catch:{ all -> 0x0051 }
            L_0x004d:
                monitor-exit(r6)     // Catch:{ all -> 0x0051 }
                return
            L_0x004f:
                monitor-exit(r6)     // Catch:{ all -> 0x0051 }
                return
            L_0x0051:
                r0 = move-exception
                monitor-exit(r6)     // Catch:{ all -> 0x0051 }
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: android.bluetooth.le.BluetoothLeScanner.BleScanCallbackWrapper.startRegistration():void");
        }

        public void stopLeScan() {
            synchronized (this) {
                if (this.mScannerId <= 0) {
                    Log.e(BluetoothLeScanner.TAG, "Error state, mLeHandle: " + this.mScannerId);
                    return;
                }
                try {
                    this.mBluetoothGatt.stopScan(this.mScannerId);
                    this.mBluetoothGatt.unregisterScanner(this.mScannerId);
                } catch (RemoteException e) {
                    Log.e(BluetoothLeScanner.TAG, "Failed to stop scan and unregister", e);
                }
                this.mScannerId = -1;
            }
        }

        /* access modifiers changed from: package-private */
        public void flushPendingBatchResults() {
            synchronized (this) {
                if (this.mScannerId <= 0) {
                    Log.e(BluetoothLeScanner.TAG, "Error state, mLeHandle: " + this.mScannerId);
                    return;
                }
                try {
                    this.mBluetoothGatt.flushPendingBatchResults(this.mScannerId);
                } catch (RemoteException e) {
                    Log.e(BluetoothLeScanner.TAG, "Failed to get pending scan results", e);
                }
            }
        }

        public void onScannerRegistered(int status, int scannerId) {
            Log.d(BluetoothLeScanner.TAG, "onScannerRegistered() - status=" + status + " scannerId=" + scannerId + " mScannerId=" + this.mScannerId);
            synchronized (this) {
                if (status == 0) {
                    try {
                        if (this.mScannerId == -1) {
                            this.mBluetoothGatt.unregisterScanner(scannerId);
                        } else {
                            this.mScannerId = scannerId;
                            this.mBluetoothGatt.startScan(this.mScannerId, this.mSettings, this.mFilters, this.mResultStorages, ActivityThread.currentOpPackageName());
                        }
                    } catch (RemoteException e) {
                        Log.e(BluetoothLeScanner.TAG, "fail to start le scan: " + e);
                        this.mScannerId = -1;
                    }
                } else if (status == 6) {
                    this.mScannerId = -2;
                } else {
                    this.mScannerId = -1;
                }
                notifyAll();
            }
        }

        public void onScanResult(final ScanResult scanResult) {
            synchronized (this) {
                if (this.mScannerId > 0) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        public void run() {
                            BleScanCallbackWrapper.this.mScanCallback.onScanResult(1, scanResult);
                        }
                    });
                }
            }
        }

        public void onBatchScanResults(final List<ScanResult> results) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                public void run() {
                    BleScanCallbackWrapper.this.mScanCallback.onBatchScanResults(results);
                }
            });
        }

        public void onFoundOrLost(final boolean onFound, final ScanResult scanResult) {
            synchronized (this) {
                if (this.mScannerId > 0) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        public void run() {
                            if (onFound) {
                                BleScanCallbackWrapper.this.mScanCallback.onScanResult(2, scanResult);
                            } else {
                                BleScanCallbackWrapper.this.mScanCallback.onScanResult(4, scanResult);
                            }
                        }
                    });
                }
            }
        }

        public void onScanManagerErrorCallback(int errorCode) {
            synchronized (this) {
                if (this.mScannerId > 0) {
                    BluetoothLeScanner.this.postCallbackError(this.mScanCallback, errorCode);
                }
            }
        }
    }

    private int postCallbackErrorOrReturn(ScanCallback callback, int errorCode) {
        if (callback == null) {
            return errorCode;
        }
        postCallbackError(callback, errorCode);
        return 0;
    }

    /* access modifiers changed from: private */
    public void postCallbackError(final ScanCallback callback, final int errorCode) {
        this.mHandler.post(new Runnable() {
            public void run() {
                callback.onScanFailed(errorCode);
            }
        });
    }

    private boolean isSettingsConfigAllowedForScan(ScanSettings settings) {
        if (this.mBluetoothAdapter.isOffloadedFilteringSupported()) {
            return true;
        }
        if (settings.getCallbackType() == 1 && settings.getReportDelayMillis() == 0) {
            return true;
        }
        return false;
    }

    private boolean isSettingsAndFilterComboAllowed(ScanSettings settings, List<ScanFilter> filterList) {
        if ((settings.getCallbackType() & 6) == 0) {
            return true;
        }
        if (filterList == null) {
            return false;
        }
        for (ScanFilter filter : filterList) {
            if (filter.isAllFieldsEmpty()) {
                return false;
            }
        }
        return true;
    }

    private boolean isHardwareResourcesAvailableForScan(ScanSettings settings) {
        int callbackType = settings.getCallbackType();
        if ((callbackType & 2) == 0 && (callbackType & 4) == 0) {
            return true;
        }
        if (!this.mBluetoothAdapter.isOffloadedFilteringSupported() || !this.mBluetoothAdapter.isHardwareTrackingFiltersAvailable()) {
            return false;
        }
        return true;
    }

    private boolean isRoutingAllowedForScan(ScanSettings settings) {
        if (settings.getCallbackType() == 8 && settings.getScanMode() == -1) {
            return false;
        }
        return true;
    }
}
