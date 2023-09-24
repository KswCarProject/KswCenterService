package android.view.accessibility;

import android.Manifest;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.content.ComponentName;
import android.content.Context;
import android.content.p002pm.ServiceInfo;
import android.content.res.Resources;
import android.net.wifi.WifiEnterpriseConfig;
import android.p007os.Binder;
import android.p007os.Handler;
import android.p007os.IBinder;
import android.p007os.Looper;
import android.p007os.Message;
import android.p007os.RemoteException;
import android.p007os.ServiceManager;
import android.p007os.SystemClock;
import android.util.ArrayMap;
import android.util.Log;
import android.util.SparseArray;
import android.view.IWindow;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.IAccessibilityManager;
import android.view.accessibility.IAccessibilityManagerClient;
import com.android.internal.C3132R;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.util.IntPair;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes4.dex */
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
    final Handler mHandler;
    int mInteractiveUiTimeout;
    @UnsupportedAppUsage(maxTargetSdk = 28)
    boolean mIsEnabled;
    @UnsupportedAppUsage(trackingBug = 123768939)
    boolean mIsHighTextContrastEnabled;
    boolean mIsTouchExplorationEnabled;
    int mNonInteractiveUiTimeout;
    private SparseArray<List<AccessibilityRequestPreparer>> mRequestPreparerLists;
    @UnsupportedAppUsage
    private IAccessibilityManager mService;
    @UnsupportedAppUsage
    final int mUserId;
    @UnsupportedAppUsage
    private final Object mLock = new Object();
    int mRelevantEventTypes = -1;
    @UnsupportedAppUsage
    private final ArrayMap<AccessibilityStateChangeListener, Handler> mAccessibilityStateChangeListeners = new ArrayMap<>();
    private final ArrayMap<TouchExplorationStateChangeListener, Handler> mTouchExplorationStateChangeListeners = new ArrayMap<>();
    private final ArrayMap<HighTextContrastChangeListener, Handler> mHighTextContrastStateChangeListeners = new ArrayMap<>();
    private final ArrayMap<AccessibilityServicesStateChangeListener, Handler> mServicesStateChangeListeners = new ArrayMap<>();
    private final IAccessibilityManagerClient.Stub mClient = new BinderC27621();
    final Handler.Callback mCallback = new MyCallback(this, null);

    /* loaded from: classes4.dex */
    public interface AccessibilityPolicy {
        List<AccessibilityServiceInfo> getEnabledAccessibilityServiceList(int i, List<AccessibilityServiceInfo> list);

        List<AccessibilityServiceInfo> getInstalledAccessibilityServiceList(List<AccessibilityServiceInfo> list);

        int getRelevantEventTypes(int i);

        boolean isEnabled(boolean z);

        AccessibilityEvent onAccessibilityEvent(AccessibilityEvent accessibilityEvent, boolean z, int i);
    }

    /* loaded from: classes4.dex */
    public interface AccessibilityServicesStateChangeListener {
        void onAccessibilityServicesStateChanged(AccessibilityManager accessibilityManager);
    }

    /* loaded from: classes4.dex */
    public interface AccessibilityStateChangeListener {
        void onAccessibilityStateChanged(boolean z);
    }

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes4.dex */
    public @interface ContentFlag {
    }

    /* loaded from: classes4.dex */
    public interface HighTextContrastChangeListener {
        void onHighTextContrastStateChanged(boolean z);
    }

    /* loaded from: classes4.dex */
    public interface TouchExplorationStateChangeListener {
        void onTouchExplorationStateChanged(boolean z);
    }

    /* renamed from: android.view.accessibility.AccessibilityManager$1 */
    /* loaded from: classes4.dex */
    class BinderC27621 extends IAccessibilityManagerClient.Stub {
        BinderC27621() {
        }

        @Override // android.view.accessibility.IAccessibilityManagerClient
        public void setState(int state) {
            AccessibilityManager.this.mHandler.obtainMessage(1, state, 0).sendToTarget();
        }

        @Override // android.view.accessibility.IAccessibilityManagerClient
        public void notifyServicesStateChanged(long updatedUiTimeout) {
            AccessibilityManager.this.updateUiTimeout(updatedUiTimeout);
            synchronized (AccessibilityManager.this.mLock) {
                if (AccessibilityManager.this.mServicesStateChangeListeners.isEmpty()) {
                    return;
                }
                ArrayMap<AccessibilityServicesStateChangeListener, Handler> listeners = new ArrayMap<>(AccessibilityManager.this.mServicesStateChangeListeners);
                int numListeners = listeners.size();
                for (int i = 0; i < numListeners; i++) {
                    final AccessibilityServicesStateChangeListener listener = (AccessibilityServicesStateChangeListener) AccessibilityManager.this.mServicesStateChangeListeners.keyAt(i);
                    ((Handler) AccessibilityManager.this.mServicesStateChangeListeners.valueAt(i)).post(new Runnable() { // from class: android.view.accessibility.-$$Lambda$AccessibilityManager$1$o7fCplskH9NlBwJvkl6NoZ0L_BA
                        @Override // java.lang.Runnable
                        public final void run() {
                            listener.onAccessibilityServicesStateChanged(AccessibilityManager.this);
                        }
                    });
                }
            }
        }

        @Override // android.view.accessibility.IAccessibilityManagerClient
        public void setRelevantEventTypes(int eventTypes) {
            AccessibilityManager.this.mRelevantEventTypes = eventTypes;
        }
    }

    @UnsupportedAppUsage
    public static AccessibilityManager getInstance(Context context) {
        int userId;
        synchronized (sInstanceSync) {
            if (sInstance == null) {
                if (Binder.getCallingUid() != 1000 && context.checkCallingOrSelfPermission(Manifest.C0000permission.INTERACT_ACROSS_USERS) != 0 && context.checkCallingOrSelfPermission(Manifest.C0000permission.INTERACT_ACROSS_USERS_FULL) != 0) {
                    userId = context.getUserId();
                    sInstance = new AccessibilityManager(context, (IAccessibilityManager) null, userId);
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
            z = this.mIsEnabled || (this.mAccessibilityPolicy != null && this.mAccessibilityPolicy.isEnabled(this.mIsEnabled));
        }
        return z;
    }

    public boolean isTouchExplorationEnabled() {
        synchronized (this.mLock) {
            IAccessibilityManager service = getServiceLocked();
            if (service == null) {
                return false;
            }
            return this.mIsTouchExplorationEnabled;
        }
    }

    @UnsupportedAppUsage
    public boolean isHighTextContrastEnabled() {
        synchronized (this.mLock) {
            IAccessibilityManager service = getServiceLocked();
            if (service == null) {
                return false;
            }
            return this.mIsHighTextContrastEnabled;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:43:0x008b, code lost:
        if (r8 == r2) goto L38;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x008e, code lost:
        return;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void sendAccessibilityEvent(AccessibilityEvent event) {
        AccessibilityEvent dispatchedEvent;
        synchronized (this.mLock) {
            IAccessibilityManager service = getServiceLocked();
            if (service == null) {
                return;
            }
            event.setEventTime(SystemClock.uptimeMillis());
            if (this.mAccessibilityPolicy != null) {
                dispatchedEvent = this.mAccessibilityPolicy.onAccessibilityEvent(event, this.mIsEnabled, this.mRelevantEventTypes);
                if (dispatchedEvent == null) {
                    return;
                }
            } else {
                dispatchedEvent = event;
            }
            if (!isEnabled()) {
                Looper myLooper = Looper.myLooper();
                if (myLooper == Looper.getMainLooper()) {
                    throw new IllegalStateException("Accessibility off. Did you forget to check that?");
                }
                Log.m70e(LOG_TAG, "AccessibilityEvent sent with accessibility disabled");
            } else if ((dispatchedEvent.getEventType() & this.mRelevantEventTypes) == 0) {
            } else {
                int userId = this.mUserId;
                try {
                    try {
                        long identityToken = Binder.clearCallingIdentity();
                        try {
                            service.sendAccessibilityEvent(dispatchedEvent, userId);
                        } finally {
                            Binder.restoreCallingIdentity(identityToken);
                        }
                    } catch (RemoteException re) {
                        Log.m69e(LOG_TAG, "Error during sending " + dispatchedEvent + WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER, re);
                    }
                } finally {
                    if (event != dispatchedEvent) {
                        event.recycle();
                    }
                    dispatchedEvent.recycle();
                }
            }
        }
    }

    public void interrupt() {
        synchronized (this.mLock) {
            IAccessibilityManager service = getServiceLocked();
            if (service == null) {
                return;
            }
            if (!isEnabled()) {
                Looper myLooper = Looper.myLooper();
                if (myLooper == Looper.getMainLooper()) {
                    throw new IllegalStateException("Accessibility off. Did you forget to check that?");
                }
                Log.m70e(LOG_TAG, "Interrupt called with accessibility disabled");
                return;
            }
            int userId = this.mUserId;
            try {
                service.interrupt(userId);
            } catch (RemoteException re) {
                Log.m69e(LOG_TAG, "Error while requesting interrupt from all services. ", re);
            }
        }
    }

    @Deprecated
    public List<ServiceInfo> getAccessibilityServiceList() {
        List<AccessibilityServiceInfo> infos = getInstalledAccessibilityServiceList();
        List<ServiceInfo> services = new ArrayList<>();
        int infoCount = infos.size();
        for (int i = 0; i < infoCount; i++) {
            AccessibilityServiceInfo info = infos.get(i);
            services.add(info.getResolveInfo().serviceInfo);
        }
        return Collections.unmodifiableList(services);
    }

    public List<AccessibilityServiceInfo> getInstalledAccessibilityServiceList() {
        synchronized (this.mLock) {
            IAccessibilityManager service = getServiceLocked();
            if (service == null) {
                return Collections.emptyList();
            }
            int userId = this.mUserId;
            List<AccessibilityServiceInfo> services = null;
            try {
                services = service.getInstalledAccessibilityServiceList(userId);
            } catch (RemoteException re) {
                Log.m69e(LOG_TAG, "Error while obtaining the installed AccessibilityServices. ", re);
            }
            if (this.mAccessibilityPolicy != null) {
                services = this.mAccessibilityPolicy.getInstalledAccessibilityServiceList(services);
            }
            if (services != null) {
                return Collections.unmodifiableList(services);
            }
            return Collections.emptyList();
        }
    }

    public List<AccessibilityServiceInfo> getEnabledAccessibilityServiceList(int feedbackTypeFlags) {
        synchronized (this.mLock) {
            IAccessibilityManager service = getServiceLocked();
            if (service == null) {
                return Collections.emptyList();
            }
            int userId = this.mUserId;
            List<AccessibilityServiceInfo> services = null;
            try {
                services = service.getEnabledAccessibilityServiceList(feedbackTypeFlags, userId);
            } catch (RemoteException re) {
                Log.m69e(LOG_TAG, "Error while obtaining the installed AccessibilityServices. ", re);
            }
            if (this.mAccessibilityPolicy != null) {
                services = this.mAccessibilityPolicy.getEnabledAccessibilityServiceList(feedbackTypeFlags, services);
            }
            if (services != null) {
                return Collections.unmodifiableList(services);
            }
            return Collections.emptyList();
        }
    }

    public boolean addAccessibilityStateChangeListener(AccessibilityStateChangeListener listener) {
        addAccessibilityStateChangeListener(listener, null);
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
        addTouchExplorationStateChangeListener(listener, null);
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
            requestPreparerList = new ArrayList(1);
            this.mRequestPreparerLists.put(id, requestPreparerList);
        }
        requestPreparerList.add(preparer);
    }

    public void removeAccessibilityRequestPreparer(AccessibilityRequestPreparer preparer) {
        int viewId;
        List<AccessibilityRequestPreparer> requestPreparerList;
        if (this.mRequestPreparerLists != null && (requestPreparerList = this.mRequestPreparerLists.get((viewId = preparer.getAccessibilityViewId()))) != null) {
            requestPreparerList.remove(preparer);
            if (requestPreparerList.isEmpty()) {
                this.mRequestPreparerLists.remove(viewId);
            }
        }
    }

    public int getRecommendedTimeoutMillis(int originalTimeout, int uiContentFlags) {
        boolean hasIconsOrText = false;
        boolean hasControls = (uiContentFlags & 4) != 0;
        if ((uiContentFlags & 1) != 0 || (uiContentFlags & 2) != 0) {
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

    /* JADX INFO: Access modifiers changed from: private */
    @UnsupportedAppUsage
    public void setStateLocked(int stateFlags) {
        boolean enabled = (stateFlags & 1) != 0;
        boolean touchExplorationEnabled = (stateFlags & 2) != 0;
        boolean highTextContrastEnabled = (stateFlags & 4) != 0;
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
                Log.m69e(LOG_TAG, "Error while adding an accessibility interaction connection. ", re);
                return -1;
            }
        }
    }

    public void removeAccessibilityInteractionConnection(IWindow windowToken) {
        synchronized (this.mLock) {
            IAccessibilityManager service = getServiceLocked();
            if (service == null) {
                return;
            }
            try {
                service.removeAccessibilityInteractionConnection(windowToken);
            } catch (RemoteException re) {
                Log.m69e(LOG_TAG, "Error while removing an accessibility interaction connection. ", re);
            }
        }
    }

    @SystemApi
    public void performAccessibilityShortcut() {
        synchronized (this.mLock) {
            IAccessibilityManager service = getServiceLocked();
            if (service == null) {
                return;
            }
            try {
                service.performAccessibilityShortcut();
            } catch (RemoteException re) {
                Log.m69e(LOG_TAG, "Error performing accessibility shortcut. ", re);
            }
        }
    }

    public void notifyAccessibilityButtonClicked(int displayId) {
        synchronized (this.mLock) {
            IAccessibilityManager service = getServiceLocked();
            if (service == null) {
                return;
            }
            try {
                service.notifyAccessibilityButtonClicked(displayId);
            } catch (RemoteException re) {
                Log.m69e(LOG_TAG, "Error while dispatching accessibility button click", re);
            }
        }
    }

    public void notifyAccessibilityButtonVisibilityChanged(boolean shown) {
        synchronized (this.mLock) {
            IAccessibilityManager service = getServiceLocked();
            if (service == null) {
                return;
            }
            try {
                service.notifyAccessibilityButtonVisibilityChanged(shown);
            } catch (RemoteException re) {
                Log.m69e(LOG_TAG, "Error while dispatching accessibility button visibility change", re);
            }
        }
    }

    public void setPictureInPictureActionReplacingConnection(IAccessibilityInteractionConnection connection) {
        synchronized (this.mLock) {
            IAccessibilityManager service = getServiceLocked();
            if (service == null) {
                return;
            }
            try {
                service.setPictureInPictureActionReplacingConnection(connection);
            } catch (RemoteException re) {
                Log.m69e(LOG_TAG, "Error setting picture in picture action replacement", re);
            }
        }
    }

    public String getAccessibilityShortcutService() {
        IAccessibilityManager service;
        synchronized (this.mLock) {
            service = getServiceLocked();
        }
        if (service != null) {
            try {
                return service.getAccessibilityShortcutService();
            } catch (RemoteException re) {
                re.rethrowFromSystemServer();
                return null;
            }
        }
        return null;
    }

    private IAccessibilityManager getServiceLocked() {
        if (this.mService == null) {
            tryConnectToServiceLocked(null);
        }
        return this.mService;
    }

    private void tryConnectToServiceLocked(IAccessibilityManager service) {
        if (service == null) {
            IBinder iBinder = ServiceManager.getService(Context.ACCESSIBILITY_SERVICE);
            if (iBinder == null) {
                return;
            }
            service = IAccessibilityManager.Stub.asInterface(iBinder);
        }
        try {
            long userStateAndRelevantEvents = service.addClient(this.mClient, this.mUserId);
            setStateLocked(IntPair.first(userStateAndRelevantEvents));
            this.mRelevantEventTypes = IntPair.second(userStateAndRelevantEvents);
            updateUiTimeout(service.getRecommendedTimeoutMillis());
            this.mService = service;
        } catch (RemoteException re) {
            Log.m69e(LOG_TAG, "AccessibilityManagerService is dead", re);
        }
    }

    private void notifyAccessibilityStateChanged() {
        synchronized (this.mLock) {
            if (this.mAccessibilityStateChangeListeners.isEmpty()) {
                return;
            }
            final boolean isEnabled = isEnabled();
            ArrayMap<AccessibilityStateChangeListener, Handler> listeners = new ArrayMap<>(this.mAccessibilityStateChangeListeners);
            int numListeners = listeners.size();
            for (int i = 0; i < numListeners; i++) {
                final AccessibilityStateChangeListener listener = listeners.keyAt(i);
                listeners.valueAt(i).post(new Runnable() { // from class: android.view.accessibility.-$$Lambda$AccessibilityManager$yzw5NYY7_MfAQ9gLy3mVllchaXo
                    @Override // java.lang.Runnable
                    public final void run() {
                        AccessibilityManager.AccessibilityStateChangeListener.this.onAccessibilityStateChanged(isEnabled);
                    }
                });
            }
        }
    }

    private void notifyTouchExplorationStateChanged() {
        synchronized (this.mLock) {
            if (this.mTouchExplorationStateChangeListeners.isEmpty()) {
                return;
            }
            final boolean isTouchExplorationEnabled = this.mIsTouchExplorationEnabled;
            ArrayMap<TouchExplorationStateChangeListener, Handler> listeners = new ArrayMap<>(this.mTouchExplorationStateChangeListeners);
            int numListeners = listeners.size();
            for (int i = 0; i < numListeners; i++) {
                final TouchExplorationStateChangeListener listener = listeners.keyAt(i);
                listeners.valueAt(i).post(new Runnable() { // from class: android.view.accessibility.-$$Lambda$AccessibilityManager$a0OtrjOl35tiW2vwyvAmY6_LiLI
                    @Override // java.lang.Runnable
                    public final void run() {
                        AccessibilityManager.TouchExplorationStateChangeListener.this.onTouchExplorationStateChanged(isTouchExplorationEnabled);
                    }
                });
            }
        }
    }

    private void notifyHighTextContrastStateChanged() {
        synchronized (this.mLock) {
            if (this.mHighTextContrastStateChangeListeners.isEmpty()) {
                return;
            }
            final boolean isHighTextContrastEnabled = this.mIsHighTextContrastEnabled;
            ArrayMap<HighTextContrastChangeListener, Handler> listeners = new ArrayMap<>(this.mHighTextContrastStateChangeListeners);
            int numListeners = listeners.size();
            for (int i = 0; i < numListeners; i++) {
                final HighTextContrastChangeListener listener = listeners.keyAt(i);
                listeners.valueAt(i).post(new Runnable() { // from class: android.view.accessibility.-$$Lambda$AccessibilityManager$4M6GrmFiqsRwVzn352N10DcU6RM
                    @Override // java.lang.Runnable
                    public final void run() {
                        AccessibilityManager.HighTextContrastChangeListener.this.onHighTextContrastStateChanged(isHighTextContrastEnabled);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateUiTimeout(long uiTimeout) {
        this.mInteractiveUiTimeout = IntPair.first(uiTimeout);
        this.mNonInteractiveUiTimeout = IntPair.second(uiTimeout);
    }

    public static boolean isAccessibilityButtonSupported() {
        Resources res = Resources.getSystem();
        return res.getBoolean(C3132R.bool.config_showNavigationBar);
    }

    /* loaded from: classes4.dex */
    private final class MyCallback implements Handler.Callback {
        public static final int MSG_SET_STATE = 1;

        private MyCallback() {
        }

        /* synthetic */ MyCallback(AccessibilityManager x0, BinderC27621 x1) {
            this();
        }

        @Override // android.p007os.Handler.Callback
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
