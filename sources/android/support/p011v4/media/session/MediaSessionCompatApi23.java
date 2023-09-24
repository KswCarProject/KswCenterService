package android.support.p011v4.media.session;

import android.net.Uri;
import android.p007os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.p011v4.media.session.MediaSessionCompatApi21;

@RequiresApi(23)
/* renamed from: android.support.v4.media.session.MediaSessionCompatApi23 */
/* loaded from: classes3.dex */
class MediaSessionCompatApi23 {

    /* renamed from: android.support.v4.media.session.MediaSessionCompatApi23$Callback */
    /* loaded from: classes3.dex */
    public interface Callback extends MediaSessionCompatApi21.Callback {
        void onPlayFromUri(Uri uri, Bundle bundle);
    }

    MediaSessionCompatApi23() {
    }

    public static Object createCallback(Callback callback) {
        return new CallbackProxy(callback);
    }

    /* renamed from: android.support.v4.media.session.MediaSessionCompatApi23$CallbackProxy */
    /* loaded from: classes3.dex */
    static class CallbackProxy<T extends Callback> extends MediaSessionCompatApi21.CallbackProxy<T> {
        public CallbackProxy(T callback) {
            super(callback);
        }

        @Override // android.media.session.MediaSession.Callback
        public void onPlayFromUri(Uri uri, Bundle extras) {
            ((Callback) this.mCallback).onPlayFromUri(uri, extras);
        }
    }
}
