package android.os;

import android.annotation.UnsupportedAppUsage;
import android.util.ArrayMap;
import android.util.Log;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.os.BinderInternal;
import com.android.internal.util.StatLogger;
import java.util.Map;

public final class ServiceManager {
    private static final int GET_SERVICE_LOG_EVERY_CALLS_CORE = SystemProperties.getInt("debug.servicemanager.log_calls_core", 100);
    private static final int GET_SERVICE_LOG_EVERY_CALLS_NON_CORE = SystemProperties.getInt("debug.servicemanager.log_calls", 200);
    private static final long GET_SERVICE_SLOW_THRESHOLD_US_CORE = ((long) (SystemProperties.getInt("debug.servicemanager.slow_call_core_ms", 10) * 1000));
    private static final long GET_SERVICE_SLOW_THRESHOLD_US_NON_CORE = ((long) (SystemProperties.getInt("debug.servicemanager.slow_call_ms", 50) * 1000));
    private static final int SLOW_LOG_INTERVAL_MS = 5000;
    private static final int STATS_LOG_INTERVAL_MS = 5000;
    private static final String TAG = "ServiceManager";
    @UnsupportedAppUsage
    private static Map<String, IBinder> sCache = new ArrayMap();
    @GuardedBy({"sLock"})
    private static int sGetServiceAccumulatedCallCount;
    @GuardedBy({"sLock"})
    private static int sGetServiceAccumulatedUs;
    @GuardedBy({"sLock"})
    private static long sLastSlowLogActualTime;
    @GuardedBy({"sLock"})
    private static long sLastSlowLogUptime;
    @GuardedBy({"sLock"})
    private static long sLastStatsLogUptime;
    private static final Object sLock = new Object();
    @UnsupportedAppUsage
    private static IServiceManager sServiceManager;
    public static final StatLogger sStatLogger = new StatLogger(new String[]{"getService()"});

    interface Stats {
        public static final int COUNT = 1;
        public static final int GET_SERVICE = 0;
    }

    @UnsupportedAppUsage
    private static IServiceManager getIServiceManager() {
        if (sServiceManager != null) {
            return sServiceManager;
        }
        sServiceManager = ServiceManagerNative.asInterface(Binder.allowBlocking(BinderInternal.getContextObject()));
        return sServiceManager;
    }

    @UnsupportedAppUsage
    public static IBinder getService(String name) {
        try {
            IBinder service = sCache.get(name);
            if (service != null) {
                return service;
            }
            return Binder.allowBlocking(rawGetService(name));
        } catch (RemoteException e) {
            Log.e(TAG, "error in getService", e);
            return null;
        }
    }

    public static IBinder getServiceOrThrow(String name) throws ServiceNotFoundException {
        IBinder binder = getService(name);
        if (binder != null) {
            return binder;
        }
        throw new ServiceNotFoundException(name);
    }

    @UnsupportedAppUsage
    public static void addService(String name, IBinder service) {
        addService(name, service, false, 8);
    }

    @UnsupportedAppUsage
    public static void addService(String name, IBinder service, boolean allowIsolated) {
        addService(name, service, allowIsolated, 8);
    }

    @UnsupportedAppUsage
    public static void addService(String name, IBinder service, boolean allowIsolated, int dumpPriority) {
        try {
            getIServiceManager().addService(name, service, allowIsolated, dumpPriority);
        } catch (RemoteException e) {
            Log.e(TAG, "error in addService", e);
        }
    }

    @UnsupportedAppUsage
    public static IBinder checkService(String name) {
        try {
            IBinder service = sCache.get(name);
            if (service != null) {
                return service;
            }
            return Binder.allowBlocking(getIServiceManager().checkService(name));
        } catch (RemoteException e) {
            Log.e(TAG, "error in checkService", e);
            return null;
        }
    }

    @UnsupportedAppUsage
    public static String[] listServices() {
        try {
            return getIServiceManager().listServices(15);
        } catch (RemoteException e) {
            Log.e(TAG, "error in listServices", e);
            return null;
        }
    }

    public static void initServiceCache(Map<String, IBinder> cache) {
        if (sCache.size() == 0) {
            sCache.putAll(cache);
            return;
        }
        throw new IllegalStateException("setServiceCache may only be called once");
    }

