package android.bluetooth.p000le;

import android.bluetooth.BluetoothDevice;

/* renamed from: android.bluetooth.le.PeriodicAdvertisingCallback */
/* loaded from: classes.dex */
public abstract class PeriodicAdvertisingCallback {
    public static final int SYNC_NO_RESOURCES = 2;
    public static final int SYNC_NO_RESPONSE = 1;
    public static final int SYNC_SUCCESS = 0;

    public void onSyncEstablished(int syncHandle, BluetoothDevice device, int advertisingSid, int skip, int timeout, int status) {
    }

    public void onPeriodicAdvertisingReport(PeriodicAdvertisingReport report) {
    }

    public void onSyncLost(int syncHandle) {
    }
}
