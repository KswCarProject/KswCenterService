package android.view.accessibility;

import android.Manifest;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ServiceInfo;
import android.content.res.Resources;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.ArrayMap;
import android.util.Log;
import android.util.SparseArray;
import android.view.IWindow;
import android.view.accessibility.IAccessibilityManager;
import android.view.accessibility.IAccessibilityManagerClient;
import com.android.internal.R;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.util.IntPair;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class AccessibilityManager {
    public static final String ACTION_CHOOSE_ACCESSIBILITY_BUTTON = "com.android.internal.intent.action.CHOOSE_ACCESSIBILITY_BUTTON";
    public static final int AUTOCLICK_DELAY_DEFAULT = 600;
    public static final int DALTONIZER_CORRECT_DEUTERANOMALY = 12;
    public static final int DALTONIZER_DISABLED = -1;
    @UnsupportedAppUsage
    public static final int DALTONIZER_SIMULATE_MONOCHROMACY = 0;
    private static final boolean DEBUG = false;
    public static final int FLAG_CONTENT_CONTROLS = 4;
    public static final int FLAG_CONTENT_ICONS = 1;
    public static final int FLAG_CONTENT_TEXT = 2;
    private static final String LOG_TAG = "AccessibilityManager";
    public static final int STATE_FLAG_ACCESSIBILITY_ENABLED = 1;
    public static final int STATE_FLAG_HIGH_TEXT_CONTRAST_ENABLED = 4;
    public static final int STATE_FLAG_TOUCH_EXPLORATION_ENABLED = 2;
    @UnsupportedAppUsage
    private static AccessibilityManager sInstance;
    @UnsupportedAppUsage
    static final Object sInstanceSync = new Object();
    AccessibilityPolicy mAccessibilityPolicy;
    @UnsupportedAppUsage
    private final ArrayMap<AccessibilityStateChangeListener, Handler> mAccessibilityStateChangeListeners = new ArrayMap<>();
    final Handler.Callback mCallback = new MyCallback();
    private final IAccessibilityManagerClient.Stub mClient = new IAccessibilityManagerClient.Stub() {
        public void setState(int state) {
            AccessibilityManager.this.mHandler.obtainMessage(1, state, 0).sendToTarget();
        }

        /* JADX WARNING: Code restructure failed: missing block: B:10:0x002b, code lost:
            if (r2 >= r0) goto L_0x0050;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:11:0x002d, code lost:
            ((android.os.Handler) android.view.accessibility.AccessibilityManager.access$200(r6.this$0).valueAt(r2)).post(new android.view.accessibility.$$Lambda$AccessibilityManager$1$o7fCplskH9NlBwJvkl6NoZ0L_BA(r6, (android.view.accessibility.AccessibilityManager.AccessibilityServicesStateChangeListener) android.view.accessibility.AccessibilityManager.access$200(r6.this$0).keyAt(r2)));
            r2 = r2 + 1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:12:0x0050, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:9:0x0026, code lost:
            r0 = r1.size();
            r2 = 0;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void notifyServicesStateChanged(long r7) {
            /*
                r6 = this;
                android.view.accessibility.AccessibilityManager r0 = android.view.accessibility.AccessibilityManager.this
                r0.updateUiTimeout(r7)
                android.view.accessibility.AccessibilityManager r0 = android.view.accessibility.AccessibilityManager.this
                java.lang.Object r0 = r0.mLock
                monitor-enter(r0)
                android.view.accessibility.AccessibilityManager r1 = android.view.accessibility.AccessibilityManager.this     // Catch:{ all -> 0x0051 }
                android.util.ArrayMap r1 = r1.mServicesStateChangeListeners     // Catch:{ all -> 0x0051 }
                boolean r1 = r1.isEmpty()     // Catch:{ all -> 0x0051 }
                if (r1 == 0) goto L_0x001a
                monitor-exit(r0)     // Catch:{ all -> 0x0051 }
                return
            L_0x001a:
                android.util.ArrayMap r1 = new android.util.ArrayMap     // Catch:{ all -> 0x0051 }
                android.view.accessibility.AccessibilityManager r2 = android.view.accessibility.AccessibilityManager.this     // Catch:{ all -> 0x0051 }
                android.util.ArrayMap r2 = r2.mServicesStateChangeListeners     // Catch:{ all -> 0x0051 }
                r1.<init>(r2)     // Catch:{ all -> 0x0051 }
                monitor-exit(r0)     // Catch:{ all -> 0x0051 }
                int r0 = r1.size()
                r2 = 0
            L_0x002b:
                if (r2 >= r0) goto L_0x0050
                android.view.accessibility.AccessibilityManager r3 = android.view.accessibility.AccessibilityManager.this
                android.util.ArrayMap r3 = r3.mServicesStateChangeListeners
                java.lang.Object r3 = r3.keyAt(r2)
                android.view.accessibility.AccessibilityManager$AccessibilityServicesStateChangeListener r3 = (android.view.accessibility.AccessibilityManager.AccessibilityServicesStateChangeListener) r3
                android.view.accessibility.AccessibilityManager r4 = android.view.accessibility.AccessibilityManager.this
                android.util.ArrayMap r4 = r4.mServicesStateChangeListeners
                java.lang.Object r4 = r4.valueAt(r2)
                android.os.Handler r4 = (android.os.Handler) r4
                android.view.accessibility.-$$Lambda$AccessibilityManager$1$o7fCplskH9NlBwJvkl6NoZ0L_BA r5 = new android.view.accessibility.-$$Lambda$AccessibilityManager$1$o7fCplskH9NlBwJvkl6NoZ0L_BA
                r5.<init>(r3)
                r4.post(r5)
                int r2 = r2 + 1
                goto L_0x002b
            L_0x0050:
                return
            L_0x0051:
                r1 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x0051 }
                throw r1
            */
            throw new UnsupportedOperationException("Method not decompiled: android.view.accessibility.AccessibilityManager.AnonymousClass1.notifyServicesStateChanged(long):void");
        }

        public void setRelevantEventTypes(int eventTypes) {
            AccessibilityManager.this.mRelevantEventTypes = eventTypes;
        }
    };
    @UnsupportedAppUsage
    final Handler mHandler;
    private final ArrayMap<HighTextContrastChangeListener, Handler> mHighTextContrastStateChangeListeners = new ArrayMap<>();
    int mInteractiveUiTimeout;
    @UnsupportedAppUsage(maxTargetSdk = 28)
    boolean mIsEnabled;
    @UnsupportedAppUsage(trackingBug = 123768939)
    boolean mIsHighTextContrastEnabled;
    boolean mIsTouchExplorationEnabled;
    /* access modifiers changed from: private */
    @UnsupportedAppUsage
    public final Object mLock = new Object();
    int mNonInteractiveUiTimeout;
    int mRelevantEventTypes = -1;
    private SparseArray<List<AccessibilityRequestPreparer>> mRequestPreparerLists;
    @UnsupportedAppUsage
    private IAccessibilityManager mService;
    /* access modifiers changed from: private */
    public final ArrayMap<AccessibilityServicesStateChangeListener, Handler> mServicesStateChangeListeners = new ArrayMap<>();
    private final ArrayMap<TouchExplorationStateChangeListener, Handler> mTouchExplorationStateChangeListeners = new ArrayMap<>();
    @UnsupportedAppUsage
    final int mUserId;

    public interface AccessibilityPolicy {
        List<AccessibilityServiceInfo> getEnabledAccessibilityServiceList(int i, List<AccessibilityServiceInfo> list);

        List<AccessibilityServiceInfo> getInstalledAccessibilityServiceList(List<AccessibilityServiceInfo> list);

        int getRelevantEventTypes(int i);

        boolean isEnabled(boolean z);

        AccessibilityEvent onAccessibilityEvent(AccessibilityEvent accessibilityEvent, boolean z, int i);
    }

    public interface AccessibilityServicesStateChangeListener {
        void onAccessibilityServicesStateChanged(AccessibilityManager accessibilityManager);
    }

    public interface AccessibilityStateChangeListener {
        void onAccessibilityStateChanged(boolean z);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ContentFlag {
    }

    public interface HighTextContrastChangeListener {
        void onHighTextContrastStateChanged(boolean z);
    }

    public interface TouchExplorationStateChangeListener {
        void onTouchExplorationStateChanged(boolean z);
    }

    @UnsupportedAppUsage
    public static AccessibilityManager getInstance(Context context) {
        int userId;
        synchronized (sInstanceSync) {
            if (sInstance == null) {
                if (!(Binder.getCallingUid() == 1000 || context.checkCallingOrSelfPermission(Manifest.permission.INTERACT_ACROSS_USERS) == 0)) {
                    if (context.checkCallingOrSelfPermission(Manifest.permission.INTERACT_ACROSS_USERS_FULL) != 0) {
                        userId = context.getUserId();
                        sInstance = new AccessibilityManager(context, (IAccessibilityManager) null, userId);
                    }
                }
                userId = -2;
                sInstance = new AccessibilityManager(context, (IAccessibilityManager) null, userId);
            }
        }
        return sInstance;
    }

    public AccessibilityManager(Context context, IAccessibilityManager service, int userId) {
        this.mHandler = new Handler(context.getMainLooper(), this.mCallback);
        this.mUserId = userId;
        synchronized (this.mLock) {
            tryConnectToServiceLocked(service);
        }
    }

    public AccessibilityManager(Handler handler, IAccessibilityManager service, int userId) {
        this.mHandler = handler;
        this.mUserId = userId;
        synchronized (this.mLock) {
            tryConnectToServiceLocked(service);
        }
    }

    public IAccessibilityManagerClient getClient() {
        return this.mClient;
    }

    @VisibleForTesting
    public Handler.Callback getCallback() {
        return this.mCallback;
    }

    public boolean isEnabled() {
        boolean z;
        synchronized (this.mLock) {
            if (!this.mIsEnabled) {
                if (this.mAccessibilityPolicy == null || !this.mAccessibilityPolicy.isEnabled(this.mIsEnabled)) {
                    z = false;
                }
            }
            z = true;
        }
        return z;
    }

    public boolean isTouchExplorationEnabled() {
        synchronized (this.mLock) {
            if (getServiceLocked() == null) {
                return false;
            }
            boolean z = this.mIsTouchExplorationEnabled;
            return z;
        }
    }

    @UnsupportedAppUsage
    public boolean isHighTextContrastEnabled() {
        synchronized (this.mLock) {
            if (getServiceLocked() == null) {
                return false;
            }
            boolean z = this.mIsHighTextContrastEnabled;
            return z;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:30:?, code lost:
        r4 = android.os.Binder.clearCallingIdentity();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:?, code lost:
        r1.sendAccessibilityEvent(r2, r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:?, code lost:
        android.os.Binder.restoreCallingIdentity(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x005f, code lost:
        if (r8 == r2) goto L_0x0064;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x0061, code lost:
        r8.recycle();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x0064, code lost:
        r2.recycle();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x0068, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:?, code lost:
        android.os.Binder.restoreCallingIdentity(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x006c, code lost:
        throw r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x006d, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x006f, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:?, code lost:
        android.util.Log.e(LOG_TAG, "Error during sending " + r2 + android.net.wifi.WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER, r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x008b, code lost:
        if (r8 == r2) goto L_0x0064;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x008e, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x008f, code lost:
        if (r8 != r2) goto L_0x0091;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x0091, code lost:
        r8.recycle();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x0094, code lost:
        r2.recycle();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x0097, code lost:
        throw r0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void sendAccessibilityEvent(android.view.accessibility.AccessibilityEvent r8) {
        /*
            r7 = this;
            java.lang.Object r0 = r7.mLock
            monitor-enter(r0)
            android.view.accessibility.IAccessibilityManager r1 = r7.getServiceLocked()     // Catch:{ all -> 0x0098 }
            if (r1 != 0) goto L_0x000b
            monitor-exit(r0)     // Catch:{ all -> 0x0098 }
            return
        L_0x000b:
            long r2 = android.os.SystemClock.uptimeMillis()     // Catch:{ all -> 0x0098 }
            r8.setEventTime(r2)     // Catch:{ all -> 0x0098 }
            android.view.accessibility.AccessibilityManager$AccessibilityPolicy r2 = r7.mAccessibilityPolicy     // Catch:{ all -> 0x0098 }
            if (r2 == 0) goto L_0x0024
            android.view.accessibility.AccessibilityManager$AccessibilityPolicy r2 = r7.mAccessibilityPolicy     // Catch:{ all -> 0x0098 }
            boolean r3 = r7.mIsEnabled     // Catch:{ all -> 0x0098 }
            int r4 = r7.mRelevantEventTypes     // Catch:{ all -> 0x0098 }
            android.view.accessibility.AccessibilityEvent r2 = r2.onAccessibilityEvent(r8, r3, r4)     // Catch:{ all -> 0x0098 }
            if (r2 != 0) goto L_0x0025
            monitor-exit(r0)     // Catch:{ all -> 0x0098 }
            return
        L_0x0024:
            r2 = r8
        L_0x0025:
            boolean r3 = r7.isEnabled()     // Catch:{ all -> 0x0098 }
            if (r3 != 0) goto L_0x0046
            android.os.Looper r3 = android.os.Looper.myLooper()     // Catch:{ all -> 0x0098 }
            android.os.Looper r4 = android.os.Looper.getMainLooper()     // Catch:{ all -> 0x0098 }
            if (r3 == r4) goto L_0x003e
            java.lang.String r4 = "AccessibilityManager"
            java.lang.String r5 = "AccessibilityEvent sent with accessibility disabled"
            android.util.Log.e(r4, r5)     // Catch:{ all -> 0x0098 }
            monitor-exit(r0)     // Catch:{ all -> 0x0098 }
            return
        L_0x003e:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException     // Catch:{ all -> 0x0098 }
            java.lang.String r5 = "Accessibility off. Did you forget to check that?"
            r4.<init>(r5)     // Catch:{ all -> 0x0098 }
            throw r4     // Catch:{ all -> 0x0098 }
        L_0x0046:
            int r3 = r2.getEventType()     // Catch:{ all -> 0x0098 }
            int r4 = r7.mRelevantEventTypes     // Catch:{ all -> 0x0098 }
            r3 = r3 & r4
            if (r3 != 0) goto L_0x0051
            monitor-exit(r0)     // Catch:{ all -> 0x0098 }
            return
        L_0x0051:
            int r3 = r7.mUserId     // Catch:{ all -> 0x0098 }
            monitor-exit(r0)     // Catch:{ all -> 0x0098 }
            long r4 = android.os.Binder.clearCallingIdentity()     // Catch:{ RemoteException -> 0x006f }
            r1.sendAccessibilityEvent(r2, r3)     // Catch:{ all -> 0x0068 }
            android.os.Binder.restoreCallingIdentity(r4)     // Catch:{ RemoteException -> 0x006f }
            if (r8 == r2) goto L_0x0064
        L_0x0061:
            r8.recycle()
        L_0x0064:
            r2.recycle()
            goto L_0x008e
        L_0x0068:
            r0 = move-exception
            android.os.Binder.restoreCallingIdentity(r4)     // Catch:{ RemoteException -> 0x006f }
            throw r0     // Catch:{ RemoteException -> 0x006f }
        L_0x006d:
            r0 = move-exception
            goto L_0x008f
        L_0x006f:
            r0 = move-exception
            java.lang.String r4 = "AccessibilityManager"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x006d }
            r5.<init>()     // Catch:{ all -> 0x006d }
            java.lang.String r6 = "Error during sending "
            r5.append(r6)     // Catch:{ all -> 0x006d }
            r5.append(r2)     // Catch:{ all -> 0x006d }
            java.lang.String r6 = " "
            r5.append(r6)     // Catch:{ all -> 0x006d }
            java.lang.String r5 = r5.toString()     // Catch:{ all -> 0x006d }
            android.util.Log.e(r4, r5, r0)     // Catch:{ all -> 0x006d }
            if (r8 == r2) goto L_0x0064
            goto L_0x0061
        L_0x008e:
            return
        L_0x008f:
            if (r8 == r2) goto L_0x0094
            r8.recycle()
        L_0x0094:
            r2.recycle()
            throw r0
        L_0x0098:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0098 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.accessibility.AccessibilityManager.sendAccessibilityEvent(android.view.accessibility.AccessibilityEvent):void");
    }

    public void interrupt() {
        synchronized (this.mLock) {
            IAccessibilityManager service = getServiceLocked();
            if (service != null) {
                if (isEnabled()) {
                    int userId = this.mUserId;
                    try {
                        service.interrupt(userId);
                    } catch (RemoteException re) {
                        Log.e(LOG_TAG, "Error while requesting interrupt from all services. ", re);
                    }
                } else if (Looper.myLooper() != Looper.getMainLooper()) {
                    Log.e(LOG_TAG, "Interrupt called with accessibility disabled");
                } else {
                    throw new IllegalStateException("Accessibility off. Did you forget to check that?");
                }
            }
        }
    }

    @Deprecated
    public List<ServiceInfo> getAccessibilityServiceList() {
        List<AccessibilityServiceInfo> infos = getInstalledAccessibilityServiceList();
        List<ServiceInfo> services = new ArrayList<>();
        int infoCount = infos.size();
        for (int i = 0; i < infoCount; i++) {
            services.add(infos.get(i).getResolveInfo().serviceInfo);
        }
        return Collections.unmodifiableList(services);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0012, code lost:
        r0 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0017, code lost:
        r0 = r1.getInstalledAccessibilityServiceList(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0019, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x001a, code lost:
        android.util.Log.e(LOG_TAG, "Error while obtaining the installed AccessibilityServices. ", r3);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.util.List<android.accessibilityservice.AccessibilityServiceInfo> getInstalledAccessibilityServiceList() {
        /*
            r6 = this;
            java.lang.Object r0 = r6.mLock
            monitor-enter(r0)
            android.view.accessibility.IAccessibilityManager r1 = r6.getServiceLocked()     // Catch:{ all -> 0x0037 }
            if (r1 != 0) goto L_0x000f
            java.util.List r2 = java.util.Collections.emptyList()     // Catch:{ all -> 0x0037 }
            monitor-exit(r0)     // Catch:{ all -> 0x0037 }
            return r2
        L_0x000f:
            int r2 = r6.mUserId     // Catch:{ all -> 0x0037 }
            monitor-exit(r0)     // Catch:{ all -> 0x0037 }
            r0 = 0
            java.util.List r3 = r1.getInstalledAccessibilityServiceList(r2)     // Catch:{ RemoteException -> 0x0019 }
            r0 = r3
            goto L_0x0021
        L_0x0019:
            r3 = move-exception
            java.lang.String r4 = "AccessibilityManager"
            java.lang.String r5 = "Error while obtaining the installed AccessibilityServices. "
            android.util.Log.e(r4, r5, r3)
        L_0x0021:
            android.view.accessibility.AccessibilityManager$AccessibilityPolicy r3 = r6.mAccessibilityPolicy
            if (r3 == 0) goto L_0x002b
            android.view.accessibility.AccessibilityManager$AccessibilityPolicy r3 = r6.mAccessibilityPolicy
            java.util.List r0 = r3.getInstalledAccessibilityServiceList(r0)
        L_0x002b:
            if (r0 == 0) goto L_0x0032
            java.util.List r3 = java.util.Collections.unmodifiableList(r0)
            return r3
        L_0x0032:
            java.util.List r3 = java.util.Collections.emptyList()
            return r3
        L_0x0037:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0037 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.accessibility.AccessibilityManager.getInstalledAccessibilityServiceList():java.util.List");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0012, code lost:
        r0 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0017, code lost:
        r0 = r1.getEnabledAccessibilityServiceList(r7, r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0019, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x001a, code lost:
        android.util.Log.e(LOG_TAG, "Error while obtaining the installed AccessibilityServices. ", r3);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.util.List<android.accessibilityservice.AccessibilityServiceInfo> getEnabledAccessibilityServiceList(int r7) {
        /*
            r6 = this;
            java.lang.Object r0 = r6.mLock
            monitor-enter(r0)
            android.view.accessibility.IAccessibilityManager r1 = r6.getServiceLocked()     // Catch:{ all -> 0x0037 }
            if (r1 != 0) goto L_0x000f
            java.util.List r2 = java.util.Collections.emptyList()     // Catch:{ all -> 0x0037 }
            monitor-exit(r0)     // Catch:{ all -> 0x0037 }
            return r2
        L_0x000f:
            int r2 = r6.mUserId     // Catch:{ all -> 0x0037 }
            monitor-exit(r0)     // Catch:{ all -> 0x0037 }
            r0 = 0
            java.util.List r3 = r1.getEnabledAccessibilityServiceList(r7, r2)     // Catch:{ RemoteException -> 0x0019 }
            r0 = r3
            goto L_0x0021
        L_0x0019:
            r3 = move-exception
            java.lang.String r4 = "AccessibilityManager"
            java.lang.String r5 = "Error while obtaining the installed AccessibilityServices. "
            android.util.Log.e(r4, r5, r3)
        L_0x0021:
            android.view.accessibility.AccessibilityManager$AccessibilityPolicy r3 = r6.mAccessibilityPolicy
            if (r3 == 0) goto L_0x002b
            android.view.accessibility.AccessibilityManager$AccessibilityPolicy r3 = r6.mAccessibilityPolicy
            java.util.List r0 = r3.getEnabledAccessibilityServiceList(r7, r0)
        L_0x002b:
            if (r0 == 0) goto L_0x0032
            java.util.List r3 = java.util.Collections.unmodifiableList(r0)
            return r3
        L_0x0032:
            java.util.List r3 = java.util.Collections.emptyList()
            return r3
        L_0x0037:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0037 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.accessibility.AccessibilityManager.getEnabledAccessibilityServiceList(int):java.util.List");
    }

    public boolean addAccessibilityStateChangeListener(AccessibilityStateChangeListener listener) {
        addAccessibilityStateChangeListener(listener, (Handler) null);
        return true;
    }

    public void addAccessibilityStateChangeListener(AccessibilityStateChangeListener listener, Handler handler) {
        synchronized (this.mLock) {
            this.mAccessibilityStateChangeListeners.put(listener, handler == null ? this.mHandler : handler);
        }
    }

    public boolean removeAccessibilityStateChangeListener(AccessibilityStateChangeListener listener) {
        boolean z;
        synchronized (this.mLock) {
            int index = this.mAccessibilityStateChangeListeners.indexOfKey(listener);
            this.mAccessibilityStateChangeListeners.remove(listener);
            z = index >= 0;
        }
        return z;
    }

    public boolean addTouchExplorationStateChangeListener(TouchExplorationStateChangeListener listener) {
        addTouchExplorationStateChangeListener(listener, (Handler) null);
        return true;
    }

    public void addTouchExplorationStateChangeListener(TouchExplorationStateChangeListener listener, Handler handler) {
        synchronized (this.mLock) {
            this.mTouchExplorationStateChangeListeners.put(listener, handler == null ? this.mHandler : handler);
        }
    }

    public boolean removeTouchExplorationStateChangeListener(TouchExplorationStateChangeListener listener) {
        boolean z;
        synchronized (this.mLock) {
            int index = this.mTouchExplorationStateChangeListeners.indexOfKey(listener);
            this.mTouchExplorationStateChangeListeners.remove(listener);
            z = index >= 0;
        }
        return z;
    }

    public void addAccessibilityServicesStateChangeListener(AccessibilityServicesStateChangeListener listener, Handler handler) {
        synchronized (this.mLock) {
            this.mServicesStateChangeListeners.put(listener, handler == null ? this.mHandler : handler);
        }
    }

    public void removeAccessibilityServicesStateChangeListener(AccessibilityServicesStateChangeListener listener) {
        synchronized (this.mLock) {
            this.mServicesStateChangeListeners.remove(listener);
        }
    }

    public void addAccessibilityRequestPreparer(AccessibilityRequestPreparer preparer) {
        if (this.mRequestPreparerLists == null) {
            this.mRequestPreparerLists = new SparseArray<>(1);
        }
        int id = preparer.getAccessibilityViewId();
        List<AccessibilityRequestPreparer> requestPreparerList = this.mRequestPreparerLists.get(id);
        if (requestPreparerList == null) {
            requestPreparerList = new ArrayList<>(1);
            this.mRequestPreparerLists.put(id, requestPreparerList);
        }
        requestPreparerList.add(preparer);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0005, code lost:
        r0 = r4.getAccessibilityViewId();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void removeAccessibilityRequestPreparer(android.view.accessibility.AccessibilityRequestPreparer r4) {
        /*
            r3 = this;
            android.util.SparseArray<java.util.List<android.view.accessibility.AccessibilityRequestPreparer>> r0 = r3.mRequestPreparerLists
            if (r0 != 0) goto L_0x0005
            return
        L_0x0005:
            int r0 = r4.getAccessibilityViewId()
            android.util.SparseArray<java.util.List<android.view.accessibility.AccessibilityRequestPreparer>> r1 = r3.mRequestPreparerLists
            java.lang.Object r1 = r1.get(r0)
            java.util.List r1 = (java.util.List) r1
            if (r1 == 0) goto L_0x0021
            r1.remove(r4)
            boolean r2 = r1.isEmpty()
            if (r2 == 0) goto L_0x0021
            android.util.SparseArray<java.util.List<android.view.accessibility.AccessibilityRequestPreparer>> r2 = r3.mRequestPreparerLists
            r2.remove(r0)
        L_0x0021:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.accessibility.AccessibilityManager.removeAccessibilityRequestPreparer(android.view.accessibility.AccessibilityRequestPreparer):void");
    }

    public int getRecommendedTimeoutMillis(int originalTimeout, int uiContentFlags) {
        boolean hasIconsOrText = false;
        boolean hasControls = (uiContentFlags & 4) != 0;
        if (!((uiContentFlags & 1) == 0 && (uiContentFlags & 2) == 0)) {
            hasIconsOrText = true;
        }
        int recommendedTimeout = originalTimeout;
        if (hasControls) {
            recommendedTimeout = Math.max(recommendedTimeout, this.mInteractiveUiTimeout);
        }
        if (hasIconsOrText) {
            return Math.max(recommendedTimeout, this.mNonInteractiveUiTimeout);
        }
        return recommendedTimeout;
    }

    public List<AccessibilityRequestPreparer> getRequestPreparersForAccessibilityId(int id) {
        if (this.mRequestPreparerLists == null) {
            return null;
        }
        return this.mRequestPreparerLists.get(id);
    }

    public void addHighTextContrastStateChangeListener(HighTextContrastChangeListener listener, Handler handler) {
        synchronized (this.mLock) {
            this.mHighTextContrastStateChangeListeners.put(listener, handler == null ? this.mHandler : handler);
        }
    }

    public void removeHighTextContrastStateChangeListener(HighTextContrastChangeListener listener) {
        synchronized (this.mLock) {
            this.mHighTextContrastStateChangeListeners.remove(listener);
        }
    }

    public void setAccessibilityPolicy(AccessibilityPolicy policy) {
        synchronized (this.mLock) {
            this.mAccessibilityPolicy = policy;
        }
    }

    public boolean isAccessibilityVolumeStreamActive() {
        List<AccessibilityServiceInfo> serviceInfos = getEnabledAccessibilityServiceList(-1);
        for (int i = 0; i < serviceInfos.size(); i++) {
            if ((serviceInfos.get(i).flags & 128) != 0) {
                return true;
            }
        }
        return false;
    }

    public boolean sendFingerprintGesture(int keyCode) {
        synchronized (this.mLock) {
            IAccessibilityManager service = getServiceLocked();
            if (service == null) {
                return false;
            }
            try {
                return service.sendFingerprintGesture(keyCode);
            } catch (RemoteException e) {
                return false;
            }
        }
    }

    @SystemApi
    public int getAccessibilityWindowId(IBinder windowToken) {
        if (windowToken == null) {
            return -1;
        }
        synchronized (this.mLock) {
            IAccessibilityManager service = getServiceLocked();
            if (service == null) {
                return -1;
            }
            try {
                return service.getAccessibilityWindowId(windowToken);
            } catch (RemoteException e) {
                return -1;
            }
        }
    }

    /* access modifiers changed from: private */
    @UnsupportedAppUsage
    public void setStateLocked(int stateFlags) {
        boolean highTextContrastEnabled = false;
        boolean enabled = (stateFlags & 1) != 0;
        boolean touchExplorationEnabled = (stateFlags & 2) != 0;
        if ((stateFlags & 4) != 0) {
            highTextContrastEnabled = true;
        }
        boolean wasEnabled = isEnabled();
        boolean wasTouchExplorationEnabled = this.mIsTouchExplorationEnabled;
        boolean wasHighTextContrastEnabled = this.mIsHighTextContrastEnabled;
        this.mIsEnabled = enabled;
        this.mIsTouchExplorationEnabled = touchExplorationEnabled;
        this.mIsHighTextContrastEnabled = highTextContrastEnabled;
        if (wasEnabled != isEnabled()) {
            notifyAccessibilityStateChanged();
        }
        if (wasTouchExplorationEnabled != touchExplorationEnabled) {
            notifyTouchExplorationStateChanged();
        }
        if (wasHighTextContrastEnabled != highTextContrastEnabled) {
            notifyHighTextContrastStateChanged();
        }
    }

    public AccessibilityServiceInfo getInstalledServiceInfoWithComponentName(ComponentName componentName) {
        List<AccessibilityServiceInfo> installedServiceInfos = getInstalledAccessibilityServiceList();
        if (installedServiceInfos == null || componentName == null) {
            return null;
        }
        for (int i = 0; i < installedServiceInfos.size(); i++) {
            if (componentName.equals(installedServiceInfos.get(i).getComponentName())) {
                return installedServiceInfos.get(i);
            }
        }
        return null;
    }

    public int addAccessibilityInteractionConnection(IWindow windowToken, String packageName, IAccessibilityInteractionConnection connection) {
        synchronized (this.mLock) {
            IAccessibilityManager service = getServiceLocked();
            if (service == null) {
                return -1;
            }
            int userId = this.mUserId;
            try {
                return service.addAccessibilityInteractionConnection(windowToken, connection, packageName, userId);
            } catch (RemoteException re) {
                Log.e(LOG_TAG, "Error while adding an accessibility interaction connection. ", re);
                return -1;
            }
        }
    }

    public void removeAccessibilityInteractionConnection(IWindow windowToken) {
        synchronized (this.mLock) {
            IAccessibilityManager service = getServiceLocked();
            if (service != null) {
                try {
                    service.removeAccessibilityInteractionConnection(windowToken);
                } catch (RemoteException re) {
                    Log.e(LOG_TAG, "Error while removing an accessibility interaction connection. ", re);
                }
            }
        }
    }

    @SystemApi
    public void performAccessibilityShortcut() {
        synchronized (this.mLock) {
            IAccessibilityManager service = getServiceLocked();
            if (service != null) {
                try {
                    service.performAccessibilityShortcut();
                } catch (RemoteException re) {
                    Log.e(LOG_TAG, "Error performing accessibility shortcut. ", re);
                }
            }
        }
    }

    public void notifyAccessibilityButtonClicked(int displayId) {
        synchronized (this.mLock) {
            IAccessibilityManager service = getServiceLocked();
            if (service != null) {
                try {
                    service.notifyAccessibilityButtonClicked(displayId);
                } catch (RemoteException re) {
                    Log.e(LOG_TAG, "Error while dispatching accessibility button click", re);
                }
            }
        }
    }

    public void notifyAccessibilityButtonVisibilityChanged(boolean shown) {
        synchronized (this.mLock) {
            IAccessibilityManager service = getServiceLocked();
            if (service != null) {
                try {
                    service.notifyAccessibilityButtonVisibilityChanged(shown);
                } catch (RemoteException re) {
                    Log.e(LOG_TAG, "Error while dispatching accessibility button visibility change", re);
                }
            }
        }
    }

    public void setPictureInPictureActionReplacingConnection(IAccessibilityInteractionConnection connection) {
        synchronized (this.mLock) {
            IAccessibilityManager service = getServiceLocked();
            if (service != null) {
                try {
                    service.setPictureInPictureActionReplacingConnection(connection);
                } catch (RemoteException re) {
                    Log.e(LOG_TAG, "Error setting picture in picture action replacement", re);
                }
            }
        }
    }

    public String getAccessibilityShortcutService() {
        IAccessibilityManager service;
        synchronized (this.mLock) {
            service = getServiceLocked();
        }
        if (service == null) {
            return null;
        }
        try {
            return service.getAccessibilityShortcutService();
        } catch (RemoteException re) {
            re.rethrowFromSystemServer();
            return null;
        }
    }

    private IAccessibilityManager getServiceLocked() {
        if (this.mService == null) {
            tryConnectToServiceLocked((IAccessibilityManager) null);
        }
        return this.mService;
    }

    private void tryConnectToServiceLocked(IAccessibilityManager service) {
        if (service == null) {
            IBinder iBinder = ServiceManager.getService(Context.ACCESSIBILITY_SERVICE);
            if (iBinder != null) {
                service = IAccessibilityManager.Stub.asInterface(iBinder);
            } else {
                return;
            }
        }
        try {
            long userStateAndRelevantEvents = service.addClient(this.mClient, this.mUserId);
            setStateLocked(IntPair.first(userStateAndRelevantEvents));
            this.mRelevantEventTypes = IntPair.second(userStateAndRelevantEvents);
            updateUiTimeout(service.getRecommendedTimeoutMillis());
            this.mService = service;
        } catch (RemoteException re) {
            Log.e(LOG_TAG, "AccessibilityManagerService is dead", re);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x001e, code lost:
        if (r3 >= r0) goto L_0x0037;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0020, code lost:
        r2.valueAt(r3).post(new android.view.accessibility.$$Lambda$AccessibilityManager$yzw5NYY7_MfAQ9gLy3mVllchaXo(r2.keyAt(r3), r1));
        r3 = r3 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0037, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0019, code lost:
        r0 = r2.size();
        r3 = 0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void notifyAccessibilityStateChanged() {
        /*
            r7 = this;
            java.lang.Object r0 = r7.mLock
            monitor-enter(r0)
            android.util.ArrayMap<android.view.accessibility.AccessibilityManager$AccessibilityStateChangeListener, android.os.Handler> r1 = r7.mAccessibilityStateChangeListeners     // Catch:{ all -> 0x0038 }
            boolean r1 = r1.isEmpty()     // Catch:{ all -> 0x0038 }
            if (r1 == 0) goto L_0x000d
            monitor-exit(r0)     // Catch:{ all -> 0x0038 }
            return
        L_0x000d:
            boolean r1 = r7.isEnabled()     // Catch:{ all -> 0x0038 }
            android.util.ArrayMap r2 = new android.util.ArrayMap     // Catch:{ all -> 0x0038 }
            android.util.ArrayMap<android.view.accessibility.AccessibilityManager$AccessibilityStateChangeListener, android.os.Handler> r3 = r7.mAccessibilityStateChangeListeners     // Catch:{ all -> 0x0038 }
            r2.<init>(r3)     // Catch:{ all -> 0x0038 }
            monitor-exit(r0)     // Catch:{ all -> 0x0038 }
            int r0 = r2.size()
            r3 = 0
        L_0x001e:
            if (r3 >= r0) goto L_0x0037
            java.lang.Object r4 = r2.keyAt(r3)
            android.view.accessibility.AccessibilityManager$AccessibilityStateChangeListener r4 = (android.view.accessibility.AccessibilityManager.AccessibilityStateChangeListener) r4
            java.lang.Object r5 = r2.valueAt(r3)
            android.os.Handler r5 = (android.os.Handler) r5
            android.view.accessibility.-$$Lambda$AccessibilityManager$yzw5NYY7_MfAQ9gLy3mVllchaXo r6 = new android.view.accessibility.-$$Lambda$AccessibilityManager$yzw5NYY7_MfAQ9gLy3mVllchaXo
            r6.<init>(r1)
            r5.post(r6)
            int r3 = r3 + 1
            goto L_0x001e
        L_0x0037:
            return
        L_0x0038:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0038 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.accessibility.AccessibilityManager.notifyAccessibilityStateChanged():void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x001c, code lost:
        if (r3 >= r0) goto L_0x0035;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x001e, code lost:
        r2.valueAt(r3).post(new android.view.accessibility.$$Lambda$AccessibilityManager$a0OtrjOl35tiW2vwyvAmY6_LiLI(r2.keyAt(r3), r1));
        r3 = r3 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0035, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0017, code lost:
        r0 = r2.size();
        r3 = 0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void notifyTouchExplorationStateChanged() {
        /*
            r7 = this;
            java.lang.Object r0 = r7.mLock
            monitor-enter(r0)
            android.util.ArrayMap<android.view.accessibility.AccessibilityManager$TouchExplorationStateChangeListener, android.os.Handler> r1 = r7.mTouchExplorationStateChangeListeners     // Catch:{ all -> 0x0036 }
            boolean r1 = r1.isEmpty()     // Catch:{ all -> 0x0036 }
            if (r1 == 0) goto L_0x000d
            monitor-exit(r0)     // Catch:{ all -> 0x0036 }
            return
        L_0x000d:
            boolean r1 = r7.mIsTouchExplorationEnabled     // Catch:{ all -> 0x0036 }
            android.util.ArrayMap r2 = new android.util.ArrayMap     // Catch:{ all -> 0x0036 }
            android.util.ArrayMap<android.view.accessibility.AccessibilityManager$TouchExplorationStateChangeListener, android.os.Handler> r3 = r7.mTouchExplorationStateChangeListeners     // Catch:{ all -> 0x0036 }
            r2.<init>(r3)     // Catch:{ all -> 0x0036 }
            monitor-exit(r0)     // Catch:{ all -> 0x0036 }
            int r0 = r2.size()
            r3 = 0
        L_0x001c:
            if (r3 >= r0) goto L_0x0035
            java.lang.Object r4 = r2.keyAt(r3)
            android.view.accessibility.AccessibilityManager$TouchExplorationStateChangeListener r4 = (android.view.accessibility.AccessibilityManager.TouchExplorationStateChangeListener) r4
            java.lang.Object r5 = r2.valueAt(r3)
            android.os.Handler r5 = (android.os.Handler) r5
            android.view.accessibility.-$$Lambda$AccessibilityManager$a0OtrjOl35tiW2vwyvAmY6_LiLI r6 = new android.view.accessibility.-$$Lambda$AccessibilityManager$a0OtrjOl35tiW2vwyvAmY6_LiLI
            r6.<init>(r1)
            r5.post(r6)
            int r3 = r3 + 1
            goto L_0x001c
        L_0x0035:
            return
        L_0x0036:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0036 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.accessibility.AccessibilityManager.notifyTouchExplorationStateChanged():void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x001c, code lost:
        if (r3 >= r0) goto L_0x0035;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x001e, code lost:
        r2.valueAt(r3).post(new android.view.accessibility.$$Lambda$AccessibilityManager$4M6GrmFiqsRwVzn352N10DcU6RM(r2.keyAt(r3), r1));
        r3 = r3 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0035, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0017, code lost:
        r0 = r2.size();
        r3 = 0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void notifyHighTextContrastStateChanged() {
        /*
            r7 = this;
            java.lang.Object r0 = r7.mLock
            monitor-enter(r0)
            android.util.ArrayMap<android.view.accessibility.AccessibilityManager$HighTextContrastChangeListener, android.os.Handler> r1 = r7.mHighTextContrastStateChangeListeners     // Catch:{ all -> 0x0036 }
            boolean r1 = r1.isEmpty()     // Catch:{ all -> 0x0036 }
            if (r1 == 0) goto L_0x000d
            monitor-exit(r0)     // Catch:{ all -> 0x0036 }
            return
        L_0x000d:
            boolean r1 = r7.mIsHighTextContrastEnabled     // Catch:{ all -> 0x0036 }
            android.util.ArrayMap r2 = new android.util.ArrayMap     // Catch:{ all -> 0x0036 }
            android.util.ArrayMap<android.view.accessibility.AccessibilityManager$HighTextContrastChangeListener, android.os.Handler> r3 = r7.mHighTextContrastStateChangeListeners     // Catch:{ all -> 0x0036 }
            r2.<init>(r3)     // Catch:{ all -> 0x0036 }
            monitor-exit(r0)     // Catch:{ all -> 0x0036 }
            int r0 = r2.size()
            r3 = 0
        L_0x001c:
            if (r3 >= r0) goto L_0x0035
            java.lang.Object r4 = r2.keyAt(r3)
            android.view.accessibility.AccessibilityManager$HighTextContrastChangeListener r4 = (android.view.accessibility.AccessibilityManager.HighTextContrastChangeListener) r4
            java.lang.Object r5 = r2.valueAt(r3)
            android.os.Handler r5 = (android.os.Handler) r5
            android.view.accessibility.-$$Lambda$AccessibilityManager$4M6GrmFiqsRwVzn352N10DcU6RM r6 = new android.view.accessibility.-$$Lambda$AccessibilityManager$4M6GrmFiqsRwVzn352N10DcU6RM
            r6.<init>(r1)
            r5.post(r6)
            int r3 = r3 + 1
            goto L_0x001c
        L_0x0035:
            return
        L_0x0036:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0036 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.accessibility.AccessibilityManager.notifyHighTextContrastStateChanged():void");
    }

    /* access modifiers changed from: private */
    public void updateUiTimeout(long uiTimeout) {
        this.mInteractiveUiTimeout = IntPair.first(uiTimeout);
        this.mNonInteractiveUiTimeout = IntPair.second(uiTimeout);
    }

    public static boolean isAccessibilityButtonSupported() {
        return Resources.getSystem().getBoolean(R.bool.config_showNavigationBar);
    }

    private final class MyCallback implements Handler.Callback {
        public static final int MSG_SET_STATE = 1;

        private MyCallback() {
        }

        public boolean handleMessage(Message message) {
            if (message.what == 1) {
                int state = message.arg1;
                synchronized (AccessibilityManager.this.mLock) {
                    AccessibilityManager.this.setStateLocked(state);
                }
            }
            return true;
        }
    }
}
