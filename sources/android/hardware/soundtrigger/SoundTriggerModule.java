package android.hardware.soundtrigger;

import android.annotation.UnsupportedAppUsage;
import android.hardware.soundtrigger.SoundTrigger;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import java.lang.ref.WeakReference;

public class SoundTriggerModule {
    private static final int EVENT_RECOGNITION = 1;
    private static final int EVENT_SERVICE_DIED = 2;
    private static final int EVENT_SERVICE_STATE_CHANGE = 4;
    private static final int EVENT_SOUNDMODEL = 3;
    private NativeEventHandlerDelegate mEventHandlerDelegate;
    @UnsupportedAppUsage
    private int mId;
    @UnsupportedAppUsage
    private long mNativeContext;

    private native void native_finalize();

    private native void native_setup(String str, Object obj);

    @UnsupportedAppUsage
    public native void detach();

    public native int getModelState(int i);

    @UnsupportedAppUsage
    public native int loadSoundModel(SoundTrigger.SoundModel soundModel, int[] iArr);

    @UnsupportedAppUsage
    public native int startRecognition(int i, SoundTrigger.RecognitionConfig recognitionConfig);

    @UnsupportedAppUsage
    public native int stopRecognition(int i);

    @UnsupportedAppUsage
    public native int unloadSoundModel(int i);

    SoundTriggerModule(int moduleId, SoundTrigger.StatusListener listener, Handler handler) {
        this.mId = moduleId;
        this.mEventHandlerDelegate = new NativeEventHandlerDelegate(listener, handler);
        native_setup(SoundTrigger.getCurrentOpPackageName(), new WeakReference(this));
    }

    /* access modifiers changed from: protected */
    public void finalize() {
        native_finalize();
    }

    private class NativeEventHandlerDelegate {
        private final Handler mHandler;

        NativeEventHandlerDelegate(final SoundTrigger.StatusListener listener, Handler handler) {
            Looper looper;
            if (handler != null) {
                looper = handler.getLooper();
            } else {
                looper = Looper.getMainLooper();
            }
            if (looper != null) {
                this.mHandler = new Handler(looper, SoundTriggerModule.this) {
                    public void handleMessage(Message msg) {
                        switch (msg.what) {
                            case 1:
                                if (listener != null) {
                                    listener.onRecognition((SoundTrigger.RecognitionEvent) msg.obj);
                                    return;
                                }
                                return;
                            case 2:
                                if (listener != null) {
                                    listener.onServiceDied();
                                    return;
                                }
                                return;
                            case 3:
                                if (listener != null) {
                                    listener.onSoundModelUpdate((SoundTrigger.SoundModelEvent) msg.obj);
                                    return;
                                }
                                return;
                            case 4:
                                if (listener != null) {
                                    listener.onServiceStateChange(msg.arg1);
                                    return;
                                }
                                return;
                            default:
                                return;
                        }
                    }
                };
            } else {
                this.mHandler = null;
            }
        }

        /* access modifiers changed from: package-private */
        public Handler handler() {
            return this.mHandler;
        }
    }

    @UnsupportedAppUsage
    private static void postEventFromNative(Object module_ref, int what, int arg1, int arg2, Object obj) {
        NativeEventHandlerDelegate delegate;
        Handler handler;
        SoundTriggerModule module = (SoundTriggerModule) ((WeakReference) module_ref).get();
        if (module != null && (delegate = module.mEventHandlerDelegate) != null && (handler = delegate.handler()) != null) {
            handler.sendMessage(handler.obtainMessage(what, arg1, arg2, obj));
        }
    }
}
