package android.bluetooth;

import android.bluetooth.BluetoothProfile;
import android.bluetooth.IBluetoothStateChangeCallback;
import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public abstract class BluetoothProfileConnector<T> {
    private final IBluetoothStateChangeCallback mBluetoothStateChangeCallback = new IBluetoothStateChangeCallback.Stub() {
        public void onBluetoothStateChange(boolean up) {
            if (up) {
                boolean unused = BluetoothProfileConnector.this.doBind();
            } else {
                BluetoothProfileConnector.this.doUnbind();
            }
        }
    };
    private final ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            BluetoothProfileConnector.this.logDebug("Proxy object connected");
            Object unused = BluetoothProfileConnector.this.mService = BluetoothProfileConnector.this.getServiceInterface(service);
            if (BluetoothProfileConnector.this.mServiceListener != null) {
                BluetoothProfileConnector.this.mServiceListener.onServiceConnected(BluetoothProfileConnector.this.mProfileId, BluetoothProfileConnector.this.mProfileProxy);
            }
        }

        public void onServiceDisconnected(ComponentName className) {
            BluetoothProfileConnector.this.logDebug("Proxy object disconnected");
            BluetoothProfileConnector.this.doUnbind();
            if (BluetoothProfileConnector.this.mServiceListener != null) {
                BluetoothProfileConnector.this.mServiceListener.onServiceDisconnected(BluetoothProfileConnector.this.mProfileId);
            }
        }
    };
    private Context mContext;
    /* access modifiers changed from: private */
    public final int mProfileId;
    private final String mProfileName;
    /* access modifiers changed from: private */
    public final BluetoothProfile mProfileProxy;
    /* access modifiers changed from: private */
    public volatile T mService;
    /* access modifiers changed from: private */
    public BluetoothProfile.ServiceListener mServiceListener;
    private final String mServiceName;

    public abstract T getServiceInterface(IBinder iBinder);

    BluetoothProfileConnector(BluetoothProfile profile, int profileId, String profileName, String serviceName) {
        this.mProfileId = profileId;
        this.mProfileProxy = profile;
        this.mProfileName = profileName;
        this.mServiceName = serviceName;
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x005f, code lost:
        return true;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean doBind() {
        /*
            r7 = this;
            android.content.ServiceConnection r0 = r7.mConnection
            monitor-enter(r0)
            T r1 = r7.mService     // Catch:{ all -> 0x0061 }
            if (r1 != 0) goto L_0x005e
            java.lang.String r1 = "Binding service..."
            r7.logDebug(r1)     // Catch:{ all -> 0x0061 }
            r1 = 0
            android.content.Intent r2 = new android.content.Intent     // Catch:{ SecurityException -> 0x0047 }
            java.lang.String r3 = r7.mServiceName     // Catch:{ SecurityException -> 0x0047 }
            r2.<init>((java.lang.String) r3)     // Catch:{ SecurityException -> 0x0047 }
            android.content.Context r3 = r7.mContext     // Catch:{ SecurityException -> 0x0047 }
            android.content.pm.PackageManager r3 = r3.getPackageManager()     // Catch:{ SecurityException -> 0x0047 }
            android.content.ComponentName r3 = r2.resolveSystemService(r3, r1)     // Catch:{ SecurityException -> 0x0047 }
            r2.setComponent(r3)     // Catch:{ SecurityException -> 0x0047 }
            if (r3 == 0) goto L_0x0031
            android.content.Context r4 = r7.mContext     // Catch:{ SecurityException -> 0x0047 }
            android.content.ServiceConnection r5 = r7.mConnection     // Catch:{ SecurityException -> 0x0047 }
            android.os.UserHandle r6 = android.os.UserHandle.CURRENT_OR_SELF     // Catch:{ SecurityException -> 0x0047 }
            boolean r4 = r4.bindServiceAsUser(r2, r5, r1, r6)     // Catch:{ SecurityException -> 0x0047 }
            if (r4 != 0) goto L_0x0030
            goto L_0x0031
        L_0x0030:
            goto L_0x005e
        L_0x0031:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ SecurityException -> 0x0047 }
            r4.<init>()     // Catch:{ SecurityException -> 0x0047 }
            java.lang.String r5 = "Could not bind to Bluetooth Service with "
            r4.append(r5)     // Catch:{ SecurityException -> 0x0047 }
            r4.append(r2)     // Catch:{ SecurityException -> 0x0047 }
            java.lang.String r4 = r4.toString()     // Catch:{ SecurityException -> 0x0047 }
            r7.logError(r4)     // Catch:{ SecurityException -> 0x0047 }
            monitor-exit(r0)     // Catch:{ all -> 0x0061 }
            return r1
        L_0x0047:
            r2 = move-exception
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x0061 }
            r3.<init>()     // Catch:{ all -> 0x0061 }
            java.lang.String r4 = "Failed to bind service. "
            r3.append(r4)     // Catch:{ all -> 0x0061 }
            r3.append(r2)     // Catch:{ all -> 0x0061 }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x0061 }
            r7.logError(r3)     // Catch:{ all -> 0x0061 }
            monitor-exit(r0)     // Catch:{ all -> 0x0061 }
            return r1
        L_0x005e:
            monitor-exit(r0)     // Catch:{ all -> 0x0061 }
            r0 = 1
            return r0
        L_0x0061:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0061 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.bluetooth.BluetoothProfileConnector.doBind():boolean");
    }

    /* access modifiers changed from: private */
    public void doUnbind() {
        synchronized (this.mConnection) {
            if (this.mService != null) {
                logDebug("Unbinding service...");
                try {
                    this.mContext.unbindService(this.mConnection);
                } catch (IllegalArgumentException ie) {
                    try {
                        logError("Unable to unbind service: " + ie);
                    } catch (Throwable th) {
                        this.mService = null;
                        throw th;
                    }
                }
                this.mService = null;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void connect(Context context, BluetoothProfile.ServiceListener listener) {
        this.mContext = context;
        this.mServiceListener = listener;
        IBluetoothManager mgr = BluetoothAdapter.getDefaultAdapter().getBluetoothManager();
        if (mgr != null) {
            try {
                mgr.registerStateChangeCallback(this.mBluetoothStateChangeCallback);
            } catch (RemoteException re) {
                logError("Failed to register state change callback. " + re);
            }
        }
        doBind();
    }

    /* access modifiers changed from: package-private */
    public void disconnect() {
        this.mServiceListener = null;
        IBluetoothManager mgr = BluetoothAdapter.getDefaultAdapter().getBluetoothManager();
        if (mgr != null) {
            try {
                mgr.unregisterStateChangeCallback(this.mBluetoothStateChangeCallback);
            } catch (RemoteException re) {
                logError("Failed to unregister state change callback" + re);
            }
        }
        doUnbind();
    }

    /* access modifiers changed from: package-private */
    public T getService() {
        return this.mService;
    }

    /* access modifiers changed from: private */
    public void logDebug(String log) {
        Log.d(this.mProfileName, log);
    }

    private void logError(String log) {
        Log.e(this.mProfileName, log);
    }
}
