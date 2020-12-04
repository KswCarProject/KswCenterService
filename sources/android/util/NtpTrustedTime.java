package android.util;

import android.annotation.UnsupportedAppUsage;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.SntpClient;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.provider.Settings;
import android.text.TextUtils;
import com.android.internal.R;

public class NtpTrustedTime implements TrustedTime {
    private static final String BACKUP_SERVER = "persist.backup.ntpServer";
    private static final boolean LOGD = true;
    private static final String TAG = "NtpTrustedTime";
    private static String mBackupServer = "";
    private static int mNtpRetries = 0;
    private static int mNtpRetriesMax = 0;
    private static Context sContext;
    private static NtpTrustedTime sSingleton;
    private boolean mBackupmode = false;
    private ConnectivityManager mCM;
    private long mCachedNtpCertainty;
    private long mCachedNtpElapsedRealtime;
    private long mCachedNtpTime;
    private boolean mHasCache;
    private final String mServer;
    private final long mTimeout;

    public NtpTrustedTime(String server, long timeout) {
        Log.d(TAG, "creating NtpTrustedTime using " + server);
        this.mServer = server;
        this.mTimeout = timeout;
    }

    @UnsupportedAppUsage
    public static synchronized NtpTrustedTime getInstance(Context context) {
        NtpTrustedTime ntpTrustedTime;
        int retryMax;
        synchronized (NtpTrustedTime.class) {
            if (sSingleton == null) {
                Resources res = context.getResources();
                ContentResolver resolver = context.getContentResolver();
                String defaultServer = res.getString(R.string.config_ntpServer);
                long defaultTimeout = (long) res.getInteger(R.integer.config_ntpTimeout);
                String secureServer = Settings.Global.getString(resolver, Settings.Global.NTP_SERVER);
                long timeout = Settings.Global.getLong(resolver, Settings.Global.NTP_TIMEOUT, defaultTimeout);
                Log.d(TAG, "defaultServer is " + defaultServer + ", defaultTimeout is " + defaultTimeout + ", secureServer is " + secureServer + ", timeout is " + timeout);
                sSingleton = new NtpTrustedTime(secureServer != null ? secureServer : defaultServer, timeout);
                sContext = context;
                String sserver_prop = Settings.Global.getString(resolver, Settings.Global.NTP_SERVER_2);
                String backupServer = SystemProperties.get((sserver_prop == null || sserver_prop.length() <= 0) ? BACKUP_SERVER : sserver_prop);
                if (backupServer != null && backupServer.length() > 0 && (retryMax = res.getInteger(R.integer.config_ntpRetry)) > 0) {
                    NtpTrustedTime ntpTrustedTime2 = sSingleton;
                    mNtpRetriesMax = retryMax;
                    NtpTrustedTime ntpTrustedTime3 = sSingleton;
                    Resources resources = res;
                    ContentResolver contentResolver = resolver;
                    mBackupServer = backupServer.trim().replace("\"", "");
                }
            }
            ntpTrustedTime = sSingleton;
        }
        return ntpTrustedTime;
    }

    public boolean forceRefresh(String host, long timeout) {
        if (host == null) {
            return forceRefresh();
        }
        Log.d(TAG, "forceRefresh() from cache miss base host");
        SntpClient client = new SntpClient();
        synchronized (this) {
            if (this.mCM == null) {
                this.mCM = (ConnectivityManager) sContext.getSystemService(ConnectivityManager.class);
            }
        }
        NetworkInfo ni = null;
        Network networkP = this.mCM == null ? null : this.mCM.getActiveNetwork();
        if (this.mCM != null) {
            ni = this.mCM.getNetworkInfo(networkP);
        }
        if (ni == null || !ni.isConnected()) {
            Log.d(TAG, "forceRefresh: no connectivity");
            return false;
        } else if (!client.requestTime(host, (int) timeout, networkP)) {
            return false;
        } else {
            this.mHasCache = true;
            this.mCachedNtpTime = client.getNtpTime();
            this.mCachedNtpElapsedRealtime = client.getNtpTimeReference();
            this.mCachedNtpCertainty = client.getRoundTripTime() / 2;
            Log.d(TAG, "Ntp Server,mHasCache:" + this.mHasCache + " ,mCachedNtpTime:" + this.mCachedNtpTime + " ,mCachedNtpElapsedRealtime:" + this.mCachedNtpElapsedRealtime);
            return true;
        }
    }

    @UnsupportedAppUsage
    public boolean forceRefresh() {
        Log.d(TAG, "forceRefresh");
        if (hasCache()) {
            return forceSync();
        }
        return false;
    }

    public boolean forceSync() {
        Log.d(TAG, "forceSync");
        synchronized (this) {
            if (this.mCM == null) {
                this.mCM = (ConnectivityManager) sContext.getSystemService(ConnectivityManager.class);
            }
        }
        return forceRefresh(this.mCM == null ? null : this.mCM.getActiveNetwork());
    }

