package android.support.p011v4.net;

import android.Manifest;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.p007os.Build;
import android.support.annotation.RequiresApi;
import android.support.annotation.RequiresPermission;
import android.support.annotation.RestrictTo;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* renamed from: android.support.v4.net.ConnectivityManagerCompat */
/* loaded from: classes3.dex */
public final class ConnectivityManagerCompat {
    private static final ConnectivityManagerCompatImpl IMPL;
    public static final int RESTRICT_BACKGROUND_STATUS_DISABLED = 1;
    public static final int RESTRICT_BACKGROUND_STATUS_ENABLED = 3;
    public static final int RESTRICT_BACKGROUND_STATUS_WHITELISTED = 2;

    /* renamed from: android.support.v4.net.ConnectivityManagerCompat$ConnectivityManagerCompatImpl */
    /* loaded from: classes3.dex */
    interface ConnectivityManagerCompatImpl {
        int getRestrictBackgroundStatus(ConnectivityManager connectivityManager);

        boolean isActiveNetworkMetered(ConnectivityManager connectivityManager);
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    /* renamed from: android.support.v4.net.ConnectivityManagerCompat$RestrictBackgroundStatus */
    /* loaded from: classes3.dex */
    public @interface RestrictBackgroundStatus {
    }

    /* renamed from: android.support.v4.net.ConnectivityManagerCompat$ConnectivityManagerCompatBaseImpl */
    /* loaded from: classes3.dex */
    static class ConnectivityManagerCompatBaseImpl implements ConnectivityManagerCompatImpl {
        ConnectivityManagerCompatBaseImpl() {
        }

        @Override // android.support.p011v4.net.ConnectivityManagerCompat.ConnectivityManagerCompatImpl
        public boolean isActiveNetworkMetered(ConnectivityManager cm) {
            NetworkInfo info = cm.getActiveNetworkInfo();
            if (info == null) {
                return true;
            }
            int type = info.getType();
            switch (type) {
                case 0:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                    return true;
                case 1:
                case 7:
                case 9:
                    return false;
                case 8:
                default:
                    return true;
            }
        }

        @Override // android.support.p011v4.net.ConnectivityManagerCompat.ConnectivityManagerCompatImpl
        public int getRestrictBackgroundStatus(ConnectivityManager cm) {
            return 3;
        }
    }

    @RequiresApi(16)
    /* renamed from: android.support.v4.net.ConnectivityManagerCompat$ConnectivityManagerCompatApi16Impl */
    /* loaded from: classes3.dex */
    static class ConnectivityManagerCompatApi16Impl extends ConnectivityManagerCompatBaseImpl {
        ConnectivityManagerCompatApi16Impl() {
        }

        @Override // android.support.p011v4.net.ConnectivityManagerCompat.ConnectivityManagerCompatBaseImpl, android.support.p011v4.net.ConnectivityManagerCompat.ConnectivityManagerCompatImpl
        public boolean isActiveNetworkMetered(ConnectivityManager cm) {
            return cm.isActiveNetworkMetered();
        }
    }

    @RequiresApi(24)
    /* renamed from: android.support.v4.net.ConnectivityManagerCompat$ConnectivityManagerCompatApi24Impl */
    /* loaded from: classes3.dex */
    static class ConnectivityManagerCompatApi24Impl extends ConnectivityManagerCompatApi16Impl {
        ConnectivityManagerCompatApi24Impl() {
        }

        @Override // android.support.p011v4.net.ConnectivityManagerCompat.ConnectivityManagerCompatBaseImpl, android.support.p011v4.net.ConnectivityManagerCompat.ConnectivityManagerCompatImpl
        public int getRestrictBackgroundStatus(ConnectivityManager cm) {
            return cm.getRestrictBackgroundStatus();
        }
    }

    static {
        if (Build.VERSION.SDK_INT >= 24) {
            IMPL = new ConnectivityManagerCompatApi24Impl();
        } else if (Build.VERSION.SDK_INT >= 16) {
            IMPL = new ConnectivityManagerCompatApi16Impl();
        } else {
            IMPL = new ConnectivityManagerCompatBaseImpl();
        }
    }

    @RequiresPermission(Manifest.C0000permission.ACCESS_NETWORK_STATE)
    public static boolean isActiveNetworkMetered(ConnectivityManager cm) {
        return IMPL.isActiveNetworkMetered(cm);
    }

    public static NetworkInfo getNetworkInfoFromBroadcast(ConnectivityManager cm, Intent intent) {
        NetworkInfo info = (NetworkInfo) intent.getParcelableExtra("networkInfo");
        if (info != null) {
            return cm.getNetworkInfo(info.getType());
        }
        return null;
    }

    public static int getRestrictBackgroundStatus(ConnectivityManager cm) {
        return IMPL.getRestrictBackgroundStatus(cm);
    }

    private ConnectivityManagerCompat() {
    }
}