    public static class ServiceNotFoundException extends Exception {
        public ServiceNotFoundException(String name) {
            super("No service published for: " + name);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:32:0x008e, code lost:
        return r4;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static android.os.IBinder rawGetService(java.lang.String r19) throws android.os.RemoteException {
        /*
            r1 = r19
            com.android.internal.util.StatLogger r0 = sStatLogger
            long r2 = r0.getTime()
            android.os.IServiceManager r0 = getIServiceManager()
            android.os.IBinder r4 = r0.getService(r1)
            com.android.internal.util.StatLogger r0 = sStatLogger
            r5 = 0
            long r6 = r0.logDurationStat(r5, r2)
            int r6 = (int) r6
            int r7 = android.os.Process.myUid()
            boolean r8 = android.os.UserHandle.isCore(r7)
            if (r8 == 0) goto L_0x0025
            long r9 = GET_SERVICE_SLOW_THRESHOLD_US_CORE
            goto L_0x0027
        L_0x0025:
            long r9 = GET_SERVICE_SLOW_THRESHOLD_US_NON_CORE
        L_0x0027:
            java.lang.Object r11 = sLock
            monitor-enter(r11)
            int r0 = sGetServiceAccumulatedUs     // Catch:{ all -> 0x008f }
            int r0 = r0 + r6
            sGetServiceAccumulatedUs = r0     // Catch:{ all -> 0x008f }
            int r0 = sGetServiceAccumulatedCallCount     // Catch:{ all -> 0x008f }
            int r0 = r0 + 1
            sGetServiceAccumulatedCallCount = r0     // Catch:{ all -> 0x008f }
            long r12 = android.os.SystemClock.uptimeMillis()     // Catch:{ all -> 0x008f }
            long r14 = (long) r6
            int r0 = (r14 > r9 ? 1 : (r14 == r9 ? 0 : -1))
            r14 = 5000(0x1388, double:2.4703E-320)
            if (r0 < 0) goto L_0x005e
            long r16 = sLastSlowLogUptime     // Catch:{ all -> 0x005a }
            long r16 = r16 + r14
            int r0 = (r12 > r16 ? 1 : (r12 == r16 ? 0 : -1))
            if (r0 > 0) goto L_0x004f
            long r16 = sLastSlowLogActualTime     // Catch:{ all -> 0x005a }
            long r14 = (long) r6     // Catch:{ all -> 0x005a }
            int r0 = (r16 > r14 ? 1 : (r16 == r14 ? 0 : -1))
            if (r0 >= 0) goto L_0x005e
        L_0x004f:
            int r0 = r6 / 1000
            android.os.EventLogTags.writeServiceManagerSlow(r0, r1)     // Catch:{ all -> 0x005a }
            sLastSlowLogUptime = r12     // Catch:{ all -> 0x005a }
            long r14 = (long) r6     // Catch:{ all -> 0x005a }
            sLastSlowLogActualTime = r14     // Catch:{ all -> 0x005a }
            goto L_0x005e
        L_0x005a:
            r0 = move-exception
            r18 = r6
            goto L_0x0092
        L_0x005e:
            if (r8 == 0) goto L_0x0063
            int r0 = GET_SERVICE_LOG_EVERY_CALLS_CORE     // Catch:{ all -> 0x005a }
            goto L_0x0065
        L_0x0063:
            int r0 = GET_SERVICE_LOG_EVERY_CALLS_NON_CORE     // Catch:{ all -> 0x008f }
        L_0x0065:
            int r14 = sGetServiceAccumulatedCallCount     // Catch:{ all -> 0x008f }
            if (r14 < r0) goto L_0x008b
            long r14 = sLastStatsLogUptime     // Catch:{ all -> 0x008f }
            r16 = 5000(0x1388, double:2.4703E-320)
            long r14 = r14 + r16
            int r14 = (r12 > r14 ? 1 : (r12 == r14 ? 0 : -1))
            if (r14 < 0) goto L_0x008b
            int r14 = sGetServiceAccumulatedCallCount     // Catch:{ all -> 0x008f }
            int r15 = sGetServiceAccumulatedUs     // Catch:{ all -> 0x008f }
            int r15 = r15 / 1000
            long r16 = sLastStatsLogUptime     // Catch:{ all -> 0x008f }
            r18 = r6
            long r5 = r12 - r16
            int r5 = (int) r5
            android.os.EventLogTags.writeServiceManagerStats(r14, r15, r5)     // Catch:{ all -> 0x0094 }
            r5 = 0
            sGetServiceAccumulatedCallCount = r5     // Catch:{ all -> 0x0094 }
            sGetServiceAccumulatedUs = r5     // Catch:{ all -> 0x0094 }
            sLastStatsLogUptime = r12     // Catch:{ all -> 0x0094 }
            goto L_0x008d
        L_0x008b:
            r18 = r6
        L_0x008d:
            monitor-exit(r11)     // Catch:{ all -> 0x0094 }
            return r4
        L_0x008f:
            r0 = move-exception
            r18 = r6
        L_0x0092:
            monitor-exit(r11)     // Catch:{ all -> 0x0094 }
            throw r0
        L_0x0094:
            r0 = move-exception
            goto L_0x0092
        */
        throw new UnsupportedOperationException("Method not decompiled: android.os.ServiceManager.rawGetService(java.lang.String):android.os.IBinder");
    }
}
