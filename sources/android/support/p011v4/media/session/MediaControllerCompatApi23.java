package android.support.p011v4.media.session;

import android.media.session.MediaController;
import android.net.Uri;
import android.p007os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.p011v4.media.session.MediaControllerCompatApi21;

@RequiresApi(23)
/* renamed from: android.support.v4.media.session.MediaControllerCompatApi23 */
/* loaded from: classes3.dex */
class MediaControllerCompatApi23 {
    MediaControllerCompatApi23() {
    }

    /* renamed from: android.support.v4.media.session.MediaControllerCompatApi23$TransportControls */
    /* loaded from: classes3.dex */
    public static class TransportControls extends MediaControllerCompatApi21.TransportControls {
        public static void playFromUri(Object controlsObj, Uri uri, Bundle extras) {
            ((MediaController.TransportControls) controlsObj).playFromUri(uri, extras);
        }
    }
}
