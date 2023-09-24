package android.support.p011v4.media;

import android.content.Context;
import android.media.browse.MediaBrowser;
import android.p007os.Bundle;
import android.p007os.Parcel;
import android.service.media.MediaBrowserService;
import android.support.annotation.RequiresApi;
import android.support.p011v4.media.MediaBrowserServiceCompatApi23;
import android.util.Log;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@RequiresApi(24)
/* renamed from: android.support.v4.media.MediaBrowserServiceCompatApi24 */
/* loaded from: classes3.dex */
class MediaBrowserServiceCompatApi24 {
    private static final String TAG = "MBSCompatApi24";
    private static Field sResultFlags;

    /* renamed from: android.support.v4.media.MediaBrowserServiceCompatApi24$ServiceCompatProxy */
    /* loaded from: classes3.dex */
    public interface ServiceCompatProxy extends MediaBrowserServiceCompatApi23.ServiceCompatProxy {
        void onLoadChildren(String str, ResultWrapper resultWrapper, Bundle bundle);
    }

    MediaBrowserServiceCompatApi24() {
    }

    static {
        try {
            sResultFlags = MediaBrowserService.Result.class.getDeclaredField("mFlags");
            sResultFlags.setAccessible(true);
        } catch (NoSuchFieldException e) {
            Log.m62w(TAG, e);
        }
    }

    public static Object createService(Context context, ServiceCompatProxy serviceProxy) {
        return new MediaBrowserServiceAdaptor(context, serviceProxy);
    }

    public static void notifyChildrenChanged(Object serviceObj, String parentId, Bundle options) {
        ((MediaBrowserService) serviceObj).notifyChildrenChanged(parentId, options);
    }

    public static Bundle getBrowserRootHints(Object serviceObj) {
        return ((MediaBrowserService) serviceObj).getBrowserRootHints();
    }

    /* renamed from: android.support.v4.media.MediaBrowserServiceCompatApi24$ResultWrapper */
    /* loaded from: classes3.dex */
    static class ResultWrapper {
        MediaBrowserService.Result mResultObj;

        ResultWrapper(MediaBrowserService.Result result) {
            this.mResultObj = result;
        }

        public void sendResult(List<Parcel> result, int flags) {
            try {
                MediaBrowserServiceCompatApi24.sResultFlags.setInt(this.mResultObj, flags);
            } catch (IllegalAccessException e) {
                Log.m62w(MediaBrowserServiceCompatApi24.TAG, e);
            }
            this.mResultObj.sendResult(parcelListToItemList(result));
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

    /* renamed from: android.support.v4.media.MediaBrowserServiceCompatApi24$MediaBrowserServiceAdaptor */
    /* loaded from: classes3.dex */
    static class MediaBrowserServiceAdaptor extends MediaBrowserServiceCompatApi23.MediaBrowserServiceAdaptor {
        MediaBrowserServiceAdaptor(Context context, ServiceCompatProxy serviceWrapper) {
            super(context, serviceWrapper);
        }

        @Override // android.service.media.MediaBrowserService
        public void onLoadChildren(String parentId, MediaBrowserService.Result<List<MediaBrowser.MediaItem>> result, Bundle options) {
            ((ServiceCompatProxy) this.mServiceProxy).onLoadChildren(parentId, new ResultWrapper(result), options);
        }
    }
}
