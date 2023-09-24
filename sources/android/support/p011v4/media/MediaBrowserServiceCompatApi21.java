package android.support.p011v4.media;

import android.content.Context;
import android.content.Intent;
import android.media.browse.MediaBrowser;
import android.media.session.MediaSession;
import android.p007os.Bundle;
import android.p007os.IBinder;
import android.p007os.Parcel;
import android.service.media.MediaBrowserService;
import android.support.annotation.RequiresApi;
import java.util.ArrayList;
import java.util.List;

@RequiresApi(21)
/* renamed from: android.support.v4.media.MediaBrowserServiceCompatApi21 */
/* loaded from: classes3.dex */
class MediaBrowserServiceCompatApi21 {

    /* renamed from: android.support.v4.media.MediaBrowserServiceCompatApi21$ServiceCompatProxy */
    /* loaded from: classes3.dex */
    public interface ServiceCompatProxy {
        BrowserRoot onGetRoot(String str, int i, Bundle bundle);

        void onLoadChildren(String str, ResultWrapper<List<Parcel>> resultWrapper);
    }

    MediaBrowserServiceCompatApi21() {
    }

    public static Object createService(Context context, ServiceCompatProxy serviceProxy) {
        return new MediaBrowserServiceAdaptor(context, serviceProxy);
    }

    public static void onCreate(Object serviceObj) {
        ((MediaBrowserService) serviceObj).onCreate();
    }

    public static IBinder onBind(Object serviceObj, Intent intent) {
        return ((MediaBrowserService) serviceObj).onBind(intent);
    }

    public static void setSessionToken(Object serviceObj, Object token) {
        ((MediaBrowserService) serviceObj).setSessionToken((MediaSession.Token) token);
    }

    public static void notifyChildrenChanged(Object serviceObj, String parentId) {
        ((MediaBrowserService) serviceObj).notifyChildrenChanged(parentId);
    }

    /* renamed from: android.support.v4.media.MediaBrowserServiceCompatApi21$ResultWrapper */
    /* loaded from: classes3.dex */
    static class ResultWrapper<T> {
        MediaBrowserService.Result mResultObj;

        ResultWrapper(MediaBrowserService.Result result) {
            this.mResultObj = result;
        }

        public void sendResult(T result) {
            if (result instanceof List) {
                this.mResultObj.sendResult(parcelListToItemList((List) result));
            } else if (result instanceof Parcel) {
                Parcel parcel = (Parcel) result;
                parcel.setDataPosition(0);
                this.mResultObj.sendResult(MediaBrowser.MediaItem.CREATOR.createFromParcel(parcel));
                parcel.recycle();
            } else {
                this.mResultObj.sendResult(null);
            }
        }

        public void detach() {
            this.mResultObj.detach();
        }

        List<MediaBrowser.MediaItem> parcelListToItemList(List<Parcel> parcelList) {
            if (parcelList == null) {
                return null;
            }
            List<MediaBrowser.MediaItem> items = new ArrayList<>();
            for (Parcel parcel : parcelList) {
                parcel.setDataPosition(0);
                items.add(MediaBrowser.MediaItem.CREATOR.createFromParcel(parcel));
                parcel.recycle();
            }
            return items;
        }
    }

    /* renamed from: android.support.v4.media.MediaBrowserServiceCompatApi21$BrowserRoot */
    /* loaded from: classes3.dex */
    static class BrowserRoot {
        final Bundle mExtras;
        final String mRootId;

        BrowserRoot(String rootId, Bundle extras) {
            this.mRootId = rootId;
            this.mExtras = extras;
        }
    }

    /* renamed from: android.support.v4.media.MediaBrowserServiceCompatApi21$MediaBrowserServiceAdaptor */
    /* loaded from: classes3.dex */
    static class MediaBrowserServiceAdaptor extends MediaBrowserService {
        final ServiceCompatProxy mServiceProxy;

        MediaBrowserServiceAdaptor(Context context, ServiceCompatProxy serviceWrapper) {
            attachBaseContext(context);
            this.mServiceProxy = serviceWrapper;
        }

        @Override // android.service.media.MediaBrowserService
        public MediaBrowserService.BrowserRoot onGetRoot(String clientPackageName, int clientUid, Bundle rootHints) {
            BrowserRoot browserRoot = this.mServiceProxy.onGetRoot(clientPackageName, clientUid, rootHints == null ? null : new Bundle(rootHints));
            if (browserRoot == null) {
                return null;
            }
            return new MediaBrowserService.BrowserRoot(browserRoot.mRootId, browserRoot.mExtras);
        }

        @Override // android.service.media.MediaBrowserService
        public void onLoadChildren(String parentId, MediaBrowserService.Result<List<MediaBrowser.MediaItem>> result) {
            this.mServiceProxy.onLoadChildren(parentId, new ResultWrapper<>(result));
        }
    }
}
