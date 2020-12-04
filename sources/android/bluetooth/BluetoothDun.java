package android.bluetooth;

import android.bluetooth.BluetoothProfile;
import android.bluetooth.IBluetoothDun;
import android.bluetooth.IBluetoothStateChangeCallback;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

public final class BluetoothDun implements BluetoothProfile {
    public static final String ACTION_CONNECTION_STATE_CHANGED = "codeaurora.bluetooth.dun.profile.action.CONNECTION_STATE_CHANGED";
    private static final boolean DBG = false;
    private static final String TAG = "BluetoothDun";
    private static final boolean VDBG = false;
    private BluetoothAdapter mAdapter;
    /* access modifiers changed from: private */
    public ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            IBluetoothDun unused = BluetoothDun.this.mDunService = IBluetoothDun.Stub.asInterface(service);
            if (BluetoothDun.this.mServiceListener != null) {
                BluetoothDun.this.mServiceListener.onServiceConnected(22, BluetoothDun.this);
            }
        }

        public void onServiceDisconnected(ComponentName className) {
            IBluetoothDun unused = BluetoothDun.this.mDunService = null;
            if (BluetoothDun.this.mServiceListener != null) {
                BluetoothDun.this.mServiceListener.onServiceDisconnected(22);
            }
        }
    };
    /* access modifiers changed from: private */
    public Context mContext;
    /* access modifiers changed from: private */
    public IBluetoothDun mDunService;
    /* access modifiers changed from: private */
    public BluetoothProfile.ServiceListener mServiceListener;
    private IBluetoothStateChangeCallback mStateChangeCallback = new IBluetoothStateChangeCallback.Stub() {
        /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void onBluetoothStateChange(boolean r5) {
            /*
                r4 = this;
                java.lang.String r0 = "BluetoothDun"
                java.lang.StringBuilder r1 = new java.lang.StringBuilder
                r1.<init>()
                java.lang.String r2 = "onBluetoothStateChange on: "
                r1.append(r2)
                r1.append(r5)
                java.lang.String r1 = r1.toString()
                android.util.Log.d(r0, r1)
                if (r5 == 0) goto L_0x0043
                android.bluetooth.BluetoothDun r0 = android.bluetooth.BluetoothDun.this     // Catch:{ IllegalStateException -> 0x0039, SecurityException -> 0x002f }
                android.bluetooth.IBluetoothDun r0 = r0.mDunService     // Catch:{ IllegalStateException -> 0x0039, SecurityException -> 0x002f }
                if (r0 != 0) goto L_0x0042
                java.lang.String r0 = "BluetoothDun"
                java.lang.String r1 = "onBluetoothStateChange call bindService"
                android.util.Log.d(r0, r1)     // Catch:{ IllegalStateException -> 0x0039, SecurityException -> 0x002f }
                android.bluetooth.BluetoothDun r0 = android.bluetooth.BluetoothDun.this     // Catch:{ IllegalStateException -> 0x0039, SecurityException -> 0x002f }
                r0.doBind()     // Catch:{ IllegalStateException -> 0x0039, SecurityException -> 0x002f }
                goto L_0x0042
            L_0x002f:
                r0 = move-exception
                java.lang.String r1 = "BluetoothDun"
                java.lang.String r2 = "onBluetoothStateChange: could not bind to DUN service: "
                android.util.Log.e(r1, r2, r0)
                goto L_0x0042
            L_0x0039:
                r0 = move-exception
                java.lang.String r1 = "BluetoothDun"
                java.lang.String r2 = "onBluetoothStateChange: could not bind to DUN service: "
                android.util.Log.e(r1, r2, r0)
            L_0x0042:
                goto L_0x0071
            L_0x0043:
                android.bluetooth.BluetoothDun r0 = android.bluetooth.BluetoothDun.this
                android.content.ServiceConnection r0 = r0.mConnection
                monitor-enter(r0)
                android.bluetooth.BluetoothDun r1 = android.bluetooth.BluetoothDun.this     // Catch:{ all -> 0x0072 }
                android.bluetooth.IBluetoothDun r1 = r1.mDunService     // Catch:{ all -> 0x0072 }
                if (r1 == 0) goto L_0x0070
                android.bluetooth.BluetoothDun r1 = android.bluetooth.BluetoothDun.this     // Catch:{ Exception -> 0x0068 }
                r2 = 0
                android.bluetooth.IBluetoothDun unused = r1.mDunService = r2     // Catch:{ Exception -> 0x0068 }
                android.bluetooth.BluetoothDun r1 = android.bluetooth.BluetoothDun.this     // Catch:{ Exception -> 0x0068 }
                android.content.Context r1 = r1.mContext     // Catch:{ Exception -> 0x0068 }
                android.bluetooth.BluetoothDun r2 = android.bluetooth.BluetoothDun.this     // Catch:{ Exception -> 0x0068 }
                android.content.ServiceConnection r2 = r2.mConnection     // Catch:{ Exception -> 0x0068 }
                r1.unbindService(r2)     // Catch:{ Exception -> 0x0068 }
                goto L_0x0070
            L_0x0068:
                r1 = move-exception
                java.lang.String r2 = "BluetoothDun"
                java.lang.String r3 = ""
                android.util.Log.e(r2, r3, r1)     // Catch:{ all -> 0x0072 }
            L_0x0070:
                monitor-exit(r0)     // Catch:{ all -> 0x0072 }
            L_0x0071:
                return
            L_0x0072:
                r1 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x0072 }
                throw r1
            */
            throw new UnsupportedOperationException("Method not decompiled: android.bluetooth.BluetoothDun.AnonymousClass1.onBluetoothStateChange(boolean):void");
        }
    };

    BluetoothDun(Context context, BluetoothProfile.ServiceListener l) {
        this.mContext = context;
        this.mServiceListener = l;
        this.mAdapter = BluetoothAdapter.getDefaultAdapter();
        try {
            this.mAdapter.getBluetoothManager().registerStateChangeCallback(this.mStateChangeCallback);
        } catch (RemoteException re) {
            Log.w(TAG, "Unable to register BluetoothStateChangeCallback", re);
        }
        Log.d(TAG, "BluetoothDun() call bindService");
        doBind();
    }

    /* access modifiers changed from: package-private */
    public boolean doBind() {
        Intent intent = new Intent(IBluetoothDun.class.getName());
        ComponentName comp = intent.resolveSystemService(this.mContext.getPackageManager(), 0);
        intent.setComponent(comp);
        if (comp != null && this.mContext.bindServiceAsUser(intent, this.mConnection, 0, Process.myUserHandle())) {
            return true;
        }
        Log.e(TAG, "Could not bind to Bluetooth Dun Service with " + intent);
        return false;
    }

    /* access modifiers changed from: package-private */
    public void close() {
        this.mServiceListener = null;
        IBluetoothManager mgr = this.mAdapter.getBluetoothManager();
        if (mgr != null) {
            try {
                mgr.unregisterStateChangeCallback(this.mStateChangeCallback);
            } catch (RemoteException re) {
                Log.w(TAG, "Unable to unregister BluetoothStateChangeCallback", re);
            }
        }
        synchronized (this.mConnection) {
            if (this.mDunService != null) {
                try {
                    this.mDunService = null;
                    this.mContext.unbindService(this.mConnection);
                } catch (Exception re2) {
                    Log.e(TAG, "", re2);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void finalize() {
        close();
    }

    public boolean disconnect(BluetoothDevice device) {
        if (this.mDunService == null || !isEnabled() || !isValidDevice(device)) {
            if (this.mDunService == null) {
                Log.w(TAG, "Proxy not attached to service");
            }
            return false;
        }
        try {
            return this.mDunService.disconnect(device);
        } catch (RemoteException e) {
            Log.e(TAG, "Stack:" + Log.getStackTraceString(new Throwable()));
            return false;
        }
    }

    public List<BluetoothDevice> getConnectedDevices() {
        if (this.mDunService == null || !isEnabled()) {
            if (this.mDunService == null) {
                Log.w(TAG, "Proxy not attached to service");
            }
            return new ArrayList();
        }
        try {
            return this.mDunService.getConnectedDevices();
        } catch (RemoteException e) {
            Log.e(TAG, "Stack:" + Log.getStackTraceString(new Throwable()));
            return new ArrayList();
        }
    }

    public List<BluetoothDevice> getDevicesMatchingConnectionStates(int[] states) {
        if (this.mDunService == null || !isEnabled()) {
            if (this.mDunService == null) {
                Log.w(TAG, "Proxy not attached to service");
            }
            return new ArrayList();
        }
        try {
            return this.mDunService.getDevicesMatchingConnectionStates(states);
        } catch (RemoteException e) {
            Log.e(TAG, "Stack:" + Log.getStackTraceString(new Throwable()));
            return new ArrayList();
        }
    }

    public int getConnectionState(BluetoothDevice device) {
        if (this.mDunService == null || !isEnabled() || !isValidDevice(device)) {
            if (this.mDunService == null) {
                Log.w(TAG, "Proxy not attached to service");
            }
            return 0;
        }
        try {
            return this.mDunService.getConnectionState(device);
        } catch (RemoteException e) {
            Log.e(TAG, "Stack:" + Log.getStackTraceString(new Throwable()));
            return 0;
        }
    }

    private boolean isEnabled() {
        if (this.mAdapter.getState() == 12) {
            return true;
        }
        return false;
    }

    private boolean isValidDevice(BluetoothDevice device) {
        if (device != null && BluetoothAdapter.checkBluetoothAddress(device.getAddress())) {
            return true;
        }
        return false;
    }

    private static void log(String msg) {
        Log.d(TAG, msg);
    }
}
