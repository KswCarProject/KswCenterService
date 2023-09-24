package android.bluetooth;

import android.bluetooth.BluetoothProfile;
import android.bluetooth.IBluetoothAvrcpController;
import android.content.Context;
import android.p007os.Binder;
import android.p007os.IBinder;
import android.p007os.RemoteException;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public final class BluetoothAvrcpController implements BluetoothProfile {
    public static final String ACTION_CONNECTION_STATE_CHANGED = "android.bluetooth.avrcp-controller.profile.action.CONNECTION_STATE_CHANGED";
    public static final String ACTION_PLAYER_SETTING = "android.bluetooth.avrcp-controller.profile.action.PLAYER_SETTING";
    private static final boolean DBG = false;
    public static final String EXTRA_PLAYER_SETTING = "android.bluetooth.avrcp-controller.profile.extra.PLAYER_SETTING";
    private static final String TAG = "BluetoothAvrcpController";
    private static final boolean VDBG = false;
    private final BluetoothProfileConnector<IBluetoothAvrcpController> mProfileConnector = new BluetoothProfileConnector(this, 12, TAG, IBluetoothAvrcpController.class.getName()) { // from class: android.bluetooth.BluetoothAvrcpController.1
        @Override // android.bluetooth.BluetoothProfileConnector
        public IBluetoothAvrcpController getServiceInterface(IBinder service) {
            return IBluetoothAvrcpController.Stub.asInterface(Binder.allowBlocking(service));
        }
    };
    private BluetoothAdapter mAdapter = BluetoothAdapter.getDefaultAdapter();

    BluetoothAvrcpController(Context context, BluetoothProfile.ServiceListener listener) {
        this.mProfileConnector.connect(context, listener);
    }

    void close() {
        this.mProfileConnector.disconnect();
    }

    private IBluetoothAvrcpController getService() {
        return this.mProfileConnector.getService();
    }

    public void finalize() {
        close();
    }

    @Override // android.bluetooth.BluetoothProfile
    public List<BluetoothDevice> getConnectedDevices() {
        IBluetoothAvrcpController service = getService();
        if (service != null && isEnabled()) {
            try {
                return service.getConnectedDevices();
            } catch (RemoteException e) {
                Log.m70e(TAG, "Stack:" + Log.getStackTraceString(new Throwable()));
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
        IBluetoothAvrcpController service = getService();
        if (service != null && isEnabled()) {
            try {
                return service.getDevicesMatchingConnectionStates(states);
            } catch (RemoteException e) {
                Log.m70e(TAG, "Stack:" + Log.getStackTraceString(new Throwable()));
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
        IBluetoothAvrcpController service = getService();
        if (service != null && isEnabled() && isValidDevice(device)) {
            try {
                return service.getConnectionState(device);
            } catch (RemoteException e) {
                Log.m70e(TAG, "Stack:" + Log.getStackTraceString(new Throwable()));
                return 0;
            }
        }
        if (service == null) {
            Log.m64w(TAG, "Proxy not attached to service");
        }
        return 0;
    }

    public BluetoothAvrcpPlayerSettings getPlayerSettings(BluetoothDevice device) {
        IBluetoothAvrcpController service = getService();
        if (service == null || !isEnabled()) {
            return null;
        }
        try {
            BluetoothAvrcpPlayerSettings settings = service.getPlayerSettings(device);
            return settings;
        } catch (RemoteException e) {
            Log.m70e(TAG, "Error talking to BT service in getMetadata() " + e);
            return null;
        }
    }

    public boolean setPlayerApplicationSetting(BluetoothAvrcpPlayerSettings plAppSetting) {
        IBluetoothAvrcpController service = getService();
        if (service != null && isEnabled()) {
            try {
                return service.setPlayerApplicationSetting(plAppSetting);
            } catch (RemoteException e) {
                Log.m70e(TAG, "Error talking to BT service in setPlayerApplicationSetting() " + e);
                return false;
            }
        }
        if (service == null) {
            Log.m64w(TAG, "Proxy not attached to service");
        }
        return false;
    }

    public void sendGroupNavigationCmd(BluetoothDevice device, int keyCode, int keyState) {
        Log.m72d(TAG, "sendGroupNavigationCmd dev = " + device + " key " + keyCode + " State = " + keyState);
        IBluetoothAvrcpController service = getService();
        if (service != null && isEnabled()) {
            try {
                service.sendGroupNavigationCmd(device, keyCode, keyState);
            } catch (RemoteException e) {
                Log.m69e(TAG, "Error talking to BT service in sendGroupNavigationCmd()", e);
            }
        } else if (service == null) {
            Log.m64w(TAG, "Proxy not attached to service");
        }
    }

    private boolean isEnabled() {
        return this.mAdapter.getState() == 12;
    }

    private static boolean isValidDevice(BluetoothDevice device) {
        return device != null && BluetoothAdapter.checkBluetoothAddress(device.getAddress());
    }

    private static void log(String msg) {
        Log.m72d(TAG, msg);
    }
}
