package android.media;

import android.annotation.UnsupportedAppUsage;
import android.content.ContentResolver;
import android.content.IntentFilter;
import android.media.IMediaHTTPConnection;
import android.net.NetworkUtils;
import android.net.ProxyInfo;
import android.os.IBinder;
import android.os.StrictMode;
import android.provider.SettingsStringUtil;
import android.util.Log;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.content.NativeLibraryHelper;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.CookieHandler;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.NoRouteToHostException;
import java.net.ProtocolException;
import java.net.Proxy;
import java.net.URL;
import java.net.UnknownServiceException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class MediaHTTPConnection extends IMediaHTTPConnection.Stub {
    private static final int CONNECT_TIMEOUT_MS = 30000;
    private static final int HTTP_TEMP_REDIRECT = 307;
    private static final int MAX_REDIRECTS = 20;
    private static final String TAG = "MediaHTTPConnection";
    private static final boolean VERBOSE = false;
    @GuardedBy({"this"})
    @UnsupportedAppUsage
    private boolean mAllowCrossDomainRedirect = true;
    @GuardedBy({"this"})
    @UnsupportedAppUsage
    private boolean mAllowCrossProtocolRedirect = true;
    @UnsupportedAppUsage
    private volatile HttpURLConnection mConnection = null;
    @GuardedBy({"this"})
    @UnsupportedAppUsage
    private long mCurrentOffset = -1;
    @GuardedBy({"this"})
    @UnsupportedAppUsage
    private Map<String, String> mHeaders = null;
    @GuardedBy({"this"})
    private InputStream mInputStream = null;
    private long mNativeContext;
    private final AtomicInteger mNumDisconnectingThreads = new AtomicInteger(0);
    @GuardedBy({"this"})
    @UnsupportedAppUsage
    private long mTotalSize = -1;
    @GuardedBy({"this"})
    @UnsupportedAppUsage
    private URL mURL = null;

    private final native void native_finalize();

    private final native IBinder native_getIMemory();

    private static final native void native_init();

    private final native int native_readAt(long j, int i);

    private final native void native_setup();

    @UnsupportedAppUsage
    public MediaHTTPConnection() {
        if (CookieHandler.getDefault() == null) {
            Log.w(TAG, "MediaHTTPConnection: Unexpected. No CookieHandler found.");
        }
        native_setup();
    }

    /* Debug info: failed to restart local var, previous not found, register: 2 */
    @UnsupportedAppUsage
    public synchronized IBinder connect(String uri, String headers) {
        try {
            disconnect();
            this.mAllowCrossDomainRedirect = true;
            this.mURL = new URL(uri);
            this.mHeaders = convertHeaderStringToMap(headers);
        } catch (MalformedURLException e) {
            return null;
        }
        return native_getIMemory();
    }

    private static boolean parseBoolean(String val) {
        try {
            return Long.parseLong(val) != 0;
        } catch (NumberFormatException e) {
            if ("true".equalsIgnoreCase(val) || "yes".equalsIgnoreCase(val)) {
                return true;
            }
            return false;
        }
    }

    private synchronized boolean filterOutInternalHeaders(String key, String val) {
        if (!"android-allow-cross-domain-redirect".equalsIgnoreCase(key)) {
            return false;
        }
        this.mAllowCrossDomainRedirect = parseBoolean(val);
        this.mAllowCrossProtocolRedirect = this.mAllowCrossDomainRedirect;
        return true;
    }

    private synchronized Map<String, String> convertHeaderStringToMap(String headers) {
        HashMap<String, String> map;
        map = new HashMap<>();
        for (String pair : headers.split("\r\n")) {
            int colonPos = pair.indexOf(SettingsStringUtil.DELIMITER);
            if (colonPos >= 0) {
                String key = pair.substring(0, colonPos);
                String val = pair.substring(colonPos + 1);
                if (!filterOutInternalHeaders(key, val)) {
                    map.put(key, val);
                }
            }
        }
        return map;
    }

    @UnsupportedAppUsage
    public void disconnect() {
        this.mNumDisconnectingThreads.incrementAndGet();
        try {
            HttpURLConnection connectionToDisconnect = this.mConnection;
            if (connectionToDisconnect != null) {
                connectionToDisconnect.disconnect();
            }
            synchronized (this) {
                teardownConnection();
                this.mHeaders = null;
                this.mURL = null;
            }
            this.mNumDisconnectingThreads.decrementAndGet();
        } catch (Throwable th) {
            this.mNumDisconnectingThreads.decrementAndGet();
            throw th;
        }
    }

    private synchronized void teardownConnection() {
        if (this.mConnection != null) {
            if (this.mInputStream != null) {
                try {
                    this.mInputStream.close();
                } catch (IOException e) {
                }
                this.mInputStream = null;
            }
            this.mConnection.disconnect();
            this.mConnection = null;
            this.mCurrentOffset = -1;
        }
    }

    private static final boolean isLocalHost(URL url) {
        String host;
        if (url == null || (host = url.getHost()) == null) {
            return false;
        }
        try {
            if (!host.equalsIgnoreCase(ProxyInfo.LOCAL_HOST) && !NetworkUtils.numericToInetAddress(host).isLoopbackAddress()) {
                return false;
            }
            return true;
        } catch (IllegalArgumentException e) {
        }
    }

    private synchronized void seekTo(long offset) throws IOException {
        int lastSlashPos;
        long j = offset;
        synchronized (this) {
            teardownConnection();
            long j2 = -1;
            try {
                URL url = this.mURL;
                boolean noProxy = isLocalHost(url);
                int redirectCount = 0;
                while (this.mNumDisconnectingThreads.get() <= 0) {
                    if (noProxy) {
                        this.mConnection = (HttpURLConnection) url.openConnection(Proxy.NO_PROXY);
                    } else {
                        this.mConnection = (HttpURLConnection) url.openConnection();
                    }
                    if (this.mNumDisconnectingThreads.get() <= 0) {
                        this.mConnection.setConnectTimeout(30000);
                        this.mConnection.setInstanceFollowRedirects(this.mAllowCrossDomainRedirect);
                        if (this.mHeaders != null) {
                            for (Map.Entry<String, String> entry : this.mHeaders.entrySet()) {
                                this.mConnection.setRequestProperty(entry.getKey(), entry.getValue());
                            }
                        }
                        if (j > 0) {
                            this.mConnection.setRequestProperty("Range", "bytes=" + j + NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
                        }
                        int response = this.mConnection.getResponseCode();
                        if (response == 300 || response == 301 || response == 302 || response == 303 || response == 307) {
                            redirectCount++;
                            if (redirectCount <= 20) {
                                String method = this.mConnection.getRequestMethod();
                                if (response == 307 && !method.equals("GET")) {
                                    if (!method.equals("HEAD")) {
                                        throw new NoRouteToHostException("Invalid redirect");
                                    }
                                }
                                String location = this.mConnection.getHeaderField("Location");
                                if (location != null) {
                                    url = new URL(this.mURL, location);
                                    if (!url.getProtocol().equals(IntentFilter.SCHEME_HTTPS)) {
                                        if (!url.getProtocol().equals(IntentFilter.SCHEME_HTTP)) {
                                            throw new NoRouteToHostException("Unsupported protocol redirect");
                                        }
                                    }
                                    boolean sameProtocol = this.mURL.getProtocol().equals(url.getProtocol());
                                    if (!this.mAllowCrossProtocolRedirect) {
                                        if (!sameProtocol) {
                                            throw new NoRouteToHostException("Cross-protocol redirects are disallowed");
                                        }
                                    }
                                    boolean sameHost = this.mURL.getHost().equals(url.getHost());
                                    if (!this.mAllowCrossDomainRedirect) {
                                        if (!sameHost) {
                                            throw new NoRouteToHostException("Cross-domain redirects are disallowed");
                                        }
                                    }
                                    if (response != 307) {
                                        this.mURL = url;
                                    }
                                    j2 = -1;
                                } else {
                                    throw new NoRouteToHostException("Invalid redirect");
                                }
                            } else {
                                throw new NoRouteToHostException("Too many redirects: " + redirectCount);
                            }
                        } else {
                            if (this.mAllowCrossDomainRedirect) {
                                this.mURL = this.mConnection.getURL();
                            }
                            if (response == 206) {
                                String contentRange = this.mConnection.getHeaderField("Content-Range");
                                this.mTotalSize = j2;
                                if (contentRange != null && (lastSlashPos = contentRange.lastIndexOf(47)) >= 0) {
                                    try {
                                        this.mTotalSize = Long.parseLong(contentRange.substring(lastSlashPos + 1));
                                    } catch (NumberFormatException e) {
                                    }
                                }
                            } else if (response == 200) {
                                this.mTotalSize = (long) this.mConnection.getContentLength();
                            } else {
                                throw new IOException();
                            }
                            if (j > 0) {
                                if (response != 206) {
                                    throw new ProtocolException();
                                }
                            }
                            this.mInputStream = new BufferedInputStream(this.mConnection.getInputStream());
                            this.mCurrentOffset = j;
                        }
                    } else {
                        throw new IOException("concurrently disconnecting");
                    }
                }
                throw new IOException("concurrently disconnecting");
            } catch (IOException e2) {
                this.mTotalSize = -1;
                teardownConnection();
                this.mCurrentOffset = -1;
                throw e2;
            }
        }
    }

    @UnsupportedAppUsage
    public synchronized int readAt(long offset, int size) {
        return native_readAt(offset, size);
    }

    private synchronized int readAt(long offset, byte[] data, int size) {
        int n;
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());
        try {
            if (offset != this.mCurrentOffset) {
                seekTo(offset);
            }
            n = this.mInputStream.read(data, 0, size);
            if (n == -1) {
                n = 0;
            }
            this.mCurrentOffset += (long) n;
        } catch (ProtocolException e) {
            Log.w(TAG, "readAt " + offset + " / " + size + " => " + e);
            return MediaPlayer.MEDIA_ERROR_UNSUPPORTED;
        } catch (NoRouteToHostException e2) {
            Log.w(TAG, "readAt " + offset + " / " + size + " => " + e2);
            return MediaPlayer.MEDIA_ERROR_UNSUPPORTED;
        } catch (UnknownServiceException e3) {
            Log.w(TAG, "readAt " + offset + " / " + size + " => " + e3);
            return MediaPlayer.MEDIA_ERROR_UNSUPPORTED;
        } catch (IOException e4) {
            return -1;
        } catch (Exception e5) {
            return -1;
        }
        return n;
    }

    public synchronized long getSize() {
        if (this.mConnection == null) {
            try {
                seekTo(0);
            } catch (IOException e) {
                return -1;
            }
        }
        return this.mTotalSize;
    }

    @UnsupportedAppUsage
    public synchronized String getMIMEType() {
        if (this.mConnection == null) {
            try {
                seekTo(0);
            } catch (IOException e) {
                return ContentResolver.MIME_TYPE_DEFAULT;
            }
        }
        return this.mConnection.getContentType();
    }

    @UnsupportedAppUsage
    public synchronized String getUri() {
        return this.mURL.toString();
    }

    /* access modifiers changed from: protected */
    public void finalize() {
        native_finalize();
    }

    static {
        System.loadLibrary("media_jni");
        native_init();
    }
}
