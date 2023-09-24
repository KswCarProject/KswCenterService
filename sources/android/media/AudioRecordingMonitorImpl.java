package android.media;

import android.media.AudioManager;
import android.media.AudioRecordingMonitorImpl;
import android.media.IAudioService;
import android.media.IRecordingConfigDispatcher;
import android.p007os.Binder;
import android.p007os.Handler;
import android.p007os.HandlerThread;
import android.p007os.IBinder;
import android.p007os.Looper;
import android.p007os.Message;
import android.p007os.RemoteException;
import android.p007os.ServiceManager;
import android.util.Log;
import com.android.internal.annotations.GuardedBy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
public class AudioRecordingMonitorImpl implements AudioRecordingMonitor {
    private static final int MSG_RECORDING_CONFIG_CHANGE = 1;
    private static final String TAG = "android.media.AudioRecordingMonitor";
    private static IAudioService sService;
    private final AudioRecordingMonitorClient mClient;
    @GuardedBy({"mRecordCallbackLock"})
    private volatile Handler mRecordingCallbackHandler;
    @GuardedBy({"mRecordCallbackLock"})
    private HandlerThread mRecordingCallbackHandlerThread;
    private final Object mRecordCallbackLock = new Object();
    @GuardedBy({"mRecordCallbackLock"})
    private LinkedList<AudioRecordingCallbackInfo> mRecordCallbackList = new LinkedList<>();
    @GuardedBy({"mRecordCallbackLock"})
    private final IRecordingConfigDispatcher mRecordingCallback = new IRecordingConfigDispatcher.Stub() { // from class: android.media.AudioRecordingMonitorImpl.1
        @Override // android.media.IRecordingConfigDispatcher
        public void dispatchRecordingConfigChange(List<AudioRecordingConfiguration> configs) {
            AudioRecordingConfiguration config = AudioRecordingMonitorImpl.this.getMyConfig(configs);
            if (config != null) {
                synchronized (AudioRecordingMonitorImpl.this.mRecordCallbackLock) {
                    if (AudioRecordingMonitorImpl.this.mRecordingCallbackHandler != null) {
                        Message m = AudioRecordingMonitorImpl.this.mRecordingCallbackHandler.obtainMessage(1, config);
                        AudioRecordingMonitorImpl.this.mRecordingCallbackHandler.sendMessage(m);
                    }
                }
            }
        }
    };

    AudioRecordingMonitorImpl(AudioRecordingMonitorClient client) {
        this.mClient = client;
    }

    @Override // android.media.AudioRecordingMonitor
    public void registerAudioRecordingCallback(Executor executor, AudioManager.AudioRecordingCallback cb) {
        if (cb == null) {
            throw new IllegalArgumentException("Illegal null AudioRecordingCallback");
        }
        if (executor == null) {
            throw new IllegalArgumentException("Illegal null Executor");
        }
        synchronized (this.mRecordCallbackLock) {
            Iterator<AudioRecordingCallbackInfo> it = this.mRecordCallbackList.iterator();
            while (it.hasNext()) {
                AudioRecordingCallbackInfo arci = it.next();
                if (arci.mCb == cb) {
                    throw new IllegalArgumentException("AudioRecordingCallback already registered");
                }
            }
            beginRecordingCallbackHandling();
            this.mRecordCallbackList.add(new AudioRecordingCallbackInfo(executor, cb));
        }
    }

    @Override // android.media.AudioRecordingMonitor
    public void unregisterAudioRecordingCallback(AudioManager.AudioRecordingCallback cb) {
        if (cb == null) {
            throw new IllegalArgumentException("Illegal null AudioRecordingCallback argument");
        }
        synchronized (this.mRecordCallbackLock) {
            Iterator<AudioRecordingCallbackInfo> it = this.mRecordCallbackList.iterator();
            while (it.hasNext()) {
                AudioRecordingCallbackInfo arci = it.next();
                if (arci.mCb == cb) {
                    this.mRecordCallbackList.remove(arci);
                    if (this.mRecordCallbackList.size() == 0) {
                        endRecordingCallbackHandling();
                    }
                }
            }
            throw new IllegalArgumentException("AudioRecordingCallback was not registered");
        }
    }

