package android.bluetooth;

import android.bluetooth.BluetoothProfile;
import android.bluetooth.IBluetoothDun;
import android.bluetooth.IBluetoothStateChangeCallback;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.p007os.IBinder;
import android.p007os.Process;
import android.p007os.RemoteException;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public final class BluetoothDun implements BluetoothProfile {
    public static final String ACTION_CONNECTION_STATE_CHANGED = "codeaurora.bluetooth.dun.profile.action.CONNECTION_STATE_CHANGED";
    private static final boolean DBG = false;
    private static final String TAG = "BluetoothDun";
    private static final boolean VDBG = false;
    private Context mContext;
    private IBluetoothDun mDunService;
    private BluetoothProfile.ServiceListener mServiceListener;
    private IBluetoothStateChangeCallback mStateChangeCallback = new IBluetoothStateChangeCallback.Stub() { // from class: android.bluetooth.BluetoothDun.1
        @Override // android.bluetooth.IBluetoothStateChangeCallback
        public void onBluetoothStateChange(boolean on) {
            Log.m72d(BluetoothDun.TAG, "onBluetoothStateChange on: " + on);
            if (on) {
                try {
                    if (BluetoothDun.this.mDunService == null) {
                        Log.m72d(BluetoothDun.TAG, "onBluetoothStateChange call bindService");
                        BluetoothDun.this.doBind();
                        return;
                    }
                    return;
                } catch (IllegalStateException e) {
                    Log.m69e(BluetoothDun.TAG, "onBluetoothStateChange: could not bind to DUN service: ", e);
                    return;
                } catch (SecurityException e2) {
                    Log.m69e(BluetoothDun.TAG, "onBluetoothStateChange: could not bind to DUN service: ", e2);
                    return;
                }
            }
            synchronized (BluetoothDun.this.mConnection) {
                if (BluetoothDun.this.mDunService != null) {
                    try {
                        BluetoothDun.this.mDunService = null;
                        BluetoothDun.this.mContext.unbindService(BluetoothDun.this.mConnection);
                    } catch (Exception re) {
                        Log.m69e(BluetoothDun.TAG, "", re);
                    }
                }
            }
        }
    };
    private ServiceConnection mConnection = new ServiceConnection() { // from class: android.bluetooth.BluetoothDun.2
        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName className, IBinder service) {
            BluetoothDun.this.mDunService = IBluetoothDun.Stub.asInterface(service);
            if (BluetoothDun.this.mServiceListener != null) {
                BluetoothDun.this.mServiceListener.onServiceConnected(22, BluetoothDun.this);
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName className) {
            BluetoothDun.this.mDunService = null;
            if (BluetoothDun.this.mServiceListener != null) {
                BluetoothDun.this.mServiceListener.onServiceDisconnected(22);
            }
        }
    };
    private BluetoothAdapter mAdapter = BluetoothAdapter.getDefaultAdapter();

    BluetoothDun(Context context, BluetoothProfile.ServiceListener l) {
        this.mContext = context;
        this.mServiceListener = l;
        try {
            this.mAdapter.getBluetoothManager().registerStateChangeCallback(this.mStateChangeCallback);
        } catch (RemoteException re) {
            Log.m63w(TAG, "Unable to register BluetoothStateChangeCallback", re);
        }
        Log.m72d(TAG, "BluetoothDun() call bindService");
        doBind();
    }

    boolean doBind() {
        Intent intent = new Intent(IBluetoothDun.class.getName());
        ComponentName comp = intent.resolveSystemService(this.mContext.getPackageManager(), 0);
        intent.setComponent(comp);
        if (comp == null || !this.mContext.bindServiceAsUser(intent, this.mConnection, 0, Process.myUserHandle())) {
            Log.m70e(TAG, "Could not bind to Bluetooth Dun Service with " + intent);
            return false;
        }
        return true;
    }

    void close() {
        this.mServiceListener = null;
        IBluetoothManager mgr = this.mAdapter.getBluetoothManager();
        if (mgr != null) {
            try {
                mgr.unregisterStateChangeCallback(this.mStateChangeCallback);
            } catch (RemoteException re) {
                Log.m63w(TAG, "Unable to unregister BluetoothStateChangeCallback", re);
            }
        }
        synchronized (this.mConnection) {
            if (this.mDunService != null) {
                try {
                    this.mDunService = null;
                    this.mContext.unbindService(this.mConnection);
                } catch (Exception re2) {
                    Log.m69e(TAG, "", re2);
                }
            }
        }
    }

    protected void finalize() {
        close();
    }

    public boolean disconnect(BluetoothDevice device) {
        if (this.mDunService != null && isEnabled() && isValidDevice(device)) {
            try {
                return this.mDunService.disconnect(device);
            } catch (RemoteException e) {
                Log.m70e(TAG, "Stack:" + Log.getStackTraceString(new Throwable()));
                return false;
            }
        }
        if (this.mDunService == null) {
            Log.m64w(TAG, "Proxy not attached to service");
        }
        return false;
    }

    @Override // android.bluetooth.BluetoothProfile
    public List<BluetoothDevice> getConnectedDevices() {
        if (this.mDunService != null && isEnabled()) {
            try {
                return this.mDunService.getConnectedDevices();
            } catch (RemoteException e) {
                Log.m70e(TAG, "Stack:" + Log.getStackTraceString(new Throwable()));
                return new ArrayList();
            }
        }
        if (this.mDunService == null) {
            Log.m64w(TAG, "Proxy not attached to service");
        }
        return new ArrayList();
    }

    @Override // android.bluetooth.BluetoothProfile
    public List<BluetoothDevice> getDevicesMatchingConnectionStates(int[] states) {
        if (this.mDunService != null && isEnabled()) {
            try {
                return this.mDunService.getDevicesMatchingConnectionStates(states);
            } catch (RemoteException e) {
                Log.m70e(TAG, "Stack:" + Log.getStackTraceString(new Throwable()));
                return new ArrayList();
            }
        }
        if (this.mDunService == null) {
            Log.m64w(TAG, "Proxy not attached to service");
        }
        return new ArrayList();
    }

    @Override // android.bluetooth.BluetoothProfile
    public int getConnectionState(BluetoothDevice device) {
        if (this.mDunService != null && isEnabled() && isValidDevice(device)) {
            try {
                return this.mDunService.getConnectionState(device);
            } catch (RemoteException e) {
                Log.m70e(TAG, "Stack:" + Log.getStackTraceString(new Throwable()));
                return 0;
            }
        }
        if (this.mDunService == null) {
            Log.m64w(TAG, "Proxy not attached to service");
        }
        return 0;
    }

    private boolean isEnabled() {
        return this.mAdapter.getState() == 12;
    }

    private boolean isValidDevice(BluetoothDevice device) {
        return device != null && BluetoothAdapter.checkBluetoothAddress(device.getAddress());
    }

    private static void log(String msg) {
        Log.m72d(TAG, msg);
    }
}
