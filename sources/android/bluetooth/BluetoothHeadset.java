package android.bluetooth;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.IBluetoothHeadset;
import android.bluetooth.IBluetoothProfileServiceConnection;
import android.bluetooth.IBluetoothStateChangeCallback;
import android.content.ComponentName;
import android.content.Context;
import android.p007os.Binder;
import android.p007os.Handler;
import android.p007os.IBinder;
import android.p007os.Looper;
import android.p007os.Message;
import android.p007os.RemoteException;
import android.util.Log;
import com.android.internal.C3132R;
import com.android.internal.annotations.GuardedBy;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/* loaded from: classes.dex */
public final class BluetoothHeadset implements BluetoothProfile {
    @UnsupportedAppUsage
    public static final String ACTION_ACTIVE_DEVICE_CHANGED = "android.bluetooth.headset.profile.action.ACTIVE_DEVICE_CHANGED";
    public static final String ACTION_AUDIO_STATE_CHANGED = "android.bluetooth.headset.profile.action.AUDIO_STATE_CHANGED";
    public static final String ACTION_CONNECTION_STATE_CHANGED = "android.bluetooth.headset.profile.action.CONNECTION_STATE_CHANGED";
    public static final String ACTION_HF_INDICATORS_VALUE_CHANGED = "android.bluetooth.headset.action.HF_INDICATORS_VALUE_CHANGED";
    public static final String ACTION_HF_TWSP_BATTERY_STATE_CHANGED = "android.bluetooth.headset.action.HF_TWSP_BATTERY_STATE_CHANGED";
    public static final String ACTION_VENDOR_SPECIFIC_HEADSET_EVENT = "android.bluetooth.headset.action.VENDOR_SPECIFIC_HEADSET_EVENT";
    public static final int AT_CMD_TYPE_ACTION = 4;
    public static final int AT_CMD_TYPE_BASIC = 3;
    public static final int AT_CMD_TYPE_READ = 0;
    public static final int AT_CMD_TYPE_SET = 2;
    public static final int AT_CMD_TYPE_TEST = 1;
    private static final boolean DBG = true;
    public static final String EXTRA_HF_INDICATORS_IND_ID = "android.bluetooth.headset.extra.HF_INDICATORS_IND_ID";
    public static final String EXTRA_HF_INDICATORS_IND_VALUE = "android.bluetooth.headset.extra.HF_INDICATORS_IND_VALUE";
    public static final String EXTRA_HF_TWSP_BATTERY_LEVEL = "android.bluetooth.headset.extra.HF_TWSP_BATTERY_LEVEL";
    public static final String EXTRA_HF_TWSP_BATTERY_STATE = "android.bluetooth.headset.extra.HF_TWSP_BATTERY_STATE";
    public static final String EXTRA_VENDOR_SPECIFIC_HEADSET_EVENT_ARGS = "android.bluetooth.headset.extra.VENDOR_SPECIFIC_HEADSET_EVENT_ARGS";
    public static final String EXTRA_VENDOR_SPECIFIC_HEADSET_EVENT_CMD = "android.bluetooth.headset.extra.VENDOR_SPECIFIC_HEADSET_EVENT_CMD";
    public static final String EXTRA_VENDOR_SPECIFIC_HEADSET_EVENT_CMD_TYPE = "android.bluetooth.headset.extra.VENDOR_SPECIFIC_HEADSET_EVENT_CMD_TYPE";
    private static final int MESSAGE_HEADSET_SERVICE_CONNECTED = 100;
    private static final int MESSAGE_HEADSET_SERVICE_DISCONNECTED = 101;
    public static final int STATE_AUDIO_CONNECTED = 12;
    public static final int STATE_AUDIO_CONNECTING = 11;
    public static final int STATE_AUDIO_DISCONNECTED = 10;
    private static final String TAG = "BluetoothHeadset";
    private static final boolean VDBG = false;
    public static final String VENDOR_RESULT_CODE_COMMAND_ANDROID = "+ANDROID";
    public static final String VENDOR_SPECIFIC_HEADSET_EVENT_COMPANY_ID_CATEGORY = "android.bluetooth.headset.intent.category.companyid";
    public static final String VENDOR_SPECIFIC_HEADSET_EVENT_IPHONEACCEV = "+IPHONEACCEV";
    public static final int VENDOR_SPECIFIC_HEADSET_EVENT_IPHONEACCEV_BATTERY_LEVEL = 1;
    public static final String VENDOR_SPECIFIC_HEADSET_EVENT_XAPL = "+XAPL";
    public static final String VENDOR_SPECIFIC_HEADSET_EVENT_XEVENT = "+XEVENT";
    public static final String VENDOR_SPECIFIC_HEADSET_EVENT_XEVENT_BATTERY_LEVEL = "BATTERY";
    private Context mContext;
    @GuardedBy({"mServiceLock"})
    private IBluetoothHeadset mService;
    private BluetoothProfile.ServiceListener mServiceListener;
    private final ReentrantReadWriteLock mServiceLock = new ReentrantReadWriteLock();
    private final IBluetoothStateChangeCallback mBluetoothStateChangeCallback = new IBluetoothStateChangeCallback.Stub() { // from class: android.bluetooth.BluetoothHeadset.1
        @Override // android.bluetooth.IBluetoothStateChangeCallback
        public void onBluetoothStateChange(boolean up) {
            Log.m72d(BluetoothHeadset.TAG, "onBluetoothStateChange: up=" + up);
            if (!up) {
                BluetoothHeadset.this.doUnbind();
            } else {
                BluetoothHeadset.this.doBind();
            }
        }
    };
    private final IBluetoothProfileServiceConnection mConnection = new IBluetoothProfileServiceConnection.Stub() { // from class: android.bluetooth.BluetoothHeadset.2
        @Override // android.bluetooth.IBluetoothProfileServiceConnection
        public void onServiceConnected(ComponentName className, IBinder service) {
            Log.m72d(BluetoothHeadset.TAG, "Proxy object connected");
            try {
                BluetoothHeadset.this.mServiceLock.writeLock().lock();
                BluetoothHeadset.this.mService = IBluetoothHeadset.Stub.asInterface(Binder.allowBlocking(service));
                BluetoothHeadset.this.mHandler.sendMessage(BluetoothHeadset.this.mHandler.obtainMessage(100));
            } finally {
                BluetoothHeadset.this.mServiceLock.writeLock().unlock();
            }
        }

        @Override // android.bluetooth.IBluetoothProfileServiceConnection
        public void onServiceDisconnected(ComponentName className) {
            Log.m72d(BluetoothHeadset.TAG, "Proxy object disconnected");
            try {
                BluetoothHeadset.this.mServiceLock.writeLock().lock();
                BluetoothHeadset.this.mHandler.sendMessage(BluetoothHeadset.this.mHandler.obtainMessage(101));
            } finally {
                BluetoothHeadset.this.mServiceLock.writeLock().unlock();
            }
        }
    };
    private final Handler mHandler = new Handler(Looper.getMainLooper()) { // from class: android.bluetooth.BluetoothHeadset.3
        @Override // android.p007os.Handler
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 100:
                    if (BluetoothHeadset.this.mServiceListener != null) {
                        BluetoothHeadset.this.mServiceListener.onServiceConnected(1, BluetoothHeadset.this);
                        return;
                    }
                    return;
                case 101:
                    if (BluetoothHeadset.this.mServiceListener != null) {
                        BluetoothHeadset.this.mServiceListener.onServiceDisconnected(1);
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    };
    private BluetoothAdapter mAdapter = BluetoothAdapter.getDefaultAdapter();

    BluetoothHeadset(Context context, BluetoothProfile.ServiceListener l) {
        this.mContext = context;
        this.mServiceListener = l;
        IBluetoothManager mgr = this.mAdapter.getBluetoothManager();
        if (mgr != null) {
            try {
                mgr.registerStateChangeCallback(this.mBluetoothStateChangeCallback);
            } catch (RemoteException e) {
                Log.m69e(TAG, "", e);
            }
        }
        doBind();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean doBind() {
        synchronized (this.mConnection) {
            if (this.mService == null) {
                Log.m72d(TAG, "Binding service...");
                try {
                    return this.mAdapter.getBluetoothManager().bindBluetoothProfileService(1, this.mConnection);
                } catch (RemoteException e) {
                    Log.m69e(TAG, "Unable to bind HeadsetService", e);
                }
            }
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doUnbind() {
        ReentrantReadWriteLock.WriteLock writeLock;
        synchronized (this.mConnection) {
            Log.m72d(TAG, "Unbinding service...");
            if (this.mService != null) {
                try {
                    this.mAdapter.getBluetoothManager().unbindBluetoothProfileService(1, this.mConnection);
                    this.mServiceLock.writeLock().lock();
                    this.mService = null;
                    writeLock = this.mServiceLock.writeLock();
                } catch (RemoteException e) {
                    Log.m69e(TAG, "Unable to unbind HeadsetService", e);
                    this.mServiceLock.writeLock().lock();
                    this.mService = null;
                    writeLock = this.mServiceLock.writeLock();
                }
                writeLock.unlock();
            }
        }
    }

    @UnsupportedAppUsage
    void close() {
        IBluetoothManager mgr = this.mAdapter.getBluetoothManager();
        if (mgr != null) {
            try {
                mgr.unregisterStateChangeCallback(this.mBluetoothStateChangeCallback);
            } catch (RemoteException re) {
                Log.m69e(TAG, "", re);
            }
        }
        this.mServiceListener = null;
        doUnbind();
    }

    protected void finalize() throws Throwable {
        close();
    }

    @SystemApi
    public boolean connect(BluetoothDevice device) {
        log("connect(" + device + ")");
        IBluetoothHeadset service = this.mService;
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

    @SystemApi
    public boolean disconnect(BluetoothDevice device) {
        log("disconnect(" + device + ")");
        IBluetoothHeadset service = this.mService;
        if (service != null && isEnabled() && isValidDevice(device)) {
            try {
                return service.disconnect(device);
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
        try {
            this.mServiceLock.readLock().lock();
            IBluetoothHeadset service = this.mService;
            if (service == null || !isEnabled()) {
                if (service == null) {
                    Log.m64w(TAG, "Proxy not attached to service");
                }
                return new ArrayList();
            }
            return service.getConnectedDevices();
        } catch (RemoteException e) {
            Log.m70e(TAG, Log.getStackTraceString(new Throwable()));
            return new ArrayList();
        } finally {
            this.mServiceLock.readLock().unlock();
        }
    }

    @Override // android.bluetooth.BluetoothProfile
    public List<BluetoothDevice> getDevicesMatchingConnectionStates(int[] states) {
        IBluetoothHeadset service = this.mService;
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
        IBluetoothHeadset service = this.mService;
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

    @SystemApi
    public boolean setPriority(BluetoothDevice device, int priority) {
        log("setPriority(" + device + ", " + priority + ")");
        IBluetoothHeadset service = this.mService;
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

    @UnsupportedAppUsage
    public int getPriority(BluetoothDevice device) {
        IBluetoothHeadset service = this.mService;
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

    public boolean startVoiceRecognition(BluetoothDevice device) {
        log("startVoiceRecognition()");
        IBluetoothHeadset service = this.mService;
        if (service != null && isEnabled() && isValidDevice(device)) {
            try {
                return service.startVoiceRecognition(device);
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

    public boolean stopVoiceRecognition(BluetoothDevice device) {
        log("stopVoiceRecognition()");
        IBluetoothHeadset service = this.mService;
        if (service != null && isEnabled() && isValidDevice(device)) {
            try {
                return service.stopVoiceRecognition(device);
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

    public boolean isAudioConnected(BluetoothDevice device) {
        IBluetoothHeadset service = this.mService;
        try {
            this.mServiceLock.readLock().lock();
            if (service != null && isEnabled() && isValidDevice(device)) {
                try {
                    return service.isAudioConnected(device);
                } catch (RemoteException e) {
                    Log.m70e(TAG, Log.getStackTraceString(new Throwable()));
                }
            }
            if (service == null) {
                Log.m64w(TAG, "Proxy not attached to service");
            }
            this.mServiceLock.readLock().unlock();
            return false;
        } finally {
            this.mServiceLock.readLock().unlock();
        }
    }

    public static boolean isBluetoothVoiceDialingEnabled(Context context) {
        return context.getResources().getBoolean(C3132R.bool.config_bluetooth_sco_off_call);
    }

    @UnsupportedAppUsage
    public int getAudioState(BluetoothDevice device) {
        IBluetoothHeadset service = this.mService;
        if (service != null && !isDisabled()) {
            try {
                return service.getAudioState(device);
            } catch (RemoteException e) {
                Log.m70e(TAG, e.toString());
                return 10;
            }
        }
        Log.m64w(TAG, "Proxy not attached to service");
        Log.m72d(TAG, Log.getStackTraceString(new Throwable()));
        return 10;
    }

    public void setAudioRouteAllowed(boolean allowed) {
        IBluetoothHeadset service = this.mService;
        if (service != null && isEnabled()) {
            try {
                service.setAudioRouteAllowed(allowed);
                return;
            } catch (RemoteException e) {
                Log.m70e(TAG, e.toString());
                return;
            }
        }
        Log.m64w(TAG, "Proxy not attached to service");
        Log.m72d(TAG, Log.getStackTraceString(new Throwable()));
    }

    public boolean getAudioRouteAllowed() {
        IBluetoothHeadset service = this.mService;
        if (service != null && isEnabled()) {
            try {
                return service.getAudioRouteAllowed();
            } catch (RemoteException e) {
                Log.m70e(TAG, e.toString());
                return false;
            }
        }
        Log.m64w(TAG, "Proxy not attached to service");
        Log.m72d(TAG, Log.getStackTraceString(new Throwable()));
        return false;
    }

    public void setForceScoAudio(boolean forced) {
        IBluetoothHeadset service = this.mService;
        if (service != null && isEnabled()) {
            try {
                service.setForceScoAudio(forced);
                return;
            } catch (RemoteException e) {
                Log.m70e(TAG, e.toString());
                return;
            }
        }
        Log.m64w(TAG, "Proxy not attached to service");
        Log.m72d(TAG, Log.getStackTraceString(new Throwable()));
    }

    public boolean isAudioOn() {
        IBluetoothHeadset service = this.mService;
        if (service != null && isEnabled()) {
            try {
                return service.isAudioOn();
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

    @UnsupportedAppUsage
    public boolean connectAudio() {
        IBluetoothHeadset service = this.mService;
        if (service != null && isEnabled()) {
            try {
                return service.connectAudio();
            } catch (RemoteException e) {
                Log.m70e(TAG, e.toString());
                return false;
            }
        }
        Log.m64w(TAG, "Proxy not attached to service");
        Log.m72d(TAG, Log.getStackTraceString(new Throwable()));
        return false;
    }

    @UnsupportedAppUsage
    public boolean disconnectAudio() {
        IBluetoothHeadset service = this.mService;
        if (service != null && isEnabled()) {
            try {
                return service.disconnectAudio();
            } catch (RemoteException e) {
                Log.m70e(TAG, e.toString());
                return false;
            }
        }
        Log.m64w(TAG, "Proxy not attached to service");
        Log.m72d(TAG, Log.getStackTraceString(new Throwable()));
        return false;
    }

    @UnsupportedAppUsage
    public boolean startScoUsingVirtualVoiceCall() {
        log("startScoUsingVirtualVoiceCall()");
        IBluetoothHeadset service = this.mService;
        if (service != null && isEnabled()) {
            try {
                return service.startScoUsingVirtualVoiceCall();
            } catch (RemoteException e) {
                Log.m70e(TAG, e.toString());
                return false;
            }
        }
        Log.m64w(TAG, "Proxy not attached to service");
        Log.m72d(TAG, Log.getStackTraceString(new Throwable()));
        return false;
    }

    @UnsupportedAppUsage
    public boolean stopScoUsingVirtualVoiceCall() {
        log("stopScoUsingVirtualVoiceCall()");
        IBluetoothHeadset service = this.mService;
        if (service != null && isEnabled()) {
            try {
                return service.stopScoUsingVirtualVoiceCall();
            } catch (RemoteException e) {
                Log.m70e(TAG, e.toString());
                return false;
            }
        }
        Log.m64w(TAG, "Proxy not attached to service");
        Log.m72d(TAG, Log.getStackTraceString(new Throwable()));
        return false;
    }

    @UnsupportedAppUsage
    public void phoneStateChanged(int numActive, int numHeld, int callState, String number, int type, String name) {
        IBluetoothHeadset service = this.mService;
        if (service != null && isEnabled()) {
            try {
                service.phoneStateChanged(numActive, numHeld, callState, number, type, name);
                return;
            } catch (RemoteException e) {
                Log.m70e(TAG, e.toString());
                return;
            }
        }
        Log.m64w(TAG, "Proxy not attached to service");
        Log.m72d(TAG, Log.getStackTraceString(new Throwable()));
    }

    public void clccResponse(int index, int direction, int status, int mode, boolean mpty, String number, int type) {
        IBluetoothHeadset service = this.mService;
        if (service != null && isEnabled()) {
            try {
                service.clccResponse(index, direction, status, mode, mpty, number, type);
                return;
            } catch (RemoteException e) {
                Log.m70e(TAG, e.toString());
                return;
            }
        }
        Log.m64w(TAG, "Proxy not attached to service");
        Log.m72d(TAG, Log.getStackTraceString(new Throwable()));
    }

    public boolean sendVendorSpecificResultCode(BluetoothDevice device, String command, String arg) {
        log("sendVendorSpecificResultCode()");
        if (command == null) {
            throw new IllegalArgumentException("command is null");
        }
        IBluetoothHeadset service = this.mService;
        if (service != null && isEnabled() && isValidDevice(device)) {
            try {
                return service.sendVendorSpecificResultCode(device, command, arg);
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

    @UnsupportedAppUsage
    public boolean setActiveDevice(BluetoothDevice device) {
        Log.m72d(TAG, "setActiveDevice: " + device);
        IBluetoothHeadset service = this.mService;
        if (service != null && isEnabled() && (device == null || isValidDevice(device))) {
            try {
                return service.setActiveDevice(device);
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

    @UnsupportedAppUsage
    public BluetoothDevice getActiveDevice() {
        IBluetoothHeadset service = this.mService;
        if (service != null && isEnabled()) {
            try {
                return service.getActiveDevice();
            } catch (RemoteException e) {
                Log.m70e(TAG, Log.getStackTraceString(new Throwable()));
            }
        }
        if (service == null) {
            Log.m64w(TAG, "Proxy not attached to service");
            return null;
        }
        return null;
    }

    public boolean isInbandRingingEnabled() {
        log("isInbandRingingEnabled()");
        IBluetoothHeadset service = this.mService;
        if (service != null && isEnabled()) {
            try {
                return service.isInbandRingingEnabled();
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

    public static boolean isInbandRingingSupported(Context context) {
        return context.getResources().getBoolean(C3132R.bool.config_bluetooth_hfp_inband_ringing_support);
    }

    @UnsupportedAppUsage
    private boolean isEnabled() {
        return this.mAdapter.getState() == 12;
    }

    private boolean isDisabled() {
        return this.mAdapter.getState() == 10;
    }

    private static boolean isValidDevice(BluetoothDevice device) {
        return device != null && BluetoothAdapter.checkBluetoothAddress(device.getAddress());
    }

    private static void log(String msg) {
        Log.m72d(TAG, msg);
    }
}