    @Override // android.media.AudioRecordingMonitor
    public AudioRecordingConfiguration getActiveRecordingConfiguration() {
        IAudioService service = getService();
        try {
            List<AudioRecordingConfiguration> configs = service.getActiveRecordingConfigurations();
            return getMyConfig(configs);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* loaded from: classes3.dex */
    private static class AudioRecordingCallbackInfo {
        final AudioManager.AudioRecordingCallback mCb;
        final Executor mExecutor;

        AudioRecordingCallbackInfo(Executor e, AudioManager.AudioRecordingCallback cb) {
            this.mExecutor = e;
            this.mCb = cb;
        }
    }

    @GuardedBy({"mRecordCallbackLock"})
    private void beginRecordingCallbackHandling() {
        if (this.mRecordingCallbackHandlerThread == null) {
            this.mRecordingCallbackHandlerThread = new HandlerThread("android.media.AudioRecordingMonitor.RecordingCallback");
            this.mRecordingCallbackHandlerThread.start();
            Looper looper = this.mRecordingCallbackHandlerThread.getLooper();
            if (looper != null) {
                this.mRecordingCallbackHandler = new HandlerC10492(looper);
                IAudioService service = getService();
                try {
                    service.registerRecordingCallback(this.mRecordingCallback);
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
        }
    }

    /* renamed from: android.media.AudioRecordingMonitorImpl$2 */
    /* loaded from: classes3.dex */
    class HandlerC10492 extends Handler {
        HandlerC10492(Looper x0) {
            super(x0);
        }

        @Override // android.p007os.Handler
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                if (msg.obj == null) {
                    return;
                }
                final ArrayList<AudioRecordingConfiguration> configs = new ArrayList<>();
                configs.add((AudioRecordingConfiguration) msg.obj);
                synchronized (AudioRecordingMonitorImpl.this.mRecordCallbackLock) {
                    if (AudioRecordingMonitorImpl.this.mRecordCallbackList.size() == 0) {
                        return;
                    }
                    LinkedList<AudioRecordingCallbackInfo> cbInfoList = new LinkedList<>(AudioRecordingMonitorImpl.this.mRecordCallbackList);
                    long identity = Binder.clearCallingIdentity();
                    try {
                        Iterator<AudioRecordingCallbackInfo> it = cbInfoList.iterator();
                        while (it.hasNext()) {
                            final AudioRecordingCallbackInfo cbi = it.next();
                            cbi.mExecutor.execute(new Runnable() { // from class: android.media.-$$Lambda$AudioRecordingMonitorImpl$2$cn04v8rie0OYr-_fiLO_SMYka7I
                                @Override // java.lang.Runnable
                                public final void run() {
                                    AudioRecordingMonitorImpl.AudioRecordingCallbackInfo.this.mCb.onRecordingConfigChanged(configs);
                                }
                            });
                        }
                        return;
                    } finally {
                        Binder.restoreCallingIdentity(identity);
                    }
                }
            }
            Log.m70e(AudioRecordingMonitorImpl.TAG, "Unknown event " + msg.what);
        }
    }

    @GuardedBy({"mRecordCallbackLock"})
    private void endRecordingCallbackHandling() {
        if (this.mRecordingCallbackHandlerThread != null) {
            IAudioService service = getService();
            try {
                service.unregisterRecordingCallback(this.mRecordingCallback);
                this.mRecordingCallbackHandlerThread.quit();
                this.mRecordingCallbackHandlerThread = null;
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    AudioRecordingConfiguration getMyConfig(List<AudioRecordingConfiguration> configs) {
        int portId = this.mClient.getPortId();
        for (AudioRecordingConfiguration config : configs) {
            if (config.getClientPortId() == portId) {
                return config;
            }
        }
        return null;
    }

    private static IAudioService getService() {
        if (sService != null) {
            return sService;
        }
        IBinder b = ServiceManager.getService("audio");
        sService = IAudioService.Stub.asInterface(b);
        return sService;
    }
}
