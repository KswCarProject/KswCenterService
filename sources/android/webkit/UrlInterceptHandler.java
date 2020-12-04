package android.webkit;

import android.annotation.UnsupportedAppUsage;
import android.webkit.CacheManager;
import java.util.Map;

@Deprecated
public interface UrlInterceptHandler {
    @Deprecated
    @UnsupportedAppUsage
    PluginData getPluginData(String str, Map<String, String> map);

    @Deprecated
    @UnsupportedAppUsage
    CacheManager.CacheResult service(String str, Map<String, String> map);
}
