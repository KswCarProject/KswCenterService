package android.bluetooth;

import android.bluetooth.BluetoothProfile;
import android.bluetooth.IBluetoothPbapClient;
import android.content.Context;
import android.p007os.Binder;
import android.p007os.IBinder;
import android.p007os.RemoteException;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public final class BluetoothPbapClient implements BluetoothProfile {
    public static final String ACTION_CONNECTION_STATE_CHANGED = "android.bluetooth.pbapclient.profile.action.CONNECTION_STATE_CHANGED";
    private static final boolean DBG = false;
    public static final int RESULT_CANCELED = 2;
    public static final int RESULT_FAILURE = 0;
    public static final int RESULT_SUCCESS = 1;
    public static final int STATE_ERROR = -1;
    private static final String TAG = "BluetoothPbapClient";
    private static final boolean VDBG = false;
    private final BluetoothProfileConnector<IBluetoothPbapClient> mProfileConnector = new BluetoothProfileConnector(this, 17, TAG, IBluetoothPbapClient.class.getName()) { // from class: android.bluetooth.BluetoothPbapClient.1
        @Override // android.bluetooth.BluetoothProfileConnector
        public IBluetoothPbapClient getServiceInterface(IBinder service) {
            return IBluetoothPbapClient.Stub.asInterface(Binder.allowBlocking(service));
        }
    };
    private BluetoothAdapter mAdapter = BluetoothAdapter.getDefaultAdapter();

    BluetoothPbapClient(Context context, BluetoothProfile.ServiceListener listener) {
        this.mProfileConnector.connect(context, listener);
    }

    protected void finalize() throws Throwable {
        try {
            close();
        } finally {
            super.finalize();
        }
    }

    public synchronized void close() {
        this.mProfileConnector.disconnect();
    }

    private IBluetoothPbapClient getService() {
        return this.mProfileConnector.getService();
    }

    public boolean connect(BluetoothDevice device) {
        IBluetoothPbapClient service = getService();
        if (service != null && isEnabled() && isValidDevice(device)) {
            try {
                return service.connect(device);
            } catch (RemoteException e) {
                Log.m70e(TAG, Log.getStackTraceString(new Throwable()));
                return false;
            }
        }
        if (service == null) {
            Log.m64w(TAG, "Proxy not attached to service");
        }
        return false;
    }

    public boolean disconnect(BluetoothDevice device) {
        IBluetoothPbapClient service = getService();
        if (service != null && isEnabled() && isValidDevice(device)) {
            try {
                service.disconnect(device);
                return true;
            } catch (RemoteException e) {
                Log.m70e(TAG, Log.getStackTraceString(new Throwable()));
                return false;
            }
        }
        if (service == null) {
            Log.m64w(TAG, "Proxy not attached to service");
        }
        return false;
    }

    @Override // android.bluetooth.BluetoothProfile
    public List<BluetoothDevice> getConnectedDevices() {
        IBluetoothPbapClient service = getService();
        if (service != null && isEnabled()) {
            try {
                return service.getConnectedDevices();
            } catch (RemoteException e) {
                Log.m70e(TAG, Log.getStackTraceString(new Throwable()));
                return new ArrayList();
            }
        }
        if (service == null) {
            Log.m64w(TAG, "Proxy not attached to service");
        }
        return new ArrayList();
    }

    @Override // android.bluetooth.BluetoothProfile
    public List<BluetoothDevice> getDevicesMatchingConnectionStates(int[] states) {
        IBluetoothPbapClient service = getService();
        if (service != null && isEnabled()) {
            try {
                return service.getDevicesMatchingConnectionStates(states);
            } catch (RemoteException e) {
                Log.m70e(TAG, Log.getStackTraceString(new Throwable()));
                return new ArrayList();
            }
        }
        if (service == null) {
            Log.m64w(TAG, "Proxy not attached to service");
        }
        return new ArrayList();
    }

    @Override // android.bluetooth.BluetoothProfile
    public int getConnectionState(BluetoothDevice device) {
        IBluetoothPbapClient service = getService();
        if (service != null && isEnabled() && isValidDevice(device)) {
            try {
                return service.getConnectionState(device);
            } catch (RemoteException e) {
                Log.m70e(TAG, Log.getStackTraceString(new Throwable()));
                return 0;
            }
        }
        if (service == null) {
            Log.m64w(TAG, "Proxy not attached to service");
        }
        return 0;
    }

    private static void log(String msg) {
        Log.m72d(TAG, msg);
    }

    private boolean isEnabled() {
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        if (adapter != null && adapter.getState() == 12) {
            return true;
        }
        log("Bluetooth is Not enabled");
        return false;
    }

    private static boolean isValidDevice(BluetoothDevice device) {
        return device != null && BluetoothAdapter.checkBluetoothAddress(device.getAddress());
    }

    public boolean setPriority(BluetoothDevice device, int priority) {
        IBluetoothPbapClient service = getService();
        if (service != null && isEnabled() && isValidDevice(device)) {
            if (priority != 0 && priority != 100) {
                return false;
            }
            try {
                return service.setPriority(device, priority);
            } catch (RemoteException e) {
                Log.m70e(TAG, Log.getStackTraceString(new Throwable()));
                return false;
            }
        }
        if (service == null) {
            Log.m64w(TAG, "Proxy not attached to service");
        }
        return false;
    }

    public int getPriority(BluetoothDevice device) {
        IBluetoothPbapClient service = getService();
        if (service != null && isEnabled() && isValidDevice(device)) {
            try {
                return service.getPriority(device);
            } catch (RemoteException e) {
                Log.m70e(TAG, Log.getStackTraceString(new Throwable()));
                return 0;
            }
        }
        if (service == null) {
            Log.m64w(TAG, "Proxy not attached to service");
        }
        return 0;
    }
}
