package android.media;

import android.annotation.UnsupportedAppUsage;
import android.media.AudioManager;
import android.p007os.Handler;
import android.p007os.HandlerThread;
import android.p007os.Message;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

/* loaded from: classes3.dex */
class AudioPortEventHandler {
    private static final int AUDIOPORT_EVENT_NEW_LISTENER = 4;
    private static final int AUDIOPORT_EVENT_PATCH_LIST_UPDATED = 2;
    private static final int AUDIOPORT_EVENT_PORT_LIST_UPDATED = 1;
    private static final int AUDIOPORT_EVENT_SERVICE_DIED = 3;
    private static final long RESCHEDULE_MESSAGE_DELAY_MS = 100;
    private static final String TAG = "AudioPortEventHandler";
    private Handler mHandler;
    private HandlerThread mHandlerThread;
    @UnsupportedAppUsage
    private long mJniCallback;
    private final ArrayList<AudioManager.OnAudioPortUpdateListener> mListeners = new ArrayList<>();

    private native void native_finalize();

    private native void native_setup(Object obj);

    AudioPortEventHandler() {
    }

    void init() {
        synchronized (this) {
            if (this.mHandler != null) {
                return;
            }
            this.mHandlerThread = new HandlerThread(TAG);
            this.mHandlerThread.start();
            if (this.mHandlerThread.getLooper() != null) {
                this.mHandler = new Handler(this.mHandlerThread.getLooper()) { // from class: android.media.AudioPortEventHandler.1
                    /* JADX WARN: Removed duplicated region for block: B:44:0x00b0 A[LOOP:1: B:42:0x00a9->B:44:0x00b0, LOOP_END] */
                    /* JADX WARN: Removed duplicated region for block: B:53:0x00bd A[SYNTHETIC] */
                    @Override // android.p007os.Handler
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public void handleMessage(Message msg) {
                        ArrayList<AudioManager.OnAudioPortUpdateListener> listeners;
                        AudioPatch[] patchList;
                        int i;
                        synchronized (this) {
                            if (msg.what != 4) {
                                listeners = AudioPortEventHandler.this.mListeners;
                            } else {
                                listeners = new ArrayList<>();
                                if (AudioPortEventHandler.this.mListeners.contains(msg.obj)) {
                                    listeners.add((AudioManager.OnAudioPortUpdateListener) msg.obj);
                                }
                            }
                        }
                        if (msg.what == 1 || msg.what == 2 || msg.what == 3) {
                            AudioManager.resetAudioPortGeneration();
                        }
                        if (listeners.isEmpty()) {
                            return;
                        }
                        ArrayList<AudioPort> ports = new ArrayList<>();
                        ArrayList<AudioPatch> patches = new ArrayList<>();
                        if (msg.what != 3) {
                            int status = AudioManager.updateAudioPortCache(ports, patches, null);
                            if (status != 0) {
                                sendMessageDelayed(obtainMessage(msg.what, msg.obj), AudioPortEventHandler.RESCHEDULE_MESSAGE_DELAY_MS);
                                return;
                            }
                        }
                        int status2 = msg.what;
                        int i2 = 0;
                        switch (status2) {
                            case 1:
                            case 4:
                                AudioPort[] portList = (AudioPort[]) ports.toArray(new AudioPort[0]);
                                for (int i3 = 0; i3 < listeners.size(); i3++) {
                                    listeners.get(i3).onAudioPortListUpdate(portList);
                                }
                                if (msg.what == 1) {
                                    return;
                                }
                                patchList = (AudioPatch[]) patches.toArray(new AudioPatch[0]);
                                while (true) {
                                    i = i2;
                                    if (i >= listeners.size()) {
                                        listeners.get(i).onAudioPatchListUpdate(patchList);
                                        i2 = i + 1;
                                    } else {
                                        return;
                                    }
                                }
                            case 2:
                                patchList = (AudioPatch[]) patches.toArray(new AudioPatch[0]);
                                while (true) {
                                    i = i2;
                                    if (i >= listeners.size()) {
                                    }
                                    listeners.get(i).onAudioPatchListUpdate(patchList);
                                    i2 = i + 1;
                                }
                                break;
                            case 3:
                                while (true) {
                                    int i4 = i2;
                                    if (i4 < listeners.size()) {
                                        listeners.get(i4).onServiceDied();
                                        i2 = i4 + 1;
                                    } else {
                                        return;
                                    }
                                }
                            default:
                                return;
                        }
                    }
                };
                native_setup(new WeakReference(this));
            } else {
                this.mHandler = null;
            }
        }
    }

    protected void finalize() {
        native_finalize();
        if (this.mHandlerThread.isAlive()) {
            this.mHandlerThread.quit();
        }
    }

    void registerListener(AudioManager.OnAudioPortUpdateListener l) {
        synchronized (this) {
            this.mListeners.add(l);
        }
        if (this.mHandler != null) {
            Message m = this.mHandler.obtainMessage(4, 0, 0, l);
            this.mHandler.sendMessage(m);
        }
    }

    void unregisterListener(AudioManager.OnAudioPortUpdateListener l) {
        synchronized (this) {
            this.mListeners.remove(l);
        }
    }

    Handler handler() {
        return this.mHandler;
    }

    @UnsupportedAppUsage
    private static void postEventFromNative(Object module_ref, int what, int arg1, int arg2, Object obj) {
        Handler handler;
        AudioPortEventHandler eventHandler = (AudioPortEventHandler) ((WeakReference) module_ref).get();
        if (eventHandler != null && eventHandler != null && (handler = eventHandler.handler()) != null) {
            Message m = handler.obtainMessage(what, arg1, arg2, obj);
            if (what != 4) {
                handler.removeMessages(what);
            }
            handler.sendMessage(m);
        }
    }
}
