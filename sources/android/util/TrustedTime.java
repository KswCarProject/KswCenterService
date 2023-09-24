package android.util;

import android.annotation.UnsupportedAppUsage;

/* loaded from: classes4.dex */
public interface TrustedTime {
    @UnsupportedAppUsage
    long currentTimeMillis();

    @UnsupportedAppUsage
    boolean forceRefresh();

    boolean forceRefresh(String str, long j);

    boolean forceSync();

    @UnsupportedAppUsage
    long getCacheAge();

    long getCacheCertainty();

    @UnsupportedAppUsage
    boolean hasCache();
}
