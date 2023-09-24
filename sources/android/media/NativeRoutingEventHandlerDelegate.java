package android.media;

import android.media.AudioRouting;
import android.p007os.Handler;

/* loaded from: classes3.dex */
class NativeRoutingEventHandlerDelegate {
    private AudioRouting mAudioRouting;
    private Handler mHandler;
    private AudioRouting.OnRoutingChangedListener mOnRoutingChangedListener;

    NativeRoutingEventHandlerDelegate(AudioRouting audioRouting, AudioRouting.OnRoutingChangedListener listener, Handler handler) {
        this.mAudioRouting = audioRouting;
        this.mOnRoutingChangedListener = listener;
        this.mHandler = handler;
    }

    void notifyClient() {
        if (this.mHandler != null) {
            this.mHandler.post(new Runnable() { // from class: android.media.NativeRoutingEventHandlerDelegate.1
                @Override // java.lang.Runnable
                public void run() {
                    if (NativeRoutingEventHandlerDelegate.this.mOnRoutingChangedListener != null) {
                        NativeRoutingEventHandlerDelegate.this.mOnRoutingChangedListener.onRoutingChanged(NativeRoutingEventHandlerDelegate.this.mAudioRouting);
                    }
                }
            });
        }
    }
}
