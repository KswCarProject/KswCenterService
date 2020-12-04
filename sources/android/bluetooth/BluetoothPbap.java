package android.bluetooth;

import android.annotation.UnsupportedAppUsage;
import android.bluetooth.IBluetoothPbap;
import android.bluetooth.IBluetoothStateChangeCallback;
import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BluetoothPbap implements BluetoothProfile {
    public static final String ACTION_CONNECTION_STATE_CHANGED = "android.bluetooth.pbap.profile.action.CONNECTION_STATE_CHANGED";
    private static final boolean DBG = false;
    public static final int RESULT_CANCELED = 2;
    public static final int RESULT_FAILURE = 0;
    public static final int RESULT_SUCCESS = 1;
    private static final String TAG = "BluetoothPbap";
    private BluetoothAdapter mAdapter;
    private final IBluetoothStateChangeCallback mBluetoothStateChangeCallback = new IBluetoothStateChangeCallback.Stub() {
        public void onBluetoothStateChange(boolean up) {
            BluetoothPbap.log("onBluetoothStateChange: up=" + up);
            if (!up) {
                BluetoothPbap.this.doUnbind();
            } else {
                BluetoothPbap.this.doBind();
            }
        }
    };
    private final ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            BluetoothPbap.log("Proxy object connected");
            IBluetoothPbap unused = BluetoothPbap.this.mService = IBluetoothPbap.Stub.asInterface(service);
            if (BluetoothPbap.this.mServiceListener != null) {
                BluetoothPbap.this.mServiceListener.onServiceConnected(BluetoothPbap.this);
            }
        }

        public void onServiceDisconnected(ComponentName className) {
            BluetoothPbap.log("Proxy object disconnected");
            BluetoothPbap.this.doUnbind();
            if (BluetoothPbap.this.mServiceListener != null) {
                BluetoothPbap.this.mServiceListener.onServiceDisconnected();
            }
        }
    };
    private final Context mContext;
    /* access modifiers changed from: private */
    public volatile IBluetoothPbap mService;
    /* access modifiers changed from: private */
    public ServiceListener mServiceListener;

    public interface ServiceListener {
        void onServiceConnected(BluetoothPbap bluetoothPbap);

        void onServiceDisconnected();
    }

    public BluetoothPbap(Context context, ServiceListener l) {
        this.mContext = context;
        this.mServiceListener = l;
        this.mAdapter = BluetoothAdapter.getDefaultAdapter();
        IBluetoothManager mgr = this.mAdapter.getBluetoothManager();
        if (mgr != null) {
            try {
                mgr.registerStateChangeCallback(this.mBluetoothStateChangeCallback);
            } catch (RemoteException re) {
                Log.e(TAG, "", re);
            }
        }
        doBind();
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x004d, code lost:
        return true;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean doBind() {
        /*
            r7 = this;
            android.content.ServiceConnection r0 = r7.mConnection
            monitor-enter(r0)
            r1 = 0
            android.bluetooth.IBluetoothPbap r2 = r7.mService     // Catch:{ SecurityException -> 0x0051 }
            if (r2 != 0) goto L_0x004b
            java.lang.String r2 = "Binding service..."
            log(r2)     // Catch:{ SecurityException -> 0x0051 }
            android.content.Intent r2 = new android.content.Intent     // Catch:{ SecurityException -> 0x0051 }
            java.lang.Class<android.bluetooth.IBluetoothPbap> r3 = android.bluetooth.IBluetoothPbap.class
            java.lang.String r3 = r3.getName()     // Catch:{ SecurityException -> 0x0051 }
            r2.<init>((java.lang.String) r3)     // Catch:{ SecurityException -> 0x0051 }
            android.content.Context r3 = r7.mContext     // Catch:{ SecurityException -> 0x0051 }
            android.content.pm.PackageManager r3 = r3.getPackageManager()     // Catch:{ SecurityException -> 0x0051 }
            android.content.ComponentName r3 = r2.resolveSystemService(r3, r1)     // Catch:{ SecurityException -> 0x0051 }
            r2.setComponent(r3)     // Catch:{ SecurityException -> 0x0051 }
            if (r3 == 0) goto L_0x0033
            android.content.Context r4 = r7.mContext     // Catch:{ SecurityException -> 0x0051 }
            android.content.ServiceConnection r5 = r7.mConnection     // Catch:{ SecurityException -> 0x0051 }
            android.os.UserHandle r6 = android.os.UserHandle.CURRENT_OR_SELF     // Catch:{ SecurityException -> 0x0051 }
            boolean r4 = r4.bindServiceAsUser(r2, r5, r1, r6)     // Catch:{ SecurityException -> 0x0051 }
            if (r4 != 0) goto L_0x004b
        L_0x0033:
            java.lang.String r4 = "BluetoothPbap"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ SecurityException -> 0x0051 }
            r5.<init>()     // Catch:{ SecurityException -> 0x0051 }
            java.lang.String r6 = "Could not bind to Bluetooth Pbap Service with "
            r5.append(r6)     // Catch:{ SecurityException -> 0x0051 }
            r5.append(r2)     // Catch:{ SecurityException -> 0x0051 }
            java.lang.String r5 = r5.toString()     // Catch:{ SecurityException -> 0x0051 }
            android.util.Log.e(r4, r5)     // Catch:{ SecurityException -> 0x0051 }
            monitor-exit(r0)     // Catch:{ all -> 0x004f }
            return r1
        L_0x004b:
            monitor-exit(r0)     // Catch:{ all -> 0x004f }
            r0 = 1
            return r0
        L_0x004f:
            r1 = move-exception
            goto L_0x005b
        L_0x0051:
            r2 = move-exception
            java.lang.String r3 = "BluetoothPbap"
            java.lang.String r4 = ""
            android.util.Log.e(r3, r4, r2)     // Catch:{ all -> 0x004f }
            monitor-exit(r0)     // Catch:{ all -> 0x004f }
            return r1
        L_0x005b:
            monitor-exit(r0)     // Catch:{ all -> 0x004f }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.bluetooth.BluetoothPbap.doBind():boolean");
    }

    /* JADX INFO: finally extract failed */
    /* access modifiers changed from: private */
    public void doUnbind() {
        synchronized (this.mConnection) {
            if (this.mService != null) {
                log("Unbinding service...");
                try {
                    this.mContext.unbindService(this.mConnection);
                    this.mService = null;
                } catch (IllegalArgumentException ie) {
                    try {
                        Log.e(TAG, "", ie);
                        this.mService = null;
                    } catch (Throwable th) {
                        this.mService = null;
                        throw th;
                    }
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        try {
            close();
        } finally {
            super.finalize();
        }
    }

    public synchronized void close() {
        IBluetoothManager mgr = this.mAdapter.getBluetoothManager();
        if (mgr != null) {
            try {
                mgr.unregisterStateChangeCallback(this.mBluetoothStateChangeCallback);
            } catch (RemoteException re) {
                Log.e(TAG, "", re);
            }
        }
        doUnbind();
        this.mServiceListener = null;
    }

    public List<BluetoothDevice> getConnectedDevices() {
        log("getConnectedDevices()");
        IBluetoothPbap service = this.mService;
        if (service == null) {
            Log.w(TAG, "Proxy not attached to service");
            return new ArrayList();
        }
        try {
            return service.getConnectedDevices();
        } catch (RemoteException e) {
            Log.e(TAG, e.toString());
            return new ArrayList();
        }
    }

    public int getConnectionState(BluetoothDevice device) {
        log("getConnectionState: device=" + device);
        IBluetoothPbap service = this.mService;
        if (service == null) {
            Log.w(TAG, "Proxy not attached to service");
            return 0;
        }
        try {
            return service.getConnectionState(device);
        } catch (RemoteException e) {
            Log.e(TAG, e.toString());
            return 0;
        }
    }

    public List<BluetoothDevice> getDevicesMatchingConnectionStates(int[] states) {
        log("getDevicesMatchingConnectionStates: states=" + Arrays.toString(states));
        IBluetoothPbap service = this.mService;
        if (service == null) {
            Log.w(TAG, "Proxy not attached to service");
            return new ArrayList();
        }
        try {
            return service.getDevicesMatchingConnectionStates(states);
        } catch (RemoteException e) {
            Log.e(TAG, e.toString());
            return new ArrayList();
        }
    }

    public boolean isConnected(BluetoothDevice device) {
        return getConnectionState(device) == 2;
    }

    @UnsupportedAppUsage
    public boolean disconnect(BluetoothDevice device) {
        log("disconnect()");
        IBluetoothPbap service = this.mService;
        if (service == null) {
            Log.w(TAG, "Proxy not attached to service");
            return false;
        }
        try {
            service.disconnect(device);
            return true;
        } catch (RemoteException e) {
            Log.e(TAG, e.toString());
            return false;
        }
    }

    /* access modifiers changed from: private */
    public static void log(String msg) {
    }
}
