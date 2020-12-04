package android.security.net.config;

import android.util.Pair;
import java.util.Locale;
import java.util.Set;
import javax.net.ssl.X509TrustManager;

public final class ApplicationConfig {
    private static ApplicationConfig sInstance;
    private static Object sLock = new Object();
    private ConfigSource mConfigSource;
    private Set<Pair<Domain, NetworkSecurityConfig>> mConfigs;
    private NetworkSecurityConfig mDefaultConfig;
    private boolean mInitialized;
    private final Object mLock = new Object();
    private X509TrustManager mTrustManager;

    public ApplicationConfig(ConfigSource configSource) {
        this.mConfigSource = configSource;
        this.mInitialized = false;
    }

    public boolean hasPerDomainConfigs() {
        ensureInitialized();
        return this.mConfigs != null && !this.mConfigs.isEmpty();
    }

    public NetworkSecurityConfig getConfigForHostname(String hostname) {
        ensureInitialized();
        if (hostname == null || hostname.isEmpty() || this.mConfigs == null) {
            return this.mDefaultConfig;
        }
        if (hostname.charAt(0) != '.') {
            String hostname2 = hostname.toLowerCase(Locale.US);
            if (hostname2.charAt(hostname2.length() - 1) == '.') {
                hostname2 = hostname2.substring(0, hostname2.length() - 1);
            }
            Pair<Domain, NetworkSecurityConfig> bestMatch = null;
            for (Pair<Domain, NetworkSecurityConfig> entry : this.mConfigs) {
                Domain domain = (Domain) entry.first;
                NetworkSecurityConfig config = (NetworkSecurityConfig) entry.second;
                if (domain.hostname.equals(hostname2)) {
                    return config;
                }
                if (domain.subdomainsIncluded && hostname2.endsWith(domain.hostname) && hostname2.charAt((hostname2.length() - domain.hostname.length()) - 1) == '.') {
                    if (bestMatch == null) {
                        bestMatch = entry;
                    } else if (domain.hostname.length() > ((Domain) bestMatch.first).hostname.length()) {
                        bestMatch = entry;
                    }
                }
            }
            if (bestMatch != null) {
                return (NetworkSecurityConfig) bestMatch.second;
            }
            return this.mDefaultConfig;
        }
        throw new IllegalArgumentException("hostname must not begin with a .");
    }

    public X509TrustManager getTrustManager() {
        ensureInitialized();
        return this.mTrustManager;
    }

    public boolean isCleartextTrafficPermitted() {
        ensureInitialized();
        if (this.mConfigs != null) {
            for (Pair<Domain, NetworkSecurityConfig> entry : this.mConfigs) {
                if (!((NetworkSecurityConfig) entry.second).isCleartextTrafficPermitted()) {
                    return false;
                }
            }
        }
        return this.mDefaultConfig.isCleartextTrafficPermitted();
    }

    public boolean isCleartextTrafficPermitted(String hostname) {
        return getConfigForHostname(hostname).isCleartextTrafficPermitted();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0042, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void handleTrustStorageUpdate() {
        /*
            r5 = this;
            java.lang.Object r0 = r5.mLock
            monitor-enter(r0)
            boolean r1 = r5.mInitialized     // Catch:{ all -> 0x0043 }
            if (r1 != 0) goto L_0x0009
            monitor-exit(r0)     // Catch:{ all -> 0x0043 }
            return
        L_0x0009:
            android.security.net.config.NetworkSecurityConfig r1 = r5.mDefaultConfig     // Catch:{ all -> 0x0043 }
            r1.handleTrustStorageUpdate()     // Catch:{ all -> 0x0043 }
            java.util.Set<android.util.Pair<android.security.net.config.Domain, android.security.net.config.NetworkSecurityConfig>> r1 = r5.mConfigs     // Catch:{ all -> 0x0043 }
            if (r1 == 0) goto L_0x0041
            java.util.HashSet r1 = new java.util.HashSet     // Catch:{ all -> 0x0043 }
            java.util.Set<android.util.Pair<android.security.net.config.Domain, android.security.net.config.NetworkSecurityConfig>> r2 = r5.mConfigs     // Catch:{ all -> 0x0043 }
            int r2 = r2.size()     // Catch:{ all -> 0x0043 }
            r1.<init>(r2)     // Catch:{ all -> 0x0043 }
            java.util.Set<android.util.Pair<android.security.net.config.Domain, android.security.net.config.NetworkSecurityConfig>> r2 = r5.mConfigs     // Catch:{ all -> 0x0043 }
            java.util.Iterator r2 = r2.iterator()     // Catch:{ all -> 0x0043 }
        L_0x0023:
            boolean r3 = r2.hasNext()     // Catch:{ all -> 0x0043 }
            if (r3 == 0) goto L_0x0041
            java.lang.Object r3 = r2.next()     // Catch:{ all -> 0x0043 }
            android.util.Pair r3 = (android.util.Pair) r3     // Catch:{ all -> 0x0043 }
            S r4 = r3.second     // Catch:{ all -> 0x0043 }
            android.security.net.config.NetworkSecurityConfig r4 = (android.security.net.config.NetworkSecurityConfig) r4     // Catch:{ all -> 0x0043 }
            boolean r4 = r1.add(r4)     // Catch:{ all -> 0x0043 }
            if (r4 == 0) goto L_0x0040
            S r4 = r3.second     // Catch:{ all -> 0x0043 }
            android.security.net.config.NetworkSecurityConfig r4 = (android.security.net.config.NetworkSecurityConfig) r4     // Catch:{ all -> 0x0043 }
            r4.handleTrustStorageUpdate()     // Catch:{ all -> 0x0043 }
        L_0x0040:
            goto L_0x0023
        L_0x0041:
            monitor-exit(r0)     // Catch:{ all -> 0x0043 }
            return
        L_0x0043:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0043 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.security.net.config.ApplicationConfig.handleTrustStorageUpdate():void");
    }

    private void ensureInitialized() {
        synchronized (this.mLock) {
            if (!this.mInitialized) {
                this.mConfigs = this.mConfigSource.getPerDomainConfigs();
                this.mDefaultConfig = this.mConfigSource.getDefaultConfig();
                this.mConfigSource = null;
                this.mTrustManager = new RootTrustManager(this);
                this.mInitialized = true;
            }
        }
    }

    public static void setDefaultInstance(ApplicationConfig config) {
        synchronized (sLock) {
            sInstance = config;
        }
    }

    public static ApplicationConfig getDefaultInstance() {
        ApplicationConfig applicationConfig;
        synchronized (sLock) {
            applicationConfig = sInstance;
        }
        return applicationConfig;
    }
}
