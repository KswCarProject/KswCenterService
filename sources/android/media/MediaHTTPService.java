package android.media;

import android.annotation.UnsupportedAppUsage;
import android.media.IMediaHTTPService;
import android.p007os.IBinder;
import android.util.Log;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.util.List;

/* loaded from: classes3.dex */
public class MediaHTTPService extends IMediaHTTPService.Stub {
    private static final String TAG = "MediaHTTPService";
    private Boolean mCookieStoreInitialized = new Boolean(false);
    private List<HttpCookie> mCookies;

    public MediaHTTPService(List<HttpCookie> cookies) {
        this.mCookies = cookies;
        Log.m66v(TAG, "MediaHTTPService(" + this + "): Cookies: " + cookies);
    }

    @Override // android.media.IMediaHTTPService
    public IMediaHTTPConnection makeHTTPConnection() {
        synchronized (this.mCookieStoreInitialized) {
            if (!this.mCookieStoreInitialized.booleanValue()) {
                CookieHandler cookieHandler = CookieHandler.getDefault();
                if (cookieHandler == null) {
                    cookieHandler = new CookieManager();
                    CookieHandler.setDefault(cookieHandler);
                    Log.m66v(TAG, "makeHTTPConnection: CookieManager created: " + cookieHandler);
                } else {
                    Log.m66v(TAG, "makeHTTPConnection: CookieHandler (" + cookieHandler + ") exists.");
                }
                if (this.mCookies != null) {
                    if (cookieHandler instanceof CookieManager) {
                        CookieManager cookieManager = (CookieManager) cookieHandler;
                        CookieStore store = cookieManager.getCookieStore();
                        for (HttpCookie cookie : this.mCookies) {
                            try {
                                store.add(null, cookie);
                            } catch (Exception e) {
                                Log.m66v(TAG, "makeHTTPConnection: CookieStore.add" + e);
                            }
                        }
                    } else {
                        Log.m64w(TAG, "makeHTTPConnection: The installed CookieHandler is not a CookieManager. Can\u2019t add the provided cookies to the cookie store.");
                    }
                }
                this.mCookieStoreInitialized = true;
                Log.m66v(TAG, "makeHTTPConnection(" + this + "): cookieHandler: " + cookieHandler + " Cookies: " + this.mCookies);
            }
        }
        return new MediaHTTPConnection();
    }

    @UnsupportedAppUsage
    static IBinder createHttpServiceBinderIfNecessary(String path) {
        return createHttpServiceBinderIfNecessary(path, null);
    }

    static IBinder createHttpServiceBinderIfNecessary(String path, List<HttpCookie> cookies) {
        if (path.startsWith("http://") || path.startsWith("https://")) {
            return new MediaHTTPService(cookies).asBinder();
        }
        if (path.startsWith("widevine://")) {
            Log.m72d(TAG, "Widevine classic is no longer supported");
            return null;
        }
        return null;
    }
}
