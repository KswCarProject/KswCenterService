package android.telephony.mbms;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ComponentInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.telecom.Logging.Session;
import android.util.Log;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class MbmsUtils {
    private static final String LOG_TAG = "MbmsUtils";

    public static boolean isContainedIn(File parent, File child) {
        try {
            return child.getCanonicalPath().startsWith(parent.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException("Failed to resolve canonical paths: " + e);
        }
    }

    public static ComponentName toComponentName(ComponentInfo ci) {
        return new ComponentName(ci.packageName, ci.name);
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x0038  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x003c  */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x0040  */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x0047 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0048 A[SYNTHETIC, Splitter:B:23:0x0048] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static android.content.ComponentName getOverrideServiceName(android.content.Context r5, java.lang.String r6) {
        /*
            r0 = 0
            int r1 = r6.hashCode()
            r2 = -1374878107(0xffffffffae0d0665, float:-3.2065368E-11)
            if (r1 == r2) goto L_0x0029
            r2 = -407466459(0xffffffffe7b68e25, float:-1.7241856E24)
            if (r1 == r2) goto L_0x001f
            r2 = 1752202112(0x68707b80, float:4.5425845E24)
            if (r1 == r2) goto L_0x0015
            goto L_0x0033
        L_0x0015:
            java.lang.String r1 = "android.telephony.action.EmbmsGroupCall"
            boolean r1 = r6.equals(r1)
            if (r1 == 0) goto L_0x0033
            r1 = 2
            goto L_0x0034
        L_0x001f:
            java.lang.String r1 = "android.telephony.action.EmbmsDownload"
            boolean r1 = r6.equals(r1)
            if (r1 == 0) goto L_0x0033
            r1 = 0
            goto L_0x0034
        L_0x0029:
            java.lang.String r1 = "android.telephony.action.EmbmsStreaming"
            boolean r1 = r6.equals(r1)
            if (r1 == 0) goto L_0x0033
            r1 = 1
            goto L_0x0034
        L_0x0033:
            r1 = -1
        L_0x0034:
            switch(r1) {
                case 0: goto L_0x0040;
                case 1: goto L_0x003c;
                case 2: goto L_0x0038;
                default: goto L_0x0037;
            }
        L_0x0037:
            goto L_0x0044
        L_0x0038:
            java.lang.String r0 = "mbms-group-call-service-override"
            goto L_0x0044
        L_0x003c:
            java.lang.String r0 = "mbms-streaming-service-override"
            goto L_0x0044
        L_0x0040:
            java.lang.String r0 = "mbms-download-service-override"
        L_0x0044:
            r1 = 0
            if (r0 != 0) goto L_0x0048
            return r1
        L_0x0048:
            android.content.pm.PackageManager r2 = r5.getPackageManager()     // Catch:{ NameNotFoundException -> 0x006b }
            java.lang.String r3 = r5.getPackageName()     // Catch:{ NameNotFoundException -> 0x006b }
            r4 = 128(0x80, float:1.794E-43)
            android.content.pm.ApplicationInfo r2 = r2.getApplicationInfo(r3, r4)     // Catch:{ NameNotFoundException -> 0x006b }
            android.os.Bundle r3 = r2.metaData
            if (r3 != 0) goto L_0x005d
            return r1
        L_0x005d:
            android.os.Bundle r3 = r2.metaData
            java.lang.String r3 = r3.getString(r0)
            if (r3 != 0) goto L_0x0066
            return r1
        L_0x0066:
            android.content.ComponentName r1 = android.content.ComponentName.unflattenFromString(r3)
            return r1
        L_0x006b:
            r2 = move-exception
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.telephony.mbms.MbmsUtils.getOverrideServiceName(android.content.Context, java.lang.String):android.content.ComponentName");
    }

    public static ServiceInfo getMiddlewareServiceInfo(Context context, String serviceAction) {
        List<ResolveInfo> services;
        PackageManager packageManager = context.getPackageManager();
        Intent queryIntent = new Intent();
        queryIntent.setAction(serviceAction);
        ComponentName overrideService = getOverrideServiceName(context, serviceAction);
        if (overrideService == null) {
            services = packageManager.queryIntentServices(queryIntent, 1048576);
        } else {
            queryIntent.setComponent(overrideService);
            services = packageManager.queryIntentServices(queryIntent, 131072);
        }
        if (services == null || services.size() == 0) {
            Log.w(LOG_TAG, "No MBMS services found, cannot get service info");
            return null;
        } else if (services.size() <= 1) {
            return services.get(0).serviceInfo;
        } else {
            Log.w(LOG_TAG, "More than one MBMS service found, cannot get unique service");
            return null;
        }
    }

    public static int startBinding(Context context, String serviceAction, ServiceConnection serviceConnection) {
        Intent bindIntent = new Intent();
        ServiceInfo mbmsServiceInfo = getMiddlewareServiceInfo(context, serviceAction);
        if (mbmsServiceInfo == null) {
            return 1;
        }
        bindIntent.setComponent(toComponentName(mbmsServiceInfo));
        context.bindService(bindIntent, serviceConnection, 1);
        return 0;
    }

    public static File getEmbmsTempFileDirForService(Context context, String serviceId) {
        return new File(MbmsTempFileProvider.getEmbmsTempFileDir(context), serviceId.replaceAll("[^a-zA-Z0-9_]", Session.SESSION_SEPARATION_CHAR_CHILD));
    }
}