    public boolean forceRefresh(Network network) {
        Log.d(TAG, "forceRefresh 2");
        if (TextUtils.isEmpty(this.mServer)) {
            return false;
        }
        synchronized (this) {
            if (this.mCM == null) {
                this.mCM = (ConnectivityManager) sContext.getSystemService(ConnectivityManager.class);
            }
        }
        NetworkInfo ni = this.mCM == null ? null : this.mCM.getNetworkInfo(network);
        if (ni == null || !ni.isConnected()) {
            Log.d(TAG, "forceRefresh: no connectivity");
            return false;
        }
        Log.d(TAG, "forceRefresh() from cache miss");
        SntpClient client = new SntpClient();
        String targetServer = this.mServer;
        if (getBackupmode()) {
            setBackupmode(false);
            targetServer = mBackupServer;
        }
        Log.d(TAG, "Ntp Server to access at:" + targetServer);
        Log.d(TAG, "Ntp Server,mHasCache:" + this.mHasCache + " ,mCachedNtpTime:" + this.mCachedNtpTime + " ,mCachedNtpElapsedRealtime:" + this.mCachedNtpElapsedRealtime);
        if (client.requestTime(targetServer, (int) this.mTimeout, network)) {
            this.mHasCache = true;
            this.mCachedNtpTime = client.getNtpTime();
            this.mCachedNtpElapsedRealtime = client.getNtpTimeReference();
            this.mCachedNtpCertainty = client.getRoundTripTime() / 2;
            Log.d(TAG, "-------- " + this.mServer + " -------- OK!!!");
            return true;
        } else if (client.requestTime("s2g.time.edu.cn", (int) this.mTimeout, network)) {
            this.mHasCache = true;
            this.mCachedNtpTime = client.getNtpTime();
            this.mCachedNtpElapsedRealtime = client.getNtpTimeReference();
            this.mCachedNtpCertainty = client.getRoundTripTime() / 2;
            Log.d(TAG, "-------- s2g.time.edu.cn -------- OK!!!");
            return true;
        } else if (client.requestTime("ntp.api.bz", (int) this.mTimeout, network)) {
            this.mHasCache = true;
            this.mCachedNtpTime = client.getNtpTime();
            this.mCachedNtpElapsedRealtime = client.getNtpTimeReference();
            this.mCachedNtpCertainty = client.getRoundTripTime() / 2;
            Log.d(TAG, "-------- ntp.api.bz -------- OK!!!");
            return true;
        } else if (client.requestTime("asia.pool.ntp.org", (int) this.mTimeout, network)) {
            this.mHasCache = true;
            this.mCachedNtpTime = client.getNtpTime();
            this.mCachedNtpElapsedRealtime = client.getNtpTimeReference();
            this.mCachedNtpCertainty = client.getRoundTripTime() / 2;
            Log.d(TAG, "-------- asia.pool.ntp.org -------- OK!!!");
            return true;
        } else if (client.requestTime("cn.pool.ntp.org", (int) this.mTimeout, network)) {
            this.mHasCache = true;
            this.mCachedNtpTime = client.getNtpTime();
            this.mCachedNtpElapsedRealtime = client.getNtpTimeReference();
            this.mCachedNtpCertainty = client.getRoundTripTime() / 2;
            Log.d(TAG, "-------- cn.pool.ntp.org -------- OK!!!");
            return true;
        } else if (client.requestTime("2.android.pool.ntp.org", (int) this.mTimeout, network)) {
            this.mHasCache = true;
            this.mCachedNtpTime = client.getNtpTime();
            this.mCachedNtpElapsedRealtime = client.getNtpTimeReference();
            this.mCachedNtpCertainty = client.getRoundTripTime() / 2;
            Log.d(TAG, "-------- 2.android.pool.ntp.org -------- OK!!!");
            return true;
        } else {
            countInBackupmode();
            return false;
        }
    }

    @UnsupportedAppUsage
    public boolean hasCache() {
        return this.mHasCache;
    }

    public long getCacheAge() {
        if (this.mHasCache) {
            return SystemClock.elapsedRealtime() - this.mCachedNtpElapsedRealtime;
        }
        return Long.MAX_VALUE;
    }

    public long getCacheCertainty() {
        if (this.mHasCache) {
            return this.mCachedNtpCertainty;
        }
        return Long.MAX_VALUE;
    }

    @UnsupportedAppUsage
    public long currentTimeMillis() {
        if (this.mHasCache) {
            Log.d(TAG, "currentTimeMillis() cache hit");
            return this.mCachedNtpTime + getCacheAge();
        }
        throw new IllegalStateException("Missing authoritative time source");
    }

    @UnsupportedAppUsage
    public long getCachedNtpTime() {
        Log.d(TAG, "getCachedNtpTime() cache hit");
        return this.mCachedNtpTime;
    }

    @UnsupportedAppUsage
    public long getCachedNtpTimeReference() {
        return this.mCachedNtpElapsedRealtime;
    }

    public void setBackupmode(boolean mode) {
        if (isBackupSupported()) {
            this.mBackupmode = mode;
        }
        Log.d(TAG, "setBackupmode() set the backup mode to be:" + this.mBackupmode);
    }

    private boolean getBackupmode() {
        return this.mBackupmode;
    }

    private boolean isBackupSupported() {
        return (mNtpRetriesMax <= 0 || mBackupServer == null || mBackupServer.length() == 0) ? false : true;
    }

    private void countInBackupmode() {
        if (isBackupSupported()) {
            mNtpRetries++;
            if (mNtpRetries >= mNtpRetriesMax) {
                mNtpRetries = 0;
                setBackupmode(true);
            }
        }
        Log.d(TAG, "countInBackupmode() func");
    }
}
