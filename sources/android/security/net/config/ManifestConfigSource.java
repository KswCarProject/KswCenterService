package android.security.net.config;

import android.content.Context;
import android.content.p002pm.ApplicationInfo;
import android.util.Log;
import android.util.Pair;
import java.util.Set;

/* loaded from: classes3.dex */
public class ManifestConfigSource implements ConfigSource {
    private static final boolean DBG = true;
    private static final String LOG_TAG = "NetworkSecurityConfig";
    private final ApplicationInfo mApplicationInfo;
    private ConfigSource mConfigSource;
    private final Context mContext;
    private final Object mLock = new Object();

    public ManifestConfigSource(Context context) {
        this.mContext = context;
        this.mApplicationInfo = new ApplicationInfo(context.getApplicationInfo());
    }

    @Override // android.security.net.config.ConfigSource
    public Set<Pair<Domain, NetworkSecurityConfig>> getPerDomainConfigs() {
        return getConfigSource().getPerDomainConfigs();
    }

    @Override // android.security.net.config.ConfigSource
    public NetworkSecurityConfig getDefaultConfig() {
        return getConfigSource().getDefaultConfig();
    }

    private ConfigSource getConfigSource() {
        ConfigSource source;
        synchronized (this.mLock) {
            if (this.mConfigSource != null) {
                return this.mConfigSource;
            }
            int configResource = this.mApplicationInfo.networkSecurityConfigRes;
            if (configResource != 0) {
                debugBuild = (this.mApplicationInfo.flags & 2) != 0;
                Log.m72d(LOG_TAG, "Using Network Security Config from resource " + this.mContext.getResources().getResourceEntryName(configResource) + " debugBuild: " + debugBuild);
                source = new XmlConfigSource(this.mContext, configResource, this.mApplicationInfo);
            } else {
                Log.m72d(LOG_TAG, "No Network Security Config specified, using platform default");
                if ((this.mApplicationInfo.flags & 134217728) != 0 && !this.mApplicationInfo.isInstantApp()) {
                    debugBuild = true;
                }
                source = new DefaultConfigSource(debugBuild, this.mApplicationInfo);
            }
            this.mConfigSource = source;
            return this.mConfigSource;
        }
    }

    /* loaded from: classes3.dex */
    private static final class DefaultConfigSource implements ConfigSource {
        private final NetworkSecurityConfig mDefaultConfig;

        DefaultConfigSource(boolean usesCleartextTraffic, ApplicationInfo info) {
            this.mDefaultConfig = NetworkSecurityConfig.getDefaultBuilder(info).setCleartextTrafficPermitted(usesCleartextTraffic).build();
        }

        @Override // android.security.net.config.ConfigSource
        public NetworkSecurityConfig getDefaultConfig() {
            return this.mDefaultConfig;
        }

        @Override // android.security.net.config.ConfigSource
        public Set<Pair<Domain, NetworkSecurityConfig>> getPerDomainConfigs() {
            return null;
        }
    }
}
