package android.bluetooth;

import android.annotation.UnsupportedAppUsage;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.IBluetoothA2dpSink;
import android.content.Context;
import android.p007os.Binder;
import android.p007os.IBinder;
import android.p007os.RemoteException;
import android.telephony.ims.ImsConferenceState;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public final class BluetoothA2dpSink implements BluetoothProfile {
    public static final String ACTION_AUDIO_CONFIG_CHANGED = "android.bluetooth.a2dp-sink.profile.action.AUDIO_CONFIG_CHANGED";
    public static final String ACTION_CONNECTION_STATE_CHANGED = "android.bluetooth.a2dp-sink.profile.action.CONNECTION_STATE_CHANGED";
    public static final String ACTION_PLAYING_STATE_CHANGED = "android.bluetooth.a2dp-sink.profile.action.PLAYING_STATE_CHANGED";
    private static final boolean DBG = true;
    public static final String EXTRA_AUDIO_CONFIG = "android.bluetooth.a2dp-sink.profile.extra.AUDIO_CONFIG";
    public static final int STATE_NOT_PLAYING = 11;
    public static final int STATE_PLAYING = 10;
    private static final String TAG = "BluetoothA2dpSink";
    private static final boolean VDBG = false;
    private final BluetoothProfileConnector<IBluetoothA2dpSink> mProfileConnector = new BluetoothProfileConnector(this, 11, TAG, IBluetoothA2dpSink.class.getName()) { // from class: android.bluetooth.BluetoothA2dpSink.1
        @Override // android.bluetooth.BluetoothProfileConnector
        public IBluetoothA2dpSink getServiceInterface(IBinder service) {
            return IBluetoothA2dpSink.Stub.asInterface(Binder.allowBlocking(service));
        }
    };
    private BluetoothAdapter mAdapter = BluetoothAdapter.getDefaultAdapter();

    BluetoothA2dpSink(Context context, BluetoothProfile.ServiceListener listener) {
        this.mProfileConnector.connect(context, listener);
    }

    void close() {
        this.mProfileConnector.disconnect();
    }

    private IBluetoothA2dpSink getService() {
        return this.mProfileConnector.getService();
    }

    public void finalize() {
        close();
    }

    public boolean connect(BluetoothDevice device) {
        log("connect(" + device + ")");
        IBluetoothA2dpSink service = getService();
        if (service != null && isEnabled() && isValidDevice(device)) {
            try {
                return service.connect(device);
            } catch (RemoteException e) {
                Log.m70e(TAG, "Stack:" + Log.getStackTraceString(new Throwable()));
                return false;
            }
        }
        if (service == null) {
            Log.m64w(TAG, "Proxy not attached to service");
        }
        return false;
    }

    @UnsupportedAppUsage
    public boolean disconnect(BluetoothDevice device) {
        log("disconnect(" + device + ")");
        IBluetoothA2dpSink service = getService();
        if (service != null && isEnabled() && isValidDevice(device)) {
            try {
                return service.disconnect(device);
            } catch (RemoteException e) {
                Log.m70e(TAG, "Stack:" + Log.getStackTraceString(new Throwable()));
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
        IBluetoothA2dpSink service = getService();
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
        IBluetoothA2dpSink service = getService();
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
        IBluetoothA2dpSink service = getService();
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

    public BluetoothAudioConfig getAudioConfig(BluetoothDevice device) {
        IBluetoothA2dpSink service = getService();
        if (service != null && isEnabled() && isValidDevice(device)) {
            try {
                return service.getAudioConfig(device);
            } catch (RemoteException e) {
                Log.m70e(TAG, "Stack:" + Log.getStackTraceString(new Throwable()));
                return null;
            }
        }
        if (service == null) {
            Log.m64w(TAG, "Proxy not attached to service");
        }
        return null;
    }

    public boolean setPriority(BluetoothDevice device, int priority) {
        log("setPriority(" + device + ", " + priority + ")");
        IBluetoothA2dpSink service = getService();
        if (service != null && isEnabled() && isValidDevice(device)) {
            if (priority != 0 && priority != 100) {
                return false;
            }
            try {
                return service.setPriority(device, priority);
            } catch (RemoteException e) {
                Log.m70e(TAG, "Stack:" + Log.getStackTraceString(new Throwable()));
                return false;
            }
        }
        if (service == null) {
            Log.m64w(TAG, "Proxy not attached to service");
        }
        return false;
    }

    public int getPriority(BluetoothDevice device) {
        IBluetoothA2dpSink service = getService();
        if (service != null && isEnabled() && isValidDevice(device)) {
            try {
                return service.getPriority(device);
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

    public boolean isA2dpPlaying(BluetoothDevice device) {
        IBluetoothA2dpSink service = getService();
        if (service != null && isEnabled() && isValidDevice(device)) {
            try {
                return service.isA2dpPlaying(device);
            } catch (RemoteException e) {
                Log.m70e(TAG, "Stack:" + Log.getStackTraceString(new Throwable()));
                return false;
            }
        }
        if (service == null) {
            Log.m64w(TAG, "Proxy not attached to service");
        }
        return false;
    }

    public static String stateToString(int state) {
        switch (state) {
            case 0:
                return ImsConferenceState.STATUS_DISCONNECTED;
            case 1:
                return "connecting";
            case 2:
                return "connected";
            case 3:
                return ImsConferenceState.STATUS_DISCONNECTING;
            default:
                switch (state) {
                    case 10:
                        return "playing";
                    case 11:
                        return "not playing";
                    default:
                        return "<unknown state " + state + ">";
                }
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
