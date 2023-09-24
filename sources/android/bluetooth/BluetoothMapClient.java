package android.bluetooth;

import android.annotation.UnsupportedAppUsage;
import android.app.PendingIntent;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.IBluetoothMapClient;
import android.content.Context;
import android.net.Uri;
import android.p007os.Binder;
import android.p007os.IBinder;
import android.p007os.RemoteException;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public final class BluetoothMapClient implements BluetoothProfile {
    public static final String ACTION_CONNECTION_STATE_CHANGED = "android.bluetooth.mapmce.profile.action.CONNECTION_STATE_CHANGED";
    public static final String ACTION_MESSAGE_DELIVERED_SUCCESSFULLY = "android.bluetooth.mapmce.profile.action.MESSAGE_DELIVERED_SUCCESSFULLY";
    public static final String ACTION_MESSAGE_RECEIVED = "android.bluetooth.mapmce.profile.action.MESSAGE_RECEIVED";
    public static final String ACTION_MESSAGE_SENT_SUCCESSFULLY = "android.bluetooth.mapmce.profile.action.MESSAGE_SENT_SUCCESSFULLY";
    public static final String EXTRA_MESSAGE_HANDLE = "android.bluetooth.mapmce.profile.extra.MESSAGE_HANDLE";
    public static final String EXTRA_MESSAGE_READ_STATUS = "android.bluetooth.mapmce.profile.extra.MESSAGE_READ_STATUS";
    public static final String EXTRA_MESSAGE_TIMESTAMP = "android.bluetooth.mapmce.profile.extra.MESSAGE_TIMESTAMP";
    public static final String EXTRA_SENDER_CONTACT_NAME = "android.bluetooth.mapmce.profile.extra.SENDER_CONTACT_NAME";
    public static final String EXTRA_SENDER_CONTACT_URI = "android.bluetooth.mapmce.profile.extra.SENDER_CONTACT_URI";
    public static final int RESULT_CANCELED = 2;
    public static final int RESULT_FAILURE = 0;
    public static final int RESULT_SUCCESS = 1;
    public static final int STATE_ERROR = -1;
    private static final int UPLOADING_FEATURE_BITMASK = 8;
    private BluetoothAdapter mAdapter;
    private final BluetoothProfileConnector<IBluetoothMapClient> mProfileConnector = new BluetoothProfileConnector(this, 18, TAG, IBluetoothMapClient.class.getName()) { // from class: android.bluetooth.BluetoothMapClient.1
        @Override // android.bluetooth.BluetoothProfileConnector
        public IBluetoothMapClient getServiceInterface(IBinder service) {
            return IBluetoothMapClient.Stub.asInterface(Binder.allowBlocking(service));
        }
    };
    private static final String TAG = "BluetoothMapClient";
    private static final boolean DBG = Log.isLoggable(TAG, 3);
    private static final boolean VDBG = Log.isLoggable(TAG, 2);

    BluetoothMapClient(Context context, BluetoothProfile.ServiceListener listener) {
        if (DBG) {
            Log.m72d(TAG, "Create BluetoothMapClient proxy object");
        }
        this.mAdapter = BluetoothAdapter.getDefaultAdapter();
        this.mProfileConnector.connect(context, listener);
    }

    protected void finalize() throws Throwable {
        try {
            close();
        } finally {
            super.finalize();
        }
    }

    public void close() {
        this.mProfileConnector.disconnect();
    }

    private IBluetoothMapClient getService() {
        return this.mProfileConnector.getService();
    }

    public boolean isConnected(BluetoothDevice device) {
        if (VDBG) {
            Log.m72d(TAG, "isConnected(" + device + ")");
        }
        IBluetoothMapClient service = getService();
        if (service != null) {
            try {
                return service.isConnected(device);
            } catch (RemoteException e) {
                Log.m70e(TAG, e.toString());
                return false;
            }
        }
        Log.m64w(TAG, "Proxy not attached to service");
        if (DBG) {
            Log.m72d(TAG, Log.getStackTraceString(new Throwable()));
            return false;
        }
        return false;
    }

    public boolean connect(BluetoothDevice device) {
        if (DBG) {
            Log.m72d(TAG, "connect(" + device + ")for MAPS MCE");
        }
        IBluetoothMapClient service = getService();
        if (service != null) {
            try {
                return service.connect(device);
            } catch (RemoteException e) {
                Log.m70e(TAG, e.toString());
                return false;
            }
        }
        Log.m64w(TAG, "Proxy not attached to service");
        if (DBG) {
            Log.m72d(TAG, Log.getStackTraceString(new Throwable()));
            return false;
        }
        return false;
    }

    public boolean disconnect(BluetoothDevice device) {
        if (DBG) {
            Log.m72d(TAG, "disconnect(" + device + ")");
        }
        IBluetoothMapClient service = getService();
        if (service != null && isEnabled() && isValidDevice(device)) {
            try {
                return service.disconnect(device);
            } catch (RemoteException e) {
                Log.m70e(TAG, Log.getStackTraceString(new Throwable()));
            }
        }
        if (service == null) {
            Log.m64w(TAG, "Proxy not attached to service");
            return false;
        }
        return false;
    }

    @Override // android.bluetooth.BluetoothProfile
    public List<BluetoothDevice> getConnectedDevices() {
        if (DBG) {
            Log.m72d(TAG, "getConnectedDevices()");
        }
        IBluetoothMapClient service = getService();
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
        if (DBG) {
            Log.m72d(TAG, "getDevicesMatchingStates()");
        }
        IBluetoothMapClient service = getService();
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
        if (DBG) {
            Log.m72d(TAG, "getConnectionState(" + device + ")");
        }
        IBluetoothMapClient service = getService();
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

    public boolean setPriority(BluetoothDevice device, int priority) {
        if (DBG) {
            Log.m72d(TAG, "setPriority(" + device + ", " + priority + ")");
        }
        IBluetoothMapClient service = getService();
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
        if (VDBG) {
            Log.m72d(TAG, "getPriority(" + device + ")");
        }
        IBluetoothMapClient service = getService();
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

    @UnsupportedAppUsage
    public boolean sendMessage(BluetoothDevice device, Uri[] contacts, String message, PendingIntent sentIntent, PendingIntent deliveredIntent) {
        if (DBG) {
            Log.m72d(TAG, "sendMessage(" + device + ", " + contacts + ", " + message);
        }
        IBluetoothMapClient service = getService();
        if (service == null || !isEnabled() || !isValidDevice(device)) {
            return false;
        }
        try {
            return service.sendMessage(device, contacts, message, sentIntent, deliveredIntent);
        } catch (RemoteException e) {
            Log.m70e(TAG, Log.getStackTraceString(new Throwable()));
            return false;
        }
    }

    public boolean getUnreadMessages(BluetoothDevice device) {
        if (DBG) {
            Log.m72d(TAG, "getUnreadMessages(" + device + ")");
        }
        IBluetoothMapClient service = getService();
        if (service == null || !isEnabled() || !isValidDevice(device)) {
            return false;
        }
        try {
            return service.getUnreadMessages(device);
        } catch (RemoteException e) {
            Log.m70e(TAG, Log.getStackTraceString(new Throwable()));
            return false;
        }
    }

    public boolean isUploadingSupported(BluetoothDevice device) {
        IBluetoothMapClient service = getService();
        if (service == null) {
            return false;
        }
        try {
            if (isEnabled() && isValidDevice(device)) {
                return (service.getSupportedFeatures(device) & 8) > 0;
            }
            return false;
        } catch (RemoteException e) {
            Log.m70e(TAG, e.getMessage());
            return false;
        }
    }

    private boolean isEnabled() {
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        if (adapter == null || adapter.getState() != 12) {
            if (DBG) {
                Log.m72d(TAG, "Bluetooth is Not enabled");
                return false;
            }
            return false;
        }
        return true;
    }

    private static boolean isValidDevice(BluetoothDevice device) {
        return device != null && BluetoothAdapter.checkBluetoothAddress(device.getAddress());
    }
}
