package android.accessibilityservice;

import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.util.ArrayMap;
import android.util.Slog;
import com.android.internal.util.Preconditions;

public final class AccessibilityButtonController {
    private static final String LOG_TAG = "A11yButtonController";
    private ArrayMap<AccessibilityButtonCallback, Handler> mCallbacks;
    private final Object mLock = new Object();
    private final IAccessibilityServiceConnection mServiceConnection;

    AccessibilityButtonController(IAccessibilityServiceConnection serviceConnection) {
        this.mServiceConnection = serviceConnection;
    }

    public boolean isAccessibilityButtonAvailable() {
        if (this.mServiceConnection == null) {
            return false;
        }
        try {
            return this.mServiceConnection.isAccessibilityButtonAvailable();
        } catch (RemoteException re) {
            Slog.w(LOG_TAG, "Failed to get accessibility button availability.", re);
            re.rethrowFromSystemServer();
            return false;
        }
    }

    public void registerAccessibilityButtonCallback(AccessibilityButtonCallback callback) {
        registerAccessibilityButtonCallback(callback, new Handler(Looper.getMainLooper()));
    }

    public void registerAccessibilityButtonCallback(AccessibilityButtonCallback callback, Handler handler) {
        Preconditions.checkNotNull(callback);
        Preconditions.checkNotNull(handler);
        synchronized (this.mLock) {
            if (this.mCallbacks == null) {
                this.mCallbacks = new ArrayMap<>();
            }
            this.mCallbacks.put(callback, handler);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x001f, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void unregisterAccessibilityButtonCallback(android.accessibilityservice.AccessibilityButtonController.AccessibilityButtonCallback r5) {
        /*
            r4 = this;
            com.android.internal.util.Preconditions.checkNotNull(r5)
            java.lang.Object r0 = r4.mLock
            monitor-enter(r0)
            android.util.ArrayMap<android.accessibilityservice.AccessibilityButtonController$AccessibilityButtonCallback, android.os.Handler> r1 = r4.mCallbacks     // Catch:{ all -> 0x0020 }
            if (r1 != 0) goto L_0x000c
            monitor-exit(r0)     // Catch:{ all -> 0x0020 }
            return
        L_0x000c:
            android.util.ArrayMap<android.accessibilityservice.AccessibilityButtonController$AccessibilityButtonCallback, android.os.Handler> r1 = r4.mCallbacks     // Catch:{ all -> 0x0020 }
            int r1 = r1.indexOfKey(r5)     // Catch:{ all -> 0x0020 }
            if (r1 < 0) goto L_0x0016
            r2 = 1
            goto L_0x0017
        L_0x0016:
            r2 = 0
        L_0x0017:
            if (r2 == 0) goto L_0x001e
            android.util.ArrayMap<android.accessibilityservice.AccessibilityButtonController$AccessibilityButtonCallback, android.os.Handler> r3 = r4.mCallbacks     // Catch:{ all -> 0x0020 }
            r3.removeAt(r1)     // Catch:{ all -> 0x0020 }
        L_0x001e:
            monitor-exit(r0)     // Catch:{ all -> 0x0020 }
            return
        L_0x0020:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0020 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.accessibilityservice.AccessibilityButtonController.unregisterAccessibilityButtonCallback(android.accessibilityservice.AccessibilityButtonController$AccessibilityButtonCallback):void");
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0018, code lost:
        r0 = 0;
        r2 = r1.size();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x001d, code lost:
        if (r0 >= r2) goto L_0x0036;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x001f, code lost:
        r1.valueAt(r0).post(new android.accessibilityservice.$$Lambda$AccessibilityButtonController$b_UAM9QJWcH4KQOC_odiN0t_boU(r6, r1.keyAt(r0)));
        r0 = r0 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0036, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void dispatchAccessibilityButtonClicked() {
        /*
            r6 = this;
            java.lang.Object r0 = r6.mLock
            monitor-enter(r0)
            android.util.ArrayMap<android.accessibilityservice.AccessibilityButtonController$AccessibilityButtonCallback, android.os.Handler> r1 = r6.mCallbacks     // Catch:{ all -> 0x0040 }
            if (r1 == 0) goto L_0x0037
            android.util.ArrayMap<android.accessibilityservice.AccessibilityButtonController$AccessibilityButtonCallback, android.os.Handler> r1 = r6.mCallbacks     // Catch:{ all -> 0x0040 }
            boolean r1 = r1.isEmpty()     // Catch:{ all -> 0x0040 }
            if (r1 == 0) goto L_0x0010
            goto L_0x0037
        L_0x0010:
            android.util.ArrayMap r1 = new android.util.ArrayMap     // Catch:{ all -> 0x0040 }
            android.util.ArrayMap<android.accessibilityservice.AccessibilityButtonController$AccessibilityButtonCallback, android.os.Handler> r2 = r6.mCallbacks     // Catch:{ all -> 0x0040 }
            r1.<init>(r2)     // Catch:{ all -> 0x0040 }
            monitor-exit(r0)     // Catch:{ all -> 0x0040 }
            r0 = 0
            int r2 = r1.size()
        L_0x001d:
            if (r0 >= r2) goto L_0x0036
            java.lang.Object r3 = r1.keyAt(r0)
            android.accessibilityservice.AccessibilityButtonController$AccessibilityButtonCallback r3 = (android.accessibilityservice.AccessibilityButtonController.AccessibilityButtonCallback) r3
            java.lang.Object r4 = r1.valueAt(r0)
            android.os.Handler r4 = (android.os.Handler) r4
            android.accessibilityservice.-$$Lambda$AccessibilityButtonController$b_UAM9QJWcH4KQOC_odiN0t_boU r5 = new android.accessibilityservice.-$$Lambda$AccessibilityButtonController$b_UAM9QJWcH4KQOC_odiN0t_boU
            r5.<init>(r3)
            r4.post(r5)
            int r0 = r0 + 1
            goto L_0x001d
        L_0x0036:
            return
        L_0x0037:
            java.lang.String r1 = "A11yButtonController"
            java.lang.String r2 = "Received accessibility button click with no callbacks!"
            android.util.Slog.w((java.lang.String) r1, (java.lang.String) r2)     // Catch:{ all -> 0x0040 }
            monitor-exit(r0)     // Catch:{ all -> 0x0040 }
            return
        L_0x0040:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0040 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.accessibilityservice.AccessibilityButtonController.dispatchAccessibilityButtonClicked():void");
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0018, code lost:
        r0 = 0;
        r2 = r1.size();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x001d, code lost:
        if (r0 >= r2) goto L_0x0036;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x001f, code lost:
        r1.valueAt(r0).post(new android.accessibilityservice.$$Lambda$AccessibilityButtonController$RskKrfcSyUz7I9Sqaziy1P990ZM(r6, r1.keyAt(r0), r7));
        r0 = r0 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0036, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void dispatchAccessibilityButtonAvailabilityChanged(boolean r7) {
        /*
            r6 = this;
            java.lang.Object r0 = r6.mLock
            monitor-enter(r0)
            android.util.ArrayMap<android.accessibilityservice.AccessibilityButtonController$AccessibilityButtonCallback, android.os.Handler> r1 = r6.mCallbacks     // Catch:{ all -> 0x0040 }
            if (r1 == 0) goto L_0x0037
            android.util.ArrayMap<android.accessibilityservice.AccessibilityButtonController$AccessibilityButtonCallback, android.os.Handler> r1 = r6.mCallbacks     // Catch:{ all -> 0x0040 }
            boolean r1 = r1.isEmpty()     // Catch:{ all -> 0x0040 }
            if (r1 == 0) goto L_0x0010
            goto L_0x0037
        L_0x0010:
            android.util.ArrayMap r1 = new android.util.ArrayMap     // Catch:{ all -> 0x0040 }
            android.util.ArrayMap<android.accessibilityservice.AccessibilityButtonController$AccessibilityButtonCallback, android.os.Handler> r2 = r6.mCallbacks     // Catch:{ all -> 0x0040 }
            r1.<init>(r2)     // Catch:{ all -> 0x0040 }
            monitor-exit(r0)     // Catch:{ all -> 0x0040 }
            r0 = 0
            int r2 = r1.size()
        L_0x001d:
            if (r0 >= r2) goto L_0x0036
            java.lang.Object r3 = r1.keyAt(r0)
            android.accessibilityservice.AccessibilityButtonController$AccessibilityButtonCallback r3 = (android.accessibilityservice.AccessibilityButtonController.AccessibilityButtonCallback) r3
            java.lang.Object r4 = r1.valueAt(r0)
            android.os.Handler r4 = (android.os.Handler) r4
            android.accessibilityservice.-$$Lambda$AccessibilityButtonController$RskKrfcSyUz7I9Sqaziy1P990ZM r5 = new android.accessibilityservice.-$$Lambda$AccessibilityButtonController$RskKrfcSyUz7I9Sqaziy1P990ZM
            r5.<init>(r3, r7)
            r4.post(r5)
            int r0 = r0 + 1
            goto L_0x001d
        L_0x0036:
            return
        L_0x0037:
            java.lang.String r1 = "A11yButtonController"
            java.lang.String r2 = "Received accessibility button availability change with no callbacks!"
            android.util.Slog.w((java.lang.String) r1, (java.lang.String) r2)     // Catch:{ all -> 0x0040 }
            monitor-exit(r0)     // Catch:{ all -> 0x0040 }
            return
        L_0x0040:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0040 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.accessibilityservice.AccessibilityButtonController.dispatchAccessibilityButtonAvailabilityChanged(boolean):void");
    }

    public static abstract class AccessibilityButtonCallback {
        public void onClicked(AccessibilityButtonController controller) {
        }

        public void onAvailabilityChanged(AccessibilityButtonController controller, boolean available) {
        }
    }
}
